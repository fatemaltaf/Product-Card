/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Test.Product.Card.Service.ServiceImpl;

import Test.Product.Card.DTO.CategoryDTO;
import Test.Product.Card.DTO.ProductDTO;
import Test.Product.Card.DTO.ProductRequest;
import Test.Product.Card.DTO.ProductResponse;
import Test.Product.Card.Model.Category;
import Test.Product.Card.Model.Product;
import Test.Product.Card.Repository.CategoryRepository;
import Test.Product.Card.Repository.ProductRepository;
import Test.Product.Card.Service.ProductService;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 *
 * @author Fatema
 */
@Service
public class ProductServiceImpl implements ProductService {
    
    @Autowired
    ProductRepository productRepository;
   
    @Autowired
    CategoryRepository categoryRepository;
    
    @Autowired
    private MessageSource messageSource;
 
    @Override
    public ProductResponse create(ProductDTO productDTO) {
        ProductResponse productResponse = new ProductResponse();
        
        Optional<Category> idCheck = categoryRepository.findById(productDTO.getCategory().getId());
        
        try {
            if(idCheck.isPresent()){
                Product product = new Product();
                product.setName(productDTO.getName());
                product.setBrand(productDTO.getBrand());
                product.setPrice(productDTO.getPrice());
                product.setDescription(productDTO.getDescription());
                product.setCategory(idCheck.get());
                product.setActiveStatus(1);

                Product savedProduct = productRepository.save(product);

                productResponse.setStatus(1);
                productResponse.setHttp_code(200);
                productResponse.setActiveStatus(savedProduct.getActiveStatus());
                productResponse.setMessage(messageSource.getMessage("product.created.success", null, LocaleContextHolder.getLocale()));

            }
            else {
                productResponse.setStatus(0);
                productResponse.setHttp_code(404);
                Map<String, String> errors = new HashMap<>();
                errors.put("categoryId", "Category not found with ID: " + productDTO.getCategory().getId());
                productResponse.setErrors(errors);
            }
        } catch (Exception e) {
            productResponse.setStatus(0);
            productResponse.setHttp_code(500);
            Map<String, String> errors = new HashMap<>();
            errors.put("error", e.getMessage());
            productResponse.setErrors(errors);
        }
        return productResponse;
    }
    
    @Override
    public ProductResponse retreive(ProductRequest requestDTO){
        ProductResponse productResponse = new ProductResponse();
        
        Pageable pageRequest = PageRequest.of(requestDTO.getPage(), requestDTO.getLimit(), Sort.by("id").descending());
        ArrayList<Product> findAllByStatus = productRepository.findAllByActiveStatus(1, pageRequest);
        productResponse.setProductList(findAllByStatus);
        productResponse.setStatus(1);
        productResponse.setActiveStatus(1);
        productResponse.setHttp_code(HttpServletResponse.SC_OK);       
        if(findAllByStatus.isEmpty()){
            productResponse.setStatus(0);
            productResponse.setHttp_code(HttpServletResponse.SC_NO_CONTENT);           
        }

        return productResponse;
    }
    
    @Override
    public ProductResponse delete(ProductDTO productDTO){
        ProductResponse productResponse = new ProductResponse();
        
        Optional<Product> productCheck = productRepository.findById(productDTO.getId());
  
          if(productCheck.isPresent()){
              Product product = productCheck.get();
              product.setActiveStatus(0);              
              productRepository.save(product);
              
            productResponse.setStatus(1);
            productResponse.setHttp_code(200);
            productResponse.setActiveStatus(0);
            productResponse.setMessage(messageSource.getMessage("product.deleted.success", null, LocaleContextHolder.getLocale()));
          }
        else{
            productResponse.setStatus(404);
            productResponse.setHttp_code(404);
            Map<String, String> errors = new HashMap<>();
            errors.put("error", messageSource.getMessage("product.not.found", null, LocaleContextHolder.getLocale()));
            productResponse.setErrors(errors);
        }
        return productResponse;
    }
    
    @Override
    public ProductResponse update(ProductDTO productDTO){
        ProductResponse productResponse = new ProductResponse();
        
        Optional<Product> productCheck = productRepository.findById(productDTO.getId());
          if(productCheck.isPresent()){
              Product product = productCheck.get();
              product.setName(productDTO.getName());
              product.setBrand(productDTO.getBrand());
              product.setPrice(productDTO.getPrice());
              product.setDescription(productDTO.getDescription());
              product.setCategory(productDTO.getCategory());
              product.setActiveStatus(productDTO.getActiveStatus());          
              productRepository.save(product);
              
              productResponse.setStatus(1);
              productResponse.setHttp_code(200);
              productResponse.setActiveStatus(product.getActiveStatus());
          }
        else{
            productResponse.setStatus(404);
            productResponse.setHttp_code(404);
            Map<String, String> errors = new HashMap<>();
            errors.put("error", messageSource.getMessage("product.not.found", null, LocaleContextHolder.getLocale()));
            productResponse.setErrors(errors);
        }
        return productResponse;
    }
    
}
