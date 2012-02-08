DROP TABLE IF EXISTS `lm_leave_adjustment_s`;
CREATE TABLE `lm_leave_adjustment_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`));

DROP TABLE IF EXISTS `lm_leave_adjustment_t`;
CREATE TABLE `lm_leave_adjustment_t` (
  `LM_LEAVE_ADJUSTMENT_ID` varchar(60) NOT NULL,
  `PRINCIPAL_ID` varchar(40) NOT NULL,
  `ACCRUAL_CAT`  varchar(15) NOT NULL,
  `LEAVE_PLAN` varchar(15) NOT NULL,
  `LEAVE_CODE` varchar(15) NOT NULL,
  `ADJUSTMENT_AMOUNT` DECIMAL(20) NOT NULL,
  `DESCRIPTION` varchar(50) NULL DEFAULT NULL,
  `TIMESTAMP` timestamp NULL DEFAULT NULL,
  `OBJ_ID` VARCHAR(36),
  `VER_NBR` BIGINT DEFAULT 1 NOT NULL,
  PRIMARY KEY (`LM_LEAVE_ADJUSTMENT_ID`));