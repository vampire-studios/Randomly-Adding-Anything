package io.github.vampirestudios.raa.history;

import io.github.vampirestudios.raa.utils.Rands;
import io.github.vampirestudios.raa.utils.Utils;
import net.minecraft.util.Identifier;

public class Civilization {
    private String name; //TODO: this just uses the dimension name generator now, change it so it uses a custom one
    private Identifier homeDimensionId; //The id of the dimension that this civilization originated in
    private ProtoDimension homeDimension; //The dimension that this civilization originated in
    private double influenceRadius; //The amount of influence that this civ has on the multiverse
    //Tech level: how advanced was the civ?
    //0: Pre stone age. The civ existed for a bit, then it died.
    //1: Stone age. The civ has achieved basic tools made structures to survive the night.
    //2: Iron age. The civ has made buildings and structures. This is where outposts and campfires come into play.
    //3: Medieval age. The civ has started to build complex structures, like castles and towers. This is also where the civ starts changing it's environment.
    //4: Post medieval age. TODO
    private int techLevel;
    private String causeOfDeath;

    public Civilization(String name, ProtoDimension homeDimension) {
        this.name = name;
        this.homeDimension = homeDimension;
        this.homeDimensionId = homeDimension.getName().getRight();
    }

    public void simulate() {
        //here we do a very basic simulation of the civilization and it's progress through time.
        //Right now, it just checks what dimension the civilization has been plopped into and does some Math(tm) on it.
        //Currently, it doesn't do anything too complicated, but in the future we will have a whole zero person strategy game like that of Dwarf Fortress.

        //If a dimension is already dead, we roll the dice 2-4 times to see if the civ can survive.
        if (Utils.checkBitFlag(homeDimension.getFlags(), Utils.DEAD) || Utils.checkBitFlag(homeDimension.getFlags(), Utils.MOLTEN) || Utils.checkBitFlag(homeDimension.getFlags(), Utils.DRY)) {
            boolean survived = false;
            for (int i = 0; i < Rands.randIntRange(3, 5); i++) {
                survived = Rands.chance(6);
                if (survived) break;
            }
            if (!survived) {
                influenceRadius = 0;
                techLevel = 0;
                return;
            } else {
                //Their influence has grown by a tiny bit because... they're not dead
                influenceRadius += Rands.randFloatRange(0.05F, 0.1F);
            }
        }

        // We do the same thing here, but it's stacked differently because we can apply a sort of gradient to how bad the temperature is.
        if (homeDimension.getTemperature() < 0.5 || homeDimension.getTemperature() > 1.5) {
            boolean survived = false;
            int rolls = 2;
            if (homeDimension.getTemperature() < 0.5) rolls += Math.round(homeDimension.getTemperature() * 8);
            if (homeDimension.getTemperature() > 1.5)
                rolls += Math.round(Math.abs(2 - homeDimension.getTemperature()) * 8);
            for (int i = 0; i < rolls; i++) {
                survived = Rands.chance(6);
                if (survived) break;
            }
            if (!survived) {
                influenceRadius = 0;
                techLevel = 0;
                return;
            } else {
                //Their influence has grown by a tiny bit because... they're not dead
                influenceRadius += Rands.randFloatRange((rolls / 80.F), (rolls / 80.F) * 2F);
            }
        }

        //If a civ gets to this level, it automatically gets to go to the stone age!
        techLevel++;

        //civs should have a minimum influence, which we calculate here.
        influenceRadius += Rands.randFloatRange(0.025F, 0.05F);

        //This is not the best way to convey influence, but it's a start.
        float temperatureDeviation = Math.abs(1 - homeDimension.getTemperature());
        influenceRadius += temperatureDeviation / 4;

        //Lush dimension civs get a bonus with their influence, and also get a leg up on their tech level.
        if (Utils.checkBitFlag(homeDimension.getFlags(), Utils.LUSH)) {
            influenceRadius += Rands.randFloatRange(0.1F, 0.15F);
            techLevel++;
        }

        //Dimensions with flat land provide a bonus to the civ because they can build more efficiently.
        if (homeDimension.getScale() < 0.75) {
            influenceRadius += homeDimension.getScale() / 4;
            techLevel++;
        }

        //We roll a die twice to see if the civ gets some final additions to their tech level.
        if (Rands.chance(6)) {
            influenceRadius += Rands.randFloatRange(0.025F, 0.05F);
            techLevel++;
        }
        if (Rands.chance(6)) {
            influenceRadius += Rands.randFloatRange(0.025F, 0.05F);
            techLevel++;
        }

        //The simulation is done! Now we just need to alter the world a bit, based on what happened to the civilization.

        //Tech level 2 and up have built buildings, so their world is abandoned.
        if (techLevel >= 2) {
            homeDimension.setAbandoned();
        }

        //Tech level 3 and up civilizations are considered civilized.
        if (techLevel >= 3) {
            homeDimension.setCivilized();
        }

        //Higher tier dimensions have a higher chance to kill off their world.
        if (techLevel == 3) {
            if (Rands.chance(6)) homeDimension.setDead();
        }

        if (techLevel >= 4) {
            if (Rands.chance(4)) homeDimension.setDead();
        }
    }

    //here we just make sure that low tier civs can't influence too much
    private void finalizeInfluence() {
        if (techLevel == 1) {
            if (influenceRadius > 0.15) influenceRadius = 0.15;
        }
        if (techLevel == 2) {
            if (influenceRadius > 0.35) influenceRadius = 0.35;
        }
        if (techLevel == 3) {
            if (influenceRadius > 0.55) influenceRadius = 0.55;
        }
    }

    public String getName() {
        return name;
    }

    public double getInfluenceRadius() {
        return influenceRadius;
    }

    public int getTechLevel() {
        return techLevel;
    }

    public String getCauseOfDeath() {
        return causeOfDeath;
    }

    public Identifier getHomeDimensionId() {
        return homeDimensionId;
    }

    public ProtoDimension getHomeDimension() {
        return homeDimension;
    }
}
