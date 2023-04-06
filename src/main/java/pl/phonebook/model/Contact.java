package pl.phonebook.model;

import jakarta.persistence.*;
import lombok.*;
import pl.phonebook.model.enums.ContactType;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Table(name = "contacts")
@Builder
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String surname;
    @Enumerated(EnumType.STRING)
    private ContactType contactType;
    @Column(name = "contactValue")
    private String value;

}
