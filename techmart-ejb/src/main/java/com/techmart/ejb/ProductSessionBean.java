package com.techmart.ejb;

import com.techmart.core.dto.request.ProductRequestDto;
import com.techmart.core.dto.response.ProductResponseDto;
import com.techmart.core.entity.Category;
import com.techmart.core.entity.Product;
import com.techmart.core.service.ProductService;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class ProductSessionBean implements ProductService {

    @PersistenceContext(unitName = "TechMartPU")
    private EntityManager em;

    @Override
    public void addProduct(ProductRequestDto requestDto) {

        Category category = em.find(Category.class, requestDto.getCategoryId());

        if (category == null) {
            throw new IllegalArgumentException("Selected category does not exist!");
        }

        Product product = Product.builder()
                .name(requestDto.getName())
                .price(requestDto.getPrice())
                .stockQuantity(requestDto.getStockQuantity())
                .imageUrl(requestDto.getImageUrl())
                .category(category)
                .build();

        em.persist(product);
    }

    @Override
    public List<ProductResponseDto> getAllProducts() {
        return em.createQuery("SELECT p FROM Product p JOIN FETCH p.category", Product.class)
                .getResultStream()
                .map(product -> ProductResponseDto.builder()
                        .id(product.getId())
                        .name(product.getName())
                        .price(product.getPrice())
                        .stockQuantity(product.getStockQuantity())
                        .imageUrl(product.getImageUrl())
                        .categoryId(product.getCategory().getId())
                        .categoryName(product.getCategory().getName())
                        .build())
                .collect(Collectors.toList());
    }
}