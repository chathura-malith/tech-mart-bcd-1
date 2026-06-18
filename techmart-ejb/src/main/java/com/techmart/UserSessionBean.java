package com.techmart;


import com.techmart.core.service.UserService;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Stateless
public class UserSessionBean implements UserService {

    @PersistenceContext(unitName = "TechMartPU")
    private EntityManager em;


    @Override
    public void getUserById(Long id) {

    }
}