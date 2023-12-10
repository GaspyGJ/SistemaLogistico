package SistemaLogistico.Calculador;

import SistemaLogistico.DTO.CaminoDTO;
import SistemaLogistico.DTO.Creator;
import SistemaLogistico.DTO.StockProductoDTO;
import SistemaLogistico.DTO.SucursalDTO;
import SistemaLogistico.Entidades.*;
import SistemaLogistico.Entidades.Utils.EstadoCamino;
import SistemaLogistico.Entidades.Utils.EstadoSucursal;

import java.util.*;
import java.util.stream.Collectors;

 public class Calculador  {

     //START Flujo Maximo --------------------------------------------------------------------------------------------------------------------------------------------------------------
     private static List<CaminoCopia> allCaminosCopia;

     /**
      * Realiza verificaciones de fuente y sumidero.
      * Inicializa la lista de CaminosCopia.
      * @return Sucursal fuente
      * @throws Exception Sucursal fuente o sumidero no existen.
      */
     private static Sucursal verificarGrafoFlujoMaximo() throws Exception {
         //Grafo dirigido.
         //Cada camino tiene un capacidad.

         //un nodo fuente (llamado PUERTO)
         Optional<Sucursal> sucursalFuente = Creator.getDTO(SucursalDTO.class).getFuente();
         if(sucursalFuente.isEmpty()){
             throw new Exception("No se encontro sucursal puerto");
         }

         //un nodo sumidero (llamdo CENTRO)

         allCaminosCopia = Creator.getDTO(CaminoDTO.class).getCaminos().stream().map(c ->
                         new CaminoCopia(c.getSucursalOrigen(), c.getSucursalDestino(),c.getCapacidadMaxima(),c.getCapacidadMaxima(),c.getNombre(),c.getEstado()))
                 .filter(c-> c.getEstado()==EstadoCamino.OPERATIVO)
                 .collect(Collectors.toList());

         return sucursalFuente.get();
     }

     private static Optional<CaminoCopia> getCaminoBySucursales(Sucursal origen, Sucursal destino) {
         return allCaminosCopia.stream().filter(c-> c.getSucursalOrigen().equals(origen) && c.getSucursalDestino().equals(destino)).findFirst();
     }

     private static List<Sucursal> getAdyacentesOperativos(Sucursal sucursal){
         return getAdyacentes(sucursal).stream().filter(s-> s.getEstado()==EstadoSucursal.OPERATIVA).collect(Collectors.toList());
     }

      public static double flujoMaximo() throws Exception {

        double flujoMaximo=0.0;

        Sucursal sucursalInicio = verificarGrafoFlujoMaximo();
        List<CaminoCopia> trayectoriaActual = siguienteTrayectoria(sucursalInicio);

        while ( ! trayectoriaActual.isEmpty() ){ //mientras la trayectoria siguiente no sea vacia.

           double menorFlujoDeLaTrayectoria = trayectoriaActual.stream().map(CaminoCopia::getCapacidadRestante).min(Double::compare).get();

            //actualizo el restante de los caminos en base al menor flujo.
            for(CaminoCopia camino : trayectoriaActual){
                camino.setCapacidadRestante(camino.getCapacidadRestante()-menorFlujoDeLaTrayectoria);
            }
            //sumo al flujo maximo el menor flujo encontrado en la trayectoria
            flujoMaximo = flujoMaximo+menorFlujoDeLaTrayectoria;

            //Bloque logg
            System.out.println("Flujo minimo de esta trayectoria: "+ menorFlujoDeLaTrayectoria );
            System.out.println("Flujo maximo total hasta ahora: "+flujoMaximo);
            //Bloque logg

            trayectoriaActual = siguienteTrayectoria(sucursalInicio);
        }

        return flujoMaximo;
    }

     /**
      * Recorre el grafo yendo por los caminos con mayor capacidad residual agregando cada camino a la trayectoria..
      * @return retorna una lista de caminos (trayectoria)
      * @throws Exception Camino entre dos sucursales es Nulo.
      */
     private static List<CaminoCopia> siguienteTrayectoria(Sucursal inicio) throws Exception {
         List<Sucursal> adyacentes = getAdyacentesOperativos(inicio);
         List<CaminoCopia> trayectoria = new ArrayList<>();

         Sucursal sucursalActual = inicio;

         while (!adyacentes.isEmpty()) {

             // Bloque logg
             System.out.println("Los adyacentes de: " + sucursalActual.getNombre());
             for (Sucursal s : adyacentes) {
                 System.out.print(s.getNombre() + " ; ");
             }
             System.out.println("");
             // Bloque logg

             Optional<CaminoCopia> caminoConMayorResidual = Optional.empty();
             double mayorResidual = 0.0;

             // Bucle para encontrar el camino con mayor residual.
             for (Sucursal sucursalAdyacente : adyacentes) {
                 Optional<CaminoCopia> camino = getCaminoBySucursales(sucursalActual, sucursalAdyacente);

                 // Verifico ciertas características del camino encontrado.
                 if (camino.isEmpty()) {
                     throw new Exception("Camino nulo");
                 } else if (camino.get().getEstado() == EstadoCamino.NO_OPERATIVO) {
                     continue; // Ignoro este camino
                 } else if (camino.get().getCapacidadRestante() == 0) {
                     continue; // Ignoro este camino
                 }

                 double residual = camino.get().getCapacidadRestante();
                 if (residual > mayorResidual) {
                     mayorResidual = residual;
                     caminoConMayorResidual = camino;
                 }
             }

             if (caminoConMayorResidual.isEmpty()) {
                 if (trayectoria.isEmpty()) {
                     // Estamos en el nodo Fuente y no hay más caminos posibles, terminamos.
                     break;
                 }
                 // No hay más caminos posibles desde esta sucursalActual, volvemos a la sucursal anterior
                 int ultimoCamino = trayectoria.size() - 1;
                 trayectoria.get(ultimoCamino).setEstado(EstadoCamino.NO_OPERATIVO);
                 Sucursal sucursalAnterior = trayectoria.get(ultimoCamino).getSucursalOrigen();
                 trayectoria.remove(ultimoCamino);
                 adyacentes = getAdyacentesOperativos(sucursalAnterior);
                 sucursalActual = sucursalAnterior;
             } else {
                 // Bloque logg
                 System.out.println("Se eligió ir por la adyacencia de: " + caminoConMayorResidual.get().getSucursalDestino().getNombre());
                 System.out.println("Con residual de: "+caminoConMayorResidual.get().getCapacidadRestante());
                 // Bloque logg

                 trayectoria.add(caminoConMayorResidual.get());
                 sucursalActual = caminoConMayorResidual.get().getSucursalDestino();
                 adyacentes = getAdyacentesOperativos(sucursalActual);
             }

         }

         // Bloque logg
         System.out.println("Trayectoria encontrada:");
         System.out.print(inicio.getNombre() + "->");
         for (CaminoCopia c : trayectoria) {
             System.out.print(c.getSucursalDestino().getNombre() + "->");
         }
         System.out.println("Fin");
         // Bloque logg

         return trayectoria;
     }
     //END Flujo Maximo --------------------------------------------------------------------------------------------------------------------------------------------------------------


     //START Page Rank --------------------------------------------------------------------------------------------------------------------------------------------------------------
     public static Map<Sucursal, Double> pageRank(){

         List<Sucursal> sucursales = Creator.getDTO(SucursalDTO.class).getSucursales();
         List<Camino> caminos = Creator.getDTO(CaminoDTO.class).getCaminos();

         // Inicializacion de el valor de PageRank para cada sucursal.
         Map<Long, Double> pageRank = new HashMap<>();
         for (Sucursal sucursal : sucursales) {
             pageRank.put(sucursal.getId(), 1.0);
         }

         // Iteracion para calcular pageRank
         int iteraciones = 40;
         double factorAmortiguacion = 0.85;

         for (int i = 0; i < iteraciones; i++) {
             Map<Long, Double> nuevosPageRank = new HashMap<>();

             for (Sucursal sucursal : sucursales) {
                 double sumatoriaPageRank = 0.0;

                 List<Camino> caminosEntrantes = caminos.stream().
                         filter(c -> sucursal.equals(c.getSucursalDestino())).toList();

                 //sumatoria por cada nodo origen de un camino entrante
                 // PR(nodoI)/CaminosSalientes(nodoI)
                 for (Camino caminoQueLLega : caminosEntrantes) {
                     Sucursal sucursalOrigen =caminoQueLLega.getSucursalOrigen();
                     double pageRankOrigen = pageRank.get(sucursalOrigen.getId());
                     long cantidadCaminosSalientesOrigen =  caminos.stream().
                             filter(c-> sucursalOrigen.equals(c.getSucursalOrigen()) )
                             .count();
                     sumatoriaPageRank += (pageRankOrigen / cantidadCaminosSalientesOrigen);
                 }

                 //aplico formula de page rank
                 double nuevoPageRank = (1-factorAmortiguacion) + factorAmortiguacion * (sumatoriaPageRank);
                 nuevosPageRank.put(sucursal.getId(), nuevoPageRank);
             }
             pageRank = nuevosPageRank;
         }

         // Ordenar PageRank de mayor a menor
         Map<Long, Double> finalPageRank = pageRank;
         Map<Sucursal, Double> orderedPageRank = new LinkedHashMap<>();

         sucursales.stream()
                 .sorted((s1, s2) -> Double.compare(finalPageRank.get(s2.getId()), finalPageRank.get(s1.getId())))
                 .forEach(s -> orderedPageRank.put(s, finalPageRank.get(s.getId())));

         return orderedPageRank;
     }
     //END Page Rank --------------------------------------------------------------------------------------------------------------------------------------------------------------


     // START Caminos --------------------------------------------------------------------------------------------------------------------------------------------------------------
     private static List<List<Sucursal>> findCaminos(Sucursal s1,Sucursal s2){
         List<List<Sucursal>>salida = new ArrayList<>();
         List<Sucursal> marcados = new ArrayList<>();
         marcados.add(s1);
         findCaminosAux(s1,s2,marcados,salida);
         return salida;
     }

     private static void findCaminosAux(Sucursal s1,Sucursal s2, List<Sucursal> marcados, List<List<Sucursal>> todos) {
         List<Sucursal> adyacentes = getAdyacentes(s1);
         List<Sucursal> copiaMarcados = null;
         for(Sucursal ady: adyacentes){
             copiaMarcados = new ArrayList<Sucursal>(marcados);
             if(ady.equals(s2)) {
                 copiaMarcados.add(s2);
                 todos.add(new ArrayList<Sucursal>(copiaMarcados));
             }
             else {
                 if( !copiaMarcados.contains(ady)) {
                     copiaMarcados.add(ady);
                     findCaminosAux(ady,s2,copiaMarcados,todos);
                 }
             }
         }
     }

     private static List<Sucursal> getAdyacentes(Sucursal s1) {
         return Creator.getDTO(CaminoDTO.class).getCaminos()
                 .stream()
                 .filter(c-> c.getEstado()== EstadoCamino.OPERATIVO)
                 .filter(c->c.getSucursalOrigen().equals(s1))
                 .map(Camino::getSucursalDestino)
                 .filter( s-> s.getEstado()== EstadoSucursal.OPERATIVA)
                 .collect(Collectors.toList());
     }
     // END Caminos --------------------------------------------------------------------------------------------------------------------------------------------------------------




     // START Otros --------------------------------------------------------------------------------------------------------------------------------------------------------------
     public static List<List<Sucursal>>  caminosParaOrdenDeProvision(OrdenProvision ordenProvision){
        Sucursal sucursalDestino = ordenProvision.getSucursalDestino();

        //buscar todos los caminos entre un nodo inicial y el nodo Destino.
        //Primero filtro las sucursales que tienen todos los items de la orden de provision
        List<Sucursal> sucursales = Creator.getDTO(SucursalDTO.class).getSucursales().stream()
                .filter(s -> {
                    if (s.equals(sucursalDestino)) return false;
                    if (s.getEstado()==EstadoSucursal.NO_OPERATIVA) return false;

                    int sumaProductosDisponibles = 0;
                    List<StockProducto> stockProductos = Creator.getDTO(StockProductoDTO.class).getStockProductosBySucursal(s);
                    for (StockProducto stockProducto : stockProductos) {
                        for (ItemOrdenProvision item : ordenProvision.getItems()) {
                            if ((stockProducto.getProducto().equals(item.getProducto())) &&
                                    stockProducto.getCantidad() >= item.getCantidad()) {
                                sumaProductosDisponibles++;
                            }
                        }
                    }
                    //retorna true si hay disponibilidad de todos los items en esta sucursal.
                    return sumaProductosDisponibles == ordenProvision.getItems().size();
                }).toList();

        List<List<Sucursal>> listaTodosLosCaminosDisponibles = new ArrayList<>();

        for (Sucursal sucursalOrigen: sucursales){
            List<List<Sucursal>>  caminos = findCaminos(sucursalOrigen,sucursalDestino);
            listaTodosLosCaminosDisponibles.addAll(caminos);
        }

        return listaTodosLosCaminosDisponibles;
    }

     /**
      * Dada hora y minuto, los transforma y los convierte en un array con dias,horas,minutos
      */
     public static Integer[] generateDuracion(Integer horas, Integer minutos){
        // Convertir a días, horas y minutos
        int dias = horas / 24;
        horas = horas % 24;
        int minutosExtras = minutos % 60;

        // Ajustar los minutos si exceden 60
        horas += minutos / 60;
        minutos = minutosExtras;

        return new Integer[]{dias,horas,minutos};
    }

    public static String[] generarHorarios(){
        String[] horarios = new String[48]; // 48 slots (24 horas * 2 medias horas)
        int hora = 0;
        int minuto = 0;

        for (int i = 0; i < horarios.length; i++) {
            String horaStr = String.format("%02d", hora);
            String minutoStr = String.format("%02d", minuto);
            horarios[i] = horaStr + ":" + minutoStr;

            minuto += 30;
            if (minuto == 60) {
                hora++;
                minuto = 0;
            }
        }
        return horarios;
    }

     // END Otros --------------------------------------------------------------------------------------------------------------------------------------------------------------


 }
