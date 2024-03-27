package SD.SDPractica1.Controllers;

import SD.SDPractica1.Entities.Event;
import SD.SDPractica1.Service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.ArrayList;

@Controller
public class EventController {
    @Autowired
    private EventService eventService;

    /**
     * This method handles GET requests to "/web" endpoint.
     * @return The name of the view "MainScreen".
     */
    @GetMapping("/web")
    public String main() {
        return "MainScreen";
    }

    /**
     * This method handles GET requests to "/web/listEvent" endpoint.
     * @param model The model to which events are added.
     * @return The name of the view "ListEvent".
     */
    @GetMapping("/web/listEvent")
    public String listEvents(Model model) {
        model.addAttribute("event", eventService.obtainAllEvents());
        return "ListEvent";
    }

    /**
     * This method handles GET requests to "/web/addEvent" endpoint.
     * @param model The model to which events are added.
     * @return The name of the view "AddEvent".
     */
    @GetMapping("/web/addEvent")
    public String addEvent(Model model) {
        Event newEvent = new Event();
        model.addAttribute("event", newEvent);
        return "AddEvent";
    }

    /**
     * This method handles POST requests to "/web/addEvent" endpoint.
     * @param model The model to which events are added.
     * @param newEvent The new event that we add to the model.
     * @return Redirects to "/web/listEvent".
     */
    @PostMapping("/web/addEvent")
    public String addEvent(Event newEvent, Model model) {
        eventService.addEvent(newEvent);
        model.addAttribute("success", true);
        return "redirect:/web/listEvent";
    }

    /**
     * This method handles GET requests to "/web/eventDetails/{id}" endpoint.
     * @param model The model to which events are added.
     * @param id The ID of the event.
     * @return Redirects to "/web/listEvent" or the name of the view "DetailEvent".
     */
    @GetMapping("/web/eventDetails/{id}")
    public String eventDetails(@PathVariable Long id, Model model) {
        Event newEvent = eventService.obtainEvent(id);
        if (newEvent == null) {
            return "redirect:/web/listEvent";
        }
        model.addAttribute("event", newEvent);
        return "DetailEvent";
    }

    /**
     * This method handles GET requests to "/web/editEvent/{id}" endpoint.
     * @param model The model to which events are added.
     * @param id The ID of the event.
     * @return Redirects to "/web/listEvent" or the name of the view "EditEvent".
     */
    @GetMapping("/web/editEvent/{id}")
    public String editEvent(@PathVariable Long id, Model model) {
        Event newEvent = eventService.obtainEvent(id);
        if (newEvent == null) {
            System.out.println("No encontrado");
            return "redirect:/web/listEvent";
        }
        model.addAttribute("event", newEvent);
        return "EditEvent";
    }

    /**
     * This method handles POST requests to "/web/editEvent" endpoint.
     * @param model The model to which events are added.
     * @param newEvent The event that we want to edit.
     * @return Redirects to "/web/listEvent" or redirects to "/web/eventDetails/{id}".
     */
    @PostMapping("/web/editEvent")
    public String editEvent(Event newEvent, Model model) {
        Event updatedEvent = eventService.updateEvent(newEvent.getId(), newEvent);
        if (updatedEvent == null) {
            return "redirect:/web/listEvent";
        }
        model.addAttribute("success", true);
        Long id = updatedEvent.getId();
        return "redirect:/web/eventDetails/" + id;
    }

    /**
     * This method handles GET requests to "/web/deleteEvent/{id}" endpoint.
     * @param id The ID of the event.
     * @return Redirects to "/web/listEvent".
     */
    @GetMapping("/web/deleteEvent/{id}")
    public String deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return "redirect:/web/listEvent";
    }

    /**
     * This method handles GET requests to "/error" endpoint.
     * @return The name of the view "Error".
     */
    @GetMapping("/error")
    public String errors() {
        return "Error";
    }

    /**
     * This method handles POST requests to "/web/searchEvent" endpoint.
     * @param model The model to which events are added.
     * @param search The string we are searching in the web search.
     * @return The name of the view "ErrorSearch" or "ResultsEvent".
     */
    @PostMapping("/web/searchEvent")
    public String search(String search, Model model){
        ArrayList<Event> results = eventService.search(search);
        if (results.isEmpty()) {
            model.addAttribute("error", true);
            return "ErrorSearch";
        }
        model.addAttribute("array", results);
        return "ResultsEvent";
    }
}