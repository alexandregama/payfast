# Payfast

## To Do

###### Infrastructure
- [x] Simulate Payments database with Map
- [x] Simulate Users database with Map

###### Retrieve Users
- [x] Endpoint to retrieve User from its **Id**, responding **JSON** format
- [x] Endpoint to retrieve User from its **Id**, responding **XML** format

###### Retrieve Payments
- [x] Endpoint to retrieve Payment from its **Id**, responding **JSON** format
- [x] Endpoint to retrieve Payment from its **Id**, responding **XML** format

###### Save Transactions
- [ ] Endpoint to save a new Transaction formatted with **JSON**
- [ ] Endpoint to save a new Transaction formatted with **XML**

###### Update Payment Status with PUT
- [ ] Endpoint to update a payment status, using **JSON** format as accept mime type
- [ ] Endpoint to update a payment status, using **XML** format as accept mime type

###### Update Payment Status with PATCH
- [ ] Endpoint to update a payment status, using **JSON** format as accept mime type 
- [ ] Endpoint to update a payment status, using **XML** format as accept mime type

###### HATEOAS Endpoint
- [ ] Endpoint to save a new Transaction and returning a HATEOAS resource, using **JSON** format
- [ ] Endpoint to save a new Transaction and returning a HATEOAS resource, using **XML** format

## Some possible problems

###### Could not find MessageBodyWriter for response object of type: com.payfast.User of media type: application/xml

You just need to configure you class to use **JAX-B**. Not that you must create a default constructor.
Example:

```java
@XmlRootElement
public class User {

   //JAX-B eyes only
   public User {
   }

}
```

## Endpoint commands

To retrieve a user by its id, returning JSON format:

```bash
$ curl -i -X GET -H "accept: application/json" http://localhost:8080/payfast/users/1
```
Possible return:
```bash
HTTP/1.1 200 OK
Connection: keep-alive
X-Powered-By: Undertow/1
Server: WildFly/8
Transfer-Encoding: chunked
Content-Type: application/json
Date: Mon, 22 Feb 2016 23:36:35 GMT

{"id":1,"name":"Alexandre Gama"}
```

To retrieve a user by its id, returning XML format:

```bash
$ curl -i -X GET -H "accept: application/xml" http://localhost:8080/payfast/users/1
```
Possible return:
```bash
HTTP/1.1 200 OK
Connection: keep-alive
X-Powered-By: Undertow/1
Server: WildFly/8
Content-Type: application/xml
Content-Length: 105
Date: Mon, 22 Feb 2016 23:37:18 GMT

<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<user>
   <id>1</id>
   <name>Alexandre Gama</name>
</user>
```


