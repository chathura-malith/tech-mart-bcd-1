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

    @Override
    public List<ProductResponseDto> searchProducts(String keyword, Integer categoryId, int page, int size) {
        StringBuilder queryStr = new StringBuilder("SELECT p FROM Product p JOIN FETCH p.category WHERE 1=1");

        if (keyword != null && !keyword.trim().isEmpty()) {
            queryStr.append(" AND LOWER(p.name) LIKE LOWER(:keyword)");
        }

        if (categoryId != null && categoryId > 0) {
            queryStr.append(" AND p.category.id = :categoryId");
        }

        var query = em.createQuery(queryStr.toString(), Product.class);

        if (keyword != null && !keyword.trim().isEmpty()) {
            query.setParameter("keyword", "%" + keyword.trim() + "%");
        }
        if (categoryId != null && categoryId > 0) {
            query.setParameter("categoryId", categoryId);
        }

        query.setFirstResult((page - 1) * size);
        query.setMaxResults(size);

        return query.getResultStream()
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

    @Override
    public long getProductTotalCount(String keyword, Integer categoryId) {
        StringBuilder queryStr = new StringBuilder("SELECT COUNT(p) FROM Product p WHERE 1=1");

        if (keyword != null && !keyword.trim().isEmpty()) {
            queryStr.append(" AND LOWER(p.name) LIKE LOWER(:keyword)");
        }
        if (categoryId != null && categoryId > 0) {
            queryStr.append(" AND p.category.id = :categoryId");
        }

        var query = em.createQuery(queryStr.toString(), Long.class);

        if (keyword != null && !keyword.trim().isEmpty()) {
            query.setParameter("keyword", "%" + keyword.trim() + "%");
        }
        if (categoryId != null && categoryId > 0) {
            query.setParameter("categoryId", categoryId);
        }

        return query.getSingleResult();
    }
}