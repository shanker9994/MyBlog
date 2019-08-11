package programming.blog.service;

import java.util.List;

import programming.blog.dto.CommentDto;

public interface CommentService {
	
	public CommentDto addComment(CommentDto commentDto);
	
	public CommentDto updateComment(CommentDto commentdto);
	
	public void deleteComment(CommentDto commentDto);
	
	public List<CommentDto> getCommentByPostId(String userGeneratedPostId);
	
	

}
