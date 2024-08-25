package Test.Product.Card.Service.ServiceImpl;

import Test.Product.Card.DTO.CategoryDTO;
import Test.Product.Card.DTO.CategoryRequest;
import Test.Product.Card.DTO.CategoryResponse;
import Test.Product.Card.Model.Category;
import Test.Product.Card.Repository.CategoryRepository;
import Test.Product.Card.Service.CategoryService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

/**
 *
 * @author Fatema
 */
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

@Service
@Validated
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private MessageSource messageSource;

    @Override
    public CategoryResponse create(CategoryDTO categoryRequest) {
        CategoryResponse response = new CategoryResponse();
        try {
            Category category = new Category();
            category.setName(categoryRequest.getName());
            category.setDescription(categoryRequest.getDescription());
            category.setActiveStatus(1);

            Category savedCategory = categoryRepository.save(category);

            response.setStatus(1);
            response.setHttp_code(201); 
            response.setActiveStatus(savedCategory.getActiveStatus());
            response.setMessage(messageSource.getMessage("category.created.success", null, LocaleContextHolder.getLocale()));

        } catch (Exception e) {            
            response.setStatus(0);
            response.setHttp_code(500); 
            Map<String, String> errors = new HashMap<>();
            errors.put("error", e.getMessage());
            response.setErrors(errors);
        }
        return response;
    }

    @Override
    public CategoryResponse getCategory(CategoryRequest categoryRequest) {
        CategoryResponse responseDTO = new CategoryResponse();
       
        Pageable pageRequest = PageRequest.of(categoryRequest.getPage(), categoryRequest.getLimit(), Sort.by("id").descending());
        ArrayList<Category> findAllByStatus = categoryRepository.findAllByActiveStatus(1, pageRequest);
        responseDTO.setCategoryList(findAllByStatus);
        responseDTO.setStatus(1);
        responseDTO.setActiveStatus(1);
        responseDTO.setHttp_code(HttpServletResponse.SC_OK);       
        if(findAllByStatus.isEmpty()){
            responseDTO.setStatus(0);
            responseDTO.setHttp_code(HttpServletResponse.SC_NO_CONTENT);           
        }
        return responseDTO;
    }

    @Override
    @Transactional
    public CategoryResponse delete(CategoryDTO requestDTO) {
        CategoryResponse response = new CategoryResponse();

        Optional<Category> categoryOptional = categoryRepository.findById(requestDTO.getId());
        if (categoryOptional.isPresent()) {
            Category category = categoryOptional.get();
            category.setActiveStatus(0); 
            categoryRepository.save(category);

            response.setStatus(200);
            response.setHttp_code(200);
            response.setActiveStatus(0);
            response.setMessage(messageSource.getMessage("category.deleted.success", null, LocaleContextHolder.getLocale()));
        } else {
            response.setStatus(404);
            response.setHttp_code(404);
            Map<String, String> errors = new HashMap<>();
            errors.put("error", messageSource.getMessage("category.not.found", null, LocaleContextHolder.getLocale()));
            response.setErrors(errors);
        }

        return response;
    }

    @Override
    @Transactional
    public CategoryResponse update(CategoryDTO categoryDTO) {
        CategoryResponse response = new CategoryResponse();

        Optional<Category> categoryOptional = categoryRepository.findById(categoryDTO.getId());
        if (categoryOptional.isPresent()) {
            Category category = categoryOptional.get();
            category.setName(categoryDTO.getName());
            category.setDescription(categoryDTO.getDescription());
            category.setActiveStatus(categoryDTO.getActiveStatus());
            categoryRepository.save(category); 

            response.setStatus(200); 
            response.setHttp_code(200);
            response.setActiveStatus(category.getActiveStatus()); 
            response.setMessage(messageSource.getMessage("category.updated.success", null, LocaleContextHolder.getLocale())); 
        } else {
            response.setStatus(404); 
            response.setHttp_code(404);
            Map<String, String> errors = new HashMap<>();
            errors.put("error", messageSource.getMessage("category.not.found", null, LocaleContextHolder.getLocale()));
            response.setErrors(errors);
        }

        return response;
    }
}
