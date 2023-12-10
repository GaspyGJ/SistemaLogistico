package SistemaLogistico.DTO;

import SistemaLogistico.Entidades.Formas.Nodo;
import SistemaLogistico.Entidades.Sucursal;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class NodoDTO{
    @Autowired
    public SistemaLogistico.DTO.Interfaces.iNodo iNodo;

    @Transactional(readOnly = true)
    public List<Nodo> getNodos() {
        return iNodo.findAll();
    }

    @Transactional
    public void saveNodo(Nodo nod) {
        iNodo.save(nod);
    }

    @Transactional
    public void deleteNodo(Long id) {
        iNodo.deleteById(id);
    }

    @Transactional
    public Optional<Nodo> getNodoById(Long id) {
        return  iNodo.findById(id);
    }

    @Transactional
    public Optional<Nodo> getNodoBySucursal(Sucursal sucursal) {
        return iNodo.findBySucursal(sucursal);
    }

    @Transactional
    public Optional<Nodo> deleteBySucursal(Sucursal sucursal) {
        return iNodo.deleteBySucursal(sucursal);
    }

}
