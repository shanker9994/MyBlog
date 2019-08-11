package programming.blog.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import programming.blog.dto.CommentDto;
import programming.blog.dto.PostDto;
import programming.blog.dto.UserDto;
import programming.blog.entity.CommentEntity;
import programming.blog.repository.CommentRepository;
import programming.blog.repository.PostRepository;
import programming.blog.service.CommentService;
import programming.blog.service.PostService;
import programming.blog.service.UserService;
import programming.blog.util.Utils;

@Service
public class CommentServiceImpl implements CommentService {
	
	@Autowired
	Utils utils;
	@Autowired
	UserService userService;
	@Autowired
	PostService postService;
	@Autowired
	CommentRepository commentRepository;
	
	private Logger logger = LoggerFactory.getLogger(CommentServiceImpl.class);
	@Override
	public CommentDto addComment(CommentDto commentDto) {
		/*1.Get comment_id
		 *2.populate postDto
		 *3.populate userDto
		 *4.convert form entity to dto*/
		
		String commentId = utils.generateCommentId(10);
		commentDto.setCommentId(commentId);
		String userEmail = commentDto.getUserEmail();
		UserDto userDto = userService.findUserByEmail(userEmail);
		if(userDto==null) {
			throw new RuntimeException("User does not exists");
		}
		commentDto.setUser(userDto);
		String postId = commentDto.getPostId();
		PostDto postDto = postService.findPostByPostId(postId);
		commentDto.setPost(postDto);
		
		/* To display the data properly, append <pre> and </pre>
		 * at the beginning and at the end of the string.
		 */
		String commentText = "<pre>"+commentDto.getCommentText()+"</pre>";
		commentDto.setCommentText(commentText);
		
		logger.info("commentDto is {}",commentDto);
		
		
		
		//commit comment
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		CommentEntity commentEntity = modelMapper.map(commentDto, CommentEntity.class);
		commentEntity = commentRepository.createComment(commentEntity);
		CommentDto uiCommentDto = modelMapper.map(commentEntity, CommentDto.class);
		return uiCommentDto;
	}

	@Override
	public CommentDto updateComment(CommentDto commentdto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteComment(CommentDto commentDto) {
		// TODO Auto-generated method stub
		
	}
	
	//get comments
	@Override
	public List<CommentDto> getCommentByPostId(String userGeneratedPostId) {
		List<CommentDto> uiComments = new ArrayList<>();
		List<CommentEntity> comments = new ArrayList<>();
		long postId = postService.getPostIdByUserGeneratedPostId(userGeneratedPostId);
		comments = commentRepository.getCommentByPost(postId);
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
		for(CommentEntity c : comments) {
			CommentDto comment = modelMapper.map(c, CommentDto.class);
			uiComments.add(comment);
		}
		return uiComments;
	}
	

}
