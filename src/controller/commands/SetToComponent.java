package controller.commands;

import controller.ImageOperationCommand;
import model.ImageProcessingModel;

/**
 * Represents the commands a user can use to set a component of the image through the controller.
 */
public class SetToComponent implements ImageOperationCommand {
  private final String channelComponent;

  /**
   * Gets the channel or component of the image that the user wants to modify.
   *
   * @param channelComponent the channel or component of the image.
   */
  public SetToComponent(String channelComponent) {
    this.channelComponent = channelComponent;
  }

  @Override
  public ImageProcessingModel executeCommand(ImageProcessingModel model) {
    return model.channelComponent(this.channelComponent);
  }
}
