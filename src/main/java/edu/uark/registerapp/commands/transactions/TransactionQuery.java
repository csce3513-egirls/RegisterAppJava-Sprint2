package edu.uark.registerapp.commands.transactions;

import java.util.UUID;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.uark.registerapp.commands.ResultCommandInterface;
import edu.uark.registerapp.commands.exceptions.NotFoundException;
import edu.uark.registerapp.models.entities.TransactionEntity;
import edu.uark.registerapp.models.repositories.TransactionRepository;
import edu.uark.registerapp.models.api.Transaction;


@Service
public class TransactionQuery implements ResultCommandInterface<Transaction> {
    @Override
    public Transaction execute() {
        final Optional<TransactionEntity> transactionEntity = 
            this.transactionRepository.findById(this.transactionId);
        
        if(!transactionEntity.isPresent()) {
            return new Transaction(transactionEntity.get());
        } else {
            throw new NotFoundException("Transaction");
        }
    }

    private UUID transactionId;
    public UUID getTransactionId() {
        return this.transactionId;
    }

    public TransactionQuery setTransactionId(final UUID transactionId) {
        this.transactionId = transactionId;
        return this;
    }

    @Autowired
	private TransactionRepository transactionRepository;

}