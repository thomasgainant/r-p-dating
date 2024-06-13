package fr.twothirds.rpd.entities;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "pairs")
public class Pair {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private User owner;

    @OneToOne
    private User a;

    @OneToOne
    private User b;

    @Column()
    private Integer iteration;

    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    public Long getId(){
        return this.id;
    }

    public User getOwner(){
        return this.owner;
    }

    public User getA(){
        return this.a;
    }

    public User getB(){
        return this.b;
    }

    public Integer getIteration(){
        return this.iteration;
    }

    public Date getCreated(){
        return this.created;
    }

    public void setId(Long id){
        this.id = id;
    }

    public void setOwner(User owner){
        this.owner = owner;
    }

    public void setA(User a){
        this.a = a;
    }

    public void setB(User b){
        this.b = b;
    }

    public void setIteration(Integer iteration){
        this.iteration = iteration;
    }

    public void setCreated(Date created){
        this.created = created;
    }
}
