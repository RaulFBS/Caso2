package caso02;

import Controlador.OrdenControlador;
import Repository.OrdenRepository;
import Vista.MainView;
import javax.swing.SwingUtilities;

public class Caso02 {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainView vista = new MainView();
            new OrdenControlador(new OrdenRepository(), vista);
            vista.setLocationRelativeTo(null);
            vista.setVisible(true);
        });
    }
}
