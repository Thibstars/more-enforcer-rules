# more-enforcer-rules #
A collection of additional enforcer rules for the maven-enforcer-plugin.

## Installation ##
### maven ###
Add a plugin repository in your pom.xml.
````xml    
<pluginRepositories>
    <pluginRepository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </pluginRepository>
</pluginRepositories>
````

## Usage ##
Under the `build` tag, introduce the enforcer plugin and include the desired dependencies.
````xml
        <pluginManagement>
            <plugins>
                <plugin>
                    <groupId>org.apache.maven.plugins</groupId>
                    <artifactId>maven-enforcer-plugin</artifactId>
                    <version>3.1.0</version>
                    <dependencies>
                        <dependency>
                            <groupId>com.github.thibstars.more-enforcer-rules</groupId>
                            <artifactId>meta-rule</artifactId>
                            <version>1.0</version>
                        </dependency>
                    </dependencies>
                </plugin>
            </plugins>
        </pluginManagement>
````
Now you can add the plugin to the `plugins` tag:
````xml
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-enforcer-plugin</artifactId>
                <version>3.1.0</version>
                <executions>
                    <execution>
                        <id>enforce</id>
                        <goals>
                            <goal>enforce</goal>
                        </goals>
                    </execution>
                </executions>
                <configuration>
                    <rules>
                        <metaRule implementation="com.github.thibstars.meta.MetaRule">
                            <namePresent>true</namePresent>
                            <descriptionPresent>true</descriptionPresent>
                        </metaRule>
                    </rules>
                </configuration>
            </plugin>
````