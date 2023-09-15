import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        tags = " ",
        plugin = {"pretty"},
        features = "classpath:features",
        publish = true
)
public class CucumberRunner {
}