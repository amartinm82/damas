package amartinm.draughts.controllers;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        CancelControllerTest.class,
        ControllerTest.class,
        LogicTest.class,
        MoveControllerTest.class,
        PlayControllerTest.class,
        ResumeControllerTest.class,
        StartControllerTest.class
})
public class AllControllersTest {
}
