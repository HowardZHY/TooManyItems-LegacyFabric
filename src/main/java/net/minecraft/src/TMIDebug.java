package net.minecraft.src;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

public class TMIDebug {
    private static long lastReportTime = 0L;

    public static void reportException(Throwable throwable) {
        try {
            System.out.println("[TMI serious error - copy from here]");
            throwable.printStackTrace();
            System.out.println("[TMI serious error - copy to here]");
            MinecraftClient.getInstance().inGameHud.getChatHud().addMessage((Text)new LiteralText("[TMI] serious error (see Game Output) " + throwable.toString()));
        }
        catch (Exception exception) {
            System.out.println("[TMI] Error during exception reporting:");
            exception.printStackTrace();
        }
    }

    public static void reportExceptionWithTimeout(Throwable throwable, long l) {
        long l2 = System.currentTimeMillis();
        if (l2 > lastReportTime + l) {
            lastReportTime = l2;
            TMIDebug.reportException(throwable);
        }
    }

    public static void logWithTrace(String string) {
        TMIDebug.logWithTrace(string, 1000);
    }

    public static void logWithTrace(String string, int n) {
        System.out.println("[TMI] " + string);
        StackTraceElement[] stackTraceElementArray = Thread.currentThread().getStackTrace();
        for (int i = 0; i < stackTraceElementArray.length && i < n; ++i) {
            System.out.println(stackTraceElementArray[i].toString());
        }
    }

    public static void logWithError(String string, Throwable throwable) {
        System.out.println("[TMI] " + string);
        throwable.printStackTrace();
    }
}