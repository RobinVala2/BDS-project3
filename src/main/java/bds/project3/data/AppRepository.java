package bds.project3.data;

import bds.project3.api.*;
import bds.project3.config.DataSourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AppRepository
{
    private static final Logger logger = LoggerFactory.getLogger(AppRepository.class);


    // Basic View
    public List<AppBasicView> getAllMembers()
    {
        try (Connection connection = DataSourceConfig.getConnection();
             PreparedStatement prpstmt = connection.prepareStatement(
                     "SELECT m.id_member, m.first_name, m.last_name, m.birthday, m.gender FROM bds.member m ORDER BY m.last_name;"))
            {
                return mapToAppView(prpstmt, connection);
            } catch (SQLException e)
            {
                logger.error("Failed to get member DB.");
            }
        return null;
    }

    private List<AppBasicView> mapToAppView(PreparedStatement prpstmt, Connection connection) throws SQLException
    {
        List<AppBasicView> view = new ArrayList<>();
        ResultSet rs = prpstmt.executeQuery();
        while (rs.next())
            {
                AppBasicView bv = new AppBasicView();
                bv.setId(rs.getLong("id_member"));
                bv.setFirst_name(rs.getString("first_name"));
                bv.setLast_name(rs.getString("last_name"));
                bv.setBirthday(rs.getString("birthday"));
                bv.setGender(rs.getString("gender"));
                view.add(bv);
            }
        return view;
    }

    // Detailed View
    public AppDetailedView getDetailedView(String last_name)
    {
        try (Connection connection = DataSourceConfig.getConnection();
             PreparedStatement prpstmt = connection.prepareStatement("SELECT id_member, first_name, last_name, birthday, gender, city, email  FROM  bds.member m\n " +
                     "LEFT JOIN bds.address a ON a.id_address = m.address\n " +
                     "LEFT JOIN bds.contact c ON c.id_contact = m.id_contact\n " +
                     "WHERE m.last_name = ?;"))
            {
                prpstmt.setString(1, last_name);
                try (ResultSet rs = prpstmt.executeQuery())
                    {
                        if (rs.next())
                            {
                              return mapToDetailedView(rs);
                            }
                    }
            } catch (SQLException e)
            {
                logger.error("Couldn't get Detailed view.");
            }
        return null;
    }


    private AppDetailedView mapToDetailedView(ResultSet rs) throws SQLException
    {
        AppDetailedView view = new AppDetailedView();
        view.setId(rs.getLong("id_member"));
        view.setFirstName(rs.getString("first_name"));
        view.setLastName(rs.getString("last_name"));
        view.setBirthday(rs.getString("birthday"));
        view.setGender(rs.getString("gender"));
        view.setCity(rs.getString("city"));
        view.setEmail(rs.getString("email"));
        return view;
    }


    // Filtered View
    public List<AppBasicView> getFilteredMembers(String last_name)
    {
        try (Connection connection = DataSourceConfig.getConnection();
             PreparedStatement prpstmt = connection.prepareStatement(
                     "SELECT m.id_member, m.first_name, m.last_name, m.birthday, m.gender FROM bds.member m " +
                             "WHERE m.last_name = ? ;"))
            {
                prpstmt.setString(1, last_name);
                return mapToAppView(prpstmt, connection);
            } catch (SQLException e)
            {
                logger.error("Failed to get Filtered DB.");
            }
        return null;
    }

    //Create View
    public void createMember(AppCreateView createView)
    {
        String insertMember =
                "INSERT INTO bds.member (first_name, last_name, birthday, gender)\n" +
                "VALUES (?,?,?,?);" ;


        try (Connection conn = DataSourceConfig.getConnection();
             PreparedStatement prpstmt = conn.prepareStatement(insertMember, Statement.RETURN_GENERATED_KEYS))
            {

                prpstmt.setString(1, createView.getFirstName());
                prpstmt.setString(2, createView.getLastName());
                prpstmt.setDate(3, createView.getBirthday());
                prpstmt.setString(4, createView.getGender());



                boolean affectedRows = prpstmt.execute();
                if (!affectedRows)
                    {
                        throw new SQLException("Creating member failed.");
                    }
            } catch (SQLException e)
            {
                logger.error("Failed to add member.");
            }
    }

    // edit member
    public void editMember(AppEditView editView)
    {
        String editSQL = " UPDATE bds.member SET first_name = ? , last_name = ?, birthday = ?, gender = ? WHERE id_member = ?;";
        String checkExistence = "SELECT last_name FROM bds.member WHERE id_member = ?";

        try (Connection conn = DataSourceConfig.getConnection();
             PreparedStatement prpstmt = conn.prepareStatement(editSQL, Statement.RETURN_GENERATED_KEYS))
            {
                prpstmt.setString(1, editView.getFirstName());
                prpstmt.setString(2, editView.getLastName());
                prpstmt.setDate(3, editView.getBirthday());
                prpstmt.setString(4, editView.getGender());
                prpstmt.setLong(5, editView.getId());

                try {
                    conn.setAutoCommit(false);
                    try (PreparedStatement ps = conn.prepareStatement(checkExistence, Statement.RETURN_GENERATED_KEYS)) {
                        ps.setLong(1,editView.getId());
                        ps.execute();
                    } catch (SQLException e) {
                        throw new SQLException("Member doesnt exist.");
                    }

                    int affectedRows = prpstmt.executeUpdate();
                    if (affectedRows == 0) {throw new SQLException("Updating member failed.");}
                    conn.commit();
                    } catch (SQLException e)
                    {
                        conn.rollback();
                    } finally
                    {
                        conn.setAutoCommit(true);
                    }
            } catch (SQLException e)
            {
                logger.error("Failed updating member.");
            }
    }

    // Remove Member
    public void removeMember(Long id)
    {
        String remove = "DELETE FROM bds.member WHERE id_member = ? ; ";
        try (Connection conn = DataSourceConfig.getConnection();
             PreparedStatement prpstmt = conn.prepareStatement(remove))
            {
                prpstmt.setLong(1, id);
                prpstmt.executeUpdate();
            } catch (SQLException e)
            {
                logger.error("Couldn't delete member.");
            }
    }

    // Injection
    public List<InjectionView> getInjectionView(String input)
    {
        String injection = "SELECT id_dummy, nickname, \"password\", email FROM bds.dummy_table WHERE id_dummy =" + input;
        try (Connection conn = DataSourceConfig.getConnection();
             Statement statement = conn.createStatement();
             ResultSet rs = statement.executeQuery(injection))
            {
                List<InjectionView> injectionViews = new ArrayList<>();
                while (rs.next())
                    {
                        injectionViews.add(mapToInjectionView(rs));
                    }
                return injectionViews;
            } catch (SQLException e)
            {
                logger.error("Failed to find users.");
            }
        return null;
    }

    private InjectionView mapToInjectionView(ResultSet rs) throws SQLException
    {
        InjectionView injectionView = new InjectionView();
        injectionView.setId(rs.getLong("id_dummy"));
        injectionView.setNickname(rs.getString("nickname"));
        injectionView.setPassword(rs.getString("password"));
        injectionView.setEmail(rs.getString("email"));
        return injectionView;
    }
}
