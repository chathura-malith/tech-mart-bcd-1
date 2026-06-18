package com.techmart.core.service;


import jakarta.ejb.Remote;

@Remote
public interface UserService {
    void getUserById(Long id);

}
