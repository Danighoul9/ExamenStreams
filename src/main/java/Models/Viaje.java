package Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Viaje {
    private long id;
    private Viajero viajero;          // se resuelve buscando por DNI al cargar
    private String lineaTransporte;   // ej: "L1", "L5", "Metro-A"
    private String origen;
    private String destino;
    private LocalDate fechaViaje;
    private LocalTime horaViaje;
    private int duracionMinutos;
    private double precio;
    private boolean incidencia;


    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("|| ID: ");
        sb.append(id);
        sb.append("| ").append(viajero.getDni());
        sb.append("| ").append(lineaTransporte);
        sb.append("| ").append(origen);
        sb.append("| ").append(destino);
        sb.append("| ").append(fechaViaje);
        sb.append(" ").append(horaViaje);
        sb.append("| ").append(duracionMinutos);
        sb.append("| ").append(precio);
        sb.append("| ").append("Incidencia: " + incidencia + " ||").append('\n');
        return sb.toString();
    }
}
