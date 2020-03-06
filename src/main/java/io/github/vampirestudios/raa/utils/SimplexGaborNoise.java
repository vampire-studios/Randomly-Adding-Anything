package io.github.vampirestudios.raa.utils;

/**
 * K.jpg's Simplex-Gabor Noise. Gabor-reminiscent noise,
 * based on OpenSimplex 2, smooth variant ("SuperSimplex")
 *
 * <gx, gy> or <gx, gy, gz> should be a unit vector multiplied
 * by the desired relative sinusoid frequency.
 * If picking the vector randomly, then for best results
 * consider smoothness and isotropy.
 *
 * Notes:
 * - Only supports basic (and approximated) sinusoidal kernels.
 * - Not normalized in its current version.
 * - You might be able to see some of the underlying triangle grid
 *   in the result, if you look at the gray regions. I don't think
 *   it's as detracting as a square grid, but it's not as nice as
 *   original Gabor noise in that regard.
 * - Gray regions can be reduced a bit, by adding up just the attns
 *   in a 'weights' variable, and returning values/weights.
 */
public class SimplexGaborNoise {
	
	private static final int PSIZE = 2048;
	private static final int PMASK = 2047;
	private double hashToOffset = 2.0 / PSIZE;

	private short[] perm;

	public SimplexGaborNoise(long seed) {
		perm = new short[PSIZE];
		short[] source = new short[PSIZE]; 
		for (short i = 0; i < PSIZE; i++)
			source[i] = i;
		for (int i = PSIZE - 1; i >= 0; i--) {
			seed = seed * 6364136223846793005L + 1442695040888963407L;
			int r = (int)((seed + 31) % (i + 1));
			if (r < 0)
				r += (i + 1);
			perm[i] = source[r];
			source[r] = source[i];
		}
	}
	
	/*
	 * Noise Evaluators
	 */
	
	/**
	 * 2D SuperSimplex noise, standard lattice orientation.
	 */
	public double noise2(double x, double y, double gx, double gy) {
		
		// Get points for A2* lattice
		double s = 0.366025403784439 * (x + y);
		double xs = x + s, ys = y + s;
		
		return noise2_Base(xs, ys, gx, gy);
	}
	
	/**
	 * 2D SuperSimplex noise, with Y pointing down the main diagonal.
	 * Might be better for a 2D sandbox style game, where Y is vertical.
	 * Probably slightly less optimal for heightmaps or continent maps.
	 */
	public double noise2_XBeforeY(double x, double y, double gx, double gy) {
		
		// Skew transform and rotation baked into one.
		double xx = x * 0.7071067811865476;
		double yy = y * 1.224744871380249;
		double gxr = (gy + gx) * 0.7071067811865476;
		double gyr = (gy - gx) * 0.7071067811865476;
		
		return noise2_Base(yy + xx, yy - xx, gxr, gyr);
	}
	
	/**
	 * 2D SuperSimplex noise base.
	 * Lookup table implementation inspired by DigitalShadow.
	 */
	private double noise2_Base(double xs, double ys, double gx, double gy) {
		double value = 0;
		
		// Get base points and offsets
		int xsb = fastFloor(xs), ysb = fastFloor(ys);
		double xsi = xs - xsb, ysi = ys - ysb;
		
		// Index to point list
		int a = (int)(xsi + ysi);
		int index =
			(a << 2) |
			(int)(xsi - ysi / 2 + 1 - a / 2.0) << 3 |
			(int)(ysi - xsi / 2 + 1 - a / 2.0) << 4;
		
		double ssi = (xsi + ysi) * -0.211324865405187;
		double xi = xsi + ssi, yi = ysi + ssi;

		// Point contributions
		for (int i = 0; i < 4; i++) {
			LatticePoint2D c = LOOKUP_2D[index + i];

			double dx = xi + c.dx, dy = yi + c.dy;
			double attn = 2.0 / 3.0 - dx * dx - dy * dy;
			if (attn <= 0) continue;

			int pxm = (xsb + c.xsv) & PMASK, pym = (ysb + c.ysv) & PMASK;
			double offset = perm[perm[pxm] ^ pym] * hashToOffset;
			double extrapolation = fastPseudoSine(gx * dx + gy * dy + offset);
			
			attn = attn * attn * attn;
			value += attn * extrapolation;
		}
		
		return value;
	}
	
