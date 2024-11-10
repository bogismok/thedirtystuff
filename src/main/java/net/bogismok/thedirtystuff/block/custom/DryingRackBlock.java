package net.bogismok.thedirtystuff.block.custom;

import com.mojang.serialization.MapCodec;
import net.bogismok.thedirtystuff.block.entity.custom.DryingRackBlockEntity;
import net.bogismok.thedirtystuff.block.entity.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class DryingRackBlock extends BaseEntityBlock {
    public static final MapCodec<DryingRackBlock> CODEC = simpleCodec(DryingRackBlock::new);

    public static final VoxelShape SHAPE = Shapes.or(
            Block.box(0.0, 15.0, 0.0, 16.0, 16.0, 16.0),
            Block.box(15.0, 0.0, 0.0, 16.0, 15.0, 1.0),
            Block.box(0.0, 0.0, 15.0, 1.0, 15.0, 16.0),
            Block.box(15.0, 0.0, 15.0, 16.0, 15.0, 16.0),
            Block.box(0.0, 0.0, 0.0, 1.0, 15.0, 1.0)
    );

    public DryingRackBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    protected MapCodec<DryingRackBlock> codec() {
        return CODEC;
    }

    protected float getShadeBrightness(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return 1.0F;
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    protected RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new DryingRackBlockEntity(blockPos, blockState);
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (pState.getBlock() != pNewState.getBlock()) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof DryingRackBlockEntity dryingRackBlockEntity) {
                dryingRackBlockEntity.drops();
            }
        }

        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, BlockHitResult pHitResult) {
        if (!pLevel.isClientSide()) {
            BlockEntity entity = pLevel.getBlockEntity(pPos);
            if(entity instanceof DryingRackBlockEntity dryingRackBlockEntity) {
                ((ServerPlayer) pPlayer).openMenu(new SimpleMenuProvider(dryingRackBlockEntity, Component.literal("Drying Rack")), pPos);
            } else {
                throw new IllegalStateException("Our Container provider is missing!");
            }
        }

        return InteractionResult.sidedSuccess(pLevel.isClientSide());
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        if(pLevel.isClientSide()) {
            return null;
        }

        return createTickerHelper(pBlockEntityType, ModBlockEntities.DRYING_RACK_BE.get(),
                (level, blockPos, blockState, dryingRackBlockEntity) -> dryingRackBlockEntity.tick(level, blockPos, blockState));
    }

}
