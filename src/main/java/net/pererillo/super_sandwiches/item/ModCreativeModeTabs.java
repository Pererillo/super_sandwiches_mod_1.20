package net.pererillo.super_sandwiches.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.pererillo.super_sandwiches.SandwichMod;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, SandwichMod.MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> SUPER_SANDWICHES_TAB = CREATIVE_MODE_TABS.register("super_sandwiches_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.SLICE_OF_BREAD.get()))
                    .title(Component.translatable("creativetab.super_sandwiches_tab"))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(ModFluids.HOT_WATER_BUCKET.get());
                        pOutput.accept(ModItems.SLICE_OF_BREAD.get());
                        pOutput.accept(ModItems.FLAVORED_PORKCHOP.get());
                        pOutput.accept(ModItems.PORK_SANDWICH.get());
                        pOutput.accept(ModItems.SUPER_PORK_SANDWICH.get());
                        pOutput.accept(ModItems.FLAVORED_BEEF.get());
                        pOutput.accept(ModItems.BEEF_SANDWICH.get());
                        pOutput.accept(ModItems.SUPER_BEEF_SANDWICH.get());
                        pOutput.accept(ModItems.FLAVORED_CHICKEN.get());
                        pOutput.accept(ModItems.TERIYAKI_SANDWICH.get());
                        pOutput.accept(ModItems.SUPER_TERIYAKI_SANDWICH.get());
                        pOutput.accept(ModItems.FLAVORED_TROPICAL_FISH.get());
                        pOutput.accept(ModItems.FISH_SANDWICH.get());
                        pOutput.accept(ModItems.SUPER_ANGLER_SANDWICH.get());
                        pOutput.accept(ModItems.FLAVORED_IRON.get());
                        pOutput.accept(ModItems.SUPER_GOLEM_SANDWICH.get());
                        pOutput.accept(ModItems.FLAVORED_DIAMOND.get());
                        pOutput.accept(ModItems.SUPER_MINER_SANDWICH.get());
                        pOutput.accept(ModItems.FLAVORED_PITCHER_PLANT.get());
                        pOutput.accept(ModItems.SUPER_TRAVELER_SANDWICH.get());
                        pOutput.accept(ModItems.FLAVORED_PHANTOM_MEMBRANE.get());
                        pOutput.accept(ModItems.SUPER_EXPLORER_SANDWICH.get());
                        pOutput.accept(ModItems.BROTH.get());







                    })
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}