	/**
	 * 3D Re-oriented 8-point BCC noise, classic orientation
	 * Proper substitute for what 3D SuperSimplex would be,
	 * in light of Forbidden Formulae.
	 * Use noise3_XYBeforeZ or noise3_XZBeforeY instead, wherever appropriate.
	 */
	public double noise3_Classic(double x, double y, double z, double gx, double gy, double gz) {
		
		// Re-orient the cubic lattices via rotation, to produce the expected look on cardinal planar slices.
		// If texturing objects that don't tend to have cardinal plane faces, you could even remove this.
		// Orthonormal rotation. Not a skew transform.
		double r = (2.0 / 3.0) * (x + y + z);
		double xr = r - x, yr = r - y, zr = r - z;
		double gr = (2.0 / 3.0) * (gx + gy + gz);
		double gxr = gr - gx, gyr = gr - gy, gzr = gr - gz;
		
		// Evaluate both lattices to form a BCC lattice.
		return noise3_BCC(xr, yr, zr, gxr, gyr, gzr);
	}
	
	/**
	 * 3D Re-oriented 8-point BCC noise, with better visual isotropy in (X, Y).
	 * Recommended for 3D terrain and time-varied animations.
	 * The Z coordinate should always be the "different" coordinate in your use case.
	 * If Y is vertical in world coordinates, call noise3_XYBeforeZ(x, z, Y) or use noise3_XZBeforeY.
	 * If Z is vertical in world coordinates, call noise3_XYBeforeZ(x, y, Z).
	 * For a time varied animation, call noise3_XYBeforeZ(x, y, T).
	 */
	public double noise3_XYBeforeZ(double x, double y, double z, double gx, double gy, double gz) {
		
		// Re-orient the cubic lattices without skewing, to make X and Y triangular like 2D.
		// Orthonormal rotation. Not a skew transform.
		double xy = x + y;
		double zz = z * 0.577350269189626;
		double s2 = xy * -0.211324865405187 - zz;
		double xr = x + s2, yr = y + s2;
		double zr = xy * 0.577350269189626 + zz;
		double gxy = gx + gy;
		double gzz = gz * 0.577350269189626;
		double gs2 = gxy * -0.211324865405187 - gzz;
		double gxr = gx + gs2, gyr = gy + gs2;
		double gzr = gxy * 0.577350269189626 + gzz;
		
		// Evaluate both lattices to form a BCC lattice.
		return noise3_BCC(xr, yr, zr, gxr, gyr, gzr);
	}
	
	/**
	 * 3D Re-oriented 8-point BCC noise, with better visual isotropy in (X, Z).
	 * Recommended for 3D terrain and time-varied animations.
	 * The Y coordinate should always be the "different" coordinate in your use case.
	 * If Y is vertical in world coordinates, call noise3_XZBeforeY(x, Y, z).
	 * If Z is vertical in world coordinates, call noise3_XZBeforeY(x, Z, y) or use noise3_XYBeforeZ.
	 * For a time varied animation, call noise3_XZBeforeY(x, T, y) or use noise3_XYBeforeZ.
	 */
	public double noise3_XZBeforeY(double x, double y, double z, double gx, double gy, double gz) {
		
		// Re-orient the cubic lattices without skewing, to make X and Z triangular like 2D.
		// Orthonormal rotation. Not a skew transform.
		double xz = x + z;
		double yy = y * 0.577350269189626;
		double s2 = xz * -0.211324865405187 - yy;
		double xr = x + s2; double zr = z + s2;
		double yr = xz * 0.577350269189626 + yy;
		double gxz = gx + gz;
		double gyy = gy * 0.577350269189626;
		double gs2 = gxz * -0.211324865405187 - gyy;
		double gxr = gx + gs2; double gzr = gz + gs2;
		double gyr = gxz * 0.577350269189626 + gyy;
		
		// Evaluate both lattices to form a BCC lattice.
		return noise3_BCC(xr, yr, zr, gxr, gyr, gzr);
	}
	
