package SD.SDPractica1.Service;

import SD.SDPractica1.Entities.Location;
import org.springframework.stereotype.Service;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collection;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class LocationService {
	private Map<Long, Location> locationMap = new ConcurrentHashMap<>();
    private AtomicLong counter = new AtomicLong();

    public LocationService(){
        String url1 = "https://img.huffingtonpost.es/files/image_720_480/uploads/2023/11/21/fachada-del-wizink-center-de-madrid.jpeg";
        Location location1 = new Location("Wizink Center", "Avenida de Felipe II, s/n", 17000, "Sala grande", "Sí", "Madrid","España", url1);
        Long id1 = counter.incrementAndGet();
        location1.setId(id1);
        locationMap.put(id1, location1);

        String url2 = "https://cadenaser.com/resizer/Akl0gq_zhNmopGEDwcsaHGrL0po=/736x552/filters:format(jpg):quality(70)/cloudfront-eu-central-1.images.arcpublishing.com/prisaradio/YD2DQTOWMZC4LNR3UN4SK6WQJY.jpg";
        Location location2 = new Location("Estadio Santiago Bernabéu", "Avenida Concha Espina, 1", 80000, "Estadio", "Sí","Madrid", "España", url2);
        Long id2 = counter.incrementAndGet();
        location2.setId(id2);
        locationMap.put(id2, location2);

        String url3 = "https://upload.wikimedia.org/wikipedia/commons/d/d8/Estadi_Ol%C3%ADmpic_Llu%C3%ADs_Companys.JPG";
        Location location3 = new Location("Estadio Olímpico Lluís Companys", "Passeig Olimpic, 17", 56000, "Estadio", "Sí","Barcelona", "España", url3);
        Long id3 = counter.incrementAndGet();
        location3.setId(id3);
        locationMap.put(id3, location3);

        String url4 = "https://indiehoy.com/wp-content/uploads/2018/09/sala-la-rivera-madrid.jpg";
        Location location4 = new Location("Sala La Riviera", "Paseo Bajo de la Virgen del Puerto, s/n", 2500, "Sala mediana", "Sí", "Madrid", "España", url4);
        Long id4 = counter.incrementAndGet();
        location4.setId(id4);
        locationMap.put(id4, location4);

        String url5 = "https://madridsecreto.co/wp-content/uploads/2023/04/parque-tierno.jpg";
        Location location5 = new Location("Parque Enrique Tierno Galván", "Calle Meneses, 4", 10000, "Parque", "Sí", "Madrid", "España", url5);
        Long id5 = counter.incrementAndGet();
        location5.setId(id5);
        locationMap.put(id5, location5);
    }

    /**
     * Adds a new location to the location map.
     * Sets the ID, creation and update timestamps, and adds the location to the map.
     * @param newLocation The location to be added.
     * @return The added location.
     */
    public Location addLocation(Location newLocation) {
        newLocation.setId(counter.incrementAndGet());
        newLocation.setUpdatedAt(LocalDateTime.now());
        newLocation.setCreatedAt(LocalDateTime.now());
        locationMap.put(newLocation.getId(), newLocation);
        return newLocation;
    }

    /**
     * Updates an existing location partially using patch data.
     * Retrieves the existing location by ID, applies patch data, and updates the location in the map.
     * @param id The ID of the location to be updated.
     * @param patchLocation The partial data to update the location.
     * @return The updated location.
     */
    public Location patchLocation(Long id, Location patchLocation) {
        Location preLocation = locationMap.get(id);

        if (preLocation == null) {
            return null;
        }

        if (patchLocation.getName() != null && !patchLocation.getName().equals(preLocation.getName())) {
            preLocation.setName(patchLocation.getName());
        }

        if (patchLocation.getAddress() != null && !patchLocation.getAddress().equals(preLocation.getAddress())) {
            preLocation.setAddress(patchLocation.getAddress());
        }

        if (patchLocation.getCapacity() != 0 && patchLocation.getCapacity() != preLocation.getCapacity()) {
            preLocation.setCapacity(patchLocation.getCapacity());
        }

        if (patchLocation.getType() != null && !patchLocation.getType().equals(preLocation.getType())) {
            preLocation.setType(patchLocation.getType());
        }

        if (patchLocation.getAccessibility() != null && !patchLocation.getAccessibility().equals(preLocation.getAccessibility())) {
            preLocation.setAccessibility(patchLocation.getAccessibility());
        }

        if (patchLocation.getCity() != null && !patchLocation.getCity().equals(preLocation.getCity())) {
            preLocation.setCity(patchLocation.getCity());
        }

        if (patchLocation.getCountry() != null && !patchLocation.getCountry().equals(preLocation.getCountry())) {
            preLocation.setCountry(patchLocation.getCountry());
        }

        if (patchLocation.getImage() != null && !patchLocation.getImage().equals(preLocation.getImage())) {
            preLocation.setImage(patchLocation.getImage());
        }

        preLocation.setUpdatedAt(LocalDateTime.now());
        locationMap.put(id, preLocation);
        return preLocation;
    }

    /**
     * Retrieves a location by its ID from the location map.
     * @param id The ID of the location to retrieve.
     * @return The location with the specified ID, or null if not found.
     */
	public Location obtainLocation (Long id){
		return locationMap.get(id);
	}

    /**
     * Retrieves all locations from the location map.
     * @return A collection of all locations.
     */
	public Collection<Location> obtainAllLocations() {
        return locationMap.values();
    }

    /**
     * Updates an existing location completely using new data.
     * Retrieves the existing location by ID, updates its data, and replaces it in the map.
     * @param id The ID of the location to be updated.
     * @param updateLocation The new data for the location.
     * @return The updated location.
     */
    public Location updateLocation(Long id, Location updateLocation) {
        if (!locationMap.containsKey(id)) {
            return null;
        }
        updateLocation.setId(id);
        updateLocation.setCreatedAt(locationMap.get(id).getCreatedAt());
        updateLocation.setUpdatedAt(LocalDateTime.now());
        locationMap.put(id, updateLocation);
        return updateLocation;
    }

    /**
     * Deletes a location from the location map by its ID.
     * @param id The ID of the location to delete.
     */
    public void deleteLocation(Long id) {
        locationMap.remove(id);
    }

    /**
     * Normalizes a string to avoid problems with accents.
     * @param input The input string to be normalized.
     * @return The normalized string.
     */
    private String normalizeString(String input) {
        return Normalizer.normalize(input, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }

    /**
     * Searches for locations based on a given string.
     * Normalizes the search string and compares it with location names, cities, countries and addresses.
     * @param s The search string.
     * @return An ArrayList of locations matching the search criteria.
     */
    public ArrayList<Location> search(String s) {
        ArrayList<Location> results = new ArrayList<>();
        String string = normalizeString(s.toUpperCase(Locale.ROOT));
        if (s.isEmpty()){
            results = null;
            return results;
        }
        for (Location l : obtainAllLocations()) {
            if (normalizeString(l.getCity().toUpperCase()).contains(string) || normalizeString(l.getCountry().toUpperCase()).contains(string) || normalizeString(l.getName().toUpperCase()).contains(string) || normalizeString(l.getAddress().toUpperCase()).contains(string)) {
                results.add(l);
            }
        }
        return results;
    }
}