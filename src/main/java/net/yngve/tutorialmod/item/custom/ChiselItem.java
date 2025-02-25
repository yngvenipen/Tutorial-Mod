package net.yngve.tutorialmod.item.custom;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;
import net.yngve.tutorialmod.block.ModBlocks;

import java.util.AbstractMap;
import java.util.Map;

public class ChiselItem extends Item {
    private static final Map<Block, Block> CHISEL_MAP =
            Map.ofEntries(
                    new AbstractMap.SimpleEntry<>(Blocks.STONE, Blocks.STONE_BRICKS),
                    new AbstractMap.SimpleEntry<>(Blocks.DEEPSLATE, Blocks.DEEPSLATE_BRICKS),
                    new AbstractMap.SimpleEntry<>(Blocks.TUFF, Blocks.TUFF_BRICKS),
                    new AbstractMap.SimpleEntry<>(Blocks.TERRACOTTA, Blocks.BRICKS),
                    new AbstractMap.SimpleEntry<>(Blocks.END_STONE, Blocks.END_STONE_BRICKS),
                    new AbstractMap.SimpleEntry<>(Blocks.PRISMARINE, Blocks.PRISMARINE_BRICKS),
                    new AbstractMap.SimpleEntry<>(Blocks.QUARTZ_BLOCK, Blocks.CHISELED_QUARTZ_BLOCK),
                    new AbstractMap.SimpleEntry<>(Blocks.BOOKSHELF, Blocks.CHISELED_BOOKSHELF),
                    new AbstractMap.SimpleEntry<>(Blocks.SANDSTONE, Blocks.CHISELED_SANDSTONE),
                    new AbstractMap.SimpleEntry<>(Blocks.NETHER_BRICKS, Blocks.CHISELED_NETHER_BRICKS),
                    new AbstractMap.SimpleEntry<>(Blocks.POLISHED_BLACKSTONE, Blocks.CHISELED_POLISHED_BLACKSTONE),
                    new AbstractMap.SimpleEntry<>(Blocks.RED_SANDSTONE, Blocks.CHISELED_RED_SANDSTONE),
                    new AbstractMap.SimpleEntry<>(Blocks.STONE_BRICKS, Blocks.CHISELED_STONE_BRICKS),
                    new AbstractMap.SimpleEntry<>(Blocks.TUFF_BRICKS, Blocks.CHISELED_TUFF_BRICKS)
            );

    public ChiselItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        Block clickedBlock = world.getBlockState(context.getBlockPos()).getBlock();

        if (CHISEL_MAP.containsKey(clickedBlock)) {
            if (!world.isClient()) {
                world.setBlockState(context.getBlockPos(), CHISEL_MAP.get(clickedBlock).getDefaultState());

                context.getStack().damage(1, ((ServerWorld) world), ((ServerPlayerEntity) context.getPlayer()),
                        item -> context.getPlayer().sendEquipmentBreakStatus(item, EquipmentSlot.MAINHAND));

                world.playSound(null, context.getBlockPos(), SoundEvents.BLOCK_GRINDSTONE_USE, SoundCategory.BLOCKS);
            }

        }

        return ActionResult.SUCCESS;
    }
}
