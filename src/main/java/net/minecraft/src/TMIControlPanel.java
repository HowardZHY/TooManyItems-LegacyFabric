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

    @Override
    public void layoutComponent() {
        int i = Control.values().length * 16;
        this.scale = 1.0f;
        if (i > this.height - 20 - this.top) {
            this.scale = 0.66f;
        }
        this.controlHeight = (int)(16.0f * this.scale);
    }

    private void updateText() {
        Control.SURVIVAL.value = null;
        Control.CREATIVE.value = null;
        Control.ADVENTURE.value = null;
        Control.SPECTATOR.value = null;
        ((this.controls.get(TMIGame.getGameMode()))).value = "selected";
        Control.CHEATS.value = TMIGame.isMultiplayer() ? "SP only" : (TMIGame.getCheats() ? "on" : "off");
        Control.DIFFICULTY.value = TMIGame.isMultiplayer() ? "SP only" : TMIGame.getDifficultyName();
        String string = Control.RAIN.value = TMIGame.isRaining() ? "on" : "off";
        Control.DRY.value = TMIGame.isMultiplayer() ? "SP only" : (TMITickEntity.preventRain ? "on" : "off");
        String string2 = Control.KEEP.value = TMIGame.getKeepInventory() ? "keep items" : "drop items";
        Control.INFINITE.value = TMIGame.isMultiplayer() ? "SP only" : (TMITickEntity.infiniteStack ? "on" : "off");
        Control.HEAL.value = TMIGame.isMultiplayer() ? "SP only" : null;
        Control.MILK.value = TMIGame.isMultiplayer() ? "SP only" : null;
    }

    @Override
    public void drawComponent(int p_drawComponent_1_, int p_drawComponent_2_) {
        this.updateText();
        int i = this.x + this.left;
        int j = this.y + this.top;
        boolean flag = this.contains(p_drawComponent_1_, p_drawComponent_2_);
        GL11.glPushMatrix();
        GL11.glScalef((float)this.scale, (float)this.scale, (float)1.0f);
        float f = 1.0f / this.scale;
        for (Control tmicontrolpanel$control : this.controls) {
            if (flag && p_drawComponent_2_ >= j && p_drawComponent_2_ < j + this.controlHeight) {
                TMIDrawing.fillRect((int)((float)this.x * f), (int)((float)j * f), (int)((float)this.width * f), (int)((float)this.controlHeight * f), 0x22FFFFFF);
            }
            String s = tmicontrolpanel$control.text;
            if (tmicontrolpanel$control.value != null) {
                s = s + ": " + tmicontrolpanel$control.value;
            }
            TMIDrawing.drawText((int)((float)i * f), (int)((float)(j + this.controlPadding) * f), s);
            j += this.controlHeight;
        }
        j = (int)((float)(this.y + this.height - 15) * f);
        TMIDrawing.drawText((int)((float)i * f), j, "TMI 1.10.2 2022-11-02 wotblitz mixined by HowardZHY", -3355444);
        GL11.glPopMatrix();
    }

    @Override
    public void mouseEvent(TMIEvent p_mouseEvent_1_) {
        int i;
        if (p_mouseEvent_1_.mouseButton == 0 && (i = (p_mouseEvent_1_.y - this.y - this.top) / this.controlHeight) >= 0 && i < this.controls.size()) {
            Control tmicontrolpanel$control = (Control)((Object)this.controls.get(i));
            switch (NamelessClass116039108.$SwitchMap$TMIControlPanel$Control[tmicontrolpanel$control.ordinal()]) {
                case 1: {
                    TMIGame.setGameMode(0);
                    break;
                }
                case 2: {
                    TMIGame.setGameMode(1);
                    break;
                }
                case 3: {
                    TMIGame.setGameMode(2);
                    break;
                }
                case 4: {
                    TMIGame.setGameMode(3);
                    break;
                }
                case 5: {
                    if (TMIGame.isMultiplayer()) break;
                    TMIGame.setCheats(!TMIGame.getCheats());
                    break;
                }
                case 6: {
                    TMIGame.incrementDifficulty();
                    break;
                }
                case 7: {
                    TMIGame.toggleRaining();
                    break;
                }
                case 8: {
                    if (TMIGame.isMultiplayer()) break;
                    TMIGame.fillPlayerHealth();
                    TMIGame.fillPlayerHunger();
                    break;
                }
                case 9: {
                    if (TMIGame.isMultiplayer()) break;
                    TMIGame.removePlayerEffects();
                    break;
                }
                case 10: {
                    if (TMIGame.isMultiplayer()) break;
                    TMITickEntity.infiniteStack = !TMITickEntity.infiniteStack;
                    break;
                }
                case 11: {
                    TMIGame.setTime(0L);
                    break;
                }
                case 12: {
                    TMIGame.setTime(6000L);
                    break;
                }
                case 13: {
                    TMIGame.setTime(13000L);
                    break;
                }
                case 14: {
                    TMIGame.setTime(18000L);
                    break;
                }
                case 15: {
                    TMIGame.setKeepInventory(!TMIGame.getKeepInventory());
                    break;
                }
                case 16: {
                    if (TMIGame.isMultiplayer()) break;
                    TMITickEntity.preventRain = !TMITickEntity.preventRain;
                }
            }
        }
    }

    static class NamelessClass116039108 {
        static final int[] $SwitchMap$TMIControlPanel$Control = new int[Control.values().length];

        NamelessClass116039108() {
        }

        static {
            try {
                NamelessClass116039108.$SwitchMap$TMIControlPanel$Control[Control.SURVIVAL.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                NamelessClass116039108.$SwitchMap$TMIControlPanel$Control[Control.CREATIVE.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                NamelessClass116039108.$SwitchMap$TMIControlPanel$Control[Control.ADVENTURE.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                NamelessClass116039108.$SwitchMap$TMIControlPanel$Control[Control.SPECTATOR.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                NamelessClass116039108.$SwitchMap$TMIControlPanel$Control[Control.CHEATS.ordinal()] = 5;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                NamelessClass116039108.$SwitchMap$TMIControlPanel$Control[Control.DIFFICULTY.ordinal()] = 6;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                NamelessClass116039108.$SwitchMap$TMIControlPanel$Control[Control.RAIN.ordinal()] = 7;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                NamelessClass116039108.$SwitchMap$TMIControlPanel$Control[Control.HEAL.ordinal()] = 8;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                NamelessClass116039108.$SwitchMap$TMIControlPanel$Control[Control.MILK.ordinal()] = 9;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                NamelessClass116039108.$SwitchMap$TMIControlPanel$Control[Control.INFINITE.ordinal()] = 10;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                NamelessClass116039108.$SwitchMap$TMIControlPanel$Control[Control.TIME_SUNRISE.ordinal()] = 11;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                NamelessClass116039108.$SwitchMap$TMIControlPanel$Control[Control.TIME_NOON.ordinal()] = 12;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                NamelessClass116039108.$SwitchMap$TMIControlPanel$Control[Control.TIME_MOONRISE.ordinal()] = 13;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                NamelessClass116039108.$SwitchMap$TMIControlPanel$Control[Control.TIME_MIDNIGHT.ordinal()] = 14;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                NamelessClass116039108.$SwitchMap$TMIControlPanel$Control[Control.KEEP.ordinal()] = 15;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                NamelessClass116039108.$SwitchMap$TMIControlPanel$Control[Control.DRY.ordinal()] = 16;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
        }
    }

    static enum Control {
        SURVIVAL("SURVIVAL", 0, "Survival mode"),
        CREATIVE("CREATIVE", 1, "Creative mode"),
        ADVENTURE("ADVENTURE", 2, "Adventure mode"),
        SPECTATOR("SPECTATOR", 3, "Spectator mode"),
        CHEATS("CHEATS", 4, "Cheats"),
        KEEP("KEEP", 5, "On death"),
        DIFFICULTY("DIFFICULTY", 6, "Difficulty"),
        RAIN("RAIN", 7, "Rain/snow"),
        DRY("DRY", 8, "Prevent rain"),
        TIME_SUNRISE("TIME_SUNRISE", 9, "Time - sunrise"),
        TIME_NOON("TIME_NOON", 10, "Time - noon"),
        TIME_MOONRISE("TIME_MOONRISE", 11, "Time - moonrise"),
        TIME_MIDNIGHT("TIME_MIDNIGHT", 12, "Time - midnight"),
        HEAL("HEAL", 13, "Heal + fill hunger"),
        MILK("MILK", 14, "Remove potion effects"),
        INFINITE("INFINITE", 15, "TMI infinite stacks");

        public final String text;
        public String value;
        private static final Control[] $VALUES;

        private Control(String p_i4_3_, int p_i4_4_, String p_i4_5_) {
            this.text = p_i4_5_;
        }

        static {
            $VALUES = new Control[]{SURVIVAL, CREATIVE, ADVENTURE, SPECTATOR, CHEATS, KEEP, DIFFICULTY, RAIN, DRY, TIME_SUNRISE, TIME_NOON, TIME_MOONRISE, TIME_MIDNIGHT, HEAL, MILK, INFINITE};
        }
    }
}