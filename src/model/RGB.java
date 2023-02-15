package model;

import java.util.ArrayList;
import java.util.Objects;

import static model.ImageGrid.MAX_VALUE;

/**
 * Represents the available operations to be done on a pixel of an image represented
 * in the RGB representation with 8 bits used per channel.
 * Invariants: Red, green, and blue are integers between 0 and 255.
 * Those are invariants because they only accurately represent the class
 * when they meet the condition. Package-private methods are intended to be package-private.
 */
public class RGB {
  private int red;
  private int green;
  private int blue;


  /**
   * Constructs this pixel as an RGB representation with 8 bits per channel.
   *
   * @param red   the value of the red in this pixel's RGB.
   * @param green the value of the green in this pixel's RGB.
   * @param blue  the value of the blue in this pixel's RGB.
   */
  public RGB(int red, int green, int blue) {
    Integer[] newColors = new Integer[]{red, green, blue};

    for (int color = 0; color < newColors.length; color++) {
      if (newColors[color] < 0) {
        newColors[color] = 0;
      } else if (newColors[color] > MAX_VALUE) {
        newColors[color] = MAX_VALUE;
      }
    }
    this.red = newColors[0];
    this.green = newColors[1];
    this.blue = newColors[2];


  }

  /**
   * Returns whether the given object has the same red, green, and blue fields as this pixel.
   *
   * @param other the object this pixel is being compared with.
   * @return true if this pixel and the given object have the same fields, and false otherwise.
   */
  @Override
  public boolean equals(Object other) {
    if (!(other instanceof RGB)) {
      return false;
    }
    RGB that = (RGB) other;
    return that.getRed() == this.red && that.getGreen() == this.green
            && that.getBlue() == this.blue;
  }

  /**
   * Creates a unique hashcode for each pixel based on its red, green, and blue field values.
   *
   * @return the number representing this pixel's hashcode.
   */
  @Override
  public int hashCode() {
    return Objects.hash(red, green, blue);
  }


  /**
   * Gets the red component of this pixel.
   *
   * @return the number representing the red component of this pixel.
   */
  public int getRed() {
    return this.red;
  }

  /**
   * Gets the green component of this pixel.
   *
   * @return the number representing the green component of this pixel.
   */
  public int getGreen() {
    return this.green;
  }

  /**
   * Gets the blue component of this pixel.
   *
   * @return the number representing the blue component of this pixel.
   */
  public int getBlue() {
    return this.blue;
  }


  /**
   * Creates a new pixel by adding/subtracting a positive constant to each field in this pixel
   * to brighten/darken it. If adding the constant makes any field greater than the given maximum
   * value of this image, that field will be set to the maximum value. If subtracting the constant
   * makes any field less than 0, that field will be set to 0.
   *
   * @param val the given constant to add to each field in this pixel.
   */
  RGB brightness(int val) {
    return new RGB(red + val, green + val, blue + val);
  }

  /**
   * Creates a new pixel with the same number across all its fields based on the given channel or
   * component. If the component is a field of this pixel, all the fields of the new pixel will
   * be set to the component's field. If the component is value, intensity, or luma, the number
   * will be calculated based on the fields of this class. If the number is greater than the
   * maximum value of this image, that number will be set to the maximum value. If the number is
   * less than 0, that number will be set to 0.
   *
   * @param component the individual channel or component that informs the calculation of the value
   *                  of the fields of this pixel.
   * @throws IllegalArgumentException if the given component is not one of the components or
   *                                  channels represented by this pixel.
   */
  RGB setComponentSame(String component) throws IllegalArgumentException {
    int num;
    switch (component) {
      case "value-component": {
        num = Math.max(Math.max(red, green), blue);
        break;
      }
      case "intensity-component": {
        num = (red + green + blue) / 3;
        break;
      }
      case "luma-component": {
        double numD = ((0.2126 * red) + (0.7152 * green) + (0.0722 * blue));
        num = (int) numD;
        break;
      }
      case "red-component": {
        num = red;
        break;
      }
      case "green-component": {
        num = green;
        break;
      }
      case "blue-component": {
        num = blue;
        break;
      }
      default:
        throw new IllegalArgumentException("invalid component name");
    }
    return new RGB(num, num, num);
  }

