package it.unimib.discover.controller.impl;

import java.nio.file.AccessDeniedException;

import org.apache.log4j.Logger;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class ExceptionHandlerController  {
	
	final static Logger log = Logger.getLogger(ExceptionHandlerController.class);
	
	@ExceptionHandler(AccessDeniedException.class)
	public ModelAndView accessDenyException(Exception ex) {
		log.error(ex.getMessage(), ex);
		
		return new ModelAndView("error/error403");
	}
	
	@ExceptionHandler(NoHandlerFoundException.class)
	public ModelAndView handlePageNotFound(Exception ex) {
		log.error(ex.getMessage());

		return new ModelAndView("error/error404");
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	public ModelAndView handleInternalErrorException(Exception ex) {
		log.error(ex.getMessage(), ex);

		return new ModelAndView("error/error500");
	}
	
	@ExceptionHandler(Exception.class)
	public ModelAndView handleAllException(Exception ex,Model model) {
		ModelAndView mv = new ModelAndView("error/error500");
		log.error(ex.getMessage(), ex);
		model.addAttribute("stacktrace",ex.getStackTrace());
		mv.addObject("stacktrace", ex.getStackTrace());
		return mv;
	}

}