	/**
	 * Generate overlapping cubic lattices for 3D Re-oriented BCC noise.
	 * Lookup table implementation inspired by DigitalShadow.
	 * It was actually faster to narrow down the points in the loop itself,
	 * than to build up the index with enough info to isolate 8 points.
	 */
	private double noise3_BCC(double xr, double yr, double zr, double gxr, double gyr, double gzr) {
		
		// Get base and offsets inside cube of first lattice.
		int xrb = fastFloor(xr), yrb = fastFloor(yr), zrb = fastFloor(zr);
		double xri = xr - xrb, yri = yr - yrb, zri = zr - zrb;
		
		// Identify which octant of the cube we're in. This determines which cell
		// in the other cubic lattice we're in, and also narrows down one point on each.
		int xht = (int)(xri + 0.5), yht = (int)(yri + 0.5), zht = (int)(zri + 0.5);
		int index = (xht) | (yht << 1) | (zht << 2);
		
		// Point contributions
		double value = 0;
		LatticePoint3D c = LOOKUP_3D[index];
		while (c != null) {
			double dxr = xri + c.dxr, dyr = yri + c.dyr, dzr = zri + c.dzr;
			double attn = 0.75 - dxr * dxr - dyr * dyr - dzr * dzr;
			if (attn < 0) {
				c = c.nextOnFailure;
			} else {
				int pxm = (xrb + c.xrv) & PMASK, pym = (yrb + c.yrv) & PMASK, pzm = (zrb + c.zrv) & PMASK;
				double offset = perm[perm[perm[pxm] ^ pym] ^ pzm] * hashToOffset;
				double extrapolation = fastPseudoSine(gxr * dxr + gyr * dyr + gzr * dzr + offset);
				
				attn = attn * attn * attn;
				value += attn * extrapolation;
				c = c.nextOnSuccess;
			}
		}
		return value;
	}
	
	/*
	 * Utility
	 */
	
	private static int fastFloor(double x) {
		int xi = (int)x;
		return x < xi ? xi - 1 : xi;
	}
	
	private static double fastPseudoSine(double x) {
		int xb = fastFloor(x);
		double t = x - xb;
		double part = t * (1 + t * t * (-2 + t));
		return (xb & 1) == 0 ? part : -part;
	}
	
	/*
	 * Definitions
	 */

