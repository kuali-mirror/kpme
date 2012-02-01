DROP TABLE IF EXISTS `lm_employee_override_s`;
CREATE TABLE `lm_employee_override_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`));

DROP TABLE IF EXISTS `lm_employee_override_t`;
CREATE TABLE `lm_employee_override_t` (
  `LM_EMPLOYEE_OVERRIDE_ID` varchar(60) NOT NULL,
  `PRINCIPAL_ID` varchar(40) NOT NULL,
  `ACCRUAL_CAT`  varchar(60) NOT NULL,
  `LEAVE_PLAN` varchar(60) NOT NULL,
  `OVERRIDE_TYPE` varchar(30) NOT NULL,
  `OVERRIDE_VALUE` bigint(20) NULL DEFAULT NULL,
  `DESCRIPTION` varchar(50) NULL DEFAULT NULL,
  `ACTIVE` varchar(1) NOT NULL DEFAULT 'N',
  `TIMESTAMP` timestamp NULL DEFAULT NULL,
  `OBJ_ID` VARCHAR(36),
  `VER_NBR` BIGINT DEFAULT 1 NOT NULL,
  PRIMARY KEY (`LM_EMPLOYEE_OVERRIDE_ID`));