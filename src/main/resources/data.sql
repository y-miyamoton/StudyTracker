INSERT INTO users (username, password_hash) VALUES
('demo', '{noop}hash');

INSERT INTO subjects (user_id, name, color_code, description) VALUES
(1, 'Java', '#FF6B6B', '言語基礎とコレクション'),
(1, 'SQL', '#4D96FF', 'RDBの基礎'),
(1, 'Spring', '#6BCB77', 'DI/Bean/Validation');

INSERT INTO tags (user_id, name, color_code) VALUES
(1, 'Udemy', '#F59E0B'),
(1, '資格', '#8B5CF6'),
(1, '復習', '#10B981');