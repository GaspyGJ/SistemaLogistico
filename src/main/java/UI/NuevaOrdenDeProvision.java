package UI;

import SistemaLogistico.DTO.Creator;
import SistemaLogistico.DTO.OrdenProvisionDTO;
import SistemaLogistico.DTO.ProductoDTO;
import SistemaLogistico.DTO.SucursalDTO;
import SistemaLogistico.Entidades.*;
import SistemaLogistico.Entidades.ItemOrdenProvision;
import SistemaLogistico.Entidades.OrdenProvision;
import SistemaLogistico.Entidades.Producto;
import SistemaLogistico.Entidades.Sucursal;
import SistemaLogistico.Entidades.Utils.EstadoOrdenProvision;
import UI.Diseño.Styles;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class NuevaOrdenDeProvision extends JDialog {
    private JPanel panel_botones1;
    private JButton button_agregar;
    private JButton button_eliminar;
    private JTable table_productoCantidad;
    private JTextField textField_tiempoMaximo;
    private JComboBox comboBox_sucursalDestino;
    private JButton button_crearProvision;
    private JButton button_cancelar;
    private JTextField textField_fechaEmision;
    private JPanel panel1;
    private JPanel panel_botones2;
    private JComboBox comboBox_producto;
    private JTextField textField_cantidad;

    //------------------------------------------------------------
    private List<Producto> productos;
    private final Sucursal sucursalDestino;
    private List<ItemOrdenProvision> itemsRequeridos;

    public NuevaOrdenDeProvision(Sucursal s){
        super();

        this.sucursalDestino= s;
        this.itemsRequeridos = new ArrayList<ItemOrdenProvision>();

        inicializateComoboBox();
        inicializateTable();
        inicializateTextField();
        createListeners();
        styleButtons();
        styleComoBox();
        styleTextField();
        styleTable();

        this.setTitle("Nueva orden de provisión");
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setContentPane(this.panel1);
        this.setSize( 500, 500);
        this.setLocationRelativeTo(null);
        this.setModal(true);
        this.setVisible(true);
    }


    private void styleButtons() {
        Styles.estilizar_buttons_pequenio(this.panel_botones1.getComponents());
        Styles.estilizar_buttons_pequenio(this.panel_botones2.getComponents());
    }
    private void styleTextField() {
        JTextField[] textFields =  {this.textField_fechaEmision,this.textField_tiempoMaximo,this.textField_cantidad};
        Styles.estilizar_textField(textFields);
    }
    private void styleComoBox() {
        JComboBox[] comboBoxs =  {this.comboBox_sucursalDestino,this.comboBox_producto};
        Styles.estilizar_comboBox(comboBoxs);
    }
    private void styleTable() {Styles.estilizar_tabla(this.table_productoCantidad);}

    private void inicializateTextField() {
        LocalDate fechaActual = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String fechaFormateada = fechaActual.format(formatter);
        textField_fechaEmision.setText(fechaFormateada);
        textField_fechaEmision.setEditable(false);
    }


    private void inicializateComoboBox() {

        List<Sucursal> sucursales = Creator.getDTO(SucursalDTO.class).getSucursales();

        DefaultComboBoxModel<String> comboBoxModelDestino = new DefaultComboBoxModel<>();
        for (Sucursal s: sucursales) {
            if(s.getNombre().equals(this.sucursalDestino.getNombre())){
                //cambie la logica, asiq solo hace falta que haya una sucursal, pero dejo el combo box por las dudas.
                comboBoxModelDestino.setSelectedItem(s.getNombre());
                comboBoxModelDestino.addElement(s.getNombre());
            }
        }
        comboBox_sucursalDestino.setModel(comboBoxModelDestino);
        //Como es uno solo podria deshabilitarlo.
        //((Component)comboBox_sucursalDestino).setEnabled(false);

        this.productos = Creator.getDTO(ProductoDTO.class).getProductos();
        DefaultComboBoxModel<String> comboBoxModelProductos = new DefaultComboBoxModel<>();
        for (Producto p: productos) {
            comboBoxModelProductos.addElement(p.getNombre());
        }
        comboBox_producto.setModel(comboBoxModelProductos);

    }
    private void inicializateTable(){

        String[] columnNames = {"Producto", "Cantidad"};
        DefaultTableModel tableModel = new DefaultTableModel(new Object[0][0], columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Ninguna columna es editable.
            }
        };

        table_productoCantidad.setModel(tableModel);
    }
    private void createListeners(){

        createListenersRestrictions();

        button_agregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String producto = (String) comboBox_producto.getSelectedItem();
                String cantidad = textField_cantidad.getText();

                if( (producto!=null && producto.isBlank()) || cantidad.isBlank() || cantidad.isBlank()) {
                    JOptionPane.showMessageDialog(null, "Datos de la orden no validos.", "Error de datos", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                for (int i = 0; i < itemsRequeridos.size(); i++) {
                    if (itemsRequeridos.get(i).getProducto().getNombre().equals(producto)) {
                        JOptionPane.showMessageDialog(null, "Ya existe item para el producto seleccionado.", "Item existente", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }

                ((DefaultTableModel) table_productoCantidad.getModel()).addRow(new Object[]{producto,cantidad});

                for (Producto p: productos) {
                    if(p.getNombre().equals(producto) ){
                        ItemOrdenProvision itemOrdenProvision= new ItemOrdenProvision();
                        itemOrdenProvision.setProducto(p);
                        itemOrdenProvision.setCantidad(Integer.parseInt(cantidad));
                        itemsRequeridos.add(itemOrdenProvision);
                        break;
                    }
                }
            }
        });

        button_eliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int filaSeleccionada = table_productoCantidad.getSelectedRow();
                if(filaSeleccionada==-1){
                    JOptionPane.showMessageDialog(null,"Seleccione la fila a eliminar.", "Oups", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                String productoName = (String) table_productoCantidad.getValueAt(filaSeleccionada,0);

                for (int i = 0; i < itemsRequeridos.size(); i++) {
                    if (itemsRequeridos.get(i).getProducto().getNombre().equals(productoName)) {
                        itemsRequeridos.remove(i);
                        break;
                    }
                }

                DefaultTableModel model = (DefaultTableModel) table_productoCantidad.getModel();
                model.removeRow(filaSeleccionada);
                model.fireTableDataChanged();

            }
        });

        button_crearProvision.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String tiempoMaximo = textField_tiempoMaximo.getText();

                String fecha = textField_fechaEmision.getText();

                //String sucursalDestino = comboBox_sucursalDestino.getSelectedItem().toString();

                if(tiempoMaximo.isBlank() || fecha.isBlank()){
                    JOptionPane.showMessageDialog(null,"Campos vacios no son validos.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if(itemsRequeridos.isEmpty()){
                    JOptionPane.showMessageDialog(null,"Agrege items a la orden", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                OrdenProvision ordenProvision = new OrdenProvision();
                ordenProvision.setEstado(EstadoOrdenProvision.PENDIENTE);

                ordenProvision.setTiempoMaximo(tiempoMaximo);
                ordenProvision.setFecha(fecha);
                ordenProvision.setSucursalDestino(sucursalDestino);
                ordenProvision.setItems(itemsRequeridos);

                //falto colocar la orden a los items creados anteriormente.
                for (ItemOrdenProvision item: itemsRequeridos) {
                    item.setOrdenProvision(ordenProvision);
                }

                Creator.getDTO(OrdenProvisionDTO.class).saveOrdenProvision(ordenProvision);
                dispose();
                JOptionPane.showMessageDialog(null,"Se creo la orden correctamente", "Exito", JOptionPane.INFORMATION_MESSAGE);
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
        textField_tiempoMaximo.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (textField_tiempoMaximo.getText().length() == 2 && c != KeyEvent.VK_BACK_SPACE) {
                    textField_tiempoMaximo.setText(textField_tiempoMaximo.getText() + ":");
                }
                if (!Character.isDigit(c) || textField_tiempoMaximo.getText().length() > 4) {
                    e.consume();
                }
            }
        });
        textField_cantidad.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if ((!Character.isDigit(c)) || (textField_cantidad.getText().length() > 4)) {
                    e.consume();
                }
            }
        });
    }
}
