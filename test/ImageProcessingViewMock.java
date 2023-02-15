import java.io.IOException;

import view.ImageProcessingView;

/**
 * Used to test that IOExceptions can be thrown in the ImageProcessingView.
 */
public class ImageProcessingViewMock implements ImageProcessingView {
  /**
   * Throws an IOException when rendering the given message.
   *
   * @param message the message to be transmitted.
   * @throws IOException when this method is called.
   */
  @Override
  public void renderMessage(String message) throws IOException {
    throw new IOException();
  }
}
