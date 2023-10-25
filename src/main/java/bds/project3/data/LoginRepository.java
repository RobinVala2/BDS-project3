package bds.project3.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import bds.project3.App;
import bds.project3.api.LoginView;
import bds.project3.config.DataSourceConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginRepository {
    private static final Logger logger = LoggerFactory.getLogger(LoginRepository.class);

    public LoginView getLoginView(String username) {
        try (Connection connection = DataSourceConfig.getConnection();
             PreparedStatement prpstmt = connection.prepareStatement(
                     "SELECT username, password FROM bds.employee_login WHERE username = ?;"))
            {
                prpstmt.setString(1, username);
                try (ResultSet rs = prpstmt.executeQuery())
                    {
                        if (rs.next())
                            {
                                return mapToLoginView(rs);
                            }
                    }
            } catch (SQLException e)
            {
                logger.error("Couldn't open Login view.");
            }
        return null;
    }

    private LoginView mapToLoginView(ResultSet rs) throws SQLException {

        LoginView loginView = new LoginView();
        loginView.setHashed_password(rs.getString("password"));
        loginView.setUsername(rs.getString("username"));
        return loginView;
    }

}
