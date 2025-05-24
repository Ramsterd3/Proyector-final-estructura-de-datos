package servicios;

import modelo.ArbolBinarioBusqueda;
import modelo.GrafoNoDirigido;
import modelo.ListaEnlazada;
import modelo.Lector;
import modelo.Libro;
import modelo.Valoracion;

import java.util.HashMap;
import java.util.Map;

public class GestorUsuarios {
    private ArbolBinarioBusqueda<Lector> lectores;
    private GrafoNoDirigido<Lector> grafoAfinidad;

    public GestorUsuarios() {
        this.lectores = new ArbolBinarioBusqueda<>();
        this.grafoAfinidad = new GrafoNoDirigido<>();
    }

    public void agregarLector(Lector lector) {
        lectores.insertar(lector);
        grafoAfinidad.agregarVertice(lector);
    }

    public void eliminarLector(Lector lector) {
        lectores.eliminar(lector);
        grafoAfinidad.eliminarVertice(lector);
    }

    public Lector buscarLectorPorCorreo(String correo) {
        final Lector[] resultado = {null};

        lectores.inorden(new ArbolBinarioBusqueda.NodoVisitante<Lector>() {
            @Override
            public void visitar(Lector lector) {
                if (lector.getCorreo().equals(correo)) {
                    resultado[0] = lector;
                }
            }
        });

        return resultado[0];
    }

    public void actualizarGrafoAfinidad() {
        // Limpiar todas las aristas existentes
        for (Lector lector : grafoAfinidad.obtenerVertices()) {
            for (Lector otroLector : grafoAfinidad.obtenerAdyacentes(lector)) {
                grafoAfinidad.eliminarArista(lector, otroLector);
            }
        }

        // Obtener todos los lectores
        final ListaEnlazada<Lector> todosLosLectores = new ListaEnlazada<>();
        lectores.inorden(new ArbolBinarioBusqueda.NodoVisitante<Lector>() {
            @Override
            public void visitar(Lector lector) {
                todosLosLectores.agregar(lector);
            }
        });

        // Comparar cada par de lectores
        for (int i = 0; i < todosLosLectores.tamaño(); i++) {
            Lector lector1 = todosLosLectores.obtener(i);

            for (int j = i + 1; j < todosLosLectores.tamaño(); j++) {
                Lector lector2 = todosLosLectores.obtener(j);

                int similitud = calcularSimilitud(lector1, lector2);

                // Si tienen al menos 3 libros en común con calificaciones similares
                if (similitud >= 3) {
                    grafoAfinidad.agregarArista(lector1, lector2, similitud);

                    // Actualizar la similitud en los objetos Lector
                    lector1.actualizarSimilitudConLector(lector2, similitud);
                    lector2.actualizarSimilitudConLector(lector1, similitud);
                }
            }
        }
    }

    private int calcularSimilitud(Lector lector1, Lector lector2) {
        Map<Libro, Integer> valoracionesLector1 = new HashMap<>();

        // Obtener valoraciones del primer lector
        for (int i = 0; i < lector1.getValoracionesRealizadas().tamaño(); i++) {
            Valoracion valoracion = lector1.getValoracionesRealizadas().obtener(i);
            valoracionesLector1.put(valoracion.getLibro(), valoracion.getCalificacion());
        }

        int librosEnComun = 0;

        // Comparar con las valoraciones del segundo lector
        for (int i = 0; i < lector2.getValoracionesRealizadas().tamaño(); i++) {
            Valoracion valoracion = lector2.getValoracionesRealizadas().obtener(i);
            Libro libro = valoracion.getLibro();

            if (valoracionesLector1.containsKey(libro)) {
                int calificacion1 = valoracionesLector1.get(libro);
                int calificacion2 = valoracion.getCalificacion();

                // Si la diferencia de calificación es 1 o menos, consideramos que tienen gustos similares
                if (Math.abs(calificacion1 - calificacion2) <= 1) {
                    librosEnComun++;
                }
            }
        }

        return librosEnComun;
    }

