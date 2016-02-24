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

## User Endpoint

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

## Payment Endpoint

To retrieve a **payment** by its **id**, returning JSON format:

```bash
$ curl -i -X GET -H "accept:application/json" http://localhost:8080/payfast/payments/1
```

Possible return:
```bash
HTTP/1.1 200 OK
Connection: keep-alive
X-Powered-By: Undertow/1
Server: WildFly/8
Transfer-Encoding: chunked
Content-Type: application/json
Date: Wed, 24 Feb 2016 00:21:45 GMT

{"id":1,"status":"INPROGRESS"}
```

If we try to retrieve a payment that **does not support XML** format, we get the following error:

```bash
HTTP/1.1 406 Not Acceptable
Connection: keep-alive
X-Powered-By: Undertow/1
Server: WildFly/8
Content-Length: 0
Date: Wed, 24 Feb 2016 00:23:07 GMT
```

Even payment accept the XML format, we must use ```java @XmlRootElement``` in our **Payment** class. If we try to retrieve without that annotation, we get the following error:

```bash
HTTP/1.1 500 Internal Server Error
Connection: keep-alive
X-Powered-By: Undertow/1
Server: WildFly/8
Content-Type: text/html
Content-Length: 112
Date: Wed, 24 Feb 2016 00:27:14 GMT

Could not find MessageBodyWriter for response object of type: com.payfast.Payment of media type: application/xmlI
```

Retrieving a payment that supports **XML** format

```bash
$ curl -i -X GET -H "accept:application/xml" http://localhost:8080/payfast/payments/1
```

Tip for the following error:
```bash
Class has two properties of the same name "id"
	this problem is related to the following location:
		at public java.lang.Long com.payfast.Payment.getId()
		at com.payfast.Payment
	this problem is related to the following location:
		at private java.lang.Long com.payfast.Payment.id
		at com.payfast.Payment
```
- This happens because JAX-B uses a getter and setter methods to serialize a object to json. We can't use ```java @XmlElement(name="id")``` for example, if we have a method getter ```java getId()```

- We can configure the class to use Field as follow:

```java
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Payment {

}
```
