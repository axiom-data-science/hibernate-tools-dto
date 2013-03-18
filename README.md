# hibernate-tools-dto

This project extends the Hibernate Tools project to add support for generation of 
a number of DTO related classes. The project's exporter and templates are used in
Hibernate Tools' hbmtemplate. The main impetus for creating the project was
the need to use Hibernate Tools to generate a complete set of classes for seamless
use in a Java/Hibernate/Flex project using the DTO pattern. It should also serve
as a good example for those looking to make their own custom Hibernate Tools
code generation output formats.

When creating DTOs and Actionscript VOs, complex data types that don't make sense for
direct transfer are skipped (geometries, CLOBs, etc). See the translator package for
details. These data types usually require the intervention of custom accessors that
can be placed in overridden createDTO and ingestDTO methods in custom POJO classes.
For example, for a geometry you could create custom DTO properties to hold encoded
polyline data, and write code in the POJO's createDTO method to translate the geometry
to an encoded polyline string. 

Another note: this project has only been tested on fairly clean and simple data structures
(no compound primary keys, no component classes). It does work with Hibernate classes with
inheritance, but Hibernate Tools has no special processing for creating merged documents from
inheriting classes.

The version prefix of this project matches the [Hibernate Tools](https://github.com/hibernate/hibernate-tools) version it depends on.

##Templates

* [pojoWithDTO](https://github.com/axiomalaska/hibernate-tools-dto/tree/master/src/main/resources/pojoWithDTO) - POJO with added createDTO and injestDTO methods.

* [pojoCustom](https://github.com/axiomalaska/hibernate-tools-dto/tree/master/src/main/resources/pojoCustom) - Simple empty POJO class that can extend generated POJOs and hold custom code.

* [DTO](https://github.com/axiomalaska/hibernate-tools-dto/tree/master/src/main/resources/DTO) - DTO object suitable for passing to client tier (no Hibernate proxies, lazy loading, etc)

* [DTOCustom](https://github.com/axiomalaska/hibernate-tools-dto/tree/master/src/main/resources/DTOCustom) - Simple empty DTO class that can extend generated DTOs and hold custom code.

* [actionscriptDTO](https://github.com/axiomalaska/hibernate-tools-dto/tree/master/src/main/resources/actionscriptDTO) - An Actionscript DTO that mirrors the Java DTO.

* [actionscriptDTOCustom](https://github.com/axiomalaska/hibernate-tools-dto/tree/master/src/main/resources/actionscriptDTOCustom) - Simple empty Actionscript DTO class then can extend generated classes.


##Additional configuration parameters

In addition to the normal hbmtemplate parameters, these additional parameters are available:

* packagename - Override the package name

* dtoPackageName - Set the DTO package name in POJOs/VOs (POJOs need to import the DTO classes)

* universalAncestor - All generated classes will extend this class (unless the classes use inheritance, in
                      which case the root class will inherit from the universal ancestor)

* remoteClassPackage - In Actionscript DTOs, override the RemoteClass alias package

* excludedTypesList - Exclude Hibernate types from Actionscript classes (comma separated)

* suffixDTOParentIds - Boolean that indicated whether foreign keys should be suffixed by their referenced class
                       name in DTOs. This is useful when each class has the same primary key name (e.g. "id"),
                       because otherwise there would be duplicate fields named "id" in a DTO with one or more
                       relationships. As an example, an Order class with a many to one relationship to a Customers
                       class, both having primary keys named id, would have its foreign key variable named as
                       idCustomer when suffixDTOParentIds is set to true.


##Example Maven usage

    <project ...> 
      ...
      <build>
        <plugins>
          <plugin>
            <groupId>org.codehaus.mojo</groupId>
            <artifactId>hibernate3-maven-plugin</artifactId>
            <executions>
        <execution>
          <id>hbm2hbmxml</id>
          <phase>initialize</phase>
          <goals>
            <goal>run</goal>
          </goals>            
          <configuration>
            <hibernatetool destdir="target/generated-resources/hibernate3">
              <jdbcconfiguration
          configurationfile="src/main/config/base.hibernate.cfg.xml"
          packagename="${project.groupId}.oikos.vo"
          reversestrategy="com.axiomalaska.oikos.reveng.CustomReverseEngineeringStrategy"
          />
              <hbm2hbmxml />
            </hibernatetool>
          </configuration>
        </execution>
            </executions>
            <dependencies>
        <dependency>
          <groupId>postgresql</groupId>
          <artifactId>postgresql</artifactId>
          <version>${postgres-version}</version>
        </dependency>             
        <dependency>
          <groupId>cglib</groupId>
          <artifactId>cglib</artifactId>
          <version>${cglib-version}</version>
        </dependency>
        <dependency>
          <groupId>com.axiomalaska</groupId>
          <artifactId>hibernate-tools-dto</artifactId>
          <version>${hibernate-tools-dto-version}</version>
        </dependency>   
        <dependency>  
          <groupId>org.hibernate</groupId>
          <artifactId>hibernate-tools</artifactId>
          <version>${hibernate-tools-version}</version>
          <exclusions>
            <exclusion>
              <artifactId>ant</artifactId>
              <groupId>ant</groupId>
            </exclusion>
          </exclusions>
        </dependency>
        <dependency>
          <groupId>org.hibernate</groupId>
          <artifactId>hibernate-core</artifactId>
          <version>${hibernate-version}</version>
        </dependency>
        <dependency>
          <groupId>org.slf4j</groupId>
          <artifactId>slf4j-simple</artifactId>
          <version>${slf4j-version}</version>
        </dependency>
        <dependency>
          <groupId>org.slf4j</groupId>
          <artifactId>slf4j-api</artifactId>
          <version>${slf4j-version}</version>             
        </dependency>
        <dependency>
          <groupId>org.apache.ant</groupId>
          <artifactId>ant</artifactId>
          <version>${ant-version}</version>
        </dependency>
            </dependencies>
          </plugin>
        </plugins>
      </build>    
      ...
    </project>
