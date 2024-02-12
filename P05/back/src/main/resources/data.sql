INSERT INTO teachers (first_name, last_name)
VALUES ('Margot', 'DELAHAYE'),
       ('Hélène', 'THIERCELIN');


INSERT INTO users (first_name, last_name, admin, email, password, created_at, updated_at)
VALUES ('Admin', 'Admin', true, 'yoga@studio.com', '$2a$10$.Hsa/ZjUVaHqi0tp9xieMeewrnZxrZ5pQRzddUXE/WjDu2ZThe6Iq', '2024-02-07 19:33:10.000000', '2024-02-08 19:35:58.000000'),
       ('User1', 'User1', false, 'User1@studio.com', '$2a$10$.Hsa/ZjUVaHqi0tp9xieMeewrnZxrZ5pQRzddUXE/WjDu2ZThe6Iq', '2024-02-07 19:33:10.000000', '2024-02-08 19:35:58.000000'),
       ('User2', 'User2', false, 'User2@studio.com', '$2a$10$.Hsa/ZjUVaHqi0tp9xieMeewrnZxrZ5pQRzddUXE/WjDu2ZThe6Iq', '2024-02-07 19:33:10.000000', '2024-02-08 19:35:58.000000'),
       ('User3', 'User3', false, 'User3@studio.com', '$2a$10$.Hsa/ZjUVaHqi0tp9xieMeewrnZxrZ5pQRzddUXE/WjDu2ZThe6Iq', '2024-02-07 19:33:10.000000', '2024-02-08 19:35:58.000000'),
       ('User4', 'User4', false, 'User4@studio.com', '$2a$10$.Hsa/ZjUVaHqi0tp9xieMeewrnZxrZ5pQRzddUXE/WjDu2ZThe6Iq', '2024-02-07 19:33:10.000000', '2024-02-08 19:35:58.000000');

INSERT INTO `sessions` (`id`, `created_at`, `date`, `description`, `name`, `updated_at`, `teacher_id`) VALUES
('1', '2024-02-07 19:33:10.000000', '2024-02-07 19:33:10.000000', 'description1', 'Testsessions1', '2024-02-07 19:33:10.000000', '1');

INSERT INTO `sessions` (`id`, `created_at`, `date`, `description`, `name`, `updated_at`, `teacher_id`) VALUES
    ('2', '2024-02-07 19:33:10.000000', '2024-02-07 19:33:10.000000', 'description2', 'Testsessions2', '2024-02-07 19:33:10.000000', '2');

INSERT INTO `participate` (`session_id`, `user_id`) VALUES ('1', '1');