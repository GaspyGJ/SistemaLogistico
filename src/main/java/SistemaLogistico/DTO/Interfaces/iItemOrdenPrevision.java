package SistemaLogistico.DTO.Interfaces;

import SistemaLogistico.Entidades.ItemOrdenProvision;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface iItemOrdenPrevision extends JpaRepository<ItemOrdenProvision,Long> { }