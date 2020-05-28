# Cinnamon

## Table of contents
* [What is Cinnamon?](https://github.com/NTTDATA-UK/cinnamon/wiki/What-is-Cinnamon%3F)
* [Why use Cinnamon?](https://github.com/NTTDATA-UK/cinnamon/wiki/Why-use-Cinnamon%3F)
* [Getting started](https://github.com/NTTDATA-EMEA/cinnamon/wiki/Getting-Started)
   * [Prerequisites](https://github.com/NTTDATA-EMEA/cinnamon/wiki/Prerequisites)



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

Powered by [NTT DATA](https://uk.nttdata.com/)
