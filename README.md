# Actividad Práctica 01 – Procesamiento Secuencial y Paralelo de Textos

## Descripción

Aplicación desarrollada en Java para comparar el procesamiento secuencial
y paralelo de un archivo de texto utilizando la clase Thread.

## Objetivo

Comparar el rendimiento del procesamiento secuencial frente al procesamiento
paralelo utilizando 2 y 4 hilos.

## Funcionalidades

- Contar el número total de palabras.
- Contar las apariciones de "Quijote".
- Contar palabras que contienen "ción".
- Procesamiento sin distinguir mayúsculas y minúsculas.
- Procesamiento secuencial.
- Procesamiento paralelo con 2 hilos.
- Procesamiento paralelo con 4 hilos.
- Medición mediante System.nanoTime().
- Cálculo del tiempo promedio.
- Cálculo del Speedup.

## Tecnologías

- Java
- Thread
- System.nanoTime()
- Visual Studio Code
- Kubuntu

## Estructura

CPARALELA/ActividadProcesamientoTextos/
│
├── src/
│   ├── Main.java
│   ├── Resultado.java
│   ├── ProcesadorTexto.java
│   ├── Secuencial.java
│   ├── ProcesadorThread.java
│   └── Paralelo.java
│
├── datos/
│   ├── DonQuijote.txt
│   └── DonQuijote_100x.txt
│
│
├── .gitignore
│
└── README.md

## Compilacion
Entrar en la carpeta raiz y ejecutar :javac -d out src/*.java
Luego : java -cp out Main
