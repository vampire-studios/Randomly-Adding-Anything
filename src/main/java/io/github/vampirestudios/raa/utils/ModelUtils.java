package io.github.vampirestudios.raa.utils;

import com.swordglowsblue.artifice.api.ArtificeResourcePack;
import net.minecraft.util.Identifier;

public class ModelUtils {

    public static void baseBlock(ArtificeResourcePack.ClientResourcePackBuilder pack, Identifier id) {
        pack.addBlockState(id, blockStateBuilder ->
                blockStateBuilder.variant("", variant -> variant.model(new Identifier(id.getNamespace(), "block/" + id.getPath()))));
        pack.addBlockModel(id, modelBuilder -> {
            modelBuilder.parent(new Identifier("block/cube_all"));
            modelBuilder.texture("all", new Identifier(id.getNamespace(), "blocks/" + id.getPath()));
        });
        pack.addItemModel(id, modelBuilder -> modelBuilder.parent(new Identifier(id.getNamespace(), "block/" + id.getPath())));
    }

    public static void simplePressurePlate(ArtificeResourcePack.ClientResourcePackBuilder pack, Identifier id) {
        pack.addBlockState(id, blockStateBuilder -> {
            blockStateBuilder.variant("powered=false", variant ->
                    variant.model(new Identifier(id.getNamespace(), "block/" + id.getPath())));
            blockStateBuilder.variant("powered=true", variant ->
                    variant.model(new Identifier(id.getNamespace(), "block/" + id.getPath() + "_down")));
        });

        pack.addBlockModel(new Identifier(id.getNamespace(), id.getPath()), modelBuilder -> {
            modelBuilder.parent(new Identifier("block/pressure_plate_up"));
            modelBuilder.texture("texture", new Identifier(id.getNamespace(), "blocks/" + id.getPath().replace("_pressure_plate", "")));
        });
        pack.addBlockModel(new Identifier(id.getNamespace(), id.getPath() + "_down"), modelBuilder -> {
            modelBuilder.parent(new Identifier("block/pressure_plate_down"));
            modelBuilder.texture("texture", new Identifier(id.getNamespace(), "blocks/" + id.getPath().replace("_pressure_plate", "")));
        });
        pack.addItemModel(id, modelBuilder -> modelBuilder.parent(new Identifier(id.getNamespace(), "block/" + id.getPath())));
    }

    public static void weightedPressurePlate(ArtificeResourcePack.ClientResourcePackBuilder pack, Identifier id) {
        pack.addBlockState(id, blockStateBuilder -> {
            blockStateBuilder.variant("power=0", variant -> variant.model(new Identifier(id.getNamespace(), "block/" + id.getPath())));
            blockStateBuilder.variant("power=1", variant -> variant.model(new Identifier(id.getNamespace(), "block/" + id.getPath() + "_down")));
            blockStateBuilder.variant("power=2", variant -> variant.model(new Identifier(id.getNamespace(), "block/" + id.getPath() + "_down")));
            blockStateBuilder.variant("power=3", variant -> variant.model(new Identifier(id.getNamespace(), "block/" + id.getPath() + "_down")));
            blockStateBuilder.variant("power=4", variant -> variant.model(new Identifier(id.getNamespace(), "block/" + id.getPath() + "_down")));
            blockStateBuilder.variant("power=5", variant -> variant.model(new Identifier(id.getNamespace(), "block/" + id.getPath() + "_down")));
            blockStateBuilder.variant("power=6", variant -> variant.model(new Identifier(id.getNamespace(), "block/" + id.getPath() + "_down")));
            blockStateBuilder.variant("power=7", variant -> variant.model(new Identifier(id.getNamespace(), "block/" + id.getPath() + "_down")));
            blockStateBuilder.variant("power=8", variant -> variant.model(new Identifier(id.getNamespace(), "block/" + id.getPath() + "_down")));
            blockStateBuilder.variant("power=9", variant -> variant.model(new Identifier(id.getNamespace(), "block/" + id.getPath() + "_down")));
            blockStateBuilder.variant("power=10", variant -> variant.model(new Identifier(id.getNamespace(), "block/" + id.getPath() + "_down")));
            blockStateBuilder.variant("power=11", variant -> variant.model(new Identifier(id.getNamespace(), "block/" + id.getPath() + "_down")));
            blockStateBuilder.variant("power=12", variant -> variant.model(new Identifier(id.getNamespace(), "block/" + id.getPath() + "_down")));
            blockStateBuilder.variant("power=13", variant -> variant.model(new Identifier(id.getNamespace(), "block/" + id.getPath() + "_down")));
            blockStateBuilder.variant("power=14", variant -> variant.model(new Identifier(id.getNamespace(), "block/" + id.getPath() + "_down")));
            blockStateBuilder.variant("power=15", variant -> variant.model(new Identifier(id.getNamespace(), "block/" + id.getPath() + "_down")));
        });

        pack.addBlockModel(new Identifier(id.getNamespace(), id.getPath()), modelBuilder -> {
            modelBuilder.parent(new Identifier("block/pressure_plate_up"));
            modelBuilder.texture("texture", new Identifier(id.getNamespace(), "blocks/" + id.getPath().replace("_pressure_plate", "")));
        });
        pack.addBlockModel(new Identifier(id.getNamespace(), id.getPath() + "_down"), modelBuilder -> {
            modelBuilder.parent(new Identifier("block/pressure_plate_down"));
            modelBuilder.texture("texture", new Identifier(id.getNamespace(), "blocks/" + id.getPath().replace("_pressure_plate", "")));
        });
        pack.addItemModel(id, modelBuilder -> modelBuilder.parent(new Identifier(id.getNamespace(), "block/" + id.getPath())));
    }

