package com.example.challengeliteralura.model;


import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosLibros(
        @JsonAlias("id") Integer id,

        @JsonAlias("title") String titulo,
        @JsonAlias("download_count") String descargas,

        @JsonAlias("authors") List<DatosAutores> autores
)

{



}
