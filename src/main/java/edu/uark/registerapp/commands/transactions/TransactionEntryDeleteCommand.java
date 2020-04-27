package edu.uark.registerapp.commands.transactions;

import java.util.Optional;
import java.util.UUID;
import java.util.List;
import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.uark.registerapp.commands.VoidCommandInterface;
import edu.uark.registerapp.commands.exceptions.NotFoundException;
import edu.uark.registerapp.commands.exceptions.ConflictException;
import edu.uark.registerapp.models.entities.TransactionEntryEntity;
import edu.uark.registerapp.models.repositories.TransactionEntryRepository;
import edu.uark.registerapp.controllers.enums.TransactionEntryDeleteBy;

//TODO: Clean up the comments

@Service
public class TransactionEntryDeleteCommand implements VoidCommandInterface {
    @Transactional
    @Override
    public void execute(){


        if(this.getDeleteBy() == TransactionEntryDeleteBy.ID) {
            deleteById();
        } else if(this.getDeleteBy() == TransactionEntryDeleteBy.TRANSACTION_ID) {
            deleteByTransactionId();
        }else {
            throw new ConflictException("Delete By not set correctly");
        }

       //final Optional<TransactionEntryEntity> transactionEntryEntity =
       //    this.transactionEntryRepository.findById(this.getId());
       //if(!transactionEntryEntity.isPresent()){

       //    final List<TransactionEntryEntity> transactionEntryEntityList =
       //        this.transactionEntryRepository.findByTransactionId(this.getTransactionId());


       //    throw new NotFoundException("TransactionEntry");
       //}
       //this.transactionEntryRepository.delete(transactionEntryEntity.get());
    }

    private void deleteById(){
        final Optional<TransactionEntryEntity> transactionEntryEntity =
            this.transactionEntryRepository.findById(this.getId());
        if(!transactionEntryEntity.isPresent()){
            throw new NotFoundException("TransactionEntry");
        }
        this.transactionEntryRepository.delete(transactionEntryEntity.get());
    }

    private void deleteByTransactionId(){
        final List<TransactionEntryEntity> transactionEntryEntityList =
            this.transactionEntryRepository.findByTransactionId(this.getTransactionId());

        if(transactionEntryEntityList.size() == 0)
        {
            throw new NotFoundException("TransactionEntry");
        }

        for(int i = 0; i < transactionEntryEntityList.size(); i++) {
            this.transactionEntryRepository.delete(transactionEntryEntityList.get(i));
        }
        

    }

    //string to determine which delete method to use
    private TransactionEntryDeleteBy deleteBy;
    public TransactionEntryDeleteBy getDeleteBy() {
        return this.deleteBy;
    }
    public TransactionEntryDeleteCommand setDeleteBy(final TransactionEntryDeleteBy deleteBy) {
        this.deleteBy = deleteBy;
        return this;
    }

    private UUID id;
    public UUID getId() {
        return this.id;
    }
    public TransactionEntryDeleteCommand setId(final UUID transactionEntryId) {
        this.id = transactionEntryId;
        return this;
    }

    private UUID transactionId;
    public UUID getTransactionId(){
        return this.transactionId;
    }

    public TransactionEntryDeleteCommand setTransactionId(final UUID transactionId) {
        this.transactionId = transactionId;
        return this;
    }

    @Autowired
    private TransactionEntryRepository transactionEntryRepository;
}