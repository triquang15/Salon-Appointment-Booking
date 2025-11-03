### **Microservice Architecture**

- Develop scalable systems with independent services.

### **Backend**

- **Spring Boot**: Robust backend services.
- **Keycloak**: Secure authentication and authorization.
- **JWT**: Token-based security.
- **MySQL**: Efficient data management.
- RabbitMQ: Event-driven communication.
- **WebSocket**: Real-time notifications.

### DevOps:

- **Docker**: Containerization for seamless deployments.

### **Frontend**

- **React** for building dynamic user interfaces,
- **TailwindCSS** for beautiful, custom designs,
- **Redux** for efficient state management,
- **Material-UI (MUI)** for elegant components
- **Formik** for seamless form handling and validation.

  ### **🚀 Build Microservices 🛠️**

## **👤 User Service**

### **🔐 Auth Endpoints**

1. **📝 POST `/auth/signup`**
    - **📝 Description:** Registers a new user with the provided signup details.
    - **📥 Request Body:**
        - 🆕 `SignupDto` (contains user registration details).
    - **📤 Response:**
        - 🟢 Success message: *"User created successfully."*
        - 📄 Authentication response object.
        
2. **🔑 POST `/auth/login`**
    - **📝 Description:** Logs in a user using their email and password.
    - **📥 Request Body:**
        - 📧 `LoginDto` (contains `email` and `password`).
    - **📤 Response:**
        - 🟢 Success message: *"User logged in successfully."*
        - 📄 Authentication response object.
        
3. **🔄 GET `/auth/access-token/refresh-token/{refreshToken}`**
    - **📝 Description:** Generates a new access token using a valid refresh token.
    - **📥 Parameters:**
        - 🔑 `refreshToken` (PathVariable).
    - **📤 Response:**
        - 🟢 Success message: *"Refresh token received successfully."*
        - 📄 Authentication response object.

---

### **👥 User Endpoints**

1. **👤 GET `/api/users/profile`**
    - **📝 Description:** Retrieves the user profile based on the JWT token.
    - **📥 Request Header:**
        - 🔑 `Authorization` (JWT token).
    - **📤 Response:**
        - 📄 `UserDTO` containing the user's profile information.
    - **✅ HTTP Status:**
        - 🟢 `200 OK` on success.
        
2. **🔍 GET `/api/users/{userId}`**
    - **📝 Description:** Retrieves user details based on their ID.
    - **📥 Path Parameter:**
        - 🆔 `userId` (ID of the user to retrieve).
    - **📤 Response:**
        - 📄 `UserDTO` containing the user's details.
    - **✅ HTTP Status:**
        - 🟢 `200 OK` on success.
        - ❌ Throws a `UserException` if the user is not found.

# **💇 Salon Service**

**Base URL:** `/api/salons`

---

## **1️⃣ Create Salon**

### **🔹 POST `/api/salons`**

- **📝 Description:** Creates a new salon for the authenticated user.
- **🔑 Authorization:**
    - **Header:** `"Authorization"` → JWT token
- **📥 Request Body:**
    - `SalonDTO` → Details of the salon to be created.
- **📤 Response:**
    - **Status:** `201 Created`
    - **Body:** `SalonDTO` → Details of the newly created salon.

---

## **2️⃣ Update Salon**

### **🔹 PUT `/api/salons/{salonId}`**

- **📝 Description:** Updates an existing salon based on the given salon ID.
- **🔑 Path Parameter:**
    - `salonId` → ID of the salon to update.
- **📥 Request Body:**
    - `Salon` → Updated details of the salon.
- **📤 Response:**
    - **Status:** `200 OK`
    - **Body:** `SalonDTO` → Updated details of the salon.

---

## **3️⃣ Get All Salons**

### **🔹 GET `/api/salons`**

- **📝 Description:** Retrieves a list of all salons.
- **📤 Response:**
    - **Status:** `200 OK`
    - **Body:** `List<SalonDTO>` → A list of all salons with owner details.

---

## **4️⃣ Get Salon By ID**

