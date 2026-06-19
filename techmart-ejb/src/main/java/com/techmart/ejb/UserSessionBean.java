package com.techmart.ejb;

import com.techmart.core.dto.request.UserRequestDto;
import com.techmart.core.dto.response.UserResponseDto;
import com.techmart.core.entity.User;
import com.techmart.core.enums.UserRole;
import com.techmart.core.service.UserService;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class UserSessionBean implements UserService {

    @PersistenceContext(unitName = "TechMartPU")
    private EntityManager em;

    @Override
    public void registerUser(UserRequestDto requestDto) {
        User user = User.builder()
                .firstName(requestDto.getFirstName())
                .lastName(requestDto.getLastName())
                .email(requestDto.getEmail())
                .mobile(requestDto.getMobile())
                .password(requestDto.getPassword())
                .role(UserRole.CUSTOMER)
                .build();

        em.persist(user);
    }

    @Override
    public UserResponseDto getUserById(Integer id) {
        User user = em.find(User.class, id);
        return user != null ? convertToResponseDto(user) : null;
    }

    @Override
    public UserResponseDto getUserByEmail(String email) {
        try {
            TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class);
            query.setParameter("email", email);
            return convertToResponseDto(query.getSingleResult());
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<UserResponseDto> getAllUsers() {
        return em.createQuery("SELECT u FROM User u", User.class)
                .getResultStream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public void updateUser(Integer id, UserRequestDto requestDto) {
        User existingUser = em.find(User.class, id);
        if (existingUser != null) {
            existingUser.setFirstName(requestDto.getFirstName());
            existingUser.setLastName(requestDto.getLastName());
            existingUser.setMobile(requestDto.getMobile());

            if (requestDto.getPassword() != null && !requestDto.getPassword().trim().isEmpty()) {
                existingUser.setPassword(requestDto.getPassword());
            }
            em.merge(existingUser);
        }
    }

    @Override
    public void deleteUser(Integer id) {
        User user = em.find(User.class, id);
        if (user != null) {
            em.remove(user);
        }
    }

    private UserResponseDto convertToResponseDto(User user) {
        return UserResponseDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .mobile(user.getMobile())
                .role(user.getRole().name())
                .build();
    }
}