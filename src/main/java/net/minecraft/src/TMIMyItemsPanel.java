package net.minecraft.src;

public class TMIMyItemsPanel
extends TMIItemsPanel {
    public TMIMyItemsPanel() {
        super(TMISaveFile.favorites);
        this.grid.myItemsView = true;
    }
}