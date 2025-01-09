package com.example.challengeliteralura.Principal;

import com.example.challengeliteralura.model.*;
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


        var opcion = -1;
        while (opcion != 0) {
            System.out.println("============================================");
            System.out.println("               Challenge Literalura          ");
            System.out.println("============================================");
            var menu = """
                    1 - Buscar libro por título
                    2 - listar autores Registrados
                    3 - listar Libros Registrados
                    4 - listar libros por idioma
                    5 - listar autores por determinado año
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
                case 5 -> buscarAutoresVivosPorAño();
                default -> System.out.println("Opción inválida");
            }

        }
    }

    private void buscarAutoresVivosPorAño() {
        System.out.println("Por favor, ingrese un determinado año para buscar autores vivos.");
        int añoConsulta = teclado.nextInt();
        teclado.nextLine();

        List<Autor> autoresVivos = repositorio.findAutoresVivosEnAño(añoConsulta);

        if (autoresVivos.isEmpty()) {
            System.out.println("No se encontraron autores vivos en el año " + añoConsulta + ".");
        } else {
            System.out.println("============================================");
            System.out.println("               Autores Vivos en el año " + añoConsulta);
            System.out.println("============================================");

            for (Autor autor : autoresVivos) {
                System.out.println("Nombre: " + autor.getNombre());
                System.out.println("Año de Nacimiento: " + autor.getAnioNacimiento());
                System.out.println("Año de Muerte: " + autor.getAnioMuerte());
                System.out.println("--------------------------------------------");
            }
        }
    }

    public void menuIdioma(){
        System.out.println("es : " + "español");
        System.out.println("en : " + "ingles");
        System.out.println("fr : " + "frances");
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
        System.out.println("Por favor ingrese el idioma de los libros que desea buscar");
        menuIdioma();
        languaje = teclado.nextLine();
        Idioma idioma = Idioma.fromCode(languaje);
        repositorio.findLibrosByIdioma(idioma).forEach(libro -> {
            if (libro == null) {
                System.out.println("No se encontraron libros en el idioma " + idioma);
                return;
            }
            System.out.println("============================================");
            System.out.println("               Libros por Idioma           ");
            System.out.println("--------------------------------------------");
            System.out.println("Título:    " + libro.getTitulo());
            System.out.println("Autor(es): " + String.join(", ", libro.getAutores().stream().map(Autor::getNombre).toList()));
            System.out.println("Descargas: " + libro.getDescargas());
            System.out.println("Idioma:    " + libro.getIdioma());
            System.out.println("--------------------------------------------");
        });

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
            System.out.println("Idioma:    " + libro.getIdioma());
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
        while(true){
            System.out.println("Escribe el idioma de los libros que deseas buscar");
            menuIdioma();
            languaje = teclado.nextLine();
            if(languaje.equals("es") || languaje.equals("en")){
                break;
            }else{
                System.out.println("Idioma no valido");
            }
        }

        System.out.println("Escribe el nombre del libro  que deseas buscar");
        var nombreLibro = teclado.nextLine();
        var json = consumoApi.obtenerDatos(URL_BASE + nombreLibro.replace(" ", "+" ) + "&languages=" + languaje);
        System.out.println(json);
        ResponseReq datos = conversor.obtenerDatos(json, ResponseReq.class);
        return datos;
    }


}
