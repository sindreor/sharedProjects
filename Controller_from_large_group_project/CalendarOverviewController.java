package controllers;


import java.net.URL;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import pu.calendar.App;
import pu.calendar.Client;
import pu.calendar.models.Activity;
import pu.calendar.models.Alert;
import pu.calendar.models.CalendarOwner;
import pu.calendar.models.Group;
import pu.calendar.models.Meeting;
import pu.calendar.models.User;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polygon;




public class CalendarOverviewController implements Initializable {

	//Things in view
	@FXML
	private Polygon btnYearUp;
	@FXML
	private Polygon btnYearDown;
	@FXML
	private Polygon btnMonthUp;
	@FXML
	private Polygon btnMonthDown;
	@FXML
	private Polygon btnWeekUp;
	@FXML
	private Polygon btnWeekDown;
	@FXML
	private Button btnAdministrerGrupper;
	@FXML
	private Button btnLoggUt;
	@FXML
	private Button btnAdministrerBrukere;
	@FXML
	private Button btnNyAktivitet;
	@FXML
	private Button btnVisKalender;
	@FXML
	private Label labUser;
	@FXML
	private Label labToday;
	@FXML
	private Label labYear;
	@FXML
	private Label labMonth;
	@FXML
	private Label labWeek;
	@FXML
	private Label labMonday;
	@FXML
	private Label labTuesday;
	@FXML
	private Label labWednesday;
	@FXML
	private Label labThursday;
	@FXML
	private Label labFriday;
	@FXML
	private Label labSaturday;
	@FXML
	private Label labSunday;
	@FXML
	private GridPane gvCalendar;
	@FXML
	private ListView<Group> lvGrupper;
	@FXML
	private ListView<User> lvBrukere;
	@FXML
	private ListView<Alert> lvVarsel;
	
	
	private CalendarOwner o;
	
	//Calendar variables and objects
	private Calendar cal;
	private int year;
	private int month;
	private int day;
	private int week;
	private int weeksInYear;
	private Date today;
	
	//Variable which holds the activity that should be shown in activityInfo
	public static Activity activityInf =null;
	
	
	
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		lvBrukere.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		lvGrupper.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		cal=Calendar.getInstance();
		today=cal.getTime();
		year=cal.get(Calendar.YEAR);
		month=cal.get(Calendar.MONTH);
		day=cal.get(Calendar.DAY_OF_MONTH);
		weeksInYear = getNumberOfWeeksInYear(year);
		o=(CalendarOwner)loginController.userLoggedIn;
		labUser.setText(o+"");//Sets the name of the current user
		labToday.setText(generateDateString());
		setWeek(year, month, day);
		setDateNavigationLabels();
		
		addUsersToListView();
		
