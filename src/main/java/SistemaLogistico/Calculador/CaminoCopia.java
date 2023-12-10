package SistemaLogistico.Calculador;

import SistemaLogistico.Entidades.Sucursal;
import SistemaLogistico.Entidades.Utils.EstadoCamino;

public class CaminoCopia {
        private Sucursal sucursalOrigen;
        private Sucursal sucursalDestino;
        private double capacidadMaxima;
        private double capacidadRestante;
        private String nombre;
        private EstadoCamino estado;

        public CaminoCopia(Sucursal sucursalOrigen, Sucursal sucursalDestino, double capacidadMaxima, double capacidadRestante, String nombre, EstadoCamino estado) {
                this.sucursalOrigen = sucursalOrigen;
                this.sucursalDestino = sucursalDestino;
                this.capacidadMaxima = capacidadMaxima;
                this.capacidadRestante = capacidadRestante;
                this.nombre = nombre;
                this.estado = estado;
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

        public double getCapacidadMaxima() {
                return capacidadMaxima;
        }

        public void setCapacidadMaxima(double capacidadMaxima) {
                this.capacidadMaxima = capacidadMaxima;
        }

        public double getCapacidadRestante() {
                return capacidadRestante;
        }

        public void setCapacidadRestante(double capacidadRestante) {
                this.capacidadRestante = capacidadRestante;
        }

        public String getNombre() {
                return nombre;
        }

        public void setNombre(String nombre) {
                this.nombre = nombre;
        }

        public EstadoCamino getEstado() {
                return estado;
        }

        public void setEstado(EstadoCamino estado) {
                this.estado = estado;
        }
}
