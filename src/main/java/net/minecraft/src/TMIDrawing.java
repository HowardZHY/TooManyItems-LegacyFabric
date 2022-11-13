package net.minecraft.src;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.List;
import javax.imageio.ImageIO;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.Screen;
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

    public static void drawText(int n, int n2, String string) {
        TMIDrawing.drawText(n, n2, string, -1, 2);
    }

    public static void drawText(int n, int n2, String string, int n3) {
        TMIDrawing.drawText(n, n2, string, n3, 2);
    }

    public static void drawText(int n, int n2, String string, int n3, int n4) {
        GL11.glDisable((int)2896);
        GL11.glDisable((int)2929);
        GL11.glPushMatrix();
        GL11.glTranslatef((float)n, (float)n2, (float)0.0f);
        if (n4 != 2) {
            float f = (float)n4 / 2.0f;
            GL11.glScalef((float)f, (float)f, (float)1.0f);
        }
        MinecraftClient.getInstance().textRenderer.draw(string, 0, 0, n3);
        GL11.glPopMatrix();
    }

    public static void drawText(int n, int n2, List<String> list, int n3, int n4) {
        for (String string : list) {
            TMIDrawing.drawText(n, n2, string, n3, n4);
            n2 += 8 * n4 / 2 + n4;
        }
    }

    public static void drawTextCentered(int n, int n2, int n3, int n4, String string, int n5, int n6) {
        int n7 = TMIDrawing.getTextWidth(string) * n6 / 2;
        TMIDrawing.drawText(n += (n3 - n7) / 2, n2 += (n4 - 8 * n6 / 2) / 2, string, n5, n6);
    }

    public static int getTextWidth(String string) {
        return TMIDrawing.getTextWidth(string, 2);
    }

    public static int getTextWidth(String string, int n) {
        if (string == null || string.equals((Object)"")) {
            return 0;
        }
        int n2 = MinecraftClient.getInstance().textRenderer.getStringWidth(string);
        if (n != 2) {
            n2 = (int)((float)n2 * ((float)n / 2.0f));
        }
        return n2;
    }

    public static int getTextWidth(List<String> list) {
        int n = 0;
        for (String string : list) {
            int n2 = TMIDrawing.getTextWidth(string);
            if (n2 <= n) continue;
            n = n2;
        }
        return n;
    }

    public static String cutTextToWidth(String string, int n) {
        while (string != null && string.length() > 0 && TMIDrawing.getTextWidth(string) > n) {
            string = string.substring(0, string.length() - 1);
        }
        return string;
    }

    public static void drawTooltip(int n, int n2, List<String> list) {
        int n3 = TMIDrawing.getTextWidth(list);
        int n4 = list.size() * 10 + (list.size() > 1 ? 2 : 0);
        int n5 = n3 + 6;
        int n6 = n4 + 6;
        int n7 = MinecraftClient.getInstance().currentScreen.width;
        int n8 = MinecraftClient.getInstance().currentScreen.height;
        int n9 = n + 12;
        int n10 = n2 - 15;
        if (n9 + n5 > n7) {
            n9 = n - n5 - 12;
        }
        if (n10 + n6 > n8) {
            n10 = n8 - n6 - 2;
        }
        if (n10 < 2) {
            n10 = 2;
        }
        TMIDrawing.drawTooltipPanel(n9, n10, n5, n6);
        int n11 = n9 + 3;
        int n12 = n10 + 4;
        boolean bl = true;
        for (String string : list) {
            TMIDrawing.drawText(n11, n12, string, -1, 2);
            if (bl) {
                n12 += 2;
                bl = false;
            }
            n12 += 10;
        }
    }

    public static void drawIcon(int p_drawIcon_0_, int p_drawIcon_1_, int p_drawIcon_2_, int p_drawIcon_3_, int p_drawIcon_4_, int p_drawIcon_5_) {
        GL11.glDisable((int)2896);
        GL11.glDisable((int)2929);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        TMIDrawing.bindTMITexture();
        MinecraftClient.getInstance().currentScreen.drawTexture(p_drawIcon_0_, p_drawIcon_1_, p_drawIcon_2_, p_drawIcon_3_, p_drawIcon_4_, p_drawIcon_5_);
    }

    public static void drawItem(int n, int n2, ItemStack itemStack) {
        TMIDrawing.drawItem(n, n2, itemStack, false);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static void drawItem(int n, int n2, ItemStack itemStack, boolean bl) {
        GL11.glEnable((int)2896);
        GL11.glEnable((int)2929);
        try {
            drawItems.renderInGuiWithOverrides(itemStack, n, n2);
            if (!bl) return;
        }
        catch (Throwable throwable) {
            drawItems.renderInGuiWithOverrides(invalid, n, n2);
        }
    }

    public static void fillRect(int p_fillRect_0_, int p_fillRect_1_, int p_fillRect_2_, int p_fillRect_3_, int p_fillRect_4_) {
        GL11.glDisable((int)2896);
        GL11.glDisable((int)2929);
        Screen screen = MinecraftClient.getInstance().currentScreen;
        DrawableHelper.fill((int)p_fillRect_0_, (int)p_fillRect_1_, (int)(p_fillRect_0_ + p_fillRect_2_), (int)(p_fillRect_1_ + p_fillRect_3_), (int)p_fillRect_4_);
    }

    public static void fillGradientRect(int n, int n2, int n3, int n4, int n5, int n6) {
        TMIDrawing.fillRect(n, n2, n3, n4, n5);
    }

    public static void drawPanel(int n, int n2, int n3, int n4, int n5, int n6, int n7) {
        TMIDrawing.fillRect(n + 1, n2, n3 - 2, 1, n6);
        TMIDrawing.fillRect(n + n3 - 1, n2 + 1, 1, n4 - 2, n6);
        TMIDrawing.fillRect(n + 1, n2 + n4 - 1, n3 - 2, 1, n7);
        TMIDrawing.fillRect(n, n2 + 1, 1, n4 - 2, n7);
        TMIDrawing.fillRect(n + 1, n2 + 1, n3 - 2, n4 - 2, n5);
    }

    public static void drawTooltipPanel(int n, int n2, int n3, int n4) {
        int n5 = -15728624;
        int n6 = -14088865;
        int n7 = -14743493;
        TMIDrawing.fillRect(n + 1, n2, n3 - 2, n4, n5);
        TMIDrawing.fillRect(n, n2 + 1, n3, n4 - 2, n5);
        TMIDrawing.fillRect(n + 1, n2 + 1, n3 - 2, 1, n6);
        n3 = 2;
        TMIDrawing.fillRect(n + 1, n2 + n4 - 2, 2, 1, n7);
        TMIDrawing.fillGradientRect(n + n3 - 2, n2 + 1, n4 - 2, 1, n6, n7);
        TMIDrawing.fillGradientRect(n + 1, n2 + 1, n4 - 2, 1, n6, n7);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static void bindTMITexture() {
        if (textureID == -1) {
            InputStream inputStream = null;
            try {
                textureID = 729294;
                inputStream = TMIDrawing.class.getResourceAsStream("tmi.png");
                BufferedImage bufferedImage = ImageIO.read((InputStream)inputStream);
                BufferedImage bufferedImage2 = new BufferedImage(512, 512, 2);
                bufferedImage2.createGraphics().drawImage((Image)bufferedImage, 0, 0, null);
                TextureUtil.method_5860((int)textureID, (BufferedImage)bufferedImage2, (boolean)false, (boolean)false);
            }
            catch (Exception exception) {
                System.out.println("[TMI] SEVERE: couldn't load tmi.png");
                exception.printStackTrace();
            }
            finally {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    }
                    catch (Exception exception) {}
                }
            }
        }
        TextureUtil.bindTexture((int)textureID);
    }
}