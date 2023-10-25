package bds.project3.api;

import java.sql.Date;

public class AppCreateView
{
    private Long id;
    private String first_name;
    private String last_name;
    private Date birthday;
    private String gender;

    private String email;

    private String city;

    public Long getId()
    {
        return this.id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getFirstName()
    {
        return this.first_name;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public String getCity()
    {
        return this.city;
    }
    public void setFirstName(String first_name)
    {
        this.first_name = first_name;
    }

    public String getLastName()
    {
        return this.last_name;
    }

    public Date getBirthday()
    {
        return this.birthday;
    }

    public String getGender()
    {
        return this.gender;
    }

    public String getEmail()
    {
        return this.email;
    }

    public void setLastName(String last_name)
    {
        this.last_name = last_name;
    }

    public void setBirthday(Date birthday)
    {
        this.birthday = birthday;
    }

    public void setGender(String gender)
    {
        this.gender = gender;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }
}
