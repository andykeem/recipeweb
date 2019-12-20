package com.example.recipeweb.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.ModelAndView;

@Component
@ControllerAdvice
public class GlobalController {

    @Bean
    public ModelAndView modelAndView() {
        return new ModelAndView();
    }

    /*@ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {MethodArgumentTypeMismatchException.class})
    public ModelAndView handleBadRequest(Exception ex, ModelAndView mav) {
//        ModelAndView mav = modelAndView();
        mav.addObject("exception", ex);
        mav.setViewName("error400");
        return mav;
    }*/

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {MethodArgumentTypeMismatchException.class})
    public String handleBadArugment(Exception ex, Model model) {
        model.addAttribute("exception", ex);
        return "error400";
    }
}
