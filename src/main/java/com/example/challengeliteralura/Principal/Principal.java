package com.example.challengeliteralura.Principal;

import com.example.challengeliteralura.model.Autor;
import com.example.challengeliteralura.model.DatosLibros;
import com.example.challengeliteralura.model.Libro;
import com.example.challengeliteralura.model.ResponseReq;
import com.example.challengeliteralura.repository.LibroRepository;
import com.example.challengeliteralura.serviceAPI.ConsumoAPI;
import com.example.challengeliteralura.serviceAPI.ConvierteDatos;

import java.util.List;
import java.util.Scanner;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private final String URL_BASE = "https://gutendex.com/books/?search=";
    private ConvierteDatos conversor = new ConvierteDatos();
    private LibroRepository repositorio;

    private  String languaje;


    public Principal(LibroRepository libroRepository) {
        this.repositorio = libroRepository;

    }

    public void mostrarMenuLibros() {

        while(true){
            System.out.println("Escribe el idioma de los libros que deseas buscar");
            languaje = teclado.nextLine();
            if(languaje.equals("es") || languaje.equals("en")){
                break;
            }else{
                System.out.println("Idioma no valido");
            }
        }


        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    1 - Buscar libro
                    2 - Buscar libros por autor
                    3 - Buscar libros por categoría
                    4 - Buscar libros por título
                    5 - Mostrar libros buscados
                    0 - Salir
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarLibroWeb();
                    break;
                case 2:
                    buscarLibrosPorAutor();
                    break;
                case 3:
                    buscarLibroPorCategoria();
                    break;
                case 4:
                    buscarLibroPorTitulo();
                    break;
                case 5:
                    mostrarLibrosBuscados();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }
    }

    private void mostrarLibrosBuscados() {
    }

    private void buscarLibroPorTitulo() {


    }

    private void buscarLibroPorCategoria() {
    }

    private void buscarLibrosPorAutor() {

    }

    public void buscarLibroWeb (){
        ResponseReq response = new ResponseReq(getDatosSerie().libros());
        // Accede a la lista de libros
        if (response.libros() != null) {
            for (DatosLibros datoslIbro : response.libros()) {
                Libro libro = new Libro(datoslIbro);
                libro.setAutores(List.of(new Autor(datoslIbro.autores().get(0).nombre(), datoslIbro.autores().get(0).anioNacimiento(), datoslIbro.autores().get(0).anioMuerte())));
                repositorio.save(libro);

            }

        } else {
            System.out.println("No se encontraron libros.");
        }
    }


    private ResponseReq getDatosSerie() {
        System.out.println("Escribe el nombre del libro  que deseas buscar");
        var nombreLibro = teclado.nextLine();
        var json = consumoApi.obtenerDatos(URL_BASE + nombreLibro.replace(" ", "+" ) + "&languages=" + languaje);
        System.out.println(json);
        ResponseReq datos = conversor.obtenerDatos(json, ResponseReq.class);
        return datos;
    }


}
