package Controller;

import Entity.Calendar;
import Entity.Event;
import Entity.Group;
import Entity.User;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.*;

public class CalendarController {

    private final static double tileSize = 60.0;
    private final static String[] days = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
    public BorderPane calendarPane;
    public Button calendarBack;
    public Button calendarNext;
    public ComboBox<String> calendarCombo;
    public Label testchange;
    public Button createGroupButton;

    private LocalDate anchorDate;
    private int calendarID;
    private int groupID;
    private int userID;

    public TilePane calendarTile;
    public AnchorPane calendarAnchor;
    public Label monthYear;

    private Map<String, Integer> groupMap = new HashMap<>();

    private void setGroupID(int gid) { groupID = gid;}
    void setUserId(int id) {
        userID = id;
        User cur_user;
        try {
            cur_user = UserFactory.getUserById(userID);

            // get user's groups
            List<Integer> gids = GroupToUserDB.getGroupsByUserId(id);
            List<String> groupnames = new ArrayList<>();
            for (int gid : gids) {
                Group gp = GroupFactory.searchGroup(gid);
                groupnames.add(gp.getGroupName());
                groupMap.put(gp.getGroupName(), gid);
            }
            calendarCombo.getItems().addAll(groupnames);
            calendarCombo.getSelectionModel().select(cur_user.getUserName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        anchorDate = LocalDate.now();
        calendarTile.setMinSize(tileSize * 7, tileSize * 6 + 10);
        calendarCombo.getSelectionModel().selectedItemProperty().addListener((selected, oldGroup, newGroup) -> {
            if (newGroup != null) {
                setGroupID(groupMap.get(newGroup));
                refreshCalendar();
            }
        });
        refreshCalendar();
    }

    public void initialize() {

    }

    public void goToLastMonth(ActionEvent actionEvent) {
        int year = anchorDate.getYear();
        int month = anchorDate.getMonthValue() - 1;
        if (month == 0) {
            year--;
            month = 12;
        }
        anchorDate = LocalDate.of(year, month, 1);
        refreshCalendar();
    }

    public void goToNextMonth(ActionEvent actionEvent) {
        int year = anchorDate.getYear();
        int month = anchorDate.getMonthValue() + 1;
        if (month == 13) {
            year++;
            month = 1;
        }
        anchorDate = LocalDate.of(year, month, 1);
        refreshCalendar();
    }

    private void refreshCalendar() {
        calendarTile.getChildren().clear();
        int firstDayOfWeek = (anchorDate.getDayOfWeek().getValue() + 7 - anchorDate.getDayOfMonth() % 7 + 1) % 7;
        Month month = anchorDate.getMonth();
        monthYear.setText(month.getDisplayName(TextStyle.FULL, Locale.US) + ' ' + anchorDate.getYear());

        for (String day : days) {
            VBox box = new VBox();
            box.setPrefSize(tileSize, 10.0);
            box.getChildren().add(new Label(day));
            calendarTile.getChildren().add(box);
        }

        // populating empty tiles before the first day of the month
        for (int i = 0; i < firstDayOfWeek; i++) {
            VBox box = new VBox();
            box.setPrefSize(tileSize, tileSize);
            calendarTile.getChildren().add(box);
        }

        Calendar cal = CalendarFactory.generateTestingCalendar(calendarID);

        for (int i = 1; i <= month.length(anchorDate.getYear() % 4 == 0); i++) {
            VBox box = new VBox();
            box.setPrefSize(tileSize, tileSize);
            box.getChildren().add(new Label(String.valueOf(i)));
            List<Event> eveList = cal.getEventListByDay(i);
            if (eveList != null) {
                for (Event e : eveList) {
                    box.getChildren().add(new Label(e.getDescription()));
                }
                box.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        VBox vb = (VBox) event.getSource();
                        try {
                            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../UI/dayCell.fxml"));
                            Parent calendarParent = fxmlLoader.load();
                            DayCellController controller = fxmlLoader.getController();
                            controller.setDate(String.format("%d%c%d%c%s%n", anchorDate.getYear(), '-',
                                    anchorDate.getMonthValue(), '-',
                                    ((Label) vb.getChildren().get(0)).getText()));
                            controller.setEvents(eveList);
                            Stage stage = new Stage();
                            stage.initModality(Modality.APPLICATION_MODAL);
                            stage.initStyle(StageStyle.UNDECORATED);
                            stage.setTitle("DayCell");
                            stage.setScene(new Scene(calendarParent));
                            stage.show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
            calendarTile.getChildren().add(box);
        }
    }

    public void createGroup(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../UI/CreateGroup.fxml"));
            Parent calendarParent = fxmlLoader.load();
            CreateGroupController controller = fxmlLoader.getController();
            controller.setUserId(userID);
            Stage stage = new Stage();

            stage.setTitle("Create a new group");
            stage.setScene(new Scene(calendarParent));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
