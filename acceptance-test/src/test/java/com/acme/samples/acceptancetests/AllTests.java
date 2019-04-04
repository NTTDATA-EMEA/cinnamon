package com.acme.samples.acceptancetests;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(glue = { "com.acme.samples.local.stepdef", "com.acme.samples.google.stepdef" }, features = "src/test/resources/features", plugin = {
        "progress", "json:target/cucumber-reports/cucumber.json" }, tags = { "@complete,@accepted" })
public class AllTests {
}
