package com.senlainc.gitcourses.javatraining.petushokvaliantsin.repository;

import com.senlainc.gitcourses.javatraining.petushokvaliantsin.api.repository.IRoomDao;
import com.senlainc.gitcourses.javatraining.petushokvaliantsin.model.Room;
import com.senlainc.gitcourses.javatraining.petushokvaliantsin.model.Room_;
import com.senlainc.gitcourses.javatraining.petushokvaliantsin.model.enumeration.RoomStatus;
import com.senlainc.gitcourses.javatraining.petushokvaliantsin.utility.exception.dao.ReadQueryException;
import org.springframework.stereotype.Repository;

import javax.persistence.PersistenceException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;
import java.util.List;

@Repository
public class RoomDao extends AbstractDao<Room, Long> implements IRoomDao {

    private static final SingularAttribute<Room, RoomStatus> STATUS_SINGULAR_ATTRIBUTE = Room_.status;

    @Override
    public Long readFreeSize() {
        try {
            final CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
            final Root<Room> root = criteriaQuery.from(Room.class);
            final Predicate predicate = criteriaBuilder.equal(root.get(STATUS_SINGULAR_ATTRIBUTE), RoomStatus.FREE);
            return entityManager.createQuery(criteriaQuery
                    .select(criteriaBuilder.count(root))
                    .where(predicate))
                    .getSingleResult();
        } catch (PersistenceException e) {
            throw new ReadQueryException(e);
        }
    }

    @Override
    public RoomStatus readStatus(long index) {
        try {
            final CriteriaQuery<RoomStatus> criteriaQuery = criteriaBuilder.createQuery(RoomStatus.class);
            final Root<Room> root = criteriaQuery.from(Room.class);
            final Predicate predicate = criteriaBuilder.equal(root.get(Room_.id), index);
            return entityManager.createQuery(criteriaQuery
                    .select(root.get(STATUS_SINGULAR_ATTRIBUTE))
                    .where(predicate))
                    .getSingleResult();
        } catch (PersistenceException e) {
            throw new ReadQueryException(e);
        }
    }

    @Override
    public Double readPrice(long index) {
        try {
            final CriteriaQuery<Double> criteriaQuery = criteriaBuilder.createQuery(Double.class);
            final Root<Room> root = criteriaQuery.from(Room.class);
            final Predicate predicate = criteriaBuilder.equal(root.get(Room_.id), index);
            return entityManager.createQuery(criteriaQuery
                    .select(root.get(Room_.price))
                    .where(predicate))
                    .getSingleResult();
        } catch (PersistenceException e) {
            throw new ReadQueryException(e);
        }
    }

    @Override
    public boolean readByNumber(int number) {
        try {
            final CriteriaQuery<Room> criteriaQuery = criteriaBuilder.createQuery(Room.class);
            final Root<Room> root = criteriaQuery.from(Room.class);
            final Predicate predicate = criteriaBuilder.equal(root.get(Room_.number), number);
            entityManager.createQuery(criteriaQuery
                    .select(root)
                    .where(predicate))
                    .getSingleResult();
            return true;
        } catch (PersistenceException e) {
            return false;
        }
    }

    @Override
    public <E> List<Room> readAll(SingularAttribute<Room, E> field, E criteria, int fistElement, int maxResult) {
        try {
            final CriteriaQuery<Room> criteriaQuery = criteriaBuilder.createQuery(Room.class);
            final Root<Room> root = criteriaQuery.from(Room.class);
            final Predicate predicate = criteriaBuilder.equal(root.get(field), criteria);
            return entityManager.createQuery(criteriaQuery
                    .select(root)
                    .where(predicate))
                    .setFirstResult(fistElement)
                    .setMaxResults(maxResult)
                    .getResultList();
        } catch (PersistenceException e) {
            throw new ReadQueryException(e);
        }
    }

    @Override
    public <E, T> List<Room> readAll(SingularAttribute<Room, E> field, E criteria, int fistElement, int maxResult,
                                     SingularAttribute<Room, T> sortParameter) {
        try {
            final CriteriaQuery<Room> criteriaQuery = criteriaBuilder.createQuery(Room.class);
            final Root<Room> root = criteriaQuery.from(Room.class);
            final Predicate predicate = criteriaBuilder.equal(root.get(field), criteria);
            return entityManager.createQuery(criteriaQuery
                    .select(root)
                    .where(predicate)
                    .orderBy(criteriaBuilder.asc(root.get(sortParameter))))
                    .setFirstResult(fistElement)
                    .setMaxResults(maxResult)
                    .getResultList();
        } catch (PersistenceException e) {
            throw new ReadQueryException(e);
        }
    }
}
