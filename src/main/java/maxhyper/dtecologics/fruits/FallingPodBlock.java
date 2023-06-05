package maxhyper.dtecologics.fruits;

import com.ferreusveritas.dynamictrees.api.TreeHelper;
import com.ferreusveritas.dynamictrees.block.PodBlock;
import com.ferreusveritas.dynamictrees.block.branch.BranchBlock;
import com.ferreusveritas.dynamictrees.block.rooty.RootyBlock;
import com.ferreusveritas.dynamictrees.systems.pod.Pod;
import maxhyper.dtecologics.DynamicTreesEcologics;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Random;

public class FallingPodBlock extends PodBlock implements IFallingFruit {

    DamageSource damageSource;

    public static float randomFruitFallChance = 0.005f;
    public static float playerDistanceToFall = 10f;

    int fallDamageMax = 40;
    float fallDamageAmount = 2.0F;

    public FallingPodBlock(Properties properties, Pod pod) {
        super(properties, pod);
        damageSource = new DamageSource(DynamicTreesEcologics.MOD_ID+".falling_fruit."+ pod.getRegistryName().getPath());
    }

    @Override
    public boolean isSupported(LevelReader world, BlockPos pos, BlockState state) {
        final BlockState branchState = world.getBlockState(pos.relative(state.getValue(HorizontalDirectionalBlock.FACING)));
        final BranchBlock branch = TreeHelper.getBranch(branchState);
        return branch != null && branch.getRadius(branchState) == 3;
    }

    @Override
    public void doTick(BlockState state, Level world, BlockPos pos, Random random) {
        if (checkToFall(state, world, pos, random)){
            //System.out.println(this.asItem());
            doFall(state, world, pos);
        } else
            super.doTick(state, world, pos, random);
    }

    @Override
    public void neighborChanged(BlockState state, Level world, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
        if (!isSupported(world, pos, state)) {
            if (!doFall(state, world, pos))
                super.neighborChanged(state, world, pos, block, fromPos, isMoving);
        }
        //DebugPacketSender.sendNeighborsUpdatePacket(world, pos);
    }

    @Override
    public DamageSource getDamageSource() {
        return damageSource;
    }

    @Override
    public int getRootY(BlockState state, Level world, BlockPos pos) {
        Direction dir = state.getValue(FallingPodBlock.FACING);
        for (int i=0;i<20;i++){
            BlockPos pos2 = pos.offset(dir.getNormal()).below(i);
            if (world.getBlockState(pos2).getBlock() instanceof RootyBlock){
                return pos2.getY();
            }
        }
        return pos.getY();
    }

    @Override
    public ItemStack getDropOnFallItems(ItemLike item, @Nonnull FallingBlockEntity entity) {
        if (entity.getServer() == null) return ItemStack.EMPTY;
        ServerLevel world = entity.getServer().getLevel(entity.level.dimension());
        if (world == null) return ItemStack.EMPTY;
        List<ItemStack> drops = getDrops(entity.getBlockState(), world, entity.blockPosition(), null);
        if (drops.isEmpty()) return ItemStack.EMPTY;
        return drops.get(0);
    }

    public float getRandomFruitFallChance() {
        return randomFruitFallChance;
    }

    @Override
    public float getPlayerDistanceToFall() {
        return playerDistanceToFall;
    }
}
