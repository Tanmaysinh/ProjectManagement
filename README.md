# Mini Project Management REST API (Spring Boot 3 / Java 21)

This is a ready-to-run Spring Boot project implementing a small project & task management REST API with JWT authentication.

## Run locally

1. Update `src/main/resources/application.properties` with your MySQL username/password and `app.jwtSecret` (base64 encoded recommended).
2. Build and run:

```bash
mvn clean package
java -jar target/mini-pm-0.0.1-SNAPSHOT.jar
```

3. Endpoints:


API Endpoints
Authentication (/auth)
Method	Endpoint	Description	Auth
POST	/auth/register	Register a new user	
POST	/auth/login	Login and get JWT token	


Projects (/projects)
Requires: Authorization: Bearer <token>

Method	Endpoint	Description
POST	/projects/create	Create a new project
GET	/projects	List projects for logged-in user
GET	/projects/{id}	Get project by ID
PUT	/projects/{id}	Update project
DELETE	/projects/{id}	Delete project & its tasks

Tasks (/tasks)
Requires: Authorization: Bearer <token>

Method  Endpoint	                        Description
POST	/tasks/{projectId}/create	        Create a task inside a project
GET	    /tasks	                            Incorrect path, required projectId but missing → documented AS-IS
GET	    /tasks/{projectId}	                List tasks for a project
GET	    /tasks/search?query={q}	            Search tasks globally by query
PUT	    /tasks/{taskId}	                    Update task by ID
GET	    /tasks/{taskId}/status/{status}	    Filter tasks by status 
GET	    /tasks/{taskId}/priority/{priority}	Filter tasks by priority 
DELETE	/tasks/{taskId}	                    Delete a task



Database Schema

CREATE TABLE users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE projects (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NULL,
    user_id INT NOT NULL,

    CONSTRAINT fk_project_user
        FOREIGN KEY (user_id) REFERENCES users(id)
        ON DELETE CASCADE
);

CREATE TABLE tasks (
    id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    status VARCHAR(50) DEFAULT 'PENDING',
    priority VARCHAR(50) DEFAULT 'MEDIUM',
    due_date DATE NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NULL,
    project_id BIGINT NOT NULL,

    CONSTRAINT fk_task_project
        FOREIGN KEY (project_id) REFERENCES projects(id)
        ON DELETE CASCADE
);

