package programming.blog.exception;

import java.time.LocalDate;
import java.util.Date;

public class UiErrorMessage {
	
	private Date date;
	private String message;
	
	UiErrorMessage(){}
	UiErrorMessage(Date date,String message){
		this.date = date;
		this.message = message;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
	

}