    public static void button(ArtificeResourcePack.ClientResourcePackBuilder pack, Identifier id) {
        pack.addBlockState(id, blockStateBuilder -> {
            blockStateBuilder.variant("face=floor,facing=east,powered=false", variant -> {
                variant.model(new Identifier(id.getNamespace(), "block/" + id.getPath()));
                variant.rotationY(90);
            });
            blockStateBuilder.variant("face=floor,facing=west,powered=false", variant -> {
                variant.model(new Identifier(id.getNamespace(), "block/" + id.getPath()));
                variant.rotationY(270);
            });
            blockStateBuilder.variant("face=floor,facing=south,powered=false", variant -> {
                variant.model(new Identifier(id.getNamespace(), "block/" + id.getPath()));
                variant.rotationY(180);
            });
            blockStateBuilder.variant("face=floor,facing=north,powered=false", variant ->
                    variant.model(new Identifier(id.getNamespace(), "block/" + id.getPath())));

            blockStateBuilder.variant("face=wall,facing=east,powered=false", variant -> {
                variant.model(new Identifier(id.getNamespace(), "block/" + id.getPath()));
                variant.rotationY(90);
                variant.rotationX(90);
                variant.uvlock(true);
            });
            blockStateBuilder.variant("face=wall,facing=west,powered=false", variant -> {
                variant.model(new Identifier(id.getNamespace(), "block/" + id.getPath()));
                variant.rotationY(270);
                variant.rotationX(90);
                variant.uvlock(true);
            });
            blockStateBuilder.variant("face=wall,facing=south,powered=false", variant -> {
                variant.model(new Identifier(id.getNamespace(), "block/" + id.getPath()));
                variant.rotationY(180);
                variant.rotationX(90);
                variant.uvlock(true);
            });
            blockStateBuilder.variant("face=wall,facing=north,powered=false", variant -> {
                variant.model(new Identifier(id.getNamespace(), "block/" + id.getPath()));
                variant.rotationX(90);
                variant.uvlock(true);
            });

            blockStateBuilder.variant("face=ceiling,facing=east,powered=false", variant -> {
                variant.model(new Identifier(id.getNamespace(), "block/" + id.getPath()));
                variant.rotationX(180);
                variant.rotationY(270);
            });
            blockStateBuilder.variant("face=ceiling,facing=west,powered=false", variant -> {
                variant.model(new Identifier(id.getNamespace(), "block/" + id.getPath()));
                variant.rotationX(180);
                variant.rotationY(90);
            });
            blockStateBuilder.variant("face=ceiling,facing=south,powered=false", variant -> {
                variant.model(new Identifier(id.getNamespace(), "block/" + id.getPath()));
                variant.rotationX(180);
            });
            blockStateBuilder.variant("face=ceiling,facing=north,powered=false", variant -> {
                variant.model(new Identifier(id.getNamespace(), "block/" + id.getPath()));
                variant.rotationX(180);
                variant.rotationY(180);
            });


            blockStateBuilder.variant("face=floor,facing=east,powered=true", variant -> {
                variant.model(new Identifier(id.getNamespace(), "block/" + id.getPath() + "_pressed"));
                variant.rotationY(90);
            });
            blockStateBuilder.variant("face=floor,facing=west,powered=true", variant -> {
                variant.model(new Identifier(id.getNamespace(), "block/" + id.getPath() + "_pressed"));
                variant.rotationY(270);
            });
            blockStateBuilder.variant("face=floor,facing=south,powered=true", variant -> {
                variant.model(new Identifier(id.getNamespace(), "block/" + id.getPath() + "_pressed"));
                variant.rotationY(180);
            });
            blockStateBuilder.variant("face=floor,facing=north,powered=true", variant ->
                    variant.model(new Identifier(id.getNamespace(), "block/" + id.getPath() + "_pressed")));

            blockStateBuilder.variant("face=wall,facing=east,powered=true", variant -> {
                variant.model(new Identifier(id.getNamespace(), "block/" + id.getPath() + "_pressed"));
                variant.rotationY(90);
                variant.rotationX(90);
                variant.uvlock(true);
            });
            blockStateBuilder.variant("face=wall,facing=west,powered=true", variant -> {
                variant.model(new Identifier(id.getNamespace(), "block/" + id.getPath() + "_pressed"));
                variant.rotationY(270);
                variant.rotationX(90);
                variant.uvlock(true);
            });
            blockStateBuilder.variant("face=wall,facing=south,powered=true", variant -> {
                variant.model(new Identifier(id.getNamespace(), "block/" + id.getPath() + "_pressed"));
                variant.rotationY(180);
                variant.rotationX(90);
                variant.uvlock(true);
            });
            blockStateBuilder.variant("face=wall,facing=north,powered=true", variant -> {
                variant.model(new Identifier(id.getNamespace(), "block/" + id.getPath() + "_pressed"));
                variant.rotationX(90);
                variant.uvlock(true);
            });

            blockStateBuilder.variant("face=ceiling,facing=east,powered=true", variant -> {
                variant.model(new Identifier(id.getNamespace(), "block/" + id.getPath() + "_pressed"));
                variant.rotationX(180);
                variant.rotationY(270);
            });
            blockStateBuilder.variant("face=ceiling,facing=west,powered=true", variant -> {
                variant.model(new Identifier(id.getNamespace(), "block/" + id.getPath() + "_pressed"));
                variant.rotationX(180);
                variant.rotationY(90);
            });
            blockStateBuilder.variant("face=ceiling,facing=south,powered=true", variant -> {
                variant.model(new Identifier(id.getNamespace(), "block/" + id.getPath() + "_pressed"));
                variant.rotationX(180);
            });
            blockStateBuilder.variant("face=ceiling,facing=north,powered=true", variant -> {
                variant.model(new Identifier(id.getNamespace(), "block/" + id.getPath() + "_pressed"));
                variant.rotationX(180);
                variant.rotationY(180);
            });
        });

        pack.addBlockModel(new Identifier(id.getNamespace(), id.getPath()), modelBuilder -> {
            modelBuilder.parent(new Identifier("block/button"));
            modelBuilder.texture("texture", new Identifier(id.getNamespace(), "blocks/" + id.getPath().replace("_button", "")));
        });
        pack.addBlockModel(new Identifier(id.getNamespace(), id.getPath() + "_inventory"), modelBuilder -> {
            modelBuilder.parent(new Identifier("block/button_inventory"));
            modelBuilder.texture("texture", new Identifier(id.getNamespace(), "blocks/" + id.getPath().replace("_button", "")));
        });
        pack.addBlockModel(new Identifier(id.getNamespace(), id.getPath() + "_pressed"), modelBuilder -> {
            modelBuilder.parent(new Identifier("block/button_pressed"));
            modelBuilder.texture("texture", new Identifier(id.getNamespace(), "blocks/" + id.getPath().replace("_button", "")));
        });
        pack.addItemModel(id, modelBuilder -> modelBuilder.parent(new Identifier(id.getNamespace(), "block/" + id.getPath() + "_inventory")));
    }

