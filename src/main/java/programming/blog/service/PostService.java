package programming.blog.service;

import java.util.List;

import programming.blog.dto.PostDto;

public interface PostService {
	
	public PostDto findPostByPostId(String postId);
	public PostDto createPost(PostDto postDto);
	public PostDto updatePost(PostDto postDto);
	public void deletePost(PostDto postDto);
	public List<PostDto> getAllPosts(int page, int limit);
	public PostDto getPostByPostId(String postId);
	public List<PostDto> getAllPostByTag(String tag);
	public List<PostDto> getNrecentPost(int num);
	public long getPostIdByUserGeneratedPostId(String userGeneratedPostId);

}
