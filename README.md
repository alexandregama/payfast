# Payfast

## To Do

###### Infrastructure
- [x] Simulate Payments database with Map
- [x] Simulate Users database with Map

###### Retrieve Users
- [ ] Endpoint to retrieve User from its **Id**, responding **JSON** format
- [ ] Endpoint to retrieve User from its **Id**, responding **XML** format
- [ ] Endpoint to retrieve User from its **Id**, responding **Text Plain** format

###### Retrieve Payments
- [ ] Endpoint to retrieve Payment from its **Id**, responding **JSON** format
- [ ] Endpoint to retrieve Payment from its **Id**, responding **XML** format
- [ ] Endpoint to retrieve Payment from its **Id**, responding **Text Plain** format

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


