package net.pererillo.super_sandwiches;

import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.material.FluidState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.pererillo.super_sandwiches.item.ModFluids;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;


// This class will not load on dedicated servers. Accessing client side code from here is safe.
@Mod(value = SandwichMod.MODID, dist = Dist.CLIENT)
// You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
@EventBusSubscriber(modid = SandwichMod.MODID, value = Dist.CLIENT)
public class SandwichModClient {
    public SandwichModClient(ModContainer container) {
        // Allows NeoForge to create a config screen for this mod's configs.
        // The config screen is accessed by going to the Mods screen > clicking on your mod > clicking on config.
        // Do not forget to add translations for your config options to the en_us.json file.
    }

    @SubscribeEvent
    static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {  // Importante: enqueueWork para que se ejecute en el hilo correcto
            ItemBlockRenderTypes.setRenderLayer(ModFluids.HOT_WATER_SOURCE.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(ModFluids.HOT_WATER_FLOWING.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(ModFluids.HOT_WATER_BLOCK.get(), RenderType.translucent());
        });

        SandwichMod.LOGGER.info("HELLO FROM CLIENT SETUP");
        SandwichMod.LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
    }


    @SubscribeEvent
    public static void registerClientExtensions(RegisterClientExtensionsEvent event) {
        event.registerFluidType(new IClientFluidTypeExtensions() {
            private static final ResourceLocation STILL = ResourceLocation.fromNamespaceAndPath(SandwichMod.MODID, "block/hot_water_still");
            private static final ResourceLocation FLOWING = ResourceLocation.fromNamespaceAndPath(SandwichMod.MODID, "block/hot_water_flow");
            private static final ResourceLocation OVERLAY = ResourceLocation.fromNamespaceAndPath(SandwichMod.MODID, "misc/water_overlay");

            @Override
            public @NotNull ResourceLocation getStillTexture() { return STILL; }

            @Override
            public @NotNull ResourceLocation getFlowingTexture() { return FLOWING; }

            @Override
            public ResourceLocation getOverlayTexture() { return OVERLAY; }

            @Override
            public int getTintColor() { return 0xFF7889ED; }

            @Override
            public int getTintColor(@NotNull FluidState state, @NotNull BlockAndTintGetter getter, @NotNull BlockPos pos) {
                return getTintColor();
            }

            @Override
            public @NotNull Vector3f modifyFogColor(@NotNull Camera camera, float partialTick, @NotNull ClientLevel level, int renderDistance, float darkenWorldAmount, @NotNull Vector3f fluidFogColor) {
                return new Vector3f(0.7F, 0.7F, 1.0F); // Niebla
            }
        }, ModFluids.HOT_WATER_TYPE.get());
    }

}