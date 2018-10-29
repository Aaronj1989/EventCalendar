package events;

import java.sql.Date;
import java.time.LocalDate;

public class EventMessage {

	private String message;
	private String subject;
	private Date date;
	public EventMessage(String subject, String message , LocalDate date){
		
		this.subject = subject;
		this.message = message;
		this.date = Date.valueOf(date);
	
	}
	
	public String getSubject(){
		return subject;
	}
	
	public String getMessage(){
		return message;
	}
	
	public String toString(){
		return this.getSubject();
	}
	
	public int getDayOfMonth(){
		//returns the exact day of the month of the current date object
	LocalDate currentDate = date.toLocalDate();
	
		return currentDate.getDayOfMonth();
	}
	
	public int getYearNumber(){
		LocalDate yearNumber = date.toLocalDate();
	return yearNumber.getYear();
	}
	
	public int getMonthNumber(){
	LocalDate monthNumber = date.toLocalDate();
	
	return monthNumber.getMonthValue();
	}
}
