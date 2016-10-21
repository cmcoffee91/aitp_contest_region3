package com.usaa.acaitp.bashscriptcrazy.russhanneman;
import java.io.Serializable;
import java.util.UUID;

/**
 * Created by christophercoffee on 10/15/16.
 */

public class Places implements Serializable {
    private String m_name;
    private String m_longitude;
    private String m_latitude;
    private String m_pic_loc;
    private String m_visits;
    private String m_time;
    private UUID m_id;

    public Places()
    {

    }

    public void setTime(String time)
    {
        this.m_time = time;
    }

    public String getTime()
    {
        return m_time;
    }

    public void setVisits(String visits)
    {
        this.m_visits = visits;
    }

    public String getVisits()
    {
        return m_visits;
    }

    public void setName(String name)
    {
        this.m_name = name;
    }

    public String getName()
    {
        return m_name;
    }

    public void setLongitude(String longitude)
    {
        m_longitude = longitude;
    }

    public String getLongitude()
    {
        return m_longitude;
    }

    public void setLatitude(String latitude)
    {
        m_latitude = latitude;
    }

    public String getLatitude()
    {
        return m_latitude;
    }


    public String getEmail()
    {
        return m_visits;
    }

    public void setPicLoc(String pic_location)
    {
        this.m_pic_loc = pic_location;
    }

    public String getPicLoc()
    {
        return m_pic_loc;
    }


    public UUID getId()
    {
        return m_id;
    }

    public void setId(UUID id)
    {
        m_id = id;
    }

    public String getPhotoFilename() {
        return "IMG_" + getId().toString() + ".jpg";
    }
}
