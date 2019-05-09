# Cinnamon

[![Gitter](https://badges.gitter.im/MagenTys/cinnamon.svg)](https://gitter.im/MagenTys/cinnamon?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

## What is Cinnamon?
Cinnamon is an open-source java framework developed by the [MagenTys](http://magentys.io) team that provides fast enablement on your automation projects.
It is designed with ease-of-use in mind, supports Behaviour Driven Development (BDD), robust extensions and easy to use abstraction layers for popular
tooling such as WebDriver. You can mix and match various [modules](https://github.com/MagenTys/cinnamon/wiki/Cinnamon-modules) depending on your requirements. Let's [get started](https://github.com/MagenTys/cinnamon/wiki/Getting-started) ...

## Why Cinnamon?
As a company, we have faced many challenges with tooling across many clients in various sectors. Cinnamon incorporates our [solutions](https://github.com/MagenTys/cinnamon/wiki/What-does-it-solve%3F)
to these challenges, which means that you don’t have to invest time in solving them yourself.


## Road map

We are currently working towards:
* enhancing support for Rest
* adding standard web and rest cherry missions
* custom Hamcrest matchers

We have plans to support:
* junit
* jbehave

## Migrating from 0.1.3

* Update `cucumber-jvm-parallel-plugin` version in pom.xml to `5.0.0`, e.g.
```
<plugin>
    <groupId>com.github.temyers</groupId>
    <artifactId>cucumber-jvm-parallel-plugin</artifactId>
    <version>5.0.0</version>
    <executions>
        <execution>
            <id>generateRunners</id>
            <phase>generate-test-sources</phase>
            <goals>
                <goal>generateRunners</goal>
            </goals>
            <configuration>
                <glue>
                    <package>com.acme</package>
                </glue>
                <plugins>
                    <plugin>
                        <name>json</name>
                    </plugin>
                </plugins>
                <namingScheme>pattern</namingScheme>
                <namingPattern>{f}IT</namingPattern>
                <cucumberOutputDir>target/cucumber-reports</cucumberOutputDir>
            </configuration>
        </execution>
    </executions>
</plugin>
```
* Update `donut-maven-plugin` version in pom.xml to `1.2.1`, e.g.
```
<plugin>
    <groupId>report.donut</groupId>
    <artifactId>donut-maven-plugin</artifactId>
    <version>1.2.1</version>
    <executions>
        <execution>
            <id>execution</id>
            <phase>post-integration-test</phase>
            <goals>
                <goal>generate</goal>
            </goals>
            <configuration>
                <resultSources>
                    <resultSource>
                        <format>cucumber</format>
                        <directory>${project.build.directory}/cucumber-reports</directory>
                    </resultSource>
                </resultSources>
                <outputPath>${project.build.directory}/donut/</outputPath>
                <timestamp>${maven.build.timestamp}</timestamp>
                <template>default</template>
                <prefix>${project.name}</prefix>
                <projectName>${project.name}</projectName>
                <projectVersion>${project.version}</projectVersion>
            </configuration>
        </execution>
    </executions>
</plugin>
```
* Update `maven-failsafe-plugin` configuration to replace:
```
<forkCount>${failsafe.fork.count}</forkCount>
<reuseForks>false</reuseForks>
```

with

```
<threadCount>${failsafe.thread.count}</threadCount>
<parallel>both</parallel>
```
* Update `GuiceInjectorSource` to replace `CucumberModules.SCENARIO` with `CucumberModules.createScenarioModule()`
* Update Guice module classes to replace references to `CucumberScopes.SCENARIO` with `ScenarioScoped.class`

## Contributing

1. Fork it
2. Create your feature branch (`git checkout -b feature/my-new-feature`)
3. Commit your changes (`git commit -am 'Add some feature'`)
4. Push to the branch (`git push origin feature/my-new-feature`)
5. Create new Pull Request

## Developers
 [Dave Bassan](https://github.com/davebassan), [Tim Myerscough](https://github.com/temyers), [Christina Daskalaki](https://github.com/chdask), [Amit Sharma](https://github.com/amitsha), [Miles Lord](https://github.com/mplord), [Hamish Tedeschi](https://github.com/MagenTysHamo), [Kevin Bradwick](https://github.com/kevbradwick)

## Acknowledgments

* Thanks to [Boni Garcia](http://bonigarcia.github.io/) for [WebDriverManager](https://github.com/bonigarcia/webdrivermanager)

Powered by [MagenTys](http://magentys.io)