### **🔹 GET `/api/salons/{salonId}`**

- **📝 Description:** Retrieves the details of a salon by its ID.
- **🔑 Path Parameter:**
    - `salonId` → ID of the salon.
- **📤 Response:**
    - **Status:** `200 OK`
    - **Body:** `SalonDTO` → Details of the specified salon.
- **❌ Error Handling:**
    - Throws an exception if the salon does not exist.

---

## **5️⃣ Search Salon By City**

### **🔹 GET `/api/salons/search`**

- **📝 Description:** Searches for salons in a specific city.
- **🔑 Query Parameter:**
    - `city` → City name to search salons.
- **📤 Response:**
    - **Status:** `200 OK`
    - **Body:** `List<SalonDTO>` → A list of salons matching the city criteria.

---

## **6️⃣ Get Salon By Owner**

### **🔹 GET `/api/salons/owner`**

- **📝 Description:** Retrieves the salon owned by the authenticated user.
- **🔑 Authorization:**
    - **Header:** `"Authorization"` → JWT token
- **📤 Response:**
    - **Status:** `200 OK`
    - **Body:** `Salon` → Details of the salon owned by the user.

---

### **🎯 Highlights**

- Each endpoint is carefully integrated with **`SalonService`** and **`UserFeignClient`** to ensure efficient data flow and mapping.
- Custom exceptions like `UserException` handle user-related errors gracefully.
- Simplified DTO mapping via `SalonMapper`.

### **📚 Note**

Make sure the `Authorization` header contains a valid JWT token for endpoints requiring user authentication. 💡

# 🌟 **Category Microservice API Documentation**

**Base URL:** `/api/categories`

**Owner-Specific Base URL:** `/api/categories/salon-owner`

---

## **1️⃣ Get All Categories**

### **🔹 GET `/api/categories`**

- **📝 Description:** Fetches all available categories.
- **📤 Response:**
    - **Status:** `200 OK`
    - **Body:** `List<Category>` → List of all categories.

---

## **2️⃣ Get Categories by Salon ID**

### **🔹 GET `/api/categories/salon/{id}`**

- **📝 Description:** Retrieves categories associated with a specific salon.
- **🔑 Path Parameter:**
    - `id` → ID of the salon.
- **🔑 Header Parameter:**
    - `"Authorization"` → JWT token of the user.
- **📤 Response:**
    - **Status:** `200 OK`
    - **Body:** `Set<Category>` → List of categories linked to the salon.
- **🔗 Feign Clients:**
    - `UserFeignClient` → Validates the user's JWT token.
    - `SalonFeignClient` → Fetches salon details by ID.

---

## **3️⃣ Get Category by ID**

### **🔹 GET `/api/categories/{id}`**

- **📝 Description:** Retrieves a single category by its ID.
- **🔑 Path Parameter:**
    - `id` → ID of the category.
- **📤 Response:**
    - **Status:**
        - `200 OK` → If the category is found.
        - `404 Not Found` → If the category does not exist.
    - **Body:** `Category` → Details of the category.

---

## **4️⃣ Delete Category by ID**

### **🔹 DELETE `/api/categories/{id}`**

- **📝 Description:** Deletes a category by its ID.
- **🔑 Path Parameter:**
    - `id` → ID of the category.
- **📤 Response:**
    - **Status:**
        - `204 No Content` → If deletion is successful.
        - `404 Not Found` → If the category does not exist.

---

## **5️⃣ Create Category (Salon Owner)**

### **🔹 POST `/api/categories/salon-owner`**

- **📝 Description:** Allows a salon owner to create a new category for their salon.
- **🔑 Header Parameter:**
    - `"Authorization"` → JWT token of the salon owner.
- **📥 Request Body:**
    - `Category` → Details of the new category.
- **📤 Response:**
    - **Status:** `201 Created`
    - **Body:** `Category` → Details of the newly created category.
- **🔗 Feign Clients:**
    - `SalonFeignClient` → Validates salon ownership via JWT token.

---

