INSERT INTO vendor (name, address) VALUES ('水果大王', '果园路1号');
INSERT INTO vendor (name, address) VALUES ('时尚服饰', '时尚街2号');
INSERT INTO vendor (name, address) VALUES ('家居生活', '家居路3号');
INSERT INTO vendor (name, address) VALUES ('电子科技', '科技大道4号');
INSERT INTO vendor (name, address) VALUES ('运动户外', '运动街5号');
-- 商家1的商品
INSERT INTO Commodity (name, category, origin, MFD) VALUES ('苹果', '水果', '山东', '2023-01-01');
INSERT INTO Commodity (name, category, origin, MFD) VALUES ('香蕉', '水果', '海南', '2023-01-02');
INSERT INTO Commodity (name, category, origin, MFD) VALUES ('西瓜', '水果', '新疆', '2023-01-03');
INSERT INTO Commodity (name, category, origin, MFD) VALUES ('橙子', '水果', '江西', '2023-01-04');
INSERT INTO Commodity (name, category, origin, MFD) VALUES ('梨', '水果', '河北', '2023-01-05');

-- 商家2的商品
INSERT INTO Commodity (name, category, origin, MFD) VALUES ('T恤', '服装', '广东', '2023-01-06');
INSERT INTO Commodity (name, category, origin, MFD) VALUES ('牛仔裤', '服装', '浙江', '2023-01-07');
INSERT INTO Commodity (name, category, origin, MFD) VALUES ('衬衫', '服装', '福建', '2023-01-08');
INSERT INTO Commodity (name, category, origin, MFD) VALUES ('夹克', '服装', '四川', '2023-01-09');
INSERT INTO Commodity (name, category, origin, MFD) VALUES ('卫衣', '服装', '江苏', '2023-01-10');

INSERT INTO Commodity (name, category, origin, MFD) VALUES ('T恤', '服装', '山东', '2023-01-01');
INSERT INTO Commodity (name, category, origin, MFD) VALUES ('牛仔裤', '服装', '海南', '2023-01-02');
INSERT INTO Commodity (name, category, origin, MFD) VALUES ('西瓜', '水果', '新疆', '2023-01-03');
INSERT INTO Commodity (name, category, origin, MFD) VALUES ('橙子', '水果', '江西', '2023-01-04');
INSERT INTO Commodity (name, category, origin, MFD) VALUES ('梨', '水果', '河北', '2023-01-05');
-- 商家3, 4, 5的商品类似上述格式继续添加
-- 商家1的商品关联
INSERT INTO sell (Commodity_ID, Vendor_ID) VALUES (1, 1);
INSERT INTO sell (Commodity_ID, Vendor_ID) VALUES (2, 1);
INSERT INTO sell (Commodity_ID, Vendor_ID) VALUES (3, 1);
INSERT INTO sell (Commodity_ID, Vendor_ID) VALUES (4, 1);
INSERT INTO sell (Commodity_ID, Vendor_ID) VALUES (5, 1);

-- 商家2的商品关联
INSERT INTO sell (Commodity_ID, Vendor_ID) VALUES (6, 2);
INSERT INTO sell (Commodity_ID, Vendor_ID) VALUES (7, 2);
INSERT INTO sell (Commodity_ID, Vendor_ID) VALUES (8, 2);
INSERT INTO sell (Commodity_ID, Vendor_ID) VALUES (9, 2);
INSERT INTO sell (Commodity_ID, Vendor_ID) VALUES (10, 2);

INSERT INTO sell (Commodity_ID, Vendor_ID) VALUES (11, 3);
INSERT INTO sell (Commodity_ID, Vendor_ID) VALUES (12, 3);
INSERT INTO sell (Commodity_ID, Vendor_ID) VALUES (13, 3);
INSERT INTO sell (Commodity_ID, Vendor_ID) VALUES (14, 3);
INSERT INTO sell (Commodity_ID, Vendor_ID) VALUES (15, 3);
-- 商家3, 4, 5
INSERT INTO Platform(id, name) VALUES (1,'京东');
INSERT INTO Platform(id, name) VALUES (2,'淘宝');
INSERT INTO Platform(id, name) VALUES (3,'拼多多');
INSERT INTO Platform(id, name) VALUES (4,'得物');
INSERT INTO Platform(id, name) VALUES (5,'抖音');

