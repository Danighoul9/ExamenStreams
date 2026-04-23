package Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Viajero {
    private String dni;
    private String nombre;
    private int edad;
    private String municipio;
    private TipoAbono tipoAbono;
    private int saldoPuntos;

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("|| DNI: ");
        sb.append(dni);
        sb.append("| ").append(nombre);
        sb.append("| ").append(edad).append(" años");
        sb.append("| ").append(municipio);
        sb.append("| ").append(tipoAbono);
        sb.append("| ").append(saldoPuntos + " ||").append('\n');
        return sb.toString();
    }
}
