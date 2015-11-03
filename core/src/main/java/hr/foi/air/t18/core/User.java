package hr.foi.air.t18.core;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Danijel on 25.10.2015..
 */
public class User
{
    private String email;
    private String username;
    private char gender;
    private String dateOfBirth;

    public User() {}

    public User(String email, String username, char gender, String dateOfBirth)
    {
        this.email = email;
        this.username = username;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmail()
    {
        return this.email;
    }

    public String getUsername()
    {
        return this.username;
    }

    public char getGender()
    {
        return this.gender;
    }

    public String getDateOfBirth()
    {
        return this.dateOfBirth;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public void setGender(char gender)
    {
        this.gender = gender;
    }

    public void setDateOfBirth(String dateOfBirth)
    {
        this.dateOfBirth = dateOfBirth;
    }

    public boolean validateEmail()
    {
        String regex = "^[a-z0-9]+[a-z0-9._*][a-z0-9]+\\@[a-z]+\\.[a-z]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(this.email);
        return matcher.matches();
    }

    public boolean validatePassword(String password1, String password2)
    {
        return password1.equals(password2);
    }

}
