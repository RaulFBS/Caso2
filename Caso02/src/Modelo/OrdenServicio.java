package Modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class OrdenServicio {
    private int id;
    private Cliente cliente;
    private Equipo equipo;
    private Servicio servicio;
    private final List<ServicioAdicional> serviciosAdicionales = new ArrayList<>();
    private Date fechaCreacion = new Date();

    public OrdenServicio() { }
    public OrdenServicio(Cliente cliente, Equipo equipo, Servicio servicio, List<ServicioAdicional> adicionales) {
        this.cliente = cliente; this.equipo = equipo; this.servicio = servicio;
        if (adicionales != null) this.serviciosAdicionales.addAll(adicionales);
    }
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
    public Equipo getEquipo() { return equipo; }
    public void setEquipo(Equipo equipo) { this.equipo = equipo; }
    public Servicio getServicio() { return servicio; }
    public void setServicio(Servicio servicio) { this.servicio = servicio; }
    public List<ServicioAdicional> getServiciosAdicionales() { return Collections.unmodifiableList(serviciosAdicionales); }
    public void setServiciosAdicionales(List<ServicioAdicional> adicionales) { serviciosAdicionales.clear(); if (adicionales != null) serviciosAdicionales.addAll(adicionales); }
    public Date getFechaCreacion() { return new Date(fechaCreacion.getTime()); }
    public void setFechaCreacion(Date fechaCreacion) { this.fechaCreacion = fechaCreacion == null ? new Date() : new Date(fechaCreacion.getTime()); }
    @Override public String toString() { return "OrdenServicio{" + id + ", cliente=" + cliente + ", equipo=" + equipo + "}"; }
}
