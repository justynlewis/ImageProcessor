package controller;

import model.ImageProcessingModel;

/**
 * Represents the commands a user can use to modify an image through the controller.
 */
public interface ImageOperationCommand {
  /**
   * Allows the user to modify the given image.
   *
   * @param model the given image to be modified.
   * @return the modified image.
   */
  ImageProcessingModel executeCommand(ImageProcessingModel model);
}
