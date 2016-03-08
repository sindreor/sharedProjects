package pu.calendar.models;

import java.util.ArrayList;


public interface Group extends Model {
    String getName();
    void setName(String name);
	Group getParentGroup();
	void setParentGroup(Group parentGroup);
	User getBoss();
    void setBoss(User boss);
    void addMember(User user);
    void removeMember(User user);
	public ArrayList<User> getMembers();
	void setMembers(ArrayList<User> members);
}
