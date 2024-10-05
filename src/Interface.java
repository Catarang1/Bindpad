import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Interface {
    
    public static double ITEM_HEIGHT = 60;
    public static Insets PADDING = new Insets(10);
    public static double LABEL_FONT_SIZE = 16;

    protected static void init(Stage primaryStage) {
    

        VBox content = new VBox();
        content.setSpacing(10);
        content.getChildren().add(Interface.createItem());
        content.getChildren().add(Interface.createItem());
        content.getChildren().add(Interface.createItem());
        content.getChildren().add(Interface.createItem());

        Button addbutton = new Button("Add");
        addbutton.getStyleClass().addAll("text_color0", "item");
        addbutton.setPrefHeight(ITEM_HEIGHT);
        addbutton.setMaxWidth(Double.MAX_VALUE);

        VBox wrapper = new VBox(content, addbutton);
        wrapper.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, null)));
        wrapper.setSpacing(10);
        wrapper.setPadding(PADDING);


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
        // System.out.println(bindFont.getName());
    }

    protected static HBox createItem(){
        Button bindButton = new Button("UNDEFINED");
        bindButton.getStyleClass().addAll("text_color0");
        bindButton.setPrefHeight(ITEM_HEIGHT - PADDING.getBottom() * 2);
        bindButton.getStyleClass().add("font");

        TextArea contentTextArea = new TextArea();
        contentTextArea.setPrefColumnCount(1);

        Button deleteButton = new Button("delete");
        deleteButton.setPrefHeight(ITEM_HEIGHT - PADDING.getBottom() * 2);
        deleteButton.getStyleClass().addAll("text_color0");

        HBox item = new HBox(bindButton, contentTextArea, deleteButton );
        item.getStyleClass().add("item");
        item.setPrefHeight(Interface.ITEM_HEIGHT);
        item.setAlignment(Pos.CENTER_LEFT);
        item.setPadding(PADDING);
        item.setSpacing(10);
        HBox.setHgrow(contentTextArea, Priority.SOMETIMES);
        return item;
    }

    protected static HBox createItem(String key, String content){
        HBox item = createItem();
        //add key and content as visible elements
        return item;
    }

}
