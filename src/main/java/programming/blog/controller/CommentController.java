package programming.blog.controller;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import programming.blog.dto.CommentDto;
import programming.blog.request.CommentDetailRequest;
import programming.blog.response.CommentDetailResponse;
import programming.blog.service.CommentService;

//@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping(path ="/posts/{postId}/comments")
public class CommentController {
	
	@Autowired
	ModelMapper modelMapper;
	@Autowired 
	CommentService commentService;
	
	private static Logger logger = LoggerFactory.getLogger(CommentController.class);

	@PostMapping
	public CommentDetailResponse createComment(@RequestBody CommentDetailRequest commentDetailRequest) {
		logger.info("-----Inside comment controller class for posting comment--------");
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		logger.info("comment details are {}",commentDetailRequest);
		CommentDto commentDto = modelMapper.map(commentDetailRequest, CommentDto.class);		
		CommentDto createdComment = commentService.addComment(commentDto);
		CommentDetailResponse uiComment = modelMapper.map(createdComment, CommentDetailResponse.class);
		return uiComment;

	}
	//http://localhost:8080/blog/posts/{postId}/comments
	@GetMapping
	public List<CommentDetailResponse> getAllCommentsForPost(@PathVariable String postId){
		logger.info("[CommentController.class]: post id is {}",postId);
		List<CommentDto> commentsReturned = commentService.getCommentByPostId(postId);
		List<CommentDetailResponse> commentsUi = new ArrayList<>();
		CommentDetailResponse commentUi;
		for(CommentDto comment :commentsReturned) {
			commentUi = modelMapper.map(comment, CommentDetailResponse.class);
			commentsUi.add(commentUi);
		}
		
		return commentsUi;
	}
	
	

}
