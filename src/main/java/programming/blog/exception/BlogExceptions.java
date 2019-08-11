package programming.blog.exception;

import java.time.LocalDate;
import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
@ControllerAdvice
public class BlogExceptions extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler({UserServiceException.class,PostServiceException.class,RuntimeException.class,Exception.class})
	  public final ResponseEntity<UiErrorMessage> handleUserExceptions(RuntimeException ex, WebRequest request) {
		//to return a particular fields,
		//create our own class , here we have 
		UiErrorMessage errorMessage = new UiErrorMessage
				(new Date(),ex.getMessage());
	    
		return new ResponseEntity<UiErrorMessage>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	  }
	
	//we can have one more exception handler method here for all other exception.

	
	}