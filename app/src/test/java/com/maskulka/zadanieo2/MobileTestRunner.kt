package com.maskulka.zadanieo2

import io.cucumber.junit.Cucumber
import io.cucumber.junit.CucumberOptions
import org.junit.runner.RunWith

@RunWith(Cucumber::class)
@CucumberOptions(
    publish = true,
    plugin = ["pretty", "html:target/cucumber.html", "json:target/cucumber-json-report.json"],
    features = ["src/test/res/features/"],
    glue = ["com.maskulka.zadanieo2"],
//    tags = "@scratch"
)
class MobileTestRunner