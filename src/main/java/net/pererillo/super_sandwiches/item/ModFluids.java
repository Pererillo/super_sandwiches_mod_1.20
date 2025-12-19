package net.pererillo.super_sandwiches.item;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
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

    // FluidType con texturas vanilla CORRECTAS
    public static final DeferredHolder<FluidType, FluidType> HOT_WATER_TYPE = FLUID_TYPES.register("hot_water",
            () -> new FluidType(FluidType.Properties.create()
                    .density(1000)
                    .viscosity(1000)
                    .temperature(350)
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
    public static final DeferredHolder<Block, LiquidBlock> HOT_WATER_BLOCK = BLOCKS.register("hot_water",
            () -> new LiquidBlock(HOT_WATER_SOURCE.get(),  // ← .get() aquí también
                    BlockBehaviour.Properties.ofFullCopy(Blocks.WATER)
                            .noCollission()      // Sin colisión (para que puedas nadar)
                            .strength(100.0F)    // Indestructible como el agua
                            .noLootTable()       // No dropea nada al romperse
                            .liquid()));         // Marca como líquido (importante)
}