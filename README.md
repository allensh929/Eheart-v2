# eheart

This application was generated using JHipster 3.12.2, you can find documentation and help at [https://jhipster.github.io/documentation-archive/v3.12.2](https://jhipster.github.io/documentation-archive/v3.12.2).

## Development

Before you can build this project, you must install and configure the following dependencies on your machine:
1. [Node.js][]: We use Node to run a development web server and build the project.
   Depending on your system, you can install Node either from source or as a pre-packaged bundle.

After installing Node, you should be able to run the following command to install development tools (like
[Bower][] and [BrowserSync][]). You will only need to run this command when dependencies change in package.json.

    npm install

We use [Gulp][] as our build system. Install the Gulp command-line tool globally with:

    npm install -g gulp-cli

Run the following commands in two separate terminals to create a blissful development experience where your browser
auto-refreshes when files change on your hard drive.

    ./mvnw
    gulp

Bower is used to manage CSS and JavaScript dependencies used in this application. You can upgrade dependencies by
specifying a newer version in `bower.json`. You can also run `bower update` and `bower install` to manage dependencies.
Add the `-h` flag on any command to see how you can use it. For example, `bower update -h`.

For further instructions on how to develop with JHipster, have a look at [Using JHipster in development][].

## Building for production

To optimize the eheart application for production, run:

    ./mvnw -Pprod clean package

This will concatenate and minify the client CSS and JavaScript files. It will also modify `index.html` so it references these new files.
To ensure everything worked, run:

    java -jar target/*.war

Then navigate to [http://localhost:8080](http://localhost:8080) in your browser.

Refer to [Using JHipster in production][] for more details.

## Testing

To launch your application's tests, run:

    ./mvnw clean test

### Client tests

Unit tests are run by [Karma][] and written with [Jasmine][]. They're located in `src/test/javascript/` and can be run with:

    gulp test


### Other tests

Performance tests are run by [Gatling][] and written in Scala. They're located in `src/test/gatling` and can be run with:

    ./mvnw gatling:execute

For more information, refer to the [Running tests page][].

## Using Docker to simplify development (optional)

You can use Docker to improve your JHipster development experience. A number of docker-compose configuration are available in the `src/main/docker` folder to launch required third party services.
For example, to start a mysql database in a docker container, run:

    docker-compose -f src/main/docker/mysql.yml up -d

To stop it and remove the container, run:

    docker-compose -f src/main/docker/mysql.yml down

You can also fully dockerize your application and all the services that it depends on.
To achieve this, first build a docker image of your app by running:

    ./mvnw package -Pprod docker:build

Then run:

    docker-compose -f src/main/docker/app.yml up -d

For more information refer to [Using Docker and Docker-Compose][], this page also contains information on the docker-compose sub-generator (`yo jhipster:docker-compose`), which is able to generate docker configurations for one or several JHipster applications.

## Continuous Integration (optional)

To set up a CI environment, consult the [Setting up Continuous Integration][] page.

[JHipster Homepage and latest documentation]: https://jhipster.github.io
[JHipster 3.12.2 archive]: https://jhipster.github.io/documentation-archive/v3.12.2

[Using JHipster in development]: https://jhipster.github.io/documentation-archive/v3.12.2/development/
[Using Docker and Docker-Compose]: https://jhipster.github.io/documentation-archive/v3.12.2/docker-compose
[Using JHipster in production]: https://jhipster.github.io/documentation-archive/v3.12.2/production/
[Running tests page]: https://jhipster.github.io/documentation-archive/v3.12.2/running-tests/
[Setting up Continuous Integration]: https://jhipster.github.io/documentation-archive/v3.12.2/setting-up-ci/

[Gatling]: http://gatling.io/
[Node.js]: https://nodejs.org/
[Bower]: http://bower.io/
[Gulp]: http://gulpjs.com/
[BrowserSync]: http://www.browsersync.io/
[Karma]: http://karma-runner.github.io/
[Jasmine]: http://jasmine.github.io/2.0/introduction.html
[Protractor]: https://angular.github.io/protractor/

## ssh server

连接服务器 ssh root@139.224.44.210 输入密码

##Database log

ALTER TABLE `eheart`.`product` 
ADD COLUMN `is_new` TINYINT(1) NULL DEFAULT 0 AFTER `last_modified_by`,
ADD COLUMN `favorite` TINYINT(1) NULL DEFAULT 0 AFTER `is_new`;


ALTER TABLE `eheart`.`doctor` 
CHANGE COLUMN `doctor_placeholder_3` `photo` VARCHAR(255) NULL DEFAULT NULL ;

CREATE TABLE `clinic` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `img` varchar(255) DEFAULT NULL,
  `created_date` timestamp NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `last_modified_date` timestamp NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `clinic_hospital` (
  `hospitals_id` bigint(20) NOT NULL,
  `clinics_id` bigint(20) NOT NULL,
  PRIMARY KEY (`clinics_id`,`hospitals_id`),
  KEY `fk_clinic_hospital_hospitals_id` (`hospitals_id`),
  CONSTRAINT `fk_clinic_hospital_clinics_id` FOREIGN KEY (`clinics_id`) REFERENCES `clinic` (`id`),
  CONSTRAINT `fk_clinic_hospital_hospitals_id` FOREIGN KEY (`hospitals_id`) REFERENCES `hospital` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `product_sub_category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `img` varchar(255) DEFAULT NULL,
  `sub_category_placeholder_1` varchar(255) DEFAULT NULL,
  `sub_category_placeholder_2` varchar(255) DEFAULT NULL,
  `created_date` timestamp NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `last_modified_date` timestamp NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `super_category_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_product_sub_category_super_category_id` (`super_category_id`),
  CONSTRAINT `fk_product_sub_category_super_category_id` FOREIGN KEY (`super_category_id`) REFERENCES `product_category` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


alter TABLE `department`
add clinic_id bigint(20) null;

alter TABLE `department`
add img varchar(255) null;

alter TABLE `hospital`
add img varchar(255) null;

alter TABLE `product_category`
add img varchar(255) null;


## Deploy Dev
mvn -DskipTests=true -Pdev package

-DskipTests，不执行测试用例，但编译测试用例类生成相应的class文件至target/test-classes下。

-Dmaven.test.skip=true，不执行测试用例，也不编译测试用例类。

scp ./eheart-0.0.1-SNAPSHOT.war root@139.224.44.210:/opt/tomcat/

sh /opt/tomcat/script.sh 



## Mysql

vi /etc/mysql/my.cnf

sudo service mysql restart

授权用户能进行远程连接

mysql -u root -p
输入mysql密码

grant all privileges on *.* to root@'%' identified by 'EheartSh+127' with grant option;
flush privileges;

## Tomcat
/opt/tomcat


## Nginx

sudo /etc/init.d/nginx stop/start
