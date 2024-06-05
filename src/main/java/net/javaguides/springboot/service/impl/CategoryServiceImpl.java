package net.javaguides.springboot.service.impl;

import net.javaguides.springboot.entity.Category;
import net.javaguides.springboot.payload.CategoryDto;
import net.javaguides.springboot.repository.CategoryRepository;
import net.javaguides.springboot.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {
    private CategoryRepository categoryRepository;
    private ModelMapper modelMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository,
                               ModelMapper modelMapper){
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CategoryDto addCategory(CategoryDto categoryDto) {
        Category category = modelMapper.map(categoryDto, Category.class);
        Category savedCategory = categoryRepository.save(category);
        return modelMapper.map(savedCategory, CategoryDto.class);
    }
}
