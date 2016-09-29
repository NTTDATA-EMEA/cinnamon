#### notes

## how to setup

1. add the dependency & the driver factory

2. You need to pass: 
   - the `browserProfile`
   - the `hubUrl` 
   
   `hubUrl` can be passed as follows: 
   
   1. full url as a parameter: 
   
   `-DhubUrl=http://<username>:<accesskey>@ondemand.saucelabs.com:80/wd/hub`
   
   2. Hardcode it in your conf file (NOT recommended)
   ` hubUrl="http://myUser:xxxxx-xxx-xx-xx@ondemand.saucelabs.com:80/wd/hub" ` 

   3. Use env vars to store your credentials: 
   `hubUrl="http://"${?sauceUsername}":"${?sauceAccessKey}"@ondemand.saucelabs.com:80/wd/hub"`
   
   Note that they are optional so the config will not fail in case you want to run the local driver. 
   Use `export sauceUsername=myuser` (also for the access key) in order to use the 3rd option. 

Saucelabs capabilities are listed here: 
https://wiki.saucelabs.com/display/DOCS/Test+Configuration+Options#TestConfigurationOptions-DeviceOrientation