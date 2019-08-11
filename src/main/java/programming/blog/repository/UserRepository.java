package programming.blog.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import programming.blog.entity.UserEntity;

@Repository
@Transactional
public class UserRepository {

	@PersistenceContext
	EntityManager em;

	public UserEntity findUserByEmail(String userEmail) {
		String sql = "select u from UserEntity u where userEmail =: userEmail";
		TypedQuery<UserEntity> ue = em.createQuery(sql, UserEntity.class);
		ue.setParameter("userEmail", userEmail);
		if(ue.getResultList().isEmpty()) {
			return null;
		}
		return ue.getSingleResult();
	}

	public UserEntity createUser(UserEntity userEntity) {
		em.persist(userEntity);
		return userEntity;
	}

}
