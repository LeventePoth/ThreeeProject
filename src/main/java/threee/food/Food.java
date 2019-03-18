package threee.food;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@Entity

public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private boolean activated;
    private String foodType;
    private String foodName;
    private String ingredients;
    private String pastaType;
    private int amount;
    private int price;
    private int bag;
    private String profilePicUrl;

    public Food(boolean activated, String foodType, String foodName, String ingredients, String pastaType,
                int amount, int price, int bag, String profilePicUrl) {
        this.activated = activated;
        this.foodType = foodType;
        this.foodName = foodName;
        this.ingredients = ingredients;
        this.pastaType = pastaType;
        this.amount = amount;
        this.price = price;
        this.bag = bag;
        this.profilePicUrl = profilePicUrl;
    }

    public Food() {

    }
}

