# Using Service Connector to connect Azure Spring Cloud with Azure Database for MySQL

This sample project is used to connecting Azure Spring Cloud with Azure Database for MySQL by Service Connector service.


## 1. Prerequisites

1. Have an Azure account with an active subscription. [Create an account for free](https://azure.microsoft.com/free/?ref=microsoft.com&utm_source=microsoft.com&utm_medium=docs&utm_campaign=visualstudio).
2. Install Java 8 or 11 </a>.
3. Install the <a href="/cli/azure/install-azure-cli" target="_blank">Azure CLI</a> 2.18.0 or higher, with which you run commands in any shell to provision and configure Azure resources.


## 2. Create Azure Database for MySQL

https://docs.microsoft.com/en-us/azure/mysql/quickstart-create-mysql-server-database-using-azure-portal

## 3. Create Azure Spring Cloud

https://docs.microsoft.com/en-us/azure/spring-cloud/quickstart?tabs=Azure-CLI&pivots=programming-language-java#provision-an-instance-of-azure-spring-cloud

## 4. Build and deploy the app

1. Clone the sample repository:

```terminal
git clone https://github.com/Azure-Samples/serviceconnector-springcloud-mysql-springboot
```

Go to the root folder of repository:

```terminal
cd serviceconnector-springcloud-mysql-springboot
```

2. Build the project locally using maven

```
mvn clean package -DskipTests 
```

3. Sign in to Azure and choose your subscription.

Azure CLI command:

```
az login

az account set --subscription <Name or ID of a subscription from the last step>
```

4. Create Azure Spring Cloud app with public endpoint assigned. If you selected Java version 11 when generating the Spring Cloud project, include the --runtime-version=Java_11 switch.

```
az spring-cloud app create -n hellospring -s <service instance name> -g <resource group name> --assign-endpoint true
```


5. Create connection between Azure Spring Cloud and Azure database for MySQL

Azure CLI command

```terminal
az spring-cloud connection create mysql -g <SpringCloud resource group> --service <SpringCloud service> --app
        <SpringCloud app> --tg <mysql resource group> --server <mysql server name> --database <mysql database> --secret name=<username> secret=<password>
```



6. Deploy the Jar file to Azure Spring Cloud app ( target/demo-0.0.1-SNAPSHOT.jar ):

```
az spring-cloud app deploy -n hellospring -s <service instance name> -g <resource group name>  --artifact-path target/demo-0.0.1-SNAPSHOT.jar
```

7. Query Azure app status after deployments with the following command.

Azure CLI command

```terminal
az spring-cloud app list -g <resource group name> -s <service instance name> -o table
```

```az-cli
Name               Location    ResourceGroup    Production Deployment    Public Url                                           Provisioning Status    CPU    Memory    Running Instance    Registered Instance    Persistent Storage
-----------------  ----------  ---------------  -----------------------  ---------------------------------------------------  ---------------------  -----  --------  ------------------  ---------------------  --------------------
hellospring         eastus    <resource group>   default                                                                       Succeeded              1      2         1/1                 0/1                    -
```

8. Navigate to the app on Azure by `Public Url` returned in above table.

## 5. Clean up
Delete Azure resources created in the sample by deleting the resource group.
```
az group delete -g <resource group name>
```

## 6. Contributing and License
This project welcomes contributions and suggestions.  Most contributions require you to agree to a
Contributor License Agreement (CLA) declaring that you have the right to, and actually do, grant us
the rights to use your contribution. For details, visit https://cla.microsoft.com.

When you submit a pull request, a CLA-bot will automatically determine whether you need to provide
a CLA and decorate the PR appropriately (e.g., label, comment). Simply follow the instructions
provided by the bot. You will only need to do this once across all repos using our CLA.

This project has adopted the [Microsoft Open Source Code of Conduct](https://opensource.microsoft.com/codeofconduct/).
For more information see the [Code of Conduct FAQ](https://opensource.microsoft.com/codeofconduct/faq/) or
contact [opencode@microsoft.com](mailto:opencode@microsoft.com) with any additional questions or comments.