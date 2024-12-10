package de.dafuqs.spectrum.inventories;

import de.dafuqs.spectrum.*;
import de.dafuqs.spectrum.helpers.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.screen.ingame.*;
import net.minecraft.client.gui.widget.*;
import net.minecraft.entity.player.*;
import net.minecraft.text.*;
import net.minecraft.util.*;

public class CompactingChestScreen extends HandledScreen<CompactingChestScreenHandler> {
	
	public static final Identifier BACKGROUND = SpectrumCommon.locate("textures/gui/container/compacting_chest.png");

	public CompactingChestScreen(CompactingChestScreenHandler handler, PlayerInventory playerInventory, Text title) {
		super(handler, playerInventory, title);
		this.backgroundHeight = 178;
	}
	
	@Override
	protected void init() {
		super.init();
		
		//client.keyboard.setRepeatEvents(true);
		setupInputFields();
	}
	
	protected void setupInputFields() {
		int x = (this.width - this.backgroundWidth) / 2 + 3;
		int y = (this.height - this.backgroundHeight) / 2 + 3;
		
		ButtonWidget craftingModeButton = ButtonWidget.builder(Text.literal("Mode"), this::craftingModeButtonPressed)
				.size(16, 16)
				.position(x + 154, y + 6)
				.build();
		//new ButtonWidget(x + 154, y + 6, 16, 16, Text.literal("Mode"), this::craftingModeButtonPressed);
		addSelectableChild(craftingModeButton);
	}
	
	private void craftingModeButtonPressed(ButtonWidget buttonWidget) {
		handler.toggleMode();
	}
	
	@Override
	protected void drawForeground(DrawContext drawContext, int mouseX, int mouseY) {
		// draw "title" and "inventory" texts
		int titleX = (backgroundWidth - textRenderer.getWidth(title)) / 2; // 8;
		int titleY = 6;
		Text title = this.title;
		int inventoryX = 8;
		int intInventoryY = 83;

		drawContext.drawText(this.textRenderer, title, titleX, titleY, RenderHelper.GREEN_COLOR, false);
		drawContext.drawText(this.textRenderer, this.playerInventoryTitle, inventoryX, intInventoryY, RenderHelper.GREEN_COLOR, false);
	}
	
	@Override
	protected void drawBackground(DrawContext drawContext, float delta, int mouseX, int mouseY) {
		int x = (width - backgroundWidth) / 2;
		int y = (height - backgroundHeight) / 2;
		drawContext.drawTexture(BACKGROUND, x, y, 0 ,0, backgroundWidth, backgroundHeight);

		// the selected crafting mode
		drawContext.drawTexture(BACKGROUND, x + 154, y + 6, 176, 16 * handler.getCraftingMode().ordinal(), 16, 16);
	}
	
	@Override
	public void render(DrawContext drawContext, int mouseX, int mouseY, float delta) {
		renderBackground(drawContext, mouseX, mouseY, delta);
		super.render(drawContext, mouseX, mouseY, delta);
		
		if (mouseX > x + 153 && mouseX < x + 153 + 16 && mouseY > y + 5 && mouseY < y + 5 + 16) {
			drawContext.drawTooltip(this.textRenderer, Text.translatable("block.spectrum.compacting_chest.toggle_crafting_mode"), mouseX, mouseY);
		} else {
			drawMouseoverTooltip(drawContext, mouseX, mouseY);
		}
	}
	
}