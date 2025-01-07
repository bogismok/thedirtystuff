package net.bogismok.thedirtystuff.block.custom;

import com.mojang.serialization.MapCodec;
import net.bogismok.thedirtystuff.item.ModItems;
import net.minecraft.world.item.Item;

public class TobaccoLeavesPalletBlock extends PalletBlock {
    public Item itemHolder() {
        return ModItems.TOBACCO_LEAVES_PACKAGE.get();
    }

    public static final MapCodec<TobaccoLeavesPalletBlock> CODEC = simpleCodec(TobaccoLeavesPalletBlock::new);

    protected MapCodec<TobaccoLeavesPalletBlock> codec() {
        return CODEC;
    }

    public TobaccoLeavesPalletBlock(Properties pProperties) {
        super(pProperties);
    }
}
