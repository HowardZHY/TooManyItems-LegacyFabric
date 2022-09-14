import java.lang.reflect.Field;
import net.minecraft.class_1115;
import net.minecraft.class_1116;
import net.minecraft.class_367;
import net.minecraft.class_415;
import net.minecraft.class_867;
import org.lwjgl.input.Mouse;

public class TMIPrivate {
  public static Field textFieldWidth = getPrivateField(class_367.class, "i", "field_1119");
  
  public static Field textFieldHeight = getPrivateField(class_367.class, "j", "field_1120");
  
  public static Field lwjglMouseEventDWheel = getPrivateField(Mouse.class, "event_dwheel", "event_dwheel");
  
  public static Field lwjglMouseDWheel = getPrivateField(Mouse.class, "dwheel", "dwheel");
  
  public static Field creativeSearchBox = getPrivateField(class_415.class, "A", "field_1380");
  
  public static Field entityIdClassMap = getPrivateField(class_867.class, "e", "field_3270");
  
  public static Field shapedRecipeWidth = getPrivateField(class_1115.class, "a", "field_4438");
  
  public static Field shapedRecipeHeight = getPrivateField(class_1115.class, "b", "field_4439");
  
  public static Field shapedRecipeInputs = getPrivateField(class_1115.class, "c", "field_4440");
  
  public static Field shapelessRecipeInputs = getPrivateField(class_1116.class, "b", "field_4443");
  
  private static Field getPrivateField(Class p_getPrivateField_0_, String p_getPrivateField_1_, String p_getPrivateField_2_) {
    try {
      Field var3;
      try {
        var3 = p_getPrivateField_0_.getDeclaredField(p_getPrivateField_1_);
      } catch (NoSuchFieldException var5) {
        var3 = p_getPrivateField_0_.getDeclaredField(p_getPrivateField_2_);
      } 
      var3.setAccessible(true);
      Field var4 = Field.class.getDeclaredField("modifiers");
      var4.setAccessible(true);
      var4.setInt(var3, var3.getModifiers() & 0xFFFFFFEF);
      return var3;
    } catch (Throwable var6) {
      return null;
    } 
  }
}
