
import java.awt.Robot;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.awt.event.KeyEvent;
import java.awt.AWTException;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;

public class Logic {

	public static boolean BINDING_MODE = false;
	public static Keybind toBeChanged = null;
	public static ArrayList<Keybind> bindList = new ArrayList<>();
	public static Robot robot;
    private static Transferable transferable = null;

    static {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    public static void pressControlV() {
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);
    }

    public static void copyToClipboard(String s) {
        StringSelection selection = new StringSelection(s);
		java.awt.datatransfer.Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selection, selection);
    }

}
