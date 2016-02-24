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
