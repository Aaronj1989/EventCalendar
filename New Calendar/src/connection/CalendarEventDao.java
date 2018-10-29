package connection;

import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;


import events.EventMessage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CalendarEventDao {
	public JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	
	public ObservableList<EventMessage> checkForEvents(int year, int month) {
    

		
		final String EVENT_CHECKER_QUERY = "select subject,message,date FROM date_events where Year(date) = ? and Month(date) = ?";
		
		return jdbcTemplate.query(EVENT_CHECKER_QUERY, new ResultSetExtractor<ObservableList<EventMessage>>() {

			@Override
			public ObservableList<EventMessage> extractData(ResultSet rs) throws SQLException, DataAccessException {
			 ObservableList<EventMessage> eventMessageList = FXCollections.observableArrayList();
			 
			   while(rs.next()) {
				   String subject = rs.getString("subject");
				   Blob blobMessage = rs.getBlob("message");
				   String message = new String(blobMessage.getBytes(1, (int) blobMessage.length()));
				   Date date = rs.getDate("date");
				   LocalDate lDate = ((java.sql.Date) date).toLocalDate();

                  EventMessage eventMessage = new EventMessage(subject, message,lDate);
                  
                  eventMessageList.add(eventMessage);
			   }
				return eventMessageList;
			}
			
			
		},year, month);
		
	}
	public void saveEventMessage(String subject, String message, Date eventDate) {
		
	final String SAVE_EVENT_QUERY_SQL = "Insert into date_events (subject, message, date)  values(?,?,?)";
		

		 jdbcTemplate.update(SAVE_EVENT_QUERY_SQL , subject, message, eventDate);
	}
	
	public void removeCurrentMessage(String subject, String message,int year, int month) {
		final String DELETE_EVENT_QUERY = "Delete from date_events where subject = ? and message = ? and year(date) = ? and month(date) = ?";
		jdbcTemplate.update(DELETE_EVENT_QUERY, subject, message, year, month);
		
	}
	
	public static CalendarEventDao getInstance() {
		
	ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("DatabaseAccess.xml");
	
	try {	return (CalendarEventDao) context.getBean("calendarEventDao");
	
	} finally {context.close();}
	}
	
}
