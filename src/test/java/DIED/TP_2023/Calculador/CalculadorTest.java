package DIED.TP_2023.Calculador;

import SistemaLogistico.Calculador.Calculador;
import SistemaLogistico.Entidades.Camino;
import SistemaLogistico.Entidades.Sucursal;
import SistemaLogistico.Entidades.Utils.EstadoCamino;
import SistemaLogistico.Entidades.Utils.EstadoSucursal;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class CalculadorTest {
    List<Sucursal> sucursalesPrueba;
    List<Camino> caminosPrueba;
    private void crearGrafoPrueba() {
        //Sucursales
        List<Sucursal> sucursales = new ArrayList<>();
        Sucursal A = new Sucursal();
        A.setNombre("A");
        A.setEstado(EstadoSucursal.OPERATIVA);
        sucursales.add(A);
        Sucursal B = new Sucursal();
        B.setNombre("B");
        B.setEstado(EstadoSucursal.OPERATIVA);
        sucursales.add(B);
        Sucursal C = new Sucursal();
        C.setNombre("C");
        C.setEstado(EstadoSucursal.OPERATIVA);
        sucursales.add(C);
        Sucursal D = new Sucursal();
        D.setNombre("D");
        D.setEstado(EstadoSucursal.OPERATIVA);
        sucursales.add(D);
        Sucursal E = new Sucursal();
        E.setNombre("E");
        E.setEstado(EstadoSucursal.OPERATIVA);
        sucursales.add(E);
        Sucursal F = new Sucursal();
        F.setNombre("F");
        F.setEstado(EstadoSucursal.OPERATIVA);
        sucursales.add(F);
        Sucursal G = new Sucursal();
        G.setNombre("G");
        G.setEstado(EstadoSucursal.OPERATIVA);
        sucursales.add(G);

        //Caminos
        List<Camino> caminos = new ArrayList<>();
        Camino c1 = new Camino();
        c1.setEstado(EstadoCamino.OPERATIVO);
        c1.setSucursalOrigen(A);
        c1.setSucursalDestino(B);
        caminos.add(c1);

        Camino c2 = new Camino();
        c2.setEstado(EstadoCamino.OPERATIVO);
        c2.setSucursalOrigen(A);
        c2.setSucursalDestino(C);
        caminos.add(c2);

        Camino c3 = new Camino();
        c3.setEstado(EstadoCamino.OPERATIVO);
        c3.setSucursalOrigen(A);
        c3.setSucursalDestino(D);
        caminos.add(c3);

        Camino c4 = new Camino();
        c4.setEstado(EstadoCamino.OPERATIVO);
        c4.setSucursalOrigen(B);
        c4.setSucursalDestino(E);
        caminos.add(c4);

        Camino c5 = new Camino();
        c5.setEstado(EstadoCamino.OPERATIVO);
        c5.setSucursalOrigen(C);
        c5.setSucursalDestino(E);
        caminos.add(c5);

        Camino c6 = new Camino();
        c6.setEstado(EstadoCamino.OPERATIVO);
        c6.setSucursalOrigen(C);
        c6.setSucursalDestino(F);
        caminos.add(c6);

        Camino c7 = new Camino();
        c7.setEstado(EstadoCamino.OPERATIVO);
        c7.setSucursalOrigen(C);
        c7.setSucursalDestino(D);
        caminos.add(c7);

        Camino c8 = new Camino();
        c8.setEstado(EstadoCamino.OPERATIVO);
        c8.setSucursalOrigen(D);
        c8.setSucursalDestino(F);
        caminos.add(c8);

        Camino c9 = new Camino();
        c9.setEstado(EstadoCamino.OPERATIVO);
        c9.setSucursalOrigen(E);
        c9.setSucursalDestino(G);
        caminos.add(c9);

        Camino c10 = new Camino();
        c10.setEstado(EstadoCamino.OPERATIVO);
        c10.setSucursalOrigen(F);
        c10.setSucursalDestino(G);
        caminos.add(c10);

        this.sucursalesPrueba=sucursales;
        this.caminosPrueba= caminos;
    }
    private List<Sucursal> getAdyacentesPrueba(Sucursal s1) {
        return this.caminosPrueba.stream()
                .filter(c-> c.getEstado()== EstadoCamino.OPERATIVO)
                .filter(c->c.getSucursalOrigen().equals(s1))
                .map(Camino::getSucursalDestino)
                .filter( s-> s.getEstado()== EstadoSucursal.OPERATIVA)
                .collect(Collectors.toList());
    }
    private void findCaminosAuxPrueba(Sucursal s1,Sucursal s2, List<Sucursal> marcados, List<List<Sucursal>> todos) {

        List<Sucursal> adyacentes = getAdyacentesPrueba(s1);
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
                    findCaminosAuxPrueba(ady,s2,copiaMarcados,todos);
                }
            }
        }
    }
    @Test
    public void testFindCaminos(){
        crearGrafoPrueba();

        Sucursal orgigen = sucursalesPrueba.get(0);
        Sucursal destino = sucursalesPrueba.get(6);

        List<List<Sucursal>>salida = new ArrayList<>();
        List<Sucursal> marcados = new ArrayList<>();
        marcados.add(orgigen);
        findCaminosAuxPrueba(orgigen,destino,marcados,salida);

        //A==0 ; B==1 ; C==2 ; D==3 ; E==4 ; F==5 ; G==6

        List<List<Sucursal>>salidaPrueba = new ArrayList<>();
        List<Sucursal> trayectoria1 = new ArrayList<>();
        //A-B-E-G
                trayectoria1.add(sucursalesPrueba.get(0));
                trayectoria1.add(sucursalesPrueba.get(1));
                trayectoria1.add(sucursalesPrueba.get(4));
                trayectoria1.add(sucursalesPrueba.get(6));
        assertTrue(salida.contains(trayectoria1));

        List<Sucursal> trayectoria2 = new ArrayList<>();
        //A-C-E-G
                trayectoria2.add(sucursalesPrueba.get(0));
                trayectoria2.add(sucursalesPrueba.get(2));
                trayectoria2.add(sucursalesPrueba.get(4));
                trayectoria2.add(sucursalesPrueba.get(6));
                salidaPrueba.add(trayectoria2);
        assertTrue(salida.contains(trayectoria2));

        List<Sucursal> trayectoria3 = new ArrayList<>();
        //A-C-F-G
                trayectoria3.add(sucursalesPrueba.get(0));
                trayectoria3.add(sucursalesPrueba.get(2));
                trayectoria3.add(sucursalesPrueba.get(5));
                trayectoria3.add(sucursalesPrueba.get(6));
                salidaPrueba.add(trayectoria3);
        assertTrue(salida.contains(trayectoria3));
        List<Sucursal> trayectoria4 = new ArrayList<>();
        //A-C-D-F-G
                trayectoria4.add(sucursalesPrueba.get(0));
                trayectoria4.add(sucursalesPrueba.get(2));
                trayectoria4.add(sucursalesPrueba.get(3));
                trayectoria4.add(sucursalesPrueba.get(5));
                trayectoria4.add(sucursalesPrueba.get(6));
                salidaPrueba.add(trayectoria4);
        assertTrue(salida.contains(trayectoria4));
        List<Sucursal> trayectoria5 = new ArrayList<>();
        //A-D-F-G
                trayectoria5.add(sucursalesPrueba.get(0));
                trayectoria5.add(sucursalesPrueba.get(3));
                trayectoria5.add(sucursalesPrueba.get(5));
                trayectoria5.add(sucursalesPrueba.get(6));
                salidaPrueba.add(trayectoria5);
        assertTrue(salida.contains(trayectoria5));
    }

    // END Caminos --------------------------------------------------------------------------------------------------------------------------------------------------------------

    @Test
    public void testGenerateDuracion(){
        // Caso de prueba 1: 72 horas y 30 minutos
        Integer[] resultado1 = Calculador.generateDuracion(72, 30);
        Integer[] esperado1 = new Integer[]{3, 0, 30};
        assertArrayEquals(esperado1, resultado1);

        // Caso de prueba 2: 55 horas y 120 minutos (equivale a 57 horas)
        Integer[] resultado2 = Calculador.generateDuracion(55, 120);
        Integer[] esperado2 = new Integer[]{2, 9, 0};
        assertArrayEquals(esperado2, resultado2);

        // Caso de prueba 3: 0 horas y 90 minutos (equivale a 1 hora y 30 minutos)
        Integer[] resultado3 = Calculador.generateDuracion(0, 90);
        Integer[] esperado3 = new Integer[]{0, 1, 30};
        assertArrayEquals(esperado3, resultado3);

        // Caso de prueba 4: 25 horas y 0 minutos
        Integer[] resultado4 = Calculador.generateDuracion(25, 0);
        Integer[] esperado4 = new Integer[]{1, 1, 0};
        assertArrayEquals(esperado4, resultado4);

        // Caso de prueba 5: 48 horas y 59 minutos
        Integer[] resultado5 = Calculador.generateDuracion(48, 59);
        Integer[] esperado5 = new Integer[]{2, 0, 59};
        assertArrayEquals(esperado5, resultado5);
    }
}
