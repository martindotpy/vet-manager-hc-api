# Vet Manager HC Api

This is the RESTful API for the application Vet Manager HC.

## Technologies

- Java
- Wildfly
- Hibernate
- MySQL
- Swagger
- JWT

## How to run

> [!CAUTION]
>
> If you want to deploy the application in a production environment, you need to
> remove the `hibernate.hbm2ddl.auto` property from the
> [persistence.xml](src/main/resources/META-INF/persistence.xml) to avoid
> database schema changes or data loss.

Run the following command to install and start the embedded Wildfly server for
production mode:

```bash
./mvnw clean install -Pproduction && ./config-server admin admin <database-connection-url> <database-user-name> <database-password> && ./mvnw wildfly:run
```

Or run the following commad for development mode:

```bash
./mvnw clean install -Pdevelopment && ./config-server admin admin <database-connection-url> <database-username> <database-password> && ./mvnw wildfly:dev
```

> [!TIP]
>
> The first and second arguments of the `config-server` script are the username
> and password of the admin user for Wildfly Management Console.
>
> The `database-connection-url` is the URL to connect to the MySQL database.
> Example: `jdbc:mysql://<database-host>:<database-port>/<database-name>`.

After run `config-server` script, you can start the Wildfly server using the
following command for production mode:

```bash
./mvnw wildfly:run
```

Or the following command for development mode:

```bash
./mvnw wildfly:dev
```

> [!NOTE]
>
> It is important to run the `config-server` script before starting the Wildfly
> server. This script will create the necessary configuration files for the
> application to run.

## Security

The application uses JWT for authentication. To access the endpoints, you need
to provide a valid token in the `Authorization` header. The token is generated
by the `/auth/login` or `/auth/register` endpoints.

All the public endpoints are defined in
[application.properties](src/main/resources/application.properties).

## Docs

All the endpoints are documented using Swagger. To access the documentation, run
the application at local and visit the following
[link](http://localhost:8080/docs).

## Next steps

The whole application will be migrated to a monolithic architecture using Spring
Boot :D.
