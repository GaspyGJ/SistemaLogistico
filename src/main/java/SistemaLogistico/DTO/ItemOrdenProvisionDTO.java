package SistemaLogistico.DTO;

import SistemaLogistico.Entidades.ItemOrdenProvision;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ItemOrdenProvisionDTO {

    @Autowired
    public SistemaLogistico.DTO.Interfaces.iItemOrdenPrevision iItemOrdenPrevision;

    @Transactional(readOnly = true)
    public List<ItemOrdenProvision> getItemsOrdenProvision() {
        return iItemOrdenPrevision.findAll();
    }

    @Transactional
    public void saveItemOrdenProvision(ItemOrdenProvision item) {
        iItemOrdenPrevision.save(item);
    }

    @Transactional
    public void deleteItemOrdenProvision(Long id) {
        iItemOrdenPrevision.deleteById(id);
    }

    @Transactional
    public Optional<ItemOrdenProvision> getItemOrdenProvisionById(Long id) {
        return  iItemOrdenPrevision.findById(id);
    }

}
