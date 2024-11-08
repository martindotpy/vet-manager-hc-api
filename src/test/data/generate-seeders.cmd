@echo off
setlocal enabledelayedexpansion

set API_URL="http://localhost:8080/api/v0"

@REM Get the JWT token
for /f "tokens=*" %%i in ('curl -X "POST" "%API_URL%/auth/login" ^
    -H "accept: application/json" ^
    -H "Content-Type: application/json" ^
    -d "{""email"": ""admin@admin.com"",""password"": ""admin""}"') do (
    set response=%%i
)

for /f "tokens=6 delims=: " %%i in ('echo !response! ^| findstr /i "jwt"') do (
    set JWT=%%i
    set JWT=!JWT:~1,-3!
)

@REM Create categories
curl -X "POST" "%API_URL%/category" ^
    -H "accept: application/json" ^
    -H "Authorization: Bearer %JWT%" ^
    -H "Content-Type: application/json" ^
    -d "{""name"": ""Juguetes""}"
curl -X "POST" "%API_URL%/category" ^
    -H "accept: application/json" ^
    -H "Authorization: Bearer %JWT%" ^
    -H "Content-Type: application/json" ^
    -d "{""name"": ""Vacunas""}"
curl -X "POST" "%API_URL%/category" ^
    -H "accept: application/json" ^
    -H "Authorization: Bearer %JWT%" ^
    -H "Content-Type: application/json" ^
    -d "{""name"": ""Alimentos""}"
curl -X "POST" "%API_URL%/category" ^
    -H "accept: application/json" ^
    -H "Authorization: Bearer %JWT%" ^
    -H "Content-Type: application/json" ^
    -d "{""name"": ""Alimentos""}"

@REM Create products
curl -X "POST" "%API_URL%/product" ^
    -H "accept: application/json" ^
    -H "Authorization: Bearer %JWT%" ^
    -H "Content-Type: application/json" ^
    -d "{""name"": ""Vacuna"",""price"": 2.10,""description"": ""Vacuna génerica"",""quantity"": 1,""categories"": [{""id"": 1, ""name"": ""Vacuna""}]}"
