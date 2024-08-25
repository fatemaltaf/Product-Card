package Test.Product.Card.Controller;

import Test.Product.Card.DTO.ProductDTO;
import Test.Product.Card.DTO.ProductRequest;
import Test.Product.Card.DTO.ProductResponse;
import Test.Product.Card.Service.ProductService;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Fatema
 */
@RestController
@RequestMapping("/api/products")
public class ProductController {
    
    @Autowired
    ProductService productService;
    
    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = "application/json", consumes = "multipart/form-data")
    public ResponseEntity<ProductResponse> createProduct(
    @ModelAttribute @Validated ProductDTO productDTO, @RequestParam(value = "lang", defaultValue = "en") String lang)
    {        
        ProductResponse productResponse = productService.create(productDTO);
        
        System.out.println("Received categoryId: " + productDTO.getCategory());

        Locale locale = new Locale(lang);
        LocaleContextHolder.setLocale(locale);
        
        if(productResponse.getStatus()==200){
            return ResponseEntity.ok(productResponse);
        }
        else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(productResponse);
        }                
    }       
    
    @RequestMapping(value = "/display", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    public ResponseEntity<ProductResponse> displayProduct(
    @RequestBody @Validated ProductRequest productDTO, @RequestParam(value = "lang", defaultValue = "en") String lang)
    {        
        ProductResponse productResponse = productService.retreive(productDTO);
        
        Locale locale = new Locale(lang);
        LocaleContextHolder.setLocale(locale);
        
        if(productResponse.getStatus()==200){
            return ResponseEntity.ok(productResponse);
        }
        else{
            return ResponseEntity.status(productResponse.getHttp_code()).body(productResponse);
        }                
    }
    
    @RequestMapping(value = "/delete", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    public ResponseEntity<ProductResponse> deleteProduct(
    @RequestBody @Validated ProductDTO productDTO, @RequestParam(value = "lang", defaultValue = "en") String lang)
    {        
        ProductResponse productResponse = productService.delete(productDTO);
        
        Locale locale = new Locale(lang);
        LocaleContextHolder.setLocale(locale);
        
        if(productResponse.getStatus()==200){
            return ResponseEntity.ok(productResponse);
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(productResponse);
        }                
    }
    
    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = "application/json", consumes = "multipart/form-data")
    public ResponseEntity<ProductResponse> updateProduct(
    @ModelAttribute @Validated ProductDTO productDTO, @RequestParam(value = "lang", defaultValue = "en") String lang) {        
    System.out.println("Received ProductDTO: " + productDTO);

    ProductResponse productResponse = productService.update(productDTO);
    System.out.println("ProductResponse: " + productResponse);
    
    Locale locale = new Locale(lang);
    LocaleContextHolder.setLocale(locale);

    if (productResponse.getStatus() == 200) {
        return ResponseEntity.ok(productResponse);
    } else {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(productResponse);
    }                
}

}
