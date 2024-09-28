package com.example.joshlongmeetup.vet;

import com.example.joshlongmeetup.adoption.DogAdoptionEvent;
import com.example.joshlongmeetup.adoption.validation.Validation;
import org.springframework.context.event.EventListener;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class Vet {

    // = (@Async + @Tranactional + @EventListener)
    @ApplicationModuleListener
//    @Async
//    @EventListener
    public void checkup(DogAdoptionEvent dogAdoptionEvent) throws InterruptedException {
        System.out.println("got a new event [ " + dogAdoptionEvent + " ]");
        Thread.sleep(5_000);
    }
}
