/*
 *  Controller.java
 *  David Töyrä (dv15dta)
 *  2019-01-30
 */
package controller;

import model.*;
import org.xml.sax.SAXException;
import view.RadioGUI;
import view.RadioMenuBar;
import view.TablePanel;
import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Controller for the radio info program. Creates the model and the GUI
 * and handles all their communication.
 */
public class Controller
{
    public RadioGUI getGui() {
        return gui;
    }

    private RadioGUI gui;
    private RadioMenuBar menuBar;
    private TablePanel tablePanel;
    private JMenuItem  allChannelsItem;
    private JMenuItem refreshButton;
    private ArrayList<RadioProgram> programList = new ArrayList<>();
    private ArrayList<Channel> channelList = new ArrayList<>();
    private JLabel refreshTimeLabel;
    private JList channelJList;


    public void setCurrentChannel(int currentChannel) {
        this.currentChannel = currentChannel;
    }

    private int currentChannel = 0;
    private RadioModel radioModel;

    /**
     * Constructor for the controller
     */
    public Controller()
    {
        //Start the model
        radioModel = new RadioModel();
        this.gui = new RadioGUI();

        try {
            radioModel.createRadioModel();
        } catch (ParserConfigurationException e) {
            gui.exceptionPane(""+e);
        } catch (SAXException e) {
            gui.exceptionPane(""+e);
        } catch (IOException e) {
            gui.exceptionPane(""+e);
        }
        channelList.addAll(radioModel.getChannelList());
        programList.addAll(radioModel.getProgramList());
        gui.addChannelPanel(channelList);

        //Creates Swing components
        TablePanel episodePanel = gui.getTablePanel();
        episodePanel.createTable(programList);
        this.menuBar = gui.getMenuBar();
        this.allChannelsItem = menuBar.getAllChannels();
        this.refreshButton = menuBar.getRefresh();
        refreshTimeLabel =menuBar.getTime();
        tablePanel = gui.getTablePanel();
        JTable table = tablePanel.getEpisodeTable();

        //Set current time on the last update lable.
        refreshTimeLabel.setText("Last update "+LocalDateTime.now().
                format(DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM)));

        gui.showChannels();
        channelJList = gui.getChannelPanel().getChannelJList();
        //Set listeners
        setMenuListeners();

        startAutomaticUpdater();

        System.out.println(SwingUtilities.isEventDispatchThread());
    }

    /**
     * Method to start the update worker.
     */
    public void startAutomaticUpdater()
    {
        AutomaticRefresher refresher = new AutomaticRefresher(this);
        refresher.execute();
    }

    /**
     * Creates the action listeners for the menu items.
     *
     */
    private void setMenuListeners()
    {
        channelJList.addListSelectionListener(new ChannelJlistListener(this,
                channelJList));
        allChannelsItem.addActionListener(new ChannelMenuListener(this));
        refreshButton.addActionListener(e -> {
            try {
                updateSchedule();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
    }

    /**
     * Update routine.
     * Re-parses the schedule API from Sveriges Radio to get an
     * updated list of programs, adds all new listeners, and attempts to
     * update the table panel if any channel is showing.
     */
    protected void updateSchedule() {

        XmlParseWorker worker = new XmlParseWorker(channelList,programList, radioModel, gui);
        worker.execute();

        allChannelsItem.addActionListener(new ChannelMenuListener( this));
        refreshTimeLabel.setText("Last update "+LocalDateTime.now().
                format(DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM)));
        if(currentChannel!=0)
        {
            for(int i = 0; i<channelList.size(); i++)
            {
                if(channelList.get(i).getChannelID() == currentChannel)
                {
                    gui.showTables(channelList.get(i).getChannelProgramList());
                }
            }
        }
    }

}
