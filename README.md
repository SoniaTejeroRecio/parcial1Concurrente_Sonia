# Proyecto Simulación Tablero de Galton - Sonia Tejero Recio

## Comenzamos 🚀

Este proyecto está desarrollado completamente en Java utilizando **JavaFX** para la creación de una interfaz gráfica interactiva. El objetivo de la aplicación es simular un **Tablero de Galton** (también conocido como "Tablero de Clavos de Galton") y mostrar visualmente cómo caen las bolas a través de una serie de clavos, generando una **distribución normal** o curva de campana, basada en los rebotes de las bolas.

## ¿Cómo he estructurado el proyecto?

- **GaltonBoard**: Clase principal de la aplicación que maneja la interfaz gráfica y la simulación del tablero. Aquí se crean los clavos, las bolas y los contenedores inferiores para visualizar la distribución de las bolas.
  
- **Productor**: Clase que simula el proceso de producción de las bolas. Este hilo se encarga de "lanzar" bolas desde la parte superior del tablero de manera continua.

- **Consumidor**: Clase que representa el consumidor que actúa sobre el buffer compartido. Sin embargo, en el contexto de la simulación del Tablero de Galton, este hilo es menos relevante para la visualización directa.

- **BufferCompartido**: Clase que implementa un buffer de bolas para coordinar la producción y el consumo de componentes. Es una implementación de cola para almacenar y manejar la sincronización de las bolas.

- **Scheduler**: Clase que se utiliza para controlar el turno de los productores, asegurando que el lanzamiento de bolas sea ordenado y continuo.

## Pantallas

### GaltonBoard

- **Descripción**: Pantalla principal de la simulación del Tablero de Galton.
- **Funcionalidades**:
  - Generación de una cuadrícula de clavos dispuesta en forma triangular.
  - Simulación de la caída de bolas a través de los clavos, mostrando cómo rebotan y caen en los contenedores inferiores.
  - Cada bola que cae en un contenedor incrementa el contador visible sobre el contenedor, lo que permite ver la distribución de las bolas en tiempo real.
  - Las bolas rebotan de forma aleatoria (izquierda o derecha) en cada clavo, simulando la ley de probabilidades.

### Productor

- **Descripción**: Clase que simula la producción de bolas que caen por el tablero.
- **Funcionalidades**:
  - Lanza bolas desde el centro del tablero.
  - Controla la velocidad de lanzamiento de las bolas.
  - Llama al método `lanzarBola()` en la clase `GaltonBoard` para simular el movimiento de las bolas.

### Consumidor

- **Descripción**: Aunque es parte del diseño original, la clase Consumidor no tiene un papel tan destacado en la visualización de la simulación del Tablero de Galton.
- **Funcionalidades**:
  - Consumo de componentes del buffer compartido en un ciclo continuo.

### BufferCompartido

- **Descripción**: Estructura que maneja la sincronización entre la producción de bolas y su lanzamiento en la simulación.
- **Funcionalidades**:
  - Implementa un sistema FIFO (First In, First Out) que asegura que las bolas se gestionen correctamente antes de ser lanzadas en la simulación.

### Scheduler

- **Descripción**: Controlador de turnos que asegura que los productores (bolas) se lancen de manera secuencial y sin colisiones en el sistema.
- **Funcionalidades**:
  - Controla el ciclo de producción de bolas en el tablero.
  - Asegura que los hilos de los productores trabajen de forma sincronizada.

## Simulación del Tablero de Galton

1. Las bolas se lanzan desde la parte superior del tablero y caen de forma natural debido a la gravedad simulada.
2. Cada vez que una bola golpea un clavo, se desvía aleatoriamente hacia la izquierda o la derecha.
3. Finalmente, la bola cae en uno de los contenedores inferiores, aumentando el contador en dicho contenedor.
4. Después de varias bolas lanzadas, se observará que los contenedores centrales tienden a tener más bolas, generando así una **distribución normal** o **campana de Gauss**.

## ¿Qué se necesita para ejecutar el proyecto? 💻

- **Java 19** o superior.
- **JavaFX** para la interfaz gráfica.
- Un entorno de desarrollo integrado (IDE) como **IntelliJ IDEA** para compilar y ejecutar el proyecto.

## Cómo ejecutar la aplicación

1. Clonar el repositorio.
2. Asegurarse de tener configurado Java y JavaFX correctamente en su IDE.
3. Ejecutar la clase `Main` desde su entorno de desarrollo.

## Corrección 🖇️

**Repositorio de GitHub:** [Repositorio](https://github.com/SoniaTejeroRecio/parcial1Concurrente_Sonia.git)


