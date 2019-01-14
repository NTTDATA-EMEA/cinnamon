package com.acme.samples.acceptancetests;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

/**
 * Convenience runner to execute local tests only. Useful if working offline.
 */
@RunWith(Cucumber.class)
@CucumberOptions(glue = { "io.jmcore", "com.acme" }, features = "src/test/resources/features", plugin = {"pretty", "json:target/cucumber-json-report.json" }, tags = { "@local", "@complete,@accepted" })
public class LocalTests {
}
