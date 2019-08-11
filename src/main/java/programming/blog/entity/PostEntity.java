package programming.blog.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.ManyToAny;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name="post")
public class PostEntity implements Serializable{
	
	private static final long serialVersionUID = 7115363459255639018L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="post_seuence_generator")
	@SequenceGenerator(name="post_seuence_generator",sequenceName="post_sequence",initialValue=1)
	private Long id;
	
	@Column(nullable=false)
	private String postId;
	
	@Column(nullable=false)
	private String title;
	
	@Column(columnDefinition="TEXT",nullable=false)
	private String content;
	
	@Column(nullable=false)
	private String description;
	
	@Column
	@CreationTimestamp
	private LocalDate creationDate;
	
	@Column
	@UpdateTimestamp
	private LocalDate lastupdatedDate;
	
	//TODO change later to list
	@Column(nullable=true)
	private String tag;
	
	//TODO Add for user and comment//
	@ManyToOne
	@JoinColumn(name="user_id")
	private UserEntity user;
	
	//for comments
	@OneToMany(mappedBy="post")
	private List<CommentEntity> comment;
	
	public PostEntity() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public List<CommentEntity> getComment() {
		return comment;
	}

	public void setComment(List<CommentEntity> comment) {
		this.comment = comment;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
	
	
	
	
}
