package pl.phonebook.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.phonebook.model.Contact;
import pl.phonebook.model.dto.ContactDto;
import pl.phonebook.model.mapper.ContactMapper;
import pl.phonebook.services.impl.ContactServiceImpl;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/contacts")
@Slf4j//logger
public class ContactController {
    private ContactServiceImpl contactServiceImpl;
    private ContactMapper contactMapper;

    public ContactController(ContactServiceImpl contactServiceImpl, ContactMapper contactMapper) {
        this.contactServiceImpl = contactServiceImpl;
        this.contactMapper = contactMapper;
    }

    @GetMapping
    public ResponseEntity<List<ContactDto>> findAll() {
        List<Contact> contacts = contactServiceImpl.findAllContacts();
        List<ContactDto> contactsDto = contacts.stream().map(contact -> contactMapper.toDto(contact)).collect(Collectors.toList());
        return ResponseEntity.ok(contactsDto);
    }
    @PostMapping
    public ResponseEntity<ContactDto> save(@RequestBody Contact contact) {
        ContactDto contactDto = contactMapper.toDto(contactServiceImpl.saveContact(contact));
        return ResponseEntity.status(HttpStatus.CREATED).body(contactDto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<ContactDto> deleteContact(@PathVariable(value = "id") long id) {
        ContactDto contactDto = contactMapper.toDto(contactServiceImpl.deleteContactById(id));
        return ResponseEntity.status(HttpStatus.OK).body(contactDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContactDto> updateContact(@PathVariable(value = "id") long id, @RequestBody Contact contact) {
        ContactDto contactDto = contactMapper.toDto(contactServiceImpl.updateContact(id, contact));
        return ResponseEntity.status(HttpStatus.OK).body(contactDto);
    }

}
