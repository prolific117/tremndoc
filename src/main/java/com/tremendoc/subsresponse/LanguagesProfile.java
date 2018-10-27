/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tremendoc.subsresponse;

import javax.persistence.Column;

/**
 *
 * @author prolific
 */
public class LanguagesProfile {
    
    private boolean english;
    
    private boolean yoruba;
     
    private boolean hausa;
      
    private boolean ibo;
       
    private boolean french;

    public boolean isEnglish() {
        return english;
    }

    public void setEnglish(boolean english) {
        this.english = english;
    }

    public boolean isYoruba() {
        return yoruba;
    }

    public void setYoruba(boolean yoruba) {
        this.yoruba = yoruba;
    }

    public boolean isHausa() {
        return hausa;
    }

    public void setHausa(boolean hausa) {
        this.hausa = hausa;
    }

    public boolean isIbo() {
        return ibo;
    }

    public void setIbo(boolean ibo) {
        this.ibo = ibo;
    }

    public boolean isFrench() {
        return french;
    }

    public void setFrench(boolean french) {
        this.french = french;
    }
    
    
}
