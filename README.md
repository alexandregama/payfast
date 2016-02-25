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

To save a new **Transaction** using **JSON** format

```bash
$ curl -i -X POST http://localhost:8080/payfast/users \
	  -d '{"id":"1","name":"Fernando Gama"}' \
	  -H "Content-Type:application/json"
```

Response

```bash
HTTP/1.1 201 Created
Connection: keep-alive
X-Powered-By: Undertow/1
Server: WildFly/8
Location: http://localhost:8080/users/5
Transfer-Encoding: chunked
Content-Type: application/json
Date: Wed, 24 Feb 2016 12:11:23 GMT

{
  "id": 5,
  "name": "Fernando Gama"
}
```

Note that we have a content location for the new resource created: ```bash Location: http://localhost:8080/users/5``` 

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

Creating a new Payment

```bash
$ curl -i -X POST http://localhost:8080/payfast/payments \
	    -H "Content-Type:application/json" \
	    -d '{"id":"1", "value": "100", "status":"INPROGRESS"}'
```

Return

```bash
HTTP/1.1 201 Created
Connection: keep-alive
X-Powered-By: Undertow/1
Server: WildFly/8
Location: http://localhost:8080/payments/7
Content-Length: 0
Date: Thu, 25 Feb 2016 10:52:08 GMT

{
  "id": 8,
  "status": "STARTED",
  "value": 100,
  "links": {
    "link": [
      {
        "rel": "confirm",
        "uri": "http://localhost:8080/payfast/payments/8",
        "method": "PUT"
      },
      {
        "rel": "cancel",
        "uri": "http://localhost:8080/payfast/payments/8",
        "method": "DELETE"
      }
    ]
  }
}
```

Note that the Endpoints returns a resource location for the new Payment created ```bash http://localhost:8080/payments/7```

Note also that we received a response with the next steps for the created resource. This is a HATEOAS approach.

## Transaction Endpoint

To retrieve a **Transaction** from its id, using **JSON** format

```bash
$ curl -i -X GET -H "accept:application/json" http://localhost:8080/payfast/transactions/1
```

Response

```bash
HTTP/1.1 200 OK
Connection: keep-alive
X-Powered-By: Undertow/1
Server: WildFly/8
Transfer-Encoding: chunked
Content-Type: application/json
Date: Wed, 24 Feb 2016 11:50:49 GMT

{
  "id": 1,
  "user": {
    "id": 1,
    "name": "Alexandre Gama"
  },
  "payment": {
    "id": 1,
    "status": "INPROGRESS"
  }
}
```

## HTTP Status Code

##### SUCCESS 2xx

200 - Ok

201 - Created

202 - Accepted (but the processing has not been completed)

203 - Partial Information

204 - No Response

##### REDIRECTION 3xx

301 - Moved

304 - Not Modified

##### ERROR 4xx

400 - Bad Request

401 - Unauthorized

403 - Forbidden

404 - Not Found

405 - Method not Allowed

##### INTERNAL ERROR 5xx

500 - Internal Error

501 - Not Implemented

502 - Service Temporaly Overloaded

503 - Gateway Timeout

## Some Errors

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

