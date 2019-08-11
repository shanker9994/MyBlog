package programming.blog.response;

import java.sql.Timestamp;

public class CommentDetailResponse {
	
	private String commentId;
	private String commentText;
	private boolean display;
	private Timestamp creationDate;
	private UserDetailResponse user;
	//private PostDetailResponse post;
	
	public CommentDetailResponse() {}

	public String getCommentId() {
		return commentId;
	}

	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}

	public String getCommentText() {
		return commentText;
	}

	public void setCommentText(String commentText) {
		this.commentText = commentText;
	}

	public boolean isDisplay() {
		return display;
	}

	public void setDisplay(boolean display) {
		this.display = display;
	}

	public Timestamp getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}

	public UserDetailResponse getUser() {
		return user;
	}

	public void setUser(UserDetailResponse user) {
		this.user = user;
	}

//	public PostDetailResponse getPost() {
//		return post;
//	}
//
//	public void setPost(PostDetailResponse post) {
//		this.post = post;
//	}
	
	

}
