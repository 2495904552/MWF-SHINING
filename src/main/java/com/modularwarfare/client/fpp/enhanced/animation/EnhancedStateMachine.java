package com.modularwarfare.client.fpp.enhanced.animation;

import com.modularwarfare.api.WeaponAnimations;
import com.modularwarfare.client.fpp.basic.animations.ReloadType;
import com.modularwarfare.client.fpp.basic.animations.StateEntry;
import com.modularwarfare.client.fpp.basic.models.ModelGun;
import com.modularwarfare.client.fpp.enhanced.AnimationType;
import com.modularwarfare.client.fpp.enhanced.models.ModelEnhancedGun;
import com.modularwarfare.common.guns.GunType;

public class EnhancedStateMachine {

    /**
     * RELOAD
     */
    public float reloadTime;
    private ReloadType reloadType;
    public boolean reloading = false;

    /**
     * Recoil
     */
    public float gunRecoil = 0F, lastGunRecoil = 0F;
    /**
     * Slide
     */
    public float gunSlide = 0F, lastGunSlide = 0F;

    /**
     * Shoot State Machine
     */
    public boolean shooting = false;
    private float shootTime;


    public ModelEnhancedGun currentModel;

    public void triggerShoot(ModelGun model, GunType gunType, int fireTickDelay) {

        lastGunRecoil = gunRecoil = 1F;
        lastGunSlide = gunSlide = 1F;

        shooting = true;
        shootTime = fireTickDelay;
    }

    public void triggerReload(int reloadTime, int reloadCount, ModelEnhancedGun model, ReloadType reloadType) {
        this.reloadTime = reloadType != ReloadType.Full ? reloadTime * 0.65f : reloadTime;
        this.reloadType = reloadType;
        this.reloading = true;
        this.currentModel = model;
    }

    public void onRenderTickUpdate(float partialTick) {
        if (reloading){
            /** RELOAD **/
            float reloadSpeed = currentModel.config.animations.get(AnimationType.RELOAD).speed * partialTick;
            float val = AnimationController.RELOAD + reloadSpeed;
            AnimationController.RELOAD = Math.max(0, Math.min(1, val));
            if(AnimationController.RELOAD == 1F){
                reloading = false;
                AnimationController.RELOAD = 0F;
            }
        }
    }

}
