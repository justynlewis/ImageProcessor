package controller.commands;

import controller.ImageOperationCommand;
import model.ImageProcessingModel;

/**
 * Represents the commands a user can use to vertically flip an image through the controller.
 */
public class VerticalFlip implements ImageOperationCommand {

  @Override
  public ImageProcessingModel executeCommand(ImageProcessingModel model) {
    return model.flipVertical();
  }
}