package net.pererillo.super_sandwiches.item;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvents;
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
                    .density(1000)
                    .viscosity(1000)
                    .temperature(350)  // Caliente
                    .canPushEntity(true)      // Empuja entidades (movimiento lento)
                    .motionScale(0.014F)      // Velocidad de movimiento como agua
                    .canHydrate(true)         // Puede hidratar cultivos
                    .canConvertToSource(true) // Puede convertirse en fuente
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
                            .strength(100.0F)
                            .noLootTable()
                            .liquid()));

    public static class HotWaterBlock extends LiquidBlock {
        public HotWaterBlock(FlowingFluid fluid, BlockBehaviour.Properties properties) {
            super(fluid, properties);
        }

        @Override
        public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
            super.entityInside(state, level, pos, entity);

            if (!level.isClientSide && entity instanceof LivingEntity living) {
                if (living.tickCount % 20 == 0) {  // Cada segundo
                    living.hurt(level.damageSources().hotFloor(), 0.5F);  // Medio corazón de daño
                }
            }
        }
    }
}