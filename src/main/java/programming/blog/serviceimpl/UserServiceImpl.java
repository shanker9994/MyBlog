package programming.blog.serviceimpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import programming.blog.dto.UserDto;
import programming.blog.entity.UserEntity;
import programming.blog.exception.ErrorMessages;
import programming.blog.exception.UserServiceException;
import programming.blog.repository.UserRepository;
import programming.blog.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	programming.blog.util.Utils utils;
	
	@Autowired
	UserRepository repository;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	private static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
	@Override
	public UserDto createUser(UserDto userDto) {
		/* check for null */
		String userEmail = userDto.getUserEmail();
		if(utils.validateEmail(userEmail)) {
			int indexOfAtRate = userEmail.indexOf('@');
			String userName = userEmail.substring(0, indexOfAtRate);
			userDto.setUserName(userName);
		}else {
			throw new UserServiceException("Invalid Email id format");
		}
		if(userDto.getUserEmail()==""||userDto.getUserName()==null||userDto.getPassword()==""||userDto.getPassword()==null) {
			throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
		}
		
		String userId = utils.generatedUserId(10);
		userDto.setUserId(userId);
		/* Replace encrypted password after security*/
		userDto.setEncryptedPassword(bCryptPasswordEncoder
				.encode(userDto.getPassword()));
		ModelMapper modelMapper = new ModelMapper();
		UserEntity userEntity = modelMapper.map(userDto,UserEntity.class);
		userEntity = repository.findUserByEmail(userDto.getUserEmail());
		if(userEntity != null ) {
			throw new UserServiceException(ErrorMessages.RECORD_ALREADY_EXISTS.getErrorMessage());
		}
		userEntity = modelMapper.map(userDto,UserEntity.class);
		userEntity = repository.createUser(userEntity);
		userDto = modelMapper.map(userEntity, UserDto.class);
		return userDto;
	}

	@Override

	public UserDto findUserByEmail(String emailId) {
		log.info("User Email is {}",emailId);
		UserEntity userEntity = repository.findUserByEmail(emailId);
		ModelMapper modelMapper = new ModelMapper();
		if(userEntity !=null) {
			UserDto userDto = modelMapper.map(userEntity, UserDto.class);
			return userDto;
		}//return null;
		return null;
	}

	@Override
	public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		UserEntity userEntity = repository.findUserByEmail(userEmail);
		if(userEntity == null) {
			throw new UsernameNotFoundException(userEmail);
		}
		 List<GrantedAuthority>roles = new ArrayList<>();
		 GrantedAuthority adminRole = new SimpleGrantedAuthority("ADMIN");
		 GrantedAuthority userRole = new SimpleGrantedAuthority("USER");
		if(userEmail.equalsIgnoreCase("shanker9994@gmail.com")) {
			
			roles.add(adminRole);
			roles.add(userRole);

			
		}else {
			roles.add(userRole);
			
		}
		return new User(userEntity.getUserEmail(),
				userEntity.getEncryptedPassword(),
				roles);
	}

}
