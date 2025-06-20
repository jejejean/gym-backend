-- Tabla de roles
CREATE TABLE roles (
   id_rol INT PRIMARY KEY AUTO_INCREMENT,
   rol VARCHAR(100)
);

-- Tabla de usuarios
CREATE TABLE users (
    id_user INT PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR(100) NOT NULL UNIQUE,
    username VARCHAR(100),
    phone VARCHAR(15),
    password VARCHAR(100) NOT NULL,
    user_type VARCHAR(50) NOT NULL,
    status VARCHAR(50) NOT NULL
);

-- Relación muchos a muchos entre usuarios y roles
CREATE TABLE users_roles (
     id_user INT,
     id_rol INT,
     PRIMARY KEY (id_user, id_rol),
     FOREIGN KEY (id_user) REFERENCES users(id_user),
     FOREIGN KEY (id_rol) REFERENCES roles(id_rol)
);

-- Perfil adicional de usuario
CREATE TABLE user_profiles (
    id INT PRIMARY KEY AUTO_INCREMENT,
    id_user INT UNIQUE,
    sex VARCHAR(50),
    height DECIMAL(10,2),
    weight DECIMAL(10,2),
    FOREIGN KEY (id_user) REFERENCES users(id_user)
);

-- Tipos de planes
CREATE TABLE plan_types (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50),
    duration_days INT,
    price DECIMAL(10,2)
);

-- Planes contratados por los usuarios
CREATE TABLE user_plans (
    id INT PRIMARY KEY AUTO_INCREMENT,
    id_plan_type INT,
    id_usuario INT,
    start_date DATE,
    end_date DATE,
    status VARCHAR(50),
    FOREIGN KEY (id_plan_type) REFERENCES plan_types(id),
    FOREIGN KEY (id_usuario) REFERENCES users(id_user)
);

-- Tabla de reservas
CREATE TABLE reserve (
    id INT PRIMARY KEY AUTO_INCREMENT,
    id_usuario INT,
    reservation_date DATE,
    FOREIGN KEY (id_usuario) REFERENCES users(id_user)
    );

-- Tabla de máquinas
CREATE TABLE machine (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL
);

-- Relación muchos a muchos entre reservas y máquinas
CREATE TABLE reserve_machine (
    reserve_id INT NOT NULL,
    machine_id INT NOT NULL,
    PRIMARY KEY (reserve_id, machine_id),
    FOREIGN KEY (reserve_id) REFERENCES reserve(id) ON DELETE CASCADE,
    FOREIGN KEY (machine_id) REFERENCES machine(id) ON DELETE CASCADE
);

-- Asistencia a una reserva
CREATE TABLE attendance (
    id INT PRIMARY KEY AUTO_INCREMENT,
    id_reserve INT,
    attended BOOLEAN,
    checkin_time TIMESTAMP,
    FOREIGN KEY (id_reserve) REFERENCES reserve(id)
);

-- Horarios disponibles
CREATE TABLE time_slots (
    id INT PRIMARY KEY AUTO_INCREMENT,
    start_time TIME,
    end_time TIME,
    date DATE,
    capacity INT
);

-- Capacidad adicional por franja y día
CREATE TABLE slot_capacity (
   id INT PRIMARY KEY AUTO_INCREMENT,
   id_time_slot INT,
   reservation_date DATE,
   max_capacity INT,
   FOREIGN KEY (id_time_slot) REFERENCES time_slots(id)
);

-- Relación muchos a muchos entre reservas y horarios
CREATE TABLE reserve_time_slots (
    reserve_id INT NOT NULL,
    time_slot_id INT NOT NULL,
    PRIMARY KEY (reserve_id, time_slot_id),
    FOREIGN KEY (reserve_id) REFERENCES reserve(id),
    FOREIGN KEY (time_slot_id) REFERENCES time_slots(id)
);