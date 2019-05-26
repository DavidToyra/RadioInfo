/*
 *  RadioModel.java
 *  David Töyrä (dv15dta)
 *  2019-01-30
 */
package model;

import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * Main model class. Parses the the channel and episodes add the
 * parsed objects to lists.
 */
public class RadioModel
{
    private ArrayList<RadioProgram> programList = new ArrayList<>();

    public ArrayList<RadioProgram> getProgramList() {
        return programList;
    }

    public ArrayList<Channel> getChannelList() {
        return channelList;
    }

    private ArrayList<Channel> channelList = new ArrayList<>();

    /**
     * Contstructor for radiomodel.
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    public RadioModel()
    {

    }

    public void createRadioModel() throws ParserConfigurationException, SAXException, IOException {
        parseChannelInfo(channelList);
        parseScheduleInfo(programList, channelList);
        for(Channel ch: channelList)
        {
            ch.makeChannelSpecificList(programList);
        }
        sortProgramList(programList);

    }

    /**
     * Sorts the programlist according to each program's start time in ascending order.
     * @param programs list of programs
     * @return
     */
    public ArrayList<RadioProgram> sortProgramList(ArrayList<RadioProgram> programs)
    {
        programs.sort(new Comparator<RadioProgram>() {
            @Override
            public int compare(RadioProgram o1, RadioProgram o2) {
                if(o1.getStartTime().equals(o2.getStartTime()))
                    return 0;
                return o1.getStartTime().isBefore(o2.getStartTime()) ? -1 : 1;
            }
        });

        return programs;
    }

    /**
     * Gets channel information from Sveriges Radio and parses the XML-file
     * and adds the objects to a list.
     * @param channelList
     * @throws IOException
     * @throws SAXException
     * @throws ParserConfigurationException
     */
    public void parseChannelInfo(ArrayList<Channel> channelList)
            throws IOException, SAXException, ParserConfigurationException
    {
        ParseChannelXml channelParser;

        channelParser = new ParseChannelXml();

        if (channelParser != null)
        {
            channelList.addAll(channelParser.channelParse());
        }
    }

    /**
     * Get information of the episodes from Sveriges Radio, parses the XML-file
     * and adds ojbects to a list.
     * @param programList
     * @param channelList
     * @throws IOException
     * @throws SAXException
     * @throws ParserConfigurationException
     */
    public void parseScheduleInfo(ArrayList<RadioProgram> programList,
                                  ArrayList<Channel> channelList)
            throws IOException, SAXException, ParserConfigurationException
    {
        ParseScheduleXml parser = null;

            for(Channel ch : channelList)
            {
                parser = new ParseScheduleXml(ch.getChannelID());
                programList.addAll(parser.getProgramList());
            }
    }
}
