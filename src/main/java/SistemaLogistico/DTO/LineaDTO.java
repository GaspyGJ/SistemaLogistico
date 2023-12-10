package SistemaLogistico.DTO;

import SistemaLogistico.Entidades.Formas.Linea;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class LineaDTO{
    @Autowired
    public SistemaLogistico.DTO.Interfaces.iLinea iLinea;

    @Transactional(readOnly = true)
    public List<Linea> getLineas() {
        return iLinea.findAll();
    }

    @Transactional
    public void saveLinea(Linea lin) {
        iLinea.save(lin);
    }

    @Transactional
    public void deleteLinea(Long id) {
        iLinea.deleteById(id);
    }

    @Transactional
    public Optional<Linea> getLineaById(Long id) {
        return  iLinea.findById(id);
    }

}
