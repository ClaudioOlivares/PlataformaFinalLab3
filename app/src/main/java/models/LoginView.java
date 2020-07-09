package models;

public class LoginView {
    private String Email;
    private String Clave;

    public LoginView(String email, String clave) {
        email = email;
        clave = clave;
    }

    public LoginView() {
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getClave() {
        return Clave;
    }

    public void setClave(String clave) {
        Clave = clave;
    }
}
