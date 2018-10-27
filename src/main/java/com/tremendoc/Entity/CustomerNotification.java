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
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author prolific
 */
@Entity
@Table(name = "customerNotifications")
public class CustomerNotification extends AbstractRepositoryModel implements Serializable {
 
    @Column(name="title")
    private String title;
    
    @Column(name="notification")
    private String notification;
    
    @Column(name="red")
    private boolean read;
    
    @ManyToOne
    @JoinColumn (name = "customer", referencedColumnName = "id")
    private Customer customer;

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    
    
}
