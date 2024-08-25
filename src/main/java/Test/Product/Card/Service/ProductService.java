/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Test.Product.Card.Service;

import Test.Product.Card.DTO.ProductDTO;
import Test.Product.Card.DTO.ProductRequest;
import Test.Product.Card.DTO.ProductResponse;

/**
 *
 * @author Fatema
 */
public interface ProductService {
 
    public ProductResponse create(ProductDTO productDTO);
    
    public ProductResponse retreive(ProductRequest requestDTO);
    
    public ProductResponse delete(ProductDTO productDTO);
    
    public ProductResponse update(ProductDTO productDTO);
}
