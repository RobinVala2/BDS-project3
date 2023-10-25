package bds.project3.api;

import java.sql.Date;

public class AppEditView
{
    private Long id;
    private String first_name;
    private String last_name;
    private Date birthday;
    private String gender;

    public Long getId()
    {
        return this.id;
    }

    public String getFirstName()
    {
        return this.first_name;
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

    public void setId(Long id)
    {
        this.id = id;
    }

    public void setFirstName(String first_name)
    {
        this.first_name = first_name;
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



}
