package edu.uark.registerapp.controllers;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

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
import edu.uark.registerapp.commands.transactions.TransactionUpdateCommand;

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
                modelAndView.addObject(
                        ViewModelNames.TRANSACTION.getValue(),
                        (new Transaction()));
            } catch (final Exception e) {
                modelAndView.addObject(
                    ViewModelNames.ERROR_MESSAGE.getValue(),
                    e.getMessage());
                modelAndView.addObject(
                    ViewModelNames.PRODUCTS.getValue(),
                    (new Product[0]));
            }
        return modelAndView;
    }

    // method thats executed when a product is added to a transaction
    @RequestMapping(value = "/{transactionId}", method = RequestMethod.POST)
    public ModelAndView addToTransaction(
        @PathVariable final UUID transactionId,
        TransactionEntry transactionEntry,
        HttpServletRequest request
    ) { 

        // TO-DO: upon execution, make sure transactionEntry is saved with correct transaction ID
        // then redirct back to transaction detail and possibly add any new transaction entry objects to ModelAndView?
        return new ModelAndView(REDIRECT_PREPEND.concat(
            ViewNames.TRANSACTION_DETAIL.getRoute()));
    }


    @Autowired
    private TransactionQuery transactionQuery;

    @Autowired
    private TransactionUpdateCommand transactionUpdateCommand;

    @Autowired
	private ProductsQuery productsQuery;
}
