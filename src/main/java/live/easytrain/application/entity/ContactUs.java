package live.easytrain.application.entity;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "contact_us")
public class ContactUs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull(message = "Please enter your first name.")
    @Size(min = 1, message = "Please enter your first name.")
    private String firstName;

    @NotNull(message = "Please enter your last name.")
    @Size(min = 1, message = "Please enter your last name.")
    private String lastName;

    @NotNull(message = "The content should not be empty.")
    @Size(min = 1, message = "The content should not be empty.")
    @Size(max = 1000, message = "The content should be 1000 characters in length.")
    @Column(name = "content", length = 1000)
    private String content;

    @NotNull(message = "The subject should not be empty.")
    @Size(min = 1, message = "The subject should not be empty.")
    private String subject;

    @Column(name = "phone_namuber", length = 20)
    private String phone;

    @NotNull(message = "Please enter the email.")
    @Size(min = 1, message = "Please enter the email.")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$", message = "Please enter a valid email.")
    private String email;

    private String terms;

    public ContactUs(String firstName, String lastName, String content, String subject, String phone, String email, String terms) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.content = content;
        this.subject = subject;
        this.phone = phone;
        this.email = email;
        this.terms = terms;
    }

    public ContactUs() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTerms() {
        return terms;
    }

    public void setTerms(String terms) {
        this.terms = terms;
    }

    public @NotNull(message = "Please enter your first name.") @Size(min = 1, message = "Please enter your first name.") String getFirstName() {
        return firstName;
    }

    public void setFirstName(@NotNull(message = "Please enter your first name.") @Size(min = 1, message = "Please enter your first name.") String firstName) {
        this.firstName = firstName;
    }

    public @NotNull(message = "Please enter your last name.") @Size(min = 1, message = "Please enter your last name.") String getLastName() {
        return lastName;
    }

    public void setLastName(@NotNull(message = "Please enter your last name.") @Size(min = 1, message = "Please enter your last name.") String lastName) {
        this.lastName = lastName;
    }
}
