## Kafka IFTTT Adapter

Kafka IFTTT Adapter acts as a middleware between IFTTT WebHooks and Kafka cluster.

If This Then That, also known as IFTTT, is a free web-based service to create chains of simple conditional statements, called applets.
 An applet is triggered by changes that occur within other web services such as Gmail, Facebook, Telegram, Instagram, or Pinterest.
 
Any service can be chosen for the `This` part as long as the `That` part will be used a WebHook Requests. 

### Running the application

The application can run in any IDE as long it has the `Lombok` plugin installed.

To run the application from the command line:

```bash
  mvn spring-boot:run
```

### Webhooks form

#### The URL
 
The application url + `/produce` 

 e.g: http://kafka.ifttt.adapter:8080/produce
#### Method: POST

#### Content Type: application/json

#### Body:

By convention `service` field should represent the service that triggered the action while the message can contain anything that user consider useful.
```json
{ 
 "service": "Google Assistant",
 "message" : "{{TextField}}"
}
```


### Cluster Setup

The application currently supports `PLAINTEXT` and `SSL` connections with Apache Kafka. 


```yaml
cluster:
    bootstrapServers: "broker.local:9092"
    securityProtocol: "PLAINTEXT"
```

#### Topic Config

The topic will be created automatically if `cluster.autoCreateTopic` is set to `true`. Keystore certificate
must have permissions to create topics.

```yaml
cluster:
  autoCreateTopic: true
  topic:
    minIsr: "1"
    partitions: "2"
    replicationFactor: "1"
    retentionMs: "3600000"
    segmentMs: "86400000"
    ifttt:
      name: ifttt
    notifications:
      name: notifications
    gitlab:
      name: pipeline
```

#### SSL Config

```yaml
cluster:
  ssl:
    key-store-location: "/path/to/keystore.jks"
    key-password: verySecretPassword
    key-store-password: verySecretPassword
    trust-store-location: "/path/to/truststore.jks"
    trust-store-password: verySecretPassword
```
#### Producer Config

```yaml
cluster:
  bootstrapServers: "broker.local:9092"
  producer:
    clientId: "ifttt-adapter-producer"
    securityProtocol: "PLAINTEXT"
```

#### Consumer Config

```yaml
cluster:
  bootstrapServers: "broker.local:9092"
  consumer:
    clientId: "ifttt-adapter-consumer"
    group-id: "io.axual.app.adapter.ifttt"
    securityProtocol: "SSL"
```

#### Prerequisites for running the application
* Java 11+
* Maven 3
