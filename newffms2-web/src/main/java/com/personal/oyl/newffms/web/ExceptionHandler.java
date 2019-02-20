package com.personal.oyl.newffms.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

public class ExceptionHandler implements HandlerExceptionResolver {
    
    private static final Logger log = LoggerFactory.getLogger(ExceptionHandler.class);

    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
            Exception ex) {
        
        String ticketNo = String.valueOf(System.currentTimeMillis());
        
        StringBuffer strBuffer = new StringBuffer();
        strBuffer.append("Exception occured. [TICKET-" + ticketNo + "], ");
        
        log.error(strBuffer.toString(), ex);
        
        
        ModelAndView rlt = new ModelAndView("exception");
        
        rlt.getModel().put("tickNo", ticketNo);
        
        return rlt;
    }

}
