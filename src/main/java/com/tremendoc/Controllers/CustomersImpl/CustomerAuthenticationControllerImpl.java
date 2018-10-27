/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tremendoc.Controllers.CustomersImpl;

import com.tremendoc.Controllers.Customers.CustomerAuthenticationController;
import com.tremendoc.Controllers.Customers.SessionController;
import com.tremendoc.Entity.Customer;
import com.tremendoc.Entity.CustomerSession;
import com.tremendoc.Entity.Repository.CustomerRepository;
import com.tremendoc.Entity.Repository.CustomerSessionRepository;
import com.tremendoc.response.CustomerCreationResponse;
import com.tremendoc.response.VerifyResponse;
import com.proxy.leanstack.commons.client.vo.ServiceResponse;
import com.proxy.leanstack.commons.security.EncryptionTool;
import com.proxy.leanstack.commons.util.RequestUtils;
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
public class CustomerAuthenticationControllerImpl implements CustomerAuthenticationController{
    
    @Autowired 
    private CustomerRepository customerRepository;
   
    @Autowired
    private SessionController sessionController;
   
    @Autowired 
    private CustomerSessionRepository customerSessionRepository;
    
    private static final Logger logger = Logger.getLogger(RequestUtils.class.getName());

    @Override
    public CustomerCreationResponse authenticateCustomer(String email, String password) {
        
        CustomerCreationResponse response = new CustomerCreationResponse(ServiceResponse.ERROR);
        
        //check for customer existence
        Customer existingCustomer = customerRepository.findByEmail(email);
        if(existingCustomer == null){
            response.setDescription("Customer does not exist");
            return response;
        }
        
        //validate password
        try{
            Boolean passwordValid = EncryptionTool.comparePassword(password, existingCustomer.getPassword());
            if(passwordValid){
                response.setEmail(email);
                response.setFirstname(existingCustomer.getFirstname());
                response.setLastName(existingCustomer.getLastName());
                response.setGender(existingCustomer.getGender());
                response.setAge(existingCustomer.getAge());
                response.setCustomerId(existingCustomer.getId());
                response.setInviteCode(existingCustomer.getInviteCode());
                
                //delete existing
                //create redis session
                String sessionKey = sessionController.createCustomerSession(existingCustomer);
                
                if(!"".equals(sessionKey)){
                   response.setSessionId(sessionKey);
                   response.setCode(ServiceResponse.SUCCESS);
                   response.setDescription(ServiceResponse.GENERAL_SUCCESS_MESSAGE); 
                }
                else{
                   response.setCode(ServiceResponse.INCOMPLETE_ERROR);
                   response.setDescription("Unable to create session"); 
                }                         
            }
            else{
                response.setDescription(ServiceResponse.INCORRECT_PASSWORD);
            }
            
        }
        catch(Exception ex){
            response.setDescription(ServiceResponse.GENERAL_ERROR_MESSAGE);
        }
        
        //send response
        return response;
    }
    
    @Override
    public VerifyResponse verifySession(String sessionId) {
        VerifyResponse response = new VerifyResponse();
        CustomerSession csession = customerSessionRepository.findBySessionID(sessionId);
        
        if(csession == null){
            response.setCode(ServiceResponse.ERROR);
            response.setValid(false);
            return response;
        }
        
        if(csession.getIsActive() == false){
            response.setCode(ServiceResponse.ERROR);
            response.setValid(false);
            return response;
        }
        else{
            response.setValid(true);
        }
        
        response.setCode(ServiceResponse.SUCCESS);
        response.setAccountId(csession.getCustomer().getId());
        response.setEmail(csession.getCustomer().getEmail());
        response.setStatus("CUSTOMER");
        
        return response;
    }
}
