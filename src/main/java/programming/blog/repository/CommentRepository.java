package programming.blog.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import programming.blog.entity.CommentEntity;

@Repository
@Transactional
public class CommentRepository {

	@Autowired
	EntityManager em;

	// Create post
	public CommentEntity createComment(CommentEntity commentEntity) {
		if (commentEntity != null) {
			em.persist(commentEntity);
			em.flush();
			return commentEntity;
		}
		return null;
	}

	// update post
	public CommentEntity updateComment(CommentEntity commentEntity) {
		return em.merge(commentEntity);
	}

	// get all comment for a post.
	public List<CommentEntity> getCommentByPost(long postId) {

		String sql = "SELECT c FROM CommentEntity c " + "WHERE c.post.id =: postId "
				+ "order by c.creationDate";
		TypedQuery<CommentEntity> commentRes = em.createQuery(sql, CommentEntity.class);
		commentRes.setParameter("postId", postId);
		return commentRes.getResultList();
	}

}
