package edu.uark.registerapp.commands.transactions;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.uark.registerapp.commands.ResultCommandInterface;
import edu.uark.registerapp.models.api.TransactionEntry;
import edu.uark.registerapp.models.entities.TransactionEntryEntity;
import edu.uark.registerapp.models.repositories.TransactionEntryRepository;

@Service
public class TransactionEntriesQuery implements ResultCommandInterface<List<TransactionEntry>> {
	@Override
	public List<TransactionEntry> execute() {
		final LinkedList<TransactionEntry> transactionEntries = new LinkedList<TransactionEntry>();

		for (final TransactionEntryEntity transactionEntryEntity : transactionEntryRepository.findAll()) {
			transactionEntries.addLast(new TransactionEntry(transactionEntryEntity));
        }
        
        //TODO: Maybe check for transaction ID to determine if already part of a finished transaction
		
		return transactionEntries;
	}

	@Autowired
	TransactionEntryRepository transactionEntryRepository;
}
