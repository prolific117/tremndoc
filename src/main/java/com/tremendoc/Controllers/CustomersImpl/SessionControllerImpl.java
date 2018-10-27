/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tremendoc.Controllers.CustomersImpl;

import com.tremendoc.Controllers.Customers.SessionController;
import com.tremendoc.Entity.Customer;
import com.tremendoc.Entity.CustomerSession;
import com.tremendoc.Entity.Repository.CustomerSessionRepository;
import com.proxy.leanstack.commons.repository.RedisRepo;
import com.proxy.leanstack.commons.util.RequestUtils;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author prolific
 */

@Service
public class SessionControllerImpl implements SessionController{
    
    private static final Logger logger = Logger.getLogger(RequestUtils.class.getName());
    
    @Autowired 
    private CustomerSessionRepository customerSessionRepository;
  
    @Override
    public String createCustomerSession(Customer customer) {
   //RedisRepo repo = new RedisRepo();
        
        String uniqueKey = "CUSTOMER@"+ new Timestamp(System.currentTimeMillis()).getTime()+"@"+customer.getId();
        
        try {
            CustomerSession session = new CustomerSession();
            
            session.setCustomer(customer);
            session.setSessionId(uniqueKey);
            
            customerSessionRepository.disableActiveSessions(new Date(), customer);
            customerSessionRepository.save(session);
            
            return uniqueKey;
        } catch (Exception ex) {
            logger.log(Level.SEVERE, ex.getMessage());
            return "";
        }
    }
  
}
