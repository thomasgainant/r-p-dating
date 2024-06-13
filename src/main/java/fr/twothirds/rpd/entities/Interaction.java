package fr.twothirds.rpd.entities;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "interactions")
public class Interaction {
    public enum Type{
        ACCEPT,
        REJECT,
        INVITE,
        ACCEPT_INVITE,
        REJECT_INVITE
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private User owner;

    @OneToOne
    private User target;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Type type;

    @OneToOne(optional = true)
    private Pair pair;

    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    public Long getId(){
        return this.id;
    }

    public User getOwner(){
        return this.owner;
    }

    public User getTarget(){
        return this.target;
    }

    public Type getType(){
        return this.type;
    }

    public Pair getPair(){
        return this.pair;
    }

    public Date getCreated(){
        return this.created;
    }
}
