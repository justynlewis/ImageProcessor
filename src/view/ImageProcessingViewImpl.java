package view;

import java.io.IOException;

/**
 * Represents the operations offered by views to display the available commands for a user when
 * they run the program, and to display any errors that arise while using the program.
 */
public class ImageProcessingViewImpl implements ImageProcessingView {
  private final Appendable output;

  /**
   * Creates the view for this program at the given location.
   *
   * @param output the given destination for the view.
   */
  public ImageProcessingViewImpl(Appendable output) {
    if (output == null) {
      throw new IllegalArgumentException("provided appendable is null");
    }
    this.output = output;
  }

  public void renderMessage(String message) throws IOException {
    output.append(message);
  }
}