-- 将“苹果”发布到所有平台
INSERT INTO publish (c_id, p_id) VALUES (1, 1);
INSERT INTO publish (c_id, p_id) VALUES (1, 2);
INSERT INTO publish (c_id, p_id) VALUES (1, 3);
INSERT INTO publish (c_id, p_id) VALUES (1, 4);
INSERT INTO publish (c_id, p_id) VALUES (1, 5);

-- 将“香蕉”发布到所有平台
INSERT INTO publish (c_id, p_id) VALUES (2, 1);
INSERT INTO publish (c_id, p_id) VALUES (2, 2);
INSERT INTO publish (c_id, p_id) VALUES (2, 3);
INSERT INTO publish (c_id, p_id) VALUES (2, 4);
INSERT INTO publish (c_id, p_id) VALUES (2, 5);

-- 为“苹果”设置价格
INSERT INTO time_price (c_id, p_id, price, time) VALUES (1, 1, 10.00, NOW());
INSERT INTO time_price (c_id, p_id, price, time) VALUES (1, 1, 11.00, NOW());
INSERT INTO time_price (c_id, p_id, price, time) VALUES (1, 1, 13.00, NOW());

INSERT INTO time_price (c_id, p_id, price, time) VALUES (1, 2, 11.00, NOW());
INSERT INTO time_price (c_id, p_id, price, time) VALUES (1, 2, 12.00, NOW());
INSERT INTO time_price (c_id, p_id, price, time) VALUES (1, 2, 10.00, NOW());

INSERT INTO time_price (c_id, p_id, price, time) VALUES (1, 3, 9.50, NOW());
INSERT INTO time_price (c_id, p_id, price, time) VALUES (1, 3, 9.90, NOW());
INSERT INTO time_price (c_id, p_id, price, time) VALUES (1, 3, 9.10, NOW());

INSERT INTO time_price (c_id, p_id, price, time) VALUES (1, 4, 10.50, NOW());
INSERT INTO time_price (c_id, p_id, price, time) VALUES (1, 4, 10.50, NOW());
INSERT INTO time_price (c_id, p_id, price, time) VALUES (1, 4, 10.50, NOW());

INSERT INTO time_price (c_id, p_id, price, time) VALUES (1, 5, 10.00, NOW());
INSERT INTO time_price (c_id, p_id, price, time) VALUES (1, 5, 18.00, NOW());
INSERT INTO time_price (c_id, p_id, price, time) VALUES (1, 5, 12.00, NOW());

-- 为“香蕉”设置价格
INSERT INTO time_price (c_id, p_id, price, time) VALUES (2, 1, 5.00, NOW());
INSERT INTO time_price (c_id, p_id, price, time) VALUES (2, 1, 8.00, NOW());
INSERT INTO time_price (c_id, p_id, price, time) VALUES (2, 1, 6.00, NOW());

INSERT INTO time_price (c_id, p_id, price, time) VALUES (2, 2, 10.50, NOW());
INSERT INTO time_price (c_id, p_id, price, time) VALUES (2, 2, 5.50, NOW());
INSERT INTO time_price (c_id, p_id, price, time) VALUES (2, 2, 7.50, NOW());

INSERT INTO time_price (c_id, p_id, price, time) VALUES (2, 3, 5.50, NOW());
INSERT INTO time_price (c_id, p_id, price, time) VALUES (2, 3, 4.50, NOW());
INSERT INTO time_price (c_id, p_id, price, time) VALUES (2, 3, 1.50, NOW())

INSERT INTO time_price (c_id, p_id, price, time) VALUES (2, 4, 5.00, NOW());
INSERT INTO time_price (c_id, p_id, price, time) VALUES (2, 4, 1.00, NOW());
INSERT INTO time_price (c_id, p_id, price, time) VALUES (2, 4, 2.00, NOW());

INSERT INTO time_price (c_id, p_id, price, time) VALUES (2, 5, 1.50, NOW());
INSERT INTO time_price (c_id, p_id, price, time) VALUES (2, 5, 2.50, NOW());
INSERT INTO time_price (c_id, p_id, price, time) VALUES (2, 5, 5.50, NOW());