    public ListaEnlazada<Lector> sugerirAmigos(Lector lector, int maxSugerencias) {
        ListaEnlazada<Lector> sugerencias = new ListaEnlazada<>();

        // Obtener amigos directos
        ListaEnlazada<Lector> amigosDirectos = new ListaEnlazada<>();
        for (Lector amigoDirecto : grafoAfinidad.obtenerAdyacentes(lector)) {
            amigosDirectos.agregar(amigoDirecto);
        }

        // Obtener amigos de amigos
        for (int i = 0; i < amigosDirectos.tamaño(); i++) {
            Lector amigoDirecto = amigosDirectos.obtener(i);

            for (Lector amigoDeAmigo : grafoAfinidad.obtenerAdyacentes(amigoDirecto)) {
                // Si no es el lector original y no es un amigo directo
                boolean esAmigoDirecto = false;
                for (int j = 0; j < amigosDirectos.tamaño(); j++) {
                    if (amigosDirectos.obtener(j).equals(amigoDeAmigo)) {
                        esAmigoDirecto = true;
                        break;
                    }
                }

                if (!amigoDeAmigo.equals(lector) && !esAmigoDirecto) {
                    // Verificar si ya está en las sugerencias
                    boolean yaExiste = false;
                    for (int j = 0; j < sugerencias.tamaño(); j++) {
                        if (sugerencias.obtener(j).equals(amigoDeAmigo)) {
                            yaExiste = true;
                            break;
                        }
                    }

                    if (!yaExiste) {
                        sugerencias.agregar(amigoDeAmigo);
                        if (sugerencias.tamaño() >= maxSugerencias) {
                            return sugerencias;
                        }
                    }
                }
            }
        }

        return sugerencias;
    }

    public ListaEnlazada<Lector> buscarCaminoEntreLectores(Lector origen, Lector destino) {
        ListaEnlazada<Lector> camino = new ListaEnlazada<>();

        for (Lector lector : grafoAfinidad.buscarCaminoMasCorto(origen, destino)) {
            camino.agregar(lector);
        }

        return camino;
    }

    public GrafoNoDirigido<Lector> getGrafoAfinidad() {
        return grafoAfinidad;
    }

    public ListaEnlazada<Lector> obtenerTodosLosLectores() {
        final ListaEnlazada<Lector> resultado = new ListaEnlazada<>();

        lectores.inorden(new ArbolBinarioBusqueda.NodoVisitante<Lector>() {
            @Override
            public void visitar(Lector lector) {
                resultado.agregar(lector);
            }
        });

        return resultado;
    }

    public boolean existeLector(String correo) {
        return buscarLectorPorCorreo(correo) != null;
    }

    public void modificarLector(Lector lector, String nuevoNombre, String nuevoApellido, String nuevoCorreo, String nuevaContraseña) {
        // Primero eliminamos el lector actual
        eliminarLector(lector);

        // Actualizamos sus datos
        lector.setNombre(nuevoNombre);
        lector.setApellido(nuevoApellido);
        lector.setCorreo(nuevoCorreo);
        lector.setContraseña(nuevaContraseña);

        // Lo volvemos a insertar
        agregarLector(lector);
    }

    public ListaEnlazada<Lector> buscarLectoresPorNombre(String textoBusqueda) {
        final ListaEnlazada<Lector> resultado = new ListaEnlazada<>();

        lectores.inorden(new ArbolBinarioBusqueda.NodoVisitante<Lector>() {
            @Override
            public void visitar(Lector lector) {
                String nombreCompleto = lector.getNombre() + " " + lector.getApellido();
                if (nombreCompleto.toLowerCase().contains(textoBusqueda.toLowerCase())) {
                    resultado.agregar(lector);
                }
            }
        });

        return resultado;
    }
}
