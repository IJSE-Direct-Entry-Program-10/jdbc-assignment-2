package lk.ijse.dep10.assignment.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lk.ijse.dep10.assignment.db.DBConnection;
import lk.ijse.dep10.assignment.model.User;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginViewController {

    public Button btnLogin;
    public PasswordField txtPassword;
    public TextField txtUsername;
    
    public void btnLoginOnAction(ActionEvent event) {
        String username = txtUsername.getText();
        String password = txtPassword.getText();

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            Statement stm = connection.createStatement();
            String sql = "SELECT * FROM User WHERE username='%s' AND password='%s'";
            sql = String.format(sql, username, password);
            ResultSet rst = stm.executeQuery(sql);

            if (!rst.next()){
                txtUsername.getStyleClass().add("invalid");
                txtPassword.getStyleClass().add("invalid");
                txtUsername.requestFocus();
                txtUsername.selectAll();
            }else{
                String fullName = rst.getString("full_name");
                User.Role role = User.Role.valueOf(rst.getString("role"));
                User principal = new User(fullName, username, password, role);
                System.getProperties().put("principal", principal);
                URL mainViewUrl = getClass().getResource("/view/MainView.fxml");
                Scene mainViewScene = new Scene(FXMLLoader.load(mainViewUrl));
                Stage stage = (Stage) btnLogin.getScene().getWindow();
                stage.setScene(mainViewScene);
                stage.sizeToScene();
                stage.centerOnScreen();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
