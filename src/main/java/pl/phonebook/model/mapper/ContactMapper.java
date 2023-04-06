package pl.phonebook.model.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pl.phonebook.model.Contact;
import pl.phonebook.model.dto.ContactDto;

@Component
public class ContactMapper {
    private final ModelMapper modelMapper;
    public ContactMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
    public ContactDto toDto(Contact contact) {
        return modelMapper.map(contact, ContactDto.class);
    }
    public Contact toEntity(ContactDto contactDto) {
        return modelMapper.map(contactDto, Contact.class);
    }
}
