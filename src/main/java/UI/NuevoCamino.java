package UI;

import SistemaLogistico.DTO.CaminoDTO;
import SistemaLogistico.DTO.Creator;
import SistemaLogistico.DTO.SucursalDTO;
import SistemaLogistico.Entidades.Camino;
import SistemaLogistico.Entidades.Sucursal;
import SistemaLogistico.Entidades.Utils.EstadoCamino;
import UI.Diseño.Styles;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class NuevoCamino extends JDialog{
    private JPanel panel1;
    private JComboBox comboBox_sucursalOrigen;
    private JTextField textField_tiempoMaximo;
    private JTextField textField_pesoMaximo;
    private JButton button_crearCamino;
    private JButton button_cancelar;
    private JComboBox comboBox_sucursalDestino;
    private JPanel panel_botones;

    private List<Sucursal> sucursales = new ArrayList<>();

    public NuevoCamino (){
        super();

        inicializateComoboBox();
        createListeners();
        styleButtons();
        styleComoBox();
        styleTextField();

        this.setTitle("Nuevo Camino");
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setContentPane(this.panel1);
        this.setSize( 400, 300);
        this.setLocationRelativeTo(null);
        this.setModal(true);
        this.setVisible(true);
    }


    private void styleButtons() {
        Styles.estilizar_buttons(this.panel_botones.getComponents());
    }

    private void styleTextField() {
        JTextField[] textFields =  {this.textField_pesoMaximo,this.textField_tiempoMaximo};
        Styles.estilizar_textField(textFields);
    }

    private void styleComoBox() {
        JComboBox[] comboBoxs =  {this.comboBox_sucursalOrigen,this.comboBox_sucursalDestino};
        Styles.estilizar_comboBox(comboBoxs);
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

        button_crearCamino.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String sucursalOrigen = (String) comboBox_sucursalOrigen.getSelectedItem();
                String sucursalDestino = (String) comboBox_sucursalDestino.getSelectedItem();
                String tiempoMaximo = textField_tiempoMaximo.getText();
                String pesoMaximo = textField_pesoMaximo.getText();

                if( (sucursalOrigen!=null && sucursalOrigen.isBlank()) ||
                        (sucursalDestino!=null && sucursalDestino.isBlank()) ||
                        tiempoMaximo.isBlank() ||
                        pesoMaximo.isBlank() ||
                        tiempoMaximo.length()!=5) {
                    JOptionPane.showMessageDialog(null, "Datos del camino no validos.", "Error de datos", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if(sucursalOrigen.equals(sucursalDestino)) {
                    JOptionPane.showMessageDialog(null, "Origen y destino no deben ser iguales", "Error de datos", JOptionPane.ERROR_MESSAGE);
                    return;
                }


                Camino camino = new Camino();

                camino.setCapacidadMaxima(Double.parseDouble(pesoMaximo));
                camino.setTiempoTransito(tiempoMaximo);
                camino.setEstado(EstadoCamino.OPERATIVO);

                for(Sucursal s: sucursales){
                    if(s.getNombre().equals(sucursalOrigen)){
                        camino.setSucursalOrigen(s);
                    }
                }
                for(Sucursal s: sucursales){
                    if(s.getNombre().equals(sucursalDestino)){
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

    private void inicializateComoboBox() {
        DefaultComboBoxModel<String> comboBoxModelOrigen = new DefaultComboBoxModel<>();
        this.sucursales = Creator.getDTO(SucursalDTO.class).getSucursales();
        for (Sucursal s: sucursales) {
            comboBoxModelOrigen.addElement(s.getNombre());
        };
        comboBox_sucursalOrigen.setModel(comboBoxModelOrigen);

        DefaultComboBoxModel<String> comboBoxModelDestino = new DefaultComboBoxModel<>();
        for (Sucursal s: sucursales) {
            comboBoxModelDestino.addElement(s.getNombre());
        };
        comboBox_sucursalDestino.setModel(comboBoxModelDestino);

    }

}
