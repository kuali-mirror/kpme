
INSERT INTO KREW_RULE_ATTR_T (RULE_ATTR_ID,NM,LBL,RULE_ATTR_TYP_CD,CLS_NM,APPL_ID,DESC_TXT,XML,OBJ_ID,VER_NBR) VALUES ('1645','BalanceTransferRoleAttribute','BalanceTransferRoleAttribute','RuleAttribute','org.kuali.kpme.tklm.leave.workflow.BalanceTransferRoleAttribute','KPME','BalanceTransferRoleAttribute',null,'445eaadd-be2e-4047-981d-35062db63034',1)
;
INSERT INTO KREW_RULE_ATTR_T (RULE_ATTR_ID,NM,LBL,RULE_ATTR_TYP_CD,CLS_NM,APPL_ID,DESC_TXT,XML,OBJ_ID,VER_NBR) VALUES ('1646','LeaveCalendarWorkAreaSearchAttribute','LeaveCalendarWorkAreaSearchAttribute','SearchableXmlAttribute','org.kuali.rice.kew.docsearch.xml.StandardGenericXMLSearchableAttribute','KPME','LeaveCalendarWorkAreaSearchAttribute','<searchingConfig>\n                <fieldDef name="workarea" title="Work Area">\n                    <display>\n                        <type>text</type>\n                    </display>\n                    <fieldEvaluation>\n                        <xpathexpression>//WORKAREA/@value</xpathexpression>\n                    </fieldEvaluation>\n                </fieldDef>\n            </searchingConfig>\n','84147fda-bd4f-4ecc-9708-f77ae665441d',1)
;
INSERT INTO KREW_RTE_TMPL_S VALUES (NULL)
;
INSERT INTO KREW_RULE_ATTR_T (RULE_ATTR_ID,NM,LBL,RULE_ATTR_TYP_CD,CLS_NM,APPL_ID,DESC_TXT,XML,OBJ_ID,VER_NBR) VALUES ('1647','LeaveCalendarPrincipalNameSearchAttribute','LeaveCalendarPrincipalNameSearchAttribute','SearchableXmlAttribute','org.kuali.rice.kew.docsearch.xml.StandardGenericXMLSearchableAttribute','KPME','LeaveCalendarPrincipalNameSearchAttribute','<searchingConfig>\n                <fieldDef name="principalName" title="Principal Name">\n                    <display>\n                        <type>text</type>\n                    </display>\n                    <fieldEvaluation>\n                        <xpathexpression>//PRINCIPALNAME/@value</xpathexpression>\n                    </fieldEvaluation>\n                </fieldDef>\n            </searchingConfig>\n','623a33fc-2aac-40f2-9cf3-a55ba260a820',1)
;
INSERT INTO KREW_RULE_ATTR_T (RULE_ATTR_ID,NM,LBL,RULE_ATTR_TYP_CD,CLS_NM,APPL_ID,DESC_TXT,XML,OBJ_ID,VER_NBR) VALUES ('1648','LeaveCalendarCalendarEntrySearchAttribute','LeaveCalendarCalendarEntrySearchAttribute','SearchableXmlAttribute','org.kuali.rice.kew.docsearch.xml.StandardGenericXMLSearchableAttribute','KPME','LeaveCalendarCalendarEntrySearchAttribute','<searchingConfig>\n                <fieldDef name="calendarEntryId" title="Calendar Entry Id">\n                    <display>\n                        <type>text</type>\n                    </display>\n                    <fieldEvaluation>\n                        <xpathexpression>//CALENTRYID/@value</xpathexpression>\n                    </fieldEvaluation>\n                </fieldDef>\n            </searchingConfig>\n','9deab750-36ab-4f7f-bb47-2ffd2b682343',1)
;
INSERT INTO KREW_RULE_ATTR_T (RULE_ATTR_ID,NM,LBL,RULE_ATTR_TYP_CD,CLS_NM,APPL_ID,DESC_TXT,XML,OBJ_ID,VER_NBR) VALUES ('1649','LeaveCalendarGroupKeySearchAttribute','LeaveCalendarGroupKeySearchAttribute','SearchableXmlAttribute','org.kuali.rice.kew.docsearch.xml.StandardGenericXMLSearchableAttribute','KPME','LeaveCalendarGroupKeySearchAttribute','<searchingConfig>\n                <fieldDef name="groupKey" title="Group Key Code">\n                    <display>\n                        <type>text</type>\n                    </display>\n                    <fieldEvaluation>\n                        <xpathexpression>//GROUPKEY/@value</xpathexpression>\n                    </fieldEvaluation>\n                </fieldDef>\n            </searchingConfig>\n','beb0627b-4878-4eb8-94cd-daf80799d84a',1)
;
INSERT INTO KREW_RULE_ATTR_T (RULE_ATTR_ID,NM,LBL,RULE_ATTR_TYP_CD,CLS_NM,APPL_ID,DESC_TXT,XML,OBJ_ID,VER_NBR) VALUES ('1650','LeaveCalendarDepartmentSearchAttribute','LeaveCalendarDepartmentSearchAttribute','SearchableXmlAttribute','org.kuali.rice.kew.docsearch.xml.StandardGenericXMLSearchableAttribute','KPME','LeaveCalendarDepartmentSearchAttribute','<searchingConfig>\n                <fieldDef name="department" title="Department">\n                    <display>\n                        <type>text</type>\n                    </display>\n                    <fieldEvaluation>\n                        <xpathexpression>//DEPARTMENT/@value</xpathexpression>\n                    </fieldEvaluation>\n                </fieldDef>\n            </searchingConfig>\n','1ca9402c-f8b2-4a60-9bfe-c9b3996909ef',1)
;
INSERT INTO KREW_RULE_ATTR_T (RULE_ATTR_ID,NM,LBL,RULE_ATTR_TYP_CD,CLS_NM,APPL_ID,DESC_TXT,XML,OBJ_ID,VER_NBR) VALUES ('1651','LeaveCalendarSalGroupSearchAttribute','LeaveCalendarSalGroupSearchAttribute','SearchableXmlAttribute','org.kuali.rice.kew.docsearch.xml.StandardGenericXMLSearchableAttribute','KPME','LeaveCalendarSalGroupSearchAttribute','<searchingConfig>\n                <fieldDef name="salGroup" title="Salary Group">\n                    <display>\n                        <type>text</type>\n                    </display>\n                    <fieldEvaluation>\n                        <xpathexpression>//SALGROUP/@value</xpathexpression>\n                    </fieldEvaluation>\n                </fieldDef>\n            </searchingConfig>\n','19a12fd7-7c5a-4464-a1e9-45218722dbfa',1)
;
INSERT INTO KREW_RULE_ATTR_T (RULE_ATTR_ID,NM,LBL,RULE_ATTR_TYP_CD,CLS_NM,APPL_ID,DESC_TXT,XML,OBJ_ID,VER_NBR) VALUES ('1652','LeaveCalendarCalEndSearchAttribute','LeaveCalendarCalEndSearchAttribute','SearchableXmlAttribute','org.kuali.rice.kew.docsearch.xml.StandardGenericXMLSearchableAttribute','KPME','LeaveCalendarCalEndSearchAttribute','<searchingConfig>\n                <fieldDef name="payCalendarEnd" title="Pay Calendar End Date">\n                    <display>\n                        <type>text</type>\n                    </display>\n                    <fieldEvaluation>\n                        <xpathexpression>//PAYENDDATE/@value</xpathexpression>\n                    </fieldEvaluation>\n                </fieldDef>\n            </searchingConfig>\n','e85e6573-5490-4193-84c3-dc18c1dad77b',1)
;
INSERT INTO KREW_RULE_ATTR_T (RULE_ATTR_ID,NM,LBL,RULE_ATTR_TYP_CD,CLS_NM,APPL_ID,DESC_TXT,XML,OBJ_ID,VER_NBR) VALUES ('1653','LeaveCalendarRoleAttribute','LeaveCalendarRoleAttribute','RuleAttribute','org.kuali.kpme.tklm.leave.workflow.LeaveCalendarRoleAttribute','KPME','LeaveCalendarRoleAttribute',null,'3288be5c-68d0-4de1-b62c-a77f05acda34',1)
;
INSERT INTO KREW_RULE_ATTR_T (RULE_ATTR_ID,NM,LBL,RULE_ATTR_TYP_CD,CLS_NM,APPL_ID,DESC_TXT,XML,OBJ_ID,VER_NBR) VALUES ('1654','LeavePayoutRoleAttribute','LeavePayoutRoleAttribute','RuleAttribute','org.kuali.kpme.tklm.leave.workflow.LeavePayoutRoleAttribute','KPME','LeavePayoutRoleAttribute',null,'eb928fb7-0692-499b-80be-1e4a361e79be',1)
;
INSERT INTO KREW_RULE_ATTR_T (RULE_ATTR_ID,NM,LBL,RULE_ATTR_TYP_CD,CLS_NM,APPL_ID,DESC_TXT,XML,OBJ_ID,VER_NBR) VALUES ('1655','LeaveRequestWorkAreaSearchAttribute','LeaveRequestWorkAreaSearchAttribute','SearchableXmlAttribute','org.kuali.rice.kew.docsearch.xml.StandardGenericXMLSearchableAttribute','KPME','LeaveRequestWorkAreaSearchAttribute','<searchingConfig>\n                <fieldDef name="workarea" title="Work Area">\n                    <display>\n                        <type>text</type>\n                    </display>\n                    <fieldEvaluation>\n                        <xpathexpression>//WORKAREA/@value</xpathexpression>\n                    </fieldEvaluation>\n                </fieldDef>\n            </searchingConfig>\n','554863cc-f4f1-4fb4-9ef9-a30b2c921766',1)
;
INSERT INTO KREW_RULE_ATTR_T (RULE_ATTR_ID,NM,LBL,RULE_ATTR_TYP_CD,CLS_NM,APPL_ID,DESC_TXT,XML,OBJ_ID,VER_NBR) VALUES ('1656','LeaveRequestPrincipalNameSearchAttribute','LeaveRequestPrincipalNameSearchAttribute','SearchableXmlAttribute','org.kuali.rice.kew.docsearch.xml.StandardGenericXMLSearchableAttribute','KPME','LeaveRequestPrincipalNameSearchAttribute','<searchingConfig>\n                <fieldDef name="principalName" title="Principal Name">\n                    <display>\n                        <type>text</type>\n                    </display>\n                    <fieldEvaluation>\n                        <xpathexpression>//PRINCIPALNAME/@value</xpathexpression>\n                    </fieldEvaluation>\n                </fieldDef>\n            </searchingConfig>\n','db93412a-33ec-426f-80c3-a95099ea1a8d',1)
;
INSERT INTO KREW_RULE_ATTR_T (RULE_ATTR_ID,NM,LBL,RULE_ATTR_TYP_CD,CLS_NM,APPL_ID,DESC_TXT,XML,OBJ_ID,VER_NBR) VALUES ('1657','LeaveRequestCalendarEntrySearchAttribute','LeaveRequestCalendarEntrySearchAttribute','SearchableXmlAttribute','org.kuali.rice.kew.docsearch.xml.StandardGenericXMLSearchableAttribute','KPME','LeaveRequestCalendarEntrySearchAttribute','<searchingConfig>\n                <fieldDef name="calendarEntryId" title="Calendar Entry Id">\n                    <display>\n                        <type>text</type>\n                    </display>\n                    <fieldEvaluation>\n                        <xpathexpression>//CALENTRYID/@value</xpathexpression>\n                    </fieldEvaluation>\n                </fieldDef>\n            </searchingConfig>\n','b80e421d-ac5f-45ed-a3ac-fe9b2c5fc117',1)
;
INSERT INTO KREW_RULE_ATTR_T (RULE_ATTR_ID,NM,LBL,RULE_ATTR_TYP_CD,CLS_NM,APPL_ID,DESC_TXT,XML,OBJ_ID,VER_NBR) VALUES ('1658','LeaveRequestGroupKeySearchAttribute','LeaveRequestGroupKeySearchAttribute','SearchableXmlAttribute','org.kuali.rice.kew.docsearch.xml.StandardGenericXMLSearchableAttribute','KPME','LeaveRequestGroupKeySearchAttribute','<searchingConfig>\n                <fieldDef name="groupKey" title="Group Key Code">\n                    <display>\n                        <type>text</type>\n                    </display>\n                    <fieldEvaluation>\n                        <xpathexpression>//GROUPKEY/@value</xpathexpression>\n                    </fieldEvaluation>\n                </fieldDef>\n            </searchingConfig>\n','a48cffc4-cf93-40fa-90d9-bf05fa9d93eb',1)
;
INSERT INTO KREW_RULE_ATTR_T (RULE_ATTR_ID,NM,LBL,RULE_ATTR_TYP_CD,CLS_NM,APPL_ID,DESC_TXT,XML,OBJ_ID,VER_NBR) VALUES ('1659','LeaveRequestDepartmentSearchAttribute','LeaveRequestDepartmentSearchAttribute','SearchableXmlAttribute','org.kuali.rice.kew.docsearch.xml.StandardGenericXMLSearchableAttribute','KPME','LeaveRequestDepartmentSearchAttribute','<searchingConfig>\n                <fieldDef name="department" title="Department">\n                    <display>\n                        <type>text</type>\n                    </display>\n                    <fieldEvaluation>\n                        <xpathexpression>//DEPARTMENT/@value</xpathexpression>\n                    </fieldEvaluation>\n                </fieldDef>\n            </searchingConfig>\n','ddc98dd7-7c3a-453c-b3d6-aeecaf0f2f43',1)
;
INSERT INTO KREW_RULE_ATTR_T (RULE_ATTR_ID,NM,LBL,RULE_ATTR_TYP_CD,CLS_NM,APPL_ID,DESC_TXT,XML,OBJ_ID,VER_NBR) VALUES ('1660','LeaveRequestSalGroupSearchAttribute','LeaveRequestSalGroupSearchAttribute','SearchableXmlAttribute','org.kuali.rice.kew.docsearch.xml.StandardGenericXMLSearchableAttribute','KPME','LeaveRequestSalGroupSearchAttribute','<searchingConfig>\n                <fieldDef name="salGroup" title="Salary Group">\n                    <display>\n                        <type>text</type>\n                    </display>\n                    <fieldEvaluation>\n                        <xpathexpression>//SALGROUP/@value</xpathexpression>\n                    </fieldEvaluation>\n                </fieldDef>\n            </searchingConfig>\n','3cb7fbce-2975-4b62-bfcf-2c5c8a9ab104',1)
;
INSERT INTO KREW_RULE_ATTR_T (RULE_ATTR_ID,NM,LBL,RULE_ATTR_TYP_CD,CLS_NM,APPL_ID,DESC_TXT,XML,OBJ_ID,VER_NBR) VALUES ('1661','LeaveRequestCalEndSearchAttribute','LeaveRequestCalEndSearchAttribute','SearchableXmlAttribute','org.kuali.rice.kew.docsearch.xml.StandardGenericXMLSearchableAttribute','KPME','LeaveRequestCalEndSearchAttribute','<searchingConfig>\n                <fieldDef name="payCalendarEnd" title="Pay Calendar End Date">\n                    <display>\n                        <type>text</type>\n                    </display>\n                    <fieldEvaluation>\n                        <xpathexpression>//PAYENDDATE/@value</xpathexpression>\n                    </fieldEvaluation>\n                </fieldDef>\n            </searchingConfig>\n','8c592c01-ab0e-412f-9a16-6195c10b143d',1)
;
INSERT INTO KREW_RULE_ATTR_T (RULE_ATTR_ID,NM,LBL,RULE_ATTR_TYP_CD,CLS_NM,APPL_ID,DESC_TXT,XML,OBJ_ID,VER_NBR) VALUES ('1662','LeaveRequestRoleAttribute','LeaveRequestRoleAttribute','RuleAttribute','org.kuali.kpme.tklm.leave.workflow.LeaveRequestRoleAttribute','KPME','LeaveRequestRoleAttribute',null,'6960320f-9a53-485e-a4eb-0f1b7ec86e29',1)
;
INSERT INTO KREW_RULE_ATTR_T (RULE_ATTR_ID,NM,LBL,RULE_ATTR_TYP_CD,CLS_NM,APPL_ID,DESC_TXT,XML,OBJ_ID,VER_NBR) VALUES ('1663','LeaveRequestCustomActionListAttribute','LeaveRequestCustomActionListAttribute','ActionListAttribute','org.kuali.kpme.tklm.leave.leaverequest.LeaveRequestCustomActionListAttribute','KPME','LeaveRequestCustomActionListAttribute',null,'865c539e-cffc-438e-a252-bd22146b5e97',1)
;
INSERT INTO KREW_RULE_ATTR_T (RULE_ATTR_ID,NM,LBL,RULE_ATTR_TYP_CD,CLS_NM,APPL_ID,DESC_TXT,XML,OBJ_ID,VER_NBR) VALUES ('1664','MissedPunchDataDictionarySearchableAttribute','Test Data Dictionary Searchable Attribute','SearchableAttribute','{http://kpme.kuali.org/tklm/v2_0}missedPunchDocSearchCustomizer','KPME','DD searchable attribute for testing purposes',null,'6c4ae9ad-176e-4c7e-97d3-8639057077fc',1)
;
INSERT INTO KREW_RULE_ATTR_T (RULE_ATTR_ID,NM,LBL,RULE_ATTR_TYP_CD,CLS_NM,APPL_ID,DESC_TXT,XML,OBJ_ID,VER_NBR) VALUES ('1665','MissedPunchSecurityFilterAttribute','MissedPunchSecurityFilterAttribute','DocumentSecurityAttribute','{http://kpme.kuali.org/tklm/v2_0}missedPunchDocSearchSecurity','KPME','MissedPunchSecurityFilterAttribute',null,'41e31259-3318-4a6b-b094-0d4517d26737',1)
;
INSERT INTO KREW_RULE_ATTR_T (RULE_ATTR_ID,NM,LBL,RULE_ATTR_TYP_CD,CLS_NM,APPL_ID,DESC_TXT,XML,OBJ_ID,VER_NBR) VALUES ('1666','TimesheetWorkAreaSearchAttribute','TimesheetWorkAreaSearchAttribute','SearchableXmlAttribute','org.kuali.rice.kew.docsearch.xml.StandardGenericXMLSearchableAttribute','KPME','TimesheetWorkAreaSearchAttribute','<searchingConfig>\n                <fieldDef name="workarea" title="Work Area">\n                    <display>\n                        <type>text</type>\n                    </display>\n                    <fieldEvaluation>\n                        <xpathexpression>//WORKAREA/@value</xpathexpression>\n                    </fieldEvaluation>\n                </fieldDef>\n            </searchingConfig>\n','b041d56b-2e78-40c5-b472-b9a7b3e0989f',1)
;
INSERT INTO KREW_RTE_TMPL_S VALUES (NULL)
;
INSERT INTO KREW_RULE_ATTR_T (RULE_ATTR_ID,NM,LBL,RULE_ATTR_TYP_CD,CLS_NM,APPL_ID,DESC_TXT,XML,OBJ_ID,VER_NBR) VALUES ('1667','TimesheetPrincipalNameSearchAttribute','TimesheetPrincipalNameSearchAttribute','SearchableXmlAttribute','org.kuali.rice.kew.docsearch.xml.StandardGenericXMLSearchableAttribute','KPME','TimesheetPrincipalNameSearchAttribute','<searchingConfig>\n                <fieldDef name="principalName" title="Principal Name">\n                    <display>\n                        <type>text</type>\n                    </display>\n                    <fieldEvaluation>\n                        <xpathexpression>//PRINCIPALNAME/@value</xpathexpression>\n                    </fieldEvaluation>\n                </fieldDef>\n            </searchingConfig>\n','d8deac83-9da4-4240-bf2d-3aaf4cff48ae',1)
;
INSERT INTO KREW_RTE_TMPL_S VALUES (NULL)
;
INSERT INTO KREW_RULE_ATTR_T (RULE_ATTR_ID,NM,LBL,RULE_ATTR_TYP_CD,CLS_NM,APPL_ID,DESC_TXT,XML,OBJ_ID,VER_NBR) VALUES ('1668','TimesheetCalendarEntrySearchAttribute','TimesheetCalendarEntrySearchAttribute','SearchableXmlAttribute','org.kuali.rice.kew.docsearch.xml.StandardGenericXMLSearchableAttribute','KPME','TimesheetCalendarEntrySearchAttribute','<searchingConfig>\n                <fieldDef name="calendarEntryId" title="Calendar Entry Id">\n                    <display>\n                        <type>text</type>\n                    </display>\n                    <fieldEvaluation>\n                        <xpathexpression>//CALENTRYID/@value</xpathexpression>\n                    </fieldEvaluation>\n                </fieldDef>\n            </searchingConfig>\n','ab090edf-d3f9-420e-b0ac-3109e0391444',1)
;
INSERT INTO KREW_RTE_TMPL_S VALUES (NULL)
;
INSERT INTO KREW_RULE_ATTR_T (RULE_ATTR_ID,NM,LBL,RULE_ATTR_TYP_CD,CLS_NM,APPL_ID,DESC_TXT,XML,OBJ_ID,VER_NBR) VALUES ('1669','TimesheetGroupKeySearchAttribute','TimesheetGroupKeySearchAttribute','SearchableXmlAttribute','org.kuali.rice.kew.docsearch.xml.StandardGenericXMLSearchableAttribute','KPME','TimesheetGroupKeySearchAttribute','<searchingConfig>\n                <fieldDef name="groupKey" title="Group Key Code">\n                    <display>\n                        <type>text</type>\n                    </display>\n                    <fieldEvaluation>\n                        <xpathexpression>//GROUPKEY/@value</xpathexpression>\n                    </fieldEvaluation>\n                </fieldDef>\n            </searchingConfig>\n','29ea0f25-4a48-4adb-b5f8-3d388be6b36a',1)
;
INSERT INTO KREW_RTE_TMPL_S VALUES (NULL)
;
INSERT INTO KREW_RULE_ATTR_T (RULE_ATTR_ID,NM,LBL,RULE_ATTR_TYP_CD,CLS_NM,APPL_ID,DESC_TXT,XML,OBJ_ID,VER_NBR) VALUES ('1670','TimesheetDepartmentSearchAttribute','TimesheetDepartmentSearchAttribute','SearchableXmlAttribute','org.kuali.rice.kew.docsearch.xml.StandardGenericXMLSearchableAttribute','KPME','TimesheetDepartmentSearchAttribute','<searchingConfig>\n                <fieldDef name="department" title="Department">\n                    <display>\n                        <type>text</type>\n                    </display>\n                    <fieldEvaluation>\n                        <xpathexpression>//DEPARTMENT/@value</xpathexpression>\n                    </fieldEvaluation>\n                </fieldDef>\n            </searchingConfig>\n','02d0b2a9-d40c-4095-a503-cdc56eca8890',1)
;
INSERT INTO KREW_RTE_TMPL_S VALUES (NULL)
;
INSERT INTO KREW_RULE_ATTR_T (RULE_ATTR_ID,NM,LBL,RULE_ATTR_TYP_CD,CLS_NM,APPL_ID,DESC_TXT,XML,OBJ_ID,VER_NBR) VALUES ('1671','TimesheetSalGroupSearchAttribute','TimesheetSalGroupSearchAttribute','SearchableXmlAttribute','org.kuali.rice.kew.docsearch.xml.StandardGenericXMLSearchableAttribute','KPME','TimesheetSalGroupSearchAttribute','<searchingConfig>\n                <fieldDef name="salGroup" title="Salary Group">\n                    <display>\n                        <type>text</type>\n                    </display>\n                    <fieldEvaluation>\n                        <xpathexpression>//SALGROUP/@value</xpathexpression>\n                    </fieldEvaluation>\n                </fieldDef>\n            </searchingConfig>\n','2f2fb939-20c3-47ae-8cce-465be26da9f8',1)
;
INSERT INTO KREW_RTE_TMPL_S VALUES (NULL)
;
INSERT INTO KREW_RULE_ATTR_T (RULE_ATTR_ID,NM,LBL,RULE_ATTR_TYP_CD,CLS_NM,APPL_ID,DESC_TXT,XML,OBJ_ID,VER_NBR) VALUES ('1672','TimesheetPayCalEndSearchAttribute','TimesheetPayCalEndSearchAttribute','SearchableXmlAttribute','org.kuali.rice.kew.docsearch.xml.StandardGenericXMLSearchableAttribute','KPME','TimesheetPayCalEndSearchAttribute','<searchingConfig>\n                <fieldDef name="payCalendarEnd" title="Pay Calendar End Date">\n                    <display>\n                        <type>text</type>\n                    </display>\n                    <fieldEvaluation>\n                        <xpathexpression>//PAYENDDATE/@value</xpathexpression>\n                    </fieldEvaluation>\n                </fieldDef>\n	        </searchingConfig>\n','3569afc4-f96a-4ae9-9cc4-1f6856ead52a',1)
;
INSERT INTO KREW_RTE_TMPL_S VALUES (NULL)
;
INSERT INTO KREW_RULE_ATTR_T (RULE_ATTR_ID,NM,LBL,RULE_ATTR_TYP_CD,CLS_NM,APPL_ID,DESC_TXT,XML,OBJ_ID,VER_NBR) VALUES ('1673','TimesheetRoleAttribute','TimesheetRoleAttribute','RuleAttribute','org.kuali.kpme.tklm.time.workflow.TimesheetRoleAttribute','KPME','TimesheetRoleAttribute',null,'e49fb877-4345-442d-a68a-a0593705de52',1)
;
INSERT INTO KREW_RTE_TMPL_S VALUES (NULL)
;
INSERT INTO KREW_RULE_TMPL_T (RULE_TMPL_ID,NM,RULE_TMPL_DESC,DLGN_RULE_TMPL_ID,OBJ_ID,VER_NBR) VALUES ('1674','BalanceTransferRuleTemplate','BalanceTransferRuleTemplate',null,'74ab1b5b-2294-4fd7-a8a9-ac667c686a10',1)
;
INSERT INTO KREW_RTE_TMPL_S VALUES (NULL)
;
INSERT INTO KREW_RULE_TMPL_ATTR_T (RULE_TMPL_ATTR_ID,RULE_TMPL_ID,RULE_ATTR_ID,REQ_IND,ACTV_IND,DSPL_ORD,DFLT_VAL,OBJ_ID,VER_NBR) VALUES ('1675','1674','1645',0,1,0,null,'c1bd782b-786c-4ff5-b4a0-9eab6a1fb8a7',1)
;
UPDATE KREW_RULE_TMPL_T SET NM='BalanceTransferRuleTemplate',RULE_TMPL_DESC='BalanceTransferRuleTemplate',DLGN_RULE_TMPL_ID=null,OBJ_ID='74ab1b5b-2294-4fd7-a8a9-ac667c686a10',VER_NBR=2 WHERE RULE_TMPL_ID = '1674'  AND VER_NBR = 1
;
UPDATE KREW_RULE_TMPL_ATTR_T SET RULE_TMPL_ID='1674',RULE_ATTR_ID='1645',REQ_IND=0,ACTV_IND=1,DSPL_ORD=0,DFLT_VAL=null,OBJ_ID='c1bd782b-786c-4ff5-b4a0-9eab6a1fb8a7',VER_NBR=2 WHERE RULE_TMPL_ATTR_ID = '1675'  AND VER_NBR = 1
;
INSERT INTO KREW_RTE_TMPL_S VALUES (NULL)
;
INSERT INTO KREW_RULE_TMPL_T (RULE_TMPL_ID,NM,RULE_TMPL_DESC,DLGN_RULE_TMPL_ID,OBJ_ID,VER_NBR) VALUES ('1676','LeaveCalendarRuleTemplate','LeaveCalendarRuleTemplate',null,'018d8851-0390-4d66-969a-2df2eda17b15',1)
;
INSERT INTO KREW_RTE_TMPL_S VALUES (NULL)
;
INSERT INTO KREW_RULE_TMPL_ATTR_T (RULE_TMPL_ATTR_ID,RULE_TMPL_ID,RULE_ATTR_ID,REQ_IND,ACTV_IND,DSPL_ORD,DFLT_VAL,OBJ_ID,VER_NBR) VALUES ('1677','1676','1653',0,1,0,null,'a113fecd-b0ac-4404-b08c-f673bd2fe556',1)
;
UPDATE KREW_RULE_TMPL_T SET NM='LeaveCalendarRuleTemplate',RULE_TMPL_DESC='LeaveCalendarRuleTemplate',DLGN_RULE_TMPL_ID=null,OBJ_ID='018d8851-0390-4d66-969a-2df2eda17b15',VER_NBR=2 WHERE RULE_TMPL_ID = '1676'  AND VER_NBR = 1
;
UPDATE KREW_RULE_TMPL_ATTR_T SET RULE_TMPL_ID='1676',RULE_ATTR_ID='1653',REQ_IND=0,ACTV_IND=1,DSPL_ORD=0,DFLT_VAL=null,OBJ_ID='a113fecd-b0ac-4404-b08c-f673bd2fe556',VER_NBR=2 WHERE RULE_TMPL_ATTR_ID = '1677'  AND VER_NBR = 1
;
INSERT INTO KREW_RTE_TMPL_S VALUES (NULL)
;
INSERT INTO KREW_RULE_TMPL_T (RULE_TMPL_ID,NM,RULE_TMPL_DESC,DLGN_RULE_TMPL_ID,OBJ_ID,VER_NBR) VALUES ('1678','LeavePayoutRuleTemplate','LeavePayoutRuleTemplate',null,'3df27989-fb87-450b-a76e-77a01cc2ee41',1)
;
INSERT INTO KREW_RTE_TMPL_S VALUES (NULL)
;
INSERT INTO KREW_RULE_TMPL_ATTR_T (RULE_TMPL_ATTR_ID,RULE_TMPL_ID,RULE_ATTR_ID,REQ_IND,ACTV_IND,DSPL_ORD,DFLT_VAL,OBJ_ID,VER_NBR) VALUES ('1679','1678','1654',0,1,0,null,'38d1cfc5-fe23-4055-b22a-bbc8a3d03e8b',1)
;
UPDATE KREW_RULE_TMPL_T SET NM='LeavePayoutRuleTemplate',RULE_TMPL_DESC='LeavePayoutRuleTemplate',DLGN_RULE_TMPL_ID=null,OBJ_ID='3df27989-fb87-450b-a76e-77a01cc2ee41',VER_NBR=2 WHERE RULE_TMPL_ID = '1678'  AND VER_NBR = 1
;
UPDATE KREW_RULE_TMPL_ATTR_T SET RULE_TMPL_ID='1678',RULE_ATTR_ID='1654',REQ_IND=0,ACTV_IND=1,DSPL_ORD=0,DFLT_VAL=null,OBJ_ID='38d1cfc5-fe23-4055-b22a-bbc8a3d03e8b',VER_NBR=2 WHERE RULE_TMPL_ATTR_ID = '1679'  AND VER_NBR = 1
;
INSERT INTO KREW_RTE_TMPL_S VALUES (NULL)
;
INSERT INTO KREW_RULE_TMPL_T (RULE_TMPL_ID,NM,RULE_TMPL_DESC,DLGN_RULE_TMPL_ID,OBJ_ID,VER_NBR) VALUES ('1680','LeaveRequestRuleTemplate','LeaveRequestRuleTemplate',null,'f65aa3ee-828d-4e8a-9e3b-3482a859f366',1)
;
INSERT INTO KREW_RTE_TMPL_S VALUES (NULL)
;
INSERT INTO KREW_RULE_TMPL_ATTR_T (RULE_TMPL_ATTR_ID,RULE_TMPL_ID,RULE_ATTR_ID,REQ_IND,ACTV_IND,DSPL_ORD,DFLT_VAL,OBJ_ID,VER_NBR) VALUES ('1681','1680','1662',0,1,0,null,'c1a3c4f8-675b-46fd-bb4b-5cc898568bb1',1)
;
UPDATE KREW_RULE_TMPL_T SET NM='LeaveRequestRuleTemplate',RULE_TMPL_DESC='LeaveRequestRuleTemplate',DLGN_RULE_TMPL_ID=null,OBJ_ID='f65aa3ee-828d-4e8a-9e3b-3482a859f366',VER_NBR=2 WHERE RULE_TMPL_ID = '1680'  AND VER_NBR = 1
;
UPDATE KREW_RULE_TMPL_ATTR_T SET RULE_TMPL_ID='1680',RULE_ATTR_ID='1662',REQ_IND=0,ACTV_IND=1,DSPL_ORD=0,DFLT_VAL=null,OBJ_ID='c1a3c4f8-675b-46fd-bb4b-5cc898568bb1',VER_NBR=2 WHERE RULE_TMPL_ATTR_ID = '1681'  AND VER_NBR = 1
;
INSERT INTO KREW_RTE_TMPL_S VALUES (NULL)
;
INSERT INTO KREW_RULE_TMPL_T (RULE_TMPL_ID,NM,RULE_TMPL_DESC,DLGN_RULE_TMPL_ID,OBJ_ID,VER_NBR) VALUES ('1682','TimesheetRuleTemplate','TimesheetRuleTemplate',null,'708ecae4-f949-4bfe-a1a1-237b2a5342bb',1)
;
INSERT INTO KREW_RTE_TMPL_S VALUES (NULL)
;
INSERT INTO KREW_RULE_TMPL_ATTR_T (RULE_TMPL_ATTR_ID,RULE_TMPL_ID,RULE_ATTR_ID,REQ_IND,ACTV_IND,DSPL_ORD,DFLT_VAL,OBJ_ID,VER_NBR) VALUES ('1683','1682','1673',0,1,0,null,'b34c97bd-8322-4dfc-89bc-299112ef390c',1)
;
UPDATE KREW_RULE_TMPL_T SET NM='TimesheetRuleTemplate',RULE_TMPL_DESC='TimesheetRuleTemplate',DLGN_RULE_TMPL_ID=null,OBJ_ID='708ecae4-f949-4bfe-a1a1-237b2a5342bb',VER_NBR=2 WHERE RULE_TMPL_ID = '1682'  AND VER_NBR = 1
;
UPDATE KREW_RULE_TMPL_ATTR_T SET RULE_TMPL_ID='1682',RULE_ATTR_ID='1673',REQ_IND=0,ACTV_IND=1,DSPL_ORD=0,DFLT_VAL=null,OBJ_ID='b34c97bd-8322-4dfc-89bc-299112ef390c',VER_NBR=2 WHERE RULE_TMPL_ATTR_ID = '1683'  AND VER_NBR = 1
;
UPDATE KREW_DOC_TYP_T SET PARNT_ID='2681',DOC_TYP_NM='KpmeDocument',DOC_TYP_VER_NBR=3,ACTV_IND=1,CUR_IND=0,DOC_TYP_DESC=null,LBL='Base KPME Rule Document',PREV_DOC_TYP_VER_NBR=null,DOC_HDR_ID=null,DOC_HDLR_URL='${kuali.docHandler.url.prefix}/kr/maintenance.do?methodToCall=docHandler',HELP_DEF_URL=null,DOC_SEARCH_HELP_URL=null,POST_PRCSR=null,AUTHORIZER=null,GRP_ID=null,BLNKT_APPR_GRP_ID=null,BLNKT_APPR_PLCY=null,RPT_GRP_ID=null,RTE_VER_NBR='1',NOTIFY_ADDR=null,SEC_XML=null,EMAIL_XSL=null,APPL_ID='KPME',OBJ_ID='da327e97-1bf1-11e4-8d4a-d30dc8c47e2b',VER_NBR=1 WHERE DOC_TYP_ID = 'KPME0001'  AND VER_NBR = 0
;
INSERT INTO KREW_DOC_HDR_S VALUES (NULL)
;
INSERT INTO KREW_DOC_TYP_T (DOC_TYP_ID,PARNT_ID,DOC_TYP_NM,DOC_TYP_VER_NBR,ACTV_IND,CUR_IND,DOC_TYP_DESC,LBL,PREV_DOC_TYP_VER_NBR,DOC_HDR_ID,DOC_HDLR_URL,HELP_DEF_URL,DOC_SEARCH_HELP_URL,POST_PRCSR,AUTHORIZER,GRP_ID,BLNKT_APPR_GRP_ID,BLNKT_APPR_PLCY,RPT_GRP_ID,RTE_VER_NBR,NOTIFY_ADDR,SEC_XML,EMAIL_XSL,APPL_ID,OBJ_ID,VER_NBR) VALUES ('3000','2681','KpmeDocument',4,1,1,'','Base KPME Rule Document','KPME0001',null,'${kuali.docHandler.url.prefix}/kr/maintenance.do?methodToCall=docHandler','','',null,null,null,null,null,null,'2',null,null,null,'KPME','6694b4a4-69f3-4692-bac0-b43ea5ff462c',1)
;
UPDATE KREW_DOC_TYP_T SET PARNT_ID='3000',DOC_TYP_NM='KpmeEffectiveDateMaintenanceDocumentType',DOC_TYP_VER_NBR=3,ACTV_IND=1,CUR_IND=1,DOC_TYP_DESC=null,LBL='KPME Effective Date Maintenance Document',PREV_DOC_TYP_VER_NBR=null,DOC_HDR_ID=null,DOC_HDLR_URL='${kuali.docHandler.url.prefix}/kr-krad/kpme/effectiveDateMaintenance?methodToCall=docHandler',HELP_DEF_URL=null,DOC_SEARCH_HELP_URL=null,POST_PRCSR=null,AUTHORIZER=null,GRP_ID=null,BLNKT_APPR_GRP_ID=null,BLNKT_APPR_PLCY=null,RPT_GRP_ID=null,RTE_VER_NBR='1',NOTIFY_ADDR=null,SEC_XML=null,EMAIL_XSL=null,APPL_ID='KPME',OBJ_ID='da6ccf74-1bf1-11e4-8d4a-d30dc8c47e2b',VER_NBR=1 WHERE DOC_TYP_ID = 'KPME0002'  AND VER_NBR = 0
;
UPDATE KREW_DOC_TYP_T SET PARNT_ID='2680',DOC_TYP_NM='RiceDocument',DOC_TYP_VER_NBR=0,ACTV_IND=1,CUR_IND=1,DOC_TYP_DESC='Parent Document Type for all Rice Documents',LBL='Rice Document',PREV_DOC_TYP_VER_NBR=null,DOC_HDR_ID=null,DOC_HDLR_URL=null,HELP_DEF_URL=null,DOC_SEARCH_HELP_URL=null,POST_PRCSR=null,AUTHORIZER=null,GRP_ID=null,BLNKT_APPR_GRP_ID=null,BLNKT_APPR_PLCY=null,RPT_GRP_ID=null,RTE_VER_NBR='2',NOTIFY_ADDR=null,SEC_XML=null,EMAIL_XSL=null,APPL_ID=null,OBJ_ID='6166CBA1BA82644DE0404F8189D86C09',VER_NBR=1 WHERE DOC_TYP_ID = '2681'  AND VER_NBR = 0
;
UPDATE KREW_DOC_TYP_T SET PARNT_ID='3000',DOC_TYP_NM='KpmeEffectiveDateMaintenanceDocumentType',DOC_TYP_VER_NBR=3,ACTV_IND=1,CUR_IND=0,DOC_TYP_DESC=null,LBL='KPME Effective Date Maintenance Document',PREV_DOC_TYP_VER_NBR=null,DOC_HDR_ID=null,DOC_HDLR_URL='${kuali.docHandler.url.prefix}/kr-krad/kpme/effectiveDateMaintenance?methodToCall=docHandler',HELP_DEF_URL=null,DOC_SEARCH_HELP_URL=null,POST_PRCSR=null,AUTHORIZER=null,GRP_ID=null,BLNKT_APPR_GRP_ID=null,BLNKT_APPR_PLCY=null,RPT_GRP_ID=null,RTE_VER_NBR='1',NOTIFY_ADDR=null,SEC_XML=null,EMAIL_XSL=null,APPL_ID='KPME',OBJ_ID='da6ccf74-1bf1-11e4-8d4a-d30dc8c47e2b',VER_NBR=2 WHERE DOC_TYP_ID = 'KPME0002'  AND VER_NBR = 1
;
INSERT INTO KREW_DOC_HDR_S VALUES (NULL)
;
INSERT INTO KREW_DOC_TYP_T (DOC_TYP_ID,PARNT_ID,DOC_TYP_NM,DOC_TYP_VER_NBR,ACTV_IND,CUR_IND,DOC_TYP_DESC,LBL,PREV_DOC_TYP_VER_NBR,DOC_HDR_ID,DOC_HDLR_URL,HELP_DEF_URL,DOC_SEARCH_HELP_URL,POST_PRCSR,AUTHORIZER,GRP_ID,BLNKT_APPR_GRP_ID,BLNKT_APPR_PLCY,RPT_GRP_ID,RTE_VER_NBR,NOTIFY_ADDR,SEC_XML,EMAIL_XSL,APPL_ID,OBJ_ID,VER_NBR) VALUES ('3001','3000','KpmeEffectiveDateMaintenanceDocumentType',4,1,1,'','Kpme Effective Maintenance Document','KPME0002',null,'${kuali.docHandler.url.prefix}/kr-krad/kpme/effectiveDateMaintenance?methodToCall=docHandler','','',null,null,null,null,null,null,'2',null,null,null,null,'2ea12a2b-0722-4855-8137-fa2d5f4465e1',1)
;
UPDATE KREW_DOC_TYP_T SET PARNT_ID='2681',DOC_TYP_NM='KpmeDocument',DOC_TYP_VER_NBR=4,ACTV_IND=1,CUR_IND=1,DOC_TYP_DESC='',LBL='Base KPME Rule Document',PREV_DOC_TYP_VER_NBR='KPME0001',DOC_HDR_ID=null,DOC_HDLR_URL='${kuali.docHandler.url.prefix}/kr/maintenance.do?methodToCall=docHandler',HELP_DEF_URL='',DOC_SEARCH_HELP_URL='',POST_PRCSR=null,AUTHORIZER=null,GRP_ID=null,BLNKT_APPR_GRP_ID=null,BLNKT_APPR_PLCY=null,RPT_GRP_ID=null,RTE_VER_NBR='2',NOTIFY_ADDR=null,SEC_XML=null,EMAIL_XSL=null,APPL_ID='KPME',OBJ_ID='6694b4a4-69f3-4692-bac0-b43ea5ff462c',VER_NBR=2 WHERE DOC_TYP_ID = '3000'  AND VER_NBR = 1
;
INSERT INTO KREW_DOC_HDR_S VALUES (NULL)
;
INSERT INTO KREW_DOC_TYP_T (DOC_TYP_ID,PARNT_ID,DOC_TYP_NM,DOC_TYP_VER_NBR,ACTV_IND,CUR_IND,DOC_TYP_DESC,LBL,PREV_DOC_TYP_VER_NBR,DOC_HDR_ID,DOC_HDLR_URL,HELP_DEF_URL,DOC_SEARCH_HELP_URL,POST_PRCSR,AUTHORIZER,GRP_ID,BLNKT_APPR_GRP_ID,BLNKT_APPR_PLCY,RPT_GRP_ID,RTE_VER_NBR,NOTIFY_ADDR,SEC_XML,EMAIL_XSL,APPL_ID,OBJ_ID,VER_NBR) VALUES ('3002','3000','AccountMaintenanceDocumentType',0,1,1,'','Account',null,null,'${kuali.docHandler.url.prefix}/kr/maintenance.do?methodToCall=docHandler','','',null,null,null,null,null,null,'2',null,null,null,null,'0535706c-1e0b-4ce4-8b51-2365651ac166',1)
;
UPDATE KREW_DOC_TYP_T SET PARNT_ID='2681',DOC_TYP_NM='KpmeDocument',DOC_TYP_VER_NBR=4,ACTV_IND=1,CUR_IND=1,DOC_TYP_DESC='',LBL='Base KPME Rule Document',PREV_DOC_TYP_VER_NBR='KPME0001',DOC_HDR_ID=null,DOC_HDLR_URL='${kuali.docHandler.url.prefix}/kr/maintenance.do?methodToCall=docHandler',HELP_DEF_URL='',DOC_SEARCH_HELP_URL='',POST_PRCSR=null,AUTHORIZER=null,GRP_ID=null,BLNKT_APPR_GRP_ID=null,BLNKT_APPR_PLCY=null,RPT_GRP_ID=null,RTE_VER_NBR='2',NOTIFY_ADDR=null,SEC_XML=null,EMAIL_XSL=null,APPL_ID='KPME',OBJ_ID='6694b4a4-69f3-4692-bac0-b43ea5ff462c',VER_NBR=3 WHERE DOC_TYP_ID = '3000'  AND VER_NBR = 2
;
INSERT INTO KREW_DOC_HDR_S VALUES (NULL)
;
INSERT INTO KREW_DOC_TYP_T (DOC_TYP_ID,PARNT_ID,DOC_TYP_NM,DOC_TYP_VER_NBR,ACTV_IND,CUR_IND,DOC_TYP_DESC,LBL,PREV_DOC_TYP_VER_NBR,DOC_HDR_ID,DOC_HDLR_URL,HELP_DEF_URL,DOC_SEARCH_HELP_URL,POST_PRCSR,AUTHORIZER,GRP_ID,BLNKT_APPR_GRP_ID,BLNKT_APPR_PLCY,RPT_GRP_ID,RTE_VER_NBR,NOTIFY_ADDR,SEC_XML,EMAIL_XSL,APPL_ID,OBJ_ID,VER_NBR) VALUES ('3003','3000','AccrualCategoryDocumentType',0,1,1,'','Accrual Category Document',null,null,'${kuali.docHandler.url.prefix}/kr/maintenance.do?methodToCall=docHandler','','',null,null,null,null,null,null,'2',null,null,null,null,'2e5a2530-15d4-46eb-9b2b-e797132ad161',1)
;
UPDATE KREW_DOC_TYP_T SET PARNT_ID='2681',DOC_TYP_NM='KpmeDocument',DOC_TYP_VER_NBR=4,ACTV_IND=1,CUR_IND=1,DOC_TYP_DESC='',LBL='Base KPME Rule Document',PREV_DOC_TYP_VER_NBR='KPME0001',DOC_HDR_ID=null,DOC_HDLR_URL='${kuali.docHandler.url.prefix}/kr/maintenance.do?methodToCall=docHandler',HELP_DEF_URL='',DOC_SEARCH_HELP_URL='',POST_PRCSR=null,AUTHORIZER=null,GRP_ID=null,BLNKT_APPR_GRP_ID=null,BLNKT_APPR_PLCY=null,RPT_GRP_ID=null,RTE_VER_NBR='2',NOTIFY_ADDR=null,SEC_XML=null,EMAIL_XSL=null,APPL_ID='KPME',OBJ_ID='6694b4a4-69f3-4692-bac0-b43ea5ff462c',VER_NBR=4 WHERE DOC_TYP_ID = '3000'  AND VER_NBR = 3
;
INSERT INTO KREW_DOC_HDR_S VALUES (NULL)
;
INSERT INTO KREW_DOC_TYP_T (DOC_TYP_ID,PARNT_ID,DOC_TYP_NM,DOC_TYP_VER_NBR,ACTV_IND,CUR_IND,DOC_TYP_DESC,LBL,PREV_DOC_TYP_VER_NBR,DOC_HDR_ID,DOC_HDLR_URL,HELP_DEF_URL,DOC_SEARCH_HELP_URL,POST_PRCSR,AUTHORIZER,GRP_ID,BLNKT_APPR_GRP_ID,BLNKT_APPR_PLCY,RPT_GRP_ID,RTE_VER_NBR,NOTIFY_ADDR,SEC_XML,EMAIL_XSL,APPL_ID,OBJ_ID,VER_NBR) VALUES ('3004','3001','AssignmentAccountDocumentType',0,1,1,'','Assignment Account Document',null,null,null,'','',null,null,null,null,null,null,'2',null,null,null,null,'693312bd-f0af-41e9-9cad-4779c2b82426',1)
;
UPDATE KREW_DOC_TYP_T SET PARNT_ID='3000',DOC_TYP_NM='KpmeEffectiveDateMaintenanceDocumentType',DOC_TYP_VER_NBR=4,ACTV_IND=1,CUR_IND=1,DOC_TYP_DESC='',LBL='Kpme Effective Maintenance Document',PREV_DOC_TYP_VER_NBR='KPME0002',DOC_HDR_ID=null,DOC_HDLR_URL='${kuali.docHandler.url.prefix}/kr-krad/kpme/effectiveDateMaintenance?methodToCall=docHandler',HELP_DEF_URL='',DOC_SEARCH_HELP_URL='',POST_PRCSR=null,AUTHORIZER=null,GRP_ID=null,BLNKT_APPR_GRP_ID=null,BLNKT_APPR_PLCY=null,RPT_GRP_ID=null,RTE_VER_NBR='2',NOTIFY_ADDR=null,SEC_XML=null,EMAIL_XSL=null,APPL_ID=null,OBJ_ID='2ea12a2b-0722-4855-8137-fa2d5f4465e1',VER_NBR=2 WHERE DOC_TYP_ID = '3001'  AND VER_NBR = 1
;
INSERT INTO KREW_DOC_HDR_S VALUES (NULL)
;
INSERT INTO KREW_DOC_TYP_T (DOC_TYP_ID,PARNT_ID,DOC_TYP_NM,DOC_TYP_VER_NBR,ACTV_IND,CUR_IND,DOC_TYP_DESC,LBL,PREV_DOC_TYP_VER_NBR,DOC_HDR_ID,DOC_HDLR_URL,HELP_DEF_URL,DOC_SEARCH_HELP_URL,POST_PRCSR,AUTHORIZER,GRP_ID,BLNKT_APPR_GRP_ID,BLNKT_APPR_PLCY,RPT_GRP_ID,RTE_VER_NBR,NOTIFY_ADDR,SEC_XML,EMAIL_XSL,APPL_ID,OBJ_ID,VER_NBR) VALUES ('3005','3001','AssignmentDocumentType',0,1,1,'','Assignment Document',null,null,null,'','',null,null,null,null,null,null,'2',null,null,null,null,'350b0f1a-b076-4eac-96f8-f5b54cf95c11',1)
;
UPDATE KREW_DOC_TYP_T SET PARNT_ID='3000',DOC_TYP_NM='KpmeEffectiveDateMaintenanceDocumentType',DOC_TYP_VER_NBR=4,ACTV_IND=1,CUR_IND=1,DOC_TYP_DESC='',LBL='Kpme Effective Maintenance Document',PREV_DOC_TYP_VER_NBR='KPME0002',DOC_HDR_ID=null,DOC_HDLR_URL='${kuali.docHandler.url.prefix}/kr-krad/kpme/effectiveDateMaintenance?methodToCall=docHandler',HELP_DEF_URL='',DOC_SEARCH_HELP_URL='',POST_PRCSR=null,AUTHORIZER=null,GRP_ID=null,BLNKT_APPR_GRP_ID=null,BLNKT_APPR_PLCY=null,RPT_GRP_ID=null,RTE_VER_NBR='2',NOTIFY_ADDR=null,SEC_XML=null,EMAIL_XSL=null,APPL_ID=null,OBJ_ID='2ea12a2b-0722-4855-8137-fa2d5f4465e1',VER_NBR=3 WHERE DOC_TYP_ID = '3001'  AND VER_NBR = 2
;
INSERT INTO KREW_DOC_HDR_S VALUES (NULL)
;
INSERT INTO KREW_DOC_TYP_T (DOC_TYP_ID,PARNT_ID,DOC_TYP_NM,DOC_TYP_VER_NBR,ACTV_IND,CUR_IND,DOC_TYP_DESC,LBL,PREV_DOC_TYP_VER_NBR,DOC_HDR_ID,DOC_HDLR_URL,HELP_DEF_URL,DOC_SEARCH_HELP_URL,POST_PRCSR,AUTHORIZER,GRP_ID,BLNKT_APPR_GRP_ID,BLNKT_APPR_PLCY,RPT_GRP_ID,RTE_VER_NBR,NOTIFY_ADDR,SEC_XML,EMAIL_XSL,APPL_ID,OBJ_ID,VER_NBR) VALUES ('3006','3000','BalanceTransferDocumentType',0,1,1,'Balance Transfer Document','Balance Transfer Document',null,null,'${kuali.docHandler.url.prefix}/kr/maintenance.do?methodToCall=docHandler','','',null,null,'KPME0001','KPME0001',null,null,'2',null,null,null,'KPME','98f78395-6e21-4cd2-ae7e-060bb24c3a15',1)
;
INSERT INTO KREW_DOC_TYP_PLCY_RELN_T (DOC_TYP_ID,DOC_PLCY_NM,PLCY_NM,PLCY_VAL,OBJ_ID,VER_NBR) VALUES ('3006','INITIATOR_MUST_CANCEL',0,null,'ae72bbf0-604a-4f22-9281-544fdff31d83',1)
;
INSERT INTO KREW_DOC_TYP_PLCY_RELN_T (DOC_TYP_ID,DOC_PLCY_NM,PLCY_NM,PLCY_VAL,OBJ_ID,VER_NBR) VALUES ('3006','INITIATOR_MUST_ROUTE',0,null,'e4d5355c-5671-4773-ac53-fb1f40d5fd8f',1)
;
INSERT INTO KREW_RTE_NODE_S VALUES (NULL)
;
INSERT INTO KREW_RTE_NODE_T (RTE_NODE_ID,DOC_TYP_ID,NM,TYP,RTE_MTHD_NM,FNL_APRVR_IND,MNDTRY_RTE_IND,GRP_ID,RTE_MTHD_CD,ACTVN_TYP,BRCH_PROTO_ID,NEXT_DOC_STAT,VER_NBR) VALUES ('2912','3006','Initiated','org.kuali.rice.kew.engine.node.InitialNode',null,0,0,'KPME0001',null,'P',null,null,1)
;

INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2456','2912','contentFragment','<start name="Initiated">\n<activationType>P</activationType>\n<mandatoryRoute>false</mandatoryRoute>\n<finalApproval>false</finalApproval>\n</start>\n')
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_S VALUES (NULL)
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2457','2912','activationType','P')
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_S VALUES (NULL)
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2458','2912','mandatoryRoute','false')
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_S VALUES (NULL)
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2459','2912','finalApproval','false')
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_S VALUES (NULL)
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2460','2912','ruleSelector','Template')
;

INSERT INTO KREW_RTE_NODE_T (RTE_NODE_ID,DOC_TYP_ID,NM,TYP,RTE_MTHD_NM,FNL_APRVR_IND,MNDTRY_RTE_IND,GRP_ID,RTE_MTHD_CD,ACTVN_TYP,BRCH_PROTO_ID,NEXT_DOC_STAT,VER_NBR) VALUES ('2913','3006','BalanceTransferSplitNode','org.kuali.kpme.tklm.leave.workflow.BalanceTransferSplitNode',null,0,0,'KPME0001',null,'S',null,null,1)
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_S VALUES (NULL)
;

INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2461','2913','contentFragment','<split name="BalanceTransferSplitNode">\n<type>org.kuali.kpme.tklm.leave.workflow.BalanceTransferSplitNode</type>\n</split>\n')
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_S VALUES (NULL)
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2462','2913','type','org.kuali.kpme.tklm.leave.workflow.BalanceTransferSplitNode')
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_S VALUES (NULL)
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2463','2913','ruleSelector','Template')
;
INSERT INTO KREW_RTE_NODE_LNK_T (TO_RTE_NODE_ID,FROM_RTE_NODE_ID) VALUES ('2913','2912')
;
INSERT INTO KREW_RTE_NODE_S VALUES (NULL)
;
INSERT INTO KREW_RTE_BRCH_PROTO_T (RTE_BRCH_PROTO_ID,BRCH_NM,VER_NBR) VALUES ('2914','calendar',1)
;
INSERT INTO KREW_RTE_NODE_S VALUES (NULL)
;
INSERT INTO KREW_RTE_NODE_T (RTE_NODE_ID,DOC_TYP_ID,NM,TYP,RTE_MTHD_NM,FNL_APRVR_IND,MNDTRY_RTE_IND,GRP_ID,RTE_MTHD_CD,ACTVN_TYP,BRCH_PROTO_ID,NEXT_DOC_STAT,VER_NBR) VALUES ('2915','3006','PeopleFlows','org.kuali.rice.kew.engine.node.RequestsNode',null,0,0,'KPME0001','RE','R','2914',null,1)
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_S VALUES (NULL)
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2464','2915','contentFragment','<requests name="PeopleFlows">\n<activationType>R</activationType>\n<rulesEngine executorClass="org.kuali.kpme.tklm.leave.workflow.krms.LeaveMaintDocRulesEngineExecutor"/>\n</requests>\n')
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_S VALUES (NULL)
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2465','2915','activationType','R')
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_S VALUES (NULL)
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2466','2915','rulesEngine','')
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_S VALUES (NULL)
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2467','2915','ruleSelector','Template')
;
INSERT INTO KREW_RTE_NODE_LNK_T (TO_RTE_NODE_ID,FROM_RTE_NODE_ID) VALUES ('2915','2913')
;
UPDATE KREW_RTE_BRCH_PROTO_T SET BRCH_NM='calendar',VER_NBR=2 WHERE RTE_BRCH_PROTO_ID = '2914'  AND VER_NBR = 1
;
INSERT INTO KREW_RTE_NODE_S VALUES (NULL)
;
INSERT INTO KREW_RTE_NODE_T (RTE_NODE_ID,DOC_TYP_ID,NM,TYP,RTE_MTHD_NM,FNL_APRVR_IND,MNDTRY_RTE_IND,GRP_ID,RTE_MTHD_CD,ACTVN_TYP,BRCH_PROTO_ID,NEXT_DOC_STAT,VER_NBR) VALUES ('2916','3006','JoinNode','org.kuali.rice.kew.engine.node.SimpleJoinNode',null,0,0,'KPME0001',null,'S','2914',null,1)
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_S VALUES (NULL)
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2468','2916','contentFragment','<join name="JoinNode"/>\n')
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_S VALUES (NULL)
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2469','2916','ruleSelector','Template')
;
INSERT INTO KREW_RTE_NODE_S VALUES (NULL)
;
INSERT INTO KREW_RTE_BRCH_PROTO_T (RTE_BRCH_PROTO_ID,BRCH_NM,VER_NBR) VALUES ('2917','maintenance',1)
;
INSERT INTO KREW_RTE_NODE_S VALUES (NULL)
;
INSERT INTO KREW_RTE_NODE_T (RTE_NODE_ID,DOC_TYP_ID,NM,TYP,RTE_MTHD_NM,FNL_APRVR_IND,MNDTRY_RTE_IND,GRP_ID,RTE_MTHD_CD,ACTVN_TYP,BRCH_PROTO_ID,NEXT_DOC_STAT,VER_NBR) VALUES ('2918','3006','noOpNode','org.kuali.rice.kew.engine.node.NoOpNode',null,0,0,'KPME0001',null,'S','2917',null,1)
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_S VALUES (NULL)
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2470','2918','contentFragment','<simple name="noOpNode">\n<type>org.kuali.rice.kew.engine.node.NoOpNode</type>\n</simple>\n')
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_S VALUES (NULL)
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2471','2918','type','org.kuali.rice.kew.engine.node.NoOpNode')
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_S VALUES (NULL)
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2472','2918','ruleSelector','Template')
;
INSERT INTO KREW_RTE_NODE_LNK_T (TO_RTE_NODE_ID,FROM_RTE_NODE_ID) VALUES ('2918','2913')
;
INSERT INTO KREW_RTE_NODE_LNK_T (FROM_RTE_NODE_ID,TO_RTE_NODE_ID) VALUES ('2918','2916')
;
INSERT INTO KREW_RTE_NODE_LNK_T (TO_RTE_NODE_ID,FROM_RTE_NODE_ID) VALUES ('2916','2915')
;
UPDATE KREW_RTE_BRCH_PROTO_T SET BRCH_NM='maintenance',VER_NBR=2 WHERE RTE_BRCH_PROTO_ID = '2917'  AND VER_NBR = 1
;
UPDATE KREW_RTE_NODE_T SET DOC_TYP_ID='3006',NM='noOpNode',TYP='org.kuali.rice.kew.engine.node.NoOpNode',RTE_MTHD_NM=null,FNL_APRVR_IND=0,MNDTRY_RTE_IND=0,GRP_ID='KPME0001',RTE_MTHD_CD=null,ACTVN_TYP='S',BRCH_PROTO_ID='2917',NEXT_DOC_STAT=null,VER_NBR=2 WHERE RTE_NODE_ID = '2918'  AND VER_NBR = 1
;
UPDATE KREW_RTE_NODE_CFG_PARM_T SET RTE_NODE_ID='2918',KEY_CD='contentFragment',VAL='<simple name="noOpNode">\n<type>org.kuali.rice.kew.engine.node.NoOpNode</type>\n</simple>\n' WHERE RTE_NODE_CFG_PARM_ID = '2470'
;
UPDATE KREW_RTE_NODE_CFG_PARM_T SET RTE_NODE_ID='2918',KEY_CD='type',VAL='org.kuali.rice.kew.engine.node.NoOpNode' WHERE RTE_NODE_CFG_PARM_ID = '2471'
;
UPDATE KREW_RTE_NODE_CFG_PARM_T SET RTE_NODE_ID='2918',KEY_CD='ruleSelector',VAL='Template' WHERE RTE_NODE_CFG_PARM_ID = '2472'
;
UPDATE KREW_RTE_BRCH_PROTO_T SET BRCH_NM='calendar',VER_NBR=3 WHERE RTE_BRCH_PROTO_ID = '2914'  AND VER_NBR = 2
;
UPDATE KREW_RTE_NODE_T SET DOC_TYP_ID='3006',NM='JoinNode',TYP='org.kuali.rice.kew.engine.node.SimpleJoinNode',RTE_MTHD_NM=null,FNL_APRVR_IND=0,MNDTRY_RTE_IND=0,GRP_ID='KPME0001',RTE_MTHD_CD=null,ACTVN_TYP='S',BRCH_PROTO_ID='2914',NEXT_DOC_STAT=null,VER_NBR=2 WHERE RTE_NODE_ID = '2916'  AND VER_NBR = 1
;
UPDATE KREW_RTE_NODE_CFG_PARM_T SET RTE_NODE_ID='2916',KEY_CD='contentFragment',VAL='<join name="JoinNode"/>\n' WHERE RTE_NODE_CFG_PARM_ID = '2468'
;
UPDATE KREW_RTE_NODE_CFG_PARM_T SET RTE_NODE_ID='2916',KEY_CD='ruleSelector',VAL='Template' WHERE RTE_NODE_CFG_PARM_ID = '2469'
;
UPDATE KREW_RTE_BRCH_PROTO_T SET BRCH_NM='calendar',VER_NBR=4 WHERE RTE_BRCH_PROTO_ID = '2914'  AND VER_NBR = 3
;
UPDATE KREW_RTE_NODE_T SET DOC_TYP_ID='3006',NM='PeopleFlows',TYP='org.kuali.rice.kew.engine.node.RequestsNode',RTE_MTHD_NM=null,FNL_APRVR_IND=0,MNDTRY_RTE_IND=0,GRP_ID='KPME0001',RTE_MTHD_CD='RE',ACTVN_TYP='R',BRCH_PROTO_ID='2914',NEXT_DOC_STAT=null,VER_NBR=2 WHERE RTE_NODE_ID = '2915'  AND VER_NBR = 1
;
UPDATE KREW_RTE_NODE_CFG_PARM_T SET RTE_NODE_ID='2915',KEY_CD='contentFragment',VAL='<requests name="PeopleFlows">\n<activationType>R</activationType>\n<rulesEngine executorClass="org.kuali.kpme.tklm.leave.workflow.krms.LeaveMaintDocRulesEngineExecutor"/>\n</requests>\n' WHERE RTE_NODE_CFG_PARM_ID = '2464'
;
UPDATE KREW_RTE_NODE_CFG_PARM_T SET RTE_NODE_ID='2915',KEY_CD='activationType',VAL='R' WHERE RTE_NODE_CFG_PARM_ID = '2465'
;
UPDATE KREW_RTE_NODE_CFG_PARM_T SET RTE_NODE_ID='2915',KEY_CD='rulesEngine',VAL='' WHERE RTE_NODE_CFG_PARM_ID = '2466'
;
UPDATE KREW_RTE_NODE_CFG_PARM_T SET RTE_NODE_ID='2915',KEY_CD='ruleSelector',VAL='Template' WHERE RTE_NODE_CFG_PARM_ID = '2467'
;
INSERT INTO KREW_RTE_NODE_S VALUES (NULL)
;
INSERT INTO KREW_DOC_TYP_PROC_T (DOC_TYP_PROC_ID,DOC_TYP_ID,INIT_RTE_NODE_ID,NM,INIT_IND,VER_NBR) VALUES ('2919','3006','2912','PRIMARY',1,1)
;
UPDATE KREW_DOC_TYP_T SET PARNT_ID='2681',DOC_TYP_NM='KpmeDocument',DOC_TYP_VER_NBR=4,ACTV_IND=1,CUR_IND=1,DOC_TYP_DESC='',LBL='Base KPME Rule Document',PREV_DOC_TYP_VER_NBR='KPME0001',DOC_HDR_ID=null,DOC_HDLR_URL='${kuali.docHandler.url.prefix}/kr/maintenance.do?methodToCall=docHandler',HELP_DEF_URL='',DOC_SEARCH_HELP_URL='',POST_PRCSR=null,AUTHORIZER=null,GRP_ID=null,BLNKT_APPR_GRP_ID=null,BLNKT_APPR_PLCY=null,RPT_GRP_ID=null,RTE_VER_NBR='2',NOTIFY_ADDR=null,SEC_XML=null,EMAIL_XSL=null,APPL_ID='KPME',OBJ_ID='6694b4a4-69f3-4692-bac0-b43ea5ff462c',VER_NBR=5 WHERE DOC_TYP_ID = '3000'  AND VER_NBR = 4
;
INSERT INTO KREW_DOC_HDR_S VALUES (NULL)
;
INSERT INTO KREW_DOC_TYP_T (DOC_TYP_ID,PARNT_ID,DOC_TYP_NM,DOC_TYP_VER_NBR,ACTV_IND,CUR_IND,DOC_TYP_DESC,LBL,PREV_DOC_TYP_VER_NBR,DOC_HDR_ID,DOC_HDLR_URL,HELP_DEF_URL,DOC_SEARCH_HELP_URL,POST_PRCSR,AUTHORIZER,GRP_ID,BLNKT_APPR_GRP_ID,BLNKT_APPR_PLCY,RPT_GRP_ID,RTE_VER_NBR,NOTIFY_ADDR,SEC_XML,EMAIL_XSL,APPL_ID,OBJ_ID,VER_NBR) VALUES ('3007','3000','COA',0,1,1,'','COA',null,null,'${kuali.docHandler.url.prefix}/kr/maintenance.do?methodToCall=docHandler','','',null,null,null,null,null,null,'2',null,null,null,null,'d89da3d9-0f19-4592-ba69-5c5d403b3824',1)
;
UPDATE KREW_DOC_TYP_T SET PARNT_ID='2681',DOC_TYP_NM='KpmeDocument',DOC_TYP_VER_NBR=4,ACTV_IND=1,CUR_IND=1,DOC_TYP_DESC='',LBL='Base KPME Rule Document',PREV_DOC_TYP_VER_NBR='KPME0001',DOC_HDR_ID=null,DOC_HDLR_URL='${kuali.docHandler.url.prefix}/kr/maintenance.do?methodToCall=docHandler',HELP_DEF_URL='',DOC_SEARCH_HELP_URL='',POST_PRCSR=null,AUTHORIZER=null,GRP_ID=null,BLNKT_APPR_GRP_ID=null,BLNKT_APPR_PLCY=null,RPT_GRP_ID=null,RTE_VER_NBR='2',NOTIFY_ADDR=null,SEC_XML=null,EMAIL_XSL=null,APPL_ID='KPME',OBJ_ID='6694b4a4-69f3-4692-bac0-b43ea5ff462c',VER_NBR=6 WHERE DOC_TYP_ID = '3000'  AND VER_NBR = 5
;
INSERT INTO KREW_DOC_HDR_S VALUES (NULL)
;
INSERT INTO KREW_DOC_TYP_T (DOC_TYP_ID,PARNT_ID,DOC_TYP_NM,DOC_TYP_VER_NBR,ACTV_IND,CUR_IND,DOC_TYP_DESC,LBL,PREV_DOC_TYP_VER_NBR,DOC_HDR_ID,DOC_HDLR_URL,HELP_DEF_URL,DOC_SEARCH_HELP_URL,POST_PRCSR,AUTHORIZER,GRP_ID,BLNKT_APPR_GRP_ID,BLNKT_APPR_PLCY,RPT_GRP_ID,RTE_VER_NBR,NOTIFY_ADDR,SEC_XML,EMAIL_XSL,APPL_ID,OBJ_ID,VER_NBR) VALUES ('3008','3000','ChartMaintenanceDocumentType',0,1,1,'','Chart Code',null,null,'${kuali.docHandler.url.prefix}/kr/maintenance.do?methodToCall=docHandler','','',null,null,null,null,null,null,'2',null,null,null,null,'e4107688-3499-4a2c-8202-83fa21c285cf',1)
;
UPDATE KREW_DOC_TYP_T SET PARNT_ID='2681',DOC_TYP_NM='KpmeDocument',DOC_TYP_VER_NBR=4,ACTV_IND=1,CUR_IND=1,DOC_TYP_DESC='',LBL='Base KPME Rule Document',PREV_DOC_TYP_VER_NBR='KPME0001',DOC_HDR_ID=null,DOC_HDLR_URL='${kuali.docHandler.url.prefix}/kr/maintenance.do?methodToCall=docHandler',HELP_DEF_URL='',DOC_SEARCH_HELP_URL='',POST_PRCSR=null,AUTHORIZER=null,GRP_ID=null,BLNKT_APPR_GRP_ID=null,BLNKT_APPR_PLCY=null,RPT_GRP_ID=null,RTE_VER_NBR='2',NOTIFY_ADDR=null,SEC_XML=null,EMAIL_XSL=null,APPL_ID='KPME',OBJ_ID='6694b4a4-69f3-4692-bac0-b43ea5ff462c',VER_NBR=7 WHERE DOC_TYP_ID = '3000'  AND VER_NBR = 6
;
INSERT INTO KREW_DOC_HDR_S VALUES (NULL)
;
INSERT INTO KREW_DOC_TYP_T (DOC_TYP_ID,PARNT_ID,DOC_TYP_NM,DOC_TYP_VER_NBR,ACTV_IND,CUR_IND,DOC_TYP_DESC,LBL,PREV_DOC_TYP_VER_NBR,DOC_HDR_ID,DOC_HDLR_URL,HELP_DEF_URL,DOC_SEARCH_HELP_URL,POST_PRCSR,AUTHORIZER,GRP_ID,BLNKT_APPR_GRP_ID,BLNKT_APPR_PLCY,RPT_GRP_ID,RTE_VER_NBR,NOTIFY_ADDR,SEC_XML,EMAIL_XSL,APPL_ID,OBJ_ID,VER_NBR) VALUES ('3009','3000','ClockLocationRuleDocumentType',0,1,1,'','Clock Location Rule Document',null,null,'${kuali.docHandler.url.prefix}/kr/maintenance.do?methodToCall=docHandler','','',null,null,null,null,null,null,'2',null,null,null,null,'7a6fe9ff-5a75-4241-9e76-717bf07bdfe5',1)
;
UPDATE KREW_DOC_TYP_T SET PARNT_ID='2681',DOC_TYP_NM='KpmeDocument',DOC_TYP_VER_NBR=4,ACTV_IND=1,CUR_IND=1,DOC_TYP_DESC='',LBL='Base KPME Rule Document',PREV_DOC_TYP_VER_NBR='KPME0001',DOC_HDR_ID=null,DOC_HDLR_URL='${kuali.docHandler.url.prefix}/kr/maintenance.do?methodToCall=docHandler',HELP_DEF_URL='',DOC_SEARCH_HELP_URL='',POST_PRCSR=null,AUTHORIZER=null,GRP_ID=null,BLNKT_APPR_GRP_ID=null,BLNKT_APPR_PLCY=null,RPT_GRP_ID=null,RTE_VER_NBR='2',NOTIFY_ADDR=null,SEC_XML=null,EMAIL_XSL=null,APPL_ID='KPME',OBJ_ID='6694b4a4-69f3-4692-bac0-b43ea5ff462c',VER_NBR=8 WHERE DOC_TYP_ID = '3000'  AND VER_NBR = 7
;
INSERT INTO KREW_DOC_HDR_S VALUES (NULL)
;
INSERT INTO KREW_DOC_TYP_T (DOC_TYP_ID,PARNT_ID,DOC_TYP_NM,DOC_TYP_VER_NBR,ACTV_IND,CUR_IND,DOC_TYP_DESC,LBL,PREV_DOC_TYP_VER_NBR,DOC_HDR_ID,DOC_HDLR_URL,HELP_DEF_URL,DOC_SEARCH_HELP_URL,POST_PRCSR,AUTHORIZER,GRP_ID,BLNKT_APPR_GRP_ID,BLNKT_APPR_PLCY,RPT_GRP_ID,RTE_VER_NBR,NOTIFY_ADDR,SEC_XML,EMAIL_XSL,APPL_ID,OBJ_ID,VER_NBR) VALUES ('3010','3000','ClockLogDocumentType',0,1,1,'','Clock Log Document',null,null,'${kuali.docHandler.url.prefix}/kr-krad/maintenance.do?methodToCall=docHandler&dataObjectClassName=org.kuali.kpme.tklm.time.clocklog.ClockLog','','',null,null,null,null,null,null,'2',null,null,null,null,'f13b6d3a-99bc-4383-a241-4a8672866354',1)
;
UPDATE KREW_DOC_TYP_T SET PARNT_ID='2681',DOC_TYP_NM='KpmeDocument',DOC_TYP_VER_NBR=4,ACTV_IND=1,CUR_IND=1,DOC_TYP_DESC='',LBL='Base KPME Rule Document',PREV_DOC_TYP_VER_NBR='KPME0001',DOC_HDR_ID=null,DOC_HDLR_URL='${kuali.docHandler.url.prefix}/kr/maintenance.do?methodToCall=docHandler',HELP_DEF_URL='',DOC_SEARCH_HELP_URL='',POST_PRCSR=null,AUTHORIZER=null,GRP_ID=null,BLNKT_APPR_GRP_ID=null,BLNKT_APPR_PLCY=null,RPT_GRP_ID=null,RTE_VER_NBR='2',NOTIFY_ADDR=null,SEC_XML=null,EMAIL_XSL=null,APPL_ID='KPME',OBJ_ID='6694b4a4-69f3-4692-bac0-b43ea5ff462c',VER_NBR=9 WHERE DOC_TYP_ID = '3000'  AND VER_NBR = 8
;
INSERT INTO KREW_DOC_HDR_S VALUES (NULL)
;
INSERT INTO KREW_DOC_TYP_T (DOC_TYP_ID,PARNT_ID,DOC_TYP_NM,DOC_TYP_VER_NBR,ACTV_IND,CUR_IND,DOC_TYP_DESC,LBL,PREV_DOC_TYP_VER_NBR,DOC_HDR_ID,DOC_HDLR_URL,HELP_DEF_URL,DOC_SEARCH_HELP_URL,POST_PRCSR,AUTHORIZER,GRP_ID,BLNKT_APPR_GRP_ID,BLNKT_APPR_PLCY,RPT_GRP_ID,RTE_VER_NBR,NOTIFY_ADDR,SEC_XML,EMAIL_XSL,APPL_ID,OBJ_ID,VER_NBR) VALUES ('3011','3000','DailyOvertimeRuleDocumentType',0,1,1,'','Daily Overtime Rule Document',null,null,'${kuali.docHandler.url.prefix}/kr/maintenance.do?methodToCall=docHandler','','',null,null,null,null,null,null,'2',null,null,null,null,'9ef87a0c-19aa-40af-be96-f9133df19ffe',1)
;
UPDATE KREW_DOC_TYP_T SET PARNT_ID='2681',DOC_TYP_NM='KpmeDocument',DOC_TYP_VER_NBR=4,ACTV_IND=1,CUR_IND=1,DOC_TYP_DESC='',LBL='Base KPME Rule Document',PREV_DOC_TYP_VER_NBR='KPME0001',DOC_HDR_ID=null,DOC_HDLR_URL='${kuali.docHandler.url.prefix}/kr/maintenance.do?methodToCall=docHandler',HELP_DEF_URL='',DOC_SEARCH_HELP_URL='',POST_PRCSR=null,AUTHORIZER=null,GRP_ID=null,BLNKT_APPR_GRP_ID=null,BLNKT_APPR_PLCY=null,RPT_GRP_ID=null,RTE_VER_NBR='2',NOTIFY_ADDR=null,SEC_XML=null,EMAIL_XSL=null,APPL_ID='KPME',OBJ_ID='6694b4a4-69f3-4692-bac0-b43ea5ff462c',VER_NBR=10 WHERE DOC_TYP_ID = '3000'  AND VER_NBR = 9
;
INSERT INTO KREW_DOC_HDR_S VALUES (NULL)
;
INSERT INTO KREW_DOC_TYP_T (DOC_TYP_ID,PARNT_ID,DOC_TYP_NM,DOC_TYP_VER_NBR,ACTV_IND,CUR_IND,DOC_TYP_DESC,LBL,PREV_DOC_TYP_VER_NBR,DOC_HDR_ID,DOC_HDLR_URL,HELP_DEF_URL,DOC_SEARCH_HELP_URL,POST_PRCSR,AUTHORIZER,GRP_ID,BLNKT_APPR_GRP_ID,BLNKT_APPR_PLCY,RPT_GRP_ID,RTE_VER_NBR,NOTIFY_ADDR,SEC_XML,EMAIL_XSL,APPL_ID,OBJ_ID,VER_NBR) VALUES ('3012','3001','DepartmentAffiliationMaintenanceDocumentType',0,1,1,'','Department Affiliation Maintenance',null,null,null,'','',null,null,null,null,null,null,'2',null,null,null,null,'e1472052-83a1-48aa-b9a3-ab4137e35ef9',1)
;
UPDATE KREW_DOC_TYP_T SET PARNT_ID='3000',DOC_TYP_NM='KpmeEffectiveDateMaintenanceDocumentType',DOC_TYP_VER_NBR=4,ACTV_IND=1,CUR_IND=1,DOC_TYP_DESC='',LBL='Kpme Effective Maintenance Document',PREV_DOC_TYP_VER_NBR='KPME0002',DOC_HDR_ID=null,DOC_HDLR_URL='${kuali.docHandler.url.prefix}/kr-krad/kpme/effectiveDateMaintenance?methodToCall=docHandler',HELP_DEF_URL='',DOC_SEARCH_HELP_URL='',POST_PRCSR=null,AUTHORIZER=null,GRP_ID=null,BLNKT_APPR_GRP_ID=null,BLNKT_APPR_PLCY=null,RPT_GRP_ID=null,RTE_VER_NBR='2',NOTIFY_ADDR=null,SEC_XML=null,EMAIL_XSL=null,APPL_ID=null,OBJ_ID='2ea12a2b-0722-4855-8137-fa2d5f4465e1',VER_NBR=4 WHERE DOC_TYP_ID = '3001'  AND VER_NBR = 3
;
INSERT INTO KREW_DOC_HDR_S VALUES (NULL)
;
INSERT INTO KREW_DOC_TYP_T (DOC_TYP_ID,PARNT_ID,DOC_TYP_NM,DOC_TYP_VER_NBR,ACTV_IND,CUR_IND,DOC_TYP_DESC,LBL,PREV_DOC_TYP_VER_NBR,DOC_HDR_ID,DOC_HDLR_URL,HELP_DEF_URL,DOC_SEARCH_HELP_URL,POST_PRCSR,AUTHORIZER,GRP_ID,BLNKT_APPR_GRP_ID,BLNKT_APPR_PLCY,RPT_GRP_ID,RTE_VER_NBR,NOTIFY_ADDR,SEC_XML,EMAIL_XSL,APPL_ID,OBJ_ID,VER_NBR) VALUES ('3013','3001','DepartmentMaintenanceDocumentType',0,1,1,'','Department Document',null,null,null,'','',null,null,null,null,null,null,'2',null,null,null,null,'197f4288-541d-4938-aa63-5d4142002f5b',1)
;
UPDATE KREW_DOC_TYP_T SET PARNT_ID='3000',DOC_TYP_NM='KpmeEffectiveDateMaintenanceDocumentType',DOC_TYP_VER_NBR=4,ACTV_IND=1,CUR_IND=1,DOC_TYP_DESC='',LBL='Kpme Effective Maintenance Document',PREV_DOC_TYP_VER_NBR='KPME0002',DOC_HDR_ID=null,DOC_HDLR_URL='${kuali.docHandler.url.prefix}/kr-krad/kpme/effectiveDateMaintenance?methodToCall=docHandler',HELP_DEF_URL='',DOC_SEARCH_HELP_URL='',POST_PRCSR=null,AUTHORIZER=null,GRP_ID=null,BLNKT_APPR_GRP_ID=null,BLNKT_APPR_PLCY=null,RPT_GRP_ID=null,RTE_VER_NBR='2',NOTIFY_ADDR=null,SEC_XML=null,EMAIL_XSL=null,APPL_ID=null,OBJ_ID='2ea12a2b-0722-4855-8137-fa2d5f4465e1',VER_NBR=5 WHERE DOC_TYP_ID = '3001'  AND VER_NBR = 4
;
INSERT INTO KREW_DOC_HDR_S VALUES (NULL)
;
INSERT INTO KREW_DOC_TYP_T (DOC_TYP_ID,PARNT_ID,DOC_TYP_NM,DOC_TYP_VER_NBR,ACTV_IND,CUR_IND,DOC_TYP_DESC,LBL,PREV_DOC_TYP_VER_NBR,DOC_HDR_ID,DOC_HDLR_URL,HELP_DEF_URL,DOC_SEARCH_HELP_URL,POST_PRCSR,AUTHORIZER,GRP_ID,BLNKT_APPR_GRP_ID,BLNKT_APPR_PLCY,RPT_GRP_ID,RTE_VER_NBR,NOTIFY_ADDR,SEC_XML,EMAIL_XSL,APPL_ID,OBJ_ID,VER_NBR) VALUES ('3014','3000','DeptLunchRuleDocumentType',0,1,1,'','Department Lunch Rule Document',null,null,'${kuali.docHandler.url.prefix}/kr/maintenance.do?methodToCall=docHandler','','',null,null,null,null,null,null,'2',null,null,null,null,'2b29c3aa-ec7a-4098-a53d-f3e18825b322',1)
;
UPDATE KREW_DOC_TYP_T SET PARNT_ID='2681',DOC_TYP_NM='KpmeDocument',DOC_TYP_VER_NBR=4,ACTV_IND=1,CUR_IND=1,DOC_TYP_DESC='',LBL='Base KPME Rule Document',PREV_DOC_TYP_VER_NBR='KPME0001',DOC_HDR_ID=null,DOC_HDLR_URL='${kuali.docHandler.url.prefix}/kr/maintenance.do?methodToCall=docHandler',HELP_DEF_URL='',DOC_SEARCH_HELP_URL='',POST_PRCSR=null,AUTHORIZER=null,GRP_ID=null,BLNKT_APPR_GRP_ID=null,BLNKT_APPR_PLCY=null,RPT_GRP_ID=null,RTE_VER_NBR='2',NOTIFY_ADDR=null,SEC_XML=null,EMAIL_XSL=null,APPL_ID='KPME',OBJ_ID='6694b4a4-69f3-4692-bac0-b43ea5ff462c',VER_NBR=11 WHERE DOC_TYP_ID = '3000'  AND VER_NBR = 10
;
INSERT INTO KREW_DOC_HDR_S VALUES (NULL)
;
INSERT INTO KREW_DOC_TYP_T (DOC_TYP_ID,PARNT_ID,DOC_TYP_NM,DOC_TYP_VER_NBR,ACTV_IND,CUR_IND,DOC_TYP_DESC,LBL,PREV_DOC_TYP_VER_NBR,DOC_HDR_ID,DOC_HDLR_URL,HELP_DEF_URL,DOC_SEARCH_HELP_URL,POST_PRCSR,AUTHORIZER,GRP_ID,BLNKT_APPR_GRP_ID,BLNKT_APPR_PLCY,RPT_GRP_ID,RTE_VER_NBR,NOTIFY_ADDR,SEC_XML,EMAIL_XSL,APPL_ID,OBJ_ID,VER_NBR) VALUES ('3015','3001','EarnCodeGroupDocumentType',0,1,1,'','Earn Code Group Document',null,null,null,'','',null,null,null,null,null,null,'2',null,null,null,null,'3db11d73-5d4d-4723-a95c-a1bf9276dd9b',1)
;
UPDATE KREW_DOC_TYP_T SET PARNT_ID='3000',DOC_TYP_NM='KpmeEffectiveDateMaintenanceDocumentType',DOC_TYP_VER_NBR=4,ACTV_IND=1,CUR_IND=1,DOC_TYP_DESC='',LBL='Kpme Effective Maintenance Document',PREV_DOC_TYP_VER_NBR='KPME0002',DOC_HDR_ID=null,DOC_HDLR_URL='${kuali.docHandler.url.prefix}/kr-krad/kpme/effectiveDateMaintenance?methodToCall=docHandler',HELP_DEF_URL='',DOC_SEARCH_HELP_URL='',POST_PRCSR=null,AUTHORIZER=null,GRP_ID=null,BLNKT_APPR_GRP_ID=null,BLNKT_APPR_PLCY=null,RPT_GRP_ID=null,RTE_VER_NBR='2',NOTIFY_ADDR=null,SEC_XML=null,EMAIL_XSL=null,APPL_ID=null,OBJ_ID='2ea12a2b-0722-4855-8137-fa2d5f4465e1',VER_NBR=6 WHERE DOC_TYP_ID = '3001'  AND VER_NBR = 5
;
INSERT INTO KREW_DOC_HDR_S VALUES (NULL)
;
INSERT INTO KREW_DOC_TYP_T (DOC_TYP_ID,PARNT_ID,DOC_TYP_NM,DOC_TYP_VER_NBR,ACTV_IND,CUR_IND,DOC_TYP_DESC,LBL,PREV_DOC_TYP_VER_NBR,DOC_HDR_ID,DOC_HDLR_URL,HELP_DEF_URL,DOC_SEARCH_HELP_URL,POST_PRCSR,AUTHORIZER,GRP_ID,BLNKT_APPR_GRP_ID,BLNKT_APPR_PLCY,RPT_GRP_ID,RTE_VER_NBR,NOTIFY_ADDR,SEC_XML,EMAIL_XSL,APPL_ID,OBJ_ID,VER_NBR) VALUES ('3016','3001','EarnCodeMaintenanceDocumentType',0,1,1,'','Earn Code Document',null,null,null,'','',null,null,null,null,null,null,'2',null,null,null,null,'5cceb9e9-7d0a-457e-9383-609b7aab31a0',1)
;
UPDATE KREW_DOC_TYP_T SET PARNT_ID='3000',DOC_TYP_NM='KpmeEffectiveDateMaintenanceDocumentType',DOC_TYP_VER_NBR=4,ACTV_IND=1,CUR_IND=1,DOC_TYP_DESC='',LBL='Kpme Effective Maintenance Document',PREV_DOC_TYP_VER_NBR='KPME0002',DOC_HDR_ID=null,DOC_HDLR_URL='${kuali.docHandler.url.prefix}/kr-krad/kpme/effectiveDateMaintenance?methodToCall=docHandler',HELP_DEF_URL='',DOC_SEARCH_HELP_URL='',POST_PRCSR=null,AUTHORIZER=null,GRP_ID=null,BLNKT_APPR_GRP_ID=null,BLNKT_APPR_PLCY=null,RPT_GRP_ID=null,RTE_VER_NBR='2',NOTIFY_ADDR=null,SEC_XML=null,EMAIL_XSL=null,APPL_ID=null,OBJ_ID='2ea12a2b-0722-4855-8137-fa2d5f4465e1',VER_NBR=7 WHERE DOC_TYP_ID = '3001'  AND VER_NBR = 6
;
INSERT INTO KREW_DOC_HDR_S VALUES (NULL)
;
INSERT INTO KREW_DOC_TYP_T (DOC_TYP_ID,PARNT_ID,DOC_TYP_NM,DOC_TYP_VER_NBR,ACTV_IND,CUR_IND,DOC_TYP_DESC,LBL,PREV_DOC_TYP_VER_NBR,DOC_HDR_ID,DOC_HDLR_URL,HELP_DEF_URL,DOC_SEARCH_HELP_URL,POST_PRCSR,AUTHORIZER,GRP_ID,BLNKT_APPR_GRP_ID,BLNKT_APPR_PLCY,RPT_GRP_ID,RTE_VER_NBR,NOTIFY_ADDR,SEC_XML,EMAIL_XSL,APPL_ID,OBJ_ID,VER_NBR) VALUES ('3017','3000','EarnCodeSecurityDocumentType',0,1,1,'','Earn Code Security Document',null,null,'${kuali.docHandler.url.prefix}/kr-krad/maintenance.do?methodToCall=docHandler&dataObjectClassName=org.kuali.kpme.core.earncode.security.EarnCodeSecurity','','',null,null,null,null,null,null,'2',null,null,null,null,'a137cb77-9a2d-41c3-b00f-19f684c32119',1)
;
UPDATE KREW_DOC_TYP_T SET PARNT_ID='2681',DOC_TYP_NM='KpmeDocument',DOC_TYP_VER_NBR=4,ACTV_IND=1,CUR_IND=1,DOC_TYP_DESC='',LBL='Base KPME Rule Document',PREV_DOC_TYP_VER_NBR='KPME0001',DOC_HDR_ID=null,DOC_HDLR_URL='${kuali.docHandler.url.prefix}/kr/maintenance.do?methodToCall=docHandler',HELP_DEF_URL='',DOC_SEARCH_HELP_URL='',POST_PRCSR=null,AUTHORIZER=null,GRP_ID=null,BLNKT_APPR_GRP_ID=null,BLNKT_APPR_PLCY=null,RPT_GRP_ID=null,RTE_VER_NBR='2',NOTIFY_ADDR=null,SEC_XML=null,EMAIL_XSL=null,APPL_ID='KPME',OBJ_ID='6694b4a4-69f3-4692-bac0-b43ea5ff462c',VER_NBR=12 WHERE DOC_TYP_ID = '3000'  AND VER_NBR = 11
;
INSERT INTO KREW_DOC_HDR_S VALUES (NULL)
;
INSERT INTO KREW_DOC_TYP_T (DOC_TYP_ID,PARNT_ID,DOC_TYP_NM,DOC_TYP_VER_NBR,ACTV_IND,CUR_IND,DOC_TYP_DESC,LBL,PREV_DOC_TYP_VER_NBR,DOC_HDR_ID,DOC_HDLR_URL,HELP_DEF_URL,DOC_SEARCH_HELP_URL,POST_PRCSR,AUTHORIZER,GRP_ID,BLNKT_APPR_GRP_ID,BLNKT_APPR_PLCY,RPT_GRP_ID,RTE_VER_NBR,NOTIFY_ADDR,SEC_XML,EMAIL_XSL,APPL_ID,OBJ_ID,VER_NBR) VALUES ('3018','3000','EmployeeOverrideDocumentType',0,1,1,'','Employee Override Document',null,null,'${kuali.docHandler.url.prefix}/kr/maintenance.do?methodToCall=docHandler','','',null,null,null,null,null,null,'2',null,null,null,null,'6c50b29a-c013-45aa-9dd5-c7c0dda6bb34',1)
;
UPDATE KREW_DOC_TYP_T SET PARNT_ID='2681',DOC_TYP_NM='KpmeDocument',DOC_TYP_VER_NBR=4,ACTV_IND=1,CUR_IND=1,DOC_TYP_DESC='',LBL='Base KPME Rule Document',PREV_DOC_TYP_VER_NBR='KPME0001',DOC_HDR_ID=null,DOC_HDLR_URL='${kuali.docHandler.url.prefix}/kr/maintenance.do?methodToCall=docHandler',HELP_DEF_URL='',DOC_SEARCH_HELP_URL='',POST_PRCSR=null,AUTHORIZER=null,GRP_ID=null,BLNKT_APPR_GRP_ID=null,BLNKT_APPR_PLCY=null,RPT_GRP_ID=null,RTE_VER_NBR='2',NOTIFY_ADDR=null,SEC_XML=null,EMAIL_XSL=null,APPL_ID='KPME',OBJ_ID='6694b4a4-69f3-4692-bac0-b43ea5ff462c',VER_NBR=13 WHERE DOC_TYP_ID = '3000'  AND VER_NBR = 12
;
INSERT INTO KREW_DOC_HDR_S VALUES (NULL)
;
INSERT INTO KREW_DOC_TYP_T (DOC_TYP_ID,PARNT_ID,DOC_TYP_NM,DOC_TYP_VER_NBR,ACTV_IND,CUR_IND,DOC_TYP_DESC,LBL,PREV_DOC_TYP_VER_NBR,DOC_HDR_ID,DOC_HDLR_URL,HELP_DEF_URL,DOC_SEARCH_HELP_URL,POST_PRCSR,AUTHORIZER,GRP_ID,BLNKT_APPR_GRP_ID,BLNKT_APPR_PLCY,RPT_GRP_ID,RTE_VER_NBR,NOTIFY_ADDR,SEC_XML,EMAIL_XSL,APPL_ID,OBJ_ID,VER_NBR) VALUES ('3019','3000','GracePeriodRuleDocumentType',0,1,1,'','Grace Period Rule Document',null,null,'${kuali.docHandler.url.prefix}/kr/maintenance.do?methodToCall=docHandler','','',null,null,null,null,null,null,'2',null,null,null,null,'40de2081-0f75-481d-bba2-a36eeddbf133',1)
;
UPDATE KREW_DOC_TYP_T SET PARNT_ID='2681',DOC_TYP_NM='KpmeDocument',DOC_TYP_VER_NBR=4,ACTV_IND=1,CUR_IND=1,DOC_TYP_DESC='',LBL='Base KPME Rule Document',PREV_DOC_TYP_VER_NBR='KPME0001',DOC_HDR_ID=null,DOC_HDLR_URL='${kuali.docHandler.url.prefix}/kr/maintenance.do?methodToCall=docHandler',HELP_DEF_URL='',DOC_SEARCH_HELP_URL='',POST_PRCSR=null,AUTHORIZER=null,GRP_ID=null,BLNKT_APPR_GRP_ID=null,BLNKT_APPR_PLCY=null,RPT_GRP_ID=null,RTE_VER_NBR='2',NOTIFY_ADDR=null,SEC_XML=null,EMAIL_XSL=null,APPL_ID='KPME',OBJ_ID='6694b4a4-69f3-4692-bac0-b43ea5ff462c',VER_NBR=14 WHERE DOC_TYP_ID = '3000'  AND VER_NBR = 13
;
INSERT INTO KREW_DOC_HDR_S VALUES (NULL)
;
INSERT INTO KREW_DOC_TYP_T (DOC_TYP_ID,PARNT_ID,DOC_TYP_NM,DOC_TYP_VER_NBR,ACTV_IND,CUR_IND,DOC_TYP_DESC,LBL,PREV_DOC_TYP_VER_NBR,DOC_HDR_ID,DOC_HDLR_URL,HELP_DEF_URL,DOC_SEARCH_HELP_URL,POST_PRCSR,AUTHORIZER,GRP_ID,BLNKT_APPR_GRP_ID,BLNKT_APPR_PLCY,RPT_GRP_ID,RTE_VER_NBR,NOTIFY_ADDR,SEC_XML,EMAIL_XSL,APPL_ID,OBJ_ID,VER_NBR) VALUES ('3020','3001','HrGroupKeyDocumentType',0,1,1,'','Hr Group Key Document',null,null,null,'','',null,null,null,null,null,null,'2',null,null,null,null,'4bea104b-2372-40c7-9535-05564aa08576',1)
;
UPDATE KREW_DOC_TYP_T SET PARNT_ID='3000',DOC_TYP_NM='KpmeEffectiveDateMaintenanceDocumentType',DOC_TYP_VER_NBR=4,ACTV_IND=1,CUR_IND=1,DOC_TYP_DESC='',LBL='Kpme Effective Maintenance Document',PREV_DOC_TYP_VER_NBR='KPME0002',DOC_HDR_ID=null,DOC_HDLR_URL='${kuali.docHandler.url.prefix}/kr-krad/kpme/effectiveDateMaintenance?methodToCall=docHandler',HELP_DEF_URL='',DOC_SEARCH_HELP_URL='',POST_PRCSR=null,AUTHORIZER=null,GRP_ID=null,BLNKT_APPR_GRP_ID=null,BLNKT_APPR_PLCY=null,RPT_GRP_ID=null,RTE_VER_NBR='2',NOTIFY_ADDR=null,SEC_XML=null,EMAIL_XSL=null,APPL_ID=null,OBJ_ID='2ea12a2b-0722-4855-8137-fa2d5f4465e1',VER_NBR=8 WHERE DOC_TYP_ID = '3001'  AND VER_NBR = 7
;
INSERT INTO KREW_DOC_HDR_S VALUES (NULL)
;
INSERT INTO KREW_DOC_TYP_T (DOC_TYP_ID,PARNT_ID,DOC_TYP_NM,DOC_TYP_VER_NBR,ACTV_IND,CUR_IND,DOC_TYP_DESC,LBL,PREV_DOC_TYP_VER_NBR,DOC_HDR_ID,DOC_HDLR_URL,HELP_DEF_URL,DOC_SEARCH_HELP_URL,POST_PRCSR,AUTHORIZER,GRP_ID,BLNKT_APPR_GRP_ID,BLNKT_APPR_PLCY,RPT_GRP_ID,RTE_VER_NBR,NOTIFY_ADDR,SEC_XML,EMAIL_XSL,APPL_ID,OBJ_ID,VER_NBR) VALUES ('3021','3000','InstitutionDocumentType',0,1,1,'','Institution Maintenance',null,null,'${kuali.docHandler.url.prefix}/kr/maintenance.do?methodToCall=docHandler','','',null,null,null,null,null,null,'2',null,null,null,null,'feea8ce3-ee2b-4c23-a0df-ed60263f847e',1)
;
UPDATE KREW_DOC_TYP_T SET PARNT_ID='2681',DOC_TYP_NM='KpmeDocument',DOC_TYP_VER_NBR=4,ACTV_IND=1,CUR_IND=1,DOC_TYP_DESC='',LBL='Base KPME Rule Document',PREV_DOC_TYP_VER_NBR='KPME0001',DOC_HDR_ID=null,DOC_HDLR_URL='${kuali.docHandler.url.prefix}/kr/maintenance.do?methodToCall=docHandler',HELP_DEF_URL='',DOC_SEARCH_HELP_URL='',POST_PRCSR=null,AUTHORIZER=null,GRP_ID=null,BLNKT_APPR_GRP_ID=null,BLNKT_APPR_PLCY=null,RPT_GRP_ID=null,RTE_VER_NBR='2',NOTIFY_ADDR=null,SEC_XML=null,EMAIL_XSL=null,APPL_ID='KPME',OBJ_ID='6694b4a4-69f3-4692-bac0-b43ea5ff462c',VER_NBR=15 WHERE DOC_TYP_ID = '3000'  AND VER_NBR = 14
;
INSERT INTO KREW_DOC_HDR_S VALUES (NULL)
;
INSERT INTO KREW_DOC_TYP_T (DOC_TYP_ID,PARNT_ID,DOC_TYP_NM,DOC_TYP_VER_NBR,ACTV_IND,CUR_IND,DOC_TYP_DESC,LBL,PREV_DOC_TYP_VER_NBR,DOC_HDR_ID,DOC_HDLR_URL,HELP_DEF_URL,DOC_SEARCH_HELP_URL,POST_PRCSR,AUTHORIZER,GRP_ID,BLNKT_APPR_GRP_ID,BLNKT_APPR_PLCY,RPT_GRP_ID,RTE_VER_NBR,NOTIFY_ADDR,SEC_XML,EMAIL_XSL,APPL_ID,OBJ_ID,VER_NBR) VALUES ('3022','3001','JobMaintenanceDocumentType',0,1,1,'','Job Document',null,null,null,'','',null,null,null,null,null,null,'2',null,null,null,null,'63f0d70d-becb-4079-8e5d-324a1a2bd031',1)
;
UPDATE KREW_DOC_TYP_T SET PARNT_ID='3000',DOC_TYP_NM='KpmeEffectiveDateMaintenanceDocumentType',DOC_TYP_VER_NBR=4,ACTV_IND=1,CUR_IND=1,DOC_TYP_DESC='',LBL='Kpme Effective Maintenance Document',PREV_DOC_TYP_VER_NBR='KPME0002',DOC_HDR_ID=null,DOC_HDLR_URL='${kuali.docHandler.url.prefix}/kr-krad/kpme/effectiveDateMaintenance?methodToCall=docHandler',HELP_DEF_URL='',DOC_SEARCH_HELP_URL='',POST_PRCSR=null,AUTHORIZER=null,GRP_ID=null,BLNKT_APPR_GRP_ID=null,BLNKT_APPR_PLCY=null,RPT_GRP_ID=null,RTE_VER_NBR='2',NOTIFY_ADDR=null,SEC_XML=null,EMAIL_XSL=null,APPL_ID=null,OBJ_ID='2ea12a2b-0722-4855-8137-fa2d5f4465e1',VER_NBR=9 WHERE DOC_TYP_ID = '3001'  AND VER_NBR = 8
;
INSERT INTO KREW_DOC_HDR_S VALUES (NULL)
;
INSERT INTO KREW_DOC_TYP_T (DOC_TYP_ID,PARNT_ID,DOC_TYP_NM,DOC_TYP_VER_NBR,ACTV_IND,CUR_IND,DOC_TYP_DESC,LBL,PREV_DOC_TYP_VER_NBR,DOC_HDR_ID,DOC_HDLR_URL,HELP_DEF_URL,DOC_SEARCH_HELP_URL,POST_PRCSR,AUTHORIZER,GRP_ID,BLNKT_APPR_GRP_ID,BLNKT_APPR_PLCY,RPT_GRP_ID,RTE_VER_NBR,NOTIFY_ADDR,SEC_XML,EMAIL_XSL,APPL_ID,OBJ_ID,VER_NBR) VALUES ('3023','3000','LeaveAdjustmentDocumentType',0,1,1,'','Leave Adjustment Document',null,null,'${kuali.docHandler.url.prefix}/kr/maintenance.do?methodToCall=docHandler','','',null,null,null,null,null,null,'2',null,null,null,null,'3361a7e7-f8d1-48eb-9b54-df25b302a76f',1)
;
UPDATE KREW_DOC_TYP_T SET PARNT_ID='2681',DOC_TYP_NM='KpmeDocument',DOC_TYP_VER_NBR=4,ACTV_IND=1,CUR_IND=1,DOC_TYP_DESC='',LBL='Base KPME Rule Document',PREV_DOC_TYP_VER_NBR='KPME0001',DOC_HDR_ID=null,DOC_HDLR_URL='${kuali.docHandler.url.prefix}/kr/maintenance.do?methodToCall=docHandler',HELP_DEF_URL='',DOC_SEARCH_HELP_URL='',POST_PRCSR=null,AUTHORIZER=null,GRP_ID=null,BLNKT_APPR_GRP_ID=null,BLNKT_APPR_PLCY=null,RPT_GRP_ID=null,RTE_VER_NBR='2',NOTIFY_ADDR=null,SEC_XML=null,EMAIL_XSL=null,APPL_ID='KPME',OBJ_ID='6694b4a4-69f3-4692-bac0-b43ea5ff462c',VER_NBR=16 WHERE DOC_TYP_ID = '3000'  AND VER_NBR = 15
;
INSERT INTO KREW_DOC_HDR_S VALUES (NULL)
;
INSERT INTO KREW_DOC_TYP_T (DOC_TYP_ID,PARNT_ID,DOC_TYP_NM,DOC_TYP_VER_NBR,ACTV_IND,CUR_IND,DOC_TYP_DESC,LBL,PREV_DOC_TYP_VER_NBR,DOC_HDR_ID,DOC_HDLR_URL,HELP_DEF_URL,DOC_SEARCH_HELP_URL,POST_PRCSR,AUTHORIZER,GRP_ID,BLNKT_APPR_GRP_ID,BLNKT_APPR_PLCY,RPT_GRP_ID,RTE_VER_NBR,NOTIFY_ADDR,SEC_XML,EMAIL_XSL,APPL_ID,OBJ_ID,VER_NBR) VALUES ('3024','3000','LeaveCalendarDocumentHeaderDocumentType',0,1,1,'','Leave Calendar Document Header Document',null,null,'${kuali.docHandler.url.prefix}/kr/maintenance.do?methodToCall=docHandler','','',null,null,null,null,null,null,'2',null,null,null,null,'02fa7372-2b72-46ba-884b-357a691444cf',1)
;
UPDATE KREW_DOC_TYP_T SET PARNT_ID='2681',DOC_TYP_NM='KpmeDocument',DOC_TYP_VER_NBR=4,ACTV_IND=1,CUR_IND=1,DOC_TYP_DESC='',LBL='Base KPME Rule Document',PREV_DOC_TYP_VER_NBR='KPME0001',DOC_HDR_ID=null,DOC_HDLR_URL='${kuali.docHandler.url.prefix}/kr/maintenance.do?methodToCall=docHandler',HELP_DEF_URL='',DOC_SEARCH_HELP_URL='',POST_PRCSR=null,AUTHORIZER=null,GRP_ID=null,BLNKT_APPR_GRP_ID=null,BLNKT_APPR_PLCY=null,RPT_GRP_ID=null,RTE_VER_NBR='2',NOTIFY_ADDR=null,SEC_XML=null,EMAIL_XSL=null,APPL_ID='KPME',OBJ_ID='6694b4a4-69f3-4692-bac0-b43ea5ff462c',VER_NBR=17 WHERE DOC_TYP_ID = '3000'  AND VER_NBR = 16
;
INSERT INTO KREW_DOC_HDR_S VALUES (NULL)
;
INSERT INTO KREW_DOC_TYP_T (DOC_TYP_ID,PARNT_ID,DOC_TYP_NM,DOC_TYP_VER_NBR,ACTV_IND,CUR_IND,DOC_TYP_DESC,LBL,PREV_DOC_TYP_VER_NBR,DOC_HDR_ID,DOC_HDLR_URL,HELP_DEF_URL,DOC_SEARCH_HELP_URL,POST_PRCSR,AUTHORIZER,GRP_ID,BLNKT_APPR_GRP_ID,BLNKT_APPR_PLCY,RPT_GRP_ID,RTE_VER_NBR,NOTIFY_ADDR,SEC_XML,EMAIL_XSL,APPL_ID,OBJ_ID,VER_NBR) VALUES ('3025','3000','LeaveCalendarDocument',0,1,1,'Leave Calendar Document','Leave Calendar',null,null,'${kuali.docHandler.url.prefix}/LeaveCalendar.do?methodToCall=docHandler','','','org.kuali.kpme.tklm.leave.workflow.postprocessor.LmPostProcessor',null,'KPME0001','KPME0001',null,null,'1',null,null,null,'KPME','f3fcada2-9850-4637-a298-09e19f6894e5',1)
;
INSERT INTO KREW_DOC_TYP_PLCY_RELN_T (DOC_TYP_ID,DOC_PLCY_NM,PLCY_NM,PLCY_VAL,OBJ_ID,VER_NBR) VALUES ('3025','INITIATOR_MUST_CANCEL',0,null,'5b9a24a0-d7b1-4bbf-b38c-7264d70be13c',1)
;
INSERT INTO KREW_DOC_TYP_PLCY_RELN_T (DOC_TYP_ID,DOC_PLCY_NM,PLCY_NM,PLCY_VAL,OBJ_ID,VER_NBR) VALUES ('3025','INITIATOR_MUST_ROUTE',0,null,'8d6e7925-70f8-46a3-a8b3-1eb67bb07b57',1)
;
INSERT INTO KREW_DOC_TYP_ATTR_S VALUES (NULL)
;
INSERT INTO KREW_DOC_TYP_ATTR_T (DOC_TYP_ATTRIB_ID,DOC_TYP_ID,RULE_ATTR_ID,ORD_INDX) VALUES ('2009','3025','1646',1)
;
INSERT INTO KREW_DOC_TYP_ATTR_S VALUES (NULL)
;
INSERT INTO KREW_DOC_TYP_ATTR_T (DOC_TYP_ATTRIB_ID,DOC_TYP_ID,RULE_ATTR_ID,ORD_INDX) VALUES ('2010','3025','1650',2)
;
INSERT INTO KREW_DOC_TYP_ATTR_S VALUES (NULL)
;
INSERT INTO KREW_DOC_TYP_ATTR_T (DOC_TYP_ATTRIB_ID,DOC_TYP_ID,RULE_ATTR_ID,ORD_INDX) VALUES ('2011','3025','1651',3)
;
INSERT INTO KREW_DOC_TYP_ATTR_S VALUES (NULL)
;
INSERT INTO KREW_DOC_TYP_ATTR_T (DOC_TYP_ATTRIB_ID,DOC_TYP_ID,RULE_ATTR_ID,ORD_INDX) VALUES ('2012','3025','1652',4)
;
INSERT INTO KREW_RTE_NODE_S VALUES (NULL)
;
INSERT INTO KREW_RTE_NODE_T (RTE_NODE_ID,DOC_TYP_ID,NM,TYP,RTE_MTHD_NM,FNL_APRVR_IND,MNDTRY_RTE_IND,GRP_ID,RTE_MTHD_CD,ACTVN_TYP,BRCH_PROTO_ID,NEXT_DOC_STAT,VER_NBR) VALUES ('2920','3025','Initiated','org.kuali.rice.kew.engine.node.InitialNode',null,0,0,'KPME0001',null,'P',null,null,1)
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_S VALUES (NULL)
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2473','2920','contentFragment','<start name="Initiated">\n<activationType>P</activationType>\n<mandatoryRoute>false</mandatoryRoute>\n<finalApproval>false</finalApproval>\n</start>\n')
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_S VALUES (NULL)
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2474','2920','activationType','P')
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_S VALUES (NULL)
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2475','2920','mandatoryRoute','false')
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_S VALUES (NULL)
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2476','2920','finalApproval','false')
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_S VALUES (NULL)
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2477','2920','ruleSelector','Template')
;
INSERT INTO KREW_RTE_NODE_S VALUES (NULL)
;
INSERT INTO KREW_RTE_NODE_T (RTE_NODE_ID,DOC_TYP_ID,NM,TYP,RTE_MTHD_NM,FNL_APRVR_IND,MNDTRY_RTE_IND,GRP_ID,RTE_MTHD_CD,ACTVN_TYP,BRCH_PROTO_ID,NEXT_DOC_STAT,VER_NBR) VALUES ('2921','3025','PeopleFlows','org.kuali.rice.kew.engine.node.RequestsNode',null,0,0,'KPME0001','RE','R',null,null,1)
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_S VALUES (NULL)
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2478','2921','contentFragment','<requests name="PeopleFlows">\n<activationType>R</activationType>\n<rulesEngine executorClass="org.kuali.kpme.tklm.leave.calendar.rules.LeaveCalendarRulesEngineExecutor"/>\n</requests>\n')
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_S VALUES (NULL)
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2479','2921','activationType','R')
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_S VALUES (NULL)
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2480','2921','rulesEngine','')
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_S VALUES (NULL)
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2481','2921','ruleSelector','Template')
;
INSERT INTO KREW_RTE_NODE_LNK_T (TO_RTE_NODE_ID,FROM_RTE_NODE_ID) VALUES ('2921','2920')
;
INSERT INTO KREW_RTE_NODE_S VALUES (NULL)
;
INSERT INTO KREW_DOC_TYP_PROC_T (DOC_TYP_PROC_ID,DOC_TYP_ID,INIT_RTE_NODE_ID,NM,INIT_IND,VER_NBR) VALUES ('2922','3025','2920','PRIMARY',1,1)
;
UPDATE KREW_DOC_TYP_T SET PARNT_ID='2681',DOC_TYP_NM='KpmeDocument',DOC_TYP_VER_NBR=4,ACTV_IND=1,CUR_IND=1,DOC_TYP_DESC='',LBL='Base KPME Rule Document',PREV_DOC_TYP_VER_NBR='KPME0001',DOC_HDR_ID=null,DOC_HDLR_URL='${kuali.docHandler.url.prefix}/kr/maintenance.do?methodToCall=docHandler',HELP_DEF_URL='',DOC_SEARCH_HELP_URL='',POST_PRCSR=null,AUTHORIZER=null,GRP_ID=null,BLNKT_APPR_GRP_ID=null,BLNKT_APPR_PLCY=null,RPT_GRP_ID=null,RTE_VER_NBR='2',NOTIFY_ADDR=null,SEC_XML=null,EMAIL_XSL=null,APPL_ID='KPME',OBJ_ID='6694b4a4-69f3-4692-bac0-b43ea5ff462c',VER_NBR=18 WHERE DOC_TYP_ID = '3000'  AND VER_NBR = 17
;
INSERT INTO KREW_DOC_HDR_S VALUES (NULL)
;
INSERT INTO KREW_DOC_TYP_T (DOC_TYP_ID,PARNT_ID,DOC_TYP_NM,DOC_TYP_VER_NBR,ACTV_IND,CUR_IND,DOC_TYP_DESC,LBL,PREV_DOC_TYP_VER_NBR,DOC_HDR_ID,DOC_HDLR_URL,HELP_DEF_URL,DOC_SEARCH_HELP_URL,POST_PRCSR,AUTHORIZER,GRP_ID,BLNKT_APPR_GRP_ID,BLNKT_APPR_PLCY,RPT_GRP_ID,RTE_VER_NBR,NOTIFY_ADDR,SEC_XML,EMAIL_XSL,APPL_ID,OBJ_ID,VER_NBR) VALUES ('3026','3000','LeaveDonationDocument',0,1,1,'','Leave Donation Document',null,null,'${kuali.docHandler.url.prefix}/kr/maintenance.do?methodToCall=docHandler','','',null,null,null,null,null,null,'2',null,null,null,null,'78ea0e36-9049-4ed9-bbbc-bf79c2144231',1)
;
UPDATE KREW_DOC_TYP_T SET PARNT_ID='2681',DOC_TYP_NM='KpmeDocument',DOC_TYP_VER_NBR=4,ACTV_IND=1,CUR_IND=1,DOC_TYP_DESC='',LBL='Base KPME Rule Document',PREV_DOC_TYP_VER_NBR='KPME0001',DOC_HDR_ID=null,DOC_HDLR_URL='${kuali.docHandler.url.prefix}/kr/maintenance.do?methodToCall=docHandler',HELP_DEF_URL='',DOC_SEARCH_HELP_URL='',POST_PRCSR=null,AUTHORIZER=null,GRP_ID=null,BLNKT_APPR_GRP_ID=null,BLNKT_APPR_PLCY=null,RPT_GRP_ID=null,RTE_VER_NBR='2',NOTIFY_ADDR=null,SEC_XML=null,EMAIL_XSL=null,APPL_ID='KPME',OBJ_ID='6694b4a4-69f3-4692-bac0-b43ea5ff462c',VER_NBR=19 WHERE DOC_TYP_ID = '3000'  AND VER_NBR = 18
;
INSERT INTO KREW_DOC_HDR_S VALUES (NULL)
;
INSERT INTO KREW_DOC_TYP_T (DOC_TYP_ID,PARNT_ID,DOC_TYP_NM,DOC_TYP_VER_NBR,ACTV_IND,CUR_IND,DOC_TYP_DESC,LBL,PREV_DOC_TYP_VER_NBR,DOC_HDR_ID,DOC_HDLR_URL,HELP_DEF_URL,DOC_SEARCH_HELP_URL,POST_PRCSR,AUTHORIZER,GRP_ID,BLNKT_APPR_GRP_ID,BLNKT_APPR_PLCY,RPT_GRP_ID,RTE_VER_NBR,NOTIFY_ADDR,SEC_XML,EMAIL_XSL,APPL_ID,OBJ_ID,VER_NBR) VALUES ('3027','3000','LeavePayoutDocumentType',0,1,1,'Leave Payout Document','Leave Payout Document',null,null,'${kuali.docHandler.url.prefix}/kr/maintenance.do?methodToCall=docHandler','','',null,null,'KPME0001','KPME0001',null,null,'2',null,null,null,'KPME','4624d7ca-ec1d-4e8c-972a-6239ad44fa3c',1)
;
INSERT INTO KREW_DOC_TYP_PLCY_RELN_T (DOC_TYP_ID,DOC_PLCY_NM,PLCY_NM,PLCY_VAL,OBJ_ID,VER_NBR) VALUES ('3027','INITIATOR_MUST_CANCEL',0,null,'2b70dcaa-7a81-4ad0-bacb-49c8e802281a',1)
;
INSERT INTO KREW_DOC_TYP_PLCY_RELN_T (DOC_TYP_ID,DOC_PLCY_NM,PLCY_NM,PLCY_VAL,OBJ_ID,VER_NBR) VALUES ('3027','INITIATOR_MUST_ROUTE',0,null,'5df19578-54f4-4ed0-b3b3-37ae415767e8',1)
;
INSERT INTO KREW_RTE_NODE_S VALUES (NULL)
;
INSERT INTO KREW_RTE_NODE_T (RTE_NODE_ID,DOC_TYP_ID,NM,TYP,RTE_MTHD_NM,FNL_APRVR_IND,MNDTRY_RTE_IND,GRP_ID,RTE_MTHD_CD,ACTVN_TYP,BRCH_PROTO_ID,NEXT_DOC_STAT,VER_NBR) VALUES ('2923','3027','Initiated','org.kuali.rice.kew.engine.node.InitialNode',null,0,0,'KPME0001',null,'P',null,null,1)
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_S VALUES (NULL)
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2482','2923','contentFragment','<start name="Initiated">\n<activationType>P</activationType>\n<mandatoryRoute>false</mandatoryRoute>\n<finalApproval>false</finalApproval>\n</start>\n')
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_S VALUES (NULL)
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2483','2923','activationType','P')
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_S VALUES (NULL)
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2484','2923','mandatoryRoute','false')
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_S VALUES (NULL)
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2485','2923','finalApproval','false')
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_S VALUES (NULL)
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2486','2923','ruleSelector','Template')
;
INSERT INTO KREW_RTE_NODE_S VALUES (NULL)
;
INSERT INTO KREW_RTE_NODE_T (RTE_NODE_ID,DOC_TYP_ID,NM,TYP,RTE_MTHD_NM,FNL_APRVR_IND,MNDTRY_RTE_IND,GRP_ID,RTE_MTHD_CD,ACTVN_TYP,BRCH_PROTO_ID,NEXT_DOC_STAT,VER_NBR) VALUES ('2924','3027','LeavePayoutSplitNode','org.kuali.kpme.tklm.leave.workflow.LeavePayoutSplitNode',null,0,0,'KPME0001',null,'S',null,null,1)
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_S VALUES (NULL)
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2487','2924','contentFragment','<split name="LeavePayoutSplitNode">\n<type>org.kuali.kpme.tklm.leave.workflow.LeavePayoutSplitNode</type>\n</split>\n')
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_S VALUES (NULL)
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2488','2924','type','org.kuali.kpme.tklm.leave.workflow.LeavePayoutSplitNode')
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_S VALUES (NULL)
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2489','2924','ruleSelector','Template')
;
INSERT INTO KREW_RTE_NODE_LNK_T (TO_RTE_NODE_ID,FROM_RTE_NODE_ID) VALUES ('2924','2923')
;
INSERT INTO KREW_RTE_NODE_S VALUES (NULL)
;
INSERT INTO KREW_RTE_BRCH_PROTO_T (RTE_BRCH_PROTO_ID,BRCH_NM,VER_NBR) VALUES ('2925','calendar',1)
;
INSERT INTO KREW_RTE_NODE_S VALUES (NULL)
;
INSERT INTO KREW_RTE_NODE_T (RTE_NODE_ID,DOC_TYP_ID,NM,TYP,RTE_MTHD_NM,FNL_APRVR_IND,MNDTRY_RTE_IND,GRP_ID,RTE_MTHD_CD,ACTVN_TYP,BRCH_PROTO_ID,NEXT_DOC_STAT,VER_NBR) VALUES ('2926','3027','PeopleFlows','org.kuali.rice.kew.engine.node.RequestsNode',null,0,0,'KPME0001','RE','R','2925',null,1)
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_S VALUES (NULL)
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2490','2926','contentFragment','<requests name="PeopleFlows">\n<activationType>R</activationType>\n<rulesEngine executorClass="org.kuali.kpme.tklm.leave.workflow.krms.LeaveMaintDocRulesEngineExecutor"/>\n</requests>\n')
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_S VALUES (NULL)
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2491','2926','activationType','R')
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_S VALUES (NULL)
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2492','2926','rulesEngine','')
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_S VALUES (NULL)
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2493','2926','ruleSelector','Template')
;
INSERT INTO KREW_RTE_NODE_LNK_T (TO_RTE_NODE_ID,FROM_RTE_NODE_ID) VALUES ('2926','2924')
;
UPDATE KREW_RTE_BRCH_PROTO_T SET BRCH_NM='calendar',VER_NBR=2 WHERE RTE_BRCH_PROTO_ID = '2925'  AND VER_NBR = 1
;
INSERT INTO KREW_RTE_NODE_S VALUES (NULL)
;
INSERT INTO KREW_RTE_NODE_T (RTE_NODE_ID,DOC_TYP_ID,NM,TYP,RTE_MTHD_NM,FNL_APRVR_IND,MNDTRY_RTE_IND,GRP_ID,RTE_MTHD_CD,ACTVN_TYP,BRCH_PROTO_ID,NEXT_DOC_STAT,VER_NBR) VALUES ('2927','3027','JoinNode','org.kuali.rice.kew.engine.node.SimpleJoinNode',null,0,0,'KPME0001',null,'S','2925',null,1)
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_S VALUES (NULL)
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2494','2927','contentFragment','<join name="JoinNode"/>\n')
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_S VALUES (NULL)
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2495','2927','ruleSelector','Template')
;
INSERT INTO KREW_RTE_NODE_S VALUES (NULL)
;
INSERT INTO KREW_RTE_BRCH_PROTO_T (RTE_BRCH_PROTO_ID,BRCH_NM,VER_NBR) VALUES ('2928','maintenance',1)
;
INSERT INTO KREW_RTE_NODE_S VALUES (NULL)
;
INSERT INTO KREW_RTE_NODE_T (RTE_NODE_ID,DOC_TYP_ID,NM,TYP,RTE_MTHD_NM,FNL_APRVR_IND,MNDTRY_RTE_IND,GRP_ID,RTE_MTHD_CD,ACTVN_TYP,BRCH_PROTO_ID,NEXT_DOC_STAT,VER_NBR) VALUES ('2929','3027','noOpNode','org.kuali.rice.kew.engine.node.NoOpNode',null,0,0,'KPME0001',null,'S','2928',null,1)
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_S VALUES (NULL)
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2496','2929','contentFragment','<simple name="noOpNode">\n<type>org.kuali.rice.kew.engine.node.NoOpNode</type>\n</simple>\n')
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_S VALUES (NULL)
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2497','2929','type','org.kuali.rice.kew.engine.node.NoOpNode')
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_S VALUES (NULL)
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2498','2929','ruleSelector','Template')
;
INSERT INTO KREW_RTE_NODE_LNK_T (TO_RTE_NODE_ID,FROM_RTE_NODE_ID) VALUES ('2929','2924')
;
INSERT INTO KREW_RTE_NODE_LNK_T (FROM_RTE_NODE_ID,TO_RTE_NODE_ID) VALUES ('2929','2927')
;
INSERT INTO KREW_RTE_NODE_LNK_T (TO_RTE_NODE_ID,FROM_RTE_NODE_ID) VALUES ('2927','2926')
;
UPDATE KREW_RTE_BRCH_PROTO_T SET BRCH_NM='maintenance',VER_NBR=2 WHERE RTE_BRCH_PROTO_ID = '2928'  AND VER_NBR = 1
;
UPDATE KREW_RTE_NODE_T SET DOC_TYP_ID='3027',NM='noOpNode',TYP='org.kuali.rice.kew.engine.node.NoOpNode',RTE_MTHD_NM=null,FNL_APRVR_IND=0,MNDTRY_RTE_IND=0,GRP_ID='KPME0001',RTE_MTHD_CD=null,ACTVN_TYP='S',BRCH_PROTO_ID='2928',NEXT_DOC_STAT=null,VER_NBR=2 WHERE RTE_NODE_ID = '2929'  AND VER_NBR = 1
;
UPDATE KREW_RTE_NODE_CFG_PARM_T SET RTE_NODE_ID='2929',KEY_CD='contentFragment',VAL='<simple name="noOpNode">\n<type>org.kuali.rice.kew.engine.node.NoOpNode</type>\n</simple>\n' WHERE RTE_NODE_CFG_PARM_ID = '2496'
;
UPDATE KREW_RTE_NODE_CFG_PARM_T SET RTE_NODE_ID='2929',KEY_CD='type',VAL='org.kuali.rice.kew.engine.node.NoOpNode' WHERE RTE_NODE_CFG_PARM_ID = '2497'
;
UPDATE KREW_RTE_NODE_CFG_PARM_T SET RTE_NODE_ID='2929',KEY_CD='ruleSelector',VAL='Template' WHERE RTE_NODE_CFG_PARM_ID = '2498'
;
UPDATE KREW_RTE_BRCH_PROTO_T SET BRCH_NM='calendar',VER_NBR=3 WHERE RTE_BRCH_PROTO_ID = '2925'  AND VER_NBR = 2
;
UPDATE KREW_RTE_NODE_T SET DOC_TYP_ID='3027',NM='JoinNode',TYP='org.kuali.rice.kew.engine.node.SimpleJoinNode',RTE_MTHD_NM=null,FNL_APRVR_IND=0,MNDTRY_RTE_IND=0,GRP_ID='KPME0001',RTE_MTHD_CD=null,ACTVN_TYP='S',BRCH_PROTO_ID='2925',NEXT_DOC_STAT=null,VER_NBR=2 WHERE RTE_NODE_ID = '2927'  AND VER_NBR = 1
;
UPDATE KREW_RTE_NODE_CFG_PARM_T SET RTE_NODE_ID='2927',KEY_CD='contentFragment',VAL='<join name="JoinNode"/>\n' WHERE RTE_NODE_CFG_PARM_ID = '2494'
;
UPDATE KREW_RTE_NODE_CFG_PARM_T SET RTE_NODE_ID='2927',KEY_CD='ruleSelector',VAL='Template' WHERE RTE_NODE_CFG_PARM_ID = '2495'
;
UPDATE KREW_RTE_BRCH_PROTO_T SET BRCH_NM='calendar',VER_NBR=4 WHERE RTE_BRCH_PROTO_ID = '2925'  AND VER_NBR = 3
;
UPDATE KREW_RTE_NODE_T SET DOC_TYP_ID='3027',NM='PeopleFlows',TYP='org.kuali.rice.kew.engine.node.RequestsNode',RTE_MTHD_NM=null,FNL_APRVR_IND=0,MNDTRY_RTE_IND=0,GRP_ID='KPME0001',RTE_MTHD_CD='RE',ACTVN_TYP='R',BRCH_PROTO_ID='2925',NEXT_DOC_STAT=null,VER_NBR=2 WHERE RTE_NODE_ID = '2926'  AND VER_NBR = 1
;
UPDATE KREW_RTE_NODE_CFG_PARM_T SET RTE_NODE_ID='2926',KEY_CD='contentFragment',VAL='<requests name="PeopleFlows">\n<activationType>R</activationType>\n<rulesEngine executorClass="org.kuali.kpme.tklm.leave.workflow.krms.LeaveMaintDocRulesEngineExecutor"/>\n</requests>\n' WHERE RTE_NODE_CFG_PARM_ID = '2490'
;
UPDATE KREW_RTE_NODE_CFG_PARM_T SET RTE_NODE_ID='2926',KEY_CD='activationType',VAL='R' WHERE RTE_NODE_CFG_PARM_ID = '2491'
;
UPDATE KREW_RTE_NODE_CFG_PARM_T SET RTE_NODE_ID='2926',KEY_CD='rulesEngine',VAL='' WHERE RTE_NODE_CFG_PARM_ID = '2492'
;
UPDATE KREW_RTE_NODE_CFG_PARM_T SET RTE_NODE_ID='2926',KEY_CD='ruleSelector',VAL='Template' WHERE RTE_NODE_CFG_PARM_ID = '2493'
;
INSERT INTO KREW_RTE_NODE_S VALUES (NULL)
;
INSERT INTO KREW_DOC_TYP_PROC_T (DOC_TYP_PROC_ID,DOC_TYP_ID,INIT_RTE_NODE_ID,NM,INIT_IND,VER_NBR) VALUES ('2930','3027','2923','PRIMARY',1,1)
;
UPDATE KREW_DOC_TYP_T SET PARNT_ID='2681',DOC_TYP_NM='KpmeDocument',DOC_TYP_VER_NBR=4,ACTV_IND=1,CUR_IND=1,DOC_TYP_DESC='',LBL='Base KPME Rule Document',PREV_DOC_TYP_VER_NBR='KPME0001',DOC_HDR_ID=null,DOC_HDLR_URL='${kuali.docHandler.url.prefix}/kr/maintenance.do?methodToCall=docHandler',HELP_DEF_URL='',DOC_SEARCH_HELP_URL='',POST_PRCSR=null,AUTHORIZER=null,GRP_ID=null,BLNKT_APPR_GRP_ID=null,BLNKT_APPR_PLCY=null,RPT_GRP_ID=null,RTE_VER_NBR='2',NOTIFY_ADDR=null,SEC_XML=null,EMAIL_XSL=null,APPL_ID='KPME',OBJ_ID='6694b4a4-69f3-4692-bac0-b43ea5ff462c',VER_NBR=20 WHERE DOC_TYP_ID = '3000'  AND VER_NBR = 19
;
INSERT INTO KREW_DOC_HDR_S VALUES (NULL)
;
INSERT INTO KREW_DOC_TYP_T (DOC_TYP_ID,PARNT_ID,DOC_TYP_NM,DOC_TYP_VER_NBR,ACTV_IND,CUR_IND,DOC_TYP_DESC,LBL,PREV_DOC_TYP_VER_NBR,DOC_HDR_ID,DOC_HDLR_URL,HELP_DEF_URL,DOC_SEARCH_HELP_URL,POST_PRCSR,AUTHORIZER,GRP_ID,BLNKT_APPR_GRP_ID,BLNKT_APPR_PLCY,RPT_GRP_ID,RTE_VER_NBR,NOTIFY_ADDR,SEC_XML,EMAIL_XSL,APPL_ID,OBJ_ID,VER_NBR) VALUES ('3028','3000','LeavePlanDocumentType',0,1,1,'','Leave Plan Document',null,null,'${kuali.docHandler.url.prefix}/kr/maintenance.do?methodToCall=docHandler','','',null,null,null,null,null,null,'2',null,null,null,null,'e75e488a-10ab-416e-ac5f-5d8ce24d8339',1)
;
UPDATE KREW_DOC_TYP_T SET PARNT_ID='2681',DOC_TYP_NM='KpmeDocument',DOC_TYP_VER_NBR=4,ACTV_IND=1,CUR_IND=1,DOC_TYP_DESC='',LBL='Base KPME Rule Document',PREV_DOC_TYP_VER_NBR='KPME0001',DOC_HDR_ID=null,DOC_HDLR_URL='${kuali.docHandler.url.prefix}/kr/maintenance.do?methodToCall=docHandler',HELP_DEF_URL='',DOC_SEARCH_HELP_URL='',POST_PRCSR=null,AUTHORIZER=null,GRP_ID=null,BLNKT_APPR_GRP_ID=null,BLNKT_APPR_PLCY=null,RPT_GRP_ID=null,RTE_VER_NBR='2',NOTIFY_ADDR=null,SEC_XML=null,EMAIL_XSL=null,APPL_ID='KPME',OBJ_ID='6694b4a4-69f3-4692-bac0-b43ea5ff462c',VER_NBR=21 WHERE DOC_TYP_ID = '3000'  AND VER_NBR = 20
;
INSERT INTO KREW_DOC_HDR_S VALUES (NULL)
;
INSERT INTO KREW_DOC_TYP_T (DOC_TYP_ID,PARNT_ID,DOC_TYP_NM,DOC_TYP_VER_NBR,ACTV_IND,CUR_IND,DOC_TYP_DESC,LBL,PREV_DOC_TYP_VER_NBR,DOC_HDR_ID,DOC_HDLR_URL,HELP_DEF_URL,DOC_SEARCH_HELP_URL,POST_PRCSR,AUTHORIZER,GRP_ID,BLNKT_APPR_GRP_ID,BLNKT_APPR_PLCY,RPT_GRP_ID,RTE_VER_NBR,NOTIFY_ADDR,SEC_XML,EMAIL_XSL,APPL_ID,OBJ_ID,VER_NBR) VALUES ('3029','3000','LeaveRequestDocument',0,1,1,'Leave Request Document','Leave Request Document',null,null,'${kuali.docHandler.url.prefix}/LeaveRequestApproval.do','','','org.kuali.kpme.tklm.leave.workflow.postprocessor.LeaveRequestPostProcessor',null,'KPME0001','KPME0001',null,null,'1',null,null,null,'KPME','dba3dc2a-9dba-4e7b-bf5d-73bac8822bd0',1)
;
INSERT INTO KREW_DOC_TYP_PLCY_RELN_T (DOC_TYP_ID,DOC_PLCY_NM,PLCY_NM,PLCY_VAL,OBJ_ID,VER_NBR) VALUES ('3029','INITIATOR_MUST_CANCEL',0,null,'1490d875-02a8-44fc-a8f7-b38756ea1c74',1)
;
INSERT INTO KREW_DOC_TYP_PLCY_RELN_T (DOC_TYP_ID,DOC_PLCY_NM,PLCY_NM,PLCY_VAL,OBJ_ID,VER_NBR) VALUES ('3029','INITIATOR_MUST_ROUTE',0,null,'5b8fd1e4-b13f-426d-9e4a-54ace690bfee',1)
;
INSERT INTO KREW_DOC_TYP_PLCY_RELN_T (DOC_TYP_ID,DOC_PLCY_NM,PLCY_NM,PLCY_VAL,OBJ_ID,VER_NBR) VALUES ('3029','NOTIFY_COMPLETED_ON_RETURN',1,null,'d1397b4e-090c-4ce3-8b3e-c548a17221ea',1)
;
INSERT INTO KREW_DOC_TYP_ATTR_S VALUES (NULL)
;
INSERT INTO KREW_DOC_TYP_ATTR_T (DOC_TYP_ATTRIB_ID,DOC_TYP_ID,RULE_ATTR_ID,ORD_INDX) VALUES ('2013','3029','1663',1)
;
INSERT INTO KREW_DOC_TYP_ATTR_S VALUES (NULL)
;
INSERT INTO KREW_DOC_TYP_ATTR_T (DOC_TYP_ATTRIB_ID,DOC_TYP_ID,RULE_ATTR_ID,ORD_INDX) VALUES ('2014','3029','1655',2)
;
INSERT INTO KREW_DOC_TYP_ATTR_S VALUES (NULL)
;
INSERT INTO KREW_DOC_TYP_ATTR_T (DOC_TYP_ATTRIB_ID,DOC_TYP_ID,RULE_ATTR_ID,ORD_INDX) VALUES ('2015','3029','1659',3)
;
INSERT INTO KREW_DOC_TYP_ATTR_S VALUES (NULL)
;
INSERT INTO KREW_DOC_TYP_ATTR_T (DOC_TYP_ATTRIB_ID,DOC_TYP_ID,RULE_ATTR_ID,ORD_INDX) VALUES ('2016','3029','1660',4)
;

