package com.example.challengeliteralura.model;


import jakarta.persistence.*;

import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "libros")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String titulo;
    private String descargas;

    @OneToMany(mappedBy = "libro", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Autor> autores;


    public Libro(DatosLibros datosLibros) {
        this.titulo = datosLibros.titulo();
        this.descargas = datosLibros.descargas();
    }

    public Libro() {
    }

    public Integer getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescargas() {
        return descargas;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }


    public List<Autor> getAutores() {
        return autores;
    }

    public void setAutores(List<Autor> autores) {
        autores.forEach(autor -> autor.setLibro(this));
        this.autores = autores;
    }

    public void setDescargas(String descargas) {
        this.descargas = descargas;
    }



}
