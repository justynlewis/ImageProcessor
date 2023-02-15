package model;


/**
 * Represents the PPM Image model with a RGB 8-bit representation for the pixels
 * in this image's pixel grid and a maximum value of 255 for any pixel.
 */
public class ImageGrid implements ImageProcessingModel {

  private final RGB[][] pixelGrid;
  private final int imageWidth;
  private final int imageHeight;
  public final static int MAX_VALUE = 255;


  /**
   * Creates the PPM Image model given the grid of pixels in RGB representation for this image.
   *
   * @param pixelGrid the grid of pixels representing this image in RGB color representation.
   * @throws IllegalArgumentException if the given array is null.
   */
  public ImageGrid(RGB[][] pixelGrid) throws IllegalArgumentException {
    if (pixelGrid == null) {
      throw new IllegalArgumentException();
    }

    this.pixelGrid = pixelGrid;
    this.imageHeight = pixelGrid.length;
    this.imageWidth = pixelGrid[0].length;

  }


  private ImageGrid applyOperation(PixelOperation operation) {
    RGB[][] newGrid = this.getGrid();
    for (int h = 0; h < imageHeight; h++) {
      for (int w = 0; w < imageWidth; w++) {
        operation.apply(newGrid, h, w);
      }
    }
    return new ImageGrid(newGrid);
  }

  @Override
  public ImageGrid brighten(int value) {
    return applyOperation((grid, h, w) -> grid[h][w] = grid[h][w].brightness(value));
  }

  @Override
  public ImageGrid flipVertical() {
    return applyOperation((grid, h, w) -> grid[h][w] = pixelGrid[imageHeight - 1 - h][w]);
  }

  @Override
  public ImageGrid flipHorizontal() {
    return applyOperation((grid, h, w) -> grid[h][w] = pixelGrid[h][imageWidth - 1 - w]);
  }

  /**
   * Creates a new image by converting this image to greyscale based on the specified
   * individual channel or component given to be visualized (red, green, blue,
   * value, luma, or intensity).
   *
   * @param component the individual channel or component to be visualized in the new image.
   * @return a new version of this image converted to greyscale based on the given
   *     channel or component.
   * @throws IllegalArgumentException if the given component is not one of the components or
   *                                  channels represented by this image.
   */
  @Override
  public ImageGrid channelComponent(String component) throws IllegalArgumentException {
    return applyOperation((grid, h, w) -> grid[h][w] = grid[h][w].setComponentSame(component));
  }


  /**
   * Creates a new image by applying a filter to either blur or sharpen it.
   *
   * @param filterType the type of filter to be applied.
   * @return a new version of this image with the applied filter.
   */
  @Override
  public ImageProcessingModel filter(String filterType) throws IllegalArgumentException {
    RGB[][] newGrid = this.getGrid();
    RGB[][] listOfFilteredRGBs = new RGB[newGrid.length][newGrid[0].length];

    for (int h = 0; h < imageHeight; h++) {
      for (int w = 0; w < imageWidth; w++) {

        RGB[][] kernelPosition;

        if (filterType.equals("blur")) {
          kernelPosition = new RGB[3][3];
        } else if (filterType.equals("sharpen")) {
          kernelPosition = new RGB[5][5];
        } else {
          throw new IllegalArgumentException("invalid filter type");
        }

        for (int kernelHeight = 0; kernelHeight < kernelPosition.length; kernelHeight++) {
          for (int kernelWidth = 0; kernelWidth < kernelPosition[0].length; kernelWidth++) {
            try {
              kernelPosition[kernelHeight][kernelWidth] =
                      newGrid[h + (kernelHeight - kernelPosition.length / 2)]
                              [w + (kernelWidth - kernelPosition.length / 2)];
            } catch (IndexOutOfBoundsException e) {
              kernelPosition[kernelHeight][kernelWidth] = null;
            }
          }
        }

        listOfFilteredRGBs[h][w] = newGrid[h][w].returnFilterValues(kernelPosition);

      }
    }


    return new ImageGrid(listOfFilteredRGBs);
  }

