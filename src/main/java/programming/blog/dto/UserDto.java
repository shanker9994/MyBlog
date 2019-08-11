package programming.blog.dto;

import java.io.Serializable;

public class UserDto implements Serializable{
	
	private static final long serialVersionUID = 2534453153014508574L;
	private long id;
	private String userId;
	private String userName;
	private String userEmail;
	private String password;
	private String encryptedPassword;
	
	public UserDto() {}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEncryptedPassword() {
		return encryptedPassword;
	}

	public void setEncryptedPassword(String encryptedPassword) {
		this.encryptedPassword = encryptedPassword;
	}

	@Override
	public String toString() {
		return "UserDto [id=" + id + ", userId=" + userId + ", userName=" + userName + ", userEmail=" + userEmail
				+ ", password=" + password + ", encryptedPassword=" + encryptedPassword + "]";
	}
	
	
	
	

}