### **🎯 Highlights**

1. **Role-Specific Endpoints:**Separate APIs for all users and salon owners.
2. **Integrated Feign Clients:**
    - `UserFeignClient` → Authenticates user requests.
    - `SalonFeignClient` → Ensures salon-owner-specific operations are secure.
3. **Comprehensive Error Handling:**
    - `404 Not Found` for non-existent resources.
    - Graceful handling of invalid requests.
4. **Enhanced Security:**JWT-based authorization ensures secure access to salon-owner-specific features.

---

### **📚 Note**

Ensure that JWT tokens are passed in the `"Authorization"` header for both salon-owner-specific and category-fetching operations. This guarantees proper authentication and data access.

---

# 🌟 **Service Offering Microservice**

**Base URL:** `/api/service-offering`

**Owner-Specific Base URL:** `/api/service-offering/salon-owner`

---

## **1️⃣ Get Services by Salon ID**

### **🔹 GET `/api/service-offering/salon/{salonId}`**

- **📝 Description:** Retrieves all services offered by a specific salon, with an optional filter by category.
- **🔑 Path Parameter:**
    - `salonId` → ID of the salon.
- **🔑 Query Parameter (Optional):**
    - `categoryId` → ID of the category to filter services.
- **📤 Response:**
    - **Status:** `200 OK`
    - **Body:** `Set<ServiceOffering>` → List of services offered by the salon.

---

## **2️⃣ Get Service by ID**

### **🔹 GET `/api/service-offering/{serviceId}`**

- **📝 Description:** Retrieves details of a specific service by its ID.
- **🔑 Path Parameter:**
    - `serviceId` → ID of the service.
- **📤 Response:**
    - **Status:** `200 OK`
    - **Body:** `ServiceOffering` → Details of the service.
- **❌ Error Handling:**
    - Throws an exception if the service does not exist.

---

## **3️⃣ Get Services by Multiple IDs**

### **🔹 GET `/api/service-offering/list/{ids}`**

- **📝 Description:** Retrieves details of multiple services by their IDs.
- **🔑 Path Parameter:**
    - `ids` → A set of service IDs (comma-separated).
- **📤 Response:**
    - **Status:** `200 OK`
    - **Body:** `Set<ServiceOffering>` → List of services matching the provided IDs.

---

## **4️⃣ Create Service (Salon Owner)**

### **🔹 POST `/api/service-offering/salon-owner`**

- **📝 Description:** Allows a salon owner to create a new service under their salon.
- **🔑 Authorization:**
    - **Header:** `"Authorization"` → JWT token.
- **📥 Request Body:**
    - `ServiceDTO` → Details of the service to be created.
- **📤 Response:**
    - **Status:** `201 Created`
    - **Body:** `ServiceOffering` → Details of the newly created service.
- **🔗 Feign Clients:**
    - `SalonFeignClient` → Retrieves salon details by the owner's JWT.
    - `CategoryFeignClient` → Fetches the category details.

---

## **5️⃣ Update Service (Salon Owner)**

### **🔹 PUT `/api/service-offering/salon-owner/{serviceId}`**

- **📝 Description:** Allows a salon owner to update an existing service.
- **🔑 Path Parameter:**
    - `serviceId` → ID of the service to update.
- **📥 Request Body:**
    - `ServiceOffering` → Updated service details.
- **📤 Response:**
    - **Status:**
        - `200 OK` → On successful update.
        - `404 Not Found` → If the service does not exist.

---

### **🎯 Highlights**

1. **Service Segmentation:**Separate endpoints for all users and salon owners.
2. **Integrated Feign Clients:**
    - `SalonFeignClient` → Ensures salon owner is authenticated.
    - `CategoryFeignClient` → Validates category data.
3. **Error Handling:**Custom exceptions and status codes provide clear responses for invalid requests.
4. **Flexibility:**Query parameter support enables dynamic filtering for services.

### **📚 Note**

Ensure proper JWT tokens are provided in the `Authorization` header for all salon-owner-specific operations.

---

