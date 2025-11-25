# Library Management System (LMS)


This project is a Library Management System (LMS) designed to manage the core functions of a library, including cataloging books, managing users, tracking loans, and storing information on authors, publishers, and book categories. Built using Java with Maven and a MySQL database, this application serves as a comprehensive backend for a library, supporting user management, book borrowing, and database relations for various entities.

---

### Screenshot / video

https://github.com/user-attachments/assets/c025d170-e5a1-4011-876c-aa345e8190bc

![image](https://github.com/user-attachments/assets/42fec7ae-5bc3-4cc6-82d8-468593f2f5fa)
![image](https://github.com/user-attachments/assets/3a9db534-2054-452f-aecf-e653c32e903f)
![image](https://github.com/user-attachments/assets/2c7a6e97-6e99-41c9-9094-0aa9fc083df3)

### Key Features

- **User Management:** Track users, authors, and library members, including their personal and contact details.
- **Book Cataloging:** Store detailed information about each book, including title, ISBN, edition, publisher, location in the library, and more.
- **Inventory Management:** Keep track of the library's stock, including book availability and condition.
- **Loan Management:** Manage borrowing transactions, including due dates, returns, and overdue fees.
- **Relational Data Handling:** Maintain relationships between authors, publishers, books, categories, and locations.

---

### 📊 UML Diagram

![diagram-export-6-11-2024-11_25_28-pm](https://github.com/user-attachments/assets/6cc5806e-3766-4e3e-8fcf-44b0feae5a9f)

---

### ⚙️ MySQL Database Setup
Before running the LMS application, you need to set up a MySQL database with the structure provided in the `SQL/lms.sql` file.

#### Step 1: Setting up MySQL Database
1. **Create Database:** Create a new MySQL database for LMS.
    ```sql
    CREATE DATABASE lms;
    USE lms;
    ```
2. **Run `SQL/lms.sql`:** Execute the lms.sql file to create the tables and relations in the database
    ```bash
    mysql -u yourUsername -p library_system < lms.sql
    ```
    
 #### Step 2: Configure `application.properties`
 Set up the `resources/application.properties` file to link the application with your MySQL database:
 ```properties
# Database connection properties

db.url=jdbc:mysql://localhost:3306/lms
db.user=root
db.password="YOUR_PASSWORD"
```

---

### Installation Setup

#### Prerequisites
1. **Java Development Kit (JDK):** Ensure JDK 11 or above is installed.
2. **Apache Maven:** Required for project building and dependency management.
3. **MySQL Database:** To store library data.

#### Installation Steps

1. **Clone the Repository:**
  ```bash
  git clone https://github.com/tomar018/Online-Library-Management-System.git
  cd library-management-system
  ```
2. **Database Setup:** Follow the MySQL Database Setup instructions above.
3. Install Dependencies: Ensure all Maven dependencies are installed:
  ```bash
  mvn clean install
  ```
4. **Run the Application:** Start the application using:
 ```bash
  mvn exec:java
  ```
