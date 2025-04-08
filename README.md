# Book Retail Shop Application

## Overview
This application is a book retail store platform that allows users to purchase books online. It features different APIs for various functionalities and implements role-based access control. The application has an API to migrated exiting data in table from SQL to Couchbase (NoSQL) database.

## Database Migration
The application has an API with logic to migrate data from a relational SQL database with tables which has relation to Couchbase, a NoSQL database. A Migration API was created to facilitate this transition, which takes a list containing SQL table names and their corresponding Couchbase bucket names.

### SQL Database Structure (Before Migration)
Database: `product_db`

**Tables:**
- **rs_book**
  - `book_id` (primary key)
  - `category`
  - `description`
  - `name`
  - `pages`
  - `price`
  - `quantity`

- **rs_order**
  - `order_id` (primary key)
  - `book_id`
  - `email`

- **user**
  - `id` (primary key)
  - `address`
  - `email`
  - `name`
  - `password`

- **role**
  - `id` (primary key)
  - `role`
  - `userid` (foreign key to user.id)

*Note: The user table has a one-to-many relationship with the role table.*

### Couchbase Database Structure (After Migration)

**Buckets:**
- **bookinventory**
  - Document key: `id` (from book_id)
  - Attributes: `category`, `description`, `name`, `pages`, `price`, `quantity`

- **order**
  - Document key: `id` (from order_id)
  - Attributes: `book_id`, `email`

- **user**
  - Document key: `id`
  - Attributes: `address`, `email`, `name`, `password`, `role` (array of JSON objects containing `id` and `role`)

## Migration Details
- Data from `rs_book` table migrates to `bookinventory` bucket
- Data from `rs_order` table migrates to `order` bucket
- Data from `user` and `role` tables migrates to `user` bucket
  - For the `role` data, JSON objects with matching `userid` (foreign key) are added to a JSON array and stored as an attribute in the corresponding user document

## Security
The application implements role-based access control using Spring Security. There are two primary user roles:
- **ADMIN**: Has access to administration APIs
- **CUSTOMER**: Has limited access to customer-facing APIs

Access to various APIs is restricted based on the user's role.

## Technologies Used
- Spring Boot
- Spring Security
- SQL
- Couchbase
- RESTful APIs

## API Documentation

The application runs on `http://localhost:8786/` and provides the following APIs:

### User Management APIs

#### Register User
- **Endpoint**: `/user/register`
- **Method**: POST
- **Access**: Public
- **Description**: Register a new user
- **Request Body**: UserDTO object (requires email, password, and address)
- **Response**: Success message

#### Admin Login
- **Endpoint**: `/user/admin/login`
- **Method**: POST
- **Access**: Admin role
- **Description**: Authenticate an admin user
- **Request Body**: UserCredentialDTO (email and password)
- **Response**: UserDTO object

#### Customer Login
- **Endpoint**: `/user/customer/login`
- **Method**: POST
- **Access**: Customer role
- **Description**: Authenticate a customer user
- **Request Body**: UserCredentialDTO (email and password)
- **Response**: UserDTO object

### Book Inventory APIs

#### Get All Books
- **Endpoint**: `/bookInventory-api/book/allBooks`
- **Method**: GET
- **Access**: Public
- **Description**: Retrieve all available books
- **Response**: Array of BookInventoryDTO objects

#### Get Book by ID
- **Endpoint**: `/bookInventory-api/book/{id}`
- **Method**: GET
- **Access**: Public
- **Description**: Retrieve details of a specific book
- **Parameters**: id (path variable)
- **Response**: BookInventoryDTO object

#### Add Book (Admin only)
- **Endpoint**: `/bookInventory-api/admin/add`
- **Method**: POST
- **Access**: Admin role
- **Description**: Add a new book to inventory
- **Request Body**: BookInventoryDTO object
- **Response**: Success message

#### Update Book Quantity (Purchase)
- **Endpoint**: `/bookInventory-api/customer/update/{id}`
- **Method**: PUT
- **Access**: Customer role
- **Description**: Reduce the quantity of a book (used during purchase)
- **Parameters**: id (path variable)
- **Response**: Success message

### Order APIs

#### Place Order
- **Endpoint**: `/order-api/customer/placeOrder`
- **Method**: POST
- **Access**: Customer role
- **Description**: Place a new book order
- **Request Body**: OrderDTO object
- **Response**: Success message

### Migration API

#### Migrate Data
- **Endpoint**: `/migration`
- **Method**: POST
- **Access**: Admin role
- **Description**: Migrate data from SQL to Couchbase
- **Request Body**: Array of strings (containing table and bucket names)
- **Response**: Success message