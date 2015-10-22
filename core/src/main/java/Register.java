import java.util.Date;

/**
 * Registration logic for ChatUp app.
 * Created by Danijel on 22.10.2015..
 */
public class Register
{
    private String email;
    private String username;
    private String password;
    private String passwordRepeat;
    private char gender;
    private Date dateOfBirth;

    public Register() {};

    public Register(String email,
                    String username,
                    String password,
                    String passwordRepeat,
                    char   gender,
                    Date   dateOfBirth)
    {
        this.email = email;
        this.username = username;
        this.password = password;
        this.passwordRepeat = passwordRepeat;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getPasswordRepeat()
    {
        return passwordRepeat;
    }

    public void setPasswordRepeat(String passwordRepeat)
    {
        this.passwordRepeat = passwordRepeat;
    }

    public char getGender()
    {
        return gender;
    }

    public void setGender(char gender)
    {
        this.gender = gender;
    }

    public Date getDateOfBirth()
    {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth)
    {
        this.dateOfBirth = dateOfBirth;
    }
}
