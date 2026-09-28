package Modelo;

public class Servicio {
    private String servicioBase;
    private String urgencia;
    private String descripcionProblema;

    public Servicio() { }
    public Servicio(String servicioBase, String urgencia, String descripcionProblema) {
        this.servicioBase = servicioBase; this.urgencia = urgencia; this.descripcionProblema = descripcionProblema;
    }
    public String getServicioBase() { return servicioBase; }
    public void setServicioBase(String servicioBase) { this.servicioBase = servicioBase; }
    public String getUrgencia() { return urgencia; }
    public void setUrgencia(String urgencia) { this.urgencia = urgencia; }
    public String getDescripcionProblema() { return descripcionProblema; }
    public void setDescripcionProblema(String descripcionProblema) { this.descripcionProblema = descripcionProblema; }
}
