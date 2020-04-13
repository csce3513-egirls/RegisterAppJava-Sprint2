package edu.uark.registerapp.commands.transactions;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.uark.registerapp.commands.VoidCommandInterface;
import edu.uark.registerapp.commands.exceptions.NotFoundException;
import edu.uark.registerapp.models.entities.TransactionEntity;
import edu.uark.registerapp.models.repositories.TransactionRepository;

@Service
public class TransactionDeleteCommand implements VoidCommandInterface{
    @Transactional
    @Override
    public void execute(){
        final Optional<TransactionEntity> transactionEntity = 
            this.transactionRepository.findById(this.getTransactionId());
        if (!transactionEntity.isPresent()) { // No transaction with the associated transaction ID exists in the database.
            throw new NotFoundException("Transaction");
        }

        this.transactionRepository.delete(transactionEntity.get());
    }

    private UUID transactionId;
    public UUID getTransactionId() {
        return this.transactionId;
    }
    public TransactionDeleteCommand setTransactionId(final UUID transactionId) {
        this.transactionId = transactionId;
        return this;
    }

    @Autowired
    private TransactionRepository transactionRepository;
}