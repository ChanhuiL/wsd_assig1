# Backend Item API

## API Endpoints

### Resources

| Method | Endpoint | Description | Response |
|---|---|---|---|
| GET | `/api/v1/items` | Get items list | 200 ok |
| GET | `/api/v1/items/{id}` | Get a item via id | 200 ok / 400 bad request |
| POST | `/api/v1/items` | Create a item | 201 created |
| POST | `/api/v1/items/randompost` | Create a random item | 201 created |
| PUT | `/api/v1/items/{id}` | Update a item | 200 ok / 404 not found |
| PUT | `/api/v1/items/discount?id=&?percentage=` | Update a item with discount percentage | 200 ok / 400 bad request / 404 not found |
| DELETE | `/api/v1/items/{id}` | Delete a item | 204 no content / 500 internal server error |
| DELETE | `/api/v1/items/delete?id=` | Delete a item | 204 no content / 503 service unavailable |


