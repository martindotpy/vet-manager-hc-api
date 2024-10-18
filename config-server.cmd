@REM Configure the server for windows
@echo off

@REM Title of the console
title %0

@REM Check if the correct number of arguments are provided
if "%~1"=="" (
    echo Usage: %0 username
    exit /b 1
)

if "%~2"=="" (
    echo Usage: %0 password
    exit /b 1
)

if "%~3"=="" (
    echo Usage: %0 database connection url
    exit /b 1
)

if "%~4"=="" (
    echo Usage: %0 database user name
    exit /b 1
)

if "%~5"=="" (
    echo Usage: %0 database password
    exit /b 1
)

@REM Set the directories
set SERVER_DIR=.\target\server
set MODULE_PATH=%SERVER_DIR%\modules\system\layers\base\com\fasterxml\jackson\jakarta\jackson-jakarta-json-provider\main
set MODULE_FILE=%MODULE_PATH%\module.xml

if exist %SERVER_DIR% (
    @REM Add the necessary modules for the server
    > "%MODULE_FILE%" (
        echo ^<?xml version="1.0" encoding="UTF-8"?^>
        echo ^<!--
        echo   ~ Copyright The WildFly Authors
        echo   ~ SPDX-License-Identifier: Apache-2.0
        echo --^>
        echo ^<module name="com.fasterxml.jackson.jakarta.jackson-jakarta-json-provider" xmlns="urn:jboss:module:1.9"^>
        echo     ^<resources^>
        echo         ^<resource-root path="jackson-jakarta-rs-json-provider-2.15.2.jar" /^>
        echo         ^<resource-root path="jackson-jakarta-rs-base-2.15.2.jar" /^>
        echo         ^<resource-root path="jackson-module-jakarta-xmlbind-annotations-2.15.2.jar" /^>
        echo     ^</resources^>
        echo     ^<dependencies^>
        echo         ^<module name="com.fasterxml.jackson.core.jackson-annotations" /^>
        echo         ^<module name="com.fasterxml.jackson.core.jackson-core" /^>
        echo         ^<module name="com.fasterxml.jackson.core.jackson-databind" /^>
        echo         ^<module name="jakarta.ws.rs.api" /^>
        echo         ^<module name="jakarta.xml.bind.api" /^>
        echo         ^<module name="org.jboss.weld.core" /^>
        echo         ^<module name="org.jboss.weld.spi" /^>
        echo         ^<module name="org.jboss.weld.api" /^>
        echo     ^</dependencies^>
        echo ^</module^>
    )

    @REM Run the server in other process
    

    @REM Add user
    .\target\server\bin\add-user start -u %1 -p %2 -g admin

    @REM Download the driver
    curl -O https://downloads.mysql.com/archives/get/p/3/file/mysql-connector-j-8.0.33.tar.gz
    tar -xvf mysql-connector-java-8.0.33.tar.gz
    move mysql-connector-java-8.0.33\mysql-connector-java-8.0.33.jar .\target\server\bin
    rmdir /s /q mysql-connector-java-8.0.33
    del mysql-connector-java-8.0.33.tar.gz

    @REM Add the data source
    .\target\server\bin\jboss-cli.bat --connect --command="module add --name=com.mysql --resources=mysql-connector-java-8.0.33.jar --dependencies=javax.api,javax.transaction.api"
    .\target\server\bin\jboss-cli.bat --connect --command="/subsystem=datasources/jdbc-driver=mysql:add(driver-name=mysql,driver-module-name=com.mysql,driver-class-name=com.mysql.cj.jdbc.Driver)"
    .\target\server\bin\jboss-cli.bat --connect --command="data-source add --name=database --jndi-name=java:/MySQLD8 --driver-name=mysql --connection-url=%3 --user-name=%4 --password=%5"
    .\target\server\bin\jboss-cli.bat --connect --command="data-source enable --name=database"
) else (
    echo The server was not built yet. Run 'mvn clean install' first.
)