package com.techwiz5.techwiz5.controllers;


import com.techwiz5.techwiz5.dtos.ResponseObject;
import com.techwiz5.techwiz5.dtos.category.CategoryDTO;
import com.techwiz5.techwiz5.entities.User;
import com.techwiz5.techwiz5.models.category.CreateCategory;
import com.techwiz5.techwiz5.models.category.EditCategory;
import com.techwiz5.techwiz5.services.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/any/category")
    ResponseEntity<ResponseObject> getAll() {
        List<CategoryDTO> list = categoryService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", list)
        );
    }

    @GetMapping("/category")
    ResponseEntity<ResponseObject> getAllByUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) auth.getPrincipal();
        List<CategoryDTO> list = categoryService.findAllByUser(currentUser);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", list)
        );
    }

    @PostMapping("/category")
    ResponseEntity<ResponseObject> create(@Valid @RequestBody CreateCategory createCategory) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) auth.getPrincipal();
        CategoryDTO categoryDTO = categoryService.create(createCategory, currentUser);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "Create Success", categoryDTO)
        );
    }
    @PutMapping("/category")
    ResponseEntity<ResponseObject> update(@Valid @RequestBody EditCategory editCategory) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) auth.getPrincipal();
        CategoryDTO categoryDTO = categoryService.update(editCategory, currentUser);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "Update Success", categoryDTO)
        );
    }

    @DeleteMapping("/category")
    ResponseEntity<ResponseObject> update(@RequestBody Long[] ids) {
        categoryService.delete(ids);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "Delete success", "")
        );
    }

}