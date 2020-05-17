package io.github.vampirestudios.raa.utils;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.util.Identifier;

import java.util.Random;

public class TextureUtils {

    public static NativeImage getImage(Identifier texture) {
        try {
            System.out.println(MinecraftClient.getInstance().getResourceManager().getResource(texture).getInputStream().toString());
            return NativeImage.read(MinecraftClient.getInstance().getResourceManager().getResource(texture).getInputStream());
        } catch(Exception ignored) {}
        return null;
    }

    public static NativeImage generateOreTexture(NativeImage stone, long seed, int color) {
        NativeImage canvas = new NativeImage(16, 16, true);
        Random rand = new Random(seed);
        // Init the canvas:
        /*for(int px = 0; px < 16; px++) {
            for(int py = 0; py < 16; py++) {
                canvas.setPixelRgba(px, py, stone.getPixelRgba(px, py));
            }
        }*/
        int [] colors = new int[6]; // Use a color palette of only 6 different colors.
        for(int i = 0; i < 6; i++) {
            int r = (color >>> 16) & 255;
            int g = (color >>> 8) & 255;
            int b = (color) & 255;
            // Add a brightness value to the color:
            int brightness = -100*(3-i)/4;
            r += brightness;
            g += brightness;
            b += brightness;
            // make sure that once a color channel is satured the others get increased further:
            int totalDif = 0;
            if(r > 255) {
                totalDif += r-255;
            }
            if(g > 255) {
                totalDif += g-255;
            }
            if(b > 255) {
                totalDif += b-255;
            }
            totalDif = totalDif*3/2;
            r += totalDif;
            g += totalDif;
            b += totalDif;
            // Bound checks(Before adding random values, so even 255 white can get modified):
            if(r > 255) r = 255;
            if(r < 0) r = 0;
            if(g > 255) g = 255;
            if(g < 0) g = 0;
            if(b > 255) b = 255;
            if(b < 0) b = 0;
            // Add some flavor to the color, so it's not just a scale based on lighting:
            r += rand.nextInt(32) - 16;
            g += rand.nextInt(32) - 16;
            b += rand.nextInt(32) - 16;
            // Bound checks:
            if(r > 255) r = 255;
            if(r < 0) r = 0;
            if(g > 255) g = 255;
            if(g < 0) g = 0;
            if(b > 255) b = 255;
            if(b < 0) b = 0;
            colors[i] = (r << 16) | (g << 8) | b;
        }
        // Size arguments for the semi major axis:
        double size = 1.1 + rand.nextDouble()*1.5;
        double variation = 0.5*size*rand.nextDouble();
        // Size arguments of the semi minor axis:
        double standard2 = size/3.5*0.7*rand.nextDouble();
        double variation2 = (1-standard2)*0.5*rand.nextDouble();
        // standard rotation and how far the rotation may differ for each spawn location:
        double rotation0 = rand.nextDouble()*2*Math.PI;
        double rotationVar = rand.nextDouble()*2*Math.PI;
        // Number of ovals drawn:
        int spawns = (int)(rand.nextDouble()*4) + 8 + (int)(30.0/Math.pow(size-variation/2, 4));
        boolean isCrystal = rand.nextDouble() < 0.0; // TODO
        int tries = 0;
        outer:
        for(int i = 0; i < spawns; i++) {
            if(!isCrystal) { // Just some rotated oval shape.
                double actualSize = size - rand.nextDouble()*variation;
                double actualSizeSmall = actualSize*(1 - (standard2+variation2*(rand.nextDouble() - 0.5)));
                // Rotate the oval by a random angle:
                double angle = rotation0 + rand.nextDouble()*rotationVar;
                double xMain = Math.sin(angle)/actualSize;
                double yMain = Math.cos(angle)/actualSize;
                double xSecn = Math.cos(angle)/actualSizeSmall;
                double ySecn = -Math.sin(angle)/actualSizeSmall;
                // Make sure the ovals don't touch the border of the block texture to remove hard edges between the ore and normal stone blocks:
                double xOffset = Math.max(Math.abs(xMain*actualSize*actualSize), Math.abs(xSecn*actualSizeSmall*actualSizeSmall));
                double yOffset = Math.max(Math.abs(yMain*actualSize*actualSize), Math.abs(ySecn*actualSizeSmall*actualSizeSmall));
                double x = xOffset + rand.nextDouble()*(15 - 2*xOffset);
                double y = yOffset + rand.nextDouble()*(15 - 2*yOffset);
                int xMin = (int)(x-actualSize);
                int xMax = (int)(x+actualSize);
                int yMin = (int)(y-actualSize);
                int yMax = (int)(y+actualSize);
                // Make sure this ellipse doesn't overlap another older one:
                for(int px = xMin; px <= xMax; px++) {
                    for(int py = yMin; py <= yMax; py++) {
                        double deltaX = px-x;
                        double deltaY = py-y;
                        double distMain = deltaX*xMain+deltaY*yMain;
                        double distSecn = deltaX*xSecn+deltaY*ySecn;
                        if(distMain*distMain+distSecn*distSecn < 1) {
                            /*if(stone.getPixelRgba(px, py) != canvas.getPixelRgba(px, py)) {
                                // Give 3 tries to create the oval coordinates, then move on to the next spawn, yo the program cannot get stuck in an infinite loop.
                                tries++;
                                if(tries < 3)
                                    i--;
                                continue outer;
                            }*/
                        }
                    }
                }
                tries = 0;
                for(int px = xMin; px <= xMax; px++) {
                    for(int py = yMin; py <= yMax; py++) {
                        double deltaX = px - x;
                        double deltaY = py - y;
                        double distMain = deltaX*xMain + deltaY*yMain;
                        double distSecn = deltaX*xSecn + deltaY*ySecn;
                        if(distMain*distMain + distSecn*distSecn < 1) {
                            // Light is determined as how far to the upper left the current pixel is relative to the center.
                            double light = (-deltaX*Math.sqrt(0.5) - deltaY*Math.sqrt(0.5))/actualSizeSmall;
                            // Determine the index in the color palette that fits the pseudo-lighting conditions:
                            int lightIndex = 3 + (int)Math.round(light*8.0/3);
                            if(lightIndex < 0) lightIndex = 0;
                            if(lightIndex >= 6) lightIndex = 5;
                            int bestColor = colors[lightIndex];
                            canvas.setPixelRgba(px, py, 0xff000000 | bestColor);
                        }
                    }
                }
            } else { // TODO

            }
        }
        return canvas;
    }

}
