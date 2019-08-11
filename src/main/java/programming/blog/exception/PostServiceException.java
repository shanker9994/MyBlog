package programming.blog.exception;

public class PostServiceException extends RuntimeException{

	private static final long serialVersionUID = -4197285324543769275L;
	
	public PostServiceException(String msg) {
		super(msg);
	}

}
