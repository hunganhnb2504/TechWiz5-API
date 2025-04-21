package com.techwiz5.techwiz5.services.impl;

import com.techwiz5.techwiz5.dtos.category.CategoryDTO;
import com.techwiz5.techwiz5.entities.Category;
import com.techwiz5.techwiz5.entities.User;
import com.techwiz5.techwiz5.exceptions.AppException;
import com.techwiz5.techwiz5.exceptions.ErrorCode;
import com.techwiz5.techwiz5.mappers.CategoryMapper;
import com.techwiz5.techwiz5.models.category.CreateCategory;
import com.techwiz5.techwiz5.models.category.EditCategory;
import com.techwiz5.techwiz5.repositories.CategoryRepository;
import com.techwiz5.techwiz5.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class ICategoryService implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<CategoryDTO> findAll() {
        return categoryRepository.findAll().stream().map(categoryMapper::toCategoryDTO).collect(Collectors.toList());
    }

    @Override
    public List<CategoryDTO> findAllByUser(User user) {
        List<Category> categories = categoryRepository.findAllByUser(user);
        return categories.stream()
                .map(categoryMapper::toCategoryDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDTO create(CreateCategory createCategory, User user) {
        Optional<Category> categoryExisting = categoryRepository.findByName(createCategory.getName());
        if (categoryExisting.isPresent()) {
            throw new AppException(ErrorCode.CATEGORY_EXISTED);
        }
        Category category = Category.builder()
                .user(user)
                .name(createCategory.getName())
                .createdBy(user.getFullName())
                .modifiedBy(user.getFullName())
                .createdDate(new Timestamp(System.currentTimeMillis()))
                .createdDate(new Timestamp(System.currentTimeMillis()))
                .build();
        categoryRepository.save(category);
        return categoryMapper.toCategoryDTO(category);
    }

    @Override
    public CategoryDTO update(EditCategory editCategory, User user) {
        Category categoryExisting = categoryRepository.findById(editCategory.getId())
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOTFOUND));
        categoryExisting.setName(editCategory.getName());
        categoryExisting.setCreatedBy(user.getFullName());
        categoryExisting.setModifiedBy(user.getFullName());
        categoryExisting.setCreatedDate(new Timestamp(System.currentTimeMillis()));
        categoryExisting.setModifiedDate(new Timestamp(System.currentTimeMillis()));
        categoryRepository.save(categoryExisting);
        return categoryMapper.toCategoryDTO(categoryExisting);
    }

    @Override
    public void delete(Long[] ids) {
        categoryRepository.deleteAllById(List.of(ids));
    }
}
