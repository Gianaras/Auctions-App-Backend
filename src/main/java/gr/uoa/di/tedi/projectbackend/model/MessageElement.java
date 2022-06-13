package gr.uoa.di.tedi.projectbackend.model;

import java.sql.Timestamp;
import java.util.Date;

public class MessageElement {
    public String content="";
    public String receiver="";
    public String sender="";

    public Integer id;

    public Timestamp date;
    public Boolean isRead = false;
}
