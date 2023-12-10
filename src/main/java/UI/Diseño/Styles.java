package UI.Diseño;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class Styles {

    public final static Font fuente_gruesa = new Font(Font.SANS_SERIF, Font.BOLD, 20);
    public final static BasicStroke lapiz_fino = new  BasicStroke(2.5f);
    public final static BasicStroke lapiz_grueso = new  BasicStroke(6f);

    public static void estilizar_buttons(Component[] botones){
        Font fuente = new Font("Arial Rounded", Font.BOLD, 14);
        Border borde = BorderFactory.createEmptyBorder(10, 20, 10, 20);
        for(Component boton: botones){
            if (boton instanceof JButton) {
                boton.setBackground(Color.BLUE);
                boton.setForeground(Color.WHITE);
                boton.setFont(fuente);
                ((JButton)boton).setFocusPainted(false);
                ((JButton)boton).setBorder(borde);
                boton.setPreferredSize(new Dimension(-1, 6));
            }
        }
    }
    public static void estilizar_buttons_pequenio(Component[] botones){
        Font fuente = new Font("Arial Rounded", Font.BOLD, 12);
        Border borde = BorderFactory.createEmptyBorder(10, 20, 10, 20);
        for(Component boton: botones){
            if (boton instanceof JButton) {
                boton.setBackground(Color.BLUE);
                boton.setForeground(Color.WHITE);
                boton.setFont(fuente);
                ((JButton)boton).setFocusPainted(false);
                ((JButton)boton).setBorder(borde);
                boton.setPreferredSize(new Dimension(-1, 3));
            }
        }
    }
    public static void estilizar_labels_bold(Component[] labels){
        Font font = new Font("Arial Rounded", Font.BOLD, 16);
        for(Component label: labels) {
            if (label instanceof JLabel) {
                label.setFont(font);
            }
        }
    }
    public static void estilizar_tabla(JTable tabla){

        Font font = new Font("Arial Rounded", Font.BOLD, 14);
        Border border = BorderFactory.createMatteBorder(0, 1, 0, 0, Color.BLACK);

        // Estilos de los encabezados
        JTableHeader tableHeader = tabla.getTableHeader();
        tableHeader.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                ((DefaultTableCellRenderer) component).setHorizontalAlignment(JLabel.CENTER);
                ((DefaultTableCellRenderer) component).setBorder(border);
                component.setPreferredSize(new Dimension(100, 25));
                component.setFont(font);

                component.setBackground(Color.GRAY);
                component.setForeground(Color.WHITE);
                return component;
            }
        });

        // Estilos de las celdas
        tabla.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                cell.setFont(font);
                cell.setBackground(row % 2 == 0 ? Color.WHITE : new Color(230, 230, 230));
                return cell;
            }
        });

        tabla.setRowHeight(20);
    }
    public static void estilizar_tabla_simple (JTable tabla){
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Estilos de los encabezados
        JTableHeader tableHeader = tabla.getTableHeader();
        tableHeader.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                component.setBackground(Color.GRAY);
                component.setForeground(Color.WHITE);
                component.setFont(component.getFont().deriveFont(Font.BOLD));
                return component;
            }
        });

        // Estilos de las celdas
        tabla.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                cell.setBackground(row % 2 == 0 ? Color.WHITE : new Color(230, 230, 230)); // Alternar colores de fila
                return cell;
            }
        });
    }
    public static void estilizar_comboBox(Component[] comboBoxes){
        Font font = new Font("Arial Rounded", Font.PLAIN, 12);
        Border border = BorderFactory.createLineBorder(Color.BLACK);

        for (Component comboBox: comboBoxes) {
            if(comboBox instanceof JComboBox){
                comboBox.setFont(font);
                comboBox.setBackground(Color.WHITE);
                comboBox.setForeground(Color.BLACK);
                ((JComboBox<?>)comboBox).setBorder(border);
                Dimension comboBoxSize = comboBox.getPreferredSize();
                comboBoxSize.height = 20;
                comboBox.setPreferredSize(comboBoxSize);
            }

        }

    }
    public static void estilizar_textField(Component[] textFields){
        Font font = new Font("Arial Rounded", Font.PLAIN, 12);
        Border border = BorderFactory.createLineBorder(Color.BLACK);

        for (Component textField: textFields) {
            if(textField instanceof JTextField){
                textField.setFont(font);
                textField.setBackground( Color.WHITE);
                textField.setForeground(Color.BLACK);
                ((JTextField)textField).setBorder(border);
                Dimension textFieldSize = textField.getPreferredSize();
                textFieldSize.height = 20;
                textField.setPreferredSize(textFieldSize);
            }

        }
    }
    public static void estilizar_lista(JList<String> lista){
        // Clase interna para el renderer personalizado
        lista.setCellRenderer(new DefaultListCellRenderer() {
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                label.setHorizontalAlignment(SwingConstants.LEFT);
                return label;
            }
        });

    }
}


