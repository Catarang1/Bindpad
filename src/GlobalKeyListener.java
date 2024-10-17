import java.awt.event.KeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

public class GlobalKeyListener implements NativeKeyListener {

	private static GlobalKeyListener gkl = null;

	private GlobalKeyListener() {
		super();
	}

	public static GlobalKeyListener get() {
		if (gkl==null) gkl = new GlobalKeyListener();
		return gkl;
	}

	@Override
	public void nativeKeyPressed(NativeKeyEvent newKeyboardEvent) {
		System.out.println(
				newKeyboardEvent.getKeyCode() + " > " + NativeKeyEvent.getKeyText(newKeyboardEvent.getKeyCode()) + " ↓");
		// IGNORE MODIFIERS
		if (ignoredModifiers(newKeyboardEvent))
			return;
		if (Logic.BINDING_MODE) {
			Logic.bindList.get(Logic.bindList.indexOf(Logic.toBeChanged)).setEvent(newKeyboardEvent);
			Logic.BINDING_MODE = false;
			Logic.toBeChanged = null;
		} else {
			for (Keybind keybind : Logic.bindList) {
				if (keybind.getEvent().getKeyCode() != newKeyboardEvent.getKeyCode()) continue;
				if (keybind.getEvent().getModifiers() != newKeyboardEvent.getModifiers()) continue;
				Logic.copyToClipboard(keybind.getContent());
				if (!newKeyboardEvent.isActionKey()) {
					Logic.robot.keyPress(KeyEvent.VK_BACK_SPACE);
					Logic.robot.keyRelease(KeyEvent.VK_BACK_SPACE);
				}
				Logic.pressControlV();
			}
		}
	}

	/* @Override
	public void nativeKeyReleased(NativeKeyEvent newKeyboardEvent) {
		System.out.println(
				newKeyboardEvent.getKeyCode() + " > " + NativeKeyEvent.getKeyText(newKeyboardEvent.getKeyCode()) + " ↑");
	} */

	private static boolean ignoredModifiers(NativeKeyEvent e) {
		return e.getKeyCode() == NativeKeyEvent.VC_SHIFT ||
			e.getKeyCode() == NativeKeyEvent.VC_CONTROL ||
			e.getKeyCode() == NativeKeyEvent.VC_ALT ||
			e.getKeyCode() == NativeKeyEvent.VC_META ||
			e.getKeyCode() == NativeKeyEvent.VC_CONTEXT_MENU ||
			e.getKeyCode() == NativeKeyEvent.VC_ENTER ||
			e.getKeyCode() == 3638 ||
			e.getKeyCode() == 29 ||
			e.getKeyCode() == 56;
	}
}