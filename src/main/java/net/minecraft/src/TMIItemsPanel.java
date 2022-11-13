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

    public TMIItemsPanel(List<ItemStack> list) {
        this.items = list;
        this.grid = new TMIItemGrid(list);
        this.searcher = new TMIFuzzySearch(list);
        TMISorting.sortByCreativeTab(list);
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
    public void keyboardEvent(TMIEvent tMIEvent) {
        if (tMIEvent.target == this.search) {
            if (tMIEvent.keyCode == 28) {
                if (this.search.value() == null || this.search.value().equals((Object)"")) {
                    if (this.firstSearchItem != null) {
                        TMIGame.giveStack(this.firstSearchItem);
                    }
                } else if (this.grid.items.size() > 0) {
                    this.firstSearchItem = new TMIStackBuilder((ItemStack)this.grid.items.get(0)).maxStack();
                    TMIGame.giveStack(this.firstSearchItem);
                    this.search.setValue("");
                }
                tMIEvent.cancel();
                return;
            }
        } else if (tMIEvent.keyCode == 15) {
            this.search.focus();
        }
    }

    @Override
    public void controlEvent(TMIEvent tMIEvent) {
        if (tMIEvent.type == 3 && tMIEvent.target == this.search) {
            if (this.search.value() == null || this.search.value().equals((Object)"")) {
                this.grid.items = this.items;
                this.grid.ignorePage = false;
                this.next.show();
                this.prev.show();
            } else {
                this.grid.items = this.searcher.query(this.search.value());
                this.grid.ignorePage = true;
                this.next.hide();
                this.prev.hide();
            }
        }
    }

    @Override
    public void mouseEvent(TMIEvent tMIEvent) {
        if (tMIEvent.target == this.prev) {
            this.grid.setPage(this.grid.page - 1);
        } else if (tMIEvent.target == this.next) {
            this.grid.setPage(this.grid.page + 1);
        }
    }
}