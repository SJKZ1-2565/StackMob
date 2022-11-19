package sjkz1.com.stack_mob.mixin;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ItemSteerable;
import net.minecraft.world.entity.Saddleable;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import sjkz1.com.stack_mob.StackMob;

@Mixin(Pig.class)
public abstract class PigMixin extends Animal
        implements ItemSteerable,
        Saddleable {
    protected PigMixin(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
    }

    @Unique
    private long realCount = 1;
    @Unique
    private int coolDown;

    @Override
    public void aiStep() {
        super.aiStep();
        if (!this.hasCustomName() && this.isAlive()) {
            this.setCustomNameVisible(true);
            this.setCustomName(StackMob.stackedEntityName(this.getRealCount(), this.getTypeName().getString()));
        }
        if (this.getCoolDown() > 0) {
            this.setCoolDown(this.getCoolDown() - 1);
        }
        if (this.getCoolDown() == 0) {
            this.setCoolDown(300);
        }
        if (this.getRealCount() <= 0) {
            this.setRealCount(1);
        }
        if (this.isInLove() && this.getRealCount() > 1) {
            PigMixin pig = (PigMixin) this.getType().create(this.level);
            this.setRealCount(this.getRealCount() - 1);
            pig.moveTo(this.getX(), this.getY(), this.getZ());
            this.setCoolDown(6000);
            pig.setCoolDown(6000);
            this.setCustomName(StackMob.stackedEntityName(this.getRealCount(), this.getTypeName().getString()));
            pig.setCustomName(StackMob.stackedEntityName(pig.getRealCount(), pig.getTypeName().getString()));
            this.level.addFreshEntity(pig);
        }
        var otherPig = this.level.getEntitiesOfClass(PigMixin.class, this.getBoundingBox().inflate(16.0d), pigMixin -> !pigMixin.isBaby());
        for (var pigs : otherPig) {
            if (pigs != this && (!this.isRemoved() || !pigs.isRemoved()) && (this.getRealCount() >= pigs.getRealCount()) && !this.isBaby()) {
                if (this.getCoolDown() == 1) {
                    pigs.discard();
                    this.setRealCount(this.getRealCount() + pigs.getRealCount());
                    this.setCustomName(StackMob.stackedEntityName(this.getRealCount(), this.getTypeName().getString()));
                }
            }
        }
    }

    @Override
    public void remove(@NotNull RemovalReason removalReason) {
        super.remove(removalReason);
        long i = this.getRealCount();
        if (!this.level.isClientSide && i > 1 && this.isDeadOrDying()) {
            {
                PigMixin pig = (PigMixin) this.getType().create(this.level);
                pig.setRealCount(i - 1);
                pig.moveTo(this.getX(), this.getY(), this.getZ());
                this.level.addFreshEntity(pig);
            }
        }
    }

    @Inject(method = "addAdditionalSaveData", at = @At(value = "TAIL"))
    public void addAdditionalSaveData(CompoundTag compoundTag, CallbackInfo ci) {
        compoundTag.putLong("RealCount", this.realCount);
        compoundTag.putLong("RealCount", this.realCount);
    }

    @Inject(method = "readAdditionalSaveData", at = @At(value = "TAIL"))
    public void readAdditionalSaveData(CompoundTag compoundTag, CallbackInfo ci) {
        this.realCount = compoundTag.getLong("RealCount");
        this.coolDown = compoundTag.getInt("CoolDown");

    }

    public void setRealCount(long realCount) {
        this.realCount = realCount;
    }

    public long getRealCount() {
        return realCount;
    }

    public int getCoolDown() {
        return coolDown;
    }

    public void setCoolDown(int coolDown) {
        this.coolDown = coolDown;
    }

}
