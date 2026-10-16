# Customer Account Service

Fresh Spring Boot validation project for CodeIntelligence and Jira CAS-1.

## Data flow
XML -> CustomerXmlRequest -> CustomerXmlMapper -> Customer model -> CustomerJpaMapper -> CustomerAccountEntity -> CustomerAccountRepository -> CUSTOMER_ACCOUNT.

## Endpoints
- POST /api/customers (application/xml)
- GET /api/customers/{customerId}
- GET /api/customers
- PUT /api/customers/{customerId} (application/xml)
- PATCH /api/customers/{customerId}/account-type?accountType=PREMIUM
- DELETE /api/customers/{customerId}

Only the six CAS-1 business attributes are represented in the XML DTO, model and entity: customerId, firstName, lastName, email, countryCode, accountType.

Run with `gradle bootRun` using JDK 21 and Gradle 8.14+ or 9.x. Run tests with `gradle test`. Import the folder into IntelliJ as a Gradle project.
