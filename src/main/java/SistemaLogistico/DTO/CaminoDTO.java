package SistemaLogistico.DTO;

import SistemaLogistico.Entidades.Camino;
import SistemaLogistico.Entidades.Sucursal;
import SistemaLogistico.Entidades.Utils.TipoBusqueda;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CaminoDTO{
    @Autowired
    public SistemaLogistico.DTO.Interfaces.iCamino iCamino;

    @Transactional(readOnly = true)
    public List<Camino> getCaminos() {
        return iCamino.findAll();
    }

    @Transactional
    public void saveCamino(Camino cam) {
        cam.setNombre( cam.getSucursalOrigen().getNombre() + "->" + cam.getSucursalDestino().getNombre());
        iCamino.save(cam);
        /*
         Trigger creado en la base de datos para insertar una "linea" asociada por cada camino insertado.
            BEGIN
            INSERT INTO linea_nodos(camino_id)
            VALUES (NEW.id);
            END
        */
    }

    @Transactional
    public void deleteCamino(Camino c) {
        iCamino.delete(c);
        /*
         Trigger creado en la base de datos para insertar una "linea" asociada por cada camino insertado.
            BEGIN
            DELETE FROM linea_nodos WHERE linea_nodos.camino_id = OLD.id;
            END
        */
    }

    @Transactional(readOnly = true)
    public List<Camino> getCaminosFiltradas(String nombre, String pesoMaximo, TipoBusqueda tipoPeso, String tiempoMaximo, TipoBusqueda tipoTiempo) {
        List<Camino> caminos = iCamino.findAll();

        // Filtrar por nombre
        if (nombre != null && !nombre.isEmpty()) {
            caminos = caminos.stream()
                    .filter(c -> c.getNombre().equalsIgnoreCase(nombre))
                    .collect(Collectors.toList());
        }

        // Filtrar por peso
        if (pesoMaximo != null && !pesoMaximo.isEmpty()) {
            if(tipoPeso == TipoBusqueda.MAYOR){
                caminos = caminos.stream()
                        .filter(c -> c.getCapacidadMaxima() >= Double.parseDouble(pesoMaximo))
                        .collect(Collectors.toList());
            }
            else if(tipoPeso == TipoBusqueda.MENOR){
                caminos = caminos.stream()
                        .filter(c -> c.getCapacidadMaxima() <= Double.parseDouble(pesoMaximo))
                        .collect(Collectors.toList());
            }
        }
        // Filtrar por tiempo
        if (tiempoMaximo != null && !tiempoMaximo.isEmpty()) {
            if(tipoTiempo == TipoBusqueda.MAYOR){
                caminos = caminos.stream()
                        .filter(c -> c.tiempoTransito_mayorA(tiempoMaximo))
                        .collect(Collectors.toList());
            }
            else if(tipoTiempo == TipoBusqueda.MENOR){
                caminos = caminos.stream()
                        .filter(c -> c.tiempoTransito_menorA(tiempoMaximo))
                        .collect(Collectors.toList());
            }
        }

        return caminos;
    }

    @Transactional(readOnly = true)
    public Optional<Camino> getCaminoBySucursales(Sucursal origen, Sucursal destino) {
        List<Camino> caminos = iCamino.findAll();
        return caminos.stream().filter(c-> c.getSucursalOrigen().equals(origen) && c.getSucursalDestino().equals(destino)).findFirst();
    }

}
