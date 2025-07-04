DROP DATABASE IF EXISTS VSLearn;
CREATE DATABASE VSLearn;
USE VSLearn;

CREATE TABLE Users (
    id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    user_name VARCHAR(255) NOT NULL,
    user_password VARCHAR(255) NOT NULL,
    user_email VARCHAR(255) NOT NULL,
    phone_number VARCHAR(12),
    user_avatar VARCHAR(255),
    user_role VARCHAR(255) NOT NULL,
    is_active TINYINT(1) NOT NULL,
    created_at DATETIME NOT NULL,
    created_by INT UNSIGNED,
    updated_at DATETIME,
    updated_by INT UNSIGNED,
    deleted_at DATETIME,
    deleted_by INT UNSIGNED,
    UNIQUE KEY user_name (user_name),
    UNIQUE KEY user_email (user_email),
    UNIQUE KEY phone_number (phone_number)
);



CREATE TABLE Topic (
    id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    topic_name VARCHAR(255) NOT NULL,
    is_free TINYINT(1) NOT NULL,
    status VARCHAR(255) NOT NULL COMMENT 'approve/reject/pending',
    created_at DATETIME NOT NULL,
    created_by INT UNSIGNED NOT NULL,
    updated_at DATETIME,
    updated_by INT UNSIGNED,
    deleted_at DATETIME,
    deleted_by INT UNSIGNED
);


CREATE TABLE SubTopic (
    id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    topic_id INT UNSIGNED NOT NULL,
    sub_topic_name VARCHAR(255) NOT NULL,
    status VARCHAR(255) NOT NULL,
    created_at DATETIME NOT NULL,
    created_by INT UNSIGNED NOT NULL,
    updated_at DATETIME,
    updated_by INT UNSIGNED,
    deleted_at DATETIME,
    deleted_by INT UNSIGNED,
    FOREIGN KEY (topic_id) REFERENCES Topic(id) ON DELETE CASCADE

);


CREATE TABLE Area (
    id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    area_name VARCHAR(255) NOT NULL,
    created_at DATETIME NOT NULL,
    created_by INT UNSIGNED NOT NULL,
    updated_at DATETIME,
    updated_by INT UNSIGNED,
    deleted_at DATETIME,
    deleted_by INT UNSIGNED
);


CREATE TABLE Packages (
    id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    package_type VARCHAR(255) NOT NULL,
    package_price INT UNSIGNED NOT NULL,
    duration_days INT UNSIGNED NOT NULL,
    discount_percent DOUBLE UNSIGNED NOT NULL,
    created_at DATETIME NOT NULL,
    created_by INT UNSIGNED NOT NULL,
    updated_at DATETIME,
    updated_by INT UNSIGNED,
    deleted_at DATETIME,
    deleted_by INT UNSIGNED
);


CREATE TABLE Progress (
    id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    sub_topic_id INT UNSIGNED NOT NULL,
    created_by INT UNSIGNED NOT NULL,
    is_complete TINYINT(1) NOT NULL,
    created_at DATETIME NOT NULL,
    FOREIGN KEY (sub_topic_id) REFERENCES SubTopic(id) ON DELETE CASCADE,
    FOREIGN KEY (created_by) REFERENCES Users(id) ON DELETE CASCADE
);


CREATE TABLE Question (
    id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    question_type VARCHAR(255) NOT NULL,
    topic_id INT UNSIGNED NOT NULL,
    question_content VARCHAR(255),
    question_answer INT NOT NULL,
    created_at DATETIME NOT NULL,
    created_by INT UNSIGNED NOT NULL,
    updated_at DATETIME,
    updated_by INT UNSIGNED,
    deleted_at DATETIME,
    deleted_by INT UNSIGNED,
    FOREIGN KEY (topic_id) REFERENCES Topic(id) ON DELETE CASCADE
);


CREATE TABLE TopicPoint (
    id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    topic_id INT UNSIGNED NOT NULL,
    create_by INT UNSIGNED NOT NULL,
    total_point DOUBLE NOT NULL,
    created_at DATETIME NOT NULL,
    updated_at DATETIME,
    updated_by INT UNSIGNED,
    FOREIGN KEY (topic_id) REFERENCES Topic(id) ON DELETE CASCADE,
    FOREIGN KEY (create_by) REFERENCES Users(id) ON DELETE CASCADE
);


CREATE TABLE Transactions (
    id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    packages_id INT UNSIGNED NOT NULL,
    start_date DATETIME NOT NULL,
    end_date DATETIME NOT NULL,
    created_by INT UNSIGNED NOT NULL,
    created_at DATETIME NOT NULL,
    FOREIGN KEY (packages_id) REFERENCES Packages(id) ON DELETE CASCADE,
    FOREIGN KEY (created_by) REFERENCES Users(id) ON DELETE CASCADE
);


CREATE TABLE Vocab (
    id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    vocab VARCHAR(255) NOT NULL,
    created_at DATETIME NOT NULL,
    created_by INT UNSIGNED NOT NULL,
    updated_at DATETIME,
    updated_by INT UNSIGNED,
    deleted_at DATETIME,
    deleted_by INT UNSIGNED,
    sub_topic_id INT UNSIGNED,
    FOREIGN KEY (sub_topic_id) REFERENCES SubTopic(id) ON DELETE CASCADE
);


CREATE TABLE Answers (
    id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    question_id INT UNSIGNED NOT NULL,
    users_id INT UNSIGNED NOT NULL,
    answer_vocab_id INT UNSIGNED NOT NULL,
    is_correct TINYINT(1) NOT NULL,
    users_answers_point DOUBLE NOT NULL,
    FOREIGN KEY (question_id) REFERENCES Question(id) ON DELETE CASCADE,
    FOREIGN KEY (answer_vocab_id) REFERENCES Vocab(id) ON DELETE CASCADE
);


CREATE TABLE UserFeedback (
    id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    feedback_content TEXT,
    rating INT UNSIGNED NOT NULL,
    created_by INT UNSIGNED NOT NULL,
    created_at DATETIME NOT NULL,
    FOREIGN KEY (created_by) REFERENCES Users(id) ON DELETE CASCADE
);


CREATE TABLE VocabArea (
    id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    vocab_id INT UNSIGNED NOT NULL,
    area_id INT UNSIGNED NOT NULL,
    vocab_area_gif VARCHAR(255) NOT NULL,
    vocab_area_description TEXT,
    created_at DATETIME NOT NULL,
    created_by INT UNSIGNED NOT NULL,
    updated_at DATETIME,
    updated_by INT UNSIGNED,
    deleted_at DATETIME,
    deleted_by INT UNSIGNED,
    FOREIGN KEY (vocab_id) REFERENCES Vocab(id) ON DELETE CASCADE,
    FOREIGN KEY (area_id) REFERENCES Area(id) ON DELETE CASCADE
);

CREATE TABLE Sentence (
    id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    sentence_gif VARCHAR(255),
    sentence_description TEXT,
    sentence_type VARCHAR(255),
    sentence_sub_topic_id INT UNSIGNED NOT NULL,
    created_at DATETIME,
    created_by INT UNSIGNED,
    updated_at DATETIME,
    updated_by INT UNSIGNED,
    deleted_at DATETIME,
    deleted_by INT UNSIGNED,
    FOREIGN KEY (sentence_sub_topic_id) REFERENCES SubTopic(id) ON DELETE CASCADE
); 