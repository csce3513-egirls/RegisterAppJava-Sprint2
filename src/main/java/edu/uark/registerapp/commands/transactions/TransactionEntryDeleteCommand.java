package edu.uark.registerapp.commands.transactions;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.uark.registerapp.commands.VoidCommandInterface;
import edu.uark.registerapp.commands.exceptions.NotFoundException;
import edu.uark.registerapp.models.entities.TransactionEntryEntity;
import edu.uark.registerapp.models.repositories.TransactionEntryRepository;

@Service
public class TransactionEntryDeleteCommand implements VoidCommandInterface {
    @Transactional
    @Override
    public void execute(){
        final Optional<TransactionEntryEntity> transactionEntryEntity =
            this.transactionEntryRepository.findById(this.getProductId());
        if(!transactionEntryEntity.isPresent()){
            throw new NotFoundException("TransactionEntry");
        }
        this.transactionEntryRepository.delete(transactionEntryEntity.get());
    }

    private UUID productId;
    public UUID getProductId() {
        return this.productId;
    }
    public TransactionEntryDeleteCommand setProductId(final UUID productId) {
        this.productId = productId;
        return this;
    }

    @Autowired
    private TransactionEntryRepository transactionEntryRepository;
}