ALTER TABLE tk_shift_differential_rl_t
  CHANGE COLUMN `sun` `sun` VARCHAR(1) NOT NULL DEFAULT 'N',
  CHANGE COLUMN `mon` `mon` VARCHAR(1) NOT NULL DEFAULT 'N' ,
  CHANGE COLUMN `tue` `tue` VARCHAR(1) NOT NULL DEFAULT 'N' ,
  CHANGE COLUMN `wed` `wed` VARCHAR(1) NOT NULL DEFAULT 'N' ,
  CHANGE COLUMN `thu` `thu` VARCHAR(1) NOT NULL DEFAULT 'N' ,
  CHANGE COLUMN `fri` `fri` VARCHAR(1) NOT NULL DEFAULT 'N' ,
  CHANGE COLUMN `sat` `sat` VARCHAR(1) NOT NULL DEFAULT 'N' ;

update tk_shift_differential_rl_t set sun = 'Y' where sun = '1';
update tk_shift_differential_rl_t set sun = 'N' where sun = '0';

update tk_shift_differential_rl_t set mon = 'Y' where mon = '1';
update tk_shift_differential_rl_t set mon = 'N' where mon = '0';

update tk_shift_differential_rl_t set tue = 'Y' where tue = '1';
update tk_shift_differential_rl_t set tue = 'N' where tue = '0';

update tk_shift_differential_rl_t set wed = 'Y' where wed = '1';
update tk_shift_differential_rl_t set wed = 'N' where wed = '0';

update tk_shift_differential_rl_t set thu = 'Y' where thu = '1';
update tk_shift_differential_rl_t set thu = 'N' where thu = '0';

update tk_shift_differential_rl_t set fri = 'Y' where fri = '1';
update tk_shift_differential_rl_t set fri = 'N' where fri = '0';

update tk_shift_differential_rl_t set sat = 'Y' where sat = '1';
update tk_shift_differential_rl_t set sat = 'N' where sat = '0';