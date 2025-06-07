CREATE TABLE Role (
    id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);


CREATE TABLE User (
    id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    profile_picture VARCHAR(255),
    role_id INT UNSIGNED NOT NULL,
    is_active BOOLEAN NOT NULL,
    created_at DATETIME NOT NULL,
    updated_at DATETIME,
    deleted_at DATETIME,
    created_by INT,
    updated_by INT,
    deleted_by INT,
    FOREIGN KEY (role_id) REFERENCES Role(id) ON DELETE CASCADE
);


CREATE TABLE Package (
    id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    type VARCHAR(255) NOT NULL,
    price INT NOT NULL,
    duration_days DATE NOT NULL,
    created_at DATETIME NOT NULL,
    created_by INT NOT NULL,
    updated_at DATETIME,
    updated_by INT,
    deleted_at DATETIME,
    deleted_by INT
);


CREATE TABLE Vocabulary (
    id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    vocab VARCHAR(255) NOT NULL,
    description TEXT,
    gif VARCHAR(255) NOT NULL,
    created_at DATETIME NOT NULL,
    created_by INT NOT NULL,
    updated_at DATETIME,
    updated_by INT,
    deleted_at DATETIME,
    delete_by INT
);


CREATE TABLE Area (
    id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    created_at DATETIME NOT NULL,
    created_by INT NOT NULL,
    updated_at DATETIME,
    updated_by INT,
    deleted_at DATETIME,
    deleted_by INT
);


CREATE TABLE Question_Type (
    id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    type VARCHAR(255) NOT NULL
);


CREATE TABLE Sub_Topic (
    id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    vocabulary_id INT UNSIGNED NOT NULL,
    created_at DATETIME NOT NULL,
    created_by INT NOT NULL,
    updated_at DATETIME,
    updated_by INT,
    deleted_at DATETIME,
    deleted_by INT,
    FOREIGN KEY (vocabulary_id) REFERENCES Vocabulary(id) ON DELETE CASCADE
);


CREATE TABLE Topic (
    id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    sub_topic_id INT UNSIGNED NOT NULL,
    is_free BOOLEAN NOT NULL,
    is_public BOOLEAN NOT NULL,
    created_at DATETIME NOT NULL,
    created_by INT NOT NULL,
    updated_at DATETIME,
    updated_by INT,
    deleted_at DATETIME,
    deleted_by INT,
    FOREIGN KEY (sub_topic_id) REFERENCES Sub_Topic(id) ON DELETE CASCADE
);


CREATE TABLE Vocab_Area (
    id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    vocab_id INT UNSIGNED NOT NULL,
    area_id INT UNSIGNED NOT NULL,
    FOREIGN KEY (vocab_id) REFERENCES Vocabulary(id) ON DELETE CASCADE,
    FOREIGN KEY (area_id) REFERENCES Area(id) ON DELETE CASCADE
);


CREATE TABLE Progress (
    id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    sub_topic_id INT UNSIGNED NOT NULL,
    user_id INT UNSIGNED NOT NULL,
    is_complete BOOLEAN NOT NULL,
    created_at DATETIME NOT NULL,
    created_by INT NOT NULL,
    FOREIGN KEY (sub_topic_id) REFERENCES Sub_Topic(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES User(id) ON DELETE CASCADE
);


CREATE TABLE Topic_point (
    id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    topic_id INT UNSIGNED NOT NULL,
    learner_id INT UNSIGNED NOT NULL,
    total_point INT NOT NULL,
    created_at DATETIME NOT NULL,
    created_by INT NOT NULL,
    updated_at DATETIME,
    updated_by INT,
    FOREIGN KEY (topic_id) REFERENCES Topic(id) ON DELETE CASCADE,
    FOREIGN KEY (learner_id) REFERENCES User(id) ON DELETE CASCADE
);


CREATE TABLE Transaction_History (
    id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    price_id INT UNSIGNED NOT NULL,
    start_date DATETIME NOT NULL,
    end_date DATETIME NOT NULL,
    created_by INT UNSIGNED NOT NULL,
    created_at DATETIME NOT NULL,
    FOREIGN KEY (price_id) REFERENCES Package(id) ON DELETE CASCADE,
    FOREIGN KEY (created_by) REFERENCES User(id) ON DELETE CASCADE
);


CREATE TABLE Test_Question (
    id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    type_id INT UNSIGNED NOT NULL,
    topic_id INT UNSIGNED NOT NULL,
    question TEXT,
    answer INT NOT NULL,
    enplaination TEXT,
    created_at DATETIME NOT NULL,
    created_by INT NOT NULL,
    updated_at DATETIME,
    updated_by INT,
    deleted_at DATETIME,
    deleted_by INT,
    FOREIGN KEY (type_id) REFERENCES Question_Type(id) ON DELETE CASCADE,
    FOREIGN KEY (topic_id) REFERENCES Topic(id) ON DELETE CASCADE
);


CREATE TABLE User_Answers (
    id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    question_id INT UNSIGNED NOT NULL,
    learner_id INT UNSIGNED NOT NULL,
    answer INT NOT NULL,
    is_correct BOOLEAN NOT NULL,
    point DOUBLE NOT NULL,
    FOREIGN KEY (question_id) REFERENCES Test_Question(id) ON DELETE CASCADE,
    FOREIGN KEY (learner_id) REFERENCES User(id) ON DELETE CASCADE
);
