package view;

import javax.swing.Icon;

/**
 * Represents the operations of the GUI.
 */
public interface ImageProcessorViewGUI {
  void setErrorLabelText(String text);

  void setFileLabelText(String text);

  void setImageIcon(Icon icon);

  String getLightNum();

  String getWidthNum();

  String getHeightNum();

  void updateHistogram();
}
