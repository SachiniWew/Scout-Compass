package com.scoutcomapss.api.event;

import com.scoutcomapss.api.auth.user.Role;
import com.scoutcomapss.api.auth.user.Scout;
import com.scoutcomapss.api.resource.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Optional;



@Service
public class EventService {




    @Autowired
    private EventRepository eventRepository;

    public String createEvent(EventCreateRequest eventCreateRequest){
        Boolean eventIsPresent = eventRepository.findByEventName(eventCreateRequest.getEventName()).isPresent();
        if(eventIsPresent){
            return  "Event already exists!";
        }else{
            Event event = Event.builder()
                    .eventName(eventCreateRequest.getEventName())
                    .eventDate(eventCreateRequest.getEventDate())
                    .dayDuration(eventCreateRequest.getDayDuration())
                    .eventLocation(eventCreateRequest.getEventLocation())
                    .startTime(eventCreateRequest.getStartTime())
                    .description(eventCreateRequest.getDescription())
                    .formLink(eventCreateRequest.getFormLink())
                    .build();

            eventRepository.save(event);


                return "Event Created Successfully ! ";

        }

    }


    public ArrayList<Event> getEventList(){
        ArrayList<Event> eventList = eventRepository.findAllByEventId();
        return  eventList;
    }

    public Event getLatestEvent(){
        Event event = eventRepository.getLatestEvent();
        return event;
    }


    public boolean deleteEvent(String eventName){
        Optional<Event> event = eventRepository.findByEventName(eventName);
        if (event.isPresent()) {
            eventRepository.delete(event.get());
            return true;
        } else {
            return false;
        }

    }

    public Long countAllEvents() {
        return eventRepository.countAllEvents();
    }

}
