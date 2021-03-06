/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tremendoc.Controllers.Customers;

import com.proxy.leanstack.commons.client.vo.ServiceResponse;
import com.tremendoc.Entity.Customer;
import com.tremendoc.Request.SessionDetail;
import com.tremendoc.subsresponse.LifestyleProfile;

/**
 *
 * @author prolific
 */
public interface LifestyleProfileController {
    public LifestyleProfile getLifestyleProfile(Customer customer);
    public ServiceResponse createLifestyleProfile(boolean sexuallyActive, boolean recreationalDrugs, SessionDetail sessionDetail);
}
