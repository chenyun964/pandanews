package sg.edu.smu.cs203.pandanews.model;

import java.util.ArrayList;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class MeasurementsDAO{
    private String title;
    private Image image;
    private String description;
    private DateTime date;

    public MeasurementDAO(String title, Image image, String description, DateTime date){
        this.title = title;
        this.image = image;
        this.description = description;
        this.date = date;
    }

    public MeasurementDAO(){

    }

    public String getTitle(){
        return title;
    }

    public Image getImage(){
        return image;
    }

    public String getDescr(){
        return description;
    }

    public DateTime getDate(){
        return date;
    }
}