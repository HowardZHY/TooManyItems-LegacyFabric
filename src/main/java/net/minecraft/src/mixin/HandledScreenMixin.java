package net.minecraft.src.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.slot.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.src.*;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;

@Mixin(HandledScreen.class)
public abstract class HandledScreenMixin extends Screen {

	// field_1347 -> backgroundWidth
	@Shadow protected int backgroundWidth;
	// field_1348 -> backgroundHeight
	@Shadow protected int backgroundHeight;
	// field_1349 -> screenHandler
	@Shadow public ScreenHandler screenHandler;

	// field_1350 -> x
	@Shadow
	protected int x;
	// field_1351 -> y
	@Shadow
	protected int y;

	@Shadow
	private Slot getSlotAt(int x, int y){
		return null;
	}

	@Inject( method = "<init>", at = @At( "TAIL" ) )
	public void onInit( ScreenHandler screenHandler, CallbackInfo ci ) {
		TMI.instance.controller.onCreate(handledScreen());
	}

	private HandledScreen handledScreen() {
		return (HandledScreen) (Object) (this);
	}

	public void handleMouse() {
		super.handleMouse();
		int i = Mouse.getEventX() * this.width / (Minecraft.getMinecraft()).width;
		int j = this.height - Mouse.getEventY() * this.height / (Minecraft.getMinecraft()).height - 1;
		TMI.instance.controller.handleScrollWheel(i, j);
	}

	@Inject( method = "render", at = @At( "HEAD" ) )
	public void onRenderStart( int mouseX, int mouseY, float tickDelta, CallbackInfo ci ) {
		this.x = ( this.width - this.backgroundWidth ) / 2;
		this.y = ( this.height - this.backgroundHeight ) / 2;
		if (handledScreen() instanceof net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen)
			try {
				TextFieldWidget textFieldWidget = (TextFieldWidget)TMIPrivateFields.creativeSearchBox.get(this);
				TMIPrivateFields.textFieldX.setInt(textFieldWidget, this.x + 82);
			} catch (Exception exception) {
				exception.printStackTrace();
			}
	}

	@Inject(
		method = "render",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/screen/ingame/HandledScreen;drawForeground(II)V"
		)
	)
	public void onRenderEnd( int mouseX, int mouseY, float tickDelta, CallbackInfo ci ) {
		GL11.glTranslatef(-this.x, -this.y, 0.0F);
		TMI.instance.controller.onEnterFrame(mouseX, mouseY, this.backgroundWidth, this.backgroundHeight );
		GL11.glTranslatef(this.x, this.y, 0.0F);
		GL11.glEnable(2896);
		GL11.glEnable(2929);
	}

	@Redirect(
			method = "method_1132",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/item/ItemStack;getTooltip(Lnet/minecraft/entity/player/PlayerEntity;Z)Ljava/util/List;"
			)
	)
	public List redirect(ItemStack paramItemStack, PlayerEntity instance, boolean advanced){
		return TMIUtils.itemDisplayNameMultiline(paramItemStack, TMIConfig.getInstance().isEnabled(), (Minecraft.getMinecraft()).options.advancedItemTooltips);
	}

	@Inject(
		method = "mouseClicked",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/Minecraft;getTime()J"
		),
		cancellable = true,
		locals = LocalCapture.CAPTURE_FAILHARD)
    public void onMouseClicked(int mouseX, int mouseY, int button, CallbackInfo ci) {
		Slot var5 = this.getSlotAt( mouseX, mouseY );
		boolean bool1 = (mouseX >= this.x && mouseY >= this.y && mouseX <= this.x + this.backgroundWidth && mouseY <= this.y + this.backgroundHeight);
		if (! TMI.instance.controller.onMouseEvent(mouseX, mouseY, button, bool1, var5, this.screenHandler)) {
			ci.cancel();
		}
	}

	@Inject( method = "keyPressed", at = @At("HEAD"), cancellable = true )
	public void onKeyPressed( char character, int code, CallbackInfo ci ) {
		if ( TMI.instance.controller.onKeypress( character, code ) )
			ci.cancel();
	}

	@Inject( method = "removed", at = @At("HEAD") )
	public void removed( CallbackInfo ci ) {
		TMI.instance.controller.onClose();
	}

	@Inject( method = "shouldPauseGame", at = @At("HEAD"))
	public void shouldPauseGame( CallbackInfoReturnable<Boolean> cir ) {
		TMI.instance.controller.shouldPauseGame();
	}

	// field_1230 -> width
	// field_1231 -> height
}