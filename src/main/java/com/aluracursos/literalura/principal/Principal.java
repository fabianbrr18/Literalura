package com.aluracursos.literalura.principal;

import com.aluracursos.literalura.model.Autor;
import com.aluracursos.literalura.model.DatosLibro;
import com.aluracursos.literalura.model.DatosResultados;
import com.aluracursos.literalura.model.Libro;
import com.aluracursos.literalura.repository.AutorRepository;
import com.aluracursos.literalura.repository.LibroRepository;
import com.aluracursos.literalura.service.ConsumoAPI;
import com.aluracursos.literalura.service.ConvierteDatos;

import java.util.Optional;
import java.util.Scanner;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();
    private final String URL_BASE = "https://gutendex.com/books/";

    private AutorRepository autorRepository;
    private LibroRepository libroRepository;

    public Principal(AutorRepository autorRepository, LibroRepository libroRepository) {
        this.autorRepository = autorRepository;
        this.libroRepository = libroRepository;
    }

    public void muestraElMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    Elija la opción a través de su número:
                    1 - Buscar libro por título
                    2 - Listar libros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos en un determinado año
                    5 - Listar libros por idioma

                    0 - Salir
                    """;
            System.out.println(menu);
            try {
                opcion = teclado.nextInt();
                teclado.nextLine();

                switch (opcion) {
                    case 1:
                        buscarLibroPorTitulo();
                        break;
                    case 2:
                        listarLibrosRegistrados();
                        break;
                    case 3:
                        listarAutoresRegistrados();
                        break;
                    case 4:
                        listarAutoresVivosEnAnio();
                        break;
                    case 5:
                        listarLibrosPorIdioma();
                        break;
                    case 0:
                        System.out.println("Cerrando la aplicación...");
                        break;
                    default:
                        System.out.println("Opción inválida.");
                }
            } catch (Exception e) {
                System.out.println("Entrada inválida. Por favor, intente de nuevo.");
                teclado.nextLine(); // Limpiar el buffer
            }
        }
    }

    private void listarLibrosRegistrados() {
        System.out.println("----- LIBROS REGISTRADOS -----");
        libroRepository.findAll().forEach(System.out::println);
        System.out.println("------------------------------");
    }

    private void listarAutoresRegistrados() {
        System.out.println("----- AUTORES REGISTRADOS -----");
        autorRepository.findAll().forEach(System.out::println);
        System.out.println("-------------------------------");
    }

    private void listarAutoresVivosEnAnio() {
        System.out.println("Ingrese el año que desea consultar:");
        try {
            var anio = teclado.nextInt();
            teclado.nextLine(); // Limpiar el buffer

            var autoresVivos = autorRepository.findAutoresVivosEnAnio(anio);
            if (autoresVivos.isEmpty()) {
                System.out.println("No se encontraron autores vivos en el año " + anio);
            } else {
                System.out.println("----- AUTORES VIVOS EN " + anio + " -----");
                autoresVivos.forEach(System.out::println);
                System.out.println("------------------------------------");
            }
        } catch (Exception e) {
            System.out.println("Entrada inválida.");
            teclado.nextLine();
        }
    }

    private void listarLibrosPorIdioma() {
        System.out.println("Ingrese el idioma para buscar los libros (ejemplo: es, en, fr, pt):");
        var idioma = teclado.nextLine();
        var librosPorIdioma = libroRepository.findByIdioma(idioma);

        if (librosPorIdioma.isEmpty()) {
            System.out.println("No se encontraron libros registrados en ese idioma.");
        } else {
            System.out.println("----- LIBROS EN EL IDIOMA '" + idioma + "' -----");
            librosPorIdioma.forEach(System.out::println);
            System.out.println("-------------------------------------------");
        }
    }

    private DatosLibro getDatosLibro() {
        System.out.println("Escribe el nombre del libro que deseas buscar:");
        var nombreLibro = teclado.nextLine();
        var json = consumoAPI.obtenerDatos(URL_BASE + "?search=" + nombreLibro.replace(" ", "%20"));
        DatosResultados datos = conversor.obtenerDatos(json, DatosResultados.class);

        // Retornamos el primer libro de los resultados o null si no se encontraron.
        Optional<DatosLibro> libroBuscado = datos.resultados().stream().findFirst();
        return libroBuscado.orElse(null);
    }

    private void buscarLibroPorTitulo() {
        DatosLibro datos = getDatosLibro();
        if (datos != null) {
            System.out.println("Libro encontrado:");
            System.out.println("Título: " + datos.titulo());

            Libro libro = new Libro(datos);
            Autor autor = null;

            if (datos.autor() != null && !datos.autor().isEmpty()) {
                var datosAutor = datos.autor().get(0);
                System.out.println("Autor: " + datosAutor.nombre() +
                        " (Nacimiento: " + datosAutor.fechaDeNacimiento() +
                        ", Fallecimiento: " + datosAutor.fechaDeFallecimiento() + ")");

                // Buscar si el autor ya existe en la base de datos
                Optional<Autor> autorExistente = autorRepository.findByNombre(datosAutor.nombre());
                if (autorExistente.isPresent()) {
                    autor = autorExistente.get();
                } else {
                    autor = new Autor(datosAutor);
                    autorRepository.save(autor);
                }
            } else {
                System.out.println("Autor: Desconocido");
            }

            if (datos.idiomas() != null && !datos.idiomas().isEmpty()) {
                System.out.println("Idioma: " + datos.idiomas().get(0));
            }

            System.out.println("Número de descargas: " + datos.numeroDeDescargas());

            // Relacionar y guardar libro
            if (autor != null) {
                libro.setAutor(autor);
            }
            try {
                libroRepository.save(libro);
                System.out.println("Libro guardado en la base de datos con éxito!");
            } catch (Exception e) {
                System.out.println("Ese libro ya se encuentra registrado o hubo un error al guardar.");
            }

            System.out.println("----------------------------------------");
        } else {
            System.out.println("Libro no encontrado.");
        }
    }
}
