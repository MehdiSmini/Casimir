import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ComboBoxNouvelleSession implements ActionListener{

    public void actionPerformed (ActionEvent e){
        JComboBox boxchat = (JComboBox) e.getSource();
        String pseudo_cible = (String) boxchat.getSelectedItem();
        if ("comboBoxSelected".equals(e.getActionCommand()))
            Main.ds.demande_session(pseudo_cible);

    }


}
