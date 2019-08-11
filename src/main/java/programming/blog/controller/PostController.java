package programming.blog.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import programming.blog.dto.PostDto;
import programming.blog.exception.BlogExceptions;
import programming.blog.exception.PostServiceException;
import programming.blog.request.PostDetailRequest;
import programming.blog.response.PostDetailResponse;
import programming.blog.service.PostService;

//@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping(path = "/posts")
public class PostController {
	
	Logger logger = LoggerFactory.getLogger(PostController.class);
	@Autowired
	PostService postService;
	
	@Autowired
	ModelMapper modelMapper;
	
	//create post http://localhost:8080/blogs/posts/
	
	@PostMapping(produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	//@PreAuthorize("hasRole('ROLE_ADMIN')")
	public PostDetailResponse createPost(@RequestBody PostDetailRequest postDetailRequest) {
		SecurityContext securityContext = SecurityContextHolder.getContext();
		Collection<? extends GrantedAuthority> roles = securityContext.getAuthentication().getAuthorities();
		Iterator<? extends GrantedAuthority> itr = roles.iterator();
		boolean auth = false;
		while(itr.hasNext()) {
			GrantedAuthority authRoles = (GrantedAuthority) itr.next();
			//logger.info("auth is controller class"+authRoles.getAuthority());
			if(authRoles.getAuthority().equals("ADMIN")) {
				auth = true;
				break;
			}
			
		}
		if(auth) {
		ModelMapper modelMapper = new ModelMapper();
		PostDto postDto = modelMapper.map(postDetailRequest, PostDto.class);
		logger.info("Post to be created details are {}",postDetailRequest);
	
		postDto = postService.createPost(postDto);
		PostDetailResponse postReturned = modelMapper.map(postDto, PostDetailResponse.class);
		logger.info("Created post details {}",postDto);
		
		return postReturned;
		}else {
			throw new PostServiceException("You are not authorized to create posts."
					+ "Please send mail to shanker4999@gmail.com for authorization");
		}
	}
	
	/*TODO Change the retured type to ResponseEntity Update post*/
	//update
	@PutMapping(produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public PostDetailResponse updatePost(@RequestBody PostDetailRequest postDetailRequest) {
		
		//modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		//convert to PostEntity
		PostDto postDto = modelMapper.map(postDetailRequest, PostDto.class);
		postDto = postService.updatePost(postDto);
		PostDetailResponse postReturned = modelMapper.map(postDto, PostDetailResponse.class);
		logger.info("Updated post is {}",postDto);
		
		return postReturned;
	}
	
	/*Delete a post*/
	@DeleteMapping
	public ResponseEntity<PostDetailResponse> deletePost(@RequestBody PostDetailRequest postDetailRequest) {
		
		ModelMapper modelMapper = new ModelMapper();
		PostDto postDto = modelMapper.map(postDetailRequest, PostDto.class);
		postService.deletePost(postDto);
		logger.info("post delete");
		
		ResponseEntity.noContent().build();
		return ResponseEntity.ok(null);
	}
	/*TODO change returned type to ResponseEntity pagination , all posts*/
	@GetMapping(produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	
	public List<PostDetailResponse> getAllPosts(@RequestParam(value="page" ,defaultValue="1") int page,
												@RequestParam(value = "limit" ,defaultValue="10") int limit) {
		List<PostDto> postDto = postService.getAllPosts(page,limit);
		
		ModelMapper modelMapper = new ModelMapper();
		List<PostDetailResponse> postUiResturned = new ArrayList<>();
		
		for(PostDto postDtoRet : postDto) {
			PostDetailResponse postRet = modelMapper.map(postDtoRet, PostDetailResponse.class);
			postUiResturned.add(postRet);
		}
		return postUiResturned;
	}
	//get post by id
	@GetMapping(path = "/{postId}",
			produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public PostDetailResponse getPostByPostId(@PathVariable String postId) {
		
		PostDto postDto = postService.getPostByPostId(postId);
		ModelMapper modelMapper = new ModelMapper();
		PostDetailResponse postUiResturned = modelMapper.map(postDto, PostDetailResponse.class);
		return postUiResturned;
	}
	//get post by tag
	@GetMapping(path = "/tags/{tag}",
			produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public List<PostDetailResponse> getAllPostByTag(@PathVariable String tag) {
		ModelMapper modelMapper = new ModelMapper();
		
		List<PostDto> postDtos = postService.getAllPostByTag(tag);
		List<PostDetailResponse> postUiReturned = new ArrayList<>();
		for(PostDto postDto : postDtos) {
			PostDetailResponse postReturned = modelMapper.map(postDto, PostDetailResponse.class);
			postUiReturned.add(postReturned);
		}
		return postUiReturned;
	}
	//N recent post
	@GetMapping(path ="/recent",
			produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public List<PostDetailResponse> getRecentPosts() {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
		
		List<PostDto> postDtos = postService.getNrecentPost(10);
		List<PostDetailResponse> postUiReturned = new ArrayList<>();
		for(PostDto postDto : postDtos) {
			PostDetailResponse dto = modelMapper.map(postDto, PostDetailResponse.class);
			postUiReturned.add(dto);
		}
		return postUiReturned;
	}
	

}
