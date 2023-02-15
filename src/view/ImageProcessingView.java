package view;

import java.io.IOException;

/**
 * Represents the operations that should be offered by views to display the available commands
 * for a user when they run the program, and to display any errors that arise
 * while using the program.
 */
public interface ImageProcessingView {
  /**
   * Renders the given message to the provided data destination.
   *
   * @param message the message to be transmitted.
   * @throws IOException if transmission of the message to the data destination fails.
   */
  void renderMessage(String message) throws IOException;
}
