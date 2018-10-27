/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tremendoc.Entity;

import com.proxy.leanstack.commons.repository.AbstractRepositoryModel;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author prolific
 */
@Entity
@Table(name = "symptomsProfile")
public class SymptomsProfile extends AbstractRepositoryModel implements Serializable {
    
    @Column(name="symptoms")
    private String symptoms;
    
    @OneToOne
    @JoinColumn (name = "customer", referencedColumnName = "id")
    private Customer customer;

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

}
