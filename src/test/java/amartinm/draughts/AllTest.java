package amartinm.draughts;

import amartinm.draughts.controllers.AllControllersTest;
import amartinm.draughts.models.AllModelsTest;
import amartinm.draughts.utils.AllUtilsTest;
import amartinm.draughts.views.AllViewsTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        AllModelsTest.class,
        AllViewsTest.class,
        AllControllersTest.class,
        AllUtilsTest.class
})
public class AllTest {
}
