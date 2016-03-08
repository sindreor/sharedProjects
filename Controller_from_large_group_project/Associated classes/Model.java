package pu.calendar.models;

import java.io.Serializable;

public interface Model extends Serializable {
	int getId();

	/**
	 * Save instance in database
	 * @return saved or not
	 */
	boolean save();

	/**
	 * Delete instance in database
	 * @return deleted or not
	 */
	boolean delete();
}
