import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;

import java.io.Serializable;
import javafx.animation.StrokeTransition;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class Keybind implements Serializable {

    protected final static NativeKeyEvent DEFAULT_EVENT = new NativeKeyEvent(NativeKeyEvent.NATIVE_KEY_PRESSED, 0, 112, 59, '?', 1);
    private final static StrokeTransition st = new StrokeTransition(Duration.millis(200), null, Color.AQUAMARINE, Color.BLUE);
    private final static StringBuilder sb = new StringBuilder();

    private static final long serialVersionUID = 1L;
    private NativeKeyEvent event;
    private String content;
    
    private KeybindNode node;

    static {
        st.setAutoReverse(true);
    }

    public Keybind () {
        this.event = DEFAULT_EVENT;
        this.content = "Default String";
        node = new KeybindNode();
        Interface.content.getChildren().add(node);
    }

    public void setEvent(NativeKeyEvent event) {
        this.event = event;
        Platform.runLater( () -> {
            node.eventButton.setText(createKeyCombinationString());
            node.eventButton.setStyle("-fx-border-color: black");
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

    private class KeybindNode extends HBox {
        
        protected Button eventButton;
        protected TextArea contentArea;

        private KeybindNode () {
            {
                eventButton = new Button(GlobalKeyListener.createKeybindString(getEvent()));
                eventButton.setBorder(new Border(new BorderStroke(Color.BLACK, 
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                eventButton.setPrefHeight(Interface.ITEM_HEIGHT - Interface.PADDING.getBottom() * 2);
                eventButton.setOnMouseClicked((e) -> {
                    eventButton.setStyle("-fx-border-color: #ab9df2");
                    eventButton.setText("bind");
                    GlobalKeyListener.toBeChanged = Keybind.this;
                    GlobalKeyListener.BINDING_MODE = true;
                });
        
                contentArea = new TextArea();
                contentArea.setPrefColumnCount(1);
                contentArea.setText(getContent());
                contentArea.textProperty().addListener((ChangeListener<String>) (observable, oldValue, newValue) -> content = newValue);
                contentArea.setOnMouseClicked( e -> {
                    if (e.getButton().equals(MouseButton.PRIMARY) && e.getClickCount() == 2 ) {
                        String extendedAreaContent = Interface.openEditContentWindow(content);
                        content = extendedAreaContent;
                        contentArea.setText(extendedAreaContent);
                    }
                });
        
                Button deleteButton = new Button();
                SVGPath svg = new SVGPath();
                svg.setContent("M20.5001 6H3.5 M18.8332 8.5L18.3732 15.3991C18.1962 18.054 18.1077 19.3815 17.2427 20.1907C16.3777 21 15.0473 21 12.3865 21H11.6132C8.95235 21 7.62195 21 6.75694 20.1907C5.89194 19.3815 5.80344 18.054 5.62644 15.3991L5.1665 8.5 M9.1709 4C9.58273 2.83481 10.694 2 12.0002 2C13.3064 2 14.4177 2.83481 14.8295 4");
                svg.setStroke(Color.web("#ab9df2"));
                svg.setFill(Color.TRANSPARENT);
                svg.setStrokeWidth(1.6);
                deleteButton.setGraphic(svg);
                deleteButton.setPadding(Interface.PADDING);
                deleteButton.setPrefHeight(Interface.ITEM_HEIGHT - Interface.PADDING.getBottom() * 2);
                deleteButton.getStyleClass().addAll("text_color0");
                deleteButton.setOnAction((e) -> {
                    Interface.content.getChildren().remove(this);
                    GlobalKeyListener.bindList.remove(Keybind.this);
                });
        
                this.getChildren().addAll(eventButton, contentArea, deleteButton);
                this.getStyleClass().add("item");
                this.setPrefHeight(Interface.ITEM_HEIGHT);
                this.setAlignment(Pos.CENTER_LEFT);
                this.setPadding(Interface.PADDING);
                this.setSpacing(10);
                HBox.setHgrow(contentArea, Priority.SOMETIMES);
            }
        }

    }
}
