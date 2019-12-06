package io.github.vampirestudios.raa.compats.recipes;

import com.swordglowsblue.artifice.api.ArtificeResourcePack;
import com.swordglowsblue.artifice.api.util.Processor;
import io.github.vampirestudios.raa.RandomlyAddingAnything;
import io.github.vampirestudios.raa.api.enums.GeneratesIn;
import io.github.vampirestudios.raa.api.enums.OreType;
import io.github.vampirestudios.raa.compats.recipes.artifice.TRBlastFurnaceRecipeBuilder;
import io.github.vampirestudios.raa.compats.recipes.artifice.TRGrinderRecipeBuilder;
import io.github.vampirestudios.raa.generation.materials.Material;
import io.github.vampirestudios.raa.registries.Materials;
import io.github.vampirestudios.raa.utils.Utils;
import net.minecraft.util.Identifier;

public class TechRebornRecipes extends RecipeCompat {
    public TechRebornRecipes() {
        super();
    }

    @Override
    public void registerRecipes(ArtificeResourcePack.ServerResourcePackBuilder dataPackBuilder) {
        this.setDataPackBuilder(dataPackBuilder);
        for (Material material : Materials.MATERIALS) {
            if (material.hasArmor()) {
                addBlastingFurnaceRecipe(new Identifier(RandomlyAddingAnything.MOD_ID, "boots_to_" + material.getName()),
                        trBlastFurnaceRecipeBuilder -> trBlastFurnaceRecipeBuilder.heat(1000)
                                .power(128)
                                .time(140)
                                .multiIngredient(raaMultiIngredientBuilder -> raaMultiIngredientBuilder
                                        .item(new Identifier(RandomlyAddingAnything.MOD_ID, material.getName() + "_boots"))
                                        .item(new Identifier("sand")))
                                .multiResult(raaMultiResultBuilder -> {
                                    raaMultiResultBuilder
                                            .item(new Identifier("techreborn:dark_ashes_dust"));
                                    if (material.getOreInformation().getOreType() == OreType.METAL) raaMultiResultBuilder.item(new Identifier(RandomlyAddingAnything.MOD_ID, material.getName() + "_ingot"), 4);
                                    if (material.getOreInformation().getOreType() == OreType.GEM) raaMultiResultBuilder.item(new Identifier(RandomlyAddingAnything.MOD_ID, material.getName() + "_gem"), 4);
                                    if (material.getOreInformation().getOreType() == OreType.CRYSTAL) raaMultiResultBuilder.item(new Identifier(RandomlyAddingAnything.MOD_ID, material.getName() + "_crystal"), 4);

                                })
                );

                addBlastingFurnaceRecipe(new Identifier(RandomlyAddingAnything.MOD_ID, "chestplate_to_" + material.getName()),
                        trBlastFurnaceRecipeBuilder -> trBlastFurnaceRecipeBuilder.heat(1000)
                                .power(128)
                                .time(140)
                                .multiIngredient(raaMultiIngredientBuilder -> raaMultiIngredientBuilder
                                        .item(new Identifier(RandomlyAddingAnything.MOD_ID, material.getName() + "_chestplate"))
                                        .item(new Identifier("sand")))
                                .multiResult(raaMultiResultBuilder -> {
                                    raaMultiResultBuilder
                                            .item(new Identifier("techreborn:dark_ashes_dust"));
                                    if (material.getOreInformation().getOreType() == OreType.METAL) raaMultiResultBuilder.item(new Identifier(RandomlyAddingAnything.MOD_ID, material.getName() + "_ingot"), 8);
                                    if (material.getOreInformation().getOreType() == OreType.GEM) raaMultiResultBuilder.item(new Identifier(RandomlyAddingAnything.MOD_ID, material.getName() + "_gem"), 8);
                                    if (material.getOreInformation().getOreType() == OreType.CRYSTAL) raaMultiResultBuilder.item(new Identifier(RandomlyAddingAnything.MOD_ID, material.getName() + "_crystal"), 8);

                                })
                );

                addBlastingFurnaceRecipe(new Identifier(RandomlyAddingAnything.MOD_ID, "helmet_to_" + material.getName()),
                        trBlastFurnaceRecipeBuilder -> trBlastFurnaceRecipeBuilder.heat(1000)
                                .power(128)
                                .time(140)
                                .multiIngredient(raaMultiIngredientBuilder -> raaMultiIngredientBuilder
                                        .item(new Identifier(RandomlyAddingAnything.MOD_ID, material.getName() + "_helmet"))
                                        .item(new Identifier("sand")))
                                .multiResult(raaMultiResultBuilder -> {
                                    raaMultiResultBuilder
                                            .item(new Identifier("techreborn:dark_ashes_dust"));
                                    if (material.getOreInformation().getOreType() == OreType.METAL) raaMultiResultBuilder.item(new Identifier(RandomlyAddingAnything.MOD_ID, material.getName() + "_ingot"), 5);
                                    if (material.getOreInformation().getOreType() == OreType.GEM) raaMultiResultBuilder.item(new Identifier(RandomlyAddingAnything.MOD_ID, material.getName() + "_gem"), 5);
                                    if (material.getOreInformation().getOreType() == OreType.CRYSTAL) raaMultiResultBuilder.item(new Identifier(RandomlyAddingAnything.MOD_ID, material.getName() + "_crystal"), 5);

                                })
                );

                addBlastingFurnaceRecipe(new Identifier(RandomlyAddingAnything.MOD_ID, "leggings_to_" + material.getName()),
                        trBlastFurnaceRecipeBuilder -> trBlastFurnaceRecipeBuilder.heat(1000)
                                .power(128)
                                .time(140)
                                .multiIngredient(raaMultiIngredientBuilder -> raaMultiIngredientBuilder
                                        .item(new Identifier(RandomlyAddingAnything.MOD_ID, material.getName() + "_leggings"))
                                        .item(new Identifier("sand")))
                                .multiResult(raaMultiResultBuilder -> {
                                    raaMultiResultBuilder
                                            .item(new Identifier("techreborn:dark_ashes_dust"));
                                    if (material.getOreInformation().getOreType() == OreType.METAL) raaMultiResultBuilder.item(new Identifier(RandomlyAddingAnything.MOD_ID, material.getName() + "_ingot"), 7);
                                    if (material.getOreInformation().getOreType() == OreType.GEM) raaMultiResultBuilder.item(new Identifier(RandomlyAddingAnything.MOD_ID, material.getName() + "_gem"), 7);
                                    if (material.getOreInformation().getOreType() == OreType.CRYSTAL) raaMultiResultBuilder.item(new Identifier(RandomlyAddingAnything.MOD_ID, material.getName() + "_crystal"), 7);

                                })
                );

                if (material.getOreInformation().getOreType() == OreType.METAL && material.getOreInformation().getGeneratesIn() != GeneratesIn.DOES_NOT_APPEAR) {
                    addGrinderRecipe(Utils.appendToPath(material.getId(), "_to_dust"), trGrinderRecipeBuilder -> trGrinderRecipeBuilder
                            .multiIngredient(raaMultiIngredientBuilder -> raaMultiIngredientBuilder.item(Utils.appendToPath(material.getId(), "_ore")))
                            .multiResult(raaMultiResultBuilder -> raaMultiResultBuilder.item(Utils.appendToPath(material.getId(), "_dust"), 2))
                            .power(material.getMiningLevel() + 2).time(270));
                }
            }
        }
    }

    private void addBlastingFurnaceRecipe(Identifier id, Processor<TRBlastFurnaceRecipeBuilder> f) {
        addRecipes(id, (r) -> {
            TRBlastFurnaceRecipeBuilder var10000 = (TRBlastFurnaceRecipeBuilder)f.process(r.type(new Identifier("techreborn:blast_furnace")));
        }, TRBlastFurnaceRecipeBuilder::new);
    }

    private void addGrinderRecipe(Identifier id, Processor<TRGrinderRecipeBuilder> f) {
        addRecipes(id, (r) -> {
            TRGrinderRecipeBuilder var10000 = (TRGrinderRecipeBuilder)f.process(r.type(new Identifier("techreborn:grinder")));
        }, TRGrinderRecipeBuilder::new);
    }
}
