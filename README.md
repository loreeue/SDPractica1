
# Práctica 1 Sistemas Distribuidos URJC

El objetivo de esta práctica es tener una aplicación web que conste de una interfaz web (formularios) y una API REST.

Nuestra práctica consiste en una web de gestión de eventos llamada **TicketSphere**, la cual tiene 3 entidades independientes, evento, localización y usuario. En la interfaz web hemos implementado mediante formularios todas las operaciones CRUD (Create, Read, Update y Delete), y en la API REST hemos implementado todas las operaciones CRUD + PATCH.

## Authors

- [@s-v-x](https://github.com/s-v-x)
- [loreeue](https://github.com/loreeue)

## API REST - Event

| Solicitud HTTP  | EndPoint | Descripción |
| ------------- | ------------- | ------------- |
| GET ALL  | /event  | Obtiene todos los eventos |
| GET  | /event/1  | Obtiene el evento 1 |
| POST | /event | Añade un nuevo evento |
| DEL  | /event/6 | Elimina el evento 6 |
| PUT  | /event/1 | Actualiza todos los datos del evento 1 |
|PATCH | /event/1 | Actualiza algunos datos del evento 1 |


## API REST - Location

| Solicitud HTTP  | EndPoint | Descripción |
| ------------- | ------------- | ------------- |
| GET ALL  | /location  | Obtiene todas las localizaciones |
| GET  | /location/1  | Obtiene la localización 1 |
| POST | /location | Añade una nueva localización |
| DEL  | /location/6 | Elimina la localización 6 |
| PUT  | /location/1 | Actualiza todos los datos de la localización 1 |
|PATCH | /location/1 | Actualiza algunos datos de la localización 1 |


## API REST - User

| Solicitud HTTP  | EndPoint | Descripción |
| ------------- | ------------- | ------------- |
| GET ALL  | /user  | Obtiene todos los usuarios |
| GET  | /user/1  | Obtiene el usuario 1 |
| POST | /user | Añade un nuevo usuario |
| DEL  | /user/6 | Elimina el usuario 6 |
| PUT  | /user/1 | Actualiza todos los datos del usuario 1 |
|PATCH | /user/1 | Actualiza algunos datos del usuario 1 |
