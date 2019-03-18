package threee.food;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FoodServiceImpl implements FoodService {

    private FoodRepository foodRepository;

    @Autowired
            public FoodServiceImpl(FoodRepository foodrepository) {
        this.foodRepository = foodrepository;
    }

    /*@Override
    public FoodDTO filterFoodByType(String foodType, String foodTitle) {
        List<Food> allFoodByType = foodRepository.findAllByFoodType(foodType);
            return new FoodDTO(allFoodByType, foodTitle);
    }*/

    @Override
    public FoodDTO filterFoodByType(String foodType, String foodTitle) {
        if (foodType == null && foodTitle == null) {
            List<Food> allNewFood = foodRepository.findAll();
            List<Food> filteredPizza = new ArrayList<>();
            List<Food> filteredSalat = new ArrayList<>();
            String pizzaTitle = "Pizzák";
            String salatTitle = "Saláták";
            for (int i = 0; i < allNewFood.size(); i++) {
                if (allNewFood.get(i).getFoodType().matches("pizza")) {
                    filteredPizza.add(allNewFood.get(i));
                }
                if (allNewFood.get(i).getFoodType().matches("saláta")) {
                    filteredSalat.add(allNewFood.get(i));
                }
            }
            if (!filteredPizza.isEmpty() && !filteredSalat.isEmpty()) {
                return new FoodDTO(filteredPizza, filteredSalat, pizzaTitle, salatTitle);
            }
    }
            List<Food> allFoodByType = foodRepository.findAllByFoodType(foodType);
            return new FoodDTO(allFoodByType, null, foodTitle, null);
    }

    // private int sum = 0;
    private List<Food> orderProducts = new ArrayList<>();
    private List<Food> finalProducts = new ArrayList<>();
    private List<Food> separatedProducts = new ArrayList<>();
    private int totalSum;

   @Override
    public OrderDTO saveOrder (long id) {
       if (id != 0) {
           Food orderId = foodRepository.findById(id);
           Integer Amount = orderId.getPrice();
           totalSum += Amount;
           orderProducts.add(orderId);
           finalProducts.add(orderId);
           int sum = 0; // Double sum = 0;

           boolean isDistinct = false;
           for(int i=0; i<orderProducts.size(); i++) {
               // if (i == 0 && separatedProducts.isEmpty()) {
               //  separatedProducts.add(orderProducts.get(0));
               //  System.out.println(separatedProducts);
               //}
               if(i == (orderProducts.size()-1)) {
               //else {
                   for (int j = 0; j < i; j++) {
                       if (orderProducts.get(i).getId() == orderProducts.get(j).getId()) {// i=1 vs j=0 (i=0) // i=2 vs j=0 és j=1 // i=3 vs j=0 és j=1 és j=2 itt valamelyikkel egyenlő
                           isDistinct = true; // i=4 vs j=0 és j=1 és j=2
                           for (int k = 0; k < i; k++) {
                               if (orderProducts.get(i).getId() == separatedProducts.get(k).getId()) {
                                   separatedProducts.get(k).setBag(separatedProducts.get(k).getBag() + 1);
                                   System.out.println(separatedProducts.get(k).getBag());
                                   break;
                               }//else break;
                           }
                           // separatedProducts.get(j).setBag(separatedProducts.get(j).getBag() + 1);
                           // System.out.println(separatedProducts.get(j).getBag());
                           // isDistinct = true;
                           // orderProducts.remove(i);
                           break;
                       }
                   }
                   if (isDistinct == false)
                   { separatedProducts.add(orderProducts.get(i));
                       //System.out.println(separatedProducts);
                       // break;
                   }
               }
           }
           System.out.println(separatedProducts);

               for (int n = 0; n < finalProducts.size(); n++) {
                   //for (Food order : orderProducts) {
                   sum += finalProducts.get(n).getPrice();
               }
               return new OrderDTO(sum, finalProducts.size(), totalSum, separatedProducts);
       }
       return new OrderDTO(null, null, null, null);
   }

   @Override
   public OrderDTO modal (int totalAmount, //mentéssssssssssssssssssssssssssssssss
                          int totalFoodPrice) {
       int returnedSum;
       int returnedSize;
       int verylastProduct = finalProducts.get(finalProducts.size()-1).getPrice();
       returnedSum = totalFoodPrice - verylastProduct;
       returnedSize = finalProducts.size()-1;
       return new OrderDTO(returnedSum, returnedSize, null, null);
   }

   public OrderDTO getOrder () {
       return new OrderDTO(0, finalProducts.size(), totalSum, null);
   }

    @Override
    public List<Food> findAllFood() {
        return foodRepository.findAll();
    }

    @Override
    public Food findFood(String foodName) {
        return foodRepository.findByFoodName(foodName);
    }

    /*@Override
   public OrderDTO modal () {
   int returnedSize;
   int temporarySum = 0;
   int returnedSum;
       for (int i = 0; i < finalProducts.size(); i++) {
           //for (Food order : orderProducts) {
           temporarySum += finalProducts.get(i).getPrice();
   }
   int verylastProduct = finalProducts.get(finalProducts.size()-1).getPrice();
       returnedSum = temporarySum - verylastProduct;
       returnedSize = finalProducts.size()-1;
       return new OrderDTO(returnedSum, returnedSize, null);
   } */

}
