
import model.ParseScheduleXml;
import model.RadioProgram;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import static org.junit.Assert.*;
public class ParseEpsiodesTest
{
    private String xmlChannelsFile = "channelXml.xml";
    private String episodesFile = "episodeXml.xml";
    String localPath;
    ArrayList<RadioProgram> programs= new ArrayList<>();

    @Test
    public void parseConstructorTest() throws IOException, SAXException, ParserConfigurationException {
        URL loc = this.getClass().getResource(episodesFile);
        localPath = loc.getPath();
        ParseScheduleXml parser = new ParseScheduleXml(localPath);
    }

    @Test
    public void shouldNotAddProgramsOutOfDate() throws IOException, SAXException, ParserConfigurationException {
        URL loc = this.getClass().getResource(episodesFile);
        localPath = loc.getPath();
        ParseScheduleXml parser = new ParseScheduleXml(localPath);
        programs.addAll(parser.getProgramList());
        assertEquals(0, programs.size());
    }

    @Test
    public void shouldNotAddProgramsWrongXmlFormat() throws IOException, SAXException, ParserConfigurationException {
        URL loc = this.getClass().getResource(xmlChannelsFile);
        localPath = loc.getPath();
        ParseScheduleXml parser = new ParseScheduleXml(localPath);
        programs.addAll(parser.getProgramList());
        assertEquals(0, programs.size());
    }

}
