package programming.blog.exception;

public enum ErrorMessages {
	MISSING_REQUIRED_FIELD("Missing required Field, Please check documentation for required fields"),
	RECORD_ALREADY_EXISTS("Email Id exists,Please login"),
	INTERNAL_SERVER_ERROR("Internal server error, Please contact admin"),
	EMAIL_ID_NOT_FPUND("Email id not found"),
	POST_ID_DOES_NOT_EXISTS("Not a valid post id"),
	PAGE_LIMIT_NOT_SET("Missing page limit");
	
	private String errorMessage;
	
	private ErrorMessages(String errorMessages) {
		this.errorMessage = errorMessages;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	
	

}
