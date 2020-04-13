package edu.uark.registerapp.commands.transactions;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.uark.registerapp.commands.ResultCommandInterface;
import edu.uark.registerapp.commands.exceptions.UnprocessableEntityException;
import edu.uark.registerapp.models.entities.ProductEntity;
import edu.uark.registerapp.models.entities.TransactionEntity;
import edu.uark.registerapp.models.entities.TransactionEntryEntity;
import edu.uark.registerapp.models.repositories.ProductRepository;
import edu.uark.registerapp.models.repositories.TransactionEntryRepository;
import edu.uark.registerapp.models.repositories.TransactionRepository;
import edu.uark.registerapp.models.api.Transaction;


public class TransactionCreateCommand implements ResultCommandInterface<Transaction> {
    @Override
    public Transaction execute() {
        this.validateProperties();
        //TODO: Test code
        final TransactionEntity transactionEntity = 
        this.transactionRepository.save(new TransactionEntity(this.apiTransaction));

        this.apiTransaction.setId(transactionEntity.getId());
        this.apiTransaction.setCashierId(transactionEntity.getCashierId());
        this.apiTransaction.setTotal(transactionEntity.getTotal());
        this.apiTransaction.setTransactionType(transactionEntity.getType());
        this.apiTransaction.setTransactionReferenceId
        (transactionEntity.getReferenceId());
        this.apiTransaction.setCreatedOn(transactionEntity.getCreatedOn());

        return this.apiTransaction;
    }

    private void validateProperties() {
        if (this.apiTransaction.getTransactionReferenceId() == 
            new UUID(0, 0)) {
                throw new UnprocessableEntityException("referenceid");
            }
    }

    private Transaction apiTransaction;
    public Transaction getApiTransaction() {
        return this.apiTransaction;
    }
    public TransactionCreateCommand setApiTransaction (final Transaction apiTransaction) {
        this.apiTransaction = apiTransaction;
        return this;
    }

    @Autowired
    private TransactionRepository transactionRepository;

}