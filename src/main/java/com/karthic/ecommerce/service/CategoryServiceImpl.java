package com.karthic.ecommerce.service;

import com.karthic.ecommerce.exception.APIException;
import com.karthic.ecommerce.exception.ResourceNotFoundException;
import com.karthic.ecommerce.repositories.CategoryRepository;
import com.karthic.ecommerce.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;


@Service
public class CategoryServiceImpl implements CategoryService{


    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        if (categories.isEmpty())
            throw new APIException("No category available");
        return categories;
    }

    @Override
    public void createCategory(Category category) {

        Category savedCategory = categoryRepository.findByCategoryName(category.getCategoryName());
        if (savedCategory!=null){
           throw new APIException("Category "+ category.getCategoryName() + " already exists");
        }

        categoryRepository.save(category);

    }

    @Override
    public String deleteCategory(Long categoryId) {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category","categoryId",categoryId) );

        categoryRepository.delete(category);
        return categoryId+" CategoryId deleted";
    }

    @Override
    public Category updateCategory(Category category, Long categoryId) {

        Category savedCategory=categoryRepository.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFoundException("Category","categoryId",categoryId) );
        category.setCategoryId(categoryId);

        savedCategory=categoryRepository.save(category);
        return savedCategory;
     }


}
