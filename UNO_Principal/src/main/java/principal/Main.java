package principal;

//@author SAUL ISAAC APODACA BALDENEGRO 00000252020

import MVC_JugarTurno.PantallaTurno;
import javax.swing.SwingUtilities;


public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                PantallaTurno ventana = new PantallaTurno();
                ventana.setVisible(true);
            }
        });
    }

}
