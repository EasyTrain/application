package live.easytrain.application.entity;

public class Email {

    private String emailAddress;

    public Email() {
    }

    public Email(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Email email = (Email) o;

        return getEmailAddress() != null ? getEmailAddress().equals(email.getEmailAddress()) : email.getEmailAddress() == null;
    }

    @Override
    public int hashCode() {
        return getEmailAddress() != null ? getEmailAddress().hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Email{" +
                "emailAddress='" + emailAddress + '\'' +
                '}';
    }
}