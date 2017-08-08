package Controller;

import Entity.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class DayCellController {
    @FXML
    public Label date;
    public Button backButton;
    public VBox eventVBox;
    private List<Event> eveList;

    void setDate(String d) {
        date.setText(d);
    }
    void setEvents(List<Event> eventList) {
        eveList = eventList;
        for (Event e : eventList) {
            eventVBox.getChildren().add(new Label(e.getDescription()));
        }
    }

    @FXML
    private void closeWindow() {
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.close();
    }
}
