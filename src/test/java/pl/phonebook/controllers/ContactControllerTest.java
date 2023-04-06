package pl.phonebook.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import pl.phonebook.model.Contact;
import pl.phonebook.model.enums.ContactType;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ContactControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    private Contact c1;
    private Contact c2;

    @Before
    public void init(){
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        c1 = new Contact(1L, "Jan", ContactType.E_MAIL, "jan@gmail.com");
        c2 = new Contact(2L, "Andrzej", ContactType.E_MAIL, "andrzej@gmail.com");
    }

    @Test
    public void getAllContactsAPI() throws Exception {
        List<Contact> contacts = List.of(c1,c2);

        String requestBody = new ObjectMapper().writeValueAsString(contacts);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/contacts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(contacts.toString()))
                .andDo(print())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(status().isOk());
    }

    @Test
    public void saveContactAPI() throws Exception {
        // given
        Contact contact = Contact.builder()
                .id(1L)
                .surname("Jan")
                .contactType(ContactType.E_MAIL)
                .value("asdas@gmail.com")
                .build();

        String requestBody = new ObjectMapper().writeValueAsString(contact);

        // when and then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/contacts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.surname").value("Jan"))
                .andExpect(jsonPath("$.value").value("asdas@gmail.com"));
    }

}
