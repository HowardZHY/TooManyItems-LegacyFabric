package net.minecraft.src;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class TMIArea
implements TMIListener {
    protected int x;
    protected int y;
    protected int z;
    protected int width;
    protected int height;
    protected boolean shown = true;
    public List<TMIArea> children = new ArrayList();
    public List<TMIListener> listeners = new ArrayList();
    protected TMIArea parent = null;
    protected TMIArea focusArea = null;
    protected TMIArea mouseoverArea = null;
    public List<String> tooltip;

    public void setSize(int n, int n2) {
        this.width = n;
        this.height = n2;
    }

    public void setPosition(int n, int n2) {
        this.x = n;
        this.y = n2;
    }

    public void setPositionRelative(TMIArea tMIArea, int n, int n2) {
        this.x = tMIArea.getX() + n;
        this.y = tMIArea.getY() + n2;
    }

    public void setPositionCenteredIn(int n, int n2, int n3, int n4) {
        this.x = n + (n3 - this.getWidth()) / 2;
        this.y = n2 + (n4 - this.getHeight()) / 2;
    }

    public void setYCenteredIn(int n, TMIArea tMIArea) {
        this.y = n;
        this.x = tMIArea.x + (tMIArea.width - this.width) / 2;
    }

    public void copyBounds(TMIArea tMIArea) {
        this.x = tMIArea.getX();
        this.y = tMIArea.getY();
        this.width = tMIArea.getWidth();
        this.height = tMIArea.getHeight();
    }

    public void addChild(TMIArea tMIArea) {
        if (!this.children.contains(tMIArea)) {
            this.children.add(tMIArea);
            if (tMIArea.parent != null) {
                tMIArea.parent.removeChild(tMIArea);
            }
            tMIArea.parent = this;
        }
    }

    public boolean contains(int n, int n2) {
        return n >= this.x && n2 >= this.y && n <= this.x + this.width && n2 <= this.y + this.height;
    }

    public void removeChild(TMIArea tMIArea) {
        this.children.remove((Object)tMIArea);
        tMIArea.parent = null;
    }

    public void removeChildrenOfType(Class clazz) {
        Iterator iterator = this.children.iterator();
        while (iterator.hasNext()) {
            TMIArea tMIArea = (TMIArea)iterator.next();
            if (!clazz.isInstance((Object)tMIArea)) continue;
            tMIArea.parent = null;
            iterator.remove();
        }
    }

    public List<TMIArea> children() {
        return this.children;
    }

    public boolean hasChildren() {
        return this.children.size() > 0;
    }

    public TMIArea getParent() {
        return this.parent;
    }

    public boolean isFocused() {
        return this.focusArea == this;
    }

    public TMIArea getFocused() {
        return this.focusArea;
    }

    public void focus() {
        this.focus(this);
    }

    public void blur() {
        this.blur(this);
    }

    public void blurFocused() {
        if (this.focusArea != null) {
            this.focusArea.blur();
        }
    }

    protected void focus(TMIArea tMIArea) {
        this.blurFocused();
        this.focusArea = tMIArea;
        if (this.parent != null) {
            this.parent.focus(tMIArea);
        }
    }

    protected void blur(TMIArea tMIArea) {
        if (this.focusArea == tMIArea) {
            this.focusArea = null;
            if (this.parent != null) {
                this.parent.blur(tMIArea);
            }
        }
    }

    protected void mouseover() {
        this.mouseover(this);
    }

    protected void mouseover(TMIArea tMIArea) {
        this.mouseoverArea = tMIArea;
        if (this.parent != null) {
            this.parent.mouseover(tMIArea);
        }
    }

    public boolean isMouseover() {
        TMIArea tMIArea = this;
        while (tMIArea.parent != null) {
            tMIArea = tMIArea.parent;
        }
        return tMIArea.mouseoverArea == this;
    }

    public TMIArea getMouseoverArea() {
        return this.mouseoverArea;
    }

    public void setTooltip(String string) {
        this.tooltip = new ArrayList(Arrays.asList((Object[])string.split("\n")));
    }

    public void setTooltip(List<String> list) {
        this.tooltip = list;
    }

    public void setTooltipLine(int n, String string) {
        this.tooltip.set(n, string);
    }

    public void clearTooltip() {
        this.tooltip = null;
    }

    public void addTooltipLine(String string) {
        if (this.tooltip == null) {
            this.tooltip = new ArrayList();
        }
        this.tooltip.add(string);
    }

    public List<String> getTooltip() {
        return this.tooltip;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getZ() {
        return this.z;
    }

    public void setZ(int n) {
        this.z = n;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public boolean visible() {
        return this.shown;
    }

    public void show(boolean bl) {
        this.shown = bl;
    }

    public void show() {
        this.shown = true;
    }

    public void hide() {
        this.shown = false;
    }

    public void addEventListener(TMIListener tMIListener) {
        if (!this.listeners.contains(tMIListener)) {
            this.listeners.add(tMIListener);
        }
    }

    public int centerX() {
        return this.x + this.width / 2;
    }

    public int centerY() {
        return this.y + this.height / 2;
    }

    public static int center(int n, int n2, int n3) {
        return n + (n2 - n3) / 2;
    }

    public void emit(TMIEvent tMIEvent) {
        for (TMIListener tMIListener : this.listeners) {
            if (tMIEvent.type == 0) {
                tMIListener.mouseEvent(tMIEvent);
                continue;
            }
            if (tMIEvent.type == 1) {
                tMIListener.keyboardEvent(tMIEvent);
                continue;
            }
            tMIListener.controlEvent(tMIEvent);
        }
    }

    public void draw(int n, int n2) {
        this.drawComponent(n, n2);
        for (TMIArea tMIArea : this.children) {
            if (!tMIArea.visible()) continue;
            tMIArea.draw(n, n2);
        }
    }

    public void doLayout() {
        this.layoutComponent();
        for (TMIArea tMIArea : this.children) {
            tMIArea.doLayout();
        }
    }

    public void drawComponent(int n, int n2) {
        this.drawComponent();
    }

    public void drawComponent() {
    }

    public void layoutComponent() {
    }

    @Override
    public void mouseEvent(TMIEvent tMIEvent) {
    }

    @Override
    public void keyboardEvent(TMIEvent tMIEvent) {
    }

    @Override
    public void controlEvent(TMIEvent tMIEvent) {
    }
}