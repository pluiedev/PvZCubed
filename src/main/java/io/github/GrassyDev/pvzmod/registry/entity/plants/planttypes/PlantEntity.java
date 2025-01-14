package io.github.GrassyDev.pvzmod.registry.entity.plants.planttypes;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.ModItems;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.day.cherrybomb.CherrybombEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.day.chomper.ChomperEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.night.doomshroom.DoomshroomEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.pool.jalapeno.JalapenoEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.roof.cabbagepult.CabbagepultEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.ancientegypt.iceberglettuce.IcebergLettuceEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.gemium.flamingpea.FlamingpeaEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.night.fumeshroom.FumeshroomEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.night.hypnoshroom.HypnoshroomEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.night.iceshroom.IceshroomEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.pool.lilypad.LilyPadEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.wildwest.peapod.PeapodEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.day.peashooter.PeashooterEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzgw.perfoomshroom.PerfoomshroomEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.night.puffshroom.PuffshroomEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.day.repeater.RepeaterEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.night.scaredyshroom.ScaredyshroomEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.day.snowpea.SnowpeaEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzadventures.snowqueenpea.SnowqueenpeaEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.pool.squash.SquashEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.day.sunflower.SunflowerEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.night.sunshroom.SunshroomEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.pool.threepeater.ThreepeaterEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.day.wallnutentity.WallnutEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzheroes.bellflower.BellflowerEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzheroes.bombseedling.BombSeedlingEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzheroes.buttonshroom.ButtonshroomEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzheroes.smallnut.SmallNutEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzheroes.sunflowerseed.SunflowerSeedEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzheroes.weeniebeanie.WeenieBeanieEntity;
import io.github.GrassyDev.pvzmod.registry.items.seedpackets.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

public abstract class PlantEntity extends GolemEntity {

	public boolean onWater;

	protected PlantEntity(EntityType<? extends GolemEntity> entityType, World world) {
		super(entityType, world);
	}

