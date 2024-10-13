import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Interface {

    public static double ITEM_HEIGHT = 80;
    public static Insets PADDING = new Insets(10);
    public static VBox content = new VBox();

    protected static void init(Stage primaryStage) {

        content.setSpacing(10);
    
        Button addbutton = new Button("Add");
        addbutton.getStyleClass().addAll("text_color0", "item");
        addbutton.setPrefHeight(ITEM_HEIGHT);
        addbutton.setMaxWidth(Double.MAX_VALUE);
        addbutton.setOnAction(e-> GlobalKeyListener.bindList.add(new Keybind()));

        VBox wrapper = new VBox(content, addbutton);
        wrapper.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, null)));
        wrapper.setSpacing(10);
        wrapper.setPadding(PADDING);


        ScrollPane root = new ScrollPane();
        root.setContent(wrapper);
        root.setId("root");

        primaryStage.setTitle("Bindpad");
        Scene scene = new Scene(root, 400, 600);
        scene.getStylesheets().add("style.css");
        primaryStage.setScene(scene);
    }

    public static String openEditContentWindow(String initialString) {
        Stage stage = new Stage();
        TextArea contentAreaExtended = new TextArea(initialString);
        Button okButton = new Button("Submit");
        Button cancelButton = new Button("Cancel");
        GridPane root = new GridPane();
        root.setBackground(new Background(new BackgroundFill(Color.web("#12141a"),  
            CornerRadii.EMPTY, Insets.EMPTY)));
            root.getStyleClass().add("contentRoot");
        GridPane.setVgrow(contentAreaExtended, Priority.ALWAYS);
        GridPane.setHgrow(okButton, Priority.ALWAYS);
        GridPane.setHgrow(cancelButton, Priority.ALWAYS);
        okButton.setMaxWidth(Double.MAX_VALUE);
        okButton.setOnAction(e-> stage.close());
        cancelButton.setMaxWidth(Double.MAX_VALUE);
        cancelButton.setOnAction(e -> {
            contentAreaExtended.setText(initialString);
            stage.close();
        });
        root.setHgap(10);
        root.setVgap(10);
        root.setPadding(PADDING);
        root.add(contentAreaExtended, 0, 0);
        GridPane.setColumnSpan(contentAreaExtended, 2);
        root.add(okButton, 0, 1);
        root.add(cancelButton, 1, 1);

        stage.setScene(new Scene(root));
        stage.getScene().getStylesheets().add("style.css");
        stage.setTitle("Content edit");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
        return contentAreaExtended.getText();
    }
}
