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

INSERT INTO logs (user_id, subject_id, start_at, end_at, minutes, memo) VALUES
(1, 1, TIMESTAMP '2025-10-20 09:00:00', TIMESTAMP '2025-10-20 10:00:00', 60, 'Javaのfor文とStream APIの練習'),
(1, 2, TIMESTAMP '2025-10-21 21:15:00', TIMESTAMP '2025-10-21 22:00:00', 45, '基本文法の確認')