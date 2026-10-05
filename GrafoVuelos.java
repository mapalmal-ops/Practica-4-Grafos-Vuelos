import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.Collections;

public class GrafoVuelos {

    private Map<String, List<Vuelo>> grafo;

    public GrafoVuelos() {
        grafo = new HashMap<>();
    }

    // Agregar una ciudad al grafo
    public void agregarCiudad(String ciudad) {
        grafo.putIfAbsent(ciudad, new ArrayList<>());
    }

    // Agregar un vuelo entre dos ciudades
    public void agregarVuelo(String origen, String destino, double precio) {
        agregarCiudad(origen);
        agregarCiudad(destino);

        grafo.get(origen).add(new Vuelo(destino, precio));
    }

    // Mostrar todos los vuelos registrados
    public void mostrarVuelos() {
        System.out.println("\n========== REPORTE DE VUELOS ==========");

        for (String ciudad : grafo.keySet()) {
            System.out.println("\nDesde " + ciudad + ":");

            if (grafo.get(ciudad).isEmpty()) {
                System.out.println("  No existen vuelos registrados.");
            } else {
                for (Vuelo vuelo : grafo.get(ciudad)) {
                    System.out.println("  -> " + vuelo);
                }
            }
        }
    }

    // Buscar la ruta mas economica utilizando Dijkstra
    public void buscarRutaMasBarata(String origen, String destino) {

        long inicio = System.nanoTime();

        Map<String, Double> costos = new HashMap<>();
        Map<String, String> anteriores = new HashMap<>();

        PriorityQueue<String> cola = new PriorityQueue<>(
            Comparator.comparingDouble(costos::get)
        );

        // Inicializar los costos
        for (String ciudad : grafo.keySet()) {
            costos.put(ciudad, Double.MAX_VALUE);
        }

        costos.put(origen, 0.0);
        cola.add(origen);

        while (!cola.isEmpty()) {

            String ciudadActual = cola.poll();

            if (ciudadActual.equals(destino)) {
                break;
            }

            for (Vuelo vuelo : grafo.get(ciudadActual)) {

                double nuevoCosto =
                    costos.get(ciudadActual) + vuelo.getPrecio();

                if (nuevoCosto < costos.get(vuelo.getDestino())) {

                    costos.put(vuelo.getDestino(), nuevoCosto);
                    anteriores.put(vuelo.getDestino(), ciudadActual);

                    cola.remove(vuelo.getDestino());
                    cola.add(vuelo.getDestino());
                }
            }
        }

        long fin = System.nanoTime();

        System.out.println("\n========== VUELO MAS ECONOMICO ==========");
        System.out.println("Origen: " + origen);
        System.out.println("Destino: " + destino);

        if (costos.get(destino) == Double.MAX_VALUE) {

            System.out.println("No existe una ruta disponible.");

        } else {

            List<String> ruta = new ArrayList<>();

            String ciudad = destino;

            while (ciudad != null) {
                ruta.add(ciudad);
                ciudad = anteriores.get(ciudad);
            }

            Collections.reverse(ruta);

            System.out.println("\nRuta encontrada:");
            System.out.println(String.join(" -> ", ruta));

            System.out.println("\nCosto total: $" + costos.get(destino));

            double tiempo = (fin - inicio) / 1_000_000.0;

            System.out.printf("Tiempo de ejecucion: %.4f ms%n", tiempo);
        }
    }
}