package net.minecraft.src;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.resource.language.I18n;

public class TMICustomFireworkPanel
extends TMIArea {
    public static int[] colors = new int[]{0xFFFFFF, 14188339, 11685080, 6724056, 0xE5E533, 8375321, 15892389, 0x4C4C4C, 0x999999, 5013401, 8339378, 3361970, 6704179, 6717235, 0x993333, 0x191919};
    private TMIItemButton fireworkButton = new TMIItemButton(null);
    private TMIItemButton chargeButton = new TMIItemButton(null);
    private TMICycleButton shapeButton;
    private TMICycleButton flightButton = new TMICycleButton("1", "2", "3", "4", "5");
    private TMICycleButton flickerButton = new TMICycleButton("Flicker", "No flicker");
    private TMICycleButton trailButton = new TMICycleButton("Trail", "No trail");
    private TMITextField nameField = new TMITextField();
    private TMILabel colorLabel = new TMILabel("Colors:");
    private TMISwatchPicker colorPicker = new TMISwatchPicker(colors, 12, true);
    private TMILabel fadeColorLabel = new TMILabel("Fade colors:");
    private TMISwatchPicker fadeColorPicker = new TMISwatchPicker(colors, 12, true);

    public TMICustomFireworkPanel() {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < 5; ++i) {
            arrayList.add((Object)I18n.translate((String)("item.fireworksCharge.type." + i), (Object[])new Object[0]));
        }
        this.shapeButton = new TMICycleButton((List<String>)arrayList);
        this.addChild(this.fireworkButton);
        this.addChild(this.chargeButton);
        this.addChild(this.shapeButton);
        this.addChild(this.flightButton);
        this.addChild(this.flickerButton);
        this.addChild(this.trailButton);
        this.addChild(this.nameField);
        this.addChild(this.colorLabel);
        this.addChild(this.colorPicker);
        this.addChild(this.fadeColorLabel);
        this.addChild(this.fadeColorPicker);
        this.flightButton.setPrefix("Flight: ");
        this.nameField.placeholder = "Name...";
        this.nameField.addEventListener(this);
        this.shapeButton.addEventListener(this);
        this.flightButton.addEventListener(this);
        this.flickerButton.addEventListener(this);
        this.trailButton.addEventListener(this);
        this.colorPicker.addEventListener(this);
        this.recreateItem();
    }

    private void recreateItem() {
        TMIFireworkBuilder tMIFireworkBuilder = new TMIFireworkBuilder().flight(this.flightButton.getIntValue());
        tMIFireworkBuilder.explosion(this.shapeButton.getIndex(), this.colorPicker.getArray(), this.fadeColorPicker.getArray(), this.flickerButton.getIndex() == 0, this.trailButton.getIndex() == 0);
        this.fireworkButton.stack = tMIFireworkBuilder.firework().name(this.nameField.value()).stack();
        this.chargeButton.stack = tMIFireworkBuilder.charge().name(this.nameField.value()).stack();
    }

    public void layoutComponent() {
        int n = this.x + (this.width - 16 - 16 - 2) / 2;
        this.fireworkButton.setPosition(n, this.y + 4);
        this.chargeButton.setPosition(n + 16 + 2, this.y + 4);
        int n2 = this.x + 4;
        int n3 = this.x + 4 + (this.width - 8) / 2;
        int n4 = this.y + 4 + 16 + 4;
        int n5 = n4 + 12;
        this.shapeButton.setSize((this.width - 4) / 2, 12);
        this.flightButton.setSize((this.width - 4) / 2, 12);
        this.flickerButton.setSize((this.width - 4) / 2, 12);
        this.trailButton.setSize((this.width - 4) / 2, 12);
        this.shapeButton.setPosition(n2, n4);
        this.flightButton.setPosition(n3, n4);
        this.flickerButton.setPosition(n2, n5);
        this.trailButton.setPosition(n3, n5);
        this.nameField.setSize(this.width - 4, 12);
        this.nameField.setPosition(this.x + 2, this.trailButton.y + 14 + 2);
        this.colorLabel.setPosition(this.x + 2, this.nameField.y + 14 + 4);
        this.colorPicker.setSize(this.width - 4, 24);
        this.colorPicker.setPosition(this.x + 2, this.colorLabel.y + 12 + 1);
        this.fadeColorLabel.setPosition(this.x + 2, this.colorPicker.y + 24 + 6);
        this.fadeColorPicker.setSize(this.width - 4, 24);
        this.fadeColorPicker.setPosition(this.x + 2, this.fadeColorLabel.y + 12 + 1);
    }

    public void controlEvent(TMIEvent tMIEvent) {
        if (tMIEvent.type == 3) {
            this.recreateItem();
        }
    }
}