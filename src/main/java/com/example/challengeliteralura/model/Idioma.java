package com.example.challengeliteralura.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Idioma {
    ESPAÑOL("es"),
    INGLES("en"),
    FRANCES("fr"),
    ITALIANO("it"),
    PORTUGUES("pt"),
    ALEMAN("de"),
    JAPONES("ja"),
    CHINO("zh"),
    RUSO("ru"),
    UNKNOWN("unknown");

    private final String code;

    Idioma(String code) {
        this.code = code;
    }

    // Usamos @JsonCreator para permitir que Jackson deserialice el código
    @JsonCreator
    public static Idioma fromCode(String code) {
        for (Idioma idioma : Idioma.values()) {
            if (idioma.code.equalsIgnoreCase(code)) {
                return idioma;
            }
        }
        return UNKNOWN;  // Valor por defecto si no se encuentra
    }

    // Usamos @JsonValue para que se pueda obtener el código al serializar
    @JsonValue
    public String getCode() {
        return code;
    }
}
