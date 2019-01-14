package com.acme.samples.acceptancetests;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(glue = { "cucumber.runtime.junit", "io.jmcore", "com.acme" }, features = "src/test/resources/features",
        plugin = {"pretty", "json:target/cucumber-reports/cucumber.json"}, tags = { "@dev,@wip" })
public class DevTests {
}
