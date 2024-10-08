import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

public class Interface {

    public static double ITEM_HEIGHT = 60;
    public static Insets PADDING = new Insets(10);
    public static double LABEL_FONT_SIZE = 16;
    public static ListView<Keybind> content;

    protected static void init(Stage primaryStage) {
    

        content = new ListView<Keybind>(GlobalKeyListener.bindList);
        content.setCellFactory(new Callback<ListView<Keybind>,ListCell<Keybind>>() {
            @Override
            public ListCell<Keybind> call(ListView<Keybind> param) {
                return new ListCell<Keybind>(){
                    @Override
                    public void updateItem(Keybind keybind, boolean empty) {
                        super.updateItem(keybind, empty);
                        if (empty || keybind == null) {
                            setText(null);
                            setGraphic(null);
                        } else {
                            setText(null);
                            setGraphic(createItem(keybind));
                        }
                    }
                };
            
            }
        });
    


        Button addbutton = new Button("Add");
        addbutton.getStyleClass().addAll("text_color0", "item");
        addbutton.setPrefHeight(ITEM_HEIGHT);
        addbutton.setMaxWidth(Double.MAX_VALUE);
        addbutton.setOnAction(e-> {
            GlobalKeyListener.bindList.add(new Keybind());
        });

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

    protected static HBox createItem(Keybind k) {
        Button bindButton = new Button(GlobalKeyListener.createKeybindString(k.getEvent()));
        bindButton.getStyleClass().addAll("text_color0");
        bindButton.setPrefHeight(ITEM_HEIGHT - PADDING.getBottom() * 2);
        bindButton.getStyleClass().add("font");
        bindButton.setOnMouseClicked((e) -> {
            k.setEvent(Keybind.DEFAULT_EVENT);
            GlobalKeyListener.toBeChanged = k;
            GlobalKeyListener.BINDING_MODE = true;
            System.out.println(k.equals(GlobalKeyListener.toBeChanged));
        });

        TextArea contentTextArea = new TextArea();
        contentTextArea.setPrefColumnCount(1);
        contentTextArea.setText(k.getContent());

        Button deleteButton = new Button("delete");
        deleteButton.setPrefHeight(ITEM_HEIGHT - PADDING.getBottom() * 2);
        deleteButton.getStyleClass().addAll("text_color0");
        deleteButton.setOnAction((e) -> GlobalKeyListener.bindList.remove(k));

        HBox item = new HBox(bindButton, contentTextArea, deleteButton);
        item.getStyleClass().add("item");
        item.setPrefHeight(Interface.ITEM_HEIGHT);
        item.setAlignment(Pos.CENTER_LEFT);
        item.setPadding(PADDING);
        item.setSpacing(10);
        HBox.setHgrow(contentTextArea, Priority.SOMETIMES);
        return item;
    }

    public static void openBindWindow() {
        Platform.runLater(() -> {
            Stage stage = new Stage();
            Text t = new Text("modal");
            Parent root = new VBox(t);
            stage.setScene(new Scene(root));
            stage.setTitle("My modal window");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(App.stage);
            stage.show();
        });

    }

}
