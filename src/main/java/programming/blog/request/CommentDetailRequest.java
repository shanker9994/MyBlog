package programming.blog.request;

import java.sql.Timestamp;

public class CommentDetailRequest {
	
	private String commentId;
	private String commentText;
	private boolean display;
	private Timestamp creationDate;
	private String postId;
	private String userEmail;
	//private UserDetailRequest user;
	//private PostDetailRequest post;
	
	public CommentDetailRequest() {}

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

//	public UserDetailRequest getUser() {
//		return user;
//	}
//
//	public void setUser(UserDetailRequest user) {
//		this.user = user;
//	}
//
//	public PostDetailRequest getPost() {
//		return post;
//	}
//
//	public void setPost(PostDetailRequest post) {
//		this.post = post;
//	}
	


	public String getPostId() {
		return postId;
	}

	public void setPostId(String postId) {
		this.postId = postId;
	}


	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	@Override
	public String toString() {
		return "CommentDetailRequest [commentId=" + commentId + ", commentText=" + commentText + ", display=" + display
				+ ", creationDate=" + creationDate + ", postId=" + postId + ", userEmail=" + userEmail + "]";
	}
	
	
	
	

}
