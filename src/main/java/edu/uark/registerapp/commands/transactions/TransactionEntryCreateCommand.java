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
public class TransactionEntryCreateCommand implements ResultCommandInterface<TransactionEntry> {
	@Transactional
	@Override
	public TransactionEntry execute() {


        final TransactionEntryEntity transactionEntryEntity =
            this.transactionEntryRepository.save(new TransactionEntryEntity(apiTransactionEntry)) ;


        this.apiTransactionEntry.setId(transactionEntryEntity.getId());
        this.apiTransactionEntry.setCreatedOn(transactionEntryEntity.getCreatedOn());

        return this.apiTransactionEntry;

	}

	// Properties
	private UUID transactionId;
	public UUID getTransactionId() {
		return this.transactionId;
	}
	public TransactionEntryCreateCommand setTransactionId(final UUID transactionId) {
		this.transactionId = transactionId;
		return this;
    }
    
    private UUID productId;
	public UUID getProductId() {
		return this.productId;
	}
	public TransactionEntryCreateCommand setProductId(final UUID productId) {
		this.productId = productId;
		return this;
    }

	private UUID quantity;
	public UUID getQuantity() {
		return this.quantity;
	}
	public TransactionEntryCreateCommand setQuantity(final UUID quantity) {
		this.quantity = quantity;
		return this;
    }

    private UUID price;
	public UUID getPrice() {
		return this.price;
	}
	public TransactionEntryCreateCommand setPrice(final UUID price) {
		this.price = price;
		return this;
    }
    

	private TransactionEntry apiTransactionEntry;
	public TransactionEntry getApiTransactionEntry() {
		return this.apiTransactionEntry;
	}
	public TransactionEntryCreateCommand setApiTransactionEntry(final TransactionEntry apiTransactionEntry) {
		this.apiTransactionEntry = apiTransactionEntry;
		return this;
	}
	
	@Autowired
	private TransactionEntryRepository transactionEntryRepository;
}
