package sherpath.edu.app.testng;

import java.sql.Timestamp;
import java.util.Calendar;

public class LogMessage 
{

	private String level;
	private String message;
	private Timestamp timestamp;

	public LogMessage(String message, String level) 
	{
		this.message = message;
		this.level = level;
		Calendar calendar = Calendar.getInstance();
		this.timestamp = new java.sql.Timestamp(calendar.getTime().getTime());
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(java.sql.Timestamp timestamp) {
		this.timestamp = timestamp;
	}
}
