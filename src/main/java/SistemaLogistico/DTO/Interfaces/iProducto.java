package SistemaLogistico.DTO.Interfaces;

import SistemaLogistico.Entidades.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface iProducto extends JpaRepository<Producto,Long> { }
