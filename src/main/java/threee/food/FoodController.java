package threee.food;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import threee.User.UserService;

import javax.servlet.http.HttpSession;


@Controller
public class FoodController {

    private FoodService foodService;
    private UserService userService;

    @Autowired
    public FoodController (FoodService foodService, UserService userService) {
        this.foodService = foodService;
        this.userService = userService;
    }

    @GetMapping("/")
    public String renderThreePage() {
        return "three";
    }

    /* @GetMapping("/menu")
    public String renderFoodPageMod(@RequestParam(name = "name1")String foodName, Model model) {
        model.addAttribute("name", foodService.findFood(foodName));
        return "/menu";
    } */

    @GetMapping("/menu")
    public String listApprentices(Model model,
                                  @RequestParam(name = "name1", required = false) String foodType,
                                  @RequestParam(name = "name2", required = false) String foodTitle) {
        model.addAttribute("fooddto",
                foodService.filterFoodByType(foodType, foodTitle));
        return "/menu";
    }

    @GetMapping("/rendeles")
    public String listAll(Model model) {
        model.addAttribute("fooddto",
                foodService.filterFoodByType(null, null));
        model.addAttribute("orderdto",
                foodService.saveOrder(0));
        return "/rendeles";
    }

    @PostMapping("/rendeles/kosar")
    public String postOrder(@RequestParam(name = "id")long id,
                            HttpSession session, Model model) {
        Object sessionUser = session.getAttribute("session");
        if (sessionUser == null) {
        return "/registration";
        }
        {
            model.addAttribute("orderdto", foodService.saveOrder(id));
            model.addAttribute("fooddto",
                    foodService.filterFoodByType(null, null));
            return "rendeles";
        }
    }

    @PostMapping("/rendeles/modal")
    public String postModal(@RequestParam(name = "totalAmount")int totalAmount,
                            @RequestParam(name = "totalFoodPrice")int totalFoodPrice,
                            HttpSession session, Model model) {
        Object sessionUser = session.getAttribute("session");
        if (sessionUser == null) {
            return "/registration";
        }
        {
            model.addAttribute("orderdto", foodService.modal(totalAmount, totalFoodPrice));
            model.addAttribute("fooddto",
                    foodService.filterFoodByType(null, null));
            return "rendeles";
        }
    }

    /* @GetMapping("/kosar")
    public String renderKoarPage(Model model) {
        model.addAttribute("orderdto",
                foodService.getOrder());
        return "kosar";
    }*/


    /* @GetMapping("/menu")
    public String renderMenuPage() {
        return "menu";
    } */

    /* @GetMapping("/three")
    public String listMenu(Model model, @RequestParam(value = "pizza") String foodType) {
        model.addAttribute("fooddto",
               foodService.filterFoodByType(foodType));
        return "menu";
    } */

    /* @GetMapping("/")
    public String listMenu(Model model, @RequestParam(value = "foodType") String foodType) {
        model.addAttribute("foods",
                foodService.findAll());
        return "menu";
    } */

   /* @GetMapping("/three")
    public String renderFoodPage(@RequestParam(name = "name1")String foodName, Model model) {
        model.addAttribute("name", foodService.findFood(foodName));
        return "three";
    } */

    /* @GetMapping("/three")
    public String renderFoodPage(@RequestParam(name = "name1")String foodName, Model model) {
        model.addAttribute("name", foodService.findFood(foodName));
        return "/menu";
    } */
}
