package io.github.GrassyDev.pvzmod.registry;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.items.*;
import io.github.GrassyDev.pvzmod.registry.items.seedpackets.*;
import io.github.GrassyDev.pvzmod.registry.items.spawneggs.*;
import io.github.GrassyDev.pvzmod.registry.items.toolclasses.PlantKillingMaterial;
import io.github.GrassyDev.pvzmod.registry.items.toolclasses.PlantKillingShovel;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ToolItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;

public class ModItems {

    public static final Item ALMANAC = new Item(new Item.Settings().maxCount(1));

    //Items
    public static final Item PLANTFOOD = new Item(new Item.Settings().maxCount(64));
	public static final Item PLANTFOOD_AQUATIC = new Item(new Item.Settings().maxCount(64));
	public static final Item PLANTFOOD_COLD = new Item(new Item.Settings().maxCount(64));
	public static final Item PLANTFOOD_FIRE = new Item(new Item.Settings().maxCount(64));
	public static final Item PLANTFOOD_FLOWER = new Item(new Item.Settings().maxCount(64));
	public static final Item PLANTFOOD_MUSHROOM = new Item(new Item.Settings().maxCount(64));
	public static final Item PLANTFOOD_TOUGH = new Item(new Item.Settings().maxCount(64));
    public static final Item PEA = new PeaItem(new Item.Settings().group(PvZCubed.PVZPLANTS).maxCount(32).rarity(Rarity.COMMON));
    public static final Item SPORE = new SporeItem(new Item.Settings().group(PvZCubed.PVZPLANTS).maxCount(64).rarity(Rarity.UNCOMMON));
    public static final Item FUME = new FumeItem(new Item.Settings().group(PvZCubed.PVZPLANTS).maxCount(8).rarity(Rarity.UNCOMMON));
	public static final Item HYPNO = new HypnoItem(new Item.Settings().group(PvZCubed.PVZPLANTS).maxCount(32).rarity(Rarity.EPIC));
    public static final Item SNOWPEAPROJ = new SnowPeaItem(new Item.Settings().group(PvZCubed.PVZPLANTS).maxCount(32).rarity(Rarity.UNCOMMON));
    public static final Item FIREPEA = new FirepeaItem(new Item.Settings().group(PvZCubed.PVZPLANTS).maxCount(16).rarity(Rarity.RARE));
	public static final Item CABBAGE = new CabbageItem(new Item.Settings().group(PvZCubed.PVZPLANTS).maxCount(32).rarity(Rarity.COMMON));
	public static final Item SNOWQUEENPEAPROJ = new SnowqueenpeaItem(new Item.Settings().group(PvZCubed.PVZPLANTS).maxCount(16).rarity(Rarity.EPIC));
	public static final Item COCONUT = new CoconutItem(new Item.Settings().group(PvZCubed.PVZPLANTS).maxCount(16).rarity(Rarity.RARE));
	public static final Item PLASMAPEA = new PlasmapeaItem(new Item.Settings().group(PvZCubed.PVZPLANTS).maxCount(16).rarity(Rarity.EPIC));
	public static final Item ICESPIKE = new IcespikeItem(new Item.Settings().group(PvZCubed.PVZPLANTS).maxCount(16).rarity(Rarity.EPIC));
	public static final Item BUBBLES = new BubblesItem(new Item.Settings().group(PvZCubed.PVZPLANTS).maxCount(16).rarity(Rarity.COMMON));
	public static final Item ARMORBUBBLE = new ArmorBubbleItem(new Item.Settings().group(PvZCubed.PVZPLANTS).maxCount(8).rarity(Rarity.UNCOMMON));
	public static final Item SPIT = new SpitItem(new Item.Settings().group(PvZCubed.PVZPLANTS).maxCount(32).rarity(Rarity.COMMON));
	public static final Item JINGLE = new JingleItem(new Item.Settings().group(PvZCubed.PVZPLANTS).maxCount(32).rarity(Rarity.COMMON));
    public static final Item SUN = new Item(new Item.Settings().group(PvZCubed.PVZPLANTS));
    public static final Item SMALLSUN = new Item(new Item.Settings().group(PvZCubed.PVZPLANTS));
    public static final Item LARGESUN = new Item(new Item.Settings().group(PvZCubed.PVZPLANTS));
    public static final Item WAVE_FLAG = new Item(new Item.Settings().group(PvZCubed.PVZZOMBIES));

