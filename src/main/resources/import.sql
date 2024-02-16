-- -- spring.jpa.generate-ddl: ture -> yaml에 설정하고 사용 가능
-- -- 이름은 import로 해줘야 함.
-- -- create & create-drop 일 때만 사용 가능
-- -- 쿼리문 한 줄로 작성해야함.

INSERT INTO `t_user` (`created_at`, `iuser`, `nm`, `uid`, `upw`, `pic`, `provider_type`, `role`, `firebase_token`) VALUES ('2024-02-15 11:48:35.014761', 1, '허블', 'hubble', '$2a$10$Meupd/opWWqJR4M6h4whEeY6ntu4jlTgGNNg9n1d9X.YI4zZzGCly', '3a9f49b5-dbd2-42e7-a811-e3c814eed799.jpg', 'LOCAL', 'USER', 'token-test22');
INSERT INTO `t_user` (`created_at`, `iuser`, `nm`, `uid`, `upw`, `pic`, `provider_type`, `role`, `firebase_token`) VALUES ('2024-02-15 11:51:07.872505', 2, '자바', 'java12', '$2a$10$/3NeyovOJlCt8NYNX8/13O0TFhMhURbMVfwmUKOJ3EIQc8Ye6L7.S', '', 'LOCAL', 'USER', NULL);
INSERT INTO `t_user`(`created_at`, `iuser`, `nm`, `uid`, `upw`, `firebase_token`, `pic`, `provider_type`, `role`) VALUES ('2024-02-15 11:47:24.598049', 3, '엠아이씨', 'mic3', '$2a$10$/tHP/A9q4WJ3LnLwp0ENQORWS6SKVQXdvGmF9gfOQHdpSUCdwrzy6', 'tttt', '4d8e1a44-2687-4457-98bb-d40073c033d2.jfif', 'LOCAL', 'USER') ON DUPLICATE KEY UPDATE nm='엠아이씨3';
INSERT INTO `t_user`(`created_at`, `iuser`, `nm`, `uid`, `upw`, `firebase_token`, `pic`, `provider_type`, `role`) VALUES ('2024-02-15 11:47:24.598049', 4, '엠아이씨', 'mic', '$2a$10$/tHP/A9q4WJ3LnLwp0ENQORWS6SKVQXdvGmF9gfOQHdpSUCdwrzy6', 'tttt', '4d8e1a44-2687-4457-98bb-d40073c033d2.jfif', 'LOCAL', 'USER') ON DUPLICATE KEY UPDATE nm='엠아이씨0';
INSERT INTO `t_user`(`created_at`, `iuser`, `nm`, `uid`, `upw`, `firebase_token`, `pic`, `provider_type`, `role`) VALUES ('2024-02-15 11:47:24.598049', 5, '엠아이씨', 'mic2', '$2a$10$/tHP/A9q4WJ3LnLwp0ENQORWS6SKVQXdvGmF9gfOQHdpSUCdwrzy6', 'tttt', '4d8e1a44-2687-4457-98bb-d40073c033d2.jfif', 'LOCAL', 'USER') ON DUPLICATE KEY UPDATE nm='엠아이씨2';


