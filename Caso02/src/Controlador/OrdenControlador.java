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
import javax.swing.text.JTextComponent;

public class OrdenControlador {
    private final IOrdenRepository repositorio;
    private final MainView vista;
    private Cliente cliente;
    private Equipo equipo;
    private OrdenServicio ordenPendiente;

    public OrdenControlador(IOrdenRepository repositorio, MainView vista) {
        if (repositorio == null || vista == null) {
            throw new IllegalArgumentException("Dependencias requeridas");
        }
        this.repositorio = repositorio;
        this.vista = vista;
        registrarEventos();
        mostrar("card2");
    }

    private void registrarEventos() {
        on("btnRegistrarCliente", e -> registrarCliente());
        on("btnLimpiar", e -> limpiarCliente());
        on("Buscar", e -> buscarCliente());
        on("btnRegistrarEquipo", e -> registrarEquipo());
        on("btnRegresar", e -> mostrar("card2"));
        on("btnRegistrarServicio", e -> prepararOrden());
        on("btnRegresarServicio", e -> mostrar("card3"));
        on("btnConfirmarOrden", e -> confirmarOrden());
        on("btnRegresarResumen", e -> mostrar("card4"));
    }

    private void registrarCliente() {
        String dni = texto("txtDni");
        String nombre = texto("txtNombreCliente");
        String telefono = texto("txtTelefono");

        if (dni.isEmpty() || nombre.isEmpty() || telefono.isEmpty()) {
            error("Complete todos los datos del cliente");
            return;
        }

        cliente = new Cliente(dni, nombre, telefono);
        info("Cliente registrado exitosamente.\nProcediendo a registrar equipo...");
        limpiarCliente();
        mostrar("card3");
    }

    private void buscarCliente() {
        String dni = texto("txtDni");
        if (dni.isEmpty()) {
            error("Ingrese un DNI para buscar");
            return;
        }
        info("Búsqueda de cliente con DNI: " + dni + "\n(Funcionalidad disponible con base de datos)");
    }

    private void limpiarCliente() {
        setTexto("txtDni", "");
        setTexto("txtNombreCliente", "");
        setTexto("txtTelefono", "");
    }

    private void registrarEquipo() {
        String tipo = valor("jComboBox1");
        String marca = texto("txtMarca");
        String modelo = texto("txtModelo");

        if (tipo == null || tipo.isEmpty() || tipo.equals("Item 1")) {
            error("Seleccione un tipo de equipo válido");
            return;
        }
        if (marca.isEmpty()) {
            error("Ingrese la marca del equipo");
            return;
        }
        if (modelo.isEmpty()) {
            error("Ingrese el modelo del equipo");
            return;
        }

        equipo = new Equipo(tipo, marca, modelo);
        info("Equipo registrado exitosamente.\nProcediendo a registrar servicio...");
        limpiarEquipo();
        mostrar("card4");
    }

    private void limpiarEquipo() {
        setTexto("txtMarca", "");
        setTexto("txtModelo", "");
    }

    private void prepararOrden() {
        if (cliente == null) {
            error("Debe registrar un cliente primero");
            return;
        }
        if (equipo == null) {
            error("Debe registrar un equipo primero");
            return;
        }

        String base = valor("jComboBox2");
        String urgencia = radio();
        String descripcion = texto("jTextArea1");

        if (base == null || base.isEmpty() || base.equals("Item 1")) {
            error("Seleccione un servicio base válido");
            return;
        }
        if (urgencia.isEmpty()) {
            error("Seleccione un nivel de urgencia");
            return;
        }
        if (descripcion.isEmpty()) {
            error("Describa el problema del equipo");
            return;
        }

        List<ServicioAdicional> adicionales = new ArrayList<>();
        String[] checks = {"jCheckBox1", "jCheckBox2", "jCheckBox3", "jCheckBox5"};

        for (String name : checks) {
            JCheckBox c = getCheckBox(name);
            if (c.isSelected()) {
                adicionales.add(new ServicioAdicional(c.getText()));
            }
        }

        ordenPendiente = new OrdenServicio(cliente, equipo,
                new Servicio(base, urgencia, descripcion),
                adicionales);

        setTexto("jTextArea2", resumen(ordenPendiente));
        limpiarServicio();
        info("Servicio registrado.\nVerifique el resumen de la orden...");
        mostrar("card5");
    }

