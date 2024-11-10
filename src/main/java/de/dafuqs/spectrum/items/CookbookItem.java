package de.dafuqs.spectrum.items;

import de.dafuqs.spectrum.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.stat.*;
import net.minecraft.text.*;
import net.minecraft.util.*;
import net.minecraft.world.*;

import java.util.*;

public class CookbookItem extends Item {
	
	public String guidebookPageToOpen;
	private final int toolTipColor;
	
	public CookbookItem(Settings settings, String guidebookPageToOpen) {
		this(settings, guidebookPageToOpen, -1);
	}

	public CookbookItem(Settings settings, String guidebookPageToOpen, int toolTipColor) {
		super(settings);
		this.guidebookPageToOpen = guidebookPageToOpen;
		this.toolTipColor = toolTipColor;
	}
	
	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		if (!world.isClient) {
			user.incrementStat(Stats.USED.getOrCreateStat(this));
			
			return TypedActionResult.success(user.getStackInHand(hand));
		} else {
			try {
				openGuidebookPage(SpectrumCommon.locate(guidebookPageToOpen), 0);
			} catch (NullPointerException e) {
				SpectrumCommon.logError(user.getName().getString() + " used a CookbookItem to open the guidebook page " + this.guidebookPageToOpen + " but it does not exist");
			}
		}
		
		return TypedActionResult.consume(user.getStackInHand(hand));
	}
	
	private void openGuidebookPage(Identifier entry, int page) {
		if (SpectrumItems.GUIDEBOOK instanceof GuidebookItem guidebook) {
			guidebook.openGuidebook(entry, page);
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
