# Intro
The below is a description of the choices I made for the *SmallToolFormatter* task. Spring-boot comments are added also.

Upon reading the task description, I took note of these two statements:

* The application must be written in a way that makes it possible to implement remaining algorithms without any considerable changes to existing code.
* It is allowed to use junit dependency for tests.

The above statements are "alpha omega", in my opinion. Not practicing these principles lead to *Not Clean Code* - and
thus, most likely, technical debt.

So with the above in mind i.e. code that can be unit tested, reused and extended - but also with Spring-boot in mind -
I have used the *Port Adapter* pattern for the task.

Yes, it is completely over-kill for the task being, but it illustrates the point.

# Port adapter
Also called the *Hexagon* pattern. I guess the term is borrowed from DDD i.e. Domain Driven Design, and was coined around 2008-2011.

I was introduced to this pattern by a tech lead person. In the beginning, I didn't like it at all.

Some of my thoughts were:

* Oh well, I know perfectly well how to decouple.
* I know how to use an interface. What have we? Strategy, Visitor, Delegator etc.
* And yes, I know SOLID, principles, patterns and best practice.
* I know how to do unit test.
* It is waste of time with all these bloated adapters i.e. maven modules. It is confusing.
* I can perfectly well make a nice project structure - without modules.
* .. etc. etc.

The fact is that I use it now. I recommend this pattern in every larger Spring-boot project - with many developers.
Why? I would like to explain.

## Elements
From a conceptual level, the pattern consists of these elements:

