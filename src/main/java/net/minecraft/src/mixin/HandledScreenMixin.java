package net.minecraft.src.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.inventory.slot.Slot;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HandledScreen.class)
public abstract class HandledScreenMixin extends Screen {

	@Shadow public int x;

	@Shadow public int y;

	@Shadow public ScreenHandler screenHandler;
	@Shadow private Slot lastClickedSlot;
	@Shadow private long lastButtonClickTime;
	@Shadow private int lastClickedButton;
	@Shadow protected abstract Slot getSlotAt( int x, int y );
	@Shadow protected int backgroundWidth;
	@Shadow protected int backgroundHeight;

	@Inject( method = "<init>", at = @At( "TAIL" ) )
	public void onInit( ScreenHandler screenHandler, CallbackInfo ci ) {
		TMI.instance.controller.onCreate((HandledScreen) (Object) this);
	}

	public void handleMouse() {
		TMI.instance.controller.onMouseEvent();
		super.handleMouse();
	}


	@Inject( method = "render", at = @At( "HEAD" ) )
	public void onRenderStart( int mouseX, int mouseY, float tickDelta, CallbackInfo ci ) {
		this.x = ( this.width - this.backgroundWidth ) / 2;
		this.y = ( this.height - this.backgroundHeight ) / 2;
		TMI.instance.controller.frameStart( mouseX, mouseY, this.x, this.y, this.backgroundWidth, this.backgroundHeight );
	}

	@Inject(
		method = "render",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/render/DiffuseLighting;disable()V",
			shift = At.Shift.BEFORE,
			ordinal = 1
		)
	)
	public void onRenderEnd( int mouseX, int mouseY, float tickDelta, CallbackInfo ci ) {
		TMI.instance.controller.frameEnd( mouseX, mouseY, this.x, this.y, this.backgroundWidth, this.backgroundHeight );
	}

	@Inject(
		method = "mouseClicked",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/screen/ingame/HandledScreen;getSlotAt(II)Lnet/minecraft/inventory/slot/Slot;",
			shift = At.Shift.BEFORE
		),
		cancellable = true
	)
	public void onMouseClicked( int mouseX, int mouseY, int button, CallbackInfo ci ) {
		if (! TMI.instance.controller.onClick(mouseX, mouseY, button, this.x, this.y, this.screenHandler)) {
			this.lastClickedSlot = this.getSlotAt( mouseX, mouseY );
			this.lastButtonClickTime = MinecraftClient.getTime();
			this.lastClickedButton = button;
			ci.cancel();
		}
	}

	@Inject( method = "keyPressed", at = @At("HEAD"), cancellable = true )
	public void onKeyPressed( char character, int code, CallbackInfo ci ) {
		if (! TMI.instance.controller.onKeypress( character, code ) )
			ci.cancel();
	}

	@Inject( method = "removed", at = @At("HEAD") )
	public void onremoved( CallbackInfo ci ) {
		TMI.instance.controller.onClose();
	}

	@Inject( method = "shouldPauseGame", at = @At("HEAD"), cancellable = true)
	public void onremoved( CallbackInfoReturnable<Boolean> cir ) {
		cir.setReturnValue( TMI.instance.controller.shouldPauseGame() );
	}

	// field_1230 -> width
	// field_1231 -> height
	// field_1347 -> backgroundWidth
	// field_1348 -> backgroundHeight
	// field_1349 -> screenHandler
	// field_1350 -> x
	// field_1351 -> y
}