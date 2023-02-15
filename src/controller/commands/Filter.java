package controller.commands;

import controller.ImageOperationCommand;
import model.ImageProcessingModel;

/**
 * Represents the commands a user can use to apply a filter to their image through the controller.
 */
public class Filter implements ImageOperationCommand {

  private final String operation;

  /**
   * Gets the filter operation that the user wants to apply.
   *
   * @param operation the filter operation.
   */
  public Filter(String operation) {
    this.operation = operation;
  }

  @Override
  public ImageProcessingModel executeCommand(ImageProcessingModel model) {
    return model.filter(this.operation);
  }

}
