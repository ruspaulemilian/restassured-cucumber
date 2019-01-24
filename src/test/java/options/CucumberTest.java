package options;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        format = {"pretty", "json:target/cucumber.json"},
        glue = "stepdefs",
        tags = "~@Ignore",
        strict = true,
        features = "src/test/features"
)

public class CucumberTest {
}
