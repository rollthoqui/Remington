
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Clase Estudiante
 * Representa a un estudiante con sus calificaciones y métodos de cálculo
 */
class Estudiante {
    private String codigo;
    private String nombre;
    private ArrayList<Double> calificaciones;

    public Estudiante(String codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.calificaciones = new ArrayList<>();
    }

    public void agregarCalificacion(double nota) {
        if (nota >= 0 && nota <= 5) {
            calificaciones.add(nota);
        }
    }

    public double calcularPromedio() {
        if (calificaciones.isEmpty()) return 0.0;
        double suma = 0;
        for (int i = 0; i < calificaciones.size(); i++) {
            suma += calificaciones.get(i);
        }
        return Math.round((suma / calificaciones.size()) * 100.0) / 100.0;
    }

    public double obtenerCalificacionMaxima() {
        if (calificaciones.isEmpty()) return 0.0;
        double max = calificaciones.get(0);
        for (int i = 1; i < calificaciones.size(); i++) {
            if (calificaciones.get(i) > max) {
                max = calificaciones.get(i);
            }
        }
        return max;
    }

    public double obtenerCalificacionMinima() {
        if (calificaciones.isEmpty()) return 0.0;
        double min = calificaciones.get(0);
        for (int i = 1; i < calificaciones.size(); i++) {
            if (calificaciones.get(i) < min) {
                min = calificaciones.get(i);
            }
        }
        return min;
    }

    public boolean aprobo() {
        return calcularPromedio() >= 3.0;
    }

    public String obtenerNivelRendimiento() {
        double promedio = calcularPromedio();
        if (promedio >= 4.5) return "Excelente";
        if (promedio >= 3.5) return "Bueno";
        if (promedio >= 3.0) return "Aceptable";
        return "Insuficiente";
    }

    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
    
    public String getCalificacionesString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < calificaciones.size(); i++) {
            sb.append(calificaciones.get(i));
            if (i < calificaciones.size() - 1) sb.append(", ");
        }
        return sb.toString();
    }
}

/**
 * Clase GestorCurso
 * Gestiona el conjunto de estudiantes y genera estadísticas del curso
 */
class GestorCurso {
    private String nombreCurso;
    private ArrayList<Estudiante> estudiantes;

    public GestorCurso(String nombreCurso) {
        this.nombreCurso = nombreCurso;
        this.estudiantes = new ArrayList<>();
    }

    public void agregarEstudiante(Estudiante estudiante) {
        if (estudiante != null) {
            estudiantes.add(estudiante);
        }
    }

    public void mostrarListadoCompleto() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("LISTADO COMPLETO - " + nombreCurso);
        System.out.println("=".repeat(80));
        
