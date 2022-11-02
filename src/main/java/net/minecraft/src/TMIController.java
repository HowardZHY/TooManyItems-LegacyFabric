package net.minecraft.src;

import java.util.List;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.screen.ScreenHandler;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class TMIController {
    public static final String COPYRIGHT = "Copyright 2011-2014 Marglyph. Free for personal or educational use only. Do not redistribute TooManyItems, including in mod packs, and do not use TooManyItems' source code or graphics in your own mods.";
    private boolean once = false;
    private boolean isOpen = false;
    private int savedScreenWidth = 0;
    private int savedScreenHeight = 0;
    private int savedGuiWidth = 0;
    private int savedGuiHeight = 0;
    private TMIArea sidebar = new TMISidebar();
    private boolean skipMouseUpOnce = false;

    public void onCreate(HandledScreen p_onCreate_1_) {
        TMITickEntity.create();
        TMIConfigFile.read();
        TMISaveFile.read();
    }

    public void frameStart(int p_frameStart_1_, int p_frameStart_2_, int p_frameStart_3_, int p_frameStart_4_, int p_frameStart_5_, int p_frameStart_6_) {
        try {
            if (!TMIConfigFile.isEnabled()) {
                return;
            }
            Screen screen = MinecraftClient.getInstance().currentScreen;
            if (screen instanceof CreativeInventoryScreen)
            {
                TextFieldWidget textfieldwidget = (TextFieldWidget)TMIPrivate.creativeSearchBox.get(screen);
                textfieldwidget.x = p_frameStart_3_ + 82;
            }
            int k = MinecraftClient.getInstance().currentScreen.width;
            int i = MinecraftClient.getInstance().currentScreen.height;
            if (k != this.savedScreenWidth || i != this.savedScreenHeight || p_frameStart_5_ != this.savedGuiWidth || p_frameStart_6_ != this.savedGuiHeight) {
                this.savedScreenWidth = k;
                this.savedScreenHeight = i;
                this.savedGuiWidth = p_frameStart_5_;
                this.savedGuiHeight = p_frameStart_6_;
                int j = (k - p_frameStart_5_) / 2;
                this.sidebar.setSize(Math.min((int)175, (int)(j - 4)), Math.min((int)340, (int)(i - 8)));
                this.sidebar.setPosition(k - j + 2, (i - this.sidebar.getHeight()) / 2);
                this.sidebar.doLayout();
            }
            TMIDispatch.determineMouseover(this.sidebar, p_frameStart_1_, p_frameStart_2_);
        }
        catch (Throwable throwable) {
            TMIDebug.reportExceptionWithTimeout(throwable, 10000L);
        }
    }

    public void frameEnd(int p_frameEnd_1_, int p_frameEnd_2_, int p_frameEnd_3_, int p_frameEnd_4_, int p_frameEnd_5_, int p_frameEnd_6_) {
        if (TMIConfigFile.isEnabled()) {
            GL11.glPushMatrix();
            GL11.glTranslatef((float)(-((float)p_frameEnd_3_)), (float)(-((float)p_frameEnd_4_)), (float)0.0f);
            try {
                List<String> list;
                this.sidebar.draw(p_frameEnd_1_, p_frameEnd_2_);
                TMIArea tmiarea = this.sidebar.getMouseoverArea();
                if (tmiarea != null && (list = tmiarea.getTooltip()) != null && list.size() > 0) {
                    TMIDrawing.drawTooltip(p_frameEnd_1_, p_frameEnd_2_, list);
                }
            }
            catch (Throwable throwable) {
                TMIDebug.reportExceptionWithTimeout(throwable, 10000L);
            }
            GL11.glPopMatrix();
            GL11.glEnable((int)2896);
            GL11.glEnable((int)2929);
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        }
    }

    public void onClose() {
        this.sidebar.blurFocused();
    }

    public boolean onClick(int p_onClick_1_, int p_onClick_2_, int p_onClick_3_, int p_onClick_4_, int p_onClick_5_, ScreenHandler p_onClick_6_) {
        try {
            if (!TMIConfigFile.isEnabled()) {
                return true;
            }
            this.sidebar.blurFocused();
            TMIEvent tmievent = TMIEvent.clickEvent(p_onClick_1_, p_onClick_2_, p_onClick_3_);
            TMIDispatch.sendMouseEvent(tmievent, this.sidebar);
            if (!tmievent.cancel && p_onClick_3_ == 0) {
                try {
                    Screen screen = MinecraftClient.getInstance().currentScreen;
                    if (screen instanceof CreativeInventoryScreen) {
                        TextFieldWidget textfieldwidget = (TextFieldWidget)TMIPrivate.creativeSearchBox.get(screen);
                        textfieldwidget.setFocused(true);
                    }
                }
                catch (Exception exception) {}
            }
            this.skipMouseUpOnce = tmievent.cancel;
            return !tmievent.cancel;
        }
        catch (Throwable throwable) {
            TMIDebug.reportException(throwable);
            return true;
        }
    }

    public boolean onKeypress(char p_onKeypress_1_, int p_onKeypress_2_) {
        try {
            if (!TMIConfigFile.isEnabled()) {
                if (p_onKeypress_2_ == TMIConfigFile.getHotkey()) {
                    TMIConfigFile.toggleEnabled();
                    return false;
                }
                return true;
            }
            TMIEvent tmievent = TMIEvent.keypressEvent(p_onKeypress_1_, p_onKeypress_2_);
            TMIDispatch.sendKeypress(tmievent, this.sidebar);
            if (!tmievent.cancel && p_onKeypress_2_ == TMIConfigFile.getHotkey()) {
                TMIConfigFile.toggleEnabled();
                return false;
            }
            return !tmievent.cancel;
        }
        catch (Throwable throwable) {
            TMIDebug.reportException(throwable);
            return true;
        }
    }

    public void onMouseEvent() {
        try {
            if (!TMIConfigFile.isEnabled()) {
                return;
            }
            int i = Mouse.getEventDWheel();
            if (i == 0) {
                return;
            }
            i = i < 0 ? -1 : 1;
            Screen screen = MinecraftClient.getInstance().currentScreen;
            int j = Mouse.getEventX() * screen.width / MinecraftClient.getInstance().width;
            int k = screen.height - Mouse.getEventY() * screen.height / MinecraftClient.getInstance().height - 1;
            TMIEvent tmievent = TMIEvent.scrollEvent(j, k, i);
            TMIDispatch.sendMouseEvent(tmievent, this.sidebar);
            if (tmievent.cancel) {
                TMIPrivate.lwjglMouseEventDWheel.setInt(null, 0);
                TMIPrivate.lwjglMouseDWheel.setInt(null, 0);
            }
        }
        catch (Throwable throwable) {
            TMIDebug.reportException(throwable);
        }
    }

    public boolean shouldPauseGame() {
        return false;
    }

    public boolean skipMouseUpOnce() {
        boolean flag = this.skipMouseUpOnce;
        this.skipMouseUpOnce = false;
        return flag;
    }
}