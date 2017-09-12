# Slack Tightener
Tightens some of the slack in Slack community version. The main feature currently being integration with our corporate Active Directory server to validate Slack users have active corporate accounts.

## Running the application
The application is designed to be built (and optionally run) using Maven and Java 8. As such you will need Maven and the Java 8 JDK installed.

* To pull the code from GitHub: `git clone git@github.com:capgemini-psdu/slack-tightener.git`
* To build the application JAR: `mvn clean package`
* To run the application (using Maven): `mvn spring-boot:run`
* To run the application (executing the fat JAR): `java -jar ./target/slack-tightener-*.jar`

## Installing the application as an OS service

We recommend that you configure the application to run via upstart so that it automatically restarts after e.g. its EC2 instance is rebooted if it is deployed to AWS.

Create the upstart job configuration:

`sudo vi /etc/init/slack-tightener.conf`

Example contents for this file are supplied below (note that the ec2-user's bash profile is run to add the Maven bin directory to the path and set the JAVA_HOME environment variable):

```
description "slack-tightener"

start on (runlevel [345] and started network)
stop on (runlevel [!345] and stopping network)

respawn limit 99 5

script
  cd /home/ec2-user/slack-tightener
  . /home/ec2-user/.bash_profile
  exec mvn spring-boot:run >> /var/log/slack-tightener.log 2>&1
end script
```

You should then be able to start and stop the application using upstart e.g.:

```
sudo start slack-tightener
sudo stop slack-tightener
sudo restart slack-tightener
```

## Customising Azure Active Directory authentication integration
The configuration bundled in this repo by default uses an Azure AD application that is set up and ready to go for the application deployed to http://slack-tightener.capgemini-psdu.com. If you want to run the application anywhere else then you must follow the step below.

Go to http://portal.azure.com and sign in.

From the left hand menu select Azure Active Directory and then App Registrations from the next left hand menu.

Select New application registration and enter a Name for your application. Keep the Application type as Web app/API. Enter a Sign-on URL, this will be the URL where the users can sign on i.e. http://<your_host>:<your_port>/tightening.

You have now created an app on the Microsoft Azure Active Directory but we still need to further configure it.

Set a reply URL to http://<your_host>:<your_port>/tightening/authorize so that active directory can return control to the slack-tightener app upon successful login.

Next we need to set the required permissions, so click on the required permissions section and tick "Read all users' basic profiles". Once you’ve selected done this click save.

We need to generate a client secret key to access Azure. Click on Keys and enter a name for the key and an expiry date. When you click save it will generate a key for you. You will have one chance to make a note of this key as when you leave the page the key is not visible.

Lastly, change the following values in the src/main/resources/application.yaml file:

The `active-directory.application.application-id` should be set to the "Application ID" of your new Azure AD app, found on its "Properties" tab.

The `active-directory.application.client-secret-key` should be set to the client secret key you noted down above.

The `redirect.host` should be set to the hostname that you intend to deploy the application to.
