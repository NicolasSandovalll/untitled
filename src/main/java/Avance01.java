import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Avance01 {


    private static final Scanner scanner = new Scanner(System.in);
    private static final List<String> usuarios = new ArrayList<>();
    private static String matriculaActual = null;


    public static void main(String[] args) {
        iniciarPrograma();
        scanner.close();
    }

    public static void mostrarMenuInicial() {
        System.out.println("\nMenú Inicial\n");
        System.out.println("1.- Iniciar Sesión");
        System.out.println("2.- Registrarse");
        System.out.println("3.- Salir");
    }

    public static void iniciarPrograma() {
        int opcion;
        do {
            mostrarMenuInicial();
            opcion = solicitarOpcion("Ingrese su opción: ", 1, 3);
            procesarOpcionInicial(opcion);
        } while (opcion != 3);
    }

    public static int solicitarOpcion(String mensaje, int min, int max) {
        while (true) {
            try {
                System.out.print(mensaje);
                int numero = scanner.nextInt();
                scanner.nextLine(); // buena práctica
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

    public static void procesarOpcionInicial(int opcion) {
        switch (opcion) {
            case 1:
                if (iniciarSesion()) {
                    menuPrincipal();
                }
                break;
            case 2:
                if (registrarUsuario()) {
                    System.out.println("Usuario registrado exitosamente, inicie sesión.");
                }
                break;
            case 3:
                System.out.println("Saliendo del programa...");
                break;
            default:
                System.out.println("Por favor, seleccione una opción correcta."); // buena práctica
        }
    }

    public static boolean iniciarSesion() {
        System.out.println("\nIniciar Sesión");
        while (true) {
            System.out.print("Ingrese su matrícula (o ingrese 0 para volver al menú anterior): ");
            matriculaActual = limpiarMatricula(scanner.nextLine());

            if (matriculaActual.equals("0")) {
                return false;
            }

            if (usuarios.contains(matriculaActual)) {
                System.out.println("¡Bienvenido!");
                return true;
            } else {
                System.out.println("Usuario no encontrado. Por favor, intente nuevamente.");
            }
        }
    }

    public static boolean registrarUsuario() {
        System.out.println("\nRegistro de Usuario");
        String matricula = obtenerMatricula();
        if (matricula == null) {
            return false; // El usuario eligió volver al menú
        }

        usuarios.add(matricula);
        matriculaActual = matricula;
        System.out.println("Usuario registrado exitosamente.");
        return true;
    }

    private static String obtenerMatricula() {
        String matricula;

        while (true) {
            System.out.print("Ingrese su matrícula (o ingrese 0 para volver al menú anterior): ");
            matricula = limpiarMatricula(scanner.nextLine());

            if (matricula.equals("0")) {
                System.out.println("Volviendo al menú principal...");
                return null; // Indica que el usuario quiere volver
            } else if (usuarios.contains(matricula)) {
                System.out.println("El usuario ya está registrado. Por favor, inicie sesión.");
                return null; // Indica que el registro falló
            } else if (!esMatriculaValida(matricula)) {
                System.out.println("Matrícula inválida. Intente nuevamente.");
            } else {
                return matricula; // Matrícula válida
            }
        }
    }

    public static String limpiarMatricula(String matricula) {
        return matricula.replaceAll("[^\\dk]", "");
    }

    public static boolean esMatriculaValida(String matricula) {
        return matricula.matches("\\d{8}[\\dk]\\d{2}");
    }

    public static void menuPrincipal() {
        System.out.println("Aquí se supone que va el menú principal, donde se reserva, cancela logias.");
    }
}