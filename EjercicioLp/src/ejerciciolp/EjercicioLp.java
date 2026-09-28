package ejerciciolp;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * @author Raul Fernando Bonilla Sanchez
 */
public class EjercicioLp {
    public static void main(String[] args) {
        // TODO code application logic here
        Pattern miObjetoPatron = Pattern.compile("[hH].*",
        Pattern.CASE_INSENSITIVE);
        String MiTexto="Raul Bonilla";
        Matcher miObjetoVerificarCoincidencia = miObjetoPatron.matcher(MiTexto);
        if (miObjetoVerificarCoincidencia.find()){
            System.out.println("Patron encontrado");
        }else {
            System.err.println("No se encuentra el patron");
        }
    }
    
}
