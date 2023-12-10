package SistemaLogistico.Entidades;

import SistemaLogistico.Entidades.Utils.EstadoSucursal;
import jakarta.persistence.*;

import java.util.Objects;


@Entity
@Table(name = "sucursal")
public class Sucursal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "horario_apertura")
    private String horarioApertura;

    @Column(name = "horario_cierre")
    private String horarioCierre;

    @Column(name = "estado")
    private EstadoSucursal estado;

    public Sucursal() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getHorarioApertura() {
        return horarioApertura;
    }

    public void setHorarioApertura(String horarioApertura) {
        this.horarioApertura = horarioApertura;
    }

    public String getHorarioCierre() {
        return horarioCierre;
    }

    public void setHorarioCierre(String horarioCierre) {
        this.horarioCierre = horarioCierre;
    }

    public EstadoSucursal getEstado() {
        return estado;
    }

    public void setEstado(EstadoSucursal estado) {
        this.estado = estado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sucursal sucursal = (Sucursal) o;
        return  Objects.equals(id, sucursal.id) &&
                Objects.equals(nombre, sucursal.nombre) &&
                Objects.equals(horarioApertura, sucursal.horarioApertura) &&
                Objects.equals(horarioCierre, sucursal.horarioCierre) &&
                estado == sucursal.estado;
    }

    @Override
    public String toString() {
        return "Sucursal{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", horarioApertura='" + horarioApertura + '\'' +
                ", horarioCierre='" + horarioCierre + '\'' +
                ", estado=" + estado +
                '}';
    }

    public boolean horaApertura_mayorA(String horarioApertura2) {

        boolean resultado;
        String horarioApertura1 = this.getHorarioApertura();

        // Extraer las horas y minutos de los horarios de apertura
        int hora1 = Integer.parseInt(horarioApertura1.substring(0,2));
        int minuto1 = Integer.parseInt(horarioApertura1.substring(3,5));
        int hora2 = Integer.parseInt(horarioApertura2.substring(0,2));
        int minuto2 = Integer.parseInt(horarioApertura2.substring(3,5));

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
    public boolean horaApertura_menorA(String horarioApertura2) {

        boolean resultado;
        String horarioApertura1 = this.getHorarioApertura();

        // Extraer las horas y minutos de los horarios de apertura
        int hora1 = Integer.parseInt(horarioApertura1.substring(0,2));
        int minuto1 = Integer.parseInt(horarioApertura1.substring(3,5));
        int hora2 = Integer.parseInt(horarioApertura2.substring(0,2));
        int minuto2 = Integer.parseInt(horarioApertura2.substring(3,5));

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
    public boolean horaCierre_mayorA(String horarioCierre2) {

        boolean resultado;
        String horarioCierre1 = this.getHorarioCierre();

        // Extraer las horas y minutos de los horarios de apertura
        int hora1 = Integer.parseInt(horarioCierre1.substring(0,2));
        int minuto1 = Integer.parseInt(horarioCierre1.substring(3,5));
        int hora2 = Integer.parseInt(horarioCierre2.substring(0,2));
        int minuto2 = Integer.parseInt(horarioCierre2.substring(3,5));

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
    public boolean horaCierre_menorA(String horarioCierre2) {

        boolean resultado;
        String horarioCierre1 = this.getHorarioCierre();

        // Extraer las horas y minutos de los horarios de apertura
        int hora1 = Integer.parseInt(horarioCierre1.substring(0,2));
        int minuto1 = Integer.parseInt(horarioCierre1.substring(3,5));
        int hora2 = Integer.parseInt(horarioCierre2.substring(0,2));
        int minuto2 = Integer.parseInt(horarioCierre2.substring(3,5));

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