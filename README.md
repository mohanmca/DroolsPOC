"# DroolsPOC" 

## If we try to use arguments
```txt
[ERROR] Failed to execute goal org.codehaus.mojo:exec-maven-plugin:1.6.0:java (default-cli) on project DroolsPOC: Unable to parse configuration of mojo org.codehaus.mojo:exec-maven-plugin:1.6.0:java for parameter arguments: Cannot store value into array: ArrayStoreException -> [Help 1]
[ERROR]
                    <arguments>
                        <argument>-classpath</argument>
                        <classpath/>
                    </arguments>
```

## How to execute TestApp without execution test-classes
1. mvn compile test exec:java -DskipTests -Dlogback.debug -Dlog4j.debug

## How to run ant plugins
mvn compile -DWORKSPACE=.