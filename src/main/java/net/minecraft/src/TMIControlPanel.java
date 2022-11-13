package net.minecraft.src;

import java.util.Arrays;
import java.util.List;
import org.lwjgl.opengl.GL11;

public class TMIControlPanel
extends TMIArea {
    private float scale;
    private int controlHeight;
    private int controlPadding = 1;
    private int top = 8;
    private int left = 8;
    private List<TMIControlPanel.Control> controls = Arrays.asList(TMIControlPanel.Control.values());

    public void layoutComponent() {
        int n = Control.values().length * 16;
        this.scale = 1.0f;
        if (n > this.height - 20 - this.top) {
            this.scale = 0.66f;
        }
        this.controlHeight = (int)(16.0f * this.scale);
    }

    private void updateText() {
        Control.SURVIVAL.value = null;
        Control.CREATIVE.value = null;
        Control.ADVENTURE.value = null;
        Control.SPECTATOR.value = null;
        ((Control)((Object)this.controls.get((int)TMIGame.getGameMode()))).value = "selected";
        Control.CHEATS.value = TMIGame.isMultiplayer() ? "SP only" : (TMIGame.getCheats() ? "on" : "off");
        Control.DIFFICULTY.value = TMIGame.isMultiplayer() ? "SP only" : TMIGame.getDifficultyName();
        String string = Control.RAIN.value = TMIGame.isRaining() ? "on" : "off";
        Control.DRY.value = TMIGame.isMultiplayer() ? "SP only" : (TMITickEntity.preventRain ? "on" : "off");
        String string2 = Control.KEEP.value = TMIGame.getKeepInventory() ? "keep items" : "drop items";
        Control.INFINITE.value = TMIGame.isMultiplayer() ? "SP only" : (TMITickEntity.infiniteStack ? "on" : "off");
        Control.HEAL.value = TMIGame.isMultiplayer() ? "SP only" : null;
        Control.MILK.value = TMIGame.isMultiplayer() ? "SP only" : null;
    }

    public void drawComponent(int n, int n2) {
        this.updateText();
        int n3 = this.x + this.left;
        int n4 = this.y + this.top;
        boolean bl = this.contains(n, n2);
        GL11.glPushMatrix();
        GL11.glScalef((float)this.scale, (float)this.scale, (float)1.0f);
        float f = 1.0f / this.scale;
        for (Control control : this.controls) {
            if (bl && n2 >= n4 && n2 < n4 + this.controlHeight) {
                TMIDrawing.fillRect((int)((float)this.x * f), (int)((float)n4 * f), (int)((float)this.width * f), (int)((float)this.controlHeight * f), 0x22FFFFFF);
            }
            String string = control.text;
            if (control.value != null) {
                string = string + ": " + control.value;
            }
            TMIDrawing.drawText((int)((float)n3 * f), (int)((float)(n4 + this.controlPadding) * f), string);
            n4 += this.controlHeight;
        }
        n4 = (int)((float)(this.y + this.height - 15) * f);
        TMIDrawing.drawText((int)((float)n3 * f), n4, "TMI 1.8.9 LF Mixin 2022-11-14", -3355444);
        GL11.glPopMatrix();
    }

    public void mouseEvent(TMIEvent tMIEvent) {
        if (tMIEvent.mouseButton != 0) {
            return;
        }
        int n = (tMIEvent.y - this.y - this.top) / this.controlHeight;
        if (n < 0 || n >= this.controls.size()) {
            return;
        }
        Control control = (Control)((Object)this.controls.get(n));
        switch (control) {
            case SURVIVAL: {
                TMIGame.setGameMode(0);
                break;
            }
            case CREATIVE: {
                TMIGame.setGameMode(1);
                break;
            }
            case ADVENTURE: {
                TMIGame.setGameMode(2);
                break;
            }
            case SPECTATOR: {
                TMIGame.setGameMode(3);
                break;
            }
            case CHEATS: {
                if (TMIGame.isMultiplayer()) break;
                TMIGame.setCheats(!TMIGame.getCheats());
                break;
            }
            case DIFFICULTY: {
                TMIGame.incrementDifficulty();
                break;
            }
            case RAIN: {
                TMIGame.toggleRaining();
                break;
            }
            case HEAL: {
                if (TMIGame.isMultiplayer()) break;
                TMIGame.fillPlayerHealth();
                TMIGame.fillPlayerHunger();
                break;
            }
            case MILK: {
                if (TMIGame.isMultiplayer()) break;
                TMIGame.removePlayerEffects();
                break;
            }
            case INFINITE: {
                if (TMIGame.isMultiplayer()) break;
                TMITickEntity.infiniteStack = !TMITickEntity.infiniteStack;
                break;
            }
            case TIME_SUNRISE: {
                TMIGame.setTime(0L);
                break;
            }
            case TIME_NOON: {
                TMIGame.setTime(6000L);
                break;
            }
            case TIME_MOONRISE: {
                TMIGame.setTime(13000L);
                break;
            }
            case TIME_MIDNIGHT: {
                TMIGame.setTime(18000L);
                break;
            }
            case KEEP: {
                TMIGame.setKeepInventory(!TMIGame.getKeepInventory());
                break;
            }
            case DRY: {
                if (TMIGame.isMultiplayer()) break;
                TMITickEntity.preventRain = !TMITickEntity.preventRain;
            }
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    static enum Control {
        SURVIVAL("Survival mode"),
        CREATIVE("Creative mode"),
        ADVENTURE("Adventure mode"),
        SPECTATOR("Spectator mode"),
        CHEATS("Cheats"),
        KEEP("On death"),
        DIFFICULTY("Difficulty"),
        RAIN("Rain/snow"),
        DRY("Prevent rain"),
        TIME_SUNRISE("Time - sunrise"),
        TIME_NOON("Time - noon"),
        TIME_MOONRISE("Time - moonrise"),
        TIME_MIDNIGHT("Time - midnight"),
        HEAL("Heal + fill hunger"),
        MILK("Remove potion effects"),
        INFINITE("TMI infinite stacks");

        public final String text;
        public String value;

        private Control(String string2) {
            this.text = string2;
        }
    }
}