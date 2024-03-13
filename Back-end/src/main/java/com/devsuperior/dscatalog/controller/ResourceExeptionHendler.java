package com.devsuperior.dscatalog.controller;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.devsuperior.dscatalog.services.DataBaseExeption;
import com.devsuperior.dscatalog.services.ResouceEntityNotFoundExeption;

@ControllerAdvice
public class ResourceExeptionHendler {

    @ExceptionHandler(ResouceEntityNotFoundExeption.class)
    public ResponseEntity<StandardError> entityNotFound(ResouceEntityNotFoundExeption e, HttpServletRequest request) {
        StandardError erro = new StandardError();
        erro.setTimeStamp(Instant.now());
        erro.setStatus(HttpStatus.NOT_FOUND.value());// 404
        erro.setError("Resource not found");
        erro.setMessage(e.getMessage());
        erro.setPath(request.getRequestURI());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
    }

    @ExceptionHandler(DataBaseExeption.class)
    public ResponseEntity<StandardError> dataBase(DataBaseExeption e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        StandardError erro = new StandardError();
        erro.setTimeStamp(Instant.now());// hora exata do erro
        erro.setStatus(status.value());// 400
        erro.setError("dataBase Exception");// mensagem de erro personalizada
        erro.setMessage(e.getMessage());// Manda para a classe DataBaseExeption que joga no catch e printa e a mensagem
                                        // que passamos
        erro.setPath(request.getRequestURI());// caminho aonde deu erro

        return ResponseEntity.status(status).body(erro);
    }

}