# Vivero Hojas del Sur · Simulacro EP2 (DSY1102)

Simulacro de la **Evaluación Parcial 2** de *Programación Orientada a Objetos*, con el mismo formato que las Formas de la prueba: un proyecto base con el modelo ya implementado y un enunciado por secciones.

| | |
|---|---|
| **Enunciado** | [`ENUNCIADO.md`](ENUNCIADO.md) |
| **Proyecto base** | Este proyecto Maven (`LEEME.txt` explica cómo abrirlo y qué ofrece el modelo) |
| **Tecnologías** | Java 21 · Maven · JavaFX 21.0.6 · Gson |
| **Tiempo** | 3 bloques, individual |

## Ramas

- `main`: **condiciones de la prueba**, con el mismo formato que los proyectos base de la evaluación (versión 3.1). Trae `Launcher`, `App`, el modelo (`cl.duoc.vivero.model`), un `pom.xml` con JavaFX y un comentario donde va Gson, y una carpeta `view` vacía. La vista `main-view.fxml`, los controladores, el DAO, el Repository y la dependencia de Gson se construyen como parte de la evaluación. No hay `module-info.java`: el proyecto corre con classpath.
- `solucion`: implementación de referencia y `GUIA.md` con la resolución paso a paso.

## Cómo ejecutar la solución

```bash
git checkout solucion
mvn javafx:run
```

La guía de resolución (`GUIA.md`) y el plan de tiempos del taller (`TALLER.md`) están en la rama `solucion`.

## Cómo practicar

1. Clona el repositorio (rama `main`) y ábrelo como proyecto Maven en IntelliJ IDEA con JDK 21.
2. Lee `ENUNCIADO.md` y resuélvelo sin mirar la rama `solucion`.
3. Compara con la solución: `git diff main solucion`.

El modelo no se modifica. Los archivos `interiores.json` y `exteriores.json` los genera la aplicación al ejecutarse y no se versionan.