    private void limpiarServicio() {
        setTexto("jTextArea1", "");
        getCheckBox("jCheckBox1").setSelected(false);
        getCheckBox("jCheckBox2").setSelected(false);
        getCheckBox("jCheckBox3").setSelected(false);
        getCheckBox("jCheckBox5").setSelected(false);
    }

    private void confirmarOrden() {
        if (ordenPendiente == null) {
            error("No hay una orden para confirmar");
            return;
        }
        repositorio.guardar(ordenPendiente);
        info("✓ Orden registrada con éxito\nCódigo de orden: " + ordenPendiente.getId());
        ordenPendiente = null;
        cliente = null;
        equipo = null;
        mostrar("card2");
    }

    private String resumen(OrdenServicio o) {
        StringBuilder s = new StringBuilder();
        s.append("═════════════════════════════\n");
        s.append("          RESUMEN DE ORDEN\n");
        s.append("═════════════════════════════\n\n");

        s.append("CLIENTE\n");
        s.append("DNI: ").append(o.getCliente().getDni()).append("\n");
        s.append("Nombre: ").append(o.getCliente().getNombre()).append("\n");
        s.append("Teléfono: ").append(o.getCliente().getTelefono()).append("\n\n");

        s.append("EQUIPO\n");
        s.append("Tipo: ").append(o.getEquipo().getTipo()).append("\n");
        s.append("Marca: ").append(o.getEquipo().getMarca()).append("\n");
        s.append("Modelo: ").append(o.getEquipo().getModelo()).append("\n\n");

        s.append("SERVICIO\n");
        s.append("Servicio: ").append(o.getServicio().getServicioBase()).append("\n");
        s.append("Urgencia: ").append(o.getServicio().getUrgencia()).append("\n");
        s.append("Problema: ").append(o.getServicio().getDescripcionProblema()).append("\n\n");

        if (!o.getServiciosAdicionales().isEmpty()) {
            s.append("SERVICIOS ADICIONALES\n");
            for (ServicioAdicional a : o.getServiciosAdicionales()) {
                s.append("✓ ").append(a.getNombre()).append("\n");
            }
        }
        s.append("\n═════════════════════════════");

        return s.toString();
    }

    private String radio() {
        JRadioButton[] radios = {getRadio("jRadioButton1"), getRadio("jRadioButton2"), getRadio("jRadioButton3")};
        for (JRadioButton r : radios) {
            if (r.isSelected()) {
                return r.getText();
            }
        }
        return "";
    }

    private void mostrar(String card) {
        JPanel p = getComponent("jPanel4");
        ((CardLayout) p.getLayout()).show(p, card);
    }

    private void on(String name, ActionListener listener) {
        ((AbstractButton) getComponent(name)).addActionListener(listener);
    }

    private String texto(String name) {
        return ((JTextComponent) getComponent(name)).getText().trim();
    }

    private void setTexto(String name, String value) {
        ((JTextComponent) getComponent(name)).setText(value == null ? "" : value);
    }

    private String valor(String name) {
        Object v = ((JComboBox<?>) getComponent(name)).getSelectedItem();
        return v == null ? "" : v.toString().trim();
    }

    private JRadioButton getRadio(String name) {
        return (JRadioButton) getComponent(name);
    }

    private JCheckBox getCheckBox(String name) {
        return (JCheckBox) getComponent(name);
    }

    @SuppressWarnings("unchecked")
    private <T extends Component> T getComponent(String name) {
        return (T) getComponentByReflection(name);
    }

    private Object getComponentByReflection(String name) {
        try {
            Field f = MainView.class.getDeclaredField(name);
            f.setAccessible(true);
            return f.get(vista);
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException("Componente inexistente: " + name, e);
        }
    }

    private void info(String m) {
        JOptionPane.showMessageDialog(vista, m, "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    private void error(String m) {
        JOptionPane.showMessageDialog(vista, m, "Validación", JOptionPane.ERROR_MESSAGE);
    }
}
