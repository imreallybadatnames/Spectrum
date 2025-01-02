package de.dafuqs.spectrum.items;

import com.klikli_dev.modonomicon.client.gui.book.*;
import de.dafuqs.spectrum.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.item.tooltip.*;
import net.minecraft.stat.*;
import net.minecraft.text.*;
import net.minecraft.util.*;
import net.minecraft.world.*;

import java.util.*;

public class CookbookItem extends Item {
	
	public BookAddress bookAddress;
	private final int toolTipColor;
	
	public CookbookItem(Settings settings, BookAddress bookAddress) {
		this(settings, bookAddress, -1);
	}
	
	public CookbookItem(Settings settings, BookAddress bookAddress, int toolTipColor) {
		super(settings);
		this.bookAddress = bookAddress;
		this.toolTipColor = toolTipColor;
	}
	
	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		if (!world.isClient) {
			user.incrementStat(Stats.USED.getOrCreateStat(this));
			
			return TypedActionResult.success(user.getStackInHand(hand));
		} else {
			try {
				openGuidebookPage(this.bookAddress);
			} catch (NullPointerException e) {
				SpectrumCommon.logError(user.getName().getString() + " used a CookbookItem to open the guidebook page " + this.bookAddress + " but it does not exist");
			}
		}
		
		return TypedActionResult.consume(user.getStackInHand(hand));
	}
	
	private void openGuidebookPage(BookAddress address) {
		if (SpectrumItems.GUIDEBOOK instanceof GuidebookItem guidebook) {
			guidebook.openGuidebook(address);
		}
	}

	@Override
	public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
		super.appendTooltip(stack, context, tooltip, type);
		if (toolTipColor == -1) {
			tooltip.add(Text.translatable(this.getTranslationKey() + ".tooltip").formatted(Formatting.GRAY));
			return;
		}

		tooltip.add(Text.translatable(this.getTranslationKey() + ".tooltip").styled(s -> s.withColor(toolTipColor)));
	}
	
}
