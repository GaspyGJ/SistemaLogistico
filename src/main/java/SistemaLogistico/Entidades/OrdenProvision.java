package SistemaLogistico.Entidades;

import SistemaLogistico.Entidades.Utils.EstadoOrdenProvision;
import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;


@Entity
@Table(name = "orden_provision")
public class OrdenProvision {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "fecha")
    private String fecha;

    @ManyToOne
    private Sucursal sucursalDestino;

    @Column(name = "tiempo_maximo")
    private String tiempoMaximo;

    @Column(name = "camino_asignado")
    private String caminoAsignado;

    @Column(name = "estado")
    private EstadoOrdenProvision estado;

    @OneToMany(mappedBy = "ordenProvision",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private List<ItemOrdenProvision> items;

    public OrdenProvision() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Sucursal getSucursalDestino() {
        return sucursalDestino;
    }

    public void setSucursalDestino(Sucursal sucursalDestino) {
        this.sucursalDestino = sucursalDestino;
    }

    public String getTiempoMaximo() {
        return tiempoMaximo;
    }

    public void setTiempoMaximo(String tiempoMaximo) {
        this.tiempoMaximo = tiempoMaximo;
    }

    public String getCaminoAsignado() {
        return caminoAsignado;
    }

    public void setCaminoAsignado(String caminoAsignado) {
        this.caminoAsignado = caminoAsignado;
    }

    public EstadoOrdenProvision getEstado() {
        return estado;
    }

    public void setEstado(EstadoOrdenProvision estado) {
        this.estado = estado;
    }

    public List<ItemOrdenProvision> getItems() {
        return items;
    }

    public void setItems(List<ItemOrdenProvision> items) {
        this.items = items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrdenProvision orden = (OrdenProvision) o;
        return  Objects.equals(id, orden.id) &&
                Objects.equals(fecha, orden.fecha) &&
                Objects.equals(sucursalDestino, orden.sucursalDestino) &&
                tiempoMaximo == orden.tiempoMaximo &&
                estado == orden.estado &&
                Objects.equals(items, orden.items);
    }

    @Override
    public String toString() {
        return "OrdenProvision{" +
                "id=" + id +
                ", fecha=" + fecha +
                ", sucursalDestino=" + sucursalDestino +
                ", tiempoMaximo=" + tiempoMaximo +
                ", estado=" + estado +
                ", items=" + items +
                '}';
    }
}
