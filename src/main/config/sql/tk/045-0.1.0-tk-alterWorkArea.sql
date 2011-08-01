ALTER TABLE `tk_work_area_t` modify `work_area` bigint(10) NOT NULL;
ALTER TABLE `tk_work_area_t` DROP PRIMARY KEY;
ALTER TABLE `tk_work_area_t` ADD PRIMARY KEY (`TK_WORK_AREA_ID`);