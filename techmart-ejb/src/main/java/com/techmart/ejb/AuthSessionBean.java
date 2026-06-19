package com.techmart.ejb;

import com.techmart.core.dto.request.LoginRequestDto;
import com.techmart.core.dto.response.UserResponseDto;
import com.techmart.core.entity.User;
import com.techmart.core.service.AuthService;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

@Stateless
public class AuthSessionBean implements AuthService {

    @PersistenceContext(unitName = "TechMartPU")
    private EntityManager em;

    @Override
    public UserResponseDto login(LoginRequestDto loginDto) {
        try {
            TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class);
            query.setParameter("email", loginDto.getEmail());
            User user = query.getSingleResult();


            if (user.getPassword().equals(loginDto.getPassword())) {
                return UserResponseDto.builder()
                        .id(user.getId())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .email(user.getEmail())
                        .mobile(user.getMobile())
                        .role(user.getRole().name())
                        .build();
            } else {
                return null;
            }

        } catch (NoResultException e) {
            return null;
        }
    }
}