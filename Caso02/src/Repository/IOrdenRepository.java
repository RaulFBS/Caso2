package Repository;

import Modelo.OrdenServicio;
import java.util.List;
import java.util.Optional;

public interface IOrdenRepository {
    OrdenServicio guardar(OrdenServicio orden);
    Optional<OrdenServicio> buscarPorId(int id);
    List<OrdenServicio> listar();
    OrdenServicio actualizar(OrdenServicio orden);
    boolean eliminar(int id);
}
