
# Yoga App

### User Guide

1. Clone this repository
2. Install Mysql and create a user and a database
3. Open src/main/resources/application.properties and update data to match your database configuration
4. Install Java and Maven
5. start the server with mvn spring-boot:run

### User Guide to tests

#### Front end
1. E2E Tests: `npm run e2e:coverage`
2. E2E Tests report: `nyc report --reporter=lcov --reporter=text-summary`
3. E2E Cypress tests: `ng e2e` 
4. E2E Generate report: `npm run e2e:ci`

#### Back end
1. Generate Jacoco report: `mvn clean test`

Additional information can be found in the `front` and `back` folders, user should read them 