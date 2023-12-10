package UI;

import SistemaLogistico.Calculador.Calculador;
import SistemaLogistico.DTO.*;
import SistemaLogistico.Entidades.*;
import SistemaLogistico.Entidades.Utils.EstadoCamino;
import SistemaLogistico.Entidades.Utils.EstadoOrdenProvision;
import SistemaLogistico.Entidades.Utils.EstadoSucursal;
import SistemaLogistico.Entidades.Utils.TipoBusqueda;
import UI.Diseño.Styles;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.util.*;
import java.util.List;

public class VentanaPrincipal extends JFrame {
    private JPanel panel_Principal;
    private JPanel panel_CustomNodos;
    private JPanel panel_sucursal;
    private JButton button_editarSucursal;
    private JPanel panel_datoSucursal;
    private JLabel label_estado;
    private JTable table_sucursalCaminos;
    private JPanel panel_camino;
    private JPanel panel_datoCamino;
    private JTable table_caminosSucursal;
    private JButton button_habilitarSucursal;
    private JButton button_eliminarSucursal;
    private JButton button_eliminarCamino;
    private JButton button_editarCamino;
    private JButton button_habilitarCamino;
    private JPanel panel_botones1;
    private JPanel panel_botones_nuevo;
    private JPanel panel_botones2;
    private JSplitPane splitPanel;
    private JLabel label_logoUTN_sucursal;
    private JTextField textField_busquedaS_nombre;
    private JRadioButton radio_horarioCierre_mayor;
    private JButton button_buscarSucursal;
    private JPanel panel_botones_buscarSucursal;
    private JButton button_nuevaSucursal;
    private JButton button_nuevoCamino;
    private JPanel panel_busqueda;
    private JTextField textField_busquedaTiempoMaximo;
    private JTextField textField_busquedaPesoMaximo;
    private JTextField textField_bsuquedaC_nombre;
    private JPanel panel_botones_buscarCamino;
    private JRadioButton radio_horarioApertura_mayor;
    private JRadioButton radio_horarioApertura_menor;
    private JRadioButton radio_horarioCierre_menor;
    private JRadioButton radio_pesoMaximo_mayor;
    private JRadioButton radio_pesoMaximo_menor;
    private JRadioButton radio_tiempoMaximo_mayor;
    private JRadioButton radio_tiempoMaximo_menor;
    private JButton button_buscarCamino;
    private JLabel label_LogoUTN_camino;
    private JButton button_limpiarBusqueda1;
    private JButton button_limpiarBusqueda2;
    private JLabel label_caminoEstado;
    private JLabel label_caminoTiempo;
    private JLabel label_caminoPeso;
    private JLabel label_Camino;
    private JButton button_stockProducto;
    private JLabel label_nombre;
    private JLabel label_horarioApertura;
    private JLabel label_horarioCierre;
    private JLabel label_Sucursal;
    private JLabel label_caminosSalientes;
    private JTable table_stockProductos;
    private JButton button_crearOrden;
    private JTextField textField_cantidadStock;
    private JButton button_actualizarStock;
    private JComboBox comboBox_producto;
    private JPanel panel_botones_stockProductos;
    private JPanel panel_stockProductos;
    private JButton button_ordenesProvision;
    private JButton button_pageRank;
    private JTree tree_ordenesDeProvision;
    private JButton asignarCaminoButton;
    private JPanel panel_ordenesPendientes;
    private JTable table_caminosPosibles;
    private JButton button_flujoMaximo;
    private JLabel label_caminoNombre;
    private JComboBox comboBox_busquedaHorarioCierre;
    private JComboBox comboBox_busquedaHorarioApertura;
    private PanelNodos panelNodos;

    private Optional<Sucursal> sucursalSeleccionada = Optional.empty() ;

    private Optional<Camino> caminoSeleccionado= Optional.empty() ;

    private Optional<OrdenProvision> ordenProvisionSeleccionado= Optional.empty();

    private boolean verStockProductos;

    public VentanaPrincipal() {
        super("Sistema Gestion Logistico");

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setContentPane(this.panel_Principal);

        panelNodos = new PanelNodos(this);
        this.panel_CustomNodos.setLayout(new BorderLayout());
        this.panel_CustomNodos.add(panelNodos);

        this.panel_camino.setVisible(false);
        this.panel_sucursal.setVisible(true);
        this.panel_stockProductos.setVisible(false);
        this.panel_ordenesPendientes.setVisible(false);

        this.verStockProductos=false;

        this.splitPanel.setDividerLocation(850);

        this.setSize(1250, 900);

        this.setLocationRelativeTo(null);

        cargarImagenUTN();

        radioButtonsAgrupar();
        styleButtons();
        styleLabels();
        styleTextFields();
        styleComboBox();
        inicializarComboBoxBusqueda();

        this.setVisible(true);

        createListeners();


    }

