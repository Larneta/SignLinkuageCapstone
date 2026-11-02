-- ASL ABC's Exam Data (Module 1)
-- This file contains the exam questions and answers for ASL ABC's

-- Insert Exam for Module 1
INSERT INTO exam (title, description, module_id, total_questions, passing_score, time_limit, created_at)
VALUES ('ASL ABC Mastery Exam', 'Comprehensive exam covering all ASL letters from A-Z', 1, 12, 70, 30, NOW());

-- Get the exam ID (assuming it's 1 for the first exam)
-- Note: In production, you would retrieve the actual ID after insertion

-- For Question 1: Identify letter C
INSERT INTO exam_questions (question_text, image_url, order_index, exam_id)
VALUES ('Which image shows the correct ASL sign for the letter "C"?', '/images/asl/question_letter_c.jpg', 1, 1);

INSERT INTO exam_answers (option_text, image_url, is_correct, order_index, question_id)
VALUES
('A', '/images/asl/letter_a.jpg', FALSE, 1, 1),
('B', '/images/asl/letter_b.jpg', FALSE, 2, 1),
('C', '/images/asl/letter_c.jpg', TRUE, 3, 1),
('D', '/images/asl/letter_d.jpg', FALSE, 4, 1);

-- For Question 2: Identify letter A
INSERT INTO exam_questions (question_text, image_url, order_index, exam_id)
VALUES ('What does the ASL letter "A" look like?', '/images/asl/question_letter_a.jpg', 2, 1);

INSERT INTO exam_answers (option_text, image_url, is_correct, order_index, question_id)
VALUES
('A', '/images/asl/letter_a.jpg', TRUE, 1, 2),
('B', '/images/asl/letter_b.jpg', FALSE, 2, 2),
('C', '/images/asl/letter_c.jpg', FALSE, 3, 2),
('D', '/images/asl/letter_d.jpg', FALSE, 4, 2);

-- For Question 3: Identify letter F
INSERT INTO exam_questions (question_text, image_url, order_index, exam_id)
VALUES ('Identify the ASL letter shown below.', '/images/asl/question_letter_f.jpg', 3, 1);

INSERT INTO exam_answers (option_text, image_url, is_correct, order_index, question_id)
VALUES
('D', '/images/asl/letter_d.jpg', FALSE, 1, 3),
('E', '/images/asl/letter_e.jpg', FALSE, 2, 3),
('F', '/images/asl/letter_f.jpg', TRUE, 3, 3),
('G', '/images/asl/letter_g.jpg', FALSE, 4, 3);

-- For Question 4: Identify letter B
INSERT INTO exam_questions (question_text, image_url, order_index, exam_id)
VALUES ('What letter is being signed in this image?', '/images/asl/question_letter_b.jpg', 4, 1);

INSERT INTO exam_answers (option_text, image_url, is_correct, order_index, question_id)
VALUES
('A', '/images/asl/letter_a.jpg', FALSE, 1, 4),
('B', '/images/asl/letter_b.jpg', TRUE, 2, 4),
('C', '/images/asl/letter_c.jpg', FALSE, 3, 4),
('D', '/images/asl/letter_d.jpg', FALSE, 4, 4);

-- For Question 5: Identify letter M
INSERT INTO exam_questions (question_text, image_url, order_index, exam_id)
VALUES ('What does the ASL letter "M" look like?', '/images/asl/question_letter_m.jpg', 5, 1);

INSERT INTO exam_answers (option_text, image_url, is_correct, order_index, question_id)
VALUES
('L', '/images/asl/letter_l.jpg', FALSE, 1, 5),
('M', '/images/asl/letter_m.jpg', TRUE, 2, 5),
('N', '/images/asl/letter_n.jpg', FALSE, 3, 5),
('O', '/images/asl/letter_o.jpg', FALSE, 4, 5);

-- For Question 6: Identify letter R
INSERT INTO exam_questions (question_text, image_url, order_index, exam_id)
VALUES ('Select the correct ASL hand sign for the letter "R".', '/images/asl/question_letter_r.jpg', 6, 1);

INSERT INTO exam_answers (option_text, image_url, is_correct, order_index, question_id)
VALUES
('P', '/images/asl/letter_p.jpg', FALSE, 1, 6),
('Q', '/images/asl/letter_q.jpg', FALSE, 2, 6),
('R', '/images/asl/letter_r.jpg', TRUE, 3, 6),
('S', '/images/asl/letter_s.jpg', FALSE, 4, 6);

-- For Question 7: Identify letter X
INSERT INTO exam_questions (question_text, image_url, order_index, exam_id)
VALUES ('Select the correct ASL hand sign for the letter "X".', '/images/asl/question_letter_x.jpg', 7, 1);

INSERT INTO exam_answers (option_text, image_url, is_correct, order_index, question_id)
VALUES
('V', '/images/asl/letter_v.jpg', FALSE, 1, 7),
('W', '/images/asl/letter_w.jpg', FALSE, 2, 7),
('X', '/images/asl/letter_x.jpg', TRUE, 3, 7),
('Y', '/images/asl/letter_y.jpg', FALSE, 4, 7);

-- For Question 8: Identify letter Z
INSERT INTO exam_questions (question_text, image_url, order_index, exam_id)
VALUES ('Which image shows the ASL sign for the letter "Z"?', '/images/asl/question_letter_z.jpg', 8, 1);

INSERT INTO exam_answers (option_text, image_url, is_correct, order_index, question_id)
VALUES
('X', '/images/asl/letter_x.jpg', FALSE, 1, 8),
('Y', '/images/asl/letter_y.jpg', FALSE, 2, 8),
('Z', '/images/asl/letter_z.jpg', TRUE, 3, 8),
('A', '/images/asl/letter_a.jpg', FALSE, 4, 8);

-- For Question 9: Identify letter E
INSERT INTO exam_questions (question_text, image_url, order_index, exam_id)
VALUES ('Identify the ASL letter shown in this image.', '/images/asl/question_letter_e.jpg', 9, 1);

INSERT INTO exam_answers (option_text, image_url, is_correct, order_index, question_id)
VALUES
('D', '/images/asl/letter_d.jpg', FALSE, 1, 9),
('E', '/images/asl/letter_e.jpg', TRUE, 2, 9),
('F', '/images/asl/letter_f.jpg', FALSE, 3, 9),
('G', '/images/asl/letter_g.jpg', FALSE, 4, 9);

-- For Question 10: Identify letter K
INSERT INTO exam_questions (question_text, image_url, order_index, exam_id)
VALUES ('What does the ASL letter "K" look like?', '/images/asl/question_letter_k.jpg', 10, 1);

INSERT INTO exam_answers (option_text, image_url, is_correct, order_index, question_id)
VALUES
('I', '/images/asl/letter_i.jpg', FALSE, 1, 10),
('J', '/images/asl/letter_j.jpg', FALSE, 2, 10),
('K', '/images/asl/letter_k.jpg', TRUE, 3, 10),
('L', '/images/asl/letter_l.jpg', FALSE, 4, 10);

-- For Question 11: Identify letter T
INSERT INTO exam_questions (question_text, image_url, order_index, exam_id)
VALUES ('Select the correct ASL hand sign for the letter "T".', '/images/asl/question_letter_t.jpg', 11, 1);

INSERT INTO exam_answers (option_text, image_url, is_correct, order_index, question_id)
VALUES
('R', '/images/asl/letter_r.jpg', FALSE, 1, 11),
('S', '/images/asl/letter_s.jpg', FALSE, 2, 11),
('T', '/images/asl/letter_t.jpg', TRUE, 3, 11),
('U', '/images/asl/letter_u.jpg', FALSE, 4, 11);

-- For Question 12: Identify letter V
INSERT INTO exam_questions (question_text, image_url, order_index, exam_id)
VALUES ('Identify the ASL letter shown below.', '/images/asl/question_letter_v.jpg', 12, 1);

INSERT INTO exam_answers (option_text, image_url, is_correct, order_index, question_id)
VALUES
('U', '/images/asl/letter_u.jpg', FALSE, 1, 12),
('V', '/images/asl/letter_v.jpg', TRUE, 2, 12),
('W', '/images/asl/letter_w.jpg', FALSE, 3, 12),
('X', '/images/asl/letter_x.jpg', FALSE, 4, 12);
