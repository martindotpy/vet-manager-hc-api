@REM Configure the server for windows
@echo off

@REM Title of the console
title %0

@REM Check if the correct number of arguments are provided
if "%~1"=="" (
    echo Usage: username password database_connection_url database_user_name database_password
    exit /b 1
)

if "%~2"=="" (
    echo Usage: username password database_connection_url database_user_name database_password
    exit /b 1
)

if "%~3"=="" (
    echo Usage: username password database_connection_url database_user_name database_password
    exit /b 1
)

if "%~4"=="" (
    echo Usage: username password database_connection_url database_user_name database_password
    exit /b 1
)

if "%~5"=="" (
    echo Usage: username password database_connection_url database_user_name database_password
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

    @REM Run the server in background
    start cmd /c mvn wildfly:run -DskipTests -q

    @REM Add user
    %SERVER_DIR%\bin\add-user.bat -u %1 -p %2 -g admin

    @REM Download the driver
    curl -L -o mysql-connector-j-8.4.0.tar.gz https://downloads.mysql.com/archives/get/p/3/file/mysql-connector-j-8.4.0.tar.gz
    tar -xvf mysql-connector-j-8.4.0.tar.gz
    move mysql-connector-j-8.4.0\mysql-connector-j-8.4.0.jar target\server\bin
    rmdir /s /q mysql-connector-j-8.4.0
    del mysql-connector-j-8.4.0.tar.gz

    @REM Add the data source
    %SERVER_DIR%\bin\jboss-cli.bat --connect --command="module add --name=com.mysql --resources=%SERVER_DIR%\bin\mysql-connector-j-8.4.0.jar --dependencies=javax.api,javax.transaction.api"
    %SERVER_DIR%\bin\jboss-cli.bat --connect --command="/subsystem=datasources/jdbc-driver=mysql:add(driver-name=mysql,driver-module-name=com.mysql,driver-class-name=com.mysql.cj.jdbc.Driver)"
    %SERVER_DIR%\bin\jboss-cli.bat --connect --command="data-source add --name=database --jndi-name=jdbc/mysql --driver-name=mysql --connection-url=%3 --user-name=%4 --password=%5 --jta=true --use-java-context=true --use-ccm=true --enabled=true --min-pool-size=5 --max-pool-size=30 --idle-timeout-minutes=15 --pool-prefill=true --pool-use-strict-min=true --query-timeout=4 --validate-on-match=true --background-validation=true --background-validation-millis=15000 --track-statements=NOWARN --new-connection-sql=""SELECT 1"" --check-valid-connection-sql=""SELECT 2"" --blocking-timeout-wait-millis=60000"

    @REM Kill the process using port 8080
    for /f "tokens=5" %%a in ('netstat -aon ^| findstr ":8080"') do (
        taskkill /PID %%a /F || echo Error killing process %%a
    )
) else (
    echo The server was not built yet. Run 'mvn clean install' first.
)