INSERT INTO `t_feed` (`ifeed`, `iuser`, `contents`, `location`, `created_at`, `updated_at`) VALUES (1, 1, 'ㄹㄹㄹ', 'ddd', '2024-01-15 13:01:49', NULL);
INSERT INTO `t_feed` (`ifeed`, `iuser`, `contents`, `location`, `created_at`, `updated_at`) VALUES (2, 1, 'ㅓㅓㅓ', 'ㅕㅕㅕㅕ', '2024-01-15 13:03:53', NULL);
INSERT INTO `t_feed` (`ifeed`, `iuser`, `contents`, `location`, `created_at`, `updated_at`) VALUES (3, 3, '좋아요', '대구', '2024-01-15 16:54:58', NULL);
INSERT INTO `t_feed` (`ifeed`, `iuser`, `contents`, `location`, `created_at`, `updated_at`) VALUES (4, 3, '뭐야?', '어디야', '2024-01-15 16:58:02', NULL);
INSERT INTO `t_feed` (`ifeed`, `iuser`, `contents`, `location`, `created_at`, `updated_at`) VALUES (5, 1, 'jjj', 'jjj', '2024-01-15 17:37:49', NULL);
INSERT INTO `t_feed` (`ifeed`, `iuser`, `contents`, `location`, `created_at`, `updated_at`) VALUES (6, 1, '아아아아', '크큭', '2024-01-15 17:38:30', NULL);
INSERT INTO `t_feed` (`ifeed`, `iuser`, `contents`, `location`, `created_at`, `updated_at`) VALUES (7, 1, '호이짜 호이짜', '남극', '2024-01-15 17:39:46', NULL);
INSERT INTO `t_feed` (`ifeed`, `iuser`, `contents`, `location`, `created_at`, `updated_at`) VALUES (8, 1, '기싱꿈꼬또', '침대위', '2024-01-15 17:41:47', NULL);
INSERT INTO `t_feed` (`ifeed`, `iuser`, `contents`, `location`, `created_at`, `updated_at`) VALUES (9, 1, '누구야!!!!', '', '2024-01-15 17:43:49', NULL);
INSERT INTO `t_feed` (`ifeed`, `iuser`, `contents`, `location`, `created_at`, `updated_at`) VALUES (10, 1, '크크크', '어딘가', '2024-01-16 09:59:04', NULL);
INSERT INTO `t_feed` (`ifeed`, `iuser`, `contents`, `location`, `created_at`, `updated_at`) VALUES (12, 1, '방네', '동네', '2024-01-29 13:05:23', NULL);
INSERT INTO `t_feed` (`ifeed`, `iuser`, `contents`, `location`, `created_at`, `updated_at`) VALUES (14, 1, '01월 30', '그린', '2024-01-30 09:43:26', NULL);
INSERT INTO `t_feed` (`ifeed`, `iuser`, `contents`, `location`, `created_at`, `updated_at`) VALUES (16, 1, '01월 30', '그린', '2024-01-30 09:48:45', NULL);
INSERT INTO `t_feed` (`ifeed`, `iuser`, `contents`, `location`, `created_at`, `updated_at`) VALUES (17, 2, 'B', 'A', '2024-02-01 12:24:35', NULL);
INSERT INTO `t_feed` (`ifeed`, `iuser`, `contents`, `location`, `created_at`, `updated_at`) VALUES (18, 2, 'B', 'A', '2024-02-01 12:25:05', NULL);
INSERT INTO `t_feed` (`ifeed`, `iuser`, `contents`, `location`, `created_at`, `updated_at`) VALUES (19, 2, 'B', 'A', '2024-02-01 12:28:08', NULL);
INSERT INTO `t_feed` (`ifeed`, `iuser`, `contents`, `location`, `created_at`, `updated_at`) VALUES (20, 2, 'B', 'A', '2024-02-01 12:28:31', NULL);
INSERT INTO `t_feed` (`ifeed`, `iuser`, `contents`, `location`, `created_at`, `updated_at`) VALUES (21, 2, 'B', 'A', '2024-02-01 12:28:42', NULL);
INSERT INTO `t_feed` (`ifeed`, `iuser`, `contents`, `location`, `created_at`, `updated_at`) VALUES (22, 2, 'B', 'A', '2024-02-01 12:29:25', NULL);
INSERT INTO `t_feed` (`ifeed`, `iuser`, `contents`, `location`, `created_at`, `updated_at`) VALUES (23, 3, 'B', 'A', '2024-02-01 12:35:49', NULL);
INSERT INTO `t_feed` (`ifeed`, `iuser`, `contents`, `location`, `created_at`, `updated_at`) VALUES (24, 2, 'B', 'A', '2024-02-01 12:36:48', NULL);
INSERT INTO `t_feed` (`ifeed`, `iuser`, `contents`, `location`, `created_at`, `updated_at`) VALUES (25, 1, 'B', 'A', '2024-02-01 12:37:42', NULL);
INSERT INTO `t_feed` (`ifeed`, `iuser`, `contents`, `location`, `created_at`, `updated_at`) VALUES (26, 2, 'B', 'A', '2024-02-01 12:37:49', NULL);
INSERT INTO `t_feed` (`ifeed`, `iuser`, `contents`, `location`, `created_at`, `updated_at`) VALUES (27, 3, 'B', 'A', '2024-02-01 12:46:04', NULL);

