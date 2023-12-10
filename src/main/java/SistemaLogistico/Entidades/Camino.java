package SistemaLogistico.Entidades;

import SistemaLogistico.Entidades.Utils.EstadoCamino;
import jakarta.persistence.*;

import java.util.Objects;


@Entity
@Table(name = "camino")
public class Camino {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sucursal_origen_id")
    private Sucursal sucursalOrigen;

    @ManyToOne
    @JoinColumn(name = "sucursal_destino_id")
    private Sucursal sucursalDestino;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "tiempo_transito")
    private String tiempoTransito;

    @Column(name = "capacidad_maxima")
    private double capacidadMaxima;

    @Column(name = "estado")
    private EstadoCamino estado;

    public Camino() {}


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Sucursal getSucursalOrigen() {
        return sucursalOrigen;
    }

    public void setSucursalOrigen(Sucursal sucursalOrigen) {
        this.sucursalOrigen = sucursalOrigen;
    }

    public Sucursal getSucursalDestino() {
        return sucursalDestino;
    }

    public void setSucursalDestino(Sucursal sucursalDestino) {
        this.sucursalDestino = sucursalDestino;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTiempoTransito() {
        return tiempoTransito;
    }

    public void setTiempoTransito(String tiempoTransito) {
        this.tiempoTransito = tiempoTransito;
    }

    public double getCapacidadMaxima() {
        return capacidadMaxima;
    }

    public void setCapacidadMaxima(double capacidadMaxima) {
        this.capacidadMaxima = capacidadMaxima;
    }

    public EstadoCamino getEstado() {
        return estado;
    }

    public void setEstado(EstadoCamino estado) {
        this.estado = estado;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Camino camino = (Camino) o;
        return  Objects.equals(id, camino.id) &&
                Objects.equals(sucursalOrigen, camino.sucursalOrigen) &&
                Objects.equals(sucursalDestino, camino.sucursalDestino) &&
                tiempoTransito.equals(camino.tiempoTransito) &&
                capacidadMaxima == camino.capacidadMaxima &&
                estado == camino.estado;
    }

    @Override
    public String toString() {
        return "Camino{" +
                "id=" + id +
                ", sucursalOrigen=" + sucursalOrigen +
                ", sucursalDestino=" + sucursalDestino +
                ", tiempoTransito=" + tiempoTransito +
                ", capacidadMaxima=" + capacidadMaxima +
                ", estado=" + estado +
                '}';
    }

    public boolean tiempoTransito_mayorA(String tiempoTransito2) {

        boolean resultado;
        String tiempoTransito1 = this.getTiempoTransito();

        // Extraer las horas y minutos de los horarios de apertura
        int hora1 = Integer.parseInt(tiempoTransito1.substring(0,2));
        int minuto1 = Integer.parseInt(tiempoTransito1.substring(3,5));
        int hora2 = Integer.parseInt(tiempoTransito2.substring(0,2));
        int minuto2 = Integer.parseInt(tiempoTransito2.substring(3,5));

        // Comparar las horas
        if (hora1 > hora2) {
            resultado = true;
        } else if (hora1 < hora2) {
            resultado = false;
        }
        // Si las horas son iguales, comparar los minutos
        else if (minuto1 > minuto2) {
            resultado = true;
        }
        else if (minuto1 < minuto2) {
            resultado = false;
        }
        else{ //son iguales.
            resultado = true;
        }

        return resultado;
    }
    public boolean tiempoTransito_menorA(String tiempoTransito2) {

        boolean resultado;
        String tiempoTransito1 = this.getTiempoTransito();

        // Extraer las horas y minutos de los horarios de apertura
        int hora1 = Integer.parseInt(tiempoTransito1.substring(0,2));
        int minuto1 = Integer.parseInt(tiempoTransito1.substring(3,5));
        int hora2 = Integer.parseInt(tiempoTransito2.substring(0,2));
        int minuto2 = Integer.parseInt(tiempoTransito2.substring(3,5));


        // Comparar las horas
        if (hora1 > hora2) {
            resultado = false;
        } else if (hora1 < hora2) {
            resultado = true;
        }
        // Si las horas son iguales, comparar los minutos
        else if (minuto1 > minuto2) {
            resultado = false;
        }
        else if (minuto1 < minuto2) {
            resultado = true;
        }
        else{ //son iguales.
            resultado = true;
        }

        return resultado;
    }


}