import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Interface {

    public static double ITEM_HEIGHT = 60;
    public static Insets PADDING = new Insets(10);
    public static double LABEL_FONT_SIZE = 16;
    public static VBox content = new VBox();

    protected static void init(Stage primaryStage) {

        content.setSpacing(10);
    
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

    public static void openBindWindow() {
        Platform.runLater(() -> {
            Stage stage = new Stage();
            Text t = new Text("modal");
            Parent root = new VBox(t);
            stage.setScene(new Scene(root));
            stage.setTitle("My modal window");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.show();
        });

    }

    public static void openEditContentWindow() {
        Platform.runLater(() -> {
            TextArea contentAreaExtended = new TextArea();
            Button okButton = new Button();
            Button cancelButton = new Button();
            Parent root = new GridPane();
            root.getChildrenUnmodifiable().addAll(okButton, cancelButton, contentAreaExtended);
            GridPane.setColumnSpan(contentAreaExtended, 2);
        
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("My modal window");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.show();
            
        });
        ;
    }

}
