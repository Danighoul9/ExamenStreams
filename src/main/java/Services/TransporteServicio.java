package Services;

import Models.TipoAbono;
import Models.Viaje;
import Models.Viajero;

import java.time.LocalDate;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class TransporteServicio {
    private final List<Viajero> viajeros;
    private final List<Viaje> viajes;

    public TransporteServicio(List<Viajero> viajeros, List<Viaje> viajes) {
        this.viajeros = viajeros;
        this.viajes = viajes;
    }

    /**
     **Consulta 1 — Viajes de una línea concreta ordenados por hora
     * Método: List<Viaje> getViajesPorLinea(String linea)
     * Mostrar todos los viajes de una línea dada (por ejemplo "L1"), ordenados por hora de forma ascendente.
     * Streams: filter, sorted
     **/
    public List<Viaje> getViajesPorLineaOrderByHora(String linea) {
        return viajes.stream()
                .filter(v -> v.getLineaTransporte().equals(linea))
                .sorted(Comparator.comparing(Viaje::getHoraViaje))
                .toList();
    }

    /**
     **Consulta 2 — Viajeros con incidencias
     * Método: List<String> getViajerosConIncidencias()
     * Obtener los nombres sin repetición de los viajeros que hayan tenido algún viaje con incidencia, ordenados alfabéticamente.
     * Streams: filter, map, distinct, sorted
     **/
    public List<String> getViajerosConIncidencias() {
        return viajes.stream()
                .filter(Viaje::isIncidencia)
                .map(v -> v.getViajero().getNombre())
                .distinct()
                .sorted()
                .toList();
    }

    /**
     **Consulta 3 — Primer viaje del día
     * Método: Optional<Viaje> getPrimerViaje(LocalDate fecha)
     * Encontrar el primer viaje registrado (el más temprano) de una fecha concreta.
     * Streams: filter, min
     **/
    public Optional<Viaje> getPrimerViaje(LocalDate fecha) {
        return viajes.stream()
                .filter(v -> v.getFechaViaje().equals(fecha))
                .min(Comparator.comparing(Viaje::getHoraViaje));
    }

    /**
     **Consulta 4 — Viajes largos
     * Método: List<Viaje> getViajesLargos(int minutos)
     * Mostrar los viajes cuya duración supere el número de minutos dado, ordenados de mayor a menor duración.
     * Streams: filter, sorted
     **/
    public List<Viaje> getViajesLargos(int minutos) {
        return viajes.stream()
                .filter(v -> v.getDuracionMinutos() > minutos)
                .sorted(Comparator.comparing(Viaje::getDuracionMinutos).reversed())
                .toList();
    }

    /**
     **Consulta 5 — Top 5 viajes más largos
     * Método: List<String> getTop5ViajesPorDuracion()
     * Obtener los 5 viajes que más han durado, mostrando linea, origen, destino, fecha y hora, de mayor a menor.
     * Streams: sorted, limit, map
     **/
    public List<String> getTop5ViajesPorDuracion() {
        return viajes.stream()
                .sorted(Comparator.comparing(Viaje::getDuracionMinutos).reversed())
                .limit(5)
                .map(v -> "| " + v.getLineaTransporte() + " " + " " +  v.getOrigen() + " " +
                        v.getDestino() + " " + v.getFechaViaje() + " " +
                        v.getHoraViaje() + " |")
                .toList();
    }
    /**
     **Consulta 6 — Gasto total por viajero
     * Método: Map<String, Double> getGastoTotalPorViajero()
     * Crear un mapa donde la clave sea el nombre del viajero y el valor sea la suma total gastada en todos sus viajes.
     * Streams: collect, groupingBy, summingDouble
     **/
    public Map<String, Double> getGastoTotalPorViajero() {
        return viajeros.stream()
                .collect(Collectors.groupingBy(Viajero::getNombre,
                        Collectors.summingDouble(Viajero::getSaldoPuntos)));
    }

    /**
     **Consulta 7 — Duración media por línea
     * Método: Map<String, Double> getDuracionMediaPorLinea()
     * Obtener la duración media de los viajes agrupada por línea de transporte.
     * En el main muestra el mapa ordenado por línea de transporte.
     * Streams: collect, groupingBy, averagingInt
     **/
    public Map<String, Double> getDuracionMediaPorLinea() {
        return viajes.stream()
                .collect(Collectors.groupingBy(Viaje::getLineaTransporte,
                        Collectors.averagingInt(Viaje::getDuracionMinutos)));
    }

    /**
     **Consulta 8 — Número de viajes por mes
     * Método: Map<Integer, Long> getViajesPorMes()
     * Crear un mapa donde la clave sea el mes (valor numérico 1–12) y el valor sea el número de viajes realizados ese mes.
     * Mostrar ordenado por mes.
     * Para mostrar ordenado por mes, puedes ordenar las claves del mapa una vez obtenido en el main cuando lo vas a pintar.
     * Streams: collect, groupingBy, counting
     **/
    public Map<Integer, Long> getViajesPorMes() {
        return viajes.stream()
                .collect(Collectors.groupingBy(v -> v.getFechaViaje().getMonthValue(),
                        Collectors.counting()));
    }


    /**
     **Consulta 9 — Municipios con incidencias
     * Método: Set<String> getMunicipiosConIncidencias()
     * Municipios que tengan incidencias ordenados alfabéticamente.
     *
     * Streams: filter, map, collect
     **/ //ARREGLAR 9

    public Set<String> getMunicipiosConIncidencias(){
         return viajes.stream()
                .filter(Viaje::isIncidencia)
                .map(v -> v.getViajero().getMunicipio())
                .collect(Collectors.toCollection(TreeSet::new));
    }


    /**
     **Consulta 10 — Estadísticas de duración de viajes
     * Método: void getEstadisticasDuracion()
     * Calcular y mostrar las siguientes estadísticas sobre la duración de todos los viajes:
     *
     * Media
     * Duración mínima
     * Duración máxima
     * Suma total de minutos
     * Streams: mapToInt, summaryStatistics
     **/
    public void getEstadisticasDuracion() {
        IntSummaryStatistics viajesStats = viajes.stream()
                .mapToInt(Viaje::getDuracionMinutos)
                .summaryStatistics();
        IO.println("Media: " + viajesStats.getAverage() + "min \n"+
                   "Duración mínima: " + viajesStats.getMin() + "min \n" +
                   "Duración máxima: " + viajesStats.getMax() + "min \n" +
                   "Suma total de minutos: " + viajesStats.getSum() + "min \n");
    }


    /**
     **Consulta 11 — Viajes gratuitos o de bajo coste
     * Método: List<String> getViajesBaratos(double precioMax)
     * Obtener todos los viajes cuyo precio sea menor o igual al umbral dado,
     * ordenados por precio ascendente y mostrando línea, origen, destino y precio.
     * Streams: filter, sorted, map (para formatear la salida)
     **/
    public List<String> getViajesBaratos(double precioMax) {
        return viajes.stream()
                .filter(v -> v.getPrecio() <= precioMax)
                .sorted(Comparator.comparing(Viaje::getPrecio))
                .map(v -> "| " + v.getLineaTransporte() + " " + " " +  v.getOrigen() + " " +
                        v.getDestino() + " " + v.getPrecio() + "€" + " |")
                .toList();
    }

    /**
     **Consulta 12 — Comprobar si todos los viajeros ANUALES superan 100 puntos
     * Método: boolean todosAnualesSuperanPuntos(int puntos)
     * Verificar si todos los viajeros con tipo de abono ANUAL tienen un saldo de puntos superior al valor dado.
     * Streams: filter sobre viajeros, allMatch
     *
     * Nota: Esta consulta opera sobre la lista viajeros, no sobre viajes.
     **/
    public boolean todosAnualesSuperanPuntos(int puntos){
        return viajeros.stream()
                .filter(v -> v.getTipoAbono().equals(TipoAbono.ANUAL))
                .allMatch(v -> v.getSaldoPuntos() > puntos);
    }

    /**
     **Consulta 13 — Mostrar el gasto de los viajeros en un mes concreto
     * Método: Map<String, Double> getGastoPorViajeroEnMes(int mes)
     * Dado un mes (valor numérico 1–12), mostrar el gasto total de cada viajero que haya realizado algún viaje ese mes.
     * Streams: filter, collect, groupingBy, summingDouble
     **/
    public Map<String, Double> getGastoPorViajeroEnMes(int mes){
        return viajes.stream()
                .filter(v -> v.getFechaViaje().getMonthValue() == mes)
                .collect(Collectors.groupingBy(v -> v.getViajero().getNombre(),
                        Collectors.summingDouble(v -> v.getViajero().getSaldoPuntos())));
    }

    /**
     **Consulta 14 — Línea con más incidencias
     * Método: Optional<String> getLineaConMasIncidencias()
     * Obtener la línea de transporte que ha acumulado más viajes con incidencia.
     *
     * Hay que sacar el máximo del EntrySet (el valor) ordenado por valor.
     * Streams: filter, collect, groupingBy, counting, max
     **/
    public Optional<String> getLineaConMasIncidencias(){
        Map<String, Long> LineaConMasIncidencias = viajes.stream()
                .filter(Viaje::isIncidencia)
                .collect(Collectors.groupingBy(Viaje::getLineaTransporte,
                        Collectors.counting()));

        LineaConMasIncidencias.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .ifPresent(entry -> IO.println(entry.getKey()));

        //Devuelvo optional.empty, sugerido por IntelliJ con la bombilla en rojo, ya que tenia que devolver algo,
        // y funciona.
        return Optional.empty();
    }


    /**
     **Consulta 15 — Viajeros con abono OCASIONAL y gasto alto
     * Método: List<String> getOcasionalesGastoAlto(double umbral)
     * Obtener los nombres de los viajeros con tipo de abono OCASIONAL cuyo gasto total acumulado supere el umbral dado.
     *
     * Streams: filter, collect, groupingBy, summingDouble, filter sobre entryset.stream()
     **/

    public List<String> getOcasionalesGastoAlto(double umbral){
        Map<String, Double> viajerosOcasionales = viajeros.stream()
                .filter(v -> v.getTipoAbono().equals(TipoAbono.OCASIONAL))
                .collect(Collectors.groupingBy(Viajero::getNombre,
                        Collectors.summingDouble(Viajero::getSaldoPuntos)));

        List<Map.Entry<String, Double>> viajeros =viajerosOcasionales.entrySet().stream()
                .filter(gastos -> gastos.getValue() > umbral)
                .toList();

        return List.of(viajeros.toString());
    }

}
