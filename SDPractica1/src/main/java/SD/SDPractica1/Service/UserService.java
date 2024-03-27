package SD.SDPractica1.Service;

import SD.SDPractica1.Entities.User;
import org.springframework.stereotype.Service;
import java.util.*;
import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class UserService {
    private Map<Long, User> userMap = new ConcurrentHashMap<>();
    private AtomicLong counter = new AtomicLong();

    public UserService() {
        //the year is: year-1900
        User usuario1 = new User("elena123", "elena@gmail.com", "elena123", "Elena", "Gonzalez", "1980-03-12", "Femenino", "/avatar-mujer.png");
        usuario1.setId(counter.incrementAndGet());
        userMap.put(usuario1.getId(), usuario1);

        User usuario2 = new User("pablo123", "pablo@gmail.com", "pablo123", "Pablo", "Gutiérrez", "1965-01-24", "Masculino", "/avatar-hombre.png");
        usuario2.setId(counter.incrementAndGet());
        userMap.put(usuario2.getId(), usuario2);

        User usuario3 = new User("miguel123", "miguel@gmail.com", "miguel123", "Miguel", "Fernández", "1967-02-19", "Masculino", "/avatar-normal.png");
        usuario3.setId(counter.incrementAndGet());
        userMap.put(usuario3.getId(), usuario3);

        User usuario4 = new User("silvia123", "svx@gmail.com", "silvia123", "Silvia", "Coldplay", "1994-07-06", "Femenino", "/avatar-mujer.png");
        usuario4.setId(counter.incrementAndGet());
        userMap.put(usuario4.getId(), usuario4);

        User usuario5 = new User("lore123", "lore@gmail.com", "lore123", "Loreto", "Uzquiano", "2003-10-01", "Femenino", "/avatar-mujer.png");
        usuario5.setId(counter.incrementAndGet());
        userMap.put(usuario5.getId(), usuario5);
    }

    /**
     * Adds a new user to the user map.
     * Sets the ID, creation and update timestamps, and adds the user to the map.
     * @param newUser The user to be added.
     * @return The added user.
     */
    public User addUser(User newUser) {
        newUser.setId(counter.incrementAndGet());
        newUser.setUpdatedAt(LocalDateTime.now());
        newUser.setCreatedAt(LocalDateTime.now());
        userMap.put(newUser.getId(), newUser);
        return newUser;
    }

    /**
     * Updates an existing user partially using patch data.
     * Retrieves the existing user by ID, applies patch data, and updates the user in the map.
     * @param id The ID of the user to be updated.
     * @param patchUser The partial data to update the user.
     * @return The updated user.
     */
    public User patchUser(Long id, User patchUser) {
        User preUser = userMap.get(id);

        if (preUser == null) {
            return null;
        }

        if (patchUser.getUsername() != null && !patchUser.getUsername().equals(preUser.getUsername())) {
            preUser.setUsername(patchUser.getUsername());
        }

        if (patchUser.getEmail() != null && !patchUser.getEmail().equals(preUser.getEmail())) {
            preUser.setEmail(patchUser.getEmail());
        }

        if (patchUser.getPassword() != null && !patchUser.getPassword().equals(preUser.getPassword())) {
            preUser.setPassword(patchUser.getPassword());
        }

        if (patchUser.getFirstName() != null && !patchUser.getFirstName().equals(preUser.getFirstName())) {
            preUser.setFirstName(patchUser.getFirstName());
        }

        if (patchUser.getLastName() != null && !patchUser.getLastName().equals(preUser.getLastName())) {
            preUser.setLastName(patchUser.getLastName());
        }

        if (patchUser.getBirthdate() != null && !patchUser.getBirthdate().equals(preUser.getBirthdate())) {
            preUser.setBirthdate(patchUser.getBirthdate());
        }

        if (patchUser.getGender() != null && !patchUser.getGender().equals(preUser.getGender())) {
            preUser.setGender(patchUser.getGender());
        }

        if (patchUser.getSelectedAvatar() != null && !patchUser.getSelectedAvatar().equals(preUser.getSelectedAvatar())) {
            preUser.setSelectedAvatar(patchUser.getSelectedAvatar());
        }

        preUser.setUpdatedAt(LocalDateTime.now());
        userMap.put(id, preUser);
        return preUser;
    }

    /**
     * Retrieves a user by its ID from the user map.
     * @param id The ID of the user to retrieve.
     * @return The user with the specified ID, or null if not found.
     */
	public User obtainUser (Long id){
		return userMap.get(id);
	}

    /**
     * Checks if a user exists with the provided username and password.
     * @param username The username of the user.
     * @param password The password of the user.
     * @return The user object if it exists with the provided credentials, otherwise null.
     */
    public User existsUser(String username, String password){
        for (User u : obtainAllUsers()) {
            if (u.getUsername().equals(username) && u.getPassword().equals(password)) {
                return u;
            }
        }
        return null;
    }

    /**
     * Retrieves all users from the user map.
     * @return A collection of all users.
     */
	public Collection<User> obtainAllUsers() {
        return userMap.values();
    }

    /**
     * Updates an existing user completely using new data.
     * Retrieves the existing user by ID, updates its data, and replaces it in the map.
     * @param id The ID of the user to be updated.
     * @param updateUser The new data for the user.
     * @return The updated user.
     */
    public User updateUser(Long id, User updateUser) {
        if (!userMap.containsKey(id)) {
            return null;
        }
        updateUser.setId(id);
        updateUser.setCreatedAt(userMap.get(id).getCreatedAt());
        updateUser.setUpdatedAt(LocalDateTime.now());
        userMap.put(id, updateUser);
        return updateUser;
    }

    /**
     * Deletes a user from the user map by its ID.
     * @param id The ID of the user to delete.
     */
    public void deleteUser(Long id) {
        userMap.remove(id);
    }

    /**
     * Checks if a user can log in.
     * @param user The user attempting to log in.
     * @return True if the user exists and the provided credentials match, otherwise false.
     */
    public boolean logIn(User user){
        if (user == null) {
            return false;
        }
        if (!userMap.containsKey(user.getId())) {
            return false;
        }
        for (User u : obtainAllUsers()) {
            if (u.getUsername().equals(user.getUsername()) && u.getPassword().equals(user.getPassword())) {
                return true;
            }
        }
        return false;
    }
}