**Food Delivery System**
-

Pre-requisites
-
- maven - 3.6.3
- jdk - 17
- docker - to run mysql 8.3.0

Steps to run `Food Delivery System`
-
1. Run `mysql` via docker using following command: `docker run -p 3306:3306 --name mysqlfooddelivery -e MYSQL_PASSWORD=sa -e MYSQL_USER=sa -e MYSQL_ROOT_PASSWORD=sa -e MYSQL_DATABASE=fooddeliverydb  mysql:8.3.0`. First run might take time as image might be getting downloaded.
2. Build the service using `mvn clean install`. First run might take time depending on the network connection and available mirrors.
3. Run the fooddeliverysystem either through IntelliJ Idea or through commandline by navigating to the folder in which `pom.xml` is present: `mvn spring-boot:run`. The service will run on port `8081`.
4. To access database from commandline/terminal run the command: `docker exec -it mysqlfooddelivery bash` to access the container followed by `mysql -u sa fooddeliverydb -p` which will prompt to enter password.

Swagger
-
- API docs can be seen in json format at http://localhost:8081/v3/api-docs
- Swagger UI can be reached at http://localhost:8081/swagger-ui/index.html#/

Postman Collection
-
- Postman collection can be imported using `<collection_json>`


