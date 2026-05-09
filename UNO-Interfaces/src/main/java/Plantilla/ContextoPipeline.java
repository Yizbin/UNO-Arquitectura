package Plantilla;

//@author SAUL ISAAC APODACA BALDENEGRO 00000252020

public class ContextoPipeline<T> {

    private T mensaje;
    private String error;
    private boolean detenido;

    public ContextoPipeline(T mensaje) {
        this.mensaje = mensaje;
    }

    public T getMensaje() {
        return mensaje;
    }

    public void setMensaje(T mensaje) {
        this.mensaje = mensaje;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public boolean estaDetenido() {
        return detenido;
    }

    public void setDetenido(boolean detenido) {
        this.detenido = detenido;
    }

    public void detener() {
        this.detenido = true;
    }

    public void detenerConError(String error) {
        this.error = error;
        this.detenido = true;
    }

}