INSERT INTO `t_feed_pics` (`ifeed_pics`, `ifeed`, `pic`, `created_at`) VALUES (1, 1, '4939a26f-44ad-4da8-bde0-8bf7bdaf083b.jpg', '2024-01-15 13:01:49');
INSERT INTO `t_feed_pics` (`ifeed_pics`, `ifeed`, `pic`, `created_at`) VALUES (2, 1, '0407053c-26af-4c5b-9812-053f8b07dc94.png', '2024-01-15 13:01:49');
INSERT INTO `t_feed_pics` (`ifeed_pics`, `ifeed`, `pic`, `created_at`) VALUES (3, 2, 'ba95bc37-bad3-4ffe-a418-f56ff56d5341.png', '2024-01-15 13:03:53');
INSERT INTO `t_feed_pics` (`ifeed_pics`, `ifeed`, `pic`, `created_at`) VALUES (4, 2, '38d5907e-3610-4a26-a5d0-25fa1919dff3.jpg', '2024-01-15 13:03:53');
INSERT INTO `t_feed_pics` (`ifeed_pics`, `ifeed`, `pic`, `created_at`) VALUES (5, 2, '2081b2c9-1196-4e36-b2e6-c8b99f1027b8.jpg', '2024-01-15 13:03:53');
INSERT INTO `t_feed_pics` (`ifeed_pics`, `ifeed`, `pic`, `created_at`) VALUES (6, 3, 'fe77d4b4-d5da-4302-8032-6cec43978ece.jpg', '2024-01-15 16:54:58');
INSERT INTO `t_feed_pics` (`ifeed_pics`, `ifeed`, `pic`, `created_at`) VALUES (7, 4, 'f8e504ab-d38b-4956-8fde-f98f34ae35a8.jpg', '2024-01-15 16:58:02');
INSERT INTO `t_feed_pics` (`ifeed_pics`, `ifeed`, `pic`, `created_at`) VALUES (8, 4, '5179853c-1762-42f0-ae2e-61dfc41f2866.jpg', '2024-01-15 16:58:02');
INSERT INTO `t_feed_pics` (`ifeed_pics`, `ifeed`, `pic`, `created_at`) VALUES (9, 5, 'e385bff8-20e2-4521-88fd-28fcca2f2970.jfif', '2024-01-15 17:37:49');
INSERT INTO `t_feed_pics` (`ifeed_pics`, `ifeed`, `pic`, `created_at`) VALUES (10, 6, '6b9fc762-0d74-405b-bda7-77889be56889.jfif', '2024-01-15 17:38:30');
INSERT INTO `t_feed_pics` (`ifeed_pics`, `ifeed`, `pic`, `created_at`) VALUES (11, 7, '41877c1b-e7ed-41e1-8196-ab685b15b5da.jfif', '2024-01-15 17:39:46');
INSERT INTO `t_feed_pics` (`ifeed_pics`, `ifeed`, `pic`, `created_at`) VALUES (12, 8, '49e37209-0039-4238-82fa-5d8cdaa73570.jfif', '2024-01-15 17:41:47');
INSERT INTO `t_feed_pics` (`ifeed_pics`, `ifeed`, `pic`, `created_at`) VALUES (13, 9, '64a28f11-89ed-4630-86ed-6b560615e972.jpg', '2024-01-15 17:43:49');
INSERT INTO `t_feed_pics` (`ifeed_pics`, `ifeed`, `pic`, `created_at`) VALUES (14, 10, '84403903-6f5e-4a4b-8aca-40487b3d5783.jpg', '2024-01-16 09:59:04');
INSERT INTO `t_feed_pics` (`ifeed_pics`, `ifeed`, `pic`, `created_at`) VALUES (15, 10, '5226ce03-fa13-4c0e-b1c9-31a50a8b1560.jfif', '2024-01-16 09:59:04');
INSERT INTO `t_feed_pics` (`ifeed_pics`, `ifeed`, `pic`, `created_at`) VALUES (16, 10, 'e876480a-0cd0-4419-b855-6e527ddb3fa0.jpg', '2024-01-16 09:59:04');
INSERT INTO `t_feed_pics` (`ifeed_pics`, `ifeed`, `pic`, `created_at`) VALUES (19, 12, '0af19515-77d2-483a-95ea-ff458767d132.jpeg', '2024-01-29 13:05:24');
INSERT INTO `t_feed_pics` (`ifeed_pics`, `ifeed`, `pic`, `created_at`) VALUES (20, 12, '13dfc55e-f6e7-4fe9-88bb-ccd70d1b7f9b.jpg', '2024-01-29 13:05:24');
INSERT INTO `t_feed_pics` (`ifeed_pics`, `ifeed`, `pic`, `created_at`) VALUES (22, 14, '326ade98-2406-4013-b2f9-544b14aebf9f.jpg', '2024-01-30 09:43:59');
INSERT INTO `t_feed_pics` (`ifeed_pics`, `ifeed`, `pic`, `created_at`) VALUES (23, 14, '0ab4b60a-23ba-4563-bb4c-316e020c7f9a.jpg', '2024-01-30 09:43:59');
INSERT INTO `t_feed_pics` (`ifeed_pics`, `ifeed`, `pic`, `created_at`) VALUES (26, 16, 'aab823ba-9185-4e66-be50-98b99dc5f8f8.jpg', '2024-01-30 09:48:45');
INSERT INTO `t_feed_pics` (`ifeed_pics`, `ifeed`, `pic`, `created_at`) VALUES (27, 16, '296d24df-1d18-4b79-91a7-633c84773913.jpg', '2024-01-30 09:48:45');
INSERT INTO `t_feed_pics` (`ifeed_pics`, `ifeed`, `pic`, `created_at`) VALUES (28, 17, '62fecd55-4c5e-40a7-8e16-14b35a55f51a.png', '2024-02-01 12:24:35');
INSERT INTO `t_feed_pics` (`ifeed_pics`, `ifeed`, `pic`, `created_at`) VALUES (29, 18, '353d291c-1505-49b0-92db-e921b310099d.png', '2024-02-01 12:25:05');
INSERT INTO `t_feed_pics` (`ifeed_pics`, `ifeed`, `pic`, `created_at`) VALUES (30, 18, '63c266a8-40ea-494a-b591-23dd9d172535.png', '2024-02-01 12:25:05');
INSERT INTO `t_feed_pics` (`ifeed_pics`, `ifeed`, `pic`, `created_at`) VALUES (31, 19, 'fc9807c5-b0fd-4c6b-92bd-c048c07db5d5.png', '2024-02-01 12:28:08');
INSERT INTO `t_feed_pics` (`ifeed_pics`, `ifeed`, `pic`, `created_at`) VALUES (32, 19, '29a523d6-ce82-4082-a138-7e28f4a48cc8.png', '2024-02-01 12:28:08');
INSERT INTO `t_feed_pics` (`ifeed_pics`, `ifeed`, `pic`, `created_at`) VALUES (33, 20, 'f8301d00-4eff-467c-8182-15243c9265d2.png', '2024-02-01 12:28:31');
INSERT INTO `t_feed_pics` (`ifeed_pics`, `ifeed`, `pic`, `created_at`) VALUES (34, 20, 'fc73494d-1941-4c0f-b90e-07ac6cb2b397.png', '2024-02-01 12:28:31');
INSERT INTO `t_feed_pics` (`ifeed_pics`, `ifeed`, `pic`, `created_at`) VALUES (35, 21, '9831e001-55d9-4f3e-b9f3-3602e89138fe.png', '2024-02-01 12:28:42');
INSERT INTO `t_feed_pics` (`ifeed_pics`, `ifeed`, `pic`, `created_at`) VALUES (36, 21, '51dfa591-62d9-4ea3-955d-9fd6e376ac70.png', '2024-02-01 12:28:42');
INSERT INTO `t_feed_pics` (`ifeed_pics`, `ifeed`, `pic`, `created_at`) VALUES (37, 22, 'fe9ac993-90af-464e-a4e8-7f1aae019d4d.png', '2024-02-01 12:29:25');
INSERT INTO `t_feed_pics` (`ifeed_pics`, `ifeed`, `pic`, `created_at`) VALUES (38, 22, '351846d8-1168-45aa-abf4-25841be8603e.png', '2024-02-01 12:29:25');
INSERT INTO `t_feed_pics` (`ifeed_pics`, `ifeed`, `pic`, `created_at`) VALUES (39, 23, '717aef09-37eb-4559-a731-f9afab746a80.png', '2024-02-01 12:35:49');
INSERT INTO `t_feed_pics` (`ifeed_pics`, `ifeed`, `pic`, `created_at`) VALUES (40, 23, '43afb165-2026-464d-b861-9cb6ec1698c2.png', '2024-02-01 12:35:49');
INSERT INTO `t_feed_pics` (`ifeed_pics`, `ifeed`, `pic`, `created_at`) VALUES (41, 24, 'c590c04d-1c06-425c-bc35-0216e8a55f32.png', '2024-02-01 12:36:48');
INSERT INTO `t_feed_pics` (`ifeed_pics`, `ifeed`, `pic`, `created_at`) VALUES (42, 24, '97053b50-30a6-4877-9883-07edce2a1b92.png', '2024-02-01 12:36:48');
INSERT INTO `t_feed_pics` (`ifeed_pics`, `ifeed`, `pic`, `created_at`) VALUES (43, 25, '7b537baf-a9de-4a5b-9834-761332d11034.png', '2024-02-01 12:37:42');
INSERT INTO `t_feed_pics` (`ifeed_pics`, `ifeed`, `pic`, `created_at`) VALUES (44, 25, '3eba5181-6337-4c1d-8bd0-4f6bf957bf9b.png', '2024-02-01 12:37:42');
INSERT INTO `t_feed_pics` (`ifeed_pics`, `ifeed`, `pic`, `created_at`) VALUES (45, 26, '21fcc560-5a53-4f4b-aa11-42fd90da5716.png', '2024-02-01 12:37:49');
INSERT INTO `t_feed_pics` (`ifeed_pics`, `ifeed`, `pic`, `created_at`) VALUES (46, 26, 'b05d34a9-f062-4a95-93ca-38b135de13fd.png', '2024-02-01 12:37:49');
INSERT INTO `t_feed_pics` (`ifeed_pics`, `ifeed`, `pic`, `created_at`) VALUES (47, 27, '6651435d-1b85-42d3-90c9-7246820eab7c.png', '2024-02-01 12:46:04');
INSERT INTO `t_feed_pics` (`ifeed_pics`, `ifeed`, `pic`, `created_at`) VALUES (48, 27, 'd6e94057-5193-4792-bae9-fede6af09e8d.png', '2024-02-01 12:46:04');


