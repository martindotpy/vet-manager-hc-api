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

Run the following command to install and start the embedded Wildfly server for
production mode:

```bash
./mvnw clean install -Pproduction && ./config-server admin admin <database_connection_url> <database_user_name> <database_password> && ./mvnw wildfly:run
```

Or run the following commad for development mode:

```bash
./mvnw clean install -Pdevelopment && ./config-server admin admin <database_connection_url> <database_user_name> <database_password> && ./mvnw wildfly:run
```

After run `config-server` script, you can start the Wildfly server using the
following command:

```bash
./mvnw wildfly:run
```

> [!NOTE]
>
> It is important to run the `config-server` script before starting the Wildfly
> server. This script will create the necessary configuration files for the
> application to run.

# Docs

All the endpoints are documented using Swagger. To access the documentation, run
the application at local and visit the following
[link](http://localhost:8080/docs).
