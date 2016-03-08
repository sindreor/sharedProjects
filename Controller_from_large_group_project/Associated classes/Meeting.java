package pu.calendar.models;

import java.util.ArrayList;

public interface Meeting extends Activity {
	ArrayList<User> getUsers();
    ArrayList<User> getAttending();
	ArrayList<User> getNotAttending();
	ArrayList<User> getInvited();
    void setAttending(ArrayList<User> attending);
	Room getRoom();
    void setRoom(Room room);
    int getPriority();
    void setPriority(int priority);
	boolean isCancelled();
	void setCancelled(boolean cancelled);
}
