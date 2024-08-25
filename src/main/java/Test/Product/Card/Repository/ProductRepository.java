package Test.Product.Card.Repository;

import Test.Product.Card.Model.Product;
import java.util.ArrayList;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Fatema
 */
public interface ProductRepository extends JpaRepository<Product,Integer>{
    public ArrayList<Product> findAllByActiveStatus(int activeStatus,Pageable pageable);
}
