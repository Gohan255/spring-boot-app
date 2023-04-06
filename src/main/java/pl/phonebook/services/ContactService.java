package pl.phonebook.services;

import pl.phonebook.model.Contact;
import pl.phonebook.model.dto.ContactDto;

import java.util.List;

public interface ContactService {

    List<Contact> findAllContacts();
    Contact saveContact(Contact contact);
    Contact deleteContactById(long id);
    Contact updateContact(long id, Contact contact);
}
