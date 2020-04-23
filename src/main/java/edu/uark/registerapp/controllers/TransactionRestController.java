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
import edu.uark.registerapp.commands.products.ProductCreateCommand;
import edu.uark.registerapp.commands.products.ProductDeleteCommand;
import edu.uark.registerapp.commands.products.ProductUpdateCommand;
import edu.uark.registerapp.controllers.enums.ViewNames;
import edu.uark.registerapp.models.api.ApiResponse;
import edu.uark.registerapp.models.api.Product;
import edu.uark.registerapp.commands.transactions.TransactionDeleteCommand;
import edu.uark.registerapp.models.entities.ActiveUserEntity;
import edu.uark.registerapp.models.entities.TransactionEntity;
import edu.uark.registerapp.models.api.Transaction;
import edu.uark.registerapp.commands.transactions.TransactionQuery;

// Added mapping and api response so shopping cart image can redirect
// to transactionDetail.html
@RestController
@RequestMapping(value = "/api")
public class TransactionRestController extends BaseRestController{
    @RequestMapping(value = "/shoppingCart", method = RequestMethod.GET)
    public @ResponseBody ApiResponse redirectToShoppingCart(
        final HttpServletRequest request,
        final HttpServletResponse response
    ){
        return (new ApiResponse())
			.setRedirectUrl(ViewNames.TRANSACTION_DETAIL.getRoute());
    }

    @RequestMapping(value = "/{transactionId}", method = RequestMethod.DELETE)
    public @ResponseBody ApiResponse deleteTransaction(
        @PathVariable final UUID transactionId, //TODO: make sure this works for deleting transaction
        final HttpServletRequest request,
        final HttpServletResponse response
    ) {

        //TODO: not sure if this is the correct way to check if current user matches employeeId for transaction
        final ActiveUserEntity activeUserEntity =
         this.validateActiveUserCommand.setSessionKey(request.getSession().getId()).execute();
        final Transaction transaction = 
        this.transactionQuery.setTransactionId(transactionId).execute();


        if(activeUserEntity.getEmployeeId() != transaction.getCashierId())
        {
            final ApiResponse apiResponse = 
                this.redirectUserNotElevated(request, response);
            return apiResponse;
        }   //TODO: is this a good way to deal with trying to delete a transaction that isn't yours?
            //also BaseRestController has no method for noPermissionsResponse like BaseRouteController


        this.transactionDeleteCommand.
            setTransactionId(transactionId).
            execute();

        return (new ApiResponse())
		    .setRedirectUrl(ViewNames.MAIN_MENU.getRoute());
    }

    
    @Autowired
    private TransactionDeleteCommand transactionDeleteCommand;

    @Autowired
    private ValidateActiveUserCommand validateActiveUserCommand;

    @Autowired
    private TransactionQuery transactionQuery;
}