package pu.calendar.models;

import java.util.Date;

public interface Alert extends Model {
	Activity getActivity();
	User getUser();
	Date getTime();
	boolean isSeen();
	void setSeen(boolean seen);
}
