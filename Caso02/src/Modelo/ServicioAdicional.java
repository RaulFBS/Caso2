/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author rs662
 */
public class ServicioAdicional {
    private String nombre;

    public ServicioAdicional(String nombre) {
        this.nombre = nombre;
    }

    public ServicioAdicional() {}

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    @Override
    public String toString() {
        return nombre;
    }
}