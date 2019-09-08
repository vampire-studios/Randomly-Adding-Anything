package fr.arthurbambou.randomlyaddinganything.items;

import fr.arthurbambou.randomlyaddinganything.RandomlyAddingAnything;
import fr.arthurbambou.randomlyaddinganything.api.enums.OreTypes;
import fr.arthurbambou.randomlyaddinganything.helpers.Rands;
import fr.arthurbambou.randomlyaddinganything.materials.Material;
import net.minecraft.item.DyeableHorseArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class RAAHorseArmorItem extends DyeableHorseArmorItem {

    private final Identifier entityTexture;
    private Material material;

    public RAAHorseArmorItem(Material material) {
        super(5, material.getName().toLowerCase(), (new Item.Settings()).maxCount(1).group(RandomlyAddingAnything.ITEM_GROUP));
        this.material = material;
        this.entityTexture = Rands.list(OreTypes.HORSE_ARMOR_MODEL_TEXTURES);
    }

    @Override
    public int getColor(ItemStack itemStack_1) {
        return material.getRGBColor();
    }

    @Override
    public Identifier getEntityTexture() {
        return entityTexture;
    }

}
