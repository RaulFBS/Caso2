package Modelo;

public class ServicioAdicional {
    private String nombre;
    public ServicioAdicional() { }
    public ServicioAdicional(String nombre) { this.nombre = nombre; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    @Override public String toString() { return nombre; }
}
