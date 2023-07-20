@echo off
set RED=[0;31m
set NC=[0m

if "%~1"=="" (
    echo Please input a .pyc file
    echo Example: You can use command python -m compileall demo.py to get a .pyc file of demo.py
    exit /b 1
)

if not exist "%~1" (
    echo Please input a .pyc file
    echo Example: You can use command python -m compileall demo.py to get a .pyc file of demo.py
    exit /b 1
)

for %%F in ("%~1") do (
    set "filename=%%~nxF"
    set "extension=%%~xF"
)

if /I not "%extension%"=="pyc" (
    echo %RED%Please input a .pyc file
    echo Example: You can use command python -m compileall demo.py to get a .pyc file of demo.py%NC%
    exit /b 1
)

java -jar target/jpvm-1.0-SNAPSHOT-jar-with-dependencies.jar %~1
