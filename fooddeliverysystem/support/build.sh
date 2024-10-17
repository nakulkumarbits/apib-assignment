docker stop mysqlfooddelivery
docker rm mysqlfooddelivery
docker run -p 3306:3306 --name mysqlfooddelivery -e MYSQL_PASSWORD=sa -e MYSQL_USER=sa -e MYSQL_ROOT_PASSWORD=sa -e MYSQL_DATABASE=fooddeliverydb  mysql:8.3.0&

# Waits 10 seconds.
sleep 10

cd ..
mvn clean install