Tip for the following error:
``` bash
java.lang.NoClassDefFoundError: org/apache/http/conn/ssl/X509HostnameVerifier
	at java.lang.Class.getDeclaredConstructors0(Native Method)
	at java.lang.Class.privateGetDeclaredConstructors(Class.java:2671)
	at java.lang.Class.getConstructor0(Class.java:3075)
	at java.lang.Class.newInstance(Class.java:412)
	at javax.ws.rs.client.FactoryFinder.newInstance(FactoryFinder.java:116)
	at javax.ws.rs.client.FactoryFinder.find(FactoryFinder.java:164)
	at javax.ws.rs.client.ClientBuilder.newBuilder(ClientBuilder.java:86)
	at javax.ws.rs.client.ClientBuilder.newClient(ClientBuilder.java:114)
	at br.com.payfast.client.UserResourceTest.shouldRetrieveAnUserByItsId(UserResourceTest.java:13)
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.lang.reflect.Method.invoke(Method.java:497)
	at org.junit.runners.model.FrameworkMethod$1.runReflectiveCall(FrameworkMethod.java:44)
	at org.junit.internal.runners.model.ReflectiveCallable.run(ReflectiveCallable.java:15)
	at org.junit.runners.model.FrameworkMethod.invokeExplosively(FrameworkMethod.java:41)
	at org.junit.internal.runners.statements.InvokeMethod.evaluate(InvokeMethod.java:20)
	at org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java:76)
	at org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java:50)
	at org.junit.runners.ParentRunner$3.run(ParentRunner.java:193)
	at org.junit.runners.ParentRunner$1.schedule(ParentRunner.java:52)
	at org.junit.runners.ParentRunner.runChildren(ParentRunner.java:191)
	at org.junit.runners.ParentRunner.access$000(ParentRunner.java:42)
	at org.junit.runners.ParentRunner$2.evaluate(ParentRunner.java:184)
	at org.junit.runners.ParentRunner.run(ParentRunner.java:236)
	at org.eclipse.jdt.internal.junit4.runner.JUnit4TestReference.run(JUnit4TestReference.java:86)
	at org.eclipse.jdt.internal.junit.runner.TestExecution.run(TestExecution.java:38)
	at org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.runTests(RemoteTestRunner.java:459)
	at org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.runTests(RemoteTestRunner.java:675)
	at org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.run(RemoteTestRunner.java:382)
	at org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.main(RemoteTestRunner.java:192)
Caused by: java.lang.ClassNotFoundException: org.apache.http.conn.ssl.X509HostnameVerifier
	at java.net.URLClassLoader.findClass(URLClassLoader.java:381)
	at java.lang.ClassLoader.loadClass(ClassLoader.java:424)
	at sun.misc.Launcher$AppClassLoader.loadClass(Launcher.java:331)
	at java.lang.ClassLoader.loadClass(ClassLoader.java:357)
	... 31 more
```
Maybe you are trying to call a ClientBuilder API without a Web Context. If you try to call the ClientBuilder API in a standalone mode, you won't have some dependencies, as **org.apache.httpcomponents** that fixes the problem above.

```xml
<dependency>
	<groupId>org.apache.httpcomponents</groupId>
	<artifactId>httpclient</artifactId>
	<version>4.3.4</version>
</dependency>
```

Tip for the following error:
```bash
java.lang.NoClassDefFoundError: org/apache/commons/io/output/DeferredFileOutputStream
	at org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder.initDefaultEngine(ResteasyClientBuilder.java:468)
	at org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder.build(ResteasyClientBuilder.java:347)
	at org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder.build(ResteasyClientBuilder.java:50)
	at javax.ws.rs.client.ClientBuilder.newClient(ClientBuilder.java:114)
	at br.com.payfast.client.UserResourceTest.shouldRetrieveAnUserByItsId(UserResourceTest.java:13)
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.lang.reflect.Method.invoke(Method.java:497)
	at org.junit.runners.model.FrameworkMethod$1.runReflectiveCall(FrameworkMethod.java:44)
	at org.junit.internal.runners.model.ReflectiveCallable.run(ReflectiveCallable.java:15)
	at org.junit.runners.model.FrameworkMethod.invokeExplosively(FrameworkMethod.java:41)
	at org.junit.internal.runners.statements.InvokeMethod.evaluate(InvokeMethod.java:20)
	at org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java:76)
	at org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java:50)
	at org.junit.runners.ParentRunner$3.run(ParentRunner.java:193)
	at org.junit.runners.ParentRunner$1.schedule(ParentRunner.java:52)
	at org.junit.runners.ParentRunner.runChildren(ParentRunner.java:191)
	at org.junit.runners.ParentRunner.access$000(ParentRunner.java:42)
	at org.junit.runners.ParentRunner$2.evaluate(ParentRunner.java:184)
	at org.junit.runners.ParentRunner.run(ParentRunner.java:236)
	at org.eclipse.jdt.internal.junit4.runner.JUnit4TestReference.run(JUnit4TestReference.java:86)
	at org.eclipse.jdt.internal.junit.runner.TestExecution.run(TestExecution.java:38)
	at org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.runTests(RemoteTestRunner.java:459)
	at org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.runTests(RemoteTestRunner.java:675)
	at org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.run(RemoteTestRunner.java:382)
	at org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.main(RemoteTestRunner.java:192)
Caused by: java.lang.ClassNotFoundException: org.apache.commons.io.output.DeferredFileOutputStream
	at java.net.URLClassLoader.findClass(URLClassLoader.java:381)
	at java.lang.ClassLoader.loadClass(ClassLoader.java:424)
	at sun.misc.Launcher$AppClassLoader.loadClass(Launcher.java:331)
	at java.lang.ClassLoader.loadClass(ClassLoader.java:357)
	... 27 more
```

