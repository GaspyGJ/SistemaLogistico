package SistemaLogistico.DTO;

import SistemaLogistico.Entidades.Formas.Nodo;
import SistemaLogistico.Entidades.Sucursal;
import SistemaLogistico.Entidades.Utils.TipoBusqueda;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SucursalDTO{
    @Autowired
    public SistemaLogistico.DTO.Interfaces.iSucursal iSucursal;

    @Transactional(readOnly = true)
    public List<Sucursal> getSucursales() {return iSucursal.findAll();}

    @Transactional
    public void saveSucursal(Sucursal s) {
        iSucursal.save(s);
        /*
        En base de datos se creo un trigger para creacion del nodo asociado.
            BEGIN
            INSERT INTO nodo_sucursal (h, w, x, y, sucursal_id) VALUES (40,40,40,40, NEW.id);
            END
        */
    }

    @Transactional
        public void deleteSucursal(Sucursal s) {

        iSucursal.delete(s);
        /*
         En base de datos se creo un trigger para eliminacion de nodo, caminos y lineas asociados a la sucursal eliminada.
            BEGIN
            DELETE FROM camino WHERE camino.sucursal_origen_id = OLD.id;
            DELETE FROM camino WHERE camino.sucursal_destino_id = OLD.id;
            DELETE FROM nodo_sucursal WHERE nodo_sucursal.sucursal_id = OLD.id;
            DELETE FROM stock_producto WHERE stock_producto.sucursal_id = OLD.id;
            DELETE FROM orden_provision WHERE orden_provision.sucursalDestino_id= OLD.id;
            END
        */

    }

    @Transactional
    public Optional<Sucursal> getSucursalById(Long id) {
        return  iSucursal.findById(id);
    }

    @Transactional(readOnly = true)
    public Optional<Nodo> getNodoBySucursal(Sucursal s){
        return  Creator.getDTO(NodoDTO.class).getNodoBySucursal(s);
    }


    @Transactional(readOnly = true)
    public List<Sucursal> getSucursalesFiltradas(String nombre, String horarioApertura, TipoBusqueda tipoApertura, String horarioCierre, TipoBusqueda tipoCierre) {
        List<Sucursal> sucursales = iSucursal.findAll();

        // Filtrar por nombre
        if (nombre != null && !nombre.isEmpty()) {
            sucursales = sucursales.stream()
                    .filter(s -> s.getNombre().equalsIgnoreCase(nombre))
                    .collect(Collectors.toList());
        }
        // Filtrar por horario de apertura
        if (horarioApertura != null && !horarioApertura.isEmpty()) {
            if(tipoApertura == TipoBusqueda.MAYOR){
                sucursales = sucursales.stream()
                        .filter(s -> s.horaApertura_mayorA(horarioApertura))
                        .collect(Collectors.toList());
            }
            else if(tipoApertura == TipoBusqueda.MENOR){
                sucursales = sucursales.stream()
                        .filter(s -> s.horaApertura_menorA(horarioApertura))
                        .collect(Collectors.toList());
            }
        }
        // Filtrar por horario de cierre
        if (horarioCierre != null && !horarioCierre.isEmpty()) {
            if(tipoCierre == TipoBusqueda.MAYOR){
                sucursales = sucursales.stream()
                        .filter(s -> s.horaCierre_mayorA(horarioCierre))
                        .collect(Collectors.toList());
            }
            else if(tipoCierre == TipoBusqueda.MENOR){
                sucursales = sucursales.stream()
                        .filter(s -> s.horaCierre_menorA(horarioCierre))
                        .collect(Collectors.toList());
            }
        }

        return sucursales;
    }

    @Transactional(readOnly = true)
    public  Optional<Sucursal> getFuente() {
        return iSucursal.findAll().stream().filter(s-> s.getNombre().equals("Puerto")).findFirst();
    }

}
