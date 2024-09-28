package com.example.joshlongmeetup.adoption;

import com.example.joshlongmeetup.adoption.validation.Validation;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.annotation.Id;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@ResponseBody
public class DogAdoptionController {

    private final DogRepository repository;

    private final ApplicationEventPublisher publisher;

    private final Validation validation;

    public DogAdoptionController(DogRepository repository, ApplicationEventPublisher publisher, Validation validation) {
        this.repository = repository;
        this.publisher = publisher;
        this.validation = validation;
    }

    @PostMapping("/dogs/{dogId}/adoptions")
    void adopt(@PathVariable Integer dogId, @RequestBody Map<String, String> owner){
        this.repository.findById(dogId).ifPresent(dog -> {
            var newDog = new Dog(dogId, dog.name(), dog.description(), owner.get("name"));
            this.repository.save(newDog);

            this.publisher.publishEvent(new DogAdoptionEvent(dogId));
        });
    }
}



// DATA ORIENTED PROGRAMMING
// - sealed types
// - records
// - pattern matching
// - smart switch expressions

interface DogRepository extends ListCrudRepository<Dog, Integer> {
}


// look mon, NO LOMBOK!
record Dog(@Id Integer id, String name, String description, String owner){

}



//

sealed interface Loan permits SecuredLoan, UnsecuredLoan {
//    void validate() throws IllegalStateException;
}

// 아래와 같은 원치않은 확장을 막기 위해 sealedClass 사용
//class NoOpLoan implements Loan {
//
//    @Override
//    public void validate() throws IllegalStateException {
//
//    }
//}

final class SecuredLoan implements Loan {}

record UnsecuredLoan(float interest) implements Loan {

}

class Loans {
    String displayMessageFor(Loan loan){
//        var msg = "";
//
//        if(loan instanceof SecuredLoan){
//            msg = "good job. well done. you got a loan.";
//        }
//
//        if (loan instanceof UnsecuredLoan (var interest) ){
////            var usl = (UnsecuredLoan) loan;
//            msg = "ouch! that " + interest + "% rate hurts";
//        }

        return switch (loan){
            case UnsecuredLoan(var interest) -> "ouch! that " + interest + "% rate hurts";
            case SecuredLoan sl -> "good job. welldone. you got a loan";
        };
    }
}