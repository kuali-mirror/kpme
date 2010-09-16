ALTER TABLE `tk_document_header_t` ADD COLUMN `PAY_BEGIN_DT` DATE NULL  AFTER `DOCUMENT_STATUS` ;
ALTER TABLE `tk`.`tk_earn_code_t` CHANGE COLUMN `earn_code_id` `tk_earn_code_id` INT(11) NOT NULL, DROP PRIMARY KEY , ADD PRIMARY KEY (`tk_earn_code_id`) ;
ALTER TABLE `tk`.`tk_assignment_t` DROP COLUMN `EARN_CODE` ;
ALTER TABLE `tk`.`tk_assign_acct_t` ADD COLUMN `EARN_CODE` VARCHAR(3) NULL  AFTER `VER_NBR` ;
