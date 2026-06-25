package com.techmart.ejb;

import com.techmart.core.dto.request.AddressRequestDto;
import com.techmart.core.dto.response.AddressResponseDto;
import com.techmart.core.entity.Address;
import com.techmart.core.entity.User;
import com.techmart.core.service.AddressService;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class AddressSessionBean implements AddressService {

    @PersistenceContext(unitName = "TechMartPU")
    private EntityManager em;

    @Override
    public List<AddressResponseDto> getUserAddresses(Integer userId) {
        return em.createQuery("SELECT a FROM Address a WHERE a.user.id = :userId", Address.class)
                .setParameter("userId", userId)
                .getResultStream()
                .map(address -> AddressResponseDto.builder()
                        .id(address.getId())
                        .streetAddress(address.getStreetAddress())
                        .city(address.getCity())
                        .postalCode(address.getPostalCode())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public Integer addAddress(Integer userId, AddressRequestDto requestDto) {
        User user = em.find(User.class, userId);

        if (user != null) {
            Address address = Address.builder()
                    .streetAddress(requestDto.getStreetAddress())
                    .city(requestDto.getCity())
                    .postalCode(requestDto.getPostalCode())
                    .user(user)
                    .build();

            em.persist(address);
            em.flush();

            return address.getId();
        }
        return null;
    }
}