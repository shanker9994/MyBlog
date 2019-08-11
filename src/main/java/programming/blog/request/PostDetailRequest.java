package programming.blog.request;

import java.time.LocalDate;

public class PostDetailRequest {
	
	private String postId;
	private String title;
	private String content;
	private LocalDate creationDate;
	private LocalDate lastupdatedDate;
	private String tag;
	private String description;
	//TODO added for user details
	private UserDetailRequest user;
	
	public PostDetailRequest() {
		// TODO Auto-generated constructor stub
	}
/*  Never set primary key of entity class, else hibernate will throw 
 * can not persists detached object
 * 
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
*/
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
	public UserDetailRequest getUser() {
		return user;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setUser(UserDetailRequest user) {
		this.user = user;
	}
	@Override
	public String toString() {
		return "PostDetailRequest [postId=" + postId + ", title=" + title + ", content=" + content + ", creationDate="
				+ creationDate + ", lastupdatedDate=" + lastupdatedDate + ", tag=" + tag + ",description="+ description+"]";
	}
	
}
