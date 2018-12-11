# Magpie

> Magpie - The bridge of data connection..

[![Build Status](https://travis-ci.org/mstao/Magpie.svg?branch=master)](https://travis-ci.org/mstao/Magpie) [![FOSSA Status](https://app.fossa.io/api/projects/git%2Bgithub.com%2Fmstao%2FMagpie.svg?type=shield)](https://app.fossa.io/projects/git%2Bgithub.com%2Fmstao%2FMagpie?ref=badge_shield) [![Join the chat at https://gitter.im/asynchronous-logger-component/Lobby](https://badges.gitter.im/asynchronous-logger-component/Lobby.svg)](https://gitter.im/asynchronous-logger-component/Lobby?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

## Features

- Java SPI for plugins, including data export configuration(console, file, logstash and etc)
- Support various data sources(File, Http, Socket[TCP/UDP], MySql, AuditLog, Redis, MongoDB and etc)
- Support Disruptor, privodes perfect performance
- Thinking...

## Getting Started

### Requirements

[Java `1.8`](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) and above.

### How to use?

```
// 1. Start the Magpie and Record messages
for (int i = 0; i < 20; i++) {
    Message message = new Message();
    message.setServiceName("aa " + i);
    Magpie.start().read(message);
}
// 2. Close the Magpie
Magpie.stop();
```

### Documents

See [me](https://mingshan.fun/Magpie/)

### Compiling

- [Java `1.8`](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) and above.<br>
- Maven 3.x

```
mvn clean package -DskipTests
```

## License

[![FOSSA Status](https://app.fossa.io/api/projects/git%2Bgithub.com%2Fmstao%2FMagpie.svg?type=large)](https://app.fossa.io/projects/git%2Bgithub.com%2Fmstao%2FMagpie?ref=badge_large)
