package bds.project3.api;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class InjectionView
{
    private final LongProperty id = new SimpleLongProperty();
    private final StringProperty nickname = new SimpleStringProperty();
    private final StringProperty password = new SimpleStringProperty();
    private final StringProperty email = new SimpleStringProperty();

    public long getId()
    {
        return id.get();
    }

    public void setId(long id)
    {
        this.id.set(id);
    }

    public LongProperty idProperty()
    {
        return id;
    }

    public String getNickname()
    {
        return nickname.get();
    }

    public void setNickname(String nickname)
    {
        this.nickname.set(nickname);
    }

    public StringProperty NicknameProperty()
    {
        return nickname;
    }

    public String getPassword()
    {
        return password.get();
    }

    public void setPassword(String password)
    {
        this.password.set(password);
    }

    public StringProperty PasswordProperty()
    {
        return password;
    }



    public String getEmail()
    {
        return email.get();
    }

    public void setEmail(String email)
    {
        this.email.set(email);
    }

    public StringProperty emailProperty()
    {
        return email;
    }
}
