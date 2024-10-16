import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
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
        stage = primaryStage;

        try (FileInputStream fis = new FileInputStream("bind.sav");
                ObjectInputStream ois = new ObjectInputStream(fis);) {

            GlobalKeyListener.bindList = (ArrayList<Keybind>) ois.readObject();
        } catch (IOException ioe) {
            System.out.println("Save file not found, proceeding with default objects loaded...");
        } catch (ClassNotFoundException c) {
            System.out.println("Class not found");
            c.printStackTrace();
        }

        // Verify list data

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                try {
                    GlobalScreen.unregisterNativeHook();
                } catch (NativeHookException e) {
                    e.printStackTrace();
                }

                try (FileOutputStream fos = new FileOutputStream("bind.sav");
                        ObjectOutputStream oos = new ObjectOutputStream(fos);) {
                    oos.writeObject(GlobalKeyListener.bindList);
                    oos.close();
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Platform.exit();
                System.exit(0);
            }
        });
        /* HOOK TEST */

        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException ex) {
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

// TODO Serializable