import java.util.HashMap;
import java.util.Map;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class GlobalKeyListener implements NativeKeyListener {

	public static GlobalKeyListener keyListener;
	public static boolean BINDING_MODE = false;
	public static StringBuilder sb = new StringBuilder();
	public static ObservableList<Keybind> bindList = FXCollections.synchronizedObservableList(FXCollections.observableArrayList());

	static {

		bindList.add(new Keybind());

		keyListener = new GlobalKeyListener() {
			public void nativeKeyPressed(NativeKeyEvent newKeyboardEvent) {
				// IGNORE MODIFIERS
				if (isModifier(newKeyboardEvent) || newKeyboardEvent.getKeyCode() == NativeKeyEvent.VC_ENTER) return;
				// USE F2 FOR BINDING TOGGLE AND NOTHING ELSE
				if (newKeyboardEvent.getKeyCode() == NativeKeyEvent.VC_F2 ) {
					BINDING_MODE = !BINDING_MODE;
					System.out.println("Binding mode: " + BINDING_MODE);
					return;
				}
				if(BINDING_MODE) {
					System.out.println("Registering " + createKeybindString(newKeyboardEvent));
					Platform.runLater(() -> bindList.add(new Keybind(newKeyboardEvent)));
					

				} else {
					for (Keybind nativeKeyEvent : GlobalKeyListener.bindList) {
						if (nativeKeyEvent.getEvent().getKeyCode() != newKeyboardEvent.getKeyCode()) continue;
						if (nativeKeyEvent.getEvent().getModifiers() != newKeyboardEvent.getModifiers()) continue;
						System.out.println(createKeybindString(newKeyboardEvent) + " in the List.");
					}
				}
			}	
		};
	}

	private static boolean isModifier(NativeKeyEvent e) {
		return 
			e.getKeyCode() == NativeKeyEvent.VC_SHIFT ||
			e.getKeyCode() == NativeKeyEvent.VC_CONTROL ||
			e.getKeyCode() == NativeKeyEvent.VC_ALT ||
			e.getKeyCode() == NativeKeyEvent.VC_META;
	}

	private static String createKeybindString(NativeKeyEvent nke) {
		sb.setLength(0);
		if (nke.getModifiers() != 0) 
			sb.append(NativeKeyEvent.getModifiersText(nke.getModifiers()) + "+");
		sb.append(NativeKeyEvent.getKeyText(nke.getKeyCode()));
		return sb.toString();
	}
}