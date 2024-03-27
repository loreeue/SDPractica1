package SD.SDPractica1.Controllers;

import SD.SDPractica1.Entities.Location;
import SD.SDPractica1.Service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import java.util.Collection;
import java.util.Objects;

@RestController
@RequestMapping("/api/location")
public class LocationRESTController {
	@Autowired
    private LocationService locationService;

    /**
     * This method handles POST requests.
     * Adds a new location received in the request body.
     * @param newLocation The location to be added.
     * @return ResponseEntity with status 201 (Created) and the added location in the body or status 400 (Bad Request).
     */
    @PostMapping
    public ResponseEntity<Location> addLocation(@RequestBody Location newLocation) {
        if (newLocation.getId() != null || newLocation.getName() == null || newLocation.getAddress() == null || newLocation.getCapacity() == 0 || newLocation.getType() == null || newLocation.getAccessibility() == null || newLocation.getCity() == null || newLocation.getCountry() == null || newLocation.getImage() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(201).body(locationService.addLocation(newLocation));
    }

    /**
     * This method handles GET requests.
     * Retrieves a location by its ID.
     * @param id The ID of the location to retrieve.
     * @return ResponseEntity with status 201 (OK) and the location in the body if found, or status 404 (Not Found) if not found.
     */
	@GetMapping("/{id}")
	public ResponseEntity<Location> obtainLocation(@PathVariable Long id){
		Location newLocation = locationService.obtainLocation(id);
		if (newLocation == null){
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return ResponseEntity.status(201).body(newLocation);
	}

    /**
     * This method handles GET requests.
     * Retrieves all locations.
     * @return ResponseEntity with status 200 (OK) and a collection of locations in the body.
     */
	@GetMapping
    public ResponseEntity<Collection<Location>> obtainAllLocations() {
        return ResponseEntity.ok(locationService.obtainAllLocations());
    }

    /**
     * This method handles PUT requests.
     * Updates a location with the specified ID using data from the request body.
     * @param id The ID of the location to update.
     * @param updateLocation The updated location data.
     * @return ResponseEntity with status 200 (OK) and the updated location in the body or status 400 (Bad Request) or status 404 (Not Found).
     */
    @PutMapping("/{id}")
    public ResponseEntity<Location> updateLocation(@PathVariable Long id, @RequestBody Location updateLocation) {
        if (updateLocation == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        if ((updateLocation.getId() != null && !Objects.equals(updateLocation.getId(), id)) || updateLocation.getName() == null || updateLocation.getAddress() == null || updateLocation.getCapacity() == 0 || updateLocation.getType() == null || updateLocation.getAccessibility() == null || updateLocation.getCity() == null || updateLocation.getCountry() == null || updateLocation.getImage() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
		Location updatedLocation = locationService.updateLocation(id, updateLocation);
        return ResponseEntity.ok(updatedLocation);
    }

    /**
     * This method handles PATCH requests.
     * Updates a location with the specified ID using partial data from the request body.
     * @param id The ID of the location to update.
     * @param updateLocation The partial updated location data.
     * @return ResponseEntity with status 200 (OK) and a success message or status 400 (Bad Request) or status 404 (Not Found).
     */
	@PatchMapping("/{id}")
	public ResponseEntity<?> patchLocation (@PathVariable Long id, @RequestBody Location updateLocation) {
        if (updateLocation == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        if (updateLocation.getId() != null && !Objects.equals(updateLocation.getId(), id)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        Location updatedLocation = locationService.patchLocation(id, updateLocation);
        return ResponseEntity.ok(updatedLocation);
	}

    /**
     * This method handles DELETE requests.
     * Deletes a location with the specified ID.
     * @param id The ID of the location to delete.
     * @return ResponseEntity with status 200 (OK) and a success message.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLocation(@PathVariable Long id) {
        locationService.deleteLocation(id);
        return ResponseEntity.ok("Localizacion con ID: " + id + " eliminada con éxito");
    }
}