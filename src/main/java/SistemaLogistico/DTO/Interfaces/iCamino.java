package SistemaLogistico.DTO.Interfaces;

import SistemaLogistico.Entidades.Camino;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface iCamino extends JpaRepository<Camino,Long> { }
