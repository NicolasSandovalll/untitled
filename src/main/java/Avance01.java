import java.util.*;

public class Avance01 {

    private static final Scanner scanner = new Scanner(System.in);
    private static final Map<Integer, List<Integer>> logiasPorCapacidad = new HashMap<>();
    private static final Map<String, String> reservas = new HashMap<>();  // Almacena "numeroLogia:capacidad"
    private static String matriculaActual = null;
    private static List<String> usuarios = new ArrayList<>();

    private static final int CAPACIDAD_7 = 7;
    private static final int CAPACIDAD_5 = 5;
    private static final int CAPACIDAD_3 = 3;

    static {
        logiasPorCapacidad.put(CAPACIDAD_7, new ArrayList<>());
        logiasPorCapacidad.put(CAPACIDAD_5, new ArrayList<>());
        logiasPorCapacidad.put(CAPACIDAD_3, new ArrayList<>());
    }

    public static void mostrarDisponibilidadLogias(int capacidad) {
        List<Integer> logias = logiasPorCapacidad.get(capacidad);
        System.out.print("Logias de " + capacidad + " personas disponibles: ");
        logias.stream()
                .filter(logia -> !isLogiaReservada(logia, capacidad))
                .forEach(logia -> System.out.print("Logia " + logia + " "));
        System.out.println();
    }

    private static boolean isLogiaReservada(int logia, int capacidad) {
        return reservas.values().stream().anyMatch(reserva -> {
            String[] partes = reserva.split(":");
            return Integer.parseInt(partes[0]) == logia && Integer.parseInt(partes[1]) == capacidad;
        });
    }

    public static void reservarLogia() {
        if (reservas.containsKey(matriculaActual)) {
            System.out.println("Ya tienes una logia reservada.");
            return;
        }

        int capacidadSeleccionada = seleccionarCapacidadLogia();
        if (capacidadSeleccionada == -1) return;

        mostrarDisponibilidadLogias(capacidadSeleccionada);

        int numeroLogia = seleccionarNumeroDeLogia(capacidadSeleccionada);
        if (numeroLogia == -1) return;

        if (solicitarIntegrantes(numeroLogia, capacidadSeleccionada)) {
            completarReserva(numeroLogia, capacidadSeleccionada);
        } else {
            System.out.println("La reserva fue cancelada.");
        }
    }

    public static void cancelarReserva() {
        if (!reservas.containsKey(matriculaActual)) {
            System.out.println("No tienes reservas para cancelar.");
        } else {
            int numeroLogia = Integer.parseInt(reservas.remove(matriculaActual).split(":")[0]);
            System.out.println("Reserva de la logia " + numeroLogia + " cancelada con éxito.");
        }
    }

    public static int seleccionarCapacidadLogia() {
        System.out.println("\nSeleccione el tamaño de la logia a reservar:");
        System.out.println("1.- Logia para 7 personas");
        System.out.println("2.- Logia para 5 personas");
        System.out.println("3.- Logia para 3 personas");
        System.out.println("4.- Cancelar");

        int opcion = solicitarOpcion("Ingrese una opción: ", 1, 4);
        return switch (opcion) {
            case 1 -> CAPACIDAD_7;
            case 2 -> CAPACIDAD_5;
            case 3 -> CAPACIDAD_3;
            case 4 -> {
                System.out.println("Cancelando la reserva y volviendo al menú anterior...");
                yield -1;
            }
            default -> -1;
        };
    }

    public static int seleccionarNumeroDeLogia(int capacidad) {
        List<Integer> logias = logiasPorCapacidad.get(capacidad);
        int numeroLogia;
        do {
            numeroLogia = solicitarNumeroLogia();
            if (!logias.contains(numeroLogia) || isLogiaReservada(numeroLogia, capacidad)) {
                System.out.println("La logia seleccionada no está disponible. Intente de nuevo.");
                numeroLogia = -1; // Reset to prompt again
            }
        } while (numeroLogia == -1);
        return numeroLogia;
    }

    public static boolean solicitarIntegrantes(int numeroLogia, int capacidad) {
        List<String> integrantes = new ArrayList<>();
        for (int i = 1; i < capacidad; i++) {
            String matricula;
            while (true) {
                System.out.print("Matrícula del compañero " + i + " (ingrese 0 para cancelar): ");
                matricula = limpiarMatricula(scanner.nextLine());
                if (matricula.equals("0")) {
                    System.out.println("Cancelando la reserva de la logia " + numeroLogia + ".");
                    return false;
                } else if (!validarMatriculaCompanero(matricula, integrantes)) {
                    continue;
                }
                integrantes.add(matricula);
                break;
            }
        }
        return true;
    }

    public static void completarReserva(int numeroLogia, int capacidad) {
        reservas.put(matriculaActual, numeroLogia + ":" + capacidad);
        System.out.println("Logia " + numeroLogia + " reservada con éxito.");
    }

    public static int solicitarNumeroLogia() {
        while (true) {
            try {
                System.out.print("Ingrese el número de logia: ");
                int numero = scanner.nextInt();
                scanner.nextLine();
                return numero;
            } catch (InputMismatchException e) {
                System.out.println("Por favor, ingrese un número entero válido.");
                scanner.nextLine();
            }
        }
    }

    public static int solicitarOpcion(String mensaje, int min, int max) {
        while (true) {
            try {
                System.out.print(mensaje);
                int numero = scanner.nextInt();
                scanner.nextLine();
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

    public static boolean validarMatriculaCompanero(String matricula, List<String> integrantes) {
        if (matricula.equals(matriculaActual)) {
            System.out.println("No puedes ingresar tu propia matrícula.");
            return false;
        } else if (!usuarios.contains(matricula)) {
            System.out.println("La matrícula no está registrada.");
            return false;
        } else if (integrantes.contains(matricula)) {
            System.out.println("La matrícula ya fue ingresada.");
            return false;
        }
        return true;
    }

    public static String limpiarMatricula(String matricula) {
        return matricula.replaceAll("[^\\dk]", "");
    }

    public static void consultarDisponibilidad() {
        mostrarDisponibilidadLogias(CAPACIDAD_7);
        mostrarDisponibilidadLogias(CAPACIDAD_5);
        mostrarDisponibilidadLogias(CAPACIDAD_3);
    }
}
