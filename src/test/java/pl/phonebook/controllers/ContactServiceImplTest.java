package pl.phonebook.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.phonebook.model.enums.ContactType;
import pl.phonebook.model.Contact;
import pl.phonebook.repo.ContactRepo;
import pl.phonebook.services.impl.ContactServiceImpl;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
public class ContactServiceImplTest {
    @Mock
    private ContactRepo contactRepo;
    @InjectMocks
    private ContactServiceImpl contactServiceImpl;

    @Captor
    private ArgumentCaptor<Contact> contactArgumentCaptor;

    private Contact c1;
    private Contact c2;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        c1 = new Contact(1L, "Jan", ContactType.E_MAIL, "jan@gmail.com");
        c2 = new Contact(2L, "Andrzej", ContactType.E_MAIL, "andrzej@gmail.com");
    }

    @Test
    public void shouldReturnAllContactsFromRepo() {
        //given
        List<Contact> contacts = Arrays.asList(c1, c2);

        //when
        when(contactRepo.findAll()).thenReturn(contacts);

        List<Contact> result = contactServiceImpl.findAllContacts();

        //then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(result.get(1), c2);
        assertEquals(contacts, result);

    }

    @Test
    public void shouldSaveContactCorrectly() throws Exception {
        //when
        when(contactRepo.save(ArgumentMatchers.any(Contact.class))).thenReturn(c1);

        Contact c = contactServiceImpl.saveContact(c1);

        //then
        assertEquals(c1, c);

        verify(contactRepo).save(contactArgumentCaptor.capture());
        Contact saved = contactArgumentCaptor.getValue();
        Assertions.assertEquals(c1, saved);

        verify(contactRepo, times(1)).save(c1);
    }

    @Test
    public void shouldDeleteContactCorrectly() throws Exception {
        //when
        when(contactRepo.findById(ArgumentMatchers.any(Long.class))).thenReturn(Optional.ofNullable(c1));

        Contact result = contactServiceImpl.deleteContactById(1L);

        //then
        assertEquals(result, c1);

        verify(contactRepo).delete(contactArgumentCaptor.capture());
        Contact deleted = contactArgumentCaptor.getValue();
        Assertions.assertEquals(c1, deleted);
    }
}

 /*       MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/contacts")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(contactContent);

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(notNullValue()))
                .andExpect(jsonPath("$.surname").value("Jan"));*/


/*    @Test
    public void shouldUpdateRecordCorrectly() throws Exception {
        Contact updatedContact = new Contact(1L, "UPDATED", "UPDATED", "UPDATED");

        Mockito.when(contactRepo.findById(c1.getId())).thenReturn(Optional.ofNullable(c1));
        Mockito.when(contactRepo.save(updatedContact)).thenReturn(updatedContact);

        String updatedContent = objectWriter.writeValueAsString(updatedContact);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/contacts/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(updatedContent);

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(notNullValue()))
                .andExpect(jsonPath("$.surname").value("UPDATED"));

    }

    @Test
    public void shouldDeleteContactFromRepoByID() throws Exception {
        Mockito.when(contactRepo.existsById(c1.getId())).thenReturn(true);
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/contacts/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }*/