    //Plant Spawn
    public static final Item SUNFLOWER_SEED_PACKET = new SunflowerSeeds(new Item.Settings().group(PvZCubed.PVZPLANTS).maxCount(8).rarity(Rarity.COMMON));
    public static final Item PEASHOOTER_SEED_PACKET = new PeashooterSeeds(new Item.Settings().group(PvZCubed.PVZPLANTS).maxCount(8).rarity(Rarity.COMMON));
    public static final Item CHERRYBOMB_SEED_PACKET = new CherryBombSeeds(new Item.Settings().group(PvZCubed.PVZPLANTS).maxCount(8).rarity(Rarity.UNCOMMON));
    public static final Item WALLNUT_SEED_PACKET = new WallnutSeeds(new Item.Settings().group(PvZCubed.PVZPLANTS).maxCount(8).rarity(Rarity.COMMON));
    public static final Item POTATOMINE_SEED_PACKET = new PotatoMineSeeds(new Item.Settings().group(PvZCubed.PVZPLANTS).maxCount(8).rarity(Rarity.COMMON));
    public static final Item SNOW_PEA_SEED_PACKET = new SnowpeaSeeds(new Item.Settings().group(PvZCubed.PVZPLANTS).maxCount(8).rarity(Rarity.UNCOMMON));
    public static final Item CHOMPER_SEED_PACKET = new ChomperSeeds(new Item.Settings().group(PvZCubed.PVZPLANTS).maxCount(8).rarity(Rarity.UNCOMMON));
    public static final Item REPEATER_SEED_PACKET = new RepeaterSeeds(new Item.Settings().group(PvZCubed.PVZPLANTS).maxCount(8).rarity(Rarity.UNCOMMON));
    public static final Item SUNSHROOM_SEED_PACKET = new SunshroomSeeds(new Item.Settings().group(PvZCubed.PVZPLANTS).maxCount(8).rarity(Rarity.COMMON));
    public static final Item PUFFSHROOM_SEED_PACKET = new PuffshroomSeeds(new Item.Settings().group(PvZCubed.PVZPLANTS).maxCount(16).rarity(Rarity.COMMON));
    public static final Item FUMESHROOM_SEED_PACKET = new FumeshroomSeeds(new Item.Settings().group(PvZCubed.PVZPLANTS).maxCount(8).rarity(Rarity.UNCOMMON));
    public static final Item GRAVEBUSTER_SEED_PACKET = new GraveBusterSeeds(new Item.Settings().group(PvZCubed.PVZPLANTS).maxCount(8).rarity(Rarity.COMMON));
    public static final Item HYPNOSHROOM_SEED_PACKET = new HypnoshroomSeeds(new Item.Settings().group(PvZCubed.PVZPLANTS).maxCount(8).rarity(Rarity.UNCOMMON));
    public static final Item SCAREDYSHROOM_SEED_PACKET = new ScaredyshroomSeeds(new Item.Settings().group(PvZCubed.PVZPLANTS).maxCount(8).rarity(Rarity.COMMON));
    public static final Item ICESHROOM_SEED_PACKET = new IceshroomSeeds(new Item.Settings().group(PvZCubed.PVZPLANTS).maxCount(8).rarity(Rarity.UNCOMMON));
    public static final Item DOOMSHROOM_SEED_PACKET = new DoomshroomSeeds(new Item.Settings().group(PvZCubed.PVZPLANTS).maxCount(8).rarity(Rarity.RARE));
	public static final Item LILYPAD_SEED_PACKET = new LilyPadSeeds(new Item.Settings().group(PvZCubed.PVZPLANTS).maxCount(16).rarity(Rarity.COMMON));
	public static final Item SQUASH_SEED_PACKET = new SquashSeeds(new Item.Settings().group(PvZCubed.PVZPLANTS).maxCount(8).rarity(Rarity.COMMON));
	public static final Item THREEPEATER_SEED_PACKET = new ThreepeaterSeeds(new Item.Settings().group(PvZCubed.PVZPLANTS).maxCount(8).rarity(Rarity.RARE));
	public static final Item TANGLEKELP_SEED_PACKET = new TangleKelpSeeds(new Item.Settings().group(PvZCubed.PVZPLANTS).maxCount(8).rarity(Rarity.COMMON));
	public static final Item JALAPENO_SEED_PACKET = new JalapenoSeeds(new Item.Settings().group(PvZCubed.PVZPLANTS).maxCount(8).rarity(Rarity.UNCOMMON));
	public static final Item SPIKEWEED_SEED_PACKET = new SpikeweedSeeds(new Item.Settings().group(PvZCubed.PVZPLANTS).maxCount(8).rarity(Rarity.COMMON));
	public static final Item CABBAGEPULT_SEED_PACKET = new CabbagepultSeeds(new Item.Settings().group(PvZCubed.PVZPLANTS).maxCount(8).rarity(Rarity.COMMON));
	public static final Item GATLINGPEA_SEED_PACKET = new GatlingpeaSeeds(new Item.Settings().group(PvZCubed.PVZPLANTS).maxCount(2).rarity(Rarity.EPIC));
	public static final Item TWINSUNFLOWER_SEED_PACKET = new TwinSunflowerSeeds(new Item.Settings().group(PvZCubed.PVZPLANTS).maxCount(2).rarity(Rarity.EPIC));
	public static final Item SPIKEROCK_SEED_PACKET = new SpikerockSeeds(new Item.Settings().group(PvZCubed.PVZPLANTS).maxCount(2).rarity(Rarity.EPIC));
	public static final Item SNOW_QUEENPEA_SEED_PACKET = new SnowqueenpeaSeeds(new Item.Settings().group(PvZCubed.PVZPLANTS).maxCount(8).rarity(Rarity.EPIC));
	public static final Item PERFOOMSHROOM_SEED_PACKET = new PerfoomshroomSeeds(new Item.Settings().group(PvZCubed.PVZPLANTS).maxCount(8).rarity(Rarity.EPIC));
	public static final Item ICEBERGLETTUCE_SEED_PACKET = new IcebergLettuceSeeds(new Item.Settings().group(PvZCubed.PVZPLANTS).maxCount(16).rarity(Rarity.COMMON));
	public static final Item COCONUTCANNON_SEED_PACKET = new CoconutCannonSeeds(new Item.Settings().group(PvZCubed.PVZPLANTS).maxCount(8).rarity(Rarity.RARE));
	public static final Item PEAPOD_SEED_PACKET = new PeaPodSeeds(new Item.Settings().group(PvZCubed.PVZPLANTS).maxCount(16).rarity(Rarity.UNCOMMON));
	public static final Item FIRE_PEA_SEED_PACKET = new FirepeaSeeds(new Item.Settings().group(PvZCubed.PVZPLANTS).maxCount(4).rarity(Rarity.EPIC));
	public static final Item NARCISSUS_SEED_PACKET = new NarcissusSeeds(new Item.Settings().group(PvZCubed.PVZPLANTS).maxCount(8).rarity(Rarity.UNCOMMON));
	public static final Item SMALLNUT_SEED_PACKET = new SmallnutSeeds(new Item.Settings().group(PvZCubed.PVZPLANTS).maxCount(16).rarity(Rarity.COMMON));
	public static final Item BUTTONSHROOM_SEED_PACKET = new ButtonshroomSeeds(new Item.Settings().group(PvZCubed.PVZPLANTS).maxCount(16).rarity(Rarity.COMMON));
	public static final Item BOMBSEEDLING_SEED_PACKET = new BombSeedlingSeeds(new Item.Settings().group(PvZCubed.PVZPLANTS).maxCount(16).rarity(Rarity.COMMON));
	public static final Item WEENIEBEANIE_SEED_PACKET = new WeenieBeanieSeeds(new Item.Settings().group(PvZCubed.PVZPLANTS).maxCount(16).rarity(Rarity.COMMON));
	public static final Item ADMIRALNAVYBEAN_SEED_PACKET = new AdmiralNavyBeanSeeds(new Item.Settings().group(PvZCubed.PVZPLANTS).maxCount(8).rarity(Rarity.UNCOMMON));
	public static final Item NAVYBEAN_SEED_PACKET = new NavyBeanSeeds(new Item.Settings().group(PvZCubed.PVZPLANTS).maxCount(8).rarity(Rarity.COMMON));
	public static final Item SUNFLOWERSEED_SEED_PACKET = new SunflowerSeedSeeds(new Item.Settings().group(PvZCubed.PVZPLANTS).maxCount(16).rarity(Rarity.COMMON));
	public static final Item BELLFLOWER_SEED_PACKET = new BellflowerSeeds(new Item.Settings().group(PvZCubed.PVZPLANTS).maxCount(8).rarity(Rarity.COMMON));

