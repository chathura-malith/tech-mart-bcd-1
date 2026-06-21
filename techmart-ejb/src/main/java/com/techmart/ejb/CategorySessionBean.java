//package com.techmart.ejb;
//
//import com.techmart.core.dto.request.CategoryRequestDto;
//import com.techmart.core.dto.response.CategoryResponseDto;
//import com.techmart.core.entity.Category;
//import com.techmart.core.service.CategoryService;
//import jakarta.ejb.Stateless;
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.PersistenceContext;
//import jakarta.persistence.TypedQuery;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Stateless
//public class CategorySessionBean implements CategoryService {
//
//    @PersistenceContext(unitName = "TechMartPU")
//    private EntityManager em;
//
//    @Override
//    public void addCategory(CategoryRequestDto requestDto) {
//
//        TypedQuery<Long> query = em.createQuery(
//                "SELECT COUNT(c) FROM Category c WHERE c.name = :name", Long.class);
//        query.setParameter("name", requestDto.getName());
//        Long count = query.getSingleResult();
//
//        if (count > 0) {
//            throw new IllegalArgumentException("Category with this name already exists!");
//        }
//        Category category = Category.builder()
//                .name(requestDto.getName())
//                .build();
//
//        em.persist(category);
//    }
//
//    @Override
//    public List<CategoryResponseDto> getAllCategories() {
//        return em.createQuery("SELECT c FROM Category c", Category.class)
//                .getResultStream()
//                .map(category -> CategoryResponseDto.builder()
//                        .id(category.getId())
//                        .name(category.getName())
//                        .build())
//                .collect(Collectors.toList());
//    }


package com.techmart.ejb;

import com.techmart.core.dto.request.CategoryRequestDto;
import com.techmart.core.dto.response.CategoryResponseDto;
import com.techmart.core.entity.Category;
import com.techmart.core.service.CategoryService;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.stream.Collectors;

@Singleton
@Startup
public class CategorySessionBean implements CategoryService {

    @PersistenceContext(unitName = "TechMartPU")
    private EntityManager em;

    private List<CategoryResponseDto> categoryCache;

    @PostConstruct
    public void init() {
        loadCategoriesToCache();
    }

    private void loadCategoriesToCache() {
        this.categoryCache = em.createQuery("SELECT c FROM Category c", Category.class)
                .getResultStream()
                .map(category -> CategoryResponseDto.builder()
                        .id(category.getId())
                        .name(category.getName())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public void addCategory(CategoryRequestDto requestDto) {

        TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(c) FROM Category c WHERE c.name = :name", Long.class);
        query.setParameter("name", requestDto.getName());
        Long count = query.getSingleResult();

        if (count > 0) {
            throw new IllegalArgumentException("Category with this name already exists!");
        }

        Category category = Category.builder()
                .name(requestDto.getName())
                .build();

        em.persist(category);

        loadCategoriesToCache();
    }

    @Override
    public List<CategoryResponseDto> getAllCategories() {
        return this.categoryCache;
    }
}