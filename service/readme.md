# README 


- I would absolutely have to show a simple diagram with the products/inventory, the auth, the orders/lineitems all separated out as separate domains that need to be cared for 
- OpenAI, graalvm, virtual threads, modulith, web, docker compose, JDBC, PGVector (do _not_ choose Postgres as it will be redundant), testcontainers , java 21 , OAuth resource server 
- Disable the docker compose support 
- Disable the resource server support 
- Start the docker compose file 
- Specify `datasource` connectivity credentials in `application.properties` 
- Schema.sql / data.sql for `orders` and `line_items` 
- Use Spring Data JDBC to model it and the 1:N relationship in the same aggregate / package 
- Put stuff under foo.orders.controllers, foo.orders.services, etc. 
	- Show how that is fine, but its not fine if u start exporting things from there as public 
	- Show the test breaking 
- on the subject of the records, talk about java 21 and data oriented programing and such  // (maybe just link them to the Brian Goetz article? )
- Ok, whenever i place an order, im depleting inventory. We need to reflect that in whatever ther service manages inventory. 
- Show `ApplicationModuleListener`
- Should the inventory be a sort of catalog of products? Can those products be off to the size and i ingest them when the program starts and write them to a VectorDB? Eg, each `product` has an `invetory_quantity` field which we deplete when an order is placed? 
	- So we’d have `orders`, and `products` 
- do we just run `products.sql` once before we start doing any serious work? Or i do build up a spring batch `Job`? Either ay i have to do _something_ in the app to read those products (either from another sql table or from a file or whatever ) and then also write their descriptions to a vector db 
	- Could i use spring integration to poll and then write messages to the vector db as new records in the products` table?  So the first time the program starts it loads all of them but then doesn’t? 
	- Or maybe use spring cloud functions catalog ? I just dont know… 
	- If each time we wrote to a vector DB we also updated a Boolean on the product table (processed == true), then we could know to not re-process the record subsequently. We could have spring integration look for the records where processed = false, and it could kick off a batch job that read all the records and wrote them to the vector store 
- then use AI and the vector db to put together another HTTP endpoint called, ‘personal shopper’ , which lets u enter a question. We turn that into a bunch of results from Teh vector db by doing similarity search, then we give the results (making sure to specify `SearchRequest.topK(10)`) to oPenAI as part of a RAG demo to get the best results (Eg, "I am looking for the best shirts to wear in the spring time?” It would be super cool if that somehow returned my spring t-shirt lol) 
- Virtual threads - show Jose or Cora’s demo? 
	- If i did Cora’s demo, i wouldn’t need the docker image, i could use the actual httpbin.org 
	- If i did Cora’s demo then i would need `OHA` to be able to access the endpoints without an OAuth token. So maybe it’s best to add all the security stuff at the very end ? 
- What’s the username for the orders? Should come from the security context.  
- Enable the resource server support. This is an API. There may be many. Best to put the OAuth client support in a single place. Create an MVC gateway and make it a token relay with a google OAuth client 
- in the `service`, setup authentication success event listener to do idempotent creation of user and then install that new user in the `SecurityContextHolder`, wrapping the existing one (see how we do it in `media-mogul` ) 
- now whenever i place an order with line items i can do with the current user being placed in the `user` field for the order 
- then turn it into a graalvm native image 
	- Elevator music 