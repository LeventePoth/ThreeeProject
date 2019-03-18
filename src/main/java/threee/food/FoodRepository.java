package threee.food;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodRepository extends CrudRepository<Food, Long> {

    List<Food> findAll();
    List<Food> findAllByFoodType(String foodType);
    Food findByFoodName(String foodName);
    Food findById(long id);
    Food findByPrice(Integer price);
}
