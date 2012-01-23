DELETE FROM `hr_calendar_entries_t` WHERE hr_calendar_entry_id = '43';
UPDATE `tk_clock_log_t` SET CLOCK_TS_TZ = 'TEST' WHERE TK_CLOCK_LOG_ID = 1