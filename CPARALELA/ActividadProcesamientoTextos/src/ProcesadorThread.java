public class ProcesadorThread extends Thread {

    private final String texto;
    private Resultado resultado;

    public ProcesadorThread(String texto) {
        this.texto = texto;
    }

    @Override
    public void run() {

        resultado = ProcesadorTexto.procesar(texto);
    }

    public Resultado getResultado() {
        return resultado;
    }
}