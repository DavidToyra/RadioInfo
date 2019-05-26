package view;

import javax.swing.*;

public class RadioMenuBar extends JMenuBar
{
    private JMenuItem refresh;
    private JMenu options;

    public JLabel getTime() {
        return time;
    }

    private JLabel time;

    public JMenuItem getAllChannels() {
        return allChannels;
    }

    private JMenuItem allChannels;

    public JMenuItem getRefresh() {
        return refresh;
    }






    public RadioMenuBar()
    {

        options = new JMenu("Options");
        time = new JLabel("Time since last refresh: ");
        this.add(time);
        refresh = new JMenuItem("Refresh");
        allChannels = new JMenuItem("Channels");
        options.add(allChannels);
        options.add(refresh);
        this.add(options);

    }
}
