package SD.SDPractica1.Controllers;

import SD.SDPractica1.Entities.User;
import SD.SDPractica1.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import java.util.Collection;
import java.util.Objects;

@RestController
@RequestMapping("/api/user")
public class UserRESTController {
    @Autowired
    private UserService userService;

    /**
     * This method handles POST requests.
     * Adds a new user received in the request body.
     * @param newUser The USER to be added.
     * @return ResponseEntity with status 201 (Created) and the added user in the body or status 400 (Bad Request).
     */
    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody User newUser) {
        if (newUser.getId() != null || newUser.getUsername() == null || newUser.getEmail() == null || newUser.getPassword() == null || newUser.getFirstName() == null || newUser.getLastName() == null || newUser.getBirthdate() == null || newUser.getGender() == null || newUser.getSelectedAvatar() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(201).body(userService.addUser(newUser));
    }

    /**
     * This method handles GET requests.
     * Retrieves a user by its ID.
     * @param id The ID of the user to retrieve.
     * @return ResponseEntity with status 201 (OK) and the user in the body if found, or status 404 (Not Found) if not found.
     */
	@GetMapping("/{id}")
	public ResponseEntity<User> obtainUser(@PathVariable Long id){
		User newUser = userService.obtainUser(id);
		if (newUser == null){
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return ResponseEntity.status(201).body(newUser);
	}

    /**
     * This method handles GET requests.
     * Retrieves all users.
     * @return ResponseEntity with status 200 (OK) and a collection of users in the body.
     */
	@GetMapping
    public ResponseEntity<Collection<User>> obtainAllUsers() {
		return ResponseEntity.ok(userService.obtainAllUsers());
    }

    /**
     * This method handles PUT requests.
     * Updates a user with the specified ID using data from the request body.
     * @param id The ID of the user to update.
     * @param updateUser The updated user data.
     * @return ResponseEntity with status 200 (OK) and the updated user in the body or status 400 (Bad Request) or status 404 (Not Found).
     */
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User updateUser) {
        if (updateUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        if ((updateUser.getId() != null && !Objects.equals(updateUser.getId(), id)) || updateUser.getUsername() == null || updateUser.getEmail() == null || updateUser.getPassword() == null || updateUser.getFirstName() == null || updateUser.getLastName() == null || updateUser.getBirthdate() == null || updateUser.getGender() == null || updateUser.getSelectedAvatar() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
		User updatedUser = userService.updateUser(id, updateUser);
        return ResponseEntity.ok(updatedUser);
    }

    /**
     * This method handles PATCH requests.
     * Updates a user with the specified ID using partial data from the request body.
     * @param id The ID of the user to update.
     * @param updateUser The partial updated user data.
     * @return ResponseEntity with status 200 (OK) and a success message or status 400 (Bad Request) or status 404 (Not Found).
     */
	@PatchMapping("/{id}")
	public ResponseEntity<?> patchUser (@PathVariable Long id, @RequestBody User updateUser) {
        if (updateUser == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        if (updateUser.getId() != null && !Objects.equals(updateUser.getId(), id)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        User updatedUser = userService.patchUser(id, updateUser);
        return ResponseEntity.ok(updatedUser);
	}

    /**
     * This method handles DELETE requests.
     * Deletes a user with the specified ID.
     * @param id The ID of the user to delete.
     * @return ResponseEntity with status 200 (OK) and a success message.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("Usuario con ID: " + id + " eliminado con éxito");
    }
}