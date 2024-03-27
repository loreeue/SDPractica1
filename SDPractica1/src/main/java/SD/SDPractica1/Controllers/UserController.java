package SD.SDPractica1.Controllers;

import SD.SDPractica1.Entities.User;
import SD.SDPractica1.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * This method handles GET requests to "/web/userList" endpoint.
     * @param model The model to which users are added.
     * @return The name of the view "ListUser".
     */
    @GetMapping("/web/userList")
    public String userList(Model model) {
        model.addAttribute("users", userService.obtainAllUsers());
        return "ListUser";
    }

    /**
     * This method handles GET requests to "/web/addUser" endpoint.
     * @param model The model to which users are added.
     * @return The name of the view "AddUser".
     */
    @GetMapping("/web/addUser")
    public String addUser(Model model) {
        User newUser = new User();
        model.addAttribute("user", newUser);
        return "AddUser";
    }

    /**
     * This method handles POST requests to "/web/addUser" endpoint.
     * @param model The model to which users are added.
     * @param newUser The new user that we add to the model.
     * @return Redirects to "/web".
     */
    @PostMapping("/web/addUser")
    public String addUser(User newUser, Model model) {
        userService.addUser(newUser);
        model.addAttribute("success", true);
        return "redirect:/web";
    }

    /**
     * This method handles GET requests to "/web/userDetails/{id}" endpoint.
     * @param model The model to which users are added.
     * @param id The ID of the user.
     * @return Redirects to "/web/userList" or the name of the view "DetailUser".
     */
    @GetMapping("/web/userDetails/{id}")
    public String userDetails(@PathVariable Long id, Model model) {
        User newUser = userService.obtainUser(id);
        if (newUser == null) {
            return "redirect:/web/userList";
        }
        model.addAttribute("user", newUser);
        return "DetailUser";
    }

    /**
     * This method handles GET requests to "/web/editUser/{id}" endpoint.
     * @param model The model to which users are added.
     * @param id The ID of the user.
     * @return Redirects to "/web/userList" or the name of the view "EditUser".
     */
    @GetMapping("/web/editUser/{id}")
    public String editUser(@PathVariable Long id, Model model) {
        User newUser = userService.obtainUser(id);
        if (newUser == null) {
            System.out.println("No encontrado");
            return "redirect:/web/userList";
        }
        model.addAttribute("user", newUser);
        return "EditUser";
    }

    /**
     * This method handles POST requests to "/web/editUser" endpoint.
     * @param model The model to which users are added.
     * @param newUser The user that we want to edit.
     * @return Redirects to "/web/userList" or redirects to "/web/userDetails/{id}".
     */
    @PostMapping("/web/editUser")
    public String editUser(User newUser, Model model) {
        User updatedUser = userService.updateUser(newUser.getId(), newUser);
        if (updatedUser == null) {
            return "redirect:/web/userList";
        }
        model.addAttribute("success", true);
        Long id = updatedUser.getId();
        return "redirect:/web/userDetails/" + id;
    }

    /**
     * This method handles GET requests to "/web/deleteUser/{id}" endpoint.
     * @param id The ID of the user.
     * @return Redirects to "/web/userList".
     */
    @GetMapping("/web/deleteUser/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/web/userList";
    }

    /**
     * This method handles GET requests to "/web/logIn" endpoint.
     * @param model The model to which users are added.
     * @return The name of the view "LogIn".
     */
    @GetMapping("/web/logIn")
    public String logIn(Model model) {
        User newUser = new User();
        model.addAttribute("user", newUser);
        return "LogIn";
    }

    /**
     * This method handles POST requests to "/web/logIn" endpoint.
     * @param model The model to which users are added.
     * @param newUser The user that we want to know if exists or not.
     * @return The name of the view "LogIn" or redirects to "/web/loggedUser/{id}".
     */
	@PostMapping("/web/logIn")
	public String logIn(User newUser, Model model) {
        User user1 = userService.existsUser(newUser.getUsername(), newUser.getPassword());
        if (!userService.logIn(user1)) {
            model.addAttribute("error", true);
            return "LogIn";
        }
        model.addAttribute("success", true);
        return "redirect:/web/loggedUser/" + user1.getId();
    }

    /**
     * This method handles GET requests to "/web/loggedUser/{id}" endpoint.
     * @param model The model to which users are added.
     * @param id The ID of the user.
     * @return The name of the view "LoggedUser".
     */
    @GetMapping("/web/loggedUser/{id}")
    public String loggedUser(@PathVariable Long id, Model model){
        User newUser = userService.obtainUser(id);
        model.addAttribute("user", newUser);
        return "LoggedUser";
    }
}