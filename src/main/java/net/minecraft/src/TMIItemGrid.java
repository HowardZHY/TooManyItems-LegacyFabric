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

    public TMIItemGrid(List<ItemStack> p_i15_1_) {
        this.items = p_i15_1_;
    }

    @Override
    public void layoutComponent() {
        this.cols = this.width / this.SPACING;
        this.rows = this.height / this.SPACING;
        this.itemsPerPage = this.rows * this.cols;
        this.LEFT = this.width % this.SPACING / 2;
    }

    @Override
    public void drawComponent(int p_drawComponent_1_, int p_drawComponent_2_) {
        if (this.items != null) {
            int k;
            ItemStack itemstack = null;
            ItemStack itemstack1 = TMIGame.getHeldItem();
            if (this.isMouseover()) {
                this.clearTooltip();
                if (itemstack1 != null) {
                    if (this.myItemsView) {
                        this.setTooltip("Add to My Items");
                    } else if (!TMIGame.isMultiplayer()) {
                        this.setTooltip("DELETE " + TMIGame.stackName(itemstack1));
                    }
                } else {
                    itemstack = this.getItemAtXY(p_drawComponent_1_, p_drawComponent_2_);
                    if (itemstack != null) {
                        List<String> list = itemstack.getTooltip((PlayerEntity)MinecraftClient.getInstance().player, true);
                        list.add("\u00a7r\u00a7aClick: give stack");
                        list.add("\u00a7r\u00a7aRight-click: give one");
                        if (this.myItemsView) {
                            list.add("\u00a7r\u00a7aShift-click: REMOVE from My Items");
                        } else {
                            list.add("\u00a7r\u00a7aShift-click: add to My Items");
                        }
                        this.setTooltip(list);
                    }
                }
            }
            int i2 = this.ignorePage ? 0 : this.page;
            int i = this.getX() + this.LEFT;
            int j = this.getY() + this.TOP;
            for (int l = k = i2 * this.itemsPerPage; l < k + this.itemsPerPage && l < this.items.size(); ++l) {
                ItemStack itemstack2 = (ItemStack)this.items.get(l);
                int i1 = (l - k) / this.cols;
                int j1 = (l - k) % this.cols;
                if (itemstack2 == itemstack) {
                    TMIDrawing.fillRect(i + j1 * this.SPACING, j + i1 * this.SPACING, this.SPACING, this.SPACING, -6250336);
                }
                int k1 = i + j1 * this.SPACING + 1;
                int l1 = j + i1 * this.SPACING + 1;
                this.drawItem(k1, l1, itemstack2);
                if (itemstack2.getItem() == this.spawnerItem) {
                    String s = TMIGame.stackName(itemstack2);
                    Pattern pattern = Pattern.compile((String)"\u00a7.");
                    s = pattern.matcher((CharSequence)s).replaceAll("");
                    s = s.substring(0, 3);
                    TMIDrawing.drawText(k1 + 1, l1 + 1, s, -2236963, 1);
                    continue;
                }
                if (itemstack2.getItem() != this.staticWaterItem && itemstack2.getItem() != this.staticLavaItem) continue;
                TMIDrawing.drawText(k1 + 1, l1 + 1, "Static", -2236963, 1);
            }
        }
    }

    protected void drawItem(int p_drawItem_1_, int p_drawItem_2_, ItemStack p_drawItem_3_) {
        TMIDrawing.drawItem(p_drawItem_1_, p_drawItem_2_, p_drawItem_3_);
    }

    public int getEvenWidth(int p_getEvenWidth_1_) {
        return p_getEvenWidth_1_ - p_getEvenWidth_1_ % this.SPACING;
    }

    public ItemStack getFirstItem() {
        int i = this.page * this.itemsPerPage;
        return i >= this.items.size() ? null : new TMIStackBuilder((ItemStack)this.items.get(i)).maxStack();
    }

    public ItemStack getItemAtRowCol(int p_getItemAtRowCol_1_, int p_getItemAtRowCol_2_) {
        int i;
        if (p_getItemAtRowCol_2_ >= 0 && p_getItemAtRowCol_2_ < this.cols && p_getItemAtRowCol_1_ >= 0 && p_getItemAtRowCol_1_ < this.rows && (i = this.page * this.itemsPerPage + p_getItemAtRowCol_1_ * this.cols + p_getItemAtRowCol_2_) < this.items.size()) {
            return (ItemStack)this.items.get(i);
        }
        return null;
    }

    public ItemStack getItemAtXY(int p_getItemAtXY_1_, int p_getItemAtXY_2_) {
        if (this.contains(p_getItemAtXY_1_, p_getItemAtXY_2_)) {
            int i = p_getItemAtXY_1_ - this.getX() - this.LEFT;
            int j = p_getItemAtXY_2_ - this.getY() - this.TOP;
            if (i >= 0 && j >= 0) {
                int k = i / this.SPACING;
                int l = j / this.SPACING;
                return this.getItemAtRowCol(l, k);
            }
            return null;
        }
        return null;
    }

    @Override
    public void mouseEvent(TMIEvent p_mouseEvent_1_) {
        if (p_mouseEvent_1_.target == this) {
            if (p_mouseEvent_1_.mouseButton == 0) {
                ItemStack itemstack1;
                ItemStack itemstack = TMIGame.getHeldItem();
                if (itemstack != null) {
                    if (this.myItemsView) {
                        TMISaveFile.addFavorite(itemstack);
                        p_mouseEvent_1_.cancel();
                        return;
                    }
                    if (!TMIGame.isMultiplayer()) {
                        TMIGame.setHeldItem(null);
                        p_mouseEvent_1_.cancel();
                        return;
                    }
                }
                if ((itemstack1 = this.getItemAtXY(p_mouseEvent_1_.x, p_mouseEvent_1_.y)) != null) {
                    if (!Keyboard.isKeyDown((int)54) && !Keyboard.isKeyDown((int)42)) {
                        TMIGame.giveStack(new TMIStackBuilder(itemstack1).maxStack());
                    } else if (this.myItemsView) {
                        TMISaveFile.removeFavorite(itemstack1);
                    } else {
                        TMISaveFile.addFavorite(itemstack1);
                    }
                }
                p_mouseEvent_1_.cancel();
            } else if (p_mouseEvent_1_.mouseButton == 1) {
                ItemStack itemstack2 = this.getItemAtXY(p_mouseEvent_1_.x, p_mouseEvent_1_.y);
                if (itemstack2 != null) {
                    TMIGame.giveStack(new TMIStackBuilder(itemstack2).amount(1).stack());
                }
                p_mouseEvent_1_.cancel();
            } else if (p_mouseEvent_1_.type == 2) {
                if (p_mouseEvent_1_.wheel < 0) {
                    this.setPage(this.page + 1);
                } else {
                    this.setPage(this.page - 1);
                }
                p_mouseEvent_1_.cancel();
            }
        }
    }

    public void setPage(int p_setPage_1_) {
        int i = this.numPages();
        if (i == 0) {
            this.page = 0;
        } else {
            while (p_setPage_1_ < 0) {
                p_setPage_1_ += i;
            }
            while (p_setPage_1_ >= i) {
                p_setPage_1_ -= i;
            }
            this.page = p_setPage_1_;
        }
    }

    public int numPages() {
        return this.items != null && this.items.size() != 0 ? 1 + (this.items.size() - 1) / this.itemsPerPage : 0;
    }
}