package usantatecla.draughts;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import usantatecla.draughts.controllers.AllControllersTest;
import usantatecla.draughts.models.AllModelsTest;
import usantatecla.draughts.utils.AllUtilsTest;
import usantatecla.draughts.views.AllViewsTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        AllModelsTest.class,
        AllViewsTest.class,
        AllControllersTest.class,
        AllUtilsTest.class
})
public class AllTest {
}
