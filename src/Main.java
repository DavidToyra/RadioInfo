/*
 *  Main.java
 *  David Töyrä (dv15dta)
 *  2019-01-30
 */
import controller.Controller;
import javax.swing.*;


/**
 * Main class for the radio info.
 * Starts the controller on the EDT.
 */
public class Main {

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Controller radioController = new Controller();
            }
        });
    }


}