    private void inicializarComboBoxBusqueda() {
        String[] horarios = Calculador.generarHorarios();

        DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>();
        for (String h: horarios) {
            comboBoxModel.addElement(h);
        }
        comboBox_busquedaHorarioApertura.setModel(comboBoxModel);

        DefaultComboBoxModel<String> comboBoxModel2 = new DefaultComboBoxModel<>();
        for (String h: horarios) {
            comboBoxModel2.addElement(h);
        }
        comboBox_busquedaHorarioCierre.setModel(comboBoxModel2);

        comboBox_busquedaHorarioApertura.setSelectedIndex(-1);
        comboBox_busquedaHorarioCierre.setSelectedIndex(-1);
    }

    private void radioButtonsAgrupar(){
        ButtonGroup buttonGroup_horarioApertura = new ButtonGroup();
        buttonGroup_horarioApertura.add(radio_horarioApertura_mayor);
        buttonGroup_horarioApertura.add(radio_horarioApertura_menor);

        ButtonGroup buttonGroup_horarioCierre= new ButtonGroup();
        buttonGroup_horarioCierre.add(radio_horarioCierre_mayor);
        buttonGroup_horarioCierre.add(radio_horarioCierre_menor);

        ButtonGroup buttonGroup_tiempoMaximo = new ButtonGroup();
        buttonGroup_tiempoMaximo.add(radio_tiempoMaximo_mayor);
        buttonGroup_tiempoMaximo.add(radio_tiempoMaximo_menor);

        ButtonGroup buttonGroup_pesoMaximo = new ButtonGroup();
        buttonGroup_pesoMaximo.add(radio_pesoMaximo_mayor);
        buttonGroup_pesoMaximo.add(radio_pesoMaximo_menor);

    }
    private void cargarImagenUTN() {
        ClassLoader classLoader = getClass().getClassLoader();
        URL imageURL = classLoader.getResource("Imagenes/logoUTN.png");
        if (imageURL != null) {
            ImageIcon imageIcon = new ImageIcon(imageURL);
            Image image = imageIcon.getImage().getScaledInstance(300, 200, Image.SCALE_SMOOTH);
            ImageIcon resizedImageIcon = new ImageIcon(image);
            label_logoUTN_sucursal.setIcon(resizedImageIcon);
            label_LogoUTN_camino.setIcon(resizedImageIcon);
        } else {
            System.out.println("No se encontró la imagen");
        }

    }
    private void styleButtons(){
        Styles.estilizar_buttons_pequenio(this.panel_botones_nuevo.getComponents());
        Styles.estilizar_buttons_pequenio(this.panel_botones_buscarSucursal.getComponents());
        Styles.estilizar_buttons_pequenio(this.panel_botones_buscarSucursal.getComponents());
        Styles.estilizar_buttons_pequenio(this.panel_botones_buscarCamino.getComponents());
        Styles.estilizar_buttons_pequenio(this.panel_botones1.getComponents());
        Styles.estilizar_buttons_pequenio(this.panel_botones2.getComponents());
        Styles.estilizar_buttons_pequenio(this.panel_botones_stockProductos.getComponents());
    }
    private void styleTextFields() {
        Component[] components = {this.textField_busquedaS_nombre,this.textField_busquedaTiempoMaximo,this.textField_busquedaPesoMaximo,
                this.textField_bsuquedaC_nombre, this.textField_cantidadStock};
        Styles.estilizar_textField(components);
    }
    private void styleLabels(){
        Styles.estilizar_labels_bold(this.panel_datoSucursal.getComponents());
        Styles.estilizar_labels_bold(this.panel_datoCamino.getComponents());
    }
    private void styleComboBox(){
            Component[] comboBoxes = {this.comboBox_busquedaHorarioApertura,this.comboBox_busquedaHorarioCierre};
            Styles.estilizar_comboBox(comboBoxes);
    }

