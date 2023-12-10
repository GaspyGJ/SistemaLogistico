package SistemaLogistico.DTO;

import SistemaLogistico.Entidades.OrdenProvision;
import SistemaLogistico.Entidades.Utils.EstadoOrdenProvision;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrdenProvisionDTO {
    @Autowired
    public SistemaLogistico.DTO.Interfaces.iOrdenProvision iOrdenProvision;

    @Transactional(readOnly = true)
    public List<OrdenProvision> getOrdenesProvision() {return iOrdenProvision.findAll();}

    @Transactional(readOnly = true)
    public List<OrdenProvision> getOrdenesProvisionPendientes() {return iOrdenProvision.findAll().stream()
            .filter(op-> op.getEstado()== EstadoOrdenProvision.PENDIENTE)
            .collect(Collectors.toList());}

    @Transactional(readOnly = true)
    public List<OrdenProvision> getOrdenesProvisionEnProceso() {return iOrdenProvision.findAll().stream()
            .filter(op-> op.getEstado()== EstadoOrdenProvision.EN_PROCESO)
            .collect(Collectors.toList());}

    @Transactional
    public void saveOrdenProvision(OrdenProvision op) {
        iOrdenProvision.save(op);
    }

    @Transactional
    public void deleteOrdenProvision(Long id) {
        iOrdenProvision.deleteById(id);
    }

    @Transactional
    public Optional<OrdenProvision> getOrdenProvisionById(Long id) {
        return  iOrdenProvision.findById(id);
    }

}
