import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;

public class CustomNativeKeyEvent extends NativeKeyEvent {

    public CustomNativeKeyEvent(int arg0, int arg1, int arg2, int arg3, char arg4, int arg5) {
        super(arg0, arg1, arg2, arg3, arg4, arg5);
    }

    public CustomNativeKeyEvent(int arg0, int arg1, int arg2, int arg3, char arg4) {
        super(arg0, arg1, arg2, arg3, arg4);
    }

    @Override
    public int hashCode() {
        return super.getKeyCode() * super.getModifiers();
    } 

}
