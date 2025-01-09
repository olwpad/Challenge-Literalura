package com.example.challengeliteralura.repository;

import com.example.challengeliteralura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;


public interface LibroRepository extends JpaRepository<Libro,Long> {


}