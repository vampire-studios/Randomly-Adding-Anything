package io.github.vampirestudios.raa.client;

public class Color {

    private static final double FACTOR = 0.7;

    private final int color;

    public Color(int r, int g, int b, int a) {
        color = ((a & 0xFF) << 24) | ((r & 0xFF) << 16) | ((g & 0xFF) << 8) | ((b & 0xFF));
    }

    public Color(int rgb) {
        color = 0xff000000 | rgb;
    }

    public Color(int r, int g, int b) {
        this(r, g, b, 255);
    }

    public Color(float r, float g, float b) {
        this((int) (r * 255 + 0.5), (int) (g * 255 + 0.5), (int) (b * 255 + 0.5));
    }

    public int getColor() {
        return color;
    }

    public int getRed() {
        return (getColor() >> 16) & 0xFF;
    }

    public int getGreen() {
        return (getColor() >> 8) & 0xFF;
    }

    public int getBlue() {
        return getColor() & 0xFF;
    }

    public int getAlpha() {
        return (getColor() >> 24) & 0xff;
    }

    public static int[] intToRgb(int color) {
        int r = (color>>16)&0xFF;
        int g = (color>>8)&0xFF;
        int b = (color>>0)&0xFF;
        return new int[] {
                r, g, b
        };
    }
    /** Gets the chroma value, which is related to the length of the vector in projected (hexagonal) space. */
    public int getChroma() {
        int r = getRed();
        int g = getGreen();
        int b = getBlue();

        int max = Math.max(Math.max(r, g), b);
        int min = Math.min(Math.min(r, g), b);
        return max-min;
    }

    /** Gets the HSV/HSL Hue, which is the angle around the color hexagon (or circle) */
    public int getHue() {
        float r = getRed()/255f;
        float g = getGreen()/255f;
        float b = getBlue()/255f;

        float max = Math.max(Math.max(r, g), b);
        float min = Math.min(Math.min(r, g), b);
        float chroma = max-min;

        if (chroma==0) return 0;

        if (max>=r) return
                (int)((((g-b)/chroma) % 6 ) * 60);
        if (max>=g) return
                (int)((((b-r)/chroma) + 2) * 60);
        if (max>=b) return
                (int)((((r-g)/chroma) + 4) * 60);

        //Mathematically, we shouldn't ever reach here
        return 0;
    }

    /** Gets the HSL Lightness, or average light intensity, of this color */
    public int getLightness() {
        int r = getRed();
        int g = getGreen();
        int b = getBlue();

        int max = Math.max(Math.max(r, g), b);
        int min = Math.min(Math.min(r, g), b);
        return (max+min)/2;
    }

    /** Gets the HSL Luma, or perceptual brightness, of this color */
    public int getLuma() {
        float r = getRed()/255f;
        float g = getGreen()/255f;
        float b = getBlue()/255f;

        return (int)(((0.2126f * r) + (0.7152f * g) + (0.0722f * b)) * 255);
    }

    /** Gets the HSV Value, which is just the largest component in the color */
    public int getValue() {
        int r = getRed();
        int g = getGreen();
        int b = getBlue();

        return Math.max(Math.max(r, g), b);
    }

    /** Gets the saturation for this color based on chrominance and HSV Value */
    public float getHSVSaturation() {
        float v = getValue(); //I don't rescale these to 0..1 because it's just the ratio between them
        if (v==0) return 0;
        float c = getChroma();
        return c/v;
    }

    /** Gets the saturation for this color based on chrominance and HSL <em>luma</em>. */
    public float getHSLSaturation() {
        float l = getLuma()/255f; //rescaled here because there's more than just a ratio going on
        if (l==0 || l==1) return 0;
        float c = getChroma()/255f;
        return c / (1 - Math.abs(2*l - 1));
    }

