import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;

public class Keybind {

    protected final static NativeKeyEvent DEFAULT_EVENT = new NativeKeyEvent(NativeKeyEvent.NATIVE_KEY_PRESSED, 0, 112, 59, '?', 1);
    private final static StringBuilder sb = new StringBuilder();

    private NativeKeyEvent event;
    private Button eventButton;
    private String content;
    private TextArea contentArea;
    private HBox node;

    public Keybind () {
        this.event = DEFAULT_EVENT;
        this.content = "Default String";
        node = createNode();
        Interface.content.getChildren().add(node);
    }

    public Keybind (NativeKeyEvent nke) {
        this();
        this.event = nke;
    }

    public void setEvent(NativeKeyEvent event) {
        this.event = event;
        Platform.runLater( () -> {
            this.eventButton.setText(createKeyCombinationString());
        });
    }

    public NativeKeyEvent getEvent() {
        return event;
    }

    public String getContent() {
        return this.content;
    }

    @Override
    public String toString() {
        String combo = createKeyCombinationString();
        sb.setLength(0);
        sb.append(this.getClass().getName());
        sb.append(" ");
        sb.append(combo);
        sb.append(" => ");
        if (content.length() > 20) {
            sb.append(content.substring(0, 18));
            sb.append("...");
        } else {
            sb.append(content);
        }
        return sb.toString();   
    }

    public String createKeyCombinationString() {
		sb.setLength(0);
		if (event.getModifiers() != 0) 
			sb.append(NativeKeyEvent.getModifiersText(event.getModifiers()) + "+");
		sb.append(NativeKeyEvent.getKeyText(event.getKeyCode()));
		return sb.toString();
	}

    public HBox createNode() {
        eventButton = new Button(GlobalKeyListener.createKeybindString(getEvent()));
        eventButton.getStyleClass().addAll("text_color0");
        eventButton.setPrefHeight(Interface.ITEM_HEIGHT - Interface.PADDING.getBottom() * 2);
        eventButton.getStyleClass().add("font");
        eventButton.setOnMouseClicked((e) -> {
            setEvent(Keybind.DEFAULT_EVENT);
            GlobalKeyListener.toBeChanged = this;
            GlobalKeyListener.BINDING_MODE = true;
        });

        contentArea = new TextArea();
        contentArea.setPrefColumnCount(1);
        contentArea.setText(getContent());
        contentArea.textProperty().addListener((ChangeListener<String>) (observable, oldValue, newValue) -> {
            content = newValue;
        });

        contentArea.setOnMouseClicked( e -> {
            if (e.getButton().equals(MouseButton.PRIMARY) && e.getClickCount() == 2 ) {
                System.out.println("implement open new window with detailed content.");
            }
        });

        Button deleteButton = new Button();
        SVGPath svg = new SVGPath();
        svg.setContent("M20.5001 6H3.5 M18.8332 8.5L18.3732 15.3991C18.1962 18.054 18.1077 19.3815 17.2427 20.1907C16.3777 21 15.0473 21 12.3865 21H11.6132C8.95235 21 7.62195 21 6.75694 20.1907C5.89194 19.3815 5.80344 18.054 5.62644 15.3991L5.1665 8.5 M9.1709 4C9.58273 2.83481 10.694 2 12.0002 2C13.3064 2 14.4177 2.83481 14.8295 4");
        svg.setStroke(Color.WHITE);
        svg.setFill(Color.TRANSPARENT);
        svg.setStrokeWidth(1.6);
        deleteButton.setGraphic(svg);
        deleteButton.setPrefHeight(Interface.ITEM_HEIGHT - Interface.PADDING.getBottom() * 2);
        deleteButton.getStyleClass().addAll("text_color0");
        deleteButton.setOnAction((e) -> {
            Interface.content.getChildren().remove(node);
            GlobalKeyListener.bindList.remove(this);
        });

        node = new HBox(eventButton, contentArea, deleteButton);
        node.getStyleClass().add("item");
        node.setPrefHeight(Interface.ITEM_HEIGHT);
        node.setAlignment(Pos.CENTER_LEFT);
        node.setPadding(Interface.PADDING);
        node.setSpacing(10);
        HBox.setHgrow(contentArea, Priority.SOMETIMES);
        return node;
    }
}
