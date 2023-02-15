package controller;

/**
 * Interface of available features in the GUI. Each method is called when the corresponding button
 * is clicked on in the GUI.
 */
public interface Features {
  /**
   * Loads an image and sets the center icon to the image.
   */
  void loadImage();

  /**
   * Saves the current image that is being displayed in the center icon.
   */
  void saveImage();

  /**
   * Applies the given operation to the center icon's image.
   *
   * @param method the given method
   * @param action the action command for the given method
   */
  void applyOp(String method, String action);

}
