package net.bogismok.thedirtystuff.block.custom;

import com.mojang.serialization.MapCodec;
import net.bogismok.thedirtystuff.item.ModItems;
import net.minecraft.world.item.Item;

public class DriedTobaccoLeavesPalletBlock extends PalletBlock {
    public Item itemHolder() {
        return ModItems.DRIED_TOBACCO_LEAVES_PACKAGE.get();
    }

    public static final MapCodec<DriedTobaccoLeavesPalletBlock> CODEC = simpleCodec(DriedTobaccoLeavesPalletBlock::new);

    protected MapCodec<DriedTobaccoLeavesPalletBlock> codec() {
        return CODEC;
    }

    public DriedTobaccoLeavesPalletBlock(Properties pProperties) {
        super(pProperties);
    }
}
