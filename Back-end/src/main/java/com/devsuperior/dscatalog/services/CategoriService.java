package com.devsuperior.dscatalog.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import com.devsuperior.dscatalog.dtos.CategoryDTO;
import com.devsuperior.dscatalog.entities.CategoryEntity;
import com.devsuperior.dscatalog.repositories.CategoryRepository;

@Service
public class CategoriService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public Page<CategoryDTO> findAllPaged(PageRequest pageRequest) {
        Page<CategoryEntity> list = categoryRepository.findAll(pageRequest);

        return list.map(x -> new CategoryDTO(x));
    }

    @Transactional(readOnly = true)
    public CategoryDTO findById(Long id) throws ResouceEntityNotFoundExeption {

        Optional<CategoryEntity> obj = categoryRepository.findById(id);
        CategoryEntity entity = obj.orElseThrow(() -> new ResouceEntityNotFoundExeption("entity not flound"));
        return new CategoryDTO(entity);
    }

    @Transactional
    public CategoryDTO insert(CategoryDTO dto) {

        CategoryEntity entity = new CategoryEntity();
        entity.setName(dto.getName());
        entity = categoryRepository.save(entity);

        return new CategoryDTO(entity);
    }

    @Transactional
    public CategoryDTO update(Long id, CategoryDTO dto) throws ResouceEntityNotFoundExeption {

        try {
            CategoryEntity entity = categoryRepository.getOne(id);
            entity.setName(dto.getName());// ele pega o getName da classe categoryDTO, que tem o getName
            entity = categoryRepository.save(entity);
            return new CategoryDTO(entity);

        } catch (EntityNotFoundException e) {
            throw new ResouceEntityNotFoundExeption("Id not flound " + id);
        }
    }

    public void delete(Long id) throws ResouceEntityNotFoundExeption, DataBaseExeption {

        try {
            categoryRepository.deleteById(id);

        } catch (EmptyResultDataAccessException e) {
            throw new ResouceEntityNotFoundExeption("Id not flound " + id);

        } catch (DataIntegrityViolationException e) {
            throw new DataBaseExeption("Integrity violation");
        }
    }
}