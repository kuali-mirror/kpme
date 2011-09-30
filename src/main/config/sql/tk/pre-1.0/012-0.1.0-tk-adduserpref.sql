create table tk_user_pref_t (
  `PRINCIPAL_ID` varchar(40) NOT NULL,
  `TIME_ZONE` varchar(30) NULL,
    PRIMARY KEY (`PRINCIPAL_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;