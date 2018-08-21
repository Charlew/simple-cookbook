package Login;

import DbConnection.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDAO {
    Connection connection;
    private static String username;
    private static Integer userId;

    public void setUsername(String username){
        this.username = username;
    }
    public String getUsername(){
        return this.username;
    }

    public void setUserId(Integer userId){
        this.userId = userId;
    }
    public Integer getUserId(){
        return this.userId;
    }


    protected boolean isLogin(String username, String password) throws SQLException {
        boolean status = false;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement("SELECT id_user, username, password FROM users WHERE username = ? AND password = ? ");
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                setUsername(username);
                setUserId(resultSet.getInt("id_user"));
                System.out.println("User authenticated successfully " + getUsername() + " ID " + getUserId());
                status = true;
            } else {
                System.out.println("Error ");
                status = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            preparedStatement.close();
            resultSet.close();
        }
        return status;
    }
}