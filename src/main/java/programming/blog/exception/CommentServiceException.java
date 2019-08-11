package programming.blog.exception;

public class CommentServiceException extends RuntimeException{
	
	private static final long serialVersionUID = 1789056628637923065L;

	public CommentServiceException(String message) {
		super(message);
	}

}