    /**
     * Creates a new <code>Color</code> that is a brighter version of this
     * <code>Color</code>.
     * <p>
     * This method applies an arbitrary scale factor to each of the three RGB
     * components of this <code>Color</code> to create a brighter version
     * of this <code>Color</code>.
     * The {@code alpha} value is preserved.
     * Although <code>brighter</code> and
     * <code>darker</code> are inverse operations, the results of a
     * series of invocations of these two methods might be inconsistent
     * because of rounding errors.
     * @return     a new <code>Color</code> object that is
     *                 a brighter version of this <code>Color</code>
     *                 with the same {@code alpha} value.
     * @see        Color#darker
     * @since      JDK1.0
     */
    public Color brighter() {
        int r = getRed();
        int g = getGreen();
        int b = getBlue();
        int alpha = getAlpha();

        /* From 2D group:
         * 1. black.brighter() should return grey
         * 2. applying brighter to blue will always return blue, brighter
         * 3. non pure color (non zero rgb) will eventually return white
         */
        int i = (int)(1.0/(1.0-FACTOR));
        if ( r == 0 && g == 0 && b == 0) {
            return new Color(i, i, i, alpha);
        }
        if ( r > 0 && r < i ) r = i;
        if ( g > 0 && g < i ) g = i;
        if ( b > 0 && b < i ) b = i;

        return new Color(Math.min((int)(r/FACTOR), 255),
                Math.min((int)(g/FACTOR), 255),
                Math.min((int)(b/FACTOR), 255),
                alpha);
    }

    /**
     * Creates a new <code>Color</code> that is a darker version of this
     * <code>Color</code>.
     * <p>
     * This method applies an arbitrary scale factor to each of the three RGB
     * components of this <code>Color</code> to create a darker version of
     * this <code>Color</code>.
     * The {@code alpha} value is preserved.
     * Although <code>brighter</code> and
     * <code>darker</code> are inverse operations, the results of a series
     * of invocations of these two methods might be inconsistent because
     * of rounding errors.
     * @return  a new <code>Color</code> object that is
     *                    a darker version of this <code>Color</code>
     *                    with the same {@code alpha} value.
     * @see        Color#brighter
     * @since      JDK1.0
     */
    public Color darker() {
        return new Color(Math.max((int)(getRed()  *FACTOR), 0),
                Math.max((int)(getGreen()*FACTOR), 0),
                Math.max((int)(getBlue() *FACTOR), 0),
                getAlpha());
    }

    /**
     * Converts the components of a color, as specified by the HSB
     * model, to an equivalent set of values for the default RGB model.
     * <p>
     * The <code>saturation</code> and <code>brightness</code> components
     * should be floating-point values between zero and one
     * (numbers in the range 0.0-1.0).  The <code>hue</code> component
     * can be any floating-point number.  The floor of this number is
     * subtracted from it to create a fraction between 0 and 1.  This
     * fractional number is then multiplied by 360 to produce the hue
     * angle in the HSB color model.
     * <p>
     * The integer that is returned by <code>HSBtoRGB</code> encodes the
     * value of a color in bits 0-23 of an integer value that is the same
     * format used by the method {@link #getColor() getRGB}.
     * This integer can be supplied as an argument to the
     * <code>Color</code> constructor that takes a single integer argument.
     * @param     hue   the hue component of the color
     * @param     saturation   the saturation of the color
     * @param     brightness   the brightness of the color
     * @return    the RGB value of the color with the indicated hue,
     *                            saturation, and brightness.
     * @see       Color#getColor() ()
     * @see       Color#Color(int)
     * @since     JDK1.0
     */
    public static int HSBtoRGB(float hue, float saturation, float brightness) {
        int r = 0, g = 0, b = 0;
        if (saturation == 0) {
            r = g = b = (int) (brightness * 255.0f + 1f);
        } else {
            float h = (hue - (float)Math.floor(hue)) * 6.0f;
            float f = h - (float)java.lang.Math.floor(h);
            float p = brightness * (1.0f - saturation);
            float q = brightness * (1.0f - saturation * f);
            float t = brightness * (1.0f - (saturation * (1.0f - f)));
            switch ((int) h) {
                case 0:
                    r = (int) (brightness * 255.0f + 1f);
                    g = (int) (t * 255.0f + 1f);
                    b = (int) (p * 255.0f + 1f);
                    break;
                case 1:
                    r = (int) (q * 255.0f + 1f);
                    g = (int) (brightness * 255.0f + 1f);
                    b = (int) (p * 255.0f + 1f);
                    break;
                case 2:
                    r = (int) (p * 255.0f + 1f);
                    g = (int) (brightness * 255.0f + 1f);
                    b = (int) (t * 255.0f + 1f);
                    break;
                case 3:
                    r = (int) (p * 255.0f + 1f);
                    g = (int) (q * 255.0f + 1f);
                    b = (int) (brightness * 255.0f + 1f);
                    break;
                case 4:
                    r = (int) (t * 255.0f + 1f);
                    g = (int) (p * 255.0f + 1f);
                    b = (int) (brightness * 255.0f + 1f);
                    break;
                case 5:
                    r = (int) (brightness * 255.0f + 1f);
                    g = (int) (p * 255.0f + 1f);
                    b = (int) (q * 255.0f + 1f);
                    break;
            }
        }
        return 0xff000000 | (r << 16) | (g << 8) | (b);
    }

