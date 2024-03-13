package com.devsuperior.dscatalog.services;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.devsuperior.dscatalog.dtos.CategoryDTO;
import com.devsuperior.dscatalog.dtos.ProductDTO;
import com.devsuperior.dscatalog.entities.CategoryEntity;
import com.devsuperior.dscatalog.entities.ProductEntity;
import com.devsuperior.dscatalog.repositories.CategoryRepository;
import com.devsuperior.dscatalog.repositories.ProductRepository;

import javax.persistence.EntityNotFoundException;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public Page<ProductDTO> findAllPaged(PageRequest pageRequest) {
        Page<ProductEntity> list = productRepository.findAll(pageRequest);
        return list.map(categories -> new ProductDTO(categories));
    }

    @Transactional(readOnly = true)
    public ProductDTO findById(Long id) throws ResouceEntityNotFoundExeption {
        Optional<ProductEntity> productEntity = productRepository.findById(id);
        ProductEntity entity = productEntity.orElseThrow(() -> new ResouceEntityNotFoundExeption("entity not flound"));
        return new ProductDTO(entity, entity.getCategories());
    }

    @Transactional(readOnly = true)
    public ProductDTO insert(ProductDTO productDTO) {
        ProductEntity productEntity = new ProductEntity();
        copyDtoToEntity(productDTO, productEntity);
        productEntity = productRepository.save(productEntity);
        return new ProductDTO(productEntity);
    }

    @Transactional
    public ProductDTO update(Long id, ProductDTO productDTO) throws ResouceEntityNotFoundExeption {

        try {
            ProductEntity entity = productRepository.getOne(id);
            copyDtoToEntity(productDTO, entity);
            entity = productRepository.save(entity);
            return new ProductDTO(entity);

        } catch (EntityNotFoundException e) {
            throw new ResouceEntityNotFoundExeption("Id not flound " + id);
        }
    }

    public void delete(Long id) throws ResouceEntityNotFoundExeption, DataBaseExeption {

        try {
            productRepository.deleteById(id);

        } catch (EmptyResultDataAccessException e) {
            throw new ResouceEntityNotFoundExeption("Id not flound " + id);

        } catch (DataIntegrityViolationException e) {
            throw new DataBaseExeption("Integrity violation");
        }
    }

    private void copyDtoToEntity(ProductDTO productDTO, ProductEntity productEntity) {

        productEntity.setName(productDTO.getName());
        productEntity.setDescription(productDTO.getDescription());
        productEntity.setDate(productDTO.getDate());
        productEntity.setImgUrl(productDTO.getImgUrl());
        productEntity.setPrice(productDTO.getPrice());

        productEntity.getCategories().clear();

        for (CategoryDTO catDto : productDTO.getCategories()) {
            CategoryEntity category = categoryRepository.getOne(catDto.getId());
            productEntity.getCategories().add(category);
        }
    }

}