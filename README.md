# Slack Tightener
Tightens some of the slack in Slack community version. The main feature currently being integration with our corporate Active Directory server to validate Slack users have active corporate accounts.

## Customising Azure Active Directory authentication integration
The configuration bundled in this repo by default uses an Azure AD application that is set up and ready to go for applications running on localhost:8888. If you want to run the application anywhere else then you must follow the step below.

Go to http://portal.azure.com and sign in.

From the left hand menu select Azure Active Directory and then App Registrations from the next left hand menu.

Select New application registration and enter a Name for your application. Keep the Application type as Web app/API. Enter a Sign-on URL, this will be the URL where the users can sign on i.e. http://<your_host>:8888/tightening.

You have now created an app on the Microsoft Azure Active Directory but we still need to further configure it.

Set a reply URL to http://<your_host>:8888/tightening/authorize so that active directory can return control to the slack-tightener app upon successful login.

Next we need to set the required permissions, so click on the required permissions section and tick "Read all users' basic profiles". Once you’ve selected done this click save.

We need to generate a client secret key to access Azure. Click on Keys and enter a name for the key and an expiry date. When you click save it will generate a key for you. You will have one chance to make a note of this key as when you leave the page the key is not visible.

Lastly, change the following values in the src/main/resources/application.yaml file:

active-directory.application.application-id: <the "Application ID" for your new Azure AD app, found on the "Properties" tab.
active-directory.application.client-secret-key: <the client secret key you noted down above>

redirect.host: <the hostname that you intend to deploy the application to>