# 🌟 **Booking Microservice API Documentation**

**Base URL:** `/api/bookings`

---

## **1️⃣ Create a Booking**

### **🔹 POST `/api/bookings`**

- **📝 Description:** Creates a new booking for a salon and generates a payment link.
- **🔑 Header Parameters:**
    - `Authorization` → JWT token of the user.
- **🔑 Query Parameters:**
    - `salonId` → ID of the salon.
    - `paymentMethod` → Payment method (e.g., `CARD`, `CASH`).
- **📥 Request Body:**
    - `BookingRequest` → Details of the booking (e.g., `serviceIds`, `startTime`, `endTime`).
- **📤 Response:**
    - **Status:** `201 Created`
    - **Body:** `PaymentLinkResponse` → Payment link and details.
- **🔗 Feign Clients:**
    - `UserFeignClient` → Validates the user using JWT.
    - `SalonFeignClient` → Verifies the salon ID.
    - `ServiceOfferingFeignClient` → Retrieves services by their IDs.
    - `PaymentFeignClient` → Generates the payment link.

---

## **2️⃣ Get All Bookings for a Customer**

### **🔹 GET `/api/bookings/customer`**

- **📝 Description:** Retrieves all bookings associated with a customer.
- **🔑 Header Parameters:**
    - `Authorization` → JWT token of the customer.
- **📤 Response:**
    - **Status:** `200 OK`
    - **Body:** `Set<BookingDTO>` → List of customer bookings.
- **🔗 Feign Clients:**
    - `UserFeignClient` → Validates the user.

---

## **3️⃣ Get All Bookings for a Salon**

### **🔹 GET `/api/bookings/salon`**

- **📝 Description:** Retrieves all bookings for a salon owned by the authenticated user.
- **🔑 Header Parameters:**
    - `Authorization` → JWT token of the salon owner.
- **📤 Response:**
    - **Status:** `200 OK`
    - **Body:** `Set<BookingDTO>` → List of salon bookings.
- **🔗 Feign Clients:**
    - `UserFeignClient` → Validates the user.
    - `SalonFeignClient` → Retrieves salon information by owner.

---

## **4️⃣ Get a Salon Report**

### **🔹 GET `/api/bookings/report`**

- **📝 Description:** Retrieves a report of all bookings for the salon owned by the authenticated user.
- **🔑 Header Parameters:**
    - `Authorization` → JWT token of the salon owner.
- **📤 Response:**
    - **Status:** `200 OK`
    - **Body:** `SalonReport` → Report details for the salon.

---

## **5️⃣ Get Bookings by Date**

### **🔹 GET `/api/bookings/slots/salon/{salonId}/date/{date}`**

- **📝 Description:** Retrieves booked slots for a salon on a specific date.
- **🔑 Path Parameters:**
    - `salonId` → ID of the salon.
    - `date` → Date for which slots are requested (format: `YYYY-MM-DD`).
- **🔑 Header Parameters:**
    - `Authorization` → JWT token of the user.
- **📤 Response:**
    - **Status:** `200 OK`
    - **Body:** `List<BookedSlotsDTO>` → List of booked slots.

---

## **6️⃣ Get a Booking by ID**

### **🔹 GET `/api/bookings/{bookingId}`**

- **📝 Description:** Retrieves details of a booking by its ID.
- **🔑 Path Parameter:**
    - `bookingId` → ID of the booking.
- **📤 Response:**
    - **Status:** `200 OK`
    - **Body:** `BookingDTO` → Details of the booking.

---

## **7️⃣ Update Booking Status**

### **🔹 PUT `/api/bookings/{bookingId}/status`**

- **📝 Description:** Updates the status of a booking (e.g., `CONFIRMED`, `CANCELLED`).
- **🔑 Path Parameter:**
    - `bookingId` → ID of the booking.
- **🔑 Query Parameter:**
    - `status` → New status for the booking.
- **📤 Response:**
    - **Status:** `200 OK`
    - **Body:** `BookingDTO` → Updated booking details.

---

### **🎯 Highlights**

