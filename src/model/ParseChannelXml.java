/*
 *  ParseChannelXml.java
 *  David Töyrä (dv15dta)
 *  2019-01-30
 */
package model;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Obtains the information about Sveriges Radio's radio channels.
 * Parses the XMl-file and adds all channel objects to a list.
 */
public class ParseChannelXml
{
    private Document doc;
    private ArrayList<Channel> channelList = new ArrayList<>();

    /**
     * Constructor
     * @throws javax.xml.parsers.ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    public ParseChannelXml() throws javax.xml.parsers.ParserConfigurationException,
            SAXException
            , IOException
    {
        //sätter strängen till adressen för api:n
        String channelURL = "http://api.sr.se/api/v2/channels?&pagination=false";

        //Skapar ny document builder
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        //Skickar in url för api:n till document builder och tar ut ett document som
        //borde bestå av xml med radio info
        this.doc = builder.parse(channelURL);
    }

    /**
     * Constructor made for testing, takes the URL as an argument.
     * @throws javax.xml.parsers.ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    public ParseChannelXml(String xmlfile) throws javax.xml.parsers.ParserConfigurationException,
            SAXException
            , IOException
    {

        //Skapar ny document builder
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        //Skickar in url för api:n till document builder och tar ut ett document som
        //borde bestå av xml med radio info
        this.doc = builder.parse(xmlfile);
    }


    /**
     * Traverse the tree to obtain the "id" and "name" attributes to create a channel.
     * @return
     */
    public ArrayList<Channel> channelParse()
    {
        NodeList channelsList = doc.getElementsByTagName("channel");
        for(int i = 0; i<channelsList.getLength() ; i++)
        {
            Node channelNode = channelsList.item(i);
            if(channelNode.getNodeType() == Node.ELEMENT_NODE)
            {
                Element channelElement = (Element)channelNode;
                int channelID = Integer.parseInt(channelElement.getAttribute("id"));
                String channelName = channelElement.getAttribute("name");
                Channel tempChannel = new Channel(channelID, channelName);
                channelList.add(tempChannel);
            }
        }
        return channelList;
    }
}
