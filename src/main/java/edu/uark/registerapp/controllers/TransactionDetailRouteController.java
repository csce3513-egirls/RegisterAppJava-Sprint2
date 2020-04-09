package edu.uark.registerapp.controllers;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import edu.uark.registerapp.commands.exceptions.NotFoundException;
import edu.uark.registerapp.controllers.enums.QueryParameterNames;
import edu.uark.registerapp.controllers.enums.ViewModelNames;
import edu.uark.registerapp.controllers.enums.ViewNames;

@Controller
@RequestMapping(value = "/transactionDetail")
public class TransactionDetailRouteController extends BaseRouteController {
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView showTransaction(
		@RequestParam final Map<String, String> queryParameters
	) {

		try {
		} catch (NotFoundException e) {
			return new ModelAndView(
				REDIRECT_PREPEND.concat(
					ViewNames.TRANSACTION_DETAIL.getRoute()));
		}

		ModelAndView modelAndView =
				new ModelAndView(ViewNames.TRANSACTION_DETAIL.getViewName());

		return modelAndView;
	}
}
