package programming.blog.controller;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import programming.blog.dto.UserDto;
import programming.blog.exception.ErrorMessages;
import programming.blog.exception.UserServiceException;
import programming.blog.request.UserDetailRequest;
import programming.blog.response.UserDetailResponse;
import programming.blog.service.UserService;

//@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping(path = "/users")
public class UserController {

	@Autowired
	UserService userService;
	
    private Logger logger = LoggerFactory.getLogger(UserController.class);
	
    @PostMapping(produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public UserDetailResponse createUser(@RequestBody UserDetailRequest userDetailRequest) {
		ModelMapper mapper = new ModelMapper();
		UserDto userDto = new UserDto();
		userDto = mapper.map(userDetailRequest, UserDto.class);
		userDto = userService.createUser(userDto);
		UserDetailResponse userDetailResponse = mapper.map(userDto, UserDetailResponse.class);
		return userDetailResponse;
	}

	@GetMapping(path = "/{emailId}",
			produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public UserDetailResponse getUserByEmailId(@PathVariable String emailId) {
		//UserDetailRequest detailRequest = new UserDetailRequest();
		logger.info("********User Email Is {}",emailId);
		ModelMapper mapper = new ModelMapper();
		UserDto userDto = new UserDto();
		//userDto = mapper.map(detailRequest, UserDto.class);
		userDto = userService.findUserByEmail(emailId);
		
		if(userDto !=null) {
			UserDetailResponse detailResponse = mapper.map(userDto, UserDetailResponse.class);
			return detailResponse;
		}else {
			//throw new UserServiceException(ErrorMessages.EMAIL_ID_NOT_FPUND.getErrorMessage());
			return null;
		}
		
	}

}
