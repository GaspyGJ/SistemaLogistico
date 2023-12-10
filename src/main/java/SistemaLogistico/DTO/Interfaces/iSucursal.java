package SistemaLogistico.DTO.Interfaces;

import SistemaLogistico.Entidades.Sucursal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface iSucursal extends JpaRepository<Sucursal,Long> { }


