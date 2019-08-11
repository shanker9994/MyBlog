package programming.blog.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.Mapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import programming.blog.dto.PostDto;
import programming.blog.dto.UserDto;
import programming.blog.entity.PostEntity;
import programming.blog.exception.ErrorMessages;
import programming.blog.exception.PostServiceException;
import programming.blog.exception.UserServiceException;
import programming.blog.repository.PostRepository;
import programming.blog.repository.UserRepository;
import programming.blog.service.PostService;
import programming.blog.service.UserService;
import programming.blog.util.Utils;

@Service
public class PostServiceImpl implements PostService{
	
	@Autowired
	Utils utils;
	
	@Autowired
	PostRepository postRepository;
	
	@Autowired
	UserService userService;
	
	@Autowired
	ModelMapper modelMapper;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public PostDto findPostByPostId(String postId) {
		PostEntity postEntity = new PostEntity();
		postEntity = postRepository.findPostByPostId(postId);
		if(postEntity == null) {
			throw new PostServiceException(ErrorMessages.POST_ID_DOES_NOT_EXISTS.getErrorMessage());
		}
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		PostDto postDto = modelMapper.map(postEntity, PostDto.class);
		//logger.info("post dto values are{}",postDto);
		
		return postDto;
	}
	//create post
	@Override
	public PostDto createPost(PostDto postDto) {
		ModelMapper modelMapper = new ModelMapper();
		UserDto userDto = postDto.getUser();
		if(userDto==null) {
			throw new UserServiceException(ErrorMessages.EMAIL_ID_NOT_FPUND.getErrorMessage());
		}
		userDto = userService.findUserByEmail(userDto.getUserEmail());
		postDto.setUser(userDto);
		postDto.setPostId(utils.generatedPostId(postDto.getTitle()));
		//change the tag name
		String tagName = postDto.getTag().trim()
				.toLowerCase().replace("\\s","");
		postDto.setTag(tagName);
		logger.info("Inside post service impl,creating post {}",postDto);
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		PostEntity postEntity = modelMapper.map(postDto, PostEntity.class);
		postEntity = postRepository.createPost(postEntity);
		
		postDto = modelMapper.map(postEntity, PostDto.class);
		logger.info("Created post details {}",postDto);
		
		return postDto;
		
	}

	@Override
	//update 
	public PostDto updatePost(PostDto postDto) {
		
		/*Validation*/
		if(postDto.getContent()=="" || postDto.getContent()==null
				||postDto.getPostId()==null ||postDto.getPostId()==""
				||postDto.getTitle()=="" || postDto.getTitle()==null
				||postDto.getCreationDate()==null||postDto.getLastupdatedDate()==null) {
			throw new PostServiceException
			(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
		}
		
		//ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		
		//populate user details
		UserDto userDto = postDto.getUser();
		userDto = userService.findUserByEmail(userDto.getUserEmail());
		if(userDto==null) {
			throw new UserServiceException(ErrorMessages.EMAIL_ID_NOT_FPUND.getErrorMessage());
		}
		postDto.setUser(userDto);
		//convert to PostEntity
		Long id = postRepository.findPostByPostId(postDto.getPostId()).getId();
		logger.info("post id is {}",id);
		PostEntity postEntity = modelMapper.map(postDto, PostEntity.class);
		if(id != null) {
			postEntity.setId(id);
		}
		postEntity = postRepository.updatePost(postEntity);
		
		postDto = modelMapper.map(postEntity, PostDto.class);
		logger.info("Updated post is {}",postDto);
		
		return postDto;
	}
	
	@Override
	public void deletePost(PostDto postDto) {
		// TODO Auto-generated method stub
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
		PostEntity postEntity = modelMapper.map(postDto, PostEntity.class);
		postRepository.deletePost(postEntity);
		logger.info("post deleted");
		return;
	}
	//pagination , all posts
	@Override
	public List<PostDto> getAllPosts(int page,int pageLimit) {
		if(page==0) {
			page = 1;
		}
		if(pageLimit==0) {
			throw new PostServiceException(ErrorMessages.PAGE_LIMIT_NOT_SET.getErrorMessage());
		}
		List<PostEntity> postEntity = postRepository.getAllPosts(page, pageLimit);
		//convert all the post entity to post dto
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
		List<PostDto> dtos = new ArrayList<>();
		for(PostEntity postEntityRet : postEntity) {
			PostDto postDto = modelMapper.map(postEntityRet, PostDto.class);
			dtos.add(postDto);
		}
		return dtos;
	}
	//get post by id
	@Override
	public PostDto getPostByPostId(String postId) {
		
		if("".equals(postId) || postId==null ||" ".equals(postId)) {
			throw new PostServiceException(ErrorMessages.POST_ID_DOES_NOT_EXISTS.getErrorMessage());
		}
		PostEntity postEntity = postRepository.getPostByPostId(postId);
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
		PostDto postDto = modelMapper.map(postEntity, PostDto.class);
		return postDto;
	}
	//get post by tag
	@Override
	public List<PostDto> getAllPostByTag(String tag) {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
		
		List<PostEntity> postEntities = postRepository.getAllPostByTag(tag);
		List<PostDto> postReturned = new ArrayList<>();
		for(PostEntity postEntity : postEntities) {
			PostDto dto = modelMapper.map(postEntity, PostDto.class);
			postReturned.add(dto);
		}
		return postReturned;
	}
	//N recent post
	@Override
	public List<PostDto> getNrecentPost(int num) {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
		
		List<PostEntity> postEntities = postRepository.getNrecentPost(num);
		List<PostDto> postReturned = new ArrayList<>();
		for(PostEntity postEntity : postEntities) {
			PostDto dto = modelMapper.map(postEntity, PostDto.class);
			postReturned.add(dto);
		}
		return postReturned;
	}

	@Override
	public long getPostIdByUserGeneratedPostId(String userGeneratedPostId) {
		// TODO Auto-generated method stub
		return postRepository.getPostByPostId(userGeneratedPostId).getId();
		
	}
	

}
