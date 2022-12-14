package net.minecraft.src;

public class TMIEvent {
    public TMIArea target;
    public boolean cancel = false;
    public int type;
    public int x = -1;
    public int y = -1;
    public int mouseButton = -1;
    public char key;
    public int keyCode = -1;
    public int wheel = -1;
    public static final int CLICK = 0;
    public static final int KEY = 1;
    public static final int SCROLL = 2;
    public static final int CHANGE = 3;
    public static final int SELECT = 4;

    public TMIEvent(int n) {
        this.type = n;
    }

    public static TMIEvent clickEvent(int n, int n2, int n3) {
        TMIEvent tMIEvent = new TMIEvent(0);
        tMIEvent.x = n;
        tMIEvent.y = n2;
        tMIEvent.mouseButton = n3;
        return tMIEvent;
    }

    public static TMIEvent keypressEvent(char c, int n) {
        TMIEvent tMIEvent = new TMIEvent(1);
        tMIEvent.key = c;
        tMIEvent.keyCode = n;
        return tMIEvent;
    }

    public static TMIEvent scrollEvent(int n, int n2, int n3) {
        TMIEvent tMIEvent = new TMIEvent(2);
        tMIEvent.x = n;
        tMIEvent.y = n2;
        tMIEvent.wheel = n3;
        return tMIEvent;
    }

    public static TMIEvent controlEvent(int n, TMIArea tMIArea) {
        TMIEvent tMIEvent = new TMIEvent(n);
        tMIEvent.target = tMIArea;
        return tMIEvent;
    }

    public void cancel() {
        this.cancel = true;
    }

    public String toString() {
        return String.format((String)"TMIEvent(area=%s, type=%s, cancel=%s, x=%s, y=%s, mouseButton=%s, keyCode=%s)", (Object[])new Object[]{this.target, this.type, this.cancel, this.x, this.y, this.mouseButton, this.keyCode});
    }
}