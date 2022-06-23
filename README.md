# pismotest
Pismo Test Application

It has in-memory database H2, it will start on the application start and create schema. You can access is via http://localhost:8080/h2-ui
with database jdbc:h2:file:~/pismodb

This application has swagger implemented, http://localhost:8080/swagger-ui.html

You can find three api in above swagger url, one is getAccount where you have to pass accountId
at start of application default account is created.

You can able create account with post method of createAccount, you just need to pass
documentNumber.

Transaction ceation api is provided with this application where you need to pass accountId which you have created through
createAccount and it returned Id to you, you can pass operation types id which is already created in h2 database. you can get value through select statement and you need to pass amount as well.

OperationsTypes

OperationType_ID    Description

    1               Normal Purchase
    2               Purchase with installments
    3               Withdrawal
    4               Credit Voucher

To Run Application:

    1 mvn clean 
    2 mvn test
    3 mvn clean install
    4 Go to the target folder
    5 java -jar PismoTest-1.0-SNAPSHOT.jar
    6 Verify your RESTful calls.