/*
 *  ChannelMenuListener.java
 *  David Töyrä (dv15dta)
 *  2019-01-30
 */
package controller;
import view.RadioGUI;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Action listener on the all channels item, shows the
 * channel list panel when clicked.
 */
public class ChannelMenuListener implements ActionListener
{
    private RadioGUI gui;
    private Controller controller;

    /**
     * Constructor for the channel menu listener.
     * @param controller the main controller
     */
    public ChannelMenuListener(Controller controller)
    {

        this.controller = controller;
        this.gui = controller.getGui();
    }

    /**
     * Show the jpanel with tables when clicked.
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e)
    {
        gui.showChannels();
        controller.setCurrentChannel(0);
    }
}