    //Zombie Spawn
    public static final Item BROWNCOATEGG = new BrowncoatEgg(new Item.Settings().group(PvZCubed.PVZZOMBIES).rarity(Rarity.UNCOMMON));
    public static final Item FLAGZOMBIEEGG = new FlagzombieEgg(new Item.Settings().group(PvZCubed.PVZZOMBIES).rarity(Rarity.RARE));
    public static final Item CONEHEADEGG = new ConeheadEgg(new Item.Settings().group(PvZCubed.PVZZOMBIES).rarity(Rarity.UNCOMMON));
    public static final Item POLEVAULTINGEGG = new PoleVaultingEgg(new Item.Settings().group(PvZCubed.PVZZOMBIES).rarity(Rarity.UNCOMMON));
    public static final Item BUCKETHEADEGG = new BucketheadEgg(new Item.Settings().group(PvZCubed.PVZZOMBIES).rarity(Rarity.UNCOMMON));
    public static final Item SCREENDOOREGG = new ScreendoorEgg(new Item.Settings().group(PvZCubed.PVZZOMBIES).rarity(Rarity.UNCOMMON));
    public static final Item NEWSPAPEREGG = new NewspaperEgg(new Item.Settings().group(PvZCubed.PVZZOMBIES).rarity(Rarity.UNCOMMON));
    public static final Item FOOTBALLEGG = new FootballEgg(new Item.Settings().group(PvZCubed.PVZZOMBIES).rarity(Rarity.RARE));
    public static final Item DANCINGZOMBIEEGG = new DancingZombieEgg(new Item.Settings().group(PvZCubed.PVZZOMBIES).rarity(Rarity.RARE));
    public static final Item BACKUPDANCEREGG = new BackupDancerEgg(new Item.Settings().group(PvZCubed.PVZZOMBIES).rarity(Rarity.UNCOMMON));
	public static final Item SNORKELEGG = new SnorkelEgg(new Item.Settings().group(PvZCubed.PVZZOMBIES).rarity(Rarity.EPIC));
	public static final Item GARGANTUAREGG = new GargantuarEgg(new Item.Settings().group(PvZCubed.PVZZOMBIES).rarity(Rarity.EPIC));
	public static final Item IMPEGG = new ImpEgg(new Item.Settings().group(PvZCubed.PVZZOMBIES).rarity(Rarity.RARE));
	public static final Item BERSERKEREGG = new BerserkerEgg(new Item.Settings().group(PvZCubed.PVZZOMBIES).rarity(Rarity.EPIC));
	public static final Item SUNDAYEDITIONEGG = new SundayEditionEgg(new Item.Settings().group(PvZCubed.PVZZOMBIES).rarity(Rarity.EPIC));
	public static final Item SUPERFANIMPEGG = new SuperFanImpEgg(new Item.Settings().group(PvZCubed.PVZZOMBIES).rarity(Rarity.RARE));
	public static final Item NEWYEARIMPEGG = new NewYearImpEgg(new Item.Settings().group(PvZCubed.PVZZOMBIES).rarity(Rarity.EPIC));
	public static final Item DEFENSIVEENDEGG = new DefensiveEndEgg(new Item.Settings().group(PvZCubed.PVZZOMBIES).rarity(Rarity.EPIC));
	public static final Item LOCUSTSWARMEGG = new LocustswarmEgg(new Item.Settings().group(PvZCubed.PVZZOMBIES).rarity(Rarity.EPIC));

