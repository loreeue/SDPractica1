package SD.SDPractica1.Controllers;

import SD.SDPractica1.Entities.Location;
import SD.SDPractica1.Service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.ArrayList;

@Controller
public class LocationController {
    @Autowired
    private LocationService locationService;

    /**
     * This method handles GET requests to "/web/listLocation" endpoint.
     * @param model The model to which locations are added.
     * @return The name of the view "ListLocation".
     */
    @GetMapping("/web/listLocation")
    public String listLocation(Model model) {
        model.addAttribute("location", locationService.obtainAllLocations());
        return "ListLocation";
    }

    /**
     * This method handles GET requests to "/web/addLocation" endpoint.
     * @param model The model to which locations are added.
     * @return The name of the view "AddLocation".
     */
    @GetMapping("/web/addLocation")
    public String addLocation(Model model) {
        Location newLocation = new Location();
        model.addAttribute("location", newLocation);
        return "AddLocation";
    }

    /**
     * This method handles POST requests to "/web/addLocation" endpoint.
     * @param model The model to which locations are added.
     * @param newLocation The new location that we add to the model.
     * @return Redirects to "/web/listLocation".
     */
    @PostMapping("/web/addLocation")
    public String addLocation(Location newLocation, Model model) {
        locationService.addLocation(newLocation);
        model.addAttribute("success", true);
        return "redirect:/web/listLocation";
    }

    /**
     * This method handles GET requests to "/web/locationDetails/{id}" endpoint.
     * @param model The model to which locations are added.
     * @param id The ID of the location.
     * @return Redirects to "/web/listLocation" or the name of the view "DetailLocation".
     */
    @GetMapping("/web/locationDetails/{id}")
    public String locationDetails(@PathVariable Long id, Model model) {
        Location newLocation = locationService.obtainLocation(id);
        if (newLocation == null) {
            return "redirect:/web/listLocation";
        }
        model.addAttribute("location", newLocation);
        return "DetailLocation";
    }

    /**
     * This method handles GET requests to "/web/editLocation/{id}" endpoint.
     * @param model The model to which locations are added.
     * @param id The ID of the location.
     * @return Redirects to "/web/listLocation" or the name of the view "EditLocation".
     */
    @GetMapping("/web/editLocation/{id}")
    public String editLocation(@PathVariable Long id, Model model) {
        Location newLocation = locationService.obtainLocation(id);
        if (newLocation == null) {
            System.out.println("No encontrado");
            return "redirect:/web/listLocation";
        }
        model.addAttribute("location", newLocation);
        return "EditLocation";
    }

    /**
     * This method handles POST requests to "/web/editLocation" endpoint.
     * @param model The model to which locations are added.
     * @param newLocation The location that we want to edit.
     * @return Redirects to "/web/listLocation" or redirects to "/web/locationDetails/{id}".
     */
    @PostMapping("/web/editLocation")
    public String editLocation(Location newLocation, Model model) {
        Location updatedLocation = locationService.updateLocation(newLocation.getId(), newLocation);
        if (updatedLocation == null) {
            return "redirect:/web/listLocation";
        }
        model.addAttribute("success", true);
        Long id = updatedLocation.getId();
        return "redirect:/web/locationDetails/" + id;
    }

    /**
     * This method handles GET requests to "/web/deleteLocation/{id}" endpoint.
     * @param id The ID of the location.
     * @return Redirects to "/web/listLocation".
     */
    @GetMapping("/web/deleteLocation/{id}")
    public String deleteLocation(@PathVariable Long id) {
        locationService.deleteLocation(id);
        return "redirect:/web/listLocation";
    }

    /**
     * This method handles GET requests to "" endpoint.
     * @param model The model to which locations are added.
     * @return The name of the view "MainScreen".
     */
    @GetMapping("")
    public String search(Model model) {
        String search = null;
        model.addAttribute("search", search);
        return "MainScreen";
    }

    /**
     * This method handles POST requests to "/web/searchLocation" endpoint.
     * @param model The model to which locations are added.
     * @param search The string we are searching in the web search.
     * @return The name of the view "ErrorSearch" or "ResultsLocation".
     */
    @PostMapping("/web/searchLocation")
    public String search(String search, Model model){
        ArrayList<Location> results = locationService.search(search);
        if (results.isEmpty()) {
            model.addAttribute("error", true);
            return "ErrorSearch";
        }
        model.addAttribute("array", results);
        return "ResultsLocation";
    }
}