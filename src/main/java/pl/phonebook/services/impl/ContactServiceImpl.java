package pl.phonebook.services.impl;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.phonebook.exceptions.NoEntityException;
import pl.phonebook.exceptions.NoSuchIdException;
import pl.phonebook.model.Contact;
import pl.phonebook.repo.ContactRepo;
import pl.phonebook.services.ContactService;

import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class ContactServiceImpl implements ContactService {

    private ContactRepo contactRepo;

    public ContactServiceImpl(ContactRepo contactRepo) {
        this.contactRepo = contactRepo;
    }


    @Override
    public List<Contact> findAllContacts() {
        List<Contact> contacts = contactRepo.findAll();
        return contacts;
    }
    @Override
    public Contact saveContact(Contact contact) {
        if (Objects.isNull(contact)) {
            throw new NoEntityException("No entity to add!");
        }
        return contactRepo.save(contact);
    }
    @Override
    @Lock(LockModeType.PESSIMISTIC_READ)
    public Contact deleteContactById(long id) {
        Contact contact = contactRepo.findById(id)
                .orElseThrow(() -> new NoSuchIdException("Contact with id " + id + " not found!"));

        contactRepo.delete(contact);

        return contact;
    }
    @Override
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public Contact updateContact(long id, Contact contact) {

        Contact existingContact = contactRepo.findById(id)
                .orElseThrow(() -> new NoSuchIdException("Contact with id " + id + " not found!"));

        if(Objects.isNull(contact)){
            throw new NoEntityException("Object is null!");
        }

        existingContact.setSurname(contact.getSurname());
        existingContact.setContactType(contact.getContactType());
        existingContact.setValue(contact.getValue());

        return contactRepo.save(existingContact);
    }

}
