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
import net.minecraft.world.level.material.FluidState;
import net.neoforged.neoforge.common.SoundActions;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.pererillo.super_sandwiches.SandwichMod;

import java.util.function.Supplier;

public class ModFluids {

    public static final DeferredRegister<FluidType> FLUID_TYPES =
            DeferredRegister.create(NeoForgeRegistries.Keys.FLUID_TYPES, SandwichMod.MODID);

    public static final DeferredRegister<Fluid> FLUIDS =
            DeferredRegister.create(BuiltInRegistries.FLUID, SandwichMod.MODID);

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.createBlocks(SandwichMod.MODID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.createItems(SandwichMod.MODID);

    // FluidType con texturas vanilla CORRECTAS
    public static final DeferredHolder<FluidType, FluidType> HOT_WATER_TYPE = FLUID_TYPES.register("hot_water",
            () -> new FluidType(FluidType.Properties.create()
                    .canExtinguish(true)
                    .density(1000)
                    .viscosity(500)
                    .temperature(350)  // Caliente
                    .canPushEntity(true)      // Empuja entidades (movimiento lento)
                    .motionScale(0.014F)      // Velocidad de movimiento como agua
                    .canHydrate(true)         // Puede hidratar cultivos
                    .canConvertToSource(false) // Puede convertirse en fuente
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
            () -> new BucketItem(HOT_WATER_SOURCE.get(),  // ← .get() aquí
                    new Item.Properties()
                            .craftRemainder(Items.BUCKET)
                            .stacksTo(1)));

    // BLOQUE: Pasa el DeferredHolder directamente (actúa como Supplier)
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

        private void tryEvaporate(Level level, BlockPos pos) {
            if (level.isClientSide) return;

            for (Direction dir : Direction.values()) {
                BlockPos neighborPos = pos.relative(dir);
                if (level.getFluidState(neighborPos).is(FluidTags.LAVA)) {
                    ServerLevel serverLevel = (ServerLevel) level;
                    RandomSource rand = serverLevel.random;

                    // Evapora hot_water
                    level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);

                    // Vapor épico
                    double x = pos.getX() + 0.5;
                    double y = pos.getY() + 0.5;
                    double z = pos.getZ() + 0.5;
                    serverLevel.sendParticles(ParticleTypes.LARGE_SMOKE, x, y, z, 10, 0.5, 0.8, 0.5, 0.1);

                    // Sonido explosivo
                    serverLevel.playSound(null, pos, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 1.0F, 0.7F + rand.nextFloat() * 0.6F);

                    // Fuerza update en lava para "limpiar" cualquier reacción pendiente
                    level.scheduleTick(neighborPos, level.getFluidState(neighborPos).getType(), 1);

                    return;
                }
            }
        }

        @Override
        public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource rand) {
            tryEvaporate(level, pos);
            if (level.getBlockState(pos).is(this)) {  // Si no se evaporó, flujo normal
                super.tick(state, level, pos, rand);
            }
        }

        @Override
        public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos neighborPos, boolean moving) {
            super.neighborChanged(state, level, pos, block, neighborPos, moving);
            tryEvaporate(level, pos);
        }

        @Override
        public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean moving) {
            super.onPlace(state, level, pos, oldState, moving);
            tryEvaporate(level, pos);
            level.scheduleTick(pos, this, 5);
        }

        @Override
        public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
            super.entityInside(state, level, pos, entity);
            if (!level.isClientSide && entity instanceof LivingEntity living && living.tickCount % 20 == 0) {
                living.hurt(level.damageSources().hotFloor(), 0.5F);
            }
        }
    }
}