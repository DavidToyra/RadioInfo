/*
 *  ParseScheduleXml.java
 *  David Töyrä (dv15dta)
 *  2019-01-30
 */
package model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


/**
 * XML-parser for the schedule information.
 * Obtains an XML file from the Sveriges Radio public URL and
 * parses the objects.
 */
public class ParseScheduleXml
{
    private Document doc;

    public ArrayList<RadioProgram> getProgramList() {
        return programList;
    }

    private ArrayList<RadioProgram> programList = new ArrayList<>();
    private ArrayList<LocalDate> dateList;

    /**
     * Constructor for parser. Builds DOM tree and then parses each
     * element.
     * @param channel channel to get episodes from.
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     */
    public ParseScheduleXml(int channel) throws ParserConfigurationException, IOException,
            SAXException {
        dateList = ParserDates(LocalDate.now());
        for(LocalDate date : dateList)
        {
            String radioProgramXml = "http://api.sr.se/api/v2/scheduledepisodes?channelid=";
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            StringBuilder scheduleXml = new StringBuilder(radioProgramXml);
            scheduleXml.append(channel);
            scheduleXml.append("&date="+ date);
            scheduleXml.append("&pagination=false");
            this.doc = builder.parse(scheduleXml.toString());
            this.programParse();
        }
    }
    /**
     * Constructor for parser testing. Builds DOM tree and then parses each
     * element.
     * @param xml string for the xml file during test.
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     */
    public ParseScheduleXml(String xml) throws ParserConfigurationException, IOException,
            SAXException {
        dateList = ParserDates(LocalDate.now());
        for(LocalDate date : dateList)
        {
            //String radioProgramXml = "http://api.sr.se/api/v2/scheduledepisodes?channelid=";
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            /*StringBuilder scheduleXml = new StringBuilder(xml);
            /*scheduleXml.append(channel);
            scheduleXml.append("&date="+ date);
            scheduleXml.append("&pagination=false");
            */
            this.doc = builder.parse(xml);
            this.programParse();
        }
    }


    /**
     * Gets the dates of yesterday, today, and tomorrow and adds them
     * in a list, that i used to get episodes from all 3 days that
     * could be on the schedule.
     * @param todayDate
     * @return
     */
    private ArrayList<LocalDate> ParserDates(LocalDate todayDate){
        ArrayList<LocalDate> threeDates = new ArrayList<>();
        LocalDate dateToday = todayDate;
        LocalDate dateTomorrow = dateToday.plusDays(1);
        LocalDate dateYesterday = dateToday.minusDays(1);

        threeDates.add(dateYesterday);
        threeDates.add(dateToday);
        threeDates.add(dateTomorrow);
        return threeDates;
    }

    /**
     * Traverse through the tree and create a program object with the relevant elements.
     */
    private void programParse()
    {
        //Create nodelist with scheduledepisode tags
        NodeList episodeList = doc.getElementsByTagName("scheduledepisode");

        //Loop through whole list and check add relevant elements
        for(int i = 0; i<episodeList.getLength(); i++)
        {
            Node episodeNode = episodeList.item(i);
            if(episodeNode.getNodeType() == Node.ELEMENT_NODE)
            {
                Element episodeElement = (Element)episodeNode;
                Element eElementProgram;

                RadioProgram tempProgram;

                eElementProgram = (Element)
                        episodeElement.getElementsByTagName("program").item(0);

                int programID = Integer.parseInt(eElementProgram.getAttribute("id"));
                String name = eElementProgram.getAttribute("name");

                //Parse title
                Node titleNode = episodeElement.getElementsByTagName("title").item(0);
                if (titleNode != null)
                {
                    name = titleNode.getTextContent();
                }
                tempProgram = new RadioProgram(programID, name);

                //Parse description
                Node descriptionNode =
                        episodeElement.getElementsByTagName("description").item(0);
                if (descriptionNode != null){
                    tempProgram.setDescription(descriptionNode.getTextContent());
                }

                //Parse channel id and name
                Element channelElement =
                        (Element)episodeElement.getElementsByTagName("channel").item(0);
                int channelID = Integer.parseInt(channelElement.getAttribute("id"));
                String channelName = channelElement.getAttribute("name");
                tempProgram.setChannelID(channelID);
                tempProgram.setChannelName(channelName);

                /* parse image url, if available */
                Node imageUrlNode =
                        episodeElement.getElementsByTagName("imageurl").item(0);
                if(imageUrlNode != null){
                    tempProgram.setImage(imageUrlNode.getTextContent());
                }
                /* parse start time */
                String start = episodeElement.getElementsByTagName
                        ("starttimeutc").item(0).getTextContent();
                ZonedDateTime timeZone = ZonedDateTime.parse(start,
                        DateTimeFormatter.ISO_INSTANT.withZone(ZoneId.of("UTC")))
                        .withZoneSameInstant(ZoneId.systemDefault());
                LocalDateTime startDate = timeZone.toLocalDateTime();
                start = startDate.toString();

                //Parse end time
                String end = episodeElement.getElementsByTagName
                        ("endtimeutc").item(0).getTextContent();
                ZonedDateTime timeZoneEnd = ZonedDateTime.parse(end,
                        DateTimeFormatter.ISO_INSTANT.withZone
                                (ZoneId.of("UTC"))).withZoneSameInstant(ZoneId.systemDefault());
                LocalDateTime endDate = timeZoneEnd.toLocalDateTime();
                end = endDate.toString();
                tempProgram.setStartTime(startDate);
                tempProgram.setEndTime(endDate);
                if(tempProgram.getStartTime().isBefore(LocalDateTime.now()
                        .minusHours(12)) || tempProgram.getEndTime()
                        .isAfter(LocalDateTime.now().plusHours(12)))
                {
                    continue;
                }
                programList.add(tempProgram);
            }
        }


    }

}
