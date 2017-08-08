package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class SignUpController {
    public TextField newUser;
    public PasswordField newPassword;

    public void btn_submit(ActionEvent actionEvent){
        // validate username and password

        try {
            int uid = UserFactory.insertUser(newUser.getText(), newPassword.getText());
            LocalDate date = LocalDate.now();
            int cid = CalendarFactory.insertCal(new ArrayList<>(), date.getYear(), date.getMonthValue());
            int gid = GroupFactory.insertGp(newUser.getText(), new ArrayList<>(), uid);
            GroupToUserDB.insertG2U(gid, uid);
            GroupToCalendarDB.insertG2C(gid, cid);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../UI/Login.fxml"));
            Parent submitParent = fxmlLoader.load();
            Stage stage=(Stage) newUser.getScene().getWindow();
            stage.setScene(new Scene(submitParent));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
