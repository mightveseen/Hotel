package com.senlainc.gitcourses.javatraining.petushokvaliantsin.repository;

import com.senlainc.gitcourses.javatraining.petushokvaliantsin.api.repository.ISystemUserDao;
import com.senlainc.gitcourses.javatraining.petushokvaliantsin.model.SystemUser;
import com.senlainc.gitcourses.javatraining.petushokvaliantsin.model.SystemUser_;
import com.senlainc.gitcourses.javatraining.petushokvaliantsin.utility.exception.dao.ReadQueryException;
import org.springframework.stereotype.Repository;

import javax.persistence.PersistenceException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Repository
public class SystemUserDao extends AbstractDao<SystemUser, Long> implements ISystemUserDao {

    @Override
    public SystemUser readByUsername(String username) {
        try {
            final CriteriaQuery<SystemUser> criteriaQuery = criteriaBuilder.createQuery(SystemUser.class);
            final Root<SystemUser> root = criteriaQuery.from(SystemUser.class);
            final Predicate predicate = criteriaBuilder.equal(root.get(SystemUser_.username), username);
            return entityManager.createQuery(criteriaQuery
                    .select(root)
                    .where(predicate))
                    .getSingleResult();
        } catch (PersistenceException e) {
            throw new ReadQueryException();
        }
    }
}
