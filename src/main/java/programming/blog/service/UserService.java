package programming.blog.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import programming.blog.dto.UserDto;

public interface UserService extends UserDetailsService{
	
	public UserDto createUser(UserDto userDto);
	public UserDto findUserByEmail(String email);

}
