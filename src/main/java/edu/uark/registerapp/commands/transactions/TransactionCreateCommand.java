//package edu.uark.registerapp.commands.transactions;
//
//import java.util.UUID;
//
//import org.springframework.beans.factory.annotation.Autowired;
//
//import edu.uark.registerapp.commands.ResultCommandInterface;
//import edu.uark.registerapp.commands.exceptions.//UnprocessableEntityException;
//import edu.uark.registerapp.models.entities.TransactionEntity;
//import edu.uark.registerapp.models.repositories.//TransactionRepository;
//import edu.uark.registerapp.models.api.Transaction;
//
//
//public class TransactionCreateCommand implements //ResultCommandInterface<Transaction> {
//    @Override
//    public Transaction execute() {
//        this.validateProperties();
//        //TODO: Test code
//        final TransactionEntity transactionEntity = 
//        this.transactionRepository.save(new TransactionEntity//(this.apiTransaction));
//
//        this.apiTransaction.setId(transactionEntity.getId());
//        this.apiTransaction.setCashierId(transactionEntity.//getCashierId());
//        this.apiTransaction.setTotal(transactionEntity.getTotal())//;
//        this.apiTransaction.setTransactionType(transactionEntity.//getType());
//        this.apiTransaction.setTransactionReferenceId
//        (transactionEntity.getReferenceId());
//        this.apiTransaction.setCreatedOn(transactionEntity.//getCreatedOn());
//
//        return this.apiTransaction;
//    }
//
//    private void validateProperties() {
//        if (this.apiTransaction.getTransactionReferenceId() == 
//            new UUID(0, 0)) {
//                throw new UnprocessableEntityException//("referenceid");
//            }
//    }
//
//    private Transaction apiTransaction;
//    public Transaction getApiTransaction() {
//        return this.apiTransaction;
//    }
//    public TransactionCreateCommand setApiTransaction (final //Transaction apiTransaction) {
//        this.apiTransaction = apiTransaction;
//        return this;
//    }
//
//    @Autowired
//    private TransactionRepository transactionRepository;
//
//}

package edu.uark.registerapp.commands.transactions;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.uark.registerapp.commands.VoidCommandInterface;
import edu.uark.registerapp.commands.ResultCommandInterface;
import edu.uark.registerapp.models.entities.ProductEntity;
import edu.uark.registerapp.models.entities.TransactionEntity;
import edu.uark.registerapp.models.entities.TransactionEntryEntity;
import edu.uark.registerapp.models.repositories.ProductRepository;
import edu.uark.registerapp.models.repositories.TransactionEntryRepository;
import edu.uark.registerapp.models.repositories.TransactionRepository;
import edu.uark.registerapp.models.api.Transaction;
import edu.uark.registerapp.models.api.TransactionEntry;

@Service
public class TransactionCreateCommand implements VoidCommandInterface {
	@Override
	public void execute() {
		long transactionTotal = 0L;
		

        UUID finishEntriesUUID = UUID.fromString("00000000-0000-0000-0000-000000000000");
    
                final List<TransactionEntryEntity> transactionEntryEntities = 
                    this.transactionEntryRepository.findByTransactionId(finishEntriesUUID); 
    
                if(transactionEntryEntities.size() == 0){
                    return;
                }
        //calculating total
        for (int i = 0; i < transactionEntryEntities.size(); i++) {
            double purchasedQuantity = transactionEntryEntities.get(i).getQuantity();  
            
            transactionTotal += (transactionEntryEntities.get(i).getPrice() * purchasedQuantity);
        
        }

		this.createTransaction(
			transactionEntryEntities,
            transactionTotal);
            return;
	}

	// Helper methods
	@Transactional
	private void createTransaction(
		final List<TransactionEntryEntity> transactionEntryEntities,
		final long transactionTotal
	) {

		final TransactionEntity transactionEntity =
			this.transactionRepository.save(
				(new TransactionEntity(this.employeeId, transactionTotal, 1)));

		for (TransactionEntryEntity transactionEntryEntity : transactionEntryEntities) {
			transactionEntryEntity.setTransactionId(transactionEntity.getId());
            //updating the entries
			this.transactionEntryRepository.save(transactionEntryEntity);
		}
	}

	// Properties
	private UUID employeeId;
	public UUID getEmployeeId() {
		return this.employeeId;
	}
	public TransactionCreateCommand setEmployeeId(final UUID employeeId) {
		this.employeeId = employeeId;
		return this;
    }

	@Autowired
	ProductRepository productRepository;

	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private TransactionEntryRepository transactionEntryRepository;
}
