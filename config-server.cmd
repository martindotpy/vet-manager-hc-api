@REM Configure the server for windows
@echo off

@REM Title of the console
title %0

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
) else (
    echo The server was not built yet. Run 'mvn clean install' first.
)
