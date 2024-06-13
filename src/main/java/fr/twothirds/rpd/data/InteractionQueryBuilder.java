package fr.twothirds.rpd.data;

import java.util.ArrayList;
import java.util.List;

import fr.twothirds.rpd.entities.Interaction;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class InteractionQueryBuilder<T> {
    private EntityManager entityManager;
    private CriteriaBuilder criteriaBuilder;
    private CriteriaQuery criteriaQuery;

    private List<Predicate> predicates = new ArrayList<Predicate>();

    private Root<Interaction> interactionRoot;

    public InteractionQueryBuilder(EntityManager entityManager){
        this.entityManager = entityManager;
        this.criteriaBuilder = this.entityManager.getCriteriaBuilder();
        this.criteriaQuery = this.criteriaBuilder.createQuery();

        this.interactionRoot = this.criteriaQuery.from(Interaction.class);
    }

    public InteractionQueryBuilder<T> filterByBoundPair(List<Long> ids){
        this.predicates.add(
            this.criteriaBuilder.in(this.interactionRoot.get("pair")).value(ids)
        );
        return this;
    }

    public TypedQuery<T> build(){
        this.criteriaQuery.where(this.predicates.toArray(new Predicate[]{}));
        return this.entityManager.createQuery(this.criteriaQuery);
    }
}
