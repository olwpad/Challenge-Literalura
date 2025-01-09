package com.example.challengeliteralura.serviceAPI;

public interface IConvierteDatos {
    <T> T obtenerDatos(String json, Class<T> clase);

}
