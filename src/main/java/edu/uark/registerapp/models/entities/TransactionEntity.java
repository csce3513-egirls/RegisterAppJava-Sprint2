package edu.uark.registerapp.models.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

///TODO: additional stuff needed?
@Entity
@Table(name="transaction")
public class TransactionEntity {
    @Id
    @Column(name="id", updatable = false)
    @GeneratedValue(strategy=GenerationType.AUTO)
    private final UUID id;

    public UUID getId() {
        return this.id;
    }

    @Column(name="cashierid")
    private UUID cashierId;

    public UUID getCashierId(){
        return cashierId;
    }

    public TransactionEntity setCashierId(final UUID cashierId){
        this.cashierId = cashierId;
        return this;
    }

    @Column(name="total")
    private long total;

    public long getTotal() {
        return total;
    }

    public TransactionEntity setTotal (final long total){
        this.total = total;
        return this;
    }

    @Column(name="transactiontype")
    private int transactionType;

    public int getTransactionType() {
        return transactionType;
    }

    public TransactionEntity setTransactionType(final int transactionType) {
        this.transactionType = transactionType;
        return this;
    }

    @Column(name="transactionreferenceid")
    private UUID transactionReferenceId;

    public UUID getTransactionReferenceId() {
        return transactionReferenceId;
    }

    public TransactionEntity setTransactionReferenceId(final UUID transactionReferenceId) {
        this.transactionReferenceId = transactionReferenceId;
        return this;
    }

    @Column(name="createdon", insertable=false, updatable = false)
    @Generated(GenerationTime.INSERT)
	private LocalDateTime createdOn;
	public LocalDateTime getCreatedOn() {
		return this.createdOn;
    }
    

    
    public TransactionEntity(){
        this.id = new UUID(0, 0);
        this.cashierId = new UUID(0, 0);
        this.total = -1;
        this.transactionType = -1;
        this.transactionReferenceId = new UUID(0, 0);
    }



}