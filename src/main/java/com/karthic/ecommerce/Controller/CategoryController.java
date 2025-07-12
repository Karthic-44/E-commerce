package com.karthic.ecommerce.Controller;


import com.karthic.ecommerce.model.Category;
import com.karthic.ecommerce.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@RestController
@RequestMapping("/api")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/public/categories")
    public ResponseEntity<List<Category>> getAllCategories() {

        List<Category> Categories = categoryService.getAllCategories();
        return new ResponseEntity<>(Categories, HttpStatus.OK);
    }

    @PostMapping("/public/categories")
    public ResponseEntity<String> CreateCategories(@Valid @RequestBody Category category) {

        categoryService.createCategory(category);
        return new ResponseEntity<>("Category Created", HttpStatus.CREATED);
    }

    @DeleteMapping("/admin/categories/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long categoryId){

            String status = categoryService.deleteCategory(categoryId);
            return new ResponseEntity<>(status, HttpStatus.OK);
        }

    @PutMapping("/public/categories/{categoryId}")
    public ResponseEntity<String> updateCategory(@Valid @RequestBody Category category, @PathVariable Long categoryId){

            Category savedcategory = categoryService.updateCategory(category, categoryId);
            return new ResponseEntity<>("Updated Category " + categoryId, HttpStatus.OK);

    }
}
