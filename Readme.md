# File Organizer 

This Tool, provides functionallity to organize and classify all files under a determinate directory.

In these times we store a lot of video, images and audio files in a dissorder way in a warehouse disk, but normally is not ordered because paste into it from diferent sources (mobile, camera, pc, etc..)

You can choose how and where the distinct filetypes will be stored: group and create folder by creation date, or by date and type, etc..



## Getting Started

This project has build 

### Prerequisites

Ensure you have Java 1.8 or higher installed. (this project requires JavaFX)

### Installing

Does not require installation, only download, extract and execute it!

[Download from here (chosse)](./distributtion/)

Once downloaded extract (zip, tar or tar.gz to your file system)

Execute .sh or exe file  (ensure chmod +x FileOrganizer.jar to be and executable)

>sudo .fileOrganizer.sh
or
>fileOrganizer.exe

### Compiling

If you want to compile from source code, it is a typical Maven project:

* mvn clean install   -> Will compile ,create the original jar under /target , and create /target/jfx/app/FileOrganizer.jar (with Manifest information)
* mvn clean assembly:assembly   -> Will compile and generate distributtion packages (.tar, .tar.gz, .zip) under ./distributtion. These compressed files will contain the .sh and .exe files 
For custom executions:
* mvn clean jfx:jar , mvn clean jfx:build-native , etc..  [Official jfx plugin site](https://github.com/javafx-maven-plugin/javafx-maven-plugin) 

## Running the tests

No test has been added



## Built With

* [JAVAFX](https://www.oracle.com/technetwork/java/javase/overview/javafx-overview-2158620.html)
* [Maven](https://maven.apache.org/) - Dependency Management
* [Official jfx plugin site](https://github.com/javafx-maven-plugin/javafx-maven-plugin) 

## Contributing

Please read [CONTRIBUTING.md](https://gist.github.com/PurpleBooth/b24679402957c63ec426) for details on our code of conduct, and the process for submitting pull requests to us.

## Versioning

We use [SemVer](http://semver.org/) for versioning. For the versions available, see the [tags on this repository](https://github.com/your/project/tags). 

## Authors

* **Daniel Peña Perez** - *Initial work* - [Serpiente](https://github.com/danipenaperez)

See also the list of [contributors](https://github.com/your/project/contributors) who participated in this project.

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

## Acknowledgments

* To You for use it.
