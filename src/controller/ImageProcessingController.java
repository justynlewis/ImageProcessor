package controller;

/**
 * Represents the operations for the controller for this program that allows the user
 * to load, process, and save images is various ways.
 */
public interface ImageProcessingController {

  /**
   * Runs the image processing program and handles how the user input guides the program.
   *
   * @throws IllegalStateException if transmission fails
   */
  void runProgram() throws IllegalStateException;
}