INSERT INTO `t_feed_comment` (`ifeed_comment`, `iuser`, `ifeed`, `COMMENT`, `created_at`, `updated_at`) VALUES (1, 3, 17, '크크크', '2024-01-15 17:12:19', '2024-01-15 17:12:19');
INSERT INTO `t_feed_comment` (`ifeed_comment`, `iuser`, `ifeed`, `COMMENT`, `created_at`, `updated_at`) VALUES (2, 1, 17, '하하하', '2024-01-16 12:43:24', '2024-01-16 12:43:24');
INSERT INTO `t_feed_comment` (`ifeed_comment`, `iuser`, `ifeed`, `COMMENT`, `created_at`, `updated_at`) VALUES (3, 1, 17, 'sdfsadfsdfsdf', '2024-01-18 10:14:27', '2024-01-18 10:14:27');
INSERT INTO `t_feed_comment` (`ifeed_comment`, `iuser`, `ifeed`, `COMMENT`, `created_at`, `updated_at`) VALUES (4, 2, 17, 's아아아dfsdf', '2024-01-18 10:14:27', '2024-01-18 10:14:27');
INSERT INTO `t_feed_comment` (`ifeed_comment`, `iuser`, `ifeed`, `COMMENT`, `created_at`, `updated_at`) VALUES (5, 1, 18, 'sadfsadfsdf', '2024-01-24 10:57:47', '2024-01-24 10:57:47');
INSERT INTO `t_feed_comment` (`ifeed_comment`, `iuser`, `ifeed`, `COMMENT`, `created_at`, `updated_at`) VALUES (6, 3, 18, '퉤퉤퉤', '2024-01-18 10:14:27', '2024-01-18 10:14:27');
INSERT INTO `t_feed_comment` (`ifeed_comment`, `iuser`, `ifeed`, `COMMENT`, `created_at`, `updated_at`) VALUES (7, 1, 18, '응????', '2024-01-18 10:14:27', '2024-01-18 10:14:27');