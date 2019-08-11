package programming.blog.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name="comment")
public class CommentEntity implements Serializable{
	
	private static final long serialVersionUID = 1765842820184880730L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="comment_sequence_generator")
	@SequenceGenerator(name="comment_sequence_generator",
			sequenceName="comment_sequence",initialValue=1)
	private long id;
	@Column(nullable=false)
	private String commentId;
	@Column(nullable=false,length=512)
	private String commentText;
	
	@Column
	private boolean display;
	
	@Column
	@CreationTimestamp
	private Timestamp creationDate;
	
	@ManyToOne
	@JoinColumn
	UserEntity user;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn
	PostEntity post;
	
	//default constructor
	public CommentEntity() {}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

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

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public PostEntity getPost() {
		return post;
	}

	public void setPost(PostEntity post) {
		this.post = post;
	}
	
	

}
