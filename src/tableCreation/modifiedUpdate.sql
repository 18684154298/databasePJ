SET GLOBAL event_scheduler = ON;
CREATE EVENT reset_modified
    ON SCHEDULE EVERY 1 DAY
        STARTS (TIMESTAMP(CURRENT_DATE) + INTERVAL 1 DAY)
    DO
    UPDATE publish SET modified = 0;
