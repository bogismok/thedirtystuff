package net.bogismok.thedirtystuff.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public abstract class PalletBlock extends Block {
    public abstract Item itemHolder();
    public static final Property<Integer> LEVEL = IntegerProperty.create("level", 0, 15);
    private static final VoxelShape[] SHAPES = new VoxelShape[16];

    static {
        VoxelShape shape = Shapes.empty();
        shape = Shapes.or(shape, Shapes.box(0, 0.0625, 0, 1, 0.125, 1));
        shape = Shapes.or(shape, Shapes.box(0.875, 0, 0.875, 1, 0.0625, 1));
        shape = Shapes.or(shape, Shapes.box(0.875, 0, 0, 1, 0.0625, 0.125));
        shape = Shapes.or(shape, Shapes.box(0, 0, 0, 0.125, 0.0625, 0.125));
        shape = Shapes.or(shape, Shapes.box(0, 0, 0.875, 0.125, 0.0625, 1));
        shape = Shapes.or(shape, Shapes.box(0, 0.125, 0, 0.5, 0.34375, 0.5));
        SHAPES[0] = shape;

        for (int i = 1; i < 16; i++) {
            VoxelShape tempShape = SHAPES[i - 1];
            tempShape = Shapes.or(tempShape, Shapes.box(0.5, 0.125, 0, 1, 0.34375, 0.5));
            if (i >= 2) tempShape = Shapes.or(tempShape, Shapes.box(0.5, 0.125, 0.5, 1, 0.34375, 1));
            if (i >= 3) tempShape = Shapes.or(tempShape, Shapes.box(0, 0.125, 0.5, 0.5, 0.34375, 1));
            if (i >= 4) tempShape = Shapes.or(tempShape, Shapes.box(0, 0.34375, 0.5, 0.5, 0.5625, 1));
            if (i >= 5) tempShape = Shapes.or(tempShape, Shapes.box(0.5, 0.34375, 0.5, 1, 0.5625, 1));
            if (i >= 6) tempShape = Shapes.or(tempShape, Shapes.box(0.5, 0.34375, 0, 1, 0.5625, 0.5));
            if (i >= 7) tempShape = Shapes.or(tempShape, Shapes.box(0, 0.34375, 0, 0.5, 0.5625, 0.5));
            if (i >= 8) tempShape = Shapes.or(tempShape, Shapes.box(0, 0.5625, 0, 0.5, 0.78125, 0.5));
            if (i >= 9) tempShape = Shapes.or(tempShape, Shapes.box(0.5, 0.5625, 0, 1, 0.78125, 0.5));
            if (i >= 10) tempShape = Shapes.or(tempShape, Shapes.box(0.5, 0.5625, 0.5, 1, 0.78125, 1));
            if (i >= 11) tempShape = Shapes.or(tempShape, Shapes.box(0, 0.5625, 0.5, 0.5, 0.78125, 1));
            if (i >= 12) tempShape = Shapes.or(tempShape, Shapes.box(0, 0.78125, 0.5, 0.5, 1, 1));
            if (i >= 13) tempShape = Shapes.or(tempShape, Shapes.box(0, 0.78125, 0, 0.5, 1, 0.5));
            if (i >= 14) tempShape = Shapes.or(tempShape, Shapes.box(0.5, 0.78125, 0, 1, 1, 0.5));
            if (i == 15) tempShape = Shapes.or(tempShape, Shapes.box(0.5, 0.78125, 0.5, 1, 1, 1));
            SHAPES[i] = tempShape;
        }
    }

    @Override
    protected VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        int stateValue = pState.getValue(LEVEL);
        return SHAPES[stateValue];
    }

    public PalletBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(LEVEL, 0));
    }

    protected float getShadeBrightness(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return 1;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(LEVEL);
    }

    @Override
    protected RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack pStack, BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHitResult) {
        int stateValue = pState.getValue(LEVEL);
        if(itemHolder() != null) {
            if(pStack.getItem() == itemHolder() && stateValue < 15) {
                pStack.consume(1, pPlayer);
                pLevel.setBlockAndUpdate(pPos, pState.setValue(LEVEL, stateValue + 1));
                return ItemInteractionResult.SUCCESS;
            }
            else if(pStack.isEmpty()) {
                if(!pPlayer.isCreative()){
                    pPlayer.addItem(new ItemStack(itemHolder()));
                }
                if(stateValue != 0){
                    pLevel.setBlockAndUpdate(pPos, pState.setValue(LEVEL, stateValue - 1));
                }
                else {
                    pLevel.removeBlock(pPos, false);
                }
                return ItemInteractionResult.SUCCESS;
            }
        }
        return ItemInteractionResult.FAIL;
    }
}
