# Vet Manager HC Api

This is the RESTful API for the application Vet Manager HC.

## Technologies

- Java
- Wildfly
- Swagger
- JPA
- MySQL

## How to run

Run the following command to install and start the embedded Wildfly server:

```bash
mvn clean install wildfly:run
```

> [!NOTE]
>
> If the following message apears in the logs:  
> `WFLYWELD0052: Using deployment classloader to load proxy classes for module com.fasterxml.jackson.jakarta.jackson-jakarta-json-provider. ...To fix this the module should declare dependencies on [org.jboss.weld.core, org.jboss.weld.spi, org.jboss.weld.api]`
>
> Add the following lines to the `module.xml` of the
> `com.fasterxml.jackson.jakarta.jackson-jakarta-json-provider` module:
>
> ```xml
> <module ... >
>   ...
>   <dependencies>
>     ...
>     <module name="org.jboss.weld.core"/>
>     <module name="org.jboss.weld.spi"/>
>     <module name="org.jboss.weld.api"/>
>   </dependencies>
> </module>
> ```
>
> This file usually is located at:
> [`target/server/modules/.../com/fasterxml/jackson/.../module.xml`](target/server/modules/system/layers/base/com/fasterxml/jackson/jakarta/jackson-jakarta-json-provider/main/module.xml)

# Docs

All the endpoints are documented using Swagger. To access the documentation, run
the application and visit the following [link](http://localhost:8080/docs).
