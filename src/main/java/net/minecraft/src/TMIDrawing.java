package net.minecraft.src;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.InputStream;
import java.util.List;
import javax.imageio.ImageIO;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.texture.TextureUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.lwjgl.opengl.GL11;

public class TMIDrawing {
    public static final int TEXT_SCALE_AGATE = 1;
    public static final int TEXT_SCALE_NORMAL = 2;
    public static final int TEXT_SCALE_BIG = 4;
    public static final int TEXT_HEIGHT = 8;
    public static final int WHITE = -1;
    public static final int BLACK = -16777216;
    public static final int HIGHLIGHT = 0x22FFFFFF;
    private static int textureID = -1;
    private static ItemRenderer drawItems = MinecraftClient.getInstance().getItemRenderer();
    private static ItemStack invalid = new ItemStack((Item)Item.REGISTRY.get(new Identifier("barrier")));

    public static void drawText(int p_drawText_0_, int p_drawText_1_, String p_drawText_2_) {
        TMIDrawing.drawText(p_drawText_0_, p_drawText_1_, p_drawText_2_, -1, 2);
    }

    public static void drawText(int p_drawText_0_, int p_drawText_1_, String p_drawText_2_, int p_drawText_3_) {
        TMIDrawing.drawText(p_drawText_0_, p_drawText_1_, p_drawText_2_, p_drawText_3_, 2);
    }

    public static void drawText(int p_drawText_0_, int p_drawText_1_, String p_drawText_2_, int p_drawText_3_, int p_drawText_4_) {
        GL11.glDisable((int)2896);
        GL11.glDisable((int)2929);
        GL11.glPushMatrix();
        GL11.glTranslatef((float)p_drawText_0_, (float)p_drawText_1_, (float)0.0f);
        if (p_drawText_4_ != 2) {
            float f = (float)p_drawText_4_ / 2.0f;
            GL11.glScalef((float)f, (float)f, (float)1.0f);
        }
        MinecraftClient.getInstance().textRenderer.draw(p_drawText_2_, 0, 0, p_drawText_3_);
        GL11.glPopMatrix();
    }

    public static void drawText(int p_drawText_0_, int p_drawText_1_, List<String> p_drawText_2_, int p_drawText_3_, int p_drawText_4_) {
        for (String s : p_drawText_2_) {
            TMIDrawing.drawText(p_drawText_0_, p_drawText_1_, s, p_drawText_3_, p_drawText_4_);
            p_drawText_1_ += 8 * p_drawText_4_ / 2 + p_drawText_4_;
        }
    }

    public static void drawTextCentered(int p_drawTextCentered_0_, int p_drawTextCentered_1_, int p_drawTextCentered_2_, int p_drawTextCentered_3_, String p_drawTextCentered_4_, int p_drawTextCentered_5_, int p_drawTextCentered_6_) {
        int i = TMIDrawing.getTextWidth(p_drawTextCentered_4_) * p_drawTextCentered_6_ / 2;
        TMIDrawing.drawText(p_drawTextCentered_0_ += (p_drawTextCentered_2_ - i) / 2, p_drawTextCentered_1_ += (p_drawTextCentered_3_ - 8 * p_drawTextCentered_6_ / 2) / 2, p_drawTextCentered_4_, p_drawTextCentered_5_, p_drawTextCentered_6_);
    }

    public static int getTextWidth(String p_getTextWidth_0_) {
        return TMIDrawing.getTextWidth(p_getTextWidth_0_, 2);
    }

    public static int getTextWidth(String p_getTextWidth_0_, int p_getTextWidth_1_) {
        if (p_getTextWidth_0_ != null && !p_getTextWidth_0_.equals((Object)"")) {
            int i = MinecraftClient.getInstance().textRenderer.getStringWidth(p_getTextWidth_0_);
            if (p_getTextWidth_1_ != 2) {
                i = (int)((float)i * ((float)p_getTextWidth_1_ / 2.0f));
            }
            return i;
        }
        return 0;
    }

