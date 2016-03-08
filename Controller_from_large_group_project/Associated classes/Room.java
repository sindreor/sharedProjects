package pu.calendar.models;

public interface Room extends Model {
    String getName();
    void setName(String name);
    String getLocation();
    void setLocation(String location);
    int getSize();
    void setSize(int size);
}
