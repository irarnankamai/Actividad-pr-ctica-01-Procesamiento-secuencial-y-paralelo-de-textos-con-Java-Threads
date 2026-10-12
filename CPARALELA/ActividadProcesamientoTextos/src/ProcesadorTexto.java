import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProcesadorTexto {

    private static final Pattern PATRON_PALABRA =
            Pattern.compile("[\\p{L}]+");

    private static final String PALABRA_BUSCADA = "quijote";
    private static final String SECUENCIA_BUSCADA = "ción";

    public static Resultado procesar(String texto) {

        long totalPalabras = 0;
        long aparicionesPalabra = 0;
        long palabrasConSecuencia = 0;

        String textoMinusculas = texto.toLowerCase();

        Matcher matcher = PATRON_PALABRA.matcher(textoMinusculas);

        while (matcher.find()) {

            String palabra = matcher.group();

            totalPalabras++;

            if (palabra.equals(PALABRA_BUSCADA)) {
                aparicionesPalabra++;
            }

            if (palabra.contains(SECUENCIA_BUSCADA)) {
                palabrasConSecuencia++;
            }
        }

        return new Resultado(
                totalPalabras,
                aparicionesPalabra,
                palabrasConSecuencia
        );
    }
}