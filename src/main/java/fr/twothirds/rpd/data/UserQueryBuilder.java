package fr.twothirds.rpd.data;

import java.util.ArrayList;
import java.util.List;

import fr.twothirds.rpd.entities.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class UserQueryBuilder<T>{
    private EntityManager entityManager;
    private CriteriaBuilder criteriaBuilder;
    private CriteriaQuery criteriaQuery;

    private List<Predicate> predicates = new ArrayList<Predicate>();

    private Root<User> userRoot;

    public UserQueryBuilder(EntityManager entityManager){
        this.entityManager = entityManager;
        this.criteriaBuilder = this.entityManager.getCriteriaBuilder();
        this.criteriaQuery = this.criteriaBuilder.createQuery();

        this.userRoot = this.criteriaQuery.from(User.class);
    }

    public UserQueryBuilder<T> filterByGender(User.Gender gender){
        this.predicates.add(
            this.criteriaBuilder.equal(this.userRoot.get("gender"), gender)
        );
        return this;
    }

    public UserQueryBuilder<T> filterByScore(Long score){
        this.predicates.add(
            this.criteriaBuilder.equal(this.userRoot.get("score"), score)
        );
        return this;
    }

    public UserQueryBuilder<T> isNot(User user){
        this.predicates.add(
            this.criteriaBuilder.notEqual(this.userRoot.get("id"), user.getId())
        );
        return this;
    }

    public UserQueryBuilder<T> getMaxUserScore(){
        this.criteriaQuery.select(this.criteriaBuilder.max(this.userRoot.get("score")));
        return this;
    }

    public TypedQuery<T> build(){
        this.criteriaQuery.where(this.predicates.toArray(new Predicate[]{}));
        return this.entityManager.createQuery(this.criteriaQuery);
    }
}