		if(o instanceof User) {
			User u=(User)o;
			if(u.isAdmin()){
				btnAdministrerBrukere.setDisable(false);//Checks if the user is an administrator
			}
		}
		setWeek(year, month, day);
		setMonthAndDay(year, week);//These must be here to avoid that no activities before today disappears
		setDatesInCalendar();
		clearCalendar();
		insertActivities(o);//Insert all activities for the user logged in(group-activities not added yet!)
		showAlerts();
		generalUpdate();
	}
	
	
	
	/**
	 * Sets the weeknumber based on a specific date
	 * @param year a year
	 * @param month month
	 * @param day a day
	 */
	
	private void setWeek(int year, int month, int day) {
		cal.clear();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH,month);
		cal.set(Calendar.DAY_OF_MONTH, day);
		this.week = cal.get(Calendar.WEEK_OF_YEAR);
		
	}
	/**
	 * Changes date variables to first day of the week
	 * @param year a year
	 * @param week a week
	 */
	
	public void setMonthAndDay(int year, int week) {//Method which changes date variables to first day of the week 
		cal.clear();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.WEEK_OF_YEAR, week);
		this.year = year;
		this.week = week;
		this.month = cal.get(Calendar.MONTH);
		this.day = cal.get(Calendar.DAY_OF_MONTH);
	}
	
	/**
	 * Generates string for today-date
	 * @return returns a formatted date-string
	 */
	private String generateDateString() {
		return day+"."+(month+1)+"."+year;
	}
	/**
	 * Sets the dates displayed in the calendar
	 */
	private void setDatesInCalendar() {
		labMonday.setText(cal.get(Calendar.DAY_OF_MONTH)+"."+(cal.get(Calendar.MONTH)+1));
		cal.add(Calendar.DATE ,1);
		labTuesday.setText(cal.get(Calendar.DAY_OF_MONTH)+"."+(cal.get(Calendar.MONTH)+1));
		cal.add(Calendar.DATE ,1);
		labWednesday.setText(cal.get(Calendar.DAY_OF_MONTH)+"."+(cal.get(Calendar.MONTH)+1));
		cal.add(Calendar.DATE ,1);
		labThursday.setText(cal.get(Calendar.DAY_OF_MONTH)+"."+(cal.get(Calendar.MONTH)+1));
		cal.add(Calendar.DATE ,1);
		labFriday.setText(cal.get(Calendar.DAY_OF_MONTH)+"."+(cal.get(Calendar.MONTH)+1));
		cal.add(Calendar.DATE ,1);
		labSaturday.setText(cal.get(Calendar.DAY_OF_MONTH)+"."+(cal.get(Calendar.MONTH)+1));
		cal.add(Calendar.DATE ,1);
		labSunday.setText(cal.get(Calendar.DAY_OF_MONTH)+"."+(cal.get(Calendar.MONTH)+1));
	}
	
	/**
	 * Sets the labels for year, month and week
	 */
	private void setDateNavigationLabels() {
		String[] months={"januar","februar","mars","april","mai","juni","juli","august","september","oktober","november","desember"};
		labYear.setText(year+"");
		labMonth.setText(months[month]);
		labWeek.setText("uke "+week+"");
	}
	/**
	 *Returns the number of weeks in a specific year
	 *@param year a year
	 *@return number of weeks in the specified year
	 */
	private int getNumberOfWeeksInYear(int year) {
		cal.clear();
	    cal.set(Calendar.YEAR, year);
	    cal.set(Calendar.MONTH, Calendar.DECEMBER);
	    cal.set(Calendar.DAY_OF_MONTH, 31);

	    int ordinalDay = cal.get(Calendar.DAY_OF_YEAR);
	    int weekDay = cal.get(Calendar.DAY_OF_WEEK) - 1; // Sunday = 0
	    int numberOfWeeks = (ordinalDay - weekDay + 10) / 7;
	    return numberOfWeeks;
	}
	/**
	 * Logs the user out and navigates to login window
	 */
	public void btnLoggUtAction() {
		loginController.userLoggedIn=null;
		App.closeTask();
		App.openLogin();
	}
	/**
	 *Opens AdministrateUserView
	 */
	public void btnAdministrerBrukereAction() {
		App.openAdministrateUser();
	}
	/**
	 *Opens AddActivityView
	 */
	public void btnNyAktivitetAction() {
		App.openAddActivity();
	}
	/**
	 *Opens AdministrateGroupView
	 */
	public void btnAdministrerGrupperAction() {
		App.openAdministrateGroup();
	}
	/**
	 *Increases the year by one
	 */
	public void btnYearUpAction() {
		year++;
		setMonthAndDay(year, week);
		setDateNavigationLabels();
		setDatesInCalendar();
		clearCalendar();
		onUpdate();
		
	}
	/**
	 *Decreases the year by one
	 */
	public void btnYearDownAction() {
		year--;
		setMonthAndDay(year, week);
		setDateNavigationLabels();
		setDatesInCalendar();
		clearCalendar();
		onUpdate();
		
		
	}
	/**
	 *Increases the month by one
	 */
	public void btnMonthUpAction() {
		month++;
		if(month>11) {
			month=0;
			year++;
			setWeek(year, month, day);
			setMonthAndDay(year, week);//Takes this to get the first day of the week
			setDateNavigationLabels();
			setDatesInCalendar();
			clearCalendar();
			onUpdate();
		}
		else {
			setWeek(year, month, day);
			setMonthAndDay(year, week);//Takes this to get the first day of the week
			setDateNavigationLabels();
			setDatesInCalendar();
			clearCalendar();
			onUpdate();
		}
		
	}
	/**
	 *Decreases the month by one
	 */
	public void btnMonthDownAction() {
		month--;
		if(month<0) {
			month=11;
			year--;
			setWeek(year, month, day);
			setMonthAndDay(year, week);//Takes this to get the first day of the week
			setDateNavigationLabels();
			setDatesInCalendar();
			clearCalendar();
			onUpdate();
		}
		else {
			setWeek(year, month, day);
			setMonthAndDay(year, week);//Takes this to get the first day of the week
			setDateNavigationLabels();
			setDatesInCalendar();
			clearCalendar();
			onUpdate();
		}
		
		
	}
	/**
	 *Increases the week by one
	 */
	public void btnWeekUpAction() {
		week++;
		if(week>weeksInYear){
			year++;
			weeksInYear = getNumberOfWeeksInYear(year);
			week=1;
			setMonthAndDay(year, week);
			setDateNavigationLabels();
			setDatesInCalendar();
			clearCalendar();
			onUpdate();
		}
		else{
			setMonthAndDay(year, week);
			setDateNavigationLabels();
			setDatesInCalendar();
			clearCalendar();
			onUpdate();
		}
		
	}
	/**
	 *Decreases the week by one
	 */
	public void btnWeekDownAction() {
		week--;
		if(week<1){
			year--;
			weeksInYear = getNumberOfWeeksInYear(year);
			week=weeksInYear;
			setMonthAndDay(year, week);
			setDateNavigationLabels();
			setDatesInCalendar();
			clearCalendar();
			onUpdate();
		}
		else{
			setMonthAndDay(year, week);
			setDateNavigationLabels();
			setDatesInCalendar();
			clearCalendar();
			onUpdate();
		}
		
		
	}
	/**
	 *Shows the calendar for the user logged in
	 */
	public void btnVisKalenderAction() {
		clearCalendar();
		lvBrukere.getSelectionModel().select(-1);
		lvGrupper.getSelectionModel().select(-1);
		insertActivities(o);
	}
	/**
	 *Gets a random color, mot in use anymore
	 *@return a RGB-string which can be used in CSS
	 */
	private String getRandomColor() { //Chooses a random color for the activities
		Random rand=new Random();
		return "rgba("+rand.nextInt(255)+","+rand.nextInt(255)+","+rand.nextInt(255)+", 0.5)";
	}
	/**
	 *Gets a color based on the inputstring
	 *@param name an activity title
	 *@return a RGB-string which can be used in CSS
	 */
	private String getStringColor(String name) {
		String color = String.format("#%X", name.hashCode());
		int r = Integer.valueOf(color.substring(1,3), 16);
		int g = Integer.valueOf(color.substring(3,5), 16);
		int b = Integer.valueOf(color.substring(5,7), 16);
		return "rgba("+r+","+g+","+b+", 0.5)";
	}
	/**
	 *Creates and positions one activity in the calendar
	 *@param a an activity-object
	 *@param color an RGB-string compatible with CSS
	 */
	@SuppressWarnings("static-access")
	private void createActivityIcon(Activity a, String color) {
		//Evaluating time and date
		Calendar start=Calendar.getInstance();
		Calendar end=Calendar.getInstance();
		start.setTime(a.getStart());
		end.setTime(a.getEnd());
		int day = start.get(Calendar.DAY_OF_WEEK);
		//Getting start position
		int startHour = start.get(Calendar.HOUR_OF_DAY);
		int startMinute = start.get(Calendar.MINUTE);
		int startPos = startHour*4;
		if(startMinute<=15 && startMinute>0) {
			startPos++;
		}
		else if(startMinute<=30) {
			startPos+=2;
		}
		else if(startMinute<=45) {
			startPos+=3;
		}
		
		//Getting end position
		int endHour = end.get(Calendar.HOUR_OF_DAY);
		int endMinute = end.get(Calendar.MINUTE);
		int endPos = endHour*4;
		if(endMinute<=15 && endMinute>0) {
			endPos++;
		}
		else if(endMinute<=30) {
			endPos+=2;
		}
		else if(endMinute<=45) {
			endPos+=3;
		}
		else{
			endPos+=4;
		}
		//Getting duration
		int duration=endPos-startPos;
		int[] days={6,0,1,2,3,4,5};//Array for positioning activities at correct day
	
		//Creating the pane
		Pane p=new Pane();
		p.setStyle("-fx-background-color:"+color);//Setting background color
		
		p.setOnMouseClicked(event -> {//This happens when the activity is clicked in the calendar
			/*
			Since we support activities over several days activity.end and activity.start might have been edited
			Therefore we re-retrieve the activity instances from the database
			 */
			try {
				// Activity might be a meeting
				if(a instanceof Meeting) {
					activityInf = Client.meetingManager.getMeeting(a.getId());
				}
				else {
					activityInf = Client.activityManager.getActivity(a.getId());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			App.openActivityInfo();
		});
		Label l=new Label();
		l.setMaxWidth(150);
		String stringStartHour=startHour+"";
		String stringEndHour=endHour+"";
		String stringStartMinute=startMinute+"";
		String stringEndMinute=endMinute+"";
		if(startHour<10) {
			stringStartHour="0"+startHour;
		}
		if(startMinute<10) {
			stringStartMinute="0"+startMinute;
		}
		if(endHour<10) {
			stringEndHour="0"+endHour;
		}
		if(endMinute<10) {
			stringEndMinute="0"+endMinute;
		}
		
		
		String text=a.getTitle()+"\n"+stringStartHour+":"+stringStartMinute+" - "+stringEndHour+":"+stringEndMinute;
		if(a instanceof Meeting) {//Checks if the activity is a meeting and adds room information if so
			Meeting m = (Meeting)a;
			text+="\nRom: "+m.getRoom();
		}
		l.setText(text);// Adding activity text
		p.getChildren().add(l);
		gvCalendar.add(p, days[day-1], startPos);
		gvCalendar.setRowSpan(p, duration);
		
		
		
	}
	
	/**
	 *Inserts all activities, from a date for one week
	 *@param o CalendarOwner-object
	 */
	private void insertActivities(CalendarOwner o) {
		
		try {
			
			cal.clear();
			cal.set(Calendar.YEAR, year);
			cal.set(Calendar.MONTH, month);
			cal.set(Calendar.DAY_OF_MONTH, day);
			Date start = cal.getTime();
			cal.add(Calendar.DATE, 6);//SJEKK OM 6 ER RIKTIG HER
			cal.set(Calendar.HOUR_OF_DAY,23);
			cal.set(Calendar.MINUTE,59);
			Date end = cal.getTime();
			ArrayList<Activity> activities;
			if(o instanceof User) {
				User u = (User) o;
				activities=Client.activityManager.getActivitiesByUserAndTime(u, start, end);
			}
			else {
				Group g = (Group) o;
				activities=Client.activityManager.getActivitiesByGroupAndTime(g, start, end);
			}
			
			for (int i = 0; i < activities.size(); i++) {
				if(activities.get(i).getEnd().after(activities.get(i).getStart())) {
					Calendar s=Calendar.getInstance();
					s.setTime(activities.get(i).getStart());
					Calendar e=Calendar.getInstance();
					e.setTime(activities.get(i).getEnd());
					String startDate=s.get(Calendar.DAY_OF_MONTH)+"."+s.get(Calendar.MONTH)+"."+s.get(Calendar.YEAR);
					String endDate=e.get(Calendar.DAY_OF_MONTH)+"."+e.get(Calendar.MONTH)+"."+e.get(Calendar.YEAR);
					Activity a = activities.get(i);
					if(endDate.equals(startDate)){//Checks if the activity lasts over several days
						createActivityIcon(a, getStringColor(a.getTitle()));
					}
					else{
						addOverlappingActivity(a, start, end);
					}
				}
				
			}
			
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		
	}
	/**
	 *This is called if an activity lasts over several days, it should split an activity and put each part in each day.
	 *@param a an Activity-object
	 *@param start Date-object for start of week 
	 *@param end Date-object for end of week
	 */
	private void addOverlappingActivity(Activity a, Date start, Date end) {
		if(a.getStart().before(start) && a.getEnd().after(end)) {//This is done for an activity which spans more than two weeks
			a.setStart(start);
			a.setEnd(end);
		}
		else if(a.getStart().before(end) && a.getEnd().after(end)) {//This is done for an activity which is in the end of the week and goes over in the next week
			a.setEnd(end);
			
		}
		else if(a.getStart().before(start) && a.getEnd().after(start)) { //This is done for an activity which is in the start of the week and goes over in the last week
			a.setStart(start);
		}
		
		Calendar s=Calendar.getInstance();//Start-calendar
		s.setTime(a.getStart());//Startdate
		Calendar e=Calendar.getInstance();//End-calendar
		e.setTime(a.getEnd());//Enddate
		String startDate=s.get(Calendar.DAY_OF_MONTH)+"."+s.get(Calendar.MONTH)+"."+s.get(Calendar.YEAR);//Startdate on string-format
		String endDate=e.get(Calendar.DAY_OF_MONTH)+"."+e.get(Calendar.MONTH)+"."+e.get(Calendar.YEAR);//Enddate on string-format
		e.add(Calendar.DATE, 1);
		String stopDate=e.get(Calendar.DAY_OF_MONTH)+"."+e.get(Calendar.MONTH)+"."+e.get(Calendar.YEAR);//Date when the comming while-loop should stop
		
		String day = startDate;//String for comparison and iteration
		Calendar c = Calendar.getInstance();//Working calendar
		int inc=0;//Just for testing
		Date activityEnd=a.getEnd();
		
		String color = getStringColor(a.getTitle());//Color so that one activity has the same color every day
		
		a.setStart(s.getTime());//This because Calendar and database saves with different formats
		a.setEnd(e.getTime());//This because Calendar and database saves with different formats
		
		while(!(day.equals(stopDate))) {//Activity starts and ends the day given by the day-variable, is also part of an activity which stretches over several weeks
			if(day.equals(startDate) && day.equals(endDate)) {
				createActivityIcon(a, color);
			}
			else if(day.equals(startDate)) {//Activity starts the day given by the day-variable
				c.clear();
				c.setTime(a.getStart());
				c.set(Calendar.HOUR_OF_DAY, 23);
				c.set(Calendar.MINUTE,59);
				a.setEnd(c.getTime());
				createActivityIcon(a, color);
			}
			else if(day.equals(endDate)) {//Activity ends the day given by the day-variable
				c.clear();
				c.setTime(s.getTime());
				c.set(Calendar.HOUR_OF_DAY, 0);
				c.set(Calendar.MINUTE,0);
				a.setStart(c.getTime());
				a.setEnd(activityEnd);
				createActivityIcon(a, color);
				
			}
			else {//Activity covers the whole day given by the day-variable
				c.clear();
				c.setTime(s.getTime());
				c.set(Calendar.HOUR_OF_DAY, 0);
				c.set(Calendar.MINUTE,0);
				a.setStart(c.getTime());
				c.clear();
				c.setTime(s.getTime());
				c.set(Calendar.HOUR_OF_DAY, 23);
				c.set(Calendar.MINUTE,59);
				a.setEnd(c.getTime());
				createActivityIcon(a, color);
				
			}
			inc++;
			s.add(Calendar.DATE, 1);
			day=s.get(Calendar.DAY_OF_MONTH)+"."+s.get(Calendar.MONTH)+"."+s.get(Calendar.YEAR);
		}
		
		
	}
	/**
	 *Removes all the content currently displayed in the calendar
	 */
	private void clearCalendar() {
		
		gvCalendar.getChildren().removeAll(gvCalendar.getChildren());
		
	}
	/**
	 *Gets users from database and displays them in a ListView, also displays all the users group in a ListView
	 */
	private void addUsersToListView() {
		try {
			lvBrukere.getItems().removeAll(lvBrukere.getItems());
			lvGrupper.getItems().removeAll(lvGrupper.getItems());
			lvBrukere.getItems().addAll(Client.userManager.getUsers());
			User u = (User)o;
			lvGrupper.getItems().addAll(Client.userManager.getGroupParticipation(u));
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	/**
	 *This method is called every time some changes should be made to the calendar
	 */
	public void onUpdate() {
		clearCalendar();
		ObservableList<User> users = lvBrukere.getSelectionModel().getSelectedItems();
		ObservableList<Group> groups = lvGrupper.getSelectionModel().getSelectedItems();
		if(users.size()==0 && groups.size()==0) {
			insertActivities(o);
		}
		
		else {
			for (User user : users) {
				insertActivities((CalendarOwner)user);
			}
			
			for (Group group : groups) {
				insertActivities((CalendarOwner)group);
			}
		}
	}
	/**
	 *Shows all the users unseen alerts in a ListView
	 */
	public void showAlerts() {
		try {
			lvVarsel.getItems().removeAll(lvVarsel.getItems());
			ArrayList<Alert> alerts=Client.alertManager.getUnseenAlerts((User)o);
			lvVarsel.getItems().addAll(alerts);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	/**
	 *When an alert is clicked this method removes it
	 */
	public void updateAlerts() {
		Alert a=lvVarsel.getSelectionModel().getSelectedItem();
		try {
			if(a!=null){
				Client.alertManager.deleteAlert(a);
				lvVarsel.getItems().remove(a);
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}	
	}
	/**
	 *Updates the CalendarOverview based on a 30 seconds timer
	 */
	public void generalUpdate() {
		Timer timer = new java.util.Timer();
		timer.schedule(new TimerTask() {
		    public void run() {
		         Platform.runLater(new Runnable() {
		            public void run() {
		            	System.out.println("Update");
		        		showAlerts();
		        		onUpdate();
		        		addUsersToListView();
		        		getStringColor("Sindre");
		        		getStringColor("Nils Pedersen");
		            }
		        });
		    }
		}, 0, 30000);
	}

}

