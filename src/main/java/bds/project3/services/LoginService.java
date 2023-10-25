package bds.project3.services;


import bds.project3.api.LoginView;
import bds.project3.data.LoginRepository;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;


public class LoginService {

    private final LoginRepository loginRepository;

    public LoginService(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }


    public boolean login(String username, String password) {
        LoginView loginView = loginRepository.getLoginView(username);

        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id); //instance of the Argon2 interface
        String hash = argon2.hash(4, 1024 * 1024, 8, password); // encoded password hash

        if (loginView != null) {

            return argon2.verify(hash, loginView.getHashed_password());

        } else {
            return false;
        }

    }

}
