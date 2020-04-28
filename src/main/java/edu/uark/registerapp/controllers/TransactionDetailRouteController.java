package edu.uark.registerapp.controllers;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import edu.uark.registerapp.commands.exceptions.NotFoundException;
import edu.uark.registerapp.controllers.enums.QueryParameterNames;
import edu.uark.registerapp.controllers.enums.ViewModelNames;
import edu.uark.registerapp.controllers.enums.ViewNames;
import edu.uark.registerapp.models.api.Product;
import edu.uark.registerapp.models.api.Transaction;
import edu.uark.registerapp.models.api.TransactionEntry;
import edu.uark.registerapp.models.entities.ActiveUserEntity;
import edu.uark.registerapp.models.entities.TransactionEntity;
import edu.uark.registerapp.models.enums.EmployeeClassification;
import edu.uark.registerapp.commands.products.ProductsQuery;
import edu.uark.registerapp.commands.transactions.TransactionQuery;
import edu.uark.registerapp.commands.transactions.TransactionEntriesQuery;
import edu.uark.registerapp.commands.transactions.TransactionEntryCreateCommand;
import edu.uark.registerapp.commands.transactions.TransactionUpdateCommand;
//TODO: clean up comments(remove the debug stuff)


@Controller
@RequestMapping(value = "/transactionDetail")
public class TransactionDetailRouteController extends BaseRouteController {

    // method thats executed everytime the transactionDetail page is displayed
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView start(
        @RequestParam final Map<String, String> queryParameters,
		final HttpServletRequest request
    ) {
        
        final Optional<ActiveUserEntity> activeUserEntity = this.getCurrentUser(request);
        if(!activeUserEntity.isPresent()){
            return this.buildInvalidSessionResponse();
        }
        

        final ModelAndView modelAndView =
        this.setErrorMessageFromQueryString(
            new ModelAndView(ViewNames.TRANSACTION_DETAIL.getViewName()),
            queryParameters);
            modelAndView.addObject( //TODO: Do we need the user to be elevated?
                ViewModelNames.IS_ELEVATED_USER.getValue(),
                this.isElevatedUser(activeUserEntity.get()));
    
            try {
                modelAndView.addObject(
                    ViewModelNames.PRODUCTS.getValue(),
                    this.productsQuery.execute());
            } catch (final Exception e) {
                modelAndView.addObject(
                    ViewModelNames.ERROR_MESSAGE.getValue(),
                    e.getMessage());
                modelAndView.addObject(
                    ViewModelNames.PRODUCTS.getValue(),
                    (new Product[0]));
            }
            final List<TransactionEntry> transactionEntries;
            try {
                transactionEntries = this.transactionEntriesQuery.execute();
                final List<TransactionEntry> actualTransactionEntries = new LinkedList<>();
                //all transactionentris with all 0 transactionIds are current transaction, this removes all others
                for (int i = 0; i < transactionEntries.size(); i++)
                {
                    UUID defaultUUID = UUID.fromString("00000000-0000-0000-0000-000000000000");
                    //System.out.println("in the " + i + " loop");
                    if(defaultUUID.equals(transactionEntries.get(i).getTransactionId()))
                    {
                        //System.out.println("the " + i + " has the " + transactionEntries.get(i).getTransactionId().toString() + "yeet");
                        //System.out.println("removing " + i + " from the list");
                        actualTransactionEntries.add(transactionEntries.get(i));
                    }

                }

                modelAndView.addObject(
                    ViewModelNames.TRANSACTION_ENTRIES.getValue(),
                    actualTransactionEntries);
            } catch (final Exception e) {
                modelAndView.addObject(
                    ViewModelNames.ERROR_MESSAGE.getValue(),
                    e.getMessage());
                modelAndView.addObject(
                    ViewModelNames.TRANSACTION_ENTRIES.getValue(),
                    (new TransactionEntry[0]));
            }



            modelAndView.addObject(
                ViewModelNames.TRANSACTION.getValue(),
                (new Transaction()));
            
        return modelAndView;
    }

    // method thats executed when a product is added to a transaction
    //@RequestMapping(value = "/{transactionId}", method = RequestMethod.POST)
    //public ModelAndView addToTransaction(
    //    @PathVariable final UUID transactionId,
    //    TransactionEntry transactionEntry,
    //    HttpServletRequest request
    //) { 
//
    //    // TO-DO: upon execution, make sure transactionEntry is saved with correct transaction ID
    //    // then redirct back to transaction detail and possibly add any new transaction entry objects to ModelAndView?
    //    return new ModelAndView(REDIRECT_PREPEND.concat(
    //        ViewNames.TRANSACTION_DETAIL.getRoute()));
    //}


    @Autowired
    private TransactionQuery transactionQuery;

    @Autowired
    private TransactionUpdateCommand transactionUpdateCommand;

    @Autowired
    private ProductsQuery productsQuery;
    
    @Autowired
    private TransactionEntriesQuery transactionEntriesQuery;

}
