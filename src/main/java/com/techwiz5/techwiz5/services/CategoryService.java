package com.techwiz5.techwiz5.services;


import com.techwiz5.techwiz5.dtos.category.CategoryDTO;
import com.techwiz5.techwiz5.entities.User;
import com.techwiz5.techwiz5.models.category.CreateCategory;
import com.techwiz5.techwiz5.models.category.EditCategory;

import java.util.List;

public interface CategoryService {
    List<CategoryDTO> findAll();
    List<CategoryDTO> findAllByUser(User user);
    CategoryDTO create(CreateCategory createCategory, User user);
    CategoryDTO update(EditCategory editCategory, User user);
    void delete(Long[] ids);
}
