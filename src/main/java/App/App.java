package App;

import Models.Viaje;
import Models.Viajero;
import Services.TransporteServicio;
import Utils.CsvLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class App {

    public static void llamarMenu(){
        IO.println("------------------------- RED TRANSPORTE PÚBLICO ------------------------");
        IO.println("******* Elige una opcion 1-16 *******");
        IO.println("1.Viajes de una línea");
        IO.println("2. Viajeros con incidencias");
        IO.println("3. Primer viaje del día");
        IO.println("4. Viajes largos");
        IO.println("5. Top 5 viajes más largos");
        IO.println("6. Gasto total por viajero");
        IO.println("7. Duración media por línea");
        IO.println("8. Número de viajes por mes");
        IO.println("9. Municipios con incidencias");
        IO.println("10. Estadísticas de duración de viajes");
        IO.println("11. Viajes gratuitos o de bajo coste");
        IO.println("12. Comprobar si todos los viajeros ANUALES superan 100 puntos");
        IO.println("13. Mostrar el gasto de los viajeros en un mes concreto");
        IO.println("14. Línea con más incidencias");
        IO.println("15. Viajeros con abono OCASIONAL y gasto alto");
        IO.println("16. Salir del programa");

    }

    private static final Logger log = LoggerFactory.getLogger(App.class);

    static void main(String[] args) throws IOException {

        List<Viajero> viajeros = CsvLoader.cargarViajeros("src/main/resources/viajeros.csv");
        List<Viaje> viajes = CsvLoader.cargarViajes("src/main/resources/viajes.csv", viajeros);

        TransporteServicio s = new TransporteServicio(viajeros, viajes);

        int opcion = 1;
        do {
            llamarMenu();
            try {
                opcion = Integer.parseInt(IO.readln());

                if (opcion < 1 || opcion > 16) {
                    IO.println("Por favor, ingrese una opcion del 1-16");
                }

                switch(opcion) {
                    case 1 ->{
                        IO.println("=== Consulta 1: Viajes de la línea L1 ====");
                        s.getViajesPorLineaOrderByHora("L1").forEach(System.out::println);
                    }

                    case 2 ->{
                        IO.println("=== Consulta 2: Viajeros con incidencias ====");
                        s.getViajerosConIncidencias().forEach(System.out::println);
                    }

                    case 3 -> {
                        IO.println("=== Consulta 3: Primer viaje de una fecha concreta ====") ;
                        IO.println(s.getPrimerViaje(LocalDate.of(2025, 3, 6)));
                    }

                    case 4 ->{
                        IO.println("=== Consulta 4: Viajes Largos Duración > 40 min ====");
                        s.getViajesLargos(40).forEach(System.out::println);
                    }

                    case 5 ->{
                        IO.println("=== Consulta 5: Top 5 Viajes por duración ====");
                        s.getTop5ViajesPorDuracion().forEach(System.out::println);
                    }

                    case 6 -> {
                        IO.println("=== Consulta 6: Gasto total por viajero ====");
                        s.getGastoTotalPorViajero()
                                .forEach((k,v) -> IO.println(k + ": " + v + "€"));
                    }

                    case 7 -> {
                        IO.println("=== Consulta 7: Duración media por linea ====");
                        s.getDuracionMediaPorLinea()
                                .forEach((k,v) -> IO.println(k + ": " + v + "min"));
                    }

                    case 8 ->{
                        IO.println("=== Consulta 8: Viajes por mes ====");
                        s.getViajesPorMes()
                                .forEach((k,v) -> IO.println(k + ": " + v));
                    }

                    case 9 -> {
                        IO.println("=== Consulta 9: Municipios con incidencias ====");
                        s.getMunicipiosConIncidencias().forEach(System.out::println);
                    }

                    case 10 ->{
                        IO.println("=== Consulta 10: Estadísticas por duración ====");
                        s.getEstadisticasDuracion();
                    }

                    case 11 -> {
                        IO.println("=== Consulta 11: Viajes Baratos <= 3€ ====");
                        s.getViajesBaratos(3).forEach(System.out::println);
                    }

                    case 12 -> {
                        IO.println("=== Consulta 12: ¿Viajeros Anuales > 100 ptos?====");
                        IO.println("¿Todos los viajeros anuales tienen un saldo superior a 100€? : " +
                                s.todosAnualesSuperanPuntos(100));

                    }

                    case 13 ->{
                        IO.println("=== Consulta 13: Gasto viajeros en ABRIL ====");
                        s.getGastoPorViajeroEnMes(4)
                                .forEach((k,v) -> IO.println(k + ": " + v + "€"));
                    }

                    case 14 ->{
                        IO.println("=== Consulta 14: Linea con mas incidencias ====");
                        s.getLineaConMasIncidencias();

                    }

                    case 15 -> {
                        IO.println("=== Consulta 15: Viajeros con gasto ocasional ====");
                        s.getOcasionalesGastoAlto(100);
                    }

                    case 16 -> IO.println("GRACIAS POR USAR NUESTRO PROGRAMA");
                }
            }catch (Exception e){
                log.error("Error de entrada/salida {}", e.getMessage(), e);
                opcion = -1;
                return;
            }


        }while(opcion != 16);







    }
}
