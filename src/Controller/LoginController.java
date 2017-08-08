package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    public TextField username;
    public PasswordField pw;
    public Text actiontarget;
    public void btn_signin(ActionEvent actionEvent) {
        if ((username.getText() != null) && !username.getText().isEmpty()) {
            actiontarget.setText("Username: " + username.getText());
            int uid = -1;
            try {
                uid = UserFactory.checkLogin(username.getText(), pw.getText());
                if (uid == -1) {
                    actiontarget.setText("wrong username or password");
                } else {
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../UI/calendar.fxml"));
                        Parent loginParent = fxmlLoader.load();
                        CalendarController controller = fxmlLoader.getController();
                        controller.setUserId(uid);
                        Stage stage=(Stage) username.getScene().getWindow();
                        stage.setTitle("Calendar");
                        stage.setScene(new Scene(loginParent));
                        stage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception sqle) {
                sqle.printStackTrace();
            }
        } else {
            actiontarget.setText("plz put ur usrname.");
        }
    }

    public void btn_signup(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../UI/SignUp.fxml"));
            Parent signUpParent = fxmlLoader.load();
            Stage stage = (Stage) username.getScene().getWindow();
            stage.setScene(new Scene(signUpParent));
            stage.show();

        } catch (IOException e){
            e.printStackTrace();
        }


    }
}
