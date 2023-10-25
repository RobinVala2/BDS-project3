package bds.project3.api;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class AppDetailedView
{
    private final LongProperty id = new SimpleLongProperty();
    private final StringProperty first_name = new SimpleStringProperty();
    private final StringProperty last_name = new SimpleStringProperty();
    private final StringProperty birthday = new SimpleStringProperty();
    private final StringProperty gender = new SimpleStringProperty();
    private final StringProperty city = new SimpleStringProperty();
    private final StringProperty email = new SimpleStringProperty();

    public Long getId()
    {
        return this.id.get();
    }

    public void setId(Long id)
    {
        this.id.setValue(id);
    }

    public String getLastName()
    {
        return this.last_name.get();
    }

    public String getFirstName()
    {
        return this.first_name.get();
    }

    public String getGender()
    {
        return this.gender.get();
    }

    public String getBirthday()
    {
        return this.birthday.get();
    }

    public String getCity()
    {
        return this.city.get();
    }

    public String getEmail()
    {
        return this.email.get();
    }

    public void setFirstName(String first_name)
    {
        this.first_name.setValue(first_name);
    }

    public void setLastName(String last_name) { this.last_name.setValue(last_name); }

    public void setGender(String gender)
    {
        this.gender.setValue(gender);
    }

    public void setBirthday(String birthday)
    {
        this.birthday.setValue(birthday);
    }

    public void setCity(String city)
    {
        this.city.setValue(city);
    }

    public void setEmail(String email) { this.email.setValue(email); }


}
