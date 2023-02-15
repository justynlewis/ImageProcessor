package controller.commands;

import controller.ImageOperationCommand;
import model.ImageProcessingModel;

/**
 * Represents the commands a user can use to downscale an image through the controller.
 */
public class Downscale implements ImageOperationCommand {
  private int width;
  private int height;

  /**
   * Downscales the image by the given width and height percentage.
   *
   * @param width  the given width
   * @param height the given height
   */
  public Downscale(int width, int height) {
    this.width = width;
    this.height = height;
  }

  @Override
  public ImageProcessingModel executeCommand(ImageProcessingModel model) {
    return model.downscaleImage(width, height);
  }
}
