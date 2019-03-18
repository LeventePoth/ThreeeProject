package threee.food;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class OrderDTO {
    Integer totalFoodPrice;
    Integer totalAmount;
    Integer totalSum;
    List<Food> separatedProducts;
}
