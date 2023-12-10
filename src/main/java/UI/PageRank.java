package UI;

import SistemaLogistico.Calculador.Calculador;
import SistemaLogistico.Entidades.Sucursal;
import UI.Diseño.Styles;

import javax.swing.*;
import java.util.Map;

public class PageRank extends JDialog{
    private JList list_pageRank;
    private JPanel panel1;

    public PageRank(){

        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setContentPane(this.panel1);
        this.setSize( 400, 500);
        this.setLocationRelativeTo(null);
        this.setModal(true);

        DefaultListModel<String> listModel = new DefaultListModel<>();

        Map<Sucursal, Double> pageRanks = Calculador.pageRank();

        pageRanks.forEach((key,value)->{
            String valor =  value.toString().substring(0, 5);
            listModel.addElement(key.getNombre()+" ---- "+ valor);
        });

        list_pageRank.setModel(listModel);

        Styles.estilizar_lista(list_pageRank);

        this.setVisible(true);
    }

}

