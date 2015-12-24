package hr.foi.air.t18.core;

import android.graphics.Bitmap;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class that represents a user.
 * Created by Danijel on 25.10.2015..
 */
public class User
{
    private String email;
    private String username;
    private String gender;
    private String dateOfBirth;
    private String status;
    private String password;
    private Bitmap profilePicture;

    /**
     * Default constructor.
     */
    public User() {}

    /**
     * Constructor used mainly for registration process of the
     * new user.
     * @param email User's email address
     * @param username User's username
     * @param gender User's gender
     * @param dateOfBirth User's date of birth
     */
    public User(String email, String username, String gender, String dateOfBirth, String password)
    {
        this.email = email;
        this.username = username;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.password=password;
    }
    public User(String email, String username, String gender, String dateOfBirth)
    {
        this.email = email;
        this.username = username;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
    }

    public User(String email, String username, String status)
    {
        this.email = email;
        this.username = username;
        this.status = status;
    }
    public User(String email, String username)
    {
        this.email = email;
        this.username = username;
    }
    /**
     * Gets user's email.
     * @return User's email
     */
    public String getEmail()
    {
        return this.email;
    }

    /**
     * Gets user's username.
     * @return User's username
     */
    public String getUsername()
    {
        return this.username;
    }

    /**
     * Gets user's gender.
     * @return User's gender as a <b>char</b>
     */
    public String getGender()
    {
        return this.gender;
    }

    /**
     * Gets user's date of birth.
     * @return User's date of birth
     */
    public String getDateOfBirth()
    {
        return this.dateOfBirth;
    }

    /**
     * Gets user's password.
     * @return User's password
     */
    public String getPassword()
    {
        return this.password;
    }

    /**
     * Sets user's email
     * @param email User's email
     */

    public void setEmail(String email)
    {
        this.email = email;
    }

    /**
     * Sets user's username.
     * @param username User's username
     */
    public void setUsername(String username)
    {
        this.username = username;
    }

    /**
     * Sets user's gender.
     * @param gender User's gender as a <b>char</b>.
     */
    public void setGender(String gender)
    {
        this.gender = gender;
    }

    /**
     * Sets user's password
     * @param password User's password
     */
    public void setPassword(String password)
    {
        this.password = password;
    }
    /**
     * Sets user's date of birth.
     * @param dateOfBirth User's date of birth
     */
    public void setDateOfBirth(String dateOfBirth)
    {
        this.dateOfBirth = dateOfBirth;
    }

    /**
     * Gets user's profile picture
     * @return User's profile picture
     */
    public Bitmap getProfilePicture() {
        return profilePicture;
    }

    /**
     * Sets user's profile picture
     * @param profilePicture
     */
    public void setProfilePicture(Bitmap profilePicture) {
        this.profilePicture = profilePicture;
    }

    /**
     * Validates user's email. Used for registration process.
     * @return True or False
     */
    public boolean validateEmail()
    {
        String regex = "^[a-z0-9]+[a-z0-9._*][a-z0-9]+\\@[a-z]+\\.[a-z]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(this.email);
        return matcher.matches();
    }

    /**
     * Validates user's password and repeated password. (The existance of this
     * method is class User needs to be discussed)
     * @param password1 User's password
     * @param password2 User's repeated password
     * @return True or False
     */
    public boolean validatePassword(String password1, String password2)
    {
        return password1.equals(password2);
    }

    public void setStatus(String status) {this.status = status;}

    public String getStatus(){return this.status;}

}
