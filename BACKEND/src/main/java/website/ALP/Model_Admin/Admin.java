package website.ALP.Model_Admin;

public class Admin {
    private String username;
    private String password;
    private String email;
    private String Role;

    public Admin(){}
    
    public Admin(String username, String password, String email, String role) {
        this.username = username;
        this.password = password;
        this.email = email;
        Role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }

    
}
