package net.bogismok.thedirtystuff.block.custom;

import net.bogismok.thedirtystuff.item.ModItems;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.CropBlock;

public class TobaccoBlock extends CropBlock {
    public TobaccoBlock(Properties pProperties){
        super(pProperties);
    }
    @Override
    protected final ItemLike getBaseSeedId() {
        return ModItems.TOBACCO_SEEDS.get();
    }
    //idk if it's good if there's only this little code, but it works, pls don't hate on me
}
