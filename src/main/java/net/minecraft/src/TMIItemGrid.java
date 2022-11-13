package net.minecraft.src;

import java.util.List;
import java.util.regex.Pattern;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.lwjgl.input.Keyboard;

public class TMIItemGrid
extends TMIArea {
    public List<ItemStack> items = null;
    public int page;
    public boolean ignorePage = false;
    public boolean myItemsView = false;
    public int itemsPerPage;
    public int cols;
    public int rows;
    public int ITEMWIDTH = 16;
    public int SPACING = this.ITEMWIDTH + 2;
    public int LEFT = 0;
    public int TOP = 0;
    protected final Item spawnerItem = new TMIStackBuilder("mob_spawner").stack().getItem();
    protected final Item staticWaterItem = new TMIStackBuilder("water").stack().getItem();
    protected final Item staticLavaItem = new TMIStackBuilder("lava").stack().getItem();

    public TMIItemGrid() {
    }

    public TMIItemGrid(List<ItemStack> list) {
        this.items = list;
    }

    @Override
    public void layoutComponent() {
        this.cols = this.width / this.SPACING;
        this.rows = this.height / this.SPACING;
        this.itemsPerPage = this.rows * this.cols;
        this.LEFT = this.width % this.SPACING / 2;
    }

    @Override
    public void drawComponent(int n, int n2) {
        int n3;
        if (this.items == null) {
            return;
        }
        ItemStack itemStack = null;
        ItemStack itemStack2 = TMIGame.getHeldItem();
        if (this.isMouseover()) {
            this.clearTooltip();
            if (itemStack2 != null) {
                if (this.myItemsView) {
                    this.setTooltip("Add to My Items");
                } else if (!TMIGame.isMultiplayer()) {
                    this.setTooltip("DELETE " + TMIGame.stackName(itemStack2));
                }
            } else {
                itemStack = this.getItemAtXY(n, n2);
                if (itemStack != null) {
                    List list = itemStack.getTooltip((PlayerEntity)MinecraftClient.getInstance().player, true);
                    list.add((Object)"\u00a7r\u00a7aClick: give stack");
                    list.add((Object)"\u00a7r\u00a7aRight-click: give one");
                    if (this.myItemsView) {
                        list.add((Object)"\u00a7r\u00a7aShift-click: REMOVE from My Items");
                    } else {
                        list.add((Object)"\u00a7r\u00a7aShift-click: add to My Items");
                    }
                    this.setTooltip((List<String>)list);
                }
            }
        }
        int n4 = this.ignorePage ? 0 : this.page;
        int n5 = this.getX() + this.LEFT;
        int n6 = this.getY() + this.TOP;
        for (int i = n3 = n4 * this.itemsPerPage; i < n3 + this.itemsPerPage && i < this.items.size(); ++i) {
            ItemStack itemStack3 = (ItemStack)this.items.get(i);
            int n7 = (i - n3) / this.cols;
            int n8 = (i - n3) % this.cols;
            if (itemStack3 == itemStack) {
                TMIDrawing.fillRect(n5 + n8 * this.SPACING, n6 + n7 * this.SPACING, this.SPACING, this.SPACING, -6250336);
            }
            int n9 = n5 + n8 * this.SPACING + 1;
            int n10 = n6 + n7 * this.SPACING + 1;
            this.drawItem(n9, n10, itemStack3);
            if (itemStack3.getItem() == this.spawnerItem) {
                String string = TMIGame.stackName(itemStack3);
                Pattern pattern = Pattern.compile((String)"\u00a7.");
                string = pattern.matcher((CharSequence)string).replaceAll("");
                string = string.substring(0, 3);
                TMIDrawing.drawText(n9 + 1, n10 + 1, string, -2236963, 1);
                continue;
            }
            if (itemStack3.getItem() != this.staticWaterItem && itemStack3.getItem() != this.staticLavaItem) continue;
            TMIDrawing.drawText(n9 + 1, n10 + 1, "Static", -2236963, 1);
        }
    }

    protected void drawItem(int n, int n2, ItemStack itemStack) {
        TMIDrawing.drawItem(n, n2, itemStack);
    }

    public int getEvenWidth(int n) {
        return n - n % this.SPACING;
    }

    public ItemStack getFirstItem() {
        int n = this.page * this.itemsPerPage;
        if (n >= this.items.size()) {
            return null;
        }
        return new TMIStackBuilder((ItemStack)this.items.get(n)).maxStack();
    }

    public ItemStack getItemAtRowCol(int n, int n2) {
        int n3;
        if (n2 >= 0 && n2 < this.cols && n >= 0 && n < this.rows && (n3 = this.page * this.itemsPerPage + n * this.cols + n2) < this.items.size()) {
            return (ItemStack)this.items.get(n3);
        }
        return null;
    }

    public ItemStack getItemAtXY(int n, int n2) {
        if (this.contains(n, n2)) {
            int n3 = n - this.getX() - this.LEFT;
            int n4 = n2 - this.getY() - this.TOP;
            if (n3 < 0 || n4 < 0) {
                return null;
            }
            int n5 = n3 / this.SPACING;
            int n6 = n4 / this.SPACING;
            return this.getItemAtRowCol(n6, n5);
        }
        return null;
    }

    @Override
    public void mouseEvent(TMIEvent tMIEvent) {
        if (tMIEvent.target == this) {
            if (tMIEvent.mouseButton == 0) {
                ItemStack itemStack;
                ItemStack itemStack2 = TMIGame.getHeldItem();
                if (itemStack2 != null) {
                    if (this.myItemsView) {
                        TMISaveFile.addFavorite(itemStack2);
                        tMIEvent.cancel();
                        return;
                    }
                    if (!TMIGame.isMultiplayer()) {
                        TMIGame.setHeldItem(null);
                        tMIEvent.cancel();
                        return;
                    }
                }
                if ((itemStack = this.getItemAtXY(tMIEvent.x, tMIEvent.y)) != null) {
                    if (Keyboard.isKeyDown((int)54) || Keyboard.isKeyDown((int)42)) {
                        if (this.myItemsView) {
                            TMISaveFile.removeFavorite(itemStack);
                        } else {
                            TMISaveFile.addFavorite(itemStack);
                        }
                    } else {
                        TMIGame.giveStack(new TMIStackBuilder(itemStack).maxStack());
                    }
                }
                tMIEvent.cancel();
            } else if (tMIEvent.mouseButton == 1) {
                ItemStack itemStack = this.getItemAtXY(tMIEvent.x, tMIEvent.y);
                if (itemStack != null) {
                    TMIGame.giveStack(new TMIStackBuilder(itemStack).amount(1).stack());
                }
                tMIEvent.cancel();
            } else if (tMIEvent.type == 2) {
                if (tMIEvent.wheel < 0) {
                    this.setPage(this.page + 1);
                } else {
                    this.setPage(this.page - 1);
                }
                tMIEvent.cancel();
            }
        }
    }

    public void setPage(int n) {
        int n2 = this.numPages();
        if (n2 == 0) {
            this.page = 0;
        } else {
            while (n < 0) {
                n += n2;
            }
            while (n >= n2) {
                n -= n2;
            }
            this.page = n;
        }
    }

    public int numPages() {
        if (this.items == null || this.items.size() == 0) {
            return 0;
        }
        return 1 + (this.items.size() - 1) / this.itemsPerPage;
    }
}