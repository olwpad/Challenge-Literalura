package com.example.challengeliteralura.model;


import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ResponseReq(

        @JsonAlias("results") List<DatosLibros> libros
) {



}
