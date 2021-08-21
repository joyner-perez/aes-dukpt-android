# Framework

A set of components and libraries for Android


Framework is a set of components and libraries to develop android applications. This has the following modules: 

* Core
* Logger


## Core 
Main componenent use to inject Android Context and others task like: 
* IO
* Utilities

### Publish
To publish new release in the Artifactory Server, execute the following command:
```
gradlew :core:assembleRelease :core:artifactoryPublish
```

### Installation
```groovy
dependencies {
  compile 'com.bayteq.android:core:1.x.x'
}
```

## Logger
This component allows you to configure an independent and configurable log system of the default Android OS.


### Publish
To publish new release in the Artifactory Server, execute the following command:
```
gradlew :logger:assembleRelease :logger:artifactoryPublish
```

## Installation
```groovy
dependencies {
  compile 'com.bayteq.android:logger:1.x.x'
}
```