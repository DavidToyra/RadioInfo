/*
 *  RadioProgram.java
 *  David Töyrä (dv15dta)
 *  2019-01-30
 */

package model;

import java.time.LocalDateTime;

/**
 * Object that represents a program in the schedule.
 * A container for all program attributes.
 */
public class RadioProgram
{
    private int programID;
    private String programName;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int channelID;
    private String channelName;
    public String getImage() {
        return image;
    }
    private String image;
    public String getDescription() {
        return description;
    }
    private String description;

    public RadioProgram(int id, String name)
    {
        this.programID = id;
        this.programName = name;
    }

    public String getChannelName() {
        return channelName;
    }

    public String getProgramName() {
        return programName;
    }

    public int getChannelID() {
        return channelID;
    }

    public void setChannelID(int channelID) {
        this.channelID = channelID;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }


    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
