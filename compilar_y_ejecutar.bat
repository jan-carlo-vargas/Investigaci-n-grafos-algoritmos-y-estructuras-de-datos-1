@echo off
REM Compila el proyecto, ejecuta la demostracion y las pruebas (Windows).
if exist out rmdir /s /q out
mkdir out
dir /s /b src\*.java > fuentes.txt
javac -encoding UTF-8 -d out @fuentes.txt
if errorlevel 1 exit /b 1
del fuentes.txt
echo ----- Demostracion -----
java -cp out grafos.Main
echo.
echo ----- Pruebas -----
java -cp out grafos.Pruebas
