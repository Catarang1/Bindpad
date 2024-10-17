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
        System.setProperty("prism.lcdtext", "false");
        System.setProperty("prism.text", "t2k");
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;

        // Try to load save file, otherwise continue with default values
        try (FileInputStream fis = new FileInputStream("bind.sav");
                ObjectInputStream ois = new ObjectInputStream(fis);) {
                Logic.bindList = (ArrayList<Keybind>) ois.readObject();
        } catch (IOException ioe) {
            System.out.println("Save file not found, proceeding with default objects loaded...");
        } catch (ClassNotFoundException c) {
            System.out.println("Class not found");
            c.printStackTrace();
        }

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
                    oos.writeObject(Logic.bindList);
                    oos.close();
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Platform.exit();
                System.exit(0);
            }
        });

        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException ex) {
            System.err.println("There was a problem registering the native hook.");
            System.err.println(ex.getMessage());
            System.exit(1);
        }

        GlobalScreen.addNativeKeyListener(GlobalKeyListener.get());

        Interface.init(primaryStage);
        primaryStage.show();
    }
}