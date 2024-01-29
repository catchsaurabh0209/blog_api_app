# Project Name ( blog_api_app )

## Overview

This project is a Spring MVC application designed for creating REST APIs with the following key features:

1. **Spring MVC Architecture for REST API Creation**
2. **JWT Authentication**
3. **Swagger for API Documentation**
4. **AWS Deployment using Beanstalk Service**
5. **MySQL for Data Storage**

The project encompasses various entities and relationships:

- **Category Table**: Stores category details with a one-to-many relationship with posts.
- **User Entity**: Manages user information.
- **Post Entity**: Contains post content and title, with a one-to-many relationship with comments.
- **Comment Entity**: Stores comment information, associated with posts.
- **Role Entity**: Holds role information.
- **Role_User Entity**: Tracks role assignments to users.

Each entity has its own REST controller API supporting CRUD operations. Additionally, the project includes APIs for user login, token generation, and user registration. JWT authentication secures these APIs by generating tokens used for authentication during access of the api's.

**pagination, sorting, searching** is implemented for the post api.

API documentation is facilitated through **Swagger**, providing clarity and ease of use for developers.

Finally, the project is deployed and hosted on **AWS** using the Beanstalk service for scalability and reliability.

## Setup Instructions

To run the project locally, follow these steps:

1. Clone the repository from GitHub.
2. Configure the MySQL database and update the application.properties file with the database credentials.
3. Build the project using Maven.
4. Run the application using your preferred IDE or command line.
5. Access the APIs using the provided endpoints.

## API Endpoints

- `/api/categories`: CRUD operations for categories.
- `/api/users`: CRUD operations for users.
- `/api/posts`: CRUD operations for posts.
- `/api/comments`: CRUD operations for comments.
- `/api/roles`: CRUD operations for roles.
- `/api/auth/login`: Endpoint for user login and token generation.
- `/api/auth/register`: Endpoint for user registration.

## Documentation

API documentation is available via Swagger. Access the Swagger UI by navigating to `/swagger-ui.html` after running the application.

## Deployment

The application is hosted on AWS using the Beanstalk service. Access the deployed application using the provided URL.

## Technologies Used

- Spring Boot
- Spring MVC
- MySQL (Database)
- Swagger
- AWS Beanstalk

## Contributors

- [Suman Saurabh](https://github.com/catchsaurabh0209) - Developer

Feel free to contribute to the project by opening issues or pull requests.

## License

This project is licensed under the [MIT License](LICENSE).
