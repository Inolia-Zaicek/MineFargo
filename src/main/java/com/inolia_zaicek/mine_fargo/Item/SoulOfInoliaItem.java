package com.inolia_zaicek.mine_fargo.Item;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.inolia_zaicek.mine_fargo.Config.MyGoConfig;
import com.inolia_zaicek.mine_fargo.Item.Iron.EarthSectSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Item.Iron.FantacySectSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Item.Iron.SoundSectSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Register.MyGoItemRegister;
import com.inolia_zaicek.mine_fargo.Util.MyGoUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ForgeRegistries;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static com.inolia_zaicek.mine_fargo.Register.MyGoItemRegister.SoulOfInolia;

@SuppressWarnings({"all", "removal"})
public class SoulOfInoliaItem extends Item implements ICurioItem {
    public SoulOfInoliaItem() {super((new Item.Properties()).stacksTo(1).fireResistant());}
    protected String getTooltipItemName() {
        return BuiltInRegistries.ITEM.getKey(this).getPath();
    }
    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        String itemName = getTooltipItemName();
        if(Screen.hasShiftDown()) {
            pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_inolia.shift_text"
            ).withStyle(style -> style.withColor(TextColor.fromRgb(0x808080))));
        }else if(Screen.hasAltDown()) {
            pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_inolia.alt_text"
            ).withStyle(style -> style.withColor(TextColor.fromRgb(0x606060))));
        }else{
            pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_inolia.text"
            ).withStyle(style -> style.withColor(TextColor.fromRgb(0xC0C0C0))));
        }
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
    @Override
    public List<Component> getAttributesTooltip(List<Component> tooltips, ItemStack stack) {
        return Collections.emptyList();
    }
    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        return ! MyGoUtil.isCurioEquipped(slotContext.entity(), SoulOfInolia.get());
    }
    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        // 调用父实现以维持默认行为（若父类有实现）
        ICurioItem.super.onEquip(slotContext, prevStack, stack);

        // 获取实体对象
        LivingEntity entity = slotContext.entity();

        // 确保在可飞行的前提下开启飞行能力
        if (entity instanceof Player player) {

            // 允许玩家飞行（开启飞行能力）
            player.getAbilities().flying = true;
            player.getAbilities().mayfly = true; // 允许持续飞行，若需要可控则保留 mayfly
            player.onUpdateAbilities(); // 触发客户端和服务器的能力刷新（确保状态生效）
        }

    }
    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        // 调用父实现以维持默认行为（若父类有实现）
        ICurioItem.super.onUnequip(slotContext, newStack, stack);
        LivingEntity entity = slotContext.entity();
        if (entity instanceof Player player&& !player.isCreative()) {
            // 取消飞行能力
            player.getAbilities().flying = false;
            player.getAbilities().mayfly = false;
            // 重新同步能力状态
            player.onUpdateAbilities();
        }
    }
    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> atts = LinkedHashMultimap.create();
        //洞穴
        if(ModList.get().isLoaded("alexscaves")) {
            atts.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(UUID.fromString("876A9BC5-F1D0-AD16-E5C1-181A5F26DE1D"), this.getTooltipItemName(), MyGoConfig.candy_cavity_soul_stone_base.get(), AttributeModifier.Operation.MULTIPLY_TOTAL));
            if (slotContext.entity() != null && slotContext.entity().isSprinting()) {
                atts.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(UUID.fromString("ADD0D4E5-D150-B733-4795-8A9CD9E13293"), this.getTooltipItemName(), MyGoConfig.candy_cavity_soul_stone_speed.get(), AttributeModifier.Operation.MULTIPLY_TOTAL));
            }
        }
        //新生魔艺
        if(ModList.get().isLoaded("ars_nouveau")) {
            atts.put(Objects.requireNonNull(ForgeRegistries.ATTRIBUTES.getValue(
                            new ResourceLocation("ars_nouveau", "ars_nouveau.perk.mana_regen")))
                    , new AttributeModifier(UUID.fromString("F706F76C-B776-703B-B9A9-F8228786C1FC"), this.getTooltipItemName(), MyGoConfig.ars_source_soul_stone_re.get(), AttributeModifier.Operation.ADDITION));
            atts.put(Objects.requireNonNull(ForgeRegistries.ATTRIBUTES.getValue(
                            new ResourceLocation("ars_nouveau", "ars_nouveau.perk.max_mana")))
                    , new AttributeModifier(UUID.fromString("20D4E8D5-F9E0-3C40-73C3-DAB83B65BA14"), this.getTooltipItemName(), MyGoConfig.ars_source_soul_stone_mana.get(), AttributeModifier.Operation.ADDITION));
            atts.put(Objects.requireNonNull(ForgeRegistries.ATTRIBUTES.getValue(
                            new ResourceLocation("ars_nouveau", "ars_nouveau.perk.mana_regen")))
                    , new AttributeModifier(UUID.fromString("A595342E-F3FC-6D23-DA6A-166534B2D0D2"), this.getTooltipItemName(), MyGoConfig.wilden_soul_stone_re.get(), AttributeModifier.Operation.MULTIPLY_BASE));
            atts.put(Objects.requireNonNull(ForgeRegistries.ATTRIBUTES.getValue(
                            new ResourceLocation("ars_nouveau", "ars_nouveau.perk.max_mana")))
                    , new AttributeModifier(UUID.fromString("2646B81E-C3A9-B0F8-6A81-EC1A04080E2B"), this.getTooltipItemName(), MyGoConfig.wilden_soul_stone_mana.get(), AttributeModifier.Operation.MULTIPLY_BASE));
            atts.put(Objects.requireNonNull(ForgeRegistries.ATTRIBUTES.getValue(
                            new ResourceLocation("ars_nouveau", "ars_nouveau.perk.spell_damage")))
                    , new AttributeModifier(UUID.fromString("FD23F7E1-CF27-A972-47F3-B845AF24CA51"), this.getTooltipItemName(), MyGoConfig.wilden_soul_stone_damage.get() +
                            MyGoConfig.ars_source_soul_stone_damage.get() + MyGoConfig.drygmy_soul_stone_damage.get(), AttributeModifier.Operation.ADDITION));
            atts.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(UUID.fromString("4361DDF7-301D-F4C1-9F3C-3D5B4CB2D2F4"), this.getTooltipItemName(), MyGoConfig.starbuncle_soul_stone.get(), AttributeModifier.Operation.MULTIPLY_BASE));
            atts.put(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(UUID.fromString("3C336B9A-1537-6FDA-0A2D-2293B4EF3272"), this.getTooltipItemName(), MyGoConfig.amethyst_golem_soul_stone.get(), AttributeModifier.Operation.ADDITION));

            if (ModList.get().isLoaded("ars_elemental")) {
                atts.put(Objects.requireNonNull(ForgeRegistries.ATTRIBUTES.getValue(
                                new ResourceLocation("ars_nouveau", "ars_nouveau.perk.spell_damage")))
                        , new AttributeModifier(UUID.fromString("BCA27A24-3697-EA84-FE9A-69EC2F0B3CB1"), this.getTooltipItemName(), MyGoConfig.siren_soul_stone.get(), AttributeModifier.Operation.ADDITION));
                atts.put(Objects.requireNonNull(ForgeRegistries.ATTRIBUTES.getValue(
                                new ResourceLocation("ars_nouveau", "ars_nouveau.perk.spell_damage")))
                        , new AttributeModifier(UUID.fromString("E1B5E133-9DB0-65AE-7CE6-DE6F3ACE2209"), this.getTooltipItemName(), MyGoConfig.firenando_soul_stone_damage.get(), AttributeModifier.Operation.ADDITION));
            }
        }
        //灾变
        if(ModList.get().isLoaded("cataclysm")) {
            atts.put(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(UUID.fromString("0FD19DB9-9EF6-0966-9367-7018B0611A72"), this.getTooltipItemName(), MyGoConfig.ancient_remnant_soul_stone_armor.get(), AttributeModifier.Operation.ADDITION));
        }
        //诡厄
        if(ModList.get().isLoaded("goety")) {
            atts.put(Attributes.MAX_HEALTH, new AttributeModifier(UUID.fromString("79FC4908-5816-BA88-6CD1-8F7CF897E548"), this.getTooltipItemName(), MyGoConfig.redstone_monstrosity_soul_stone_hp_up.get(), AttributeModifier.Operation.MULTIPLY_TOTAL));
        }
        //冰火
        if(ModList.get().isLoaded("iceandfire")) {
            atts.put(Attributes.ARMOR, new AttributeModifier(UUID.fromString("049E0BF6-8AC9-E349-DF1B-F43E2A6612DA"), this.getTooltipItemName(),
                    MyGoConfig.fire_dragon_steel_soul_stone_armor.get()+MyGoConfig.ice_dragon_steel_soul_stone_armor.get()
                            +MyGoConfig.lightning_dragon_steel_soul_stone_armor.get(), AttributeModifier.Operation.ADDITION));
            atts.put(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(UUID.fromString("B922AF89-9E01-E078-6129-E0F1E369B3B9"), this.getTooltipItemName(),
                    MyGoConfig.fire_dragon_steel_soul_stone_armor_t.get()+MyGoConfig.ice_dragon_steel_soul_stone_armor_t.get()
                            +MyGoConfig.lightning_dragon_steel_soul_stone_armor_t.get(), AttributeModifier.Operation.ADDITION));
            atts.put(ForgeMod.SWIM_SPEED.get(), new AttributeModifier(UUID.fromString("2BDC0A50-1864-CD08-133A-2AFF4DDA5A8C"), this.getTooltipItemName(),
                    MyGoConfig.hippocampus_soul_stone_speed.get(), AttributeModifier.Operation.MULTIPLY_BASE));
            atts.put(ForgeMod.ENTITY_REACH.get(), new AttributeModifier(UUID.fromString("401859BE-8F0A-0098-80EE-4E4BC8C42CA0"), this.getTooltipItemName(),
                    MyGoConfig.death_worm_soul_stone.get(), AttributeModifier.Operation.ADDITION));
        }
        //铁魔法
        if(ModList.get().isLoaded("irons_spellbooks")) {
            atts.put(Objects.requireNonNull(
                    ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("irons_spellbooks", "fire_spell_power"))), new AttributeModifier(UUID.fromString("3277E97E-E18E-8D53-415B-41757F36BE45"), this.getTooltipItemName(), MyGoConfig.fire_sect_soul_stone_fire_power.get(), AttributeModifier.Operation.MULTIPLY_BASE));

            atts.put(Objects.requireNonNull(
                    ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("irons_spellbooks", "ice_spell_power"))), new AttributeModifier(UUID.fromString("E7BB7099-B0C3-1B1E-0903-940FBB05A6B6"), this.getTooltipItemName(), MyGoConfig.ice_sect_soul_stone_ice_power.get(), AttributeModifier.Operation.MULTIPLY_BASE));

            atts.put(Objects.requireNonNull(
                    ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("irons_spellbooks", "lightning_spell_power"))), new AttributeModifier(UUID.fromString("756271A2-5BF2-3FCB-FAF2-C30DEBD6B130"), this.getTooltipItemName(), MyGoConfig.lightning_sect_soul_stone_power.get(), AttributeModifier.Operation.MULTIPLY_BASE));

            atts.put(Objects.requireNonNull(
                    ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("irons_spellbooks", "evocation_spell_power"))), new AttributeModifier(UUID.fromString("07BB10F5-7D60-96FA-1406-EA3F72211B6E"), this.getTooltipItemName(), MyGoConfig.nature_sect_soul_stone_power.get(), AttributeModifier.Operation.MULTIPLY_BASE));

            atts.put(Objects.requireNonNull(
                    ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("irons_spellbooks", "holy_spell_power"))), new AttributeModifier(UUID.fromString("EEFF2E54-8D03-0796-9B81-A0047753D29B"), this.getTooltipItemName(), MyGoConfig.holy_sect_soul_stone_power.get(), AttributeModifier.Operation.MULTIPLY_BASE));

            atts.put(Objects.requireNonNull(
                    ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("irons_spellbooks", "blood_spell_power"))), new AttributeModifier(UUID.fromString("C7D4381F-68D1-161F-7A2E-31E1987B33C9"), this.getTooltipItemName(), MyGoConfig.blood_sect_soul_stone_power.get(), AttributeModifier.Operation.MULTIPLY_BASE));

            atts.put(Objects.requireNonNull(
                    ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("irons_spellbooks", "nature_spell_power"))), new AttributeModifier(UUID.fromString("ADD89E31-6A51-BC59-E87B-08FA0F7D7ADB"), this.getTooltipItemName(), MyGoConfig.nature_sect_soul_stone_power.get(), AttributeModifier.Operation.MULTIPLY_BASE));

            atts.put(Objects.requireNonNull(
                    ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("irons_spellbooks", "ender_spell_power"))), new AttributeModifier(UUID.fromString("51D79B32-1235-50A4-11B0-200766D5CC46"), this.getTooltipItemName(), MyGoConfig.ender_sect_soul_stone_power.get(), AttributeModifier.Operation.MULTIPLY_BASE));

            atts.put(Objects.requireNonNull(
                    ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("irons_spellbooks", "eldritch_spell_power"))), new AttributeModifier(UUID.fromString("4ADA9FD4-3527-0007-B74E-8B8E02C28E0A"), this.getTooltipItemName(), MyGoConfig.eldritch_sect_soul_stone_power.get(), AttributeModifier.Operation.MULTIPLY_BASE));
            atts.put(Objects.requireNonNull(ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("irons_spellbooks", "max_mana"))),
                    new AttributeModifier(UUID.fromString("D0E64E8A-0472-A263-0A9D-428046740EAC"), getTooltipItemName(), MyGoConfig.iron_mana.get()*9, AttributeModifier.Operation.ADDITION));
            if (ModList.get().isLoaded("traveloptics")) {
                atts.put(Objects.requireNonNull(
                        ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("traveloptics", "aqua_spell_power"))), new AttributeModifier(UUID.fromString("D36780B8-DE59-808A-2559-4421EB85A550"), this.getTooltipItemName(), MyGoConfig.aqua_sect_soul_stone_power.get(), AttributeModifier.Operation.MULTIPLY_BASE));
                atts.put(Objects.requireNonNull(ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("irons_spellbooks", "max_mana"))),
                        new AttributeModifier(UUID.fromString("192E7534-A96C-55A1-3AB8-919A394184E8"), getTooltipItemName(), MyGoConfig.iron_mana.get(), AttributeModifier.Operation.ADDITION));
            }
            if (ModList.get().isLoaded("familiarslib")) {
                atts.put(Objects.requireNonNull(ForgeRegistries.ATTRIBUTES.getValue(
                                new ResourceLocation("familiarslib", "sound_spell_power")))
                        , new AttributeModifier(UUID.fromString("DE2663E8-AA99-EE87-879E-25FC2BE1FEB0"), this.getTooltipItemName(), MyGoConfig.sound_sect_soul_stone_power.get(), AttributeModifier.Operation.MULTIPLY_BASE));

                atts.put(Objects.requireNonNull(ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("irons_spellbooks", "max_mana"))),
                        new AttributeModifier(UUID.fromString("425C1975-4DF0-96AD-DC55-96FD5BCC2D09"), getTooltipItemName(), MyGoConfig.iron_mana.get(), AttributeModifier.Operation.ADDITION));
            }
            if (ModList.get().isLoaded("gtbcs_geomancy_plus")) {
                atts.put(Objects.requireNonNull(ForgeRegistries.ATTRIBUTES.getValue(
                                new ResourceLocation("gtbcs_geomancy_plus", "geo_spell_power")))
                        , new AttributeModifier(UUID.fromString("D7DC988F-825E-41FB-E63A-240818CFBAA0"), this.getTooltipItemName(), MyGoConfig.earth_sect_soul_stone_power.get(), AttributeModifier.Operation.MULTIPLY_BASE));
                atts.put(Objects.requireNonNull(ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("irons_spellbooks", "max_mana"))),
                        new AttributeModifier(UUID.fromString("A0BF54F3-0296-8256-5CD2-1ED0C2F1D0F2"), getTooltipItemName(), MyGoConfig.iron_mana.get(), AttributeModifier.Operation.ADDITION));
            }
            if (ModList.get().isLoaded("fantasy_ending")) {
                atts.put(Objects.requireNonNull(ForgeRegistries.ATTRIBUTES.getValue(
                                new ResourceLocation("irons_spellbooks", "spell_power")))
                        , new AttributeModifier(UUID.fromString("88B106C1-198A-0313-B4F5-EE2BE46658FF"), this.getTooltipItemName(), MyGoConfig.fantasy_sect_soul_stone_power.get(), AttributeModifier.Operation.MULTIPLY_BASE));
                atts.put(Objects.requireNonNull(ForgeRegistries.ATTRIBUTES.getValue(
                                new ResourceLocation("fantasy_ending", "fantasy_spell_power")))
                        , new AttributeModifier(UUID.fromString("53CFE361-B37B-C3E9-CF85-4AC97975206F"), this.getTooltipItemName(), MyGoConfig.fantasy_sect_soul_stone_fantacy_power.get(), AttributeModifier.Operation.MULTIPLY_BASE));
                atts.put(Objects.requireNonNull(ForgeRegistries.ATTRIBUTES.getValue(
                                new ResourceLocation("fantasy_ending", "dream_shadow_damage")))
                        , new AttributeModifier(UUID.fromString("0204FA0E-F630-EDD0-CF9F-A1330699D620"), this.getTooltipItemName(), MyGoConfig.fantasy_sect_soul_stone_dream_shadow_damage.get(), AttributeModifier.Operation.ADDITION));
                atts.put(Objects.requireNonNull(ForgeRegistries.ATTRIBUTES.getValue(
                                new ResourceLocation("fantasy_ending", "dream_shadow_damage_resistance")))
                        , new AttributeModifier(UUID.fromString("F0AF89A7-4AAD-C3A9-CD8D-61550CB29ADA"), this.getTooltipItemName(), MyGoConfig.fantasy_sect_soul_stone_dream_shadow_damage_resistance.get(), AttributeModifier.Operation.MULTIPLY_BASE));
                atts.put(Objects.requireNonNull(ForgeRegistries.ATTRIBUTES.getValue(
                                new ResourceLocation("fantasy_ending", "fantasy_ending_damage")))
                        , new AttributeModifier(UUID.fromString("18FC21D6-47D5-6EF1-20C3-0F18C88A1201"), this.getTooltipItemName(), MyGoConfig.fantasy_sect_soul_stone_fantasy_ending_damage.get(), AttributeModifier.Operation.ADDITION));
                atts.put(Objects.requireNonNull(ForgeRegistries.ATTRIBUTES.getValue(
                                new ResourceLocation("fantasy_ending", "fantasy_ending_damage_resistance")))
                        , new AttributeModifier(UUID.fromString("D9B6A0E8-05FD-1353-CB65-5684C21B561A"), this.getTooltipItemName(), MyGoConfig.fantasy_sect_soul_stone_fantasy_ending_damage_resistance.get(), AttributeModifier.Operation.MULTIPLY_BASE));
                atts.put(Objects.requireNonNull(ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("irons_spellbooks", "max_mana"))),
                        new AttributeModifier(UUID.fromString("B87465DF-F9D8-2A94-99B5-6DD0AEE92616"), getTooltipItemName(), MyGoConfig.iron_mana.get(), AttributeModifier.Operation.ADDITION));
            }
        }
        //莱特兰 拓展
        if(ModList.get().isLoaded("l2complements")) {
            if(slotContext.entity()!=null) {
                LivingEntity livingEntity = slotContext.entity();
                if(livingEntity.isUnderWater() || livingEntity.isInWater() || livingEntity.isInWaterOrRain() || livingEntity.isInWaterRainOrBubble() ) {
                    atts.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(UUID.fromString("EB447AD1-8DE8-4C2C-8A2D-4174E4AE19E2"), this.getTooltipItemName(), MyGoConfig.poseidite_complements_soul_stone_atk.get(), AttributeModifier.Operation.MULTIPLY_TOTAL));
                    atts.put(Attributes.ATTACK_SPEED, new AttributeModifier(UUID.fromString("51B58978-3A41-AEF0-66AA-2AAAD94357EB"), this.getTooltipItemName(), MyGoConfig.poseidite_complements_soul_stone_speed.get(), AttributeModifier.Operation.MULTIPLY_TOTAL));
                }
            }
            atts.put(ForgeMod.ENTITY_REACH.get(), new AttributeModifier(UUID.fromString("3415CEE8-A854-0E3B-9B82-B4708821BD1F"), this.getTooltipItemName(), MyGoConfig.shulkerate_complements_soul_stone_entity.get(), AttributeModifier.Operation.ADDITION));
            atts.put(ForgeMod.BLOCK_REACH.get(), new AttributeModifier(UUID.fromString("4D00889B-80BF-AC79-BEFF-AB91B451A647"), this.getTooltipItemName(), MyGoConfig.shulkerate_complements_soul_stone_block.get(), AttributeModifier.Operation.ADDITION));
        }
        //莱特兰 恶意
        if(ModList.get().isLoaded("l2hostility")) {
            atts.put(Attributes.MAX_HEALTH, new AttributeModifier(UUID.fromString("F5EE4A29-53BA-679F-9EF8-08A924DF5AD6"), this.getTooltipItemName(),
                    MyGoConfig.body_hostility_soul_stone_hp.get(), AttributeModifier.Operation.MULTIPLY_TOTAL));
            atts.put(Attributes.ARMOR, new AttributeModifier(UUID.fromString("FFA4CB03-4DF7-2832-570B-BF14FB6DFD70"), this.getTooltipItemName(),
                    MyGoConfig.body_hostility_soul_stone_armor.get(), AttributeModifier.Operation.ADDITION));
            atts.put(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(UUID.fromString("5668B40A-8AA9-CEAA-2283-131FA9183E5D"), this.getTooltipItemName(),
                    MyGoConfig.body_hostility_soul_stone_armor_t.get(), AttributeModifier.Operation.ADDITION));
        }
        //传奇怪物
        if(ModList.get().isLoaded("legendary_monsters")) {
            atts.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(UUID.fromString("D760316F-5CE2-8DC1-FC0A-BA2B9C042F8F"), this.getTooltipItemName(), MyGoConfig.withered_abomination_soul_stone_atk.get(), AttributeModifier.Operation.MULTIPLY_TOTAL));
            atts.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(UUID.fromString("02522641-0560-6CBC-C86B-327FFA43E887"), this.getTooltipItemName(), MyGoConfig.skeletosaurus_soul_stone_atk.get(), AttributeModifier.Operation.MULTIPLY_TOTAL));
            atts.put(Attributes.ARMOR, new AttributeModifier(UUID.fromString("63BA4726-9EFB-D2CB-96EB-9259DCE84E95"), this.getTooltipItemName(), MyGoConfig.skeletosaurus_soul_stone_armor.get(), AttributeModifier.Operation.ADDITION));
            atts.put(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(UUID.fromString("2E5AB7A0-85EA-9CB6-C7EA-1A547FFB10C6"), this.getTooltipItemName(), MyGoConfig.skeletosaurus_soul_stone_knockback_resistance.get(), AttributeModifier.Operation.ADDITION));
        }
        //原版四魂
        atts.put(Attributes.FLYING_SPEED, new AttributeModifier(UUID.fromString("A5EF443D-166A-5FC1-FEF7-A239CBF76E31"), this.getTooltipItemName(), MyGoConfig.wing_soul_stone.get(), AttributeModifier.Operation.MULTIPLY_TOTAL));
        atts.put(ForgeMod.SWIM_SPEED.get(), new AttributeModifier(UUID.fromString("1D1531E0-3820-5F95-572C-BB66375BF0B3"), this.getTooltipItemName(), MyGoConfig.ocean_soul_stone.get(), AttributeModifier.Operation.MULTIPLY_TOTAL));
        atts.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(UUID.fromString("BB27987C-F7C4-42D2-E0D4-32EB624DCC64"), this.getTooltipItemName(), MyGoConfig.coal_soul_stone.get(), AttributeModifier.Operation.MULTIPLY_TOTAL));
        atts.put(Attributes.ATTACK_SPEED, new AttributeModifier(UUID.fromString("9A4E1776-443D-408A-6923-50E99E18CFB1"), this.getTooltipItemName(), MyGoConfig.redstone_soul_stone.get(), AttributeModifier.Operation.MULTIPLY_TOTAL));
        atts.put(Attributes.ARMOR, new AttributeModifier(UUID.fromString("5FB97E03-D53E-2724-5405-611C81B6F328"), this.getTooltipItemName(), MyGoConfig.iron_soul_stone.get(), AttributeModifier.Operation.ADDITION));
        atts.put(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(UUID.fromString("F6B58534-FA77-D0EC-8E78-1C7920FBA14A"), this.getTooltipItemName(), MyGoConfig.netherite_soul_stone.get(), AttributeModifier.Operation.ADDITION));
        atts.put(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(UUID.fromString("FC653097-5753-FFF6-ADBA-2590E41F5B77"), this.getTooltipItemName(), MyGoConfig.netherite_soul_stone_armor.get(), AttributeModifier.Operation.ADDITION));
        //海产品
        if (ModList.get().isLoaded("aquaculture")) {
            atts.put(ForgeMod.SWIM_SPEED.get(), new AttributeModifier(UUID.fromString("EBD57946-53B3-6C3E-858D-34BABF4D6F4C"), this.getTooltipItemName(), MyGoConfig.neptunium_soul_stone_swim.get(), AttributeModifier.Operation.MULTIPLY_BASE));
        }
        //七罪
        if(ModList.get().isLoaded("sons_of_sins")) {
            atts.put(Attributes.ATTACK_SPEED, new AttributeModifier(UUID.fromString("E7C69163-B957-47B3-813E-F0FB5FB41026"), this.getTooltipItemName(), MyGoConfig.envy_sin_soul_stone_atk_speed.get(), AttributeModifier.Operation.MULTIPLY_TOTAL));
            atts.put(Attributes.MAX_HEALTH, new AttributeModifier(UUID.fromString("B71A0B29-1C6C-B78D-527A-63CE7AEFE875"), this.getTooltipItemName(), MyGoConfig.sloth_sin_soul_stone_hp.get()-MyGoConfig.wrath_sin_soul_stone_hp.get(), AttributeModifier.Operation.MULTIPLY_TOTAL));
            if(slotContext.entity()!=null&&slotContext.entity().isSprinting()) {
                atts.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(UUID.fromString("88ED9E3B-0A75-E269-F628-B9A78773B438"), this.getTooltipItemName(), MyGoConfig.envy_sin_soul_stone_speed.get(), AttributeModifier.Operation.MULTIPLY_TOTAL));
            }
        }
        //TACZ
        if(ModList.get().isLoaded("tacz")) {
            atts.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(UUID.fromString("763B583A-FB90-D331-5A4A-F2D3503E5AD5"), this.getTooltipItemName(), MyGoConfig.submachine_gun_soul_stone_speed.get(), AttributeModifier.Operation.MULTIPLY_TOTAL));
        }
        //暮色森林
        if(ModList.get().isLoaded("twilightforest")) {
            atts.put(Attributes.MAX_HEALTH, new AttributeModifier(UUID.fromString("735EFF38-0512-607C-BE9E-AB826279C5CC"), this.getTooltipItemName(), MyGoConfig.alpha_yeti_soul_stone_hp.get(), AttributeModifier.Operation.MULTIPLY_BASE));
            atts.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(UUID.fromString("6BC99875-E554-2391-5558-D122D17F851E"), this.getTooltipItemName(), MyGoConfig.naga_soul_stone_speed.get(), AttributeModifier.Operation.MULTIPLY_BASE));
            atts.put(Attributes.ARMOR, new AttributeModifier(UUID.fromString("D3FBA014-CAA4-E915-FD16-522E2C704C78"), this.getTooltipItemName(), MyGoConfig.ironwood_soul_stone_armor.get(), AttributeModifier.Operation.ADDITION));
            atts.put(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(UUID.fromString("9855AA31-1B22-2D7D-32F1-CFCA2380AE90"), this.getTooltipItemName(), MyGoConfig.ironwood_soul_stone_armor_toughness.get(), AttributeModifier.Operation.ADDITION));
            atts.put(ForgeMod.ENTITY_REACH.get(), new AttributeModifier(UUID.fromString("BF562646-25D5-7F40-68A8-3631E0446E85"), this.getTooltipItemName(), MyGoConfig.twilight_giant_soul_stone_entity.get(), AttributeModifier.Operation.ADDITION));
            atts.put(ForgeMod.BLOCK_REACH.get(), new AttributeModifier(UUID.fromString("648F6F3A-D692-6DD0-B8E2-A7CD04D397D9"), this.getTooltipItemName(), MyGoConfig.twilight_giant_soul_stone_block.get(), AttributeModifier.Operation.ADDITION));
        }
        //灵灾
        if(ModList.get().isLoaded("malum")) {
            float amount = (float) (MyGoConfig.tainted_soul_stone_buff_speed.get() * 1);
            if (slotContext.entity() != null) {
                float health = slotContext.entity().getHealth();
                float maxHealth = slotContext.entity().getMaxHealth();
                float pct = health / maxHealth;
                amount *= (2 - pct);
            }
            atts.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(UUID.fromString("B187490F-B039-D283-4C54-AC91AD418645"), this.getTooltipItemName(),
                    amount+MyGoConfig.void_tablet_soul_stone_att_speed.get(), AttributeModifier.Operation.MULTIPLY_TOTAL));
            //灵魂护盾
            atts.put(Objects.requireNonNull(ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("malum", "soul_ward_capacity")))
                    , new AttributeModifier(UUID.fromString("282FFDD2-E890-13CA-7DF1-5874606F84ED"), this.getTooltipItemName(),
                            MyGoConfig.tainted_soul_stone_soul_shield_a.get()+MyGoConfig.soul_ward_capacity.get(), AttributeModifier.Operation.ADDITION));
            atts.put(Objects.requireNonNull(ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("malum", "soul_ward_integrity")))
                    , new AttributeModifier(UUID.fromString("7A25EEE5-E306-8997-C21D-FE94976E9224"), this.getTooltipItemName(),
                            MyGoConfig.tainted_soul_stone_soul_shield_b.get()+MyGoConfig.soul_ward_integrity.get(), AttributeModifier.Operation.MULTIPLY_BASE));

            ///法强
            atts.put(Objects.requireNonNull(ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("lodestone", "magic_proficiency")))
                    , new AttributeModifier(UUID.fromString("6569892C-108D-802B-C383-B887E3BACAED"), this.getTooltipItemName(),
                            MyGoConfig.tainted_soul_stone_magic.get()+MyGoConfig.void_tablet_soul_stone_att_magic.get(), AttributeModifier.Operation.MULTIPLY_BASE));
            //铁魔法
            if (ModList.get().isLoaded("irons_spellbooks")) {
                atts.put(Objects.requireNonNull(
                                ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("irons_spellbooks", "spell_power")))
                        , new AttributeModifier(UUID.fromString("8B637A1C-132F-1456-56C0-C4A4589D04E1"), this.getTooltipItemName(),
                                MyGoConfig.tainted_soul_stone_magic.get()+MyGoConfig.void_tablet_soul_stone_att_magic.get(), AttributeModifier.Operation.MULTIPLY_BASE));
            }
            atts.put(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(UUID.fromString("CA931206-D2F4-ED03-38B1-4E06DBD0F138"), this.getTooltipItemName(), MyGoConfig.void_tablet_soul_stone_att_armor.get(), AttributeModifier.Operation.MULTIPLY_TOTAL));
            atts.put(Attributes.MAX_HEALTH, new AttributeModifier(UUID.fromString("373D120A-947E-7536-C35F-A95F25772AFD"), this.getTooltipItemName(), MyGoConfig.void_tablet_soul_stone_att_hp.get(), AttributeModifier.Operation.MULTIPLY_TOTAL));
            //灵魂护盾
            atts.put(Objects.requireNonNull(ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("malum", "reserve_staff_charges")))
                    , new AttributeModifier(UUID.fromString("620D0CFF-C4DD-6A04-70CB-52D48C70CBA4"), this.getTooltipItemName(), MyGoConfig.void_tablet_soul_stone_att_staff.get(), AttributeModifier.Operation.ADDITION));

            atts.put(
                    Objects.requireNonNull(ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("malum", "spirit_spoils")))
                    , new AttributeModifier(UUID.fromString("15C9E345-A499-D567-3BCF-9E4950DFFA97"), getTooltipItemName(), MyGoConfig.spirit_soul_stone.get(), AttributeModifier.Operation.ADDITION));
            atts.put(
                    Objects.requireNonNull(ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("malum", "scythe_proficiency")))
                    , new AttributeModifier(UUID.fromString("5CC9E341-B84E-2544-0D53-7BE9324B5BC5"), getTooltipItemName(), MyGoConfig.scythe_soul_stone_att.get(), AttributeModifier.Operation.MULTIPLY_BASE));

            atts.put(Objects.requireNonNull(ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("malum", "soul_ward_recovery_rate")))
                    , new AttributeModifier(UUID.fromString("B79D882C-390F-F3EF-D1C8-47004E5F89D3"), this.getTooltipItemName(), MyGoConfig.soul_ward_recovery_rate.get(), AttributeModifier.Operation.MULTIPLY_BASE));
        }
        //神秘遗物
        if(ModList.get().isLoaded("enigmaticlegacy")) {
            atts.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(uuid, getTooltipItemName(), MyGoConfig.abyss_soul_stone_atk.get()+
                    MyGoConfig.abyss_soul_stone_atk_max.get(),AttributeModifier.Operation.ADDITION));
            atts.put(Attributes.ARMOR, new AttributeModifier(uuid, getTooltipItemName(),
                    MyGoConfig.abyss_soul_stone_armor_max.get(), AttributeModifier.Operation.ADDITION));
        }
        return atts;
    }
}
