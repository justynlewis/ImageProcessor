import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import view.ImageProcessingView;
import view.ImageProcessingViewImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Tests the ImageProcessingViewImpl class.
 */
public class ImageProcessingViewTest {
  private ImageProcessingView viewNegOne;

  private ImageProcessingView viewZero;
  private StringBuffer outOne;
  private ImageProcessingView viewOne;
  private StringBuffer outTwo;
  private ImageProcessingView viewTwo;

  @Before
  public void setUp() {
    viewNegOne = new ImageProcessingViewMock();

    viewZero = new ImageProcessingViewMock();

    outOne = new StringBuffer();
    viewOne = new ImageProcessingViewImpl(outOne);

    outTwo = new StringBuffer();
    viewTwo = new ImageProcessingViewImpl(outTwo);
  }

  @Test
  public void testSetUpExceptionNullOutput() {
    try {
      StringBuffer outE = null;
      ImageProcessingView viewE = new ImageProcessingViewImpl(outE);
      fail("No exception");
    } catch (IllegalArgumentException e) {
      //exception thrown
    }
  }

  @Test
  public void testRenderMessage() {
    try {
      assertEquals("", outOne.toString());
      viewOne.renderMessage("test");
      assertEquals("test", outOne.toString());
      viewOne.renderMessage("\n \b  ?/ .$*@! ~");
      assertEquals("test\n \b  ?/ .$*@! ~", outOne.toString());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  public void testRenderMessageTwo() {
    try {
      assertEquals("", outTwo.toString());
      viewTwo.renderMessage("682 903");
      assertEquals("682 903", outTwo.toString());
      viewTwo.renderMessage("Hiiii" + System.lineSeparator());
      assertEquals("682 903Hiiii" + System.lineSeparator(), outTwo.toString());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  public void testRenderMessageExceptionOne() {
    try {
      viewZero.renderMessage("try");
      fail("No exception");
    } catch (IOException e) {
      //exception thrown
    }
  }

  @Test
  public void testRenderMessageExceptionTwo() {
    try {
      viewNegOne.renderMessage("?>< bmk 8#4$* ~");
      fail("No exception");
    } catch (IOException e) {
      //exception thrown
    }
  }
}
