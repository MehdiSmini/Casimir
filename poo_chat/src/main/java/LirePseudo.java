import java.util.*;
import java.io.*;

public class LirePseudo {

    private  Scanner Sc = new Scanner(System.in);
    private String pseudo ;

    public String lire_pseudo() {
        ouvrir_fichier("user_pseudo.csv");
        return this.pseudo;
    }

    public void ouvrir_fichier(String chemin){
        String csvFile = chemin;
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";

        try {
            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] file = line.split(cvsSplitBy);

                this.pseudo = file[0];

            }
        } catch(java.io.FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
