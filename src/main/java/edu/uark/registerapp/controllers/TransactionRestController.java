package edu.uark.registerapp.controllers;

import java.util.UUID;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import edu.uark.registerapp.commands.activeUsers.ValidateActiveUserCommand;
import edu.uark.registerapp.controllers.enums.ViewNames;
import edu.uark.registerapp.models.api.ApiResponse;
import edu.uark.registerapp.models.api.Product;
import edu.uark.registerapp.commands.transactions.TransactionDeleteCommand;
import edu.uark.registerapp.commands.transactions.TransactionEntryCreateCommand;
import edu.uark.registerapp.commands.transactions.TransactionEntryDeleteCommand;
import edu.uark.registerapp.models.entities.ActiveUserEntity;
import edu.uark.registerapp.models.entities.TransactionEntity;
import edu.uark.registerapp.models.api.Transaction;
import edu.uark.registerapp.models.api.TransactionEntry;
import edu.uark.registerapp.commands.transactions.TransactionQuery;

// when a variable within an object that is of type /api/transaction is manipulated, the controller maps here
@RestController
@RequestMapping(value = "/api/transactionEntry")
public class TransactionRestController extends BaseRestController{
    // method that occurs when a transactionEntry is created
    @RequestMapping(value = "/", method = RequestMethod.POST)
	public @ResponseBody ApiResponse createTransactionEntry(
		@RequestBody final TransactionEntry transactionEntry,
		final HttpServletRequest request,
		final HttpServletResponse response
	) {

		return this.transactionEntryCreateCommand
			.setApiTransactionEntry(transactionEntry)
			.execute();
	}
   
    @RequestMapping(value="/{id}", method = RequestMethod.DELETE)
	public @ResponseBody ApiResponse cancelTransactionEntry(
        @PathVariable final UUID transactionEntryId,
        final HttpServletRequest request
	) { 
        try{
            this.transactionEntryDeleteCommand.
                setId(transactionEntryId).
                execute();
        }
        catch(Exception e){
            return (new ApiResponse())
			.setRedirectUrl(ViewNames.MAIN_MENU.getRoute());
        }
		return (new ApiResponse())
			.setRedirectUrl(ViewNames.MAIN_MENU.getRoute());
	}

    @Autowired
    private TransactionEntryDeleteCommand transactionEntryDeleteCommand;

    @Autowired
    private ValidateActiveUserCommand validateActiveUserCommand;

    @Autowired
    private TransactionEntryCreateCommand transactionEntryCreateCommand;

    @Autowired
    private TransactionQuery transactionQuery;
}