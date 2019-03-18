package threee.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import threee.food.FoodService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class UserController {

  private UserService userService;
  private UserRepository userRepository;
  private threee.User.MailgunService mailgunService;
  private FoodService foodService;

  @Autowired
  public UserController(UserService userService, UserRepository userRepository, threee.User.MailgunService mailgunService, FoodService foodService) {
    this.userService = userService;
    this.userRepository = userRepository;
    this.mailgunService = mailgunService;
    this.foodService = foodService;
  }
  
  /* @GetMapping("/")
  public String loadLoginPage(HttpSession session){
    Object sessionUser = session.getAttribute("session");
    if (sessionUser != null) {
      return "redirect:/apprentices";
    }
    return "redirect:/login";
  }
  */
  @GetMapping("/registration")
  public String renderRegistrationPage(@ModelAttribute Users users, Model model) {
    model.addAttribute("users", new Users());
    return "registration";
  }

  @PostMapping("/registration")
  public String signUp(@ModelAttribute Users users,
                       @RequestParam(name = "confirm")String confirm,
                       HttpServletRequest request, Model model) {
    String errorMessage = userService.errorHandler(users, confirm);
    model.addAttribute("error", errorMessage);
    if (errorMessage != null) {
      return "registration";
    }
    userService.register(users);
    HttpSession session = request.getSession();
    session.setAttribute("session", userService.findOneByEmail(users.getEmail()).getEmail());
    mailgunService.sendSimpleMessage(users);
    if (userService.isAdminEmail(users)) {
      userService.setAdminFields(users);
    }
    return "redirect:/rendeles";
  }

  @GetMapping ("/admin/profile")
  public String renderAdminProfile (HttpSession session, Model model){
    Object sessionUser = session.getAttribute("session");
    if (sessionUser == null) {
      return "redirect:/login";
    }
    model.addAttribute("sessionUser", userService.findOneByEmail(sessionUser.toString()));
    return "adminProfile";
  }

  @GetMapping("/user/profile")
  public String renderUserProfile (HttpSession session, Model model){
    Object sessionUser = session.getAttribute("session");
    if (sessionUser == null) {
      return "redirect:/login";
    }
    model.addAttribute("sessionUser", userService.findOneByEmail(sessionUser.toString()));
    return "userProfile";
  }

  /* @GetMapping("/login")
  public String renderLoginPage() {
    return "login";
  } */

  @PostMapping("/rendeles")
  public String login(@RequestParam(name = "email")String email,
                      @RequestParam(name = "password")String password, Model model,
                      HttpServletRequest request, RedirectAttributes redirectAttributes) {
    String errorMessage = userService.loginErrorHandler(email, password);
    model.addAttribute("error", errorMessage);
    model.addAttribute("orderdto", foodService.saveOrder(0));
    model.addAttribute("fooddto",
              foodService.filterFoodByType(null, null));
    if (errorMessage != null) {
      return "rendeles";
    }
    HttpSession session = request.getSession();
    session.setAttribute("session", userService.findOneByEmail(email).getEmail());
    model.addAttribute("orderdto", foodService.saveOrder(0));
    model.addAttribute("error", "Szia, Póth Levcsi");
    return "rendeles";
  }

  @GetMapping("/admin/partner-management")
  public String renderPartnerManagementPage(Model model, HttpSession session){
    Object sessionUser = session.getAttribute("session");
    if (sessionUser == null) {
      return "redirect:/login";
    } else if (!userService.isAdminEmail(userService.findOneByEmail(session.getAttribute("session").toString()))) {
      return "redirect:/user/profile";
    }
    model.addAttribute("sessionUser", userService.findOneByEmail(sessionUser.toString()));
    model.addAttribute("allNotAdmins", userService.findAllByAdministratorFalse());
  return "adminPartnerManagement";
  }

  @PostMapping("admin/partner-management")
  public String search (@RequestParam(required = false, name = "active")boolean active,
                        @RequestParam(required = false, name = "inactive")boolean inactive,
                        @RequestParam(required = false, name = "search")String search, Model model){
    model.addAttribute("results", userService.listUsersByActivity(active, inactive, userRepository.findBySearchTerm(search)));
    if (userService.listUsersByActivity(active, inactive, userRepository.findBySearchTerm(search)).size() == 0) {
      model.addAttribute("errorMessage", "Sorry, we couldn't find any results for " + search);
    }
    return "adminPartnerManagement";
  }



  @PostMapping("admin/partner-management/activation")
  public String userActivation(@RequestParam(name = "id")Long id){
    userService.activation(userService.findById(id));
    return "redirect:/admin/partner-management";
  }

  @GetMapping("/logout")
  public String logOut(HttpSession session) {
    if (session.getAttribute("session") != null) {
      session.invalidate();
    }
    return "redirect:/login";
  }
}
