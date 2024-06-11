package net.javaguides.springboot.service.impl;

import net.javaguides.springboot.entity.Category;
import net.javaguides.springboot.exception.ResourceNotFoundException;
import net.javaguides.springboot.payload.CategoryDto;
import net.javaguides.springboot.repository.CategoryRepository;
import net.javaguides.springboot.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

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

    @Override
    public CategoryDto getCategory(Long id){
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));
        return modelMapper.map(category, CategoryDto.class);
    }

    @Override
    public List<CategoryDto> getAllCategories(){
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map((category) -> modelMapper.map(category, CategoryDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Long categoryId){
        return categoryRepository.findById(categoryId)
                .map((category) -> {
                    category = Category.builder()
                            .id(categoryId)
                            .name(categoryDto.getName())
                            .description(categoryDto.getDescription())
                            .posts(category.getPosts())
                            .build();
                    return modelMapper.map(categoryRepository.save(category), CategoryDto.class);

                }).orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
    }

    @Override
    public void deleteCategory(Long categoryId){
        categoryRepository.deleteById(categoryId);
    }
}
