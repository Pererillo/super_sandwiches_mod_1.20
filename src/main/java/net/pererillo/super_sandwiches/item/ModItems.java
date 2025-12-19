package net.pererillo.super_sandwiches.item;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredItem;
import net.pererillo.super_sandwiches.SandwichMod;
import net.pererillo.super_sandwiches.item.ModFluids;

public class ModItems {
    public static final DeferredRegister.Items ITEMS =
            DeferredRegister.createItems(SandwichMod.MODID);

    public static final DeferredItem<Item> PORK_SANDWICH = ITEMS.register("pork_sandwich",
            () -> new Item(new Item.Properties().food(ModFoods.PORK_SANDWICH)));
    public static final DeferredItem<Item> SUPER_PORK_SANDWICH = ITEMS.register("super_pork_sandwich",
            () -> new Item(new Item.Properties().food(ModFoods.SUPER_PORK_SANDWICH)));
    public static final DeferredItem<Item> FLAVORED_PORKCHOP = ITEMS.register("flavored_porkchop",
            () -> new Item(new Item.Properties().food(ModFoods.FLAVORED_PORKCHOP)));
    public static final DeferredItem<Item> FLAVORED_BEEF = ITEMS.register("flavored_beef",
            () -> new Item(new Item.Properties().food(ModFoods.FLAVORED_BEEF)));
    public static final DeferredItem<Item> FLAVORED_CHICKEN = ITEMS.register("flavored_chicken",
            () -> new Item(new Item.Properties().food(ModFoods.FLAVORED_CHICKEN)));
    public static final DeferredItem<Item> FLAVORED_TROPICAL_FISH = ITEMS.register("flavored_tropical_fish",
            () -> new Item(new Item.Properties().food(ModFoods.FLAVORED_TROPICAL_FISH)));
    public static final DeferredItem<Item> FLAVORED_IRON = ITEMS.register("flavored_iron",
            () -> new Item(new Item.Properties().food(ModFoods.FLAVORED_IRON)));
    public static final DeferredItem<Item> FLAVORED_DIAMOND = ITEMS.register("flavored_diamond",
            () -> new Item(new Item.Properties().food(ModFoods.FLAVORED_DIAMOND)));
    public static final DeferredItem<Item> FLAVORED_PITCHER_PLANT = ITEMS.register("flavored_pitcher_plant",
            () -> new Item(new Item.Properties().food(ModFoods.FLAVORED_PITCHER_PLANT)));
    public static final DeferredItem<Item> FLAVORED_PHANTOM_MEMBRANE = ITEMS.register("flavored_phantom_membrane",
            () -> new Item(new Item.Properties().food(ModFoods.FLAVORED_PHANTOM_MEMBRANE)));
    public static final DeferredItem<Item> BEEF_SANDWICH = ITEMS.register("beef_sandwich",
            () -> new Item(new Item.Properties().food(ModFoods.BEEF_SANDWICH)));
    public static final DeferredItem<Item> SUPER_BEEF_SANDWICH = ITEMS.register("super_beef_sandwich",
            () -> new Item(new Item.Properties().food(ModFoods.SUPER_BEEF_SANDWICH)));
    public static final DeferredItem<Item> TERIYAKI_SANDWICH = ITEMS.register("teriyaki_sandwich",
            () -> new Item(new Item.Properties().food(ModFoods.TERIYAKI_SANDWICH)));
    public static final DeferredItem<Item> SUPER_TERIYAKI_SANDWICH = ITEMS.register("super_teriyaki_sandwich",
            () -> new Item(new Item.Properties().food(ModFoods.SUPER_TERIYAKI_SANDWICH)));
    public static final DeferredItem<Item> SUPER_ANGLER_SANDWICH = ITEMS.register("super_angler_sandwich",
            () -> new Item(new Item.Properties().food(ModFoods.SUPER_ANGLER_SANDWICH)));
    public static final DeferredItem<Item> FISH_SANDWICH = ITEMS.register("fish_sandwich",
            () -> new Item(new Item.Properties().food(ModFoods.FISH_SANDWICH)));
    public static final DeferredItem<Item> SUPER_GOLEM_SANDWICH = ITEMS.register("super_golem_sandwich",
            () -> new Item(new Item.Properties().food(ModFoods.SUPER_GOLEM_SANDWICH)));
    public static final DeferredItem<Item> SUPER_MINER_SANDWICH = ITEMS.register("super_miner_sandwich",
            () -> new Item(new Item.Properties().food(ModFoods.SUPER_MINER_SANDWICH)));
    public static final DeferredItem<Item> SUPER_TRAVELER_SANDWICH = ITEMS.register("super_traveler_sandwich",
            () -> new Item(new Item.Properties().food(ModFoods.SUPER_TRAVELER_SANDWICH)));
    public static final DeferredItem<Item> SUPER_EXPLORER_SANDWICH = ITEMS.register("super_explorer_sandwich",
            () -> new Item(new Item.Properties().food(ModFoods.SUPER_EXPLORER_SANDWICH)));
    public static final DeferredItem<Item> SLICE_OF_BREAD = ITEMS.register("slice_of_bread",
            () -> new Item(new Item.Properties().food(ModFoods.SLICE_OF_BREAD)));
    public static final DeferredItem<Item> BROTH = ITEMS.register("broth",
            () -> new Item(new Item.Properties()));
//
//    public static final DeferredRegister<Fluid> FLUIDS =
//            DeferredRegister.create(ForgeRegistries.FLUIDS, SandwichMod.MOD_ID);
//
//    public static final RegistryObject<Fluid> UN_FILLABLE = FLUIDS.register("un_fillable",
//            () -> new Fluid() {
//                @Override
//                public Item getBucket() {
//                    return null;
//                }
//
//                @Override
//                protected boolean canBeReplacedWith(FluidState pState, BlockGetter pLevel, BlockPos pPos, Fluid pFluid, Direction pDirection) {
//                    return false;
//                }
//
//                @Override
//                protected Vec3 getFlow(BlockGetter pBlockReader, BlockPos pPos, FluidState pFluidState) {
//                    return null;
//                }
//
//                @Override
//                public int getTickDelay(LevelReader pLevel) {
//                    return 0;
//                }
//
//                @Override
//                protected float getExplosionResistance() {
//                    return 0;
//                }
//
//                @Override
//                public float getHeight(FluidState pState, BlockGetter pLevel, BlockPos pPos) {
//                    return 0;
//                }
//
//                @Override
//                public float getOwnHeight(FluidState pState) {
//                    return 0;
//                }
//
//                @Override
//                protected BlockState createLegacyBlock(FluidState pState) {
//                    return null;
//                }
//
//                @Override
//                public boolean isSource(FluidState pState) {
//                    return false;
//                }
//
//                @Override
//                public int getAmount(FluidState pState) {
//                    return 0;
//                }
//
//                @Override
//                public VoxelShape getShape(FluidState pState, BlockGetter pLevel, BlockPos pPos) {
//                    return null;
//                }
//            });
//
//    public static final RegistryObject<Item> HOT_WATER_BUCKET = ITEMS.register("hot_water_bucket",
//            () -> new BucketItem(ModItems.UN_FILLABLE, (new Item.Properties()).craftRemainder(Items.BUCKET).stacksTo(1)));
//
//
    public static void register(IEventBus eventBus) {

        ITEMS.register(eventBus);
        ModFluids.FLUID_TYPES.register(eventBus);
        ModFluids.FLUIDS.register(eventBus);
        ModFluids.BLOCKS.register(eventBus);

    }



}