        for (int i = 0; i < estudiantes.size(); i++) {
            Estudiante est = estudiantes.get(i);
            System.out.println("\nEstudiante #" + (i + 1));
            System.out.println("  Código: " + est.getCodigo());
            System.out.println("  Nombre: " + est.getNombre());
            System.out.println("  Calificaciones: [" + est.getCalificacionesString() + "]");
            System.out.println("  Promedio: " + est.calcularPromedio());
            System.out.println("  Nota Máxima: " + est.obtenerCalificacionMaxima());
            System.out.println("  Nota Mínima: " + est.obtenerCalificacionMinima());
            System.out.println("  Estado: " + (est.aprobo() ? "APROBADO" : "REPROBADO"));
            System.out.println("  Nivel: " + est.obtenerNivelRendimiento());
        }
    }

    public double calcularPromedioGeneral() {
        if (estudiantes.isEmpty()) return 0.0;
        double sumaPromedios = 0;
        for (int i = 0; i < estudiantes.size(); i++) {
            sumaPromedios += estudiantes.get(i).calcularPromedio();
        }
        return Math.round((sumaPromedios / estudiantes.size()) * 100.0) / 100.0;
    }

    public Estudiante identificarMejorEstudiante() {
        if (estudiantes.isEmpty()) return null;
        Estudiante mejorEstudiante = estudiantes.get(0);
        double mejorPromedio = mejorEstudiante.calcularPromedio();
        
        for (int i = 1; i < estudiantes.size(); i++) {
            double promedioActual = estudiantes.get(i).calcularPromedio();
            if (promedioActual > mejorPromedio) {
                mejorPromedio = promedioActual;
                mejorEstudiante = estudiantes.get(i);
            }
        }
        return mejorEstudiante;
    }

    class EstadisticasAprobacion {
        int aprobados;
        int reprobados;
        EstadisticasAprobacion(int aprobados, int reprobados) {
            this.aprobados = aprobados;
            this.reprobados = reprobados;
        }
    }

    public EstadisticasAprobacion contarAprobadosReprobados() {
        int aprobados = 0;
        int reprobados = 0;
        for (int i = 0; i < estudiantes.size(); i++) {
            if (estudiantes.get(i).aprobo()) {
                aprobados++;
            } else {
                reprobados++;
            }
        }
        return new EstadisticasAprobacion(aprobados, reprobados);
    }

    class DistribucionCalificaciones {
        int excelente, bueno, aceptable, insuficiente;
        DistribucionCalificaciones() {
            this.excelente = 0;
            this.bueno = 0;
            this.aceptable = 0;
            this.insuficiente = 0;
        }
    }

    public DistribucionCalificaciones generarDistribucionCalificaciones() {
        DistribucionCalificaciones dist = new DistribucionCalificaciones();
        for (int i = 0; i < estudiantes.size(); i++) {
            String nivel = estudiantes.get(i).obtenerNivelRendimiento();
            switch (nivel) {
                case "Excelente": dist.excelente++; break;
                case "Bueno": dist.bueno++; break;
                case "Aceptable": dist.aceptable++; break;
                case "Insuficiente": dist.insuficiente++; break;
            }
        }
        return dist;
    }

    public void generarReporteEstadistico() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("REPORTE ESTADÍSTICO - " + nombreCurso);
        System.out.println("=".repeat(80));
        
        System.out.println("\n📋 1. ESTUDIANTES Y PROMEDIOS:");
        System.out.println("-".repeat(80));
        for (int i = 0; i < estudiantes.size(); i++) {
            Estudiante est = estudiantes.get(i);
            String estado = est.aprobo() ? "✓ APROBADO" : "✗ REPROBADO";
            System.out.printf("   %-25s | Promedio: %.2f | %s%n", 
                est.getNombre(), est.calcularPromedio(), estado);
        }
        
        System.out.println("\n📊 2. PROMEDIO GENERAL DEL CURSO:");
        System.out.println("-".repeat(80));
        System.out.printf("   %.2f%n", calcularPromedioGeneral());
        
        System.out.println("\n🏆 3. ESTUDIANTE DESTACADO:");
        System.out.println("-".repeat(80));
        Estudiante mejor = identificarMejorEstudiante();
        if (mejor != null) {
            System.out.println("   " + mejor.getNombre() + " (Código: " + mejor.getCodigo() + ")");
            System.out.printf("   Promedio: %.2f%n", mejor.calcularPromedio());
        }
        
        System.out.println("\n📈 4. ESTADÍSTICAS DE APROBACIÓN:");
        System.out.println("-".repeat(80));
        EstadisticasAprobacion stats = contarAprobadosReprobados();
        int total = estudiantes.size();
        double porcentajeAprobados = total > 0 ? (stats.aprobados * 100.0 / total) : 0;
        double porcentajeReprobados = total > 0 ? (stats.reprobados * 100.0 / total) : 0;
        System.out.printf("   Aprobados: %d (%.2f%%)%n", stats.aprobados, porcentajeAprobados);
        System.out.printf("   Reprobados: %d (%.2f%%)%n", stats.reprobados, porcentajeReprobados);
        System.out.println("   Total: " + total);
        
        System.out.println("\n📉 5. DISTRIBUCIÓN DE CALIFICACIONES:");
        System.out.println("-".repeat(80));
        DistribucionCalificaciones dist = generarDistribucionCalificaciones();
        System.out.println("   Excelente (≥4.5):    " + dist.excelente + " estudiantes");
        System.out.println("   Bueno (≥3.5):        " + dist.bueno + " estudiantes");
        System.out.println("   Aceptable (≥3.0):    " + dist.aceptable + " estudiantes");
        System.out.println("   Insuficiente (<3.0): " + dist.insuficiente + " estudiantes");
        System.out.println("\n" + "=".repeat(80) + "\n");
    }

    public void ordenarEstudiantesPorPromedio() {
        int n = estudiantes.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                double promedioActual = estudiantes.get(j).calcularPromedio();
                double promedioSiguiente = estudiantes.get(j + 1).calcularPromedio();
                if (promedioActual < promedioSiguiente) {
                    Estudiante temp = estudiantes.get(j);
                    estudiantes.set(j, estudiantes.get(j + 1));
                    estudiantes.set(j + 1, temp);
                }
            }
        }
        System.out.println("\n✓ Estudiantes ordenados por promedio (mayor a menor)\n");
    }
}

/**
 * Clase Principal
 */
public class SistemaGestionCalificaciones {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("\n🎓 SISTEMA DE GESTIÓN DE CALIFICACIONES UNIVERSITARIAS");
        System.out.println("Universidad Nacional");
        System.out.println("=".repeat(80) + "\n");
        
        // Solicitar nombre del curso
        System.out.print("Ingrese el nombre del curso: ");
        String nombreCurso = scanner.nextLine();
        GestorCurso curso = new GestorCurso(nombreCurso);
        
        // Solicitar cantidad de estudiantes
        System.out.print("¿Cuántos estudiantes desea registrar?: ");
        int cantidadEstudiantes = scanner.nextInt();
        scanner.nextLine();
        
        // Ingresar datos de cada estudiante
        for (int i = 0; i < cantidadEstudiantes; i++) {
            System.out.println("\n" + "-".repeat(60));
            System.out.println("ESTUDIANTE #" + (i + 1));
            System.out.println("-".repeat(60));
            
            // Código
            System.out.print("Código: ");
            String codigo = scanner.nextLine();
            
            // Nombre
            System.out.print("Nombre completo: ");
            String nombre = scanner.nextLine();
            
            Estudiante estudiante = new Estudiante(codigo, nombre);
            
            // Cantidad de calificaciones
            System.out.print("¿Cuántas calificaciones?: ");
            int numCalificaciones = scanner.nextInt();
            scanner.nextLine();
            
            // Ingresar calificaciones
            System.out.println("\nIngrese las calificaciones (0.0 - 5.0):");
            for (int j = 0; j < numCalificaciones; j++) {
                System.out.print("  Calificación " + (j + 1) + ": ");
                double nota = scanner.nextDouble();
                scanner.nextLine();
                estudiante.agregarCalificacion(nota);
            }
            
            curso.agregarEstudiante(estudiante);
            System.out.println("✓ Estudiante agregado (Promedio: " + 
                estudiante.calcularPromedio() + ")");
        }
        
        // Mostrar resultados
        curso.mostrarListadoCompleto();
        curso.generarReporteEstadistico();
        curso.ordenarEstudiantesPorPromedio();
        curso.mostrarListadoCompleto();
        
        scanner.close();
        System.out.println("\n¡Gracias por usar el sistema!\n");
    }
}