    public static void wall(ArtificeResourcePack.ClientResourcePackBuilder pack, Identifier id) {
        pack.addBlockState(id, blockStateBuilder -> {
            blockStateBuilder.multipartCase(aCase -> aCase.when("up", "true").apply(variant -> {
                variant.model(new Identifier(id.getNamespace(), "block/" + id.getPath() + "_post"));
            }));
            blockStateBuilder.multipartCase(aCase -> aCase.when("north", "true").apply(variant -> {
                variant.model(new Identifier(id.getNamespace(), "block/" + id.getPath() + "_side"));
                variant.uvlock(true);
            }));
            blockStateBuilder.multipartCase(aCase -> aCase.when("east", "true").apply(variant -> {
                variant.model(new Identifier(id.getNamespace(), "block/" + id.getPath() + "_side"));
                variant.uvlock(true);
                variant.rotationY(90);
            }));
            blockStateBuilder.multipartCase(aCase -> aCase.when("south", "true").apply(variant -> {
                variant.model(new Identifier(id.getNamespace(), "block/" + id.getPath() + "_side"));
                variant.uvlock(true);
                variant.rotationY(180);
            }));
            blockStateBuilder.multipartCase(aCase -> aCase.when("west", "true").apply(variant -> {
                variant.model(new Identifier(id.getNamespace(), "block/" + id.getPath() + "_side"));
                variant.uvlock(true);
                variant.rotationY(270);
            }));
        });

        pack.addBlockModel(new Identifier(id.getNamespace(), id.getPath() + "_inventory"), modelBuilder -> {
            modelBuilder.parent(new Identifier("block/wall_inventory"));
            modelBuilder.texture("wall", new Identifier(id.getNamespace(), "blocks/" + id.getPath().replace("_wall", "")));
        });
        pack.addBlockModel(new Identifier(id.getNamespace(), id.getPath() + "_post"), modelBuilder -> {
            modelBuilder.parent(new Identifier("block/template_wall_post"));
            modelBuilder.texture("wall", new Identifier(id.getNamespace(), "blocks/" + id.getPath().replace("_wall", "")));
        });
        pack.addBlockModel(new Identifier(id.getNamespace(), id.getPath() + "_side"), modelBuilder -> {
            modelBuilder.parent(new Identifier("block/template_wall_side"));
            modelBuilder.texture("wall", new Identifier(id.getNamespace(), "blocks/" + id.getPath().replace("_wall", "")));
        });
        pack.addItemModel(id, modelBuilder -> modelBuilder.parent(new Identifier(id.getNamespace(), "block/" + id.getPath() + "_inventory")));
    }

