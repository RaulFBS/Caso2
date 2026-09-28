package Repository;

import Modelo.OrdenServicio;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class OrdenRepository implements IOrdenRepository {
    private final List<OrdenServicio> ordenes = new ArrayList<>();
    private int siguienteId = 1;

    @Override public synchronized OrdenServicio guardar(OrdenServicio orden) {
        if (orden == null) throw new IllegalArgumentException("La orden no puede ser nula");
        if (orden.getId() == 0) orden.setId(siguienteId++);
        else if (orden.getId() >= siguienteId) siguienteId = orden.getId() + 1;
        ordenes.add(orden);
        return orden;
    }
    @Override public synchronized Optional<OrdenServicio> buscarPorId(int id) {
        return ordenes.stream().filter(o -> o.getId() == id).findFirst();
    }
    @Override public synchronized List<OrdenServicio> listar() { return Collections.unmodifiableList(new ArrayList<>(ordenes)); }
    @Override public synchronized OrdenServicio actualizar(OrdenServicio orden) {
        if (orden == null || orden.getId() == 0) throw new IllegalArgumentException("Orden inválida");
        for (int i = 0; i < ordenes.size(); i++) if (ordenes.get(i).getId() == orden.getId()) { ordenes.set(i, orden); return orden; }
        throw new IllegalArgumentException("No existe la orden " + orden.getId());
    }
    @Override public synchronized boolean eliminar(int id) { return ordenes.removeIf(o -> o.getId() == id); }
}
