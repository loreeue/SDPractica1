package SD.SDPractica1.Controllers;

import SD.SDPractica1.Entities.Event;
import SD.SDPractica1.Service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Collection;
import java.util.Objects;

@RestController
@RequestMapping("/api/event")
public class EventRESTController {
    @Autowired
    private EventService eventService;

    /**
     * This method handles POST requests.
     * Adds a new event received in the request body.
     * @param newEvent The event to be added.
     * @return ResponseEntity with status 201 (Created) and the added event in the body or status 400 (Bad Request).
     */
    @PostMapping
    public ResponseEntity<Event> addEvent(@RequestBody Event newEvent) {
        if (newEvent.getId() != null || newEvent.getName() == null || newEvent.getDescription() == null || newEvent.getDateTime() == null || newEvent.getImage() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(201).body(eventService.addEvent(newEvent));
    }

    /**
     * This method handles GET requests.
     * Retrieves an event by its ID.
     * @param id The ID of the event to retrieve.
     * @return ResponseEntity with status 201 (OK) and the event in the body if found, or status 404 (Not Found) if not found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Event> obtainEvent(@PathVariable Long id) {
        Event newEvent = eventService.obtainEvent(id);
        if (newEvent == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(201).body(newEvent);
    }

    /**
     * This method handles GET requests.
     * Retrieves all events.
     * @return ResponseEntity with status 200 (OK) and a collection of events in the body.
     */
    @GetMapping
    public ResponseEntity<Collection<Event>> obtainAllEvents() {
        return ResponseEntity.ok(eventService.obtainAllEvents());
    }

    /**
     * This method handles PUT requests.
     * Updates an event with the specified ID using data from the request body.
     * @param id The ID of the event to update.
     * @param updateEvent The updated event data.
     * @return ResponseEntity with status 200 (OK) and the updated event in the body or status 400 (Bad Request) or status 404 (Not Found).
     */
    @PutMapping("/{id}")
    public ResponseEntity<Event> updateEvent(@PathVariable Long id, @RequestBody Event updateEvent) {
        Event existingEvent = eventService.obtainEvent(id);
        if (existingEvent == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        if ((updateEvent.getId() != null && !Objects.equals(updateEvent.getId(), id)) || updateEvent.getName() == null || updateEvent.getDescription() == null || updateEvent.getDateTime() == null || updateEvent.getImage() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        Event updatedEvent = eventService.updateEvent(id, updateEvent);
        return ResponseEntity.ok(updatedEvent);
    }

    /**
     * This method handles PATCH requests.
     * Updates an event with the specified ID using partial data from the request body.
     * @param id The ID of the event to update.
     * @param updateEvent The partial updated event data.
     * @return ResponseEntity with status 200 (OK) and a success message or status 400 (Bad Request) or status 404 (Not Found).
     */
    @PatchMapping("/{id}")
    public ResponseEntity<?> patchEvent (@PathVariable Long id, @RequestBody Event updateEvent) {
        if (updateEvent == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        if (updateEvent.getId() != null && !Objects.equals(updateEvent.getId(), id)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        Event updatedEvent = eventService.patchEvent(id, updateEvent);
        return ResponseEntity.ok(updatedEvent);
    }

    /**
     * This method handles DELETE requests.
     * Deletes an event with the specified ID.
     * @param id The ID of the event to delete.
     * @return ResponseEntity with status 200 (OK) and a success message.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return ResponseEntity.ok("Evento con ID: " + id + " eliminado con éxito");
    }
}