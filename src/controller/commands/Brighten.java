package controller.commands;

import controller.ImageOperationCommand;
import model.ImageProcessingModel;

/**
 * Represents the commands a user can use to brighten an image through the controller.
 */
public class Brighten implements ImageOperationCommand {
  private final int increment;

  /**
   * Gets the amount by which to brighten the image.
   *
   * @param increment the value by which to brighten the image.
   * @throws IllegalArgumentException if the given increment is a negative number.
   */
  public Brighten(int increment) throws IllegalArgumentException {

    if (increment < 0) {
      throw new IllegalArgumentException();
    }
    this.increment = increment;

  }

  @Override

  public ImageProcessingModel executeCommand(ImageProcessingModel model) {
    return model.brighten(this.increment);
  }
}

