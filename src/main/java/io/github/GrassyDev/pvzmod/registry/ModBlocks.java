package io.github.GrassyDev.pvzmod.registry;

import io.github.GrassyDev.pvzmod.PvZCubed;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.quiltmc.qsl.block.extensions.api.QuiltBlockSettings;

public class ModBlocks {

    public static final Block GRASS_TILE = new Block(QuiltBlockSettings
            .of(Material.SOIL)
			.requiresTool()
            .sounds(BlockSoundGroup.GRASS)
            .strength(1.9f, 1200f));

    public static final Block DARK_GRASS_TILE = new Block(QuiltBlockSettings
            .of(Material.SOIL)
			.requiresTool()
            .sounds(BlockSoundGroup.GRASS)
            .strength(1.9f, 1200f));

	public static final Block ROOF_TILE = new Block(QuiltBlockSettings
			.of(Material.STONE)
			.requiresTool()
			.sounds(BlockSoundGroup.DEEPSLATE_BRICKS)
			.strength(1.9f, 1200f));

	public static final Block DARK_ROOF_TILE = new Block(QuiltBlockSettings
			.of(Material.STONE)
			.requiresTool()
			.sounds(BlockSoundGroup.DEEPSLATE_BRICKS)
			.strength(1.9f, 1200f));


	public static final Block UPGRADE_TILE = new Block(QuiltBlockSettings
			.of(Material.AMETHYST)
			.requiresTool()
			.sounds(BlockSoundGroup.AMETHYST_BLOCK)
			.strength(1.9f, 1200f)
			.luminance(3));


	public static final Block PREMIUM_TILE = new Block(QuiltBlockSettings
			.of(Material.AMETHYST)
			.requiresTool()
			.sounds(BlockSoundGroup.AMETHYST_BLOCK)
			.strength(1.9f, 1200f)
			.luminance(3));

	public static final Block EGYPT_TILE = new Block(QuiltBlockSettings
			.of(Material.STONE)
			.requiresTool()
			.sounds(BlockSoundGroup.STONE)
			.strength(1.9f, 1200f));


	public static final Block DARK_EGYPT_TILE = new Block(QuiltBlockSettings
			.of(Material.STONE)
			.requiresTool()
			.sounds(BlockSoundGroup.STONE)
			.strength(1.9f, 1200f));

	public static final Block PIRATE_TILE = new Block(QuiltBlockSettings
			.of(Material.WOOD)
			.requiresTool()
			.sounds(BlockSoundGroup.WOOD)
			.strength(1.9f, 1200f));


	public static final Block DARK_PIRATE_TILE = new Block(QuiltBlockSettings
			.of(Material.WOOD)
			.requiresTool()
			.sounds(BlockSoundGroup.WOOD)
			.strength(1.9f, 1200f));

	public static final Block WEST_TILE = new Block(QuiltBlockSettings
			.of(Material.STONE)
			.requiresTool()
			.sounds(BlockSoundGroup.STONE)
			.strength(1.9f, 1200f));


	public static final Block DARK_WEST_TILE = new Block(QuiltBlockSettings
			.of(Material.STONE)
			.requiresTool()
			.sounds(BlockSoundGroup.STONE)
			.strength(1.9f, 1200f));

	public static final Block NIGHT_TILE = new Block(QuiltBlockSettings
			.of(Material.STONE)
			.requiresTool()
			.sounds(BlockSoundGroup.STONE)
			.strength(1.9f, 1200f));

	public static final Block DARK_NIGHT_TILE = new Block(QuiltBlockSettings
			.of(Material.STONE)
			.requiresTool()
			.sounds(BlockSoundGroup.STONE)
			.strength(1.9f, 1200f));

	public static final Block SAND_TILE = new Block(QuiltBlockSettings
			.of(Material.SOIL)
			.requiresTool()
			.sounds(BlockSoundGroup.SAND)
			.strength(1.9f, 1200f));

	public static final Block DARK_SAND_TILE = new Block(QuiltBlockSettings
			.of(Material.SOIL)
			.requiresTool()
			.sounds(BlockSoundGroup.SAND)
			.strength(1.9f, 1200f));

	public static final Block UNDERWATER_TILE = new Block(QuiltBlockSettings
			.of(Material.SOIL)
			.requiresTool()
			.sounds(BlockSoundGroup.SAND)
			.strength(1.9f, 1200f));

	public static final Block DARK_UNDERWATER_TILE = new Block(QuiltBlockSettings
			.of(Material.SOIL)
			.requiresTool()
			.sounds(BlockSoundGroup.SAND)
			.strength(1.9f, 1200f));



	public static void registerBlocks(){

        Registry.register(Registry.BLOCK, new Identifier(PvZCubed.MOD_ID,"grass_tile"), GRASS_TILE);
        Registry.register(Registry.BLOCK, new Identifier(PvZCubed.MOD_ID,"dark_grass_tile"), DARK_GRASS_TILE);
		Registry.register(Registry.BLOCK, new Identifier(PvZCubed.MOD_ID,"roof_tile"), ROOF_TILE);
		Registry.register(Registry.BLOCK, new Identifier(PvZCubed.MOD_ID,"dark_roof_tile"), DARK_ROOF_TILE);
		Registry.register(Registry.BLOCK, new Identifier(PvZCubed.MOD_ID,"upgrade_tile"), UPGRADE_TILE);
		Registry.register(Registry.BLOCK, new Identifier(PvZCubed.MOD_ID,"premium_tile"), PREMIUM_TILE);
		Registry.register(Registry.BLOCK, new Identifier(PvZCubed.MOD_ID,"egypt_tile"), EGYPT_TILE);
		Registry.register(Registry.BLOCK, new Identifier(PvZCubed.MOD_ID,"dark_egypt_tile"), DARK_EGYPT_TILE);
		Registry.register(Registry.BLOCK, new Identifier(PvZCubed.MOD_ID,"pirate_tile"), PIRATE_TILE);
		Registry.register(Registry.BLOCK, new Identifier(PvZCubed.MOD_ID,"dark_pirate_tile"), DARK_PIRATE_TILE);
		Registry.register(Registry.BLOCK, new Identifier(PvZCubed.MOD_ID,"west_tile"), WEST_TILE);
		Registry.register(Registry.BLOCK, new Identifier(PvZCubed.MOD_ID,"dark_west_tile"), DARK_WEST_TILE);
		Registry.register(Registry.BLOCK, new Identifier(PvZCubed.MOD_ID,"night_tile"), NIGHT_TILE);
		Registry.register(Registry.BLOCK, new Identifier(PvZCubed.MOD_ID,"dark_night_tile"), DARK_NIGHT_TILE);
		Registry.register(Registry.BLOCK, new Identifier(PvZCubed.MOD_ID,"sand_tile"), SAND_TILE);
		Registry.register(Registry.BLOCK, new Identifier(PvZCubed.MOD_ID,"dark_sand_tile"), DARK_SAND_TILE);
		Registry.register(Registry.BLOCK, new Identifier(PvZCubed.MOD_ID,"underwater_tile"), UNDERWATER_TILE);
		Registry.register(Registry.BLOCK, new Identifier(PvZCubed.MOD_ID,"dark_underwater_tile"), DARK_UNDERWATER_TILE);

    }

}
