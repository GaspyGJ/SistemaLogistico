package UI;

import SistemaLogistico.DTO.CaminoDTO;
import SistemaLogistico.DTO.Creator;
import SistemaLogistico.DTO.SucursalDTO;
import SistemaLogistico.Entidades.Camino;
import SistemaLogistico.Entidades.Sucursal;
import UI.Diseño.Styles;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class EditarCamino extends JDialog {
    private JPanel panel_editarCamino;
    private JComboBox comboBox_sucursalDestino;
    private JTextField textField_pesoMaximo;
    private JTextField textField_tiempoMaximo;
    private JButton button_cancelar;
    private JButton button_actualizar;
    private JPanel panel_botones1;

    private List<Sucursal> sucursales;
    private final Camino camino;

    public EditarCamino (Camino c){
        super();

        this.camino=c;
        this.sucursales= new ArrayList<>();

        inicializateComoboBox();
        inicializateTextFields();
        createListeners();

        styleButtons();
        styleComoBox();
        styleTextField();

        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setContentPane(this.panel_editarCamino);
        this.setSize( 400, 300);
        this.setLocationRelativeTo(null);
        this.setModal(true);
        this.setVisible(true);
    }

    private void styleButtons() {
        Styles.estilizar_buttons(this.panel_botones1.getComponents());
    }
    private void styleComoBox() {
        Component[] comboBoxes = {this.comboBox_sucursalDestino};
        Styles.estilizar_comboBox(comboBoxes);
    }
    private void styleTextField() {
        Component[] textFields = {this.textField_pesoMaximo, this.textField_tiempoMaximo};
        Styles.estilizar_textField(textFields);
    }

    private void createListeners() {
        textField_pesoMaximo.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if(textField_pesoMaximo.getText().length()>4){
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

        button_actualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String sucursal = (String) comboBox_sucursalDestino.getSelectedItem();
                String tiempoMaximo = textField_tiempoMaximo.getText();
                String pesoMaximo = textField_pesoMaximo.getText();

                if( (sucursal!=null && sucursal.isBlank()) || tiempoMaximo.isBlank() || pesoMaximo.isBlank()
                        || tiempoMaximo.length()!=5) {
                    JOptionPane.showMessageDialog(null, "Campos vacio no valido.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if(  camino.getSucursalOrigen().getNombre().equals(sucursal)) {
                    JOptionPane.showMessageDialog(null, "Origen y destino no deben ser iguales", "Error de datos", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                camino.setCapacidadMaxima(Double.parseDouble(pesoMaximo));
                camino.setTiempoTransito(tiempoMaximo);

                for(Sucursal s: sucursales){
                        if(s.getNombre().equals(sucursal)){
                            camino.setSucursalDestino(s);
                        }
                }
                Creator.getDTO(CaminoDTO.class).saveCamino(camino);

                dispose();
            }
        });

        button_cancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    dispose();
            }
        });
    }

    private void inicializateTextFields() {
        this.textField_pesoMaximo.setText(String.valueOf(camino.getCapacidadMaxima()));
        this.textField_tiempoMaximo.setText(camino.getTiempoTransito());
    }

    private void inicializateComoboBox() {
        DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>();
        this.sucursales = Creator.getDTO(SucursalDTO.class).getSucursales();
        for (Sucursal s: sucursales) {
            comboBoxModel.addElement(s.getNombre());
        }
        comboBoxModel.setSelectedItem(camino.getSucursalDestino().getNombre());
        comboBox_sucursalDestino.setModel(comboBoxModel);
    }

}
