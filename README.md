# Quarkus - Mongock
<!-- ALL-CONTRIBUTORS-BADGE:START - Do not remove or modify this section -->
[![All Contributors](https://img.shields.io/badge/all_contributors-1-orange.svg?style=flat-square)](#contributors-)
<!-- ALL-CONTRIBUTORS-BADGE:END -->

[![Version](https://img.shields.io/maven-central/v/io.quarkiverse.mongock/quarkus-mongock?logo=apache-maven&style=flat-square)](https://search.maven.org/artifact/io.quarkiverse.mongock/quarkus-mongock)

_Provide Mongock MongoDB Database migrations inside Quarkus applications_

This project embeds the Mongock database migration toolset in order to run migrations during quarkus startup.


To get started, add the dependency:

```xml
<dependency>
    <groupId>io.quarkiverse.mongock</groupId>
    <artifactId>quarkus-mongock</artifactId>
</dependency>
```

Add the following config parameters to your application properties:

```xml
quarkus.mongock.scan.package=com.rubean.phonepos.mongodb.migrations
quarkus.mongock.enabled=true
quarkus.mongock.health.enabled=true
```

Add the specific change units to the specified config path, see above.

```java
@ApplicationScoped
@ChangeUnit(id = "YOURID", order = "1", author = "mongock")
public class MongockChangeUnitExample {

    
    @Execution
    public void execution(ClientSession clientSession, MongoDatabase mongoDatabase) {

        Bson filter = Filters.eq("somekey", "somevalue");
        Bson update = Updates.set("somekey", "some value to be set");

        MongoCollection<Document> collection = mongoDatabase.getCollection("collectionname", Document.class);
        
        //please do not forget to pass in the clientSession to your updates - otherwise you break mongock transactional behaviour
        collection.updateMany(clientSession, filter, update);
        

    }

    @RollbackExecution
    public void rollbackExecution(ClientSession clientSession, MongoDatabase mongoDatabase) {
    //define your rollback here
    }

}
```


##Note:
current side effects, we need to overwrite/add the following dependency into the integrating quarkus project, as the quarkus bom is overwiting the library with an older 9.x version leading to mongock issues. 

I tried to mitigate the issue by forcing the updated library into the exension but the quarkus bom is overwriting it with the old version.

```xml
<dependency>
	<groupId>org.reflections</groupId>
	<artifactId>reflections</artifactId>
	<version>0.10.1</version>
</dependency>
```

<!-- 
For more details, check the complete [documentation](https://quarkiverse.github.io/quarkiverse-docs/quarkus-mongock/dev/index.html).
-->
## Contributors ✨

Thanks goes to these wonderful people ([emoji key](https://allcontributors.org/docs/en/emoji-key)):

<!-- ALL-CONTRIBUTORS-LIST:START - Do not remove or modify this section -->
<!-- prettier-ignore-start -->
<!-- markdownlint-disable -->
<table>
  <tr>
    <td align="center"><a href="https://github.com/janknobloch"><img src="https://avatars.githubusercontent.com/janknobloch?v=4?s=100" width="100px;" alt=""/><br /><sub><b>janknobloch</b></sub></a><br /><a href="https://github.com/quarkiverse/quarkus-mongock/commits?author=janknobloch" title="Code">💻</a> <a href="#maintenance-janknobloch" title="Maintenance">🚧</a></td>
  </tr>
</table>

<!-- markdownlint-restore -->
<!-- prettier-ignore-end -->

<!-- ALL-CONTRIBUTORS-LIST:END -->

This project follows the [all-contributors](https://github.com/all-contributors/all-contributors) specification. Contributions of any kind welcome!
