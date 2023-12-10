package SistemaLogistico.DTO;

import SistemaLogistico.Entidades.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoDTO {
    @Autowired
    public SistemaLogistico.DTO.Interfaces.iProducto iProducto;

    @Transactional(readOnly = true)
    public List<Producto> getProductos() {
        return iProducto.findAll();
    }

    @Transactional
    public void saveProducto(Producto p) {
        iProducto.save(p);
    }

    @Transactional
    public void deleteProducto(Long id) {
        iProducto.deleteById(id);
    }

    @Transactional
    public Optional<Producto> getProductoById(Long id) {
        return  iProducto.findById(id);
    }
}
