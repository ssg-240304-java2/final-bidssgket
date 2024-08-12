INSERT INTO auction (bid_success, min_tender_price, max_tender_price, tender_deleted, member_no, product_no, tender_date)
VALUES
    (0, 152000, 270000,0,3,4, now());

SELECT a.bid_no,a.member_no, m.member_name, p.product_name, a.bid_success, a.min_tender_price, a.max_tender_price
from auction a
    join member m ON a.member_no = m.member_no
    join product p ON a.product_no = p.product_no;

INSERT INTO auction (bid_success, min_tender_price, max_tender_price, tender_deleted, member_no, product_no, tender_date)
VALUES
    (0, 81000, 100000,0,1,2, now());

INSERT INTO auction (bid_success, min_tender_price, max_tender_price, tender_deleted, member_no, product_no, tender_date)
VALUES (0, 81700, 160000,0,3,2, now());

INSERT INTO auction (bid_success, min_tender_price, max_tender_price, tender_deleted, member_no, product_no, tender_date)
VALUES (0, 82000, 200000,0,2,2, now());

# ,
# (0, 81700, 160000,0,3,2, now()),
# (0, 82000, 200000,0,2,2, now()),
# (0, 81000, 100000,0,7,2, now());
