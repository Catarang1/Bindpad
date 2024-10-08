import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;

public class Keybind {

    protected final static NativeKeyEvent DEFAULT_EVENT = new NativeKeyEvent(NativeKeyEvent.NATIVE_KEY_PRESSED, 0, 112, 59, '?', 1);
    private final static StringBuilder sb = new StringBuilder();

    private NativeKeyEvent event;
    private String content;

    public Keybind () {
        this.event = DEFAULT_EVENT;
        this.content = "Default String";
    }

    public Keybind (NativeKeyEvent nke) {
        this();
        this.event = nke;
    }

    public void setEvent(NativeKeyEvent event) {
        this.event = event;
    }

    public NativeKeyEvent getEvent() {
        return event;
    }

    public String getContent() {
        return this.content;
    }

    @Override
    public String toString() {
        String combo = createKeyCombinationString();
        sb.setLength(0);
        sb.append(this.getClass().getName());
        sb.append(" ");
        sb.append(combo);
        sb.append(" => ");
        if (content.length() > 20) {
            sb.append(content.substring(0, 18));
            sb.append("...");
        } else {
            sb.append(content);
        }
        return sb.toString();   
    }

    public String createKeyCombinationString() {
		sb.setLength(0);
		if (event.getModifiers() != 0) 
			sb.append(NativeKeyEvent.getModifiersText(event.getModifiers()) + "+");
		sb.append(NativeKeyEvent.getKeyText(event.getKeyCode()));
		return sb.toString();
	}
}
