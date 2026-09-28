package Controlador;

import Modelo.*;
import Repository.IOrdenRepository;
import Vista.MainView;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class OrdenControlador {
    private final IOrdenRepository repositorio;
    private final MainView vista;
    private Cliente cliente;
    private Equipo equipo;
    private OrdenServicio ordenPendiente;

    public OrdenControlador(IOrdenRepository repositorio, MainView vista) {
        if (repositorio == null || vista == null) throw new IllegalArgumentException("Dependencias requeridas");
        this.repositorio = repositorio; this.vista = vista; registrarEventos();
    }

    private void registrarEventos() {
        on("btnRegistrarCliente", e -> registrarCliente());
        on("btnLimpiar", e -> limpiarCliente());
        on("btnRegistrarEquipo", e -> registrarEquipo());
        on("btnRegresar", e -> mostrar("card2"));
        on("btnRegistrarServicio", e -> prepararOrden());
        on("btnRegresarServicio", e -> mostrar("card3"));
        on("btnConfirmarOrden", e -> confirmarOrden());
        on("btnRegresarResumen", e -> mostrar("card4"));
    }

    private void registrarCliente() {
        String dni = texto("txtDni"), nombre = texto("txtNombreCliente"), telefono = texto("txtTelefono");
        if (dni.isEmpty() || nombre.isEmpty() || telefono.isEmpty()) { error("Complete todos los datos del cliente"); return; }
        cliente = new Cliente(dni, nombre, telefono); info("Cliente registrado"); mostrar("card3");
    }
    private void limpiarCliente() { setTexto("txtDni", ""); setTexto("txtNombreCliente", ""); setTexto("txtTelefono", ""); }
    private void registrarEquipo() {
        String tipo = valor("jComboBox1"), marca = texto("txtMarca"), modelo = texto("txtModelo");
        if (tipo.isEmpty() || marca.isEmpty() || modelo.isEmpty()) { error("Complete todos los datos del equipo"); return; }
        equipo = new Equipo(tipo, marca, modelo); info("Equipo registrado"); mostrar("card4");
    }
    private void prepararOrden() {
        if (cliente == null || equipo == null) { error("Registre primero el cliente y el equipo"); return; }
        String base = valor("jComboBox2"), urgencia = radio();
        String descripcion = texto("jTextArea1");
        if (base.isEmpty() || urgencia.isEmpty() || descripcion.isEmpty()) { error("Complete los datos del servicio"); return; }
        List<ServicioAdicional> adicionales = new ArrayList<>();
        String[] checks = {"jCheckBox1", "jCheckBox2", "jCheckBox3", "jCheckBox5"};
        for (String name : checks) { JCheckBox c = field(name); if (c.isSelected()) adicionales.add(new ServicioAdicional(c.getText())); }
        ordenPendiente = new OrdenServicio(cliente, equipo, new Servicio(base, urgencia, descripcion), adicionales);
        setTexto("jTextArea2", resumen(ordenPendiente)); mostrar("card5");
    }
    private void confirmarOrden() { if (ordenPendiente == null) { error("No hay una orden para confirmar"); return; } repositorio.guardar(ordenPendiente); info("Orden registrada con código " + ordenPendiente.getId()); ordenPendiente = null; mostrar("card2"); }
    private String resumen(OrdenServicio o) { StringBuilder s = new StringBuilder(); s.append("CLIENTE\n").append(o.getCliente()).append("\n\nEQUIPO\n").append(o.getEquipo()).append("\n\nSERVICIO\n").append(o.getServicio().getServicioBase()).append(" - ").append(o.getServicio().getUrgencia()).append("\n").append(o.getServicio().getDescripcionProblema()).append("\n\nADICIONALES\n"); for (ServicioAdicional a : o.getServiciosAdicionales()) s.append("- ").append(a).append('\n'); return s.toString(); }
    private String radio() { if (button("jRadioButton1").isSelected()) return button("jRadioButton1").getText(); if (button("jRadioButton2").isSelected()) return button("jRadioButton2").getText(); if (button("jRadioButton3").isSelected()) return button("jRadioButton3").getText(); return ""; }
    private void mostrar(String card) { JPanel p = field("jPanel4"); ((CardLayout)p.getLayout()).show(p, card); }
    private void on(String name, ActionListener listener) { ((AbstractButton) field(name)).addActionListener(listener); }
    private String texto(String name) { return ((JTextComponent) field(name)).getText().trim(); }
    private void setTexto(String name, String value) { ((JTextComponent) field(name)).setText(value); }
    private String valor(String name) { Object v = ((JComboBox<?>) field(name)).getSelectedItem(); return v == null ? "" : v.toString().trim(); }
    private JRadioButton button(String name) { return field(name); }
    private JCheckBox field(String name) { return fieldRaw(name); }
    @SuppressWarnings("unchecked") private <T extends Component> T field(String name) { return (T) fieldRaw(name); }
    private Object fieldRaw(String name) { try { Field f = MainView.class.getDeclaredField(name); f.setAccessible(true); return f.get(vista); } catch (ReflectiveOperationException e) { throw new IllegalStateException("Componente inexistente: " + name, e); } }
    private void info(String m) { JOptionPane.showMessageDialog(vista, m, "Información", JOptionPane.INFORMATION_MESSAGE); }
    private void error(String m) { JOptionPane.showMessageDialog(vista, m, "Validación", JOptionPane.ERROR_MESSAGE); }
}
