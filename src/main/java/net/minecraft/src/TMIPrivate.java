package net.minecraft.src;

import java.lang.reflect.Field;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.entity.EntityType;
import net.minecraft.recipe.ShapedRecipeType;
import net.minecraft.recipe.ShapelessRecipeType;
import org.lwjgl.input.Mouse;

public class TMIPrivate {
    public static Field textFieldWidth = TMIPrivate.getPrivateField(TextFieldWidget.class, "field_1119", "width");
    public static Field textFieldHeight = TMIPrivate.getPrivateField(TextFieldWidget.class, "field_1120", "height");
    public static Field lwjglMouseEventDWheel = TMIPrivate.getPrivateField(Mouse.class, "event_dwheel", "event_dwheel");
    public static Field lwjglMouseDWheel = TMIPrivate.getPrivateField(Mouse.class, "dwheel", "dwheel");
    public static Field creativeSearchBox = TMIPrivate.getPrivateField(CreativeInventoryScreen.class, "field_1380", "searchField");
    public static Field entityIdClassMap = TMIPrivate.getPrivateField(EntityType.class, "field_3270", "ID_CLASS_MAP");
    public static Field shapedRecipeWidth = TMIPrivate.getPrivateField(ShapedRecipeType.class, "a", "field_4438");
    public static Field shapedRecipeHeight = TMIPrivate.getPrivateField(ShapedRecipeType.class, "b", "field_4439");
    public static Field shapedRecipeInputs = TMIPrivate.getPrivateField(ShapedRecipeType.class, "c", "field_4440");
    public static Field shapelessRecipeInputs = TMIPrivate.getPrivateField(ShapelessRecipeType.class, "stacks", "field_4443");

    private static Field getPrivateField(Class p_getPrivateField_0_, String p_getPrivateField_1_, String p_getPrivateField_2_) {
        try {
            Field var3;
            try {
                var3 = p_getPrivateField_0_.getDeclaredField(p_getPrivateField_1_);
            }
            catch (NoSuchFieldException var5) {
                var3 = p_getPrivateField_0_.getDeclaredField(p_getPrivateField_2_);
            }
            var3.setAccessible(true);
            Field var4 = Field.class.getDeclaredField("modifiers");
            var4.setAccessible(true);
            var4.setInt((Object)var3, var3.getModifiers() & 0xFFFFFFEF);
            return var3;
        }
        catch (Throwable throwable) {
            TMIDebug.reportException(throwable);
            return null;
        }
    }
}