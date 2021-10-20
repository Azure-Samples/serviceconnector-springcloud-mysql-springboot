# Using Service Connector to connect Azure Spring Cloud with Azure Database for MySQL

This sample project is used for Service Connector quickstart.



## 1. Prerequisites

1. Have an Azure account with an active subscription. [Create an account for free](https://azure.microsoft.com/free/?ref=microsoft.com&utm_source=microsoft.com&utm_medium=docs&utm_campaign=visualstudio).
2. Install Java 8 or 11 </a>.
3. Install the <a href="/cli/azure/install-azure-cli" target="_blank">Azure CLI</a> 2.18.0 or higher, with which you run commands in any shell to provision and configure Azure resources.

---

Check that your Azure CLI version is 2.18.0 or higher:

```azurecli
az --version
```

If you need to upgrade, try the `az upgrade` command (requires version 2.11+) or see <a href="/cli/azure/install-azure-cli" target="_blank">Install the Azure CLI</a>.

Then sign in to Azure through the CLI:

```azurecli
az login
```

This command opens a browser to gather your credentials. When the command finishes, it shows JSON output containing information about your subscriptions.

Once signed in, you can run Azure commands with the Azure CLI to work with resources in your subscription.

Having issues? [Let us know](https://aka.ms/DjangoCLITutorialHelp).

## 2. Clone or download the sample app


Clone the sample repository:

```terminal
git clone https://github.com/Azure-Samples/serviceconnector-springcloud-mysql-springboot
```

Go to the root folder of repository:

```terminal
cd serviceconnector-springcloud-mysql-springboot
```

## 3. Create Azure Database for MySQL

https://docs.microsoft.com/en-us/azure/mysql/quickstart-create-mysql-server-database-using-azure-portal

## 4. Create a Spring Cloud application

https://docs.microsoft.com/en-us/azure/spring-cloud/quickstart?tabs=Azure-CLI&pivots=programming-language-java#provision-an-instance-of-azure-spring-cloud

## 5. Build and deploy the app

1. Sign in to Azure and choose your subscription.

Azure CLI

```
az login

az account set --subscription <Name or ID of a subscription from the last step>
```

2. Create the app with public endpoint assigned. If you selected Java version 11 when generating the Spring Cloud project, include the --runtime-version=Java_11 switch.

```
az spring-cloud app create -n hellospring -s <service instance name> -g <resource group name> --assign-endpoint true
```


3. Create connection

CLI command:

```
az spring-cloud connection create mysql -g <SpringCloud resource group> --service <SpringCloud service> --app
        <SpringCloud app> --tg <mysql resource group> --server <mysql server name> --database <mysql database> --secret name=<username> secret=<password>
```

4. Build the project using maven

```
mvn clean package -DskipTests 
```

5. Deploy the Jar file for the app ( target/demo-0.0.1-SNAPSHOT.jar ):

```
az spring-cloud app deploy -n hellospring -s <service instance name> -g <resource group name>  --artifact-path target/demo-0.0.1-SNAPSHOT.jar
```

6. Query app status after deployments with the following command.

Azure CLI

```terminal
az spring-cloud app list -o table
```

```az-cli
Name               Location    ResourceGroup    Production Deployment    Public Url                                           Provisioning Status    CPU    Memory    Running Instance    Registered Instance    Persistent Storage
-----------------  ----------  ---------------  -----------------------  ---------------------------------------------------  ---------------------  -----  --------  ------------------  ---------------------  --------------------
hellospring         eastus    <resource group>   default                                                                       Succeeded              1      2         1/1                 0/1                    -
```