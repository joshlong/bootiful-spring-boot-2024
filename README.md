# Bootiful Spring Boot 2024

this is a place to practice and derust the new talk for 2024




what things do we want to include in the new edition? 

- java 21
- devtools
- docker compose
- moduliths 
- graphql 
- spring data jdbc 
- kafka 
- graalvm native images
- Postgres?
- spring authorization server



- make sure to setup a spring ai env variable in `~/.zshrc`
- add the following maven repositories 
```
  <repositories>
    <repository>
      <id>spring-milestones</id>
      <name>Spring Milestones</name>
      <url>https://repo.spring.io/milestone</url>
      <snapshots>
        <enabled>false</enabled>
      </snapshots>
    </repository>
    <repository>
      <id>spring-snapshots</id>
      <name>Spring Snapshots</name>
      <url>https://repo.spring.io/snapshot</url>
      <releases>
        <enabled>false</enabled>
      </releases>
    </repository>
  </repositories>
  <pluginRepositories>
    <pluginRepository>
      <id>spring-milestones</id>
      <name>Spring Milestones</name>
      <url>https://repo.spring.io/milestone</url>
      <snapshots>
        <enabled>false</enabled>
      </snapshots>
    </pluginRepository>
    <pluginRepository>
      <id>spring-snapshots</id>
      <name>Spring Snapshots</name>
      <url>https://repo.spring.io/snapshot</url>
      <releases>
        <enabled>false</enabled>
      </releases>
    </pluginRepository>
  </pluginRepositories>
```

### new domain model 


- `customer` (spring auth server?)
- `product` (1:N on customer, maybe?)
- `order` - whenever we get a new order we update the product inventory

- build out customer, product, order in separate packages 
- show a spring moduliths test enforcing unidirectionality 
- use spring ai to generate descriptions for our products
	- talk about how that took a long time, show the virtual threads support
- graphql controllers in each package manipulating the entity, but everything is package private. how do we communicate changes to other parts of the system? events! and it turns out spring has a nice mechanism for that
	- the spring application event bus , but what about transactions? 
	- ecternal services? externalized annotation!
- ok, u can try it out in the console. but what about these customers? lets link that to the spring security auth server! and use the AuthenticationSuccessEvent or something to do an upsert 
- alright! it's looking good! let's get this thing compiled for prod! a couple of things we care about: buildpacks and graalvm !


