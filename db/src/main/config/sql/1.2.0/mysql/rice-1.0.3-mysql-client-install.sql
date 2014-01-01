--
-- Copyright 2004-2014 The Kuali Foundation
--
-- Licensed under the Educational Community License, Version 2.0 (the "License");
-- you may not use this file except in compliance with the License.
-- You may obtain a copy of the License at
--
-- http://www.opensource.org/licenses/ecl2.php
--
-- Unless required by applicable law or agreed to in writing, software
-- distributed under the License is distributed on an "AS IS" BASIS,
-- WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
-- See the License for the specific language governing permissions and
-- limitations under the License.
--


# -----------------------------------------------------------------------
# KRNS_ADHOC_RTE_ACTN_RECIP_T
# -----------------------------------------------------------------------
CREATE TABLE KRNS_ADHOC_RTE_ACTN_RECIP_T
(
      RECIP_TYP_CD DECIMAL(1)
        , ACTN_RQST_CD VARCHAR(30)
        , ACTN_RQST_RECIP_ID VARCHAR(70)
        , OBJ_ID VARCHAR(36) NOT NULL
        , VER_NBR DECIMAL(8) default 1 NOT NULL
        , DOC_HDR_ID VARCHAR(14)
    
    , CONSTRAINT KRNS_ADHOC_RTE_ACTN_RECIP_TP1 PRIMARY KEY(RECIP_TYP_CD,ACTN_RQST_CD,ACTN_RQST_RECIP_ID,DOC_HDR_ID)

    , CONSTRAINT KRNS_ADHOC_RTE_ACTN_RECIP_TC0 UNIQUE (OBJ_ID)

    , INDEX KRNS_ADHOC_RTE_ACTN_RECIP_T2 (DOC_HDR_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
;


# -----------------------------------------------------------------------
# KRNS_ATT_T
# -----------------------------------------------------------------------
CREATE TABLE KRNS_ATT_T
(
      NTE_ID DECIMAL(14)
        , OBJ_ID VARCHAR(36) NOT NULL
        , VER_NBR DECIMAL(8) default 1 NOT NULL
        , MIME_TYP VARCHAR(255)
        , FILE_NM VARCHAR(250)
        , ATT_ID VARCHAR(36)
        , FILE_SZ DECIMAL(14)
        , ATT_TYP_CD VARCHAR(2)
    
    , CONSTRAINT KRNS_ATT_TP1 PRIMARY KEY(NTE_ID)

    , CONSTRAINT KRNS_ATT_TC0 UNIQUE (OBJ_ID)


) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
;


# -----------------------------------------------------------------------
# KRNS_DOC_HDR_T
# -----------------------------------------------------------------------
CREATE TABLE KRNS_DOC_HDR_T
(
      DOC_HDR_ID VARCHAR(14)
        , OBJ_ID VARCHAR(36) NOT NULL
        , VER_NBR DECIMAL(8) default 1 NOT NULL
        , FDOC_DESC VARCHAR(40)
        , ORG_DOC_HDR_ID VARCHAR(10)
        , TMPL_DOC_HDR_ID VARCHAR(14)
        , EXPLANATION VARCHAR(400)
    
    , CONSTRAINT KRNS_DOC_HDR_TP1 PRIMARY KEY(DOC_HDR_ID)

    , CONSTRAINT KRNS_DOC_HDR_TC0 UNIQUE (OBJ_ID)

    , INDEX KRNS_DOC_HDR_TI3 (ORG_DOC_HDR_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
;


# -----------------------------------------------------------------------
# KRNS_LOOKUP_RSLT_T
# -----------------------------------------------------------------------
CREATE TABLE KRNS_LOOKUP_RSLT_T
(
      LOOKUP_RSLT_ID VARCHAR(14)
        , OBJ_ID VARCHAR(36) NOT NULL
        , VER_NBR DECIMAL(8) default 1 NOT NULL
        , PRNCPL_ID VARCHAR(40) NOT NULL
        , LOOKUP_DT DATETIME NOT NULL
        , SERIALZD_RSLTS LONGTEXT
    
    , CONSTRAINT KRNS_LOOKUP_RSLT_TP1 PRIMARY KEY(LOOKUP_RSLT_ID)

    , CONSTRAINT KRNS_LOOKUP_RSLT_TC0 UNIQUE (OBJ_ID)


) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
;


# -----------------------------------------------------------------------
# KRNS_LOOKUP_SEL_T
# -----------------------------------------------------------------------
CREATE TABLE KRNS_LOOKUP_SEL_T
(
      LOOKUP_RSLT_ID VARCHAR(14)
        , OBJ_ID VARCHAR(36) NOT NULL
        , VER_NBR DECIMAL(8) default 1 NOT NULL
        , PRNCPL_ID VARCHAR(40) NOT NULL
        , LOOKUP_DT DATETIME NOT NULL
        , SEL_OBJ_IDS LONGTEXT
    
    , CONSTRAINT KRNS_LOOKUP_SEL_TP1 PRIMARY KEY(LOOKUP_RSLT_ID)

    , CONSTRAINT KRNS_LOOKUP_SEL_TC0 UNIQUE (OBJ_ID)


) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
;


# -----------------------------------------------------------------------
# KRNS_MAINT_DOC_ATT_T
# -----------------------------------------------------------------------
CREATE TABLE KRNS_MAINT_DOC_ATT_T
(
      DOC_HDR_ID VARCHAR(14)
        , ATT_CNTNT LONGBLOB NOT NULL
        , FILE_NM VARCHAR(150)
        , CNTNT_TYP VARCHAR(255)
        , OBJ_ID VARCHAR(36) NOT NULL
        , VER_NBR DECIMAL(8) default 1 NOT NULL
    
    , CONSTRAINT KRNS_MAINT_DOC_ATT_TP1 PRIMARY KEY(DOC_HDR_ID)

    , CONSTRAINT KRNS_MAINT_DOC_ATT_TC0 UNIQUE (OBJ_ID)


) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
;


# -----------------------------------------------------------------------
# KRNS_MAINT_DOC_T
# -----------------------------------------------------------------------
CREATE TABLE KRNS_MAINT_DOC_T
(
      DOC_HDR_ID VARCHAR(14)
        , OBJ_ID VARCHAR(36) NOT NULL
        , VER_NBR DECIMAL(8) default 1 NOT NULL
        , DOC_CNTNT LONGTEXT
    
    , CONSTRAINT KRNS_MAINT_DOC_TP1 PRIMARY KEY(DOC_HDR_ID)

    , CONSTRAINT KRNS_MAINT_DOC_TC0 UNIQUE (OBJ_ID)


) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
;


# -----------------------------------------------------------------------
# KRNS_MAINT_LOCK_T
# -----------------------------------------------------------------------
CREATE TABLE KRNS_MAINT_LOCK_T
(
      MAINT_LOCK_REP_TXT VARCHAR(500)
        , OBJ_ID VARCHAR(36) NOT NULL
        , VER_NBR DECIMAL(8) default 1 NOT NULL
        , DOC_HDR_ID VARCHAR(14) NOT NULL
        , MAINT_LOCK_ID VARCHAR(14)
    
    , CONSTRAINT KRNS_MAINT_LOCK_TP1 PRIMARY KEY(MAINT_LOCK_ID)

    , CONSTRAINT KRNS_MAINT_LOCK_TC0 UNIQUE (OBJ_ID)

    , INDEX KRNS_MAINT_LOCK_TI2 (DOC_HDR_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
;


# -----------------------------------------------------------------------
# KRNS_NTE_T
# -----------------------------------------------------------------------
CREATE TABLE KRNS_NTE_T
(
      NTE_ID DECIMAL(14)
        , OBJ_ID VARCHAR(36) NOT NULL
        , VER_NBR DECIMAL(8) default 1 NOT NULL
        , RMT_OBJ_ID VARCHAR(36) NOT NULL
        , AUTH_PRNCPL_ID VARCHAR(40) NOT NULL
        , POST_TS DATETIME NOT NULL
        , NTE_TYP_CD VARCHAR(4) NOT NULL
        , TXT VARCHAR(800)
        , PRG_CD VARCHAR(1)
        , TPC_TXT VARCHAR(40)
    
    , CONSTRAINT KRNS_NTE_TP1 PRIMARY KEY(NTE_ID)

    , CONSTRAINT KRNS_NTE_TC0 UNIQUE (OBJ_ID)


) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
;


# -----------------------------------------------------------------------
# KRNS_NTE_TYP_T
# -----------------------------------------------------------------------
CREATE TABLE KRNS_NTE_TYP_T
(
      NTE_TYP_CD VARCHAR(4)
        , OBJ_ID VARCHAR(36) NOT NULL
        , VER_NBR DECIMAL(8) default 1 NOT NULL
        , TYP_DESC_TXT VARCHAR(100)
        , ACTV_IND VARCHAR(1)
    
    , CONSTRAINT KRNS_NTE_TYP_TP1 PRIMARY KEY(NTE_TYP_CD)

    , CONSTRAINT KRNS_NTE_TYP_TC0 UNIQUE (OBJ_ID)


) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
;


# -----------------------------------------------------------------------
# KRNS_PESSIMISTIC_LOCK_T
# -----------------------------------------------------------------------
CREATE TABLE KRNS_PESSIMISTIC_LOCK_T
(
      PESSIMISTIC_LOCK_ID DECIMAL(14)
        , OBJ_ID VARCHAR(36) NOT NULL
        , VER_NBR DECIMAL(8) default 1 NOT NULL
        , LOCK_DESC_TXT VARCHAR(4000)
        , DOC_HDR_ID VARCHAR(14) NOT NULL
        , GNRT_DT DATETIME NOT NULL
        , PRNCPL_ID VARCHAR(40) NOT NULL
    
    , CONSTRAINT KRNS_PESSIMISTIC_LOCK_TP1 PRIMARY KEY(PESSIMISTIC_LOCK_ID)

    , CONSTRAINT KRNS_PESSIMISTIC_LOCK_TC0 UNIQUE (OBJ_ID)

    , INDEX KRNS_PESSIMISTIC_LOCK_TI1 (DOC_HDR_ID)
    , INDEX KRNS_PESSIMISTIC_LOCK_TI2 (PRNCPL_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
;


# -----------------------------------------------------------------------
# KRNS_SESN_DOC_T
# -----------------------------------------------------------------------
CREATE TABLE KRNS_SESN_DOC_T
(
      SESN_DOC_ID VARCHAR(40)
        , DOC_HDR_ID VARCHAR(14)
        , PRNCPL_ID VARCHAR(40)
        , IP_ADDR VARCHAR(60)
        , SERIALZD_DOC_FRM LONGBLOB
        , LAST_UPDT_DT DATETIME
        , CONTENT_ENCRYPTED_IND CHAR(1) default 'N'
    
    , CONSTRAINT KRNS_SESN_DOC_TP1 PRIMARY KEY(SESN_DOC_ID,DOC_HDR_ID,PRNCPL_ID,IP_ADDR)


    , INDEX KRNS_SESN_DOC_TI1 (LAST_UPDT_DT)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
;


# -----------------------------------------------------------------------
# KRSB_BAM_PARM_T
# -----------------------------------------------------------------------
CREATE TABLE KRSB_BAM_PARM_T
(
      BAM_PARM_ID DECIMAL(14)
        , BAM_ID DECIMAL(14) NOT NULL
        , PARM LONGTEXT NOT NULL
    
    , CONSTRAINT KRSB_BAM_PARM_TP1 PRIMARY KEY(BAM_PARM_ID)


    , INDEX KREW_BAM_PARM_TI1 (BAM_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
;


# -----------------------------------------------------------------------
# KRSB_BAM_T
# -----------------------------------------------------------------------
CREATE TABLE KRSB_BAM_T
(
      BAM_ID DECIMAL(14)
        , SVC_NM VARCHAR(255) NOT NULL
        , SVC_URL VARCHAR(500) NOT NULL
        , MTHD_NM VARCHAR(2000) NOT NULL
        , THRD_NM VARCHAR(500) NOT NULL
        , CALL_DT DATETIME NOT NULL
        , TGT_TO_STR VARCHAR(2000) NOT NULL
        , SRVR_IND DECIMAL(1) NOT NULL
        , EXCPN_TO_STR VARCHAR(2000)
        , EXCPN_MSG LONGTEXT
    
    , CONSTRAINT KRSB_BAM_TP1 PRIMARY KEY(BAM_ID)


    , INDEX KRSB_BAM_TI1 (SVC_NM, MTHD_NM)
    , INDEX KRSB_BAM_TI2 (SVC_NM)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
;


# -----------------------------------------------------------------------
# KRSB_MSG_PYLD_T
# -----------------------------------------------------------------------
CREATE TABLE KRSB_MSG_PYLD_T
(
      MSG_QUE_ID DECIMAL(14)
        , MSG_PYLD LONGTEXT NOT NULL
    
    , CONSTRAINT KRSB_MSG_PYLD_TP1 PRIMARY KEY(MSG_QUE_ID)



) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
;


# -----------------------------------------------------------------------
# KRSB_MSG_QUE_T
# -----------------------------------------------------------------------
CREATE TABLE KRSB_MSG_QUE_T
(
      MSG_QUE_ID DECIMAL(14)
        , DT DATETIME NOT NULL
        , EXP_DT DATETIME
        , PRIO DECIMAL(8) NOT NULL
        , STAT_CD CHAR(1) NOT NULL
        , RTRY_CNT DECIMAL(8) NOT NULL
        , IP_NBR VARCHAR(2000) NOT NULL
        , SVC_NM VARCHAR(255)
        , SVC_NMSPC VARCHAR(255) NOT NULL
        , SVC_MTHD_NM VARCHAR(2000)
        , APP_VAL_ONE VARCHAR(2000)
        , APP_VAL_TWO VARCHAR(2000)
        , VER_NBR DECIMAL(8) default 0
    
    , CONSTRAINT KRSB_MSG_QUE_TP1 PRIMARY KEY(MSG_QUE_ID)


    , INDEX KRSB_MSG_QUE_TI1 (SVC_NM, SVC_MTHD_NM)
    , INDEX KRSB_MSG_QUE_TI2 (SVC_NMSPC, STAT_CD, IP_NBR, DT)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
;


# -----------------------------------------------------------------------
# KRSB_QRTZ_BLOB_TRIGGERS
# -----------------------------------------------------------------------
CREATE TABLE KRSB_QRTZ_BLOB_TRIGGERS
(
      TRIGGER_NAME VARCHAR(80)
        , TRIGGER_GROUP VARCHAR(80)
        , BLOB_DATA LONGBLOB
    
    , CONSTRAINT KRSB_QRTZ_BLOB_TRIGGERSP1 PRIMARY KEY(TRIGGER_NAME,TRIGGER_GROUP)



) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
;


# -----------------------------------------------------------------------
# KRSB_QRTZ_CALENDARS
# -----------------------------------------------------------------------
CREATE TABLE KRSB_QRTZ_CALENDARS
(
      CALENDAR_NAME VARCHAR(80)
        , CALENDAR LONGBLOB NOT NULL
    
    , CONSTRAINT KRSB_QRTZ_CALENDARSP1 PRIMARY KEY(CALENDAR_NAME)



) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
;


# -----------------------------------------------------------------------
# KRSB_QRTZ_CRON_TRIGGERS
# -----------------------------------------------------------------------
CREATE TABLE KRSB_QRTZ_CRON_TRIGGERS
(
      TRIGGER_NAME VARCHAR(80)
        , TRIGGER_GROUP VARCHAR(80)
        , CRON_EXPRESSION VARCHAR(80) NOT NULL
        , TIME_ZONE_ID VARCHAR(80)
    
    , CONSTRAINT KRSB_QRTZ_CRON_TRIGGERSP1 PRIMARY KEY(TRIGGER_NAME,TRIGGER_GROUP)



) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
;


# -----------------------------------------------------------------------
# KRSB_QRTZ_FIRED_TRIGGERS
# -----------------------------------------------------------------------
CREATE TABLE KRSB_QRTZ_FIRED_TRIGGERS
(
      ENTRY_ID VARCHAR(95)
        , TRIGGER_NAME VARCHAR(80) NOT NULL
        , TRIGGER_GROUP VARCHAR(80) NOT NULL
        , IS_VOLATILE VARCHAR(1) NOT NULL
        , INSTANCE_NAME VARCHAR(80) NOT NULL
        , FIRED_TIME DECIMAL(13) NOT NULL
        , PRIORITY DECIMAL(13) NOT NULL
        , STATE VARCHAR(16) NOT NULL
        , JOB_NAME VARCHAR(80)
        , JOB_GROUP VARCHAR(80)
        , IS_STATEFUL VARCHAR(1)
        , REQUESTS_RECOVERY VARCHAR(1)
    
    , CONSTRAINT KRSB_QRTZ_FIRED_TRIGGERSP1 PRIMARY KEY(ENTRY_ID)


    , INDEX KRSB_QRTZ_FIRED_TRIGGERS_TI1 (JOB_GROUP)
    , INDEX KRSB_QRTZ_FIRED_TRIGGERS_TI2 (JOB_NAME)
    , INDEX KRSB_QRTZ_FIRED_TRIGGERS_TI3 (REQUESTS_RECOVERY)
    , INDEX KRSB_QRTZ_FIRED_TRIGGERS_TI4 (IS_STATEFUL)
    , INDEX KRSB_QRTZ_FIRED_TRIGGERS_TI5 (TRIGGER_GROUP)
    , INDEX KRSB_QRTZ_FIRED_TRIGGERS_TI6 (INSTANCE_NAME)
    , INDEX KRSB_QRTZ_FIRED_TRIGGERS_TI7 (TRIGGER_NAME)
    , INDEX KRSB_QRTZ_FIRED_TRIGGERS_TI8 (TRIGGER_NAME, TRIGGER_GROUP)
    , INDEX KRSB_QRTZ_FIRED_TRIGGERS_TI9 (IS_VOLATILE)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
;


# -----------------------------------------------------------------------
# KRSB_QRTZ_JOB_DETAILS
# -----------------------------------------------------------------------
CREATE TABLE KRSB_QRTZ_JOB_DETAILS
(
      JOB_NAME VARCHAR(80)
        , JOB_GROUP VARCHAR(80)
        , DESCRIPTION VARCHAR(120)
        , JOB_CLASS_NAME VARCHAR(128) NOT NULL
        , IS_DURABLE VARCHAR(1) NOT NULL
        , IS_VOLATILE VARCHAR(1) NOT NULL
        , IS_STATEFUL VARCHAR(1) NOT NULL
        , REQUESTS_RECOVERY VARCHAR(1) NOT NULL
        , JOB_DATA LONGBLOB
    
    , CONSTRAINT KRSB_QRTZ_JOB_DETAILSP1 PRIMARY KEY(JOB_NAME,JOB_GROUP)


    , INDEX KRSB_QRTZ_JOB_DETAILS_TI1 (REQUESTS_RECOVERY)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
;


# -----------------------------------------------------------------------
# KRSB_QRTZ_JOB_LISTENERS
# -----------------------------------------------------------------------
CREATE TABLE KRSB_QRTZ_JOB_LISTENERS
(
      JOB_NAME VARCHAR(80)
        , JOB_GROUP VARCHAR(80)
        , JOB_LISTENER VARCHAR(80)
    
    , CONSTRAINT KRSB_QRTZ_JOB_LISTENERSP1 PRIMARY KEY(JOB_NAME,JOB_GROUP,JOB_LISTENER)



) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
;


# -----------------------------------------------------------------------
# KRSB_QRTZ_LOCKS
# -----------------------------------------------------------------------
CREATE TABLE KRSB_QRTZ_LOCKS
(
      LOCK_NAME VARCHAR(40)
    
    , CONSTRAINT KRSB_QRTZ_LOCKSP1 PRIMARY KEY(LOCK_NAME)



) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
;


# -----------------------------------------------------------------------
# KRSB_QRTZ_PAUSED_TRIGGER_GRPS
# -----------------------------------------------------------------------
CREATE TABLE KRSB_QRTZ_PAUSED_TRIGGER_GRPS
(
      TRIGGER_GROUP VARCHAR(80)
    
    , CONSTRAINT KRSB_QRTZ_PAUSED_TRIGGER_GRP1 PRIMARY KEY(TRIGGER_GROUP)



) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
;


# -----------------------------------------------------------------------
# KRSB_QRTZ_SCHEDULER_STATE
# -----------------------------------------------------------------------
CREATE TABLE KRSB_QRTZ_SCHEDULER_STATE
(
      INSTANCE_NAME VARCHAR(80)
        , LAST_CHECKIN_TIME DECIMAL(13) NOT NULL
        , CHECKIN_INTERVAL DECIMAL(13) NOT NULL
    
    , CONSTRAINT KRSB_QRTZ_SCHEDULER_STATEP1 PRIMARY KEY(INSTANCE_NAME)



) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
;


# -----------------------------------------------------------------------
# KRSB_QRTZ_SIMPLE_TRIGGERS
# -----------------------------------------------------------------------
CREATE TABLE KRSB_QRTZ_SIMPLE_TRIGGERS
(
      TRIGGER_NAME VARCHAR(80)
        , TRIGGER_GROUP VARCHAR(80)
        , REPEAT_COUNT DECIMAL(7) NOT NULL
        , REPEAT_INTERVAL DECIMAL(12) NOT NULL
        , TIMES_TRIGGERED DECIMAL(7) NOT NULL
    
    , CONSTRAINT KRSB_QRTZ_SIMPLE_TRIGGERSP1 PRIMARY KEY(TRIGGER_NAME,TRIGGER_GROUP)



) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
;


# -----------------------------------------------------------------------
# KRSB_QRTZ_TRIGGERS
# -----------------------------------------------------------------------
CREATE TABLE KRSB_QRTZ_TRIGGERS
(
      TRIGGER_NAME VARCHAR(80)
        , TRIGGER_GROUP VARCHAR(80)
        , JOB_NAME VARCHAR(80) NOT NULL
        , JOB_GROUP VARCHAR(80) NOT NULL
        , IS_VOLATILE VARCHAR(1) NOT NULL
        , DESCRIPTION VARCHAR(120)
        , NEXT_FIRE_TIME DECIMAL(13)
        , PREV_FIRE_TIME DECIMAL(13)
        , PRIORITY DECIMAL(13)
        , TRIGGER_STATE VARCHAR(16) NOT NULL
        , TRIGGER_TYPE VARCHAR(8) NOT NULL
        , START_TIME DECIMAL(13) NOT NULL
        , END_TIME DECIMAL(13)
        , CALENDAR_NAME VARCHAR(80)
        , MISFIRE_INSTR DECIMAL(2)
        , JOB_DATA LONGBLOB
    
    , CONSTRAINT KRSB_QRTZ_TRIGGERSP1 PRIMARY KEY(TRIGGER_NAME,TRIGGER_GROUP)


    , INDEX KRSB_QRTZ_TRIGGERS_TI1 (NEXT_FIRE_TIME)
    , INDEX KRSB_QRTZ_TRIGGERS_TI2 (NEXT_FIRE_TIME, TRIGGER_STATE)
    , INDEX KRSB_QRTZ_TRIGGERS_TI3 (TRIGGER_STATE)
    , INDEX KRSB_QRTZ_TRIGGERS_TI4 (IS_VOLATILE)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
;


# -----------------------------------------------------------------------
# KRSB_QRTZ_TRIGGER_LISTENERS
# -----------------------------------------------------------------------
CREATE TABLE KRSB_QRTZ_TRIGGER_LISTENERS
(
      TRIGGER_NAME VARCHAR(80)
        , TRIGGER_GROUP VARCHAR(80)
        , TRIGGER_LISTENER VARCHAR(80)
    
    , CONSTRAINT KRSB_QRTZ_TRIGGER_LISTENERSP1 PRIMARY KEY(TRIGGER_NAME,TRIGGER_GROUP,TRIGGER_LISTENER)



) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
;


# -----------------------------------------------------------------------
# KR_QRTZ_PAUSED_TRIGGERS_GRPS
# -----------------------------------------------------------------------
CREATE TABLE KR_QRTZ_PAUSED_TRIGGERS_GRPS
(
      TRIGGER_GROUP VARCHAR(80)
    
    , CONSTRAINT KR_QRTZ_PAUSED_TRIGGERS_GRPP1 PRIMARY KEY(TRIGGER_GROUP)



) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
;


# -----------------------------------------------------------------------
# KRNS_LOCK_S
# -----------------------------------------------------------------------
CREATE TABLE KRNS_LOCK_S
(
    id BIGINT not null auto_increment, primary key (id) 
) ENGINE MyISAM
;
ALTER TABLE KRNS_LOCK_S auto_increment = 2000
;

# -----------------------------------------------------------------------
# KRNS_LOOKUP_RSLT_S
# -----------------------------------------------------------------------
CREATE TABLE KRNS_LOOKUP_RSLT_S
(
    id BIGINT not null auto_increment, primary key (id) 
) ENGINE MyISAM
;
ALTER TABLE KRNS_LOOKUP_RSLT_S auto_increment = 2000
;

# -----------------------------------------------------------------------
# KRNS_MAINT_LOCK_S
# -----------------------------------------------------------------------
CREATE TABLE KRNS_MAINT_LOCK_S
(
    id BIGINT not null auto_increment, primary key (id) 
) ENGINE MyISAM
;
ALTER TABLE KRNS_MAINT_LOCK_S auto_increment = 2000
;

# -----------------------------------------------------------------------
# KRNS_NTE_S
# -----------------------------------------------------------------------
CREATE TABLE KRNS_NTE_S
(
    id BIGINT not null auto_increment, primary key (id) 
) ENGINE MyISAM
;
ALTER TABLE KRNS_NTE_S auto_increment = 2020
;

# -----------------------------------------------------------------------
# KRSB_BAM_PARM_S
# -----------------------------------------------------------------------
CREATE TABLE KRSB_BAM_PARM_S
(
    id BIGINT not null auto_increment, primary key (id) 
) ENGINE MyISAM
;
ALTER TABLE KRSB_BAM_PARM_S auto_increment = 2000
;

# -----------------------------------------------------------------------
# KRSB_BAM_S
# -----------------------------------------------------------------------
CREATE TABLE KRSB_BAM_S
(
    id BIGINT not null auto_increment, primary key (id) 
) ENGINE MyISAM
;
ALTER TABLE KRSB_BAM_S auto_increment = 2000
;

# -----------------------------------------------------------------------
# KRSB_MSG_QUE_S
# -----------------------------------------------------------------------
CREATE TABLE KRSB_MSG_QUE_S
(
    id BIGINT not null auto_increment, primary key (id) 
) ENGINE MyISAM
;
ALTER TABLE KRSB_MSG_QUE_S auto_increment = 121
;

--
-- KRNS_NTE_TYP_T
--


INSERT INTO KRNS_NTE_TYP_T (ACTV_IND,NTE_TYP_CD,OBJ_ID,TYP_DESC_TXT,VER_NBR)
  VALUES ('Y','BO','53680C68F5A9AD9BE0404F8189D80A6C','DOCUMENT BUSINESS OBJECT',1)
;
INSERT INTO KRNS_NTE_TYP_T (ACTV_IND,NTE_TYP_CD,OBJ_ID,TYP_DESC_TXT,VER_NBR)
  VALUES ('Y','DH','53680C68F5AAAD9BE0404F8189D80A6C','DOCUMENT HEADER',1)
;

--
-- KRSB_QRTZ_LOCKS
--


INSERT INTO KRSB_QRTZ_LOCKS (LOCK_NAME)
  VALUES ('CALENDAR_ACCESS')
;
INSERT INTO KRSB_QRTZ_LOCKS (LOCK_NAME)
  VALUES ('JOB_ACCESS')
;
INSERT INTO KRSB_QRTZ_LOCKS (LOCK_NAME)
  VALUES ('MISFIRE_ACCESS')
;
INSERT INTO KRSB_QRTZ_LOCKS (LOCK_NAME)
  VALUES ('STATE_ACCESS')
;
INSERT INTO KRSB_QRTZ_LOCKS (LOCK_NAME)
  VALUES ('TRIGGER_ACCESS')
;

ALTER TABLE KRNS_ATT_T
    ADD CONSTRAINT KRNS_ATT_TR1
    FOREIGN KEY (NTE_ID)
    REFERENCES KRNS_NTE_T (NTE_ID)
;

ALTER TABLE KRNS_MAINT_DOC_T
    ADD CONSTRAINT KRNS_MAINT_DOC_TR1
    FOREIGN KEY (DOC_HDR_ID)
    REFERENCES KRNS_DOC_HDR_T (DOC_HDR_ID)
;

ALTER TABLE KRNS_NTE_T
    ADD CONSTRAINT KRNS_NTE_TR1
    FOREIGN KEY (NTE_TYP_CD)
    REFERENCES KRNS_NTE_TYP_T (NTE_TYP_CD)
;

ALTER TABLE KRSB_QRTZ_BLOB_TRIGGERS
    ADD CONSTRAINT KRSB_QRTZ_BLOB_TRIGGERS_TR1
    FOREIGN KEY (TRIGGER_NAME, TRIGGER_GROUP)
    REFERENCES KRSB_QRTZ_TRIGGERS (TRIGGER_NAME, TRIGGER_GROUP)
;

ALTER TABLE KRSB_QRTZ_CRON_TRIGGERS
    ADD CONSTRAINT KRSB_QRTZ_CRON_TRIGGERS_TR1
    FOREIGN KEY (TRIGGER_NAME, TRIGGER_GROUP)
    REFERENCES KRSB_QRTZ_TRIGGERS (TRIGGER_NAME, TRIGGER_GROUP)
;

ALTER TABLE KRSB_QRTZ_JOB_LISTENERS
    ADD CONSTRAINT KRSB_QUARTZ_JOB_LISTENERS_TR1
    FOREIGN KEY (JOB_NAME, JOB_GROUP)
    REFERENCES KRSB_QRTZ_JOB_DETAILS (JOB_NAME, JOB_GROUP)
;

ALTER TABLE KRSB_QRTZ_SIMPLE_TRIGGERS
    ADD CONSTRAINT KRSB_QRTZ_SIMPLE_TRIGGERS_TR1
    FOREIGN KEY (TRIGGER_NAME, TRIGGER_GROUP)
    REFERENCES KRSB_QRTZ_TRIGGERS (TRIGGER_NAME, TRIGGER_GROUP)
;

ALTER TABLE KRSB_QRTZ_TRIGGER_LISTENERS
    ADD CONSTRAINT KRSB_QRTZ_TRIGGER_LISTENE_TR1
    FOREIGN KEY (TRIGGER_NAME, TRIGGER_GROUP)
    REFERENCES KRSB_QRTZ_TRIGGERS (TRIGGER_NAME, TRIGGER_GROUP)
;
