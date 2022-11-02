package net.minecraft.src;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

public class TMIDebug {
    private static long lastReportTime = 0L;

    public static void reportException(Throwable p_reportException_0_) {
        try {
            System.out.println("[TMI serious error - copy from here]");
            p_reportException_0_.printStackTrace();
            System.out.println("[TMI serious error - copy to here]");
            MinecraftClient.getInstance().inGameHud.getChatHud().addMessage((Text)new LiteralText("[TMI] serious error (see Game Output) " + p_reportException_0_.toString()));
        }
        catch (Exception exception) {
            System.out.println("[TMI] Error during exception reporting:");
            exception.printStackTrace();
        }
    }

    public static void reportExceptionWithTimeout(Throwable p_reportExceptionWithTimeout_0_, long p_reportExceptionWithTimeout_1_) {
        long i = System.currentTimeMillis();
        if (i > lastReportTime + p_reportExceptionWithTimeout_1_) {
            lastReportTime = i;
            TMIDebug.reportException(p_reportExceptionWithTimeout_0_);
        }
    }

    public static void logWithTrace(String p_logWithTrace_0_) {
        TMIDebug.logWithTrace(p_logWithTrace_0_, 1000);
    }

    public static void logWithTrace(String p_logWithTrace_0_, int p_logWithTrace_1_) {
        System.out.println("[TMI] " + p_logWithTrace_0_);
        StackTraceElement[] astacktraceelement = Thread.currentThread().getStackTrace();
        for (int i = 0; i < astacktraceelement.length && i < p_logWithTrace_1_; ++i) {
            System.out.println(astacktraceelement[i].toString());
        }
    }

    public static void logWithError(String p_logWithError_0_, Throwable p_logWithError_1_) {
        System.out.println("[TMI] " + p_logWithError_0_);
        p_logWithError_1_.printStackTrace();
    }
}