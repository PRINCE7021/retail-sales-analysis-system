@echo off
REM build.bat - Windows version of build.sh
cd /d "%~dp0"
if not exist out mkdir out
dir /s /b src\*.java > sources.txt
javac -encoding UTF-8 -d out @sources.txt
if errorlevel 1 (
    echo Build failed. Please check the errors above.
) else (
    echo Build successful. Compiled classes are in the out folder.
)
del sources.txt
