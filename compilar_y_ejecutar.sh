#!/usr/bin/env bash
# Compila el proyecto, ejecuta la demostración y las pruebas.
set -e
rm -rf out && mkdir out
javac -encoding UTF-8 -d out src/grafos/*.java
echo "----- Demostración -----"
java -cp out grafos.Main
echo
echo "----- Pruebas -----"
java -cp out grafos.Pruebas
