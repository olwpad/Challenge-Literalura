package com.example.challengeliteralura.model;

import jakarta.persistence.*;


@Entity
@Table(name = "autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String nombre;
    private String anioNacimiento;
    private String anioMuerte;

    @ManyToOne
    @jakarta.persistence.JoinColumn(name = "libro_id")
    private Libro libro;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Autor() {
    }

    public Autor(String nombre, String anioNacimiento, String anioMuerte) {
        this.nombre = nombre;
        this.anioNacimiento = anioNacimiento;
        this.anioMuerte = anioMuerte;
    }

    public String getNombre() {
        return nombre;
    }

    public String getAnioNacimiento() {
        return anioNacimiento;
    }

    public String getAnioMuerte() {
        return anioMuerte;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setAnioNacimiento(String anioNacimiento) {
        this.anioNacimiento = anioNacimiento;
    }

    public void setAnioMuerte(String anioMuerte) {
        this.anioMuerte = anioMuerte;
    }

    @Override
    public String toString() {
        return "Autor{" +
                "nombre='" + nombre + '\'' +
                ", anioNacimiento='" + anioNacimiento + '\'' +
                ", anioMuerte='" + anioMuerte + '\'' +
                '}';
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }
}
