/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Test.Product.Card.Repository;

import Test.Product.Card.Model.Category;
import java.util.ArrayList;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Fatema
 */
public interface CategoryRepository extends JpaRepository<Category, Integer>{
    public ArrayList<Category> findAllByActiveStatus(int activeStatus,Pageable pageable);
}