    public static int getTextWidth(List<String> p_getTextWidth_0_) {
        int i = 0;
        for (String s : p_getTextWidth_0_) {
            int j = TMIDrawing.getTextWidth(s);
            if (j <= i) continue;
            i = j;
        }
        return i;
    }

    public static String cutTextToWidth(String p_cutTextToWidth_0_, int p_cutTextToWidth_1_) {
        while (p_cutTextToWidth_0_ != null && p_cutTextToWidth_0_.length() > 0 && TMIDrawing.getTextWidth(p_cutTextToWidth_0_) > p_cutTextToWidth_1_) {
            p_cutTextToWidth_0_ = p_cutTextToWidth_0_.substring(0, p_cutTextToWidth_0_.length() - 1);
        }
        return p_cutTextToWidth_0_;
    }

    public static void drawTooltip(int p_drawTooltip_0_, int p_drawTooltip_1_, List<String> p_drawTooltip_2_) {
        int i = TMIDrawing.getTextWidth(p_drawTooltip_2_);
        int j = p_drawTooltip_2_.size() * 10 + (p_drawTooltip_2_.size() > 1 ? 2 : 0);
        int k = i + 6;
        int l = j + 6;
        int i1 = MinecraftClient.getInstance().currentScreen.width;
        int j1 = MinecraftClient.getInstance().currentScreen.height;
        int k1 = p_drawTooltip_0_ + 12;
        int l1 = p_drawTooltip_1_ - 15;
        if (k1 + k > i1) {
            k1 = p_drawTooltip_0_ - k - 12;
        }
        if (l1 + l > j1) {
            l1 = j1 - l - 2;
        }
        if (l1 < 2) {
            l1 = 2;
        }
        TMIDrawing.drawTooltipPanel(k1, l1, k, l);
        int i2 = k1 + 3;
        int j2 = l1 + 4;
        boolean flag = true;
        for (String s : p_drawTooltip_2_) {
            TMIDrawing.drawText(i2, j2, s, -1, 2);
            if (flag) {
                j2 += 2;
                flag = false;
            }
            j2 += 10;
        }
    }

    public static void drawIcon(int p_drawIcon_0_, int p_drawIcon_1_, int p_drawIcon_2_, int p_drawIcon_3_, int p_drawIcon_4_, int p_drawIcon_5_) {
        GL11.glDisable((int)2896);
        GL11.glDisable((int)2929);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        TMIDrawing.bindTMITexture();
        MinecraftClient.getInstance().currentScreen.drawTexture(p_drawIcon_0_, p_drawIcon_1_, p_drawIcon_2_, p_drawIcon_3_, p_drawIcon_4_, p_drawIcon_5_);
    }

