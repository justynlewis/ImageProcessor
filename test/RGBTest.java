import org.junit.Before;
import org.junit.Test;

import model.RGB;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Tests the RGB class.
 */
public class RGBTest {
  private RGB red;
  private RGB green;
  private RGB blue;
  private RGB pink;

  private RGB white;
  private RGB black;
  private RGB grey;

  private RGB red2;
  private RGB red3;
  private RGB pink2;
  private RGB pink3;

  @Before
  public void setUp() {
    red = new RGB(255, 0, 0);
    green = new RGB(0, 255, 0);
    blue = new RGB(0, 0, 255);
    pink = new RGB(250, 100, 150);

    white = new RGB(255, 255, 255);
    black = new RGB(0, 0, 0);
    grey = new RGB(150, 150, 150);

    RGB white2 = new RGB(255, 255, 255);
    RGB black2 = new RGB(0, 0, 0);
    RGB grey2 = new RGB(150, 150, 150);

    red2 = new RGB(255, 0, 0);
    red3 = new RGB(254, 0, 0);
    pink2 = new RGB(250, 100, 150);
    pink3 = new RGB(250, 10, 0);

  }

  @Test
  public void testGetRed() {
    assertEquals(255, red.getRed());
    assertEquals(0, green.getRed());
    assertEquals(0, blue.getRed());
    assertEquals(250, pink.getRed());
    assertEquals(255, white.getRed());
    assertEquals(0, black.getRed());
    assertEquals(150, grey.getRed());
  }

  @Test
  public void testGetGreen() {
    assertEquals(0, red.getGreen());
    assertEquals(255, green.getGreen());
    assertEquals(0, blue.getGreen());
    assertEquals(100, pink.getGreen());
    assertEquals(255, white.getGreen());
    assertEquals(0, black.getGreen());
    assertEquals(150, grey.getGreen());
  }

  @Test
  public void testGetBlue() {
    assertEquals(0, red.getBlue());
    assertEquals(0, green.getBlue());
    assertEquals(255, blue.getBlue());
    assertEquals(150, pink.getBlue());
    assertEquals(255, white.getBlue());
    assertEquals(0, black.getBlue());
    assertEquals(150, grey.getBlue());
  }

  @Test
  public void testEquals() {
    assertEquals(red2, red);
    assertNotEquals(red3, red);

    assertEquals(pink2, pink);
    assertNotEquals(pink3, pink);
    assertNotEquals(red, pink);

  }

  @Test
  public void testHashCode() {
    assertEquals(red2.hashCode(), red.hashCode());
    assertNotEquals(red3.hashCode(), red.hashCode());

    assertEquals(pink2.hashCode(), pink.hashCode());
    assertNotEquals(pink3.hashCode(), pink.hashCode());
    assertNotEquals(red.hashCode(), pink.hashCode());
  }
}
