package net.pererillo.super_sandwiches.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class ModFoods {

    public static final FoodProperties FLAVORED_PORKCHOP = (new FoodProperties.Builder()).nutrition(10).saturationModifier(0.8F).build();
    public static final FoodProperties PORK_SANDWICH = (new FoodProperties.Builder()).nutrition(12).saturationModifier(1.0F).build();
    public static final FoodProperties SUPER_PORK_SANDWICH = (new FoodProperties.Builder()).nutrition(14).saturationModifier(1.2F).effect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 1200, 0), 1.0F).alwaysEdible().build();
    public static final FoodProperties FLAVORED_BEEF = (new FoodProperties.Builder()).nutrition(10).saturationModifier(0.8F).build();
    public static final FoodProperties BEEF_SANDWICH = (new FoodProperties.Builder()).nutrition(12).saturationModifier(1.0F).build();
    public static final FoodProperties SUPER_BEEF_SANDWICH = (new FoodProperties.Builder()).nutrition(14).saturationModifier(1.2F).effect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 1200, 0), 1.0F).alwaysEdible().build();
    public static final FoodProperties FLAVORED_CHICKEN = (new FoodProperties.Builder()).nutrition(8).saturationModifier(0.8F).build();
    public static final FoodProperties TERIYAKI_SANDWICH = (new FoodProperties.Builder()).nutrition(10).saturationModifier(1.0F).build();
    public static final FoodProperties SUPER_TERIYAKI_SANDWICH = (new FoodProperties.Builder()).nutrition(12).saturationModifier(1.2F).effect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 1200, 0), 1.0F).effect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE,600,0), 1.0F).alwaysEdible().build();
    public static final FoodProperties FLAVORED_TROPICAL_FISH = (new FoodProperties.Builder()).nutrition(2).saturationModifier(0.6F).build();
    public static final FoodProperties FISH_SANDWICH = (new FoodProperties.Builder()).nutrition(8).saturationModifier(1.0F).build();
    public static final FoodProperties SUPER_ANGLER_SANDWICH = (new FoodProperties.Builder()).nutrition(12).saturationModifier(1.2F).effect(new MobEffectInstance(MobEffects.LUCK, 6000, 1), 1.0F).effect(new MobEffectInstance(MobEffects.WATER_BREATHING,600,0), 1.0F).alwaysEdible().build();
    public static final FoodProperties FLAVORED_IRON = (new FoodProperties.Builder()).nutrition(4).saturationModifier(0.8F).effect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE,300,0),1.0F).effect(new MobEffectInstance(MobEffects.CONFUSION,300,2),0.5F).build();
    public static final FoodProperties SUPER_GOLEM_SANDWICH = (new FoodProperties.Builder()).nutrition(8).saturationModifier(1.2F).effect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 1200, 1), 1.0F).alwaysEdible().build();
    public static final FoodProperties FLAVORED_PITCHER_PLANT = (new FoodProperties.Builder()).nutrition(6).saturationModifier(0.8F).effect(new MobEffectInstance(MobEffects.HEAL,0,0),1.0F).effect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED,300,0),1.0F).alwaysEdible().build();
    public static final FoodProperties SUPER_TRAVELER_SANDWICH = (new FoodProperties.Builder()).nutrition(10).saturationModifier(1.2F).effect(new MobEffectInstance(MobEffects.JUMP, 9600, 2), 1.0F).effect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 9600, 6), 1.0F).alwaysEdible().build();
    public static final FoodProperties FLAVORED_DIAMOND = (new FoodProperties.Builder()).nutrition(4).saturationModifier(0.8F).effect(new MobEffectInstance(MobEffects.CONFUSION,300,2),0.5F).build();
    public static final FoodProperties SUPER_MINER_SANDWICH = (new FoodProperties.Builder()).nutrition(10).saturationModifier(1.2F).effect(new MobEffectInstance(MobEffects.DIG_SPEED, 6000, 2), 1.0F).effect(new MobEffectInstance(MobEffects.NIGHT_VISION, 6000, 0),1.0F).effect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 1200, 3),1.0F).alwaysEdible().build();
    public static final FoodProperties FLAVORED_PHANTOM_MEMBRANE = (new FoodProperties.Builder()).nutrition(3).saturationModifier(0.6F).effect(new MobEffectInstance(MobEffects.LEVITATION,150,1),1.0F).build();
    public static final FoodProperties SUPER_EXPLORER_SANDWICH = (new FoodProperties.Builder()).nutrition(10).saturationModifier(1.2F).effect(new MobEffectInstance(MobEffects.JUMP, 1200, 50), 1.0F).effect(new MobEffectInstance(MobEffects.SLOW_FALLING, 1200, 1), 1.0F).alwaysEdible().build();
    public static final FoodProperties SLICE_OF_BREAD = (new FoodProperties.Builder()).nutrition(3).saturationModifier(0.3F).build();

}
