package maxhyper.dtecologics.genfeatures;

import com.ferreusveritas.dynamictrees.api.TreeHelper;
import com.ferreusveritas.dynamictrees.api.configuration.ConfigurationProperty;
import com.ferreusveritas.dynamictrees.compat.season.SeasonHelper;
import com.ferreusveritas.dynamictrees.systems.genfeature.GenFeature;
import com.ferreusveritas.dynamictrees.systems.genfeature.GenFeatureConfiguration;
import com.ferreusveritas.dynamictrees.systems.genfeature.context.PostGenerationContext;
import com.ferreusveritas.dynamictrees.systems.genfeature.context.PostGrowContext;
import com.ferreusveritas.dynamictrees.systems.pod.Pod;
import com.ferreusveritas.dynamictrees.util.CoordUtils;
import com.ferreusveritas.dynamictrees.util.LevelContext;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.LeavesBlock;
import org.jetbrains.annotations.NotNull;

public class PalmFruitGenFeature extends GenFeature {

    public static final ConfigurationProperty<Pod> POD =
            ConfigurationProperty.property("pod", Pod.class);

    public static final ConfigurationProperty<Integer> EXPAND_UP_FRUIT_HEIGHT = ConfigurationProperty.integer("expand_up_fruit_height");
    public static final ConfigurationProperty<Integer> EXPAND_DOWN_FRUIT_HEIGHT = ConfigurationProperty.integer("expand_down_fruit_height");

    public PalmFruitGenFeature(ResourceLocation registryName) {
        super(registryName);
    }

    @Override
    protected void registerProperties() {
        this.register(POD, QUANTITY, FRUITING_RADIUS, PLACE_CHANCE, EXPAND_UP_FRUIT_HEIGHT, EXPAND_DOWN_FRUIT_HEIGHT);
    }

    @Override
    public @NotNull GenFeatureConfiguration createDefaultConfiguration() {
        return new GenFeatureConfiguration(this)
                .with(POD, Pod.NULL)
                .with(QUANTITY, 8)
                .with(FRUITING_RADIUS, 6)
                .with(PLACE_CHANCE, 0.25f)
                .with(EXPAND_UP_FRUIT_HEIGHT, 0)
                .with(EXPAND_DOWN_FRUIT_HEIGHT, 0);
    }

    @Override
    protected boolean postGrow(GenFeatureConfiguration configuration, PostGrowContext context) {
        final LevelContext levelContext = context.levelContext();
        final LevelAccessor level = context.level();
        final BlockPos rootPos = context.pos();
        if ((TreeHelper.getRadius(level, rootPos.above()) >= configuration.get(FRUITING_RADIUS)) && context.natural() &&
                level.getRandom().nextInt() % 16 == 0) {
            if (context.species().seasonalFruitProductionFactor(LevelContext.create(level), rootPos) > level.getRandom().nextFloat()) {
                addFruit(configuration, levelContext, rootPos, getLeavesHeight(rootPos, level).below(), false);
                return true;
            }
        }
        return false;
    }

    private BlockPos getLeavesHeight(BlockPos rootPos, LevelAccessor level) {
        for (int y = 1; y < 20; y++) {
            BlockPos testPos = rootPos.above(y);
            if ((level.getBlockState(testPos).getBlock() instanceof LeavesBlock)) {
                return testPos;
            }
        }
        return rootPos;
    }

    @Override
    protected boolean postGenerate(GenFeatureConfiguration configuration, PostGenerationContext context) {
        final LevelContext level = context.levelContext();
        final BlockPos rootPos = context.pos();
        boolean placed = false;
        int qty = configuration.get(QUANTITY);
        qty *= context.fruitProductionFactor();
        for (int i = 0; i < qty; i++) {
            if (!context.endPoints().isEmpty() && level.accessor().getRandom().nextFloat() <= configuration.get(PLACE_CHANCE)) {
                addFruit(configuration, level, rootPos, context.endPoints().get(0), true);
                placed = true;
            }
        }
        return placed;
    }

    protected void addFruit(GenFeatureConfiguration configuration, LevelContext level, BlockPos rootPos, BlockPos leavesPos, boolean worldGen) {
        if (rootPos.getY() == leavesPos.getY()) return;
        Direction placeDir = CoordUtils.HORIZONTALS[level.accessor().getRandom().nextInt(4)];
        BlockPos pos = expandRandom(configuration, level, leavesPos.offset(placeDir.getNormal()));
        if (level.accessor().getBlockState(pos).getMaterial().isReplaceable()) {
            Float seasonValue = SeasonHelper.getSeasonValue(level, rootPos);
            Pod pod = configuration.get(POD);
            if (worldGen) {
                pod.placeDuringWorldGen(level.accessor(), pos, seasonValue, placeDir.getOpposite(), 8);
            } else {
                pod.place(level.accessor(), pos, seasonValue, placeDir.getOpposite(), 8);
            }
        }
    }

    protected BlockPos expandRandom(GenFeatureConfiguration configuration, LevelContext level, BlockPos startingPos){
        int fullHeight = 1+configuration.get(EXPAND_UP_FRUIT_HEIGHT)+configuration.get(EXPAND_DOWN_FRUIT_HEIGHT);
        return startingPos.below(configuration.get(EXPAND_DOWN_FRUIT_HEIGHT))
                .above(level.accessor().getRandom().nextInt(fullHeight));
    }

}