    //Grave Spawn
    public static final Item BASICGRAVESPAWN = new BasicGraveSpawn(new Item.Settings().group(PvZCubed.PVZGRAVES).rarity(Rarity.COMMON));
    public static final Item NIGHTGRAVESPAWN = new NightGraveSpawn(new Item.Settings().group(PvZCubed.PVZGRAVES).rarity(Rarity.UNCOMMON));
	public static final Item POOLGRAVESPAWN = new PoolGraveSpawn(new Item.Settings().group(PvZCubed.PVZGRAVES).rarity(Rarity.RARE));

    //Blocks
    public static final BlockItem GRASS_TILE = new BlockItem(ModBlocks.GRASS_TILE, new Item.Settings().group(PvZCubed.PVZBLOCKS));
    public static final BlockItem DARK_GRASS_TILE = new BlockItem(ModBlocks.DARK_GRASS_TILE, new Item.Settings().group(PvZCubed.PVZBLOCKS));
	public static final BlockItem ROOF_TILE = new BlockItem(ModBlocks.ROOF_TILE, new Item.Settings().group(PvZCubed.PVZBLOCKS));
	public static final BlockItem DARK_ROOF_TILE = new BlockItem(ModBlocks.DARK_ROOF_TILE, new Item.Settings().group(PvZCubed.PVZBLOCKS));
	public static final BlockItem UPGRADE_TILE = new BlockItem(ModBlocks.UPGRADE_TILE, new Item.Settings().group(PvZCubed.PVZBLOCKS));
	public static final BlockItem PREMIUM_TILE = new BlockItem(ModBlocks.PREMIUM_TILE, new Item.Settings().group(PvZCubed.PVZBLOCKS));
	public static final BlockItem EGYPT_TILE = new BlockItem(ModBlocks.EGYPT_TILE, new Item.Settings().group(PvZCubed.PVZBLOCKS));
	public static final BlockItem DARK_EGYPT_TILE = new BlockItem(ModBlocks.DARK_EGYPT_TILE, new Item.Settings().group(PvZCubed.PVZBLOCKS));
	public static final BlockItem PIRATE_TILE = new BlockItem(ModBlocks.PIRATE_TILE, new Item.Settings().group(PvZCubed.PVZBLOCKS));
	public static final BlockItem DARK_PIRATE_TILE = new BlockItem(ModBlocks.DARK_PIRATE_TILE, new Item.Settings().group(PvZCubed.PVZBLOCKS));
	public static final BlockItem WEST_TILE = new BlockItem(ModBlocks.WEST_TILE, new Item.Settings().group(PvZCubed.PVZBLOCKS));
	public static final BlockItem DARK_WEST_TILE = new BlockItem(ModBlocks.DARK_WEST_TILE, new Item.Settings().group(PvZCubed.PVZBLOCKS));
	public static final BlockItem NIGHT_TILE = new BlockItem(ModBlocks.NIGHT_TILE, new Item.Settings().group(PvZCubed.PVZBLOCKS));
	public static final BlockItem DARK_NIGHT_TILE = new BlockItem(ModBlocks.DARK_NIGHT_TILE, new Item.Settings().group(PvZCubed.PVZBLOCKS));
	public static final BlockItem SAND_TILE = new BlockItem(ModBlocks.SAND_TILE, new Item.Settings().group(PvZCubed.PVZBLOCKS));
	public static final BlockItem DARK_SAND_TILE = new BlockItem(ModBlocks.DARK_SAND_TILE, new Item.Settings().group(PvZCubed.PVZBLOCKS));
	public static final BlockItem UNDERWATER_TILE = new BlockItem(ModBlocks.UNDERWATER_TILE, new Item.Settings().group(PvZCubed.PVZBLOCKS));
	public static final BlockItem DARK_UNDERWATER_TILE = new BlockItem(ModBlocks.DARK_UNDERWATER_TILE, new Item.Settings().group(PvZCubed.PVZBLOCKS));

