import model.Channel;
import model.ParseChannelXml;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import static org.junit.Assert.*;

public class ParseChannelsTest
{
    private String xmlChannelsFile = "channelXml.xml";
    private String badXml = "badChannelFormatXml.xml";
    String localPath;
    ArrayList<Channel> channels = new ArrayList<>();

    @Test
    public void parseConstructorTest() throws IOException, SAXException, ParserConfigurationException {
        URL loc = this.getClass().getResource(xmlChannelsFile);
        localPath = loc.getPath();
        ParseChannelXml parser = new ParseChannelXml(localPath);
    }

    @Test(expected = SAXException.class)
    public void parseConstructorTestShouldThrowException() throws IOException, SAXException, ParserConfigurationException {
        URL loc = this.getClass().getResource("emptyChannelXml.xml");
        localPath = loc.getPath();
        ParseChannelXml parser = new ParseChannelXml(localPath);
    }

    @Test
    public void shouldParse10Channels() throws IOException, SAXException, ParserConfigurationException {
        URL loc = this.getClass().getResource(xmlChannelsFile);
        localPath = loc.getPath();
        ParseChannelXml parser = new ParseChannelXml(localPath);
        channels.addAll(parser.channelParse());
        assertEquals(10, channels.size());
    }

    @Test
    public void shouldNotParseChannelsWrongXml() throws IOException, SAXException, ParserConfigurationException {
        URL loc = this.getClass().getResource(badXml);
        localPath = loc.getPath();
        ParseChannelXml parser = new ParseChannelXml(localPath);
        channels.addAll(parser.channelParse());
        assertEquals(0, channels.size());
    }



}
