public class Resultado {

    private long totalPalabras;
    private long aparicionesPalabra;
    private long palabrasConSecuencia;

    public Resultado() {
        this.totalPalabras = 0;
        this.aparicionesPalabra = 0;
        this.palabrasConSecuencia = 0;
    }

    public Resultado(long totalPalabras,
                     long aparicionesPalabra,
                     long palabrasConSecuencia) {

        this.totalPalabras = totalPalabras;
        this.aparicionesPalabra = aparicionesPalabra;
        this.palabrasConSecuencia = palabrasConSecuencia;
    }

    public long getTotalPalabras() {
        return totalPalabras;
    }

    public long getAparicionesPalabra() {
        return aparicionesPalabra;
    }

    public long getPalabrasConSecuencia() {
        return palabrasConSecuencia;
    }

    public void sumar(Resultado otro) {
        this.totalPalabras += otro.totalPalabras;
        this.aparicionesPalabra += otro.aparicionesPalabra;
        this.palabrasConSecuencia += otro.palabrasConSecuencia;
    }

    @Override
    public String toString() {
        return "Total de palabras: " + totalPalabras +
               "\nApariciones de \"Quijote\": " + aparicionesPalabra +
               "\nPalabras que contienen \"ción\": " + palabrasConSecuencia;
    }
}