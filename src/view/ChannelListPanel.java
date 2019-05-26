package view;

import model.Channel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ChannelListPanel extends JPanel
{
    ArrayList<Channel> channelList = new ArrayList<>();

    public JList getChannelJList() {
        return channelJList;
    }

    private JList channelJList;
    public ChannelListPanel(ArrayList<Channel> channelList)
    {
        this.setLayout(new BorderLayout());
        this.channelList.addAll(channelList);
    }

    public void makeChannelJList()
    {
        channelJList = new JList<>(channelList.toArray());

        JScrollPane listSrollPane = new JScrollPane();
        listSrollPane.createVerticalScrollBar();
        listSrollPane.getViewport().add(channelJList);

        this.add(listSrollPane, BorderLayout.CENTER);
        this.setVisible(true);
    }
}
