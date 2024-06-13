package fr.twothirds.rpd.data;

import java.util.ArrayList;
import java.util.List;

import fr.twothirds.rpd.entities.Pair;
import fr.twothirds.rpd.entities.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class PairQueryBuilder<T> {
    private EntityManager entityManager;
    private CriteriaBuilder criteriaBuilder;
    private CriteriaQuery criteriaQuery;

    private List<Predicate> predicates = new ArrayList<Predicate>();

    private Root<Pair> pairRoot;

    public PairQueryBuilder(EntityManager entityManager){
        this.entityManager = entityManager;
        this.criteriaBuilder = this.entityManager.getCriteriaBuilder();
        this.criteriaQuery = this.criteriaBuilder.createQuery();

        this.pairRoot = this.criteriaQuery.from(Pair.class);
    }

    public PairQueryBuilder<T> filterByOwner(User owner){
        this.predicates.add(
            this.criteriaBuilder.equal(this.pairRoot.get("owner"), owner)
        );
        return this;
    }

    public TypedQuery<T> build(){
        this.criteriaQuery.where(this.predicates.toArray(new Predicate[]{}));
        return this.entityManager.createQuery(this.criteriaQuery);
    }
}
