import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Interface {
    
    public static double ITEM_HEIGHT = 60;
    public static double LABEL_FONT_SIZE = 16;


    protected static void init(Stage primaryStage) {
      
        VBox content = new VBox();
        content.setSpacing(10);
        content.getChildren().add(Interface.createItem());
        content.getChildren().add(Interface.createItem());
        content.getChildren().add(Interface.createItem());
        content.getChildren().add(Interface.createItem());
        content.getChildren().add(Interface.createItem());

        Label l = new Label("Add");
        BorderPane addbutton = new BorderPane();
        addbutton.setCenter(l);
        addbutton.getStyleClass().add("item");
        addbutton.setPrefHeight(ITEM_HEIGHT);

        VBox wrapper = new VBox(content, addbutton);
        wrapper.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, null)));
        wrapper.setSpacing(10);
        wrapper.setPadding(new Insets(10));


        ScrollPane root = new ScrollPane();
        root.setContent(wrapper);
        root.setStyle("-fx-background: #12141a; -fx-background-color: #12141a");
        root.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        root.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        root.setFitToWidth(true);

        primaryStage.setTitle("Bindpad");
        Scene scene = new Scene(root, 400, 600);
        scene.getStylesheets().add("style.css");
        primaryStage.setScene(scene);
    }

    protected static HBox createItem(){
        Label key = new Label("UNDEFINED");
        key.setStyle("-fx-font-size: " + LABEL_FONT_SIZE + "px");

        BorderPane keyLabel = new BorderPane();
        keyLabel.setStyle("-fx-border-color: black; -fx-background-radius: 6px; -fx-border-radius: 6px; -fx-background-color: 6px; -fx-background-color: #12141a");
        keyLabel.setPrefHeight(ITEM_HEIGHT);
        keyLabel.setCenter(key);
        keyLabel.setPadding(new Insets(10));

        TextArea contentTextArea = new TextArea();
        contentTextArea.setPrefColumnCount(1);
        contentTextArea.setStyle("text-area-background: blue ;");
        Button deleteButton = new Button("delete");
        HBox item = new HBox(keyLabel, contentTextArea, deleteButton );
        item.getStyleClass().add("item");
        item.setPrefHeight(Interface.ITEM_HEIGHT);
        item.setAlignment(Pos.CENTER_LEFT);
        item.setPadding(new Insets(10));
        item.setSpacing(10);
        item.setHgrow(keyLabel, Priority.NEVER);
        item.setHgrow(contentTextArea, Priority.SOMETIMES);
        deleteButton.setPrefHeight(ITEM_HEIGHT - 10);
        return item;
    }

    protected static HBox createItem(String key, String content){
        HBox item = createItem();
        //add key and content as visible elements
        return item;
    }

}
