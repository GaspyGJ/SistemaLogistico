package UI;

import SistemaLogistico.DTO.*;
import SistemaLogistico.Entidades.Camino;
import SistemaLogistico.Entidades.Formas.Linea;
import SistemaLogistico.Entidades.Formas.Nodo;
import SistemaLogistico.Entidades.Sucursal;
import SistemaLogistico.Entidades.Utils.EstadoCamino;
import SistemaLogistico.Entidades.Utils.EstadoSucursal;
import UI.Diseño.Colores;
import UI.Diseño.Styles;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Ellipse2D;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class PanelNodos extends JPanel {

    public Optional<Nodo> nodoSeleccionado;
    List<Nodo> nodos;
    List<Linea> lineas;

    private final Container padre;

    public PanelNodos(Container padre) {

        this.padre = padre;

        setBackground(Colores.gris);

        nodoSeleccionado= Optional.empty();

        nodos = Creator.getDTO(NodoDTO.class).getNodos();
        lineas = Creator.getDTO(LineaDTO.class).getLineas();

        inicializar();
        createListeners();
    }

    public void inicializar(){
        for(Nodo n: nodos){
            if(n.getSucursal().getEstado()== EstadoSucursal.OPERATIVA){
                n.setColorRelleno(Colores.verde);
            }
            else{
                n.setColorRelleno(Colores.rojo);
            }
            n.setColorBorde(Colores.negro);
            n.setGrosor(Styles.lapiz_fino);
        }
        for(Linea l: lineas){
            if(l.getCamino().getEstado()== EstadoCamino.OPERATIVO){
                l.setColor(Colores.verde);
            }
            else{
                l.setColor(Colores.rojo);
            }
            l.setGrosor(Styles.lapiz_fino);
        }
        this.repaint();
    }
    public void actualizar(){
        nodoSeleccionado= Optional.empty();
        nodos = Creator.getDTO(NodoDTO.class).getNodos();
        lineas = Creator.getDTO(LineaDTO.class).getLineas();
        this.inicializar();
    }
    public void mostrarBusquedaSucursal(List<Sucursal> sucursalesBusqueda){
        this.inicializar();

        for(Sucursal s : sucursalesBusqueda){
            for (Nodo n: nodos) {
                if(Objects.equals(n.getSucursal().getId(), s.getId())){
                    n.setColorBorde(Colores.rosado);
                    n.setGrosor(Styles.lapiz_grueso);
                    break;
                }
            }
        }
        this.repaint();
    }
    public void mostrarBusquedaCamino(List<Camino> caminosBusqueda){
        this.inicializar();

        for(Camino c : caminosBusqueda){
            for (Linea l: lineas) {
                if(Objects.equals(l.getCamino().getId(), c.getId())){
                    l.setColor(Colores.rosado);
                    l.setGrosor(Styles.lapiz_grueso);
                    break;
                }
            }
        }
        this.repaint();
    }

    public void createListeners(){
        addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                int x = e.getX();
                int y = e.getY();

                if (e.getButton() == MouseEvent.BUTTON1 /*&& e.getClickCount() == 2*/) {
                    //Clic izquierdo sobre un nodo?
                    actualizar();
                    Optional<Nodo> resultado1  =  nodos.stream().filter(n -> n.clicked(x,y) ).findFirst();
                    if(resultado1.isPresent()) {
                        ((VentanaPrincipal) padre).setPanelDatosSucursal(Optional.of(resultado1.get().getSucursal()));
                        resultado1.get().setGrosor(Styles.lapiz_grueso);
                        resultado1.get().setColorBorde(Colores.rosado);
                        repaint();
                    }
                    else {
                        //Clic izquierdo sobre una linea
                        actualizar();
                        Optional<Linea> resultado2  =  lineas.stream().filter(l -> {
                            try {
                                return l.clicked(x,y);
                            } catch (Exception ex) {
                                throw new RuntimeException(ex);
                            }
                        }).findFirst();

                        if(resultado2.isPresent()){
                            ((VentanaPrincipal) padre).setPanelDatosCamino(Optional.of( resultado2.get().getCamino()));
                            resultado2.get().setColor(Colores.rosado);
                            repaint();
                        }

                    }
                }

            }
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                int x = e.getX();
                int y = e.getY();

                if (e.getButton() == MouseEvent.BUTTON1) {
                    //Clic izquierdo sobre un nodo?

                    Optional<Nodo> resultado  =  nodos.stream().filter(n -> n.clicked(x,y) ).findFirst();
                    if(resultado.isPresent()){
                        setCursor(new Cursor( Cursor.HAND_CURSOR));
                        nodoSeleccionado = resultado;
                    }
                }

            }
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                if( nodoSeleccionado.isPresent() ){

                    Creator.getDTO(NodoDTO.class).saveNodo(nodoSeleccionado.get());
                    repaint();
                    nodoSeleccionado = Optional.empty();
                }
                setCursor(new Cursor( Cursor.DEFAULT_CURSOR));
            }
        });
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {

                if(nodoSeleccionado.isEmpty()) return;

                setCursor(new Cursor( Cursor.MOVE_CURSOR));

                int x = e.getX();
                int y = e.getY();

                int x_Panel = getX();
                int y_Panel = getY();

                //si el mouse no esta dentro del panel.. limito al borde del panel.
                if(x<x_Panel){
                    x= x_Panel + ((nodoSeleccionado.get().w)/2);

                } else if ( x>x_Panel+getWidth()) {
                    x= x_Panel+getWidth() - ((nodoSeleccionado.get().w)/2);

                }

                if( y<y_Panel){
                    y= y_Panel + ((nodoSeleccionado.get().h)/2);

                } else if (y>y_Panel+getHeight()) {
                    y= y_Panel+getHeight() - ((nodoSeleccionado.get().h)/2);
                }

                nodoSeleccionado.get().x= (x - (nodoSeleccionado.get().w)/2);
                nodoSeleccionado.get().y= (y - (nodoSeleccionado.get().h)/2);
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        try {
            dibujar(g2d);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void dibujar(Graphics2D g2d) throws Exception{

        g2d.setFont(Styles.fuente_gruesa);

        for (Linea l : lineas) {
            Optional<Nodo> nodoInicial = Creator.getDTO(SucursalDTO.class).getNodoBySucursal(l.getCamino().getSucursalOrigen());
            Optional<Nodo> nodoFinal = Creator.getDTO(SucursalDTO.class).getNodoBySucursal(l.getCamino().getSucursalDestino());

            if (nodoInicial.isEmpty()) throw new Exception("NodoInicial Empty");
            if (nodoFinal.isEmpty()) throw new Exception("NodoFinal Empty");

            int x_i = (nodoInicial.get().x + (nodoInicial.get().w) / 2);
            int y_i = (nodoInicial.get().y + (nodoInicial.get().h) / 2);
            int x_f = (nodoFinal.get().x + (nodoFinal.get().w) / 2);
            int y_f = (nodoFinal.get().y + (nodoFinal.get().h) / 2);

            g2d.setColor(l.getColor());
            g2d.setStroke(l.getGrosor());
            g2d.drawLine(x_i, y_i, x_f, y_f);

            int arrowSize = 10;
            int dx = x_f - x_i;
            int dy = y_f - y_i;
            double angle = Math.atan2(dy, dx);

            int x_medio = (x_i + x_f) / 2;
            int y_medio = (y_i + y_f) / 2;

            // Coordenadas de los puntos de la flecha
            int x1 = x_medio + (int) (arrowSize * Math.cos(angle + Math.toRadians(150)));
            int y1 = y_medio + (int) (arrowSize * Math.sin(angle + Math.toRadians(150)));
            int x2 = x_medio + (int) (arrowSize * Math.cos(angle - Math.toRadians(150)));
            int y2 = y_medio + (int) (arrowSize * Math.sin(angle - Math.toRadians(150)));

            // Dibujar las líneas de la flecha
            g2d.setStroke(Styles.lapiz_fino);
            g2d.setColor(Colores.negro);
            g2d.drawLine(x_medio, y_medio, x1, y1);
            g2d.drawLine(x_medio, y_medio, x2, y2);
        }

        for (Nodo n: nodos) {
            Ellipse2D.Double nodoElipse = new Ellipse2D.Double(n.x,n.y,n.w,n.h);

            g2d.setStroke(n.getGrosor());
            g2d.setColor(n.getColorBorde());
            g2d.draw(nodoElipse);
            g2d.setColor(n.getColorRelleno());
            g2d.fill(nodoElipse);

            g2d.setColor(Colores.negro);
            int x = (n.x + (n.w) / 2)-(n.sucursal.getNombre().length()*5);
            int y = (n.y + (n.h) / 2)+8;
            g2d.drawString(n.sucursal.getNombre(),x,y);

        }
    }

    public void pintarCamino(List<Sucursal> sucursales){

        inicializar();

        // acondiciono los nodos
        for(Nodo n : this.nodos){
            for (Sucursal sucursal: sucursales){
                if(n.getSucursal().equals(sucursal)){
                    n.setColorBorde(Colores.rosado);
                    n.setGrosor(Styles.lapiz_grueso);
                    break;
                }
            }
        }

        for(int i=0 ; i<sucursales.size()-1; i++){
            Optional<Camino> camino = Creator.getDTO(CaminoDTO.class).getCaminoBySucursales(sucursales.get(i),sucursales.get(i+1));

            if(camino.isEmpty()){  return;}

            lineas.stream().filter( l-> l.getCamino().equals(camino.get())).forEach(l->{
                l.setColor(Colores.rosado);
                l.setGrosor(Styles.lapiz_grueso);
            });
        }

        repaint();
    }

}