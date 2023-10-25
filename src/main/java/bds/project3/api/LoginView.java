package bds.project3.api;

public class LoginView
{

    private String username;

    private String hashed_password;

    public String getUsername(){return username;}

    public String getHashed_password(){return hashed_password;}

    public  void setUsername(String username) {this.username = username;}

    public void setHashed_password(String hashed_password) {this.hashed_password = hashed_password;}

}