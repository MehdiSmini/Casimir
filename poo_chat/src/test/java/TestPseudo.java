import java.util.*;
import java.io.*;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Before;
import org.junit.Test;


public class TestPseudo {
    public String pseudo;
    public LirePseudo lp = new LirePseudo();


    @Test
    public void test1(){
        pseudo = lp.lire_pseudo();
        System.out.println(pseudo);

    }

}