* Port (adapter interface).
* Adapters (raw implementation).
* Domain (model i.e. POJO's and interfaces).
* Core (SpringBoot application startup).
* Config (Beans, properties etc.).
* Use-case (the business logic).

## Maven modules
In the below, note that the port *part* i.e. the interfaces, are located in the *domain* maven module - thus the *maven modules* become:

* Adapter
  * N maven modules, each with a particular raw adapter implementation (located in the *adapter* package).
* Domain - 1 maven module with the domain model (i.e. POJO's of some type) and all port interfaces, each implemented by their adapters, respectively.
* Core - 1 maven module with SpringBoot application startup.
* Config - 1 maven module bringing in properties, @Beans instantiation and security etc..
* Use-case - 1 maven module with all the use-cases i.e. the business logic.

Ps in the following *domain type* and *domain model type* are used interchangeably.

## An example
![Port Adapter Example](/etc/doc/images/port-adapter.png)

Note that the *port* also illustrates the use-case implementation of the *executor interface* (see below).

## Adapters
An *Adapter* is an implementation of some resource, using raw types and methods from a particular library.

### Implementation
Implementations could be:

* SQL client doing CRUD operations.
* Apache Kafka client (publishing messages) or listener (receiving messages).
* REST client or endpoint.

The resource uses raw types for the implementation in question.

Example types are:

* DTO's generated by OpenAPI (openapi-generator-maven-plugin).
* Hibernate entities.

The adapter must implement a *port* interface.

### Calling
There are two types of calling, either *outgoing* or *ingoing*.

#### Outgoing
Here the use-case is calling the adapter e.g. like a *client* calling a REST endpoint or publishing a message. The *port interface* is used by the use-case.

#### Ingoing
Here the adapter is calling the use-case e.g. when adapter endpoint is called or message is received. The *executor interface* (see *Domain* below) is used by the adapter.

Parameters used in constructors and methods are domain type (or native types).

### Mapping
Mappers are located in the adapter. They're able to map between domain types and raw adapter types.
Raw adapter types does **not** escape the adapter.

### Dependencies
The adapter only has two Maven dependencies:

* the library used for that particular adapter e.g. Apache Kafka.
* the domain i.e. the model (types), the port -and executor interfaces.

Besides the above, the adapter is oblivious to all other modules in the project.

### Rules
* the adapter only uses the executor interface i.e. **not** a concrete implementation of a use-case. Thus, the use-case is decoupled from the adapter.
* adapters do **not** know about other adapters i.e. an adapter must not depend on another adapter like e.g. inject another adapter.
* it is the responsibility of the adapter to map between domain -and raw adapter types (3rd part library implementation type). Thus raw adapter types does **not** escape the adapter. They're mapped into domain types, which the adapter knows about (maven dependency). Only the adapter knows about both worlds of types - the adapter and the domain (types).
* the adapter will be called by the use-case, using the port interface, for *outgoing* calls.
* the use-case will be called by the adapter, using the executor interface, for *ingoing* calls.
 
## Domain
The domain consists of the model (i.e. *types*), *ports* -and *executor* interfaces used in the project.

### Model
This is basically POJO's, of some type, without any annotations besides (maybe) Lombok.

### Port
Here the port interfaces reside - each implemented by some adapter. The interfaces are located in the *port* package.

### Executor
Here the executor interfaces reside - each implemented by some use-case. The interfaces are located in the *executor* package.

#### Examples
Some examples of executor interfaces could be:

    public interface Executor {
      void execute();
    }

    public interface Executor<I> {
      void execute(I i);
    }

    public interface Executor<I,O> {
      O execute(I i);
    }

Note that the domain types are used for the *generics*.

### Dependencies
The domain module have no dependencies to other modules.

### Rules
* the domain module has **no** dependency to other maven modules.
* the port -and executor interface *is* the decouple mechanism between the use-case and the adapter.

## Use-case
The use-case *is* the business logic. From a dependency point of view, it only knows the domain types and ports - **not** the concrete adapter implementation. It is literately impossible to instantiate a concrete implementation (type), of some adapter, and use it
in the use case.

### Execute
The use-case is implementing the *Executor* interface. This is how the use-case is decoupled from the adapter. Again, the adapter then does **not** need to know about concrete use-case implementations. Thus, the use-case maven dependency, in the adapter, is **not** needed - only the domain module dependency.

### Dependencies
* the domain i.e. the model (types), port -and executor interfaces.
 
### Rules
* the use-case only uses port interfaces i.e. **not** a concrete implementation of an adapter. Thus, the adapter is decoupled from the use-case.
* the use-case only uses the domain model (types) i.e. **not** raw 3rd part library types or methods.
* adapter resources are used indiviually by the use-case i.e. no adapter injection, into another adapter, is made.
* the use-case is located in the Hexagon - like a *center*, orchestrating the business logic, with the usage of adapters for input and output.
* the use-case must implement the executor interface.

## Config
The *Config* module configures the dependency management i.e. bean instantiation and injection throughout the project.

It is here the use-case and the apdater are decoupled by:
* injecting the adapter (port interface) into the use-case for outgoing calls.
* injecting the use-case (executor interface) into the adapter for ingoing calls.

Note that *exclude filters* can be used in the @ComponentScan to avoid scanning of e.g. @RestController. This way @Qualifier on the executor interface is avoided - and thus also the use-case dependency in the adapter.

### Dependencies
Basically all except the *core* module i.e. all adapters, use-case and the domain module are needed to instantiate resources.

### Rules
From a Spring-boot perspective i.e. @Configuration class (or classes) of:

* bringing in properties.
* @Bean resource instantiation (as mentioned above).
* Security config (OAuth 2.0).
* etc..

## Core
The *Core* module is responsible for starting the application e.g. like *main* or *SpringBootApplication*.

## Conclusion
The result of using this pattern:
* **decoupled** - the use-case and adapters are completely decoupled through the port -and executor interface. Also, it is impossible to
  instantiate a concrete adapter implementation, thus it is **not** possible to have raw 3rd part types and library methods
  mixed with business logic. Also, if using the executor interface, it is impossible to instantiate a concrete use-case implementation from within the adapter.
  All in all, there is a good chance for *Clean Code*. 
* **unit test (use-case)** - because of the decoupling, the use-case unit test becomes very simple and clean, since it only
  focuses on business logic and domain types (includes port interfaces). Mocking and stubbing domain types and interfaces
  are very simple i.e. configuration details for mocking libraries like e.g. H2 are **not** bloating the use-case unit test. 
  And the @SpringBootTest annotation is **not** used in this test, thus the use-case test also becomes fast.
* **unit test (adapter)** - this unit test is testing the concrete adapter implementation, which is tight coupled to 3rd part
  library dependencies. And that is fine. And again, test library dependencies can be used in the adapter e.g. H2. But again,
  neither the 3rd part library raw types or methods - nor H2 is mixed with the use-case. In other words, the separation is
  *two-fold*, both in the production code (abstraction) and thus *also* in the unit test.
* **overview** - it is easy to overview the project since there is a clean distinction between use-case and adapter implementation.
  Especially in projects with many developers commiting to the same project.
* **template** - a standard project template can be made with most common modules and adapters e.g. Kafka, Cassandra, Mongodb, SQL etc. etc..
* **standard** - it could (maybe) be easier to communicate and keep the project *clean* (whatever the "clean" definition is).

# Questions

## 0d0a
It is not seen in the output example in the PDF, but this can be seen when looking in a hex editor:

    0d0a

Where

    0d = line break chr(10) i.e. \r
    0a = new line chr(13) i.e \n

I have chosen to ignore this, but would like to discuss it.

# ArgumentParser (adapter)

## Optional
Instead of:

    FormatEnum formatEnum = null;
    Integer width = null;

I could have used:

    Optional<FormatEnum> oFormatEnum = Optional.empty();
    Optional<Integer> oWidth = Optional.empty();

And then e.g.

    FormatEnum formatEnum = oFormatEnum.orElseThrow(() -> new IllegalArgumentException("FormatEnum is null"));

And let the toEnum return an optional:

    public static Optional<FormatEnum> toEnum(String type) {
        ...
        return Arrays.stream(FormatEnum.values())
            .filter(x -> x.type().equalsIgnoreCase(type))
            .findFirst();
    }

I decided not to use Optional, since I see it as an error, if the enum is not found. Also, if returning an optional,
the exception thrown, is an interpretation - not the actual cause.

# Console (adapter)
The *ConsoleInputReader* is tightly coupled with 

    Scanner scanner = new Scanner(System.in);

Also, the *ConsoleOutputWriter* is tightly coupled using

    System.out.println(output);

If making unit test of these adapters, they could be decoupled also.

# Formatter adapter

## Note on describing operators
Before injecting resources into the *AlignFormatter* the code for *LeftAlignFormatter* looked like this e.g.

     if (len >= width) {
        if (!alignment.isEmpty()) {
            addSpacesToAlignment(alignment, width - alignment.length());
            appendToResult(alignment, result);
            clear(alignment);
        }

        appendToResult(token, result);

    } else {
    ...

It could be written as

    if (isTokenGreaterThanOrEqualWidth(len, width)) {
        if (isAlignmentNotEmpty(alignment)) {
            addSpacesAndAppendToResult(alignment, result, width);
        }

        appendToResult(token, result);

    } else {
    ...

Again, it depends on what the standard is.

Ps I don't like "and" functions i.e. functions should do one thing and do it well.

## Injecting resources
After making the "left" and "right", I could see that the loop (now in AlignFormatter) basically did the same thing
regardless the type of formatting i.e. I decided to make it general thus injecting resources based on the type of
formatting functionality. These formatting types are implemented

* Left
* Right
* Centered

It should be easy to implement "Hard" and "Justified". If needed, a new class can be created, implementing the 
*LineFormatter* interface. Ps as I understand "hard format" should split the word and "justified format" should
keep the word, making the width larger, for that particular alignment.

# BeanConfig
This class can be seen as Spring-boot config class setting up @Beans for dependency management.

## Properties
Logging should be done instead of just using *System.out.println* for the exception message.

# Enum
I did not have any use for an *id* i.e. I removed it. It is important not to have *dead code*. But when I do need it,
I write the type as below:

    public enum FormatEnum {
      LEFT_ALIGNED(0, "left", "Left aligned"),
      RIGHT_ALIGNED(1, "right", "Left aligned"),
      CENTERED(2, "centered", "Centered");

      final private int id;
      final private String type;
      final private String description;

      FormatEnum(int id, String type, String description) {
          this.id = id;
          this.type = type;
          this.description = description;
      }

      public int id() {
          return id;
      }

      public String type() {
          return type;
      }

      public String description() {
          return description;
      }

      public static FormatEnum toEnum(int id) {
          return Arrays.stream(FormatEnum.values())
              .filter(x -> x.id() == id)
              .findFirst().orElseThrow(() ->
                  new IllegalArgumentException(String.format("Unknown format type. id = %d", id)));
      }

      public static FormatEnum toEnum(String type) {
          Objects.requireNonNull(type, "Format type can not be null");
          return Arrays.stream(FormatEnum.values())
              .filter(x -> x.type().equalsIgnoreCase(type))
              .findFirst().orElseThrow(() ->
                  new IllegalArgumentException(String.format("Unknown format type. type = %s", type)));
      }
    }

Often the enum type is written to db, and instead of a char type I like better to use integer in the db (for more on
Hibernate, see below). If the enum is part of db indexing no performance gain would be made, but they seldom are, thus
a numeric compare is faster than a string compare. As I remember, Mysql and Postgresql supports the enum type - MSSQL,
DB2 and Oracle doesn't.

I thought about making a *Visitor* with the enum types i.e. like one entry point for using the enum when creating objects
(like a generic factory) or executing some process. This way the enum type is not scattered around the project (with lack of
type check when e.g. adding a new enum). But then again, Java 21 does the enum check i.e. the compiler makes an error.
Also, *default* is not needed (throwing an exception) in the switch.

# Hibernate
My comment on Hibernate. Many people like Hibernate and I agree to some extent. 

## Pros
* it is fast for prototyping (but then my "praise" stops).
 
## Cons
* Hibernate is making a lot of objects, and it can be a problem for the garbage collector if reading a lot
  of data (if the reference is kept i.e. not null, the gc will not remove). I have experienced this many times in monoliths.
  I have to say, I have made all the mistakes regarding performance and scaling with Hibernate (JPA). And after learning
  about it, I have made analysis and refactored several monoliths with scaling and performance problems.
* joining tables can be a performance hit on the application. Especially if having many joins and querying lots of data. Usually,
  the problem is that the *business logic* is made during the "read", instead of moving the logic to the "write" side, and
  then expose the result in a *materialized view* with no joins (fast read). It is not a Hibernate problem, it is a design
  problem, but the point is that when using Hibernate, un-experienced developers forget about "Lazy" and "Eager" and do not
  make performance test, and well, suddenly the problem arrives in production. And it starts with business asking for some
  feature that needs some more data - and the question "How long will it take?" (on the planning meeting). And the
  answer might be quite optimistic because "Oh well, we have that e.g. list you're asking about in this particular entity. Hmm, 
  let me see - it will take 1 hour to implement". And business is happy. My point is just - one really have to be careful about
  scaling and performance when working with relational db (also).
* queries - at some point native queries are needed anyway i.e. I rather prefer plain old JDBC queries with the use of HikariCP.
* entities - the entities must not escape into use-case logic, thus they are mapped. Again, I rather just use JDBC queries
  and then map the result-set directly into domain objects, avoiding entity objects all together.
* custom types in entities - some like AttributeConverter for mapping and using custom types in entities, but it
  can really be a pain when upgrading Spring-boot (i.e. when also upgrading Hibernate).
  I just prefer, simple (native) types, if possible.
* indexing - even Oracle starts to slow down on performance when indexing above 1-2 million rows using timebased UUID as primary key.
  I recommend using Apache Cassandra (or other distributed db) if working with these data requirements - in particular *joins*.

## Conclusion
One really have to be careful with Hibernate and designing relational db, if used. I mean, it is important to make both
scaling and performance test and not rely on a unit test with e.g. 3 customers using H2. Relational db and JDBC is great, but
it is also important to be aware of other alternatives like Mongodb, Cassandra etc.. Many problems can be solved by
moving the business logic from the "read" side to the "write" side (CQRS), but then again, if the legacy system is using
batches, legacy problems can still exist (instead of using event driven and asynchronous systems). This is where people
start to use threads in their monoliths to improve performance (been there my self), but it does not solve the real problem.

### Eventual consistency
Apache Cassandra provides async future onSuccess callback upon node writes i.e. writing is guaranteed upon callback. But it does
introduce a *wait* on the future. Retry with maxAttempt for read, could also be introduced, in cases where reading is made
quickly after writing, but this *pull* strategy is not recommended.
A better design might be to use *Cassandra CDC* (Change Data Capture) i.e. a listener will be notified upon persistence - basically subscribing to *commit log* publisher receiving standardized events.
This way, no future wait is needed (onSuccess) and the design is clean from a async *push* based design view.
Own CDC listener can be made or Debezium can be used. CDC is not enabled by default on tables, but is done with *alter* setting *cdc = true*.
If using event-based design -and architechture, then probably a better solution, is to use *dual write* i.e. write async to Cassandra (no future wait) and publish event to either internal listener
(subscriber) or to a message broker (e.g. Apache Kafka). It is fine with the dual write (no transaction), since a step-engine will (could) handle the two
steps of writing to db (step 1) and publish the message (step 2). This way, the message will not be published, if persistence call
failed (the async "without wait" call can still throw an exception). Note that 24/7 recover with retry and error handling can
be performed with event-based step engines.
Apache Cassandra is able to make 9ms writes on average and even faster read because of MemTables (and Key -and Row Caches). Using either
of the above patterns, it is possible to keep the *eventual consistency* - which is good - but at the same time being notified consistently
using this *push* mechanism. So basically, with the above in mind, including event-based step-engine (using *Eventsourcing*) - complete
error handling 24/7, scaling and performance - with full data transparency in prod - can be achieved for real-time systems (have to be explained in more detail).
Lastly, there is no *holy grail* - every design -and architecture solution has it's pros and cons. It is just nice to know what is possible and what
different technologies offer - and then bring together all the ideas, and based on analysis, find the best solution(s). This way good decisions can be made.

# Warnings
In my opinion, it is important to remove compile and build warnings. I see it as technical debt. It is just waste of time
constantly confirming that some warning should be ignored - which actually is wrong. It is something that should be fixed.

# Spring-boot
Just some comments on Spring-boot and unit testing with Spring-boot.

## Port and Adapter
Spring-boot projects (microservice) quickly take in a lot of dependencies like:

* OpenAPI
* Apache Kafka
* Apache Cassandra
* Mongodb
* Redis
* Hibernate
* JDBC
* HikariCP
* Oracle, MSSQL, DB2 etc.

And it can quickly become a problem with raw types and library methods used all over the place. It will, most likely,
be a problem sooner or later, since the project will start to be tight coupled with raw library types and methods i.e.
having dependencies.

If the project is created by 1 developer it can be fine, but usually many developers are working on the same project, and
suddenly there is a high demand for extra communication of extra standards, like "remember not to do this and that".
Not only does it take time to communicate and describe (maybe in a Developer Guide, that has to be up to date), but also
the consequence is mind-shifting. I think it is important to remove mind-shifting, if possible, since we loose focus on
the task at hand i.e. making value for business.

One thing is to keep the production code clean. The other thing is to keep the unit test clean.
It is simply mandatory to have clean code, in order to have *clean unit test code*.
I believe that the *Port and Adapter* pattern can help with that in larger projects. But again, it is possible
to make a mess in the project, if people do not agree on some kind of standard.

## Unit test
The @SpringBootTest annotation can be both a wonderful thing, but also be used to test non-decoupled production code. And when that
happens, usually it will be the "whole world" that is started in every unit test. This wasting of time can be seen, when running
build pipelines.

Below are some unit test examples i.e. the way I make unit tests using Spring-boot.

### REST Controller unit test

    @AutoConfigureMockMvc
    @ContextConfiguration(classes = {RestControllerTest.class})
    @WebMvcTest
    public class RestControllerTest {

      @MockBean
      private Executor<SomeDomainType> executor;

      private final MockMvc;

      @Test
      public void test() {

          Mockito.when(.. on the executor interface..);

          mockMvc.perform
            .. and then
            .andExpect(..)
      }
    }

In the above, the *executor* interface, implemented by the use-case in the production code, is mocked - not a concrete implementation of the use-case.

### Use-case unit test
Again, if using the Port and Adapter pattern the unit test will be very simple only using domain model types and port
interfaces. Only the @Test and @Mockito annotations are used, thus the unit test will be simple (and fast).
The @SpringBootTest annotation will **not** be used in the use-case test.

### H2 unit test
In the example below, the SQL adapter class to be tested, is isolated. The inner static main class is running the test.
The @TestPropertySource annotation is used to bring in H2 properties. The @DataJpaTest annotation, which does not scan
@Service and @Component classes, could also be used.

    @SpringBootTest(classes = {SomeRepositoryTest.ApplicationTest.class})
    @TestPropertySource(
    properties = {
      "spring.flyway.enabled=false",
      "spring.datasource.driver-class-name=org.h2.Driver",
      "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect",
      "spring.datasource.url=jdbc:h2:mem:db;DB_CLOSE_DELAY=-1",
      "spring.datasource.username=sa",
      "spring.datasource.password=sa",
      "spring.jpa.hibernate.ddl-auto=create-drop"
      }
    )
    public class SomeRepositoryTest {

      @Autowired
      private SomeRepositoryInterface someRepositoryInterface;

      @Test
      public void test() {
      .. the test
      }

      @SpringBootApplication(scanBasePackages = "path to repository with entities")
      @EnableJpaRepositories("path to repository with entities")
      @EntityScan("maybe path to your model")
      public static class ApplicationTest {
        public static void main(String[] args) {
          SpringApplication.run(ApplicationTest.class, args);
        }
      }
    }

Since the above test only instantiates objects, that are needed, the test becomes fast. Ps each entity class could also be
imported individually for more performance.

### Using @TestConfiguration to configure beans
Sometimes it can be helpful to use the @TestConfiguration annotation to configure beans in the unit test. Again, 
only the classes needed to be tested, are included. The @Import annotation can be used to import classes also.

    @SpringBootTest(classes = {
    SomeTest.ApplicationTest.class,
    SomeTest.SomeTestConfig.class})
    public class SomeTest {

      @Autowired
      private SomeInterface someInterface;

      @Test
      public void test() {
      .. some test on the mocked interface
      }


      @TestConfiguration
      public static class SomeTestConfig {

        @Bean
        public SomeInterface someInterface() {
          return new SomeInterefaceImplementation();
        }
      }

      @SpringBootApplication
      public static class ApplicationTest {
        public static void main(String[] args) {
          SpringApplication.run(ApplicationTest.class, args);
        }
      }
    }

# Dependency Injection and Lombok
In most cases Lombok can be used. It can give some issues when extending classes, but I rarely extend classes - it is
just better to inject resources. Anyway, it becomes like this

    @RequiredArgsConstructor
    public class SomeClass {
      final private SomeInterface someInterface;

Ps the @NonNull (lombok) annotation can also be used.

# Requirements
Install these dependencies

* Java 21
* Maven (3.9.9) 

And set these environment variables

    JAVA_HOME
    M2_HOME

# Run
To build the application goto to project root and run

    mvn clean install

To run the application goto

    ../modules/core/target

And run

    java -jar format-jar-with-dependencies.jar --help

You will then get more detailed help.

# Test
Goto project root and run

    mvn test

# Debug

Set the classpath to

    core

The main class is located in

    com.pub.format.modules.core.Main

Set the parameters in "Program arguments" e.g.:

    --type left --width 5

You should now be able to debug the application.