1. **Role-Specific Endpoints:**
    - Separate APIs for customers and salon owners.
2. **Integrated Feign Clients:**
    - `UserFeignClient` → Validates user requests.
    - `SalonFeignClient` → Ensures secure access to salon-related data.
    - `ServiceOfferingFeignClient` → Retrieves service details.
3. **Error Handling:**
    - `404 Not Found` → If the resource does not exist.
    - `400 Bad Request` → For invalid input or state.
4. **Enhanced Security:**
    - JWT-based authorization for all sensitive operations.
5. **Flexible Bookings Management:**
    - Support for creating, updating, and querying bookings efficiently.

---

# 🌟 Payment Microservice API Documentation

## Base URL: `/api/payments`

The Payment Controller handles APIs related to payment processing, including creating payment links, retrieving payment orders, and proceeding with payments.

---

## **Create Payment Link**

### **POST** `/create`

Creates a payment link for the specified booking using the given payment method.

### **Headers**

- **Authorization**: Bearer token of the user (`String`) *(Required)*

### **Request Body**

- **BookingDTO**: The details of the booking for which payment is being initiated.

### **Query Parameters**

- **paymentMethod**: The payment method to use (e.g., `CREDIT_CARD`, `DEBIT_CARD`, `UPI`, etc.)

### **Responses**

- **200 OK**: Returns the payment link details.
    
    ```json
    {
      "id": "string",
      "link": "string",
      "status": "string"
    }
    
    ```
    
- **400 Bad Request**: If required parameters are missing or invalid.
- **500 Internal Server Error**: If an error occurs while creating the payment link.

---

## **Get Payment Order by ID**

### **GET** `/{paymentOrderId}`

Fetches a payment order by its unique ID.

### **Path Parameters**

- **paymentOrderId**: The ID of the payment order (`Long`) *(Required)*

### **Responses**

- **200 OK**: Returns the payment order details.
    
    ```json
    {
      "id": "Long",
      "amount": "Double",
      "status": "String",
      "createdAt": "String",
      "updatedAt": "String"
    }
    
    ```
    
- **404 Not Found**: If the payment order with the specified ID does not exist.

---

## **Proceed Payment**

### **PATCH** `/proceed`

Processes a payment for the specified payment order.

### **Query Parameters**

- **paymentId**: The unique ID of the payment made by the user (`String`) *(Required)*
- **paymentLinkId**: The unique ID of the payment link (`String`) *(Required)*

### **Responses**

- **200 OK**: Returns `true` if the payment was processed successfully.
    
    ```json
    true
    
    ```
    
- **400 Bad Request**: If the payment ID or link ID is invalid.
- **500 Internal Server Error**: If an error occurs while processing the payment.

---

## **Exceptions and Error Handling**

### **UserException**

- Thrown when there is an issue with user authentication or user-related data.

### **RazorpayException**

- Thrown for issues related to Razorpay integration, such as link creation failures.

### **General Error Responses**

- **400 Bad Request**: For invalid input or missing data.
- **500 Internal Server Error**: For any unexpected server-side errors.

---

## **Dependencies and Notes**

- The controller relies on the following services:
    - **PaymentService**: To handle payment-related logic.
    - **UserFeignClient**: To fetch user details from a remote service.
- Supported payment methods include Razorpay integrations.

---

### Setup Project On Local Machine

## Steps to run frontend

- npm i
- npm start

### Backend Setup

- run all microservices
- run keycloak server(user need to install docker for this)

- Install Docker
then run keycloak

command : docker run -p 8080:8080 -e KC_BOOTSTRAP_ADMIN_USERNAME=admin -e KC_BOOTSTRAP_ADMIN_PASSWORD=admin quay.io/keycloak/keycloak:26.1.0 start-dev


1. create client
2. change clientId, CLIENT_SECRET, CLIENT_ID, username, password in Keycloak user service
3. create admin => asign admin role (don't forgot off temporary option)
4. create client role=> CUSTOMER => SALON_OWNER
5. increase access token life span

