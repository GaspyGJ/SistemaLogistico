package SistemaLogistico.DTO.Interfaces;

import SistemaLogistico.Entidades.StockProducto;
import SistemaLogistico.Entidades.Sucursal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface iStockProducto extends JpaRepository<StockProducto,Long> {
    List<StockProducto> findBySucursal(Sucursal sucursal);

}