  /**
   * Creates a new image by applying a color transformation to make it either a greyscale image
   * or a sepia-toned image.
   *
   * @param transformationType the type of color image to be created.
   * @return a new version of this image with the applied color transformation.
   */
  @Override
  public ImageGrid colorTransformation(String transformationType)
          throws IllegalArgumentException {
    if (transformationType.equals("greyscale")) {
      return this.channelComponent("luma-component");
    } else {
      return applyOperation((grid, h, w) ->
              grid[h][w] = grid[h][w].setComponentColor(transformationType));
    }
  }

  /**
   * Creates a new image by downscaling, or shrinking, the image by the given width and height
   * percentages.
   * @param widthPercentage  the given width percentage
   * @param heightPercentage the given height percentage
   * @return a new image with the applied downscale change
   */
  public ImageGrid downscaleImage(int widthPercentage, int heightPercentage) {
    RGB[][] currGrid = this.getGrid();
    int newHeight = (int) (imageHeight * (heightPercentage / 100.0));
    int newWidth = (int) (imageWidth * (widthPercentage / 100.0));
    RGB[][] newGrid = new RGB[newHeight][newWidth];

    for (int h = 0; h < newHeight; h++) {
      for (int w = 0; w < newWidth; w++) {


        double currH = (h + 0.0) / imageHeight * newHeight;
        double currW = (w + 0.0) / imageWidth * newWidth;
        int flW = (int) Math.floor(currW);
        int cW = (int) Math.floor(currW) + 1;
        int flH = (int) Math.floor(currH);
        int cH = (int) Math.floor(currH) + 1;
        System.out.println("" + flW + " " + cW + " " + flH + " " + cH);

        if (flH >= imageHeight) {
          flH = imageHeight - 1;
        }
        if (cH >= imageHeight) {
          cH = imageHeight - 1;
        }
        if (flW >= imageWidth) {
          flW = imageWidth - 1;
        }
        if (cW >= imageWidth) {
          cW = imageWidth - 1;
        }
        RGB a = currGrid[flH][flW];
        RGB b = currGrid[cH][flW];
        RGB c = currGrid[flH][cW];
        RGB d = currGrid[cH][cW];

        if (currH == cH) {
          cH += 1;
        }
        if (currW == cW) {
          cW += 1;
        }

        double m = b.getRed() * (currH - flH) + a.getRed() * (cH - currH);
        double n = d.getRed() * (currH - flH) + c.getRed() * (cH - currH);
        int p = (int) (n * (currW - flW) + m * (cW - currW));
        double mg = b.getGreen() * (currH - flH) + a.getGreen() * (cH - currH);
        double ng = d.getGreen() * (currH - flH) + c.getGreen() * (cH - currH);
        int pg = (int) (ng * (currW - flW) + mg * (cW - currW));
        double mb = b.getBlue() * (currH - flH) + a.getBlue() * (cH - currH);
        double nb = d.getBlue() * (currH - flH) + c.getBlue() * (cH - currH);
        int pb = (int) (nb * (currW - flW) + mb * (cW - currW));


        newGrid[h][w] = new RGB(p, pg, pb);
      }
    }
    return new ImageGrid(newGrid);
  }


  /**
   * Makes a copy of the pixel grid representing this image.
   *
   * @return a copy of this image's pixel grid.
   */
  public RGB[][] getGrid() {
    RGB[][] ret = new RGB[imageHeight][imageWidth];
    for (int h = 0; h < imageHeight; h++) {
      for (int w = 0; w < imageWidth; w++) {
        ret[h][w] = new RGB(pixelGrid[h][w].getRed(),
                pixelGrid[h][w].getGreen(),
                pixelGrid[h][w].getBlue());
      }
    }
    return ret;
  }

  public int getImageWidth() {
    return imageWidth;
  }

  public int getImageHeight() {
    return imageHeight;
  }

  public int getMaxValue() {
    return MAX_VALUE;
  }
}
