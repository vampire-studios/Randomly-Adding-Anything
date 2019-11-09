package io.github.vampirestudios.raa.config.readers.material;

import blue.endless.jankson.JsonObject;
import io.github.vampirestudios.raa.RandomlyAddingAnything;
import io.github.vampirestudios.raa.api.enums.GeneratesIn;
import io.github.vampirestudios.raa.api.enums.OreTypes;
import io.github.vampirestudios.raa.api.enums.TextureTypes;
import io.github.vampirestudios.raa.config.readers.Version;
import io.github.vampirestudios.raa.generation.materials.CustomArmorMaterial;
import io.github.vampirestudios.raa.generation.materials.CustomToolMaterial;
import io.github.vampirestudios.raa.generation.materials.MaterialBuilder;
import io.github.vampirestudios.raa.utils.Rands;
import net.minecraft.util.Identifier;

@SuppressWarnings("ConstantConditions")
public enum MaterialFields {
    ID(Version.V1, "id", (configVersion, builder, jsonObject) -> {
        if(jsonObject.containsKey("id"))
            return builder.id(Identifier.tryParse(jsonObject.get(String.class, "id")));
        else
            return builder.id(new Identifier(RandomlyAddingAnything.MOD_ID, RandomlyAddingAnything.CONFIG.namingLanguage.getMaterialNameGenerator().asId(jsonObject.get(String.class, "name"))));
    }),
    NAME(Version.OLD, "name", (configVersion, builder, jsonObject) -> {
        return builder.name(jsonObject.get(String.class, "name"));
    }),
    RGB(Version.OLD, "rgb", (configVersion, builder, jsonObject) -> {
        if (configVersion.getNumber() > 0) return builder;
        return builder.color(jsonObject.get(int.class, "rgb"));
    }),
    COLOR(Version.V1, "color", (configVersion, builder, jsonObject) -> {
        if (configVersion.getNumber() == 0) return builder;
        return builder.color(jsonObject.get(int.class, "color"));
    }),
    ARMOR(Version.OLD, "armor", (configVersion, builder, jsonObject) -> {
        return builder.armor(jsonObject.get(boolean.class, "armor"));
    }),
    TOOLS(Version.OLD, "tools", (configVersion, builder, jsonObject) -> {
        return builder.tools(jsonObject.get(boolean.class, "tools"));
    }),
    WEAPONS(Version.OLD, "weapons", (configVersion, builder, jsonObject) -> {
        return builder.weapons(jsonObject.get(boolean.class, "weapons"));
    }),
    GLOW(Version.OLD, "glowing", (configVersion, builder, jsonObject) -> {
        return builder.glowing(jsonObject.get(boolean.class, "glowing"));
    }),
    ORE_FLOWERS(Version.OLD, "oreFlower", (configVersion, builder, jsonObject) -> {
        return builder.oreFlower(jsonObject.get(boolean.class, "oreFlower"));
    }),
    MINING_LEVEL(Version.OLD, "miningLevel", (configVersion, builder, jsonObject) -> {
        if (!jsonObject.containsKey("miningLevel")) {
            builder.miningLevel(Rands.randInt(4));
        } else {
            builder.miningLevel(jsonObject.get(int.class, "miningLevel"));
        }
        return builder;
    }),
    NUGGET(Version.OLD, "nuggetTexture", (configVersion, builder, jsonObject) -> {
        if (configVersion.getNumber() > 0) {
            if (!jsonObject.containsKey("nuggetTexture")) {
                builder.nuggetTexture(null);
            } else builder.nuggetTexture(idFromJson(jsonObject, "nuggetTexture"));
        } else {
            if (jsonObject.get(String.class, "nuggetTexture").equals("null")) {
                builder.nuggetTexture(null);
            } else builder.nuggetTexture(new Identifier(jsonObject.get(String.class, "nuggetTexture")));
        }
        return builder;
    }),
    FOOD(Version.OLD, "food", (configVersion, builder, jsonObject) -> {
        if (!jsonObject.containsKey("food")) {
            builder.food(Rands.chance(4));
        } else {
            builder.food(jsonObject.get(boolean.class, "food"));
        }
        return builder;
    }),
    ARMOR_MATERIAL(Version.OLD, "armorMaterial", (configVersion, builder, jsonObject) -> {
        if (jsonObject.get(boolean.class, "armor")) {
            if (configVersion.getNumber() > 0) {
                String name = jsonObject.get(String.class, "name");
                JsonObject oreInfo = jsonObject.getObject("oreInformation");
                OreTypes oreTypes = oreInfo.get(OreTypes.class, "oreType");

                JsonObject armorObject = jsonObject.getObject("armorMaterial");
                int durabilityMultiplier = armorObject.get(int.class, "durabilityMultiplier");
                int[] protectionAmounts = armorObject.get(int[].class, "protectionAmounts");
                int enchantability = armorObject.get(int.class, "enchantability");
                float toughness = armorObject.get(float.class, "toughness");
                int horseArmorBonus = armorObject.get(int.class, "horseArmorBonus");
                CustomArmorMaterial armorMaterial = new CustomArmorMaterial(
                        name, oreTypes, durabilityMultiplier, protectionAmounts,
                        enchantability, toughness, horseArmorBonus
                );
                builder.armor(armorMaterial);
            } else {
                builder.armor(true);
            }
        }
        return builder;
    }),
    TOOL_MATERIAL(Version.OLD, "toolMaterial", (configVersion, builder, jsonObject) -> {
        if (configVersion.getNumber() > 0) {
            String name = jsonObject.get(String.class, "name");
            int miningLevel = jsonObject.get(int.class, "miningLevel");
            JsonObject oreInfo = jsonObject.getObject("oreInformation");
            OreTypes oreTypes = oreInfo.get(OreTypes.class, "oreType");

            if (jsonObject.get(boolean.class, "tools")) {
                JsonObject toolObject = jsonObject.getObject("toolMaterial");
                int durability = toolObject.get(int.class, "durability");
                int enchantability = toolObject.get(int.class, "enchantability");
                float miningSpeed = toolObject.get(float.class, "miningSpeed");
                float attackDamage = toolObject.get(float.class, "attackDamage");
                float hoeAttackSpeed = toolObject.get(float.class, "hoeAttackSpeed");
                float axeAttackDamage = toolObject.get(float.class, "axeAttackDamage");
                float axeAttackSpeed = toolObject.get(float.class, "axeAttackSpeed");
                float swordAttackDamage = toolObject.get(float.class, "swordAttackDamage");
                CustomToolMaterial toolMaterial = new CustomToolMaterial(
                        name, oreTypes, durability, miningSpeed, attackDamage,
                        miningLevel, enchantability, hoeAttackSpeed, axeAttackDamage,
                        axeAttackSpeed, swordAttackDamage
                );
//                System.out.println("tools");
                builder.tools(toolMaterial);
            }

            if (jsonObject.get(boolean.class, "weapons")) {
                JsonObject toolObject = jsonObject.getObject("toolMaterial");
                int durability = toolObject.get(int.class, "durability");
                int enchantability = toolObject.get(int.class, "enchantability");
                float miningSpeed = toolObject.get(float.class, "miningSpeed");
                float attackDamage = toolObject.get(float.class, "attackDamage");
                float hoeAttackSpeed = toolObject.get(float.class, "hoeAttackSpeed");
                float axeAttackDamage = toolObject.get(float.class, "axeAttackDamage");
                float axeAttackSpeed = toolObject.get(float.class, "axeAttackSpeed");
                float swordAttackDamage = toolObject.get(float.class, "swordAttackDamage");
                CustomToolMaterial toolMaterial = new CustomToolMaterial(
                        name, oreTypes, durability, miningSpeed, attackDamage,
                        miningLevel, enchantability, hoeAttackSpeed, axeAttackDamage,
                        axeAttackSpeed, swordAttackDamage
                );
//                System.out.println("weapons");
                builder.weapons(toolMaterial);
            }
        } else {
            if (jsonObject.get(boolean.class, "tools")) {
                builder.tools(true);
            }
            if (jsonObject.get(boolean.class, "weapons")) {
                builder.weapons(true);
            }
        }
        return builder;
    }),
    ORE_INFO_OLD(Version.OLD, "oreInformationJSON", (configVersion, builder, jsonObject) -> {
        if (configVersion.getNumber() > 0) return builder;
        if (jsonObject.containsKey("oreInformationJSON")) {
            JsonObject oreInfo = jsonObject.getObject("oreInformationJSON");
            builder.oreType(oreInfo.get(OreTypes.class, "oreTypes"))
                    .generatesIn(oreInfo.get(GeneratesIn.class, "generatesIn"))
                    .overlayTexture(new Identifier(oreInfo.get(String.class, "overlayTexture")))
                    .minXPAmount(0)
                    .maxXPAmount(Rands.randIntRange(0, 4))
                    .oreClusterSize(Rands.randIntRange(2, 6));
        }
        return builder;
    }),
    ORE_INFO(Version.V1, "oreInformation", (configVersion, builder, jsonObject) -> {
        if (configVersion.getNumber() > 0) {
            JsonObject oreInfo = jsonObject.getObject("oreInformation");
            OreTypes oreTypes = oreInfo.get(OreTypes.class, "oreType");
            builder.oreType(oreTypes)
                    .generatesIn(oreInfo.get(GeneratesIn.class, "generateIn"))
                    .overlayTexture(idFromJson(oreInfo,"overlayTexture"))
                    .minXPAmount(0)
                    .maxXPAmount(oreInfo.get(int.class, "maxXPAmount"))
                    .oreClusterSize(oreInfo.get(int.class, "oreClusterSize"));
        }
        return builder;
    }),
    RESOURCE_ITEM(Version.OLD, "resourceItemTexture", (configVersion, builder, jsonObject) -> {
        if (configVersion.getNumber() > 0) {
            builder.resourceItemTexture(idFromJson(jsonObject, "resourceItemTexture"));
        } else {
            Identifier resourceItem = new Identifier(jsonObject.get(String.class, "resourceItemTexture"));
            if (TextureTypes.INGOT_TEXTURES.contains(resourceItem) || TextureTypes.CRYSTAL_ITEM_TEXTURES.contains(resourceItem) || TextureTypes.GEM_ITEM_TEXTURES.contains(resourceItem)) {
                builder.resourceItemTexture(resourceItem);
            }
        }
        return builder;
    }),
    STORAGE_BLOCK(Version.OLD, "storageBlockTexture", (configVersion, builder, jsonObject) -> {
        if (configVersion.getNumber() > 0) {
            builder.storageBlockTexture(idFromJson(jsonObject,"storageBlockTexture"));
        } else {
            Identifier storageBlock = new Identifier(jsonObject.get(String.class, "storageBlockTexture"));
            if (TextureTypes.METAL_BLOCK_TEXTURES.contains(storageBlock) || TextureTypes.CRYSTAL_BLOCK_TEXTURES.contains(storageBlock) || TextureTypes.GEM_BLOCK_TEXTURES.contains(storageBlock)) {
                builder.storageBlockTexture(storageBlock);
            }
        }
        return builder;
    });

    private Version implementedVersion;
    private String name;
    private MaterialFieldsInterface fieldsInterface;

    MaterialFields(Version implementedVersion, String name, MaterialFieldsInterface fieldsInterface) {
        this.implementedVersion = implementedVersion;
        this.name = name;
        this.fieldsInterface = fieldsInterface;
    }

    public String getName() {
        return name;
    }

    public MaterialBuilder read(Version configVersion, MaterialBuilder builder, JsonObject jsonObject) {
        return this.fieldsInterface.read(configVersion, builder, jsonObject);
    }

    protected interface MaterialFieldsInterface {
        MaterialBuilder read(Version configVersion, MaterialBuilder builder, JsonObject jsonObject);
    }

    private static Identifier idFromJson(JsonObject jsonObject, String name) {
        JsonObject jsonObject1 = jsonObject.getObject(name);
        if (jsonObject1.containsKey("namespace")) return new Identifier(jsonObject1.get(String.class, "namespace"), jsonObject1.get(String.class, "path"));
        else return new Identifier(jsonObject1.get(String.class, "field_13353"), jsonObject1.get(String.class, "field_13355"));
    }
}
