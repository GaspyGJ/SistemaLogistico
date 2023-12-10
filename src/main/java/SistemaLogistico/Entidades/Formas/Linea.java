package SistemaLogistico.Entidades.Formas;

import SistemaLogistico.DTO.Creator;
import SistemaLogistico.DTO.NodoDTO;
import SistemaLogistico.Entidades.Camino;
import UI.Diseño.Colores;
import UI.Diseño.Styles;
import jakarta.persistence.*;

import java.awt.*;
import java.util.Optional;

@Entity
@Table(name = "Linea_Nodos")
public class Linea{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long id;

    @OneToOne
    public Camino camino;

    @Transient
    public Color color = Colores.negro;

    @Transient
    public Stroke grosor = Styles.lapiz_fino;

    public Linea() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Camino getCamino() {
        return camino;
    }

    public void setCamino(Camino camino) {
        this.camino = camino;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Stroke getGrosor() {
        return grosor;
    }

    public void setGrosor(Stroke grosor) {
        this.grosor = grosor;
    }

    public boolean clicked(int x_clicked, int y_clicked) throws Exception {

        Optional<Nodo> nodoi = Creator.getDTO(NodoDTO.class).getNodoBySucursal(camino.getSucursalOrigen());
        Optional<Nodo> nodof = Creator.getDTO(NodoDTO.class).getNodoBySucursal(camino.getSucursalDestino());

        if(nodoi.isEmpty()) throw new Exception("Sucursal no tiene nodo Asignado. Nodo is NULL");
        if(nodof.isEmpty()) throw new Exception("Sucursal no tiene nodo Asignado. Nodo is NULL");

        Nodo nodoInicial= nodoi.get();
        Nodo nodoFinal= nodof.get();

        int x1 = (int) ( nodoInicial.x + (nodoInicial.w) / 2);
        int y1 = (int) (nodoInicial.y + (nodoInicial.h) / 2);
        int x2 = (int) (nodoFinal.x + (nodoFinal.w) / 2);
        int y2 = (int) (nodoFinal.y + (nodoFinal.h) / 2);

        double distancia1 = Math.sqrt(Math.pow(x_clicked - x1, 2) + Math.pow(y_clicked - y1, 2));
        double distancia2 = Math.sqrt(Math.pow(x_clicked - x2, 2) + Math.pow(y_clicked - y2, 2));

        double totalDistancia = Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));

        double tolerancia = 0.5;

        return Math.abs(distancia1 + distancia2 - totalDistancia) < tolerancia;
    }


}
