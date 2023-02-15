import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Represents the test suite for this program.
 */
@RunWith(Suite.class)

@Suite.SuiteClasses(
        {
                RGBTest.class,
                ImageGridTest.class,
                ControllerTest.class,
                GUIControllerTest.class,
                ImageProcessingViewTest.class
        }
)

public class TestSuite {
}