    private void createListeners(){
        createListenersSeccionCamino();
        createListenersSeccionSucursal();
        createListenersSeccionBusqueda();
        createListenersSeccionStockProductos();
        createListenersSeccionOrdenesPendientes();

        button_pageRank.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PageRank ventana_pageRank = new PageRank();
            }
        });

        button_flujoMaximo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    JOptionPane.showMessageDialog(null,
                            "El flujo maximo es : " +Calculador.flujoMaximo() +" kg",
                            "Flujo maximo",JOptionPane.INFORMATION_MESSAGE);


                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null,ex.getMessage(), "Error interno", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        button_ordenesProvision.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setPanelDatosOrdenes();
            }
        });

    }
    private void createListenersSeccionBusqueda(){
        button_buscarSucursal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String nombre = textField_busquedaS_nombre.getText();
                String horarioApertura = (String) comboBox_busquedaHorarioApertura.getSelectedItem();
                String horarioCierre = (String) comboBox_busquedaHorarioCierre.getSelectedItem();
                TipoBusqueda tipoApertura = TipoBusqueda.MAYOR; //por defecto busca mayor
                TipoBusqueda tipoCierre = TipoBusqueda.MAYOR; //por defecto busca mayor

                if(nombre.isBlank() && (horarioCierre==null || horarioCierre.isBlank()) && ( horarioApertura==null || horarioApertura.isBlank()) ){
                    JOptionPane.showMessageDialog(null,"Agrege al menos un filtro.", "Sin Filtro", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                if(horarioApertura != null && !horarioApertura.isBlank()){
                    if(radio_horarioApertura_mayor.isSelected() ){
                        tipoApertura = TipoBusqueda.MAYOR;
                    }
                    else if( radio_horarioApertura_menor.isSelected()){
                        tipoApertura = TipoBusqueda.MENOR;
                    }
                    else if(horarioApertura.length()!=5 ){
                        JOptionPane.showMessageDialog(null, "Datos ingresados no validos.", "Error de datos", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    else{
                        JOptionPane.showMessageDialog(null,"Colocar mayor o menor en los filtros","Error",JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }


                if(horarioCierre!=null && !horarioCierre.isBlank()){
                    if(radio_horarioCierre_mayor.isSelected() ){
                        tipoCierre = TipoBusqueda.MAYOR;
                    }
                    else if( radio_horarioCierre_menor.isSelected()){
                        tipoCierre = TipoBusqueda.MENOR;
                    }
                    else if(horarioCierre.length()!=5 ){
                        JOptionPane.showMessageDialog(null, "Datos ingresados no validos.", "Error de datos", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    else{
                        JOptionPane.showMessageDialog(null,"Colocar mayor o menor en los filtros","Error",JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }

                List<Sucursal> sucursalesFiltradas = Creator.getDTO(SucursalDTO.class).getSucursalesFiltradas(nombre,horarioApertura,tipoApertura,horarioCierre,tipoCierre);
                if(sucursalesFiltradas.isEmpty()){
                    JOptionPane.showMessageDialog(null,"No se encontraron coincidencias","Que mala suerte",JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                panelNodos.mostrarBusquedaSucursal(sucursalesFiltradas);
            }
        });

        button_buscarCamino.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String nombre = textField_bsuquedaC_nombre.getText();
                String pesoMaximo = textField_busquedaPesoMaximo.getText();
                String tiempoMaximo = textField_busquedaTiempoMaximo.getText();
                TipoBusqueda tipoPeso = TipoBusqueda.MAYOR;
                TipoBusqueda tipoTiempo= TipoBusqueda.MAYOR;


                if(nombre.isBlank() && pesoMaximo.isBlank() && tiempoMaximo.isBlank() ){
                    JOptionPane.showMessageDialog(null,"Agrege al menos un filtro.", "Sin Filtro", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                if(!pesoMaximo.isBlank()){
                    if( radio_pesoMaximo_mayor.isSelected()){
                        tipoPeso = TipoBusqueda.MAYOR;
                    }
                    else if(radio_pesoMaximo_menor.isSelected()){
                        tipoPeso = TipoBusqueda.MENOR;
                    }
                    else if(tiempoMaximo.length()!=5 ){
                        JOptionPane.showMessageDialog(null, "Datos ingresados no validos.", "Error de datos", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    else{
                        JOptionPane.showMessageDialog(null,"Colocar mayor o menor en los filtros","Error",JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }

                if(!tiempoMaximo.isBlank()){
                    if(radio_tiempoMaximo_mayor.isSelected()){
                        tipoTiempo = TipoBusqueda.MAYOR;
                    }
                    else if(radio_tiempoMaximo_menor.isSelected()){
                        tipoTiempo = TipoBusqueda.MENOR;
                    }
                    else{
                        JOptionPane.showMessageDialog(null,"Colocar mayor o menor en los filtros","Error",JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }

                List<Camino> caminosFiltrados = Creator.getDTO(CaminoDTO.class).getCaminosFiltradas(nombre,pesoMaximo,tipoPeso,tiempoMaximo,tipoTiempo);

                if(caminosFiltrados.isEmpty()){
                    JOptionPane.showMessageDialog(null,"No se encontraron coincidencias","Que mala suerte",JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                panelNodos.mostrarBusquedaCamino(caminosFiltrados);
            }
        });

        button_limpiarBusqueda1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelNodos.inicializar();
                textField_busquedaS_nombre.setText("");
                comboBox_busquedaHorarioApertura.setSelectedIndex(-1);
                comboBox_busquedaHorarioCierre.setSelectedIndex(-1);
                radio_horarioCierre_menor.setSelected(false);
                radio_horarioCierre_mayor.setSelected(false);
                radio_horarioApertura_menor.setSelected(false);
                radio_horarioApertura_mayor.setSelected(false);
            }
        });

        button_limpiarBusqueda2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelNodos.inicializar();
                textField_busquedaPesoMaximo.setText("");
                textField_bsuquedaC_nombre.setText("");
                textField_busquedaTiempoMaximo.setText("");
                radio_tiempoMaximo_mayor.setSelected(false);
                radio_tiempoMaximo_menor.setSelected(false);
                radio_pesoMaximo_mayor.setSelected(false);
                radio_pesoMaximo_menor.setSelected(false);
            }
        });

        textField_busquedaS_nombre.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if( textField_busquedaS_nombre.getText().length()>50){
                    e.consume();
                }
            }
        });
        textField_bsuquedaC_nombre.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if( textField_bsuquedaC_nombre.getText().length()>50){
                    e.consume();
                }
            }
        });
        textField_busquedaPesoMaximo.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if( textField_busquedaPesoMaximo.getText().length()>4){
                    e.consume();
                }
                else if ((!Character.isDigit(c) && c != '.') || ( c == '.' && textField_busquedaPesoMaximo.getText().contains("."))){
                    e.consume();
                }
            }
        });
        textField_busquedaTiempoMaximo.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if(textField_busquedaTiempoMaximo.getText().length()==2 && c != KeyEvent.VK_BACK_SPACE){
                    textField_busquedaTiempoMaximo.setText(textField_busquedaTiempoMaximo.getText()+":");
                }
                if (!Character.isDigit(c) || textField_busquedaTiempoMaximo.getText().length()>4) {
                    e.consume();
                }
            }
        });
        textField_cantidadStock.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c)){
                    e.consume();
                }
            }
        });

    }
    private void createListenersSeccionSucursal(){

        button_eliminarSucursal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(sucursalSeleccionada.isEmpty()){
                    //sucursal is NULL
                    JOptionPane.showMessageDialog(null,"Primero seleccione una sucursal.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int eleccion = JOptionPane.showConfirmDialog(null, "Seguro que quiere eliminar la sucursal "+sucursalSeleccionada.get().getNombre()+"?",
                        "Eliminar sucursal", JOptionPane.YES_NO_OPTION);
                if(eleccion!=0) return;

                Creator.getDTO(SucursalDTO.class).deleteSucursal(sucursalSeleccionada.get());

                //Reseteo los datos del panel de datos de sucursal
                sucursalSeleccionada = Optional.empty();
                setPanelDatosSucursal(sucursalSeleccionada);

                panelNodos.actualizar();
            }
        });

        button_nuevaSucursal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NuevaSucursal ventana_nuevaSucursal = new NuevaSucursal();
                ventana_nuevaSucursal.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        panelNodos.actualizar();
                    }
                });
            }

        });

        button_editarSucursal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(sucursalSeleccionada.isEmpty()){
                    //sucursal is NULL
                    JOptionPane.showMessageDialog(null,"Primero seleccione una sucursal.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                EditarSucursal ventana_editarSucursal = new EditarSucursal(sucursalSeleccionada.get());
                ventana_editarSucursal.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        panelNodos.actualizar();

                    }
                });
            }
        });

        button_habilitarSucursal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(sucursalSeleccionada.isEmpty()){
                    //sucursal is NULL
                    JOptionPane.showMessageDialog(null,"Primero seleccione una sucursal.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if(sucursalSeleccionada.get().getEstado() == EstadoSucursal.OPERATIVA){
                    sucursalSeleccionada.get().setEstado(EstadoSucursal.NO_OPERATIVA);
                    label_estado.setText("Estado: "+EstadoSucursal.NO_OPERATIVA);
                    button_habilitarSucursal.setText("Habilitar");
                }
                else {
                    sucursalSeleccionada.get().setEstado(EstadoSucursal.OPERATIVA);
                    label_estado.setText("Estado: "+EstadoSucursal.OPERATIVA);
                    button_habilitarSucursal.setText("Deshabilitar");
                }
                Creator.getDTO(SucursalDTO.class).saveSucursal(sucursalSeleccionada.get());

                panelNodos.actualizar();
            }
        });

        button_stockProducto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (sucursalSeleccionada.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Primero seleccione una sucursal.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if(verStockProductos==true){
                    panel_stockProductos.setVisible(false);
                    verStockProductos=false;
                    return;
                }
                String[] columnNames = {"Producto", "Stock"};
                DefaultTableModel tableModel = new DefaultTableModel(new Object[0][0], columnNames) {
                    @Override
                    public boolean isCellEditable(int row, int column) {
                        return false; // Ninguna columna es editable.
                    }
                };

                for (StockProducto sp : Creator.getDTO(StockProductoDTO.class).getStockProductosBySucursal(sucursalSeleccionada.get()) ) {
                    tableModel.addRow(new Object[]{sp.getProducto().getNombre(), sp.getCantidad()});
                }

                table_stockProductos.setModel(tableModel);
                Styles.estilizar_tabla(table_stockProductos);

                DefaultComboBoxModel<String> comboBoxModelProducto = new DefaultComboBoxModel<>();
                for (Producto p : Creator.getDTO(ProductoDTO.class).getProductos()) {
                    comboBoxModelProducto.addElement(p.getNombre());
                }
                comboBox_producto.setModel(comboBoxModelProducto);
                Styles.estilizar_comboBox(new Component[]{comboBox_producto});

                panel_stockProductos.setVisible(true);
                verStockProductos=true;

            }
        });
    }
    private void createListenersSeccionCamino(){
        button_habilitarCamino.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(caminoSeleccionado.isEmpty()){
                    //sucursal is NULL
                    JOptionPane.showMessageDialog(null,"Primero seleccione un camino.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if(caminoSeleccionado.get().getEstado() == EstadoCamino.OPERATIVO){
                    caminoSeleccionado.get().setEstado(EstadoCamino.NO_OPERATIVO);
                    button_habilitarSucursal.setText("Habilitar");
                }
                else {
                    caminoSeleccionado.get().setEstado(EstadoCamino.OPERATIVO);
                    button_habilitarCamino.setText("Deshabilitar");
                }
                Creator.getDTO(CaminoDTO.class).saveCamino(caminoSeleccionado.get());

                setPanelDatosCamino(caminoSeleccionado);

                panelNodos.actualizar();
            }
        });

        button_eliminarCamino.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(caminoSeleccionado.isEmpty()){
                    //sucursal is NULL
                    JOptionPane.showMessageDialog(null,"Primero seleccione un camino.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int eleccion = JOptionPane.showConfirmDialog(null, "Seguro que quiere eliminar el camino ?",
                        "Eliminar camino", JOptionPane.YES_NO_OPTION);

                if(eleccion!=0) return;

                Creator.getDTO(CaminoDTO.class).deleteCamino(caminoSeleccionado.get());

                panelNodos.actualizar();
                caminoSeleccionado = Optional.empty();
                setPanelDatosCamino(caminoSeleccionado);
            }
        });

        button_editarCamino.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(caminoSeleccionado.isEmpty()){
                    //sucursal is NULL
                    JOptionPane.showMessageDialog(null,"Primero seleccione un camino.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                EditarCamino ventana_editarCamino = new EditarCamino(caminoSeleccionado.get());
                ventana_editarCamino.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        panelNodos.actualizar();
                    }
                });
            }
        });

        button_nuevoCamino.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NuevoCamino ventana_nuevoCamino = new NuevoCamino();
                ventana_nuevoCamino.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        panelNodos.actualizar();
                    }
                });
            }
        });
    }
    private void createListenersSeccionStockProductos(){

        button_actualizarStock.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(sucursalSeleccionada.isEmpty()){
                    //sucursal is NULL
                    JOptionPane.showMessageDialog(null,"No se pudo actualizar los productos, seleccione una sucursal.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String nombreProducto = comboBox_producto.getSelectedItem().toString();
                String cantidadStock = textField_cantidadStock.getText();

                if(nombreProducto.isBlank() || cantidadStock.isBlank()){
                    JOptionPane.showMessageDialog(null,"Campo vacio no valido.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                DefaultTableModel model = (DefaultTableModel) table_stockProductos.getModel();
                List<StockProducto> stockProductos_sucursal = Creator.getDTO(StockProductoDTO.class).getStockProductosBySucursal(sucursalSeleccionada.get());


                //me fijo si es un nuevo stock o una actualizacion.
                for (int row = 0; row < model.getRowCount(); row++) {
                    String nombreProductoTabla = model.getValueAt(row, 0).toString();
                    if (nombreProductoTabla.equals(nombreProducto)) {
                        model.setValueAt(cantidadStock, row, 1); // Actualizar el valor de stock en la columna correspondiente
                        model.fireTableCellUpdated(row, 1); // Notificar que se ha actualizado la celda

                        stockProductos_sucursal.stream()
                                .filter( sp -> sp.getProducto().getNombre().equals(nombreProducto))
                                .forEach(sp->{
                                    sp.setCantidad(Integer.parseInt(cantidadStock));
                                    Creator.getDTO(StockProductoDTO.class).saveStockProducto(sp);
                                });

                        return;
                    }
                }

                //Si no se encontro (no return) antes, debo crear Stock Producto y asignarselo a la sucursal
                StockProducto sp = new StockProducto();

                Optional<Producto>  producto = Creator.getDTO(ProductoDTO.class).getProductos().stream().filter(p -> p.getNombre().equals(nombreProducto)).findFirst();
                if(producto.isEmpty()){
                    JOptionPane.showMessageDialog(null,"No se encontro el producto en la base de datos", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                sp.setProducto(producto.get());
                sp.setCantidad(Integer.parseInt(cantidadStock));
                sp.setSucursal(sucursalSeleccionada.get());

                //guardo el stock en la bbdd
                Creator.getDTO(StockProductoDTO.class).saveStockProducto(sp);

                //ACTUALIZAR LA TABLA
                model.addRow(new Object[]{nombreProducto,cantidadStock});
                model.fireTableDataChanged();
            }
        });

        button_crearOrden.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(sucursalSeleccionada.isEmpty()){
                    //sucursal is NULL
                    JOptionPane.showMessageDialog(null,"No se pudo actualizar los productos, seleccione una sucursal.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                NuevaOrdenDeProvision ventana_nuevaOrdenProvision = new NuevaOrdenDeProvision(sucursalSeleccionada.get());
                ventana_nuevaOrdenProvision.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        //.. nothing
                    }
                });
            }
        });

        textField_cantidadStock.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if ( (!Character.isDigit(c)) || ( textField_cantidadStock.getText().length()>4)){
                    e.consume();
                }
            }
        });

    }
    private void createListenersSeccionOrdenesPendientes(){
        tree_ordenesDeProvision.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree_ordenesDeProvision.getLastSelectedPathComponent();
                if (selectedNode == null) {
                    return;
                }
                int profundidad = selectedNode.getLevel();
                if(profundidad!=2){return;}

                setPanelCaminosOrdenesPendientes();
            }
        });

        table_caminosPosibles.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                String[] nombresDelCamino = new String[0];

                if (!e.getValueIsAdjusting()) {
                    int selectedIndex = table_caminosPosibles.getSelectedRow();
                    if (selectedIndex == -1) {
                        return;
                    }
                    String caminoSeleccionado = (String) table_caminosPosibles.getValueAt(selectedIndex,0);
                    nombresDelCamino = caminoSeleccionado.split(" > ");
                }
                List<Sucursal> sucursales = Creator.getDTO(SucursalDTO.class).getSucursales();
                List<Sucursal> caminoSucursales = new ArrayList<>();

                for (String nombre : nombresDelCamino) {
                    if(nombre.equals("FIN")) break;
                    for (Sucursal sucursal : sucursales) {
                        if (sucursal.getNombre().equals(nombre.trim())) {
                            caminoSucursales.add(sucursal);
                        }
                    }
                }
                panelNodos.pintarCamino(caminoSucursales);
            }
        });

        asignarCaminoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(ordenProvisionSeleccionado.isEmpty()){
                    JOptionPane.showMessageDialog(null,"Seleccione una orden de provision","Oups",JOptionPane.WARNING_MESSAGE);
                    return;
                }

                int selectedIndex = table_caminosPosibles.getSelectedRow();
                if (selectedIndex == -1) {
                    JOptionPane.showMessageDialog(null,"Seleccione un camino","Oups",JOptionPane.WARNING_MESSAGE);
                    return;
                }
                String caminoSeleccionado = (String) table_caminosPosibles.getValueAt(table_caminosPosibles.getSelectedRow(),0);
                ordenProvisionSeleccionado.get().setCaminoAsignado(caminoSeleccionado);
                ordenProvisionSeleccionado.get().setEstado(EstadoOrdenProvision.EN_PROCESO);
                Creator.getDTO(OrdenProvisionDTO.class).saveOrdenProvision(ordenProvisionSeleccionado.get());
                ordenProvisionSeleccionado = Optional.empty();

                //limpio la tabla --> @@@@ NO FUNCIONA, NO BORRA LOS ELEMENTOS NOSE PORQUE.
                DefaultTableModel model = (DefaultTableModel) table_caminosPosibles.getModel();
                for(int i=0; i<table_sucursalCaminos.getRowCount();i++){
                    model.removeRow(i);
                }
                model.fireTableDataChanged();

                //limpio el grafo
                panelNodos.inicializar();
                JOptionPane.showMessageDialog(null,"Camino Asignado","Exito",JOptionPane.INFORMATION_MESSAGE);
            }
        });

    }

    public void setPanelDatosSucursal(@NotNull Optional<Sucursal> sucursal) {

        caminoSeleccionado = Optional.empty();
        ordenProvisionSeleccionado = Optional.empty();

        if(sucursal.isEmpty()){ // --> se elimino la sucursal que se habia seleccionado
            label_nombre.setText("Nombre: " + "\t");
            label_estado.setText("Estado: " + "\t");
            label_horarioApertura.setText("Horario apertura: " + "\t");
            label_horarioCierre.setText("Horario cierre: " + "\t");

            DefaultTableModel model = (DefaultTableModel) this.table_sucursalCaminos.getModel();
            for(int i=0; i<this.table_sucursalCaminos.getRowCount();i++){
                model.removeRow(i);
            }
            model.fireTableDataChanged();

            return;
        }

        this.sucursalSeleccionada = sucursal;
        Sucursal s = sucursal.get();
        this.panel_camino.setVisible(false);
        this.panel_sucursal.setVisible(true);
        this.panel_stockProductos.setVisible(false);
        this.panel_ordenesPendientes.setVisible(false);

        label_nombre.setText("Nombre: " + "\t" + s.getNombre());
        label_estado.setText("Estado: " + "\t" + s.getEstado().toString());
        label_horarioApertura.setText("Horario apertura: " + "\t" + s.getHorarioApertura() + " hs");
        label_horarioCierre.setText("Horario cierre: " + "\t" + s.getHorarioCierre() + " hs");

        String[] columnNames = {"Destino", "Estado"};

        List<Object[]> rowData = new ArrayList<>();

        //Necesito los caminos asociados a un NODO

        Creator.getDTO(CaminoDTO.class).getCaminos().
                stream().
                filter(c-> Objects.equals(c.getSucursalOrigen().getId(), s.getId())).
                forEach(c->{
                            rowData.add(new Object[]{c.getSucursalDestino().getNombre(), c.getEstado().toString()});
                        }
                );
        DefaultTableModel tableModel = new DefaultTableModel(rowData.toArray(new Object[0][0]), columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Ninguna columna es editable.
            }
        };

        table_sucursalCaminos.setModel(tableModel);

        Styles.estilizar_tabla(table_sucursalCaminos);

        if(s.getEstado()==EstadoSucursal.OPERATIVA){
            button_habilitarSucursal.setText("Deshabilitar");
        }
        else { button_habilitarSucursal.setText("Habilitar");}

    }
    public void setPanelDatosCamino(@NotNull Optional<Camino> camino) {

        sucursalSeleccionada = Optional.empty();
        ordenProvisionSeleccionado = Optional.empty();

        if(camino.isEmpty()){ // --> se elimino la sucursal que se habia seleccionado
            label_caminoNombre.setText("Nombre: " + "\t");
            label_caminoEstado.setText("Estado: " + "\t");
            label_caminoPeso.setText("Peso maximo: " + "\t");
            label_caminoTiempo.setText("Tiempo maximo: " + "\t");

            DefaultTableModel model = (DefaultTableModel) this.table_caminosSucursal.getModel();
            for(int i=0; i<this.table_caminosSucursal.getRowCount();i++){
                model.removeRow(i);
            }
            model.fireTableDataChanged();
            return;
        }

        this.caminoSeleccionado = camino;
        Camino c = camino.get();

        this.panel_sucursal.setVisible(false);
        this.panel_camino.setVisible(true);
        this.panel_ordenesPendientes.setVisible(false);

        label_caminoNombre.setText("Nombre: " + "\t" + c.getNombre());
        label_caminoEstado.setText("Estado: " + "\t" + c.getEstado().toString());
        label_caminoPeso.setText("Peso maximo: " + "\t" + c.getCapacidadMaxima() + " kg");
        label_caminoTiempo.setText("Tiempo maximo: " + "\t" + c.getTiempoTransito() + " hrs");

        String[] columnNames = {"Origen", "Destino", "Estado"};

        List<Object[]> rowData = new ArrayList<>();

        //Necesito los caminos asociados a un NODO
        rowData.add(new Object[]{c.getSucursalOrigen().getNombre(),c.getSucursalDestino().getNombre(), c.getEstado().toString()});
        DefaultTableModel tableModel = new DefaultTableModel(rowData.toArray(new Object[0][0]), columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Ninguna columna es editable.
            }
        };

        table_caminosSucursal.setModel(tableModel);

        Styles.estilizar_tabla(table_caminosSucursal);

        if(c.getEstado()==EstadoCamino.OPERATIVO){
            button_habilitarCamino.setText("Deshabilitar");
        }
        else { button_habilitarCamino.setText("Habilitar");}

    }
    private void setPanelDatosOrdenes() {
        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("Ordenes de Provision");
        DefaultTreeModel treeModel = new DefaultTreeModel(rootNode);
        tree_ordenesDeProvision.setModel(treeModel);
        setDatosOrdenesEnProceso();
        setDatosOrdenesPendientes();

        tree_ordenesDeProvision.expandRow(0);
        tree_ordenesDeProvision.expandRow(2);
    }
    private void setDatosOrdenesPendientes() {

        sucursalSeleccionada = Optional.empty();
        caminoSeleccionado = Optional.empty();

        panel_camino.setVisible(false);
        panel_sucursal.setVisible(false);

        List<OrdenProvision> ordenesPendientes = Creator.getDTO(OrdenProvisionDTO.class).getOrdenesProvisionPendientes();

        DefaultMutableTreeNode nodoPendientes = new DefaultMutableTreeNode("Pendientes");
        ((DefaultMutableTreeNode)tree_ordenesDeProvision.getModel().getRoot()).add(nodoPendientes);

        for (OrdenProvision ordenProvision: ordenesPendientes) {
            DefaultMutableTreeNode nodoHijo = new DefaultMutableTreeNode(ordenProvision.getId());
            nodoPendientes.add(nodoHijo);

            String[] tiempoTransito = ordenProvision.getTiempoMaximo().split(":");
            Integer horaMaxima = Integer.parseInt(tiempoTransito[0]);
            Integer minutoMaximo = Integer.parseInt(tiempoTransito[1]);
            Integer[] tiempoMaximo = Calculador.generateDuracion(horaMaxima, minutoMaximo);
            String tiempoFinal=  tiempoMaximo[0] + " dias " + tiempoMaximo[1] + " hrs " + tiempoMaximo[2] + " min";

            nodoHijo.add(new DefaultMutableTreeNode("Destino "+ordenProvision.getSucursalDestino().getNombre()));
            nodoHijo.add(new DefaultMutableTreeNode("Tiempo de entrega maximo: "+ tiempoFinal));

            DefaultMutableTreeNode items = new DefaultMutableTreeNode("Productos");
            nodoHijo.add(items);

            for (ItemOrdenProvision item: ordenProvision.getItems()) {
                DefaultMutableTreeNode nodoNieto = new DefaultMutableTreeNode(item.getProducto().getNombre() +" --- "+item.getCantidad()+" unidades");
                items.add(nodoNieto);
            }

        }
        panel_ordenesPendientes.setVisible(true);
    }
    private void setDatosOrdenesEnProceso() {

        List<OrdenProvision> ordenesEnProceso = Creator.getDTO(OrdenProvisionDTO.class).getOrdenesProvisionEnProceso();

        DefaultMutableTreeNode nodoEnProceso = new DefaultMutableTreeNode("En proceso");
        ((DefaultMutableTreeNode)tree_ordenesDeProvision.getModel().getRoot()).add(nodoEnProceso);

        for (OrdenProvision ordenProvision: ordenesEnProceso) {
            DefaultMutableTreeNode nodoHijo = new DefaultMutableTreeNode(ordenProvision.getId());
            nodoEnProceso.add(nodoHijo);

            String[] tiempoTransito = ordenProvision.getTiempoMaximo().split(":");
            Integer horaMaxima = Integer.parseInt(tiempoTransito[0]);
            Integer minutoMaximo = Integer.parseInt(tiempoTransito[1]);
            Integer[] tiempoMaximo = Calculador.generateDuracion(horaMaxima, minutoMaximo);
            String tiempoFinal=  tiempoMaximo[0] + " dias " + tiempoMaximo[1] + " hrs " + tiempoMaximo[2] + " min";

            nodoHijo.add(new DefaultMutableTreeNode("Destino "+ordenProvision.getSucursalDestino().getNombre()));
            nodoHijo.add(new DefaultMutableTreeNode("Tiempo de entrega maximo: "+ tiempoFinal));
            nodoHijo.add(new DefaultMutableTreeNode("Camino asignado: " + ordenProvision.getCaminoAsignado()));

            DefaultMutableTreeNode items = new DefaultMutableTreeNode("Productos");
            nodoHijo.add(items);

            for (ItemOrdenProvision item: ordenProvision.getItems()) {
                DefaultMutableTreeNode nodoNieto = new DefaultMutableTreeNode(item.getProducto().getNombre() +" --- "+item.getCantidad()+" unidades");
                items.add(nodoNieto);
            }

        }
        panel_ordenesPendientes.setVisible(true);
    }
    private void setPanelCaminosOrdenesPendientes() {

        // Obtengo el el nodo de orden pendiente seleccionado
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree_ordenesDeProvision.getLastSelectedPathComponent();
        if (selectedNode == null) {return;}
        if (selectedNode.getUserObject().equals("Ordenes Pendientes")){return;}

        // Obtener el contenido del nodo seleccionado
        Object selectedNodeContent = selectedNode.getUserObject();
        ordenProvisionSeleccionado = Creator.getDTO(OrdenProvisionDTO.class)
                .getOrdenProvisionById(Long.parseLong(selectedNodeContent.toString()));

        if(ordenProvisionSeleccionado.isEmpty()){
            JOptionPane.showMessageDialog(null,"No se encontro la orden","Error",JOptionPane.ERROR_MESSAGE);
            return;
        }

        List<List<Sucursal>> caminos = Calculador.caminosParaOrdenDeProvision(ordenProvisionSeleccionado.get());

        if(caminos.size()==0){
            JOptionPane.showMessageDialog(null,"No se encontraron caminos disponibles","Oups",JOptionPane.WARNING_MESSAGE);
            return;
        }
        String[] columnNames = {"Camino","Tiempo estimado"};
        DefaultTableModel tableModel = new DefaultTableModel(new Object[0][0],columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (List<Sucursal> sucursales : caminos) {

            StringBuilder label = new StringBuilder();
            Integer horas = 0;
            Integer minutos = 0;
            Integer[] tiempoEstimado= {};

            for (int i = 0; i < sucursales.size() - 1; i++) {

                label.append(sucursales.get(i).getNombre()).append("  >  ");

                Optional<Camino> camino = Creator.getDTO(CaminoDTO.class).getCaminoBySucursales(sucursales.get(i), sucursales.get(i + 1));

                if(camino.isEmpty()){
                    JOptionPane.showMessageDialog(null, "Error al buscar camino", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                // formato  HH:MM  ejemplo  05:45  cinco horas y 45 minutos
                String[] tiempoTransito = camino.get().getTiempoTransito().split(":");
                horas += Integer.parseInt(tiempoTransito[0]);
                minutos += Integer.parseInt(tiempoTransito[1]);
            }
            label.append(sucursales.get(sucursales.size()-1).getNombre());//sucursal destino.
            tiempoEstimado = Calculador.generateDuracion(horas, minutos);

            //agregar elemento a la tabla
            String[] fila = {String.valueOf(label), tiempoEstimado[0] + " dias " + tiempoEstimado[1] + " hrs " + tiempoEstimado[2] + " min"};
            tableModel.addRow(fila);

        }

        table_caminosPosibles.setModel(tableModel);
        table_caminosPosibles.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        Styles.estilizar_tabla_simple(table_caminosPosibles);

    }

}

