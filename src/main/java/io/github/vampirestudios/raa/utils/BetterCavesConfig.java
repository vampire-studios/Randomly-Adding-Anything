package io.github.vampirestudios.raa.utils;

/**
 * This holds the baked (runtime) values for our config.
 * These values should never be from changed outside this package.
 */
public final class BetterCavesConfig {
    public static int lavaDepth = 10;

    // Cave gen vars
    public static String caveBiomeSize = "Large";
    public static String cavernBiomeSize = "Small";
    public static int    surfaceCutoff = 10;
    public static int    maxCaveAltitude = 120;

    // Cubic Cave vars
    public static int     cubicCaveBottom = 1;
    public static double  cubicYComp = 3.0F;
    public static double  cubicXZComp = 1.0F;
    public static String  cubicCaveFreq = "VeryCommon";
    public static float   cubicNoiseThreshold    = 0.95f;
    public static int     cubicFractalOctaves    = 1;
    public static float   cubicFractalGain       = .3f;
    public static float   cubicFractalFreq       = .03f;
    public static boolean cubicEnableTurbulence  = false;
    public static int     cubicTurbulenceOctaves = 3;
    public static float   cubicTurbulenceGain    = 45f;
    public static float   cubicTurbulenceFreq    = .01f;
    public static int     cubicNumGenerators     = 2;
    public static boolean cubicYAdjust           = true;
    public static float   cubicYAdjustF1         = .9f;
    public static float   cubicYAdjustF2         = .8f;

    // Simplex Cave vars
    public static int     simplexCaveBottom = 1;
    public static double  simplexYComp = 3.0F;
    public static double  simplexXZComp = 1.0F;
    public static String  simplexCaveFreq = "VeryCommon";
    public static float   simplexNoiseThreshold    = 0.86f;
    public static int     simplexFractalOctaves    = 1;
    public static float   simplexFractalGain       = .3f;
    public static float   simplexFractalFreq       = .017f;
    public static boolean simplexEnableTurbulence  = false;
    public static int     simplexTurbulenceOctaves = 3;
    public static float   simplexTurbulenceGain    = 45f;
    public static float   simplexTurbulenceFreq    = .01f;
    public static int     simplexNumGenerators     = 2;
    public static boolean simplexYAdjust           = true;
    public static float   simplexYAdjustF1         = .95f;
    public static float   simplexYAdjustF2         = .9f;

    // Lava Cavern vars
    public static int     lavaCavernCaveTop = 30;
    public static int     lavaCavernCaveBottom = 1;
    public static double  lavaCavernYComp = 1.0F;
    public static double  lavaCavernXZComp = 1.0F;
    public static String  lavaCavernCaveFreq = "Normal";
    public static float   lavaCavernNoiseThreshold = 0.7f;
    public static int     lavaCavernFractalOctaves = 1;
    public static float   lavaCavernFractalGain    = .3f;
    public static float   lavaCavernFractalFreq    = .03f;
    public static int     lavaCavernNumGenerators  = 2;

    // Lava Cavern vars
    public static int     flooredCavernCaveTop = 30;
    public static int     flooredCavernCaveBottom = 1;
    public static double  flooredCavernYComp = 1.0F;
    public static double  flooredCavernXZComp = 1.0F;
    public static String  flooredCavernCaveFreq = "Normal";
    public static float   flooredCavernNoiseThreshold = 0.7f;
    public static int     flooredCavernFractalOctaves = 1;
    public static float   flooredCavernFractalGain    = .3f;
    public static float   flooredCavernFractalFreq    = .03f;
    public static int     flooredCavernNumGenerators  = 2;

    // Water Cavern vars
    public static double  waterCavernYComp = 1.0F;
    public static double  waterCavernXZComp = 1.0F;
    public static float   waterCavernNoiseThreshold = 0.75f;
    public static int     waterCavernFractalOctaves = 1;
    public static float   waterCavernFractalGain    = .3f;
    public static float   waterCavernFractalFreq    = .03f;
    public static int     waterCavernNumGenerators  = 2;

    // Water biome vars
    public static boolean enableWaterBiomes = false;
    public static String waterBiomeFreq = "Normal";

    // Debug vars
    public static boolean enableDebugVisualizer = false;
}