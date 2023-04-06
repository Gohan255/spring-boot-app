package pl.phonebook.exceptions;


import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NoEntityException extends EntityNotFoundException {

    public NoEntityException(String message) {
        super(message);
    }
}