    public static void stairs(ArtificeResourcePack.ClientResourcePackBuilder pack, Identifier id) {
        pack.addBlockState(id, blockStateBuilder -> {
            blockStateBuilder.variant("facing=east,half=bottom,shape=straight", variant ->
                    variant.model(new Identifier(id.getNamespace(), "block/" + id.getPath())));
            blockStateBuilder.variant("facing=west,half=bottom,shape=straight", variant -> {
                variant.model(new Identifier(id.getNamespace(), "block/" + id.getPath()));
                variant.rotationY(180);
                variant.uvlock(true);
            });
            blockStateBuilder.variant("facing=south,half=bottom,shape=straight", variant -> {
                variant.model(new Identifier(id.getNamespace(), "block/" + id.getPath()));
                variant.rotationY(90);
                variant.uvlock(true);
            });
            blockStateBuilder.variant("facing=north,half=bottom,shape=straight", variant -> {
                variant.model(new Identifier(id.getNamespace(), "block/" + id.getPath()));
                variant.rotationY(270);
                variant.uvlock(true);
            });

            blockStateBuilder.variant("facing=east,half=bottom,shape=outer_right", variant ->
                    variant.model(new Identifier(id.getNamespace(), "block/" + id.getPath() + "_outer")));
            blockStateBuilder.variant("facing=west,half=bottom,shape=outer_right", variant -> {
                variant.model(new Identifier(id.getNamespace(), "block/" + id.getPath() + "_outer"));
                variant.rotationY(180);
                variant.uvlock(true);
            });
            blockStateBuilder.variant("facing=south,half=bottom,shape=outer_right", variant -> {
                variant.model(new Identifier(id.getNamespace(), "block/" + id.getPath() + "_outer"));
                variant.rotationY(90);
                variant.uvlock(true);
            });
            blockStateBuilder.variant("facing=north,half=bottom,shape=outer_right", variant -> {
                variant.model(new Identifier(id.getNamespace(), "block/" + id.getPath() + "_outer"));
                variant.rotationY(270);
                variant.uvlock(true);
            });

            blockStateBuilder.variant("facing=south,half=bottom,shape=outer_left", variant ->
                    variant.model(new Identifier(id.getNamespace(), "block/" + id.getPath() + "_outer")));
            blockStateBuilder.variant("facing=north,half=bottom,shape=outer_left", variant -> {
                variant.model(new Identifier(id.getNamespace(), "block/" + id.getPath() + "_outer"));
                variant.rotationY(180);
                variant.uvlock(true);
            });
            blockStateBuilder.variant("facing=west,half=bottom,shape=outer_left", variant -> {
                variant.model(new Identifier(id.getNamespace(), "block/" + id.getPath() + "_outer"));
                variant.rotationY(90);
                variant.uvlock(true);
            });
            blockStateBuilder.variant("facing=east,half=bottom,shape=outer_left", variant -> {
                variant.model(new Identifier(id.getNamespace(), "block/" + id.getPath() + "_outer"));
                variant.rotationY(270);
                variant.uvlock(true);
            });

            blockStateBuilder.variant("facing=east,half=bottom,shape=inner_right", variant ->
                    variant.model(new Identifier(id.getNamespace(), "block/" + id.getPath() + "_inner")));
            blockStateBuilder.variant("facing=west,half=bottom,shape=inner_right", variant -> {
                variant.model(new Identifier(id.getNamespace(), "block/" + id.getPath() + "_inner"));
                variant.rotationY(180);
                variant.uvlock(true);
            });
            blockStateBuilder.variant("facing=south,half=bottom,shape=inner_right", variant -> {
                variant.model(new Identifier(id.getNamespace(), "block/" + id.getPath() + "_inner"));
                variant.rotationY(90);
                variant.uvlock(true);
            });
            blockStateBuilder.variant("facing=north,half=bottom,shape=inner_right", variant -> {
                variant.model(new Identifier(id.getNamespace(), "block/" + id.getPath() + "_inner"));
                variant.rotationY(270);
                variant.uvlock(true);
            });

            blockStateBuilder.variant("facing=south,half=bottom,shape=inner_left", variant ->
                    variant.model(new Identifier(id.getNamespace(), "block/" + id.getPath() + "_inner")));
            blockStateBuilder.variant("facing=north,half=bottom,shape=inner_left", variant -> {
                variant.model(new Identifier(id.getNamespace(), "block/" + id.getPath() + "_inner"));
                variant.rotationY(180);
                variant.uvlock(true);
            });
            blockStateBuilder.variant("facing=west,half=bottom,shape=inner_left", variant -> {
                variant.model(new Identifier(id.getNamespace(), "block/" + id.getPath() + "_inner"));
                variant.rotationY(90);
                variant.uvlock(true);
            });
            blockStateBuilder.variant("facing=east,half=bottom,shape=inner_left", variant -> {
                variant.model(new Identifier(id.getNamespace(), "block/" + id.getPath() + "_inner"));
                variant.rotationY(270);
                variant.uvlock(true);
            });


            blockStateBuilder.variant("facing=east,half=top,shape=straight", variant -> {
                variant.model(new Identifier(id.getNamespace(), "block/" + id.getPath()));
                variant.rotationX(180);
                variant.uvlock(true);
            });
            blockStateBuilder.variant("facing=west,half=top,shape=straight", variant -> {
                variant.model(new Identifier(id.getNamespace(), "block/" + id.getPath()));
                variant.rotationY(180);
                variant.uvlock(true);
                variant.rotationX(180);
            });
            blockStateBuilder.variant("facing=south,half=top,shape=straight", variant -> {
                variant.model(new Identifier(id.getNamespace(), "block/" + id.getPath()));
                variant.rotationY(90);
                variant.uvlock(true);
                variant.rotationX(180);
            });
            blockStateBuilder.variant("facing=north,half=top,shape=straight", variant -> {
                variant.model(new Identifier(id.getNamespace(), "block/" + id.getPath()));
                variant.rotationY(270);
                variant.uvlock(true);
                variant.rotationX(180);
            });

            blockStateBuilder.variant("facing=east,half=top,shape=outer_left", variant -> {
                variant.model(new Identifier(id.getNamespace(), "block/" + id.getPath() + "_outer"));
                variant.rotationX(180);
                variant.uvlock(true);
            });
            blockStateBuilder.variant("facing=west,half=top,shape=outer_left", variant -> {
                variant.model(new Identifier(id.getNamespace(), "block/" + id.getPath() + "_outer"));
                variant.rotationY(180);
                variant.uvlock(true);
                variant.rotationX(180);
            });
            blockStateBuilder.variant("facing=south,half=top,shape=outer_left", variant -> {
                variant.model(new Identifier(id.getNamespace(), "block/" + id.getPath() + "_outer"));
                variant.rotationY(90);
                variant.uvlock(true);
                variant.rotationX(180);
            });
            blockStateBuilder.variant("facing=north,half=top,shape=outer_left", variant -> {
                variant.model(new Identifier(id.getNamespace(), "block/" + id.getPath() + "_outer"));
                variant.rotationY(270);
                variant.uvlock(true);
                variant.rotationX(180);
            });

            blockStateBuilder.variant("facing=south,half=top,shape=outer_right", variant -> {
                variant.model(new Identifier(id.getNamespace(), "block/" + id.getPath() + "_outer"));
                variant.rotationY(180);
                variant.rotationX(180);
                variant.uvlock(true);
            });
            blockStateBuilder.variant("facing=north,half=top,shape=outer_right", variant -> {
                variant.model(new Identifier(id.getNamespace(), "block/" + id.getPath() + "_outer"));
                variant.uvlock(true);
                variant.rotationX(180);
            });
            blockStateBuilder.variant("facing=west,half=top,shape=outer_right", variant -> {
                variant.model(new Identifier(id.getNamespace(), "block/" + id.getPath() + "_outer"));
                variant.uvlock(true);
                variant.rotationX(180);
                variant.rotationY(270);
            });
            blockStateBuilder.variant("facing=east,half=top,shape=outer_right", variant -> {
                variant.model(new Identifier(id.getNamespace(), "block/" + id.getPath() + "_outer"));
                variant.uvlock(true);
                variant.rotationX(180);
                variant.rotationY(90);
            });

            blockStateBuilder.variant("facing=north,half=top,shape=inner_right", variant -> {
                variant.model(new Identifier(id.getNamespace(), "block/" + id.getPath() + "_inner"));
                variant.rotationX(180);
                variant.uvlock(true);
            });
            blockStateBuilder.variant("facing=south,half=top,shape=inner_right", variant -> {
                variant.model(new Identifier(id.getNamespace(), "block/" + id.getPath() + "_inner"));
                variant.rotationY(180);
                variant.uvlock(true);
                variant.rotationX(180);
            });
            blockStateBuilder.variant("facing=east,half=top,shape=inner_right", variant -> {
                variant.model(new Identifier(id.getNamespace(), "block/" + id.getPath() + "_inner"));
                variant.rotationY(90);
                variant.uvlock(true);
                variant.rotationX(180);
            });
            blockStateBuilder.variant("facing=west,half=top,shape=inner_right", variant -> {
                variant.model(new Identifier(id.getNamespace(), "block/" + id.getPath() + "_inner"));
                variant.rotationY(270);
                variant.uvlock(true);
                variant.rotationX(180);
            });

            blockStateBuilder.variant("facing=east,half=top,shape=inner_left", variant -> {
                variant.model(new Identifier(id.getNamespace(), "block/" + id.getPath() + "_inner"));
                variant.rotationX(180);
                variant.uvlock(true);
            });
            blockStateBuilder.variant("facing=west,half=top,shape=inner_left", variant -> {
                variant.model(new Identifier(id.getNamespace(), "block/" + id.getPath() + "_inner"));
                variant.rotationY(180);
                variant.uvlock(true);
                variant.rotationX(180);
            });
            blockStateBuilder.variant("facing=south,half=top,shape=inner_left", variant -> {
                variant.model(new Identifier(id.getNamespace(), "block/" + id.getPath() + "_inner"));
                variant.rotationY(90);
                variant.uvlock(true);
                variant.rotationX(180);
            });
            blockStateBuilder.variant("facing=north,half=top,shape=inner_left", variant -> {
                variant.model(new Identifier(id.getNamespace(), "block/" + id.getPath() + "_inner"));
                variant.rotationY(270);
                variant.uvlock(true);
                variant.rotationX(180);
            });
        });
        pack.addBlockModel(new Identifier(id.getNamespace(), id.getPath()), modelBuilder -> {
            modelBuilder.parent(new Identifier("block/stairs"));
            modelBuilder.texture("bottom", new Identifier(id.getNamespace(), "blocks/" + id.getPath().replace("_stairs", "")));
            modelBuilder.texture("top", new Identifier(id.getNamespace(), "blocks/" + id.getPath().replace("_stairs", "")));
            modelBuilder.texture("side", new Identifier(id.getNamespace(), "blocks/" + id.getPath().replace("_stairs", "")));
        });
        pack.addBlockModel(new Identifier(id.getNamespace(), id.getPath() + "_inner"), modelBuilder -> {
            modelBuilder.parent(new Identifier("block/inner_stairs"));
            modelBuilder.texture("bottom", new Identifier(id.getNamespace(), "blocks/" + id.getPath().replace("_stairs", "")));
            modelBuilder.texture("top", new Identifier(id.getNamespace(), "blocks/" + id.getPath().replace("_stairs", "")));
            modelBuilder.texture("side", new Identifier(id.getNamespace(), "blocks/" + id.getPath().replace("_stairs", "")));
        });
        pack.addBlockModel(new Identifier(id.getNamespace(), id.getPath() + "_outer"), modelBuilder -> {
            modelBuilder.parent(new Identifier("block/outer_stairs"));
            modelBuilder.texture("bottom", new Identifier(id.getNamespace(), "blocks/" + id.getPath().replace("_stairs", "")));
            modelBuilder.texture("top", new Identifier(id.getNamespace(), "blocks/" + id.getPath().replace("_stairs", "")));
            modelBuilder.texture("side", new Identifier(id.getNamespace(), "blocks/" + id.getPath().replace("_stairs", "")));
        });
        pack.addItemModel(id, modelBuilder -> modelBuilder.parent(new Identifier(id.getNamespace(), "block/" + id.getPath())));
    }

