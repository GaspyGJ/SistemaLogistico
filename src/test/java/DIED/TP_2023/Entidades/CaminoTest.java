package DIED.TP_2023.Entidades;

import SistemaLogistico.Entidades.Camino;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CaminoTest {

    @Test
    public void testTiempoTransitoMayorA() {

        Camino camino = new Camino();
        camino.setTiempoTransito("02:30");

        //Hay 5 caminos posibles siguiendo los diferentes IF ELSE.
        //tiempo de transito tiene formato: HH:MM

        assertTrue(camino.tiempoTransito_mayorA("02:00"));
        assertTrue(camino.tiempoTransito_mayorA("02:15"));
        assertTrue(camino.tiempoTransito_mayorA("02:30"));
        assertFalse(camino.tiempoTransito_mayorA("03:00"));
        assertFalse(camino.tiempoTransito_mayorA("04:00"));

        // Otros
        assertFalse(camino.tiempoTransito_mayorA("20:45"));
        assertTrue(camino.tiempoTransito_mayorA("00:90"));
        assertTrue(camino.tiempoTransito_mayorA("00:10"));
    }

    @Test
    public void testTiempoTransitoMenorA() {
        // Crea una instancia de la clase Camino
        Camino camino = new Camino();
        camino.setTiempoTransito("02:30");

        assertFalse(camino.tiempoTransito_menorA("02:00"));
        assertFalse(camino.tiempoTransito_menorA("02:15"));
        assertTrue(camino.tiempoTransito_menorA("02:30"));
        assertTrue(camino.tiempoTransito_menorA("03:00"));
        assertTrue(camino.tiempoTransito_menorA("04:00"));

        // Otros
        assertFalse(camino.tiempoTransito_menorA("00:30"));
        assertTrue(camino.tiempoTransito_menorA("03:05"));
        assertFalse(camino.tiempoTransito_menorA("00:00"));
    }
}
