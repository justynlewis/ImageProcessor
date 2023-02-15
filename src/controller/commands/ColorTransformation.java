package controller.commands;

import controller.ImageOperationCommand;
import model.ImageProcessingModel;

/**
 * Represents the commands a user can use to change the colors of an image through the controller.
 */
public class ColorTransformation implements ImageOperationCommand {

  private final String operation;

  /**
   * Gets the color change operation that the user wants to apply.
   *
   * @param operation the color change operation.
   */
  public ColorTransformation(String operation) {
    this.operation = operation;
  }


  @Override
  public ImageProcessingModel executeCommand(ImageProcessingModel model) {
    return model.colorTransformation(this.operation);
  }

}
