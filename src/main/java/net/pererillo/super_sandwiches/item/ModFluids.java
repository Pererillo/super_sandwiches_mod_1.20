package net.pererillo.super_sandwiches.item;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.common.SoundActions;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.pererillo.super_sandwiches.SandwichMod;

public class ModFluids {

    public static final DeferredRegister<FluidType> FLUID_TYPES =
            DeferredRegister.create(NeoForgeRegistries.Keys.FLUID_TYPES, SandwichMod.MODID);

    public static final DeferredRegister<Fluid> FLUIDS =
            DeferredRegister.create(BuiltInRegistries.FLUID, SandwichMod.MODID);

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.createBlocks(SandwichMod.MODID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.createItems(SandwichMod.MODID);

    // FluidType con texturas vanilla
    public static final DeferredHolder<FluidType, FluidType> HOT_WATER_TYPE = FLUID_TYPES.register("hot_water",
            () -> new FluidType(FluidType.Properties.create()
                    .canExtinguish(true)
                    .density(1000)
                    .viscosity(500)
                    .temperature(1300)
                    .canPushEntity(true)      // Empuja entidades
                    .motionScale(0.014F)      // Velocidad de movimiento
                    .canHydrate(true)         // Puede hidratar cultivos
                    .canConvertToSource(false) // Si puede convertirse en fuente
                    .supportsBoating(true)    // Permite barcos
                    .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL)
                    .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY)
            ));

    // Source y Flowing
    public static final DeferredHolder<Fluid, FlowingFluid> HOT_WATER_SOURCE = FLUIDS.register("hot_water",
            () -> new BaseFlowingFluid.Source(makeProperties()));

    public static final DeferredHolder<Fluid, FlowingFluid> HOT_WATER_FLOWING = FLUIDS.register("flowing_hot_water",
            () -> new BaseFlowingFluid.Flowing(makeProperties()));

    private static BaseFlowingFluid.Properties makeProperties() {
        return new BaseFlowingFluid.Properties(
                HOT_WATER_TYPE, HOT_WATER_SOURCE, HOT_WATER_FLOWING)
                .bucket(HOT_WATER_BUCKET)
                .block(HOT_WATER_BLOCK)
                .slopeFindDistance(4)
                .levelDecreasePerBlock(1)
                .tickRate(5);
    }

    public static final DeferredHolder<Item, BucketItem> HOT_WATER_BUCKET = ITEMS.register("hot_water_bucket",
            () -> new BucketItem(HOT_WATER_SOURCE.get(),
                    new Item.Properties()
                            .craftRemainder(Items.BUCKET)
                            .stacksTo(1)));


    public static final DeferredHolder<Block, HotWaterBlock> HOT_WATER_BLOCK = BLOCKS.register("hot_water",
            () -> new HotWaterBlock(HOT_WATER_SOURCE.get(),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.WATER)
                            .noCollission()
                            .strength(50.0F)
                            .noLootTable()
                            .liquid()));

    public static class HotWaterBlock extends LiquidBlock {
        public HotWaterBlock(FlowingFluid fluid, BlockBehaviour.Properties properties) {
            super(fluid, properties);
        }

        // EVAPORACIÓN EN LAVA
        private void tryEvaporate(Level level, BlockPos pos) {
            if (level.isClientSide) return;

            for (Direction dir : Direction.values()) {
                BlockPos neighborPos = pos.relative(dir);
                if (level.getFluidState(neighborPos).is(FluidTags.LAVA)) {
                    ServerLevel serverLevel = (ServerLevel) level;
                    RandomSource rand = serverLevel.random;

                    level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);

                    double x = pos.getX() + 0.5;
                    double y = pos.getY() + 0.5;
                    double z = pos.getZ() + 0.5;
                    serverLevel.sendParticles(ParticleTypes.LARGE_SMOKE, x, y, z, 40, 0.5, 0.8, 0.5, 0.1);
                    serverLevel.playSound(null, pos, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 2.0F, 0.7F + rand.nextFloat() * 0.6F);
                    level.scheduleTick(neighborPos, level.getFluidState(neighborPos).getType(), 1);
                    return;
                }
            }
        }

        // DERRITE GRADUAL EN UN RANGO DE 3 (vanilla-like)
        private void tryMeltNearby(ServerLevel level, BlockPos pos, RandomSource rand) {
            if (rand.nextFloat() < 0.6F) {  // Ajustar para más/menos velocidad
                int range = 3;
                for (int i = 0; i < 15; ++i) {
                    BlockPos meltPos = pos.offset(
                            rand.nextInt(range * 2 + 1) - range,
                            rand.nextInt(range * 2 + 1) - range,
                            rand.nextInt(range * 2 + 1) - range
                    );

                    BlockState meltState = level.getBlockState(meltPos);
                    if (meltState.is(Blocks.SNOW) || meltState.is(Blocks.SNOW_BLOCK)) {
                        level.setBlock(meltPos, Blocks.AIR.defaultBlockState(), 2);
                    } else if (meltState.is(Blocks.ICE) || meltState.is(Blocks.BLUE_ICE) ||
                            meltState.is(Blocks.PACKED_ICE) || meltState.is(Blocks.FROSTED_ICE)) {
                        level.setBlock(meltPos, Blocks.WATER.defaultBlockState(), 2);
                    }
                }
            }
        }

        @Override
        public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource rand) {
            tryEvaporate(level, pos);
            if (level.getBlockState(pos).is(this)) {
                super.tick(state, level, pos, rand);

                if (state.getValue(LEVEL) == 0) {
                    tryMeltNearby(level, pos, rand);
                    level.scheduleTick(pos, this, 40);  // Cada segundo con el source quieto
                }
            }
        }

        @Override
        public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean moving) {
            super.onPlace(state, level, pos, oldState, moving);
            tryEvaporate(level, pos);

            if (state.getValue(LEVEL) == 0) {
                level.scheduleTick(pos, this, 20);
            }
        }

        @Override
        public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos neighborPos, boolean moving) {
            super.neighborChanged(state, level, pos, block, neighborPos, moving);
            tryEvaporate(level, pos);
        }

        // PARTÍCULAS + SONIDO BURBUJEO (client-side)
        @Override
        public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource rand) {
            super.animateTick(state, level, pos, rand);

            if (state.getValue(LEVEL) == 0 && level.isClientSide) {
                // Burbujas pequeñas
                double x = pos.getX() + rand.nextDouble();
                double y = pos.getY() + rand.nextDouble();
                double z = pos.getZ() + rand.nextDouble();
                level.addParticle(ParticleTypes.BUBBLE, x, y, z, 0.0, 0.04 + rand.nextDouble() * 0.04, 0.0);

                // Vapor leve
                level.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, x, y + 0.1, z, 0.0, 0.02 + rand.nextDouble() * 0.02, 0.0);

                // Sonido burbujeo sutil cada ~4 seg
                if (rand.nextInt(5) == 0) {
                    level.playLocalSound(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                            SoundEvents.BUBBLE_COLUMN_BUBBLE_POP, SoundSource.BLOCKS,
                            0.3F + rand.nextFloat() * 0.2F, 0.8F + rand.nextFloat() * 0.4F, false);
                }
            }

            if (state.getValue(LEVEL) == (rand.nextInt(6+1)) && level.isClientSide) {
                // Burbujas pequeñas
                double x = pos.getX() + rand.nextDouble();
                double y = pos.getY() + rand.nextDouble();
                double z = pos.getZ() + rand.nextDouble();
                level.addParticle(ParticleTypes.BUBBLE, x, y, z, 0.0, 0.04 + rand.nextDouble() * 0.04, 0.0);

                // Vapor leve

                // Sonido burbujeo sutil
                if (rand.nextInt(20) == 0) {
                    level.playLocalSound(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                            SoundEvents.BUBBLE_COLUMN_BUBBLE_POP, SoundSource.BLOCKS,
                            0.3F + rand.nextFloat() * 0.2F, 0.8F + rand.nextFloat() * 0.4F, false);
                }
            }
        }

        @Override
        public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
            super.entityInside(state, level, pos, entity);

            if (!level.isClientSide && entity instanceof LivingEntity living && living.tickCount % 20 == 0) {
                living.hurt(level.damageSources().hotFloor(), 1.0F);
            }
        }
    }
}