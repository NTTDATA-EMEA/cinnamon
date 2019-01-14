package com.acme.samples.acceptancetests;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(glue = { "cucumber.runtime.junit", "io.jmcore", "com.acme" }, features = "src/test/resources/features",
        plugin = {"pretty", "json:target/cucumber-reports/cucumber.json"}, tags = { "@complete,@accepted" })
public class AllTests {
}