    //Tools
    public static ToolItem DAVES_SHOVEL = new PlantKillingShovel(PlantKillingMaterial.INSTANCE, 2, 0F, new Item.Settings().group(PvZCubed.PVZPLANTS).rarity(Rarity.EPIC));


    //addItem
    //addBlock
    public static void registerItems() {
        Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"icon_almanac"), ALMANAC);
        Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"plantfood"), PLANTFOOD);
		Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"plantfood_aquatic"), PLANTFOOD_AQUATIC);
		Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"plantfood_cold"), PLANTFOOD_COLD);
		Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"plantfood_fire"), PLANTFOOD_FIRE);
		Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"plantfood_flower"), PLANTFOOD_FLOWER);
		Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"plantfood_mushroom"), PLANTFOOD_MUSHROOM);
		Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"plantfood_tough"), PLANTFOOD_TOUGH);
        Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"pea"), PEA);
        Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"spore"), SPORE);
        Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"fume"), FUME);
		Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"hypno"), HYPNO);
        Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"snowpeaproj"), SNOWPEAPROJ);
		Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"firepea"), FIREPEA);
		Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"cabbage"), CABBAGE);
		Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"snowqueenpeaproj"), SNOWQUEENPEAPROJ);
		Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"coconut"), COCONUT);
		Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"plasmapea"), PLASMAPEA);
		Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"icespike"), ICESPIKE);
		Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"bubblesitem"), BUBBLES);
		Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"armorbubbleitem"), ARMORBUBBLE);
		Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"spit"), SPIT);
		Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"jingle"), JINGLE);
        Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"sun"), SUN);
        Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"smallsun"), SMALLSUN);
        Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"largesun"), LARGESUN);
        Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"wave_flag"), WAVE_FLAG);
        Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"grass_tile"), GRASS_TILE);
        Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"dark_grass_tile"), DARK_GRASS_TILE);
		Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"roof_tile"), ROOF_TILE);
		Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"dark_roof_tile"), DARK_ROOF_TILE);
		Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"upgrade_tile"), UPGRADE_TILE);
		Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"premium_tile"), PREMIUM_TILE);
		Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"egypt_tile"), EGYPT_TILE);
		Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"dark_egypt_tile"), DARK_EGYPT_TILE);
		Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"pirate_tile"), PIRATE_TILE);
		Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"dark_pirate_tile"), DARK_PIRATE_TILE);
		Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"west_tile"), WEST_TILE);
		Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"dark_west_tile"), DARK_WEST_TILE);
		Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"night_tile"), NIGHT_TILE);
		Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"dark_night_tile"), DARK_NIGHT_TILE);
		Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"sand_tile"), SAND_TILE);
		Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"dark_sand_tile"), DARK_SAND_TILE);
		Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"underwater_tile"), UNDERWATER_TILE);
		Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"dark_underwater_tile"), DARK_UNDERWATER_TILE);
        Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"sunflower_seed_packet"), SUNFLOWER_SEED_PACKET);
        Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"peashooter_seed_packet"), PEASHOOTER_SEED_PACKET);
        Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"cherrybomb_seed_packet"), CHERRYBOMB_SEED_PACKET);
        Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"wallnut_seed_packet"), WALLNUT_SEED_PACKET);
        Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"potatomine_seed_packet"), POTATOMINE_SEED_PACKET);
        Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"snowpea_seed_packet"), SNOW_PEA_SEED_PACKET);
        Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"chomper_seed_packet"), CHOMPER_SEED_PACKET);
        Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"repeater_seed_packet"), REPEATER_SEED_PACKET);
        Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"sunshroom_seed_packet"), SUNSHROOM_SEED_PACKET);
        Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"puffshroom_seed_packet"), PUFFSHROOM_SEED_PACKET);
        Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"fumeshroom_seed_packet"), FUMESHROOM_SEED_PACKET);
        Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"gravebuster_seed_packet"), GRAVEBUSTER_SEED_PACKET);
        Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"hypnoshroom_seed_packet"), HYPNOSHROOM_SEED_PACKET);
        Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"scaredyshroom_seed_packet"), SCAREDYSHROOM_SEED_PACKET);
        Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"iceshroom_seed_packet"), ICESHROOM_SEED_PACKET);
        Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"doomshroom_seed_packet"), DOOMSHROOM_SEED_PACKET);
		Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"lilypad_seed_packet"), LILYPAD_SEED_PACKET);
		Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"squash_seed_packet"), SQUASH_SEED_PACKET);
		Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"tanglekelp_seed_packet"), TANGLEKELP_SEED_PACKET);
		Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"jalapeno_seed_packet"), JALAPENO_SEED_PACKET);
		Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"spikeweed_seed_packet"), SPIKEWEED_SEED_PACKET);
        Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"threepeater_seed_packet"), THREEPEATER_SEED_PACKET);
		Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"cabbagepult_seed_packet"), CABBAGEPULT_SEED_PACKET);
		Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"gatlingpea_seed_packet"), GATLINGPEA_SEED_PACKET);
		Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"twinsunflower_seed_packet"), TWINSUNFLOWER_SEED_PACKET);
		Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"spikerock_seed_packet"), SPIKEROCK_SEED_PACKET);
		Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"snowqueenpea_seed_packet"), SNOW_QUEENPEA_SEED_PACKET);
		Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"perfoomshroom_seed_packet"), PERFOOMSHROOM_SEED_PACKET);
		Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"iceberglettuce_seed_packet"), ICEBERGLETTUCE_SEED_PACKET);
		Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"coconutcannon_seed_packet"), COCONUTCANNON_SEED_PACKET);
		Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"peapod_seed_packet"), PEAPOD_SEED_PACKET);
		Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"firepea_seed_packet"), FIRE_PEA_SEED_PACKET);
		Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"narcissus_seed_packet"), NARCISSUS_SEED_PACKET);
		Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"smallnut_seed_packet"), SMALLNUT_SEED_PACKET);
		Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"buttonshroom_seed_packet"), BUTTONSHROOM_SEED_PACKET);
		Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"bombseedling_seed_packet"), BOMBSEEDLING_SEED_PACKET);
		Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"weeniebeanie_seed_packet"), WEENIEBEANIE_SEED_PACKET);
		Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"navybean_seed_packet"), NAVYBEAN_SEED_PACKET);
		Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"admiralnavybean_seed_packet"), ADMIRALNAVYBEAN_SEED_PACKET);
		Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"sunflowerseed_seed_packet"), SUNFLOWERSEED_SEED_PACKET);
		Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"bellflower_seed_packet"), BELLFLOWER_SEED_PACKET);
        Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"browncoat_egg"), BROWNCOATEGG);
        Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"flagzombie_egg"), FLAGZOMBIEEGG);
        Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"conehead_egg"), CONEHEADEGG);
        Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"polevaulting_egg"), POLEVAULTINGEGG);
        Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"buckethead_egg"), BUCKETHEADEGG);
        Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"screendoor_egg"), SCREENDOOREGG);
        Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"newspaper_egg"), NEWSPAPEREGG);
        Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"football_egg"), FOOTBALLEGG);
        Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"dancingzombie_egg"), DANCINGZOMBIEEGG);
        Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"backupdancer_egg"), BACKUPDANCEREGG);
		Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"snorkel_egg"), SNORKELEGG);
		Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"gargantuar_egg"), GARGANTUAREGG);
		Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"imp_egg"), IMPEGG);
		Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"berserker_egg"), BERSERKEREGG);
		Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"sundayedition_egg"), SUNDAYEDITIONEGG);
		Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"superfanimp_egg"), SUPERFANIMPEGG);
		Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"newyearimp_egg"), NEWYEARIMPEGG);
		Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"defensiveend_egg"), DEFENSIVEENDEGG);
		Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"locustswarm_egg"), LOCUSTSWARMEGG);
        Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"basicgrave_spawn"), BASICGRAVESPAWN);
        Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"nightgrave_spawn"), NIGHTGRAVESPAWN);
		Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"poolgrave_spawn"), POOLGRAVESPAWN);
        Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"daves_shovel"), DAVES_SHOVEL);

    }

}
