# AsyncLoggerComponent

> An asynchronous logger compenent with Disruptor.

[![Build Status](https://travis-ci.org/mstao/asynchronous-logger-component.svg?branch=master)](https://travis-ci.org/mstao/asynchronous-logger-component) 
[![Join the chat at https://gitter.im/asynchronous-logger-component/Lobby](https://badges.gitter.im/asynchronous-logger-component/Lobby.svg)](https://gitter.im/asynchronous-logger-component/Lobby?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

## Features

- Java SPI for plugins, including LogExport configuration(console, file, logstash and etc)
- Support Disruptor, privodes perfect performance
- Thinking...

## Getting Started

### Requirements

[Java `1.8`](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) and above.

### How to use?

```
// 1. Start the context of asynclogger
AsyncLoggerContext.start();
// 2. Get asynclogger
AsyncLogger asyncLogger = AsyncLoggerContext.getAsyncLogger();
// 3. Record messages
for (int i = 0; i < 2; i++) {
    Message message = new Message();
    message.setServiceName("aa " + i);
    asyncLogger.logMessage(message);
}
// 4. Close the context of asynclogger
AsyncLoggerContext.stop();
```

### Documents

See [me](http://mingshan.me/asynchronous-logger-component/)

### Compiling

- [Java `1.8`](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) and above.<br>
- Maven 3.x

```
mvn clean package -DskipTests
```

## License

Apache License 2.0
