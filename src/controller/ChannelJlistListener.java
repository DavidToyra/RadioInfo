/*
 *  ChannelJListListener.java
 *  David Töyrä (dv15dta)
 *  2019-01-30
 */
package controller;
import model.Channel;
import view.RadioGUI;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


/**
 * Listener for the channel jlist. When a channel is selected
 * show it's programs on the table panel.
 */
public class ChannelJlistListener implements ListSelectionListener
{
    private JList channelList;
    private RadioGUI gui;
    private Controller controller;

    /**
     * Constructor for the jlist listener.
     * @param controller main controller
     * @param channelList list of radio channels
     */
    public ChannelJlistListener(Controller controller, JList channelList)
    {
        this.channelList = channelList;
        this.controller = controller;
        this.gui = controller.getGui();
    }
    @Override
    public void valueChanged(ListSelectionEvent e)
    {
        if(!e.getValueIsAdjusting() && channelList.getSelectedIndex()>=0)
        {
            Channel channel = (Channel)channelList.getSelectedValue();
            gui.showTables(channel.getChannelProgramList());
            channelList.clearSelection();
            controller.setCurrentChannel(channel.getChannelID());
        }
    }
}
