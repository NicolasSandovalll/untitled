import java.util.InputMismatchException;
import java.util.Scanner;

public class Avance01 {

    private static final Scanner scanner = new Scanner(System.in);

    public static void menuPrincipal() {
        int opcion;
        do {
            mostrarMenuPrincipal();
            opcion = solicitarOpcion("Ingrese una opción:", 1, 4);
            procesarOpcionPrincipal(opcion);
        } while (opcion != 4);
    }

    public static void mostrarMenuPrincipal() {
        System.out.println("\nMenú Principal\n");
        System.out.println("1.- Reservar Logia");
        System.out.println("2.- Consultar Disponibilidad de una Logia");
        System.out.println("3.- Cancelar Reserva de una Logia");
        System.out.println("4.- Salir");
    }

    public static int solicitarOpcion(String mensaje, int min, int max) {
        while (true) {
            try {
                System.out.print(mensaje);
                int numero = scanner.nextInt();
                scanner.nextLine(); // buena práctica según chatGPT , pero si se saca, funcionará de igual manera.
                if (numero >= min && numero <= max) {
                    return numero;
                } else {
                    System.out.println("Por favor, ingrese un número entre " + min + " y " + max + ".");
                }
            } catch (InputMismatchException e) {
                System.out.println("Por favor, ingrese un número entero válido.");
                scanner.nextLine();
            }
        }
    }

    public static void procesarOpcionPrincipal(int opcion) {
        switch (opcion) {
            case 1:
                reservarLogia();
                break;
            case 2:
                consultarDisponibilidad();
                break;
            case 3:
                cancelarReserva();
                break;
            case 4:
                System.out.println("Saliendo del programa...");
                break;
            default:
                System.out.println("opción inválida."); // buena práctica según chaGPT , si se saca funcionará de igual manera , ya que nunca se ejecutará.
        }
    }


    public static void reservarLogia() {
        System.out.println("Aquí va la opción (reservarLogia)");
        // Implementar lógica de reserva aquí
    }

    public static void consultarDisponibilidad() {
        System.out.println("Aquí va la opción (consultarDisponibilidad)");
        // Implementar lógica de consulta aquí
    }

    public static void cancelarReserva() {
        System.out.println("Aquí va la opción (cancelarReserva)");
        // Implementar lógica de cancelación aquí
    }
}
