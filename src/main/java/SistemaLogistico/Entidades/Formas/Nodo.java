package SistemaLogistico.Entidades.Formas;

import SistemaLogistico.Entidades.Sucursal;

import UI.Diseño.Colores;
import UI.Diseño.Styles;
import jakarta.persistence.*;

import java.awt.*;

@Entity
@Table(name = "Nodo_Sucursal")
public class Nodo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long id;

    @OneToOne
    public Sucursal sucursal;

    @Column(name = "x")
    public int x;

    @Column(name = "y")
    public int y;

    @Column(name = "w")
    public int w;

    @Column(name = "h")
    public int h;

    @Transient
    public Color colorBorde = Colores.negro;

    @Transient
    public Color colorRelleno = Colores.negro;

    @Transient
    public Stroke grosor = Styles.lapiz_fino;

    public Nodo(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Sucursal getSucursal() {
        return sucursal;
    }

    public void setSucursal(Sucursal sucursal) {
        this.sucursal = sucursal;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public Color getColorBorde() {
        return colorBorde;
    }

    public void setColorBorde(Color colorBorde) {
        this.colorBorde = colorBorde;
    }

    public Color getColorRelleno() {
        return colorRelleno;
    }

    public void setColorRelleno(Color colorRelleno) {
        this.colorRelleno = colorRelleno;
    }

    public Stroke getGrosor() {
        return grosor;
    }

    public void setGrosor(Stroke grosor) {
        this.grosor = grosor;
    }

    public boolean clicked(int x_clicked, int y_clicked){
        if( x_clicked>=this.x && x_clicked<=this.x+w && y_clicked>=this.y && y_clicked<=this.y+h ){
            return true;
        }
        return false;
    }

}
