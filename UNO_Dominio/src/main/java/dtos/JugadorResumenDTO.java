package dtos;

//@author SAUL ISAAC APODACA BALDENEGRO 00000252020

public class JugadorResumenDTO {
    
    private String nombreUsuario;
    private String rutaAvatar;
    private int cantidadDeCartas;
    private int puntos;
    private boolean enTurno;

    public JugadorResumenDTO() {
    }

    public JugadorResumenDTO(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public JugadorResumenDTO(String nombreUsuario, int cantidadDeCartas, int puntos, boolean enTurno) {
        this.nombreUsuario = nombreUsuario;
        this.cantidadDeCartas = cantidadDeCartas;
        this.puntos = puntos;
        this.enTurno = enTurno;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public int getCantidadDeCartas() {
        return cantidadDeCartas;
    }

    public void setCantidadDeCartas(int cantidadDeCartas) {
        this.cantidadDeCartas = cantidadDeCartas;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public boolean isEnTurno() {
        return enTurno;
    }

    public void setEnTurno(boolean enTurno) {
        this.enTurno = enTurno;
    }

    public String getRutaAvatar() {
        return rutaAvatar;
    }

    public void setRutaAvatar(String rutaAvatar) {
        this.rutaAvatar = rutaAvatar;
    }
}
