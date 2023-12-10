package SistemaLogistico.DTO;

import SistemaLogistico.Entidades.StockProducto;
import SistemaLogistico.Entidades.Sucursal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class StockProductoDTO{
    @Autowired
    public SistemaLogistico.DTO.Interfaces.iStockProducto iStockProducto;

    @Transactional(readOnly = true)
    public List<StockProducto> getStockProductos() {
        return iStockProducto.findAll();
    }

    @Transactional
    public void saveStockProducto(StockProducto sp) {
        iStockProducto.save(sp);
    }

    @Transactional
    public void deleteStockProducto(Long id) {
        iStockProducto.deleteById(id);
    }

    @Transactional
    public List<StockProducto> getStockProductosBySucursal(Sucursal sucursal){ return iStockProducto.findBySucursal(sucursal);}

    @Transactional
    public Optional<StockProducto> getStockProductoById(Long id) {
        return  iStockProducto.findById(id);
    }
}
