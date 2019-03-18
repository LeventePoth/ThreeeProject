package threee.food;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class FoodDTO {
    List<Food> foods;
    List<Food> foods1;
    String foodTitle;
    String foodTitle1;
}