	public void rideLilyPad(LivingEntity livingEntity){
		this.refreshPositionAndAngles(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), livingEntity.bodyYaw, 0.0F);
		this.startRiding(livingEntity);
	}

	public void tick() {
		super.tick();
		Entity vehicle = this.getVehicle();
		if (vehicle instanceof LilyPadEntity){
			vehicle.setBodyYaw(this.bodyYaw);
		}
	}

	public HitResult amphibiousRaycast(double maxDistance) {
		Vec3d vec3d1 = this.getPos();
		Vec3d vec3d2 = new Vec3d(vec3d1.x, vec3d1.y - maxDistance, vec3d1.z);
		return this.world.raycast(new RaycastContext(vec3d1, vec3d2, RaycastContext.ShapeType.OUTLINE, RaycastContext.FluidHandling.ANY, this));
	}


	public ActionResult addPlants(PlayerEntity player, Hand hand) {
		ItemStack itemStack = player.getStackInHand(hand);
		Item item = itemStack.getItem();
		SoundEvent sound = null;
		boolean itemCooldown = player.getItemCooldownManager().isCoolingDown(item);
		if (this instanceof LilyPadEntity lilyPadEntity) {
			if (onWater) {
				sound = SoundEvents.ENTITY_PLAYER_SPLASH_HIGH_SPEED;
			} else {
				sound = PvZCubed.PLANTPLANTEDEVENT;
			}
			lilyPadEntity.setPuffshroomPermanency(LilyPadEntity.PuffPermanency.PERMANENT);
		}

		if (this.getPassengerList().isEmpty()) {
			/**PEASHOOTER**/
			float volume = 0.6f;
			if (itemStack.isOf(ModItems.PEASHOOTER_SEED_PACKET) && !itemCooldown) {
				if (world instanceof ServerWorld) {
					ServerWorld serverWorld = (ServerWorld) world;
					PeashooterEntity plantEntity = (PeashooterEntity) PvZEntity.PEASHOOTER.create(serverWorld, itemStack.getNbt(), (Text) null, player, this.getBlockPos(), SpawnReason.SPAWN_EGG, true, true);
					if (plantEntity == null) {
						return ActionResult.FAIL;
					}

					float f = (float) MathHelper.floor((MathHelper.wrapDegrees(player.getYaw() - 180.0F) + 22.5F) / 45.0F) * 45.0F;
					plantEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), f, 0.0F);
					world.spawnEntity(plantEntity);
					plantEntity.rideLilyPad(this);
					world.playSound((PlayerEntity) null, plantEntity.getX(), plantEntity.getY(), plantEntity.getZ(), sound, SoundCategory.BLOCKS, volume, 0.8F);
				}
				if (!player.getAbilities().creativeMode) {
					itemStack.decrement(1);
					player.getItemCooldownManager().set(ModItems.PEASHOOTER_SEED_PACKET, PeashooterSeeds.cooldown);
				}
				return ActionResult.SUCCESS;
			}

			/**SUNFLOWER**/
			if (itemStack.isOf(ModItems.SUNFLOWER_SEED_PACKET) && !itemCooldown) {
				if (world instanceof ServerWorld) {
					ServerWorld serverWorld = (ServerWorld) world;
					SunflowerEntity plantEntity = (SunflowerEntity) PvZEntity.SUNFLOWER.create(serverWorld, itemStack.getNbt(), (Text) null, player, this.getBlockPos(), SpawnReason.SPAWN_EGG, true, true);
					if (plantEntity == null) {
						return ActionResult.FAIL;
					}

					float f = (float) MathHelper.floor((MathHelper.wrapDegrees(player.getYaw() - 180.0F) + 22.5F) / 45.0F) * 45.0F;
					plantEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), f, 0.0F);
					world.spawnEntity(plantEntity);
					plantEntity.rideLilyPad(this);
					world.playSound((PlayerEntity) null, plantEntity.getX(), plantEntity.getY(), plantEntity.getZ(), sound, SoundCategory.BLOCKS, volume, 0.8F);
					plantEntity.sunProducingTime = 600;
					plantEntity.playSound(PvZCubed.SUNDROPEVENT, 0.5F, 1F);
					plantEntity.dropItem(ModItems.SUN);
					plantEntity.dropItem(ModItems.SUN);
				}
				if (!player.getAbilities().creativeMode) {
					itemStack.decrement(1);
					player.getItemCooldownManager().set(ModItems.SUNFLOWER_SEED_PACKET, SunflowerSeeds.cooldown);
				}
				return ActionResult.SUCCESS;
			}

			/**CHERRY BOMB**/
			if (itemStack.isOf(ModItems.CHERRYBOMB_SEED_PACKET) && !itemCooldown) {
				if (world instanceof ServerWorld) {
					ServerWorld serverWorld = (ServerWorld) world;
					CherrybombEntity plantEntity = (CherrybombEntity) PvZEntity.CHERRYBOMB.create(serverWorld, itemStack.getNbt(), (Text) null, player, this.getBlockPos(), SpawnReason.SPAWN_EGG, true, true);
					if (plantEntity == null) {
						return ActionResult.FAIL;
					}

					float f = (float) MathHelper.floor((MathHelper.wrapDegrees(player.getYaw() - 180.0F) + 22.5F) / 45.0F) * 45.0F;
					plantEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), f, 0.0F);
					world.spawnEntity(plantEntity);
					plantEntity.rideLilyPad(this);
					world.playSound((PlayerEntity) null, plantEntity.getX(), plantEntity.getY(), plantEntity.getZ(), sound, SoundCategory.BLOCKS, volume, 0.8F);
				}
				if (!player.getAbilities().creativeMode) {
					itemStack.decrement(1);
					player.getItemCooldownManager().set(ModItems.CHERRYBOMB_SEED_PACKET, CherryBombSeeds.cooldown);
				}
				return ActionResult.SUCCESS;
			}

			/**WALLNUT**/
			if (itemStack.isOf(ModItems.WALLNUT_SEED_PACKET) && !itemCooldown) {
				if (world instanceof ServerWorld) {
					ServerWorld serverWorld = (ServerWorld) world;
					WallnutEntity plantEntity = (WallnutEntity) PvZEntity.WALLNUT.create(serverWorld, itemStack.getNbt(), (Text) null, player, this.getBlockPos(), SpawnReason.SPAWN_EGG, true, true);
					if (plantEntity == null) {
						return ActionResult.FAIL;
					}

					float f = (float) MathHelper.floor((MathHelper.wrapDegrees(player.getYaw() - 180.0F) + 22.5F) / 45.0F) * 45.0F;
					plantEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), f, 0.0F);
					world.spawnEntity(plantEntity);
					plantEntity.rideLilyPad(this);
					world.playSound((PlayerEntity) null, plantEntity.getX(), plantEntity.getY(), plantEntity.getZ(), sound, SoundCategory.BLOCKS, volume, 0.8F);
				}
				if (!player.getAbilities().creativeMode) {
					itemStack.decrement(1);
					player.getItemCooldownManager().set(ModItems.WALLNUT_SEED_PACKET, WallnutSeeds.cooldown);
				}
				return ActionResult.SUCCESS;
			}

			/**SNOW PEA**/
			if (itemStack.isOf(ModItems.SNOW_PEA_SEED_PACKET) && !itemCooldown) {
				if (world instanceof ServerWorld) {
					ServerWorld serverWorld = (ServerWorld) world;
					SnowpeaEntity plantEntity = (SnowpeaEntity) PvZEntity.SNOWPEA.create(serverWorld, itemStack.getNbt(), (Text) null, player, this.getBlockPos(), SpawnReason.SPAWN_EGG, true, true);
					if (plantEntity == null) {
						return ActionResult.FAIL;
					}

					float f = (float) MathHelper.floor((MathHelper.wrapDegrees(player.getYaw() - 180.0F) + 22.5F) / 45.0F) * 45.0F;
					plantEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), f, 0.0F);
					world.spawnEntity(plantEntity);
					plantEntity.rideLilyPad(this);
					world.playSound((PlayerEntity) null, plantEntity.getX(), plantEntity.getY(), plantEntity.getZ(), sound, SoundCategory.BLOCKS, volume, 0.8F);
				}
				if (!player.getAbilities().creativeMode) {
					itemStack.decrement(1);
					player.getItemCooldownManager().set(ModItems.SNOW_PEA_SEED_PACKET, SnowpeaSeeds.cooldown);
				}
				return ActionResult.SUCCESS;
			}

			/**CHOMPER**/
			if (itemStack.isOf(ModItems.CHOMPER_SEED_PACKET) && !itemCooldown) {
				if (world instanceof ServerWorld) {
					ServerWorld serverWorld = (ServerWorld) world;
					ChomperEntity plantEntity = (ChomperEntity) PvZEntity.CHOMPER.create(serverWorld, itemStack.getNbt(), (Text) null, player, this.getBlockPos(), SpawnReason.SPAWN_EGG, true, true);
					if (plantEntity == null) {
						return ActionResult.FAIL;
					}

					float f = (float) MathHelper.floor((MathHelper.wrapDegrees(player.getYaw() - 180.0F) + 22.5F) / 45.0F) * 45.0F;
					plantEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), f, 0.0F);
					world.spawnEntity(plantEntity);
					plantEntity.rideLilyPad(this);
					world.playSound((PlayerEntity) null, plantEntity.getX(), plantEntity.getY(), plantEntity.getZ(), sound, SoundCategory.BLOCKS, volume, 0.8F);
				}
				if (!player.getAbilities().creativeMode) {
					itemStack.decrement(1);
					player.getItemCooldownManager().set(ModItems.CHOMPER_SEED_PACKET, ChomperSeeds.cooldown);
				}
				return ActionResult.SUCCESS;
			}

			/**REPEATER**/
			if (itemStack.isOf(ModItems.REPEATER_SEED_PACKET) && !itemCooldown) {
				if (world instanceof ServerWorld) {
					ServerWorld serverWorld = (ServerWorld) world;
					RepeaterEntity plantEntity = (RepeaterEntity) PvZEntity.REPEATER.create(serverWorld, itemStack.getNbt(), (Text) null, player, this.getBlockPos(), SpawnReason.SPAWN_EGG, true, true);
					if (plantEntity == null) {
						return ActionResult.FAIL;
					}

					float f = (float) MathHelper.floor((MathHelper.wrapDegrees(player.getYaw() - 180.0F) + 22.5F) / 45.0F) * 45.0F;
					plantEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), f, 0.0F);
					world.spawnEntity(plantEntity);
					plantEntity.rideLilyPad(this);
					world.playSound((PlayerEntity) null, plantEntity.getX(), plantEntity.getY(), plantEntity.getZ(), sound, SoundCategory.BLOCKS, volume, 0.8F);
				}
				if (!player.getAbilities().creativeMode) {
					itemStack.decrement(1);
					player.getItemCooldownManager().set(ModItems.REPEATER_SEED_PACKET, RepeaterSeeds.cooldown);
				}
				return ActionResult.SUCCESS;
			}

			/**PUFF-SHROOM**/
			if (itemStack.isOf(ModItems.PUFFSHROOM_SEED_PACKET) && !itemCooldown) {
				if (world instanceof ServerWorld) {
					ServerWorld serverWorld = (ServerWorld) world;
					PuffshroomEntity plantEntity = (PuffshroomEntity) PvZEntity.PUFFSHROOM.create(serverWorld, itemStack.getNbt(), (Text) null, player, this.getBlockPos(), SpawnReason.SPAWN_EGG, true, true);
					if (plantEntity == null) {
						return ActionResult.FAIL;
					}

					float f = (float) MathHelper.floor((MathHelper.wrapDegrees(player.getYaw() - 180.0F) + 22.5F) / 45.0F) * 45.0F;
					plantEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), f, 0.0F);
					world.spawnEntity(plantEntity);
					plantEntity.rideLilyPad(this);
					plantEntity.setPuffshroomPermanency(PuffshroomEntity.PuffPermanency.PERMANENT);
					world.playSound((PlayerEntity) null, plantEntity.getX(), plantEntity.getY(), plantEntity.getZ(), sound, SoundCategory.BLOCKS, volume, 0.8F);
				}
				if (!player.getAbilities().creativeMode) {
					itemStack.decrement(1);
					player.getItemCooldownManager().set(ModItems.PUFFSHROOM_SEED_PACKET, PuffshroomSeeds.cooldown);
				}
				return ActionResult.SUCCESS;
			}

			/**SUN-SHROOM**/
			if (itemStack.isOf(ModItems.SUNSHROOM_SEED_PACKET) && !itemCooldown) {
				if (world instanceof ServerWorld) {
					ServerWorld serverWorld = (ServerWorld) world;
					SunshroomEntity plantEntity = (SunshroomEntity) PvZEntity.SUNSHROOM.create(serverWorld, itemStack.getNbt(), (Text) null, player, this.getBlockPos(), SpawnReason.SPAWN_EGG, true, true);
					if (plantEntity == null) {
						return ActionResult.FAIL;
					}

					float f = (float) MathHelper.floor((MathHelper.wrapDegrees(player.getYaw() - 180.0F) + 22.5F) / 45.0F) * 45.0F;
					plantEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), f, 0.0F);
					world.spawnEntity(plantEntity);
					plantEntity.rideLilyPad(this);
					world.playSound((PlayerEntity) null, plantEntity.getX(), plantEntity.getY(), plantEntity.getZ(), sound, SoundCategory.BLOCKS, volume, 0.8F);
					plantEntity.sunProducingTime = 100;
				}
				if (!player.getAbilities().creativeMode) {
					itemStack.decrement(1);
					player.getItemCooldownManager().set(ModItems.SUNSHROOM_SEED_PACKET, SunshroomSeeds.cooldown);
				}
				return ActionResult.SUCCESS;
			}

			/**FUME-SHROOM**/
			if (itemStack.isOf(ModItems.FUMESHROOM_SEED_PACKET) && !itemCooldown) {
				if (world instanceof ServerWorld) {
					ServerWorld serverWorld = (ServerWorld) world;
					FumeshroomEntity plantEntity = (FumeshroomEntity) PvZEntity.FUMESHROOM.create(serverWorld, itemStack.getNbt(), (Text) null, player, this.getBlockPos(), SpawnReason.SPAWN_EGG, true, true);
					if (plantEntity == null) {
						return ActionResult.FAIL;
					}

					float f = (float) MathHelper.floor((MathHelper.wrapDegrees(player.getYaw() - 180.0F) + 22.5F) / 45.0F) * 45.0F;
					plantEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), f, 0.0F);
					world.spawnEntity(plantEntity);
					plantEntity.rideLilyPad(this);
					world.playSound((PlayerEntity) null, plantEntity.getX(), plantEntity.getY(), plantEntity.getZ(), sound, SoundCategory.BLOCKS, volume, 0.8F);
				}
				if (!player.getAbilities().creativeMode) {
					itemStack.decrement(1);
					player.getItemCooldownManager().set(ModItems.FUMESHROOM_SEED_PACKET, FumeshroomSeeds.cooldown);
				}
				return ActionResult.SUCCESS;
			}

			/**HYPNO-SHROOM**/
			if (itemStack.isOf(ModItems.HYPNOSHROOM_SEED_PACKET) && !itemCooldown) {
				if (world instanceof ServerWorld) {
					ServerWorld serverWorld = (ServerWorld) world;
					HypnoshroomEntity plantEntity = (HypnoshroomEntity) PvZEntity.HYPNOSHROOM.create(serverWorld, itemStack.getNbt(), (Text) null, player, this.getBlockPos(), SpawnReason.SPAWN_EGG, true, true);
					if (plantEntity == null) {
						return ActionResult.FAIL;
					}

					float f = (float) MathHelper.floor((MathHelper.wrapDegrees(player.getYaw() - 180.0F) + 22.5F) / 45.0F) * 45.0F;
					plantEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), f, 0.0F);
					world.spawnEntity(plantEntity);
					plantEntity.rideLilyPad(this);
					world.playSound((PlayerEntity) null, plantEntity.getX(), plantEntity.getY(), plantEntity.getZ(), sound, SoundCategory.BLOCKS, volume, 0.8F);
				}
				if (!player.getAbilities().creativeMode) {
					itemStack.decrement(1);
					player.getItemCooldownManager().set(ModItems.HYPNOSHROOM_SEED_PACKET, HypnoshroomSeeds.cooldown);
				}
				return ActionResult.SUCCESS;
			}

			/**SCAREDY-SHROOM**/
			if (itemStack.isOf(ModItems.SCAREDYSHROOM_SEED_PACKET) && !itemCooldown) {
				if (world instanceof ServerWorld) {
					ServerWorld serverWorld = (ServerWorld) world;
					ScaredyshroomEntity plantEntity = (ScaredyshroomEntity) PvZEntity.SCAREDYSHROOM.create(serverWorld, itemStack.getNbt(), (Text) null, player, this.getBlockPos(), SpawnReason.SPAWN_EGG, true, true);
					if (plantEntity == null) {
						return ActionResult.FAIL;
					}

					float f = (float) MathHelper.floor((MathHelper.wrapDegrees(player.getYaw() - 180.0F) + 22.5F) / 45.0F) * 45.0F;
					plantEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), f, 0.0F);
					world.spawnEntity(plantEntity);
					plantEntity.rideLilyPad(this);
					world.playSound((PlayerEntity) null, plantEntity.getX(), plantEntity.getY(), plantEntity.getZ(), sound, SoundCategory.BLOCKS, volume, 0.8F);
				}
				if (!player.getAbilities().creativeMode) {
					itemStack.decrement(1);
					player.getItemCooldownManager().set(ModItems.SCAREDYSHROOM_SEED_PACKET, ScaredyshroomSeeds.cooldown);
				}
				return ActionResult.SUCCESS;
			}

			/**ICE-SHROOM**/
			if (itemStack.isOf(ModItems.ICESHROOM_SEED_PACKET) && !itemCooldown) {
				if (world instanceof ServerWorld) {
					ServerWorld serverWorld = (ServerWorld) world;
					IceshroomEntity plantEntity = (IceshroomEntity) PvZEntity.ICESHROOM.create(serverWorld, itemStack.getNbt(), (Text) null, player, this.getBlockPos(), SpawnReason.SPAWN_EGG, true, true);
					if (plantEntity == null) {
						return ActionResult.FAIL;
					}

					float f = (float) MathHelper.floor((MathHelper.wrapDegrees(player.getYaw() - 180.0F) + 22.5F) / 45.0F) * 45.0F;
					plantEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), f, 0.0F);
					world.spawnEntity(plantEntity);
					plantEntity.rideLilyPad(this);
					world.playSound((PlayerEntity) null, plantEntity.getX(), plantEntity.getY(), plantEntity.getZ(), sound, SoundCategory.BLOCKS, volume, 0.8F);
				}
				if (!player.getAbilities().creativeMode) {
					itemStack.decrement(1);
					player.getItemCooldownManager().set(ModItems.ICESHROOM_SEED_PACKET, IceshroomSeeds.cooldown);
				}
				return ActionResult.SUCCESS;
			}

			/**DOOMSHROOM**/
			if (itemStack.isOf(ModItems.DOOMSHROOM_SEED_PACKET) && !itemCooldown) {
				if (world instanceof ServerWorld) {
					ServerWorld serverWorld = (ServerWorld) world;
					DoomshroomEntity plantEntity = (DoomshroomEntity) PvZEntity.DOOMSHROOM.create(serverWorld, itemStack.getNbt(), (Text) null, player, this.getBlockPos(), SpawnReason.SPAWN_EGG, true, true);
					if (plantEntity == null) {
						return ActionResult.FAIL;
					}

					float f = (float) MathHelper.floor((MathHelper.wrapDegrees(player.getYaw() - 180.0F) + 22.5F) / 45.0F) * 45.0F;
					plantEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), f, 0.0F);
					world.spawnEntity(plantEntity);
					plantEntity.rideLilyPad(this);
					world.playSound((PlayerEntity) null, plantEntity.getX(), plantEntity.getY(), plantEntity.getZ(), sound, SoundCategory.BLOCKS, volume, 0.8F);
				}
				if (!player.getAbilities().creativeMode) {
					itemStack.decrement(1);
					player.getItemCooldownManager().set(ModItems.DOOMSHROOM_SEED_PACKET, DoomshroomSeeds.cooldown);
				}
				return ActionResult.SUCCESS;
			}

			/**SQUASH**/
			if (itemStack.isOf(ModItems.SQUASH_SEED_PACKET) && !itemCooldown) {
				if (world instanceof ServerWorld) {
					ServerWorld serverWorld = (ServerWorld) world;
					SquashEntity plantEntity = (SquashEntity) PvZEntity.SQUASH.create(serverWorld, itemStack.getNbt(), (Text) null, player, this.getBlockPos(), SpawnReason.SPAWN_EGG, true, true);
					if (plantEntity == null) {
						return ActionResult.FAIL;
					}

					float f = (float) MathHelper.floor((MathHelper.wrapDegrees(player.getYaw() - 180.0F) + 22.5F) / 45.0F) * 45.0F;
					plantEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), f, 0.0F);
					plantEntity.originalVec3d = plantEntity.getPos();
					world.spawnEntity(plantEntity);
					plantEntity.rideLilyPad(this);
					world.playSound((PlayerEntity) null, plantEntity.getX(), plantEntity.getY(), plantEntity.getZ(), sound, SoundCategory.BLOCKS, volume, 0.8F);
				}
				if (!player.getAbilities().creativeMode) {
					itemStack.decrement(1);
					player.getItemCooldownManager().set(ModItems.SQUASH_SEED_PACKET, SquashSeeds.cooldown);
				}
				return ActionResult.SUCCESS;
			}

			/**THREEPEATER**/
			if (itemStack.isOf(ModItems.THREEPEATER_SEED_PACKET) && !itemCooldown) {
				if (world instanceof ServerWorld) {
					ServerWorld serverWorld = (ServerWorld) world;
					ThreepeaterEntity plantEntity = (ThreepeaterEntity) PvZEntity.THREEPEATER.create(serverWorld, itemStack.getNbt(), (Text) null, player, this.getBlockPos(), SpawnReason.SPAWN_EGG, true, true);
					if (plantEntity == null) {
						return ActionResult.FAIL;
					}

					float f = (float) MathHelper.floor((MathHelper.wrapDegrees(player.getYaw() - 180.0F) + 22.5F) / 45.0F) * 45.0F;
					plantEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), f, 0.0F);
					world.spawnEntity(plantEntity);
					plantEntity.rideLilyPad(this);
					world.playSound((PlayerEntity) null, plantEntity.getX(), plantEntity.getY(), plantEntity.getZ(), sound, SoundCategory.BLOCKS, volume, 0.8F);
				}
				if (!player.getAbilities().creativeMode) {
					itemStack.decrement(1);
					player.getItemCooldownManager().set(ModItems.THREEPEATER_SEED_PACKET, ThreepeaterSeeds.cooldown);
				}
				return ActionResult.SUCCESS;
			}

			/**JALAPENO**/
			if (itemStack.isOf(ModItems.JALAPENO_SEED_PACKET) && !itemCooldown) {
				if (world instanceof ServerWorld) {
					ServerWorld serverWorld = (ServerWorld) world;
					JalapenoEntity plantEntity = (JalapenoEntity) PvZEntity.JALAPENO.create(serverWorld, itemStack.getNbt(), (Text) null, player, this.getBlockPos(), SpawnReason.SPAWN_EGG, true, true);
					if (plantEntity == null) {
						return ActionResult.FAIL;
					}

					float f = (float) MathHelper.floor((MathHelper.wrapDegrees(player.getYaw() - 180.0F) + 22.5F) / 45.0F) * 45.0F;
					plantEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), f, 0.0F);
					world.spawnEntity(plantEntity);
					plantEntity.rideLilyPad(this);
					world.playSound((PlayerEntity) null, plantEntity.getX(), plantEntity.getY(), plantEntity.getZ(), sound, SoundCategory.BLOCKS, volume, 0.8F);
				}
				if (!player.getAbilities().creativeMode) {
					itemStack.decrement(1);
					player.getItemCooldownManager().set(ModItems.JALAPENO_SEED_PACKET, JalapenoSeeds.cooldown);
				}
				return ActionResult.SUCCESS;
			}

			/**CABBAGE-PULT**/
			if (itemStack.isOf(ModItems.CABBAGEPULT_SEED_PACKET) && !itemCooldown) {
				if (world instanceof ServerWorld) {
					ServerWorld serverWorld = (ServerWorld) world;
					CabbagepultEntity plantEntity = (CabbagepultEntity) PvZEntity.CABBAGEPULT.create(serverWorld, itemStack.getNbt(), (Text) null, player, this.getBlockPos(), SpawnReason.SPAWN_EGG, true, true);
					if (plantEntity == null) {
						return ActionResult.FAIL;
					}

					float f = (float) MathHelper.floor((MathHelper.wrapDegrees(player.getYaw() - 180.0F) + 22.5F) / 45.0F) * 45.0F;
					plantEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), f, 0.0F);
					world.spawnEntity(plantEntity);
					plantEntity.rideLilyPad(this);
					world.playSound((PlayerEntity) null, plantEntity.getX(), plantEntity.getY(), plantEntity.getZ(), sound, SoundCategory.BLOCKS, volume, 0.8F);
				}
				if (!player.getAbilities().creativeMode) {
					itemStack.decrement(1);
					player.getItemCooldownManager().set(ModItems.CABBAGEPULT_SEED_PACKET, CabbagepultSeeds.cooldown);
				}
				return ActionResult.SUCCESS;
			}

			/**SNOW QUEEN PEA**/
			if (itemStack.isOf(ModItems.SNOW_QUEENPEA_SEED_PACKET) && !itemCooldown) {
				if (world instanceof ServerWorld) {
					ServerWorld serverWorld = (ServerWorld) world;
					SnowqueenpeaEntity plantEntity = (SnowqueenpeaEntity) PvZEntity.SNOWQUEENPEA.create(serverWorld, itemStack.getNbt(), (Text) null, player, this.getBlockPos(), SpawnReason.SPAWN_EGG, true, true);
					if (plantEntity == null) {
						return ActionResult.FAIL;
					}

					float f = (float) MathHelper.floor((MathHelper.wrapDegrees(player.getYaw() - 180.0F) + 22.5F) / 45.0F) * 45.0F;
					plantEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), f, 0.0F);
					world.spawnEntity(plantEntity);
					plantEntity.rideLilyPad(this);
					world.playSound((PlayerEntity) null, plantEntity.getX(), plantEntity.getY(), plantEntity.getZ(), sound, SoundCategory.BLOCKS, volume, 0.8F);
				}
				if (!player.getAbilities().creativeMode) {
					itemStack.decrement(1);
					player.getItemCooldownManager().set(ModItems.SNOW_QUEENPEA_SEED_PACKET, SnowqueenpeaSeeds.cooldown);
				}
				return ActionResult.SUCCESS;
			}

			/**PERFOOM-SHROOM**/
			if (itemStack.isOf(ModItems.PERFOOMSHROOM_SEED_PACKET) && !itemCooldown) {
				if (world instanceof ServerWorld) {
					ServerWorld serverWorld = (ServerWorld) world;
					PerfoomshroomEntity plantEntity = (PerfoomshroomEntity) PvZEntity.PERFOOMSHROOM.create(serverWorld, itemStack.getNbt(), (Text) null, player, this.getBlockPos(), SpawnReason.SPAWN_EGG, true, true);
					if (plantEntity == null) {
						return ActionResult.FAIL;
					}

					float f = (float) MathHelper.floor((MathHelper.wrapDegrees(player.getYaw() - 180.0F) + 22.5F) / 45.0F) * 45.0F;
					plantEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), f, 0.0F);
					world.spawnEntity(plantEntity);
					plantEntity.rideLilyPad(this);
					world.playSound((PlayerEntity) null, plantEntity.getX(), plantEntity.getY(), plantEntity.getZ(), sound, SoundCategory.BLOCKS, volume, 0.8F);
				}
				if (!player.getAbilities().creativeMode) {
					itemStack.decrement(1);
					player.getItemCooldownManager().set(ModItems.PERFOOMSHROOM_SEED_PACKET, PerfoomshroomSeeds.cooldown);
				}
				return ActionResult.SUCCESS;
			}

			/**ICEBERG LETTUCE**/
			if (itemStack.isOf(ModItems.ICEBERGLETTUCE_SEED_PACKET) && !itemCooldown) {
				if (world instanceof ServerWorld) {
					ServerWorld serverWorld = (ServerWorld) world;
					IcebergLettuceEntity plantEntity = (IcebergLettuceEntity) PvZEntity.ICEBERGLETTUCE.create(serverWorld, itemStack.getNbt(), (Text) null, player, this.getBlockPos(), SpawnReason.SPAWN_EGG, true, true);
					if (plantEntity == null) {
						return ActionResult.FAIL;
					}

					float f = (float) MathHelper.floor((MathHelper.wrapDegrees(player.getYaw() - 180.0F) + 22.5F) / 45.0F) * 45.0F;
					plantEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), f, 0.0F);
					world.spawnEntity(plantEntity);
					plantEntity.rideLilyPad(this);
					plantEntity.setPuffshroomPermanency(IcebergLettuceEntity.PuffPermanency.PERMANENT);
					world.playSound((PlayerEntity) null, plantEntity.getX(), plantEntity.getY(), plantEntity.getZ(), sound, SoundCategory.BLOCKS, volume, 0.8F);
				}
				if (!player.getAbilities().creativeMode) {
					itemStack.decrement(1);
					player.getItemCooldownManager().set(ModItems.ICEBERGLETTUCE_SEED_PACKET, IcebergLettuceSeeds.cooldown);
				}
				return ActionResult.SUCCESS;
			}

			/**PEAPOD**/
			if (itemStack.isOf(ModItems.PEAPOD_SEED_PACKET) && !itemCooldown) {
				if (world instanceof ServerWorld) {
					ServerWorld serverWorld = (ServerWorld) world;
					PeapodEntity plantEntity = (PeapodEntity) PvZEntity.PEAPOD.create(serverWorld, itemStack.getNbt(), (Text) null, player, this.getBlockPos(), SpawnReason.SPAWN_EGG, true, true);
					if (plantEntity == null) {
						return ActionResult.FAIL;
					}

					float f = (float) MathHelper.floor((MathHelper.wrapDegrees(player.getYaw() - 180.0F) + 22.5F) / 45.0F) * 45.0F;
					plantEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), f, 0.0F);
					world.spawnEntity(plantEntity);
					plantEntity.rideLilyPad(this);
					world.playSound((PlayerEntity) null, plantEntity.getX(), plantEntity.getY(), plantEntity.getZ(), sound, SoundCategory.BLOCKS, volume, 0.8F);
				}
				if (!player.getAbilities().creativeMode) {
					itemStack.decrement(1);
					player.getItemCooldownManager().set(ModItems.PEAPOD_SEED_PACKET, PeaPodSeeds.cooldown);
				}
				return ActionResult.SUCCESS;
			}

			/**FIRE PEA**/
			if (itemStack.isOf(ModItems.FIRE_PEA_SEED_PACKET) && !itemCooldown) {
				if (world instanceof ServerWorld) {
					ServerWorld serverWorld = (ServerWorld) world;
					FlamingpeaEntity plantEntity = (FlamingpeaEntity) PvZEntity.FLAMINGPEA.create(serverWorld, itemStack.getNbt(), (Text) null, player, this.getBlockPos(), SpawnReason.SPAWN_EGG, true, true);
					if (plantEntity == null) {
						return ActionResult.FAIL;
					}

					float f = (float) MathHelper.floor((MathHelper.wrapDegrees(player.getYaw() - 180.0F) + 22.5F) / 45.0F) * 45.0F;
					plantEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), f, 0.0F);
					world.spawnEntity(plantEntity);
					plantEntity.rideLilyPad(this);
					world.playSound((PlayerEntity) null, plantEntity.getX(), plantEntity.getY(), plantEntity.getZ(), sound, SoundCategory.BLOCKS, volume, 0.8F);
				}
				if (!player.getAbilities().creativeMode) {
					itemStack.decrement(1);
					player.getItemCooldownManager().set(ModItems.FIRE_PEA_SEED_PACKET, FirepeaSeeds.cooldown);
				}
				return ActionResult.SUCCESS;
			}

			/**SMALL-NUT**/
			if (itemStack.isOf(ModItems.SMALLNUT_SEED_PACKET) && !itemCooldown) {
				if (world instanceof ServerWorld) {
					ServerWorld serverWorld = (ServerWorld) world;
					SmallNutEntity plantEntity = (SmallNutEntity) PvZEntity.SMALLNUT.create(serverWorld, itemStack.getNbt(), (Text) null, player, this.getBlockPos(), SpawnReason.SPAWN_EGG, true, true);
					if (plantEntity == null) {
						return ActionResult.FAIL;
					}

					float f = (float) MathHelper.floor((MathHelper.wrapDegrees(player.getYaw() - 180.0F) + 22.5F) / 45.0F) * 45.0F;
					plantEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), f, 0.0F);
					world.spawnEntity(plantEntity);
					plantEntity.rideLilyPad(this);
					plantEntity.setPuffshroomPermanency(SmallNutEntity.PuffPermanency.PERMANENT);
					world.playSound((PlayerEntity) null, plantEntity.getX(), plantEntity.getY(), plantEntity.getZ(), sound, SoundCategory.BLOCKS, volume, 0.8F);
				}
				if (!player.getAbilities().creativeMode) {
					itemStack.decrement(1);
					player.getItemCooldownManager().set(ModItems.SMALLNUT_SEED_PACKET, SmallnutSeeds.cooldown);
				}
				return ActionResult.SUCCESS;
			}

			/**BUTTON-SHROOM**/
			if (itemStack.isOf(ModItems.BUTTONSHROOM_SEED_PACKET) && !itemCooldown) {
				if (world instanceof ServerWorld) {
					ServerWorld serverWorld = (ServerWorld) world;
					ButtonshroomEntity plantEntity = (ButtonshroomEntity) PvZEntity.BUTTONSHROOM.create(serverWorld, itemStack.getNbt(), (Text) null, player, this.getBlockPos(), SpawnReason.SPAWN_EGG, true, true);
					if (plantEntity == null) {
						return ActionResult.FAIL;
					}

					float f = (float) MathHelper.floor((MathHelper.wrapDegrees(player.getYaw() - 180.0F) + 22.5F) / 45.0F) * 45.0F;
					plantEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), f, 0.0F);
					world.spawnEntity(plantEntity);
					plantEntity.rideLilyPad(this);
					plantEntity.setPuffshroomPermanency(ButtonshroomEntity.PuffPermanency.PERMANENT);
					world.playSound((PlayerEntity) null, plantEntity.getX(), plantEntity.getY(), plantEntity.getZ(), sound, SoundCategory.BLOCKS, volume, 0.8F);
				}
				if (!player.getAbilities().creativeMode) {
					itemStack.decrement(1);
					player.getItemCooldownManager().set(ModItems.BUTTONSHROOM_SEED_PACKET, ButtonshroomSeeds.cooldown);
				}
				return ActionResult.SUCCESS;
			}

			/**BOMB SEEDLING**/
			if (itemStack.isOf(ModItems.BOMBSEEDLING_SEED_PACKET) && !itemCooldown) {
				if (world instanceof ServerWorld) {
					ServerWorld serverWorld = (ServerWorld) world;
					BombSeedlingEntity plantEntity = (BombSeedlingEntity) PvZEntity.BOMBSEEDLING.create(serverWorld, itemStack.getNbt(), (Text) null, player, this.getBlockPos(), SpawnReason.SPAWN_EGG, true, true);
					if (plantEntity == null) {
						return ActionResult.FAIL;
					}

					float f = (float) MathHelper.floor((MathHelper.wrapDegrees(player.getYaw() - 180.0F) + 22.5F) / 45.0F) * 45.0F;
					plantEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), f, 0.0F);
					world.spawnEntity(plantEntity);
					plantEntity.rideLilyPad(this);
					plantEntity.setPuffshroomPermanency(BombSeedlingEntity.PuffPermanency.PERMANENT);
					world.playSound((PlayerEntity) null, plantEntity.getX(), plantEntity.getY(), plantEntity.getZ(), sound, SoundCategory.BLOCKS, volume, 0.8F);
				}
				if (!player.getAbilities().creativeMode) {
					itemStack.decrement(1);
					player.getItemCooldownManager().set(ModItems.BOMBSEEDLING_SEED_PACKET, BombSeedlingSeeds.cooldown);
				}
				return ActionResult.SUCCESS;
			}

			/**WEENIE BEANIE**/
			if (itemStack.isOf(ModItems.WEENIEBEANIE_SEED_PACKET) && !itemCooldown) {
				if (world instanceof ServerWorld) {
					ServerWorld serverWorld = (ServerWorld) world;
					WeenieBeanieEntity plantEntity = (WeenieBeanieEntity) PvZEntity.WEENIEBEANIE.create(serverWorld, itemStack.getNbt(), (Text) null, player, this.getBlockPos(), SpawnReason.SPAWN_EGG, true, true);
					if (plantEntity == null) {
						return ActionResult.FAIL;
					}

					float f = (float) MathHelper.floor((MathHelper.wrapDegrees(player.getYaw() - 180.0F) + 22.5F) / 45.0F) * 45.0F;
					plantEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), f, 0.0F);
					world.spawnEntity(plantEntity);
					plantEntity.rideLilyPad(this);
					plantEntity.setPuffshroomPermanency(WeenieBeanieEntity.PuffPermanency.PERMANENT);
					world.playSound((PlayerEntity) null, plantEntity.getX(), plantEntity.getY(), plantEntity.getZ(), sound, SoundCategory.BLOCKS, volume, 0.8F);
				}
				if (!player.getAbilities().creativeMode) {
					itemStack.decrement(1);
					player.getItemCooldownManager().set(ModItems.WEENIEBEANIE_SEED_PACKET, WeenieBeanieSeeds.cooldown);
				}
				return ActionResult.SUCCESS;
			}

			/**SUNFLOWER SEED**/
			if (itemStack.isOf(ModItems.SUNFLOWERSEED_SEED_PACKET) && !itemCooldown) {
				if (world instanceof ServerWorld) {
					ServerWorld serverWorld = (ServerWorld) world;
					SunflowerSeedEntity plantEntity = (SunflowerSeedEntity) PvZEntity.SUNFLOWERSEED.create(serverWorld, itemStack.getNbt(), (Text) null, player, this.getBlockPos(), SpawnReason.SPAWN_EGG, true, true);
					if (plantEntity == null) {
						return ActionResult.FAIL;
					}

					float f = (float) MathHelper.floor((MathHelper.wrapDegrees(player.getYaw() - 180.0F) + 22.5F) / 45.0F) * 45.0F;
					plantEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), f, 0.0F);
					world.spawnEntity(plantEntity);
					plantEntity.rideLilyPad(this);
					plantEntity.setPuffshroomPermanency(SunflowerSeedEntity.PuffPermanency.PERMANENT);
					world.playSound((PlayerEntity) null, plantEntity.getX(), plantEntity.getY(), plantEntity.getZ(), sound, SoundCategory.BLOCKS, volume, 0.8F);
				}
				if (!player.getAbilities().creativeMode) {
					itemStack.decrement(1);
					player.getItemCooldownManager().set(ModItems.SUNFLOWERSEED_SEED_PACKET, SunflowerSeedSeeds.cooldown);
				}
				return ActionResult.SUCCESS;
			}

			/**BELLFLOWER**/
			if (itemStack.isOf(ModItems.BELLFLOWER_SEED_PACKET) && !itemCooldown) {
				if (world instanceof ServerWorld) {
					ServerWorld serverWorld = (ServerWorld) world;
					BellflowerEntity plantEntity = (BellflowerEntity) PvZEntity.BELLFLOWER.create(serverWorld, itemStack.getNbt(), (Text) null, player, this.getBlockPos(), SpawnReason.SPAWN_EGG, true, true);
					if (plantEntity == null) {
						return ActionResult.FAIL;
					}

					float f = (float) MathHelper.floor((MathHelper.wrapDegrees(player.getYaw() - 180.0F) + 22.5F) / 45.0F) * 45.0F;
					plantEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), f, 0.0F);
					world.spawnEntity(plantEntity);
					plantEntity.rideLilyPad(this);
					world.playSound((PlayerEntity) null, plantEntity.getX(), plantEntity.getY(), plantEntity.getZ(), sound, SoundCategory.BLOCKS, volume, 0.8F);
				}
				if (!player.getAbilities().creativeMode) {
					itemStack.decrement(1);
					player.getItemCooldownManager().set(ModItems.BELLFLOWER_SEED_PACKET, BellflowerSeeds.cooldown);
				}
				return ActionResult.SUCCESS;
			}else {
				return ActionResult.CONSUME;
			}
		}
		else {
			return ActionResult.FAIL;
		}
	}
}
