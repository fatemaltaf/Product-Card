/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Test.Product.Card.Controller;

import Test.Product.Card.DTO.CategoryDTO;
import Test.Product.Card.DTO.CategoryRequest;
import Test.Product.Card.DTO.CategoryResponse;
import Test.Product.Card.Service.CategoryService;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Fatema
 */
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.LocaleResolver;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private MessageSource messageSource;

    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    public ResponseEntity<CategoryResponse> createCategory(
            @RequestBody @Validated CategoryDTO categoryDTO,
            @RequestParam(value = "lang", defaultValue = "en") String lang) {

        Locale locale = new Locale(lang);
        LocaleContextHolder.setLocale(locale);

        CategoryResponse response = categoryService.create(categoryDTO);

        if (response.getStatus() == 200) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @RequestMapping(value = "/show", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    public ResponseEntity<CategoryResponse> getAllCategories(
            @RequestBody CategoryRequest categoryRequest,
            @RequestParam(value = "lang", defaultValue = "en") String lang) {

        Locale locale = new Locale(lang);
        LocaleContextHolder.setLocale(locale);

        CategoryResponse response = categoryService.getCategory(categoryRequest);

        if (response.getStatus() == 200) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(response.getHttp_code()).body(response);
        }
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    public ResponseEntity<CategoryResponse> deleteCategory(
            @RequestBody @Validated CategoryDTO categoryDTO,
            @RequestParam(value = "lang", defaultValue = "en") String lang) {

        Locale locale = new Locale(lang);
        LocaleContextHolder.setLocale(locale);

        CategoryResponse response = categoryService.delete(categoryDTO);

        if (response.getStatus() == 200) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    public ResponseEntity<CategoryResponse> updateCategory(
            @RequestBody @Validated CategoryDTO categoryDTO,
            @RequestParam(value = "lang", defaultValue = "en") String lang) {

        Locale locale = new Locale(lang);
        LocaleContextHolder.setLocale(locale);

        CategoryResponse response = categoryService.update(categoryDTO);

        if (response.getStatus() == 200) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}
