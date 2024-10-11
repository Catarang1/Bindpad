import java.util.ArrayList;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;

import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

public class GlobalKeyListener implements NativeKeyListener {

	public static GlobalKeyListener keyListener;
	public static boolean BINDING_MODE = false;
	public static StringBuilder sb = new StringBuilder();
	public static Keybind toBeChanged = null;
	public static ArrayList<Keybind> bindList = new ArrayList<>();
	public static Robot robot;

	static {

		try {
			robot = new Robot();
		} catch (AWTException e) {e.printStackTrace();}

		bindList.add(new Keybind());
		
		keyListener = new GlobalKeyListener() {
			public void nativeKeyPressed(NativeKeyEvent newKeyboardEvent) {
				// IGNORE MODIFIERS
				if (isModifier(newKeyboardEvent) || newKeyboardEvent.getKeyCode() == NativeKeyEvent.VC_ENTER) return;
				if(BINDING_MODE) {
					bindList.get(bindList.indexOf(toBeChanged)).setEvent(newKeyboardEvent);
					BINDING_MODE = false;
					toBeChanged = null;
				} else {
					for (Keybind keybind : GlobalKeyListener.bindList) {
						if (keybind.getEvent().getKeyCode() != newKeyboardEvent.getKeyCode()) continue;
						if (keybind.getEvent().getModifiers() != newKeyboardEvent.getModifiers()) continue;
						System.out.println(keybind.getContent());
						StringSelection selection = new StringSelection(keybind.getContent());
    					java.awt.datatransfer.Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    					clipboard.setContents(selection, selection);
						robot.keyPress(KeyEvent.VK_CONTROL);
						robot.keyPress(KeyEvent.VK_V);
						robot.keyRelease(KeyEvent.VK_V);
						robot.keyRelease(KeyEvent.VK_CONTROL);
						// https://stackoverflow.com/questions/20343716/my-custom-paste-from-clipboard-action
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