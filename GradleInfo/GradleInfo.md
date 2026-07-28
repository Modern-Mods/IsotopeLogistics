# Maven #
Mekanism v10+ is also available via [ModMaven](https://modmaven.dev/) for developers wishing to make use of our API. Big thanks to K4Unl for hosting.

Update your `build.gradle` file to include the following: 

```groovy
repositories {
    maven { url 'https://modmaven.dev/' }
}

dependencies {
    compileOnly "mekanism:Mekanism:${mekanism_version}:api"
    
    // If you want to test/use Mekanism & its modules during `runClient` invocation, use the following
    runtimeOnly fg.deobf("mekanism:Mekanism:${mekanism_version}")// Mekanism
    runtimeOnly fg.deobf("mekanism:Mekanism:${mekanism_version}:additions")// Mekanism: Additions
    runtimeOnly fg.deobf("mekanism:Mekanism:${mekanism_version}:generators")// Mekanism: Generators
    runtimeOnly fg.deobf("mekanism:Mekanism:${mekanism_version}:tools")// Mekanism: Tools
}
```

Add the following to your `gradle.properties` file (see [Maven](https://modmaven.dev/mekanism/Mekanism/) for the list of available versions):

```properties
mekanism_version=1.21.1-10.7.15.80
```