    /**
     * Converts the components of a color, as specified by the default RGB
     * model, to an equivalent set of values for hue, saturation, and
     * brightness that are the three components of the HSB model.
     * <p>
     * If the <code>hsbvals</code> argument is <code>null</code>, then a
     * new array is allocated to return the result. Otherwise, the method
     * returns the array <code>hsbvals</code>, with the values put into
     * that array.
     * @param     r   the red component of the color
     * @param     g   the green component of the color
     * @param     b   the blue component of the color
     * @param     hsbvals  the array used to return the
     *                     three HSB values, or <code>null</code>
     * @return    an array of three elements containing the hue, saturation,
     *                     and brightness (in that order), of the color with
     *                     the indicated red, green, and blue components.
     * @see       Color#getColor() ()
     * @see       Color#Color(int)
     * @since     JDK1.0
     */
    public static float[] RGBtoHSB(int r, int g, int b, float[] hsbvals) {
        float hue, saturation, brightness;
        if (hsbvals == null) {
            hsbvals = new float[3];
        }
        int cmax = Math.max(r, g);
        if (b > cmax) cmax = b;
        int cmin = Math.min(r, g);
        if (b < cmin) cmin = b;

        brightness = ((float) cmax) / 255.0f;
        if (cmax != 0)
            saturation = ((float) (cmax - cmin)) / ((float) cmax);
        else
            saturation = 0;
        if (saturation == 0)
            hue = 0;
        else {
            float redc = ((float) (cmax - r)) / ((float) (cmax - cmin));
            float greenc = ((float) (cmax - g)) / ((float) (cmax - cmin));
            float bluec = ((float) (cmax - b)) / ((float) (cmax - cmin));
            if (r == cmax)
                hue = bluec - greenc;
            else if (g == cmax)
                hue = 2.0f + redc - bluec;
            else
                hue = 4.0f + greenc - redc;
            hue = hue / 6.0f;
            if (hue < 0)
                hue = hue + 1.0f;
        }
        hsbvals[0] = hue;
        hsbvals[1] = saturation;
        hsbvals[2] = brightness;
        return hsbvals;
    }

    /**
     * Creates a <code>Color</code> object based on the specified values
     * for the HSB color model.
     * <p>
     * The <code>s</code> and <code>b</code> components should be
     * floating-point values between zero and one
     * (numbers in the range 0.0-1.0).  The <code>h</code> component
     * can be any floating-point number.  The floor of this number is
     * subtracted from it to create a fraction between 0 and 1.  This
     * fractional number is then multiplied by 360 to produce the hue
     * angle in the HSB color model.
     * @param  h   the hue component
     * @param  s   the saturation of the color
     * @param  b   the brightness of the color
     * @return  a <code>Color</code> object with the specified hue,
     *                                 saturation, and brightness.
     * @since   JDK1.0
     */
    public static Color getHSBColor(float h, float s, float b) {
        return new Color(HSBtoRGB(h, s, b));
    }
}
