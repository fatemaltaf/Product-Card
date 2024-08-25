/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Test.Product.Card.Service;

import Test.Product.Card.DTO.CategoryDTO;
import Test.Product.Card.DTO.CategoryRequest;
import Test.Product.Card.DTO.CategoryResponse;

/**
 *
 * @author Fatema
 */
public interface CategoryService {
            
    public CategoryResponse create(CategoryDTO categoryRequest);
    
    public CategoryResponse getCategory(CategoryRequest categoryRequest);
    
    public CategoryResponse delete(CategoryDTO requestDTO);
       
    public CategoryResponse update(CategoryDTO categoryDTO);
}
