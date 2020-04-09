package edu.uark.registerapp.controllers;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import edu.uark.registerapp.commands.products.ProductQuery;
import edu.uark.registerapp.controllers.enums.ViewModelNames;
import edu.uark.registerapp.controllers.enums.ViewNames;
import edu.uark.registerapp.models.api.Product;
import edu.uark.registerapp.models.entities.ActiveUserEntity;
import edu.uark.registerapp.models.entities.TransactionEntity;
import edu.uark.registerapp.models.enums.EmployeeClassification;

@Controller
@RequestMapping(value = "/transactionDetail")
public class TransactionDetailRouteController extends BaseRouteController {
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
        return modelAndView;
        

        
    
    }


    

}