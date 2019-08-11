package programming.blog.dto;

import java.io.Serializable;
import java.time.LocalDate;

public class PostDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6462684014430270462L;
	private Long id;
	private String postId;
	private String title;
	private String content;
	private LocalDate creationDate;
	private LocalDate lastupdatedDate;
	private String tag; //TODO later change to list
	private String description;
	//private UserDto user;  //TODO
	private UserDto user;
	
	//constructor
	public PostDto(){}
	
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
	
	public UserDto getUser() {
		return user;
	}

	public void setUser(UserDto user) {
		this.user = user;
	}
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "PostDto [id=" + id + ", postId=" + postId + ", title=" + title + ", content=" + content
				+ ", creationDate=" + creationDate + ", lastupdatedDate=" + lastupdatedDate + ", tag=" + tag
				+ ", description=" + description + "]";
	}
	
	

	
	
	
	

}
