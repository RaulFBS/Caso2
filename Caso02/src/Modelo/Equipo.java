package Modelo;

public class Equipo {
    private int id;
    private String tipo;
    private String marca;
    private String modelo;

    public Equipo() { }
    public Equipo(int id, String tipo, String marca, String modelo) {
        this.id = id; this.tipo = tipo; this.marca = marca; this.modelo = modelo;
    }
    public Equipo(String tipo, String marca, String modelo) { this(0, tipo, marca, modelo); }
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }
    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }
    @Override public String toString() { return tipo + " - " + marca + " " + modelo; }
}
