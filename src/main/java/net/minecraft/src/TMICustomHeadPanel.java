package net.minecraft.src;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;

class TMICustomHeadPanel
extends TMIArea {
    private TMIItemButton head = new TMIItemButton(new TMIStackBuilder("skull").meta(3).stack());
    private TMITextField name = new TMITextField();

    public TMICustomHeadPanel() {
        this.name.placeholder = "Player name...";
        this.addChild(this.head);
        this.addChild(this.name);
        this.name.addEventListener(this);
    }

    @Override
    public void layoutComponent() {
        this.head.setSize(16, 16);
        this.head.x = this.x + (this.width - 16) / 2;
        this.head.y = this.y + 10;
        this.name.setSize(this.width - 4, 12);
        this.name.setPosition(this.x + 2, this.y + 10 + 16 + 4);
    }

    @Override
    public void controlEvent(TMIEvent p_controlEvent_1_) {
        if (p_controlEvent_1_.type == 3) {
            if (this.name.value() == null || this.name.value().equals((Object)"")) {
                this.head.stack = new TMIStackBuilder("skull").meta(3).stack();
                return;
            }
            TMIStackBuilder tmistackbuilder = new TMIStackBuilder("skull").meta(3);
            NbtCompound nbttagcompound = new NbtCompound();
            nbttagcompound.putString("Name", this.name.value());
            tmistackbuilder.tag().put("SkullOwner", (NbtElement)nbttagcompound);
            this.head.stack = tmistackbuilder.stack();
        }
    }
}