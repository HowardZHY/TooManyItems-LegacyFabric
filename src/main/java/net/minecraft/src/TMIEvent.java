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

    public TMIEvent(int p_i11_1_) {
        this.type = p_i11_1_;
    }

    public static TMIEvent clickEvent(int p_clickEvent_0_, int p_clickEvent_1_, int p_clickEvent_2_) {
        TMIEvent tmievent = new TMIEvent(0);
        tmievent.x = p_clickEvent_0_;
        tmievent.y = p_clickEvent_1_;
        tmievent.mouseButton = p_clickEvent_2_;
        return tmievent;
    }

    public static TMIEvent keypressEvent(char p_keypressEvent_0_, int p_keypressEvent_1_) {
        TMIEvent tmievent = new TMIEvent(1);
        tmievent.key = p_keypressEvent_0_;
        tmievent.keyCode = p_keypressEvent_1_;
        return tmievent;
    }

    public static TMIEvent scrollEvent(int p_scrollEvent_0_, int p_scrollEvent_1_, int p_scrollEvent_2_) {
        TMIEvent tmievent = new TMIEvent(2);
        tmievent.x = p_scrollEvent_0_;
        tmievent.y = p_scrollEvent_1_;
        tmievent.wheel = p_scrollEvent_2_;
        return tmievent;
    }

    public static TMIEvent controlEvent(int p_controlEvent_0_, TMIArea p_controlEvent_1_) {
        TMIEvent tmievent = new TMIEvent(p_controlEvent_0_);
        tmievent.target = p_controlEvent_1_;
        return tmievent;
    }

    public void cancel() {
        this.cancel = true;
    }

    public String toString() {
        return String.format((String)"TMIEvent(area=%s, type=%s, cancel=%s, x=%s, y=%s, mouseButton=%s, keyCode=%s)", (Object[])new Object[]{this.target, this.type, this.cancel, this.x, this.y, this.mouseButton, this.keyCode});
    }
}