package net.minecraft.src;

import java.util.LinkedList;
import java.util.Iterator;

public class TMIDispatch {
    public static void sendMouseEvent(TMIEvent p_sendMouseEvent_0_, TMIArea p_sendMouseEvent_1_) {
        TMIArea tmiarea = TMIDispatch.findTopmostContainingXY(p_sendMouseEvent_1_, p_sendMouseEvent_0_.x, p_sendMouseEvent_0_.y);
        if (tmiarea != null) {
            p_sendMouseEvent_0_.target = tmiarea;
            tmiarea.mouseEvent(p_sendMouseEvent_0_);
        }
        TMIArea var3 = tmiarea;

        while (!p_sendMouseEvent_0_.cancel && var3 != null && var3.getParent() != null)
        {
            var3 = var3.getParent();
            var3.mouseEvent(p_sendMouseEvent_0_);
        }
    }

    public static void sendKeypress(TMIEvent p_sendKeypress_0_, TMIArea p_sendKeypress_1_) {
        if (p_sendKeypress_1_.focusArea != null) {
            p_sendKeypress_0_.target = p_sendKeypress_1_.focusArea;
            p_sendKeypress_1_.focusArea.keyboardEvent(p_sendKeypress_0_);
        } else {
            p_sendKeypress_1_.keyboardEvent(p_sendKeypress_0_);
        }
    }

    public static void determineMouseover(TMIArea p_determineMouseover_0_, int p_determineMouseover_1_, int p_determineMouseover_2_) {
        TMIArea tmiarea = TMIDispatch.findTopmostContainingXY(p_determineMouseover_0_, p_determineMouseover_1_, p_determineMouseover_2_);
        p_determineMouseover_0_.mouseover(tmiarea);
    }

    public static TMIArea findTopmostContainingXY(TMIArea p_findTopmostContainingXY_0_, int p_findTopmostContainingXY_1_, int p_findTopmostContainingXY_2_) {
        LinkedList linkedlist = new LinkedList();
        TMIArea var4 = null;
        linkedlist.offer(p_findTopmostContainingXY_0_);
        while (!linkedlist.isEmpty()) {
            TMIArea var5 = (TMIArea)linkedlist.remove();
            if (var5.visible() && var5.contains(p_findTopmostContainingXY_1_, p_findTopmostContainingXY_2_))
            {
                if (var4 == null || var4.getZ() <= var5.getZ())
                {
                    var4 = var5;
                }

                if (var5.hasChildren())
                {
                    Iterator var6 = var5.children().iterator();

                    while (var6.hasNext())
                    {
                        TMIArea var7 = (TMIArea)var6.next();
                        linkedlist.offer(var7);
                    }
                }
            }
        }
        return var4;
    }
}