    public static void drawItem(int p_drawItem_0_, int p_drawItem_1_, ItemStack p_drawItem_2_) {
        TMIDrawing.drawItem(p_drawItem_0_, p_drawItem_1_, p_drawItem_2_, false);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static void drawItem(int p_drawItem_0_, int p_drawItem_1_, ItemStack p_drawItem_2_, boolean p_drawItem_3_) {
        GL11.glEnable((int)2896);
        GL11.glEnable((int)2929);
        try {
            drawItems.method_12461(p_drawItem_2_, p_drawItem_0_, p_drawItem_1_);
            if (!p_drawItem_3_) return;
        }
        catch (Throwable var5) {
            drawItems.method_12461(invalid, p_drawItem_0_, p_drawItem_1_);
        }
    }

    public static void fillRect(int p_fillRect_0_, int p_fillRect_1_, int p_fillRect_2_, int p_fillRect_3_, int p_fillRect_4_) {
        GL11.glDisable((int)2896);
        GL11.glDisable((int)2929);
        Screen guiscreen = MinecraftClient.getInstance().currentScreen;
        DrawableHelper.fill((int)p_fillRect_0_, (int)p_fillRect_1_, (int)(p_fillRect_0_ + p_fillRect_2_), (int)(p_fillRect_1_ + p_fillRect_3_), (int)p_fillRect_4_);
    }

    public static void fillGradientRect(int p_fillGradientRect_0_, int p_fillGradientRect_1_, int p_fillGradientRect_2_, int p_fillGradientRect_3_, int p_fillGradientRect_4_, int p_fillGradientRect_5_) {
        TMIDrawing.fillRect(p_fillGradientRect_0_, p_fillGradientRect_1_, p_fillGradientRect_2_, p_fillGradientRect_3_, p_fillGradientRect_4_);
    }

    public static void drawPanel(int p_drawPanel_0_, int p_drawPanel_1_, int p_drawPanel_2_, int p_drawPanel_3_, int p_drawPanel_4_, int p_drawPanel_5_, int p_drawPanel_6_) {
        TMIDrawing.fillRect(p_drawPanel_0_ + 1, p_drawPanel_1_, p_drawPanel_2_ - 2, 1, p_drawPanel_5_);
        TMIDrawing.fillRect(p_drawPanel_0_ + p_drawPanel_2_ - 1, p_drawPanel_1_ + 1, 1, p_drawPanel_3_ - 2, p_drawPanel_5_);
        TMIDrawing.fillRect(p_drawPanel_0_ + 1, p_drawPanel_1_ + p_drawPanel_3_ - 1, p_drawPanel_2_ - 2, 1, p_drawPanel_6_);
        TMIDrawing.fillRect(p_drawPanel_0_, p_drawPanel_1_ + 1, 1, p_drawPanel_3_ - 2, p_drawPanel_6_);
        TMIDrawing.fillRect(p_drawPanel_0_ + 1, p_drawPanel_1_ + 1, p_drawPanel_2_ - 2, p_drawPanel_3_ - 2, p_drawPanel_4_);
    }

    public static void drawTooltipPanel(int p_drawTooltipPanel_0_, int p_drawTooltipPanel_1_, int p_drawTooltipPanel_2_, int p_drawTooltipPanel_3_) {
        int i = -15728624;
        int j = -14088865;
        int k = -14743493;
        TMIDrawing.fillRect(p_drawTooltipPanel_0_ + 1, p_drawTooltipPanel_1_, p_drawTooltipPanel_2_ - 2, p_drawTooltipPanel_3_, i);
        TMIDrawing.fillRect(p_drawTooltipPanel_0_, p_drawTooltipPanel_1_ + 1, p_drawTooltipPanel_2_, p_drawTooltipPanel_3_ - 2, i);
        TMIDrawing.fillRect(p_drawTooltipPanel_0_ + 1, p_drawTooltipPanel_1_ + 1, p_drawTooltipPanel_2_ - 2, 1, j);
        int l = p_drawTooltipPanel_0_ + 1;
        int i1 = p_drawTooltipPanel_1_ + p_drawTooltipPanel_3_ - 2;
        int b0 = 2;
        TMIDrawing.fillRect(l, i1, 2, 1, k);
        TMIDrawing.fillGradientRect(p_drawTooltipPanel_0_ + b0 - 2, p_drawTooltipPanel_1_ + 1, p_drawTooltipPanel_3_ - 2, 1, j, k);
        TMIDrawing.fillGradientRect(p_drawTooltipPanel_0_ + 1, p_drawTooltipPanel_1_ + 1, p_drawTooltipPanel_3_ - 2, 1, j, k);
    }

    private static void bindTMITexture() {
        if (textureID == -1) {
            InputStream inputstream = null;
            try {
                textureID = 729294;
                inputstream = TMIDrawing.class.getResourceAsStream("tmi.png");
                BufferedImage bufferedimage = ImageIO.read((InputStream)inputstream);
                BufferedImage bufferedimage1 = new BufferedImage(512, 512, 2);
                bufferedimage1.createGraphics().drawImage((Image)bufferedimage, 0, 0, (ImageObserver)null);
                TextureUtil.method_5860((int)textureID, (BufferedImage)bufferedimage1, (boolean)false, (boolean)false);
            }
            catch (Exception exception) {
                System.out.println("[TMI] SEVERE: couldn't load tmi.png");
                exception.printStackTrace();
            }
            finally {
                if (inputstream != null) {
                    try {
                        inputstream.close();
                    }
                    catch (Exception exception) {}
                }
            }
        }
        TextureUtil.bindTexture((int)textureID);
    }
}