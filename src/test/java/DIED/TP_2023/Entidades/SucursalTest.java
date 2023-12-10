package DIED.TP_2023.Entidades;

import SistemaLogistico.Entidades.Sucursal;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SucursalTest {

    @Test
    public void testHoraAperturaMayorA() {
        // Crea una instancia de la clase Sucursal

        //Hay 5 caminos posibles siguiendo los diferentes IF ELSE.

        Sucursal sucursal = new Sucursal();
        sucursal.setHorarioApertura("09:00");

        assertTrue(sucursal.horaApertura_mayorA("08:00"));
        assertTrue(sucursal.horaApertura_mayorA("08:30"));
        assertTrue(sucursal.horaApertura_mayorA("09:00"));
        assertFalse(sucursal.horaApertura_mayorA("09:30"));
        assertFalse(sucursal.horaApertura_mayorA("10:00"));

        //Otros
        assertFalse(sucursal.horaApertura_mayorA("23:45"));
        assertTrue(sucursal.horaApertura_mayorA("03:00"));
        assertTrue(sucursal.horaApertura_mayorA("00:00"));

    }

    @Test
    public void testHoraAperturaMenorA() {
        // Crea una instancia de la clase Sucursal

        //Hay 5 caminos posibles siguiendo los diferentes IF ELSE.

        Sucursal sucursal = new Sucursal();
        sucursal.setHorarioApertura("09:00");

        assertFalse(sucursal.horaApertura_menorA("08:00"));
        assertFalse(sucursal.horaApertura_menorA("08:30"));
        assertTrue(sucursal.horaApertura_menorA("09:00"));
        assertTrue(sucursal.horaApertura_menorA("09:30"));
        assertTrue(sucursal.horaApertura_menorA("10:00"));

        //Otros
        assertTrue(sucursal.horaApertura_menorA("23:45"));
        assertFalse(sucursal.horaApertura_menorA("03:00"));
        assertFalse(sucursal.horaApertura_menorA("00:00"));

    }

    @Test
    public void testHoraCierreMenorA() {
        //Hay 5 caminos posibles siguiendo los diferentes IF ELSE.

        Sucursal sucursal = new Sucursal();
        sucursal.setHorarioCierre("09:00");

        assertFalse(sucursal.horaCierre_menorA("08:00"));
        assertFalse(sucursal.horaCierre_menorA("08:30"));
        assertTrue(sucursal.horaCierre_menorA("09:00"));
        assertTrue(sucursal.horaCierre_menorA("09:30"));
        assertTrue(sucursal.horaCierre_menorA("10:00"));

        //Otros
        assertTrue(sucursal.horaCierre_menorA("23:45"));
        assertFalse(sucursal.horaCierre_menorA("03:00"));
        assertFalse(sucursal.horaCierre_menorA("00:00"));

    }

    @Test
    public void testHoraCierreMayorA() {
        // Crea una instancia de la clase Sucursal

        //Hay 5 caminos posibles siguiendo los diferentes IF ELSE.

        Sucursal sucursal = new Sucursal();
        sucursal.setHorarioCierre("09:00");

        assertTrue(sucursal.horaCierre_mayorA("08:00"));
        assertTrue(sucursal.horaCierre_mayorA("08:30"));
        assertTrue(sucursal.horaCierre_mayorA("09:00"));
        assertFalse(sucursal.horaCierre_mayorA("09:30"));
        assertFalse(sucursal.horaCierre_mayorA("10:00"));

        //Otros
        assertFalse(sucursal.horaCierre_mayorA("23:45"));
        assertTrue(sucursal.horaCierre_mayorA("03:00"));
        assertTrue(sucursal.horaCierre_mayorA("00:00"));
    }
}
