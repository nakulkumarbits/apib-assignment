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
- Postman collection can be imported using `Food Delivery API.postman_collection.json`

Database schema 
-
Expand the below collapsible sections to check the schema.

<details>
  <summary>Address</summary>

  ```
CREATE TABLE `Address` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `addressLine1` varchar(255) DEFAULT NULL,
  `addressLine2` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `createdDate` datetime(6) DEFAULT NULL,
  `modifiedDate` datetime(6) DEFAULT NULL,
  `pinCode` varchar(255) DEFAULT NULL,
  `state` varchar(255) DEFAULT NULL,
  `version` bigint DEFAULT NULL,
  PRIMARY KEY (`id`)
);
```
  
</details>

<details>
  <summary>Administrator</summary>

```
CREATE TABLE `Administrator` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKcsfxiaqnaple91nkb0fa3p5s0` (`user_id`),
  CONSTRAINT `FK8hau117bg0amv9oi6hfvauptc` FOREIGN KEY (`user_id`) REFERENCES `User` (`id`)
);
```
</details>

<details>
  <summary>BlacklistedToken</summary>

  ```
CREATE TABLE `BlacklistedToken` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `expiration` datetime(6) DEFAULT NULL,
  `token` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
);
```
</details>  

<details>
  <summary>Customer</summary>

```
CREATE TABLE `Customer` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `firstName` varchar(255) NOT NULL,
  `lastName` varchar(255) NOT NULL,
  `mobileNo` varchar(255) NOT NULL,
  `address_id` bigint DEFAULT NULL,
  `payment_detail_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK3qgg01qojcmbdp47dkaom9x45` (`email`),
  UNIQUE KEY `UKi7f12t1rqxjnufl1k69g1887v` (`address_id`),
  UNIQUE KEY `UKlkvkcfp0vrq50v228378wp8sa` (`payment_detail_id`),
  UNIQUE KEY `UK8mymjl93593fro12fetnugjxr` (`user_id`),
  CONSTRAINT `FK6fcmqdibqefuoimvjyji0a8xu` FOREIGN KEY (`payment_detail_id`) REFERENCES `PaymentDetail` (`id`),
  CONSTRAINT `FK6y0rqloalxi6r6lpoqtfohp9i` FOREIGN KEY (`user_id`) REFERENCES `User` (`id`),
  CONSTRAINT `FKfok4ytcqy7lovuiilldbebpd9` FOREIGN KEY (`address_id`) REFERENCES `Address` (`id`)
);
```
</details>


<details>
  <summary>DeliveryPersonnel</summary>
  
```
CREATE TABLE `DeliveryPersonnel` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `vehicleType` enum('FOUR_WHEELER','TWO_WHEELER') DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  `address_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK3kg10lc6v68heiwtes2aaqjkx` (`user_id`),
  UNIQUE KEY `UKk7lt86tncb8trb16ry7if05mi` (`address_id`),
  CONSTRAINT `FKe2p5fmq53r6w8o3x0srnjhiu2` FOREIGN KEY (`user_id`) REFERENCES `User` (`id`),
  CONSTRAINT `FKl78iipn6rri5odq2sypst2xjq` FOREIGN KEY (`address_id`) REFERENCES `Address` (`id`)
);
```
</details>

<details>
  <summary>MenuItem</summary>
  
```
CREATE TABLE `MenuItem` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `createdDate` datetime(6) DEFAULT NULL,
  `cuisine` enum('CHINESE','INDIAN','ITALIAN','KOREAN','MEXICAN','MUGHLAI') DEFAULT NULL,
  `description` varchar(255) NOT NULL,
  `itemAvailable` enum('NO','YES') DEFAULT NULL,
  `itemType` enum('NON_VEG','VEG') DEFAULT NULL,
  `modifiedDate` datetime(6) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `price` double NOT NULL,
  `version` bigint DEFAULT NULL,
  `restaurant_owner_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKeuojx6ppdp8f387uj2thbssig` (`restaurant_owner_id`),
  CONSTRAINT `FKeuojx6ppdp8f387uj2thbssig` FOREIGN KEY (`restaurant_owner_id`) REFERENCES `Restaurant` (`id`)
);
```
</details>

<details>
  <summary>OrderDetail</summary>
  
```
CREATE TABLE `OrderDetail` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `createdDate` datetime(6) DEFAULT NULL,
  `modifiedDate` datetime(6) DEFAULT NULL,
  `orderStatus` enum('ACCEPTED','AWAITING_CONFIRMATION','CANCELLED','DELIVERED','IN_PREPARATION','OUT_FOR_DELIVERY','READY_FOR_DELIVERY','REJECTED') NOT NULL,
  `paymentMethod` enum('CASH','CREDIT_CARD','DEBIT_CARD','UPI') NOT NULL,
  `totalAmount` double NOT NULL,
  `version` bigint DEFAULT NULL,
  `customer_id` bigint NOT NULL,
  `payment_id` bigint DEFAULT NULL,
  `restaurant_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK3u354ci12f8qgq55qvec32xku` (`restaurant_id`),
  UNIQUE KEY `UKnw8e5ie5lb7pw9db1xwy1sj0c` (`payment_id`),
  KEY `FKlwylj1qrsxh84g8d7er0f5585` (`customer_id`),
  CONSTRAINT `FK2vnd6yad4877v3soxcqk937u1` FOREIGN KEY (`restaurant_id`) REFERENCES `Restaurant` (`id`),
  CONSTRAINT `FKlwylj1qrsxh84g8d7er0f5585` FOREIGN KEY (`customer_id`) REFERENCES `Customer` (`id`),
  CONSTRAINT `FKso7egdy3vsx02j9rh8bd2tv95` FOREIGN KEY (`payment_id`) REFERENCES `OrderPaymentDetail` (`id`)
);
```
</details>

<details>
  <summary>OrderItem</summary>

```
CREATE TABLE `OrderItem` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `price` double NOT NULL,
  `quantity` int NOT NULL,
  `totalPrice` double NOT NULL,
  `menu_item_id` bigint NOT NULL,
  `order_detail_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKaqqkf7tjdpqcuohm2g7uww24q` (`menu_item_id`),
  KEY `FK7cmys9vguhxa89fd095xyeyje` (`order_detail_id`),
  CONSTRAINT `FK7cmys9vguhxa89fd095xyeyje` FOREIGN KEY (`order_detail_id`) REFERENCES `OrderDetail` (`id`),
  CONSTRAINT `FKaqqkf7tjdpqcuohm2g7uww24q` FOREIGN KEY (`menu_item_id`) REFERENCES `MenuItem` (`id`)
);
```
</details>

<details>
  <summary>OrderPaymentDetail</summary>

```
CREATE TABLE `OrderPaymentDetail` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `amount` double NOT NULL,
  `createdDate` datetime(6) DEFAULT NULL,
  `modifiedDate` datetime(6) DEFAULT NULL,
  `paymentMethod` enum('CASH','CREDIT_CARD','DEBIT_CARD','UPI') NOT NULL,
  `paymentStatus` enum('COMPLETED','IN_PROGRESS','REJECTED') NOT NULL,
  `version` bigint DEFAULT NULL,
  PRIMARY KEY (`id`)
);
```
</details>

<details>
  <summary>PaymentDetail</summary>

```
CREATE TABLE `PaymentDetail` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `cardNumber` varchar(255) DEFAULT NULL,
  `createdDate` datetime(6) DEFAULT NULL,
  `modifiedDate` datetime(6) DEFAULT NULL,
  `paymentMethod` enum('CASH','CREDIT_CARD','DEBIT_CARD','UPI') NOT NULL,
  `upiId` varchar(255) DEFAULT NULL,
  `version` bigint DEFAULT NULL,
  PRIMARY KEY (`id`)
);
```
</details>

<details>
  <summary>Restaurant</summary>

```
CREATE TABLE `Restaurant` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `hoursOfOperation` varchar(255) NOT NULL,
  `restaurantName` varchar(255) NOT NULL,
  `address_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKqv32m3urxsfjse6opfhwumq4v` (`address_id`),
  UNIQUE KEY `UK22c22gdvl7ytyrp7blgbx50hn` (`user_id`),
  CONSTRAINT `FK8i5m1l2w0etkuaeh6l3c6926w` FOREIGN KEY (`user_id`) REFERENCES `User` (`id`),
  CONSTRAINT `FKggm3momemeke04br3yroi9dbg` FOREIGN KEY (`address_id`) REFERENCES `Address` (`id`)
);
```
</details>

<details>
  <summary>RestaurantDeliveryZone</summary>
  
```
CREATE TABLE `RestaurantDeliveryZone` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `pinCode` varchar(255) DEFAULT NULL,
  `zoneName` varchar(255) DEFAULT NULL,
  `restaurant_owner_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKp8exwsjyt2fg829brviuljgmv` (`restaurant_owner_id`),
  CONSTRAINT `FKp8exwsjyt2fg829brviuljgmv` FOREIGN KEY (`restaurant_owner_id`) REFERENCES `Restaurant` (`id`)
);
```
</details>

<details>
  <summary>RestaurantOpeningDetail</summary>
  
```
CREATE TABLE `RestaurantOpeningDetail` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `closingTime` varchar(255) DEFAULT NULL,
  `day` varchar(255) DEFAULT NULL,
  `openingTime` varchar(255) DEFAULT NULL,
  `restaurant_owner_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKbgr41gh89agchdtlrj7uanr0v` (`restaurant_owner_id`),
  CONSTRAINT `FKbgr41gh89agchdtlrj7uanr0v` FOREIGN KEY (`restaurant_owner_id`) REFERENCES `Restaurant` (`id`)
);
```
</details>

<details>
  <summary>User</summary>

```
CREATE TABLE `User` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `createdDate` datetime(6) DEFAULT NULL,
  `lastLogin` datetime(6) DEFAULT NULL,
  `modifiedDate` datetime(6) DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `role` enum('ADMIN','CUSTOMER','DELIVERY_PERSONNEL','RESTAURANT_OWNER') NOT NULL,
  `status` enum('ACTIVATED','DEACTIVATED') NOT NULL,
  `username` varchar(255) NOT NULL,
  `version` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKjreodf78a7pl5qidfh43axdfb` (`username`)
);
```
</details>

