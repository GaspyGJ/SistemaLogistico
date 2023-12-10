package SistemaLogistico.DTO.Interfaces;

import SistemaLogistico.Entidades.Formas.Nodo;
import SistemaLogistico.Entidades.Sucursal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface iNodo extends JpaRepository<Nodo,Long> {
    Optional<Nodo> findBySucursal(Sucursal sucursal);
    Optional<Nodo> deleteBySucursal(Sucursal sucursal);
}