If you are in a standalone mode, you need the following dependencie:

```xml
 <dependency>
	<groupId>commons-io</groupId>
	<artifactId>commons-io</artifactId>
	<version>2.4</version>
</dependency>
```

If you have got the following error:

```bash
javax.ws.rs.client.ResponseProcessingException: javax.ws.rs.ProcessingException: Unable to find a MessageBodyReader of content-type application/json and type class br.com.payfast.Payment
	at org.jboss.resteasy.client.jaxrs.internal.ClientInvocation.extractResult(ClientInvocation.java:140)
	at org.jboss.resteasy.client.jaxrs.internal.ClientInvocation.invoke(ClientInvocation.java:444)
	at org.jboss.resteasy.client.jaxrs.internal.ClientInvocationBuilder.get(ClientInvocationBuilder.java:165)
	at br.com.payfast.client.UserResourceTest.shouldRetrieveAnUserByItsId(UserResourceTest.java:19)
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.lang.reflect.Method.invoke(Method.java:497)
	at org.junit.runners.model.FrameworkMethod$1.runReflectiveCall(FrameworkMethod.java:44)
	at org.junit.internal.runners.model.ReflectiveCallable.run(ReflectiveCallable.java:15)
	at org.junit.runners.model.FrameworkMethod.invokeExplosively(FrameworkMethod.java:41)
	at org.junit.internal.runners.statements.InvokeMethod.evaluate(InvokeMethod.java:20)
	at org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java:76)
	at org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java:50)
	at org.junit.runners.ParentRunner$3.run(ParentRunner.java:193)
	at org.junit.runners.ParentRunner$1.schedule(ParentRunner.java:52)
	at org.junit.runners.ParentRunner.runChildren(ParentRunner.java:191)
	at org.junit.runners.ParentRunner.access$000(ParentRunner.java:42)
	at org.junit.runners.ParentRunner$2.evaluate(ParentRunner.java:184)
	at org.junit.runners.ParentRunner.run(ParentRunner.java:236)
	at org.eclipse.jdt.internal.junit4.runner.JUnit4TestReference.run(JUnit4TestReference.java:86)
	at org.eclipse.jdt.internal.junit.runner.TestExecution.run(TestExecution.java:38)
	at org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.runTests(RemoteTestRunner.java:459)
	at org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.runTests(RemoteTestRunner.java:675)
	at org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.run(RemoteTestRunner.java:382)
	at org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.main(RemoteTestRunner.java:192)
Caused by: javax.ws.rs.ProcessingException: Unable to find a MessageBodyReader of content-type application/json and type class br.com.payfast.Payment
	at org.jboss.resteasy.core.interception.ClientReaderInterceptorContext.throwReaderNotFound(ClientReaderInterceptorContext.java:39)
	at org.jboss.resteasy.core.interception.AbstractReaderInterceptorContext.getReader(AbstractReaderInterceptorContext.java:73)
	at org.jboss.resteasy.core.interception.AbstractReaderInterceptorContext.proceed(AbstractReaderInterceptorContext.java:50)
	at org.jboss.resteasy.plugins.interceptors.encoding.GZIPDecodingInterceptor.aroundReadFrom(GZIPDecodingInterceptor.java:59)
	at org.jboss.resteasy.core.interception.AbstractReaderInterceptorContext.proceed(AbstractReaderInterceptorContext.java:53)
	at org.jboss.resteasy.client.jaxrs.internal.ClientResponse.readFrom(ClientResponse.java:248)
	at org.jboss.resteasy.client.jaxrs.internal.ClientResponse.readEntity(ClientResponse.java:181)
	at org.jboss.resteasy.specimpl.BuiltResponse.readEntity(BuiltResponse.java:211)
	at org.jboss.resteasy.client.jaxrs.internal.ClientInvocation.extractResult(ClientInvocation.java:104)
	... 25 more

```

Maybe you are trying to call the ClientBuilder API from a standalone mode. If its true, you must get the following dependency:
```xml
 <dependency>
	<groupId>commons-io</groupId>
	<artifactId>commons-io</artifactId>
	<version>2.4</version>
</dependency>
```

The possible problem is tha RestEasy is unable to find the Jackson provider
