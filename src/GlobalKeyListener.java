import java.util.HashSet;
import java.util.Set;

import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;


public class GlobalKeyListener implements NativeKeyListener {

	public static final Set<NativeKeyEvent> registeredKeyCombinations = new HashSet<NativeKeyEvent>();
	public static GlobalKeyListener keyListener;
	public static boolean BINDING_MODE = false;

	static {
		keyListener = new GlobalKeyListener() {
			public void nativeKeyPressed(CustomNativeKeyEvent e) {
				// IGNORE MODIFIERS
				if (isModifier(e)) return;
				// USE F2 FOR BINDING TOGGLE AND NOTHING ELSE
				if (e.getKeyCode() == NativeKeyEvent.VC_F2) {
					BINDING_MODE = !BINDING_MODE;
					System.out.println("Binding mode: " + BINDING_MODE);
					return;
				}
				if(BINDING_MODE) {
					System.out.println("Registering " + NativeKeyEvent.getModifiersText(e.getModifiers()) + "+" + NativeKeyEvent.getKeyText(e.getKeyCode()));
					registeredKeyCombinations.add(e);
				} else {
					System.out.println("Registered Key Combination: " + registeredKeyCombinations.contains(e));
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
}