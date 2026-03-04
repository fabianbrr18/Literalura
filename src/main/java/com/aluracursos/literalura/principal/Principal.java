package com.aluracursos.literalura.principal;

import com.aluracursos.literalura.model.DatosLibro;
import com.aluracursos.literalura.model.DatosResultados;
import com.aluracursos.literalura.service.ConsumoAPI;
import com.aluracursos.literalura.service.ConvierteDatos;

import java.util.Optional;
import java.util.Scanner;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();
    private final String URL_BASE = "https://gutendex.com/books/";

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
                        System.out.println("Opción en construcción: Listar libros registrados");
                        break;
                    case 3:
                        System.out.println("Opción en construcción: Listar autores registrados");
                        break;
                    case 4:
                        System.out.println("Opción en construcción: Listar autores vivos");
                        break;
                    case 5:
                        System.out.println("Opción en construcción: Listar libros por idioma");
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

            if (datos.autor() != null && !datos.autor().isEmpty()) {
                var autor = datos.autor().get(0);
                System.out.println("Autor: " + autor.nombre() +
                        " (Nacimiento: " + autor.fechaDeNacimiento() +
                        ", Fallecimiento: " + autor.fechaDeFallecimiento() + ")");
            } else {
                System.out.println("Autor: Desconocido");
            }

            if (datos.idiomas() != null && !datos.idiomas().isEmpty()) {
                System.out.println("Idioma: " + datos.idiomas().get(0));
            }

            System.out.println("Número de descargas: " + datos.numeroDeDescargas());
            System.out.println("----------------------------------------");
        } else {
            System.out.println("Libro no encontrado.");
        }
    }
}
