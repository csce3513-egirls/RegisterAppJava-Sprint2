package edu.uark.registerapp.commands.transactions;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.uark.registerapp.commands.ResultCommandInterface;
import edu.uark.registerapp.commands.exceptions.NotFoundException;
import edu.uark.registerapp.models.api.TransactionEntry;
import edu.uark.registerapp.models.entities.TransactionEntryEntity;
import edu.uark.registerapp.models.repositories.TransactionEntryRepository;

@Service
public class TransactionEntryUpdateCommand implements ResultCommandInterface<TransactionEntry> {
	@Transactional
	@Override
	public TransactionEntry execute() {

		final Optional<TransactionEntryEntity> transactionEntryEntity =
			this.transactionEntryRepository.findById(this.productId); 
		if (!transactionEntryEntity.isPresent()) { // No record with the associated record ID exists in the database.
			throw new NotFoundException("TransactionEntry");
		}

		// Synchronize any incoming changes for UPDATE to the database.
		this.apiTransactionEntry = transactionEntryEntity.get().synchronize(this.apiTransactionEntry);

		// Write, via an UPDATE, any changes to the database.
		this.transactionEntryRepository.save(transactionEntryEntity.get());

		return this.apiTransactionEntry;
	}

	// Properties
	private UUID transactionId;
	public UUID getTransactionId() {
		return this.transactionId;
	}
	public TransactionEntryUpdateCommand setTransactionId(final UUID transactionId) {
		this.transactionId = transactionId;
		return this;
    }
    
    private UUID productId;
	public UUID getProductId() {
		return this.productId;
	}
	public TransactionEntryUpdateCommand setProductId(final UUID productId) {
		this.productId = productId;
		return this;
    }

	private UUID quantity;
	public UUID getQuantity() {
		return this.quantity;
	}
	public TransactionEntryUpdateCommand setQuantity(final UUID quantity) {
		this.quantity = quantity;
		return this;
    }

    private UUID price;
	public UUID getPrice() {
		return this.price;
	}
	public TransactionEntryUpdateCommand setPrice(final UUID price) {
		this.price = price;
		return this;
    }
    

	private TransactionEntry apiTransactionEntry;
	public TransactionEntry getApiTransactionEntry() {
		return this.apiTransactionEntry;
	}
	public TransactionEntryUpdateCommand setApiTransactionEntry(final TransactionEntry apiTransactionEntry) {
		this.apiTransactionEntry = apiTransactionEntry;
		return this;
	}
	
	@Autowired
	private TransactionEntryRepository transactionEntryRepository;
}
