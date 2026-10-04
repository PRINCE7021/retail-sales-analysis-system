@echo off
REM run.bat - Windows version of run.sh (run build.bat first)
cd /d "%~dp0"
if not exist out call build.bat
java -Dfile.encoding=UTF-8 -cp out com.retail.sales.Main
