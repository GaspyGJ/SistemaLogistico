package DIED.TP_2023.Entidades.Formas;

import SistemaLogistico.Entidades.Formas.Nodo;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class NodoTest {

        @Test
        public void testClickedInsideArea() {
            // Creamos una instancia de la clase ClickableArea con coordenadas (10, 10) y tamaño (20x20)
            Nodo nodo = new Nodo();
            nodo.setW(20);
            nodo.setH(20);
            nodo.setX(10);
            nodo.setY(10);

            // Probamos la función clicked con coordenadas que están dentro del área
            assertTrue(nodo.clicked(20, 20));  // Punto central del área
            assertTrue(nodo.clicked(15, 15));  // Punto dentro del área
            assertTrue(nodo.clicked(25, 25));  // Punto dentro del área
            assertTrue(nodo.clicked(10, 10));  // Esquina superior izquierda del área
            assertTrue(nodo.clicked(30, 30));  // Esquina inferior derecha del área
            assertTrue(nodo.clicked(10, 30));  // Esquina inferior izquierda del área
            assertTrue(nodo.clicked(30, 10));  // Esquina inferior derecha del área

            // Probamos la función clicked con coordenadas que están fuera del área
            assertFalse(nodo.clicked(5, 5));    // Fuera del área en la esquina superior izquierda
            assertFalse(nodo.clicked(31, 31));  // Fuera del área en la esquina inferior derecha
            assertFalse(nodo.clicked(8, 15));   // Fuera del área en el eje X
            assertFalse(nodo.clicked(15, 35));  // Fuera del área en el eje Y
        }

}