INSERT INTO KREW_DOC_TYP_ATTR_T (DOC_TYP_ATTRIB_ID,DOC_TYP_ID,RULE_ATTR_ID,ORD_INDX) VALUES ('2017','3029','1661',5)
;
INSERT INTO KREW_RTE_NODE_S VALUES (NULL)
;
INSERT INTO KREW_RTE_NODE_T (RTE_NODE_ID,DOC_TYP_ID,NM,TYP,RTE_MTHD_NM,FNL_APRVR_IND,MNDTRY_RTE_IND,GRP_ID,RTE_MTHD_CD,ACTVN_TYP,BRCH_PROTO_ID,NEXT_DOC_STAT,VER_NBR) VALUES ('2931','3029','Initiated','org.kuali.rice.kew.engine.node.InitialNode',null,0,0,'KPME0001',null,'P',null,null,1)
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_S VALUES (NULL)
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2499','2931','contentFragment','<start name="Initiated">\n<activationType>P</activationType>\n<mandatoryRoute>false</mandatoryRoute>\n<finalApproval>false</finalApproval>\n</start>\n')
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_S VALUES (NULL)
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2500','2931','activationType','P')
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_S VALUES (NULL)
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2501','2931','mandatoryRoute','false')
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_S VALUES (NULL)
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2502','2931','finalApproval','false')
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_S VALUES (NULL)
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2503','2931','ruleSelector','Template')
;
INSERT INTO KREW_RTE_NODE_S VALUES (NULL)
;
INSERT INTO KREW_RTE_NODE_T (RTE_NODE_ID,DOC_TYP_ID,NM,TYP,RTE_MTHD_NM,FNL_APRVR_IND,MNDTRY_RTE_IND,GRP_ID,RTE_MTHD_CD,ACTVN_TYP,BRCH_PROTO_ID,NEXT_DOC_STAT,VER_NBR) VALUES ('2932','3029','PeopleFlows','org.kuali.rice.kew.engine.node.RequestsNode',null,0,0,'KPME0001','RE','R',null,null,1)
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_S VALUES (NULL)
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2504','2932','contentFragment','<requests name="PeopleFlows">\n<activationType>R</activationType>\n<rulesEngine executorClass="org.kuali.kpme.tklm.leave.request.krms.LeaveRequestRulesEngineExecutor"/>\n</requests>\n')
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_S VALUES (NULL)
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2505','2932','activationType','R')
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_S VALUES (NULL)
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2506','2932','rulesEngine','')
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_S VALUES (NULL)
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2507','2932','ruleSelector','Template')
;
INSERT INTO KREW_RTE_NODE_LNK_T (TO_RTE_NODE_ID,FROM_RTE_NODE_ID) VALUES ('2932','2931')
;
INSERT INTO KREW_RTE_NODE_S VALUES (NULL)
;
INSERT INTO KREW_DOC_TYP_PROC_T (DOC_TYP_PROC_ID,DOC_TYP_ID,INIT_RTE_NODE_ID,NM,INIT_IND,VER_NBR) VALUES ('2933','3029','2931','PRIMARY',1,1)
;
UPDATE KREW_DOC_TYP_T SET PARNT_ID='2681',DOC_TYP_NM='KpmeDocument',DOC_TYP_VER_NBR=4,ACTV_IND=1,CUR_IND=1,DOC_TYP_DESC='',LBL='Base KPME Rule Document',PREV_DOC_TYP_VER_NBR='KPME0001',DOC_HDR_ID=null,DOC_HDLR_URL='${kuali.docHandler.url.prefix}/kr/maintenance.do?methodToCall=docHandler',HELP_DEF_URL='',DOC_SEARCH_HELP_URL='',POST_PRCSR=null,AUTHORIZER=null,GRP_ID=null,BLNKT_APPR_GRP_ID=null,BLNKT_APPR_PLCY=null,RPT_GRP_ID=null,RTE_VER_NBR='2',NOTIFY_ADDR=null,SEC_XML=null,EMAIL_XSL=null,APPL_ID='KPME',OBJ_ID='6694b4a4-69f3-4692-bac0-b43ea5ff462c',VER_NBR=22 WHERE DOC_TYP_ID = '3000'  AND VER_NBR = 21
;
INSERT INTO KREW_DOC_HDR_S VALUES (NULL)
;
INSERT INTO KREW_DOC_TYP_T (DOC_TYP_ID,PARNT_ID,DOC_TYP_NM,DOC_TYP_VER_NBR,ACTV_IND,CUR_IND,DOC_TYP_DESC,LBL,PREV_DOC_TYP_VER_NBR,DOC_HDR_ID,DOC_HDLR_URL,HELP_DEF_URL,DOC_SEARCH_HELP_URL,POST_PRCSR,AUTHORIZER,GRP_ID,BLNKT_APPR_GRP_ID,BLNKT_APPR_PLCY,RPT_GRP_ID,RTE_VER_NBR,NOTIFY_ADDR,SEC_XML,EMAIL_XSL,APPL_ID,OBJ_ID,VER_NBR) VALUES ('3030','3001','LocationMaintenanceDocumentType',0,1,1,'','Location Document',null,null,null,'','',null,null,null,null,null,null,'2',null,null,null,null,'d8d162b5-a660-4ec8-ac24-400cdd04d04d',1)
;
UPDATE KREW_DOC_TYP_T SET PARNT_ID='3000',DOC_TYP_NM='KpmeEffectiveDateMaintenanceDocumentType',DOC_TYP_VER_NBR=4,ACTV_IND=1,CUR_IND=1,DOC_TYP_DESC='',LBL='Kpme Effective Maintenance Document',PREV_DOC_TYP_VER_NBR='KPME0002',DOC_HDR_ID=null,DOC_HDLR_URL='${kuali.docHandler.url.prefix}/kr-krad/kpme/effectiveDateMaintenance?methodToCall=docHandler',HELP_DEF_URL='',DOC_SEARCH_HELP_URL='',POST_PRCSR=null,AUTHORIZER=null,GRP_ID=null,BLNKT_APPR_GRP_ID=null,BLNKT_APPR_PLCY=null,RPT_GRP_ID=null,RTE_VER_NBR='2',NOTIFY_ADDR=null,SEC_XML=null,EMAIL_XSL=null,APPL_ID=null,OBJ_ID='2ea12a2b-0722-4855-8137-fa2d5f4465e1',VER_NBR=10 WHERE DOC_TYP_ID = '3001'  AND VER_NBR = 9
;
INSERT INTO KREW_DOC_HDR_S VALUES (NULL)
;
INSERT INTO KREW_DOC_TYP_T (DOC_TYP_ID,PARNT_ID,DOC_TYP_NM,DOC_TYP_VER_NBR,ACTV_IND,CUR_IND,DOC_TYP_DESC,LBL,PREV_DOC_TYP_VER_NBR,DOC_HDR_ID,DOC_HDLR_URL,HELP_DEF_URL,DOC_SEARCH_HELP_URL,POST_PRCSR,AUTHORIZER,GRP_ID,BLNKT_APPR_GRP_ID,BLNKT_APPR_PLCY,RPT_GRP_ID,RTE_VER_NBR,NOTIFY_ADDR,SEC_XML,EMAIL_XSL,APPL_ID,OBJ_ID,VER_NBR) VALUES ('3031','3000','MissedPunchDocumentType',0,1,1,'Missed Punch Document','MissedPunchDocument',null,null,'${kuali.docHandler.url.prefix}/kpme/missedPunch?methodToCall=docHandler','','',null,null,'KPME0001','KPME0001',null,null,'1',null,'<security>\n<securityAttribute name="MissedPunchSecurityFilterAttribute"/>\n</security>\n',null,'KPME','7f8e49c5-1cb3-4c78-aba2-09925165e132',1)
;
INSERT INTO KREW_DOC_TYP_ATTR_S VALUES (NULL)
;
INSERT INTO KREW_DOC_TYP_ATTR_T (DOC_TYP_ATTRIB_ID,DOC_TYP_ID,RULE_ATTR_ID,ORD_INDX) VALUES ('2018','3031','1664',1)
;
INSERT INTO KREW_RTE_NODE_S VALUES (NULL)
;
INSERT INTO KREW_RTE_NODE_T (RTE_NODE_ID,DOC_TYP_ID,NM,TYP,RTE_MTHD_NM,FNL_APRVR_IND,MNDTRY_RTE_IND,GRP_ID,RTE_MTHD_CD,ACTVN_TYP,BRCH_PROTO_ID,NEXT_DOC_STAT,VER_NBR) VALUES ('2934','3031','adhoc','org.kuali.rice.kew.engine.node.InitialNode',null,0,0,'KPME0001',null,'P',null,null,1)
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_S VALUES (NULL)
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2508','2934','contentFragment','<start name="adhoc">\n<exceptionGroupName namespace="KPME-HR">System Administrator</exceptionGroupName>\n<activationType>P</activationType>\n<mandatoryRoute>false</mandatoryRoute>\n<finalApproval>false</finalApproval>\n</start>\n')
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_S VALUES (NULL)
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2509','2934','exceptionGroupName','System Administrator')
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_S VALUES (NULL)
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2510','2934','activationType','P')
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_S VALUES (NULL)
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2511','2934','mandatoryRoute','false')
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_S VALUES (NULL)
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2512','2934','finalApproval','false')
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_S VALUES (NULL)
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2513','2934','ruleSelector','Template')
;
INSERT INTO KREW_RTE_NODE_S VALUES (NULL)
;
INSERT INTO KREW_RTE_NODE_T (RTE_NODE_ID,DOC_TYP_ID,NM,TYP,RTE_MTHD_NM,FNL_APRVR_IND,MNDTRY_RTE_IND,GRP_ID,RTE_MTHD_CD,ACTVN_TYP,BRCH_PROTO_ID,NEXT_DOC_STAT,VER_NBR) VALUES ('2935','3031','PeopleFlows','org.kuali.rice.kew.engine.node.RequestsNode',null,0,0,'KPME0001','RE','R',null,null,1)
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_S VALUES (NULL)
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2514','2935','contentFragment','<requests name="PeopleFlows">\n<activationType>R</activationType>\n<rulesEngine executorClass="org.kuali.kpme.tklm.time.missedpunch.rules.MissedPunchRulesEngineExecutor"/>\n</requests>\n')
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_S VALUES (NULL)
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2515','2935','activationType','R')
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_S VALUES (NULL)
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2516','2935','rulesEngine','')
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_S VALUES (NULL)
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2517','2935','ruleSelector','Template')
;
INSERT INTO KREW_RTE_NODE_LNK_T (TO_RTE_NODE_ID,FROM_RTE_NODE_ID) VALUES ('2935','2934')
;
INSERT INTO KREW_RTE_NODE_S VALUES (NULL)
;
INSERT INTO KREW_DOC_TYP_PROC_T (DOC_TYP_PROC_ID,DOC_TYP_ID,INIT_RTE_NODE_ID,NM,INIT_IND,VER_NBR) VALUES ('2936','3031','2934','PRIMARY',1,1)
;
UPDATE KREW_DOC_TYP_T SET PARNT_ID='2681',DOC_TYP_NM='KpmeDocument',DOC_TYP_VER_NBR=4,ACTV_IND=1,CUR_IND=1,DOC_TYP_DESC='',LBL='Base KPME Rule Document',PREV_DOC_TYP_VER_NBR='KPME0001',DOC_HDR_ID=null,DOC_HDLR_URL='${kuali.docHandler.url.prefix}/kr/maintenance.do?methodToCall=docHandler',HELP_DEF_URL='',DOC_SEARCH_HELP_URL='',POST_PRCSR=null,AUTHORIZER=null,GRP_ID=null,BLNKT_APPR_GRP_ID=null,BLNKT_APPR_PLCY=null,RPT_GRP_ID=null,RTE_VER_NBR='2',NOTIFY_ADDR=null,SEC_XML=null,EMAIL_XSL=null,APPL_ID='KPME',OBJ_ID='6694b4a4-69f3-4692-bac0-b43ea5ff462c',VER_NBR=23 WHERE DOC_TYP_ID = '3000'  AND VER_NBR = 22
;
INSERT INTO KREW_DOC_HDR_S VALUES (NULL)
;
INSERT INTO KREW_DOC_TYP_T (DOC_TYP_ID,PARNT_ID,DOC_TYP_NM,DOC_TYP_VER_NBR,ACTV_IND,CUR_IND,DOC_TYP_DESC,LBL,PREV_DOC_TYP_VER_NBR,DOC_HDR_ID,DOC_HDLR_URL,HELP_DEF_URL,DOC_SEARCH_HELP_URL,POST_PRCSR,AUTHORIZER,GRP_ID,BLNKT_APPR_GRP_ID,BLNKT_APPR_PLCY,RPT_GRP_ID,RTE_VER_NBR,NOTIFY_ADDR,SEC_XML,EMAIL_XSL,APPL_ID,OBJ_ID,VER_NBR) VALUES ('3032','3000','ObjectCodeMaintenanceDocumentType',0,1,1,'','Object Code',null,null,'${kuali.docHandler.url.prefix}/kr/maintenance.do?methodToCall=docHandler','','',null,null,null,null,null,null,'2',null,null,null,null,'54cc39a0-ae0d-4c5a-9d34-1875ebcd9817',1)
;
UPDATE KREW_DOC_TYP_T SET PARNT_ID='2681',DOC_TYP_NM='KpmeDocument',DOC_TYP_VER_NBR=4,ACTV_IND=1,CUR_IND=1,DOC_TYP_DESC='',LBL='Base KPME Rule Document',PREV_DOC_TYP_VER_NBR='KPME0001',DOC_HDR_ID=null,DOC_HDLR_URL='${kuali.docHandler.url.prefix}/kr/maintenance.do?methodToCall=docHandler',HELP_DEF_URL='',DOC_SEARCH_HELP_URL='',POST_PRCSR=null,AUTHORIZER=null,GRP_ID=null,BLNKT_APPR_GRP_ID=null,BLNKT_APPR_PLCY=null,RPT_GRP_ID=null,RTE_VER_NBR='2',NOTIFY_ADDR=null,SEC_XML=null,EMAIL_XSL=null,APPL_ID='KPME',OBJ_ID='6694b4a4-69f3-4692-bac0-b43ea5ff462c',VER_NBR=24 WHERE DOC_TYP_ID = '3000'  AND VER_NBR = 23
;
INSERT INTO KREW_DOC_HDR_S VALUES (NULL)
;
INSERT INTO KREW_DOC_TYP_T (DOC_TYP_ID,PARNT_ID,DOC_TYP_NM,DOC_TYP_VER_NBR,ACTV_IND,CUR_IND,DOC_TYP_DESC,LBL,PREV_DOC_TYP_VER_NBR,DOC_HDR_ID,DOC_HDLR_URL,HELP_DEF_URL,DOC_SEARCH_HELP_URL,POST_PRCSR,AUTHORIZER,GRP_ID,BLNKT_APPR_GRP_ID,BLNKT_APPR_PLCY,RPT_GRP_ID,RTE_VER_NBR,NOTIFY_ADDR,SEC_XML,EMAIL_XSL,APPL_ID,OBJ_ID,VER_NBR) VALUES ('3033','3000','OrganizationMaintenanceDocumentType',0,1,1,'','Organization',null,null,'${kuali.docHandler.url.prefix}/kr/maintenance.do?methodToCall=docHandler','','',null,null,null,null,null,null,'2',null,null,null,null,'6203a1ae-d44a-4bd3-b06f-9c4794108d6f',1)
;
UPDATE KREW_DOC_TYP_T SET PARNT_ID='2681',DOC_TYP_NM='KpmeDocument',DOC_TYP_VER_NBR=4,ACTV_IND=1,CUR_IND=1,DOC_TYP_DESC='',LBL='Base KPME Rule Document',PREV_DOC_TYP_VER_NBR='KPME0001',DOC_HDR_ID=null,DOC_HDLR_URL='${kuali.docHandler.url.prefix}/kr/maintenance.do?methodToCall=docHandler',HELP_DEF_URL='',DOC_SEARCH_HELP_URL='',POST_PRCSR=null,AUTHORIZER=null,GRP_ID=null,BLNKT_APPR_GRP_ID=null,BLNKT_APPR_PLCY=null,RPT_GRP_ID=null,RTE_VER_NBR='2',NOTIFY_ADDR=null,SEC_XML=null,EMAIL_XSL=null,APPL_ID='KPME',OBJ_ID='6694b4a4-69f3-4692-bac0-b43ea5ff462c',VER_NBR=25 WHERE DOC_TYP_ID = '3000'  AND VER_NBR = 24
;
INSERT INTO KREW_DOC_HDR_S VALUES (NULL)
;
INSERT INTO KREW_DOC_TYP_T (DOC_TYP_ID,PARNT_ID,DOC_TYP_NM,DOC_TYP_VER_NBR,ACTV_IND,CUR_IND,DOC_TYP_DESC,LBL,PREV_DOC_TYP_VER_NBR,DOC_HDR_ID,DOC_HDLR_URL,HELP_DEF_URL,DOC_SEARCH_HELP_URL,POST_PRCSR,AUTHORIZER,GRP_ID,BLNKT_APPR_GRP_ID,BLNKT_APPR_PLCY,RPT_GRP_ID,RTE_VER_NBR,NOTIFY_ADDR,SEC_XML,EMAIL_XSL,APPL_ID,OBJ_ID,VER_NBR) VALUES ('3034','3000','ProjectCodeMaintenanceDocumentType',0,1,1,'','Project Code',null,null,'${kuali.docHandler.url.prefix}/kr/maintenance.do?methodToCall=docHandler','','',null,null,null,null,null,null,'2',null,null,null,null,'a30df1d1-42e5-4b4c-b8bc-fe01e45ca1c3',1)
;
UPDATE KREW_DOC_TYP_T SET PARNT_ID='2681',DOC_TYP_NM='KpmeDocument',DOC_TYP_VER_NBR=4,ACTV_IND=1,CUR_IND=1,DOC_TYP_DESC='',LBL='Base KPME Rule Document',PREV_DOC_TYP_VER_NBR='KPME0001',DOC_HDR_ID=null,DOC_HDLR_URL='${kuali.docHandler.url.prefix}/kr/maintenance.do?methodToCall=docHandler',HELP_DEF_URL='',DOC_SEARCH_HELP_URL='',POST_PRCSR=null,AUTHORIZER=null,GRP_ID=null,BLNKT_APPR_GRP_ID=null,BLNKT_APPR_PLCY=null,RPT_GRP_ID=null,RTE_VER_NBR='2',NOTIFY_ADDR=null,SEC_XML=null,EMAIL_XSL=null,APPL_ID='KPME',OBJ_ID='6694b4a4-69f3-4692-bac0-b43ea5ff462c',VER_NBR=26 WHERE DOC_TYP_ID = '3000'  AND VER_NBR = 25
;
INSERT INTO KREW_DOC_HDR_S VALUES (NULL)
;
INSERT INTO KREW_DOC_TYP_T (DOC_TYP_ID,PARNT_ID,DOC_TYP_NM,DOC_TYP_VER_NBR,ACTV_IND,CUR_IND,DOC_TYP_DESC,LBL,PREV_DOC_TYP_VER_NBR,DOC_HDR_ID,DOC_HDLR_URL,HELP_DEF_URL,DOC_SEARCH_HELP_URL,POST_PRCSR,AUTHORIZER,GRP_ID,BLNKT_APPR_GRP_ID,BLNKT_APPR_PLCY,RPT_GRP_ID,RTE_VER_NBR,NOTIFY_ADDR,SEC_XML,EMAIL_XSL,APPL_ID,OBJ_ID,VER_NBR) VALUES ('3035','3000','PayCalendarDocumentType',0,1,1,'','Calendar Document',null,null,'${kuali.docHandler.url.prefix}/kr-krad/kpme/nonEffectiveMaintenance?methodToCall=docHandler','','',null,null,null,null,null,null,'2',null,null,null,null,'eb7c1694-37f2-4372-a9f8-b04cd0537f16',1)
;
UPDATE KREW_DOC_TYP_T SET PARNT_ID='2681',DOC_TYP_NM='KpmeDocument',DOC_TYP_VER_NBR=4,ACTV_IND=1,CUR_IND=1,DOC_TYP_DESC='',LBL='Base KPME Rule Document',PREV_DOC_TYP_VER_NBR='KPME0001',DOC_HDR_ID=null,DOC_HDLR_URL='${kuali.docHandler.url.prefix}/kr/maintenance.do?methodToCall=docHandler',HELP_DEF_URL='',DOC_SEARCH_HELP_URL='',POST_PRCSR=null,AUTHORIZER=null,GRP_ID=null,BLNKT_APPR_GRP_ID=null,BLNKT_APPR_PLCY=null,RPT_GRP_ID=null,RTE_VER_NBR='2',NOTIFY_ADDR=null,SEC_XML=null,EMAIL_XSL=null,APPL_ID='KPME',OBJ_ID='6694b4a4-69f3-4692-bac0-b43ea5ff462c',VER_NBR=27 WHERE DOC_TYP_ID = '3000'  AND VER_NBR = 26
;
INSERT INTO KREW_DOC_HDR_S VALUES (NULL)
;
INSERT INTO KREW_DOC_TYP_T (DOC_TYP_ID,PARNT_ID,DOC_TYP_NM,DOC_TYP_VER_NBR,ACTV_IND,CUR_IND,DOC_TYP_DESC,LBL,PREV_DOC_TYP_VER_NBR,DOC_HDR_ID,DOC_HDLR_URL,HELP_DEF_URL,DOC_SEARCH_HELP_URL,POST_PRCSR,AUTHORIZER,GRP_ID,BLNKT_APPR_GRP_ID,BLNKT_APPR_PLCY,RPT_GRP_ID,RTE_VER_NBR,NOTIFY_ADDR,SEC_XML,EMAIL_XSL,APPL_ID,OBJ_ID,VER_NBR) VALUES ('3036','3000','PayCalendarEntriesDocumentType',0,1,1,'','Calendar Entry Document',null,null,'${kuali.docHandler.url.prefix}/kr-krad/kpme/nonEffectiveMaintenance?methodToCall=docHandler','','',null,null,null,null,null,null,'2',null,null,null,null,'76c7b599-3ef9-404e-be0d-10c6941deefe',1)
;
UPDATE KREW_DOC_TYP_T SET PARNT_ID='2681',DOC_TYP_NM='KpmeDocument',DOC_TYP_VER_NBR=4,ACTV_IND=1,CUR_IND=1,DOC_TYP_DESC='',LBL='Base KPME Rule Document',PREV_DOC_TYP_VER_NBR='KPME0001',DOC_HDR_ID=null,DOC_HDLR_URL='${kuali.docHandler.url.prefix}/kr/maintenance.do?methodToCall=docHandler',HELP_DEF_URL='',DOC_SEARCH_HELP_URL='',POST_PRCSR=null,AUTHORIZER=null,GRP_ID=null,BLNKT_APPR_GRP_ID=null,BLNKT_APPR_PLCY=null,RPT_GRP_ID=null,RTE_VER_NBR='2',NOTIFY_ADDR=null,SEC_XML=null,EMAIL_XSL=null,APPL_ID='KPME',OBJ_ID='6694b4a4-69f3-4692-bac0-b43ea5ff462c',VER_NBR=28 WHERE DOC_TYP_ID = '3000'  AND VER_NBR = 27
;
INSERT INTO KREW_DOC_HDR_S VALUES (NULL)
;
INSERT INTO KREW_DOC_TYP_T (DOC_TYP_ID,PARNT_ID,DOC_TYP_NM,DOC_TYP_VER_NBR,ACTV_IND,CUR_IND,DOC_TYP_DESC,LBL,PREV_DOC_TYP_VER_NBR,DOC_HDR_ID,DOC_HDLR_URL,HELP_DEF_URL,DOC_SEARCH_HELP_URL,POST_PRCSR,AUTHORIZER,GRP_ID,BLNKT_APPR_GRP_ID,BLNKT_APPR_PLCY,RPT_GRP_ID,RTE_VER_NBR,NOTIFY_ADDR,SEC_XML,EMAIL_XSL,APPL_ID,OBJ_ID,VER_NBR) VALUES ('3037','3001','PayGradeMaintenanceDocumentType',0,1,1,'','Pay Grade Document',null,null,null,'','',null,null,null,null,null,null,'2',null,null,null,null,'610a6329-67a3-4c51-9c1a-e33cd4c18fe1',1)
;
UPDATE KREW_DOC_TYP_T SET PARNT_ID='3000',DOC_TYP_NM='KpmeEffectiveDateMaintenanceDocumentType',DOC_TYP_VER_NBR=4,ACTV_IND=1,CUR_IND=1,DOC_TYP_DESC='',LBL='Kpme Effective Maintenance Document',PREV_DOC_TYP_VER_NBR='KPME0002',DOC_HDR_ID=null,DOC_HDLR_URL='${kuali.docHandler.url.prefix}/kr-krad/kpme/effectiveDateMaintenance?methodToCall=docHandler',HELP_DEF_URL='',DOC_SEARCH_HELP_URL='',POST_PRCSR=null,AUTHORIZER=null,GRP_ID=null,BLNKT_APPR_GRP_ID=null,BLNKT_APPR_PLCY=null,RPT_GRP_ID=null,RTE_VER_NBR='2',NOTIFY_ADDR=null,SEC_XML=null,EMAIL_XSL=null,APPL_ID=null,OBJ_ID='2ea12a2b-0722-4855-8137-fa2d5f4465e1',VER_NBR=11 WHERE DOC_TYP_ID = '3001'  AND VER_NBR = 10
;
INSERT INTO KREW_DOC_HDR_S VALUES (NULL)
;
INSERT INTO KREW_DOC_TYP_T (DOC_TYP_ID,PARNT_ID,DOC_TYP_NM,DOC_TYP_VER_NBR,ACTV_IND,CUR_IND,DOC_TYP_DESC,LBL,PREV_DOC_TYP_VER_NBR,DOC_HDR_ID,DOC_HDLR_URL,HELP_DEF_URL,DOC_SEARCH_HELP_URL,POST_PRCSR,AUTHORIZER,GRP_ID,BLNKT_APPR_GRP_ID,BLNKT_APPR_PLCY,RPT_GRP_ID,RTE_VER_NBR,NOTIFY_ADDR,SEC_XML,EMAIL_XSL,APPL_ID,OBJ_ID,VER_NBR) VALUES ('3038','3000','PayStepMaintenanceDocumentType',0,1,1,'','Pay Step Maintenance',null,null,'${kuali.docHandler.url.prefix}/kr/maintenance.do?methodToCall=docHandler','','',null,null,null,null,null,null,'2',null,null,null,null,'908cbde2-d46e-4ced-b02e-3ff397ee0123',1)
;
UPDATE KREW_DOC_TYP_T SET PARNT_ID='2681',DOC_TYP_NM='KpmeDocument',DOC_TYP_VER_NBR=4,ACTV_IND=1,CUR_IND=1,DOC_TYP_DESC='',LBL='Base KPME Rule Document',PREV_DOC_TYP_VER_NBR='KPME0001',DOC_HDR_ID=null,DOC_HDLR_URL='${kuali.docHandler.url.prefix}/kr/maintenance.do?methodToCall=docHandler',HELP_DEF_URL='',DOC_SEARCH_HELP_URL='',POST_PRCSR=null,AUTHORIZER=null,GRP_ID=null,BLNKT_APPR_GRP_ID=null,BLNKT_APPR_PLCY=null,RPT_GRP_ID=null,RTE_VER_NBR='2',NOTIFY_ADDR=null,SEC_XML=null,EMAIL_XSL=null,APPL_ID='KPME',OBJ_ID='6694b4a4-69f3-4692-bac0-b43ea5ff462c',VER_NBR=29 WHERE DOC_TYP_ID = '3000'  AND VER_NBR = 28
;
INSERT INTO KREW_DOC_HDR_S VALUES (NULL)
;
INSERT INTO KREW_DOC_TYP_T (DOC_TYP_ID,PARNT_ID,DOC_TYP_NM,DOC_TYP_VER_NBR,ACTV_IND,CUR_IND,DOC_TYP_DESC,LBL,PREV_DOC_TYP_VER_NBR,DOC_HDR_ID,DOC_HDLR_URL,HELP_DEF_URL,DOC_SEARCH_HELP_URL,POST_PRCSR,AUTHORIZER,GRP_ID,BLNKT_APPR_GRP_ID,BLNKT_APPR_PLCY,RPT_GRP_ID,RTE_VER_NBR,NOTIFY_ADDR,SEC_XML,EMAIL_XSL,APPL_ID,OBJ_ID,VER_NBR) VALUES ('3039','3001','PayTypeDocumentType',0,1,1,'','Pay Type Document',null,null,null,'','',null,null,null,null,null,null,'2',null,null,null,null,'1f5364ae-5515-46c9-96e6-c2a66cbf2996',1)
;
UPDATE KREW_DOC_TYP_T SET PARNT_ID='3000',DOC_TYP_NM='KpmeEffectiveDateMaintenanceDocumentType',DOC_TYP_VER_NBR=4,ACTV_IND=1,CUR_IND=1,DOC_TYP_DESC='',LBL='Kpme Effective Maintenance Document',PREV_DOC_TYP_VER_NBR='KPME0002',DOC_HDR_ID=null,DOC_HDLR_URL='${kuali.docHandler.url.prefix}/kr-krad/kpme/effectiveDateMaintenance?methodToCall=docHandler',HELP_DEF_URL='',DOC_SEARCH_HELP_URL='',POST_PRCSR=null,AUTHORIZER=null,GRP_ID=null,BLNKT_APPR_GRP_ID=null,BLNKT_APPR_PLCY=null,RPT_GRP_ID=null,RTE_VER_NBR='2',NOTIFY_ADDR=null,SEC_XML=null,EMAIL_XSL=null,APPL_ID=null,OBJ_ID='2ea12a2b-0722-4855-8137-fa2d5f4465e1',VER_NBR=12 WHERE DOC_TYP_ID = '3001'  AND VER_NBR = 11
;
INSERT INTO KREW_DOC_HDR_S VALUES (NULL)
;
INSERT INTO KREW_DOC_TYP_T (DOC_TYP_ID,PARNT_ID,DOC_TYP_NM,DOC_TYP_VER_NBR,ACTV_IND,CUR_IND,DOC_TYP_DESC,LBL,PREV_DOC_TYP_VER_NBR,DOC_HDR_ID,DOC_HDLR_URL,HELP_DEF_URL,DOC_SEARCH_HELP_URL,POST_PRCSR,AUTHORIZER,GRP_ID,BLNKT_APPR_GRP_ID,BLNKT_APPR_PLCY,RPT_GRP_ID,RTE_VER_NBR,NOTIFY_ADDR,SEC_XML,EMAIL_XSL,APPL_ID,OBJ_ID,VER_NBR) VALUES ('3040','3001','PositionAppointmentMaintenanceDocumentType',0,1,1,'','Position Appointment Document',null,null,null,'','',null,null,null,null,null,null,'2',null,null,null,null,'00d0b58c-d8f4-4a1a-907d-a91b2383cd16',1)
;
UPDATE KREW_DOC_TYP_T SET PARNT_ID='3000',DOC_TYP_NM='KpmeEffectiveDateMaintenanceDocumentType',DOC_TYP_VER_NBR=4,ACTV_IND=1,CUR_IND=1,DOC_TYP_DESC='',LBL='Kpme Effective Maintenance Document',PREV_DOC_TYP_VER_NBR='KPME0002',DOC_HDR_ID=null,DOC_HDLR_URL='${kuali.docHandler.url.prefix}/kr-krad/kpme/effectiveDateMaintenance?methodToCall=docHandler',HELP_DEF_URL='',DOC_SEARCH_HELP_URL='',POST_PRCSR=null,AUTHORIZER=null,GRP_ID=null,BLNKT_APPR_GRP_ID=null,BLNKT_APPR_PLCY=null,RPT_GRP_ID=null,RTE_VER_NBR='2',NOTIFY_ADDR=null,SEC_XML=null,EMAIL_XSL=null,APPL_ID=null,OBJ_ID='2ea12a2b-0722-4855-8137-fa2d5f4465e1',VER_NBR=13 WHERE DOC_TYP_ID = '3001'  AND VER_NBR = 12
;
INSERT INTO KREW_DOC_HDR_S VALUES (NULL)
;
INSERT INTO KREW_DOC_TYP_T (DOC_TYP_ID,PARNT_ID,DOC_TYP_NM,DOC_TYP_VER_NBR,ACTV_IND,CUR_IND,DOC_TYP_DESC,LBL,PREV_DOC_TYP_VER_NBR,DOC_HDR_ID,DOC_HDLR_URL,HELP_DEF_URL,DOC_SEARCH_HELP_URL,POST_PRCSR,AUTHORIZER,GRP_ID,BLNKT_APPR_GRP_ID,BLNKT_APPR_PLCY,RPT_GRP_ID,RTE_VER_NBR,NOTIFY_ADDR,SEC_XML,EMAIL_XSL,APPL_ID,OBJ_ID,VER_NBR) VALUES ('3041','3001','PositionBaseMaintenanceDocumentType',0,1,1,'','Position Document',null,null,null,'','',null,null,null,null,null,null,'2',null,null,null,null,'f829ed64-00be-4c01-93cc-4792ea0dc70f',1)
;
UPDATE KREW_DOC_TYP_T SET PARNT_ID='3000',DOC_TYP_NM='KpmeEffectiveDateMaintenanceDocumentType',DOC_TYP_VER_NBR=4,ACTV_IND=1,CUR_IND=1,DOC_TYP_DESC='',LBL='Kpme Effective Maintenance Document',PREV_DOC_TYP_VER_NBR='KPME0002',DOC_HDR_ID=null,DOC_HDLR_URL='${kuali.docHandler.url.prefix}/kr-krad/kpme/effectiveDateMaintenance?methodToCall=docHandler',HELP_DEF_URL='',DOC_SEARCH_HELP_URL='',POST_PRCSR=null,AUTHORIZER=null,GRP_ID=null,BLNKT_APPR_GRP_ID=null,BLNKT_APPR_PLCY=null,RPT_GRP_ID=null,RTE_VER_NBR='2',NOTIFY_ADDR=null,SEC_XML=null,EMAIL_XSL=null,APPL_ID=null,OBJ_ID='2ea12a2b-0722-4855-8137-fa2d5f4465e1',VER_NBR=14 WHERE DOC_TYP_ID = '3001'  AND VER_NBR = 13
;
INSERT INTO KREW_DOC_HDR_S VALUES (NULL)
;
INSERT INTO KREW_DOC_TYP_T (DOC_TYP_ID,PARNT_ID,DOC_TYP_NM,DOC_TYP_VER_NBR,ACTV_IND,CUR_IND,DOC_TYP_DESC,LBL,PREV_DOC_TYP_VER_NBR,DOC_HDR_ID,DOC_HDLR_URL,HELP_DEF_URL,DOC_SEARCH_HELP_URL,POST_PRCSR,AUTHORIZER,GRP_ID,BLNKT_APPR_GRP_ID,BLNKT_APPR_PLCY,RPT_GRP_ID,RTE_VER_NBR,NOTIFY_ADDR,SEC_XML,EMAIL_XSL,APPL_ID,OBJ_ID,VER_NBR) VALUES ('3042','3001','ClassificationMaintenanceDocumentType',0,1,1,'','Classification Maintenance',null,null,null,'','',null,null,null,null,null,null,'2',null,null,null,null,'50f25e55-bb98-4611-8c0a-418512a91b18',1)
;
UPDATE KREW_DOC_TYP_T SET PARNT_ID='3000',DOC_TYP_NM='KpmeEffectiveDateMaintenanceDocumentType',DOC_TYP_VER_NBR=4,ACTV_IND=1,CUR_IND=1,DOC_TYP_DESC='',LBL='Kpme Effective Maintenance Document',PREV_DOC_TYP_VER_NBR='KPME0002',DOC_HDR_ID=null,DOC_HDLR_URL='${kuali.docHandler.url.prefix}/kr-krad/kpme/effectiveDateMaintenance?methodToCall=docHandler',HELP_DEF_URL='',DOC_SEARCH_HELP_URL='',POST_PRCSR=null,AUTHORIZER=null,GRP_ID=null,BLNKT_APPR_GRP_ID=null,BLNKT_APPR_PLCY=null,RPT_GRP_ID=null,RTE_VER_NBR='2',NOTIFY_ADDR=null,SEC_XML=null,EMAIL_XSL=null,APPL_ID=null,OBJ_ID='2ea12a2b-0722-4855-8137-fa2d5f4465e1',VER_NBR=15 WHERE DOC_TYP_ID = '3001'  AND VER_NBR = 14
;
INSERT INTO KREW_DOC_HDR_S VALUES (NULL)
;
INSERT INTO KREW_DOC_TYP_T (DOC_TYP_ID,PARNT_ID,DOC_TYP_NM,DOC_TYP_VER_NBR,ACTV_IND,CUR_IND,DOC_TYP_DESC,LBL,PREV_DOC_TYP_VER_NBR,DOC_HDR_ID,DOC_HDLR_URL,HELP_DEF_URL,DOC_SEARCH_HELP_URL,POST_PRCSR,AUTHORIZER,GRP_ID,BLNKT_APPR_GRP_ID,BLNKT_APPR_PLCY,RPT_GRP_ID,RTE_VER_NBR,NOTIFY_ADDR,SEC_XML,EMAIL_XSL,APPL_ID,OBJ_ID,VER_NBR) VALUES ('3043','3001','PositionDepartmentMaintenanceDocumentType',0,1,1,'','Position Department Maintenance',null,null,null,'','',null,null,null,null,null,null,'2',null,null,null,null,'0eaf304c-5602-467b-8f14-08ca2a9c9c3b',1)
;
UPDATE KREW_DOC_TYP_T SET PARNT_ID='3000',DOC_TYP_NM='KpmeEffectiveDateMaintenanceDocumentType',DOC_TYP_VER_NBR=4,ACTV_IND=1,CUR_IND=1,DOC_TYP_DESC='',LBL='Kpme Effective Maintenance Document',PREV_DOC_TYP_VER_NBR='KPME0002',DOC_HDR_ID=null,DOC_HDLR_URL='${kuali.docHandler.url.prefix}/kr-krad/kpme/effectiveDateMaintenance?methodToCall=docHandler',HELP_DEF_URL='',DOC_SEARCH_HELP_URL='',POST_PRCSR=null,AUTHORIZER=null,GRP_ID=null,BLNKT_APPR_GRP_ID=null,BLNKT_APPR_PLCY=null,RPT_GRP_ID=null,RTE_VER_NBR='2',NOTIFY_ADDR=null,SEC_XML=null,EMAIL_XSL=null,APPL_ID=null,OBJ_ID='2ea12a2b-0722-4855-8137-fa2d5f4465e1',VER_NBR=16 WHERE DOC_TYP_ID = '3001'  AND VER_NBR = 15
;
INSERT INTO KREW_DOC_HDR_S VALUES (NULL)
;
INSERT INTO KREW_DOC_TYP_T (DOC_TYP_ID,PARNT_ID,DOC_TYP_NM,DOC_TYP_VER_NBR,ACTV_IND,CUR_IND,DOC_TYP_DESC,LBL,PREV_DOC_TYP_VER_NBR,DOC_HDR_ID,DOC_HDLR_URL,HELP_DEF_URL,DOC_SEARCH_HELP_URL,POST_PRCSR,AUTHORIZER,GRP_ID,BLNKT_APPR_GRP_ID,BLNKT_APPR_PLCY,RPT_GRP_ID,RTE_VER_NBR,NOTIFY_ADDR,SEC_XML,EMAIL_XSL,APPL_ID,OBJ_ID,VER_NBR) VALUES ('3044','3001','PositionFlagMaintenanceDocumentType',0,1,1,'','Position Flag Maintenance',null,null,null,'','',null,null,null,null,null,null,'2',null,null,null,null,'0a358771-5a3d-464a-8faa-49aa14d4e72e',1)
;
UPDATE KREW_DOC_TYP_T SET PARNT_ID='3000',DOC_TYP_NM='KpmeEffectiveDateMaintenanceDocumentType',DOC_TYP_VER_NBR=4,ACTV_IND=1,CUR_IND=1,DOC_TYP_DESC='',LBL='Kpme Effective Maintenance Document',PREV_DOC_TYP_VER_NBR='KPME0002',DOC_HDR_ID=null,DOC_HDLR_URL='${kuali.docHandler.url.prefix}/kr-krad/kpme/effectiveDateMaintenance?methodToCall=docHandler',HELP_DEF_URL='',DOC_SEARCH_HELP_URL='',POST_PRCSR=null,AUTHORIZER=null,GRP_ID=null,BLNKT_APPR_GRP_ID=null,BLNKT_APPR_PLCY=null,RPT_GRP_ID=null,RTE_VER_NBR='2',NOTIFY_ADDR=null,SEC_XML=null,EMAIL_XSL=null,APPL_ID=null,OBJ_ID='2ea12a2b-0722-4855-8137-fa2d5f4465e1',VER_NBR=17 WHERE DOC_TYP_ID = '3001'  AND VER_NBR = 16
;
INSERT INTO KREW_DOC_HDR_S VALUES (NULL)
;
INSERT INTO KREW_DOC_TYP_T (DOC_TYP_ID,PARNT_ID,DOC_TYP_NM,DOC_TYP_VER_NBR,ACTV_IND,CUR_IND,DOC_TYP_DESC,LBL,PREV_DOC_TYP_VER_NBR,DOC_HDR_ID,DOC_HDLR_URL,HELP_DEF_URL,DOC_SEARCH_HELP_URL,POST_PRCSR,AUTHORIZER,GRP_ID,BLNKT_APPR_GRP_ID,BLNKT_APPR_PLCY,RPT_GRP_ID,RTE_VER_NBR,NOTIFY_ADDR,SEC_XML,EMAIL_XSL,APPL_ID,OBJ_ID,VER_NBR) VALUES ('3045','3001','PositionMaintenanceDocumentType',0,1,1,'','Position Document',null,null,null,'','',null,null,null,null,null,null,'1',null,null,null,'KPME','32cd1542-53f9-48ea-ad43-72c572981c0d',1)
;
INSERT INTO KREW_RTE_NODE_S VALUES (NULL)
;
INSERT INTO KREW_RTE_NODE_T (RTE_NODE_ID,DOC_TYP_ID,NM,TYP,RTE_MTHD_NM,FNL_APRVR_IND,MNDTRY_RTE_IND,GRP_ID,RTE_MTHD_CD,ACTVN_TYP,BRCH_PROTO_ID,NEXT_DOC_STAT,VER_NBR) VALUES ('2937','3045','Initiated','org.kuali.rice.kew.engine.node.InitialNode',null,0,0,null,null,'P',null,null,1)
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_S VALUES (NULL)
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2518','2937','contentFragment','<start name="Initiated">\n<activationType>P</activationType>\n<mandatoryRoute>false</mandatoryRoute>\n<finalApproval>false</finalApproval>\n</start>\n')
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_S VALUES (NULL)
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2519','2937','activationType','P')
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_S VALUES (NULL)
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2520','2937','mandatoryRoute','false')
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_S VALUES (NULL)
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2521','2937','finalApproval','false')
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_S VALUES (NULL)
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2522','2937','ruleSelector','Template')
;
INSERT INTO KREW_RTE_NODE_S VALUES (NULL)
;
INSERT INTO KREW_RTE_NODE_T (RTE_NODE_ID,DOC_TYP_ID,NM,TYP,RTE_MTHD_NM,FNL_APRVR_IND,MNDTRY_RTE_IND,GRP_ID,RTE_MTHD_CD,ACTVN_TYP,BRCH_PROTO_ID,NEXT_DOC_STAT,VER_NBR) VALUES ('2938','3045','PeopleFlows','org.kuali.rice.kew.engine.node.RequestsNode',null,0,0,null,'RE','R',null,null,1)
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_S VALUES (NULL)
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2523','2938','contentFragment','<requests name="PeopleFlows">\n<activationType>R</activationType>\n<rulesEngine executorClass="org.kuali.kpme.pm.workflow.krms.PositionMaintDocRulesEngineExecutor"/>\n</requests>\n')
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_S VALUES (NULL)
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2524','2938','activationType','R')
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_S VALUES (NULL)
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2525','2938','rulesEngine','')
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_S VALUES (NULL)
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2526','2938','ruleSelector','Template')
;
INSERT INTO KREW_RTE_NODE_LNK_T (TO_RTE_NODE_ID,FROM_RTE_NODE_ID) VALUES ('2938','2937')
;
INSERT INTO KREW_RTE_NODE_S VALUES (NULL)
;
INSERT INTO KREW_DOC_TYP_PROC_T (DOC_TYP_PROC_ID,DOC_TYP_ID,INIT_RTE_NODE_ID,NM,INIT_IND,VER_NBR) VALUES ('2939','3045','2937','PRIMARY',1,1)
;
UPDATE KREW_DOC_TYP_T SET PARNT_ID='3000',DOC_TYP_NM='KpmeEffectiveDateMaintenanceDocumentType',DOC_TYP_VER_NBR=4,ACTV_IND=1,CUR_IND=1,DOC_TYP_DESC='',LBL='Kpme Effective Maintenance Document',PREV_DOC_TYP_VER_NBR='KPME0002',DOC_HDR_ID=null,DOC_HDLR_URL='${kuali.docHandler.url.prefix}/kr-krad/kpme/effectiveDateMaintenance?methodToCall=docHandler',HELP_DEF_URL='',DOC_SEARCH_HELP_URL='',POST_PRCSR=null,AUTHORIZER=null,GRP_ID=null,BLNKT_APPR_GRP_ID=null,BLNKT_APPR_PLCY=null,RPT_GRP_ID=null,RTE_VER_NBR='2',NOTIFY_ADDR=null,SEC_XML=null,EMAIL_XSL=null,APPL_ID=null,OBJ_ID='2ea12a2b-0722-4855-8137-fa2d5f4465e1',VER_NBR=18 WHERE DOC_TYP_ID = '3001'  AND VER_NBR = 17
;
INSERT INTO KREW_DOC_HDR_S VALUES (NULL)
;
INSERT INTO KREW_DOC_TYP_T (DOC_TYP_ID,PARNT_ID,DOC_TYP_NM,DOC_TYP_VER_NBR,ACTV_IND,CUR_IND,DOC_TYP_DESC,LBL,PREV_DOC_TYP_VER_NBR,DOC_HDR_ID,DOC_HDLR_URL,HELP_DEF_URL,DOC_SEARCH_HELP_URL,POST_PRCSR,AUTHORIZER,GRP_ID,BLNKT_APPR_GRP_ID,BLNKT_APPR_PLCY,RPT_GRP_ID,RTE_VER_NBR,NOTIFY_ADDR,SEC_XML,EMAIL_XSL,APPL_ID,OBJ_ID,VER_NBR) VALUES ('3046','3001','PositionReportCatMaintenanceDocumentType',0,1,1,'','Position Report Category Maintenance',null,null,null,'','',null,null,null,null,null,null,'2',null,null,null,null,'cec8768c-3db8-4cf7-a0fe-f3df1d9b3cff',1)
;
UPDATE KREW_DOC_TYP_T SET PARNT_ID='3000',DOC_TYP_NM='KpmeEffectiveDateMaintenanceDocumentType',DOC_TYP_VER_NBR=4,ACTV_IND=1,CUR_IND=1,DOC_TYP_DESC='',LBL='Kpme Effective Maintenance Document',PREV_DOC_TYP_VER_NBR='KPME0002',DOC_HDR_ID=null,DOC_HDLR_URL='${kuali.docHandler.url.prefix}/kr-krad/kpme/effectiveDateMaintenance?methodToCall=docHandler',HELP_DEF_URL='',DOC_SEARCH_HELP_URL='',POST_PRCSR=null,AUTHORIZER=null,GRP_ID=null,BLNKT_APPR_GRP_ID=null,BLNKT_APPR_PLCY=null,RPT_GRP_ID=null,RTE_VER_NBR='2',NOTIFY_ADDR=null,SEC_XML=null,EMAIL_XSL=null,APPL_ID=null,OBJ_ID='2ea12a2b-0722-4855-8137-fa2d5f4465e1',VER_NBR=19 WHERE DOC_TYP_ID = '3001'  AND VER_NBR = 18
;
INSERT INTO KREW_DOC_HDR_S VALUES (NULL)
;
INSERT INTO KREW_DOC_TYP_T (DOC_TYP_ID,PARNT_ID,DOC_TYP_NM,DOC_TYP_VER_NBR,ACTV_IND,CUR_IND,DOC_TYP_DESC,LBL,PREV_DOC_TYP_VER_NBR,DOC_HDR_ID,DOC_HDLR_URL,HELP_DEF_URL,DOC_SEARCH_HELP_URL,POST_PRCSR,AUTHORIZER,GRP_ID,BLNKT_APPR_GRP_ID,BLNKT_APPR_PLCY,RPT_GRP_ID,RTE_VER_NBR,NOTIFY_ADDR,SEC_XML,EMAIL_XSL,APPL_ID,OBJ_ID,VER_NBR) VALUES ('3047','3001','PositionReportGroupMaintenanceDocumentType',0,1,1,'','Position Report Group Maintenance',null,null,null,'','',null,null,null,null,null,null,'2',null,null,null,null,'fc72834e-0e34-40c2-a4e8-03685de32ed5',1)
;
UPDATE KREW_DOC_TYP_T SET PARNT_ID='3000',DOC_TYP_NM='KpmeEffectiveDateMaintenanceDocumentType',DOC_TYP_VER_NBR=4,ACTV_IND=1,CUR_IND=1,DOC_TYP_DESC='',LBL='Kpme Effective Maintenance Document',PREV_DOC_TYP_VER_NBR='KPME0002',DOC_HDR_ID=null,DOC_HDLR_URL='${kuali.docHandler.url.prefix}/kr-krad/kpme/effectiveDateMaintenance?methodToCall=docHandler',HELP_DEF_URL='',DOC_SEARCH_HELP_URL='',POST_PRCSR=null,AUTHORIZER=null,GRP_ID=null,BLNKT_APPR_GRP_ID=null,BLNKT_APPR_PLCY=null,RPT_GRP_ID=null,RTE_VER_NBR='2',NOTIFY_ADDR=null,SEC_XML=null,EMAIL_XSL=null,APPL_ID=null,OBJ_ID='2ea12a2b-0722-4855-8137-fa2d5f4465e1',VER_NBR=20 WHERE DOC_TYP_ID = '3001'  AND VER_NBR = 19
;
INSERT INTO KREW_DOC_HDR_S VALUES (NULL)
;
INSERT INTO KREW_DOC_TYP_T (DOC_TYP_ID,PARNT_ID,DOC_TYP_NM,DOC_TYP_VER_NBR,ACTV_IND,CUR_IND,DOC_TYP_DESC,LBL,PREV_DOC_TYP_VER_NBR,DOC_HDR_ID,DOC_HDLR_URL,HELP_DEF_URL,DOC_SEARCH_HELP_URL,POST_PRCSR,AUTHORIZER,GRP_ID,BLNKT_APPR_GRP_ID,BLNKT_APPR_PLCY,RPT_GRP_ID,RTE_VER_NBR,NOTIFY_ADDR,SEC_XML,EMAIL_XSL,APPL_ID,OBJ_ID,VER_NBR) VALUES ('3048','3001','PositionReportSubCatMaintenanceDocumentType',0,1,1,'','Position Report Sub Category Maintenance',null,null,null,'','',null,null,null,null,null,null,'2',null,null,null,null,'e73a24fb-78df-41e2-8f08-2344b8add8b1',1)
;
UPDATE KREW_DOC_TYP_T SET PARNT_ID='3000',DOC_TYP_NM='KpmeEffectiveDateMaintenanceDocumentType',DOC_TYP_VER_NBR=4,ACTV_IND=1,CUR_IND=1,DOC_TYP_DESC='',LBL='Kpme Effective Maintenance Document',PREV_DOC_TYP_VER_NBR='KPME0002',DOC_HDR_ID=null,DOC_HDLR_URL='${kuali.docHandler.url.prefix}/kr-krad/kpme/effectiveDateMaintenance?methodToCall=docHandler',HELP_DEF_URL='',DOC_SEARCH_HELP_URL='',POST_PRCSR=null,AUTHORIZER=null,GRP_ID=null,BLNKT_APPR_GRP_ID=null,BLNKT_APPR_PLCY=null,RPT_GRP_ID=null,RTE_VER_NBR='2',NOTIFY_ADDR=null,SEC_XML=null,EMAIL_XSL=null,APPL_ID=null,OBJ_ID='2ea12a2b-0722-4855-8137-fa2d5f4465e1',VER_NBR=21 WHERE DOC_TYP_ID = '3001'  AND VER_NBR = 20
;
INSERT INTO KREW_DOC_HDR_S VALUES (NULL)
;
INSERT INTO KREW_DOC_TYP_T (DOC_TYP_ID,PARNT_ID,DOC_TYP_NM,DOC_TYP_VER_NBR,ACTV_IND,CUR_IND,DOC_TYP_DESC,LBL,PREV_DOC_TYP_VER_NBR,DOC_HDR_ID,DOC_HDLR_URL,HELP_DEF_URL,DOC_SEARCH_HELP_URL,POST_PRCSR,AUTHORIZER,GRP_ID,BLNKT_APPR_GRP_ID,BLNKT_APPR_PLCY,RPT_GRP_ID,RTE_VER_NBR,NOTIFY_ADDR,SEC_XML,EMAIL_XSL,APPL_ID,OBJ_ID,VER_NBR) VALUES ('3049','3001','PositionReportTypeMaintenanceDocumentType',0,1,1,'','Position Report Type Maintenance',null,null,null,'','',null,null,null,null,null,null,'2',null,null,null,null,'ec777616-6a25-4dae-93ec-ff801f3c5715',1)
;
UPDATE KREW_DOC_TYP_T SET PARNT_ID='3000',DOC_TYP_NM='KpmeEffectiveDateMaintenanceDocumentType',DOC_TYP_VER_NBR=4,ACTV_IND=1,CUR_IND=1,DOC_TYP_DESC='',LBL='Kpme Effective Maintenance Document',PREV_DOC_TYP_VER_NBR='KPME0002',DOC_HDR_ID=null,DOC_HDLR_URL='${kuali.docHandler.url.prefix}/kr-krad/kpme/effectiveDateMaintenance?methodToCall=docHandler',HELP_DEF_URL='',DOC_SEARCH_HELP_URL='',POST_PRCSR=null,AUTHORIZER=null,GRP_ID=null,BLNKT_APPR_GRP_ID=null,BLNKT_APPR_PLCY=null,RPT_GRP_ID=null,RTE_VER_NBR='2',NOTIFY_ADDR=null,SEC_XML=null,EMAIL_XSL=null,APPL_ID=null,OBJ_ID='2ea12a2b-0722-4855-8137-fa2d5f4465e1',VER_NBR=22 WHERE DOC_TYP_ID = '3001'  AND VER_NBR = 21
;
INSERT INTO KREW_DOC_HDR_S VALUES (NULL)
;
INSERT INTO KREW_DOC_TYP_T (DOC_TYP_ID,PARNT_ID,DOC_TYP_NM,DOC_TYP_VER_NBR,ACTV_IND,CUR_IND,DOC_TYP_DESC,LBL,PREV_DOC_TYP_VER_NBR,DOC_HDR_ID,DOC_HDLR_URL,HELP_DEF_URL,DOC_SEARCH_HELP_URL,POST_PRCSR,AUTHORIZER,GRP_ID,BLNKT_APPR_GRP_ID,BLNKT_APPR_PLCY,RPT_GRP_ID,RTE_VER_NBR,NOTIFY_ADDR,SEC_XML,EMAIL_XSL,APPL_ID,OBJ_ID,VER_NBR) VALUES ('3050','3001','PositionResponsibilityOptionMaintenanceDocumentType',0,1,1,'','Position Responsibility Option Maintenance',null,null,null,'','',null,null,null,null,null,null,'2',null,null,null,null,'53804ccf-3351-48e6-a9bd-c5f91af8c8aa',1)
;
UPDATE KREW_DOC_TYP_T SET PARNT_ID='3000',DOC_TYP_NM='KpmeEffectiveDateMaintenanceDocumentType',DOC_TYP_VER_NBR=4,ACTV_IND=1,CUR_IND=1,DOC_TYP_DESC='',LBL='Kpme Effective Maintenance Document',PREV_DOC_TYP_VER_NBR='KPME0002',DOC_HDR_ID=null,DOC_HDLR_URL='${kuali.docHandler.url.prefix}/kr-krad/kpme/effectiveDateMaintenance?methodToCall=docHandler',HELP_DEF_URL='',DOC_SEARCH_HELP_URL='',POST_PRCSR=null,AUTHORIZER=null,GRP_ID=null,BLNKT_APPR_GRP_ID=null,BLNKT_APPR_PLCY=null,RPT_GRP_ID=null,RTE_VER_NBR='2',NOTIFY_ADDR=null,SEC_XML=null,EMAIL_XSL=null,APPL_ID=null,OBJ_ID='2ea12a2b-0722-4855-8137-fa2d5f4465e1',VER_NBR=23 WHERE DOC_TYP_ID = '3001'  AND VER_NBR = 22
;
INSERT INTO KREW_DOC_HDR_S VALUES (NULL)
;
INSERT INTO KREW_DOC_TYP_T (DOC_TYP_ID,PARNT_ID,DOC_TYP_NM,DOC_TYP_VER_NBR,ACTV_IND,CUR_IND,DOC_TYP_DESC,LBL,PREV_DOC_TYP_VER_NBR,DOC_HDR_ID,DOC_HDLR_URL,HELP_DEF_URL,DOC_SEARCH_HELP_URL,POST_PRCSR,AUTHORIZER,GRP_ID,BLNKT_APPR_GRP_ID,BLNKT_APPR_PLCY,RPT_GRP_ID,RTE_VER_NBR,NOTIFY_ADDR,SEC_XML,EMAIL_XSL,APPL_ID,OBJ_ID,VER_NBR) VALUES ('3051','3001','PositionTypeMaintenanceDocumentType',0,1,1,'','Position Type Maintenance',null,null,null,'','',null,null,null,null,null,null,'2',null,null,null,null,'45ef5604-58cf-4ed8-9a70-aea9df96631b',1)
;
UPDATE KREW_DOC_TYP_T SET PARNT_ID='3000',DOC_TYP_NM='KpmeEffectiveDateMaintenanceDocumentType',DOC_TYP_VER_NBR=4,ACTV_IND=1,CUR_IND=1,DOC_TYP_DESC='',LBL='Kpme Effective Maintenance Document',PREV_DOC_TYP_VER_NBR='KPME0002',DOC_HDR_ID=null,DOC_HDLR_URL='${kuali.docHandler.url.prefix}/kr-krad/kpme/effectiveDateMaintenance?methodToCall=docHandler',HELP_DEF_URL='',DOC_SEARCH_HELP_URL='',POST_PRCSR=null,AUTHORIZER=null,GRP_ID=null,BLNKT_APPR_GRP_ID=null,BLNKT_APPR_PLCY=null,RPT_GRP_ID=null,RTE_VER_NBR='2',NOTIFY_ADDR=null,SEC_XML=null,EMAIL_XSL=null,APPL_ID=null,OBJ_ID='2ea12a2b-0722-4855-8137-fa2d5f4465e1',VER_NBR=24 WHERE DOC_TYP_ID = '3001'  AND VER_NBR = 23
;
INSERT INTO KREW_DOC_HDR_S VALUES (NULL)
;
INSERT INTO KREW_DOC_TYP_T (DOC_TYP_ID,PARNT_ID,DOC_TYP_NM,DOC_TYP_VER_NBR,ACTV_IND,CUR_IND,DOC_TYP_DESC,LBL,PREV_DOC_TYP_VER_NBR,DOC_HDR_ID,DOC_HDLR_URL,HELP_DEF_URL,DOC_SEARCH_HELP_URL,POST_PRCSR,AUTHORIZER,GRP_ID,BLNKT_APPR_GRP_ID,BLNKT_APPR_PLCY,RPT_GRP_ID,RTE_VER_NBR,NOTIFY_ADDR,SEC_XML,EMAIL_XSL,APPL_ID,OBJ_ID,VER_NBR) VALUES ('3052','3000','PrincipalCalendarMaintenanceDocumentType',0,1,1,'','Principal Calendar Document',null,null,'${kuali.docHandler.url.prefix}/kr/maintenance.do?methodToCall=docHandler','','',null,null,null,null,null,null,'2',null,null,null,null,'989ea129-f24c-4776-8ebe-82910bbc32ff',1)
;
UPDATE KREW_DOC_TYP_T SET PARNT_ID='2681',DOC_TYP_NM='KpmeDocument',DOC_TYP_VER_NBR=4,ACTV_IND=1,CUR_IND=1,DOC_TYP_DESC='',LBL='Base KPME Rule Document',PREV_DOC_TYP_VER_NBR='KPME0001',DOC_HDR_ID=null,DOC_HDLR_URL='${kuali.docHandler.url.prefix}/kr/maintenance.do?methodToCall=docHandler',HELP_DEF_URL='',DOC_SEARCH_HELP_URL='',POST_PRCSR=null,AUTHORIZER=null,GRP_ID=null,BLNKT_APPR_GRP_ID=null,BLNKT_APPR_PLCY=null,RPT_GRP_ID=null,RTE_VER_NBR='2',NOTIFY_ADDR=null,SEC_XML=null,EMAIL_XSL=null,APPL_ID='KPME',OBJ_ID='6694b4a4-69f3-4692-bac0-b43ea5ff462c',VER_NBR=30 WHERE DOC_TYP_ID = '3000'  AND VER_NBR = 29
;
INSERT INTO KREW_DOC_HDR_S VALUES (NULL)
;
INSERT INTO KREW_DOC_TYP_T (DOC_TYP_ID,PARNT_ID,DOC_TYP_NM,DOC_TYP_VER_NBR,ACTV_IND,CUR_IND,DOC_TYP_DESC,LBL,PREV_DOC_TYP_VER_NBR,DOC_HDR_ID,DOC_HDLR_URL,HELP_DEF_URL,DOC_SEARCH_HELP_URL,POST_PRCSR,AUTHORIZER,GRP_ID,BLNKT_APPR_GRP_ID,BLNKT_APPR_PLCY,RPT_GRP_ID,RTE_VER_NBR,NOTIFY_ADDR,SEC_XML,EMAIL_XSL,APPL_ID,OBJ_ID,VER_NBR) VALUES ('3053','3001','PrincipalHRAttributesMaintenanceDocumentType',0,1,1,'','Principal HR Attributes Document',null,null,null,'','',null,null,null,null,null,null,'2',null,null,null,null,'330f5528-aa24-4f2f-9fd7-2d82396d4715',1)
;
UPDATE KREW_DOC_TYP_T SET PARNT_ID='3000',DOC_TYP_NM='KpmeEffectiveDateMaintenanceDocumentType',DOC_TYP_VER_NBR=4,ACTV_IND=1,CUR_IND=1,DOC_TYP_DESC='',LBL='Kpme Effective Maintenance Document',PREV_DOC_TYP_VER_NBR='KPME0002',DOC_HDR_ID=null,DOC_HDLR_URL='${kuali.docHandler.url.prefix}/kr-krad/kpme/effectiveDateMaintenance?methodToCall=docHandler',HELP_DEF_URL='',DOC_SEARCH_HELP_URL='',POST_PRCSR=null,AUTHORIZER=null,GRP_ID=null,BLNKT_APPR_GRP_ID=null,BLNKT_APPR_PLCY=null,RPT_GRP_ID=null,RTE_VER_NBR='2',NOTIFY_ADDR=null,SEC_XML=null,EMAIL_XSL=null,APPL_ID=null,OBJ_ID='2ea12a2b-0722-4855-8137-fa2d5f4465e1',VER_NBR=25 WHERE DOC_TYP_ID = '3001'  AND VER_NBR = 24
;
INSERT INTO KREW_DOC_HDR_S VALUES (NULL)
;
INSERT INTO KREW_DOC_TYP_T (DOC_TYP_ID,PARNT_ID,DOC_TYP_NM,DOC_TYP_VER_NBR,ACTV_IND,CUR_IND,DOC_TYP_DESC,LBL,PREV_DOC_TYP_VER_NBR,DOC_HDR_ID,DOC_HDLR_URL,HELP_DEF_URL,DOC_SEARCH_HELP_URL,POST_PRCSR,AUTHORIZER,GRP_ID,BLNKT_APPR_GRP_ID,BLNKT_APPR_PLCY,RPT_GRP_ID,RTE_VER_NBR,NOTIFY_ADDR,SEC_XML,EMAIL_XSL,APPL_ID,OBJ_ID,VER_NBR) VALUES ('3054','3001','PstnContractTypeMaintenanceDocumentType',0,1,1,'','Position Contract Type Maintenance',null,null,null,'','',null,null,null,null,null,null,'2',null,null,null,null,'4a61d8e9-4b88-4da5-ae8c-ac4f6292f23a',1)
;
UPDATE KREW_DOC_TYP_T SET PARNT_ID='3000',DOC_TYP_NM='KpmeEffectiveDateMaintenanceDocumentType',DOC_TYP_VER_NBR=4,ACTV_IND=1,CUR_IND=1,DOC_TYP_DESC='',LBL='Kpme Effective Maintenance Document',PREV_DOC_TYP_VER_NBR='KPME0002',DOC_HDR_ID=null,DOC_HDLR_URL='${kuali.docHandler.url.prefix}/kr-krad/kpme/effectiveDateMaintenance?methodToCall=docHandler',HELP_DEF_URL='',DOC_SEARCH_HELP_URL='',POST_PRCSR=null,AUTHORIZER=null,GRP_ID=null,BLNKT_APPR_GRP_ID=null,BLNKT_APPR_PLCY=null,RPT_GRP_ID=null,RTE_VER_NBR='2',NOTIFY_ADDR=null,SEC_XML=null,EMAIL_XSL=null,APPL_ID=null,OBJ_ID='2ea12a2b-0722-4855-8137-fa2d5f4465e1',VER_NBR=26 WHERE DOC_TYP_ID = '3001'  AND VER_NBR = 25
;
INSERT INTO KREW_DOC_HDR_S VALUES (NULL)
;
INSERT INTO KREW_DOC_TYP_T (DOC_TYP_ID,PARNT_ID,DOC_TYP_NM,DOC_TYP_VER_NBR,ACTV_IND,CUR_IND,DOC_TYP_DESC,LBL,PREV_DOC_TYP_VER_NBR,DOC_HDR_ID,DOC_HDLR_URL,HELP_DEF_URL,DOC_SEARCH_HELP_URL,POST_PRCSR,AUTHORIZER,GRP_ID,BLNKT_APPR_GRP_ID,BLNKT_APPR_PLCY,RPT_GRP_ID,RTE_VER_NBR,NOTIFY_ADDR,SEC_XML,EMAIL_XSL,APPL_ID,OBJ_ID,VER_NBR) VALUES ('3055','3001','PstnQlfctnVlMaintenanceDocumentType',0,1,1,'','Position Qualification Value Maintenance',null,null,null,'','',null,null,null,null,null,null,'2',null,null,null,null,'516e0dbd-baa3-4fb5-ad76-c3c7a9724034',1)
;
UPDATE KREW_DOC_TYP_T SET PARNT_ID='3000',DOC_TYP_NM='KpmeEffectiveDateMaintenanceDocumentType',DOC_TYP_VER_NBR=4,ACTV_IND=1,CUR_IND=1,DOC_TYP_DESC='',LBL='Kpme Effective Maintenance Document',PREV_DOC_TYP_VER_NBR='KPME0002',DOC_HDR_ID=null,DOC_HDLR_URL='${kuali.docHandler.url.prefix}/kr-krad/kpme/effectiveDateMaintenance?methodToCall=docHandler',HELP_DEF_URL='',DOC_SEARCH_HELP_URL='',POST_PRCSR=null,AUTHORIZER=null,GRP_ID=null,BLNKT_APPR_GRP_ID=null,BLNKT_APPR_PLCY=null,RPT_GRP_ID=null,RTE_VER_NBR='2',NOTIFY_ADDR=null,SEC_XML=null,EMAIL_XSL=null,APPL_ID=null,OBJ_ID='2ea12a2b-0722-4855-8137-fa2d5f4465e1',VER_NBR=27 WHERE DOC_TYP_ID = '3001'  AND VER_NBR = 26
;
INSERT INTO KREW_DOC_HDR_S VALUES (NULL)
;
INSERT INTO KREW_DOC_TYP_T (DOC_TYP_ID,PARNT_ID,DOC_TYP_NM,DOC_TYP_VER_NBR,ACTV_IND,CUR_IND,DOC_TYP_DESC,LBL,PREV_DOC_TYP_VER_NBR,DOC_HDR_ID,DOC_HDLR_URL,HELP_DEF_URL,DOC_SEARCH_HELP_URL,POST_PRCSR,AUTHORIZER,GRP_ID,BLNKT_APPR_GRP_ID,BLNKT_APPR_PLCY,RPT_GRP_ID,RTE_VER_NBR,NOTIFY_ADDR,SEC_XML,EMAIL_XSL,APPL_ID,OBJ_ID,VER_NBR) VALUES ('3056','3001','PstnQlfrTypeMaintenanceDocumentType',0,1,1,'','Position Qualifier Type Maintenance',null,null,null,'','',null,null,null,null,null,null,'2',null,null,null,null,'59976636-7d2c-4c10-a9f8-a7e29286531e',1)
;
UPDATE KREW_DOC_TYP_T SET PARNT_ID='3000',DOC_TYP_NM='KpmeEffectiveDateMaintenanceDocumentType',DOC_TYP_VER_NBR=4,ACTV_IND=1,CUR_IND=1,DOC_TYP_DESC='',LBL='Kpme Effective Maintenance Document',PREV_DOC_TYP_VER_NBR='KPME0002',DOC_HDR_ID=null,DOC_HDLR_URL='${kuali.docHandler.url.prefix}/kr-krad/kpme/effectiveDateMaintenance?methodToCall=docHandler',HELP_DEF_URL='',DOC_SEARCH_HELP_URL='',POST_PRCSR=null,AUTHORIZER=null,GRP_ID=null,BLNKT_APPR_GRP_ID=null,BLNKT_APPR_PLCY=null,RPT_GRP_ID=null,RTE_VER_NBR='2',NOTIFY_ADDR=null,SEC_XML=null,EMAIL_XSL=null,APPL_ID=null,OBJ_ID='2ea12a2b-0722-4855-8137-fa2d5f4465e1',VER_NBR=28 WHERE DOC_TYP_ID = '3001'  AND VER_NBR = 27
;
INSERT INTO KREW_DOC_HDR_S VALUES (NULL)
;
INSERT INTO KREW_DOC_TYP_T (DOC_TYP_ID,PARNT_ID,DOC_TYP_NM,DOC_TYP_VER_NBR,ACTV_IND,CUR_IND,DOC_TYP_DESC,LBL,PREV_DOC_TYP_VER_NBR,DOC_HDR_ID,DOC_HDLR_URL,HELP_DEF_URL,DOC_SEARCH_HELP_URL,POST_PRCSR,AUTHORIZER,GRP_ID,BLNKT_APPR_GRP_ID,BLNKT_APPR_PLCY,RPT_GRP_ID,RTE_VER_NBR,NOTIFY_ADDR,SEC_XML,EMAIL_XSL,APPL_ID,OBJ_ID,VER_NBR) VALUES ('3057','3001','PstnRptGrpSubCatMaintenanceDocumentType',0,1,1,'','Position Report Group Sub Category Maintenance',null,null,null,'','',null,null,null,null,null,null,'2',null,null,null,null,'c8a13890-71c4-46d2-8c59-0218056044a5',1)
;
UPDATE KREW_DOC_TYP_T SET PARNT_ID='3000',DOC_TYP_NM='KpmeEffectiveDateMaintenanceDocumentType',DOC_TYP_VER_NBR=4,ACTV_IND=1,CUR_IND=1,DOC_TYP_DESC='',LBL='Kpme Effective Maintenance Document',PREV_DOC_TYP_VER_NBR='KPME0002',DOC_HDR_ID=null,DOC_HDLR_URL='${kuali.docHandler.url.prefix}/kr-krad/kpme/effectiveDateMaintenance?methodToCall=docHandler',HELP_DEF_URL='',DOC_SEARCH_HELP_URL='',POST_PRCSR=null,AUTHORIZER=null,GRP_ID=null,BLNKT_APPR_GRP_ID=null,BLNKT_APPR_PLCY=null,RPT_GRP_ID=null,RTE_VER_NBR='2',NOTIFY_ADDR=null,SEC_XML=null,EMAIL_XSL=null,APPL_ID=null,OBJ_ID='2ea12a2b-0722-4855-8137-fa2d5f4465e1',VER_NBR=29 WHERE DOC_TYP_ID = '3001'  AND VER_NBR = 28
;
INSERT INTO KREW_DOC_HDR_S VALUES (NULL)
;
INSERT INTO KREW_DOC_TYP_T (DOC_TYP_ID,PARNT_ID,DOC_TYP_NM,DOC_TYP_VER_NBR,ACTV_IND,CUR_IND,DOC_TYP_DESC,LBL,PREV_DOC_TYP_VER_NBR,DOC_HDR_ID,DOC_HDLR_URL,HELP_DEF_URL,DOC_SEARCH_HELP_URL,POST_PRCSR,AUTHORIZER,GRP_ID,BLNKT_APPR_GRP_ID,BLNKT_APPR_PLCY,RPT_GRP_ID,RTE_VER_NBR,NOTIFY_ADDR,SEC_XML,EMAIL_XSL,APPL_ID,OBJ_ID,VER_NBR) VALUES ('3058','3000','SubAccountMaintenanceDocumentType',0,1,1,'','Sub Account',null,null,'${kuali.docHandler.url.prefix}/kr/maintenance.do?methodToCall=docHandler','','',null,null,null,null,null,null,'2',null,null,null,null,'550ebfc0-8932-427a-901a-4a0439a0fc78',1)
;
UPDATE KREW_DOC_TYP_T SET PARNT_ID='2681',DOC_TYP_NM='KpmeDocument',DOC_TYP_VER_NBR=4,ACTV_IND=1,CUR_IND=1,DOC_TYP_DESC='',LBL='Base KPME Rule Document',PREV_DOC_TYP_VER_NBR='KPME0001',DOC_HDR_ID=null,DOC_HDLR_URL='${kuali.docHandler.url.prefix}/kr/maintenance.do?methodToCall=docHandler',HELP_DEF_URL='',DOC_SEARCH_HELP_URL='',POST_PRCSR=null,AUTHORIZER=null,GRP_ID=null,BLNKT_APPR_GRP_ID=null,BLNKT_APPR_PLCY=null,RPT_GRP_ID=null,RTE_VER_NBR='2',NOTIFY_ADDR=null,SEC_XML=null,EMAIL_XSL=null,APPL_ID='KPME',OBJ_ID='6694b4a4-69f3-4692-bac0-b43ea5ff462c',VER_NBR=31 WHERE DOC_TYP_ID = '3000'  AND VER_NBR = 30
;
INSERT INTO KREW_DOC_HDR_S VALUES (NULL)
;
INSERT INTO KREW_DOC_TYP_T (DOC_TYP_ID,PARNT_ID,DOC_TYP_NM,DOC_TYP_VER_NBR,ACTV_IND,CUR_IND,DOC_TYP_DESC,LBL,PREV_DOC_TYP_VER_NBR,DOC_HDR_ID,DOC_HDLR_URL,HELP_DEF_URL,DOC_SEARCH_HELP_URL,POST_PRCSR,AUTHORIZER,GRP_ID,BLNKT_APPR_GRP_ID,BLNKT_APPR_PLCY,RPT_GRP_ID,RTE_VER_NBR,NOTIFY_ADDR,SEC_XML,EMAIL_XSL,APPL_ID,OBJ_ID,VER_NBR) VALUES ('3059','3000','SubObjectCodeMaintenanceDocumentType',0,1,1,'','Sub Object Code',null,null,'${kuali.docHandler.url.prefix}/kr/maintenance.do?methodToCall=docHandler','','',null,null,null,null,null,null,'2',null,null,null,null,'a0cbb5ec-33ff-485b-adab-bf79d244ad9c',1)
;
UPDATE KREW_DOC_TYP_T SET PARNT_ID='2681',DOC_TYP_NM='KpmeDocument',DOC_TYP_VER_NBR=4,ACTV_IND=1,CUR_IND=1,DOC_TYP_DESC='',LBL='Base KPME Rule Document',PREV_DOC_TYP_VER_NBR='KPME0001',DOC_HDR_ID=null,DOC_HDLR_URL='${kuali.docHandler.url.prefix}/kr/maintenance.do?methodToCall=docHandler',HELP_DEF_URL='',DOC_SEARCH_HELP_URL='',POST_PRCSR=null,AUTHORIZER=null,GRP_ID=null,BLNKT_APPR_GRP_ID=null,BLNKT_APPR_PLCY=null,RPT_GRP_ID=null,RTE_VER_NBR='2',NOTIFY_ADDR=null,SEC_XML=null,EMAIL_XSL=null,APPL_ID='KPME',OBJ_ID='6694b4a4-69f3-4692-bac0-b43ea5ff462c',VER_NBR=32 WHERE DOC_TYP_ID = '3000'  AND VER_NBR = 31
;
INSERT INTO KREW_DOC_HDR_S VALUES (NULL)
;
INSERT INTO KREW_DOC_TYP_T (DOC_TYP_ID,PARNT_ID,DOC_TYP_NM,DOC_TYP_VER_NBR,ACTV_IND,CUR_IND,DOC_TYP_DESC,LBL,PREV_DOC_TYP_VER_NBR,DOC_HDR_ID,DOC_HDLR_URL,HELP_DEF_URL,DOC_SEARCH_HELP_URL,POST_PRCSR,AUTHORIZER,GRP_ID,BLNKT_APPR_GRP_ID,BLNKT_APPR_PLCY,RPT_GRP_ID,RTE_VER_NBR,NOTIFY_ADDR,SEC_XML,EMAIL_XSL,APPL_ID,OBJ_ID,VER_NBR) VALUES ('3060','3001','SalaryGroupMaintenanceDocumentType',0,1,1,'','Salary Group Document',null,null,null,'','',null,null,null,null,null,null,'2',null,null,null,null,'1d5f9f41-733b-48ab-ba99-ac9f424dc4d8',1)
;
UPDATE KREW_DOC_TYP_T SET PARNT_ID='3000',DOC_TYP_NM='KpmeEffectiveDateMaintenanceDocumentType',DOC_TYP_VER_NBR=4,ACTV_IND=1,CUR_IND=1,DOC_TYP_DESC='',LBL='Kpme Effective Maintenance Document',PREV_DOC_TYP_VER_NBR='KPME0002',DOC_HDR_ID=null,DOC_HDLR_URL='${kuali.docHandler.url.prefix}/kr-krad/kpme/effectiveDateMaintenance?methodToCall=docHandler',HELP_DEF_URL='',DOC_SEARCH_HELP_URL='',POST_PRCSR=null,AUTHORIZER=null,GRP_ID=null,BLNKT_APPR_GRP_ID=null,BLNKT_APPR_PLCY=null,RPT_GRP_ID=null,RTE_VER_NBR='2',NOTIFY_ADDR=null,SEC_XML=null,EMAIL_XSL=null,APPL_ID=null,OBJ_ID='2ea12a2b-0722-4855-8137-fa2d5f4465e1',VER_NBR=30 WHERE DOC_TYP_ID = '3001'  AND VER_NBR = 29
;
INSERT INTO KREW_DOC_HDR_S VALUES (NULL)
;
INSERT INTO KREW_DOC_TYP_T (DOC_TYP_ID,PARNT_ID,DOC_TYP_NM,DOC_TYP_VER_NBR,ACTV_IND,CUR_IND,DOC_TYP_DESC,LBL,PREV_DOC_TYP_VER_NBR,DOC_HDR_ID,DOC_HDLR_URL,HELP_DEF_URL,DOC_SEARCH_HELP_URL,POST_PRCSR,AUTHORIZER,GRP_ID,BLNKT_APPR_GRP_ID,BLNKT_APPR_PLCY,RPT_GRP_ID,RTE_VER_NBR,NOTIFY_ADDR,SEC_XML,EMAIL_XSL,APPL_ID,OBJ_ID,VER_NBR) VALUES ('3061','3000','ShiftDifferentialRuleDocumentType',0,1,1,'','Shift Differential Rule Document',null,null,'${kuali.docHandler.url.prefix}/kr/maintenance.do?methodToCall=docHandler','','',null,null,null,null,null,null,'2',null,null,null,null,'ae2d35ff-e7d1-49fb-88a2-27a7008eb8f7',1)
;
UPDATE KREW_DOC_TYP_T SET PARNT_ID='2681',DOC_TYP_NM='KpmeDocument',DOC_TYP_VER_NBR=4,ACTV_IND=1,CUR_IND=1,DOC_TYP_DESC='',LBL='Base KPME Rule Document',PREV_DOC_TYP_VER_NBR='KPME0001',DOC_HDR_ID=null,DOC_HDLR_URL='${kuali.docHandler.url.prefix}/kr/maintenance.do?methodToCall=docHandler',HELP_DEF_URL='',DOC_SEARCH_HELP_URL='',POST_PRCSR=null,AUTHORIZER=null,GRP_ID=null,BLNKT_APPR_GRP_ID=null,BLNKT_APPR_PLCY=null,RPT_GRP_ID=null,RTE_VER_NBR='2',NOTIFY_ADDR=null,SEC_XML=null,EMAIL_XSL=null,APPL_ID='KPME',OBJ_ID='6694b4a4-69f3-4692-bac0-b43ea5ff462c',VER_NBR=33 WHERE DOC_TYP_ID = '3000'  AND VER_NBR = 32
;
INSERT INTO KREW_DOC_HDR_S VALUES (NULL)
;
INSERT INTO KREW_DOC_TYP_T (DOC_TYP_ID,PARNT_ID,DOC_TYP_NM,DOC_TYP_VER_NBR,ACTV_IND,CUR_IND,DOC_TYP_DESC,LBL,PREV_DOC_TYP_VER_NBR,DOC_HDR_ID,DOC_HDLR_URL,HELP_DEF_URL,DOC_SEARCH_HELP_URL,POST_PRCSR,AUTHORIZER,GRP_ID,BLNKT_APPR_GRP_ID,BLNKT_APPR_PLCY,RPT_GRP_ID,RTE_VER_NBR,NOTIFY_ADDR,SEC_XML,EMAIL_XSL,APPL_ID,OBJ_ID,VER_NBR) VALUES ('3062','3001','ShiftDifferentialRuleTypeDocumentType',0,1,1,'','Shift Differential Rule Type Document',null,null,null,'','',null,null,null,null,null,null,'2',null,null,null,null,'5bf824b6-5f57-4773-9e95-264cee7b1089',1)
;
UPDATE KREW_DOC_TYP_T SET PARNT_ID='3000',DOC_TYP_NM='KpmeEffectiveDateMaintenanceDocumentType',DOC_TYP_VER_NBR=4,ACTV_IND=1,CUR_IND=1,DOC_TYP_DESC='',LBL='Kpme Effective Maintenance Document',PREV_DOC_TYP_VER_NBR='KPME0002',DOC_HDR_ID=null,DOC_HDLR_URL='${kuali.docHandler.url.prefix}/kr-krad/kpme/effectiveDateMaintenance?methodToCall=docHandler',HELP_DEF_URL='',DOC_SEARCH_HELP_URL='',POST_PRCSR=null,AUTHORIZER=null,GRP_ID=null,BLNKT_APPR_GRP_ID=null,BLNKT_APPR_PLCY=null,RPT_GRP_ID=null,RTE_VER_NBR='2',NOTIFY_ADDR=null,SEC_XML=null,EMAIL_XSL=null,APPL_ID=null,OBJ_ID='2ea12a2b-0722-4855-8137-fa2d5f4465e1',VER_NBR=31 WHERE DOC_TYP_ID = '3001'  AND VER_NBR = 30
;
INSERT INTO KREW_DOC_HDR_S VALUES (NULL)
;
INSERT INTO KREW_DOC_TYP_T (DOC_TYP_ID,PARNT_ID,DOC_TYP_NM,DOC_TYP_VER_NBR,ACTV_IND,CUR_IND,DOC_TYP_DESC,LBL,PREV_DOC_TYP_VER_NBR,DOC_HDR_ID,DOC_HDLR_URL,HELP_DEF_URL,DOC_SEARCH_HELP_URL,POST_PRCSR,AUTHORIZER,GRP_ID,BLNKT_APPR_GRP_ID,BLNKT_APPR_PLCY,RPT_GRP_ID,RTE_VER_NBR,NOTIFY_ADDR,SEC_XML,EMAIL_XSL,APPL_ID,OBJ_ID,VER_NBR) VALUES ('3063','3000','SystemLunchRuleDocumentType',0,1,1,'','System Lunch Rule Document',null,null,'${kuali.docHandler.url.prefix}/kr/maintenance.do?methodToCall=docHandler','','',null,null,null,null,null,null,'2',null,null,null,null,'e4871d74-19cf-49b9-8d24-7a2e07d1c35d',1)
;
UPDATE KREW_DOC_TYP_T SET PARNT_ID='2681',DOC_TYP_NM='KpmeDocument',DOC_TYP_VER_NBR=4,ACTV_IND=1,CUR_IND=1,DOC_TYP_DESC='',LBL='Base KPME Rule Document',PREV_DOC_TYP_VER_NBR='KPME0001',DOC_HDR_ID=null,DOC_HDLR_URL='${kuali.docHandler.url.prefix}/kr/maintenance.do?methodToCall=docHandler',HELP_DEF_URL='',DOC_SEARCH_HELP_URL='',POST_PRCSR=null,AUTHORIZER=null,GRP_ID=null,BLNKT_APPR_GRP_ID=null,BLNKT_APPR_PLCY=null,RPT_GRP_ID=null,RTE_VER_NBR='2',NOTIFY_ADDR=null,SEC_XML=null,EMAIL_XSL=null,APPL_ID='KPME',OBJ_ID='6694b4a4-69f3-4692-bac0-b43ea5ff462c',VER_NBR=34 WHERE DOC_TYP_ID = '3000'  AND VER_NBR = 33
;
INSERT INTO KREW_DOC_HDR_S VALUES (NULL)
;
INSERT INTO KREW_DOC_TYP_T (DOC_TYP_ID,PARNT_ID,DOC_TYP_NM,DOC_TYP_VER_NBR,ACTV_IND,CUR_IND,DOC_TYP_DESC,LBL,PREV_DOC_TYP_VER_NBR,DOC_HDR_ID,DOC_HDLR_URL,HELP_DEF_URL,DOC_SEARCH_HELP_URL,POST_PRCSR,AUTHORIZER,GRP_ID,BLNKT_APPR_GRP_ID,BLNKT_APPR_PLCY,RPT_GRP_ID,RTE_VER_NBR,NOTIFY_ADDR,SEC_XML,EMAIL_XSL,APPL_ID,OBJ_ID,VER_NBR) VALUES ('3064','3000','SystemScheduledTimeOffDocumentType',0,1,1,'','System Scheduled Time Off Document',null,null,'${kuali.docHandler.url.prefix}/kr/maintenance.do?methodToCall=docHandler','','',null,null,null,null,null,null,'2',null,null,null,null,'3b6e3c66-717c-4e3c-8635-39c0cf4e1301',1)
;
UPDATE KREW_DOC_TYP_T SET PARNT_ID='2681',DOC_TYP_NM='KpmeDocument',DOC_TYP_VER_NBR=4,ACTV_IND=1,CUR_IND=1,DOC_TYP_DESC='',LBL='Base KPME Rule Document',PREV_DOC_TYP_VER_NBR='KPME0001',DOC_HDR_ID=null,DOC_HDLR_URL='${kuali.docHandler.url.prefix}/kr/maintenance.do?methodToCall=docHandler',HELP_DEF_URL='',DOC_SEARCH_HELP_URL='',POST_PRCSR=null,AUTHORIZER=null,GRP_ID=null,BLNKT_APPR_GRP_ID=null,BLNKT_APPR_PLCY=null,RPT_GRP_ID=null,RTE_VER_NBR='2',NOTIFY_ADDR=null,SEC_XML=null,EMAIL_XSL=null,APPL_ID='KPME',OBJ_ID='6694b4a4-69f3-4692-bac0-b43ea5ff462c',VER_NBR=35 WHERE DOC_TYP_ID = '3000'  AND VER_NBR = 34
;
INSERT INTO KREW_DOC_HDR_S VALUES (NULL)
;
INSERT INTO KREW_DOC_TYP_T (DOC_TYP_ID,PARNT_ID,DOC_TYP_NM,DOC_TYP_VER_NBR,ACTV_IND,CUR_IND,DOC_TYP_DESC,LBL,PREV_DOC_TYP_VER_NBR,DOC_HDR_ID,DOC_HDLR_URL,HELP_DEF_URL,DOC_SEARCH_HELP_URL,POST_PRCSR,AUTHORIZER,GRP_ID,BLNKT_APPR_GRP_ID,BLNKT_APPR_PLCY,RPT_GRP_ID,RTE_VER_NBR,NOTIFY_ADDR,SEC_XML,EMAIL_XSL,APPL_ID,OBJ_ID,VER_NBR) VALUES ('3065','3000','TimeCollectionRuleDocumentType',0,1,1,'','Time Collection Rule Document',null,null,'${kuali.docHandler.url.prefix}/kr/maintenance.do?methodToCall=docHandler','','',null,null,null,null,null,null,'2',null,null,null,null,'0a2f5e40-6703-4b21-8d7f-f9cdc82a2989',1)
;
UPDATE KREW_DOC_TYP_T SET PARNT_ID='2681',DOC_TYP_NM='KpmeDocument',DOC_TYP_VER_NBR=4,ACTV_IND=1,CUR_IND=1,DOC_TYP_DESC='',LBL='Base KPME Rule Document',PREV_DOC_TYP_VER_NBR='KPME0001',DOC_HDR_ID=null,DOC_HDLR_URL='${kuali.docHandler.url.prefix}/kr/maintenance.do?methodToCall=docHandler',HELP_DEF_URL='',DOC_SEARCH_HELP_URL='',POST_PRCSR=null,AUTHORIZER=null,GRP_ID=null,BLNKT_APPR_GRP_ID=null,BLNKT_APPR_PLCY=null,RPT_GRP_ID=null,RTE_VER_NBR='2',NOTIFY_ADDR=null,SEC_XML=null,EMAIL_XSL=null,APPL_ID='KPME',OBJ_ID='6694b4a4-69f3-4692-bac0-b43ea5ff462c',VER_NBR=36 WHERE DOC_TYP_ID = '3000'  AND VER_NBR = 35
;
INSERT INTO KREW_DOC_HDR_S VALUES (NULL)
;
INSERT INTO KREW_DOC_TYP_T (DOC_TYP_ID,PARNT_ID,DOC_TYP_NM,DOC_TYP_VER_NBR,ACTV_IND,CUR_IND,DOC_TYP_DESC,LBL,PREV_DOC_TYP_VER_NBR,DOC_HDR_ID,DOC_HDLR_URL,HELP_DEF_URL,DOC_SEARCH_HELP_URL,POST_PRCSR,AUTHORIZER,GRP_ID,BLNKT_APPR_GRP_ID,BLNKT_APPR_PLCY,RPT_GRP_ID,RTE_VER_NBR,NOTIFY_ADDR,SEC_XML,EMAIL_XSL,APPL_ID,OBJ_ID,VER_NBR) VALUES ('3066','3000','TimesheetDocument',0,1,1,'Timesheet Document','Timesheet Document',null,null,'${kuali.docHandler.url.prefix}/Timesheet.do?methodToCall=docHandler','','','org.kuali.kpme.tklm.time.workflow.postprocessor.TkPostProcessor',null,'KPME0001','KPME0001',null,null,'1',null,null,null,'KPME','67225f40-cd8d-48a0-ab6f-b76bc34e6ef5',1)
;
INSERT INTO KREW_DOC_TYP_PLCY_RELN_T (DOC_TYP_ID,DOC_PLCY_NM,PLCY_NM,PLCY_VAL,OBJ_ID,VER_NBR) VALUES ('3066','INITIATOR_MUST_CANCEL',0,null,'b6e665d6-2439-4d3c-8f67-5de80a2f66eb',1)
;
INSERT INTO KREW_DOC_TYP_PLCY_RELN_T (DOC_TYP_ID,DOC_PLCY_NM,PLCY_NM,PLCY_VAL,OBJ_ID,VER_NBR) VALUES ('3066','INITIATOR_MUST_ROUTE',0,null,'1269b063-b3f2-45d4-9c99-335c33f2f9ab',1)
;
INSERT INTO KREW_DOC_TYP_ATTR_S VALUES (NULL)
;
INSERT INTO KREW_DOC_TYP_ATTR_T (DOC_TYP_ATTRIB_ID,DOC_TYP_ID,RULE_ATTR_ID,ORD_INDX) VALUES ('2019','3066','1666',1)
;
INSERT INTO KREW_DOC_TYP_ATTR_S VALUES (NULL)
;
INSERT INTO KREW_DOC_TYP_ATTR_T (DOC_TYP_ATTRIB_ID,DOC_TYP_ID,RULE_ATTR_ID,ORD_INDX) VALUES ('2020','3066','1670',2)
;
INSERT INTO KREW_DOC_TYP_ATTR_S VALUES (NULL)
;
INSERT INTO KREW_DOC_TYP_ATTR_T (DOC_TYP_ATTRIB_ID,DOC_TYP_ID,RULE_ATTR_ID,ORD_INDX) VALUES ('2021','3066','1671',3)
;
INSERT INTO KREW_DOC_TYP_ATTR_S VALUES (NULL)
;
INSERT INTO KREW_DOC_TYP_ATTR_T (DOC_TYP_ATTRIB_ID,DOC_TYP_ID,RULE_ATTR_ID,ORD_INDX) VALUES ('2022','3066','1672',4)
;
INSERT INTO KREW_RTE_NODE_S VALUES (NULL)
;
INSERT INTO KREW_RTE_NODE_T (RTE_NODE_ID,DOC_TYP_ID,NM,TYP,RTE_MTHD_NM,FNL_APRVR_IND,MNDTRY_RTE_IND,GRP_ID,RTE_MTHD_CD,ACTVN_TYP,BRCH_PROTO_ID,NEXT_DOC_STAT,VER_NBR) VALUES ('2940','3066','Initiated','org.kuali.rice.kew.engine.node.InitialNode',null,0,0,'KPME0001',null,'P',null,null,1)
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_S VALUES (NULL)
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2527','2940','contentFragment','<start name="Initiated">\n<activationType>P</activationType>\n<mandatoryRoute>false</mandatoryRoute>\n<finalApproval>false</finalApproval>\n</start>\n')
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_S VALUES (NULL)
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2528','2940','activationType','P')
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_S VALUES (NULL)
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2529','2940','mandatoryRoute','false')
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_S VALUES (NULL)
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2530','2940','finalApproval','false')
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_S VALUES (NULL)
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2531','2940','ruleSelector','Template')
;
INSERT INTO KREW_RTE_NODE_S VALUES (NULL)
;
INSERT INTO KREW_RTE_NODE_T (RTE_NODE_ID,DOC_TYP_ID,NM,TYP,RTE_MTHD_NM,FNL_APRVR_IND,MNDTRY_RTE_IND,GRP_ID,RTE_MTHD_CD,ACTVN_TYP,BRCH_PROTO_ID,NEXT_DOC_STAT,VER_NBR) VALUES ('2941','3066','PeopleFlows','org.kuali.rice.kew.engine.node.RequestsNode',null,0,0,'KPME0001','RE','R',null,null,1)
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_S VALUES (NULL)
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2532','2941','contentFragment','<requests name="PeopleFlows">\n<activationType>R</activationType>\n<rulesEngine executorClass="org.kuali.kpme.tklm.time.timesheet.rules.TimesheetRulesEngineExecutor"/>\n</requests>\n')
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_S VALUES (NULL)
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2533','2941','activationType','R')
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_S VALUES (NULL)
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2534','2941','rulesEngine','')
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_S VALUES (NULL)
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2535','2941','ruleSelector','Template')
;
INSERT INTO KREW_RTE_NODE_LNK_T (TO_RTE_NODE_ID,FROM_RTE_NODE_ID) VALUES ('2941','2940')
;
INSERT INTO KREW_RTE_NODE_S VALUES (NULL)
;
INSERT INTO KREW_DOC_TYP_PROC_T (DOC_TYP_PROC_ID,DOC_TYP_ID,INIT_RTE_NODE_ID,NM,INIT_IND,VER_NBR) VALUES ('2942','3066','2940','PRIMARY',1,1)
;
UPDATE KREW_DOC_TYP_T SET PARNT_ID='2681',DOC_TYP_NM='KpmeDocument',DOC_TYP_VER_NBR=4,ACTV_IND=1,CUR_IND=1,DOC_TYP_DESC='',LBL='Base KPME Rule Document',PREV_DOC_TYP_VER_NBR='KPME0001',DOC_HDR_ID=null,DOC_HDLR_URL='${kuali.docHandler.url.prefix}/kr/maintenance.do?methodToCall=docHandler',HELP_DEF_URL='',DOC_SEARCH_HELP_URL='',POST_PRCSR=null,AUTHORIZER=null,GRP_ID=null,BLNKT_APPR_GRP_ID=null,BLNKT_APPR_PLCY=null,RPT_GRP_ID=null,RTE_VER_NBR='2',NOTIFY_ADDR=null,SEC_XML=null,EMAIL_XSL=null,APPL_ID='KPME',OBJ_ID='6694b4a4-69f3-4692-bac0-b43ea5ff462c',VER_NBR=37 WHERE DOC_TYP_ID = '3000'  AND VER_NBR = 36
;
INSERT INTO KREW_DOC_HDR_S VALUES (NULL)
;
INSERT INTO KREW_DOC_TYP_T (DOC_TYP_ID,PARNT_ID,DOC_TYP_NM,DOC_TYP_VER_NBR,ACTV_IND,CUR_IND,DOC_TYP_DESC,LBL,PREV_DOC_TYP_VER_NBR,DOC_HDR_ID,DOC_HDLR_URL,HELP_DEF_URL,DOC_SEARCH_HELP_URL,POST_PRCSR,AUTHORIZER,GRP_ID,BLNKT_APPR_GRP_ID,BLNKT_APPR_PLCY,RPT_GRP_ID,RTE_VER_NBR,NOTIFY_ADDR,SEC_XML,EMAIL_XSL,APPL_ID,OBJ_ID,VER_NBR) VALUES ('3067','3000','TimesheetDocumentHeaderDocumentType',0,1,1,'','Timesheet Document Header Document',null,null,'${kuali.docHandler.url.prefix}/kr/maintenance.do?methodToCall=docHandler','','',null,null,null,null,null,null,'2',null,null,null,null,'42293361-f868-40b4-b826-275e49a72551',1)
;
UPDATE KREW_DOC_TYP_T SET PARNT_ID='2681',DOC_TYP_NM='KpmeDocument',DOC_TYP_VER_NBR=4,ACTV_IND=1,CUR_IND=1,DOC_TYP_DESC='',LBL='Base KPME Rule Document',PREV_DOC_TYP_VER_NBR='KPME0001',DOC_HDR_ID=null,DOC_HDLR_URL='${kuali.docHandler.url.prefix}/kr/maintenance.do?methodToCall=docHandler',HELP_DEF_URL='',DOC_SEARCH_HELP_URL='',POST_PRCSR=null,AUTHORIZER=null,GRP_ID=null,BLNKT_APPR_GRP_ID=null,BLNKT_APPR_PLCY=null,RPT_GRP_ID=null,RTE_VER_NBR='2',NOTIFY_ADDR=null,SEC_XML=null,EMAIL_XSL=null,APPL_ID='KPME',OBJ_ID='6694b4a4-69f3-4692-bac0-b43ea5ff462c',VER_NBR=38 WHERE DOC_TYP_ID = '3000'  AND VER_NBR = 37
;
INSERT INTO KREW_DOC_HDR_S VALUES (NULL)
;
INSERT INTO KREW_DOC_TYP_T (DOC_TYP_ID,PARNT_ID,DOC_TYP_NM,DOC_TYP_VER_NBR,ACTV_IND,CUR_IND,DOC_TYP_DESC,LBL,PREV_DOC_TYP_VER_NBR,DOC_HDR_ID,DOC_HDLR_URL,HELP_DEF_URL,DOC_SEARCH_HELP_URL,POST_PRCSR,AUTHORIZER,GRP_ID,BLNKT_APPR_GRP_ID,BLNKT_APPR_PLCY,RPT_GRP_ID,RTE_VER_NBR,NOTIFY_ADDR,SEC_XML,EMAIL_XSL,APPL_ID,OBJ_ID,VER_NBR) VALUES ('3068','3000','TimesheetDocumentHeaderMaintenanceDocumentType',0,1,1,'','Timesheet Document Header Maintenance Document',null,null,'${kuali.docHandler.url.prefix}/kr/maintenance.do?methodToCall=docHandler','','',null,null,null,null,null,null,'2',null,null,null,null,'4e7ad8c2-ebdf-4b50-bc28-85f527a3b937',1)
;
UPDATE KREW_DOC_TYP_T SET PARNT_ID='2681',DOC_TYP_NM='KpmeDocument',DOC_TYP_VER_NBR=4,ACTV_IND=1,CUR_IND=1,DOC_TYP_DESC='',LBL='Base KPME Rule Document',PREV_DOC_TYP_VER_NBR='KPME0001',DOC_HDR_ID=null,DOC_HDLR_URL='${kuali.docHandler.url.prefix}/kr/maintenance.do?methodToCall=docHandler',HELP_DEF_URL='',DOC_SEARCH_HELP_URL='',POST_PRCSR=null,AUTHORIZER=null,GRP_ID=null,BLNKT_APPR_GRP_ID=null,BLNKT_APPR_PLCY=null,RPT_GRP_ID=null,RTE_VER_NBR='2',NOTIFY_ADDR=null,SEC_XML=null,EMAIL_XSL=null,APPL_ID='KPME',OBJ_ID='6694b4a4-69f3-4692-bac0-b43ea5ff462c',VER_NBR=39 WHERE DOC_TYP_ID = '3000'  AND VER_NBR = 38
;
INSERT INTO KREW_DOC_HDR_S VALUES (NULL)
;
INSERT INTO KREW_DOC_TYP_T (DOC_TYP_ID,PARNT_ID,DOC_TYP_NM,DOC_TYP_VER_NBR,ACTV_IND,CUR_IND,DOC_TYP_DESC,LBL,PREV_DOC_TYP_VER_NBR,DOC_HDR_ID,DOC_HDLR_URL,HELP_DEF_URL,DOC_SEARCH_HELP_URL,POST_PRCSR,AUTHORIZER,GRP_ID,BLNKT_APPR_GRP_ID,BLNKT_APPR_PLCY,RPT_GRP_ID,RTE_VER_NBR,NOTIFY_ADDR,SEC_XML,EMAIL_XSL,APPL_ID,OBJ_ID,VER_NBR) VALUES ('3069','3000','WeeklyOvertimeRuleDocumentType',0,1,1,'','Weekly Overtime Rule Document',null,null,'${kuali.docHandler.url.prefix}/kr/maintenance.do?methodToCall=docHandler','','',null,null,null,null,null,null,'2',null,null,null,null,'0aa4c8a1-2b66-4f59-9323-bf743f2688e7',1)
;
UPDATE KREW_DOC_TYP_T SET PARNT_ID='2681',DOC_TYP_NM='KpmeDocument',DOC_TYP_VER_NBR=4,ACTV_IND=1,CUR_IND=1,DOC_TYP_DESC='',LBL='Base KPME Rule Document',PREV_DOC_TYP_VER_NBR='KPME0001',DOC_HDR_ID=null,DOC_HDLR_URL='${kuali.docHandler.url.prefix}/kr/maintenance.do?methodToCall=docHandler',HELP_DEF_URL='',DOC_SEARCH_HELP_URL='',POST_PRCSR=null,AUTHORIZER=null,GRP_ID=null,BLNKT_APPR_GRP_ID=null,BLNKT_APPR_PLCY=null,RPT_GRP_ID=null,RTE_VER_NBR='2',NOTIFY_ADDR=null,SEC_XML=null,EMAIL_XSL=null,APPL_ID='KPME',OBJ_ID='6694b4a4-69f3-4692-bac0-b43ea5ff462c',VER_NBR=40 WHERE DOC_TYP_ID = '3000'  AND VER_NBR = 39
;
INSERT INTO KREW_DOC_HDR_S VALUES (NULL)
;
INSERT INTO KREW_DOC_TYP_T (DOC_TYP_ID,PARNT_ID,DOC_TYP_NM,DOC_TYP_VER_NBR,ACTV_IND,CUR_IND,DOC_TYP_DESC,LBL,PREV_DOC_TYP_VER_NBR,DOC_HDR_ID,DOC_HDLR_URL,HELP_DEF_URL,DOC_SEARCH_HELP_URL,POST_PRCSR,AUTHORIZER,GRP_ID,BLNKT_APPR_GRP_ID,BLNKT_APPR_PLCY,RPT_GRP_ID,RTE_VER_NBR,NOTIFY_ADDR,SEC_XML,EMAIL_XSL,APPL_ID,OBJ_ID,VER_NBR) VALUES ('3070','3000','WeeklyOvertimeRuleGroupDocumentType',0,1,1,'','Weekly Overtime Rule Group Document',null,null,'${kuali.docHandler.url.prefix}/kr/maintenance.do?methodToCall=docHandler','','',null,null,null,null,null,null,'2',null,null,null,null,'5f1054e0-9e29-42dc-accd-23ab454a338c',1)
;
UPDATE KREW_DOC_TYP_T SET PARNT_ID='2681',DOC_TYP_NM='KpmeDocument',DOC_TYP_VER_NBR=4,ACTV_IND=1,CUR_IND=1,DOC_TYP_DESC='',LBL='Base KPME Rule Document',PREV_DOC_TYP_VER_NBR='KPME0001',DOC_HDR_ID=null,DOC_HDLR_URL='${kuali.docHandler.url.prefix}/kr/maintenance.do?methodToCall=docHandler',HELP_DEF_URL='',DOC_SEARCH_HELP_URL='',POST_PRCSR=null,AUTHORIZER=null,GRP_ID=null,BLNKT_APPR_GRP_ID=null,BLNKT_APPR_PLCY=null,RPT_GRP_ID=null,RTE_VER_NBR='2',NOTIFY_ADDR=null,SEC_XML=null,EMAIL_XSL=null,APPL_ID='KPME',OBJ_ID='6694b4a4-69f3-4692-bac0-b43ea5ff462c',VER_NBR=41 WHERE DOC_TYP_ID = '3000'  AND VER_NBR = 40
;
INSERT INTO KREW_DOC_HDR_S VALUES (NULL)
;
INSERT INTO KREW_DOC_TYP_T (DOC_TYP_ID,PARNT_ID,DOC_TYP_NM,DOC_TYP_VER_NBR,ACTV_IND,CUR_IND,DOC_TYP_DESC,LBL,PREV_DOC_TYP_VER_NBR,DOC_HDR_ID,DOC_HDLR_URL,HELP_DEF_URL,DOC_SEARCH_HELP_URL,POST_PRCSR,AUTHORIZER,GRP_ID,BLNKT_APPR_GRP_ID,BLNKT_APPR_PLCY,RPT_GRP_ID,RTE_VER_NBR,NOTIFY_ADDR,SEC_XML,EMAIL_XSL,APPL_ID,OBJ_ID,VER_NBR) VALUES ('3071','3001','WorkAreaMaintenanceDocumentType',0,1,1,'','Work Area Document',null,null,null,'','',null,null,null,null,null,null,'2',null,null,null,null,'e455f9ec-4bd1-48c8-bbf5-a8b6976aa0b0',1)
;
UPDATE KREW_DOC_TYP_T SET PARNT_ID='3000',DOC_TYP_NM='KpmeEffectiveDateMaintenanceDocumentType',DOC_TYP_VER_NBR=4,ACTV_IND=1,CUR_IND=1,DOC_TYP_DESC='',LBL='Kpme Effective Maintenance Document',PREV_DOC_TYP_VER_NBR='KPME0002',DOC_HDR_ID=null,DOC_HDLR_URL='${kuali.docHandler.url.prefix}/kr-krad/kpme/effectiveDateMaintenance?methodToCall=docHandler',HELP_DEF_URL='',DOC_SEARCH_HELP_URL='',POST_PRCSR=null,AUTHORIZER=null,GRP_ID=null,BLNKT_APPR_GRP_ID=null,BLNKT_APPR_PLCY=null,RPT_GRP_ID=null,RTE_VER_NBR='2',NOTIFY_ADDR=null,SEC_XML=null,EMAIL_XSL=null,APPL_ID=null,OBJ_ID='2ea12a2b-0722-4855-8137-fa2d5f4465e1',VER_NBR=32 WHERE DOC_TYP_ID = '3001'  AND VER_NBR = 31
;
INSERT INTO KREW_DOC_HDR_S VALUES (NULL)
;
INSERT INTO KREW_DOC_TYP_T (DOC_TYP_ID,PARNT_ID,DOC_TYP_NM,DOC_TYP_VER_NBR,ACTV_IND,CUR_IND,DOC_TYP_DESC,LBL,PREV_DOC_TYP_VER_NBR,DOC_HDR_ID,DOC_HDLR_URL,HELP_DEF_URL,DOC_SEARCH_HELP_URL,POST_PRCSR,AUTHORIZER,GRP_ID,BLNKT_APPR_GRP_ID,BLNKT_APPR_PLCY,RPT_GRP_ID,RTE_VER_NBR,NOTIFY_ADDR,SEC_XML,EMAIL_XSL,APPL_ID,OBJ_ID,VER_NBR) VALUES ('3072','2681','ComponentMaintenanceDocument',0,1,1,'Create/edit a component','Component Maintenance Document',null,null,'${kr.url}/maintenance.do?methodToCall=docHandler','default.htm?turl=WordDocuments%2Fparametertype.htm','','org.kuali.rice.krad.workflow.postprocessor.KualiPostProcessor',null,'1','1',null,null,'2',null,null,null,null,'e1aedd55-3527-46c1-89d2-bf32f0bfbcf1',1)
;
INSERT INTO KREW_RTE_NODE_S VALUES (NULL)
;
INSERT INTO KREW_RTE_NODE_T (RTE_NODE_ID,DOC_TYP_ID,NM,TYP,RTE_MTHD_NM,FNL_APRVR_IND,MNDTRY_RTE_IND,GRP_ID,RTE_MTHD_CD,ACTVN_TYP,BRCH_PROTO_ID,NEXT_DOC_STAT,VER_NBR) VALUES ('2943','3072','Initiated','org.kuali.rice.kew.engine.node.InitialNode',null,0,0,'1',null,'P',null,null,1)
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_S VALUES (NULL)
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2536','2943','contentFragment','<start name="Initiated">\n<activationType>P</activationType>\n<mandatoryRoute>false</mandatoryRoute>\n<finalApproval>false</finalApproval>\n</start>\n')
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_S VALUES (NULL)
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2537','2943','activationType','P')
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_S VALUES (NULL)
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2538','2943','mandatoryRoute','false')
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_S VALUES (NULL)
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2539','2943','finalApproval','false')
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_S VALUES (NULL)
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2540','2943','ruleSelector','Template')
;
INSERT INTO KREW_RTE_NODE_S VALUES (NULL)
;
INSERT INTO KREW_DOC_TYP_PROC_T (DOC_TYP_PROC_ID,DOC_TYP_ID,INIT_RTE_NODE_ID,NM,INIT_IND,VER_NBR) VALUES ('2944','3072','2943','PRIMARY',1,1)
;
UPDATE KREW_DOC_TYP_T SET PARNT_ID='2680',DOC_TYP_NM='RiceDocument',DOC_TYP_VER_NBR=0,ACTV_IND=1,CUR_IND=1,DOC_TYP_DESC='Parent Document Type for all Rice Documents',LBL='Rice Document',PREV_DOC_TYP_VER_NBR=null,DOC_HDR_ID=null,DOC_HDLR_URL=null,HELP_DEF_URL=null,DOC_SEARCH_HELP_URL=null,POST_PRCSR=null,AUTHORIZER=null,GRP_ID=null,BLNKT_APPR_GRP_ID=null,BLNKT_APPR_PLCY=null,RPT_GRP_ID=null,RTE_VER_NBR='2',NOTIFY_ADDR=null,SEC_XML=null,EMAIL_XSL=null,APPL_ID=null,OBJ_ID='6166CBA1BA82644DE0404F8189D86C09',VER_NBR=2 WHERE DOC_TYP_ID = '2681'  AND VER_NBR = 1
;
INSERT INTO KREW_DOC_HDR_S VALUES (NULL)
;
INSERT INTO KREW_DOC_TYP_T (DOC_TYP_ID,PARNT_ID,DOC_TYP_NM,DOC_TYP_VER_NBR,ACTV_IND,CUR_IND,DOC_TYP_DESC,LBL,PREV_DOC_TYP_VER_NBR,DOC_HDR_ID,DOC_HDLR_URL,HELP_DEF_URL,DOC_SEARCH_HELP_URL,POST_PRCSR,AUTHORIZER,GRP_ID,BLNKT_APPR_GRP_ID,BLNKT_APPR_PLCY,RPT_GRP_ID,RTE_VER_NBR,NOTIFY_ADDR,SEC_XML,EMAIL_XSL,APPL_ID,OBJ_ID,VER_NBR) VALUES ('3073','2681','AgendaEditorMaintenanceDocument',0,1,1,'Create a KRMS Agenda','KRMS Agenda Editor Maintenance Document',null,null,'${kr.krad.url}/krmsAgendaEditor?methodToCall=docHandler',null,null,'org.kuali.rice.krad.workflow.postprocessor.KualiPostProcessor',null,'1','1',null,null,'2',null,null,null,null,'76bf0036-a78b-477a-8797-9075f07124d1',1)
;
UPDATE KREW_DOC_TYP_T SET PARNT_ID='2680',DOC_TYP_NM='RiceDocument',DOC_TYP_VER_NBR=0,ACTV_IND=1,CUR_IND=1,DOC_TYP_DESC='Parent Document Type for all Rice Documents',LBL='Rice Document',PREV_DOC_TYP_VER_NBR=null,DOC_HDR_ID=null,DOC_HDLR_URL=null,HELP_DEF_URL=null,DOC_SEARCH_HELP_URL=null,POST_PRCSR=null,AUTHORIZER=null,GRP_ID=null,BLNKT_APPR_GRP_ID=null,BLNKT_APPR_PLCY=null,RPT_GRP_ID=null,RTE_VER_NBR='2',NOTIFY_ADDR=null,SEC_XML=null,EMAIL_XSL=null,APPL_ID=null,OBJ_ID='6166CBA1BA82644DE0404F8189D86C09',VER_NBR=3 WHERE DOC_TYP_ID = '2681'  AND VER_NBR = 2
;
INSERT INTO KREW_DOC_HDR_S VALUES (NULL)
;
INSERT INTO KREW_DOC_TYP_T (DOC_TYP_ID,PARNT_ID,DOC_TYP_NM,DOC_TYP_VER_NBR,ACTV_IND,CUR_IND,DOC_TYP_DESC,LBL,PREV_DOC_TYP_VER_NBR,DOC_HDR_ID,DOC_HDLR_URL,HELP_DEF_URL,DOC_SEARCH_HELP_URL,POST_PRCSR,AUTHORIZER,GRP_ID,BLNKT_APPR_GRP_ID,BLNKT_APPR_PLCY,RPT_GRP_ID,RTE_VER_NBR,NOTIFY_ADDR,SEC_XML,EMAIL_XSL,APPL_ID,OBJ_ID,VER_NBR) VALUES ('3074','2681','AgendaMaintenanceDocument',0,1,1,'Create a New Agenda','Agenda Maintenance Document',null,null,'${kr.krad.url}/maintenance?methodToCall=docHandler','','','org.kuali.rice.krad.workflow.postprocessor.KualiPostProcessor',null,'1','1',null,null,'2',null,null,null,null,'c40dd2cb-b9fe-40f0-8662-ee6d6fc68468',1)
;
UPDATE KREW_DOC_TYP_T SET PARNT_ID='2680',DOC_TYP_NM='RiceDocument',DOC_TYP_VER_NBR=0,ACTV_IND=1,CUR_IND=1,DOC_TYP_DESC='Parent Document Type for all Rice Documents',LBL='Rice Document',PREV_DOC_TYP_VER_NBR=null,DOC_HDR_ID=null,DOC_HDLR_URL=null,HELP_DEF_URL=null,DOC_SEARCH_HELP_URL=null,POST_PRCSR=null,AUTHORIZER=null,GRP_ID=null,BLNKT_APPR_GRP_ID=null,BLNKT_APPR_PLCY=null,RPT_GRP_ID=null,RTE_VER_NBR='2',NOTIFY_ADDR=null,SEC_XML=null,EMAIL_XSL=null,APPL_ID=null,OBJ_ID='6166CBA1BA82644DE0404F8189D86C09',VER_NBR=4 WHERE DOC_TYP_ID = '2681'  AND VER_NBR = 3
;
INSERT INTO KREW_DOC_HDR_S VALUES (NULL)
;
INSERT INTO KREW_DOC_TYP_T (DOC_TYP_ID,PARNT_ID,DOC_TYP_NM,DOC_TYP_VER_NBR,ACTV_IND,CUR_IND,DOC_TYP_DESC,LBL,PREV_DOC_TYP_VER_NBR,DOC_HDR_ID,DOC_HDLR_URL,HELP_DEF_URL,DOC_SEARCH_HELP_URL,POST_PRCSR,AUTHORIZER,GRP_ID,BLNKT_APPR_GRP_ID,BLNKT_APPR_PLCY,RPT_GRP_ID,RTE_VER_NBR,NOTIFY_ADDR,SEC_XML,EMAIL_XSL,APPL_ID,OBJ_ID,VER_NBR) VALUES ('3075','2681','ContextMaintenanceDocument',0,1,1,'Create a KRMS Context','KRMS Context Maintenance Document',null,null,'${kr.krad.url}/maintenance?methodToCall=docHandler','','','org.kuali.rice.krad.workflow.postprocessor.KualiPostProcessor',null,'1','1',null,null,'2',null,null,null,null,'2de98c8d-8f45-4a6e-ae23-11c2e481baeb',1)
;
UPDATE KREW_DOC_TYP_T SET PARNT_ID='2680',DOC_TYP_NM='RiceDocument',DOC_TYP_VER_NBR=0,ACTV_IND=1,CUR_IND=1,DOC_TYP_DESC='Parent Document Type for all Rice Documents',LBL='Rice Document',PREV_DOC_TYP_VER_NBR=null,DOC_HDR_ID=null,DOC_HDLR_URL=null,HELP_DEF_URL=null,DOC_SEARCH_HELP_URL=null,POST_PRCSR=null,AUTHORIZER=null,GRP_ID=null,BLNKT_APPR_GRP_ID=null,BLNKT_APPR_PLCY=null,RPT_GRP_ID=null,RTE_VER_NBR='2',NOTIFY_ADDR=null,SEC_XML=null,EMAIL_XSL=null,APPL_ID=null,OBJ_ID='6166CBA1BA82644DE0404F8189D86C09',VER_NBR=5 WHERE DOC_TYP_ID = '2681'  AND VER_NBR = 4
;
INSERT INTO KREW_DOC_HDR_S VALUES (NULL)
;
INSERT INTO KREW_DOC_TYP_T (DOC_TYP_ID,PARNT_ID,DOC_TYP_NM,DOC_TYP_VER_NBR,ACTV_IND,CUR_IND,DOC_TYP_DESC,LBL,PREV_DOC_TYP_VER_NBR,DOC_HDR_ID,DOC_HDLR_URL,HELP_DEF_URL,DOC_SEARCH_HELP_URL,POST_PRCSR,AUTHORIZER,GRP_ID,BLNKT_APPR_GRP_ID,BLNKT_APPR_PLCY,RPT_GRP_ID,RTE_VER_NBR,NOTIFY_ADDR,SEC_XML,EMAIL_XSL,APPL_ID,OBJ_ID,VER_NBR) VALUES ('3076','2681','PeopleFlowMaintenanceDocument',0,1,1,'','PeopleFlowMaintenanceDocument',null,null,'${kr.krad.url}/peopleFlowMaintenance?methodToCall=docHandler','','',null,null,null,null,null,null,'2',null,null,null,null,'492b4c4b-009f-4166-bd7e-4b48a8abf9fe',1)
;
UPDATE KREW_DOC_TYP_T SET PARNT_ID='2680',DOC_TYP_NM='RiceDocument',DOC_TYP_VER_NBR=0,ACTV_IND=1,CUR_IND=1,DOC_TYP_DESC='Parent Document Type for all Rice Documents',LBL='Rice Document',PREV_DOC_TYP_VER_NBR=null,DOC_HDR_ID=null,DOC_HDLR_URL=null,HELP_DEF_URL=null,DOC_SEARCH_HELP_URL=null,POST_PRCSR=null,AUTHORIZER=null,GRP_ID=null,BLNKT_APPR_GRP_ID=null,BLNKT_APPR_PLCY=null,RPT_GRP_ID=null,RTE_VER_NBR='2',NOTIFY_ADDR=null,SEC_XML=null,EMAIL_XSL=null,APPL_ID=null,OBJ_ID='6166CBA1BA82644DE0404F8189D86C09',VER_NBR=6 WHERE DOC_TYP_ID = '2681'  AND VER_NBR = 5
;
INSERT INTO KREW_DOC_HDR_S VALUES (NULL)
;
INSERT INTO KREW_DOC_TYP_T (DOC_TYP_ID,PARNT_ID,DOC_TYP_NM,DOC_TYP_VER_NBR,ACTV_IND,CUR_IND,DOC_TYP_DESC,LBL,PREV_DOC_TYP_VER_NBR,DOC_HDR_ID,DOC_HDLR_URL,HELP_DEF_URL,DOC_SEARCH_HELP_URL,POST_PRCSR,AUTHORIZER,GRP_ID,BLNKT_APPR_GRP_ID,BLNKT_APPR_PLCY,RPT_GRP_ID,RTE_VER_NBR,NOTIFY_ADDR,SEC_XML,EMAIL_XSL,APPL_ID,OBJ_ID,VER_NBR) VALUES ('3077','2681','RuleMaintenanceDocument',0,1,1,'Create a New Rule Maintenance Document','Rule Maintenance Document',null,null,'${kr.krad.url}/maintenance?methodToCall=docHandler','','','org.kuali.rice.krad.workflow.postprocessor.KualiPostProcessor',null,'1','1',null,null,'2',null,null,null,null,'313d7dbf-b48a-44c8-a13d-bba013328378',1)
;
INSERT INTO KREW_RTE_NODE_S VALUES (NULL)
;
INSERT INTO KREW_RTE_NODE_T (RTE_NODE_ID,DOC_TYP_ID,NM,TYP,RTE_MTHD_NM,FNL_APRVR_IND,MNDTRY_RTE_IND,GRP_ID,RTE_MTHD_CD,ACTVN_TYP,BRCH_PROTO_ID,NEXT_DOC_STAT,VER_NBR) VALUES ('2945','3077','Initiated','org.kuali.rice.kew.engine.node.InitialNode',null,0,0,'1',null,'P',null,null,1)
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_S VALUES (NULL)
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2541','2945','contentFragment','<start name="Initiated">\n<activationType>P</activationType>\n<mandatoryRoute>false</mandatoryRoute>\n<finalApproval>false</finalApproval>\n</start>\n')
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_S VALUES (NULL)
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2542','2945','activationType','P')
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_S VALUES (NULL)
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2543','2945','mandatoryRoute','false')
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_S VALUES (NULL)
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2544','2945','finalApproval','false')
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_S VALUES (NULL)
;
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2545','2945','ruleSelector','Template')
;
INSERT INTO KREW_RTE_NODE_S VALUES (NULL)
;
INSERT INTO KREW_DOC_TYP_PROC_T (DOC_TYP_PROC_ID,DOC_TYP_ID,INIT_RTE_NODE_ID,NM,INIT_IND,VER_NBR) VALUES ('2946','3077','2945','PRIMARY',1,1)
;
UPDATE KREW_DOC_TYP_T SET PARNT_ID='2680',DOC_TYP_NM='RiceDocument',DOC_TYP_VER_NBR=0,ACTV_IND=1,CUR_IND=1,DOC_TYP_DESC='Parent Document Type for all Rice Documents',LBL='Rice Document',PREV_DOC_TYP_VER_NBR=null,DOC_HDR_ID=null,DOC_HDLR_URL=null,HELP_DEF_URL=null,DOC_SEARCH_HELP_URL=null,POST_PRCSR=null,AUTHORIZER=null,GRP_ID=null,BLNKT_APPR_GRP_ID=null,BLNKT_APPR_PLCY=null,RPT_GRP_ID=null,RTE_VER_NBR='2',NOTIFY_ADDR=null,SEC_XML=null,EMAIL_XSL=null,APPL_ID=null,OBJ_ID='6166CBA1BA82644DE0404F8189D86C09',VER_NBR=7 WHERE DOC_TYP_ID = '2681'  AND VER_NBR = 6
;
INSERT INTO KREW_DOC_HDR_S VALUES (NULL)
;
INSERT INTO KREW_DOC_TYP_T (DOC_TYP_ID,PARNT_ID,DOC_TYP_NM,DOC_TYP_VER_NBR,ACTV_IND,CUR_IND,DOC_TYP_DESC,LBL,PREV_DOC_TYP_VER_NBR,DOC_HDR_ID,DOC_HDLR_URL,HELP_DEF_URL,DOC_SEARCH_HELP_URL,POST_PRCSR,AUTHORIZER,GRP_ID,BLNKT_APPR_GRP_ID,BLNKT_APPR_PLCY,RPT_GRP_ID,RTE_VER_NBR,NOTIFY_ADDR,SEC_XML,EMAIL_XSL,APPL_ID,OBJ_ID,VER_NBR) VALUES ('3078','2681','TermMaintenanceDocument',0,1,1,'Create a KRMS Term','KRMS Term Maintenance Document',null,null,'${kr.krad.url}/maintenance?methodToCall=docHandler','','','org.kuali.rice.krad.workflow.postprocessor.KualiPostProcessor',null,'1','1',null,null,'2',null,null,null,null,'c34e1bef-9ad9-4dda-bb50-1ee1733e3ac2',1)
;
UPDATE KREW_DOC_TYP_T SET PARNT_ID='2680',DOC_TYP_NM='RiceDocument',DOC_TYP_VER_NBR=0,ACTV_IND=1,CUR_IND=1,DOC_TYP_DESC='Parent Document Type for all Rice Documents',LBL='Rice Document',PREV_DOC_TYP_VER_NBR=null,DOC_HDR_ID=null,DOC_HDLR_URL=null,HELP_DEF_URL=null,DOC_SEARCH_HELP_URL=null,POST_PRCSR=null,AUTHORIZER=null,GRP_ID=null,BLNKT_APPR_GRP_ID=null,BLNKT_APPR_PLCY=null,RPT_GRP_ID=null,RTE_VER_NBR='2',NOTIFY_ADDR=null,SEC_XML=null,EMAIL_XSL=null,APPL_ID=null,OBJ_ID='6166CBA1BA82644DE0404F8189D86C09',VER_NBR=8 WHERE DOC_TYP_ID = '2681'  AND VER_NBR = 7
;
INSERT INTO KREW_DOC_HDR_S VALUES (NULL)
;
INSERT INTO KREW_DOC_TYP_T (DOC_TYP_ID,PARNT_ID,DOC_TYP_NM,DOC_TYP_VER_NBR,ACTV_IND,CUR_IND,DOC_TYP_DESC,LBL,PREV_DOC_TYP_VER_NBR,DOC_HDR_ID,DOC_HDLR_URL,HELP_DEF_URL,DOC_SEARCH_HELP_URL,POST_PRCSR,AUTHORIZER,GRP_ID,BLNKT_APPR_GRP_ID,BLNKT_APPR_PLCY,RPT_GRP_ID,RTE_VER_NBR,NOTIFY_ADDR,SEC_XML,EMAIL_XSL,APPL_ID,OBJ_ID,VER_NBR) VALUES ('3079','2681','TermSpecificationMaintenanceDocument',0,1,1,'Create a KRMS Term Specification','KRMS Term Specification Maintenance Document',null,null,'${kr.krad.url}/maintenance?methodToCall=docHandler','','','org.kuali.rice.krad.workflow.postprocessor.KualiPostProcessor',null,'1','1',null,null,'2',null,null,null,null,'6d1d32ce-d51d-478b-8c3a-0ad6857fbb28',1)
;
UPDATE KREW_DOC_TYP_T SET PARNT_ID='2680',DOC_TYP_NM='RiceDocument',DOC_TYP_VER_NBR=0,ACTV_IND=1,CUR_IND=1,DOC_TYP_DESC='Parent Document Type for all Rice Documents',LBL='Rice Document',PREV_DOC_TYP_VER_NBR=null,DOC_HDR_ID=null,DOC_HDLR_URL=null,HELP_DEF_URL=null,DOC_SEARCH_HELP_URL=null,POST_PRCSR=null,AUTHORIZER=null,GRP_ID=null,BLNKT_APPR_GRP_ID=null,BLNKT_APPR_PLCY=null,RPT_GRP_ID=null,RTE_VER_NBR='2',NOTIFY_ADDR=null,SEC_XML=null,EMAIL_XSL=null,APPL_ID=null,OBJ_ID='6166CBA1BA82644DE0404F8189D86C09',VER_NBR=9 WHERE DOC_TYP_ID = '2681'  AND VER_NBR = 8
;
INSERT INTO KREW_RSP_S VALUES (NULL)
;
INSERT INTO KREW_RSP_S VALUES (NULL)
;
INSERT INTO KREW_RTE_TMPL_S VALUES (NULL)
;
INSERT INTO KREW_RULE_T (RULE_ID,NM,RULE_VER_NBR,RULE_TMPL_ID,RULE_EXPR_ID,ACTV_IND,RULE_BASE_VAL_DESC,FRC_ACTN,DOC_TYP_NM,DOC_HDR_ID,FRM_DT,TO_DT,DACTVN_DT,CUR_IND,TMPL_RULE_IND,PREV_VER_RULE_ID,DLGN_IND,ACTVN_DT,OBJ_ID,VER_NBR) VALUES ('1684','BalanceTransferRule',0,'1674',null,1,'BalanceTransferRule',1,'BalanceTransferDocumentType',null,null,null,null,1,0,null,0,'2014-08-04 22:12:46','72438ad3-8fb5-417c-b6df-7f34227ba51d',1)
;
INSERT INTO KREW_RSP_S VALUES (NULL)
;
INSERT INTO KREW_RULE_RSP_T (RULE_RSP_ID,RSP_ID,RULE_ID,PRIO,ACTN_RQST_CD,NM,TYP,APPR_PLCY,OBJ_ID,VER_NBR) VALUES ('2067','2065','1684',1,'A','org.kuali.kpme.tklm.leave.workflow.BalanceTransferRoleAttribute!Approver','R','F','f28ce17f-8812-4e14-a507-32b664a43870',1)
;
INSERT INTO KREW_RSP_S VALUES (NULL)
;
INSERT INTO KREW_RULE_RSP_T (RULE_RSP_ID,RSP_ID,RULE_ID,PRIO,ACTN_RQST_CD,NM,TYP,APPR_PLCY,OBJ_ID,VER_NBR) VALUES ('2068','2066','1684',2,'A','org.kuali.kpme.tklm.leave.workflow.BalanceTransferRoleAttribute!Approver Delegate','R','F','e58cd14b-1fb7-4ff7-9974-d1d904885943',1)
;
INSERT INTO KREW_RSP_S VALUES (NULL)
;
INSERT INTO KREW_RSP_S VALUES (NULL)
;
INSERT INTO KREW_RTE_TMPL_S VALUES (NULL)
;
INSERT INTO KREW_RULE_T (RULE_ID,NM,RULE_VER_NBR,RULE_TMPL_ID,RULE_EXPR_ID,ACTV_IND,RULE_BASE_VAL_DESC,FRC_ACTN,DOC_TYP_NM,DOC_HDR_ID,FRM_DT,TO_DT,DACTVN_DT,CUR_IND,TMPL_RULE_IND,PREV_VER_RULE_ID,DLGN_IND,ACTVN_DT,OBJ_ID,VER_NBR) VALUES ('1685','LeaveCalendarRule',0,'1676',null,1,'LeaveCalendarRule',1,'LeaveCalendarDocument',null,null,null,null,1,0,null,0,'2014-08-04 22:12:46','ed0c2d32-f6ec-4f9c-9ca4-36a1cea82070',1)
;
INSERT INTO KREW_RSP_S VALUES (NULL)
;
INSERT INTO KREW_RULE_RSP_T (RULE_RSP_ID,RSP_ID,RULE_ID,PRIO,ACTN_RQST_CD,NM,TYP,APPR_PLCY,OBJ_ID,VER_NBR) VALUES ('2071','2069','1685',1,'A','org.kuali.kpme.tklm.leave.workflow.LeaveCalendarRoleAttribute!Approver','R','F','f0260efa-0d04-4348-96f8-6d5b13ec3cf5',1)
;
INSERT INTO KREW_RSP_S VALUES (NULL)
;
INSERT INTO KREW_RULE_RSP_T (RULE_RSP_ID,RSP_ID,RULE_ID,PRIO,ACTN_RQST_CD,NM,TYP,APPR_PLCY,OBJ_ID,VER_NBR) VALUES ('2072','2070','1685',2,'A','org.kuali.kpme.tklm.leave.workflow.LeaveCalendarRoleAttribute!Approver Delegate','R','F','06734783-bf04-4c42-88a0-2be7e31ca2ef',1)
;
INSERT INTO KREW_RSP_S VALUES (NULL)
;
INSERT INTO KREW_RSP_S VALUES (NULL)
;
INSERT INTO KREW_RTE_TMPL_S VALUES (NULL)
;
INSERT INTO KREW_RULE_T (RULE_ID,NM,RULE_VER_NBR,RULE_TMPL_ID,RULE_EXPR_ID,ACTV_IND,RULE_BASE_VAL_DESC,FRC_ACTN,DOC_TYP_NM,DOC_HDR_ID,FRM_DT,TO_DT,DACTVN_DT,CUR_IND,TMPL_RULE_IND,PREV_VER_RULE_ID,DLGN_IND,ACTVN_DT,OBJ_ID,VER_NBR) VALUES ('1686','LeavePayoutWorkflowRule',0,'1678',null,1,'LeavePayoutWorkflowRule',1,'LeavePayoutDocumentType',null,null,null,null,1,0,null,0,'2014-08-04 22:12:46','4934968c-021b-42fa-b111-ad2c06950571',1)
;
INSERT INTO KREW_RSP_S VALUES (NULL)
;
INSERT INTO KREW_RULE_RSP_T (RULE_RSP_ID,RSP_ID,RULE_ID,PRIO,ACTN_RQST_CD,NM,TYP,APPR_PLCY,OBJ_ID,VER_NBR) VALUES ('2075','2073','1686',1,'A','org.kuali.kpme.tklm.leave.workflow.LeavePayoutRoleAttribute!Approver','R','F','a4461840-6bee-47d0-8c3c-2186b7e0517f',1)
;
INSERT INTO KREW_RSP_S VALUES (NULL)
;
INSERT INTO KREW_RULE_RSP_T (RULE_RSP_ID,RSP_ID,RULE_ID,PRIO,ACTN_RQST_CD,NM,TYP,APPR_PLCY,OBJ_ID,VER_NBR) VALUES ('2076','2074','1686',2,'A','org.kuali.kpme.tklm.leave.workflow.LeavePayoutRoleAttribute!Approver Delegate','R','F','242a4f69-e183-4157-9c0f-35a32fbbb7a9',1)
;
INSERT INTO KREW_RSP_S VALUES (NULL)
;
INSERT INTO KREW_RSP_S VALUES (NULL)
;
INSERT INTO KREW_RTE_TMPL_S VALUES (NULL)
;
INSERT INTO KREW_RULE_T (RULE_ID,NM,RULE_VER_NBR,RULE_TMPL_ID,RULE_EXPR_ID,ACTV_IND,RULE_BASE_VAL_DESC,FRC_ACTN,DOC_TYP_NM,DOC_HDR_ID,FRM_DT,TO_DT,DACTVN_DT,CUR_IND,TMPL_RULE_IND,PREV_VER_RULE_ID,DLGN_IND,ACTVN_DT,OBJ_ID,VER_NBR) VALUES ('1687','LeaveRequestRule',0,'1680',null,1,'LeaveRequestRule',1,'LeaveRequestDocument',null,null,null,null,1,0,null,0,'2014-08-04 22:12:46','d1b06c3a-e76e-49e4-8088-5428cf3e9772',1)
;
INSERT INTO KREW_RSP_S VALUES (NULL)
;
INSERT INTO KREW_RULE_RSP_T (RULE_RSP_ID,RSP_ID,RULE_ID,PRIO,ACTN_RQST_CD,NM,TYP,APPR_PLCY,OBJ_ID,VER_NBR) VALUES ('2079','2077','1687',1,'A','org.kuali.kpme.tklm.leave.workflow.LeaveRequestRoleAttribute!Approver','R','F','e17eafa8-b539-423a-941d-d879798bdc15',1)
;
INSERT INTO KREW_RSP_S VALUES (NULL)
;
INSERT INTO KREW_RULE_RSP_T (RULE_RSP_ID,RSP_ID,RULE_ID,PRIO,ACTN_RQST_CD,NM,TYP,APPR_PLCY,OBJ_ID,VER_NBR) VALUES ('2080','2078','1687',2,'A','org.kuali.kpme.tklm.leave.workflow.LeaveRequestRoleAttribute!Approver Delegate','R','F','afe0e647-c65d-4d2c-85f5-dd5007d5e749',1)
;
INSERT INTO KREW_RSP_S VALUES (NULL)
;
INSERT INTO KREW_RSP_S VALUES (NULL)
;
INSERT INTO KREW_RTE_TMPL_S VALUES (NULL)
;
INSERT INTO KREW_RULE_T (RULE_ID,NM,RULE_VER_NBR,RULE_TMPL_ID,RULE_EXPR_ID,ACTV_IND,RULE_BASE_VAL_DESC,FRC_ACTN,DOC_TYP_NM,DOC_HDR_ID,FRM_DT,TO_DT,DACTVN_DT,CUR_IND,TMPL_RULE_IND,PREV_VER_RULE_ID,DLGN_IND,ACTVN_DT,OBJ_ID,VER_NBR) VALUES ('1688','TimesheetRule',0,'1682',null,1,'TimesheetRule',1,'TimesheetDocument',null,null,null,null,1,0,null,0,'2014-08-04 22:12:47','2ba4e982-5ae7-4372-800e-8e1ee8ebe72b',1)
;
INSERT INTO KREW_RSP_S VALUES (NULL)
;
INSERT INTO KREW_RULE_RSP_T (RULE_RSP_ID,RSP_ID,RULE_ID,PRIO,ACTN_RQST_CD,NM,TYP,APPR_PLCY,OBJ_ID,VER_NBR) VALUES ('2083','2081','1688',1,'A','org.kuali.kpme.tklm.time.workflow.TimesheetRoleAttribute!Approver','R','F','8dad80a3-a970-471d-9d1b-3ddfff1749da',1)
;
INSERT INTO KREW_RSP_S VALUES (NULL)
;
INSERT INTO KREW_RULE_RSP_T (RULE_RSP_ID,RSP_ID,RULE_ID,PRIO,ACTN_RQST_CD,NM,TYP,APPR_PLCY,OBJ_ID,VER_NBR) VALUES ('2084','2082','1688',2,'A','org.kuali.kpme.tklm.time.workflow.TimesheetRoleAttribute!Approver Delegate','R','F','b80518c4-6327-4d68-867c-2af7e048722e',1)
;

INSERT INTO KREW_RTE_TMPL_S VALUES (50000);
INSERT INTO KREW_RSP_S VALUES (50000);
INSERT INTO KREW_DOC_HDR_S VALUES (50000);
INSERT INTO KREW_RTE_NODE_CFG_PARM_S VALUES (50000);
INSERT INTO KREW_DOC_TYP_ATTR_S VALUES (50000);
INSERT INTO KREW_RTE_NODE_S VALUES (50000);