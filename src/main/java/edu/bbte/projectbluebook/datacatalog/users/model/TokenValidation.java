package edu.bbte.projectbluebook.datacatalog.users.model;

public class TokenValidation {

    private String userName;
    private String role;

    public TokenValidation(String userName, String role) {
        this.userName = userName;
        this.role = role;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int roleid() {
        if (role.equals("admin")) {
            return 3;
        }
        if (role.equals("user")) {
            return 2;
        }
        return 1;
    }
}
