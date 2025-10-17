CREATE TABLE IF NOT EXISTS users
(
  id            BIGINT       PRIMARY KEY AUTO_INCREMENT,
  username      VARCHAR(50)  NOT NULL UNIQUE,
  password_hash VARCHAR(255) NOT NULL,
  created_at    TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at    TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS subjects
(
  id          BIGINT       PRIMARY KEY AUTO_INCREMENT,
  user_id     BIGINT       NOT NULL,
  name        VARCHAR(100) NOT NULL,
  color_code  VARCHAR(7),
  description VARCHAR(255),
  archived    BOOLEAN      DEFAULT FALSE,
  created_at  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  CONSTRAINT fk_subjects_user FOREIGN KEY (user_id) REFERENCES users(id)
);
CREATE INDEX IF NOT EXISTS idx_subjects_user_name ON subjects(user_id, name);

CREATE TABLE IF NOT EXISTS tags
(
  id         BIGINT       PRIMARY KEY AUTO_INCREMENT,
  user_id    BIGINT       NOT NULL,
  name       VARCHAR(100) NOT NULL,
  color_code VARCHAR(7),
  created_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  CONSTRAINT fk_tags_user FOREIGN KEY (user_id) REFERENCES users(id)
);
CREATE UNIQUE INDEX idx_tags_user_name ON tags(user_id, name);

CREATE TABLE IF NOT EXISTS logs
(
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  user_id BIGINT NOT NULL,
  subject_id BIGINT NOT NULL,
  start_at TIMESTAMP NOT NULL,
  end_at TIMESTAMP NOT NULL,
  minutes INT NOT NULL,
  memo VARCHAR(500),
  created_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  CONSTRAINT fk_logs_user FOREIGN KEY (user_id) REFERENCES users(id),
  CONSTRAINT fk_logs_subject FOREIGN KEY (subject_id) REFERENCES subjects(id) ON DELETE CASCADE
);
CREATE INDEX IF NOT EXISTS idx_logs_user_subject_start ON logs(user_id, subject_id, start_at);

CREATE TABLE IF NOT EXISTS log_tags
(
user_id BIGINT NOT NULL,
log_id BIGINT NOT NULL,
tag_id BIGINT NOT NULL,
CONSTRAINT fk_logtag_user FOREIGN KEY (user_id) REFERENCES users(id),
CONSTRAINT fk_logtag_log FOREIGN KEY (log_id) REFERENCES logs(id) ON DELETE CASCADE,
CONSTRAINT fk_logtag_tag FOREIGN KEY (tag_id) REFERENCES tags(id) ON DELETE CASCADE,
CONSTRAINT fk_log_tag PRIMARY KEY (user_id, log_id, tag_id)
);
CREATE INDEX IF NOT EXISTS idx_logtag_user_tag ON log_tags(user_id, tag_id);

CREATE TABLE IF NOT EXISTS pomodoro_session
(
id BIGINT PRIMARY KEY AUTO_INCREMENT,
user_id BIGINT NOT NULL,
subject_id BIGINT NOT NULL,
focus_minutes INT NOT NULL,
break_minutes INT,
start_at TIMESTAMP NOT NULL,
end_at TIMESTAMP,
CONSTRAINT fk_pomo_user FOREIGN KEY (user_id) REFERENCES users(id),
CONSTRAINT fk_pomo_subjects FOREIGN KEY (subject_id) REFERENCES subjects(id) ON DELETE CASCADE
);
CREATE INDEX IF NOT EXISTS idx_pomo_user_subject_start ON pomodoro_session(user_id, subject_id, start_at);

CREATE TABLE IF NOT EXISTS app_audit
(
id BIGINT PRIMARY KEY AUTO_INCREMENT,
user_id BIGINT NOT NULL,
action VARCHAR(30) NOT NULL,
entity VARCHAR(30) NOT NULL,
entity_id BIGINT,
detail VARCHAR(500),
created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
CONSTRAINT fk_audit_user FOREIGN KEY (user_id) REFERENCES users(id)
);
CREATE INDEX IF NOT EXISTS idx_audit_user_created ON app_audit(user_id,created_at DESC);
CREATE INDEX IF NOT EXISTS idx_audit_user_entity ON app_audit(user_id, entity, entity_id, created_at);
































