#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.acceptancetests;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(glue = { "${package}" }, features = "src/test/resources/features", plugin = { "json:target/cucumber-reports/cucumber.json" }, tags = { "@dev,@wip" })
public class DevTests {
}
