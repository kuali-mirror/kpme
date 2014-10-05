CREATE TABLE "EDO"."EDO_AREA_OF_EXCELLENCE"
  (
    "AREA_OF_EXCELLENCE_TYPE_ID"   NUMBER(8,0),
    "OPTION_KEY" VARCHAR2(3 CHAR),
    "OPTION_VALUE" VARCHAR2(50 BYTE),   
    "CREATE_DATE" DATE,
    "CREATED_BY" VARCHAR2(50 BYTE),
    "LAST_UPDATE_DATE" DATE,
    "UPDATED_BY"  VARCHAR2(50 BYTE),
    CONSTRAINT "AREA_OF_EXCELLENCE_TYPE_P1" PRIMARY KEY ("AREA_OF_EXCELLENCE_TYPE_ID")
    );
    

insert into EDO_AREA_OF_EXCELLENCE(AREA_OF_EXCELLENCE_TYPE_ID,OPTION_KEY,OPTION_VALUE,CREATE_DATE,CREATED_BY,LAST_UPDATE_DATE,UPDATED_BY)values
                                  (1,'R','Research/Creative Activity',to_date('01-APR-13','DD-MON-RR'),'nrmalae',to_date('01-APR-13','DD-MON-RR'),'nrmalae');

insert into EDO_AREA_OF_EXCELLENCE(AREA_OF_EXCELLENCE_TYPE_ID,OPTION_KEY,OPTION_VALUE,CREATE_DATE,CREATED_BY,LAST_UPDATE_DATE,UPDATED_BY)values
                                  (2,'T','Teaching',to_date('01-APR-13','DD-MON-RR'),'nrmalae',to_date('01-APR-13','DD-MON-RR'),'nrmalae');

insert into EDO_AREA_OF_EXCELLENCE(AREA_OF_EXCELLENCE_TYPE_ID,OPTION_KEY,OPTION_VALUE,CREATE_DATE,CREATED_BY,LAST_UPDATE_DATE,UPDATED_BY)values
                                  (3,'S','Service Engagement',to_date('01-APR-13','DD-MON-RR'),'nrmalae',to_date('01-APR-13','DD-MON-RR'),'nrmalae');
                                  
insert into EDO_AREA_OF_EXCELLENCE(AREA_OF_EXCELLENCE_TYPE_ID,OPTION_KEY,OPTION_VALUE,CREATE_DATE,CREATED_BY,LAST_UPDATE_DATE,UPDATED_BY)values
                                  (4,'B','Balanced Case',to_date('01-APR-13','DD-MON-RR'),'nrmalae',to_date('01-APR-13','DD-MON-RR'),'nrmalae');
