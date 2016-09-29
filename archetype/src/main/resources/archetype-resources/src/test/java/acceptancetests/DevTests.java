#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.acceptancetests;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(glue = { "io.magentys.cinnamon", "${package}" }, features = "src/test/resources/features", format = { "pretty",
        "json:target/cucumber-reports/cucumber.json" }, tags = { "@dev,@wip" })
public class DevTests {
}
