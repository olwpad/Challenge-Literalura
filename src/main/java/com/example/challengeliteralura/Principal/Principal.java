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
                    1 - Buscar libro por título
                    2 - listar autores Registrados
                    3 - listar Libros Registrados
                    4 - listar libros por idioma
                    0 - Salir
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1 -> buscarLibroPorTitulo();
                case 2 -> listarAutoresRegistrados();
                case 3-> listarLibrosRegistrados();
                case 4 -> listarLibrosPorIdioma();
                case 0 -> System.out.println("Cerrando la aplicación...");
                default -> System.out.println("Opción inválida");
            }
        }
    }

    private void listarAutoresRegistrados() {
        List<Autor> autores = repositorio.findAll().stream().flatMap(libro -> libro.getAutores().stream()).toList();

        if (autores.isEmpty()) {
            System.out.println("No hay autores registrados.");
            return;
        }else
        {
            System.out.println("============================================");
            System.out.println("               Autores Registrados           ");
            System.out.println("============================================");

            for (Autor autor : autores) {
                System.out.println("Nombre:    " + autor.getNombre());
                System.out.println("Año de Nacimiento: " + autor.getAnioNacimiento());
                System.out.println("Año de Muerte: " + autor.getAnioMuerte());
                System.out.println("--------------------------------------------");
            }
        }
    }

    private void listarLibrosPorIdioma() {
    }

    private void listarLibrosRegistrados() {
        List<Libro> libros = repositorio.findAll();

        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados.");
            return;
        }

        System.out.println("============================================");
        System.out.println("               Libros Registrados           ");
        System.out.println("============================================");

        for (Libro libro : libros) {
            System.out.println("Título:    " + libro.getTitulo());
            System.out.println("Autor(es): " + String.join(", ", libro.getAutores().stream().map(Autor::getNombre).toList()));
            System.out.println("Descargas: " + libro.getDescargas());
            System.out.println("--------------------------------------------");
        }
    }




    public void buscarLibroPorTitulo (){
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
