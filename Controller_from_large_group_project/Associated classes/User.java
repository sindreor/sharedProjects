package pu.calendar.models;

public interface User extends Model {
    void setEmail(String email);
    String getEmail();
    void setPassword(String password);
    boolean checkPassword(String password);
    void setFirstname(String firstname);
    String getFirstname();
    String getLastname();
    String getPassword();
    void setLastname(String lastname);
    void setAdmin(boolean admin);
    boolean isAdmin();
	String getName();
}
