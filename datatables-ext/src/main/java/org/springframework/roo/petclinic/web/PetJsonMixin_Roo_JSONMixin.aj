// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.springframework.roo.petclinic.web;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.Set;
import org.springframework.roo.petclinic.domain.Owner;
import org.springframework.roo.petclinic.domain.Visit;
import org.springframework.roo.petclinic.web.OwnerDeserializer;
import org.springframework.roo.petclinic.web.PetJsonMixin;

privileged aspect PetJsonMixin_Roo_JSONMixin {
    
    /**
     * TODO Auto-generated attribute documentation
     */
    @JsonIgnore
    private Set<Visit> PetJsonMixin.visits;
    
    /**
     * TODO Auto-generated attribute documentation
     */
    @JsonDeserialize(using = OwnerDeserializer.class)
    private Owner PetJsonMixin.owner;
    
    /**
     * TODO Auto-generated method documentation
     * 
     * @return Set
     */
    public Set<Visit> PetJsonMixin.getVisits() {
        return visits;
    }
    
    /**
     * TODO Auto-generated method documentation
     * 
     * @param visits
     */
    public void PetJsonMixin.setVisits(Set<Visit> visits) {
        this.visits = visits;
    }
    
    /**
     * TODO Auto-generated method documentation
     * 
     * @return Owner
     */
    public Owner PetJsonMixin.getOwner() {
        return owner;
    }
    
    /**
     * TODO Auto-generated method documentation
     * 
     * @param owner
     */
    public void PetJsonMixin.setOwner(Owner owner) {
        this.owner = owner;
    }
    
}