/*
 *  XmlParseWorker.java
 *  David Töyrä (dv15dta)
 *  2019-01-30
 */
package controller;

import model.Channel;
import model.RadioModel;
import model.RadioProgram;
import org.xml.sax.SAXException;
import view.RadioGUI;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * SwingWorker that is responsible for updating the programlist.
 */
public class XmlParseWorker extends SwingWorker
{
    private ArrayList<Channel> channelList;
    private ArrayList<RadioProgram> programList;
    private RadioModel model;
    private RadioGUI gui;

    /**
     * Constructor for the swingworker
     * @param channelList
     * @param programList
     * @param model
     */
    public XmlParseWorker(ArrayList<Channel> channelList, ArrayList<RadioProgram> programList,
                          RadioModel model, RadioGUI gui)
    {
        this.channelList = channelList;
        this.programList = programList;
        this.model = model;
        this.gui = gui;
    }

    /**
     * Background task, re-parses the episodes xml to get an
     * updated program list.
     * @return list of programs
     */
    @Override
    protected Object doInBackground() throws ParserConfigurationException, SAXException,
            IOException {

        programList.clear();
        model.parseScheduleInfo(programList,channelList);
        for(Channel ch: channelList)
        {
            ch.makeChannelSpecificList(programList);
        }
        return programList;
    }

    @Override
    protected void done() {
        try {
            get();
        } catch (InterruptedException e) {
            gui.exceptionPane("" + e);
        } catch (ExecutionException e) {
            gui.exceptionPane("" + e);
        }
    }

}
