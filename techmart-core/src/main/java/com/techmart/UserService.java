package com.techmart;


import jakarta.ejb.Remote;

import java.util.List;

@Remote
public interface UserService {
    void getUserById(Long id);

}
