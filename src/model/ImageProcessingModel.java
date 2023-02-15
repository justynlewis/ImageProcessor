package model;

/**
 * Represents the available operations to be done on images.
 * One object of the model represents one image.
 */
public interface ImageProcessingModel {

  /**
   * Creates a new image by adding/subtracting a positive constant to the color representation
   * of each pixel of this image to brighten/darken it. If adding the constant makes a color
   * representation value greater than the maximum value, the color representation will be set to
   * the maximum value. If subtracting the constant makes the color representation value less than
   * 0, the color representation will be set to 0.
   *
   * @param value the amount to increase/decrease this image's brightness by.
   * @return a new version of this image with its brightness changed by the value given.
   */
  ImageProcessingModel brighten(int value);

  /**
   * Creates a new image by flipping this image vertically.
   *
   * @return a new version of this image flipped over the x-axis.
   */
  ImageProcessingModel flipVertical();

  /**
   * Creates a new image by flipping this image horizontally.
   *
   * @return a new version of this image flipped over the y-axis.
   */
  ImageProcessingModel flipHorizontal();

  /**
   * Creates a new image by converting this image to greyscale based on the specified
   * individual channel or component given to be visualized.
   *
   * @param component the individual channel or component to be visualized in the new image.
   * @return a new version of this image converted to greyscale based on the given
   *         channel or component.
   * @throws IllegalArgumentException if the given component is not one of the components or
   *                                  channels represented by this image.
   */
  ImageProcessingModel channelComponent(String component) throws IllegalArgumentException;

  /**
   * Creates a new image by applying the given filter type to change pixels in this image depending
   * on the values of their neighbors.
   *
   * @param filterType the type of filter to be applied.
   * @return a new version of this image with the applied filter.
   */
  ImageProcessingModel filter(String filterType);

  /**
   * Creates a new image by applying the given color transformation to modify the color of this
   * image's pixels based on their current colors.
   *
   * @param transformationType the type of color image to be created.
   * @return a new version of this image with the applied color transformation.
   */
  ImageProcessingModel colorTransformation(String transformationType);

  /**
   * Creates a new image by downscaling the image's size based on the given width and height
   * percentages.
   *
   * @param widthPercentage  the given width percentage
   * @param heightPercentage the given height percentage
   * @return a new version of this image with the applied downscale change
   */
  ImageProcessingModel downscaleImage(int widthPercentage, int heightPercentage);

  /**
   * Makes a copy of the pixel grid representing this image.
   *
   * @return a copy of this image's pixel grid.
   */
  RGB[][] getGrid();

  /**
   * Gets the width of this image.
   *
   * @return the number representing the width of this image.
   */
  int getImageWidth();

  /**
   * Gets the height of this image.
   *
   * @return the number representing the height of this image.
   */
  int getImageHeight();

  /**
   * Gets the maximum value of a color in this image.
   *
   * @return the number representing the maximum value of a color in this image.
   */
  int getMaxValue();
}
