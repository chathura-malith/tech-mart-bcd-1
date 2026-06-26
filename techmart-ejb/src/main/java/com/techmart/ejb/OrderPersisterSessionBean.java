//package com.techmart.ejb;
//
//import com.techmart.core.dto.message.OrderMessageDto;
//import com.techmart.core.entity.Address;
//import com.techmart.core.entity.Order;
//import com.techmart.core.entity.OrderItem;
//import com.techmart.core.entity.Product;
//import com.techmart.core.entity.User;
//import com.techmart.core.enums.OrderStatus;
//import jakarta.ejb.Stateless;
//import jakarta.json.bind.Jsonb;
//import jakarta.json.bind.JsonbBuilder;
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.PersistenceContext;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Stateless
//public class OrderPersisterSessionBean {
//
//    @PersistenceContext
//    private EntityManager em;
//
//    // 🌟 මේක දැන් Stateless Bean එකක් නිසා ඉබේම Database Transaction එකක් හැදෙනවා
//    public void saveOrderToDatabase(String jsonMessage) {
//        try (Jsonb jsonb = JsonbBuilder.create()) {
//            OrderMessageDto dto = jsonb.fromJson(jsonMessage, OrderMessageDto.class);
//
//            Order order = new Order();
//            order.setOrderDate(LocalDateTime.now());
//            order.setTotalAmount(dto.getTotalAmount());
//            order.setStatus(OrderStatus.PENDING);
//
//            order.setUser(em.getReference(User.class, dto.getUserId()));
//            order.setAddress(em.getReference(Address.class, dto.getAddressId()));
//
//            List<OrderItem> items = dto.getItems().stream().map(itemDto -> {
//                OrderItem item = new OrderItem();
//                item.setOrder(order);
//                item.setProduct(em.getReference(Product.class, itemDto.getProductId()));
//                item.setQuantity(itemDto.getQuantity());
//                item.setUnitPrice(itemDto.getUnitPrice());
//                return item;
//            }).collect(Collectors.toList());
//
//            order.setOrderItems(items);
//
//            em.persist(order);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new RuntimeException("Database error during order processing", e);
//        }
//    }
//}


package com.techmart.ejb;

import com.techmart.core.dto.message.OrderMessageDto;
import com.techmart.core.entity.Address;
import com.techmart.core.entity.Order;
import com.techmart.core.entity.OrderItem;
import com.techmart.core.entity.Product;
import com.techmart.core.entity.User;
import com.techmart.core.enums.OrderStatus;
import jakarta.ejb.Stateless;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class OrderPersisterSessionBean {

    @PersistenceContext
    private EntityManager em;

    public void saveOrderToDatabase(String jsonMessage) {
        try (Jsonb jsonb = JsonbBuilder.create()) {
            OrderMessageDto dto = jsonb.fromJson(jsonMessage, OrderMessageDto.class);

            Order order = Order.builder()
                    .orderDate(LocalDateTime.now())
                    .totalAmount(dto.getTotalAmount())
                    .status(OrderStatus.PENDING)
                    .user(em.getReference(User.class, dto.getUserId()))
                    .address(em.getReference(Address.class, dto.getAddressId()))
                    .build();

            List<OrderItem> items = dto.getItems().stream().map(itemDto ->
                    OrderItem.builder()
                            .order(order)
                            .product(em.getReference(Product.class, itemDto.getProductId()))
                            .quantity(itemDto.getQuantity())
                            .unitPrice(itemDto.getUnitPrice())
                            .build()
            ).collect(Collectors.toList());

            order.setOrderItems(items);

            em.persist(order);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Database error during order processing", e);
        }
    }
}