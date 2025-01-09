package com.example.challengeliteralura.model;


import jakarta.persistence.*;

import java.util.List;

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

    @Enumerated(EnumType.STRING)
    private Idioma idioma;



    public Libro(DatosLibros datosLibros) {
        System.out.println("datosLibros = " + datosLibros);

        // Obtener el primer idioma de la lista (si existe)
        String idiomaCode = datosLibros.idioma().stream()
                .findFirst()  // Obtener el primer elemento de la lista
                .map(Idioma::getCode)  // Obtener el código del idioma
                .orElse(Idioma.UNKNOWN.getCode());  // Si no hay idioma, usar UNKNOWN

        // Asignar el idioma utilizando la función fromCode
        this.idioma = Idioma.fromCode(idiomaCode);

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


    public Idioma getIdioma() {
        return idioma;
    }

    public void setIdioma(Idioma idioma) {
        this.idioma = idioma;
    }

}