	private static final LatticePoint2D[] LOOKUP_2D;
	private static final LatticePoint3D[] LOOKUP_3D;
	static {
		LOOKUP_2D = new LatticePoint2D[8 * 4];
		LOOKUP_3D = new LatticePoint3D[8];
		
		for (int i = 0; i < 8; i++) {
			int i1, j1, i2, j2;
			if ((i & 1) == 0) {
				if ((i & 2) == 0) { i1 = -1; j1 = 0; } else { i1 = 1; j1 = 0; }
				if ((i & 4) == 0) { i2 = 0; j2 = -1; } else { i2 = 0; j2 = 1; }
			} else {
				if ((i & 2) != 0) { i1 = 2; j1 = 1; } else { i1 = 0; j1 = 1; }
				if ((i & 4) != 0) { i2 = 1; j2 = 2; } else { i2 = 1; j2 = 0; }
			}
			LOOKUP_2D[i * 4] = new LatticePoint2D(0, 0);
			LOOKUP_2D[i * 4 + 1] = new LatticePoint2D(1, 1);
			LOOKUP_2D[i * 4 + 2] = new LatticePoint2D(i1, j1);
			LOOKUP_2D[i * 4 + 3] = new LatticePoint2D(i2, j2);
		}
		
		for (int i = 0; i < 8; i++) {
			int i1, j1, k1, i2, j2, k2;
			i1 = (i) & 1; j1 = (i >> 1) & 1; k1 = (i >> 2) & 1;
			i2 = i1 ^ 1; j2 = j1 ^ 1; k2 = k1 ^ 1;
			
			// The two points within this octant, one from each of the two cubic half-lattices.
			LatticePoint3D c0 = new LatticePoint3D(i1, j1, k1, 0);
			LatticePoint3D c1 = new LatticePoint3D(i1 + i2, j1 + j2, k1 + k2, 1);
			
			// (1, 0, 0) vs (0, 1, 1) away from octant.
			LatticePoint3D c2 = new LatticePoint3D(i1 ^ 1, j1, k1, 0);
			LatticePoint3D c3 = new LatticePoint3D(i1, j1 ^ 1, k1 ^ 1, 0);
			
			// (1, 0, 0) vs (0, 1, 1) away from octant, on second half-lattice.
			LatticePoint3D c4 = new LatticePoint3D(i1 + (i2 ^ 1), j1 + j2, k1 + k2, 1);
			LatticePoint3D c5 = new LatticePoint3D(i1 + i2, j1 + (j2 ^ 1), k1 + (k2 ^ 1), 1);
			
			// (0, 1, 0) vs (1, 0, 1) away from octant.
			LatticePoint3D c6 = new LatticePoint3D(i1, j1 ^ 1, k1, 0);
			LatticePoint3D c7 = new LatticePoint3D(i1 ^ 1, j1, k1 ^ 1, 0);
			
			// (0, 1, 0) vs (1, 0, 1) away from octant, on second half-lattice.
			LatticePoint3D c8 = new LatticePoint3D(i1 + i2, j1 + (j2 ^ 1), k1 + k2, 1);
			LatticePoint3D c9 = new LatticePoint3D(i1 + (i2 ^ 1), j1 + j2, k1 + (k2 ^ 1), 1);
			
			// (0, 0, 1) vs (1, 1, 0) away from octant.
			LatticePoint3D cA = new LatticePoint3D(i1, j1, k1 ^ 1, 0);
			LatticePoint3D cB = new LatticePoint3D(i1 ^ 1, j1 ^ 1, k1, 0);
			
			// (0, 0, 1) vs (1, 1, 0) away from octant, on second half-lattice.
			LatticePoint3D cC = new LatticePoint3D(i1 + i2, j1 + j2, k1 + (k2 ^ 1), 1);
			LatticePoint3D cD = new LatticePoint3D(i1 + (i2 ^ 1), j1 + (j2 ^ 1), k1 + k2, 1);
			
			// First two points are guaranteed.
			c0.nextOnFailure = c0.nextOnSuccess = c1;
			c1.nextOnFailure = c1.nextOnSuccess = c2;
			
			// If c2 is in range, then we know c3 and c4 are not.
			c2.nextOnFailure = c3; c2.nextOnSuccess = c5;
			c3.nextOnFailure = c4; c3.nextOnSuccess = c4;
			
			// If c4 is in range, then we know c5 is not.
			c4.nextOnFailure = c5; c4.nextOnSuccess = c6;
			c5.nextOnFailure = c5.nextOnSuccess = c6;
			
			// If c6 is in range, then we know c7 and c8 are not.
			c6.nextOnFailure = c7; c6.nextOnSuccess = c9;
			c7.nextOnFailure = c8; c7.nextOnSuccess = c8;
			
			// If c8 is in range, then we know c9 is not.
			c8.nextOnFailure = c9; c8.nextOnSuccess = cA;
			c9.nextOnFailure = c9.nextOnSuccess = cA;
			
			// If cA is in range, then we know cB and cC are not.
			cA.nextOnFailure = cB; cA.nextOnSuccess = cD;
			cB.nextOnFailure = cC; cB.nextOnSuccess = cC;
			
			// If cC is in range, then we know cD is not.
			cC.nextOnFailure = cD; cC.nextOnSuccess = null;
			cD.nextOnFailure = cD.nextOnSuccess = null;
			
			LOOKUP_3D[i] = c0;
			
		}
	}
	
	private static class LatticePoint2D {
		int xsv, ysv;
		double dx, dy;
		public LatticePoint2D(int xsv, int ysv) {
			this.xsv = xsv; this.ysv = ysv;
			double ssv = (xsv + ysv) * -0.211324865405187;
			this.dx = -xsv - ssv;
			this.dy = -ysv - ssv;
		}
	}
	
	private static class LatticePoint3D {
		public double dxr, dyr, dzr;
		public int xrv, yrv, zrv;
		LatticePoint3D nextOnFailure, nextOnSuccess;
		public LatticePoint3D(int xrv, int yrv, int zrv, int lattice) {
			this.dxr = -xrv + lattice * 0.5; this.dyr = -yrv + lattice * 0.5; this.dzr = -zrv + lattice * 0.5;
			this.xrv = xrv + lattice * 1024; this.yrv = yrv + lattice * 1024; this.zrv = zrv + lattice * 1024;
		}
	}
}