#myretail-api
This RESTful service provides product details from Target Redsky source and price from Mongo DB setup locally.

##Technology Stack:
1. SpringBoot - RESTful service development
2. MongoDB - NoSQL database to store the product price details
3. Gradle - Build Tool

#Endpoint URL and Methods:
GET - http://localhost:8080/myretail/v1/products/{id}
PUT - http://localhost:8080/myretail/v1/products/{id}
Content-Type: application/json

#Example: 
Request - GET
URL - http://localhost:8080/myretail/v1/products/16696652
Response - 
```json
{
id: 16696652,
name: "Beats Solo 2 Wireless - Black (MHNG2AM/A)",
current_price: {
value: 999.99,
currency_code: "USD"
}
}
```

Request - PUT
URL - http://localhost:8080/myretail/v1/products/16696652
Request Body -
```json
{
  "current_price": 1999.99,
  "currency_code": "USD"
}
```
Response -
```json
{
  "id": "57e1d683b86c3ba1f88d32ab",
  "modify_timestamp": 1487607147322,
  "product_id": 16696652,
  "current_price": 1999.99,
  "currency_code": "USD"
}
```

##Usage
1. Clone the repo
2. Install Mongo DB and create a collection (product_price) and insert product price data for product ids: 13860428, 15117729, 16483589, 16696652, 16752456, 15643793
Sample document:
```json
{
    "_id" : ObjectId("57e1d683b86c3ba1f88d32ab"),
    "_class" : "com.myretail.products.dao.MyRetailProductPrice",
    "product_id" : 13860428,
    "current_price" : 49.99,
    "currency_code" : "USD",
    "modifyTimestamp" : ISODate("2017-02-20T16:23:42.455Z")
}
```
3. Build JAR - ./gradlew clean build

# Author
Name: Shreyas Shivaprakash
Email: shreyas.aradya@gmail.com