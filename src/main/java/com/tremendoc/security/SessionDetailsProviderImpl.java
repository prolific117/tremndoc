/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tremendoc.security;

import com.tremendoc.Entity.Customer;
import com.tremendoc.Entity.CustomerSession;
import com.tremendoc.Entity.Repository.CustomerSessionRepository;
import com.tremendoc.Request.SessionDetail;
import com.tremendoc.model.ApplicationUser;
import com.tremendoc.response.VerifyResponse;
import com.tremendoc.security.impl.SessionDetailProvider;
import com.proxy.leanstack.commons.client.vo.ServiceResponse;
import com.proxy.leanstack.commons.util.RequestUtils;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
/**
/**
 *
 * @author prolific
 */
@Service
public class SessionDetailsProviderImpl implements SessionDetailProvider {

     private String adminServiceUrl ="";
     
    @Autowired 
    private CustomerSessionRepository customerSessionRepository;
    
       private static final String UNAUTHENTICATED_MESSAGE = 
            "Authorization credentials invalid";
    private static final String UNAUTHORIZED_MESSAGE = 
            "Unauthorized!";
    private static final String SESSION_EXPIRED_MESSAGE = 
            "Session expired!. Please login again";
    
     private static final Logger logger = Logger.getLogger(RequestUtils.class.getName());
    
    
    @Override
    public SessionDetail getSession(String sessionId) {       
        String[] split = sessionId.split("@");
        /*if("ADMIN".equals(split[0])){
            return verifyAdminSession(sessionId);
        }
        else*/
            return verifyUserSession(sessionId);
    }
    
    /*public SessionDetail verifyAdminSession(String sessionId){
        
    }*/
    
    public SessionDetail verifyUserSession(String sessionId){
        logger.log(Level.INFO, sessionId);
        
        CustomerSession session = customerSessionRepository.findBySessionID(sessionId);
        if(session == null){
            return null;
     }
     
     SessionDetail result = new SessionDetail();
     
     if(!session.getIsActive()){
         result.setIsActive(false);
         return result;
     }
     
     
     //TODO: Improve SessoinDetails to object builder DP
     result.setSessionId(sessionId);
     result.setIsActive(true);
     
     Customer customer = session.getCustomer();
     ApplicationUser applicationUser = new ApplicationUser(customer.getId(), customer.getEmail(), "CUSTOMER");
                       result.setPrincipal(applicationUser);
     result.setPrincipal(applicationUser);
     
     return result;
    }

    @Override
    public ServiceResponse getUnauthenticatedMessage() {
       return new ServiceResponse(ServiceResponse.AUTHORIZATION_ERROR, UNAUTHENTICATED_MESSAGE);
    }

    @Override
    public ServiceResponse getUnauthorizedMessage() {
       return new ServiceResponse(ServiceResponse.AUTHORIZATION_ERROR, UNAUTHORIZED_MESSAGE);
    }

    @Override
    public ServiceResponse getExpiredMessage() {
         return new ServiceResponse(ServiceResponse.AUTHORIZATION_ERROR, SESSION_EXPIRED_MESSAGE);
    }

  @Override
    public Object getInvalidSignature() {
      ServiceResponse response = new ServiceResponse(10);
      response.setDescription("Invalid Signature");

      return response;
    }

    @Override
    public Object getValidationMessage(String message) {
       ServiceResponse response = new ServiceResponse(10);
       response.setDescription(message);

      return response;
    }


    @Override
    public Object getAppVersionCheckFailMessage() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
 
    
}