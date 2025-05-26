package utilidades;

import modelo.*;
import servicios.*;
import modelo.ListaEnlazada;

import java.util.Random;

public class CargadorDatosIniciales {

    public static void cargarDatos(ServicioAutenticacion servicioAutenticacion,
                                   GestorLibros gestorLibros,
                                   GestorUsuarios gestorUsuarios) {

        // Cargar administradores
        cargarAdministradores(servicioAutenticacion);

        // Cargar lectores
        cargarLectores(servicioAutenticacion, gestorUsuarios);

        // Cargar libros
        cargarLibros(gestorLibros);

        // Cargar préstamos y valoraciones
      //  cargarPrestamosYValoraciones(servicioAutenticacion, gestorLibros, gestorUsuarios);

        // Actualizar grafo de afinidad
       // gestorUsuarios.actualizarGrafoAfinidad();
    }

    private static void cargarAdministradores(ServicioAutenticacion servicioAutenticacion) {
        Administrador admin1 = new Administrador("Admin", "Principal", "admin@biblioteca.com", "admin123",Tipo.ADMIN);
        Administrador admin2 = new Administrador("Juan", "Pérez", "juan.perez@biblioteca.com", "juan123",Tipo.ADMIN);

        servicioAutenticacion.registrarUsuario(admin1);
        servicioAutenticacion.registrarUsuario(admin2);

        System.out.println("Administradores cargados: 2");
    }

    private static void cargarLectores(ServicioAutenticacion servicioAutenticacion, GestorUsuarios gestorUsuarios) {
        String[] nombres = {"Ana", "Carlos", "María", "Pedro", "Laura", "Miguel", "Sofía", "David", "Elena", "Javier"};
        String[] apellidos = {"García", "Rodríguez", "Martínez", "López", "González", "Fernández", "Sánchez", "Pérez", "Gómez", "Díaz"};

        for (int i = 0; i < nombres.length; i++) {
            String nombre = nombres[i];
            String apellido = apellidos[i];
            String correo = nombre.toLowerCase() + "." + apellido.toLowerCase() + "@mail.com";
            String contraseña = nombre.toLowerCase() + "123";
            Tipo tipo=Tipo.ADMIN;

            Lector lector = new Lector(nombre, apellido, correo, contraseña,tipo);
            servicioAutenticacion.registrarUsuario(lector);
            gestorUsuarios.agregarLector(lector);
        }

        System.out.println("Lectores cargados: " + nombres.length);
    }

    private static void cargarLibros(GestorLibros gestorLibros) {
        // Literatura clásica
        gestorLibros.agregarLibro(new Libro("Don Quijote de la Mancha", "Miguel de Cervantes", 1605, "Literatura Clásica"));
        gestorLibros.agregarLibro(new Libro("Cien años de soledad", "Gabriel García Márquez", 1967, "Literatura Clásica"));
        gestorLibros.agregarLibro(new Libro("1984", "George Orwell", 1949, "Literatura Clásica"));
        gestorLibros.agregarLibro(new Libro("Orgullo y prejuicio", "Jane Austen", 1813, "Literatura Clásica"));
        gestorLibros.agregarLibro(new Libro("Crimen y castigo", "Fiódor Dostoyevski", 1866, "Literatura Clásica"));

        // Ciencia ficción
        gestorLibros.agregarLibro(new Libro("Dune", "Frank Herbert", 1965, "Ciencia Ficción"));
        gestorLibros.agregarLibro(new Libro("Neuromante", "William Gibson", 1984, "Ciencia Ficción"));
        gestorLibros.agregarLibro(new Libro("El juego de Ender", "Orson Scott Card", 1985, "Ciencia Ficción"));
        gestorLibros.agregarLibro(new Libro("Fundación", "Isaac Asimov", 1951, "Ciencia Ficción"));
        gestorLibros.agregarLibro(new Libro("¿Sueñan los androides con ovejas eléctricas?", "Philip K. Dick", 1968, "Ciencia Ficción"));

        // Fantasía
        gestorLibros.agregarLibro(new Libro("El Señor de los Anillos", "J.R.R. Tolkien", 1954, "Fantasía"));
        gestorLibros.agregarLibro(new Libro("Harry Potter y la piedra filosofal", "J.K. Rowling", 1997, "Fantasía"));
        gestorLibros.agregarLibro(new Libro("Canción de hielo y fuego", "George R.R. Martin", 1996, "Fantasía"));
        gestorLibros.agregarLibro(new Libro("El nombre del viento", "Patrick Rothfuss", 2007, "Fantasía"));
        gestorLibros.agregarLibro(new Libro("El último deseo", "Andrzej Sapkowski", 1993, "Fantasía"));

        // Misterio
        gestorLibros.agregarLibro(new Libro("Asesinato en el Orient Express", "Agatha Christie", 1934, "Misterio"));
        gestorLibros.agregarLibro(new Libro("El código Da Vinci", "Dan Brown", 2003, "Misterio"));
        gestorLibros.agregarLibro(new Libro("Los crímenes de la calle Morgue", "Edgar Allan Poe", 1841, "Misterio"));
        gestorLibros.agregarLibro(new Libro("La chica del tren", "Paula Hawkins", 2015, "Misterio"));
        gestorLibros.agregarLibro(new Libro("El silencio de los corderos", "Thomas Harris", 1988, "Misterio"));

        // Programación
        gestorLibros.agregarLibro(new Libro("Clean Code", "Robert C. Martin", 2008, "Programación"));
        gestorLibros.agregarLibro(new Libro("Introduction to Algorithms", "Thomas H. Cormen", 1990, "Programación"));
        gestorLibros.agregarLibro(new Libro("Design Patterns", "Erich Gamma", 1994, "Programación"));
        gestorLibros.agregarLibro(new Libro("The Pragmatic Programmer", "Andrew Hunt", 1999, "Programación"));
        gestorLibros.agregarLibro(new Libro("Effective Java", "Joshua Bloch", 2001, "Programación"));

        System.out.println("Libros cargados: 25");
    }


}
