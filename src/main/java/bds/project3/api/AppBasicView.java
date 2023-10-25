package bds.project3.api;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class AppBasicView
{
    private final LongProperty id = new SimpleLongProperty();
    private final StringProperty first_name = new SimpleStringProperty();
    private final StringProperty last_name = new SimpleStringProperty();
    private final StringProperty birthday = new SimpleStringProperty();
    private final StringProperty gender = new SimpleStringProperty();

    public Long getId()
    {
        return idProperty().get();
    }

    public String getFirst_Name()
    {
        return first_nameProperty().get();
    }

    public String getLast_name()
    {
        return last_nameProperty().get();
    }

    public String getBirthday()
    {
        return birthdayProperty().get();
    }

    public String getGender()
    {
        return genderProperty().get();
    }

    public void setId(Long id)
    {
        this.idProperty().setValue(id);
    }

    public void setFirst_name(String first_name)
    {
        this.first_nameProperty().setValue(first_name);
    }

    public void setLast_name(String last_name)
    {
        this.last_nameProperty().setValue(last_name);
    }

    public void setBirthday(String birthday)
    {
        this.birthdayProperty().setValue(birthday);
    }

    public void setGender(String gender)
    {
        this.genderProperty().setValue(gender);
    }

    public LongProperty idProperty()
    {
        return id;
    }

    public StringProperty first_nameProperty()
    {
        return first_name;
    }

    public StringProperty last_nameProperty()
    {
        return last_name;
    }

    public StringProperty birthdayProperty()
    {
        return birthday;
    }

    public StringProperty genderProperty()
    {
        return gender;
    }

}