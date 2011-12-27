# Create a simple daily overtime rule
#
# REG -> OVT; 8 hours Minimum; 15.00 Max Gap.
# Dept: TEST-DEPT       Work Area: 30
# Location: BW
insert into tk_daily_overtime_rl_t values (2000, 'SD1', 'BW', '2011-01-02', 'admin', '2011-07-26 11:23:34', 'TEST-DEPT', '30', '15.00', '8', 'Y', 'REG', 'OVT');