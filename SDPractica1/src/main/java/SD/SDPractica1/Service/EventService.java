package SD.SDPractica1.Service;

import SD.SDPractica1.Entities.Event;
import org.springframework.stereotype.Service;
import java.text.Normalizer;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class EventService {
    private Map<Long, Event> eventMap = new ConcurrentHashMap<>();
    private AtomicLong counter = new AtomicLong();

    public EventService() {
        String url1 = "https://i.blogs.es/25d3e1/rammstein/1366_2000.jpg";
        String url2 = "https://cloudfront-eu-central-1.images.arcpublishing.com/prisa/SYY4JP477GNNYVCBWQIXXDYRAY.jpg";
        String url3 = "https://images.lifestyleasia.com/wp-content/uploads/sites/5/2023/06/12142541/coldplay-singapore-concert-2024-music-of-the-spheres-world-tour-asian-leg-ticket-prices-details-sale-dates-venue-1.jpeg";
        String url4 = "https://uh.gsstatic.es/sfAttachPlugin/1307299.jpg";
        String url5 = "https://s2.ppllstatics.com/elcomercio/www/multimedia/2023/12/19/estopa-gijon-life-kRGF-U2101033974079SXG-1200x840@El%20Comercio.jpg";

        Event event1 = new Event("RAMMSTEIN", "Feur frei! con tus alemanes favoritos!", "2024-10-03 20:00", url1);
        Long id1 = counter.incrementAndGet();
        event1.setId(id1);
        eventMap.put(id1, event1);

        Event event2 = new Event("METALLICA", "Master, master, where's the dreams that I've been after? Ven a dejarte la garganta en un concierto único.", "2024-07-12 19:30", url2);
        Long id2 = counter.incrementAndGet();
        event2.setId(id2);
        eventMap.put(id2, event2);

        Event event3 = new Event("COLDPLAY", "Nobody said it was easy, no one ever said it would be this hard.", "2025-07-03 20:30", url3);
        Long id3 = counter.incrementAndGet();
        event3.setId(id3);
        eventMap.put(id3, event3);

        Event event4 = new Event("AITANA", "¡Ven y disfruta de Aitana en una noche inolvidable!", "2024-05-19 21:00", url4);
        Long id4 = counter.incrementAndGet();
        event4.setId(id4);
        eventMap.put(id4, event4);

        Event event5 = new Event("ESTOPA", "Esto... es... ESTOPA!", "2024-06-22 20:00", url5);
        Long id5 = counter.incrementAndGet();
        event5.setId(id5);
        eventMap.put(id5, event5);
    }

    /**
     * Adds a new event to the event map.
     * Sets the ID, creation and update timestamps, and adds the event to the map.
     * @param newEvent The event to be added.
     * @return The added event.
     */
    public Event addEvent(Event newEvent) {
        newEvent.setId(counter.incrementAndGet());
        newEvent.setUpdatedAt(LocalDateTime.now());
        newEvent.setCreatedAt(LocalDateTime.now());
        eventMap.put(newEvent.getId(), newEvent);
        return newEvent;
    }

    /**
     * Updates an existing event partially using patch data.
     * Retrieves the existing event by ID, applies patch data, and updates the event in the map.
     * @param id The ID of the event to be updated.
     * @param patchEvent The partial data to update the event.
     * @return The updated event.
     */
    public Event patchEvent(Long id, Event patchEvent) {
        Event preEvent = eventMap.get(id);

        if (preEvent == null) {
            return null;
        }

        if (patchEvent.getName() != null && !patchEvent.getName().equals(patchEvent.getName())) {
            preEvent.setName(patchEvent.getName());
        }

        if (patchEvent.getDescription() != null && !patchEvent.getDescription().equals(preEvent.getDescription())) {
            preEvent.setDescription(patchEvent.getDescription());
        }

        if (patchEvent.getDateTime() != null && !patchEvent.getDateTime().equals(preEvent.getDateTime())) {
            preEvent.setDateTime(patchEvent.getDateTime());
        }

        if (patchEvent.getImage() != null && !patchEvent.getImage().equals(preEvent.getImage())) {
            preEvent.setImage(patchEvent.getImage());
        }

        preEvent.setUpdatedAt(LocalDateTime.now());
        eventMap.put(id, preEvent);
        return preEvent;
    }

    /**
     * Retrieves an event by its ID from the event map.
     * @param id The ID of the event to retrieve.
     * @return The event with the specified ID, or null if not found.
     */
    public Event obtainEvent(Long id) {
        return eventMap.get(id);
    }

    /**
     * Retrieves all events from the event map.
     * @return A collection of all events.
     */
    public Collection<Event> obtainAllEvents() {
        return eventMap.values();
    }

    /**
     * Updates an existing event completely using new data.
     * Retrieves the existing event by ID, updates its data, and replaces it in the map.
     * @param id The ID of the event to be updated.
     * @param newEvent The new data for the event.
     * @return The updated event.
     */
    public Event updateEvent(Long id, Event newEvent) {
        if (!eventMap.containsKey(id)) {
            return null;
        }
        newEvent.setId(id);
        newEvent.setCreatedAt(eventMap.get(id).getCreatedAt());
        newEvent.setUpdatedAt(LocalDateTime.now());
        eventMap.put(id, newEvent);
        return newEvent;
    }

    /**
     * Deletes an event from the event map by its ID.
     * @param id The ID of the event to delete.
     */
    public void deleteEvent(Long id) {
        eventMap.remove(id);
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
     * Searches for events based on a given string.
     * Normalizes the search string and compares it with event names and descriptions.
     * @param s The search string.
     * @return An ArrayList of events matching the search criteria.
     */
    public ArrayList<Event> search(String s) {
        ArrayList<Event> results = new ArrayList<>();
        String string = normalizeString(s.toUpperCase(Locale.ROOT));
        if (s.isEmpty()){
            results = null;
            return results;
        }
        for (Event e : obtainAllEvents()) {
            if (normalizeString(e.getName().toUpperCase()).contains(string) || normalizeString(e.getDescription().toUpperCase()).contains(string)) {
                results.add(e);
            }
        }
        return results;
    }
}