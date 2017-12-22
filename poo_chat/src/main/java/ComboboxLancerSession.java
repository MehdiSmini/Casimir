import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ComboboxLancerSession implements ActionListener {


    public void actionPerformed (ActionEvent e){
        JComboBox boxchat = (JComboBox) e.getSource();
        String pseud=(String)boxchat.getSelectedItem();
        FenetreSession fen= new FenetreSession(pseud);



    }












}