    public static void slab(ArtificeResourcePack.ClientResourcePackBuilder pack, Identifier id) {
        pack.addBlockState(id, blockStateBuilder -> {
            blockStateBuilder.variant("type=bottom", variant ->
                    variant.model(new Identifier(id.getNamespace(), "block/" + id.getPath())));
            blockStateBuilder.variant("type=top", variant ->
                    variant.model(new Identifier(id.getNamespace(), "block/" + id.getPath() + "_top")));
            blockStateBuilder.variant("type=double", variant ->
                    variant.model(new Identifier(id.getNamespace(), "block/" + id.getPath().replace("_slab", ""))));
        });
        pack.addBlockModel(id, modelBuilder -> {
            modelBuilder.parent(new Identifier("block/slab"));
            modelBuilder.texture("bottom", new Identifier(id.getNamespace(), "blocks/" + id.getPath().replace("_slab", "")));
            modelBuilder.texture("top", new Identifier(id.getNamespace(), "blocks/" + id.getPath().replace("_slab", "")));
            modelBuilder.texture("side", new Identifier(id.getNamespace(), "blocks/" + id.getPath().replace("_slab", "")));
        });
        pack.addBlockModel(new Identifier(id.getNamespace(), id.getPath() + "_top"), modelBuilder -> {
            modelBuilder.parent(new Identifier("block/slab_top"));
            modelBuilder.texture("bottom", new Identifier(id.getNamespace(), "blocks/" + id.getPath().replace("_slab", "")));
            modelBuilder.texture("top", new Identifier(id.getNamespace(), "blocks/" + id.getPath().replace("_slab", "")));
            modelBuilder.texture("side", new Identifier(id.getNamespace(), "blocks/" + id.getPath().replace("_slab", "")));
        });
        pack.addItemModel(id, modelBuilder -> modelBuilder.parent(new Identifier(id.getNamespace(), "block/" + id.getPath())));
    }

}
