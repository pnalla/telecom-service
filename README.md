# telecom-service

```
Telecom operator database stores phone numbers associated to customers
(1 customer: N phone numbers) and we will provide interfaces to manage them.
Below capabilities are provided:
• get all phone numbers
• get all phone numbers of a single customer
• activate a phone number
```

### Endpoints
```
Get all phone numbers [GET api]: /v{version}/phones
Get all phone numbers of a single customer[GET api]: /v{version}/customer/{customerId}/phones
Activate a phone number[PUT api]: /v{version}/phone/{phoneNumber}/active
```

### Technologies/tools used: 
```
Springboot, Java8, Mapstructs, Gradle, PostgresSql, Flyway, docker, Testing- Mockito, RestAssured
```

### Notes:
```
1.Docker is required to run blackbox tests 
2.I have used mapstructs to map the objects easily. 
3.Implemented versioning of the api's (thought its not fully validated)
4. custom exception "DataNotFound" is thrown if the data doesn't exists 
5. Added mapstruct implementaion to show extra skills
6. Pagination is implemented in service class for better performance in sql query (just added as an extra feature)
7.I have written Unit Tests, Component Tests and blackbox test cases.
8. Schema files are in db/migration folder
```

### To Run the app locally: 
```
./gradlew bootRun
```

### To run unit and component tests 
```
./gradlew test
```

### Running blackbox tests
```
Before running blackbox tests you need to run 
./gradlew bootRun and then run the test locally.
```