  /**
   * Creates a new pixel with the operations on the given kernel applied to this pixel.
   * If applying the kernel makes any field greater than the given maximum value of this image,
   * that field will be set to the maximum value. If applying the kernel makes any field less than
   * 0, that field will be set to 0.
   *
   * @param positions the kernel to apply operations on.
   */
  RGB returnFilterValues(RGB[][] positions) {
    ArrayList<Integer> neighborReds = new ArrayList<>();
    ArrayList<Integer> neighborGreens = new ArrayList<>();
    ArrayList<Integer> neighborBlues = new ArrayList<>();
    int redValue = 0;
    int greenValue = 0;
    int blueValue = 0;


    double[][] kernel;
    if (positions.length == 3) {
      kernel = new double[][]{
              {1 / 16d, 1 / 8d, 1 / 16d},
              {1 / 8d, 1 / 4d, 1 / 8d},
              {1 / 16d, 1 / 8d, 1 / 16d}};

    } else {
      kernel = new double[][]{
              {-1 / 8d, -1 / 8d, -1 / 8d, -1 / 8d, -1 / 8d},
              {-1 / 8d, 1 / 4d, 1 / 4d, 1 / 4d, -1 / 8d},
              {-1 / 8d, 1 / 4d, 1d, 1 / 4d, -1 / 8d},
              {-1 / 8d, 1 / 4d, 1 / 4d, 1 / 4d, -1 / 8d},
              {-1 / 8d, -1 / 8d, -1 / 8d, -1 / 8d, -1 / 8d}};
    }

    for (int h = 0; h < positions.length; h++) {
      for (int w = 0; w < positions.length; w++) {
        try {
          neighborReds.add((int) (positions[h][w].getRed() * kernel[h][w]));
          neighborGreens.add((int) (positions[h][w].getGreen() * kernel[h][w]));
          neighborBlues.add((int) (positions[h][w].getBlue() * kernel[h][w]));
        } catch (NullPointerException ignored) {
          // no intended effect
        }
      }
    }

    for (int value : neighborReds) {
      try {
        redValue += value;
      } catch (NullPointerException ignored) {
        // no intended effect
      }

    }
    for (int value : neighborGreens) {
      try {
        greenValue += value;
      } catch (NullPointerException ignored) {
        // no intended effect
      }
    }
    for (int value : neighborBlues) {
      try {
        blueValue += value;
      } catch (NullPointerException ignored) {
        // no intended effect
      }
    }

    return new RGB(redValue, greenValue, blueValue);
  }

  /**
   * Creates a new pixel with the given color transformation applied to this pixel by multiplying
   * this pixel by a matrix. If applying the transformation makes any field greater than the given
   * maximum value of this image, that field will be set to the maximum value. If applying the
   * transformation makes any field less than 0, that field will be set to 0.
   *
   * @param transformationType the type of color-transformation to be applied on this pixel.
   * @throws IllegalArgumentException if the given transformation type is not supported.
   */
  RGB setComponentColor(String transformationType)
          throws IllegalArgumentException {
    Double[][] matrix;

    if ("sepia".equals(transformationType)) {
      matrix = new Double[][]{{0.393, 0.769, 0.189},
          {0.349, 0.686, 0.168}, {0.272, 0.534, 0.131}};
    } else {
      throw new IllegalArgumentException("invalid component name");
    }

    Integer[] newColors = new Integer[]{red, green, blue};
    Integer[] currColors = new Integer[]{red, green, blue};
    double currC = 0;
    for (int r = 0; r < matrix.length; r++) {
      for (int c = 0; c < matrix[0].length; c++) {
        currC += currColors[c] * matrix[r][c];
      }
      newColors[r] = (int) currC;
      currC = 0;
    }

    red = newColors[0];
    green = newColors[1];
    blue = newColors[2];

    return new RGB(red, green, blue);
  }
}