package edu.uark.registerapp.commands.transactions;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.uark.registerapp.commands.ResultCommandInterface;
import edu.uark.registerapp.commands.exceptions.NotFoundException;
import edu.uark.registerapp.models.api.Transaction;
import edu.uark.registerapp.models.entities.TransactionEntity;
import edu.uark.registerapp.models.repositories.TransactionRepository;

@Service
public class TransactionUpdateCommand implements ResultCommandInterface<Transaction> {
	@Transactional
	@Override
	public Transaction execute() {
		//this.validateProperties();

		final Optional<TransactionEntity> transactionEntity =
			this.transactionRepository.findById(this.transactionReferenceId);
		if (!transactionEntity.isPresent()) { // No record with the associated record ID exists in the database.
			throw new NotFoundException("Transaction");
		}

		// Synchronize any incoming changes for UPDATE to the database.
		this.apiTransaction = transactionEntity.get().synchronize(this.apiTransaction);

		// Write, via an UPDATE, any changes to the database.
		this.transactionRepository.save(transactionEntity.get());

		return this.apiTransaction;
	}

	// Helper methods
	/*private void validateProperties() {
		if (StringUtils.isBlank(this.apiTransaction.getTransactionReferenceId())) {
			throw new UnprocessableEntityException("lookupcode");
		}
	}*/

	// Properties
	private UUID transactionReferenceId;
	public UUID geTransactionReferenceId() {
		return this.transactionReferenceId;
	}
	public TransactionUpdateCommand setId(final UUID transactionReferenceId) {
		this.transactionReferenceId = transactionReferenceId;
		return this;
    }
    
    private UUID total;
	public UUID getTotal() {
		return this.total;
	}
	public TransactionUpdateCommand setTotal(final UUID total) {
		this.total = total;
		return this;
    }
    
    private UUID transactionType;
	public UUID getTransactionType() {
		return this.transactionType;
	}
	public TransactionUpdateCommand setTransactionTypeotal(final UUID transactionType) {
		this.transactionType = transactionType;
		return this;
	}

	private Transaction apiTransaction;
	public Transaction getApiPTransaction() {
		return this.apiTransaction;
	}
	public TransactionUpdateCommand setApiTransaction(final Transaction apiTransaction) {
		this.apiTransaction = apiTransaction;
		return this;
	}
	
	@Autowired
	private TransactionRepository transactionRepository;
}
