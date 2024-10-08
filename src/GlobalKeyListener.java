import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class GlobalKeyListener implements NativeKeyListener {

	public static GlobalKeyListener keyListener;
	public static boolean BINDING_MODE = false;
	public static StringBuilder sb = new StringBuilder();
	public static Keybind toBeChanged = null;
	public static ObservableList<Keybind> bindList = FXCollections.synchronizedObservableList(FXCollections.observableArrayList());

	static {

		bindList.add(new Keybind());
		
		keyListener = new GlobalKeyListener() {
			public void nativeKeyPressed(NativeKeyEvent newKeyboardEvent) {
				// IGNORE MODIFIERS
				if (isModifier(newKeyboardEvent) || newKeyboardEvent.getKeyCode() == NativeKeyEvent.VC_ENTER) return;
				if(BINDING_MODE) {
					System.out.println("key pressed in binding mode");
					bindList.get(bindList.indexOf(toBeChanged)).setEvent(newKeyboardEvent);
					Interface.content.refresh();
					BINDING_MODE = false;
					toBeChanged = null;
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

	public static String createKeybindString(NativeKeyEvent nke) {
		sb.setLength(0);
		if (nke.getModifiers() != 0) 
			sb.append(NativeKeyEvent.getModifiersText(nke.getModifiers()) + "+");
		sb.append(NativeKeyEvent.getKeyText(nke.getKeyCode()));
		return sb.toString();
	}
}