Testing
-
|No. | Description | Screenshot |
|----|-------------|------------|
|1.  | Jacoco test coverage report | ![unit-test-coverage-jacoco](https://github.com/user-attachments/assets/b5148f99-9001-4b02-9e1e-a6820db253ce) |
|2.  | Jacoco test coverage report for service package | ![unit-test-coverage-jacoco-2](https://github.com/user-attachments/assets/cd0320b8-5f3c-4a49-b9c9-772c1f811607) |
|3.  | Test cases folder structure | ![unit-test-classes-structure](https://github.com/user-attachments/assets/5499eea0-e060-48f8-98e3-697d22aabe20) |
|4.  | Admin registration successful | ![registration-admin-successful](https://github.com/user-attachments/assets/69504629-6cf8-4771-8bdd-10bb5d4d7279) |
|5.  | Admin registration failed | ![registration-admin-bad-request](https://github.com/user-attachments/assets/ec63b82f-81c4-4281-8ac0-e4eb41ef051f) |
|6.  | Customer registration successful | ![registration-customer-successful](https://github.com/user-attachments/assets/f780ffea-339f-4bc5-b9e0-5bcd6f52d786) |
|7.  | Customer regisration failed | ![registration-customer-bad-request](https://github.com/user-attachments/assets/dc205c8f-c58c-40cf-bf95-531ccba77201) |
|8.  | Delivery personnel registration successful | ![registration-delivery-successful](https://github.com/user-attachments/assets/d12264bc-0dbd-4d12-9723-2f171f1aa6a5) |
|9.  | Delivery personnel registration failed | ![registration-delivery-bad-request](https://github.com/user-attachments/assets/b99f5486-a720-44be-b17c-989fca17f0f2) |
|10. | Restaurant registration successful | ![registration-restaurant-successful](https://github.com/user-attachments/assets/212161e8-1ab1-4265-a7b2-5429fb7cc4a2) ![registration-restaurant-successful-2](https://github.com/user-attachments/assets/efee3e59-e990-4620-a34c-fc27078416bb) ![registration-restaurant-successful-3](https://github.com/user-attachments/assets/a3353552-870c-4d85-abf3-a38c4b941bf8) |
|11. | Restaurant registration failed | ![registration-restaurant-bad-request](https://github.com/user-attachments/assets/cef74565-9b26-4a54-ae5b-567cda7859e7) | 
|12. | Customer profile fetch successful | ![profile-fetch-customer-successful](https://github.com/user-attachments/assets/f6dbbcf7-6b99-4e37-8fb4-4938a558c271) |
|13. | Customer profile update successful | ![profile-update-customer-successful](https://github.com/user-attachments/assets/6dfba4b1-58f3-456b-bf0a-e8ba387d1cd8) |
|14. | Delivery personnel profile successful | ![profile-fetch-delivery-personnel-successful](https://github.com/user-attachments/assets/35963a20-4d38-4371-9780-ac1867fc6e43) |
|15. | Delivery personnel update successful | ![profile-update-delivery-personnel-successful](https://github.com/user-attachments/assets/48821169-ca4c-4dc9-abf7-214f0e215b7e) |
|16. |
|17. |
|18. |
|19. |
|20. |









