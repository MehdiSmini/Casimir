import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class Fenetre extends JFrame{

    //private final JComboBox<Session> sessionsComboBox = new JComboBox<>(User.sessions.values().toArray(new Session[0]));
    private final JComboBox<String> sessionsComboBox = new JComboBox<>();
    private final JComboBox<String> agentsComboBox = new JComboBox<>();

    JPanel pan = new JPanel();
    JPanel top = new JPanel();
    JPanel center = new JPanel();


    JPanel bottom = new JPanel();

    private ButtonListener listener;


    public Fenetre(){

        this.setTitle(User.getPseudo());
        this.setSize(800, 200);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setContentPane(pan);

        this.setLayout(new GridLayout(1,1));

        JLabel listeSessions = new JLabel("Liste des Sessions : ");
        listeSessions.setLabelFor(sessionsComboBox);

        //top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
        sessionsComboBox.setPreferredSize(new Dimension(220, 25));
        sessionsComboBox.addActionListener(new ComboboxLancerSession());
        agentsComboBox.addActionListener(new ComboBoxNouvelleSession());






        /*Map<String,Session> map = new HashMap<String,Session>();
        map = User.sessions;
        for (Map.Entry<String,Session> e : map.entrySet()) {
            sessionsComboBox.addItem(e.getKey());
        }*/


        JLabel listeAgents = new JLabel("Liste des Agents : ");
        listeAgents.setLabelFor(agentsComboBox);

        agentsComboBox.setPreferredSize(new Dimension(220, 25));

        /*Map<String,Agent> map2 = new HashMap<String,Agent>();
        map2 = User.agents_actifs;
        for (Map.Entry<String,Agent> e : map2.entrySet()) {
            agentsComboBox.addItem(e.getKey());
        }*/

        JButton button = new JButton("Changer Pseudo");
        button.addActionListener(e -> buttonClicked());

        JButton button2 = new JButton("Nouvelle Session");
        button2.addActionListener(e -> button2Clicked());



        top.add(listeSessions);
        top.add(sessionsComboBox);

        top.add(listeAgents);
        top.add(agentsComboBox);

        center.add(button);
        center.add(button2);



        pan.add(top,BorderLayout.NORTH);
        pan.add(center,BorderLayout.CENTER);
        pan.add(bottom, BorderLayout.SOUTH);

        this.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent windowEvent) {

            }

            @Override
            public void windowClosing(WindowEvent windowEvent) {

            }

            @Override
            public void windowClosed(WindowEvent windowEvent) {
                Main.mr.broadcast_udp_packet("d"+User.getPseudo()+User.getPort());
                Main.lr.setRunning(false);
                Main.setFinished(true);
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


            }
        });

        this.setVisible(true);
    }

    private void buttonClicked(){
        FenetrePseudo fen = new FenetrePseudo();
        //Main.cp.changerPseudo();
        Main.fenetre.setTitle(User.getPseudo());
    }

    private void button2Clicked(){
        System.out.println("button2Clicked declenche");
        String pseud = (String) agentsComboBox.getSelectedItem();
        Main.ds.demande_session(pseud);
    }

    /*public void Afficher(ArrayList<Message> msg){
        for (Message s : msg){
            messagesField.append(s.getData() + "\n");
        }
    }*/



    public void addToSessionBox(String pseudo){
        sessionsComboBox.addItem(pseudo);
    }

    public void addToAgentsBox(String pseudo){
        agentsComboBox.addItem(pseudo);
    }

    public void removeFromSessionBox(String pseudo){
        sessionsComboBox.removeItem(pseudo);
    }

    public void removeFromAgentBox(String pseudo){
        agentsComboBox.removeItem(pseudo);
    }

    public void setListener(ButtonListener listener) {this.listener = listener;}



}