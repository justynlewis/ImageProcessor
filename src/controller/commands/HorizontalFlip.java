package controller.commands;

import controller.ImageOperationCommand;
import model.ImageProcessingModel;

/**
 * Represents the commands a user can use to horizontally flip an image through the controller.
 */
public class HorizontalFlip implements ImageOperationCommand {
  @Override
  public ImageProcessingModel executeCommand(ImageProcessingModel model) {
    return model.flipHorizontal();
  }
}
