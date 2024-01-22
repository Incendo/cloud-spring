<div align="center">
<img src="https://github.com/Incendo/cloud/raw/master/img/CloudNew.png" width="300px"/>
<br/>
<h1>cloud-spring</h1>

![license](https://img.shields.io/github/license/incendo/cloud.svg)
[![central](https://img.shields.io/maven-central/v/org.incendo/cloud-spring)](https://search.maven.org/search?q=org.incendo)
![build](https://img.shields.io/github/actions/workflow/status/incendo/cloud-spring/build.yml?logo=github)
[![docs](https://img.shields.io/readthedocs/incendocloud?logo=readthedocs)](https://cloud.incendo.org)
</div>

This is an opinionated implementation of [Cloud](https://github.com/incendo/cloud) for 
[Spring Shell](https://spring.io/projects/spring-shell).
Cloud for Spring maps to a quite small subset of the Spring Shell features and does not aim to be a replacement
for working with spring-shell.
Rather, it's a way to use a familiar command framework to quickly & easily create commands for your Spring application.

Spring Shell uses "options" (what Cloud would call flags) for input, which Cloud does not do.
We therefore map all arguments to an array of strings, which means that we get access to (nearly) all Cloud features.

The example module contains a Spring Boot application with a couple of commands.

## features

- auto-discovery of `CommandBean` instances as well as `@ScanCommands`-annotated classes
- supports both interactive & non-interactive (CLI) commands

![cli](img/cli.png)

- support for Spring Shell features such as descriptions and command groups

![descriptions](img/descriptions.png)
![help](img/help.png)

- configurable by overriding the bean bindings
- completions!

![completions](img/completions.png)

- support for native builds

![native](img/native.png)

## limitations

- no intermediate executors (you can do `/cat add` and `/cat remove` but not `/cat`)

## links

- Docs: https://cloud.incendo.org/spring/
- Incendo Discord: https://discord.gg/aykZu32
