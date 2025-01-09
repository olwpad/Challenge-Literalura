package com.example.challengeliteralura.repository;

import com.example.challengeliteralura.model.Autor;
import com.example.challengeliteralura.model.Idioma;
import com.example.challengeliteralura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface LibroRepository extends JpaRepository<Libro,Long> {

    List<Libro> findByTituloContainsIgnoreCase(String nombreLibro);

    @Query("SELECT l FROM Libro l WHERE l.idioma = :idioma")
    List<Libro> findLibrosByIdioma(@Param("idioma") Idioma idioma);


    @Query("SELECT a FROM Autor a WHERE a.anioNacimiento <= :añoConsulta AND a.anioMuerte >= :añoConsulta")
    List<Autor> findAutoresVivosEnAño(int añoConsulta);
}