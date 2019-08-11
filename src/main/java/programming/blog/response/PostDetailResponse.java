package programming.blog.response;

import java.time.LocalDate;

public class PostDetailResponse {

	private String postId;
	private String title;
	private String content;
	private LocalDate creationDate;
	private LocalDate lastupdatedDate;
	private String tag; //later change to list
	//TODO need to add person details
	private String description;
    private UserDetailResponse user;
	
	public PostDetailResponse() {
		// TODO Auto-generated constructor stub
	}

	public String getPostId() {
		return postId;
	}

	public void setPostId(String postId) {
		this.postId = postId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public LocalDate getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDate creationDate) {
		this.creationDate = creationDate;
	}

	public LocalDate getLastupdatedDate() {
		return lastupdatedDate;
	}

	public void setLastupdatedDate(LocalDate lastupdatedDate) {
		this.lastupdatedDate = lastupdatedDate;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public UserDetailResponse getUser() {
		return user;
	}

	public void setUser(UserDetailResponse user) {
		this.user = user;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
