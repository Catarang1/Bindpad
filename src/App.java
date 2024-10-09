import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.input.Clipboard;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class App extends Application {

    public static Stage stage;
    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        System.setProperty("prism.lcdtext", "false");
        System.setProperty("prism.text", "t2k");

        /* StringSelection selection = new StringSelection("hello");
        java.awt.datatransfer.Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.getContents(clipboard); */

        stage = primaryStage;
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
        @Override public void handle(WindowEvent t) {
            try {
                GlobalScreen.unregisterNativeHook();
            } catch (NativeHookException e) {
                e.printStackTrace();
            }
            Platform.exit();
            System.exit(0);
        }
        });

        /* HOOK TEST */

        try {
			GlobalScreen.registerNativeHook();
		}
		catch (NativeHookException ex) {
			System.err.println("There was a problem registering the native hook.");
			System.err.println(ex.getMessage());
			System.exit(1);
		}

		GlobalScreen.addNativeKeyListener(GlobalKeyListener.keyListener);

        /* HOOK TEST END */

        Interface.init(primaryStage);
        primaryStage.show();
    }
}