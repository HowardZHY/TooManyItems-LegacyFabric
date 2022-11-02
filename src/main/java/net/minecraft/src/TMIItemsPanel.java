package net.minecraft.src;

import java.util.List;
import net.minecraft.item.ItemStack;

public class TMIItemsPanel
extends TMIArea {
    public List<ItemStack> items;
    protected TMITextField search = new TMITextField();
    protected TMIItemGrid grid;
    protected ItemStack firstSearchItem = null;
    protected TMIFuzzySearch searcher;
    protected TMIButton prev = new TMIButton(null, "< Prev");
    protected TMIButton next = new TMIButton(null, "Next >");

    public TMIItemsPanel() {
        this(TMIGame.allItems());
    }

    public TMIItemsPanel(List<ItemStack> p_i16_1_) {
        this.items = p_i16_1_;
        this.grid = new TMIItemGrid(p_i16_1_);
        this.searcher = new TMIFuzzySearch(p_i16_1_);
        TMISorting.sortByCreativeTab(p_i16_1_);
        this.addChild(this.search);
        this.addChild(this.grid);
        this.addChild(this.prev);
        this.addChild(this.next);
        this.search.addEventListener(this);
    }

    @Override
    public void layoutComponent() {
        this.search.setPosition(this.x + 2, this.y + 1);
        this.search.setSize(this.width - 4, 12);
        this.grid.setPosition(this.x, this.y + 12 + 4);
        this.grid.setSize(this.width, this.height - 12 - 4 - 2 - 10);
        this.prev.setSize(this.width / 2, 12);
        this.prev.setPosition(this.x, this.y + this.height - 12);
        this.next.setSize(this.width / 2, 12);
        this.next.setPosition(this.x + this.width / 2, this.y + this.height - 12);
    }

    @Override
    public void keyboardEvent(TMIEvent p_keyboardEvent_1_) {
        if (p_keyboardEvent_1_.target == this.search) {
            if (p_keyboardEvent_1_.keyCode == 28) {
                if (this.search.value() != null && !this.search.value().equals((Object)"")) {
                    if (this.grid.items.size() > 0) {
                        this.firstSearchItem = new TMIStackBuilder((ItemStack)this.grid.items.get(0)).maxStack();
                        TMIGame.giveStack(this.firstSearchItem);
                        this.search.setValue("");
                    }
                } else if (this.firstSearchItem != null) {
                    TMIGame.giveStack(this.firstSearchItem);
                }
                p_keyboardEvent_1_.cancel();
                return;
            }
        } else if (p_keyboardEvent_1_.keyCode == 15) {
            this.search.focus();
        }
    }

    @Override
    public void controlEvent(TMIEvent p_controlEvent_1_) {
        if (p_controlEvent_1_.type == 3 && p_controlEvent_1_.target == this.search) {
            if (this.search.value() != null && !this.search.value().equals((Object)"")) {
                this.grid.items = this.searcher.query(this.search.value());
                this.grid.ignorePage = true;
                this.next.hide();
                this.prev.hide();
            } else {
                this.grid.items = this.items;
                this.grid.ignorePage = false;
                this.next.show();
                this.prev.show();
            }
        }
    }

    @Override
    public void mouseEvent(TMIEvent p_mouseEvent_1_) {
        if (p_mouseEvent_1_.target == this.prev) {
            this.grid.setPage(this.grid.page - 1);
        } else if (p_mouseEvent_1_.target == this.next) {
            this.grid.setPage(this.grid.page + 1);
        }
    }
}