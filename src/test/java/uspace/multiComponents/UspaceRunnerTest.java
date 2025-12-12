package uspace.multiComponents;


import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(glue = {"uspace.multiComponents.glue"})
public class UspaceRunnerTest {
}
