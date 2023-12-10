package SistemaLogistico.DTO.Interfaces;

import SistemaLogistico.Entidades.OrdenProvision;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface iOrdenProvision extends JpaRepository<OrdenProvision,Long> { }
