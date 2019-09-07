package fr.arthurbambou.randomlyaddinganything.mixins.client;

import fr.arthurbambou.randomlyaddinganything.items.RAAArmorItem;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.item.ArmorItem;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Map;

@Mixin(ArmorFeatureRenderer.class)
public abstract class ArmorFeatureRendererMixin {

    @Shadow @Final private static Map<String, Identifier> ARMOR_TEXTURE_CACHE;

    /**
     * @author Vampire Studios
     */
    @Overwrite
    private Identifier method_4174(ArmorItem armorItem_1, boolean boolean_1, String string_1) {
        Identifier identifier;
        if (armorItem_1 instanceof RAAArmorItem) {
            identifier = new Identifier("raa", "textures/models/armor/" +
                    armorItem_1.getMaterial().getName() + "_layer_" + (boolean_1 ? 2 : 1) + (string_1 == null ? "" : "_" + string_1) + ".png");
        } else {
            identifier = new Identifier("textures/models/armor/" +
                    armorItem_1.getMaterial().getName() + "_layer_" + (boolean_1 ? 2 : 1) + (string_1 == null ? "" : "_" + string_1) + ".png");
        }
        return ARMOR_TEXTURE_CACHE.computeIfAbsent(identifier.toString(), Identifier::new);
    }

}