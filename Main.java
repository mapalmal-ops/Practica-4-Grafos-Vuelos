import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("====================================");
        System.out.println("   SISTEMA DE VUELOS ECONOMICOS");
        System.out.println("====================================");

        // Crear el grafo de vuelos
        GrafoVuelos grafo = new GrafoVuelos();

        // Registrar vuelos y sus precios
        grafo.agregarVuelo("Quito", "Guayaquil", 80);
        grafo.agregarVuelo("Quito", "Cuenca", 65);
        grafo.agregarVuelo("Quito", "Manta", 70);

        grafo.agregarVuelo("Guayaquil", "Cuenca", 50);
        grafo.agregarVuelo("Guayaquil", "Loja", 75);

        grafo.agregarVuelo("Cuenca", "Loja", 45);
        grafo.agregarVuelo("Cuenca", "Manta", 60);

        grafo.agregarVuelo("Manta", "Loja", 55);

        // Mostrar todos los vuelos registrados
        grafo.mostrarVuelos();

        // Solicitar origen y destino al usuario
        System.out.println("\n========== BUSQUEDA DE VUELO ==========");

        System.out.print("Ingrese la ciudad de origen: ");
        String origen = scanner.nextLine();

        System.out.print("Ingrese la ciudad de destino: ");
        String destino = scanner.nextLine();

        // Buscar la ruta mas economica
        grafo.buscarRutaMasBarata(origen, destino);

        scanner.close();
    }
}