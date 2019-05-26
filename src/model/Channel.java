/*
 *  Channel.java
 *  David Töyrä (dv15dta)
 *  2019-01-30
 */
package model;

import java.util.ArrayList;

public class Channel
{
    private int channelID;
    private String channelName;
    private ArrayList<RadioProgram> channelProgramList = new ArrayList<>();

    /**
     * Consturctor for channel object of the radio.
     * The channel has an ID, name, and a list of programs.
     * @param id
     * @param name
     */
    public Channel(int id, String name)
    {
        this.channelID = id;
        this.channelName=name;
    }

    /**
     * Create a list with programs that is on this channel.
     * @param programList - list of all programs
     * @return - list with programs only on this channel
     */
    public ArrayList<RadioProgram> makeChannelSpecificList(ArrayList<RadioProgram> programList)
    {
        channelProgramList.clear();
        for(int i = 0; i<programList.size() ; i++)
        {
            if(programList.get(i).getChannelID() == this.channelID)
            {
                channelProgramList.add(programList.get(i));
            }
        }
        return channelProgramList;
    }

    /**
     * Override toString method to return the the channel name.
     * @return attribute that contains channel name.
     */
    @Override
    public String toString()
    {
        return channelName;
    }

    /**
     * Get the channel's program list.
     * @return list of programs.
     */
    public ArrayList<RadioProgram> getChannelProgramList() {
        return channelProgramList;
    }

    /**
     * Get the channel's ID
     * @return integer of channel ID
     */
    public int getChannelID() {
        return channelID;
    }
}
