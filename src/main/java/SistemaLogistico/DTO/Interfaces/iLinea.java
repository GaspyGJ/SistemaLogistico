package SistemaLogistico.DTO.Interfaces;

import SistemaLogistico.Entidades.Formas.Linea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface iLinea extends JpaRepository<Linea,Long> { }