# account-card-api

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: <https://quarkus.io/>.

## Running the application in dev mode

Start the required services using Docker Compose:
```shell script
docker-compose up -d
```

You can run your application in dev mode that enables live coding using:

```shell script
mvn clean install
```

```shell script
java -jar target/quarkus-app/account-card-api-1.0.0-SNAPSHOT.jar
```

```shell script
./mvnw quarkus:dev
```

## Access Swagger UI: Once your application is up and running, open your browser and navigate to:
http://localhost:8080/swagger-ui/
