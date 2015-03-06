[Documentation table of contents](toc.md) / User Manual

# User Manual

## What to configure

* Queue URL: the endpoint to the queue (tipically a RabbitMQ queue)
* KB URL: the endpoint to the Knowledge Base (tipically a Fuseki datastore)
* Listener port: the port on which the listener will wait for messages.

## How to configure

The monitoring manager can be configured by means of different options (latters replaces the formers):
* Default Configuration
* Environment Variables
* System Properties
* CLI Arguments

### Default Configuration

* Queue URL: `http://localhost:5672`
* KB URL: `http://localhost:3030/ds`
* Listener port: `31337`.

### Environment Variables

```
MODACLOUDS_HDB_QUEUE_ENDPOINT_IP
MODACLOUDS_HDB_QUEUE_ENDPOINT_PORT
MODACLOUDS_HDB_KB_ENDPOINT_IP
MODACLOUDS_HDB_KB_ENDPOINT_PORT
MODACLOUDS_HDB_KB_DATASET_PATH
MODACLOUDS_HDB_LISTENER_PORT
```

where:

* Queue URL: `http://${MODACLOUDS_HDB_QUEUE_ENDPOINT_IP}:${MODACLOUDS_HDB_QUEUE_ENDPOINT_PORT}`
* KB URL: `http://${MODACLOUDS_HDB_KB_ENDPOINT_IP}:${MODACLOUDS_HDB_KB_ENDPOINT_PORT}${MODACLOUDS_HDB_KB_DATASET_PATH}`
* Listener port: `${MODACLOUDS_HDB_LISTENER_PORT}`.

### System Properties

Same names used for Environment Variables.

### CLI Arguments

Usage available by running `./historydb -help`:

```
Usage: historydb [options]
  Options:
    -queueip
       Queue endpoint IP address
       Default: 127.0.0.1
    -queueport
       Queue endpoint port
       Default: 5672
    -kbip
       KB endpoint IP address
       Default: 127.0.0.1
    -kbpath
       KB URL path
       Default: /ds
    -kbport
       KB endpoint port
       Default: 3030
    -listenerport
       Listener endpoint port
       Default: 31337
    -fakemessages
       Test the tool sending a number of fake messages at the beginning
       Default: 0
    -waitfakemessages
       The ms to wait between each fake message
       Default: 1000 (1 second)
    -help | -h | --help
       Shows this message.
```

There are two new parameters here that are useful when testing the tool: `-fakemessages` and `-waitfakemessages`. If you call the tool for having 30 fake messages,
30 random messages will be sent when the tool starts, and this is useful for testing the environment.