package controller.commands;

import controller.ImageOperationCommand;
import model.ImageProcessingModel;

/**
 * Represents the commands a user can use to darken an image through the controller.
 */
public class Darken implements ImageOperationCommand {
  private final int increment;

  /**
   * Gets the amount by which to darken the image.
   *
   * @param increment the value by which to darken the image.
   * @throws IllegalArgumentException if the given increment is a negative number.
   */
  public Darken(int increment) {

    if (increment < 0) {
      throw new IllegalArgumentException();
    }
    this.increment = increment;

  }

  @Override
  public ImageProcessingModel executeCommand(ImageProcessingModel model) {
    return model.brighten(-this.increment);
  }
}