package model;

/**
 * A functional interface that applies different operations to an RGB.
 */
@FunctionalInterface
interface PixelOperation {
  /**
   * Applies a function to this pixel.
   *
   * @param grid the given imageGrid
   * @param h    this pixel's height
   * @param w    this pixel's width
   */
  void apply(RGB[][] grid, int h, int w);
}
