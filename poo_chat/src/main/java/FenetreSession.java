import sun.awt.WindowClosingListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.*;


public class FenetreSession extends JFrame{

    private String pseudo_distant;

    private final JTextField keyboardField = new JTextField(20);


    JPanel pan = new JPanel();
    JPanel top = new JPanel();
    JPanel bottom = new JPanel();
    JPanel center = new JPanel();

    //private PseudoListener Listener;
    private final JTextArea messagesField = new JTextArea(20,30);

    public String getPseudo_distant() {
        return pseudo_distant;
    }

    public FenetreSession(String pseudo_cible) {
        this.pseudo_distant=pseudo_cible;
        this.setTitle(pseudo_cible);
        this.setSize(400, 500);
        this.setLocationRelativeTo(null);


        this.setContentPane(pan);
        messagesField.setEditable(false);
        messagesField.setLineWrap(true);
        keyboardField.setEditable(true);
        /*for (Message s : User.){
            messagesField.append(s.getData() + "\n");*/

        JButton button = new JButton("Envoyer");
        button.addActionListener(e->buttonClicked());



        top.add(messagesField);
        bottom.add(keyboardField);
        center.add(button);

        pan.add(top, BorderLayout.NORTH);
        pan.add(bottom, BorderLayout.SOUTH);
        pan.add(center,BorderLayout.CENTER);

        //getRootPane().setDefaultButton(button);

        /*JLabel messagesLabel = new JLabel("Messages envoyés : ");
        messagesLabel.setLabelFor(messagesField);*/


        /*public void Afficher(ArrayList<Message> msg){
            for (Message s : msg){
                messagesField.append(s.getData() + "\n");
            }
        }*/

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
                String pseudo=getPseudo_distant();
                if (User.sessions.containsKey(pseudo)){
                    User.sessions.remove(pseudo);
                }

            }
        });


        new Thread(){
            @Override
            public void run(){
                while(true){
                    Session sessionperso=User.sessions.get(getFenetreSession().getPseudo_distant());
                    if (sessionperso.getNbNewMessages()>0){
                        addLineChatBox(sessionperso.getMessages().get(sessionperso.getMessages().size()-1).toString());
                        sessionperso.decreaseNbNewMessages();
                    }

                }
            }
        }.start();


    }

    private void buttonClicked(){
        String pseudoAsText = keyboardField.getText();
        System.out.println(pseudoAsText);
        Message msg = new Message(pseudoAsText,false,pseudoAsText.length(),User.getPseudo());
        Main.em.envoyer_message(msg,this.getPseudo_distant());
        messagesField.append(msg.toString());
    }

    private void addLineChatBox (String line){
        messagesField.append("\n");
        messagesField.append(line);
    }


    public FenetreSession getFenetreSession(){
        return this;
    }


}