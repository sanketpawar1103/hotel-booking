# Hotel App API Documentation

## Base URL

```txt
http://localhost:3000
```

---

# 1. Search Hotels

## Endpoint

```http
GET /api/search/hotels
```

## Full URL

```txt
http://localhost:3000/api/search/hotels
```

## Query Parameters

| Parameter | Type   | Required | Description                |
| --------- | ------ | -------- | -------------------------- |
| city      | string | Yes      | City name to search hotels |

## Example Request

```txt
GET /api/search/hotels?city=New%20York
```

## Example Response

```json
[
  {
    "id": 1,
    "name": "Hotel ABC",
    "city": "New York"
  }
]
```

---

# 2. User Signup

## Endpoint

```http
POST /api/users/register
```

## Full URL

```txt
http://localhost:3000/api/users/register
```

## Headers

| Header       | Value            |
| ------------ | ---------------- |
| Content-Type | application/json |

## Request Body

| Field    | Type   | Required | Description   |
| -------- | ------ | -------- | ------------- |
| username | string | Yes      | User username |
| password | string | Yes      | User password |

## Example Request Body

```json
{
  "username": "testuser",
  "password": "password123"
}
```

## Example Response

```json
{
  "message": "User registered successfully"
}
```

---

# 3. User Signin

## Endpoint

```http
POST /api/users/login
```

## Full URL

```txt
http://localhost:3000/api/users/login
```

## Headers

| Header       | Value            |
| ------------ | ---------------- |
| Content-Type | application/json |

## Request Body

| Field    | Type   | Required | Description         |
| -------- | ------ | -------- | ------------------- |
| username | string | Yes      | Registered username |
| password | string | Yes      | User password       |

## Example Request Body

```json
{
  "username": "testuser",
  "password": "password123"
}
```

## Example Response

```json
{
  "token": "JWT_TOKEN"
}
```

---

# 4. List Bookings

## Endpoint

```http
GET /api/bookings
```

## Full URL

```txt
http://localhost:3000/api/bookings
```

## Headers

| Header        | Value              |
| ------------- | ------------------ |
| Authorization | Bearer <JWT_TOKEN> |

## Authentication

Requires JWT token from login API.

## Example Request

```txt
GET /api/bookings
```

## Example Response

```json
[
  {
    "booking_id": 1,
    "hotel_id": 1,
    "rooms": 1
  }
]
```

---

# 5. Book Hotel

## Endpoint

```http
POST /api/bookings
```

## Full URL

```txt
http://localhost:3000/api/bookings
```

## Headers

| Header        | Value              |
| ------------- | ------------------ |
| Authorization | Bearer <JWT_TOKEN> |
| Content-Type  | application/json   |

## Authentication

Requires JWT token from login API.

## Request Body

| Field    | Type   | Required | Description     |
| -------- | ------ | -------- | --------------- |
| hotel_id | number | Yes      | Hotel ID        |
| rooms    | number | Yes      | Number of rooms |

## Example Request Body

```json
{
  "hotel_id": 1,
  "rooms": 1
}
```

## Example Response

```json
{
  "message": "Booking successful"
}
```

---

# 6. Download Booking Receipt

## Endpoint

```http
GET /api/bookings/:id/receipt.pdf
```

## Full URL

```txt
http://localhost:3000/api/bookings/1/receipt.pdf
```

## Headers

| Header        | Value              |
| ------------- | ------------------ |
| Authorization | Bearer <JWT_TOKEN> |

## Path Parameters

| Parameter | Type   | Required | Description |
| --------- | ------ | -------- | ----------- |
| id        | number | Yes      | Booking ID  |

## Authentication

Requires JWT token from login API.

## Example Request

```txt
GET /api/bookings/1/receipt.pdf
```

## Expected Response

PDF file download for booking receipt.

---

# Authentication Flow

```txt
Signup -> Login -> Receive JWT Token -> Access Protected APIs
```

Protected APIs:

* List Bookings
* Book Hotel
* Download Receipt

---

# Notes

* All APIs run on localhost:3000
* Protected APIs require Bearer token
* Content-Type for POST APIs should be application/json
* Receipt endpoint in original YAML had a typo and was corrected to:

```txt
/api/bookings/:id/receipt.pdf
```
