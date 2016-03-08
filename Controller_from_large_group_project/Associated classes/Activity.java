package pu.calendar.models;
import java.util.Date;

public interface Activity extends Model {
    String getTitle();
    void setTitle(String title);
    String getDescription();
    void setDescription(String description);
    String getLocation();
    void setLocation(String location);
    Date getEnd();
    void setEnd(Date end);
    Date getStart();
    void setStart(Date start);
	User getUser();
	Group getGroup();
	boolean isUserParticipating(User user);
	int getActivityId();
}
