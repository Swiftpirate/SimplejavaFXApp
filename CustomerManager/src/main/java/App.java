import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class App extends Application {

    private final ObservableList<Customer> customers = FXCollections.observableArrayList();

    @Override
    public void start(Stage stage) {
        // ----- Form -----
        TextField nameField = new TextField();
        nameField.setPromptText("Customer name");

        ComboBox<String> provinceBox = new ComboBox<>();
        provinceBox.getItems().addAll(
                "Central", "Copperbelt", "Eastern", "Luapula", "Lusaka",
                "Muchinga", "Northern", "North-Western", "Southern", "Western");
        provinceBox.setPromptText("Select province");

        GridPane form = new GridPane();
        form.setPadding(new Insets(15));
        form.setHgap(10);
        form.setVgap(10);
        form.addRow(0, new Label("Name:"), nameField);
        form.addRow(1, new Label("Province:"), provinceBox);

        // ----- Table -----
        TableView<Customer> table = new TableView<>(customers);

        TableColumn<Customer, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Customer, String> provinceCol = new TableColumn<>("Province");
        provinceCol.setCellValueFactory(new PropertyValueFactory<>("province"));

        table.getColumns().addAll(nameCol, provinceCol);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);

        // ----- Add button (validation) -----
        Button addButton = new Button("Add");
        addButton.setDefaultButton(true);

        addButton.setOnAction(e -> {
            String name = nameField.getText().trim();
            String province = provinceBox.getValue();

            if (name.isEmpty()) {
                showError("Please enter a name.");
                nameField.requestFocus();
                return;
            }
            if (province == null) {
                showError("Please select a province.");
                provinceBox.requestFocus();
                return;
            }

            customers.add(new Customer(name, province));
            nameField.clear();
            provinceBox.setValue(null);
            nameField.requestFocus();
        });

        form.add(addButton, 1, 2);

        // ----- Delete button (confirmation) -----
        Button deleteButton = new Button("Delete Selected");

        deleteButton.setOnAction(e -> {
            Customer selected = table.getSelectionModel().getSelectedItem();

            if (selected == null) {
                showError("Please select a customer to delete.");
                return;
            }

            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                    "Delete " + selected.getName() + "?",
                    ButtonType.YES, ButtonType.NO);
            confirm.setHeaderText(null);

            if (confirm.showAndWait().orElse(ButtonType.NO) == ButtonType.YES) {
                customers.remove(selected);
            }
        });

        // ----- Layout -----
        VBox root = new VBox(10, form, table, deleteButton);
        root.setPadding(new Insets(10));
        VBox.setVgrow(table, Priority.ALWAYS);

        // ----- Colours -----
        root.setStyle("-fx-background-color: linear-gradient(to bottom right, #1e3c72, #2a5298);");

        for (var node : form.getChildren()) {
            if (node instanceof Label label) {
                label.setStyle("-fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold;");
            }
        }

        String baseStyle = "-fx-font-weight: bold; -fx-background-radius: 20; -fx-padding: 6 18 6 18;";
        addButton.setStyle("-fx-background-color: #f9a825; -fx-text-fill: #1e1e1e; " + baseStyle);
        deleteButton.setStyle("-fx-background-color: #e53935; -fx-text-fill: white; " + baseStyle);

        table.setStyle("-fx-background-radius: 8; -fx-border-radius: 8;");

        // ----- Animation -----
        addHoverEffect(addButton);
        addHoverEffect(deleteButton);

        stage.setScene(new Scene(root, 420, 450));
        stage.setTitle("Customer Form");
        stage.show();

        FadeTransition fade = new FadeTransition(Duration.millis(900), root);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.play();
    }

    private void addHoverEffect(Button button) {
        ScaleTransition grow = new ScaleTransition(Duration.millis(150), button);
        grow.setToX(1.1);
        grow.setToY(1.1);

        ScaleTransition shrink = new ScaleTransition(Duration.millis(150), button);
        shrink.setToX(1.0);
        shrink.setToY(1.0);

        button.setOnMouseEntered(e -> {
            shrink.stop();
            grow.playFromStart();
        });
        button.setOnMouseExited(e -> {
            grow.stop();
            shrink.playFromStart();
        });
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING, message);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}