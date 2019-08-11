package programming.blog.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import programming.blog.entity.PostEntity;

@Repository
@Transactional
public class PostRepository {
	
	Logger logger = LoggerFactory.getLogger(PostEntity.class); 
	
	@PersistenceContext
	EntityManager em;
	
	//find postById
	public PostEntity findPostByPostId(String postId) {
		
		String sql ="select p from PostEntity p where p.postId = :postId";
		TypedQuery<PostEntity> query = em.createQuery(sql, PostEntity.class);
		query.setParameter("postId", postId);
		return query.getSingleResult();
	}
	
	//create post
	@Transactional
	public PostEntity createPost(PostEntity postEntity) {
		logger.info("Creating post");
		em.persist(postEntity);
		em.flush();
		logger.info("Post Created");
		return postEntity;
	}
	//updatePost
	public PostEntity updatePost(PostEntity postEntity) {
		logger.info("Updating post with postId "+postEntity.getPostId());
		postEntity = em.merge(postEntity);
		return postEntity;
	}
	//delete post
	public void deletePost(PostEntity postEntity) {
		logger.info("deleting post "+postEntity.getTitle());
		em.remove(postEntity);
	}
	//getAllpost
	public List<PostEntity> getAllPosts(int page , int pageLimit){
		String sql = "select p from PostEntity p order by p.id desc";
//		TypedQuery<PostEntity> query = em.createQuery(sql, PostEntity.class)
//				.setMaxResults(pageLimit).setFirstResult((page-1) * pageLimit);
		TypedQuery<PostEntity> query = em.createQuery(sql, PostEntity.class);
		return query.getResultList();
	}
	//getPostById
	public PostEntity getPostByPostId(String postId) {
		PostEntity postEntity = findPostByPostId(postId);
		return postEntity;
	}
	//getAllpostBytag
	public List<PostEntity> getAllPostByTag(String tag){
		String sql = "select p from PostEntity p where p.tag =: tag order by p.id desc";
		logger.info("--------Tag is {}",tag);
		TypedQuery<PostEntity> query = em.createQuery(sql,PostEntity.class);
		query.setParameter("tag", tag);
		return query.getResultList();
	}
	//getNrecentPost
	public List<PostEntity> getNrecentPost(int num){
		String nativeSql = "select * from post order by id desc LIMIT 0,10";
		Query nQuery = em.createNativeQuery(nativeSql, PostEntity.class);
		List<PostEntity> query = nQuery.getResultList();
		return query;
		
	}
	
	

}
