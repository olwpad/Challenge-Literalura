package com.example.challengeliteralura.repository;

import com.example.challengeliteralura.model.Autor;
import com.example.challengeliteralura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface LibroRepository extends JpaRepository<Libro,Long> {

    List<Libro> findByTituloContainsIgnoreCase(String nombreLibro);



}