import sun.awt.WindowClosingListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.*;

public class FenetrePseudo extends JFrame{

    private final JTextField pseudoField = new JTextField(10);

    JPanel pan = new JPanel();
    JPanel top = new JPanel();
    JPanel bottom = new JPanel();

    //private PseudoListener Listener;

    public FenetrePseudo() {
        this.setTitle("Choix du Pseudo Utilisateur");
        this.setSize(350, 200);
        this.setLocationRelativeTo(null);


        this.setContentPane(pan);



        JLabel pseudoLabel = new JLabel("Choisissez votre pseudo : ");
        pseudoLabel.setLabelFor(pseudoField);

        JButton button = new JButton("OK");
        button.addActionListener(e->buttonClicked());

        top.add(pseudoLabel);
        top.add(pseudoField);

        bottom.add(button);

        pan.add(top, BorderLayout.NORTH);
        pan.add(bottom, BorderLayout.SOUTH);

        getRootPane().setDefaultButton(button);

        this.setVisible(true);
        this.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent windowEvent) {

            }

            @Override
            public void windowClosing(WindowEvent windowEvent) {

            }

            @Override
            public void windowClosed(WindowEvent windowEvent) {

            }

            @Override
            public void windowIconified(WindowEvent windowEvent) {

            }

            @Override
            public void windowDeiconified(WindowEvent windowEvent) {

            }

            @Override
            public void windowActivated(WindowEvent windowEvent) {

            }

            @Override
            public void windowDeactivated(WindowEvent windowEvent) {
                FenetrePseudo fen;
                if (User.getPseudo()==null)
                    fen = new FenetrePseudo();
                //Main.fenetre.setTitle(User.getPseudo());
            }
        });
    }

    private void buttonClicked(){
        String pseudoAsText = pseudoField.getText();
        System.out.println(pseudoAsText);
        Main.cp.setPseudo_state(false);
        Main.cp.choisir_pseudo(pseudoAsText);
        if(Main.cp.getPseudo_state()) {
            User.setPseudo(pseudoAsText);
            this.dispatchEvent(new WindowEvent(this,WindowEvent.WINDOW_CLOSING));
            if (!(Main.fenetre==null))
                Main.fenetre.setTitle(User.getPseudo());

        }

    }



}