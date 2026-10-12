import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {

    private static final String ARCHIVO_ORIGINAL = "datos/DonQuijote.txt";

    private static final String ARCHIVO_AMPLIADO = "datos/DonQuijote_100x.txt";

    private static final int REPETICIONES = 5;

    public static void main(String[] args) {

        try {
            System.out.println(" PROCESAMIENTO DE TEXTO CON THREADS");

            // 1. Cargar el documento original en memoria.
            String textoOriginal =
                    Files.readString(
                            Paths.get(ARCHIVO_ORIGINAL)
                    );
            System.out.println("\nArchivo original cargado.");
            System.out.println(
                    "Caracteres: " + textoOriginal.length()
            );

            // 2. Procesamiento secuencial del documento original.
            Resultado resultadoSecuencial =
                    Secuencial.ejecutar(textoOriginal);

            System.out.println("\nRESULTADO SECUENCIAL");
            System.out.println(resultadoSecuencial);

            // 3. Generar el documento ampliado 100 veces.
            String textoAmpliado =
                    generarTextoAmpliado(textoOriginal, 100);

            Files.writeString(
                    Paths.get(ARCHIVO_AMPLIADO),
                    textoAmpliado
            );

            System.out.println("\nArchivo ampliado generado.");
            System.out.println(
                    "Caracteres: " + textoAmpliado.length()
            );

            // 4. Medir el rendimiento del documento original.
            System.out.println(" PRUEBAS DE RENDIMIENTO");

            ejecutarPruebas(
                    "ORIGINAL",
                    textoOriginal
            );

            // 5. Medir el rendimiento del documento ampliado.
            ejecutarPruebas(
                    "AMPLIADO 100x",
                    textoAmpliado
            );

            // 6. Comprobar que los resultados coincidan.
            System.out.println(" VERIFICACIÓN DE RESULTADOS");

            comprobarResultados(
                    "DOCUMENTO ORIGINAL",
                    textoOriginal
            );

            comprobarResultados(
                    "DOCUMENTO AMPLIADO 100x",
                    textoAmpliado
            );
            System.out.println(" VERIFICACIÓN FINALIZADA");

        } catch (IOException e) {

            System.err.println(
                    "Error al leer o escribir archivos: "
                    + e.getMessage()
            );

        } catch (RuntimeException e) {

            System.err.println(
                    "\nERROR DURANTE LA EJECUCIÓN: "
                    + e.getMessage()
            );
        }
    }

    // Ejecuta las pruebas de rendimiento para un documento.
    private static void ejecutarPruebas(
            String nombre,
            String texto) {

        System.out.println("\n\nDATOS: " + nombre);

        // Obtener los resultados de referencia.
        Resultado referencia = Secuencial.ejecutar(texto);

        // Medir el procesamiento secuencial.
        long tiempoSecuencial = medirSecuencial(texto);

        System.out.printf("1 hilo (secuencial): %.4f ms%n", tiempoSecuencial / 1_000_000.0
        );

        // Medir el procesamiento con dos hilos.
        double promedio2 =
                medirParalelo(texto, 2, referencia);

        System.out.printf("2 hilos: %.4f ms%n", promedio2 / 1_000_000.0
        );

        // Medir el procesamiento con cuatro hilos.
        double promedio4 = medirParalelo(texto, 4, referencia);

        System.out.printf("4 hilos: %.4f ms%n", promedio4 / 1_000_000.0
        );

        // Calcular la aceleración.
        double speedup2 = tiempoSecuencial / promedio2;

        double speedup4 = tiempoSecuencial / promedio4;

        System.out.printf("Speedup 2 hilos: %.4f%n", speedup2
        );

        System.out.printf("Speedup 4 hilos: %.4f%n", speedup4
        );
    }

    // Mide el tiempo promedio del procesamiento secuencial.
    private static long medirSecuencial(String texto) {

        // Calentamiento de la JVM.
        for (int i = 0; i < 2; i++) {
            Secuencial.ejecutar(texto);
        }

        long suma = 0;

        for (int i = 0; i < REPETICIONES; i++) {

            long inicio = System.nanoTime();

            Resultado resultado =
                    Secuencial.ejecutar(texto);

            long fin = System.nanoTime();

            verificarResultadoNoNulo(resultado);

            suma += fin - inicio;
        }

        return suma / REPETICIONES;
    }

    // Mide el tiempo promedio del procesamiento paralelo.
    private static double medirParalelo(
            String texto,
            int numeroHilos,
            Resultado referencia) {

        // Calentamiento de la JVM.
        for (int i = 0; i < 2; i++) {

            Resultado resultado =
                    Paralelo.ejecutar(texto, numeroHilos);

            compararResultados(
                    referencia,
                    resultado,
                    "Calentamiento con " + numeroHilos + " hilos"
            );
        }

        long suma = 0;

        for (int i = 0; i < REPETICIONES; i++) {

            long inicio = System.nanoTime();

            Resultado resultado =
                    Paralelo.ejecutar(texto, numeroHilos);

            long fin = System.nanoTime();

            // Comprobar que el resultado sea correcto.
            compararResultados(
                    referencia,
                    resultado,
                    "Medición con " + numeroHilos + " hilos"
            );

            suma += fin - inicio;
        }

        return (double) suma / REPETICIONES;
    }

    // Verifica que el resultado no sea nulo.
    private static void verificarResultadoNoNulo(
            Resultado resultado) {

        if (resultado == null) {
            throw new RuntimeException(
                    "El resultado del procesamiento es nulo."
            );
        }
    }

    // Compara los resultados secuenciales y paralelos.
    private static void compararResultados(
            Resultado secuencial,
            Resultado paralelo,
            String descripcion) {

        verificarResultadoNoNulo(secuencial);
        verificarResultadoNoNulo(paralelo);

        if (secuencial.getTotalPalabras()
                != paralelo.getTotalPalabras()) {

            throw new RuntimeException(
                    "El total de palabras no coincide. "
                    + descripcion
            );
        }

        if (secuencial.getAparicionesPalabra()
                != paralelo.getAparicionesPalabra()) {

            throw new RuntimeException(
                    "Las apariciones de Quijote no coinciden. "
                    + descripcion
            );
        }

        if (secuencial.getPalabrasConSecuencia()
                != paralelo.getPalabrasConSecuencia()) {

            throw new RuntimeException(
                    "Las palabras que contienen ción no coinciden. "
                    + descripcion
            );
        }
    }

    // Comprueba los resultados para 2 y 4 hilos.
    private static void comprobarResultados(
            String nombre,
            String texto) {

        System.out.println("VERIFICACIÓN: " + nombre);

        // Resultado de referencia.
        Resultado secuencial =
                Secuencial.ejecutar(texto);

        // Verificar con dos hilos.
        Resultado paralelo2 =
                Paralelo.ejecutar(texto, 2);

        compararResultados(
                secuencial,
                paralelo2,
                nombre + " - 2 hilos"
        );

        System.out.println(
                "CORRECTO: los resultados secuenciales y "
                + "de 2 hilos coinciden."
        );

        // Verificar con cuatro hilos.
        Resultado paralelo4 =
                Paralelo.ejecutar(texto, 4);

        compararResultados(
                secuencial,
                paralelo4,
                nombre + " - 4 hilos"
        );

        System.out.println(
                "CORRECTO: los resultados secuenciales y "
                + "de 4 hilos coinciden."
        );

        // Mostrar los resultados de referencia.
        System.out.println("\nResultados comprobados:");
        System.out.println(secuencial);
    }

    // Genera una versión ampliada del documento.
    private static String generarTextoAmpliado(String texto, int veces) {

        StringBuilder resultado = new StringBuilder(texto.length() * veces);

        for (int i = 0; i < veces; i++) {

            resultado.append(texto);

            if (!texto.endsWith("\n")) {
                resultado.append("\n");
            }
        }

        return resultado.toString();
    }
}