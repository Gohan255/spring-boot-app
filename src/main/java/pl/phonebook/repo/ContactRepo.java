package pl.phonebook.repo;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import pl.phonebook.model.Contact;

import java.util.Optional;

public interface ContactRepo extends JpaRepository<Contact, Long> {

}
