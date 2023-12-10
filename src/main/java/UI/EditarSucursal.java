package UI;

import SistemaLogistico.Calculador.Calculador;
import SistemaLogistico.DTO.CaminoDTO;
import SistemaLogistico.DTO.Creator;
import SistemaLogistico.DTO.SucursalDTO;
import SistemaLogistico.Entidades.Camino;
import SistemaLogistico.Entidades.Sucursal;
import SistemaLogistico.Entidades.Utils.EstadoCamino;
import SistemaLogistico.Entidades.Utils.EstadoSucursal;
import UI.Diseño.Styles;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class EditarSucursal extends JDialog{
    private JTextField textField_nombre;
    private JComboBox comboBox_SucursalDestino;
    private JTextField textField_pesoMaximo;
    private JTextField textField_tiempoMaximo;
    private JPanel panel_botones1;
    private JButton button_agregar;
    private JButton button_eliminar;
    private JTable table_sucursalCaminos;
    private JPanel panel_botones2;
    private JButton button_cancelar;
    private JButton button_modificar;
    private JPanel panel_datosSucursal;
    private JPanel panel_EditarSucursal;
    private JComboBox comboBox_horarioCierre;
    private JComboBox comboBox_horarioApertura;

    private java.util.List<Camino> caminosExistentes;
    private java.util.List<Camino> caminosNuevos;
    private java.util.List<Camino> caminosEliminados;
    private List<Sucursal> sucursales;

    private final Sucursal sucursal;


    public EditarSucursal(Sucursal sucursal){
        super();

        this.caminosExistentes = new ArrayList<>();
        this.caminosNuevos = new ArrayList<>();
        this.caminosEliminados = new ArrayList<>();
        this.sucursales = new ArrayList<>();

        this.sucursal=sucursal;

        inicializateComoboBox();
        inicializateTable();
        inicializateTextFields();
        createListeners();

        styleButtons();
        styleComoBox();
        styleTextField();
        styleTable();

        this.setTitle("Editar Sucursal");
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setContentPane(this.panel_EditarSucursal);
        this.setSize( 400, 500);
        this.setLocationRelativeTo(null);
        this.setModal(true);
        this.setVisible(true);
    }

    private void styleTable() {
        Styles.estilizar_tabla(this.table_sucursalCaminos);
    }
    private void styleButtons() {
        Styles.estilizar_buttons_pequenio(this.panel_botones1.getComponents());
        Styles.estilizar_buttons(this.panel_botones2.getComponents());
    }
    private void styleComoBox() {
        Component[] comboBoxes = {this.comboBox_SucursalDestino,this.comboBox_horarioApertura,this.comboBox_horarioCierre};
        Styles.estilizar_comboBox(comboBoxes);
    }
    private void styleTextField() {
        Component[] textFields = {this.textField_nombre, this.textField_pesoMaximo, this.textField_tiempoMaximo};
        Styles.estilizar_textField(textFields);
    }

    private void inicializateTextFields(){
        this.textField_nombre.setText(this.sucursal.getNombre());
    }
    private void inicializateComoboBox(){
        DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>();
        this.sucursales = Creator.getDTO(SucursalDTO.class).getSucursales();
        for (Sucursal s: sucursales) {
            comboBoxModel.addElement(s.getNombre());
        }
        comboBox_SucursalDestino.setModel(comboBoxModel);


        String[] horarios = Calculador.generarHorarios();

        DefaultComboBoxModel<String> comboBoxModel2 = new DefaultComboBoxModel<>();
        for (String h: horarios) {
            comboBoxModel2.addElement(h);
        }
        comboBoxModel2.setSelectedItem(sucursal.getHorarioApertura());
        comboBox_horarioApertura.setModel(comboBoxModel2);

        DefaultComboBoxModel<String> comboBoxModel3 = new DefaultComboBoxModel<>();
        for (String h: horarios) {
            comboBoxModel3.addElement(h);
        }
        comboBoxModel3.setSelectedItem(sucursal.getHorarioCierre());
        comboBox_horarioCierre.setModel(comboBoxModel3);

    }
    private void inicializateTable(){

        String[] columnNames = {"Destino", "Peso", "Tiempo"};
        DefaultTableModel tableModel = new DefaultTableModel(new Object[0][0], columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Ninguna columna es editable.
            }
        };

        table_sucursalCaminos.setModel(tableModel);

        table_sucursalCaminos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        Styles.estilizar_tabla(table_sucursalCaminos);

        //Relleno con los datos de la sucursal.
        Creator.getDTO(CaminoDTO.class).getCaminos()
                .stream()
                .filter(c-> c.getSucursalOrigen().getId()==this.sucursal.getId())
                .forEach( c->  {
                    caminosExistentes.add(c);
                    ((DefaultTableModel) table_sucursalCaminos
                            .getModel())
                            .addRow(new Object[]{c.getSucursalDestino().getNombre(),c.getCapacidadMaxima(),c.getTiempoTransito()});
                } );
    }
    private void createListeners(){

        createListenersRestrictions();

        button_agregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String sucursal_seleccionada = (String) comboBox_SucursalDestino.getSelectedItem();
                String tiempoMaximo = textField_tiempoMaximo.getText();
                String pesoMaximo = textField_pesoMaximo.getText();

                if( (sucursal_seleccionada!=null && sucursal_seleccionada.isBlank()) || tiempoMaximo.isBlank() || pesoMaximo.isBlank()
                        || tiempoMaximo.length()!=5) {
                    JOptionPane.showMessageDialog(null, "Datos del camino no validos.", "Error de datos", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if(  sucursal.getNombre().equals(sucursal_seleccionada)) {
                    JOptionPane.showMessageDialog(null, "Origen y destino no deben ser iguales", "Error de datos", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                for(Camino c : caminosExistentes){
                    if (c.getSucursalDestino().getNombre().equals(sucursal_seleccionada)) {
                        JOptionPane.showMessageDialog(null, "Ya existe un camino para la sucursal seleccionada.", "Camino existente", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
                for(Camino c : caminosNuevos){
                    if (c.getSucursalDestino().getNombre().equals(sucursal_seleccionada)) {
                        JOptionPane.showMessageDialog(null, "Ya existe un camino para la sucursal seleccionada.", "Camino existente", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }

                ((DefaultTableModel) table_sucursalCaminos.getModel()).addRow(new Object[]{sucursal_seleccionada,pesoMaximo,tiempoMaximo});

                for (Sucursal s: sucursales) {
                    if(s.getNombre().equals(sucursal_seleccionada) ){
                        //Creo los caminos, pero falta agregar la sucursal Destino. (Se hace al aceptar y crear la sucursal)
                        Camino c= new Camino();
                        c.setSucursalDestino(s);
                        c.setEstado(EstadoCamino.OPERATIVO);
                        c.setTiempoTransito(tiempoMaximo);
                        c.setCapacidadMaxima(Double.parseDouble(pesoMaximo));
                        caminosNuevos.add(c);
                        break;
                    }
                }
            }
        });

        button_eliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int filaSeleccionada = table_sucursalCaminos.getSelectedRow();
                if(filaSeleccionada==-1){
                    JOptionPane.showMessageDialog(null,"Seleccione la fila a eliminar.", "Oups", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                String sucursal = (String) table_sucursalCaminos.getValueAt(filaSeleccionada,0);

                for (Camino c: caminosExistentes) {
                    if (c.getSucursalDestino().getNombre().equals(sucursal)) {
                        caminosExistentes.remove(c);
                        caminosEliminados.add(c);
                        break;
                    }
                }
                for (Camino c: caminosNuevos) {
                    if (c.getSucursalDestino().getNombre().equals(sucursal)) {
                        caminosNuevos.remove(c);
                        break;
                    }
                }

                DefaultTableModel model = (DefaultTableModel) table_sucursalCaminos.getModel();
                model.removeRow(filaSeleccionada);
                model.fireTableDataChanged();

            }
        });

        button_modificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String nombre = textField_nombre.getText();
                String horario_apertura = (String) comboBox_horarioApertura.getSelectedItem();
                String horario_cierre = (String) comboBox_horarioCierre.getSelectedItem();

                //@@@ VER MEJOR LAS CONDICIONES
                if(nombre.isBlank() || horario_apertura==null || horario_apertura.isBlank() || horario_apertura.length()!=5
                                    || horario_cierre==null ||horario_cierre.isBlank() ||  horario_cierre.length()!=5){
                    JOptionPane.showMessageDialog(null, "Datos del sucursal no validos.", "Error de datos", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                //CREAR LOS OBJETOS Y ENVIARLOS AL DTO.
                try{
                    sucursal.setEstado(EstadoSucursal.OPERATIVA);
                    sucursal.setNombre(nombre);
                    sucursal.setHorarioApertura(horario_apertura);
                    sucursal.setHorarioCierre(horario_cierre);

                    Creator.getDTO(SucursalDTO.class).saveSucursal(sucursal);

                    for (Camino c: caminosEliminados) {
                        Creator.getDTO(CaminoDTO.class).deleteCamino(c);
                    }

                    for (Camino c: caminosNuevos) {
                        c.setSucursalOrigen(sucursal);
                        Creator.getDTO(CaminoDTO.class).saveCamino(c);
                    }

                    //Caminos existentes quedan como estan.

                    JOptionPane.showMessageDialog(null, "Sucursal actualizada.", "Exito", JOptionPane.INFORMATION_MESSAGE);
                    dispose();

                }catch (Exception exeption){
                    JOptionPane.showMessageDialog(null, "No se pudo actualizar.", "Error en la base de datos", JOptionPane.ERROR_MESSAGE);
                    System.out.println(exeption.getMessage());
                }


            }
        });

        button_cancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                dispose();
            }
        });

    }

    private void createListenersRestrictions() {

        textField_pesoMaximo.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if( textField_pesoMaximo.getText().length() >4 ){
                    e.consume();
                }
                else if ((!Character.isDigit(c) && c != '.') || ( c == '.' && textField_pesoMaximo.getText().contains("."))){
                    e.consume();
                }
            }
        });
        textField_tiempoMaximo.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();

                if(textField_tiempoMaximo.getText().length()==2 && c != KeyEvent.VK_BACK_SPACE){
                    textField_tiempoMaximo.setText(textField_tiempoMaximo.getText()+":");
                }
                if (!Character.isDigit(c) || textField_tiempoMaximo.getText().length()>4) {
                    e.consume();
                }
            }
        });
    }


}
