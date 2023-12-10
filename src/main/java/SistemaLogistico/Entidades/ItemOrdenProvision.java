package SistemaLogistico.Entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.util.Objects;


@Entity
@Table(name = "item_orden_provision")
public class ItemOrdenProvision {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    private OrdenProvision ordenProvision;

    @ManyToOne
    private Producto producto;

    @Column(name = "cantidad")
    private int cantidad;

    public ItemOrdenProvision() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OrdenProvision getOrdenProvision() {
        return ordenProvision;
    }

    public void setOrdenProvision(OrdenProvision ordenProvision) {
        this.ordenProvision = ordenProvision;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemOrdenProvision item = (ItemOrdenProvision) o;
        return  Objects.equals(id, item.id) &&
                Objects.equals(ordenProvision, item.ordenProvision) &&
                Objects.equals(producto, item.producto) &&
                cantidad == item.cantidad;
    }

    @Override
    public String toString() {
        return "ItemOrdenProvision{" +
                "id=" + id +
                ", ordenProvision=" + ordenProvision +
                ", producto=" + producto +
                ", cantidad=" + cantidad +
                '}';
    }

}
