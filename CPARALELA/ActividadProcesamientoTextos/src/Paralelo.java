public class Paralelo {

    public static Resultado ejecutar(
            String texto,
            int numeroHilos) {

        ProcesadorThread[] hilos =
                new ProcesadorThread[numeroHilos];

        int longitud = texto.length();

        int[] limites = new int[numeroHilos + 1];

        limites[0] = 0;
        limites[numeroHilos] = longitud;

        for (int i = 1; i < numeroHilos; i++) {

            int posicion =
                    (i * longitud) / numeroHilos;

            while (posicion < longitud &&
                   !Character.isWhitespace(
                           texto.charAt(posicion))) {

                posicion++;
            }

            limites[i] = posicion;
        }

        for (int i = 0; i < numeroHilos; i++) {

            int inicio = limites[i];
            int fin = limites[i + 1];

            String fragmento =
                    texto.substring(inicio, fin);

            hilos[i] =
                    new ProcesadorThread(fragmento);
        }

        // Iniciar todos los hilos
        for (int i = 0; i < numeroHilos; i++) {

            hilos[i].start();
        }

        Resultado resultadoFinal =
                new Resultado();

        // Esperar a todos los hilos
        for (int i = 0; i < numeroHilos; i++) {

            try {

                hilos[i].join();

                resultadoFinal.sumar(
                        hilos[i].getResultado()
                );

            } catch (InterruptedException e) {

                Thread.currentThread().interrupt();

                throw new RuntimeException(
                        "El hilo fue interrumpido.",
                        e
                );
            }
        }

        return resultadoFinal;
    }
}