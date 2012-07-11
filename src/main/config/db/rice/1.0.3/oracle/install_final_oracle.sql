--
-- KREN_CHNL_T
--


INSERT INTO KREN_CHNL_T (CHNL_ID,DESC_TXT,NM,SUBSCRB_IND,VER_NBR)
  VALUES (500,'Builtin channel for KEW','KEW','N',1)
/

--
-- KREN_CNTNT_TYP_T
--


INSERT INTO KREN_CNTNT_TYP_T (CNTNT_TYP_ID,CNTNT_TYP_VER_NBR,CUR_IND,DESC_TXT,NM,NMSPC_CD,VER_NBR,XSD,XSL)
  VALUES (1,0,'T','Simple content type','Simple','notification/ContentTypeSimple',1,'<?xml version="1.0" encoding="UTF-8"?>
<!-- This schema describes a simple notification.  It only contains a content
element which is a String...about as simple as one can get -->
<schema xmlns="http://www.w3.org/2001/XMLSchema"
  xmlns:c="ns:notification/common"
  xmlns:cs="ns:notification/ContentTypeSimple"
  targetNamespace="ns:notification/ContentTypeSimple"
  attributeFormDefault="unqualified" 
    elementFormDefault="qualified">
  <annotation>
    <documentation xml:lang="en">
      Simple Content Schema
    </documentation>
  </annotation>
  <import namespace="ns:notification/common" schemaLocation="resource:notification/notification-common" />
  <!--  The content element is just a String -->
  <element name="content">
    <complexType>
      <sequence>
        <element name="message" type="c:LongStringType"/>
      </sequence>
    </complexType>
  </element>
</schema>','<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet
   version="1.0" 
   xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
   xmlns:n="ns:notification/ContentTypeSimple" 
   xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
   xsi:schemaLocation="ns:notification/ContentTypeSimple resource:notification/ContentTypeSimple" 
   exclude-result-prefixes="n xsi">
   <xsl:output method="html" omit-xml-declaration="yes" />
   <xsl:template match="/n:content/n:message">
      <strong>
          <xsl:value-of select="." disable-output-escaping="yes"/>
      </strong>
   </xsl:template>
</xsl:stylesheet>')
/
INSERT INTO KREN_CNTNT_TYP_T (CNTNT_TYP_ID,CNTNT_TYP_VER_NBR,CUR_IND,DESC_TXT,NM,NMSPC_CD,VER_NBR,XSD,XSL)
  VALUES (2,0,'T','Event content type','Event','notification/ContentTypeEvent',1,'<?xml version="1.0" encoding="UTF-8"?>
<!-- This schema defines an generic event notification type in order for it
to be accepted into the system. -->
<schema xmlns="http://www.w3.org/2001/XMLSchema" xmlns:c="ns:notification/common" xmlns:ce="ns:notification/ContentTypeEvent" targetNamespace="ns:notification/ContentTypeEvent" attributeFormDefault="unqualified" elementFormDefault="qualified">
  <annotation>
    <documentation xml:lang="en">Content Event Schema</documentation>
  </annotation>
  <import namespace="ns:notification/common" schemaLocation="resource:notification/notification-common" />
  <!-- The content element describes the content of the notification.  It
  contains a message (a simple String) and a message element -->
  <element name="content">
    <complexType>
      <sequence>
        <element name="message" type="c:LongStringType"/>
        <element ref="ce:event"/>
      </sequence>
    </complexType>
  </element>
  <!-- This is the event element.  It describes a simple event type containing a
  summary, description, location, and start/stop times -->
  <element name="event">
    <complexType>
      <sequence>
        <element name="summary" type="c:NonEmptyShortStringType" />
        <element name="description" type="c:NonEmptyShortStringType" />
        <element name="location" type="c:NonEmptyShortStringType" />
        <element name="startDateTime" type="c:NonEmptyShortStringType" />
        <element name="stopDateTime" type="c:NonEmptyShortStringType" />
      </sequence>
    </complexType>
  </element>
</schema>','<?xml version="1.0" encoding="UTF-8"?>
<!-- style sheet declaration: be very careful editing the following, the
     default namespace must be used otherwise elements will not match -->
<xsl:stylesheet
    version="1.0" 
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
    xmlns:n="ns:notification/ContentTypeEvent" 
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
    xsi:schemaLocation="ns:notification/ContentTypeEvent resource:notification/ContentTypeEvent" 
    exclude-result-prefixes="n xsi">
    <!-- output an html fragment -->
    <xsl:output method="html" indent="yes" />
    <!-- match everything -->
    <xsl:template match="/n:content" >
        <table class="bord-all">
            <xsl:apply-templates />
        </table>
    </xsl:template>
    <!--  match message element in the default namespace and render as strong -->
    <xsl:template match="n:message" >
        <caption>
            <strong><xsl:value-of select="." disable-output-escaping="yes"/></strong>
        </caption>
    </xsl:template>
    <!-- match on event in the default namespace and display all children -->
    <xsl:template match="n:event">
        <tr>
            <td class="thnormal"><strong>Summary: </strong></td>
            <td class="thnormal"><xsl:value-of select="n:summary" /></td>
        </tr>
        <tr>
            <td class="thnormal"><strong>Description: </strong></td>
            <td class="thnormal"><xsl:value-of select="n:description" /></td>
        </tr>
        <tr>
            <td class="thnormal"><strong>Location: </strong></td>
            <td class="thnormal"><xsl:value-of select="n:location" /></td>
        </tr>
        <tr>
            <td class="thnormal"><strong>Start Time: </strong></td>
            <td class="thnormal"><xsl:value-of select="n:startDateTime" /></td>
        </tr>
        <tr>
            <td class="thnormal"><strong>End Time: </strong></td>
            <td class="thnormal"><xsl:value-of select="n:stopDateTime" /></td>
        </tr>
    </xsl:template> 
</xsl:stylesheet>')
/
--
-- KREN_PRIO_T
--


INSERT INTO KREN_PRIO_T (DESC_TXT,NM,PRIO_ID,PRIO_ORD,VER_NBR)
  VALUES ('Normal priority','Normal',1,2,1)
/
INSERT INTO KREN_PRIO_T (DESC_TXT,NM,PRIO_ID,PRIO_ORD,VER_NBR)
  VALUES ('A low priority','Low',2,3,1)
/
INSERT INTO KREN_PRIO_T (DESC_TXT,NM,PRIO_ID,PRIO_ORD,VER_NBR)
  VALUES ('A high priority','High',3,1,1)
/
--
-- KREW_DOC_TYP_ATTR_T
--


INSERT INTO KREW_DOC_TYP_ATTR_T (DOC_TYP_ATTRIB_ID,DOC_TYP_ID,ORD_INDX,RULE_ATTR_ID)
  VALUES (2000,2023,1,1003)
/
INSERT INTO KREW_DOC_TYP_ATTR_T (DOC_TYP_ATTRIB_ID,DOC_TYP_ID,ORD_INDX,RULE_ATTR_ID)
  VALUES (2001,2023,2,1006)
/
INSERT INTO KREW_DOC_TYP_ATTR_T (DOC_TYP_ATTRIB_ID,DOC_TYP_ID,ORD_INDX,RULE_ATTR_ID)
  VALUES (2002,2023,3,1004)
/
INSERT INTO KREW_DOC_TYP_ATTR_T (DOC_TYP_ATTRIB_ID,DOC_TYP_ID,ORD_INDX,RULE_ATTR_ID)
  VALUES (2003,2023,4,1007)
/
INSERT INTO KREW_DOC_TYP_ATTR_T (DOC_TYP_ATTRIB_ID,DOC_TYP_ID,ORD_INDX,RULE_ATTR_ID)
  VALUES (2004,2023,5,1005)
/
INSERT INTO KREW_DOC_TYP_ATTR_T (DOC_TYP_ATTRIB_ID,DOC_TYP_ID,ORD_INDX,RULE_ATTR_ID)
  VALUES (2005,2023,6,1008)
/
INSERT INTO KREW_DOC_TYP_ATTR_T (DOC_TYP_ATTRIB_ID,DOC_TYP_ID,ORD_INDX,RULE_ATTR_ID)
  VALUES (2006,2023,7,1009)
/
INSERT INTO KREW_DOC_TYP_ATTR_T (DOC_TYP_ATTRIB_ID,DOC_TYP_ID,ORD_INDX,RULE_ATTR_ID)
  VALUES (2007,2024,1,1003)
/
--
-- KREW_DOC_TYP_PLCY_RELN_T
--


INSERT INTO KREW_DOC_TYP_PLCY_RELN_T (DOC_PLCY_NM,DOC_TYP_ID,OBJ_ID,PLCY_NM,VER_NBR)
  VALUES ('DEFAULT_APPROVE',2024,'61BA2DCF3BE658EEE0404F8189D80CAE',1,1)
/
INSERT INTO KREW_DOC_TYP_PLCY_RELN_T (DOC_PLCY_NM,DOC_TYP_ID,OBJ_ID,PLCY_NM,VER_NBR)
  VALUES ('DEFAULT_APPROVE',2680,'61BA2DCF3BE858EEE0404F8189D80CAE',1,2)
/
INSERT INTO KREW_DOC_TYP_PLCY_RELN_T (DOC_PLCY_NM,DOC_TYP_ID,OBJ_ID,PLCY_NM,VER_NBR)
  VALUES ('LOOK_FUTURE',2024,'61BA2DCF3BE758EEE0404F8189D80CAE',1,1)
/
INSERT INTO KREW_DOC_TYP_PLCY_RELN_T (DOC_PLCY_NM,DOC_TYP_ID,OBJ_ID,PLCY_NM,VER_NBR)
  VALUES ('LOOK_FUTURE',2680,'61BA2DCF3BE958EEE0404F8189D80CAE',1,2)
/
--
-- KREW_DOC_TYP_PROC_T
--


INSERT INTO KREW_DOC_TYP_PROC_T (DOC_TYP_ID,DOC_TYP_PROC_ID,INIT_IND,INIT_RTE_NODE_ID,NM,VER_NBR)
  VALUES (2011,2005,1,2004,'PRIMARY',2)
/
INSERT INTO KREW_DOC_TYP_PROC_T (DOC_TYP_ID,DOC_TYP_PROC_ID,INIT_IND,INIT_RTE_NODE_ID,NM,VER_NBR)
  VALUES (2012,2007,1,2006,'PRIMARY',2)
/
INSERT INTO KREW_DOC_TYP_PROC_T (DOC_TYP_ID,DOC_TYP_PROC_ID,INIT_IND,INIT_RTE_NODE_ID,NM,VER_NBR)
  VALUES (2023,2040,1,2039,'PRIMARY',1)
/
INSERT INTO KREW_DOC_TYP_PROC_T (DOC_TYP_ID,DOC_TYP_PROC_ID,INIT_IND,INIT_RTE_NODE_ID,NM,VER_NBR)
  VALUES (2024,2044,1,2041,'PRIMARY',1)
/
INSERT INTO KREW_DOC_TYP_PROC_T (DOC_TYP_ID,DOC_TYP_PROC_ID,INIT_IND,INIT_RTE_NODE_ID,NM,VER_NBR)
  VALUES (2031,2062,1,2061,'PRIMARY',1)
/
INSERT INTO KREW_DOC_TYP_PROC_T (DOC_TYP_ID,DOC_TYP_PROC_ID,INIT_IND,INIT_RTE_NODE_ID,NM,VER_NBR)
  VALUES (2032,2064,1,2063,'PRIMARY',1)
/
INSERT INTO KREW_DOC_TYP_PROC_T (DOC_TYP_ID,DOC_TYP_PROC_ID,INIT_IND,INIT_RTE_NODE_ID,NM,VER_NBR)
  VALUES (2033,2066,1,2065,'PRIMARY',1)
/
INSERT INTO KREW_DOC_TYP_PROC_T (DOC_TYP_ID,DOC_TYP_PROC_ID,INIT_IND,INIT_RTE_NODE_ID,NM,VER_NBR)
  VALUES (2034,2068,1,2067,'PRIMARY',1)
/
INSERT INTO KREW_DOC_TYP_PROC_T (DOC_TYP_ID,DOC_TYP_PROC_ID,INIT_IND,INIT_RTE_NODE_ID,NM,VER_NBR)
  VALUES (2680,2841,1,2840,'PRIMARY',2)
/
INSERT INTO KREW_DOC_TYP_PROC_T (DOC_TYP_ID,DOC_TYP_PROC_ID,INIT_IND,INIT_RTE_NODE_ID,NM,VER_NBR)
  VALUES (2995,2900,1,2898,'PRIMARY',1)
/
INSERT INTO KREW_DOC_TYP_PROC_T (DOC_TYP_ID,DOC_TYP_PROC_ID,INIT_IND,INIT_RTE_NODE_ID,NM,VER_NBR)
  VALUES (2996,2903,1,2901,'PRIMARY',1)
/
INSERT INTO KREW_DOC_TYP_PROC_T (DOC_TYP_ID,DOC_TYP_PROC_ID,INIT_IND,INIT_RTE_NODE_ID,NM,VER_NBR)
  VALUES (2997,2907,1,2904,'PRIMARY',1)
/
INSERT INTO KREW_DOC_TYP_PROC_T (DOC_TYP_ID,DOC_TYP_PROC_ID,INIT_IND,INIT_RTE_NODE_ID,NM,VER_NBR)
  VALUES (2998,2909,1,2908,'PRIMARY',1)
/
INSERT INTO KREW_DOC_TYP_PROC_T (DOC_TYP_ID,DOC_TYP_PROC_ID,INIT_IND,INIT_RTE_NODE_ID,NM,VER_NBR)
  VALUES (2999,2911,1,2910,'PRIMARY',1)
/
--
-- KREW_DOC_TYP_T
--


INSERT INTO KREW_DOC_TYP_T (ACTV_IND,BLNKT_APPR_GRP_ID,CUR_IND,DOC_HDLR_URL,DOC_TYP_DESC,DOC_TYP_ID,DOC_TYP_NM,DOC_TYP_VER_NBR,GRP_ID,HELP_DEF_URL,LBL,OBJ_ID,PARNT_ID,POST_PRCSR,RTE_VER_NBR,VER_NBR)
  VALUES (1,'1',1,'${kr.url}/maintenance.do?methodToCall=docHandler','Workflow Maintenance Document Type Document',2011,'DocumentTypeDocument',0,'1','default.htm?turl=WordDocuments%2Fdocumenttype.htm','Workflow Maintenance Document Type Document','6166CBA1BA5D644DE0404F8189D86C09',2681,'org.kuali.rice.kns.workflow.postprocessor.KualiPostProcessor','2',0)
/
INSERT INTO KREW_DOC_TYP_T (ACTV_IND,BLNKT_APPR_GRP_ID,CUR_IND,DOC_HDLR_URL,DOC_TYP_DESC,DOC_TYP_ID,DOC_TYP_NM,DOC_TYP_VER_NBR,GRP_ID,LBL,OBJ_ID,PARNT_ID,POST_PRCSR,RTE_VER_NBR,VER_NBR)
  VALUES (1,'1',1,'${kr.url}/maintenance.do?methodToCall=docHandler','Rule Maintenance Document Type Document',2012,'RoutingRuleDocument',0,'1','Rule Maintenance Document Type Document','6166CBA1BA5E644DE0404F8189D86C09',2681,'org.kuali.rice.kns.workflow.postprocessor.KualiPostProcessor','2',0)
/
INSERT INTO KREW_DOC_TYP_T (ACTV_IND,BLNKT_APPR_GRP_ID,CUR_IND,DOC_HDLR_URL,DOC_TYP_DESC,DOC_TYP_ID,DOC_TYP_NM,DOC_TYP_VER_NBR,GRP_ID,LBL,OBJ_ID,POST_PRCSR,RTE_VER_NBR,VER_NBR)
  VALUES (1,'1',1,'${ken.url}/DetailView.form','This is the re-usable notification document type that will be used for delivering all notifications with KEW.',2023,'KualiNotification',0,'2000','Notification','6166CBA1BA69644DE0404F8189D86C09','org.kuali.rice.ken.postprocessor.kew.NotificationPostProcessor','1',0)
/
INSERT INTO KREW_DOC_TYP_T (ACTV_IND,BLNKT_APPR_GRP_ID,CUR_IND,DOC_HDLR_URL,DOC_TYP_DESC,DOC_TYP_ID,DOC_TYP_NM,DOC_TYP_VER_NBR,GRP_ID,LBL,OBJ_ID,POST_PRCSR,RTE_VER_NBR,VER_NBR)
  VALUES (1,'1',1,'${ken.url}/AdministerNotificationRequest.form','Create a New Notification Request',2024,'SendNotificationRequest',0,'1','Send Notification Request','6166CBA1BA6A644DE0404F8189D86C09','org.kuali.rice.ken.postprocessor.kew.NotificationSenderFormPostProcessor','2',0)
/
INSERT INTO KREW_DOC_TYP_T (ACTV_IND,BLNKT_APPR_GRP_ID,CUR_IND,DOC_HDLR_URL,DOC_TYP_DESC,DOC_TYP_ID,DOC_TYP_NM,DOC_TYP_VER_NBR,GRP_ID,HELP_DEF_URL,LBL,OBJ_ID,PARNT_ID,POST_PRCSR,RTE_VER_NBR,VER_NBR)
  VALUES (1,'1',1,'${kr.url}/maintenance.do?methodToCall=docHandler','Create/edit parameter namespaces',2031,'NamespaceMaintenanceDocument',0,'1','default.htm?turl=WordDocuments%2Fnamespace.htm','Namespace','6166CBA1BA71644DE0404F8189D86C09',2681,'org.kuali.rice.kns.workflow.postprocessor.KualiPostProcessor','2',0)
/
INSERT INTO KREW_DOC_TYP_T (ACTV_IND,BLNKT_APPR_GRP_ID,CUR_IND,DOC_HDLR_URL,DOC_TYP_DESC,DOC_TYP_ID,DOC_TYP_NM,DOC_TYP_VER_NBR,GRP_ID,HELP_DEF_URL,LBL,OBJ_ID,PARNT_ID,POST_PRCSR,RTE_VER_NBR,VER_NBR)
  VALUES (1,'1',1,'${kr.url}/maintenance.do?methodToCall=docHandler','Create/edit a parameter type',2032,'ParameterTypeMaintenanceDocument',0,'1','default.htm?turl=WordDocuments%2Fparametertype.htm','Parameter Type Maintenance Document','6166CBA1BA72644DE0404F8189D86C09',2681,'org.kuali.rice.kns.workflow.postprocessor.KualiPostProcessor','2',0)
/
INSERT INTO KREW_DOC_TYP_T (ACTV_IND,BLNKT_APPR_GRP_ID,CUR_IND,DOC_HDLR_URL,DOC_TYP_DESC,DOC_TYP_ID,DOC_TYP_NM,DOC_TYP_VER_NBR,GRP_ID,HELP_DEF_URL,LBL,OBJ_ID,PARNT_ID,POST_PRCSR,RTE_VER_NBR,VER_NBR)
  VALUES (1,'1',1,'${kr.url}/maintenance.do?methodToCall=docHandler','Create/edit a parameter detail type',2033,'ParameterDetailTypeMaintenanceDocument',0,'1','default.htm?turl=WordDocuments%2Fparametercomponent.htm','Parameter Detail Type Maintenance Document','6166CBA1BA73644DE0404F8189D86C09',2681,'org.kuali.rice.kns.workflow.postprocessor.KualiPostProcessor','2',0)
/
INSERT INTO KREW_DOC_TYP_T (ACTV_IND,BLNKT_APPR_GRP_ID,CUR_IND,DOC_HDLR_URL,DOC_TYP_DESC,DOC_TYP_ID,DOC_TYP_NM,DOC_TYP_VER_NBR,GRP_ID,HELP_DEF_URL,LBL,OBJ_ID,PARNT_ID,POST_PRCSR,RTE_VER_NBR,VER_NBR)
  VALUES (1,'1',1,'${kr.url}/maintenance.do?methodToCall=docHandler','Create/edit a parameter',2034,'ParameterMaintenanceDocument',0,'1','default.htm?turl=WordDocuments%2Fparameter.htm','Parameter Maintenance Document','6166CBA1BA74644DE0404F8189D86C09',2681,'org.kuali.rice.kns.workflow.postprocessor.KualiPostProcessor','2',0)
/
INSERT INTO KREW_DOC_TYP_T (ACTV_IND,CUR_IND,DOC_TYP_DESC,DOC_TYP_ID,DOC_TYP_NM,DOC_TYP_VER_NBR,LBL,OBJ_ID,POST_PRCSR,RTE_VER_NBR,VER_NBR)
  VALUES (1,1,'KualiDocument',2680,'KualiDocument',0,'KualiDocument','6166CBA1BA81644DE0404F8189D86C09','org.kuali.rice.kns.workflow.postprocessor.KualiPostProcessor','2',0)
/
INSERT INTO KREW_DOC_TYP_T (ACTV_IND,CUR_IND,DOC_TYP_DESC,DOC_TYP_ID,DOC_TYP_NM,DOC_TYP_VER_NBR,LBL,OBJ_ID,PARNT_ID,RTE_VER_NBR,VER_NBR)
  VALUES (1,1,'Parent Document Type for all Rice Documents',2681,'RiceDocument',0,'Rice Document','6166CBA1BA82644DE0404F8189D86C09',2680,'2',0)
/
INSERT INTO KREW_DOC_TYP_T (ACTV_IND,CUR_IND,DOC_HDLR_URL,DOC_TYP_DESC,DOC_TYP_ID,DOC_TYP_NM,DOC_TYP_VER_NBR,LBL,OBJ_ID,PARNT_ID,POST_PRCSR,RTE_VER_NBR,VER_NBR)
  VALUES (1,1,'${kr.url}/maintenance.do?methodToCall=docHandler','Routing Rule Delegation',2699,'RoutingRuleDelegationMaintenanceDocument',0,'Routing Rule Delegation','A6DC8753-AF90-7A01-0EF7-E6D446529668',2681,'org.kuali.rice.kns.workflow.postprocessor.KualiPostProcessor','2',0)
/
INSERT INTO KREW_DOC_TYP_T (ACTV_IND,CUR_IND,DOC_HDLR_URL,DOC_TYP_ID,DOC_TYP_NM,DOC_TYP_VER_NBR,HELP_DEF_URL,LBL,OBJ_ID,PARNT_ID,RTE_VER_NBR,VER_NBR)
  VALUES (1,1,'${kr.url}/maintenance.do?methodToCall=docHandler',2708,'CampusMaintenanceDocument',0,'default.htm?turl=WordDocuments%2Fcampus.htm','CampusMaintenanceDocument','616D94CA-D08D-D036-E77D-4B53DB34CD95',2681,'2',0)
/
INSERT INTO KREW_DOC_TYP_T (ACTV_IND,CUR_IND,DOC_HDLR_URL,DOC_TYP_ID,DOC_TYP_NM,DOC_TYP_VER_NBR,HELP_DEF_URL,LBL,OBJ_ID,PARNT_ID,RTE_VER_NBR,VER_NBR)
  VALUES (1,1,'${kr.url}/maintenance.do?methodToCall=docHandler',2709,'CampusTypeMaintenanceDocument',0,'default.htm?turl=WordDocuments%2Fcampustype.htm','CampusTypeMaintenanceDocument','DE0B8588-E459-C07A-87B8-6ACD693AE70C',2681,'2',0)
/
INSERT INTO KREW_DOC_TYP_T (ACTV_IND,CUR_IND,DOC_HDLR_URL,DOC_TYP_ID,DOC_TYP_NM,DOC_TYP_VER_NBR,HELP_DEF_URL,LBL,OBJ_ID,PARNT_ID,RTE_VER_NBR,VER_NBR)
  VALUES (1,1,'${kr.url}/maintenance.do?methodToCall=docHandler',2710,'CountryMaintenanceDocument',0,'default.htm?turl=WordDocuments%2Fcountry.htm','CountryMaintenanceDocument','82EDB593-97BA-428E-C6E7-A7F3031CFAEB',2681,'2',0)
/
INSERT INTO KREW_DOC_TYP_T (ACTV_IND,CUR_IND,DOC_HDLR_URL,DOC_TYP_ID,DOC_TYP_NM,DOC_TYP_VER_NBR,HELP_DEF_URL,LBL,OBJ_ID,PARNT_ID,RTE_VER_NBR,VER_NBR)
  VALUES (1,1,'${kr.url}/maintenance.do?methodToCall=docHandler',2711,'CountyMaintenanceDocument',0,'default.htm?turl=WordDocuments%2Fcounty.htm','CountyMaintenanceDocument','C972E260-5552-BB63-72E6-A514301B0326',2681,'2',0)
/
INSERT INTO KREW_DOC_TYP_T (ACTV_IND,CUR_IND,DOC_HDLR_URL,DOC_TYP_ID,DOC_TYP_NM,DOC_TYP_VER_NBR,HELP_DEF_URL,LBL,OBJ_ID,PARNT_ID,RTE_VER_NBR,VER_NBR)
  VALUES (1,1,'${kr.url}/maintenance.do?methodToCall=docHandler',2712,'PostalCodeMaintenanceDocument',0,'default.htm?turl=WordDocuments%2Fpostalcode.htm','PostalCodeMaintenanceDocument','B79D1104-BC48-1597-AFBE-773EED31A110',2681,'2',0)
/
INSERT INTO KREW_DOC_TYP_T (ACTV_IND,CUR_IND,DOC_HDLR_URL,DOC_TYP_ID,DOC_TYP_NM,DOC_TYP_VER_NBR,HELP_DEF_URL,LBL,OBJ_ID,PARNT_ID,RTE_VER_NBR,VER_NBR)
  VALUES (1,1,'${kr.url}/maintenance.do?methodToCall=docHandler',2713,'StateMaintenanceDocument',0,'default.htm?turl=WordDocuments%2Fstate.htm','StateMaintenanceDocument','EF2378F6-E770-D7BF-B7F1-C18881E3AFF0',2681,'2',0)
/
INSERT INTO KREW_DOC_TYP_T (ACTV_IND,CUR_IND,DOC_TYP_ID,DOC_TYP_NM,DOC_TYP_VER_NBR,LBL,OBJ_ID,PARNT_ID,RTE_VER_NBR,VER_NBR)
  VALUES (1,1,2994,'IdentityManagementDocument',0,'Undefined','917DA614-1BED-D8B4-F3BA-760EB30DF553',2681,'2',0)
/
INSERT INTO KREW_DOC_TYP_T (ACTV_IND,CUR_IND,DOC_HDLR_URL,DOC_TYP_ID,DOC_TYP_NM,DOC_TYP_VER_NBR,HELP_DEF_URL,LBL,OBJ_ID,PARNT_ID,RTE_VER_NBR,VER_NBR)
  VALUES (1,1,'${kim.url}/identityManagementRoleDocument.do?methodToCall=docHandler',2995,'IdentityManagementRoleDocument',0,'default.htm?turl=WordDocuments%2Frole.htm','Role','6A96BB4F-13F2-72D0-5D12-723E09416097',2994,'2',0)
/
INSERT INTO KREW_DOC_TYP_T (ACTV_IND,CUR_IND,DOC_HDLR_URL,DOC_TYP_ID,DOC_TYP_NM,DOC_TYP_VER_NBR,HELP_DEF_URL,LBL,OBJ_ID,PARNT_ID,RTE_VER_NBR,VER_NBR)
  VALUES (1,1,'${kim.url}/identityManagementGroupDocument.do?methodToCall=docHandler',2996,'IdentityManagementGroupDocument',0,'default.htm?turl=WordDocuments%2Fgroup.htm','Group','8A4693A2-B198-1DA8-5258-E22D29F9EEC4',2994,'2',0)
/
INSERT INTO KREW_DOC_TYP_T (ACTV_IND,CUR_IND,DOC_HDLR_URL,DOC_TYP_ID,DOC_TYP_NM,DOC_TYP_VER_NBR,HELP_DEF_URL,LBL,OBJ_ID,PARNT_ID,RTE_VER_NBR,VER_NBR)
  VALUES (1,1,'${kim.url}/identityManagementPersonDocument.do?methodToCall=docHandler',2997,'IdentityManagementPersonDocument',0,'default.htm?turl=WordDocuments%2Fperson.htm','Person','A9B573D5-AFF9-95F4-AF12-1055A26CCB9A',2994,'2',0)
/
INSERT INTO KREW_DOC_TYP_T (ACTV_IND,CUR_IND,DOC_HDLR_URL,DOC_TYP_ID,DOC_TYP_NM,DOC_TYP_VER_NBR,HELP_DEF_URL,LBL,OBJ_ID,PARNT_ID,RTE_VER_NBR,VER_NBR)
  VALUES (1,1,'${kr.url}/maintenance.do?methodToCall=docHandler',2998,'IdentityManagementReviewResponsibilityMaintenanceDocument',0,'default.htm?turl=WordDocuments%2Fresponsibility.htm','Review Responsibility','05C15C71-A466-36FF-2339-A6D303C268F7',2994,'2',0)
/
INSERT INTO KREW_DOC_TYP_T (ACTV_IND,CUR_IND,DOC_HDLR_URL,DOC_TYP_ID,DOC_TYP_NM,DOC_TYP_VER_NBR,HELP_DEF_URL,LBL,OBJ_ID,PARNT_ID,RTE_VER_NBR,VER_NBR)
  VALUES (1,1,'${kr.url}/maintenance.do?methodToCall=docHandler',2999,'IdentityManagementGenericPermissionMaintenanceDocument',0,'default.htm?turl=WordDocuments%2Fpermission.htm','Permission','93B3B080-E14E-1836-67D0-0AC58A57E61B',2994,'2',0)
/
--
-- KREW_RTE_NODE_CFG_PARM_T
--


INSERT INTO KREW_RTE_NODE_CFG_PARM_T (KEY_CD,RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,VAL)
  VALUES ('contentFragment',2012,2004,'<start name="placeholder"><activationType>S</activationType></start>')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (KEY_CD,RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,VAL)
  VALUES ('activationType',2013,2004,'S')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (KEY_CD,RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,VAL)
  VALUES ('ruleSelector',2014,2004,'Template')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (KEY_CD,RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,VAL)
  VALUES ('contentFragment',2015,2006,'<start name="placeholder"><activationType>S</activationType></start>')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (KEY_CD,RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,VAL)
  VALUES ('activationType',2016,2006,'S')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (KEY_CD,RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,VAL)
  VALUES ('ruleSelector',2017,2006,'Template')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (KEY_CD,RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,VAL)
  VALUES ('contentFragment',2118,2039,'<start name="Adhoc Routing"><activationType>S</activationType><mandatoryRoute>false</mandatoryRoute><finalApproval>true</finalApproval></start>')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (KEY_CD,RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,VAL)
  VALUES ('activationType',2119,2039,'S')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (KEY_CD,RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,VAL)
  VALUES ('mandatoryRoute',2120,2039,'false')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (KEY_CD,RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,VAL)
  VALUES ('finalApproval',2121,2039,'true')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (KEY_CD,RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,VAL)
  VALUES ('ruleSelector',2122,2039,'Template')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (KEY_CD,RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,VAL)
  VALUES ('contentFragment',2123,2041,'<start name="Initiated"><activationType>S</activationType></start>')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (KEY_CD,RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,VAL)
  VALUES ('activationType',2124,2041,'S')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (KEY_CD,RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,VAL)
  VALUES ('ruleSelector',2125,2041,'Template')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (KEY_CD,RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,VAL)
  VALUES ('contentFragment',2126,2042,'<requests name="ReviewersNode"><ruleTemplate>ReviewersRouting</ruleTemplate></requests>')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (KEY_CD,RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,VAL)
  VALUES ('ruleTemplate',2127,2042,'ReviewersRouting')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (KEY_CD,RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,VAL)
  VALUES ('ruleSelector',2128,2042,'Template')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (KEY_CD,RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,VAL)
  VALUES ('contentFragment',2129,2043,'<requests name="RequestsNode"><ruleTemplate>NotificationRouting</ruleTemplate></requests>')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (KEY_CD,RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,VAL)
  VALUES ('ruleTemplate',2130,2043,'NotificationRouting')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (KEY_CD,RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,VAL)
  VALUES ('ruleSelector',2131,2043,'Template')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (KEY_CD,RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,VAL)
  VALUES ('contentFragment',2170,2061,'<start name="Initiated"><activationType>P</activationType><mandatoryRoute>false</mandatoryRoute><finalApproval>false</finalApproval></start>')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (KEY_CD,RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,VAL)
  VALUES ('activationType',2171,2061,'P')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (KEY_CD,RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,VAL)
  VALUES ('mandatoryRoute',2172,2061,'false')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (KEY_CD,RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,VAL)
  VALUES ('finalApproval',2173,2061,'false')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (KEY_CD,RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,VAL)
  VALUES ('ruleSelector',2174,2061,'Template')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (KEY_CD,RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,VAL)
  VALUES ('contentFragment',2175,2063,'<start name="Initiated"><activationType>P</activationType><mandatoryRoute>false</mandatoryRoute><finalApproval>false</finalApproval></start>')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (KEY_CD,RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,VAL)
  VALUES ('activationType',2176,2063,'P')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (KEY_CD,RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,VAL)
  VALUES ('mandatoryRoute',2177,2063,'false')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (KEY_CD,RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,VAL)
  VALUES ('finalApproval',2178,2063,'false')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (KEY_CD,RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,VAL)
  VALUES ('ruleSelector',2179,2063,'Template')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (KEY_CD,RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,VAL)
  VALUES ('contentFragment',2180,2065,'<start name="Initiated"><activationType>P</activationType><mandatoryRoute>false</mandatoryRoute><finalApproval>false</finalApproval></start>')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (KEY_CD,RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,VAL)
  VALUES ('activationType',2181,2065,'P')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (KEY_CD,RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,VAL)
  VALUES ('mandatoryRoute',2182,2065,'false')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (KEY_CD,RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,VAL)
  VALUES ('finalApproval',2183,2065,'false')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (KEY_CD,RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,VAL)
  VALUES ('ruleSelector',2184,2065,'Template')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (KEY_CD,RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,VAL)
  VALUES ('contentFragment',2185,2067,'<start name="Initiated"><activationType>P</activationType><mandatoryRoute>false</mandatoryRoute><finalApproval>false</finalApproval></start>')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (KEY_CD,RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,VAL)
  VALUES ('activationType',2186,2067,'P')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (KEY_CD,RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,VAL)
  VALUES ('mandatoryRoute',2187,2067,'false')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (KEY_CD,RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,VAL)
  VALUES ('finalApproval',2188,2067,'false')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (KEY_CD,RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,VAL)
  VALUES ('ruleSelector',2189,2067,'Template')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (KEY_CD,RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,VAL)
  VALUES ('contentFragment',2380,2840,'<start name="PreRoute"><activationType>S</activationType><mandatoryRoute>false</mandatoryRoute><finalApproval>false</finalApproval></start>')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (KEY_CD,RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,VAL)
  VALUES ('activationType',2381,2840,'S')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (KEY_CD,RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,VAL)
  VALUES ('mandatoryRoute',2382,2840,'false')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (KEY_CD,RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,VAL)
  VALUES ('finalApproval',2383,2840,'false')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (KEY_CD,RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,VAL)
  VALUES ('ruleSelector',2384,2840,'Template')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (KEY_CD,RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,VAL)
  VALUES ('contentFragment',2430,2898,'<start name="AdHoc"/>')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (KEY_CD,RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,VAL)
  VALUES ('ruleSelector',2431,2898,'Template')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (KEY_CD,RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,VAL)
  VALUES ('contentFragment',2432,2899,'<role name="RoleType"><qualifierResolverClass>org.kuali.rice.kim.workflow.attribute.KimTypeQualifierResolver</qualifierResolverClass><activationType>P</activationType></role>')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (KEY_CD,RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,VAL)
  VALUES ('qualifierResolverClass',2433,2899,'org.kuali.rice.kim.workflow.attribute.KimTypeQualifierResolver')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (KEY_CD,RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,VAL)
  VALUES ('activationType',2434,2899,'P')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (KEY_CD,RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,VAL)
  VALUES ('ruleSelector',2435,2899,'Template')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (KEY_CD,RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,VAL)
  VALUES ('contentFragment',2436,2901,'<start name="AdHoc"/>')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (KEY_CD,RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,VAL)
  VALUES ('ruleSelector',2437,2901,'Template')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (KEY_CD,RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,VAL)
  VALUES ('contentFragment',2438,2902,'<role name="GroupType"><qualifierResolverClass>org.kuali.rice.kim.workflow.attribute.KimTypeQualifierResolver</qualifierResolverClass><activationType>P</activationType></role>')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (KEY_CD,RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,VAL)
  VALUES ('qualifierResolverClass',2439,2902,'org.kuali.rice.kim.workflow.attribute.KimTypeQualifierResolver')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (KEY_CD,RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,VAL)
  VALUES ('activationType',2440,2902,'P')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (KEY_CD,RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,VAL)
  VALUES ('ruleSelector',2441,2902,'Template')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (KEY_CD,RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,VAL)
  VALUES ('contentFragment',2442,2904,'<start name="AdHoc"/>')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (KEY_CD,RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,VAL)
  VALUES ('ruleSelector',2443,2904,'Template')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (KEY_CD,RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,VAL)
  VALUES ('contentFragment',2444,2905,'<role name="GroupType"><qualifierResolverClass>org.kuali.rice.kim.workflow.attribute.KimTypeQualifierResolver</qualifierResolverClass><activationType>P</activationType></role>')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (KEY_CD,RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,VAL)
  VALUES ('qualifierResolverClass',2445,2905,'org.kuali.rice.kim.workflow.attribute.KimTypeQualifierResolver')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (KEY_CD,RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,VAL)
  VALUES ('activationType',2446,2905,'P')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (KEY_CD,RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,VAL)
  VALUES ('ruleSelector',2447,2905,'Template')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (KEY_CD,RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,VAL)
  VALUES ('contentFragment',2448,2906,'<role name="RoleType"><qualifierResolverClass>org.kuali.rice.kim.workflow.attribute.KimTypeQualifierResolver</qualifierResolverClass><activationType>P</activationType></role>')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (KEY_CD,RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,VAL)
  VALUES ('qualifierResolverClass',2449,2906,'org.kuali.rice.kim.workflow.attribute.KimTypeQualifierResolver')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (KEY_CD,RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,VAL)
  VALUES ('activationType',2450,2906,'P')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (KEY_CD,RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,VAL)
  VALUES ('ruleSelector',2451,2906,'Template')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (KEY_CD,RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,VAL)
  VALUES ('contentFragment',2452,2908,'<start name="AdHoc"/>')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (KEY_CD,RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,VAL)
  VALUES ('ruleSelector',2453,2908,'Template')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (KEY_CD,RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,VAL)
  VALUES ('contentFragment',2454,2910,'<start name="AdHoc"/>')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (KEY_CD,RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,VAL)
  VALUES ('ruleSelector',2455,2910,'Template')
/
--
-- KREW_RTE_NODE_LNK_T
--


INSERT INTO KREW_RTE_NODE_LNK_T (FROM_RTE_NODE_ID,TO_RTE_NODE_ID)
  VALUES (2041,2042)
/
INSERT INTO KREW_RTE_NODE_LNK_T (FROM_RTE_NODE_ID,TO_RTE_NODE_ID)
  VALUES (2042,2043)
/
INSERT INTO KREW_RTE_NODE_LNK_T (FROM_RTE_NODE_ID,TO_RTE_NODE_ID)
  VALUES (2898,2899)
/
INSERT INTO KREW_RTE_NODE_LNK_T (FROM_RTE_NODE_ID,TO_RTE_NODE_ID)
  VALUES (2901,2902)
/
INSERT INTO KREW_RTE_NODE_LNK_T (FROM_RTE_NODE_ID,TO_RTE_NODE_ID)
  VALUES (2904,2905)
/
INSERT INTO KREW_RTE_NODE_LNK_T (FROM_RTE_NODE_ID,TO_RTE_NODE_ID)
  VALUES (2905,2906)
/
--
-- KREW_RTE_NODE_T
--


INSERT INTO KREW_RTE_NODE_T (ACTVN_TYP,DOC_TYP_ID,FNL_APRVR_IND,GRP_ID,MNDTRY_RTE_IND,NM,RTE_NODE_ID,TYP,VER_NBR)
  VALUES ('S',2011,0,'1',0,'placeholder',2004,'org.kuali.rice.kew.engine.node.InitialNode',2)
/
INSERT INTO KREW_RTE_NODE_T (ACTVN_TYP,DOC_TYP_ID,FNL_APRVR_IND,GRP_ID,MNDTRY_RTE_IND,NM,RTE_NODE_ID,TYP,VER_NBR)
  VALUES ('S',2012,0,'1',0,'placeholder',2006,'org.kuali.rice.kew.engine.node.InitialNode',2)
/
INSERT INTO KREW_RTE_NODE_T (ACTVN_TYP,DOC_TYP_ID,FNL_APRVR_IND,GRP_ID,MNDTRY_RTE_IND,NM,RTE_NODE_ID,TYP,VER_NBR)
  VALUES ('S',2023,1,'1',0,'Adhoc Routing',2039,'org.kuali.rice.kew.engine.node.InitialNode',1)
/
INSERT INTO KREW_RTE_NODE_T (ACTVN_TYP,DOC_TYP_ID,FNL_APRVR_IND,GRP_ID,MNDTRY_RTE_IND,NM,RTE_NODE_ID,TYP,VER_NBR)
  VALUES ('S',2024,0,'1',0,'Initiated',2041,'org.kuali.rice.kew.engine.node.InitialNode',1)
/
INSERT INTO KREW_RTE_NODE_T (ACTVN_TYP,DOC_TYP_ID,FNL_APRVR_IND,GRP_ID,MNDTRY_RTE_IND,NM,RTE_MTHD_CD,RTE_MTHD_NM,RTE_NODE_ID,TYP,VER_NBR)
  VALUES ('S',2024,0,'1',0,'ReviewersNode','FR','ReviewersRouting',2042,'org.kuali.rice.kew.engine.node.RequestsNode',1)
/
INSERT INTO KREW_RTE_NODE_T (ACTVN_TYP,DOC_TYP_ID,FNL_APRVR_IND,GRP_ID,MNDTRY_RTE_IND,NM,RTE_MTHD_CD,RTE_MTHD_NM,RTE_NODE_ID,TYP,VER_NBR)
  VALUES ('S',2024,0,'1',0,'RequestsNode','FR','NotificationRouting',2043,'org.kuali.rice.kew.engine.node.RequestsNode',1)
/
INSERT INTO KREW_RTE_NODE_T (ACTVN_TYP,DOC_TYP_ID,FNL_APRVR_IND,GRP_ID,MNDTRY_RTE_IND,NM,RTE_NODE_ID,TYP,VER_NBR)
  VALUES ('P',2031,0,'1',0,'Initiated',2061,'org.kuali.rice.kew.engine.node.InitialNode',1)
/
INSERT INTO KREW_RTE_NODE_T (ACTVN_TYP,DOC_TYP_ID,FNL_APRVR_IND,GRP_ID,MNDTRY_RTE_IND,NM,RTE_NODE_ID,TYP,VER_NBR)
  VALUES ('P',2032,0,'1',0,'Initiated',2063,'org.kuali.rice.kew.engine.node.InitialNode',1)
/
INSERT INTO KREW_RTE_NODE_T (ACTVN_TYP,DOC_TYP_ID,FNL_APRVR_IND,GRP_ID,MNDTRY_RTE_IND,NM,RTE_NODE_ID,TYP,VER_NBR)
  VALUES ('P',2033,0,'1',0,'Initiated',2065,'org.kuali.rice.kew.engine.node.InitialNode',1)
/
INSERT INTO KREW_RTE_NODE_T (ACTVN_TYP,DOC_TYP_ID,FNL_APRVR_IND,GRP_ID,MNDTRY_RTE_IND,NM,RTE_NODE_ID,TYP,VER_NBR)
  VALUES ('P',2034,0,'1',0,'Initiated',2067,'org.kuali.rice.kew.engine.node.InitialNode',1)
/
INSERT INTO KREW_RTE_NODE_T (ACTVN_TYP,DOC_TYP_ID,FNL_APRVR_IND,MNDTRY_RTE_IND,NM,RTE_NODE_ID,TYP,VER_NBR)
  VALUES ('S',2680,0,0,'PreRoute',2840,'org.kuali.rice.kew.engine.node.InitialNode',2)
/
INSERT INTO KREW_RTE_NODE_T (ACTVN_TYP,DOC_TYP_ID,FNL_APRVR_IND,MNDTRY_RTE_IND,NM,RTE_NODE_ID,TYP,VER_NBR)
  VALUES ('S',2995,0,0,'AdHoc',2898,'org.kuali.rice.kew.engine.node.InitialNode',1)
/
INSERT INTO KREW_RTE_NODE_T (ACTVN_TYP,DOC_TYP_ID,FNL_APRVR_IND,MNDTRY_RTE_IND,NM,RTE_MTHD_CD,RTE_MTHD_NM,RTE_NODE_ID,TYP,VER_NBR)
  VALUES ('P',2995,0,0,'RoleType','RM','org.kuali.rice.kew.role.RoleRouteModule',2899,'org.kuali.rice.kew.engine.node.RoleNode',1)
/
INSERT INTO KREW_RTE_NODE_T (ACTVN_TYP,DOC_TYP_ID,FNL_APRVR_IND,MNDTRY_RTE_IND,NM,RTE_NODE_ID,TYP,VER_NBR)
  VALUES ('S',2996,0,0,'AdHoc',2901,'org.kuali.rice.kew.engine.node.InitialNode',1)
/
INSERT INTO KREW_RTE_NODE_T (ACTVN_TYP,DOC_TYP_ID,FNL_APRVR_IND,MNDTRY_RTE_IND,NM,RTE_MTHD_CD,RTE_MTHD_NM,RTE_NODE_ID,TYP,VER_NBR)
  VALUES ('P',2996,0,0,'GroupType','RM','org.kuali.rice.kew.role.RoleRouteModule',2902,'org.kuali.rice.kew.engine.node.RoleNode',1)
/
INSERT INTO KREW_RTE_NODE_T (ACTVN_TYP,DOC_TYP_ID,FNL_APRVR_IND,MNDTRY_RTE_IND,NM,RTE_NODE_ID,TYP,VER_NBR)
  VALUES ('S',2997,0,0,'AdHoc',2904,'org.kuali.rice.kew.engine.node.InitialNode',1)
/
INSERT INTO KREW_RTE_NODE_T (ACTVN_TYP,DOC_TYP_ID,FNL_APRVR_IND,MNDTRY_RTE_IND,NM,RTE_MTHD_CD,RTE_MTHD_NM,RTE_NODE_ID,TYP,VER_NBR)
  VALUES ('P',2997,0,0,'GroupType','RM','org.kuali.rice.kew.role.RoleRouteModule',2905,'org.kuali.rice.kew.engine.node.RoleNode',1)
/
INSERT INTO KREW_RTE_NODE_T (ACTVN_TYP,DOC_TYP_ID,FNL_APRVR_IND,MNDTRY_RTE_IND,NM,RTE_MTHD_CD,RTE_MTHD_NM,RTE_NODE_ID,TYP,VER_NBR)
  VALUES ('P',2997,0,0,'RoleType','RM','org.kuali.rice.kew.role.RoleRouteModule',2906,'org.kuali.rice.kew.engine.node.RoleNode',1)
/
INSERT INTO KREW_RTE_NODE_T (ACTVN_TYP,DOC_TYP_ID,FNL_APRVR_IND,MNDTRY_RTE_IND,NM,RTE_NODE_ID,TYP,VER_NBR)
  VALUES ('S',2998,0,0,'AdHoc',2908,'org.kuali.rice.kew.engine.node.InitialNode',1)
/
INSERT INTO KREW_RTE_NODE_T (ACTVN_TYP,DOC_TYP_ID,FNL_APRVR_IND,MNDTRY_RTE_IND,NM,RTE_NODE_ID,TYP,VER_NBR)
  VALUES ('S',2999,0,0,'AdHoc',2910,'org.kuali.rice.kew.engine.node.InitialNode',1)
/
--
-- KREW_RULE_ATTR_T
--


INSERT INTO KREW_RULE_ATTR_T (CLS_NM,DESC_TXT,LBL,NM,OBJ_ID,RULE_ATTR_ID,RULE_ATTR_TYP_CD,VER_NBR)
  VALUES ('org.kuali.rice.kew.rule.RuleRoutingAttribute','Rule Routing Attribute','Rule Routing Attribute','RuleRoutingAttribute','6166CBA1B94F644DE0404F8189D86C09',1000,'RuleAttribute',3)
/
INSERT INTO KREW_RULE_ATTR_T (CLS_NM,DESC_TXT,LBL,NM,OBJ_ID,RULE_ATTR_ID,RULE_ATTR_TYP_CD,VER_NBR)
  VALUES ('org.kuali.rice.ken.kew.NotificationCustomActionListAttribute','Notification Action List Attribute','Notification  Action List Attribute','NotificationCustomActionListAttribute','6166CBA1B952644DE0404F8189D86C09',1003,'ActionListAttribute',2)
/
INSERT INTO KREW_RULE_ATTR_T (CLS_NM,DESC_TXT,LBL,NM,OBJ_ID,RULE_ATTR_ID,RULE_ATTR_TYP_CD,VER_NBR,XML)
  VALUES ('org.kuali.rice.kew.docsearch.xml.StandardGenericXMLSearchableAttribute','The associated channel that this message was sent on.','Notification Channel','NotificationChannelSearchableAttribute','6166CBA1B953644DE0404F8189D86C09',1004,'SearchableXmlAttribute',1,'<searchingConfig>
              <fieldDef name="notificationChannel" title="Notification Channel">
                  <display>
                      <type>text</type>
                  </display>
                  <validation required="false"/>
                  <fieldEvaluation>
                      <xpathexpression>string(/documentContent/applicationContent/notification/channel/name)</xpathexpression>
                  </fieldEvaluation>
              </fieldDef>
           </searchingConfig>')
/
INSERT INTO KREW_RULE_ATTR_T (CLS_NM,DESC_TXT,LBL,NM,OBJ_ID,RULE_ATTR_ID,RULE_ATTR_TYP_CD,VER_NBR,XML)
  VALUES ('org.kuali.rice.kew.docsearch.xml.StandardGenericXMLSearchableAttribute','The priority of this notification.','Notification Priority','NotificationPrioritySearchableAttribute','6166CBA1B954644DE0404F8189D86C09',1005,'SearchableXmlAttribute',1,'<searchingConfig>
              <fieldDef name="notificationPriority" title="Notification Priority">
                  <display>
                      <type>text</type>
                  </display>
                  <validation required="false"/>
                  <fieldEvaluation>
                      <xpathexpression>string(/documentContent/applicationContent/notification/priority/name)</xpathexpression>
                  </fieldEvaluation>
              </fieldDef>
           </searchingConfig>')
/
INSERT INTO KREW_RULE_ATTR_T (CLS_NM,DESC_TXT,LBL,NM,OBJ_ID,RULE_ATTR_ID,RULE_ATTR_TYP_CD,VER_NBR,XML)
  VALUES ('org.kuali.rice.kew.docsearch.xml.StandardGenericXMLSearchableAttribute','The content type of this notification.','Notification Content Type','NotificationContentTypeSearchableAttribute','6166CBA1B955644DE0404F8189D86C09',1006,'SearchableXmlAttribute',1,'<searchingConfig>
              <fieldDef name="notificationContentType" title="Notification Content Type">
                  <display>
                      <type>text</type>
                  </display>
                  <validation required="false"/>
                  <fieldEvaluation>
                      <xpathexpression>string(/documentContent/applicationContent/notification/contentType/name)</xpathexpression>
                  </fieldEvaluation>
              </fieldDef>
           </searchingConfig>')
/
INSERT INTO KREW_RULE_ATTR_T (CLS_NM,DESC_TXT,LBL,NM,OBJ_ID,RULE_ATTR_ID,RULE_ATTR_TYP_CD,VER_NBR,XML)
  VALUES ('org.kuali.rice.kew.docsearch.xml.StandardGenericXMLSearchableAttribute','The producer of this notification.','Notification Producer','NotificationProducerSearchableAttribute','6166CBA1B956644DE0404F8189D86C09',1007,'SearchableXmlAttribute',1,'<searchingConfig>
              <fieldDef name="notificationProducer" title="Notification Producer">
                  <display>
                      <type>text</type>
                  </display>
                  <validation required="false"/>
                  <fieldEvaluation>
                      <xpathexpression>string(/documentContent/applicationContent/notification/producer/name)</xpathexpression>
                  </fieldEvaluation>
              </fieldDef>
           </searchingConfig>')
/
INSERT INTO KREW_RULE_ATTR_T (CLS_NM,DESC_TXT,LBL,NM,OBJ_ID,RULE_ATTR_ID,RULE_ATTR_TYP_CD,VER_NBR,XML)
  VALUES ('org.kuali.rice.kew.docsearch.xml.StandardGenericXMLSearchableAttribute','Those who are receiving this notification.','Notification Recipient','NotificationRecipientsSearchableAttribute','6166CBA1B957644DE0404F8189D86C09',1008,'SearchableXmlAttribute',1,'<searchingConfig>
              <fieldDef name="notificationRecipients" title="Notification Recipients">
                  <display>
                      <type>text</type>
                  </display>
                  <validation required="false"/>
                  <fieldEvaluation>
                      <xpathexpression>string(/documentContent/applicationContent/notification//recipients)</xpathexpression>
                  </fieldEvaluation>
              </fieldDef>
           </searchingConfig>')
/
INSERT INTO KREW_RULE_ATTR_T (CLS_NM,DESC_TXT,LBL,NM,OBJ_ID,RULE_ATTR_ID,RULE_ATTR_TYP_CD,VER_NBR,XML)
  VALUES ('org.kuali.rice.kew.docsearch.xml.StandardGenericXMLSearchableAttribute','Those who this notification is being sent on behalf of.','Notification Senders','NotificationSendersSearchableAttribute','6166CBA1B958644DE0404F8189D86C09',1009,'SearchableXmlAttribute',1,'<searchingConfig>
              <fieldDef name="notificationSenders" title="Notification Senders">
                  <display>
                      <type>text</type>
                  </display>
                  <validation required="false"/>
                  <fieldEvaluation>
                      <xpathexpression>string(/documentContent/applicationContent/notification//senders)</xpathexpression>
                  </fieldEvaluation>
              </fieldDef>
           </searchingConfig>')
/
INSERT INTO KREW_RULE_ATTR_T (CLS_NM,DESC_TXT,LBL,NM,OBJ_ID,RULE_ATTR_ID,RULE_ATTR_TYP_CD,VER_NBR)
  VALUES ('org.kuali.rice.ken.kew.ChannelReviewerRoleAttribute','Channel Reviewer Role Attribute','Channel Reviewer Role Attribute','ChannelReviewerRoleAttribute','6166CBA1B959644DE0404F8189D86C09',1010,'RuleAttribute',1)
/
INSERT INTO KREW_RULE_ATTR_T (CLS_NM,DESC_TXT,LBL,NM,OBJ_ID,RULE_ATTR_ID,RULE_ATTR_TYP_CD,VER_NBR,XML)
  VALUES ('org.kuali.rice.kew.docsearch.xml.StandardGenericXMLSearchableAttribute','XML Searchable attribute','XML Searchable attribute','XMLSearchableAttribute','6166CBA1B9C5644DE0404F8189D86C09',1233,'SearchableXmlAttribute',1,'<searchingConfig>
        <fieldDef name="givenname" title="First name">
          <display>
            <type>text</type>
          </display>
          <searchDefinition autoWildcardLocation="prefixonly"/>
          <validation required="true">
            <regex>^[a-zA-Z ]+$</regex>
            <message>Invalid first name</message>
          </validation>
          <fieldEvaluation>
            <xpathexpression>//givenname/value</xpathexpression>
          </fieldEvaluation>
        </fieldDef>
      </searchingConfig>')
/
--
-- KREW_RULE_EXT_T
--


INSERT INTO KREW_RULE_EXT_T (RULE_EXT_ID,RULE_ID,RULE_TMPL_ATTR_ID,VER_NBR)
  VALUES (1045,1044,1024,1)
/
INSERT INTO KREW_RULE_EXT_T (RULE_EXT_ID,RULE_ID,RULE_TMPL_ATTR_ID,VER_NBR)
  VALUES (1047,1046,1027,1)
/
INSERT INTO KREW_RULE_EXT_T (RULE_EXT_ID,RULE_ID,RULE_TMPL_ATTR_ID,VER_NBR)
  VALUES (1104,1103,1102,1)
/
INSERT INTO KREW_RULE_EXT_T (RULE_EXT_ID,RULE_ID,RULE_TMPL_ATTR_ID,VER_NBR)
  VALUES (1107,1106,1102,1)
/
--
-- KREW_RULE_EXT_VAL_T
--


INSERT INTO KREW_RULE_EXT_VAL_T (KEY_CD,RULE_EXT_ID,RULE_EXT_VAL_ID,VAL,VER_NBR)
  VALUES ('destination',1047,1048,'las vegas',1)
/
INSERT INTO KREW_RULE_EXT_VAL_T (KEY_CD,RULE_EXT_ID,RULE_EXT_VAL_ID,VAL,VER_NBR)
  VALUES ('campus',1104,1105,'IUB',1)
/
INSERT INTO KREW_RULE_EXT_VAL_T (KEY_CD,RULE_EXT_ID,RULE_EXT_VAL_ID,VAL,VER_NBR)
  VALUES ('campus',1107,1108,'IUPUI',1)
/
--
-- KREW_RULE_RSP_T
--


INSERT INTO KREW_RULE_RSP_T (ACTN_RQST_CD,APPR_PLCY,NM,OBJ_ID,PRIO,RSP_ID,RULE_ID,RULE_RSP_ID,TYP,VER_NBR)
  VALUES ('A','F','org.kuali.rice.ken.kew.ChannelReviewerRoleAttribute!reviewers','6166CBA1BBFC644DE0404F8189D86C09',1,2020,1044,2021,'R',1)
/
INSERT INTO KREW_RULE_RSP_T (ACTN_RQST_CD,APPR_PLCY,NM,OBJ_ID,PRIO,RSP_ID,RULE_ID,RULE_RSP_ID,TYP,VER_NBR)
  VALUES ('A','F','user4','6166CBA1BBFD644DE0404F8189D86C09',1,2022,1046,2023,'F',1)
/
INSERT INTO KREW_RULE_RSP_T (ACTN_RQST_CD,APPR_PLCY,NM,OBJ_ID,PRIO,RSP_ID,RULE_ID,RULE_RSP_ID,TYP,VER_NBR)
  VALUES ('A','F','edu.sampleu.travel.workflow.EmployeeAttribute!employee','6166CBA1BBFE644DE0404F8189D86C09',1,2024,1049,2025,'R',1)
/
INSERT INTO KREW_RULE_RSP_T (ACTN_RQST_CD,APPR_PLCY,NM,OBJ_ID,PRIO,RSP_ID,RULE_ID,RULE_RSP_ID,TYP,VER_NBR)
  VALUES ('A','F','edu.sampleu.travel.workflow.EmployeeAttribute!supervisr','6166CBA1BBFF644DE0404F8189D86C09',1,2026,1050,2027,'R',1)
/
INSERT INTO KREW_RULE_RSP_T (ACTN_RQST_CD,APPR_PLCY,NM,OBJ_ID,PRIO,RSP_ID,RULE_ID,RULE_RSP_ID,TYP,VER_NBR)
  VALUES ('K','F','edu.sampleu.travel.workflow.EmployeeAttribute!director','6166CBA1BC00644DE0404F8189D86C09',1,2028,1051,2029,'R',1)
/
INSERT INTO KREW_RULE_RSP_T (ACTN_RQST_CD,APPR_PLCY,NM,OBJ_ID,PRIO,RSP_ID,RULE_ID,RULE_RSP_ID,TYP,VER_NBR)
  VALUES ('A','F','edu.sampleu.travel.workflow.AccountAttribute!FO','6166CBA1BC01644DE0404F8189D86C09',1,2030,1052,2031,'R',1)
/
INSERT INTO KREW_RULE_RSP_T (ACTN_RQST_CD,APPR_PLCY,NM,OBJ_ID,PRIO,RSP_ID,RULE_ID,RULE_RSP_ID,TYP,VER_NBR)
  VALUES ('A','F','2202','6166CBA1BC02644DE0404F8189D86C09',1,2040,1103,2041,'G',1)
/
INSERT INTO KREW_RULE_RSP_T (ACTN_RQST_CD,APPR_PLCY,NM,OBJ_ID,PRIO,RSP_ID,RULE_ID,RULE_RSP_ID,TYP,VER_NBR)
  VALUES ('A','F','2203','6166CBA1BC03644DE0404F8189D86C09',1,2042,1106,2043,'G',1)
/
INSERT INTO KREW_RULE_RSP_T (ACTN_RQST_CD,APPR_PLCY,NM,OBJ_ID,PRIO,RSP_ID,RULE_ID,RULE_RSP_ID,TYP,VER_NBR)
  VALUES ('A','F','9997','B1FCE360-EA7A-C2B8-9D70-88C39A982094',1,2063,1642,2064,'G',1)
/
--
-- KREW_RULE_T
--


INSERT INTO KREW_RULE_T (ACTVN_DT,ACTV_IND,CUR_IND,DACTVN_DT,DLGN_IND,DOC_TYP_NM,FRC_ACTN,FRM_DT,NM,OBJ_ID,RULE_BASE_VAL_DESC,RULE_ID,RULE_TMPL_ID,RULE_VER_NBR,TMPL_RULE_IND,TO_DT,VER_NBR)
  VALUES (TO_DATE( '20080801155902', 'YYYYMMDDHH24MISS' ),1,1,TO_DATE( '21000101000000', 'YYYYMMDDHH24MISS' ),0,'SendNotificationRequest',1,TO_DATE( '20080801155902', 'YYYYMMDDHH24MISS' ),'SendNotificationRequest.Reviewers','6166CBA1BBE9644DE0404F8189D86C09','Notification Request Reviewers',1044,1023,0,0,TO_DATE( '21000101000000', 'YYYYMMDDHH24MISS' ),0)
/
--
-- KREW_RULE_TMPL_ATTR_T
--


INSERT INTO KREW_RULE_TMPL_ATTR_T (ACTV_IND,DSPL_ORD,OBJ_ID,REQ_IND,RULE_ATTR_ID,RULE_TMPL_ATTR_ID,RULE_TMPL_ID,VER_NBR)
  VALUES (1,2,'6166CBA1BB16644DE0404F8189D86C09',1,1010,1024,1023,2)
/
INSERT INTO KREW_RULE_TMPL_ATTR_T (ACTV_IND,DSPL_ORD,OBJ_ID,REQ_IND,RULE_ATTR_ID,RULE_TMPL_ATTR_ID,RULE_TMPL_ID,VER_NBR)
  VALUES (1,3,'6166CBA1BB17644DE0404F8189D86C09',1,1011,1027,1026,8)
/
INSERT INTO KREW_RULE_TMPL_ATTR_T (ACTV_IND,DSPL_ORD,OBJ_ID,REQ_IND,RULE_ATTR_ID,RULE_TMPL_ATTR_ID,RULE_TMPL_ID,VER_NBR)
  VALUES (1,4,'6166CBA1BB18644DE0404F8189D86C09',1,1012,1029,1028,2)
/
INSERT INTO KREW_RULE_TMPL_ATTR_T (ACTV_IND,DSPL_ORD,OBJ_ID,REQ_IND,RULE_ATTR_ID,RULE_TMPL_ATTR_ID,RULE_TMPL_ID,VER_NBR)
  VALUES (1,5,'6166CBA1BB19644DE0404F8189D86C09',1,1012,1031,1030,2)
/
INSERT INTO KREW_RULE_TMPL_ATTR_T (ACTV_IND,DSPL_ORD,OBJ_ID,REQ_IND,RULE_ATTR_ID,RULE_TMPL_ATTR_ID,RULE_TMPL_ID,VER_NBR)
  VALUES (1,6,'6166CBA1BB1A644DE0404F8189D86C09',1,1013,1033,1032,2)
/
INSERT INTO KREW_RULE_TMPL_ATTR_T (ACTV_IND,DSPL_ORD,OBJ_ID,REQ_IND,RULE_ATTR_ID,RULE_TMPL_ATTR_ID,RULE_TMPL_ID,VER_NBR)
  VALUES (1,0,'6166CBA1BB1C644DE0404F8189D86C09',0,1100,1102,1101,2)
/
INSERT INTO KREW_RULE_TMPL_ATTR_T (ACTV_IND,DSPL_ORD,OBJ_ID,REQ_IND,RULE_ATTR_ID,RULE_TMPL_ATTR_ID,RULE_TMPL_ID,VER_NBR)
  VALUES (1,3,'6166CBA1BB20644DE0404F8189D86C09',1,1000,1321,1320,2)
/
--
-- KREW_RULE_TMPL_T
--


INSERT INTO KREW_RULE_TMPL_T (NM,OBJ_ID,RULE_TMPL_DESC,RULE_TMPL_ID,VER_NBR)
  VALUES ('WorkflowDocumentDelegationTemplate','6166CBA1BA86644DE0404F8189D86C09','WorkflowDocumentDelegationTemplate',1015,2)
/
INSERT INTO KREW_RULE_TMPL_T (DLGN_RULE_TMPL_ID,NM,OBJ_ID,RULE_TMPL_DESC,RULE_TMPL_ID,VER_NBR)
  VALUES (1015,'WorkflowDocumentTemplate','6166CBA1BA87644DE0404F8189D86C09','Workflow Document Template',1016,4)
/
INSERT INTO KREW_RULE_TMPL_T (NM,OBJ_ID,RULE_TMPL_DESC,RULE_TMPL_ID,VER_NBR)
  VALUES ('ReviewersRouting','6166CBA1BA8C644DE0404F8189D86C09','Routes to channel reviewers',1023,2)
/
INSERT INTO KREW_RULE_TMPL_T (NM,OBJ_ID,RULE_TMPL_DESC,RULE_TMPL_ID,VER_NBR)
  VALUES ('NotificationRouting','6166CBA1BA8D644DE0404F8189D86C09','The standard rule template for sending notification messages.',1025,2)
/
INSERT INTO KREW_RULE_TMPL_T (NM,OBJ_ID,RULE_TMPL_DESC,RULE_TMPL_ID,VER_NBR)
  VALUES ('RuleRoutingTemplate','6166CBA1BA96644DE0404F8189D86C09','RuleRoutingTemplate',1320,2)
/
--
-- KRIM_ADDR_TYP_T
--


INSERT INTO KRIM_ADDR_TYP_T (ACTV_IND,ADDR_TYP_CD,DISPLAY_SORT_CD,LAST_UPDT_DT,NM,OBJ_ID,VER_NBR)
  VALUES ('Y','HM','b',TO_DATE( '20081113140629', 'YYYYMMDDHH24MISS' ),'Home','5B97C50B03706110E0404F8189D85213',1)
/
INSERT INTO KRIM_ADDR_TYP_T (ACTV_IND,ADDR_TYP_CD,DISPLAY_SORT_CD,LAST_UPDT_DT,NM,OBJ_ID,VER_NBR)
  VALUES ('Y','OTH','c',TO_DATE( '20081113140629', 'YYYYMMDDHH24MISS' ),'Other','5B97C50B03716110E0404F8189D85213',1)
/
INSERT INTO KRIM_ADDR_TYP_T (ACTV_IND,ADDR_TYP_CD,DISPLAY_SORT_CD,LAST_UPDT_DT,NM,OBJ_ID,VER_NBR)
  VALUES ('Y','WRK','a',TO_DATE( '20081113140630', 'YYYYMMDDHH24MISS' ),'Work','5B97C50B03726110E0404F8189D85213',1)
/
--
-- KRIM_AFLTN_TYP_T
--


INSERT INTO KRIM_AFLTN_TYP_T (ACTV_IND,AFLTN_TYP_CD,DISPLAY_SORT_CD,EMP_AFLTN_TYP_IND,LAST_UPDT_DT,NM,OBJ_ID,VER_NBR)
  VALUES ('Y','AFLT','d','N',TO_DATE( '20081113140630', 'YYYYMMDDHH24MISS' ),'Affiliate','5B97C50B03736110E0404F8189D85213',1)
/
INSERT INTO KRIM_AFLTN_TYP_T (ACTV_IND,AFLTN_TYP_CD,DISPLAY_SORT_CD,EMP_AFLTN_TYP_IND,LAST_UPDT_DT,NM,OBJ_ID,VER_NBR)
  VALUES ('Y','FCLTY','b','Y',TO_DATE( '20081113140630', 'YYYYMMDDHH24MISS' ),'Faculty','5B97C50B03746110E0404F8189D85213',1)
/
INSERT INTO KRIM_AFLTN_TYP_T (ACTV_IND,AFLTN_TYP_CD,DISPLAY_SORT_CD,EMP_AFLTN_TYP_IND,LAST_UPDT_DT,NM,OBJ_ID,VER_NBR)
  VALUES ('Y','STAFF','c','Y',TO_DATE( '20081113140630', 'YYYYMMDDHH24MISS' ),'Staff','5B97C50B03756110E0404F8189D85213',1)
/
INSERT INTO KRIM_AFLTN_TYP_T (ACTV_IND,AFLTN_TYP_CD,DISPLAY_SORT_CD,EMP_AFLTN_TYP_IND,LAST_UPDT_DT,NM,OBJ_ID,VER_NBR)
  VALUES ('Y','STDNT','a','N',TO_DATE( '20081113140630', 'YYYYMMDDHH24MISS' ),'Student','5B97C50B03766110E0404F8189D85213',1)
/
--
-- KRIM_ATTR_DEFN_T
--


INSERT INTO KRIM_ATTR_DEFN_T (ACTV_IND,CMPNT_NM,KIM_ATTR_DEFN_ID,NM,NMSPC_CD,OBJ_ID,VER_NBR)
  VALUES ('Y','org.kuali.rice.kim.bo.impl.KimAttributes','1','beanName','KR-SYS','5ADF18B6D4887954E0404F8189D85002',1)
/
INSERT INTO KRIM_ATTR_DEFN_T (ACTV_IND,CMPNT_NM,KIM_ATTR_DEFN_ID,NM,NMSPC_CD,OBJ_ID,VER_NBR)
  VALUES ('Y','org.kuali.rice.kim.bo.impl.KimAttributes','10','editMode','KR-NS','5ADF18B6D4917954E0404F8189D85002',1)
/
INSERT INTO KRIM_ATTR_DEFN_T (ACTV_IND,CMPNT_NM,KIM_ATTR_DEFN_ID,NM,NMSPC_CD,OBJ_ID,VER_NBR)
  VALUES ('Y','org.kuali.rice.kim.bo.impl.KimAttributes','11','parameterName','KR-NS','5ADF18B6D4927954E0404F8189D85002',1)
/
INSERT INTO KRIM_ATTR_DEFN_T (ACTV_IND,CMPNT_NM,KIM_ATTR_DEFN_ID,NM,NMSPC_CD,OBJ_ID,VER_NBR)
  VALUES ('Y','org.kuali.rice.kim.bo.impl.KimAttributes','12','campusCode','KR-NS','5ADF18B6D4937954E0404F8189D85002',1)
/
INSERT INTO KRIM_ATTR_DEFN_T (ACTV_IND,CMPNT_NM,KIM_ATTR_DEFN_ID,NM,NMSPC_CD,OBJ_ID,VER_NBR)
  VALUES ('Y','org.kuali.rice.kim.bo.impl.KimAttributes','13','documentTypeName','KR-WKFLW','5ADF18B6D4947954E0404F8189D85002',1)
/
INSERT INTO KRIM_ATTR_DEFN_T (ACTV_IND,CMPNT_NM,KIM_ATTR_DEFN_ID,NM,NMSPC_CD,OBJ_ID,VER_NBR)
  VALUES ('Y','org.kuali.rice.kim.bo.impl.KimAttributes','14','actionRequestCd','KR-WKFLW','5ADF18B6D4957954E0404F8189D85002',1)
/
INSERT INTO KRIM_ATTR_DEFN_T (ACTV_IND,CMPNT_NM,KIM_ATTR_DEFN_ID,NM,NMSPC_CD,OBJ_ID,VER_NBR)
  VALUES ('Y','org.kuali.rice.kim.bo.impl.KimAttributes','15','routeStatusCode','KR-WKFLW','5ADF18B6D4967954E0404F8189D85002',1)
/
INSERT INTO KRIM_ATTR_DEFN_T (ACTV_IND,CMPNT_NM,KIM_ATTR_DEFN_ID,NM,NMSPC_CD,OBJ_ID,VER_NBR)
  VALUES ('Y','org.kuali.rice.kim.bo.impl.KimAttributes','16','routeNodeName','KR-WKFLW','5ADF18B6D4977954E0404F8189D85002',1)
/
INSERT INTO KRIM_ATTR_DEFN_T (ACTV_IND,CMPNT_NM,KIM_ATTR_DEFN_ID,NM,NMSPC_CD,OBJ_ID,VER_NBR)
  VALUES ('Y','org.kuali.rice.kim.bo.impl.KimAttributes','18','roleName','KR-IDM','5ADF18B6D4997954E0404F8189D85002',1)
/
INSERT INTO KRIM_ATTR_DEFN_T (ACTV_IND,CMPNT_NM,KIM_ATTR_DEFN_ID,NM,NMSPC_CD,OBJ_ID,VER_NBR)
  VALUES ('Y','org.kuali.rice.kim.bo.impl.KimAttributes','19','permissionName','KR-IDM','5ADF18B6D49A7954E0404F8189D85002',1)
/
INSERT INTO KRIM_ATTR_DEFN_T (ACTV_IND,CMPNT_NM,KIM_ATTR_DEFN_ID,NM,NMSPC_CD,OBJ_ID,VER_NBR)
  VALUES ('Y','org.kuali.rice.kim.bo.impl.KimAttributes','2','actionClass','KR-SYS','5ADF18B6D4897954E0404F8189D85002',1)
/
INSERT INTO KRIM_ATTR_DEFN_T (ACTV_IND,CMPNT_NM,KIM_ATTR_DEFN_ID,NM,NMSPC_CD,OBJ_ID,VER_NBR)
  VALUES ('Y','org.kuali.rice.kim.bo.impl.KimAttributes','20','responsibilityName','KR-IDM','5ADF18B6D49B7954E0404F8189D85002',1)
/
INSERT INTO KRIM_ATTR_DEFN_T (ACTV_IND,CMPNT_NM,KIM_ATTR_DEFN_ID,NM,NMSPC_CD,OBJ_ID,VER_NBR)
  VALUES ('Y','org.kuali.rice.kim.bo.impl.KimAttributes','21','groupName','KR-IDM','5ADF18B6D49C7954E0404F8189D85002',1)
/
INSERT INTO KRIM_ATTR_DEFN_T (ACTV_IND,CMPNT_NM,KIM_ATTR_DEFN_ID,NM,NMSPC_CD,OBJ_ID,VER_NBR)
  VALUES ('Y','org.kuali.rice.kim.bo.impl.KimAttributes','3','buttonName','KR-SYS','5ADF18B6D48A7954E0404F8189D85002',1)
/
INSERT INTO KRIM_ATTR_DEFN_T (ACTV_IND,CMPNT_NM,KIM_ATTR_DEFN_ID,NM,NMSPC_CD,OBJ_ID,VER_NBR)
  VALUES ('Y','org.kuali.rice.kim.bo.impl.KimAttributes','4','namespaceCode','KR-NS','5ADF18B6D48B7954E0404F8189D85002',1)
/
INSERT INTO KRIM_ATTR_DEFN_T (ACTV_IND,CMPNT_NM,KIM_ATTR_DEFN_ID,NM,NMSPC_CD,OBJ_ID,VER_NBR)
  VALUES ('Y','org.kuali.rice.kim.bo.impl.KimAttributes','40','required','KR-WKFLW','5C4970B2B2DF8277E0404F8189D80B30',1)
/
INSERT INTO KRIM_ATTR_DEFN_T (ACTV_IND,CMPNT_NM,KIM_ATTR_DEFN_ID,NM,NMSPC_CD,OBJ_ID,VER_NBR)
  VALUES ('Y','org.kuali.rice.kim.bo.impl.KimAttributes','41','actionDetailsAtRoleMemberLevel','KR-WKFLW','5C4970B2B2E08277E0404F8189D80B30',1)
/
INSERT INTO KRIM_ATTR_DEFN_T (ACTV_IND,CMPNT_NM,KIM_ATTR_DEFN_ID,NM,NMSPC_CD,OBJ_ID,VER_NBR)
  VALUES ('Y','org.kuali.rice.kim.bo.impl.KimAttributes','42','documentNumber','KR-NS','5C7D997640635002E0404F8189D86F11',1)
/
INSERT INTO KRIM_ATTR_DEFN_T (ACTV_IND,CMPNT_NM,KIM_ATTR_DEFN_ID,NM,NMSPC_CD,OBJ_ID,VER_NBR)
  VALUES ('Y','org.kuali.rice.kim.bo.impl.KimAttributes','44','sectionId','KR-NS','603B735FA6BCFE21E0404F8189D8083B',1)
/
INSERT INTO KRIM_ATTR_DEFN_T (ACTV_IND,CMPNT_NM,KIM_ATTR_DEFN_ID,NM,NMSPC_CD,OBJ_ID,VER_NBR)
  VALUES ('Y','org.kuali.rice.kim.bo.impl.KimAttributes','46','qualifierResolverProvidedIdentifier','KR-IDM','69FA55ACC2EE2598E0404F8189D86880',1)
/
INSERT INTO KRIM_ATTR_DEFN_T (ACTV_IND,CMPNT_NM,KIM_ATTR_DEFN_ID,NM,NMSPC_CD,OBJ_ID,VER_NBR)
  VALUES ('Y','org.kuali.rice.kim.bo.impl.KimAttributes','5','componentName','KR-NS','5ADF18B6D48C7954E0404F8189D85002',1)
/
INSERT INTO KRIM_ATTR_DEFN_T (ACTV_IND,CMPNT_NM,KIM_ATTR_DEFN_ID,NM,NMSPC_CD,OBJ_ID,VER_NBR)
  VALUES ('Y','org.kuali.rice.kim.bo.impl.KimAttributes','6','propertyName','KR-NS','5ADF18B6D48D7954E0404F8189D85002',1)
/
INSERT INTO KRIM_ATTR_DEFN_T (ACTV_IND,CMPNT_NM,KIM_ATTR_DEFN_ID,NM,NMSPC_CD,OBJ_ID,VER_NBR)
  VALUES ('Y','org.kuali.rice.kim.bo.impl.KimAttributes','7','existingRecordsOnly','KR-NS','603B735FA6BAFE21E0404F8189D8083B',1)
/
INSERT INTO KRIM_ATTR_DEFN_T (ACTV_IND,CMPNT_NM,KIM_ATTR_DEFN_ID,NM,NMSPC_CD,OBJ_ID,VER_NBR)
  VALUES ('Y','org.kuali.rice.kim.bo.impl.KimAttributes','8','createdBySelfOnly','KR-NS','5ADF18B6D48F7954E0404F8189D85002',1)
/
INSERT INTO KRIM_ATTR_DEFN_T (ACTV_IND,CMPNT_NM,KIM_ATTR_DEFN_ID,NM,NMSPC_CD,OBJ_ID,VER_NBR)
  VALUES ('Y','org.kuali.rice.kim.bo.impl.KimAttributes','9','attachmentTypeCode','KR-NS','5ADF18B6D4907954E0404F8189D85002',1)
/
--
-- KRIM_EMAIL_TYP_T
--


INSERT INTO KRIM_EMAIL_TYP_T (ACTV_IND,DISPLAY_SORT_CD,EMAIL_TYP_CD,LAST_UPDT_DT,NM,OBJ_ID,VER_NBR)
  VALUES ('Y','b','HM',TO_DATE( '20081113140631', 'YYYYMMDDHH24MISS' ),'Home','5B97C50B03776110E0404F8189D85213',1)
/
INSERT INTO KRIM_EMAIL_TYP_T (ACTV_IND,DISPLAY_SORT_CD,EMAIL_TYP_CD,LAST_UPDT_DT,NM,OBJ_ID,VER_NBR)
  VALUES ('Y','c','OTH',TO_DATE( '20081113140631', 'YYYYMMDDHH24MISS' ),'Other','5B97C50B03786110E0404F8189D85213',1)
/
INSERT INTO KRIM_EMAIL_TYP_T (ACTV_IND,DISPLAY_SORT_CD,EMAIL_TYP_CD,LAST_UPDT_DT,NM,OBJ_ID,VER_NBR)
  VALUES ('Y','a','WRK',TO_DATE( '20081113140631', 'YYYYMMDDHH24MISS' ),'Work','5B97C50B03796110E0404F8189D85213',1)
/
--
-- KRIM_EMP_STAT_T
--


INSERT INTO KRIM_EMP_STAT_T (ACTV_IND,DISPLAY_SORT_CD,EMP_STAT_CD,LAST_UPDT_DT,NM,OBJ_ID,VER_NBR)
  VALUES ('Y','01','A',TO_DATE( '20081113140631', 'YYYYMMDDHH24MISS' ),'Active','5B97C50B037A6110E0404F8189D85213',1)
/
INSERT INTO KRIM_EMP_STAT_T (ACTV_IND,DISPLAY_SORT_CD,EMP_STAT_CD,LAST_UPDT_DT,NM,OBJ_ID,VER_NBR)
  VALUES ('Y','99','D',TO_DATE( '20081113140631', 'YYYYMMDDHH24MISS' ),'Deceased','5B97C50B037B6110E0404F8189D85213',1)
/
INSERT INTO KRIM_EMP_STAT_T (ACTV_IND,DISPLAY_SORT_CD,EMP_STAT_CD,LAST_UPDT_DT,NM,OBJ_ID,VER_NBR)
  VALUES ('Y','04','L',TO_DATE( '20081113140632', 'YYYYMMDDHH24MISS' ),'On Non-Pay Leave','5B97C50B037C6110E0404F8189D85213',1)
/
INSERT INTO KRIM_EMP_STAT_T (ACTV_IND,DISPLAY_SORT_CD,EMP_STAT_CD,LAST_UPDT_DT,NM,OBJ_ID,VER_NBR)
  VALUES ('Y','03','N',TO_DATE( '20081113140632', 'YYYYMMDDHH24MISS' ),'Status Not Yet Processed','5B97C50B037D6110E0404F8189D85213',1)
/
INSERT INTO KRIM_EMP_STAT_T (ACTV_IND,DISPLAY_SORT_CD,EMP_STAT_CD,LAST_UPDT_DT,NM,OBJ_ID,VER_NBR)
  VALUES ('Y','02','P',TO_DATE( '20081113140632', 'YYYYMMDDHH24MISS' ),'Processing','5B97C50B037E6110E0404F8189D85213',1)
/
INSERT INTO KRIM_EMP_STAT_T (ACTV_IND,DISPLAY_SORT_CD,EMP_STAT_CD,LAST_UPDT_DT,NM,OBJ_ID,VER_NBR)
  VALUES ('Y','10','R',TO_DATE( '20081113140632', 'YYYYMMDDHH24MISS' ),'Retired','5B97C50B037F6110E0404F8189D85213',1)
/
INSERT INTO KRIM_EMP_STAT_T (ACTV_IND,DISPLAY_SORT_CD,EMP_STAT_CD,LAST_UPDT_DT,NM,OBJ_ID,VER_NBR)
  VALUES ('Y','97','T',TO_DATE( '20081113140632', 'YYYYMMDDHH24MISS' ),'Terminated','5B97C50B03806110E0404F8189D85213',1)
/
--
-- KRIM_EMP_TYP_T
--


INSERT INTO KRIM_EMP_TYP_T (ACTV_IND,DISPLAY_SORT_CD,EMP_TYP_CD,LAST_UPDT_DT,NM,OBJ_ID,VER_NBR)
  VALUES ('Y','02','N',TO_DATE( '20081113140632', 'YYYYMMDDHH24MISS' ),'Non-Professional','5B97C50B03826110E0404F8189D85213',1)
/
INSERT INTO KRIM_EMP_TYP_T (ACTV_IND,DISPLAY_SORT_CD,EMP_TYP_CD,LAST_UPDT_DT,NM,OBJ_ID,VER_NBR)
  VALUES ('Y','99','O',TO_DATE( '20081113140633', 'YYYYMMDDHH24MISS' ),'Other','5B97C50B03836110E0404F8189D85213',1)
/
INSERT INTO KRIM_EMP_TYP_T (ACTV_IND,DISPLAY_SORT_CD,EMP_TYP_CD,LAST_UPDT_DT,NM,OBJ_ID,VER_NBR)
  VALUES ('Y','01','P',TO_DATE( '20081113140633', 'YYYYMMDDHH24MISS' ),'Professional','5B97C50B03846110E0404F8189D85213',1)
/
--
-- KRIM_ENTITY_EMAIL_T
--


INSERT INTO KRIM_ENTITY_EMAIL_T (ACTV_IND,DFLT_IND,EMAIL_ADDR,EMAIL_TYP_CD,ENTITY_EMAIL_ID,ENTITY_ID,ENT_TYP_CD,LAST_UPDT_DT,OBJ_ID,VER_NBR)
  VALUES ('Y','Y','test@email.edu','WRK','1200','1100','PERSON',TO_DATE( '20081113140655', 'YYYYMMDDHH24MISS' ),'5B97C50B04066110E0404F8189D85213',1)
/
INSERT INTO KRIM_ENTITY_EMAIL_T (ACTV_IND,DFLT_IND,EMAIL_ADDR,EMAIL_TYP_CD,ENTITY_EMAIL_ID,ENTITY_ID,ENT_TYP_CD,LAST_UPDT_DT,OBJ_ID,VER_NBR)
  VALUES ('Y','Y','test@email.edu','WRK','1231','1131','PERSON',TO_DATE( '20081113140700', 'YYYYMMDDHH24MISS' ),'5B97C50B04256110E0404F8189D85213',1)
/
--
-- KRIM_ENTITY_ENT_TYP_T
--


INSERT INTO KRIM_ENTITY_ENT_TYP_T (ACTV_IND,ENTITY_ID,ENT_TYP_CD,LAST_UPDT_DT,OBJ_ID,VER_NBR)
  VALUES ('Y','1','SYSTEM',TO_DATE( '20081107094902', 'YYYYMMDDHH24MISS' ),'5B1B6B919CCB6496E0404F8189D822F2',1)
/
INSERT INTO KRIM_ENTITY_ENT_TYP_T (ACTV_IND,ENTITY_ID,ENT_TYP_CD,LAST_UPDT_DT,OBJ_ID,VER_NBR)
  VALUES ('Y','1100','PERSON',TO_DATE( '20081113140649', 'YYYYMMDDHH24MISS' ),'5B97C50B03E06110E0404F8189D85213',1)
/
INSERT INTO KRIM_ENTITY_ENT_TYP_T (ACTV_IND,ENTITY_ID,ENT_TYP_CD,LAST_UPDT_DT,OBJ_ID,VER_NBR)
  VALUES ('Y','1131','PERSON',TO_DATE( '20081113140654', 'YYYYMMDDHH24MISS' ),'5B97C50B03FF6110E0404F8189D85213',1)
/
--
-- KRIM_ENTITY_NM_T
--


INSERT INTO KRIM_ENTITY_NM_T (ACTV_IND,DFLT_IND,ENTITY_ID,ENTITY_NM_ID,FIRST_NM,LAST_NM,LAST_UPDT_DT,NM_TYP_CD,OBJ_ID,VER_NBR)
  VALUES ('Y','Y','1100','1200','admin','admin',TO_DATE( '20081113140702', 'YYYYMMDDHH24MISS' ),'PRFR','5B97C50B042C6110E0404F8189D85213',1)
/
INSERT INTO KRIM_ENTITY_NM_T (ACTV_IND,DFLT_IND,ENTITY_ID,ENTITY_NM_ID,FIRST_NM,LAST_NM,LAST_UPDT_DT,NM_TYP_CD,OBJ_ID,VER_NBR)
  VALUES ('Y','Y','1131','1231','Notification','System',TO_DATE( '20081113140707', 'YYYYMMDDHH24MISS' ),'PRFR','5B97C50B044B6110E0404F8189D85213',1)
/
--
-- KRIM_ENTITY_T
--


INSERT INTO KRIM_ENTITY_T (ACTV_IND,ENTITY_ID,LAST_UPDT_DT,OBJ_ID,VER_NBR)
  VALUES ('Y','1',TO_DATE( '20081107094902', 'YYYYMMDDHH24MISS' ),'5B1B6B919CC96496E0404F8189D822F2',1)
/
INSERT INTO KRIM_ENTITY_T (ACTV_IND,ENTITY_ID,LAST_UPDT_DT,OBJ_ID,VER_NBR)
  VALUES ('Y','1100',TO_DATE( '20081113140635', 'YYYYMMDDHH24MISS' ),'5B97C50B03946110E0404F8189D85213',1)
/
INSERT INTO KRIM_ENTITY_T (ACTV_IND,ENTITY_ID,LAST_UPDT_DT,OBJ_ID,VER_NBR)
  VALUES ('Y','1131',TO_DATE( '20081113140640', 'YYYYMMDDHH24MISS' ),'5B97C50B03B36110E0404F8189D85213',1)
/
--
-- KRIM_ENT_NM_TYP_T
--


INSERT INTO KRIM_ENT_NM_TYP_T (ACTV_IND,DISPLAY_SORT_CD,ENT_NM_TYP_CD,LAST_UPDT_DT,NM,OBJ_ID,VER_NBR)
  VALUES ('Y','c','OTH',TO_DATE( '20081113140633', 'YYYYMMDDHH24MISS' ),'Other','5B97C50B03856110E0404F8189D85213',1)
/
INSERT INTO KRIM_ENT_NM_TYP_T (ACTV_IND,DISPLAY_SORT_CD,ENT_NM_TYP_CD,LAST_UPDT_DT,NM,OBJ_ID,VER_NBR)
  VALUES ('Y','b','PRFR',TO_DATE( '20081113140633', 'YYYYMMDDHH24MISS' ),'Preferred','5B97C50B03866110E0404F8189D85213',1)
/
INSERT INTO KRIM_ENT_NM_TYP_T (ACTV_IND,DISPLAY_SORT_CD,ENT_NM_TYP_CD,LAST_UPDT_DT,NM,OBJ_ID,VER_NBR)
  VALUES ('Y','a','PRM',TO_DATE( '20081113140633', 'YYYYMMDDHH24MISS' ),'Primary','5B97C50B03876110E0404F8189D85213',1)
/
--
-- KRIM_ENT_TYP_T
--


INSERT INTO KRIM_ENT_TYP_T (ACTV_IND,DISPLAY_SORT_CD,ENT_TYP_CD,NM,OBJ_ID,VER_NBR)
  VALUES ('Y','01','PERSON','Person','5B97C50B03886110E0404F8189D85213',1)
/
INSERT INTO KRIM_ENT_TYP_T (ACTV_IND,DISPLAY_SORT_CD,ENT_TYP_CD,NM,OBJ_ID,VER_NBR)
  VALUES ('Y','02','SYSTEM','System','5B97C50B03896110E0404F8189D85213',1)
/
--
-- KRIM_EXT_ID_TYP_T
--


INSERT INTO KRIM_EXT_ID_TYP_T (ACTV_IND,DISPLAY_SORT_CD,ENCR_REQ_IND,EXT_ID_TYP_CD,LAST_UPDT_DT,NM,OBJ_ID,VER_NBR)
  VALUES ('Y','03','Y','TAX',TO_DATE( '20081113140635', 'YYYYMMDDHH24MISS' ),'Tax ID','5B97C50B038F6110E0404F8189D85213',1)
/
--
-- KRIM_GRP_MBR_T
--


INSERT INTO KRIM_GRP_MBR_T (GRP_ID,GRP_MBR_ID,LAST_UPDT_DT,MBR_ID,MBR_TYP_CD,OBJ_ID,VER_NBR)
  VALUES ('2000','1122',TO_DATE( '20081208124957', 'YYYYMMDDHH24MISS' ),'notsysadm','P','5B97C50B04CB6110E0404F8189D85213',1)
/
INSERT INTO KRIM_GRP_MBR_T (GRP_ID,GRP_MBR_ID,LAST_UPDT_DT,MBR_ID,MBR_TYP_CD,OBJ_ID,VER_NBR)
  VALUES ('1','1123',TO_DATE( '20081208124957', 'YYYYMMDDHH24MISS' ),'admin','P','5B97C50B04CC6110E0404F8189D85213',1)
/
INSERT INTO KRIM_GRP_MBR_T (GRP_ID,GRP_MBR_ID,LAST_UPDT_DT,MBR_ID,MBR_TYP_CD,OBJ_ID,VER_NBR)
  VALUES ('1','1124',TO_DATE( '20081208124957', 'YYYYMMDDHH24MISS' ),'notsys','P','5B97C50B04CD6110E0404F8189D85213',1)
/
--
-- KRIM_GRP_T
--


INSERT INTO KRIM_GRP_T (ACTV_IND,GRP_DESC,GRP_ID,GRP_NM,KIM_TYP_ID,LAST_UPDT_DT,NMSPC_CD,OBJ_ID,VER_NBR)
  VALUES ('Y','WorkflowAdmin','1','WorkflowAdmin','1',TO_DATE( '20081113140721', 'YYYYMMDDHH24MISS' ),'KR-WKFLW','5B97C50B04A16110E0404F8189D85213',1)
/
INSERT INTO KRIM_GRP_T (ACTV_IND,GRP_DESC,GRP_ID,GRP_NM,KIM_TYP_ID,LAST_UPDT_DT,NMSPC_CD,OBJ_ID,VER_NBR)
  VALUES ('Y','Notification system admin group for automation.','2000','NotificationAdmin','1',TO_DATE( '20081113140721', 'YYYYMMDDHH24MISS' ),'KR-WKFLW','5B97C50B04A26110E0404F8189D85213',1)
/
--
-- KRIM_PERM_ATTR_DATA_T
--


INSERT INTO KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID,ATTR_VAL,KIM_ATTR_DEFN_ID,KIM_TYP_ID,OBJ_ID,PERM_ID,VER_NBR)
  VALUES ('181','org.kuali.rice.kew.documentoperation.web.DocumentOperationAction','2','12','5B4F09744A25EF33E0404F8189D84F24','140',1)
/
INSERT INTO KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID,ATTR_VAL,KIM_ATTR_DEFN_ID,KIM_TYP_ID,OBJ_ID,PERM_ID,VER_NBR)
  VALUES ('183','org.kuali.rice.ksb.security.admin.web.JavaSecurityManagementAction','2','12','5B4F09744A27EF33E0404F8189D84F24','141',1)
/
INSERT INTO KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID,ATTR_VAL,KIM_ATTR_DEFN_ID,KIM_TYP_ID,OBJ_ID,PERM_ID,VER_NBR)
  VALUES ('185','org.kuali.rice.ksb.messaging.web.MessageQueueAction','2','12','5B4F09744A29EF33E0404F8189D84F24','142',1)
/
INSERT INTO KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID,ATTR_VAL,KIM_ATTR_DEFN_ID,KIM_TYP_ID,OBJ_ID,PERM_ID,VER_NBR)
  VALUES ('187','org.kuali.rice.ksb.messaging.web.ServiceRegistryAction','2','12','5B4F09744A2BEF33E0404F8189D84F24','143',1)
/
INSERT INTO KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID,ATTR_VAL,KIM_ATTR_DEFN_ID,KIM_TYP_ID,OBJ_ID,PERM_ID,VER_NBR)
  VALUES ('189','org.kuali.rice.ksb.messaging.web.ThreadPoolAction','2','12','5B4F09744A2DEF33E0404F8189D84F24','144',1)
/
INSERT INTO KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID,ATTR_VAL,KIM_ATTR_DEFN_ID,KIM_TYP_ID,OBJ_ID,PERM_ID,VER_NBR)
  VALUES ('191','org.kuali.rice.ksb.messaging.web.QuartzQueueAction','2','12','5B4F09744A2FEF33E0404F8189D84F24','145',1)
/
INSERT INTO KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID,ATTR_VAL,KIM_ATTR_DEFN_ID,KIM_TYP_ID,OBJ_ID,PERM_ID,VER_NBR)
  VALUES ('192','RiceDocument','13','5','5B4F09744A30EF33E0404F8189D84F24','146',1)
/
INSERT INTO KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID,ATTR_VAL,KIM_ATTR_DEFN_ID,KIM_TYP_ID,OBJ_ID,PERM_ID,VER_NBR)
  VALUES ('193','RiceDocument','13','3','5B4F09744A31EF33E0404F8189D84F24','147',1)
/
INSERT INTO KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID,ATTR_VAL,KIM_ATTR_DEFN_ID,KIM_TYP_ID,OBJ_ID,PERM_ID,VER_NBR)
  VALUES ('194','RiceDocument','13','3','5B4F09744A32EF33E0404F8189D84F24','148',1)
/
INSERT INTO KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID,ATTR_VAL,KIM_ATTR_DEFN_ID,KIM_TYP_ID,OBJ_ID,PERM_ID,VER_NBR)
  VALUES ('195','RiceDocument','13','3','5B4F09744A33EF33E0404F8189D84F24','149',1)
/
INSERT INTO KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID,ATTR_VAL,KIM_ATTR_DEFN_ID,KIM_TYP_ID,OBJ_ID,PERM_ID,VER_NBR)
  VALUES ('196','KR*','4','18','5B4F09744A34EF33E0404F8189D84F24','150',1)
/
INSERT INTO KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID,ATTR_VAL,KIM_ATTR_DEFN_ID,KIM_TYP_ID,OBJ_ID,PERM_ID,VER_NBR)
  VALUES ('197','KR*','4','19','5B4F09744A35EF33E0404F8189D84F24','151',1)
/
INSERT INTO KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID,ATTR_VAL,KIM_ATTR_DEFN_ID,KIM_TYP_ID,OBJ_ID,PERM_ID,VER_NBR)
  VALUES ('198','KR*','4','20','5B4F09744A36EF33E0404F8189D84F24','152',1)
/
INSERT INTO KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID,ATTR_VAL,KIM_ATTR_DEFN_ID,KIM_TYP_ID,OBJ_ID,PERM_ID,VER_NBR)
  VALUES ('201','KR*','4','21','5B4F09744A39EF33E0404F8189D84F24','155',1)
/
INSERT INTO KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID,ATTR_VAL,KIM_ATTR_DEFN_ID,KIM_TYP_ID,OBJ_ID,PERM_ID,VER_NBR)
  VALUES ('202','RiceDocument','13','3','5B4F09744A3AEF33E0404F8189D84F24','156',1)
/
INSERT INTO KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID,ATTR_VAL,KIM_ATTR_DEFN_ID,KIM_TYP_ID,OBJ_ID,PERM_ID,VER_NBR)
  VALUES ('203','KUALI','4','21','5B4F09744A39EF33E0404F8189D84F25','833',1)
/
INSERT INTO KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID,ATTR_VAL,KIM_ATTR_DEFN_ID,KIM_TYP_ID,OBJ_ID,PERM_ID,VER_NBR)
  VALUES ('204','KUALI','4','18','5B4F09744A39EF33E0404F8189D84F26','834',1)
/
INSERT INTO KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID,ATTR_VAL,KIM_ATTR_DEFN_ID,KIM_TYP_ID,OBJ_ID,PERM_ID,VER_NBR)
  VALUES ('205','KUALI','4','19','5B4F09744A39EF33E0404F8189D84F27','835',1)
/
INSERT INTO KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID,ATTR_VAL,KIM_ATTR_DEFN_ID,KIM_TYP_ID,OBJ_ID,PERM_ID,VER_NBR)
  VALUES ('206','KUALI','4','20','5B4F09744A39EF33E0404F8189D84F28','836',1)
/
INSERT INTO KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID,ATTR_VAL,KIM_ATTR_DEFN_ID,KIM_TYP_ID,OBJ_ID,PERM_ID,VER_NBR)
  VALUES ('211','KR*','4','10','5B4F09744A43EF33E0404F8189D84F24','161',1)
/
INSERT INTO KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID,ATTR_VAL,KIM_ATTR_DEFN_ID,KIM_TYP_ID,OBJ_ID,PERM_ID,VER_NBR)
  VALUES ('213','KR*','4','10','5B4F09744A45EF33E0404F8189D84F24','162',1)
/
INSERT INTO KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID,ATTR_VAL,KIM_ATTR_DEFN_ID,KIM_TYP_ID,OBJ_ID,PERM_ID,VER_NBR)
  VALUES ('216','KR*','4','16','5B4F09744A48EF33E0404F8189D84F24','163',1)
/
INSERT INTO KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID,ATTR_VAL,KIM_ATTR_DEFN_ID,KIM_TYP_ID,OBJ_ID,PERM_ID,VER_NBR)
  VALUES ('217','KR*','4','15','5B4F09744A49EF33E0404F8189D84F24','164',1)
/
INSERT INTO KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID,ATTR_VAL,KIM_ATTR_DEFN_ID,KIM_TYP_ID,OBJ_ID,PERM_ID,VER_NBR)
  VALUES ('218','RiceDocument','13','3','5B4F09744A4AEF33E0404F8189D84F24','165',1)
/
INSERT INTO KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID,ATTR_VAL,KIM_ATTR_DEFN_ID,KIM_TYP_ID,OBJ_ID,PERM_ID,VER_NBR)
  VALUES ('219','KR*','4','12','5B4F09744A4BEF33E0404F8189D84F24','166',1)
/
INSERT INTO KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID,ATTR_VAL,KIM_ATTR_DEFN_ID,KIM_TYP_ID,OBJ_ID,PERM_ID,VER_NBR)
  VALUES ('220','KualiDocument','13','8','5B4F09744A4CEF33E0404F8189D84F24','167',1)
/
INSERT INTO KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID,ATTR_VAL,KIM_ATTR_DEFN_ID,KIM_TYP_ID,OBJ_ID,PERM_ID,VER_NBR)
  VALUES ('221','KualiDocument','13','3','5B4F09744A4DEF33E0404F8189D84F24','168',1)
/
INSERT INTO KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID,ATTR_VAL,KIM_ATTR_DEFN_ID,KIM_TYP_ID,OBJ_ID,PERM_ID,VER_NBR)
  VALUES ('222','RiceDocument','13','8','5B4F09744A4EEF33E0404F8189D84F24','290',1)
/
INSERT INTO KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID,ATTR_VAL,KIM_ATTR_DEFN_ID,KIM_TYP_ID,OBJ_ID,PERM_ID,VER_NBR)
  VALUES ('223','A','14','4','5B4F09744A4FEF33E0404F8189D84F24','170',1)
/
INSERT INTO KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID,ATTR_VAL,KIM_ATTR_DEFN_ID,KIM_TYP_ID,OBJ_ID,PERM_ID,VER_NBR)
  VALUES ('225','F','14','4','5B4F09744A51EF33E0404F8189D84F24','172',1)
/
INSERT INTO KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID,ATTR_VAL,KIM_ATTR_DEFN_ID,KIM_TYP_ID,OBJ_ID,PERM_ID,VER_NBR)
  VALUES ('226','K','14','4','5B4F09744A52EF33E0404F8189D84F24','173',1)
/
INSERT INTO KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID,ATTR_VAL,KIM_ATTR_DEFN_ID,KIM_TYP_ID,OBJ_ID,PERM_ID,VER_NBR)
  VALUES ('237','KualiDocument','13','8','5B4F09744A5DEF33E0404F8189D84F24','180',1)
/
INSERT INTO KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID,ATTR_VAL,KIM_ATTR_DEFN_ID,KIM_TYP_ID,OBJ_ID,PERM_ID,VER_NBR)
  VALUES ('238','PreRoute','16','8','5B4F09744A5EEF33E0404F8189D84F24','180',1)
/
INSERT INTO KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID,ATTR_VAL,KIM_ATTR_DEFN_ID,KIM_TYP_ID,OBJ_ID,PERM_ID,VER_NBR)
  VALUES ('239','KualiDocument','13','8','5B4F09744A5FEF33E0404F8189D84F24','181',1)
/
INSERT INTO KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID,ATTR_VAL,KIM_ATTR_DEFN_ID,KIM_TYP_ID,OBJ_ID,PERM_ID,VER_NBR)
  VALUES ('240','R','15','8','5B4F09744A60EF33E0404F8189D84F24','181',1)
/
INSERT INTO KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID,ATTR_VAL,KIM_ATTR_DEFN_ID,KIM_TYP_ID,OBJ_ID,PERM_ID,VER_NBR)
  VALUES ('243','KimPrincipalImpl','5','11','5B4F09744A63EF33E0404F8189D84F24','183',1)
/
INSERT INTO KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID,ATTR_VAL,KIM_ATTR_DEFN_ID,KIM_TYP_ID,OBJ_ID,PERM_ID,VER_NBR)
  VALUES ('244','password','6','11','5B4F09744A64EF33E0404F8189D84F24','183',1)
/
INSERT INTO KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID,ATTR_VAL,KIM_ATTR_DEFN_ID,KIM_TYP_ID,OBJ_ID,PERM_ID,VER_NBR)
  VALUES ('249','PreRoute','16','8','5B6013B3AD121A9CE0404F8189D87094','167',1)
/
INSERT INTO KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID,ATTR_VAL,KIM_ATTR_DEFN_ID,KIM_TYP_ID,OBJ_ID,PERM_ID,VER_NBR)
  VALUES ('376','KualiDocument','13','9','606763510FC496D3E0404F8189D857A2','259',1)
/
INSERT INTO KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID,ATTR_VAL,KIM_ATTR_DEFN_ID,KIM_TYP_ID,OBJ_ID,PERM_ID,VER_NBR)
  VALUES ('379','KualiDocument','13','9','606763510FC796D3E0404F8189D857A2','261',1)
/
INSERT INTO KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID,ATTR_VAL,KIM_ATTR_DEFN_ID,KIM_TYP_ID,OBJ_ID,PERM_ID,VER_NBR)
  VALUES ('384','RiceDocument','13','59','606763510FCE96D3E0404F8189D857A2','264',1)
/
INSERT INTO KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID,ATTR_VAL,KIM_ATTR_DEFN_ID,KIM_TYP_ID,OBJ_ID,PERM_ID,VER_NBR)
  VALUES ('385','false','8','59','606763510FCF96D3E0404F8189D857A2','264',1)
/
INSERT INTO KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID,ATTR_VAL,KIM_ATTR_DEFN_ID,KIM_TYP_ID,OBJ_ID,PERM_ID,VER_NBR)
  VALUES ('431','IdentityManagementPersonDocument','5','11','6314CC58CF59B7B5E0404F8189D84439','306',1)
/
INSERT INTO KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID,ATTR_VAL,KIM_ATTR_DEFN_ID,KIM_TYP_ID,OBJ_ID,PERM_ID,VER_NBR)
  VALUES ('432','taxId','6','11','6314CC58CF5AB7B5E0404F8189D84439','306',1)
/
INSERT INTO KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID,ATTR_VAL,KIM_ATTR_DEFN_ID,KIM_TYP_ID,OBJ_ID,PERM_ID,VER_NBR)
  VALUES ('478','KualiDocument','13','5','662384B381BA67A1E0404F8189D868A6','332',1)
/
INSERT INTO KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID,ATTR_VAL,KIM_ATTR_DEFN_ID,KIM_TYP_ID,OBJ_ID,PERM_ID,VER_NBR)
  VALUES ('479','F','14','5','662384B381BB67A1E0404F8189D868A6','332',1)
/
INSERT INTO KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID,ATTR_VAL,KIM_ATTR_DEFN_ID,KIM_TYP_ID,OBJ_ID,PERM_ID,VER_NBR)
  VALUES ('480','KualiDocument','13','5','662384B381BE67A1E0404F8189D868A6','333',1)
/
INSERT INTO KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID,ATTR_VAL,KIM_ATTR_DEFN_ID,KIM_TYP_ID,OBJ_ID,PERM_ID,VER_NBR)
  VALUES ('481','K','14','5','662384B381BF67A1E0404F8189D868A6','333',1)
/
INSERT INTO KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID,ATTR_VAL,KIM_ATTR_DEFN_ID,KIM_TYP_ID,OBJ_ID,PERM_ID,VER_NBR)
  VALUES ('482','KualiDocument','13','5','662384B381C267A1E0404F8189D868A6','334',1)
/
INSERT INTO KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID,ATTR_VAL,KIM_ATTR_DEFN_ID,KIM_TYP_ID,OBJ_ID,PERM_ID,VER_NBR)
  VALUES ('483','A','14','5','662384B381C367A1E0404F8189D868A6','334',1)
/
INSERT INTO KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID,ATTR_VAL,KIM_ATTR_DEFN_ID,KIM_TYP_ID,OBJ_ID,PERM_ID,VER_NBR)
  VALUES ('701','RuleTemplate','5','10','A207FBACC3764793896D0D769F6DBBFF','701',1)
/
INSERT INTO KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID,ATTR_VAL,KIM_ATTR_DEFN_ID,KIM_TYP_ID,OBJ_ID,PERM_ID,VER_NBR)
  VALUES ('702','EDocLiteStyle','5','10','37F35D51EE714A8B8A66802F89626C50','702',1)
/
INSERT INTO KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID,ATTR_VAL,KIM_ATTR_DEFN_ID,KIM_TYP_ID,OBJ_ID,PERM_ID,VER_NBR)
  VALUES ('703','EDocLiteDefinition','5','10','950DE6A28C144A9B87CA2CFD9FF337C8','703',1)
/
INSERT INTO KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID,ATTR_VAL,KIM_ATTR_DEFN_ID,KIM_TYP_ID,OBJ_ID,PERM_ID,VER_NBR)
  VALUES ('707','RuleAttribute','5','10','D270B9BA7E494507AD0B5D184AB13479','707',1)
/
INSERT INTO KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID,ATTR_VAL,KIM_ATTR_DEFN_ID,KIM_TYP_ID,OBJ_ID,PERM_ID,VER_NBR)
  VALUES ('719','ParameterDetailType','5','10','45EEFA6F23FD4015B927D1D19F482888','719',1)
/
INSERT INTO KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID,ATTR_VAL,KIM_ATTR_DEFN_ID,KIM_TYP_ID,OBJ_ID,PERM_ID,VER_NBR)
  VALUES ('720','ParameterNamespace','5','10','FFF2C6639C6041F1B148AA9901C8A1F7','720',1)
/
INSERT INTO KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID,ATTR_VAL,KIM_ATTR_DEFN_ID,KIM_TYP_ID,OBJ_ID,PERM_ID,VER_NBR)
  VALUES ('721','ParameterType','5','10','6D489E39C0BC4890803E3A5F412297BE','721',1)
/
INSERT INTO KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID,ATTR_VAL,KIM_ATTR_DEFN_ID,KIM_TYP_ID,OBJ_ID,PERM_ID,VER_NBR)
  VALUES ('751','KR-WKFLW','4','10','A3142D53EC1541BE92223181852C37CF','701',1)
/
INSERT INTO KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID,ATTR_VAL,KIM_ATTR_DEFN_ID,KIM_TYP_ID,OBJ_ID,PERM_ID,VER_NBR)
  VALUES ('752','KR-WKFLW','4','10','6250F01B4CA243FCBA64A93FE4EAFE83','702',1)
/
INSERT INTO KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID,ATTR_VAL,KIM_ATTR_DEFN_ID,KIM_TYP_ID,OBJ_ID,PERM_ID,VER_NBR)
  VALUES ('753','KR-WKFLW','4','10','E0F57E480CF646A585BC92E91AB5FB1D','703',1)
/
INSERT INTO KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID,ATTR_VAL,KIM_ATTR_DEFN_ID,KIM_TYP_ID,OBJ_ID,PERM_ID,VER_NBR)
  VALUES ('757','KR-WKFLW','4','10','266BD230C3AA40EB9FC8079FBB960DF0','707',1)
/
INSERT INTO KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID,ATTR_VAL,KIM_ATTR_DEFN_ID,KIM_TYP_ID,OBJ_ID,PERM_ID,VER_NBR)
  VALUES ('768','KR-NS','4','10','160C75C4C8A3411D8856A50F38487EB2','719',1)
/
INSERT INTO KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID,ATTR_VAL,KIM_ATTR_DEFN_ID,KIM_TYP_ID,OBJ_ID,PERM_ID,VER_NBR)
  VALUES ('769','KR-NS','4','10','992DE7BE4C3C46F8AEDBE7925CBEE6B3','720',1)
/
INSERT INTO KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID,ATTR_VAL,KIM_ATTR_DEFN_ID,KIM_TYP_ID,OBJ_ID,PERM_ID,VER_NBR)
  VALUES ('770','KR-NS','4','10','330AAEB4459F4A34BBF8FECEA9E23404','721',1)
/
INSERT INTO KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID,ATTR_VAL,KIM_ATTR_DEFN_ID,KIM_TYP_ID,OBJ_ID,PERM_ID,VER_NBR)
  VALUES ('800','KR-WKFLW','4','10','606763510FD296D3E0404F8189D857A2','801',1)
/
INSERT INTO KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID,ATTR_VAL,KIM_ATTR_DEFN_ID,KIM_TYP_ID,OBJ_ID,PERM_ID,VER_NBR)
  VALUES ('801','RuleTemplate','5','10','606763510FD396D3E0404F8189D857A2','801',1)
/
INSERT INTO KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID,ATTR_VAL,KIM_ATTR_DEFN_ID,KIM_TYP_ID,OBJ_ID,PERM_ID,VER_NBR)
  VALUES ('802','KR-WKFLW','4','10','606763510FD596D3E0404F8189D857A2','802',1)
/
INSERT INTO KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID,ATTR_VAL,KIM_ATTR_DEFN_ID,KIM_TYP_ID,OBJ_ID,PERM_ID,VER_NBR)
  VALUES ('803','EDocLiteStyle','5','10','606763510FD696D3E0404F8189D857A2','802',1)
/
INSERT INTO KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID,ATTR_VAL,KIM_ATTR_DEFN_ID,KIM_TYP_ID,OBJ_ID,PERM_ID,VER_NBR)
  VALUES ('804','KR-WKFLW','4','10','606763510FD896D3E0404F8189D857A2','803',1)
/
INSERT INTO KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID,ATTR_VAL,KIM_ATTR_DEFN_ID,KIM_TYP_ID,OBJ_ID,PERM_ID,VER_NBR)
  VALUES ('805','EDocLiteDefinition','5','10','606763510FD996D3E0404F8189D857A2','803',1)
/
INSERT INTO KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID,ATTR_VAL,KIM_ATTR_DEFN_ID,KIM_TYP_ID,OBJ_ID,PERM_ID,VER_NBR)
  VALUES ('806','KR-WKFLW','4','10','606763510FDB96D3E0404F8189D857A2','807',1)
/
INSERT INTO KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID,ATTR_VAL,KIM_ATTR_DEFN_ID,KIM_TYP_ID,OBJ_ID,PERM_ID,VER_NBR)
  VALUES ('807','RuleAttribute','5','10','606763510FDC96D3E0404F8189D857A2','807',1)
/
INSERT INTO KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID,ATTR_VAL,KIM_ATTR_DEFN_ID,KIM_TYP_ID,OBJ_ID,PERM_ID,VER_NBR)
  VALUES ('814','PessimisticLock','5','10','606763510FE396D3E0404F8189D857A2','814',1)
/
INSERT INTO KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID,ATTR_VAL,KIM_ATTR_DEFN_ID,KIM_TYP_ID,OBJ_ID,PERM_ID,VER_NBR)
  VALUES ('819','ParameterDetailType','5','10','606763510FE696D3E0404F8189D857A2','819',1)
/
INSERT INTO KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID,ATTR_VAL,KIM_ATTR_DEFN_ID,KIM_TYP_ID,OBJ_ID,PERM_ID,VER_NBR)
  VALUES ('820','ParameterNamespace','5','10','606763510FE996D3E0404F8189D857A2','820',1)
/
INSERT INTO KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID,ATTR_VAL,KIM_ATTR_DEFN_ID,KIM_TYP_ID,OBJ_ID,PERM_ID,VER_NBR)
  VALUES ('821','ParameterType','5','10','606763510FEC96D3E0404F8189D857A2','821',1)
/
INSERT INTO KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID,ATTR_VAL,KIM_ATTR_DEFN_ID,KIM_TYP_ID,OBJ_ID,PERM_ID,VER_NBR)
  VALUES ('864','KR-NS','4','10','606763510FE296D3E0404F8189D857A2','814',1)
/
INSERT INTO KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID,ATTR_VAL,KIM_ATTR_DEFN_ID,KIM_TYP_ID,OBJ_ID,PERM_ID,VER_NBR)
  VALUES ('868','KR-NS','4','10','606763510FE596D3E0404F8189D857A2','819',1)
/
INSERT INTO KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID,ATTR_VAL,KIM_ATTR_DEFN_ID,KIM_TYP_ID,OBJ_ID,PERM_ID,VER_NBR)
  VALUES ('869','KR-NS','4','10','606763510FE896D3E0404F8189D857A2','820',1)
/
INSERT INTO KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID,ATTR_VAL,KIM_ATTR_DEFN_ID,KIM_TYP_ID,OBJ_ID,PERM_ID,VER_NBR)
  VALUES ('870','KR-NS','4','10','606763510FEB96D3E0404F8189D857A2','821',1)
/
INSERT INTO KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID,ATTR_VAL,KIM_ATTR_DEFN_ID,KIM_TYP_ID,OBJ_ID,PERM_ID,VER_NBR)
  VALUES ('873','KR-WKFLW','4','12','606763510FF396D3E0404F8189D857A2','265',1)
/
INSERT INTO KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID,ATTR_VAL,KIM_ATTR_DEFN_ID,KIM_TYP_ID,OBJ_ID,PERM_ID,VER_NBR)
  VALUES ('874','org.kuali.rice.kew.batch.web.IngesterAction','2','12','606763510FF496D3E0404F8189D857A2','265',1)
/
INSERT INTO KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID,ATTR_VAL,KIM_ATTR_DEFN_ID,KIM_TYP_ID,OBJ_ID,PERM_ID,VER_NBR)
  VALUES ('880','org.kuali.rice.ksb.messaging.web.ConfigViewerAction','2','12','ECCB8A6C-A0DA-5311-6A57-40F743EA334C','840',1)
/
INSERT INTO KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID,ATTR_VAL,KIM_ATTR_DEFN_ID,KIM_TYP_ID,OBJ_ID,PERM_ID,VER_NBR)
  VALUES ('881','KualiDocument','13','3','717e2c18-89e4-11df-98b1-1300c9ee50c1','841',1)
/
--
-- KRIM_PERM_T
--


INSERT INTO KRIM_PERM_T (ACTV_IND,DESC_TXT,NM,NMSPC_CD,OBJ_ID,PERM_ID,PERM_TMPL_ID,VER_NBR)
  VALUES ('Y','Allows users to access the Document Operation screen.','Use Screen','KR-WKFLW','5B4F09744944EF33E0404F8189D84F24','140','29',1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND,DESC_TXT,NM,NMSPC_CD,OBJ_ID,PERM_ID,PERM_TMPL_ID,VER_NBR)
  VALUES ('Y','Allows users to access the Java Security Management screen.','Use Screen','KR-BUS','5B4F09744945EF33E0404F8189D84F24','141','29',1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND,DESC_TXT,NM,NMSPC_CD,OBJ_ID,PERM_ID,PERM_TMPL_ID,VER_NBR)
  VALUES ('Y','Allows users to access the Message Queue screen.','Use Screen','KR-BUS','5B4F09744946EF33E0404F8189D84F24','142','29',1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND,DESC_TXT,NM,NMSPC_CD,OBJ_ID,PERM_ID,PERM_TMPL_ID,VER_NBR)
  VALUES ('Y','Allows users to access the Service Registry screen.','Use Screen','KR-BUS','5B4F09744947EF33E0404F8189D84F24','143','29',1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND,DESC_TXT,NM,NMSPC_CD,OBJ_ID,PERM_ID,PERM_TMPL_ID,VER_NBR)
  VALUES ('Y','Allows users to access the Thread Pool screen.','Use Screen','KR-BUS','5B4F09744948EF33E0404F8189D84F24','144','29',1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND,DESC_TXT,NM,NMSPC_CD,OBJ_ID,PERM_ID,PERM_TMPL_ID,VER_NBR)
  VALUES ('Y','Allows users to access the Quartz Queue screen.','Use Screen','KR-BUS','5B4F09744949EF33E0404F8189D84F24','145','29',1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND,DESC_TXT,NM,NMSPC_CD,OBJ_ID,PERM_ID,PERM_TMPL_ID,VER_NBR)
  VALUES ('Y','Allows a user to receive ad hoc requests for RICE Documents.','Ad Hoc Review Document','KR-SYS','5B4F0974494AEF33E0404F8189D84F24','146','9',1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND,DESC_TXT,NM,NMSPC_CD,OBJ_ID,PERM_ID,PERM_TMPL_ID,VER_NBR)
  VALUES ('Y','Allows users to open RICE Documents via the Super search option in Document Search and take Administrative workflow actions on them (such as approving the document, approving individual requests, or sending the document to a specified route node).','Administer Routing for Document','KR-SYS','5B4F0974494BEF33E0404F8189D84F24','147','3',1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND,DESC_TXT,NM,NMSPC_CD,OBJ_ID,PERM_ID,PERM_TMPL_ID,VER_NBR)
  VALUES ('Y','Allows access to the Blanket Approval button on RICE Documents.','Blanket Approve Document','KR-SYS','5B4F0974494CEF33E0404F8189D84F24','148','4',1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND,DESC_TXT,NM,NMSPC_CD,OBJ_ID,PERM_ID,PERM_TMPL_ID,VER_NBR)
  VALUES ('Y','Authorizes the initiation of RICE Documents.','Initiate Document','KR-SYS','5B4F0974494DEF33E0404F8189D84F24','149','10',1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND,DESC_TXT,NM,NMSPC_CD,OBJ_ID,PERM_ID,PERM_TMPL_ID,VER_NBR)
  VALUES ('Y','Authorizes users to modify the information on the Assignees Tab of the Role Document and the Roles section of the Membership Tab on the Person Document for Roles with a Module Code beginning with KR.','Assign Role','KR-SYS','5B4F0974494EEF33E0404F8189D84F24','150','35',1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND,DESC_TXT,NM,NMSPC_CD,OBJ_ID,PERM_ID,PERM_TMPL_ID,VER_NBR)
  VALUES ('Y','Authorizes users to modify the information on the Permissions tab of the Role Document for roles with a module code beginning with KR.','Grant Permission','KR-SYS','5B4F0974494FEF33E0404F8189D84F24','151','36',1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND,DESC_TXT,NM,NMSPC_CD,OBJ_ID,PERM_ID,PERM_TMPL_ID,VER_NBR)
  VALUES ('Y','Authorizes users to modify the information on the Responsibility tab of the Role Document for roles with a Module Code that begins with KR.','Grant Responsibility','KR-SYS','5B4F09744950EF33E0404F8189D84F24','152','37',1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND,DESC_TXT,NM,NMSPC_CD,OBJ_ID,PERM_ID,PERM_TMPL_ID,VER_NBR)
  VALUES ('Y','Authorizes users to modify the information on the Assignees Tab of the Group Document and the Group section of the Membership Tab on the Person Document for groups with namespaces beginning with KR.','Populate Group','KR-SYS','5B4F09744953EF33E0404F8189D84F24','155','38',1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND,DESC_TXT,NM,NMSPC_CD,OBJ_ID,PERM_ID,PERM_TMPL_ID,VER_NBR)
  VALUES ('Y','Authorizes users to copy RICE Documents.','Copy Document','KR-SYS','5B4F09744954EF33E0404F8189D84F24','156','2',1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND,DESC_TXT,NM,NMSPC_CD,OBJ_ID,PERM_ID,PERM_TMPL_ID,VER_NBR)
  VALUES ('Y','Allows users to access Kuali RICE inquiries.','Inquire Into Records','KR-SYS','5B4F09744959EF33E0404F8189D84F24','161','24',1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND,DESC_TXT,NM,NMSPC_CD,OBJ_ID,PERM_ID,PERM_TMPL_ID,VER_NBR)
  VALUES ('Y','Allow users to access Kuali RICE lookups.','Look Up Records','KR-SYS','5B4F0974495AEF33E0404F8189D84F24','162','23',1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND,DESC_TXT,NM,NMSPC_CD,OBJ_ID,PERM_ID,PERM_TMPL_ID,VER_NBR)
  VALUES ('Y','Authorizes to initiate and edit the Parameter document for parameters with a module code beginning with KR.','Maintain System Parameter','KR-SYS','5B4F0974495BEF33E0404F8189D84F24','163','34',1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND,DESC_TXT,NM,NMSPC_CD,OBJ_ID,PERM_ID,PERM_TMPL_ID,VER_NBR)
  VALUES ('Y','Allows users to access and run Batch Jobs associated with KR modules via the Schedule link.','Modify Batch Job','KR-SYS','5B4F0974495CEF33E0404F8189D84F24','164','32',1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND,DESC_TXT,NM,NMSPC_CD,OBJ_ID,PERM_ID,PERM_TMPL_ID,VER_NBR)
  VALUES ('Y','Authorizes users to open RICE Documents.','Open Document','KR-SYS','5B4F0974495DEF33E0404F8189D84F24','165','40',1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND,DESC_TXT,NM,NMSPC_CD,OBJ_ID,PERM_ID,PERM_TMPL_ID,VER_NBR)
  VALUES ('Y','Allows users to access all RICE screens.','Use Screen','KR-SYS','5B4F0974495EEF33E0404F8189D84F24','166','29',1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND,DESC_TXT,NM,NMSPC_CD,OBJ_ID,PERM_ID,PERM_TMPL_ID,VER_NBR)
  VALUES ('Y','Authorizes users to cancel a document prior to it being submitted for routing.','Cancel Document','KUALI','5B4F0974495FEF33E0404F8189D84F24','167','14',1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND,DESC_TXT,NM,NMSPC_CD,OBJ_ID,PERM_ID,PERM_TMPL_ID,VER_NBR)
  VALUES ('Y','Authorizes users to submit a document for routing.','Route Document','KUALI','5B4F09744960EF33E0404F8189D84F24','168','5',1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND,DESC_TXT,NM,NMSPC_CD,OBJ_ID,PERM_ID,PERM_TMPL_ID,VER_NBR)
  VALUES ('Y','Authorizes users to take the Approve action on documents routed to them.','Take Requested Action','KUALI','5B4F09744962EF33E0404F8189D84F24','170','8',1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND,DESC_TXT,NM,NMSPC_CD,OBJ_ID,PERM_ID,PERM_TMPL_ID,VER_NBR)
  VALUES ('Y','Authorizes users to take the FYI action on documents routed to them.','Take Requested Action','KUALI','5B4F09744964EF33E0404F8189D84F24','172','8',1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND,DESC_TXT,NM,NMSPC_CD,OBJ_ID,PERM_ID,PERM_TMPL_ID,VER_NBR)
  VALUES ('Y','Authorizes users to take the Acknowledge action on documents routed to them.','Take Requested Action','KUALI','5B4F09744965EF33E0404F8189D84F24','173','8',1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND,DESC_TXT,NM,NMSPC_CD,OBJ_ID,PERM_ID,PERM_TMPL_ID,VER_NBR)
  VALUES ('Y','Authorizes users to login to the Kuali portal.','Log In','KUALI','5B4F09744966EF33E0404F8189D84F24','174','1',1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND,DESC_TXT,NM,NMSPC_CD,OBJ_ID,PERM_ID,PERM_TMPL_ID,VER_NBR)
  VALUES ('Y','Allows users to edit Kuali documents that are in ENROUTE status.','Edit Document','KUALI','5B4F0974496CEF33E0404F8189D84F24','180','16',1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND,DESC_TXT,NM,NMSPC_CD,OBJ_ID,PERM_ID,PERM_TMPL_ID,VER_NBR)
  VALUES ('Y','Allows users to edit Kuali documents that are in ENROUTE status.','Edit Document','KUALI','5B4F0974496DEF33E0404F8189D84F24','181','16',1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND,DESC_TXT,NM,NMSPC_CD,OBJ_ID,PERM_ID,PERM_TMPL_ID,VER_NBR)
  VALUES ('Y','Authorizes users to view the entire Tax Identification Number on the Payee ACH document and Inquiry.','Full Unmask Field','KR-SYS','5B4F0974496FEF33E0404F8189D84F24','183','27',1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND,DESC_TXT,NM,NMSPC_CD,OBJ_ID,PERM_ID,PERM_TMPL_ID,VER_NBR)
  VALUES ('Y','Users who can add notes and attachments to any document answering to the Kuali Document parent document type.','Add Note / Attachment','KUALI','606763510FC396D3E0404F8189D857A2','259','45',1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND,DESC_TXT,NM,NMSPC_CD,OBJ_ID,PERM_ID,PERM_TMPL_ID,VER_NBR)
  VALUES ('Y','Authorizes users to view notes and attachments on documents answering to the KualiDocument parent document type.','View Note / Attachment','KUALI','606763510FC696D3E0404F8189D857A2','261','46',1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND,DESC_TXT,NM,NMSPC_CD,OBJ_ID,PERM_ID,PERM_TMPL_ID,VER_NBR)
  VALUES ('Y','Authorizes users to delete notes and attachments created by any user on documents answering to the RICE Document parent document type.','Delete Note / Attachment','KR-SYS','606763510FCD96D3E0404F8189D857A2','264','47',1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND,NM,NMSPC_CD,OBJ_ID,PERM_ID,PERM_TMPL_ID,VER_NBR)
  VALUES ('Y','Use Screen','KR-WKFLW','606763510FF296D3E0404F8189D857A2','265','29',1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND,DESC_TXT,NM,NMSPC_CD,OBJ_ID,PERM_ID,PERM_TMPL_ID,VER_NBR)
  VALUES ('Y','Administer Pessimistic Locking','Administer Pessimistic Locking','KR-NS','611BE30E404E5818E0404F8189D801C2','289','1',1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND,DESC_TXT,NM,NMSPC_CD,OBJ_ID,PERM_ID,PERM_TMPL_ID,VER_NBR)
  VALUES ('Y','Users who can save RICE documents','Save Document','KR-SYS','5B4F09744961EF33E0404F8189D84F24','290','15',1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND,DESC_TXT,NM,NMSPC_CD,OBJ_ID,PERM_ID,PERM_TMPL_ID,VER_NBR)
  VALUES ('Y','Authorizes users to access other users action lists via the Help Desk Action List Login.','View Other Action List','KR-WKFLW','641E580969E31B49E0404F8189D86190','298','1',1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND,DESC_TXT,NM,NMSPC_CD,OBJ_ID,PERM_ID,PERM_TMPL_ID,VER_NBR)
  VALUES ('Y','Users who can perform a document search with no criteria or result limits.','Unrestricted Document Search','KR-WKFLW','641E580969E51B49E0404F8189D86190','299','1',1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND,DESC_TXT,NM,NMSPC_CD,OBJ_ID,PERM_ID,PERM_TMPL_ID,VER_NBR)
  VALUES ('Y','Authorizes users to view the entire Tax Identification Number on the Person document and inquiry.','Full Unmask Field','KR-SYS','6314CC58CF58B7B5E0404F8189D84439','306','27',1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND,DESC_TXT,NM,NMSPC_CD,OBJ_ID,PERM_ID,PERM_TMPL_ID,VER_NBR)
  VALUES ('Y','Users who can modify entity records in Kuali Identity Management.','Modify Entity','KR-IDM','638DD46953F9BCD5E0404F8189D86240','307','1',1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND,DESC_TXT,NM,NMSPC_CD,OBJ_ID,PERM_ID,PERM_TMPL_ID,VER_NBR)
  VALUES ('Y','Authorizes users to send FYI ad hoc requests for Kuali Documents','Send Ad Hoc Request','KR-SYS','662384B381B967A1E0404F8189D868A6','332','49',1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND,DESC_TXT,NM,NMSPC_CD,OBJ_ID,PERM_ID,PERM_TMPL_ID,VER_NBR)
  VALUES ('Y','Authorizes users to send Acknowledge ad hoc requests for Kuali Documents','Send Ad Hoc Request','KR-SYS','662384B381BD67A1E0404F8189D868A6','333','49',1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND,DESC_TXT,NM,NMSPC_CD,OBJ_ID,PERM_ID,PERM_TMPL_ID,VER_NBR)
  VALUES ('Y','Authorizes users to send Approve ad hoc requests for Kuali Documents','Send Ad Hoc Request','KR-SYS','662384B381C167A1E0404F8189D868A6','334','49',1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND,DESC_TXT,NM,NMSPC_CD,OBJ_ID,PERM_ID,PERM_TMPL_ID,VER_NBR)
  VALUES ('Y','Allows a user to override entity privacy preferences','Override Entity Privacy Preferences','KR-IDM','5B4F09744953EF33E0404F8189D84F29','378','1',1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND,DESC_TXT,NM,NMSPC_CD,OBJ_ID,PERM_ID,PERM_TMPL_ID,VER_NBR)
  VALUES ('Y','Allow users to access the Rule Template lookup.','Look Up Records','KR-WKFLW','04C7706012554535AF8DC48DC6CC331C','701','23',1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND,DESC_TXT,NM,NMSPC_CD,OBJ_ID,PERM_ID,PERM_TMPL_ID,VER_NBR)
  VALUES ('Y','Allow users to access the Stylesheet lookup.','Look Up Records','KR-WKFLW','471FF4B19A4648D4B84194530AE22158','702','23',1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND,DESC_TXT,NM,NMSPC_CD,OBJ_ID,PERM_ID,PERM_TMPL_ID,VER_NBR)
  VALUES ('Y','Allow users to access the eDocLite lookup.','Look Up Records','KR-WKFLW','E6875070DC5B4FD59193F7445C33E7AB','703','23',1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND,DESC_TXT,NM,NMSPC_CD,OBJ_ID,PERM_ID,PERM_TMPL_ID,VER_NBR)
  VALUES ('Y','Allow users to access the Rule Attribute lookup.','Look Up Records','KR-WKFLW','28CE0127B8A14AB4BFD39920C5398A69','707','23',1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND,DESC_TXT,NM,NMSPC_CD,OBJ_ID,PERM_ID,PERM_TMPL_ID,VER_NBR)
  VALUES ('Y','Allow users to access the Parameter Component lookup.','Look Up Records','KR-NS','45F0E8E1B9784756A3C0582980912991','719','23',1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND,DESC_TXT,NM,NMSPC_CD,OBJ_ID,PERM_ID,PERM_TMPL_ID,VER_NBR)
  VALUES ('Y','Allow users to access the Namespace lookup.','Look Up Records','KR-NS','FFF2C6639C6041F1B148AA9901C8A1F7','720','23',1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND,DESC_TXT,NM,NMSPC_CD,OBJ_ID,PERM_ID,PERM_TMPL_ID,VER_NBR)
  VALUES ('Y','Allow users to access the Parameter Type lookup.','Look Up Records','KR-NS','B1BD57EE64274E62AC9425C7FF185A44','721','23',1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND,DESC_TXT,NM,NMSPC_CD,OBJ_ID,PERM_ID,PERM_TMPL_ID,VER_NBR)
  VALUES ('Y','Allow users to access the Rule Template inquiry.','Inquire Into Records','KR-WKFLW','606763510FD196D3E0404F8189D857A2','801','24',1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND,DESC_TXT,NM,NMSPC_CD,OBJ_ID,PERM_ID,PERM_TMPL_ID,VER_NBR)
  VALUES ('Y','Allow users to access the Stylesheet inquiry.','Inquire Into Records','KR-WKFLW','606763510FD496D3E0404F8189D857A2','802','24',1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND,DESC_TXT,NM,NMSPC_CD,OBJ_ID,PERM_ID,PERM_TMPL_ID,VER_NBR)
  VALUES ('Y','Allow users to access the eDocLite inquiry.','Inquire Into Records','KR-WKFLW','606763510FD796D3E0404F8189D857A2','803','24',1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND,DESC_TXT,NM,NMSPC_CD,OBJ_ID,PERM_ID,PERM_TMPL_ID,VER_NBR)
  VALUES ('Y','Allow users to access the Rule Attribute inquiry.','Inquire Into Records','KR-WKFLW','606763510FDA96D3E0404F8189D857A2','807','24',1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND,DESC_TXT,NM,NMSPC_CD,OBJ_ID,PERM_ID,PERM_TMPL_ID,VER_NBR)
  VALUES ('Y','Allow users to access the Pessimistic Lock inquiry.','Inquire Into Records','KR-NS','606763510FE196D3E0404F8189D857A2','814','24',1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND,DESC_TXT,NM,NMSPC_CD,OBJ_ID,PERM_ID,PERM_TMPL_ID,VER_NBR)
  VALUES ('Y','Allow users to access the Parameter Component inquiry.','Inquire Into Records','KR-NS','606763510FE496D3E0404F8189D857A2','819','24',1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND,DESC_TXT,NM,NMSPC_CD,OBJ_ID,PERM_ID,PERM_TMPL_ID,VER_NBR)
  VALUES ('Y','Allow users to access the Namespace inquiry.','Inquire Into Records','KR-NS','606763510FE796D3E0404F8189D857A2','820','24',1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND,DESC_TXT,NM,NMSPC_CD,OBJ_ID,PERM_ID,PERM_TMPL_ID,VER_NBR)
  VALUES ('Y','Allow users to access the Parameter Type inquiry.','Inquire Into Records','KR-NS','606763510FEA96D3E0404F8189D857A2','821','24',1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND,DESC_TXT,NM,NMSPC_CD,OBJ_ID,PERM_ID,PERM_TMPL_ID,VER_NBR)
  VALUES ('Y','Authorizes users to modify the information on the Assignees Tab of the Group Document and the Group section of the Membership Tab on the Person Document for groups with the KUALI namespace.','Populate Group','KR-SYS','5B4F09744953EF33E0404F8189D84F25','833','38',1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND,DESC_TXT,NM,NMSPC_CD,OBJ_ID,PERM_ID,PERM_TMPL_ID,VER_NBR)
  VALUES ('Y','Authorizes users to modify the information on the Assignees Tab of the Role Document and the Roles section of the Membership Tab on the Person Document for Roles with the KUALI namespace.','Assign Role','KR-SYS','5B4F09744953EF33E0404F8189D84F26','834','35',1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND,DESC_TXT,NM,NMSPC_CD,OBJ_ID,PERM_ID,PERM_TMPL_ID,VER_NBR)
  VALUES ('Y','Authorizes users to modify the information on the Permissions tab of the Role Document for roles with the KUALI namespace.','Grant Permission','KR-SYS','5B4F09744953EF33E0404F8189D84F27','835','36',1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND,DESC_TXT,NM,NMSPC_CD,OBJ_ID,PERM_ID,PERM_TMPL_ID,VER_NBR)
  VALUES ('Y','Authorizes users to modify the information on the Responsibility tab of the Role Document for roles with the KUALI namespace.','Grant Responsibility','KR-SYS','5B4F09744953EF33E0404F8189D84F28','836','37',1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND,DESC_TXT,NM,NMSPC_CD,OBJ_ID,PERM_ID,PERM_TMPL_ID,VER_NBR)
  VALUES ('Y','Allows users to access the Configuration Viewer screen','Use Screen','KR-BUS','97469975-D110-9A65-5EE5-F21FD1BEB5B2','840','29',1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND,NM,NMSPC_CD,OBJ_ID,PERM_ID,PERM_TMPL_ID,VER_NBR)
  VALUES ('Y','Add Message to Route Log','KUALI','65677409-89e4-11df-98b1-1300c9ee50c1','841','51',1)
/
--
-- KRIM_PERM_TMPL_T
--


INSERT INTO KRIM_PERM_TMPL_T (ACTV_IND,KIM_TYP_ID,NM,NMSPC_CD,OBJ_ID,PERM_TMPL_ID,VER_NBR)
  VALUES ('Y','1','Default','KUALI','5ADF18B6D4857954E0404F8189D85002','1',1)
/
INSERT INTO KRIM_PERM_TMPL_T (ACTV_IND,KIM_TYP_ID,NM,NMSPC_CD,OBJ_ID,PERM_TMPL_ID,VER_NBR)
  VALUES ('Y','3','Initiate Document','KR-SYS','5ADF18B6D4BF7954E0404F8189D85002','10',1)
/
INSERT INTO KRIM_PERM_TMPL_T (ACTV_IND,KIM_TYP_ID,NM,NMSPC_CD,OBJ_ID,PERM_TMPL_ID,VER_NBR)
  VALUES ('Y','8','Cancel Document','KR-WKFLW','5ADF18B6D4CA7954E0404F8189D85002','14',1)
/
INSERT INTO KRIM_PERM_TMPL_T (ACTV_IND,KIM_TYP_ID,NM,NMSPC_CD,OBJ_ID,PERM_TMPL_ID,VER_NBR)
  VALUES ('Y','8','Save Document','KR-WKFLW','5ADF18B6D4CB7954E0404F8189D85002','15',1)
/
INSERT INTO KRIM_PERM_TMPL_T (ACTV_IND,KIM_TYP_ID,NM,NMSPC_CD,OBJ_ID,PERM_TMPL_ID,VER_NBR)
  VALUES ('Y','8','Edit Document','KR-NS','5ADF18B6D4CC7954E0404F8189D85002','16',1)
/
INSERT INTO KRIM_PERM_TMPL_T (ACTV_IND,KIM_TYP_ID,NM,NMSPC_CD,OBJ_ID,PERM_TMPL_ID,VER_NBR)
  VALUES ('Y','3','Copy Document','KR-NS','5ADF18B6D4AF7954E0404F8189D85002','2',1)
/
INSERT INTO KRIM_PERM_TMPL_T (ACTV_IND,KIM_TYP_ID,NM,NMSPC_CD,OBJ_ID,PERM_TMPL_ID,VER_NBR)
  VALUES ('Y','10','Look Up Records','KR-NS','5ADF18B6D4DA7954E0404F8189D85002','23',1)
/
INSERT INTO KRIM_PERM_TMPL_T (ACTV_IND,KIM_TYP_ID,NM,NMSPC_CD,OBJ_ID,PERM_TMPL_ID,VER_NBR)
  VALUES ('Y','10','Inquire Into Records','KR-NS','5ADF18B6D4DB7954E0404F8189D85002','24',1)
/
INSERT INTO KRIM_PERM_TMPL_T (ACTV_IND,KIM_TYP_ID,NM,NMSPC_CD,OBJ_ID,PERM_TMPL_ID,VER_NBR)
  VALUES ('Y','11','View Inquiry or Maintenance Document Field','KR-NS','5ADF18B6D4DF7954E0404F8189D85002','25',1)
/
INSERT INTO KRIM_PERM_TMPL_T (ACTV_IND,KIM_TYP_ID,NM,NMSPC_CD,OBJ_ID,PERM_TMPL_ID,VER_NBR)
  VALUES ('Y','11','Modify Maintenance Document Field','KR-NS','5ADF18B6D4E07954E0404F8189D85002','26',1)
/
INSERT INTO KRIM_PERM_TMPL_T (ACTV_IND,KIM_TYP_ID,NM,NMSPC_CD,OBJ_ID,PERM_TMPL_ID,VER_NBR)
  VALUES ('Y','11','Full Unmask Field','KR-NS','5ADF18B6D4E17954E0404F8189D85002','27',1)
/
INSERT INTO KRIM_PERM_TMPL_T (ACTV_IND,KIM_TYP_ID,NM,NMSPC_CD,OBJ_ID,PERM_TMPL_ID,VER_NBR)
  VALUES ('Y','11','Partial Unmask Field','KR-NS','5ADF18B6D4E27954E0404F8189D85002','28',1)
/
INSERT INTO KRIM_PERM_TMPL_T (ACTV_IND,KIM_TYP_ID,NM,NMSPC_CD,OBJ_ID,PERM_TMPL_ID,VER_NBR)
  VALUES ('Y','12','Use Screen','KR-NS','5ADF18B6D4E67954E0404F8189D85002','29',1)
/
INSERT INTO KRIM_PERM_TMPL_T (ACTV_IND,KIM_TYP_ID,NM,NMSPC_CD,OBJ_ID,PERM_TMPL_ID,VER_NBR)
  VALUES ('Y','3','Administer Routing for Document','KR-WKFLW','5ADF18B6D4B07954E0404F8189D85002','3',1)
/
INSERT INTO KRIM_PERM_TMPL_T (ACTV_IND,KIM_TYP_ID,NM,NMSPC_CD,OBJ_ID,PERM_TMPL_ID,VER_NBR)
  VALUES ('Y','13','Perform Custom Maintenance Document Function','KR-NS','5ADF18B6D4E97954E0404F8189D85002','30',1)
/
INSERT INTO KRIM_PERM_TMPL_T (ACTV_IND,KIM_TYP_ID,NM,NMSPC_CD,OBJ_ID,PERM_TMPL_ID,VER_NBR)
  VALUES ('Y','14','Use Transactional Document','KR-NS','5ADF18B6D4EC7954E0404F8189D85002','31',1)
/
INSERT INTO KRIM_PERM_TMPL_T (ACTV_IND,KIM_TYP_ID,NM,NMSPC_CD,OBJ_ID,PERM_TMPL_ID,VER_NBR)
  VALUES ('Y','15','Modify Batch Job','KR-NS','5ADF18B6D4F07954E0404F8189D85002','32',1)
/
INSERT INTO KRIM_PERM_TMPL_T (ACTV_IND,KIM_TYP_ID,NM,NMSPC_CD,OBJ_ID,PERM_TMPL_ID,VER_NBR)
  VALUES ('Y','15','Upload Batch Input File(s)','KR-NS','5ADF18B6D4F17954E0404F8189D85002','33',1)
/
INSERT INTO KRIM_PERM_TMPL_T (ACTV_IND,KIM_TYP_ID,NM,NMSPC_CD,OBJ_ID,PERM_TMPL_ID,VER_NBR)
  VALUES ('Y','16','Maintain System Parameter','KR-NS','5ADF18B6D4F67954E0404F8189D85002','34',1)
/
INSERT INTO KRIM_PERM_TMPL_T (ACTV_IND,KIM_TYP_ID,NM,NMSPC_CD,OBJ_ID,PERM_TMPL_ID,VER_NBR)
  VALUES ('Y','18','Assign Role','KR-IDM','5ADF18B6D4FC7954E0404F8189D85002','35',1)
/
INSERT INTO KRIM_PERM_TMPL_T (ACTV_IND,KIM_TYP_ID,NM,NMSPC_CD,OBJ_ID,PERM_TMPL_ID,VER_NBR)
  VALUES ('Y','19','Grant Permission','KR-IDM','5ADF18B6D5007954E0404F8189D85002','36',1)
/
INSERT INTO KRIM_PERM_TMPL_T (ACTV_IND,KIM_TYP_ID,NM,NMSPC_CD,OBJ_ID,PERM_TMPL_ID,VER_NBR)
  VALUES ('Y','20','Grant Responsibility','KR-IDM','5ADF18B6D5047954E0404F8189D85002','37',1)
/
INSERT INTO KRIM_PERM_TMPL_T (ACTV_IND,KIM_TYP_ID,NM,NMSPC_CD,OBJ_ID,PERM_TMPL_ID,VER_NBR)
  VALUES ('Y','21','Populate Group','KR-IDM','5ADF18B6D5087954E0404F8189D85002','38',1)
/
INSERT INTO KRIM_PERM_TMPL_T (ACTV_IND,KIM_TYP_ID,NM,NMSPC_CD,OBJ_ID,PERM_TMPL_ID,VER_NBR)
  VALUES ('Y','3','Blanket Approve Document','KR-WKFLW','5ADF18B6D4B17954E0404F8189D85002','4',1)
/
INSERT INTO KRIM_PERM_TMPL_T (ACTV_IND,KIM_TYP_ID,NM,NMSPC_CD,OBJ_ID,PERM_TMPL_ID,VER_NBR)
  VALUES ('Y','3','Open Document','KR-NS','5ADF18B6D4AE7954E0404F8189D85002','40',1)
/
INSERT INTO KRIM_PERM_TMPL_T (ACTV_IND,KIM_TYP_ID,NM,NMSPC_CD,OBJ_ID,PERM_TMPL_ID,VER_NBR)
  VALUES ('Y','56','Create / Maintain Record(s)','KR-NS','603B735FA6C4FE21E0404F8189D8083B','42',1)
/
INSERT INTO KRIM_PERM_TMPL_T (ACTV_IND,KIM_TYP_ID,NM,NMSPC_CD,OBJ_ID,PERM_TMPL_ID,VER_NBR)
  VALUES ('Y','57','View Inquiry or Maintenance Document Section','KR-NS','603B735FA6C0FE21E0404F8189D8083B','43',1)
/
INSERT INTO KRIM_PERM_TMPL_T (ACTV_IND,KIM_TYP_ID,NM,NMSPC_CD,OBJ_ID,PERM_TMPL_ID,VER_NBR)
  VALUES ('Y','57','Modify Maintenance Document Section','KR-NS','603B735FA6C1FE21E0404F8189D8083B','44',1)
/
INSERT INTO KRIM_PERM_TMPL_T (ACTV_IND,KIM_TYP_ID,NM,NMSPC_CD,OBJ_ID,PERM_TMPL_ID,VER_NBR)
  VALUES ('Y','9','Add Note / Attachment','KR-NS','606763510FC096D3E0404F8189D857A2','45',1)
/
INSERT INTO KRIM_PERM_TMPL_T (ACTV_IND,KIM_TYP_ID,NM,NMSPC_CD,OBJ_ID,PERM_TMPL_ID,VER_NBR)
  VALUES ('Y','9','View Note / Attachment','KR-NS','606763510FC196D3E0404F8189D857A2','46',1)
/
INSERT INTO KRIM_PERM_TMPL_T (ACTV_IND,KIM_TYP_ID,NM,NMSPC_CD,OBJ_ID,PERM_TMPL_ID,VER_NBR)
  VALUES ('Y','59','Delete Note / Attachment','KR-NS','606763510FC296D3E0404F8189D857A2','47',1)
/
INSERT INTO KRIM_PERM_TMPL_T (ACTV_IND,KIM_TYP_ID,NM,NMSPC_CD,OBJ_ID,PERM_TMPL_ID,VER_NBR)
  VALUES ('Y','5','Send Ad Hoc Request','KR-NS','662384B381B867A1E0404F8189D868A6','49',1)
/
INSERT INTO KRIM_PERM_TMPL_T (ACTV_IND,KIM_TYP_ID,NM,NMSPC_CD,OBJ_ID,PERM_TMPL_ID,VER_NBR)
  VALUES ('Y','3','Route Document','KR-WKFLW','5ADF18B6D4B27954E0404F8189D85002','5',1)
/
INSERT INTO KRIM_PERM_TMPL_T (ACTV_IND,KIM_TYP_ID,NM,NMSPC_CD,OBJ_ID,PERM_TMPL_ID,VER_NBR)
  VALUES ('Y','3','Add Message to Route Log','KR-WKFLW','430ad531-89e4-11df-98b1-1300c9ee50c1','51',1)
/
INSERT INTO KRIM_PERM_TMPL_T (ACTV_IND,KIM_TYP_ID,NM,NMSPC_CD,OBJ_ID,PERM_TMPL_ID,VER_NBR)
  VALUES ('Y','4','Take Requested Action','KR-NS','5ADF18B6D4B77954E0404F8189D85002','8',1)
/
INSERT INTO KRIM_PERM_TMPL_T (ACTV_IND,KIM_TYP_ID,NM,NMSPC_CD,OBJ_ID,PERM_TMPL_ID,VER_NBR)
  VALUES ('Y','5','Ad Hoc Review Document','KR-WKFLW','5ADF18B6D4BB7954E0404F8189D85002','9',1)
/
--
-- KRIM_PHONE_TYP_T
--


INSERT INTO KRIM_PHONE_TYP_T (ACTV_IND,DISPLAY_SORT_CD,LAST_UPDT_DT,OBJ_ID,PHONE_TYP_CD,PHONE_TYP_NM,VER_NBR)
  VALUES ('Y','b',TO_DATE( '20081113140635', 'YYYYMMDDHH24MISS' ),'5B97C50B03906110E0404F8189D85213','HM','Home',1)
/
INSERT INTO KRIM_PHONE_TYP_T (ACTV_IND,DISPLAY_SORT_CD,LAST_UPDT_DT,OBJ_ID,PHONE_TYP_CD,PHONE_TYP_NM,VER_NBR)
  VALUES ('Y','c',TO_DATE( '20081113140635', 'YYYYMMDDHH24MISS' ),'5B97C50B03916110E0404F8189D85213','MBL','Mobile',1)
/
INSERT INTO KRIM_PHONE_TYP_T (ACTV_IND,DISPLAY_SORT_CD,LAST_UPDT_DT,OBJ_ID,PHONE_TYP_CD,PHONE_TYP_NM,VER_NBR)
  VALUES ('Y','d',TO_DATE( '20081113140635', 'YYYYMMDDHH24MISS' ),'5B97C50B03926110E0404F8189D85213','OTH','Other',1)
/
INSERT INTO KRIM_PHONE_TYP_T (ACTV_IND,DISPLAY_SORT_CD,LAST_UPDT_DT,OBJ_ID,PHONE_TYP_CD,PHONE_TYP_NM,VER_NBR)
  VALUES ('Y','a',TO_DATE( '20081113140635', 'YYYYMMDDHH24MISS' ),'5B97C50B03936110E0404F8189D85213','WRK','Work',1)
/
--
-- KRIM_PRNCPL_T
--


INSERT INTO KRIM_PRNCPL_T (ACTV_IND,ENTITY_ID,LAST_UPDT_DT,OBJ_ID,PRNCPL_ID,PRNCPL_NM,VER_NBR)
  VALUES ('Y','1',TO_DATE( '20081107094902', 'YYYYMMDDHH24MISS' ),'5B1B6B919CCA6496E0404F8189D822F2','1','kr',1)
/
INSERT INTO KRIM_PRNCPL_T (ACTV_IND,ENTITY_ID,LAST_UPDT_DT,OBJ_ID,PRNCPL_ID,PRNCPL_NM,VER_NBR)
  VALUES ('Y','1100',TO_DATE( '20081113140643', 'YYYYMMDDHH24MISS' ),'5B97C50B03C56110E0404F8189D85213','admin','admin',1)
/
INSERT INTO KRIM_PRNCPL_T (ACTV_IND,ENTITY_ID,LAST_UPDT_DT,OBJ_ID,PRNCPL_ID,PRNCPL_NM,VER_NBR)
  VALUES ('Y','1131',TO_DATE( '20081113140642', 'YYYYMMDDHH24MISS' ),'5B97C50B03BB6110E0404F8189D85213','notsys','notsys',1)
/
--
-- KRIM_ROLE_MBR_T
--


INSERT INTO KRIM_ROLE_MBR_T (LAST_UPDT_DT,MBR_ID,MBR_TYP_CD,OBJ_ID,ROLE_ID,ROLE_MBR_ID,VER_NBR)
  VALUES (TO_DATE( '20090806170240', 'YYYYMMDDHH24MISS' ),'1','P','5B4B421E43857717E0404F8189D821F7','90','1282',1)
/
INSERT INTO KRIM_ROLE_MBR_T (LAST_UPDT_DT,MBR_ID,MBR_TYP_CD,OBJ_ID,ROLE_ID,ROLE_MBR_ID,VER_NBR)
  VALUES (TO_DATE( '20081208124941', 'YYYYMMDDHH24MISS' ),'admin','P','D0B057F8E7B949EFA94AE8CDA47DE41B','63','1283',1)
/
--
-- KRIM_ROLE_PERM_T
--


INSERT INTO KRIM_ROLE_PERM_T (ACTV_IND,OBJ_ID,PERM_ID,ROLE_ID,ROLE_PERM_ID,VER_NBR)
  VALUES ('Y','5C27A267EF5C7417E0404F8189D830A9','140','63','183',1)
/
INSERT INTO KRIM_ROLE_PERM_T (ACTV_IND,OBJ_ID,PERM_ID,ROLE_ID,ROLE_PERM_ID,VER_NBR)
  VALUES ('Y','5C27A267EF5D7417E0404F8189D830A9','141','63','184',1)
/
INSERT INTO KRIM_ROLE_PERM_T (ACTV_IND,OBJ_ID,PERM_ID,ROLE_ID,ROLE_PERM_ID,VER_NBR)
  VALUES ('Y','5C27A267EF5E7417E0404F8189D830A9','142','63','185',1)
/
INSERT INTO KRIM_ROLE_PERM_T (ACTV_IND,OBJ_ID,PERM_ID,ROLE_ID,ROLE_PERM_ID,VER_NBR)
  VALUES ('Y','5C27A267EF5F7417E0404F8189D830A9','143','63','186',1)
/
INSERT INTO KRIM_ROLE_PERM_T (ACTV_IND,OBJ_ID,PERM_ID,ROLE_ID,ROLE_PERM_ID,VER_NBR)
  VALUES ('Y','5C27A267EF607417E0404F8189D830A9','144','63','187',1)
/
INSERT INTO KRIM_ROLE_PERM_T (ACTV_IND,OBJ_ID,PERM_ID,ROLE_ID,ROLE_PERM_ID,VER_NBR)
  VALUES ('Y','5C27A267EF617417E0404F8189D830A9','145','63','188',1)
/
INSERT INTO KRIM_ROLE_PERM_T (ACTV_IND,OBJ_ID,PERM_ID,ROLE_ID,ROLE_PERM_ID,VER_NBR)
  VALUES ('Y','5C27A267EF627417E0404F8189D830A9','146','1','189',1)
/
INSERT INTO KRIM_ROLE_PERM_T (ACTV_IND,OBJ_ID,PERM_ID,ROLE_ID,ROLE_PERM_ID,VER_NBR)
  VALUES ('Y','5C27A267EF637417E0404F8189D830A9','147','63','190',1)
/
INSERT INTO KRIM_ROLE_PERM_T (ACTV_IND,OBJ_ID,PERM_ID,ROLE_ID,ROLE_PERM_ID,VER_NBR)
  VALUES ('Y','5C27A267EF647417E0404F8189D830A9','148','63','191',1)
/
INSERT INTO KRIM_ROLE_PERM_T (ACTV_IND,OBJ_ID,PERM_ID,ROLE_ID,ROLE_PERM_ID,VER_NBR)
  VALUES ('Y','5C27A267EF667417E0404F8189D830A9','149','1','193',1)
/
INSERT INTO KRIM_ROLE_PERM_T (ACTV_IND,OBJ_ID,PERM_ID,ROLE_ID,ROLE_PERM_ID,VER_NBR)
  VALUES ('Y','5C27A267EF677417E0404F8189D830A9','150','63','194',1)
/
INSERT INTO KRIM_ROLE_PERM_T (ACTV_IND,OBJ_ID,PERM_ID,ROLE_ID,ROLE_PERM_ID,VER_NBR)
  VALUES ('Y','5C27A267EF687417E0404F8189D830A9','151','63','195',1)
/
INSERT INTO KRIM_ROLE_PERM_T (ACTV_IND,OBJ_ID,PERM_ID,ROLE_ID,ROLE_PERM_ID,VER_NBR)
  VALUES ('Y','5C27A267EF697417E0404F8189D830A9','152','63','196',1)
/
INSERT INTO KRIM_ROLE_PERM_T (ACTV_IND,OBJ_ID,PERM_ID,ROLE_ID,ROLE_PERM_ID,VER_NBR)
  VALUES ('Y','5C27A267EF6D7417E0404F8189D830A9','155','63','200',1)
/
INSERT INTO KRIM_ROLE_PERM_T (ACTV_IND,OBJ_ID,PERM_ID,ROLE_ID,ROLE_PERM_ID,VER_NBR)
  VALUES ('Y','5C27A267EF6E7417E0404F8189D830A9','156','1','201',1)
/
INSERT INTO KRIM_ROLE_PERM_T (ACTV_IND,OBJ_ID,PERM_ID,ROLE_ID,ROLE_PERM_ID,VER_NBR)
  VALUES ('Y','5C27A267EF737417E0404F8189D830A9','161','1','206',1)
/
INSERT INTO KRIM_ROLE_PERM_T (ACTV_IND,OBJ_ID,PERM_ID,ROLE_ID,ROLE_PERM_ID,VER_NBR)
  VALUES ('Y','5C27A267EF747417E0404F8189D830A9','162','1','207',1)
/
INSERT INTO KRIM_ROLE_PERM_T (ACTV_IND,OBJ_ID,PERM_ID,ROLE_ID,ROLE_PERM_ID,VER_NBR)
  VALUES ('Y','5C27A267EF757417E0404F8189D830A9','163','63','208',1)
/
INSERT INTO KRIM_ROLE_PERM_T (ACTV_IND,OBJ_ID,PERM_ID,ROLE_ID,ROLE_PERM_ID,VER_NBR)
  VALUES ('Y','5C27A267EF777417E0404F8189D830A9','164','63','210',1)
/
INSERT INTO KRIM_ROLE_PERM_T (ACTV_IND,OBJ_ID,PERM_ID,ROLE_ID,ROLE_PERM_ID,VER_NBR)
  VALUES ('Y','5C27A267EF787417E0404F8189D830A9','165','1','211',1)
/
INSERT INTO KRIM_ROLE_PERM_T (ACTV_IND,OBJ_ID,PERM_ID,ROLE_ID,ROLE_PERM_ID,VER_NBR)
  VALUES ('Y','5C27A267EF797417E0404F8189D830A9','167','66','212',1)
/
INSERT INTO KRIM_ROLE_PERM_T (ACTV_IND,OBJ_ID,PERM_ID,ROLE_ID,ROLE_PERM_ID,VER_NBR)
  VALUES ('Y','5C27A267EF7A7417E0404F8189D830A9','168','66','213',1)
/
INSERT INTO KRIM_ROLE_PERM_T (ACTV_IND,OBJ_ID,PERM_ID,ROLE_ID,ROLE_PERM_ID,VER_NBR)
  VALUES ('Y','5C27A267EF7B7417E0404F8189D830A9','290','60','214',1)
/
INSERT INTO KRIM_ROLE_PERM_T (ACTV_IND,OBJ_ID,PERM_ID,ROLE_ID,ROLE_PERM_ID,VER_NBR)
  VALUES ('Y','5C27A267EF7D7417E0404F8189D830A9','170','59','216',1)
/
INSERT INTO KRIM_ROLE_PERM_T (ACTV_IND,OBJ_ID,PERM_ID,ROLE_ID,ROLE_PERM_ID,VER_NBR)
  VALUES ('Y','5C27A267EF7F7417E0404F8189D830A9','172','89','218',1)
/
INSERT INTO KRIM_ROLE_PERM_T (ACTV_IND,OBJ_ID,PERM_ID,ROLE_ID,ROLE_PERM_ID,VER_NBR)
  VALUES ('Y','5C27A267EF807417E0404F8189D830A9','173','88','219',1)
/
INSERT INTO KRIM_ROLE_PERM_T (ACTV_IND,OBJ_ID,PERM_ID,ROLE_ID,ROLE_PERM_ID,VER_NBR)
  VALUES ('Y','5C27A267EF817417E0404F8189D830A9','174','1','220',1)
/
INSERT INTO KRIM_ROLE_PERM_T (ACTV_IND,OBJ_ID,PERM_ID,ROLE_ID,ROLE_PERM_ID,VER_NBR)
  VALUES ('Y','5C27A267EF877417E0404F8189D830A9','180','60','226',1)
/
INSERT INTO KRIM_ROLE_PERM_T (ACTV_IND,OBJ_ID,PERM_ID,ROLE_ID,ROLE_PERM_ID,VER_NBR)
  VALUES ('Y','5C27A267EF887417E0404F8189D830A9','181','59','227',1)
/
INSERT INTO KRIM_ROLE_PERM_T (ACTV_IND,OBJ_ID,PERM_ID,ROLE_ID,ROLE_PERM_ID,VER_NBR)
  VALUES ('Y','5C27A267EF8A7417E0404F8189D830A9','183','63','229',1)
/
INSERT INTO KRIM_ROLE_PERM_T (ACTV_IND,OBJ_ID,PERM_ID,ROLE_ID,ROLE_PERM_ID,VER_NBR)
  VALUES ('Y','5C27A267EF5C7417E0404F8189D830AA','378','63','230',1)
/
INSERT INTO KRIM_ROLE_PERM_T (ACTV_IND,OBJ_ID,PERM_ID,ROLE_ID,ROLE_PERM_ID,VER_NBR)
  VALUES ('Y','70086A2DF17D62E4E0404F8189D863CD','156','95','250',1)
/
INSERT INTO KRIM_ROLE_PERM_T (ACTV_IND,OBJ_ID,PERM_ID,ROLE_ID,ROLE_PERM_ID,VER_NBR)
  VALUES ('Y','70086A2DF17E62E4E0404F8189D863CD','181','97','251',1)
/
INSERT INTO KRIM_ROLE_PERM_T (ACTV_IND,OBJ_ID,PERM_ID,ROLE_ID,ROLE_PERM_ID,VER_NBR)
  VALUES ('Y','606763510FC596D3E0404F8189D857A2','259','61','512',1)
/
INSERT INTO KRIM_ROLE_PERM_T (ACTV_IND,OBJ_ID,PERM_ID,ROLE_ID,ROLE_PERM_ID,VER_NBR)
  VALUES ('Y','606763510FC896D3E0404F8189D857A2','261','83','516',1)
/
INSERT INTO KRIM_ROLE_PERM_T (ACTV_IND,OBJ_ID,PERM_ID,ROLE_ID,ROLE_PERM_ID,VER_NBR)
  VALUES ('Y','606763510FD096D3E0404F8189D857A2','264','63','519',1)
/
INSERT INTO KRIM_ROLE_PERM_T (ACTV_IND,OBJ_ID,PERM_ID,ROLE_ID,ROLE_PERM_ID,VER_NBR)
  VALUES ('Y','611BE30E404F5818E0404F8189D801C2','289','63','550',1)
/
INSERT INTO KRIM_ROLE_PERM_T (ACTV_IND,OBJ_ID,PERM_ID,ROLE_ID,ROLE_PERM_ID,VER_NBR)
  VALUES ('Y','61815E6C62D3B647E0404F8189D873B3','290','90','552',1)
/
INSERT INTO KRIM_ROLE_PERM_T (ACTV_IND,OBJ_ID,PERM_ID,ROLE_ID,ROLE_PERM_ID,VER_NBR)
  VALUES ('Y','641E580969E41B49E0404F8189D86190','298','63','564',1)
/
INSERT INTO KRIM_ROLE_PERM_T (ACTV_IND,OBJ_ID,PERM_ID,ROLE_ID,ROLE_PERM_ID,VER_NBR)
  VALUES ('Y','641E580969E61B49E0404F8189D86190','299','63','566',1)
/
INSERT INTO KRIM_ROLE_PERM_T (ACTV_IND,OBJ_ID,PERM_ID,ROLE_ID,ROLE_PERM_ID,VER_NBR)
  VALUES ('Y','6314CC58CF5BB7B5E0404F8189D84439','306','63','578',1)
/
INSERT INTO KRIM_ROLE_PERM_T (ACTV_IND,OBJ_ID,PERM_ID,ROLE_ID,ROLE_PERM_ID,VER_NBR)
  VALUES ('Y','662384B381C067A1E0404F8189D868A6','333','83','616',1)
/
INSERT INTO KRIM_ROLE_PERM_T (ACTV_IND,OBJ_ID,PERM_ID,ROLE_ID,ROLE_PERM_ID,VER_NBR)
  VALUES ('Y','662384B381C467A1E0404F8189D868A6','334','66','617',1)
/
INSERT INTO KRIM_ROLE_PERM_T (ACTV_IND,OBJ_ID,PERM_ID,ROLE_ID,ROLE_PERM_ID,VER_NBR)
  VALUES ('Y','662384B381BC67A1E0404F8189D868A6','332','83','618',1)
/
INSERT INTO KRIM_ROLE_PERM_T (ACTV_IND,OBJ_ID,PERM_ID,ROLE_ID,ROLE_PERM_ID,VER_NBR)
  VALUES ('Y','6A869257B306EB49E0404F8189D8697A','334','59','683',1)
/
INSERT INTO KRIM_ROLE_PERM_T (ACTV_IND,OBJ_ID,PERM_ID,ROLE_ID,ROLE_PERM_ID,VER_NBR)
  VALUES ('Y','C7C5063258494135A27DFDA4868F1D8B','701','63','701',1)
/
INSERT INTO KRIM_ROLE_PERM_T (ACTV_IND,OBJ_ID,PERM_ID,ROLE_ID,ROLE_PERM_ID,VER_NBR)
  VALUES ('Y','6F5D842F42DC4018B6D9E8909A841062','702','63','702',1)
/
INSERT INTO KRIM_ROLE_PERM_T (ACTV_IND,OBJ_ID,PERM_ID,ROLE_ID,ROLE_PERM_ID,VER_NBR)
  VALUES ('Y','70D86205B39A411F8A179555254E3737','703','63','703',1)
/
INSERT INTO KRIM_ROLE_PERM_T (ACTV_IND,OBJ_ID,PERM_ID,ROLE_ID,ROLE_PERM_ID,VER_NBR)
  VALUES ('Y','B27306E137C94947BA8AFFD2892722D8','707','63','707',1)
/
INSERT INTO KRIM_ROLE_PERM_T (ACTV_IND,OBJ_ID,PERM_ID,ROLE_ID,ROLE_PERM_ID,VER_NBR)
  VALUES ('Y','4B02BCD0AA764732997C77D139300784','719','63','719',1)
/
INSERT INTO KRIM_ROLE_PERM_T (ACTV_IND,OBJ_ID,PERM_ID,ROLE_ID,ROLE_PERM_ID,VER_NBR)
  VALUES ('Y','7B920FA6145B47F6BF1B67B8EF3E96F4','720','63','720',1)
/
INSERT INTO KRIM_ROLE_PERM_T (ACTV_IND,OBJ_ID,PERM_ID,ROLE_ID,ROLE_PERM_ID,VER_NBR)
  VALUES ('Y','A2794759AE9E47CB970DEABA625EA64C','721','63','721',1)
/
INSERT INTO KRIM_ROLE_PERM_T (ACTV_IND,OBJ_ID,PERM_ID,ROLE_ID,ROLE_PERM_ID,VER_NBR)
  VALUES ('Y','05993B68C8034FD2A758E3441C6AD961','161','1','730',1)
/
INSERT INTO KRIM_ROLE_PERM_T (ACTV_IND,OBJ_ID,PERM_ID,ROLE_ID,ROLE_PERM_ID,VER_NBR)
  VALUES ('Y','25F3589FEFFE4FFCBB4C0577AD722538','162','1','731',1)
/
INSERT INTO KRIM_ROLE_PERM_T (ACTV_IND,OBJ_ID,PERM_ID,ROLE_ID,ROLE_PERM_ID,VER_NBR)
  VALUES ('Y','5AFD381C59B34E9D9BA26B70A739C9A2','163','1','732',1)
/
INSERT INTO KRIM_ROLE_PERM_T (ACTV_IND,OBJ_ID,PERM_ID,ROLE_ID,ROLE_PERM_ID,VER_NBR)
  VALUES ('Y','606763510FDD96D3E0404F8189D857A2','801','63','808',1)
/
INSERT INTO KRIM_ROLE_PERM_T (ACTV_IND,OBJ_ID,PERM_ID,ROLE_ID,ROLE_PERM_ID,VER_NBR)
  VALUES ('Y','606763510FDE96D3E0404F8189D857A2','802','63','809',1)
/
INSERT INTO KRIM_ROLE_PERM_T (ACTV_IND,OBJ_ID,PERM_ID,ROLE_ID,ROLE_PERM_ID,VER_NBR)
  VALUES ('Y','606763510FDF96D3E0404F8189D857A2','803','63','810',1)
/
INSERT INTO KRIM_ROLE_PERM_T (ACTV_IND,OBJ_ID,PERM_ID,ROLE_ID,ROLE_PERM_ID,VER_NBR)
  VALUES ('Y','606763510FE096D3E0404F8189D857A2','807','63','811',1)
/
INSERT INTO KRIM_ROLE_PERM_T (ACTV_IND,OBJ_ID,PERM_ID,ROLE_ID,ROLE_PERM_ID,VER_NBR)
  VALUES ('Y','606763510FED96D3E0404F8189D857A2','814','63','814',1)
/
INSERT INTO KRIM_ROLE_PERM_T (ACTV_IND,OBJ_ID,PERM_ID,ROLE_ID,ROLE_PERM_ID,VER_NBR)
  VALUES ('Y','606763510FEE96D3E0404F8189D857A2','819','63','819',1)
/
INSERT INTO KRIM_ROLE_PERM_T (ACTV_IND,OBJ_ID,PERM_ID,ROLE_ID,ROLE_PERM_ID,VER_NBR)
  VALUES ('Y','606763510FEF96D3E0404F8189D857A2','820','63','820',1)
/
INSERT INTO KRIM_ROLE_PERM_T (ACTV_IND,OBJ_ID,PERM_ID,ROLE_ID,ROLE_PERM_ID,VER_NBR)
  VALUES ('Y','606763510FF096D3E0404F8189D857A2','821','63','821',1)
/
INSERT INTO KRIM_ROLE_PERM_T (ACTV_IND,OBJ_ID,PERM_ID,ROLE_ID,ROLE_PERM_ID,VER_NBR)
  VALUES ('Y','606763510FF196D3E0404F8189D857A2','166','1','826',1)
/
INSERT INTO KRIM_ROLE_PERM_T (ACTV_IND,OBJ_ID,PERM_ID,ROLE_ID,ROLE_PERM_ID,VER_NBR)
  VALUES ('Y','606763510FF596D3E0404F8189D857A2','265','63','827',1)
/
INSERT INTO KRIM_ROLE_PERM_T (ACTV_IND,OBJ_ID,PERM_ID,ROLE_ID,ROLE_PERM_ID,VER_NBR)
  VALUES ('Y','606763510FF696D3E0404F8189D857A2','140','63','828',1)
/
INSERT INTO KRIM_ROLE_PERM_T (ACTV_IND,OBJ_ID,PERM_ID,ROLE_ID,ROLE_PERM_ID,VER_NBR)
  VALUES ('Y','606763510FF796D3E0404F8189D857A2','141','63','829',1)
/
INSERT INTO KRIM_ROLE_PERM_T (ACTV_IND,OBJ_ID,PERM_ID,ROLE_ID,ROLE_PERM_ID,VER_NBR)
  VALUES ('Y','606763510FF896D3E0404F8189D857A2','142','63','830',1)
/
INSERT INTO KRIM_ROLE_PERM_T (ACTV_IND,OBJ_ID,PERM_ID,ROLE_ID,ROLE_PERM_ID,VER_NBR)
  VALUES ('Y','606763510FF996D3E0404F8189D857A2','143','63','831',1)
/
INSERT INTO KRIM_ROLE_PERM_T (ACTV_IND,OBJ_ID,PERM_ID,ROLE_ID,ROLE_PERM_ID,VER_NBR)
  VALUES ('Y','606763510FFA96D3E0404F8189D857A2','144','63','832',1)
/
INSERT INTO KRIM_ROLE_PERM_T (ACTV_IND,OBJ_ID,PERM_ID,ROLE_ID,ROLE_PERM_ID,VER_NBR)
  VALUES ('Y','606763510FFB96D3E0404F8189D857A2','145','63','833',1)
/
INSERT INTO KRIM_ROLE_PERM_T (ACTV_IND,OBJ_ID,PERM_ID,ROLE_ID,ROLE_PERM_ID,VER_NBR)
  VALUES ('Y','5C27A267EF6D7417E0404F8189D830AA','833','63','838',1)
/
INSERT INTO KRIM_ROLE_PERM_T (ACTV_IND,OBJ_ID,PERM_ID,ROLE_ID,ROLE_PERM_ID,VER_NBR)
  VALUES ('Y','5C27A267EF6D7417E0404F8189D830AB','834','63','839',1)
/
INSERT INTO KRIM_ROLE_PERM_T (ACTV_IND,OBJ_ID,PERM_ID,ROLE_ID,ROLE_PERM_ID,VER_NBR)
  VALUES ('Y','5C27A267EF6D7417E0404F8189D830AC','835','63','840',1)
/
INSERT INTO KRIM_ROLE_PERM_T (ACTV_IND,OBJ_ID,PERM_ID,ROLE_ID,ROLE_PERM_ID,VER_NBR)
  VALUES ('Y','5C27A267EF6D7417E0404F8189D830AD','835','63','841',1)
/
INSERT INTO KRIM_ROLE_PERM_T (ACTV_IND,OBJ_ID,PERM_ID,ROLE_ID,ROLE_PERM_ID,VER_NBR)
  VALUES ('Y','70086A2DF17C62E4E0404F8189D863CD','307','63','850',1)
/
INSERT INTO KRIM_ROLE_PERM_T (ACTV_IND,OBJ_ID,PERM_ID,ROLE_ID,ROLE_PERM_ID,VER_NBR)
  VALUES ('Y','E83AB210-EB48-3BDE-2D6F-F6177869AE27','840','63','855',1)
/
--
-- KRIM_ROLE_T
--


INSERT INTO KRIM_ROLE_T (ACTV_IND,DESC_TXT,KIM_TYP_ID,LAST_UPDT_DT,NMSPC_CD,OBJ_ID,ROLE_ID,ROLE_NM,VER_NBR)
  VALUES ('Y','This role derives its members from the users in the Principal table. This role gives users high-level permissions to interact with RICE documents and to login to KUALI.','2',TO_DATE( '20081104143710', 'YYYYMMDDHH24MISS' ),'KUALI','5ADF18B6D4847954E0404F8189D85002','1','User',1)
/
INSERT INTO KRIM_ROLE_T (ACTV_IND,DESC_TXT,KIM_TYP_ID,LAST_UPDT_DT,NMSPC_CD,OBJ_ID,ROLE_ID,ROLE_NM,VER_NBR)
  VALUES ('Y','This role derives its members from users with that have received an action request for a given document.','42',TO_DATE( '20081114141017', 'YYYYMMDDHH24MISS' ),'KR-WKFLW','5BABFACC4F62A8EEE0404F8189D8770F','59','Approve Request Recipient',1)
/
INSERT INTO KRIM_ROLE_T (ACTV_IND,DESC_TXT,KIM_TYP_ID,LAST_UPDT_DT,NMSPC_CD,OBJ_ID,ROLE_ID,ROLE_NM,VER_NBR)
  VALUES ('Y','This role derives its members from the initiator listed within the route log of a given document.','43',TO_DATE( '20081114141017', 'YYYYMMDDHH24MISS' ),'KR-WKFLW','5BABFACC4F63A8EEE0404F8189D8770F','60','Initiator',1)
/
INSERT INTO KRIM_ROLE_T (ACTV_IND,DESC_TXT,KIM_TYP_ID,LAST_UPDT_DT,NMSPC_CD,OBJ_ID,ROLE_ID,ROLE_NM,VER_NBR)
  VALUES ('Y','This role derives its members from the initiator and action request recipients listed within the route log of a given document.','43',TO_DATE( '20081114141017', 'YYYYMMDDHH24MISS' ),'KR-WKFLW','5BABFACC4F64A8EEE0404F8189D8770F','61','Initiator or Reviewer',1)
/
INSERT INTO KRIM_ROLE_T (ACTV_IND,DESC_TXT,KIM_TYP_ID,LAST_UPDT_DT,NMSPC_CD,OBJ_ID,ROLE_ID,ROLE_NM,VER_NBR)
  VALUES ('Y','This role can take superuser actions and blanket approve RICE documents as well as being able to modify and assign permissions, responsibilities and roles belonging to the KR namespaces.','1',TO_DATE( '20081108115522', 'YYYYMMDDHH24MISS' ),'KR-SYS','5B31640F0105ADF1E0404F8189D84647','63','Technical Administrator',1)
/
INSERT INTO KRIM_ROLE_T (ACTV_IND,DESC_TXT,KIM_TYP_ID,LAST_UPDT_DT,NMSPC_CD,OBJ_ID,ROLE_ID,ROLE_NM,VER_NBR)
  VALUES ('Y','This role derives its members from users with the Edit Document permission for a given document type.,','45',TO_DATE( '20081114141017', 'YYYYMMDDHH24MISS' ),'KR-NS','5BABFACC4F61A8EEE0404F8189D8770F','66','Document Editor',1)
/
INSERT INTO KRIM_ROLE_T (ACTV_IND,DESC_TXT,KIM_TYP_ID,LAST_UPDT_DT,NMSPC_CD,OBJ_ID,ROLE_ID,ROLE_NM,VER_NBR)
  VALUES ('Y','This role derives its members from the user who took the Complete action on a given document.','43',TO_DATE( '20081114141017', 'YYYYMMDDHH24MISS' ),'KR-WKFLW','5BABFACC4F65A8EEE0404F8189D8770F','67','Router',1)
/
INSERT INTO KRIM_ROLE_T (ACTV_IND,DESC_TXT,KIM_TYP_ID,LAST_UPDT_DT,NMSPC_CD,OBJ_ID,ROLE_ID,ROLE_NM,VER_NBR)
  VALUES ('Y','This role derives its members from users with the Open Document permission for a given document type.,','60',TO_DATE( '20090113192616', 'YYYYMMDDHH24MISS' ),'KR-NS','606763510FBF96D3E0404F8189D857A2','83','Document Opener',1)
/
INSERT INTO KRIM_ROLE_T (ACTV_IND,DESC_TXT,KIM_TYP_ID,LAST_UPDT_DT,NMSPC_CD,OBJ_ID,ROLE_ID,ROLE_NM,VER_NBR)
  VALUES ('Y','This role derives its members from users with an acknowledge action request in the route log of a given document.','42',TO_DATE( '20090121130202', 'YYYYMMDDHH24MISS' ),'KR-WKFLW','6102F3FA08CE45CFE0404F8189D8317E','88','Acknowledge Request Recipient',1)
/
INSERT INTO KRIM_ROLE_T (ACTV_IND,DESC_TXT,KIM_TYP_ID,LAST_UPDT_DT,NMSPC_CD,OBJ_ID,ROLE_ID,ROLE_NM,VER_NBR)
  VALUES ('Y','This role derives its members from users with an FYI action request in the route log of a given document.','42',TO_DATE( '20090121130203', 'YYYYMMDDHH24MISS' ),'KR-WKFLW','6102F3FA08CF45CFE0404F8189D8317E','89','FYI Request Recipient',1)
/
INSERT INTO KRIM_ROLE_T (ACTV_IND,DESC_TXT,KIM_TYP_ID,LAST_UPDT_DT,NMSPC_CD,OBJ_ID,ROLE_ID,ROLE_NM,VER_NBR)
  VALUES ('Y','This role represents the KR System User, that is the user ID RICE uses when it takes programmed actions.','1',TO_DATE( '20090806170249', 'YYYYMMDDHH24MISS' ),'KR-SYS','61815E6C62D0B647E0404F8189D873B3','90','System User',1)
/
INSERT INTO KRIM_ROLE_T (ACTV_IND,DESC_TXT,KIM_TYP_ID,LAST_UPDT_DT,NMSPC_CD,OBJ_ID,ROLE_ID,ROLE_NM,VER_NBR)
  VALUES ('Y','This role derives its members from users with the Initiate Document permission for a given document type.','66',TO_DATE( '20090806170249', 'YYYYMMDDHH24MISS' ),'KR-SYS','67F145466E8B9160E0404F8189D86771','95','Document Initiator',1)
/
INSERT INTO KRIM_ROLE_T (ACTV_IND,DESC_TXT,KIM_TYP_ID,LAST_UPDT_DT,NMSPC_CD,OBJ_ID,ROLE_ID,ROLE_NM,VER_NBR)
  VALUES ('Y','This role derives its members from users with an Approval action request (that was not generated via the ad-hoc recipients tab) in the route log of a given document.','42',TO_DATE( '20090806170249', 'YYYYMMDDHH24MISS' ),'KR-WKFLW','67F145466EB09160E0404F8189D86771','97','Non-Ad Hoc Approve Request Recipient',1)
/
--
-- KRIM_RSP_TMPL_T
--


INSERT INTO KRIM_RSP_TMPL_T (ACTV_IND,KIM_TYP_ID,NM,NMSPC_CD,OBJ_ID,RSP_TMPL_ID,VER_NBR)
  VALUES ('Y','7','Review','KR-WKFLW','5ADFE172441D6320E0404F8189D85169','1',1)
/
INSERT INTO KRIM_RSP_TMPL_T (ACTV_IND,KIM_TYP_ID,NM,NMSPC_CD,OBJ_ID,RSP_TMPL_ID,VER_NBR)
  VALUES ('Y','54','Resolve Exception','KR-WKFLW','60432A73A6A49F65E0404F8189D86AA4','2',1)
/
--
-- KRIM_TYP_ATTR_T
--


INSERT INTO KRIM_TYP_ATTR_T (ACTV_IND,KIM_ATTR_DEFN_ID,KIM_TYP_ATTR_ID,KIM_TYP_ID,OBJ_ID,SORT_CD,VER_NBR)
  VALUES ('Y','13','1','3','5ADF18B6D4AD7954E0404F8189D85002','a',1)
/
INSERT INTO KRIM_TYP_ATTR_T (ACTV_IND,KIM_ATTR_DEFN_ID,KIM_TYP_ATTR_ID,KIM_TYP_ID,OBJ_ID,SORT_CD,VER_NBR)
  VALUES ('Y','15','10','8','5ADF18B6D4C87954E0404F8189D85002','c',1)
/
INSERT INTO KRIM_TYP_ATTR_T (ACTV_IND,KIM_ATTR_DEFN_ID,KIM_TYP_ATTR_ID,KIM_TYP_ID,OBJ_ID,SORT_CD,VER_NBR)
  VALUES ('Y','13','101','56','603B735FA6BBFE21E0404F8189D8083B','a',1)
/
INSERT INTO KRIM_TYP_ATTR_T (ACTV_IND,KIM_ATTR_DEFN_ID,KIM_TYP_ATTR_ID,KIM_TYP_ID,OBJ_ID,SORT_CD,VER_NBR)
  VALUES ('Y','7','102','56','603B735FA6C2FE21E0404F8189D8083B','b',1)
/
INSERT INTO KRIM_TYP_ATTR_T (ACTV_IND,KIM_ATTR_DEFN_ID,KIM_TYP_ATTR_ID,KIM_TYP_ID,OBJ_ID,SORT_CD,VER_NBR)
  VALUES ('Y','5','103','57','603B735FA6BEFE21E0404F8189D8083B','a',1)
/
INSERT INTO KRIM_TYP_ATTR_T (ACTV_IND,KIM_ATTR_DEFN_ID,KIM_TYP_ATTR_ID,KIM_TYP_ID,OBJ_ID,SORT_CD,VER_NBR)
  VALUES ('Y','44','104','57','603B735FA6BFFE21E0404F8189D8083B','b',1)
/
INSERT INTO KRIM_TYP_ATTR_T (ACTV_IND,KIM_ATTR_DEFN_ID,KIM_TYP_ATTR_ID,KIM_TYP_ID,OBJ_ID,SORT_CD,VER_NBR)
  VALUES ('Y','13','107','54','60432A73A6A39F65E0404F8189D86AA4','a',1)
/
INSERT INTO KRIM_TYP_ATTR_T (ACTV_IND,KIM_ATTR_DEFN_ID,KIM_TYP_ATTR_ID,KIM_TYP_ID,OBJ_ID,SORT_CD,VER_NBR)
  VALUES ('Y','13','108','59','606763510FBD96D3E0404F8189D857A2','a',1)
/
INSERT INTO KRIM_TYP_ATTR_T (ACTV_IND,KIM_ATTR_DEFN_ID,KIM_TYP_ATTR_ID,KIM_TYP_ID,OBJ_ID,SORT_CD,VER_NBR)
  VALUES ('Y','8','109','59','606763510FBE96D3E0404F8189D857A2','b',1)
/
INSERT INTO KRIM_TYP_ATTR_T (ACTV_IND,KIM_ATTR_DEFN_ID,KIM_TYP_ATTR_ID,KIM_TYP_ID,OBJ_ID,SORT_CD,VER_NBR)
  VALUES ('Y','16','11','8','5ADF18B6D4C97954E0404F8189D85002','b',1)
/
INSERT INTO KRIM_TYP_ATTR_T (ACTV_IND,KIM_ATTR_DEFN_ID,KIM_TYP_ATTR_ID,KIM_TYP_ID,OBJ_ID,SORT_CD,VER_NBR)
  VALUES ('Y','4','111','67','67F145466E909160E0404F8189D86771','a',1)
/
INSERT INTO KRIM_TYP_ATTR_T (ACTV_IND,KIM_ATTR_DEFN_ID,KIM_TYP_ATTR_ID,KIM_TYP_ID,OBJ_ID,SORT_CD,VER_NBR)
  VALUES ('Y','13','112','14','67F145466E959160E0404F8189D86771','b',1)
/
INSERT INTO KRIM_TYP_ATTR_T (ACTV_IND,KIM_ATTR_DEFN_ID,KIM_TYP_ATTR_ID,KIM_TYP_ID,OBJ_ID,SORT_CD,VER_NBR)
  VALUES ('Y','13','12','9','5ADF18B6D4CE7954E0404F8189D85002','a',1)
/
INSERT INTO KRIM_TYP_ATTR_T (ACTV_IND,KIM_ATTR_DEFN_ID,KIM_TYP_ATTR_ID,KIM_TYP_ID,OBJ_ID,SORT_CD,VER_NBR)
  VALUES ('Y','9','14','9','5ADF18B6D4D07954E0404F8189D85002','b',1)
/
INSERT INTO KRIM_TYP_ATTR_T (ACTV_IND,KIM_ATTR_DEFN_ID,KIM_TYP_ATTR_ID,KIM_TYP_ID,OBJ_ID,SORT_CD,VER_NBR)
  VALUES ('Y','4','15','10','5ADF18B6D4D87954E0404F8189D85002','a',1)
/
INSERT INTO KRIM_TYP_ATTR_T (ACTV_IND,KIM_ATTR_DEFN_ID,KIM_TYP_ATTR_ID,KIM_TYP_ID,OBJ_ID,SORT_CD,VER_NBR)
  VALUES ('Y','5','16','10','5ADF18B6D4D97954E0404F8189D85002','b',1)
/
INSERT INTO KRIM_TYP_ATTR_T (ACTV_IND,KIM_ATTR_DEFN_ID,KIM_TYP_ATTR_ID,KIM_TYP_ID,OBJ_ID,SORT_CD,VER_NBR)
  VALUES ('Y','5','17','11','5ADF18B6D4DD7954E0404F8189D85002','a',1)
/
INSERT INTO KRIM_TYP_ATTR_T (ACTV_IND,KIM_ATTR_DEFN_ID,KIM_TYP_ATTR_ID,KIM_TYP_ID,OBJ_ID,SORT_CD,VER_NBR)
  VALUES ('Y','6','18','11','5ADF18B6D4DE7954E0404F8189D85002','b',1)
/
INSERT INTO KRIM_TYP_ATTR_T (ACTV_IND,KIM_ATTR_DEFN_ID,KIM_TYP_ATTR_ID,KIM_TYP_ID,OBJ_ID,SORT_CD,VER_NBR)
  VALUES ('Y','4','19','12','5ADF18B6D4E47954E0404F8189D85002','a',1)
/
INSERT INTO KRIM_TYP_ATTR_T (ACTV_IND,KIM_ATTR_DEFN_ID,KIM_TYP_ATTR_ID,KIM_TYP_ID,OBJ_ID,SORT_CD,VER_NBR)
  VALUES ('Y','14','2','4','5ADF18B6D4B67954E0404F8189D85002','a',1)
/
INSERT INTO KRIM_TYP_ATTR_T (ACTV_IND,KIM_ATTR_DEFN_ID,KIM_TYP_ATTR_ID,KIM_TYP_ID,OBJ_ID,SORT_CD,VER_NBR)
  VALUES ('Y','2','20','12','5ADF18B6D4E57954E0404F8189D85002','b',1)
/
INSERT INTO KRIM_TYP_ATTR_T (ACTV_IND,KIM_ATTR_DEFN_ID,KIM_TYP_ATTR_ID,KIM_TYP_ID,OBJ_ID,SORT_CD,VER_NBR)
  VALUES ('Y','3','21','13','5ADF18B6D4E87954E0404F8189D85002','a',1)
/
INSERT INTO KRIM_TYP_ATTR_T (ACTV_IND,KIM_ATTR_DEFN_ID,KIM_TYP_ATTR_ID,KIM_TYP_ID,OBJ_ID,SORT_CD,VER_NBR)
  VALUES ('Y','10','22','14','5ADF18B6D4EB7954E0404F8189D85002','a',1)
/
INSERT INTO KRIM_TYP_ATTR_T (ACTV_IND,KIM_ATTR_DEFN_ID,KIM_TYP_ATTR_ID,KIM_TYP_ID,OBJ_ID,SORT_CD,VER_NBR)
  VALUES ('Y','4','23','15','5ADF18B6D4EE7954E0404F8189D85002','a',1)
/
INSERT INTO KRIM_TYP_ATTR_T (ACTV_IND,KIM_ATTR_DEFN_ID,KIM_TYP_ATTR_ID,KIM_TYP_ID,OBJ_ID,SORT_CD,VER_NBR)
  VALUES ('Y','1','24','15','5ADF18B6D4EF7954E0404F8189D85002','b',1)
/
INSERT INTO KRIM_TYP_ATTR_T (ACTV_IND,KIM_ATTR_DEFN_ID,KIM_TYP_ATTR_ID,KIM_TYP_ID,OBJ_ID,SORT_CD,VER_NBR)
  VALUES ('Y','4','25','16','5ADF18B6D4F37954E0404F8189D85002','a',1)
/
INSERT INTO KRIM_TYP_ATTR_T (ACTV_IND,KIM_ATTR_DEFN_ID,KIM_TYP_ATTR_ID,KIM_TYP_ID,OBJ_ID,SORT_CD,VER_NBR)
  VALUES ('Y','5','26','16','5ADF18B6D4F47954E0404F8189D85002','b',1)
/
INSERT INTO KRIM_TYP_ATTR_T (ACTV_IND,KIM_ATTR_DEFN_ID,KIM_TYP_ATTR_ID,KIM_TYP_ID,OBJ_ID,SORT_CD,VER_NBR)
  VALUES ('Y','11','27','16','5ADF18B6D4F57954E0404F8189D85002','c',1)
/
INSERT INTO KRIM_TYP_ATTR_T (ACTV_IND,KIM_ATTR_DEFN_ID,KIM_TYP_ATTR_ID,KIM_TYP_ID,OBJ_ID,SORT_CD,VER_NBR)
  VALUES ('Y','12','28','17','5ADF18B6D4F87954E0404F8189D85002','a',1)
/
INSERT INTO KRIM_TYP_ATTR_T (ACTV_IND,KIM_ATTR_DEFN_ID,KIM_TYP_ATTR_ID,KIM_TYP_ID,OBJ_ID,SORT_CD,VER_NBR)
  VALUES ('Y','4','29','18','5ADF18B6D4FA7954E0404F8189D85002','a',1)
/
INSERT INTO KRIM_TYP_ATTR_T (ACTV_IND,KIM_ATTR_DEFN_ID,KIM_TYP_ATTR_ID,KIM_TYP_ID,OBJ_ID,SORT_CD,VER_NBR)
  VALUES ('Y','13','3','5','5ADF18B6D4B97954E0404F8189D85002','a',1)
/
INSERT INTO KRIM_TYP_ATTR_T (ACTV_IND,KIM_ATTR_DEFN_ID,KIM_TYP_ATTR_ID,KIM_TYP_ID,OBJ_ID,SORT_CD,VER_NBR)
  VALUES ('Y','18','30','18','5ADF18B6D4FB7954E0404F8189D85002','b',1)
/
INSERT INTO KRIM_TYP_ATTR_T (ACTV_IND,KIM_ATTR_DEFN_ID,KIM_TYP_ATTR_ID,KIM_TYP_ID,OBJ_ID,SORT_CD,VER_NBR)
  VALUES ('Y','4','31','19','5ADF18B6D4FE7954E0404F8189D85002','a',1)
/
INSERT INTO KRIM_TYP_ATTR_T (ACTV_IND,KIM_ATTR_DEFN_ID,KIM_TYP_ATTR_ID,KIM_TYP_ID,OBJ_ID,SORT_CD,VER_NBR)
  VALUES ('Y','19','32','19','5ADF18B6D4FF7954E0404F8189D85002','b',1)
/
INSERT INTO KRIM_TYP_ATTR_T (ACTV_IND,KIM_ATTR_DEFN_ID,KIM_TYP_ATTR_ID,KIM_TYP_ID,OBJ_ID,SORT_CD,VER_NBR)
  VALUES ('Y','4','33','20','5ADF18B6D5027954E0404F8189D85002','a',1)
/
INSERT INTO KRIM_TYP_ATTR_T (ACTV_IND,KIM_ATTR_DEFN_ID,KIM_TYP_ATTR_ID,KIM_TYP_ID,OBJ_ID,SORT_CD,VER_NBR)
  VALUES ('Y','20','34','20','5ADF18B6D5037954E0404F8189D85002','b',1)
/
INSERT INTO KRIM_TYP_ATTR_T (ACTV_IND,KIM_ATTR_DEFN_ID,KIM_TYP_ATTR_ID,KIM_TYP_ID,OBJ_ID,SORT_CD,VER_NBR)
  VALUES ('Y','4','35','21','5ADF18B6D5067954E0404F8189D85002','a',1)
/
INSERT INTO KRIM_TYP_ATTR_T (ACTV_IND,KIM_ATTR_DEFN_ID,KIM_TYP_ATTR_ID,KIM_TYP_ID,OBJ_ID,SORT_CD,VER_NBR)
  VALUES ('Y','21','36','21','5ADF18B6D5077954E0404F8189D85002','b',1)
/
INSERT INTO KRIM_TYP_ATTR_T (ACTV_IND,KIM_ATTR_DEFN_ID,KIM_TYP_ATTR_ID,KIM_TYP_ID,OBJ_ID,SORT_CD,VER_NBR)
  VALUES ('Y','14','4','5','5ADF18B6D4BA7954E0404F8189D85002','b',1)
/
INSERT INTO KRIM_TYP_ATTR_T (ACTV_IND,KIM_ATTR_DEFN_ID,KIM_TYP_ATTR_ID,KIM_TYP_ID,OBJ_ID,SORT_CD,VER_NBR)
  VALUES ('Y','13','7','7','5ADF18B6D4C17954E0404F8189D85002','a',1)
/
INSERT INTO KRIM_TYP_ATTR_T (ACTV_IND,KIM_ATTR_DEFN_ID,KIM_TYP_ATTR_ID,KIM_TYP_ID,OBJ_ID,SORT_CD,VER_NBR)
  VALUES ('Y','16','8','7','5ADF18B6D4C27954E0404F8189D85002','b',1)
/
INSERT INTO KRIM_TYP_ATTR_T (ACTV_IND,KIM_ATTR_DEFN_ID,KIM_TYP_ATTR_ID,KIM_TYP_ID,OBJ_ID,SORT_CD,VER_NBR)
  VALUES ('Y','40','80','7','5C4970B2B2E18277E0404F8189D80B30','c',1)
/
INSERT INTO KRIM_TYP_ATTR_T (ACTV_IND,KIM_ATTR_DEFN_ID,KIM_TYP_ATTR_ID,KIM_TYP_ID,OBJ_ID,SORT_CD,VER_NBR)
  VALUES ('Y','41','81','7','5C4970B2B2E28277E0404F8189D80B30','d',1)
/
INSERT INTO KRIM_TYP_ATTR_T (ACTV_IND,KIM_ATTR_DEFN_ID,KIM_TYP_ATTR_ID,KIM_TYP_ID,OBJ_ID,SORT_CD,VER_NBR)
  VALUES ('Y','42','89','42','5C7D997640695002E0404F8189D86F11','a',1)
/
INSERT INTO KRIM_TYP_ATTR_T (ACTV_IND,KIM_ATTR_DEFN_ID,KIM_TYP_ATTR_ID,KIM_TYP_ID,OBJ_ID,SORT_CD,VER_NBR)
  VALUES ('Y','13','9','8','5ADF18B6D4C77954E0404F8189D85002','a',1)
/
INSERT INTO KRIM_TYP_ATTR_T (ACTV_IND,KIM_ATTR_DEFN_ID,KIM_TYP_ATTR_ID,KIM_TYP_ID,OBJ_ID,SORT_CD,VER_NBR)
  VALUES ('Y','42','91','43','5C7D9976406B5002E0404F8189D86F11','a',1)
/
INSERT INTO KRIM_TYP_ATTR_T (ACTV_IND,KIM_ATTR_DEFN_ID,KIM_TYP_ATTR_ID,KIM_TYP_ID,OBJ_ID,SORT_CD,VER_NBR)
  VALUES ('Y','13','95','52','5C997D14EAC3FE40E0404F8189D87DC5','a',1)
/
INSERT INTO KRIM_TYP_ATTR_T (ACTV_IND,KIM_ATTR_DEFN_ID,KIM_TYP_ATTR_ID,KIM_TYP_ID,OBJ_ID,SORT_CD,VER_NBR)
  VALUES ('Y','16','96','52','5C997D14EAC4FE40E0404F8189D87DC5','b',1)
/
INSERT INTO KRIM_TYP_ATTR_T (ACTV_IND,KIM_ATTR_DEFN_ID,KIM_TYP_ATTR_ID,KIM_TYP_ID,OBJ_ID,SORT_CD,VER_NBR)
  VALUES ('Y','6','97','52','5C997D14EAC5FE40E0404F8189D87DC5','c',1)
/
--
-- KRIM_TYP_T
--


INSERT INTO KRIM_TYP_T (ACTV_IND,KIM_TYP_ID,NM,NMSPC_CD,OBJ_ID,VER_NBR)
  VALUES ('Y','1','Default','KUALI','5ADF18B6D4827954E0404F8189D85002',1)
/
INSERT INTO KRIM_TYP_T (ACTV_IND,KIM_TYP_ID,NM,NMSPC_CD,OBJ_ID,SRVC_NM,VER_NBR)
  VALUES ('Y','10','Namespace or Component','KR-NS','5ADF18B6D4D77954E0404F8189D85002','namespaceOrComponentPermissionTypeService',1)
/
INSERT INTO KRIM_TYP_T (ACTV_IND,KIM_TYP_ID,NM,NMSPC_CD,OBJ_ID,SRVC_NM,VER_NBR)
  VALUES ('Y','11','Component Field','KR-NS','5ADF18B6D4DC7954E0404F8189D85002','componentFieldPermissionTypeService',1)
/
INSERT INTO KRIM_TYP_T (ACTV_IND,KIM_TYP_ID,NM,NMSPC_CD,OBJ_ID,SRVC_NM,VER_NBR)
  VALUES ('Y','12','Namespace or Action','KR-NS','5ADF18B6D4E37954E0404F8189D85002','namespaceOrActionPermissionTypeService',1)
/
INSERT INTO KRIM_TYP_T (ACTV_IND,KIM_TYP_ID,NM,NMSPC_CD,OBJ_ID,SRVC_NM,VER_NBR)
  VALUES ('Y','13','Button','KR-NS','5ADF18B6D4E77954E0404F8189D85002','buttonPermissionTypeService',1)
/
INSERT INTO KRIM_TYP_T (ACTV_IND,KIM_TYP_ID,NM,NMSPC_CD,OBJ_ID,SRVC_NM,VER_NBR)
  VALUES ('Y','14','Edit Mode & Document Type','KR-NS','5ADF18B6D4EA7954E0404F8189D85002','documentTypeAndEditModePermissionTypeService',1)
/
INSERT INTO KRIM_TYP_T (ACTV_IND,KIM_TYP_ID,NM,NMSPC_CD,OBJ_ID,SRVC_NM,VER_NBR)
  VALUES ('Y','15','Batch Feed or Job','KR-NS','5ADF18B6D4ED7954E0404F8189D85002','batchFeedOrJobPermissionTypeService',1)
/
INSERT INTO KRIM_TYP_T (ACTV_IND,KIM_TYP_ID,NM,NMSPC_CD,OBJ_ID,SRVC_NM,VER_NBR)
  VALUES ('Y','16','Parameter','KR-NS','5ADF18B6D4F27954E0404F8189D85002','parameterPermissionTypeService',1)
/
INSERT INTO KRIM_TYP_T (ACTV_IND,KIM_TYP_ID,NM,NMSPC_CD,OBJ_ID,SRVC_NM,VER_NBR)
  VALUES ('Y','17','Campus','KR-NS','5ADF18B6D4F77954E0404F8189D85002','campusRoleService',1)
/
INSERT INTO KRIM_TYP_T (ACTV_IND,KIM_TYP_ID,NM,NMSPC_CD,OBJ_ID,SRVC_NM,VER_NBR)
  VALUES ('Y','18','Role','KR-IDM','5ADF18B6D4F97954E0404F8189D85002','rolePermissionTypeService',1)
/
INSERT INTO KRIM_TYP_T (ACTV_IND,KIM_TYP_ID,NM,NMSPC_CD,OBJ_ID,SRVC_NM,VER_NBR)
  VALUES ('Y','19','Permission','KR-IDM','5ADF18B6D4FD7954E0404F8189D85002','permissionPermissionTypeService',1)
/
INSERT INTO KRIM_TYP_T (ACTV_IND,KIM_TYP_ID,NM,NMSPC_CD,OBJ_ID,SRVC_NM,VER_NBR)
  VALUES ('Y','2','Derived Role: Principal','KR-IDM','5ADF18B6D4837954E0404F8189D85002','activePrincipalRoleTypeService',1)
/
INSERT INTO KRIM_TYP_T (ACTV_IND,KIM_TYP_ID,NM,NMSPC_CD,OBJ_ID,SRVC_NM,VER_NBR)
  VALUES ('Y','20','Responsibility','KR-IDM','5ADF18B6D5017954E0404F8189D85002','responsibilityPermissionTypeService',1)
/
INSERT INTO KRIM_TYP_T (ACTV_IND,KIM_TYP_ID,NM,NMSPC_CD,OBJ_ID,SRVC_NM,VER_NBR)
  VALUES ('Y','21','Group','KR-IDM','5ADF18B6D5057954E0404F8189D85002','groupPermissionTypeService',1)
/
INSERT INTO KRIM_TYP_T (ACTV_IND,KIM_TYP_ID,NM,NMSPC_CD,OBJ_ID,SRVC_NM,VER_NBR)
  VALUES ('Y','3','Document Type (Permission)','KR-SYS','5ADF18B6D4AC7954E0404F8189D85002','documentTypePermissionTypeService',1)
/
INSERT INTO KRIM_TYP_T (ACTV_IND,KIM_TYP_ID,NM,NMSPC_CD,OBJ_ID,SRVC_NM,VER_NBR)
  VALUES ('Y','4','Action Request Type','KR-WKFLW','5ADF18B6D4B57954E0404F8189D85002','actionRequestTypePermissionTypeService',1)
/
INSERT INTO KRIM_TYP_T (ACTV_IND,KIM_TYP_ID,NM,NMSPC_CD,OBJ_ID,SRVC_NM,VER_NBR)
  VALUES ('Y','42','Derived Role: Action Request','KR-WKFLW','5ADF18B6D53B7954E0404F8189D85002','actionRequestDerivedRoleTypeService',1)
/
INSERT INTO KRIM_TYP_T (ACTV_IND,KIM_TYP_ID,NM,NMSPC_CD,OBJ_ID,SRVC_NM,VER_NBR)
  VALUES ('Y','43','Derived Role: Route Log','KR-WKFLW','5ADF18B6D53C7954E0404F8189D85002','routeLogDerivedRoleTypeService',1)
/
INSERT INTO KRIM_TYP_T (ACTV_IND,KIM_TYP_ID,NM,NMSPC_CD,OBJ_ID,SRVC_NM,VER_NBR)
  VALUES ('Y','45','Derived Role: Permission (Edit Document)','KR-NS','5B6013B3AD131A9CE0404F8189D87094','documentEditorRoleTypeService',1)
/
INSERT INTO KRIM_TYP_T (ACTV_IND,KIM_TYP_ID,NM,NMSPC_CD,OBJ_ID,SRVC_NM,VER_NBR)
  VALUES ('Y','5','Ad Hoc Review','KR-WKFLW','5ADF18B6D4B87954E0404F8189D85002','adhocReviewPermissionTypeService',1)
/
INSERT INTO KRIM_TYP_T (ACTV_IND,KIM_TYP_ID,NM,NMSPC_CD,OBJ_ID,SRVC_NM,VER_NBR)
  VALUES ('Y','52','Document Type, Routing Node & Field(s)','KR-SYS','5C997D14EAC2FE40E0404F8189D87DC5','documentTypeAndNodeAndFieldsPermissionTypeService',1)
/
INSERT INTO KRIM_TYP_T (ACTV_IND,KIM_TYP_ID,NM,NMSPC_CD,OBJ_ID,SRVC_NM,VER_NBR)
  VALUES ('Y','54','Document Type (Responsibility)','KR-KEW','60432A73A6A29F65E0404F8189D86AA4','documentTypeResponsibilityTypeService',1)
/
INSERT INTO KRIM_TYP_T (ACTV_IND,KIM_TYP_ID,NM,NMSPC_CD,OBJ_ID,SRVC_NM,VER_NBR)
  VALUES ('Y','56','Document Type & Existing Records Only','KR-NS','603B735FA6B9FE21E0404F8189D8083B','documentTypeAndExistingRecordsOnlyPermissionTypeService',1)
/
INSERT INTO KRIM_TYP_T (ACTV_IND,KIM_TYP_ID,NM,NMSPC_CD,OBJ_ID,SRVC_NM,VER_NBR)
  VALUES ('Y','57','Component Section','KR-NS','603B735FA6BDFE21E0404F8189D8083B','componentSectionPermissionTypeService',1)
/
INSERT INTO KRIM_TYP_T (ACTV_IND,KIM_TYP_ID,NM,NMSPC_CD,OBJ_ID,SRVC_NM,VER_NBR)
  VALUES ('Y','59','Document Type & Relationship to Note Author','KR-NS','606763510FBB96D3E0404F8189D857A2','documentTypeAndRelationshipToNoteAuthorPermissionTypeService',1)
/
INSERT INTO KRIM_TYP_T (ACTV_IND,KIM_TYP_ID,NM,NMSPC_CD,OBJ_ID,SRVC_NM,VER_NBR)
  VALUES ('Y','60','Derived Role: Permission (Open Document)','KR-NS','606763510FBC96D3E0404F8189D857A2','documentOpenerRoleTypeService',1)
/
INSERT INTO KRIM_TYP_T (ACTV_IND,KIM_TYP_ID,NM,NMSPC_CD,OBJ_ID,SRVC_NM,VER_NBR)
  VALUES ('Y','66','Derived Role: Permission (Initiate Document)','KR-SYS','67F145466E8A9160E0404F8189D86771','documentInitiatorRoleTypeService',1)
/
INSERT INTO KRIM_TYP_T (ACTV_IND,KIM_TYP_ID,NM,NMSPC_CD,OBJ_ID,SRVC_NM,VER_NBR)
  VALUES ('Y','67','Namespace','KR-NS','67F145466E8F9160E0404F8189D86771','namespacePermissionTypeService',1)
/
INSERT INTO KRIM_TYP_T (ACTV_IND,KIM_TYP_ID,NM,NMSPC_CD,OBJ_ID,SRVC_NM,VER_NBR)
  VALUES ('Y','7','Document Type, Routing Node & Action Information','KR-WKFLW','5ADF18B6D4C07954E0404F8189D85002','reviewResponsibilityTypeService',1)
/
INSERT INTO KRIM_TYP_T (ACTV_IND,KIM_TYP_ID,NM,NMSPC_CD,OBJ_ID,SRVC_NM,VER_NBR)
  VALUES ('Y','8','Document Type & Routing Node or State','KR-SYS','5ADF18B6D4C67954E0404F8189D85002','documentTypeAndNodeOrStatePermissionTypeService',1)
/
INSERT INTO KRIM_TYP_T (ACTV_IND,KIM_TYP_ID,NM,NMSPC_CD,OBJ_ID,SRVC_NM,VER_NBR)
  VALUES ('Y','9','Document Type & Attachment Type','KR-NS','5ADF18B6D4CD7954E0404F8189D85002','documentTypeAndAttachmentTypePermissionTypeService',1)
/
--
-- KRNS_CMP_TYP_T
--


INSERT INTO KRNS_CMP_TYP_T (ACTV_IND,CAMPUS_TYP_CD,CMP_TYP_NM,DOBJ_MAINT_CD_ACTV_IND,OBJ_ID,VER_NBR)
  VALUES ('Y','B','BOTH','Y','1EFD72846DC90024E043814FD8810024',1)
/
INSERT INTO KRNS_CMP_TYP_T (ACTV_IND,CAMPUS_TYP_CD,CMP_TYP_NM,DOBJ_MAINT_CD_ACTV_IND,OBJ_ID,VER_NBR)
  VALUES ('Y','F','FISCAL','Y','1EFD72846DCA0024E043814FD8810024',1)
/
INSERT INTO KRNS_CMP_TYP_T (ACTV_IND,CAMPUS_TYP_CD,CMP_TYP_NM,DOBJ_MAINT_CD_ACTV_IND,OBJ_ID,VER_NBR)
  VALUES ('Y','P','PHYSICAL','Y','1EFD72846DCB0024E043814FD8810024',1)
/
--
-- KRNS_NMSPC_T
--


INSERT INTO KRNS_NMSPC_T (ACTV_IND,APPL_NMSPC_CD,NM,NMSPC_CD,OBJ_ID,VER_NBR)
  VALUES ('Y','RICE','Service Bus','KR-BUS','5B960CFDBB370FDFE0404F8189D83CBD',1)
/
INSERT INTO KRNS_NMSPC_T (ACTV_IND,APPL_NMSPC_CD,NM,NMSPC_CD,OBJ_ID,VER_NBR)
  VALUES ('Y','RICE','Identity Management','KR-IDM','61645D045B0005D7E0404F8189D849B1',1)
/
INSERT INTO KRNS_NMSPC_T (ACTV_IND,APPL_NMSPC_CD,NM,NMSPC_CD,OBJ_ID,VER_NBR)
  VALUES ('Y','RICE','Kuali Nervous System','KR-NS','53680C68F595AD9BE0404F8189D80A6C',1)
/
INSERT INTO KRNS_NMSPC_T (ACTV_IND,APPL_NMSPC_CD,NM,NMSPC_CD,OBJ_ID,VER_NBR)
  VALUES ('Y','RICE','Notification','KR-NTFCN','5B960CFDBB360FDFE0404F8189D83CBD',1)
/
INSERT INTO KRNS_NMSPC_T (ACTV_IND,APPL_NMSPC_CD,NM,NMSPC_CD,OBJ_ID,VER_NBR)
  VALUES ('Y','RICE','Enterprise Infrastructure','KR-SYS','5B960CFDBB390FDFE0404F8189D83CBD',1)
/
INSERT INTO KRNS_NMSPC_T (ACTV_IND,APPL_NMSPC_CD,NM,NMSPC_CD,OBJ_ID,VER_NBR)
  VALUES ('Y','RICE','Workflow','KR-WKFLW','5E1D690C419B3E2EE0404F8189D82677',0)
/
INSERT INTO KRNS_NMSPC_T (ACTV_IND,NM,NMSPC_CD,OBJ_ID,VER_NBR)
  VALUES ('Y','Kuali Systems','KUALI','5ADF18B6D4817954E0404F8189D85002',1)
/
--
-- KRNS_NTE_TYP_T
--


INSERT INTO KRNS_NTE_TYP_T (ACTV_IND,NTE_TYP_CD,OBJ_ID,TYP_DESC_TXT,VER_NBR)
  VALUES ('Y','BO','53680C68F5A9AD9BE0404F8189D80A6C','DOCUMENT BUSINESS OBJECT',1)
/
INSERT INTO KRNS_NTE_TYP_T (ACTV_IND,NTE_TYP_CD,OBJ_ID,TYP_DESC_TXT,VER_NBR)
  VALUES ('Y','DH','53680C68F5AAAD9BE0404F8189D80A6C','DOCUMENT HEADER',1)
/
--
-- KRNS_PARM_DTL_TYP_T
--


INSERT INTO KRNS_PARM_DTL_TYP_T (ACTV_IND,NM,NMSPC_CD,OBJ_ID,PARM_DTL_TYP_CD,VER_NBR)
  VALUES ('Y','All','KR-IDM','69A9BABE4A0BBD56E0404F8189D82B0F','All',1)
/
INSERT INTO KRNS_PARM_DTL_TYP_T (ACTV_IND,NM,NMSPC_CD,OBJ_ID,PARM_DTL_TYP_CD,VER_NBR)
  VALUES ('Y','Batch','KR-IDM','69A9BABE4A0CBD56E0404F8189D82B0F','Batch',1)
/
INSERT INTO KRNS_PARM_DTL_TYP_T (ACTV_IND,NM,NMSPC_CD,OBJ_ID,PARM_DTL_TYP_CD,VER_NBR)
  VALUES ('Y','Document','KR-IDM','69A9BABE4A0DBD56E0404F8189D82B0F','Document',1)
/
INSERT INTO KRNS_PARM_DTL_TYP_T (ACTV_IND,NM,NMSPC_CD,OBJ_ID,PARM_DTL_TYP_CD,VER_NBR)
  VALUES ('Y','Lookup','KR-IDM','69A9BABE4A0EBD56E0404F8189D82B0F','Lookup',1)
/
INSERT INTO KRNS_PARM_DTL_TYP_T (ACTV_IND,NM,NMSPC_CD,OBJ_ID,PARM_DTL_TYP_CD,VER_NBR)
  VALUES ('Y','Purge Pending Attachments Step','KR-IDM','69A9BABE4A0FBD56E0404F8189D82B0F','PurgePendingAttachmentsStep',1)
/
INSERT INTO KRNS_PARM_DTL_TYP_T (ACTV_IND,NM,NMSPC_CD,OBJ_ID,PARM_DTL_TYP_CD,VER_NBR)
  VALUES ('Y','Purge Session Documents Step','KR-IDM','69A9BABE4A10BD56E0404F8189D82B0F','PurgeSessionDocumentsStep',1)
/
INSERT INTO KRNS_PARM_DTL_TYP_T (ACTV_IND,NM,NMSPC_CD,OBJ_ID,PARM_DTL_TYP_CD,VER_NBR)
  VALUES ('Y','Schedule Step','KR-IDM','69A9BABE4A11BD56E0404F8189D82B0F','ScheduleStep',1)
/
INSERT INTO KRNS_PARM_DTL_TYP_T (ACTV_IND,NM,NMSPC_CD,OBJ_ID,PARM_DTL_TYP_CD,VER_NBR)
  VALUES ('Y','All','KR-NS','53680C68F596AD9BE0404F8189D80A6C','All',1)
/
INSERT INTO KRNS_PARM_DTL_TYP_T (ACTV_IND,NM,NMSPC_CD,OBJ_ID,PARM_DTL_TYP_CD,VER_NBR)
  VALUES ('Y','Batch','KR-NS','53680C68F597AD9BE0404F8189D80A6C','Batch',1)
/
INSERT INTO KRNS_PARM_DTL_TYP_T (ACTV_IND,NM,NMSPC_CD,OBJ_ID,PARM_DTL_TYP_CD,VER_NBR)
  VALUES ('Y','Document','KR-NS','53680C68F598AD9BE0404F8189D80A6C','Document',1)
/
INSERT INTO KRNS_PARM_DTL_TYP_T (ACTV_IND,NM,NMSPC_CD,OBJ_ID,PARM_DTL_TYP_CD,VER_NBR)
  VALUES ('Y','Lookup','KR-NS','53680C68F599AD9BE0404F8189D80A6C','Lookup',1)
/
INSERT INTO KRNS_PARM_DTL_TYP_T (ACTV_IND,NM,NMSPC_CD,OBJ_ID,PARM_DTL_TYP_CD,VER_NBR)
  VALUES ('Y','Purge Pending Attachments Step','KR-NS','5A689075D3577AEBE0404F8189D80321','PurgePendingAttachmentsStep',1)
/
INSERT INTO KRNS_PARM_DTL_TYP_T (ACTV_IND,NM,NMSPC_CD,OBJ_ID,PARM_DTL_TYP_CD,VER_NBR)
  VALUES ('Y','Purge Session Documents Step','KR-NS','5A689075D3567AEBE0404F8189D80321','PurgeSessionDocumentsStep',1)
/
INSERT INTO KRNS_PARM_DTL_TYP_T (ACTV_IND,NM,NMSPC_CD,OBJ_ID,PARM_DTL_TYP_CD,VER_NBR)
  VALUES ('Y','Schedule Step','KR-NS','5A689075D3587AEBE0404F8189D80321','ScheduleStep',1)
/
INSERT INTO KRNS_PARM_DTL_TYP_T (ACTV_IND,NM,NMSPC_CD,OBJ_ID,PARM_DTL_TYP_CD,VER_NBR)
  VALUES ('Y','Action List','KR-WKFLW','1821D8BAB21E498F9FB1ECCA25C37F9B','ActionList',1)
/
INSERT INTO KRNS_PARM_DTL_TYP_T (ACTV_IND,NM,NMSPC_CD,OBJ_ID,PARM_DTL_TYP_CD,VER_NBR)
  VALUES ('Y','All','KR-WKFLW','705611FB54134417E0404F8189D8453B','All',1)
/
INSERT INTO KRNS_PARM_DTL_TYP_T (ACTV_IND,NM,NMSPC_CD,OBJ_ID,PARM_DTL_TYP_CD,VER_NBR)
  VALUES ('Y','Backdoor','KR-WKFLW','F7E44233C2C440FFB1A399548951160A','Backdoor',1)
/
INSERT INTO KRNS_PARM_DTL_TYP_T (ACTV_IND,NM,NMSPC_CD,OBJ_ID,PARM_DTL_TYP_CD,VER_NBR)
  VALUES ('Y','eDocLite','KR-WKFLW','51DD5B9FACDD4EDAA9CA8D53A82FCCCA','EDocLite  ',1)
/
INSERT INTO KRNS_PARM_DTL_TYP_T (ACTV_IND,NM,NMSPC_CD,OBJ_ID,PARM_DTL_TYP_CD,VER_NBR)
  VALUES ('Y','Feature','KR-WKFLW','BBD9976498A4441F904013004F3D70B3','Feature',1)
/
INSERT INTO KRNS_PARM_DTL_TYP_T (ACTV_IND,NM,NMSPC_CD,OBJ_ID,PARM_DTL_TYP_CD,VER_NBR)
  VALUES ('Y','Global Reviewer','KR-WKFLW','C21B0C6229144F6FBC52A10A38E51E3B','GlobalReviewer',1)
/
INSERT INTO KRNS_PARM_DTL_TYP_T (ACTV_IND,NM,NMSPC_CD,OBJ_ID,PARM_DTL_TYP_CD,VER_NBR)
  VALUES ('Y','Mailer','KR-WKFLW','5DB9D1433E214325BE380C82762A223B','Mailer',1)
/
INSERT INTO KRNS_PARM_DTL_TYP_T (ACTV_IND,NM,NMSPC_CD,OBJ_ID,PARM_DTL_TYP_CD,VER_NBR)
  VALUES ('Y','Note','KR-WKFLW','868D39EC269B4402B3136C74C2342F22','Note',1)
/
INSERT INTO KRNS_PARM_DTL_TYP_T (ACTV_IND,NM,NMSPC_CD,OBJ_ID,PARM_DTL_TYP_CD,VER_NBR)
  VALUES ('Y','Notification','KR-WKFLW','D04AFB1812E34723ABEB64986AC61DC9','Notification',1)
/
INSERT INTO KRNS_PARM_DTL_TYP_T (ACTV_IND,NM,NMSPC_CD,OBJ_ID,PARM_DTL_TYP_CD,VER_NBR)
  VALUES ('Y','Quick Link','KR-WKFLW','3E26DA76458A46D68CBAF209DA036157','QuickLinks',1)
/
INSERT INTO KRNS_PARM_DTL_TYP_T (ACTV_IND,NM,NMSPC_CD,OBJ_ID,PARM_DTL_TYP_CD,VER_NBR)
  VALUES ('Y','Routing','KR-WKFLW','583C2D3562D44DBAA5FEA998EB601DC9','Route',1)
/
INSERT INTO KRNS_PARM_DTL_TYP_T (ACTV_IND,NM,NMSPC_CD,OBJ_ID,PARM_DTL_TYP_CD,VER_NBR)
  VALUES ('Y','Route Queue','KR-WKFLW','D4F6DDEF69B24265AA2A170A62A1CADB','RouteQueue',1)
/
--
-- KRNS_PARM_T
--


INSERT INTO KRNS_PARM_T (APPL_NMSPC_CD,CONS_CD,NMSPC_CD,OBJ_ID,PARM_DESC_TXT,PARM_DTL_TYP_CD,PARM_NM,PARM_TYP_CD,TXT,VER_NBR)
  VALUES ('KUALI','A','KR-IDM','2238b58e-8fb9-102c-9461-def224dad9b3','The maximum number of role or group members to display at once on their documents. If the number is above this value, the document will switch into a paging mode with only this many rows displayed at a time.','Document','MAX_MEMBERS_PER_PAGE','CONFG','20',1)
/
INSERT INTO KRNS_PARM_T (APPL_NMSPC_CD,CONS_CD,NMSPC_CD,OBJ_ID,PARM_DTL_TYP_CD,PARM_NM,PARM_TYP_CD,TXT,VER_NBR)
  VALUES ('KUALI','A','KR-IDM','61645D045B0105D7E0404F8189D849B1','PersonDocumentName','PREFIXES','CONFG','Ms;Mrs;Mr;Dr',1)
/
INSERT INTO KRNS_PARM_T (APPL_NMSPC_CD,CONS_CD,NMSPC_CD,OBJ_ID,PARM_DTL_TYP_CD,PARM_NM,PARM_TYP_CD,TXT,VER_NBR)
  VALUES ('KUALI','A','KR-IDM','61645D045B0205D7E0404F8189D849B1','PersonDocumentName','SUFFIXES','CONFG','Jr;Sr;Mr;Md',1)
/
INSERT INTO KRNS_PARM_T (APPL_NMSPC_CD,CONS_CD,NMSPC_CD,OBJ_ID,PARM_DESC_TXT,PARM_DTL_TYP_CD,PARM_NM,PARM_TYP_CD,TXT,VER_NBR)
  VALUES ('KUALI','A','KR-NS','53680C68F59AAD9BE0404F8189D80A6C','Flag for enabling/disabling (Y/N) the demonstration encryption check.','All','CHECK_ENCRYPTION_SERVICE_OVERRIDE_IND','CONFG','Y',1)
/
INSERT INTO KRNS_PARM_T (APPL_NMSPC_CD,CONS_CD,NMSPC_CD,OBJ_ID,PARM_DESC_TXT,PARM_DTL_TYP_CD,PARM_NM,PARM_TYP_CD,TXT,VER_NBR)
  VALUES ('KUALI','A','KR-NS','664F8ABEC723DBCDE0404F8189D85427','A single date format string that the DateTimeService will use to format dates to be used in a file name when DateTimeServiceImpl.toDateStringForFilename(Date) is called. For a more technical description of how characters in the parameter value will be interpreted, please consult the javadocs for java.text.SimpleDateFormat. Any changes will be applied when the application is restarted.','All','DATE_TO_STRING_FORMAT_FOR_FILE_NAME','CONFG','yyyyMMdd',1)
/
INSERT INTO KRNS_PARM_T (APPL_NMSPC_CD,CONS_CD,NMSPC_CD,OBJ_ID,PARM_DESC_TXT,PARM_DTL_TYP_CD,PARM_NM,PARM_TYP_CD,TXT,VER_NBR)
  VALUES ('KUALI','A','KR-NS','664F8ABEC725DBCDE0404F8189D85427','A single date format string that the DateTimeService will use to format a date to be displayed on a web page. For a more technical description of how characters in the parameter value will be interpreted, please consult the javadocs for java.text.SimpleDateFormat. Any changes will be applied when the application is restarted.','All','DATE_TO_STRING_FORMAT_FOR_USER_INTERFACE','CONFG','MM/dd/yyyy',1)
/
INSERT INTO KRNS_PARM_T (APPL_NMSPC_CD,CONS_CD,NMSPC_CD,OBJ_ID,PARM_DESC_TXT,PARM_DTL_TYP_CD,PARM_NM,PARM_TYP_CD,TXT,VER_NBR)
  VALUES ('KUALI','A','KR-NS','64B87B4C5E3B8F4CE0404F8189D8291A','Used as the default country code when relating records that do not have a country code to records that do have a country code, e.g. validating a zip code where the country is not collected.','All','DEFAULT_COUNTRY','CONFG','US',1)
/
INSERT INTO KRNS_PARM_T (APPL_NMSPC_CD,CONS_CD,NMSPC_CD,OBJ_ID,PARM_DESC_TXT,PARM_DTL_TYP_CD,PARM_NM,PARM_TYP_CD,TXT,VER_NBR)
  VALUES ('KUALI','A','KR-NS','53680C68F59BAD9BE0404F8189D80A6C','Flag for enabling/disabling direct inquiries on screens that are drawn by the nervous system (i.e. lookups and maintenance documents)','All','ENABLE_DIRECT_INQUIRIES_IND','CONFG','Y',1)
/
INSERT INTO KRNS_PARM_T (APPL_NMSPC_CD,CONS_CD,NMSPC_CD,OBJ_ID,PARM_DESC_TXT,PARM_DTL_TYP_CD,PARM_NM,PARM_TYP_CD,TXT,VER_NBR)
  VALUES ('KUALI','A','KR-NS','53680C68F59CAD9BE0404F8189D80A6C','Indicates whether field level help links are enabled on lookup pages and documents.','All','ENABLE_FIELD_LEVEL_HELP_IND','CONFG','N',1)
/
INSERT INTO KRNS_PARM_T (APPL_NMSPC_CD,CONS_CD,NMSPC_CD,OBJ_ID,PARM_DESC_TXT,PARM_DTL_TYP_CD,PARM_NM,PARM_TYP_CD,TXT,VER_NBR)
  VALUES ('KUALI','A','KR-NS','53680C68F59DAD9BE0404F8189D80A6C','Maximum file upload size for the application. Used by PojoFormBase. Must be an integer, optionally followed by "K", "M", or "G". Only used if no other upload limits are in effect.','All','MAX_FILE_SIZE_DEFAULT_UPLOAD','CONFG','5M',1)
/
INSERT INTO KRNS_PARM_T (APPL_NMSPC_CD,CONS_CD,NMSPC_CD,OBJ_ID,PARM_DESC_TXT,PARM_DTL_TYP_CD,PARM_NM,PARM_TYP_CD,TXT,VER_NBR)
  VALUES ('KUALI','A','KR-NS','5a5fbe94-846f-102c-8db0-c405cae621f3','A semi-colon delimted list of regular expressions that identify 
potentially sensitive data in strings.  These patterns will be matched 
against notes, document explanations, and routing annotations.','All','SENSITIVE_DATA_PATTERNS','CONFG','[0-9]{9};[0-9]{3}-[0-9]{2}-[0-9]{4}',1)
/
INSERT INTO KRNS_PARM_T (APPL_NMSPC_CD,CONS_CD,NMSPC_CD,OBJ_ID,PARM_DESC_TXT,PARM_DTL_TYP_CD,PARM_NM,PARM_TYP_CD,TXT,VER_NBR)
  VALUES ('KUALI','A','KR-NS','e7d133f3-b5fe-11df-ad0a-d18f5709259f','If set to ''Y'' when sensitive data is found the user will be prompted to continue the action or cancel. If this is set to ''N'' the user will be presented with an error message and will not be allowed to continue with the action until the sensitive data is removed.','All','SENSITIVE_DATA_PATTERNS_WARNING_IND','CONFG','N',1)
/
INSERT INTO KRNS_PARM_T (APPL_NMSPC_CD,CONS_CD,NMSPC_CD,OBJ_ID,PARM_DESC_TXT,PARM_DTL_TYP_CD,PARM_NM,PARM_TYP_CD,TXT,VER_NBR)
  VALUES ('KUALI','A','KR-NS','664F8ABEC722DBCDE0404F8189D85427','A semi-colon delimted list of strings representing date formats that the DateTimeService will use to parse dates when DateTimeServiceImpl.convertToSqlDate(String) or DateTimeServiceImpl.convertToDate(String) is called. Note that patterns will be applied in the order listed (and the first applicable one will be used). For a more technical description of how characters in the parameter value will be interpreted, please consult the javadocs for java.text.SimpleDateFormat. Any changes will be applied when the application is restarted.','All','STRING_TO_DATE_FORMATS','CONFG','MM/dd/yyyy hh:mm a;MM/dd/yy;MM/dd/yyyy;MM-dd-yy;MMddyy;MMMM dd;yyyy;MM/dd/yy HH:mm:ss;MM/dd/yyyy HH:mm:ss;MM-dd-yy HH:mm:ss;MMddyy HH:mm:ss;MMMM dd HH:mm:ss;yyyy HH:mm:ss',1)
/
INSERT INTO KRNS_PARM_T (APPL_NMSPC_CD,CONS_CD,NMSPC_CD,OBJ_ID,PARM_DESC_TXT,PARM_DTL_TYP_CD,PARM_NM,PARM_TYP_CD,TXT,VER_NBR)
  VALUES ('KUALI','A','KR-NS','664F8ABEC727DBCDE0404F8189D85427','A semi-colon delimted list of strings representing date formats that the DateTimeService will use to parse date and times when DateTimeServiceImpl.convertToDateTime(String) or DateTimeServiceImpl.convertToSqlTimestamp(String) is called. Note that patterns will be applied in the order listed (and the first applicable one will be used). For a more technical description of how characters in the parameter value will be interpreted, please consult the javadocs for java.text.SimpleDateFormat. Any changes will be applied when the application is restarted.','All','STRING_TO_TIMESTAMP_FORMATS','CONFG','MM/dd/yyyy hh:mm a;MM/dd/yy;MM/dd/yyyy;MM-dd-yy;MMddyy;MMMM dd;yyyy;MM/dd/yy HH:mm:ss;MM/dd/yyyy HH:mm:ss;MM-dd-yy HH:mm:ss;MMddyy HH:mm:ss;MMMM dd HH:mm:ss;yyyy HH:mm:ss',1)
/
INSERT INTO KRNS_PARM_T (APPL_NMSPC_CD,CONS_CD,NMSPC_CD,OBJ_ID,PARM_DESC_TXT,PARM_DTL_TYP_CD,PARM_NM,PARM_TYP_CD,TXT,VER_NBR)
  VALUES ('KUALI','A','KR-NS','664F8ABEC724DBCDE0404F8189D85427','A single date format string that the DateTimeService will use to format a date and time string to be used in a file name when DateTimeServiceImpl.toDateTimeStringForFilename(Date) is called.. For a more technical description of how characters in the parameter value will be interpreted, please consult the javadocs for java.text.SimpleDateFormat. Any changes will be applied when the application is restarted.','All','TIMESTAMP_TO_STRING_FORMAT_FOR_FILE_NAME','CONFG','yyyyMMdd-HH-mm-ss-S',1)
/
INSERT INTO KRNS_PARM_T (APPL_NMSPC_CD,CONS_CD,NMSPC_CD,OBJ_ID,PARM_DESC_TXT,PARM_DTL_TYP_CD,PARM_NM,PARM_TYP_CD,TXT,VER_NBR)
  VALUES ('KUALI','A','KR-NS','664F8ABEC726DBCDE0404F8189D85427','A single date format string that the DateTimeService will use to format a date and time to be displayed on a web page. For a more technical description of how characters in the parameter value will be interpreted, please consult the javadocs for java.text.SimpleDateFormat. Any changes will be applied when the application is restarted.','All','TIMESTAMP_TO_STRING_FORMAT_FOR_USER_INTERFACE','CONFG','MM/dd/yyyy hh:mm a',1)
/
INSERT INTO KRNS_PARM_T (APPL_NMSPC_CD,CONS_CD,NMSPC_CD,OBJ_ID,PARM_DESC_TXT,PARM_DTL_TYP_CD,PARM_NM,PARM_TYP_CD,TXT,VER_NBR)
  VALUES ('KUALI','A','KR-NS','5A689075D35E7AEBE0404F8189D80321','Batch file types that are active options for the file upload screen.','Batch','ACTIVE_FILE_TYPES','CONFG','collectorInputFileType;procurementCardInputFileType;enterpriseFeederFileSetType;assetBarcodeInventoryInputFileType;customerLoadInputFileType',1)
/
INSERT INTO KRNS_PARM_T (APPL_NMSPC_CD,CONS_CD,NMSPC_CD,OBJ_ID,PARM_DESC_TXT,PARM_DTL_TYP_CD,PARM_NM,PARM_TYP_CD,TXT,VER_NBR)
  VALUES ('KUALI','A','KR-NS','92AB93B362565F2CE0404F8189D8219A','Controls whether the nervous system will show the blanket approve button to a user who is authorized for blanket approval but is neither the initiator of the particular document nor the recipient of an active, pending, approve action request.','Document','ALLOW_ENROUTE_BLANKET_APPROVE_WITHOUT_APPROVAL_REQUEST_IND','CONFG','N',1)
/
INSERT INTO KRNS_PARM_T (APPL_NMSPC_CD,CONS_CD,NMSPC_CD,OBJ_ID,PARM_DESC_TXT,PARM_DTL_TYP_CD,PARM_NM,PARM_TYP_CD,TXT,VER_NBR)
  VALUES ('KUALI','A','KR-NS','53680C68F59EAD9BE0404F8189D80A6C','If Y, the Route Report button will be displayed on the document actions bar if the document is using the default DocumentAuthorizerBase.getDocumentActionFlags to set the canPerformRouteReport property of the returned DocumentActionFlags instance.','Document','DEFAULT_CAN_PERFORM_ROUTE_REPORT_IND','CONFG','N',1)
/
INSERT INTO KRNS_PARM_T (APPL_NMSPC_CD,CONS_CD,NMSPC_CD,OBJ_ID,PARM_DESC_TXT,PARM_DTL_TYP_CD,PARM_NM,PARM_TYP_CD,TXT,VER_NBR)
  VALUES ('KUALI','A','KR-NS','53680C68F5A0AD9BE0404F8189D80A6C','Maximum attachment upload size for the application. Used by KualiDocumentFormBase. Must be an integer, optionally followed by "K", "M", or "G".','Document','MAX_FILE_SIZE_ATTACHMENT','CONFG','5M',1)
/
INSERT INTO KRNS_PARM_T (APPL_NMSPC_CD,CONS_CD,NMSPC_CD,OBJ_ID,PARM_DESC_TXT,PARM_DTL_TYP_CD,PARM_NM,PARM_TYP_CD,TXT,VER_NBR)
  VALUES ('KUALI','A','KR-NS','53680C68F5A1AD9BE0404F8189D80A6C','Some documents provide the functionality to send notes to another user using a workflow FYI or acknowledge functionality. This parameter specifies the default action that will be used when sending notes. This parameter should be one of the following 2 values: "K" for acknowledge or "F" for fyi. Depending on the notes and workflow service implementation, other values may be possible.','Document','SEND_NOTE_WORKFLOW_NOTIFICATION_ACTIONS','CONFG','K',1)
/
INSERT INTO KRNS_PARM_T (APPL_NMSPC_CD,CONS_CD,NMSPC_CD,OBJ_ID,PARM_DESC_TXT,PARM_DTL_TYP_CD,PARM_NM,PARM_TYP_CD,TXT,VER_NBR)
  VALUES ('KUALI','A','KR-NS','53680C68F5A4AD9BE0404F8189D80A6C','The number of minutes before a session expires that user should be warned when a document uses pessimistic locking.','Document','SESSION_TIMEOUT_WARNING_MESSAGE_TIME','CONFG','5',1)
/
INSERT INTO KRNS_PARM_T (APPL_NMSPC_CD,CONS_CD,NMSPC_CD,OBJ_ID,PARM_DESC_TXT,PARM_DTL_TYP_CD,PARM_NM,PARM_TYP_CD,TXT,VER_NBR)
  VALUES ('KUALI','A','KR-NS','53680C68F5A3AD9BE0404F8189D80A6C','Lookup results may continue to be persisted in the DB long after they are needed. This parameter represents the maximum amount of time, in seconds, that the results will be allowed to persist in the DB before they are deleted from the DB.','Lookup','MULTIPLE_VALUE_RESULTS_EXPIRATION_SECONDS','CONFG','86400',1)
/
INSERT INTO KRNS_PARM_T (APPL_NMSPC_CD,CONS_CD,NMSPC_CD,OBJ_ID,PARM_DESC_TXT,PARM_DTL_TYP_CD,PARM_NM,PARM_TYP_CD,TXT,VER_NBR)
  VALUES ('KUALI','A','KR-NS','53680C68F5A6AD9BE0404F8189D80A6C','Maximum number of rows that will be displayed on a look-up results screen.','Lookup','MULTIPLE_VALUE_RESULTS_PER_PAGE','CONFG','100',1)
/
INSERT INTO KRNS_PARM_T (APPL_NMSPC_CD,CONS_CD,NMSPC_CD,OBJ_ID,PARM_DESC_TXT,PARM_DTL_TYP_CD,PARM_NM,PARM_TYP_CD,TXT,VER_NBR)
  VALUES ('KUALI','A','KR-NS','53680C68F5A7AD9BE0404F8189D80A6C','If a maxLength attribute has not been set on a lookup result field in the data dictionary, then the result column''s max length will be the value of this parameter. Set this parameter to 0 for an unlimited default length or a positive value (i.e. greater than 0) for a finite max length.','Lookup','RESULTS_DEFAULT_MAX_COLUMN_LENGTH','CONFG','70',1)
/
INSERT INTO KRNS_PARM_T (APPL_NMSPC_CD,CONS_CD,NMSPC_CD,OBJ_ID,PARM_DESC_TXT,PARM_DTL_TYP_CD,PARM_NM,PARM_TYP_CD,TXT,VER_NBR)
  VALUES ('KUALI','A','KR-NS','53680C68F5A8AD9BE0404F8189D80A6C','Maximum number of results returned in a look-up query.','Lookup','RESULTS_LIMIT','CONFG','200',1)
/
INSERT INTO KRNS_PARM_T (APPL_NMSPC_CD,CONS_CD,NMSPC_CD,OBJ_ID,PARM_DESC_TXT,PARM_DTL_TYP_CD,PARM_NM,PARM_TYP_CD,TXT,VER_NBR)
  VALUES ('KUALI','A','KR-NS','5A689075D35A7AEBE0404F8189D80321','Pending attachments are attachments that do not yet have a permanent link with the associated Business Object (BO). These pending attachments are stored in the attachments.pending.directory (defined in the configuration service). If the BO is never persisted, then this attachment will become orphaned (i.e. not associated with any BO), but will remain in this directory. The PurgePendingAttachmentsStep batch step deletes these pending attachment files that are older than the value of this parameter. The unit of this value is seconds. Do not set this value too short, as this will cause problems attaching files to BOs.','PurgePendingAttachmentsStep','MAX_AGE','CONFG','86400',1)
/
INSERT INTO KRNS_PARM_T (APPL_NMSPC_CD,CONS_CD,NMSPC_CD,OBJ_ID,PARM_DESC_TXT,PARM_DTL_TYP_CD,PARM_NM,PARM_TYP_CD,TXT,VER_NBR)
  VALUES ('KUALI','A','KR-NS','5A689075D3597AEBE0404F8189D80321','Determines the age of the session document records that the the step will operate on, e.g. if this param is set to 4, the rows with a last update timestamp older that 4 days prior to when the job is running will be deleted.','PurgeSessionDocumentsStep','NUMBER_OF_DAYS_SINCE_LAST_UPDATE','CONFG','1',1)
/
INSERT INTO KRNS_PARM_T (APPL_NMSPC_CD,CONS_CD,NMSPC_CD,OBJ_ID,PARM_DESC_TXT,PARM_DTL_TYP_CD,PARM_NM,PARM_TYP_CD,TXT,VER_NBR)
  VALUES ('KUALI','A','KR-NS','5A689075D35C7AEBE0404F8189D80321','Controls when the daily batch schedule should terminate. The scheduler service implementation compares the start time of the schedule job from quartz with this time on day after the schedule job started running.','ScheduleStep','CUTOFF_TIME','CONFG','02:00:00:AM',1)
/
INSERT INTO KRNS_PARM_T (APPL_NMSPC_CD,CONS_CD,NMSPC_CD,OBJ_ID,PARM_DESC_TXT,PARM_DTL_TYP_CD,PARM_NM,PARM_TYP_CD,TXT,VER_NBR)
  VALUES ('KUALI','A','KR-NS','5A689075D35D7AEBE0404F8189D80321','Controls whether when the system is comparing the schedule start day & time with the scheduleStep_CUTOFF_TIME parameter, it considers the specified time to apply to the day after the schedule starts.','ScheduleStep','CUTOFF_TIME_NEXT_DAY_IND','CONFG','Y',1)
/
INSERT INTO KRNS_PARM_T (APPL_NMSPC_CD,CONS_CD,NMSPC_CD,OBJ_ID,PARM_DESC_TXT,PARM_DTL_TYP_CD,PARM_NM,PARM_TYP_CD,TXT,VER_NBR)
  VALUES ('KUALI','A','KR-NS','5A689075D35B7AEBE0404F8189D80321','Time in milliseconds that the scheduleStep should wait between iterations.','ScheduleStep','STATUS_CHECK_INTERVAL','CONFG','30000',1)
/
INSERT INTO KRNS_PARM_T (APPL_NMSPC_CD,CONS_CD,NMSPC_CD,OBJ_ID,PARM_DESC_TXT,PARM_DTL_TYP_CD,PARM_NM,PARM_TYP_CD,TXT,VER_NBR)
  VALUES ('KUALI','A','KR-WKFLW','290E45BA032F4F4FB423CE5F78AC52E1','Flag to specify if clicking on a Document ID from the Action List will load the Document in a new window.','ActionList','ACTION_LIST_DOCUMENT_POPUP_IND','CONFG','Y',1)
/
INSERT INTO KRNS_PARM_T (APPL_NMSPC_CD,CONS_CD,NMSPC_CD,OBJ_ID,PARM_DESC_TXT,PARM_DTL_TYP_CD,PARM_NM,PARM_TYP_CD,TXT,VER_NBR)
  VALUES ('KUALI','A','KR-WKFLW','967B0311A5E94F7191B2C544FA7DE095','Flag to specify if clicking on a Route Log from the Action List will load the Route Log in a new window.','ActionList','ACTION_LIST_ROUTE_LOG_POPUP_IND','CONFG','N',1)
/
INSERT INTO KRNS_PARM_T (APPL_NMSPC_CD,CONS_CD,NMSPC_CD,OBJ_ID,PARM_DESC_TXT,PARM_DTL_TYP_CD,PARM_NM,PARM_TYP_CD,VER_NBR)
  VALUES ('KUALI','A','KR-WKFLW','340789CDF30F4252A1A2A42AD39B90B2','Default email address used for testing.','ActionList','EMAIL_NOTIFICATION_TEST_ADDRESS','CONFG',1)
/
INSERT INTO KRNS_PARM_T (APPL_NMSPC_CD,CONS_CD,NMSPC_CD,OBJ_ID,PARM_DESC_TXT,PARM_DTL_TYP_CD,PARM_NM,PARM_TYP_CD,VER_NBR)
  VALUES ('KUALI','A','KR-WKFLW','2CE075BC0C59435CA6DEFF724492DE3F','Throttles the number of results returned on all users Action Lists, regardless of their user preferences.  This is intended to be used in a situation where excessively large Action Lists are causing performance issues.','ActionList','PAGE_SIZE_THROTTLE','CONFG',1)
/
INSERT INTO KRNS_PARM_T (APPL_NMSPC_CD,CONS_CD,NMSPC_CD,OBJ_ID,PARM_DESC_TXT,PARM_DTL_TYP_CD,PARM_NM,PARM_TYP_CD,TXT,VER_NBR)
  VALUES ('KUALI','A','KR-WKFLW','A87659E198214A8B90BE5BEF41630411','Flag to determine whether or not to send email notification.','ActionList','SEND_EMAIL_NOTIFICATION_IND','CONFG','N',1)
/
INSERT INTO KRNS_PARM_T (APPL_NMSPC_CD,CONS_CD,NMSPC_CD,OBJ_ID,PARM_DESC_TXT,PARM_DTL_TYP_CD,PARM_NM,PARM_TYP_CD,TXT,VER_NBR)
  VALUES ('KUALI','A','KR-WKFLW','5C731F2968A3689AE0404F8189D86653','Flag for enabling/disabling document type permission checks to use KIM Permissions as priority over Document Type policies.','All','KIM_PRIORITY_ON_DOC_TYP_PERMS_IND','CONFG','N',1)
/
INSERT INTO KRNS_PARM_T (APPL_NMSPC_CD,CONS_CD,NMSPC_CD,OBJ_ID,PARM_DESC_TXT,PARM_DTL_TYP_CD,PARM_NM,PARM_TYP_CD,VER_NBR)
  VALUES ('KUALI','A','KR-WKFLW','4656B6E7E9844E2C9E2255014AFC86B5','The maximum number of nodes the workflow engine will process before it determines the process is a runaway process.  This is prevent infinite "loops" in the workflow engine.','All','MAXIMUM_NODES_BEFORE_RUNAWAY','CONFG',1)
/
INSERT INTO KRNS_PARM_T (APPL_NMSPC_CD,CONS_CD,NMSPC_CD,OBJ_ID,PARM_DESC_TXT,PARM_DTL_TYP_CD,PARM_NM,PARM_TYP_CD,TXT,VER_NBR)
  VALUES ('KUALI','A','KR-WKFLW','8A37388A2D7A46EF9E6BF3FA8D08A03A','Flag to specify whether or not a file upload box is displayed for KEW notes which allows for uploading of an attachment with the note.','All','SHOW_ATTACHMENTS_IND','CONFG','Y',1)
/
INSERT INTO KRNS_PARM_T (APPL_NMSPC_CD,CONS_CD,NMSPC_CD,OBJ_ID,PARM_DESC_TXT,PARM_DTL_TYP_CD,PARM_NM,PARM_TYP_CD,TXT,VER_NBR)
  VALUES ('KUALI','A','KR-WKFLW','9BD6785416434C4D9E5F05AF077DB9B7','Flag to show the backdoor login.','Backdoor','SHOW_BACK_DOOR_LOGIN_IND','CONFG','Y',1)
/
INSERT INTO KRNS_PARM_T (APPL_NMSPC_CD,CONS_CD,NMSPC_CD,OBJ_ID,PARM_DESC_TXT,PARM_DTL_TYP_CD,PARM_NM,PARM_TYP_CD,TXT,VER_NBR)
  VALUES ('KUALI','A','KR-WKFLW','E78100F6F14C4932B54F7719FA5C27E9','Flag to specify if clicking on a Document ID from Document Search will load the Document in a new window.','DocSearchCriteriaDTO','DOCUMENT_SEARCH_POPUP_IND','CONFG','Y',1)
/
INSERT INTO KRNS_PARM_T (APPL_NMSPC_CD,CONS_CD,NMSPC_CD,OBJ_ID,PARM_DESC_TXT,PARM_DTL_TYP_CD,PARM_NM,PARM_TYP_CD,TXT,VER_NBR)
  VALUES ('KUALI','A','KR-WKFLW','632680DDE9A7478CBD379FAF90C7AE72','Flag to specify if clicking on a Route Log from Document Search will load the Route Log in a new window.','DocSearchCriteriaDTO','DOCUMENT_SEARCH_ROUTE_LOG_POPUP_IND','CONFG','N',1)
/
INSERT INTO KRNS_PARM_T (APPL_NMSPC_CD,CONS_CD,NMSPC_CD,OBJ_ID,PARM_DESC_TXT,PARM_DTL_TYP_CD,PARM_NM,PARM_TYP_CD,VER_NBR)
  VALUES ('KUALI','A','KR-WKFLW','D43459D143FC46C6BF83C71AC2383B76','Limit of fetch more iterations for document searches.','DocSearchCriteriaDTO','FETCH_MORE_ITERATION_LIMIT','CONFG',1)
/
INSERT INTO KRNS_PARM_T (APPL_NMSPC_CD,CONS_CD,NMSPC_CD,OBJ_ID,PARM_DESC_TXT,PARM_DTL_TYP_CD,PARM_NM,PARM_TYP_CD,VER_NBR)
  VALUES ('KUALI','A','KR-WKFLW','E324D85082184EB6967537B3EE1F655B','Maximum number of documents to return from a search.','DocSearchCriteriaDTO','RESULT_CAP','CONFG',1)
/
INSERT INTO KRNS_PARM_T (APPL_NMSPC_CD,CONS_CD,NMSPC_CD,OBJ_ID,PARM_DESC_TXT,PARM_DTL_TYP_CD,PARM_NM,PARM_TYP_CD,TXT,VER_NBR)
  VALUES ('KUALI','A','KR-WKFLW','68B2EA08E13A4FF3B9EDBD5415818C93','Defines whether the debug transform is enabled for eDcoLite.','EDocLite','DEBUG_TRANSFORM_IND','CONFG','N',1)
/
INSERT INTO KRNS_PARM_T (APPL_NMSPC_CD,CONS_CD,NMSPC_CD,OBJ_ID,PARM_DESC_TXT,PARM_DTL_TYP_CD,PARM_NM,PARM_TYP_CD,TXT,VER_NBR)
  VALUES ('KUALI','A','KR-WKFLW','FCAEE745A7E64AF5982937C47EBC2698','Defines whether XSLTC is used for eDocLite.','EDocLite','USE_XSLTC_IND','CONFG','N',1)
/
INSERT INTO KRNS_PARM_T (APPL_NMSPC_CD,CONS_CD,NMSPC_CD,OBJ_ID,PARM_DESC_TXT,PARM_DTL_TYP_CD,PARM_NM,PARM_TYP_CD,VER_NBR)
  VALUES ('KUALI','A','KR-WKFLW','BEBDBCFA74A5458EADE2CF075FFF206E','A flag to specify whether the WorkflowInfo.isLastApproverAtNode(...) API method attempts to active requests first, prior to execution.','Feature','IS_LAST_APPROVER_ACTIVATE_FIRST_IND','CONFG',1)
/
INSERT INTO KRNS_PARM_T (APPL_NMSPC_CD,CONS_CD,NMSPC_CD,OBJ_ID,PARM_DESC_TXT,PARM_DTL_TYP_CD,PARM_NM,PARM_TYP_CD,TXT,VER_NBR)
  VALUES ('KUALI','A','KR-WKFLW','700AB6A6E23740D0B3E00E02A8FB6347','Default from email address for notifications.','Mailer','FROM_ADDRESS','CONFG','quickstart@localhost',1)
/
INSERT INTO KRNS_PARM_T (APPL_NMSPC_CD,CONS_CD,NMSPC_CD,OBJ_ID,PARM_DESC_TXT,PARM_DTL_TYP_CD,PARM_NM,PARM_TYP_CD,VER_NBR)
  VALUES ('KUALI','A','KR-WKFLW','08280F2575904F3586CF48BB97907506','Defines whether or not to send a notification to users excluded from a workgroup.','Notification','NOTIFY_EXCLUDED_USERS_IND','CONFG',1)
/
INSERT INTO KRNS_PARM_T (APPL_NMSPC_CD,CONS_CD,NMSPC_CD,OBJ_ID,PARM_DESC_TXT,PARM_DTL_TYP_CD,PARM_NM,PARM_TYP_CD,VER_NBR)
  VALUES ('KUALI','A','KR-WKFLW','5292CFD9A0EA48BEB22A2EB3B3BD3CDA','Comma seperated list of Document Types to exclude from the Rule Quicklinks.','QuickLinks','RESTRICT_DOCUMENT_TYPES','CONFG',1)
/
INSERT INTO KRNS_PARM_T (APPL_NMSPC_CD,CONS_CD,NMSPC_CD,OBJ_ID,PARM_DESC_TXT,PARM_DTL_TYP_CD,PARM_NM,PARM_TYP_CD,TXT,VER_NBR)
  VALUES ('KUALI','A','KR-WKFLW','E05A692D62E54B87901D872DC37208A1','Indicator to determine if rule caching is enabled.','Rule','CACHING_IND','CONFG','Y',1)
/
INSERT INTO KRNS_PARM_T (APPL_NMSPC_CD,CONS_CD,NMSPC_CD,OBJ_ID,PARM_DESC_TXT,PARM_DTL_TYP_CD,PARM_NM,PARM_TYP_CD,VER_NBR)
  VALUES ('KUALI','A','KR-WKFLW','BDE964269F2743338C00A4326B676195','Defines custom Document Type processes to use for certain types of routing rules.','Rule','CUSTOM_DOCUMENT_TYPES','CONFG',1)
/
INSERT INTO KRNS_PARM_T (APPL_NMSPC_CD,CONS_CD,NMSPC_CD,OBJ_ID,PARM_DESC_TXT,PARM_DTL_TYP_CD,PARM_NM,PARM_TYP_CD,TXT,VER_NBR)
  VALUES ('KUALI','A','KR-WKFLW','21EA54B9A9E846709E76C176DE0AF47C','Specifies that maximum number of delegation rules that will be displayed on a Rule inquiry before the screen shows a count of delegate rules and provides a link for the user to show them.','Rule','DELEGATE_LIMIT','CONFG','20',1)
/
INSERT INTO KRNS_PARM_T (APPL_NMSPC_CD,CONS_CD,NMSPC_CD,OBJ_ID,PARM_DESC_TXT,PARM_DTL_TYP_CD,PARM_NM,PARM_TYP_CD,TXT,VER_NBR)
  VALUES ('KUALI','A','KR-WKFLW','96868C896B4B4A8BA87AD20E42948431','Flag to determine whether or not a change to a routing rule should be applied retroactively to existing documents.','Rule','GENERATE_ACTION_REQUESTS_IND','CONFG','Y',1)
/
INSERT INTO KRNS_PARM_T (APPL_NMSPC_CD,CONS_CD,NMSPC_CD,OBJ_ID,PARM_DESC_TXT,PARM_DTL_TYP_CD,PARM_NM,PARM_TYP_CD,TXT,VER_NBR)
  VALUES ('KUALI','A','KR-WKFLW','8AE796DB88484468830A8879630CCF5D','Amount of time after a rule change is made before the rule cache update message is sent.','Rule','RULE_CACHE_REQUEUE_DELAY','CONFG','5000',1)
/
--
-- KRNS_PARM_TYP_T
--


INSERT INTO KRNS_PARM_TYP_T (ACTV_IND,NM,OBJ_ID,PARM_TYP_CD,VER_NBR)
  VALUES ('Y','Authorization','53680C68F593AD9BE0404F8189D80A6C','AUTH',1)
/
INSERT INTO KRNS_PARM_TYP_T (ACTV_IND,NM,OBJ_ID,PARM_TYP_CD,VER_NBR)
  VALUES ('Y','Config','53680C68F591AD9BE0404F8189D80A6C','CONFG',1)
/
INSERT INTO KRNS_PARM_TYP_T (ACTV_IND,NM,OBJ_ID,PARM_TYP_CD,VER_NBR)
  VALUES ('Y','Help','53680C68F594AD9BE0404F8189D80A6C','HELP',1)
/
INSERT INTO KRNS_PARM_TYP_T (ACTV_IND,NM,OBJ_ID,PARM_TYP_CD,VER_NBR)
  VALUES ('Y','Document Validation','53680C68F592AD9BE0404F8189D80A6C','VALID',1)
/
--
-- KRSB_MSG_PYLD_T
--


INSERT INTO KRSB_MSG_PYLD_T (MSG_PYLD,MSG_QUE_ID)
  VALUES (EMPTY_CLOB(),361)
/
-- Length: 6108
-- Chunks: 2
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 361    FOR UPDATE;        
    buffer := 'rO0ABXNyAC1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLkFzeW5jaHJvbm91c0NhbGzxnQ5Y0t0lZQIAB1oAFWlnbm9yZVN0b3JlQW5kRm9yd2FyZFsACWFyZ3VtZW50c3QAE1tMamF2YS9sYW5nL09iamVjdDtMAAhjYWxsYmFja3QAM0xvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL0FzeW5jaHJvbm91c0NhbGxiYWNrO0wAB2NvbnRleHR0ABZMamF2YS9pby9TZXJpYWxpemFibGU7TAAKbWV0aG9kTmFtZXQAEkxqYXZhL2xhbmcvU3RyaW5nO1sACnBhcmFtVHlwZXN0ABJbTGphdmEvbGFuZy9DbGFzcztMAAtzZXJ2aWNlSW5mb3QAKkxvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL1NlcnZpY2VJbmZvO3hwAHVyABNbTGphdmEubGFuZy5PYmplY3Q7kM5YnxBzKWwCAAB4cAAAAAFzcgBDY29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLnBsdWdpbnMuY2x1c3RlcnN1cHBvcnQuQ2x1c3Rlck5vdGlmaWNhdGlvbhY+6VckvG9pAgACSQAEdHlwZUwABGRhdGFxAH4AA3hwAAAAAnQAM0RvY3VtZW50VHlwZVJ1bGVDYWNoZTpDYW1wdXNUeXBlTWFpbnRlbmFuY2VEb2N1bWVudHBwdAAGaW52b2tldXIAEltMamF2YS5sYW5nLkNsYXNzO6sW167LzVqZAgAAeHAAAAABdnIAFGphdmEuaW8uU2VyaWFsaXphYmxlEJthDdebZO0CAAB4cHNyAChvcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLlNlcnZpY2VJbmZvxRcjtikEt7oCAAtMAAVhbGl2ZXQAE0xqYXZhL2xhbmcvQm9vbGVhbjtMABRlbmRwb2ludEFsdGVybmF0ZVVybHEAfgAETAALZW5kcG9pbnRVcmxxAH4ABEwACmxvY2tWZXJOYnJ0ABNMamF2YS9sYW5nL0ludGVnZXI7TAAObWVzc2FnZUVudHJ5SWR0ABBMamF2YS9sYW5nL0xvbmc7TAAFcW5hbWV0ABtMamF2YXgveG1sL25hbWVzcGFjZS9RTmFtZTtMABpzZXJpYWxpemVkU2VydmljZU5hbWVzcGFjZXEAfgAETAAIc2VydmVySXBxAH4ABEwAEXNlcnZpY2VEZWZpbml0aW9udAAwTG9yZy9rdWFsaS9yaWNlL2tzYi9tZXNzYWdpbmcvU2VydmljZURlZmluaXRpb247TAALc2VydmljZU5hbWVxAH4ABEwAEHNlcnZpY2VOYW1lc3BhY2VxAH4ABHhwc3IAEWphdmEubGFuZy5Cb29sZWFuzSBygNWc+u4CAAFaAAV2YWx1ZXhwAXB0AEBodHRwOi8vbG9jYWxob3N0OjgwODAva3ItZGV2L3JlbW90aW5nL09TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNlc3IAEWphdmEubGFuZy5JbnRlZ2VyEuKgpPeBhzgCAAFJAAV2YWx1ZXhyABBqYXZhLmxhbmcuTnVtYmVyhqyVHQuU4IsCAAB4cAAAAAFzcgAOamF2YS5sYW5nLkxvbmc7i+SQzI8j3wIAAUoABXZhbHVleHEAfgAdAAAAAAAAD31zcgAZamF2YXgueG1sLm5hbWVzcGFjZS5RTmFtZYFtqC38O91sAgADTAAJbG9jYWxQYXJ0cQB+AARMAAxuYW1lc3BhY2VVUklxAH4ABEwABnByZWZpeHEAfgAEeHB0ABpPU0NhY2hlTm90aWZpY2F0aW9uU2VydmljZXQAAHEAfgAkdAd0ck8wQUJYTnlBREp2Y21jdWEzVmhiR2t1Y21salpTNXJjMkl1YldWemMyRm5hVzVuTGtwaGRtRlRaWEoyYVdObFJHVm1hVzVwZEdsdmJyYm5EVjlCTkd6eEFnQUJUQUFSYzJWeWRtbGpaVWx1ZEdWeVptRmpaWE4wQUJCTWFtRjJZUzkxZEdsc0wweHBjM1E3ZUhJQUxtOXlaeTVyZFdGc2FTNXlhV05sTG10ellpNXRaWE56WVdkcGJtY3VVMlZ5ZG1salpVUmxabWx1YVhScGIyNEFtd0pQV04wcGZnSUFEVXdBQzJKMWMxTmxZM1Z5YVhSNWRBQVRUR3BoZG1FdmJHRnVaeTlDYjI5c1pXRnVPMHdBRDJOeVpXUmxiblJwWVd4elZIbHdaWFFBVEV4dmNtY3ZhM1ZoYkdrdmNtbGpaUzlqYjNKbEwzTmxZM1Z5YVhSNUwyTnlaV1JsYm5ScFlXeHpMME55WldSbGJuUnBZV3h6VTI5MWNtTmxKRU55WldSbGJuUnBZV3h6Vkhsd1pUdE1BQkJzYjJOaGJGTmxjblpwWTJWT1lXMWxkQUFTVEdwaGRtRXZiR0Z1Wnk5VGRISnBibWM3VEFBWGJXVnpjMkZuWlVWNFkyVndkR2x2YmtoaGJtUnNaWEp4QUg0QUJVd0FERzFwYkd4cGMxUnZUR2wyWlhRQUVFeHFZWFpoTDJ4aGJtY3ZURzl1Wnp0TUFBaHdjbWx2Y21sMGVYUUFFMHhxWVhaaEwyeGhibWN2U1c1MFpXZGxjanRNQUFWeGRXVjFaWEVBZmdBRFRBQU5jbVYwY25sQmRIUmxiWEIwYzNFQWZnQUhUQUFIYzJWeWRtbGpaWFFBRWt4cVlYWmhMMnhoYm1jdlQySnFaV04wTzB3QUQzTmxjblpwWTJWRmJtUlFiMmx1ZEhRQURreHFZWFpoTDI1bGRDOVZVa3c3VEFBVGMyVnlkbWxqWlU1aGJXVlRjR0ZqWlZWU1NYRUFmZ0FGVEFBUWMyVnlkbWxqWlU1aGJXVnpjR0ZqWlhFQWZnQUZUQUFMYzJWeWRtbGpaVkJoZEdoeEFINEFCWGh3YzNJQUVXcGhkbUV1YkdGdVp5NUNiMjlzWldGdXpTQnlnTldjK3U0Q0FBRmFBQVYyWVd4MVpYaHdBWEIwQUJwUFUwTmhZMmhsVG05MGFXWnBZMkYwYVc5dVUyVnlkbWxqWlhRQVRXOXlaeTVyZFdGc2FTNXlhV05sTG10ellpNXRaWE56WVdkcGJtY3VaWGhqWlhCMGFXOXVhR0Z1Wkd4cGJtY3VSR1ZtWVhWc2RFMWxjM05oWjJWRmVHTmxjSFJwYjI1SVlXNWtiR1Z5YzNJQURtcGhkbUV1YkdGdVp5NU1iMjVuTzR2a2tNeVBJOThDQUFGS0FBVjJZV3gxWlhoeUFCQnFZWFpoTG14aGJtY3VUblZ0WW1WeWhxeVZIUXVVNElzQ0FBQjRjUC8vLy8vLy8vLy9jM0lBRVdwaGRtRXViR0Z1Wnk1SmJuUmxaMlZ5RXVLZ3BQZUJoemdDQUFGSkFBVjJZV3gxWlhoeEFINEFFQUFBQUFOemNRQitBQXNBY1FCK0FCTndjM0lBREdwaGRtRXVibVYwTGxWU1RKWWxOellhL09SeUF3QUhTUUFJYUdGemFFTnZaR1ZKQUFSd2IzSjBUQUFKWVhWMGFHOXlhWFI1Y1FCK0FBVk1BQVJtYVd4bGNRQitBQVZNQUFSb2IzTjBjUUIrQUFWTUFBaHdjbTkwYjJOdmJIRUFmZ0FGVEFBRGNtVm1jUUIrQUFWNGNQLy8vLzhBQUIrUWRBQU9iRzlqWVd4b2IzTjBPamd3T0RCMEFDc3ZhM0l0WkdWMkwzSmxiVzkwYVc1bkwwOVRRMkZqYUdWT2IzUnBabWxqWVhScGIyNVRaWEoyYVdObGRBQUpiRzlqWVd4b2IzTjBkQUFFYUhSMGNIQjRkQUFBZEFBR1ZGSkJWa1ZNZEFBQkwzTnlBQk5xWVhaaExuVjBhV3d1UVhKeVlYbE1hWE4wZUlIU0habkhZWjBEQUFGSkFBUnphWHBsZUhBQUFBQUZkd1FBQUFBS2RBQXpi';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 361    FOR UPDATE;        
    buffer := 'M0puTG10MVlXeHBMbkpwWTJVdWEzTmlMbTFsYzNOaFoybHVaeTV6WlhKMmFXTmxMa3RUUWtwaGRtRlRaWEoyYVdObGRBQThZMjl0TG05d1pXNXplVzF3YUc5dWVTNXZjMk5oWTJobExtSmhjMlV1WlhabGJuUnpMa05oWTJobFJXNTBjbmxGZG1WdWRFeHBjM1JsYm1WeWRBQTNZMjl0TG05d1pXNXplVzF3YUc5dWVTNXZjMk5oWTJobExtSmhjMlV1WlhabGJuUnpMa05oWTJobFJYWmxiblJNYVhOMFpXNWxjblFBRjJwaGRtRXVkWFJwYkM1RmRtVnVkRXhwYzNSbGJtVnlkQUFzWTI5dExtOXdaVzV6ZVcxd2FHOXVlUzV2YzJOaFkyaGxMbUpoYzJVdVRHbG1aV041WTJ4bFFYZGhjbVY0dAANMTI5LjE4Ni43OS40NXNyADJvcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLkphdmFTZXJ2aWNlRGVmaW5pdGlvbrbnDV9BNGzxAgABTAARc2VydmljZUludGVyZmFjZXN0ABBMamF2YS91dGlsL0xpc3Q7eHIALm9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuU2VydmljZURlZmluaXRpb24AmwJPWN0pfgIADUwAC2J1c1NlY3VyaXR5cQB+ABNMAA9jcmVkZW50aWFsc1R5cGV0AExMb3JnL2t1YWxpL3JpY2UvY29yZS9zZWN1cml0eS9jcmVkZW50aWFscy9DcmVkZW50aWFsc1NvdXJjZSRDcmVkZW50aWFsc1R5cGU7TAAQbG9jYWxTZXJ2aWNlTmFtZXEAfgAETAAXbWVzc2FnZUV4Y2VwdGlvbkhhbmRsZXJxAH4ABEwADG1pbGxpc1RvTGl2ZXEAfgAVTAAIcHJpb3JpdHlxAH4AFEwABXF1ZXVlcQB+ABNMAA1yZXRyeUF0dGVtcHRzcQB+ABRMAAdzZXJ2aWNldAASTGphdmEvbGFuZy9PYmplY3Q7TAAPc2VydmljZUVuZFBvaW50dAAOTGphdmEvbmV0L1VSTDtMABNzZXJ2aWNlTmFtZVNwYWNlVVJJcQB+AARMABBzZXJ2aWNlTmFtZXNwYWNlcQB+AARMAAtzZXJ2aWNlUGF0aHEAfgAEeHBzcQB+ABkBcHQAGk9TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNldABNb3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5leGNlcHRpb25oYW5kbGluZy5EZWZhdWx0TWVzc2FnZUV4Y2VwdGlvbkhhbmRsZXJzcQB+AB///////////3NxAH4AHAAAAANzcQB+ABkAcQB+ADJwc3IADGphdmEubmV0LlVSTJYlNzYa/ORyAwAHSQAIaGFzaENvZGVJAARwb3J0TAAJYXV0aG9yaXR5cQB+AARMAARmaWxlcQB+AARMAARob3N0cQB+AARMAAhwcm90b2NvbHEAfgAETAADcmVmcQB+AAR4cGkboqAAAB+QdAAObG9jYWxob3N0OjgwODB0ACsva3ItZGV2L3JlbW90aW5nL09TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNldAAJbG9jYWxob3N0dAAEaHR0cHB4dAAAdAAGVFJBVkVMdAABL3NyABNqYXZhLnV0aWwuQXJyYXlMaXN0eIHSHZnHYZ0DAAFJAARzaXpleHAAAAAFdwQAAAAKdAAzb3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5zZXJ2aWNlLktTQkphdmFTZXJ2aWNldAA8Y29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLmJhc2UuZXZlbnRzLkNhY2hlRW50cnlFdmVudExpc3RlbmVydAA3Y29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLmJhc2UuZXZlbnRzLkNhY2hlRXZlbnRMaXN0ZW5lcnQAF2phdmEudXRpbC5FdmVudExpc3RlbmVydAAsY29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLmJhc2UuTGlmZWN5Y2xlQXdhcmV4cQB+ACN0AAZUUkFWRUw=';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
INSERT INTO KRSB_MSG_PYLD_T (MSG_PYLD,MSG_QUE_ID)
  VALUES (EMPTY_CLOB(),362)
/
-- Length: 6104
-- Chunks: 2
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 362    FOR UPDATE;        
    buffer := 'rO0ABXNyAC1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLkFzeW5jaHJvbm91c0NhbGzxnQ5Y0t0lZQIAB1oAFWlnbm9yZVN0b3JlQW5kRm9yd2FyZFsACWFyZ3VtZW50c3QAE1tMamF2YS9sYW5nL09iamVjdDtMAAhjYWxsYmFja3QAM0xvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL0FzeW5jaHJvbm91c0NhbGxiYWNrO0wAB2NvbnRleHR0ABZMamF2YS9pby9TZXJpYWxpemFibGU7TAAKbWV0aG9kTmFtZXQAEkxqYXZhL2xhbmcvU3RyaW5nO1sACnBhcmFtVHlwZXN0ABJbTGphdmEvbGFuZy9DbGFzcztMAAtzZXJ2aWNlSW5mb3QAKkxvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL1NlcnZpY2VJbmZvO3hwAHVyABNbTGphdmEubGFuZy5PYmplY3Q7kM5YnxBzKWwCAAB4cAAAAAFzcgBDY29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLnBsdWdpbnMuY2x1c3RlcnN1cHBvcnQuQ2x1c3Rlck5vdGlmaWNhdGlvbhY+6VckvG9pAgACSQAEdHlwZUwABGRhdGFxAH4AA3hwAAAAAnQAMERvY3VtZW50VHlwZVJ1bGVDYWNoZTpDb3VudHJ5TWFpbnRlbmFuY2VEb2N1bWVudHBwdAAGaW52b2tldXIAEltMamF2YS5sYW5nLkNsYXNzO6sW167LzVqZAgAAeHAAAAABdnIAFGphdmEuaW8uU2VyaWFsaXphYmxlEJthDdebZO0CAAB4cHNyAChvcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLlNlcnZpY2VJbmZvxRcjtikEt7oCAAtMAAVhbGl2ZXQAE0xqYXZhL2xhbmcvQm9vbGVhbjtMABRlbmRwb2ludEFsdGVybmF0ZVVybHEAfgAETAALZW5kcG9pbnRVcmxxAH4ABEwACmxvY2tWZXJOYnJ0ABNMamF2YS9sYW5nL0ludGVnZXI7TAAObWVzc2FnZUVudHJ5SWR0ABBMamF2YS9sYW5nL0xvbmc7TAAFcW5hbWV0ABtMamF2YXgveG1sL25hbWVzcGFjZS9RTmFtZTtMABpzZXJpYWxpemVkU2VydmljZU5hbWVzcGFjZXEAfgAETAAIc2VydmVySXBxAH4ABEwAEXNlcnZpY2VEZWZpbml0aW9udAAwTG9yZy9rdWFsaS9yaWNlL2tzYi9tZXNzYWdpbmcvU2VydmljZURlZmluaXRpb247TAALc2VydmljZU5hbWVxAH4ABEwAEHNlcnZpY2VOYW1lc3BhY2VxAH4ABHhwc3IAEWphdmEubGFuZy5Cb29sZWFuzSBygNWc+u4CAAFaAAV2YWx1ZXhwAXB0AEBodHRwOi8vbG9jYWxob3N0OjgwODAva3ItZGV2L3JlbW90aW5nL09TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNlc3IAEWphdmEubGFuZy5JbnRlZ2VyEuKgpPeBhzgCAAFJAAV2YWx1ZXhyABBqYXZhLmxhbmcuTnVtYmVyhqyVHQuU4IsCAAB4cAAAAAFzcgAOamF2YS5sYW5nLkxvbmc7i+SQzI8j3wIAAUoABXZhbHVleHEAfgAdAAAAAAAAD31zcgAZamF2YXgueG1sLm5hbWVzcGFjZS5RTmFtZYFtqC38O91sAgADTAAJbG9jYWxQYXJ0cQB+AARMAAxuYW1lc3BhY2VVUklxAH4ABEwABnByZWZpeHEAfgAEeHB0ABpPU0NhY2hlTm90aWZpY2F0aW9uU2VydmljZXQAAHEAfgAkdAd0ck8wQUJYTnlBREp2Y21jdWEzVmhiR2t1Y21salpTNXJjMkl1YldWemMyRm5hVzVuTGtwaGRtRlRaWEoyYVdObFJHVm1hVzVwZEdsdmJyYm5EVjlCTkd6eEFnQUJUQUFSYzJWeWRtbGpaVWx1ZEdWeVptRmpaWE4wQUJCTWFtRjJZUzkxZEdsc0wweHBjM1E3ZUhJQUxtOXlaeTVyZFdGc2FTNXlhV05sTG10ellpNXRaWE56WVdkcGJtY3VVMlZ5ZG1salpVUmxabWx1YVhScGIyNEFtd0pQV04wcGZnSUFEVXdBQzJKMWMxTmxZM1Z5YVhSNWRBQVRUR3BoZG1FdmJHRnVaeTlDYjI5c1pXRnVPMHdBRDJOeVpXUmxiblJwWVd4elZIbHdaWFFBVEV4dmNtY3ZhM1ZoYkdrdmNtbGpaUzlqYjNKbEwzTmxZM1Z5YVhSNUwyTnlaV1JsYm5ScFlXeHpMME55WldSbGJuUnBZV3h6VTI5MWNtTmxKRU55WldSbGJuUnBZV3h6Vkhsd1pUdE1BQkJzYjJOaGJGTmxjblpwWTJWT1lXMWxkQUFTVEdwaGRtRXZiR0Z1Wnk5VGRISnBibWM3VEFBWGJXVnpjMkZuWlVWNFkyVndkR2x2YmtoaGJtUnNaWEp4QUg0QUJVd0FERzFwYkd4cGMxUnZUR2wyWlhRQUVFeHFZWFpoTDJ4aGJtY3ZURzl1Wnp0TUFBaHdjbWx2Y21sMGVYUUFFMHhxWVhaaEwyeGhibWN2U1c1MFpXZGxjanRNQUFWeGRXVjFaWEVBZmdBRFRBQU5jbVYwY25sQmRIUmxiWEIwYzNFQWZnQUhUQUFIYzJWeWRtbGpaWFFBRWt4cVlYWmhMMnhoYm1jdlQySnFaV04wTzB3QUQzTmxjblpwWTJWRmJtUlFiMmx1ZEhRQURreHFZWFpoTDI1bGRDOVZVa3c3VEFBVGMyVnlkbWxqWlU1aGJXVlRjR0ZqWlZWU1NYRUFmZ0FGVEFBUWMyVnlkbWxqWlU1aGJXVnpjR0ZqWlhFQWZnQUZUQUFMYzJWeWRtbGpaVkJoZEdoeEFINEFCWGh3YzNJQUVXcGhkbUV1YkdGdVp5NUNiMjlzWldGdXpTQnlnTldjK3U0Q0FBRmFBQVYyWVd4MVpYaHdBWEIwQUJwUFUwTmhZMmhsVG05MGFXWnBZMkYwYVc5dVUyVnlkbWxqWlhRQVRXOXlaeTVyZFdGc2FTNXlhV05sTG10ellpNXRaWE56WVdkcGJtY3VaWGhqWlhCMGFXOXVhR0Z1Wkd4cGJtY3VSR1ZtWVhWc2RFMWxjM05oWjJWRmVHTmxjSFJwYjI1SVlXNWtiR1Z5YzNJQURtcGhkbUV1YkdGdVp5NU1iMjVuTzR2a2tNeVBJOThDQUFGS0FBVjJZV3gxWlhoeUFCQnFZWFpoTG14aGJtY3VUblZ0WW1WeWhxeVZIUXVVNElzQ0FBQjRjUC8vLy8vLy8vLy9jM0lBRVdwaGRtRXViR0Z1Wnk1SmJuUmxaMlZ5RXVLZ3BQZUJoemdDQUFGSkFBVjJZV3gxWlhoeEFINEFFQUFBQUFOemNRQitBQXNBY1FCK0FCTndjM0lBREdwaGRtRXVibVYwTGxWU1RKWWxOellhL09SeUF3QUhTUUFJYUdGemFFTnZaR1ZKQUFSd2IzSjBUQUFKWVhWMGFHOXlhWFI1Y1FCK0FBVk1BQVJtYVd4bGNRQitBQVZNQUFSb2IzTjBjUUIrQUFWTUFBaHdjbTkwYjJOdmJIRUFmZ0FGVEFBRGNtVm1jUUIrQUFWNGNQLy8vLzhBQUIrUWRBQU9iRzlqWVd4b2IzTjBPamd3T0RCMEFDc3ZhM0l0WkdWMkwzSmxiVzkwYVc1bkwwOVRRMkZqYUdWT2IzUnBabWxqWVhScGIyNVRaWEoyYVdObGRBQUpiRzlqWVd4b2IzTjBkQUFFYUhSMGNIQjRkQUFBZEFBR1ZGSkJWa1ZNZEFBQkwzTnlBQk5xWVhaaExuVjBhV3d1UVhKeVlYbE1hWE4wZUlIU0habkhZWjBEQUFGSkFBUnphWHBsZUhBQUFBQUZkd1FBQUFBS2RBQXpiM0pu';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 362    FOR UPDATE;        
    buffer := 'TG10MVlXeHBMbkpwWTJVdWEzTmlMbTFsYzNOaFoybHVaeTV6WlhKMmFXTmxMa3RUUWtwaGRtRlRaWEoyYVdObGRBQThZMjl0TG05d1pXNXplVzF3YUc5dWVTNXZjMk5oWTJobExtSmhjMlV1WlhabGJuUnpMa05oWTJobFJXNTBjbmxGZG1WdWRFeHBjM1JsYm1WeWRBQTNZMjl0TG05d1pXNXplVzF3YUc5dWVTNXZjMk5oWTJobExtSmhjMlV1WlhabGJuUnpMa05oWTJobFJYWmxiblJNYVhOMFpXNWxjblFBRjJwaGRtRXVkWFJwYkM1RmRtVnVkRXhwYzNSbGJtVnlkQUFzWTI5dExtOXdaVzV6ZVcxd2FHOXVlUzV2YzJOaFkyaGxMbUpoYzJVdVRHbG1aV041WTJ4bFFYZGhjbVY0dAANMTI5LjE4Ni43OS40NXNyADJvcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLkphdmFTZXJ2aWNlRGVmaW5pdGlvbrbnDV9BNGzxAgABTAARc2VydmljZUludGVyZmFjZXN0ABBMamF2YS91dGlsL0xpc3Q7eHIALm9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuU2VydmljZURlZmluaXRpb24AmwJPWN0pfgIADUwAC2J1c1NlY3VyaXR5cQB+ABNMAA9jcmVkZW50aWFsc1R5cGV0AExMb3JnL2t1YWxpL3JpY2UvY29yZS9zZWN1cml0eS9jcmVkZW50aWFscy9DcmVkZW50aWFsc1NvdXJjZSRDcmVkZW50aWFsc1R5cGU7TAAQbG9jYWxTZXJ2aWNlTmFtZXEAfgAETAAXbWVzc2FnZUV4Y2VwdGlvbkhhbmRsZXJxAH4ABEwADG1pbGxpc1RvTGl2ZXEAfgAVTAAIcHJpb3JpdHlxAH4AFEwABXF1ZXVlcQB+ABNMAA1yZXRyeUF0dGVtcHRzcQB+ABRMAAdzZXJ2aWNldAASTGphdmEvbGFuZy9PYmplY3Q7TAAPc2VydmljZUVuZFBvaW50dAAOTGphdmEvbmV0L1VSTDtMABNzZXJ2aWNlTmFtZVNwYWNlVVJJcQB+AARMABBzZXJ2aWNlTmFtZXNwYWNlcQB+AARMAAtzZXJ2aWNlUGF0aHEAfgAEeHBzcQB+ABkBcHQAGk9TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNldABNb3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5leGNlcHRpb25oYW5kbGluZy5EZWZhdWx0TWVzc2FnZUV4Y2VwdGlvbkhhbmRsZXJzcQB+AB///////////3NxAH4AHAAAAANzcQB+ABkAcQB+ADJwc3IADGphdmEubmV0LlVSTJYlNzYa/ORyAwAHSQAIaGFzaENvZGVJAARwb3J0TAAJYXV0aG9yaXR5cQB+AARMAARmaWxlcQB+AARMAARob3N0cQB+AARMAAhwcm90b2NvbHEAfgAETAADcmVmcQB+AAR4cGkboqAAAB+QdAAObG9jYWxob3N0OjgwODB0ACsva3ItZGV2L3JlbW90aW5nL09TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNldAAJbG9jYWxob3N0dAAEaHR0cHB4dAAAdAAGVFJBVkVMdAABL3NyABNqYXZhLnV0aWwuQXJyYXlMaXN0eIHSHZnHYZ0DAAFJAARzaXpleHAAAAAFdwQAAAAKdAAzb3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5zZXJ2aWNlLktTQkphdmFTZXJ2aWNldAA8Y29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLmJhc2UuZXZlbnRzLkNhY2hlRW50cnlFdmVudExpc3RlbmVydAA3Y29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLmJhc2UuZXZlbnRzLkNhY2hlRXZlbnRMaXN0ZW5lcnQAF2phdmEudXRpbC5FdmVudExpc3RlbmVydAAsY29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLmJhc2UuTGlmZWN5Y2xlQXdhcmV4cQB+ACN0AAZUUkFWRUw=';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
INSERT INTO KRSB_MSG_PYLD_T (MSG_PYLD,MSG_QUE_ID)
  VALUES (EMPTY_CLOB(),363)
/
-- Length: 6104
-- Chunks: 2
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 363    FOR UPDATE;        
    buffer := 'rO0ABXNyAC1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLkFzeW5jaHJvbm91c0NhbGzxnQ5Y0t0lZQIAB1oAFWlnbm9yZVN0b3JlQW5kRm9yd2FyZFsACWFyZ3VtZW50c3QAE1tMamF2YS9sYW5nL09iamVjdDtMAAhjYWxsYmFja3QAM0xvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL0FzeW5jaHJvbm91c0NhbGxiYWNrO0wAB2NvbnRleHR0ABZMamF2YS9pby9TZXJpYWxpemFibGU7TAAKbWV0aG9kTmFtZXQAEkxqYXZhL2xhbmcvU3RyaW5nO1sACnBhcmFtVHlwZXN0ABJbTGphdmEvbGFuZy9DbGFzcztMAAtzZXJ2aWNlSW5mb3QAKkxvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL1NlcnZpY2VJbmZvO3hwAHVyABNbTGphdmEubGFuZy5PYmplY3Q7kM5YnxBzKWwCAAB4cAAAAAFzcgBDY29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLnBsdWdpbnMuY2x1c3RlcnN1cHBvcnQuQ2x1c3Rlck5vdGlmaWNhdGlvbhY+6VckvG9pAgACSQAEdHlwZUwABGRhdGFxAH4AA3hwAAAAAnQAL0RvY3VtZW50VHlwZVJ1bGVDYWNoZTpDb3VudHlNYWludGVuYW5jZURvY3VtZW50cHB0AAZpbnZva2V1cgASW0xqYXZhLmxhbmcuQ2xhc3M7qxbXrsvNWpkCAAB4cAAAAAF2cgAUamF2YS5pby5TZXJpYWxpemFibGUQm2EN15tk7QIAAHhwc3IAKG9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuU2VydmljZUluZm/FFyO2KQS3ugIAC0wABWFsaXZldAATTGphdmEvbGFuZy9Cb29sZWFuO0wAFGVuZHBvaW50QWx0ZXJuYXRlVXJscQB+AARMAAtlbmRwb2ludFVybHEAfgAETAAKbG9ja1Zlck5icnQAE0xqYXZhL2xhbmcvSW50ZWdlcjtMAA5tZXNzYWdlRW50cnlJZHQAEExqYXZhL2xhbmcvTG9uZztMAAVxbmFtZXQAG0xqYXZheC94bWwvbmFtZXNwYWNlL1FOYW1lO0wAGnNlcmlhbGl6ZWRTZXJ2aWNlTmFtZXNwYWNlcQB+AARMAAhzZXJ2ZXJJcHEAfgAETAARc2VydmljZURlZmluaXRpb250ADBMb3JnL2t1YWxpL3JpY2Uva3NiL21lc3NhZ2luZy9TZXJ2aWNlRGVmaW5pdGlvbjtMAAtzZXJ2aWNlTmFtZXEAfgAETAAQc2VydmljZU5hbWVzcGFjZXEAfgAEeHBzcgARamF2YS5sYW5nLkJvb2xlYW7NIHKA1Zz67gIAAVoABXZhbHVleHABcHQAQGh0dHA6Ly9sb2NhbGhvc3Q6ODA4MC9rci1kZXYvcmVtb3RpbmcvT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2VzcgARamF2YS5sYW5nLkludGVnZXIS4qCk94GHOAIAAUkABXZhbHVleHIAEGphdmEubGFuZy5OdW1iZXKGrJUdC5TgiwIAAHhwAAAAAXNyAA5qYXZhLmxhbmcuTG9uZzuL5JDMjyPfAgABSgAFdmFsdWV4cQB+AB0AAAAAAAAPfXNyABlqYXZheC54bWwubmFtZXNwYWNlLlFOYW1lgW2oLfw73WwCAANMAAlsb2NhbFBhcnRxAH4ABEwADG5hbWVzcGFjZVVSSXEAfgAETAAGcHJlZml4cQB+AAR4cHQAGk9TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNldAAAcQB+ACR0B3RyTzBBQlhOeUFESnZjbWN1YTNWaGJHa3VjbWxqWlM1cmMySXViV1Z6YzJGbmFXNW5Ma3BoZG1GVFpYSjJhV05sUkdWbWFXNXBkR2x2YnJibkRWOUJOR3p4QWdBQlRBQVJjMlZ5ZG1salpVbHVkR1Z5Wm1GalpYTjBBQkJNYW1GMllTOTFkR2xzTDB4cGMzUTdlSElBTG05eVp5NXJkV0ZzYVM1eWFXTmxMbXR6WWk1dFpYTnpZV2RwYm1jdVUyVnlkbWxqWlVSbFptbHVhWFJwYjI0QW13SlBXTjBwZmdJQURVd0FDMkoxYzFObFkzVnlhWFI1ZEFBVFRHcGhkbUV2YkdGdVp5OUNiMjlzWldGdU8wd0FEMk55WldSbGJuUnBZV3h6Vkhsd1pYUUFURXh2Y21jdmEzVmhiR2t2Y21salpTOWpiM0psTDNObFkzVnlhWFI1TDJOeVpXUmxiblJwWVd4ekwwTnlaV1JsYm5ScFlXeHpVMjkxY21ObEpFTnlaV1JsYm5ScFlXeHpWSGx3WlR0TUFCQnNiMk5oYkZObGNuWnBZMlZPWVcxbGRBQVNUR3BoZG1FdmJHRnVaeTlUZEhKcGJtYzdUQUFYYldWemMyRm5aVVY0WTJWd2RHbHZia2hoYm1Sc1pYSnhBSDRBQlV3QURHMXBiR3hwYzFSdlRHbDJaWFFBRUV4cVlYWmhMMnhoYm1jdlRHOXVaenRNQUFod2NtbHZjbWwwZVhRQUUweHFZWFpoTDJ4aGJtY3ZTVzUwWldkbGNqdE1BQVZ4ZFdWMVpYRUFmZ0FEVEFBTmNtVjBjbmxCZEhSbGJYQjBjM0VBZmdBSFRBQUhjMlZ5ZG1salpYUUFFa3hxWVhaaEwyeGhibWN2VDJKcVpXTjBPMHdBRDNObGNuWnBZMlZGYm1SUWIybHVkSFFBRGt4cVlYWmhMMjVsZEM5VlVrdzdUQUFUYzJWeWRtbGpaVTVoYldWVGNHRmpaVlZTU1hFQWZnQUZUQUFRYzJWeWRtbGpaVTVoYldWemNHRmpaWEVBZmdBRlRBQUxjMlZ5ZG1salpWQmhkR2h4QUg0QUJYaHdjM0lBRVdwaGRtRXViR0Z1Wnk1Q2IyOXNaV0Z1elNCeWdOV2MrdTRDQUFGYUFBVjJZV3gxWlhod0FYQjBBQnBQVTBOaFkyaGxUbTkwYVdacFkyRjBhVzl1VTJWeWRtbGpaWFFBVFc5eVp5NXJkV0ZzYVM1eWFXTmxMbXR6WWk1dFpYTnpZV2RwYm1jdVpYaGpaWEIwYVc5dWFHRnVaR3hwYm1jdVJHVm1ZWFZzZEUxbGMzTmhaMlZGZUdObGNIUnBiMjVJWVc1a2JHVnljM0lBRG1waGRtRXViR0Z1Wnk1TWIyNW5PNHZra015UEk5OENBQUZLQUFWMllXeDFaWGh5QUJCcVlYWmhMbXhoYm1jdVRuVnRZbVZ5aHF5VkhRdVU0SXNDQUFCNGNQLy8vLy8vLy8vL2MzSUFFV3BoZG1FdWJHRnVaeTVKYm5SbFoyVnlFdUtncFBlQmh6Z0NBQUZKQUFWMllXeDFaWGh4QUg0QUVBQUFBQU56Y1FCK0FBc0FjUUIrQUJOd2MzSUFER3BoZG1FdWJtVjBMbFZTVEpZbE56WWEvT1J5QXdBSFNRQUlhR0Z6YUVOdlpHVkpBQVJ3YjNKMFRBQUpZWFYwYUc5eWFYUjVjUUIrQUFWTUFBUm1hV3hsY1FCK0FBVk1BQVJvYjNOMGNRQitBQVZNQUFod2NtOTBiMk52YkhFQWZnQUZUQUFEY21WbWNRQitBQVY0Y1AvLy8vOEFBQitRZEFBT2JHOWpZV3hvYjNOME9qZ3dPREIwQUNzdmEzSXRaR1YyTDNKbGJXOTBhVzVuTDA5VFEyRmphR1ZPYjNScFptbGpZWFJwYjI1VFpYSjJhV05sZEFBSmJHOWpZV3hvYjNOMGRBQUVhSFIwY0hCNGRBQUFkQUFHVkZKQlZrVk1kQUFCTDNOeUFCTnFZWFpoTG5WMGFXd3VRWEp5WVhsTWFYTjBlSUhTSFpuSFlaMERBQUZKQUFSemFYcGxlSEFBQUFBRmR3UUFBQUFLZEFBemIzSm5M';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 363    FOR UPDATE;        
    buffer := 'bXQxWVd4cExuSnBZMlV1YTNOaUxtMWxjM05oWjJsdVp5NXpaWEoyYVdObExrdFRRa3BoZG1GVFpYSjJhV05sZEFBOFkyOXRMbTl3Wlc1emVXMXdhRzl1ZVM1dmMyTmhZMmhsTG1KaGMyVXVaWFpsYm5SekxrTmhZMmhsUlc1MGNubEZkbVZ1ZEV4cGMzUmxibVZ5ZEFBM1kyOXRMbTl3Wlc1emVXMXdhRzl1ZVM1dmMyTmhZMmhsTG1KaGMyVXVaWFpsYm5SekxrTmhZMmhsUlhabGJuUk1hWE4wWlc1bGNuUUFGMnBoZG1FdWRYUnBiQzVGZG1WdWRFeHBjM1JsYm1WeWRBQXNZMjl0TG05d1pXNXplVzF3YUc5dWVTNXZjMk5oWTJobExtSmhjMlV1VEdsbVpXTjVZMnhsUVhkaGNtVjR0AA0xMjkuMTg2Ljc5LjQ1c3IAMm9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuSmF2YVNlcnZpY2VEZWZpbml0aW9utucNX0E0bPECAAFMABFzZXJ2aWNlSW50ZXJmYWNlc3QAEExqYXZhL3V0aWwvTGlzdDt4cgAub3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5TZXJ2aWNlRGVmaW5pdGlvbgCbAk9Y3Sl+AgANTAALYnVzU2VjdXJpdHlxAH4AE0wAD2NyZWRlbnRpYWxzVHlwZXQATExvcmcva3VhbGkvcmljZS9jb3JlL3NlY3VyaXR5L2NyZWRlbnRpYWxzL0NyZWRlbnRpYWxzU291cmNlJENyZWRlbnRpYWxzVHlwZTtMABBsb2NhbFNlcnZpY2VOYW1lcQB+AARMABdtZXNzYWdlRXhjZXB0aW9uSGFuZGxlcnEAfgAETAAMbWlsbGlzVG9MaXZlcQB+ABVMAAhwcmlvcml0eXEAfgAUTAAFcXVldWVxAH4AE0wADXJldHJ5QXR0ZW1wdHNxAH4AFEwAB3NlcnZpY2V0ABJMamF2YS9sYW5nL09iamVjdDtMAA9zZXJ2aWNlRW5kUG9pbnR0AA5MamF2YS9uZXQvVVJMO0wAE3NlcnZpY2VOYW1lU3BhY2VVUklxAH4ABEwAEHNlcnZpY2VOYW1lc3BhY2VxAH4ABEwAC3NlcnZpY2VQYXRocQB+AAR4cHNxAH4AGQFwdAAaT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2V0AE1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLmV4Y2VwdGlvbmhhbmRsaW5nLkRlZmF1bHRNZXNzYWdlRXhjZXB0aW9uSGFuZGxlcnNxAH4AH///////////c3EAfgAcAAAAA3NxAH4AGQBxAH4AMnBzcgAMamF2YS5uZXQuVVJMliU3Nhr85HIDAAdJAAhoYXNoQ29kZUkABHBvcnRMAAlhdXRob3JpdHlxAH4ABEwABGZpbGVxAH4ABEwABGhvc3RxAH4ABEwACHByb3RvY29scQB+AARMAANyZWZxAH4ABHhwaRuioAAAH5B0AA5sb2NhbGhvc3Q6ODA4MHQAKy9rci1kZXYvcmVtb3RpbmcvT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2V0AAlsb2NhbGhvc3R0AARodHRwcHh0AAB0AAZUUkFWRUx0AAEvc3IAE2phdmEudXRpbC5BcnJheUxpc3R4gdIdmcdhnQMAAUkABHNpemV4cAAAAAV3BAAAAAp0ADNvcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLnNlcnZpY2UuS1NCSmF2YVNlcnZpY2V0ADxjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5ldmVudHMuQ2FjaGVFbnRyeUV2ZW50TGlzdGVuZXJ0ADdjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5ldmVudHMuQ2FjaGVFdmVudExpc3RlbmVydAAXamF2YS51dGlsLkV2ZW50TGlzdGVuZXJ0ACxjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5MaWZlY3ljbGVBd2FyZXhxAH4AI3QABlRSQVZFTA==';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
INSERT INTO KRSB_MSG_PYLD_T (MSG_PYLD,MSG_QUE_ID)
  VALUES (EMPTY_CLOB(),364)
/
-- Length: 6108
-- Chunks: 2
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 364    FOR UPDATE;        
    buffer := 'rO0ABXNyAC1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLkFzeW5jaHJvbm91c0NhbGzxnQ5Y0t0lZQIAB1oAFWlnbm9yZVN0b3JlQW5kRm9yd2FyZFsACWFyZ3VtZW50c3QAE1tMamF2YS9sYW5nL09iamVjdDtMAAhjYWxsYmFja3QAM0xvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL0FzeW5jaHJvbm91c0NhbGxiYWNrO0wAB2NvbnRleHR0ABZMamF2YS9pby9TZXJpYWxpemFibGU7TAAKbWV0aG9kTmFtZXQAEkxqYXZhL2xhbmcvU3RyaW5nO1sACnBhcmFtVHlwZXN0ABJbTGphdmEvbGFuZy9DbGFzcztMAAtzZXJ2aWNlSW5mb3QAKkxvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL1NlcnZpY2VJbmZvO3hwAHVyABNbTGphdmEubGFuZy5PYmplY3Q7kM5YnxBzKWwCAAB4cAAAAAFzcgBDY29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLnBsdWdpbnMuY2x1c3RlcnN1cHBvcnQuQ2x1c3Rlck5vdGlmaWNhdGlvbhY+6VckvG9pAgACSQAEdHlwZUwABGRhdGFxAH4AA3hwAAAAAnQAM0RvY3VtZW50VHlwZVJ1bGVDYWNoZTpQb3N0YWxDb2RlTWFpbnRlbmFuY2VEb2N1bWVudHBwdAAGaW52b2tldXIAEltMamF2YS5sYW5nLkNsYXNzO6sW167LzVqZAgAAeHAAAAABdnIAFGphdmEuaW8uU2VyaWFsaXphYmxlEJthDdebZO0CAAB4cHNyAChvcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLlNlcnZpY2VJbmZvxRcjtikEt7oCAAtMAAVhbGl2ZXQAE0xqYXZhL2xhbmcvQm9vbGVhbjtMABRlbmRwb2ludEFsdGVybmF0ZVVybHEAfgAETAALZW5kcG9pbnRVcmxxAH4ABEwACmxvY2tWZXJOYnJ0ABNMamF2YS9sYW5nL0ludGVnZXI7TAAObWVzc2FnZUVudHJ5SWR0ABBMamF2YS9sYW5nL0xvbmc7TAAFcW5hbWV0ABtMamF2YXgveG1sL25hbWVzcGFjZS9RTmFtZTtMABpzZXJpYWxpemVkU2VydmljZU5hbWVzcGFjZXEAfgAETAAIc2VydmVySXBxAH4ABEwAEXNlcnZpY2VEZWZpbml0aW9udAAwTG9yZy9rdWFsaS9yaWNlL2tzYi9tZXNzYWdpbmcvU2VydmljZURlZmluaXRpb247TAALc2VydmljZU5hbWVxAH4ABEwAEHNlcnZpY2VOYW1lc3BhY2VxAH4ABHhwc3IAEWphdmEubGFuZy5Cb29sZWFuzSBygNWc+u4CAAFaAAV2YWx1ZXhwAXB0AEBodHRwOi8vbG9jYWxob3N0OjgwODAva3ItZGV2L3JlbW90aW5nL09TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNlc3IAEWphdmEubGFuZy5JbnRlZ2VyEuKgpPeBhzgCAAFJAAV2YWx1ZXhyABBqYXZhLmxhbmcuTnVtYmVyhqyVHQuU4IsCAAB4cAAAAAFzcgAOamF2YS5sYW5nLkxvbmc7i+SQzI8j3wIAAUoABXZhbHVleHEAfgAdAAAAAAAAD31zcgAZamF2YXgueG1sLm5hbWVzcGFjZS5RTmFtZYFtqC38O91sAgADTAAJbG9jYWxQYXJ0cQB+AARMAAxuYW1lc3BhY2VVUklxAH4ABEwABnByZWZpeHEAfgAEeHB0ABpPU0NhY2hlTm90aWZpY2F0aW9uU2VydmljZXQAAHEAfgAkdAd0ck8wQUJYTnlBREp2Y21jdWEzVmhiR2t1Y21salpTNXJjMkl1YldWemMyRm5hVzVuTGtwaGRtRlRaWEoyYVdObFJHVm1hVzVwZEdsdmJyYm5EVjlCTkd6eEFnQUJUQUFSYzJWeWRtbGpaVWx1ZEdWeVptRmpaWE4wQUJCTWFtRjJZUzkxZEdsc0wweHBjM1E3ZUhJQUxtOXlaeTVyZFdGc2FTNXlhV05sTG10ellpNXRaWE56WVdkcGJtY3VVMlZ5ZG1salpVUmxabWx1YVhScGIyNEFtd0pQV04wcGZnSUFEVXdBQzJKMWMxTmxZM1Z5YVhSNWRBQVRUR3BoZG1FdmJHRnVaeTlDYjI5c1pXRnVPMHdBRDJOeVpXUmxiblJwWVd4elZIbHdaWFFBVEV4dmNtY3ZhM1ZoYkdrdmNtbGpaUzlqYjNKbEwzTmxZM1Z5YVhSNUwyTnlaV1JsYm5ScFlXeHpMME55WldSbGJuUnBZV3h6VTI5MWNtTmxKRU55WldSbGJuUnBZV3h6Vkhsd1pUdE1BQkJzYjJOaGJGTmxjblpwWTJWT1lXMWxkQUFTVEdwaGRtRXZiR0Z1Wnk5VGRISnBibWM3VEFBWGJXVnpjMkZuWlVWNFkyVndkR2x2YmtoaGJtUnNaWEp4QUg0QUJVd0FERzFwYkd4cGMxUnZUR2wyWlhRQUVFeHFZWFpoTDJ4aGJtY3ZURzl1Wnp0TUFBaHdjbWx2Y21sMGVYUUFFMHhxWVhaaEwyeGhibWN2U1c1MFpXZGxjanRNQUFWeGRXVjFaWEVBZmdBRFRBQU5jbVYwY25sQmRIUmxiWEIwYzNFQWZnQUhUQUFIYzJWeWRtbGpaWFFBRWt4cVlYWmhMMnhoYm1jdlQySnFaV04wTzB3QUQzTmxjblpwWTJWRmJtUlFiMmx1ZEhRQURreHFZWFpoTDI1bGRDOVZVa3c3VEFBVGMyVnlkbWxqWlU1aGJXVlRjR0ZqWlZWU1NYRUFmZ0FGVEFBUWMyVnlkbWxqWlU1aGJXVnpjR0ZqWlhFQWZnQUZUQUFMYzJWeWRtbGpaVkJoZEdoeEFINEFCWGh3YzNJQUVXcGhkbUV1YkdGdVp5NUNiMjlzWldGdXpTQnlnTldjK3U0Q0FBRmFBQVYyWVd4MVpYaHdBWEIwQUJwUFUwTmhZMmhsVG05MGFXWnBZMkYwYVc5dVUyVnlkbWxqWlhRQVRXOXlaeTVyZFdGc2FTNXlhV05sTG10ellpNXRaWE56WVdkcGJtY3VaWGhqWlhCMGFXOXVhR0Z1Wkd4cGJtY3VSR1ZtWVhWc2RFMWxjM05oWjJWRmVHTmxjSFJwYjI1SVlXNWtiR1Z5YzNJQURtcGhkbUV1YkdGdVp5NU1iMjVuTzR2a2tNeVBJOThDQUFGS0FBVjJZV3gxWlhoeUFCQnFZWFpoTG14aGJtY3VUblZ0WW1WeWhxeVZIUXVVNElzQ0FBQjRjUC8vLy8vLy8vLy9jM0lBRVdwaGRtRXViR0Z1Wnk1SmJuUmxaMlZ5RXVLZ3BQZUJoemdDQUFGSkFBVjJZV3gxWlhoeEFINEFFQUFBQUFOemNRQitBQXNBY1FCK0FCTndjM0lBREdwaGRtRXVibVYwTGxWU1RKWWxOellhL09SeUF3QUhTUUFJYUdGemFFTnZaR1ZKQUFSd2IzSjBUQUFKWVhWMGFHOXlhWFI1Y1FCK0FBVk1BQVJtYVd4bGNRQitBQVZNQUFSb2IzTjBjUUIrQUFWTUFBaHdjbTkwYjJOdmJIRUFmZ0FGVEFBRGNtVm1jUUIrQUFWNGNQLy8vLzhBQUIrUWRBQU9iRzlqWVd4b2IzTjBPamd3T0RCMEFDc3ZhM0l0WkdWMkwzSmxiVzkwYVc1bkwwOVRRMkZqYUdWT2IzUnBabWxqWVhScGIyNVRaWEoyYVdObGRBQUpiRzlqWVd4b2IzTjBkQUFFYUhSMGNIQjRkQUFBZEFBR1ZGSkJWa1ZNZEFBQkwzTnlBQk5xWVhaaExuVjBhV3d1UVhKeVlYbE1hWE4wZUlIU0habkhZWjBEQUFGSkFBUnphWHBsZUhBQUFBQUZkd1FBQUFBS2RBQXpi';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 364    FOR UPDATE;        
    buffer := 'M0puTG10MVlXeHBMbkpwWTJVdWEzTmlMbTFsYzNOaFoybHVaeTV6WlhKMmFXTmxMa3RUUWtwaGRtRlRaWEoyYVdObGRBQThZMjl0TG05d1pXNXplVzF3YUc5dWVTNXZjMk5oWTJobExtSmhjMlV1WlhabGJuUnpMa05oWTJobFJXNTBjbmxGZG1WdWRFeHBjM1JsYm1WeWRBQTNZMjl0TG05d1pXNXplVzF3YUc5dWVTNXZjMk5oWTJobExtSmhjMlV1WlhabGJuUnpMa05oWTJobFJYWmxiblJNYVhOMFpXNWxjblFBRjJwaGRtRXVkWFJwYkM1RmRtVnVkRXhwYzNSbGJtVnlkQUFzWTI5dExtOXdaVzV6ZVcxd2FHOXVlUzV2YzJOaFkyaGxMbUpoYzJVdVRHbG1aV041WTJ4bFFYZGhjbVY0dAANMTI5LjE4Ni43OS40NXNyADJvcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLkphdmFTZXJ2aWNlRGVmaW5pdGlvbrbnDV9BNGzxAgABTAARc2VydmljZUludGVyZmFjZXN0ABBMamF2YS91dGlsL0xpc3Q7eHIALm9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuU2VydmljZURlZmluaXRpb24AmwJPWN0pfgIADUwAC2J1c1NlY3VyaXR5cQB+ABNMAA9jcmVkZW50aWFsc1R5cGV0AExMb3JnL2t1YWxpL3JpY2UvY29yZS9zZWN1cml0eS9jcmVkZW50aWFscy9DcmVkZW50aWFsc1NvdXJjZSRDcmVkZW50aWFsc1R5cGU7TAAQbG9jYWxTZXJ2aWNlTmFtZXEAfgAETAAXbWVzc2FnZUV4Y2VwdGlvbkhhbmRsZXJxAH4ABEwADG1pbGxpc1RvTGl2ZXEAfgAVTAAIcHJpb3JpdHlxAH4AFEwABXF1ZXVlcQB+ABNMAA1yZXRyeUF0dGVtcHRzcQB+ABRMAAdzZXJ2aWNldAASTGphdmEvbGFuZy9PYmplY3Q7TAAPc2VydmljZUVuZFBvaW50dAAOTGphdmEvbmV0L1VSTDtMABNzZXJ2aWNlTmFtZVNwYWNlVVJJcQB+AARMABBzZXJ2aWNlTmFtZXNwYWNlcQB+AARMAAtzZXJ2aWNlUGF0aHEAfgAEeHBzcQB+ABkBcHQAGk9TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNldABNb3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5leGNlcHRpb25oYW5kbGluZy5EZWZhdWx0TWVzc2FnZUV4Y2VwdGlvbkhhbmRsZXJzcQB+AB///////////3NxAH4AHAAAAANzcQB+ABkAcQB+ADJwc3IADGphdmEubmV0LlVSTJYlNzYa/ORyAwAHSQAIaGFzaENvZGVJAARwb3J0TAAJYXV0aG9yaXR5cQB+AARMAARmaWxlcQB+AARMAARob3N0cQB+AARMAAhwcm90b2NvbHEAfgAETAADcmVmcQB+AAR4cGkboqAAAB+QdAAObG9jYWxob3N0OjgwODB0ACsva3ItZGV2L3JlbW90aW5nL09TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNldAAJbG9jYWxob3N0dAAEaHR0cHB4dAAAdAAGVFJBVkVMdAABL3NyABNqYXZhLnV0aWwuQXJyYXlMaXN0eIHSHZnHYZ0DAAFJAARzaXpleHAAAAAFdwQAAAAKdAAzb3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5zZXJ2aWNlLktTQkphdmFTZXJ2aWNldAA8Y29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLmJhc2UuZXZlbnRzLkNhY2hlRW50cnlFdmVudExpc3RlbmVydAA3Y29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLmJhc2UuZXZlbnRzLkNhY2hlRXZlbnRMaXN0ZW5lcnQAF2phdmEudXRpbC5FdmVudExpc3RlbmVydAAsY29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLmJhc2UuTGlmZWN5Y2xlQXdhcmV4cQB+ACN0AAZUUkFWRUw=';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
INSERT INTO KRSB_MSG_PYLD_T (MSG_PYLD,MSG_QUE_ID)
  VALUES (EMPTY_CLOB(),365)
/
-- Length: 6100
-- Chunks: 2
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 365    FOR UPDATE;        
    buffer := 'rO0ABXNyAC1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLkFzeW5jaHJvbm91c0NhbGzxnQ5Y0t0lZQIAB1oAFWlnbm9yZVN0b3JlQW5kRm9yd2FyZFsACWFyZ3VtZW50c3QAE1tMamF2YS9sYW5nL09iamVjdDtMAAhjYWxsYmFja3QAM0xvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL0FzeW5jaHJvbm91c0NhbGxiYWNrO0wAB2NvbnRleHR0ABZMamF2YS9pby9TZXJpYWxpemFibGU7TAAKbWV0aG9kTmFtZXQAEkxqYXZhL2xhbmcvU3RyaW5nO1sACnBhcmFtVHlwZXN0ABJbTGphdmEvbGFuZy9DbGFzcztMAAtzZXJ2aWNlSW5mb3QAKkxvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL1NlcnZpY2VJbmZvO3hwAHVyABNbTGphdmEubGFuZy5PYmplY3Q7kM5YnxBzKWwCAAB4cAAAAAFzcgBDY29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLnBsdWdpbnMuY2x1c3RlcnN1cHBvcnQuQ2x1c3Rlck5vdGlmaWNhdGlvbhY+6VckvG9pAgACSQAEdHlwZUwABGRhdGFxAH4AA3hwAAAAAnQALkRvY3VtZW50VHlwZVJ1bGVDYWNoZTpTdGF0ZU1haW50ZW5hbmNlRG9jdW1lbnRwcHQABmludm9rZXVyABJbTGphdmEubGFuZy5DbGFzczurFteuy81amQIAAHhwAAAAAXZyABRqYXZhLmlvLlNlcmlhbGl6YWJsZRCbYQ3Xm2TtAgAAeHBzcgAob3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5TZXJ2aWNlSW5mb8UXI7YpBLe6AgALTAAFYWxpdmV0ABNMamF2YS9sYW5nL0Jvb2xlYW47TAAUZW5kcG9pbnRBbHRlcm5hdGVVcmxxAH4ABEwAC2VuZHBvaW50VXJscQB+AARMAApsb2NrVmVyTmJydAATTGphdmEvbGFuZy9JbnRlZ2VyO0wADm1lc3NhZ2VFbnRyeUlkdAAQTGphdmEvbGFuZy9Mb25nO0wABXFuYW1ldAAbTGphdmF4L3htbC9uYW1lc3BhY2UvUU5hbWU7TAAac2VyaWFsaXplZFNlcnZpY2VOYW1lc3BhY2VxAH4ABEwACHNlcnZlcklwcQB+AARMABFzZXJ2aWNlRGVmaW5pdGlvbnQAMExvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL1NlcnZpY2VEZWZpbml0aW9uO0wAC3NlcnZpY2VOYW1lcQB+AARMABBzZXJ2aWNlTmFtZXNwYWNlcQB+AAR4cHNyABFqYXZhLmxhbmcuQm9vbGVhbs0gcoDVnPruAgABWgAFdmFsdWV4cAFwdABAaHR0cDovL2xvY2FsaG9zdDo4MDgwL2tyLWRldi9yZW1vdGluZy9PU0NhY2hlTm90aWZpY2F0aW9uU2VydmljZXNyABFqYXZhLmxhbmcuSW50ZWdlchLioKT3gYc4AgABSQAFdmFsdWV4cgAQamF2YS5sYW5nLk51bWJlcoaslR0LlOCLAgAAeHAAAAABc3IADmphdmEubGFuZy5Mb25nO4vkkMyPI98CAAFKAAV2YWx1ZXhxAH4AHQAAAAAAAA99c3IAGWphdmF4LnhtbC5uYW1lc3BhY2UuUU5hbWWBbagt/DvdbAIAA0wACWxvY2FsUGFydHEAfgAETAAMbmFtZXNwYWNlVVJJcQB+AARMAAZwcmVmaXhxAH4ABHhwdAAaT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2V0AABxAH4AJHQHdHJPMEFCWE55QURKdmNtY3VhM1ZoYkdrdWNtbGpaUzVyYzJJdWJXVnpjMkZuYVc1bkxrcGhkbUZUWlhKMmFXTmxSR1ZtYVc1cGRHbHZicmJuRFY5Qk5HenhBZ0FCVEFBUmMyVnlkbWxqWlVsdWRHVnlabUZqWlhOMEFCQk1hbUYyWVM5MWRHbHNMMHhwYzNRN2VISUFMbTl5Wnk1cmRXRnNhUzV5YVdObExtdHpZaTV0WlhOellXZHBibWN1VTJWeWRtbGpaVVJsWm1sdWFYUnBiMjRBbXdKUFdOMHBmZ0lBRFV3QUMySjFjMU5sWTNWeWFYUjVkQUFUVEdwaGRtRXZiR0Z1Wnk5Q2IyOXNaV0Z1TzB3QUQyTnlaV1JsYm5ScFlXeHpWSGx3WlhRQVRFeHZjbWN2YTNWaGJHa3ZjbWxqWlM5amIzSmxMM05sWTNWeWFYUjVMMk55WldSbGJuUnBZV3h6TDBOeVpXUmxiblJwWVd4elUyOTFjbU5sSkVOeVpXUmxiblJwWVd4elZIbHdaVHRNQUJCc2IyTmhiRk5sY25acFkyVk9ZVzFsZEFBU1RHcGhkbUV2YkdGdVp5OVRkSEpwYm1jN1RBQVhiV1Z6YzJGblpVVjRZMlZ3ZEdsdmJraGhibVJzWlhKeEFINEFCVXdBREcxcGJHeHBjMVJ2VEdsMlpYUUFFRXhxWVhaaEwyeGhibWN2VEc5dVp6dE1BQWh3Y21sdmNtbDBlWFFBRTB4cVlYWmhMMnhoYm1jdlNXNTBaV2RsY2p0TUFBVnhkV1YxWlhFQWZnQURUQUFOY21WMGNubEJkSFJsYlhCMGMzRUFmZ0FIVEFBSGMyVnlkbWxqWlhRQUVreHFZWFpoTDJ4aGJtY3ZUMkpxWldOME8wd0FEM05sY25acFkyVkZibVJRYjJsdWRIUUFEa3hxWVhaaEwyNWxkQzlWVWt3N1RBQVRjMlZ5ZG1salpVNWhiV1ZUY0dGalpWVlNTWEVBZmdBRlRBQVFjMlZ5ZG1salpVNWhiV1Z6Y0dGalpYRUFmZ0FGVEFBTGMyVnlkbWxqWlZCaGRHaHhBSDRBQlhod2MzSUFFV3BoZG1FdWJHRnVaeTVDYjI5c1pXRnV6U0J5Z05XYyt1NENBQUZhQUFWMllXeDFaWGh3QVhCMEFCcFBVME5oWTJobFRtOTBhV1pwWTJGMGFXOXVVMlZ5ZG1salpYUUFUVzl5Wnk1cmRXRnNhUzV5YVdObExtdHpZaTV0WlhOellXZHBibWN1WlhoalpYQjBhVzl1YUdGdVpHeHBibWN1UkdWbVlYVnNkRTFsYzNOaFoyVkZlR05sY0hScGIyNUlZVzVrYkdWeWMzSUFEbXBoZG1FdWJHRnVaeTVNYjI1bk80dmtrTXlQSTk4Q0FBRktBQVYyWVd4MVpYaHlBQkJxWVhaaExteGhibWN1VG5WdFltVnlocXlWSFF1VTRJc0NBQUI0Y1AvLy8vLy8vLy8vYzNJQUVXcGhkbUV1YkdGdVp5NUpiblJsWjJWeUV1S2dwUGVCaHpnQ0FBRkpBQVYyWVd4MVpYaHhBSDRBRUFBQUFBTnpjUUIrQUFzQWNRQitBQk53YzNJQURHcGhkbUV1Ym1WMExsVlNUSllsTnpZYS9PUnlBd0FIU1FBSWFHRnphRU52WkdWSkFBUndiM0owVEFBSllYVjBhRzl5YVhSNWNRQitBQVZNQUFSbWFXeGxjUUIrQUFWTUFBUm9iM04wY1FCK0FBVk1BQWh3Y205MGIyTnZiSEVBZmdBRlRBQURjbVZtY1FCK0FBVjRjUC8vLy84QUFCK1FkQUFPYkc5allXeG9iM04wT2pnd09EQjBBQ3N2YTNJdFpHVjJMM0psYlc5MGFXNW5MMDlUUTJGamFHVk9iM1JwWm1sallYUnBiMjVUWlhKMmFXTmxkQUFKYkc5allXeG9iM04wZEFBRWFIUjBjSEI0ZEFBQWRBQUdWRkpCVmtWTWRBQUJMM055QUJOcVlYWmhMblYwYVd3dVFYSnlZWGxNYVhOMGVJSFNIWm5IWVowREFBRkpBQVJ6YVhwbGVIQUFBQUFGZHdRQUFBQUtkQUF6YjNKbkxt';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 365    FOR UPDATE;        
    buffer := 'dDFZV3hwTG5KcFkyVXVhM05pTG0xbGMzTmhaMmx1Wnk1elpYSjJhV05sTGt0VFFrcGhkbUZUWlhKMmFXTmxkQUE4WTI5dExtOXdaVzV6ZVcxd2FHOXVlUzV2YzJOaFkyaGxMbUpoYzJVdVpYWmxiblJ6TGtOaFkyaGxSVzUwY25sRmRtVnVkRXhwYzNSbGJtVnlkQUEzWTI5dExtOXdaVzV6ZVcxd2FHOXVlUzV2YzJOaFkyaGxMbUpoYzJVdVpYWmxiblJ6TGtOaFkyaGxSWFpsYm5STWFYTjBaVzVsY25RQUYycGhkbUV1ZFhScGJDNUZkbVZ1ZEV4cGMzUmxibVZ5ZEFBc1kyOXRMbTl3Wlc1emVXMXdhRzl1ZVM1dmMyTmhZMmhsTG1KaGMyVXVUR2xtWldONVkyeGxRWGRoY21WNHQADTEyOS4xODYuNzkuNDVzcgAyb3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5KYXZhU2VydmljZURlZmluaXRpb2625w1fQTRs8QIAAUwAEXNlcnZpY2VJbnRlcmZhY2VzdAAQTGphdmEvdXRpbC9MaXN0O3hyAC5vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLlNlcnZpY2VEZWZpbml0aW9uAJsCT1jdKX4CAA1MAAtidXNTZWN1cml0eXEAfgATTAAPY3JlZGVudGlhbHNUeXBldABMTG9yZy9rdWFsaS9yaWNlL2NvcmUvc2VjdXJpdHkvY3JlZGVudGlhbHMvQ3JlZGVudGlhbHNTb3VyY2UkQ3JlZGVudGlhbHNUeXBlO0wAEGxvY2FsU2VydmljZU5hbWVxAH4ABEwAF21lc3NhZ2VFeGNlcHRpb25IYW5kbGVycQB+AARMAAxtaWxsaXNUb0xpdmVxAH4AFUwACHByaW9yaXR5cQB+ABRMAAVxdWV1ZXEAfgATTAANcmV0cnlBdHRlbXB0c3EAfgAUTAAHc2VydmljZXQAEkxqYXZhL2xhbmcvT2JqZWN0O0wAD3NlcnZpY2VFbmRQb2ludHQADkxqYXZhL25ldC9VUkw7TAATc2VydmljZU5hbWVTcGFjZVVSSXEAfgAETAAQc2VydmljZU5hbWVzcGFjZXEAfgAETAALc2VydmljZVBhdGhxAH4ABHhwc3EAfgAZAXB0ABpPU0NhY2hlTm90aWZpY2F0aW9uU2VydmljZXQATW9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuZXhjZXB0aW9uaGFuZGxpbmcuRGVmYXVsdE1lc3NhZ2VFeGNlcHRpb25IYW5kbGVyc3EAfgAf//////////9zcQB+ABwAAAADc3EAfgAZAHEAfgAycHNyAAxqYXZhLm5ldC5VUkyWJTc2GvzkcgMAB0kACGhhc2hDb2RlSQAEcG9ydEwACWF1dGhvcml0eXEAfgAETAAEZmlsZXEAfgAETAAEaG9zdHEAfgAETAAIcHJvdG9jb2xxAH4ABEwAA3JlZnEAfgAEeHBpG6KgAAAfkHQADmxvY2FsaG9zdDo4MDgwdAArL2tyLWRldi9yZW1vdGluZy9PU0NhY2hlTm90aWZpY2F0aW9uU2VydmljZXQACWxvY2FsaG9zdHQABGh0dHBweHQAAHQABlRSQVZFTHQAAS9zcgATamF2YS51dGlsLkFycmF5TGlzdHiB0h2Zx2GdAwABSQAEc2l6ZXhwAAAABXcEAAAACnQAM29yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuc2VydmljZS5LU0JKYXZhU2VydmljZXQAPGNvbS5vcGVuc3ltcGhvbnkub3NjYWNoZS5iYXNlLmV2ZW50cy5DYWNoZUVudHJ5RXZlbnRMaXN0ZW5lcnQAN2NvbS5vcGVuc3ltcGhvbnkub3NjYWNoZS5iYXNlLmV2ZW50cy5DYWNoZUV2ZW50TGlzdGVuZXJ0ABdqYXZhLnV0aWwuRXZlbnRMaXN0ZW5lcnQALGNvbS5vcGVuc3ltcGhvbnkub3NjYWNoZS5iYXNlLkxpZmVjeWNsZUF3YXJleHEAfgAjdAAGVFJBVkVM';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
INSERT INTO KRSB_MSG_PYLD_T (MSG_PYLD,MSG_QUE_ID)
  VALUES (EMPTY_CLOB(),366)
/
-- Length: 6104
-- Chunks: 2
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 366    FOR UPDATE;        
    buffer := 'rO0ABXNyAC1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLkFzeW5jaHJvbm91c0NhbGzxnQ5Y0t0lZQIAB1oAFWlnbm9yZVN0b3JlQW5kRm9yd2FyZFsACWFyZ3VtZW50c3QAE1tMamF2YS9sYW5nL09iamVjdDtMAAhjYWxsYmFja3QAM0xvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL0FzeW5jaHJvbm91c0NhbGxiYWNrO0wAB2NvbnRleHR0ABZMamF2YS9pby9TZXJpYWxpemFibGU7TAAKbWV0aG9kTmFtZXQAEkxqYXZhL2xhbmcvU3RyaW5nO1sACnBhcmFtVHlwZXN0ABJbTGphdmEvbGFuZy9DbGFzcztMAAtzZXJ2aWNlSW5mb3QAKkxvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL1NlcnZpY2VJbmZvO3hwAHVyABNbTGphdmEubGFuZy5PYmplY3Q7kM5YnxBzKWwCAAB4cAAAAAFzcgBDY29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLnBsdWdpbnMuY2x1c3RlcnN1cHBvcnQuQ2x1c3Rlck5vdGlmaWNhdGlvbhY+6VckvG9pAgACSQAEdHlwZUwABGRhdGFxAH4AA3hwAAAAAnQAMERvY3VtZW50VHlwZVJ1bGVDYWNoZTpJZGVudGl0eU1hbmFnZW1lbnREb2N1bWVudHBwdAAGaW52b2tldXIAEltMamF2YS5sYW5nLkNsYXNzO6sW167LzVqZAgAAeHAAAAABdnIAFGphdmEuaW8uU2VyaWFsaXphYmxlEJthDdebZO0CAAB4cHNyAChvcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLlNlcnZpY2VJbmZvxRcjtikEt7oCAAtMAAVhbGl2ZXQAE0xqYXZhL2xhbmcvQm9vbGVhbjtMABRlbmRwb2ludEFsdGVybmF0ZVVybHEAfgAETAALZW5kcG9pbnRVcmxxAH4ABEwACmxvY2tWZXJOYnJ0ABNMamF2YS9sYW5nL0ludGVnZXI7TAAObWVzc2FnZUVudHJ5SWR0ABBMamF2YS9sYW5nL0xvbmc7TAAFcW5hbWV0ABtMamF2YXgveG1sL25hbWVzcGFjZS9RTmFtZTtMABpzZXJpYWxpemVkU2VydmljZU5hbWVzcGFjZXEAfgAETAAIc2VydmVySXBxAH4ABEwAEXNlcnZpY2VEZWZpbml0aW9udAAwTG9yZy9rdWFsaS9yaWNlL2tzYi9tZXNzYWdpbmcvU2VydmljZURlZmluaXRpb247TAALc2VydmljZU5hbWVxAH4ABEwAEHNlcnZpY2VOYW1lc3BhY2VxAH4ABHhwc3IAEWphdmEubGFuZy5Cb29sZWFuzSBygNWc+u4CAAFaAAV2YWx1ZXhwAXB0AEBodHRwOi8vbG9jYWxob3N0OjgwODAva3ItZGV2L3JlbW90aW5nL09TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNlc3IAEWphdmEubGFuZy5JbnRlZ2VyEuKgpPeBhzgCAAFJAAV2YWx1ZXhyABBqYXZhLmxhbmcuTnVtYmVyhqyVHQuU4IsCAAB4cAAAAAFzcgAOamF2YS5sYW5nLkxvbmc7i+SQzI8j3wIAAUoABXZhbHVleHEAfgAdAAAAAAAAD31zcgAZamF2YXgueG1sLm5hbWVzcGFjZS5RTmFtZYFtqC38O91sAgADTAAJbG9jYWxQYXJ0cQB+AARMAAxuYW1lc3BhY2VVUklxAH4ABEwABnByZWZpeHEAfgAEeHB0ABpPU0NhY2hlTm90aWZpY2F0aW9uU2VydmljZXQAAHEAfgAkdAd0ck8wQUJYTnlBREp2Y21jdWEzVmhiR2t1Y21salpTNXJjMkl1YldWemMyRm5hVzVuTGtwaGRtRlRaWEoyYVdObFJHVm1hVzVwZEdsdmJyYm5EVjlCTkd6eEFnQUJUQUFSYzJWeWRtbGpaVWx1ZEdWeVptRmpaWE4wQUJCTWFtRjJZUzkxZEdsc0wweHBjM1E3ZUhJQUxtOXlaeTVyZFdGc2FTNXlhV05sTG10ellpNXRaWE56WVdkcGJtY3VVMlZ5ZG1salpVUmxabWx1YVhScGIyNEFtd0pQV04wcGZnSUFEVXdBQzJKMWMxTmxZM1Z5YVhSNWRBQVRUR3BoZG1FdmJHRnVaeTlDYjI5c1pXRnVPMHdBRDJOeVpXUmxiblJwWVd4elZIbHdaWFFBVEV4dmNtY3ZhM1ZoYkdrdmNtbGpaUzlqYjNKbEwzTmxZM1Z5YVhSNUwyTnlaV1JsYm5ScFlXeHpMME55WldSbGJuUnBZV3h6VTI5MWNtTmxKRU55WldSbGJuUnBZV3h6Vkhsd1pUdE1BQkJzYjJOaGJGTmxjblpwWTJWT1lXMWxkQUFTVEdwaGRtRXZiR0Z1Wnk5VGRISnBibWM3VEFBWGJXVnpjMkZuWlVWNFkyVndkR2x2YmtoaGJtUnNaWEp4QUg0QUJVd0FERzFwYkd4cGMxUnZUR2wyWlhRQUVFeHFZWFpoTDJ4aGJtY3ZURzl1Wnp0TUFBaHdjbWx2Y21sMGVYUUFFMHhxWVhaaEwyeGhibWN2U1c1MFpXZGxjanRNQUFWeGRXVjFaWEVBZmdBRFRBQU5jbVYwY25sQmRIUmxiWEIwYzNFQWZnQUhUQUFIYzJWeWRtbGpaWFFBRWt4cVlYWmhMMnhoYm1jdlQySnFaV04wTzB3QUQzTmxjblpwWTJWRmJtUlFiMmx1ZEhRQURreHFZWFpoTDI1bGRDOVZVa3c3VEFBVGMyVnlkbWxqWlU1aGJXVlRjR0ZqWlZWU1NYRUFmZ0FGVEFBUWMyVnlkbWxqWlU1aGJXVnpjR0ZqWlhFQWZnQUZUQUFMYzJWeWRtbGpaVkJoZEdoeEFINEFCWGh3YzNJQUVXcGhkbUV1YkdGdVp5NUNiMjlzWldGdXpTQnlnTldjK3U0Q0FBRmFBQVYyWVd4MVpYaHdBWEIwQUJwUFUwTmhZMmhsVG05MGFXWnBZMkYwYVc5dVUyVnlkbWxqWlhRQVRXOXlaeTVyZFdGc2FTNXlhV05sTG10ellpNXRaWE56WVdkcGJtY3VaWGhqWlhCMGFXOXVhR0Z1Wkd4cGJtY3VSR1ZtWVhWc2RFMWxjM05oWjJWRmVHTmxjSFJwYjI1SVlXNWtiR1Z5YzNJQURtcGhkbUV1YkdGdVp5NU1iMjVuTzR2a2tNeVBJOThDQUFGS0FBVjJZV3gxWlhoeUFCQnFZWFpoTG14aGJtY3VUblZ0WW1WeWhxeVZIUXVVNElzQ0FBQjRjUC8vLy8vLy8vLy9jM0lBRVdwaGRtRXViR0Z1Wnk1SmJuUmxaMlZ5RXVLZ3BQZUJoemdDQUFGSkFBVjJZV3gxWlhoeEFINEFFQUFBQUFOemNRQitBQXNBY1FCK0FCTndjM0lBREdwaGRtRXVibVYwTGxWU1RKWWxOellhL09SeUF3QUhTUUFJYUdGemFFTnZaR1ZKQUFSd2IzSjBUQUFKWVhWMGFHOXlhWFI1Y1FCK0FBVk1BQVJtYVd4bGNRQitBQVZNQUFSb2IzTjBjUUIrQUFWTUFBaHdjbTkwYjJOdmJIRUFmZ0FGVEFBRGNtVm1jUUIrQUFWNGNQLy8vLzhBQUIrUWRBQU9iRzlqWVd4b2IzTjBPamd3T0RCMEFDc3ZhM0l0WkdWMkwzSmxiVzkwYVc1bkwwOVRRMkZqYUdWT2IzUnBabWxqWVhScGIyNVRaWEoyYVdObGRBQUpiRzlqWVd4b2IzTjBkQUFFYUhSMGNIQjRkQUFBZEFBR1ZGSkJWa1ZNZEFBQkwzTnlBQk5xWVhaaExuVjBhV3d1UVhKeVlYbE1hWE4wZUlIU0habkhZWjBEQUFGSkFBUnphWHBsZUhBQUFBQUZkd1FBQUFBS2RBQXpiM0pu';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 366    FOR UPDATE;        
    buffer := 'TG10MVlXeHBMbkpwWTJVdWEzTmlMbTFsYzNOaFoybHVaeTV6WlhKMmFXTmxMa3RUUWtwaGRtRlRaWEoyYVdObGRBQThZMjl0TG05d1pXNXplVzF3YUc5dWVTNXZjMk5oWTJobExtSmhjMlV1WlhabGJuUnpMa05oWTJobFJXNTBjbmxGZG1WdWRFeHBjM1JsYm1WeWRBQTNZMjl0TG05d1pXNXplVzF3YUc5dWVTNXZjMk5oWTJobExtSmhjMlV1WlhabGJuUnpMa05oWTJobFJYWmxiblJNYVhOMFpXNWxjblFBRjJwaGRtRXVkWFJwYkM1RmRtVnVkRXhwYzNSbGJtVnlkQUFzWTI5dExtOXdaVzV6ZVcxd2FHOXVlUzV2YzJOaFkyaGxMbUpoYzJVdVRHbG1aV041WTJ4bFFYZGhjbVY0dAANMTI5LjE4Ni43OS40NXNyADJvcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLkphdmFTZXJ2aWNlRGVmaW5pdGlvbrbnDV9BNGzxAgABTAARc2VydmljZUludGVyZmFjZXN0ABBMamF2YS91dGlsL0xpc3Q7eHIALm9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuU2VydmljZURlZmluaXRpb24AmwJPWN0pfgIADUwAC2J1c1NlY3VyaXR5cQB+ABNMAA9jcmVkZW50aWFsc1R5cGV0AExMb3JnL2t1YWxpL3JpY2UvY29yZS9zZWN1cml0eS9jcmVkZW50aWFscy9DcmVkZW50aWFsc1NvdXJjZSRDcmVkZW50aWFsc1R5cGU7TAAQbG9jYWxTZXJ2aWNlTmFtZXEAfgAETAAXbWVzc2FnZUV4Y2VwdGlvbkhhbmRsZXJxAH4ABEwADG1pbGxpc1RvTGl2ZXEAfgAVTAAIcHJpb3JpdHlxAH4AFEwABXF1ZXVlcQB+ABNMAA1yZXRyeUF0dGVtcHRzcQB+ABRMAAdzZXJ2aWNldAASTGphdmEvbGFuZy9PYmplY3Q7TAAPc2VydmljZUVuZFBvaW50dAAOTGphdmEvbmV0L1VSTDtMABNzZXJ2aWNlTmFtZVNwYWNlVVJJcQB+AARMABBzZXJ2aWNlTmFtZXNwYWNlcQB+AARMAAtzZXJ2aWNlUGF0aHEAfgAEeHBzcQB+ABkBcHQAGk9TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNldABNb3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5leGNlcHRpb25oYW5kbGluZy5EZWZhdWx0TWVzc2FnZUV4Y2VwdGlvbkhhbmRsZXJzcQB+AB///////////3NxAH4AHAAAAANzcQB+ABkAcQB+ADJwc3IADGphdmEubmV0LlVSTJYlNzYa/ORyAwAHSQAIaGFzaENvZGVJAARwb3J0TAAJYXV0aG9yaXR5cQB+AARMAARmaWxlcQB+AARMAARob3N0cQB+AARMAAhwcm90b2NvbHEAfgAETAADcmVmcQB+AAR4cGkboqAAAB+QdAAObG9jYWxob3N0OjgwODB0ACsva3ItZGV2L3JlbW90aW5nL09TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNldAAJbG9jYWxob3N0dAAEaHR0cHB4dAAAdAAGVFJBVkVMdAABL3NyABNqYXZhLnV0aWwuQXJyYXlMaXN0eIHSHZnHYZ0DAAFJAARzaXpleHAAAAAFdwQAAAAKdAAzb3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5zZXJ2aWNlLktTQkphdmFTZXJ2aWNldAA8Y29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLmJhc2UuZXZlbnRzLkNhY2hlRW50cnlFdmVudExpc3RlbmVydAA3Y29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLmJhc2UuZXZlbnRzLkNhY2hlRXZlbnRMaXN0ZW5lcnQAF2phdmEudXRpbC5FdmVudExpc3RlbmVydAAsY29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLmJhc2UuTGlmZWN5Y2xlQXdhcmV4cQB+ACN0AAZUUkFWRUw=';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
INSERT INTO KRSB_MSG_PYLD_T (MSG_PYLD,MSG_QUE_ID)
  VALUES (EMPTY_CLOB(),367)
/
-- Length: 6112
-- Chunks: 2
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 367    FOR UPDATE;        
    buffer := 'rO0ABXNyAC1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLkFzeW5jaHJvbm91c0NhbGzxnQ5Y0t0lZQIAB1oAFWlnbm9yZVN0b3JlQW5kRm9yd2FyZFsACWFyZ3VtZW50c3QAE1tMamF2YS9sYW5nL09iamVjdDtMAAhjYWxsYmFja3QAM0xvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL0FzeW5jaHJvbm91c0NhbGxiYWNrO0wAB2NvbnRleHR0ABZMamF2YS9pby9TZXJpYWxpemFibGU7TAAKbWV0aG9kTmFtZXQAEkxqYXZhL2xhbmcvU3RyaW5nO1sACnBhcmFtVHlwZXN0ABJbTGphdmEvbGFuZy9DbGFzcztMAAtzZXJ2aWNlSW5mb3QAKkxvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL1NlcnZpY2VJbmZvO3hwAHVyABNbTGphdmEubGFuZy5PYmplY3Q7kM5YnxBzKWwCAAB4cAAAAAFzcgBDY29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLnBsdWdpbnMuY2x1c3RlcnN1cHBvcnQuQ2x1c3Rlck5vdGlmaWNhdGlvbhY+6VckvG9pAgACSQAEdHlwZUwABGRhdGFxAH4AA3hwAAAAAnQANURvY3VtZW50VHlwZVJ1bGVDYWNoZTpJZGVudGl0eU1hbmFnZW1lbnRHcm91cERvY3VtZW50cHB0AAZpbnZva2V1cgASW0xqYXZhLmxhbmcuQ2xhc3M7qxbXrsvNWpkCAAB4cAAAAAF2cgAUamF2YS5pby5TZXJpYWxpemFibGUQm2EN15tk7QIAAHhwc3IAKG9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuU2VydmljZUluZm/FFyO2KQS3ugIAC0wABWFsaXZldAATTGphdmEvbGFuZy9Cb29sZWFuO0wAFGVuZHBvaW50QWx0ZXJuYXRlVXJscQB+AARMAAtlbmRwb2ludFVybHEAfgAETAAKbG9ja1Zlck5icnQAE0xqYXZhL2xhbmcvSW50ZWdlcjtMAA5tZXNzYWdlRW50cnlJZHQAEExqYXZhL2xhbmcvTG9uZztMAAVxbmFtZXQAG0xqYXZheC94bWwvbmFtZXNwYWNlL1FOYW1lO0wAGnNlcmlhbGl6ZWRTZXJ2aWNlTmFtZXNwYWNlcQB+AARMAAhzZXJ2ZXJJcHEAfgAETAARc2VydmljZURlZmluaXRpb250ADBMb3JnL2t1YWxpL3JpY2Uva3NiL21lc3NhZ2luZy9TZXJ2aWNlRGVmaW5pdGlvbjtMAAtzZXJ2aWNlTmFtZXEAfgAETAAQc2VydmljZU5hbWVzcGFjZXEAfgAEeHBzcgARamF2YS5sYW5nLkJvb2xlYW7NIHKA1Zz67gIAAVoABXZhbHVleHABcHQAQGh0dHA6Ly9sb2NhbGhvc3Q6ODA4MC9rci1kZXYvcmVtb3RpbmcvT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2VzcgARamF2YS5sYW5nLkludGVnZXIS4qCk94GHOAIAAUkABXZhbHVleHIAEGphdmEubGFuZy5OdW1iZXKGrJUdC5TgiwIAAHhwAAAAAXNyAA5qYXZhLmxhbmcuTG9uZzuL5JDMjyPfAgABSgAFdmFsdWV4cQB+AB0AAAAAAAAPfXNyABlqYXZheC54bWwubmFtZXNwYWNlLlFOYW1lgW2oLfw73WwCAANMAAlsb2NhbFBhcnRxAH4ABEwADG5hbWVzcGFjZVVSSXEAfgAETAAGcHJlZml4cQB+AAR4cHQAGk9TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNldAAAcQB+ACR0B3RyTzBBQlhOeUFESnZjbWN1YTNWaGJHa3VjbWxqWlM1cmMySXViV1Z6YzJGbmFXNW5Ma3BoZG1GVFpYSjJhV05sUkdWbWFXNXBkR2x2YnJibkRWOUJOR3p4QWdBQlRBQVJjMlZ5ZG1salpVbHVkR1Z5Wm1GalpYTjBBQkJNYW1GMllTOTFkR2xzTDB4cGMzUTdlSElBTG05eVp5NXJkV0ZzYVM1eWFXTmxMbXR6WWk1dFpYTnpZV2RwYm1jdVUyVnlkbWxqWlVSbFptbHVhWFJwYjI0QW13SlBXTjBwZmdJQURVd0FDMkoxYzFObFkzVnlhWFI1ZEFBVFRHcGhkbUV2YkdGdVp5OUNiMjlzWldGdU8wd0FEMk55WldSbGJuUnBZV3h6Vkhsd1pYUUFURXh2Y21jdmEzVmhiR2t2Y21salpTOWpiM0psTDNObFkzVnlhWFI1TDJOeVpXUmxiblJwWVd4ekwwTnlaV1JsYm5ScFlXeHpVMjkxY21ObEpFTnlaV1JsYm5ScFlXeHpWSGx3WlR0TUFCQnNiMk5oYkZObGNuWnBZMlZPWVcxbGRBQVNUR3BoZG1FdmJHRnVaeTlUZEhKcGJtYzdUQUFYYldWemMyRm5aVVY0WTJWd2RHbHZia2hoYm1Sc1pYSnhBSDRBQlV3QURHMXBiR3hwYzFSdlRHbDJaWFFBRUV4cVlYWmhMMnhoYm1jdlRHOXVaenRNQUFod2NtbHZjbWwwZVhRQUUweHFZWFpoTDJ4aGJtY3ZTVzUwWldkbGNqdE1BQVZ4ZFdWMVpYRUFmZ0FEVEFBTmNtVjBjbmxCZEhSbGJYQjBjM0VBZmdBSFRBQUhjMlZ5ZG1salpYUUFFa3hxWVhaaEwyeGhibWN2VDJKcVpXTjBPMHdBRDNObGNuWnBZMlZGYm1SUWIybHVkSFFBRGt4cVlYWmhMMjVsZEM5VlVrdzdUQUFUYzJWeWRtbGpaVTVoYldWVGNHRmpaVlZTU1hFQWZnQUZUQUFRYzJWeWRtbGpaVTVoYldWemNHRmpaWEVBZmdBRlRBQUxjMlZ5ZG1salpWQmhkR2h4QUg0QUJYaHdjM0lBRVdwaGRtRXViR0Z1Wnk1Q2IyOXNaV0Z1elNCeWdOV2MrdTRDQUFGYUFBVjJZV3gxWlhod0FYQjBBQnBQVTBOaFkyaGxUbTkwYVdacFkyRjBhVzl1VTJWeWRtbGpaWFFBVFc5eVp5NXJkV0ZzYVM1eWFXTmxMbXR6WWk1dFpYTnpZV2RwYm1jdVpYaGpaWEIwYVc5dWFHRnVaR3hwYm1jdVJHVm1ZWFZzZEUxbGMzTmhaMlZGZUdObGNIUnBiMjVJWVc1a2JHVnljM0lBRG1waGRtRXViR0Z1Wnk1TWIyNW5PNHZra015UEk5OENBQUZLQUFWMllXeDFaWGh5QUJCcVlYWmhMbXhoYm1jdVRuVnRZbVZ5aHF5VkhRdVU0SXNDQUFCNGNQLy8vLy8vLy8vL2MzSUFFV3BoZG1FdWJHRnVaeTVKYm5SbFoyVnlFdUtncFBlQmh6Z0NBQUZKQUFWMllXeDFaWGh4QUg0QUVBQUFBQU56Y1FCK0FBc0FjUUIrQUJOd2MzSUFER3BoZG1FdWJtVjBMbFZTVEpZbE56WWEvT1J5QXdBSFNRQUlhR0Z6YUVOdlpHVkpBQVJ3YjNKMFRBQUpZWFYwYUc5eWFYUjVjUUIrQUFWTUFBUm1hV3hsY1FCK0FBVk1BQVJvYjNOMGNRQitBQVZNQUFod2NtOTBiMk52YkhFQWZnQUZUQUFEY21WbWNRQitBQVY0Y1AvLy8vOEFBQitRZEFBT2JHOWpZV3hvYjNOME9qZ3dPREIwQUNzdmEzSXRaR1YyTDNKbGJXOTBhVzVuTDA5VFEyRmphR1ZPYjNScFptbGpZWFJwYjI1VFpYSjJhV05sZEFBSmJHOWpZV3hvYjNOMGRBQUVhSFIwY0hCNGRBQUFkQUFHVkZKQlZrVk1kQUFCTDNOeUFCTnFZWFpoTG5WMGFXd3VRWEp5WVhsTWFYTjBlSUhTSFpuSFlaMERBQUZKQUFSemFYcGxlSEFBQUFBRmR3UUFBQUFLZEFB';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 367    FOR UPDATE;        
    buffer := 'emIzSm5MbXQxWVd4cExuSnBZMlV1YTNOaUxtMWxjM05oWjJsdVp5NXpaWEoyYVdObExrdFRRa3BoZG1GVFpYSjJhV05sZEFBOFkyOXRMbTl3Wlc1emVXMXdhRzl1ZVM1dmMyTmhZMmhsTG1KaGMyVXVaWFpsYm5SekxrTmhZMmhsUlc1MGNubEZkbVZ1ZEV4cGMzUmxibVZ5ZEFBM1kyOXRMbTl3Wlc1emVXMXdhRzl1ZVM1dmMyTmhZMmhsTG1KaGMyVXVaWFpsYm5SekxrTmhZMmhsUlhabGJuUk1hWE4wWlc1bGNuUUFGMnBoZG1FdWRYUnBiQzVGZG1WdWRFeHBjM1JsYm1WeWRBQXNZMjl0TG05d1pXNXplVzF3YUc5dWVTNXZjMk5oWTJobExtSmhjMlV1VEdsbVpXTjVZMnhsUVhkaGNtVjR0AA0xMjkuMTg2Ljc5LjQ1c3IAMm9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuSmF2YVNlcnZpY2VEZWZpbml0aW9utucNX0E0bPECAAFMABFzZXJ2aWNlSW50ZXJmYWNlc3QAEExqYXZhL3V0aWwvTGlzdDt4cgAub3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5TZXJ2aWNlRGVmaW5pdGlvbgCbAk9Y3Sl+AgANTAALYnVzU2VjdXJpdHlxAH4AE0wAD2NyZWRlbnRpYWxzVHlwZXQATExvcmcva3VhbGkvcmljZS9jb3JlL3NlY3VyaXR5L2NyZWRlbnRpYWxzL0NyZWRlbnRpYWxzU291cmNlJENyZWRlbnRpYWxzVHlwZTtMABBsb2NhbFNlcnZpY2VOYW1lcQB+AARMABdtZXNzYWdlRXhjZXB0aW9uSGFuZGxlcnEAfgAETAAMbWlsbGlzVG9MaXZlcQB+ABVMAAhwcmlvcml0eXEAfgAUTAAFcXVldWVxAH4AE0wADXJldHJ5QXR0ZW1wdHNxAH4AFEwAB3NlcnZpY2V0ABJMamF2YS9sYW5nL09iamVjdDtMAA9zZXJ2aWNlRW5kUG9pbnR0AA5MamF2YS9uZXQvVVJMO0wAE3NlcnZpY2VOYW1lU3BhY2VVUklxAH4ABEwAEHNlcnZpY2VOYW1lc3BhY2VxAH4ABEwAC3NlcnZpY2VQYXRocQB+AAR4cHNxAH4AGQFwdAAaT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2V0AE1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLmV4Y2VwdGlvbmhhbmRsaW5nLkRlZmF1bHRNZXNzYWdlRXhjZXB0aW9uSGFuZGxlcnNxAH4AH///////////c3EAfgAcAAAAA3NxAH4AGQBxAH4AMnBzcgAMamF2YS5uZXQuVVJMliU3Nhr85HIDAAdJAAhoYXNoQ29kZUkABHBvcnRMAAlhdXRob3JpdHlxAH4ABEwABGZpbGVxAH4ABEwABGhvc3RxAH4ABEwACHByb3RvY29scQB+AARMAANyZWZxAH4ABHhwaRuioAAAH5B0AA5sb2NhbGhvc3Q6ODA4MHQAKy9rci1kZXYvcmVtb3RpbmcvT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2V0AAlsb2NhbGhvc3R0AARodHRwcHh0AAB0AAZUUkFWRUx0AAEvc3IAE2phdmEudXRpbC5BcnJheUxpc3R4gdIdmcdhnQMAAUkABHNpemV4cAAAAAV3BAAAAAp0ADNvcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLnNlcnZpY2UuS1NCSmF2YVNlcnZpY2V0ADxjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5ldmVudHMuQ2FjaGVFbnRyeUV2ZW50TGlzdGVuZXJ0ADdjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5ldmVudHMuQ2FjaGVFdmVudExpc3RlbmVydAAXamF2YS51dGlsLkV2ZW50TGlzdGVuZXJ0ACxjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5MaWZlY3ljbGVBd2FyZXhxAH4AI3QABlRSQVZFTA==';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
INSERT INTO KRSB_MSG_PYLD_T (MSG_PYLD,MSG_QUE_ID)
  VALUES (EMPTY_CLOB(),368)
/
-- Length: 6060
-- Chunks: 2
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 368    FOR UPDATE;        
    buffer := 'rO0ABXNyAC1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLkFzeW5jaHJvbm91c0NhbGzxnQ5Y0t0lZQIAB1oAFWlnbm9yZVN0b3JlQW5kRm9yd2FyZFsACWFyZ3VtZW50c3QAE1tMamF2YS9sYW5nL09iamVjdDtMAAhjYWxsYmFja3QAM0xvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL0FzeW5jaHJvbm91c0NhbGxiYWNrO0wAB2NvbnRleHR0ABZMamF2YS9pby9TZXJpYWxpemFibGU7TAAKbWV0aG9kTmFtZXQAEkxqYXZhL2xhbmcvU3RyaW5nO1sACnBhcmFtVHlwZXN0ABJbTGphdmEvbGFuZy9DbGFzcztMAAtzZXJ2aWNlSW5mb3QAKkxvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL1NlcnZpY2VJbmZvO3hwAHVyABNbTGphdmEubGFuZy5PYmplY3Q7kM5YnxBzKWwCAAB4cAAAAAFzcgBDY29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLnBsdWdpbnMuY2x1c3RlcnN1cHBvcnQuQ2x1c3Rlck5vdGlmaWNhdGlvbhY+6VckvG9pAgACSQAEdHlwZUwABGRhdGFxAH4AA3hwAAAAAnQADkRvY3VtZW50VHlwZUlkcHB0AAZpbnZva2V1cgASW0xqYXZhLmxhbmcuQ2xhc3M7qxbXrsvNWpkCAAB4cAAAAAF2cgAUamF2YS5pby5TZXJpYWxpemFibGUQm2EN15tk7QIAAHhwc3IAKG9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuU2VydmljZUluZm/FFyO2KQS3ugIAC0wABWFsaXZldAATTGphdmEvbGFuZy9Cb29sZWFuO0wAFGVuZHBvaW50QWx0ZXJuYXRlVXJscQB+AARMAAtlbmRwb2ludFVybHEAfgAETAAKbG9ja1Zlck5icnQAE0xqYXZhL2xhbmcvSW50ZWdlcjtMAA5tZXNzYWdlRW50cnlJZHQAEExqYXZhL2xhbmcvTG9uZztMAAVxbmFtZXQAG0xqYXZheC94bWwvbmFtZXNwYWNlL1FOYW1lO0wAGnNlcmlhbGl6ZWRTZXJ2aWNlTmFtZXNwYWNlcQB+AARMAAhzZXJ2ZXJJcHEAfgAETAARc2VydmljZURlZmluaXRpb250ADBMb3JnL2t1YWxpL3JpY2Uva3NiL21lc3NhZ2luZy9TZXJ2aWNlRGVmaW5pdGlvbjtMAAtzZXJ2aWNlTmFtZXEAfgAETAAQc2VydmljZU5hbWVzcGFjZXEAfgAEeHBzcgARamF2YS5sYW5nLkJvb2xlYW7NIHKA1Zz67gIAAVoABXZhbHVleHABcHQAQGh0dHA6Ly9sb2NhbGhvc3Q6ODA4MC9rci1kZXYvcmVtb3RpbmcvT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2VzcgARamF2YS5sYW5nLkludGVnZXIS4qCk94GHOAIAAUkABXZhbHVleHIAEGphdmEubGFuZy5OdW1iZXKGrJUdC5TgiwIAAHhwAAAAAXNyAA5qYXZhLmxhbmcuTG9uZzuL5JDMjyPfAgABSgAFdmFsdWV4cQB+AB0AAAAAAAAPfXNyABlqYXZheC54bWwubmFtZXNwYWNlLlFOYW1lgW2oLfw73WwCAANMAAlsb2NhbFBhcnRxAH4ABEwADG5hbWVzcGFjZVVSSXEAfgAETAAGcHJlZml4cQB+AAR4cHQAGk9TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNldAAAcQB+ACR0B3RyTzBBQlhOeUFESnZjbWN1YTNWaGJHa3VjbWxqWlM1cmMySXViV1Z6YzJGbmFXNW5Ma3BoZG1GVFpYSjJhV05sUkdWbWFXNXBkR2x2YnJibkRWOUJOR3p4QWdBQlRBQVJjMlZ5ZG1salpVbHVkR1Z5Wm1GalpYTjBBQkJNYW1GMllTOTFkR2xzTDB4cGMzUTdlSElBTG05eVp5NXJkV0ZzYVM1eWFXTmxMbXR6WWk1dFpYTnpZV2RwYm1jdVUyVnlkbWxqWlVSbFptbHVhWFJwYjI0QW13SlBXTjBwZmdJQURVd0FDMkoxYzFObFkzVnlhWFI1ZEFBVFRHcGhkbUV2YkdGdVp5OUNiMjlzWldGdU8wd0FEMk55WldSbGJuUnBZV3h6Vkhsd1pYUUFURXh2Y21jdmEzVmhiR2t2Y21salpTOWpiM0psTDNObFkzVnlhWFI1TDJOeVpXUmxiblJwWVd4ekwwTnlaV1JsYm5ScFlXeHpVMjkxY21ObEpFTnlaV1JsYm5ScFlXeHpWSGx3WlR0TUFCQnNiMk5oYkZObGNuWnBZMlZPWVcxbGRBQVNUR3BoZG1FdmJHRnVaeTlUZEhKcGJtYzdUQUFYYldWemMyRm5aVVY0WTJWd2RHbHZia2hoYm1Sc1pYSnhBSDRBQlV3QURHMXBiR3hwYzFSdlRHbDJaWFFBRUV4cVlYWmhMMnhoYm1jdlRHOXVaenRNQUFod2NtbHZjbWwwZVhRQUUweHFZWFpoTDJ4aGJtY3ZTVzUwWldkbGNqdE1BQVZ4ZFdWMVpYRUFmZ0FEVEFBTmNtVjBjbmxCZEhSbGJYQjBjM0VBZmdBSFRBQUhjMlZ5ZG1salpYUUFFa3hxWVhaaEwyeGhibWN2VDJKcVpXTjBPMHdBRDNObGNuWnBZMlZGYm1SUWIybHVkSFFBRGt4cVlYWmhMMjVsZEM5VlVrdzdUQUFUYzJWeWRtbGpaVTVoYldWVGNHRmpaVlZTU1hFQWZnQUZUQUFRYzJWeWRtbGpaVTVoYldWemNHRmpaWEVBZmdBRlRBQUxjMlZ5ZG1salpWQmhkR2h4QUg0QUJYaHdjM0lBRVdwaGRtRXViR0Z1Wnk1Q2IyOXNaV0Z1elNCeWdOV2MrdTRDQUFGYUFBVjJZV3gxWlhod0FYQjBBQnBQVTBOaFkyaGxUbTkwYVdacFkyRjBhVzl1VTJWeWRtbGpaWFFBVFc5eVp5NXJkV0ZzYVM1eWFXTmxMbXR6WWk1dFpYTnpZV2RwYm1jdVpYaGpaWEIwYVc5dWFHRnVaR3hwYm1jdVJHVm1ZWFZzZEUxbGMzTmhaMlZGZUdObGNIUnBiMjVJWVc1a2JHVnljM0lBRG1waGRtRXViR0Z1Wnk1TWIyNW5PNHZra015UEk5OENBQUZLQUFWMllXeDFaWGh5QUJCcVlYWmhMbXhoYm1jdVRuVnRZbVZ5aHF5VkhRdVU0SXNDQUFCNGNQLy8vLy8vLy8vL2MzSUFFV3BoZG1FdWJHRnVaeTVKYm5SbFoyVnlFdUtncFBlQmh6Z0NBQUZKQUFWMllXeDFaWGh4QUg0QUVBQUFBQU56Y1FCK0FBc0FjUUIrQUJOd2MzSUFER3BoZG1FdWJtVjBMbFZTVEpZbE56WWEvT1J5QXdBSFNRQUlhR0Z6YUVOdlpHVkpBQVJ3YjNKMFRBQUpZWFYwYUc5eWFYUjVjUUIrQUFWTUFBUm1hV3hsY1FCK0FBVk1BQVJvYjNOMGNRQitBQVZNQUFod2NtOTBiMk52YkhFQWZnQUZUQUFEY21WbWNRQitBQVY0Y1AvLy8vOEFBQitRZEFBT2JHOWpZV3hvYjNOME9qZ3dPREIwQUNzdmEzSXRaR1YyTDNKbGJXOTBhVzVuTDA5VFEyRmphR1ZPYjNScFptbGpZWFJwYjI1VFpYSjJhV05sZEFBSmJHOWpZV3hvYjNOMGRBQUVhSFIwY0hCNGRBQUFkQUFHVkZKQlZrVk1kQUFCTDNOeUFCTnFZWFpoTG5WMGFXd3VRWEp5WVhsTWFYTjBlSUhTSFpuSFlaMERBQUZKQUFSemFYcGxlSEFBQUFBRmR3UUFBQUFLZEFBemIzSm5MbXQxWVd4cExuSnBZMlV1YTNOaUxtMWxjM05oWjJsdVp5';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 368    FOR UPDATE;        
    buffer := 'NXpaWEoyYVdObExrdFRRa3BoZG1GVFpYSjJhV05sZEFBOFkyOXRMbTl3Wlc1emVXMXdhRzl1ZVM1dmMyTmhZMmhsTG1KaGMyVXVaWFpsYm5SekxrTmhZMmhsUlc1MGNubEZkbVZ1ZEV4cGMzUmxibVZ5ZEFBM1kyOXRMbTl3Wlc1emVXMXdhRzl1ZVM1dmMyTmhZMmhsTG1KaGMyVXVaWFpsYm5SekxrTmhZMmhsUlhabGJuUk1hWE4wWlc1bGNuUUFGMnBoZG1FdWRYUnBiQzVGZG1WdWRFeHBjM1JsYm1WeWRBQXNZMjl0TG05d1pXNXplVzF3YUc5dWVTNXZjMk5oWTJobExtSmhjMlV1VEdsbVpXTjVZMnhsUVhkaGNtVjR0AA0xMjkuMTg2Ljc5LjQ1c3IAMm9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuSmF2YVNlcnZpY2VEZWZpbml0aW9utucNX0E0bPECAAFMABFzZXJ2aWNlSW50ZXJmYWNlc3QAEExqYXZhL3V0aWwvTGlzdDt4cgAub3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5TZXJ2aWNlRGVmaW5pdGlvbgCbAk9Y3Sl+AgANTAALYnVzU2VjdXJpdHlxAH4AE0wAD2NyZWRlbnRpYWxzVHlwZXQATExvcmcva3VhbGkvcmljZS9jb3JlL3NlY3VyaXR5L2NyZWRlbnRpYWxzL0NyZWRlbnRpYWxzU291cmNlJENyZWRlbnRpYWxzVHlwZTtMABBsb2NhbFNlcnZpY2VOYW1lcQB+AARMABdtZXNzYWdlRXhjZXB0aW9uSGFuZGxlcnEAfgAETAAMbWlsbGlzVG9MaXZlcQB+ABVMAAhwcmlvcml0eXEAfgAUTAAFcXVldWVxAH4AE0wADXJldHJ5QXR0ZW1wdHNxAH4AFEwAB3NlcnZpY2V0ABJMamF2YS9sYW5nL09iamVjdDtMAA9zZXJ2aWNlRW5kUG9pbnR0AA5MamF2YS9uZXQvVVJMO0wAE3NlcnZpY2VOYW1lU3BhY2VVUklxAH4ABEwAEHNlcnZpY2VOYW1lc3BhY2VxAH4ABEwAC3NlcnZpY2VQYXRocQB+AAR4cHNxAH4AGQFwdAAaT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2V0AE1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLmV4Y2VwdGlvbmhhbmRsaW5nLkRlZmF1bHRNZXNzYWdlRXhjZXB0aW9uSGFuZGxlcnNxAH4AH///////////c3EAfgAcAAAAA3NxAH4AGQBxAH4AMnBzcgAMamF2YS5uZXQuVVJMliU3Nhr85HIDAAdJAAhoYXNoQ29kZUkABHBvcnRMAAlhdXRob3JpdHlxAH4ABEwABGZpbGVxAH4ABEwABGhvc3RxAH4ABEwACHByb3RvY29scQB+AARMAANyZWZxAH4ABHhwaRuioAAAH5B0AA5sb2NhbGhvc3Q6ODA4MHQAKy9rci1kZXYvcmVtb3RpbmcvT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2V0AAlsb2NhbGhvc3R0AARodHRwcHh0AAB0AAZUUkFWRUx0AAEvc3IAE2phdmEudXRpbC5BcnJheUxpc3R4gdIdmcdhnQMAAUkABHNpemV4cAAAAAV3BAAAAAp0ADNvcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLnNlcnZpY2UuS1NCSmF2YVNlcnZpY2V0ADxjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5ldmVudHMuQ2FjaGVFbnRyeUV2ZW50TGlzdGVuZXJ0ADdjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5ldmVudHMuQ2FjaGVFdmVudExpc3RlbmVydAAXamF2YS51dGlsLkV2ZW50TGlzdGVuZXJ0ACxjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5MaWZlY3ljbGVBd2FyZXhxAH4AI3QABlRSQVZFTA==';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
INSERT INTO KRSB_MSG_PYLD_T (MSG_PYLD,MSG_QUE_ID)
  VALUES (EMPTY_CLOB(),369)
/
-- Length: 6060
-- Chunks: 2
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 369    FOR UPDATE;        
    buffer := 'rO0ABXNyAC1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLkFzeW5jaHJvbm91c0NhbGzxnQ5Y0t0lZQIAB1oAFWlnbm9yZVN0b3JlQW5kRm9yd2FyZFsACWFyZ3VtZW50c3QAE1tMamF2YS9sYW5nL09iamVjdDtMAAhjYWxsYmFja3QAM0xvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL0FzeW5jaHJvbm91c0NhbGxiYWNrO0wAB2NvbnRleHR0ABZMamF2YS9pby9TZXJpYWxpemFibGU7TAAKbWV0aG9kTmFtZXQAEkxqYXZhL2xhbmcvU3RyaW5nO1sACnBhcmFtVHlwZXN0ABJbTGphdmEvbGFuZy9DbGFzcztMAAtzZXJ2aWNlSW5mb3QAKkxvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL1NlcnZpY2VJbmZvO3hwAHVyABNbTGphdmEubGFuZy5PYmplY3Q7kM5YnxBzKWwCAAB4cAAAAAFzcgBDY29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLnBsdWdpbnMuY2x1c3RlcnN1cHBvcnQuQ2x1c3Rlck5vdGlmaWNhdGlvbhY+6VckvG9pAgACSQAEdHlwZUwABGRhdGFxAH4AA3hwAAAAAnQAEERvY3VtZW50VHlwZU5hbWVwcHQABmludm9rZXVyABJbTGphdmEubGFuZy5DbGFzczurFteuy81amQIAAHhwAAAAAXZyABRqYXZhLmlvLlNlcmlhbGl6YWJsZRCbYQ3Xm2TtAgAAeHBzcgAob3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5TZXJ2aWNlSW5mb8UXI7YpBLe6AgALTAAFYWxpdmV0ABNMamF2YS9sYW5nL0Jvb2xlYW47TAAUZW5kcG9pbnRBbHRlcm5hdGVVcmxxAH4ABEwAC2VuZHBvaW50VXJscQB+AARMAApsb2NrVmVyTmJydAATTGphdmEvbGFuZy9JbnRlZ2VyO0wADm1lc3NhZ2VFbnRyeUlkdAAQTGphdmEvbGFuZy9Mb25nO0wABXFuYW1ldAAbTGphdmF4L3htbC9uYW1lc3BhY2UvUU5hbWU7TAAac2VyaWFsaXplZFNlcnZpY2VOYW1lc3BhY2VxAH4ABEwACHNlcnZlcklwcQB+AARMABFzZXJ2aWNlRGVmaW5pdGlvbnQAMExvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL1NlcnZpY2VEZWZpbml0aW9uO0wAC3NlcnZpY2VOYW1lcQB+AARMABBzZXJ2aWNlTmFtZXNwYWNlcQB+AAR4cHNyABFqYXZhLmxhbmcuQm9vbGVhbs0gcoDVnPruAgABWgAFdmFsdWV4cAFwdABAaHR0cDovL2xvY2FsaG9zdDo4MDgwL2tyLWRldi9yZW1vdGluZy9PU0NhY2hlTm90aWZpY2F0aW9uU2VydmljZXNyABFqYXZhLmxhbmcuSW50ZWdlchLioKT3gYc4AgABSQAFdmFsdWV4cgAQamF2YS5sYW5nLk51bWJlcoaslR0LlOCLAgAAeHAAAAABc3IADmphdmEubGFuZy5Mb25nO4vkkMyPI98CAAFKAAV2YWx1ZXhxAH4AHQAAAAAAAA99c3IAGWphdmF4LnhtbC5uYW1lc3BhY2UuUU5hbWWBbagt/DvdbAIAA0wACWxvY2FsUGFydHEAfgAETAAMbmFtZXNwYWNlVVJJcQB+AARMAAZwcmVmaXhxAH4ABHhwdAAaT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2V0AABxAH4AJHQHdHJPMEFCWE55QURKdmNtY3VhM1ZoYkdrdWNtbGpaUzVyYzJJdWJXVnpjMkZuYVc1bkxrcGhkbUZUWlhKMmFXTmxSR1ZtYVc1cGRHbHZicmJuRFY5Qk5HenhBZ0FCVEFBUmMyVnlkbWxqWlVsdWRHVnlabUZqWlhOMEFCQk1hbUYyWVM5MWRHbHNMMHhwYzNRN2VISUFMbTl5Wnk1cmRXRnNhUzV5YVdObExtdHpZaTV0WlhOellXZHBibWN1VTJWeWRtbGpaVVJsWm1sdWFYUnBiMjRBbXdKUFdOMHBmZ0lBRFV3QUMySjFjMU5sWTNWeWFYUjVkQUFUVEdwaGRtRXZiR0Z1Wnk5Q2IyOXNaV0Z1TzB3QUQyTnlaV1JsYm5ScFlXeHpWSGx3WlhRQVRFeHZjbWN2YTNWaGJHa3ZjbWxqWlM5amIzSmxMM05sWTNWeWFYUjVMMk55WldSbGJuUnBZV3h6TDBOeVpXUmxiblJwWVd4elUyOTFjbU5sSkVOeVpXUmxiblJwWVd4elZIbHdaVHRNQUJCc2IyTmhiRk5sY25acFkyVk9ZVzFsZEFBU1RHcGhkbUV2YkdGdVp5OVRkSEpwYm1jN1RBQVhiV1Z6YzJGblpVVjRZMlZ3ZEdsdmJraGhibVJzWlhKeEFINEFCVXdBREcxcGJHeHBjMVJ2VEdsMlpYUUFFRXhxWVhaaEwyeGhibWN2VEc5dVp6dE1BQWh3Y21sdmNtbDBlWFFBRTB4cVlYWmhMMnhoYm1jdlNXNTBaV2RsY2p0TUFBVnhkV1YxWlhFQWZnQURUQUFOY21WMGNubEJkSFJsYlhCMGMzRUFmZ0FIVEFBSGMyVnlkbWxqWlhRQUVreHFZWFpoTDJ4aGJtY3ZUMkpxWldOME8wd0FEM05sY25acFkyVkZibVJRYjJsdWRIUUFEa3hxWVhaaEwyNWxkQzlWVWt3N1RBQVRjMlZ5ZG1salpVNWhiV1ZUY0dGalpWVlNTWEVBZmdBRlRBQVFjMlZ5ZG1salpVNWhiV1Z6Y0dGalpYRUFmZ0FGVEFBTGMyVnlkbWxqWlZCaGRHaHhBSDRBQlhod2MzSUFFV3BoZG1FdWJHRnVaeTVDYjI5c1pXRnV6U0J5Z05XYyt1NENBQUZhQUFWMllXeDFaWGh3QVhCMEFCcFBVME5oWTJobFRtOTBhV1pwWTJGMGFXOXVVMlZ5ZG1salpYUUFUVzl5Wnk1cmRXRnNhUzV5YVdObExtdHpZaTV0WlhOellXZHBibWN1WlhoalpYQjBhVzl1YUdGdVpHeHBibWN1UkdWbVlYVnNkRTFsYzNOaFoyVkZlR05sY0hScGIyNUlZVzVrYkdWeWMzSUFEbXBoZG1FdWJHRnVaeTVNYjI1bk80dmtrTXlQSTk4Q0FBRktBQVYyWVd4MVpYaHlBQkJxWVhaaExteGhibWN1VG5WdFltVnlocXlWSFF1VTRJc0NBQUI0Y1AvLy8vLy8vLy8vYzNJQUVXcGhkbUV1YkdGdVp5NUpiblJsWjJWeUV1S2dwUGVCaHpnQ0FBRkpBQVYyWVd4MVpYaHhBSDRBRUFBQUFBTnpjUUIrQUFzQWNRQitBQk53YzNJQURHcGhkbUV1Ym1WMExsVlNUSllsTnpZYS9PUnlBd0FIU1FBSWFHRnphRU52WkdWSkFBUndiM0owVEFBSllYVjBhRzl5YVhSNWNRQitBQVZNQUFSbWFXeGxjUUIrQUFWTUFBUm9iM04wY1FCK0FBVk1BQWh3Y205MGIyTnZiSEVBZmdBRlRBQURjbVZtY1FCK0FBVjRjUC8vLy84QUFCK1FkQUFPYkc5allXeG9iM04wT2pnd09EQjBBQ3N2YTNJdFpHVjJMM0psYlc5MGFXNW5MMDlUUTJGamFHVk9iM1JwWm1sallYUnBiMjVUWlhKMmFXTmxkQUFKYkc5allXeG9iM04wZEFBRWFIUjBjSEI0ZEFBQWRBQUdWRkpCVmtWTWRBQUJMM055QUJOcVlYWmhMblYwYVd3dVFYSnlZWGxNYVhOMGVJSFNIWm5IWVowREFBRkpBQVJ6YVhwbGVIQUFBQUFGZHdRQUFBQUtkQUF6YjNKbkxtdDFZV3hwTG5KcFkyVXVhM05pTG0xbGMzTmhaMmx1';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 369    FOR UPDATE;        
    buffer := 'Wnk1elpYSjJhV05sTGt0VFFrcGhkbUZUWlhKMmFXTmxkQUE4WTI5dExtOXdaVzV6ZVcxd2FHOXVlUzV2YzJOaFkyaGxMbUpoYzJVdVpYWmxiblJ6TGtOaFkyaGxSVzUwY25sRmRtVnVkRXhwYzNSbGJtVnlkQUEzWTI5dExtOXdaVzV6ZVcxd2FHOXVlUzV2YzJOaFkyaGxMbUpoYzJVdVpYWmxiblJ6TGtOaFkyaGxSWFpsYm5STWFYTjBaVzVsY25RQUYycGhkbUV1ZFhScGJDNUZkbVZ1ZEV4cGMzUmxibVZ5ZEFBc1kyOXRMbTl3Wlc1emVXMXdhRzl1ZVM1dmMyTmhZMmhsTG1KaGMyVXVUR2xtWldONVkyeGxRWGRoY21WNHQADTEyOS4xODYuNzkuNDVzcgAyb3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5KYXZhU2VydmljZURlZmluaXRpb2625w1fQTRs8QIAAUwAEXNlcnZpY2VJbnRlcmZhY2VzdAAQTGphdmEvdXRpbC9MaXN0O3hyAC5vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLlNlcnZpY2VEZWZpbml0aW9uAJsCT1jdKX4CAA1MAAtidXNTZWN1cml0eXEAfgATTAAPY3JlZGVudGlhbHNUeXBldABMTG9yZy9rdWFsaS9yaWNlL2NvcmUvc2VjdXJpdHkvY3JlZGVudGlhbHMvQ3JlZGVudGlhbHNTb3VyY2UkQ3JlZGVudGlhbHNUeXBlO0wAEGxvY2FsU2VydmljZU5hbWVxAH4ABEwAF21lc3NhZ2VFeGNlcHRpb25IYW5kbGVycQB+AARMAAxtaWxsaXNUb0xpdmVxAH4AFUwACHByaW9yaXR5cQB+ABRMAAVxdWV1ZXEAfgATTAANcmV0cnlBdHRlbXB0c3EAfgAUTAAHc2VydmljZXQAEkxqYXZhL2xhbmcvT2JqZWN0O0wAD3NlcnZpY2VFbmRQb2ludHQADkxqYXZhL25ldC9VUkw7TAATc2VydmljZU5hbWVTcGFjZVVSSXEAfgAETAAQc2VydmljZU5hbWVzcGFjZXEAfgAETAALc2VydmljZVBhdGhxAH4ABHhwc3EAfgAZAXB0ABpPU0NhY2hlTm90aWZpY2F0aW9uU2VydmljZXQATW9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuZXhjZXB0aW9uaGFuZGxpbmcuRGVmYXVsdE1lc3NhZ2VFeGNlcHRpb25IYW5kbGVyc3EAfgAf//////////9zcQB+ABwAAAADc3EAfgAZAHEAfgAycHNyAAxqYXZhLm5ldC5VUkyWJTc2GvzkcgMAB0kACGhhc2hDb2RlSQAEcG9ydEwACWF1dGhvcml0eXEAfgAETAAEZmlsZXEAfgAETAAEaG9zdHEAfgAETAAIcHJvdG9jb2xxAH4ABEwAA3JlZnEAfgAEeHBpG6KgAAAfkHQADmxvY2FsaG9zdDo4MDgwdAArL2tyLWRldi9yZW1vdGluZy9PU0NhY2hlTm90aWZpY2F0aW9uU2VydmljZXQACWxvY2FsaG9zdHQABGh0dHBweHQAAHQABlRSQVZFTHQAAS9zcgATamF2YS51dGlsLkFycmF5TGlzdHiB0h2Zx2GdAwABSQAEc2l6ZXhwAAAABXcEAAAACnQAM29yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuc2VydmljZS5LU0JKYXZhU2VydmljZXQAPGNvbS5vcGVuc3ltcGhvbnkub3NjYWNoZS5iYXNlLmV2ZW50cy5DYWNoZUVudHJ5RXZlbnRMaXN0ZW5lcnQAN2NvbS5vcGVuc3ltcGhvbnkub3NjYWNoZS5iYXNlLmV2ZW50cy5DYWNoZUV2ZW50TGlzdGVuZXJ0ABdqYXZhLnV0aWwuRXZlbnRMaXN0ZW5lcnQALGNvbS5vcGVuc3ltcGhvbnkub3NjYWNoZS5iYXNlLkxpZmVjeWNsZUF3YXJleHEAfgAjdAAGVFJBVkVM';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
INSERT INTO KRSB_MSG_PYLD_T (MSG_PYLD,MSG_QUE_ID)
  VALUES (EMPTY_CLOB(),370)
/
-- Length: 6084
-- Chunks: 2
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 370    FOR UPDATE;        
    buffer := 'rO0ABXNyAC1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLkFzeW5jaHJvbm91c0NhbGzxnQ5Y0t0lZQIAB1oAFWlnbm9yZVN0b3JlQW5kRm9yd2FyZFsACWFyZ3VtZW50c3QAE1tMamF2YS9sYW5nL09iamVjdDtMAAhjYWxsYmFja3QAM0xvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL0FzeW5jaHJvbm91c0NhbGxiYWNrO0wAB2NvbnRleHR0ABZMamF2YS9pby9TZXJpYWxpemFibGU7TAAKbWV0aG9kTmFtZXQAEkxqYXZhL2xhbmcvU3RyaW5nO1sACnBhcmFtVHlwZXN0ABJbTGphdmEvbGFuZy9DbGFzcztMAAtzZXJ2aWNlSW5mb3QAKkxvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL1NlcnZpY2VJbmZvO3hwAHVyABNbTGphdmEubGFuZy5PYmplY3Q7kM5YnxBzKWwCAAB4cAAAAAFzcgBDY29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLnBsdWdpbnMuY2x1c3RlcnN1cHBvcnQuQ2x1c3Rlck5vdGlmaWNhdGlvbhY+6VckvG9pAgACSQAEdHlwZUwABGRhdGFxAH4AA3hwAAAAAXQAIERvY3VtZW50VHlwZTpDdXJyZW50Um9vdHNJbkNhY2hlcHB0AAZpbnZva2V1cgASW0xqYXZhLmxhbmcuQ2xhc3M7qxbXrsvNWpkCAAB4cAAAAAF2cgAUamF2YS5pby5TZXJpYWxpemFibGUQm2EN15tk7QIAAHhwc3IAKG9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuU2VydmljZUluZm/FFyO2KQS3ugIAC0wABWFsaXZldAATTGphdmEvbGFuZy9Cb29sZWFuO0wAFGVuZHBvaW50QWx0ZXJuYXRlVXJscQB+AARMAAtlbmRwb2ludFVybHEAfgAETAAKbG9ja1Zlck5icnQAE0xqYXZhL2xhbmcvSW50ZWdlcjtMAA5tZXNzYWdlRW50cnlJZHQAEExqYXZhL2xhbmcvTG9uZztMAAVxbmFtZXQAG0xqYXZheC94bWwvbmFtZXNwYWNlL1FOYW1lO0wAGnNlcmlhbGl6ZWRTZXJ2aWNlTmFtZXNwYWNlcQB+AARMAAhzZXJ2ZXJJcHEAfgAETAARc2VydmljZURlZmluaXRpb250ADBMb3JnL2t1YWxpL3JpY2Uva3NiL21lc3NhZ2luZy9TZXJ2aWNlRGVmaW5pdGlvbjtMAAtzZXJ2aWNlTmFtZXEAfgAETAAQc2VydmljZU5hbWVzcGFjZXEAfgAEeHBzcgARamF2YS5sYW5nLkJvb2xlYW7NIHKA1Zz67gIAAVoABXZhbHVleHABcHQAQGh0dHA6Ly9sb2NhbGhvc3Q6ODA4MC9rci1kZXYvcmVtb3RpbmcvT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2VzcgARamF2YS5sYW5nLkludGVnZXIS4qCk94GHOAIAAUkABXZhbHVleHIAEGphdmEubGFuZy5OdW1iZXKGrJUdC5TgiwIAAHhwAAAAAXNyAA5qYXZhLmxhbmcuTG9uZzuL5JDMjyPfAgABSgAFdmFsdWV4cQB+AB0AAAAAAAAPfXNyABlqYXZheC54bWwubmFtZXNwYWNlLlFOYW1lgW2oLfw73WwCAANMAAlsb2NhbFBhcnRxAH4ABEwADG5hbWVzcGFjZVVSSXEAfgAETAAGcHJlZml4cQB+AAR4cHQAGk9TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNldAAAcQB+ACR0B3RyTzBBQlhOeUFESnZjbWN1YTNWaGJHa3VjbWxqWlM1cmMySXViV1Z6YzJGbmFXNW5Ma3BoZG1GVFpYSjJhV05sUkdWbWFXNXBkR2x2YnJibkRWOUJOR3p4QWdBQlRBQVJjMlZ5ZG1salpVbHVkR1Z5Wm1GalpYTjBBQkJNYW1GMllTOTFkR2xzTDB4cGMzUTdlSElBTG05eVp5NXJkV0ZzYVM1eWFXTmxMbXR6WWk1dFpYTnpZV2RwYm1jdVUyVnlkbWxqWlVSbFptbHVhWFJwYjI0QW13SlBXTjBwZmdJQURVd0FDMkoxYzFObFkzVnlhWFI1ZEFBVFRHcGhkbUV2YkdGdVp5OUNiMjlzWldGdU8wd0FEMk55WldSbGJuUnBZV3h6Vkhsd1pYUUFURXh2Y21jdmEzVmhiR2t2Y21salpTOWpiM0psTDNObFkzVnlhWFI1TDJOeVpXUmxiblJwWVd4ekwwTnlaV1JsYm5ScFlXeHpVMjkxY21ObEpFTnlaV1JsYm5ScFlXeHpWSGx3WlR0TUFCQnNiMk5oYkZObGNuWnBZMlZPWVcxbGRBQVNUR3BoZG1FdmJHRnVaeTlUZEhKcGJtYzdUQUFYYldWemMyRm5aVVY0WTJWd2RHbHZia2hoYm1Sc1pYSnhBSDRBQlV3QURHMXBiR3hwYzFSdlRHbDJaWFFBRUV4cVlYWmhMMnhoYm1jdlRHOXVaenRNQUFod2NtbHZjbWwwZVhRQUUweHFZWFpoTDJ4aGJtY3ZTVzUwWldkbGNqdE1BQVZ4ZFdWMVpYRUFmZ0FEVEFBTmNtVjBjbmxCZEhSbGJYQjBjM0VBZmdBSFRBQUhjMlZ5ZG1salpYUUFFa3hxWVhaaEwyeGhibWN2VDJKcVpXTjBPMHdBRDNObGNuWnBZMlZGYm1SUWIybHVkSFFBRGt4cVlYWmhMMjVsZEM5VlVrdzdUQUFUYzJWeWRtbGpaVTVoYldWVGNHRmpaVlZTU1hFQWZnQUZUQUFRYzJWeWRtbGpaVTVoYldWemNHRmpaWEVBZmdBRlRBQUxjMlZ5ZG1salpWQmhkR2h4QUg0QUJYaHdjM0lBRVdwaGRtRXViR0Z1Wnk1Q2IyOXNaV0Z1elNCeWdOV2MrdTRDQUFGYUFBVjJZV3gxWlhod0FYQjBBQnBQVTBOaFkyaGxUbTkwYVdacFkyRjBhVzl1VTJWeWRtbGpaWFFBVFc5eVp5NXJkV0ZzYVM1eWFXTmxMbXR6WWk1dFpYTnpZV2RwYm1jdVpYaGpaWEIwYVc5dWFHRnVaR3hwYm1jdVJHVm1ZWFZzZEUxbGMzTmhaMlZGZUdObGNIUnBiMjVJWVc1a2JHVnljM0lBRG1waGRtRXViR0Z1Wnk1TWIyNW5PNHZra015UEk5OENBQUZLQUFWMllXeDFaWGh5QUJCcVlYWmhMbXhoYm1jdVRuVnRZbVZ5aHF5VkhRdVU0SXNDQUFCNGNQLy8vLy8vLy8vL2MzSUFFV3BoZG1FdWJHRnVaeTVKYm5SbFoyVnlFdUtncFBlQmh6Z0NBQUZKQUFWMllXeDFaWGh4QUg0QUVBQUFBQU56Y1FCK0FBc0FjUUIrQUJOd2MzSUFER3BoZG1FdWJtVjBMbFZTVEpZbE56WWEvT1J5QXdBSFNRQUlhR0Z6YUVOdlpHVkpBQVJ3YjNKMFRBQUpZWFYwYUc5eWFYUjVjUUIrQUFWTUFBUm1hV3hsY1FCK0FBVk1BQVJvYjNOMGNRQitBQVZNQUFod2NtOTBiMk52YkhFQWZnQUZUQUFEY21WbWNRQitBQVY0Y1AvLy8vOEFBQitRZEFBT2JHOWpZV3hvYjNOME9qZ3dPREIwQUNzdmEzSXRaR1YyTDNKbGJXOTBhVzVuTDA5VFEyRmphR1ZPYjNScFptbGpZWFJwYjI1VFpYSjJhV05sZEFBSmJHOWpZV3hvYjNOMGRBQUVhSFIwY0hCNGRBQUFkQUFHVkZKQlZrVk1kQUFCTDNOeUFCTnFZWFpoTG5WMGFXd3VRWEp5WVhsTWFYTjBlSUhTSFpuSFlaMERBQUZKQUFSemFYcGxlSEFBQUFBRmR3UUFBQUFLZEFBemIzSm5MbXQxWVd4cExuSnBZMlV1';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 370    FOR UPDATE;        
    buffer := 'YTNOaUxtMWxjM05oWjJsdVp5NXpaWEoyYVdObExrdFRRa3BoZG1GVFpYSjJhV05sZEFBOFkyOXRMbTl3Wlc1emVXMXdhRzl1ZVM1dmMyTmhZMmhsTG1KaGMyVXVaWFpsYm5SekxrTmhZMmhsUlc1MGNubEZkbVZ1ZEV4cGMzUmxibVZ5ZEFBM1kyOXRMbTl3Wlc1emVXMXdhRzl1ZVM1dmMyTmhZMmhsTG1KaGMyVXVaWFpsYm5SekxrTmhZMmhsUlhabGJuUk1hWE4wWlc1bGNuUUFGMnBoZG1FdWRYUnBiQzVGZG1WdWRFeHBjM1JsYm1WeWRBQXNZMjl0TG05d1pXNXplVzF3YUc5dWVTNXZjMk5oWTJobExtSmhjMlV1VEdsbVpXTjVZMnhsUVhkaGNtVjR0AA0xMjkuMTg2Ljc5LjQ1c3IAMm9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuSmF2YVNlcnZpY2VEZWZpbml0aW9utucNX0E0bPECAAFMABFzZXJ2aWNlSW50ZXJmYWNlc3QAEExqYXZhL3V0aWwvTGlzdDt4cgAub3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5TZXJ2aWNlRGVmaW5pdGlvbgCbAk9Y3Sl+AgANTAALYnVzU2VjdXJpdHlxAH4AE0wAD2NyZWRlbnRpYWxzVHlwZXQATExvcmcva3VhbGkvcmljZS9jb3JlL3NlY3VyaXR5L2NyZWRlbnRpYWxzL0NyZWRlbnRpYWxzU291cmNlJENyZWRlbnRpYWxzVHlwZTtMABBsb2NhbFNlcnZpY2VOYW1lcQB+AARMABdtZXNzYWdlRXhjZXB0aW9uSGFuZGxlcnEAfgAETAAMbWlsbGlzVG9MaXZlcQB+ABVMAAhwcmlvcml0eXEAfgAUTAAFcXVldWVxAH4AE0wADXJldHJ5QXR0ZW1wdHNxAH4AFEwAB3NlcnZpY2V0ABJMamF2YS9sYW5nL09iamVjdDtMAA9zZXJ2aWNlRW5kUG9pbnR0AA5MamF2YS9uZXQvVVJMO0wAE3NlcnZpY2VOYW1lU3BhY2VVUklxAH4ABEwAEHNlcnZpY2VOYW1lc3BhY2VxAH4ABEwAC3NlcnZpY2VQYXRocQB+AAR4cHNxAH4AGQFwdAAaT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2V0AE1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLmV4Y2VwdGlvbmhhbmRsaW5nLkRlZmF1bHRNZXNzYWdlRXhjZXB0aW9uSGFuZGxlcnNxAH4AH///////////c3EAfgAcAAAAA3NxAH4AGQBxAH4AMnBzcgAMamF2YS5uZXQuVVJMliU3Nhr85HIDAAdJAAhoYXNoQ29kZUkABHBvcnRMAAlhdXRob3JpdHlxAH4ABEwABGZpbGVxAH4ABEwABGhvc3RxAH4ABEwACHByb3RvY29scQB+AARMAANyZWZxAH4ABHhwaRuioAAAH5B0AA5sb2NhbGhvc3Q6ODA4MHQAKy9rci1kZXYvcmVtb3RpbmcvT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2V0AAlsb2NhbGhvc3R0AARodHRwcHh0AAB0AAZUUkFWRUx0AAEvc3IAE2phdmEudXRpbC5BcnJheUxpc3R4gdIdmcdhnQMAAUkABHNpemV4cAAAAAV3BAAAAAp0ADNvcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLnNlcnZpY2UuS1NCSmF2YVNlcnZpY2V0ADxjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5ldmVudHMuQ2FjaGVFbnRyeUV2ZW50TGlzdGVuZXJ0ADdjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5ldmVudHMuQ2FjaGVFdmVudExpc3RlbmVydAAXamF2YS51dGlsLkV2ZW50TGlzdGVuZXJ0ACxjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5MaWZlY3ljbGVBd2FyZXhxAH4AI3QABlRSQVZFTA==';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
INSERT INTO KRSB_MSG_PYLD_T (MSG_PYLD,MSG_QUE_ID)
  VALUES (EMPTY_CLOB(),371)
/
-- Length: 6060
-- Chunks: 2
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 371    FOR UPDATE;        
    buffer := 'rO0ABXNyAC1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLkFzeW5jaHJvbm91c0NhbGzxnQ5Y0t0lZQIAB1oAFWlnbm9yZVN0b3JlQW5kRm9yd2FyZFsACWFyZ3VtZW50c3QAE1tMamF2YS9sYW5nL09iamVjdDtMAAhjYWxsYmFja3QAM0xvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL0FzeW5jaHJvbm91c0NhbGxiYWNrO0wAB2NvbnRleHR0ABZMamF2YS9pby9TZXJpYWxpemFibGU7TAAKbWV0aG9kTmFtZXQAEkxqYXZhL2xhbmcvU3RyaW5nO1sACnBhcmFtVHlwZXN0ABJbTGphdmEvbGFuZy9DbGFzcztMAAtzZXJ2aWNlSW5mb3QAKkxvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL1NlcnZpY2VJbmZvO3hwAHVyABNbTGphdmEubGFuZy5PYmplY3Q7kM5YnxBzKWwCAAB4cAAAAAFzcgBDY29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLnBsdWdpbnMuY2x1c3RlcnN1cHBvcnQuQ2x1c3Rlck5vdGlmaWNhdGlvbhY+6VckvG9pAgACSQAEdHlwZUwABGRhdGFxAH4AA3hwAAAAAnQADkRvY3VtZW50VHlwZUlkcHB0AAZpbnZva2V1cgASW0xqYXZhLmxhbmcuQ2xhc3M7qxbXrsvNWpkCAAB4cAAAAAF2cgAUamF2YS5pby5TZXJpYWxpemFibGUQm2EN15tk7QIAAHhwc3IAKG9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuU2VydmljZUluZm/FFyO2KQS3ugIAC0wABWFsaXZldAATTGphdmEvbGFuZy9Cb29sZWFuO0wAFGVuZHBvaW50QWx0ZXJuYXRlVXJscQB+AARMAAtlbmRwb2ludFVybHEAfgAETAAKbG9ja1Zlck5icnQAE0xqYXZhL2xhbmcvSW50ZWdlcjtMAA5tZXNzYWdlRW50cnlJZHQAEExqYXZhL2xhbmcvTG9uZztMAAVxbmFtZXQAG0xqYXZheC94bWwvbmFtZXNwYWNlL1FOYW1lO0wAGnNlcmlhbGl6ZWRTZXJ2aWNlTmFtZXNwYWNlcQB+AARMAAhzZXJ2ZXJJcHEAfgAETAARc2VydmljZURlZmluaXRpb250ADBMb3JnL2t1YWxpL3JpY2Uva3NiL21lc3NhZ2luZy9TZXJ2aWNlRGVmaW5pdGlvbjtMAAtzZXJ2aWNlTmFtZXEAfgAETAAQc2VydmljZU5hbWVzcGFjZXEAfgAEeHBzcgARamF2YS5sYW5nLkJvb2xlYW7NIHKA1Zz67gIAAVoABXZhbHVleHABcHQAQGh0dHA6Ly9sb2NhbGhvc3Q6ODA4MC9rci1kZXYvcmVtb3RpbmcvT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2VzcgARamF2YS5sYW5nLkludGVnZXIS4qCk94GHOAIAAUkABXZhbHVleHIAEGphdmEubGFuZy5OdW1iZXKGrJUdC5TgiwIAAHhwAAAAAXNyAA5qYXZhLmxhbmcuTG9uZzuL5JDMjyPfAgABSgAFdmFsdWV4cQB+AB0AAAAAAAAPfXNyABlqYXZheC54bWwubmFtZXNwYWNlLlFOYW1lgW2oLfw73WwCAANMAAlsb2NhbFBhcnRxAH4ABEwADG5hbWVzcGFjZVVSSXEAfgAETAAGcHJlZml4cQB+AAR4cHQAGk9TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNldAAAcQB+ACR0B3RyTzBBQlhOeUFESnZjbWN1YTNWaGJHa3VjbWxqWlM1cmMySXViV1Z6YzJGbmFXNW5Ma3BoZG1GVFpYSjJhV05sUkdWbWFXNXBkR2x2YnJibkRWOUJOR3p4QWdBQlRBQVJjMlZ5ZG1salpVbHVkR1Z5Wm1GalpYTjBBQkJNYW1GMllTOTFkR2xzTDB4cGMzUTdlSElBTG05eVp5NXJkV0ZzYVM1eWFXTmxMbXR6WWk1dFpYTnpZV2RwYm1jdVUyVnlkbWxqWlVSbFptbHVhWFJwYjI0QW13SlBXTjBwZmdJQURVd0FDMkoxYzFObFkzVnlhWFI1ZEFBVFRHcGhkbUV2YkdGdVp5OUNiMjlzWldGdU8wd0FEMk55WldSbGJuUnBZV3h6Vkhsd1pYUUFURXh2Y21jdmEzVmhiR2t2Y21salpTOWpiM0psTDNObFkzVnlhWFI1TDJOeVpXUmxiblJwWVd4ekwwTnlaV1JsYm5ScFlXeHpVMjkxY21ObEpFTnlaV1JsYm5ScFlXeHpWSGx3WlR0TUFCQnNiMk5oYkZObGNuWnBZMlZPWVcxbGRBQVNUR3BoZG1FdmJHRnVaeTlUZEhKcGJtYzdUQUFYYldWemMyRm5aVVY0WTJWd2RHbHZia2hoYm1Sc1pYSnhBSDRBQlV3QURHMXBiR3hwYzFSdlRHbDJaWFFBRUV4cVlYWmhMMnhoYm1jdlRHOXVaenRNQUFod2NtbHZjbWwwZVhRQUUweHFZWFpoTDJ4aGJtY3ZTVzUwWldkbGNqdE1BQVZ4ZFdWMVpYRUFmZ0FEVEFBTmNtVjBjbmxCZEhSbGJYQjBjM0VBZmdBSFRBQUhjMlZ5ZG1salpYUUFFa3hxWVhaaEwyeGhibWN2VDJKcVpXTjBPMHdBRDNObGNuWnBZMlZGYm1SUWIybHVkSFFBRGt4cVlYWmhMMjVsZEM5VlVrdzdUQUFUYzJWeWRtbGpaVTVoYldWVGNHRmpaVlZTU1hFQWZnQUZUQUFRYzJWeWRtbGpaVTVoYldWemNHRmpaWEVBZmdBRlRBQUxjMlZ5ZG1salpWQmhkR2h4QUg0QUJYaHdjM0lBRVdwaGRtRXViR0Z1Wnk1Q2IyOXNaV0Z1elNCeWdOV2MrdTRDQUFGYUFBVjJZV3gxWlhod0FYQjBBQnBQVTBOaFkyaGxUbTkwYVdacFkyRjBhVzl1VTJWeWRtbGpaWFFBVFc5eVp5NXJkV0ZzYVM1eWFXTmxMbXR6WWk1dFpYTnpZV2RwYm1jdVpYaGpaWEIwYVc5dWFHRnVaR3hwYm1jdVJHVm1ZWFZzZEUxbGMzTmhaMlZGZUdObGNIUnBiMjVJWVc1a2JHVnljM0lBRG1waGRtRXViR0Z1Wnk1TWIyNW5PNHZra015UEk5OENBQUZLQUFWMllXeDFaWGh5QUJCcVlYWmhMbXhoYm1jdVRuVnRZbVZ5aHF5VkhRdVU0SXNDQUFCNGNQLy8vLy8vLy8vL2MzSUFFV3BoZG1FdWJHRnVaeTVKYm5SbFoyVnlFdUtncFBlQmh6Z0NBQUZKQUFWMllXeDFaWGh4QUg0QUVBQUFBQU56Y1FCK0FBc0FjUUIrQUJOd2MzSUFER3BoZG1FdWJtVjBMbFZTVEpZbE56WWEvT1J5QXdBSFNRQUlhR0Z6YUVOdlpHVkpBQVJ3YjNKMFRBQUpZWFYwYUc5eWFYUjVjUUIrQUFWTUFBUm1hV3hsY1FCK0FBVk1BQVJvYjNOMGNRQitBQVZNQUFod2NtOTBiMk52YkhFQWZnQUZUQUFEY21WbWNRQitBQVY0Y1AvLy8vOEFBQitRZEFBT2JHOWpZV3hvYjNOME9qZ3dPREIwQUNzdmEzSXRaR1YyTDNKbGJXOTBhVzVuTDA5VFEyRmphR1ZPYjNScFptbGpZWFJwYjI1VFpYSjJhV05sZEFBSmJHOWpZV3hvYjNOMGRBQUVhSFIwY0hCNGRBQUFkQUFHVkZKQlZrVk1kQUFCTDNOeUFCTnFZWFpoTG5WMGFXd3VRWEp5WVhsTWFYTjBlSUhTSFpuSFlaMERBQUZKQUFSemFYcGxlSEFBQUFBRmR3UUFBQUFLZEFBemIzSm5MbXQxWVd4cExuSnBZMlV1YTNOaUxtMWxjM05oWjJsdVp5';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 371    FOR UPDATE;        
    buffer := 'NXpaWEoyYVdObExrdFRRa3BoZG1GVFpYSjJhV05sZEFBOFkyOXRMbTl3Wlc1emVXMXdhRzl1ZVM1dmMyTmhZMmhsTG1KaGMyVXVaWFpsYm5SekxrTmhZMmhsUlc1MGNubEZkbVZ1ZEV4cGMzUmxibVZ5ZEFBM1kyOXRMbTl3Wlc1emVXMXdhRzl1ZVM1dmMyTmhZMmhsTG1KaGMyVXVaWFpsYm5SekxrTmhZMmhsUlhabGJuUk1hWE4wWlc1bGNuUUFGMnBoZG1FdWRYUnBiQzVGZG1WdWRFeHBjM1JsYm1WeWRBQXNZMjl0TG05d1pXNXplVzF3YUc5dWVTNXZjMk5oWTJobExtSmhjMlV1VEdsbVpXTjVZMnhsUVhkaGNtVjR0AA0xMjkuMTg2Ljc5LjQ1c3IAMm9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuSmF2YVNlcnZpY2VEZWZpbml0aW9utucNX0E0bPECAAFMABFzZXJ2aWNlSW50ZXJmYWNlc3QAEExqYXZhL3V0aWwvTGlzdDt4cgAub3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5TZXJ2aWNlRGVmaW5pdGlvbgCbAk9Y3Sl+AgANTAALYnVzU2VjdXJpdHlxAH4AE0wAD2NyZWRlbnRpYWxzVHlwZXQATExvcmcva3VhbGkvcmljZS9jb3JlL3NlY3VyaXR5L2NyZWRlbnRpYWxzL0NyZWRlbnRpYWxzU291cmNlJENyZWRlbnRpYWxzVHlwZTtMABBsb2NhbFNlcnZpY2VOYW1lcQB+AARMABdtZXNzYWdlRXhjZXB0aW9uSGFuZGxlcnEAfgAETAAMbWlsbGlzVG9MaXZlcQB+ABVMAAhwcmlvcml0eXEAfgAUTAAFcXVldWVxAH4AE0wADXJldHJ5QXR0ZW1wdHNxAH4AFEwAB3NlcnZpY2V0ABJMamF2YS9sYW5nL09iamVjdDtMAA9zZXJ2aWNlRW5kUG9pbnR0AA5MamF2YS9uZXQvVVJMO0wAE3NlcnZpY2VOYW1lU3BhY2VVUklxAH4ABEwAEHNlcnZpY2VOYW1lc3BhY2VxAH4ABEwAC3NlcnZpY2VQYXRocQB+AAR4cHNxAH4AGQFwdAAaT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2V0AE1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLmV4Y2VwdGlvbmhhbmRsaW5nLkRlZmF1bHRNZXNzYWdlRXhjZXB0aW9uSGFuZGxlcnNxAH4AH///////////c3EAfgAcAAAAA3NxAH4AGQBxAH4AMnBzcgAMamF2YS5uZXQuVVJMliU3Nhr85HIDAAdJAAhoYXNoQ29kZUkABHBvcnRMAAlhdXRob3JpdHlxAH4ABEwABGZpbGVxAH4ABEwABGhvc3RxAH4ABEwACHByb3RvY29scQB+AARMAANyZWZxAH4ABHhwaRuioAAAH5B0AA5sb2NhbGhvc3Q6ODA4MHQAKy9rci1kZXYvcmVtb3RpbmcvT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2V0AAlsb2NhbGhvc3R0AARodHRwcHh0AAB0AAZUUkFWRUx0AAEvc3IAE2phdmEudXRpbC5BcnJheUxpc3R4gdIdmcdhnQMAAUkABHNpemV4cAAAAAV3BAAAAAp0ADNvcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLnNlcnZpY2UuS1NCSmF2YVNlcnZpY2V0ADxjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5ldmVudHMuQ2FjaGVFbnRyeUV2ZW50TGlzdGVuZXJ0ADdjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5ldmVudHMuQ2FjaGVFdmVudExpc3RlbmVydAAXamF2YS51dGlsLkV2ZW50TGlzdGVuZXJ0ACxjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5MaWZlY3ljbGVBd2FyZXhxAH4AI3QABlRSQVZFTA==';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
INSERT INTO KRSB_MSG_PYLD_T (MSG_PYLD,MSG_QUE_ID)
  VALUES (EMPTY_CLOB(),372)
/
-- Length: 6060
-- Chunks: 2
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 372    FOR UPDATE;        
    buffer := 'rO0ABXNyAC1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLkFzeW5jaHJvbm91c0NhbGzxnQ5Y0t0lZQIAB1oAFWlnbm9yZVN0b3JlQW5kRm9yd2FyZFsACWFyZ3VtZW50c3QAE1tMamF2YS9sYW5nL09iamVjdDtMAAhjYWxsYmFja3QAM0xvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL0FzeW5jaHJvbm91c0NhbGxiYWNrO0wAB2NvbnRleHR0ABZMamF2YS9pby9TZXJpYWxpemFibGU7TAAKbWV0aG9kTmFtZXQAEkxqYXZhL2xhbmcvU3RyaW5nO1sACnBhcmFtVHlwZXN0ABJbTGphdmEvbGFuZy9DbGFzcztMAAtzZXJ2aWNlSW5mb3QAKkxvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL1NlcnZpY2VJbmZvO3hwAHVyABNbTGphdmEubGFuZy5PYmplY3Q7kM5YnxBzKWwCAAB4cAAAAAFzcgBDY29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLnBsdWdpbnMuY2x1c3RlcnN1cHBvcnQuQ2x1c3Rlck5vdGlmaWNhdGlvbhY+6VckvG9pAgACSQAEdHlwZUwABGRhdGFxAH4AA3hwAAAAAnQAEERvY3VtZW50VHlwZU5hbWVwcHQABmludm9rZXVyABJbTGphdmEubGFuZy5DbGFzczurFteuy81amQIAAHhwAAAAAXZyABRqYXZhLmlvLlNlcmlhbGl6YWJsZRCbYQ3Xm2TtAgAAeHBzcgAob3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5TZXJ2aWNlSW5mb8UXI7YpBLe6AgALTAAFYWxpdmV0ABNMamF2YS9sYW5nL0Jvb2xlYW47TAAUZW5kcG9pbnRBbHRlcm5hdGVVcmxxAH4ABEwAC2VuZHBvaW50VXJscQB+AARMAApsb2NrVmVyTmJydAATTGphdmEvbGFuZy9JbnRlZ2VyO0wADm1lc3NhZ2VFbnRyeUlkdAAQTGphdmEvbGFuZy9Mb25nO0wABXFuYW1ldAAbTGphdmF4L3htbC9uYW1lc3BhY2UvUU5hbWU7TAAac2VyaWFsaXplZFNlcnZpY2VOYW1lc3BhY2VxAH4ABEwACHNlcnZlcklwcQB+AARMABFzZXJ2aWNlRGVmaW5pdGlvbnQAMExvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL1NlcnZpY2VEZWZpbml0aW9uO0wAC3NlcnZpY2VOYW1lcQB+AARMABBzZXJ2aWNlTmFtZXNwYWNlcQB+AAR4cHNyABFqYXZhLmxhbmcuQm9vbGVhbs0gcoDVnPruAgABWgAFdmFsdWV4cAFwdABAaHR0cDovL2xvY2FsaG9zdDo4MDgwL2tyLWRldi9yZW1vdGluZy9PU0NhY2hlTm90aWZpY2F0aW9uU2VydmljZXNyABFqYXZhLmxhbmcuSW50ZWdlchLioKT3gYc4AgABSQAFdmFsdWV4cgAQamF2YS5sYW5nLk51bWJlcoaslR0LlOCLAgAAeHAAAAABc3IADmphdmEubGFuZy5Mb25nO4vkkMyPI98CAAFKAAV2YWx1ZXhxAH4AHQAAAAAAAA99c3IAGWphdmF4LnhtbC5uYW1lc3BhY2UuUU5hbWWBbagt/DvdbAIAA0wACWxvY2FsUGFydHEAfgAETAAMbmFtZXNwYWNlVVJJcQB+AARMAAZwcmVmaXhxAH4ABHhwdAAaT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2V0AABxAH4AJHQHdHJPMEFCWE55QURKdmNtY3VhM1ZoYkdrdWNtbGpaUzVyYzJJdWJXVnpjMkZuYVc1bkxrcGhkbUZUWlhKMmFXTmxSR1ZtYVc1cGRHbHZicmJuRFY5Qk5HenhBZ0FCVEFBUmMyVnlkbWxqWlVsdWRHVnlabUZqWlhOMEFCQk1hbUYyWVM5MWRHbHNMMHhwYzNRN2VISUFMbTl5Wnk1cmRXRnNhUzV5YVdObExtdHpZaTV0WlhOellXZHBibWN1VTJWeWRtbGpaVVJsWm1sdWFYUnBiMjRBbXdKUFdOMHBmZ0lBRFV3QUMySjFjMU5sWTNWeWFYUjVkQUFUVEdwaGRtRXZiR0Z1Wnk5Q2IyOXNaV0Z1TzB3QUQyTnlaV1JsYm5ScFlXeHpWSGx3WlhRQVRFeHZjbWN2YTNWaGJHa3ZjbWxqWlM5amIzSmxMM05sWTNWeWFYUjVMMk55WldSbGJuUnBZV3h6TDBOeVpXUmxiblJwWVd4elUyOTFjbU5sSkVOeVpXUmxiblJwWVd4elZIbHdaVHRNQUJCc2IyTmhiRk5sY25acFkyVk9ZVzFsZEFBU1RHcGhkbUV2YkdGdVp5OVRkSEpwYm1jN1RBQVhiV1Z6YzJGblpVVjRZMlZ3ZEdsdmJraGhibVJzWlhKeEFINEFCVXdBREcxcGJHeHBjMVJ2VEdsMlpYUUFFRXhxWVhaaEwyeGhibWN2VEc5dVp6dE1BQWh3Y21sdmNtbDBlWFFBRTB4cVlYWmhMMnhoYm1jdlNXNTBaV2RsY2p0TUFBVnhkV1YxWlhFQWZnQURUQUFOY21WMGNubEJkSFJsYlhCMGMzRUFmZ0FIVEFBSGMyVnlkbWxqWlhRQUVreHFZWFpoTDJ4aGJtY3ZUMkpxWldOME8wd0FEM05sY25acFkyVkZibVJRYjJsdWRIUUFEa3hxWVhaaEwyNWxkQzlWVWt3N1RBQVRjMlZ5ZG1salpVNWhiV1ZUY0dGalpWVlNTWEVBZmdBRlRBQVFjMlZ5ZG1salpVNWhiV1Z6Y0dGalpYRUFmZ0FGVEFBTGMyVnlkbWxqWlZCaGRHaHhBSDRBQlhod2MzSUFFV3BoZG1FdWJHRnVaeTVDYjI5c1pXRnV6U0J5Z05XYyt1NENBQUZhQUFWMllXeDFaWGh3QVhCMEFCcFBVME5oWTJobFRtOTBhV1pwWTJGMGFXOXVVMlZ5ZG1salpYUUFUVzl5Wnk1cmRXRnNhUzV5YVdObExtdHpZaTV0WlhOellXZHBibWN1WlhoalpYQjBhVzl1YUdGdVpHeHBibWN1UkdWbVlYVnNkRTFsYzNOaFoyVkZlR05sY0hScGIyNUlZVzVrYkdWeWMzSUFEbXBoZG1FdWJHRnVaeTVNYjI1bk80dmtrTXlQSTk4Q0FBRktBQVYyWVd4MVpYaHlBQkJxWVhaaExteGhibWN1VG5WdFltVnlocXlWSFF1VTRJc0NBQUI0Y1AvLy8vLy8vLy8vYzNJQUVXcGhkbUV1YkdGdVp5NUpiblJsWjJWeUV1S2dwUGVCaHpnQ0FBRkpBQVYyWVd4MVpYaHhBSDRBRUFBQUFBTnpjUUIrQUFzQWNRQitBQk53YzNJQURHcGhkbUV1Ym1WMExsVlNUSllsTnpZYS9PUnlBd0FIU1FBSWFHRnphRU52WkdWSkFBUndiM0owVEFBSllYVjBhRzl5YVhSNWNRQitBQVZNQUFSbWFXeGxjUUIrQUFWTUFBUm9iM04wY1FCK0FBVk1BQWh3Y205MGIyTnZiSEVBZmdBRlRBQURjbVZtY1FCK0FBVjRjUC8vLy84QUFCK1FkQUFPYkc5allXeG9iM04wT2pnd09EQjBBQ3N2YTNJdFpHVjJMM0psYlc5MGFXNW5MMDlUUTJGamFHVk9iM1JwWm1sallYUnBiMjVUWlhKMmFXTmxkQUFKYkc5allXeG9iM04wZEFBRWFIUjBjSEI0ZEFBQWRBQUdWRkpCVmtWTWRBQUJMM055QUJOcVlYWmhMblYwYVd3dVFYSnlZWGxNYVhOMGVJSFNIWm5IWVowREFBRkpBQVJ6YVhwbGVIQUFBQUFGZHdRQUFBQUtkQUF6YjNKbkxtdDFZV3hwTG5KcFkyVXVhM05pTG0xbGMzTmhaMmx1';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 372    FOR UPDATE;        
    buffer := 'Wnk1elpYSjJhV05sTGt0VFFrcGhkbUZUWlhKMmFXTmxkQUE4WTI5dExtOXdaVzV6ZVcxd2FHOXVlUzV2YzJOaFkyaGxMbUpoYzJVdVpYWmxiblJ6TGtOaFkyaGxSVzUwY25sRmRtVnVkRXhwYzNSbGJtVnlkQUEzWTI5dExtOXdaVzV6ZVcxd2FHOXVlUzV2YzJOaFkyaGxMbUpoYzJVdVpYWmxiblJ6TGtOaFkyaGxSWFpsYm5STWFYTjBaVzVsY25RQUYycGhkbUV1ZFhScGJDNUZkbVZ1ZEV4cGMzUmxibVZ5ZEFBc1kyOXRMbTl3Wlc1emVXMXdhRzl1ZVM1dmMyTmhZMmhsTG1KaGMyVXVUR2xtWldONVkyeGxRWGRoY21WNHQADTEyOS4xODYuNzkuNDVzcgAyb3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5KYXZhU2VydmljZURlZmluaXRpb2625w1fQTRs8QIAAUwAEXNlcnZpY2VJbnRlcmZhY2VzdAAQTGphdmEvdXRpbC9MaXN0O3hyAC5vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLlNlcnZpY2VEZWZpbml0aW9uAJsCT1jdKX4CAA1MAAtidXNTZWN1cml0eXEAfgATTAAPY3JlZGVudGlhbHNUeXBldABMTG9yZy9rdWFsaS9yaWNlL2NvcmUvc2VjdXJpdHkvY3JlZGVudGlhbHMvQ3JlZGVudGlhbHNTb3VyY2UkQ3JlZGVudGlhbHNUeXBlO0wAEGxvY2FsU2VydmljZU5hbWVxAH4ABEwAF21lc3NhZ2VFeGNlcHRpb25IYW5kbGVycQB+AARMAAxtaWxsaXNUb0xpdmVxAH4AFUwACHByaW9yaXR5cQB+ABRMAAVxdWV1ZXEAfgATTAANcmV0cnlBdHRlbXB0c3EAfgAUTAAHc2VydmljZXQAEkxqYXZhL2xhbmcvT2JqZWN0O0wAD3NlcnZpY2VFbmRQb2ludHQADkxqYXZhL25ldC9VUkw7TAATc2VydmljZU5hbWVTcGFjZVVSSXEAfgAETAAQc2VydmljZU5hbWVzcGFjZXEAfgAETAALc2VydmljZVBhdGhxAH4ABHhwc3EAfgAZAXB0ABpPU0NhY2hlTm90aWZpY2F0aW9uU2VydmljZXQATW9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuZXhjZXB0aW9uaGFuZGxpbmcuRGVmYXVsdE1lc3NhZ2VFeGNlcHRpb25IYW5kbGVyc3EAfgAf//////////9zcQB+ABwAAAADc3EAfgAZAHEAfgAycHNyAAxqYXZhLm5ldC5VUkyWJTc2GvzkcgMAB0kACGhhc2hDb2RlSQAEcG9ydEwACWF1dGhvcml0eXEAfgAETAAEZmlsZXEAfgAETAAEaG9zdHEAfgAETAAIcHJvdG9jb2xxAH4ABEwAA3JlZnEAfgAEeHBpG6KgAAAfkHQADmxvY2FsaG9zdDo4MDgwdAArL2tyLWRldi9yZW1vdGluZy9PU0NhY2hlTm90aWZpY2F0aW9uU2VydmljZXQACWxvY2FsaG9zdHQABGh0dHBweHQAAHQABlRSQVZFTHQAAS9zcgATamF2YS51dGlsLkFycmF5TGlzdHiB0h2Zx2GdAwABSQAEc2l6ZXhwAAAABXcEAAAACnQAM29yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuc2VydmljZS5LU0JKYXZhU2VydmljZXQAPGNvbS5vcGVuc3ltcGhvbnkub3NjYWNoZS5iYXNlLmV2ZW50cy5DYWNoZUVudHJ5RXZlbnRMaXN0ZW5lcnQAN2NvbS5vcGVuc3ltcGhvbnkub3NjYWNoZS5iYXNlLmV2ZW50cy5DYWNoZUV2ZW50TGlzdGVuZXJ0ABdqYXZhLnV0aWwuRXZlbnRMaXN0ZW5lcnQALGNvbS5vcGVuc3ltcGhvbnkub3NjYWNoZS5iYXNlLkxpZmVjeWNsZUF3YXJleHEAfgAjdAAGVFJBVkVM';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
INSERT INTO KRSB_MSG_PYLD_T (MSG_PYLD,MSG_QUE_ID)
  VALUES (EMPTY_CLOB(),373)
/
-- Length: 6084
-- Chunks: 2
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 373    FOR UPDATE;        
    buffer := 'rO0ABXNyAC1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLkFzeW5jaHJvbm91c0NhbGzxnQ5Y0t0lZQIAB1oAFWlnbm9yZVN0b3JlQW5kRm9yd2FyZFsACWFyZ3VtZW50c3QAE1tMamF2YS9sYW5nL09iamVjdDtMAAhjYWxsYmFja3QAM0xvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL0FzeW5jaHJvbm91c0NhbGxiYWNrO0wAB2NvbnRleHR0ABZMamF2YS9pby9TZXJpYWxpemFibGU7TAAKbWV0aG9kTmFtZXQAEkxqYXZhL2xhbmcvU3RyaW5nO1sACnBhcmFtVHlwZXN0ABJbTGphdmEvbGFuZy9DbGFzcztMAAtzZXJ2aWNlSW5mb3QAKkxvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL1NlcnZpY2VJbmZvO3hwAHVyABNbTGphdmEubGFuZy5PYmplY3Q7kM5YnxBzKWwCAAB4cAAAAAFzcgBDY29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLnBsdWdpbnMuY2x1c3RlcnN1cHBvcnQuQ2x1c3Rlck5vdGlmaWNhdGlvbhY+6VckvG9pAgACSQAEdHlwZUwABGRhdGFxAH4AA3hwAAAAAXQAIERvY3VtZW50VHlwZTpDdXJyZW50Um9vdHNJbkNhY2hlcHB0AAZpbnZva2V1cgASW0xqYXZhLmxhbmcuQ2xhc3M7qxbXrsvNWpkCAAB4cAAAAAF2cgAUamF2YS5pby5TZXJpYWxpemFibGUQm2EN15tk7QIAAHhwc3IAKG9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuU2VydmljZUluZm/FFyO2KQS3ugIAC0wABWFsaXZldAATTGphdmEvbGFuZy9Cb29sZWFuO0wAFGVuZHBvaW50QWx0ZXJuYXRlVXJscQB+AARMAAtlbmRwb2ludFVybHEAfgAETAAKbG9ja1Zlck5icnQAE0xqYXZhL2xhbmcvSW50ZWdlcjtMAA5tZXNzYWdlRW50cnlJZHQAEExqYXZhL2xhbmcvTG9uZztMAAVxbmFtZXQAG0xqYXZheC94bWwvbmFtZXNwYWNlL1FOYW1lO0wAGnNlcmlhbGl6ZWRTZXJ2aWNlTmFtZXNwYWNlcQB+AARMAAhzZXJ2ZXJJcHEAfgAETAARc2VydmljZURlZmluaXRpb250ADBMb3JnL2t1YWxpL3JpY2Uva3NiL21lc3NhZ2luZy9TZXJ2aWNlRGVmaW5pdGlvbjtMAAtzZXJ2aWNlTmFtZXEAfgAETAAQc2VydmljZU5hbWVzcGFjZXEAfgAEeHBzcgARamF2YS5sYW5nLkJvb2xlYW7NIHKA1Zz67gIAAVoABXZhbHVleHABcHQAQGh0dHA6Ly9sb2NhbGhvc3Q6ODA4MC9rci1kZXYvcmVtb3RpbmcvT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2VzcgARamF2YS5sYW5nLkludGVnZXIS4qCk94GHOAIAAUkABXZhbHVleHIAEGphdmEubGFuZy5OdW1iZXKGrJUdC5TgiwIAAHhwAAAAAXNyAA5qYXZhLmxhbmcuTG9uZzuL5JDMjyPfAgABSgAFdmFsdWV4cQB+AB0AAAAAAAAPfXNyABlqYXZheC54bWwubmFtZXNwYWNlLlFOYW1lgW2oLfw73WwCAANMAAlsb2NhbFBhcnRxAH4ABEwADG5hbWVzcGFjZVVSSXEAfgAETAAGcHJlZml4cQB+AAR4cHQAGk9TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNldAAAcQB+ACR0B3RyTzBBQlhOeUFESnZjbWN1YTNWaGJHa3VjbWxqWlM1cmMySXViV1Z6YzJGbmFXNW5Ma3BoZG1GVFpYSjJhV05sUkdWbWFXNXBkR2x2YnJibkRWOUJOR3p4QWdBQlRBQVJjMlZ5ZG1salpVbHVkR1Z5Wm1GalpYTjBBQkJNYW1GMllTOTFkR2xzTDB4cGMzUTdlSElBTG05eVp5NXJkV0ZzYVM1eWFXTmxMbXR6WWk1dFpYTnpZV2RwYm1jdVUyVnlkbWxqWlVSbFptbHVhWFJwYjI0QW13SlBXTjBwZmdJQURVd0FDMkoxYzFObFkzVnlhWFI1ZEFBVFRHcGhkbUV2YkdGdVp5OUNiMjlzWldGdU8wd0FEMk55WldSbGJuUnBZV3h6Vkhsd1pYUUFURXh2Y21jdmEzVmhiR2t2Y21salpTOWpiM0psTDNObFkzVnlhWFI1TDJOeVpXUmxiblJwWVd4ekwwTnlaV1JsYm5ScFlXeHpVMjkxY21ObEpFTnlaV1JsYm5ScFlXeHpWSGx3WlR0TUFCQnNiMk5oYkZObGNuWnBZMlZPWVcxbGRBQVNUR3BoZG1FdmJHRnVaeTlUZEhKcGJtYzdUQUFYYldWemMyRm5aVVY0WTJWd2RHbHZia2hoYm1Sc1pYSnhBSDRBQlV3QURHMXBiR3hwYzFSdlRHbDJaWFFBRUV4cVlYWmhMMnhoYm1jdlRHOXVaenRNQUFod2NtbHZjbWwwZVhRQUUweHFZWFpoTDJ4aGJtY3ZTVzUwWldkbGNqdE1BQVZ4ZFdWMVpYRUFmZ0FEVEFBTmNtVjBjbmxCZEhSbGJYQjBjM0VBZmdBSFRBQUhjMlZ5ZG1salpYUUFFa3hxWVhaaEwyeGhibWN2VDJKcVpXTjBPMHdBRDNObGNuWnBZMlZGYm1SUWIybHVkSFFBRGt4cVlYWmhMMjVsZEM5VlVrdzdUQUFUYzJWeWRtbGpaVTVoYldWVGNHRmpaVlZTU1hFQWZnQUZUQUFRYzJWeWRtbGpaVTVoYldWemNHRmpaWEVBZmdBRlRBQUxjMlZ5ZG1salpWQmhkR2h4QUg0QUJYaHdjM0lBRVdwaGRtRXViR0Z1Wnk1Q2IyOXNaV0Z1elNCeWdOV2MrdTRDQUFGYUFBVjJZV3gxWlhod0FYQjBBQnBQVTBOaFkyaGxUbTkwYVdacFkyRjBhVzl1VTJWeWRtbGpaWFFBVFc5eVp5NXJkV0ZzYVM1eWFXTmxMbXR6WWk1dFpYTnpZV2RwYm1jdVpYaGpaWEIwYVc5dWFHRnVaR3hwYm1jdVJHVm1ZWFZzZEUxbGMzTmhaMlZGZUdObGNIUnBiMjVJWVc1a2JHVnljM0lBRG1waGRtRXViR0Z1Wnk1TWIyNW5PNHZra015UEk5OENBQUZLQUFWMllXeDFaWGh5QUJCcVlYWmhMbXhoYm1jdVRuVnRZbVZ5aHF5VkhRdVU0SXNDQUFCNGNQLy8vLy8vLy8vL2MzSUFFV3BoZG1FdWJHRnVaeTVKYm5SbFoyVnlFdUtncFBlQmh6Z0NBQUZKQUFWMllXeDFaWGh4QUg0QUVBQUFBQU56Y1FCK0FBc0FjUUIrQUJOd2MzSUFER3BoZG1FdWJtVjBMbFZTVEpZbE56WWEvT1J5QXdBSFNRQUlhR0Z6YUVOdlpHVkpBQVJ3YjNKMFRBQUpZWFYwYUc5eWFYUjVjUUIrQUFWTUFBUm1hV3hsY1FCK0FBVk1BQVJvYjNOMGNRQitBQVZNQUFod2NtOTBiMk52YkhFQWZnQUZUQUFEY21WbWNRQitBQVY0Y1AvLy8vOEFBQitRZEFBT2JHOWpZV3hvYjNOME9qZ3dPREIwQUNzdmEzSXRaR1YyTDNKbGJXOTBhVzVuTDA5VFEyRmphR1ZPYjNScFptbGpZWFJwYjI1VFpYSjJhV05sZEFBSmJHOWpZV3hvYjNOMGRBQUVhSFIwY0hCNGRBQUFkQUFHVkZKQlZrVk1kQUFCTDNOeUFCTnFZWFpoTG5WMGFXd3VRWEp5WVhsTWFYTjBlSUhTSFpuSFlaMERBQUZKQUFSemFYcGxlSEFBQUFBRmR3UUFBQUFLZEFBemIzSm5MbXQxWVd4cExuSnBZMlV1';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 373    FOR UPDATE;        
    buffer := 'YTNOaUxtMWxjM05oWjJsdVp5NXpaWEoyYVdObExrdFRRa3BoZG1GVFpYSjJhV05sZEFBOFkyOXRMbTl3Wlc1emVXMXdhRzl1ZVM1dmMyTmhZMmhsTG1KaGMyVXVaWFpsYm5SekxrTmhZMmhsUlc1MGNubEZkbVZ1ZEV4cGMzUmxibVZ5ZEFBM1kyOXRMbTl3Wlc1emVXMXdhRzl1ZVM1dmMyTmhZMmhsTG1KaGMyVXVaWFpsYm5SekxrTmhZMmhsUlhabGJuUk1hWE4wWlc1bGNuUUFGMnBoZG1FdWRYUnBiQzVGZG1WdWRFeHBjM1JsYm1WeWRBQXNZMjl0TG05d1pXNXplVzF3YUc5dWVTNXZjMk5oWTJobExtSmhjMlV1VEdsbVpXTjVZMnhsUVhkaGNtVjR0AA0xMjkuMTg2Ljc5LjQ1c3IAMm9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuSmF2YVNlcnZpY2VEZWZpbml0aW9utucNX0E0bPECAAFMABFzZXJ2aWNlSW50ZXJmYWNlc3QAEExqYXZhL3V0aWwvTGlzdDt4cgAub3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5TZXJ2aWNlRGVmaW5pdGlvbgCbAk9Y3Sl+AgANTAALYnVzU2VjdXJpdHlxAH4AE0wAD2NyZWRlbnRpYWxzVHlwZXQATExvcmcva3VhbGkvcmljZS9jb3JlL3NlY3VyaXR5L2NyZWRlbnRpYWxzL0NyZWRlbnRpYWxzU291cmNlJENyZWRlbnRpYWxzVHlwZTtMABBsb2NhbFNlcnZpY2VOYW1lcQB+AARMABdtZXNzYWdlRXhjZXB0aW9uSGFuZGxlcnEAfgAETAAMbWlsbGlzVG9MaXZlcQB+ABVMAAhwcmlvcml0eXEAfgAUTAAFcXVldWVxAH4AE0wADXJldHJ5QXR0ZW1wdHNxAH4AFEwAB3NlcnZpY2V0ABJMamF2YS9sYW5nL09iamVjdDtMAA9zZXJ2aWNlRW5kUG9pbnR0AA5MamF2YS9uZXQvVVJMO0wAE3NlcnZpY2VOYW1lU3BhY2VVUklxAH4ABEwAEHNlcnZpY2VOYW1lc3BhY2VxAH4ABEwAC3NlcnZpY2VQYXRocQB+AAR4cHNxAH4AGQFwdAAaT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2V0AE1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLmV4Y2VwdGlvbmhhbmRsaW5nLkRlZmF1bHRNZXNzYWdlRXhjZXB0aW9uSGFuZGxlcnNxAH4AH///////////c3EAfgAcAAAAA3NxAH4AGQBxAH4AMnBzcgAMamF2YS5uZXQuVVJMliU3Nhr85HIDAAdJAAhoYXNoQ29kZUkABHBvcnRMAAlhdXRob3JpdHlxAH4ABEwABGZpbGVxAH4ABEwABGhvc3RxAH4ABEwACHByb3RvY29scQB+AARMAANyZWZxAH4ABHhwaRuioAAAH5B0AA5sb2NhbGhvc3Q6ODA4MHQAKy9rci1kZXYvcmVtb3RpbmcvT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2V0AAlsb2NhbGhvc3R0AARodHRwcHh0AAB0AAZUUkFWRUx0AAEvc3IAE2phdmEudXRpbC5BcnJheUxpc3R4gdIdmcdhnQMAAUkABHNpemV4cAAAAAV3BAAAAAp0ADNvcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLnNlcnZpY2UuS1NCSmF2YVNlcnZpY2V0ADxjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5ldmVudHMuQ2FjaGVFbnRyeUV2ZW50TGlzdGVuZXJ0ADdjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5ldmVudHMuQ2FjaGVFdmVudExpc3RlbmVydAAXamF2YS51dGlsLkV2ZW50TGlzdGVuZXJ0ACxjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5MaWZlY3ljbGVBd2FyZXhxAH4AI3QABlRSQVZFTA==';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
INSERT INTO KRSB_MSG_PYLD_T (MSG_PYLD,MSG_QUE_ID)
  VALUES (EMPTY_CLOB(),374)
/
-- Length: 6088
-- Chunks: 2
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 374    FOR UPDATE;        
    buffer := 'rO0ABXNyAC1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLkFzeW5jaHJvbm91c0NhbGzxnQ5Y0t0lZQIAB1oAFWlnbm9yZVN0b3JlQW5kRm9yd2FyZFsACWFyZ3VtZW50c3QAE1tMamF2YS9sYW5nL09iamVjdDtMAAhjYWxsYmFja3QAM0xvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL0FzeW5jaHJvbm91c0NhbGxiYWNrO0wAB2NvbnRleHR0ABZMamF2YS9pby9TZXJpYWxpemFibGU7TAAKbWV0aG9kTmFtZXQAEkxqYXZhL2xhbmcvU3RyaW5nO1sACnBhcmFtVHlwZXN0ABJbTGphdmEvbGFuZy9DbGFzcztMAAtzZXJ2aWNlSW5mb3QAKkxvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL1NlcnZpY2VJbmZvO3hwAHVyABNbTGphdmEubGFuZy5PYmplY3Q7kM5YnxBzKWwCAAB4cAAAAAFzcgBDY29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLnBsdWdpbnMuY2x1c3RlcnN1cHBvcnQuQ2x1c3Rlck5vdGlmaWNhdGlvbhY+6VckvG9pAgACSQAEdHlwZUwABGRhdGFxAH4AA3hwAAAAAnQAI0RvY3VtZW50VHlwZVJ1bGVDYWNoZTpLdWFsaURvY3VtZW50cHB0AAZpbnZva2V1cgASW0xqYXZhLmxhbmcuQ2xhc3M7qxbXrsvNWpkCAAB4cAAAAAF2cgAUamF2YS5pby5TZXJpYWxpemFibGUQm2EN15tk7QIAAHhwc3IAKG9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuU2VydmljZUluZm/FFyO2KQS3ugIAC0wABWFsaXZldAATTGphdmEvbGFuZy9Cb29sZWFuO0wAFGVuZHBvaW50QWx0ZXJuYXRlVXJscQB+AARMAAtlbmRwb2ludFVybHEAfgAETAAKbG9ja1Zlck5icnQAE0xqYXZhL2xhbmcvSW50ZWdlcjtMAA5tZXNzYWdlRW50cnlJZHQAEExqYXZhL2xhbmcvTG9uZztMAAVxbmFtZXQAG0xqYXZheC94bWwvbmFtZXNwYWNlL1FOYW1lO0wAGnNlcmlhbGl6ZWRTZXJ2aWNlTmFtZXNwYWNlcQB+AARMAAhzZXJ2ZXJJcHEAfgAETAARc2VydmljZURlZmluaXRpb250ADBMb3JnL2t1YWxpL3JpY2Uva3NiL21lc3NhZ2luZy9TZXJ2aWNlRGVmaW5pdGlvbjtMAAtzZXJ2aWNlTmFtZXEAfgAETAAQc2VydmljZU5hbWVzcGFjZXEAfgAEeHBzcgARamF2YS5sYW5nLkJvb2xlYW7NIHKA1Zz67gIAAVoABXZhbHVleHABcHQAQGh0dHA6Ly9sb2NhbGhvc3Q6ODA4MC9rci1kZXYvcmVtb3RpbmcvT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2VzcgARamF2YS5sYW5nLkludGVnZXIS4qCk94GHOAIAAUkABXZhbHVleHIAEGphdmEubGFuZy5OdW1iZXKGrJUdC5TgiwIAAHhwAAAAAXNyAA5qYXZhLmxhbmcuTG9uZzuL5JDMjyPfAgABSgAFdmFsdWV4cQB+AB0AAAAAAAAPfXNyABlqYXZheC54bWwubmFtZXNwYWNlLlFOYW1lgW2oLfw73WwCAANMAAlsb2NhbFBhcnRxAH4ABEwADG5hbWVzcGFjZVVSSXEAfgAETAAGcHJlZml4cQB+AAR4cHQAGk9TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNldAAAcQB+ACR0B3RyTzBBQlhOeUFESnZjbWN1YTNWaGJHa3VjbWxqWlM1cmMySXViV1Z6YzJGbmFXNW5Ma3BoZG1GVFpYSjJhV05sUkdWbWFXNXBkR2x2YnJibkRWOUJOR3p4QWdBQlRBQVJjMlZ5ZG1salpVbHVkR1Z5Wm1GalpYTjBBQkJNYW1GMllTOTFkR2xzTDB4cGMzUTdlSElBTG05eVp5NXJkV0ZzYVM1eWFXTmxMbXR6WWk1dFpYTnpZV2RwYm1jdVUyVnlkbWxqWlVSbFptbHVhWFJwYjI0QW13SlBXTjBwZmdJQURVd0FDMkoxYzFObFkzVnlhWFI1ZEFBVFRHcGhkbUV2YkdGdVp5OUNiMjlzWldGdU8wd0FEMk55WldSbGJuUnBZV3h6Vkhsd1pYUUFURXh2Y21jdmEzVmhiR2t2Y21salpTOWpiM0psTDNObFkzVnlhWFI1TDJOeVpXUmxiblJwWVd4ekwwTnlaV1JsYm5ScFlXeHpVMjkxY21ObEpFTnlaV1JsYm5ScFlXeHpWSGx3WlR0TUFCQnNiMk5oYkZObGNuWnBZMlZPWVcxbGRBQVNUR3BoZG1FdmJHRnVaeTlUZEhKcGJtYzdUQUFYYldWemMyRm5aVVY0WTJWd2RHbHZia2hoYm1Sc1pYSnhBSDRBQlV3QURHMXBiR3hwYzFSdlRHbDJaWFFBRUV4cVlYWmhMMnhoYm1jdlRHOXVaenRNQUFod2NtbHZjbWwwZVhRQUUweHFZWFpoTDJ4aGJtY3ZTVzUwWldkbGNqdE1BQVZ4ZFdWMVpYRUFmZ0FEVEFBTmNtVjBjbmxCZEhSbGJYQjBjM0VBZmdBSFRBQUhjMlZ5ZG1salpYUUFFa3hxWVhaaEwyeGhibWN2VDJKcVpXTjBPMHdBRDNObGNuWnBZMlZGYm1SUWIybHVkSFFBRGt4cVlYWmhMMjVsZEM5VlVrdzdUQUFUYzJWeWRtbGpaVTVoYldWVGNHRmpaVlZTU1hFQWZnQUZUQUFRYzJWeWRtbGpaVTVoYldWemNHRmpaWEVBZmdBRlRBQUxjMlZ5ZG1salpWQmhkR2h4QUg0QUJYaHdjM0lBRVdwaGRtRXViR0Z1Wnk1Q2IyOXNaV0Z1elNCeWdOV2MrdTRDQUFGYUFBVjJZV3gxWlhod0FYQjBBQnBQVTBOaFkyaGxUbTkwYVdacFkyRjBhVzl1VTJWeWRtbGpaWFFBVFc5eVp5NXJkV0ZzYVM1eWFXTmxMbXR6WWk1dFpYTnpZV2RwYm1jdVpYaGpaWEIwYVc5dWFHRnVaR3hwYm1jdVJHVm1ZWFZzZEUxbGMzTmhaMlZGZUdObGNIUnBiMjVJWVc1a2JHVnljM0lBRG1waGRtRXViR0Z1Wnk1TWIyNW5PNHZra015UEk5OENBQUZLQUFWMllXeDFaWGh5QUJCcVlYWmhMbXhoYm1jdVRuVnRZbVZ5aHF5VkhRdVU0SXNDQUFCNGNQLy8vLy8vLy8vL2MzSUFFV3BoZG1FdWJHRnVaeTVKYm5SbFoyVnlFdUtncFBlQmh6Z0NBQUZKQUFWMllXeDFaWGh4QUg0QUVBQUFBQU56Y1FCK0FBc0FjUUIrQUJOd2MzSUFER3BoZG1FdWJtVjBMbFZTVEpZbE56WWEvT1J5QXdBSFNRQUlhR0Z6YUVOdlpHVkpBQVJ3YjNKMFRBQUpZWFYwYUc5eWFYUjVjUUIrQUFWTUFBUm1hV3hsY1FCK0FBVk1BQVJvYjNOMGNRQitBQVZNQUFod2NtOTBiMk52YkhFQWZnQUZUQUFEY21WbWNRQitBQVY0Y1AvLy8vOEFBQitRZEFBT2JHOWpZV3hvYjNOME9qZ3dPREIwQUNzdmEzSXRaR1YyTDNKbGJXOTBhVzVuTDA5VFEyRmphR1ZPYjNScFptbGpZWFJwYjI1VFpYSjJhV05sZEFBSmJHOWpZV3hvYjNOMGRBQUVhSFIwY0hCNGRBQUFkQUFHVkZKQlZrVk1kQUFCTDNOeUFCTnFZWFpoTG5WMGFXd3VRWEp5WVhsTWFYTjBlSUhTSFpuSFlaMERBQUZKQUFSemFYcGxlSEFBQUFBRmR3UUFBQUFLZEFBemIzSm5MbXQxWVd4cExuSnBZ';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 374    FOR UPDATE;        
    buffer := 'MlV1YTNOaUxtMWxjM05oWjJsdVp5NXpaWEoyYVdObExrdFRRa3BoZG1GVFpYSjJhV05sZEFBOFkyOXRMbTl3Wlc1emVXMXdhRzl1ZVM1dmMyTmhZMmhsTG1KaGMyVXVaWFpsYm5SekxrTmhZMmhsUlc1MGNubEZkbVZ1ZEV4cGMzUmxibVZ5ZEFBM1kyOXRMbTl3Wlc1emVXMXdhRzl1ZVM1dmMyTmhZMmhsTG1KaGMyVXVaWFpsYm5SekxrTmhZMmhsUlhabGJuUk1hWE4wWlc1bGNuUUFGMnBoZG1FdWRYUnBiQzVGZG1WdWRFeHBjM1JsYm1WeWRBQXNZMjl0TG05d1pXNXplVzF3YUc5dWVTNXZjMk5oWTJobExtSmhjMlV1VEdsbVpXTjVZMnhsUVhkaGNtVjR0AA0xMjkuMTg2Ljc5LjQ1c3IAMm9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuSmF2YVNlcnZpY2VEZWZpbml0aW9utucNX0E0bPECAAFMABFzZXJ2aWNlSW50ZXJmYWNlc3QAEExqYXZhL3V0aWwvTGlzdDt4cgAub3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5TZXJ2aWNlRGVmaW5pdGlvbgCbAk9Y3Sl+AgANTAALYnVzU2VjdXJpdHlxAH4AE0wAD2NyZWRlbnRpYWxzVHlwZXQATExvcmcva3VhbGkvcmljZS9jb3JlL3NlY3VyaXR5L2NyZWRlbnRpYWxzL0NyZWRlbnRpYWxzU291cmNlJENyZWRlbnRpYWxzVHlwZTtMABBsb2NhbFNlcnZpY2VOYW1lcQB+AARMABdtZXNzYWdlRXhjZXB0aW9uSGFuZGxlcnEAfgAETAAMbWlsbGlzVG9MaXZlcQB+ABVMAAhwcmlvcml0eXEAfgAUTAAFcXVldWVxAH4AE0wADXJldHJ5QXR0ZW1wdHNxAH4AFEwAB3NlcnZpY2V0ABJMamF2YS9sYW5nL09iamVjdDtMAA9zZXJ2aWNlRW5kUG9pbnR0AA5MamF2YS9uZXQvVVJMO0wAE3NlcnZpY2VOYW1lU3BhY2VVUklxAH4ABEwAEHNlcnZpY2VOYW1lc3BhY2VxAH4ABEwAC3NlcnZpY2VQYXRocQB+AAR4cHNxAH4AGQFwdAAaT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2V0AE1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLmV4Y2VwdGlvbmhhbmRsaW5nLkRlZmF1bHRNZXNzYWdlRXhjZXB0aW9uSGFuZGxlcnNxAH4AH///////////c3EAfgAcAAAAA3NxAH4AGQBxAH4AMnBzcgAMamF2YS5uZXQuVVJMliU3Nhr85HIDAAdJAAhoYXNoQ29kZUkABHBvcnRMAAlhdXRob3JpdHlxAH4ABEwABGZpbGVxAH4ABEwABGhvc3RxAH4ABEwACHByb3RvY29scQB+AARMAANyZWZxAH4ABHhwaRuioAAAH5B0AA5sb2NhbGhvc3Q6ODA4MHQAKy9rci1kZXYvcmVtb3RpbmcvT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2V0AAlsb2NhbGhvc3R0AARodHRwcHh0AAB0AAZUUkFWRUx0AAEvc3IAE2phdmEudXRpbC5BcnJheUxpc3R4gdIdmcdhnQMAAUkABHNpemV4cAAAAAV3BAAAAAp0ADNvcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLnNlcnZpY2UuS1NCSmF2YVNlcnZpY2V0ADxjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5ldmVudHMuQ2FjaGVFbnRyeUV2ZW50TGlzdGVuZXJ0ADdjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5ldmVudHMuQ2FjaGVFdmVudExpc3RlbmVydAAXamF2YS51dGlsLkV2ZW50TGlzdGVuZXJ0ACxjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5MaWZlY3ljbGVBd2FyZXhxAH4AI3QABlRSQVZFTA==';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
INSERT INTO KRSB_MSG_PYLD_T (MSG_PYLD,MSG_QUE_ID)
  VALUES (EMPTY_CLOB(),375)
/
-- Length: 6084
-- Chunks: 2
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 375    FOR UPDATE;        
    buffer := 'rO0ABXNyAC1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLkFzeW5jaHJvbm91c0NhbGzxnQ5Y0t0lZQIAB1oAFWlnbm9yZVN0b3JlQW5kRm9yd2FyZFsACWFyZ3VtZW50c3QAE1tMamF2YS9sYW5nL09iamVjdDtMAAhjYWxsYmFja3QAM0xvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL0FzeW5jaHJvbm91c0NhbGxiYWNrO0wAB2NvbnRleHR0ABZMamF2YS9pby9TZXJpYWxpemFibGU7TAAKbWV0aG9kTmFtZXQAEkxqYXZhL2xhbmcvU3RyaW5nO1sACnBhcmFtVHlwZXN0ABJbTGphdmEvbGFuZy9DbGFzcztMAAtzZXJ2aWNlSW5mb3QAKkxvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL1NlcnZpY2VJbmZvO3hwAHVyABNbTGphdmEubGFuZy5PYmplY3Q7kM5YnxBzKWwCAAB4cAAAAAFzcgBDY29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLnBsdWdpbnMuY2x1c3RlcnN1cHBvcnQuQ2x1c3Rlck5vdGlmaWNhdGlvbhY+6VckvG9pAgACSQAEdHlwZUwABGRhdGFxAH4AA3hwAAAAAnQAIkRvY3VtZW50VHlwZVJ1bGVDYWNoZTpSaWNlRG9jdW1lbnRwcHQABmludm9rZXVyABJbTGphdmEubGFuZy5DbGFzczurFteuy81amQIAAHhwAAAAAXZyABRqYXZhLmlvLlNlcmlhbGl6YWJsZRCbYQ3Xm2TtAgAAeHBzcgAob3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5TZXJ2aWNlSW5mb8UXI7YpBLe6AgALTAAFYWxpdmV0ABNMamF2YS9sYW5nL0Jvb2xlYW47TAAUZW5kcG9pbnRBbHRlcm5hdGVVcmxxAH4ABEwAC2VuZHBvaW50VXJscQB+AARMAApsb2NrVmVyTmJydAATTGphdmEvbGFuZy9JbnRlZ2VyO0wADm1lc3NhZ2VFbnRyeUlkdAAQTGphdmEvbGFuZy9Mb25nO0wABXFuYW1ldAAbTGphdmF4L3htbC9uYW1lc3BhY2UvUU5hbWU7TAAac2VyaWFsaXplZFNlcnZpY2VOYW1lc3BhY2VxAH4ABEwACHNlcnZlcklwcQB+AARMABFzZXJ2aWNlRGVmaW5pdGlvbnQAMExvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL1NlcnZpY2VEZWZpbml0aW9uO0wAC3NlcnZpY2VOYW1lcQB+AARMABBzZXJ2aWNlTmFtZXNwYWNlcQB+AAR4cHNyABFqYXZhLmxhbmcuQm9vbGVhbs0gcoDVnPruAgABWgAFdmFsdWV4cAFwdABAaHR0cDovL2xvY2FsaG9zdDo4MDgwL2tyLWRldi9yZW1vdGluZy9PU0NhY2hlTm90aWZpY2F0aW9uU2VydmljZXNyABFqYXZhLmxhbmcuSW50ZWdlchLioKT3gYc4AgABSQAFdmFsdWV4cgAQamF2YS5sYW5nLk51bWJlcoaslR0LlOCLAgAAeHAAAAABc3IADmphdmEubGFuZy5Mb25nO4vkkMyPI98CAAFKAAV2YWx1ZXhxAH4AHQAAAAAAAA99c3IAGWphdmF4LnhtbC5uYW1lc3BhY2UuUU5hbWWBbagt/DvdbAIAA0wACWxvY2FsUGFydHEAfgAETAAMbmFtZXNwYWNlVVJJcQB+AARMAAZwcmVmaXhxAH4ABHhwdAAaT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2V0AABxAH4AJHQHdHJPMEFCWE55QURKdmNtY3VhM1ZoYkdrdWNtbGpaUzVyYzJJdWJXVnpjMkZuYVc1bkxrcGhkbUZUWlhKMmFXTmxSR1ZtYVc1cGRHbHZicmJuRFY5Qk5HenhBZ0FCVEFBUmMyVnlkbWxqWlVsdWRHVnlabUZqWlhOMEFCQk1hbUYyWVM5MWRHbHNMMHhwYzNRN2VISUFMbTl5Wnk1cmRXRnNhUzV5YVdObExtdHpZaTV0WlhOellXZHBibWN1VTJWeWRtbGpaVVJsWm1sdWFYUnBiMjRBbXdKUFdOMHBmZ0lBRFV3QUMySjFjMU5sWTNWeWFYUjVkQUFUVEdwaGRtRXZiR0Z1Wnk5Q2IyOXNaV0Z1TzB3QUQyTnlaV1JsYm5ScFlXeHpWSGx3WlhRQVRFeHZjbWN2YTNWaGJHa3ZjbWxqWlM5amIzSmxMM05sWTNWeWFYUjVMMk55WldSbGJuUnBZV3h6TDBOeVpXUmxiblJwWVd4elUyOTFjbU5sSkVOeVpXUmxiblJwWVd4elZIbHdaVHRNQUJCc2IyTmhiRk5sY25acFkyVk9ZVzFsZEFBU1RHcGhkbUV2YkdGdVp5OVRkSEpwYm1jN1RBQVhiV1Z6YzJGblpVVjRZMlZ3ZEdsdmJraGhibVJzWlhKeEFINEFCVXdBREcxcGJHeHBjMVJ2VEdsMlpYUUFFRXhxWVhaaEwyeGhibWN2VEc5dVp6dE1BQWh3Y21sdmNtbDBlWFFBRTB4cVlYWmhMMnhoYm1jdlNXNTBaV2RsY2p0TUFBVnhkV1YxWlhFQWZnQURUQUFOY21WMGNubEJkSFJsYlhCMGMzRUFmZ0FIVEFBSGMyVnlkbWxqWlhRQUVreHFZWFpoTDJ4aGJtY3ZUMkpxWldOME8wd0FEM05sY25acFkyVkZibVJRYjJsdWRIUUFEa3hxWVhaaEwyNWxkQzlWVWt3N1RBQVRjMlZ5ZG1salpVNWhiV1ZUY0dGalpWVlNTWEVBZmdBRlRBQVFjMlZ5ZG1salpVNWhiV1Z6Y0dGalpYRUFmZ0FGVEFBTGMyVnlkbWxqWlZCaGRHaHhBSDRBQlhod2MzSUFFV3BoZG1FdWJHRnVaeTVDYjI5c1pXRnV6U0J5Z05XYyt1NENBQUZhQUFWMllXeDFaWGh3QVhCMEFCcFBVME5oWTJobFRtOTBhV1pwWTJGMGFXOXVVMlZ5ZG1salpYUUFUVzl5Wnk1cmRXRnNhUzV5YVdObExtdHpZaTV0WlhOellXZHBibWN1WlhoalpYQjBhVzl1YUdGdVpHeHBibWN1UkdWbVlYVnNkRTFsYzNOaFoyVkZlR05sY0hScGIyNUlZVzVrYkdWeWMzSUFEbXBoZG1FdWJHRnVaeTVNYjI1bk80dmtrTXlQSTk4Q0FBRktBQVYyWVd4MVpYaHlBQkJxWVhaaExteGhibWN1VG5WdFltVnlocXlWSFF1VTRJc0NBQUI0Y1AvLy8vLy8vLy8vYzNJQUVXcGhkbUV1YkdGdVp5NUpiblJsWjJWeUV1S2dwUGVCaHpnQ0FBRkpBQVYyWVd4MVpYaHhBSDRBRUFBQUFBTnpjUUIrQUFzQWNRQitBQk53YzNJQURHcGhkbUV1Ym1WMExsVlNUSllsTnpZYS9PUnlBd0FIU1FBSWFHRnphRU52WkdWSkFBUndiM0owVEFBSllYVjBhRzl5YVhSNWNRQitBQVZNQUFSbWFXeGxjUUIrQUFWTUFBUm9iM04wY1FCK0FBVk1BQWh3Y205MGIyTnZiSEVBZmdBRlRBQURjbVZtY1FCK0FBVjRjUC8vLy84QUFCK1FkQUFPYkc5allXeG9iM04wT2pnd09EQjBBQ3N2YTNJdFpHVjJMM0psYlc5MGFXNW5MMDlUUTJGamFHVk9iM1JwWm1sallYUnBiMjVUWlhKMmFXTmxkQUFKYkc5allXeG9iM04wZEFBRWFIUjBjSEI0ZEFBQWRBQUdWRkpCVmtWTWRBQUJMM055QUJOcVlYWmhMblYwYVd3dVFYSnlZWGxNYVhOMGVJSFNIWm5IWVowREFBRkpBQVJ6YVhwbGVIQUFBQUFGZHdRQUFBQUtkQUF6YjNKbkxtdDFZV3hwTG5KcFky';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 375    FOR UPDATE;        
    buffer := 'VXVhM05pTG0xbGMzTmhaMmx1Wnk1elpYSjJhV05sTGt0VFFrcGhkbUZUWlhKMmFXTmxkQUE4WTI5dExtOXdaVzV6ZVcxd2FHOXVlUzV2YzJOaFkyaGxMbUpoYzJVdVpYWmxiblJ6TGtOaFkyaGxSVzUwY25sRmRtVnVkRXhwYzNSbGJtVnlkQUEzWTI5dExtOXdaVzV6ZVcxd2FHOXVlUzV2YzJOaFkyaGxMbUpoYzJVdVpYWmxiblJ6TGtOaFkyaGxSWFpsYm5STWFYTjBaVzVsY25RQUYycGhkbUV1ZFhScGJDNUZkbVZ1ZEV4cGMzUmxibVZ5ZEFBc1kyOXRMbTl3Wlc1emVXMXdhRzl1ZVM1dmMyTmhZMmhsTG1KaGMyVXVUR2xtWldONVkyeGxRWGRoY21WNHQADTEyOS4xODYuNzkuNDVzcgAyb3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5KYXZhU2VydmljZURlZmluaXRpb2625w1fQTRs8QIAAUwAEXNlcnZpY2VJbnRlcmZhY2VzdAAQTGphdmEvdXRpbC9MaXN0O3hyAC5vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLlNlcnZpY2VEZWZpbml0aW9uAJsCT1jdKX4CAA1MAAtidXNTZWN1cml0eXEAfgATTAAPY3JlZGVudGlhbHNUeXBldABMTG9yZy9rdWFsaS9yaWNlL2NvcmUvc2VjdXJpdHkvY3JlZGVudGlhbHMvQ3JlZGVudGlhbHNTb3VyY2UkQ3JlZGVudGlhbHNUeXBlO0wAEGxvY2FsU2VydmljZU5hbWVxAH4ABEwAF21lc3NhZ2VFeGNlcHRpb25IYW5kbGVycQB+AARMAAxtaWxsaXNUb0xpdmVxAH4AFUwACHByaW9yaXR5cQB+ABRMAAVxdWV1ZXEAfgATTAANcmV0cnlBdHRlbXB0c3EAfgAUTAAHc2VydmljZXQAEkxqYXZhL2xhbmcvT2JqZWN0O0wAD3NlcnZpY2VFbmRQb2ludHQADkxqYXZhL25ldC9VUkw7TAATc2VydmljZU5hbWVTcGFjZVVSSXEAfgAETAAQc2VydmljZU5hbWVzcGFjZXEAfgAETAALc2VydmljZVBhdGhxAH4ABHhwc3EAfgAZAXB0ABpPU0NhY2hlTm90aWZpY2F0aW9uU2VydmljZXQATW9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuZXhjZXB0aW9uaGFuZGxpbmcuRGVmYXVsdE1lc3NhZ2VFeGNlcHRpb25IYW5kbGVyc3EAfgAf//////////9zcQB+ABwAAAADc3EAfgAZAHEAfgAycHNyAAxqYXZhLm5ldC5VUkyWJTc2GvzkcgMAB0kACGhhc2hDb2RlSQAEcG9ydEwACWF1dGhvcml0eXEAfgAETAAEZmlsZXEAfgAETAAEaG9zdHEAfgAETAAIcHJvdG9jb2xxAH4ABEwAA3JlZnEAfgAEeHBpG6KgAAAfkHQADmxvY2FsaG9zdDo4MDgwdAArL2tyLWRldi9yZW1vdGluZy9PU0NhY2hlTm90aWZpY2F0aW9uU2VydmljZXQACWxvY2FsaG9zdHQABGh0dHBweHQAAHQABlRSQVZFTHQAAS9zcgATamF2YS51dGlsLkFycmF5TGlzdHiB0h2Zx2GdAwABSQAEc2l6ZXhwAAAABXcEAAAACnQAM29yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuc2VydmljZS5LU0JKYXZhU2VydmljZXQAPGNvbS5vcGVuc3ltcGhvbnkub3NjYWNoZS5iYXNlLmV2ZW50cy5DYWNoZUVudHJ5RXZlbnRMaXN0ZW5lcnQAN2NvbS5vcGVuc3ltcGhvbnkub3NjYWNoZS5iYXNlLmV2ZW50cy5DYWNoZUV2ZW50TGlzdGVuZXJ0ABdqYXZhLnV0aWwuRXZlbnRMaXN0ZW5lcnQALGNvbS5vcGVuc3ltcGhvbnkub3NjYWNoZS5iYXNlLkxpZmVjeWNsZUF3YXJleHEAfgAjdAAGVFJBVkVM';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
INSERT INTO KRSB_MSG_PYLD_T (MSG_PYLD,MSG_QUE_ID)
  VALUES (EMPTY_CLOB(),376)
/
-- Length: 6096
-- Chunks: 2
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 376    FOR UPDATE;        
    buffer := 'rO0ABXNyAC1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLkFzeW5jaHJvbm91c0NhbGzxnQ5Y0t0lZQIAB1oAFWlnbm9yZVN0b3JlQW5kRm9yd2FyZFsACWFyZ3VtZW50c3QAE1tMamF2YS9sYW5nL09iamVjdDtMAAhjYWxsYmFja3QAM0xvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL0FzeW5jaHJvbm91c0NhbGxiYWNrO0wAB2NvbnRleHR0ABZMamF2YS9pby9TZXJpYWxpemFibGU7TAAKbWV0aG9kTmFtZXQAEkxqYXZhL2xhbmcvU3RyaW5nO1sACnBhcmFtVHlwZXN0ABJbTGphdmEvbGFuZy9DbGFzcztMAAtzZXJ2aWNlSW5mb3QAKkxvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL1NlcnZpY2VJbmZvO3hwAHVyABNbTGphdmEubGFuZy5PYmplY3Q7kM5YnxBzKWwCAAB4cAAAAAFzcgBDY29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLnBsdWdpbnMuY2x1c3RlcnN1cHBvcnQuQ2x1c3Rlck5vdGlmaWNhdGlvbhY+6VckvG9pAgACSQAEdHlwZUwABGRhdGFxAH4AA3hwAAAAAnQAKkRvY3VtZW50VHlwZVJ1bGVDYWNoZTpEb2N1bWVudFR5cGVEb2N1bWVudHBwdAAGaW52b2tldXIAEltMamF2YS5sYW5nLkNsYXNzO6sW167LzVqZAgAAeHAAAAABdnIAFGphdmEuaW8uU2VyaWFsaXphYmxlEJthDdebZO0CAAB4cHNyAChvcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLlNlcnZpY2VJbmZvxRcjtikEt7oCAAtMAAVhbGl2ZXQAE0xqYXZhL2xhbmcvQm9vbGVhbjtMABRlbmRwb2ludEFsdGVybmF0ZVVybHEAfgAETAALZW5kcG9pbnRVcmxxAH4ABEwACmxvY2tWZXJOYnJ0ABNMamF2YS9sYW5nL0ludGVnZXI7TAAObWVzc2FnZUVudHJ5SWR0ABBMamF2YS9sYW5nL0xvbmc7TAAFcW5hbWV0ABtMamF2YXgveG1sL25hbWVzcGFjZS9RTmFtZTtMABpzZXJpYWxpemVkU2VydmljZU5hbWVzcGFjZXEAfgAETAAIc2VydmVySXBxAH4ABEwAEXNlcnZpY2VEZWZpbml0aW9udAAwTG9yZy9rdWFsaS9yaWNlL2tzYi9tZXNzYWdpbmcvU2VydmljZURlZmluaXRpb247TAALc2VydmljZU5hbWVxAH4ABEwAEHNlcnZpY2VOYW1lc3BhY2VxAH4ABHhwc3IAEWphdmEubGFuZy5Cb29sZWFuzSBygNWc+u4CAAFaAAV2YWx1ZXhwAXB0AEBodHRwOi8vbG9jYWxob3N0OjgwODAva3ItZGV2L3JlbW90aW5nL09TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNlc3IAEWphdmEubGFuZy5JbnRlZ2VyEuKgpPeBhzgCAAFJAAV2YWx1ZXhyABBqYXZhLmxhbmcuTnVtYmVyhqyVHQuU4IsCAAB4cAAAAAFzcgAOamF2YS5sYW5nLkxvbmc7i+SQzI8j3wIAAUoABXZhbHVleHEAfgAdAAAAAAAAD31zcgAZamF2YXgueG1sLm5hbWVzcGFjZS5RTmFtZYFtqC38O91sAgADTAAJbG9jYWxQYXJ0cQB+AARMAAxuYW1lc3BhY2VVUklxAH4ABEwABnByZWZpeHEAfgAEeHB0ABpPU0NhY2hlTm90aWZpY2F0aW9uU2VydmljZXQAAHEAfgAkdAd0ck8wQUJYTnlBREp2Y21jdWEzVmhiR2t1Y21salpTNXJjMkl1YldWemMyRm5hVzVuTGtwaGRtRlRaWEoyYVdObFJHVm1hVzVwZEdsdmJyYm5EVjlCTkd6eEFnQUJUQUFSYzJWeWRtbGpaVWx1ZEdWeVptRmpaWE4wQUJCTWFtRjJZUzkxZEdsc0wweHBjM1E3ZUhJQUxtOXlaeTVyZFdGc2FTNXlhV05sTG10ellpNXRaWE56WVdkcGJtY3VVMlZ5ZG1salpVUmxabWx1YVhScGIyNEFtd0pQV04wcGZnSUFEVXdBQzJKMWMxTmxZM1Z5YVhSNWRBQVRUR3BoZG1FdmJHRnVaeTlDYjI5c1pXRnVPMHdBRDJOeVpXUmxiblJwWVd4elZIbHdaWFFBVEV4dmNtY3ZhM1ZoYkdrdmNtbGpaUzlqYjNKbEwzTmxZM1Z5YVhSNUwyTnlaV1JsYm5ScFlXeHpMME55WldSbGJuUnBZV3h6VTI5MWNtTmxKRU55WldSbGJuUnBZV3h6Vkhsd1pUdE1BQkJzYjJOaGJGTmxjblpwWTJWT1lXMWxkQUFTVEdwaGRtRXZiR0Z1Wnk5VGRISnBibWM3VEFBWGJXVnpjMkZuWlVWNFkyVndkR2x2YmtoaGJtUnNaWEp4QUg0QUJVd0FERzFwYkd4cGMxUnZUR2wyWlhRQUVFeHFZWFpoTDJ4aGJtY3ZURzl1Wnp0TUFBaHdjbWx2Y21sMGVYUUFFMHhxWVhaaEwyeGhibWN2U1c1MFpXZGxjanRNQUFWeGRXVjFaWEVBZmdBRFRBQU5jbVYwY25sQmRIUmxiWEIwYzNFQWZnQUhUQUFIYzJWeWRtbGpaWFFBRWt4cVlYWmhMMnhoYm1jdlQySnFaV04wTzB3QUQzTmxjblpwWTJWRmJtUlFiMmx1ZEhRQURreHFZWFpoTDI1bGRDOVZVa3c3VEFBVGMyVnlkbWxqWlU1aGJXVlRjR0ZqWlZWU1NYRUFmZ0FGVEFBUWMyVnlkbWxqWlU1aGJXVnpjR0ZqWlhFQWZnQUZUQUFMYzJWeWRtbGpaVkJoZEdoeEFINEFCWGh3YzNJQUVXcGhkbUV1YkdGdVp5NUNiMjlzWldGdXpTQnlnTldjK3U0Q0FBRmFBQVYyWVd4MVpYaHdBWEIwQUJwUFUwTmhZMmhsVG05MGFXWnBZMkYwYVc5dVUyVnlkbWxqWlhRQVRXOXlaeTVyZFdGc2FTNXlhV05sTG10ellpNXRaWE56WVdkcGJtY3VaWGhqWlhCMGFXOXVhR0Z1Wkd4cGJtY3VSR1ZtWVhWc2RFMWxjM05oWjJWRmVHTmxjSFJwYjI1SVlXNWtiR1Z5YzNJQURtcGhkbUV1YkdGdVp5NU1iMjVuTzR2a2tNeVBJOThDQUFGS0FBVjJZV3gxWlhoeUFCQnFZWFpoTG14aGJtY3VUblZ0WW1WeWhxeVZIUXVVNElzQ0FBQjRjUC8vLy8vLy8vLy9jM0lBRVdwaGRtRXViR0Z1Wnk1SmJuUmxaMlZ5RXVLZ3BQZUJoemdDQUFGSkFBVjJZV3gxWlhoeEFINEFFQUFBQUFOemNRQitBQXNBY1FCK0FCTndjM0lBREdwaGRtRXVibVYwTGxWU1RKWWxOellhL09SeUF3QUhTUUFJYUdGemFFTnZaR1ZKQUFSd2IzSjBUQUFKWVhWMGFHOXlhWFI1Y1FCK0FBVk1BQVJtYVd4bGNRQitBQVZNQUFSb2IzTjBjUUIrQUFWTUFBaHdjbTkwYjJOdmJIRUFmZ0FGVEFBRGNtVm1jUUIrQUFWNGNQLy8vLzhBQUIrUWRBQU9iRzlqWVd4b2IzTjBPamd3T0RCMEFDc3ZhM0l0WkdWMkwzSmxiVzkwYVc1bkwwOVRRMkZqYUdWT2IzUnBabWxqWVhScGIyNVRaWEoyYVdObGRBQUpiRzlqWVd4b2IzTjBkQUFFYUhSMGNIQjRkQUFBZEFBR1ZGSkJWa1ZNZEFBQkwzTnlBQk5xWVhaaExuVjBhV3d1UVhKeVlYbE1hWE4wZUlIU0habkhZWjBEQUFGSkFBUnphWHBsZUhBQUFBQUZkd1FBQUFBS2RBQXpiM0puTG10MVlX';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 376    FOR UPDATE;        
    buffer := 'eHBMbkpwWTJVdWEzTmlMbTFsYzNOaFoybHVaeTV6WlhKMmFXTmxMa3RUUWtwaGRtRlRaWEoyYVdObGRBQThZMjl0TG05d1pXNXplVzF3YUc5dWVTNXZjMk5oWTJobExtSmhjMlV1WlhabGJuUnpMa05oWTJobFJXNTBjbmxGZG1WdWRFeHBjM1JsYm1WeWRBQTNZMjl0TG05d1pXNXplVzF3YUc5dWVTNXZjMk5oWTJobExtSmhjMlV1WlhabGJuUnpMa05oWTJobFJYWmxiblJNYVhOMFpXNWxjblFBRjJwaGRtRXVkWFJwYkM1RmRtVnVkRXhwYzNSbGJtVnlkQUFzWTI5dExtOXdaVzV6ZVcxd2FHOXVlUzV2YzJOaFkyaGxMbUpoYzJVdVRHbG1aV041WTJ4bFFYZGhjbVY0dAANMTI5LjE4Ni43OS40NXNyADJvcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLkphdmFTZXJ2aWNlRGVmaW5pdGlvbrbnDV9BNGzxAgABTAARc2VydmljZUludGVyZmFjZXN0ABBMamF2YS91dGlsL0xpc3Q7eHIALm9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuU2VydmljZURlZmluaXRpb24AmwJPWN0pfgIADUwAC2J1c1NlY3VyaXR5cQB+ABNMAA9jcmVkZW50aWFsc1R5cGV0AExMb3JnL2t1YWxpL3JpY2UvY29yZS9zZWN1cml0eS9jcmVkZW50aWFscy9DcmVkZW50aWFsc1NvdXJjZSRDcmVkZW50aWFsc1R5cGU7TAAQbG9jYWxTZXJ2aWNlTmFtZXEAfgAETAAXbWVzc2FnZUV4Y2VwdGlvbkhhbmRsZXJxAH4ABEwADG1pbGxpc1RvTGl2ZXEAfgAVTAAIcHJpb3JpdHlxAH4AFEwABXF1ZXVlcQB+ABNMAA1yZXRyeUF0dGVtcHRzcQB+ABRMAAdzZXJ2aWNldAASTGphdmEvbGFuZy9PYmplY3Q7TAAPc2VydmljZUVuZFBvaW50dAAOTGphdmEvbmV0L1VSTDtMABNzZXJ2aWNlTmFtZVNwYWNlVVJJcQB+AARMABBzZXJ2aWNlTmFtZXNwYWNlcQB+AARMAAtzZXJ2aWNlUGF0aHEAfgAEeHBzcQB+ABkBcHQAGk9TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNldABNb3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5leGNlcHRpb25oYW5kbGluZy5EZWZhdWx0TWVzc2FnZUV4Y2VwdGlvbkhhbmRsZXJzcQB+AB///////////3NxAH4AHAAAAANzcQB+ABkAcQB+ADJwc3IADGphdmEubmV0LlVSTJYlNzYa/ORyAwAHSQAIaGFzaENvZGVJAARwb3J0TAAJYXV0aG9yaXR5cQB+AARMAARmaWxlcQB+AARMAARob3N0cQB+AARMAAhwcm90b2NvbHEAfgAETAADcmVmcQB+AAR4cGkboqAAAB+QdAAObG9jYWxob3N0OjgwODB0ACsva3ItZGV2L3JlbW90aW5nL09TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNldAAJbG9jYWxob3N0dAAEaHR0cHB4dAAAdAAGVFJBVkVMdAABL3NyABNqYXZhLnV0aWwuQXJyYXlMaXN0eIHSHZnHYZ0DAAFJAARzaXpleHAAAAAFdwQAAAAKdAAzb3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5zZXJ2aWNlLktTQkphdmFTZXJ2aWNldAA8Y29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLmJhc2UuZXZlbnRzLkNhY2hlRW50cnlFdmVudExpc3RlbmVydAA3Y29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLmJhc2UuZXZlbnRzLkNhY2hlRXZlbnRMaXN0ZW5lcnQAF2phdmEudXRpbC5FdmVudExpc3RlbmVydAAsY29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLmJhc2UuTGlmZWN5Y2xlQXdhcmV4cQB+ACN0AAZUUkFWRUw=';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
INSERT INTO KRSB_MSG_PYLD_T (MSG_PYLD,MSG_QUE_ID)
  VALUES (EMPTY_CLOB(),377)
/
-- Length: 6096
-- Chunks: 2
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 377    FOR UPDATE;        
    buffer := 'rO0ABXNyAC1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLkFzeW5jaHJvbm91c0NhbGzxnQ5Y0t0lZQIAB1oAFWlnbm9yZVN0b3JlQW5kRm9yd2FyZFsACWFyZ3VtZW50c3QAE1tMamF2YS9sYW5nL09iamVjdDtMAAhjYWxsYmFja3QAM0xvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL0FzeW5jaHJvbm91c0NhbGxiYWNrO0wAB2NvbnRleHR0ABZMamF2YS9pby9TZXJpYWxpemFibGU7TAAKbWV0aG9kTmFtZXQAEkxqYXZhL2xhbmcvU3RyaW5nO1sACnBhcmFtVHlwZXN0ABJbTGphdmEvbGFuZy9DbGFzcztMAAtzZXJ2aWNlSW5mb3QAKkxvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL1NlcnZpY2VJbmZvO3hwAHVyABNbTGphdmEubGFuZy5PYmplY3Q7kM5YnxBzKWwCAAB4cAAAAAFzcgBDY29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLnBsdWdpbnMuY2x1c3RlcnN1cHBvcnQuQ2x1c3Rlck5vdGlmaWNhdGlvbhY+6VckvG9pAgACSQAEdHlwZUwABGRhdGFxAH4AA3hwAAAAAnQAKURvY3VtZW50VHlwZVJ1bGVDYWNoZTpSb3V0aW5nUnVsZURvY3VtZW50cHB0AAZpbnZva2V1cgASW0xqYXZhLmxhbmcuQ2xhc3M7qxbXrsvNWpkCAAB4cAAAAAF2cgAUamF2YS5pby5TZXJpYWxpemFibGUQm2EN15tk7QIAAHhwc3IAKG9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuU2VydmljZUluZm/FFyO2KQS3ugIAC0wABWFsaXZldAATTGphdmEvbGFuZy9Cb29sZWFuO0wAFGVuZHBvaW50QWx0ZXJuYXRlVXJscQB+AARMAAtlbmRwb2ludFVybHEAfgAETAAKbG9ja1Zlck5icnQAE0xqYXZhL2xhbmcvSW50ZWdlcjtMAA5tZXNzYWdlRW50cnlJZHQAEExqYXZhL2xhbmcvTG9uZztMAAVxbmFtZXQAG0xqYXZheC94bWwvbmFtZXNwYWNlL1FOYW1lO0wAGnNlcmlhbGl6ZWRTZXJ2aWNlTmFtZXNwYWNlcQB+AARMAAhzZXJ2ZXJJcHEAfgAETAARc2VydmljZURlZmluaXRpb250ADBMb3JnL2t1YWxpL3JpY2Uva3NiL21lc3NhZ2luZy9TZXJ2aWNlRGVmaW5pdGlvbjtMAAtzZXJ2aWNlTmFtZXEAfgAETAAQc2VydmljZU5hbWVzcGFjZXEAfgAEeHBzcgARamF2YS5sYW5nLkJvb2xlYW7NIHKA1Zz67gIAAVoABXZhbHVleHABcHQAQGh0dHA6Ly9sb2NhbGhvc3Q6ODA4MC9rci1kZXYvcmVtb3RpbmcvT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2VzcgARamF2YS5sYW5nLkludGVnZXIS4qCk94GHOAIAAUkABXZhbHVleHIAEGphdmEubGFuZy5OdW1iZXKGrJUdC5TgiwIAAHhwAAAAAXNyAA5qYXZhLmxhbmcuTG9uZzuL5JDMjyPfAgABSgAFdmFsdWV4cQB+AB0AAAAAAAAPfXNyABlqYXZheC54bWwubmFtZXNwYWNlLlFOYW1lgW2oLfw73WwCAANMAAlsb2NhbFBhcnRxAH4ABEwADG5hbWVzcGFjZVVSSXEAfgAETAAGcHJlZml4cQB+AAR4cHQAGk9TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNldAAAcQB+ACR0B3RyTzBBQlhOeUFESnZjbWN1YTNWaGJHa3VjbWxqWlM1cmMySXViV1Z6YzJGbmFXNW5Ma3BoZG1GVFpYSjJhV05sUkdWbWFXNXBkR2x2YnJibkRWOUJOR3p4QWdBQlRBQVJjMlZ5ZG1salpVbHVkR1Z5Wm1GalpYTjBBQkJNYW1GMllTOTFkR2xzTDB4cGMzUTdlSElBTG05eVp5NXJkV0ZzYVM1eWFXTmxMbXR6WWk1dFpYTnpZV2RwYm1jdVUyVnlkbWxqWlVSbFptbHVhWFJwYjI0QW13SlBXTjBwZmdJQURVd0FDMkoxYzFObFkzVnlhWFI1ZEFBVFRHcGhkbUV2YkdGdVp5OUNiMjlzWldGdU8wd0FEMk55WldSbGJuUnBZV3h6Vkhsd1pYUUFURXh2Y21jdmEzVmhiR2t2Y21salpTOWpiM0psTDNObFkzVnlhWFI1TDJOeVpXUmxiblJwWVd4ekwwTnlaV1JsYm5ScFlXeHpVMjkxY21ObEpFTnlaV1JsYm5ScFlXeHpWSGx3WlR0TUFCQnNiMk5oYkZObGNuWnBZMlZPWVcxbGRBQVNUR3BoZG1FdmJHRnVaeTlUZEhKcGJtYzdUQUFYYldWemMyRm5aVVY0WTJWd2RHbHZia2hoYm1Sc1pYSnhBSDRBQlV3QURHMXBiR3hwYzFSdlRHbDJaWFFBRUV4cVlYWmhMMnhoYm1jdlRHOXVaenRNQUFod2NtbHZjbWwwZVhRQUUweHFZWFpoTDJ4aGJtY3ZTVzUwWldkbGNqdE1BQVZ4ZFdWMVpYRUFmZ0FEVEFBTmNtVjBjbmxCZEhSbGJYQjBjM0VBZmdBSFRBQUhjMlZ5ZG1salpYUUFFa3hxWVhaaEwyeGhibWN2VDJKcVpXTjBPMHdBRDNObGNuWnBZMlZGYm1SUWIybHVkSFFBRGt4cVlYWmhMMjVsZEM5VlVrdzdUQUFUYzJWeWRtbGpaVTVoYldWVGNHRmpaVlZTU1hFQWZnQUZUQUFRYzJWeWRtbGpaVTVoYldWemNHRmpaWEVBZmdBRlRBQUxjMlZ5ZG1salpWQmhkR2h4QUg0QUJYaHdjM0lBRVdwaGRtRXViR0Z1Wnk1Q2IyOXNaV0Z1elNCeWdOV2MrdTRDQUFGYUFBVjJZV3gxWlhod0FYQjBBQnBQVTBOaFkyaGxUbTkwYVdacFkyRjBhVzl1VTJWeWRtbGpaWFFBVFc5eVp5NXJkV0ZzYVM1eWFXTmxMbXR6WWk1dFpYTnpZV2RwYm1jdVpYaGpaWEIwYVc5dWFHRnVaR3hwYm1jdVJHVm1ZWFZzZEUxbGMzTmhaMlZGZUdObGNIUnBiMjVJWVc1a2JHVnljM0lBRG1waGRtRXViR0Z1Wnk1TWIyNW5PNHZra015UEk5OENBQUZLQUFWMllXeDFaWGh5QUJCcVlYWmhMbXhoYm1jdVRuVnRZbVZ5aHF5VkhRdVU0SXNDQUFCNGNQLy8vLy8vLy8vL2MzSUFFV3BoZG1FdWJHRnVaeTVKYm5SbFoyVnlFdUtncFBlQmh6Z0NBQUZKQUFWMllXeDFaWGh4QUg0QUVBQUFBQU56Y1FCK0FBc0FjUUIrQUJOd2MzSUFER3BoZG1FdWJtVjBMbFZTVEpZbE56WWEvT1J5QXdBSFNRQUlhR0Z6YUVOdlpHVkpBQVJ3YjNKMFRBQUpZWFYwYUc5eWFYUjVjUUIrQUFWTUFBUm1hV3hsY1FCK0FBVk1BQVJvYjNOMGNRQitBQVZNQUFod2NtOTBiMk52YkhFQWZnQUZUQUFEY21WbWNRQitBQVY0Y1AvLy8vOEFBQitRZEFBT2JHOWpZV3hvYjNOME9qZ3dPREIwQUNzdmEzSXRaR1YyTDNKbGJXOTBhVzVuTDA5VFEyRmphR1ZPYjNScFptbGpZWFJwYjI1VFpYSjJhV05sZEFBSmJHOWpZV3hvYjNOMGRBQUVhSFIwY0hCNGRBQUFkQUFHVkZKQlZrVk1kQUFCTDNOeUFCTnFZWFpoTG5WMGFXd3VRWEp5WVhsTWFYTjBlSUhTSFpuSFlaMERBQUZKQUFSemFYcGxlSEFBQUFBRmR3UUFBQUFLZEFBemIzSm5MbXQxWVd4';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 377    FOR UPDATE;        
    buffer := 'cExuSnBZMlV1YTNOaUxtMWxjM05oWjJsdVp5NXpaWEoyYVdObExrdFRRa3BoZG1GVFpYSjJhV05sZEFBOFkyOXRMbTl3Wlc1emVXMXdhRzl1ZVM1dmMyTmhZMmhsTG1KaGMyVXVaWFpsYm5SekxrTmhZMmhsUlc1MGNubEZkbVZ1ZEV4cGMzUmxibVZ5ZEFBM1kyOXRMbTl3Wlc1emVXMXdhRzl1ZVM1dmMyTmhZMmhsTG1KaGMyVXVaWFpsYm5SekxrTmhZMmhsUlhabGJuUk1hWE4wWlc1bGNuUUFGMnBoZG1FdWRYUnBiQzVGZG1WdWRFeHBjM1JsYm1WeWRBQXNZMjl0TG05d1pXNXplVzF3YUc5dWVTNXZjMk5oWTJobExtSmhjMlV1VEdsbVpXTjVZMnhsUVhkaGNtVjR0AA0xMjkuMTg2Ljc5LjQ1c3IAMm9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuSmF2YVNlcnZpY2VEZWZpbml0aW9utucNX0E0bPECAAFMABFzZXJ2aWNlSW50ZXJmYWNlc3QAEExqYXZhL3V0aWwvTGlzdDt4cgAub3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5TZXJ2aWNlRGVmaW5pdGlvbgCbAk9Y3Sl+AgANTAALYnVzU2VjdXJpdHlxAH4AE0wAD2NyZWRlbnRpYWxzVHlwZXQATExvcmcva3VhbGkvcmljZS9jb3JlL3NlY3VyaXR5L2NyZWRlbnRpYWxzL0NyZWRlbnRpYWxzU291cmNlJENyZWRlbnRpYWxzVHlwZTtMABBsb2NhbFNlcnZpY2VOYW1lcQB+AARMABdtZXNzYWdlRXhjZXB0aW9uSGFuZGxlcnEAfgAETAAMbWlsbGlzVG9MaXZlcQB+ABVMAAhwcmlvcml0eXEAfgAUTAAFcXVldWVxAH4AE0wADXJldHJ5QXR0ZW1wdHNxAH4AFEwAB3NlcnZpY2V0ABJMamF2YS9sYW5nL09iamVjdDtMAA9zZXJ2aWNlRW5kUG9pbnR0AA5MamF2YS9uZXQvVVJMO0wAE3NlcnZpY2VOYW1lU3BhY2VVUklxAH4ABEwAEHNlcnZpY2VOYW1lc3BhY2VxAH4ABEwAC3NlcnZpY2VQYXRocQB+AAR4cHNxAH4AGQFwdAAaT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2V0AE1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLmV4Y2VwdGlvbmhhbmRsaW5nLkRlZmF1bHRNZXNzYWdlRXhjZXB0aW9uSGFuZGxlcnNxAH4AH///////////c3EAfgAcAAAAA3NxAH4AGQBxAH4AMnBzcgAMamF2YS5uZXQuVVJMliU3Nhr85HIDAAdJAAhoYXNoQ29kZUkABHBvcnRMAAlhdXRob3JpdHlxAH4ABEwABGZpbGVxAH4ABEwABGhvc3RxAH4ABEwACHByb3RvY29scQB+AARMAANyZWZxAH4ABHhwaRuioAAAH5B0AA5sb2NhbGhvc3Q6ODA4MHQAKy9rci1kZXYvcmVtb3RpbmcvT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2V0AAlsb2NhbGhvc3R0AARodHRwcHh0AAB0AAZUUkFWRUx0AAEvc3IAE2phdmEudXRpbC5BcnJheUxpc3R4gdIdmcdhnQMAAUkABHNpemV4cAAAAAV3BAAAAAp0ADNvcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLnNlcnZpY2UuS1NCSmF2YVNlcnZpY2V0ADxjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5ldmVudHMuQ2FjaGVFbnRyeUV2ZW50TGlzdGVuZXJ0ADdjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5ldmVudHMuQ2FjaGVFdmVudExpc3RlbmVydAAXamF2YS51dGlsLkV2ZW50TGlzdGVuZXJ0ACxjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5MaWZlY3ljbGVBd2FyZXhxAH4AI3QABlRSQVZFTA==';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
INSERT INTO KRSB_MSG_PYLD_T (MSG_PYLD,MSG_QUE_ID)
  VALUES (EMPTY_CLOB(),378)
/
-- Length: 6108
-- Chunks: 2
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 378    FOR UPDATE;        
    buffer := 'rO0ABXNyAC1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLkFzeW5jaHJvbm91c0NhbGzxnQ5Y0t0lZQIAB1oAFWlnbm9yZVN0b3JlQW5kRm9yd2FyZFsACWFyZ3VtZW50c3QAE1tMamF2YS9sYW5nL09iamVjdDtMAAhjYWxsYmFja3QAM0xvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL0FzeW5jaHJvbm91c0NhbGxiYWNrO0wAB2NvbnRleHR0ABZMamF2YS9pby9TZXJpYWxpemFibGU7TAAKbWV0aG9kTmFtZXQAEkxqYXZhL2xhbmcvU3RyaW5nO1sACnBhcmFtVHlwZXN0ABJbTGphdmEvbGFuZy9DbGFzcztMAAtzZXJ2aWNlSW5mb3QAKkxvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL1NlcnZpY2VJbmZvO3hwAHVyABNbTGphdmEubGFuZy5PYmplY3Q7kM5YnxBzKWwCAAB4cAAAAAFzcgBDY29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLnBsdWdpbnMuY2x1c3RlcnN1cHBvcnQuQ2x1c3Rlck5vdGlmaWNhdGlvbhY+6VckvG9pAgACSQAEdHlwZUwABGRhdGFxAH4AA3hwAAAAAnQAMkRvY3VtZW50VHlwZVJ1bGVDYWNoZTpOYW1lc3BhY2VNYWludGVuYW5jZURvY3VtZW50cHB0AAZpbnZva2V1cgASW0xqYXZhLmxhbmcuQ2xhc3M7qxbXrsvNWpkCAAB4cAAAAAF2cgAUamF2YS5pby5TZXJpYWxpemFibGUQm2EN15tk7QIAAHhwc3IAKG9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuU2VydmljZUluZm/FFyO2KQS3ugIAC0wABWFsaXZldAATTGphdmEvbGFuZy9Cb29sZWFuO0wAFGVuZHBvaW50QWx0ZXJuYXRlVXJscQB+AARMAAtlbmRwb2ludFVybHEAfgAETAAKbG9ja1Zlck5icnQAE0xqYXZhL2xhbmcvSW50ZWdlcjtMAA5tZXNzYWdlRW50cnlJZHQAEExqYXZhL2xhbmcvTG9uZztMAAVxbmFtZXQAG0xqYXZheC94bWwvbmFtZXNwYWNlL1FOYW1lO0wAGnNlcmlhbGl6ZWRTZXJ2aWNlTmFtZXNwYWNlcQB+AARMAAhzZXJ2ZXJJcHEAfgAETAARc2VydmljZURlZmluaXRpb250ADBMb3JnL2t1YWxpL3JpY2Uva3NiL21lc3NhZ2luZy9TZXJ2aWNlRGVmaW5pdGlvbjtMAAtzZXJ2aWNlTmFtZXEAfgAETAAQc2VydmljZU5hbWVzcGFjZXEAfgAEeHBzcgARamF2YS5sYW5nLkJvb2xlYW7NIHKA1Zz67gIAAVoABXZhbHVleHABcHQAQGh0dHA6Ly9sb2NhbGhvc3Q6ODA4MC9rci1kZXYvcmVtb3RpbmcvT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2VzcgARamF2YS5sYW5nLkludGVnZXIS4qCk94GHOAIAAUkABXZhbHVleHIAEGphdmEubGFuZy5OdW1iZXKGrJUdC5TgiwIAAHhwAAAAAXNyAA5qYXZhLmxhbmcuTG9uZzuL5JDMjyPfAgABSgAFdmFsdWV4cQB+AB0AAAAAAAAPfXNyABlqYXZheC54bWwubmFtZXNwYWNlLlFOYW1lgW2oLfw73WwCAANMAAlsb2NhbFBhcnRxAH4ABEwADG5hbWVzcGFjZVVSSXEAfgAETAAGcHJlZml4cQB+AAR4cHQAGk9TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNldAAAcQB+ACR0B3RyTzBBQlhOeUFESnZjbWN1YTNWaGJHa3VjbWxqWlM1cmMySXViV1Z6YzJGbmFXNW5Ma3BoZG1GVFpYSjJhV05sUkdWbWFXNXBkR2x2YnJibkRWOUJOR3p4QWdBQlRBQVJjMlZ5ZG1salpVbHVkR1Z5Wm1GalpYTjBBQkJNYW1GMllTOTFkR2xzTDB4cGMzUTdlSElBTG05eVp5NXJkV0ZzYVM1eWFXTmxMbXR6WWk1dFpYTnpZV2RwYm1jdVUyVnlkbWxqWlVSbFptbHVhWFJwYjI0QW13SlBXTjBwZmdJQURVd0FDMkoxYzFObFkzVnlhWFI1ZEFBVFRHcGhkbUV2YkdGdVp5OUNiMjlzWldGdU8wd0FEMk55WldSbGJuUnBZV3h6Vkhsd1pYUUFURXh2Y21jdmEzVmhiR2t2Y21salpTOWpiM0psTDNObFkzVnlhWFI1TDJOeVpXUmxiblJwWVd4ekwwTnlaV1JsYm5ScFlXeHpVMjkxY21ObEpFTnlaV1JsYm5ScFlXeHpWSGx3WlR0TUFCQnNiMk5oYkZObGNuWnBZMlZPWVcxbGRBQVNUR3BoZG1FdmJHRnVaeTlUZEhKcGJtYzdUQUFYYldWemMyRm5aVVY0WTJWd2RHbHZia2hoYm1Sc1pYSnhBSDRBQlV3QURHMXBiR3hwYzFSdlRHbDJaWFFBRUV4cVlYWmhMMnhoYm1jdlRHOXVaenRNQUFod2NtbHZjbWwwZVhRQUUweHFZWFpoTDJ4aGJtY3ZTVzUwWldkbGNqdE1BQVZ4ZFdWMVpYRUFmZ0FEVEFBTmNtVjBjbmxCZEhSbGJYQjBjM0VBZmdBSFRBQUhjMlZ5ZG1salpYUUFFa3hxWVhaaEwyeGhibWN2VDJKcVpXTjBPMHdBRDNObGNuWnBZMlZGYm1SUWIybHVkSFFBRGt4cVlYWmhMMjVsZEM5VlVrdzdUQUFUYzJWeWRtbGpaVTVoYldWVGNHRmpaVlZTU1hFQWZnQUZUQUFRYzJWeWRtbGpaVTVoYldWemNHRmpaWEVBZmdBRlRBQUxjMlZ5ZG1salpWQmhkR2h4QUg0QUJYaHdjM0lBRVdwaGRtRXViR0Z1Wnk1Q2IyOXNaV0Z1elNCeWdOV2MrdTRDQUFGYUFBVjJZV3gxWlhod0FYQjBBQnBQVTBOaFkyaGxUbTkwYVdacFkyRjBhVzl1VTJWeWRtbGpaWFFBVFc5eVp5NXJkV0ZzYVM1eWFXTmxMbXR6WWk1dFpYTnpZV2RwYm1jdVpYaGpaWEIwYVc5dWFHRnVaR3hwYm1jdVJHVm1ZWFZzZEUxbGMzTmhaMlZGZUdObGNIUnBiMjVJWVc1a2JHVnljM0lBRG1waGRtRXViR0Z1Wnk1TWIyNW5PNHZra015UEk5OENBQUZLQUFWMllXeDFaWGh5QUJCcVlYWmhMbXhoYm1jdVRuVnRZbVZ5aHF5VkhRdVU0SXNDQUFCNGNQLy8vLy8vLy8vL2MzSUFFV3BoZG1FdWJHRnVaeTVKYm5SbFoyVnlFdUtncFBlQmh6Z0NBQUZKQUFWMllXeDFaWGh4QUg0QUVBQUFBQU56Y1FCK0FBc0FjUUIrQUJOd2MzSUFER3BoZG1FdWJtVjBMbFZTVEpZbE56WWEvT1J5QXdBSFNRQUlhR0Z6YUVOdlpHVkpBQVJ3YjNKMFRBQUpZWFYwYUc5eWFYUjVjUUIrQUFWTUFBUm1hV3hsY1FCK0FBVk1BQVJvYjNOMGNRQitBQVZNQUFod2NtOTBiMk52YkhFQWZnQUZUQUFEY21WbWNRQitBQVY0Y1AvLy8vOEFBQitRZEFBT2JHOWpZV3hvYjNOME9qZ3dPREIwQUNzdmEzSXRaR1YyTDNKbGJXOTBhVzVuTDA5VFEyRmphR1ZPYjNScFptbGpZWFJwYjI1VFpYSjJhV05sZEFBSmJHOWpZV3hvYjNOMGRBQUVhSFIwY0hCNGRBQUFkQUFHVkZKQlZrVk1kQUFCTDNOeUFCTnFZWFpoTG5WMGFXd3VRWEp5WVhsTWFYTjBlSUhTSFpuSFlaMERBQUZKQUFSemFYcGxlSEFBQUFBRmR3UUFBQUFLZEFBemIz';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 378    FOR UPDATE;        
    buffer := 'Sm5MbXQxWVd4cExuSnBZMlV1YTNOaUxtMWxjM05oWjJsdVp5NXpaWEoyYVdObExrdFRRa3BoZG1GVFpYSjJhV05sZEFBOFkyOXRMbTl3Wlc1emVXMXdhRzl1ZVM1dmMyTmhZMmhsTG1KaGMyVXVaWFpsYm5SekxrTmhZMmhsUlc1MGNubEZkbVZ1ZEV4cGMzUmxibVZ5ZEFBM1kyOXRMbTl3Wlc1emVXMXdhRzl1ZVM1dmMyTmhZMmhsTG1KaGMyVXVaWFpsYm5SekxrTmhZMmhsUlhabGJuUk1hWE4wWlc1bGNuUUFGMnBoZG1FdWRYUnBiQzVGZG1WdWRFeHBjM1JsYm1WeWRBQXNZMjl0TG05d1pXNXplVzF3YUc5dWVTNXZjMk5oWTJobExtSmhjMlV1VEdsbVpXTjVZMnhsUVhkaGNtVjR0AA0xMjkuMTg2Ljc5LjQ1c3IAMm9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuSmF2YVNlcnZpY2VEZWZpbml0aW9utucNX0E0bPECAAFMABFzZXJ2aWNlSW50ZXJmYWNlc3QAEExqYXZhL3V0aWwvTGlzdDt4cgAub3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5TZXJ2aWNlRGVmaW5pdGlvbgCbAk9Y3Sl+AgANTAALYnVzU2VjdXJpdHlxAH4AE0wAD2NyZWRlbnRpYWxzVHlwZXQATExvcmcva3VhbGkvcmljZS9jb3JlL3NlY3VyaXR5L2NyZWRlbnRpYWxzL0NyZWRlbnRpYWxzU291cmNlJENyZWRlbnRpYWxzVHlwZTtMABBsb2NhbFNlcnZpY2VOYW1lcQB+AARMABdtZXNzYWdlRXhjZXB0aW9uSGFuZGxlcnEAfgAETAAMbWlsbGlzVG9MaXZlcQB+ABVMAAhwcmlvcml0eXEAfgAUTAAFcXVldWVxAH4AE0wADXJldHJ5QXR0ZW1wdHNxAH4AFEwAB3NlcnZpY2V0ABJMamF2YS9sYW5nL09iamVjdDtMAA9zZXJ2aWNlRW5kUG9pbnR0AA5MamF2YS9uZXQvVVJMO0wAE3NlcnZpY2VOYW1lU3BhY2VVUklxAH4ABEwAEHNlcnZpY2VOYW1lc3BhY2VxAH4ABEwAC3NlcnZpY2VQYXRocQB+AAR4cHNxAH4AGQFwdAAaT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2V0AE1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLmV4Y2VwdGlvbmhhbmRsaW5nLkRlZmF1bHRNZXNzYWdlRXhjZXB0aW9uSGFuZGxlcnNxAH4AH///////////c3EAfgAcAAAAA3NxAH4AGQBxAH4AMnBzcgAMamF2YS5uZXQuVVJMliU3Nhr85HIDAAdJAAhoYXNoQ29kZUkABHBvcnRMAAlhdXRob3JpdHlxAH4ABEwABGZpbGVxAH4ABEwABGhvc3RxAH4ABEwACHByb3RvY29scQB+AARMAANyZWZxAH4ABHhwaRuioAAAH5B0AA5sb2NhbGhvc3Q6ODA4MHQAKy9rci1kZXYvcmVtb3RpbmcvT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2V0AAlsb2NhbGhvc3R0AARodHRwcHh0AAB0AAZUUkFWRUx0AAEvc3IAE2phdmEudXRpbC5BcnJheUxpc3R4gdIdmcdhnQMAAUkABHNpemV4cAAAAAV3BAAAAAp0ADNvcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLnNlcnZpY2UuS1NCSmF2YVNlcnZpY2V0ADxjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5ldmVudHMuQ2FjaGVFbnRyeUV2ZW50TGlzdGVuZXJ0ADdjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5ldmVudHMuQ2FjaGVFdmVudExpc3RlbmVydAAXamF2YS51dGlsLkV2ZW50TGlzdGVuZXJ0ACxjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5MaWZlY3ljbGVBd2FyZXhxAH4AI3QABlRSQVZFTA==';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
INSERT INTO KRSB_MSG_PYLD_T (MSG_PYLD,MSG_QUE_ID)
  VALUES (EMPTY_CLOB(),379)
/
-- Length: 6112
-- Chunks: 2
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 379    FOR UPDATE;        
    buffer := 'rO0ABXNyAC1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLkFzeW5jaHJvbm91c0NhbGzxnQ5Y0t0lZQIAB1oAFWlnbm9yZVN0b3JlQW5kRm9yd2FyZFsACWFyZ3VtZW50c3QAE1tMamF2YS9sYW5nL09iamVjdDtMAAhjYWxsYmFja3QAM0xvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL0FzeW5jaHJvbm91c0NhbGxiYWNrO0wAB2NvbnRleHR0ABZMamF2YS9pby9TZXJpYWxpemFibGU7TAAKbWV0aG9kTmFtZXQAEkxqYXZhL2xhbmcvU3RyaW5nO1sACnBhcmFtVHlwZXN0ABJbTGphdmEvbGFuZy9DbGFzcztMAAtzZXJ2aWNlSW5mb3QAKkxvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL1NlcnZpY2VJbmZvO3hwAHVyABNbTGphdmEubGFuZy5PYmplY3Q7kM5YnxBzKWwCAAB4cAAAAAFzcgBDY29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLnBsdWdpbnMuY2x1c3RlcnN1cHBvcnQuQ2x1c3Rlck5vdGlmaWNhdGlvbhY+6VckvG9pAgACSQAEdHlwZUwABGRhdGFxAH4AA3hwAAAAAnQANkRvY3VtZW50VHlwZVJ1bGVDYWNoZTpQYXJhbWV0ZXJUeXBlTWFpbnRlbmFuY2VEb2N1bWVudHBwdAAGaW52b2tldXIAEltMamF2YS5sYW5nLkNsYXNzO6sW167LzVqZAgAAeHAAAAABdnIAFGphdmEuaW8uU2VyaWFsaXphYmxlEJthDdebZO0CAAB4cHNyAChvcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLlNlcnZpY2VJbmZvxRcjtikEt7oCAAtMAAVhbGl2ZXQAE0xqYXZhL2xhbmcvQm9vbGVhbjtMABRlbmRwb2ludEFsdGVybmF0ZVVybHEAfgAETAALZW5kcG9pbnRVcmxxAH4ABEwACmxvY2tWZXJOYnJ0ABNMamF2YS9sYW5nL0ludGVnZXI7TAAObWVzc2FnZUVudHJ5SWR0ABBMamF2YS9sYW5nL0xvbmc7TAAFcW5hbWV0ABtMamF2YXgveG1sL25hbWVzcGFjZS9RTmFtZTtMABpzZXJpYWxpemVkU2VydmljZU5hbWVzcGFjZXEAfgAETAAIc2VydmVySXBxAH4ABEwAEXNlcnZpY2VEZWZpbml0aW9udAAwTG9yZy9rdWFsaS9yaWNlL2tzYi9tZXNzYWdpbmcvU2VydmljZURlZmluaXRpb247TAALc2VydmljZU5hbWVxAH4ABEwAEHNlcnZpY2VOYW1lc3BhY2VxAH4ABHhwc3IAEWphdmEubGFuZy5Cb29sZWFuzSBygNWc+u4CAAFaAAV2YWx1ZXhwAXB0AEBodHRwOi8vbG9jYWxob3N0OjgwODAva3ItZGV2L3JlbW90aW5nL09TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNlc3IAEWphdmEubGFuZy5JbnRlZ2VyEuKgpPeBhzgCAAFJAAV2YWx1ZXhyABBqYXZhLmxhbmcuTnVtYmVyhqyVHQuU4IsCAAB4cAAAAAFzcgAOamF2YS5sYW5nLkxvbmc7i+SQzI8j3wIAAUoABXZhbHVleHEAfgAdAAAAAAAAD31zcgAZamF2YXgueG1sLm5hbWVzcGFjZS5RTmFtZYFtqC38O91sAgADTAAJbG9jYWxQYXJ0cQB+AARMAAxuYW1lc3BhY2VVUklxAH4ABEwABnByZWZpeHEAfgAEeHB0ABpPU0NhY2hlTm90aWZpY2F0aW9uU2VydmljZXQAAHEAfgAkdAd0ck8wQUJYTnlBREp2Y21jdWEzVmhiR2t1Y21salpTNXJjMkl1YldWemMyRm5hVzVuTGtwaGRtRlRaWEoyYVdObFJHVm1hVzVwZEdsdmJyYm5EVjlCTkd6eEFnQUJUQUFSYzJWeWRtbGpaVWx1ZEdWeVptRmpaWE4wQUJCTWFtRjJZUzkxZEdsc0wweHBjM1E3ZUhJQUxtOXlaeTVyZFdGc2FTNXlhV05sTG10ellpNXRaWE56WVdkcGJtY3VVMlZ5ZG1salpVUmxabWx1YVhScGIyNEFtd0pQV04wcGZnSUFEVXdBQzJKMWMxTmxZM1Z5YVhSNWRBQVRUR3BoZG1FdmJHRnVaeTlDYjI5c1pXRnVPMHdBRDJOeVpXUmxiblJwWVd4elZIbHdaWFFBVEV4dmNtY3ZhM1ZoYkdrdmNtbGpaUzlqYjNKbEwzTmxZM1Z5YVhSNUwyTnlaV1JsYm5ScFlXeHpMME55WldSbGJuUnBZV3h6VTI5MWNtTmxKRU55WldSbGJuUnBZV3h6Vkhsd1pUdE1BQkJzYjJOaGJGTmxjblpwWTJWT1lXMWxkQUFTVEdwaGRtRXZiR0Z1Wnk5VGRISnBibWM3VEFBWGJXVnpjMkZuWlVWNFkyVndkR2x2YmtoaGJtUnNaWEp4QUg0QUJVd0FERzFwYkd4cGMxUnZUR2wyWlhRQUVFeHFZWFpoTDJ4aGJtY3ZURzl1Wnp0TUFBaHdjbWx2Y21sMGVYUUFFMHhxWVhaaEwyeGhibWN2U1c1MFpXZGxjanRNQUFWeGRXVjFaWEVBZmdBRFRBQU5jbVYwY25sQmRIUmxiWEIwYzNFQWZnQUhUQUFIYzJWeWRtbGpaWFFBRWt4cVlYWmhMMnhoYm1jdlQySnFaV04wTzB3QUQzTmxjblpwWTJWRmJtUlFiMmx1ZEhRQURreHFZWFpoTDI1bGRDOVZVa3c3VEFBVGMyVnlkbWxqWlU1aGJXVlRjR0ZqWlZWU1NYRUFmZ0FGVEFBUWMyVnlkbWxqWlU1aGJXVnpjR0ZqWlhFQWZnQUZUQUFMYzJWeWRtbGpaVkJoZEdoeEFINEFCWGh3YzNJQUVXcGhkbUV1YkdGdVp5NUNiMjlzWldGdXpTQnlnTldjK3U0Q0FBRmFBQVYyWVd4MVpYaHdBWEIwQUJwUFUwTmhZMmhsVG05MGFXWnBZMkYwYVc5dVUyVnlkbWxqWlhRQVRXOXlaeTVyZFdGc2FTNXlhV05sTG10ellpNXRaWE56WVdkcGJtY3VaWGhqWlhCMGFXOXVhR0Z1Wkd4cGJtY3VSR1ZtWVhWc2RFMWxjM05oWjJWRmVHTmxjSFJwYjI1SVlXNWtiR1Z5YzNJQURtcGhkbUV1YkdGdVp5NU1iMjVuTzR2a2tNeVBJOThDQUFGS0FBVjJZV3gxWlhoeUFCQnFZWFpoTG14aGJtY3VUblZ0WW1WeWhxeVZIUXVVNElzQ0FBQjRjUC8vLy8vLy8vLy9jM0lBRVdwaGRtRXViR0Z1Wnk1SmJuUmxaMlZ5RXVLZ3BQZUJoemdDQUFGSkFBVjJZV3gxWlhoeEFINEFFQUFBQUFOemNRQitBQXNBY1FCK0FCTndjM0lBREdwaGRtRXVibVYwTGxWU1RKWWxOellhL09SeUF3QUhTUUFJYUdGemFFTnZaR1ZKQUFSd2IzSjBUQUFKWVhWMGFHOXlhWFI1Y1FCK0FBVk1BQVJtYVd4bGNRQitBQVZNQUFSb2IzTjBjUUIrQUFWTUFBaHdjbTkwYjJOdmJIRUFmZ0FGVEFBRGNtVm1jUUIrQUFWNGNQLy8vLzhBQUIrUWRBQU9iRzlqWVd4b2IzTjBPamd3T0RCMEFDc3ZhM0l0WkdWMkwzSmxiVzkwYVc1bkwwOVRRMkZqYUdWT2IzUnBabWxqWVhScGIyNVRaWEoyYVdObGRBQUpiRzlqWVd4b2IzTjBkQUFFYUhSMGNIQjRkQUFBZEFBR1ZGSkJWa1ZNZEFBQkwzTnlBQk5xWVhaaExuVjBhV3d1UVhKeVlYbE1hWE4wZUlIU0habkhZWjBEQUFGSkFBUnphWHBsZUhBQUFBQUZkd1FBQUFBS2RB';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 379    FOR UPDATE;        
    buffer := 'QXpiM0puTG10MVlXeHBMbkpwWTJVdWEzTmlMbTFsYzNOaFoybHVaeTV6WlhKMmFXTmxMa3RUUWtwaGRtRlRaWEoyYVdObGRBQThZMjl0TG05d1pXNXplVzF3YUc5dWVTNXZjMk5oWTJobExtSmhjMlV1WlhabGJuUnpMa05oWTJobFJXNTBjbmxGZG1WdWRFeHBjM1JsYm1WeWRBQTNZMjl0TG05d1pXNXplVzF3YUc5dWVTNXZjMk5oWTJobExtSmhjMlV1WlhabGJuUnpMa05oWTJobFJYWmxiblJNYVhOMFpXNWxjblFBRjJwaGRtRXVkWFJwYkM1RmRtVnVkRXhwYzNSbGJtVnlkQUFzWTI5dExtOXdaVzV6ZVcxd2FHOXVlUzV2YzJOaFkyaGxMbUpoYzJVdVRHbG1aV041WTJ4bFFYZGhjbVY0dAANMTI5LjE4Ni43OS40NXNyADJvcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLkphdmFTZXJ2aWNlRGVmaW5pdGlvbrbnDV9BNGzxAgABTAARc2VydmljZUludGVyZmFjZXN0ABBMamF2YS91dGlsL0xpc3Q7eHIALm9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuU2VydmljZURlZmluaXRpb24AmwJPWN0pfgIADUwAC2J1c1NlY3VyaXR5cQB+ABNMAA9jcmVkZW50aWFsc1R5cGV0AExMb3JnL2t1YWxpL3JpY2UvY29yZS9zZWN1cml0eS9jcmVkZW50aWFscy9DcmVkZW50aWFsc1NvdXJjZSRDcmVkZW50aWFsc1R5cGU7TAAQbG9jYWxTZXJ2aWNlTmFtZXEAfgAETAAXbWVzc2FnZUV4Y2VwdGlvbkhhbmRsZXJxAH4ABEwADG1pbGxpc1RvTGl2ZXEAfgAVTAAIcHJpb3JpdHlxAH4AFEwABXF1ZXVlcQB+ABNMAA1yZXRyeUF0dGVtcHRzcQB+ABRMAAdzZXJ2aWNldAASTGphdmEvbGFuZy9PYmplY3Q7TAAPc2VydmljZUVuZFBvaW50dAAOTGphdmEvbmV0L1VSTDtMABNzZXJ2aWNlTmFtZVNwYWNlVVJJcQB+AARMABBzZXJ2aWNlTmFtZXNwYWNlcQB+AARMAAtzZXJ2aWNlUGF0aHEAfgAEeHBzcQB+ABkBcHQAGk9TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNldABNb3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5leGNlcHRpb25oYW5kbGluZy5EZWZhdWx0TWVzc2FnZUV4Y2VwdGlvbkhhbmRsZXJzcQB+AB///////////3NxAH4AHAAAAANzcQB+ABkAcQB+ADJwc3IADGphdmEubmV0LlVSTJYlNzYa/ORyAwAHSQAIaGFzaENvZGVJAARwb3J0TAAJYXV0aG9yaXR5cQB+AARMAARmaWxlcQB+AARMAARob3N0cQB+AARMAAhwcm90b2NvbHEAfgAETAADcmVmcQB+AAR4cGkboqAAAB+QdAAObG9jYWxob3N0OjgwODB0ACsva3ItZGV2L3JlbW90aW5nL09TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNldAAJbG9jYWxob3N0dAAEaHR0cHB4dAAAdAAGVFJBVkVMdAABL3NyABNqYXZhLnV0aWwuQXJyYXlMaXN0eIHSHZnHYZ0DAAFJAARzaXpleHAAAAAFdwQAAAAKdAAzb3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5zZXJ2aWNlLktTQkphdmFTZXJ2aWNldAA8Y29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLmJhc2UuZXZlbnRzLkNhY2hlRW50cnlFdmVudExpc3RlbmVydAA3Y29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLmJhc2UuZXZlbnRzLkNhY2hlRXZlbnRMaXN0ZW5lcnQAF2phdmEudXRpbC5FdmVudExpc3RlbmVydAAsY29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLmJhc2UuTGlmZWN5Y2xlQXdhcmV4cQB+ACN0AAZUUkFWRUw=';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
INSERT INTO KRSB_MSG_PYLD_T (MSG_PYLD,MSG_QUE_ID)
  VALUES (EMPTY_CLOB(),380)
/
-- Length: 6120
-- Chunks: 2
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 380    FOR UPDATE;        
    buffer := 'rO0ABXNyAC1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLkFzeW5jaHJvbm91c0NhbGzxnQ5Y0t0lZQIAB1oAFWlnbm9yZVN0b3JlQW5kRm9yd2FyZFsACWFyZ3VtZW50c3QAE1tMamF2YS9sYW5nL09iamVjdDtMAAhjYWxsYmFja3QAM0xvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL0FzeW5jaHJvbm91c0NhbGxiYWNrO0wAB2NvbnRleHR0ABZMamF2YS9pby9TZXJpYWxpemFibGU7TAAKbWV0aG9kTmFtZXQAEkxqYXZhL2xhbmcvU3RyaW5nO1sACnBhcmFtVHlwZXN0ABJbTGphdmEvbGFuZy9DbGFzcztMAAtzZXJ2aWNlSW5mb3QAKkxvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL1NlcnZpY2VJbmZvO3hwAHVyABNbTGphdmEubGFuZy5PYmplY3Q7kM5YnxBzKWwCAAB4cAAAAAFzcgBDY29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLnBsdWdpbnMuY2x1c3RlcnN1cHBvcnQuQ2x1c3Rlck5vdGlmaWNhdGlvbhY+6VckvG9pAgACSQAEdHlwZUwABGRhdGFxAH4AA3hwAAAAAnQAPERvY3VtZW50VHlwZVJ1bGVDYWNoZTpQYXJhbWV0ZXJEZXRhaWxUeXBlTWFpbnRlbmFuY2VEb2N1bWVudHBwdAAGaW52b2tldXIAEltMamF2YS5sYW5nLkNsYXNzO6sW167LzVqZAgAAeHAAAAABdnIAFGphdmEuaW8uU2VyaWFsaXphYmxlEJthDdebZO0CAAB4cHNyAChvcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLlNlcnZpY2VJbmZvxRcjtikEt7oCAAtMAAVhbGl2ZXQAE0xqYXZhL2xhbmcvQm9vbGVhbjtMABRlbmRwb2ludEFsdGVybmF0ZVVybHEAfgAETAALZW5kcG9pbnRVcmxxAH4ABEwACmxvY2tWZXJOYnJ0ABNMamF2YS9sYW5nL0ludGVnZXI7TAAObWVzc2FnZUVudHJ5SWR0ABBMamF2YS9sYW5nL0xvbmc7TAAFcW5hbWV0ABtMamF2YXgveG1sL25hbWVzcGFjZS9RTmFtZTtMABpzZXJpYWxpemVkU2VydmljZU5hbWVzcGFjZXEAfgAETAAIc2VydmVySXBxAH4ABEwAEXNlcnZpY2VEZWZpbml0aW9udAAwTG9yZy9rdWFsaS9yaWNlL2tzYi9tZXNzYWdpbmcvU2VydmljZURlZmluaXRpb247TAALc2VydmljZU5hbWVxAH4ABEwAEHNlcnZpY2VOYW1lc3BhY2VxAH4ABHhwc3IAEWphdmEubGFuZy5Cb29sZWFuzSBygNWc+u4CAAFaAAV2YWx1ZXhwAXB0AEBodHRwOi8vbG9jYWxob3N0OjgwODAva3ItZGV2L3JlbW90aW5nL09TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNlc3IAEWphdmEubGFuZy5JbnRlZ2VyEuKgpPeBhzgCAAFJAAV2YWx1ZXhyABBqYXZhLmxhbmcuTnVtYmVyhqyVHQuU4IsCAAB4cAAAAAFzcgAOamF2YS5sYW5nLkxvbmc7i+SQzI8j3wIAAUoABXZhbHVleHEAfgAdAAAAAAAAD31zcgAZamF2YXgueG1sLm5hbWVzcGFjZS5RTmFtZYFtqC38O91sAgADTAAJbG9jYWxQYXJ0cQB+AARMAAxuYW1lc3BhY2VVUklxAH4ABEwABnByZWZpeHEAfgAEeHB0ABpPU0NhY2hlTm90aWZpY2F0aW9uU2VydmljZXQAAHEAfgAkdAd0ck8wQUJYTnlBREp2Y21jdWEzVmhiR2t1Y21salpTNXJjMkl1YldWemMyRm5hVzVuTGtwaGRtRlRaWEoyYVdObFJHVm1hVzVwZEdsdmJyYm5EVjlCTkd6eEFnQUJUQUFSYzJWeWRtbGpaVWx1ZEdWeVptRmpaWE4wQUJCTWFtRjJZUzkxZEdsc0wweHBjM1E3ZUhJQUxtOXlaeTVyZFdGc2FTNXlhV05sTG10ellpNXRaWE56WVdkcGJtY3VVMlZ5ZG1salpVUmxabWx1YVhScGIyNEFtd0pQV04wcGZnSUFEVXdBQzJKMWMxTmxZM1Z5YVhSNWRBQVRUR3BoZG1FdmJHRnVaeTlDYjI5c1pXRnVPMHdBRDJOeVpXUmxiblJwWVd4elZIbHdaWFFBVEV4dmNtY3ZhM1ZoYkdrdmNtbGpaUzlqYjNKbEwzTmxZM1Z5YVhSNUwyTnlaV1JsYm5ScFlXeHpMME55WldSbGJuUnBZV3h6VTI5MWNtTmxKRU55WldSbGJuUnBZV3h6Vkhsd1pUdE1BQkJzYjJOaGJGTmxjblpwWTJWT1lXMWxkQUFTVEdwaGRtRXZiR0Z1Wnk5VGRISnBibWM3VEFBWGJXVnpjMkZuWlVWNFkyVndkR2x2YmtoaGJtUnNaWEp4QUg0QUJVd0FERzFwYkd4cGMxUnZUR2wyWlhRQUVFeHFZWFpoTDJ4aGJtY3ZURzl1Wnp0TUFBaHdjbWx2Y21sMGVYUUFFMHhxWVhaaEwyeGhibWN2U1c1MFpXZGxjanRNQUFWeGRXVjFaWEVBZmdBRFRBQU5jbVYwY25sQmRIUmxiWEIwYzNFQWZnQUhUQUFIYzJWeWRtbGpaWFFBRWt4cVlYWmhMMnhoYm1jdlQySnFaV04wTzB3QUQzTmxjblpwWTJWRmJtUlFiMmx1ZEhRQURreHFZWFpoTDI1bGRDOVZVa3c3VEFBVGMyVnlkbWxqWlU1aGJXVlRjR0ZqWlZWU1NYRUFmZ0FGVEFBUWMyVnlkbWxqWlU1aGJXVnpjR0ZqWlhFQWZnQUZUQUFMYzJWeWRtbGpaVkJoZEdoeEFINEFCWGh3YzNJQUVXcGhkbUV1YkdGdVp5NUNiMjlzWldGdXpTQnlnTldjK3U0Q0FBRmFBQVYyWVd4MVpYaHdBWEIwQUJwUFUwTmhZMmhsVG05MGFXWnBZMkYwYVc5dVUyVnlkbWxqWlhRQVRXOXlaeTVyZFdGc2FTNXlhV05sTG10ellpNXRaWE56WVdkcGJtY3VaWGhqWlhCMGFXOXVhR0Z1Wkd4cGJtY3VSR1ZtWVhWc2RFMWxjM05oWjJWRmVHTmxjSFJwYjI1SVlXNWtiR1Z5YzNJQURtcGhkbUV1YkdGdVp5NU1iMjVuTzR2a2tNeVBJOThDQUFGS0FBVjJZV3gxWlhoeUFCQnFZWFpoTG14aGJtY3VUblZ0WW1WeWhxeVZIUXVVNElzQ0FBQjRjUC8vLy8vLy8vLy9jM0lBRVdwaGRtRXViR0Z1Wnk1SmJuUmxaMlZ5RXVLZ3BQZUJoemdDQUFGSkFBVjJZV3gxWlhoeEFINEFFQUFBQUFOemNRQitBQXNBY1FCK0FCTndjM0lBREdwaGRtRXVibVYwTGxWU1RKWWxOellhL09SeUF3QUhTUUFJYUdGemFFTnZaR1ZKQUFSd2IzSjBUQUFKWVhWMGFHOXlhWFI1Y1FCK0FBVk1BQVJtYVd4bGNRQitBQVZNQUFSb2IzTjBjUUIrQUFWTUFBaHdjbTkwYjJOdmJIRUFmZ0FGVEFBRGNtVm1jUUIrQUFWNGNQLy8vLzhBQUIrUWRBQU9iRzlqWVd4b2IzTjBPamd3T0RCMEFDc3ZhM0l0WkdWMkwzSmxiVzkwYVc1bkwwOVRRMkZqYUdWT2IzUnBabWxqWVhScGIyNVRaWEoyYVdObGRBQUpiRzlqWVd4b2IzTjBkQUFFYUhSMGNIQjRkQUFBZEFBR1ZGSkJWa1ZNZEFBQkwzTnlBQk5xWVhaaExuVjBhV3d1UVhKeVlYbE1hWE4wZUlIU0habkhZWjBEQUFGSkFBUnphWHBsZUhBQUFBQUZkd1FB';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 380    FOR UPDATE;        
    buffer := 'QUFBS2RBQXpiM0puTG10MVlXeHBMbkpwWTJVdWEzTmlMbTFsYzNOaFoybHVaeTV6WlhKMmFXTmxMa3RUUWtwaGRtRlRaWEoyYVdObGRBQThZMjl0TG05d1pXNXplVzF3YUc5dWVTNXZjMk5oWTJobExtSmhjMlV1WlhabGJuUnpMa05oWTJobFJXNTBjbmxGZG1WdWRFeHBjM1JsYm1WeWRBQTNZMjl0TG05d1pXNXplVzF3YUc5dWVTNXZjMk5oWTJobExtSmhjMlV1WlhabGJuUnpMa05oWTJobFJYWmxiblJNYVhOMFpXNWxjblFBRjJwaGRtRXVkWFJwYkM1RmRtVnVkRXhwYzNSbGJtVnlkQUFzWTI5dExtOXdaVzV6ZVcxd2FHOXVlUzV2YzJOaFkyaGxMbUpoYzJVdVRHbG1aV041WTJ4bFFYZGhjbVY0dAANMTI5LjE4Ni43OS40NXNyADJvcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLkphdmFTZXJ2aWNlRGVmaW5pdGlvbrbnDV9BNGzxAgABTAARc2VydmljZUludGVyZmFjZXN0ABBMamF2YS91dGlsL0xpc3Q7eHIALm9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuU2VydmljZURlZmluaXRpb24AmwJPWN0pfgIADUwAC2J1c1NlY3VyaXR5cQB+ABNMAA9jcmVkZW50aWFsc1R5cGV0AExMb3JnL2t1YWxpL3JpY2UvY29yZS9zZWN1cml0eS9jcmVkZW50aWFscy9DcmVkZW50aWFsc1NvdXJjZSRDcmVkZW50aWFsc1R5cGU7TAAQbG9jYWxTZXJ2aWNlTmFtZXEAfgAETAAXbWVzc2FnZUV4Y2VwdGlvbkhhbmRsZXJxAH4ABEwADG1pbGxpc1RvTGl2ZXEAfgAVTAAIcHJpb3JpdHlxAH4AFEwABXF1ZXVlcQB+ABNMAA1yZXRyeUF0dGVtcHRzcQB+ABRMAAdzZXJ2aWNldAASTGphdmEvbGFuZy9PYmplY3Q7TAAPc2VydmljZUVuZFBvaW50dAAOTGphdmEvbmV0L1VSTDtMABNzZXJ2aWNlTmFtZVNwYWNlVVJJcQB+AARMABBzZXJ2aWNlTmFtZXNwYWNlcQB+AARMAAtzZXJ2aWNlUGF0aHEAfgAEeHBzcQB+ABkBcHQAGk9TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNldABNb3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5leGNlcHRpb25oYW5kbGluZy5EZWZhdWx0TWVzc2FnZUV4Y2VwdGlvbkhhbmRsZXJzcQB+AB///////////3NxAH4AHAAAAANzcQB+ABkAcQB+ADJwc3IADGphdmEubmV0LlVSTJYlNzYa/ORyAwAHSQAIaGFzaENvZGVJAARwb3J0TAAJYXV0aG9yaXR5cQB+AARMAARmaWxlcQB+AARMAARob3N0cQB+AARMAAhwcm90b2NvbHEAfgAETAADcmVmcQB+AAR4cGkboqAAAB+QdAAObG9jYWxob3N0OjgwODB0ACsva3ItZGV2L3JlbW90aW5nL09TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNldAAJbG9jYWxob3N0dAAEaHR0cHB4dAAAdAAGVFJBVkVMdAABL3NyABNqYXZhLnV0aWwuQXJyYXlMaXN0eIHSHZnHYZ0DAAFJAARzaXpleHAAAAAFdwQAAAAKdAAzb3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5zZXJ2aWNlLktTQkphdmFTZXJ2aWNldAA8Y29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLmJhc2UuZXZlbnRzLkNhY2hlRW50cnlFdmVudExpc3RlbmVydAA3Y29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLmJhc2UuZXZlbnRzLkNhY2hlRXZlbnRMaXN0ZW5lcnQAF2phdmEudXRpbC5FdmVudExpc3RlbmVydAAsY29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLmJhc2UuTGlmZWN5Y2xlQXdhcmV4cQB+ACN0AAZUUkFWRUw=';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
INSERT INTO KRSB_MSG_PYLD_T (MSG_PYLD,MSG_QUE_ID)
  VALUES (EMPTY_CLOB(),381)
/
-- Length: 6108
-- Chunks: 2
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 381    FOR UPDATE;        
    buffer := 'rO0ABXNyAC1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLkFzeW5jaHJvbm91c0NhbGzxnQ5Y0t0lZQIAB1oAFWlnbm9yZVN0b3JlQW5kRm9yd2FyZFsACWFyZ3VtZW50c3QAE1tMamF2YS9sYW5nL09iamVjdDtMAAhjYWxsYmFja3QAM0xvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL0FzeW5jaHJvbm91c0NhbGxiYWNrO0wAB2NvbnRleHR0ABZMamF2YS9pby9TZXJpYWxpemFibGU7TAAKbWV0aG9kTmFtZXQAEkxqYXZhL2xhbmcvU3RyaW5nO1sACnBhcmFtVHlwZXN0ABJbTGphdmEvbGFuZy9DbGFzcztMAAtzZXJ2aWNlSW5mb3QAKkxvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL1NlcnZpY2VJbmZvO3hwAHVyABNbTGphdmEubGFuZy5PYmplY3Q7kM5YnxBzKWwCAAB4cAAAAAFzcgBDY29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLnBsdWdpbnMuY2x1c3RlcnN1cHBvcnQuQ2x1c3Rlck5vdGlmaWNhdGlvbhY+6VckvG9pAgACSQAEdHlwZUwABGRhdGFxAH4AA3hwAAAAAnQAMkRvY3VtZW50VHlwZVJ1bGVDYWNoZTpQYXJhbWV0ZXJNYWludGVuYW5jZURvY3VtZW50cHB0AAZpbnZva2V1cgASW0xqYXZhLmxhbmcuQ2xhc3M7qxbXrsvNWpkCAAB4cAAAAAF2cgAUamF2YS5pby5TZXJpYWxpemFibGUQm2EN15tk7QIAAHhwc3IAKG9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuU2VydmljZUluZm/FFyO2KQS3ugIAC0wABWFsaXZldAATTGphdmEvbGFuZy9Cb29sZWFuO0wAFGVuZHBvaW50QWx0ZXJuYXRlVXJscQB+AARMAAtlbmRwb2ludFVybHEAfgAETAAKbG9ja1Zlck5icnQAE0xqYXZhL2xhbmcvSW50ZWdlcjtMAA5tZXNzYWdlRW50cnlJZHQAEExqYXZhL2xhbmcvTG9uZztMAAVxbmFtZXQAG0xqYXZheC94bWwvbmFtZXNwYWNlL1FOYW1lO0wAGnNlcmlhbGl6ZWRTZXJ2aWNlTmFtZXNwYWNlcQB+AARMAAhzZXJ2ZXJJcHEAfgAETAARc2VydmljZURlZmluaXRpb250ADBMb3JnL2t1YWxpL3JpY2Uva3NiL21lc3NhZ2luZy9TZXJ2aWNlRGVmaW5pdGlvbjtMAAtzZXJ2aWNlTmFtZXEAfgAETAAQc2VydmljZU5hbWVzcGFjZXEAfgAEeHBzcgARamF2YS5sYW5nLkJvb2xlYW7NIHKA1Zz67gIAAVoABXZhbHVleHABcHQAQGh0dHA6Ly9sb2NhbGhvc3Q6ODA4MC9rci1kZXYvcmVtb3RpbmcvT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2VzcgARamF2YS5sYW5nLkludGVnZXIS4qCk94GHOAIAAUkABXZhbHVleHIAEGphdmEubGFuZy5OdW1iZXKGrJUdC5TgiwIAAHhwAAAAAXNyAA5qYXZhLmxhbmcuTG9uZzuL5JDMjyPfAgABSgAFdmFsdWV4cQB+AB0AAAAAAAAPfXNyABlqYXZheC54bWwubmFtZXNwYWNlLlFOYW1lgW2oLfw73WwCAANMAAlsb2NhbFBhcnRxAH4ABEwADG5hbWVzcGFjZVVSSXEAfgAETAAGcHJlZml4cQB+AAR4cHQAGk9TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNldAAAcQB+ACR0B3RyTzBBQlhOeUFESnZjbWN1YTNWaGJHa3VjbWxqWlM1cmMySXViV1Z6YzJGbmFXNW5Ma3BoZG1GVFpYSjJhV05sUkdWbWFXNXBkR2x2YnJibkRWOUJOR3p4QWdBQlRBQVJjMlZ5ZG1salpVbHVkR1Z5Wm1GalpYTjBBQkJNYW1GMllTOTFkR2xzTDB4cGMzUTdlSElBTG05eVp5NXJkV0ZzYVM1eWFXTmxMbXR6WWk1dFpYTnpZV2RwYm1jdVUyVnlkbWxqWlVSbFptbHVhWFJwYjI0QW13SlBXTjBwZmdJQURVd0FDMkoxYzFObFkzVnlhWFI1ZEFBVFRHcGhkbUV2YkdGdVp5OUNiMjlzWldGdU8wd0FEMk55WldSbGJuUnBZV3h6Vkhsd1pYUUFURXh2Y21jdmEzVmhiR2t2Y21salpTOWpiM0psTDNObFkzVnlhWFI1TDJOeVpXUmxiblJwWVd4ekwwTnlaV1JsYm5ScFlXeHpVMjkxY21ObEpFTnlaV1JsYm5ScFlXeHpWSGx3WlR0TUFCQnNiMk5oYkZObGNuWnBZMlZPWVcxbGRBQVNUR3BoZG1FdmJHRnVaeTlUZEhKcGJtYzdUQUFYYldWemMyRm5aVVY0WTJWd2RHbHZia2hoYm1Sc1pYSnhBSDRBQlV3QURHMXBiR3hwYzFSdlRHbDJaWFFBRUV4cVlYWmhMMnhoYm1jdlRHOXVaenRNQUFod2NtbHZjbWwwZVhRQUUweHFZWFpoTDJ4aGJtY3ZTVzUwWldkbGNqdE1BQVZ4ZFdWMVpYRUFmZ0FEVEFBTmNtVjBjbmxCZEhSbGJYQjBjM0VBZmdBSFRBQUhjMlZ5ZG1salpYUUFFa3hxWVhaaEwyeGhibWN2VDJKcVpXTjBPMHdBRDNObGNuWnBZMlZGYm1SUWIybHVkSFFBRGt4cVlYWmhMMjVsZEM5VlVrdzdUQUFUYzJWeWRtbGpaVTVoYldWVGNHRmpaVlZTU1hFQWZnQUZUQUFRYzJWeWRtbGpaVTVoYldWemNHRmpaWEVBZmdBRlRBQUxjMlZ5ZG1salpWQmhkR2h4QUg0QUJYaHdjM0lBRVdwaGRtRXViR0Z1Wnk1Q2IyOXNaV0Z1elNCeWdOV2MrdTRDQUFGYUFBVjJZV3gxWlhod0FYQjBBQnBQVTBOaFkyaGxUbTkwYVdacFkyRjBhVzl1VTJWeWRtbGpaWFFBVFc5eVp5NXJkV0ZzYVM1eWFXTmxMbXR6WWk1dFpYTnpZV2RwYm1jdVpYaGpaWEIwYVc5dWFHRnVaR3hwYm1jdVJHVm1ZWFZzZEUxbGMzTmhaMlZGZUdObGNIUnBiMjVJWVc1a2JHVnljM0lBRG1waGRtRXViR0Z1Wnk1TWIyNW5PNHZra015UEk5OENBQUZLQUFWMllXeDFaWGh5QUJCcVlYWmhMbXhoYm1jdVRuVnRZbVZ5aHF5VkhRdVU0SXNDQUFCNGNQLy8vLy8vLy8vL2MzSUFFV3BoZG1FdWJHRnVaeTVKYm5SbFoyVnlFdUtncFBlQmh6Z0NBQUZKQUFWMllXeDFaWGh4QUg0QUVBQUFBQU56Y1FCK0FBc0FjUUIrQUJOd2MzSUFER3BoZG1FdWJtVjBMbFZTVEpZbE56WWEvT1J5QXdBSFNRQUlhR0Z6YUVOdlpHVkpBQVJ3YjNKMFRBQUpZWFYwYUc5eWFYUjVjUUIrQUFWTUFBUm1hV3hsY1FCK0FBVk1BQVJvYjNOMGNRQitBQVZNQUFod2NtOTBiMk52YkhFQWZnQUZUQUFEY21WbWNRQitBQVY0Y1AvLy8vOEFBQitRZEFBT2JHOWpZV3hvYjNOME9qZ3dPREIwQUNzdmEzSXRaR1YyTDNKbGJXOTBhVzVuTDA5VFEyRmphR1ZPYjNScFptbGpZWFJwYjI1VFpYSjJhV05sZEFBSmJHOWpZV3hvYjNOMGRBQUVhSFIwY0hCNGRBQUFkQUFHVkZKQlZrVk1kQUFCTDNOeUFCTnFZWFpoTG5WMGFXd3VRWEp5WVhsTWFYTjBlSUhTSFpuSFlaMERBQUZKQUFSemFYcGxlSEFBQUFBRmR3UUFBQUFLZEFBemIz';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 381    FOR UPDATE;        
    buffer := 'Sm5MbXQxWVd4cExuSnBZMlV1YTNOaUxtMWxjM05oWjJsdVp5NXpaWEoyYVdObExrdFRRa3BoZG1GVFpYSjJhV05sZEFBOFkyOXRMbTl3Wlc1emVXMXdhRzl1ZVM1dmMyTmhZMmhsTG1KaGMyVXVaWFpsYm5SekxrTmhZMmhsUlc1MGNubEZkbVZ1ZEV4cGMzUmxibVZ5ZEFBM1kyOXRMbTl3Wlc1emVXMXdhRzl1ZVM1dmMyTmhZMmhsTG1KaGMyVXVaWFpsYm5SekxrTmhZMmhsUlhabGJuUk1hWE4wWlc1bGNuUUFGMnBoZG1FdWRYUnBiQzVGZG1WdWRFeHBjM1JsYm1WeWRBQXNZMjl0TG05d1pXNXplVzF3YUc5dWVTNXZjMk5oWTJobExtSmhjMlV1VEdsbVpXTjVZMnhsUVhkaGNtVjR0AA0xMjkuMTg2Ljc5LjQ1c3IAMm9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuSmF2YVNlcnZpY2VEZWZpbml0aW9utucNX0E0bPECAAFMABFzZXJ2aWNlSW50ZXJmYWNlc3QAEExqYXZhL3V0aWwvTGlzdDt4cgAub3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5TZXJ2aWNlRGVmaW5pdGlvbgCbAk9Y3Sl+AgANTAALYnVzU2VjdXJpdHlxAH4AE0wAD2NyZWRlbnRpYWxzVHlwZXQATExvcmcva3VhbGkvcmljZS9jb3JlL3NlY3VyaXR5L2NyZWRlbnRpYWxzL0NyZWRlbnRpYWxzU291cmNlJENyZWRlbnRpYWxzVHlwZTtMABBsb2NhbFNlcnZpY2VOYW1lcQB+AARMABdtZXNzYWdlRXhjZXB0aW9uSGFuZGxlcnEAfgAETAAMbWlsbGlzVG9MaXZlcQB+ABVMAAhwcmlvcml0eXEAfgAUTAAFcXVldWVxAH4AE0wADXJldHJ5QXR0ZW1wdHNxAH4AFEwAB3NlcnZpY2V0ABJMamF2YS9sYW5nL09iamVjdDtMAA9zZXJ2aWNlRW5kUG9pbnR0AA5MamF2YS9uZXQvVVJMO0wAE3NlcnZpY2VOYW1lU3BhY2VVUklxAH4ABEwAEHNlcnZpY2VOYW1lc3BhY2VxAH4ABEwAC3NlcnZpY2VQYXRocQB+AAR4cHNxAH4AGQFwdAAaT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2V0AE1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLmV4Y2VwdGlvbmhhbmRsaW5nLkRlZmF1bHRNZXNzYWdlRXhjZXB0aW9uSGFuZGxlcnNxAH4AH///////////c3EAfgAcAAAAA3NxAH4AGQBxAH4AMnBzcgAMamF2YS5uZXQuVVJMliU3Nhr85HIDAAdJAAhoYXNoQ29kZUkABHBvcnRMAAlhdXRob3JpdHlxAH4ABEwABGZpbGVxAH4ABEwABGhvc3RxAH4ABEwACHByb3RvY29scQB+AARMAANyZWZxAH4ABHhwaRuioAAAH5B0AA5sb2NhbGhvc3Q6ODA4MHQAKy9rci1kZXYvcmVtb3RpbmcvT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2V0AAlsb2NhbGhvc3R0AARodHRwcHh0AAB0AAZUUkFWRUx0AAEvc3IAE2phdmEudXRpbC5BcnJheUxpc3R4gdIdmcdhnQMAAUkABHNpemV4cAAAAAV3BAAAAAp0ADNvcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLnNlcnZpY2UuS1NCSmF2YVNlcnZpY2V0ADxjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5ldmVudHMuQ2FjaGVFbnRyeUV2ZW50TGlzdGVuZXJ0ADdjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5ldmVudHMuQ2FjaGVFdmVudExpc3RlbmVydAAXamF2YS51dGlsLkV2ZW50TGlzdGVuZXJ0ACxjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5MaWZlY3ljbGVBd2FyZXhxAH4AI3QABlRSQVZFTA==';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
INSERT INTO KRSB_MSG_PYLD_T (MSG_PYLD,MSG_QUE_ID)
  VALUES (EMPTY_CLOB(),382)
/
-- Length: 6088
-- Chunks: 2
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 382    FOR UPDATE;        
    buffer := 'rO0ABXNyAC1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLkFzeW5jaHJvbm91c0NhbGzxnQ5Y0t0lZQIAB1oAFWlnbm9yZVN0b3JlQW5kRm9yd2FyZFsACWFyZ3VtZW50c3QAE1tMamF2YS9sYW5nL09iamVjdDtMAAhjYWxsYmFja3QAM0xvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL0FzeW5jaHJvbm91c0NhbGxiYWNrO0wAB2NvbnRleHR0ABZMamF2YS9pby9TZXJpYWxpemFibGU7TAAKbWV0aG9kTmFtZXQAEkxqYXZhL2xhbmcvU3RyaW5nO1sACnBhcmFtVHlwZXN0ABJbTGphdmEvbGFuZy9DbGFzcztMAAtzZXJ2aWNlSW5mb3QAKkxvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL1NlcnZpY2VJbmZvO3hwAHVyABNbTGphdmEubGFuZy5PYmplY3Q7kM5YnxBzKWwCAAB4cAAAAAFzcgBDY29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLnBsdWdpbnMuY2x1c3RlcnN1cHBvcnQuQ2x1c3Rlck5vdGlmaWNhdGlvbhY+6VckvG9pAgACSQAEdHlwZUwABGRhdGFxAH4AA3hwAAAAAnQAI0RvY3VtZW50VHlwZVJ1bGVDYWNoZTpUcmF2ZWxSZXF1ZXN0cHB0AAZpbnZva2V1cgASW0xqYXZhLmxhbmcuQ2xhc3M7qxbXrsvNWpkCAAB4cAAAAAF2cgAUamF2YS5pby5TZXJpYWxpemFibGUQm2EN15tk7QIAAHhwc3IAKG9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuU2VydmljZUluZm/FFyO2KQS3ugIAC0wABWFsaXZldAATTGphdmEvbGFuZy9Cb29sZWFuO0wAFGVuZHBvaW50QWx0ZXJuYXRlVXJscQB+AARMAAtlbmRwb2ludFVybHEAfgAETAAKbG9ja1Zlck5icnQAE0xqYXZhL2xhbmcvSW50ZWdlcjtMAA5tZXNzYWdlRW50cnlJZHQAEExqYXZhL2xhbmcvTG9uZztMAAVxbmFtZXQAG0xqYXZheC94bWwvbmFtZXNwYWNlL1FOYW1lO0wAGnNlcmlhbGl6ZWRTZXJ2aWNlTmFtZXNwYWNlcQB+AARMAAhzZXJ2ZXJJcHEAfgAETAARc2VydmljZURlZmluaXRpb250ADBMb3JnL2t1YWxpL3JpY2Uva3NiL21lc3NhZ2luZy9TZXJ2aWNlRGVmaW5pdGlvbjtMAAtzZXJ2aWNlTmFtZXEAfgAETAAQc2VydmljZU5hbWVzcGFjZXEAfgAEeHBzcgARamF2YS5sYW5nLkJvb2xlYW7NIHKA1Zz67gIAAVoABXZhbHVleHABcHQAQGh0dHA6Ly9sb2NhbGhvc3Q6ODA4MC9rci1kZXYvcmVtb3RpbmcvT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2VzcgARamF2YS5sYW5nLkludGVnZXIS4qCk94GHOAIAAUkABXZhbHVleHIAEGphdmEubGFuZy5OdW1iZXKGrJUdC5TgiwIAAHhwAAAAAXNyAA5qYXZhLmxhbmcuTG9uZzuL5JDMjyPfAgABSgAFdmFsdWV4cQB+AB0AAAAAAAAPfXNyABlqYXZheC54bWwubmFtZXNwYWNlLlFOYW1lgW2oLfw73WwCAANMAAlsb2NhbFBhcnRxAH4ABEwADG5hbWVzcGFjZVVSSXEAfgAETAAGcHJlZml4cQB+AAR4cHQAGk9TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNldAAAcQB+ACR0B3RyTzBBQlhOeUFESnZjbWN1YTNWaGJHa3VjbWxqWlM1cmMySXViV1Z6YzJGbmFXNW5Ma3BoZG1GVFpYSjJhV05sUkdWbWFXNXBkR2x2YnJibkRWOUJOR3p4QWdBQlRBQVJjMlZ5ZG1salpVbHVkR1Z5Wm1GalpYTjBBQkJNYW1GMllTOTFkR2xzTDB4cGMzUTdlSElBTG05eVp5NXJkV0ZzYVM1eWFXTmxMbXR6WWk1dFpYTnpZV2RwYm1jdVUyVnlkbWxqWlVSbFptbHVhWFJwYjI0QW13SlBXTjBwZmdJQURVd0FDMkoxYzFObFkzVnlhWFI1ZEFBVFRHcGhkbUV2YkdGdVp5OUNiMjlzWldGdU8wd0FEMk55WldSbGJuUnBZV3h6Vkhsd1pYUUFURXh2Y21jdmEzVmhiR2t2Y21salpTOWpiM0psTDNObFkzVnlhWFI1TDJOeVpXUmxiblJwWVd4ekwwTnlaV1JsYm5ScFlXeHpVMjkxY21ObEpFTnlaV1JsYm5ScFlXeHpWSGx3WlR0TUFCQnNiMk5oYkZObGNuWnBZMlZPWVcxbGRBQVNUR3BoZG1FdmJHRnVaeTlUZEhKcGJtYzdUQUFYYldWemMyRm5aVVY0WTJWd2RHbHZia2hoYm1Sc1pYSnhBSDRBQlV3QURHMXBiR3hwYzFSdlRHbDJaWFFBRUV4cVlYWmhMMnhoYm1jdlRHOXVaenRNQUFod2NtbHZjbWwwZVhRQUUweHFZWFpoTDJ4aGJtY3ZTVzUwWldkbGNqdE1BQVZ4ZFdWMVpYRUFmZ0FEVEFBTmNtVjBjbmxCZEhSbGJYQjBjM0VBZmdBSFRBQUhjMlZ5ZG1salpYUUFFa3hxWVhaaEwyeGhibWN2VDJKcVpXTjBPMHdBRDNObGNuWnBZMlZGYm1SUWIybHVkSFFBRGt4cVlYWmhMMjVsZEM5VlVrdzdUQUFUYzJWeWRtbGpaVTVoYldWVGNHRmpaVlZTU1hFQWZnQUZUQUFRYzJWeWRtbGpaVTVoYldWemNHRmpaWEVBZmdBRlRBQUxjMlZ5ZG1salpWQmhkR2h4QUg0QUJYaHdjM0lBRVdwaGRtRXViR0Z1Wnk1Q2IyOXNaV0Z1elNCeWdOV2MrdTRDQUFGYUFBVjJZV3gxWlhod0FYQjBBQnBQVTBOaFkyaGxUbTkwYVdacFkyRjBhVzl1VTJWeWRtbGpaWFFBVFc5eVp5NXJkV0ZzYVM1eWFXTmxMbXR6WWk1dFpYTnpZV2RwYm1jdVpYaGpaWEIwYVc5dWFHRnVaR3hwYm1jdVJHVm1ZWFZzZEUxbGMzTmhaMlZGZUdObGNIUnBiMjVJWVc1a2JHVnljM0lBRG1waGRtRXViR0Z1Wnk1TWIyNW5PNHZra015UEk5OENBQUZLQUFWMllXeDFaWGh5QUJCcVlYWmhMbXhoYm1jdVRuVnRZbVZ5aHF5VkhRdVU0SXNDQUFCNGNQLy8vLy8vLy8vL2MzSUFFV3BoZG1FdWJHRnVaeTVKYm5SbFoyVnlFdUtncFBlQmh6Z0NBQUZKQUFWMllXeDFaWGh4QUg0QUVBQUFBQU56Y1FCK0FBc0FjUUIrQUJOd2MzSUFER3BoZG1FdWJtVjBMbFZTVEpZbE56WWEvT1J5QXdBSFNRQUlhR0Z6YUVOdlpHVkpBQVJ3YjNKMFRBQUpZWFYwYUc5eWFYUjVjUUIrQUFWTUFBUm1hV3hsY1FCK0FBVk1BQVJvYjNOMGNRQitBQVZNQUFod2NtOTBiMk52YkhFQWZnQUZUQUFEY21WbWNRQitBQVY0Y1AvLy8vOEFBQitRZEFBT2JHOWpZV3hvYjNOME9qZ3dPREIwQUNzdmEzSXRaR1YyTDNKbGJXOTBhVzVuTDA5VFEyRmphR1ZPYjNScFptbGpZWFJwYjI1VFpYSjJhV05sZEFBSmJHOWpZV3hvYjNOMGRBQUVhSFIwY0hCNGRBQUFkQUFHVkZKQlZrVk1kQUFCTDNOeUFCTnFZWFpoTG5WMGFXd3VRWEp5WVhsTWFYTjBlSUhTSFpuSFlaMERBQUZKQUFSemFYcGxlSEFBQUFBRmR3UUFBQUFLZEFBemIzSm5MbXQxWVd4cExuSnBZ';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 382    FOR UPDATE;        
    buffer := 'MlV1YTNOaUxtMWxjM05oWjJsdVp5NXpaWEoyYVdObExrdFRRa3BoZG1GVFpYSjJhV05sZEFBOFkyOXRMbTl3Wlc1emVXMXdhRzl1ZVM1dmMyTmhZMmhsTG1KaGMyVXVaWFpsYm5SekxrTmhZMmhsUlc1MGNubEZkbVZ1ZEV4cGMzUmxibVZ5ZEFBM1kyOXRMbTl3Wlc1emVXMXdhRzl1ZVM1dmMyTmhZMmhsTG1KaGMyVXVaWFpsYm5SekxrTmhZMmhsUlhabGJuUk1hWE4wWlc1bGNuUUFGMnBoZG1FdWRYUnBiQzVGZG1WdWRFeHBjM1JsYm1WeWRBQXNZMjl0TG05d1pXNXplVzF3YUc5dWVTNXZjMk5oWTJobExtSmhjMlV1VEdsbVpXTjVZMnhsUVhkaGNtVjR0AA0xMjkuMTg2Ljc5LjQ1c3IAMm9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuSmF2YVNlcnZpY2VEZWZpbml0aW9utucNX0E0bPECAAFMABFzZXJ2aWNlSW50ZXJmYWNlc3QAEExqYXZhL3V0aWwvTGlzdDt4cgAub3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5TZXJ2aWNlRGVmaW5pdGlvbgCbAk9Y3Sl+AgANTAALYnVzU2VjdXJpdHlxAH4AE0wAD2NyZWRlbnRpYWxzVHlwZXQATExvcmcva3VhbGkvcmljZS9jb3JlL3NlY3VyaXR5L2NyZWRlbnRpYWxzL0NyZWRlbnRpYWxzU291cmNlJENyZWRlbnRpYWxzVHlwZTtMABBsb2NhbFNlcnZpY2VOYW1lcQB+AARMABdtZXNzYWdlRXhjZXB0aW9uSGFuZGxlcnEAfgAETAAMbWlsbGlzVG9MaXZlcQB+ABVMAAhwcmlvcml0eXEAfgAUTAAFcXVldWVxAH4AE0wADXJldHJ5QXR0ZW1wdHNxAH4AFEwAB3NlcnZpY2V0ABJMamF2YS9sYW5nL09iamVjdDtMAA9zZXJ2aWNlRW5kUG9pbnR0AA5MamF2YS9uZXQvVVJMO0wAE3NlcnZpY2VOYW1lU3BhY2VVUklxAH4ABEwAEHNlcnZpY2VOYW1lc3BhY2VxAH4ABEwAC3NlcnZpY2VQYXRocQB+AAR4cHNxAH4AGQFwdAAaT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2V0AE1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLmV4Y2VwdGlvbmhhbmRsaW5nLkRlZmF1bHRNZXNzYWdlRXhjZXB0aW9uSGFuZGxlcnNxAH4AH///////////c3EAfgAcAAAAA3NxAH4AGQBxAH4AMnBzcgAMamF2YS5uZXQuVVJMliU3Nhr85HIDAAdJAAhoYXNoQ29kZUkABHBvcnRMAAlhdXRob3JpdHlxAH4ABEwABGZpbGVxAH4ABEwABGhvc3RxAH4ABEwACHByb3RvY29scQB+AARMAANyZWZxAH4ABHhwaRuioAAAH5B0AA5sb2NhbGhvc3Q6ODA4MHQAKy9rci1kZXYvcmVtb3RpbmcvT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2V0AAlsb2NhbGhvc3R0AARodHRwcHh0AAB0AAZUUkFWRUx0AAEvc3IAE2phdmEudXRpbC5BcnJheUxpc3R4gdIdmcdhnQMAAUkABHNpemV4cAAAAAV3BAAAAAp0ADNvcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLnNlcnZpY2UuS1NCSmF2YVNlcnZpY2V0ADxjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5ldmVudHMuQ2FjaGVFbnRyeUV2ZW50TGlzdGVuZXJ0ADdjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5ldmVudHMuQ2FjaGVFdmVudExpc3RlbmVydAAXamF2YS51dGlsLkV2ZW50TGlzdGVuZXJ0ACxjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5MaWZlY3ljbGVBd2FyZXhxAH4AI3QABlRSQVZFTA==';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
INSERT INTO KRSB_MSG_PYLD_T (MSG_PYLD,MSG_QUE_ID)
  VALUES (EMPTY_CLOB(),383)
/
-- Length: 6124
-- Chunks: 2
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 383    FOR UPDATE;        
    buffer := 'rO0ABXNyAC1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLkFzeW5jaHJvbm91c0NhbGzxnQ5Y0t0lZQIAB1oAFWlnbm9yZVN0b3JlQW5kRm9yd2FyZFsACWFyZ3VtZW50c3QAE1tMamF2YS9sYW5nL09iamVjdDtMAAhjYWxsYmFja3QAM0xvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL0FzeW5jaHJvbm91c0NhbGxiYWNrO0wAB2NvbnRleHR0ABZMamF2YS9pby9TZXJpYWxpemFibGU7TAAKbWV0aG9kTmFtZXQAEkxqYXZhL2xhbmcvU3RyaW5nO1sACnBhcmFtVHlwZXN0ABJbTGphdmEvbGFuZy9DbGFzcztMAAtzZXJ2aWNlSW5mb3QAKkxvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL1NlcnZpY2VJbmZvO3hwAHVyABNbTGphdmEubGFuZy5PYmplY3Q7kM5YnxBzKWwCAAB4cAAAAAFzcgBDY29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLnBsdWdpbnMuY2x1c3RlcnN1cHBvcnQuQ2x1c3Rlck5vdGlmaWNhdGlvbhY+6VckvG9pAgACSQAEdHlwZUwABGRhdGFxAH4AA3hwAAAAAnQAPkRvY3VtZW50VHlwZVJ1bGVDYWNoZTpSb3V0aW5nUnVsZURlbGVnYXRpb25NYWludGVuYW5jZURvY3VtZW50cHB0AAZpbnZva2V1cgASW0xqYXZhLmxhbmcuQ2xhc3M7qxbXrsvNWpkCAAB4cAAAAAF2cgAUamF2YS5pby5TZXJpYWxpemFibGUQm2EN15tk7QIAAHhwc3IAKG9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuU2VydmljZUluZm/FFyO2KQS3ugIAC0wABWFsaXZldAATTGphdmEvbGFuZy9Cb29sZWFuO0wAFGVuZHBvaW50QWx0ZXJuYXRlVXJscQB+AARMAAtlbmRwb2ludFVybHEAfgAETAAKbG9ja1Zlck5icnQAE0xqYXZhL2xhbmcvSW50ZWdlcjtMAA5tZXNzYWdlRW50cnlJZHQAEExqYXZhL2xhbmcvTG9uZztMAAVxbmFtZXQAG0xqYXZheC94bWwvbmFtZXNwYWNlL1FOYW1lO0wAGnNlcmlhbGl6ZWRTZXJ2aWNlTmFtZXNwYWNlcQB+AARMAAhzZXJ2ZXJJcHEAfgAETAARc2VydmljZURlZmluaXRpb250ADBMb3JnL2t1YWxpL3JpY2Uva3NiL21lc3NhZ2luZy9TZXJ2aWNlRGVmaW5pdGlvbjtMAAtzZXJ2aWNlTmFtZXEAfgAETAAQc2VydmljZU5hbWVzcGFjZXEAfgAEeHBzcgARamF2YS5sYW5nLkJvb2xlYW7NIHKA1Zz67gIAAVoABXZhbHVleHABcHQAQGh0dHA6Ly9sb2NhbGhvc3Q6ODA4MC9rci1kZXYvcmVtb3RpbmcvT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2VzcgARamF2YS5sYW5nLkludGVnZXIS4qCk94GHOAIAAUkABXZhbHVleHIAEGphdmEubGFuZy5OdW1iZXKGrJUdC5TgiwIAAHhwAAAAAXNyAA5qYXZhLmxhbmcuTG9uZzuL5JDMjyPfAgABSgAFdmFsdWV4cQB+AB0AAAAAAAAPfXNyABlqYXZheC54bWwubmFtZXNwYWNlLlFOYW1lgW2oLfw73WwCAANMAAlsb2NhbFBhcnRxAH4ABEwADG5hbWVzcGFjZVVSSXEAfgAETAAGcHJlZml4cQB+AAR4cHQAGk9TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNldAAAcQB+ACR0B3RyTzBBQlhOeUFESnZjbWN1YTNWaGJHa3VjbWxqWlM1cmMySXViV1Z6YzJGbmFXNW5Ma3BoZG1GVFpYSjJhV05sUkdWbWFXNXBkR2x2YnJibkRWOUJOR3p4QWdBQlRBQVJjMlZ5ZG1salpVbHVkR1Z5Wm1GalpYTjBBQkJNYW1GMllTOTFkR2xzTDB4cGMzUTdlSElBTG05eVp5NXJkV0ZzYVM1eWFXTmxMbXR6WWk1dFpYTnpZV2RwYm1jdVUyVnlkbWxqWlVSbFptbHVhWFJwYjI0QW13SlBXTjBwZmdJQURVd0FDMkoxYzFObFkzVnlhWFI1ZEFBVFRHcGhkbUV2YkdGdVp5OUNiMjlzWldGdU8wd0FEMk55WldSbGJuUnBZV3h6Vkhsd1pYUUFURXh2Y21jdmEzVmhiR2t2Y21salpTOWpiM0psTDNObFkzVnlhWFI1TDJOeVpXUmxiblJwWVd4ekwwTnlaV1JsYm5ScFlXeHpVMjkxY21ObEpFTnlaV1JsYm5ScFlXeHpWSGx3WlR0TUFCQnNiMk5oYkZObGNuWnBZMlZPWVcxbGRBQVNUR3BoZG1FdmJHRnVaeTlUZEhKcGJtYzdUQUFYYldWemMyRm5aVVY0WTJWd2RHbHZia2hoYm1Sc1pYSnhBSDRBQlV3QURHMXBiR3hwYzFSdlRHbDJaWFFBRUV4cVlYWmhMMnhoYm1jdlRHOXVaenRNQUFod2NtbHZjbWwwZVhRQUUweHFZWFpoTDJ4aGJtY3ZTVzUwWldkbGNqdE1BQVZ4ZFdWMVpYRUFmZ0FEVEFBTmNtVjBjbmxCZEhSbGJYQjBjM0VBZmdBSFRBQUhjMlZ5ZG1salpYUUFFa3hxWVhaaEwyeGhibWN2VDJKcVpXTjBPMHdBRDNObGNuWnBZMlZGYm1SUWIybHVkSFFBRGt4cVlYWmhMMjVsZEM5VlVrdzdUQUFUYzJWeWRtbGpaVTVoYldWVGNHRmpaVlZTU1hFQWZnQUZUQUFRYzJWeWRtbGpaVTVoYldWemNHRmpaWEVBZmdBRlRBQUxjMlZ5ZG1salpWQmhkR2h4QUg0QUJYaHdjM0lBRVdwaGRtRXViR0Z1Wnk1Q2IyOXNaV0Z1elNCeWdOV2MrdTRDQUFGYUFBVjJZV3gxWlhod0FYQjBBQnBQVTBOaFkyaGxUbTkwYVdacFkyRjBhVzl1VTJWeWRtbGpaWFFBVFc5eVp5NXJkV0ZzYVM1eWFXTmxMbXR6WWk1dFpYTnpZV2RwYm1jdVpYaGpaWEIwYVc5dWFHRnVaR3hwYm1jdVJHVm1ZWFZzZEUxbGMzTmhaMlZGZUdObGNIUnBiMjVJWVc1a2JHVnljM0lBRG1waGRtRXViR0Z1Wnk1TWIyNW5PNHZra015UEk5OENBQUZLQUFWMllXeDFaWGh5QUJCcVlYWmhMbXhoYm1jdVRuVnRZbVZ5aHF5VkhRdVU0SXNDQUFCNGNQLy8vLy8vLy8vL2MzSUFFV3BoZG1FdWJHRnVaeTVKYm5SbFoyVnlFdUtncFBlQmh6Z0NBQUZKQUFWMllXeDFaWGh4QUg0QUVBQUFBQU56Y1FCK0FBc0FjUUIrQUJOd2MzSUFER3BoZG1FdWJtVjBMbFZTVEpZbE56WWEvT1J5QXdBSFNRQUlhR0Z6YUVOdlpHVkpBQVJ3YjNKMFRBQUpZWFYwYUc5eWFYUjVjUUIrQUFWTUFBUm1hV3hsY1FCK0FBVk1BQVJvYjNOMGNRQitBQVZNQUFod2NtOTBiMk52YkhFQWZnQUZUQUFEY21WbWNRQitBQVY0Y1AvLy8vOEFBQitRZEFBT2JHOWpZV3hvYjNOME9qZ3dPREIwQUNzdmEzSXRaR1YyTDNKbGJXOTBhVzVuTDA5VFEyRmphR1ZPYjNScFptbGpZWFJwYjI1VFpYSjJhV05sZEFBSmJHOWpZV3hvYjNOMGRBQUVhSFIwY0hCNGRBQUFkQUFHVkZKQlZrVk1kQUFCTDNOeUFCTnFZWFpoTG5WMGFXd3VRWEp5WVhsTWFYTjBlSUhTSFpuSFlaMERBQUZKQUFSemFYcGxlSEFBQUFBRmR3';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 383    FOR UPDATE;        
    buffer := 'UUFBQUFLZEFBemIzSm5MbXQxWVd4cExuSnBZMlV1YTNOaUxtMWxjM05oWjJsdVp5NXpaWEoyYVdObExrdFRRa3BoZG1GVFpYSjJhV05sZEFBOFkyOXRMbTl3Wlc1emVXMXdhRzl1ZVM1dmMyTmhZMmhsTG1KaGMyVXVaWFpsYm5SekxrTmhZMmhsUlc1MGNubEZkbVZ1ZEV4cGMzUmxibVZ5ZEFBM1kyOXRMbTl3Wlc1emVXMXdhRzl1ZVM1dmMyTmhZMmhsTG1KaGMyVXVaWFpsYm5SekxrTmhZMmhsUlhabGJuUk1hWE4wWlc1bGNuUUFGMnBoZG1FdWRYUnBiQzVGZG1WdWRFeHBjM1JsYm1WeWRBQXNZMjl0TG05d1pXNXplVzF3YUc5dWVTNXZjMk5oWTJobExtSmhjMlV1VEdsbVpXTjVZMnhsUVhkaGNtVjR0AA0xMjkuMTg2Ljc5LjQ1c3IAMm9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuSmF2YVNlcnZpY2VEZWZpbml0aW9utucNX0E0bPECAAFMABFzZXJ2aWNlSW50ZXJmYWNlc3QAEExqYXZhL3V0aWwvTGlzdDt4cgAub3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5TZXJ2aWNlRGVmaW5pdGlvbgCbAk9Y3Sl+AgANTAALYnVzU2VjdXJpdHlxAH4AE0wAD2NyZWRlbnRpYWxzVHlwZXQATExvcmcva3VhbGkvcmljZS9jb3JlL3NlY3VyaXR5L2NyZWRlbnRpYWxzL0NyZWRlbnRpYWxzU291cmNlJENyZWRlbnRpYWxzVHlwZTtMABBsb2NhbFNlcnZpY2VOYW1lcQB+AARMABdtZXNzYWdlRXhjZXB0aW9uSGFuZGxlcnEAfgAETAAMbWlsbGlzVG9MaXZlcQB+ABVMAAhwcmlvcml0eXEAfgAUTAAFcXVldWVxAH4AE0wADXJldHJ5QXR0ZW1wdHNxAH4AFEwAB3NlcnZpY2V0ABJMamF2YS9sYW5nL09iamVjdDtMAA9zZXJ2aWNlRW5kUG9pbnR0AA5MamF2YS9uZXQvVVJMO0wAE3NlcnZpY2VOYW1lU3BhY2VVUklxAH4ABEwAEHNlcnZpY2VOYW1lc3BhY2VxAH4ABEwAC3NlcnZpY2VQYXRocQB+AAR4cHNxAH4AGQFwdAAaT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2V0AE1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLmV4Y2VwdGlvbmhhbmRsaW5nLkRlZmF1bHRNZXNzYWdlRXhjZXB0aW9uSGFuZGxlcnNxAH4AH///////////c3EAfgAcAAAAA3NxAH4AGQBxAH4AMnBzcgAMamF2YS5uZXQuVVJMliU3Nhr85HIDAAdJAAhoYXNoQ29kZUkABHBvcnRMAAlhdXRob3JpdHlxAH4ABEwABGZpbGVxAH4ABEwABGhvc3RxAH4ABEwACHByb3RvY29scQB+AARMAANyZWZxAH4ABHhwaRuioAAAH5B0AA5sb2NhbGhvc3Q6ODA4MHQAKy9rci1kZXYvcmVtb3RpbmcvT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2V0AAlsb2NhbGhvc3R0AARodHRwcHh0AAB0AAZUUkFWRUx0AAEvc3IAE2phdmEudXRpbC5BcnJheUxpc3R4gdIdmcdhnQMAAUkABHNpemV4cAAAAAV3BAAAAAp0ADNvcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLnNlcnZpY2UuS1NCSmF2YVNlcnZpY2V0ADxjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5ldmVudHMuQ2FjaGVFbnRyeUV2ZW50TGlzdGVuZXJ0ADdjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5ldmVudHMuQ2FjaGVFdmVudExpc3RlbmVydAAXamF2YS51dGlsLkV2ZW50TGlzdGVuZXJ0ACxjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5MaWZlY3ljbGVBd2FyZXhxAH4AI3QABlRSQVZFTA==';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
INSERT INTO KRSB_MSG_PYLD_T (MSG_PYLD,MSG_QUE_ID)
  VALUES (EMPTY_CLOB(),384)
/
-- Length: 6112
-- Chunks: 2
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 384    FOR UPDATE;        
    buffer := 'rO0ABXNyAC1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLkFzeW5jaHJvbm91c0NhbGzxnQ5Y0t0lZQIAB1oAFWlnbm9yZVN0b3JlQW5kRm9yd2FyZFsACWFyZ3VtZW50c3QAE1tMamF2YS9sYW5nL09iamVjdDtMAAhjYWxsYmFja3QAM0xvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL0FzeW5jaHJvbm91c0NhbGxiYWNrO0wAB2NvbnRleHR0ABZMamF2YS9pby9TZXJpYWxpemFibGU7TAAKbWV0aG9kTmFtZXQAEkxqYXZhL2xhbmcvU3RyaW5nO1sACnBhcmFtVHlwZXN0ABJbTGphdmEvbGFuZy9DbGFzcztMAAtzZXJ2aWNlSW5mb3QAKkxvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL1NlcnZpY2VJbmZvO3hwAHVyABNbTGphdmEubGFuZy5PYmplY3Q7kM5YnxBzKWwCAAB4cAAAAAFzcgBDY29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLnBsdWdpbnMuY2x1c3RlcnN1cHBvcnQuQ2x1c3Rlck5vdGlmaWNhdGlvbhY+6VckvG9pAgACSQAEdHlwZUwABGRhdGFxAH4AA3hwAAAAAnQANURvY3VtZW50VHlwZVJ1bGVDYWNoZTpJZGVudGl0eU1hbmFnZW1lbnRHcm91cERvY3VtZW50cHB0AAZpbnZva2V1cgASW0xqYXZhLmxhbmcuQ2xhc3M7qxbXrsvNWpkCAAB4cAAAAAF2cgAUamF2YS5pby5TZXJpYWxpemFibGUQm2EN15tk7QIAAHhwc3IAKG9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuU2VydmljZUluZm/FFyO2KQS3ugIAC0wABWFsaXZldAATTGphdmEvbGFuZy9Cb29sZWFuO0wAFGVuZHBvaW50QWx0ZXJuYXRlVXJscQB+AARMAAtlbmRwb2ludFVybHEAfgAETAAKbG9ja1Zlck5icnQAE0xqYXZhL2xhbmcvSW50ZWdlcjtMAA5tZXNzYWdlRW50cnlJZHQAEExqYXZhL2xhbmcvTG9uZztMAAVxbmFtZXQAG0xqYXZheC94bWwvbmFtZXNwYWNlL1FOYW1lO0wAGnNlcmlhbGl6ZWRTZXJ2aWNlTmFtZXNwYWNlcQB+AARMAAhzZXJ2ZXJJcHEAfgAETAARc2VydmljZURlZmluaXRpb250ADBMb3JnL2t1YWxpL3JpY2Uva3NiL21lc3NhZ2luZy9TZXJ2aWNlRGVmaW5pdGlvbjtMAAtzZXJ2aWNlTmFtZXEAfgAETAAQc2VydmljZU5hbWVzcGFjZXEAfgAEeHBzcgARamF2YS5sYW5nLkJvb2xlYW7NIHKA1Zz67gIAAVoABXZhbHVleHABcHQAQGh0dHA6Ly9sb2NhbGhvc3Q6ODA4MC9rci1kZXYvcmVtb3RpbmcvT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2VzcgARamF2YS5sYW5nLkludGVnZXIS4qCk94GHOAIAAUkABXZhbHVleHIAEGphdmEubGFuZy5OdW1iZXKGrJUdC5TgiwIAAHhwAAAAAXNyAA5qYXZhLmxhbmcuTG9uZzuL5JDMjyPfAgABSgAFdmFsdWV4cQB+AB0AAAAAAAAPfXNyABlqYXZheC54bWwubmFtZXNwYWNlLlFOYW1lgW2oLfw73WwCAANMAAlsb2NhbFBhcnRxAH4ABEwADG5hbWVzcGFjZVVSSXEAfgAETAAGcHJlZml4cQB+AAR4cHQAGk9TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNldAAAcQB+ACR0B3RyTzBBQlhOeUFESnZjbWN1YTNWaGJHa3VjbWxqWlM1cmMySXViV1Z6YzJGbmFXNW5Ma3BoZG1GVFpYSjJhV05sUkdWbWFXNXBkR2x2YnJibkRWOUJOR3p4QWdBQlRBQVJjMlZ5ZG1salpVbHVkR1Z5Wm1GalpYTjBBQkJNYW1GMllTOTFkR2xzTDB4cGMzUTdlSElBTG05eVp5NXJkV0ZzYVM1eWFXTmxMbXR6WWk1dFpYTnpZV2RwYm1jdVUyVnlkbWxqWlVSbFptbHVhWFJwYjI0QW13SlBXTjBwZmdJQURVd0FDMkoxYzFObFkzVnlhWFI1ZEFBVFRHcGhkbUV2YkdGdVp5OUNiMjlzWldGdU8wd0FEMk55WldSbGJuUnBZV3h6Vkhsd1pYUUFURXh2Y21jdmEzVmhiR2t2Y21salpTOWpiM0psTDNObFkzVnlhWFI1TDJOeVpXUmxiblJwWVd4ekwwTnlaV1JsYm5ScFlXeHpVMjkxY21ObEpFTnlaV1JsYm5ScFlXeHpWSGx3WlR0TUFCQnNiMk5oYkZObGNuWnBZMlZPWVcxbGRBQVNUR3BoZG1FdmJHRnVaeTlUZEhKcGJtYzdUQUFYYldWemMyRm5aVVY0WTJWd2RHbHZia2hoYm1Sc1pYSnhBSDRBQlV3QURHMXBiR3hwYzFSdlRHbDJaWFFBRUV4cVlYWmhMMnhoYm1jdlRHOXVaenRNQUFod2NtbHZjbWwwZVhRQUUweHFZWFpoTDJ4aGJtY3ZTVzUwWldkbGNqdE1BQVZ4ZFdWMVpYRUFmZ0FEVEFBTmNtVjBjbmxCZEhSbGJYQjBjM0VBZmdBSFRBQUhjMlZ5ZG1salpYUUFFa3hxWVhaaEwyeGhibWN2VDJKcVpXTjBPMHdBRDNObGNuWnBZMlZGYm1SUWIybHVkSFFBRGt4cVlYWmhMMjVsZEM5VlVrdzdUQUFUYzJWeWRtbGpaVTVoYldWVGNHRmpaVlZTU1hFQWZnQUZUQUFRYzJWeWRtbGpaVTVoYldWemNHRmpaWEVBZmdBRlRBQUxjMlZ5ZG1salpWQmhkR2h4QUg0QUJYaHdjM0lBRVdwaGRtRXViR0Z1Wnk1Q2IyOXNaV0Z1elNCeWdOV2MrdTRDQUFGYUFBVjJZV3gxWlhod0FYQjBBQnBQVTBOaFkyaGxUbTkwYVdacFkyRjBhVzl1VTJWeWRtbGpaWFFBVFc5eVp5NXJkV0ZzYVM1eWFXTmxMbXR6WWk1dFpYTnpZV2RwYm1jdVpYaGpaWEIwYVc5dWFHRnVaR3hwYm1jdVJHVm1ZWFZzZEUxbGMzTmhaMlZGZUdObGNIUnBiMjVJWVc1a2JHVnljM0lBRG1waGRtRXViR0Z1Wnk1TWIyNW5PNHZra015UEk5OENBQUZLQUFWMllXeDFaWGh5QUJCcVlYWmhMbXhoYm1jdVRuVnRZbVZ5aHF5VkhRdVU0SXNDQUFCNGNQLy8vLy8vLy8vL2MzSUFFV3BoZG1FdWJHRnVaeTVKYm5SbFoyVnlFdUtncFBlQmh6Z0NBQUZKQUFWMllXeDFaWGh4QUg0QUVBQUFBQU56Y1FCK0FBc0FjUUIrQUJOd2MzSUFER3BoZG1FdWJtVjBMbFZTVEpZbE56WWEvT1J5QXdBSFNRQUlhR0Z6YUVOdlpHVkpBQVJ3YjNKMFRBQUpZWFYwYUc5eWFYUjVjUUIrQUFWTUFBUm1hV3hsY1FCK0FBVk1BQVJvYjNOMGNRQitBQVZNQUFod2NtOTBiMk52YkhFQWZnQUZUQUFEY21WbWNRQitBQVY0Y1AvLy8vOEFBQitRZEFBT2JHOWpZV3hvYjNOME9qZ3dPREIwQUNzdmEzSXRaR1YyTDNKbGJXOTBhVzVuTDA5VFEyRmphR1ZPYjNScFptbGpZWFJwYjI1VFpYSjJhV05sZEFBSmJHOWpZV3hvYjNOMGRBQUVhSFIwY0hCNGRBQUFkQUFHVkZKQlZrVk1kQUFCTDNOeUFCTnFZWFpoTG5WMGFXd3VRWEp5WVhsTWFYTjBlSUhTSFpuSFlaMERBQUZKQUFSemFYcGxlSEFBQUFBRmR3UUFBQUFLZEFB';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 384    FOR UPDATE;        
    buffer := 'emIzSm5MbXQxWVd4cExuSnBZMlV1YTNOaUxtMWxjM05oWjJsdVp5NXpaWEoyYVdObExrdFRRa3BoZG1GVFpYSjJhV05sZEFBOFkyOXRMbTl3Wlc1emVXMXdhRzl1ZVM1dmMyTmhZMmhsTG1KaGMyVXVaWFpsYm5SekxrTmhZMmhsUlc1MGNubEZkbVZ1ZEV4cGMzUmxibVZ5ZEFBM1kyOXRMbTl3Wlc1emVXMXdhRzl1ZVM1dmMyTmhZMmhsTG1KaGMyVXVaWFpsYm5SekxrTmhZMmhsUlhabGJuUk1hWE4wWlc1bGNuUUFGMnBoZG1FdWRYUnBiQzVGZG1WdWRFeHBjM1JsYm1WeWRBQXNZMjl0TG05d1pXNXplVzF3YUc5dWVTNXZjMk5oWTJobExtSmhjMlV1VEdsbVpXTjVZMnhsUVhkaGNtVjR0AA0xMjkuMTg2Ljc5LjQ1c3IAMm9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuSmF2YVNlcnZpY2VEZWZpbml0aW9utucNX0E0bPECAAFMABFzZXJ2aWNlSW50ZXJmYWNlc3QAEExqYXZhL3V0aWwvTGlzdDt4cgAub3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5TZXJ2aWNlRGVmaW5pdGlvbgCbAk9Y3Sl+AgANTAALYnVzU2VjdXJpdHlxAH4AE0wAD2NyZWRlbnRpYWxzVHlwZXQATExvcmcva3VhbGkvcmljZS9jb3JlL3NlY3VyaXR5L2NyZWRlbnRpYWxzL0NyZWRlbnRpYWxzU291cmNlJENyZWRlbnRpYWxzVHlwZTtMABBsb2NhbFNlcnZpY2VOYW1lcQB+AARMABdtZXNzYWdlRXhjZXB0aW9uSGFuZGxlcnEAfgAETAAMbWlsbGlzVG9MaXZlcQB+ABVMAAhwcmlvcml0eXEAfgAUTAAFcXVldWVxAH4AE0wADXJldHJ5QXR0ZW1wdHNxAH4AFEwAB3NlcnZpY2V0ABJMamF2YS9sYW5nL09iamVjdDtMAA9zZXJ2aWNlRW5kUG9pbnR0AA5MamF2YS9uZXQvVVJMO0wAE3NlcnZpY2VOYW1lU3BhY2VVUklxAH4ABEwAEHNlcnZpY2VOYW1lc3BhY2VxAH4ABEwAC3NlcnZpY2VQYXRocQB+AAR4cHNxAH4AGQFwdAAaT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2V0AE1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLmV4Y2VwdGlvbmhhbmRsaW5nLkRlZmF1bHRNZXNzYWdlRXhjZXB0aW9uSGFuZGxlcnNxAH4AH///////////c3EAfgAcAAAAA3NxAH4AGQBxAH4AMnBzcgAMamF2YS5uZXQuVVJMliU3Nhr85HIDAAdJAAhoYXNoQ29kZUkABHBvcnRMAAlhdXRob3JpdHlxAH4ABEwABGZpbGVxAH4ABEwABGhvc3RxAH4ABEwACHByb3RvY29scQB+AARMAANyZWZxAH4ABHhwaRuioAAAH5B0AA5sb2NhbGhvc3Q6ODA4MHQAKy9rci1kZXYvcmVtb3RpbmcvT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2V0AAlsb2NhbGhvc3R0AARodHRwcHh0AAB0AAZUUkFWRUx0AAEvc3IAE2phdmEudXRpbC5BcnJheUxpc3R4gdIdmcdhnQMAAUkABHNpemV4cAAAAAV3BAAAAAp0ADNvcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLnNlcnZpY2UuS1NCSmF2YVNlcnZpY2V0ADxjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5ldmVudHMuQ2FjaGVFbnRyeUV2ZW50TGlzdGVuZXJ0ADdjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5ldmVudHMuQ2FjaGVFdmVudExpc3RlbmVydAAXamF2YS51dGlsLkV2ZW50TGlzdGVuZXJ0ACxjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5MaWZlY3ljbGVBd2FyZXhxAH4AI3QABlRSQVZFTA==';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
INSERT INTO KRSB_MSG_PYLD_T (MSG_PYLD,MSG_QUE_ID)
  VALUES (EMPTY_CLOB(),385)
/
-- Length: 6108
-- Chunks: 2
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 385    FOR UPDATE;        
    buffer := 'rO0ABXNyAC1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLkFzeW5jaHJvbm91c0NhbGzxnQ5Y0t0lZQIAB1oAFWlnbm9yZVN0b3JlQW5kRm9yd2FyZFsACWFyZ3VtZW50c3QAE1tMamF2YS9sYW5nL09iamVjdDtMAAhjYWxsYmFja3QAM0xvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL0FzeW5jaHJvbm91c0NhbGxiYWNrO0wAB2NvbnRleHR0ABZMamF2YS9pby9TZXJpYWxpemFibGU7TAAKbWV0aG9kTmFtZXQAEkxqYXZhL2xhbmcvU3RyaW5nO1sACnBhcmFtVHlwZXN0ABJbTGphdmEvbGFuZy9DbGFzcztMAAtzZXJ2aWNlSW5mb3QAKkxvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL1NlcnZpY2VJbmZvO3hwAHVyABNbTGphdmEubGFuZy5PYmplY3Q7kM5YnxBzKWwCAAB4cAAAAAFzcgBDY29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLnBsdWdpbnMuY2x1c3RlcnN1cHBvcnQuQ2x1c3Rlck5vdGlmaWNhdGlvbhY+6VckvG9pAgACSQAEdHlwZUwABGRhdGFxAH4AA3hwAAAAAnQANERvY3VtZW50VHlwZVJ1bGVDYWNoZTpJZGVudGl0eU1hbmFnZW1lbnRSb2xlRG9jdW1lbnRwcHQABmludm9rZXVyABJbTGphdmEubGFuZy5DbGFzczurFteuy81amQIAAHhwAAAAAXZyABRqYXZhLmlvLlNlcmlhbGl6YWJsZRCbYQ3Xm2TtAgAAeHBzcgAob3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5TZXJ2aWNlSW5mb8UXI7YpBLe6AgALTAAFYWxpdmV0ABNMamF2YS9sYW5nL0Jvb2xlYW47TAAUZW5kcG9pbnRBbHRlcm5hdGVVcmxxAH4ABEwAC2VuZHBvaW50VXJscQB+AARMAApsb2NrVmVyTmJydAATTGphdmEvbGFuZy9JbnRlZ2VyO0wADm1lc3NhZ2VFbnRyeUlkdAAQTGphdmEvbGFuZy9Mb25nO0wABXFuYW1ldAAbTGphdmF4L3htbC9uYW1lc3BhY2UvUU5hbWU7TAAac2VyaWFsaXplZFNlcnZpY2VOYW1lc3BhY2VxAH4ABEwACHNlcnZlcklwcQB+AARMABFzZXJ2aWNlRGVmaW5pdGlvbnQAMExvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL1NlcnZpY2VEZWZpbml0aW9uO0wAC3NlcnZpY2VOYW1lcQB+AARMABBzZXJ2aWNlTmFtZXNwYWNlcQB+AAR4cHNyABFqYXZhLmxhbmcuQm9vbGVhbs0gcoDVnPruAgABWgAFdmFsdWV4cAFwdABAaHR0cDovL2xvY2FsaG9zdDo4MDgwL2tyLWRldi9yZW1vdGluZy9PU0NhY2hlTm90aWZpY2F0aW9uU2VydmljZXNyABFqYXZhLmxhbmcuSW50ZWdlchLioKT3gYc4AgABSQAFdmFsdWV4cgAQamF2YS5sYW5nLk51bWJlcoaslR0LlOCLAgAAeHAAAAABc3IADmphdmEubGFuZy5Mb25nO4vkkMyPI98CAAFKAAV2YWx1ZXhxAH4AHQAAAAAAAA99c3IAGWphdmF4LnhtbC5uYW1lc3BhY2UuUU5hbWWBbagt/DvdbAIAA0wACWxvY2FsUGFydHEAfgAETAAMbmFtZXNwYWNlVVJJcQB+AARMAAZwcmVmaXhxAH4ABHhwdAAaT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2V0AABxAH4AJHQHdHJPMEFCWE55QURKdmNtY3VhM1ZoYkdrdWNtbGpaUzVyYzJJdWJXVnpjMkZuYVc1bkxrcGhkbUZUWlhKMmFXTmxSR1ZtYVc1cGRHbHZicmJuRFY5Qk5HenhBZ0FCVEFBUmMyVnlkbWxqWlVsdWRHVnlabUZqWlhOMEFCQk1hbUYyWVM5MWRHbHNMMHhwYzNRN2VISUFMbTl5Wnk1cmRXRnNhUzV5YVdObExtdHpZaTV0WlhOellXZHBibWN1VTJWeWRtbGpaVVJsWm1sdWFYUnBiMjRBbXdKUFdOMHBmZ0lBRFV3QUMySjFjMU5sWTNWeWFYUjVkQUFUVEdwaGRtRXZiR0Z1Wnk5Q2IyOXNaV0Z1TzB3QUQyTnlaV1JsYm5ScFlXeHpWSGx3WlhRQVRFeHZjbWN2YTNWaGJHa3ZjbWxqWlM5amIzSmxMM05sWTNWeWFYUjVMMk55WldSbGJuUnBZV3h6TDBOeVpXUmxiblJwWVd4elUyOTFjbU5sSkVOeVpXUmxiblJwWVd4elZIbHdaVHRNQUJCc2IyTmhiRk5sY25acFkyVk9ZVzFsZEFBU1RHcGhkbUV2YkdGdVp5OVRkSEpwYm1jN1RBQVhiV1Z6YzJGblpVVjRZMlZ3ZEdsdmJraGhibVJzWlhKeEFINEFCVXdBREcxcGJHeHBjMVJ2VEdsMlpYUUFFRXhxWVhaaEwyeGhibWN2VEc5dVp6dE1BQWh3Y21sdmNtbDBlWFFBRTB4cVlYWmhMMnhoYm1jdlNXNTBaV2RsY2p0TUFBVnhkV1YxWlhFQWZnQURUQUFOY21WMGNubEJkSFJsYlhCMGMzRUFmZ0FIVEFBSGMyVnlkbWxqWlhRQUVreHFZWFpoTDJ4aGJtY3ZUMkpxWldOME8wd0FEM05sY25acFkyVkZibVJRYjJsdWRIUUFEa3hxWVhaaEwyNWxkQzlWVWt3N1RBQVRjMlZ5ZG1salpVNWhiV1ZUY0dGalpWVlNTWEVBZmdBRlRBQVFjMlZ5ZG1salpVNWhiV1Z6Y0dGalpYRUFmZ0FGVEFBTGMyVnlkbWxqWlZCaGRHaHhBSDRBQlhod2MzSUFFV3BoZG1FdWJHRnVaeTVDYjI5c1pXRnV6U0J5Z05XYyt1NENBQUZhQUFWMllXeDFaWGh3QVhCMEFCcFBVME5oWTJobFRtOTBhV1pwWTJGMGFXOXVVMlZ5ZG1salpYUUFUVzl5Wnk1cmRXRnNhUzV5YVdObExtdHpZaTV0WlhOellXZHBibWN1WlhoalpYQjBhVzl1YUdGdVpHeHBibWN1UkdWbVlYVnNkRTFsYzNOaFoyVkZlR05sY0hScGIyNUlZVzVrYkdWeWMzSUFEbXBoZG1FdWJHRnVaeTVNYjI1bk80dmtrTXlQSTk4Q0FBRktBQVYyWVd4MVpYaHlBQkJxWVhaaExteGhibWN1VG5WdFltVnlocXlWSFF1VTRJc0NBQUI0Y1AvLy8vLy8vLy8vYzNJQUVXcGhkbUV1YkdGdVp5NUpiblJsWjJWeUV1S2dwUGVCaHpnQ0FBRkpBQVYyWVd4MVpYaHhBSDRBRUFBQUFBTnpjUUIrQUFzQWNRQitBQk53YzNJQURHcGhkbUV1Ym1WMExsVlNUSllsTnpZYS9PUnlBd0FIU1FBSWFHRnphRU52WkdWSkFBUndiM0owVEFBSllYVjBhRzl5YVhSNWNRQitBQVZNQUFSbWFXeGxjUUIrQUFWTUFBUm9iM04wY1FCK0FBVk1BQWh3Y205MGIyTnZiSEVBZmdBRlRBQURjbVZtY1FCK0FBVjRjUC8vLy84QUFCK1FkQUFPYkc5allXeG9iM04wT2pnd09EQjBBQ3N2YTNJdFpHVjJMM0psYlc5MGFXNW5MMDlUUTJGamFHVk9iM1JwWm1sallYUnBiMjVUWlhKMmFXTmxkQUFKYkc5allXeG9iM04wZEFBRWFIUjBjSEI0ZEFBQWRBQUdWRkpCVmtWTWRBQUJMM055QUJOcVlYWmhMblYwYVd3dVFYSnlZWGxNYVhOMGVJSFNIWm5IWVowREFBRkpBQVJ6YVhwbGVIQUFBQUFGZHdRQUFBQUtkQUF6';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 385    FOR UPDATE;        
    buffer := 'YjNKbkxtdDFZV3hwTG5KcFkyVXVhM05pTG0xbGMzTmhaMmx1Wnk1elpYSjJhV05sTGt0VFFrcGhkbUZUWlhKMmFXTmxkQUE4WTI5dExtOXdaVzV6ZVcxd2FHOXVlUzV2YzJOaFkyaGxMbUpoYzJVdVpYWmxiblJ6TGtOaFkyaGxSVzUwY25sRmRtVnVkRXhwYzNSbGJtVnlkQUEzWTI5dExtOXdaVzV6ZVcxd2FHOXVlUzV2YzJOaFkyaGxMbUpoYzJVdVpYWmxiblJ6TGtOaFkyaGxSWFpsYm5STWFYTjBaVzVsY25RQUYycGhkbUV1ZFhScGJDNUZkbVZ1ZEV4cGMzUmxibVZ5ZEFBc1kyOXRMbTl3Wlc1emVXMXdhRzl1ZVM1dmMyTmhZMmhsTG1KaGMyVXVUR2xtWldONVkyeGxRWGRoY21WNHQADTEyOS4xODYuNzkuNDVzcgAyb3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5KYXZhU2VydmljZURlZmluaXRpb2625w1fQTRs8QIAAUwAEXNlcnZpY2VJbnRlcmZhY2VzdAAQTGphdmEvdXRpbC9MaXN0O3hyAC5vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLlNlcnZpY2VEZWZpbml0aW9uAJsCT1jdKX4CAA1MAAtidXNTZWN1cml0eXEAfgATTAAPY3JlZGVudGlhbHNUeXBldABMTG9yZy9rdWFsaS9yaWNlL2NvcmUvc2VjdXJpdHkvY3JlZGVudGlhbHMvQ3JlZGVudGlhbHNTb3VyY2UkQ3JlZGVudGlhbHNUeXBlO0wAEGxvY2FsU2VydmljZU5hbWVxAH4ABEwAF21lc3NhZ2VFeGNlcHRpb25IYW5kbGVycQB+AARMAAxtaWxsaXNUb0xpdmVxAH4AFUwACHByaW9yaXR5cQB+ABRMAAVxdWV1ZXEAfgATTAANcmV0cnlBdHRlbXB0c3EAfgAUTAAHc2VydmljZXQAEkxqYXZhL2xhbmcvT2JqZWN0O0wAD3NlcnZpY2VFbmRQb2ludHQADkxqYXZhL25ldC9VUkw7TAATc2VydmljZU5hbWVTcGFjZVVSSXEAfgAETAAQc2VydmljZU5hbWVzcGFjZXEAfgAETAALc2VydmljZVBhdGhxAH4ABHhwc3EAfgAZAXB0ABpPU0NhY2hlTm90aWZpY2F0aW9uU2VydmljZXQATW9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuZXhjZXB0aW9uaGFuZGxpbmcuRGVmYXVsdE1lc3NhZ2VFeGNlcHRpb25IYW5kbGVyc3EAfgAf//////////9zcQB+ABwAAAADc3EAfgAZAHEAfgAycHNyAAxqYXZhLm5ldC5VUkyWJTc2GvzkcgMAB0kACGhhc2hDb2RlSQAEcG9ydEwACWF1dGhvcml0eXEAfgAETAAEZmlsZXEAfgAETAAEaG9zdHEAfgAETAAIcHJvdG9jb2xxAH4ABEwAA3JlZnEAfgAEeHBpG6KgAAAfkHQADmxvY2FsaG9zdDo4MDgwdAArL2tyLWRldi9yZW1vdGluZy9PU0NhY2hlTm90aWZpY2F0aW9uU2VydmljZXQACWxvY2FsaG9zdHQABGh0dHBweHQAAHQABlRSQVZFTHQAAS9zcgATamF2YS51dGlsLkFycmF5TGlzdHiB0h2Zx2GdAwABSQAEc2l6ZXhwAAAABXcEAAAACnQAM29yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuc2VydmljZS5LU0JKYXZhU2VydmljZXQAPGNvbS5vcGVuc3ltcGhvbnkub3NjYWNoZS5iYXNlLmV2ZW50cy5DYWNoZUVudHJ5RXZlbnRMaXN0ZW5lcnQAN2NvbS5vcGVuc3ltcGhvbnkub3NjYWNoZS5iYXNlLmV2ZW50cy5DYWNoZUV2ZW50TGlzdGVuZXJ0ABdqYXZhLnV0aWwuRXZlbnRMaXN0ZW5lcnQALGNvbS5vcGVuc3ltcGhvbnkub3NjYWNoZS5iYXNlLkxpZmVjeWNsZUF3YXJleHEAfgAjdAAGVFJBVkVM';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
INSERT INTO KRSB_MSG_PYLD_T (MSG_PYLD,MSG_QUE_ID)
  VALUES (EMPTY_CLOB(),386)
/
-- Length: 6112
-- Chunks: 2
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 386    FOR UPDATE;        
    buffer := 'rO0ABXNyAC1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLkFzeW5jaHJvbm91c0NhbGzxnQ5Y0t0lZQIAB1oAFWlnbm9yZVN0b3JlQW5kRm9yd2FyZFsACWFyZ3VtZW50c3QAE1tMamF2YS9sYW5nL09iamVjdDtMAAhjYWxsYmFja3QAM0xvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL0FzeW5jaHJvbm91c0NhbGxiYWNrO0wAB2NvbnRleHR0ABZMamF2YS9pby9TZXJpYWxpemFibGU7TAAKbWV0aG9kTmFtZXQAEkxqYXZhL2xhbmcvU3RyaW5nO1sACnBhcmFtVHlwZXN0ABJbTGphdmEvbGFuZy9DbGFzcztMAAtzZXJ2aWNlSW5mb3QAKkxvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL1NlcnZpY2VJbmZvO3hwAHVyABNbTGphdmEubGFuZy5PYmplY3Q7kM5YnxBzKWwCAAB4cAAAAAFzcgBDY29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLnBsdWdpbnMuY2x1c3RlcnN1cHBvcnQuQ2x1c3Rlck5vdGlmaWNhdGlvbhY+6VckvG9pAgACSQAEdHlwZUwABGRhdGFxAH4AA3hwAAAAAnQANURvY3VtZW50VHlwZVJ1bGVDYWNoZTpSZWNpcGVQYXJlbnRNYWludGVuYW5jZURvY3VtZW50cHB0AAZpbnZva2V1cgASW0xqYXZhLmxhbmcuQ2xhc3M7qxbXrsvNWpkCAAB4cAAAAAF2cgAUamF2YS5pby5TZXJpYWxpemFibGUQm2EN15tk7QIAAHhwc3IAKG9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuU2VydmljZUluZm/FFyO2KQS3ugIAC0wABWFsaXZldAATTGphdmEvbGFuZy9Cb29sZWFuO0wAFGVuZHBvaW50QWx0ZXJuYXRlVXJscQB+AARMAAtlbmRwb2ludFVybHEAfgAETAAKbG9ja1Zlck5icnQAE0xqYXZhL2xhbmcvSW50ZWdlcjtMAA5tZXNzYWdlRW50cnlJZHQAEExqYXZhL2xhbmcvTG9uZztMAAVxbmFtZXQAG0xqYXZheC94bWwvbmFtZXNwYWNlL1FOYW1lO0wAGnNlcmlhbGl6ZWRTZXJ2aWNlTmFtZXNwYWNlcQB+AARMAAhzZXJ2ZXJJcHEAfgAETAARc2VydmljZURlZmluaXRpb250ADBMb3JnL2t1YWxpL3JpY2Uva3NiL21lc3NhZ2luZy9TZXJ2aWNlRGVmaW5pdGlvbjtMAAtzZXJ2aWNlTmFtZXEAfgAETAAQc2VydmljZU5hbWVzcGFjZXEAfgAEeHBzcgARamF2YS5sYW5nLkJvb2xlYW7NIHKA1Zz67gIAAVoABXZhbHVleHABcHQAQGh0dHA6Ly9sb2NhbGhvc3Q6ODA4MC9rci1kZXYvcmVtb3RpbmcvT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2VzcgARamF2YS5sYW5nLkludGVnZXIS4qCk94GHOAIAAUkABXZhbHVleHIAEGphdmEubGFuZy5OdW1iZXKGrJUdC5TgiwIAAHhwAAAAAXNyAA5qYXZhLmxhbmcuTG9uZzuL5JDMjyPfAgABSgAFdmFsdWV4cQB+AB0AAAAAAAAPfXNyABlqYXZheC54bWwubmFtZXNwYWNlLlFOYW1lgW2oLfw73WwCAANMAAlsb2NhbFBhcnRxAH4ABEwADG5hbWVzcGFjZVVSSXEAfgAETAAGcHJlZml4cQB+AAR4cHQAGk9TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNldAAAcQB+ACR0B3RyTzBBQlhOeUFESnZjbWN1YTNWaGJHa3VjbWxqWlM1cmMySXViV1Z6YzJGbmFXNW5Ma3BoZG1GVFpYSjJhV05sUkdWbWFXNXBkR2x2YnJibkRWOUJOR3p4QWdBQlRBQVJjMlZ5ZG1salpVbHVkR1Z5Wm1GalpYTjBBQkJNYW1GMllTOTFkR2xzTDB4cGMzUTdlSElBTG05eVp5NXJkV0ZzYVM1eWFXTmxMbXR6WWk1dFpYTnpZV2RwYm1jdVUyVnlkbWxqWlVSbFptbHVhWFJwYjI0QW13SlBXTjBwZmdJQURVd0FDMkoxYzFObFkzVnlhWFI1ZEFBVFRHcGhkbUV2YkdGdVp5OUNiMjlzWldGdU8wd0FEMk55WldSbGJuUnBZV3h6Vkhsd1pYUUFURXh2Y21jdmEzVmhiR2t2Y21salpTOWpiM0psTDNObFkzVnlhWFI1TDJOeVpXUmxiblJwWVd4ekwwTnlaV1JsYm5ScFlXeHpVMjkxY21ObEpFTnlaV1JsYm5ScFlXeHpWSGx3WlR0TUFCQnNiMk5oYkZObGNuWnBZMlZPWVcxbGRBQVNUR3BoZG1FdmJHRnVaeTlUZEhKcGJtYzdUQUFYYldWemMyRm5aVVY0WTJWd2RHbHZia2hoYm1Sc1pYSnhBSDRBQlV3QURHMXBiR3hwYzFSdlRHbDJaWFFBRUV4cVlYWmhMMnhoYm1jdlRHOXVaenRNQUFod2NtbHZjbWwwZVhRQUUweHFZWFpoTDJ4aGJtY3ZTVzUwWldkbGNqdE1BQVZ4ZFdWMVpYRUFmZ0FEVEFBTmNtVjBjbmxCZEhSbGJYQjBjM0VBZmdBSFRBQUhjMlZ5ZG1salpYUUFFa3hxWVhaaEwyeGhibWN2VDJKcVpXTjBPMHdBRDNObGNuWnBZMlZGYm1SUWIybHVkSFFBRGt4cVlYWmhMMjVsZEM5VlVrdzdUQUFUYzJWeWRtbGpaVTVoYldWVGNHRmpaVlZTU1hFQWZnQUZUQUFRYzJWeWRtbGpaVTVoYldWemNHRmpaWEVBZmdBRlRBQUxjMlZ5ZG1salpWQmhkR2h4QUg0QUJYaHdjM0lBRVdwaGRtRXViR0Z1Wnk1Q2IyOXNaV0Z1elNCeWdOV2MrdTRDQUFGYUFBVjJZV3gxWlhod0FYQjBBQnBQVTBOaFkyaGxUbTkwYVdacFkyRjBhVzl1VTJWeWRtbGpaWFFBVFc5eVp5NXJkV0ZzYVM1eWFXTmxMbXR6WWk1dFpYTnpZV2RwYm1jdVpYaGpaWEIwYVc5dWFHRnVaR3hwYm1jdVJHVm1ZWFZzZEUxbGMzTmhaMlZGZUdObGNIUnBiMjVJWVc1a2JHVnljM0lBRG1waGRtRXViR0Z1Wnk1TWIyNW5PNHZra015UEk5OENBQUZLQUFWMllXeDFaWGh5QUJCcVlYWmhMbXhoYm1jdVRuVnRZbVZ5aHF5VkhRdVU0SXNDQUFCNGNQLy8vLy8vLy8vL2MzSUFFV3BoZG1FdWJHRnVaeTVKYm5SbFoyVnlFdUtncFBlQmh6Z0NBQUZKQUFWMllXeDFaWGh4QUg0QUVBQUFBQU56Y1FCK0FBc0FjUUIrQUJOd2MzSUFER3BoZG1FdWJtVjBMbFZTVEpZbE56WWEvT1J5QXdBSFNRQUlhR0Z6YUVOdlpHVkpBQVJ3YjNKMFRBQUpZWFYwYUc5eWFYUjVjUUIrQUFWTUFBUm1hV3hsY1FCK0FBVk1BQVJvYjNOMGNRQitBQVZNQUFod2NtOTBiMk52YkhFQWZnQUZUQUFEY21WbWNRQitBQVY0Y1AvLy8vOEFBQitRZEFBT2JHOWpZV3hvYjNOME9qZ3dPREIwQUNzdmEzSXRaR1YyTDNKbGJXOTBhVzVuTDA5VFEyRmphR1ZPYjNScFptbGpZWFJwYjI1VFpYSjJhV05sZEFBSmJHOWpZV3hvYjNOMGRBQUVhSFIwY0hCNGRBQUFkQUFHVkZKQlZrVk1kQUFCTDNOeUFCTnFZWFpoTG5WMGFXd3VRWEp5WVhsTWFYTjBlSUhTSFpuSFlaMERBQUZKQUFSemFYcGxlSEFBQUFBRmR3UUFBQUFLZEFB';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 386    FOR UPDATE;        
    buffer := 'emIzSm5MbXQxWVd4cExuSnBZMlV1YTNOaUxtMWxjM05oWjJsdVp5NXpaWEoyYVdObExrdFRRa3BoZG1GVFpYSjJhV05sZEFBOFkyOXRMbTl3Wlc1emVXMXdhRzl1ZVM1dmMyTmhZMmhsTG1KaGMyVXVaWFpsYm5SekxrTmhZMmhsUlc1MGNubEZkbVZ1ZEV4cGMzUmxibVZ5ZEFBM1kyOXRMbTl3Wlc1emVXMXdhRzl1ZVM1dmMyTmhZMmhsTG1KaGMyVXVaWFpsYm5SekxrTmhZMmhsUlhabGJuUk1hWE4wWlc1bGNuUUFGMnBoZG1FdWRYUnBiQzVGZG1WdWRFeHBjM1JsYm1WeWRBQXNZMjl0TG05d1pXNXplVzF3YUc5dWVTNXZjMk5oWTJobExtSmhjMlV1VEdsbVpXTjVZMnhsUVhkaGNtVjR0AA0xMjkuMTg2Ljc5LjQ1c3IAMm9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuSmF2YVNlcnZpY2VEZWZpbml0aW9utucNX0E0bPECAAFMABFzZXJ2aWNlSW50ZXJmYWNlc3QAEExqYXZhL3V0aWwvTGlzdDt4cgAub3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5TZXJ2aWNlRGVmaW5pdGlvbgCbAk9Y3Sl+AgANTAALYnVzU2VjdXJpdHlxAH4AE0wAD2NyZWRlbnRpYWxzVHlwZXQATExvcmcva3VhbGkvcmljZS9jb3JlL3NlY3VyaXR5L2NyZWRlbnRpYWxzL0NyZWRlbnRpYWxzU291cmNlJENyZWRlbnRpYWxzVHlwZTtMABBsb2NhbFNlcnZpY2VOYW1lcQB+AARMABdtZXNzYWdlRXhjZXB0aW9uSGFuZGxlcnEAfgAETAAMbWlsbGlzVG9MaXZlcQB+ABVMAAhwcmlvcml0eXEAfgAUTAAFcXVldWVxAH4AE0wADXJldHJ5QXR0ZW1wdHNxAH4AFEwAB3NlcnZpY2V0ABJMamF2YS9sYW5nL09iamVjdDtMAA9zZXJ2aWNlRW5kUG9pbnR0AA5MamF2YS9uZXQvVVJMO0wAE3NlcnZpY2VOYW1lU3BhY2VVUklxAH4ABEwAEHNlcnZpY2VOYW1lc3BhY2VxAH4ABEwAC3NlcnZpY2VQYXRocQB+AAR4cHNxAH4AGQFwdAAaT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2V0AE1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLmV4Y2VwdGlvbmhhbmRsaW5nLkRlZmF1bHRNZXNzYWdlRXhjZXB0aW9uSGFuZGxlcnNxAH4AH///////////c3EAfgAcAAAAA3NxAH4AGQBxAH4AMnBzcgAMamF2YS5uZXQuVVJMliU3Nhr85HIDAAdJAAhoYXNoQ29kZUkABHBvcnRMAAlhdXRob3JpdHlxAH4ABEwABGZpbGVxAH4ABEwABGhvc3RxAH4ABEwACHByb3RvY29scQB+AARMAANyZWZxAH4ABHhwaRuioAAAH5B0AA5sb2NhbGhvc3Q6ODA4MHQAKy9rci1kZXYvcmVtb3RpbmcvT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2V0AAlsb2NhbGhvc3R0AARodHRwcHh0AAB0AAZUUkFWRUx0AAEvc3IAE2phdmEudXRpbC5BcnJheUxpc3R4gdIdmcdhnQMAAUkABHNpemV4cAAAAAV3BAAAAAp0ADNvcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLnNlcnZpY2UuS1NCSmF2YVNlcnZpY2V0ADxjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5ldmVudHMuQ2FjaGVFbnRyeUV2ZW50TGlzdGVuZXJ0ADdjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5ldmVudHMuQ2FjaGVFdmVudExpc3RlbmVydAAXamF2YS51dGlsLkV2ZW50TGlzdGVuZXJ0ACxjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5MaWZlY3ljbGVBd2FyZXhxAH4AI3QABlRSQVZFTA==';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
INSERT INTO KRSB_MSG_PYLD_T (MSG_PYLD,MSG_QUE_ID)
  VALUES (EMPTY_CLOB(),387)
/
-- Length: 6112
-- Chunks: 2
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 387    FOR UPDATE;        
    buffer := 'rO0ABXNyAC1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLkFzeW5jaHJvbm91c0NhbGzxnQ5Y0t0lZQIAB1oAFWlnbm9yZVN0b3JlQW5kRm9yd2FyZFsACWFyZ3VtZW50c3QAE1tMamF2YS9sYW5nL09iamVjdDtMAAhjYWxsYmFja3QAM0xvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL0FzeW5jaHJvbm91c0NhbGxiYWNrO0wAB2NvbnRleHR0ABZMamF2YS9pby9TZXJpYWxpemFibGU7TAAKbWV0aG9kTmFtZXQAEkxqYXZhL2xhbmcvU3RyaW5nO1sACnBhcmFtVHlwZXN0ABJbTGphdmEvbGFuZy9DbGFzcztMAAtzZXJ2aWNlSW5mb3QAKkxvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL1NlcnZpY2VJbmZvO3hwAHVyABNbTGphdmEubGFuZy5PYmplY3Q7kM5YnxBzKWwCAAB4cAAAAAFzcgBDY29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLnBsdWdpbnMuY2x1c3RlcnN1cHBvcnQuQ2x1c3Rlck5vdGlmaWNhdGlvbhY+6VckvG9pAgACSQAEdHlwZUwABGRhdGFxAH4AA3hwAAAAAnQAN0RvY3VtZW50VHlwZVJ1bGVDYWNoZTpSZWNpcGVDYXRlZ29yeU1haW50ZW5hbmNlRG9jdW1lbnRwcHQABmludm9rZXVyABJbTGphdmEubGFuZy5DbGFzczurFteuy81amQIAAHhwAAAAAXZyABRqYXZhLmlvLlNlcmlhbGl6YWJsZRCbYQ3Xm2TtAgAAeHBzcgAob3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5TZXJ2aWNlSW5mb8UXI7YpBLe6AgALTAAFYWxpdmV0ABNMamF2YS9sYW5nL0Jvb2xlYW47TAAUZW5kcG9pbnRBbHRlcm5hdGVVcmxxAH4ABEwAC2VuZHBvaW50VXJscQB+AARMAApsb2NrVmVyTmJydAATTGphdmEvbGFuZy9JbnRlZ2VyO0wADm1lc3NhZ2VFbnRyeUlkdAAQTGphdmEvbGFuZy9Mb25nO0wABXFuYW1ldAAbTGphdmF4L3htbC9uYW1lc3BhY2UvUU5hbWU7TAAac2VyaWFsaXplZFNlcnZpY2VOYW1lc3BhY2VxAH4ABEwACHNlcnZlcklwcQB+AARMABFzZXJ2aWNlRGVmaW5pdGlvbnQAMExvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL1NlcnZpY2VEZWZpbml0aW9uO0wAC3NlcnZpY2VOYW1lcQB+AARMABBzZXJ2aWNlTmFtZXNwYWNlcQB+AAR4cHNyABFqYXZhLmxhbmcuQm9vbGVhbs0gcoDVnPruAgABWgAFdmFsdWV4cAFwdABAaHR0cDovL2xvY2FsaG9zdDo4MDgwL2tyLWRldi9yZW1vdGluZy9PU0NhY2hlTm90aWZpY2F0aW9uU2VydmljZXNyABFqYXZhLmxhbmcuSW50ZWdlchLioKT3gYc4AgABSQAFdmFsdWV4cgAQamF2YS5sYW5nLk51bWJlcoaslR0LlOCLAgAAeHAAAAABc3IADmphdmEubGFuZy5Mb25nO4vkkMyPI98CAAFKAAV2YWx1ZXhxAH4AHQAAAAAAAA99c3IAGWphdmF4LnhtbC5uYW1lc3BhY2UuUU5hbWWBbagt/DvdbAIAA0wACWxvY2FsUGFydHEAfgAETAAMbmFtZXNwYWNlVVJJcQB+AARMAAZwcmVmaXhxAH4ABHhwdAAaT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2V0AABxAH4AJHQHdHJPMEFCWE55QURKdmNtY3VhM1ZoYkdrdWNtbGpaUzVyYzJJdWJXVnpjMkZuYVc1bkxrcGhkbUZUWlhKMmFXTmxSR1ZtYVc1cGRHbHZicmJuRFY5Qk5HenhBZ0FCVEFBUmMyVnlkbWxqWlVsdWRHVnlabUZqWlhOMEFCQk1hbUYyWVM5MWRHbHNMMHhwYzNRN2VISUFMbTl5Wnk1cmRXRnNhUzV5YVdObExtdHpZaTV0WlhOellXZHBibWN1VTJWeWRtbGpaVVJsWm1sdWFYUnBiMjRBbXdKUFdOMHBmZ0lBRFV3QUMySjFjMU5sWTNWeWFYUjVkQUFUVEdwaGRtRXZiR0Z1Wnk5Q2IyOXNaV0Z1TzB3QUQyTnlaV1JsYm5ScFlXeHpWSGx3WlhRQVRFeHZjbWN2YTNWaGJHa3ZjbWxqWlM5amIzSmxMM05sWTNWeWFYUjVMMk55WldSbGJuUnBZV3h6TDBOeVpXUmxiblJwWVd4elUyOTFjbU5sSkVOeVpXUmxiblJwWVd4elZIbHdaVHRNQUJCc2IyTmhiRk5sY25acFkyVk9ZVzFsZEFBU1RHcGhkbUV2YkdGdVp5OVRkSEpwYm1jN1RBQVhiV1Z6YzJGblpVVjRZMlZ3ZEdsdmJraGhibVJzWlhKeEFINEFCVXdBREcxcGJHeHBjMVJ2VEdsMlpYUUFFRXhxWVhaaEwyeGhibWN2VEc5dVp6dE1BQWh3Y21sdmNtbDBlWFFBRTB4cVlYWmhMMnhoYm1jdlNXNTBaV2RsY2p0TUFBVnhkV1YxWlhFQWZnQURUQUFOY21WMGNubEJkSFJsYlhCMGMzRUFmZ0FIVEFBSGMyVnlkbWxqWlhRQUVreHFZWFpoTDJ4aGJtY3ZUMkpxWldOME8wd0FEM05sY25acFkyVkZibVJRYjJsdWRIUUFEa3hxWVhaaEwyNWxkQzlWVWt3N1RBQVRjMlZ5ZG1salpVNWhiV1ZUY0dGalpWVlNTWEVBZmdBRlRBQVFjMlZ5ZG1salpVNWhiV1Z6Y0dGalpYRUFmZ0FGVEFBTGMyVnlkbWxqWlZCaGRHaHhBSDRBQlhod2MzSUFFV3BoZG1FdWJHRnVaeTVDYjI5c1pXRnV6U0J5Z05XYyt1NENBQUZhQUFWMllXeDFaWGh3QVhCMEFCcFBVME5oWTJobFRtOTBhV1pwWTJGMGFXOXVVMlZ5ZG1salpYUUFUVzl5Wnk1cmRXRnNhUzV5YVdObExtdHpZaTV0WlhOellXZHBibWN1WlhoalpYQjBhVzl1YUdGdVpHeHBibWN1UkdWbVlYVnNkRTFsYzNOaFoyVkZlR05sY0hScGIyNUlZVzVrYkdWeWMzSUFEbXBoZG1FdWJHRnVaeTVNYjI1bk80dmtrTXlQSTk4Q0FBRktBQVYyWVd4MVpYaHlBQkJxWVhaaExteGhibWN1VG5WdFltVnlocXlWSFF1VTRJc0NBQUI0Y1AvLy8vLy8vLy8vYzNJQUVXcGhkbUV1YkdGdVp5NUpiblJsWjJWeUV1S2dwUGVCaHpnQ0FBRkpBQVYyWVd4MVpYaHhBSDRBRUFBQUFBTnpjUUIrQUFzQWNRQitBQk53YzNJQURHcGhkbUV1Ym1WMExsVlNUSllsTnpZYS9PUnlBd0FIU1FBSWFHRnphRU52WkdWSkFBUndiM0owVEFBSllYVjBhRzl5YVhSNWNRQitBQVZNQUFSbWFXeGxjUUIrQUFWTUFBUm9iM04wY1FCK0FBVk1BQWh3Y205MGIyTnZiSEVBZmdBRlRBQURjbVZtY1FCK0FBVjRjUC8vLy84QUFCK1FkQUFPYkc5allXeG9iM04wT2pnd09EQjBBQ3N2YTNJdFpHVjJMM0psYlc5MGFXNW5MMDlUUTJGamFHVk9iM1JwWm1sallYUnBiMjVUWlhKMmFXTmxkQUFKYkc5allXeG9iM04wZEFBRWFIUjBjSEI0ZEFBQWRBQUdWRkpCVmtWTWRBQUJMM055QUJOcVlYWmhMblYwYVd3dVFYSnlZWGxNYVhOMGVJSFNIWm5IWVowREFBRkpBQVJ6YVhwbGVIQUFBQUFGZHdRQUFBQUtk';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 387    FOR UPDATE;        
    buffer := 'QUF6YjNKbkxtdDFZV3hwTG5KcFkyVXVhM05pTG0xbGMzTmhaMmx1Wnk1elpYSjJhV05sTGt0VFFrcGhkbUZUWlhKMmFXTmxkQUE4WTI5dExtOXdaVzV6ZVcxd2FHOXVlUzV2YzJOaFkyaGxMbUpoYzJVdVpYWmxiblJ6TGtOaFkyaGxSVzUwY25sRmRtVnVkRXhwYzNSbGJtVnlkQUEzWTI5dExtOXdaVzV6ZVcxd2FHOXVlUzV2YzJOaFkyaGxMbUpoYzJVdVpYWmxiblJ6TGtOaFkyaGxSWFpsYm5STWFYTjBaVzVsY25RQUYycGhkbUV1ZFhScGJDNUZkbVZ1ZEV4cGMzUmxibVZ5ZEFBc1kyOXRMbTl3Wlc1emVXMXdhRzl1ZVM1dmMyTmhZMmhsTG1KaGMyVXVUR2xtWldONVkyeGxRWGRoY21WNHQADTEyOS4xODYuNzkuNDVzcgAyb3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5KYXZhU2VydmljZURlZmluaXRpb2625w1fQTRs8QIAAUwAEXNlcnZpY2VJbnRlcmZhY2VzdAAQTGphdmEvdXRpbC9MaXN0O3hyAC5vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLlNlcnZpY2VEZWZpbml0aW9uAJsCT1jdKX4CAA1MAAtidXNTZWN1cml0eXEAfgATTAAPY3JlZGVudGlhbHNUeXBldABMTG9yZy9rdWFsaS9yaWNlL2NvcmUvc2VjdXJpdHkvY3JlZGVudGlhbHMvQ3JlZGVudGlhbHNTb3VyY2UkQ3JlZGVudGlhbHNUeXBlO0wAEGxvY2FsU2VydmljZU5hbWVxAH4ABEwAF21lc3NhZ2VFeGNlcHRpb25IYW5kbGVycQB+AARMAAxtaWxsaXNUb0xpdmVxAH4AFUwACHByaW9yaXR5cQB+ABRMAAVxdWV1ZXEAfgATTAANcmV0cnlBdHRlbXB0c3EAfgAUTAAHc2VydmljZXQAEkxqYXZhL2xhbmcvT2JqZWN0O0wAD3NlcnZpY2VFbmRQb2ludHQADkxqYXZhL25ldC9VUkw7TAATc2VydmljZU5hbWVTcGFjZVVSSXEAfgAETAAQc2VydmljZU5hbWVzcGFjZXEAfgAETAALc2VydmljZVBhdGhxAH4ABHhwc3EAfgAZAXB0ABpPU0NhY2hlTm90aWZpY2F0aW9uU2VydmljZXQATW9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuZXhjZXB0aW9uaGFuZGxpbmcuRGVmYXVsdE1lc3NhZ2VFeGNlcHRpb25IYW5kbGVyc3EAfgAf//////////9zcQB+ABwAAAADc3EAfgAZAHEAfgAycHNyAAxqYXZhLm5ldC5VUkyWJTc2GvzkcgMAB0kACGhhc2hDb2RlSQAEcG9ydEwACWF1dGhvcml0eXEAfgAETAAEZmlsZXEAfgAETAAEaG9zdHEAfgAETAAIcHJvdG9jb2xxAH4ABEwAA3JlZnEAfgAEeHBpG6KgAAAfkHQADmxvY2FsaG9zdDo4MDgwdAArL2tyLWRldi9yZW1vdGluZy9PU0NhY2hlTm90aWZpY2F0aW9uU2VydmljZXQACWxvY2FsaG9zdHQABGh0dHBweHQAAHQABlRSQVZFTHQAAS9zcgATamF2YS51dGlsLkFycmF5TGlzdHiB0h2Zx2GdAwABSQAEc2l6ZXhwAAAABXcEAAAACnQAM29yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuc2VydmljZS5LU0JKYXZhU2VydmljZXQAPGNvbS5vcGVuc3ltcGhvbnkub3NjYWNoZS5iYXNlLmV2ZW50cy5DYWNoZUVudHJ5RXZlbnRMaXN0ZW5lcnQAN2NvbS5vcGVuc3ltcGhvbnkub3NjYWNoZS5iYXNlLmV2ZW50cy5DYWNoZUV2ZW50TGlzdGVuZXJ0ABdqYXZhLnV0aWwuRXZlbnRMaXN0ZW5lcnQALGNvbS5vcGVuc3ltcGhvbnkub3NjYWNoZS5iYXNlLkxpZmVjeWNsZUF3YXJleHEAfgAjdAAGVFJBVkVM';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
INSERT INTO KRSB_MSG_PYLD_T (MSG_PYLD,MSG_QUE_ID)
  VALUES (EMPTY_CLOB(),388)
/
-- Length: 6116
-- Chunks: 2
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 388    FOR UPDATE;        
    buffer := 'rO0ABXNyAC1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLkFzeW5jaHJvbm91c0NhbGzxnQ5Y0t0lZQIAB1oAFWlnbm9yZVN0b3JlQW5kRm9yd2FyZFsACWFyZ3VtZW50c3QAE1tMamF2YS9sYW5nL09iamVjdDtMAAhjYWxsYmFja3QAM0xvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL0FzeW5jaHJvbm91c0NhbGxiYWNrO0wAB2NvbnRleHR0ABZMamF2YS9pby9TZXJpYWxpemFibGU7TAAKbWV0aG9kTmFtZXQAEkxqYXZhL2xhbmcvU3RyaW5nO1sACnBhcmFtVHlwZXN0ABJbTGphdmEvbGFuZy9DbGFzcztMAAtzZXJ2aWNlSW5mb3QAKkxvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL1NlcnZpY2VJbmZvO3hwAHVyABNbTGphdmEubGFuZy5PYmplY3Q7kM5YnxBzKWwCAAB4cAAAAAFzcgBDY29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLnBsdWdpbnMuY2x1c3RlcnN1cHBvcnQuQ2x1c3Rlck5vdGlmaWNhdGlvbhY+6VckvG9pAgACSQAEdHlwZUwABGRhdGFxAH4AA3hwAAAAAnQAOURvY3VtZW50VHlwZVJ1bGVDYWNoZTpSZWNpcGVJbmdyZWRpZW50TWFpbnRlbmFuY2VEb2N1bWVudHBwdAAGaW52b2tldXIAEltMamF2YS5sYW5nLkNsYXNzO6sW167LzVqZAgAAeHAAAAABdnIAFGphdmEuaW8uU2VyaWFsaXphYmxlEJthDdebZO0CAAB4cHNyAChvcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLlNlcnZpY2VJbmZvxRcjtikEt7oCAAtMAAVhbGl2ZXQAE0xqYXZhL2xhbmcvQm9vbGVhbjtMABRlbmRwb2ludEFsdGVybmF0ZVVybHEAfgAETAALZW5kcG9pbnRVcmxxAH4ABEwACmxvY2tWZXJOYnJ0ABNMamF2YS9sYW5nL0ludGVnZXI7TAAObWVzc2FnZUVudHJ5SWR0ABBMamF2YS9sYW5nL0xvbmc7TAAFcW5hbWV0ABtMamF2YXgveG1sL25hbWVzcGFjZS9RTmFtZTtMABpzZXJpYWxpemVkU2VydmljZU5hbWVzcGFjZXEAfgAETAAIc2VydmVySXBxAH4ABEwAEXNlcnZpY2VEZWZpbml0aW9udAAwTG9yZy9rdWFsaS9yaWNlL2tzYi9tZXNzYWdpbmcvU2VydmljZURlZmluaXRpb247TAALc2VydmljZU5hbWVxAH4ABEwAEHNlcnZpY2VOYW1lc3BhY2VxAH4ABHhwc3IAEWphdmEubGFuZy5Cb29sZWFuzSBygNWc+u4CAAFaAAV2YWx1ZXhwAXB0AEBodHRwOi8vbG9jYWxob3N0OjgwODAva3ItZGV2L3JlbW90aW5nL09TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNlc3IAEWphdmEubGFuZy5JbnRlZ2VyEuKgpPeBhzgCAAFJAAV2YWx1ZXhyABBqYXZhLmxhbmcuTnVtYmVyhqyVHQuU4IsCAAB4cAAAAAFzcgAOamF2YS5sYW5nLkxvbmc7i+SQzI8j3wIAAUoABXZhbHVleHEAfgAdAAAAAAAAD31zcgAZamF2YXgueG1sLm5hbWVzcGFjZS5RTmFtZYFtqC38O91sAgADTAAJbG9jYWxQYXJ0cQB+AARMAAxuYW1lc3BhY2VVUklxAH4ABEwABnByZWZpeHEAfgAEeHB0ABpPU0NhY2hlTm90aWZpY2F0aW9uU2VydmljZXQAAHEAfgAkdAd0ck8wQUJYTnlBREp2Y21jdWEzVmhiR2t1Y21salpTNXJjMkl1YldWemMyRm5hVzVuTGtwaGRtRlRaWEoyYVdObFJHVm1hVzVwZEdsdmJyYm5EVjlCTkd6eEFnQUJUQUFSYzJWeWRtbGpaVWx1ZEdWeVptRmpaWE4wQUJCTWFtRjJZUzkxZEdsc0wweHBjM1E3ZUhJQUxtOXlaeTVyZFdGc2FTNXlhV05sTG10ellpNXRaWE56WVdkcGJtY3VVMlZ5ZG1salpVUmxabWx1YVhScGIyNEFtd0pQV04wcGZnSUFEVXdBQzJKMWMxTmxZM1Z5YVhSNWRBQVRUR3BoZG1FdmJHRnVaeTlDYjI5c1pXRnVPMHdBRDJOeVpXUmxiblJwWVd4elZIbHdaWFFBVEV4dmNtY3ZhM1ZoYkdrdmNtbGpaUzlqYjNKbEwzTmxZM1Z5YVhSNUwyTnlaV1JsYm5ScFlXeHpMME55WldSbGJuUnBZV3h6VTI5MWNtTmxKRU55WldSbGJuUnBZV3h6Vkhsd1pUdE1BQkJzYjJOaGJGTmxjblpwWTJWT1lXMWxkQUFTVEdwaGRtRXZiR0Z1Wnk5VGRISnBibWM3VEFBWGJXVnpjMkZuWlVWNFkyVndkR2x2YmtoaGJtUnNaWEp4QUg0QUJVd0FERzFwYkd4cGMxUnZUR2wyWlhRQUVFeHFZWFpoTDJ4aGJtY3ZURzl1Wnp0TUFBaHdjbWx2Y21sMGVYUUFFMHhxWVhaaEwyeGhibWN2U1c1MFpXZGxjanRNQUFWeGRXVjFaWEVBZmdBRFRBQU5jbVYwY25sQmRIUmxiWEIwYzNFQWZnQUhUQUFIYzJWeWRtbGpaWFFBRWt4cVlYWmhMMnhoYm1jdlQySnFaV04wTzB3QUQzTmxjblpwWTJWRmJtUlFiMmx1ZEhRQURreHFZWFpoTDI1bGRDOVZVa3c3VEFBVGMyVnlkbWxqWlU1aGJXVlRjR0ZqWlZWU1NYRUFmZ0FGVEFBUWMyVnlkbWxqWlU1aGJXVnpjR0ZqWlhFQWZnQUZUQUFMYzJWeWRtbGpaVkJoZEdoeEFINEFCWGh3YzNJQUVXcGhkbUV1YkdGdVp5NUNiMjlzWldGdXpTQnlnTldjK3U0Q0FBRmFBQVYyWVd4MVpYaHdBWEIwQUJwUFUwTmhZMmhsVG05MGFXWnBZMkYwYVc5dVUyVnlkbWxqWlhRQVRXOXlaeTVyZFdGc2FTNXlhV05sTG10ellpNXRaWE56WVdkcGJtY3VaWGhqWlhCMGFXOXVhR0Z1Wkd4cGJtY3VSR1ZtWVhWc2RFMWxjM05oWjJWRmVHTmxjSFJwYjI1SVlXNWtiR1Z5YzNJQURtcGhkbUV1YkdGdVp5NU1iMjVuTzR2a2tNeVBJOThDQUFGS0FBVjJZV3gxWlhoeUFCQnFZWFpoTG14aGJtY3VUblZ0WW1WeWhxeVZIUXVVNElzQ0FBQjRjUC8vLy8vLy8vLy9jM0lBRVdwaGRtRXViR0Z1Wnk1SmJuUmxaMlZ5RXVLZ3BQZUJoemdDQUFGSkFBVjJZV3gxWlhoeEFINEFFQUFBQUFOemNRQitBQXNBY1FCK0FCTndjM0lBREdwaGRtRXVibVYwTGxWU1RKWWxOellhL09SeUF3QUhTUUFJYUdGemFFTnZaR1ZKQUFSd2IzSjBUQUFKWVhWMGFHOXlhWFI1Y1FCK0FBVk1BQVJtYVd4bGNRQitBQVZNQUFSb2IzTjBjUUIrQUFWTUFBaHdjbTkwYjJOdmJIRUFmZ0FGVEFBRGNtVm1jUUIrQUFWNGNQLy8vLzhBQUIrUWRBQU9iRzlqWVd4b2IzTjBPamd3T0RCMEFDc3ZhM0l0WkdWMkwzSmxiVzkwYVc1bkwwOVRRMkZqYUdWT2IzUnBabWxqWVhScGIyNVRaWEoyYVdObGRBQUpiRzlqWVd4b2IzTjBkQUFFYUhSMGNIQjRkQUFBZEFBR1ZGSkJWa1ZNZEFBQkwzTnlBQk5xWVhaaExuVjBhV3d1UVhKeVlYbE1hWE4wZUlIU0habkhZWjBEQUFGSkFBUnphWHBsZUhBQUFBQUZkd1FBQUFB';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 388    FOR UPDATE;        
    buffer := 'S2RBQXpiM0puTG10MVlXeHBMbkpwWTJVdWEzTmlMbTFsYzNOaFoybHVaeTV6WlhKMmFXTmxMa3RUUWtwaGRtRlRaWEoyYVdObGRBQThZMjl0TG05d1pXNXplVzF3YUc5dWVTNXZjMk5oWTJobExtSmhjMlV1WlhabGJuUnpMa05oWTJobFJXNTBjbmxGZG1WdWRFeHBjM1JsYm1WeWRBQTNZMjl0TG05d1pXNXplVzF3YUc5dWVTNXZjMk5oWTJobExtSmhjMlV1WlhabGJuUnpMa05oWTJobFJYWmxiblJNYVhOMFpXNWxjblFBRjJwaGRtRXVkWFJwYkM1RmRtVnVkRXhwYzNSbGJtVnlkQUFzWTI5dExtOXdaVzV6ZVcxd2FHOXVlUzV2YzJOaFkyaGxMbUpoYzJVdVRHbG1aV041WTJ4bFFYZGhjbVY0dAANMTI5LjE4Ni43OS40NXNyADJvcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLkphdmFTZXJ2aWNlRGVmaW5pdGlvbrbnDV9BNGzxAgABTAARc2VydmljZUludGVyZmFjZXN0ABBMamF2YS91dGlsL0xpc3Q7eHIALm9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuU2VydmljZURlZmluaXRpb24AmwJPWN0pfgIADUwAC2J1c1NlY3VyaXR5cQB+ABNMAA9jcmVkZW50aWFsc1R5cGV0AExMb3JnL2t1YWxpL3JpY2UvY29yZS9zZWN1cml0eS9jcmVkZW50aWFscy9DcmVkZW50aWFsc1NvdXJjZSRDcmVkZW50aWFsc1R5cGU7TAAQbG9jYWxTZXJ2aWNlTmFtZXEAfgAETAAXbWVzc2FnZUV4Y2VwdGlvbkhhbmRsZXJxAH4ABEwADG1pbGxpc1RvTGl2ZXEAfgAVTAAIcHJpb3JpdHlxAH4AFEwABXF1ZXVlcQB+ABNMAA1yZXRyeUF0dGVtcHRzcQB+ABRMAAdzZXJ2aWNldAASTGphdmEvbGFuZy9PYmplY3Q7TAAPc2VydmljZUVuZFBvaW50dAAOTGphdmEvbmV0L1VSTDtMABNzZXJ2aWNlTmFtZVNwYWNlVVJJcQB+AARMABBzZXJ2aWNlTmFtZXNwYWNlcQB+AARMAAtzZXJ2aWNlUGF0aHEAfgAEeHBzcQB+ABkBcHQAGk9TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNldABNb3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5leGNlcHRpb25oYW5kbGluZy5EZWZhdWx0TWVzc2FnZUV4Y2VwdGlvbkhhbmRsZXJzcQB+AB///////////3NxAH4AHAAAAANzcQB+ABkAcQB+ADJwc3IADGphdmEubmV0LlVSTJYlNzYa/ORyAwAHSQAIaGFzaENvZGVJAARwb3J0TAAJYXV0aG9yaXR5cQB+AARMAARmaWxlcQB+AARMAARob3N0cQB+AARMAAhwcm90b2NvbHEAfgAETAADcmVmcQB+AAR4cGkboqAAAB+QdAAObG9jYWxob3N0OjgwODB0ACsva3ItZGV2L3JlbW90aW5nL09TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNldAAJbG9jYWxob3N0dAAEaHR0cHB4dAAAdAAGVFJBVkVMdAABL3NyABNqYXZhLnV0aWwuQXJyYXlMaXN0eIHSHZnHYZ0DAAFJAARzaXpleHAAAAAFdwQAAAAKdAAzb3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5zZXJ2aWNlLktTQkphdmFTZXJ2aWNldAA8Y29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLmJhc2UuZXZlbnRzLkNhY2hlRW50cnlFdmVudExpc3RlbmVydAA3Y29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLmJhc2UuZXZlbnRzLkNhY2hlRXZlbnRMaXN0ZW5lcnQAF2phdmEudXRpbC5FdmVudExpc3RlbmVydAAsY29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLmJhc2UuTGlmZWN5Y2xlQXdhcmV4cQB+ACN0AAZUUkFWRUw=';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
INSERT INTO KRSB_MSG_PYLD_T (MSG_PYLD,MSG_QUE_ID)
  VALUES (EMPTY_CLOB(),389)
/
-- Length: 6104
-- Chunks: 2
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 389    FOR UPDATE;        
    buffer := 'rO0ABXNyAC1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLkFzeW5jaHJvbm91c0NhbGzxnQ5Y0t0lZQIAB1oAFWlnbm9yZVN0b3JlQW5kRm9yd2FyZFsACWFyZ3VtZW50c3QAE1tMamF2YS9sYW5nL09iamVjdDtMAAhjYWxsYmFja3QAM0xvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL0FzeW5jaHJvbm91c0NhbGxiYWNrO0wAB2NvbnRleHR0ABZMamF2YS9pby9TZXJpYWxpemFibGU7TAAKbWV0aG9kTmFtZXQAEkxqYXZhL2xhbmcvU3RyaW5nO1sACnBhcmFtVHlwZXN0ABJbTGphdmEvbGFuZy9DbGFzcztMAAtzZXJ2aWNlSW5mb3QAKkxvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL1NlcnZpY2VJbmZvO3hwAHVyABNbTGphdmEubGFuZy5PYmplY3Q7kM5YnxBzKWwCAAB4cAAAAAFzcgBDY29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLnBsdWdpbnMuY2x1c3RlcnN1cHBvcnQuQ2x1c3Rlck5vdGlmaWNhdGlvbhY+6VckvG9pAgACSQAEdHlwZUwABGRhdGFxAH4AA3hwAAAAAnQAL0RvY3VtZW50VHlwZVJ1bGVDYWNoZTpSZWNpcGVNYWludGVuYW5jZURvY3VtZW50cHB0AAZpbnZva2V1cgASW0xqYXZhLmxhbmcuQ2xhc3M7qxbXrsvNWpkCAAB4cAAAAAF2cgAUamF2YS5pby5TZXJpYWxpemFibGUQm2EN15tk7QIAAHhwc3IAKG9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuU2VydmljZUluZm/FFyO2KQS3ugIAC0wABWFsaXZldAATTGphdmEvbGFuZy9Cb29sZWFuO0wAFGVuZHBvaW50QWx0ZXJuYXRlVXJscQB+AARMAAtlbmRwb2ludFVybHEAfgAETAAKbG9ja1Zlck5icnQAE0xqYXZhL2xhbmcvSW50ZWdlcjtMAA5tZXNzYWdlRW50cnlJZHQAEExqYXZhL2xhbmcvTG9uZztMAAVxbmFtZXQAG0xqYXZheC94bWwvbmFtZXNwYWNlL1FOYW1lO0wAGnNlcmlhbGl6ZWRTZXJ2aWNlTmFtZXNwYWNlcQB+AARMAAhzZXJ2ZXJJcHEAfgAETAARc2VydmljZURlZmluaXRpb250ADBMb3JnL2t1YWxpL3JpY2Uva3NiL21lc3NhZ2luZy9TZXJ2aWNlRGVmaW5pdGlvbjtMAAtzZXJ2aWNlTmFtZXEAfgAETAAQc2VydmljZU5hbWVzcGFjZXEAfgAEeHBzcgARamF2YS5sYW5nLkJvb2xlYW7NIHKA1Zz67gIAAVoABXZhbHVleHABcHQAQGh0dHA6Ly9sb2NhbGhvc3Q6ODA4MC9rci1kZXYvcmVtb3RpbmcvT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2VzcgARamF2YS5sYW5nLkludGVnZXIS4qCk94GHOAIAAUkABXZhbHVleHIAEGphdmEubGFuZy5OdW1iZXKGrJUdC5TgiwIAAHhwAAAAAXNyAA5qYXZhLmxhbmcuTG9uZzuL5JDMjyPfAgABSgAFdmFsdWV4cQB+AB0AAAAAAAAPfXNyABlqYXZheC54bWwubmFtZXNwYWNlLlFOYW1lgW2oLfw73WwCAANMAAlsb2NhbFBhcnRxAH4ABEwADG5hbWVzcGFjZVVSSXEAfgAETAAGcHJlZml4cQB+AAR4cHQAGk9TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNldAAAcQB+ACR0B3RyTzBBQlhOeUFESnZjbWN1YTNWaGJHa3VjbWxqWlM1cmMySXViV1Z6YzJGbmFXNW5Ma3BoZG1GVFpYSjJhV05sUkdWbWFXNXBkR2x2YnJibkRWOUJOR3p4QWdBQlRBQVJjMlZ5ZG1salpVbHVkR1Z5Wm1GalpYTjBBQkJNYW1GMllTOTFkR2xzTDB4cGMzUTdlSElBTG05eVp5NXJkV0ZzYVM1eWFXTmxMbXR6WWk1dFpYTnpZV2RwYm1jdVUyVnlkbWxqWlVSbFptbHVhWFJwYjI0QW13SlBXTjBwZmdJQURVd0FDMkoxYzFObFkzVnlhWFI1ZEFBVFRHcGhkbUV2YkdGdVp5OUNiMjlzWldGdU8wd0FEMk55WldSbGJuUnBZV3h6Vkhsd1pYUUFURXh2Y21jdmEzVmhiR2t2Y21salpTOWpiM0psTDNObFkzVnlhWFI1TDJOeVpXUmxiblJwWVd4ekwwTnlaV1JsYm5ScFlXeHpVMjkxY21ObEpFTnlaV1JsYm5ScFlXeHpWSGx3WlR0TUFCQnNiMk5oYkZObGNuWnBZMlZPWVcxbGRBQVNUR3BoZG1FdmJHRnVaeTlUZEhKcGJtYzdUQUFYYldWemMyRm5aVVY0WTJWd2RHbHZia2hoYm1Sc1pYSnhBSDRBQlV3QURHMXBiR3hwYzFSdlRHbDJaWFFBRUV4cVlYWmhMMnhoYm1jdlRHOXVaenRNQUFod2NtbHZjbWwwZVhRQUUweHFZWFpoTDJ4aGJtY3ZTVzUwWldkbGNqdE1BQVZ4ZFdWMVpYRUFmZ0FEVEFBTmNtVjBjbmxCZEhSbGJYQjBjM0VBZmdBSFRBQUhjMlZ5ZG1salpYUUFFa3hxWVhaaEwyeGhibWN2VDJKcVpXTjBPMHdBRDNObGNuWnBZMlZGYm1SUWIybHVkSFFBRGt4cVlYWmhMMjVsZEM5VlVrdzdUQUFUYzJWeWRtbGpaVTVoYldWVGNHRmpaVlZTU1hFQWZnQUZUQUFRYzJWeWRtbGpaVTVoYldWemNHRmpaWEVBZmdBRlRBQUxjMlZ5ZG1salpWQmhkR2h4QUg0QUJYaHdjM0lBRVdwaGRtRXViR0Z1Wnk1Q2IyOXNaV0Z1elNCeWdOV2MrdTRDQUFGYUFBVjJZV3gxWlhod0FYQjBBQnBQVTBOaFkyaGxUbTkwYVdacFkyRjBhVzl1VTJWeWRtbGpaWFFBVFc5eVp5NXJkV0ZzYVM1eWFXTmxMbXR6WWk1dFpYTnpZV2RwYm1jdVpYaGpaWEIwYVc5dWFHRnVaR3hwYm1jdVJHVm1ZWFZzZEUxbGMzTmhaMlZGZUdObGNIUnBiMjVJWVc1a2JHVnljM0lBRG1waGRtRXViR0Z1Wnk1TWIyNW5PNHZra015UEk5OENBQUZLQUFWMllXeDFaWGh5QUJCcVlYWmhMbXhoYm1jdVRuVnRZbVZ5aHF5VkhRdVU0SXNDQUFCNGNQLy8vLy8vLy8vL2MzSUFFV3BoZG1FdWJHRnVaeTVKYm5SbFoyVnlFdUtncFBlQmh6Z0NBQUZKQUFWMllXeDFaWGh4QUg0QUVBQUFBQU56Y1FCK0FBc0FjUUIrQUJOd2MzSUFER3BoZG1FdWJtVjBMbFZTVEpZbE56WWEvT1J5QXdBSFNRQUlhR0Z6YUVOdlpHVkpBQVJ3YjNKMFRBQUpZWFYwYUc5eWFYUjVjUUIrQUFWTUFBUm1hV3hsY1FCK0FBVk1BQVJvYjNOMGNRQitBQVZNQUFod2NtOTBiMk52YkhFQWZnQUZUQUFEY21WbWNRQitBQVY0Y1AvLy8vOEFBQitRZEFBT2JHOWpZV3hvYjNOME9qZ3dPREIwQUNzdmEzSXRaR1YyTDNKbGJXOTBhVzVuTDA5VFEyRmphR1ZPYjNScFptbGpZWFJwYjI1VFpYSjJhV05sZEFBSmJHOWpZV3hvYjNOMGRBQUVhSFIwY0hCNGRBQUFkQUFHVkZKQlZrVk1kQUFCTDNOeUFCTnFZWFpoTG5WMGFXd3VRWEp5WVhsTWFYTjBlSUhTSFpuSFlaMERBQUZKQUFSemFYcGxlSEFBQUFBRmR3UUFBQUFLZEFBemIzSm5M';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 389    FOR UPDATE;        
    buffer := 'bXQxWVd4cExuSnBZMlV1YTNOaUxtMWxjM05oWjJsdVp5NXpaWEoyYVdObExrdFRRa3BoZG1GVFpYSjJhV05sZEFBOFkyOXRMbTl3Wlc1emVXMXdhRzl1ZVM1dmMyTmhZMmhsTG1KaGMyVXVaWFpsYm5SekxrTmhZMmhsUlc1MGNubEZkbVZ1ZEV4cGMzUmxibVZ5ZEFBM1kyOXRMbTl3Wlc1emVXMXdhRzl1ZVM1dmMyTmhZMmhsTG1KaGMyVXVaWFpsYm5SekxrTmhZMmhsUlhabGJuUk1hWE4wWlc1bGNuUUFGMnBoZG1FdWRYUnBiQzVGZG1WdWRFeHBjM1JsYm1WeWRBQXNZMjl0TG05d1pXNXplVzF3YUc5dWVTNXZjMk5oWTJobExtSmhjMlV1VEdsbVpXTjVZMnhsUVhkaGNtVjR0AA0xMjkuMTg2Ljc5LjQ1c3IAMm9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuSmF2YVNlcnZpY2VEZWZpbml0aW9utucNX0E0bPECAAFMABFzZXJ2aWNlSW50ZXJmYWNlc3QAEExqYXZhL3V0aWwvTGlzdDt4cgAub3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5TZXJ2aWNlRGVmaW5pdGlvbgCbAk9Y3Sl+AgANTAALYnVzU2VjdXJpdHlxAH4AE0wAD2NyZWRlbnRpYWxzVHlwZXQATExvcmcva3VhbGkvcmljZS9jb3JlL3NlY3VyaXR5L2NyZWRlbnRpYWxzL0NyZWRlbnRpYWxzU291cmNlJENyZWRlbnRpYWxzVHlwZTtMABBsb2NhbFNlcnZpY2VOYW1lcQB+AARMABdtZXNzYWdlRXhjZXB0aW9uSGFuZGxlcnEAfgAETAAMbWlsbGlzVG9MaXZlcQB+ABVMAAhwcmlvcml0eXEAfgAUTAAFcXVldWVxAH4AE0wADXJldHJ5QXR0ZW1wdHNxAH4AFEwAB3NlcnZpY2V0ABJMamF2YS9sYW5nL09iamVjdDtMAA9zZXJ2aWNlRW5kUG9pbnR0AA5MamF2YS9uZXQvVVJMO0wAE3NlcnZpY2VOYW1lU3BhY2VVUklxAH4ABEwAEHNlcnZpY2VOYW1lc3BhY2VxAH4ABEwAC3NlcnZpY2VQYXRocQB+AAR4cHNxAH4AGQFwdAAaT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2V0AE1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLmV4Y2VwdGlvbmhhbmRsaW5nLkRlZmF1bHRNZXNzYWdlRXhjZXB0aW9uSGFuZGxlcnNxAH4AH///////////c3EAfgAcAAAAA3NxAH4AGQBxAH4AMnBzcgAMamF2YS5uZXQuVVJMliU3Nhr85HIDAAdJAAhoYXNoQ29kZUkABHBvcnRMAAlhdXRob3JpdHlxAH4ABEwABGZpbGVxAH4ABEwABGhvc3RxAH4ABEwACHByb3RvY29scQB+AARMAANyZWZxAH4ABHhwaRuioAAAH5B0AA5sb2NhbGhvc3Q6ODA4MHQAKy9rci1kZXYvcmVtb3RpbmcvT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2V0AAlsb2NhbGhvc3R0AARodHRwcHh0AAB0AAZUUkFWRUx0AAEvc3IAE2phdmEudXRpbC5BcnJheUxpc3R4gdIdmcdhnQMAAUkABHNpemV4cAAAAAV3BAAAAAp0ADNvcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLnNlcnZpY2UuS1NCSmF2YVNlcnZpY2V0ADxjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5ldmVudHMuQ2FjaGVFbnRyeUV2ZW50TGlzdGVuZXJ0ADdjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5ldmVudHMuQ2FjaGVFdmVudExpc3RlbmVydAAXamF2YS51dGlsLkV2ZW50TGlzdGVuZXJ0ACxjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5MaWZlY3ljbGVBd2FyZXhxAH4AI3QABlRSQVZFTA==';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
INSERT INTO KRSB_MSG_PYLD_T (MSG_PYLD,MSG_QUE_ID)
  VALUES (EMPTY_CLOB(),390)
/
-- Length: 6104
-- Chunks: 2
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 390    FOR UPDATE;        
    buffer := 'rO0ABXNyAC1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLkFzeW5jaHJvbm91c0NhbGzxnQ5Y0t0lZQIAB1oAFWlnbm9yZVN0b3JlQW5kRm9yd2FyZFsACWFyZ3VtZW50c3QAE1tMamF2YS9sYW5nL09iamVjdDtMAAhjYWxsYmFja3QAM0xvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL0FzeW5jaHJvbm91c0NhbGxiYWNrO0wAB2NvbnRleHR0ABZMamF2YS9pby9TZXJpYWxpemFibGU7TAAKbWV0aG9kTmFtZXQAEkxqYXZhL2xhbmcvU3RyaW5nO1sACnBhcmFtVHlwZXN0ABJbTGphdmEvbGFuZy9DbGFzcztMAAtzZXJ2aWNlSW5mb3QAKkxvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL1NlcnZpY2VJbmZvO3hwAHVyABNbTGphdmEubGFuZy5PYmplY3Q7kM5YnxBzKWwCAAB4cAAAAAFzcgBDY29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLnBsdWdpbnMuY2x1c3RlcnN1cHBvcnQuQ2x1c3Rlck5vdGlmaWNhdGlvbhY+6VckvG9pAgACSQAEdHlwZUwABGRhdGFxAH4AA3hwAAAAAnQAL0RvY3VtZW50VHlwZVJ1bGVDYWNoZTpDYW1wdXNNYWludGVuYW5jZURvY3VtZW50cHB0AAZpbnZva2V1cgASW0xqYXZhLmxhbmcuQ2xhc3M7qxbXrsvNWpkCAAB4cAAAAAF2cgAUamF2YS5pby5TZXJpYWxpemFibGUQm2EN15tk7QIAAHhwc3IAKG9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuU2VydmljZUluZm/FFyO2KQS3ugIAC0wABWFsaXZldAATTGphdmEvbGFuZy9Cb29sZWFuO0wAFGVuZHBvaW50QWx0ZXJuYXRlVXJscQB+AARMAAtlbmRwb2ludFVybHEAfgAETAAKbG9ja1Zlck5icnQAE0xqYXZhL2xhbmcvSW50ZWdlcjtMAA5tZXNzYWdlRW50cnlJZHQAEExqYXZhL2xhbmcvTG9uZztMAAVxbmFtZXQAG0xqYXZheC94bWwvbmFtZXNwYWNlL1FOYW1lO0wAGnNlcmlhbGl6ZWRTZXJ2aWNlTmFtZXNwYWNlcQB+AARMAAhzZXJ2ZXJJcHEAfgAETAARc2VydmljZURlZmluaXRpb250ADBMb3JnL2t1YWxpL3JpY2Uva3NiL21lc3NhZ2luZy9TZXJ2aWNlRGVmaW5pdGlvbjtMAAtzZXJ2aWNlTmFtZXEAfgAETAAQc2VydmljZU5hbWVzcGFjZXEAfgAEeHBzcgARamF2YS5sYW5nLkJvb2xlYW7NIHKA1Zz67gIAAVoABXZhbHVleHABcHQAQGh0dHA6Ly9sb2NhbGhvc3Q6ODA4MC9rci1kZXYvcmVtb3RpbmcvT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2VzcgARamF2YS5sYW5nLkludGVnZXIS4qCk94GHOAIAAUkABXZhbHVleHIAEGphdmEubGFuZy5OdW1iZXKGrJUdC5TgiwIAAHhwAAAAAXNyAA5qYXZhLmxhbmcuTG9uZzuL5JDMjyPfAgABSgAFdmFsdWV4cQB+AB0AAAAAAAAPfXNyABlqYXZheC54bWwubmFtZXNwYWNlLlFOYW1lgW2oLfw73WwCAANMAAlsb2NhbFBhcnRxAH4ABEwADG5hbWVzcGFjZVVSSXEAfgAETAAGcHJlZml4cQB+AAR4cHQAGk9TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNldAAAcQB+ACR0B3RyTzBBQlhOeUFESnZjbWN1YTNWaGJHa3VjbWxqWlM1cmMySXViV1Z6YzJGbmFXNW5Ma3BoZG1GVFpYSjJhV05sUkdWbWFXNXBkR2x2YnJibkRWOUJOR3p4QWdBQlRBQVJjMlZ5ZG1salpVbHVkR1Z5Wm1GalpYTjBBQkJNYW1GMllTOTFkR2xzTDB4cGMzUTdlSElBTG05eVp5NXJkV0ZzYVM1eWFXTmxMbXR6WWk1dFpYTnpZV2RwYm1jdVUyVnlkbWxqWlVSbFptbHVhWFJwYjI0QW13SlBXTjBwZmdJQURVd0FDMkoxYzFObFkzVnlhWFI1ZEFBVFRHcGhkbUV2YkdGdVp5OUNiMjlzWldGdU8wd0FEMk55WldSbGJuUnBZV3h6Vkhsd1pYUUFURXh2Y21jdmEzVmhiR2t2Y21salpTOWpiM0psTDNObFkzVnlhWFI1TDJOeVpXUmxiblJwWVd4ekwwTnlaV1JsYm5ScFlXeHpVMjkxY21ObEpFTnlaV1JsYm5ScFlXeHpWSGx3WlR0TUFCQnNiMk5oYkZObGNuWnBZMlZPWVcxbGRBQVNUR3BoZG1FdmJHRnVaeTlUZEhKcGJtYzdUQUFYYldWemMyRm5aVVY0WTJWd2RHbHZia2hoYm1Sc1pYSnhBSDRBQlV3QURHMXBiR3hwYzFSdlRHbDJaWFFBRUV4cVlYWmhMMnhoYm1jdlRHOXVaenRNQUFod2NtbHZjbWwwZVhRQUUweHFZWFpoTDJ4aGJtY3ZTVzUwWldkbGNqdE1BQVZ4ZFdWMVpYRUFmZ0FEVEFBTmNtVjBjbmxCZEhSbGJYQjBjM0VBZmdBSFRBQUhjMlZ5ZG1salpYUUFFa3hxWVhaaEwyeGhibWN2VDJKcVpXTjBPMHdBRDNObGNuWnBZMlZGYm1SUWIybHVkSFFBRGt4cVlYWmhMMjVsZEM5VlVrdzdUQUFUYzJWeWRtbGpaVTVoYldWVGNHRmpaVlZTU1hFQWZnQUZUQUFRYzJWeWRtbGpaVTVoYldWemNHRmpaWEVBZmdBRlRBQUxjMlZ5ZG1salpWQmhkR2h4QUg0QUJYaHdjM0lBRVdwaGRtRXViR0Z1Wnk1Q2IyOXNaV0Z1elNCeWdOV2MrdTRDQUFGYUFBVjJZV3gxWlhod0FYQjBBQnBQVTBOaFkyaGxUbTkwYVdacFkyRjBhVzl1VTJWeWRtbGpaWFFBVFc5eVp5NXJkV0ZzYVM1eWFXTmxMbXR6WWk1dFpYTnpZV2RwYm1jdVpYaGpaWEIwYVc5dWFHRnVaR3hwYm1jdVJHVm1ZWFZzZEUxbGMzTmhaMlZGZUdObGNIUnBiMjVJWVc1a2JHVnljM0lBRG1waGRtRXViR0Z1Wnk1TWIyNW5PNHZra015UEk5OENBQUZLQUFWMllXeDFaWGh5QUJCcVlYWmhMbXhoYm1jdVRuVnRZbVZ5aHF5VkhRdVU0SXNDQUFCNGNQLy8vLy8vLy8vL2MzSUFFV3BoZG1FdWJHRnVaeTVKYm5SbFoyVnlFdUtncFBlQmh6Z0NBQUZKQUFWMllXeDFaWGh4QUg0QUVBQUFBQU56Y1FCK0FBc0FjUUIrQUJOd2MzSUFER3BoZG1FdWJtVjBMbFZTVEpZbE56WWEvT1J5QXdBSFNRQUlhR0Z6YUVOdlpHVkpBQVJ3YjNKMFRBQUpZWFYwYUc5eWFYUjVjUUIrQUFWTUFBUm1hV3hsY1FCK0FBVk1BQVJvYjNOMGNRQitBQVZNQUFod2NtOTBiMk52YkhFQWZnQUZUQUFEY21WbWNRQitBQVY0Y1AvLy8vOEFBQitRZEFBT2JHOWpZV3hvYjNOME9qZ3dPREIwQUNzdmEzSXRaR1YyTDNKbGJXOTBhVzVuTDA5VFEyRmphR1ZPYjNScFptbGpZWFJwYjI1VFpYSjJhV05sZEFBSmJHOWpZV3hvYjNOMGRBQUVhSFIwY0hCNGRBQUFkQUFHVkZKQlZrVk1kQUFCTDNOeUFCTnFZWFpoTG5WMGFXd3VRWEp5WVhsTWFYTjBlSUhTSFpuSFlaMERBQUZKQUFSemFYcGxlSEFBQUFBRmR3UUFBQUFLZEFBemIzSm5M';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 390    FOR UPDATE;        
    buffer := 'bXQxWVd4cExuSnBZMlV1YTNOaUxtMWxjM05oWjJsdVp5NXpaWEoyYVdObExrdFRRa3BoZG1GVFpYSjJhV05sZEFBOFkyOXRMbTl3Wlc1emVXMXdhRzl1ZVM1dmMyTmhZMmhsTG1KaGMyVXVaWFpsYm5SekxrTmhZMmhsUlc1MGNubEZkbVZ1ZEV4cGMzUmxibVZ5ZEFBM1kyOXRMbTl3Wlc1emVXMXdhRzl1ZVM1dmMyTmhZMmhsTG1KaGMyVXVaWFpsYm5SekxrTmhZMmhsUlhabGJuUk1hWE4wWlc1bGNuUUFGMnBoZG1FdWRYUnBiQzVGZG1WdWRFeHBjM1JsYm1WeWRBQXNZMjl0TG05d1pXNXplVzF3YUc5dWVTNXZjMk5oWTJobExtSmhjMlV1VEdsbVpXTjVZMnhsUVhkaGNtVjR0AA0xMjkuMTg2Ljc5LjQ1c3IAMm9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuSmF2YVNlcnZpY2VEZWZpbml0aW9utucNX0E0bPECAAFMABFzZXJ2aWNlSW50ZXJmYWNlc3QAEExqYXZhL3V0aWwvTGlzdDt4cgAub3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5TZXJ2aWNlRGVmaW5pdGlvbgCbAk9Y3Sl+AgANTAALYnVzU2VjdXJpdHlxAH4AE0wAD2NyZWRlbnRpYWxzVHlwZXQATExvcmcva3VhbGkvcmljZS9jb3JlL3NlY3VyaXR5L2NyZWRlbnRpYWxzL0NyZWRlbnRpYWxzU291cmNlJENyZWRlbnRpYWxzVHlwZTtMABBsb2NhbFNlcnZpY2VOYW1lcQB+AARMABdtZXNzYWdlRXhjZXB0aW9uSGFuZGxlcnEAfgAETAAMbWlsbGlzVG9MaXZlcQB+ABVMAAhwcmlvcml0eXEAfgAUTAAFcXVldWVxAH4AE0wADXJldHJ5QXR0ZW1wdHNxAH4AFEwAB3NlcnZpY2V0ABJMamF2YS9sYW5nL09iamVjdDtMAA9zZXJ2aWNlRW5kUG9pbnR0AA5MamF2YS9uZXQvVVJMO0wAE3NlcnZpY2VOYW1lU3BhY2VVUklxAH4ABEwAEHNlcnZpY2VOYW1lc3BhY2VxAH4ABEwAC3NlcnZpY2VQYXRocQB+AAR4cHNxAH4AGQFwdAAaT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2V0AE1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLmV4Y2VwdGlvbmhhbmRsaW5nLkRlZmF1bHRNZXNzYWdlRXhjZXB0aW9uSGFuZGxlcnNxAH4AH///////////c3EAfgAcAAAAA3NxAH4AGQBxAH4AMnBzcgAMamF2YS5uZXQuVVJMliU3Nhr85HIDAAdJAAhoYXNoQ29kZUkABHBvcnRMAAlhdXRob3JpdHlxAH4ABEwABGZpbGVxAH4ABEwABGhvc3RxAH4ABEwACHByb3RvY29scQB+AARMAANyZWZxAH4ABHhwaRuioAAAH5B0AA5sb2NhbGhvc3Q6ODA4MHQAKy9rci1kZXYvcmVtb3RpbmcvT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2V0AAlsb2NhbGhvc3R0AARodHRwcHh0AAB0AAZUUkFWRUx0AAEvc3IAE2phdmEudXRpbC5BcnJheUxpc3R4gdIdmcdhnQMAAUkABHNpemV4cAAAAAV3BAAAAAp0ADNvcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLnNlcnZpY2UuS1NCSmF2YVNlcnZpY2V0ADxjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5ldmVudHMuQ2FjaGVFbnRyeUV2ZW50TGlzdGVuZXJ0ADdjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5ldmVudHMuQ2FjaGVFdmVudExpc3RlbmVydAAXamF2YS51dGlsLkV2ZW50TGlzdGVuZXJ0ACxjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5MaWZlY3ljbGVBd2FyZXhxAH4AI3QABlRSQVZFTA==';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
INSERT INTO KRSB_MSG_PYLD_T (MSG_PYLD,MSG_QUE_ID)
  VALUES (EMPTY_CLOB(),391)
/
-- Length: 6108
-- Chunks: 2
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 391    FOR UPDATE;        
    buffer := 'rO0ABXNyAC1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLkFzeW5jaHJvbm91c0NhbGzxnQ5Y0t0lZQIAB1oAFWlnbm9yZVN0b3JlQW5kRm9yd2FyZFsACWFyZ3VtZW50c3QAE1tMamF2YS9sYW5nL09iamVjdDtMAAhjYWxsYmFja3QAM0xvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL0FzeW5jaHJvbm91c0NhbGxiYWNrO0wAB2NvbnRleHR0ABZMamF2YS9pby9TZXJpYWxpemFibGU7TAAKbWV0aG9kTmFtZXQAEkxqYXZhL2xhbmcvU3RyaW5nO1sACnBhcmFtVHlwZXN0ABJbTGphdmEvbGFuZy9DbGFzcztMAAtzZXJ2aWNlSW5mb3QAKkxvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL1NlcnZpY2VJbmZvO3hwAHVyABNbTGphdmEubGFuZy5PYmplY3Q7kM5YnxBzKWwCAAB4cAAAAAFzcgBDY29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLnBsdWdpbnMuY2x1c3RlcnN1cHBvcnQuQ2x1c3Rlck5vdGlmaWNhdGlvbhY+6VckvG9pAgACSQAEdHlwZUwABGRhdGFxAH4AA3hwAAAAAnQAM0RvY3VtZW50VHlwZVJ1bGVDYWNoZTpDYW1wdXNUeXBlTWFpbnRlbmFuY2VEb2N1bWVudHBwdAAGaW52b2tldXIAEltMamF2YS5sYW5nLkNsYXNzO6sW167LzVqZAgAAeHAAAAABdnIAFGphdmEuaW8uU2VyaWFsaXphYmxlEJthDdebZO0CAAB4cHNyAChvcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLlNlcnZpY2VJbmZvxRcjtikEt7oCAAtMAAVhbGl2ZXQAE0xqYXZhL2xhbmcvQm9vbGVhbjtMABRlbmRwb2ludEFsdGVybmF0ZVVybHEAfgAETAALZW5kcG9pbnRVcmxxAH4ABEwACmxvY2tWZXJOYnJ0ABNMamF2YS9sYW5nL0ludGVnZXI7TAAObWVzc2FnZUVudHJ5SWR0ABBMamF2YS9sYW5nL0xvbmc7TAAFcW5hbWV0ABtMamF2YXgveG1sL25hbWVzcGFjZS9RTmFtZTtMABpzZXJpYWxpemVkU2VydmljZU5hbWVzcGFjZXEAfgAETAAIc2VydmVySXBxAH4ABEwAEXNlcnZpY2VEZWZpbml0aW9udAAwTG9yZy9rdWFsaS9yaWNlL2tzYi9tZXNzYWdpbmcvU2VydmljZURlZmluaXRpb247TAALc2VydmljZU5hbWVxAH4ABEwAEHNlcnZpY2VOYW1lc3BhY2VxAH4ABHhwc3IAEWphdmEubGFuZy5Cb29sZWFuzSBygNWc+u4CAAFaAAV2YWx1ZXhwAXB0AEBodHRwOi8vbG9jYWxob3N0OjgwODAva3ItZGV2L3JlbW90aW5nL09TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNlc3IAEWphdmEubGFuZy5JbnRlZ2VyEuKgpPeBhzgCAAFJAAV2YWx1ZXhyABBqYXZhLmxhbmcuTnVtYmVyhqyVHQuU4IsCAAB4cAAAAAFzcgAOamF2YS5sYW5nLkxvbmc7i+SQzI8j3wIAAUoABXZhbHVleHEAfgAdAAAAAAAAD31zcgAZamF2YXgueG1sLm5hbWVzcGFjZS5RTmFtZYFtqC38O91sAgADTAAJbG9jYWxQYXJ0cQB+AARMAAxuYW1lc3BhY2VVUklxAH4ABEwABnByZWZpeHEAfgAEeHB0ABpPU0NhY2hlTm90aWZpY2F0aW9uU2VydmljZXQAAHEAfgAkdAd0ck8wQUJYTnlBREp2Y21jdWEzVmhiR2t1Y21salpTNXJjMkl1YldWemMyRm5hVzVuTGtwaGRtRlRaWEoyYVdObFJHVm1hVzVwZEdsdmJyYm5EVjlCTkd6eEFnQUJUQUFSYzJWeWRtbGpaVWx1ZEdWeVptRmpaWE4wQUJCTWFtRjJZUzkxZEdsc0wweHBjM1E3ZUhJQUxtOXlaeTVyZFdGc2FTNXlhV05sTG10ellpNXRaWE56WVdkcGJtY3VVMlZ5ZG1salpVUmxabWx1YVhScGIyNEFtd0pQV04wcGZnSUFEVXdBQzJKMWMxTmxZM1Z5YVhSNWRBQVRUR3BoZG1FdmJHRnVaeTlDYjI5c1pXRnVPMHdBRDJOeVpXUmxiblJwWVd4elZIbHdaWFFBVEV4dmNtY3ZhM1ZoYkdrdmNtbGpaUzlqYjNKbEwzTmxZM1Z5YVhSNUwyTnlaV1JsYm5ScFlXeHpMME55WldSbGJuUnBZV3h6VTI5MWNtTmxKRU55WldSbGJuUnBZV3h6Vkhsd1pUdE1BQkJzYjJOaGJGTmxjblpwWTJWT1lXMWxkQUFTVEdwaGRtRXZiR0Z1Wnk5VGRISnBibWM3VEFBWGJXVnpjMkZuWlVWNFkyVndkR2x2YmtoaGJtUnNaWEp4QUg0QUJVd0FERzFwYkd4cGMxUnZUR2wyWlhRQUVFeHFZWFpoTDJ4aGJtY3ZURzl1Wnp0TUFBaHdjbWx2Y21sMGVYUUFFMHhxWVhaaEwyeGhibWN2U1c1MFpXZGxjanRNQUFWeGRXVjFaWEVBZmdBRFRBQU5jbVYwY25sQmRIUmxiWEIwYzNFQWZnQUhUQUFIYzJWeWRtbGpaWFFBRWt4cVlYWmhMMnhoYm1jdlQySnFaV04wTzB3QUQzTmxjblpwWTJWRmJtUlFiMmx1ZEhRQURreHFZWFpoTDI1bGRDOVZVa3c3VEFBVGMyVnlkbWxqWlU1aGJXVlRjR0ZqWlZWU1NYRUFmZ0FGVEFBUWMyVnlkbWxqWlU1aGJXVnpjR0ZqWlhFQWZnQUZUQUFMYzJWeWRtbGpaVkJoZEdoeEFINEFCWGh3YzNJQUVXcGhkbUV1YkdGdVp5NUNiMjlzWldGdXpTQnlnTldjK3U0Q0FBRmFBQVYyWVd4MVpYaHdBWEIwQUJwUFUwTmhZMmhsVG05MGFXWnBZMkYwYVc5dVUyVnlkbWxqWlhRQVRXOXlaeTVyZFdGc2FTNXlhV05sTG10ellpNXRaWE56WVdkcGJtY3VaWGhqWlhCMGFXOXVhR0Z1Wkd4cGJtY3VSR1ZtWVhWc2RFMWxjM05oWjJWRmVHTmxjSFJwYjI1SVlXNWtiR1Z5YzNJQURtcGhkbUV1YkdGdVp5NU1iMjVuTzR2a2tNeVBJOThDQUFGS0FBVjJZV3gxWlhoeUFCQnFZWFpoTG14aGJtY3VUblZ0WW1WeWhxeVZIUXVVNElzQ0FBQjRjUC8vLy8vLy8vLy9jM0lBRVdwaGRtRXViR0Z1Wnk1SmJuUmxaMlZ5RXVLZ3BQZUJoemdDQUFGSkFBVjJZV3gxWlhoeEFINEFFQUFBQUFOemNRQitBQXNBY1FCK0FCTndjM0lBREdwaGRtRXVibVYwTGxWU1RKWWxOellhL09SeUF3QUhTUUFJYUdGemFFTnZaR1ZKQUFSd2IzSjBUQUFKWVhWMGFHOXlhWFI1Y1FCK0FBVk1BQVJtYVd4bGNRQitBQVZNQUFSb2IzTjBjUUIrQUFWTUFBaHdjbTkwYjJOdmJIRUFmZ0FGVEFBRGNtVm1jUUIrQUFWNGNQLy8vLzhBQUIrUWRBQU9iRzlqWVd4b2IzTjBPamd3T0RCMEFDc3ZhM0l0WkdWMkwzSmxiVzkwYVc1bkwwOVRRMkZqYUdWT2IzUnBabWxqWVhScGIyNVRaWEoyYVdObGRBQUpiRzlqWVd4b2IzTjBkQUFFYUhSMGNIQjRkQUFBZEFBR1ZGSkJWa1ZNZEFBQkwzTnlBQk5xWVhaaExuVjBhV3d1UVhKeVlYbE1hWE4wZUlIU0habkhZWjBEQUFGSkFBUnphWHBsZUhBQUFBQUZkd1FBQUFBS2RBQXpi';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 391    FOR UPDATE;        
    buffer := 'M0puTG10MVlXeHBMbkpwWTJVdWEzTmlMbTFsYzNOaFoybHVaeTV6WlhKMmFXTmxMa3RUUWtwaGRtRlRaWEoyYVdObGRBQThZMjl0TG05d1pXNXplVzF3YUc5dWVTNXZjMk5oWTJobExtSmhjMlV1WlhabGJuUnpMa05oWTJobFJXNTBjbmxGZG1WdWRFeHBjM1JsYm1WeWRBQTNZMjl0TG05d1pXNXplVzF3YUc5dWVTNXZjMk5oWTJobExtSmhjMlV1WlhabGJuUnpMa05oWTJobFJYWmxiblJNYVhOMFpXNWxjblFBRjJwaGRtRXVkWFJwYkM1RmRtVnVkRXhwYzNSbGJtVnlkQUFzWTI5dExtOXdaVzV6ZVcxd2FHOXVlUzV2YzJOaFkyaGxMbUpoYzJVdVRHbG1aV041WTJ4bFFYZGhjbVY0dAANMTI5LjE4Ni43OS40NXNyADJvcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLkphdmFTZXJ2aWNlRGVmaW5pdGlvbrbnDV9BNGzxAgABTAARc2VydmljZUludGVyZmFjZXN0ABBMamF2YS91dGlsL0xpc3Q7eHIALm9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuU2VydmljZURlZmluaXRpb24AmwJPWN0pfgIADUwAC2J1c1NlY3VyaXR5cQB+ABNMAA9jcmVkZW50aWFsc1R5cGV0AExMb3JnL2t1YWxpL3JpY2UvY29yZS9zZWN1cml0eS9jcmVkZW50aWFscy9DcmVkZW50aWFsc1NvdXJjZSRDcmVkZW50aWFsc1R5cGU7TAAQbG9jYWxTZXJ2aWNlTmFtZXEAfgAETAAXbWVzc2FnZUV4Y2VwdGlvbkhhbmRsZXJxAH4ABEwADG1pbGxpc1RvTGl2ZXEAfgAVTAAIcHJpb3JpdHlxAH4AFEwABXF1ZXVlcQB+ABNMAA1yZXRyeUF0dGVtcHRzcQB+ABRMAAdzZXJ2aWNldAASTGphdmEvbGFuZy9PYmplY3Q7TAAPc2VydmljZUVuZFBvaW50dAAOTGphdmEvbmV0L1VSTDtMABNzZXJ2aWNlTmFtZVNwYWNlVVJJcQB+AARMABBzZXJ2aWNlTmFtZXNwYWNlcQB+AARMAAtzZXJ2aWNlUGF0aHEAfgAEeHBzcQB+ABkBcHQAGk9TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNldABNb3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5leGNlcHRpb25oYW5kbGluZy5EZWZhdWx0TWVzc2FnZUV4Y2VwdGlvbkhhbmRsZXJzcQB+AB///////////3NxAH4AHAAAAANzcQB+ABkAcQB+ADJwc3IADGphdmEubmV0LlVSTJYlNzYa/ORyAwAHSQAIaGFzaENvZGVJAARwb3J0TAAJYXV0aG9yaXR5cQB+AARMAARmaWxlcQB+AARMAARob3N0cQB+AARMAAhwcm90b2NvbHEAfgAETAADcmVmcQB+AAR4cGkboqAAAB+QdAAObG9jYWxob3N0OjgwODB0ACsva3ItZGV2L3JlbW90aW5nL09TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNldAAJbG9jYWxob3N0dAAEaHR0cHB4dAAAdAAGVFJBVkVMdAABL3NyABNqYXZhLnV0aWwuQXJyYXlMaXN0eIHSHZnHYZ0DAAFJAARzaXpleHAAAAAFdwQAAAAKdAAzb3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5zZXJ2aWNlLktTQkphdmFTZXJ2aWNldAA8Y29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLmJhc2UuZXZlbnRzLkNhY2hlRW50cnlFdmVudExpc3RlbmVydAA3Y29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLmJhc2UuZXZlbnRzLkNhY2hlRXZlbnRMaXN0ZW5lcnQAF2phdmEudXRpbC5FdmVudExpc3RlbmVydAAsY29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLmJhc2UuTGlmZWN5Y2xlQXdhcmV4cQB+ACN0AAZUUkFWRUw=';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
INSERT INTO KRSB_MSG_PYLD_T (MSG_PYLD,MSG_QUE_ID)
  VALUES (EMPTY_CLOB(),392)
/
-- Length: 6104
-- Chunks: 2
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 392    FOR UPDATE;        
    buffer := 'rO0ABXNyAC1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLkFzeW5jaHJvbm91c0NhbGzxnQ5Y0t0lZQIAB1oAFWlnbm9yZVN0b3JlQW5kRm9yd2FyZFsACWFyZ3VtZW50c3QAE1tMamF2YS9sYW5nL09iamVjdDtMAAhjYWxsYmFja3QAM0xvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL0FzeW5jaHJvbm91c0NhbGxiYWNrO0wAB2NvbnRleHR0ABZMamF2YS9pby9TZXJpYWxpemFibGU7TAAKbWV0aG9kTmFtZXQAEkxqYXZhL2xhbmcvU3RyaW5nO1sACnBhcmFtVHlwZXN0ABJbTGphdmEvbGFuZy9DbGFzcztMAAtzZXJ2aWNlSW5mb3QAKkxvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL1NlcnZpY2VJbmZvO3hwAHVyABNbTGphdmEubGFuZy5PYmplY3Q7kM5YnxBzKWwCAAB4cAAAAAFzcgBDY29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLnBsdWdpbnMuY2x1c3RlcnN1cHBvcnQuQ2x1c3Rlck5vdGlmaWNhdGlvbhY+6VckvG9pAgACSQAEdHlwZUwABGRhdGFxAH4AA3hwAAAAAnQAMERvY3VtZW50VHlwZVJ1bGVDYWNoZTpDb3VudHJ5TWFpbnRlbmFuY2VEb2N1bWVudHBwdAAGaW52b2tldXIAEltMamF2YS5sYW5nLkNsYXNzO6sW167LzVqZAgAAeHAAAAABdnIAFGphdmEuaW8uU2VyaWFsaXphYmxlEJthDdebZO0CAAB4cHNyAChvcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLlNlcnZpY2VJbmZvxRcjtikEt7oCAAtMAAVhbGl2ZXQAE0xqYXZhL2xhbmcvQm9vbGVhbjtMABRlbmRwb2ludEFsdGVybmF0ZVVybHEAfgAETAALZW5kcG9pbnRVcmxxAH4ABEwACmxvY2tWZXJOYnJ0ABNMamF2YS9sYW5nL0ludGVnZXI7TAAObWVzc2FnZUVudHJ5SWR0ABBMamF2YS9sYW5nL0xvbmc7TAAFcW5hbWV0ABtMamF2YXgveG1sL25hbWVzcGFjZS9RTmFtZTtMABpzZXJpYWxpemVkU2VydmljZU5hbWVzcGFjZXEAfgAETAAIc2VydmVySXBxAH4ABEwAEXNlcnZpY2VEZWZpbml0aW9udAAwTG9yZy9rdWFsaS9yaWNlL2tzYi9tZXNzYWdpbmcvU2VydmljZURlZmluaXRpb247TAALc2VydmljZU5hbWVxAH4ABEwAEHNlcnZpY2VOYW1lc3BhY2VxAH4ABHhwc3IAEWphdmEubGFuZy5Cb29sZWFuzSBygNWc+u4CAAFaAAV2YWx1ZXhwAXB0AEBodHRwOi8vbG9jYWxob3N0OjgwODAva3ItZGV2L3JlbW90aW5nL09TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNlc3IAEWphdmEubGFuZy5JbnRlZ2VyEuKgpPeBhzgCAAFJAAV2YWx1ZXhyABBqYXZhLmxhbmcuTnVtYmVyhqyVHQuU4IsCAAB4cAAAAAFzcgAOamF2YS5sYW5nLkxvbmc7i+SQzI8j3wIAAUoABXZhbHVleHEAfgAdAAAAAAAAD31zcgAZamF2YXgueG1sLm5hbWVzcGFjZS5RTmFtZYFtqC38O91sAgADTAAJbG9jYWxQYXJ0cQB+AARMAAxuYW1lc3BhY2VVUklxAH4ABEwABnByZWZpeHEAfgAEeHB0ABpPU0NhY2hlTm90aWZpY2F0aW9uU2VydmljZXQAAHEAfgAkdAd0ck8wQUJYTnlBREp2Y21jdWEzVmhiR2t1Y21salpTNXJjMkl1YldWemMyRm5hVzVuTGtwaGRtRlRaWEoyYVdObFJHVm1hVzVwZEdsdmJyYm5EVjlCTkd6eEFnQUJUQUFSYzJWeWRtbGpaVWx1ZEdWeVptRmpaWE4wQUJCTWFtRjJZUzkxZEdsc0wweHBjM1E3ZUhJQUxtOXlaeTVyZFdGc2FTNXlhV05sTG10ellpNXRaWE56WVdkcGJtY3VVMlZ5ZG1salpVUmxabWx1YVhScGIyNEFtd0pQV04wcGZnSUFEVXdBQzJKMWMxTmxZM1Z5YVhSNWRBQVRUR3BoZG1FdmJHRnVaeTlDYjI5c1pXRnVPMHdBRDJOeVpXUmxiblJwWVd4elZIbHdaWFFBVEV4dmNtY3ZhM1ZoYkdrdmNtbGpaUzlqYjNKbEwzTmxZM1Z5YVhSNUwyTnlaV1JsYm5ScFlXeHpMME55WldSbGJuUnBZV3h6VTI5MWNtTmxKRU55WldSbGJuUnBZV3h6Vkhsd1pUdE1BQkJzYjJOaGJGTmxjblpwWTJWT1lXMWxkQUFTVEdwaGRtRXZiR0Z1Wnk5VGRISnBibWM3VEFBWGJXVnpjMkZuWlVWNFkyVndkR2x2YmtoaGJtUnNaWEp4QUg0QUJVd0FERzFwYkd4cGMxUnZUR2wyWlhRQUVFeHFZWFpoTDJ4aGJtY3ZURzl1Wnp0TUFBaHdjbWx2Y21sMGVYUUFFMHhxWVhaaEwyeGhibWN2U1c1MFpXZGxjanRNQUFWeGRXVjFaWEVBZmdBRFRBQU5jbVYwY25sQmRIUmxiWEIwYzNFQWZnQUhUQUFIYzJWeWRtbGpaWFFBRWt4cVlYWmhMMnhoYm1jdlQySnFaV04wTzB3QUQzTmxjblpwWTJWRmJtUlFiMmx1ZEhRQURreHFZWFpoTDI1bGRDOVZVa3c3VEFBVGMyVnlkbWxqWlU1aGJXVlRjR0ZqWlZWU1NYRUFmZ0FGVEFBUWMyVnlkbWxqWlU1aGJXVnpjR0ZqWlhFQWZnQUZUQUFMYzJWeWRtbGpaVkJoZEdoeEFINEFCWGh3YzNJQUVXcGhkbUV1YkdGdVp5NUNiMjlzWldGdXpTQnlnTldjK3U0Q0FBRmFBQVYyWVd4MVpYaHdBWEIwQUJwUFUwTmhZMmhsVG05MGFXWnBZMkYwYVc5dVUyVnlkbWxqWlhRQVRXOXlaeTVyZFdGc2FTNXlhV05sTG10ellpNXRaWE56WVdkcGJtY3VaWGhqWlhCMGFXOXVhR0Z1Wkd4cGJtY3VSR1ZtWVhWc2RFMWxjM05oWjJWRmVHTmxjSFJwYjI1SVlXNWtiR1Z5YzNJQURtcGhkbUV1YkdGdVp5NU1iMjVuTzR2a2tNeVBJOThDQUFGS0FBVjJZV3gxWlhoeUFCQnFZWFpoTG14aGJtY3VUblZ0WW1WeWhxeVZIUXVVNElzQ0FBQjRjUC8vLy8vLy8vLy9jM0lBRVdwaGRtRXViR0Z1Wnk1SmJuUmxaMlZ5RXVLZ3BQZUJoemdDQUFGSkFBVjJZV3gxWlhoeEFINEFFQUFBQUFOemNRQitBQXNBY1FCK0FCTndjM0lBREdwaGRtRXVibVYwTGxWU1RKWWxOellhL09SeUF3QUhTUUFJYUdGemFFTnZaR1ZKQUFSd2IzSjBUQUFKWVhWMGFHOXlhWFI1Y1FCK0FBVk1BQVJtYVd4bGNRQitBQVZNQUFSb2IzTjBjUUIrQUFWTUFBaHdjbTkwYjJOdmJIRUFmZ0FGVEFBRGNtVm1jUUIrQUFWNGNQLy8vLzhBQUIrUWRBQU9iRzlqWVd4b2IzTjBPamd3T0RCMEFDc3ZhM0l0WkdWMkwzSmxiVzkwYVc1bkwwOVRRMkZqYUdWT2IzUnBabWxqWVhScGIyNVRaWEoyYVdObGRBQUpiRzlqWVd4b2IzTjBkQUFFYUhSMGNIQjRkQUFBZEFBR1ZGSkJWa1ZNZEFBQkwzTnlBQk5xWVhaaExuVjBhV3d1UVhKeVlYbE1hWE4wZUlIU0habkhZWjBEQUFGSkFBUnphWHBsZUhBQUFBQUZkd1FBQUFBS2RBQXpiM0pu';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 392    FOR UPDATE;        
    buffer := 'TG10MVlXeHBMbkpwWTJVdWEzTmlMbTFsYzNOaFoybHVaeTV6WlhKMmFXTmxMa3RUUWtwaGRtRlRaWEoyYVdObGRBQThZMjl0TG05d1pXNXplVzF3YUc5dWVTNXZjMk5oWTJobExtSmhjMlV1WlhabGJuUnpMa05oWTJobFJXNTBjbmxGZG1WdWRFeHBjM1JsYm1WeWRBQTNZMjl0TG05d1pXNXplVzF3YUc5dWVTNXZjMk5oWTJobExtSmhjMlV1WlhabGJuUnpMa05oWTJobFJYWmxiblJNYVhOMFpXNWxjblFBRjJwaGRtRXVkWFJwYkM1RmRtVnVkRXhwYzNSbGJtVnlkQUFzWTI5dExtOXdaVzV6ZVcxd2FHOXVlUzV2YzJOaFkyaGxMbUpoYzJVdVRHbG1aV041WTJ4bFFYZGhjbVY0dAANMTI5LjE4Ni43OS40NXNyADJvcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLkphdmFTZXJ2aWNlRGVmaW5pdGlvbrbnDV9BNGzxAgABTAARc2VydmljZUludGVyZmFjZXN0ABBMamF2YS91dGlsL0xpc3Q7eHIALm9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuU2VydmljZURlZmluaXRpb24AmwJPWN0pfgIADUwAC2J1c1NlY3VyaXR5cQB+ABNMAA9jcmVkZW50aWFsc1R5cGV0AExMb3JnL2t1YWxpL3JpY2UvY29yZS9zZWN1cml0eS9jcmVkZW50aWFscy9DcmVkZW50aWFsc1NvdXJjZSRDcmVkZW50aWFsc1R5cGU7TAAQbG9jYWxTZXJ2aWNlTmFtZXEAfgAETAAXbWVzc2FnZUV4Y2VwdGlvbkhhbmRsZXJxAH4ABEwADG1pbGxpc1RvTGl2ZXEAfgAVTAAIcHJpb3JpdHlxAH4AFEwABXF1ZXVlcQB+ABNMAA1yZXRyeUF0dGVtcHRzcQB+ABRMAAdzZXJ2aWNldAASTGphdmEvbGFuZy9PYmplY3Q7TAAPc2VydmljZUVuZFBvaW50dAAOTGphdmEvbmV0L1VSTDtMABNzZXJ2aWNlTmFtZVNwYWNlVVJJcQB+AARMABBzZXJ2aWNlTmFtZXNwYWNlcQB+AARMAAtzZXJ2aWNlUGF0aHEAfgAEeHBzcQB+ABkBcHQAGk9TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNldABNb3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5leGNlcHRpb25oYW5kbGluZy5EZWZhdWx0TWVzc2FnZUV4Y2VwdGlvbkhhbmRsZXJzcQB+AB///////////3NxAH4AHAAAAANzcQB+ABkAcQB+ADJwc3IADGphdmEubmV0LlVSTJYlNzYa/ORyAwAHSQAIaGFzaENvZGVJAARwb3J0TAAJYXV0aG9yaXR5cQB+AARMAARmaWxlcQB+AARMAARob3N0cQB+AARMAAhwcm90b2NvbHEAfgAETAADcmVmcQB+AAR4cGkboqAAAB+QdAAObG9jYWxob3N0OjgwODB0ACsva3ItZGV2L3JlbW90aW5nL09TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNldAAJbG9jYWxob3N0dAAEaHR0cHB4dAAAdAAGVFJBVkVMdAABL3NyABNqYXZhLnV0aWwuQXJyYXlMaXN0eIHSHZnHYZ0DAAFJAARzaXpleHAAAAAFdwQAAAAKdAAzb3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5zZXJ2aWNlLktTQkphdmFTZXJ2aWNldAA8Y29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLmJhc2UuZXZlbnRzLkNhY2hlRW50cnlFdmVudExpc3RlbmVydAA3Y29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLmJhc2UuZXZlbnRzLkNhY2hlRXZlbnRMaXN0ZW5lcnQAF2phdmEudXRpbC5FdmVudExpc3RlbmVydAAsY29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLmJhc2UuTGlmZWN5Y2xlQXdhcmV4cQB+ACN0AAZUUkFWRUw=';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
INSERT INTO KRSB_MSG_PYLD_T (MSG_PYLD,MSG_QUE_ID)
  VALUES (EMPTY_CLOB(),393)
/
-- Length: 6104
-- Chunks: 2
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 393    FOR UPDATE;        
    buffer := 'rO0ABXNyAC1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLkFzeW5jaHJvbm91c0NhbGzxnQ5Y0t0lZQIAB1oAFWlnbm9yZVN0b3JlQW5kRm9yd2FyZFsACWFyZ3VtZW50c3QAE1tMamF2YS9sYW5nL09iamVjdDtMAAhjYWxsYmFja3QAM0xvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL0FzeW5jaHJvbm91c0NhbGxiYWNrO0wAB2NvbnRleHR0ABZMamF2YS9pby9TZXJpYWxpemFibGU7TAAKbWV0aG9kTmFtZXQAEkxqYXZhL2xhbmcvU3RyaW5nO1sACnBhcmFtVHlwZXN0ABJbTGphdmEvbGFuZy9DbGFzcztMAAtzZXJ2aWNlSW5mb3QAKkxvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL1NlcnZpY2VJbmZvO3hwAHVyABNbTGphdmEubGFuZy5PYmplY3Q7kM5YnxBzKWwCAAB4cAAAAAFzcgBDY29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLnBsdWdpbnMuY2x1c3RlcnN1cHBvcnQuQ2x1c3Rlck5vdGlmaWNhdGlvbhY+6VckvG9pAgACSQAEdHlwZUwABGRhdGFxAH4AA3hwAAAAAnQAL0RvY3VtZW50VHlwZVJ1bGVDYWNoZTpDb3VudHlNYWludGVuYW5jZURvY3VtZW50cHB0AAZpbnZva2V1cgASW0xqYXZhLmxhbmcuQ2xhc3M7qxbXrsvNWpkCAAB4cAAAAAF2cgAUamF2YS5pby5TZXJpYWxpemFibGUQm2EN15tk7QIAAHhwc3IAKG9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuU2VydmljZUluZm/FFyO2KQS3ugIAC0wABWFsaXZldAATTGphdmEvbGFuZy9Cb29sZWFuO0wAFGVuZHBvaW50QWx0ZXJuYXRlVXJscQB+AARMAAtlbmRwb2ludFVybHEAfgAETAAKbG9ja1Zlck5icnQAE0xqYXZhL2xhbmcvSW50ZWdlcjtMAA5tZXNzYWdlRW50cnlJZHQAEExqYXZhL2xhbmcvTG9uZztMAAVxbmFtZXQAG0xqYXZheC94bWwvbmFtZXNwYWNlL1FOYW1lO0wAGnNlcmlhbGl6ZWRTZXJ2aWNlTmFtZXNwYWNlcQB+AARMAAhzZXJ2ZXJJcHEAfgAETAARc2VydmljZURlZmluaXRpb250ADBMb3JnL2t1YWxpL3JpY2Uva3NiL21lc3NhZ2luZy9TZXJ2aWNlRGVmaW5pdGlvbjtMAAtzZXJ2aWNlTmFtZXEAfgAETAAQc2VydmljZU5hbWVzcGFjZXEAfgAEeHBzcgARamF2YS5sYW5nLkJvb2xlYW7NIHKA1Zz67gIAAVoABXZhbHVleHABcHQAQGh0dHA6Ly9sb2NhbGhvc3Q6ODA4MC9rci1kZXYvcmVtb3RpbmcvT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2VzcgARamF2YS5sYW5nLkludGVnZXIS4qCk94GHOAIAAUkABXZhbHVleHIAEGphdmEubGFuZy5OdW1iZXKGrJUdC5TgiwIAAHhwAAAAAXNyAA5qYXZhLmxhbmcuTG9uZzuL5JDMjyPfAgABSgAFdmFsdWV4cQB+AB0AAAAAAAAPfXNyABlqYXZheC54bWwubmFtZXNwYWNlLlFOYW1lgW2oLfw73WwCAANMAAlsb2NhbFBhcnRxAH4ABEwADG5hbWVzcGFjZVVSSXEAfgAETAAGcHJlZml4cQB+AAR4cHQAGk9TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNldAAAcQB+ACR0B3RyTzBBQlhOeUFESnZjbWN1YTNWaGJHa3VjbWxqWlM1cmMySXViV1Z6YzJGbmFXNW5Ma3BoZG1GVFpYSjJhV05sUkdWbWFXNXBkR2x2YnJibkRWOUJOR3p4QWdBQlRBQVJjMlZ5ZG1salpVbHVkR1Z5Wm1GalpYTjBBQkJNYW1GMllTOTFkR2xzTDB4cGMzUTdlSElBTG05eVp5NXJkV0ZzYVM1eWFXTmxMbXR6WWk1dFpYTnpZV2RwYm1jdVUyVnlkbWxqWlVSbFptbHVhWFJwYjI0QW13SlBXTjBwZmdJQURVd0FDMkoxYzFObFkzVnlhWFI1ZEFBVFRHcGhkbUV2YkdGdVp5OUNiMjlzWldGdU8wd0FEMk55WldSbGJuUnBZV3h6Vkhsd1pYUUFURXh2Y21jdmEzVmhiR2t2Y21salpTOWpiM0psTDNObFkzVnlhWFI1TDJOeVpXUmxiblJwWVd4ekwwTnlaV1JsYm5ScFlXeHpVMjkxY21ObEpFTnlaV1JsYm5ScFlXeHpWSGx3WlR0TUFCQnNiMk5oYkZObGNuWnBZMlZPWVcxbGRBQVNUR3BoZG1FdmJHRnVaeTlUZEhKcGJtYzdUQUFYYldWemMyRm5aVVY0WTJWd2RHbHZia2hoYm1Sc1pYSnhBSDRBQlV3QURHMXBiR3hwYzFSdlRHbDJaWFFBRUV4cVlYWmhMMnhoYm1jdlRHOXVaenRNQUFod2NtbHZjbWwwZVhRQUUweHFZWFpoTDJ4aGJtY3ZTVzUwWldkbGNqdE1BQVZ4ZFdWMVpYRUFmZ0FEVEFBTmNtVjBjbmxCZEhSbGJYQjBjM0VBZmdBSFRBQUhjMlZ5ZG1salpYUUFFa3hxWVhaaEwyeGhibWN2VDJKcVpXTjBPMHdBRDNObGNuWnBZMlZGYm1SUWIybHVkSFFBRGt4cVlYWmhMMjVsZEM5VlVrdzdUQUFUYzJWeWRtbGpaVTVoYldWVGNHRmpaVlZTU1hFQWZnQUZUQUFRYzJWeWRtbGpaVTVoYldWemNHRmpaWEVBZmdBRlRBQUxjMlZ5ZG1salpWQmhkR2h4QUg0QUJYaHdjM0lBRVdwaGRtRXViR0Z1Wnk1Q2IyOXNaV0Z1elNCeWdOV2MrdTRDQUFGYUFBVjJZV3gxWlhod0FYQjBBQnBQVTBOaFkyaGxUbTkwYVdacFkyRjBhVzl1VTJWeWRtbGpaWFFBVFc5eVp5NXJkV0ZzYVM1eWFXTmxMbXR6WWk1dFpYTnpZV2RwYm1jdVpYaGpaWEIwYVc5dWFHRnVaR3hwYm1jdVJHVm1ZWFZzZEUxbGMzTmhaMlZGZUdObGNIUnBiMjVJWVc1a2JHVnljM0lBRG1waGRtRXViR0Z1Wnk1TWIyNW5PNHZra015UEk5OENBQUZLQUFWMllXeDFaWGh5QUJCcVlYWmhMbXhoYm1jdVRuVnRZbVZ5aHF5VkhRdVU0SXNDQUFCNGNQLy8vLy8vLy8vL2MzSUFFV3BoZG1FdWJHRnVaeTVKYm5SbFoyVnlFdUtncFBlQmh6Z0NBQUZKQUFWMllXeDFaWGh4QUg0QUVBQUFBQU56Y1FCK0FBc0FjUUIrQUJOd2MzSUFER3BoZG1FdWJtVjBMbFZTVEpZbE56WWEvT1J5QXdBSFNRQUlhR0Z6YUVOdlpHVkpBQVJ3YjNKMFRBQUpZWFYwYUc5eWFYUjVjUUIrQUFWTUFBUm1hV3hsY1FCK0FBVk1BQVJvYjNOMGNRQitBQVZNQUFod2NtOTBiMk52YkhFQWZnQUZUQUFEY21WbWNRQitBQVY0Y1AvLy8vOEFBQitRZEFBT2JHOWpZV3hvYjNOME9qZ3dPREIwQUNzdmEzSXRaR1YyTDNKbGJXOTBhVzVuTDA5VFEyRmphR1ZPYjNScFptbGpZWFJwYjI1VFpYSjJhV05sZEFBSmJHOWpZV3hvYjNOMGRBQUVhSFIwY0hCNGRBQUFkQUFHVkZKQlZrVk1kQUFCTDNOeUFCTnFZWFpoTG5WMGFXd3VRWEp5WVhsTWFYTjBlSUhTSFpuSFlaMERBQUZKQUFSemFYcGxlSEFBQUFBRmR3UUFBQUFLZEFBemIzSm5M';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 393    FOR UPDATE;        
    buffer := 'bXQxWVd4cExuSnBZMlV1YTNOaUxtMWxjM05oWjJsdVp5NXpaWEoyYVdObExrdFRRa3BoZG1GVFpYSjJhV05sZEFBOFkyOXRMbTl3Wlc1emVXMXdhRzl1ZVM1dmMyTmhZMmhsTG1KaGMyVXVaWFpsYm5SekxrTmhZMmhsUlc1MGNubEZkbVZ1ZEV4cGMzUmxibVZ5ZEFBM1kyOXRMbTl3Wlc1emVXMXdhRzl1ZVM1dmMyTmhZMmhsTG1KaGMyVXVaWFpsYm5SekxrTmhZMmhsUlhabGJuUk1hWE4wWlc1bGNuUUFGMnBoZG1FdWRYUnBiQzVGZG1WdWRFeHBjM1JsYm1WeWRBQXNZMjl0TG05d1pXNXplVzF3YUc5dWVTNXZjMk5oWTJobExtSmhjMlV1VEdsbVpXTjVZMnhsUVhkaGNtVjR0AA0xMjkuMTg2Ljc5LjQ1c3IAMm9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuSmF2YVNlcnZpY2VEZWZpbml0aW9utucNX0E0bPECAAFMABFzZXJ2aWNlSW50ZXJmYWNlc3QAEExqYXZhL3V0aWwvTGlzdDt4cgAub3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5TZXJ2aWNlRGVmaW5pdGlvbgCbAk9Y3Sl+AgANTAALYnVzU2VjdXJpdHlxAH4AE0wAD2NyZWRlbnRpYWxzVHlwZXQATExvcmcva3VhbGkvcmljZS9jb3JlL3NlY3VyaXR5L2NyZWRlbnRpYWxzL0NyZWRlbnRpYWxzU291cmNlJENyZWRlbnRpYWxzVHlwZTtMABBsb2NhbFNlcnZpY2VOYW1lcQB+AARMABdtZXNzYWdlRXhjZXB0aW9uSGFuZGxlcnEAfgAETAAMbWlsbGlzVG9MaXZlcQB+ABVMAAhwcmlvcml0eXEAfgAUTAAFcXVldWVxAH4AE0wADXJldHJ5QXR0ZW1wdHNxAH4AFEwAB3NlcnZpY2V0ABJMamF2YS9sYW5nL09iamVjdDtMAA9zZXJ2aWNlRW5kUG9pbnR0AA5MamF2YS9uZXQvVVJMO0wAE3NlcnZpY2VOYW1lU3BhY2VVUklxAH4ABEwAEHNlcnZpY2VOYW1lc3BhY2VxAH4ABEwAC3NlcnZpY2VQYXRocQB+AAR4cHNxAH4AGQFwdAAaT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2V0AE1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLmV4Y2VwdGlvbmhhbmRsaW5nLkRlZmF1bHRNZXNzYWdlRXhjZXB0aW9uSGFuZGxlcnNxAH4AH///////////c3EAfgAcAAAAA3NxAH4AGQBxAH4AMnBzcgAMamF2YS5uZXQuVVJMliU3Nhr85HIDAAdJAAhoYXNoQ29kZUkABHBvcnRMAAlhdXRob3JpdHlxAH4ABEwABGZpbGVxAH4ABEwABGhvc3RxAH4ABEwACHByb3RvY29scQB+AARMAANyZWZxAH4ABHhwaRuioAAAH5B0AA5sb2NhbGhvc3Q6ODA4MHQAKy9rci1kZXYvcmVtb3RpbmcvT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2V0AAlsb2NhbGhvc3R0AARodHRwcHh0AAB0AAZUUkFWRUx0AAEvc3IAE2phdmEudXRpbC5BcnJheUxpc3R4gdIdmcdhnQMAAUkABHNpemV4cAAAAAV3BAAAAAp0ADNvcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLnNlcnZpY2UuS1NCSmF2YVNlcnZpY2V0ADxjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5ldmVudHMuQ2FjaGVFbnRyeUV2ZW50TGlzdGVuZXJ0ADdjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5ldmVudHMuQ2FjaGVFdmVudExpc3RlbmVydAAXamF2YS51dGlsLkV2ZW50TGlzdGVuZXJ0ACxjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5MaWZlY3ljbGVBd2FyZXhxAH4AI3QABlRSQVZFTA==';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
INSERT INTO KRSB_MSG_PYLD_T (MSG_PYLD,MSG_QUE_ID)
  VALUES (EMPTY_CLOB(),394)
/
-- Length: 6108
-- Chunks: 2
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 394    FOR UPDATE;        
    buffer := 'rO0ABXNyAC1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLkFzeW5jaHJvbm91c0NhbGzxnQ5Y0t0lZQIAB1oAFWlnbm9yZVN0b3JlQW5kRm9yd2FyZFsACWFyZ3VtZW50c3QAE1tMamF2YS9sYW5nL09iamVjdDtMAAhjYWxsYmFja3QAM0xvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL0FzeW5jaHJvbm91c0NhbGxiYWNrO0wAB2NvbnRleHR0ABZMamF2YS9pby9TZXJpYWxpemFibGU7TAAKbWV0aG9kTmFtZXQAEkxqYXZhL2xhbmcvU3RyaW5nO1sACnBhcmFtVHlwZXN0ABJbTGphdmEvbGFuZy9DbGFzcztMAAtzZXJ2aWNlSW5mb3QAKkxvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL1NlcnZpY2VJbmZvO3hwAHVyABNbTGphdmEubGFuZy5PYmplY3Q7kM5YnxBzKWwCAAB4cAAAAAFzcgBDY29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLnBsdWdpbnMuY2x1c3RlcnN1cHBvcnQuQ2x1c3Rlck5vdGlmaWNhdGlvbhY+6VckvG9pAgACSQAEdHlwZUwABGRhdGFxAH4AA3hwAAAAAnQAM0RvY3VtZW50VHlwZVJ1bGVDYWNoZTpQb3N0YWxDb2RlTWFpbnRlbmFuY2VEb2N1bWVudHBwdAAGaW52b2tldXIAEltMamF2YS5sYW5nLkNsYXNzO6sW167LzVqZAgAAeHAAAAABdnIAFGphdmEuaW8uU2VyaWFsaXphYmxlEJthDdebZO0CAAB4cHNyAChvcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLlNlcnZpY2VJbmZvxRcjtikEt7oCAAtMAAVhbGl2ZXQAE0xqYXZhL2xhbmcvQm9vbGVhbjtMABRlbmRwb2ludEFsdGVybmF0ZVVybHEAfgAETAALZW5kcG9pbnRVcmxxAH4ABEwACmxvY2tWZXJOYnJ0ABNMamF2YS9sYW5nL0ludGVnZXI7TAAObWVzc2FnZUVudHJ5SWR0ABBMamF2YS9sYW5nL0xvbmc7TAAFcW5hbWV0ABtMamF2YXgveG1sL25hbWVzcGFjZS9RTmFtZTtMABpzZXJpYWxpemVkU2VydmljZU5hbWVzcGFjZXEAfgAETAAIc2VydmVySXBxAH4ABEwAEXNlcnZpY2VEZWZpbml0aW9udAAwTG9yZy9rdWFsaS9yaWNlL2tzYi9tZXNzYWdpbmcvU2VydmljZURlZmluaXRpb247TAALc2VydmljZU5hbWVxAH4ABEwAEHNlcnZpY2VOYW1lc3BhY2VxAH4ABHhwc3IAEWphdmEubGFuZy5Cb29sZWFuzSBygNWc+u4CAAFaAAV2YWx1ZXhwAXB0AEBodHRwOi8vbG9jYWxob3N0OjgwODAva3ItZGV2L3JlbW90aW5nL09TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNlc3IAEWphdmEubGFuZy5JbnRlZ2VyEuKgpPeBhzgCAAFJAAV2YWx1ZXhyABBqYXZhLmxhbmcuTnVtYmVyhqyVHQuU4IsCAAB4cAAAAAFzcgAOamF2YS5sYW5nLkxvbmc7i+SQzI8j3wIAAUoABXZhbHVleHEAfgAdAAAAAAAAD31zcgAZamF2YXgueG1sLm5hbWVzcGFjZS5RTmFtZYFtqC38O91sAgADTAAJbG9jYWxQYXJ0cQB+AARMAAxuYW1lc3BhY2VVUklxAH4ABEwABnByZWZpeHEAfgAEeHB0ABpPU0NhY2hlTm90aWZpY2F0aW9uU2VydmljZXQAAHEAfgAkdAd0ck8wQUJYTnlBREp2Y21jdWEzVmhiR2t1Y21salpTNXJjMkl1YldWemMyRm5hVzVuTGtwaGRtRlRaWEoyYVdObFJHVm1hVzVwZEdsdmJyYm5EVjlCTkd6eEFnQUJUQUFSYzJWeWRtbGpaVWx1ZEdWeVptRmpaWE4wQUJCTWFtRjJZUzkxZEdsc0wweHBjM1E3ZUhJQUxtOXlaeTVyZFdGc2FTNXlhV05sTG10ellpNXRaWE56WVdkcGJtY3VVMlZ5ZG1salpVUmxabWx1YVhScGIyNEFtd0pQV04wcGZnSUFEVXdBQzJKMWMxTmxZM1Z5YVhSNWRBQVRUR3BoZG1FdmJHRnVaeTlDYjI5c1pXRnVPMHdBRDJOeVpXUmxiblJwWVd4elZIbHdaWFFBVEV4dmNtY3ZhM1ZoYkdrdmNtbGpaUzlqYjNKbEwzTmxZM1Z5YVhSNUwyTnlaV1JsYm5ScFlXeHpMME55WldSbGJuUnBZV3h6VTI5MWNtTmxKRU55WldSbGJuUnBZV3h6Vkhsd1pUdE1BQkJzYjJOaGJGTmxjblpwWTJWT1lXMWxkQUFTVEdwaGRtRXZiR0Z1Wnk5VGRISnBibWM3VEFBWGJXVnpjMkZuWlVWNFkyVndkR2x2YmtoaGJtUnNaWEp4QUg0QUJVd0FERzFwYkd4cGMxUnZUR2wyWlhRQUVFeHFZWFpoTDJ4aGJtY3ZURzl1Wnp0TUFBaHdjbWx2Y21sMGVYUUFFMHhxWVhaaEwyeGhibWN2U1c1MFpXZGxjanRNQUFWeGRXVjFaWEVBZmdBRFRBQU5jbVYwY25sQmRIUmxiWEIwYzNFQWZnQUhUQUFIYzJWeWRtbGpaWFFBRWt4cVlYWmhMMnhoYm1jdlQySnFaV04wTzB3QUQzTmxjblpwWTJWRmJtUlFiMmx1ZEhRQURreHFZWFpoTDI1bGRDOVZVa3c3VEFBVGMyVnlkbWxqWlU1aGJXVlRjR0ZqWlZWU1NYRUFmZ0FGVEFBUWMyVnlkbWxqWlU1aGJXVnpjR0ZqWlhFQWZnQUZUQUFMYzJWeWRtbGpaVkJoZEdoeEFINEFCWGh3YzNJQUVXcGhkbUV1YkdGdVp5NUNiMjlzWldGdXpTQnlnTldjK3U0Q0FBRmFBQVYyWVd4MVpYaHdBWEIwQUJwUFUwTmhZMmhsVG05MGFXWnBZMkYwYVc5dVUyVnlkbWxqWlhRQVRXOXlaeTVyZFdGc2FTNXlhV05sTG10ellpNXRaWE56WVdkcGJtY3VaWGhqWlhCMGFXOXVhR0Z1Wkd4cGJtY3VSR1ZtWVhWc2RFMWxjM05oWjJWRmVHTmxjSFJwYjI1SVlXNWtiR1Z5YzNJQURtcGhkbUV1YkdGdVp5NU1iMjVuTzR2a2tNeVBJOThDQUFGS0FBVjJZV3gxWlhoeUFCQnFZWFpoTG14aGJtY3VUblZ0WW1WeWhxeVZIUXVVNElzQ0FBQjRjUC8vLy8vLy8vLy9jM0lBRVdwaGRtRXViR0Z1Wnk1SmJuUmxaMlZ5RXVLZ3BQZUJoemdDQUFGSkFBVjJZV3gxWlhoeEFINEFFQUFBQUFOemNRQitBQXNBY1FCK0FCTndjM0lBREdwaGRtRXVibVYwTGxWU1RKWWxOellhL09SeUF3QUhTUUFJYUdGemFFTnZaR1ZKQUFSd2IzSjBUQUFKWVhWMGFHOXlhWFI1Y1FCK0FBVk1BQVJtYVd4bGNRQitBQVZNQUFSb2IzTjBjUUIrQUFWTUFBaHdjbTkwYjJOdmJIRUFmZ0FGVEFBRGNtVm1jUUIrQUFWNGNQLy8vLzhBQUIrUWRBQU9iRzlqWVd4b2IzTjBPamd3T0RCMEFDc3ZhM0l0WkdWMkwzSmxiVzkwYVc1bkwwOVRRMkZqYUdWT2IzUnBabWxqWVhScGIyNVRaWEoyYVdObGRBQUpiRzlqWVd4b2IzTjBkQUFFYUhSMGNIQjRkQUFBZEFBR1ZGSkJWa1ZNZEFBQkwzTnlBQk5xWVhaaExuVjBhV3d1UVhKeVlYbE1hWE4wZUlIU0habkhZWjBEQUFGSkFBUnphWHBsZUhBQUFBQUZkd1FBQUFBS2RBQXpi';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 394    FOR UPDATE;        
    buffer := 'M0puTG10MVlXeHBMbkpwWTJVdWEzTmlMbTFsYzNOaFoybHVaeTV6WlhKMmFXTmxMa3RUUWtwaGRtRlRaWEoyYVdObGRBQThZMjl0TG05d1pXNXplVzF3YUc5dWVTNXZjMk5oWTJobExtSmhjMlV1WlhabGJuUnpMa05oWTJobFJXNTBjbmxGZG1WdWRFeHBjM1JsYm1WeWRBQTNZMjl0TG05d1pXNXplVzF3YUc5dWVTNXZjMk5oWTJobExtSmhjMlV1WlhabGJuUnpMa05oWTJobFJYWmxiblJNYVhOMFpXNWxjblFBRjJwaGRtRXVkWFJwYkM1RmRtVnVkRXhwYzNSbGJtVnlkQUFzWTI5dExtOXdaVzV6ZVcxd2FHOXVlUzV2YzJOaFkyaGxMbUpoYzJVdVRHbG1aV041WTJ4bFFYZGhjbVY0dAANMTI5LjE4Ni43OS40NXNyADJvcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLkphdmFTZXJ2aWNlRGVmaW5pdGlvbrbnDV9BNGzxAgABTAARc2VydmljZUludGVyZmFjZXN0ABBMamF2YS91dGlsL0xpc3Q7eHIALm9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuU2VydmljZURlZmluaXRpb24AmwJPWN0pfgIADUwAC2J1c1NlY3VyaXR5cQB+ABNMAA9jcmVkZW50aWFsc1R5cGV0AExMb3JnL2t1YWxpL3JpY2UvY29yZS9zZWN1cml0eS9jcmVkZW50aWFscy9DcmVkZW50aWFsc1NvdXJjZSRDcmVkZW50aWFsc1R5cGU7TAAQbG9jYWxTZXJ2aWNlTmFtZXEAfgAETAAXbWVzc2FnZUV4Y2VwdGlvbkhhbmRsZXJxAH4ABEwADG1pbGxpc1RvTGl2ZXEAfgAVTAAIcHJpb3JpdHlxAH4AFEwABXF1ZXVlcQB+ABNMAA1yZXRyeUF0dGVtcHRzcQB+ABRMAAdzZXJ2aWNldAASTGphdmEvbGFuZy9PYmplY3Q7TAAPc2VydmljZUVuZFBvaW50dAAOTGphdmEvbmV0L1VSTDtMABNzZXJ2aWNlTmFtZVNwYWNlVVJJcQB+AARMABBzZXJ2aWNlTmFtZXNwYWNlcQB+AARMAAtzZXJ2aWNlUGF0aHEAfgAEeHBzcQB+ABkBcHQAGk9TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNldABNb3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5leGNlcHRpb25oYW5kbGluZy5EZWZhdWx0TWVzc2FnZUV4Y2VwdGlvbkhhbmRsZXJzcQB+AB///////////3NxAH4AHAAAAANzcQB+ABkAcQB+ADJwc3IADGphdmEubmV0LlVSTJYlNzYa/ORyAwAHSQAIaGFzaENvZGVJAARwb3J0TAAJYXV0aG9yaXR5cQB+AARMAARmaWxlcQB+AARMAARob3N0cQB+AARMAAhwcm90b2NvbHEAfgAETAADcmVmcQB+AAR4cGkboqAAAB+QdAAObG9jYWxob3N0OjgwODB0ACsva3ItZGV2L3JlbW90aW5nL09TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNldAAJbG9jYWxob3N0dAAEaHR0cHB4dAAAdAAGVFJBVkVMdAABL3NyABNqYXZhLnV0aWwuQXJyYXlMaXN0eIHSHZnHYZ0DAAFJAARzaXpleHAAAAAFdwQAAAAKdAAzb3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5zZXJ2aWNlLktTQkphdmFTZXJ2aWNldAA8Y29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLmJhc2UuZXZlbnRzLkNhY2hlRW50cnlFdmVudExpc3RlbmVydAA3Y29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLmJhc2UuZXZlbnRzLkNhY2hlRXZlbnRMaXN0ZW5lcnQAF2phdmEudXRpbC5FdmVudExpc3RlbmVydAAsY29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLmJhc2UuTGlmZWN5Y2xlQXdhcmV4cQB+ACN0AAZUUkFWRUw=';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
INSERT INTO KRSB_MSG_PYLD_T (MSG_PYLD,MSG_QUE_ID)
  VALUES (EMPTY_CLOB(),395)
/
-- Length: 6100
-- Chunks: 2
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 395    FOR UPDATE;        
    buffer := 'rO0ABXNyAC1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLkFzeW5jaHJvbm91c0NhbGzxnQ5Y0t0lZQIAB1oAFWlnbm9yZVN0b3JlQW5kRm9yd2FyZFsACWFyZ3VtZW50c3QAE1tMamF2YS9sYW5nL09iamVjdDtMAAhjYWxsYmFja3QAM0xvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL0FzeW5jaHJvbm91c0NhbGxiYWNrO0wAB2NvbnRleHR0ABZMamF2YS9pby9TZXJpYWxpemFibGU7TAAKbWV0aG9kTmFtZXQAEkxqYXZhL2xhbmcvU3RyaW5nO1sACnBhcmFtVHlwZXN0ABJbTGphdmEvbGFuZy9DbGFzcztMAAtzZXJ2aWNlSW5mb3QAKkxvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL1NlcnZpY2VJbmZvO3hwAHVyABNbTGphdmEubGFuZy5PYmplY3Q7kM5YnxBzKWwCAAB4cAAAAAFzcgBDY29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLnBsdWdpbnMuY2x1c3RlcnN1cHBvcnQuQ2x1c3Rlck5vdGlmaWNhdGlvbhY+6VckvG9pAgACSQAEdHlwZUwABGRhdGFxAH4AA3hwAAAAAnQALkRvY3VtZW50VHlwZVJ1bGVDYWNoZTpTdGF0ZU1haW50ZW5hbmNlRG9jdW1lbnRwcHQABmludm9rZXVyABJbTGphdmEubGFuZy5DbGFzczurFteuy81amQIAAHhwAAAAAXZyABRqYXZhLmlvLlNlcmlhbGl6YWJsZRCbYQ3Xm2TtAgAAeHBzcgAob3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5TZXJ2aWNlSW5mb8UXI7YpBLe6AgALTAAFYWxpdmV0ABNMamF2YS9sYW5nL0Jvb2xlYW47TAAUZW5kcG9pbnRBbHRlcm5hdGVVcmxxAH4ABEwAC2VuZHBvaW50VXJscQB+AARMAApsb2NrVmVyTmJydAATTGphdmEvbGFuZy9JbnRlZ2VyO0wADm1lc3NhZ2VFbnRyeUlkdAAQTGphdmEvbGFuZy9Mb25nO0wABXFuYW1ldAAbTGphdmF4L3htbC9uYW1lc3BhY2UvUU5hbWU7TAAac2VyaWFsaXplZFNlcnZpY2VOYW1lc3BhY2VxAH4ABEwACHNlcnZlcklwcQB+AARMABFzZXJ2aWNlRGVmaW5pdGlvbnQAMExvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL1NlcnZpY2VEZWZpbml0aW9uO0wAC3NlcnZpY2VOYW1lcQB+AARMABBzZXJ2aWNlTmFtZXNwYWNlcQB+AAR4cHNyABFqYXZhLmxhbmcuQm9vbGVhbs0gcoDVnPruAgABWgAFdmFsdWV4cAFwdABAaHR0cDovL2xvY2FsaG9zdDo4MDgwL2tyLWRldi9yZW1vdGluZy9PU0NhY2hlTm90aWZpY2F0aW9uU2VydmljZXNyABFqYXZhLmxhbmcuSW50ZWdlchLioKT3gYc4AgABSQAFdmFsdWV4cgAQamF2YS5sYW5nLk51bWJlcoaslR0LlOCLAgAAeHAAAAABc3IADmphdmEubGFuZy5Mb25nO4vkkMyPI98CAAFKAAV2YWx1ZXhxAH4AHQAAAAAAAA99c3IAGWphdmF4LnhtbC5uYW1lc3BhY2UuUU5hbWWBbagt/DvdbAIAA0wACWxvY2FsUGFydHEAfgAETAAMbmFtZXNwYWNlVVJJcQB+AARMAAZwcmVmaXhxAH4ABHhwdAAaT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2V0AABxAH4AJHQHdHJPMEFCWE55QURKdmNtY3VhM1ZoYkdrdWNtbGpaUzVyYzJJdWJXVnpjMkZuYVc1bkxrcGhkbUZUWlhKMmFXTmxSR1ZtYVc1cGRHbHZicmJuRFY5Qk5HenhBZ0FCVEFBUmMyVnlkbWxqWlVsdWRHVnlabUZqWlhOMEFCQk1hbUYyWVM5MWRHbHNMMHhwYzNRN2VISUFMbTl5Wnk1cmRXRnNhUzV5YVdObExtdHpZaTV0WlhOellXZHBibWN1VTJWeWRtbGpaVVJsWm1sdWFYUnBiMjRBbXdKUFdOMHBmZ0lBRFV3QUMySjFjMU5sWTNWeWFYUjVkQUFUVEdwaGRtRXZiR0Z1Wnk5Q2IyOXNaV0Z1TzB3QUQyTnlaV1JsYm5ScFlXeHpWSGx3WlhRQVRFeHZjbWN2YTNWaGJHa3ZjbWxqWlM5amIzSmxMM05sWTNWeWFYUjVMMk55WldSbGJuUnBZV3h6TDBOeVpXUmxiblJwWVd4elUyOTFjbU5sSkVOeVpXUmxiblJwWVd4elZIbHdaVHRNQUJCc2IyTmhiRk5sY25acFkyVk9ZVzFsZEFBU1RHcGhkbUV2YkdGdVp5OVRkSEpwYm1jN1RBQVhiV1Z6YzJGblpVVjRZMlZ3ZEdsdmJraGhibVJzWlhKeEFINEFCVXdBREcxcGJHeHBjMVJ2VEdsMlpYUUFFRXhxWVhaaEwyeGhibWN2VEc5dVp6dE1BQWh3Y21sdmNtbDBlWFFBRTB4cVlYWmhMMnhoYm1jdlNXNTBaV2RsY2p0TUFBVnhkV1YxWlhFQWZnQURUQUFOY21WMGNubEJkSFJsYlhCMGMzRUFmZ0FIVEFBSGMyVnlkbWxqWlhRQUVreHFZWFpoTDJ4aGJtY3ZUMkpxWldOME8wd0FEM05sY25acFkyVkZibVJRYjJsdWRIUUFEa3hxWVhaaEwyNWxkQzlWVWt3N1RBQVRjMlZ5ZG1salpVNWhiV1ZUY0dGalpWVlNTWEVBZmdBRlRBQVFjMlZ5ZG1salpVNWhiV1Z6Y0dGalpYRUFmZ0FGVEFBTGMyVnlkbWxqWlZCaGRHaHhBSDRBQlhod2MzSUFFV3BoZG1FdWJHRnVaeTVDYjI5c1pXRnV6U0J5Z05XYyt1NENBQUZhQUFWMllXeDFaWGh3QVhCMEFCcFBVME5oWTJobFRtOTBhV1pwWTJGMGFXOXVVMlZ5ZG1salpYUUFUVzl5Wnk1cmRXRnNhUzV5YVdObExtdHpZaTV0WlhOellXZHBibWN1WlhoalpYQjBhVzl1YUdGdVpHeHBibWN1UkdWbVlYVnNkRTFsYzNOaFoyVkZlR05sY0hScGIyNUlZVzVrYkdWeWMzSUFEbXBoZG1FdWJHRnVaeTVNYjI1bk80dmtrTXlQSTk4Q0FBRktBQVYyWVd4MVpYaHlBQkJxWVhaaExteGhibWN1VG5WdFltVnlocXlWSFF1VTRJc0NBQUI0Y1AvLy8vLy8vLy8vYzNJQUVXcGhkbUV1YkdGdVp5NUpiblJsWjJWeUV1S2dwUGVCaHpnQ0FBRkpBQVYyWVd4MVpYaHhBSDRBRUFBQUFBTnpjUUIrQUFzQWNRQitBQk53YzNJQURHcGhkbUV1Ym1WMExsVlNUSllsTnpZYS9PUnlBd0FIU1FBSWFHRnphRU52WkdWSkFBUndiM0owVEFBSllYVjBhRzl5YVhSNWNRQitBQVZNQUFSbWFXeGxjUUIrQUFWTUFBUm9iM04wY1FCK0FBVk1BQWh3Y205MGIyTnZiSEVBZmdBRlRBQURjbVZtY1FCK0FBVjRjUC8vLy84QUFCK1FkQUFPYkc5allXeG9iM04wT2pnd09EQjBBQ3N2YTNJdFpHVjJMM0psYlc5MGFXNW5MMDlUUTJGamFHVk9iM1JwWm1sallYUnBiMjVUWlhKMmFXTmxkQUFKYkc5allXeG9iM04wZEFBRWFIUjBjSEI0ZEFBQWRBQUdWRkpCVmtWTWRBQUJMM055QUJOcVlYWmhMblYwYVd3dVFYSnlZWGxNYVhOMGVJSFNIWm5IWVowREFBRkpBQVJ6YVhwbGVIQUFBQUFGZHdRQUFBQUtkQUF6YjNKbkxt';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 395    FOR UPDATE;        
    buffer := 'dDFZV3hwTG5KcFkyVXVhM05pTG0xbGMzTmhaMmx1Wnk1elpYSjJhV05sTGt0VFFrcGhkbUZUWlhKMmFXTmxkQUE4WTI5dExtOXdaVzV6ZVcxd2FHOXVlUzV2YzJOaFkyaGxMbUpoYzJVdVpYWmxiblJ6TGtOaFkyaGxSVzUwY25sRmRtVnVkRXhwYzNSbGJtVnlkQUEzWTI5dExtOXdaVzV6ZVcxd2FHOXVlUzV2YzJOaFkyaGxMbUpoYzJVdVpYWmxiblJ6TGtOaFkyaGxSWFpsYm5STWFYTjBaVzVsY25RQUYycGhkbUV1ZFhScGJDNUZkbVZ1ZEV4cGMzUmxibVZ5ZEFBc1kyOXRMbTl3Wlc1emVXMXdhRzl1ZVM1dmMyTmhZMmhsTG1KaGMyVXVUR2xtWldONVkyeGxRWGRoY21WNHQADTEyOS4xODYuNzkuNDVzcgAyb3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5KYXZhU2VydmljZURlZmluaXRpb2625w1fQTRs8QIAAUwAEXNlcnZpY2VJbnRlcmZhY2VzdAAQTGphdmEvdXRpbC9MaXN0O3hyAC5vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLlNlcnZpY2VEZWZpbml0aW9uAJsCT1jdKX4CAA1MAAtidXNTZWN1cml0eXEAfgATTAAPY3JlZGVudGlhbHNUeXBldABMTG9yZy9rdWFsaS9yaWNlL2NvcmUvc2VjdXJpdHkvY3JlZGVudGlhbHMvQ3JlZGVudGlhbHNTb3VyY2UkQ3JlZGVudGlhbHNUeXBlO0wAEGxvY2FsU2VydmljZU5hbWVxAH4ABEwAF21lc3NhZ2VFeGNlcHRpb25IYW5kbGVycQB+AARMAAxtaWxsaXNUb0xpdmVxAH4AFUwACHByaW9yaXR5cQB+ABRMAAVxdWV1ZXEAfgATTAANcmV0cnlBdHRlbXB0c3EAfgAUTAAHc2VydmljZXQAEkxqYXZhL2xhbmcvT2JqZWN0O0wAD3NlcnZpY2VFbmRQb2ludHQADkxqYXZhL25ldC9VUkw7TAATc2VydmljZU5hbWVTcGFjZVVSSXEAfgAETAAQc2VydmljZU5hbWVzcGFjZXEAfgAETAALc2VydmljZVBhdGhxAH4ABHhwc3EAfgAZAXB0ABpPU0NhY2hlTm90aWZpY2F0aW9uU2VydmljZXQATW9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuZXhjZXB0aW9uaGFuZGxpbmcuRGVmYXVsdE1lc3NhZ2VFeGNlcHRpb25IYW5kbGVyc3EAfgAf//////////9zcQB+ABwAAAADc3EAfgAZAHEAfgAycHNyAAxqYXZhLm5ldC5VUkyWJTc2GvzkcgMAB0kACGhhc2hDb2RlSQAEcG9ydEwACWF1dGhvcml0eXEAfgAETAAEZmlsZXEAfgAETAAEaG9zdHEAfgAETAAIcHJvdG9jb2xxAH4ABEwAA3JlZnEAfgAEeHBpG6KgAAAfkHQADmxvY2FsaG9zdDo4MDgwdAArL2tyLWRldi9yZW1vdGluZy9PU0NhY2hlTm90aWZpY2F0aW9uU2VydmljZXQACWxvY2FsaG9zdHQABGh0dHBweHQAAHQABlRSQVZFTHQAAS9zcgATamF2YS51dGlsLkFycmF5TGlzdHiB0h2Zx2GdAwABSQAEc2l6ZXhwAAAABXcEAAAACnQAM29yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuc2VydmljZS5LU0JKYXZhU2VydmljZXQAPGNvbS5vcGVuc3ltcGhvbnkub3NjYWNoZS5iYXNlLmV2ZW50cy5DYWNoZUVudHJ5RXZlbnRMaXN0ZW5lcnQAN2NvbS5vcGVuc3ltcGhvbnkub3NjYWNoZS5iYXNlLmV2ZW50cy5DYWNoZUV2ZW50TGlzdGVuZXJ0ABdqYXZhLnV0aWwuRXZlbnRMaXN0ZW5lcnQALGNvbS5vcGVuc3ltcGhvbnkub3NjYWNoZS5iYXNlLkxpZmVjeWNsZUF3YXJleHEAfgAjdAAGVFJBVkVM';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
INSERT INTO KRSB_MSG_PYLD_T (MSG_PYLD,MSG_QUE_ID)
  VALUES (EMPTY_CLOB(),396)
/
-- Length: 6104
-- Chunks: 2
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 396    FOR UPDATE;        
    buffer := 'rO0ABXNyAC1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLkFzeW5jaHJvbm91c0NhbGzxnQ5Y0t0lZQIAB1oAFWlnbm9yZVN0b3JlQW5kRm9yd2FyZFsACWFyZ3VtZW50c3QAE1tMamF2YS9sYW5nL09iamVjdDtMAAhjYWxsYmFja3QAM0xvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL0FzeW5jaHJvbm91c0NhbGxiYWNrO0wAB2NvbnRleHR0ABZMamF2YS9pby9TZXJpYWxpemFibGU7TAAKbWV0aG9kTmFtZXQAEkxqYXZhL2xhbmcvU3RyaW5nO1sACnBhcmFtVHlwZXN0ABJbTGphdmEvbGFuZy9DbGFzcztMAAtzZXJ2aWNlSW5mb3QAKkxvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL1NlcnZpY2VJbmZvO3hwAHVyABNbTGphdmEubGFuZy5PYmplY3Q7kM5YnxBzKWwCAAB4cAAAAAFzcgBDY29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLnBsdWdpbnMuY2x1c3RlcnN1cHBvcnQuQ2x1c3Rlck5vdGlmaWNhdGlvbhY+6VckvG9pAgACSQAEdHlwZUwABGRhdGFxAH4AA3hwAAAAAnQAMERvY3VtZW50VHlwZVJ1bGVDYWNoZTpJZGVudGl0eU1hbmFnZW1lbnREb2N1bWVudHBwdAAGaW52b2tldXIAEltMamF2YS5sYW5nLkNsYXNzO6sW167LzVqZAgAAeHAAAAABdnIAFGphdmEuaW8uU2VyaWFsaXphYmxlEJthDdebZO0CAAB4cHNyAChvcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLlNlcnZpY2VJbmZvxRcjtikEt7oCAAtMAAVhbGl2ZXQAE0xqYXZhL2xhbmcvQm9vbGVhbjtMABRlbmRwb2ludEFsdGVybmF0ZVVybHEAfgAETAALZW5kcG9pbnRVcmxxAH4ABEwACmxvY2tWZXJOYnJ0ABNMamF2YS9sYW5nL0ludGVnZXI7TAAObWVzc2FnZUVudHJ5SWR0ABBMamF2YS9sYW5nL0xvbmc7TAAFcW5hbWV0ABtMamF2YXgveG1sL25hbWVzcGFjZS9RTmFtZTtMABpzZXJpYWxpemVkU2VydmljZU5hbWVzcGFjZXEAfgAETAAIc2VydmVySXBxAH4ABEwAEXNlcnZpY2VEZWZpbml0aW9udAAwTG9yZy9rdWFsaS9yaWNlL2tzYi9tZXNzYWdpbmcvU2VydmljZURlZmluaXRpb247TAALc2VydmljZU5hbWVxAH4ABEwAEHNlcnZpY2VOYW1lc3BhY2VxAH4ABHhwc3IAEWphdmEubGFuZy5Cb29sZWFuzSBygNWc+u4CAAFaAAV2YWx1ZXhwAXB0AEBodHRwOi8vbG9jYWxob3N0OjgwODAva3ItZGV2L3JlbW90aW5nL09TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNlc3IAEWphdmEubGFuZy5JbnRlZ2VyEuKgpPeBhzgCAAFJAAV2YWx1ZXhyABBqYXZhLmxhbmcuTnVtYmVyhqyVHQuU4IsCAAB4cAAAAAFzcgAOamF2YS5sYW5nLkxvbmc7i+SQzI8j3wIAAUoABXZhbHVleHEAfgAdAAAAAAAAD31zcgAZamF2YXgueG1sLm5hbWVzcGFjZS5RTmFtZYFtqC38O91sAgADTAAJbG9jYWxQYXJ0cQB+AARMAAxuYW1lc3BhY2VVUklxAH4ABEwABnByZWZpeHEAfgAEeHB0ABpPU0NhY2hlTm90aWZpY2F0aW9uU2VydmljZXQAAHEAfgAkdAd0ck8wQUJYTnlBREp2Y21jdWEzVmhiR2t1Y21salpTNXJjMkl1YldWemMyRm5hVzVuTGtwaGRtRlRaWEoyYVdObFJHVm1hVzVwZEdsdmJyYm5EVjlCTkd6eEFnQUJUQUFSYzJWeWRtbGpaVWx1ZEdWeVptRmpaWE4wQUJCTWFtRjJZUzkxZEdsc0wweHBjM1E3ZUhJQUxtOXlaeTVyZFdGc2FTNXlhV05sTG10ellpNXRaWE56WVdkcGJtY3VVMlZ5ZG1salpVUmxabWx1YVhScGIyNEFtd0pQV04wcGZnSUFEVXdBQzJKMWMxTmxZM1Z5YVhSNWRBQVRUR3BoZG1FdmJHRnVaeTlDYjI5c1pXRnVPMHdBRDJOeVpXUmxiblJwWVd4elZIbHdaWFFBVEV4dmNtY3ZhM1ZoYkdrdmNtbGpaUzlqYjNKbEwzTmxZM1Z5YVhSNUwyTnlaV1JsYm5ScFlXeHpMME55WldSbGJuUnBZV3h6VTI5MWNtTmxKRU55WldSbGJuUnBZV3h6Vkhsd1pUdE1BQkJzYjJOaGJGTmxjblpwWTJWT1lXMWxkQUFTVEdwaGRtRXZiR0Z1Wnk5VGRISnBibWM3VEFBWGJXVnpjMkZuWlVWNFkyVndkR2x2YmtoaGJtUnNaWEp4QUg0QUJVd0FERzFwYkd4cGMxUnZUR2wyWlhRQUVFeHFZWFpoTDJ4aGJtY3ZURzl1Wnp0TUFBaHdjbWx2Y21sMGVYUUFFMHhxWVhaaEwyeGhibWN2U1c1MFpXZGxjanRNQUFWeGRXVjFaWEVBZmdBRFRBQU5jbVYwY25sQmRIUmxiWEIwYzNFQWZnQUhUQUFIYzJWeWRtbGpaWFFBRWt4cVlYWmhMMnhoYm1jdlQySnFaV04wTzB3QUQzTmxjblpwWTJWRmJtUlFiMmx1ZEhRQURreHFZWFpoTDI1bGRDOVZVa3c3VEFBVGMyVnlkbWxqWlU1aGJXVlRjR0ZqWlZWU1NYRUFmZ0FGVEFBUWMyVnlkbWxqWlU1aGJXVnpjR0ZqWlhFQWZnQUZUQUFMYzJWeWRtbGpaVkJoZEdoeEFINEFCWGh3YzNJQUVXcGhkbUV1YkdGdVp5NUNiMjlzWldGdXpTQnlnTldjK3U0Q0FBRmFBQVYyWVd4MVpYaHdBWEIwQUJwUFUwTmhZMmhsVG05MGFXWnBZMkYwYVc5dVUyVnlkbWxqWlhRQVRXOXlaeTVyZFdGc2FTNXlhV05sTG10ellpNXRaWE56WVdkcGJtY3VaWGhqWlhCMGFXOXVhR0Z1Wkd4cGJtY3VSR1ZtWVhWc2RFMWxjM05oWjJWRmVHTmxjSFJwYjI1SVlXNWtiR1Z5YzNJQURtcGhkbUV1YkdGdVp5NU1iMjVuTzR2a2tNeVBJOThDQUFGS0FBVjJZV3gxWlhoeUFCQnFZWFpoTG14aGJtY3VUblZ0WW1WeWhxeVZIUXVVNElzQ0FBQjRjUC8vLy8vLy8vLy9jM0lBRVdwaGRtRXViR0Z1Wnk1SmJuUmxaMlZ5RXVLZ3BQZUJoemdDQUFGSkFBVjJZV3gxWlhoeEFINEFFQUFBQUFOemNRQitBQXNBY1FCK0FCTndjM0lBREdwaGRtRXVibVYwTGxWU1RKWWxOellhL09SeUF3QUhTUUFJYUdGemFFTnZaR1ZKQUFSd2IzSjBUQUFKWVhWMGFHOXlhWFI1Y1FCK0FBVk1BQVJtYVd4bGNRQitBQVZNQUFSb2IzTjBjUUIrQUFWTUFBaHdjbTkwYjJOdmJIRUFmZ0FGVEFBRGNtVm1jUUIrQUFWNGNQLy8vLzhBQUIrUWRBQU9iRzlqWVd4b2IzTjBPamd3T0RCMEFDc3ZhM0l0WkdWMkwzSmxiVzkwYVc1bkwwOVRRMkZqYUdWT2IzUnBabWxqWVhScGIyNVRaWEoyYVdObGRBQUpiRzlqWVd4b2IzTjBkQUFFYUhSMGNIQjRkQUFBZEFBR1ZGSkJWa1ZNZEFBQkwzTnlBQk5xWVhaaExuVjBhV3d1UVhKeVlYbE1hWE4wZUlIU0habkhZWjBEQUFGSkFBUnphWHBsZUhBQUFBQUZkd1FBQUFBS2RBQXpiM0pu';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 396    FOR UPDATE;        
    buffer := 'TG10MVlXeHBMbkpwWTJVdWEzTmlMbTFsYzNOaFoybHVaeTV6WlhKMmFXTmxMa3RUUWtwaGRtRlRaWEoyYVdObGRBQThZMjl0TG05d1pXNXplVzF3YUc5dWVTNXZjMk5oWTJobExtSmhjMlV1WlhabGJuUnpMa05oWTJobFJXNTBjbmxGZG1WdWRFeHBjM1JsYm1WeWRBQTNZMjl0TG05d1pXNXplVzF3YUc5dWVTNXZjMk5oWTJobExtSmhjMlV1WlhabGJuUnpMa05oWTJobFJYWmxiblJNYVhOMFpXNWxjblFBRjJwaGRtRXVkWFJwYkM1RmRtVnVkRXhwYzNSbGJtVnlkQUFzWTI5dExtOXdaVzV6ZVcxd2FHOXVlUzV2YzJOaFkyaGxMbUpoYzJVdVRHbG1aV041WTJ4bFFYZGhjbVY0dAANMTI5LjE4Ni43OS40NXNyADJvcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLkphdmFTZXJ2aWNlRGVmaW5pdGlvbrbnDV9BNGzxAgABTAARc2VydmljZUludGVyZmFjZXN0ABBMamF2YS91dGlsL0xpc3Q7eHIALm9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuU2VydmljZURlZmluaXRpb24AmwJPWN0pfgIADUwAC2J1c1NlY3VyaXR5cQB+ABNMAA9jcmVkZW50aWFsc1R5cGV0AExMb3JnL2t1YWxpL3JpY2UvY29yZS9zZWN1cml0eS9jcmVkZW50aWFscy9DcmVkZW50aWFsc1NvdXJjZSRDcmVkZW50aWFsc1R5cGU7TAAQbG9jYWxTZXJ2aWNlTmFtZXEAfgAETAAXbWVzc2FnZUV4Y2VwdGlvbkhhbmRsZXJxAH4ABEwADG1pbGxpc1RvTGl2ZXEAfgAVTAAIcHJpb3JpdHlxAH4AFEwABXF1ZXVlcQB+ABNMAA1yZXRyeUF0dGVtcHRzcQB+ABRMAAdzZXJ2aWNldAASTGphdmEvbGFuZy9PYmplY3Q7TAAPc2VydmljZUVuZFBvaW50dAAOTGphdmEvbmV0L1VSTDtMABNzZXJ2aWNlTmFtZVNwYWNlVVJJcQB+AARMABBzZXJ2aWNlTmFtZXNwYWNlcQB+AARMAAtzZXJ2aWNlUGF0aHEAfgAEeHBzcQB+ABkBcHQAGk9TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNldABNb3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5leGNlcHRpb25oYW5kbGluZy5EZWZhdWx0TWVzc2FnZUV4Y2VwdGlvbkhhbmRsZXJzcQB+AB///////////3NxAH4AHAAAAANzcQB+ABkAcQB+ADJwc3IADGphdmEubmV0LlVSTJYlNzYa/ORyAwAHSQAIaGFzaENvZGVJAARwb3J0TAAJYXV0aG9yaXR5cQB+AARMAARmaWxlcQB+AARMAARob3N0cQB+AARMAAhwcm90b2NvbHEAfgAETAADcmVmcQB+AAR4cGkboqAAAB+QdAAObG9jYWxob3N0OjgwODB0ACsva3ItZGV2L3JlbW90aW5nL09TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNldAAJbG9jYWxob3N0dAAEaHR0cHB4dAAAdAAGVFJBVkVMdAABL3NyABNqYXZhLnV0aWwuQXJyYXlMaXN0eIHSHZnHYZ0DAAFJAARzaXpleHAAAAAFdwQAAAAKdAAzb3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5zZXJ2aWNlLktTQkphdmFTZXJ2aWNldAA8Y29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLmJhc2UuZXZlbnRzLkNhY2hlRW50cnlFdmVudExpc3RlbmVydAA3Y29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLmJhc2UuZXZlbnRzLkNhY2hlRXZlbnRMaXN0ZW5lcnQAF2phdmEudXRpbC5FdmVudExpc3RlbmVydAAsY29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLmJhc2UuTGlmZWN5Y2xlQXdhcmV4cQB+ACN0AAZUUkFWRUw=';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
INSERT INTO KRSB_MSG_PYLD_T (MSG_PYLD,MSG_QUE_ID)
  VALUES (EMPTY_CLOB(),397)
/
-- Length: 6112
-- Chunks: 2
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 397    FOR UPDATE;        
    buffer := 'rO0ABXNyAC1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLkFzeW5jaHJvbm91c0NhbGzxnQ5Y0t0lZQIAB1oAFWlnbm9yZVN0b3JlQW5kRm9yd2FyZFsACWFyZ3VtZW50c3QAE1tMamF2YS9sYW5nL09iamVjdDtMAAhjYWxsYmFja3QAM0xvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL0FzeW5jaHJvbm91c0NhbGxiYWNrO0wAB2NvbnRleHR0ABZMamF2YS9pby9TZXJpYWxpemFibGU7TAAKbWV0aG9kTmFtZXQAEkxqYXZhL2xhbmcvU3RyaW5nO1sACnBhcmFtVHlwZXN0ABJbTGphdmEvbGFuZy9DbGFzcztMAAtzZXJ2aWNlSW5mb3QAKkxvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL1NlcnZpY2VJbmZvO3hwAHVyABNbTGphdmEubGFuZy5PYmplY3Q7kM5YnxBzKWwCAAB4cAAAAAFzcgBDY29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLnBsdWdpbnMuY2x1c3RlcnN1cHBvcnQuQ2x1c3Rlck5vdGlmaWNhdGlvbhY+6VckvG9pAgACSQAEdHlwZUwABGRhdGFxAH4AA3hwAAAAAnQANkRvY3VtZW50VHlwZVJ1bGVDYWNoZTpJZGVudGl0eU1hbmFnZW1lbnRQZXJzb25Eb2N1bWVudHBwdAAGaW52b2tldXIAEltMamF2YS5sYW5nLkNsYXNzO6sW167LzVqZAgAAeHAAAAABdnIAFGphdmEuaW8uU2VyaWFsaXphYmxlEJthDdebZO0CAAB4cHNyAChvcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLlNlcnZpY2VJbmZvxRcjtikEt7oCAAtMAAVhbGl2ZXQAE0xqYXZhL2xhbmcvQm9vbGVhbjtMABRlbmRwb2ludEFsdGVybmF0ZVVybHEAfgAETAALZW5kcG9pbnRVcmxxAH4ABEwACmxvY2tWZXJOYnJ0ABNMamF2YS9sYW5nL0ludGVnZXI7TAAObWVzc2FnZUVudHJ5SWR0ABBMamF2YS9sYW5nL0xvbmc7TAAFcW5hbWV0ABtMamF2YXgveG1sL25hbWVzcGFjZS9RTmFtZTtMABpzZXJpYWxpemVkU2VydmljZU5hbWVzcGFjZXEAfgAETAAIc2VydmVySXBxAH4ABEwAEXNlcnZpY2VEZWZpbml0aW9udAAwTG9yZy9rdWFsaS9yaWNlL2tzYi9tZXNzYWdpbmcvU2VydmljZURlZmluaXRpb247TAALc2VydmljZU5hbWVxAH4ABEwAEHNlcnZpY2VOYW1lc3BhY2VxAH4ABHhwc3IAEWphdmEubGFuZy5Cb29sZWFuzSBygNWc+u4CAAFaAAV2YWx1ZXhwAXB0AEBodHRwOi8vbG9jYWxob3N0OjgwODAva3ItZGV2L3JlbW90aW5nL09TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNlc3IAEWphdmEubGFuZy5JbnRlZ2VyEuKgpPeBhzgCAAFJAAV2YWx1ZXhyABBqYXZhLmxhbmcuTnVtYmVyhqyVHQuU4IsCAAB4cAAAAAFzcgAOamF2YS5sYW5nLkxvbmc7i+SQzI8j3wIAAUoABXZhbHVleHEAfgAdAAAAAAAAD31zcgAZamF2YXgueG1sLm5hbWVzcGFjZS5RTmFtZYFtqC38O91sAgADTAAJbG9jYWxQYXJ0cQB+AARMAAxuYW1lc3BhY2VVUklxAH4ABEwABnByZWZpeHEAfgAEeHB0ABpPU0NhY2hlTm90aWZpY2F0aW9uU2VydmljZXQAAHEAfgAkdAd0ck8wQUJYTnlBREp2Y21jdWEzVmhiR2t1Y21salpTNXJjMkl1YldWemMyRm5hVzVuTGtwaGRtRlRaWEoyYVdObFJHVm1hVzVwZEdsdmJyYm5EVjlCTkd6eEFnQUJUQUFSYzJWeWRtbGpaVWx1ZEdWeVptRmpaWE4wQUJCTWFtRjJZUzkxZEdsc0wweHBjM1E3ZUhJQUxtOXlaeTVyZFdGc2FTNXlhV05sTG10ellpNXRaWE56WVdkcGJtY3VVMlZ5ZG1salpVUmxabWx1YVhScGIyNEFtd0pQV04wcGZnSUFEVXdBQzJKMWMxTmxZM1Z5YVhSNWRBQVRUR3BoZG1FdmJHRnVaeTlDYjI5c1pXRnVPMHdBRDJOeVpXUmxiblJwWVd4elZIbHdaWFFBVEV4dmNtY3ZhM1ZoYkdrdmNtbGpaUzlqYjNKbEwzTmxZM1Z5YVhSNUwyTnlaV1JsYm5ScFlXeHpMME55WldSbGJuUnBZV3h6VTI5MWNtTmxKRU55WldSbGJuUnBZV3h6Vkhsd1pUdE1BQkJzYjJOaGJGTmxjblpwWTJWT1lXMWxkQUFTVEdwaGRtRXZiR0Z1Wnk5VGRISnBibWM3VEFBWGJXVnpjMkZuWlVWNFkyVndkR2x2YmtoaGJtUnNaWEp4QUg0QUJVd0FERzFwYkd4cGMxUnZUR2wyWlhRQUVFeHFZWFpoTDJ4aGJtY3ZURzl1Wnp0TUFBaHdjbWx2Y21sMGVYUUFFMHhxWVhaaEwyeGhibWN2U1c1MFpXZGxjanRNQUFWeGRXVjFaWEVBZmdBRFRBQU5jbVYwY25sQmRIUmxiWEIwYzNFQWZnQUhUQUFIYzJWeWRtbGpaWFFBRWt4cVlYWmhMMnhoYm1jdlQySnFaV04wTzB3QUQzTmxjblpwWTJWRmJtUlFiMmx1ZEhRQURreHFZWFpoTDI1bGRDOVZVa3c3VEFBVGMyVnlkbWxqWlU1aGJXVlRjR0ZqWlZWU1NYRUFmZ0FGVEFBUWMyVnlkbWxqWlU1aGJXVnpjR0ZqWlhFQWZnQUZUQUFMYzJWeWRtbGpaVkJoZEdoeEFINEFCWGh3YzNJQUVXcGhkbUV1YkdGdVp5NUNiMjlzWldGdXpTQnlnTldjK3U0Q0FBRmFBQVYyWVd4MVpYaHdBWEIwQUJwUFUwTmhZMmhsVG05MGFXWnBZMkYwYVc5dVUyVnlkbWxqWlhRQVRXOXlaeTVyZFdGc2FTNXlhV05sTG10ellpNXRaWE56WVdkcGJtY3VaWGhqWlhCMGFXOXVhR0Z1Wkd4cGJtY3VSR1ZtWVhWc2RFMWxjM05oWjJWRmVHTmxjSFJwYjI1SVlXNWtiR1Z5YzNJQURtcGhkbUV1YkdGdVp5NU1iMjVuTzR2a2tNeVBJOThDQUFGS0FBVjJZV3gxWlhoeUFCQnFZWFpoTG14aGJtY3VUblZ0WW1WeWhxeVZIUXVVNElzQ0FBQjRjUC8vLy8vLy8vLy9jM0lBRVdwaGRtRXViR0Z1Wnk1SmJuUmxaMlZ5RXVLZ3BQZUJoemdDQUFGSkFBVjJZV3gxWlhoeEFINEFFQUFBQUFOemNRQitBQXNBY1FCK0FCTndjM0lBREdwaGRtRXVibVYwTGxWU1RKWWxOellhL09SeUF3QUhTUUFJYUdGemFFTnZaR1ZKQUFSd2IzSjBUQUFKWVhWMGFHOXlhWFI1Y1FCK0FBVk1BQVJtYVd4bGNRQitBQVZNQUFSb2IzTjBjUUIrQUFWTUFBaHdjbTkwYjJOdmJIRUFmZ0FGVEFBRGNtVm1jUUIrQUFWNGNQLy8vLzhBQUIrUWRBQU9iRzlqWVd4b2IzTjBPamd3T0RCMEFDc3ZhM0l0WkdWMkwzSmxiVzkwYVc1bkwwOVRRMkZqYUdWT2IzUnBabWxqWVhScGIyNVRaWEoyYVdObGRBQUpiRzlqWVd4b2IzTjBkQUFFYUhSMGNIQjRkQUFBZEFBR1ZGSkJWa1ZNZEFBQkwzTnlBQk5xWVhaaExuVjBhV3d1UVhKeVlYbE1hWE4wZUlIU0habkhZWjBEQUFGSkFBUnphWHBsZUhBQUFBQUZkd1FBQUFBS2RB';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 397    FOR UPDATE;        
    buffer := 'QXpiM0puTG10MVlXeHBMbkpwWTJVdWEzTmlMbTFsYzNOaFoybHVaeTV6WlhKMmFXTmxMa3RUUWtwaGRtRlRaWEoyYVdObGRBQThZMjl0TG05d1pXNXplVzF3YUc5dWVTNXZjMk5oWTJobExtSmhjMlV1WlhabGJuUnpMa05oWTJobFJXNTBjbmxGZG1WdWRFeHBjM1JsYm1WeWRBQTNZMjl0TG05d1pXNXplVzF3YUc5dWVTNXZjMk5oWTJobExtSmhjMlV1WlhabGJuUnpMa05oWTJobFJYWmxiblJNYVhOMFpXNWxjblFBRjJwaGRtRXVkWFJwYkM1RmRtVnVkRXhwYzNSbGJtVnlkQUFzWTI5dExtOXdaVzV6ZVcxd2FHOXVlUzV2YzJOaFkyaGxMbUpoYzJVdVRHbG1aV041WTJ4bFFYZGhjbVY0dAANMTI5LjE4Ni43OS40NXNyADJvcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLkphdmFTZXJ2aWNlRGVmaW5pdGlvbrbnDV9BNGzxAgABTAARc2VydmljZUludGVyZmFjZXN0ABBMamF2YS91dGlsL0xpc3Q7eHIALm9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuU2VydmljZURlZmluaXRpb24AmwJPWN0pfgIADUwAC2J1c1NlY3VyaXR5cQB+ABNMAA9jcmVkZW50aWFsc1R5cGV0AExMb3JnL2t1YWxpL3JpY2UvY29yZS9zZWN1cml0eS9jcmVkZW50aWFscy9DcmVkZW50aWFsc1NvdXJjZSRDcmVkZW50aWFsc1R5cGU7TAAQbG9jYWxTZXJ2aWNlTmFtZXEAfgAETAAXbWVzc2FnZUV4Y2VwdGlvbkhhbmRsZXJxAH4ABEwADG1pbGxpc1RvTGl2ZXEAfgAVTAAIcHJpb3JpdHlxAH4AFEwABXF1ZXVlcQB+ABNMAA1yZXRyeUF0dGVtcHRzcQB+ABRMAAdzZXJ2aWNldAASTGphdmEvbGFuZy9PYmplY3Q7TAAPc2VydmljZUVuZFBvaW50dAAOTGphdmEvbmV0L1VSTDtMABNzZXJ2aWNlTmFtZVNwYWNlVVJJcQB+AARMABBzZXJ2aWNlTmFtZXNwYWNlcQB+AARMAAtzZXJ2aWNlUGF0aHEAfgAEeHBzcQB+ABkBcHQAGk9TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNldABNb3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5leGNlcHRpb25oYW5kbGluZy5EZWZhdWx0TWVzc2FnZUV4Y2VwdGlvbkhhbmRsZXJzcQB+AB///////////3NxAH4AHAAAAANzcQB+ABkAcQB+ADJwc3IADGphdmEubmV0LlVSTJYlNzYa/ORyAwAHSQAIaGFzaENvZGVJAARwb3J0TAAJYXV0aG9yaXR5cQB+AARMAARmaWxlcQB+AARMAARob3N0cQB+AARMAAhwcm90b2NvbHEAfgAETAADcmVmcQB+AAR4cGkboqAAAB+QdAAObG9jYWxob3N0OjgwODB0ACsva3ItZGV2L3JlbW90aW5nL09TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNldAAJbG9jYWxob3N0dAAEaHR0cHB4dAAAdAAGVFJBVkVMdAABL3NyABNqYXZhLnV0aWwuQXJyYXlMaXN0eIHSHZnHYZ0DAAFJAARzaXpleHAAAAAFdwQAAAAKdAAzb3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5zZXJ2aWNlLktTQkphdmFTZXJ2aWNldAA8Y29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLmJhc2UuZXZlbnRzLkNhY2hlRW50cnlFdmVudExpc3RlbmVydAA3Y29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLmJhc2UuZXZlbnRzLkNhY2hlRXZlbnRMaXN0ZW5lcnQAF2phdmEudXRpbC5FdmVudExpc3RlbmVydAAsY29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLmJhc2UuTGlmZWN5Y2xlQXdhcmV4cQB+ACN0AAZUUkFWRUw=';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
INSERT INTO KRSB_MSG_PYLD_T (MSG_PYLD,MSG_QUE_ID)
  VALUES (EMPTY_CLOB(),398)
/
-- Length: 6060
-- Chunks: 2
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 398    FOR UPDATE;        
    buffer := 'rO0ABXNyAC1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLkFzeW5jaHJvbm91c0NhbGzxnQ5Y0t0lZQIAB1oAFWlnbm9yZVN0b3JlQW5kRm9yd2FyZFsACWFyZ3VtZW50c3QAE1tMamF2YS9sYW5nL09iamVjdDtMAAhjYWxsYmFja3QAM0xvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL0FzeW5jaHJvbm91c0NhbGxiYWNrO0wAB2NvbnRleHR0ABZMamF2YS9pby9TZXJpYWxpemFibGU7TAAKbWV0aG9kTmFtZXQAEkxqYXZhL2xhbmcvU3RyaW5nO1sACnBhcmFtVHlwZXN0ABJbTGphdmEvbGFuZy9DbGFzcztMAAtzZXJ2aWNlSW5mb3QAKkxvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL1NlcnZpY2VJbmZvO3hwAHVyABNbTGphdmEubGFuZy5PYmplY3Q7kM5YnxBzKWwCAAB4cAAAAAFzcgBDY29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLnBsdWdpbnMuY2x1c3RlcnN1cHBvcnQuQ2x1c3Rlck5vdGlmaWNhdGlvbhY+6VckvG9pAgACSQAEdHlwZUwABGRhdGFxAH4AA3hwAAAAAnQADkRvY3VtZW50VHlwZUlkcHB0AAZpbnZva2V1cgASW0xqYXZhLmxhbmcuQ2xhc3M7qxbXrsvNWpkCAAB4cAAAAAF2cgAUamF2YS5pby5TZXJpYWxpemFibGUQm2EN15tk7QIAAHhwc3IAKG9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuU2VydmljZUluZm/FFyO2KQS3ugIAC0wABWFsaXZldAATTGphdmEvbGFuZy9Cb29sZWFuO0wAFGVuZHBvaW50QWx0ZXJuYXRlVXJscQB+AARMAAtlbmRwb2ludFVybHEAfgAETAAKbG9ja1Zlck5icnQAE0xqYXZhL2xhbmcvSW50ZWdlcjtMAA5tZXNzYWdlRW50cnlJZHQAEExqYXZhL2xhbmcvTG9uZztMAAVxbmFtZXQAG0xqYXZheC94bWwvbmFtZXNwYWNlL1FOYW1lO0wAGnNlcmlhbGl6ZWRTZXJ2aWNlTmFtZXNwYWNlcQB+AARMAAhzZXJ2ZXJJcHEAfgAETAARc2VydmljZURlZmluaXRpb250ADBMb3JnL2t1YWxpL3JpY2Uva3NiL21lc3NhZ2luZy9TZXJ2aWNlRGVmaW5pdGlvbjtMAAtzZXJ2aWNlTmFtZXEAfgAETAAQc2VydmljZU5hbWVzcGFjZXEAfgAEeHBzcgARamF2YS5sYW5nLkJvb2xlYW7NIHKA1Zz67gIAAVoABXZhbHVleHABcHQAQGh0dHA6Ly9sb2NhbGhvc3Q6ODA4MC9rci1kZXYvcmVtb3RpbmcvT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2VzcgARamF2YS5sYW5nLkludGVnZXIS4qCk94GHOAIAAUkABXZhbHVleHIAEGphdmEubGFuZy5OdW1iZXKGrJUdC5TgiwIAAHhwAAAAAXNyAA5qYXZhLmxhbmcuTG9uZzuL5JDMjyPfAgABSgAFdmFsdWV4cQB+AB0AAAAAAAAPfXNyABlqYXZheC54bWwubmFtZXNwYWNlLlFOYW1lgW2oLfw73WwCAANMAAlsb2NhbFBhcnRxAH4ABEwADG5hbWVzcGFjZVVSSXEAfgAETAAGcHJlZml4cQB+AAR4cHQAGk9TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNldAAAcQB+ACR0B3RyTzBBQlhOeUFESnZjbWN1YTNWaGJHa3VjbWxqWlM1cmMySXViV1Z6YzJGbmFXNW5Ma3BoZG1GVFpYSjJhV05sUkdWbWFXNXBkR2x2YnJibkRWOUJOR3p4QWdBQlRBQVJjMlZ5ZG1salpVbHVkR1Z5Wm1GalpYTjBBQkJNYW1GMllTOTFkR2xzTDB4cGMzUTdlSElBTG05eVp5NXJkV0ZzYVM1eWFXTmxMbXR6WWk1dFpYTnpZV2RwYm1jdVUyVnlkbWxqWlVSbFptbHVhWFJwYjI0QW13SlBXTjBwZmdJQURVd0FDMkoxYzFObFkzVnlhWFI1ZEFBVFRHcGhkbUV2YkdGdVp5OUNiMjlzWldGdU8wd0FEMk55WldSbGJuUnBZV3h6Vkhsd1pYUUFURXh2Y21jdmEzVmhiR2t2Y21salpTOWpiM0psTDNObFkzVnlhWFI1TDJOeVpXUmxiblJwWVd4ekwwTnlaV1JsYm5ScFlXeHpVMjkxY21ObEpFTnlaV1JsYm5ScFlXeHpWSGx3WlR0TUFCQnNiMk5oYkZObGNuWnBZMlZPWVcxbGRBQVNUR3BoZG1FdmJHRnVaeTlUZEhKcGJtYzdUQUFYYldWemMyRm5aVVY0WTJWd2RHbHZia2hoYm1Sc1pYSnhBSDRBQlV3QURHMXBiR3hwYzFSdlRHbDJaWFFBRUV4cVlYWmhMMnhoYm1jdlRHOXVaenRNQUFod2NtbHZjbWwwZVhRQUUweHFZWFpoTDJ4aGJtY3ZTVzUwWldkbGNqdE1BQVZ4ZFdWMVpYRUFmZ0FEVEFBTmNtVjBjbmxCZEhSbGJYQjBjM0VBZmdBSFRBQUhjMlZ5ZG1salpYUUFFa3hxWVhaaEwyeGhibWN2VDJKcVpXTjBPMHdBRDNObGNuWnBZMlZGYm1SUWIybHVkSFFBRGt4cVlYWmhMMjVsZEM5VlVrdzdUQUFUYzJWeWRtbGpaVTVoYldWVGNHRmpaVlZTU1hFQWZnQUZUQUFRYzJWeWRtbGpaVTVoYldWemNHRmpaWEVBZmdBRlRBQUxjMlZ5ZG1salpWQmhkR2h4QUg0QUJYaHdjM0lBRVdwaGRtRXViR0Z1Wnk1Q2IyOXNaV0Z1elNCeWdOV2MrdTRDQUFGYUFBVjJZV3gxWlhod0FYQjBBQnBQVTBOaFkyaGxUbTkwYVdacFkyRjBhVzl1VTJWeWRtbGpaWFFBVFc5eVp5NXJkV0ZzYVM1eWFXTmxMbXR6WWk1dFpYTnpZV2RwYm1jdVpYaGpaWEIwYVc5dWFHRnVaR3hwYm1jdVJHVm1ZWFZzZEUxbGMzTmhaMlZGZUdObGNIUnBiMjVJWVc1a2JHVnljM0lBRG1waGRtRXViR0Z1Wnk1TWIyNW5PNHZra015UEk5OENBQUZLQUFWMllXeDFaWGh5QUJCcVlYWmhMbXhoYm1jdVRuVnRZbVZ5aHF5VkhRdVU0SXNDQUFCNGNQLy8vLy8vLy8vL2MzSUFFV3BoZG1FdWJHRnVaeTVKYm5SbFoyVnlFdUtncFBlQmh6Z0NBQUZKQUFWMllXeDFaWGh4QUg0QUVBQUFBQU56Y1FCK0FBc0FjUUIrQUJOd2MzSUFER3BoZG1FdWJtVjBMbFZTVEpZbE56WWEvT1J5QXdBSFNRQUlhR0Z6YUVOdlpHVkpBQVJ3YjNKMFRBQUpZWFYwYUc5eWFYUjVjUUIrQUFWTUFBUm1hV3hsY1FCK0FBVk1BQVJvYjNOMGNRQitBQVZNQUFod2NtOTBiMk52YkhFQWZnQUZUQUFEY21WbWNRQitBQVY0Y1AvLy8vOEFBQitRZEFBT2JHOWpZV3hvYjNOME9qZ3dPREIwQUNzdmEzSXRaR1YyTDNKbGJXOTBhVzVuTDA5VFEyRmphR1ZPYjNScFptbGpZWFJwYjI1VFpYSjJhV05sZEFBSmJHOWpZV3hvYjNOMGRBQUVhSFIwY0hCNGRBQUFkQUFHVkZKQlZrVk1kQUFCTDNOeUFCTnFZWFpoTG5WMGFXd3VRWEp5WVhsTWFYTjBlSUhTSFpuSFlaMERBQUZKQUFSemFYcGxlSEFBQUFBRmR3UUFBQUFLZEFBemIzSm5MbXQxWVd4cExuSnBZMlV1YTNOaUxtMWxjM05oWjJsdVp5';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 398    FOR UPDATE;        
    buffer := 'NXpaWEoyYVdObExrdFRRa3BoZG1GVFpYSjJhV05sZEFBOFkyOXRMbTl3Wlc1emVXMXdhRzl1ZVM1dmMyTmhZMmhsTG1KaGMyVXVaWFpsYm5SekxrTmhZMmhsUlc1MGNubEZkbVZ1ZEV4cGMzUmxibVZ5ZEFBM1kyOXRMbTl3Wlc1emVXMXdhRzl1ZVM1dmMyTmhZMmhsTG1KaGMyVXVaWFpsYm5SekxrTmhZMmhsUlhabGJuUk1hWE4wWlc1bGNuUUFGMnBoZG1FdWRYUnBiQzVGZG1WdWRFeHBjM1JsYm1WeWRBQXNZMjl0TG05d1pXNXplVzF3YUc5dWVTNXZjMk5oWTJobExtSmhjMlV1VEdsbVpXTjVZMnhsUVhkaGNtVjR0AA0xMjkuMTg2Ljc5LjQ1c3IAMm9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuSmF2YVNlcnZpY2VEZWZpbml0aW9utucNX0E0bPECAAFMABFzZXJ2aWNlSW50ZXJmYWNlc3QAEExqYXZhL3V0aWwvTGlzdDt4cgAub3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5TZXJ2aWNlRGVmaW5pdGlvbgCbAk9Y3Sl+AgANTAALYnVzU2VjdXJpdHlxAH4AE0wAD2NyZWRlbnRpYWxzVHlwZXQATExvcmcva3VhbGkvcmljZS9jb3JlL3NlY3VyaXR5L2NyZWRlbnRpYWxzL0NyZWRlbnRpYWxzU291cmNlJENyZWRlbnRpYWxzVHlwZTtMABBsb2NhbFNlcnZpY2VOYW1lcQB+AARMABdtZXNzYWdlRXhjZXB0aW9uSGFuZGxlcnEAfgAETAAMbWlsbGlzVG9MaXZlcQB+ABVMAAhwcmlvcml0eXEAfgAUTAAFcXVldWVxAH4AE0wADXJldHJ5QXR0ZW1wdHNxAH4AFEwAB3NlcnZpY2V0ABJMamF2YS9sYW5nL09iamVjdDtMAA9zZXJ2aWNlRW5kUG9pbnR0AA5MamF2YS9uZXQvVVJMO0wAE3NlcnZpY2VOYW1lU3BhY2VVUklxAH4ABEwAEHNlcnZpY2VOYW1lc3BhY2VxAH4ABEwAC3NlcnZpY2VQYXRocQB+AAR4cHNxAH4AGQFwdAAaT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2V0AE1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLmV4Y2VwdGlvbmhhbmRsaW5nLkRlZmF1bHRNZXNzYWdlRXhjZXB0aW9uSGFuZGxlcnNxAH4AH///////////c3EAfgAcAAAAA3NxAH4AGQBxAH4AMnBzcgAMamF2YS5uZXQuVVJMliU3Nhr85HIDAAdJAAhoYXNoQ29kZUkABHBvcnRMAAlhdXRob3JpdHlxAH4ABEwABGZpbGVxAH4ABEwABGhvc3RxAH4ABEwACHByb3RvY29scQB+AARMAANyZWZxAH4ABHhwaRuioAAAH5B0AA5sb2NhbGhvc3Q6ODA4MHQAKy9rci1kZXYvcmVtb3RpbmcvT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2V0AAlsb2NhbGhvc3R0AARodHRwcHh0AAB0AAZUUkFWRUx0AAEvc3IAE2phdmEudXRpbC5BcnJheUxpc3R4gdIdmcdhnQMAAUkABHNpemV4cAAAAAV3BAAAAAp0ADNvcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLnNlcnZpY2UuS1NCSmF2YVNlcnZpY2V0ADxjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5ldmVudHMuQ2FjaGVFbnRyeUV2ZW50TGlzdGVuZXJ0ADdjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5ldmVudHMuQ2FjaGVFdmVudExpc3RlbmVydAAXamF2YS51dGlsLkV2ZW50TGlzdGVuZXJ0ACxjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5MaWZlY3ljbGVBd2FyZXhxAH4AI3QABlRSQVZFTA==';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
INSERT INTO KRSB_MSG_PYLD_T (MSG_PYLD,MSG_QUE_ID)
  VALUES (EMPTY_CLOB(),399)
/
-- Length: 6060
-- Chunks: 2
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 399    FOR UPDATE;        
    buffer := 'rO0ABXNyAC1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLkFzeW5jaHJvbm91c0NhbGzxnQ5Y0t0lZQIAB1oAFWlnbm9yZVN0b3JlQW5kRm9yd2FyZFsACWFyZ3VtZW50c3QAE1tMamF2YS9sYW5nL09iamVjdDtMAAhjYWxsYmFja3QAM0xvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL0FzeW5jaHJvbm91c0NhbGxiYWNrO0wAB2NvbnRleHR0ABZMamF2YS9pby9TZXJpYWxpemFibGU7TAAKbWV0aG9kTmFtZXQAEkxqYXZhL2xhbmcvU3RyaW5nO1sACnBhcmFtVHlwZXN0ABJbTGphdmEvbGFuZy9DbGFzcztMAAtzZXJ2aWNlSW5mb3QAKkxvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL1NlcnZpY2VJbmZvO3hwAHVyABNbTGphdmEubGFuZy5PYmplY3Q7kM5YnxBzKWwCAAB4cAAAAAFzcgBDY29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLnBsdWdpbnMuY2x1c3RlcnN1cHBvcnQuQ2x1c3Rlck5vdGlmaWNhdGlvbhY+6VckvG9pAgACSQAEdHlwZUwABGRhdGFxAH4AA3hwAAAAAnQAEERvY3VtZW50VHlwZU5hbWVwcHQABmludm9rZXVyABJbTGphdmEubGFuZy5DbGFzczurFteuy81amQIAAHhwAAAAAXZyABRqYXZhLmlvLlNlcmlhbGl6YWJsZRCbYQ3Xm2TtAgAAeHBzcgAob3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5TZXJ2aWNlSW5mb8UXI7YpBLe6AgALTAAFYWxpdmV0ABNMamF2YS9sYW5nL0Jvb2xlYW47TAAUZW5kcG9pbnRBbHRlcm5hdGVVcmxxAH4ABEwAC2VuZHBvaW50VXJscQB+AARMAApsb2NrVmVyTmJydAATTGphdmEvbGFuZy9JbnRlZ2VyO0wADm1lc3NhZ2VFbnRyeUlkdAAQTGphdmEvbGFuZy9Mb25nO0wABXFuYW1ldAAbTGphdmF4L3htbC9uYW1lc3BhY2UvUU5hbWU7TAAac2VyaWFsaXplZFNlcnZpY2VOYW1lc3BhY2VxAH4ABEwACHNlcnZlcklwcQB+AARMABFzZXJ2aWNlRGVmaW5pdGlvbnQAMExvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL1NlcnZpY2VEZWZpbml0aW9uO0wAC3NlcnZpY2VOYW1lcQB+AARMABBzZXJ2aWNlTmFtZXNwYWNlcQB+AAR4cHNyABFqYXZhLmxhbmcuQm9vbGVhbs0gcoDVnPruAgABWgAFdmFsdWV4cAFwdABAaHR0cDovL2xvY2FsaG9zdDo4MDgwL2tyLWRldi9yZW1vdGluZy9PU0NhY2hlTm90aWZpY2F0aW9uU2VydmljZXNyABFqYXZhLmxhbmcuSW50ZWdlchLioKT3gYc4AgABSQAFdmFsdWV4cgAQamF2YS5sYW5nLk51bWJlcoaslR0LlOCLAgAAeHAAAAABc3IADmphdmEubGFuZy5Mb25nO4vkkMyPI98CAAFKAAV2YWx1ZXhxAH4AHQAAAAAAAA99c3IAGWphdmF4LnhtbC5uYW1lc3BhY2UuUU5hbWWBbagt/DvdbAIAA0wACWxvY2FsUGFydHEAfgAETAAMbmFtZXNwYWNlVVJJcQB+AARMAAZwcmVmaXhxAH4ABHhwdAAaT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2V0AABxAH4AJHQHdHJPMEFCWE55QURKdmNtY3VhM1ZoYkdrdWNtbGpaUzVyYzJJdWJXVnpjMkZuYVc1bkxrcGhkbUZUWlhKMmFXTmxSR1ZtYVc1cGRHbHZicmJuRFY5Qk5HenhBZ0FCVEFBUmMyVnlkbWxqWlVsdWRHVnlabUZqWlhOMEFCQk1hbUYyWVM5MWRHbHNMMHhwYzNRN2VISUFMbTl5Wnk1cmRXRnNhUzV5YVdObExtdHpZaTV0WlhOellXZHBibWN1VTJWeWRtbGpaVVJsWm1sdWFYUnBiMjRBbXdKUFdOMHBmZ0lBRFV3QUMySjFjMU5sWTNWeWFYUjVkQUFUVEdwaGRtRXZiR0Z1Wnk5Q2IyOXNaV0Z1TzB3QUQyTnlaV1JsYm5ScFlXeHpWSGx3WlhRQVRFeHZjbWN2YTNWaGJHa3ZjbWxqWlM5amIzSmxMM05sWTNWeWFYUjVMMk55WldSbGJuUnBZV3h6TDBOeVpXUmxiblJwWVd4elUyOTFjbU5sSkVOeVpXUmxiblJwWVd4elZIbHdaVHRNQUJCc2IyTmhiRk5sY25acFkyVk9ZVzFsZEFBU1RHcGhkbUV2YkdGdVp5OVRkSEpwYm1jN1RBQVhiV1Z6YzJGblpVVjRZMlZ3ZEdsdmJraGhibVJzWlhKeEFINEFCVXdBREcxcGJHeHBjMVJ2VEdsMlpYUUFFRXhxWVhaaEwyeGhibWN2VEc5dVp6dE1BQWh3Y21sdmNtbDBlWFFBRTB4cVlYWmhMMnhoYm1jdlNXNTBaV2RsY2p0TUFBVnhkV1YxWlhFQWZnQURUQUFOY21WMGNubEJkSFJsYlhCMGMzRUFmZ0FIVEFBSGMyVnlkbWxqWlhRQUVreHFZWFpoTDJ4aGJtY3ZUMkpxWldOME8wd0FEM05sY25acFkyVkZibVJRYjJsdWRIUUFEa3hxWVhaaEwyNWxkQzlWVWt3N1RBQVRjMlZ5ZG1salpVNWhiV1ZUY0dGalpWVlNTWEVBZmdBRlRBQVFjMlZ5ZG1salpVNWhiV1Z6Y0dGalpYRUFmZ0FGVEFBTGMyVnlkbWxqWlZCaGRHaHhBSDRBQlhod2MzSUFFV3BoZG1FdWJHRnVaeTVDYjI5c1pXRnV6U0J5Z05XYyt1NENBQUZhQUFWMllXeDFaWGh3QVhCMEFCcFBVME5oWTJobFRtOTBhV1pwWTJGMGFXOXVVMlZ5ZG1salpYUUFUVzl5Wnk1cmRXRnNhUzV5YVdObExtdHpZaTV0WlhOellXZHBibWN1WlhoalpYQjBhVzl1YUdGdVpHeHBibWN1UkdWbVlYVnNkRTFsYzNOaFoyVkZlR05sY0hScGIyNUlZVzVrYkdWeWMzSUFEbXBoZG1FdWJHRnVaeTVNYjI1bk80dmtrTXlQSTk4Q0FBRktBQVYyWVd4MVpYaHlBQkJxWVhaaExteGhibWN1VG5WdFltVnlocXlWSFF1VTRJc0NBQUI0Y1AvLy8vLy8vLy8vYzNJQUVXcGhkbUV1YkdGdVp5NUpiblJsWjJWeUV1S2dwUGVCaHpnQ0FBRkpBQVYyWVd4MVpYaHhBSDRBRUFBQUFBTnpjUUIrQUFzQWNRQitBQk53YzNJQURHcGhkbUV1Ym1WMExsVlNUSllsTnpZYS9PUnlBd0FIU1FBSWFHRnphRU52WkdWSkFBUndiM0owVEFBSllYVjBhRzl5YVhSNWNRQitBQVZNQUFSbWFXeGxjUUIrQUFWTUFBUm9iM04wY1FCK0FBVk1BQWh3Y205MGIyTnZiSEVBZmdBRlRBQURjbVZtY1FCK0FBVjRjUC8vLy84QUFCK1FkQUFPYkc5allXeG9iM04wT2pnd09EQjBBQ3N2YTNJdFpHVjJMM0psYlc5MGFXNW5MMDlUUTJGamFHVk9iM1JwWm1sallYUnBiMjVUWlhKMmFXTmxkQUFKYkc5allXeG9iM04wZEFBRWFIUjBjSEI0ZEFBQWRBQUdWRkpCVmtWTWRBQUJMM055QUJOcVlYWmhMblYwYVd3dVFYSnlZWGxNYVhOMGVJSFNIWm5IWVowREFBRkpBQVJ6YVhwbGVIQUFBQUFGZHdRQUFBQUtkQUF6YjNKbkxtdDFZV3hwTG5KcFkyVXVhM05pTG0xbGMzTmhaMmx1';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 399    FOR UPDATE;        
    buffer := 'Wnk1elpYSjJhV05sTGt0VFFrcGhkbUZUWlhKMmFXTmxkQUE4WTI5dExtOXdaVzV6ZVcxd2FHOXVlUzV2YzJOaFkyaGxMbUpoYzJVdVpYWmxiblJ6TGtOaFkyaGxSVzUwY25sRmRtVnVkRXhwYzNSbGJtVnlkQUEzWTI5dExtOXdaVzV6ZVcxd2FHOXVlUzV2YzJOaFkyaGxMbUpoYzJVdVpYWmxiblJ6TGtOaFkyaGxSWFpsYm5STWFYTjBaVzVsY25RQUYycGhkbUV1ZFhScGJDNUZkbVZ1ZEV4cGMzUmxibVZ5ZEFBc1kyOXRMbTl3Wlc1emVXMXdhRzl1ZVM1dmMyTmhZMmhsTG1KaGMyVXVUR2xtWldONVkyeGxRWGRoY21WNHQADTEyOS4xODYuNzkuNDVzcgAyb3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5KYXZhU2VydmljZURlZmluaXRpb2625w1fQTRs8QIAAUwAEXNlcnZpY2VJbnRlcmZhY2VzdAAQTGphdmEvdXRpbC9MaXN0O3hyAC5vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLlNlcnZpY2VEZWZpbml0aW9uAJsCT1jdKX4CAA1MAAtidXNTZWN1cml0eXEAfgATTAAPY3JlZGVudGlhbHNUeXBldABMTG9yZy9rdWFsaS9yaWNlL2NvcmUvc2VjdXJpdHkvY3JlZGVudGlhbHMvQ3JlZGVudGlhbHNTb3VyY2UkQ3JlZGVudGlhbHNUeXBlO0wAEGxvY2FsU2VydmljZU5hbWVxAH4ABEwAF21lc3NhZ2VFeGNlcHRpb25IYW5kbGVycQB+AARMAAxtaWxsaXNUb0xpdmVxAH4AFUwACHByaW9yaXR5cQB+ABRMAAVxdWV1ZXEAfgATTAANcmV0cnlBdHRlbXB0c3EAfgAUTAAHc2VydmljZXQAEkxqYXZhL2xhbmcvT2JqZWN0O0wAD3NlcnZpY2VFbmRQb2ludHQADkxqYXZhL25ldC9VUkw7TAATc2VydmljZU5hbWVTcGFjZVVSSXEAfgAETAAQc2VydmljZU5hbWVzcGFjZXEAfgAETAALc2VydmljZVBhdGhxAH4ABHhwc3EAfgAZAXB0ABpPU0NhY2hlTm90aWZpY2F0aW9uU2VydmljZXQATW9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuZXhjZXB0aW9uaGFuZGxpbmcuRGVmYXVsdE1lc3NhZ2VFeGNlcHRpb25IYW5kbGVyc3EAfgAf//////////9zcQB+ABwAAAADc3EAfgAZAHEAfgAycHNyAAxqYXZhLm5ldC5VUkyWJTc2GvzkcgMAB0kACGhhc2hDb2RlSQAEcG9ydEwACWF1dGhvcml0eXEAfgAETAAEZmlsZXEAfgAETAAEaG9zdHEAfgAETAAIcHJvdG9jb2xxAH4ABEwAA3JlZnEAfgAEeHBpG6KgAAAfkHQADmxvY2FsaG9zdDo4MDgwdAArL2tyLWRldi9yZW1vdGluZy9PU0NhY2hlTm90aWZpY2F0aW9uU2VydmljZXQACWxvY2FsaG9zdHQABGh0dHBweHQAAHQABlRSQVZFTHQAAS9zcgATamF2YS51dGlsLkFycmF5TGlzdHiB0h2Zx2GdAwABSQAEc2l6ZXhwAAAABXcEAAAACnQAM29yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuc2VydmljZS5LU0JKYXZhU2VydmljZXQAPGNvbS5vcGVuc3ltcGhvbnkub3NjYWNoZS5iYXNlLmV2ZW50cy5DYWNoZUVudHJ5RXZlbnRMaXN0ZW5lcnQAN2NvbS5vcGVuc3ltcGhvbnkub3NjYWNoZS5iYXNlLmV2ZW50cy5DYWNoZUV2ZW50TGlzdGVuZXJ0ABdqYXZhLnV0aWwuRXZlbnRMaXN0ZW5lcnQALGNvbS5vcGVuc3ltcGhvbnkub3NjYWNoZS5iYXNlLkxpZmVjeWNsZUF3YXJleHEAfgAjdAAGVFJBVkVM';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
INSERT INTO KRSB_MSG_PYLD_T (MSG_PYLD,MSG_QUE_ID)
  VALUES (EMPTY_CLOB(),400)
/
-- Length: 6084
-- Chunks: 2
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 400    FOR UPDATE;        
    buffer := 'rO0ABXNyAC1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLkFzeW5jaHJvbm91c0NhbGzxnQ5Y0t0lZQIAB1oAFWlnbm9yZVN0b3JlQW5kRm9yd2FyZFsACWFyZ3VtZW50c3QAE1tMamF2YS9sYW5nL09iamVjdDtMAAhjYWxsYmFja3QAM0xvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL0FzeW5jaHJvbm91c0NhbGxiYWNrO0wAB2NvbnRleHR0ABZMamF2YS9pby9TZXJpYWxpemFibGU7TAAKbWV0aG9kTmFtZXQAEkxqYXZhL2xhbmcvU3RyaW5nO1sACnBhcmFtVHlwZXN0ABJbTGphdmEvbGFuZy9DbGFzcztMAAtzZXJ2aWNlSW5mb3QAKkxvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL1NlcnZpY2VJbmZvO3hwAHVyABNbTGphdmEubGFuZy5PYmplY3Q7kM5YnxBzKWwCAAB4cAAAAAFzcgBDY29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLnBsdWdpbnMuY2x1c3RlcnN1cHBvcnQuQ2x1c3Rlck5vdGlmaWNhdGlvbhY+6VckvG9pAgACSQAEdHlwZUwABGRhdGFxAH4AA3hwAAAAAXQAIERvY3VtZW50VHlwZTpDdXJyZW50Um9vdHNJbkNhY2hlcHB0AAZpbnZva2V1cgASW0xqYXZhLmxhbmcuQ2xhc3M7qxbXrsvNWpkCAAB4cAAAAAF2cgAUamF2YS5pby5TZXJpYWxpemFibGUQm2EN15tk7QIAAHhwc3IAKG9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuU2VydmljZUluZm/FFyO2KQS3ugIAC0wABWFsaXZldAATTGphdmEvbGFuZy9Cb29sZWFuO0wAFGVuZHBvaW50QWx0ZXJuYXRlVXJscQB+AARMAAtlbmRwb2ludFVybHEAfgAETAAKbG9ja1Zlck5icnQAE0xqYXZhL2xhbmcvSW50ZWdlcjtMAA5tZXNzYWdlRW50cnlJZHQAEExqYXZhL2xhbmcvTG9uZztMAAVxbmFtZXQAG0xqYXZheC94bWwvbmFtZXNwYWNlL1FOYW1lO0wAGnNlcmlhbGl6ZWRTZXJ2aWNlTmFtZXNwYWNlcQB+AARMAAhzZXJ2ZXJJcHEAfgAETAARc2VydmljZURlZmluaXRpb250ADBMb3JnL2t1YWxpL3JpY2Uva3NiL21lc3NhZ2luZy9TZXJ2aWNlRGVmaW5pdGlvbjtMAAtzZXJ2aWNlTmFtZXEAfgAETAAQc2VydmljZU5hbWVzcGFjZXEAfgAEeHBzcgARamF2YS5sYW5nLkJvb2xlYW7NIHKA1Zz67gIAAVoABXZhbHVleHABcHQAQGh0dHA6Ly9sb2NhbGhvc3Q6ODA4MC9rci1kZXYvcmVtb3RpbmcvT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2VzcgARamF2YS5sYW5nLkludGVnZXIS4qCk94GHOAIAAUkABXZhbHVleHIAEGphdmEubGFuZy5OdW1iZXKGrJUdC5TgiwIAAHhwAAAAAXNyAA5qYXZhLmxhbmcuTG9uZzuL5JDMjyPfAgABSgAFdmFsdWV4cQB+AB0AAAAAAAAPfXNyABlqYXZheC54bWwubmFtZXNwYWNlLlFOYW1lgW2oLfw73WwCAANMAAlsb2NhbFBhcnRxAH4ABEwADG5hbWVzcGFjZVVSSXEAfgAETAAGcHJlZml4cQB+AAR4cHQAGk9TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNldAAAcQB+ACR0B3RyTzBBQlhOeUFESnZjbWN1YTNWaGJHa3VjbWxqWlM1cmMySXViV1Z6YzJGbmFXNW5Ma3BoZG1GVFpYSjJhV05sUkdWbWFXNXBkR2x2YnJibkRWOUJOR3p4QWdBQlRBQVJjMlZ5ZG1salpVbHVkR1Z5Wm1GalpYTjBBQkJNYW1GMllTOTFkR2xzTDB4cGMzUTdlSElBTG05eVp5NXJkV0ZzYVM1eWFXTmxMbXR6WWk1dFpYTnpZV2RwYm1jdVUyVnlkbWxqWlVSbFptbHVhWFJwYjI0QW13SlBXTjBwZmdJQURVd0FDMkoxYzFObFkzVnlhWFI1ZEFBVFRHcGhkbUV2YkdGdVp5OUNiMjlzWldGdU8wd0FEMk55WldSbGJuUnBZV3h6Vkhsd1pYUUFURXh2Y21jdmEzVmhiR2t2Y21salpTOWpiM0psTDNObFkzVnlhWFI1TDJOeVpXUmxiblJwWVd4ekwwTnlaV1JsYm5ScFlXeHpVMjkxY21ObEpFTnlaV1JsYm5ScFlXeHpWSGx3WlR0TUFCQnNiMk5oYkZObGNuWnBZMlZPWVcxbGRBQVNUR3BoZG1FdmJHRnVaeTlUZEhKcGJtYzdUQUFYYldWemMyRm5aVVY0WTJWd2RHbHZia2hoYm1Sc1pYSnhBSDRBQlV3QURHMXBiR3hwYzFSdlRHbDJaWFFBRUV4cVlYWmhMMnhoYm1jdlRHOXVaenRNQUFod2NtbHZjbWwwZVhRQUUweHFZWFpoTDJ4aGJtY3ZTVzUwWldkbGNqdE1BQVZ4ZFdWMVpYRUFmZ0FEVEFBTmNtVjBjbmxCZEhSbGJYQjBjM0VBZmdBSFRBQUhjMlZ5ZG1salpYUUFFa3hxWVhaaEwyeGhibWN2VDJKcVpXTjBPMHdBRDNObGNuWnBZMlZGYm1SUWIybHVkSFFBRGt4cVlYWmhMMjVsZEM5VlVrdzdUQUFUYzJWeWRtbGpaVTVoYldWVGNHRmpaVlZTU1hFQWZnQUZUQUFRYzJWeWRtbGpaVTVoYldWemNHRmpaWEVBZmdBRlRBQUxjMlZ5ZG1salpWQmhkR2h4QUg0QUJYaHdjM0lBRVdwaGRtRXViR0Z1Wnk1Q2IyOXNaV0Z1elNCeWdOV2MrdTRDQUFGYUFBVjJZV3gxWlhod0FYQjBBQnBQVTBOaFkyaGxUbTkwYVdacFkyRjBhVzl1VTJWeWRtbGpaWFFBVFc5eVp5NXJkV0ZzYVM1eWFXTmxMbXR6WWk1dFpYTnpZV2RwYm1jdVpYaGpaWEIwYVc5dWFHRnVaR3hwYm1jdVJHVm1ZWFZzZEUxbGMzTmhaMlZGZUdObGNIUnBiMjVJWVc1a2JHVnljM0lBRG1waGRtRXViR0Z1Wnk1TWIyNW5PNHZra015UEk5OENBQUZLQUFWMllXeDFaWGh5QUJCcVlYWmhMbXhoYm1jdVRuVnRZbVZ5aHF5VkhRdVU0SXNDQUFCNGNQLy8vLy8vLy8vL2MzSUFFV3BoZG1FdWJHRnVaeTVKYm5SbFoyVnlFdUtncFBlQmh6Z0NBQUZKQUFWMllXeDFaWGh4QUg0QUVBQUFBQU56Y1FCK0FBc0FjUUIrQUJOd2MzSUFER3BoZG1FdWJtVjBMbFZTVEpZbE56WWEvT1J5QXdBSFNRQUlhR0Z6YUVOdlpHVkpBQVJ3YjNKMFRBQUpZWFYwYUc5eWFYUjVjUUIrQUFWTUFBUm1hV3hsY1FCK0FBVk1BQVJvYjNOMGNRQitBQVZNQUFod2NtOTBiMk52YkhFQWZnQUZUQUFEY21WbWNRQitBQVY0Y1AvLy8vOEFBQitRZEFBT2JHOWpZV3hvYjNOME9qZ3dPREIwQUNzdmEzSXRaR1YyTDNKbGJXOTBhVzVuTDA5VFEyRmphR1ZPYjNScFptbGpZWFJwYjI1VFpYSjJhV05sZEFBSmJHOWpZV3hvYjNOMGRBQUVhSFIwY0hCNGRBQUFkQUFHVkZKQlZrVk1kQUFCTDNOeUFCTnFZWFpoTG5WMGFXd3VRWEp5WVhsTWFYTjBlSUhTSFpuSFlaMERBQUZKQUFSemFYcGxlSEFBQUFBRmR3UUFBQUFLZEFBemIzSm5MbXQxWVd4cExuSnBZMlV1';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 400    FOR UPDATE;        
    buffer := 'YTNOaUxtMWxjM05oWjJsdVp5NXpaWEoyYVdObExrdFRRa3BoZG1GVFpYSjJhV05sZEFBOFkyOXRMbTl3Wlc1emVXMXdhRzl1ZVM1dmMyTmhZMmhsTG1KaGMyVXVaWFpsYm5SekxrTmhZMmhsUlc1MGNubEZkbVZ1ZEV4cGMzUmxibVZ5ZEFBM1kyOXRMbTl3Wlc1emVXMXdhRzl1ZVM1dmMyTmhZMmhsTG1KaGMyVXVaWFpsYm5SekxrTmhZMmhsUlhabGJuUk1hWE4wWlc1bGNuUUFGMnBoZG1FdWRYUnBiQzVGZG1WdWRFeHBjM1JsYm1WeWRBQXNZMjl0TG05d1pXNXplVzF3YUc5dWVTNXZjMk5oWTJobExtSmhjMlV1VEdsbVpXTjVZMnhsUVhkaGNtVjR0AA0xMjkuMTg2Ljc5LjQ1c3IAMm9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuSmF2YVNlcnZpY2VEZWZpbml0aW9utucNX0E0bPECAAFMABFzZXJ2aWNlSW50ZXJmYWNlc3QAEExqYXZhL3V0aWwvTGlzdDt4cgAub3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5TZXJ2aWNlRGVmaW5pdGlvbgCbAk9Y3Sl+AgANTAALYnVzU2VjdXJpdHlxAH4AE0wAD2NyZWRlbnRpYWxzVHlwZXQATExvcmcva3VhbGkvcmljZS9jb3JlL3NlY3VyaXR5L2NyZWRlbnRpYWxzL0NyZWRlbnRpYWxzU291cmNlJENyZWRlbnRpYWxzVHlwZTtMABBsb2NhbFNlcnZpY2VOYW1lcQB+AARMABdtZXNzYWdlRXhjZXB0aW9uSGFuZGxlcnEAfgAETAAMbWlsbGlzVG9MaXZlcQB+ABVMAAhwcmlvcml0eXEAfgAUTAAFcXVldWVxAH4AE0wADXJldHJ5QXR0ZW1wdHNxAH4AFEwAB3NlcnZpY2V0ABJMamF2YS9sYW5nL09iamVjdDtMAA9zZXJ2aWNlRW5kUG9pbnR0AA5MamF2YS9uZXQvVVJMO0wAE3NlcnZpY2VOYW1lU3BhY2VVUklxAH4ABEwAEHNlcnZpY2VOYW1lc3BhY2VxAH4ABEwAC3NlcnZpY2VQYXRocQB+AAR4cHNxAH4AGQFwdAAaT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2V0AE1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLmV4Y2VwdGlvbmhhbmRsaW5nLkRlZmF1bHRNZXNzYWdlRXhjZXB0aW9uSGFuZGxlcnNxAH4AH///////////c3EAfgAcAAAAA3NxAH4AGQBxAH4AMnBzcgAMamF2YS5uZXQuVVJMliU3Nhr85HIDAAdJAAhoYXNoQ29kZUkABHBvcnRMAAlhdXRob3JpdHlxAH4ABEwABGZpbGVxAH4ABEwABGhvc3RxAH4ABEwACHByb3RvY29scQB+AARMAANyZWZxAH4ABHhwaRuioAAAH5B0AA5sb2NhbGhvc3Q6ODA4MHQAKy9rci1kZXYvcmVtb3RpbmcvT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2V0AAlsb2NhbGhvc3R0AARodHRwcHh0AAB0AAZUUkFWRUx0AAEvc3IAE2phdmEudXRpbC5BcnJheUxpc3R4gdIdmcdhnQMAAUkABHNpemV4cAAAAAV3BAAAAAp0ADNvcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLnNlcnZpY2UuS1NCSmF2YVNlcnZpY2V0ADxjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5ldmVudHMuQ2FjaGVFbnRyeUV2ZW50TGlzdGVuZXJ0ADdjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5ldmVudHMuQ2FjaGVFdmVudExpc3RlbmVydAAXamF2YS51dGlsLkV2ZW50TGlzdGVuZXJ0ACxjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5MaWZlY3ljbGVBd2FyZXhxAH4AI3QABlRSQVZFTA==';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
INSERT INTO KRSB_MSG_PYLD_T (MSG_PYLD,MSG_QUE_ID)
  VALUES (EMPTY_CLOB(),401)
/
-- Length: 6060
-- Chunks: 2
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 401    FOR UPDATE;        
    buffer := 'rO0ABXNyAC1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLkFzeW5jaHJvbm91c0NhbGzxnQ5Y0t0lZQIAB1oAFWlnbm9yZVN0b3JlQW5kRm9yd2FyZFsACWFyZ3VtZW50c3QAE1tMamF2YS9sYW5nL09iamVjdDtMAAhjYWxsYmFja3QAM0xvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL0FzeW5jaHJvbm91c0NhbGxiYWNrO0wAB2NvbnRleHR0ABZMamF2YS9pby9TZXJpYWxpemFibGU7TAAKbWV0aG9kTmFtZXQAEkxqYXZhL2xhbmcvU3RyaW5nO1sACnBhcmFtVHlwZXN0ABJbTGphdmEvbGFuZy9DbGFzcztMAAtzZXJ2aWNlSW5mb3QAKkxvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL1NlcnZpY2VJbmZvO3hwAHVyABNbTGphdmEubGFuZy5PYmplY3Q7kM5YnxBzKWwCAAB4cAAAAAFzcgBDY29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLnBsdWdpbnMuY2x1c3RlcnN1cHBvcnQuQ2x1c3Rlck5vdGlmaWNhdGlvbhY+6VckvG9pAgACSQAEdHlwZUwABGRhdGFxAH4AA3hwAAAAAnQADkRvY3VtZW50VHlwZUlkcHB0AAZpbnZva2V1cgASW0xqYXZhLmxhbmcuQ2xhc3M7qxbXrsvNWpkCAAB4cAAAAAF2cgAUamF2YS5pby5TZXJpYWxpemFibGUQm2EN15tk7QIAAHhwc3IAKG9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuU2VydmljZUluZm/FFyO2KQS3ugIAC0wABWFsaXZldAATTGphdmEvbGFuZy9Cb29sZWFuO0wAFGVuZHBvaW50QWx0ZXJuYXRlVXJscQB+AARMAAtlbmRwb2ludFVybHEAfgAETAAKbG9ja1Zlck5icnQAE0xqYXZhL2xhbmcvSW50ZWdlcjtMAA5tZXNzYWdlRW50cnlJZHQAEExqYXZhL2xhbmcvTG9uZztMAAVxbmFtZXQAG0xqYXZheC94bWwvbmFtZXNwYWNlL1FOYW1lO0wAGnNlcmlhbGl6ZWRTZXJ2aWNlTmFtZXNwYWNlcQB+AARMAAhzZXJ2ZXJJcHEAfgAETAARc2VydmljZURlZmluaXRpb250ADBMb3JnL2t1YWxpL3JpY2Uva3NiL21lc3NhZ2luZy9TZXJ2aWNlRGVmaW5pdGlvbjtMAAtzZXJ2aWNlTmFtZXEAfgAETAAQc2VydmljZU5hbWVzcGFjZXEAfgAEeHBzcgARamF2YS5sYW5nLkJvb2xlYW7NIHKA1Zz67gIAAVoABXZhbHVleHABcHQAQGh0dHA6Ly9sb2NhbGhvc3Q6ODA4MC9rci1kZXYvcmVtb3RpbmcvT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2VzcgARamF2YS5sYW5nLkludGVnZXIS4qCk94GHOAIAAUkABXZhbHVleHIAEGphdmEubGFuZy5OdW1iZXKGrJUdC5TgiwIAAHhwAAAAAXNyAA5qYXZhLmxhbmcuTG9uZzuL5JDMjyPfAgABSgAFdmFsdWV4cQB+AB0AAAAAAAAPfXNyABlqYXZheC54bWwubmFtZXNwYWNlLlFOYW1lgW2oLfw73WwCAANMAAlsb2NhbFBhcnRxAH4ABEwADG5hbWVzcGFjZVVSSXEAfgAETAAGcHJlZml4cQB+AAR4cHQAGk9TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNldAAAcQB+ACR0B3RyTzBBQlhOeUFESnZjbWN1YTNWaGJHa3VjbWxqWlM1cmMySXViV1Z6YzJGbmFXNW5Ma3BoZG1GVFpYSjJhV05sUkdWbWFXNXBkR2x2YnJibkRWOUJOR3p4QWdBQlRBQVJjMlZ5ZG1salpVbHVkR1Z5Wm1GalpYTjBBQkJNYW1GMllTOTFkR2xzTDB4cGMzUTdlSElBTG05eVp5NXJkV0ZzYVM1eWFXTmxMbXR6WWk1dFpYTnpZV2RwYm1jdVUyVnlkbWxqWlVSbFptbHVhWFJwYjI0QW13SlBXTjBwZmdJQURVd0FDMkoxYzFObFkzVnlhWFI1ZEFBVFRHcGhkbUV2YkdGdVp5OUNiMjlzWldGdU8wd0FEMk55WldSbGJuUnBZV3h6Vkhsd1pYUUFURXh2Y21jdmEzVmhiR2t2Y21salpTOWpiM0psTDNObFkzVnlhWFI1TDJOeVpXUmxiblJwWVd4ekwwTnlaV1JsYm5ScFlXeHpVMjkxY21ObEpFTnlaV1JsYm5ScFlXeHpWSGx3WlR0TUFCQnNiMk5oYkZObGNuWnBZMlZPWVcxbGRBQVNUR3BoZG1FdmJHRnVaeTlUZEhKcGJtYzdUQUFYYldWemMyRm5aVVY0WTJWd2RHbHZia2hoYm1Sc1pYSnhBSDRBQlV3QURHMXBiR3hwYzFSdlRHbDJaWFFBRUV4cVlYWmhMMnhoYm1jdlRHOXVaenRNQUFod2NtbHZjbWwwZVhRQUUweHFZWFpoTDJ4aGJtY3ZTVzUwWldkbGNqdE1BQVZ4ZFdWMVpYRUFmZ0FEVEFBTmNtVjBjbmxCZEhSbGJYQjBjM0VBZmdBSFRBQUhjMlZ5ZG1salpYUUFFa3hxWVhaaEwyeGhibWN2VDJKcVpXTjBPMHdBRDNObGNuWnBZMlZGYm1SUWIybHVkSFFBRGt4cVlYWmhMMjVsZEM5VlVrdzdUQUFUYzJWeWRtbGpaVTVoYldWVGNHRmpaVlZTU1hFQWZnQUZUQUFRYzJWeWRtbGpaVTVoYldWemNHRmpaWEVBZmdBRlRBQUxjMlZ5ZG1salpWQmhkR2h4QUg0QUJYaHdjM0lBRVdwaGRtRXViR0Z1Wnk1Q2IyOXNaV0Z1elNCeWdOV2MrdTRDQUFGYUFBVjJZV3gxWlhod0FYQjBBQnBQVTBOaFkyaGxUbTkwYVdacFkyRjBhVzl1VTJWeWRtbGpaWFFBVFc5eVp5NXJkV0ZzYVM1eWFXTmxMbXR6WWk1dFpYTnpZV2RwYm1jdVpYaGpaWEIwYVc5dWFHRnVaR3hwYm1jdVJHVm1ZWFZzZEUxbGMzTmhaMlZGZUdObGNIUnBiMjVJWVc1a2JHVnljM0lBRG1waGRtRXViR0Z1Wnk1TWIyNW5PNHZra015UEk5OENBQUZLQUFWMllXeDFaWGh5QUJCcVlYWmhMbXhoYm1jdVRuVnRZbVZ5aHF5VkhRdVU0SXNDQUFCNGNQLy8vLy8vLy8vL2MzSUFFV3BoZG1FdWJHRnVaeTVKYm5SbFoyVnlFdUtncFBlQmh6Z0NBQUZKQUFWMllXeDFaWGh4QUg0QUVBQUFBQU56Y1FCK0FBc0FjUUIrQUJOd2MzSUFER3BoZG1FdWJtVjBMbFZTVEpZbE56WWEvT1J5QXdBSFNRQUlhR0Z6YUVOdlpHVkpBQVJ3YjNKMFRBQUpZWFYwYUc5eWFYUjVjUUIrQUFWTUFBUm1hV3hsY1FCK0FBVk1BQVJvYjNOMGNRQitBQVZNQUFod2NtOTBiMk52YkhFQWZnQUZUQUFEY21WbWNRQitBQVY0Y1AvLy8vOEFBQitRZEFBT2JHOWpZV3hvYjNOME9qZ3dPREIwQUNzdmEzSXRaR1YyTDNKbGJXOTBhVzVuTDA5VFEyRmphR1ZPYjNScFptbGpZWFJwYjI1VFpYSjJhV05sZEFBSmJHOWpZV3hvYjNOMGRBQUVhSFIwY0hCNGRBQUFkQUFHVkZKQlZrVk1kQUFCTDNOeUFCTnFZWFpoTG5WMGFXd3VRWEp5WVhsTWFYTjBlSUhTSFpuSFlaMERBQUZKQUFSemFYcGxlSEFBQUFBRmR3UUFBQUFLZEFBemIzSm5MbXQxWVd4cExuSnBZMlV1YTNOaUxtMWxjM05oWjJsdVp5';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 401    FOR UPDATE;        
    buffer := 'NXpaWEoyYVdObExrdFRRa3BoZG1GVFpYSjJhV05sZEFBOFkyOXRMbTl3Wlc1emVXMXdhRzl1ZVM1dmMyTmhZMmhsTG1KaGMyVXVaWFpsYm5SekxrTmhZMmhsUlc1MGNubEZkbVZ1ZEV4cGMzUmxibVZ5ZEFBM1kyOXRMbTl3Wlc1emVXMXdhRzl1ZVM1dmMyTmhZMmhsTG1KaGMyVXVaWFpsYm5SekxrTmhZMmhsUlhabGJuUk1hWE4wWlc1bGNuUUFGMnBoZG1FdWRYUnBiQzVGZG1WdWRFeHBjM1JsYm1WeWRBQXNZMjl0TG05d1pXNXplVzF3YUc5dWVTNXZjMk5oWTJobExtSmhjMlV1VEdsbVpXTjVZMnhsUVhkaGNtVjR0AA0xMjkuMTg2Ljc5LjQ1c3IAMm9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuSmF2YVNlcnZpY2VEZWZpbml0aW9utucNX0E0bPECAAFMABFzZXJ2aWNlSW50ZXJmYWNlc3QAEExqYXZhL3V0aWwvTGlzdDt4cgAub3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5TZXJ2aWNlRGVmaW5pdGlvbgCbAk9Y3Sl+AgANTAALYnVzU2VjdXJpdHlxAH4AE0wAD2NyZWRlbnRpYWxzVHlwZXQATExvcmcva3VhbGkvcmljZS9jb3JlL3NlY3VyaXR5L2NyZWRlbnRpYWxzL0NyZWRlbnRpYWxzU291cmNlJENyZWRlbnRpYWxzVHlwZTtMABBsb2NhbFNlcnZpY2VOYW1lcQB+AARMABdtZXNzYWdlRXhjZXB0aW9uSGFuZGxlcnEAfgAETAAMbWlsbGlzVG9MaXZlcQB+ABVMAAhwcmlvcml0eXEAfgAUTAAFcXVldWVxAH4AE0wADXJldHJ5QXR0ZW1wdHNxAH4AFEwAB3NlcnZpY2V0ABJMamF2YS9sYW5nL09iamVjdDtMAA9zZXJ2aWNlRW5kUG9pbnR0AA5MamF2YS9uZXQvVVJMO0wAE3NlcnZpY2VOYW1lU3BhY2VVUklxAH4ABEwAEHNlcnZpY2VOYW1lc3BhY2VxAH4ABEwAC3NlcnZpY2VQYXRocQB+AAR4cHNxAH4AGQFwdAAaT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2V0AE1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLmV4Y2VwdGlvbmhhbmRsaW5nLkRlZmF1bHRNZXNzYWdlRXhjZXB0aW9uSGFuZGxlcnNxAH4AH///////////c3EAfgAcAAAAA3NxAH4AGQBxAH4AMnBzcgAMamF2YS5uZXQuVVJMliU3Nhr85HIDAAdJAAhoYXNoQ29kZUkABHBvcnRMAAlhdXRob3JpdHlxAH4ABEwABGZpbGVxAH4ABEwABGhvc3RxAH4ABEwACHByb3RvY29scQB+AARMAANyZWZxAH4ABHhwaRuioAAAH5B0AA5sb2NhbGhvc3Q6ODA4MHQAKy9rci1kZXYvcmVtb3RpbmcvT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2V0AAlsb2NhbGhvc3R0AARodHRwcHh0AAB0AAZUUkFWRUx0AAEvc3IAE2phdmEudXRpbC5BcnJheUxpc3R4gdIdmcdhnQMAAUkABHNpemV4cAAAAAV3BAAAAAp0ADNvcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLnNlcnZpY2UuS1NCSmF2YVNlcnZpY2V0ADxjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5ldmVudHMuQ2FjaGVFbnRyeUV2ZW50TGlzdGVuZXJ0ADdjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5ldmVudHMuQ2FjaGVFdmVudExpc3RlbmVydAAXamF2YS51dGlsLkV2ZW50TGlzdGVuZXJ0ACxjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5MaWZlY3ljbGVBd2FyZXhxAH4AI3QABlRSQVZFTA==';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
INSERT INTO KRSB_MSG_PYLD_T (MSG_PYLD,MSG_QUE_ID)
  VALUES (EMPTY_CLOB(),402)
/
-- Length: 6060
-- Chunks: 2
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 402    FOR UPDATE;        
    buffer := 'rO0ABXNyAC1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLkFzeW5jaHJvbm91c0NhbGzxnQ5Y0t0lZQIAB1oAFWlnbm9yZVN0b3JlQW5kRm9yd2FyZFsACWFyZ3VtZW50c3QAE1tMamF2YS9sYW5nL09iamVjdDtMAAhjYWxsYmFja3QAM0xvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL0FzeW5jaHJvbm91c0NhbGxiYWNrO0wAB2NvbnRleHR0ABZMamF2YS9pby9TZXJpYWxpemFibGU7TAAKbWV0aG9kTmFtZXQAEkxqYXZhL2xhbmcvU3RyaW5nO1sACnBhcmFtVHlwZXN0ABJbTGphdmEvbGFuZy9DbGFzcztMAAtzZXJ2aWNlSW5mb3QAKkxvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL1NlcnZpY2VJbmZvO3hwAHVyABNbTGphdmEubGFuZy5PYmplY3Q7kM5YnxBzKWwCAAB4cAAAAAFzcgBDY29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLnBsdWdpbnMuY2x1c3RlcnN1cHBvcnQuQ2x1c3Rlck5vdGlmaWNhdGlvbhY+6VckvG9pAgACSQAEdHlwZUwABGRhdGFxAH4AA3hwAAAAAnQAEERvY3VtZW50VHlwZU5hbWVwcHQABmludm9rZXVyABJbTGphdmEubGFuZy5DbGFzczurFteuy81amQIAAHhwAAAAAXZyABRqYXZhLmlvLlNlcmlhbGl6YWJsZRCbYQ3Xm2TtAgAAeHBzcgAob3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5TZXJ2aWNlSW5mb8UXI7YpBLe6AgALTAAFYWxpdmV0ABNMamF2YS9sYW5nL0Jvb2xlYW47TAAUZW5kcG9pbnRBbHRlcm5hdGVVcmxxAH4ABEwAC2VuZHBvaW50VXJscQB+AARMAApsb2NrVmVyTmJydAATTGphdmEvbGFuZy9JbnRlZ2VyO0wADm1lc3NhZ2VFbnRyeUlkdAAQTGphdmEvbGFuZy9Mb25nO0wABXFuYW1ldAAbTGphdmF4L3htbC9uYW1lc3BhY2UvUU5hbWU7TAAac2VyaWFsaXplZFNlcnZpY2VOYW1lc3BhY2VxAH4ABEwACHNlcnZlcklwcQB+AARMABFzZXJ2aWNlRGVmaW5pdGlvbnQAMExvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL1NlcnZpY2VEZWZpbml0aW9uO0wAC3NlcnZpY2VOYW1lcQB+AARMABBzZXJ2aWNlTmFtZXNwYWNlcQB+AAR4cHNyABFqYXZhLmxhbmcuQm9vbGVhbs0gcoDVnPruAgABWgAFdmFsdWV4cAFwdABAaHR0cDovL2xvY2FsaG9zdDo4MDgwL2tyLWRldi9yZW1vdGluZy9PU0NhY2hlTm90aWZpY2F0aW9uU2VydmljZXNyABFqYXZhLmxhbmcuSW50ZWdlchLioKT3gYc4AgABSQAFdmFsdWV4cgAQamF2YS5sYW5nLk51bWJlcoaslR0LlOCLAgAAeHAAAAABc3IADmphdmEubGFuZy5Mb25nO4vkkMyPI98CAAFKAAV2YWx1ZXhxAH4AHQAAAAAAAA99c3IAGWphdmF4LnhtbC5uYW1lc3BhY2UuUU5hbWWBbagt/DvdbAIAA0wACWxvY2FsUGFydHEAfgAETAAMbmFtZXNwYWNlVVJJcQB+AARMAAZwcmVmaXhxAH4ABHhwdAAaT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2V0AABxAH4AJHQHdHJPMEFCWE55QURKdmNtY3VhM1ZoYkdrdWNtbGpaUzVyYzJJdWJXVnpjMkZuYVc1bkxrcGhkbUZUWlhKMmFXTmxSR1ZtYVc1cGRHbHZicmJuRFY5Qk5HenhBZ0FCVEFBUmMyVnlkbWxqWlVsdWRHVnlabUZqWlhOMEFCQk1hbUYyWVM5MWRHbHNMMHhwYzNRN2VISUFMbTl5Wnk1cmRXRnNhUzV5YVdObExtdHpZaTV0WlhOellXZHBibWN1VTJWeWRtbGpaVVJsWm1sdWFYUnBiMjRBbXdKUFdOMHBmZ0lBRFV3QUMySjFjMU5sWTNWeWFYUjVkQUFUVEdwaGRtRXZiR0Z1Wnk5Q2IyOXNaV0Z1TzB3QUQyTnlaV1JsYm5ScFlXeHpWSGx3WlhRQVRFeHZjbWN2YTNWaGJHa3ZjbWxqWlM5amIzSmxMM05sWTNWeWFYUjVMMk55WldSbGJuUnBZV3h6TDBOeVpXUmxiblJwWVd4elUyOTFjbU5sSkVOeVpXUmxiblJwWVd4elZIbHdaVHRNQUJCc2IyTmhiRk5sY25acFkyVk9ZVzFsZEFBU1RHcGhkbUV2YkdGdVp5OVRkSEpwYm1jN1RBQVhiV1Z6YzJGblpVVjRZMlZ3ZEdsdmJraGhibVJzWlhKeEFINEFCVXdBREcxcGJHeHBjMVJ2VEdsMlpYUUFFRXhxWVhaaEwyeGhibWN2VEc5dVp6dE1BQWh3Y21sdmNtbDBlWFFBRTB4cVlYWmhMMnhoYm1jdlNXNTBaV2RsY2p0TUFBVnhkV1YxWlhFQWZnQURUQUFOY21WMGNubEJkSFJsYlhCMGMzRUFmZ0FIVEFBSGMyVnlkbWxqWlhRQUVreHFZWFpoTDJ4aGJtY3ZUMkpxWldOME8wd0FEM05sY25acFkyVkZibVJRYjJsdWRIUUFEa3hxWVhaaEwyNWxkQzlWVWt3N1RBQVRjMlZ5ZG1salpVNWhiV1ZUY0dGalpWVlNTWEVBZmdBRlRBQVFjMlZ5ZG1salpVNWhiV1Z6Y0dGalpYRUFmZ0FGVEFBTGMyVnlkbWxqWlZCaGRHaHhBSDRBQlhod2MzSUFFV3BoZG1FdWJHRnVaeTVDYjI5c1pXRnV6U0J5Z05XYyt1NENBQUZhQUFWMllXeDFaWGh3QVhCMEFCcFBVME5oWTJobFRtOTBhV1pwWTJGMGFXOXVVMlZ5ZG1salpYUUFUVzl5Wnk1cmRXRnNhUzV5YVdObExtdHpZaTV0WlhOellXZHBibWN1WlhoalpYQjBhVzl1YUdGdVpHeHBibWN1UkdWbVlYVnNkRTFsYzNOaFoyVkZlR05sY0hScGIyNUlZVzVrYkdWeWMzSUFEbXBoZG1FdWJHRnVaeTVNYjI1bk80dmtrTXlQSTk4Q0FBRktBQVYyWVd4MVpYaHlBQkJxWVhaaExteGhibWN1VG5WdFltVnlocXlWSFF1VTRJc0NBQUI0Y1AvLy8vLy8vLy8vYzNJQUVXcGhkbUV1YkdGdVp5NUpiblJsWjJWeUV1S2dwUGVCaHpnQ0FBRkpBQVYyWVd4MVpYaHhBSDRBRUFBQUFBTnpjUUIrQUFzQWNRQitBQk53YzNJQURHcGhkbUV1Ym1WMExsVlNUSllsTnpZYS9PUnlBd0FIU1FBSWFHRnphRU52WkdWSkFBUndiM0owVEFBSllYVjBhRzl5YVhSNWNRQitBQVZNQUFSbWFXeGxjUUIrQUFWTUFBUm9iM04wY1FCK0FBVk1BQWh3Y205MGIyTnZiSEVBZmdBRlRBQURjbVZtY1FCK0FBVjRjUC8vLy84QUFCK1FkQUFPYkc5allXeG9iM04wT2pnd09EQjBBQ3N2YTNJdFpHVjJMM0psYlc5MGFXNW5MMDlUUTJGamFHVk9iM1JwWm1sallYUnBiMjVUWlhKMmFXTmxkQUFKYkc5allXeG9iM04wZEFBRWFIUjBjSEI0ZEFBQWRBQUdWRkpCVmtWTWRBQUJMM055QUJOcVlYWmhMblYwYVd3dVFYSnlZWGxNYVhOMGVJSFNIWm5IWVowREFBRkpBQVJ6YVhwbGVIQUFBQUFGZHdRQUFBQUtkQUF6YjNKbkxtdDFZV3hwTG5KcFkyVXVhM05pTG0xbGMzTmhaMmx1';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 402    FOR UPDATE;        
    buffer := 'Wnk1elpYSjJhV05sTGt0VFFrcGhkbUZUWlhKMmFXTmxkQUE4WTI5dExtOXdaVzV6ZVcxd2FHOXVlUzV2YzJOaFkyaGxMbUpoYzJVdVpYWmxiblJ6TGtOaFkyaGxSVzUwY25sRmRtVnVkRXhwYzNSbGJtVnlkQUEzWTI5dExtOXdaVzV6ZVcxd2FHOXVlUzV2YzJOaFkyaGxMbUpoYzJVdVpYWmxiblJ6TGtOaFkyaGxSWFpsYm5STWFYTjBaVzVsY25RQUYycGhkbUV1ZFhScGJDNUZkbVZ1ZEV4cGMzUmxibVZ5ZEFBc1kyOXRMbTl3Wlc1emVXMXdhRzl1ZVM1dmMyTmhZMmhsTG1KaGMyVXVUR2xtWldONVkyeGxRWGRoY21WNHQADTEyOS4xODYuNzkuNDVzcgAyb3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5KYXZhU2VydmljZURlZmluaXRpb2625w1fQTRs8QIAAUwAEXNlcnZpY2VJbnRlcmZhY2VzdAAQTGphdmEvdXRpbC9MaXN0O3hyAC5vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLlNlcnZpY2VEZWZpbml0aW9uAJsCT1jdKX4CAA1MAAtidXNTZWN1cml0eXEAfgATTAAPY3JlZGVudGlhbHNUeXBldABMTG9yZy9rdWFsaS9yaWNlL2NvcmUvc2VjdXJpdHkvY3JlZGVudGlhbHMvQ3JlZGVudGlhbHNTb3VyY2UkQ3JlZGVudGlhbHNUeXBlO0wAEGxvY2FsU2VydmljZU5hbWVxAH4ABEwAF21lc3NhZ2VFeGNlcHRpb25IYW5kbGVycQB+AARMAAxtaWxsaXNUb0xpdmVxAH4AFUwACHByaW9yaXR5cQB+ABRMAAVxdWV1ZXEAfgATTAANcmV0cnlBdHRlbXB0c3EAfgAUTAAHc2VydmljZXQAEkxqYXZhL2xhbmcvT2JqZWN0O0wAD3NlcnZpY2VFbmRQb2ludHQADkxqYXZhL25ldC9VUkw7TAATc2VydmljZU5hbWVTcGFjZVVSSXEAfgAETAAQc2VydmljZU5hbWVzcGFjZXEAfgAETAALc2VydmljZVBhdGhxAH4ABHhwc3EAfgAZAXB0ABpPU0NhY2hlTm90aWZpY2F0aW9uU2VydmljZXQATW9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuZXhjZXB0aW9uaGFuZGxpbmcuRGVmYXVsdE1lc3NhZ2VFeGNlcHRpb25IYW5kbGVyc3EAfgAf//////////9zcQB+ABwAAAADc3EAfgAZAHEAfgAycHNyAAxqYXZhLm5ldC5VUkyWJTc2GvzkcgMAB0kACGhhc2hDb2RlSQAEcG9ydEwACWF1dGhvcml0eXEAfgAETAAEZmlsZXEAfgAETAAEaG9zdHEAfgAETAAIcHJvdG9jb2xxAH4ABEwAA3JlZnEAfgAEeHBpG6KgAAAfkHQADmxvY2FsaG9zdDo4MDgwdAArL2tyLWRldi9yZW1vdGluZy9PU0NhY2hlTm90aWZpY2F0aW9uU2VydmljZXQACWxvY2FsaG9zdHQABGh0dHBweHQAAHQABlRSQVZFTHQAAS9zcgATamF2YS51dGlsLkFycmF5TGlzdHiB0h2Zx2GdAwABSQAEc2l6ZXhwAAAABXcEAAAACnQAM29yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuc2VydmljZS5LU0JKYXZhU2VydmljZXQAPGNvbS5vcGVuc3ltcGhvbnkub3NjYWNoZS5iYXNlLmV2ZW50cy5DYWNoZUVudHJ5RXZlbnRMaXN0ZW5lcnQAN2NvbS5vcGVuc3ltcGhvbnkub3NjYWNoZS5iYXNlLmV2ZW50cy5DYWNoZUV2ZW50TGlzdGVuZXJ0ABdqYXZhLnV0aWwuRXZlbnRMaXN0ZW5lcnQALGNvbS5vcGVuc3ltcGhvbnkub3NjYWNoZS5iYXNlLkxpZmVjeWNsZUF3YXJleHEAfgAjdAAGVFJBVkVM';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
INSERT INTO KRSB_MSG_PYLD_T (MSG_PYLD,MSG_QUE_ID)
  VALUES (EMPTY_CLOB(),403)
/
-- Length: 6084
-- Chunks: 2
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 403    FOR UPDATE;        
    buffer := 'rO0ABXNyAC1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLkFzeW5jaHJvbm91c0NhbGzxnQ5Y0t0lZQIAB1oAFWlnbm9yZVN0b3JlQW5kRm9yd2FyZFsACWFyZ3VtZW50c3QAE1tMamF2YS9sYW5nL09iamVjdDtMAAhjYWxsYmFja3QAM0xvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL0FzeW5jaHJvbm91c0NhbGxiYWNrO0wAB2NvbnRleHR0ABZMamF2YS9pby9TZXJpYWxpemFibGU7TAAKbWV0aG9kTmFtZXQAEkxqYXZhL2xhbmcvU3RyaW5nO1sACnBhcmFtVHlwZXN0ABJbTGphdmEvbGFuZy9DbGFzcztMAAtzZXJ2aWNlSW5mb3QAKkxvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL1NlcnZpY2VJbmZvO3hwAHVyABNbTGphdmEubGFuZy5PYmplY3Q7kM5YnxBzKWwCAAB4cAAAAAFzcgBDY29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLnBsdWdpbnMuY2x1c3RlcnN1cHBvcnQuQ2x1c3Rlck5vdGlmaWNhdGlvbhY+6VckvG9pAgACSQAEdHlwZUwABGRhdGFxAH4AA3hwAAAAAXQAIERvY3VtZW50VHlwZTpDdXJyZW50Um9vdHNJbkNhY2hlcHB0AAZpbnZva2V1cgASW0xqYXZhLmxhbmcuQ2xhc3M7qxbXrsvNWpkCAAB4cAAAAAF2cgAUamF2YS5pby5TZXJpYWxpemFibGUQm2EN15tk7QIAAHhwc3IAKG9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuU2VydmljZUluZm/FFyO2KQS3ugIAC0wABWFsaXZldAATTGphdmEvbGFuZy9Cb29sZWFuO0wAFGVuZHBvaW50QWx0ZXJuYXRlVXJscQB+AARMAAtlbmRwb2ludFVybHEAfgAETAAKbG9ja1Zlck5icnQAE0xqYXZhL2xhbmcvSW50ZWdlcjtMAA5tZXNzYWdlRW50cnlJZHQAEExqYXZhL2xhbmcvTG9uZztMAAVxbmFtZXQAG0xqYXZheC94bWwvbmFtZXNwYWNlL1FOYW1lO0wAGnNlcmlhbGl6ZWRTZXJ2aWNlTmFtZXNwYWNlcQB+AARMAAhzZXJ2ZXJJcHEAfgAETAARc2VydmljZURlZmluaXRpb250ADBMb3JnL2t1YWxpL3JpY2Uva3NiL21lc3NhZ2luZy9TZXJ2aWNlRGVmaW5pdGlvbjtMAAtzZXJ2aWNlTmFtZXEAfgAETAAQc2VydmljZU5hbWVzcGFjZXEAfgAEeHBzcgARamF2YS5sYW5nLkJvb2xlYW7NIHKA1Zz67gIAAVoABXZhbHVleHABcHQAQGh0dHA6Ly9sb2NhbGhvc3Q6ODA4MC9rci1kZXYvcmVtb3RpbmcvT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2VzcgARamF2YS5sYW5nLkludGVnZXIS4qCk94GHOAIAAUkABXZhbHVleHIAEGphdmEubGFuZy5OdW1iZXKGrJUdC5TgiwIAAHhwAAAAAXNyAA5qYXZhLmxhbmcuTG9uZzuL5JDMjyPfAgABSgAFdmFsdWV4cQB+AB0AAAAAAAAPfXNyABlqYXZheC54bWwubmFtZXNwYWNlLlFOYW1lgW2oLfw73WwCAANMAAlsb2NhbFBhcnRxAH4ABEwADG5hbWVzcGFjZVVSSXEAfgAETAAGcHJlZml4cQB+AAR4cHQAGk9TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNldAAAcQB+ACR0B3RyTzBBQlhOeUFESnZjbWN1YTNWaGJHa3VjbWxqWlM1cmMySXViV1Z6YzJGbmFXNW5Ma3BoZG1GVFpYSjJhV05sUkdWbWFXNXBkR2x2YnJibkRWOUJOR3p4QWdBQlRBQVJjMlZ5ZG1salpVbHVkR1Z5Wm1GalpYTjBBQkJNYW1GMllTOTFkR2xzTDB4cGMzUTdlSElBTG05eVp5NXJkV0ZzYVM1eWFXTmxMbXR6WWk1dFpYTnpZV2RwYm1jdVUyVnlkbWxqWlVSbFptbHVhWFJwYjI0QW13SlBXTjBwZmdJQURVd0FDMkoxYzFObFkzVnlhWFI1ZEFBVFRHcGhkbUV2YkdGdVp5OUNiMjlzWldGdU8wd0FEMk55WldSbGJuUnBZV3h6Vkhsd1pYUUFURXh2Y21jdmEzVmhiR2t2Y21salpTOWpiM0psTDNObFkzVnlhWFI1TDJOeVpXUmxiblJwWVd4ekwwTnlaV1JsYm5ScFlXeHpVMjkxY21ObEpFTnlaV1JsYm5ScFlXeHpWSGx3WlR0TUFCQnNiMk5oYkZObGNuWnBZMlZPWVcxbGRBQVNUR3BoZG1FdmJHRnVaeTlUZEhKcGJtYzdUQUFYYldWemMyRm5aVVY0WTJWd2RHbHZia2hoYm1Sc1pYSnhBSDRBQlV3QURHMXBiR3hwYzFSdlRHbDJaWFFBRUV4cVlYWmhMMnhoYm1jdlRHOXVaenRNQUFod2NtbHZjbWwwZVhRQUUweHFZWFpoTDJ4aGJtY3ZTVzUwWldkbGNqdE1BQVZ4ZFdWMVpYRUFmZ0FEVEFBTmNtVjBjbmxCZEhSbGJYQjBjM0VBZmdBSFRBQUhjMlZ5ZG1salpYUUFFa3hxWVhaaEwyeGhibWN2VDJKcVpXTjBPMHdBRDNObGNuWnBZMlZGYm1SUWIybHVkSFFBRGt4cVlYWmhMMjVsZEM5VlVrdzdUQUFUYzJWeWRtbGpaVTVoYldWVGNHRmpaVlZTU1hFQWZnQUZUQUFRYzJWeWRtbGpaVTVoYldWemNHRmpaWEVBZmdBRlRBQUxjMlZ5ZG1salpWQmhkR2h4QUg0QUJYaHdjM0lBRVdwaGRtRXViR0Z1Wnk1Q2IyOXNaV0Z1elNCeWdOV2MrdTRDQUFGYUFBVjJZV3gxWlhod0FYQjBBQnBQVTBOaFkyaGxUbTkwYVdacFkyRjBhVzl1VTJWeWRtbGpaWFFBVFc5eVp5NXJkV0ZzYVM1eWFXTmxMbXR6WWk1dFpYTnpZV2RwYm1jdVpYaGpaWEIwYVc5dWFHRnVaR3hwYm1jdVJHVm1ZWFZzZEUxbGMzTmhaMlZGZUdObGNIUnBiMjVJWVc1a2JHVnljM0lBRG1waGRtRXViR0Z1Wnk1TWIyNW5PNHZra015UEk5OENBQUZLQUFWMllXeDFaWGh5QUJCcVlYWmhMbXhoYm1jdVRuVnRZbVZ5aHF5VkhRdVU0SXNDQUFCNGNQLy8vLy8vLy8vL2MzSUFFV3BoZG1FdWJHRnVaeTVKYm5SbFoyVnlFdUtncFBlQmh6Z0NBQUZKQUFWMllXeDFaWGh4QUg0QUVBQUFBQU56Y1FCK0FBc0FjUUIrQUJOd2MzSUFER3BoZG1FdWJtVjBMbFZTVEpZbE56WWEvT1J5QXdBSFNRQUlhR0Z6YUVOdlpHVkpBQVJ3YjNKMFRBQUpZWFYwYUc5eWFYUjVjUUIrQUFWTUFBUm1hV3hsY1FCK0FBVk1BQVJvYjNOMGNRQitBQVZNQUFod2NtOTBiMk52YkhFQWZnQUZUQUFEY21WbWNRQitBQVY0Y1AvLy8vOEFBQitRZEFBT2JHOWpZV3hvYjNOME9qZ3dPREIwQUNzdmEzSXRaR1YyTDNKbGJXOTBhVzVuTDA5VFEyRmphR1ZPYjNScFptbGpZWFJwYjI1VFpYSjJhV05sZEFBSmJHOWpZV3hvYjNOMGRBQUVhSFIwY0hCNGRBQUFkQUFHVkZKQlZrVk1kQUFCTDNOeUFCTnFZWFpoTG5WMGFXd3VRWEp5WVhsTWFYTjBlSUhTSFpuSFlaMERBQUZKQUFSemFYcGxlSEFBQUFBRmR3UUFBQUFLZEFBemIzSm5MbXQxWVd4cExuSnBZMlV1';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 403    FOR UPDATE;        
    buffer := 'YTNOaUxtMWxjM05oWjJsdVp5NXpaWEoyYVdObExrdFRRa3BoZG1GVFpYSjJhV05sZEFBOFkyOXRMbTl3Wlc1emVXMXdhRzl1ZVM1dmMyTmhZMmhsTG1KaGMyVXVaWFpsYm5SekxrTmhZMmhsUlc1MGNubEZkbVZ1ZEV4cGMzUmxibVZ5ZEFBM1kyOXRMbTl3Wlc1emVXMXdhRzl1ZVM1dmMyTmhZMmhsTG1KaGMyVXVaWFpsYm5SekxrTmhZMmhsUlhabGJuUk1hWE4wWlc1bGNuUUFGMnBoZG1FdWRYUnBiQzVGZG1WdWRFeHBjM1JsYm1WeWRBQXNZMjl0TG05d1pXNXplVzF3YUc5dWVTNXZjMk5oWTJobExtSmhjMlV1VEdsbVpXTjVZMnhsUVhkaGNtVjR0AA0xMjkuMTg2Ljc5LjQ1c3IAMm9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuSmF2YVNlcnZpY2VEZWZpbml0aW9utucNX0E0bPECAAFMABFzZXJ2aWNlSW50ZXJmYWNlc3QAEExqYXZhL3V0aWwvTGlzdDt4cgAub3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5TZXJ2aWNlRGVmaW5pdGlvbgCbAk9Y3Sl+AgANTAALYnVzU2VjdXJpdHlxAH4AE0wAD2NyZWRlbnRpYWxzVHlwZXQATExvcmcva3VhbGkvcmljZS9jb3JlL3NlY3VyaXR5L2NyZWRlbnRpYWxzL0NyZWRlbnRpYWxzU291cmNlJENyZWRlbnRpYWxzVHlwZTtMABBsb2NhbFNlcnZpY2VOYW1lcQB+AARMABdtZXNzYWdlRXhjZXB0aW9uSGFuZGxlcnEAfgAETAAMbWlsbGlzVG9MaXZlcQB+ABVMAAhwcmlvcml0eXEAfgAUTAAFcXVldWVxAH4AE0wADXJldHJ5QXR0ZW1wdHNxAH4AFEwAB3NlcnZpY2V0ABJMamF2YS9sYW5nL09iamVjdDtMAA9zZXJ2aWNlRW5kUG9pbnR0AA5MamF2YS9uZXQvVVJMO0wAE3NlcnZpY2VOYW1lU3BhY2VVUklxAH4ABEwAEHNlcnZpY2VOYW1lc3BhY2VxAH4ABEwAC3NlcnZpY2VQYXRocQB+AAR4cHNxAH4AGQFwdAAaT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2V0AE1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLmV4Y2VwdGlvbmhhbmRsaW5nLkRlZmF1bHRNZXNzYWdlRXhjZXB0aW9uSGFuZGxlcnNxAH4AH///////////c3EAfgAcAAAAA3NxAH4AGQBxAH4AMnBzcgAMamF2YS5uZXQuVVJMliU3Nhr85HIDAAdJAAhoYXNoQ29kZUkABHBvcnRMAAlhdXRob3JpdHlxAH4ABEwABGZpbGVxAH4ABEwABGhvc3RxAH4ABEwACHByb3RvY29scQB+AARMAANyZWZxAH4ABHhwaRuioAAAH5B0AA5sb2NhbGhvc3Q6ODA4MHQAKy9rci1kZXYvcmVtb3RpbmcvT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2V0AAlsb2NhbGhvc3R0AARodHRwcHh0AAB0AAZUUkFWRUx0AAEvc3IAE2phdmEudXRpbC5BcnJheUxpc3R4gdIdmcdhnQMAAUkABHNpemV4cAAAAAV3BAAAAAp0ADNvcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLnNlcnZpY2UuS1NCSmF2YVNlcnZpY2V0ADxjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5ldmVudHMuQ2FjaGVFbnRyeUV2ZW50TGlzdGVuZXJ0ADdjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5ldmVudHMuQ2FjaGVFdmVudExpc3RlbmVydAAXamF2YS51dGlsLkV2ZW50TGlzdGVuZXJ0ACxjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5MaWZlY3ljbGVBd2FyZXhxAH4AI3QABlRSQVZFTA==';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
INSERT INTO KRSB_MSG_PYLD_T (MSG_PYLD,MSG_QUE_ID)
  VALUES (EMPTY_CLOB(),404)
/
-- Length: 6088
-- Chunks: 2
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 404    FOR UPDATE;        
    buffer := 'rO0ABXNyAC1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLkFzeW5jaHJvbm91c0NhbGzxnQ5Y0t0lZQIAB1oAFWlnbm9yZVN0b3JlQW5kRm9yd2FyZFsACWFyZ3VtZW50c3QAE1tMamF2YS9sYW5nL09iamVjdDtMAAhjYWxsYmFja3QAM0xvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL0FzeW5jaHJvbm91c0NhbGxiYWNrO0wAB2NvbnRleHR0ABZMamF2YS9pby9TZXJpYWxpemFibGU7TAAKbWV0aG9kTmFtZXQAEkxqYXZhL2xhbmcvU3RyaW5nO1sACnBhcmFtVHlwZXN0ABJbTGphdmEvbGFuZy9DbGFzcztMAAtzZXJ2aWNlSW5mb3QAKkxvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL1NlcnZpY2VJbmZvO3hwAHVyABNbTGphdmEubGFuZy5PYmplY3Q7kM5YnxBzKWwCAAB4cAAAAAFzcgBDY29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLnBsdWdpbnMuY2x1c3RlcnN1cHBvcnQuQ2x1c3Rlck5vdGlmaWNhdGlvbhY+6VckvG9pAgACSQAEdHlwZUwABGRhdGFxAH4AA3hwAAAAAnQAI0RvY3VtZW50VHlwZVJ1bGVDYWNoZTpLdWFsaURvY3VtZW50cHB0AAZpbnZva2V1cgASW0xqYXZhLmxhbmcuQ2xhc3M7qxbXrsvNWpkCAAB4cAAAAAF2cgAUamF2YS5pby5TZXJpYWxpemFibGUQm2EN15tk7QIAAHhwc3IAKG9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuU2VydmljZUluZm/FFyO2KQS3ugIAC0wABWFsaXZldAATTGphdmEvbGFuZy9Cb29sZWFuO0wAFGVuZHBvaW50QWx0ZXJuYXRlVXJscQB+AARMAAtlbmRwb2ludFVybHEAfgAETAAKbG9ja1Zlck5icnQAE0xqYXZhL2xhbmcvSW50ZWdlcjtMAA5tZXNzYWdlRW50cnlJZHQAEExqYXZhL2xhbmcvTG9uZztMAAVxbmFtZXQAG0xqYXZheC94bWwvbmFtZXNwYWNlL1FOYW1lO0wAGnNlcmlhbGl6ZWRTZXJ2aWNlTmFtZXNwYWNlcQB+AARMAAhzZXJ2ZXJJcHEAfgAETAARc2VydmljZURlZmluaXRpb250ADBMb3JnL2t1YWxpL3JpY2Uva3NiL21lc3NhZ2luZy9TZXJ2aWNlRGVmaW5pdGlvbjtMAAtzZXJ2aWNlTmFtZXEAfgAETAAQc2VydmljZU5hbWVzcGFjZXEAfgAEeHBzcgARamF2YS5sYW5nLkJvb2xlYW7NIHKA1Zz67gIAAVoABXZhbHVleHABcHQAQGh0dHA6Ly9sb2NhbGhvc3Q6ODA4MC9rci1kZXYvcmVtb3RpbmcvT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2VzcgARamF2YS5sYW5nLkludGVnZXIS4qCk94GHOAIAAUkABXZhbHVleHIAEGphdmEubGFuZy5OdW1iZXKGrJUdC5TgiwIAAHhwAAAAAXNyAA5qYXZhLmxhbmcuTG9uZzuL5JDMjyPfAgABSgAFdmFsdWV4cQB+AB0AAAAAAAAPfXNyABlqYXZheC54bWwubmFtZXNwYWNlLlFOYW1lgW2oLfw73WwCAANMAAlsb2NhbFBhcnRxAH4ABEwADG5hbWVzcGFjZVVSSXEAfgAETAAGcHJlZml4cQB+AAR4cHQAGk9TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNldAAAcQB+ACR0B3RyTzBBQlhOeUFESnZjbWN1YTNWaGJHa3VjbWxqWlM1cmMySXViV1Z6YzJGbmFXNW5Ma3BoZG1GVFpYSjJhV05sUkdWbWFXNXBkR2x2YnJibkRWOUJOR3p4QWdBQlRBQVJjMlZ5ZG1salpVbHVkR1Z5Wm1GalpYTjBBQkJNYW1GMllTOTFkR2xzTDB4cGMzUTdlSElBTG05eVp5NXJkV0ZzYVM1eWFXTmxMbXR6WWk1dFpYTnpZV2RwYm1jdVUyVnlkbWxqWlVSbFptbHVhWFJwYjI0QW13SlBXTjBwZmdJQURVd0FDMkoxYzFObFkzVnlhWFI1ZEFBVFRHcGhkbUV2YkdGdVp5OUNiMjlzWldGdU8wd0FEMk55WldSbGJuUnBZV3h6Vkhsd1pYUUFURXh2Y21jdmEzVmhiR2t2Y21salpTOWpiM0psTDNObFkzVnlhWFI1TDJOeVpXUmxiblJwWVd4ekwwTnlaV1JsYm5ScFlXeHpVMjkxY21ObEpFTnlaV1JsYm5ScFlXeHpWSGx3WlR0TUFCQnNiMk5oYkZObGNuWnBZMlZPWVcxbGRBQVNUR3BoZG1FdmJHRnVaeTlUZEhKcGJtYzdUQUFYYldWemMyRm5aVVY0WTJWd2RHbHZia2hoYm1Sc1pYSnhBSDRBQlV3QURHMXBiR3hwYzFSdlRHbDJaWFFBRUV4cVlYWmhMMnhoYm1jdlRHOXVaenRNQUFod2NtbHZjbWwwZVhRQUUweHFZWFpoTDJ4aGJtY3ZTVzUwWldkbGNqdE1BQVZ4ZFdWMVpYRUFmZ0FEVEFBTmNtVjBjbmxCZEhSbGJYQjBjM0VBZmdBSFRBQUhjMlZ5ZG1salpYUUFFa3hxWVhaaEwyeGhibWN2VDJKcVpXTjBPMHdBRDNObGNuWnBZMlZGYm1SUWIybHVkSFFBRGt4cVlYWmhMMjVsZEM5VlVrdzdUQUFUYzJWeWRtbGpaVTVoYldWVGNHRmpaVlZTU1hFQWZnQUZUQUFRYzJWeWRtbGpaVTVoYldWemNHRmpaWEVBZmdBRlRBQUxjMlZ5ZG1salpWQmhkR2h4QUg0QUJYaHdjM0lBRVdwaGRtRXViR0Z1Wnk1Q2IyOXNaV0Z1elNCeWdOV2MrdTRDQUFGYUFBVjJZV3gxWlhod0FYQjBBQnBQVTBOaFkyaGxUbTkwYVdacFkyRjBhVzl1VTJWeWRtbGpaWFFBVFc5eVp5NXJkV0ZzYVM1eWFXTmxMbXR6WWk1dFpYTnpZV2RwYm1jdVpYaGpaWEIwYVc5dWFHRnVaR3hwYm1jdVJHVm1ZWFZzZEUxbGMzTmhaMlZGZUdObGNIUnBiMjVJWVc1a2JHVnljM0lBRG1waGRtRXViR0Z1Wnk1TWIyNW5PNHZra015UEk5OENBQUZLQUFWMllXeDFaWGh5QUJCcVlYWmhMbXhoYm1jdVRuVnRZbVZ5aHF5VkhRdVU0SXNDQUFCNGNQLy8vLy8vLy8vL2MzSUFFV3BoZG1FdWJHRnVaeTVKYm5SbFoyVnlFdUtncFBlQmh6Z0NBQUZKQUFWMllXeDFaWGh4QUg0QUVBQUFBQU56Y1FCK0FBc0FjUUIrQUJOd2MzSUFER3BoZG1FdWJtVjBMbFZTVEpZbE56WWEvT1J5QXdBSFNRQUlhR0Z6YUVOdlpHVkpBQVJ3YjNKMFRBQUpZWFYwYUc5eWFYUjVjUUIrQUFWTUFBUm1hV3hsY1FCK0FBVk1BQVJvYjNOMGNRQitBQVZNQUFod2NtOTBiMk52YkhFQWZnQUZUQUFEY21WbWNRQitBQVY0Y1AvLy8vOEFBQitRZEFBT2JHOWpZV3hvYjNOME9qZ3dPREIwQUNzdmEzSXRaR1YyTDNKbGJXOTBhVzVuTDA5VFEyRmphR1ZPYjNScFptbGpZWFJwYjI1VFpYSjJhV05sZEFBSmJHOWpZV3hvYjNOMGRBQUVhSFIwY0hCNGRBQUFkQUFHVkZKQlZrVk1kQUFCTDNOeUFCTnFZWFpoTG5WMGFXd3VRWEp5WVhsTWFYTjBlSUhTSFpuSFlaMERBQUZKQUFSemFYcGxlSEFBQUFBRmR3UUFBQUFLZEFBemIzSm5MbXQxWVd4cExuSnBZ';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 404    FOR UPDATE;        
    buffer := 'MlV1YTNOaUxtMWxjM05oWjJsdVp5NXpaWEoyYVdObExrdFRRa3BoZG1GVFpYSjJhV05sZEFBOFkyOXRMbTl3Wlc1emVXMXdhRzl1ZVM1dmMyTmhZMmhsTG1KaGMyVXVaWFpsYm5SekxrTmhZMmhsUlc1MGNubEZkbVZ1ZEV4cGMzUmxibVZ5ZEFBM1kyOXRMbTl3Wlc1emVXMXdhRzl1ZVM1dmMyTmhZMmhsTG1KaGMyVXVaWFpsYm5SekxrTmhZMmhsUlhabGJuUk1hWE4wWlc1bGNuUUFGMnBoZG1FdWRYUnBiQzVGZG1WdWRFeHBjM1JsYm1WeWRBQXNZMjl0TG05d1pXNXplVzF3YUc5dWVTNXZjMk5oWTJobExtSmhjMlV1VEdsbVpXTjVZMnhsUVhkaGNtVjR0AA0xMjkuMTg2Ljc5LjQ1c3IAMm9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuSmF2YVNlcnZpY2VEZWZpbml0aW9utucNX0E0bPECAAFMABFzZXJ2aWNlSW50ZXJmYWNlc3QAEExqYXZhL3V0aWwvTGlzdDt4cgAub3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5TZXJ2aWNlRGVmaW5pdGlvbgCbAk9Y3Sl+AgANTAALYnVzU2VjdXJpdHlxAH4AE0wAD2NyZWRlbnRpYWxzVHlwZXQATExvcmcva3VhbGkvcmljZS9jb3JlL3NlY3VyaXR5L2NyZWRlbnRpYWxzL0NyZWRlbnRpYWxzU291cmNlJENyZWRlbnRpYWxzVHlwZTtMABBsb2NhbFNlcnZpY2VOYW1lcQB+AARMABdtZXNzYWdlRXhjZXB0aW9uSGFuZGxlcnEAfgAETAAMbWlsbGlzVG9MaXZlcQB+ABVMAAhwcmlvcml0eXEAfgAUTAAFcXVldWVxAH4AE0wADXJldHJ5QXR0ZW1wdHNxAH4AFEwAB3NlcnZpY2V0ABJMamF2YS9sYW5nL09iamVjdDtMAA9zZXJ2aWNlRW5kUG9pbnR0AA5MamF2YS9uZXQvVVJMO0wAE3NlcnZpY2VOYW1lU3BhY2VVUklxAH4ABEwAEHNlcnZpY2VOYW1lc3BhY2VxAH4ABEwAC3NlcnZpY2VQYXRocQB+AAR4cHNxAH4AGQFwdAAaT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2V0AE1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLmV4Y2VwdGlvbmhhbmRsaW5nLkRlZmF1bHRNZXNzYWdlRXhjZXB0aW9uSGFuZGxlcnNxAH4AH///////////c3EAfgAcAAAAA3NxAH4AGQBxAH4AMnBzcgAMamF2YS5uZXQuVVJMliU3Nhr85HIDAAdJAAhoYXNoQ29kZUkABHBvcnRMAAlhdXRob3JpdHlxAH4ABEwABGZpbGVxAH4ABEwABGhvc3RxAH4ABEwACHByb3RvY29scQB+AARMAANyZWZxAH4ABHhwaRuioAAAH5B0AA5sb2NhbGhvc3Q6ODA4MHQAKy9rci1kZXYvcmVtb3RpbmcvT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2V0AAlsb2NhbGhvc3R0AARodHRwcHh0AAB0AAZUUkFWRUx0AAEvc3IAE2phdmEudXRpbC5BcnJheUxpc3R4gdIdmcdhnQMAAUkABHNpemV4cAAAAAV3BAAAAAp0ADNvcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLnNlcnZpY2UuS1NCSmF2YVNlcnZpY2V0ADxjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5ldmVudHMuQ2FjaGVFbnRyeUV2ZW50TGlzdGVuZXJ0ADdjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5ldmVudHMuQ2FjaGVFdmVudExpc3RlbmVydAAXamF2YS51dGlsLkV2ZW50TGlzdGVuZXJ0ACxjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5MaWZlY3ljbGVBd2FyZXhxAH4AI3QABlRSQVZFTA==';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
INSERT INTO KRSB_MSG_PYLD_T (MSG_PYLD,MSG_QUE_ID)
  VALUES (EMPTY_CLOB(),405)
/
-- Length: 6084
-- Chunks: 2
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 405    FOR UPDATE;        
    buffer := 'rO0ABXNyAC1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLkFzeW5jaHJvbm91c0NhbGzxnQ5Y0t0lZQIAB1oAFWlnbm9yZVN0b3JlQW5kRm9yd2FyZFsACWFyZ3VtZW50c3QAE1tMamF2YS9sYW5nL09iamVjdDtMAAhjYWxsYmFja3QAM0xvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL0FzeW5jaHJvbm91c0NhbGxiYWNrO0wAB2NvbnRleHR0ABZMamF2YS9pby9TZXJpYWxpemFibGU7TAAKbWV0aG9kTmFtZXQAEkxqYXZhL2xhbmcvU3RyaW5nO1sACnBhcmFtVHlwZXN0ABJbTGphdmEvbGFuZy9DbGFzcztMAAtzZXJ2aWNlSW5mb3QAKkxvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL1NlcnZpY2VJbmZvO3hwAHVyABNbTGphdmEubGFuZy5PYmplY3Q7kM5YnxBzKWwCAAB4cAAAAAFzcgBDY29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLnBsdWdpbnMuY2x1c3RlcnN1cHBvcnQuQ2x1c3Rlck5vdGlmaWNhdGlvbhY+6VckvG9pAgACSQAEdHlwZUwABGRhdGFxAH4AA3hwAAAAAnQAIkRvY3VtZW50VHlwZVJ1bGVDYWNoZTpSaWNlRG9jdW1lbnRwcHQABmludm9rZXVyABJbTGphdmEubGFuZy5DbGFzczurFteuy81amQIAAHhwAAAAAXZyABRqYXZhLmlvLlNlcmlhbGl6YWJsZRCbYQ3Xm2TtAgAAeHBzcgAob3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5TZXJ2aWNlSW5mb8UXI7YpBLe6AgALTAAFYWxpdmV0ABNMamF2YS9sYW5nL0Jvb2xlYW47TAAUZW5kcG9pbnRBbHRlcm5hdGVVcmxxAH4ABEwAC2VuZHBvaW50VXJscQB+AARMAApsb2NrVmVyTmJydAATTGphdmEvbGFuZy9JbnRlZ2VyO0wADm1lc3NhZ2VFbnRyeUlkdAAQTGphdmEvbGFuZy9Mb25nO0wABXFuYW1ldAAbTGphdmF4L3htbC9uYW1lc3BhY2UvUU5hbWU7TAAac2VyaWFsaXplZFNlcnZpY2VOYW1lc3BhY2VxAH4ABEwACHNlcnZlcklwcQB+AARMABFzZXJ2aWNlRGVmaW5pdGlvbnQAMExvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL1NlcnZpY2VEZWZpbml0aW9uO0wAC3NlcnZpY2VOYW1lcQB+AARMABBzZXJ2aWNlTmFtZXNwYWNlcQB+AAR4cHNyABFqYXZhLmxhbmcuQm9vbGVhbs0gcoDVnPruAgABWgAFdmFsdWV4cAFwdABAaHR0cDovL2xvY2FsaG9zdDo4MDgwL2tyLWRldi9yZW1vdGluZy9PU0NhY2hlTm90aWZpY2F0aW9uU2VydmljZXNyABFqYXZhLmxhbmcuSW50ZWdlchLioKT3gYc4AgABSQAFdmFsdWV4cgAQamF2YS5sYW5nLk51bWJlcoaslR0LlOCLAgAAeHAAAAABc3IADmphdmEubGFuZy5Mb25nO4vkkMyPI98CAAFKAAV2YWx1ZXhxAH4AHQAAAAAAAA99c3IAGWphdmF4LnhtbC5uYW1lc3BhY2UuUU5hbWWBbagt/DvdbAIAA0wACWxvY2FsUGFydHEAfgAETAAMbmFtZXNwYWNlVVJJcQB+AARMAAZwcmVmaXhxAH4ABHhwdAAaT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2V0AABxAH4AJHQHdHJPMEFCWE55QURKdmNtY3VhM1ZoYkdrdWNtbGpaUzVyYzJJdWJXVnpjMkZuYVc1bkxrcGhkbUZUWlhKMmFXTmxSR1ZtYVc1cGRHbHZicmJuRFY5Qk5HenhBZ0FCVEFBUmMyVnlkbWxqWlVsdWRHVnlabUZqWlhOMEFCQk1hbUYyWVM5MWRHbHNMMHhwYzNRN2VISUFMbTl5Wnk1cmRXRnNhUzV5YVdObExtdHpZaTV0WlhOellXZHBibWN1VTJWeWRtbGpaVVJsWm1sdWFYUnBiMjRBbXdKUFdOMHBmZ0lBRFV3QUMySjFjMU5sWTNWeWFYUjVkQUFUVEdwaGRtRXZiR0Z1Wnk5Q2IyOXNaV0Z1TzB3QUQyTnlaV1JsYm5ScFlXeHpWSGx3WlhRQVRFeHZjbWN2YTNWaGJHa3ZjbWxqWlM5amIzSmxMM05sWTNWeWFYUjVMMk55WldSbGJuUnBZV3h6TDBOeVpXUmxiblJwWVd4elUyOTFjbU5sSkVOeVpXUmxiblJwWVd4elZIbHdaVHRNQUJCc2IyTmhiRk5sY25acFkyVk9ZVzFsZEFBU1RHcGhkbUV2YkdGdVp5OVRkSEpwYm1jN1RBQVhiV1Z6YzJGblpVVjRZMlZ3ZEdsdmJraGhibVJzWlhKeEFINEFCVXdBREcxcGJHeHBjMVJ2VEdsMlpYUUFFRXhxWVhaaEwyeGhibWN2VEc5dVp6dE1BQWh3Y21sdmNtbDBlWFFBRTB4cVlYWmhMMnhoYm1jdlNXNTBaV2RsY2p0TUFBVnhkV1YxWlhFQWZnQURUQUFOY21WMGNubEJkSFJsYlhCMGMzRUFmZ0FIVEFBSGMyVnlkbWxqWlhRQUVreHFZWFpoTDJ4aGJtY3ZUMkpxWldOME8wd0FEM05sY25acFkyVkZibVJRYjJsdWRIUUFEa3hxWVhaaEwyNWxkQzlWVWt3N1RBQVRjMlZ5ZG1salpVNWhiV1ZUY0dGalpWVlNTWEVBZmdBRlRBQVFjMlZ5ZG1salpVNWhiV1Z6Y0dGalpYRUFmZ0FGVEFBTGMyVnlkbWxqWlZCaGRHaHhBSDRBQlhod2MzSUFFV3BoZG1FdWJHRnVaeTVDYjI5c1pXRnV6U0J5Z05XYyt1NENBQUZhQUFWMllXeDFaWGh3QVhCMEFCcFBVME5oWTJobFRtOTBhV1pwWTJGMGFXOXVVMlZ5ZG1salpYUUFUVzl5Wnk1cmRXRnNhUzV5YVdObExtdHpZaTV0WlhOellXZHBibWN1WlhoalpYQjBhVzl1YUdGdVpHeHBibWN1UkdWbVlYVnNkRTFsYzNOaFoyVkZlR05sY0hScGIyNUlZVzVrYkdWeWMzSUFEbXBoZG1FdWJHRnVaeTVNYjI1bk80dmtrTXlQSTk4Q0FBRktBQVYyWVd4MVpYaHlBQkJxWVhaaExteGhibWN1VG5WdFltVnlocXlWSFF1VTRJc0NBQUI0Y1AvLy8vLy8vLy8vYzNJQUVXcGhkbUV1YkdGdVp5NUpiblJsWjJWeUV1S2dwUGVCaHpnQ0FBRkpBQVYyWVd4MVpYaHhBSDRBRUFBQUFBTnpjUUIrQUFzQWNRQitBQk53YzNJQURHcGhkbUV1Ym1WMExsVlNUSllsTnpZYS9PUnlBd0FIU1FBSWFHRnphRU52WkdWSkFBUndiM0owVEFBSllYVjBhRzl5YVhSNWNRQitBQVZNQUFSbWFXeGxjUUIrQUFWTUFBUm9iM04wY1FCK0FBVk1BQWh3Y205MGIyTnZiSEVBZmdBRlRBQURjbVZtY1FCK0FBVjRjUC8vLy84QUFCK1FkQUFPYkc5allXeG9iM04wT2pnd09EQjBBQ3N2YTNJdFpHVjJMM0psYlc5MGFXNW5MMDlUUTJGamFHVk9iM1JwWm1sallYUnBiMjVUWlhKMmFXTmxkQUFKYkc5allXeG9iM04wZEFBRWFIUjBjSEI0ZEFBQWRBQUdWRkpCVmtWTWRBQUJMM055QUJOcVlYWmhMblYwYVd3dVFYSnlZWGxNYVhOMGVJSFNIWm5IWVowREFBRkpBQVJ6YVhwbGVIQUFBQUFGZHdRQUFBQUtkQUF6YjNKbkxtdDFZV3hwTG5KcFky';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 405    FOR UPDATE;        
    buffer := 'VXVhM05pTG0xbGMzTmhaMmx1Wnk1elpYSjJhV05sTGt0VFFrcGhkbUZUWlhKMmFXTmxkQUE4WTI5dExtOXdaVzV6ZVcxd2FHOXVlUzV2YzJOaFkyaGxMbUpoYzJVdVpYWmxiblJ6TGtOaFkyaGxSVzUwY25sRmRtVnVkRXhwYzNSbGJtVnlkQUEzWTI5dExtOXdaVzV6ZVcxd2FHOXVlUzV2YzJOaFkyaGxMbUpoYzJVdVpYWmxiblJ6TGtOaFkyaGxSWFpsYm5STWFYTjBaVzVsY25RQUYycGhkbUV1ZFhScGJDNUZkbVZ1ZEV4cGMzUmxibVZ5ZEFBc1kyOXRMbTl3Wlc1emVXMXdhRzl1ZVM1dmMyTmhZMmhsTG1KaGMyVXVUR2xtWldONVkyeGxRWGRoY21WNHQADTEyOS4xODYuNzkuNDVzcgAyb3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5KYXZhU2VydmljZURlZmluaXRpb2625w1fQTRs8QIAAUwAEXNlcnZpY2VJbnRlcmZhY2VzdAAQTGphdmEvdXRpbC9MaXN0O3hyAC5vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLlNlcnZpY2VEZWZpbml0aW9uAJsCT1jdKX4CAA1MAAtidXNTZWN1cml0eXEAfgATTAAPY3JlZGVudGlhbHNUeXBldABMTG9yZy9rdWFsaS9yaWNlL2NvcmUvc2VjdXJpdHkvY3JlZGVudGlhbHMvQ3JlZGVudGlhbHNTb3VyY2UkQ3JlZGVudGlhbHNUeXBlO0wAEGxvY2FsU2VydmljZU5hbWVxAH4ABEwAF21lc3NhZ2VFeGNlcHRpb25IYW5kbGVycQB+AARMAAxtaWxsaXNUb0xpdmVxAH4AFUwACHByaW9yaXR5cQB+ABRMAAVxdWV1ZXEAfgATTAANcmV0cnlBdHRlbXB0c3EAfgAUTAAHc2VydmljZXQAEkxqYXZhL2xhbmcvT2JqZWN0O0wAD3NlcnZpY2VFbmRQb2ludHQADkxqYXZhL25ldC9VUkw7TAATc2VydmljZU5hbWVTcGFjZVVSSXEAfgAETAAQc2VydmljZU5hbWVzcGFjZXEAfgAETAALc2VydmljZVBhdGhxAH4ABHhwc3EAfgAZAXB0ABpPU0NhY2hlTm90aWZpY2F0aW9uU2VydmljZXQATW9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuZXhjZXB0aW9uaGFuZGxpbmcuRGVmYXVsdE1lc3NhZ2VFeGNlcHRpb25IYW5kbGVyc3EAfgAf//////////9zcQB+ABwAAAADc3EAfgAZAHEAfgAycHNyAAxqYXZhLm5ldC5VUkyWJTc2GvzkcgMAB0kACGhhc2hDb2RlSQAEcG9ydEwACWF1dGhvcml0eXEAfgAETAAEZmlsZXEAfgAETAAEaG9zdHEAfgAETAAIcHJvdG9jb2xxAH4ABEwAA3JlZnEAfgAEeHBpG6KgAAAfkHQADmxvY2FsaG9zdDo4MDgwdAArL2tyLWRldi9yZW1vdGluZy9PU0NhY2hlTm90aWZpY2F0aW9uU2VydmljZXQACWxvY2FsaG9zdHQABGh0dHBweHQAAHQABlRSQVZFTHQAAS9zcgATamF2YS51dGlsLkFycmF5TGlzdHiB0h2Zx2GdAwABSQAEc2l6ZXhwAAAABXcEAAAACnQAM29yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuc2VydmljZS5LU0JKYXZhU2VydmljZXQAPGNvbS5vcGVuc3ltcGhvbnkub3NjYWNoZS5iYXNlLmV2ZW50cy5DYWNoZUVudHJ5RXZlbnRMaXN0ZW5lcnQAN2NvbS5vcGVuc3ltcGhvbnkub3NjYWNoZS5iYXNlLmV2ZW50cy5DYWNoZUV2ZW50TGlzdGVuZXJ0ABdqYXZhLnV0aWwuRXZlbnRMaXN0ZW5lcnQALGNvbS5vcGVuc3ltcGhvbnkub3NjYWNoZS5iYXNlLkxpZmVjeWNsZUF3YXJleHEAfgAjdAAGVFJBVkVM';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
INSERT INTO KRSB_MSG_PYLD_T (MSG_PYLD,MSG_QUE_ID)
  VALUES (EMPTY_CLOB(),406)
/
-- Length: 6096
-- Chunks: 2
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 406    FOR UPDATE;        
    buffer := 'rO0ABXNyAC1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLkFzeW5jaHJvbm91c0NhbGzxnQ5Y0t0lZQIAB1oAFWlnbm9yZVN0b3JlQW5kRm9yd2FyZFsACWFyZ3VtZW50c3QAE1tMamF2YS9sYW5nL09iamVjdDtMAAhjYWxsYmFja3QAM0xvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL0FzeW5jaHJvbm91c0NhbGxiYWNrO0wAB2NvbnRleHR0ABZMamF2YS9pby9TZXJpYWxpemFibGU7TAAKbWV0aG9kTmFtZXQAEkxqYXZhL2xhbmcvU3RyaW5nO1sACnBhcmFtVHlwZXN0ABJbTGphdmEvbGFuZy9DbGFzcztMAAtzZXJ2aWNlSW5mb3QAKkxvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL1NlcnZpY2VJbmZvO3hwAHVyABNbTGphdmEubGFuZy5PYmplY3Q7kM5YnxBzKWwCAAB4cAAAAAFzcgBDY29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLnBsdWdpbnMuY2x1c3RlcnN1cHBvcnQuQ2x1c3Rlck5vdGlmaWNhdGlvbhY+6VckvG9pAgACSQAEdHlwZUwABGRhdGFxAH4AA3hwAAAAAnQAKkRvY3VtZW50VHlwZVJ1bGVDYWNoZTpEb2N1bWVudFR5cGVEb2N1bWVudHBwdAAGaW52b2tldXIAEltMamF2YS5sYW5nLkNsYXNzO6sW167LzVqZAgAAeHAAAAABdnIAFGphdmEuaW8uU2VyaWFsaXphYmxlEJthDdebZO0CAAB4cHNyAChvcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLlNlcnZpY2VJbmZvxRcjtikEt7oCAAtMAAVhbGl2ZXQAE0xqYXZhL2xhbmcvQm9vbGVhbjtMABRlbmRwb2ludEFsdGVybmF0ZVVybHEAfgAETAALZW5kcG9pbnRVcmxxAH4ABEwACmxvY2tWZXJOYnJ0ABNMamF2YS9sYW5nL0ludGVnZXI7TAAObWVzc2FnZUVudHJ5SWR0ABBMamF2YS9sYW5nL0xvbmc7TAAFcW5hbWV0ABtMamF2YXgveG1sL25hbWVzcGFjZS9RTmFtZTtMABpzZXJpYWxpemVkU2VydmljZU5hbWVzcGFjZXEAfgAETAAIc2VydmVySXBxAH4ABEwAEXNlcnZpY2VEZWZpbml0aW9udAAwTG9yZy9rdWFsaS9yaWNlL2tzYi9tZXNzYWdpbmcvU2VydmljZURlZmluaXRpb247TAALc2VydmljZU5hbWVxAH4ABEwAEHNlcnZpY2VOYW1lc3BhY2VxAH4ABHhwc3IAEWphdmEubGFuZy5Cb29sZWFuzSBygNWc+u4CAAFaAAV2YWx1ZXhwAXB0AEBodHRwOi8vbG9jYWxob3N0OjgwODAva3ItZGV2L3JlbW90aW5nL09TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNlc3IAEWphdmEubGFuZy5JbnRlZ2VyEuKgpPeBhzgCAAFJAAV2YWx1ZXhyABBqYXZhLmxhbmcuTnVtYmVyhqyVHQuU4IsCAAB4cAAAAAFzcgAOamF2YS5sYW5nLkxvbmc7i+SQzI8j3wIAAUoABXZhbHVleHEAfgAdAAAAAAAAD31zcgAZamF2YXgueG1sLm5hbWVzcGFjZS5RTmFtZYFtqC38O91sAgADTAAJbG9jYWxQYXJ0cQB+AARMAAxuYW1lc3BhY2VVUklxAH4ABEwABnByZWZpeHEAfgAEeHB0ABpPU0NhY2hlTm90aWZpY2F0aW9uU2VydmljZXQAAHEAfgAkdAd0ck8wQUJYTnlBREp2Y21jdWEzVmhiR2t1Y21salpTNXJjMkl1YldWemMyRm5hVzVuTGtwaGRtRlRaWEoyYVdObFJHVm1hVzVwZEdsdmJyYm5EVjlCTkd6eEFnQUJUQUFSYzJWeWRtbGpaVWx1ZEdWeVptRmpaWE4wQUJCTWFtRjJZUzkxZEdsc0wweHBjM1E3ZUhJQUxtOXlaeTVyZFdGc2FTNXlhV05sTG10ellpNXRaWE56WVdkcGJtY3VVMlZ5ZG1salpVUmxabWx1YVhScGIyNEFtd0pQV04wcGZnSUFEVXdBQzJKMWMxTmxZM1Z5YVhSNWRBQVRUR3BoZG1FdmJHRnVaeTlDYjI5c1pXRnVPMHdBRDJOeVpXUmxiblJwWVd4elZIbHdaWFFBVEV4dmNtY3ZhM1ZoYkdrdmNtbGpaUzlqYjNKbEwzTmxZM1Z5YVhSNUwyTnlaV1JsYm5ScFlXeHpMME55WldSbGJuUnBZV3h6VTI5MWNtTmxKRU55WldSbGJuUnBZV3h6Vkhsd1pUdE1BQkJzYjJOaGJGTmxjblpwWTJWT1lXMWxkQUFTVEdwaGRtRXZiR0Z1Wnk5VGRISnBibWM3VEFBWGJXVnpjMkZuWlVWNFkyVndkR2x2YmtoaGJtUnNaWEp4QUg0QUJVd0FERzFwYkd4cGMxUnZUR2wyWlhRQUVFeHFZWFpoTDJ4aGJtY3ZURzl1Wnp0TUFBaHdjbWx2Y21sMGVYUUFFMHhxWVhaaEwyeGhibWN2U1c1MFpXZGxjanRNQUFWeGRXVjFaWEVBZmdBRFRBQU5jbVYwY25sQmRIUmxiWEIwYzNFQWZnQUhUQUFIYzJWeWRtbGpaWFFBRWt4cVlYWmhMMnhoYm1jdlQySnFaV04wTzB3QUQzTmxjblpwWTJWRmJtUlFiMmx1ZEhRQURreHFZWFpoTDI1bGRDOVZVa3c3VEFBVGMyVnlkbWxqWlU1aGJXVlRjR0ZqWlZWU1NYRUFmZ0FGVEFBUWMyVnlkbWxqWlU1aGJXVnpjR0ZqWlhFQWZnQUZUQUFMYzJWeWRtbGpaVkJoZEdoeEFINEFCWGh3YzNJQUVXcGhkbUV1YkdGdVp5NUNiMjlzWldGdXpTQnlnTldjK3U0Q0FBRmFBQVYyWVd4MVpYaHdBWEIwQUJwUFUwTmhZMmhsVG05MGFXWnBZMkYwYVc5dVUyVnlkbWxqWlhRQVRXOXlaeTVyZFdGc2FTNXlhV05sTG10ellpNXRaWE56WVdkcGJtY3VaWGhqWlhCMGFXOXVhR0Z1Wkd4cGJtY3VSR1ZtWVhWc2RFMWxjM05oWjJWRmVHTmxjSFJwYjI1SVlXNWtiR1Z5YzNJQURtcGhkbUV1YkdGdVp5NU1iMjVuTzR2a2tNeVBJOThDQUFGS0FBVjJZV3gxWlhoeUFCQnFZWFpoTG14aGJtY3VUblZ0WW1WeWhxeVZIUXVVNElzQ0FBQjRjUC8vLy8vLy8vLy9jM0lBRVdwaGRtRXViR0Z1Wnk1SmJuUmxaMlZ5RXVLZ3BQZUJoemdDQUFGSkFBVjJZV3gxWlhoeEFINEFFQUFBQUFOemNRQitBQXNBY1FCK0FCTndjM0lBREdwaGRtRXVibVYwTGxWU1RKWWxOellhL09SeUF3QUhTUUFJYUdGemFFTnZaR1ZKQUFSd2IzSjBUQUFKWVhWMGFHOXlhWFI1Y1FCK0FBVk1BQVJtYVd4bGNRQitBQVZNQUFSb2IzTjBjUUIrQUFWTUFBaHdjbTkwYjJOdmJIRUFmZ0FGVEFBRGNtVm1jUUIrQUFWNGNQLy8vLzhBQUIrUWRBQU9iRzlqWVd4b2IzTjBPamd3T0RCMEFDc3ZhM0l0WkdWMkwzSmxiVzkwYVc1bkwwOVRRMkZqYUdWT2IzUnBabWxqWVhScGIyNVRaWEoyYVdObGRBQUpiRzlqWVd4b2IzTjBkQUFFYUhSMGNIQjRkQUFBZEFBR1ZGSkJWa1ZNZEFBQkwzTnlBQk5xWVhaaExuVjBhV3d1UVhKeVlYbE1hWE4wZUlIU0habkhZWjBEQUFGSkFBUnphWHBsZUhBQUFBQUZkd1FBQUFBS2RBQXpiM0puTG10MVlX';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 406    FOR UPDATE;        
    buffer := 'eHBMbkpwWTJVdWEzTmlMbTFsYzNOaFoybHVaeTV6WlhKMmFXTmxMa3RUUWtwaGRtRlRaWEoyYVdObGRBQThZMjl0TG05d1pXNXplVzF3YUc5dWVTNXZjMk5oWTJobExtSmhjMlV1WlhabGJuUnpMa05oWTJobFJXNTBjbmxGZG1WdWRFeHBjM1JsYm1WeWRBQTNZMjl0TG05d1pXNXplVzF3YUc5dWVTNXZjMk5oWTJobExtSmhjMlV1WlhabGJuUnpMa05oWTJobFJYWmxiblJNYVhOMFpXNWxjblFBRjJwaGRtRXVkWFJwYkM1RmRtVnVkRXhwYzNSbGJtVnlkQUFzWTI5dExtOXdaVzV6ZVcxd2FHOXVlUzV2YzJOaFkyaGxMbUpoYzJVdVRHbG1aV041WTJ4bFFYZGhjbVY0dAANMTI5LjE4Ni43OS40NXNyADJvcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLkphdmFTZXJ2aWNlRGVmaW5pdGlvbrbnDV9BNGzxAgABTAARc2VydmljZUludGVyZmFjZXN0ABBMamF2YS91dGlsL0xpc3Q7eHIALm9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuU2VydmljZURlZmluaXRpb24AmwJPWN0pfgIADUwAC2J1c1NlY3VyaXR5cQB+ABNMAA9jcmVkZW50aWFsc1R5cGV0AExMb3JnL2t1YWxpL3JpY2UvY29yZS9zZWN1cml0eS9jcmVkZW50aWFscy9DcmVkZW50aWFsc1NvdXJjZSRDcmVkZW50aWFsc1R5cGU7TAAQbG9jYWxTZXJ2aWNlTmFtZXEAfgAETAAXbWVzc2FnZUV4Y2VwdGlvbkhhbmRsZXJxAH4ABEwADG1pbGxpc1RvTGl2ZXEAfgAVTAAIcHJpb3JpdHlxAH4AFEwABXF1ZXVlcQB+ABNMAA1yZXRyeUF0dGVtcHRzcQB+ABRMAAdzZXJ2aWNldAASTGphdmEvbGFuZy9PYmplY3Q7TAAPc2VydmljZUVuZFBvaW50dAAOTGphdmEvbmV0L1VSTDtMABNzZXJ2aWNlTmFtZVNwYWNlVVJJcQB+AARMABBzZXJ2aWNlTmFtZXNwYWNlcQB+AARMAAtzZXJ2aWNlUGF0aHEAfgAEeHBzcQB+ABkBcHQAGk9TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNldABNb3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5leGNlcHRpb25oYW5kbGluZy5EZWZhdWx0TWVzc2FnZUV4Y2VwdGlvbkhhbmRsZXJzcQB+AB///////////3NxAH4AHAAAAANzcQB+ABkAcQB+ADJwc3IADGphdmEubmV0LlVSTJYlNzYa/ORyAwAHSQAIaGFzaENvZGVJAARwb3J0TAAJYXV0aG9yaXR5cQB+AARMAARmaWxlcQB+AARMAARob3N0cQB+AARMAAhwcm90b2NvbHEAfgAETAADcmVmcQB+AAR4cGkboqAAAB+QdAAObG9jYWxob3N0OjgwODB0ACsva3ItZGV2L3JlbW90aW5nL09TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNldAAJbG9jYWxob3N0dAAEaHR0cHB4dAAAdAAGVFJBVkVMdAABL3NyABNqYXZhLnV0aWwuQXJyYXlMaXN0eIHSHZnHYZ0DAAFJAARzaXpleHAAAAAFdwQAAAAKdAAzb3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5zZXJ2aWNlLktTQkphdmFTZXJ2aWNldAA8Y29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLmJhc2UuZXZlbnRzLkNhY2hlRW50cnlFdmVudExpc3RlbmVydAA3Y29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLmJhc2UuZXZlbnRzLkNhY2hlRXZlbnRMaXN0ZW5lcnQAF2phdmEudXRpbC5FdmVudExpc3RlbmVydAAsY29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLmJhc2UuTGlmZWN5Y2xlQXdhcmV4cQB+ACN0AAZUUkFWRUw=';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
INSERT INTO KRSB_MSG_PYLD_T (MSG_PYLD,MSG_QUE_ID)
  VALUES (EMPTY_CLOB(),407)
/
-- Length: 6096
-- Chunks: 2
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 407    FOR UPDATE;        
    buffer := 'rO0ABXNyAC1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLkFzeW5jaHJvbm91c0NhbGzxnQ5Y0t0lZQIAB1oAFWlnbm9yZVN0b3JlQW5kRm9yd2FyZFsACWFyZ3VtZW50c3QAE1tMamF2YS9sYW5nL09iamVjdDtMAAhjYWxsYmFja3QAM0xvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL0FzeW5jaHJvbm91c0NhbGxiYWNrO0wAB2NvbnRleHR0ABZMamF2YS9pby9TZXJpYWxpemFibGU7TAAKbWV0aG9kTmFtZXQAEkxqYXZhL2xhbmcvU3RyaW5nO1sACnBhcmFtVHlwZXN0ABJbTGphdmEvbGFuZy9DbGFzcztMAAtzZXJ2aWNlSW5mb3QAKkxvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL1NlcnZpY2VJbmZvO3hwAHVyABNbTGphdmEubGFuZy5PYmplY3Q7kM5YnxBzKWwCAAB4cAAAAAFzcgBDY29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLnBsdWdpbnMuY2x1c3RlcnN1cHBvcnQuQ2x1c3Rlck5vdGlmaWNhdGlvbhY+6VckvG9pAgACSQAEdHlwZUwABGRhdGFxAH4AA3hwAAAAAnQAKURvY3VtZW50VHlwZVJ1bGVDYWNoZTpSb3V0aW5nUnVsZURvY3VtZW50cHB0AAZpbnZva2V1cgASW0xqYXZhLmxhbmcuQ2xhc3M7qxbXrsvNWpkCAAB4cAAAAAF2cgAUamF2YS5pby5TZXJpYWxpemFibGUQm2EN15tk7QIAAHhwc3IAKG9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuU2VydmljZUluZm/FFyO2KQS3ugIAC0wABWFsaXZldAATTGphdmEvbGFuZy9Cb29sZWFuO0wAFGVuZHBvaW50QWx0ZXJuYXRlVXJscQB+AARMAAtlbmRwb2ludFVybHEAfgAETAAKbG9ja1Zlck5icnQAE0xqYXZhL2xhbmcvSW50ZWdlcjtMAA5tZXNzYWdlRW50cnlJZHQAEExqYXZhL2xhbmcvTG9uZztMAAVxbmFtZXQAG0xqYXZheC94bWwvbmFtZXNwYWNlL1FOYW1lO0wAGnNlcmlhbGl6ZWRTZXJ2aWNlTmFtZXNwYWNlcQB+AARMAAhzZXJ2ZXJJcHEAfgAETAARc2VydmljZURlZmluaXRpb250ADBMb3JnL2t1YWxpL3JpY2Uva3NiL21lc3NhZ2luZy9TZXJ2aWNlRGVmaW5pdGlvbjtMAAtzZXJ2aWNlTmFtZXEAfgAETAAQc2VydmljZU5hbWVzcGFjZXEAfgAEeHBzcgARamF2YS5sYW5nLkJvb2xlYW7NIHKA1Zz67gIAAVoABXZhbHVleHABcHQAQGh0dHA6Ly9sb2NhbGhvc3Q6ODA4MC9rci1kZXYvcmVtb3RpbmcvT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2VzcgARamF2YS5sYW5nLkludGVnZXIS4qCk94GHOAIAAUkABXZhbHVleHIAEGphdmEubGFuZy5OdW1iZXKGrJUdC5TgiwIAAHhwAAAAAXNyAA5qYXZhLmxhbmcuTG9uZzuL5JDMjyPfAgABSgAFdmFsdWV4cQB+AB0AAAAAAAAPfXNyABlqYXZheC54bWwubmFtZXNwYWNlLlFOYW1lgW2oLfw73WwCAANMAAlsb2NhbFBhcnRxAH4ABEwADG5hbWVzcGFjZVVSSXEAfgAETAAGcHJlZml4cQB+AAR4cHQAGk9TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNldAAAcQB+ACR0B3RyTzBBQlhOeUFESnZjbWN1YTNWaGJHa3VjbWxqWlM1cmMySXViV1Z6YzJGbmFXNW5Ma3BoZG1GVFpYSjJhV05sUkdWbWFXNXBkR2x2YnJibkRWOUJOR3p4QWdBQlRBQVJjMlZ5ZG1salpVbHVkR1Z5Wm1GalpYTjBBQkJNYW1GMllTOTFkR2xzTDB4cGMzUTdlSElBTG05eVp5NXJkV0ZzYVM1eWFXTmxMbXR6WWk1dFpYTnpZV2RwYm1jdVUyVnlkbWxqWlVSbFptbHVhWFJwYjI0QW13SlBXTjBwZmdJQURVd0FDMkoxYzFObFkzVnlhWFI1ZEFBVFRHcGhkbUV2YkdGdVp5OUNiMjlzWldGdU8wd0FEMk55WldSbGJuUnBZV3h6Vkhsd1pYUUFURXh2Y21jdmEzVmhiR2t2Y21salpTOWpiM0psTDNObFkzVnlhWFI1TDJOeVpXUmxiblJwWVd4ekwwTnlaV1JsYm5ScFlXeHpVMjkxY21ObEpFTnlaV1JsYm5ScFlXeHpWSGx3WlR0TUFCQnNiMk5oYkZObGNuWnBZMlZPWVcxbGRBQVNUR3BoZG1FdmJHRnVaeTlUZEhKcGJtYzdUQUFYYldWemMyRm5aVVY0WTJWd2RHbHZia2hoYm1Sc1pYSnhBSDRBQlV3QURHMXBiR3hwYzFSdlRHbDJaWFFBRUV4cVlYWmhMMnhoYm1jdlRHOXVaenRNQUFod2NtbHZjbWwwZVhRQUUweHFZWFpoTDJ4aGJtY3ZTVzUwWldkbGNqdE1BQVZ4ZFdWMVpYRUFmZ0FEVEFBTmNtVjBjbmxCZEhSbGJYQjBjM0VBZmdBSFRBQUhjMlZ5ZG1salpYUUFFa3hxWVhaaEwyeGhibWN2VDJKcVpXTjBPMHdBRDNObGNuWnBZMlZGYm1SUWIybHVkSFFBRGt4cVlYWmhMMjVsZEM5VlVrdzdUQUFUYzJWeWRtbGpaVTVoYldWVGNHRmpaVlZTU1hFQWZnQUZUQUFRYzJWeWRtbGpaVTVoYldWemNHRmpaWEVBZmdBRlRBQUxjMlZ5ZG1salpWQmhkR2h4QUg0QUJYaHdjM0lBRVdwaGRtRXViR0Z1Wnk1Q2IyOXNaV0Z1elNCeWdOV2MrdTRDQUFGYUFBVjJZV3gxWlhod0FYQjBBQnBQVTBOaFkyaGxUbTkwYVdacFkyRjBhVzl1VTJWeWRtbGpaWFFBVFc5eVp5NXJkV0ZzYVM1eWFXTmxMbXR6WWk1dFpYTnpZV2RwYm1jdVpYaGpaWEIwYVc5dWFHRnVaR3hwYm1jdVJHVm1ZWFZzZEUxbGMzTmhaMlZGZUdObGNIUnBiMjVJWVc1a2JHVnljM0lBRG1waGRtRXViR0Z1Wnk1TWIyNW5PNHZra015UEk5OENBQUZLQUFWMllXeDFaWGh5QUJCcVlYWmhMbXhoYm1jdVRuVnRZbVZ5aHF5VkhRdVU0SXNDQUFCNGNQLy8vLy8vLy8vL2MzSUFFV3BoZG1FdWJHRnVaeTVKYm5SbFoyVnlFdUtncFBlQmh6Z0NBQUZKQUFWMllXeDFaWGh4QUg0QUVBQUFBQU56Y1FCK0FBc0FjUUIrQUJOd2MzSUFER3BoZG1FdWJtVjBMbFZTVEpZbE56WWEvT1J5QXdBSFNRQUlhR0Z6YUVOdlpHVkpBQVJ3YjNKMFRBQUpZWFYwYUc5eWFYUjVjUUIrQUFWTUFBUm1hV3hsY1FCK0FBVk1BQVJvYjNOMGNRQitBQVZNQUFod2NtOTBiMk52YkhFQWZnQUZUQUFEY21WbWNRQitBQVY0Y1AvLy8vOEFBQitRZEFBT2JHOWpZV3hvYjNOME9qZ3dPREIwQUNzdmEzSXRaR1YyTDNKbGJXOTBhVzVuTDA5VFEyRmphR1ZPYjNScFptbGpZWFJwYjI1VFpYSjJhV05sZEFBSmJHOWpZV3hvYjNOMGRBQUVhSFIwY0hCNGRBQUFkQUFHVkZKQlZrVk1kQUFCTDNOeUFCTnFZWFpoTG5WMGFXd3VRWEp5WVhsTWFYTjBlSUhTSFpuSFlaMERBQUZKQUFSemFYcGxlSEFBQUFBRmR3UUFBQUFLZEFBemIzSm5MbXQxWVd4';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 407    FOR UPDATE;        
    buffer := 'cExuSnBZMlV1YTNOaUxtMWxjM05oWjJsdVp5NXpaWEoyYVdObExrdFRRa3BoZG1GVFpYSjJhV05sZEFBOFkyOXRMbTl3Wlc1emVXMXdhRzl1ZVM1dmMyTmhZMmhsTG1KaGMyVXVaWFpsYm5SekxrTmhZMmhsUlc1MGNubEZkbVZ1ZEV4cGMzUmxibVZ5ZEFBM1kyOXRMbTl3Wlc1emVXMXdhRzl1ZVM1dmMyTmhZMmhsTG1KaGMyVXVaWFpsYm5SekxrTmhZMmhsUlhabGJuUk1hWE4wWlc1bGNuUUFGMnBoZG1FdWRYUnBiQzVGZG1WdWRFeHBjM1JsYm1WeWRBQXNZMjl0TG05d1pXNXplVzF3YUc5dWVTNXZjMk5oWTJobExtSmhjMlV1VEdsbVpXTjVZMnhsUVhkaGNtVjR0AA0xMjkuMTg2Ljc5LjQ1c3IAMm9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuSmF2YVNlcnZpY2VEZWZpbml0aW9utucNX0E0bPECAAFMABFzZXJ2aWNlSW50ZXJmYWNlc3QAEExqYXZhL3V0aWwvTGlzdDt4cgAub3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5TZXJ2aWNlRGVmaW5pdGlvbgCbAk9Y3Sl+AgANTAALYnVzU2VjdXJpdHlxAH4AE0wAD2NyZWRlbnRpYWxzVHlwZXQATExvcmcva3VhbGkvcmljZS9jb3JlL3NlY3VyaXR5L2NyZWRlbnRpYWxzL0NyZWRlbnRpYWxzU291cmNlJENyZWRlbnRpYWxzVHlwZTtMABBsb2NhbFNlcnZpY2VOYW1lcQB+AARMABdtZXNzYWdlRXhjZXB0aW9uSGFuZGxlcnEAfgAETAAMbWlsbGlzVG9MaXZlcQB+ABVMAAhwcmlvcml0eXEAfgAUTAAFcXVldWVxAH4AE0wADXJldHJ5QXR0ZW1wdHNxAH4AFEwAB3NlcnZpY2V0ABJMamF2YS9sYW5nL09iamVjdDtMAA9zZXJ2aWNlRW5kUG9pbnR0AA5MamF2YS9uZXQvVVJMO0wAE3NlcnZpY2VOYW1lU3BhY2VVUklxAH4ABEwAEHNlcnZpY2VOYW1lc3BhY2VxAH4ABEwAC3NlcnZpY2VQYXRocQB+AAR4cHNxAH4AGQFwdAAaT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2V0AE1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLmV4Y2VwdGlvbmhhbmRsaW5nLkRlZmF1bHRNZXNzYWdlRXhjZXB0aW9uSGFuZGxlcnNxAH4AH///////////c3EAfgAcAAAAA3NxAH4AGQBxAH4AMnBzcgAMamF2YS5uZXQuVVJMliU3Nhr85HIDAAdJAAhoYXNoQ29kZUkABHBvcnRMAAlhdXRob3JpdHlxAH4ABEwABGZpbGVxAH4ABEwABGhvc3RxAH4ABEwACHByb3RvY29scQB+AARMAANyZWZxAH4ABHhwaRuioAAAH5B0AA5sb2NhbGhvc3Q6ODA4MHQAKy9rci1kZXYvcmVtb3RpbmcvT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2V0AAlsb2NhbGhvc3R0AARodHRwcHh0AAB0AAZUUkFWRUx0AAEvc3IAE2phdmEudXRpbC5BcnJheUxpc3R4gdIdmcdhnQMAAUkABHNpemV4cAAAAAV3BAAAAAp0ADNvcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLnNlcnZpY2UuS1NCSmF2YVNlcnZpY2V0ADxjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5ldmVudHMuQ2FjaGVFbnRyeUV2ZW50TGlzdGVuZXJ0ADdjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5ldmVudHMuQ2FjaGVFdmVudExpc3RlbmVydAAXamF2YS51dGlsLkV2ZW50TGlzdGVuZXJ0ACxjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5MaWZlY3ljbGVBd2FyZXhxAH4AI3QABlRSQVZFTA==';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
INSERT INTO KRSB_MSG_PYLD_T (MSG_PYLD,MSG_QUE_ID)
  VALUES (EMPTY_CLOB(),408)
/
-- Length: 6108
-- Chunks: 2
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 408    FOR UPDATE;        
    buffer := 'rO0ABXNyAC1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLkFzeW5jaHJvbm91c0NhbGzxnQ5Y0t0lZQIAB1oAFWlnbm9yZVN0b3JlQW5kRm9yd2FyZFsACWFyZ3VtZW50c3QAE1tMamF2YS9sYW5nL09iamVjdDtMAAhjYWxsYmFja3QAM0xvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL0FzeW5jaHJvbm91c0NhbGxiYWNrO0wAB2NvbnRleHR0ABZMamF2YS9pby9TZXJpYWxpemFibGU7TAAKbWV0aG9kTmFtZXQAEkxqYXZhL2xhbmcvU3RyaW5nO1sACnBhcmFtVHlwZXN0ABJbTGphdmEvbGFuZy9DbGFzcztMAAtzZXJ2aWNlSW5mb3QAKkxvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL1NlcnZpY2VJbmZvO3hwAHVyABNbTGphdmEubGFuZy5PYmplY3Q7kM5YnxBzKWwCAAB4cAAAAAFzcgBDY29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLnBsdWdpbnMuY2x1c3RlcnN1cHBvcnQuQ2x1c3Rlck5vdGlmaWNhdGlvbhY+6VckvG9pAgACSQAEdHlwZUwABGRhdGFxAH4AA3hwAAAAAnQAMkRvY3VtZW50VHlwZVJ1bGVDYWNoZTpOYW1lc3BhY2VNYWludGVuYW5jZURvY3VtZW50cHB0AAZpbnZva2V1cgASW0xqYXZhLmxhbmcuQ2xhc3M7qxbXrsvNWpkCAAB4cAAAAAF2cgAUamF2YS5pby5TZXJpYWxpemFibGUQm2EN15tk7QIAAHhwc3IAKG9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuU2VydmljZUluZm/FFyO2KQS3ugIAC0wABWFsaXZldAATTGphdmEvbGFuZy9Cb29sZWFuO0wAFGVuZHBvaW50QWx0ZXJuYXRlVXJscQB+AARMAAtlbmRwb2ludFVybHEAfgAETAAKbG9ja1Zlck5icnQAE0xqYXZhL2xhbmcvSW50ZWdlcjtMAA5tZXNzYWdlRW50cnlJZHQAEExqYXZhL2xhbmcvTG9uZztMAAVxbmFtZXQAG0xqYXZheC94bWwvbmFtZXNwYWNlL1FOYW1lO0wAGnNlcmlhbGl6ZWRTZXJ2aWNlTmFtZXNwYWNlcQB+AARMAAhzZXJ2ZXJJcHEAfgAETAARc2VydmljZURlZmluaXRpb250ADBMb3JnL2t1YWxpL3JpY2Uva3NiL21lc3NhZ2luZy9TZXJ2aWNlRGVmaW5pdGlvbjtMAAtzZXJ2aWNlTmFtZXEAfgAETAAQc2VydmljZU5hbWVzcGFjZXEAfgAEeHBzcgARamF2YS5sYW5nLkJvb2xlYW7NIHKA1Zz67gIAAVoABXZhbHVleHABcHQAQGh0dHA6Ly9sb2NhbGhvc3Q6ODA4MC9rci1kZXYvcmVtb3RpbmcvT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2VzcgARamF2YS5sYW5nLkludGVnZXIS4qCk94GHOAIAAUkABXZhbHVleHIAEGphdmEubGFuZy5OdW1iZXKGrJUdC5TgiwIAAHhwAAAAAXNyAA5qYXZhLmxhbmcuTG9uZzuL5JDMjyPfAgABSgAFdmFsdWV4cQB+AB0AAAAAAAAPfXNyABlqYXZheC54bWwubmFtZXNwYWNlLlFOYW1lgW2oLfw73WwCAANMAAlsb2NhbFBhcnRxAH4ABEwADG5hbWVzcGFjZVVSSXEAfgAETAAGcHJlZml4cQB+AAR4cHQAGk9TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNldAAAcQB+ACR0B3RyTzBBQlhOeUFESnZjbWN1YTNWaGJHa3VjbWxqWlM1cmMySXViV1Z6YzJGbmFXNW5Ma3BoZG1GVFpYSjJhV05sUkdWbWFXNXBkR2x2YnJibkRWOUJOR3p4QWdBQlRBQVJjMlZ5ZG1salpVbHVkR1Z5Wm1GalpYTjBBQkJNYW1GMllTOTFkR2xzTDB4cGMzUTdlSElBTG05eVp5NXJkV0ZzYVM1eWFXTmxMbXR6WWk1dFpYTnpZV2RwYm1jdVUyVnlkbWxqWlVSbFptbHVhWFJwYjI0QW13SlBXTjBwZmdJQURVd0FDMkoxYzFObFkzVnlhWFI1ZEFBVFRHcGhkbUV2YkdGdVp5OUNiMjlzWldGdU8wd0FEMk55WldSbGJuUnBZV3h6Vkhsd1pYUUFURXh2Y21jdmEzVmhiR2t2Y21salpTOWpiM0psTDNObFkzVnlhWFI1TDJOeVpXUmxiblJwWVd4ekwwTnlaV1JsYm5ScFlXeHpVMjkxY21ObEpFTnlaV1JsYm5ScFlXeHpWSGx3WlR0TUFCQnNiMk5oYkZObGNuWnBZMlZPWVcxbGRBQVNUR3BoZG1FdmJHRnVaeTlUZEhKcGJtYzdUQUFYYldWemMyRm5aVVY0WTJWd2RHbHZia2hoYm1Sc1pYSnhBSDRBQlV3QURHMXBiR3hwYzFSdlRHbDJaWFFBRUV4cVlYWmhMMnhoYm1jdlRHOXVaenRNQUFod2NtbHZjbWwwZVhRQUUweHFZWFpoTDJ4aGJtY3ZTVzUwWldkbGNqdE1BQVZ4ZFdWMVpYRUFmZ0FEVEFBTmNtVjBjbmxCZEhSbGJYQjBjM0VBZmdBSFRBQUhjMlZ5ZG1salpYUUFFa3hxWVhaaEwyeGhibWN2VDJKcVpXTjBPMHdBRDNObGNuWnBZMlZGYm1SUWIybHVkSFFBRGt4cVlYWmhMMjVsZEM5VlVrdzdUQUFUYzJWeWRtbGpaVTVoYldWVGNHRmpaVlZTU1hFQWZnQUZUQUFRYzJWeWRtbGpaVTVoYldWemNHRmpaWEVBZmdBRlRBQUxjMlZ5ZG1salpWQmhkR2h4QUg0QUJYaHdjM0lBRVdwaGRtRXViR0Z1Wnk1Q2IyOXNaV0Z1elNCeWdOV2MrdTRDQUFGYUFBVjJZV3gxWlhod0FYQjBBQnBQVTBOaFkyaGxUbTkwYVdacFkyRjBhVzl1VTJWeWRtbGpaWFFBVFc5eVp5NXJkV0ZzYVM1eWFXTmxMbXR6WWk1dFpYTnpZV2RwYm1jdVpYaGpaWEIwYVc5dWFHRnVaR3hwYm1jdVJHVm1ZWFZzZEUxbGMzTmhaMlZGZUdObGNIUnBiMjVJWVc1a2JHVnljM0lBRG1waGRtRXViR0Z1Wnk1TWIyNW5PNHZra015UEk5OENBQUZLQUFWMllXeDFaWGh5QUJCcVlYWmhMbXhoYm1jdVRuVnRZbVZ5aHF5VkhRdVU0SXNDQUFCNGNQLy8vLy8vLy8vL2MzSUFFV3BoZG1FdWJHRnVaeTVKYm5SbFoyVnlFdUtncFBlQmh6Z0NBQUZKQUFWMllXeDFaWGh4QUg0QUVBQUFBQU56Y1FCK0FBc0FjUUIrQUJOd2MzSUFER3BoZG1FdWJtVjBMbFZTVEpZbE56WWEvT1J5QXdBSFNRQUlhR0Z6YUVOdlpHVkpBQVJ3YjNKMFRBQUpZWFYwYUc5eWFYUjVjUUIrQUFWTUFBUm1hV3hsY1FCK0FBVk1BQVJvYjNOMGNRQitBQVZNQUFod2NtOTBiMk52YkhFQWZnQUZUQUFEY21WbWNRQitBQVY0Y1AvLy8vOEFBQitRZEFBT2JHOWpZV3hvYjNOME9qZ3dPREIwQUNzdmEzSXRaR1YyTDNKbGJXOTBhVzVuTDA5VFEyRmphR1ZPYjNScFptbGpZWFJwYjI1VFpYSjJhV05sZEFBSmJHOWpZV3hvYjNOMGRBQUVhSFIwY0hCNGRBQUFkQUFHVkZKQlZrVk1kQUFCTDNOeUFCTnFZWFpoTG5WMGFXd3VRWEp5WVhsTWFYTjBlSUhTSFpuSFlaMERBQUZKQUFSemFYcGxlSEFBQUFBRmR3UUFBQUFLZEFBemIz';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 408    FOR UPDATE;        
    buffer := 'Sm5MbXQxWVd4cExuSnBZMlV1YTNOaUxtMWxjM05oWjJsdVp5NXpaWEoyYVdObExrdFRRa3BoZG1GVFpYSjJhV05sZEFBOFkyOXRMbTl3Wlc1emVXMXdhRzl1ZVM1dmMyTmhZMmhsTG1KaGMyVXVaWFpsYm5SekxrTmhZMmhsUlc1MGNubEZkbVZ1ZEV4cGMzUmxibVZ5ZEFBM1kyOXRMbTl3Wlc1emVXMXdhRzl1ZVM1dmMyTmhZMmhsTG1KaGMyVXVaWFpsYm5SekxrTmhZMmhsUlhabGJuUk1hWE4wWlc1bGNuUUFGMnBoZG1FdWRYUnBiQzVGZG1WdWRFeHBjM1JsYm1WeWRBQXNZMjl0TG05d1pXNXplVzF3YUc5dWVTNXZjMk5oWTJobExtSmhjMlV1VEdsbVpXTjVZMnhsUVhkaGNtVjR0AA0xMjkuMTg2Ljc5LjQ1c3IAMm9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuSmF2YVNlcnZpY2VEZWZpbml0aW9utucNX0E0bPECAAFMABFzZXJ2aWNlSW50ZXJmYWNlc3QAEExqYXZhL3V0aWwvTGlzdDt4cgAub3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5TZXJ2aWNlRGVmaW5pdGlvbgCbAk9Y3Sl+AgANTAALYnVzU2VjdXJpdHlxAH4AE0wAD2NyZWRlbnRpYWxzVHlwZXQATExvcmcva3VhbGkvcmljZS9jb3JlL3NlY3VyaXR5L2NyZWRlbnRpYWxzL0NyZWRlbnRpYWxzU291cmNlJENyZWRlbnRpYWxzVHlwZTtMABBsb2NhbFNlcnZpY2VOYW1lcQB+AARMABdtZXNzYWdlRXhjZXB0aW9uSGFuZGxlcnEAfgAETAAMbWlsbGlzVG9MaXZlcQB+ABVMAAhwcmlvcml0eXEAfgAUTAAFcXVldWVxAH4AE0wADXJldHJ5QXR0ZW1wdHNxAH4AFEwAB3NlcnZpY2V0ABJMamF2YS9sYW5nL09iamVjdDtMAA9zZXJ2aWNlRW5kUG9pbnR0AA5MamF2YS9uZXQvVVJMO0wAE3NlcnZpY2VOYW1lU3BhY2VVUklxAH4ABEwAEHNlcnZpY2VOYW1lc3BhY2VxAH4ABEwAC3NlcnZpY2VQYXRocQB+AAR4cHNxAH4AGQFwdAAaT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2V0AE1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLmV4Y2VwdGlvbmhhbmRsaW5nLkRlZmF1bHRNZXNzYWdlRXhjZXB0aW9uSGFuZGxlcnNxAH4AH///////////c3EAfgAcAAAAA3NxAH4AGQBxAH4AMnBzcgAMamF2YS5uZXQuVVJMliU3Nhr85HIDAAdJAAhoYXNoQ29kZUkABHBvcnRMAAlhdXRob3JpdHlxAH4ABEwABGZpbGVxAH4ABEwABGhvc3RxAH4ABEwACHByb3RvY29scQB+AARMAANyZWZxAH4ABHhwaRuioAAAH5B0AA5sb2NhbGhvc3Q6ODA4MHQAKy9rci1kZXYvcmVtb3RpbmcvT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2V0AAlsb2NhbGhvc3R0AARodHRwcHh0AAB0AAZUUkFWRUx0AAEvc3IAE2phdmEudXRpbC5BcnJheUxpc3R4gdIdmcdhnQMAAUkABHNpemV4cAAAAAV3BAAAAAp0ADNvcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLnNlcnZpY2UuS1NCSmF2YVNlcnZpY2V0ADxjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5ldmVudHMuQ2FjaGVFbnRyeUV2ZW50TGlzdGVuZXJ0ADdjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5ldmVudHMuQ2FjaGVFdmVudExpc3RlbmVydAAXamF2YS51dGlsLkV2ZW50TGlzdGVuZXJ0ACxjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5MaWZlY3ljbGVBd2FyZXhxAH4AI3QABlRSQVZFTA==';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
INSERT INTO KRSB_MSG_PYLD_T (MSG_PYLD,MSG_QUE_ID)
  VALUES (EMPTY_CLOB(),409)
/
-- Length: 6112
-- Chunks: 2
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 409    FOR UPDATE;        
    buffer := 'rO0ABXNyAC1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLkFzeW5jaHJvbm91c0NhbGzxnQ5Y0t0lZQIAB1oAFWlnbm9yZVN0b3JlQW5kRm9yd2FyZFsACWFyZ3VtZW50c3QAE1tMamF2YS9sYW5nL09iamVjdDtMAAhjYWxsYmFja3QAM0xvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL0FzeW5jaHJvbm91c0NhbGxiYWNrO0wAB2NvbnRleHR0ABZMamF2YS9pby9TZXJpYWxpemFibGU7TAAKbWV0aG9kTmFtZXQAEkxqYXZhL2xhbmcvU3RyaW5nO1sACnBhcmFtVHlwZXN0ABJbTGphdmEvbGFuZy9DbGFzcztMAAtzZXJ2aWNlSW5mb3QAKkxvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL1NlcnZpY2VJbmZvO3hwAHVyABNbTGphdmEubGFuZy5PYmplY3Q7kM5YnxBzKWwCAAB4cAAAAAFzcgBDY29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLnBsdWdpbnMuY2x1c3RlcnN1cHBvcnQuQ2x1c3Rlck5vdGlmaWNhdGlvbhY+6VckvG9pAgACSQAEdHlwZUwABGRhdGFxAH4AA3hwAAAAAnQANkRvY3VtZW50VHlwZVJ1bGVDYWNoZTpQYXJhbWV0ZXJUeXBlTWFpbnRlbmFuY2VEb2N1bWVudHBwdAAGaW52b2tldXIAEltMamF2YS5sYW5nLkNsYXNzO6sW167LzVqZAgAAeHAAAAABdnIAFGphdmEuaW8uU2VyaWFsaXphYmxlEJthDdebZO0CAAB4cHNyAChvcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLlNlcnZpY2VJbmZvxRcjtikEt7oCAAtMAAVhbGl2ZXQAE0xqYXZhL2xhbmcvQm9vbGVhbjtMABRlbmRwb2ludEFsdGVybmF0ZVVybHEAfgAETAALZW5kcG9pbnRVcmxxAH4ABEwACmxvY2tWZXJOYnJ0ABNMamF2YS9sYW5nL0ludGVnZXI7TAAObWVzc2FnZUVudHJ5SWR0ABBMamF2YS9sYW5nL0xvbmc7TAAFcW5hbWV0ABtMamF2YXgveG1sL25hbWVzcGFjZS9RTmFtZTtMABpzZXJpYWxpemVkU2VydmljZU5hbWVzcGFjZXEAfgAETAAIc2VydmVySXBxAH4ABEwAEXNlcnZpY2VEZWZpbml0aW9udAAwTG9yZy9rdWFsaS9yaWNlL2tzYi9tZXNzYWdpbmcvU2VydmljZURlZmluaXRpb247TAALc2VydmljZU5hbWVxAH4ABEwAEHNlcnZpY2VOYW1lc3BhY2VxAH4ABHhwc3IAEWphdmEubGFuZy5Cb29sZWFuzSBygNWc+u4CAAFaAAV2YWx1ZXhwAXB0AEBodHRwOi8vbG9jYWxob3N0OjgwODAva3ItZGV2L3JlbW90aW5nL09TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNlc3IAEWphdmEubGFuZy5JbnRlZ2VyEuKgpPeBhzgCAAFJAAV2YWx1ZXhyABBqYXZhLmxhbmcuTnVtYmVyhqyVHQuU4IsCAAB4cAAAAAFzcgAOamF2YS5sYW5nLkxvbmc7i+SQzI8j3wIAAUoABXZhbHVleHEAfgAdAAAAAAAAD31zcgAZamF2YXgueG1sLm5hbWVzcGFjZS5RTmFtZYFtqC38O91sAgADTAAJbG9jYWxQYXJ0cQB+AARMAAxuYW1lc3BhY2VVUklxAH4ABEwABnByZWZpeHEAfgAEeHB0ABpPU0NhY2hlTm90aWZpY2F0aW9uU2VydmljZXQAAHEAfgAkdAd0ck8wQUJYTnlBREp2Y21jdWEzVmhiR2t1Y21salpTNXJjMkl1YldWemMyRm5hVzVuTGtwaGRtRlRaWEoyYVdObFJHVm1hVzVwZEdsdmJyYm5EVjlCTkd6eEFnQUJUQUFSYzJWeWRtbGpaVWx1ZEdWeVptRmpaWE4wQUJCTWFtRjJZUzkxZEdsc0wweHBjM1E3ZUhJQUxtOXlaeTVyZFdGc2FTNXlhV05sTG10ellpNXRaWE56WVdkcGJtY3VVMlZ5ZG1salpVUmxabWx1YVhScGIyNEFtd0pQV04wcGZnSUFEVXdBQzJKMWMxTmxZM1Z5YVhSNWRBQVRUR3BoZG1FdmJHRnVaeTlDYjI5c1pXRnVPMHdBRDJOeVpXUmxiblJwWVd4elZIbHdaWFFBVEV4dmNtY3ZhM1ZoYkdrdmNtbGpaUzlqYjNKbEwzTmxZM1Z5YVhSNUwyTnlaV1JsYm5ScFlXeHpMME55WldSbGJuUnBZV3h6VTI5MWNtTmxKRU55WldSbGJuUnBZV3h6Vkhsd1pUdE1BQkJzYjJOaGJGTmxjblpwWTJWT1lXMWxkQUFTVEdwaGRtRXZiR0Z1Wnk5VGRISnBibWM3VEFBWGJXVnpjMkZuWlVWNFkyVndkR2x2YmtoaGJtUnNaWEp4QUg0QUJVd0FERzFwYkd4cGMxUnZUR2wyWlhRQUVFeHFZWFpoTDJ4aGJtY3ZURzl1Wnp0TUFBaHdjbWx2Y21sMGVYUUFFMHhxWVhaaEwyeGhibWN2U1c1MFpXZGxjanRNQUFWeGRXVjFaWEVBZmdBRFRBQU5jbVYwY25sQmRIUmxiWEIwYzNFQWZnQUhUQUFIYzJWeWRtbGpaWFFBRWt4cVlYWmhMMnhoYm1jdlQySnFaV04wTzB3QUQzTmxjblpwWTJWRmJtUlFiMmx1ZEhRQURreHFZWFpoTDI1bGRDOVZVa3c3VEFBVGMyVnlkbWxqWlU1aGJXVlRjR0ZqWlZWU1NYRUFmZ0FGVEFBUWMyVnlkbWxqWlU1aGJXVnpjR0ZqWlhFQWZnQUZUQUFMYzJWeWRtbGpaVkJoZEdoeEFINEFCWGh3YzNJQUVXcGhkbUV1YkdGdVp5NUNiMjlzWldGdXpTQnlnTldjK3U0Q0FBRmFBQVYyWVd4MVpYaHdBWEIwQUJwUFUwTmhZMmhsVG05MGFXWnBZMkYwYVc5dVUyVnlkbWxqWlhRQVRXOXlaeTVyZFdGc2FTNXlhV05sTG10ellpNXRaWE56WVdkcGJtY3VaWGhqWlhCMGFXOXVhR0Z1Wkd4cGJtY3VSR1ZtWVhWc2RFMWxjM05oWjJWRmVHTmxjSFJwYjI1SVlXNWtiR1Z5YzNJQURtcGhkbUV1YkdGdVp5NU1iMjVuTzR2a2tNeVBJOThDQUFGS0FBVjJZV3gxWlhoeUFCQnFZWFpoTG14aGJtY3VUblZ0WW1WeWhxeVZIUXVVNElzQ0FBQjRjUC8vLy8vLy8vLy9jM0lBRVdwaGRtRXViR0Z1Wnk1SmJuUmxaMlZ5RXVLZ3BQZUJoemdDQUFGSkFBVjJZV3gxWlhoeEFINEFFQUFBQUFOemNRQitBQXNBY1FCK0FCTndjM0lBREdwaGRtRXVibVYwTGxWU1RKWWxOellhL09SeUF3QUhTUUFJYUdGemFFTnZaR1ZKQUFSd2IzSjBUQUFKWVhWMGFHOXlhWFI1Y1FCK0FBVk1BQVJtYVd4bGNRQitBQVZNQUFSb2IzTjBjUUIrQUFWTUFBaHdjbTkwYjJOdmJIRUFmZ0FGVEFBRGNtVm1jUUIrQUFWNGNQLy8vLzhBQUIrUWRBQU9iRzlqWVd4b2IzTjBPamd3T0RCMEFDc3ZhM0l0WkdWMkwzSmxiVzkwYVc1bkwwOVRRMkZqYUdWT2IzUnBabWxqWVhScGIyNVRaWEoyYVdObGRBQUpiRzlqWVd4b2IzTjBkQUFFYUhSMGNIQjRkQUFBZEFBR1ZGSkJWa1ZNZEFBQkwzTnlBQk5xWVhaaExuVjBhV3d1UVhKeVlYbE1hWE4wZUlIU0habkhZWjBEQUFGSkFBUnphWHBsZUhBQUFBQUZkd1FBQUFBS2RB';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 409    FOR UPDATE;        
    buffer := 'QXpiM0puTG10MVlXeHBMbkpwWTJVdWEzTmlMbTFsYzNOaFoybHVaeTV6WlhKMmFXTmxMa3RUUWtwaGRtRlRaWEoyYVdObGRBQThZMjl0TG05d1pXNXplVzF3YUc5dWVTNXZjMk5oWTJobExtSmhjMlV1WlhabGJuUnpMa05oWTJobFJXNTBjbmxGZG1WdWRFeHBjM1JsYm1WeWRBQTNZMjl0TG05d1pXNXplVzF3YUc5dWVTNXZjMk5oWTJobExtSmhjMlV1WlhabGJuUnpMa05oWTJobFJYWmxiblJNYVhOMFpXNWxjblFBRjJwaGRtRXVkWFJwYkM1RmRtVnVkRXhwYzNSbGJtVnlkQUFzWTI5dExtOXdaVzV6ZVcxd2FHOXVlUzV2YzJOaFkyaGxMbUpoYzJVdVRHbG1aV041WTJ4bFFYZGhjbVY0dAANMTI5LjE4Ni43OS40NXNyADJvcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLkphdmFTZXJ2aWNlRGVmaW5pdGlvbrbnDV9BNGzxAgABTAARc2VydmljZUludGVyZmFjZXN0ABBMamF2YS91dGlsL0xpc3Q7eHIALm9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuU2VydmljZURlZmluaXRpb24AmwJPWN0pfgIADUwAC2J1c1NlY3VyaXR5cQB+ABNMAA9jcmVkZW50aWFsc1R5cGV0AExMb3JnL2t1YWxpL3JpY2UvY29yZS9zZWN1cml0eS9jcmVkZW50aWFscy9DcmVkZW50aWFsc1NvdXJjZSRDcmVkZW50aWFsc1R5cGU7TAAQbG9jYWxTZXJ2aWNlTmFtZXEAfgAETAAXbWVzc2FnZUV4Y2VwdGlvbkhhbmRsZXJxAH4ABEwADG1pbGxpc1RvTGl2ZXEAfgAVTAAIcHJpb3JpdHlxAH4AFEwABXF1ZXVlcQB+ABNMAA1yZXRyeUF0dGVtcHRzcQB+ABRMAAdzZXJ2aWNldAASTGphdmEvbGFuZy9PYmplY3Q7TAAPc2VydmljZUVuZFBvaW50dAAOTGphdmEvbmV0L1VSTDtMABNzZXJ2aWNlTmFtZVNwYWNlVVJJcQB+AARMABBzZXJ2aWNlTmFtZXNwYWNlcQB+AARMAAtzZXJ2aWNlUGF0aHEAfgAEeHBzcQB+ABkBcHQAGk9TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNldABNb3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5leGNlcHRpb25oYW5kbGluZy5EZWZhdWx0TWVzc2FnZUV4Y2VwdGlvbkhhbmRsZXJzcQB+AB///////////3NxAH4AHAAAAANzcQB+ABkAcQB+ADJwc3IADGphdmEubmV0LlVSTJYlNzYa/ORyAwAHSQAIaGFzaENvZGVJAARwb3J0TAAJYXV0aG9yaXR5cQB+AARMAARmaWxlcQB+AARMAARob3N0cQB+AARMAAhwcm90b2NvbHEAfgAETAADcmVmcQB+AAR4cGkboqAAAB+QdAAObG9jYWxob3N0OjgwODB0ACsva3ItZGV2L3JlbW90aW5nL09TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNldAAJbG9jYWxob3N0dAAEaHR0cHB4dAAAdAAGVFJBVkVMdAABL3NyABNqYXZhLnV0aWwuQXJyYXlMaXN0eIHSHZnHYZ0DAAFJAARzaXpleHAAAAAFdwQAAAAKdAAzb3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5zZXJ2aWNlLktTQkphdmFTZXJ2aWNldAA8Y29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLmJhc2UuZXZlbnRzLkNhY2hlRW50cnlFdmVudExpc3RlbmVydAA3Y29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLmJhc2UuZXZlbnRzLkNhY2hlRXZlbnRMaXN0ZW5lcnQAF2phdmEudXRpbC5FdmVudExpc3RlbmVydAAsY29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLmJhc2UuTGlmZWN5Y2xlQXdhcmV4cQB+ACN0AAZUUkFWRUw=';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
INSERT INTO KRSB_MSG_PYLD_T (MSG_PYLD,MSG_QUE_ID)
  VALUES (EMPTY_CLOB(),410)
/
-- Length: 6120
-- Chunks: 2
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 410    FOR UPDATE;        
    buffer := 'rO0ABXNyAC1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLkFzeW5jaHJvbm91c0NhbGzxnQ5Y0t0lZQIAB1oAFWlnbm9yZVN0b3JlQW5kRm9yd2FyZFsACWFyZ3VtZW50c3QAE1tMamF2YS9sYW5nL09iamVjdDtMAAhjYWxsYmFja3QAM0xvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL0FzeW5jaHJvbm91c0NhbGxiYWNrO0wAB2NvbnRleHR0ABZMamF2YS9pby9TZXJpYWxpemFibGU7TAAKbWV0aG9kTmFtZXQAEkxqYXZhL2xhbmcvU3RyaW5nO1sACnBhcmFtVHlwZXN0ABJbTGphdmEvbGFuZy9DbGFzcztMAAtzZXJ2aWNlSW5mb3QAKkxvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL1NlcnZpY2VJbmZvO3hwAHVyABNbTGphdmEubGFuZy5PYmplY3Q7kM5YnxBzKWwCAAB4cAAAAAFzcgBDY29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLnBsdWdpbnMuY2x1c3RlcnN1cHBvcnQuQ2x1c3Rlck5vdGlmaWNhdGlvbhY+6VckvG9pAgACSQAEdHlwZUwABGRhdGFxAH4AA3hwAAAAAnQAPERvY3VtZW50VHlwZVJ1bGVDYWNoZTpQYXJhbWV0ZXJEZXRhaWxUeXBlTWFpbnRlbmFuY2VEb2N1bWVudHBwdAAGaW52b2tldXIAEltMamF2YS5sYW5nLkNsYXNzO6sW167LzVqZAgAAeHAAAAABdnIAFGphdmEuaW8uU2VyaWFsaXphYmxlEJthDdebZO0CAAB4cHNyAChvcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLlNlcnZpY2VJbmZvxRcjtikEt7oCAAtMAAVhbGl2ZXQAE0xqYXZhL2xhbmcvQm9vbGVhbjtMABRlbmRwb2ludEFsdGVybmF0ZVVybHEAfgAETAALZW5kcG9pbnRVcmxxAH4ABEwACmxvY2tWZXJOYnJ0ABNMamF2YS9sYW5nL0ludGVnZXI7TAAObWVzc2FnZUVudHJ5SWR0ABBMamF2YS9sYW5nL0xvbmc7TAAFcW5hbWV0ABtMamF2YXgveG1sL25hbWVzcGFjZS9RTmFtZTtMABpzZXJpYWxpemVkU2VydmljZU5hbWVzcGFjZXEAfgAETAAIc2VydmVySXBxAH4ABEwAEXNlcnZpY2VEZWZpbml0aW9udAAwTG9yZy9rdWFsaS9yaWNlL2tzYi9tZXNzYWdpbmcvU2VydmljZURlZmluaXRpb247TAALc2VydmljZU5hbWVxAH4ABEwAEHNlcnZpY2VOYW1lc3BhY2VxAH4ABHhwc3IAEWphdmEubGFuZy5Cb29sZWFuzSBygNWc+u4CAAFaAAV2YWx1ZXhwAXB0AEBodHRwOi8vbG9jYWxob3N0OjgwODAva3ItZGV2L3JlbW90aW5nL09TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNlc3IAEWphdmEubGFuZy5JbnRlZ2VyEuKgpPeBhzgCAAFJAAV2YWx1ZXhyABBqYXZhLmxhbmcuTnVtYmVyhqyVHQuU4IsCAAB4cAAAAAFzcgAOamF2YS5sYW5nLkxvbmc7i+SQzI8j3wIAAUoABXZhbHVleHEAfgAdAAAAAAAAD31zcgAZamF2YXgueG1sLm5hbWVzcGFjZS5RTmFtZYFtqC38O91sAgADTAAJbG9jYWxQYXJ0cQB+AARMAAxuYW1lc3BhY2VVUklxAH4ABEwABnByZWZpeHEAfgAEeHB0ABpPU0NhY2hlTm90aWZpY2F0aW9uU2VydmljZXQAAHEAfgAkdAd0ck8wQUJYTnlBREp2Y21jdWEzVmhiR2t1Y21salpTNXJjMkl1YldWemMyRm5hVzVuTGtwaGRtRlRaWEoyYVdObFJHVm1hVzVwZEdsdmJyYm5EVjlCTkd6eEFnQUJUQUFSYzJWeWRtbGpaVWx1ZEdWeVptRmpaWE4wQUJCTWFtRjJZUzkxZEdsc0wweHBjM1E3ZUhJQUxtOXlaeTVyZFdGc2FTNXlhV05sTG10ellpNXRaWE56WVdkcGJtY3VVMlZ5ZG1salpVUmxabWx1YVhScGIyNEFtd0pQV04wcGZnSUFEVXdBQzJKMWMxTmxZM1Z5YVhSNWRBQVRUR3BoZG1FdmJHRnVaeTlDYjI5c1pXRnVPMHdBRDJOeVpXUmxiblJwWVd4elZIbHdaWFFBVEV4dmNtY3ZhM1ZoYkdrdmNtbGpaUzlqYjNKbEwzTmxZM1Z5YVhSNUwyTnlaV1JsYm5ScFlXeHpMME55WldSbGJuUnBZV3h6VTI5MWNtTmxKRU55WldSbGJuUnBZV3h6Vkhsd1pUdE1BQkJzYjJOaGJGTmxjblpwWTJWT1lXMWxkQUFTVEdwaGRtRXZiR0Z1Wnk5VGRISnBibWM3VEFBWGJXVnpjMkZuWlVWNFkyVndkR2x2YmtoaGJtUnNaWEp4QUg0QUJVd0FERzFwYkd4cGMxUnZUR2wyWlhRQUVFeHFZWFpoTDJ4aGJtY3ZURzl1Wnp0TUFBaHdjbWx2Y21sMGVYUUFFMHhxWVhaaEwyeGhibWN2U1c1MFpXZGxjanRNQUFWeGRXVjFaWEVBZmdBRFRBQU5jbVYwY25sQmRIUmxiWEIwYzNFQWZnQUhUQUFIYzJWeWRtbGpaWFFBRWt4cVlYWmhMMnhoYm1jdlQySnFaV04wTzB3QUQzTmxjblpwWTJWRmJtUlFiMmx1ZEhRQURreHFZWFpoTDI1bGRDOVZVa3c3VEFBVGMyVnlkbWxqWlU1aGJXVlRjR0ZqWlZWU1NYRUFmZ0FGVEFBUWMyVnlkbWxqWlU1aGJXVnpjR0ZqWlhFQWZnQUZUQUFMYzJWeWRtbGpaVkJoZEdoeEFINEFCWGh3YzNJQUVXcGhkbUV1YkdGdVp5NUNiMjlzWldGdXpTQnlnTldjK3U0Q0FBRmFBQVYyWVd4MVpYaHdBWEIwQUJwUFUwTmhZMmhsVG05MGFXWnBZMkYwYVc5dVUyVnlkbWxqWlhRQVRXOXlaeTVyZFdGc2FTNXlhV05sTG10ellpNXRaWE56WVdkcGJtY3VaWGhqWlhCMGFXOXVhR0Z1Wkd4cGJtY3VSR1ZtWVhWc2RFMWxjM05oWjJWRmVHTmxjSFJwYjI1SVlXNWtiR1Z5YzNJQURtcGhkbUV1YkdGdVp5NU1iMjVuTzR2a2tNeVBJOThDQUFGS0FBVjJZV3gxWlhoeUFCQnFZWFpoTG14aGJtY3VUblZ0WW1WeWhxeVZIUXVVNElzQ0FBQjRjUC8vLy8vLy8vLy9jM0lBRVdwaGRtRXViR0Z1Wnk1SmJuUmxaMlZ5RXVLZ3BQZUJoemdDQUFGSkFBVjJZV3gxWlhoeEFINEFFQUFBQUFOemNRQitBQXNBY1FCK0FCTndjM0lBREdwaGRtRXVibVYwTGxWU1RKWWxOellhL09SeUF3QUhTUUFJYUdGemFFTnZaR1ZKQUFSd2IzSjBUQUFKWVhWMGFHOXlhWFI1Y1FCK0FBVk1BQVJtYVd4bGNRQitBQVZNQUFSb2IzTjBjUUIrQUFWTUFBaHdjbTkwYjJOdmJIRUFmZ0FGVEFBRGNtVm1jUUIrQUFWNGNQLy8vLzhBQUIrUWRBQU9iRzlqWVd4b2IzTjBPamd3T0RCMEFDc3ZhM0l0WkdWMkwzSmxiVzkwYVc1bkwwOVRRMkZqYUdWT2IzUnBabWxqWVhScGIyNVRaWEoyYVdObGRBQUpiRzlqWVd4b2IzTjBkQUFFYUhSMGNIQjRkQUFBZEFBR1ZGSkJWa1ZNZEFBQkwzTnlBQk5xWVhaaExuVjBhV3d1UVhKeVlYbE1hWE4wZUlIU0habkhZWjBEQUFGSkFBUnphWHBsZUhBQUFBQUZkd1FB';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 410    FOR UPDATE;        
    buffer := 'QUFBS2RBQXpiM0puTG10MVlXeHBMbkpwWTJVdWEzTmlMbTFsYzNOaFoybHVaeTV6WlhKMmFXTmxMa3RUUWtwaGRtRlRaWEoyYVdObGRBQThZMjl0TG05d1pXNXplVzF3YUc5dWVTNXZjMk5oWTJobExtSmhjMlV1WlhabGJuUnpMa05oWTJobFJXNTBjbmxGZG1WdWRFeHBjM1JsYm1WeWRBQTNZMjl0TG05d1pXNXplVzF3YUc5dWVTNXZjMk5oWTJobExtSmhjMlV1WlhabGJuUnpMa05oWTJobFJYWmxiblJNYVhOMFpXNWxjblFBRjJwaGRtRXVkWFJwYkM1RmRtVnVkRXhwYzNSbGJtVnlkQUFzWTI5dExtOXdaVzV6ZVcxd2FHOXVlUzV2YzJOaFkyaGxMbUpoYzJVdVRHbG1aV041WTJ4bFFYZGhjbVY0dAANMTI5LjE4Ni43OS40NXNyADJvcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLkphdmFTZXJ2aWNlRGVmaW5pdGlvbrbnDV9BNGzxAgABTAARc2VydmljZUludGVyZmFjZXN0ABBMamF2YS91dGlsL0xpc3Q7eHIALm9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuU2VydmljZURlZmluaXRpb24AmwJPWN0pfgIADUwAC2J1c1NlY3VyaXR5cQB+ABNMAA9jcmVkZW50aWFsc1R5cGV0AExMb3JnL2t1YWxpL3JpY2UvY29yZS9zZWN1cml0eS9jcmVkZW50aWFscy9DcmVkZW50aWFsc1NvdXJjZSRDcmVkZW50aWFsc1R5cGU7TAAQbG9jYWxTZXJ2aWNlTmFtZXEAfgAETAAXbWVzc2FnZUV4Y2VwdGlvbkhhbmRsZXJxAH4ABEwADG1pbGxpc1RvTGl2ZXEAfgAVTAAIcHJpb3JpdHlxAH4AFEwABXF1ZXVlcQB+ABNMAA1yZXRyeUF0dGVtcHRzcQB+ABRMAAdzZXJ2aWNldAASTGphdmEvbGFuZy9PYmplY3Q7TAAPc2VydmljZUVuZFBvaW50dAAOTGphdmEvbmV0L1VSTDtMABNzZXJ2aWNlTmFtZVNwYWNlVVJJcQB+AARMABBzZXJ2aWNlTmFtZXNwYWNlcQB+AARMAAtzZXJ2aWNlUGF0aHEAfgAEeHBzcQB+ABkBcHQAGk9TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNldABNb3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5leGNlcHRpb25oYW5kbGluZy5EZWZhdWx0TWVzc2FnZUV4Y2VwdGlvbkhhbmRsZXJzcQB+AB///////////3NxAH4AHAAAAANzcQB+ABkAcQB+ADJwc3IADGphdmEubmV0LlVSTJYlNzYa/ORyAwAHSQAIaGFzaENvZGVJAARwb3J0TAAJYXV0aG9yaXR5cQB+AARMAARmaWxlcQB+AARMAARob3N0cQB+AARMAAhwcm90b2NvbHEAfgAETAADcmVmcQB+AAR4cGkboqAAAB+QdAAObG9jYWxob3N0OjgwODB0ACsva3ItZGV2L3JlbW90aW5nL09TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNldAAJbG9jYWxob3N0dAAEaHR0cHB4dAAAdAAGVFJBVkVMdAABL3NyABNqYXZhLnV0aWwuQXJyYXlMaXN0eIHSHZnHYZ0DAAFJAARzaXpleHAAAAAFdwQAAAAKdAAzb3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5zZXJ2aWNlLktTQkphdmFTZXJ2aWNldAA8Y29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLmJhc2UuZXZlbnRzLkNhY2hlRW50cnlFdmVudExpc3RlbmVydAA3Y29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLmJhc2UuZXZlbnRzLkNhY2hlRXZlbnRMaXN0ZW5lcnQAF2phdmEudXRpbC5FdmVudExpc3RlbmVydAAsY29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLmJhc2UuTGlmZWN5Y2xlQXdhcmV4cQB+ACN0AAZUUkFWRUw=';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
INSERT INTO KRSB_MSG_PYLD_T (MSG_PYLD,MSG_QUE_ID)
  VALUES (EMPTY_CLOB(),411)
/
-- Length: 6108
-- Chunks: 2
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 411    FOR UPDATE;        
    buffer := 'rO0ABXNyAC1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLkFzeW5jaHJvbm91c0NhbGzxnQ5Y0t0lZQIAB1oAFWlnbm9yZVN0b3JlQW5kRm9yd2FyZFsACWFyZ3VtZW50c3QAE1tMamF2YS9sYW5nL09iamVjdDtMAAhjYWxsYmFja3QAM0xvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL0FzeW5jaHJvbm91c0NhbGxiYWNrO0wAB2NvbnRleHR0ABZMamF2YS9pby9TZXJpYWxpemFibGU7TAAKbWV0aG9kTmFtZXQAEkxqYXZhL2xhbmcvU3RyaW5nO1sACnBhcmFtVHlwZXN0ABJbTGphdmEvbGFuZy9DbGFzcztMAAtzZXJ2aWNlSW5mb3QAKkxvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL1NlcnZpY2VJbmZvO3hwAHVyABNbTGphdmEubGFuZy5PYmplY3Q7kM5YnxBzKWwCAAB4cAAAAAFzcgBDY29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLnBsdWdpbnMuY2x1c3RlcnN1cHBvcnQuQ2x1c3Rlck5vdGlmaWNhdGlvbhY+6VckvG9pAgACSQAEdHlwZUwABGRhdGFxAH4AA3hwAAAAAnQAMkRvY3VtZW50VHlwZVJ1bGVDYWNoZTpQYXJhbWV0ZXJNYWludGVuYW5jZURvY3VtZW50cHB0AAZpbnZva2V1cgASW0xqYXZhLmxhbmcuQ2xhc3M7qxbXrsvNWpkCAAB4cAAAAAF2cgAUamF2YS5pby5TZXJpYWxpemFibGUQm2EN15tk7QIAAHhwc3IAKG9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuU2VydmljZUluZm/FFyO2KQS3ugIAC0wABWFsaXZldAATTGphdmEvbGFuZy9Cb29sZWFuO0wAFGVuZHBvaW50QWx0ZXJuYXRlVXJscQB+AARMAAtlbmRwb2ludFVybHEAfgAETAAKbG9ja1Zlck5icnQAE0xqYXZhL2xhbmcvSW50ZWdlcjtMAA5tZXNzYWdlRW50cnlJZHQAEExqYXZhL2xhbmcvTG9uZztMAAVxbmFtZXQAG0xqYXZheC94bWwvbmFtZXNwYWNlL1FOYW1lO0wAGnNlcmlhbGl6ZWRTZXJ2aWNlTmFtZXNwYWNlcQB+AARMAAhzZXJ2ZXJJcHEAfgAETAARc2VydmljZURlZmluaXRpb250ADBMb3JnL2t1YWxpL3JpY2Uva3NiL21lc3NhZ2luZy9TZXJ2aWNlRGVmaW5pdGlvbjtMAAtzZXJ2aWNlTmFtZXEAfgAETAAQc2VydmljZU5hbWVzcGFjZXEAfgAEeHBzcgARamF2YS5sYW5nLkJvb2xlYW7NIHKA1Zz67gIAAVoABXZhbHVleHABcHQAQGh0dHA6Ly9sb2NhbGhvc3Q6ODA4MC9rci1kZXYvcmVtb3RpbmcvT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2VzcgARamF2YS5sYW5nLkludGVnZXIS4qCk94GHOAIAAUkABXZhbHVleHIAEGphdmEubGFuZy5OdW1iZXKGrJUdC5TgiwIAAHhwAAAAAXNyAA5qYXZhLmxhbmcuTG9uZzuL5JDMjyPfAgABSgAFdmFsdWV4cQB+AB0AAAAAAAAPfXNyABlqYXZheC54bWwubmFtZXNwYWNlLlFOYW1lgW2oLfw73WwCAANMAAlsb2NhbFBhcnRxAH4ABEwADG5hbWVzcGFjZVVSSXEAfgAETAAGcHJlZml4cQB+AAR4cHQAGk9TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNldAAAcQB+ACR0B3RyTzBBQlhOeUFESnZjbWN1YTNWaGJHa3VjbWxqWlM1cmMySXViV1Z6YzJGbmFXNW5Ma3BoZG1GVFpYSjJhV05sUkdWbWFXNXBkR2x2YnJibkRWOUJOR3p4QWdBQlRBQVJjMlZ5ZG1salpVbHVkR1Z5Wm1GalpYTjBBQkJNYW1GMllTOTFkR2xzTDB4cGMzUTdlSElBTG05eVp5NXJkV0ZzYVM1eWFXTmxMbXR6WWk1dFpYTnpZV2RwYm1jdVUyVnlkbWxqWlVSbFptbHVhWFJwYjI0QW13SlBXTjBwZmdJQURVd0FDMkoxYzFObFkzVnlhWFI1ZEFBVFRHcGhkbUV2YkdGdVp5OUNiMjlzWldGdU8wd0FEMk55WldSbGJuUnBZV3h6Vkhsd1pYUUFURXh2Y21jdmEzVmhiR2t2Y21salpTOWpiM0psTDNObFkzVnlhWFI1TDJOeVpXUmxiblJwWVd4ekwwTnlaV1JsYm5ScFlXeHpVMjkxY21ObEpFTnlaV1JsYm5ScFlXeHpWSGx3WlR0TUFCQnNiMk5oYkZObGNuWnBZMlZPWVcxbGRBQVNUR3BoZG1FdmJHRnVaeTlUZEhKcGJtYzdUQUFYYldWemMyRm5aVVY0WTJWd2RHbHZia2hoYm1Sc1pYSnhBSDRBQlV3QURHMXBiR3hwYzFSdlRHbDJaWFFBRUV4cVlYWmhMMnhoYm1jdlRHOXVaenRNQUFod2NtbHZjbWwwZVhRQUUweHFZWFpoTDJ4aGJtY3ZTVzUwWldkbGNqdE1BQVZ4ZFdWMVpYRUFmZ0FEVEFBTmNtVjBjbmxCZEhSbGJYQjBjM0VBZmdBSFRBQUhjMlZ5ZG1salpYUUFFa3hxWVhaaEwyeGhibWN2VDJKcVpXTjBPMHdBRDNObGNuWnBZMlZGYm1SUWIybHVkSFFBRGt4cVlYWmhMMjVsZEM5VlVrdzdUQUFUYzJWeWRtbGpaVTVoYldWVGNHRmpaVlZTU1hFQWZnQUZUQUFRYzJWeWRtbGpaVTVoYldWemNHRmpaWEVBZmdBRlRBQUxjMlZ5ZG1salpWQmhkR2h4QUg0QUJYaHdjM0lBRVdwaGRtRXViR0Z1Wnk1Q2IyOXNaV0Z1elNCeWdOV2MrdTRDQUFGYUFBVjJZV3gxWlhod0FYQjBBQnBQVTBOaFkyaGxUbTkwYVdacFkyRjBhVzl1VTJWeWRtbGpaWFFBVFc5eVp5NXJkV0ZzYVM1eWFXTmxMbXR6WWk1dFpYTnpZV2RwYm1jdVpYaGpaWEIwYVc5dWFHRnVaR3hwYm1jdVJHVm1ZWFZzZEUxbGMzTmhaMlZGZUdObGNIUnBiMjVJWVc1a2JHVnljM0lBRG1waGRtRXViR0Z1Wnk1TWIyNW5PNHZra015UEk5OENBQUZLQUFWMllXeDFaWGh5QUJCcVlYWmhMbXhoYm1jdVRuVnRZbVZ5aHF5VkhRdVU0SXNDQUFCNGNQLy8vLy8vLy8vL2MzSUFFV3BoZG1FdWJHRnVaeTVKYm5SbFoyVnlFdUtncFBlQmh6Z0NBQUZKQUFWMllXeDFaWGh4QUg0QUVBQUFBQU56Y1FCK0FBc0FjUUIrQUJOd2MzSUFER3BoZG1FdWJtVjBMbFZTVEpZbE56WWEvT1J5QXdBSFNRQUlhR0Z6YUVOdlpHVkpBQVJ3YjNKMFRBQUpZWFYwYUc5eWFYUjVjUUIrQUFWTUFBUm1hV3hsY1FCK0FBVk1BQVJvYjNOMGNRQitBQVZNQUFod2NtOTBiMk52YkhFQWZnQUZUQUFEY21WbWNRQitBQVY0Y1AvLy8vOEFBQitRZEFBT2JHOWpZV3hvYjNOME9qZ3dPREIwQUNzdmEzSXRaR1YyTDNKbGJXOTBhVzVuTDA5VFEyRmphR1ZPYjNScFptbGpZWFJwYjI1VFpYSjJhV05sZEFBSmJHOWpZV3hvYjNOMGRBQUVhSFIwY0hCNGRBQUFkQUFHVkZKQlZrVk1kQUFCTDNOeUFCTnFZWFpoTG5WMGFXd3VRWEp5WVhsTWFYTjBlSUhTSFpuSFlaMERBQUZKQUFSemFYcGxlSEFBQUFBRmR3UUFBQUFLZEFBemIz';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 411    FOR UPDATE;        
    buffer := 'Sm5MbXQxWVd4cExuSnBZMlV1YTNOaUxtMWxjM05oWjJsdVp5NXpaWEoyYVdObExrdFRRa3BoZG1GVFpYSjJhV05sZEFBOFkyOXRMbTl3Wlc1emVXMXdhRzl1ZVM1dmMyTmhZMmhsTG1KaGMyVXVaWFpsYm5SekxrTmhZMmhsUlc1MGNubEZkbVZ1ZEV4cGMzUmxibVZ5ZEFBM1kyOXRMbTl3Wlc1emVXMXdhRzl1ZVM1dmMyTmhZMmhsTG1KaGMyVXVaWFpsYm5SekxrTmhZMmhsUlhabGJuUk1hWE4wWlc1bGNuUUFGMnBoZG1FdWRYUnBiQzVGZG1WdWRFeHBjM1JsYm1WeWRBQXNZMjl0TG05d1pXNXplVzF3YUc5dWVTNXZjMk5oWTJobExtSmhjMlV1VEdsbVpXTjVZMnhsUVhkaGNtVjR0AA0xMjkuMTg2Ljc5LjQ1c3IAMm9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuSmF2YVNlcnZpY2VEZWZpbml0aW9utucNX0E0bPECAAFMABFzZXJ2aWNlSW50ZXJmYWNlc3QAEExqYXZhL3V0aWwvTGlzdDt4cgAub3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5TZXJ2aWNlRGVmaW5pdGlvbgCbAk9Y3Sl+AgANTAALYnVzU2VjdXJpdHlxAH4AE0wAD2NyZWRlbnRpYWxzVHlwZXQATExvcmcva3VhbGkvcmljZS9jb3JlL3NlY3VyaXR5L2NyZWRlbnRpYWxzL0NyZWRlbnRpYWxzU291cmNlJENyZWRlbnRpYWxzVHlwZTtMABBsb2NhbFNlcnZpY2VOYW1lcQB+AARMABdtZXNzYWdlRXhjZXB0aW9uSGFuZGxlcnEAfgAETAAMbWlsbGlzVG9MaXZlcQB+ABVMAAhwcmlvcml0eXEAfgAUTAAFcXVldWVxAH4AE0wADXJldHJ5QXR0ZW1wdHNxAH4AFEwAB3NlcnZpY2V0ABJMamF2YS9sYW5nL09iamVjdDtMAA9zZXJ2aWNlRW5kUG9pbnR0AA5MamF2YS9uZXQvVVJMO0wAE3NlcnZpY2VOYW1lU3BhY2VVUklxAH4ABEwAEHNlcnZpY2VOYW1lc3BhY2VxAH4ABEwAC3NlcnZpY2VQYXRocQB+AAR4cHNxAH4AGQFwdAAaT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2V0AE1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLmV4Y2VwdGlvbmhhbmRsaW5nLkRlZmF1bHRNZXNzYWdlRXhjZXB0aW9uSGFuZGxlcnNxAH4AH///////////c3EAfgAcAAAAA3NxAH4AGQBxAH4AMnBzcgAMamF2YS5uZXQuVVJMliU3Nhr85HIDAAdJAAhoYXNoQ29kZUkABHBvcnRMAAlhdXRob3JpdHlxAH4ABEwABGZpbGVxAH4ABEwABGhvc3RxAH4ABEwACHByb3RvY29scQB+AARMAANyZWZxAH4ABHhwaRuioAAAH5B0AA5sb2NhbGhvc3Q6ODA4MHQAKy9rci1kZXYvcmVtb3RpbmcvT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2V0AAlsb2NhbGhvc3R0AARodHRwcHh0AAB0AAZUUkFWRUx0AAEvc3IAE2phdmEudXRpbC5BcnJheUxpc3R4gdIdmcdhnQMAAUkABHNpemV4cAAAAAV3BAAAAAp0ADNvcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLnNlcnZpY2UuS1NCSmF2YVNlcnZpY2V0ADxjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5ldmVudHMuQ2FjaGVFbnRyeUV2ZW50TGlzdGVuZXJ0ADdjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5ldmVudHMuQ2FjaGVFdmVudExpc3RlbmVydAAXamF2YS51dGlsLkV2ZW50TGlzdGVuZXJ0ACxjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5MaWZlY3ljbGVBd2FyZXhxAH4AI3QABlRSQVZFTA==';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
INSERT INTO KRSB_MSG_PYLD_T (MSG_PYLD,MSG_QUE_ID)
  VALUES (EMPTY_CLOB(),412)
/
-- Length: 6088
-- Chunks: 2
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 412    FOR UPDATE;        
    buffer := 'rO0ABXNyAC1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLkFzeW5jaHJvbm91c0NhbGzxnQ5Y0t0lZQIAB1oAFWlnbm9yZVN0b3JlQW5kRm9yd2FyZFsACWFyZ3VtZW50c3QAE1tMamF2YS9sYW5nL09iamVjdDtMAAhjYWxsYmFja3QAM0xvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL0FzeW5jaHJvbm91c0NhbGxiYWNrO0wAB2NvbnRleHR0ABZMamF2YS9pby9TZXJpYWxpemFibGU7TAAKbWV0aG9kTmFtZXQAEkxqYXZhL2xhbmcvU3RyaW5nO1sACnBhcmFtVHlwZXN0ABJbTGphdmEvbGFuZy9DbGFzcztMAAtzZXJ2aWNlSW5mb3QAKkxvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL1NlcnZpY2VJbmZvO3hwAHVyABNbTGphdmEubGFuZy5PYmplY3Q7kM5YnxBzKWwCAAB4cAAAAAFzcgBDY29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLnBsdWdpbnMuY2x1c3RlcnN1cHBvcnQuQ2x1c3Rlck5vdGlmaWNhdGlvbhY+6VckvG9pAgACSQAEdHlwZUwABGRhdGFxAH4AA3hwAAAAAnQAI0RvY3VtZW50VHlwZVJ1bGVDYWNoZTpUcmF2ZWxSZXF1ZXN0cHB0AAZpbnZva2V1cgASW0xqYXZhLmxhbmcuQ2xhc3M7qxbXrsvNWpkCAAB4cAAAAAF2cgAUamF2YS5pby5TZXJpYWxpemFibGUQm2EN15tk7QIAAHhwc3IAKG9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuU2VydmljZUluZm/FFyO2KQS3ugIAC0wABWFsaXZldAATTGphdmEvbGFuZy9Cb29sZWFuO0wAFGVuZHBvaW50QWx0ZXJuYXRlVXJscQB+AARMAAtlbmRwb2ludFVybHEAfgAETAAKbG9ja1Zlck5icnQAE0xqYXZhL2xhbmcvSW50ZWdlcjtMAA5tZXNzYWdlRW50cnlJZHQAEExqYXZhL2xhbmcvTG9uZztMAAVxbmFtZXQAG0xqYXZheC94bWwvbmFtZXNwYWNlL1FOYW1lO0wAGnNlcmlhbGl6ZWRTZXJ2aWNlTmFtZXNwYWNlcQB+AARMAAhzZXJ2ZXJJcHEAfgAETAARc2VydmljZURlZmluaXRpb250ADBMb3JnL2t1YWxpL3JpY2Uva3NiL21lc3NhZ2luZy9TZXJ2aWNlRGVmaW5pdGlvbjtMAAtzZXJ2aWNlTmFtZXEAfgAETAAQc2VydmljZU5hbWVzcGFjZXEAfgAEeHBzcgARamF2YS5sYW5nLkJvb2xlYW7NIHKA1Zz67gIAAVoABXZhbHVleHABcHQAQGh0dHA6Ly9sb2NhbGhvc3Q6ODA4MC9rci1kZXYvcmVtb3RpbmcvT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2VzcgARamF2YS5sYW5nLkludGVnZXIS4qCk94GHOAIAAUkABXZhbHVleHIAEGphdmEubGFuZy5OdW1iZXKGrJUdC5TgiwIAAHhwAAAAAXNyAA5qYXZhLmxhbmcuTG9uZzuL5JDMjyPfAgABSgAFdmFsdWV4cQB+AB0AAAAAAAAPfXNyABlqYXZheC54bWwubmFtZXNwYWNlLlFOYW1lgW2oLfw73WwCAANMAAlsb2NhbFBhcnRxAH4ABEwADG5hbWVzcGFjZVVSSXEAfgAETAAGcHJlZml4cQB+AAR4cHQAGk9TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNldAAAcQB+ACR0B3RyTzBBQlhOeUFESnZjbWN1YTNWaGJHa3VjbWxqWlM1cmMySXViV1Z6YzJGbmFXNW5Ma3BoZG1GVFpYSjJhV05sUkdWbWFXNXBkR2x2YnJibkRWOUJOR3p4QWdBQlRBQVJjMlZ5ZG1salpVbHVkR1Z5Wm1GalpYTjBBQkJNYW1GMllTOTFkR2xzTDB4cGMzUTdlSElBTG05eVp5NXJkV0ZzYVM1eWFXTmxMbXR6WWk1dFpYTnpZV2RwYm1jdVUyVnlkbWxqWlVSbFptbHVhWFJwYjI0QW13SlBXTjBwZmdJQURVd0FDMkoxYzFObFkzVnlhWFI1ZEFBVFRHcGhkbUV2YkdGdVp5OUNiMjlzWldGdU8wd0FEMk55WldSbGJuUnBZV3h6Vkhsd1pYUUFURXh2Y21jdmEzVmhiR2t2Y21salpTOWpiM0psTDNObFkzVnlhWFI1TDJOeVpXUmxiblJwWVd4ekwwTnlaV1JsYm5ScFlXeHpVMjkxY21ObEpFTnlaV1JsYm5ScFlXeHpWSGx3WlR0TUFCQnNiMk5oYkZObGNuWnBZMlZPWVcxbGRBQVNUR3BoZG1FdmJHRnVaeTlUZEhKcGJtYzdUQUFYYldWemMyRm5aVVY0WTJWd2RHbHZia2hoYm1Sc1pYSnhBSDRBQlV3QURHMXBiR3hwYzFSdlRHbDJaWFFBRUV4cVlYWmhMMnhoYm1jdlRHOXVaenRNQUFod2NtbHZjbWwwZVhRQUUweHFZWFpoTDJ4aGJtY3ZTVzUwWldkbGNqdE1BQVZ4ZFdWMVpYRUFmZ0FEVEFBTmNtVjBjbmxCZEhSbGJYQjBjM0VBZmdBSFRBQUhjMlZ5ZG1salpYUUFFa3hxWVhaaEwyeGhibWN2VDJKcVpXTjBPMHdBRDNObGNuWnBZMlZGYm1SUWIybHVkSFFBRGt4cVlYWmhMMjVsZEM5VlVrdzdUQUFUYzJWeWRtbGpaVTVoYldWVGNHRmpaVlZTU1hFQWZnQUZUQUFRYzJWeWRtbGpaVTVoYldWemNHRmpaWEVBZmdBRlRBQUxjMlZ5ZG1salpWQmhkR2h4QUg0QUJYaHdjM0lBRVdwaGRtRXViR0Z1Wnk1Q2IyOXNaV0Z1elNCeWdOV2MrdTRDQUFGYUFBVjJZV3gxWlhod0FYQjBBQnBQVTBOaFkyaGxUbTkwYVdacFkyRjBhVzl1VTJWeWRtbGpaWFFBVFc5eVp5NXJkV0ZzYVM1eWFXTmxMbXR6WWk1dFpYTnpZV2RwYm1jdVpYaGpaWEIwYVc5dWFHRnVaR3hwYm1jdVJHVm1ZWFZzZEUxbGMzTmhaMlZGZUdObGNIUnBiMjVJWVc1a2JHVnljM0lBRG1waGRtRXViR0Z1Wnk1TWIyNW5PNHZra015UEk5OENBQUZLQUFWMllXeDFaWGh5QUJCcVlYWmhMbXhoYm1jdVRuVnRZbVZ5aHF5VkhRdVU0SXNDQUFCNGNQLy8vLy8vLy8vL2MzSUFFV3BoZG1FdWJHRnVaeTVKYm5SbFoyVnlFdUtncFBlQmh6Z0NBQUZKQUFWMllXeDFaWGh4QUg0QUVBQUFBQU56Y1FCK0FBc0FjUUIrQUJOd2MzSUFER3BoZG1FdWJtVjBMbFZTVEpZbE56WWEvT1J5QXdBSFNRQUlhR0Z6YUVOdlpHVkpBQVJ3YjNKMFRBQUpZWFYwYUc5eWFYUjVjUUIrQUFWTUFBUm1hV3hsY1FCK0FBVk1BQVJvYjNOMGNRQitBQVZNQUFod2NtOTBiMk52YkhFQWZnQUZUQUFEY21WbWNRQitBQVY0Y1AvLy8vOEFBQitRZEFBT2JHOWpZV3hvYjNOME9qZ3dPREIwQUNzdmEzSXRaR1YyTDNKbGJXOTBhVzVuTDA5VFEyRmphR1ZPYjNScFptbGpZWFJwYjI1VFpYSjJhV05sZEFBSmJHOWpZV3hvYjNOMGRBQUVhSFIwY0hCNGRBQUFkQUFHVkZKQlZrVk1kQUFCTDNOeUFCTnFZWFpoTG5WMGFXd3VRWEp5WVhsTWFYTjBlSUhTSFpuSFlaMERBQUZKQUFSemFYcGxlSEFBQUFBRmR3UUFBQUFLZEFBemIzSm5MbXQxWVd4cExuSnBZ';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 412    FOR UPDATE;        
    buffer := 'MlV1YTNOaUxtMWxjM05oWjJsdVp5NXpaWEoyYVdObExrdFRRa3BoZG1GVFpYSjJhV05sZEFBOFkyOXRMbTl3Wlc1emVXMXdhRzl1ZVM1dmMyTmhZMmhsTG1KaGMyVXVaWFpsYm5SekxrTmhZMmhsUlc1MGNubEZkbVZ1ZEV4cGMzUmxibVZ5ZEFBM1kyOXRMbTl3Wlc1emVXMXdhRzl1ZVM1dmMyTmhZMmhsTG1KaGMyVXVaWFpsYm5SekxrTmhZMmhsUlhabGJuUk1hWE4wWlc1bGNuUUFGMnBoZG1FdWRYUnBiQzVGZG1WdWRFeHBjM1JsYm1WeWRBQXNZMjl0TG05d1pXNXplVzF3YUc5dWVTNXZjMk5oWTJobExtSmhjMlV1VEdsbVpXTjVZMnhsUVhkaGNtVjR0AA0xMjkuMTg2Ljc5LjQ1c3IAMm9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuSmF2YVNlcnZpY2VEZWZpbml0aW9utucNX0E0bPECAAFMABFzZXJ2aWNlSW50ZXJmYWNlc3QAEExqYXZhL3V0aWwvTGlzdDt4cgAub3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5TZXJ2aWNlRGVmaW5pdGlvbgCbAk9Y3Sl+AgANTAALYnVzU2VjdXJpdHlxAH4AE0wAD2NyZWRlbnRpYWxzVHlwZXQATExvcmcva3VhbGkvcmljZS9jb3JlL3NlY3VyaXR5L2NyZWRlbnRpYWxzL0NyZWRlbnRpYWxzU291cmNlJENyZWRlbnRpYWxzVHlwZTtMABBsb2NhbFNlcnZpY2VOYW1lcQB+AARMABdtZXNzYWdlRXhjZXB0aW9uSGFuZGxlcnEAfgAETAAMbWlsbGlzVG9MaXZlcQB+ABVMAAhwcmlvcml0eXEAfgAUTAAFcXVldWVxAH4AE0wADXJldHJ5QXR0ZW1wdHNxAH4AFEwAB3NlcnZpY2V0ABJMamF2YS9sYW5nL09iamVjdDtMAA9zZXJ2aWNlRW5kUG9pbnR0AA5MamF2YS9uZXQvVVJMO0wAE3NlcnZpY2VOYW1lU3BhY2VVUklxAH4ABEwAEHNlcnZpY2VOYW1lc3BhY2VxAH4ABEwAC3NlcnZpY2VQYXRocQB+AAR4cHNxAH4AGQFwdAAaT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2V0AE1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLmV4Y2VwdGlvbmhhbmRsaW5nLkRlZmF1bHRNZXNzYWdlRXhjZXB0aW9uSGFuZGxlcnNxAH4AH///////////c3EAfgAcAAAAA3NxAH4AGQBxAH4AMnBzcgAMamF2YS5uZXQuVVJMliU3Nhr85HIDAAdJAAhoYXNoQ29kZUkABHBvcnRMAAlhdXRob3JpdHlxAH4ABEwABGZpbGVxAH4ABEwABGhvc3RxAH4ABEwACHByb3RvY29scQB+AARMAANyZWZxAH4ABHhwaRuioAAAH5B0AA5sb2NhbGhvc3Q6ODA4MHQAKy9rci1kZXYvcmVtb3RpbmcvT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2V0AAlsb2NhbGhvc3R0AARodHRwcHh0AAB0AAZUUkFWRUx0AAEvc3IAE2phdmEudXRpbC5BcnJheUxpc3R4gdIdmcdhnQMAAUkABHNpemV4cAAAAAV3BAAAAAp0ADNvcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLnNlcnZpY2UuS1NCSmF2YVNlcnZpY2V0ADxjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5ldmVudHMuQ2FjaGVFbnRyeUV2ZW50TGlzdGVuZXJ0ADdjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5ldmVudHMuQ2FjaGVFdmVudExpc3RlbmVydAAXamF2YS51dGlsLkV2ZW50TGlzdGVuZXJ0ACxjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5MaWZlY3ljbGVBd2FyZXhxAH4AI3QABlRSQVZFTA==';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
INSERT INTO KRSB_MSG_PYLD_T (MSG_PYLD,MSG_QUE_ID)
  VALUES (EMPTY_CLOB(),413)
/
-- Length: 6112
-- Chunks: 2
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 413    FOR UPDATE;        
    buffer := 'rO0ABXNyAC1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLkFzeW5jaHJvbm91c0NhbGzxnQ5Y0t0lZQIAB1oAFWlnbm9yZVN0b3JlQW5kRm9yd2FyZFsACWFyZ3VtZW50c3QAE1tMamF2YS9sYW5nL09iamVjdDtMAAhjYWxsYmFja3QAM0xvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL0FzeW5jaHJvbm91c0NhbGxiYWNrO0wAB2NvbnRleHR0ABZMamF2YS9pby9TZXJpYWxpemFibGU7TAAKbWV0aG9kTmFtZXQAEkxqYXZhL2xhbmcvU3RyaW5nO1sACnBhcmFtVHlwZXN0ABJbTGphdmEvbGFuZy9DbGFzcztMAAtzZXJ2aWNlSW5mb3QAKkxvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL1NlcnZpY2VJbmZvO3hwAHVyABNbTGphdmEubGFuZy5PYmplY3Q7kM5YnxBzKWwCAAB4cAAAAAFzcgBDY29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLnBsdWdpbnMuY2x1c3RlcnN1cHBvcnQuQ2x1c3Rlck5vdGlmaWNhdGlvbhY+6VckvG9pAgACSQAEdHlwZUwABGRhdGFxAH4AA3hwAAAAAnQANkRvY3VtZW50VHlwZVJ1bGVDYWNoZTpJZGVudGl0eU1hbmFnZW1lbnRQZXJzb25Eb2N1bWVudHBwdAAGaW52b2tldXIAEltMamF2YS5sYW5nLkNsYXNzO6sW167LzVqZAgAAeHAAAAABdnIAFGphdmEuaW8uU2VyaWFsaXphYmxlEJthDdebZO0CAAB4cHNyAChvcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLlNlcnZpY2VJbmZvxRcjtikEt7oCAAtMAAVhbGl2ZXQAE0xqYXZhL2xhbmcvQm9vbGVhbjtMABRlbmRwb2ludEFsdGVybmF0ZVVybHEAfgAETAALZW5kcG9pbnRVcmxxAH4ABEwACmxvY2tWZXJOYnJ0ABNMamF2YS9sYW5nL0ludGVnZXI7TAAObWVzc2FnZUVudHJ5SWR0ABBMamF2YS9sYW5nL0xvbmc7TAAFcW5hbWV0ABtMamF2YXgveG1sL25hbWVzcGFjZS9RTmFtZTtMABpzZXJpYWxpemVkU2VydmljZU5hbWVzcGFjZXEAfgAETAAIc2VydmVySXBxAH4ABEwAEXNlcnZpY2VEZWZpbml0aW9udAAwTG9yZy9rdWFsaS9yaWNlL2tzYi9tZXNzYWdpbmcvU2VydmljZURlZmluaXRpb247TAALc2VydmljZU5hbWVxAH4ABEwAEHNlcnZpY2VOYW1lc3BhY2VxAH4ABHhwc3IAEWphdmEubGFuZy5Cb29sZWFuzSBygNWc+u4CAAFaAAV2YWx1ZXhwAXB0AEBodHRwOi8vbG9jYWxob3N0OjgwODAva3ItZGV2L3JlbW90aW5nL09TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNlc3IAEWphdmEubGFuZy5JbnRlZ2VyEuKgpPeBhzgCAAFJAAV2YWx1ZXhyABBqYXZhLmxhbmcuTnVtYmVyhqyVHQuU4IsCAAB4cAAAAAFzcgAOamF2YS5sYW5nLkxvbmc7i+SQzI8j3wIAAUoABXZhbHVleHEAfgAdAAAAAAAAD31zcgAZamF2YXgueG1sLm5hbWVzcGFjZS5RTmFtZYFtqC38O91sAgADTAAJbG9jYWxQYXJ0cQB+AARMAAxuYW1lc3BhY2VVUklxAH4ABEwABnByZWZpeHEAfgAEeHB0ABpPU0NhY2hlTm90aWZpY2F0aW9uU2VydmljZXQAAHEAfgAkdAd0ck8wQUJYTnlBREp2Y21jdWEzVmhiR2t1Y21salpTNXJjMkl1YldWemMyRm5hVzVuTGtwaGRtRlRaWEoyYVdObFJHVm1hVzVwZEdsdmJyYm5EVjlCTkd6eEFnQUJUQUFSYzJWeWRtbGpaVWx1ZEdWeVptRmpaWE4wQUJCTWFtRjJZUzkxZEdsc0wweHBjM1E3ZUhJQUxtOXlaeTVyZFdGc2FTNXlhV05sTG10ellpNXRaWE56WVdkcGJtY3VVMlZ5ZG1salpVUmxabWx1YVhScGIyNEFtd0pQV04wcGZnSUFEVXdBQzJKMWMxTmxZM1Z5YVhSNWRBQVRUR3BoZG1FdmJHRnVaeTlDYjI5c1pXRnVPMHdBRDJOeVpXUmxiblJwWVd4elZIbHdaWFFBVEV4dmNtY3ZhM1ZoYkdrdmNtbGpaUzlqYjNKbEwzTmxZM1Z5YVhSNUwyTnlaV1JsYm5ScFlXeHpMME55WldSbGJuUnBZV3h6VTI5MWNtTmxKRU55WldSbGJuUnBZV3h6Vkhsd1pUdE1BQkJzYjJOaGJGTmxjblpwWTJWT1lXMWxkQUFTVEdwaGRtRXZiR0Z1Wnk5VGRISnBibWM3VEFBWGJXVnpjMkZuWlVWNFkyVndkR2x2YmtoaGJtUnNaWEp4QUg0QUJVd0FERzFwYkd4cGMxUnZUR2wyWlhRQUVFeHFZWFpoTDJ4aGJtY3ZURzl1Wnp0TUFBaHdjbWx2Y21sMGVYUUFFMHhxWVhaaEwyeGhibWN2U1c1MFpXZGxjanRNQUFWeGRXVjFaWEVBZmdBRFRBQU5jbVYwY25sQmRIUmxiWEIwYzNFQWZnQUhUQUFIYzJWeWRtbGpaWFFBRWt4cVlYWmhMMnhoYm1jdlQySnFaV04wTzB3QUQzTmxjblpwWTJWRmJtUlFiMmx1ZEhRQURreHFZWFpoTDI1bGRDOVZVa3c3VEFBVGMyVnlkbWxqWlU1aGJXVlRjR0ZqWlZWU1NYRUFmZ0FGVEFBUWMyVnlkbWxqWlU1aGJXVnpjR0ZqWlhFQWZnQUZUQUFMYzJWeWRtbGpaVkJoZEdoeEFINEFCWGh3YzNJQUVXcGhkbUV1YkdGdVp5NUNiMjlzWldGdXpTQnlnTldjK3U0Q0FBRmFBQVYyWVd4MVpYaHdBWEIwQUJwUFUwTmhZMmhsVG05MGFXWnBZMkYwYVc5dVUyVnlkbWxqWlhRQVRXOXlaeTVyZFdGc2FTNXlhV05sTG10ellpNXRaWE56WVdkcGJtY3VaWGhqWlhCMGFXOXVhR0Z1Wkd4cGJtY3VSR1ZtWVhWc2RFMWxjM05oWjJWRmVHTmxjSFJwYjI1SVlXNWtiR1Z5YzNJQURtcGhkbUV1YkdGdVp5NU1iMjVuTzR2a2tNeVBJOThDQUFGS0FBVjJZV3gxWlhoeUFCQnFZWFpoTG14aGJtY3VUblZ0WW1WeWhxeVZIUXVVNElzQ0FBQjRjUC8vLy8vLy8vLy9jM0lBRVdwaGRtRXViR0Z1Wnk1SmJuUmxaMlZ5RXVLZ3BQZUJoemdDQUFGSkFBVjJZV3gxWlhoeEFINEFFQUFBQUFOemNRQitBQXNBY1FCK0FCTndjM0lBREdwaGRtRXVibVYwTGxWU1RKWWxOellhL09SeUF3QUhTUUFJYUdGemFFTnZaR1ZKQUFSd2IzSjBUQUFKWVhWMGFHOXlhWFI1Y1FCK0FBVk1BQVJtYVd4bGNRQitBQVZNQUFSb2IzTjBjUUIrQUFWTUFBaHdjbTkwYjJOdmJIRUFmZ0FGVEFBRGNtVm1jUUIrQUFWNGNQLy8vLzhBQUIrUWRBQU9iRzlqWVd4b2IzTjBPamd3T0RCMEFDc3ZhM0l0WkdWMkwzSmxiVzkwYVc1bkwwOVRRMkZqYUdWT2IzUnBabWxqWVhScGIyNVRaWEoyYVdObGRBQUpiRzlqWVd4b2IzTjBkQUFFYUhSMGNIQjRkQUFBZEFBR1ZGSkJWa1ZNZEFBQkwzTnlBQk5xWVhaaExuVjBhV3d1UVhKeVlYbE1hWE4wZUlIU0habkhZWjBEQUFGSkFBUnphWHBsZUhBQUFBQUZkd1FBQUFBS2RB';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 413    FOR UPDATE;        
    buffer := 'QXpiM0puTG10MVlXeHBMbkpwWTJVdWEzTmlMbTFsYzNOaFoybHVaeTV6WlhKMmFXTmxMa3RUUWtwaGRtRlRaWEoyYVdObGRBQThZMjl0TG05d1pXNXplVzF3YUc5dWVTNXZjMk5oWTJobExtSmhjMlV1WlhabGJuUnpMa05oWTJobFJXNTBjbmxGZG1WdWRFeHBjM1JsYm1WeWRBQTNZMjl0TG05d1pXNXplVzF3YUc5dWVTNXZjMk5oWTJobExtSmhjMlV1WlhabGJuUnpMa05oWTJobFJYWmxiblJNYVhOMFpXNWxjblFBRjJwaGRtRXVkWFJwYkM1RmRtVnVkRXhwYzNSbGJtVnlkQUFzWTI5dExtOXdaVzV6ZVcxd2FHOXVlUzV2YzJOaFkyaGxMbUpoYzJVdVRHbG1aV041WTJ4bFFYZGhjbVY0dAANMTI5LjE4Ni43OS40NXNyADJvcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLkphdmFTZXJ2aWNlRGVmaW5pdGlvbrbnDV9BNGzxAgABTAARc2VydmljZUludGVyZmFjZXN0ABBMamF2YS91dGlsL0xpc3Q7eHIALm9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuU2VydmljZURlZmluaXRpb24AmwJPWN0pfgIADUwAC2J1c1NlY3VyaXR5cQB+ABNMAA9jcmVkZW50aWFsc1R5cGV0AExMb3JnL2t1YWxpL3JpY2UvY29yZS9zZWN1cml0eS9jcmVkZW50aWFscy9DcmVkZW50aWFsc1NvdXJjZSRDcmVkZW50aWFsc1R5cGU7TAAQbG9jYWxTZXJ2aWNlTmFtZXEAfgAETAAXbWVzc2FnZUV4Y2VwdGlvbkhhbmRsZXJxAH4ABEwADG1pbGxpc1RvTGl2ZXEAfgAVTAAIcHJpb3JpdHlxAH4AFEwABXF1ZXVlcQB+ABNMAA1yZXRyeUF0dGVtcHRzcQB+ABRMAAdzZXJ2aWNldAASTGphdmEvbGFuZy9PYmplY3Q7TAAPc2VydmljZUVuZFBvaW50dAAOTGphdmEvbmV0L1VSTDtMABNzZXJ2aWNlTmFtZVNwYWNlVVJJcQB+AARMABBzZXJ2aWNlTmFtZXNwYWNlcQB+AARMAAtzZXJ2aWNlUGF0aHEAfgAEeHBzcQB+ABkBcHQAGk9TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNldABNb3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5leGNlcHRpb25oYW5kbGluZy5EZWZhdWx0TWVzc2FnZUV4Y2VwdGlvbkhhbmRsZXJzcQB+AB///////////3NxAH4AHAAAAANzcQB+ABkAcQB+ADJwc3IADGphdmEubmV0LlVSTJYlNzYa/ORyAwAHSQAIaGFzaENvZGVJAARwb3J0TAAJYXV0aG9yaXR5cQB+AARMAARmaWxlcQB+AARMAARob3N0cQB+AARMAAhwcm90b2NvbHEAfgAETAADcmVmcQB+AAR4cGkboqAAAB+QdAAObG9jYWxob3N0OjgwODB0ACsva3ItZGV2L3JlbW90aW5nL09TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNldAAJbG9jYWxob3N0dAAEaHR0cHB4dAAAdAAGVFJBVkVMdAABL3NyABNqYXZhLnV0aWwuQXJyYXlMaXN0eIHSHZnHYZ0DAAFJAARzaXpleHAAAAAFdwQAAAAKdAAzb3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5zZXJ2aWNlLktTQkphdmFTZXJ2aWNldAA8Y29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLmJhc2UuZXZlbnRzLkNhY2hlRW50cnlFdmVudExpc3RlbmVydAA3Y29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLmJhc2UuZXZlbnRzLkNhY2hlRXZlbnRMaXN0ZW5lcnQAF2phdmEudXRpbC5FdmVudExpc3RlbmVydAAsY29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLmJhc2UuTGlmZWN5Y2xlQXdhcmV4cQB+ACN0AAZUUkFWRUw=';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
INSERT INTO KRSB_MSG_PYLD_T (MSG_PYLD,MSG_QUE_ID)
  VALUES (EMPTY_CLOB(),414)
/
-- Length: 6124
-- Chunks: 2
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 414    FOR UPDATE;        
    buffer := 'rO0ABXNyAC1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLkFzeW5jaHJvbm91c0NhbGzxnQ5Y0t0lZQIAB1oAFWlnbm9yZVN0b3JlQW5kRm9yd2FyZFsACWFyZ3VtZW50c3QAE1tMamF2YS9sYW5nL09iamVjdDtMAAhjYWxsYmFja3QAM0xvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL0FzeW5jaHJvbm91c0NhbGxiYWNrO0wAB2NvbnRleHR0ABZMamF2YS9pby9TZXJpYWxpemFibGU7TAAKbWV0aG9kTmFtZXQAEkxqYXZhL2xhbmcvU3RyaW5nO1sACnBhcmFtVHlwZXN0ABJbTGphdmEvbGFuZy9DbGFzcztMAAtzZXJ2aWNlSW5mb3QAKkxvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL1NlcnZpY2VJbmZvO3hwAHVyABNbTGphdmEubGFuZy5PYmplY3Q7kM5YnxBzKWwCAAB4cAAAAAFzcgBDY29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLnBsdWdpbnMuY2x1c3RlcnN1cHBvcnQuQ2x1c3Rlck5vdGlmaWNhdGlvbhY+6VckvG9pAgACSQAEdHlwZUwABGRhdGFxAH4AA3hwAAAAAnQAPkRvY3VtZW50VHlwZVJ1bGVDYWNoZTpSb3V0aW5nUnVsZURlbGVnYXRpb25NYWludGVuYW5jZURvY3VtZW50cHB0AAZpbnZva2V1cgASW0xqYXZhLmxhbmcuQ2xhc3M7qxbXrsvNWpkCAAB4cAAAAAF2cgAUamF2YS5pby5TZXJpYWxpemFibGUQm2EN15tk7QIAAHhwc3IAKG9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuU2VydmljZUluZm/FFyO2KQS3ugIAC0wABWFsaXZldAATTGphdmEvbGFuZy9Cb29sZWFuO0wAFGVuZHBvaW50QWx0ZXJuYXRlVXJscQB+AARMAAtlbmRwb2ludFVybHEAfgAETAAKbG9ja1Zlck5icnQAE0xqYXZhL2xhbmcvSW50ZWdlcjtMAA5tZXNzYWdlRW50cnlJZHQAEExqYXZhL2xhbmcvTG9uZztMAAVxbmFtZXQAG0xqYXZheC94bWwvbmFtZXNwYWNlL1FOYW1lO0wAGnNlcmlhbGl6ZWRTZXJ2aWNlTmFtZXNwYWNlcQB+AARMAAhzZXJ2ZXJJcHEAfgAETAARc2VydmljZURlZmluaXRpb250ADBMb3JnL2t1YWxpL3JpY2Uva3NiL21lc3NhZ2luZy9TZXJ2aWNlRGVmaW5pdGlvbjtMAAtzZXJ2aWNlTmFtZXEAfgAETAAQc2VydmljZU5hbWVzcGFjZXEAfgAEeHBzcgARamF2YS5sYW5nLkJvb2xlYW7NIHKA1Zz67gIAAVoABXZhbHVleHABcHQAQGh0dHA6Ly9sb2NhbGhvc3Q6ODA4MC9rci1kZXYvcmVtb3RpbmcvT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2VzcgARamF2YS5sYW5nLkludGVnZXIS4qCk94GHOAIAAUkABXZhbHVleHIAEGphdmEubGFuZy5OdW1iZXKGrJUdC5TgiwIAAHhwAAAAAXNyAA5qYXZhLmxhbmcuTG9uZzuL5JDMjyPfAgABSgAFdmFsdWV4cQB+AB0AAAAAAAAPfXNyABlqYXZheC54bWwubmFtZXNwYWNlLlFOYW1lgW2oLfw73WwCAANMAAlsb2NhbFBhcnRxAH4ABEwADG5hbWVzcGFjZVVSSXEAfgAETAAGcHJlZml4cQB+AAR4cHQAGk9TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNldAAAcQB+ACR0B3RyTzBBQlhOeUFESnZjbWN1YTNWaGJHa3VjbWxqWlM1cmMySXViV1Z6YzJGbmFXNW5Ma3BoZG1GVFpYSjJhV05sUkdWbWFXNXBkR2x2YnJibkRWOUJOR3p4QWdBQlRBQVJjMlZ5ZG1salpVbHVkR1Z5Wm1GalpYTjBBQkJNYW1GMllTOTFkR2xzTDB4cGMzUTdlSElBTG05eVp5NXJkV0ZzYVM1eWFXTmxMbXR6WWk1dFpYTnpZV2RwYm1jdVUyVnlkbWxqWlVSbFptbHVhWFJwYjI0QW13SlBXTjBwZmdJQURVd0FDMkoxYzFObFkzVnlhWFI1ZEFBVFRHcGhkbUV2YkdGdVp5OUNiMjlzWldGdU8wd0FEMk55WldSbGJuUnBZV3h6Vkhsd1pYUUFURXh2Y21jdmEzVmhiR2t2Y21salpTOWpiM0psTDNObFkzVnlhWFI1TDJOeVpXUmxiblJwWVd4ekwwTnlaV1JsYm5ScFlXeHpVMjkxY21ObEpFTnlaV1JsYm5ScFlXeHpWSGx3WlR0TUFCQnNiMk5oYkZObGNuWnBZMlZPWVcxbGRBQVNUR3BoZG1FdmJHRnVaeTlUZEhKcGJtYzdUQUFYYldWemMyRm5aVVY0WTJWd2RHbHZia2hoYm1Sc1pYSnhBSDRBQlV3QURHMXBiR3hwYzFSdlRHbDJaWFFBRUV4cVlYWmhMMnhoYm1jdlRHOXVaenRNQUFod2NtbHZjbWwwZVhRQUUweHFZWFpoTDJ4aGJtY3ZTVzUwWldkbGNqdE1BQVZ4ZFdWMVpYRUFmZ0FEVEFBTmNtVjBjbmxCZEhSbGJYQjBjM0VBZmdBSFRBQUhjMlZ5ZG1salpYUUFFa3hxWVhaaEwyeGhibWN2VDJKcVpXTjBPMHdBRDNObGNuWnBZMlZGYm1SUWIybHVkSFFBRGt4cVlYWmhMMjVsZEM5VlVrdzdUQUFUYzJWeWRtbGpaVTVoYldWVGNHRmpaVlZTU1hFQWZnQUZUQUFRYzJWeWRtbGpaVTVoYldWemNHRmpaWEVBZmdBRlRBQUxjMlZ5ZG1salpWQmhkR2h4QUg0QUJYaHdjM0lBRVdwaGRtRXViR0Z1Wnk1Q2IyOXNaV0Z1elNCeWdOV2MrdTRDQUFGYUFBVjJZV3gxWlhod0FYQjBBQnBQVTBOaFkyaGxUbTkwYVdacFkyRjBhVzl1VTJWeWRtbGpaWFFBVFc5eVp5NXJkV0ZzYVM1eWFXTmxMbXR6WWk1dFpYTnpZV2RwYm1jdVpYaGpaWEIwYVc5dWFHRnVaR3hwYm1jdVJHVm1ZWFZzZEUxbGMzTmhaMlZGZUdObGNIUnBiMjVJWVc1a2JHVnljM0lBRG1waGRtRXViR0Z1Wnk1TWIyNW5PNHZra015UEk5OENBQUZLQUFWMllXeDFaWGh5QUJCcVlYWmhMbXhoYm1jdVRuVnRZbVZ5aHF5VkhRdVU0SXNDQUFCNGNQLy8vLy8vLy8vL2MzSUFFV3BoZG1FdWJHRnVaeTVKYm5SbFoyVnlFdUtncFBlQmh6Z0NBQUZKQUFWMllXeDFaWGh4QUg0QUVBQUFBQU56Y1FCK0FBc0FjUUIrQUJOd2MzSUFER3BoZG1FdWJtVjBMbFZTVEpZbE56WWEvT1J5QXdBSFNRQUlhR0Z6YUVOdlpHVkpBQVJ3YjNKMFRBQUpZWFYwYUc5eWFYUjVjUUIrQUFWTUFBUm1hV3hsY1FCK0FBVk1BQVJvYjNOMGNRQitBQVZNQUFod2NtOTBiMk52YkhFQWZnQUZUQUFEY21WbWNRQitBQVY0Y1AvLy8vOEFBQitRZEFBT2JHOWpZV3hvYjNOME9qZ3dPREIwQUNzdmEzSXRaR1YyTDNKbGJXOTBhVzVuTDA5VFEyRmphR1ZPYjNScFptbGpZWFJwYjI1VFpYSjJhV05sZEFBSmJHOWpZV3hvYjNOMGRBQUVhSFIwY0hCNGRBQUFkQUFHVkZKQlZrVk1kQUFCTDNOeUFCTnFZWFpoTG5WMGFXd3VRWEp5WVhsTWFYTjBlSUhTSFpuSFlaMERBQUZKQUFSemFYcGxlSEFBQUFBRmR3';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 414    FOR UPDATE;        
    buffer := 'UUFBQUFLZEFBemIzSm5MbXQxWVd4cExuSnBZMlV1YTNOaUxtMWxjM05oWjJsdVp5NXpaWEoyYVdObExrdFRRa3BoZG1GVFpYSjJhV05sZEFBOFkyOXRMbTl3Wlc1emVXMXdhRzl1ZVM1dmMyTmhZMmhsTG1KaGMyVXVaWFpsYm5SekxrTmhZMmhsUlc1MGNubEZkbVZ1ZEV4cGMzUmxibVZ5ZEFBM1kyOXRMbTl3Wlc1emVXMXdhRzl1ZVM1dmMyTmhZMmhsTG1KaGMyVXVaWFpsYm5SekxrTmhZMmhsUlhabGJuUk1hWE4wWlc1bGNuUUFGMnBoZG1FdWRYUnBiQzVGZG1WdWRFeHBjM1JsYm1WeWRBQXNZMjl0TG05d1pXNXplVzF3YUc5dWVTNXZjMk5oWTJobExtSmhjMlV1VEdsbVpXTjVZMnhsUVhkaGNtVjR0AA0xMjkuMTg2Ljc5LjQ1c3IAMm9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuSmF2YVNlcnZpY2VEZWZpbml0aW9utucNX0E0bPECAAFMABFzZXJ2aWNlSW50ZXJmYWNlc3QAEExqYXZhL3V0aWwvTGlzdDt4cgAub3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5TZXJ2aWNlRGVmaW5pdGlvbgCbAk9Y3Sl+AgANTAALYnVzU2VjdXJpdHlxAH4AE0wAD2NyZWRlbnRpYWxzVHlwZXQATExvcmcva3VhbGkvcmljZS9jb3JlL3NlY3VyaXR5L2NyZWRlbnRpYWxzL0NyZWRlbnRpYWxzU291cmNlJENyZWRlbnRpYWxzVHlwZTtMABBsb2NhbFNlcnZpY2VOYW1lcQB+AARMABdtZXNzYWdlRXhjZXB0aW9uSGFuZGxlcnEAfgAETAAMbWlsbGlzVG9MaXZlcQB+ABVMAAhwcmlvcml0eXEAfgAUTAAFcXVldWVxAH4AE0wADXJldHJ5QXR0ZW1wdHNxAH4AFEwAB3NlcnZpY2V0ABJMamF2YS9sYW5nL09iamVjdDtMAA9zZXJ2aWNlRW5kUG9pbnR0AA5MamF2YS9uZXQvVVJMO0wAE3NlcnZpY2VOYW1lU3BhY2VVUklxAH4ABEwAEHNlcnZpY2VOYW1lc3BhY2VxAH4ABEwAC3NlcnZpY2VQYXRocQB+AAR4cHNxAH4AGQFwdAAaT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2V0AE1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLmV4Y2VwdGlvbmhhbmRsaW5nLkRlZmF1bHRNZXNzYWdlRXhjZXB0aW9uSGFuZGxlcnNxAH4AH///////////c3EAfgAcAAAAA3NxAH4AGQBxAH4AMnBzcgAMamF2YS5uZXQuVVJMliU3Nhr85HIDAAdJAAhoYXNoQ29kZUkABHBvcnRMAAlhdXRob3JpdHlxAH4ABEwABGZpbGVxAH4ABEwABGhvc3RxAH4ABEwACHByb3RvY29scQB+AARMAANyZWZxAH4ABHhwaRuioAAAH5B0AA5sb2NhbGhvc3Q6ODA4MHQAKy9rci1kZXYvcmVtb3RpbmcvT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2V0AAlsb2NhbGhvc3R0AARodHRwcHh0AAB0AAZUUkFWRUx0AAEvc3IAE2phdmEudXRpbC5BcnJheUxpc3R4gdIdmcdhnQMAAUkABHNpemV4cAAAAAV3BAAAAAp0ADNvcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLnNlcnZpY2UuS1NCSmF2YVNlcnZpY2V0ADxjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5ldmVudHMuQ2FjaGVFbnRyeUV2ZW50TGlzdGVuZXJ0ADdjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5ldmVudHMuQ2FjaGVFdmVudExpc3RlbmVydAAXamF2YS51dGlsLkV2ZW50TGlzdGVuZXJ0ACxjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5MaWZlY3ljbGVBd2FyZXhxAH4AI3QABlRSQVZFTA==';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
INSERT INTO KRSB_MSG_PYLD_T (MSG_PYLD,MSG_QUE_ID)
  VALUES (EMPTY_CLOB(),415)
/
-- Length: 6112
-- Chunks: 2
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 415    FOR UPDATE;        
    buffer := 'rO0ABXNyAC1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLkFzeW5jaHJvbm91c0NhbGzxnQ5Y0t0lZQIAB1oAFWlnbm9yZVN0b3JlQW5kRm9yd2FyZFsACWFyZ3VtZW50c3QAE1tMamF2YS9sYW5nL09iamVjdDtMAAhjYWxsYmFja3QAM0xvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL0FzeW5jaHJvbm91c0NhbGxiYWNrO0wAB2NvbnRleHR0ABZMamF2YS9pby9TZXJpYWxpemFibGU7TAAKbWV0aG9kTmFtZXQAEkxqYXZhL2xhbmcvU3RyaW5nO1sACnBhcmFtVHlwZXN0ABJbTGphdmEvbGFuZy9DbGFzcztMAAtzZXJ2aWNlSW5mb3QAKkxvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL1NlcnZpY2VJbmZvO3hwAHVyABNbTGphdmEubGFuZy5PYmplY3Q7kM5YnxBzKWwCAAB4cAAAAAFzcgBDY29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLnBsdWdpbnMuY2x1c3RlcnN1cHBvcnQuQ2x1c3Rlck5vdGlmaWNhdGlvbhY+6VckvG9pAgACSQAEdHlwZUwABGRhdGFxAH4AA3hwAAAAAnQANURvY3VtZW50VHlwZVJ1bGVDYWNoZTpJZGVudGl0eU1hbmFnZW1lbnRHcm91cERvY3VtZW50cHB0AAZpbnZva2V1cgASW0xqYXZhLmxhbmcuQ2xhc3M7qxbXrsvNWpkCAAB4cAAAAAF2cgAUamF2YS5pby5TZXJpYWxpemFibGUQm2EN15tk7QIAAHhwc3IAKG9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuU2VydmljZUluZm/FFyO2KQS3ugIAC0wABWFsaXZldAATTGphdmEvbGFuZy9Cb29sZWFuO0wAFGVuZHBvaW50QWx0ZXJuYXRlVXJscQB+AARMAAtlbmRwb2ludFVybHEAfgAETAAKbG9ja1Zlck5icnQAE0xqYXZhL2xhbmcvSW50ZWdlcjtMAA5tZXNzYWdlRW50cnlJZHQAEExqYXZhL2xhbmcvTG9uZztMAAVxbmFtZXQAG0xqYXZheC94bWwvbmFtZXNwYWNlL1FOYW1lO0wAGnNlcmlhbGl6ZWRTZXJ2aWNlTmFtZXNwYWNlcQB+AARMAAhzZXJ2ZXJJcHEAfgAETAARc2VydmljZURlZmluaXRpb250ADBMb3JnL2t1YWxpL3JpY2Uva3NiL21lc3NhZ2luZy9TZXJ2aWNlRGVmaW5pdGlvbjtMAAtzZXJ2aWNlTmFtZXEAfgAETAAQc2VydmljZU5hbWVzcGFjZXEAfgAEeHBzcgARamF2YS5sYW5nLkJvb2xlYW7NIHKA1Zz67gIAAVoABXZhbHVleHABcHQAQGh0dHA6Ly9sb2NhbGhvc3Q6ODA4MC9rci1kZXYvcmVtb3RpbmcvT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2VzcgARamF2YS5sYW5nLkludGVnZXIS4qCk94GHOAIAAUkABXZhbHVleHIAEGphdmEubGFuZy5OdW1iZXKGrJUdC5TgiwIAAHhwAAAAAXNyAA5qYXZhLmxhbmcuTG9uZzuL5JDMjyPfAgABSgAFdmFsdWV4cQB+AB0AAAAAAAAPfXNyABlqYXZheC54bWwubmFtZXNwYWNlLlFOYW1lgW2oLfw73WwCAANMAAlsb2NhbFBhcnRxAH4ABEwADG5hbWVzcGFjZVVSSXEAfgAETAAGcHJlZml4cQB+AAR4cHQAGk9TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNldAAAcQB+ACR0B3RyTzBBQlhOeUFESnZjbWN1YTNWaGJHa3VjbWxqWlM1cmMySXViV1Z6YzJGbmFXNW5Ma3BoZG1GVFpYSjJhV05sUkdWbWFXNXBkR2x2YnJibkRWOUJOR3p4QWdBQlRBQVJjMlZ5ZG1salpVbHVkR1Z5Wm1GalpYTjBBQkJNYW1GMllTOTFkR2xzTDB4cGMzUTdlSElBTG05eVp5NXJkV0ZzYVM1eWFXTmxMbXR6WWk1dFpYTnpZV2RwYm1jdVUyVnlkbWxqWlVSbFptbHVhWFJwYjI0QW13SlBXTjBwZmdJQURVd0FDMkoxYzFObFkzVnlhWFI1ZEFBVFRHcGhkbUV2YkdGdVp5OUNiMjlzWldGdU8wd0FEMk55WldSbGJuUnBZV3h6Vkhsd1pYUUFURXh2Y21jdmEzVmhiR2t2Y21salpTOWpiM0psTDNObFkzVnlhWFI1TDJOeVpXUmxiblJwWVd4ekwwTnlaV1JsYm5ScFlXeHpVMjkxY21ObEpFTnlaV1JsYm5ScFlXeHpWSGx3WlR0TUFCQnNiMk5oYkZObGNuWnBZMlZPWVcxbGRBQVNUR3BoZG1FdmJHRnVaeTlUZEhKcGJtYzdUQUFYYldWemMyRm5aVVY0WTJWd2RHbHZia2hoYm1Sc1pYSnhBSDRBQlV3QURHMXBiR3hwYzFSdlRHbDJaWFFBRUV4cVlYWmhMMnhoYm1jdlRHOXVaenRNQUFod2NtbHZjbWwwZVhRQUUweHFZWFpoTDJ4aGJtY3ZTVzUwWldkbGNqdE1BQVZ4ZFdWMVpYRUFmZ0FEVEFBTmNtVjBjbmxCZEhSbGJYQjBjM0VBZmdBSFRBQUhjMlZ5ZG1salpYUUFFa3hxWVhaaEwyeGhibWN2VDJKcVpXTjBPMHdBRDNObGNuWnBZMlZGYm1SUWIybHVkSFFBRGt4cVlYWmhMMjVsZEM5VlVrdzdUQUFUYzJWeWRtbGpaVTVoYldWVGNHRmpaVlZTU1hFQWZnQUZUQUFRYzJWeWRtbGpaVTVoYldWemNHRmpaWEVBZmdBRlRBQUxjMlZ5ZG1salpWQmhkR2h4QUg0QUJYaHdjM0lBRVdwaGRtRXViR0Z1Wnk1Q2IyOXNaV0Z1elNCeWdOV2MrdTRDQUFGYUFBVjJZV3gxWlhod0FYQjBBQnBQVTBOaFkyaGxUbTkwYVdacFkyRjBhVzl1VTJWeWRtbGpaWFFBVFc5eVp5NXJkV0ZzYVM1eWFXTmxMbXR6WWk1dFpYTnpZV2RwYm1jdVpYaGpaWEIwYVc5dWFHRnVaR3hwYm1jdVJHVm1ZWFZzZEUxbGMzTmhaMlZGZUdObGNIUnBiMjVJWVc1a2JHVnljM0lBRG1waGRtRXViR0Z1Wnk1TWIyNW5PNHZra015UEk5OENBQUZLQUFWMllXeDFaWGh5QUJCcVlYWmhMbXhoYm1jdVRuVnRZbVZ5aHF5VkhRdVU0SXNDQUFCNGNQLy8vLy8vLy8vL2MzSUFFV3BoZG1FdWJHRnVaeTVKYm5SbFoyVnlFdUtncFBlQmh6Z0NBQUZKQUFWMllXeDFaWGh4QUg0QUVBQUFBQU56Y1FCK0FBc0FjUUIrQUJOd2MzSUFER3BoZG1FdWJtVjBMbFZTVEpZbE56WWEvT1J5QXdBSFNRQUlhR0Z6YUVOdlpHVkpBQVJ3YjNKMFRBQUpZWFYwYUc5eWFYUjVjUUIrQUFWTUFBUm1hV3hsY1FCK0FBVk1BQVJvYjNOMGNRQitBQVZNQUFod2NtOTBiMk52YkhFQWZnQUZUQUFEY21WbWNRQitBQVY0Y1AvLy8vOEFBQitRZEFBT2JHOWpZV3hvYjNOME9qZ3dPREIwQUNzdmEzSXRaR1YyTDNKbGJXOTBhVzVuTDA5VFEyRmphR1ZPYjNScFptbGpZWFJwYjI1VFpYSjJhV05sZEFBSmJHOWpZV3hvYjNOMGRBQUVhSFIwY0hCNGRBQUFkQUFHVkZKQlZrVk1kQUFCTDNOeUFCTnFZWFpoTG5WMGFXd3VRWEp5WVhsTWFYTjBlSUhTSFpuSFlaMERBQUZKQUFSemFYcGxlSEFBQUFBRmR3UUFBQUFLZEFB';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 415    FOR UPDATE;        
    buffer := 'emIzSm5MbXQxWVd4cExuSnBZMlV1YTNOaUxtMWxjM05oWjJsdVp5NXpaWEoyYVdObExrdFRRa3BoZG1GVFpYSjJhV05sZEFBOFkyOXRMbTl3Wlc1emVXMXdhRzl1ZVM1dmMyTmhZMmhsTG1KaGMyVXVaWFpsYm5SekxrTmhZMmhsUlc1MGNubEZkbVZ1ZEV4cGMzUmxibVZ5ZEFBM1kyOXRMbTl3Wlc1emVXMXdhRzl1ZVM1dmMyTmhZMmhsTG1KaGMyVXVaWFpsYm5SekxrTmhZMmhsUlhabGJuUk1hWE4wWlc1bGNuUUFGMnBoZG1FdWRYUnBiQzVGZG1WdWRFeHBjM1JsYm1WeWRBQXNZMjl0TG05d1pXNXplVzF3YUc5dWVTNXZjMk5oWTJobExtSmhjMlV1VEdsbVpXTjVZMnhsUVhkaGNtVjR0AA0xMjkuMTg2Ljc5LjQ1c3IAMm9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuSmF2YVNlcnZpY2VEZWZpbml0aW9utucNX0E0bPECAAFMABFzZXJ2aWNlSW50ZXJmYWNlc3QAEExqYXZhL3V0aWwvTGlzdDt4cgAub3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5TZXJ2aWNlRGVmaW5pdGlvbgCbAk9Y3Sl+AgANTAALYnVzU2VjdXJpdHlxAH4AE0wAD2NyZWRlbnRpYWxzVHlwZXQATExvcmcva3VhbGkvcmljZS9jb3JlL3NlY3VyaXR5L2NyZWRlbnRpYWxzL0NyZWRlbnRpYWxzU291cmNlJENyZWRlbnRpYWxzVHlwZTtMABBsb2NhbFNlcnZpY2VOYW1lcQB+AARMABdtZXNzYWdlRXhjZXB0aW9uSGFuZGxlcnEAfgAETAAMbWlsbGlzVG9MaXZlcQB+ABVMAAhwcmlvcml0eXEAfgAUTAAFcXVldWVxAH4AE0wADXJldHJ5QXR0ZW1wdHNxAH4AFEwAB3NlcnZpY2V0ABJMamF2YS9sYW5nL09iamVjdDtMAA9zZXJ2aWNlRW5kUG9pbnR0AA5MamF2YS9uZXQvVVJMO0wAE3NlcnZpY2VOYW1lU3BhY2VVUklxAH4ABEwAEHNlcnZpY2VOYW1lc3BhY2VxAH4ABEwAC3NlcnZpY2VQYXRocQB+AAR4cHNxAH4AGQFwdAAaT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2V0AE1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLmV4Y2VwdGlvbmhhbmRsaW5nLkRlZmF1bHRNZXNzYWdlRXhjZXB0aW9uSGFuZGxlcnNxAH4AH///////////c3EAfgAcAAAAA3NxAH4AGQBxAH4AMnBzcgAMamF2YS5uZXQuVVJMliU3Nhr85HIDAAdJAAhoYXNoQ29kZUkABHBvcnRMAAlhdXRob3JpdHlxAH4ABEwABGZpbGVxAH4ABEwABGhvc3RxAH4ABEwACHByb3RvY29scQB+AARMAANyZWZxAH4ABHhwaRuioAAAH5B0AA5sb2NhbGhvc3Q6ODA4MHQAKy9rci1kZXYvcmVtb3RpbmcvT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2V0AAlsb2NhbGhvc3R0AARodHRwcHh0AAB0AAZUUkFWRUx0AAEvc3IAE2phdmEudXRpbC5BcnJheUxpc3R4gdIdmcdhnQMAAUkABHNpemV4cAAAAAV3BAAAAAp0ADNvcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLnNlcnZpY2UuS1NCSmF2YVNlcnZpY2V0ADxjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5ldmVudHMuQ2FjaGVFbnRyeUV2ZW50TGlzdGVuZXJ0ADdjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5ldmVudHMuQ2FjaGVFdmVudExpc3RlbmVydAAXamF2YS51dGlsLkV2ZW50TGlzdGVuZXJ0ACxjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5MaWZlY3ljbGVBd2FyZXhxAH4AI3QABlRSQVZFTA==';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
INSERT INTO KRSB_MSG_PYLD_T (MSG_PYLD,MSG_QUE_ID)
  VALUES (EMPTY_CLOB(),416)
/
-- Length: 6108
-- Chunks: 2
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 416    FOR UPDATE;        
    buffer := 'rO0ABXNyAC1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLkFzeW5jaHJvbm91c0NhbGzxnQ5Y0t0lZQIAB1oAFWlnbm9yZVN0b3JlQW5kRm9yd2FyZFsACWFyZ3VtZW50c3QAE1tMamF2YS9sYW5nL09iamVjdDtMAAhjYWxsYmFja3QAM0xvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL0FzeW5jaHJvbm91c0NhbGxiYWNrO0wAB2NvbnRleHR0ABZMamF2YS9pby9TZXJpYWxpemFibGU7TAAKbWV0aG9kTmFtZXQAEkxqYXZhL2xhbmcvU3RyaW5nO1sACnBhcmFtVHlwZXN0ABJbTGphdmEvbGFuZy9DbGFzcztMAAtzZXJ2aWNlSW5mb3QAKkxvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL1NlcnZpY2VJbmZvO3hwAHVyABNbTGphdmEubGFuZy5PYmplY3Q7kM5YnxBzKWwCAAB4cAAAAAFzcgBDY29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLnBsdWdpbnMuY2x1c3RlcnN1cHBvcnQuQ2x1c3Rlck5vdGlmaWNhdGlvbhY+6VckvG9pAgACSQAEdHlwZUwABGRhdGFxAH4AA3hwAAAAAnQANERvY3VtZW50VHlwZVJ1bGVDYWNoZTpJZGVudGl0eU1hbmFnZW1lbnRSb2xlRG9jdW1lbnRwcHQABmludm9rZXVyABJbTGphdmEubGFuZy5DbGFzczurFteuy81amQIAAHhwAAAAAXZyABRqYXZhLmlvLlNlcmlhbGl6YWJsZRCbYQ3Xm2TtAgAAeHBzcgAob3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5TZXJ2aWNlSW5mb8UXI7YpBLe6AgALTAAFYWxpdmV0ABNMamF2YS9sYW5nL0Jvb2xlYW47TAAUZW5kcG9pbnRBbHRlcm5hdGVVcmxxAH4ABEwAC2VuZHBvaW50VXJscQB+AARMAApsb2NrVmVyTmJydAATTGphdmEvbGFuZy9JbnRlZ2VyO0wADm1lc3NhZ2VFbnRyeUlkdAAQTGphdmEvbGFuZy9Mb25nO0wABXFuYW1ldAAbTGphdmF4L3htbC9uYW1lc3BhY2UvUU5hbWU7TAAac2VyaWFsaXplZFNlcnZpY2VOYW1lc3BhY2VxAH4ABEwACHNlcnZlcklwcQB+AARMABFzZXJ2aWNlRGVmaW5pdGlvbnQAMExvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL1NlcnZpY2VEZWZpbml0aW9uO0wAC3NlcnZpY2VOYW1lcQB+AARMABBzZXJ2aWNlTmFtZXNwYWNlcQB+AAR4cHNyABFqYXZhLmxhbmcuQm9vbGVhbs0gcoDVnPruAgABWgAFdmFsdWV4cAFwdABAaHR0cDovL2xvY2FsaG9zdDo4MDgwL2tyLWRldi9yZW1vdGluZy9PU0NhY2hlTm90aWZpY2F0aW9uU2VydmljZXNyABFqYXZhLmxhbmcuSW50ZWdlchLioKT3gYc4AgABSQAFdmFsdWV4cgAQamF2YS5sYW5nLk51bWJlcoaslR0LlOCLAgAAeHAAAAABc3IADmphdmEubGFuZy5Mb25nO4vkkMyPI98CAAFKAAV2YWx1ZXhxAH4AHQAAAAAAAA99c3IAGWphdmF4LnhtbC5uYW1lc3BhY2UuUU5hbWWBbagt/DvdbAIAA0wACWxvY2FsUGFydHEAfgAETAAMbmFtZXNwYWNlVVJJcQB+AARMAAZwcmVmaXhxAH4ABHhwdAAaT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2V0AABxAH4AJHQHdHJPMEFCWE55QURKdmNtY3VhM1ZoYkdrdWNtbGpaUzVyYzJJdWJXVnpjMkZuYVc1bkxrcGhkbUZUWlhKMmFXTmxSR1ZtYVc1cGRHbHZicmJuRFY5Qk5HenhBZ0FCVEFBUmMyVnlkbWxqWlVsdWRHVnlabUZqWlhOMEFCQk1hbUYyWVM5MWRHbHNMMHhwYzNRN2VISUFMbTl5Wnk1cmRXRnNhUzV5YVdObExtdHpZaTV0WlhOellXZHBibWN1VTJWeWRtbGpaVVJsWm1sdWFYUnBiMjRBbXdKUFdOMHBmZ0lBRFV3QUMySjFjMU5sWTNWeWFYUjVkQUFUVEdwaGRtRXZiR0Z1Wnk5Q2IyOXNaV0Z1TzB3QUQyTnlaV1JsYm5ScFlXeHpWSGx3WlhRQVRFeHZjbWN2YTNWaGJHa3ZjbWxqWlM5amIzSmxMM05sWTNWeWFYUjVMMk55WldSbGJuUnBZV3h6TDBOeVpXUmxiblJwWVd4elUyOTFjbU5sSkVOeVpXUmxiblJwWVd4elZIbHdaVHRNQUJCc2IyTmhiRk5sY25acFkyVk9ZVzFsZEFBU1RHcGhkbUV2YkdGdVp5OVRkSEpwYm1jN1RBQVhiV1Z6YzJGblpVVjRZMlZ3ZEdsdmJraGhibVJzWlhKeEFINEFCVXdBREcxcGJHeHBjMVJ2VEdsMlpYUUFFRXhxWVhaaEwyeGhibWN2VEc5dVp6dE1BQWh3Y21sdmNtbDBlWFFBRTB4cVlYWmhMMnhoYm1jdlNXNTBaV2RsY2p0TUFBVnhkV1YxWlhFQWZnQURUQUFOY21WMGNubEJkSFJsYlhCMGMzRUFmZ0FIVEFBSGMyVnlkbWxqWlhRQUVreHFZWFpoTDJ4aGJtY3ZUMkpxWldOME8wd0FEM05sY25acFkyVkZibVJRYjJsdWRIUUFEa3hxWVhaaEwyNWxkQzlWVWt3N1RBQVRjMlZ5ZG1salpVNWhiV1ZUY0dGalpWVlNTWEVBZmdBRlRBQVFjMlZ5ZG1salpVNWhiV1Z6Y0dGalpYRUFmZ0FGVEFBTGMyVnlkbWxqWlZCaGRHaHhBSDRBQlhod2MzSUFFV3BoZG1FdWJHRnVaeTVDYjI5c1pXRnV6U0J5Z05XYyt1NENBQUZhQUFWMllXeDFaWGh3QVhCMEFCcFBVME5oWTJobFRtOTBhV1pwWTJGMGFXOXVVMlZ5ZG1salpYUUFUVzl5Wnk1cmRXRnNhUzV5YVdObExtdHpZaTV0WlhOellXZHBibWN1WlhoalpYQjBhVzl1YUdGdVpHeHBibWN1UkdWbVlYVnNkRTFsYzNOaFoyVkZlR05sY0hScGIyNUlZVzVrYkdWeWMzSUFEbXBoZG1FdWJHRnVaeTVNYjI1bk80dmtrTXlQSTk4Q0FBRktBQVYyWVd4MVpYaHlBQkJxWVhaaExteGhibWN1VG5WdFltVnlocXlWSFF1VTRJc0NBQUI0Y1AvLy8vLy8vLy8vYzNJQUVXcGhkbUV1YkdGdVp5NUpiblJsWjJWeUV1S2dwUGVCaHpnQ0FBRkpBQVYyWVd4MVpYaHhBSDRBRUFBQUFBTnpjUUIrQUFzQWNRQitBQk53YzNJQURHcGhkbUV1Ym1WMExsVlNUSllsTnpZYS9PUnlBd0FIU1FBSWFHRnphRU52WkdWSkFBUndiM0owVEFBSllYVjBhRzl5YVhSNWNRQitBQVZNQUFSbWFXeGxjUUIrQUFWTUFBUm9iM04wY1FCK0FBVk1BQWh3Y205MGIyTnZiSEVBZmdBRlRBQURjbVZtY1FCK0FBVjRjUC8vLy84QUFCK1FkQUFPYkc5allXeG9iM04wT2pnd09EQjBBQ3N2YTNJdFpHVjJMM0psYlc5MGFXNW5MMDlUUTJGamFHVk9iM1JwWm1sallYUnBiMjVUWlhKMmFXTmxkQUFKYkc5allXeG9iM04wZEFBRWFIUjBjSEI0ZEFBQWRBQUdWRkpCVmtWTWRBQUJMM055QUJOcVlYWmhMblYwYVd3dVFYSnlZWGxNYVhOMGVJSFNIWm5IWVowREFBRkpBQVJ6YVhwbGVIQUFBQUFGZHdRQUFBQUtkQUF6';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 416    FOR UPDATE;        
    buffer := 'YjNKbkxtdDFZV3hwTG5KcFkyVXVhM05pTG0xbGMzTmhaMmx1Wnk1elpYSjJhV05sTGt0VFFrcGhkbUZUWlhKMmFXTmxkQUE4WTI5dExtOXdaVzV6ZVcxd2FHOXVlUzV2YzJOaFkyaGxMbUpoYzJVdVpYWmxiblJ6TGtOaFkyaGxSVzUwY25sRmRtVnVkRXhwYzNSbGJtVnlkQUEzWTI5dExtOXdaVzV6ZVcxd2FHOXVlUzV2YzJOaFkyaGxMbUpoYzJVdVpYWmxiblJ6TGtOaFkyaGxSWFpsYm5STWFYTjBaVzVsY25RQUYycGhkbUV1ZFhScGJDNUZkbVZ1ZEV4cGMzUmxibVZ5ZEFBc1kyOXRMbTl3Wlc1emVXMXdhRzl1ZVM1dmMyTmhZMmhsTG1KaGMyVXVUR2xtWldONVkyeGxRWGRoY21WNHQADTEyOS4xODYuNzkuNDVzcgAyb3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5KYXZhU2VydmljZURlZmluaXRpb2625w1fQTRs8QIAAUwAEXNlcnZpY2VJbnRlcmZhY2VzdAAQTGphdmEvdXRpbC9MaXN0O3hyAC5vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLlNlcnZpY2VEZWZpbml0aW9uAJsCT1jdKX4CAA1MAAtidXNTZWN1cml0eXEAfgATTAAPY3JlZGVudGlhbHNUeXBldABMTG9yZy9rdWFsaS9yaWNlL2NvcmUvc2VjdXJpdHkvY3JlZGVudGlhbHMvQ3JlZGVudGlhbHNTb3VyY2UkQ3JlZGVudGlhbHNUeXBlO0wAEGxvY2FsU2VydmljZU5hbWVxAH4ABEwAF21lc3NhZ2VFeGNlcHRpb25IYW5kbGVycQB+AARMAAxtaWxsaXNUb0xpdmVxAH4AFUwACHByaW9yaXR5cQB+ABRMAAVxdWV1ZXEAfgATTAANcmV0cnlBdHRlbXB0c3EAfgAUTAAHc2VydmljZXQAEkxqYXZhL2xhbmcvT2JqZWN0O0wAD3NlcnZpY2VFbmRQb2ludHQADkxqYXZhL25ldC9VUkw7TAATc2VydmljZU5hbWVTcGFjZVVSSXEAfgAETAAQc2VydmljZU5hbWVzcGFjZXEAfgAETAALc2VydmljZVBhdGhxAH4ABHhwc3EAfgAZAXB0ABpPU0NhY2hlTm90aWZpY2F0aW9uU2VydmljZXQATW9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuZXhjZXB0aW9uaGFuZGxpbmcuRGVmYXVsdE1lc3NhZ2VFeGNlcHRpb25IYW5kbGVyc3EAfgAf//////////9zcQB+ABwAAAADc3EAfgAZAHEAfgAycHNyAAxqYXZhLm5ldC5VUkyWJTc2GvzkcgMAB0kACGhhc2hDb2RlSQAEcG9ydEwACWF1dGhvcml0eXEAfgAETAAEZmlsZXEAfgAETAAEaG9zdHEAfgAETAAIcHJvdG9jb2xxAH4ABEwAA3JlZnEAfgAEeHBpG6KgAAAfkHQADmxvY2FsaG9zdDo4MDgwdAArL2tyLWRldi9yZW1vdGluZy9PU0NhY2hlTm90aWZpY2F0aW9uU2VydmljZXQACWxvY2FsaG9zdHQABGh0dHBweHQAAHQABlRSQVZFTHQAAS9zcgATamF2YS51dGlsLkFycmF5TGlzdHiB0h2Zx2GdAwABSQAEc2l6ZXhwAAAABXcEAAAACnQAM29yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuc2VydmljZS5LU0JKYXZhU2VydmljZXQAPGNvbS5vcGVuc3ltcGhvbnkub3NjYWNoZS5iYXNlLmV2ZW50cy5DYWNoZUVudHJ5RXZlbnRMaXN0ZW5lcnQAN2NvbS5vcGVuc3ltcGhvbnkub3NjYWNoZS5iYXNlLmV2ZW50cy5DYWNoZUV2ZW50TGlzdGVuZXJ0ABdqYXZhLnV0aWwuRXZlbnRMaXN0ZW5lcnQALGNvbS5vcGVuc3ltcGhvbnkub3NjYWNoZS5iYXNlLkxpZmVjeWNsZUF3YXJleHEAfgAjdAAGVFJBVkVM';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
INSERT INTO KRSB_MSG_PYLD_T (MSG_PYLD,MSG_QUE_ID)
  VALUES (EMPTY_CLOB(),417)
/
-- Length: 6112
-- Chunks: 2
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 417    FOR UPDATE;        
    buffer := 'rO0ABXNyAC1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLkFzeW5jaHJvbm91c0NhbGzxnQ5Y0t0lZQIAB1oAFWlnbm9yZVN0b3JlQW5kRm9yd2FyZFsACWFyZ3VtZW50c3QAE1tMamF2YS9sYW5nL09iamVjdDtMAAhjYWxsYmFja3QAM0xvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL0FzeW5jaHJvbm91c0NhbGxiYWNrO0wAB2NvbnRleHR0ABZMamF2YS9pby9TZXJpYWxpemFibGU7TAAKbWV0aG9kTmFtZXQAEkxqYXZhL2xhbmcvU3RyaW5nO1sACnBhcmFtVHlwZXN0ABJbTGphdmEvbGFuZy9DbGFzcztMAAtzZXJ2aWNlSW5mb3QAKkxvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL1NlcnZpY2VJbmZvO3hwAHVyABNbTGphdmEubGFuZy5PYmplY3Q7kM5YnxBzKWwCAAB4cAAAAAFzcgBDY29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLnBsdWdpbnMuY2x1c3RlcnN1cHBvcnQuQ2x1c3Rlck5vdGlmaWNhdGlvbhY+6VckvG9pAgACSQAEdHlwZUwABGRhdGFxAH4AA3hwAAAAAnQANURvY3VtZW50VHlwZVJ1bGVDYWNoZTpSZWNpcGVQYXJlbnRNYWludGVuYW5jZURvY3VtZW50cHB0AAZpbnZva2V1cgASW0xqYXZhLmxhbmcuQ2xhc3M7qxbXrsvNWpkCAAB4cAAAAAF2cgAUamF2YS5pby5TZXJpYWxpemFibGUQm2EN15tk7QIAAHhwc3IAKG9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuU2VydmljZUluZm/FFyO2KQS3ugIAC0wABWFsaXZldAATTGphdmEvbGFuZy9Cb29sZWFuO0wAFGVuZHBvaW50QWx0ZXJuYXRlVXJscQB+AARMAAtlbmRwb2ludFVybHEAfgAETAAKbG9ja1Zlck5icnQAE0xqYXZhL2xhbmcvSW50ZWdlcjtMAA5tZXNzYWdlRW50cnlJZHQAEExqYXZhL2xhbmcvTG9uZztMAAVxbmFtZXQAG0xqYXZheC94bWwvbmFtZXNwYWNlL1FOYW1lO0wAGnNlcmlhbGl6ZWRTZXJ2aWNlTmFtZXNwYWNlcQB+AARMAAhzZXJ2ZXJJcHEAfgAETAARc2VydmljZURlZmluaXRpb250ADBMb3JnL2t1YWxpL3JpY2Uva3NiL21lc3NhZ2luZy9TZXJ2aWNlRGVmaW5pdGlvbjtMAAtzZXJ2aWNlTmFtZXEAfgAETAAQc2VydmljZU5hbWVzcGFjZXEAfgAEeHBzcgARamF2YS5sYW5nLkJvb2xlYW7NIHKA1Zz67gIAAVoABXZhbHVleHABcHQAQGh0dHA6Ly9sb2NhbGhvc3Q6ODA4MC9rci1kZXYvcmVtb3RpbmcvT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2VzcgARamF2YS5sYW5nLkludGVnZXIS4qCk94GHOAIAAUkABXZhbHVleHIAEGphdmEubGFuZy5OdW1iZXKGrJUdC5TgiwIAAHhwAAAAAXNyAA5qYXZhLmxhbmcuTG9uZzuL5JDMjyPfAgABSgAFdmFsdWV4cQB+AB0AAAAAAAAPfXNyABlqYXZheC54bWwubmFtZXNwYWNlLlFOYW1lgW2oLfw73WwCAANMAAlsb2NhbFBhcnRxAH4ABEwADG5hbWVzcGFjZVVSSXEAfgAETAAGcHJlZml4cQB+AAR4cHQAGk9TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNldAAAcQB+ACR0B3RyTzBBQlhOeUFESnZjbWN1YTNWaGJHa3VjbWxqWlM1cmMySXViV1Z6YzJGbmFXNW5Ma3BoZG1GVFpYSjJhV05sUkdWbWFXNXBkR2x2YnJibkRWOUJOR3p4QWdBQlRBQVJjMlZ5ZG1salpVbHVkR1Z5Wm1GalpYTjBBQkJNYW1GMllTOTFkR2xzTDB4cGMzUTdlSElBTG05eVp5NXJkV0ZzYVM1eWFXTmxMbXR6WWk1dFpYTnpZV2RwYm1jdVUyVnlkbWxqWlVSbFptbHVhWFJwYjI0QW13SlBXTjBwZmdJQURVd0FDMkoxYzFObFkzVnlhWFI1ZEFBVFRHcGhkbUV2YkdGdVp5OUNiMjlzWldGdU8wd0FEMk55WldSbGJuUnBZV3h6Vkhsd1pYUUFURXh2Y21jdmEzVmhiR2t2Y21salpTOWpiM0psTDNObFkzVnlhWFI1TDJOeVpXUmxiblJwWVd4ekwwTnlaV1JsYm5ScFlXeHpVMjkxY21ObEpFTnlaV1JsYm5ScFlXeHpWSGx3WlR0TUFCQnNiMk5oYkZObGNuWnBZMlZPWVcxbGRBQVNUR3BoZG1FdmJHRnVaeTlUZEhKcGJtYzdUQUFYYldWemMyRm5aVVY0WTJWd2RHbHZia2hoYm1Sc1pYSnhBSDRBQlV3QURHMXBiR3hwYzFSdlRHbDJaWFFBRUV4cVlYWmhMMnhoYm1jdlRHOXVaenRNQUFod2NtbHZjbWwwZVhRQUUweHFZWFpoTDJ4aGJtY3ZTVzUwWldkbGNqdE1BQVZ4ZFdWMVpYRUFmZ0FEVEFBTmNtVjBjbmxCZEhSbGJYQjBjM0VBZmdBSFRBQUhjMlZ5ZG1salpYUUFFa3hxWVhaaEwyeGhibWN2VDJKcVpXTjBPMHdBRDNObGNuWnBZMlZGYm1SUWIybHVkSFFBRGt4cVlYWmhMMjVsZEM5VlVrdzdUQUFUYzJWeWRtbGpaVTVoYldWVGNHRmpaVlZTU1hFQWZnQUZUQUFRYzJWeWRtbGpaVTVoYldWemNHRmpaWEVBZmdBRlRBQUxjMlZ5ZG1salpWQmhkR2h4QUg0QUJYaHdjM0lBRVdwaGRtRXViR0Z1Wnk1Q2IyOXNaV0Z1elNCeWdOV2MrdTRDQUFGYUFBVjJZV3gxWlhod0FYQjBBQnBQVTBOaFkyaGxUbTkwYVdacFkyRjBhVzl1VTJWeWRtbGpaWFFBVFc5eVp5NXJkV0ZzYVM1eWFXTmxMbXR6WWk1dFpYTnpZV2RwYm1jdVpYaGpaWEIwYVc5dWFHRnVaR3hwYm1jdVJHVm1ZWFZzZEUxbGMzTmhaMlZGZUdObGNIUnBiMjVJWVc1a2JHVnljM0lBRG1waGRtRXViR0Z1Wnk1TWIyNW5PNHZra015UEk5OENBQUZLQUFWMllXeDFaWGh5QUJCcVlYWmhMbXhoYm1jdVRuVnRZbVZ5aHF5VkhRdVU0SXNDQUFCNGNQLy8vLy8vLy8vL2MzSUFFV3BoZG1FdWJHRnVaeTVKYm5SbFoyVnlFdUtncFBlQmh6Z0NBQUZKQUFWMllXeDFaWGh4QUg0QUVBQUFBQU56Y1FCK0FBc0FjUUIrQUJOd2MzSUFER3BoZG1FdWJtVjBMbFZTVEpZbE56WWEvT1J5QXdBSFNRQUlhR0Z6YUVOdlpHVkpBQVJ3YjNKMFRBQUpZWFYwYUc5eWFYUjVjUUIrQUFWTUFBUm1hV3hsY1FCK0FBVk1BQVJvYjNOMGNRQitBQVZNQUFod2NtOTBiMk52YkhFQWZnQUZUQUFEY21WbWNRQitBQVY0Y1AvLy8vOEFBQitRZEFBT2JHOWpZV3hvYjNOME9qZ3dPREIwQUNzdmEzSXRaR1YyTDNKbGJXOTBhVzVuTDA5VFEyRmphR1ZPYjNScFptbGpZWFJwYjI1VFpYSjJhV05sZEFBSmJHOWpZV3hvYjNOMGRBQUVhSFIwY0hCNGRBQUFkQUFHVkZKQlZrVk1kQUFCTDNOeUFCTnFZWFpoTG5WMGFXd3VRWEp5WVhsTWFYTjBlSUhTSFpuSFlaMERBQUZKQUFSemFYcGxlSEFBQUFBRmR3UUFBQUFLZEFB';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 417    FOR UPDATE;        
    buffer := 'emIzSm5MbXQxWVd4cExuSnBZMlV1YTNOaUxtMWxjM05oWjJsdVp5NXpaWEoyYVdObExrdFRRa3BoZG1GVFpYSjJhV05sZEFBOFkyOXRMbTl3Wlc1emVXMXdhRzl1ZVM1dmMyTmhZMmhsTG1KaGMyVXVaWFpsYm5SekxrTmhZMmhsUlc1MGNubEZkbVZ1ZEV4cGMzUmxibVZ5ZEFBM1kyOXRMbTl3Wlc1emVXMXdhRzl1ZVM1dmMyTmhZMmhsTG1KaGMyVXVaWFpsYm5SekxrTmhZMmhsUlhabGJuUk1hWE4wWlc1bGNuUUFGMnBoZG1FdWRYUnBiQzVGZG1WdWRFeHBjM1JsYm1WeWRBQXNZMjl0TG05d1pXNXplVzF3YUc5dWVTNXZjMk5oWTJobExtSmhjMlV1VEdsbVpXTjVZMnhsUVhkaGNtVjR0AA0xMjkuMTg2Ljc5LjQ1c3IAMm9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuSmF2YVNlcnZpY2VEZWZpbml0aW9utucNX0E0bPECAAFMABFzZXJ2aWNlSW50ZXJmYWNlc3QAEExqYXZhL3V0aWwvTGlzdDt4cgAub3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5TZXJ2aWNlRGVmaW5pdGlvbgCbAk9Y3Sl+AgANTAALYnVzU2VjdXJpdHlxAH4AE0wAD2NyZWRlbnRpYWxzVHlwZXQATExvcmcva3VhbGkvcmljZS9jb3JlL3NlY3VyaXR5L2NyZWRlbnRpYWxzL0NyZWRlbnRpYWxzU291cmNlJENyZWRlbnRpYWxzVHlwZTtMABBsb2NhbFNlcnZpY2VOYW1lcQB+AARMABdtZXNzYWdlRXhjZXB0aW9uSGFuZGxlcnEAfgAETAAMbWlsbGlzVG9MaXZlcQB+ABVMAAhwcmlvcml0eXEAfgAUTAAFcXVldWVxAH4AE0wADXJldHJ5QXR0ZW1wdHNxAH4AFEwAB3NlcnZpY2V0ABJMamF2YS9sYW5nL09iamVjdDtMAA9zZXJ2aWNlRW5kUG9pbnR0AA5MamF2YS9uZXQvVVJMO0wAE3NlcnZpY2VOYW1lU3BhY2VVUklxAH4ABEwAEHNlcnZpY2VOYW1lc3BhY2VxAH4ABEwAC3NlcnZpY2VQYXRocQB+AAR4cHNxAH4AGQFwdAAaT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2V0AE1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLmV4Y2VwdGlvbmhhbmRsaW5nLkRlZmF1bHRNZXNzYWdlRXhjZXB0aW9uSGFuZGxlcnNxAH4AH///////////c3EAfgAcAAAAA3NxAH4AGQBxAH4AMnBzcgAMamF2YS5uZXQuVVJMliU3Nhr85HIDAAdJAAhoYXNoQ29kZUkABHBvcnRMAAlhdXRob3JpdHlxAH4ABEwABGZpbGVxAH4ABEwABGhvc3RxAH4ABEwACHByb3RvY29scQB+AARMAANyZWZxAH4ABHhwaRuioAAAH5B0AA5sb2NhbGhvc3Q6ODA4MHQAKy9rci1kZXYvcmVtb3RpbmcvT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2V0AAlsb2NhbGhvc3R0AARodHRwcHh0AAB0AAZUUkFWRUx0AAEvc3IAE2phdmEudXRpbC5BcnJheUxpc3R4gdIdmcdhnQMAAUkABHNpemV4cAAAAAV3BAAAAAp0ADNvcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLnNlcnZpY2UuS1NCSmF2YVNlcnZpY2V0ADxjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5ldmVudHMuQ2FjaGVFbnRyeUV2ZW50TGlzdGVuZXJ0ADdjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5ldmVudHMuQ2FjaGVFdmVudExpc3RlbmVydAAXamF2YS51dGlsLkV2ZW50TGlzdGVuZXJ0ACxjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5MaWZlY3ljbGVBd2FyZXhxAH4AI3QABlRSQVZFTA==';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
INSERT INTO KRSB_MSG_PYLD_T (MSG_PYLD,MSG_QUE_ID)
  VALUES (EMPTY_CLOB(),418)
/
-- Length: 6112
-- Chunks: 2
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 418    FOR UPDATE;        
    buffer := 'rO0ABXNyAC1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLkFzeW5jaHJvbm91c0NhbGzxnQ5Y0t0lZQIAB1oAFWlnbm9yZVN0b3JlQW5kRm9yd2FyZFsACWFyZ3VtZW50c3QAE1tMamF2YS9sYW5nL09iamVjdDtMAAhjYWxsYmFja3QAM0xvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL0FzeW5jaHJvbm91c0NhbGxiYWNrO0wAB2NvbnRleHR0ABZMamF2YS9pby9TZXJpYWxpemFibGU7TAAKbWV0aG9kTmFtZXQAEkxqYXZhL2xhbmcvU3RyaW5nO1sACnBhcmFtVHlwZXN0ABJbTGphdmEvbGFuZy9DbGFzcztMAAtzZXJ2aWNlSW5mb3QAKkxvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL1NlcnZpY2VJbmZvO3hwAHVyABNbTGphdmEubGFuZy5PYmplY3Q7kM5YnxBzKWwCAAB4cAAAAAFzcgBDY29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLnBsdWdpbnMuY2x1c3RlcnN1cHBvcnQuQ2x1c3Rlck5vdGlmaWNhdGlvbhY+6VckvG9pAgACSQAEdHlwZUwABGRhdGFxAH4AA3hwAAAAAnQAN0RvY3VtZW50VHlwZVJ1bGVDYWNoZTpSZWNpcGVDYXRlZ29yeU1haW50ZW5hbmNlRG9jdW1lbnRwcHQABmludm9rZXVyABJbTGphdmEubGFuZy5DbGFzczurFteuy81amQIAAHhwAAAAAXZyABRqYXZhLmlvLlNlcmlhbGl6YWJsZRCbYQ3Xm2TtAgAAeHBzcgAob3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5TZXJ2aWNlSW5mb8UXI7YpBLe6AgALTAAFYWxpdmV0ABNMamF2YS9sYW5nL0Jvb2xlYW47TAAUZW5kcG9pbnRBbHRlcm5hdGVVcmxxAH4ABEwAC2VuZHBvaW50VXJscQB+AARMAApsb2NrVmVyTmJydAATTGphdmEvbGFuZy9JbnRlZ2VyO0wADm1lc3NhZ2VFbnRyeUlkdAAQTGphdmEvbGFuZy9Mb25nO0wABXFuYW1ldAAbTGphdmF4L3htbC9uYW1lc3BhY2UvUU5hbWU7TAAac2VyaWFsaXplZFNlcnZpY2VOYW1lc3BhY2VxAH4ABEwACHNlcnZlcklwcQB+AARMABFzZXJ2aWNlRGVmaW5pdGlvbnQAMExvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL1NlcnZpY2VEZWZpbml0aW9uO0wAC3NlcnZpY2VOYW1lcQB+AARMABBzZXJ2aWNlTmFtZXNwYWNlcQB+AAR4cHNyABFqYXZhLmxhbmcuQm9vbGVhbs0gcoDVnPruAgABWgAFdmFsdWV4cAFwdABAaHR0cDovL2xvY2FsaG9zdDo4MDgwL2tyLWRldi9yZW1vdGluZy9PU0NhY2hlTm90aWZpY2F0aW9uU2VydmljZXNyABFqYXZhLmxhbmcuSW50ZWdlchLioKT3gYc4AgABSQAFdmFsdWV4cgAQamF2YS5sYW5nLk51bWJlcoaslR0LlOCLAgAAeHAAAAABc3IADmphdmEubGFuZy5Mb25nO4vkkMyPI98CAAFKAAV2YWx1ZXhxAH4AHQAAAAAAAA99c3IAGWphdmF4LnhtbC5uYW1lc3BhY2UuUU5hbWWBbagt/DvdbAIAA0wACWxvY2FsUGFydHEAfgAETAAMbmFtZXNwYWNlVVJJcQB+AARMAAZwcmVmaXhxAH4ABHhwdAAaT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2V0AABxAH4AJHQHdHJPMEFCWE55QURKdmNtY3VhM1ZoYkdrdWNtbGpaUzVyYzJJdWJXVnpjMkZuYVc1bkxrcGhkbUZUWlhKMmFXTmxSR1ZtYVc1cGRHbHZicmJuRFY5Qk5HenhBZ0FCVEFBUmMyVnlkbWxqWlVsdWRHVnlabUZqWlhOMEFCQk1hbUYyWVM5MWRHbHNMMHhwYzNRN2VISUFMbTl5Wnk1cmRXRnNhUzV5YVdObExtdHpZaTV0WlhOellXZHBibWN1VTJWeWRtbGpaVVJsWm1sdWFYUnBiMjRBbXdKUFdOMHBmZ0lBRFV3QUMySjFjMU5sWTNWeWFYUjVkQUFUVEdwaGRtRXZiR0Z1Wnk5Q2IyOXNaV0Z1TzB3QUQyTnlaV1JsYm5ScFlXeHpWSGx3WlhRQVRFeHZjbWN2YTNWaGJHa3ZjbWxqWlM5amIzSmxMM05sWTNWeWFYUjVMMk55WldSbGJuUnBZV3h6TDBOeVpXUmxiblJwWVd4elUyOTFjbU5sSkVOeVpXUmxiblJwWVd4elZIbHdaVHRNQUJCc2IyTmhiRk5sY25acFkyVk9ZVzFsZEFBU1RHcGhkbUV2YkdGdVp5OVRkSEpwYm1jN1RBQVhiV1Z6YzJGblpVVjRZMlZ3ZEdsdmJraGhibVJzWlhKeEFINEFCVXdBREcxcGJHeHBjMVJ2VEdsMlpYUUFFRXhxWVhaaEwyeGhibWN2VEc5dVp6dE1BQWh3Y21sdmNtbDBlWFFBRTB4cVlYWmhMMnhoYm1jdlNXNTBaV2RsY2p0TUFBVnhkV1YxWlhFQWZnQURUQUFOY21WMGNubEJkSFJsYlhCMGMzRUFmZ0FIVEFBSGMyVnlkbWxqWlhRQUVreHFZWFpoTDJ4aGJtY3ZUMkpxWldOME8wd0FEM05sY25acFkyVkZibVJRYjJsdWRIUUFEa3hxWVhaaEwyNWxkQzlWVWt3N1RBQVRjMlZ5ZG1salpVNWhiV1ZUY0dGalpWVlNTWEVBZmdBRlRBQVFjMlZ5ZG1salpVNWhiV1Z6Y0dGalpYRUFmZ0FGVEFBTGMyVnlkbWxqWlZCaGRHaHhBSDRBQlhod2MzSUFFV3BoZG1FdWJHRnVaeTVDYjI5c1pXRnV6U0J5Z05XYyt1NENBQUZhQUFWMllXeDFaWGh3QVhCMEFCcFBVME5oWTJobFRtOTBhV1pwWTJGMGFXOXVVMlZ5ZG1salpYUUFUVzl5Wnk1cmRXRnNhUzV5YVdObExtdHpZaTV0WlhOellXZHBibWN1WlhoalpYQjBhVzl1YUdGdVpHeHBibWN1UkdWbVlYVnNkRTFsYzNOaFoyVkZlR05sY0hScGIyNUlZVzVrYkdWeWMzSUFEbXBoZG1FdWJHRnVaeTVNYjI1bk80dmtrTXlQSTk4Q0FBRktBQVYyWVd4MVpYaHlBQkJxWVhaaExteGhibWN1VG5WdFltVnlocXlWSFF1VTRJc0NBQUI0Y1AvLy8vLy8vLy8vYzNJQUVXcGhkbUV1YkdGdVp5NUpiblJsWjJWeUV1S2dwUGVCaHpnQ0FBRkpBQVYyWVd4MVpYaHhBSDRBRUFBQUFBTnpjUUIrQUFzQWNRQitBQk53YzNJQURHcGhkbUV1Ym1WMExsVlNUSllsTnpZYS9PUnlBd0FIU1FBSWFHRnphRU52WkdWSkFBUndiM0owVEFBSllYVjBhRzl5YVhSNWNRQitBQVZNQUFSbWFXeGxjUUIrQUFWTUFBUm9iM04wY1FCK0FBVk1BQWh3Y205MGIyTnZiSEVBZmdBRlRBQURjbVZtY1FCK0FBVjRjUC8vLy84QUFCK1FkQUFPYkc5allXeG9iM04wT2pnd09EQjBBQ3N2YTNJdFpHVjJMM0psYlc5MGFXNW5MMDlUUTJGamFHVk9iM1JwWm1sallYUnBiMjVUWlhKMmFXTmxkQUFKYkc5allXeG9iM04wZEFBRWFIUjBjSEI0ZEFBQWRBQUdWRkpCVmtWTWRBQUJMM055QUJOcVlYWmhMblYwYVd3dVFYSnlZWGxNYVhOMGVJSFNIWm5IWVowREFBRkpBQVJ6YVhwbGVIQUFBQUFGZHdRQUFBQUtk';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 418    FOR UPDATE;        
    buffer := 'QUF6YjNKbkxtdDFZV3hwTG5KcFkyVXVhM05pTG0xbGMzTmhaMmx1Wnk1elpYSjJhV05sTGt0VFFrcGhkbUZUWlhKMmFXTmxkQUE4WTI5dExtOXdaVzV6ZVcxd2FHOXVlUzV2YzJOaFkyaGxMbUpoYzJVdVpYWmxiblJ6TGtOaFkyaGxSVzUwY25sRmRtVnVkRXhwYzNSbGJtVnlkQUEzWTI5dExtOXdaVzV6ZVcxd2FHOXVlUzV2YzJOaFkyaGxMbUpoYzJVdVpYWmxiblJ6TGtOaFkyaGxSWFpsYm5STWFYTjBaVzVsY25RQUYycGhkbUV1ZFhScGJDNUZkbVZ1ZEV4cGMzUmxibVZ5ZEFBc1kyOXRMbTl3Wlc1emVXMXdhRzl1ZVM1dmMyTmhZMmhsTG1KaGMyVXVUR2xtWldONVkyeGxRWGRoY21WNHQADTEyOS4xODYuNzkuNDVzcgAyb3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5KYXZhU2VydmljZURlZmluaXRpb2625w1fQTRs8QIAAUwAEXNlcnZpY2VJbnRlcmZhY2VzdAAQTGphdmEvdXRpbC9MaXN0O3hyAC5vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLlNlcnZpY2VEZWZpbml0aW9uAJsCT1jdKX4CAA1MAAtidXNTZWN1cml0eXEAfgATTAAPY3JlZGVudGlhbHNUeXBldABMTG9yZy9rdWFsaS9yaWNlL2NvcmUvc2VjdXJpdHkvY3JlZGVudGlhbHMvQ3JlZGVudGlhbHNTb3VyY2UkQ3JlZGVudGlhbHNUeXBlO0wAEGxvY2FsU2VydmljZU5hbWVxAH4ABEwAF21lc3NhZ2VFeGNlcHRpb25IYW5kbGVycQB+AARMAAxtaWxsaXNUb0xpdmVxAH4AFUwACHByaW9yaXR5cQB+ABRMAAVxdWV1ZXEAfgATTAANcmV0cnlBdHRlbXB0c3EAfgAUTAAHc2VydmljZXQAEkxqYXZhL2xhbmcvT2JqZWN0O0wAD3NlcnZpY2VFbmRQb2ludHQADkxqYXZhL25ldC9VUkw7TAATc2VydmljZU5hbWVTcGFjZVVSSXEAfgAETAAQc2VydmljZU5hbWVzcGFjZXEAfgAETAALc2VydmljZVBhdGhxAH4ABHhwc3EAfgAZAXB0ABpPU0NhY2hlTm90aWZpY2F0aW9uU2VydmljZXQATW9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuZXhjZXB0aW9uaGFuZGxpbmcuRGVmYXVsdE1lc3NhZ2VFeGNlcHRpb25IYW5kbGVyc3EAfgAf//////////9zcQB+ABwAAAADc3EAfgAZAHEAfgAycHNyAAxqYXZhLm5ldC5VUkyWJTc2GvzkcgMAB0kACGhhc2hDb2RlSQAEcG9ydEwACWF1dGhvcml0eXEAfgAETAAEZmlsZXEAfgAETAAEaG9zdHEAfgAETAAIcHJvdG9jb2xxAH4ABEwAA3JlZnEAfgAEeHBpG6KgAAAfkHQADmxvY2FsaG9zdDo4MDgwdAArL2tyLWRldi9yZW1vdGluZy9PU0NhY2hlTm90aWZpY2F0aW9uU2VydmljZXQACWxvY2FsaG9zdHQABGh0dHBweHQAAHQABlRSQVZFTHQAAS9zcgATamF2YS51dGlsLkFycmF5TGlzdHiB0h2Zx2GdAwABSQAEc2l6ZXhwAAAABXcEAAAACnQAM29yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuc2VydmljZS5LU0JKYXZhU2VydmljZXQAPGNvbS5vcGVuc3ltcGhvbnkub3NjYWNoZS5iYXNlLmV2ZW50cy5DYWNoZUVudHJ5RXZlbnRMaXN0ZW5lcnQAN2NvbS5vcGVuc3ltcGhvbnkub3NjYWNoZS5iYXNlLmV2ZW50cy5DYWNoZUV2ZW50TGlzdGVuZXJ0ABdqYXZhLnV0aWwuRXZlbnRMaXN0ZW5lcnQALGNvbS5vcGVuc3ltcGhvbnkub3NjYWNoZS5iYXNlLkxpZmVjeWNsZUF3YXJleHEAfgAjdAAGVFJBVkVM';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
INSERT INTO KRSB_MSG_PYLD_T (MSG_PYLD,MSG_QUE_ID)
  VALUES (EMPTY_CLOB(),419)
/
-- Length: 6116
-- Chunks: 2
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 419    FOR UPDATE;        
    buffer := 'rO0ABXNyAC1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLkFzeW5jaHJvbm91c0NhbGzxnQ5Y0t0lZQIAB1oAFWlnbm9yZVN0b3JlQW5kRm9yd2FyZFsACWFyZ3VtZW50c3QAE1tMamF2YS9sYW5nL09iamVjdDtMAAhjYWxsYmFja3QAM0xvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL0FzeW5jaHJvbm91c0NhbGxiYWNrO0wAB2NvbnRleHR0ABZMamF2YS9pby9TZXJpYWxpemFibGU7TAAKbWV0aG9kTmFtZXQAEkxqYXZhL2xhbmcvU3RyaW5nO1sACnBhcmFtVHlwZXN0ABJbTGphdmEvbGFuZy9DbGFzcztMAAtzZXJ2aWNlSW5mb3QAKkxvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL1NlcnZpY2VJbmZvO3hwAHVyABNbTGphdmEubGFuZy5PYmplY3Q7kM5YnxBzKWwCAAB4cAAAAAFzcgBDY29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLnBsdWdpbnMuY2x1c3RlcnN1cHBvcnQuQ2x1c3Rlck5vdGlmaWNhdGlvbhY+6VckvG9pAgACSQAEdHlwZUwABGRhdGFxAH4AA3hwAAAAAnQAOURvY3VtZW50VHlwZVJ1bGVDYWNoZTpSZWNpcGVJbmdyZWRpZW50TWFpbnRlbmFuY2VEb2N1bWVudHBwdAAGaW52b2tldXIAEltMamF2YS5sYW5nLkNsYXNzO6sW167LzVqZAgAAeHAAAAABdnIAFGphdmEuaW8uU2VyaWFsaXphYmxlEJthDdebZO0CAAB4cHNyAChvcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLlNlcnZpY2VJbmZvxRcjtikEt7oCAAtMAAVhbGl2ZXQAE0xqYXZhL2xhbmcvQm9vbGVhbjtMABRlbmRwb2ludEFsdGVybmF0ZVVybHEAfgAETAALZW5kcG9pbnRVcmxxAH4ABEwACmxvY2tWZXJOYnJ0ABNMamF2YS9sYW5nL0ludGVnZXI7TAAObWVzc2FnZUVudHJ5SWR0ABBMamF2YS9sYW5nL0xvbmc7TAAFcW5hbWV0ABtMamF2YXgveG1sL25hbWVzcGFjZS9RTmFtZTtMABpzZXJpYWxpemVkU2VydmljZU5hbWVzcGFjZXEAfgAETAAIc2VydmVySXBxAH4ABEwAEXNlcnZpY2VEZWZpbml0aW9udAAwTG9yZy9rdWFsaS9yaWNlL2tzYi9tZXNzYWdpbmcvU2VydmljZURlZmluaXRpb247TAALc2VydmljZU5hbWVxAH4ABEwAEHNlcnZpY2VOYW1lc3BhY2VxAH4ABHhwc3IAEWphdmEubGFuZy5Cb29sZWFuzSBygNWc+u4CAAFaAAV2YWx1ZXhwAXB0AEBodHRwOi8vbG9jYWxob3N0OjgwODAva3ItZGV2L3JlbW90aW5nL09TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNlc3IAEWphdmEubGFuZy5JbnRlZ2VyEuKgpPeBhzgCAAFJAAV2YWx1ZXhyABBqYXZhLmxhbmcuTnVtYmVyhqyVHQuU4IsCAAB4cAAAAAFzcgAOamF2YS5sYW5nLkxvbmc7i+SQzI8j3wIAAUoABXZhbHVleHEAfgAdAAAAAAAAD31zcgAZamF2YXgueG1sLm5hbWVzcGFjZS5RTmFtZYFtqC38O91sAgADTAAJbG9jYWxQYXJ0cQB+AARMAAxuYW1lc3BhY2VVUklxAH4ABEwABnByZWZpeHEAfgAEeHB0ABpPU0NhY2hlTm90aWZpY2F0aW9uU2VydmljZXQAAHEAfgAkdAd0ck8wQUJYTnlBREp2Y21jdWEzVmhiR2t1Y21salpTNXJjMkl1YldWemMyRm5hVzVuTGtwaGRtRlRaWEoyYVdObFJHVm1hVzVwZEdsdmJyYm5EVjlCTkd6eEFnQUJUQUFSYzJWeWRtbGpaVWx1ZEdWeVptRmpaWE4wQUJCTWFtRjJZUzkxZEdsc0wweHBjM1E3ZUhJQUxtOXlaeTVyZFdGc2FTNXlhV05sTG10ellpNXRaWE56WVdkcGJtY3VVMlZ5ZG1salpVUmxabWx1YVhScGIyNEFtd0pQV04wcGZnSUFEVXdBQzJKMWMxTmxZM1Z5YVhSNWRBQVRUR3BoZG1FdmJHRnVaeTlDYjI5c1pXRnVPMHdBRDJOeVpXUmxiblJwWVd4elZIbHdaWFFBVEV4dmNtY3ZhM1ZoYkdrdmNtbGpaUzlqYjNKbEwzTmxZM1Z5YVhSNUwyTnlaV1JsYm5ScFlXeHpMME55WldSbGJuUnBZV3h6VTI5MWNtTmxKRU55WldSbGJuUnBZV3h6Vkhsd1pUdE1BQkJzYjJOaGJGTmxjblpwWTJWT1lXMWxkQUFTVEdwaGRtRXZiR0Z1Wnk5VGRISnBibWM3VEFBWGJXVnpjMkZuWlVWNFkyVndkR2x2YmtoaGJtUnNaWEp4QUg0QUJVd0FERzFwYkd4cGMxUnZUR2wyWlhRQUVFeHFZWFpoTDJ4aGJtY3ZURzl1Wnp0TUFBaHdjbWx2Y21sMGVYUUFFMHhxWVhaaEwyeGhibWN2U1c1MFpXZGxjanRNQUFWeGRXVjFaWEVBZmdBRFRBQU5jbVYwY25sQmRIUmxiWEIwYzNFQWZnQUhUQUFIYzJWeWRtbGpaWFFBRWt4cVlYWmhMMnhoYm1jdlQySnFaV04wTzB3QUQzTmxjblpwWTJWRmJtUlFiMmx1ZEhRQURreHFZWFpoTDI1bGRDOVZVa3c3VEFBVGMyVnlkbWxqWlU1aGJXVlRjR0ZqWlZWU1NYRUFmZ0FGVEFBUWMyVnlkbWxqWlU1aGJXVnpjR0ZqWlhFQWZnQUZUQUFMYzJWeWRtbGpaVkJoZEdoeEFINEFCWGh3YzNJQUVXcGhkbUV1YkdGdVp5NUNiMjlzWldGdXpTQnlnTldjK3U0Q0FBRmFBQVYyWVd4MVpYaHdBWEIwQUJwUFUwTmhZMmhsVG05MGFXWnBZMkYwYVc5dVUyVnlkbWxqWlhRQVRXOXlaeTVyZFdGc2FTNXlhV05sTG10ellpNXRaWE56WVdkcGJtY3VaWGhqWlhCMGFXOXVhR0Z1Wkd4cGJtY3VSR1ZtWVhWc2RFMWxjM05oWjJWRmVHTmxjSFJwYjI1SVlXNWtiR1Z5YzNJQURtcGhkbUV1YkdGdVp5NU1iMjVuTzR2a2tNeVBJOThDQUFGS0FBVjJZV3gxWlhoeUFCQnFZWFpoTG14aGJtY3VUblZ0WW1WeWhxeVZIUXVVNElzQ0FBQjRjUC8vLy8vLy8vLy9jM0lBRVdwaGRtRXViR0Z1Wnk1SmJuUmxaMlZ5RXVLZ3BQZUJoemdDQUFGSkFBVjJZV3gxWlhoeEFINEFFQUFBQUFOemNRQitBQXNBY1FCK0FCTndjM0lBREdwaGRtRXVibVYwTGxWU1RKWWxOellhL09SeUF3QUhTUUFJYUdGemFFTnZaR1ZKQUFSd2IzSjBUQUFKWVhWMGFHOXlhWFI1Y1FCK0FBVk1BQVJtYVd4bGNRQitBQVZNQUFSb2IzTjBjUUIrQUFWTUFBaHdjbTkwYjJOdmJIRUFmZ0FGVEFBRGNtVm1jUUIrQUFWNGNQLy8vLzhBQUIrUWRBQU9iRzlqWVd4b2IzTjBPamd3T0RCMEFDc3ZhM0l0WkdWMkwzSmxiVzkwYVc1bkwwOVRRMkZqYUdWT2IzUnBabWxqWVhScGIyNVRaWEoyYVdObGRBQUpiRzlqWVd4b2IzTjBkQUFFYUhSMGNIQjRkQUFBZEFBR1ZGSkJWa1ZNZEFBQkwzTnlBQk5xWVhaaExuVjBhV3d1UVhKeVlYbE1hWE4wZUlIU0habkhZWjBEQUFGSkFBUnphWHBsZUhBQUFBQUZkd1FBQUFB';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 419    FOR UPDATE;        
    buffer := 'S2RBQXpiM0puTG10MVlXeHBMbkpwWTJVdWEzTmlMbTFsYzNOaFoybHVaeTV6WlhKMmFXTmxMa3RUUWtwaGRtRlRaWEoyYVdObGRBQThZMjl0TG05d1pXNXplVzF3YUc5dWVTNXZjMk5oWTJobExtSmhjMlV1WlhabGJuUnpMa05oWTJobFJXNTBjbmxGZG1WdWRFeHBjM1JsYm1WeWRBQTNZMjl0TG05d1pXNXplVzF3YUc5dWVTNXZjMk5oWTJobExtSmhjMlV1WlhabGJuUnpMa05oWTJobFJYWmxiblJNYVhOMFpXNWxjblFBRjJwaGRtRXVkWFJwYkM1RmRtVnVkRXhwYzNSbGJtVnlkQUFzWTI5dExtOXdaVzV6ZVcxd2FHOXVlUzV2YzJOaFkyaGxMbUpoYzJVdVRHbG1aV041WTJ4bFFYZGhjbVY0dAANMTI5LjE4Ni43OS40NXNyADJvcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLkphdmFTZXJ2aWNlRGVmaW5pdGlvbrbnDV9BNGzxAgABTAARc2VydmljZUludGVyZmFjZXN0ABBMamF2YS91dGlsL0xpc3Q7eHIALm9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuU2VydmljZURlZmluaXRpb24AmwJPWN0pfgIADUwAC2J1c1NlY3VyaXR5cQB+ABNMAA9jcmVkZW50aWFsc1R5cGV0AExMb3JnL2t1YWxpL3JpY2UvY29yZS9zZWN1cml0eS9jcmVkZW50aWFscy9DcmVkZW50aWFsc1NvdXJjZSRDcmVkZW50aWFsc1R5cGU7TAAQbG9jYWxTZXJ2aWNlTmFtZXEAfgAETAAXbWVzc2FnZUV4Y2VwdGlvbkhhbmRsZXJxAH4ABEwADG1pbGxpc1RvTGl2ZXEAfgAVTAAIcHJpb3JpdHlxAH4AFEwABXF1ZXVlcQB+ABNMAA1yZXRyeUF0dGVtcHRzcQB+ABRMAAdzZXJ2aWNldAASTGphdmEvbGFuZy9PYmplY3Q7TAAPc2VydmljZUVuZFBvaW50dAAOTGphdmEvbmV0L1VSTDtMABNzZXJ2aWNlTmFtZVNwYWNlVVJJcQB+AARMABBzZXJ2aWNlTmFtZXNwYWNlcQB+AARMAAtzZXJ2aWNlUGF0aHEAfgAEeHBzcQB+ABkBcHQAGk9TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNldABNb3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5leGNlcHRpb25oYW5kbGluZy5EZWZhdWx0TWVzc2FnZUV4Y2VwdGlvbkhhbmRsZXJzcQB+AB///////////3NxAH4AHAAAAANzcQB+ABkAcQB+ADJwc3IADGphdmEubmV0LlVSTJYlNzYa/ORyAwAHSQAIaGFzaENvZGVJAARwb3J0TAAJYXV0aG9yaXR5cQB+AARMAARmaWxlcQB+AARMAARob3N0cQB+AARMAAhwcm90b2NvbHEAfgAETAADcmVmcQB+AAR4cGkboqAAAB+QdAAObG9jYWxob3N0OjgwODB0ACsva3ItZGV2L3JlbW90aW5nL09TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNldAAJbG9jYWxob3N0dAAEaHR0cHB4dAAAdAAGVFJBVkVMdAABL3NyABNqYXZhLnV0aWwuQXJyYXlMaXN0eIHSHZnHYZ0DAAFJAARzaXpleHAAAAAFdwQAAAAKdAAzb3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5zZXJ2aWNlLktTQkphdmFTZXJ2aWNldAA8Y29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLmJhc2UuZXZlbnRzLkNhY2hlRW50cnlFdmVudExpc3RlbmVydAA3Y29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLmJhc2UuZXZlbnRzLkNhY2hlRXZlbnRMaXN0ZW5lcnQAF2phdmEudXRpbC5FdmVudExpc3RlbmVydAAsY29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLmJhc2UuTGlmZWN5Y2xlQXdhcmV4cQB+ACN0AAZUUkFWRUw=';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
INSERT INTO KRSB_MSG_PYLD_T (MSG_PYLD,MSG_QUE_ID)
  VALUES (EMPTY_CLOB(),420)
/
-- Length: 6104
-- Chunks: 2
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 420    FOR UPDATE;        
    buffer := 'rO0ABXNyAC1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLkFzeW5jaHJvbm91c0NhbGzxnQ5Y0t0lZQIAB1oAFWlnbm9yZVN0b3JlQW5kRm9yd2FyZFsACWFyZ3VtZW50c3QAE1tMamF2YS9sYW5nL09iamVjdDtMAAhjYWxsYmFja3QAM0xvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL0FzeW5jaHJvbm91c0NhbGxiYWNrO0wAB2NvbnRleHR0ABZMamF2YS9pby9TZXJpYWxpemFibGU7TAAKbWV0aG9kTmFtZXQAEkxqYXZhL2xhbmcvU3RyaW5nO1sACnBhcmFtVHlwZXN0ABJbTGphdmEvbGFuZy9DbGFzcztMAAtzZXJ2aWNlSW5mb3QAKkxvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL1NlcnZpY2VJbmZvO3hwAHVyABNbTGphdmEubGFuZy5PYmplY3Q7kM5YnxBzKWwCAAB4cAAAAAFzcgBDY29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLnBsdWdpbnMuY2x1c3RlcnN1cHBvcnQuQ2x1c3Rlck5vdGlmaWNhdGlvbhY+6VckvG9pAgACSQAEdHlwZUwABGRhdGFxAH4AA3hwAAAAAnQAL0RvY3VtZW50VHlwZVJ1bGVDYWNoZTpSZWNpcGVNYWludGVuYW5jZURvY3VtZW50cHB0AAZpbnZva2V1cgASW0xqYXZhLmxhbmcuQ2xhc3M7qxbXrsvNWpkCAAB4cAAAAAF2cgAUamF2YS5pby5TZXJpYWxpemFibGUQm2EN15tk7QIAAHhwc3IAKG9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuU2VydmljZUluZm/FFyO2KQS3ugIAC0wABWFsaXZldAATTGphdmEvbGFuZy9Cb29sZWFuO0wAFGVuZHBvaW50QWx0ZXJuYXRlVXJscQB+AARMAAtlbmRwb2ludFVybHEAfgAETAAKbG9ja1Zlck5icnQAE0xqYXZhL2xhbmcvSW50ZWdlcjtMAA5tZXNzYWdlRW50cnlJZHQAEExqYXZhL2xhbmcvTG9uZztMAAVxbmFtZXQAG0xqYXZheC94bWwvbmFtZXNwYWNlL1FOYW1lO0wAGnNlcmlhbGl6ZWRTZXJ2aWNlTmFtZXNwYWNlcQB+AARMAAhzZXJ2ZXJJcHEAfgAETAARc2VydmljZURlZmluaXRpb250ADBMb3JnL2t1YWxpL3JpY2Uva3NiL21lc3NhZ2luZy9TZXJ2aWNlRGVmaW5pdGlvbjtMAAtzZXJ2aWNlTmFtZXEAfgAETAAQc2VydmljZU5hbWVzcGFjZXEAfgAEeHBzcgARamF2YS5sYW5nLkJvb2xlYW7NIHKA1Zz67gIAAVoABXZhbHVleHABcHQAQGh0dHA6Ly9sb2NhbGhvc3Q6ODA4MC9rci1kZXYvcmVtb3RpbmcvT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2VzcgARamF2YS5sYW5nLkludGVnZXIS4qCk94GHOAIAAUkABXZhbHVleHIAEGphdmEubGFuZy5OdW1iZXKGrJUdC5TgiwIAAHhwAAAAAXNyAA5qYXZhLmxhbmcuTG9uZzuL5JDMjyPfAgABSgAFdmFsdWV4cQB+AB0AAAAAAAAPfXNyABlqYXZheC54bWwubmFtZXNwYWNlLlFOYW1lgW2oLfw73WwCAANMAAlsb2NhbFBhcnRxAH4ABEwADG5hbWVzcGFjZVVSSXEAfgAETAAGcHJlZml4cQB+AAR4cHQAGk9TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNldAAAcQB+ACR0B3RyTzBBQlhOeUFESnZjbWN1YTNWaGJHa3VjbWxqWlM1cmMySXViV1Z6YzJGbmFXNW5Ma3BoZG1GVFpYSjJhV05sUkdWbWFXNXBkR2x2YnJibkRWOUJOR3p4QWdBQlRBQVJjMlZ5ZG1salpVbHVkR1Z5Wm1GalpYTjBBQkJNYW1GMllTOTFkR2xzTDB4cGMzUTdlSElBTG05eVp5NXJkV0ZzYVM1eWFXTmxMbXR6WWk1dFpYTnpZV2RwYm1jdVUyVnlkbWxqWlVSbFptbHVhWFJwYjI0QW13SlBXTjBwZmdJQURVd0FDMkoxYzFObFkzVnlhWFI1ZEFBVFRHcGhkbUV2YkdGdVp5OUNiMjlzWldGdU8wd0FEMk55WldSbGJuUnBZV3h6Vkhsd1pYUUFURXh2Y21jdmEzVmhiR2t2Y21salpTOWpiM0psTDNObFkzVnlhWFI1TDJOeVpXUmxiblJwWVd4ekwwTnlaV1JsYm5ScFlXeHpVMjkxY21ObEpFTnlaV1JsYm5ScFlXeHpWSGx3WlR0TUFCQnNiMk5oYkZObGNuWnBZMlZPWVcxbGRBQVNUR3BoZG1FdmJHRnVaeTlUZEhKcGJtYzdUQUFYYldWemMyRm5aVVY0WTJWd2RHbHZia2hoYm1Sc1pYSnhBSDRBQlV3QURHMXBiR3hwYzFSdlRHbDJaWFFBRUV4cVlYWmhMMnhoYm1jdlRHOXVaenRNQUFod2NtbHZjbWwwZVhRQUUweHFZWFpoTDJ4aGJtY3ZTVzUwWldkbGNqdE1BQVZ4ZFdWMVpYRUFmZ0FEVEFBTmNtVjBjbmxCZEhSbGJYQjBjM0VBZmdBSFRBQUhjMlZ5ZG1salpYUUFFa3hxWVhaaEwyeGhibWN2VDJKcVpXTjBPMHdBRDNObGNuWnBZMlZGYm1SUWIybHVkSFFBRGt4cVlYWmhMMjVsZEM5VlVrdzdUQUFUYzJWeWRtbGpaVTVoYldWVGNHRmpaVlZTU1hFQWZnQUZUQUFRYzJWeWRtbGpaVTVoYldWemNHRmpaWEVBZmdBRlRBQUxjMlZ5ZG1salpWQmhkR2h4QUg0QUJYaHdjM0lBRVdwaGRtRXViR0Z1Wnk1Q2IyOXNaV0Z1elNCeWdOV2MrdTRDQUFGYUFBVjJZV3gxWlhod0FYQjBBQnBQVTBOaFkyaGxUbTkwYVdacFkyRjBhVzl1VTJWeWRtbGpaWFFBVFc5eVp5NXJkV0ZzYVM1eWFXTmxMbXR6WWk1dFpYTnpZV2RwYm1jdVpYaGpaWEIwYVc5dWFHRnVaR3hwYm1jdVJHVm1ZWFZzZEUxbGMzTmhaMlZGZUdObGNIUnBiMjVJWVc1a2JHVnljM0lBRG1waGRtRXViR0Z1Wnk1TWIyNW5PNHZra015UEk5OENBQUZLQUFWMllXeDFaWGh5QUJCcVlYWmhMbXhoYm1jdVRuVnRZbVZ5aHF5VkhRdVU0SXNDQUFCNGNQLy8vLy8vLy8vL2MzSUFFV3BoZG1FdWJHRnVaeTVKYm5SbFoyVnlFdUtncFBlQmh6Z0NBQUZKQUFWMllXeDFaWGh4QUg0QUVBQUFBQU56Y1FCK0FBc0FjUUIrQUJOd2MzSUFER3BoZG1FdWJtVjBMbFZTVEpZbE56WWEvT1J5QXdBSFNRQUlhR0Z6YUVOdlpHVkpBQVJ3YjNKMFRBQUpZWFYwYUc5eWFYUjVjUUIrQUFWTUFBUm1hV3hsY1FCK0FBVk1BQVJvYjNOMGNRQitBQVZNQUFod2NtOTBiMk52YkhFQWZnQUZUQUFEY21WbWNRQitBQVY0Y1AvLy8vOEFBQitRZEFBT2JHOWpZV3hvYjNOME9qZ3dPREIwQUNzdmEzSXRaR1YyTDNKbGJXOTBhVzVuTDA5VFEyRmphR1ZPYjNScFptbGpZWFJwYjI1VFpYSjJhV05sZEFBSmJHOWpZV3hvYjNOMGRBQUVhSFIwY0hCNGRBQUFkQUFHVkZKQlZrVk1kQUFCTDNOeUFCTnFZWFpoTG5WMGFXd3VRWEp5WVhsTWFYTjBlSUhTSFpuSFlaMERBQUZKQUFSemFYcGxlSEFBQUFBRmR3UUFBQUFLZEFBemIzSm5M';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 420    FOR UPDATE;        
    buffer := 'bXQxWVd4cExuSnBZMlV1YTNOaUxtMWxjM05oWjJsdVp5NXpaWEoyYVdObExrdFRRa3BoZG1GVFpYSjJhV05sZEFBOFkyOXRMbTl3Wlc1emVXMXdhRzl1ZVM1dmMyTmhZMmhsTG1KaGMyVXVaWFpsYm5SekxrTmhZMmhsUlc1MGNubEZkbVZ1ZEV4cGMzUmxibVZ5ZEFBM1kyOXRMbTl3Wlc1emVXMXdhRzl1ZVM1dmMyTmhZMmhsTG1KaGMyVXVaWFpsYm5SekxrTmhZMmhsUlhabGJuUk1hWE4wWlc1bGNuUUFGMnBoZG1FdWRYUnBiQzVGZG1WdWRFeHBjM1JsYm1WeWRBQXNZMjl0TG05d1pXNXplVzF3YUc5dWVTNXZjMk5oWTJobExtSmhjMlV1VEdsbVpXTjVZMnhsUVhkaGNtVjR0AA0xMjkuMTg2Ljc5LjQ1c3IAMm9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuSmF2YVNlcnZpY2VEZWZpbml0aW9utucNX0E0bPECAAFMABFzZXJ2aWNlSW50ZXJmYWNlc3QAEExqYXZhL3V0aWwvTGlzdDt4cgAub3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5TZXJ2aWNlRGVmaW5pdGlvbgCbAk9Y3Sl+AgANTAALYnVzU2VjdXJpdHlxAH4AE0wAD2NyZWRlbnRpYWxzVHlwZXQATExvcmcva3VhbGkvcmljZS9jb3JlL3NlY3VyaXR5L2NyZWRlbnRpYWxzL0NyZWRlbnRpYWxzU291cmNlJENyZWRlbnRpYWxzVHlwZTtMABBsb2NhbFNlcnZpY2VOYW1lcQB+AARMABdtZXNzYWdlRXhjZXB0aW9uSGFuZGxlcnEAfgAETAAMbWlsbGlzVG9MaXZlcQB+ABVMAAhwcmlvcml0eXEAfgAUTAAFcXVldWVxAH4AE0wADXJldHJ5QXR0ZW1wdHNxAH4AFEwAB3NlcnZpY2V0ABJMamF2YS9sYW5nL09iamVjdDtMAA9zZXJ2aWNlRW5kUG9pbnR0AA5MamF2YS9uZXQvVVJMO0wAE3NlcnZpY2VOYW1lU3BhY2VVUklxAH4ABEwAEHNlcnZpY2VOYW1lc3BhY2VxAH4ABEwAC3NlcnZpY2VQYXRocQB+AAR4cHNxAH4AGQFwdAAaT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2V0AE1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLmV4Y2VwdGlvbmhhbmRsaW5nLkRlZmF1bHRNZXNzYWdlRXhjZXB0aW9uSGFuZGxlcnNxAH4AH///////////c3EAfgAcAAAAA3NxAH4AGQBxAH4AMnBzcgAMamF2YS5uZXQuVVJMliU3Nhr85HIDAAdJAAhoYXNoQ29kZUkABHBvcnRMAAlhdXRob3JpdHlxAH4ABEwABGZpbGVxAH4ABEwABGhvc3RxAH4ABEwACHByb3RvY29scQB+AARMAANyZWZxAH4ABHhwaRuioAAAH5B0AA5sb2NhbGhvc3Q6ODA4MHQAKy9rci1kZXYvcmVtb3RpbmcvT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2V0AAlsb2NhbGhvc3R0AARodHRwcHh0AAB0AAZUUkFWRUx0AAEvc3IAE2phdmEudXRpbC5BcnJheUxpc3R4gdIdmcdhnQMAAUkABHNpemV4cAAAAAV3BAAAAAp0ADNvcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLnNlcnZpY2UuS1NCSmF2YVNlcnZpY2V0ADxjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5ldmVudHMuQ2FjaGVFbnRyeUV2ZW50TGlzdGVuZXJ0ADdjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5ldmVudHMuQ2FjaGVFdmVudExpc3RlbmVydAAXamF2YS51dGlsLkV2ZW50TGlzdGVuZXJ0ACxjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5MaWZlY3ljbGVBd2FyZXhxAH4AI3QABlRSQVZFTA==';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
INSERT INTO KRSB_MSG_PYLD_T (MSG_PYLD,MSG_QUE_ID)
  VALUES (EMPTY_CLOB(),421)
/
-- Length: 6104
-- Chunks: 2
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 421    FOR UPDATE;        
    buffer := 'rO0ABXNyAC1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLkFzeW5jaHJvbm91c0NhbGzxnQ5Y0t0lZQIAB1oAFWlnbm9yZVN0b3JlQW5kRm9yd2FyZFsACWFyZ3VtZW50c3QAE1tMamF2YS9sYW5nL09iamVjdDtMAAhjYWxsYmFja3QAM0xvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL0FzeW5jaHJvbm91c0NhbGxiYWNrO0wAB2NvbnRleHR0ABZMamF2YS9pby9TZXJpYWxpemFibGU7TAAKbWV0aG9kTmFtZXQAEkxqYXZhL2xhbmcvU3RyaW5nO1sACnBhcmFtVHlwZXN0ABJbTGphdmEvbGFuZy9DbGFzcztMAAtzZXJ2aWNlSW5mb3QAKkxvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL1NlcnZpY2VJbmZvO3hwAHVyABNbTGphdmEubGFuZy5PYmplY3Q7kM5YnxBzKWwCAAB4cAAAAAFzcgBDY29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLnBsdWdpbnMuY2x1c3RlcnN1cHBvcnQuQ2x1c3Rlck5vdGlmaWNhdGlvbhY+6VckvG9pAgACSQAEdHlwZUwABGRhdGFxAH4AA3hwAAAAAnQAL0RvY3VtZW50VHlwZVJ1bGVDYWNoZTpDYW1wdXNNYWludGVuYW5jZURvY3VtZW50cHB0AAZpbnZva2V1cgASW0xqYXZhLmxhbmcuQ2xhc3M7qxbXrsvNWpkCAAB4cAAAAAF2cgAUamF2YS5pby5TZXJpYWxpemFibGUQm2EN15tk7QIAAHhwc3IAKG9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuU2VydmljZUluZm/FFyO2KQS3ugIAC0wABWFsaXZldAATTGphdmEvbGFuZy9Cb29sZWFuO0wAFGVuZHBvaW50QWx0ZXJuYXRlVXJscQB+AARMAAtlbmRwb2ludFVybHEAfgAETAAKbG9ja1Zlck5icnQAE0xqYXZhL2xhbmcvSW50ZWdlcjtMAA5tZXNzYWdlRW50cnlJZHQAEExqYXZhL2xhbmcvTG9uZztMAAVxbmFtZXQAG0xqYXZheC94bWwvbmFtZXNwYWNlL1FOYW1lO0wAGnNlcmlhbGl6ZWRTZXJ2aWNlTmFtZXNwYWNlcQB+AARMAAhzZXJ2ZXJJcHEAfgAETAARc2VydmljZURlZmluaXRpb250ADBMb3JnL2t1YWxpL3JpY2Uva3NiL21lc3NhZ2luZy9TZXJ2aWNlRGVmaW5pdGlvbjtMAAtzZXJ2aWNlTmFtZXEAfgAETAAQc2VydmljZU5hbWVzcGFjZXEAfgAEeHBzcgARamF2YS5sYW5nLkJvb2xlYW7NIHKA1Zz67gIAAVoABXZhbHVleHABcHQAQGh0dHA6Ly9sb2NhbGhvc3Q6ODA4MC9rci1kZXYvcmVtb3RpbmcvT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2VzcgARamF2YS5sYW5nLkludGVnZXIS4qCk94GHOAIAAUkABXZhbHVleHIAEGphdmEubGFuZy5OdW1iZXKGrJUdC5TgiwIAAHhwAAAAAXNyAA5qYXZhLmxhbmcuTG9uZzuL5JDMjyPfAgABSgAFdmFsdWV4cQB+AB0AAAAAAAAPfXNyABlqYXZheC54bWwubmFtZXNwYWNlLlFOYW1lgW2oLfw73WwCAANMAAlsb2NhbFBhcnRxAH4ABEwADG5hbWVzcGFjZVVSSXEAfgAETAAGcHJlZml4cQB+AAR4cHQAGk9TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNldAAAcQB+ACR0B3RyTzBBQlhOeUFESnZjbWN1YTNWaGJHa3VjbWxqWlM1cmMySXViV1Z6YzJGbmFXNW5Ma3BoZG1GVFpYSjJhV05sUkdWbWFXNXBkR2x2YnJibkRWOUJOR3p4QWdBQlRBQVJjMlZ5ZG1salpVbHVkR1Z5Wm1GalpYTjBBQkJNYW1GMllTOTFkR2xzTDB4cGMzUTdlSElBTG05eVp5NXJkV0ZzYVM1eWFXTmxMbXR6WWk1dFpYTnpZV2RwYm1jdVUyVnlkbWxqWlVSbFptbHVhWFJwYjI0QW13SlBXTjBwZmdJQURVd0FDMkoxYzFObFkzVnlhWFI1ZEFBVFRHcGhkbUV2YkdGdVp5OUNiMjlzWldGdU8wd0FEMk55WldSbGJuUnBZV3h6Vkhsd1pYUUFURXh2Y21jdmEzVmhiR2t2Y21salpTOWpiM0psTDNObFkzVnlhWFI1TDJOeVpXUmxiblJwWVd4ekwwTnlaV1JsYm5ScFlXeHpVMjkxY21ObEpFTnlaV1JsYm5ScFlXeHpWSGx3WlR0TUFCQnNiMk5oYkZObGNuWnBZMlZPWVcxbGRBQVNUR3BoZG1FdmJHRnVaeTlUZEhKcGJtYzdUQUFYYldWemMyRm5aVVY0WTJWd2RHbHZia2hoYm1Sc1pYSnhBSDRBQlV3QURHMXBiR3hwYzFSdlRHbDJaWFFBRUV4cVlYWmhMMnhoYm1jdlRHOXVaenRNQUFod2NtbHZjbWwwZVhRQUUweHFZWFpoTDJ4aGJtY3ZTVzUwWldkbGNqdE1BQVZ4ZFdWMVpYRUFmZ0FEVEFBTmNtVjBjbmxCZEhSbGJYQjBjM0VBZmdBSFRBQUhjMlZ5ZG1salpYUUFFa3hxWVhaaEwyeGhibWN2VDJKcVpXTjBPMHdBRDNObGNuWnBZMlZGYm1SUWIybHVkSFFBRGt4cVlYWmhMMjVsZEM5VlVrdzdUQUFUYzJWeWRtbGpaVTVoYldWVGNHRmpaVlZTU1hFQWZnQUZUQUFRYzJWeWRtbGpaVTVoYldWemNHRmpaWEVBZmdBRlRBQUxjMlZ5ZG1salpWQmhkR2h4QUg0QUJYaHdjM0lBRVdwaGRtRXViR0Z1Wnk1Q2IyOXNaV0Z1elNCeWdOV2MrdTRDQUFGYUFBVjJZV3gxWlhod0FYQjBBQnBQVTBOaFkyaGxUbTkwYVdacFkyRjBhVzl1VTJWeWRtbGpaWFFBVFc5eVp5NXJkV0ZzYVM1eWFXTmxMbXR6WWk1dFpYTnpZV2RwYm1jdVpYaGpaWEIwYVc5dWFHRnVaR3hwYm1jdVJHVm1ZWFZzZEUxbGMzTmhaMlZGZUdObGNIUnBiMjVJWVc1a2JHVnljM0lBRG1waGRtRXViR0Z1Wnk1TWIyNW5PNHZra015UEk5OENBQUZLQUFWMllXeDFaWGh5QUJCcVlYWmhMbXhoYm1jdVRuVnRZbVZ5aHF5VkhRdVU0SXNDQUFCNGNQLy8vLy8vLy8vL2MzSUFFV3BoZG1FdWJHRnVaeTVKYm5SbFoyVnlFdUtncFBlQmh6Z0NBQUZKQUFWMllXeDFaWGh4QUg0QUVBQUFBQU56Y1FCK0FBc0FjUUIrQUJOd2MzSUFER3BoZG1FdWJtVjBMbFZTVEpZbE56WWEvT1J5QXdBSFNRQUlhR0Z6YUVOdlpHVkpBQVJ3YjNKMFRBQUpZWFYwYUc5eWFYUjVjUUIrQUFWTUFBUm1hV3hsY1FCK0FBVk1BQVJvYjNOMGNRQitBQVZNQUFod2NtOTBiMk52YkhFQWZnQUZUQUFEY21WbWNRQitBQVY0Y1AvLy8vOEFBQitRZEFBT2JHOWpZV3hvYjNOME9qZ3dPREIwQUNzdmEzSXRaR1YyTDNKbGJXOTBhVzVuTDA5VFEyRmphR1ZPYjNScFptbGpZWFJwYjI1VFpYSjJhV05sZEFBSmJHOWpZV3hvYjNOMGRBQUVhSFIwY0hCNGRBQUFkQUFHVkZKQlZrVk1kQUFCTDNOeUFCTnFZWFpoTG5WMGFXd3VRWEp5WVhsTWFYTjBlSUhTSFpuSFlaMERBQUZKQUFSemFYcGxlSEFBQUFBRmR3UUFBQUFLZEFBemIzSm5M';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 421    FOR UPDATE;        
    buffer := 'bXQxWVd4cExuSnBZMlV1YTNOaUxtMWxjM05oWjJsdVp5NXpaWEoyYVdObExrdFRRa3BoZG1GVFpYSjJhV05sZEFBOFkyOXRMbTl3Wlc1emVXMXdhRzl1ZVM1dmMyTmhZMmhsTG1KaGMyVXVaWFpsYm5SekxrTmhZMmhsUlc1MGNubEZkbVZ1ZEV4cGMzUmxibVZ5ZEFBM1kyOXRMbTl3Wlc1emVXMXdhRzl1ZVM1dmMyTmhZMmhsTG1KaGMyVXVaWFpsYm5SekxrTmhZMmhsUlhabGJuUk1hWE4wWlc1bGNuUUFGMnBoZG1FdWRYUnBiQzVGZG1WdWRFeHBjM1JsYm1WeWRBQXNZMjl0TG05d1pXNXplVzF3YUc5dWVTNXZjMk5oWTJobExtSmhjMlV1VEdsbVpXTjVZMnhsUVhkaGNtVjR0AA0xMjkuMTg2Ljc5LjQ1c3IAMm9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuSmF2YVNlcnZpY2VEZWZpbml0aW9utucNX0E0bPECAAFMABFzZXJ2aWNlSW50ZXJmYWNlc3QAEExqYXZhL3V0aWwvTGlzdDt4cgAub3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5TZXJ2aWNlRGVmaW5pdGlvbgCbAk9Y3Sl+AgANTAALYnVzU2VjdXJpdHlxAH4AE0wAD2NyZWRlbnRpYWxzVHlwZXQATExvcmcva3VhbGkvcmljZS9jb3JlL3NlY3VyaXR5L2NyZWRlbnRpYWxzL0NyZWRlbnRpYWxzU291cmNlJENyZWRlbnRpYWxzVHlwZTtMABBsb2NhbFNlcnZpY2VOYW1lcQB+AARMABdtZXNzYWdlRXhjZXB0aW9uSGFuZGxlcnEAfgAETAAMbWlsbGlzVG9MaXZlcQB+ABVMAAhwcmlvcml0eXEAfgAUTAAFcXVldWVxAH4AE0wADXJldHJ5QXR0ZW1wdHNxAH4AFEwAB3NlcnZpY2V0ABJMamF2YS9sYW5nL09iamVjdDtMAA9zZXJ2aWNlRW5kUG9pbnR0AA5MamF2YS9uZXQvVVJMO0wAE3NlcnZpY2VOYW1lU3BhY2VVUklxAH4ABEwAEHNlcnZpY2VOYW1lc3BhY2VxAH4ABEwAC3NlcnZpY2VQYXRocQB+AAR4cHNxAH4AGQFwdAAaT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2V0AE1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLmV4Y2VwdGlvbmhhbmRsaW5nLkRlZmF1bHRNZXNzYWdlRXhjZXB0aW9uSGFuZGxlcnNxAH4AH///////////c3EAfgAcAAAAA3NxAH4AGQBxAH4AMnBzcgAMamF2YS5uZXQuVVJMliU3Nhr85HIDAAdJAAhoYXNoQ29kZUkABHBvcnRMAAlhdXRob3JpdHlxAH4ABEwABGZpbGVxAH4ABEwABGhvc3RxAH4ABEwACHByb3RvY29scQB+AARMAANyZWZxAH4ABHhwaRuioAAAH5B0AA5sb2NhbGhvc3Q6ODA4MHQAKy9rci1kZXYvcmVtb3RpbmcvT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2V0AAlsb2NhbGhvc3R0AARodHRwcHh0AAB0AAZUUkFWRUx0AAEvc3IAE2phdmEudXRpbC5BcnJheUxpc3R4gdIdmcdhnQMAAUkABHNpemV4cAAAAAV3BAAAAAp0ADNvcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLnNlcnZpY2UuS1NCSmF2YVNlcnZpY2V0ADxjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5ldmVudHMuQ2FjaGVFbnRyeUV2ZW50TGlzdGVuZXJ0ADdjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5ldmVudHMuQ2FjaGVFdmVudExpc3RlbmVydAAXamF2YS51dGlsLkV2ZW50TGlzdGVuZXJ0ACxjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5MaWZlY3ljbGVBd2FyZXhxAH4AI3QABlRSQVZFTA==';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
INSERT INTO KRSB_MSG_PYLD_T (MSG_PYLD,MSG_QUE_ID)
  VALUES (EMPTY_CLOB(),422)
/
-- Length: 6108
-- Chunks: 2
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 422    FOR UPDATE;        
    buffer := 'rO0ABXNyAC1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLkFzeW5jaHJvbm91c0NhbGzxnQ5Y0t0lZQIAB1oAFWlnbm9yZVN0b3JlQW5kRm9yd2FyZFsACWFyZ3VtZW50c3QAE1tMamF2YS9sYW5nL09iamVjdDtMAAhjYWxsYmFja3QAM0xvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL0FzeW5jaHJvbm91c0NhbGxiYWNrO0wAB2NvbnRleHR0ABZMamF2YS9pby9TZXJpYWxpemFibGU7TAAKbWV0aG9kTmFtZXQAEkxqYXZhL2xhbmcvU3RyaW5nO1sACnBhcmFtVHlwZXN0ABJbTGphdmEvbGFuZy9DbGFzcztMAAtzZXJ2aWNlSW5mb3QAKkxvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL1NlcnZpY2VJbmZvO3hwAHVyABNbTGphdmEubGFuZy5PYmplY3Q7kM5YnxBzKWwCAAB4cAAAAAFzcgBDY29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLnBsdWdpbnMuY2x1c3RlcnN1cHBvcnQuQ2x1c3Rlck5vdGlmaWNhdGlvbhY+6VckvG9pAgACSQAEdHlwZUwABGRhdGFxAH4AA3hwAAAAAnQAM0RvY3VtZW50VHlwZVJ1bGVDYWNoZTpDYW1wdXNUeXBlTWFpbnRlbmFuY2VEb2N1bWVudHBwdAAGaW52b2tldXIAEltMamF2YS5sYW5nLkNsYXNzO6sW167LzVqZAgAAeHAAAAABdnIAFGphdmEuaW8uU2VyaWFsaXphYmxlEJthDdebZO0CAAB4cHNyAChvcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLlNlcnZpY2VJbmZvxRcjtikEt7oCAAtMAAVhbGl2ZXQAE0xqYXZhL2xhbmcvQm9vbGVhbjtMABRlbmRwb2ludEFsdGVybmF0ZVVybHEAfgAETAALZW5kcG9pbnRVcmxxAH4ABEwACmxvY2tWZXJOYnJ0ABNMamF2YS9sYW5nL0ludGVnZXI7TAAObWVzc2FnZUVudHJ5SWR0ABBMamF2YS9sYW5nL0xvbmc7TAAFcW5hbWV0ABtMamF2YXgveG1sL25hbWVzcGFjZS9RTmFtZTtMABpzZXJpYWxpemVkU2VydmljZU5hbWVzcGFjZXEAfgAETAAIc2VydmVySXBxAH4ABEwAEXNlcnZpY2VEZWZpbml0aW9udAAwTG9yZy9rdWFsaS9yaWNlL2tzYi9tZXNzYWdpbmcvU2VydmljZURlZmluaXRpb247TAALc2VydmljZU5hbWVxAH4ABEwAEHNlcnZpY2VOYW1lc3BhY2VxAH4ABHhwc3IAEWphdmEubGFuZy5Cb29sZWFuzSBygNWc+u4CAAFaAAV2YWx1ZXhwAXB0AEBodHRwOi8vbG9jYWxob3N0OjgwODAva3ItZGV2L3JlbW90aW5nL09TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNlc3IAEWphdmEubGFuZy5JbnRlZ2VyEuKgpPeBhzgCAAFJAAV2YWx1ZXhyABBqYXZhLmxhbmcuTnVtYmVyhqyVHQuU4IsCAAB4cAAAAAFzcgAOamF2YS5sYW5nLkxvbmc7i+SQzI8j3wIAAUoABXZhbHVleHEAfgAdAAAAAAAAD31zcgAZamF2YXgueG1sLm5hbWVzcGFjZS5RTmFtZYFtqC38O91sAgADTAAJbG9jYWxQYXJ0cQB+AARMAAxuYW1lc3BhY2VVUklxAH4ABEwABnByZWZpeHEAfgAEeHB0ABpPU0NhY2hlTm90aWZpY2F0aW9uU2VydmljZXQAAHEAfgAkdAd0ck8wQUJYTnlBREp2Y21jdWEzVmhiR2t1Y21salpTNXJjMkl1YldWemMyRm5hVzVuTGtwaGRtRlRaWEoyYVdObFJHVm1hVzVwZEdsdmJyYm5EVjlCTkd6eEFnQUJUQUFSYzJWeWRtbGpaVWx1ZEdWeVptRmpaWE4wQUJCTWFtRjJZUzkxZEdsc0wweHBjM1E3ZUhJQUxtOXlaeTVyZFdGc2FTNXlhV05sTG10ellpNXRaWE56WVdkcGJtY3VVMlZ5ZG1salpVUmxabWx1YVhScGIyNEFtd0pQV04wcGZnSUFEVXdBQzJKMWMxTmxZM1Z5YVhSNWRBQVRUR3BoZG1FdmJHRnVaeTlDYjI5c1pXRnVPMHdBRDJOeVpXUmxiblJwWVd4elZIbHdaWFFBVEV4dmNtY3ZhM1ZoYkdrdmNtbGpaUzlqYjNKbEwzTmxZM1Z5YVhSNUwyTnlaV1JsYm5ScFlXeHpMME55WldSbGJuUnBZV3h6VTI5MWNtTmxKRU55WldSbGJuUnBZV3h6Vkhsd1pUdE1BQkJzYjJOaGJGTmxjblpwWTJWT1lXMWxkQUFTVEdwaGRtRXZiR0Z1Wnk5VGRISnBibWM3VEFBWGJXVnpjMkZuWlVWNFkyVndkR2x2YmtoaGJtUnNaWEp4QUg0QUJVd0FERzFwYkd4cGMxUnZUR2wyWlhRQUVFeHFZWFpoTDJ4aGJtY3ZURzl1Wnp0TUFBaHdjbWx2Y21sMGVYUUFFMHhxWVhaaEwyeGhibWN2U1c1MFpXZGxjanRNQUFWeGRXVjFaWEVBZmdBRFRBQU5jbVYwY25sQmRIUmxiWEIwYzNFQWZnQUhUQUFIYzJWeWRtbGpaWFFBRWt4cVlYWmhMMnhoYm1jdlQySnFaV04wTzB3QUQzTmxjblpwWTJWRmJtUlFiMmx1ZEhRQURreHFZWFpoTDI1bGRDOVZVa3c3VEFBVGMyVnlkbWxqWlU1aGJXVlRjR0ZqWlZWU1NYRUFmZ0FGVEFBUWMyVnlkbWxqWlU1aGJXVnpjR0ZqWlhFQWZnQUZUQUFMYzJWeWRtbGpaVkJoZEdoeEFINEFCWGh3YzNJQUVXcGhkbUV1YkdGdVp5NUNiMjlzWldGdXpTQnlnTldjK3U0Q0FBRmFBQVYyWVd4MVpYaHdBWEIwQUJwUFUwTmhZMmhsVG05MGFXWnBZMkYwYVc5dVUyVnlkbWxqWlhRQVRXOXlaeTVyZFdGc2FTNXlhV05sTG10ellpNXRaWE56WVdkcGJtY3VaWGhqWlhCMGFXOXVhR0Z1Wkd4cGJtY3VSR1ZtWVhWc2RFMWxjM05oWjJWRmVHTmxjSFJwYjI1SVlXNWtiR1Z5YzNJQURtcGhkbUV1YkdGdVp5NU1iMjVuTzR2a2tNeVBJOThDQUFGS0FBVjJZV3gxWlhoeUFCQnFZWFpoTG14aGJtY3VUblZ0WW1WeWhxeVZIUXVVNElzQ0FBQjRjUC8vLy8vLy8vLy9jM0lBRVdwaGRtRXViR0Z1Wnk1SmJuUmxaMlZ5RXVLZ3BQZUJoemdDQUFGSkFBVjJZV3gxWlhoeEFINEFFQUFBQUFOemNRQitBQXNBY1FCK0FCTndjM0lBREdwaGRtRXVibVYwTGxWU1RKWWxOellhL09SeUF3QUhTUUFJYUdGemFFTnZaR1ZKQUFSd2IzSjBUQUFKWVhWMGFHOXlhWFI1Y1FCK0FBVk1BQVJtYVd4bGNRQitBQVZNQUFSb2IzTjBjUUIrQUFWTUFBaHdjbTkwYjJOdmJIRUFmZ0FGVEFBRGNtVm1jUUIrQUFWNGNQLy8vLzhBQUIrUWRBQU9iRzlqWVd4b2IzTjBPamd3T0RCMEFDc3ZhM0l0WkdWMkwzSmxiVzkwYVc1bkwwOVRRMkZqYUdWT2IzUnBabWxqWVhScGIyNVRaWEoyYVdObGRBQUpiRzlqWVd4b2IzTjBkQUFFYUhSMGNIQjRkQUFBZEFBR1ZGSkJWa1ZNZEFBQkwzTnlBQk5xWVhaaExuVjBhV3d1UVhKeVlYbE1hWE4wZUlIU0habkhZWjBEQUFGSkFBUnphWHBsZUhBQUFBQUZkd1FBQUFBS2RBQXpi';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 422    FOR UPDATE;        
    buffer := 'M0puTG10MVlXeHBMbkpwWTJVdWEzTmlMbTFsYzNOaFoybHVaeTV6WlhKMmFXTmxMa3RUUWtwaGRtRlRaWEoyYVdObGRBQThZMjl0TG05d1pXNXplVzF3YUc5dWVTNXZjMk5oWTJobExtSmhjMlV1WlhabGJuUnpMa05oWTJobFJXNTBjbmxGZG1WdWRFeHBjM1JsYm1WeWRBQTNZMjl0TG05d1pXNXplVzF3YUc5dWVTNXZjMk5oWTJobExtSmhjMlV1WlhabGJuUnpMa05oWTJobFJYWmxiblJNYVhOMFpXNWxjblFBRjJwaGRtRXVkWFJwYkM1RmRtVnVkRXhwYzNSbGJtVnlkQUFzWTI5dExtOXdaVzV6ZVcxd2FHOXVlUzV2YzJOaFkyaGxMbUpoYzJVdVRHbG1aV041WTJ4bFFYZGhjbVY0dAANMTI5LjE4Ni43OS40NXNyADJvcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLkphdmFTZXJ2aWNlRGVmaW5pdGlvbrbnDV9BNGzxAgABTAARc2VydmljZUludGVyZmFjZXN0ABBMamF2YS91dGlsL0xpc3Q7eHIALm9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuU2VydmljZURlZmluaXRpb24AmwJPWN0pfgIADUwAC2J1c1NlY3VyaXR5cQB+ABNMAA9jcmVkZW50aWFsc1R5cGV0AExMb3JnL2t1YWxpL3JpY2UvY29yZS9zZWN1cml0eS9jcmVkZW50aWFscy9DcmVkZW50aWFsc1NvdXJjZSRDcmVkZW50aWFsc1R5cGU7TAAQbG9jYWxTZXJ2aWNlTmFtZXEAfgAETAAXbWVzc2FnZUV4Y2VwdGlvbkhhbmRsZXJxAH4ABEwADG1pbGxpc1RvTGl2ZXEAfgAVTAAIcHJpb3JpdHlxAH4AFEwABXF1ZXVlcQB+ABNMAA1yZXRyeUF0dGVtcHRzcQB+ABRMAAdzZXJ2aWNldAASTGphdmEvbGFuZy9PYmplY3Q7TAAPc2VydmljZUVuZFBvaW50dAAOTGphdmEvbmV0L1VSTDtMABNzZXJ2aWNlTmFtZVNwYWNlVVJJcQB+AARMABBzZXJ2aWNlTmFtZXNwYWNlcQB+AARMAAtzZXJ2aWNlUGF0aHEAfgAEeHBzcQB+ABkBcHQAGk9TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNldABNb3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5leGNlcHRpb25oYW5kbGluZy5EZWZhdWx0TWVzc2FnZUV4Y2VwdGlvbkhhbmRsZXJzcQB+AB///////////3NxAH4AHAAAAANzcQB+ABkAcQB+ADJwc3IADGphdmEubmV0LlVSTJYlNzYa/ORyAwAHSQAIaGFzaENvZGVJAARwb3J0TAAJYXV0aG9yaXR5cQB+AARMAARmaWxlcQB+AARMAARob3N0cQB+AARMAAhwcm90b2NvbHEAfgAETAADcmVmcQB+AAR4cGkboqAAAB+QdAAObG9jYWxob3N0OjgwODB0ACsva3ItZGV2L3JlbW90aW5nL09TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNldAAJbG9jYWxob3N0dAAEaHR0cHB4dAAAdAAGVFJBVkVMdAABL3NyABNqYXZhLnV0aWwuQXJyYXlMaXN0eIHSHZnHYZ0DAAFJAARzaXpleHAAAAAFdwQAAAAKdAAzb3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5zZXJ2aWNlLktTQkphdmFTZXJ2aWNldAA8Y29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLmJhc2UuZXZlbnRzLkNhY2hlRW50cnlFdmVudExpc3RlbmVydAA3Y29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLmJhc2UuZXZlbnRzLkNhY2hlRXZlbnRMaXN0ZW5lcnQAF2phdmEudXRpbC5FdmVudExpc3RlbmVydAAsY29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLmJhc2UuTGlmZWN5Y2xlQXdhcmV4cQB+ACN0AAZUUkFWRUw=';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
INSERT INTO KRSB_MSG_PYLD_T (MSG_PYLD,MSG_QUE_ID)
  VALUES (EMPTY_CLOB(),423)
/
-- Length: 6104
-- Chunks: 2
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 423    FOR UPDATE;        
    buffer := 'rO0ABXNyAC1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLkFzeW5jaHJvbm91c0NhbGzxnQ5Y0t0lZQIAB1oAFWlnbm9yZVN0b3JlQW5kRm9yd2FyZFsACWFyZ3VtZW50c3QAE1tMamF2YS9sYW5nL09iamVjdDtMAAhjYWxsYmFja3QAM0xvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL0FzeW5jaHJvbm91c0NhbGxiYWNrO0wAB2NvbnRleHR0ABZMamF2YS9pby9TZXJpYWxpemFibGU7TAAKbWV0aG9kTmFtZXQAEkxqYXZhL2xhbmcvU3RyaW5nO1sACnBhcmFtVHlwZXN0ABJbTGphdmEvbGFuZy9DbGFzcztMAAtzZXJ2aWNlSW5mb3QAKkxvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL1NlcnZpY2VJbmZvO3hwAHVyABNbTGphdmEubGFuZy5PYmplY3Q7kM5YnxBzKWwCAAB4cAAAAAFzcgBDY29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLnBsdWdpbnMuY2x1c3RlcnN1cHBvcnQuQ2x1c3Rlck5vdGlmaWNhdGlvbhY+6VckvG9pAgACSQAEdHlwZUwABGRhdGFxAH4AA3hwAAAAAnQAMERvY3VtZW50VHlwZVJ1bGVDYWNoZTpDb3VudHJ5TWFpbnRlbmFuY2VEb2N1bWVudHBwdAAGaW52b2tldXIAEltMamF2YS5sYW5nLkNsYXNzO6sW167LzVqZAgAAeHAAAAABdnIAFGphdmEuaW8uU2VyaWFsaXphYmxlEJthDdebZO0CAAB4cHNyAChvcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLlNlcnZpY2VJbmZvxRcjtikEt7oCAAtMAAVhbGl2ZXQAE0xqYXZhL2xhbmcvQm9vbGVhbjtMABRlbmRwb2ludEFsdGVybmF0ZVVybHEAfgAETAALZW5kcG9pbnRVcmxxAH4ABEwACmxvY2tWZXJOYnJ0ABNMamF2YS9sYW5nL0ludGVnZXI7TAAObWVzc2FnZUVudHJ5SWR0ABBMamF2YS9sYW5nL0xvbmc7TAAFcW5hbWV0ABtMamF2YXgveG1sL25hbWVzcGFjZS9RTmFtZTtMABpzZXJpYWxpemVkU2VydmljZU5hbWVzcGFjZXEAfgAETAAIc2VydmVySXBxAH4ABEwAEXNlcnZpY2VEZWZpbml0aW9udAAwTG9yZy9rdWFsaS9yaWNlL2tzYi9tZXNzYWdpbmcvU2VydmljZURlZmluaXRpb247TAALc2VydmljZU5hbWVxAH4ABEwAEHNlcnZpY2VOYW1lc3BhY2VxAH4ABHhwc3IAEWphdmEubGFuZy5Cb29sZWFuzSBygNWc+u4CAAFaAAV2YWx1ZXhwAXB0AEBodHRwOi8vbG9jYWxob3N0OjgwODAva3ItZGV2L3JlbW90aW5nL09TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNlc3IAEWphdmEubGFuZy5JbnRlZ2VyEuKgpPeBhzgCAAFJAAV2YWx1ZXhyABBqYXZhLmxhbmcuTnVtYmVyhqyVHQuU4IsCAAB4cAAAAAFzcgAOamF2YS5sYW5nLkxvbmc7i+SQzI8j3wIAAUoABXZhbHVleHEAfgAdAAAAAAAAD31zcgAZamF2YXgueG1sLm5hbWVzcGFjZS5RTmFtZYFtqC38O91sAgADTAAJbG9jYWxQYXJ0cQB+AARMAAxuYW1lc3BhY2VVUklxAH4ABEwABnByZWZpeHEAfgAEeHB0ABpPU0NhY2hlTm90aWZpY2F0aW9uU2VydmljZXQAAHEAfgAkdAd0ck8wQUJYTnlBREp2Y21jdWEzVmhiR2t1Y21salpTNXJjMkl1YldWemMyRm5hVzVuTGtwaGRtRlRaWEoyYVdObFJHVm1hVzVwZEdsdmJyYm5EVjlCTkd6eEFnQUJUQUFSYzJWeWRtbGpaVWx1ZEdWeVptRmpaWE4wQUJCTWFtRjJZUzkxZEdsc0wweHBjM1E3ZUhJQUxtOXlaeTVyZFdGc2FTNXlhV05sTG10ellpNXRaWE56WVdkcGJtY3VVMlZ5ZG1salpVUmxabWx1YVhScGIyNEFtd0pQV04wcGZnSUFEVXdBQzJKMWMxTmxZM1Z5YVhSNWRBQVRUR3BoZG1FdmJHRnVaeTlDYjI5c1pXRnVPMHdBRDJOeVpXUmxiblJwWVd4elZIbHdaWFFBVEV4dmNtY3ZhM1ZoYkdrdmNtbGpaUzlqYjNKbEwzTmxZM1Z5YVhSNUwyTnlaV1JsYm5ScFlXeHpMME55WldSbGJuUnBZV3h6VTI5MWNtTmxKRU55WldSbGJuUnBZV3h6Vkhsd1pUdE1BQkJzYjJOaGJGTmxjblpwWTJWT1lXMWxkQUFTVEdwaGRtRXZiR0Z1Wnk5VGRISnBibWM3VEFBWGJXVnpjMkZuWlVWNFkyVndkR2x2YmtoaGJtUnNaWEp4QUg0QUJVd0FERzFwYkd4cGMxUnZUR2wyWlhRQUVFeHFZWFpoTDJ4aGJtY3ZURzl1Wnp0TUFBaHdjbWx2Y21sMGVYUUFFMHhxWVhaaEwyeGhibWN2U1c1MFpXZGxjanRNQUFWeGRXVjFaWEVBZmdBRFRBQU5jbVYwY25sQmRIUmxiWEIwYzNFQWZnQUhUQUFIYzJWeWRtbGpaWFFBRWt4cVlYWmhMMnhoYm1jdlQySnFaV04wTzB3QUQzTmxjblpwWTJWRmJtUlFiMmx1ZEhRQURreHFZWFpoTDI1bGRDOVZVa3c3VEFBVGMyVnlkbWxqWlU1aGJXVlRjR0ZqWlZWU1NYRUFmZ0FGVEFBUWMyVnlkbWxqWlU1aGJXVnpjR0ZqWlhFQWZnQUZUQUFMYzJWeWRtbGpaVkJoZEdoeEFINEFCWGh3YzNJQUVXcGhkbUV1YkdGdVp5NUNiMjlzWldGdXpTQnlnTldjK3U0Q0FBRmFBQVYyWVd4MVpYaHdBWEIwQUJwUFUwTmhZMmhsVG05MGFXWnBZMkYwYVc5dVUyVnlkbWxqWlhRQVRXOXlaeTVyZFdGc2FTNXlhV05sTG10ellpNXRaWE56WVdkcGJtY3VaWGhqWlhCMGFXOXVhR0Z1Wkd4cGJtY3VSR1ZtWVhWc2RFMWxjM05oWjJWRmVHTmxjSFJwYjI1SVlXNWtiR1Z5YzNJQURtcGhkbUV1YkdGdVp5NU1iMjVuTzR2a2tNeVBJOThDQUFGS0FBVjJZV3gxWlhoeUFCQnFZWFpoTG14aGJtY3VUblZ0WW1WeWhxeVZIUXVVNElzQ0FBQjRjUC8vLy8vLy8vLy9jM0lBRVdwaGRtRXViR0Z1Wnk1SmJuUmxaMlZ5RXVLZ3BQZUJoemdDQUFGSkFBVjJZV3gxWlhoeEFINEFFQUFBQUFOemNRQitBQXNBY1FCK0FCTndjM0lBREdwaGRtRXVibVYwTGxWU1RKWWxOellhL09SeUF3QUhTUUFJYUdGemFFTnZaR1ZKQUFSd2IzSjBUQUFKWVhWMGFHOXlhWFI1Y1FCK0FBVk1BQVJtYVd4bGNRQitBQVZNQUFSb2IzTjBjUUIrQUFWTUFBaHdjbTkwYjJOdmJIRUFmZ0FGVEFBRGNtVm1jUUIrQUFWNGNQLy8vLzhBQUIrUWRBQU9iRzlqWVd4b2IzTjBPamd3T0RCMEFDc3ZhM0l0WkdWMkwzSmxiVzkwYVc1bkwwOVRRMkZqYUdWT2IzUnBabWxqWVhScGIyNVRaWEoyYVdObGRBQUpiRzlqWVd4b2IzTjBkQUFFYUhSMGNIQjRkQUFBZEFBR1ZGSkJWa1ZNZEFBQkwzTnlBQk5xWVhaaExuVjBhV3d1UVhKeVlYbE1hWE4wZUlIU0habkhZWjBEQUFGSkFBUnphWHBsZUhBQUFBQUZkd1FBQUFBS2RBQXpiM0pu';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 423    FOR UPDATE;        
    buffer := 'TG10MVlXeHBMbkpwWTJVdWEzTmlMbTFsYzNOaFoybHVaeTV6WlhKMmFXTmxMa3RUUWtwaGRtRlRaWEoyYVdObGRBQThZMjl0TG05d1pXNXplVzF3YUc5dWVTNXZjMk5oWTJobExtSmhjMlV1WlhabGJuUnpMa05oWTJobFJXNTBjbmxGZG1WdWRFeHBjM1JsYm1WeWRBQTNZMjl0TG05d1pXNXplVzF3YUc5dWVTNXZjMk5oWTJobExtSmhjMlV1WlhabGJuUnpMa05oWTJobFJYWmxiblJNYVhOMFpXNWxjblFBRjJwaGRtRXVkWFJwYkM1RmRtVnVkRXhwYzNSbGJtVnlkQUFzWTI5dExtOXdaVzV6ZVcxd2FHOXVlUzV2YzJOaFkyaGxMbUpoYzJVdVRHbG1aV041WTJ4bFFYZGhjbVY0dAANMTI5LjE4Ni43OS40NXNyADJvcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLkphdmFTZXJ2aWNlRGVmaW5pdGlvbrbnDV9BNGzxAgABTAARc2VydmljZUludGVyZmFjZXN0ABBMamF2YS91dGlsL0xpc3Q7eHIALm9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuU2VydmljZURlZmluaXRpb24AmwJPWN0pfgIADUwAC2J1c1NlY3VyaXR5cQB+ABNMAA9jcmVkZW50aWFsc1R5cGV0AExMb3JnL2t1YWxpL3JpY2UvY29yZS9zZWN1cml0eS9jcmVkZW50aWFscy9DcmVkZW50aWFsc1NvdXJjZSRDcmVkZW50aWFsc1R5cGU7TAAQbG9jYWxTZXJ2aWNlTmFtZXEAfgAETAAXbWVzc2FnZUV4Y2VwdGlvbkhhbmRsZXJxAH4ABEwADG1pbGxpc1RvTGl2ZXEAfgAVTAAIcHJpb3JpdHlxAH4AFEwABXF1ZXVlcQB+ABNMAA1yZXRyeUF0dGVtcHRzcQB+ABRMAAdzZXJ2aWNldAASTGphdmEvbGFuZy9PYmplY3Q7TAAPc2VydmljZUVuZFBvaW50dAAOTGphdmEvbmV0L1VSTDtMABNzZXJ2aWNlTmFtZVNwYWNlVVJJcQB+AARMABBzZXJ2aWNlTmFtZXNwYWNlcQB+AARMAAtzZXJ2aWNlUGF0aHEAfgAEeHBzcQB+ABkBcHQAGk9TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNldABNb3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5leGNlcHRpb25oYW5kbGluZy5EZWZhdWx0TWVzc2FnZUV4Y2VwdGlvbkhhbmRsZXJzcQB+AB///////////3NxAH4AHAAAAANzcQB+ABkAcQB+ADJwc3IADGphdmEubmV0LlVSTJYlNzYa/ORyAwAHSQAIaGFzaENvZGVJAARwb3J0TAAJYXV0aG9yaXR5cQB+AARMAARmaWxlcQB+AARMAARob3N0cQB+AARMAAhwcm90b2NvbHEAfgAETAADcmVmcQB+AAR4cGkboqAAAB+QdAAObG9jYWxob3N0OjgwODB0ACsva3ItZGV2L3JlbW90aW5nL09TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNldAAJbG9jYWxob3N0dAAEaHR0cHB4dAAAdAAGVFJBVkVMdAABL3NyABNqYXZhLnV0aWwuQXJyYXlMaXN0eIHSHZnHYZ0DAAFJAARzaXpleHAAAAAFdwQAAAAKdAAzb3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5zZXJ2aWNlLktTQkphdmFTZXJ2aWNldAA8Y29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLmJhc2UuZXZlbnRzLkNhY2hlRW50cnlFdmVudExpc3RlbmVydAA3Y29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLmJhc2UuZXZlbnRzLkNhY2hlRXZlbnRMaXN0ZW5lcnQAF2phdmEudXRpbC5FdmVudExpc3RlbmVydAAsY29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLmJhc2UuTGlmZWN5Y2xlQXdhcmV4cQB+ACN0AAZUUkFWRUw=';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
INSERT INTO KRSB_MSG_PYLD_T (MSG_PYLD,MSG_QUE_ID)
  VALUES (EMPTY_CLOB(),424)
/
-- Length: 6104
-- Chunks: 2
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 424    FOR UPDATE;        
    buffer := 'rO0ABXNyAC1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLkFzeW5jaHJvbm91c0NhbGzxnQ5Y0t0lZQIAB1oAFWlnbm9yZVN0b3JlQW5kRm9yd2FyZFsACWFyZ3VtZW50c3QAE1tMamF2YS9sYW5nL09iamVjdDtMAAhjYWxsYmFja3QAM0xvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL0FzeW5jaHJvbm91c0NhbGxiYWNrO0wAB2NvbnRleHR0ABZMamF2YS9pby9TZXJpYWxpemFibGU7TAAKbWV0aG9kTmFtZXQAEkxqYXZhL2xhbmcvU3RyaW5nO1sACnBhcmFtVHlwZXN0ABJbTGphdmEvbGFuZy9DbGFzcztMAAtzZXJ2aWNlSW5mb3QAKkxvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL1NlcnZpY2VJbmZvO3hwAHVyABNbTGphdmEubGFuZy5PYmplY3Q7kM5YnxBzKWwCAAB4cAAAAAFzcgBDY29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLnBsdWdpbnMuY2x1c3RlcnN1cHBvcnQuQ2x1c3Rlck5vdGlmaWNhdGlvbhY+6VckvG9pAgACSQAEdHlwZUwABGRhdGFxAH4AA3hwAAAAAnQAL0RvY3VtZW50VHlwZVJ1bGVDYWNoZTpDb3VudHlNYWludGVuYW5jZURvY3VtZW50cHB0AAZpbnZva2V1cgASW0xqYXZhLmxhbmcuQ2xhc3M7qxbXrsvNWpkCAAB4cAAAAAF2cgAUamF2YS5pby5TZXJpYWxpemFibGUQm2EN15tk7QIAAHhwc3IAKG9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuU2VydmljZUluZm/FFyO2KQS3ugIAC0wABWFsaXZldAATTGphdmEvbGFuZy9Cb29sZWFuO0wAFGVuZHBvaW50QWx0ZXJuYXRlVXJscQB+AARMAAtlbmRwb2ludFVybHEAfgAETAAKbG9ja1Zlck5icnQAE0xqYXZhL2xhbmcvSW50ZWdlcjtMAA5tZXNzYWdlRW50cnlJZHQAEExqYXZhL2xhbmcvTG9uZztMAAVxbmFtZXQAG0xqYXZheC94bWwvbmFtZXNwYWNlL1FOYW1lO0wAGnNlcmlhbGl6ZWRTZXJ2aWNlTmFtZXNwYWNlcQB+AARMAAhzZXJ2ZXJJcHEAfgAETAARc2VydmljZURlZmluaXRpb250ADBMb3JnL2t1YWxpL3JpY2Uva3NiL21lc3NhZ2luZy9TZXJ2aWNlRGVmaW5pdGlvbjtMAAtzZXJ2aWNlTmFtZXEAfgAETAAQc2VydmljZU5hbWVzcGFjZXEAfgAEeHBzcgARamF2YS5sYW5nLkJvb2xlYW7NIHKA1Zz67gIAAVoABXZhbHVleHABcHQAQGh0dHA6Ly9sb2NhbGhvc3Q6ODA4MC9rci1kZXYvcmVtb3RpbmcvT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2VzcgARamF2YS5sYW5nLkludGVnZXIS4qCk94GHOAIAAUkABXZhbHVleHIAEGphdmEubGFuZy5OdW1iZXKGrJUdC5TgiwIAAHhwAAAAAXNyAA5qYXZhLmxhbmcuTG9uZzuL5JDMjyPfAgABSgAFdmFsdWV4cQB+AB0AAAAAAAAPfXNyABlqYXZheC54bWwubmFtZXNwYWNlLlFOYW1lgW2oLfw73WwCAANMAAlsb2NhbFBhcnRxAH4ABEwADG5hbWVzcGFjZVVSSXEAfgAETAAGcHJlZml4cQB+AAR4cHQAGk9TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNldAAAcQB+ACR0B3RyTzBBQlhOeUFESnZjbWN1YTNWaGJHa3VjbWxqWlM1cmMySXViV1Z6YzJGbmFXNW5Ma3BoZG1GVFpYSjJhV05sUkdWbWFXNXBkR2x2YnJibkRWOUJOR3p4QWdBQlRBQVJjMlZ5ZG1salpVbHVkR1Z5Wm1GalpYTjBBQkJNYW1GMllTOTFkR2xzTDB4cGMzUTdlSElBTG05eVp5NXJkV0ZzYVM1eWFXTmxMbXR6WWk1dFpYTnpZV2RwYm1jdVUyVnlkbWxqWlVSbFptbHVhWFJwYjI0QW13SlBXTjBwZmdJQURVd0FDMkoxYzFObFkzVnlhWFI1ZEFBVFRHcGhkbUV2YkdGdVp5OUNiMjlzWldGdU8wd0FEMk55WldSbGJuUnBZV3h6Vkhsd1pYUUFURXh2Y21jdmEzVmhiR2t2Y21salpTOWpiM0psTDNObFkzVnlhWFI1TDJOeVpXUmxiblJwWVd4ekwwTnlaV1JsYm5ScFlXeHpVMjkxY21ObEpFTnlaV1JsYm5ScFlXeHpWSGx3WlR0TUFCQnNiMk5oYkZObGNuWnBZMlZPWVcxbGRBQVNUR3BoZG1FdmJHRnVaeTlUZEhKcGJtYzdUQUFYYldWemMyRm5aVVY0WTJWd2RHbHZia2hoYm1Sc1pYSnhBSDRBQlV3QURHMXBiR3hwYzFSdlRHbDJaWFFBRUV4cVlYWmhMMnhoYm1jdlRHOXVaenRNQUFod2NtbHZjbWwwZVhRQUUweHFZWFpoTDJ4aGJtY3ZTVzUwWldkbGNqdE1BQVZ4ZFdWMVpYRUFmZ0FEVEFBTmNtVjBjbmxCZEhSbGJYQjBjM0VBZmdBSFRBQUhjMlZ5ZG1salpYUUFFa3hxWVhaaEwyeGhibWN2VDJKcVpXTjBPMHdBRDNObGNuWnBZMlZGYm1SUWIybHVkSFFBRGt4cVlYWmhMMjVsZEM5VlVrdzdUQUFUYzJWeWRtbGpaVTVoYldWVGNHRmpaVlZTU1hFQWZnQUZUQUFRYzJWeWRtbGpaVTVoYldWemNHRmpaWEVBZmdBRlRBQUxjMlZ5ZG1salpWQmhkR2h4QUg0QUJYaHdjM0lBRVdwaGRtRXViR0Z1Wnk1Q2IyOXNaV0Z1elNCeWdOV2MrdTRDQUFGYUFBVjJZV3gxWlhod0FYQjBBQnBQVTBOaFkyaGxUbTkwYVdacFkyRjBhVzl1VTJWeWRtbGpaWFFBVFc5eVp5NXJkV0ZzYVM1eWFXTmxMbXR6WWk1dFpYTnpZV2RwYm1jdVpYaGpaWEIwYVc5dWFHRnVaR3hwYm1jdVJHVm1ZWFZzZEUxbGMzTmhaMlZGZUdObGNIUnBiMjVJWVc1a2JHVnljM0lBRG1waGRtRXViR0Z1Wnk1TWIyNW5PNHZra015UEk5OENBQUZLQUFWMllXeDFaWGh5QUJCcVlYWmhMbXhoYm1jdVRuVnRZbVZ5aHF5VkhRdVU0SXNDQUFCNGNQLy8vLy8vLy8vL2MzSUFFV3BoZG1FdWJHRnVaeTVKYm5SbFoyVnlFdUtncFBlQmh6Z0NBQUZKQUFWMllXeDFaWGh4QUg0QUVBQUFBQU56Y1FCK0FBc0FjUUIrQUJOd2MzSUFER3BoZG1FdWJtVjBMbFZTVEpZbE56WWEvT1J5QXdBSFNRQUlhR0Z6YUVOdlpHVkpBQVJ3YjNKMFRBQUpZWFYwYUc5eWFYUjVjUUIrQUFWTUFBUm1hV3hsY1FCK0FBVk1BQVJvYjNOMGNRQitBQVZNQUFod2NtOTBiMk52YkhFQWZnQUZUQUFEY21WbWNRQitBQVY0Y1AvLy8vOEFBQitRZEFBT2JHOWpZV3hvYjNOME9qZ3dPREIwQUNzdmEzSXRaR1YyTDNKbGJXOTBhVzVuTDA5VFEyRmphR1ZPYjNScFptbGpZWFJwYjI1VFpYSjJhV05sZEFBSmJHOWpZV3hvYjNOMGRBQUVhSFIwY0hCNGRBQUFkQUFHVkZKQlZrVk1kQUFCTDNOeUFCTnFZWFpoTG5WMGFXd3VRWEp5WVhsTWFYTjBlSUhTSFpuSFlaMERBQUZKQUFSemFYcGxlSEFBQUFBRmR3UUFBQUFLZEFBemIzSm5M';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 424    FOR UPDATE;        
    buffer := 'bXQxWVd4cExuSnBZMlV1YTNOaUxtMWxjM05oWjJsdVp5NXpaWEoyYVdObExrdFRRa3BoZG1GVFpYSjJhV05sZEFBOFkyOXRMbTl3Wlc1emVXMXdhRzl1ZVM1dmMyTmhZMmhsTG1KaGMyVXVaWFpsYm5SekxrTmhZMmhsUlc1MGNubEZkbVZ1ZEV4cGMzUmxibVZ5ZEFBM1kyOXRMbTl3Wlc1emVXMXdhRzl1ZVM1dmMyTmhZMmhsTG1KaGMyVXVaWFpsYm5SekxrTmhZMmhsUlhabGJuUk1hWE4wWlc1bGNuUUFGMnBoZG1FdWRYUnBiQzVGZG1WdWRFeHBjM1JsYm1WeWRBQXNZMjl0TG05d1pXNXplVzF3YUc5dWVTNXZjMk5oWTJobExtSmhjMlV1VEdsbVpXTjVZMnhsUVhkaGNtVjR0AA0xMjkuMTg2Ljc5LjQ1c3IAMm9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuSmF2YVNlcnZpY2VEZWZpbml0aW9utucNX0E0bPECAAFMABFzZXJ2aWNlSW50ZXJmYWNlc3QAEExqYXZhL3V0aWwvTGlzdDt4cgAub3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5TZXJ2aWNlRGVmaW5pdGlvbgCbAk9Y3Sl+AgANTAALYnVzU2VjdXJpdHlxAH4AE0wAD2NyZWRlbnRpYWxzVHlwZXQATExvcmcva3VhbGkvcmljZS9jb3JlL3NlY3VyaXR5L2NyZWRlbnRpYWxzL0NyZWRlbnRpYWxzU291cmNlJENyZWRlbnRpYWxzVHlwZTtMABBsb2NhbFNlcnZpY2VOYW1lcQB+AARMABdtZXNzYWdlRXhjZXB0aW9uSGFuZGxlcnEAfgAETAAMbWlsbGlzVG9MaXZlcQB+ABVMAAhwcmlvcml0eXEAfgAUTAAFcXVldWVxAH4AE0wADXJldHJ5QXR0ZW1wdHNxAH4AFEwAB3NlcnZpY2V0ABJMamF2YS9sYW5nL09iamVjdDtMAA9zZXJ2aWNlRW5kUG9pbnR0AA5MamF2YS9uZXQvVVJMO0wAE3NlcnZpY2VOYW1lU3BhY2VVUklxAH4ABEwAEHNlcnZpY2VOYW1lc3BhY2VxAH4ABEwAC3NlcnZpY2VQYXRocQB+AAR4cHNxAH4AGQFwdAAaT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2V0AE1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLmV4Y2VwdGlvbmhhbmRsaW5nLkRlZmF1bHRNZXNzYWdlRXhjZXB0aW9uSGFuZGxlcnNxAH4AH///////////c3EAfgAcAAAAA3NxAH4AGQBxAH4AMnBzcgAMamF2YS5uZXQuVVJMliU3Nhr85HIDAAdJAAhoYXNoQ29kZUkABHBvcnRMAAlhdXRob3JpdHlxAH4ABEwABGZpbGVxAH4ABEwABGhvc3RxAH4ABEwACHByb3RvY29scQB+AARMAANyZWZxAH4ABHhwaRuioAAAH5B0AA5sb2NhbGhvc3Q6ODA4MHQAKy9rci1kZXYvcmVtb3RpbmcvT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2V0AAlsb2NhbGhvc3R0AARodHRwcHh0AAB0AAZUUkFWRUx0AAEvc3IAE2phdmEudXRpbC5BcnJheUxpc3R4gdIdmcdhnQMAAUkABHNpemV4cAAAAAV3BAAAAAp0ADNvcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLnNlcnZpY2UuS1NCSmF2YVNlcnZpY2V0ADxjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5ldmVudHMuQ2FjaGVFbnRyeUV2ZW50TGlzdGVuZXJ0ADdjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5ldmVudHMuQ2FjaGVFdmVudExpc3RlbmVydAAXamF2YS51dGlsLkV2ZW50TGlzdGVuZXJ0ACxjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5MaWZlY3ljbGVBd2FyZXhxAH4AI3QABlRSQVZFTA==';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
INSERT INTO KRSB_MSG_PYLD_T (MSG_PYLD,MSG_QUE_ID)
  VALUES (EMPTY_CLOB(),425)
/
-- Length: 6108
-- Chunks: 2
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 425    FOR UPDATE;        
    buffer := 'rO0ABXNyAC1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLkFzeW5jaHJvbm91c0NhbGzxnQ5Y0t0lZQIAB1oAFWlnbm9yZVN0b3JlQW5kRm9yd2FyZFsACWFyZ3VtZW50c3QAE1tMamF2YS9sYW5nL09iamVjdDtMAAhjYWxsYmFja3QAM0xvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL0FzeW5jaHJvbm91c0NhbGxiYWNrO0wAB2NvbnRleHR0ABZMamF2YS9pby9TZXJpYWxpemFibGU7TAAKbWV0aG9kTmFtZXQAEkxqYXZhL2xhbmcvU3RyaW5nO1sACnBhcmFtVHlwZXN0ABJbTGphdmEvbGFuZy9DbGFzcztMAAtzZXJ2aWNlSW5mb3QAKkxvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL1NlcnZpY2VJbmZvO3hwAHVyABNbTGphdmEubGFuZy5PYmplY3Q7kM5YnxBzKWwCAAB4cAAAAAFzcgBDY29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLnBsdWdpbnMuY2x1c3RlcnN1cHBvcnQuQ2x1c3Rlck5vdGlmaWNhdGlvbhY+6VckvG9pAgACSQAEdHlwZUwABGRhdGFxAH4AA3hwAAAAAnQAM0RvY3VtZW50VHlwZVJ1bGVDYWNoZTpQb3N0YWxDb2RlTWFpbnRlbmFuY2VEb2N1bWVudHBwdAAGaW52b2tldXIAEltMamF2YS5sYW5nLkNsYXNzO6sW167LzVqZAgAAeHAAAAABdnIAFGphdmEuaW8uU2VyaWFsaXphYmxlEJthDdebZO0CAAB4cHNyAChvcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLlNlcnZpY2VJbmZvxRcjtikEt7oCAAtMAAVhbGl2ZXQAE0xqYXZhL2xhbmcvQm9vbGVhbjtMABRlbmRwb2ludEFsdGVybmF0ZVVybHEAfgAETAALZW5kcG9pbnRVcmxxAH4ABEwACmxvY2tWZXJOYnJ0ABNMamF2YS9sYW5nL0ludGVnZXI7TAAObWVzc2FnZUVudHJ5SWR0ABBMamF2YS9sYW5nL0xvbmc7TAAFcW5hbWV0ABtMamF2YXgveG1sL25hbWVzcGFjZS9RTmFtZTtMABpzZXJpYWxpemVkU2VydmljZU5hbWVzcGFjZXEAfgAETAAIc2VydmVySXBxAH4ABEwAEXNlcnZpY2VEZWZpbml0aW9udAAwTG9yZy9rdWFsaS9yaWNlL2tzYi9tZXNzYWdpbmcvU2VydmljZURlZmluaXRpb247TAALc2VydmljZU5hbWVxAH4ABEwAEHNlcnZpY2VOYW1lc3BhY2VxAH4ABHhwc3IAEWphdmEubGFuZy5Cb29sZWFuzSBygNWc+u4CAAFaAAV2YWx1ZXhwAXB0AEBodHRwOi8vbG9jYWxob3N0OjgwODAva3ItZGV2L3JlbW90aW5nL09TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNlc3IAEWphdmEubGFuZy5JbnRlZ2VyEuKgpPeBhzgCAAFJAAV2YWx1ZXhyABBqYXZhLmxhbmcuTnVtYmVyhqyVHQuU4IsCAAB4cAAAAAFzcgAOamF2YS5sYW5nLkxvbmc7i+SQzI8j3wIAAUoABXZhbHVleHEAfgAdAAAAAAAAD31zcgAZamF2YXgueG1sLm5hbWVzcGFjZS5RTmFtZYFtqC38O91sAgADTAAJbG9jYWxQYXJ0cQB+AARMAAxuYW1lc3BhY2VVUklxAH4ABEwABnByZWZpeHEAfgAEeHB0ABpPU0NhY2hlTm90aWZpY2F0aW9uU2VydmljZXQAAHEAfgAkdAd0ck8wQUJYTnlBREp2Y21jdWEzVmhiR2t1Y21salpTNXJjMkl1YldWemMyRm5hVzVuTGtwaGRtRlRaWEoyYVdObFJHVm1hVzVwZEdsdmJyYm5EVjlCTkd6eEFnQUJUQUFSYzJWeWRtbGpaVWx1ZEdWeVptRmpaWE4wQUJCTWFtRjJZUzkxZEdsc0wweHBjM1E3ZUhJQUxtOXlaeTVyZFdGc2FTNXlhV05sTG10ellpNXRaWE56WVdkcGJtY3VVMlZ5ZG1salpVUmxabWx1YVhScGIyNEFtd0pQV04wcGZnSUFEVXdBQzJKMWMxTmxZM1Z5YVhSNWRBQVRUR3BoZG1FdmJHRnVaeTlDYjI5c1pXRnVPMHdBRDJOeVpXUmxiblJwWVd4elZIbHdaWFFBVEV4dmNtY3ZhM1ZoYkdrdmNtbGpaUzlqYjNKbEwzTmxZM1Z5YVhSNUwyTnlaV1JsYm5ScFlXeHpMME55WldSbGJuUnBZV3h6VTI5MWNtTmxKRU55WldSbGJuUnBZV3h6Vkhsd1pUdE1BQkJzYjJOaGJGTmxjblpwWTJWT1lXMWxkQUFTVEdwaGRtRXZiR0Z1Wnk5VGRISnBibWM3VEFBWGJXVnpjMkZuWlVWNFkyVndkR2x2YmtoaGJtUnNaWEp4QUg0QUJVd0FERzFwYkd4cGMxUnZUR2wyWlhRQUVFeHFZWFpoTDJ4aGJtY3ZURzl1Wnp0TUFBaHdjbWx2Y21sMGVYUUFFMHhxWVhaaEwyeGhibWN2U1c1MFpXZGxjanRNQUFWeGRXVjFaWEVBZmdBRFRBQU5jbVYwY25sQmRIUmxiWEIwYzNFQWZnQUhUQUFIYzJWeWRtbGpaWFFBRWt4cVlYWmhMMnhoYm1jdlQySnFaV04wTzB3QUQzTmxjblpwWTJWRmJtUlFiMmx1ZEhRQURreHFZWFpoTDI1bGRDOVZVa3c3VEFBVGMyVnlkbWxqWlU1aGJXVlRjR0ZqWlZWU1NYRUFmZ0FGVEFBUWMyVnlkbWxqWlU1aGJXVnpjR0ZqWlhFQWZnQUZUQUFMYzJWeWRtbGpaVkJoZEdoeEFINEFCWGh3YzNJQUVXcGhkbUV1YkdGdVp5NUNiMjlzWldGdXpTQnlnTldjK3U0Q0FBRmFBQVYyWVd4MVpYaHdBWEIwQUJwUFUwTmhZMmhsVG05MGFXWnBZMkYwYVc5dVUyVnlkbWxqWlhRQVRXOXlaeTVyZFdGc2FTNXlhV05sTG10ellpNXRaWE56WVdkcGJtY3VaWGhqWlhCMGFXOXVhR0Z1Wkd4cGJtY3VSR1ZtWVhWc2RFMWxjM05oWjJWRmVHTmxjSFJwYjI1SVlXNWtiR1Z5YzNJQURtcGhkbUV1YkdGdVp5NU1iMjVuTzR2a2tNeVBJOThDQUFGS0FBVjJZV3gxWlhoeUFCQnFZWFpoTG14aGJtY3VUblZ0WW1WeWhxeVZIUXVVNElzQ0FBQjRjUC8vLy8vLy8vLy9jM0lBRVdwaGRtRXViR0Z1Wnk1SmJuUmxaMlZ5RXVLZ3BQZUJoemdDQUFGSkFBVjJZV3gxWlhoeEFINEFFQUFBQUFOemNRQitBQXNBY1FCK0FCTndjM0lBREdwaGRtRXVibVYwTGxWU1RKWWxOellhL09SeUF3QUhTUUFJYUdGemFFTnZaR1ZKQUFSd2IzSjBUQUFKWVhWMGFHOXlhWFI1Y1FCK0FBVk1BQVJtYVd4bGNRQitBQVZNQUFSb2IzTjBjUUIrQUFWTUFBaHdjbTkwYjJOdmJIRUFmZ0FGVEFBRGNtVm1jUUIrQUFWNGNQLy8vLzhBQUIrUWRBQU9iRzlqWVd4b2IzTjBPamd3T0RCMEFDc3ZhM0l0WkdWMkwzSmxiVzkwYVc1bkwwOVRRMkZqYUdWT2IzUnBabWxqWVhScGIyNVRaWEoyYVdObGRBQUpiRzlqWVd4b2IzTjBkQUFFYUhSMGNIQjRkQUFBZEFBR1ZGSkJWa1ZNZEFBQkwzTnlBQk5xWVhaaExuVjBhV3d1UVhKeVlYbE1hWE4wZUlIU0habkhZWjBEQUFGSkFBUnphWHBsZUhBQUFBQUZkd1FBQUFBS2RBQXpi';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 425    FOR UPDATE;        
    buffer := 'M0puTG10MVlXeHBMbkpwWTJVdWEzTmlMbTFsYzNOaFoybHVaeTV6WlhKMmFXTmxMa3RUUWtwaGRtRlRaWEoyYVdObGRBQThZMjl0TG05d1pXNXplVzF3YUc5dWVTNXZjMk5oWTJobExtSmhjMlV1WlhabGJuUnpMa05oWTJobFJXNTBjbmxGZG1WdWRFeHBjM1JsYm1WeWRBQTNZMjl0TG05d1pXNXplVzF3YUc5dWVTNXZjMk5oWTJobExtSmhjMlV1WlhabGJuUnpMa05oWTJobFJYWmxiblJNYVhOMFpXNWxjblFBRjJwaGRtRXVkWFJwYkM1RmRtVnVkRXhwYzNSbGJtVnlkQUFzWTI5dExtOXdaVzV6ZVcxd2FHOXVlUzV2YzJOaFkyaGxMbUpoYzJVdVRHbG1aV041WTJ4bFFYZGhjbVY0dAANMTI5LjE4Ni43OS40NXNyADJvcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLkphdmFTZXJ2aWNlRGVmaW5pdGlvbrbnDV9BNGzxAgABTAARc2VydmljZUludGVyZmFjZXN0ABBMamF2YS91dGlsL0xpc3Q7eHIALm9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuU2VydmljZURlZmluaXRpb24AmwJPWN0pfgIADUwAC2J1c1NlY3VyaXR5cQB+ABNMAA9jcmVkZW50aWFsc1R5cGV0AExMb3JnL2t1YWxpL3JpY2UvY29yZS9zZWN1cml0eS9jcmVkZW50aWFscy9DcmVkZW50aWFsc1NvdXJjZSRDcmVkZW50aWFsc1R5cGU7TAAQbG9jYWxTZXJ2aWNlTmFtZXEAfgAETAAXbWVzc2FnZUV4Y2VwdGlvbkhhbmRsZXJxAH4ABEwADG1pbGxpc1RvTGl2ZXEAfgAVTAAIcHJpb3JpdHlxAH4AFEwABXF1ZXVlcQB+ABNMAA1yZXRyeUF0dGVtcHRzcQB+ABRMAAdzZXJ2aWNldAASTGphdmEvbGFuZy9PYmplY3Q7TAAPc2VydmljZUVuZFBvaW50dAAOTGphdmEvbmV0L1VSTDtMABNzZXJ2aWNlTmFtZVNwYWNlVVJJcQB+AARMABBzZXJ2aWNlTmFtZXNwYWNlcQB+AARMAAtzZXJ2aWNlUGF0aHEAfgAEeHBzcQB+ABkBcHQAGk9TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNldABNb3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5leGNlcHRpb25oYW5kbGluZy5EZWZhdWx0TWVzc2FnZUV4Y2VwdGlvbkhhbmRsZXJzcQB+AB///////////3NxAH4AHAAAAANzcQB+ABkAcQB+ADJwc3IADGphdmEubmV0LlVSTJYlNzYa/ORyAwAHSQAIaGFzaENvZGVJAARwb3J0TAAJYXV0aG9yaXR5cQB+AARMAARmaWxlcQB+AARMAARob3N0cQB+AARMAAhwcm90b2NvbHEAfgAETAADcmVmcQB+AAR4cGkboqAAAB+QdAAObG9jYWxob3N0OjgwODB0ACsva3ItZGV2L3JlbW90aW5nL09TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNldAAJbG9jYWxob3N0dAAEaHR0cHB4dAAAdAAGVFJBVkVMdAABL3NyABNqYXZhLnV0aWwuQXJyYXlMaXN0eIHSHZnHYZ0DAAFJAARzaXpleHAAAAAFdwQAAAAKdAAzb3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5zZXJ2aWNlLktTQkphdmFTZXJ2aWNldAA8Y29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLmJhc2UuZXZlbnRzLkNhY2hlRW50cnlFdmVudExpc3RlbmVydAA3Y29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLmJhc2UuZXZlbnRzLkNhY2hlRXZlbnRMaXN0ZW5lcnQAF2phdmEudXRpbC5FdmVudExpc3RlbmVydAAsY29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLmJhc2UuTGlmZWN5Y2xlQXdhcmV4cQB+ACN0AAZUUkFWRUw=';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
INSERT INTO KRSB_MSG_PYLD_T (MSG_PYLD,MSG_QUE_ID)
  VALUES (EMPTY_CLOB(),426)
/
-- Length: 6100
-- Chunks: 2
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 426    FOR UPDATE;        
    buffer := 'rO0ABXNyAC1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLkFzeW5jaHJvbm91c0NhbGzxnQ5Y0t0lZQIAB1oAFWlnbm9yZVN0b3JlQW5kRm9yd2FyZFsACWFyZ3VtZW50c3QAE1tMamF2YS9sYW5nL09iamVjdDtMAAhjYWxsYmFja3QAM0xvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL0FzeW5jaHJvbm91c0NhbGxiYWNrO0wAB2NvbnRleHR0ABZMamF2YS9pby9TZXJpYWxpemFibGU7TAAKbWV0aG9kTmFtZXQAEkxqYXZhL2xhbmcvU3RyaW5nO1sACnBhcmFtVHlwZXN0ABJbTGphdmEvbGFuZy9DbGFzcztMAAtzZXJ2aWNlSW5mb3QAKkxvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL1NlcnZpY2VJbmZvO3hwAHVyABNbTGphdmEubGFuZy5PYmplY3Q7kM5YnxBzKWwCAAB4cAAAAAFzcgBDY29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLnBsdWdpbnMuY2x1c3RlcnN1cHBvcnQuQ2x1c3Rlck5vdGlmaWNhdGlvbhY+6VckvG9pAgACSQAEdHlwZUwABGRhdGFxAH4AA3hwAAAAAnQALkRvY3VtZW50VHlwZVJ1bGVDYWNoZTpTdGF0ZU1haW50ZW5hbmNlRG9jdW1lbnRwcHQABmludm9rZXVyABJbTGphdmEubGFuZy5DbGFzczurFteuy81amQIAAHhwAAAAAXZyABRqYXZhLmlvLlNlcmlhbGl6YWJsZRCbYQ3Xm2TtAgAAeHBzcgAob3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5TZXJ2aWNlSW5mb8UXI7YpBLe6AgALTAAFYWxpdmV0ABNMamF2YS9sYW5nL0Jvb2xlYW47TAAUZW5kcG9pbnRBbHRlcm5hdGVVcmxxAH4ABEwAC2VuZHBvaW50VXJscQB+AARMAApsb2NrVmVyTmJydAATTGphdmEvbGFuZy9JbnRlZ2VyO0wADm1lc3NhZ2VFbnRyeUlkdAAQTGphdmEvbGFuZy9Mb25nO0wABXFuYW1ldAAbTGphdmF4L3htbC9uYW1lc3BhY2UvUU5hbWU7TAAac2VyaWFsaXplZFNlcnZpY2VOYW1lc3BhY2VxAH4ABEwACHNlcnZlcklwcQB+AARMABFzZXJ2aWNlRGVmaW5pdGlvbnQAMExvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL1NlcnZpY2VEZWZpbml0aW9uO0wAC3NlcnZpY2VOYW1lcQB+AARMABBzZXJ2aWNlTmFtZXNwYWNlcQB+AAR4cHNyABFqYXZhLmxhbmcuQm9vbGVhbs0gcoDVnPruAgABWgAFdmFsdWV4cAFwdABAaHR0cDovL2xvY2FsaG9zdDo4MDgwL2tyLWRldi9yZW1vdGluZy9PU0NhY2hlTm90aWZpY2F0aW9uU2VydmljZXNyABFqYXZhLmxhbmcuSW50ZWdlchLioKT3gYc4AgABSQAFdmFsdWV4cgAQamF2YS5sYW5nLk51bWJlcoaslR0LlOCLAgAAeHAAAAABc3IADmphdmEubGFuZy5Mb25nO4vkkMyPI98CAAFKAAV2YWx1ZXhxAH4AHQAAAAAAAA99c3IAGWphdmF4LnhtbC5uYW1lc3BhY2UuUU5hbWWBbagt/DvdbAIAA0wACWxvY2FsUGFydHEAfgAETAAMbmFtZXNwYWNlVVJJcQB+AARMAAZwcmVmaXhxAH4ABHhwdAAaT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2V0AABxAH4AJHQHdHJPMEFCWE55QURKdmNtY3VhM1ZoYkdrdWNtbGpaUzVyYzJJdWJXVnpjMkZuYVc1bkxrcGhkbUZUWlhKMmFXTmxSR1ZtYVc1cGRHbHZicmJuRFY5Qk5HenhBZ0FCVEFBUmMyVnlkbWxqWlVsdWRHVnlabUZqWlhOMEFCQk1hbUYyWVM5MWRHbHNMMHhwYzNRN2VISUFMbTl5Wnk1cmRXRnNhUzV5YVdObExtdHpZaTV0WlhOellXZHBibWN1VTJWeWRtbGpaVVJsWm1sdWFYUnBiMjRBbXdKUFdOMHBmZ0lBRFV3QUMySjFjMU5sWTNWeWFYUjVkQUFUVEdwaGRtRXZiR0Z1Wnk5Q2IyOXNaV0Z1TzB3QUQyTnlaV1JsYm5ScFlXeHpWSGx3WlhRQVRFeHZjbWN2YTNWaGJHa3ZjbWxqWlM5amIzSmxMM05sWTNWeWFYUjVMMk55WldSbGJuUnBZV3h6TDBOeVpXUmxiblJwWVd4elUyOTFjbU5sSkVOeVpXUmxiblJwWVd4elZIbHdaVHRNQUJCc2IyTmhiRk5sY25acFkyVk9ZVzFsZEFBU1RHcGhkbUV2YkdGdVp5OVRkSEpwYm1jN1RBQVhiV1Z6YzJGblpVVjRZMlZ3ZEdsdmJraGhibVJzWlhKeEFINEFCVXdBREcxcGJHeHBjMVJ2VEdsMlpYUUFFRXhxWVhaaEwyeGhibWN2VEc5dVp6dE1BQWh3Y21sdmNtbDBlWFFBRTB4cVlYWmhMMnhoYm1jdlNXNTBaV2RsY2p0TUFBVnhkV1YxWlhFQWZnQURUQUFOY21WMGNubEJkSFJsYlhCMGMzRUFmZ0FIVEFBSGMyVnlkbWxqWlhRQUVreHFZWFpoTDJ4aGJtY3ZUMkpxWldOME8wd0FEM05sY25acFkyVkZibVJRYjJsdWRIUUFEa3hxWVhaaEwyNWxkQzlWVWt3N1RBQVRjMlZ5ZG1salpVNWhiV1ZUY0dGalpWVlNTWEVBZmdBRlRBQVFjMlZ5ZG1salpVNWhiV1Z6Y0dGalpYRUFmZ0FGVEFBTGMyVnlkbWxqWlZCaGRHaHhBSDRBQlhod2MzSUFFV3BoZG1FdWJHRnVaeTVDYjI5c1pXRnV6U0J5Z05XYyt1NENBQUZhQUFWMllXeDFaWGh3QVhCMEFCcFBVME5oWTJobFRtOTBhV1pwWTJGMGFXOXVVMlZ5ZG1salpYUUFUVzl5Wnk1cmRXRnNhUzV5YVdObExtdHpZaTV0WlhOellXZHBibWN1WlhoalpYQjBhVzl1YUdGdVpHeHBibWN1UkdWbVlYVnNkRTFsYzNOaFoyVkZlR05sY0hScGIyNUlZVzVrYkdWeWMzSUFEbXBoZG1FdWJHRnVaeTVNYjI1bk80dmtrTXlQSTk4Q0FBRktBQVYyWVd4MVpYaHlBQkJxWVhaaExteGhibWN1VG5WdFltVnlocXlWSFF1VTRJc0NBQUI0Y1AvLy8vLy8vLy8vYzNJQUVXcGhkbUV1YkdGdVp5NUpiblJsWjJWeUV1S2dwUGVCaHpnQ0FBRkpBQVYyWVd4MVpYaHhBSDRBRUFBQUFBTnpjUUIrQUFzQWNRQitBQk53YzNJQURHcGhkbUV1Ym1WMExsVlNUSllsTnpZYS9PUnlBd0FIU1FBSWFHRnphRU52WkdWSkFBUndiM0owVEFBSllYVjBhRzl5YVhSNWNRQitBQVZNQUFSbWFXeGxjUUIrQUFWTUFBUm9iM04wY1FCK0FBVk1BQWh3Y205MGIyTnZiSEVBZmdBRlRBQURjbVZtY1FCK0FBVjRjUC8vLy84QUFCK1FkQUFPYkc5allXeG9iM04wT2pnd09EQjBBQ3N2YTNJdFpHVjJMM0psYlc5MGFXNW5MMDlUUTJGamFHVk9iM1JwWm1sallYUnBiMjVUWlhKMmFXTmxkQUFKYkc5allXeG9iM04wZEFBRWFIUjBjSEI0ZEFBQWRBQUdWRkpCVmtWTWRBQUJMM055QUJOcVlYWmhMblYwYVd3dVFYSnlZWGxNYVhOMGVJSFNIWm5IWVowREFBRkpBQVJ6YVhwbGVIQUFBQUFGZHdRQUFBQUtkQUF6YjNKbkxt';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 426    FOR UPDATE;        
    buffer := 'dDFZV3hwTG5KcFkyVXVhM05pTG0xbGMzTmhaMmx1Wnk1elpYSjJhV05sTGt0VFFrcGhkbUZUWlhKMmFXTmxkQUE4WTI5dExtOXdaVzV6ZVcxd2FHOXVlUzV2YzJOaFkyaGxMbUpoYzJVdVpYWmxiblJ6TGtOaFkyaGxSVzUwY25sRmRtVnVkRXhwYzNSbGJtVnlkQUEzWTI5dExtOXdaVzV6ZVcxd2FHOXVlUzV2YzJOaFkyaGxMbUpoYzJVdVpYWmxiblJ6TGtOaFkyaGxSWFpsYm5STWFYTjBaVzVsY25RQUYycGhkbUV1ZFhScGJDNUZkbVZ1ZEV4cGMzUmxibVZ5ZEFBc1kyOXRMbTl3Wlc1emVXMXdhRzl1ZVM1dmMyTmhZMmhsTG1KaGMyVXVUR2xtWldONVkyeGxRWGRoY21WNHQADTEyOS4xODYuNzkuNDVzcgAyb3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5KYXZhU2VydmljZURlZmluaXRpb2625w1fQTRs8QIAAUwAEXNlcnZpY2VJbnRlcmZhY2VzdAAQTGphdmEvdXRpbC9MaXN0O3hyAC5vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLlNlcnZpY2VEZWZpbml0aW9uAJsCT1jdKX4CAA1MAAtidXNTZWN1cml0eXEAfgATTAAPY3JlZGVudGlhbHNUeXBldABMTG9yZy9rdWFsaS9yaWNlL2NvcmUvc2VjdXJpdHkvY3JlZGVudGlhbHMvQ3JlZGVudGlhbHNTb3VyY2UkQ3JlZGVudGlhbHNUeXBlO0wAEGxvY2FsU2VydmljZU5hbWVxAH4ABEwAF21lc3NhZ2VFeGNlcHRpb25IYW5kbGVycQB+AARMAAxtaWxsaXNUb0xpdmVxAH4AFUwACHByaW9yaXR5cQB+ABRMAAVxdWV1ZXEAfgATTAANcmV0cnlBdHRlbXB0c3EAfgAUTAAHc2VydmljZXQAEkxqYXZhL2xhbmcvT2JqZWN0O0wAD3NlcnZpY2VFbmRQb2ludHQADkxqYXZhL25ldC9VUkw7TAATc2VydmljZU5hbWVTcGFjZVVSSXEAfgAETAAQc2VydmljZU5hbWVzcGFjZXEAfgAETAALc2VydmljZVBhdGhxAH4ABHhwc3EAfgAZAXB0ABpPU0NhY2hlTm90aWZpY2F0aW9uU2VydmljZXQATW9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuZXhjZXB0aW9uaGFuZGxpbmcuRGVmYXVsdE1lc3NhZ2VFeGNlcHRpb25IYW5kbGVyc3EAfgAf//////////9zcQB+ABwAAAADc3EAfgAZAHEAfgAycHNyAAxqYXZhLm5ldC5VUkyWJTc2GvzkcgMAB0kACGhhc2hDb2RlSQAEcG9ydEwACWF1dGhvcml0eXEAfgAETAAEZmlsZXEAfgAETAAEaG9zdHEAfgAETAAIcHJvdG9jb2xxAH4ABEwAA3JlZnEAfgAEeHBpG6KgAAAfkHQADmxvY2FsaG9zdDo4MDgwdAArL2tyLWRldi9yZW1vdGluZy9PU0NhY2hlTm90aWZpY2F0aW9uU2VydmljZXQACWxvY2FsaG9zdHQABGh0dHBweHQAAHQABlRSQVZFTHQAAS9zcgATamF2YS51dGlsLkFycmF5TGlzdHiB0h2Zx2GdAwABSQAEc2l6ZXhwAAAABXcEAAAACnQAM29yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuc2VydmljZS5LU0JKYXZhU2VydmljZXQAPGNvbS5vcGVuc3ltcGhvbnkub3NjYWNoZS5iYXNlLmV2ZW50cy5DYWNoZUVudHJ5RXZlbnRMaXN0ZW5lcnQAN2NvbS5vcGVuc3ltcGhvbnkub3NjYWNoZS5iYXNlLmV2ZW50cy5DYWNoZUV2ZW50TGlzdGVuZXJ0ABdqYXZhLnV0aWwuRXZlbnRMaXN0ZW5lcnQALGNvbS5vcGVuc3ltcGhvbnkub3NjYWNoZS5iYXNlLkxpZmVjeWNsZUF3YXJleHEAfgAjdAAGVFJBVkVM';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
INSERT INTO KRSB_MSG_PYLD_T (MSG_PYLD,MSG_QUE_ID)
  VALUES (EMPTY_CLOB(),427)
/
-- Length: 6104
-- Chunks: 2
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 427    FOR UPDATE;        
    buffer := 'rO0ABXNyAC1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLkFzeW5jaHJvbm91c0NhbGzxnQ5Y0t0lZQIAB1oAFWlnbm9yZVN0b3JlQW5kRm9yd2FyZFsACWFyZ3VtZW50c3QAE1tMamF2YS9sYW5nL09iamVjdDtMAAhjYWxsYmFja3QAM0xvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL0FzeW5jaHJvbm91c0NhbGxiYWNrO0wAB2NvbnRleHR0ABZMamF2YS9pby9TZXJpYWxpemFibGU7TAAKbWV0aG9kTmFtZXQAEkxqYXZhL2xhbmcvU3RyaW5nO1sACnBhcmFtVHlwZXN0ABJbTGphdmEvbGFuZy9DbGFzcztMAAtzZXJ2aWNlSW5mb3QAKkxvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL1NlcnZpY2VJbmZvO3hwAHVyABNbTGphdmEubGFuZy5PYmplY3Q7kM5YnxBzKWwCAAB4cAAAAAFzcgBDY29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLnBsdWdpbnMuY2x1c3RlcnN1cHBvcnQuQ2x1c3Rlck5vdGlmaWNhdGlvbhY+6VckvG9pAgACSQAEdHlwZUwABGRhdGFxAH4AA3hwAAAAAnQAMERvY3VtZW50VHlwZVJ1bGVDYWNoZTpJZGVudGl0eU1hbmFnZW1lbnREb2N1bWVudHBwdAAGaW52b2tldXIAEltMamF2YS5sYW5nLkNsYXNzO6sW167LzVqZAgAAeHAAAAABdnIAFGphdmEuaW8uU2VyaWFsaXphYmxlEJthDdebZO0CAAB4cHNyAChvcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLlNlcnZpY2VJbmZvxRcjtikEt7oCAAtMAAVhbGl2ZXQAE0xqYXZhL2xhbmcvQm9vbGVhbjtMABRlbmRwb2ludEFsdGVybmF0ZVVybHEAfgAETAALZW5kcG9pbnRVcmxxAH4ABEwACmxvY2tWZXJOYnJ0ABNMamF2YS9sYW5nL0ludGVnZXI7TAAObWVzc2FnZUVudHJ5SWR0ABBMamF2YS9sYW5nL0xvbmc7TAAFcW5hbWV0ABtMamF2YXgveG1sL25hbWVzcGFjZS9RTmFtZTtMABpzZXJpYWxpemVkU2VydmljZU5hbWVzcGFjZXEAfgAETAAIc2VydmVySXBxAH4ABEwAEXNlcnZpY2VEZWZpbml0aW9udAAwTG9yZy9rdWFsaS9yaWNlL2tzYi9tZXNzYWdpbmcvU2VydmljZURlZmluaXRpb247TAALc2VydmljZU5hbWVxAH4ABEwAEHNlcnZpY2VOYW1lc3BhY2VxAH4ABHhwc3IAEWphdmEubGFuZy5Cb29sZWFuzSBygNWc+u4CAAFaAAV2YWx1ZXhwAXB0AEBodHRwOi8vbG9jYWxob3N0OjgwODAva3ItZGV2L3JlbW90aW5nL09TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNlc3IAEWphdmEubGFuZy5JbnRlZ2VyEuKgpPeBhzgCAAFJAAV2YWx1ZXhyABBqYXZhLmxhbmcuTnVtYmVyhqyVHQuU4IsCAAB4cAAAAAFzcgAOamF2YS5sYW5nLkxvbmc7i+SQzI8j3wIAAUoABXZhbHVleHEAfgAdAAAAAAAAD31zcgAZamF2YXgueG1sLm5hbWVzcGFjZS5RTmFtZYFtqC38O91sAgADTAAJbG9jYWxQYXJ0cQB+AARMAAxuYW1lc3BhY2VVUklxAH4ABEwABnByZWZpeHEAfgAEeHB0ABpPU0NhY2hlTm90aWZpY2F0aW9uU2VydmljZXQAAHEAfgAkdAd0ck8wQUJYTnlBREp2Y21jdWEzVmhiR2t1Y21salpTNXJjMkl1YldWemMyRm5hVzVuTGtwaGRtRlRaWEoyYVdObFJHVm1hVzVwZEdsdmJyYm5EVjlCTkd6eEFnQUJUQUFSYzJWeWRtbGpaVWx1ZEdWeVptRmpaWE4wQUJCTWFtRjJZUzkxZEdsc0wweHBjM1E3ZUhJQUxtOXlaeTVyZFdGc2FTNXlhV05sTG10ellpNXRaWE56WVdkcGJtY3VVMlZ5ZG1salpVUmxabWx1YVhScGIyNEFtd0pQV04wcGZnSUFEVXdBQzJKMWMxTmxZM1Z5YVhSNWRBQVRUR3BoZG1FdmJHRnVaeTlDYjI5c1pXRnVPMHdBRDJOeVpXUmxiblJwWVd4elZIbHdaWFFBVEV4dmNtY3ZhM1ZoYkdrdmNtbGpaUzlqYjNKbEwzTmxZM1Z5YVhSNUwyTnlaV1JsYm5ScFlXeHpMME55WldSbGJuUnBZV3h6VTI5MWNtTmxKRU55WldSbGJuUnBZV3h6Vkhsd1pUdE1BQkJzYjJOaGJGTmxjblpwWTJWT1lXMWxkQUFTVEdwaGRtRXZiR0Z1Wnk5VGRISnBibWM3VEFBWGJXVnpjMkZuWlVWNFkyVndkR2x2YmtoaGJtUnNaWEp4QUg0QUJVd0FERzFwYkd4cGMxUnZUR2wyWlhRQUVFeHFZWFpoTDJ4aGJtY3ZURzl1Wnp0TUFBaHdjbWx2Y21sMGVYUUFFMHhxWVhaaEwyeGhibWN2U1c1MFpXZGxjanRNQUFWeGRXVjFaWEVBZmdBRFRBQU5jbVYwY25sQmRIUmxiWEIwYzNFQWZnQUhUQUFIYzJWeWRtbGpaWFFBRWt4cVlYWmhMMnhoYm1jdlQySnFaV04wTzB3QUQzTmxjblpwWTJWRmJtUlFiMmx1ZEhRQURreHFZWFpoTDI1bGRDOVZVa3c3VEFBVGMyVnlkbWxqWlU1aGJXVlRjR0ZqWlZWU1NYRUFmZ0FGVEFBUWMyVnlkbWxqWlU1aGJXVnpjR0ZqWlhFQWZnQUZUQUFMYzJWeWRtbGpaVkJoZEdoeEFINEFCWGh3YzNJQUVXcGhkbUV1YkdGdVp5NUNiMjlzWldGdXpTQnlnTldjK3U0Q0FBRmFBQVYyWVd4MVpYaHdBWEIwQUJwUFUwTmhZMmhsVG05MGFXWnBZMkYwYVc5dVUyVnlkbWxqWlhRQVRXOXlaeTVyZFdGc2FTNXlhV05sTG10ellpNXRaWE56WVdkcGJtY3VaWGhqWlhCMGFXOXVhR0Z1Wkd4cGJtY3VSR1ZtWVhWc2RFMWxjM05oWjJWRmVHTmxjSFJwYjI1SVlXNWtiR1Z5YzNJQURtcGhkbUV1YkdGdVp5NU1iMjVuTzR2a2tNeVBJOThDQUFGS0FBVjJZV3gxWlhoeUFCQnFZWFpoTG14aGJtY3VUblZ0WW1WeWhxeVZIUXVVNElzQ0FBQjRjUC8vLy8vLy8vLy9jM0lBRVdwaGRtRXViR0Z1Wnk1SmJuUmxaMlZ5RXVLZ3BQZUJoemdDQUFGSkFBVjJZV3gxWlhoeEFINEFFQUFBQUFOemNRQitBQXNBY1FCK0FCTndjM0lBREdwaGRtRXVibVYwTGxWU1RKWWxOellhL09SeUF3QUhTUUFJYUdGemFFTnZaR1ZKQUFSd2IzSjBUQUFKWVhWMGFHOXlhWFI1Y1FCK0FBVk1BQVJtYVd4bGNRQitBQVZNQUFSb2IzTjBjUUIrQUFWTUFBaHdjbTkwYjJOdmJIRUFmZ0FGVEFBRGNtVm1jUUIrQUFWNGNQLy8vLzhBQUIrUWRBQU9iRzlqWVd4b2IzTjBPamd3T0RCMEFDc3ZhM0l0WkdWMkwzSmxiVzkwYVc1bkwwOVRRMkZqYUdWT2IzUnBabWxqWVhScGIyNVRaWEoyYVdObGRBQUpiRzlqWVd4b2IzTjBkQUFFYUhSMGNIQjRkQUFBZEFBR1ZGSkJWa1ZNZEFBQkwzTnlBQk5xWVhaaExuVjBhV3d1UVhKeVlYbE1hWE4wZUlIU0habkhZWjBEQUFGSkFBUnphWHBsZUhBQUFBQUZkd1FBQUFBS2RBQXpiM0pu';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 427    FOR UPDATE;        
    buffer := 'TG10MVlXeHBMbkpwWTJVdWEzTmlMbTFsYzNOaFoybHVaeTV6WlhKMmFXTmxMa3RUUWtwaGRtRlRaWEoyYVdObGRBQThZMjl0TG05d1pXNXplVzF3YUc5dWVTNXZjMk5oWTJobExtSmhjMlV1WlhabGJuUnpMa05oWTJobFJXNTBjbmxGZG1WdWRFeHBjM1JsYm1WeWRBQTNZMjl0TG05d1pXNXplVzF3YUc5dWVTNXZjMk5oWTJobExtSmhjMlV1WlhabGJuUnpMa05oWTJobFJYWmxiblJNYVhOMFpXNWxjblFBRjJwaGRtRXVkWFJwYkM1RmRtVnVkRXhwYzNSbGJtVnlkQUFzWTI5dExtOXdaVzV6ZVcxd2FHOXVlUzV2YzJOaFkyaGxMbUpoYzJVdVRHbG1aV041WTJ4bFFYZGhjbVY0dAANMTI5LjE4Ni43OS40NXNyADJvcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLkphdmFTZXJ2aWNlRGVmaW5pdGlvbrbnDV9BNGzxAgABTAARc2VydmljZUludGVyZmFjZXN0ABBMamF2YS91dGlsL0xpc3Q7eHIALm9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuU2VydmljZURlZmluaXRpb24AmwJPWN0pfgIADUwAC2J1c1NlY3VyaXR5cQB+ABNMAA9jcmVkZW50aWFsc1R5cGV0AExMb3JnL2t1YWxpL3JpY2UvY29yZS9zZWN1cml0eS9jcmVkZW50aWFscy9DcmVkZW50aWFsc1NvdXJjZSRDcmVkZW50aWFsc1R5cGU7TAAQbG9jYWxTZXJ2aWNlTmFtZXEAfgAETAAXbWVzc2FnZUV4Y2VwdGlvbkhhbmRsZXJxAH4ABEwADG1pbGxpc1RvTGl2ZXEAfgAVTAAIcHJpb3JpdHlxAH4AFEwABXF1ZXVlcQB+ABNMAA1yZXRyeUF0dGVtcHRzcQB+ABRMAAdzZXJ2aWNldAASTGphdmEvbGFuZy9PYmplY3Q7TAAPc2VydmljZUVuZFBvaW50dAAOTGphdmEvbmV0L1VSTDtMABNzZXJ2aWNlTmFtZVNwYWNlVVJJcQB+AARMABBzZXJ2aWNlTmFtZXNwYWNlcQB+AARMAAtzZXJ2aWNlUGF0aHEAfgAEeHBzcQB+ABkBcHQAGk9TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNldABNb3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5leGNlcHRpb25oYW5kbGluZy5EZWZhdWx0TWVzc2FnZUV4Y2VwdGlvbkhhbmRsZXJzcQB+AB///////////3NxAH4AHAAAAANzcQB+ABkAcQB+ADJwc3IADGphdmEubmV0LlVSTJYlNzYa/ORyAwAHSQAIaGFzaENvZGVJAARwb3J0TAAJYXV0aG9yaXR5cQB+AARMAARmaWxlcQB+AARMAARob3N0cQB+AARMAAhwcm90b2NvbHEAfgAETAADcmVmcQB+AAR4cGkboqAAAB+QdAAObG9jYWxob3N0OjgwODB0ACsva3ItZGV2L3JlbW90aW5nL09TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNldAAJbG9jYWxob3N0dAAEaHR0cHB4dAAAdAAGVFJBVkVMdAABL3NyABNqYXZhLnV0aWwuQXJyYXlMaXN0eIHSHZnHYZ0DAAFJAARzaXpleHAAAAAFdwQAAAAKdAAzb3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5zZXJ2aWNlLktTQkphdmFTZXJ2aWNldAA8Y29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLmJhc2UuZXZlbnRzLkNhY2hlRW50cnlFdmVudExpc3RlbmVydAA3Y29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLmJhc2UuZXZlbnRzLkNhY2hlRXZlbnRMaXN0ZW5lcnQAF2phdmEudXRpbC5FdmVudExpc3RlbmVydAAsY29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLmJhc2UuTGlmZWN5Y2xlQXdhcmV4cQB+ACN0AAZUUkFWRUw=';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
INSERT INTO KRSB_MSG_PYLD_T (MSG_PYLD,MSG_QUE_ID)
  VALUES (EMPTY_CLOB(),428)
/
-- Length: 6144
-- Chunks: 2
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 428    FOR UPDATE;        
    buffer := 'rO0ABXNyAC1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLkFzeW5jaHJvbm91c0NhbGzxnQ5Y0t0lZQIAB1oAFWlnbm9yZVN0b3JlQW5kRm9yd2FyZFsACWFyZ3VtZW50c3QAE1tMamF2YS9sYW5nL09iamVjdDtMAAhjYWxsYmFja3QAM0xvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL0FzeW5jaHJvbm91c0NhbGxiYWNrO0wAB2NvbnRleHR0ABZMamF2YS9pby9TZXJpYWxpemFibGU7TAAKbWV0aG9kTmFtZXQAEkxqYXZhL2xhbmcvU3RyaW5nO1sACnBhcmFtVHlwZXN0ABJbTGphdmEvbGFuZy9DbGFzcztMAAtzZXJ2aWNlSW5mb3QAKkxvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL1NlcnZpY2VJbmZvO3hwAHVyABNbTGphdmEubGFuZy5PYmplY3Q7kM5YnxBzKWwCAAB4cAAAAAFzcgBDY29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLnBsdWdpbnMuY2x1c3RlcnN1cHBvcnQuQ2x1c3Rlck5vdGlmaWNhdGlvbhY+6VckvG9pAgACSQAEdHlwZUwABGRhdGFxAH4AA3hwAAAAAnQAT0RvY3VtZW50VHlwZVJ1bGVDYWNoZTpJZGVudGl0eU1hbmFnZW1lbnRSZXZpZXdSZXNwb25zaWJpbGl0eU1haW50ZW5hbmNlRG9jdW1lbnRwcHQABmludm9rZXVyABJbTGphdmEubGFuZy5DbGFzczurFteuy81amQIAAHhwAAAAAXZyABRqYXZhLmlvLlNlcmlhbGl6YWJsZRCbYQ3Xm2TtAgAAeHBzcgAob3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5TZXJ2aWNlSW5mb8UXI7YpBLe6AgALTAAFYWxpdmV0ABNMamF2YS9sYW5nL0Jvb2xlYW47TAAUZW5kcG9pbnRBbHRlcm5hdGVVcmxxAH4ABEwAC2VuZHBvaW50VXJscQB+AARMAApsb2NrVmVyTmJydAATTGphdmEvbGFuZy9JbnRlZ2VyO0wADm1lc3NhZ2VFbnRyeUlkdAAQTGphdmEvbGFuZy9Mb25nO0wABXFuYW1ldAAbTGphdmF4L3htbC9uYW1lc3BhY2UvUU5hbWU7TAAac2VyaWFsaXplZFNlcnZpY2VOYW1lc3BhY2VxAH4ABEwACHNlcnZlcklwcQB+AARMABFzZXJ2aWNlRGVmaW5pdGlvbnQAMExvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL1NlcnZpY2VEZWZpbml0aW9uO0wAC3NlcnZpY2VOYW1lcQB+AARMABBzZXJ2aWNlTmFtZXNwYWNlcQB+AAR4cHNyABFqYXZhLmxhbmcuQm9vbGVhbs0gcoDVnPruAgABWgAFdmFsdWV4cAFwdABAaHR0cDovL2xvY2FsaG9zdDo4MDgwL2tyLWRldi9yZW1vdGluZy9PU0NhY2hlTm90aWZpY2F0aW9uU2VydmljZXNyABFqYXZhLmxhbmcuSW50ZWdlchLioKT3gYc4AgABSQAFdmFsdWV4cgAQamF2YS5sYW5nLk51bWJlcoaslR0LlOCLAgAAeHAAAAABc3IADmphdmEubGFuZy5Mb25nO4vkkMyPI98CAAFKAAV2YWx1ZXhxAH4AHQAAAAAAAA99c3IAGWphdmF4LnhtbC5uYW1lc3BhY2UuUU5hbWWBbagt/DvdbAIAA0wACWxvY2FsUGFydHEAfgAETAAMbmFtZXNwYWNlVVJJcQB+AARMAAZwcmVmaXhxAH4ABHhwdAAaT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2V0AABxAH4AJHQHdHJPMEFCWE55QURKdmNtY3VhM1ZoYkdrdWNtbGpaUzVyYzJJdWJXVnpjMkZuYVc1bkxrcGhkbUZUWlhKMmFXTmxSR1ZtYVc1cGRHbHZicmJuRFY5Qk5HenhBZ0FCVEFBUmMyVnlkbWxqWlVsdWRHVnlabUZqWlhOMEFCQk1hbUYyWVM5MWRHbHNMMHhwYzNRN2VISUFMbTl5Wnk1cmRXRnNhUzV5YVdObExtdHpZaTV0WlhOellXZHBibWN1VTJWeWRtbGpaVVJsWm1sdWFYUnBiMjRBbXdKUFdOMHBmZ0lBRFV3QUMySjFjMU5sWTNWeWFYUjVkQUFUVEdwaGRtRXZiR0Z1Wnk5Q2IyOXNaV0Z1TzB3QUQyTnlaV1JsYm5ScFlXeHpWSGx3WlhRQVRFeHZjbWN2YTNWaGJHa3ZjbWxqWlM5amIzSmxMM05sWTNWeWFYUjVMMk55WldSbGJuUnBZV3h6TDBOeVpXUmxiblJwWVd4elUyOTFjbU5sSkVOeVpXUmxiblJwWVd4elZIbHdaVHRNQUJCc2IyTmhiRk5sY25acFkyVk9ZVzFsZEFBU1RHcGhkbUV2YkdGdVp5OVRkSEpwYm1jN1RBQVhiV1Z6YzJGblpVVjRZMlZ3ZEdsdmJraGhibVJzWlhKeEFINEFCVXdBREcxcGJHeHBjMVJ2VEdsMlpYUUFFRXhxWVhaaEwyeGhibWN2VEc5dVp6dE1BQWh3Y21sdmNtbDBlWFFBRTB4cVlYWmhMMnhoYm1jdlNXNTBaV2RsY2p0TUFBVnhkV1YxWlhFQWZnQURUQUFOY21WMGNubEJkSFJsYlhCMGMzRUFmZ0FIVEFBSGMyVnlkbWxqWlhRQUVreHFZWFpoTDJ4aGJtY3ZUMkpxWldOME8wd0FEM05sY25acFkyVkZibVJRYjJsdWRIUUFEa3hxWVhaaEwyNWxkQzlWVWt3N1RBQVRjMlZ5ZG1salpVNWhiV1ZUY0dGalpWVlNTWEVBZmdBRlRBQVFjMlZ5ZG1salpVNWhiV1Z6Y0dGalpYRUFmZ0FGVEFBTGMyVnlkbWxqWlZCaGRHaHhBSDRBQlhod2MzSUFFV3BoZG1FdWJHRnVaeTVDYjI5c1pXRnV6U0J5Z05XYyt1NENBQUZhQUFWMllXeDFaWGh3QVhCMEFCcFBVME5oWTJobFRtOTBhV1pwWTJGMGFXOXVVMlZ5ZG1salpYUUFUVzl5Wnk1cmRXRnNhUzV5YVdObExtdHpZaTV0WlhOellXZHBibWN1WlhoalpYQjBhVzl1YUdGdVpHeHBibWN1UkdWbVlYVnNkRTFsYzNOaFoyVkZlR05sY0hScGIyNUlZVzVrYkdWeWMzSUFEbXBoZG1FdWJHRnVaeTVNYjI1bk80dmtrTXlQSTk4Q0FBRktBQVYyWVd4MVpYaHlBQkJxWVhaaExteGhibWN1VG5WdFltVnlocXlWSFF1VTRJc0NBQUI0Y1AvLy8vLy8vLy8vYzNJQUVXcGhkbUV1YkdGdVp5NUpiblJsWjJWeUV1S2dwUGVCaHpnQ0FBRkpBQVYyWVd4MVpYaHhBSDRBRUFBQUFBTnpjUUIrQUFzQWNRQitBQk53YzNJQURHcGhkbUV1Ym1WMExsVlNUSllsTnpZYS9PUnlBd0FIU1FBSWFHRnphRU52WkdWSkFBUndiM0owVEFBSllYVjBhRzl5YVhSNWNRQitBQVZNQUFSbWFXeGxjUUIrQUFWTUFBUm9iM04wY1FCK0FBVk1BQWh3Y205MGIyTnZiSEVBZmdBRlRBQURjbVZtY1FCK0FBVjRjUC8vLy84QUFCK1FkQUFPYkc5allXeG9iM04wT2pnd09EQjBBQ3N2YTNJdFpHVjJMM0psYlc5MGFXNW5MMDlUUTJGamFHVk9iM1JwWm1sallYUnBiMjVUWlhKMmFXTmxkQUFKYkc5allXeG9iM04wZEFBRWFIUjBjSEI0ZEFBQWRBQUdWRkpCVmtWTWRBQUJMM055QUJOcVlYWmhMblYwYVd3dVFYSnlZWGxNYVhOMGVJSFNIWm5IWVowREFBRkpB';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 428    FOR UPDATE;        
    buffer := 'QVJ6YVhwbGVIQUFBQUFGZHdRQUFBQUtkQUF6YjNKbkxtdDFZV3hwTG5KcFkyVXVhM05pTG0xbGMzTmhaMmx1Wnk1elpYSjJhV05sTGt0VFFrcGhkbUZUWlhKMmFXTmxkQUE4WTI5dExtOXdaVzV6ZVcxd2FHOXVlUzV2YzJOaFkyaGxMbUpoYzJVdVpYWmxiblJ6TGtOaFkyaGxSVzUwY25sRmRtVnVkRXhwYzNSbGJtVnlkQUEzWTI5dExtOXdaVzV6ZVcxd2FHOXVlUzV2YzJOaFkyaGxMbUpoYzJVdVpYWmxiblJ6TGtOaFkyaGxSWFpsYm5STWFYTjBaVzVsY25RQUYycGhkbUV1ZFhScGJDNUZkbVZ1ZEV4cGMzUmxibVZ5ZEFBc1kyOXRMbTl3Wlc1emVXMXdhRzl1ZVM1dmMyTmhZMmhsTG1KaGMyVXVUR2xtWldONVkyeGxRWGRoY21WNHQADTEyOS4xODYuNzkuNDVzcgAyb3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5KYXZhU2VydmljZURlZmluaXRpb2625w1fQTRs8QIAAUwAEXNlcnZpY2VJbnRlcmZhY2VzdAAQTGphdmEvdXRpbC9MaXN0O3hyAC5vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLlNlcnZpY2VEZWZpbml0aW9uAJsCT1jdKX4CAA1MAAtidXNTZWN1cml0eXEAfgATTAAPY3JlZGVudGlhbHNUeXBldABMTG9yZy9rdWFsaS9yaWNlL2NvcmUvc2VjdXJpdHkvY3JlZGVudGlhbHMvQ3JlZGVudGlhbHNTb3VyY2UkQ3JlZGVudGlhbHNUeXBlO0wAEGxvY2FsU2VydmljZU5hbWVxAH4ABEwAF21lc3NhZ2VFeGNlcHRpb25IYW5kbGVycQB+AARMAAxtaWxsaXNUb0xpdmVxAH4AFUwACHByaW9yaXR5cQB+ABRMAAVxdWV1ZXEAfgATTAANcmV0cnlBdHRlbXB0c3EAfgAUTAAHc2VydmljZXQAEkxqYXZhL2xhbmcvT2JqZWN0O0wAD3NlcnZpY2VFbmRQb2ludHQADkxqYXZhL25ldC9VUkw7TAATc2VydmljZU5hbWVTcGFjZVVSSXEAfgAETAAQc2VydmljZU5hbWVzcGFjZXEAfgAETAALc2VydmljZVBhdGhxAH4ABHhwc3EAfgAZAXB0ABpPU0NhY2hlTm90aWZpY2F0aW9uU2VydmljZXQATW9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuZXhjZXB0aW9uaGFuZGxpbmcuRGVmYXVsdE1lc3NhZ2VFeGNlcHRpb25IYW5kbGVyc3EAfgAf//////////9zcQB+ABwAAAADc3EAfgAZAHEAfgAycHNyAAxqYXZhLm5ldC5VUkyWJTc2GvzkcgMAB0kACGhhc2hDb2RlSQAEcG9ydEwACWF1dGhvcml0eXEAfgAETAAEZmlsZXEAfgAETAAEaG9zdHEAfgAETAAIcHJvdG9jb2xxAH4ABEwAA3JlZnEAfgAEeHBpG6KgAAAfkHQADmxvY2FsaG9zdDo4MDgwdAArL2tyLWRldi9yZW1vdGluZy9PU0NhY2hlTm90aWZpY2F0aW9uU2VydmljZXQACWxvY2FsaG9zdHQABGh0dHBweHQAAHQABlRSQVZFTHQAAS9zcgATamF2YS51dGlsLkFycmF5TGlzdHiB0h2Zx2GdAwABSQAEc2l6ZXhwAAAABXcEAAAACnQAM29yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuc2VydmljZS5LU0JKYXZhU2VydmljZXQAPGNvbS5vcGVuc3ltcGhvbnkub3NjYWNoZS5iYXNlLmV2ZW50cy5DYWNoZUVudHJ5RXZlbnRMaXN0ZW5lcnQAN2NvbS5vcGVuc3ltcGhvbnkub3NjYWNoZS5iYXNlLmV2ZW50cy5DYWNoZUV2ZW50TGlzdGVuZXJ0ABdqYXZhLnV0aWwuRXZlbnRMaXN0ZW5lcnQALGNvbS5vcGVuc3ltcGhvbnkub3NjYWNoZS5iYXNlLkxpZmVjeWNsZUF3YXJleHEAfgAjdAAGVFJBVkVM';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
INSERT INTO KRSB_MSG_PYLD_T (MSG_PYLD,MSG_QUE_ID)
  VALUES (EMPTY_CLOB(),429)
/
-- Length: 6060
-- Chunks: 2
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 429    FOR UPDATE;        
    buffer := 'rO0ABXNyAC1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLkFzeW5jaHJvbm91c0NhbGzxnQ5Y0t0lZQIAB1oAFWlnbm9yZVN0b3JlQW5kRm9yd2FyZFsACWFyZ3VtZW50c3QAE1tMamF2YS9sYW5nL09iamVjdDtMAAhjYWxsYmFja3QAM0xvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL0FzeW5jaHJvbm91c0NhbGxiYWNrO0wAB2NvbnRleHR0ABZMamF2YS9pby9TZXJpYWxpemFibGU7TAAKbWV0aG9kTmFtZXQAEkxqYXZhL2xhbmcvU3RyaW5nO1sACnBhcmFtVHlwZXN0ABJbTGphdmEvbGFuZy9DbGFzcztMAAtzZXJ2aWNlSW5mb3QAKkxvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL1NlcnZpY2VJbmZvO3hwAHVyABNbTGphdmEubGFuZy5PYmplY3Q7kM5YnxBzKWwCAAB4cAAAAAFzcgBDY29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLnBsdWdpbnMuY2x1c3RlcnN1cHBvcnQuQ2x1c3Rlck5vdGlmaWNhdGlvbhY+6VckvG9pAgACSQAEdHlwZUwABGRhdGFxAH4AA3hwAAAAAnQADkRvY3VtZW50VHlwZUlkcHB0AAZpbnZva2V1cgASW0xqYXZhLmxhbmcuQ2xhc3M7qxbXrsvNWpkCAAB4cAAAAAF2cgAUamF2YS5pby5TZXJpYWxpemFibGUQm2EN15tk7QIAAHhwc3IAKG9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuU2VydmljZUluZm/FFyO2KQS3ugIAC0wABWFsaXZldAATTGphdmEvbGFuZy9Cb29sZWFuO0wAFGVuZHBvaW50QWx0ZXJuYXRlVXJscQB+AARMAAtlbmRwb2ludFVybHEAfgAETAAKbG9ja1Zlck5icnQAE0xqYXZhL2xhbmcvSW50ZWdlcjtMAA5tZXNzYWdlRW50cnlJZHQAEExqYXZhL2xhbmcvTG9uZztMAAVxbmFtZXQAG0xqYXZheC94bWwvbmFtZXNwYWNlL1FOYW1lO0wAGnNlcmlhbGl6ZWRTZXJ2aWNlTmFtZXNwYWNlcQB+AARMAAhzZXJ2ZXJJcHEAfgAETAARc2VydmljZURlZmluaXRpb250ADBMb3JnL2t1YWxpL3JpY2Uva3NiL21lc3NhZ2luZy9TZXJ2aWNlRGVmaW5pdGlvbjtMAAtzZXJ2aWNlTmFtZXEAfgAETAAQc2VydmljZU5hbWVzcGFjZXEAfgAEeHBzcgARamF2YS5sYW5nLkJvb2xlYW7NIHKA1Zz67gIAAVoABXZhbHVleHABcHQAQGh0dHA6Ly9sb2NhbGhvc3Q6ODA4MC9rci1kZXYvcmVtb3RpbmcvT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2VzcgARamF2YS5sYW5nLkludGVnZXIS4qCk94GHOAIAAUkABXZhbHVleHIAEGphdmEubGFuZy5OdW1iZXKGrJUdC5TgiwIAAHhwAAAAAXNyAA5qYXZhLmxhbmcuTG9uZzuL5JDMjyPfAgABSgAFdmFsdWV4cQB+AB0AAAAAAAAPfXNyABlqYXZheC54bWwubmFtZXNwYWNlLlFOYW1lgW2oLfw73WwCAANMAAlsb2NhbFBhcnRxAH4ABEwADG5hbWVzcGFjZVVSSXEAfgAETAAGcHJlZml4cQB+AAR4cHQAGk9TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNldAAAcQB+ACR0B3RyTzBBQlhOeUFESnZjbWN1YTNWaGJHa3VjbWxqWlM1cmMySXViV1Z6YzJGbmFXNW5Ma3BoZG1GVFpYSjJhV05sUkdWbWFXNXBkR2x2YnJibkRWOUJOR3p4QWdBQlRBQVJjMlZ5ZG1salpVbHVkR1Z5Wm1GalpYTjBBQkJNYW1GMllTOTFkR2xzTDB4cGMzUTdlSElBTG05eVp5NXJkV0ZzYVM1eWFXTmxMbXR6WWk1dFpYTnpZV2RwYm1jdVUyVnlkbWxqWlVSbFptbHVhWFJwYjI0QW13SlBXTjBwZmdJQURVd0FDMkoxYzFObFkzVnlhWFI1ZEFBVFRHcGhkbUV2YkdGdVp5OUNiMjlzWldGdU8wd0FEMk55WldSbGJuUnBZV3h6Vkhsd1pYUUFURXh2Y21jdmEzVmhiR2t2Y21salpTOWpiM0psTDNObFkzVnlhWFI1TDJOeVpXUmxiblJwWVd4ekwwTnlaV1JsYm5ScFlXeHpVMjkxY21ObEpFTnlaV1JsYm5ScFlXeHpWSGx3WlR0TUFCQnNiMk5oYkZObGNuWnBZMlZPWVcxbGRBQVNUR3BoZG1FdmJHRnVaeTlUZEhKcGJtYzdUQUFYYldWemMyRm5aVVY0WTJWd2RHbHZia2hoYm1Sc1pYSnhBSDRBQlV3QURHMXBiR3hwYzFSdlRHbDJaWFFBRUV4cVlYWmhMMnhoYm1jdlRHOXVaenRNQUFod2NtbHZjbWwwZVhRQUUweHFZWFpoTDJ4aGJtY3ZTVzUwWldkbGNqdE1BQVZ4ZFdWMVpYRUFmZ0FEVEFBTmNtVjBjbmxCZEhSbGJYQjBjM0VBZmdBSFRBQUhjMlZ5ZG1salpYUUFFa3hxWVhaaEwyeGhibWN2VDJKcVpXTjBPMHdBRDNObGNuWnBZMlZGYm1SUWIybHVkSFFBRGt4cVlYWmhMMjVsZEM5VlVrdzdUQUFUYzJWeWRtbGpaVTVoYldWVGNHRmpaVlZTU1hFQWZnQUZUQUFRYzJWeWRtbGpaVTVoYldWemNHRmpaWEVBZmdBRlRBQUxjMlZ5ZG1salpWQmhkR2h4QUg0QUJYaHdjM0lBRVdwaGRtRXViR0Z1Wnk1Q2IyOXNaV0Z1elNCeWdOV2MrdTRDQUFGYUFBVjJZV3gxWlhod0FYQjBBQnBQVTBOaFkyaGxUbTkwYVdacFkyRjBhVzl1VTJWeWRtbGpaWFFBVFc5eVp5NXJkV0ZzYVM1eWFXTmxMbXR6WWk1dFpYTnpZV2RwYm1jdVpYaGpaWEIwYVc5dWFHRnVaR3hwYm1jdVJHVm1ZWFZzZEUxbGMzTmhaMlZGZUdObGNIUnBiMjVJWVc1a2JHVnljM0lBRG1waGRtRXViR0Z1Wnk1TWIyNW5PNHZra015UEk5OENBQUZLQUFWMllXeDFaWGh5QUJCcVlYWmhMbXhoYm1jdVRuVnRZbVZ5aHF5VkhRdVU0SXNDQUFCNGNQLy8vLy8vLy8vL2MzSUFFV3BoZG1FdWJHRnVaeTVKYm5SbFoyVnlFdUtncFBlQmh6Z0NBQUZKQUFWMllXeDFaWGh4QUg0QUVBQUFBQU56Y1FCK0FBc0FjUUIrQUJOd2MzSUFER3BoZG1FdWJtVjBMbFZTVEpZbE56WWEvT1J5QXdBSFNRQUlhR0Z6YUVOdlpHVkpBQVJ3YjNKMFRBQUpZWFYwYUc5eWFYUjVjUUIrQUFWTUFBUm1hV3hsY1FCK0FBVk1BQVJvYjNOMGNRQitBQVZNQUFod2NtOTBiMk52YkhFQWZnQUZUQUFEY21WbWNRQitBQVY0Y1AvLy8vOEFBQitRZEFBT2JHOWpZV3hvYjNOME9qZ3dPREIwQUNzdmEzSXRaR1YyTDNKbGJXOTBhVzVuTDA5VFEyRmphR1ZPYjNScFptbGpZWFJwYjI1VFpYSjJhV05sZEFBSmJHOWpZV3hvYjNOMGRBQUVhSFIwY0hCNGRBQUFkQUFHVkZKQlZrVk1kQUFCTDNOeUFCTnFZWFpoTG5WMGFXd3VRWEp5WVhsTWFYTjBlSUhTSFpuSFlaMERBQUZKQUFSemFYcGxlSEFBQUFBRmR3UUFBQUFLZEFBemIzSm5MbXQxWVd4cExuSnBZMlV1YTNOaUxtMWxjM05oWjJsdVp5';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 429    FOR UPDATE;        
    buffer := 'NXpaWEoyYVdObExrdFRRa3BoZG1GVFpYSjJhV05sZEFBOFkyOXRMbTl3Wlc1emVXMXdhRzl1ZVM1dmMyTmhZMmhsTG1KaGMyVXVaWFpsYm5SekxrTmhZMmhsUlc1MGNubEZkbVZ1ZEV4cGMzUmxibVZ5ZEFBM1kyOXRMbTl3Wlc1emVXMXdhRzl1ZVM1dmMyTmhZMmhsTG1KaGMyVXVaWFpsYm5SekxrTmhZMmhsUlhabGJuUk1hWE4wWlc1bGNuUUFGMnBoZG1FdWRYUnBiQzVGZG1WdWRFeHBjM1JsYm1WeWRBQXNZMjl0TG05d1pXNXplVzF3YUc5dWVTNXZjMk5oWTJobExtSmhjMlV1VEdsbVpXTjVZMnhsUVhkaGNtVjR0AA0xMjkuMTg2Ljc5LjQ1c3IAMm9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuSmF2YVNlcnZpY2VEZWZpbml0aW9utucNX0E0bPECAAFMABFzZXJ2aWNlSW50ZXJmYWNlc3QAEExqYXZhL3V0aWwvTGlzdDt4cgAub3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5TZXJ2aWNlRGVmaW5pdGlvbgCbAk9Y3Sl+AgANTAALYnVzU2VjdXJpdHlxAH4AE0wAD2NyZWRlbnRpYWxzVHlwZXQATExvcmcva3VhbGkvcmljZS9jb3JlL3NlY3VyaXR5L2NyZWRlbnRpYWxzL0NyZWRlbnRpYWxzU291cmNlJENyZWRlbnRpYWxzVHlwZTtMABBsb2NhbFNlcnZpY2VOYW1lcQB+AARMABdtZXNzYWdlRXhjZXB0aW9uSGFuZGxlcnEAfgAETAAMbWlsbGlzVG9MaXZlcQB+ABVMAAhwcmlvcml0eXEAfgAUTAAFcXVldWVxAH4AE0wADXJldHJ5QXR0ZW1wdHNxAH4AFEwAB3NlcnZpY2V0ABJMamF2YS9sYW5nL09iamVjdDtMAA9zZXJ2aWNlRW5kUG9pbnR0AA5MamF2YS9uZXQvVVJMO0wAE3NlcnZpY2VOYW1lU3BhY2VVUklxAH4ABEwAEHNlcnZpY2VOYW1lc3BhY2VxAH4ABEwAC3NlcnZpY2VQYXRocQB+AAR4cHNxAH4AGQFwdAAaT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2V0AE1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLmV4Y2VwdGlvbmhhbmRsaW5nLkRlZmF1bHRNZXNzYWdlRXhjZXB0aW9uSGFuZGxlcnNxAH4AH///////////c3EAfgAcAAAAA3NxAH4AGQBxAH4AMnBzcgAMamF2YS5uZXQuVVJMliU3Nhr85HIDAAdJAAhoYXNoQ29kZUkABHBvcnRMAAlhdXRob3JpdHlxAH4ABEwABGZpbGVxAH4ABEwABGhvc3RxAH4ABEwACHByb3RvY29scQB+AARMAANyZWZxAH4ABHhwaRuioAAAH5B0AA5sb2NhbGhvc3Q6ODA4MHQAKy9rci1kZXYvcmVtb3RpbmcvT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2V0AAlsb2NhbGhvc3R0AARodHRwcHh0AAB0AAZUUkFWRUx0AAEvc3IAE2phdmEudXRpbC5BcnJheUxpc3R4gdIdmcdhnQMAAUkABHNpemV4cAAAAAV3BAAAAAp0ADNvcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLnNlcnZpY2UuS1NCSmF2YVNlcnZpY2V0ADxjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5ldmVudHMuQ2FjaGVFbnRyeUV2ZW50TGlzdGVuZXJ0ADdjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5ldmVudHMuQ2FjaGVFdmVudExpc3RlbmVydAAXamF2YS51dGlsLkV2ZW50TGlzdGVuZXJ0ACxjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5MaWZlY3ljbGVBd2FyZXhxAH4AI3QABlRSQVZFTA==';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
INSERT INTO KRSB_MSG_PYLD_T (MSG_PYLD,MSG_QUE_ID)
  VALUES (EMPTY_CLOB(),430)
/
-- Length: 6060
-- Chunks: 2
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 430    FOR UPDATE;        
    buffer := 'rO0ABXNyAC1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLkFzeW5jaHJvbm91c0NhbGzxnQ5Y0t0lZQIAB1oAFWlnbm9yZVN0b3JlQW5kRm9yd2FyZFsACWFyZ3VtZW50c3QAE1tMamF2YS9sYW5nL09iamVjdDtMAAhjYWxsYmFja3QAM0xvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL0FzeW5jaHJvbm91c0NhbGxiYWNrO0wAB2NvbnRleHR0ABZMamF2YS9pby9TZXJpYWxpemFibGU7TAAKbWV0aG9kTmFtZXQAEkxqYXZhL2xhbmcvU3RyaW5nO1sACnBhcmFtVHlwZXN0ABJbTGphdmEvbGFuZy9DbGFzcztMAAtzZXJ2aWNlSW5mb3QAKkxvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL1NlcnZpY2VJbmZvO3hwAHVyABNbTGphdmEubGFuZy5PYmplY3Q7kM5YnxBzKWwCAAB4cAAAAAFzcgBDY29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLnBsdWdpbnMuY2x1c3RlcnN1cHBvcnQuQ2x1c3Rlck5vdGlmaWNhdGlvbhY+6VckvG9pAgACSQAEdHlwZUwABGRhdGFxAH4AA3hwAAAAAnQAEERvY3VtZW50VHlwZU5hbWVwcHQABmludm9rZXVyABJbTGphdmEubGFuZy5DbGFzczurFteuy81amQIAAHhwAAAAAXZyABRqYXZhLmlvLlNlcmlhbGl6YWJsZRCbYQ3Xm2TtAgAAeHBzcgAob3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5TZXJ2aWNlSW5mb8UXI7YpBLe6AgALTAAFYWxpdmV0ABNMamF2YS9sYW5nL0Jvb2xlYW47TAAUZW5kcG9pbnRBbHRlcm5hdGVVcmxxAH4ABEwAC2VuZHBvaW50VXJscQB+AARMAApsb2NrVmVyTmJydAATTGphdmEvbGFuZy9JbnRlZ2VyO0wADm1lc3NhZ2VFbnRyeUlkdAAQTGphdmEvbGFuZy9Mb25nO0wABXFuYW1ldAAbTGphdmF4L3htbC9uYW1lc3BhY2UvUU5hbWU7TAAac2VyaWFsaXplZFNlcnZpY2VOYW1lc3BhY2VxAH4ABEwACHNlcnZlcklwcQB+AARMABFzZXJ2aWNlRGVmaW5pdGlvbnQAMExvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL1NlcnZpY2VEZWZpbml0aW9uO0wAC3NlcnZpY2VOYW1lcQB+AARMABBzZXJ2aWNlTmFtZXNwYWNlcQB+AAR4cHNyABFqYXZhLmxhbmcuQm9vbGVhbs0gcoDVnPruAgABWgAFdmFsdWV4cAFwdABAaHR0cDovL2xvY2FsaG9zdDo4MDgwL2tyLWRldi9yZW1vdGluZy9PU0NhY2hlTm90aWZpY2F0aW9uU2VydmljZXNyABFqYXZhLmxhbmcuSW50ZWdlchLioKT3gYc4AgABSQAFdmFsdWV4cgAQamF2YS5sYW5nLk51bWJlcoaslR0LlOCLAgAAeHAAAAABc3IADmphdmEubGFuZy5Mb25nO4vkkMyPI98CAAFKAAV2YWx1ZXhxAH4AHQAAAAAAAA99c3IAGWphdmF4LnhtbC5uYW1lc3BhY2UuUU5hbWWBbagt/DvdbAIAA0wACWxvY2FsUGFydHEAfgAETAAMbmFtZXNwYWNlVVJJcQB+AARMAAZwcmVmaXhxAH4ABHhwdAAaT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2V0AABxAH4AJHQHdHJPMEFCWE55QURKdmNtY3VhM1ZoYkdrdWNtbGpaUzVyYzJJdWJXVnpjMkZuYVc1bkxrcGhkbUZUWlhKMmFXTmxSR1ZtYVc1cGRHbHZicmJuRFY5Qk5HenhBZ0FCVEFBUmMyVnlkbWxqWlVsdWRHVnlabUZqWlhOMEFCQk1hbUYyWVM5MWRHbHNMMHhwYzNRN2VISUFMbTl5Wnk1cmRXRnNhUzV5YVdObExtdHpZaTV0WlhOellXZHBibWN1VTJWeWRtbGpaVVJsWm1sdWFYUnBiMjRBbXdKUFdOMHBmZ0lBRFV3QUMySjFjMU5sWTNWeWFYUjVkQUFUVEdwaGRtRXZiR0Z1Wnk5Q2IyOXNaV0Z1TzB3QUQyTnlaV1JsYm5ScFlXeHpWSGx3WlhRQVRFeHZjbWN2YTNWaGJHa3ZjbWxqWlM5amIzSmxMM05sWTNWeWFYUjVMMk55WldSbGJuUnBZV3h6TDBOeVpXUmxiblJwWVd4elUyOTFjbU5sSkVOeVpXUmxiblJwWVd4elZIbHdaVHRNQUJCc2IyTmhiRk5sY25acFkyVk9ZVzFsZEFBU1RHcGhkbUV2YkdGdVp5OVRkSEpwYm1jN1RBQVhiV1Z6YzJGblpVVjRZMlZ3ZEdsdmJraGhibVJzWlhKeEFINEFCVXdBREcxcGJHeHBjMVJ2VEdsMlpYUUFFRXhxWVhaaEwyeGhibWN2VEc5dVp6dE1BQWh3Y21sdmNtbDBlWFFBRTB4cVlYWmhMMnhoYm1jdlNXNTBaV2RsY2p0TUFBVnhkV1YxWlhFQWZnQURUQUFOY21WMGNubEJkSFJsYlhCMGMzRUFmZ0FIVEFBSGMyVnlkbWxqWlhRQUVreHFZWFpoTDJ4aGJtY3ZUMkpxWldOME8wd0FEM05sY25acFkyVkZibVJRYjJsdWRIUUFEa3hxWVhaaEwyNWxkQzlWVWt3N1RBQVRjMlZ5ZG1salpVNWhiV1ZUY0dGalpWVlNTWEVBZmdBRlRBQVFjMlZ5ZG1salpVNWhiV1Z6Y0dGalpYRUFmZ0FGVEFBTGMyVnlkbWxqWlZCaGRHaHhBSDRBQlhod2MzSUFFV3BoZG1FdWJHRnVaeTVDYjI5c1pXRnV6U0J5Z05XYyt1NENBQUZhQUFWMllXeDFaWGh3QVhCMEFCcFBVME5oWTJobFRtOTBhV1pwWTJGMGFXOXVVMlZ5ZG1salpYUUFUVzl5Wnk1cmRXRnNhUzV5YVdObExtdHpZaTV0WlhOellXZHBibWN1WlhoalpYQjBhVzl1YUdGdVpHeHBibWN1UkdWbVlYVnNkRTFsYzNOaFoyVkZlR05sY0hScGIyNUlZVzVrYkdWeWMzSUFEbXBoZG1FdWJHRnVaeTVNYjI1bk80dmtrTXlQSTk4Q0FBRktBQVYyWVd4MVpYaHlBQkJxWVhaaExteGhibWN1VG5WdFltVnlocXlWSFF1VTRJc0NBQUI0Y1AvLy8vLy8vLy8vYzNJQUVXcGhkbUV1YkdGdVp5NUpiblJsWjJWeUV1S2dwUGVCaHpnQ0FBRkpBQVYyWVd4MVpYaHhBSDRBRUFBQUFBTnpjUUIrQUFzQWNRQitBQk53YzNJQURHcGhkbUV1Ym1WMExsVlNUSllsTnpZYS9PUnlBd0FIU1FBSWFHRnphRU52WkdWSkFBUndiM0owVEFBSllYVjBhRzl5YVhSNWNRQitBQVZNQUFSbWFXeGxjUUIrQUFWTUFBUm9iM04wY1FCK0FBVk1BQWh3Y205MGIyTnZiSEVBZmdBRlRBQURjbVZtY1FCK0FBVjRjUC8vLy84QUFCK1FkQUFPYkc5allXeG9iM04wT2pnd09EQjBBQ3N2YTNJdFpHVjJMM0psYlc5MGFXNW5MMDlUUTJGamFHVk9iM1JwWm1sallYUnBiMjVUWlhKMmFXTmxkQUFKYkc5allXeG9iM04wZEFBRWFIUjBjSEI0ZEFBQWRBQUdWRkpCVmtWTWRBQUJMM055QUJOcVlYWmhMblYwYVd3dVFYSnlZWGxNYVhOMGVJSFNIWm5IWVowREFBRkpBQVJ6YVhwbGVIQUFBQUFGZHdRQUFBQUtkQUF6YjNKbkxtdDFZV3hwTG5KcFkyVXVhM05pTG0xbGMzTmhaMmx1';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 430    FOR UPDATE;        
    buffer := 'Wnk1elpYSjJhV05sTGt0VFFrcGhkbUZUWlhKMmFXTmxkQUE4WTI5dExtOXdaVzV6ZVcxd2FHOXVlUzV2YzJOaFkyaGxMbUpoYzJVdVpYWmxiblJ6TGtOaFkyaGxSVzUwY25sRmRtVnVkRXhwYzNSbGJtVnlkQUEzWTI5dExtOXdaVzV6ZVcxd2FHOXVlUzV2YzJOaFkyaGxMbUpoYzJVdVpYWmxiblJ6TGtOaFkyaGxSWFpsYm5STWFYTjBaVzVsY25RQUYycGhkbUV1ZFhScGJDNUZkbVZ1ZEV4cGMzUmxibVZ5ZEFBc1kyOXRMbTl3Wlc1emVXMXdhRzl1ZVM1dmMyTmhZMmhsTG1KaGMyVXVUR2xtWldONVkyeGxRWGRoY21WNHQADTEyOS4xODYuNzkuNDVzcgAyb3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5KYXZhU2VydmljZURlZmluaXRpb2625w1fQTRs8QIAAUwAEXNlcnZpY2VJbnRlcmZhY2VzdAAQTGphdmEvdXRpbC9MaXN0O3hyAC5vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLlNlcnZpY2VEZWZpbml0aW9uAJsCT1jdKX4CAA1MAAtidXNTZWN1cml0eXEAfgATTAAPY3JlZGVudGlhbHNUeXBldABMTG9yZy9rdWFsaS9yaWNlL2NvcmUvc2VjdXJpdHkvY3JlZGVudGlhbHMvQ3JlZGVudGlhbHNTb3VyY2UkQ3JlZGVudGlhbHNUeXBlO0wAEGxvY2FsU2VydmljZU5hbWVxAH4ABEwAF21lc3NhZ2VFeGNlcHRpb25IYW5kbGVycQB+AARMAAxtaWxsaXNUb0xpdmVxAH4AFUwACHByaW9yaXR5cQB+ABRMAAVxdWV1ZXEAfgATTAANcmV0cnlBdHRlbXB0c3EAfgAUTAAHc2VydmljZXQAEkxqYXZhL2xhbmcvT2JqZWN0O0wAD3NlcnZpY2VFbmRQb2ludHQADkxqYXZhL25ldC9VUkw7TAATc2VydmljZU5hbWVTcGFjZVVSSXEAfgAETAAQc2VydmljZU5hbWVzcGFjZXEAfgAETAALc2VydmljZVBhdGhxAH4ABHhwc3EAfgAZAXB0ABpPU0NhY2hlTm90aWZpY2F0aW9uU2VydmljZXQATW9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuZXhjZXB0aW9uaGFuZGxpbmcuRGVmYXVsdE1lc3NhZ2VFeGNlcHRpb25IYW5kbGVyc3EAfgAf//////////9zcQB+ABwAAAADc3EAfgAZAHEAfgAycHNyAAxqYXZhLm5ldC5VUkyWJTc2GvzkcgMAB0kACGhhc2hDb2RlSQAEcG9ydEwACWF1dGhvcml0eXEAfgAETAAEZmlsZXEAfgAETAAEaG9zdHEAfgAETAAIcHJvdG9jb2xxAH4ABEwAA3JlZnEAfgAEeHBpG6KgAAAfkHQADmxvY2FsaG9zdDo4MDgwdAArL2tyLWRldi9yZW1vdGluZy9PU0NhY2hlTm90aWZpY2F0aW9uU2VydmljZXQACWxvY2FsaG9zdHQABGh0dHBweHQAAHQABlRSQVZFTHQAAS9zcgATamF2YS51dGlsLkFycmF5TGlzdHiB0h2Zx2GdAwABSQAEc2l6ZXhwAAAABXcEAAAACnQAM29yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuc2VydmljZS5LU0JKYXZhU2VydmljZXQAPGNvbS5vcGVuc3ltcGhvbnkub3NjYWNoZS5iYXNlLmV2ZW50cy5DYWNoZUVudHJ5RXZlbnRMaXN0ZW5lcnQAN2NvbS5vcGVuc3ltcGhvbnkub3NjYWNoZS5iYXNlLmV2ZW50cy5DYWNoZUV2ZW50TGlzdGVuZXJ0ABdqYXZhLnV0aWwuRXZlbnRMaXN0ZW5lcnQALGNvbS5vcGVuc3ltcGhvbnkub3NjYWNoZS5iYXNlLkxpZmVjeWNsZUF3YXJleHEAfgAjdAAGVFJBVkVM';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
INSERT INTO KRSB_MSG_PYLD_T (MSG_PYLD,MSG_QUE_ID)
  VALUES (EMPTY_CLOB(),431)
/
-- Length: 6084
-- Chunks: 2
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 431    FOR UPDATE;        
    buffer := 'rO0ABXNyAC1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLkFzeW5jaHJvbm91c0NhbGzxnQ5Y0t0lZQIAB1oAFWlnbm9yZVN0b3JlQW5kRm9yd2FyZFsACWFyZ3VtZW50c3QAE1tMamF2YS9sYW5nL09iamVjdDtMAAhjYWxsYmFja3QAM0xvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL0FzeW5jaHJvbm91c0NhbGxiYWNrO0wAB2NvbnRleHR0ABZMamF2YS9pby9TZXJpYWxpemFibGU7TAAKbWV0aG9kTmFtZXQAEkxqYXZhL2xhbmcvU3RyaW5nO1sACnBhcmFtVHlwZXN0ABJbTGphdmEvbGFuZy9DbGFzcztMAAtzZXJ2aWNlSW5mb3QAKkxvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL1NlcnZpY2VJbmZvO3hwAHVyABNbTGphdmEubGFuZy5PYmplY3Q7kM5YnxBzKWwCAAB4cAAAAAFzcgBDY29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLnBsdWdpbnMuY2x1c3RlcnN1cHBvcnQuQ2x1c3Rlck5vdGlmaWNhdGlvbhY+6VckvG9pAgACSQAEdHlwZUwABGRhdGFxAH4AA3hwAAAAAXQAIERvY3VtZW50VHlwZTpDdXJyZW50Um9vdHNJbkNhY2hlcHB0AAZpbnZva2V1cgASW0xqYXZhLmxhbmcuQ2xhc3M7qxbXrsvNWpkCAAB4cAAAAAF2cgAUamF2YS5pby5TZXJpYWxpemFibGUQm2EN15tk7QIAAHhwc3IAKG9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuU2VydmljZUluZm/FFyO2KQS3ugIAC0wABWFsaXZldAATTGphdmEvbGFuZy9Cb29sZWFuO0wAFGVuZHBvaW50QWx0ZXJuYXRlVXJscQB+AARMAAtlbmRwb2ludFVybHEAfgAETAAKbG9ja1Zlck5icnQAE0xqYXZhL2xhbmcvSW50ZWdlcjtMAA5tZXNzYWdlRW50cnlJZHQAEExqYXZhL2xhbmcvTG9uZztMAAVxbmFtZXQAG0xqYXZheC94bWwvbmFtZXNwYWNlL1FOYW1lO0wAGnNlcmlhbGl6ZWRTZXJ2aWNlTmFtZXNwYWNlcQB+AARMAAhzZXJ2ZXJJcHEAfgAETAARc2VydmljZURlZmluaXRpb250ADBMb3JnL2t1YWxpL3JpY2Uva3NiL21lc3NhZ2luZy9TZXJ2aWNlRGVmaW5pdGlvbjtMAAtzZXJ2aWNlTmFtZXEAfgAETAAQc2VydmljZU5hbWVzcGFjZXEAfgAEeHBzcgARamF2YS5sYW5nLkJvb2xlYW7NIHKA1Zz67gIAAVoABXZhbHVleHABcHQAQGh0dHA6Ly9sb2NhbGhvc3Q6ODA4MC9rci1kZXYvcmVtb3RpbmcvT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2VzcgARamF2YS5sYW5nLkludGVnZXIS4qCk94GHOAIAAUkABXZhbHVleHIAEGphdmEubGFuZy5OdW1iZXKGrJUdC5TgiwIAAHhwAAAAAXNyAA5qYXZhLmxhbmcuTG9uZzuL5JDMjyPfAgABSgAFdmFsdWV4cQB+AB0AAAAAAAAPfXNyABlqYXZheC54bWwubmFtZXNwYWNlLlFOYW1lgW2oLfw73WwCAANMAAlsb2NhbFBhcnRxAH4ABEwADG5hbWVzcGFjZVVSSXEAfgAETAAGcHJlZml4cQB+AAR4cHQAGk9TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNldAAAcQB+ACR0B3RyTzBBQlhOeUFESnZjbWN1YTNWaGJHa3VjbWxqWlM1cmMySXViV1Z6YzJGbmFXNW5Ma3BoZG1GVFpYSjJhV05sUkdWbWFXNXBkR2x2YnJibkRWOUJOR3p4QWdBQlRBQVJjMlZ5ZG1salpVbHVkR1Z5Wm1GalpYTjBBQkJNYW1GMllTOTFkR2xzTDB4cGMzUTdlSElBTG05eVp5NXJkV0ZzYVM1eWFXTmxMbXR6WWk1dFpYTnpZV2RwYm1jdVUyVnlkbWxqWlVSbFptbHVhWFJwYjI0QW13SlBXTjBwZmdJQURVd0FDMkoxYzFObFkzVnlhWFI1ZEFBVFRHcGhkbUV2YkdGdVp5OUNiMjlzWldGdU8wd0FEMk55WldSbGJuUnBZV3h6Vkhsd1pYUUFURXh2Y21jdmEzVmhiR2t2Y21salpTOWpiM0psTDNObFkzVnlhWFI1TDJOeVpXUmxiblJwWVd4ekwwTnlaV1JsYm5ScFlXeHpVMjkxY21ObEpFTnlaV1JsYm5ScFlXeHpWSGx3WlR0TUFCQnNiMk5oYkZObGNuWnBZMlZPWVcxbGRBQVNUR3BoZG1FdmJHRnVaeTlUZEhKcGJtYzdUQUFYYldWemMyRm5aVVY0WTJWd2RHbHZia2hoYm1Sc1pYSnhBSDRBQlV3QURHMXBiR3hwYzFSdlRHbDJaWFFBRUV4cVlYWmhMMnhoYm1jdlRHOXVaenRNQUFod2NtbHZjbWwwZVhRQUUweHFZWFpoTDJ4aGJtY3ZTVzUwWldkbGNqdE1BQVZ4ZFdWMVpYRUFmZ0FEVEFBTmNtVjBjbmxCZEhSbGJYQjBjM0VBZmdBSFRBQUhjMlZ5ZG1salpYUUFFa3hxWVhaaEwyeGhibWN2VDJKcVpXTjBPMHdBRDNObGNuWnBZMlZGYm1SUWIybHVkSFFBRGt4cVlYWmhMMjVsZEM5VlVrdzdUQUFUYzJWeWRtbGpaVTVoYldWVGNHRmpaVlZTU1hFQWZnQUZUQUFRYzJWeWRtbGpaVTVoYldWemNHRmpaWEVBZmdBRlRBQUxjMlZ5ZG1salpWQmhkR2h4QUg0QUJYaHdjM0lBRVdwaGRtRXViR0Z1Wnk1Q2IyOXNaV0Z1elNCeWdOV2MrdTRDQUFGYUFBVjJZV3gxWlhod0FYQjBBQnBQVTBOaFkyaGxUbTkwYVdacFkyRjBhVzl1VTJWeWRtbGpaWFFBVFc5eVp5NXJkV0ZzYVM1eWFXTmxMbXR6WWk1dFpYTnpZV2RwYm1jdVpYaGpaWEIwYVc5dWFHRnVaR3hwYm1jdVJHVm1ZWFZzZEUxbGMzTmhaMlZGZUdObGNIUnBiMjVJWVc1a2JHVnljM0lBRG1waGRtRXViR0Z1Wnk1TWIyNW5PNHZra015UEk5OENBQUZLQUFWMllXeDFaWGh5QUJCcVlYWmhMbXhoYm1jdVRuVnRZbVZ5aHF5VkhRdVU0SXNDQUFCNGNQLy8vLy8vLy8vL2MzSUFFV3BoZG1FdWJHRnVaeTVKYm5SbFoyVnlFdUtncFBlQmh6Z0NBQUZKQUFWMllXeDFaWGh4QUg0QUVBQUFBQU56Y1FCK0FBc0FjUUIrQUJOd2MzSUFER3BoZG1FdWJtVjBMbFZTVEpZbE56WWEvT1J5QXdBSFNRQUlhR0Z6YUVOdlpHVkpBQVJ3YjNKMFRBQUpZWFYwYUc5eWFYUjVjUUIrQUFWTUFBUm1hV3hsY1FCK0FBVk1BQVJvYjNOMGNRQitBQVZNQUFod2NtOTBiMk52YkhFQWZnQUZUQUFEY21WbWNRQitBQVY0Y1AvLy8vOEFBQitRZEFBT2JHOWpZV3hvYjNOME9qZ3dPREIwQUNzdmEzSXRaR1YyTDNKbGJXOTBhVzVuTDA5VFEyRmphR1ZPYjNScFptbGpZWFJwYjI1VFpYSjJhV05sZEFBSmJHOWpZV3hvYjNOMGRBQUVhSFIwY0hCNGRBQUFkQUFHVkZKQlZrVk1kQUFCTDNOeUFCTnFZWFpoTG5WMGFXd3VRWEp5WVhsTWFYTjBlSUhTSFpuSFlaMERBQUZKQUFSemFYcGxlSEFBQUFBRmR3UUFBQUFLZEFBemIzSm5MbXQxWVd4cExuSnBZMlV1';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 431    FOR UPDATE;        
    buffer := 'YTNOaUxtMWxjM05oWjJsdVp5NXpaWEoyYVdObExrdFRRa3BoZG1GVFpYSjJhV05sZEFBOFkyOXRMbTl3Wlc1emVXMXdhRzl1ZVM1dmMyTmhZMmhsTG1KaGMyVXVaWFpsYm5SekxrTmhZMmhsUlc1MGNubEZkbVZ1ZEV4cGMzUmxibVZ5ZEFBM1kyOXRMbTl3Wlc1emVXMXdhRzl1ZVM1dmMyTmhZMmhsTG1KaGMyVXVaWFpsYm5SekxrTmhZMmhsUlhabGJuUk1hWE4wWlc1bGNuUUFGMnBoZG1FdWRYUnBiQzVGZG1WdWRFeHBjM1JsYm1WeWRBQXNZMjl0TG05d1pXNXplVzF3YUc5dWVTNXZjMk5oWTJobExtSmhjMlV1VEdsbVpXTjVZMnhsUVhkaGNtVjR0AA0xMjkuMTg2Ljc5LjQ1c3IAMm9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuSmF2YVNlcnZpY2VEZWZpbml0aW9utucNX0E0bPECAAFMABFzZXJ2aWNlSW50ZXJmYWNlc3QAEExqYXZhL3V0aWwvTGlzdDt4cgAub3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5TZXJ2aWNlRGVmaW5pdGlvbgCbAk9Y3Sl+AgANTAALYnVzU2VjdXJpdHlxAH4AE0wAD2NyZWRlbnRpYWxzVHlwZXQATExvcmcva3VhbGkvcmljZS9jb3JlL3NlY3VyaXR5L2NyZWRlbnRpYWxzL0NyZWRlbnRpYWxzU291cmNlJENyZWRlbnRpYWxzVHlwZTtMABBsb2NhbFNlcnZpY2VOYW1lcQB+AARMABdtZXNzYWdlRXhjZXB0aW9uSGFuZGxlcnEAfgAETAAMbWlsbGlzVG9MaXZlcQB+ABVMAAhwcmlvcml0eXEAfgAUTAAFcXVldWVxAH4AE0wADXJldHJ5QXR0ZW1wdHNxAH4AFEwAB3NlcnZpY2V0ABJMamF2YS9sYW5nL09iamVjdDtMAA9zZXJ2aWNlRW5kUG9pbnR0AA5MamF2YS9uZXQvVVJMO0wAE3NlcnZpY2VOYW1lU3BhY2VVUklxAH4ABEwAEHNlcnZpY2VOYW1lc3BhY2VxAH4ABEwAC3NlcnZpY2VQYXRocQB+AAR4cHNxAH4AGQFwdAAaT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2V0AE1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLmV4Y2VwdGlvbmhhbmRsaW5nLkRlZmF1bHRNZXNzYWdlRXhjZXB0aW9uSGFuZGxlcnNxAH4AH///////////c3EAfgAcAAAAA3NxAH4AGQBxAH4AMnBzcgAMamF2YS5uZXQuVVJMliU3Nhr85HIDAAdJAAhoYXNoQ29kZUkABHBvcnRMAAlhdXRob3JpdHlxAH4ABEwABGZpbGVxAH4ABEwABGhvc3RxAH4ABEwACHByb3RvY29scQB+AARMAANyZWZxAH4ABHhwaRuioAAAH5B0AA5sb2NhbGhvc3Q6ODA4MHQAKy9rci1kZXYvcmVtb3RpbmcvT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2V0AAlsb2NhbGhvc3R0AARodHRwcHh0AAB0AAZUUkFWRUx0AAEvc3IAE2phdmEudXRpbC5BcnJheUxpc3R4gdIdmcdhnQMAAUkABHNpemV4cAAAAAV3BAAAAAp0ADNvcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLnNlcnZpY2UuS1NCSmF2YVNlcnZpY2V0ADxjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5ldmVudHMuQ2FjaGVFbnRyeUV2ZW50TGlzdGVuZXJ0ADdjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5ldmVudHMuQ2FjaGVFdmVudExpc3RlbmVydAAXamF2YS51dGlsLkV2ZW50TGlzdGVuZXJ0ACxjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5MaWZlY3ljbGVBd2FyZXhxAH4AI3QABlRSQVZFTA==';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
INSERT INTO KRSB_MSG_PYLD_T (MSG_PYLD,MSG_QUE_ID)
  VALUES (EMPTY_CLOB(),432)
/
-- Length: 6060
-- Chunks: 2
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 432    FOR UPDATE;        
    buffer := 'rO0ABXNyAC1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLkFzeW5jaHJvbm91c0NhbGzxnQ5Y0t0lZQIAB1oAFWlnbm9yZVN0b3JlQW5kRm9yd2FyZFsACWFyZ3VtZW50c3QAE1tMamF2YS9sYW5nL09iamVjdDtMAAhjYWxsYmFja3QAM0xvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL0FzeW5jaHJvbm91c0NhbGxiYWNrO0wAB2NvbnRleHR0ABZMamF2YS9pby9TZXJpYWxpemFibGU7TAAKbWV0aG9kTmFtZXQAEkxqYXZhL2xhbmcvU3RyaW5nO1sACnBhcmFtVHlwZXN0ABJbTGphdmEvbGFuZy9DbGFzcztMAAtzZXJ2aWNlSW5mb3QAKkxvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL1NlcnZpY2VJbmZvO3hwAHVyABNbTGphdmEubGFuZy5PYmplY3Q7kM5YnxBzKWwCAAB4cAAAAAFzcgBDY29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLnBsdWdpbnMuY2x1c3RlcnN1cHBvcnQuQ2x1c3Rlck5vdGlmaWNhdGlvbhY+6VckvG9pAgACSQAEdHlwZUwABGRhdGFxAH4AA3hwAAAAAnQADkRvY3VtZW50VHlwZUlkcHB0AAZpbnZva2V1cgASW0xqYXZhLmxhbmcuQ2xhc3M7qxbXrsvNWpkCAAB4cAAAAAF2cgAUamF2YS5pby5TZXJpYWxpemFibGUQm2EN15tk7QIAAHhwc3IAKG9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuU2VydmljZUluZm/FFyO2KQS3ugIAC0wABWFsaXZldAATTGphdmEvbGFuZy9Cb29sZWFuO0wAFGVuZHBvaW50QWx0ZXJuYXRlVXJscQB+AARMAAtlbmRwb2ludFVybHEAfgAETAAKbG9ja1Zlck5icnQAE0xqYXZhL2xhbmcvSW50ZWdlcjtMAA5tZXNzYWdlRW50cnlJZHQAEExqYXZhL2xhbmcvTG9uZztMAAVxbmFtZXQAG0xqYXZheC94bWwvbmFtZXNwYWNlL1FOYW1lO0wAGnNlcmlhbGl6ZWRTZXJ2aWNlTmFtZXNwYWNlcQB+AARMAAhzZXJ2ZXJJcHEAfgAETAARc2VydmljZURlZmluaXRpb250ADBMb3JnL2t1YWxpL3JpY2Uva3NiL21lc3NhZ2luZy9TZXJ2aWNlRGVmaW5pdGlvbjtMAAtzZXJ2aWNlTmFtZXEAfgAETAAQc2VydmljZU5hbWVzcGFjZXEAfgAEeHBzcgARamF2YS5sYW5nLkJvb2xlYW7NIHKA1Zz67gIAAVoABXZhbHVleHABcHQAQGh0dHA6Ly9sb2NhbGhvc3Q6ODA4MC9rci1kZXYvcmVtb3RpbmcvT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2VzcgARamF2YS5sYW5nLkludGVnZXIS4qCk94GHOAIAAUkABXZhbHVleHIAEGphdmEubGFuZy5OdW1iZXKGrJUdC5TgiwIAAHhwAAAAAXNyAA5qYXZhLmxhbmcuTG9uZzuL5JDMjyPfAgABSgAFdmFsdWV4cQB+AB0AAAAAAAAPfXNyABlqYXZheC54bWwubmFtZXNwYWNlLlFOYW1lgW2oLfw73WwCAANMAAlsb2NhbFBhcnRxAH4ABEwADG5hbWVzcGFjZVVSSXEAfgAETAAGcHJlZml4cQB+AAR4cHQAGk9TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNldAAAcQB+ACR0B3RyTzBBQlhOeUFESnZjbWN1YTNWaGJHa3VjbWxqWlM1cmMySXViV1Z6YzJGbmFXNW5Ma3BoZG1GVFpYSjJhV05sUkdWbWFXNXBkR2x2YnJibkRWOUJOR3p4QWdBQlRBQVJjMlZ5ZG1salpVbHVkR1Z5Wm1GalpYTjBBQkJNYW1GMllTOTFkR2xzTDB4cGMzUTdlSElBTG05eVp5NXJkV0ZzYVM1eWFXTmxMbXR6WWk1dFpYTnpZV2RwYm1jdVUyVnlkbWxqWlVSbFptbHVhWFJwYjI0QW13SlBXTjBwZmdJQURVd0FDMkoxYzFObFkzVnlhWFI1ZEFBVFRHcGhkbUV2YkdGdVp5OUNiMjlzWldGdU8wd0FEMk55WldSbGJuUnBZV3h6Vkhsd1pYUUFURXh2Y21jdmEzVmhiR2t2Y21salpTOWpiM0psTDNObFkzVnlhWFI1TDJOeVpXUmxiblJwWVd4ekwwTnlaV1JsYm5ScFlXeHpVMjkxY21ObEpFTnlaV1JsYm5ScFlXeHpWSGx3WlR0TUFCQnNiMk5oYkZObGNuWnBZMlZPWVcxbGRBQVNUR3BoZG1FdmJHRnVaeTlUZEhKcGJtYzdUQUFYYldWemMyRm5aVVY0WTJWd2RHbHZia2hoYm1Sc1pYSnhBSDRBQlV3QURHMXBiR3hwYzFSdlRHbDJaWFFBRUV4cVlYWmhMMnhoYm1jdlRHOXVaenRNQUFod2NtbHZjbWwwZVhRQUUweHFZWFpoTDJ4aGJtY3ZTVzUwWldkbGNqdE1BQVZ4ZFdWMVpYRUFmZ0FEVEFBTmNtVjBjbmxCZEhSbGJYQjBjM0VBZmdBSFRBQUhjMlZ5ZG1salpYUUFFa3hxWVhaaEwyeGhibWN2VDJKcVpXTjBPMHdBRDNObGNuWnBZMlZGYm1SUWIybHVkSFFBRGt4cVlYWmhMMjVsZEM5VlVrdzdUQUFUYzJWeWRtbGpaVTVoYldWVGNHRmpaVlZTU1hFQWZnQUZUQUFRYzJWeWRtbGpaVTVoYldWemNHRmpaWEVBZmdBRlRBQUxjMlZ5ZG1salpWQmhkR2h4QUg0QUJYaHdjM0lBRVdwaGRtRXViR0Z1Wnk1Q2IyOXNaV0Z1elNCeWdOV2MrdTRDQUFGYUFBVjJZV3gxWlhod0FYQjBBQnBQVTBOaFkyaGxUbTkwYVdacFkyRjBhVzl1VTJWeWRtbGpaWFFBVFc5eVp5NXJkV0ZzYVM1eWFXTmxMbXR6WWk1dFpYTnpZV2RwYm1jdVpYaGpaWEIwYVc5dWFHRnVaR3hwYm1jdVJHVm1ZWFZzZEUxbGMzTmhaMlZGZUdObGNIUnBiMjVJWVc1a2JHVnljM0lBRG1waGRtRXViR0Z1Wnk1TWIyNW5PNHZra015UEk5OENBQUZLQUFWMllXeDFaWGh5QUJCcVlYWmhMbXhoYm1jdVRuVnRZbVZ5aHF5VkhRdVU0SXNDQUFCNGNQLy8vLy8vLy8vL2MzSUFFV3BoZG1FdWJHRnVaeTVKYm5SbFoyVnlFdUtncFBlQmh6Z0NBQUZKQUFWMllXeDFaWGh4QUg0QUVBQUFBQU56Y1FCK0FBc0FjUUIrQUJOd2MzSUFER3BoZG1FdWJtVjBMbFZTVEpZbE56WWEvT1J5QXdBSFNRQUlhR0Z6YUVOdlpHVkpBQVJ3YjNKMFRBQUpZWFYwYUc5eWFYUjVjUUIrQUFWTUFBUm1hV3hsY1FCK0FBVk1BQVJvYjNOMGNRQitBQVZNQUFod2NtOTBiMk52YkhFQWZnQUZUQUFEY21WbWNRQitBQVY0Y1AvLy8vOEFBQitRZEFBT2JHOWpZV3hvYjNOME9qZ3dPREIwQUNzdmEzSXRaR1YyTDNKbGJXOTBhVzVuTDA5VFEyRmphR1ZPYjNScFptbGpZWFJwYjI1VFpYSjJhV05sZEFBSmJHOWpZV3hvYjNOMGRBQUVhSFIwY0hCNGRBQUFkQUFHVkZKQlZrVk1kQUFCTDNOeUFCTnFZWFpoTG5WMGFXd3VRWEp5WVhsTWFYTjBlSUhTSFpuSFlaMERBQUZKQUFSemFYcGxlSEFBQUFBRmR3UUFBQUFLZEFBemIzSm5MbXQxWVd4cExuSnBZMlV1YTNOaUxtMWxjM05oWjJsdVp5';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 432    FOR UPDATE;        
    buffer := 'NXpaWEoyYVdObExrdFRRa3BoZG1GVFpYSjJhV05sZEFBOFkyOXRMbTl3Wlc1emVXMXdhRzl1ZVM1dmMyTmhZMmhsTG1KaGMyVXVaWFpsYm5SekxrTmhZMmhsUlc1MGNubEZkbVZ1ZEV4cGMzUmxibVZ5ZEFBM1kyOXRMbTl3Wlc1emVXMXdhRzl1ZVM1dmMyTmhZMmhsTG1KaGMyVXVaWFpsYm5SekxrTmhZMmhsUlhabGJuUk1hWE4wWlc1bGNuUUFGMnBoZG1FdWRYUnBiQzVGZG1WdWRFeHBjM1JsYm1WeWRBQXNZMjl0TG05d1pXNXplVzF3YUc5dWVTNXZjMk5oWTJobExtSmhjMlV1VEdsbVpXTjVZMnhsUVhkaGNtVjR0AA0xMjkuMTg2Ljc5LjQ1c3IAMm9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuSmF2YVNlcnZpY2VEZWZpbml0aW9utucNX0E0bPECAAFMABFzZXJ2aWNlSW50ZXJmYWNlc3QAEExqYXZhL3V0aWwvTGlzdDt4cgAub3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5TZXJ2aWNlRGVmaW5pdGlvbgCbAk9Y3Sl+AgANTAALYnVzU2VjdXJpdHlxAH4AE0wAD2NyZWRlbnRpYWxzVHlwZXQATExvcmcva3VhbGkvcmljZS9jb3JlL3NlY3VyaXR5L2NyZWRlbnRpYWxzL0NyZWRlbnRpYWxzU291cmNlJENyZWRlbnRpYWxzVHlwZTtMABBsb2NhbFNlcnZpY2VOYW1lcQB+AARMABdtZXNzYWdlRXhjZXB0aW9uSGFuZGxlcnEAfgAETAAMbWlsbGlzVG9MaXZlcQB+ABVMAAhwcmlvcml0eXEAfgAUTAAFcXVldWVxAH4AE0wADXJldHJ5QXR0ZW1wdHNxAH4AFEwAB3NlcnZpY2V0ABJMamF2YS9sYW5nL09iamVjdDtMAA9zZXJ2aWNlRW5kUG9pbnR0AA5MamF2YS9uZXQvVVJMO0wAE3NlcnZpY2VOYW1lU3BhY2VVUklxAH4ABEwAEHNlcnZpY2VOYW1lc3BhY2VxAH4ABEwAC3NlcnZpY2VQYXRocQB+AAR4cHNxAH4AGQFwdAAaT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2V0AE1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLmV4Y2VwdGlvbmhhbmRsaW5nLkRlZmF1bHRNZXNzYWdlRXhjZXB0aW9uSGFuZGxlcnNxAH4AH///////////c3EAfgAcAAAAA3NxAH4AGQBxAH4AMnBzcgAMamF2YS5uZXQuVVJMliU3Nhr85HIDAAdJAAhoYXNoQ29kZUkABHBvcnRMAAlhdXRob3JpdHlxAH4ABEwABGZpbGVxAH4ABEwABGhvc3RxAH4ABEwACHByb3RvY29scQB+AARMAANyZWZxAH4ABHhwaRuioAAAH5B0AA5sb2NhbGhvc3Q6ODA4MHQAKy9rci1kZXYvcmVtb3RpbmcvT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2V0AAlsb2NhbGhvc3R0AARodHRwcHh0AAB0AAZUUkFWRUx0AAEvc3IAE2phdmEudXRpbC5BcnJheUxpc3R4gdIdmcdhnQMAAUkABHNpemV4cAAAAAV3BAAAAAp0ADNvcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLnNlcnZpY2UuS1NCSmF2YVNlcnZpY2V0ADxjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5ldmVudHMuQ2FjaGVFbnRyeUV2ZW50TGlzdGVuZXJ0ADdjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5ldmVudHMuQ2FjaGVFdmVudExpc3RlbmVydAAXamF2YS51dGlsLkV2ZW50TGlzdGVuZXJ0ACxjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5MaWZlY3ljbGVBd2FyZXhxAH4AI3QABlRSQVZFTA==';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
INSERT INTO KRSB_MSG_PYLD_T (MSG_PYLD,MSG_QUE_ID)
  VALUES (EMPTY_CLOB(),433)
/
-- Length: 6060
-- Chunks: 2
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 433    FOR UPDATE;        
    buffer := 'rO0ABXNyAC1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLkFzeW5jaHJvbm91c0NhbGzxnQ5Y0t0lZQIAB1oAFWlnbm9yZVN0b3JlQW5kRm9yd2FyZFsACWFyZ3VtZW50c3QAE1tMamF2YS9sYW5nL09iamVjdDtMAAhjYWxsYmFja3QAM0xvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL0FzeW5jaHJvbm91c0NhbGxiYWNrO0wAB2NvbnRleHR0ABZMamF2YS9pby9TZXJpYWxpemFibGU7TAAKbWV0aG9kTmFtZXQAEkxqYXZhL2xhbmcvU3RyaW5nO1sACnBhcmFtVHlwZXN0ABJbTGphdmEvbGFuZy9DbGFzcztMAAtzZXJ2aWNlSW5mb3QAKkxvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL1NlcnZpY2VJbmZvO3hwAHVyABNbTGphdmEubGFuZy5PYmplY3Q7kM5YnxBzKWwCAAB4cAAAAAFzcgBDY29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLnBsdWdpbnMuY2x1c3RlcnN1cHBvcnQuQ2x1c3Rlck5vdGlmaWNhdGlvbhY+6VckvG9pAgACSQAEdHlwZUwABGRhdGFxAH4AA3hwAAAAAnQAEERvY3VtZW50VHlwZU5hbWVwcHQABmludm9rZXVyABJbTGphdmEubGFuZy5DbGFzczurFteuy81amQIAAHhwAAAAAXZyABRqYXZhLmlvLlNlcmlhbGl6YWJsZRCbYQ3Xm2TtAgAAeHBzcgAob3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5TZXJ2aWNlSW5mb8UXI7YpBLe6AgALTAAFYWxpdmV0ABNMamF2YS9sYW5nL0Jvb2xlYW47TAAUZW5kcG9pbnRBbHRlcm5hdGVVcmxxAH4ABEwAC2VuZHBvaW50VXJscQB+AARMAApsb2NrVmVyTmJydAATTGphdmEvbGFuZy9JbnRlZ2VyO0wADm1lc3NhZ2VFbnRyeUlkdAAQTGphdmEvbGFuZy9Mb25nO0wABXFuYW1ldAAbTGphdmF4L3htbC9uYW1lc3BhY2UvUU5hbWU7TAAac2VyaWFsaXplZFNlcnZpY2VOYW1lc3BhY2VxAH4ABEwACHNlcnZlcklwcQB+AARMABFzZXJ2aWNlRGVmaW5pdGlvbnQAMExvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL1NlcnZpY2VEZWZpbml0aW9uO0wAC3NlcnZpY2VOYW1lcQB+AARMABBzZXJ2aWNlTmFtZXNwYWNlcQB+AAR4cHNyABFqYXZhLmxhbmcuQm9vbGVhbs0gcoDVnPruAgABWgAFdmFsdWV4cAFwdABAaHR0cDovL2xvY2FsaG9zdDo4MDgwL2tyLWRldi9yZW1vdGluZy9PU0NhY2hlTm90aWZpY2F0aW9uU2VydmljZXNyABFqYXZhLmxhbmcuSW50ZWdlchLioKT3gYc4AgABSQAFdmFsdWV4cgAQamF2YS5sYW5nLk51bWJlcoaslR0LlOCLAgAAeHAAAAABc3IADmphdmEubGFuZy5Mb25nO4vkkMyPI98CAAFKAAV2YWx1ZXhxAH4AHQAAAAAAAA99c3IAGWphdmF4LnhtbC5uYW1lc3BhY2UuUU5hbWWBbagt/DvdbAIAA0wACWxvY2FsUGFydHEAfgAETAAMbmFtZXNwYWNlVVJJcQB+AARMAAZwcmVmaXhxAH4ABHhwdAAaT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2V0AABxAH4AJHQHdHJPMEFCWE55QURKdmNtY3VhM1ZoYkdrdWNtbGpaUzVyYzJJdWJXVnpjMkZuYVc1bkxrcGhkbUZUWlhKMmFXTmxSR1ZtYVc1cGRHbHZicmJuRFY5Qk5HenhBZ0FCVEFBUmMyVnlkbWxqWlVsdWRHVnlabUZqWlhOMEFCQk1hbUYyWVM5MWRHbHNMMHhwYzNRN2VISUFMbTl5Wnk1cmRXRnNhUzV5YVdObExtdHpZaTV0WlhOellXZHBibWN1VTJWeWRtbGpaVVJsWm1sdWFYUnBiMjRBbXdKUFdOMHBmZ0lBRFV3QUMySjFjMU5sWTNWeWFYUjVkQUFUVEdwaGRtRXZiR0Z1Wnk5Q2IyOXNaV0Z1TzB3QUQyTnlaV1JsYm5ScFlXeHpWSGx3WlhRQVRFeHZjbWN2YTNWaGJHa3ZjbWxqWlM5amIzSmxMM05sWTNWeWFYUjVMMk55WldSbGJuUnBZV3h6TDBOeVpXUmxiblJwWVd4elUyOTFjbU5sSkVOeVpXUmxiblJwWVd4elZIbHdaVHRNQUJCc2IyTmhiRk5sY25acFkyVk9ZVzFsZEFBU1RHcGhkbUV2YkdGdVp5OVRkSEpwYm1jN1RBQVhiV1Z6YzJGblpVVjRZMlZ3ZEdsdmJraGhibVJzWlhKeEFINEFCVXdBREcxcGJHeHBjMVJ2VEdsMlpYUUFFRXhxWVhaaEwyeGhibWN2VEc5dVp6dE1BQWh3Y21sdmNtbDBlWFFBRTB4cVlYWmhMMnhoYm1jdlNXNTBaV2RsY2p0TUFBVnhkV1YxWlhFQWZnQURUQUFOY21WMGNubEJkSFJsYlhCMGMzRUFmZ0FIVEFBSGMyVnlkbWxqWlhRQUVreHFZWFpoTDJ4aGJtY3ZUMkpxWldOME8wd0FEM05sY25acFkyVkZibVJRYjJsdWRIUUFEa3hxWVhaaEwyNWxkQzlWVWt3N1RBQVRjMlZ5ZG1salpVNWhiV1ZUY0dGalpWVlNTWEVBZmdBRlRBQVFjMlZ5ZG1salpVNWhiV1Z6Y0dGalpYRUFmZ0FGVEFBTGMyVnlkbWxqWlZCaGRHaHhBSDRBQlhod2MzSUFFV3BoZG1FdWJHRnVaeTVDYjI5c1pXRnV6U0J5Z05XYyt1NENBQUZhQUFWMllXeDFaWGh3QVhCMEFCcFBVME5oWTJobFRtOTBhV1pwWTJGMGFXOXVVMlZ5ZG1salpYUUFUVzl5Wnk1cmRXRnNhUzV5YVdObExtdHpZaTV0WlhOellXZHBibWN1WlhoalpYQjBhVzl1YUdGdVpHeHBibWN1UkdWbVlYVnNkRTFsYzNOaFoyVkZlR05sY0hScGIyNUlZVzVrYkdWeWMzSUFEbXBoZG1FdWJHRnVaeTVNYjI1bk80dmtrTXlQSTk4Q0FBRktBQVYyWVd4MVpYaHlBQkJxWVhaaExteGhibWN1VG5WdFltVnlocXlWSFF1VTRJc0NBQUI0Y1AvLy8vLy8vLy8vYzNJQUVXcGhkbUV1YkdGdVp5NUpiblJsWjJWeUV1S2dwUGVCaHpnQ0FBRkpBQVYyWVd4MVpYaHhBSDRBRUFBQUFBTnpjUUIrQUFzQWNRQitBQk53YzNJQURHcGhkbUV1Ym1WMExsVlNUSllsTnpZYS9PUnlBd0FIU1FBSWFHRnphRU52WkdWSkFBUndiM0owVEFBSllYVjBhRzl5YVhSNWNRQitBQVZNQUFSbWFXeGxjUUIrQUFWTUFBUm9iM04wY1FCK0FBVk1BQWh3Y205MGIyTnZiSEVBZmdBRlRBQURjbVZtY1FCK0FBVjRjUC8vLy84QUFCK1FkQUFPYkc5allXeG9iM04wT2pnd09EQjBBQ3N2YTNJdFpHVjJMM0psYlc5MGFXNW5MMDlUUTJGamFHVk9iM1JwWm1sallYUnBiMjVUWlhKMmFXTmxkQUFKYkc5allXeG9iM04wZEFBRWFIUjBjSEI0ZEFBQWRBQUdWRkpCVmtWTWRBQUJMM055QUJOcVlYWmhMblYwYVd3dVFYSnlZWGxNYVhOMGVJSFNIWm5IWVowREFBRkpBQVJ6YVhwbGVIQUFBQUFGZHdRQUFBQUtkQUF6YjNKbkxtdDFZV3hwTG5KcFkyVXVhM05pTG0xbGMzTmhaMmx1';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 433    FOR UPDATE;        
    buffer := 'Wnk1elpYSjJhV05sTGt0VFFrcGhkbUZUWlhKMmFXTmxkQUE4WTI5dExtOXdaVzV6ZVcxd2FHOXVlUzV2YzJOaFkyaGxMbUpoYzJVdVpYWmxiblJ6TGtOaFkyaGxSVzUwY25sRmRtVnVkRXhwYzNSbGJtVnlkQUEzWTI5dExtOXdaVzV6ZVcxd2FHOXVlUzV2YzJOaFkyaGxMbUpoYzJVdVpYWmxiblJ6TGtOaFkyaGxSWFpsYm5STWFYTjBaVzVsY25RQUYycGhkbUV1ZFhScGJDNUZkbVZ1ZEV4cGMzUmxibVZ5ZEFBc1kyOXRMbTl3Wlc1emVXMXdhRzl1ZVM1dmMyTmhZMmhsTG1KaGMyVXVUR2xtWldONVkyeGxRWGRoY21WNHQADTEyOS4xODYuNzkuNDVzcgAyb3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5KYXZhU2VydmljZURlZmluaXRpb2625w1fQTRs8QIAAUwAEXNlcnZpY2VJbnRlcmZhY2VzdAAQTGphdmEvdXRpbC9MaXN0O3hyAC5vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLlNlcnZpY2VEZWZpbml0aW9uAJsCT1jdKX4CAA1MAAtidXNTZWN1cml0eXEAfgATTAAPY3JlZGVudGlhbHNUeXBldABMTG9yZy9rdWFsaS9yaWNlL2NvcmUvc2VjdXJpdHkvY3JlZGVudGlhbHMvQ3JlZGVudGlhbHNTb3VyY2UkQ3JlZGVudGlhbHNUeXBlO0wAEGxvY2FsU2VydmljZU5hbWVxAH4ABEwAF21lc3NhZ2VFeGNlcHRpb25IYW5kbGVycQB+AARMAAxtaWxsaXNUb0xpdmVxAH4AFUwACHByaW9yaXR5cQB+ABRMAAVxdWV1ZXEAfgATTAANcmV0cnlBdHRlbXB0c3EAfgAUTAAHc2VydmljZXQAEkxqYXZhL2xhbmcvT2JqZWN0O0wAD3NlcnZpY2VFbmRQb2ludHQADkxqYXZhL25ldC9VUkw7TAATc2VydmljZU5hbWVTcGFjZVVSSXEAfgAETAAQc2VydmljZU5hbWVzcGFjZXEAfgAETAALc2VydmljZVBhdGhxAH4ABHhwc3EAfgAZAXB0ABpPU0NhY2hlTm90aWZpY2F0aW9uU2VydmljZXQATW9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuZXhjZXB0aW9uaGFuZGxpbmcuRGVmYXVsdE1lc3NhZ2VFeGNlcHRpb25IYW5kbGVyc3EAfgAf//////////9zcQB+ABwAAAADc3EAfgAZAHEAfgAycHNyAAxqYXZhLm5ldC5VUkyWJTc2GvzkcgMAB0kACGhhc2hDb2RlSQAEcG9ydEwACWF1dGhvcml0eXEAfgAETAAEZmlsZXEAfgAETAAEaG9zdHEAfgAETAAIcHJvdG9jb2xxAH4ABEwAA3JlZnEAfgAEeHBpG6KgAAAfkHQADmxvY2FsaG9zdDo4MDgwdAArL2tyLWRldi9yZW1vdGluZy9PU0NhY2hlTm90aWZpY2F0aW9uU2VydmljZXQACWxvY2FsaG9zdHQABGh0dHBweHQAAHQABlRSQVZFTHQAAS9zcgATamF2YS51dGlsLkFycmF5TGlzdHiB0h2Zx2GdAwABSQAEc2l6ZXhwAAAABXcEAAAACnQAM29yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuc2VydmljZS5LU0JKYXZhU2VydmljZXQAPGNvbS5vcGVuc3ltcGhvbnkub3NjYWNoZS5iYXNlLmV2ZW50cy5DYWNoZUVudHJ5RXZlbnRMaXN0ZW5lcnQAN2NvbS5vcGVuc3ltcGhvbnkub3NjYWNoZS5iYXNlLmV2ZW50cy5DYWNoZUV2ZW50TGlzdGVuZXJ0ABdqYXZhLnV0aWwuRXZlbnRMaXN0ZW5lcnQALGNvbS5vcGVuc3ltcGhvbnkub3NjYWNoZS5iYXNlLkxpZmVjeWNsZUF3YXJleHEAfgAjdAAGVFJBVkVM';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
INSERT INTO KRSB_MSG_PYLD_T (MSG_PYLD,MSG_QUE_ID)
  VALUES (EMPTY_CLOB(),434)
/
-- Length: 6084
-- Chunks: 2
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 434    FOR UPDATE;        
    buffer := 'rO0ABXNyAC1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLkFzeW5jaHJvbm91c0NhbGzxnQ5Y0t0lZQIAB1oAFWlnbm9yZVN0b3JlQW5kRm9yd2FyZFsACWFyZ3VtZW50c3QAE1tMamF2YS9sYW5nL09iamVjdDtMAAhjYWxsYmFja3QAM0xvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL0FzeW5jaHJvbm91c0NhbGxiYWNrO0wAB2NvbnRleHR0ABZMamF2YS9pby9TZXJpYWxpemFibGU7TAAKbWV0aG9kTmFtZXQAEkxqYXZhL2xhbmcvU3RyaW5nO1sACnBhcmFtVHlwZXN0ABJbTGphdmEvbGFuZy9DbGFzcztMAAtzZXJ2aWNlSW5mb3QAKkxvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL1NlcnZpY2VJbmZvO3hwAHVyABNbTGphdmEubGFuZy5PYmplY3Q7kM5YnxBzKWwCAAB4cAAAAAFzcgBDY29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLnBsdWdpbnMuY2x1c3RlcnN1cHBvcnQuQ2x1c3Rlck5vdGlmaWNhdGlvbhY+6VckvG9pAgACSQAEdHlwZUwABGRhdGFxAH4AA3hwAAAAAXQAIERvY3VtZW50VHlwZTpDdXJyZW50Um9vdHNJbkNhY2hlcHB0AAZpbnZva2V1cgASW0xqYXZhLmxhbmcuQ2xhc3M7qxbXrsvNWpkCAAB4cAAAAAF2cgAUamF2YS5pby5TZXJpYWxpemFibGUQm2EN15tk7QIAAHhwc3IAKG9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuU2VydmljZUluZm/FFyO2KQS3ugIAC0wABWFsaXZldAATTGphdmEvbGFuZy9Cb29sZWFuO0wAFGVuZHBvaW50QWx0ZXJuYXRlVXJscQB+AARMAAtlbmRwb2ludFVybHEAfgAETAAKbG9ja1Zlck5icnQAE0xqYXZhL2xhbmcvSW50ZWdlcjtMAA5tZXNzYWdlRW50cnlJZHQAEExqYXZhL2xhbmcvTG9uZztMAAVxbmFtZXQAG0xqYXZheC94bWwvbmFtZXNwYWNlL1FOYW1lO0wAGnNlcmlhbGl6ZWRTZXJ2aWNlTmFtZXNwYWNlcQB+AARMAAhzZXJ2ZXJJcHEAfgAETAARc2VydmljZURlZmluaXRpb250ADBMb3JnL2t1YWxpL3JpY2Uva3NiL21lc3NhZ2luZy9TZXJ2aWNlRGVmaW5pdGlvbjtMAAtzZXJ2aWNlTmFtZXEAfgAETAAQc2VydmljZU5hbWVzcGFjZXEAfgAEeHBzcgARamF2YS5sYW5nLkJvb2xlYW7NIHKA1Zz67gIAAVoABXZhbHVleHABcHQAQGh0dHA6Ly9sb2NhbGhvc3Q6ODA4MC9rci1kZXYvcmVtb3RpbmcvT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2VzcgARamF2YS5sYW5nLkludGVnZXIS4qCk94GHOAIAAUkABXZhbHVleHIAEGphdmEubGFuZy5OdW1iZXKGrJUdC5TgiwIAAHhwAAAAAXNyAA5qYXZhLmxhbmcuTG9uZzuL5JDMjyPfAgABSgAFdmFsdWV4cQB+AB0AAAAAAAAPfXNyABlqYXZheC54bWwubmFtZXNwYWNlLlFOYW1lgW2oLfw73WwCAANMAAlsb2NhbFBhcnRxAH4ABEwADG5hbWVzcGFjZVVSSXEAfgAETAAGcHJlZml4cQB+AAR4cHQAGk9TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNldAAAcQB+ACR0B3RyTzBBQlhOeUFESnZjbWN1YTNWaGJHa3VjbWxqWlM1cmMySXViV1Z6YzJGbmFXNW5Ma3BoZG1GVFpYSjJhV05sUkdWbWFXNXBkR2x2YnJibkRWOUJOR3p4QWdBQlRBQVJjMlZ5ZG1salpVbHVkR1Z5Wm1GalpYTjBBQkJNYW1GMllTOTFkR2xzTDB4cGMzUTdlSElBTG05eVp5NXJkV0ZzYVM1eWFXTmxMbXR6WWk1dFpYTnpZV2RwYm1jdVUyVnlkbWxqWlVSbFptbHVhWFJwYjI0QW13SlBXTjBwZmdJQURVd0FDMkoxYzFObFkzVnlhWFI1ZEFBVFRHcGhkbUV2YkdGdVp5OUNiMjlzWldGdU8wd0FEMk55WldSbGJuUnBZV3h6Vkhsd1pYUUFURXh2Y21jdmEzVmhiR2t2Y21salpTOWpiM0psTDNObFkzVnlhWFI1TDJOeVpXUmxiblJwWVd4ekwwTnlaV1JsYm5ScFlXeHpVMjkxY21ObEpFTnlaV1JsYm5ScFlXeHpWSGx3WlR0TUFCQnNiMk5oYkZObGNuWnBZMlZPWVcxbGRBQVNUR3BoZG1FdmJHRnVaeTlUZEhKcGJtYzdUQUFYYldWemMyRm5aVVY0WTJWd2RHbHZia2hoYm1Sc1pYSnhBSDRBQlV3QURHMXBiR3hwYzFSdlRHbDJaWFFBRUV4cVlYWmhMMnhoYm1jdlRHOXVaenRNQUFod2NtbHZjbWwwZVhRQUUweHFZWFpoTDJ4aGJtY3ZTVzUwWldkbGNqdE1BQVZ4ZFdWMVpYRUFmZ0FEVEFBTmNtVjBjbmxCZEhSbGJYQjBjM0VBZmdBSFRBQUhjMlZ5ZG1salpYUUFFa3hxWVhaaEwyeGhibWN2VDJKcVpXTjBPMHdBRDNObGNuWnBZMlZGYm1SUWIybHVkSFFBRGt4cVlYWmhMMjVsZEM5VlVrdzdUQUFUYzJWeWRtbGpaVTVoYldWVGNHRmpaVlZTU1hFQWZnQUZUQUFRYzJWeWRtbGpaVTVoYldWemNHRmpaWEVBZmdBRlRBQUxjMlZ5ZG1salpWQmhkR2h4QUg0QUJYaHdjM0lBRVdwaGRtRXViR0Z1Wnk1Q2IyOXNaV0Z1elNCeWdOV2MrdTRDQUFGYUFBVjJZV3gxWlhod0FYQjBBQnBQVTBOaFkyaGxUbTkwYVdacFkyRjBhVzl1VTJWeWRtbGpaWFFBVFc5eVp5NXJkV0ZzYVM1eWFXTmxMbXR6WWk1dFpYTnpZV2RwYm1jdVpYaGpaWEIwYVc5dWFHRnVaR3hwYm1jdVJHVm1ZWFZzZEUxbGMzTmhaMlZGZUdObGNIUnBiMjVJWVc1a2JHVnljM0lBRG1waGRtRXViR0Z1Wnk1TWIyNW5PNHZra015UEk5OENBQUZLQUFWMllXeDFaWGh5QUJCcVlYWmhMbXhoYm1jdVRuVnRZbVZ5aHF5VkhRdVU0SXNDQUFCNGNQLy8vLy8vLy8vL2MzSUFFV3BoZG1FdWJHRnVaeTVKYm5SbFoyVnlFdUtncFBlQmh6Z0NBQUZKQUFWMllXeDFaWGh4QUg0QUVBQUFBQU56Y1FCK0FBc0FjUUIrQUJOd2MzSUFER3BoZG1FdWJtVjBMbFZTVEpZbE56WWEvT1J5QXdBSFNRQUlhR0Z6YUVOdlpHVkpBQVJ3YjNKMFRBQUpZWFYwYUc5eWFYUjVjUUIrQUFWTUFBUm1hV3hsY1FCK0FBVk1BQVJvYjNOMGNRQitBQVZNQUFod2NtOTBiMk52YkhFQWZnQUZUQUFEY21WbWNRQitBQVY0Y1AvLy8vOEFBQitRZEFBT2JHOWpZV3hvYjNOME9qZ3dPREIwQUNzdmEzSXRaR1YyTDNKbGJXOTBhVzVuTDA5VFEyRmphR1ZPYjNScFptbGpZWFJwYjI1VFpYSjJhV05sZEFBSmJHOWpZV3hvYjNOMGRBQUVhSFIwY0hCNGRBQUFkQUFHVkZKQlZrVk1kQUFCTDNOeUFCTnFZWFpoTG5WMGFXd3VRWEp5WVhsTWFYTjBlSUhTSFpuSFlaMERBQUZKQUFSemFYcGxlSEFBQUFBRmR3UUFBQUFLZEFBemIzSm5MbXQxWVd4cExuSnBZMlV1';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 434    FOR UPDATE;        
    buffer := 'YTNOaUxtMWxjM05oWjJsdVp5NXpaWEoyYVdObExrdFRRa3BoZG1GVFpYSjJhV05sZEFBOFkyOXRMbTl3Wlc1emVXMXdhRzl1ZVM1dmMyTmhZMmhsTG1KaGMyVXVaWFpsYm5SekxrTmhZMmhsUlc1MGNubEZkbVZ1ZEV4cGMzUmxibVZ5ZEFBM1kyOXRMbTl3Wlc1emVXMXdhRzl1ZVM1dmMyTmhZMmhsTG1KaGMyVXVaWFpsYm5SekxrTmhZMmhsUlhabGJuUk1hWE4wWlc1bGNuUUFGMnBoZG1FdWRYUnBiQzVGZG1WdWRFeHBjM1JsYm1WeWRBQXNZMjl0TG05d1pXNXplVzF3YUc5dWVTNXZjMk5oWTJobExtSmhjMlV1VEdsbVpXTjVZMnhsUVhkaGNtVjR0AA0xMjkuMTg2Ljc5LjQ1c3IAMm9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuSmF2YVNlcnZpY2VEZWZpbml0aW9utucNX0E0bPECAAFMABFzZXJ2aWNlSW50ZXJmYWNlc3QAEExqYXZhL3V0aWwvTGlzdDt4cgAub3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5TZXJ2aWNlRGVmaW5pdGlvbgCbAk9Y3Sl+AgANTAALYnVzU2VjdXJpdHlxAH4AE0wAD2NyZWRlbnRpYWxzVHlwZXQATExvcmcva3VhbGkvcmljZS9jb3JlL3NlY3VyaXR5L2NyZWRlbnRpYWxzL0NyZWRlbnRpYWxzU291cmNlJENyZWRlbnRpYWxzVHlwZTtMABBsb2NhbFNlcnZpY2VOYW1lcQB+AARMABdtZXNzYWdlRXhjZXB0aW9uSGFuZGxlcnEAfgAETAAMbWlsbGlzVG9MaXZlcQB+ABVMAAhwcmlvcml0eXEAfgAUTAAFcXVldWVxAH4AE0wADXJldHJ5QXR0ZW1wdHNxAH4AFEwAB3NlcnZpY2V0ABJMamF2YS9sYW5nL09iamVjdDtMAA9zZXJ2aWNlRW5kUG9pbnR0AA5MamF2YS9uZXQvVVJMO0wAE3NlcnZpY2VOYW1lU3BhY2VVUklxAH4ABEwAEHNlcnZpY2VOYW1lc3BhY2VxAH4ABEwAC3NlcnZpY2VQYXRocQB+AAR4cHNxAH4AGQFwdAAaT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2V0AE1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLmV4Y2VwdGlvbmhhbmRsaW5nLkRlZmF1bHRNZXNzYWdlRXhjZXB0aW9uSGFuZGxlcnNxAH4AH///////////c3EAfgAcAAAAA3NxAH4AGQBxAH4AMnBzcgAMamF2YS5uZXQuVVJMliU3Nhr85HIDAAdJAAhoYXNoQ29kZUkABHBvcnRMAAlhdXRob3JpdHlxAH4ABEwABGZpbGVxAH4ABEwABGhvc3RxAH4ABEwACHByb3RvY29scQB+AARMAANyZWZxAH4ABHhwaRuioAAAH5B0AA5sb2NhbGhvc3Q6ODA4MHQAKy9rci1kZXYvcmVtb3RpbmcvT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2V0AAlsb2NhbGhvc3R0AARodHRwcHh0AAB0AAZUUkFWRUx0AAEvc3IAE2phdmEudXRpbC5BcnJheUxpc3R4gdIdmcdhnQMAAUkABHNpemV4cAAAAAV3BAAAAAp0ADNvcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLnNlcnZpY2UuS1NCSmF2YVNlcnZpY2V0ADxjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5ldmVudHMuQ2FjaGVFbnRyeUV2ZW50TGlzdGVuZXJ0ADdjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5ldmVudHMuQ2FjaGVFdmVudExpc3RlbmVydAAXamF2YS51dGlsLkV2ZW50TGlzdGVuZXJ0ACxjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5MaWZlY3ljbGVBd2FyZXhxAH4AI3QABlRSQVZFTA==';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
INSERT INTO KRSB_MSG_PYLD_T (MSG_PYLD,MSG_QUE_ID)
  VALUES (EMPTY_CLOB(),435)
/
-- Length: 6088
-- Chunks: 2
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 435    FOR UPDATE;        
    buffer := 'rO0ABXNyAC1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLkFzeW5jaHJvbm91c0NhbGzxnQ5Y0t0lZQIAB1oAFWlnbm9yZVN0b3JlQW5kRm9yd2FyZFsACWFyZ3VtZW50c3QAE1tMamF2YS9sYW5nL09iamVjdDtMAAhjYWxsYmFja3QAM0xvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL0FzeW5jaHJvbm91c0NhbGxiYWNrO0wAB2NvbnRleHR0ABZMamF2YS9pby9TZXJpYWxpemFibGU7TAAKbWV0aG9kTmFtZXQAEkxqYXZhL2xhbmcvU3RyaW5nO1sACnBhcmFtVHlwZXN0ABJbTGphdmEvbGFuZy9DbGFzcztMAAtzZXJ2aWNlSW5mb3QAKkxvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL1NlcnZpY2VJbmZvO3hwAHVyABNbTGphdmEubGFuZy5PYmplY3Q7kM5YnxBzKWwCAAB4cAAAAAFzcgBDY29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLnBsdWdpbnMuY2x1c3RlcnN1cHBvcnQuQ2x1c3Rlck5vdGlmaWNhdGlvbhY+6VckvG9pAgACSQAEdHlwZUwABGRhdGFxAH4AA3hwAAAAAnQAI0RvY3VtZW50VHlwZVJ1bGVDYWNoZTpLdWFsaURvY3VtZW50cHB0AAZpbnZva2V1cgASW0xqYXZhLmxhbmcuQ2xhc3M7qxbXrsvNWpkCAAB4cAAAAAF2cgAUamF2YS5pby5TZXJpYWxpemFibGUQm2EN15tk7QIAAHhwc3IAKG9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuU2VydmljZUluZm/FFyO2KQS3ugIAC0wABWFsaXZldAATTGphdmEvbGFuZy9Cb29sZWFuO0wAFGVuZHBvaW50QWx0ZXJuYXRlVXJscQB+AARMAAtlbmRwb2ludFVybHEAfgAETAAKbG9ja1Zlck5icnQAE0xqYXZhL2xhbmcvSW50ZWdlcjtMAA5tZXNzYWdlRW50cnlJZHQAEExqYXZhL2xhbmcvTG9uZztMAAVxbmFtZXQAG0xqYXZheC94bWwvbmFtZXNwYWNlL1FOYW1lO0wAGnNlcmlhbGl6ZWRTZXJ2aWNlTmFtZXNwYWNlcQB+AARMAAhzZXJ2ZXJJcHEAfgAETAARc2VydmljZURlZmluaXRpb250ADBMb3JnL2t1YWxpL3JpY2Uva3NiL21lc3NhZ2luZy9TZXJ2aWNlRGVmaW5pdGlvbjtMAAtzZXJ2aWNlTmFtZXEAfgAETAAQc2VydmljZU5hbWVzcGFjZXEAfgAEeHBzcgARamF2YS5sYW5nLkJvb2xlYW7NIHKA1Zz67gIAAVoABXZhbHVleHABcHQAQGh0dHA6Ly9sb2NhbGhvc3Q6ODA4MC9rci1kZXYvcmVtb3RpbmcvT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2VzcgARamF2YS5sYW5nLkludGVnZXIS4qCk94GHOAIAAUkABXZhbHVleHIAEGphdmEubGFuZy5OdW1iZXKGrJUdC5TgiwIAAHhwAAAAAXNyAA5qYXZhLmxhbmcuTG9uZzuL5JDMjyPfAgABSgAFdmFsdWV4cQB+AB0AAAAAAAAPfXNyABlqYXZheC54bWwubmFtZXNwYWNlLlFOYW1lgW2oLfw73WwCAANMAAlsb2NhbFBhcnRxAH4ABEwADG5hbWVzcGFjZVVSSXEAfgAETAAGcHJlZml4cQB+AAR4cHQAGk9TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNldAAAcQB+ACR0B3RyTzBBQlhOeUFESnZjbWN1YTNWaGJHa3VjbWxqWlM1cmMySXViV1Z6YzJGbmFXNW5Ma3BoZG1GVFpYSjJhV05sUkdWbWFXNXBkR2x2YnJibkRWOUJOR3p4QWdBQlRBQVJjMlZ5ZG1salpVbHVkR1Z5Wm1GalpYTjBBQkJNYW1GMllTOTFkR2xzTDB4cGMzUTdlSElBTG05eVp5NXJkV0ZzYVM1eWFXTmxMbXR6WWk1dFpYTnpZV2RwYm1jdVUyVnlkbWxqWlVSbFptbHVhWFJwYjI0QW13SlBXTjBwZmdJQURVd0FDMkoxYzFObFkzVnlhWFI1ZEFBVFRHcGhkbUV2YkdGdVp5OUNiMjlzWldGdU8wd0FEMk55WldSbGJuUnBZV3h6Vkhsd1pYUUFURXh2Y21jdmEzVmhiR2t2Y21salpTOWpiM0psTDNObFkzVnlhWFI1TDJOeVpXUmxiblJwWVd4ekwwTnlaV1JsYm5ScFlXeHpVMjkxY21ObEpFTnlaV1JsYm5ScFlXeHpWSGx3WlR0TUFCQnNiMk5oYkZObGNuWnBZMlZPWVcxbGRBQVNUR3BoZG1FdmJHRnVaeTlUZEhKcGJtYzdUQUFYYldWemMyRm5aVVY0WTJWd2RHbHZia2hoYm1Sc1pYSnhBSDRBQlV3QURHMXBiR3hwYzFSdlRHbDJaWFFBRUV4cVlYWmhMMnhoYm1jdlRHOXVaenRNQUFod2NtbHZjbWwwZVhRQUUweHFZWFpoTDJ4aGJtY3ZTVzUwWldkbGNqdE1BQVZ4ZFdWMVpYRUFmZ0FEVEFBTmNtVjBjbmxCZEhSbGJYQjBjM0VBZmdBSFRBQUhjMlZ5ZG1salpYUUFFa3hxWVhaaEwyeGhibWN2VDJKcVpXTjBPMHdBRDNObGNuWnBZMlZGYm1SUWIybHVkSFFBRGt4cVlYWmhMMjVsZEM5VlVrdzdUQUFUYzJWeWRtbGpaVTVoYldWVGNHRmpaVlZTU1hFQWZnQUZUQUFRYzJWeWRtbGpaVTVoYldWemNHRmpaWEVBZmdBRlRBQUxjMlZ5ZG1salpWQmhkR2h4QUg0QUJYaHdjM0lBRVdwaGRtRXViR0Z1Wnk1Q2IyOXNaV0Z1elNCeWdOV2MrdTRDQUFGYUFBVjJZV3gxWlhod0FYQjBBQnBQVTBOaFkyaGxUbTkwYVdacFkyRjBhVzl1VTJWeWRtbGpaWFFBVFc5eVp5NXJkV0ZzYVM1eWFXTmxMbXR6WWk1dFpYTnpZV2RwYm1jdVpYaGpaWEIwYVc5dWFHRnVaR3hwYm1jdVJHVm1ZWFZzZEUxbGMzTmhaMlZGZUdObGNIUnBiMjVJWVc1a2JHVnljM0lBRG1waGRtRXViR0Z1Wnk1TWIyNW5PNHZra015UEk5OENBQUZLQUFWMllXeDFaWGh5QUJCcVlYWmhMbXhoYm1jdVRuVnRZbVZ5aHF5VkhRdVU0SXNDQUFCNGNQLy8vLy8vLy8vL2MzSUFFV3BoZG1FdWJHRnVaeTVKYm5SbFoyVnlFdUtncFBlQmh6Z0NBQUZKQUFWMllXeDFaWGh4QUg0QUVBQUFBQU56Y1FCK0FBc0FjUUIrQUJOd2MzSUFER3BoZG1FdWJtVjBMbFZTVEpZbE56WWEvT1J5QXdBSFNRQUlhR0Z6YUVOdlpHVkpBQVJ3YjNKMFRBQUpZWFYwYUc5eWFYUjVjUUIrQUFWTUFBUm1hV3hsY1FCK0FBVk1BQVJvYjNOMGNRQitBQVZNQUFod2NtOTBiMk52YkhFQWZnQUZUQUFEY21WbWNRQitBQVY0Y1AvLy8vOEFBQitRZEFBT2JHOWpZV3hvYjNOME9qZ3dPREIwQUNzdmEzSXRaR1YyTDNKbGJXOTBhVzVuTDA5VFEyRmphR1ZPYjNScFptbGpZWFJwYjI1VFpYSjJhV05sZEFBSmJHOWpZV3hvYjNOMGRBQUVhSFIwY0hCNGRBQUFkQUFHVkZKQlZrVk1kQUFCTDNOeUFCTnFZWFpoTG5WMGFXd3VRWEp5WVhsTWFYTjBlSUhTSFpuSFlaMERBQUZKQUFSemFYcGxlSEFBQUFBRmR3UUFBQUFLZEFBemIzSm5MbXQxWVd4cExuSnBZ';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 435    FOR UPDATE;        
    buffer := 'MlV1YTNOaUxtMWxjM05oWjJsdVp5NXpaWEoyYVdObExrdFRRa3BoZG1GVFpYSjJhV05sZEFBOFkyOXRMbTl3Wlc1emVXMXdhRzl1ZVM1dmMyTmhZMmhsTG1KaGMyVXVaWFpsYm5SekxrTmhZMmhsUlc1MGNubEZkbVZ1ZEV4cGMzUmxibVZ5ZEFBM1kyOXRMbTl3Wlc1emVXMXdhRzl1ZVM1dmMyTmhZMmhsTG1KaGMyVXVaWFpsYm5SekxrTmhZMmhsUlhabGJuUk1hWE4wWlc1bGNuUUFGMnBoZG1FdWRYUnBiQzVGZG1WdWRFeHBjM1JsYm1WeWRBQXNZMjl0TG05d1pXNXplVzF3YUc5dWVTNXZjMk5oWTJobExtSmhjMlV1VEdsbVpXTjVZMnhsUVhkaGNtVjR0AA0xMjkuMTg2Ljc5LjQ1c3IAMm9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuSmF2YVNlcnZpY2VEZWZpbml0aW9utucNX0E0bPECAAFMABFzZXJ2aWNlSW50ZXJmYWNlc3QAEExqYXZhL3V0aWwvTGlzdDt4cgAub3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5TZXJ2aWNlRGVmaW5pdGlvbgCbAk9Y3Sl+AgANTAALYnVzU2VjdXJpdHlxAH4AE0wAD2NyZWRlbnRpYWxzVHlwZXQATExvcmcva3VhbGkvcmljZS9jb3JlL3NlY3VyaXR5L2NyZWRlbnRpYWxzL0NyZWRlbnRpYWxzU291cmNlJENyZWRlbnRpYWxzVHlwZTtMABBsb2NhbFNlcnZpY2VOYW1lcQB+AARMABdtZXNzYWdlRXhjZXB0aW9uSGFuZGxlcnEAfgAETAAMbWlsbGlzVG9MaXZlcQB+ABVMAAhwcmlvcml0eXEAfgAUTAAFcXVldWVxAH4AE0wADXJldHJ5QXR0ZW1wdHNxAH4AFEwAB3NlcnZpY2V0ABJMamF2YS9sYW5nL09iamVjdDtMAA9zZXJ2aWNlRW5kUG9pbnR0AA5MamF2YS9uZXQvVVJMO0wAE3NlcnZpY2VOYW1lU3BhY2VVUklxAH4ABEwAEHNlcnZpY2VOYW1lc3BhY2VxAH4ABEwAC3NlcnZpY2VQYXRocQB+AAR4cHNxAH4AGQFwdAAaT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2V0AE1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLmV4Y2VwdGlvbmhhbmRsaW5nLkRlZmF1bHRNZXNzYWdlRXhjZXB0aW9uSGFuZGxlcnNxAH4AH///////////c3EAfgAcAAAAA3NxAH4AGQBxAH4AMnBzcgAMamF2YS5uZXQuVVJMliU3Nhr85HIDAAdJAAhoYXNoQ29kZUkABHBvcnRMAAlhdXRob3JpdHlxAH4ABEwABGZpbGVxAH4ABEwABGhvc3RxAH4ABEwACHByb3RvY29scQB+AARMAANyZWZxAH4ABHhwaRuioAAAH5B0AA5sb2NhbGhvc3Q6ODA4MHQAKy9rci1kZXYvcmVtb3RpbmcvT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2V0AAlsb2NhbGhvc3R0AARodHRwcHh0AAB0AAZUUkFWRUx0AAEvc3IAE2phdmEudXRpbC5BcnJheUxpc3R4gdIdmcdhnQMAAUkABHNpemV4cAAAAAV3BAAAAAp0ADNvcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLnNlcnZpY2UuS1NCSmF2YVNlcnZpY2V0ADxjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5ldmVudHMuQ2FjaGVFbnRyeUV2ZW50TGlzdGVuZXJ0ADdjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5ldmVudHMuQ2FjaGVFdmVudExpc3RlbmVydAAXamF2YS51dGlsLkV2ZW50TGlzdGVuZXJ0ACxjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5MaWZlY3ljbGVBd2FyZXhxAH4AI3QABlRSQVZFTA==';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
INSERT INTO KRSB_MSG_PYLD_T (MSG_PYLD,MSG_QUE_ID)
  VALUES (EMPTY_CLOB(),436)
/
-- Length: 6084
-- Chunks: 2
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 436    FOR UPDATE;        
    buffer := 'rO0ABXNyAC1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLkFzeW5jaHJvbm91c0NhbGzxnQ5Y0t0lZQIAB1oAFWlnbm9yZVN0b3JlQW5kRm9yd2FyZFsACWFyZ3VtZW50c3QAE1tMamF2YS9sYW5nL09iamVjdDtMAAhjYWxsYmFja3QAM0xvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL0FzeW5jaHJvbm91c0NhbGxiYWNrO0wAB2NvbnRleHR0ABZMamF2YS9pby9TZXJpYWxpemFibGU7TAAKbWV0aG9kTmFtZXQAEkxqYXZhL2xhbmcvU3RyaW5nO1sACnBhcmFtVHlwZXN0ABJbTGphdmEvbGFuZy9DbGFzcztMAAtzZXJ2aWNlSW5mb3QAKkxvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL1NlcnZpY2VJbmZvO3hwAHVyABNbTGphdmEubGFuZy5PYmplY3Q7kM5YnxBzKWwCAAB4cAAAAAFzcgBDY29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLnBsdWdpbnMuY2x1c3RlcnN1cHBvcnQuQ2x1c3Rlck5vdGlmaWNhdGlvbhY+6VckvG9pAgACSQAEdHlwZUwABGRhdGFxAH4AA3hwAAAAAnQAIkRvY3VtZW50VHlwZVJ1bGVDYWNoZTpSaWNlRG9jdW1lbnRwcHQABmludm9rZXVyABJbTGphdmEubGFuZy5DbGFzczurFteuy81amQIAAHhwAAAAAXZyABRqYXZhLmlvLlNlcmlhbGl6YWJsZRCbYQ3Xm2TtAgAAeHBzcgAob3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5TZXJ2aWNlSW5mb8UXI7YpBLe6AgALTAAFYWxpdmV0ABNMamF2YS9sYW5nL0Jvb2xlYW47TAAUZW5kcG9pbnRBbHRlcm5hdGVVcmxxAH4ABEwAC2VuZHBvaW50VXJscQB+AARMAApsb2NrVmVyTmJydAATTGphdmEvbGFuZy9JbnRlZ2VyO0wADm1lc3NhZ2VFbnRyeUlkdAAQTGphdmEvbGFuZy9Mb25nO0wABXFuYW1ldAAbTGphdmF4L3htbC9uYW1lc3BhY2UvUU5hbWU7TAAac2VyaWFsaXplZFNlcnZpY2VOYW1lc3BhY2VxAH4ABEwACHNlcnZlcklwcQB+AARMABFzZXJ2aWNlRGVmaW5pdGlvbnQAMExvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL1NlcnZpY2VEZWZpbml0aW9uO0wAC3NlcnZpY2VOYW1lcQB+AARMABBzZXJ2aWNlTmFtZXNwYWNlcQB+AAR4cHNyABFqYXZhLmxhbmcuQm9vbGVhbs0gcoDVnPruAgABWgAFdmFsdWV4cAFwdABAaHR0cDovL2xvY2FsaG9zdDo4MDgwL2tyLWRldi9yZW1vdGluZy9PU0NhY2hlTm90aWZpY2F0aW9uU2VydmljZXNyABFqYXZhLmxhbmcuSW50ZWdlchLioKT3gYc4AgABSQAFdmFsdWV4cgAQamF2YS5sYW5nLk51bWJlcoaslR0LlOCLAgAAeHAAAAABc3IADmphdmEubGFuZy5Mb25nO4vkkMyPI98CAAFKAAV2YWx1ZXhxAH4AHQAAAAAAAA99c3IAGWphdmF4LnhtbC5uYW1lc3BhY2UuUU5hbWWBbagt/DvdbAIAA0wACWxvY2FsUGFydHEAfgAETAAMbmFtZXNwYWNlVVJJcQB+AARMAAZwcmVmaXhxAH4ABHhwdAAaT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2V0AABxAH4AJHQHdHJPMEFCWE55QURKdmNtY3VhM1ZoYkdrdWNtbGpaUzVyYzJJdWJXVnpjMkZuYVc1bkxrcGhkbUZUWlhKMmFXTmxSR1ZtYVc1cGRHbHZicmJuRFY5Qk5HenhBZ0FCVEFBUmMyVnlkbWxqWlVsdWRHVnlabUZqWlhOMEFCQk1hbUYyWVM5MWRHbHNMMHhwYzNRN2VISUFMbTl5Wnk1cmRXRnNhUzV5YVdObExtdHpZaTV0WlhOellXZHBibWN1VTJWeWRtbGpaVVJsWm1sdWFYUnBiMjRBbXdKUFdOMHBmZ0lBRFV3QUMySjFjMU5sWTNWeWFYUjVkQUFUVEdwaGRtRXZiR0Z1Wnk5Q2IyOXNaV0Z1TzB3QUQyTnlaV1JsYm5ScFlXeHpWSGx3WlhRQVRFeHZjbWN2YTNWaGJHa3ZjbWxqWlM5amIzSmxMM05sWTNWeWFYUjVMMk55WldSbGJuUnBZV3h6TDBOeVpXUmxiblJwWVd4elUyOTFjbU5sSkVOeVpXUmxiblJwWVd4elZIbHdaVHRNQUJCc2IyTmhiRk5sY25acFkyVk9ZVzFsZEFBU1RHcGhkbUV2YkdGdVp5OVRkSEpwYm1jN1RBQVhiV1Z6YzJGblpVVjRZMlZ3ZEdsdmJraGhibVJzWlhKeEFINEFCVXdBREcxcGJHeHBjMVJ2VEdsMlpYUUFFRXhxWVhaaEwyeGhibWN2VEc5dVp6dE1BQWh3Y21sdmNtbDBlWFFBRTB4cVlYWmhMMnhoYm1jdlNXNTBaV2RsY2p0TUFBVnhkV1YxWlhFQWZnQURUQUFOY21WMGNubEJkSFJsYlhCMGMzRUFmZ0FIVEFBSGMyVnlkbWxqWlhRQUVreHFZWFpoTDJ4aGJtY3ZUMkpxWldOME8wd0FEM05sY25acFkyVkZibVJRYjJsdWRIUUFEa3hxWVhaaEwyNWxkQzlWVWt3N1RBQVRjMlZ5ZG1salpVNWhiV1ZUY0dGalpWVlNTWEVBZmdBRlRBQVFjMlZ5ZG1salpVNWhiV1Z6Y0dGalpYRUFmZ0FGVEFBTGMyVnlkbWxqWlZCaGRHaHhBSDRBQlhod2MzSUFFV3BoZG1FdWJHRnVaeTVDYjI5c1pXRnV6U0J5Z05XYyt1NENBQUZhQUFWMllXeDFaWGh3QVhCMEFCcFBVME5oWTJobFRtOTBhV1pwWTJGMGFXOXVVMlZ5ZG1salpYUUFUVzl5Wnk1cmRXRnNhUzV5YVdObExtdHpZaTV0WlhOellXZHBibWN1WlhoalpYQjBhVzl1YUdGdVpHeHBibWN1UkdWbVlYVnNkRTFsYzNOaFoyVkZlR05sY0hScGIyNUlZVzVrYkdWeWMzSUFEbXBoZG1FdWJHRnVaeTVNYjI1bk80dmtrTXlQSTk4Q0FBRktBQVYyWVd4MVpYaHlBQkJxWVhaaExteGhibWN1VG5WdFltVnlocXlWSFF1VTRJc0NBQUI0Y1AvLy8vLy8vLy8vYzNJQUVXcGhkbUV1YkdGdVp5NUpiblJsWjJWeUV1S2dwUGVCaHpnQ0FBRkpBQVYyWVd4MVpYaHhBSDRBRUFBQUFBTnpjUUIrQUFzQWNRQitBQk53YzNJQURHcGhkbUV1Ym1WMExsVlNUSllsTnpZYS9PUnlBd0FIU1FBSWFHRnphRU52WkdWSkFBUndiM0owVEFBSllYVjBhRzl5YVhSNWNRQitBQVZNQUFSbWFXeGxjUUIrQUFWTUFBUm9iM04wY1FCK0FBVk1BQWh3Y205MGIyTnZiSEVBZmdBRlRBQURjbVZtY1FCK0FBVjRjUC8vLy84QUFCK1FkQUFPYkc5allXeG9iM04wT2pnd09EQjBBQ3N2YTNJdFpHVjJMM0psYlc5MGFXNW5MMDlUUTJGamFHVk9iM1JwWm1sallYUnBiMjVUWlhKMmFXTmxkQUFKYkc5allXeG9iM04wZEFBRWFIUjBjSEI0ZEFBQWRBQUdWRkpCVmtWTWRBQUJMM055QUJOcVlYWmhMblYwYVd3dVFYSnlZWGxNYVhOMGVJSFNIWm5IWVowREFBRkpBQVJ6YVhwbGVIQUFBQUFGZHdRQUFBQUtkQUF6YjNKbkxtdDFZV3hwTG5KcFky';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 436    FOR UPDATE;        
    buffer := 'VXVhM05pTG0xbGMzTmhaMmx1Wnk1elpYSjJhV05sTGt0VFFrcGhkbUZUWlhKMmFXTmxkQUE4WTI5dExtOXdaVzV6ZVcxd2FHOXVlUzV2YzJOaFkyaGxMbUpoYzJVdVpYWmxiblJ6TGtOaFkyaGxSVzUwY25sRmRtVnVkRXhwYzNSbGJtVnlkQUEzWTI5dExtOXdaVzV6ZVcxd2FHOXVlUzV2YzJOaFkyaGxMbUpoYzJVdVpYWmxiblJ6TGtOaFkyaGxSWFpsYm5STWFYTjBaVzVsY25RQUYycGhkbUV1ZFhScGJDNUZkbVZ1ZEV4cGMzUmxibVZ5ZEFBc1kyOXRMbTl3Wlc1emVXMXdhRzl1ZVM1dmMyTmhZMmhsTG1KaGMyVXVUR2xtWldONVkyeGxRWGRoY21WNHQADTEyOS4xODYuNzkuNDVzcgAyb3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5KYXZhU2VydmljZURlZmluaXRpb2625w1fQTRs8QIAAUwAEXNlcnZpY2VJbnRlcmZhY2VzdAAQTGphdmEvdXRpbC9MaXN0O3hyAC5vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLlNlcnZpY2VEZWZpbml0aW9uAJsCT1jdKX4CAA1MAAtidXNTZWN1cml0eXEAfgATTAAPY3JlZGVudGlhbHNUeXBldABMTG9yZy9rdWFsaS9yaWNlL2NvcmUvc2VjdXJpdHkvY3JlZGVudGlhbHMvQ3JlZGVudGlhbHNTb3VyY2UkQ3JlZGVudGlhbHNUeXBlO0wAEGxvY2FsU2VydmljZU5hbWVxAH4ABEwAF21lc3NhZ2VFeGNlcHRpb25IYW5kbGVycQB+AARMAAxtaWxsaXNUb0xpdmVxAH4AFUwACHByaW9yaXR5cQB+ABRMAAVxdWV1ZXEAfgATTAANcmV0cnlBdHRlbXB0c3EAfgAUTAAHc2VydmljZXQAEkxqYXZhL2xhbmcvT2JqZWN0O0wAD3NlcnZpY2VFbmRQb2ludHQADkxqYXZhL25ldC9VUkw7TAATc2VydmljZU5hbWVTcGFjZVVSSXEAfgAETAAQc2VydmljZU5hbWVzcGFjZXEAfgAETAALc2VydmljZVBhdGhxAH4ABHhwc3EAfgAZAXB0ABpPU0NhY2hlTm90aWZpY2F0aW9uU2VydmljZXQATW9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuZXhjZXB0aW9uaGFuZGxpbmcuRGVmYXVsdE1lc3NhZ2VFeGNlcHRpb25IYW5kbGVyc3EAfgAf//////////9zcQB+ABwAAAADc3EAfgAZAHEAfgAycHNyAAxqYXZhLm5ldC5VUkyWJTc2GvzkcgMAB0kACGhhc2hDb2RlSQAEcG9ydEwACWF1dGhvcml0eXEAfgAETAAEZmlsZXEAfgAETAAEaG9zdHEAfgAETAAIcHJvdG9jb2xxAH4ABEwAA3JlZnEAfgAEeHBpG6KgAAAfkHQADmxvY2FsaG9zdDo4MDgwdAArL2tyLWRldi9yZW1vdGluZy9PU0NhY2hlTm90aWZpY2F0aW9uU2VydmljZXQACWxvY2FsaG9zdHQABGh0dHBweHQAAHQABlRSQVZFTHQAAS9zcgATamF2YS51dGlsLkFycmF5TGlzdHiB0h2Zx2GdAwABSQAEc2l6ZXhwAAAABXcEAAAACnQAM29yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuc2VydmljZS5LU0JKYXZhU2VydmljZXQAPGNvbS5vcGVuc3ltcGhvbnkub3NjYWNoZS5iYXNlLmV2ZW50cy5DYWNoZUVudHJ5RXZlbnRMaXN0ZW5lcnQAN2NvbS5vcGVuc3ltcGhvbnkub3NjYWNoZS5iYXNlLmV2ZW50cy5DYWNoZUV2ZW50TGlzdGVuZXJ0ABdqYXZhLnV0aWwuRXZlbnRMaXN0ZW5lcnQALGNvbS5vcGVuc3ltcGhvbnkub3NjYWNoZS5iYXNlLkxpZmVjeWNsZUF3YXJleHEAfgAjdAAGVFJBVkVM';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
INSERT INTO KRSB_MSG_PYLD_T (MSG_PYLD,MSG_QUE_ID)
  VALUES (EMPTY_CLOB(),437)
/
-- Length: 6096
-- Chunks: 2
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 437    FOR UPDATE;        
    buffer := 'rO0ABXNyAC1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLkFzeW5jaHJvbm91c0NhbGzxnQ5Y0t0lZQIAB1oAFWlnbm9yZVN0b3JlQW5kRm9yd2FyZFsACWFyZ3VtZW50c3QAE1tMamF2YS9sYW5nL09iamVjdDtMAAhjYWxsYmFja3QAM0xvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL0FzeW5jaHJvbm91c0NhbGxiYWNrO0wAB2NvbnRleHR0ABZMamF2YS9pby9TZXJpYWxpemFibGU7TAAKbWV0aG9kTmFtZXQAEkxqYXZhL2xhbmcvU3RyaW5nO1sACnBhcmFtVHlwZXN0ABJbTGphdmEvbGFuZy9DbGFzcztMAAtzZXJ2aWNlSW5mb3QAKkxvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL1NlcnZpY2VJbmZvO3hwAHVyABNbTGphdmEubGFuZy5PYmplY3Q7kM5YnxBzKWwCAAB4cAAAAAFzcgBDY29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLnBsdWdpbnMuY2x1c3RlcnN1cHBvcnQuQ2x1c3Rlck5vdGlmaWNhdGlvbhY+6VckvG9pAgACSQAEdHlwZUwABGRhdGFxAH4AA3hwAAAAAnQAKkRvY3VtZW50VHlwZVJ1bGVDYWNoZTpEb2N1bWVudFR5cGVEb2N1bWVudHBwdAAGaW52b2tldXIAEltMamF2YS5sYW5nLkNsYXNzO6sW167LzVqZAgAAeHAAAAABdnIAFGphdmEuaW8uU2VyaWFsaXphYmxlEJthDdebZO0CAAB4cHNyAChvcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLlNlcnZpY2VJbmZvxRcjtikEt7oCAAtMAAVhbGl2ZXQAE0xqYXZhL2xhbmcvQm9vbGVhbjtMABRlbmRwb2ludEFsdGVybmF0ZVVybHEAfgAETAALZW5kcG9pbnRVcmxxAH4ABEwACmxvY2tWZXJOYnJ0ABNMamF2YS9sYW5nL0ludGVnZXI7TAAObWVzc2FnZUVudHJ5SWR0ABBMamF2YS9sYW5nL0xvbmc7TAAFcW5hbWV0ABtMamF2YXgveG1sL25hbWVzcGFjZS9RTmFtZTtMABpzZXJpYWxpemVkU2VydmljZU5hbWVzcGFjZXEAfgAETAAIc2VydmVySXBxAH4ABEwAEXNlcnZpY2VEZWZpbml0aW9udAAwTG9yZy9rdWFsaS9yaWNlL2tzYi9tZXNzYWdpbmcvU2VydmljZURlZmluaXRpb247TAALc2VydmljZU5hbWVxAH4ABEwAEHNlcnZpY2VOYW1lc3BhY2VxAH4ABHhwc3IAEWphdmEubGFuZy5Cb29sZWFuzSBygNWc+u4CAAFaAAV2YWx1ZXhwAXB0AEBodHRwOi8vbG9jYWxob3N0OjgwODAva3ItZGV2L3JlbW90aW5nL09TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNlc3IAEWphdmEubGFuZy5JbnRlZ2VyEuKgpPeBhzgCAAFJAAV2YWx1ZXhyABBqYXZhLmxhbmcuTnVtYmVyhqyVHQuU4IsCAAB4cAAAAAFzcgAOamF2YS5sYW5nLkxvbmc7i+SQzI8j3wIAAUoABXZhbHVleHEAfgAdAAAAAAAAD31zcgAZamF2YXgueG1sLm5hbWVzcGFjZS5RTmFtZYFtqC38O91sAgADTAAJbG9jYWxQYXJ0cQB+AARMAAxuYW1lc3BhY2VVUklxAH4ABEwABnByZWZpeHEAfgAEeHB0ABpPU0NhY2hlTm90aWZpY2F0aW9uU2VydmljZXQAAHEAfgAkdAd0ck8wQUJYTnlBREp2Y21jdWEzVmhiR2t1Y21salpTNXJjMkl1YldWemMyRm5hVzVuTGtwaGRtRlRaWEoyYVdObFJHVm1hVzVwZEdsdmJyYm5EVjlCTkd6eEFnQUJUQUFSYzJWeWRtbGpaVWx1ZEdWeVptRmpaWE4wQUJCTWFtRjJZUzkxZEdsc0wweHBjM1E3ZUhJQUxtOXlaeTVyZFdGc2FTNXlhV05sTG10ellpNXRaWE56WVdkcGJtY3VVMlZ5ZG1salpVUmxabWx1YVhScGIyNEFtd0pQV04wcGZnSUFEVXdBQzJKMWMxTmxZM1Z5YVhSNWRBQVRUR3BoZG1FdmJHRnVaeTlDYjI5c1pXRnVPMHdBRDJOeVpXUmxiblJwWVd4elZIbHdaWFFBVEV4dmNtY3ZhM1ZoYkdrdmNtbGpaUzlqYjNKbEwzTmxZM1Z5YVhSNUwyTnlaV1JsYm5ScFlXeHpMME55WldSbGJuUnBZV3h6VTI5MWNtTmxKRU55WldSbGJuUnBZV3h6Vkhsd1pUdE1BQkJzYjJOaGJGTmxjblpwWTJWT1lXMWxkQUFTVEdwaGRtRXZiR0Z1Wnk5VGRISnBibWM3VEFBWGJXVnpjMkZuWlVWNFkyVndkR2x2YmtoaGJtUnNaWEp4QUg0QUJVd0FERzFwYkd4cGMxUnZUR2wyWlhRQUVFeHFZWFpoTDJ4aGJtY3ZURzl1Wnp0TUFBaHdjbWx2Y21sMGVYUUFFMHhxWVhaaEwyeGhibWN2U1c1MFpXZGxjanRNQUFWeGRXVjFaWEVBZmdBRFRBQU5jbVYwY25sQmRIUmxiWEIwYzNFQWZnQUhUQUFIYzJWeWRtbGpaWFFBRWt4cVlYWmhMMnhoYm1jdlQySnFaV04wTzB3QUQzTmxjblpwWTJWRmJtUlFiMmx1ZEhRQURreHFZWFpoTDI1bGRDOVZVa3c3VEFBVGMyVnlkbWxqWlU1aGJXVlRjR0ZqWlZWU1NYRUFmZ0FGVEFBUWMyVnlkbWxqWlU1aGJXVnpjR0ZqWlhFQWZnQUZUQUFMYzJWeWRtbGpaVkJoZEdoeEFINEFCWGh3YzNJQUVXcGhkbUV1YkdGdVp5NUNiMjlzWldGdXpTQnlnTldjK3U0Q0FBRmFBQVYyWVd4MVpYaHdBWEIwQUJwUFUwTmhZMmhsVG05MGFXWnBZMkYwYVc5dVUyVnlkbWxqWlhRQVRXOXlaeTVyZFdGc2FTNXlhV05sTG10ellpNXRaWE56WVdkcGJtY3VaWGhqWlhCMGFXOXVhR0Z1Wkd4cGJtY3VSR1ZtWVhWc2RFMWxjM05oWjJWRmVHTmxjSFJwYjI1SVlXNWtiR1Z5YzNJQURtcGhkbUV1YkdGdVp5NU1iMjVuTzR2a2tNeVBJOThDQUFGS0FBVjJZV3gxWlhoeUFCQnFZWFpoTG14aGJtY3VUblZ0WW1WeWhxeVZIUXVVNElzQ0FBQjRjUC8vLy8vLy8vLy9jM0lBRVdwaGRtRXViR0Z1Wnk1SmJuUmxaMlZ5RXVLZ3BQZUJoemdDQUFGSkFBVjJZV3gxWlhoeEFINEFFQUFBQUFOemNRQitBQXNBY1FCK0FCTndjM0lBREdwaGRtRXVibVYwTGxWU1RKWWxOellhL09SeUF3QUhTUUFJYUdGemFFTnZaR1ZKQUFSd2IzSjBUQUFKWVhWMGFHOXlhWFI1Y1FCK0FBVk1BQVJtYVd4bGNRQitBQVZNQUFSb2IzTjBjUUIrQUFWTUFBaHdjbTkwYjJOdmJIRUFmZ0FGVEFBRGNtVm1jUUIrQUFWNGNQLy8vLzhBQUIrUWRBQU9iRzlqWVd4b2IzTjBPamd3T0RCMEFDc3ZhM0l0WkdWMkwzSmxiVzkwYVc1bkwwOVRRMkZqYUdWT2IzUnBabWxqWVhScGIyNVRaWEoyYVdObGRBQUpiRzlqWVd4b2IzTjBkQUFFYUhSMGNIQjRkQUFBZEFBR1ZGSkJWa1ZNZEFBQkwzTnlBQk5xWVhaaExuVjBhV3d1UVhKeVlYbE1hWE4wZUlIU0habkhZWjBEQUFGSkFBUnphWHBsZUhBQUFBQUZkd1FBQUFBS2RBQXpiM0puTG10MVlX';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 437    FOR UPDATE;        
    buffer := 'eHBMbkpwWTJVdWEzTmlMbTFsYzNOaFoybHVaeTV6WlhKMmFXTmxMa3RUUWtwaGRtRlRaWEoyYVdObGRBQThZMjl0TG05d1pXNXplVzF3YUc5dWVTNXZjMk5oWTJobExtSmhjMlV1WlhabGJuUnpMa05oWTJobFJXNTBjbmxGZG1WdWRFeHBjM1JsYm1WeWRBQTNZMjl0TG05d1pXNXplVzF3YUc5dWVTNXZjMk5oWTJobExtSmhjMlV1WlhabGJuUnpMa05oWTJobFJYWmxiblJNYVhOMFpXNWxjblFBRjJwaGRtRXVkWFJwYkM1RmRtVnVkRXhwYzNSbGJtVnlkQUFzWTI5dExtOXdaVzV6ZVcxd2FHOXVlUzV2YzJOaFkyaGxMbUpoYzJVdVRHbG1aV041WTJ4bFFYZGhjbVY0dAANMTI5LjE4Ni43OS40NXNyADJvcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLkphdmFTZXJ2aWNlRGVmaW5pdGlvbrbnDV9BNGzxAgABTAARc2VydmljZUludGVyZmFjZXN0ABBMamF2YS91dGlsL0xpc3Q7eHIALm9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuU2VydmljZURlZmluaXRpb24AmwJPWN0pfgIADUwAC2J1c1NlY3VyaXR5cQB+ABNMAA9jcmVkZW50aWFsc1R5cGV0AExMb3JnL2t1YWxpL3JpY2UvY29yZS9zZWN1cml0eS9jcmVkZW50aWFscy9DcmVkZW50aWFsc1NvdXJjZSRDcmVkZW50aWFsc1R5cGU7TAAQbG9jYWxTZXJ2aWNlTmFtZXEAfgAETAAXbWVzc2FnZUV4Y2VwdGlvbkhhbmRsZXJxAH4ABEwADG1pbGxpc1RvTGl2ZXEAfgAVTAAIcHJpb3JpdHlxAH4AFEwABXF1ZXVlcQB+ABNMAA1yZXRyeUF0dGVtcHRzcQB+ABRMAAdzZXJ2aWNldAASTGphdmEvbGFuZy9PYmplY3Q7TAAPc2VydmljZUVuZFBvaW50dAAOTGphdmEvbmV0L1VSTDtMABNzZXJ2aWNlTmFtZVNwYWNlVVJJcQB+AARMABBzZXJ2aWNlTmFtZXNwYWNlcQB+AARMAAtzZXJ2aWNlUGF0aHEAfgAEeHBzcQB+ABkBcHQAGk9TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNldABNb3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5leGNlcHRpb25oYW5kbGluZy5EZWZhdWx0TWVzc2FnZUV4Y2VwdGlvbkhhbmRsZXJzcQB+AB///////////3NxAH4AHAAAAANzcQB+ABkAcQB+ADJwc3IADGphdmEubmV0LlVSTJYlNzYa/ORyAwAHSQAIaGFzaENvZGVJAARwb3J0TAAJYXV0aG9yaXR5cQB+AARMAARmaWxlcQB+AARMAARob3N0cQB+AARMAAhwcm90b2NvbHEAfgAETAADcmVmcQB+AAR4cGkboqAAAB+QdAAObG9jYWxob3N0OjgwODB0ACsva3ItZGV2L3JlbW90aW5nL09TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNldAAJbG9jYWxob3N0dAAEaHR0cHB4dAAAdAAGVFJBVkVMdAABL3NyABNqYXZhLnV0aWwuQXJyYXlMaXN0eIHSHZnHYZ0DAAFJAARzaXpleHAAAAAFdwQAAAAKdAAzb3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5zZXJ2aWNlLktTQkphdmFTZXJ2aWNldAA8Y29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLmJhc2UuZXZlbnRzLkNhY2hlRW50cnlFdmVudExpc3RlbmVydAA3Y29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLmJhc2UuZXZlbnRzLkNhY2hlRXZlbnRMaXN0ZW5lcnQAF2phdmEudXRpbC5FdmVudExpc3RlbmVydAAsY29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLmJhc2UuTGlmZWN5Y2xlQXdhcmV4cQB+ACN0AAZUUkFWRUw=';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
INSERT INTO KRSB_MSG_PYLD_T (MSG_PYLD,MSG_QUE_ID)
  VALUES (EMPTY_CLOB(),438)
/
-- Length: 6096
-- Chunks: 2
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 438    FOR UPDATE;        
    buffer := 'rO0ABXNyAC1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLkFzeW5jaHJvbm91c0NhbGzxnQ5Y0t0lZQIAB1oAFWlnbm9yZVN0b3JlQW5kRm9yd2FyZFsACWFyZ3VtZW50c3QAE1tMamF2YS9sYW5nL09iamVjdDtMAAhjYWxsYmFja3QAM0xvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL0FzeW5jaHJvbm91c0NhbGxiYWNrO0wAB2NvbnRleHR0ABZMamF2YS9pby9TZXJpYWxpemFibGU7TAAKbWV0aG9kTmFtZXQAEkxqYXZhL2xhbmcvU3RyaW5nO1sACnBhcmFtVHlwZXN0ABJbTGphdmEvbGFuZy9DbGFzcztMAAtzZXJ2aWNlSW5mb3QAKkxvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL1NlcnZpY2VJbmZvO3hwAHVyABNbTGphdmEubGFuZy5PYmplY3Q7kM5YnxBzKWwCAAB4cAAAAAFzcgBDY29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLnBsdWdpbnMuY2x1c3RlcnN1cHBvcnQuQ2x1c3Rlck5vdGlmaWNhdGlvbhY+6VckvG9pAgACSQAEdHlwZUwABGRhdGFxAH4AA3hwAAAAAnQAKURvY3VtZW50VHlwZVJ1bGVDYWNoZTpSb3V0aW5nUnVsZURvY3VtZW50cHB0AAZpbnZva2V1cgASW0xqYXZhLmxhbmcuQ2xhc3M7qxbXrsvNWpkCAAB4cAAAAAF2cgAUamF2YS5pby5TZXJpYWxpemFibGUQm2EN15tk7QIAAHhwc3IAKG9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuU2VydmljZUluZm/FFyO2KQS3ugIAC0wABWFsaXZldAATTGphdmEvbGFuZy9Cb29sZWFuO0wAFGVuZHBvaW50QWx0ZXJuYXRlVXJscQB+AARMAAtlbmRwb2ludFVybHEAfgAETAAKbG9ja1Zlck5icnQAE0xqYXZhL2xhbmcvSW50ZWdlcjtMAA5tZXNzYWdlRW50cnlJZHQAEExqYXZhL2xhbmcvTG9uZztMAAVxbmFtZXQAG0xqYXZheC94bWwvbmFtZXNwYWNlL1FOYW1lO0wAGnNlcmlhbGl6ZWRTZXJ2aWNlTmFtZXNwYWNlcQB+AARMAAhzZXJ2ZXJJcHEAfgAETAARc2VydmljZURlZmluaXRpb250ADBMb3JnL2t1YWxpL3JpY2Uva3NiL21lc3NhZ2luZy9TZXJ2aWNlRGVmaW5pdGlvbjtMAAtzZXJ2aWNlTmFtZXEAfgAETAAQc2VydmljZU5hbWVzcGFjZXEAfgAEeHBzcgARamF2YS5sYW5nLkJvb2xlYW7NIHKA1Zz67gIAAVoABXZhbHVleHABcHQAQGh0dHA6Ly9sb2NhbGhvc3Q6ODA4MC9rci1kZXYvcmVtb3RpbmcvT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2VzcgARamF2YS5sYW5nLkludGVnZXIS4qCk94GHOAIAAUkABXZhbHVleHIAEGphdmEubGFuZy5OdW1iZXKGrJUdC5TgiwIAAHhwAAAAAXNyAA5qYXZhLmxhbmcuTG9uZzuL5JDMjyPfAgABSgAFdmFsdWV4cQB+AB0AAAAAAAAPfXNyABlqYXZheC54bWwubmFtZXNwYWNlLlFOYW1lgW2oLfw73WwCAANMAAlsb2NhbFBhcnRxAH4ABEwADG5hbWVzcGFjZVVSSXEAfgAETAAGcHJlZml4cQB+AAR4cHQAGk9TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNldAAAcQB+ACR0B3RyTzBBQlhOeUFESnZjbWN1YTNWaGJHa3VjbWxqWlM1cmMySXViV1Z6YzJGbmFXNW5Ma3BoZG1GVFpYSjJhV05sUkdWbWFXNXBkR2x2YnJibkRWOUJOR3p4QWdBQlRBQVJjMlZ5ZG1salpVbHVkR1Z5Wm1GalpYTjBBQkJNYW1GMllTOTFkR2xzTDB4cGMzUTdlSElBTG05eVp5NXJkV0ZzYVM1eWFXTmxMbXR6WWk1dFpYTnpZV2RwYm1jdVUyVnlkbWxqWlVSbFptbHVhWFJwYjI0QW13SlBXTjBwZmdJQURVd0FDMkoxYzFObFkzVnlhWFI1ZEFBVFRHcGhkbUV2YkdGdVp5OUNiMjlzWldGdU8wd0FEMk55WldSbGJuUnBZV3h6Vkhsd1pYUUFURXh2Y21jdmEzVmhiR2t2Y21salpTOWpiM0psTDNObFkzVnlhWFI1TDJOeVpXUmxiblJwWVd4ekwwTnlaV1JsYm5ScFlXeHpVMjkxY21ObEpFTnlaV1JsYm5ScFlXeHpWSGx3WlR0TUFCQnNiMk5oYkZObGNuWnBZMlZPWVcxbGRBQVNUR3BoZG1FdmJHRnVaeTlUZEhKcGJtYzdUQUFYYldWemMyRm5aVVY0WTJWd2RHbHZia2hoYm1Sc1pYSnhBSDRBQlV3QURHMXBiR3hwYzFSdlRHbDJaWFFBRUV4cVlYWmhMMnhoYm1jdlRHOXVaenRNQUFod2NtbHZjbWwwZVhRQUUweHFZWFpoTDJ4aGJtY3ZTVzUwWldkbGNqdE1BQVZ4ZFdWMVpYRUFmZ0FEVEFBTmNtVjBjbmxCZEhSbGJYQjBjM0VBZmdBSFRBQUhjMlZ5ZG1salpYUUFFa3hxWVhaaEwyeGhibWN2VDJKcVpXTjBPMHdBRDNObGNuWnBZMlZGYm1SUWIybHVkSFFBRGt4cVlYWmhMMjVsZEM5VlVrdzdUQUFUYzJWeWRtbGpaVTVoYldWVGNHRmpaVlZTU1hFQWZnQUZUQUFRYzJWeWRtbGpaVTVoYldWemNHRmpaWEVBZmdBRlRBQUxjMlZ5ZG1salpWQmhkR2h4QUg0QUJYaHdjM0lBRVdwaGRtRXViR0Z1Wnk1Q2IyOXNaV0Z1elNCeWdOV2MrdTRDQUFGYUFBVjJZV3gxWlhod0FYQjBBQnBQVTBOaFkyaGxUbTkwYVdacFkyRjBhVzl1VTJWeWRtbGpaWFFBVFc5eVp5NXJkV0ZzYVM1eWFXTmxMbXR6WWk1dFpYTnpZV2RwYm1jdVpYaGpaWEIwYVc5dWFHRnVaR3hwYm1jdVJHVm1ZWFZzZEUxbGMzTmhaMlZGZUdObGNIUnBiMjVJWVc1a2JHVnljM0lBRG1waGRtRXViR0Z1Wnk1TWIyNW5PNHZra015UEk5OENBQUZLQUFWMllXeDFaWGh5QUJCcVlYWmhMbXhoYm1jdVRuVnRZbVZ5aHF5VkhRdVU0SXNDQUFCNGNQLy8vLy8vLy8vL2MzSUFFV3BoZG1FdWJHRnVaeTVKYm5SbFoyVnlFdUtncFBlQmh6Z0NBQUZKQUFWMllXeDFaWGh4QUg0QUVBQUFBQU56Y1FCK0FBc0FjUUIrQUJOd2MzSUFER3BoZG1FdWJtVjBMbFZTVEpZbE56WWEvT1J5QXdBSFNRQUlhR0Z6YUVOdlpHVkpBQVJ3YjNKMFRBQUpZWFYwYUc5eWFYUjVjUUIrQUFWTUFBUm1hV3hsY1FCK0FBVk1BQVJvYjNOMGNRQitBQVZNQUFod2NtOTBiMk52YkhFQWZnQUZUQUFEY21WbWNRQitBQVY0Y1AvLy8vOEFBQitRZEFBT2JHOWpZV3hvYjNOME9qZ3dPREIwQUNzdmEzSXRaR1YyTDNKbGJXOTBhVzVuTDA5VFEyRmphR1ZPYjNScFptbGpZWFJwYjI1VFpYSjJhV05sZEFBSmJHOWpZV3hvYjNOMGRBQUVhSFIwY0hCNGRBQUFkQUFHVkZKQlZrVk1kQUFCTDNOeUFCTnFZWFpoTG5WMGFXd3VRWEp5WVhsTWFYTjBlSUhTSFpuSFlaMERBQUZKQUFSemFYcGxlSEFBQUFBRmR3UUFBQUFLZEFBemIzSm5MbXQxWVd4';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 438    FOR UPDATE;        
    buffer := 'cExuSnBZMlV1YTNOaUxtMWxjM05oWjJsdVp5NXpaWEoyYVdObExrdFRRa3BoZG1GVFpYSjJhV05sZEFBOFkyOXRMbTl3Wlc1emVXMXdhRzl1ZVM1dmMyTmhZMmhsTG1KaGMyVXVaWFpsYm5SekxrTmhZMmhsUlc1MGNubEZkbVZ1ZEV4cGMzUmxibVZ5ZEFBM1kyOXRMbTl3Wlc1emVXMXdhRzl1ZVM1dmMyTmhZMmhsTG1KaGMyVXVaWFpsYm5SekxrTmhZMmhsUlhabGJuUk1hWE4wWlc1bGNuUUFGMnBoZG1FdWRYUnBiQzVGZG1WdWRFeHBjM1JsYm1WeWRBQXNZMjl0TG05d1pXNXplVzF3YUc5dWVTNXZjMk5oWTJobExtSmhjMlV1VEdsbVpXTjVZMnhsUVhkaGNtVjR0AA0xMjkuMTg2Ljc5LjQ1c3IAMm9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuSmF2YVNlcnZpY2VEZWZpbml0aW9utucNX0E0bPECAAFMABFzZXJ2aWNlSW50ZXJmYWNlc3QAEExqYXZhL3V0aWwvTGlzdDt4cgAub3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5TZXJ2aWNlRGVmaW5pdGlvbgCbAk9Y3Sl+AgANTAALYnVzU2VjdXJpdHlxAH4AE0wAD2NyZWRlbnRpYWxzVHlwZXQATExvcmcva3VhbGkvcmljZS9jb3JlL3NlY3VyaXR5L2NyZWRlbnRpYWxzL0NyZWRlbnRpYWxzU291cmNlJENyZWRlbnRpYWxzVHlwZTtMABBsb2NhbFNlcnZpY2VOYW1lcQB+AARMABdtZXNzYWdlRXhjZXB0aW9uSGFuZGxlcnEAfgAETAAMbWlsbGlzVG9MaXZlcQB+ABVMAAhwcmlvcml0eXEAfgAUTAAFcXVldWVxAH4AE0wADXJldHJ5QXR0ZW1wdHNxAH4AFEwAB3NlcnZpY2V0ABJMamF2YS9sYW5nL09iamVjdDtMAA9zZXJ2aWNlRW5kUG9pbnR0AA5MamF2YS9uZXQvVVJMO0wAE3NlcnZpY2VOYW1lU3BhY2VVUklxAH4ABEwAEHNlcnZpY2VOYW1lc3BhY2VxAH4ABEwAC3NlcnZpY2VQYXRocQB+AAR4cHNxAH4AGQFwdAAaT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2V0AE1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLmV4Y2VwdGlvbmhhbmRsaW5nLkRlZmF1bHRNZXNzYWdlRXhjZXB0aW9uSGFuZGxlcnNxAH4AH///////////c3EAfgAcAAAAA3NxAH4AGQBxAH4AMnBzcgAMamF2YS5uZXQuVVJMliU3Nhr85HIDAAdJAAhoYXNoQ29kZUkABHBvcnRMAAlhdXRob3JpdHlxAH4ABEwABGZpbGVxAH4ABEwABGhvc3RxAH4ABEwACHByb3RvY29scQB+AARMAANyZWZxAH4ABHhwaRuioAAAH5B0AA5sb2NhbGhvc3Q6ODA4MHQAKy9rci1kZXYvcmVtb3RpbmcvT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2V0AAlsb2NhbGhvc3R0AARodHRwcHh0AAB0AAZUUkFWRUx0AAEvc3IAE2phdmEudXRpbC5BcnJheUxpc3R4gdIdmcdhnQMAAUkABHNpemV4cAAAAAV3BAAAAAp0ADNvcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLnNlcnZpY2UuS1NCSmF2YVNlcnZpY2V0ADxjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5ldmVudHMuQ2FjaGVFbnRyeUV2ZW50TGlzdGVuZXJ0ADdjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5ldmVudHMuQ2FjaGVFdmVudExpc3RlbmVydAAXamF2YS51dGlsLkV2ZW50TGlzdGVuZXJ0ACxjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5MaWZlY3ljbGVBd2FyZXhxAH4AI3QABlRSQVZFTA==';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
INSERT INTO KRSB_MSG_PYLD_T (MSG_PYLD,MSG_QUE_ID)
  VALUES (EMPTY_CLOB(),439)
/
-- Length: 6108
-- Chunks: 2
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 439    FOR UPDATE;        
    buffer := 'rO0ABXNyAC1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLkFzeW5jaHJvbm91c0NhbGzxnQ5Y0t0lZQIAB1oAFWlnbm9yZVN0b3JlQW5kRm9yd2FyZFsACWFyZ3VtZW50c3QAE1tMamF2YS9sYW5nL09iamVjdDtMAAhjYWxsYmFja3QAM0xvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL0FzeW5jaHJvbm91c0NhbGxiYWNrO0wAB2NvbnRleHR0ABZMamF2YS9pby9TZXJpYWxpemFibGU7TAAKbWV0aG9kTmFtZXQAEkxqYXZhL2xhbmcvU3RyaW5nO1sACnBhcmFtVHlwZXN0ABJbTGphdmEvbGFuZy9DbGFzcztMAAtzZXJ2aWNlSW5mb3QAKkxvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL1NlcnZpY2VJbmZvO3hwAHVyABNbTGphdmEubGFuZy5PYmplY3Q7kM5YnxBzKWwCAAB4cAAAAAFzcgBDY29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLnBsdWdpbnMuY2x1c3RlcnN1cHBvcnQuQ2x1c3Rlck5vdGlmaWNhdGlvbhY+6VckvG9pAgACSQAEdHlwZUwABGRhdGFxAH4AA3hwAAAAAnQAMkRvY3VtZW50VHlwZVJ1bGVDYWNoZTpOYW1lc3BhY2VNYWludGVuYW5jZURvY3VtZW50cHB0AAZpbnZva2V1cgASW0xqYXZhLmxhbmcuQ2xhc3M7qxbXrsvNWpkCAAB4cAAAAAF2cgAUamF2YS5pby5TZXJpYWxpemFibGUQm2EN15tk7QIAAHhwc3IAKG9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuU2VydmljZUluZm/FFyO2KQS3ugIAC0wABWFsaXZldAATTGphdmEvbGFuZy9Cb29sZWFuO0wAFGVuZHBvaW50QWx0ZXJuYXRlVXJscQB+AARMAAtlbmRwb2ludFVybHEAfgAETAAKbG9ja1Zlck5icnQAE0xqYXZhL2xhbmcvSW50ZWdlcjtMAA5tZXNzYWdlRW50cnlJZHQAEExqYXZhL2xhbmcvTG9uZztMAAVxbmFtZXQAG0xqYXZheC94bWwvbmFtZXNwYWNlL1FOYW1lO0wAGnNlcmlhbGl6ZWRTZXJ2aWNlTmFtZXNwYWNlcQB+AARMAAhzZXJ2ZXJJcHEAfgAETAARc2VydmljZURlZmluaXRpb250ADBMb3JnL2t1YWxpL3JpY2Uva3NiL21lc3NhZ2luZy9TZXJ2aWNlRGVmaW5pdGlvbjtMAAtzZXJ2aWNlTmFtZXEAfgAETAAQc2VydmljZU5hbWVzcGFjZXEAfgAEeHBzcgARamF2YS5sYW5nLkJvb2xlYW7NIHKA1Zz67gIAAVoABXZhbHVleHABcHQAQGh0dHA6Ly9sb2NhbGhvc3Q6ODA4MC9rci1kZXYvcmVtb3RpbmcvT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2VzcgARamF2YS5sYW5nLkludGVnZXIS4qCk94GHOAIAAUkABXZhbHVleHIAEGphdmEubGFuZy5OdW1iZXKGrJUdC5TgiwIAAHhwAAAAAXNyAA5qYXZhLmxhbmcuTG9uZzuL5JDMjyPfAgABSgAFdmFsdWV4cQB+AB0AAAAAAAAPfXNyABlqYXZheC54bWwubmFtZXNwYWNlLlFOYW1lgW2oLfw73WwCAANMAAlsb2NhbFBhcnRxAH4ABEwADG5hbWVzcGFjZVVSSXEAfgAETAAGcHJlZml4cQB+AAR4cHQAGk9TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNldAAAcQB+ACR0B3RyTzBBQlhOeUFESnZjbWN1YTNWaGJHa3VjbWxqWlM1cmMySXViV1Z6YzJGbmFXNW5Ma3BoZG1GVFpYSjJhV05sUkdWbWFXNXBkR2x2YnJibkRWOUJOR3p4QWdBQlRBQVJjMlZ5ZG1salpVbHVkR1Z5Wm1GalpYTjBBQkJNYW1GMllTOTFkR2xzTDB4cGMzUTdlSElBTG05eVp5NXJkV0ZzYVM1eWFXTmxMbXR6WWk1dFpYTnpZV2RwYm1jdVUyVnlkbWxqWlVSbFptbHVhWFJwYjI0QW13SlBXTjBwZmdJQURVd0FDMkoxYzFObFkzVnlhWFI1ZEFBVFRHcGhkbUV2YkdGdVp5OUNiMjlzWldGdU8wd0FEMk55WldSbGJuUnBZV3h6Vkhsd1pYUUFURXh2Y21jdmEzVmhiR2t2Y21salpTOWpiM0psTDNObFkzVnlhWFI1TDJOeVpXUmxiblJwWVd4ekwwTnlaV1JsYm5ScFlXeHpVMjkxY21ObEpFTnlaV1JsYm5ScFlXeHpWSGx3WlR0TUFCQnNiMk5oYkZObGNuWnBZMlZPWVcxbGRBQVNUR3BoZG1FdmJHRnVaeTlUZEhKcGJtYzdUQUFYYldWemMyRm5aVVY0WTJWd2RHbHZia2hoYm1Sc1pYSnhBSDRBQlV3QURHMXBiR3hwYzFSdlRHbDJaWFFBRUV4cVlYWmhMMnhoYm1jdlRHOXVaenRNQUFod2NtbHZjbWwwZVhRQUUweHFZWFpoTDJ4aGJtY3ZTVzUwWldkbGNqdE1BQVZ4ZFdWMVpYRUFmZ0FEVEFBTmNtVjBjbmxCZEhSbGJYQjBjM0VBZmdBSFRBQUhjMlZ5ZG1salpYUUFFa3hxWVhaaEwyeGhibWN2VDJKcVpXTjBPMHdBRDNObGNuWnBZMlZGYm1SUWIybHVkSFFBRGt4cVlYWmhMMjVsZEM5VlVrdzdUQUFUYzJWeWRtbGpaVTVoYldWVGNHRmpaVlZTU1hFQWZnQUZUQUFRYzJWeWRtbGpaVTVoYldWemNHRmpaWEVBZmdBRlRBQUxjMlZ5ZG1salpWQmhkR2h4QUg0QUJYaHdjM0lBRVdwaGRtRXViR0Z1Wnk1Q2IyOXNaV0Z1elNCeWdOV2MrdTRDQUFGYUFBVjJZV3gxWlhod0FYQjBBQnBQVTBOaFkyaGxUbTkwYVdacFkyRjBhVzl1VTJWeWRtbGpaWFFBVFc5eVp5NXJkV0ZzYVM1eWFXTmxMbXR6WWk1dFpYTnpZV2RwYm1jdVpYaGpaWEIwYVc5dWFHRnVaR3hwYm1jdVJHVm1ZWFZzZEUxbGMzTmhaMlZGZUdObGNIUnBiMjVJWVc1a2JHVnljM0lBRG1waGRtRXViR0Z1Wnk1TWIyNW5PNHZra015UEk5OENBQUZLQUFWMllXeDFaWGh5QUJCcVlYWmhMbXhoYm1jdVRuVnRZbVZ5aHF5VkhRdVU0SXNDQUFCNGNQLy8vLy8vLy8vL2MzSUFFV3BoZG1FdWJHRnVaeTVKYm5SbFoyVnlFdUtncFBlQmh6Z0NBQUZKQUFWMllXeDFaWGh4QUg0QUVBQUFBQU56Y1FCK0FBc0FjUUIrQUJOd2MzSUFER3BoZG1FdWJtVjBMbFZTVEpZbE56WWEvT1J5QXdBSFNRQUlhR0Z6YUVOdlpHVkpBQVJ3YjNKMFRBQUpZWFYwYUc5eWFYUjVjUUIrQUFWTUFBUm1hV3hsY1FCK0FBVk1BQVJvYjNOMGNRQitBQVZNQUFod2NtOTBiMk52YkhFQWZnQUZUQUFEY21WbWNRQitBQVY0Y1AvLy8vOEFBQitRZEFBT2JHOWpZV3hvYjNOME9qZ3dPREIwQUNzdmEzSXRaR1YyTDNKbGJXOTBhVzVuTDA5VFEyRmphR1ZPYjNScFptbGpZWFJwYjI1VFpYSjJhV05sZEFBSmJHOWpZV3hvYjNOMGRBQUVhSFIwY0hCNGRBQUFkQUFHVkZKQlZrVk1kQUFCTDNOeUFCTnFZWFpoTG5WMGFXd3VRWEp5WVhsTWFYTjBlSUhTSFpuSFlaMERBQUZKQUFSemFYcGxlSEFBQUFBRmR3UUFBQUFLZEFBemIz';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 439    FOR UPDATE;        
    buffer := 'Sm5MbXQxWVd4cExuSnBZMlV1YTNOaUxtMWxjM05oWjJsdVp5NXpaWEoyYVdObExrdFRRa3BoZG1GVFpYSjJhV05sZEFBOFkyOXRMbTl3Wlc1emVXMXdhRzl1ZVM1dmMyTmhZMmhsTG1KaGMyVXVaWFpsYm5SekxrTmhZMmhsUlc1MGNubEZkbVZ1ZEV4cGMzUmxibVZ5ZEFBM1kyOXRMbTl3Wlc1emVXMXdhRzl1ZVM1dmMyTmhZMmhsTG1KaGMyVXVaWFpsYm5SekxrTmhZMmhsUlhabGJuUk1hWE4wWlc1bGNuUUFGMnBoZG1FdWRYUnBiQzVGZG1WdWRFeHBjM1JsYm1WeWRBQXNZMjl0TG05d1pXNXplVzF3YUc5dWVTNXZjMk5oWTJobExtSmhjMlV1VEdsbVpXTjVZMnhsUVhkaGNtVjR0AA0xMjkuMTg2Ljc5LjQ1c3IAMm9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuSmF2YVNlcnZpY2VEZWZpbml0aW9utucNX0E0bPECAAFMABFzZXJ2aWNlSW50ZXJmYWNlc3QAEExqYXZhL3V0aWwvTGlzdDt4cgAub3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5TZXJ2aWNlRGVmaW5pdGlvbgCbAk9Y3Sl+AgANTAALYnVzU2VjdXJpdHlxAH4AE0wAD2NyZWRlbnRpYWxzVHlwZXQATExvcmcva3VhbGkvcmljZS9jb3JlL3NlY3VyaXR5L2NyZWRlbnRpYWxzL0NyZWRlbnRpYWxzU291cmNlJENyZWRlbnRpYWxzVHlwZTtMABBsb2NhbFNlcnZpY2VOYW1lcQB+AARMABdtZXNzYWdlRXhjZXB0aW9uSGFuZGxlcnEAfgAETAAMbWlsbGlzVG9MaXZlcQB+ABVMAAhwcmlvcml0eXEAfgAUTAAFcXVldWVxAH4AE0wADXJldHJ5QXR0ZW1wdHNxAH4AFEwAB3NlcnZpY2V0ABJMamF2YS9sYW5nL09iamVjdDtMAA9zZXJ2aWNlRW5kUG9pbnR0AA5MamF2YS9uZXQvVVJMO0wAE3NlcnZpY2VOYW1lU3BhY2VVUklxAH4ABEwAEHNlcnZpY2VOYW1lc3BhY2VxAH4ABEwAC3NlcnZpY2VQYXRocQB+AAR4cHNxAH4AGQFwdAAaT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2V0AE1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLmV4Y2VwdGlvbmhhbmRsaW5nLkRlZmF1bHRNZXNzYWdlRXhjZXB0aW9uSGFuZGxlcnNxAH4AH///////////c3EAfgAcAAAAA3NxAH4AGQBxAH4AMnBzcgAMamF2YS5uZXQuVVJMliU3Nhr85HIDAAdJAAhoYXNoQ29kZUkABHBvcnRMAAlhdXRob3JpdHlxAH4ABEwABGZpbGVxAH4ABEwABGhvc3RxAH4ABEwACHByb3RvY29scQB+AARMAANyZWZxAH4ABHhwaRuioAAAH5B0AA5sb2NhbGhvc3Q6ODA4MHQAKy9rci1kZXYvcmVtb3RpbmcvT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2V0AAlsb2NhbGhvc3R0AARodHRwcHh0AAB0AAZUUkFWRUx0AAEvc3IAE2phdmEudXRpbC5BcnJheUxpc3R4gdIdmcdhnQMAAUkABHNpemV4cAAAAAV3BAAAAAp0ADNvcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLnNlcnZpY2UuS1NCSmF2YVNlcnZpY2V0ADxjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5ldmVudHMuQ2FjaGVFbnRyeUV2ZW50TGlzdGVuZXJ0ADdjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5ldmVudHMuQ2FjaGVFdmVudExpc3RlbmVydAAXamF2YS51dGlsLkV2ZW50TGlzdGVuZXJ0ACxjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5MaWZlY3ljbGVBd2FyZXhxAH4AI3QABlRSQVZFTA==';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
INSERT INTO KRSB_MSG_PYLD_T (MSG_PYLD,MSG_QUE_ID)
  VALUES (EMPTY_CLOB(),440)
/
-- Length: 6112
-- Chunks: 2
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 440    FOR UPDATE;        
    buffer := 'rO0ABXNyAC1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLkFzeW5jaHJvbm91c0NhbGzxnQ5Y0t0lZQIAB1oAFWlnbm9yZVN0b3JlQW5kRm9yd2FyZFsACWFyZ3VtZW50c3QAE1tMamF2YS9sYW5nL09iamVjdDtMAAhjYWxsYmFja3QAM0xvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL0FzeW5jaHJvbm91c0NhbGxiYWNrO0wAB2NvbnRleHR0ABZMamF2YS9pby9TZXJpYWxpemFibGU7TAAKbWV0aG9kTmFtZXQAEkxqYXZhL2xhbmcvU3RyaW5nO1sACnBhcmFtVHlwZXN0ABJbTGphdmEvbGFuZy9DbGFzcztMAAtzZXJ2aWNlSW5mb3QAKkxvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL1NlcnZpY2VJbmZvO3hwAHVyABNbTGphdmEubGFuZy5PYmplY3Q7kM5YnxBzKWwCAAB4cAAAAAFzcgBDY29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLnBsdWdpbnMuY2x1c3RlcnN1cHBvcnQuQ2x1c3Rlck5vdGlmaWNhdGlvbhY+6VckvG9pAgACSQAEdHlwZUwABGRhdGFxAH4AA3hwAAAAAnQANkRvY3VtZW50VHlwZVJ1bGVDYWNoZTpQYXJhbWV0ZXJUeXBlTWFpbnRlbmFuY2VEb2N1bWVudHBwdAAGaW52b2tldXIAEltMamF2YS5sYW5nLkNsYXNzO6sW167LzVqZAgAAeHAAAAABdnIAFGphdmEuaW8uU2VyaWFsaXphYmxlEJthDdebZO0CAAB4cHNyAChvcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLlNlcnZpY2VJbmZvxRcjtikEt7oCAAtMAAVhbGl2ZXQAE0xqYXZhL2xhbmcvQm9vbGVhbjtMABRlbmRwb2ludEFsdGVybmF0ZVVybHEAfgAETAALZW5kcG9pbnRVcmxxAH4ABEwACmxvY2tWZXJOYnJ0ABNMamF2YS9sYW5nL0ludGVnZXI7TAAObWVzc2FnZUVudHJ5SWR0ABBMamF2YS9sYW5nL0xvbmc7TAAFcW5hbWV0ABtMamF2YXgveG1sL25hbWVzcGFjZS9RTmFtZTtMABpzZXJpYWxpemVkU2VydmljZU5hbWVzcGFjZXEAfgAETAAIc2VydmVySXBxAH4ABEwAEXNlcnZpY2VEZWZpbml0aW9udAAwTG9yZy9rdWFsaS9yaWNlL2tzYi9tZXNzYWdpbmcvU2VydmljZURlZmluaXRpb247TAALc2VydmljZU5hbWVxAH4ABEwAEHNlcnZpY2VOYW1lc3BhY2VxAH4ABHhwc3IAEWphdmEubGFuZy5Cb29sZWFuzSBygNWc+u4CAAFaAAV2YWx1ZXhwAXB0AEBodHRwOi8vbG9jYWxob3N0OjgwODAva3ItZGV2L3JlbW90aW5nL09TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNlc3IAEWphdmEubGFuZy5JbnRlZ2VyEuKgpPeBhzgCAAFJAAV2YWx1ZXhyABBqYXZhLmxhbmcuTnVtYmVyhqyVHQuU4IsCAAB4cAAAAAFzcgAOamF2YS5sYW5nLkxvbmc7i+SQzI8j3wIAAUoABXZhbHVleHEAfgAdAAAAAAAAD31zcgAZamF2YXgueG1sLm5hbWVzcGFjZS5RTmFtZYFtqC38O91sAgADTAAJbG9jYWxQYXJ0cQB+AARMAAxuYW1lc3BhY2VVUklxAH4ABEwABnByZWZpeHEAfgAEeHB0ABpPU0NhY2hlTm90aWZpY2F0aW9uU2VydmljZXQAAHEAfgAkdAd0ck8wQUJYTnlBREp2Y21jdWEzVmhiR2t1Y21salpTNXJjMkl1YldWemMyRm5hVzVuTGtwaGRtRlRaWEoyYVdObFJHVm1hVzVwZEdsdmJyYm5EVjlCTkd6eEFnQUJUQUFSYzJWeWRtbGpaVWx1ZEdWeVptRmpaWE4wQUJCTWFtRjJZUzkxZEdsc0wweHBjM1E3ZUhJQUxtOXlaeTVyZFdGc2FTNXlhV05sTG10ellpNXRaWE56WVdkcGJtY3VVMlZ5ZG1salpVUmxabWx1YVhScGIyNEFtd0pQV04wcGZnSUFEVXdBQzJKMWMxTmxZM1Z5YVhSNWRBQVRUR3BoZG1FdmJHRnVaeTlDYjI5c1pXRnVPMHdBRDJOeVpXUmxiblJwWVd4elZIbHdaWFFBVEV4dmNtY3ZhM1ZoYkdrdmNtbGpaUzlqYjNKbEwzTmxZM1Z5YVhSNUwyTnlaV1JsYm5ScFlXeHpMME55WldSbGJuUnBZV3h6VTI5MWNtTmxKRU55WldSbGJuUnBZV3h6Vkhsd1pUdE1BQkJzYjJOaGJGTmxjblpwWTJWT1lXMWxkQUFTVEdwaGRtRXZiR0Z1Wnk5VGRISnBibWM3VEFBWGJXVnpjMkZuWlVWNFkyVndkR2x2YmtoaGJtUnNaWEp4QUg0QUJVd0FERzFwYkd4cGMxUnZUR2wyWlhRQUVFeHFZWFpoTDJ4aGJtY3ZURzl1Wnp0TUFBaHdjbWx2Y21sMGVYUUFFMHhxWVhaaEwyeGhibWN2U1c1MFpXZGxjanRNQUFWeGRXVjFaWEVBZmdBRFRBQU5jbVYwY25sQmRIUmxiWEIwYzNFQWZnQUhUQUFIYzJWeWRtbGpaWFFBRWt4cVlYWmhMMnhoYm1jdlQySnFaV04wTzB3QUQzTmxjblpwWTJWRmJtUlFiMmx1ZEhRQURreHFZWFpoTDI1bGRDOVZVa3c3VEFBVGMyVnlkbWxqWlU1aGJXVlRjR0ZqWlZWU1NYRUFmZ0FGVEFBUWMyVnlkbWxqWlU1aGJXVnpjR0ZqWlhFQWZnQUZUQUFMYzJWeWRtbGpaVkJoZEdoeEFINEFCWGh3YzNJQUVXcGhkbUV1YkdGdVp5NUNiMjlzWldGdXpTQnlnTldjK3U0Q0FBRmFBQVYyWVd4MVpYaHdBWEIwQUJwUFUwTmhZMmhsVG05MGFXWnBZMkYwYVc5dVUyVnlkbWxqWlhRQVRXOXlaeTVyZFdGc2FTNXlhV05sTG10ellpNXRaWE56WVdkcGJtY3VaWGhqWlhCMGFXOXVhR0Z1Wkd4cGJtY3VSR1ZtWVhWc2RFMWxjM05oWjJWRmVHTmxjSFJwYjI1SVlXNWtiR1Z5YzNJQURtcGhkbUV1YkdGdVp5NU1iMjVuTzR2a2tNeVBJOThDQUFGS0FBVjJZV3gxWlhoeUFCQnFZWFpoTG14aGJtY3VUblZ0WW1WeWhxeVZIUXVVNElzQ0FBQjRjUC8vLy8vLy8vLy9jM0lBRVdwaGRtRXViR0Z1Wnk1SmJuUmxaMlZ5RXVLZ3BQZUJoemdDQUFGSkFBVjJZV3gxWlhoeEFINEFFQUFBQUFOemNRQitBQXNBY1FCK0FCTndjM0lBREdwaGRtRXVibVYwTGxWU1RKWWxOellhL09SeUF3QUhTUUFJYUdGemFFTnZaR1ZKQUFSd2IzSjBUQUFKWVhWMGFHOXlhWFI1Y1FCK0FBVk1BQVJtYVd4bGNRQitBQVZNQUFSb2IzTjBjUUIrQUFWTUFBaHdjbTkwYjJOdmJIRUFmZ0FGVEFBRGNtVm1jUUIrQUFWNGNQLy8vLzhBQUIrUWRBQU9iRzlqWVd4b2IzTjBPamd3T0RCMEFDc3ZhM0l0WkdWMkwzSmxiVzkwYVc1bkwwOVRRMkZqYUdWT2IzUnBabWxqWVhScGIyNVRaWEoyYVdObGRBQUpiRzlqWVd4b2IzTjBkQUFFYUhSMGNIQjRkQUFBZEFBR1ZGSkJWa1ZNZEFBQkwzTnlBQk5xWVhaaExuVjBhV3d1UVhKeVlYbE1hWE4wZUlIU0habkhZWjBEQUFGSkFBUnphWHBsZUhBQUFBQUZkd1FBQUFBS2RB';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 440    FOR UPDATE;        
    buffer := 'QXpiM0puTG10MVlXeHBMbkpwWTJVdWEzTmlMbTFsYzNOaFoybHVaeTV6WlhKMmFXTmxMa3RUUWtwaGRtRlRaWEoyYVdObGRBQThZMjl0TG05d1pXNXplVzF3YUc5dWVTNXZjMk5oWTJobExtSmhjMlV1WlhabGJuUnpMa05oWTJobFJXNTBjbmxGZG1WdWRFeHBjM1JsYm1WeWRBQTNZMjl0TG05d1pXNXplVzF3YUc5dWVTNXZjMk5oWTJobExtSmhjMlV1WlhabGJuUnpMa05oWTJobFJYWmxiblJNYVhOMFpXNWxjblFBRjJwaGRtRXVkWFJwYkM1RmRtVnVkRXhwYzNSbGJtVnlkQUFzWTI5dExtOXdaVzV6ZVcxd2FHOXVlUzV2YzJOaFkyaGxMbUpoYzJVdVRHbG1aV041WTJ4bFFYZGhjbVY0dAANMTI5LjE4Ni43OS40NXNyADJvcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLkphdmFTZXJ2aWNlRGVmaW5pdGlvbrbnDV9BNGzxAgABTAARc2VydmljZUludGVyZmFjZXN0ABBMamF2YS91dGlsL0xpc3Q7eHIALm9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuU2VydmljZURlZmluaXRpb24AmwJPWN0pfgIADUwAC2J1c1NlY3VyaXR5cQB+ABNMAA9jcmVkZW50aWFsc1R5cGV0AExMb3JnL2t1YWxpL3JpY2UvY29yZS9zZWN1cml0eS9jcmVkZW50aWFscy9DcmVkZW50aWFsc1NvdXJjZSRDcmVkZW50aWFsc1R5cGU7TAAQbG9jYWxTZXJ2aWNlTmFtZXEAfgAETAAXbWVzc2FnZUV4Y2VwdGlvbkhhbmRsZXJxAH4ABEwADG1pbGxpc1RvTGl2ZXEAfgAVTAAIcHJpb3JpdHlxAH4AFEwABXF1ZXVlcQB+ABNMAA1yZXRyeUF0dGVtcHRzcQB+ABRMAAdzZXJ2aWNldAASTGphdmEvbGFuZy9PYmplY3Q7TAAPc2VydmljZUVuZFBvaW50dAAOTGphdmEvbmV0L1VSTDtMABNzZXJ2aWNlTmFtZVNwYWNlVVJJcQB+AARMABBzZXJ2aWNlTmFtZXNwYWNlcQB+AARMAAtzZXJ2aWNlUGF0aHEAfgAEeHBzcQB+ABkBcHQAGk9TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNldABNb3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5leGNlcHRpb25oYW5kbGluZy5EZWZhdWx0TWVzc2FnZUV4Y2VwdGlvbkhhbmRsZXJzcQB+AB///////////3NxAH4AHAAAAANzcQB+ABkAcQB+ADJwc3IADGphdmEubmV0LlVSTJYlNzYa/ORyAwAHSQAIaGFzaENvZGVJAARwb3J0TAAJYXV0aG9yaXR5cQB+AARMAARmaWxlcQB+AARMAARob3N0cQB+AARMAAhwcm90b2NvbHEAfgAETAADcmVmcQB+AAR4cGkboqAAAB+QdAAObG9jYWxob3N0OjgwODB0ACsva3ItZGV2L3JlbW90aW5nL09TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNldAAJbG9jYWxob3N0dAAEaHR0cHB4dAAAdAAGVFJBVkVMdAABL3NyABNqYXZhLnV0aWwuQXJyYXlMaXN0eIHSHZnHYZ0DAAFJAARzaXpleHAAAAAFdwQAAAAKdAAzb3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5zZXJ2aWNlLktTQkphdmFTZXJ2aWNldAA8Y29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLmJhc2UuZXZlbnRzLkNhY2hlRW50cnlFdmVudExpc3RlbmVydAA3Y29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLmJhc2UuZXZlbnRzLkNhY2hlRXZlbnRMaXN0ZW5lcnQAF2phdmEudXRpbC5FdmVudExpc3RlbmVydAAsY29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLmJhc2UuTGlmZWN5Y2xlQXdhcmV4cQB+ACN0AAZUUkFWRUw=';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
INSERT INTO KRSB_MSG_PYLD_T (MSG_PYLD,MSG_QUE_ID)
  VALUES (EMPTY_CLOB(),441)
/
-- Length: 6120
-- Chunks: 2
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 441    FOR UPDATE;        
    buffer := 'rO0ABXNyAC1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLkFzeW5jaHJvbm91c0NhbGzxnQ5Y0t0lZQIAB1oAFWlnbm9yZVN0b3JlQW5kRm9yd2FyZFsACWFyZ3VtZW50c3QAE1tMamF2YS9sYW5nL09iamVjdDtMAAhjYWxsYmFja3QAM0xvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL0FzeW5jaHJvbm91c0NhbGxiYWNrO0wAB2NvbnRleHR0ABZMamF2YS9pby9TZXJpYWxpemFibGU7TAAKbWV0aG9kTmFtZXQAEkxqYXZhL2xhbmcvU3RyaW5nO1sACnBhcmFtVHlwZXN0ABJbTGphdmEvbGFuZy9DbGFzcztMAAtzZXJ2aWNlSW5mb3QAKkxvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL1NlcnZpY2VJbmZvO3hwAHVyABNbTGphdmEubGFuZy5PYmplY3Q7kM5YnxBzKWwCAAB4cAAAAAFzcgBDY29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLnBsdWdpbnMuY2x1c3RlcnN1cHBvcnQuQ2x1c3Rlck5vdGlmaWNhdGlvbhY+6VckvG9pAgACSQAEdHlwZUwABGRhdGFxAH4AA3hwAAAAAnQAPERvY3VtZW50VHlwZVJ1bGVDYWNoZTpQYXJhbWV0ZXJEZXRhaWxUeXBlTWFpbnRlbmFuY2VEb2N1bWVudHBwdAAGaW52b2tldXIAEltMamF2YS5sYW5nLkNsYXNzO6sW167LzVqZAgAAeHAAAAABdnIAFGphdmEuaW8uU2VyaWFsaXphYmxlEJthDdebZO0CAAB4cHNyAChvcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLlNlcnZpY2VJbmZvxRcjtikEt7oCAAtMAAVhbGl2ZXQAE0xqYXZhL2xhbmcvQm9vbGVhbjtMABRlbmRwb2ludEFsdGVybmF0ZVVybHEAfgAETAALZW5kcG9pbnRVcmxxAH4ABEwACmxvY2tWZXJOYnJ0ABNMamF2YS9sYW5nL0ludGVnZXI7TAAObWVzc2FnZUVudHJ5SWR0ABBMamF2YS9sYW5nL0xvbmc7TAAFcW5hbWV0ABtMamF2YXgveG1sL25hbWVzcGFjZS9RTmFtZTtMABpzZXJpYWxpemVkU2VydmljZU5hbWVzcGFjZXEAfgAETAAIc2VydmVySXBxAH4ABEwAEXNlcnZpY2VEZWZpbml0aW9udAAwTG9yZy9rdWFsaS9yaWNlL2tzYi9tZXNzYWdpbmcvU2VydmljZURlZmluaXRpb247TAALc2VydmljZU5hbWVxAH4ABEwAEHNlcnZpY2VOYW1lc3BhY2VxAH4ABHhwc3IAEWphdmEubGFuZy5Cb29sZWFuzSBygNWc+u4CAAFaAAV2YWx1ZXhwAXB0AEBodHRwOi8vbG9jYWxob3N0OjgwODAva3ItZGV2L3JlbW90aW5nL09TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNlc3IAEWphdmEubGFuZy5JbnRlZ2VyEuKgpPeBhzgCAAFJAAV2YWx1ZXhyABBqYXZhLmxhbmcuTnVtYmVyhqyVHQuU4IsCAAB4cAAAAAFzcgAOamF2YS5sYW5nLkxvbmc7i+SQzI8j3wIAAUoABXZhbHVleHEAfgAdAAAAAAAAD31zcgAZamF2YXgueG1sLm5hbWVzcGFjZS5RTmFtZYFtqC38O91sAgADTAAJbG9jYWxQYXJ0cQB+AARMAAxuYW1lc3BhY2VVUklxAH4ABEwABnByZWZpeHEAfgAEeHB0ABpPU0NhY2hlTm90aWZpY2F0aW9uU2VydmljZXQAAHEAfgAkdAd0ck8wQUJYTnlBREp2Y21jdWEzVmhiR2t1Y21salpTNXJjMkl1YldWemMyRm5hVzVuTGtwaGRtRlRaWEoyYVdObFJHVm1hVzVwZEdsdmJyYm5EVjlCTkd6eEFnQUJUQUFSYzJWeWRtbGpaVWx1ZEdWeVptRmpaWE4wQUJCTWFtRjJZUzkxZEdsc0wweHBjM1E3ZUhJQUxtOXlaeTVyZFdGc2FTNXlhV05sTG10ellpNXRaWE56WVdkcGJtY3VVMlZ5ZG1salpVUmxabWx1YVhScGIyNEFtd0pQV04wcGZnSUFEVXdBQzJKMWMxTmxZM1Z5YVhSNWRBQVRUR3BoZG1FdmJHRnVaeTlDYjI5c1pXRnVPMHdBRDJOeVpXUmxiblJwWVd4elZIbHdaWFFBVEV4dmNtY3ZhM1ZoYkdrdmNtbGpaUzlqYjNKbEwzTmxZM1Z5YVhSNUwyTnlaV1JsYm5ScFlXeHpMME55WldSbGJuUnBZV3h6VTI5MWNtTmxKRU55WldSbGJuUnBZV3h6Vkhsd1pUdE1BQkJzYjJOaGJGTmxjblpwWTJWT1lXMWxkQUFTVEdwaGRtRXZiR0Z1Wnk5VGRISnBibWM3VEFBWGJXVnpjMkZuWlVWNFkyVndkR2x2YmtoaGJtUnNaWEp4QUg0QUJVd0FERzFwYkd4cGMxUnZUR2wyWlhRQUVFeHFZWFpoTDJ4aGJtY3ZURzl1Wnp0TUFBaHdjbWx2Y21sMGVYUUFFMHhxWVhaaEwyeGhibWN2U1c1MFpXZGxjanRNQUFWeGRXVjFaWEVBZmdBRFRBQU5jbVYwY25sQmRIUmxiWEIwYzNFQWZnQUhUQUFIYzJWeWRtbGpaWFFBRWt4cVlYWmhMMnhoYm1jdlQySnFaV04wTzB3QUQzTmxjblpwWTJWRmJtUlFiMmx1ZEhRQURreHFZWFpoTDI1bGRDOVZVa3c3VEFBVGMyVnlkbWxqWlU1aGJXVlRjR0ZqWlZWU1NYRUFmZ0FGVEFBUWMyVnlkbWxqWlU1aGJXVnpjR0ZqWlhFQWZnQUZUQUFMYzJWeWRtbGpaVkJoZEdoeEFINEFCWGh3YzNJQUVXcGhkbUV1YkdGdVp5NUNiMjlzWldGdXpTQnlnTldjK3U0Q0FBRmFBQVYyWVd4MVpYaHdBWEIwQUJwUFUwTmhZMmhsVG05MGFXWnBZMkYwYVc5dVUyVnlkbWxqWlhRQVRXOXlaeTVyZFdGc2FTNXlhV05sTG10ellpNXRaWE56WVdkcGJtY3VaWGhqWlhCMGFXOXVhR0Z1Wkd4cGJtY3VSR1ZtWVhWc2RFMWxjM05oWjJWRmVHTmxjSFJwYjI1SVlXNWtiR1Z5YzNJQURtcGhkbUV1YkdGdVp5NU1iMjVuTzR2a2tNeVBJOThDQUFGS0FBVjJZV3gxWlhoeUFCQnFZWFpoTG14aGJtY3VUblZ0WW1WeWhxeVZIUXVVNElzQ0FBQjRjUC8vLy8vLy8vLy9jM0lBRVdwaGRtRXViR0Z1Wnk1SmJuUmxaMlZ5RXVLZ3BQZUJoemdDQUFGSkFBVjJZV3gxWlhoeEFINEFFQUFBQUFOemNRQitBQXNBY1FCK0FCTndjM0lBREdwaGRtRXVibVYwTGxWU1RKWWxOellhL09SeUF3QUhTUUFJYUdGemFFTnZaR1ZKQUFSd2IzSjBUQUFKWVhWMGFHOXlhWFI1Y1FCK0FBVk1BQVJtYVd4bGNRQitBQVZNQUFSb2IzTjBjUUIrQUFWTUFBaHdjbTkwYjJOdmJIRUFmZ0FGVEFBRGNtVm1jUUIrQUFWNGNQLy8vLzhBQUIrUWRBQU9iRzlqWVd4b2IzTjBPamd3T0RCMEFDc3ZhM0l0WkdWMkwzSmxiVzkwYVc1bkwwOVRRMkZqYUdWT2IzUnBabWxqWVhScGIyNVRaWEoyYVdObGRBQUpiRzlqWVd4b2IzTjBkQUFFYUhSMGNIQjRkQUFBZEFBR1ZGSkJWa1ZNZEFBQkwzTnlBQk5xWVhaaExuVjBhV3d1UVhKeVlYbE1hWE4wZUlIU0habkhZWjBEQUFGSkFBUnphWHBsZUhBQUFBQUZkd1FB';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 441    FOR UPDATE;        
    buffer := 'QUFBS2RBQXpiM0puTG10MVlXeHBMbkpwWTJVdWEzTmlMbTFsYzNOaFoybHVaeTV6WlhKMmFXTmxMa3RUUWtwaGRtRlRaWEoyYVdObGRBQThZMjl0TG05d1pXNXplVzF3YUc5dWVTNXZjMk5oWTJobExtSmhjMlV1WlhabGJuUnpMa05oWTJobFJXNTBjbmxGZG1WdWRFeHBjM1JsYm1WeWRBQTNZMjl0TG05d1pXNXplVzF3YUc5dWVTNXZjMk5oWTJobExtSmhjMlV1WlhabGJuUnpMa05oWTJobFJYWmxiblJNYVhOMFpXNWxjblFBRjJwaGRtRXVkWFJwYkM1RmRtVnVkRXhwYzNSbGJtVnlkQUFzWTI5dExtOXdaVzV6ZVcxd2FHOXVlUzV2YzJOaFkyaGxMbUpoYzJVdVRHbG1aV041WTJ4bFFYZGhjbVY0dAANMTI5LjE4Ni43OS40NXNyADJvcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLkphdmFTZXJ2aWNlRGVmaW5pdGlvbrbnDV9BNGzxAgABTAARc2VydmljZUludGVyZmFjZXN0ABBMamF2YS91dGlsL0xpc3Q7eHIALm9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuU2VydmljZURlZmluaXRpb24AmwJPWN0pfgIADUwAC2J1c1NlY3VyaXR5cQB+ABNMAA9jcmVkZW50aWFsc1R5cGV0AExMb3JnL2t1YWxpL3JpY2UvY29yZS9zZWN1cml0eS9jcmVkZW50aWFscy9DcmVkZW50aWFsc1NvdXJjZSRDcmVkZW50aWFsc1R5cGU7TAAQbG9jYWxTZXJ2aWNlTmFtZXEAfgAETAAXbWVzc2FnZUV4Y2VwdGlvbkhhbmRsZXJxAH4ABEwADG1pbGxpc1RvTGl2ZXEAfgAVTAAIcHJpb3JpdHlxAH4AFEwABXF1ZXVlcQB+ABNMAA1yZXRyeUF0dGVtcHRzcQB+ABRMAAdzZXJ2aWNldAASTGphdmEvbGFuZy9PYmplY3Q7TAAPc2VydmljZUVuZFBvaW50dAAOTGphdmEvbmV0L1VSTDtMABNzZXJ2aWNlTmFtZVNwYWNlVVJJcQB+AARMABBzZXJ2aWNlTmFtZXNwYWNlcQB+AARMAAtzZXJ2aWNlUGF0aHEAfgAEeHBzcQB+ABkBcHQAGk9TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNldABNb3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5leGNlcHRpb25oYW5kbGluZy5EZWZhdWx0TWVzc2FnZUV4Y2VwdGlvbkhhbmRsZXJzcQB+AB///////////3NxAH4AHAAAAANzcQB+ABkAcQB+ADJwc3IADGphdmEubmV0LlVSTJYlNzYa/ORyAwAHSQAIaGFzaENvZGVJAARwb3J0TAAJYXV0aG9yaXR5cQB+AARMAARmaWxlcQB+AARMAARob3N0cQB+AARMAAhwcm90b2NvbHEAfgAETAADcmVmcQB+AAR4cGkboqAAAB+QdAAObG9jYWxob3N0OjgwODB0ACsva3ItZGV2L3JlbW90aW5nL09TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNldAAJbG9jYWxob3N0dAAEaHR0cHB4dAAAdAAGVFJBVkVMdAABL3NyABNqYXZhLnV0aWwuQXJyYXlMaXN0eIHSHZnHYZ0DAAFJAARzaXpleHAAAAAFdwQAAAAKdAAzb3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5zZXJ2aWNlLktTQkphdmFTZXJ2aWNldAA8Y29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLmJhc2UuZXZlbnRzLkNhY2hlRW50cnlFdmVudExpc3RlbmVydAA3Y29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLmJhc2UuZXZlbnRzLkNhY2hlRXZlbnRMaXN0ZW5lcnQAF2phdmEudXRpbC5FdmVudExpc3RlbmVydAAsY29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLmJhc2UuTGlmZWN5Y2xlQXdhcmV4cQB+ACN0AAZUUkFWRUw=';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
INSERT INTO KRSB_MSG_PYLD_T (MSG_PYLD,MSG_QUE_ID)
  VALUES (EMPTY_CLOB(),442)
/
-- Length: 6108
-- Chunks: 2
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 442    FOR UPDATE;        
    buffer := 'rO0ABXNyAC1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLkFzeW5jaHJvbm91c0NhbGzxnQ5Y0t0lZQIAB1oAFWlnbm9yZVN0b3JlQW5kRm9yd2FyZFsACWFyZ3VtZW50c3QAE1tMamF2YS9sYW5nL09iamVjdDtMAAhjYWxsYmFja3QAM0xvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL0FzeW5jaHJvbm91c0NhbGxiYWNrO0wAB2NvbnRleHR0ABZMamF2YS9pby9TZXJpYWxpemFibGU7TAAKbWV0aG9kTmFtZXQAEkxqYXZhL2xhbmcvU3RyaW5nO1sACnBhcmFtVHlwZXN0ABJbTGphdmEvbGFuZy9DbGFzcztMAAtzZXJ2aWNlSW5mb3QAKkxvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL1NlcnZpY2VJbmZvO3hwAHVyABNbTGphdmEubGFuZy5PYmplY3Q7kM5YnxBzKWwCAAB4cAAAAAFzcgBDY29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLnBsdWdpbnMuY2x1c3RlcnN1cHBvcnQuQ2x1c3Rlck5vdGlmaWNhdGlvbhY+6VckvG9pAgACSQAEdHlwZUwABGRhdGFxAH4AA3hwAAAAAnQAMkRvY3VtZW50VHlwZVJ1bGVDYWNoZTpQYXJhbWV0ZXJNYWludGVuYW5jZURvY3VtZW50cHB0AAZpbnZva2V1cgASW0xqYXZhLmxhbmcuQ2xhc3M7qxbXrsvNWpkCAAB4cAAAAAF2cgAUamF2YS5pby5TZXJpYWxpemFibGUQm2EN15tk7QIAAHhwc3IAKG9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuU2VydmljZUluZm/FFyO2KQS3ugIAC0wABWFsaXZldAATTGphdmEvbGFuZy9Cb29sZWFuO0wAFGVuZHBvaW50QWx0ZXJuYXRlVXJscQB+AARMAAtlbmRwb2ludFVybHEAfgAETAAKbG9ja1Zlck5icnQAE0xqYXZhL2xhbmcvSW50ZWdlcjtMAA5tZXNzYWdlRW50cnlJZHQAEExqYXZhL2xhbmcvTG9uZztMAAVxbmFtZXQAG0xqYXZheC94bWwvbmFtZXNwYWNlL1FOYW1lO0wAGnNlcmlhbGl6ZWRTZXJ2aWNlTmFtZXNwYWNlcQB+AARMAAhzZXJ2ZXJJcHEAfgAETAARc2VydmljZURlZmluaXRpb250ADBMb3JnL2t1YWxpL3JpY2Uva3NiL21lc3NhZ2luZy9TZXJ2aWNlRGVmaW5pdGlvbjtMAAtzZXJ2aWNlTmFtZXEAfgAETAAQc2VydmljZU5hbWVzcGFjZXEAfgAEeHBzcgARamF2YS5sYW5nLkJvb2xlYW7NIHKA1Zz67gIAAVoABXZhbHVleHABcHQAQGh0dHA6Ly9sb2NhbGhvc3Q6ODA4MC9rci1kZXYvcmVtb3RpbmcvT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2VzcgARamF2YS5sYW5nLkludGVnZXIS4qCk94GHOAIAAUkABXZhbHVleHIAEGphdmEubGFuZy5OdW1iZXKGrJUdC5TgiwIAAHhwAAAAAXNyAA5qYXZhLmxhbmcuTG9uZzuL5JDMjyPfAgABSgAFdmFsdWV4cQB+AB0AAAAAAAAPfXNyABlqYXZheC54bWwubmFtZXNwYWNlLlFOYW1lgW2oLfw73WwCAANMAAlsb2NhbFBhcnRxAH4ABEwADG5hbWVzcGFjZVVSSXEAfgAETAAGcHJlZml4cQB+AAR4cHQAGk9TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNldAAAcQB+ACR0B3RyTzBBQlhOeUFESnZjbWN1YTNWaGJHa3VjbWxqWlM1cmMySXViV1Z6YzJGbmFXNW5Ma3BoZG1GVFpYSjJhV05sUkdWbWFXNXBkR2x2YnJibkRWOUJOR3p4QWdBQlRBQVJjMlZ5ZG1salpVbHVkR1Z5Wm1GalpYTjBBQkJNYW1GMllTOTFkR2xzTDB4cGMzUTdlSElBTG05eVp5NXJkV0ZzYVM1eWFXTmxMbXR6WWk1dFpYTnpZV2RwYm1jdVUyVnlkbWxqWlVSbFptbHVhWFJwYjI0QW13SlBXTjBwZmdJQURVd0FDMkoxYzFObFkzVnlhWFI1ZEFBVFRHcGhkbUV2YkdGdVp5OUNiMjlzWldGdU8wd0FEMk55WldSbGJuUnBZV3h6Vkhsd1pYUUFURXh2Y21jdmEzVmhiR2t2Y21salpTOWpiM0psTDNObFkzVnlhWFI1TDJOeVpXUmxiblJwWVd4ekwwTnlaV1JsYm5ScFlXeHpVMjkxY21ObEpFTnlaV1JsYm5ScFlXeHpWSGx3WlR0TUFCQnNiMk5oYkZObGNuWnBZMlZPWVcxbGRBQVNUR3BoZG1FdmJHRnVaeTlUZEhKcGJtYzdUQUFYYldWemMyRm5aVVY0WTJWd2RHbHZia2hoYm1Sc1pYSnhBSDRBQlV3QURHMXBiR3hwYzFSdlRHbDJaWFFBRUV4cVlYWmhMMnhoYm1jdlRHOXVaenRNQUFod2NtbHZjbWwwZVhRQUUweHFZWFpoTDJ4aGJtY3ZTVzUwWldkbGNqdE1BQVZ4ZFdWMVpYRUFmZ0FEVEFBTmNtVjBjbmxCZEhSbGJYQjBjM0VBZmdBSFRBQUhjMlZ5ZG1salpYUUFFa3hxWVhaaEwyeGhibWN2VDJKcVpXTjBPMHdBRDNObGNuWnBZMlZGYm1SUWIybHVkSFFBRGt4cVlYWmhMMjVsZEM5VlVrdzdUQUFUYzJWeWRtbGpaVTVoYldWVGNHRmpaVlZTU1hFQWZnQUZUQUFRYzJWeWRtbGpaVTVoYldWemNHRmpaWEVBZmdBRlRBQUxjMlZ5ZG1salpWQmhkR2h4QUg0QUJYaHdjM0lBRVdwaGRtRXViR0Z1Wnk1Q2IyOXNaV0Z1elNCeWdOV2MrdTRDQUFGYUFBVjJZV3gxWlhod0FYQjBBQnBQVTBOaFkyaGxUbTkwYVdacFkyRjBhVzl1VTJWeWRtbGpaWFFBVFc5eVp5NXJkV0ZzYVM1eWFXTmxMbXR6WWk1dFpYTnpZV2RwYm1jdVpYaGpaWEIwYVc5dWFHRnVaR3hwYm1jdVJHVm1ZWFZzZEUxbGMzTmhaMlZGZUdObGNIUnBiMjVJWVc1a2JHVnljM0lBRG1waGRtRXViR0Z1Wnk1TWIyNW5PNHZra015UEk5OENBQUZLQUFWMllXeDFaWGh5QUJCcVlYWmhMbXhoYm1jdVRuVnRZbVZ5aHF5VkhRdVU0SXNDQUFCNGNQLy8vLy8vLy8vL2MzSUFFV3BoZG1FdWJHRnVaeTVKYm5SbFoyVnlFdUtncFBlQmh6Z0NBQUZKQUFWMllXeDFaWGh4QUg0QUVBQUFBQU56Y1FCK0FBc0FjUUIrQUJOd2MzSUFER3BoZG1FdWJtVjBMbFZTVEpZbE56WWEvT1J5QXdBSFNRQUlhR0Z6YUVOdlpHVkpBQVJ3YjNKMFRBQUpZWFYwYUc5eWFYUjVjUUIrQUFWTUFBUm1hV3hsY1FCK0FBVk1BQVJvYjNOMGNRQitBQVZNQUFod2NtOTBiMk52YkhFQWZnQUZUQUFEY21WbWNRQitBQVY0Y1AvLy8vOEFBQitRZEFBT2JHOWpZV3hvYjNOME9qZ3dPREIwQUNzdmEzSXRaR1YyTDNKbGJXOTBhVzVuTDA5VFEyRmphR1ZPYjNScFptbGpZWFJwYjI1VFpYSjJhV05sZEFBSmJHOWpZV3hvYjNOMGRBQUVhSFIwY0hCNGRBQUFkQUFHVkZKQlZrVk1kQUFCTDNOeUFCTnFZWFpoTG5WMGFXd3VRWEp5WVhsTWFYTjBlSUhTSFpuSFlaMERBQUZKQUFSemFYcGxlSEFBQUFBRmR3UUFBQUFLZEFBemIz';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 442    FOR UPDATE;        
    buffer := 'Sm5MbXQxWVd4cExuSnBZMlV1YTNOaUxtMWxjM05oWjJsdVp5NXpaWEoyYVdObExrdFRRa3BoZG1GVFpYSjJhV05sZEFBOFkyOXRMbTl3Wlc1emVXMXdhRzl1ZVM1dmMyTmhZMmhsTG1KaGMyVXVaWFpsYm5SekxrTmhZMmhsUlc1MGNubEZkbVZ1ZEV4cGMzUmxibVZ5ZEFBM1kyOXRMbTl3Wlc1emVXMXdhRzl1ZVM1dmMyTmhZMmhsTG1KaGMyVXVaWFpsYm5SekxrTmhZMmhsUlhabGJuUk1hWE4wWlc1bGNuUUFGMnBoZG1FdWRYUnBiQzVGZG1WdWRFeHBjM1JsYm1WeWRBQXNZMjl0TG05d1pXNXplVzF3YUc5dWVTNXZjMk5oWTJobExtSmhjMlV1VEdsbVpXTjVZMnhsUVhkaGNtVjR0AA0xMjkuMTg2Ljc5LjQ1c3IAMm9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuSmF2YVNlcnZpY2VEZWZpbml0aW9utucNX0E0bPECAAFMABFzZXJ2aWNlSW50ZXJmYWNlc3QAEExqYXZhL3V0aWwvTGlzdDt4cgAub3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5TZXJ2aWNlRGVmaW5pdGlvbgCbAk9Y3Sl+AgANTAALYnVzU2VjdXJpdHlxAH4AE0wAD2NyZWRlbnRpYWxzVHlwZXQATExvcmcva3VhbGkvcmljZS9jb3JlL3NlY3VyaXR5L2NyZWRlbnRpYWxzL0NyZWRlbnRpYWxzU291cmNlJENyZWRlbnRpYWxzVHlwZTtMABBsb2NhbFNlcnZpY2VOYW1lcQB+AARMABdtZXNzYWdlRXhjZXB0aW9uSGFuZGxlcnEAfgAETAAMbWlsbGlzVG9MaXZlcQB+ABVMAAhwcmlvcml0eXEAfgAUTAAFcXVldWVxAH4AE0wADXJldHJ5QXR0ZW1wdHNxAH4AFEwAB3NlcnZpY2V0ABJMamF2YS9sYW5nL09iamVjdDtMAA9zZXJ2aWNlRW5kUG9pbnR0AA5MamF2YS9uZXQvVVJMO0wAE3NlcnZpY2VOYW1lU3BhY2VVUklxAH4ABEwAEHNlcnZpY2VOYW1lc3BhY2VxAH4ABEwAC3NlcnZpY2VQYXRocQB+AAR4cHNxAH4AGQFwdAAaT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2V0AE1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLmV4Y2VwdGlvbmhhbmRsaW5nLkRlZmF1bHRNZXNzYWdlRXhjZXB0aW9uSGFuZGxlcnNxAH4AH///////////c3EAfgAcAAAAA3NxAH4AGQBxAH4AMnBzcgAMamF2YS5uZXQuVVJMliU3Nhr85HIDAAdJAAhoYXNoQ29kZUkABHBvcnRMAAlhdXRob3JpdHlxAH4ABEwABGZpbGVxAH4ABEwABGhvc3RxAH4ABEwACHByb3RvY29scQB+AARMAANyZWZxAH4ABHhwaRuioAAAH5B0AA5sb2NhbGhvc3Q6ODA4MHQAKy9rci1kZXYvcmVtb3RpbmcvT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2V0AAlsb2NhbGhvc3R0AARodHRwcHh0AAB0AAZUUkFWRUx0AAEvc3IAE2phdmEudXRpbC5BcnJheUxpc3R4gdIdmcdhnQMAAUkABHNpemV4cAAAAAV3BAAAAAp0ADNvcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLnNlcnZpY2UuS1NCSmF2YVNlcnZpY2V0ADxjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5ldmVudHMuQ2FjaGVFbnRyeUV2ZW50TGlzdGVuZXJ0ADdjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5ldmVudHMuQ2FjaGVFdmVudExpc3RlbmVydAAXamF2YS51dGlsLkV2ZW50TGlzdGVuZXJ0ACxjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5MaWZlY3ljbGVBd2FyZXhxAH4AI3QABlRSQVZFTA==';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
INSERT INTO KRSB_MSG_PYLD_T (MSG_PYLD,MSG_QUE_ID)
  VALUES (EMPTY_CLOB(),443)
/
-- Length: 6088
-- Chunks: 2
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 443    FOR UPDATE;        
    buffer := 'rO0ABXNyAC1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLkFzeW5jaHJvbm91c0NhbGzxnQ5Y0t0lZQIAB1oAFWlnbm9yZVN0b3JlQW5kRm9yd2FyZFsACWFyZ3VtZW50c3QAE1tMamF2YS9sYW5nL09iamVjdDtMAAhjYWxsYmFja3QAM0xvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL0FzeW5jaHJvbm91c0NhbGxiYWNrO0wAB2NvbnRleHR0ABZMamF2YS9pby9TZXJpYWxpemFibGU7TAAKbWV0aG9kTmFtZXQAEkxqYXZhL2xhbmcvU3RyaW5nO1sACnBhcmFtVHlwZXN0ABJbTGphdmEvbGFuZy9DbGFzcztMAAtzZXJ2aWNlSW5mb3QAKkxvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL1NlcnZpY2VJbmZvO3hwAHVyABNbTGphdmEubGFuZy5PYmplY3Q7kM5YnxBzKWwCAAB4cAAAAAFzcgBDY29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLnBsdWdpbnMuY2x1c3RlcnN1cHBvcnQuQ2x1c3Rlck5vdGlmaWNhdGlvbhY+6VckvG9pAgACSQAEdHlwZUwABGRhdGFxAH4AA3hwAAAAAnQAI0RvY3VtZW50VHlwZVJ1bGVDYWNoZTpUcmF2ZWxSZXF1ZXN0cHB0AAZpbnZva2V1cgASW0xqYXZhLmxhbmcuQ2xhc3M7qxbXrsvNWpkCAAB4cAAAAAF2cgAUamF2YS5pby5TZXJpYWxpemFibGUQm2EN15tk7QIAAHhwc3IAKG9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuU2VydmljZUluZm/FFyO2KQS3ugIAC0wABWFsaXZldAATTGphdmEvbGFuZy9Cb29sZWFuO0wAFGVuZHBvaW50QWx0ZXJuYXRlVXJscQB+AARMAAtlbmRwb2ludFVybHEAfgAETAAKbG9ja1Zlck5icnQAE0xqYXZhL2xhbmcvSW50ZWdlcjtMAA5tZXNzYWdlRW50cnlJZHQAEExqYXZhL2xhbmcvTG9uZztMAAVxbmFtZXQAG0xqYXZheC94bWwvbmFtZXNwYWNlL1FOYW1lO0wAGnNlcmlhbGl6ZWRTZXJ2aWNlTmFtZXNwYWNlcQB+AARMAAhzZXJ2ZXJJcHEAfgAETAARc2VydmljZURlZmluaXRpb250ADBMb3JnL2t1YWxpL3JpY2Uva3NiL21lc3NhZ2luZy9TZXJ2aWNlRGVmaW5pdGlvbjtMAAtzZXJ2aWNlTmFtZXEAfgAETAAQc2VydmljZU5hbWVzcGFjZXEAfgAEeHBzcgARamF2YS5sYW5nLkJvb2xlYW7NIHKA1Zz67gIAAVoABXZhbHVleHABcHQAQGh0dHA6Ly9sb2NhbGhvc3Q6ODA4MC9rci1kZXYvcmVtb3RpbmcvT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2VzcgARamF2YS5sYW5nLkludGVnZXIS4qCk94GHOAIAAUkABXZhbHVleHIAEGphdmEubGFuZy5OdW1iZXKGrJUdC5TgiwIAAHhwAAAAAXNyAA5qYXZhLmxhbmcuTG9uZzuL5JDMjyPfAgABSgAFdmFsdWV4cQB+AB0AAAAAAAAPfXNyABlqYXZheC54bWwubmFtZXNwYWNlLlFOYW1lgW2oLfw73WwCAANMAAlsb2NhbFBhcnRxAH4ABEwADG5hbWVzcGFjZVVSSXEAfgAETAAGcHJlZml4cQB+AAR4cHQAGk9TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNldAAAcQB+ACR0B3RyTzBBQlhOeUFESnZjbWN1YTNWaGJHa3VjbWxqWlM1cmMySXViV1Z6YzJGbmFXNW5Ma3BoZG1GVFpYSjJhV05sUkdWbWFXNXBkR2x2YnJibkRWOUJOR3p4QWdBQlRBQVJjMlZ5ZG1salpVbHVkR1Z5Wm1GalpYTjBBQkJNYW1GMllTOTFkR2xzTDB4cGMzUTdlSElBTG05eVp5NXJkV0ZzYVM1eWFXTmxMbXR6WWk1dFpYTnpZV2RwYm1jdVUyVnlkbWxqWlVSbFptbHVhWFJwYjI0QW13SlBXTjBwZmdJQURVd0FDMkoxYzFObFkzVnlhWFI1ZEFBVFRHcGhkbUV2YkdGdVp5OUNiMjlzWldGdU8wd0FEMk55WldSbGJuUnBZV3h6Vkhsd1pYUUFURXh2Y21jdmEzVmhiR2t2Y21salpTOWpiM0psTDNObFkzVnlhWFI1TDJOeVpXUmxiblJwWVd4ekwwTnlaV1JsYm5ScFlXeHpVMjkxY21ObEpFTnlaV1JsYm5ScFlXeHpWSGx3WlR0TUFCQnNiMk5oYkZObGNuWnBZMlZPWVcxbGRBQVNUR3BoZG1FdmJHRnVaeTlUZEhKcGJtYzdUQUFYYldWemMyRm5aVVY0WTJWd2RHbHZia2hoYm1Sc1pYSnhBSDRBQlV3QURHMXBiR3hwYzFSdlRHbDJaWFFBRUV4cVlYWmhMMnhoYm1jdlRHOXVaenRNQUFod2NtbHZjbWwwZVhRQUUweHFZWFpoTDJ4aGJtY3ZTVzUwWldkbGNqdE1BQVZ4ZFdWMVpYRUFmZ0FEVEFBTmNtVjBjbmxCZEhSbGJYQjBjM0VBZmdBSFRBQUhjMlZ5ZG1salpYUUFFa3hxWVhaaEwyeGhibWN2VDJKcVpXTjBPMHdBRDNObGNuWnBZMlZGYm1SUWIybHVkSFFBRGt4cVlYWmhMMjVsZEM5VlVrdzdUQUFUYzJWeWRtbGpaVTVoYldWVGNHRmpaVlZTU1hFQWZnQUZUQUFRYzJWeWRtbGpaVTVoYldWemNHRmpaWEVBZmdBRlRBQUxjMlZ5ZG1salpWQmhkR2h4QUg0QUJYaHdjM0lBRVdwaGRtRXViR0Z1Wnk1Q2IyOXNaV0Z1elNCeWdOV2MrdTRDQUFGYUFBVjJZV3gxWlhod0FYQjBBQnBQVTBOaFkyaGxUbTkwYVdacFkyRjBhVzl1VTJWeWRtbGpaWFFBVFc5eVp5NXJkV0ZzYVM1eWFXTmxMbXR6WWk1dFpYTnpZV2RwYm1jdVpYaGpaWEIwYVc5dWFHRnVaR3hwYm1jdVJHVm1ZWFZzZEUxbGMzTmhaMlZGZUdObGNIUnBiMjVJWVc1a2JHVnljM0lBRG1waGRtRXViR0Z1Wnk1TWIyNW5PNHZra015UEk5OENBQUZLQUFWMllXeDFaWGh5QUJCcVlYWmhMbXhoYm1jdVRuVnRZbVZ5aHF5VkhRdVU0SXNDQUFCNGNQLy8vLy8vLy8vL2MzSUFFV3BoZG1FdWJHRnVaeTVKYm5SbFoyVnlFdUtncFBlQmh6Z0NBQUZKQUFWMllXeDFaWGh4QUg0QUVBQUFBQU56Y1FCK0FBc0FjUUIrQUJOd2MzSUFER3BoZG1FdWJtVjBMbFZTVEpZbE56WWEvT1J5QXdBSFNRQUlhR0Z6YUVOdlpHVkpBQVJ3YjNKMFRBQUpZWFYwYUc5eWFYUjVjUUIrQUFWTUFBUm1hV3hsY1FCK0FBVk1BQVJvYjNOMGNRQitBQVZNQUFod2NtOTBiMk52YkhFQWZnQUZUQUFEY21WbWNRQitBQVY0Y1AvLy8vOEFBQitRZEFBT2JHOWpZV3hvYjNOME9qZ3dPREIwQUNzdmEzSXRaR1YyTDNKbGJXOTBhVzVuTDA5VFEyRmphR1ZPYjNScFptbGpZWFJwYjI1VFpYSjJhV05sZEFBSmJHOWpZV3hvYjNOMGRBQUVhSFIwY0hCNGRBQUFkQUFHVkZKQlZrVk1kQUFCTDNOeUFCTnFZWFpoTG5WMGFXd3VRWEp5WVhsTWFYTjBlSUhTSFpuSFlaMERBQUZKQUFSemFYcGxlSEFBQUFBRmR3UUFBQUFLZEFBemIzSm5MbXQxWVd4cExuSnBZ';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 443    FOR UPDATE;        
    buffer := 'MlV1YTNOaUxtMWxjM05oWjJsdVp5NXpaWEoyYVdObExrdFRRa3BoZG1GVFpYSjJhV05sZEFBOFkyOXRMbTl3Wlc1emVXMXdhRzl1ZVM1dmMyTmhZMmhsTG1KaGMyVXVaWFpsYm5SekxrTmhZMmhsUlc1MGNubEZkbVZ1ZEV4cGMzUmxibVZ5ZEFBM1kyOXRMbTl3Wlc1emVXMXdhRzl1ZVM1dmMyTmhZMmhsTG1KaGMyVXVaWFpsYm5SekxrTmhZMmhsUlhabGJuUk1hWE4wWlc1bGNuUUFGMnBoZG1FdWRYUnBiQzVGZG1WdWRFeHBjM1JsYm1WeWRBQXNZMjl0TG05d1pXNXplVzF3YUc5dWVTNXZjMk5oWTJobExtSmhjMlV1VEdsbVpXTjVZMnhsUVhkaGNtVjR0AA0xMjkuMTg2Ljc5LjQ1c3IAMm9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuSmF2YVNlcnZpY2VEZWZpbml0aW9utucNX0E0bPECAAFMABFzZXJ2aWNlSW50ZXJmYWNlc3QAEExqYXZhL3V0aWwvTGlzdDt4cgAub3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5TZXJ2aWNlRGVmaW5pdGlvbgCbAk9Y3Sl+AgANTAALYnVzU2VjdXJpdHlxAH4AE0wAD2NyZWRlbnRpYWxzVHlwZXQATExvcmcva3VhbGkvcmljZS9jb3JlL3NlY3VyaXR5L2NyZWRlbnRpYWxzL0NyZWRlbnRpYWxzU291cmNlJENyZWRlbnRpYWxzVHlwZTtMABBsb2NhbFNlcnZpY2VOYW1lcQB+AARMABdtZXNzYWdlRXhjZXB0aW9uSGFuZGxlcnEAfgAETAAMbWlsbGlzVG9MaXZlcQB+ABVMAAhwcmlvcml0eXEAfgAUTAAFcXVldWVxAH4AE0wADXJldHJ5QXR0ZW1wdHNxAH4AFEwAB3NlcnZpY2V0ABJMamF2YS9sYW5nL09iamVjdDtMAA9zZXJ2aWNlRW5kUG9pbnR0AA5MamF2YS9uZXQvVVJMO0wAE3NlcnZpY2VOYW1lU3BhY2VVUklxAH4ABEwAEHNlcnZpY2VOYW1lc3BhY2VxAH4ABEwAC3NlcnZpY2VQYXRocQB+AAR4cHNxAH4AGQFwdAAaT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2V0AE1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLmV4Y2VwdGlvbmhhbmRsaW5nLkRlZmF1bHRNZXNzYWdlRXhjZXB0aW9uSGFuZGxlcnNxAH4AH///////////c3EAfgAcAAAAA3NxAH4AGQBxAH4AMnBzcgAMamF2YS5uZXQuVVJMliU3Nhr85HIDAAdJAAhoYXNoQ29kZUkABHBvcnRMAAlhdXRob3JpdHlxAH4ABEwABGZpbGVxAH4ABEwABGhvc3RxAH4ABEwACHByb3RvY29scQB+AARMAANyZWZxAH4ABHhwaRuioAAAH5B0AA5sb2NhbGhvc3Q6ODA4MHQAKy9rci1kZXYvcmVtb3RpbmcvT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2V0AAlsb2NhbGhvc3R0AARodHRwcHh0AAB0AAZUUkFWRUx0AAEvc3IAE2phdmEudXRpbC5BcnJheUxpc3R4gdIdmcdhnQMAAUkABHNpemV4cAAAAAV3BAAAAAp0ADNvcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLnNlcnZpY2UuS1NCSmF2YVNlcnZpY2V0ADxjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5ldmVudHMuQ2FjaGVFbnRyeUV2ZW50TGlzdGVuZXJ0ADdjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5ldmVudHMuQ2FjaGVFdmVudExpc3RlbmVydAAXamF2YS51dGlsLkV2ZW50TGlzdGVuZXJ0ACxjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5MaWZlY3ljbGVBd2FyZXhxAH4AI3QABlRSQVZFTA==';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
INSERT INTO KRSB_MSG_PYLD_T (MSG_PYLD,MSG_QUE_ID)
  VALUES (EMPTY_CLOB(),444)
/
-- Length: 6112
-- Chunks: 2
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 444    FOR UPDATE;        
    buffer := 'rO0ABXNyAC1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLkFzeW5jaHJvbm91c0NhbGzxnQ5Y0t0lZQIAB1oAFWlnbm9yZVN0b3JlQW5kRm9yd2FyZFsACWFyZ3VtZW50c3QAE1tMamF2YS9sYW5nL09iamVjdDtMAAhjYWxsYmFja3QAM0xvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL0FzeW5jaHJvbm91c0NhbGxiYWNrO0wAB2NvbnRleHR0ABZMamF2YS9pby9TZXJpYWxpemFibGU7TAAKbWV0aG9kTmFtZXQAEkxqYXZhL2xhbmcvU3RyaW5nO1sACnBhcmFtVHlwZXN0ABJbTGphdmEvbGFuZy9DbGFzcztMAAtzZXJ2aWNlSW5mb3QAKkxvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL1NlcnZpY2VJbmZvO3hwAHVyABNbTGphdmEubGFuZy5PYmplY3Q7kM5YnxBzKWwCAAB4cAAAAAFzcgBDY29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLnBsdWdpbnMuY2x1c3RlcnN1cHBvcnQuQ2x1c3Rlck5vdGlmaWNhdGlvbhY+6VckvG9pAgACSQAEdHlwZUwABGRhdGFxAH4AA3hwAAAAAnQANkRvY3VtZW50VHlwZVJ1bGVDYWNoZTpJZGVudGl0eU1hbmFnZW1lbnRQZXJzb25Eb2N1bWVudHBwdAAGaW52b2tldXIAEltMamF2YS5sYW5nLkNsYXNzO6sW167LzVqZAgAAeHAAAAABdnIAFGphdmEuaW8uU2VyaWFsaXphYmxlEJthDdebZO0CAAB4cHNyAChvcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLlNlcnZpY2VJbmZvxRcjtikEt7oCAAtMAAVhbGl2ZXQAE0xqYXZhL2xhbmcvQm9vbGVhbjtMABRlbmRwb2ludEFsdGVybmF0ZVVybHEAfgAETAALZW5kcG9pbnRVcmxxAH4ABEwACmxvY2tWZXJOYnJ0ABNMamF2YS9sYW5nL0ludGVnZXI7TAAObWVzc2FnZUVudHJ5SWR0ABBMamF2YS9sYW5nL0xvbmc7TAAFcW5hbWV0ABtMamF2YXgveG1sL25hbWVzcGFjZS9RTmFtZTtMABpzZXJpYWxpemVkU2VydmljZU5hbWVzcGFjZXEAfgAETAAIc2VydmVySXBxAH4ABEwAEXNlcnZpY2VEZWZpbml0aW9udAAwTG9yZy9rdWFsaS9yaWNlL2tzYi9tZXNzYWdpbmcvU2VydmljZURlZmluaXRpb247TAALc2VydmljZU5hbWVxAH4ABEwAEHNlcnZpY2VOYW1lc3BhY2VxAH4ABHhwc3IAEWphdmEubGFuZy5Cb29sZWFuzSBygNWc+u4CAAFaAAV2YWx1ZXhwAXB0AEBodHRwOi8vbG9jYWxob3N0OjgwODAva3ItZGV2L3JlbW90aW5nL09TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNlc3IAEWphdmEubGFuZy5JbnRlZ2VyEuKgpPeBhzgCAAFJAAV2YWx1ZXhyABBqYXZhLmxhbmcuTnVtYmVyhqyVHQuU4IsCAAB4cAAAAAFzcgAOamF2YS5sYW5nLkxvbmc7i+SQzI8j3wIAAUoABXZhbHVleHEAfgAdAAAAAAAAD31zcgAZamF2YXgueG1sLm5hbWVzcGFjZS5RTmFtZYFtqC38O91sAgADTAAJbG9jYWxQYXJ0cQB+AARMAAxuYW1lc3BhY2VVUklxAH4ABEwABnByZWZpeHEAfgAEeHB0ABpPU0NhY2hlTm90aWZpY2F0aW9uU2VydmljZXQAAHEAfgAkdAd0ck8wQUJYTnlBREp2Y21jdWEzVmhiR2t1Y21salpTNXJjMkl1YldWemMyRm5hVzVuTGtwaGRtRlRaWEoyYVdObFJHVm1hVzVwZEdsdmJyYm5EVjlCTkd6eEFnQUJUQUFSYzJWeWRtbGpaVWx1ZEdWeVptRmpaWE4wQUJCTWFtRjJZUzkxZEdsc0wweHBjM1E3ZUhJQUxtOXlaeTVyZFdGc2FTNXlhV05sTG10ellpNXRaWE56WVdkcGJtY3VVMlZ5ZG1salpVUmxabWx1YVhScGIyNEFtd0pQV04wcGZnSUFEVXdBQzJKMWMxTmxZM1Z5YVhSNWRBQVRUR3BoZG1FdmJHRnVaeTlDYjI5c1pXRnVPMHdBRDJOeVpXUmxiblJwWVd4elZIbHdaWFFBVEV4dmNtY3ZhM1ZoYkdrdmNtbGpaUzlqYjNKbEwzTmxZM1Z5YVhSNUwyTnlaV1JsYm5ScFlXeHpMME55WldSbGJuUnBZV3h6VTI5MWNtTmxKRU55WldSbGJuUnBZV3h6Vkhsd1pUdE1BQkJzYjJOaGJGTmxjblpwWTJWT1lXMWxkQUFTVEdwaGRtRXZiR0Z1Wnk5VGRISnBibWM3VEFBWGJXVnpjMkZuWlVWNFkyVndkR2x2YmtoaGJtUnNaWEp4QUg0QUJVd0FERzFwYkd4cGMxUnZUR2wyWlhRQUVFeHFZWFpoTDJ4aGJtY3ZURzl1Wnp0TUFBaHdjbWx2Y21sMGVYUUFFMHhxWVhaaEwyeGhibWN2U1c1MFpXZGxjanRNQUFWeGRXVjFaWEVBZmdBRFRBQU5jbVYwY25sQmRIUmxiWEIwYzNFQWZnQUhUQUFIYzJWeWRtbGpaWFFBRWt4cVlYWmhMMnhoYm1jdlQySnFaV04wTzB3QUQzTmxjblpwWTJWRmJtUlFiMmx1ZEhRQURreHFZWFpoTDI1bGRDOVZVa3c3VEFBVGMyVnlkbWxqWlU1aGJXVlRjR0ZqWlZWU1NYRUFmZ0FGVEFBUWMyVnlkbWxqWlU1aGJXVnpjR0ZqWlhFQWZnQUZUQUFMYzJWeWRtbGpaVkJoZEdoeEFINEFCWGh3YzNJQUVXcGhkbUV1YkdGdVp5NUNiMjlzWldGdXpTQnlnTldjK3U0Q0FBRmFBQVYyWVd4MVpYaHdBWEIwQUJwUFUwTmhZMmhsVG05MGFXWnBZMkYwYVc5dVUyVnlkbWxqWlhRQVRXOXlaeTVyZFdGc2FTNXlhV05sTG10ellpNXRaWE56WVdkcGJtY3VaWGhqWlhCMGFXOXVhR0Z1Wkd4cGJtY3VSR1ZtWVhWc2RFMWxjM05oWjJWRmVHTmxjSFJwYjI1SVlXNWtiR1Z5YzNJQURtcGhkbUV1YkdGdVp5NU1iMjVuTzR2a2tNeVBJOThDQUFGS0FBVjJZV3gxWlhoeUFCQnFZWFpoTG14aGJtY3VUblZ0WW1WeWhxeVZIUXVVNElzQ0FBQjRjUC8vLy8vLy8vLy9jM0lBRVdwaGRtRXViR0Z1Wnk1SmJuUmxaMlZ5RXVLZ3BQZUJoemdDQUFGSkFBVjJZV3gxWlhoeEFINEFFQUFBQUFOemNRQitBQXNBY1FCK0FCTndjM0lBREdwaGRtRXVibVYwTGxWU1RKWWxOellhL09SeUF3QUhTUUFJYUdGemFFTnZaR1ZKQUFSd2IzSjBUQUFKWVhWMGFHOXlhWFI1Y1FCK0FBVk1BQVJtYVd4bGNRQitBQVZNQUFSb2IzTjBjUUIrQUFWTUFBaHdjbTkwYjJOdmJIRUFmZ0FGVEFBRGNtVm1jUUIrQUFWNGNQLy8vLzhBQUIrUWRBQU9iRzlqWVd4b2IzTjBPamd3T0RCMEFDc3ZhM0l0WkdWMkwzSmxiVzkwYVc1bkwwOVRRMkZqYUdWT2IzUnBabWxqWVhScGIyNVRaWEoyYVdObGRBQUpiRzlqWVd4b2IzTjBkQUFFYUhSMGNIQjRkQUFBZEFBR1ZGSkJWa1ZNZEFBQkwzTnlBQk5xWVhaaExuVjBhV3d1UVhKeVlYbE1hWE4wZUlIU0habkhZWjBEQUFGSkFBUnphWHBsZUhBQUFBQUZkd1FBQUFBS2RB';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 444    FOR UPDATE;        
    buffer := 'QXpiM0puTG10MVlXeHBMbkpwWTJVdWEzTmlMbTFsYzNOaFoybHVaeTV6WlhKMmFXTmxMa3RUUWtwaGRtRlRaWEoyYVdObGRBQThZMjl0TG05d1pXNXplVzF3YUc5dWVTNXZjMk5oWTJobExtSmhjMlV1WlhabGJuUnpMa05oWTJobFJXNTBjbmxGZG1WdWRFeHBjM1JsYm1WeWRBQTNZMjl0TG05d1pXNXplVzF3YUc5dWVTNXZjMk5oWTJobExtSmhjMlV1WlhabGJuUnpMa05oWTJobFJYWmxiblJNYVhOMFpXNWxjblFBRjJwaGRtRXVkWFJwYkM1RmRtVnVkRXhwYzNSbGJtVnlkQUFzWTI5dExtOXdaVzV6ZVcxd2FHOXVlUzV2YzJOaFkyaGxMbUpoYzJVdVRHbG1aV041WTJ4bFFYZGhjbVY0dAANMTI5LjE4Ni43OS40NXNyADJvcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLkphdmFTZXJ2aWNlRGVmaW5pdGlvbrbnDV9BNGzxAgABTAARc2VydmljZUludGVyZmFjZXN0ABBMamF2YS91dGlsL0xpc3Q7eHIALm9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuU2VydmljZURlZmluaXRpb24AmwJPWN0pfgIADUwAC2J1c1NlY3VyaXR5cQB+ABNMAA9jcmVkZW50aWFsc1R5cGV0AExMb3JnL2t1YWxpL3JpY2UvY29yZS9zZWN1cml0eS9jcmVkZW50aWFscy9DcmVkZW50aWFsc1NvdXJjZSRDcmVkZW50aWFsc1R5cGU7TAAQbG9jYWxTZXJ2aWNlTmFtZXEAfgAETAAXbWVzc2FnZUV4Y2VwdGlvbkhhbmRsZXJxAH4ABEwADG1pbGxpc1RvTGl2ZXEAfgAVTAAIcHJpb3JpdHlxAH4AFEwABXF1ZXVlcQB+ABNMAA1yZXRyeUF0dGVtcHRzcQB+ABRMAAdzZXJ2aWNldAASTGphdmEvbGFuZy9PYmplY3Q7TAAPc2VydmljZUVuZFBvaW50dAAOTGphdmEvbmV0L1VSTDtMABNzZXJ2aWNlTmFtZVNwYWNlVVJJcQB+AARMABBzZXJ2aWNlTmFtZXNwYWNlcQB+AARMAAtzZXJ2aWNlUGF0aHEAfgAEeHBzcQB+ABkBcHQAGk9TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNldABNb3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5leGNlcHRpb25oYW5kbGluZy5EZWZhdWx0TWVzc2FnZUV4Y2VwdGlvbkhhbmRsZXJzcQB+AB///////////3NxAH4AHAAAAANzcQB+ABkAcQB+ADJwc3IADGphdmEubmV0LlVSTJYlNzYa/ORyAwAHSQAIaGFzaENvZGVJAARwb3J0TAAJYXV0aG9yaXR5cQB+AARMAARmaWxlcQB+AARMAARob3N0cQB+AARMAAhwcm90b2NvbHEAfgAETAADcmVmcQB+AAR4cGkboqAAAB+QdAAObG9jYWxob3N0OjgwODB0ACsva3ItZGV2L3JlbW90aW5nL09TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNldAAJbG9jYWxob3N0dAAEaHR0cHB4dAAAdAAGVFJBVkVMdAABL3NyABNqYXZhLnV0aWwuQXJyYXlMaXN0eIHSHZnHYZ0DAAFJAARzaXpleHAAAAAFdwQAAAAKdAAzb3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5zZXJ2aWNlLktTQkphdmFTZXJ2aWNldAA8Y29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLmJhc2UuZXZlbnRzLkNhY2hlRW50cnlFdmVudExpc3RlbmVydAA3Y29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLmJhc2UuZXZlbnRzLkNhY2hlRXZlbnRMaXN0ZW5lcnQAF2phdmEudXRpbC5FdmVudExpc3RlbmVydAAsY29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLmJhc2UuTGlmZWN5Y2xlQXdhcmV4cQB+ACN0AAZUUkFWRUw=';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
INSERT INTO KRSB_MSG_PYLD_T (MSG_PYLD,MSG_QUE_ID)
  VALUES (EMPTY_CLOB(),445)
/
-- Length: 6124
-- Chunks: 2
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 445    FOR UPDATE;        
    buffer := 'rO0ABXNyAC1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLkFzeW5jaHJvbm91c0NhbGzxnQ5Y0t0lZQIAB1oAFWlnbm9yZVN0b3JlQW5kRm9yd2FyZFsACWFyZ3VtZW50c3QAE1tMamF2YS9sYW5nL09iamVjdDtMAAhjYWxsYmFja3QAM0xvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL0FzeW5jaHJvbm91c0NhbGxiYWNrO0wAB2NvbnRleHR0ABZMamF2YS9pby9TZXJpYWxpemFibGU7TAAKbWV0aG9kTmFtZXQAEkxqYXZhL2xhbmcvU3RyaW5nO1sACnBhcmFtVHlwZXN0ABJbTGphdmEvbGFuZy9DbGFzcztMAAtzZXJ2aWNlSW5mb3QAKkxvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL1NlcnZpY2VJbmZvO3hwAHVyABNbTGphdmEubGFuZy5PYmplY3Q7kM5YnxBzKWwCAAB4cAAAAAFzcgBDY29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLnBsdWdpbnMuY2x1c3RlcnN1cHBvcnQuQ2x1c3Rlck5vdGlmaWNhdGlvbhY+6VckvG9pAgACSQAEdHlwZUwABGRhdGFxAH4AA3hwAAAAAnQAPkRvY3VtZW50VHlwZVJ1bGVDYWNoZTpSb3V0aW5nUnVsZURlbGVnYXRpb25NYWludGVuYW5jZURvY3VtZW50cHB0AAZpbnZva2V1cgASW0xqYXZhLmxhbmcuQ2xhc3M7qxbXrsvNWpkCAAB4cAAAAAF2cgAUamF2YS5pby5TZXJpYWxpemFibGUQm2EN15tk7QIAAHhwc3IAKG9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuU2VydmljZUluZm/FFyO2KQS3ugIAC0wABWFsaXZldAATTGphdmEvbGFuZy9Cb29sZWFuO0wAFGVuZHBvaW50QWx0ZXJuYXRlVXJscQB+AARMAAtlbmRwb2ludFVybHEAfgAETAAKbG9ja1Zlck5icnQAE0xqYXZhL2xhbmcvSW50ZWdlcjtMAA5tZXNzYWdlRW50cnlJZHQAEExqYXZhL2xhbmcvTG9uZztMAAVxbmFtZXQAG0xqYXZheC94bWwvbmFtZXNwYWNlL1FOYW1lO0wAGnNlcmlhbGl6ZWRTZXJ2aWNlTmFtZXNwYWNlcQB+AARMAAhzZXJ2ZXJJcHEAfgAETAARc2VydmljZURlZmluaXRpb250ADBMb3JnL2t1YWxpL3JpY2Uva3NiL21lc3NhZ2luZy9TZXJ2aWNlRGVmaW5pdGlvbjtMAAtzZXJ2aWNlTmFtZXEAfgAETAAQc2VydmljZU5hbWVzcGFjZXEAfgAEeHBzcgARamF2YS5sYW5nLkJvb2xlYW7NIHKA1Zz67gIAAVoABXZhbHVleHABcHQAQGh0dHA6Ly9sb2NhbGhvc3Q6ODA4MC9rci1kZXYvcmVtb3RpbmcvT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2VzcgARamF2YS5sYW5nLkludGVnZXIS4qCk94GHOAIAAUkABXZhbHVleHIAEGphdmEubGFuZy5OdW1iZXKGrJUdC5TgiwIAAHhwAAAAAXNyAA5qYXZhLmxhbmcuTG9uZzuL5JDMjyPfAgABSgAFdmFsdWV4cQB+AB0AAAAAAAAPfXNyABlqYXZheC54bWwubmFtZXNwYWNlLlFOYW1lgW2oLfw73WwCAANMAAlsb2NhbFBhcnRxAH4ABEwADG5hbWVzcGFjZVVSSXEAfgAETAAGcHJlZml4cQB+AAR4cHQAGk9TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNldAAAcQB+ACR0B3RyTzBBQlhOeUFESnZjbWN1YTNWaGJHa3VjbWxqWlM1cmMySXViV1Z6YzJGbmFXNW5Ma3BoZG1GVFpYSjJhV05sUkdWbWFXNXBkR2x2YnJibkRWOUJOR3p4QWdBQlRBQVJjMlZ5ZG1salpVbHVkR1Z5Wm1GalpYTjBBQkJNYW1GMllTOTFkR2xzTDB4cGMzUTdlSElBTG05eVp5NXJkV0ZzYVM1eWFXTmxMbXR6WWk1dFpYTnpZV2RwYm1jdVUyVnlkbWxqWlVSbFptbHVhWFJwYjI0QW13SlBXTjBwZmdJQURVd0FDMkoxYzFObFkzVnlhWFI1ZEFBVFRHcGhkbUV2YkdGdVp5OUNiMjlzWldGdU8wd0FEMk55WldSbGJuUnBZV3h6Vkhsd1pYUUFURXh2Y21jdmEzVmhiR2t2Y21salpTOWpiM0psTDNObFkzVnlhWFI1TDJOeVpXUmxiblJwWVd4ekwwTnlaV1JsYm5ScFlXeHpVMjkxY21ObEpFTnlaV1JsYm5ScFlXeHpWSGx3WlR0TUFCQnNiMk5oYkZObGNuWnBZMlZPWVcxbGRBQVNUR3BoZG1FdmJHRnVaeTlUZEhKcGJtYzdUQUFYYldWemMyRm5aVVY0WTJWd2RHbHZia2hoYm1Sc1pYSnhBSDRBQlV3QURHMXBiR3hwYzFSdlRHbDJaWFFBRUV4cVlYWmhMMnhoYm1jdlRHOXVaenRNQUFod2NtbHZjbWwwZVhRQUUweHFZWFpoTDJ4aGJtY3ZTVzUwWldkbGNqdE1BQVZ4ZFdWMVpYRUFmZ0FEVEFBTmNtVjBjbmxCZEhSbGJYQjBjM0VBZmdBSFRBQUhjMlZ5ZG1salpYUUFFa3hxWVhaaEwyeGhibWN2VDJKcVpXTjBPMHdBRDNObGNuWnBZMlZGYm1SUWIybHVkSFFBRGt4cVlYWmhMMjVsZEM5VlVrdzdUQUFUYzJWeWRtbGpaVTVoYldWVGNHRmpaVlZTU1hFQWZnQUZUQUFRYzJWeWRtbGpaVTVoYldWemNHRmpaWEVBZmdBRlRBQUxjMlZ5ZG1salpWQmhkR2h4QUg0QUJYaHdjM0lBRVdwaGRtRXViR0Z1Wnk1Q2IyOXNaV0Z1elNCeWdOV2MrdTRDQUFGYUFBVjJZV3gxWlhod0FYQjBBQnBQVTBOaFkyaGxUbTkwYVdacFkyRjBhVzl1VTJWeWRtbGpaWFFBVFc5eVp5NXJkV0ZzYVM1eWFXTmxMbXR6WWk1dFpYTnpZV2RwYm1jdVpYaGpaWEIwYVc5dWFHRnVaR3hwYm1jdVJHVm1ZWFZzZEUxbGMzTmhaMlZGZUdObGNIUnBiMjVJWVc1a2JHVnljM0lBRG1waGRtRXViR0Z1Wnk1TWIyNW5PNHZra015UEk5OENBQUZLQUFWMllXeDFaWGh5QUJCcVlYWmhMbXhoYm1jdVRuVnRZbVZ5aHF5VkhRdVU0SXNDQUFCNGNQLy8vLy8vLy8vL2MzSUFFV3BoZG1FdWJHRnVaeTVKYm5SbFoyVnlFdUtncFBlQmh6Z0NBQUZKQUFWMllXeDFaWGh4QUg0QUVBQUFBQU56Y1FCK0FBc0FjUUIrQUJOd2MzSUFER3BoZG1FdWJtVjBMbFZTVEpZbE56WWEvT1J5QXdBSFNRQUlhR0Z6YUVOdlpHVkpBQVJ3YjNKMFRBQUpZWFYwYUc5eWFYUjVjUUIrQUFWTUFBUm1hV3hsY1FCK0FBVk1BQVJvYjNOMGNRQitBQVZNQUFod2NtOTBiMk52YkhFQWZnQUZUQUFEY21WbWNRQitBQVY0Y1AvLy8vOEFBQitRZEFBT2JHOWpZV3hvYjNOME9qZ3dPREIwQUNzdmEzSXRaR1YyTDNKbGJXOTBhVzVuTDA5VFEyRmphR1ZPYjNScFptbGpZWFJwYjI1VFpYSjJhV05sZEFBSmJHOWpZV3hvYjNOMGRBQUVhSFIwY0hCNGRBQUFkQUFHVkZKQlZrVk1kQUFCTDNOeUFCTnFZWFpoTG5WMGFXd3VRWEp5WVhsTWFYTjBlSUhTSFpuSFlaMERBQUZKQUFSemFYcGxlSEFBQUFBRmR3';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 445    FOR UPDATE;        
    buffer := 'UUFBQUFLZEFBemIzSm5MbXQxWVd4cExuSnBZMlV1YTNOaUxtMWxjM05oWjJsdVp5NXpaWEoyYVdObExrdFRRa3BoZG1GVFpYSjJhV05sZEFBOFkyOXRMbTl3Wlc1emVXMXdhRzl1ZVM1dmMyTmhZMmhsTG1KaGMyVXVaWFpsYm5SekxrTmhZMmhsUlc1MGNubEZkbVZ1ZEV4cGMzUmxibVZ5ZEFBM1kyOXRMbTl3Wlc1emVXMXdhRzl1ZVM1dmMyTmhZMmhsTG1KaGMyVXVaWFpsYm5SekxrTmhZMmhsUlhabGJuUk1hWE4wWlc1bGNuUUFGMnBoZG1FdWRYUnBiQzVGZG1WdWRFeHBjM1JsYm1WeWRBQXNZMjl0TG05d1pXNXplVzF3YUc5dWVTNXZjMk5oWTJobExtSmhjMlV1VEdsbVpXTjVZMnhsUVhkaGNtVjR0AA0xMjkuMTg2Ljc5LjQ1c3IAMm9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuSmF2YVNlcnZpY2VEZWZpbml0aW9utucNX0E0bPECAAFMABFzZXJ2aWNlSW50ZXJmYWNlc3QAEExqYXZhL3V0aWwvTGlzdDt4cgAub3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5TZXJ2aWNlRGVmaW5pdGlvbgCbAk9Y3Sl+AgANTAALYnVzU2VjdXJpdHlxAH4AE0wAD2NyZWRlbnRpYWxzVHlwZXQATExvcmcva3VhbGkvcmljZS9jb3JlL3NlY3VyaXR5L2NyZWRlbnRpYWxzL0NyZWRlbnRpYWxzU291cmNlJENyZWRlbnRpYWxzVHlwZTtMABBsb2NhbFNlcnZpY2VOYW1lcQB+AARMABdtZXNzYWdlRXhjZXB0aW9uSGFuZGxlcnEAfgAETAAMbWlsbGlzVG9MaXZlcQB+ABVMAAhwcmlvcml0eXEAfgAUTAAFcXVldWVxAH4AE0wADXJldHJ5QXR0ZW1wdHNxAH4AFEwAB3NlcnZpY2V0ABJMamF2YS9sYW5nL09iamVjdDtMAA9zZXJ2aWNlRW5kUG9pbnR0AA5MamF2YS9uZXQvVVJMO0wAE3NlcnZpY2VOYW1lU3BhY2VVUklxAH4ABEwAEHNlcnZpY2VOYW1lc3BhY2VxAH4ABEwAC3NlcnZpY2VQYXRocQB+AAR4cHNxAH4AGQFwdAAaT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2V0AE1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLmV4Y2VwdGlvbmhhbmRsaW5nLkRlZmF1bHRNZXNzYWdlRXhjZXB0aW9uSGFuZGxlcnNxAH4AH///////////c3EAfgAcAAAAA3NxAH4AGQBxAH4AMnBzcgAMamF2YS5uZXQuVVJMliU3Nhr85HIDAAdJAAhoYXNoQ29kZUkABHBvcnRMAAlhdXRob3JpdHlxAH4ABEwABGZpbGVxAH4ABEwABGhvc3RxAH4ABEwACHByb3RvY29scQB+AARMAANyZWZxAH4ABHhwaRuioAAAH5B0AA5sb2NhbGhvc3Q6ODA4MHQAKy9rci1kZXYvcmVtb3RpbmcvT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2V0AAlsb2NhbGhvc3R0AARodHRwcHh0AAB0AAZUUkFWRUx0AAEvc3IAE2phdmEudXRpbC5BcnJheUxpc3R4gdIdmcdhnQMAAUkABHNpemV4cAAAAAV3BAAAAAp0ADNvcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLnNlcnZpY2UuS1NCSmF2YVNlcnZpY2V0ADxjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5ldmVudHMuQ2FjaGVFbnRyeUV2ZW50TGlzdGVuZXJ0ADdjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5ldmVudHMuQ2FjaGVFdmVudExpc3RlbmVydAAXamF2YS51dGlsLkV2ZW50TGlzdGVuZXJ0ACxjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5MaWZlY3ljbGVBd2FyZXhxAH4AI3QABlRSQVZFTA==';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
INSERT INTO KRSB_MSG_PYLD_T (MSG_PYLD,MSG_QUE_ID)
  VALUES (EMPTY_CLOB(),446)
/
-- Length: 6112
-- Chunks: 2
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 446    FOR UPDATE;        
    buffer := 'rO0ABXNyAC1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLkFzeW5jaHJvbm91c0NhbGzxnQ5Y0t0lZQIAB1oAFWlnbm9yZVN0b3JlQW5kRm9yd2FyZFsACWFyZ3VtZW50c3QAE1tMamF2YS9sYW5nL09iamVjdDtMAAhjYWxsYmFja3QAM0xvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL0FzeW5jaHJvbm91c0NhbGxiYWNrO0wAB2NvbnRleHR0ABZMamF2YS9pby9TZXJpYWxpemFibGU7TAAKbWV0aG9kTmFtZXQAEkxqYXZhL2xhbmcvU3RyaW5nO1sACnBhcmFtVHlwZXN0ABJbTGphdmEvbGFuZy9DbGFzcztMAAtzZXJ2aWNlSW5mb3QAKkxvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL1NlcnZpY2VJbmZvO3hwAHVyABNbTGphdmEubGFuZy5PYmplY3Q7kM5YnxBzKWwCAAB4cAAAAAFzcgBDY29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLnBsdWdpbnMuY2x1c3RlcnN1cHBvcnQuQ2x1c3Rlck5vdGlmaWNhdGlvbhY+6VckvG9pAgACSQAEdHlwZUwABGRhdGFxAH4AA3hwAAAAAnQANURvY3VtZW50VHlwZVJ1bGVDYWNoZTpJZGVudGl0eU1hbmFnZW1lbnRHcm91cERvY3VtZW50cHB0AAZpbnZva2V1cgASW0xqYXZhLmxhbmcuQ2xhc3M7qxbXrsvNWpkCAAB4cAAAAAF2cgAUamF2YS5pby5TZXJpYWxpemFibGUQm2EN15tk7QIAAHhwc3IAKG9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuU2VydmljZUluZm/FFyO2KQS3ugIAC0wABWFsaXZldAATTGphdmEvbGFuZy9Cb29sZWFuO0wAFGVuZHBvaW50QWx0ZXJuYXRlVXJscQB+AARMAAtlbmRwb2ludFVybHEAfgAETAAKbG9ja1Zlck5icnQAE0xqYXZhL2xhbmcvSW50ZWdlcjtMAA5tZXNzYWdlRW50cnlJZHQAEExqYXZhL2xhbmcvTG9uZztMAAVxbmFtZXQAG0xqYXZheC94bWwvbmFtZXNwYWNlL1FOYW1lO0wAGnNlcmlhbGl6ZWRTZXJ2aWNlTmFtZXNwYWNlcQB+AARMAAhzZXJ2ZXJJcHEAfgAETAARc2VydmljZURlZmluaXRpb250ADBMb3JnL2t1YWxpL3JpY2Uva3NiL21lc3NhZ2luZy9TZXJ2aWNlRGVmaW5pdGlvbjtMAAtzZXJ2aWNlTmFtZXEAfgAETAAQc2VydmljZU5hbWVzcGFjZXEAfgAEeHBzcgARamF2YS5sYW5nLkJvb2xlYW7NIHKA1Zz67gIAAVoABXZhbHVleHABcHQAQGh0dHA6Ly9sb2NhbGhvc3Q6ODA4MC9rci1kZXYvcmVtb3RpbmcvT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2VzcgARamF2YS5sYW5nLkludGVnZXIS4qCk94GHOAIAAUkABXZhbHVleHIAEGphdmEubGFuZy5OdW1iZXKGrJUdC5TgiwIAAHhwAAAAAXNyAA5qYXZhLmxhbmcuTG9uZzuL5JDMjyPfAgABSgAFdmFsdWV4cQB+AB0AAAAAAAAPfXNyABlqYXZheC54bWwubmFtZXNwYWNlLlFOYW1lgW2oLfw73WwCAANMAAlsb2NhbFBhcnRxAH4ABEwADG5hbWVzcGFjZVVSSXEAfgAETAAGcHJlZml4cQB+AAR4cHQAGk9TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNldAAAcQB+ACR0B3RyTzBBQlhOeUFESnZjbWN1YTNWaGJHa3VjbWxqWlM1cmMySXViV1Z6YzJGbmFXNW5Ma3BoZG1GVFpYSjJhV05sUkdWbWFXNXBkR2x2YnJibkRWOUJOR3p4QWdBQlRBQVJjMlZ5ZG1salpVbHVkR1Z5Wm1GalpYTjBBQkJNYW1GMllTOTFkR2xzTDB4cGMzUTdlSElBTG05eVp5NXJkV0ZzYVM1eWFXTmxMbXR6WWk1dFpYTnpZV2RwYm1jdVUyVnlkbWxqWlVSbFptbHVhWFJwYjI0QW13SlBXTjBwZmdJQURVd0FDMkoxYzFObFkzVnlhWFI1ZEFBVFRHcGhkbUV2YkdGdVp5OUNiMjlzWldGdU8wd0FEMk55WldSbGJuUnBZV3h6Vkhsd1pYUUFURXh2Y21jdmEzVmhiR2t2Y21salpTOWpiM0psTDNObFkzVnlhWFI1TDJOeVpXUmxiblJwWVd4ekwwTnlaV1JsYm5ScFlXeHpVMjkxY21ObEpFTnlaV1JsYm5ScFlXeHpWSGx3WlR0TUFCQnNiMk5oYkZObGNuWnBZMlZPWVcxbGRBQVNUR3BoZG1FdmJHRnVaeTlUZEhKcGJtYzdUQUFYYldWemMyRm5aVVY0WTJWd2RHbHZia2hoYm1Sc1pYSnhBSDRBQlV3QURHMXBiR3hwYzFSdlRHbDJaWFFBRUV4cVlYWmhMMnhoYm1jdlRHOXVaenRNQUFod2NtbHZjbWwwZVhRQUUweHFZWFpoTDJ4aGJtY3ZTVzUwWldkbGNqdE1BQVZ4ZFdWMVpYRUFmZ0FEVEFBTmNtVjBjbmxCZEhSbGJYQjBjM0VBZmdBSFRBQUhjMlZ5ZG1salpYUUFFa3hxWVhaaEwyeGhibWN2VDJKcVpXTjBPMHdBRDNObGNuWnBZMlZGYm1SUWIybHVkSFFBRGt4cVlYWmhMMjVsZEM5VlVrdzdUQUFUYzJWeWRtbGpaVTVoYldWVGNHRmpaVlZTU1hFQWZnQUZUQUFRYzJWeWRtbGpaVTVoYldWemNHRmpaWEVBZmdBRlRBQUxjMlZ5ZG1salpWQmhkR2h4QUg0QUJYaHdjM0lBRVdwaGRtRXViR0Z1Wnk1Q2IyOXNaV0Z1elNCeWdOV2MrdTRDQUFGYUFBVjJZV3gxWlhod0FYQjBBQnBQVTBOaFkyaGxUbTkwYVdacFkyRjBhVzl1VTJWeWRtbGpaWFFBVFc5eVp5NXJkV0ZzYVM1eWFXTmxMbXR6WWk1dFpYTnpZV2RwYm1jdVpYaGpaWEIwYVc5dWFHRnVaR3hwYm1jdVJHVm1ZWFZzZEUxbGMzTmhaMlZGZUdObGNIUnBiMjVJWVc1a2JHVnljM0lBRG1waGRtRXViR0Z1Wnk1TWIyNW5PNHZra015UEk5OENBQUZLQUFWMllXeDFaWGh5QUJCcVlYWmhMbXhoYm1jdVRuVnRZbVZ5aHF5VkhRdVU0SXNDQUFCNGNQLy8vLy8vLy8vL2MzSUFFV3BoZG1FdWJHRnVaeTVKYm5SbFoyVnlFdUtncFBlQmh6Z0NBQUZKQUFWMllXeDFaWGh4QUg0QUVBQUFBQU56Y1FCK0FBc0FjUUIrQUJOd2MzSUFER3BoZG1FdWJtVjBMbFZTVEpZbE56WWEvT1J5QXdBSFNRQUlhR0Z6YUVOdlpHVkpBQVJ3YjNKMFRBQUpZWFYwYUc5eWFYUjVjUUIrQUFWTUFBUm1hV3hsY1FCK0FBVk1BQVJvYjNOMGNRQitBQVZNQUFod2NtOTBiMk52YkhFQWZnQUZUQUFEY21WbWNRQitBQVY0Y1AvLy8vOEFBQitRZEFBT2JHOWpZV3hvYjNOME9qZ3dPREIwQUNzdmEzSXRaR1YyTDNKbGJXOTBhVzVuTDA5VFEyRmphR1ZPYjNScFptbGpZWFJwYjI1VFpYSjJhV05sZEFBSmJHOWpZV3hvYjNOMGRBQUVhSFIwY0hCNGRBQUFkQUFHVkZKQlZrVk1kQUFCTDNOeUFCTnFZWFpoTG5WMGFXd3VRWEp5WVhsTWFYTjBlSUhTSFpuSFlaMERBQUZKQUFSemFYcGxlSEFBQUFBRmR3UUFBQUFLZEFB';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 446    FOR UPDATE;        
    buffer := 'emIzSm5MbXQxWVd4cExuSnBZMlV1YTNOaUxtMWxjM05oWjJsdVp5NXpaWEoyYVdObExrdFRRa3BoZG1GVFpYSjJhV05sZEFBOFkyOXRMbTl3Wlc1emVXMXdhRzl1ZVM1dmMyTmhZMmhsTG1KaGMyVXVaWFpsYm5SekxrTmhZMmhsUlc1MGNubEZkbVZ1ZEV4cGMzUmxibVZ5ZEFBM1kyOXRMbTl3Wlc1emVXMXdhRzl1ZVM1dmMyTmhZMmhsTG1KaGMyVXVaWFpsYm5SekxrTmhZMmhsUlhabGJuUk1hWE4wWlc1bGNuUUFGMnBoZG1FdWRYUnBiQzVGZG1WdWRFeHBjM1JsYm1WeWRBQXNZMjl0TG05d1pXNXplVzF3YUc5dWVTNXZjMk5oWTJobExtSmhjMlV1VEdsbVpXTjVZMnhsUVhkaGNtVjR0AA0xMjkuMTg2Ljc5LjQ1c3IAMm9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuSmF2YVNlcnZpY2VEZWZpbml0aW9utucNX0E0bPECAAFMABFzZXJ2aWNlSW50ZXJmYWNlc3QAEExqYXZhL3V0aWwvTGlzdDt4cgAub3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5TZXJ2aWNlRGVmaW5pdGlvbgCbAk9Y3Sl+AgANTAALYnVzU2VjdXJpdHlxAH4AE0wAD2NyZWRlbnRpYWxzVHlwZXQATExvcmcva3VhbGkvcmljZS9jb3JlL3NlY3VyaXR5L2NyZWRlbnRpYWxzL0NyZWRlbnRpYWxzU291cmNlJENyZWRlbnRpYWxzVHlwZTtMABBsb2NhbFNlcnZpY2VOYW1lcQB+AARMABdtZXNzYWdlRXhjZXB0aW9uSGFuZGxlcnEAfgAETAAMbWlsbGlzVG9MaXZlcQB+ABVMAAhwcmlvcml0eXEAfgAUTAAFcXVldWVxAH4AE0wADXJldHJ5QXR0ZW1wdHNxAH4AFEwAB3NlcnZpY2V0ABJMamF2YS9sYW5nL09iamVjdDtMAA9zZXJ2aWNlRW5kUG9pbnR0AA5MamF2YS9uZXQvVVJMO0wAE3NlcnZpY2VOYW1lU3BhY2VVUklxAH4ABEwAEHNlcnZpY2VOYW1lc3BhY2VxAH4ABEwAC3NlcnZpY2VQYXRocQB+AAR4cHNxAH4AGQFwdAAaT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2V0AE1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLmV4Y2VwdGlvbmhhbmRsaW5nLkRlZmF1bHRNZXNzYWdlRXhjZXB0aW9uSGFuZGxlcnNxAH4AH///////////c3EAfgAcAAAAA3NxAH4AGQBxAH4AMnBzcgAMamF2YS5uZXQuVVJMliU3Nhr85HIDAAdJAAhoYXNoQ29kZUkABHBvcnRMAAlhdXRob3JpdHlxAH4ABEwABGZpbGVxAH4ABEwABGhvc3RxAH4ABEwACHByb3RvY29scQB+AARMAANyZWZxAH4ABHhwaRuioAAAH5B0AA5sb2NhbGhvc3Q6ODA4MHQAKy9rci1kZXYvcmVtb3RpbmcvT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2V0AAlsb2NhbGhvc3R0AARodHRwcHh0AAB0AAZUUkFWRUx0AAEvc3IAE2phdmEudXRpbC5BcnJheUxpc3R4gdIdmcdhnQMAAUkABHNpemV4cAAAAAV3BAAAAAp0ADNvcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLnNlcnZpY2UuS1NCSmF2YVNlcnZpY2V0ADxjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5ldmVudHMuQ2FjaGVFbnRyeUV2ZW50TGlzdGVuZXJ0ADdjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5ldmVudHMuQ2FjaGVFdmVudExpc3RlbmVydAAXamF2YS51dGlsLkV2ZW50TGlzdGVuZXJ0ACxjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5MaWZlY3ljbGVBd2FyZXhxAH4AI3QABlRSQVZFTA==';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
INSERT INTO KRSB_MSG_PYLD_T (MSG_PYLD,MSG_QUE_ID)
  VALUES (EMPTY_CLOB(),447)
/
-- Length: 6108
-- Chunks: 2
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 447    FOR UPDATE;        
    buffer := 'rO0ABXNyAC1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLkFzeW5jaHJvbm91c0NhbGzxnQ5Y0t0lZQIAB1oAFWlnbm9yZVN0b3JlQW5kRm9yd2FyZFsACWFyZ3VtZW50c3QAE1tMamF2YS9sYW5nL09iamVjdDtMAAhjYWxsYmFja3QAM0xvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL0FzeW5jaHJvbm91c0NhbGxiYWNrO0wAB2NvbnRleHR0ABZMamF2YS9pby9TZXJpYWxpemFibGU7TAAKbWV0aG9kTmFtZXQAEkxqYXZhL2xhbmcvU3RyaW5nO1sACnBhcmFtVHlwZXN0ABJbTGphdmEvbGFuZy9DbGFzcztMAAtzZXJ2aWNlSW5mb3QAKkxvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL1NlcnZpY2VJbmZvO3hwAHVyABNbTGphdmEubGFuZy5PYmplY3Q7kM5YnxBzKWwCAAB4cAAAAAFzcgBDY29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLnBsdWdpbnMuY2x1c3RlcnN1cHBvcnQuQ2x1c3Rlck5vdGlmaWNhdGlvbhY+6VckvG9pAgACSQAEdHlwZUwABGRhdGFxAH4AA3hwAAAAAnQANERvY3VtZW50VHlwZVJ1bGVDYWNoZTpJZGVudGl0eU1hbmFnZW1lbnRSb2xlRG9jdW1lbnRwcHQABmludm9rZXVyABJbTGphdmEubGFuZy5DbGFzczurFteuy81amQIAAHhwAAAAAXZyABRqYXZhLmlvLlNlcmlhbGl6YWJsZRCbYQ3Xm2TtAgAAeHBzcgAob3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5TZXJ2aWNlSW5mb8UXI7YpBLe6AgALTAAFYWxpdmV0ABNMamF2YS9sYW5nL0Jvb2xlYW47TAAUZW5kcG9pbnRBbHRlcm5hdGVVcmxxAH4ABEwAC2VuZHBvaW50VXJscQB+AARMAApsb2NrVmVyTmJydAATTGphdmEvbGFuZy9JbnRlZ2VyO0wADm1lc3NhZ2VFbnRyeUlkdAAQTGphdmEvbGFuZy9Mb25nO0wABXFuYW1ldAAbTGphdmF4L3htbC9uYW1lc3BhY2UvUU5hbWU7TAAac2VyaWFsaXplZFNlcnZpY2VOYW1lc3BhY2VxAH4ABEwACHNlcnZlcklwcQB+AARMABFzZXJ2aWNlRGVmaW5pdGlvbnQAMExvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL1NlcnZpY2VEZWZpbml0aW9uO0wAC3NlcnZpY2VOYW1lcQB+AARMABBzZXJ2aWNlTmFtZXNwYWNlcQB+AAR4cHNyABFqYXZhLmxhbmcuQm9vbGVhbs0gcoDVnPruAgABWgAFdmFsdWV4cAFwdABAaHR0cDovL2xvY2FsaG9zdDo4MDgwL2tyLWRldi9yZW1vdGluZy9PU0NhY2hlTm90aWZpY2F0aW9uU2VydmljZXNyABFqYXZhLmxhbmcuSW50ZWdlchLioKT3gYc4AgABSQAFdmFsdWV4cgAQamF2YS5sYW5nLk51bWJlcoaslR0LlOCLAgAAeHAAAAABc3IADmphdmEubGFuZy5Mb25nO4vkkMyPI98CAAFKAAV2YWx1ZXhxAH4AHQAAAAAAAA99c3IAGWphdmF4LnhtbC5uYW1lc3BhY2UuUU5hbWWBbagt/DvdbAIAA0wACWxvY2FsUGFydHEAfgAETAAMbmFtZXNwYWNlVVJJcQB+AARMAAZwcmVmaXhxAH4ABHhwdAAaT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2V0AABxAH4AJHQHdHJPMEFCWE55QURKdmNtY3VhM1ZoYkdrdWNtbGpaUzVyYzJJdWJXVnpjMkZuYVc1bkxrcGhkbUZUWlhKMmFXTmxSR1ZtYVc1cGRHbHZicmJuRFY5Qk5HenhBZ0FCVEFBUmMyVnlkbWxqWlVsdWRHVnlabUZqWlhOMEFCQk1hbUYyWVM5MWRHbHNMMHhwYzNRN2VISUFMbTl5Wnk1cmRXRnNhUzV5YVdObExtdHpZaTV0WlhOellXZHBibWN1VTJWeWRtbGpaVVJsWm1sdWFYUnBiMjRBbXdKUFdOMHBmZ0lBRFV3QUMySjFjMU5sWTNWeWFYUjVkQUFUVEdwaGRtRXZiR0Z1Wnk5Q2IyOXNaV0Z1TzB3QUQyTnlaV1JsYm5ScFlXeHpWSGx3WlhRQVRFeHZjbWN2YTNWaGJHa3ZjbWxqWlM5amIzSmxMM05sWTNWeWFYUjVMMk55WldSbGJuUnBZV3h6TDBOeVpXUmxiblJwWVd4elUyOTFjbU5sSkVOeVpXUmxiblJwWVd4elZIbHdaVHRNQUJCc2IyTmhiRk5sY25acFkyVk9ZVzFsZEFBU1RHcGhkbUV2YkdGdVp5OVRkSEpwYm1jN1RBQVhiV1Z6YzJGblpVVjRZMlZ3ZEdsdmJraGhibVJzWlhKeEFINEFCVXdBREcxcGJHeHBjMVJ2VEdsMlpYUUFFRXhxWVhaaEwyeGhibWN2VEc5dVp6dE1BQWh3Y21sdmNtbDBlWFFBRTB4cVlYWmhMMnhoYm1jdlNXNTBaV2RsY2p0TUFBVnhkV1YxWlhFQWZnQURUQUFOY21WMGNubEJkSFJsYlhCMGMzRUFmZ0FIVEFBSGMyVnlkbWxqWlhRQUVreHFZWFpoTDJ4aGJtY3ZUMkpxWldOME8wd0FEM05sY25acFkyVkZibVJRYjJsdWRIUUFEa3hxWVhaaEwyNWxkQzlWVWt3N1RBQVRjMlZ5ZG1salpVNWhiV1ZUY0dGalpWVlNTWEVBZmdBRlRBQVFjMlZ5ZG1salpVNWhiV1Z6Y0dGalpYRUFmZ0FGVEFBTGMyVnlkbWxqWlZCaGRHaHhBSDRBQlhod2MzSUFFV3BoZG1FdWJHRnVaeTVDYjI5c1pXRnV6U0J5Z05XYyt1NENBQUZhQUFWMllXeDFaWGh3QVhCMEFCcFBVME5oWTJobFRtOTBhV1pwWTJGMGFXOXVVMlZ5ZG1salpYUUFUVzl5Wnk1cmRXRnNhUzV5YVdObExtdHpZaTV0WlhOellXZHBibWN1WlhoalpYQjBhVzl1YUdGdVpHeHBibWN1UkdWbVlYVnNkRTFsYzNOaFoyVkZlR05sY0hScGIyNUlZVzVrYkdWeWMzSUFEbXBoZG1FdWJHRnVaeTVNYjI1bk80dmtrTXlQSTk4Q0FBRktBQVYyWVd4MVpYaHlBQkJxWVhaaExteGhibWN1VG5WdFltVnlocXlWSFF1VTRJc0NBQUI0Y1AvLy8vLy8vLy8vYzNJQUVXcGhkbUV1YkdGdVp5NUpiblJsWjJWeUV1S2dwUGVCaHpnQ0FBRkpBQVYyWVd4MVpYaHhBSDRBRUFBQUFBTnpjUUIrQUFzQWNRQitBQk53YzNJQURHcGhkbUV1Ym1WMExsVlNUSllsTnpZYS9PUnlBd0FIU1FBSWFHRnphRU52WkdWSkFBUndiM0owVEFBSllYVjBhRzl5YVhSNWNRQitBQVZNQUFSbWFXeGxjUUIrQUFWTUFBUm9iM04wY1FCK0FBVk1BQWh3Y205MGIyTnZiSEVBZmdBRlRBQURjbVZtY1FCK0FBVjRjUC8vLy84QUFCK1FkQUFPYkc5allXeG9iM04wT2pnd09EQjBBQ3N2YTNJdFpHVjJMM0psYlc5MGFXNW5MMDlUUTJGamFHVk9iM1JwWm1sallYUnBiMjVUWlhKMmFXTmxkQUFKYkc5allXeG9iM04wZEFBRWFIUjBjSEI0ZEFBQWRBQUdWRkpCVmtWTWRBQUJMM055QUJOcVlYWmhMblYwYVd3dVFYSnlZWGxNYVhOMGVJSFNIWm5IWVowREFBRkpBQVJ6YVhwbGVIQUFBQUFGZHdRQUFBQUtkQUF6';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 447    FOR UPDATE;        
    buffer := 'YjNKbkxtdDFZV3hwTG5KcFkyVXVhM05pTG0xbGMzTmhaMmx1Wnk1elpYSjJhV05sTGt0VFFrcGhkbUZUWlhKMmFXTmxkQUE4WTI5dExtOXdaVzV6ZVcxd2FHOXVlUzV2YzJOaFkyaGxMbUpoYzJVdVpYWmxiblJ6TGtOaFkyaGxSVzUwY25sRmRtVnVkRXhwYzNSbGJtVnlkQUEzWTI5dExtOXdaVzV6ZVcxd2FHOXVlUzV2YzJOaFkyaGxMbUpoYzJVdVpYWmxiblJ6TGtOaFkyaGxSWFpsYm5STWFYTjBaVzVsY25RQUYycGhkbUV1ZFhScGJDNUZkbVZ1ZEV4cGMzUmxibVZ5ZEFBc1kyOXRMbTl3Wlc1emVXMXdhRzl1ZVM1dmMyTmhZMmhsTG1KaGMyVXVUR2xtWldONVkyeGxRWGRoY21WNHQADTEyOS4xODYuNzkuNDVzcgAyb3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5KYXZhU2VydmljZURlZmluaXRpb2625w1fQTRs8QIAAUwAEXNlcnZpY2VJbnRlcmZhY2VzdAAQTGphdmEvdXRpbC9MaXN0O3hyAC5vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLlNlcnZpY2VEZWZpbml0aW9uAJsCT1jdKX4CAA1MAAtidXNTZWN1cml0eXEAfgATTAAPY3JlZGVudGlhbHNUeXBldABMTG9yZy9rdWFsaS9yaWNlL2NvcmUvc2VjdXJpdHkvY3JlZGVudGlhbHMvQ3JlZGVudGlhbHNTb3VyY2UkQ3JlZGVudGlhbHNUeXBlO0wAEGxvY2FsU2VydmljZU5hbWVxAH4ABEwAF21lc3NhZ2VFeGNlcHRpb25IYW5kbGVycQB+AARMAAxtaWxsaXNUb0xpdmVxAH4AFUwACHByaW9yaXR5cQB+ABRMAAVxdWV1ZXEAfgATTAANcmV0cnlBdHRlbXB0c3EAfgAUTAAHc2VydmljZXQAEkxqYXZhL2xhbmcvT2JqZWN0O0wAD3NlcnZpY2VFbmRQb2ludHQADkxqYXZhL25ldC9VUkw7TAATc2VydmljZU5hbWVTcGFjZVVSSXEAfgAETAAQc2VydmljZU5hbWVzcGFjZXEAfgAETAALc2VydmljZVBhdGhxAH4ABHhwc3EAfgAZAXB0ABpPU0NhY2hlTm90aWZpY2F0aW9uU2VydmljZXQATW9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuZXhjZXB0aW9uaGFuZGxpbmcuRGVmYXVsdE1lc3NhZ2VFeGNlcHRpb25IYW5kbGVyc3EAfgAf//////////9zcQB+ABwAAAADc3EAfgAZAHEAfgAycHNyAAxqYXZhLm5ldC5VUkyWJTc2GvzkcgMAB0kACGhhc2hDb2RlSQAEcG9ydEwACWF1dGhvcml0eXEAfgAETAAEZmlsZXEAfgAETAAEaG9zdHEAfgAETAAIcHJvdG9jb2xxAH4ABEwAA3JlZnEAfgAEeHBpG6KgAAAfkHQADmxvY2FsaG9zdDo4MDgwdAArL2tyLWRldi9yZW1vdGluZy9PU0NhY2hlTm90aWZpY2F0aW9uU2VydmljZXQACWxvY2FsaG9zdHQABGh0dHBweHQAAHQABlRSQVZFTHQAAS9zcgATamF2YS51dGlsLkFycmF5TGlzdHiB0h2Zx2GdAwABSQAEc2l6ZXhwAAAABXcEAAAACnQAM29yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuc2VydmljZS5LU0JKYXZhU2VydmljZXQAPGNvbS5vcGVuc3ltcGhvbnkub3NjYWNoZS5iYXNlLmV2ZW50cy5DYWNoZUVudHJ5RXZlbnRMaXN0ZW5lcnQAN2NvbS5vcGVuc3ltcGhvbnkub3NjYWNoZS5iYXNlLmV2ZW50cy5DYWNoZUV2ZW50TGlzdGVuZXJ0ABdqYXZhLnV0aWwuRXZlbnRMaXN0ZW5lcnQALGNvbS5vcGVuc3ltcGhvbnkub3NjYWNoZS5iYXNlLkxpZmVjeWNsZUF3YXJleHEAfgAjdAAGVFJBVkVM';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
INSERT INTO KRSB_MSG_PYLD_T (MSG_PYLD,MSG_QUE_ID)
  VALUES (EMPTY_CLOB(),448)
/
-- Length: 6112
-- Chunks: 2
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 448    FOR UPDATE;        
    buffer := 'rO0ABXNyAC1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLkFzeW5jaHJvbm91c0NhbGzxnQ5Y0t0lZQIAB1oAFWlnbm9yZVN0b3JlQW5kRm9yd2FyZFsACWFyZ3VtZW50c3QAE1tMamF2YS9sYW5nL09iamVjdDtMAAhjYWxsYmFja3QAM0xvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL0FzeW5jaHJvbm91c0NhbGxiYWNrO0wAB2NvbnRleHR0ABZMamF2YS9pby9TZXJpYWxpemFibGU7TAAKbWV0aG9kTmFtZXQAEkxqYXZhL2xhbmcvU3RyaW5nO1sACnBhcmFtVHlwZXN0ABJbTGphdmEvbGFuZy9DbGFzcztMAAtzZXJ2aWNlSW5mb3QAKkxvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL1NlcnZpY2VJbmZvO3hwAHVyABNbTGphdmEubGFuZy5PYmplY3Q7kM5YnxBzKWwCAAB4cAAAAAFzcgBDY29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLnBsdWdpbnMuY2x1c3RlcnN1cHBvcnQuQ2x1c3Rlck5vdGlmaWNhdGlvbhY+6VckvG9pAgACSQAEdHlwZUwABGRhdGFxAH4AA3hwAAAAAnQANURvY3VtZW50VHlwZVJ1bGVDYWNoZTpSZWNpcGVQYXJlbnRNYWludGVuYW5jZURvY3VtZW50cHB0AAZpbnZva2V1cgASW0xqYXZhLmxhbmcuQ2xhc3M7qxbXrsvNWpkCAAB4cAAAAAF2cgAUamF2YS5pby5TZXJpYWxpemFibGUQm2EN15tk7QIAAHhwc3IAKG9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuU2VydmljZUluZm/FFyO2KQS3ugIAC0wABWFsaXZldAATTGphdmEvbGFuZy9Cb29sZWFuO0wAFGVuZHBvaW50QWx0ZXJuYXRlVXJscQB+AARMAAtlbmRwb2ludFVybHEAfgAETAAKbG9ja1Zlck5icnQAE0xqYXZhL2xhbmcvSW50ZWdlcjtMAA5tZXNzYWdlRW50cnlJZHQAEExqYXZhL2xhbmcvTG9uZztMAAVxbmFtZXQAG0xqYXZheC94bWwvbmFtZXNwYWNlL1FOYW1lO0wAGnNlcmlhbGl6ZWRTZXJ2aWNlTmFtZXNwYWNlcQB+AARMAAhzZXJ2ZXJJcHEAfgAETAARc2VydmljZURlZmluaXRpb250ADBMb3JnL2t1YWxpL3JpY2Uva3NiL21lc3NhZ2luZy9TZXJ2aWNlRGVmaW5pdGlvbjtMAAtzZXJ2aWNlTmFtZXEAfgAETAAQc2VydmljZU5hbWVzcGFjZXEAfgAEeHBzcgARamF2YS5sYW5nLkJvb2xlYW7NIHKA1Zz67gIAAVoABXZhbHVleHABcHQAQGh0dHA6Ly9sb2NhbGhvc3Q6ODA4MC9rci1kZXYvcmVtb3RpbmcvT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2VzcgARamF2YS5sYW5nLkludGVnZXIS4qCk94GHOAIAAUkABXZhbHVleHIAEGphdmEubGFuZy5OdW1iZXKGrJUdC5TgiwIAAHhwAAAAAXNyAA5qYXZhLmxhbmcuTG9uZzuL5JDMjyPfAgABSgAFdmFsdWV4cQB+AB0AAAAAAAAPfXNyABlqYXZheC54bWwubmFtZXNwYWNlLlFOYW1lgW2oLfw73WwCAANMAAlsb2NhbFBhcnRxAH4ABEwADG5hbWVzcGFjZVVSSXEAfgAETAAGcHJlZml4cQB+AAR4cHQAGk9TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNldAAAcQB+ACR0B3RyTzBBQlhOeUFESnZjbWN1YTNWaGJHa3VjbWxqWlM1cmMySXViV1Z6YzJGbmFXNW5Ma3BoZG1GVFpYSjJhV05sUkdWbWFXNXBkR2x2YnJibkRWOUJOR3p4QWdBQlRBQVJjMlZ5ZG1salpVbHVkR1Z5Wm1GalpYTjBBQkJNYW1GMllTOTFkR2xzTDB4cGMzUTdlSElBTG05eVp5NXJkV0ZzYVM1eWFXTmxMbXR6WWk1dFpYTnpZV2RwYm1jdVUyVnlkbWxqWlVSbFptbHVhWFJwYjI0QW13SlBXTjBwZmdJQURVd0FDMkoxYzFObFkzVnlhWFI1ZEFBVFRHcGhkbUV2YkdGdVp5OUNiMjlzWldGdU8wd0FEMk55WldSbGJuUnBZV3h6Vkhsd1pYUUFURXh2Y21jdmEzVmhiR2t2Y21salpTOWpiM0psTDNObFkzVnlhWFI1TDJOeVpXUmxiblJwWVd4ekwwTnlaV1JsYm5ScFlXeHpVMjkxY21ObEpFTnlaV1JsYm5ScFlXeHpWSGx3WlR0TUFCQnNiMk5oYkZObGNuWnBZMlZPWVcxbGRBQVNUR3BoZG1FdmJHRnVaeTlUZEhKcGJtYzdUQUFYYldWemMyRm5aVVY0WTJWd2RHbHZia2hoYm1Sc1pYSnhBSDRBQlV3QURHMXBiR3hwYzFSdlRHbDJaWFFBRUV4cVlYWmhMMnhoYm1jdlRHOXVaenRNQUFod2NtbHZjbWwwZVhRQUUweHFZWFpoTDJ4aGJtY3ZTVzUwWldkbGNqdE1BQVZ4ZFdWMVpYRUFmZ0FEVEFBTmNtVjBjbmxCZEhSbGJYQjBjM0VBZmdBSFRBQUhjMlZ5ZG1salpYUUFFa3hxWVhaaEwyeGhibWN2VDJKcVpXTjBPMHdBRDNObGNuWnBZMlZGYm1SUWIybHVkSFFBRGt4cVlYWmhMMjVsZEM5VlVrdzdUQUFUYzJWeWRtbGpaVTVoYldWVGNHRmpaVlZTU1hFQWZnQUZUQUFRYzJWeWRtbGpaVTVoYldWemNHRmpaWEVBZmdBRlRBQUxjMlZ5ZG1salpWQmhkR2h4QUg0QUJYaHdjM0lBRVdwaGRtRXViR0Z1Wnk1Q2IyOXNaV0Z1elNCeWdOV2MrdTRDQUFGYUFBVjJZV3gxWlhod0FYQjBBQnBQVTBOaFkyaGxUbTkwYVdacFkyRjBhVzl1VTJWeWRtbGpaWFFBVFc5eVp5NXJkV0ZzYVM1eWFXTmxMbXR6WWk1dFpYTnpZV2RwYm1jdVpYaGpaWEIwYVc5dWFHRnVaR3hwYm1jdVJHVm1ZWFZzZEUxbGMzTmhaMlZGZUdObGNIUnBiMjVJWVc1a2JHVnljM0lBRG1waGRtRXViR0Z1Wnk1TWIyNW5PNHZra015UEk5OENBQUZLQUFWMllXeDFaWGh5QUJCcVlYWmhMbXhoYm1jdVRuVnRZbVZ5aHF5VkhRdVU0SXNDQUFCNGNQLy8vLy8vLy8vL2MzSUFFV3BoZG1FdWJHRnVaeTVKYm5SbFoyVnlFdUtncFBlQmh6Z0NBQUZKQUFWMllXeDFaWGh4QUg0QUVBQUFBQU56Y1FCK0FBc0FjUUIrQUJOd2MzSUFER3BoZG1FdWJtVjBMbFZTVEpZbE56WWEvT1J5QXdBSFNRQUlhR0Z6YUVOdlpHVkpBQVJ3YjNKMFRBQUpZWFYwYUc5eWFYUjVjUUIrQUFWTUFBUm1hV3hsY1FCK0FBVk1BQVJvYjNOMGNRQitBQVZNQUFod2NtOTBiMk52YkhFQWZnQUZUQUFEY21WbWNRQitBQVY0Y1AvLy8vOEFBQitRZEFBT2JHOWpZV3hvYjNOME9qZ3dPREIwQUNzdmEzSXRaR1YyTDNKbGJXOTBhVzVuTDA5VFEyRmphR1ZPYjNScFptbGpZWFJwYjI1VFpYSjJhV05sZEFBSmJHOWpZV3hvYjNOMGRBQUVhSFIwY0hCNGRBQUFkQUFHVkZKQlZrVk1kQUFCTDNOeUFCTnFZWFpoTG5WMGFXd3VRWEp5WVhsTWFYTjBlSUhTSFpuSFlaMERBQUZKQUFSemFYcGxlSEFBQUFBRmR3UUFBQUFLZEFB';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 448    FOR UPDATE;        
    buffer := 'emIzSm5MbXQxWVd4cExuSnBZMlV1YTNOaUxtMWxjM05oWjJsdVp5NXpaWEoyYVdObExrdFRRa3BoZG1GVFpYSjJhV05sZEFBOFkyOXRMbTl3Wlc1emVXMXdhRzl1ZVM1dmMyTmhZMmhsTG1KaGMyVXVaWFpsYm5SekxrTmhZMmhsUlc1MGNubEZkbVZ1ZEV4cGMzUmxibVZ5ZEFBM1kyOXRMbTl3Wlc1emVXMXdhRzl1ZVM1dmMyTmhZMmhsTG1KaGMyVXVaWFpsYm5SekxrTmhZMmhsUlhabGJuUk1hWE4wWlc1bGNuUUFGMnBoZG1FdWRYUnBiQzVGZG1WdWRFeHBjM1JsYm1WeWRBQXNZMjl0TG05d1pXNXplVzF3YUc5dWVTNXZjMk5oWTJobExtSmhjMlV1VEdsbVpXTjVZMnhsUVhkaGNtVjR0AA0xMjkuMTg2Ljc5LjQ1c3IAMm9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuSmF2YVNlcnZpY2VEZWZpbml0aW9utucNX0E0bPECAAFMABFzZXJ2aWNlSW50ZXJmYWNlc3QAEExqYXZhL3V0aWwvTGlzdDt4cgAub3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5TZXJ2aWNlRGVmaW5pdGlvbgCbAk9Y3Sl+AgANTAALYnVzU2VjdXJpdHlxAH4AE0wAD2NyZWRlbnRpYWxzVHlwZXQATExvcmcva3VhbGkvcmljZS9jb3JlL3NlY3VyaXR5L2NyZWRlbnRpYWxzL0NyZWRlbnRpYWxzU291cmNlJENyZWRlbnRpYWxzVHlwZTtMABBsb2NhbFNlcnZpY2VOYW1lcQB+AARMABdtZXNzYWdlRXhjZXB0aW9uSGFuZGxlcnEAfgAETAAMbWlsbGlzVG9MaXZlcQB+ABVMAAhwcmlvcml0eXEAfgAUTAAFcXVldWVxAH4AE0wADXJldHJ5QXR0ZW1wdHNxAH4AFEwAB3NlcnZpY2V0ABJMamF2YS9sYW5nL09iamVjdDtMAA9zZXJ2aWNlRW5kUG9pbnR0AA5MamF2YS9uZXQvVVJMO0wAE3NlcnZpY2VOYW1lU3BhY2VVUklxAH4ABEwAEHNlcnZpY2VOYW1lc3BhY2VxAH4ABEwAC3NlcnZpY2VQYXRocQB+AAR4cHNxAH4AGQFwdAAaT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2V0AE1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLmV4Y2VwdGlvbmhhbmRsaW5nLkRlZmF1bHRNZXNzYWdlRXhjZXB0aW9uSGFuZGxlcnNxAH4AH///////////c3EAfgAcAAAAA3NxAH4AGQBxAH4AMnBzcgAMamF2YS5uZXQuVVJMliU3Nhr85HIDAAdJAAhoYXNoQ29kZUkABHBvcnRMAAlhdXRob3JpdHlxAH4ABEwABGZpbGVxAH4ABEwABGhvc3RxAH4ABEwACHByb3RvY29scQB+AARMAANyZWZxAH4ABHhwaRuioAAAH5B0AA5sb2NhbGhvc3Q6ODA4MHQAKy9rci1kZXYvcmVtb3RpbmcvT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2V0AAlsb2NhbGhvc3R0AARodHRwcHh0AAB0AAZUUkFWRUx0AAEvc3IAE2phdmEudXRpbC5BcnJheUxpc3R4gdIdmcdhnQMAAUkABHNpemV4cAAAAAV3BAAAAAp0ADNvcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLnNlcnZpY2UuS1NCSmF2YVNlcnZpY2V0ADxjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5ldmVudHMuQ2FjaGVFbnRyeUV2ZW50TGlzdGVuZXJ0ADdjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5ldmVudHMuQ2FjaGVFdmVudExpc3RlbmVydAAXamF2YS51dGlsLkV2ZW50TGlzdGVuZXJ0ACxjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5MaWZlY3ljbGVBd2FyZXhxAH4AI3QABlRSQVZFTA==';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
INSERT INTO KRSB_MSG_PYLD_T (MSG_PYLD,MSG_QUE_ID)
  VALUES (EMPTY_CLOB(),449)
/
-- Length: 6112
-- Chunks: 2
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 449    FOR UPDATE;        
    buffer := 'rO0ABXNyAC1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLkFzeW5jaHJvbm91c0NhbGzxnQ5Y0t0lZQIAB1oAFWlnbm9yZVN0b3JlQW5kRm9yd2FyZFsACWFyZ3VtZW50c3QAE1tMamF2YS9sYW5nL09iamVjdDtMAAhjYWxsYmFja3QAM0xvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL0FzeW5jaHJvbm91c0NhbGxiYWNrO0wAB2NvbnRleHR0ABZMamF2YS9pby9TZXJpYWxpemFibGU7TAAKbWV0aG9kTmFtZXQAEkxqYXZhL2xhbmcvU3RyaW5nO1sACnBhcmFtVHlwZXN0ABJbTGphdmEvbGFuZy9DbGFzcztMAAtzZXJ2aWNlSW5mb3QAKkxvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL1NlcnZpY2VJbmZvO3hwAHVyABNbTGphdmEubGFuZy5PYmplY3Q7kM5YnxBzKWwCAAB4cAAAAAFzcgBDY29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLnBsdWdpbnMuY2x1c3RlcnN1cHBvcnQuQ2x1c3Rlck5vdGlmaWNhdGlvbhY+6VckvG9pAgACSQAEdHlwZUwABGRhdGFxAH4AA3hwAAAAAnQAN0RvY3VtZW50VHlwZVJ1bGVDYWNoZTpSZWNpcGVDYXRlZ29yeU1haW50ZW5hbmNlRG9jdW1lbnRwcHQABmludm9rZXVyABJbTGphdmEubGFuZy5DbGFzczurFteuy81amQIAAHhwAAAAAXZyABRqYXZhLmlvLlNlcmlhbGl6YWJsZRCbYQ3Xm2TtAgAAeHBzcgAob3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5TZXJ2aWNlSW5mb8UXI7YpBLe6AgALTAAFYWxpdmV0ABNMamF2YS9sYW5nL0Jvb2xlYW47TAAUZW5kcG9pbnRBbHRlcm5hdGVVcmxxAH4ABEwAC2VuZHBvaW50VXJscQB+AARMAApsb2NrVmVyTmJydAATTGphdmEvbGFuZy9JbnRlZ2VyO0wADm1lc3NhZ2VFbnRyeUlkdAAQTGphdmEvbGFuZy9Mb25nO0wABXFuYW1ldAAbTGphdmF4L3htbC9uYW1lc3BhY2UvUU5hbWU7TAAac2VyaWFsaXplZFNlcnZpY2VOYW1lc3BhY2VxAH4ABEwACHNlcnZlcklwcQB+AARMABFzZXJ2aWNlRGVmaW5pdGlvbnQAMExvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL1NlcnZpY2VEZWZpbml0aW9uO0wAC3NlcnZpY2VOYW1lcQB+AARMABBzZXJ2aWNlTmFtZXNwYWNlcQB+AAR4cHNyABFqYXZhLmxhbmcuQm9vbGVhbs0gcoDVnPruAgABWgAFdmFsdWV4cAFwdABAaHR0cDovL2xvY2FsaG9zdDo4MDgwL2tyLWRldi9yZW1vdGluZy9PU0NhY2hlTm90aWZpY2F0aW9uU2VydmljZXNyABFqYXZhLmxhbmcuSW50ZWdlchLioKT3gYc4AgABSQAFdmFsdWV4cgAQamF2YS5sYW5nLk51bWJlcoaslR0LlOCLAgAAeHAAAAABc3IADmphdmEubGFuZy5Mb25nO4vkkMyPI98CAAFKAAV2YWx1ZXhxAH4AHQAAAAAAAA99c3IAGWphdmF4LnhtbC5uYW1lc3BhY2UuUU5hbWWBbagt/DvdbAIAA0wACWxvY2FsUGFydHEAfgAETAAMbmFtZXNwYWNlVVJJcQB+AARMAAZwcmVmaXhxAH4ABHhwdAAaT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2V0AABxAH4AJHQHdHJPMEFCWE55QURKdmNtY3VhM1ZoYkdrdWNtbGpaUzVyYzJJdWJXVnpjMkZuYVc1bkxrcGhkbUZUWlhKMmFXTmxSR1ZtYVc1cGRHbHZicmJuRFY5Qk5HenhBZ0FCVEFBUmMyVnlkbWxqWlVsdWRHVnlabUZqWlhOMEFCQk1hbUYyWVM5MWRHbHNMMHhwYzNRN2VISUFMbTl5Wnk1cmRXRnNhUzV5YVdObExtdHpZaTV0WlhOellXZHBibWN1VTJWeWRtbGpaVVJsWm1sdWFYUnBiMjRBbXdKUFdOMHBmZ0lBRFV3QUMySjFjMU5sWTNWeWFYUjVkQUFUVEdwaGRtRXZiR0Z1Wnk5Q2IyOXNaV0Z1TzB3QUQyTnlaV1JsYm5ScFlXeHpWSGx3WlhRQVRFeHZjbWN2YTNWaGJHa3ZjbWxqWlM5amIzSmxMM05sWTNWeWFYUjVMMk55WldSbGJuUnBZV3h6TDBOeVpXUmxiblJwWVd4elUyOTFjbU5sSkVOeVpXUmxiblJwWVd4elZIbHdaVHRNQUJCc2IyTmhiRk5sY25acFkyVk9ZVzFsZEFBU1RHcGhkbUV2YkdGdVp5OVRkSEpwYm1jN1RBQVhiV1Z6YzJGblpVVjRZMlZ3ZEdsdmJraGhibVJzWlhKeEFINEFCVXdBREcxcGJHeHBjMVJ2VEdsMlpYUUFFRXhxWVhaaEwyeGhibWN2VEc5dVp6dE1BQWh3Y21sdmNtbDBlWFFBRTB4cVlYWmhMMnhoYm1jdlNXNTBaV2RsY2p0TUFBVnhkV1YxWlhFQWZnQURUQUFOY21WMGNubEJkSFJsYlhCMGMzRUFmZ0FIVEFBSGMyVnlkbWxqWlhRQUVreHFZWFpoTDJ4aGJtY3ZUMkpxWldOME8wd0FEM05sY25acFkyVkZibVJRYjJsdWRIUUFEa3hxWVhaaEwyNWxkQzlWVWt3N1RBQVRjMlZ5ZG1salpVNWhiV1ZUY0dGalpWVlNTWEVBZmdBRlRBQVFjMlZ5ZG1salpVNWhiV1Z6Y0dGalpYRUFmZ0FGVEFBTGMyVnlkbWxqWlZCaGRHaHhBSDRBQlhod2MzSUFFV3BoZG1FdWJHRnVaeTVDYjI5c1pXRnV6U0J5Z05XYyt1NENBQUZhQUFWMllXeDFaWGh3QVhCMEFCcFBVME5oWTJobFRtOTBhV1pwWTJGMGFXOXVVMlZ5ZG1salpYUUFUVzl5Wnk1cmRXRnNhUzV5YVdObExtdHpZaTV0WlhOellXZHBibWN1WlhoalpYQjBhVzl1YUdGdVpHeHBibWN1UkdWbVlYVnNkRTFsYzNOaFoyVkZlR05sY0hScGIyNUlZVzVrYkdWeWMzSUFEbXBoZG1FdWJHRnVaeTVNYjI1bk80dmtrTXlQSTk4Q0FBRktBQVYyWVd4MVpYaHlBQkJxWVhaaExteGhibWN1VG5WdFltVnlocXlWSFF1VTRJc0NBQUI0Y1AvLy8vLy8vLy8vYzNJQUVXcGhkbUV1YkdGdVp5NUpiblJsWjJWeUV1S2dwUGVCaHpnQ0FBRkpBQVYyWVd4MVpYaHhBSDRBRUFBQUFBTnpjUUIrQUFzQWNRQitBQk53YzNJQURHcGhkbUV1Ym1WMExsVlNUSllsTnpZYS9PUnlBd0FIU1FBSWFHRnphRU52WkdWSkFBUndiM0owVEFBSllYVjBhRzl5YVhSNWNRQitBQVZNQUFSbWFXeGxjUUIrQUFWTUFBUm9iM04wY1FCK0FBVk1BQWh3Y205MGIyTnZiSEVBZmdBRlRBQURjbVZtY1FCK0FBVjRjUC8vLy84QUFCK1FkQUFPYkc5allXeG9iM04wT2pnd09EQjBBQ3N2YTNJdFpHVjJMM0psYlc5MGFXNW5MMDlUUTJGamFHVk9iM1JwWm1sallYUnBiMjVUWlhKMmFXTmxkQUFKYkc5allXeG9iM04wZEFBRWFIUjBjSEI0ZEFBQWRBQUdWRkpCVmtWTWRBQUJMM055QUJOcVlYWmhMblYwYVd3dVFYSnlZWGxNYVhOMGVJSFNIWm5IWVowREFBRkpBQVJ6YVhwbGVIQUFBQUFGZHdRQUFBQUtk';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 449    FOR UPDATE;        
    buffer := 'QUF6YjNKbkxtdDFZV3hwTG5KcFkyVXVhM05pTG0xbGMzTmhaMmx1Wnk1elpYSjJhV05sTGt0VFFrcGhkbUZUWlhKMmFXTmxkQUE4WTI5dExtOXdaVzV6ZVcxd2FHOXVlUzV2YzJOaFkyaGxMbUpoYzJVdVpYWmxiblJ6TGtOaFkyaGxSVzUwY25sRmRtVnVkRXhwYzNSbGJtVnlkQUEzWTI5dExtOXdaVzV6ZVcxd2FHOXVlUzV2YzJOaFkyaGxMbUpoYzJVdVpYWmxiblJ6TGtOaFkyaGxSWFpsYm5STWFYTjBaVzVsY25RQUYycGhkbUV1ZFhScGJDNUZkbVZ1ZEV4cGMzUmxibVZ5ZEFBc1kyOXRMbTl3Wlc1emVXMXdhRzl1ZVM1dmMyTmhZMmhsTG1KaGMyVXVUR2xtWldONVkyeGxRWGRoY21WNHQADTEyOS4xODYuNzkuNDVzcgAyb3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5KYXZhU2VydmljZURlZmluaXRpb2625w1fQTRs8QIAAUwAEXNlcnZpY2VJbnRlcmZhY2VzdAAQTGphdmEvdXRpbC9MaXN0O3hyAC5vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLlNlcnZpY2VEZWZpbml0aW9uAJsCT1jdKX4CAA1MAAtidXNTZWN1cml0eXEAfgATTAAPY3JlZGVudGlhbHNUeXBldABMTG9yZy9rdWFsaS9yaWNlL2NvcmUvc2VjdXJpdHkvY3JlZGVudGlhbHMvQ3JlZGVudGlhbHNTb3VyY2UkQ3JlZGVudGlhbHNUeXBlO0wAEGxvY2FsU2VydmljZU5hbWVxAH4ABEwAF21lc3NhZ2VFeGNlcHRpb25IYW5kbGVycQB+AARMAAxtaWxsaXNUb0xpdmVxAH4AFUwACHByaW9yaXR5cQB+ABRMAAVxdWV1ZXEAfgATTAANcmV0cnlBdHRlbXB0c3EAfgAUTAAHc2VydmljZXQAEkxqYXZhL2xhbmcvT2JqZWN0O0wAD3NlcnZpY2VFbmRQb2ludHQADkxqYXZhL25ldC9VUkw7TAATc2VydmljZU5hbWVTcGFjZVVSSXEAfgAETAAQc2VydmljZU5hbWVzcGFjZXEAfgAETAALc2VydmljZVBhdGhxAH4ABHhwc3EAfgAZAXB0ABpPU0NhY2hlTm90aWZpY2F0aW9uU2VydmljZXQATW9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuZXhjZXB0aW9uaGFuZGxpbmcuRGVmYXVsdE1lc3NhZ2VFeGNlcHRpb25IYW5kbGVyc3EAfgAf//////////9zcQB+ABwAAAADc3EAfgAZAHEAfgAycHNyAAxqYXZhLm5ldC5VUkyWJTc2GvzkcgMAB0kACGhhc2hDb2RlSQAEcG9ydEwACWF1dGhvcml0eXEAfgAETAAEZmlsZXEAfgAETAAEaG9zdHEAfgAETAAIcHJvdG9jb2xxAH4ABEwAA3JlZnEAfgAEeHBpG6KgAAAfkHQADmxvY2FsaG9zdDo4MDgwdAArL2tyLWRldi9yZW1vdGluZy9PU0NhY2hlTm90aWZpY2F0aW9uU2VydmljZXQACWxvY2FsaG9zdHQABGh0dHBweHQAAHQABlRSQVZFTHQAAS9zcgATamF2YS51dGlsLkFycmF5TGlzdHiB0h2Zx2GdAwABSQAEc2l6ZXhwAAAABXcEAAAACnQAM29yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuc2VydmljZS5LU0JKYXZhU2VydmljZXQAPGNvbS5vcGVuc3ltcGhvbnkub3NjYWNoZS5iYXNlLmV2ZW50cy5DYWNoZUVudHJ5RXZlbnRMaXN0ZW5lcnQAN2NvbS5vcGVuc3ltcGhvbnkub3NjYWNoZS5iYXNlLmV2ZW50cy5DYWNoZUV2ZW50TGlzdGVuZXJ0ABdqYXZhLnV0aWwuRXZlbnRMaXN0ZW5lcnQALGNvbS5vcGVuc3ltcGhvbnkub3NjYWNoZS5iYXNlLkxpZmVjeWNsZUF3YXJleHEAfgAjdAAGVFJBVkVM';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
INSERT INTO KRSB_MSG_PYLD_T (MSG_PYLD,MSG_QUE_ID)
  VALUES (EMPTY_CLOB(),450)
/
-- Length: 6116
-- Chunks: 2
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 450    FOR UPDATE;        
    buffer := 'rO0ABXNyAC1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLkFzeW5jaHJvbm91c0NhbGzxnQ5Y0t0lZQIAB1oAFWlnbm9yZVN0b3JlQW5kRm9yd2FyZFsACWFyZ3VtZW50c3QAE1tMamF2YS9sYW5nL09iamVjdDtMAAhjYWxsYmFja3QAM0xvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL0FzeW5jaHJvbm91c0NhbGxiYWNrO0wAB2NvbnRleHR0ABZMamF2YS9pby9TZXJpYWxpemFibGU7TAAKbWV0aG9kTmFtZXQAEkxqYXZhL2xhbmcvU3RyaW5nO1sACnBhcmFtVHlwZXN0ABJbTGphdmEvbGFuZy9DbGFzcztMAAtzZXJ2aWNlSW5mb3QAKkxvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL1NlcnZpY2VJbmZvO3hwAHVyABNbTGphdmEubGFuZy5PYmplY3Q7kM5YnxBzKWwCAAB4cAAAAAFzcgBDY29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLnBsdWdpbnMuY2x1c3RlcnN1cHBvcnQuQ2x1c3Rlck5vdGlmaWNhdGlvbhY+6VckvG9pAgACSQAEdHlwZUwABGRhdGFxAH4AA3hwAAAAAnQAOURvY3VtZW50VHlwZVJ1bGVDYWNoZTpSZWNpcGVJbmdyZWRpZW50TWFpbnRlbmFuY2VEb2N1bWVudHBwdAAGaW52b2tldXIAEltMamF2YS5sYW5nLkNsYXNzO6sW167LzVqZAgAAeHAAAAABdnIAFGphdmEuaW8uU2VyaWFsaXphYmxlEJthDdebZO0CAAB4cHNyAChvcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLlNlcnZpY2VJbmZvxRcjtikEt7oCAAtMAAVhbGl2ZXQAE0xqYXZhL2xhbmcvQm9vbGVhbjtMABRlbmRwb2ludEFsdGVybmF0ZVVybHEAfgAETAALZW5kcG9pbnRVcmxxAH4ABEwACmxvY2tWZXJOYnJ0ABNMamF2YS9sYW5nL0ludGVnZXI7TAAObWVzc2FnZUVudHJ5SWR0ABBMamF2YS9sYW5nL0xvbmc7TAAFcW5hbWV0ABtMamF2YXgveG1sL25hbWVzcGFjZS9RTmFtZTtMABpzZXJpYWxpemVkU2VydmljZU5hbWVzcGFjZXEAfgAETAAIc2VydmVySXBxAH4ABEwAEXNlcnZpY2VEZWZpbml0aW9udAAwTG9yZy9rdWFsaS9yaWNlL2tzYi9tZXNzYWdpbmcvU2VydmljZURlZmluaXRpb247TAALc2VydmljZU5hbWVxAH4ABEwAEHNlcnZpY2VOYW1lc3BhY2VxAH4ABHhwc3IAEWphdmEubGFuZy5Cb29sZWFuzSBygNWc+u4CAAFaAAV2YWx1ZXhwAXB0AEBodHRwOi8vbG9jYWxob3N0OjgwODAva3ItZGV2L3JlbW90aW5nL09TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNlc3IAEWphdmEubGFuZy5JbnRlZ2VyEuKgpPeBhzgCAAFJAAV2YWx1ZXhyABBqYXZhLmxhbmcuTnVtYmVyhqyVHQuU4IsCAAB4cAAAAAFzcgAOamF2YS5sYW5nLkxvbmc7i+SQzI8j3wIAAUoABXZhbHVleHEAfgAdAAAAAAAAD31zcgAZamF2YXgueG1sLm5hbWVzcGFjZS5RTmFtZYFtqC38O91sAgADTAAJbG9jYWxQYXJ0cQB+AARMAAxuYW1lc3BhY2VVUklxAH4ABEwABnByZWZpeHEAfgAEeHB0ABpPU0NhY2hlTm90aWZpY2F0aW9uU2VydmljZXQAAHEAfgAkdAd0ck8wQUJYTnlBREp2Y21jdWEzVmhiR2t1Y21salpTNXJjMkl1YldWemMyRm5hVzVuTGtwaGRtRlRaWEoyYVdObFJHVm1hVzVwZEdsdmJyYm5EVjlCTkd6eEFnQUJUQUFSYzJWeWRtbGpaVWx1ZEdWeVptRmpaWE4wQUJCTWFtRjJZUzkxZEdsc0wweHBjM1E3ZUhJQUxtOXlaeTVyZFdGc2FTNXlhV05sTG10ellpNXRaWE56WVdkcGJtY3VVMlZ5ZG1salpVUmxabWx1YVhScGIyNEFtd0pQV04wcGZnSUFEVXdBQzJKMWMxTmxZM1Z5YVhSNWRBQVRUR3BoZG1FdmJHRnVaeTlDYjI5c1pXRnVPMHdBRDJOeVpXUmxiblJwWVd4elZIbHdaWFFBVEV4dmNtY3ZhM1ZoYkdrdmNtbGpaUzlqYjNKbEwzTmxZM1Z5YVhSNUwyTnlaV1JsYm5ScFlXeHpMME55WldSbGJuUnBZV3h6VTI5MWNtTmxKRU55WldSbGJuUnBZV3h6Vkhsd1pUdE1BQkJzYjJOaGJGTmxjblpwWTJWT1lXMWxkQUFTVEdwaGRtRXZiR0Z1Wnk5VGRISnBibWM3VEFBWGJXVnpjMkZuWlVWNFkyVndkR2x2YmtoaGJtUnNaWEp4QUg0QUJVd0FERzFwYkd4cGMxUnZUR2wyWlhRQUVFeHFZWFpoTDJ4aGJtY3ZURzl1Wnp0TUFBaHdjbWx2Y21sMGVYUUFFMHhxWVhaaEwyeGhibWN2U1c1MFpXZGxjanRNQUFWeGRXVjFaWEVBZmdBRFRBQU5jbVYwY25sQmRIUmxiWEIwYzNFQWZnQUhUQUFIYzJWeWRtbGpaWFFBRWt4cVlYWmhMMnhoYm1jdlQySnFaV04wTzB3QUQzTmxjblpwWTJWRmJtUlFiMmx1ZEhRQURreHFZWFpoTDI1bGRDOVZVa3c3VEFBVGMyVnlkbWxqWlU1aGJXVlRjR0ZqWlZWU1NYRUFmZ0FGVEFBUWMyVnlkbWxqWlU1aGJXVnpjR0ZqWlhFQWZnQUZUQUFMYzJWeWRtbGpaVkJoZEdoeEFINEFCWGh3YzNJQUVXcGhkbUV1YkdGdVp5NUNiMjlzWldGdXpTQnlnTldjK3U0Q0FBRmFBQVYyWVd4MVpYaHdBWEIwQUJwUFUwTmhZMmhsVG05MGFXWnBZMkYwYVc5dVUyVnlkbWxqWlhRQVRXOXlaeTVyZFdGc2FTNXlhV05sTG10ellpNXRaWE56WVdkcGJtY3VaWGhqWlhCMGFXOXVhR0Z1Wkd4cGJtY3VSR1ZtWVhWc2RFMWxjM05oWjJWRmVHTmxjSFJwYjI1SVlXNWtiR1Z5YzNJQURtcGhkbUV1YkdGdVp5NU1iMjVuTzR2a2tNeVBJOThDQUFGS0FBVjJZV3gxWlhoeUFCQnFZWFpoTG14aGJtY3VUblZ0WW1WeWhxeVZIUXVVNElzQ0FBQjRjUC8vLy8vLy8vLy9jM0lBRVdwaGRtRXViR0Z1Wnk1SmJuUmxaMlZ5RXVLZ3BQZUJoemdDQUFGSkFBVjJZV3gxWlhoeEFINEFFQUFBQUFOemNRQitBQXNBY1FCK0FCTndjM0lBREdwaGRtRXVibVYwTGxWU1RKWWxOellhL09SeUF3QUhTUUFJYUdGemFFTnZaR1ZKQUFSd2IzSjBUQUFKWVhWMGFHOXlhWFI1Y1FCK0FBVk1BQVJtYVd4bGNRQitBQVZNQUFSb2IzTjBjUUIrQUFWTUFBaHdjbTkwYjJOdmJIRUFmZ0FGVEFBRGNtVm1jUUIrQUFWNGNQLy8vLzhBQUIrUWRBQU9iRzlqWVd4b2IzTjBPamd3T0RCMEFDc3ZhM0l0WkdWMkwzSmxiVzkwYVc1bkwwOVRRMkZqYUdWT2IzUnBabWxqWVhScGIyNVRaWEoyYVdObGRBQUpiRzlqWVd4b2IzTjBkQUFFYUhSMGNIQjRkQUFBZEFBR1ZGSkJWa1ZNZEFBQkwzTnlBQk5xWVhaaExuVjBhV3d1UVhKeVlYbE1hWE4wZUlIU0habkhZWjBEQUFGSkFBUnphWHBsZUhBQUFBQUZkd1FBQUFB';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 450    FOR UPDATE;        
    buffer := 'S2RBQXpiM0puTG10MVlXeHBMbkpwWTJVdWEzTmlMbTFsYzNOaFoybHVaeTV6WlhKMmFXTmxMa3RUUWtwaGRtRlRaWEoyYVdObGRBQThZMjl0TG05d1pXNXplVzF3YUc5dWVTNXZjMk5oWTJobExtSmhjMlV1WlhabGJuUnpMa05oWTJobFJXNTBjbmxGZG1WdWRFeHBjM1JsYm1WeWRBQTNZMjl0TG05d1pXNXplVzF3YUc5dWVTNXZjMk5oWTJobExtSmhjMlV1WlhabGJuUnpMa05oWTJobFJYWmxiblJNYVhOMFpXNWxjblFBRjJwaGRtRXVkWFJwYkM1RmRtVnVkRXhwYzNSbGJtVnlkQUFzWTI5dExtOXdaVzV6ZVcxd2FHOXVlUzV2YzJOaFkyaGxMbUpoYzJVdVRHbG1aV041WTJ4bFFYZGhjbVY0dAANMTI5LjE4Ni43OS40NXNyADJvcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLkphdmFTZXJ2aWNlRGVmaW5pdGlvbrbnDV9BNGzxAgABTAARc2VydmljZUludGVyZmFjZXN0ABBMamF2YS91dGlsL0xpc3Q7eHIALm9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuU2VydmljZURlZmluaXRpb24AmwJPWN0pfgIADUwAC2J1c1NlY3VyaXR5cQB+ABNMAA9jcmVkZW50aWFsc1R5cGV0AExMb3JnL2t1YWxpL3JpY2UvY29yZS9zZWN1cml0eS9jcmVkZW50aWFscy9DcmVkZW50aWFsc1NvdXJjZSRDcmVkZW50aWFsc1R5cGU7TAAQbG9jYWxTZXJ2aWNlTmFtZXEAfgAETAAXbWVzc2FnZUV4Y2VwdGlvbkhhbmRsZXJxAH4ABEwADG1pbGxpc1RvTGl2ZXEAfgAVTAAIcHJpb3JpdHlxAH4AFEwABXF1ZXVlcQB+ABNMAA1yZXRyeUF0dGVtcHRzcQB+ABRMAAdzZXJ2aWNldAASTGphdmEvbGFuZy9PYmplY3Q7TAAPc2VydmljZUVuZFBvaW50dAAOTGphdmEvbmV0L1VSTDtMABNzZXJ2aWNlTmFtZVNwYWNlVVJJcQB+AARMABBzZXJ2aWNlTmFtZXNwYWNlcQB+AARMAAtzZXJ2aWNlUGF0aHEAfgAEeHBzcQB+ABkBcHQAGk9TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNldABNb3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5leGNlcHRpb25oYW5kbGluZy5EZWZhdWx0TWVzc2FnZUV4Y2VwdGlvbkhhbmRsZXJzcQB+AB///////////3NxAH4AHAAAAANzcQB+ABkAcQB+ADJwc3IADGphdmEubmV0LlVSTJYlNzYa/ORyAwAHSQAIaGFzaENvZGVJAARwb3J0TAAJYXV0aG9yaXR5cQB+AARMAARmaWxlcQB+AARMAARob3N0cQB+AARMAAhwcm90b2NvbHEAfgAETAADcmVmcQB+AAR4cGkboqAAAB+QdAAObG9jYWxob3N0OjgwODB0ACsva3ItZGV2L3JlbW90aW5nL09TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNldAAJbG9jYWxob3N0dAAEaHR0cHB4dAAAdAAGVFJBVkVMdAABL3NyABNqYXZhLnV0aWwuQXJyYXlMaXN0eIHSHZnHYZ0DAAFJAARzaXpleHAAAAAFdwQAAAAKdAAzb3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5zZXJ2aWNlLktTQkphdmFTZXJ2aWNldAA8Y29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLmJhc2UuZXZlbnRzLkNhY2hlRW50cnlFdmVudExpc3RlbmVydAA3Y29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLmJhc2UuZXZlbnRzLkNhY2hlRXZlbnRMaXN0ZW5lcnQAF2phdmEudXRpbC5FdmVudExpc3RlbmVydAAsY29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLmJhc2UuTGlmZWN5Y2xlQXdhcmV4cQB+ACN0AAZUUkFWRUw=';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
INSERT INTO KRSB_MSG_PYLD_T (MSG_PYLD,MSG_QUE_ID)
  VALUES (EMPTY_CLOB(),451)
/
-- Length: 6104
-- Chunks: 2
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 451    FOR UPDATE;        
    buffer := 'rO0ABXNyAC1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLkFzeW5jaHJvbm91c0NhbGzxnQ5Y0t0lZQIAB1oAFWlnbm9yZVN0b3JlQW5kRm9yd2FyZFsACWFyZ3VtZW50c3QAE1tMamF2YS9sYW5nL09iamVjdDtMAAhjYWxsYmFja3QAM0xvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL0FzeW5jaHJvbm91c0NhbGxiYWNrO0wAB2NvbnRleHR0ABZMamF2YS9pby9TZXJpYWxpemFibGU7TAAKbWV0aG9kTmFtZXQAEkxqYXZhL2xhbmcvU3RyaW5nO1sACnBhcmFtVHlwZXN0ABJbTGphdmEvbGFuZy9DbGFzcztMAAtzZXJ2aWNlSW5mb3QAKkxvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL1NlcnZpY2VJbmZvO3hwAHVyABNbTGphdmEubGFuZy5PYmplY3Q7kM5YnxBzKWwCAAB4cAAAAAFzcgBDY29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLnBsdWdpbnMuY2x1c3RlcnN1cHBvcnQuQ2x1c3Rlck5vdGlmaWNhdGlvbhY+6VckvG9pAgACSQAEdHlwZUwABGRhdGFxAH4AA3hwAAAAAnQAL0RvY3VtZW50VHlwZVJ1bGVDYWNoZTpSZWNpcGVNYWludGVuYW5jZURvY3VtZW50cHB0AAZpbnZva2V1cgASW0xqYXZhLmxhbmcuQ2xhc3M7qxbXrsvNWpkCAAB4cAAAAAF2cgAUamF2YS5pby5TZXJpYWxpemFibGUQm2EN15tk7QIAAHhwc3IAKG9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuU2VydmljZUluZm/FFyO2KQS3ugIAC0wABWFsaXZldAATTGphdmEvbGFuZy9Cb29sZWFuO0wAFGVuZHBvaW50QWx0ZXJuYXRlVXJscQB+AARMAAtlbmRwb2ludFVybHEAfgAETAAKbG9ja1Zlck5icnQAE0xqYXZhL2xhbmcvSW50ZWdlcjtMAA5tZXNzYWdlRW50cnlJZHQAEExqYXZhL2xhbmcvTG9uZztMAAVxbmFtZXQAG0xqYXZheC94bWwvbmFtZXNwYWNlL1FOYW1lO0wAGnNlcmlhbGl6ZWRTZXJ2aWNlTmFtZXNwYWNlcQB+AARMAAhzZXJ2ZXJJcHEAfgAETAARc2VydmljZURlZmluaXRpb250ADBMb3JnL2t1YWxpL3JpY2Uva3NiL21lc3NhZ2luZy9TZXJ2aWNlRGVmaW5pdGlvbjtMAAtzZXJ2aWNlTmFtZXEAfgAETAAQc2VydmljZU5hbWVzcGFjZXEAfgAEeHBzcgARamF2YS5sYW5nLkJvb2xlYW7NIHKA1Zz67gIAAVoABXZhbHVleHABcHQAQGh0dHA6Ly9sb2NhbGhvc3Q6ODA4MC9rci1kZXYvcmVtb3RpbmcvT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2VzcgARamF2YS5sYW5nLkludGVnZXIS4qCk94GHOAIAAUkABXZhbHVleHIAEGphdmEubGFuZy5OdW1iZXKGrJUdC5TgiwIAAHhwAAAAAXNyAA5qYXZhLmxhbmcuTG9uZzuL5JDMjyPfAgABSgAFdmFsdWV4cQB+AB0AAAAAAAAPfXNyABlqYXZheC54bWwubmFtZXNwYWNlLlFOYW1lgW2oLfw73WwCAANMAAlsb2NhbFBhcnRxAH4ABEwADG5hbWVzcGFjZVVSSXEAfgAETAAGcHJlZml4cQB+AAR4cHQAGk9TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNldAAAcQB+ACR0B3RyTzBBQlhOeUFESnZjbWN1YTNWaGJHa3VjbWxqWlM1cmMySXViV1Z6YzJGbmFXNW5Ma3BoZG1GVFpYSjJhV05sUkdWbWFXNXBkR2x2YnJibkRWOUJOR3p4QWdBQlRBQVJjMlZ5ZG1salpVbHVkR1Z5Wm1GalpYTjBBQkJNYW1GMllTOTFkR2xzTDB4cGMzUTdlSElBTG05eVp5NXJkV0ZzYVM1eWFXTmxMbXR6WWk1dFpYTnpZV2RwYm1jdVUyVnlkbWxqWlVSbFptbHVhWFJwYjI0QW13SlBXTjBwZmdJQURVd0FDMkoxYzFObFkzVnlhWFI1ZEFBVFRHcGhkbUV2YkdGdVp5OUNiMjlzWldGdU8wd0FEMk55WldSbGJuUnBZV3h6Vkhsd1pYUUFURXh2Y21jdmEzVmhiR2t2Y21salpTOWpiM0psTDNObFkzVnlhWFI1TDJOeVpXUmxiblJwWVd4ekwwTnlaV1JsYm5ScFlXeHpVMjkxY21ObEpFTnlaV1JsYm5ScFlXeHpWSGx3WlR0TUFCQnNiMk5oYkZObGNuWnBZMlZPWVcxbGRBQVNUR3BoZG1FdmJHRnVaeTlUZEhKcGJtYzdUQUFYYldWemMyRm5aVVY0WTJWd2RHbHZia2hoYm1Sc1pYSnhBSDRBQlV3QURHMXBiR3hwYzFSdlRHbDJaWFFBRUV4cVlYWmhMMnhoYm1jdlRHOXVaenRNQUFod2NtbHZjbWwwZVhRQUUweHFZWFpoTDJ4aGJtY3ZTVzUwWldkbGNqdE1BQVZ4ZFdWMVpYRUFmZ0FEVEFBTmNtVjBjbmxCZEhSbGJYQjBjM0VBZmdBSFRBQUhjMlZ5ZG1salpYUUFFa3hxWVhaaEwyeGhibWN2VDJKcVpXTjBPMHdBRDNObGNuWnBZMlZGYm1SUWIybHVkSFFBRGt4cVlYWmhMMjVsZEM5VlVrdzdUQUFUYzJWeWRtbGpaVTVoYldWVGNHRmpaVlZTU1hFQWZnQUZUQUFRYzJWeWRtbGpaVTVoYldWemNHRmpaWEVBZmdBRlRBQUxjMlZ5ZG1salpWQmhkR2h4QUg0QUJYaHdjM0lBRVdwaGRtRXViR0Z1Wnk1Q2IyOXNaV0Z1elNCeWdOV2MrdTRDQUFGYUFBVjJZV3gxWlhod0FYQjBBQnBQVTBOaFkyaGxUbTkwYVdacFkyRjBhVzl1VTJWeWRtbGpaWFFBVFc5eVp5NXJkV0ZzYVM1eWFXTmxMbXR6WWk1dFpYTnpZV2RwYm1jdVpYaGpaWEIwYVc5dWFHRnVaR3hwYm1jdVJHVm1ZWFZzZEUxbGMzTmhaMlZGZUdObGNIUnBiMjVJWVc1a2JHVnljM0lBRG1waGRtRXViR0Z1Wnk1TWIyNW5PNHZra015UEk5OENBQUZLQUFWMllXeDFaWGh5QUJCcVlYWmhMbXhoYm1jdVRuVnRZbVZ5aHF5VkhRdVU0SXNDQUFCNGNQLy8vLy8vLy8vL2MzSUFFV3BoZG1FdWJHRnVaeTVKYm5SbFoyVnlFdUtncFBlQmh6Z0NBQUZKQUFWMllXeDFaWGh4QUg0QUVBQUFBQU56Y1FCK0FBc0FjUUIrQUJOd2MzSUFER3BoZG1FdWJtVjBMbFZTVEpZbE56WWEvT1J5QXdBSFNRQUlhR0Z6YUVOdlpHVkpBQVJ3YjNKMFRBQUpZWFYwYUc5eWFYUjVjUUIrQUFWTUFBUm1hV3hsY1FCK0FBVk1BQVJvYjNOMGNRQitBQVZNQUFod2NtOTBiMk52YkhFQWZnQUZUQUFEY21WbWNRQitBQVY0Y1AvLy8vOEFBQitRZEFBT2JHOWpZV3hvYjNOME9qZ3dPREIwQUNzdmEzSXRaR1YyTDNKbGJXOTBhVzVuTDA5VFEyRmphR1ZPYjNScFptbGpZWFJwYjI1VFpYSjJhV05sZEFBSmJHOWpZV3hvYjNOMGRBQUVhSFIwY0hCNGRBQUFkQUFHVkZKQlZrVk1kQUFCTDNOeUFCTnFZWFpoTG5WMGFXd3VRWEp5WVhsTWFYTjBlSUhTSFpuSFlaMERBQUZKQUFSemFYcGxlSEFBQUFBRmR3UUFBQUFLZEFBemIzSm5M';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 451    FOR UPDATE;        
    buffer := 'bXQxWVd4cExuSnBZMlV1YTNOaUxtMWxjM05oWjJsdVp5NXpaWEoyYVdObExrdFRRa3BoZG1GVFpYSjJhV05sZEFBOFkyOXRMbTl3Wlc1emVXMXdhRzl1ZVM1dmMyTmhZMmhsTG1KaGMyVXVaWFpsYm5SekxrTmhZMmhsUlc1MGNubEZkbVZ1ZEV4cGMzUmxibVZ5ZEFBM1kyOXRMbTl3Wlc1emVXMXdhRzl1ZVM1dmMyTmhZMmhsTG1KaGMyVXVaWFpsYm5SekxrTmhZMmhsUlhabGJuUk1hWE4wWlc1bGNuUUFGMnBoZG1FdWRYUnBiQzVGZG1WdWRFeHBjM1JsYm1WeWRBQXNZMjl0TG05d1pXNXplVzF3YUc5dWVTNXZjMk5oWTJobExtSmhjMlV1VEdsbVpXTjVZMnhsUVhkaGNtVjR0AA0xMjkuMTg2Ljc5LjQ1c3IAMm9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuSmF2YVNlcnZpY2VEZWZpbml0aW9utucNX0E0bPECAAFMABFzZXJ2aWNlSW50ZXJmYWNlc3QAEExqYXZhL3V0aWwvTGlzdDt4cgAub3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5TZXJ2aWNlRGVmaW5pdGlvbgCbAk9Y3Sl+AgANTAALYnVzU2VjdXJpdHlxAH4AE0wAD2NyZWRlbnRpYWxzVHlwZXQATExvcmcva3VhbGkvcmljZS9jb3JlL3NlY3VyaXR5L2NyZWRlbnRpYWxzL0NyZWRlbnRpYWxzU291cmNlJENyZWRlbnRpYWxzVHlwZTtMABBsb2NhbFNlcnZpY2VOYW1lcQB+AARMABdtZXNzYWdlRXhjZXB0aW9uSGFuZGxlcnEAfgAETAAMbWlsbGlzVG9MaXZlcQB+ABVMAAhwcmlvcml0eXEAfgAUTAAFcXVldWVxAH4AE0wADXJldHJ5QXR0ZW1wdHNxAH4AFEwAB3NlcnZpY2V0ABJMamF2YS9sYW5nL09iamVjdDtMAA9zZXJ2aWNlRW5kUG9pbnR0AA5MamF2YS9uZXQvVVJMO0wAE3NlcnZpY2VOYW1lU3BhY2VVUklxAH4ABEwAEHNlcnZpY2VOYW1lc3BhY2VxAH4ABEwAC3NlcnZpY2VQYXRocQB+AAR4cHNxAH4AGQFwdAAaT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2V0AE1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLmV4Y2VwdGlvbmhhbmRsaW5nLkRlZmF1bHRNZXNzYWdlRXhjZXB0aW9uSGFuZGxlcnNxAH4AH///////////c3EAfgAcAAAAA3NxAH4AGQBxAH4AMnBzcgAMamF2YS5uZXQuVVJMliU3Nhr85HIDAAdJAAhoYXNoQ29kZUkABHBvcnRMAAlhdXRob3JpdHlxAH4ABEwABGZpbGVxAH4ABEwABGhvc3RxAH4ABEwACHByb3RvY29scQB+AARMAANyZWZxAH4ABHhwaRuioAAAH5B0AA5sb2NhbGhvc3Q6ODA4MHQAKy9rci1kZXYvcmVtb3RpbmcvT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2V0AAlsb2NhbGhvc3R0AARodHRwcHh0AAB0AAZUUkFWRUx0AAEvc3IAE2phdmEudXRpbC5BcnJheUxpc3R4gdIdmcdhnQMAAUkABHNpemV4cAAAAAV3BAAAAAp0ADNvcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLnNlcnZpY2UuS1NCSmF2YVNlcnZpY2V0ADxjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5ldmVudHMuQ2FjaGVFbnRyeUV2ZW50TGlzdGVuZXJ0ADdjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5ldmVudHMuQ2FjaGVFdmVudExpc3RlbmVydAAXamF2YS51dGlsLkV2ZW50TGlzdGVuZXJ0ACxjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5MaWZlY3ljbGVBd2FyZXhxAH4AI3QABlRSQVZFTA==';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
INSERT INTO KRSB_MSG_PYLD_T (MSG_PYLD,MSG_QUE_ID)
  VALUES (EMPTY_CLOB(),452)
/
-- Length: 6104
-- Chunks: 2
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 452    FOR UPDATE;        
    buffer := 'rO0ABXNyAC1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLkFzeW5jaHJvbm91c0NhbGzxnQ5Y0t0lZQIAB1oAFWlnbm9yZVN0b3JlQW5kRm9yd2FyZFsACWFyZ3VtZW50c3QAE1tMamF2YS9sYW5nL09iamVjdDtMAAhjYWxsYmFja3QAM0xvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL0FzeW5jaHJvbm91c0NhbGxiYWNrO0wAB2NvbnRleHR0ABZMamF2YS9pby9TZXJpYWxpemFibGU7TAAKbWV0aG9kTmFtZXQAEkxqYXZhL2xhbmcvU3RyaW5nO1sACnBhcmFtVHlwZXN0ABJbTGphdmEvbGFuZy9DbGFzcztMAAtzZXJ2aWNlSW5mb3QAKkxvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL1NlcnZpY2VJbmZvO3hwAHVyABNbTGphdmEubGFuZy5PYmplY3Q7kM5YnxBzKWwCAAB4cAAAAAFzcgBDY29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLnBsdWdpbnMuY2x1c3RlcnN1cHBvcnQuQ2x1c3Rlck5vdGlmaWNhdGlvbhY+6VckvG9pAgACSQAEdHlwZUwABGRhdGFxAH4AA3hwAAAAAnQAL0RvY3VtZW50VHlwZVJ1bGVDYWNoZTpDYW1wdXNNYWludGVuYW5jZURvY3VtZW50cHB0AAZpbnZva2V1cgASW0xqYXZhLmxhbmcuQ2xhc3M7qxbXrsvNWpkCAAB4cAAAAAF2cgAUamF2YS5pby5TZXJpYWxpemFibGUQm2EN15tk7QIAAHhwc3IAKG9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuU2VydmljZUluZm/FFyO2KQS3ugIAC0wABWFsaXZldAATTGphdmEvbGFuZy9Cb29sZWFuO0wAFGVuZHBvaW50QWx0ZXJuYXRlVXJscQB+AARMAAtlbmRwb2ludFVybHEAfgAETAAKbG9ja1Zlck5icnQAE0xqYXZhL2xhbmcvSW50ZWdlcjtMAA5tZXNzYWdlRW50cnlJZHQAEExqYXZhL2xhbmcvTG9uZztMAAVxbmFtZXQAG0xqYXZheC94bWwvbmFtZXNwYWNlL1FOYW1lO0wAGnNlcmlhbGl6ZWRTZXJ2aWNlTmFtZXNwYWNlcQB+AARMAAhzZXJ2ZXJJcHEAfgAETAARc2VydmljZURlZmluaXRpb250ADBMb3JnL2t1YWxpL3JpY2Uva3NiL21lc3NhZ2luZy9TZXJ2aWNlRGVmaW5pdGlvbjtMAAtzZXJ2aWNlTmFtZXEAfgAETAAQc2VydmljZU5hbWVzcGFjZXEAfgAEeHBzcgARamF2YS5sYW5nLkJvb2xlYW7NIHKA1Zz67gIAAVoABXZhbHVleHABcHQAQGh0dHA6Ly9sb2NhbGhvc3Q6ODA4MC9rci1kZXYvcmVtb3RpbmcvT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2VzcgARamF2YS5sYW5nLkludGVnZXIS4qCk94GHOAIAAUkABXZhbHVleHIAEGphdmEubGFuZy5OdW1iZXKGrJUdC5TgiwIAAHhwAAAAAXNyAA5qYXZhLmxhbmcuTG9uZzuL5JDMjyPfAgABSgAFdmFsdWV4cQB+AB0AAAAAAAAPfXNyABlqYXZheC54bWwubmFtZXNwYWNlLlFOYW1lgW2oLfw73WwCAANMAAlsb2NhbFBhcnRxAH4ABEwADG5hbWVzcGFjZVVSSXEAfgAETAAGcHJlZml4cQB+AAR4cHQAGk9TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNldAAAcQB+ACR0B3RyTzBBQlhOeUFESnZjbWN1YTNWaGJHa3VjbWxqWlM1cmMySXViV1Z6YzJGbmFXNW5Ma3BoZG1GVFpYSjJhV05sUkdWbWFXNXBkR2x2YnJibkRWOUJOR3p4QWdBQlRBQVJjMlZ5ZG1salpVbHVkR1Z5Wm1GalpYTjBBQkJNYW1GMllTOTFkR2xzTDB4cGMzUTdlSElBTG05eVp5NXJkV0ZzYVM1eWFXTmxMbXR6WWk1dFpYTnpZV2RwYm1jdVUyVnlkbWxqWlVSbFptbHVhWFJwYjI0QW13SlBXTjBwZmdJQURVd0FDMkoxYzFObFkzVnlhWFI1ZEFBVFRHcGhkbUV2YkdGdVp5OUNiMjlzWldGdU8wd0FEMk55WldSbGJuUnBZV3h6Vkhsd1pYUUFURXh2Y21jdmEzVmhiR2t2Y21salpTOWpiM0psTDNObFkzVnlhWFI1TDJOeVpXUmxiblJwWVd4ekwwTnlaV1JsYm5ScFlXeHpVMjkxY21ObEpFTnlaV1JsYm5ScFlXeHpWSGx3WlR0TUFCQnNiMk5oYkZObGNuWnBZMlZPWVcxbGRBQVNUR3BoZG1FdmJHRnVaeTlUZEhKcGJtYzdUQUFYYldWemMyRm5aVVY0WTJWd2RHbHZia2hoYm1Sc1pYSnhBSDRBQlV3QURHMXBiR3hwYzFSdlRHbDJaWFFBRUV4cVlYWmhMMnhoYm1jdlRHOXVaenRNQUFod2NtbHZjbWwwZVhRQUUweHFZWFpoTDJ4aGJtY3ZTVzUwWldkbGNqdE1BQVZ4ZFdWMVpYRUFmZ0FEVEFBTmNtVjBjbmxCZEhSbGJYQjBjM0VBZmdBSFRBQUhjMlZ5ZG1salpYUUFFa3hxWVhaaEwyeGhibWN2VDJKcVpXTjBPMHdBRDNObGNuWnBZMlZGYm1SUWIybHVkSFFBRGt4cVlYWmhMMjVsZEM5VlVrdzdUQUFUYzJWeWRtbGpaVTVoYldWVGNHRmpaVlZTU1hFQWZnQUZUQUFRYzJWeWRtbGpaVTVoYldWemNHRmpaWEVBZmdBRlRBQUxjMlZ5ZG1salpWQmhkR2h4QUg0QUJYaHdjM0lBRVdwaGRtRXViR0Z1Wnk1Q2IyOXNaV0Z1elNCeWdOV2MrdTRDQUFGYUFBVjJZV3gxWlhod0FYQjBBQnBQVTBOaFkyaGxUbTkwYVdacFkyRjBhVzl1VTJWeWRtbGpaWFFBVFc5eVp5NXJkV0ZzYVM1eWFXTmxMbXR6WWk1dFpYTnpZV2RwYm1jdVpYaGpaWEIwYVc5dWFHRnVaR3hwYm1jdVJHVm1ZWFZzZEUxbGMzTmhaMlZGZUdObGNIUnBiMjVJWVc1a2JHVnljM0lBRG1waGRtRXViR0Z1Wnk1TWIyNW5PNHZra015UEk5OENBQUZLQUFWMllXeDFaWGh5QUJCcVlYWmhMbXhoYm1jdVRuVnRZbVZ5aHF5VkhRdVU0SXNDQUFCNGNQLy8vLy8vLy8vL2MzSUFFV3BoZG1FdWJHRnVaeTVKYm5SbFoyVnlFdUtncFBlQmh6Z0NBQUZKQUFWMllXeDFaWGh4QUg0QUVBQUFBQU56Y1FCK0FBc0FjUUIrQUJOd2MzSUFER3BoZG1FdWJtVjBMbFZTVEpZbE56WWEvT1J5QXdBSFNRQUlhR0Z6YUVOdlpHVkpBQVJ3YjNKMFRBQUpZWFYwYUc5eWFYUjVjUUIrQUFWTUFBUm1hV3hsY1FCK0FBVk1BQVJvYjNOMGNRQitBQVZNQUFod2NtOTBiMk52YkhFQWZnQUZUQUFEY21WbWNRQitBQVY0Y1AvLy8vOEFBQitRZEFBT2JHOWpZV3hvYjNOME9qZ3dPREIwQUNzdmEzSXRaR1YyTDNKbGJXOTBhVzVuTDA5VFEyRmphR1ZPYjNScFptbGpZWFJwYjI1VFpYSjJhV05sZEFBSmJHOWpZV3hvYjNOMGRBQUVhSFIwY0hCNGRBQUFkQUFHVkZKQlZrVk1kQUFCTDNOeUFCTnFZWFpoTG5WMGFXd3VRWEp5WVhsTWFYTjBlSUhTSFpuSFlaMERBQUZKQUFSemFYcGxlSEFBQUFBRmR3UUFBQUFLZEFBemIzSm5M';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 452    FOR UPDATE;        
    buffer := 'bXQxWVd4cExuSnBZMlV1YTNOaUxtMWxjM05oWjJsdVp5NXpaWEoyYVdObExrdFRRa3BoZG1GVFpYSjJhV05sZEFBOFkyOXRMbTl3Wlc1emVXMXdhRzl1ZVM1dmMyTmhZMmhsTG1KaGMyVXVaWFpsYm5SekxrTmhZMmhsUlc1MGNubEZkbVZ1ZEV4cGMzUmxibVZ5ZEFBM1kyOXRMbTl3Wlc1emVXMXdhRzl1ZVM1dmMyTmhZMmhsTG1KaGMyVXVaWFpsYm5SekxrTmhZMmhsUlhabGJuUk1hWE4wWlc1bGNuUUFGMnBoZG1FdWRYUnBiQzVGZG1WdWRFeHBjM1JsYm1WeWRBQXNZMjl0TG05d1pXNXplVzF3YUc5dWVTNXZjMk5oWTJobExtSmhjMlV1VEdsbVpXTjVZMnhsUVhkaGNtVjR0AA0xMjkuMTg2Ljc5LjQ1c3IAMm9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuSmF2YVNlcnZpY2VEZWZpbml0aW9utucNX0E0bPECAAFMABFzZXJ2aWNlSW50ZXJmYWNlc3QAEExqYXZhL3V0aWwvTGlzdDt4cgAub3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5TZXJ2aWNlRGVmaW5pdGlvbgCbAk9Y3Sl+AgANTAALYnVzU2VjdXJpdHlxAH4AE0wAD2NyZWRlbnRpYWxzVHlwZXQATExvcmcva3VhbGkvcmljZS9jb3JlL3NlY3VyaXR5L2NyZWRlbnRpYWxzL0NyZWRlbnRpYWxzU291cmNlJENyZWRlbnRpYWxzVHlwZTtMABBsb2NhbFNlcnZpY2VOYW1lcQB+AARMABdtZXNzYWdlRXhjZXB0aW9uSGFuZGxlcnEAfgAETAAMbWlsbGlzVG9MaXZlcQB+ABVMAAhwcmlvcml0eXEAfgAUTAAFcXVldWVxAH4AE0wADXJldHJ5QXR0ZW1wdHNxAH4AFEwAB3NlcnZpY2V0ABJMamF2YS9sYW5nL09iamVjdDtMAA9zZXJ2aWNlRW5kUG9pbnR0AA5MamF2YS9uZXQvVVJMO0wAE3NlcnZpY2VOYW1lU3BhY2VVUklxAH4ABEwAEHNlcnZpY2VOYW1lc3BhY2VxAH4ABEwAC3NlcnZpY2VQYXRocQB+AAR4cHNxAH4AGQFwdAAaT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2V0AE1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLmV4Y2VwdGlvbmhhbmRsaW5nLkRlZmF1bHRNZXNzYWdlRXhjZXB0aW9uSGFuZGxlcnNxAH4AH///////////c3EAfgAcAAAAA3NxAH4AGQBxAH4AMnBzcgAMamF2YS5uZXQuVVJMliU3Nhr85HIDAAdJAAhoYXNoQ29kZUkABHBvcnRMAAlhdXRob3JpdHlxAH4ABEwABGZpbGVxAH4ABEwABGhvc3RxAH4ABEwACHByb3RvY29scQB+AARMAANyZWZxAH4ABHhwaRuioAAAH5B0AA5sb2NhbGhvc3Q6ODA4MHQAKy9rci1kZXYvcmVtb3RpbmcvT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2V0AAlsb2NhbGhvc3R0AARodHRwcHh0AAB0AAZUUkFWRUx0AAEvc3IAE2phdmEudXRpbC5BcnJheUxpc3R4gdIdmcdhnQMAAUkABHNpemV4cAAAAAV3BAAAAAp0ADNvcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLnNlcnZpY2UuS1NCSmF2YVNlcnZpY2V0ADxjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5ldmVudHMuQ2FjaGVFbnRyeUV2ZW50TGlzdGVuZXJ0ADdjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5ldmVudHMuQ2FjaGVFdmVudExpc3RlbmVydAAXamF2YS51dGlsLkV2ZW50TGlzdGVuZXJ0ACxjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5MaWZlY3ljbGVBd2FyZXhxAH4AI3QABlRSQVZFTA==';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
INSERT INTO KRSB_MSG_PYLD_T (MSG_PYLD,MSG_QUE_ID)
  VALUES (EMPTY_CLOB(),453)
/
-- Length: 6108
-- Chunks: 2
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 453    FOR UPDATE;        
    buffer := 'rO0ABXNyAC1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLkFzeW5jaHJvbm91c0NhbGzxnQ5Y0t0lZQIAB1oAFWlnbm9yZVN0b3JlQW5kRm9yd2FyZFsACWFyZ3VtZW50c3QAE1tMamF2YS9sYW5nL09iamVjdDtMAAhjYWxsYmFja3QAM0xvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL0FzeW5jaHJvbm91c0NhbGxiYWNrO0wAB2NvbnRleHR0ABZMamF2YS9pby9TZXJpYWxpemFibGU7TAAKbWV0aG9kTmFtZXQAEkxqYXZhL2xhbmcvU3RyaW5nO1sACnBhcmFtVHlwZXN0ABJbTGphdmEvbGFuZy9DbGFzcztMAAtzZXJ2aWNlSW5mb3QAKkxvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL1NlcnZpY2VJbmZvO3hwAHVyABNbTGphdmEubGFuZy5PYmplY3Q7kM5YnxBzKWwCAAB4cAAAAAFzcgBDY29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLnBsdWdpbnMuY2x1c3RlcnN1cHBvcnQuQ2x1c3Rlck5vdGlmaWNhdGlvbhY+6VckvG9pAgACSQAEdHlwZUwABGRhdGFxAH4AA3hwAAAAAnQAM0RvY3VtZW50VHlwZVJ1bGVDYWNoZTpDYW1wdXNUeXBlTWFpbnRlbmFuY2VEb2N1bWVudHBwdAAGaW52b2tldXIAEltMamF2YS5sYW5nLkNsYXNzO6sW167LzVqZAgAAeHAAAAABdnIAFGphdmEuaW8uU2VyaWFsaXphYmxlEJthDdebZO0CAAB4cHNyAChvcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLlNlcnZpY2VJbmZvxRcjtikEt7oCAAtMAAVhbGl2ZXQAE0xqYXZhL2xhbmcvQm9vbGVhbjtMABRlbmRwb2ludEFsdGVybmF0ZVVybHEAfgAETAALZW5kcG9pbnRVcmxxAH4ABEwACmxvY2tWZXJOYnJ0ABNMamF2YS9sYW5nL0ludGVnZXI7TAAObWVzc2FnZUVudHJ5SWR0ABBMamF2YS9sYW5nL0xvbmc7TAAFcW5hbWV0ABtMamF2YXgveG1sL25hbWVzcGFjZS9RTmFtZTtMABpzZXJpYWxpemVkU2VydmljZU5hbWVzcGFjZXEAfgAETAAIc2VydmVySXBxAH4ABEwAEXNlcnZpY2VEZWZpbml0aW9udAAwTG9yZy9rdWFsaS9yaWNlL2tzYi9tZXNzYWdpbmcvU2VydmljZURlZmluaXRpb247TAALc2VydmljZU5hbWVxAH4ABEwAEHNlcnZpY2VOYW1lc3BhY2VxAH4ABHhwc3IAEWphdmEubGFuZy5Cb29sZWFuzSBygNWc+u4CAAFaAAV2YWx1ZXhwAXB0AEBodHRwOi8vbG9jYWxob3N0OjgwODAva3ItZGV2L3JlbW90aW5nL09TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNlc3IAEWphdmEubGFuZy5JbnRlZ2VyEuKgpPeBhzgCAAFJAAV2YWx1ZXhyABBqYXZhLmxhbmcuTnVtYmVyhqyVHQuU4IsCAAB4cAAAAAFzcgAOamF2YS5sYW5nLkxvbmc7i+SQzI8j3wIAAUoABXZhbHVleHEAfgAdAAAAAAAAD31zcgAZamF2YXgueG1sLm5hbWVzcGFjZS5RTmFtZYFtqC38O91sAgADTAAJbG9jYWxQYXJ0cQB+AARMAAxuYW1lc3BhY2VVUklxAH4ABEwABnByZWZpeHEAfgAEeHB0ABpPU0NhY2hlTm90aWZpY2F0aW9uU2VydmljZXQAAHEAfgAkdAd0ck8wQUJYTnlBREp2Y21jdWEzVmhiR2t1Y21salpTNXJjMkl1YldWemMyRm5hVzVuTGtwaGRtRlRaWEoyYVdObFJHVm1hVzVwZEdsdmJyYm5EVjlCTkd6eEFnQUJUQUFSYzJWeWRtbGpaVWx1ZEdWeVptRmpaWE4wQUJCTWFtRjJZUzkxZEdsc0wweHBjM1E3ZUhJQUxtOXlaeTVyZFdGc2FTNXlhV05sTG10ellpNXRaWE56WVdkcGJtY3VVMlZ5ZG1salpVUmxabWx1YVhScGIyNEFtd0pQV04wcGZnSUFEVXdBQzJKMWMxTmxZM1Z5YVhSNWRBQVRUR3BoZG1FdmJHRnVaeTlDYjI5c1pXRnVPMHdBRDJOeVpXUmxiblJwWVd4elZIbHdaWFFBVEV4dmNtY3ZhM1ZoYkdrdmNtbGpaUzlqYjNKbEwzTmxZM1Z5YVhSNUwyTnlaV1JsYm5ScFlXeHpMME55WldSbGJuUnBZV3h6VTI5MWNtTmxKRU55WldSbGJuUnBZV3h6Vkhsd1pUdE1BQkJzYjJOaGJGTmxjblpwWTJWT1lXMWxkQUFTVEdwaGRtRXZiR0Z1Wnk5VGRISnBibWM3VEFBWGJXVnpjMkZuWlVWNFkyVndkR2x2YmtoaGJtUnNaWEp4QUg0QUJVd0FERzFwYkd4cGMxUnZUR2wyWlhRQUVFeHFZWFpoTDJ4aGJtY3ZURzl1Wnp0TUFBaHdjbWx2Y21sMGVYUUFFMHhxWVhaaEwyeGhibWN2U1c1MFpXZGxjanRNQUFWeGRXVjFaWEVBZmdBRFRBQU5jbVYwY25sQmRIUmxiWEIwYzNFQWZnQUhUQUFIYzJWeWRtbGpaWFFBRWt4cVlYWmhMMnhoYm1jdlQySnFaV04wTzB3QUQzTmxjblpwWTJWRmJtUlFiMmx1ZEhRQURreHFZWFpoTDI1bGRDOVZVa3c3VEFBVGMyVnlkbWxqWlU1aGJXVlRjR0ZqWlZWU1NYRUFmZ0FGVEFBUWMyVnlkbWxqWlU1aGJXVnpjR0ZqWlhFQWZnQUZUQUFMYzJWeWRtbGpaVkJoZEdoeEFINEFCWGh3YzNJQUVXcGhkbUV1YkdGdVp5NUNiMjlzWldGdXpTQnlnTldjK3U0Q0FBRmFBQVYyWVd4MVpYaHdBWEIwQUJwUFUwTmhZMmhsVG05MGFXWnBZMkYwYVc5dVUyVnlkbWxqWlhRQVRXOXlaeTVyZFdGc2FTNXlhV05sTG10ellpNXRaWE56WVdkcGJtY3VaWGhqWlhCMGFXOXVhR0Z1Wkd4cGJtY3VSR1ZtWVhWc2RFMWxjM05oWjJWRmVHTmxjSFJwYjI1SVlXNWtiR1Z5YzNJQURtcGhkbUV1YkdGdVp5NU1iMjVuTzR2a2tNeVBJOThDQUFGS0FBVjJZV3gxWlhoeUFCQnFZWFpoTG14aGJtY3VUblZ0WW1WeWhxeVZIUXVVNElzQ0FBQjRjUC8vLy8vLy8vLy9jM0lBRVdwaGRtRXViR0Z1Wnk1SmJuUmxaMlZ5RXVLZ3BQZUJoemdDQUFGSkFBVjJZV3gxWlhoeEFINEFFQUFBQUFOemNRQitBQXNBY1FCK0FCTndjM0lBREdwaGRtRXVibVYwTGxWU1RKWWxOellhL09SeUF3QUhTUUFJYUdGemFFTnZaR1ZKQUFSd2IzSjBUQUFKWVhWMGFHOXlhWFI1Y1FCK0FBVk1BQVJtYVd4bGNRQitBQVZNQUFSb2IzTjBjUUIrQUFWTUFBaHdjbTkwYjJOdmJIRUFmZ0FGVEFBRGNtVm1jUUIrQUFWNGNQLy8vLzhBQUIrUWRBQU9iRzlqWVd4b2IzTjBPamd3T0RCMEFDc3ZhM0l0WkdWMkwzSmxiVzkwYVc1bkwwOVRRMkZqYUdWT2IzUnBabWxqWVhScGIyNVRaWEoyYVdObGRBQUpiRzlqWVd4b2IzTjBkQUFFYUhSMGNIQjRkQUFBZEFBR1ZGSkJWa1ZNZEFBQkwzTnlBQk5xWVhaaExuVjBhV3d1UVhKeVlYbE1hWE4wZUlIU0habkhZWjBEQUFGSkFBUnphWHBsZUhBQUFBQUZkd1FBQUFBS2RBQXpi';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 453    FOR UPDATE;        
    buffer := 'M0puTG10MVlXeHBMbkpwWTJVdWEzTmlMbTFsYzNOaFoybHVaeTV6WlhKMmFXTmxMa3RUUWtwaGRtRlRaWEoyYVdObGRBQThZMjl0TG05d1pXNXplVzF3YUc5dWVTNXZjMk5oWTJobExtSmhjMlV1WlhabGJuUnpMa05oWTJobFJXNTBjbmxGZG1WdWRFeHBjM1JsYm1WeWRBQTNZMjl0TG05d1pXNXplVzF3YUc5dWVTNXZjMk5oWTJobExtSmhjMlV1WlhabGJuUnpMa05oWTJobFJYWmxiblJNYVhOMFpXNWxjblFBRjJwaGRtRXVkWFJwYkM1RmRtVnVkRXhwYzNSbGJtVnlkQUFzWTI5dExtOXdaVzV6ZVcxd2FHOXVlUzV2YzJOaFkyaGxMbUpoYzJVdVRHbG1aV041WTJ4bFFYZGhjbVY0dAANMTI5LjE4Ni43OS40NXNyADJvcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLkphdmFTZXJ2aWNlRGVmaW5pdGlvbrbnDV9BNGzxAgABTAARc2VydmljZUludGVyZmFjZXN0ABBMamF2YS91dGlsL0xpc3Q7eHIALm9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuU2VydmljZURlZmluaXRpb24AmwJPWN0pfgIADUwAC2J1c1NlY3VyaXR5cQB+ABNMAA9jcmVkZW50aWFsc1R5cGV0AExMb3JnL2t1YWxpL3JpY2UvY29yZS9zZWN1cml0eS9jcmVkZW50aWFscy9DcmVkZW50aWFsc1NvdXJjZSRDcmVkZW50aWFsc1R5cGU7TAAQbG9jYWxTZXJ2aWNlTmFtZXEAfgAETAAXbWVzc2FnZUV4Y2VwdGlvbkhhbmRsZXJxAH4ABEwADG1pbGxpc1RvTGl2ZXEAfgAVTAAIcHJpb3JpdHlxAH4AFEwABXF1ZXVlcQB+ABNMAA1yZXRyeUF0dGVtcHRzcQB+ABRMAAdzZXJ2aWNldAASTGphdmEvbGFuZy9PYmplY3Q7TAAPc2VydmljZUVuZFBvaW50dAAOTGphdmEvbmV0L1VSTDtMABNzZXJ2aWNlTmFtZVNwYWNlVVJJcQB+AARMABBzZXJ2aWNlTmFtZXNwYWNlcQB+AARMAAtzZXJ2aWNlUGF0aHEAfgAEeHBzcQB+ABkBcHQAGk9TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNldABNb3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5leGNlcHRpb25oYW5kbGluZy5EZWZhdWx0TWVzc2FnZUV4Y2VwdGlvbkhhbmRsZXJzcQB+AB///////////3NxAH4AHAAAAANzcQB+ABkAcQB+ADJwc3IADGphdmEubmV0LlVSTJYlNzYa/ORyAwAHSQAIaGFzaENvZGVJAARwb3J0TAAJYXV0aG9yaXR5cQB+AARMAARmaWxlcQB+AARMAARob3N0cQB+AARMAAhwcm90b2NvbHEAfgAETAADcmVmcQB+AAR4cGkboqAAAB+QdAAObG9jYWxob3N0OjgwODB0ACsva3ItZGV2L3JlbW90aW5nL09TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNldAAJbG9jYWxob3N0dAAEaHR0cHB4dAAAdAAGVFJBVkVMdAABL3NyABNqYXZhLnV0aWwuQXJyYXlMaXN0eIHSHZnHYZ0DAAFJAARzaXpleHAAAAAFdwQAAAAKdAAzb3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5zZXJ2aWNlLktTQkphdmFTZXJ2aWNldAA8Y29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLmJhc2UuZXZlbnRzLkNhY2hlRW50cnlFdmVudExpc3RlbmVydAA3Y29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLmJhc2UuZXZlbnRzLkNhY2hlRXZlbnRMaXN0ZW5lcnQAF2phdmEudXRpbC5FdmVudExpc3RlbmVydAAsY29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLmJhc2UuTGlmZWN5Y2xlQXdhcmV4cQB+ACN0AAZUUkFWRUw=';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
INSERT INTO KRSB_MSG_PYLD_T (MSG_PYLD,MSG_QUE_ID)
  VALUES (EMPTY_CLOB(),454)
/
-- Length: 6104
-- Chunks: 2
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 454    FOR UPDATE;        
    buffer := 'rO0ABXNyAC1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLkFzeW5jaHJvbm91c0NhbGzxnQ5Y0t0lZQIAB1oAFWlnbm9yZVN0b3JlQW5kRm9yd2FyZFsACWFyZ3VtZW50c3QAE1tMamF2YS9sYW5nL09iamVjdDtMAAhjYWxsYmFja3QAM0xvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL0FzeW5jaHJvbm91c0NhbGxiYWNrO0wAB2NvbnRleHR0ABZMamF2YS9pby9TZXJpYWxpemFibGU7TAAKbWV0aG9kTmFtZXQAEkxqYXZhL2xhbmcvU3RyaW5nO1sACnBhcmFtVHlwZXN0ABJbTGphdmEvbGFuZy9DbGFzcztMAAtzZXJ2aWNlSW5mb3QAKkxvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL1NlcnZpY2VJbmZvO3hwAHVyABNbTGphdmEubGFuZy5PYmplY3Q7kM5YnxBzKWwCAAB4cAAAAAFzcgBDY29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLnBsdWdpbnMuY2x1c3RlcnN1cHBvcnQuQ2x1c3Rlck5vdGlmaWNhdGlvbhY+6VckvG9pAgACSQAEdHlwZUwABGRhdGFxAH4AA3hwAAAAAnQAMERvY3VtZW50VHlwZVJ1bGVDYWNoZTpDb3VudHJ5TWFpbnRlbmFuY2VEb2N1bWVudHBwdAAGaW52b2tldXIAEltMamF2YS5sYW5nLkNsYXNzO6sW167LzVqZAgAAeHAAAAABdnIAFGphdmEuaW8uU2VyaWFsaXphYmxlEJthDdebZO0CAAB4cHNyAChvcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLlNlcnZpY2VJbmZvxRcjtikEt7oCAAtMAAVhbGl2ZXQAE0xqYXZhL2xhbmcvQm9vbGVhbjtMABRlbmRwb2ludEFsdGVybmF0ZVVybHEAfgAETAALZW5kcG9pbnRVcmxxAH4ABEwACmxvY2tWZXJOYnJ0ABNMamF2YS9sYW5nL0ludGVnZXI7TAAObWVzc2FnZUVudHJ5SWR0ABBMamF2YS9sYW5nL0xvbmc7TAAFcW5hbWV0ABtMamF2YXgveG1sL25hbWVzcGFjZS9RTmFtZTtMABpzZXJpYWxpemVkU2VydmljZU5hbWVzcGFjZXEAfgAETAAIc2VydmVySXBxAH4ABEwAEXNlcnZpY2VEZWZpbml0aW9udAAwTG9yZy9rdWFsaS9yaWNlL2tzYi9tZXNzYWdpbmcvU2VydmljZURlZmluaXRpb247TAALc2VydmljZU5hbWVxAH4ABEwAEHNlcnZpY2VOYW1lc3BhY2VxAH4ABHhwc3IAEWphdmEubGFuZy5Cb29sZWFuzSBygNWc+u4CAAFaAAV2YWx1ZXhwAXB0AEBodHRwOi8vbG9jYWxob3N0OjgwODAva3ItZGV2L3JlbW90aW5nL09TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNlc3IAEWphdmEubGFuZy5JbnRlZ2VyEuKgpPeBhzgCAAFJAAV2YWx1ZXhyABBqYXZhLmxhbmcuTnVtYmVyhqyVHQuU4IsCAAB4cAAAAAFzcgAOamF2YS5sYW5nLkxvbmc7i+SQzI8j3wIAAUoABXZhbHVleHEAfgAdAAAAAAAAD31zcgAZamF2YXgueG1sLm5hbWVzcGFjZS5RTmFtZYFtqC38O91sAgADTAAJbG9jYWxQYXJ0cQB+AARMAAxuYW1lc3BhY2VVUklxAH4ABEwABnByZWZpeHEAfgAEeHB0ABpPU0NhY2hlTm90aWZpY2F0aW9uU2VydmljZXQAAHEAfgAkdAd0ck8wQUJYTnlBREp2Y21jdWEzVmhiR2t1Y21salpTNXJjMkl1YldWemMyRm5hVzVuTGtwaGRtRlRaWEoyYVdObFJHVm1hVzVwZEdsdmJyYm5EVjlCTkd6eEFnQUJUQUFSYzJWeWRtbGpaVWx1ZEdWeVptRmpaWE4wQUJCTWFtRjJZUzkxZEdsc0wweHBjM1E3ZUhJQUxtOXlaeTVyZFdGc2FTNXlhV05sTG10ellpNXRaWE56WVdkcGJtY3VVMlZ5ZG1salpVUmxabWx1YVhScGIyNEFtd0pQV04wcGZnSUFEVXdBQzJKMWMxTmxZM1Z5YVhSNWRBQVRUR3BoZG1FdmJHRnVaeTlDYjI5c1pXRnVPMHdBRDJOeVpXUmxiblJwWVd4elZIbHdaWFFBVEV4dmNtY3ZhM1ZoYkdrdmNtbGpaUzlqYjNKbEwzTmxZM1Z5YVhSNUwyTnlaV1JsYm5ScFlXeHpMME55WldSbGJuUnBZV3h6VTI5MWNtTmxKRU55WldSbGJuUnBZV3h6Vkhsd1pUdE1BQkJzYjJOaGJGTmxjblpwWTJWT1lXMWxkQUFTVEdwaGRtRXZiR0Z1Wnk5VGRISnBibWM3VEFBWGJXVnpjMkZuWlVWNFkyVndkR2x2YmtoaGJtUnNaWEp4QUg0QUJVd0FERzFwYkd4cGMxUnZUR2wyWlhRQUVFeHFZWFpoTDJ4aGJtY3ZURzl1Wnp0TUFBaHdjbWx2Y21sMGVYUUFFMHhxWVhaaEwyeGhibWN2U1c1MFpXZGxjanRNQUFWeGRXVjFaWEVBZmdBRFRBQU5jbVYwY25sQmRIUmxiWEIwYzNFQWZnQUhUQUFIYzJWeWRtbGpaWFFBRWt4cVlYWmhMMnhoYm1jdlQySnFaV04wTzB3QUQzTmxjblpwWTJWRmJtUlFiMmx1ZEhRQURreHFZWFpoTDI1bGRDOVZVa3c3VEFBVGMyVnlkbWxqWlU1aGJXVlRjR0ZqWlZWU1NYRUFmZ0FGVEFBUWMyVnlkbWxqWlU1aGJXVnpjR0ZqWlhFQWZnQUZUQUFMYzJWeWRtbGpaVkJoZEdoeEFINEFCWGh3YzNJQUVXcGhkbUV1YkdGdVp5NUNiMjlzWldGdXpTQnlnTldjK3U0Q0FBRmFBQVYyWVd4MVpYaHdBWEIwQUJwUFUwTmhZMmhsVG05MGFXWnBZMkYwYVc5dVUyVnlkbWxqWlhRQVRXOXlaeTVyZFdGc2FTNXlhV05sTG10ellpNXRaWE56WVdkcGJtY3VaWGhqWlhCMGFXOXVhR0Z1Wkd4cGJtY3VSR1ZtWVhWc2RFMWxjM05oWjJWRmVHTmxjSFJwYjI1SVlXNWtiR1Z5YzNJQURtcGhkbUV1YkdGdVp5NU1iMjVuTzR2a2tNeVBJOThDQUFGS0FBVjJZV3gxWlhoeUFCQnFZWFpoTG14aGJtY3VUblZ0WW1WeWhxeVZIUXVVNElzQ0FBQjRjUC8vLy8vLy8vLy9jM0lBRVdwaGRtRXViR0Z1Wnk1SmJuUmxaMlZ5RXVLZ3BQZUJoemdDQUFGSkFBVjJZV3gxWlhoeEFINEFFQUFBQUFOemNRQitBQXNBY1FCK0FCTndjM0lBREdwaGRtRXVibVYwTGxWU1RKWWxOellhL09SeUF3QUhTUUFJYUdGemFFTnZaR1ZKQUFSd2IzSjBUQUFKWVhWMGFHOXlhWFI1Y1FCK0FBVk1BQVJtYVd4bGNRQitBQVZNQUFSb2IzTjBjUUIrQUFWTUFBaHdjbTkwYjJOdmJIRUFmZ0FGVEFBRGNtVm1jUUIrQUFWNGNQLy8vLzhBQUIrUWRBQU9iRzlqWVd4b2IzTjBPamd3T0RCMEFDc3ZhM0l0WkdWMkwzSmxiVzkwYVc1bkwwOVRRMkZqYUdWT2IzUnBabWxqWVhScGIyNVRaWEoyYVdObGRBQUpiRzlqWVd4b2IzTjBkQUFFYUhSMGNIQjRkQUFBZEFBR1ZGSkJWa1ZNZEFBQkwzTnlBQk5xWVhaaExuVjBhV3d1UVhKeVlYbE1hWE4wZUlIU0habkhZWjBEQUFGSkFBUnphWHBsZUhBQUFBQUZkd1FBQUFBS2RBQXpiM0pu';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 454    FOR UPDATE;        
    buffer := 'TG10MVlXeHBMbkpwWTJVdWEzTmlMbTFsYzNOaFoybHVaeTV6WlhKMmFXTmxMa3RUUWtwaGRtRlRaWEoyYVdObGRBQThZMjl0TG05d1pXNXplVzF3YUc5dWVTNXZjMk5oWTJobExtSmhjMlV1WlhabGJuUnpMa05oWTJobFJXNTBjbmxGZG1WdWRFeHBjM1JsYm1WeWRBQTNZMjl0TG05d1pXNXplVzF3YUc5dWVTNXZjMk5oWTJobExtSmhjMlV1WlhabGJuUnpMa05oWTJobFJYWmxiblJNYVhOMFpXNWxjblFBRjJwaGRtRXVkWFJwYkM1RmRtVnVkRXhwYzNSbGJtVnlkQUFzWTI5dExtOXdaVzV6ZVcxd2FHOXVlUzV2YzJOaFkyaGxMbUpoYzJVdVRHbG1aV041WTJ4bFFYZGhjbVY0dAANMTI5LjE4Ni43OS40NXNyADJvcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLkphdmFTZXJ2aWNlRGVmaW5pdGlvbrbnDV9BNGzxAgABTAARc2VydmljZUludGVyZmFjZXN0ABBMamF2YS91dGlsL0xpc3Q7eHIALm9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuU2VydmljZURlZmluaXRpb24AmwJPWN0pfgIADUwAC2J1c1NlY3VyaXR5cQB+ABNMAA9jcmVkZW50aWFsc1R5cGV0AExMb3JnL2t1YWxpL3JpY2UvY29yZS9zZWN1cml0eS9jcmVkZW50aWFscy9DcmVkZW50aWFsc1NvdXJjZSRDcmVkZW50aWFsc1R5cGU7TAAQbG9jYWxTZXJ2aWNlTmFtZXEAfgAETAAXbWVzc2FnZUV4Y2VwdGlvbkhhbmRsZXJxAH4ABEwADG1pbGxpc1RvTGl2ZXEAfgAVTAAIcHJpb3JpdHlxAH4AFEwABXF1ZXVlcQB+ABNMAA1yZXRyeUF0dGVtcHRzcQB+ABRMAAdzZXJ2aWNldAASTGphdmEvbGFuZy9PYmplY3Q7TAAPc2VydmljZUVuZFBvaW50dAAOTGphdmEvbmV0L1VSTDtMABNzZXJ2aWNlTmFtZVNwYWNlVVJJcQB+AARMABBzZXJ2aWNlTmFtZXNwYWNlcQB+AARMAAtzZXJ2aWNlUGF0aHEAfgAEeHBzcQB+ABkBcHQAGk9TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNldABNb3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5leGNlcHRpb25oYW5kbGluZy5EZWZhdWx0TWVzc2FnZUV4Y2VwdGlvbkhhbmRsZXJzcQB+AB///////////3NxAH4AHAAAAANzcQB+ABkAcQB+ADJwc3IADGphdmEubmV0LlVSTJYlNzYa/ORyAwAHSQAIaGFzaENvZGVJAARwb3J0TAAJYXV0aG9yaXR5cQB+AARMAARmaWxlcQB+AARMAARob3N0cQB+AARMAAhwcm90b2NvbHEAfgAETAADcmVmcQB+AAR4cGkboqAAAB+QdAAObG9jYWxob3N0OjgwODB0ACsva3ItZGV2L3JlbW90aW5nL09TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNldAAJbG9jYWxob3N0dAAEaHR0cHB4dAAAdAAGVFJBVkVMdAABL3NyABNqYXZhLnV0aWwuQXJyYXlMaXN0eIHSHZnHYZ0DAAFJAARzaXpleHAAAAAFdwQAAAAKdAAzb3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5zZXJ2aWNlLktTQkphdmFTZXJ2aWNldAA8Y29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLmJhc2UuZXZlbnRzLkNhY2hlRW50cnlFdmVudExpc3RlbmVydAA3Y29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLmJhc2UuZXZlbnRzLkNhY2hlRXZlbnRMaXN0ZW5lcnQAF2phdmEudXRpbC5FdmVudExpc3RlbmVydAAsY29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLmJhc2UuTGlmZWN5Y2xlQXdhcmV4cQB+ACN0AAZUUkFWRUw=';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
INSERT INTO KRSB_MSG_PYLD_T (MSG_PYLD,MSG_QUE_ID)
  VALUES (EMPTY_CLOB(),455)
/
-- Length: 6104
-- Chunks: 2
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 455    FOR UPDATE;        
    buffer := 'rO0ABXNyAC1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLkFzeW5jaHJvbm91c0NhbGzxnQ5Y0t0lZQIAB1oAFWlnbm9yZVN0b3JlQW5kRm9yd2FyZFsACWFyZ3VtZW50c3QAE1tMamF2YS9sYW5nL09iamVjdDtMAAhjYWxsYmFja3QAM0xvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL0FzeW5jaHJvbm91c0NhbGxiYWNrO0wAB2NvbnRleHR0ABZMamF2YS9pby9TZXJpYWxpemFibGU7TAAKbWV0aG9kTmFtZXQAEkxqYXZhL2xhbmcvU3RyaW5nO1sACnBhcmFtVHlwZXN0ABJbTGphdmEvbGFuZy9DbGFzcztMAAtzZXJ2aWNlSW5mb3QAKkxvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL1NlcnZpY2VJbmZvO3hwAHVyABNbTGphdmEubGFuZy5PYmplY3Q7kM5YnxBzKWwCAAB4cAAAAAFzcgBDY29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLnBsdWdpbnMuY2x1c3RlcnN1cHBvcnQuQ2x1c3Rlck5vdGlmaWNhdGlvbhY+6VckvG9pAgACSQAEdHlwZUwABGRhdGFxAH4AA3hwAAAAAnQAL0RvY3VtZW50VHlwZVJ1bGVDYWNoZTpDb3VudHlNYWludGVuYW5jZURvY3VtZW50cHB0AAZpbnZva2V1cgASW0xqYXZhLmxhbmcuQ2xhc3M7qxbXrsvNWpkCAAB4cAAAAAF2cgAUamF2YS5pby5TZXJpYWxpemFibGUQm2EN15tk7QIAAHhwc3IAKG9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuU2VydmljZUluZm/FFyO2KQS3ugIAC0wABWFsaXZldAATTGphdmEvbGFuZy9Cb29sZWFuO0wAFGVuZHBvaW50QWx0ZXJuYXRlVXJscQB+AARMAAtlbmRwb2ludFVybHEAfgAETAAKbG9ja1Zlck5icnQAE0xqYXZhL2xhbmcvSW50ZWdlcjtMAA5tZXNzYWdlRW50cnlJZHQAEExqYXZhL2xhbmcvTG9uZztMAAVxbmFtZXQAG0xqYXZheC94bWwvbmFtZXNwYWNlL1FOYW1lO0wAGnNlcmlhbGl6ZWRTZXJ2aWNlTmFtZXNwYWNlcQB+AARMAAhzZXJ2ZXJJcHEAfgAETAARc2VydmljZURlZmluaXRpb250ADBMb3JnL2t1YWxpL3JpY2Uva3NiL21lc3NhZ2luZy9TZXJ2aWNlRGVmaW5pdGlvbjtMAAtzZXJ2aWNlTmFtZXEAfgAETAAQc2VydmljZU5hbWVzcGFjZXEAfgAEeHBzcgARamF2YS5sYW5nLkJvb2xlYW7NIHKA1Zz67gIAAVoABXZhbHVleHABcHQAQGh0dHA6Ly9sb2NhbGhvc3Q6ODA4MC9rci1kZXYvcmVtb3RpbmcvT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2VzcgARamF2YS5sYW5nLkludGVnZXIS4qCk94GHOAIAAUkABXZhbHVleHIAEGphdmEubGFuZy5OdW1iZXKGrJUdC5TgiwIAAHhwAAAAAXNyAA5qYXZhLmxhbmcuTG9uZzuL5JDMjyPfAgABSgAFdmFsdWV4cQB+AB0AAAAAAAAPfXNyABlqYXZheC54bWwubmFtZXNwYWNlLlFOYW1lgW2oLfw73WwCAANMAAlsb2NhbFBhcnRxAH4ABEwADG5hbWVzcGFjZVVSSXEAfgAETAAGcHJlZml4cQB+AAR4cHQAGk9TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNldAAAcQB+ACR0B3RyTzBBQlhOeUFESnZjbWN1YTNWaGJHa3VjbWxqWlM1cmMySXViV1Z6YzJGbmFXNW5Ma3BoZG1GVFpYSjJhV05sUkdWbWFXNXBkR2x2YnJibkRWOUJOR3p4QWdBQlRBQVJjMlZ5ZG1salpVbHVkR1Z5Wm1GalpYTjBBQkJNYW1GMllTOTFkR2xzTDB4cGMzUTdlSElBTG05eVp5NXJkV0ZzYVM1eWFXTmxMbXR6WWk1dFpYTnpZV2RwYm1jdVUyVnlkbWxqWlVSbFptbHVhWFJwYjI0QW13SlBXTjBwZmdJQURVd0FDMkoxYzFObFkzVnlhWFI1ZEFBVFRHcGhkbUV2YkdGdVp5OUNiMjlzWldGdU8wd0FEMk55WldSbGJuUnBZV3h6Vkhsd1pYUUFURXh2Y21jdmEzVmhiR2t2Y21salpTOWpiM0psTDNObFkzVnlhWFI1TDJOeVpXUmxiblJwWVd4ekwwTnlaV1JsYm5ScFlXeHpVMjkxY21ObEpFTnlaV1JsYm5ScFlXeHpWSGx3WlR0TUFCQnNiMk5oYkZObGNuWnBZMlZPWVcxbGRBQVNUR3BoZG1FdmJHRnVaeTlUZEhKcGJtYzdUQUFYYldWemMyRm5aVVY0WTJWd2RHbHZia2hoYm1Sc1pYSnhBSDRBQlV3QURHMXBiR3hwYzFSdlRHbDJaWFFBRUV4cVlYWmhMMnhoYm1jdlRHOXVaenRNQUFod2NtbHZjbWwwZVhRQUUweHFZWFpoTDJ4aGJtY3ZTVzUwWldkbGNqdE1BQVZ4ZFdWMVpYRUFmZ0FEVEFBTmNtVjBjbmxCZEhSbGJYQjBjM0VBZmdBSFRBQUhjMlZ5ZG1salpYUUFFa3hxWVhaaEwyeGhibWN2VDJKcVpXTjBPMHdBRDNObGNuWnBZMlZGYm1SUWIybHVkSFFBRGt4cVlYWmhMMjVsZEM5VlVrdzdUQUFUYzJWeWRtbGpaVTVoYldWVGNHRmpaVlZTU1hFQWZnQUZUQUFRYzJWeWRtbGpaVTVoYldWemNHRmpaWEVBZmdBRlRBQUxjMlZ5ZG1salpWQmhkR2h4QUg0QUJYaHdjM0lBRVdwaGRtRXViR0Z1Wnk1Q2IyOXNaV0Z1elNCeWdOV2MrdTRDQUFGYUFBVjJZV3gxWlhod0FYQjBBQnBQVTBOaFkyaGxUbTkwYVdacFkyRjBhVzl1VTJWeWRtbGpaWFFBVFc5eVp5NXJkV0ZzYVM1eWFXTmxMbXR6WWk1dFpYTnpZV2RwYm1jdVpYaGpaWEIwYVc5dWFHRnVaR3hwYm1jdVJHVm1ZWFZzZEUxbGMzTmhaMlZGZUdObGNIUnBiMjVJWVc1a2JHVnljM0lBRG1waGRtRXViR0Z1Wnk1TWIyNW5PNHZra015UEk5OENBQUZLQUFWMllXeDFaWGh5QUJCcVlYWmhMbXhoYm1jdVRuVnRZbVZ5aHF5VkhRdVU0SXNDQUFCNGNQLy8vLy8vLy8vL2MzSUFFV3BoZG1FdWJHRnVaeTVKYm5SbFoyVnlFdUtncFBlQmh6Z0NBQUZKQUFWMllXeDFaWGh4QUg0QUVBQUFBQU56Y1FCK0FBc0FjUUIrQUJOd2MzSUFER3BoZG1FdWJtVjBMbFZTVEpZbE56WWEvT1J5QXdBSFNRQUlhR0Z6YUVOdlpHVkpBQVJ3YjNKMFRBQUpZWFYwYUc5eWFYUjVjUUIrQUFWTUFBUm1hV3hsY1FCK0FBVk1BQVJvYjNOMGNRQitBQVZNQUFod2NtOTBiMk52YkhFQWZnQUZUQUFEY21WbWNRQitBQVY0Y1AvLy8vOEFBQitRZEFBT2JHOWpZV3hvYjNOME9qZ3dPREIwQUNzdmEzSXRaR1YyTDNKbGJXOTBhVzVuTDA5VFEyRmphR1ZPYjNScFptbGpZWFJwYjI1VFpYSjJhV05sZEFBSmJHOWpZV3hvYjNOMGRBQUVhSFIwY0hCNGRBQUFkQUFHVkZKQlZrVk1kQUFCTDNOeUFCTnFZWFpoTG5WMGFXd3VRWEp5WVhsTWFYTjBlSUhTSFpuSFlaMERBQUZKQUFSemFYcGxlSEFBQUFBRmR3UUFBQUFLZEFBemIzSm5M';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 455    FOR UPDATE;        
    buffer := 'bXQxWVd4cExuSnBZMlV1YTNOaUxtMWxjM05oWjJsdVp5NXpaWEoyYVdObExrdFRRa3BoZG1GVFpYSjJhV05sZEFBOFkyOXRMbTl3Wlc1emVXMXdhRzl1ZVM1dmMyTmhZMmhsTG1KaGMyVXVaWFpsYm5SekxrTmhZMmhsUlc1MGNubEZkbVZ1ZEV4cGMzUmxibVZ5ZEFBM1kyOXRMbTl3Wlc1emVXMXdhRzl1ZVM1dmMyTmhZMmhsTG1KaGMyVXVaWFpsYm5SekxrTmhZMmhsUlhabGJuUk1hWE4wWlc1bGNuUUFGMnBoZG1FdWRYUnBiQzVGZG1WdWRFeHBjM1JsYm1WeWRBQXNZMjl0TG05d1pXNXplVzF3YUc5dWVTNXZjMk5oWTJobExtSmhjMlV1VEdsbVpXTjVZMnhsUVhkaGNtVjR0AA0xMjkuMTg2Ljc5LjQ1c3IAMm9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuSmF2YVNlcnZpY2VEZWZpbml0aW9utucNX0E0bPECAAFMABFzZXJ2aWNlSW50ZXJmYWNlc3QAEExqYXZhL3V0aWwvTGlzdDt4cgAub3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5TZXJ2aWNlRGVmaW5pdGlvbgCbAk9Y3Sl+AgANTAALYnVzU2VjdXJpdHlxAH4AE0wAD2NyZWRlbnRpYWxzVHlwZXQATExvcmcva3VhbGkvcmljZS9jb3JlL3NlY3VyaXR5L2NyZWRlbnRpYWxzL0NyZWRlbnRpYWxzU291cmNlJENyZWRlbnRpYWxzVHlwZTtMABBsb2NhbFNlcnZpY2VOYW1lcQB+AARMABdtZXNzYWdlRXhjZXB0aW9uSGFuZGxlcnEAfgAETAAMbWlsbGlzVG9MaXZlcQB+ABVMAAhwcmlvcml0eXEAfgAUTAAFcXVldWVxAH4AE0wADXJldHJ5QXR0ZW1wdHNxAH4AFEwAB3NlcnZpY2V0ABJMamF2YS9sYW5nL09iamVjdDtMAA9zZXJ2aWNlRW5kUG9pbnR0AA5MamF2YS9uZXQvVVJMO0wAE3NlcnZpY2VOYW1lU3BhY2VVUklxAH4ABEwAEHNlcnZpY2VOYW1lc3BhY2VxAH4ABEwAC3NlcnZpY2VQYXRocQB+AAR4cHNxAH4AGQFwdAAaT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2V0AE1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLmV4Y2VwdGlvbmhhbmRsaW5nLkRlZmF1bHRNZXNzYWdlRXhjZXB0aW9uSGFuZGxlcnNxAH4AH///////////c3EAfgAcAAAAA3NxAH4AGQBxAH4AMnBzcgAMamF2YS5uZXQuVVJMliU3Nhr85HIDAAdJAAhoYXNoQ29kZUkABHBvcnRMAAlhdXRob3JpdHlxAH4ABEwABGZpbGVxAH4ABEwABGhvc3RxAH4ABEwACHByb3RvY29scQB+AARMAANyZWZxAH4ABHhwaRuioAAAH5B0AA5sb2NhbGhvc3Q6ODA4MHQAKy9rci1kZXYvcmVtb3RpbmcvT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2V0AAlsb2NhbGhvc3R0AARodHRwcHh0AAB0AAZUUkFWRUx0AAEvc3IAE2phdmEudXRpbC5BcnJheUxpc3R4gdIdmcdhnQMAAUkABHNpemV4cAAAAAV3BAAAAAp0ADNvcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLnNlcnZpY2UuS1NCSmF2YVNlcnZpY2V0ADxjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5ldmVudHMuQ2FjaGVFbnRyeUV2ZW50TGlzdGVuZXJ0ADdjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5ldmVudHMuQ2FjaGVFdmVudExpc3RlbmVydAAXamF2YS51dGlsLkV2ZW50TGlzdGVuZXJ0ACxjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5MaWZlY3ljbGVBd2FyZXhxAH4AI3QABlRSQVZFTA==';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
INSERT INTO KRSB_MSG_PYLD_T (MSG_PYLD,MSG_QUE_ID)
  VALUES (EMPTY_CLOB(),456)
/
-- Length: 6108
-- Chunks: 2
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 456    FOR UPDATE;        
    buffer := 'rO0ABXNyAC1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLkFzeW5jaHJvbm91c0NhbGzxnQ5Y0t0lZQIAB1oAFWlnbm9yZVN0b3JlQW5kRm9yd2FyZFsACWFyZ3VtZW50c3QAE1tMamF2YS9sYW5nL09iamVjdDtMAAhjYWxsYmFja3QAM0xvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL0FzeW5jaHJvbm91c0NhbGxiYWNrO0wAB2NvbnRleHR0ABZMamF2YS9pby9TZXJpYWxpemFibGU7TAAKbWV0aG9kTmFtZXQAEkxqYXZhL2xhbmcvU3RyaW5nO1sACnBhcmFtVHlwZXN0ABJbTGphdmEvbGFuZy9DbGFzcztMAAtzZXJ2aWNlSW5mb3QAKkxvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL1NlcnZpY2VJbmZvO3hwAHVyABNbTGphdmEubGFuZy5PYmplY3Q7kM5YnxBzKWwCAAB4cAAAAAFzcgBDY29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLnBsdWdpbnMuY2x1c3RlcnN1cHBvcnQuQ2x1c3Rlck5vdGlmaWNhdGlvbhY+6VckvG9pAgACSQAEdHlwZUwABGRhdGFxAH4AA3hwAAAAAnQAM0RvY3VtZW50VHlwZVJ1bGVDYWNoZTpQb3N0YWxDb2RlTWFpbnRlbmFuY2VEb2N1bWVudHBwdAAGaW52b2tldXIAEltMamF2YS5sYW5nLkNsYXNzO6sW167LzVqZAgAAeHAAAAABdnIAFGphdmEuaW8uU2VyaWFsaXphYmxlEJthDdebZO0CAAB4cHNyAChvcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLlNlcnZpY2VJbmZvxRcjtikEt7oCAAtMAAVhbGl2ZXQAE0xqYXZhL2xhbmcvQm9vbGVhbjtMABRlbmRwb2ludEFsdGVybmF0ZVVybHEAfgAETAALZW5kcG9pbnRVcmxxAH4ABEwACmxvY2tWZXJOYnJ0ABNMamF2YS9sYW5nL0ludGVnZXI7TAAObWVzc2FnZUVudHJ5SWR0ABBMamF2YS9sYW5nL0xvbmc7TAAFcW5hbWV0ABtMamF2YXgveG1sL25hbWVzcGFjZS9RTmFtZTtMABpzZXJpYWxpemVkU2VydmljZU5hbWVzcGFjZXEAfgAETAAIc2VydmVySXBxAH4ABEwAEXNlcnZpY2VEZWZpbml0aW9udAAwTG9yZy9rdWFsaS9yaWNlL2tzYi9tZXNzYWdpbmcvU2VydmljZURlZmluaXRpb247TAALc2VydmljZU5hbWVxAH4ABEwAEHNlcnZpY2VOYW1lc3BhY2VxAH4ABHhwc3IAEWphdmEubGFuZy5Cb29sZWFuzSBygNWc+u4CAAFaAAV2YWx1ZXhwAXB0AEBodHRwOi8vbG9jYWxob3N0OjgwODAva3ItZGV2L3JlbW90aW5nL09TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNlc3IAEWphdmEubGFuZy5JbnRlZ2VyEuKgpPeBhzgCAAFJAAV2YWx1ZXhyABBqYXZhLmxhbmcuTnVtYmVyhqyVHQuU4IsCAAB4cAAAAAFzcgAOamF2YS5sYW5nLkxvbmc7i+SQzI8j3wIAAUoABXZhbHVleHEAfgAdAAAAAAAAD31zcgAZamF2YXgueG1sLm5hbWVzcGFjZS5RTmFtZYFtqC38O91sAgADTAAJbG9jYWxQYXJ0cQB+AARMAAxuYW1lc3BhY2VVUklxAH4ABEwABnByZWZpeHEAfgAEeHB0ABpPU0NhY2hlTm90aWZpY2F0aW9uU2VydmljZXQAAHEAfgAkdAd0ck8wQUJYTnlBREp2Y21jdWEzVmhiR2t1Y21salpTNXJjMkl1YldWemMyRm5hVzVuTGtwaGRtRlRaWEoyYVdObFJHVm1hVzVwZEdsdmJyYm5EVjlCTkd6eEFnQUJUQUFSYzJWeWRtbGpaVWx1ZEdWeVptRmpaWE4wQUJCTWFtRjJZUzkxZEdsc0wweHBjM1E3ZUhJQUxtOXlaeTVyZFdGc2FTNXlhV05sTG10ellpNXRaWE56WVdkcGJtY3VVMlZ5ZG1salpVUmxabWx1YVhScGIyNEFtd0pQV04wcGZnSUFEVXdBQzJKMWMxTmxZM1Z5YVhSNWRBQVRUR3BoZG1FdmJHRnVaeTlDYjI5c1pXRnVPMHdBRDJOeVpXUmxiblJwWVd4elZIbHdaWFFBVEV4dmNtY3ZhM1ZoYkdrdmNtbGpaUzlqYjNKbEwzTmxZM1Z5YVhSNUwyTnlaV1JsYm5ScFlXeHpMME55WldSbGJuUnBZV3h6VTI5MWNtTmxKRU55WldSbGJuUnBZV3h6Vkhsd1pUdE1BQkJzYjJOaGJGTmxjblpwWTJWT1lXMWxkQUFTVEdwaGRtRXZiR0Z1Wnk5VGRISnBibWM3VEFBWGJXVnpjMkZuWlVWNFkyVndkR2x2YmtoaGJtUnNaWEp4QUg0QUJVd0FERzFwYkd4cGMxUnZUR2wyWlhRQUVFeHFZWFpoTDJ4aGJtY3ZURzl1Wnp0TUFBaHdjbWx2Y21sMGVYUUFFMHhxWVhaaEwyeGhibWN2U1c1MFpXZGxjanRNQUFWeGRXVjFaWEVBZmdBRFRBQU5jbVYwY25sQmRIUmxiWEIwYzNFQWZnQUhUQUFIYzJWeWRtbGpaWFFBRWt4cVlYWmhMMnhoYm1jdlQySnFaV04wTzB3QUQzTmxjblpwWTJWRmJtUlFiMmx1ZEhRQURreHFZWFpoTDI1bGRDOVZVa3c3VEFBVGMyVnlkbWxqWlU1aGJXVlRjR0ZqWlZWU1NYRUFmZ0FGVEFBUWMyVnlkbWxqWlU1aGJXVnpjR0ZqWlhFQWZnQUZUQUFMYzJWeWRtbGpaVkJoZEdoeEFINEFCWGh3YzNJQUVXcGhkbUV1YkdGdVp5NUNiMjlzWldGdXpTQnlnTldjK3U0Q0FBRmFBQVYyWVd4MVpYaHdBWEIwQUJwUFUwTmhZMmhsVG05MGFXWnBZMkYwYVc5dVUyVnlkbWxqWlhRQVRXOXlaeTVyZFdGc2FTNXlhV05sTG10ellpNXRaWE56WVdkcGJtY3VaWGhqWlhCMGFXOXVhR0Z1Wkd4cGJtY3VSR1ZtWVhWc2RFMWxjM05oWjJWRmVHTmxjSFJwYjI1SVlXNWtiR1Z5YzNJQURtcGhkbUV1YkdGdVp5NU1iMjVuTzR2a2tNeVBJOThDQUFGS0FBVjJZV3gxWlhoeUFCQnFZWFpoTG14aGJtY3VUblZ0WW1WeWhxeVZIUXVVNElzQ0FBQjRjUC8vLy8vLy8vLy9jM0lBRVdwaGRtRXViR0Z1Wnk1SmJuUmxaMlZ5RXVLZ3BQZUJoemdDQUFGSkFBVjJZV3gxWlhoeEFINEFFQUFBQUFOemNRQitBQXNBY1FCK0FCTndjM0lBREdwaGRtRXVibVYwTGxWU1RKWWxOellhL09SeUF3QUhTUUFJYUdGemFFTnZaR1ZKQUFSd2IzSjBUQUFKWVhWMGFHOXlhWFI1Y1FCK0FBVk1BQVJtYVd4bGNRQitBQVZNQUFSb2IzTjBjUUIrQUFWTUFBaHdjbTkwYjJOdmJIRUFmZ0FGVEFBRGNtVm1jUUIrQUFWNGNQLy8vLzhBQUIrUWRBQU9iRzlqWVd4b2IzTjBPamd3T0RCMEFDc3ZhM0l0WkdWMkwzSmxiVzkwYVc1bkwwOVRRMkZqYUdWT2IzUnBabWxqWVhScGIyNVRaWEoyYVdObGRBQUpiRzlqWVd4b2IzTjBkQUFFYUhSMGNIQjRkQUFBZEFBR1ZGSkJWa1ZNZEFBQkwzTnlBQk5xWVhaaExuVjBhV3d1UVhKeVlYbE1hWE4wZUlIU0habkhZWjBEQUFGSkFBUnphWHBsZUhBQUFBQUZkd1FBQUFBS2RBQXpi';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 456    FOR UPDATE;        
    buffer := 'M0puTG10MVlXeHBMbkpwWTJVdWEzTmlMbTFsYzNOaFoybHVaeTV6WlhKMmFXTmxMa3RUUWtwaGRtRlRaWEoyYVdObGRBQThZMjl0TG05d1pXNXplVzF3YUc5dWVTNXZjMk5oWTJobExtSmhjMlV1WlhabGJuUnpMa05oWTJobFJXNTBjbmxGZG1WdWRFeHBjM1JsYm1WeWRBQTNZMjl0TG05d1pXNXplVzF3YUc5dWVTNXZjMk5oWTJobExtSmhjMlV1WlhabGJuUnpMa05oWTJobFJYWmxiblJNYVhOMFpXNWxjblFBRjJwaGRtRXVkWFJwYkM1RmRtVnVkRXhwYzNSbGJtVnlkQUFzWTI5dExtOXdaVzV6ZVcxd2FHOXVlUzV2YzJOaFkyaGxMbUpoYzJVdVRHbG1aV041WTJ4bFFYZGhjbVY0dAANMTI5LjE4Ni43OS40NXNyADJvcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLkphdmFTZXJ2aWNlRGVmaW5pdGlvbrbnDV9BNGzxAgABTAARc2VydmljZUludGVyZmFjZXN0ABBMamF2YS91dGlsL0xpc3Q7eHIALm9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuU2VydmljZURlZmluaXRpb24AmwJPWN0pfgIADUwAC2J1c1NlY3VyaXR5cQB+ABNMAA9jcmVkZW50aWFsc1R5cGV0AExMb3JnL2t1YWxpL3JpY2UvY29yZS9zZWN1cml0eS9jcmVkZW50aWFscy9DcmVkZW50aWFsc1NvdXJjZSRDcmVkZW50aWFsc1R5cGU7TAAQbG9jYWxTZXJ2aWNlTmFtZXEAfgAETAAXbWVzc2FnZUV4Y2VwdGlvbkhhbmRsZXJxAH4ABEwADG1pbGxpc1RvTGl2ZXEAfgAVTAAIcHJpb3JpdHlxAH4AFEwABXF1ZXVlcQB+ABNMAA1yZXRyeUF0dGVtcHRzcQB+ABRMAAdzZXJ2aWNldAASTGphdmEvbGFuZy9PYmplY3Q7TAAPc2VydmljZUVuZFBvaW50dAAOTGphdmEvbmV0L1VSTDtMABNzZXJ2aWNlTmFtZVNwYWNlVVJJcQB+AARMABBzZXJ2aWNlTmFtZXNwYWNlcQB+AARMAAtzZXJ2aWNlUGF0aHEAfgAEeHBzcQB+ABkBcHQAGk9TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNldABNb3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5leGNlcHRpb25oYW5kbGluZy5EZWZhdWx0TWVzc2FnZUV4Y2VwdGlvbkhhbmRsZXJzcQB+AB///////////3NxAH4AHAAAAANzcQB+ABkAcQB+ADJwc3IADGphdmEubmV0LlVSTJYlNzYa/ORyAwAHSQAIaGFzaENvZGVJAARwb3J0TAAJYXV0aG9yaXR5cQB+AARMAARmaWxlcQB+AARMAARob3N0cQB+AARMAAhwcm90b2NvbHEAfgAETAADcmVmcQB+AAR4cGkboqAAAB+QdAAObG9jYWxob3N0OjgwODB0ACsva3ItZGV2L3JlbW90aW5nL09TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNldAAJbG9jYWxob3N0dAAEaHR0cHB4dAAAdAAGVFJBVkVMdAABL3NyABNqYXZhLnV0aWwuQXJyYXlMaXN0eIHSHZnHYZ0DAAFJAARzaXpleHAAAAAFdwQAAAAKdAAzb3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5zZXJ2aWNlLktTQkphdmFTZXJ2aWNldAA8Y29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLmJhc2UuZXZlbnRzLkNhY2hlRW50cnlFdmVudExpc3RlbmVydAA3Y29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLmJhc2UuZXZlbnRzLkNhY2hlRXZlbnRMaXN0ZW5lcnQAF2phdmEudXRpbC5FdmVudExpc3RlbmVydAAsY29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLmJhc2UuTGlmZWN5Y2xlQXdhcmV4cQB+ACN0AAZUUkFWRUw=';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
INSERT INTO KRSB_MSG_PYLD_T (MSG_PYLD,MSG_QUE_ID)
  VALUES (EMPTY_CLOB(),457)
/
-- Length: 6100
-- Chunks: 2
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 457    FOR UPDATE;        
    buffer := 'rO0ABXNyAC1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLkFzeW5jaHJvbm91c0NhbGzxnQ5Y0t0lZQIAB1oAFWlnbm9yZVN0b3JlQW5kRm9yd2FyZFsACWFyZ3VtZW50c3QAE1tMamF2YS9sYW5nL09iamVjdDtMAAhjYWxsYmFja3QAM0xvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL0FzeW5jaHJvbm91c0NhbGxiYWNrO0wAB2NvbnRleHR0ABZMamF2YS9pby9TZXJpYWxpemFibGU7TAAKbWV0aG9kTmFtZXQAEkxqYXZhL2xhbmcvU3RyaW5nO1sACnBhcmFtVHlwZXN0ABJbTGphdmEvbGFuZy9DbGFzcztMAAtzZXJ2aWNlSW5mb3QAKkxvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL1NlcnZpY2VJbmZvO3hwAHVyABNbTGphdmEubGFuZy5PYmplY3Q7kM5YnxBzKWwCAAB4cAAAAAFzcgBDY29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLnBsdWdpbnMuY2x1c3RlcnN1cHBvcnQuQ2x1c3Rlck5vdGlmaWNhdGlvbhY+6VckvG9pAgACSQAEdHlwZUwABGRhdGFxAH4AA3hwAAAAAnQALkRvY3VtZW50VHlwZVJ1bGVDYWNoZTpTdGF0ZU1haW50ZW5hbmNlRG9jdW1lbnRwcHQABmludm9rZXVyABJbTGphdmEubGFuZy5DbGFzczurFteuy81amQIAAHhwAAAAAXZyABRqYXZhLmlvLlNlcmlhbGl6YWJsZRCbYQ3Xm2TtAgAAeHBzcgAob3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5TZXJ2aWNlSW5mb8UXI7YpBLe6AgALTAAFYWxpdmV0ABNMamF2YS9sYW5nL0Jvb2xlYW47TAAUZW5kcG9pbnRBbHRlcm5hdGVVcmxxAH4ABEwAC2VuZHBvaW50VXJscQB+AARMAApsb2NrVmVyTmJydAATTGphdmEvbGFuZy9JbnRlZ2VyO0wADm1lc3NhZ2VFbnRyeUlkdAAQTGphdmEvbGFuZy9Mb25nO0wABXFuYW1ldAAbTGphdmF4L3htbC9uYW1lc3BhY2UvUU5hbWU7TAAac2VyaWFsaXplZFNlcnZpY2VOYW1lc3BhY2VxAH4ABEwACHNlcnZlcklwcQB+AARMABFzZXJ2aWNlRGVmaW5pdGlvbnQAMExvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL1NlcnZpY2VEZWZpbml0aW9uO0wAC3NlcnZpY2VOYW1lcQB+AARMABBzZXJ2aWNlTmFtZXNwYWNlcQB+AAR4cHNyABFqYXZhLmxhbmcuQm9vbGVhbs0gcoDVnPruAgABWgAFdmFsdWV4cAFwdABAaHR0cDovL2xvY2FsaG9zdDo4MDgwL2tyLWRldi9yZW1vdGluZy9PU0NhY2hlTm90aWZpY2F0aW9uU2VydmljZXNyABFqYXZhLmxhbmcuSW50ZWdlchLioKT3gYc4AgABSQAFdmFsdWV4cgAQamF2YS5sYW5nLk51bWJlcoaslR0LlOCLAgAAeHAAAAABc3IADmphdmEubGFuZy5Mb25nO4vkkMyPI98CAAFKAAV2YWx1ZXhxAH4AHQAAAAAAAA99c3IAGWphdmF4LnhtbC5uYW1lc3BhY2UuUU5hbWWBbagt/DvdbAIAA0wACWxvY2FsUGFydHEAfgAETAAMbmFtZXNwYWNlVVJJcQB+AARMAAZwcmVmaXhxAH4ABHhwdAAaT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2V0AABxAH4AJHQHdHJPMEFCWE55QURKdmNtY3VhM1ZoYkdrdWNtbGpaUzVyYzJJdWJXVnpjMkZuYVc1bkxrcGhkbUZUWlhKMmFXTmxSR1ZtYVc1cGRHbHZicmJuRFY5Qk5HenhBZ0FCVEFBUmMyVnlkbWxqWlVsdWRHVnlabUZqWlhOMEFCQk1hbUYyWVM5MWRHbHNMMHhwYzNRN2VISUFMbTl5Wnk1cmRXRnNhUzV5YVdObExtdHpZaTV0WlhOellXZHBibWN1VTJWeWRtbGpaVVJsWm1sdWFYUnBiMjRBbXdKUFdOMHBmZ0lBRFV3QUMySjFjMU5sWTNWeWFYUjVkQUFUVEdwaGRtRXZiR0Z1Wnk5Q2IyOXNaV0Z1TzB3QUQyTnlaV1JsYm5ScFlXeHpWSGx3WlhRQVRFeHZjbWN2YTNWaGJHa3ZjbWxqWlM5amIzSmxMM05sWTNWeWFYUjVMMk55WldSbGJuUnBZV3h6TDBOeVpXUmxiblJwWVd4elUyOTFjbU5sSkVOeVpXUmxiblJwWVd4elZIbHdaVHRNQUJCc2IyTmhiRk5sY25acFkyVk9ZVzFsZEFBU1RHcGhkbUV2YkdGdVp5OVRkSEpwYm1jN1RBQVhiV1Z6YzJGblpVVjRZMlZ3ZEdsdmJraGhibVJzWlhKeEFINEFCVXdBREcxcGJHeHBjMVJ2VEdsMlpYUUFFRXhxWVhaaEwyeGhibWN2VEc5dVp6dE1BQWh3Y21sdmNtbDBlWFFBRTB4cVlYWmhMMnhoYm1jdlNXNTBaV2RsY2p0TUFBVnhkV1YxWlhFQWZnQURUQUFOY21WMGNubEJkSFJsYlhCMGMzRUFmZ0FIVEFBSGMyVnlkbWxqWlhRQUVreHFZWFpoTDJ4aGJtY3ZUMkpxWldOME8wd0FEM05sY25acFkyVkZibVJRYjJsdWRIUUFEa3hxWVhaaEwyNWxkQzlWVWt3N1RBQVRjMlZ5ZG1salpVNWhiV1ZUY0dGalpWVlNTWEVBZmdBRlRBQVFjMlZ5ZG1salpVNWhiV1Z6Y0dGalpYRUFmZ0FGVEFBTGMyVnlkbWxqWlZCaGRHaHhBSDRBQlhod2MzSUFFV3BoZG1FdWJHRnVaeTVDYjI5c1pXRnV6U0J5Z05XYyt1NENBQUZhQUFWMllXeDFaWGh3QVhCMEFCcFBVME5oWTJobFRtOTBhV1pwWTJGMGFXOXVVMlZ5ZG1salpYUUFUVzl5Wnk1cmRXRnNhUzV5YVdObExtdHpZaTV0WlhOellXZHBibWN1WlhoalpYQjBhVzl1YUdGdVpHeHBibWN1UkdWbVlYVnNkRTFsYzNOaFoyVkZlR05sY0hScGIyNUlZVzVrYkdWeWMzSUFEbXBoZG1FdWJHRnVaeTVNYjI1bk80dmtrTXlQSTk4Q0FBRktBQVYyWVd4MVpYaHlBQkJxWVhaaExteGhibWN1VG5WdFltVnlocXlWSFF1VTRJc0NBQUI0Y1AvLy8vLy8vLy8vYzNJQUVXcGhkbUV1YkdGdVp5NUpiblJsWjJWeUV1S2dwUGVCaHpnQ0FBRkpBQVYyWVd4MVpYaHhBSDRBRUFBQUFBTnpjUUIrQUFzQWNRQitBQk53YzNJQURHcGhkbUV1Ym1WMExsVlNUSllsTnpZYS9PUnlBd0FIU1FBSWFHRnphRU52WkdWSkFBUndiM0owVEFBSllYVjBhRzl5YVhSNWNRQitBQVZNQUFSbWFXeGxjUUIrQUFWTUFBUm9iM04wY1FCK0FBVk1BQWh3Y205MGIyTnZiSEVBZmdBRlRBQURjbVZtY1FCK0FBVjRjUC8vLy84QUFCK1FkQUFPYkc5allXeG9iM04wT2pnd09EQjBBQ3N2YTNJdFpHVjJMM0psYlc5MGFXNW5MMDlUUTJGamFHVk9iM1JwWm1sallYUnBiMjVUWlhKMmFXTmxkQUFKYkc5allXeG9iM04wZEFBRWFIUjBjSEI0ZEFBQWRBQUdWRkpCVmtWTWRBQUJMM055QUJOcVlYWmhMblYwYVd3dVFYSnlZWGxNYVhOMGVJSFNIWm5IWVowREFBRkpBQVJ6YVhwbGVIQUFBQUFGZHdRQUFBQUtkQUF6YjNKbkxt';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 457    FOR UPDATE;        
    buffer := 'dDFZV3hwTG5KcFkyVXVhM05pTG0xbGMzTmhaMmx1Wnk1elpYSjJhV05sTGt0VFFrcGhkbUZUWlhKMmFXTmxkQUE4WTI5dExtOXdaVzV6ZVcxd2FHOXVlUzV2YzJOaFkyaGxMbUpoYzJVdVpYWmxiblJ6TGtOaFkyaGxSVzUwY25sRmRtVnVkRXhwYzNSbGJtVnlkQUEzWTI5dExtOXdaVzV6ZVcxd2FHOXVlUzV2YzJOaFkyaGxMbUpoYzJVdVpYWmxiblJ6TGtOaFkyaGxSWFpsYm5STWFYTjBaVzVsY25RQUYycGhkbUV1ZFhScGJDNUZkbVZ1ZEV4cGMzUmxibVZ5ZEFBc1kyOXRMbTl3Wlc1emVXMXdhRzl1ZVM1dmMyTmhZMmhsTG1KaGMyVXVUR2xtWldONVkyeGxRWGRoY21WNHQADTEyOS4xODYuNzkuNDVzcgAyb3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5KYXZhU2VydmljZURlZmluaXRpb2625w1fQTRs8QIAAUwAEXNlcnZpY2VJbnRlcmZhY2VzdAAQTGphdmEvdXRpbC9MaXN0O3hyAC5vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLlNlcnZpY2VEZWZpbml0aW9uAJsCT1jdKX4CAA1MAAtidXNTZWN1cml0eXEAfgATTAAPY3JlZGVudGlhbHNUeXBldABMTG9yZy9rdWFsaS9yaWNlL2NvcmUvc2VjdXJpdHkvY3JlZGVudGlhbHMvQ3JlZGVudGlhbHNTb3VyY2UkQ3JlZGVudGlhbHNUeXBlO0wAEGxvY2FsU2VydmljZU5hbWVxAH4ABEwAF21lc3NhZ2VFeGNlcHRpb25IYW5kbGVycQB+AARMAAxtaWxsaXNUb0xpdmVxAH4AFUwACHByaW9yaXR5cQB+ABRMAAVxdWV1ZXEAfgATTAANcmV0cnlBdHRlbXB0c3EAfgAUTAAHc2VydmljZXQAEkxqYXZhL2xhbmcvT2JqZWN0O0wAD3NlcnZpY2VFbmRQb2ludHQADkxqYXZhL25ldC9VUkw7TAATc2VydmljZU5hbWVTcGFjZVVSSXEAfgAETAAQc2VydmljZU5hbWVzcGFjZXEAfgAETAALc2VydmljZVBhdGhxAH4ABHhwc3EAfgAZAXB0ABpPU0NhY2hlTm90aWZpY2F0aW9uU2VydmljZXQATW9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuZXhjZXB0aW9uaGFuZGxpbmcuRGVmYXVsdE1lc3NhZ2VFeGNlcHRpb25IYW5kbGVyc3EAfgAf//////////9zcQB+ABwAAAADc3EAfgAZAHEAfgAycHNyAAxqYXZhLm5ldC5VUkyWJTc2GvzkcgMAB0kACGhhc2hDb2RlSQAEcG9ydEwACWF1dGhvcml0eXEAfgAETAAEZmlsZXEAfgAETAAEaG9zdHEAfgAETAAIcHJvdG9jb2xxAH4ABEwAA3JlZnEAfgAEeHBpG6KgAAAfkHQADmxvY2FsaG9zdDo4MDgwdAArL2tyLWRldi9yZW1vdGluZy9PU0NhY2hlTm90aWZpY2F0aW9uU2VydmljZXQACWxvY2FsaG9zdHQABGh0dHBweHQAAHQABlRSQVZFTHQAAS9zcgATamF2YS51dGlsLkFycmF5TGlzdHiB0h2Zx2GdAwABSQAEc2l6ZXhwAAAABXcEAAAACnQAM29yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuc2VydmljZS5LU0JKYXZhU2VydmljZXQAPGNvbS5vcGVuc3ltcGhvbnkub3NjYWNoZS5iYXNlLmV2ZW50cy5DYWNoZUVudHJ5RXZlbnRMaXN0ZW5lcnQAN2NvbS5vcGVuc3ltcGhvbnkub3NjYWNoZS5iYXNlLmV2ZW50cy5DYWNoZUV2ZW50TGlzdGVuZXJ0ABdqYXZhLnV0aWwuRXZlbnRMaXN0ZW5lcnQALGNvbS5vcGVuc3ltcGhvbnkub3NjYWNoZS5iYXNlLkxpZmVjeWNsZUF3YXJleHEAfgAjdAAGVFJBVkVM';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
INSERT INTO KRSB_MSG_PYLD_T (MSG_PYLD,MSG_QUE_ID)
  VALUES (EMPTY_CLOB(),458)
/
-- Length: 6104
-- Chunks: 2
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 458    FOR UPDATE;        
    buffer := 'rO0ABXNyAC1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLkFzeW5jaHJvbm91c0NhbGzxnQ5Y0t0lZQIAB1oAFWlnbm9yZVN0b3JlQW5kRm9yd2FyZFsACWFyZ3VtZW50c3QAE1tMamF2YS9sYW5nL09iamVjdDtMAAhjYWxsYmFja3QAM0xvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL0FzeW5jaHJvbm91c0NhbGxiYWNrO0wAB2NvbnRleHR0ABZMamF2YS9pby9TZXJpYWxpemFibGU7TAAKbWV0aG9kTmFtZXQAEkxqYXZhL2xhbmcvU3RyaW5nO1sACnBhcmFtVHlwZXN0ABJbTGphdmEvbGFuZy9DbGFzcztMAAtzZXJ2aWNlSW5mb3QAKkxvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL1NlcnZpY2VJbmZvO3hwAHVyABNbTGphdmEubGFuZy5PYmplY3Q7kM5YnxBzKWwCAAB4cAAAAAFzcgBDY29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLnBsdWdpbnMuY2x1c3RlcnN1cHBvcnQuQ2x1c3Rlck5vdGlmaWNhdGlvbhY+6VckvG9pAgACSQAEdHlwZUwABGRhdGFxAH4AA3hwAAAAAnQAMERvY3VtZW50VHlwZVJ1bGVDYWNoZTpJZGVudGl0eU1hbmFnZW1lbnREb2N1bWVudHBwdAAGaW52b2tldXIAEltMamF2YS5sYW5nLkNsYXNzO6sW167LzVqZAgAAeHAAAAABdnIAFGphdmEuaW8uU2VyaWFsaXphYmxlEJthDdebZO0CAAB4cHNyAChvcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLlNlcnZpY2VJbmZvxRcjtikEt7oCAAtMAAVhbGl2ZXQAE0xqYXZhL2xhbmcvQm9vbGVhbjtMABRlbmRwb2ludEFsdGVybmF0ZVVybHEAfgAETAALZW5kcG9pbnRVcmxxAH4ABEwACmxvY2tWZXJOYnJ0ABNMamF2YS9sYW5nL0ludGVnZXI7TAAObWVzc2FnZUVudHJ5SWR0ABBMamF2YS9sYW5nL0xvbmc7TAAFcW5hbWV0ABtMamF2YXgveG1sL25hbWVzcGFjZS9RTmFtZTtMABpzZXJpYWxpemVkU2VydmljZU5hbWVzcGFjZXEAfgAETAAIc2VydmVySXBxAH4ABEwAEXNlcnZpY2VEZWZpbml0aW9udAAwTG9yZy9rdWFsaS9yaWNlL2tzYi9tZXNzYWdpbmcvU2VydmljZURlZmluaXRpb247TAALc2VydmljZU5hbWVxAH4ABEwAEHNlcnZpY2VOYW1lc3BhY2VxAH4ABHhwc3IAEWphdmEubGFuZy5Cb29sZWFuzSBygNWc+u4CAAFaAAV2YWx1ZXhwAXB0AEBodHRwOi8vbG9jYWxob3N0OjgwODAva3ItZGV2L3JlbW90aW5nL09TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNlc3IAEWphdmEubGFuZy5JbnRlZ2VyEuKgpPeBhzgCAAFJAAV2YWx1ZXhyABBqYXZhLmxhbmcuTnVtYmVyhqyVHQuU4IsCAAB4cAAAAAFzcgAOamF2YS5sYW5nLkxvbmc7i+SQzI8j3wIAAUoABXZhbHVleHEAfgAdAAAAAAAAD31zcgAZamF2YXgueG1sLm5hbWVzcGFjZS5RTmFtZYFtqC38O91sAgADTAAJbG9jYWxQYXJ0cQB+AARMAAxuYW1lc3BhY2VVUklxAH4ABEwABnByZWZpeHEAfgAEeHB0ABpPU0NhY2hlTm90aWZpY2F0aW9uU2VydmljZXQAAHEAfgAkdAd0ck8wQUJYTnlBREp2Y21jdWEzVmhiR2t1Y21salpTNXJjMkl1YldWemMyRm5hVzVuTGtwaGRtRlRaWEoyYVdObFJHVm1hVzVwZEdsdmJyYm5EVjlCTkd6eEFnQUJUQUFSYzJWeWRtbGpaVWx1ZEdWeVptRmpaWE4wQUJCTWFtRjJZUzkxZEdsc0wweHBjM1E3ZUhJQUxtOXlaeTVyZFdGc2FTNXlhV05sTG10ellpNXRaWE56WVdkcGJtY3VVMlZ5ZG1salpVUmxabWx1YVhScGIyNEFtd0pQV04wcGZnSUFEVXdBQzJKMWMxTmxZM1Z5YVhSNWRBQVRUR3BoZG1FdmJHRnVaeTlDYjI5c1pXRnVPMHdBRDJOeVpXUmxiblJwWVd4elZIbHdaWFFBVEV4dmNtY3ZhM1ZoYkdrdmNtbGpaUzlqYjNKbEwzTmxZM1Z5YVhSNUwyTnlaV1JsYm5ScFlXeHpMME55WldSbGJuUnBZV3h6VTI5MWNtTmxKRU55WldSbGJuUnBZV3h6Vkhsd1pUdE1BQkJzYjJOaGJGTmxjblpwWTJWT1lXMWxkQUFTVEdwaGRtRXZiR0Z1Wnk5VGRISnBibWM3VEFBWGJXVnpjMkZuWlVWNFkyVndkR2x2YmtoaGJtUnNaWEp4QUg0QUJVd0FERzFwYkd4cGMxUnZUR2wyWlhRQUVFeHFZWFpoTDJ4aGJtY3ZURzl1Wnp0TUFBaHdjbWx2Y21sMGVYUUFFMHhxWVhaaEwyeGhibWN2U1c1MFpXZGxjanRNQUFWeGRXVjFaWEVBZmdBRFRBQU5jbVYwY25sQmRIUmxiWEIwYzNFQWZnQUhUQUFIYzJWeWRtbGpaWFFBRWt4cVlYWmhMMnhoYm1jdlQySnFaV04wTzB3QUQzTmxjblpwWTJWRmJtUlFiMmx1ZEhRQURreHFZWFpoTDI1bGRDOVZVa3c3VEFBVGMyVnlkbWxqWlU1aGJXVlRjR0ZqWlZWU1NYRUFmZ0FGVEFBUWMyVnlkbWxqWlU1aGJXVnpjR0ZqWlhFQWZnQUZUQUFMYzJWeWRtbGpaVkJoZEdoeEFINEFCWGh3YzNJQUVXcGhkbUV1YkdGdVp5NUNiMjlzWldGdXpTQnlnTldjK3U0Q0FBRmFBQVYyWVd4MVpYaHdBWEIwQUJwUFUwTmhZMmhsVG05MGFXWnBZMkYwYVc5dVUyVnlkbWxqWlhRQVRXOXlaeTVyZFdGc2FTNXlhV05sTG10ellpNXRaWE56WVdkcGJtY3VaWGhqWlhCMGFXOXVhR0Z1Wkd4cGJtY3VSR1ZtWVhWc2RFMWxjM05oWjJWRmVHTmxjSFJwYjI1SVlXNWtiR1Z5YzNJQURtcGhkbUV1YkdGdVp5NU1iMjVuTzR2a2tNeVBJOThDQUFGS0FBVjJZV3gxWlhoeUFCQnFZWFpoTG14aGJtY3VUblZ0WW1WeWhxeVZIUXVVNElzQ0FBQjRjUC8vLy8vLy8vLy9jM0lBRVdwaGRtRXViR0Z1Wnk1SmJuUmxaMlZ5RXVLZ3BQZUJoemdDQUFGSkFBVjJZV3gxWlhoeEFINEFFQUFBQUFOemNRQitBQXNBY1FCK0FCTndjM0lBREdwaGRtRXVibVYwTGxWU1RKWWxOellhL09SeUF3QUhTUUFJYUdGemFFTnZaR1ZKQUFSd2IzSjBUQUFKWVhWMGFHOXlhWFI1Y1FCK0FBVk1BQVJtYVd4bGNRQitBQVZNQUFSb2IzTjBjUUIrQUFWTUFBaHdjbTkwYjJOdmJIRUFmZ0FGVEFBRGNtVm1jUUIrQUFWNGNQLy8vLzhBQUIrUWRBQU9iRzlqWVd4b2IzTjBPamd3T0RCMEFDc3ZhM0l0WkdWMkwzSmxiVzkwYVc1bkwwOVRRMkZqYUdWT2IzUnBabWxqWVhScGIyNVRaWEoyYVdObGRBQUpiRzlqWVd4b2IzTjBkQUFFYUhSMGNIQjRkQUFBZEFBR1ZGSkJWa1ZNZEFBQkwzTnlBQk5xWVhaaExuVjBhV3d1UVhKeVlYbE1hWE4wZUlIU0habkhZWjBEQUFGSkFBUnphWHBsZUhBQUFBQUZkd1FBQUFBS2RBQXpiM0pu';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 458    FOR UPDATE;        
    buffer := 'TG10MVlXeHBMbkpwWTJVdWEzTmlMbTFsYzNOaFoybHVaeTV6WlhKMmFXTmxMa3RUUWtwaGRtRlRaWEoyYVdObGRBQThZMjl0TG05d1pXNXplVzF3YUc5dWVTNXZjMk5oWTJobExtSmhjMlV1WlhabGJuUnpMa05oWTJobFJXNTBjbmxGZG1WdWRFeHBjM1JsYm1WeWRBQTNZMjl0TG05d1pXNXplVzF3YUc5dWVTNXZjMk5oWTJobExtSmhjMlV1WlhabGJuUnpMa05oWTJobFJYWmxiblJNYVhOMFpXNWxjblFBRjJwaGRtRXVkWFJwYkM1RmRtVnVkRXhwYzNSbGJtVnlkQUFzWTI5dExtOXdaVzV6ZVcxd2FHOXVlUzV2YzJOaFkyaGxMbUpoYzJVdVRHbG1aV041WTJ4bFFYZGhjbVY0dAANMTI5LjE4Ni43OS40NXNyADJvcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLkphdmFTZXJ2aWNlRGVmaW5pdGlvbrbnDV9BNGzxAgABTAARc2VydmljZUludGVyZmFjZXN0ABBMamF2YS91dGlsL0xpc3Q7eHIALm9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuU2VydmljZURlZmluaXRpb24AmwJPWN0pfgIADUwAC2J1c1NlY3VyaXR5cQB+ABNMAA9jcmVkZW50aWFsc1R5cGV0AExMb3JnL2t1YWxpL3JpY2UvY29yZS9zZWN1cml0eS9jcmVkZW50aWFscy9DcmVkZW50aWFsc1NvdXJjZSRDcmVkZW50aWFsc1R5cGU7TAAQbG9jYWxTZXJ2aWNlTmFtZXEAfgAETAAXbWVzc2FnZUV4Y2VwdGlvbkhhbmRsZXJxAH4ABEwADG1pbGxpc1RvTGl2ZXEAfgAVTAAIcHJpb3JpdHlxAH4AFEwABXF1ZXVlcQB+ABNMAA1yZXRyeUF0dGVtcHRzcQB+ABRMAAdzZXJ2aWNldAASTGphdmEvbGFuZy9PYmplY3Q7TAAPc2VydmljZUVuZFBvaW50dAAOTGphdmEvbmV0L1VSTDtMABNzZXJ2aWNlTmFtZVNwYWNlVVJJcQB+AARMABBzZXJ2aWNlTmFtZXNwYWNlcQB+AARMAAtzZXJ2aWNlUGF0aHEAfgAEeHBzcQB+ABkBcHQAGk9TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNldABNb3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5leGNlcHRpb25oYW5kbGluZy5EZWZhdWx0TWVzc2FnZUV4Y2VwdGlvbkhhbmRsZXJzcQB+AB///////////3NxAH4AHAAAAANzcQB+ABkAcQB+ADJwc3IADGphdmEubmV0LlVSTJYlNzYa/ORyAwAHSQAIaGFzaENvZGVJAARwb3J0TAAJYXV0aG9yaXR5cQB+AARMAARmaWxlcQB+AARMAARob3N0cQB+AARMAAhwcm90b2NvbHEAfgAETAADcmVmcQB+AAR4cGkboqAAAB+QdAAObG9jYWxob3N0OjgwODB0ACsva3ItZGV2L3JlbW90aW5nL09TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNldAAJbG9jYWxob3N0dAAEaHR0cHB4dAAAdAAGVFJBVkVMdAABL3NyABNqYXZhLnV0aWwuQXJyYXlMaXN0eIHSHZnHYZ0DAAFJAARzaXpleHAAAAAFdwQAAAAKdAAzb3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5zZXJ2aWNlLktTQkphdmFTZXJ2aWNldAA8Y29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLmJhc2UuZXZlbnRzLkNhY2hlRW50cnlFdmVudExpc3RlbmVydAA3Y29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLmJhc2UuZXZlbnRzLkNhY2hlRXZlbnRMaXN0ZW5lcnQAF2phdmEudXRpbC5FdmVudExpc3RlbmVydAAsY29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLmJhc2UuTGlmZWN5Y2xlQXdhcmV4cQB+ACN0AAZUUkFWRUw=';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
INSERT INTO KRSB_MSG_PYLD_T (MSG_PYLD,MSG_QUE_ID)
  VALUES (EMPTY_CLOB(),459)
/
-- Length: 6140
-- Chunks: 2
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 459    FOR UPDATE;        
    buffer := 'rO0ABXNyAC1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLkFzeW5jaHJvbm91c0NhbGzxnQ5Y0t0lZQIAB1oAFWlnbm9yZVN0b3JlQW5kRm9yd2FyZFsACWFyZ3VtZW50c3QAE1tMamF2YS9sYW5nL09iamVjdDtMAAhjYWxsYmFja3QAM0xvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL0FzeW5jaHJvbm91c0NhbGxiYWNrO0wAB2NvbnRleHR0ABZMamF2YS9pby9TZXJpYWxpemFibGU7TAAKbWV0aG9kTmFtZXQAEkxqYXZhL2xhbmcvU3RyaW5nO1sACnBhcmFtVHlwZXN0ABJbTGphdmEvbGFuZy9DbGFzcztMAAtzZXJ2aWNlSW5mb3QAKkxvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL1NlcnZpY2VJbmZvO3hwAHVyABNbTGphdmEubGFuZy5PYmplY3Q7kM5YnxBzKWwCAAB4cAAAAAFzcgBDY29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLnBsdWdpbnMuY2x1c3RlcnN1cHBvcnQuQ2x1c3Rlck5vdGlmaWNhdGlvbhY+6VckvG9pAgACSQAEdHlwZUwABGRhdGFxAH4AA3hwAAAAAnQATERvY3VtZW50VHlwZVJ1bGVDYWNoZTpJZGVudGl0eU1hbmFnZW1lbnRHZW5lcmljUGVybWlzc2lvbk1haW50ZW5hbmNlRG9jdW1lbnRwcHQABmludm9rZXVyABJbTGphdmEubGFuZy5DbGFzczurFteuy81amQIAAHhwAAAAAXZyABRqYXZhLmlvLlNlcmlhbGl6YWJsZRCbYQ3Xm2TtAgAAeHBzcgAob3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5TZXJ2aWNlSW5mb8UXI7YpBLe6AgALTAAFYWxpdmV0ABNMamF2YS9sYW5nL0Jvb2xlYW47TAAUZW5kcG9pbnRBbHRlcm5hdGVVcmxxAH4ABEwAC2VuZHBvaW50VXJscQB+AARMAApsb2NrVmVyTmJydAATTGphdmEvbGFuZy9JbnRlZ2VyO0wADm1lc3NhZ2VFbnRyeUlkdAAQTGphdmEvbGFuZy9Mb25nO0wABXFuYW1ldAAbTGphdmF4L3htbC9uYW1lc3BhY2UvUU5hbWU7TAAac2VyaWFsaXplZFNlcnZpY2VOYW1lc3BhY2VxAH4ABEwACHNlcnZlcklwcQB+AARMABFzZXJ2aWNlRGVmaW5pdGlvbnQAMExvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL1NlcnZpY2VEZWZpbml0aW9uO0wAC3NlcnZpY2VOYW1lcQB+AARMABBzZXJ2aWNlTmFtZXNwYWNlcQB+AAR4cHNyABFqYXZhLmxhbmcuQm9vbGVhbs0gcoDVnPruAgABWgAFdmFsdWV4cAFwdABAaHR0cDovL2xvY2FsaG9zdDo4MDgwL2tyLWRldi9yZW1vdGluZy9PU0NhY2hlTm90aWZpY2F0aW9uU2VydmljZXNyABFqYXZhLmxhbmcuSW50ZWdlchLioKT3gYc4AgABSQAFdmFsdWV4cgAQamF2YS5sYW5nLk51bWJlcoaslR0LlOCLAgAAeHAAAAABc3IADmphdmEubGFuZy5Mb25nO4vkkMyPI98CAAFKAAV2YWx1ZXhxAH4AHQAAAAAAAA99c3IAGWphdmF4LnhtbC5uYW1lc3BhY2UuUU5hbWWBbagt/DvdbAIAA0wACWxvY2FsUGFydHEAfgAETAAMbmFtZXNwYWNlVVJJcQB+AARMAAZwcmVmaXhxAH4ABHhwdAAaT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2V0AABxAH4AJHQHdHJPMEFCWE55QURKdmNtY3VhM1ZoYkdrdWNtbGpaUzVyYzJJdWJXVnpjMkZuYVc1bkxrcGhkbUZUWlhKMmFXTmxSR1ZtYVc1cGRHbHZicmJuRFY5Qk5HenhBZ0FCVEFBUmMyVnlkbWxqWlVsdWRHVnlabUZqWlhOMEFCQk1hbUYyWVM5MWRHbHNMMHhwYzNRN2VISUFMbTl5Wnk1cmRXRnNhUzV5YVdObExtdHpZaTV0WlhOellXZHBibWN1VTJWeWRtbGpaVVJsWm1sdWFYUnBiMjRBbXdKUFdOMHBmZ0lBRFV3QUMySjFjMU5sWTNWeWFYUjVkQUFUVEdwaGRtRXZiR0Z1Wnk5Q2IyOXNaV0Z1TzB3QUQyTnlaV1JsYm5ScFlXeHpWSGx3WlhRQVRFeHZjbWN2YTNWaGJHa3ZjbWxqWlM5amIzSmxMM05sWTNWeWFYUjVMMk55WldSbGJuUnBZV3h6TDBOeVpXUmxiblJwWVd4elUyOTFjbU5sSkVOeVpXUmxiblJwWVd4elZIbHdaVHRNQUJCc2IyTmhiRk5sY25acFkyVk9ZVzFsZEFBU1RHcGhkbUV2YkdGdVp5OVRkSEpwYm1jN1RBQVhiV1Z6YzJGblpVVjRZMlZ3ZEdsdmJraGhibVJzWlhKeEFINEFCVXdBREcxcGJHeHBjMVJ2VEdsMlpYUUFFRXhxWVhaaEwyeGhibWN2VEc5dVp6dE1BQWh3Y21sdmNtbDBlWFFBRTB4cVlYWmhMMnhoYm1jdlNXNTBaV2RsY2p0TUFBVnhkV1YxWlhFQWZnQURUQUFOY21WMGNubEJkSFJsYlhCMGMzRUFmZ0FIVEFBSGMyVnlkbWxqWlhRQUVreHFZWFpoTDJ4aGJtY3ZUMkpxWldOME8wd0FEM05sY25acFkyVkZibVJRYjJsdWRIUUFEa3hxWVhaaEwyNWxkQzlWVWt3N1RBQVRjMlZ5ZG1salpVNWhiV1ZUY0dGalpWVlNTWEVBZmdBRlRBQVFjMlZ5ZG1salpVNWhiV1Z6Y0dGalpYRUFmZ0FGVEFBTGMyVnlkbWxqWlZCaGRHaHhBSDRBQlhod2MzSUFFV3BoZG1FdWJHRnVaeTVDYjI5c1pXRnV6U0J5Z05XYyt1NENBQUZhQUFWMllXeDFaWGh3QVhCMEFCcFBVME5oWTJobFRtOTBhV1pwWTJGMGFXOXVVMlZ5ZG1salpYUUFUVzl5Wnk1cmRXRnNhUzV5YVdObExtdHpZaTV0WlhOellXZHBibWN1WlhoalpYQjBhVzl1YUdGdVpHeHBibWN1UkdWbVlYVnNkRTFsYzNOaFoyVkZlR05sY0hScGIyNUlZVzVrYkdWeWMzSUFEbXBoZG1FdWJHRnVaeTVNYjI1bk80dmtrTXlQSTk4Q0FBRktBQVYyWVd4MVpYaHlBQkJxWVhaaExteGhibWN1VG5WdFltVnlocXlWSFF1VTRJc0NBQUI0Y1AvLy8vLy8vLy8vYzNJQUVXcGhkbUV1YkdGdVp5NUpiblJsWjJWeUV1S2dwUGVCaHpnQ0FBRkpBQVYyWVd4MVpYaHhBSDRBRUFBQUFBTnpjUUIrQUFzQWNRQitBQk53YzNJQURHcGhkbUV1Ym1WMExsVlNUSllsTnpZYS9PUnlBd0FIU1FBSWFHRnphRU52WkdWSkFBUndiM0owVEFBSllYVjBhRzl5YVhSNWNRQitBQVZNQUFSbWFXeGxjUUIrQUFWTUFBUm9iM04wY1FCK0FBVk1BQWh3Y205MGIyTnZiSEVBZmdBRlRBQURjbVZtY1FCK0FBVjRjUC8vLy84QUFCK1FkQUFPYkc5allXeG9iM04wT2pnd09EQjBBQ3N2YTNJdFpHVjJMM0psYlc5MGFXNW5MMDlUUTJGamFHVk9iM1JwWm1sallYUnBiMjVUWlhKMmFXTmxkQUFKYkc5allXeG9iM04wZEFBRWFIUjBjSEI0ZEFBQWRBQUdWRkpCVmtWTWRBQUJMM055QUJOcVlYWmhMblYwYVd3dVFYSnlZWGxNYVhOMGVJSFNIWm5IWVowREFBRkpBQVJ6';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 459    FOR UPDATE;        
    buffer := 'YVhwbGVIQUFBQUFGZHdRQUFBQUtkQUF6YjNKbkxtdDFZV3hwTG5KcFkyVXVhM05pTG0xbGMzTmhaMmx1Wnk1elpYSjJhV05sTGt0VFFrcGhkbUZUWlhKMmFXTmxkQUE4WTI5dExtOXdaVzV6ZVcxd2FHOXVlUzV2YzJOaFkyaGxMbUpoYzJVdVpYWmxiblJ6TGtOaFkyaGxSVzUwY25sRmRtVnVkRXhwYzNSbGJtVnlkQUEzWTI5dExtOXdaVzV6ZVcxd2FHOXVlUzV2YzJOaFkyaGxMbUpoYzJVdVpYWmxiblJ6TGtOaFkyaGxSWFpsYm5STWFYTjBaVzVsY25RQUYycGhkbUV1ZFhScGJDNUZkbVZ1ZEV4cGMzUmxibVZ5ZEFBc1kyOXRMbTl3Wlc1emVXMXdhRzl1ZVM1dmMyTmhZMmhsTG1KaGMyVXVUR2xtWldONVkyeGxRWGRoY21WNHQADTEyOS4xODYuNzkuNDVzcgAyb3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5KYXZhU2VydmljZURlZmluaXRpb2625w1fQTRs8QIAAUwAEXNlcnZpY2VJbnRlcmZhY2VzdAAQTGphdmEvdXRpbC9MaXN0O3hyAC5vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLlNlcnZpY2VEZWZpbml0aW9uAJsCT1jdKX4CAA1MAAtidXNTZWN1cml0eXEAfgATTAAPY3JlZGVudGlhbHNUeXBldABMTG9yZy9rdWFsaS9yaWNlL2NvcmUvc2VjdXJpdHkvY3JlZGVudGlhbHMvQ3JlZGVudGlhbHNTb3VyY2UkQ3JlZGVudGlhbHNUeXBlO0wAEGxvY2FsU2VydmljZU5hbWVxAH4ABEwAF21lc3NhZ2VFeGNlcHRpb25IYW5kbGVycQB+AARMAAxtaWxsaXNUb0xpdmVxAH4AFUwACHByaW9yaXR5cQB+ABRMAAVxdWV1ZXEAfgATTAANcmV0cnlBdHRlbXB0c3EAfgAUTAAHc2VydmljZXQAEkxqYXZhL2xhbmcvT2JqZWN0O0wAD3NlcnZpY2VFbmRQb2ludHQADkxqYXZhL25ldC9VUkw7TAATc2VydmljZU5hbWVTcGFjZVVSSXEAfgAETAAQc2VydmljZU5hbWVzcGFjZXEAfgAETAALc2VydmljZVBhdGhxAH4ABHhwc3EAfgAZAXB0ABpPU0NhY2hlTm90aWZpY2F0aW9uU2VydmljZXQATW9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuZXhjZXB0aW9uaGFuZGxpbmcuRGVmYXVsdE1lc3NhZ2VFeGNlcHRpb25IYW5kbGVyc3EAfgAf//////////9zcQB+ABwAAAADc3EAfgAZAHEAfgAycHNyAAxqYXZhLm5ldC5VUkyWJTc2GvzkcgMAB0kACGhhc2hDb2RlSQAEcG9ydEwACWF1dGhvcml0eXEAfgAETAAEZmlsZXEAfgAETAAEaG9zdHEAfgAETAAIcHJvdG9jb2xxAH4ABEwAA3JlZnEAfgAEeHBpG6KgAAAfkHQADmxvY2FsaG9zdDo4MDgwdAArL2tyLWRldi9yZW1vdGluZy9PU0NhY2hlTm90aWZpY2F0aW9uU2VydmljZXQACWxvY2FsaG9zdHQABGh0dHBweHQAAHQABlRSQVZFTHQAAS9zcgATamF2YS51dGlsLkFycmF5TGlzdHiB0h2Zx2GdAwABSQAEc2l6ZXhwAAAABXcEAAAACnQAM29yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuc2VydmljZS5LU0JKYXZhU2VydmljZXQAPGNvbS5vcGVuc3ltcGhvbnkub3NjYWNoZS5iYXNlLmV2ZW50cy5DYWNoZUVudHJ5RXZlbnRMaXN0ZW5lcnQAN2NvbS5vcGVuc3ltcGhvbnkub3NjYWNoZS5iYXNlLmV2ZW50cy5DYWNoZUV2ZW50TGlzdGVuZXJ0ABdqYXZhLnV0aWwuRXZlbnRMaXN0ZW5lcnQALGNvbS5vcGVuc3ltcGhvbnkub3NjYWNoZS5iYXNlLkxpZmVjeWNsZUF3YXJleHEAfgAjdAAGVFJBVkVM';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
INSERT INTO KRSB_MSG_PYLD_T (MSG_PYLD,MSG_QUE_ID)
  VALUES (EMPTY_CLOB(),460)
/
-- Length: 6060
-- Chunks: 2
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 460    FOR UPDATE;        
    buffer := 'rO0ABXNyAC1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLkFzeW5jaHJvbm91c0NhbGzxnQ5Y0t0lZQIAB1oAFWlnbm9yZVN0b3JlQW5kRm9yd2FyZFsACWFyZ3VtZW50c3QAE1tMamF2YS9sYW5nL09iamVjdDtMAAhjYWxsYmFja3QAM0xvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL0FzeW5jaHJvbm91c0NhbGxiYWNrO0wAB2NvbnRleHR0ABZMamF2YS9pby9TZXJpYWxpemFibGU7TAAKbWV0aG9kTmFtZXQAEkxqYXZhL2xhbmcvU3RyaW5nO1sACnBhcmFtVHlwZXN0ABJbTGphdmEvbGFuZy9DbGFzcztMAAtzZXJ2aWNlSW5mb3QAKkxvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL1NlcnZpY2VJbmZvO3hwAHVyABNbTGphdmEubGFuZy5PYmplY3Q7kM5YnxBzKWwCAAB4cAAAAAFzcgBDY29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLnBsdWdpbnMuY2x1c3RlcnN1cHBvcnQuQ2x1c3Rlck5vdGlmaWNhdGlvbhY+6VckvG9pAgACSQAEdHlwZUwABGRhdGFxAH4AA3hwAAAAAnQADkRvY3VtZW50VHlwZUlkcHB0AAZpbnZva2V1cgASW0xqYXZhLmxhbmcuQ2xhc3M7qxbXrsvNWpkCAAB4cAAAAAF2cgAUamF2YS5pby5TZXJpYWxpemFibGUQm2EN15tk7QIAAHhwc3IAKG9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuU2VydmljZUluZm/FFyO2KQS3ugIAC0wABWFsaXZldAATTGphdmEvbGFuZy9Cb29sZWFuO0wAFGVuZHBvaW50QWx0ZXJuYXRlVXJscQB+AARMAAtlbmRwb2ludFVybHEAfgAETAAKbG9ja1Zlck5icnQAE0xqYXZhL2xhbmcvSW50ZWdlcjtMAA5tZXNzYWdlRW50cnlJZHQAEExqYXZhL2xhbmcvTG9uZztMAAVxbmFtZXQAG0xqYXZheC94bWwvbmFtZXNwYWNlL1FOYW1lO0wAGnNlcmlhbGl6ZWRTZXJ2aWNlTmFtZXNwYWNlcQB+AARMAAhzZXJ2ZXJJcHEAfgAETAARc2VydmljZURlZmluaXRpb250ADBMb3JnL2t1YWxpL3JpY2Uva3NiL21lc3NhZ2luZy9TZXJ2aWNlRGVmaW5pdGlvbjtMAAtzZXJ2aWNlTmFtZXEAfgAETAAQc2VydmljZU5hbWVzcGFjZXEAfgAEeHBzcgARamF2YS5sYW5nLkJvb2xlYW7NIHKA1Zz67gIAAVoABXZhbHVleHABcHQAQGh0dHA6Ly9sb2NhbGhvc3Q6ODA4MC9rci1kZXYvcmVtb3RpbmcvT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2VzcgARamF2YS5sYW5nLkludGVnZXIS4qCk94GHOAIAAUkABXZhbHVleHIAEGphdmEubGFuZy5OdW1iZXKGrJUdC5TgiwIAAHhwAAAAAXNyAA5qYXZhLmxhbmcuTG9uZzuL5JDMjyPfAgABSgAFdmFsdWV4cQB+AB0AAAAAAAAPfXNyABlqYXZheC54bWwubmFtZXNwYWNlLlFOYW1lgW2oLfw73WwCAANMAAlsb2NhbFBhcnRxAH4ABEwADG5hbWVzcGFjZVVSSXEAfgAETAAGcHJlZml4cQB+AAR4cHQAGk9TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNldAAAcQB+ACR0B3RyTzBBQlhOeUFESnZjbWN1YTNWaGJHa3VjbWxqWlM1cmMySXViV1Z6YzJGbmFXNW5Ma3BoZG1GVFpYSjJhV05sUkdWbWFXNXBkR2x2YnJibkRWOUJOR3p4QWdBQlRBQVJjMlZ5ZG1salpVbHVkR1Z5Wm1GalpYTjBBQkJNYW1GMllTOTFkR2xzTDB4cGMzUTdlSElBTG05eVp5NXJkV0ZzYVM1eWFXTmxMbXR6WWk1dFpYTnpZV2RwYm1jdVUyVnlkbWxqWlVSbFptbHVhWFJwYjI0QW13SlBXTjBwZmdJQURVd0FDMkoxYzFObFkzVnlhWFI1ZEFBVFRHcGhkbUV2YkdGdVp5OUNiMjlzWldGdU8wd0FEMk55WldSbGJuUnBZV3h6Vkhsd1pYUUFURXh2Y21jdmEzVmhiR2t2Y21salpTOWpiM0psTDNObFkzVnlhWFI1TDJOeVpXUmxiblJwWVd4ekwwTnlaV1JsYm5ScFlXeHpVMjkxY21ObEpFTnlaV1JsYm5ScFlXeHpWSGx3WlR0TUFCQnNiMk5oYkZObGNuWnBZMlZPWVcxbGRBQVNUR3BoZG1FdmJHRnVaeTlUZEhKcGJtYzdUQUFYYldWemMyRm5aVVY0WTJWd2RHbHZia2hoYm1Sc1pYSnhBSDRBQlV3QURHMXBiR3hwYzFSdlRHbDJaWFFBRUV4cVlYWmhMMnhoYm1jdlRHOXVaenRNQUFod2NtbHZjbWwwZVhRQUUweHFZWFpoTDJ4aGJtY3ZTVzUwWldkbGNqdE1BQVZ4ZFdWMVpYRUFmZ0FEVEFBTmNtVjBjbmxCZEhSbGJYQjBjM0VBZmdBSFRBQUhjMlZ5ZG1salpYUUFFa3hxWVhaaEwyeGhibWN2VDJKcVpXTjBPMHdBRDNObGNuWnBZMlZGYm1SUWIybHVkSFFBRGt4cVlYWmhMMjVsZEM5VlVrdzdUQUFUYzJWeWRtbGpaVTVoYldWVGNHRmpaVlZTU1hFQWZnQUZUQUFRYzJWeWRtbGpaVTVoYldWemNHRmpaWEVBZmdBRlRBQUxjMlZ5ZG1salpWQmhkR2h4QUg0QUJYaHdjM0lBRVdwaGRtRXViR0Z1Wnk1Q2IyOXNaV0Z1elNCeWdOV2MrdTRDQUFGYUFBVjJZV3gxWlhod0FYQjBBQnBQVTBOaFkyaGxUbTkwYVdacFkyRjBhVzl1VTJWeWRtbGpaWFFBVFc5eVp5NXJkV0ZzYVM1eWFXTmxMbXR6WWk1dFpYTnpZV2RwYm1jdVpYaGpaWEIwYVc5dWFHRnVaR3hwYm1jdVJHVm1ZWFZzZEUxbGMzTmhaMlZGZUdObGNIUnBiMjVJWVc1a2JHVnljM0lBRG1waGRtRXViR0Z1Wnk1TWIyNW5PNHZra015UEk5OENBQUZLQUFWMllXeDFaWGh5QUJCcVlYWmhMbXhoYm1jdVRuVnRZbVZ5aHF5VkhRdVU0SXNDQUFCNGNQLy8vLy8vLy8vL2MzSUFFV3BoZG1FdWJHRnVaeTVKYm5SbFoyVnlFdUtncFBlQmh6Z0NBQUZKQUFWMllXeDFaWGh4QUg0QUVBQUFBQU56Y1FCK0FBc0FjUUIrQUJOd2MzSUFER3BoZG1FdWJtVjBMbFZTVEpZbE56WWEvT1J5QXdBSFNRQUlhR0Z6YUVOdlpHVkpBQVJ3YjNKMFRBQUpZWFYwYUc5eWFYUjVjUUIrQUFWTUFBUm1hV3hsY1FCK0FBVk1BQVJvYjNOMGNRQitBQVZNQUFod2NtOTBiMk52YkhFQWZnQUZUQUFEY21WbWNRQitBQVY0Y1AvLy8vOEFBQitRZEFBT2JHOWpZV3hvYjNOME9qZ3dPREIwQUNzdmEzSXRaR1YyTDNKbGJXOTBhVzVuTDA5VFEyRmphR1ZPYjNScFptbGpZWFJwYjI1VFpYSjJhV05sZEFBSmJHOWpZV3hvYjNOMGRBQUVhSFIwY0hCNGRBQUFkQUFHVkZKQlZrVk1kQUFCTDNOeUFCTnFZWFpoTG5WMGFXd3VRWEp5WVhsTWFYTjBlSUhTSFpuSFlaMERBQUZKQUFSemFYcGxlSEFBQUFBRmR3UUFBQUFLZEFBemIzSm5MbXQxWVd4cExuSnBZMlV1YTNOaUxtMWxjM05oWjJsdVp5';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 460    FOR UPDATE;        
    buffer := 'NXpaWEoyYVdObExrdFRRa3BoZG1GVFpYSjJhV05sZEFBOFkyOXRMbTl3Wlc1emVXMXdhRzl1ZVM1dmMyTmhZMmhsTG1KaGMyVXVaWFpsYm5SekxrTmhZMmhsUlc1MGNubEZkbVZ1ZEV4cGMzUmxibVZ5ZEFBM1kyOXRMbTl3Wlc1emVXMXdhRzl1ZVM1dmMyTmhZMmhsTG1KaGMyVXVaWFpsYm5SekxrTmhZMmhsUlhabGJuUk1hWE4wWlc1bGNuUUFGMnBoZG1FdWRYUnBiQzVGZG1WdWRFeHBjM1JsYm1WeWRBQXNZMjl0TG05d1pXNXplVzF3YUc5dWVTNXZjMk5oWTJobExtSmhjMlV1VEdsbVpXTjVZMnhsUVhkaGNtVjR0AA0xMjkuMTg2Ljc5LjQ1c3IAMm9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuSmF2YVNlcnZpY2VEZWZpbml0aW9utucNX0E0bPECAAFMABFzZXJ2aWNlSW50ZXJmYWNlc3QAEExqYXZhL3V0aWwvTGlzdDt4cgAub3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5TZXJ2aWNlRGVmaW5pdGlvbgCbAk9Y3Sl+AgANTAALYnVzU2VjdXJpdHlxAH4AE0wAD2NyZWRlbnRpYWxzVHlwZXQATExvcmcva3VhbGkvcmljZS9jb3JlL3NlY3VyaXR5L2NyZWRlbnRpYWxzL0NyZWRlbnRpYWxzU291cmNlJENyZWRlbnRpYWxzVHlwZTtMABBsb2NhbFNlcnZpY2VOYW1lcQB+AARMABdtZXNzYWdlRXhjZXB0aW9uSGFuZGxlcnEAfgAETAAMbWlsbGlzVG9MaXZlcQB+ABVMAAhwcmlvcml0eXEAfgAUTAAFcXVldWVxAH4AE0wADXJldHJ5QXR0ZW1wdHNxAH4AFEwAB3NlcnZpY2V0ABJMamF2YS9sYW5nL09iamVjdDtMAA9zZXJ2aWNlRW5kUG9pbnR0AA5MamF2YS9uZXQvVVJMO0wAE3NlcnZpY2VOYW1lU3BhY2VVUklxAH4ABEwAEHNlcnZpY2VOYW1lc3BhY2VxAH4ABEwAC3NlcnZpY2VQYXRocQB+AAR4cHNxAH4AGQFwdAAaT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2V0AE1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLmV4Y2VwdGlvbmhhbmRsaW5nLkRlZmF1bHRNZXNzYWdlRXhjZXB0aW9uSGFuZGxlcnNxAH4AH///////////c3EAfgAcAAAAA3NxAH4AGQBxAH4AMnBzcgAMamF2YS5uZXQuVVJMliU3Nhr85HIDAAdJAAhoYXNoQ29kZUkABHBvcnRMAAlhdXRob3JpdHlxAH4ABEwABGZpbGVxAH4ABEwABGhvc3RxAH4ABEwACHByb3RvY29scQB+AARMAANyZWZxAH4ABHhwaRuioAAAH5B0AA5sb2NhbGhvc3Q6ODA4MHQAKy9rci1kZXYvcmVtb3RpbmcvT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2V0AAlsb2NhbGhvc3R0AARodHRwcHh0AAB0AAZUUkFWRUx0AAEvc3IAE2phdmEudXRpbC5BcnJheUxpc3R4gdIdmcdhnQMAAUkABHNpemV4cAAAAAV3BAAAAAp0ADNvcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLnNlcnZpY2UuS1NCSmF2YVNlcnZpY2V0ADxjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5ldmVudHMuQ2FjaGVFbnRyeUV2ZW50TGlzdGVuZXJ0ADdjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5ldmVudHMuQ2FjaGVFdmVudExpc3RlbmVydAAXamF2YS51dGlsLkV2ZW50TGlzdGVuZXJ0ACxjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5MaWZlY3ljbGVBd2FyZXhxAH4AI3QABlRSQVZFTA==';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
INSERT INTO KRSB_MSG_PYLD_T (MSG_PYLD,MSG_QUE_ID)
  VALUES (EMPTY_CLOB(),461)
/
-- Length: 6060
-- Chunks: 2
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 461    FOR UPDATE;        
    buffer := 'rO0ABXNyAC1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLkFzeW5jaHJvbm91c0NhbGzxnQ5Y0t0lZQIAB1oAFWlnbm9yZVN0b3JlQW5kRm9yd2FyZFsACWFyZ3VtZW50c3QAE1tMamF2YS9sYW5nL09iamVjdDtMAAhjYWxsYmFja3QAM0xvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL0FzeW5jaHJvbm91c0NhbGxiYWNrO0wAB2NvbnRleHR0ABZMamF2YS9pby9TZXJpYWxpemFibGU7TAAKbWV0aG9kTmFtZXQAEkxqYXZhL2xhbmcvU3RyaW5nO1sACnBhcmFtVHlwZXN0ABJbTGphdmEvbGFuZy9DbGFzcztMAAtzZXJ2aWNlSW5mb3QAKkxvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL1NlcnZpY2VJbmZvO3hwAHVyABNbTGphdmEubGFuZy5PYmplY3Q7kM5YnxBzKWwCAAB4cAAAAAFzcgBDY29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLnBsdWdpbnMuY2x1c3RlcnN1cHBvcnQuQ2x1c3Rlck5vdGlmaWNhdGlvbhY+6VckvG9pAgACSQAEdHlwZUwABGRhdGFxAH4AA3hwAAAAAnQAEERvY3VtZW50VHlwZU5hbWVwcHQABmludm9rZXVyABJbTGphdmEubGFuZy5DbGFzczurFteuy81amQIAAHhwAAAAAXZyABRqYXZhLmlvLlNlcmlhbGl6YWJsZRCbYQ3Xm2TtAgAAeHBzcgAob3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5TZXJ2aWNlSW5mb8UXI7YpBLe6AgALTAAFYWxpdmV0ABNMamF2YS9sYW5nL0Jvb2xlYW47TAAUZW5kcG9pbnRBbHRlcm5hdGVVcmxxAH4ABEwAC2VuZHBvaW50VXJscQB+AARMAApsb2NrVmVyTmJydAATTGphdmEvbGFuZy9JbnRlZ2VyO0wADm1lc3NhZ2VFbnRyeUlkdAAQTGphdmEvbGFuZy9Mb25nO0wABXFuYW1ldAAbTGphdmF4L3htbC9uYW1lc3BhY2UvUU5hbWU7TAAac2VyaWFsaXplZFNlcnZpY2VOYW1lc3BhY2VxAH4ABEwACHNlcnZlcklwcQB+AARMABFzZXJ2aWNlRGVmaW5pdGlvbnQAMExvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL1NlcnZpY2VEZWZpbml0aW9uO0wAC3NlcnZpY2VOYW1lcQB+AARMABBzZXJ2aWNlTmFtZXNwYWNlcQB+AAR4cHNyABFqYXZhLmxhbmcuQm9vbGVhbs0gcoDVnPruAgABWgAFdmFsdWV4cAFwdABAaHR0cDovL2xvY2FsaG9zdDo4MDgwL2tyLWRldi9yZW1vdGluZy9PU0NhY2hlTm90aWZpY2F0aW9uU2VydmljZXNyABFqYXZhLmxhbmcuSW50ZWdlchLioKT3gYc4AgABSQAFdmFsdWV4cgAQamF2YS5sYW5nLk51bWJlcoaslR0LlOCLAgAAeHAAAAABc3IADmphdmEubGFuZy5Mb25nO4vkkMyPI98CAAFKAAV2YWx1ZXhxAH4AHQAAAAAAAA99c3IAGWphdmF4LnhtbC5uYW1lc3BhY2UuUU5hbWWBbagt/DvdbAIAA0wACWxvY2FsUGFydHEAfgAETAAMbmFtZXNwYWNlVVJJcQB+AARMAAZwcmVmaXhxAH4ABHhwdAAaT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2V0AABxAH4AJHQHdHJPMEFCWE55QURKdmNtY3VhM1ZoYkdrdWNtbGpaUzVyYzJJdWJXVnpjMkZuYVc1bkxrcGhkbUZUWlhKMmFXTmxSR1ZtYVc1cGRHbHZicmJuRFY5Qk5HenhBZ0FCVEFBUmMyVnlkbWxqWlVsdWRHVnlabUZqWlhOMEFCQk1hbUYyWVM5MWRHbHNMMHhwYzNRN2VISUFMbTl5Wnk1cmRXRnNhUzV5YVdObExtdHpZaTV0WlhOellXZHBibWN1VTJWeWRtbGpaVVJsWm1sdWFYUnBiMjRBbXdKUFdOMHBmZ0lBRFV3QUMySjFjMU5sWTNWeWFYUjVkQUFUVEdwaGRtRXZiR0Z1Wnk5Q2IyOXNaV0Z1TzB3QUQyTnlaV1JsYm5ScFlXeHpWSGx3WlhRQVRFeHZjbWN2YTNWaGJHa3ZjbWxqWlM5amIzSmxMM05sWTNWeWFYUjVMMk55WldSbGJuUnBZV3h6TDBOeVpXUmxiblJwWVd4elUyOTFjbU5sSkVOeVpXUmxiblJwWVd4elZIbHdaVHRNQUJCc2IyTmhiRk5sY25acFkyVk9ZVzFsZEFBU1RHcGhkbUV2YkdGdVp5OVRkSEpwYm1jN1RBQVhiV1Z6YzJGblpVVjRZMlZ3ZEdsdmJraGhibVJzWlhKeEFINEFCVXdBREcxcGJHeHBjMVJ2VEdsMlpYUUFFRXhxWVhaaEwyeGhibWN2VEc5dVp6dE1BQWh3Y21sdmNtbDBlWFFBRTB4cVlYWmhMMnhoYm1jdlNXNTBaV2RsY2p0TUFBVnhkV1YxWlhFQWZnQURUQUFOY21WMGNubEJkSFJsYlhCMGMzRUFmZ0FIVEFBSGMyVnlkbWxqWlhRQUVreHFZWFpoTDJ4aGJtY3ZUMkpxWldOME8wd0FEM05sY25acFkyVkZibVJRYjJsdWRIUUFEa3hxWVhaaEwyNWxkQzlWVWt3N1RBQVRjMlZ5ZG1salpVNWhiV1ZUY0dGalpWVlNTWEVBZmdBRlRBQVFjMlZ5ZG1salpVNWhiV1Z6Y0dGalpYRUFmZ0FGVEFBTGMyVnlkbWxqWlZCaGRHaHhBSDRBQlhod2MzSUFFV3BoZG1FdWJHRnVaeTVDYjI5c1pXRnV6U0J5Z05XYyt1NENBQUZhQUFWMllXeDFaWGh3QVhCMEFCcFBVME5oWTJobFRtOTBhV1pwWTJGMGFXOXVVMlZ5ZG1salpYUUFUVzl5Wnk1cmRXRnNhUzV5YVdObExtdHpZaTV0WlhOellXZHBibWN1WlhoalpYQjBhVzl1YUdGdVpHeHBibWN1UkdWbVlYVnNkRTFsYzNOaFoyVkZlR05sY0hScGIyNUlZVzVrYkdWeWMzSUFEbXBoZG1FdWJHRnVaeTVNYjI1bk80dmtrTXlQSTk4Q0FBRktBQVYyWVd4MVpYaHlBQkJxWVhaaExteGhibWN1VG5WdFltVnlocXlWSFF1VTRJc0NBQUI0Y1AvLy8vLy8vLy8vYzNJQUVXcGhkbUV1YkdGdVp5NUpiblJsWjJWeUV1S2dwUGVCaHpnQ0FBRkpBQVYyWVd4MVpYaHhBSDRBRUFBQUFBTnpjUUIrQUFzQWNRQitBQk53YzNJQURHcGhkbUV1Ym1WMExsVlNUSllsTnpZYS9PUnlBd0FIU1FBSWFHRnphRU52WkdWSkFBUndiM0owVEFBSllYVjBhRzl5YVhSNWNRQitBQVZNQUFSbWFXeGxjUUIrQUFWTUFBUm9iM04wY1FCK0FBVk1BQWh3Y205MGIyTnZiSEVBZmdBRlRBQURjbVZtY1FCK0FBVjRjUC8vLy84QUFCK1FkQUFPYkc5allXeG9iM04wT2pnd09EQjBBQ3N2YTNJdFpHVjJMM0psYlc5MGFXNW5MMDlUUTJGamFHVk9iM1JwWm1sallYUnBiMjVUWlhKMmFXTmxkQUFKYkc5allXeG9iM04wZEFBRWFIUjBjSEI0ZEFBQWRBQUdWRkpCVmtWTWRBQUJMM055QUJOcVlYWmhMblYwYVd3dVFYSnlZWGxNYVhOMGVJSFNIWm5IWVowREFBRkpBQVJ6YVhwbGVIQUFBQUFGZHdRQUFBQUtkQUF6YjNKbkxtdDFZV3hwTG5KcFkyVXVhM05pTG0xbGMzTmhaMmx1';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 461    FOR UPDATE;        
    buffer := 'Wnk1elpYSjJhV05sTGt0VFFrcGhkbUZUWlhKMmFXTmxkQUE4WTI5dExtOXdaVzV6ZVcxd2FHOXVlUzV2YzJOaFkyaGxMbUpoYzJVdVpYWmxiblJ6TGtOaFkyaGxSVzUwY25sRmRtVnVkRXhwYzNSbGJtVnlkQUEzWTI5dExtOXdaVzV6ZVcxd2FHOXVlUzV2YzJOaFkyaGxMbUpoYzJVdVpYWmxiblJ6TGtOaFkyaGxSWFpsYm5STWFYTjBaVzVsY25RQUYycGhkbUV1ZFhScGJDNUZkbVZ1ZEV4cGMzUmxibVZ5ZEFBc1kyOXRMbTl3Wlc1emVXMXdhRzl1ZVM1dmMyTmhZMmhsTG1KaGMyVXVUR2xtWldONVkyeGxRWGRoY21WNHQADTEyOS4xODYuNzkuNDVzcgAyb3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5KYXZhU2VydmljZURlZmluaXRpb2625w1fQTRs8QIAAUwAEXNlcnZpY2VJbnRlcmZhY2VzdAAQTGphdmEvdXRpbC9MaXN0O3hyAC5vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLlNlcnZpY2VEZWZpbml0aW9uAJsCT1jdKX4CAA1MAAtidXNTZWN1cml0eXEAfgATTAAPY3JlZGVudGlhbHNUeXBldABMTG9yZy9rdWFsaS9yaWNlL2NvcmUvc2VjdXJpdHkvY3JlZGVudGlhbHMvQ3JlZGVudGlhbHNTb3VyY2UkQ3JlZGVudGlhbHNUeXBlO0wAEGxvY2FsU2VydmljZU5hbWVxAH4ABEwAF21lc3NhZ2VFeGNlcHRpb25IYW5kbGVycQB+AARMAAxtaWxsaXNUb0xpdmVxAH4AFUwACHByaW9yaXR5cQB+ABRMAAVxdWV1ZXEAfgATTAANcmV0cnlBdHRlbXB0c3EAfgAUTAAHc2VydmljZXQAEkxqYXZhL2xhbmcvT2JqZWN0O0wAD3NlcnZpY2VFbmRQb2ludHQADkxqYXZhL25ldC9VUkw7TAATc2VydmljZU5hbWVTcGFjZVVSSXEAfgAETAAQc2VydmljZU5hbWVzcGFjZXEAfgAETAALc2VydmljZVBhdGhxAH4ABHhwc3EAfgAZAXB0ABpPU0NhY2hlTm90aWZpY2F0aW9uU2VydmljZXQATW9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuZXhjZXB0aW9uaGFuZGxpbmcuRGVmYXVsdE1lc3NhZ2VFeGNlcHRpb25IYW5kbGVyc3EAfgAf//////////9zcQB+ABwAAAADc3EAfgAZAHEAfgAycHNyAAxqYXZhLm5ldC5VUkyWJTc2GvzkcgMAB0kACGhhc2hDb2RlSQAEcG9ydEwACWF1dGhvcml0eXEAfgAETAAEZmlsZXEAfgAETAAEaG9zdHEAfgAETAAIcHJvdG9jb2xxAH4ABEwAA3JlZnEAfgAEeHBpG6KgAAAfkHQADmxvY2FsaG9zdDo4MDgwdAArL2tyLWRldi9yZW1vdGluZy9PU0NhY2hlTm90aWZpY2F0aW9uU2VydmljZXQACWxvY2FsaG9zdHQABGh0dHBweHQAAHQABlRSQVZFTHQAAS9zcgATamF2YS51dGlsLkFycmF5TGlzdHiB0h2Zx2GdAwABSQAEc2l6ZXhwAAAABXcEAAAACnQAM29yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuc2VydmljZS5LU0JKYXZhU2VydmljZXQAPGNvbS5vcGVuc3ltcGhvbnkub3NjYWNoZS5iYXNlLmV2ZW50cy5DYWNoZUVudHJ5RXZlbnRMaXN0ZW5lcnQAN2NvbS5vcGVuc3ltcGhvbnkub3NjYWNoZS5iYXNlLmV2ZW50cy5DYWNoZUV2ZW50TGlzdGVuZXJ0ABdqYXZhLnV0aWwuRXZlbnRMaXN0ZW5lcnQALGNvbS5vcGVuc3ltcGhvbnkub3NjYWNoZS5iYXNlLkxpZmVjeWNsZUF3YXJleHEAfgAjdAAGVFJBVkVM';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
INSERT INTO KRSB_MSG_PYLD_T (MSG_PYLD,MSG_QUE_ID)
  VALUES (EMPTY_CLOB(),462)
/
-- Length: 6084
-- Chunks: 2
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 462    FOR UPDATE;        
    buffer := 'rO0ABXNyAC1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLkFzeW5jaHJvbm91c0NhbGzxnQ5Y0t0lZQIAB1oAFWlnbm9yZVN0b3JlQW5kRm9yd2FyZFsACWFyZ3VtZW50c3QAE1tMamF2YS9sYW5nL09iamVjdDtMAAhjYWxsYmFja3QAM0xvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL0FzeW5jaHJvbm91c0NhbGxiYWNrO0wAB2NvbnRleHR0ABZMamF2YS9pby9TZXJpYWxpemFibGU7TAAKbWV0aG9kTmFtZXQAEkxqYXZhL2xhbmcvU3RyaW5nO1sACnBhcmFtVHlwZXN0ABJbTGphdmEvbGFuZy9DbGFzcztMAAtzZXJ2aWNlSW5mb3QAKkxvcmcva3VhbGkvcmljZS9rc2IvbWVzc2FnaW5nL1NlcnZpY2VJbmZvO3hwAHVyABNbTGphdmEubGFuZy5PYmplY3Q7kM5YnxBzKWwCAAB4cAAAAAFzcgBDY29tLm9wZW5zeW1waG9ueS5vc2NhY2hlLnBsdWdpbnMuY2x1c3RlcnN1cHBvcnQuQ2x1c3Rlck5vdGlmaWNhdGlvbhY+6VckvG9pAgACSQAEdHlwZUwABGRhdGFxAH4AA3hwAAAAAXQAIERvY3VtZW50VHlwZTpDdXJyZW50Um9vdHNJbkNhY2hlcHB0AAZpbnZva2V1cgASW0xqYXZhLmxhbmcuQ2xhc3M7qxbXrsvNWpkCAAB4cAAAAAF2cgAUamF2YS5pby5TZXJpYWxpemFibGUQm2EN15tk7QIAAHhwc3IAKG9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuU2VydmljZUluZm/FFyO2KQS3ugIAC0wABWFsaXZldAATTGphdmEvbGFuZy9Cb29sZWFuO0wAFGVuZHBvaW50QWx0ZXJuYXRlVXJscQB+AARMAAtlbmRwb2ludFVybHEAfgAETAAKbG9ja1Zlck5icnQAE0xqYXZhL2xhbmcvSW50ZWdlcjtMAA5tZXNzYWdlRW50cnlJZHQAEExqYXZhL2xhbmcvTG9uZztMAAVxbmFtZXQAG0xqYXZheC94bWwvbmFtZXNwYWNlL1FOYW1lO0wAGnNlcmlhbGl6ZWRTZXJ2aWNlTmFtZXNwYWNlcQB+AARMAAhzZXJ2ZXJJcHEAfgAETAARc2VydmljZURlZmluaXRpb250ADBMb3JnL2t1YWxpL3JpY2Uva3NiL21lc3NhZ2luZy9TZXJ2aWNlRGVmaW5pdGlvbjtMAAtzZXJ2aWNlTmFtZXEAfgAETAAQc2VydmljZU5hbWVzcGFjZXEAfgAEeHBzcgARamF2YS5sYW5nLkJvb2xlYW7NIHKA1Zz67gIAAVoABXZhbHVleHABcHQAQGh0dHA6Ly9sb2NhbGhvc3Q6ODA4MC9rci1kZXYvcmVtb3RpbmcvT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2VzcgARamF2YS5sYW5nLkludGVnZXIS4qCk94GHOAIAAUkABXZhbHVleHIAEGphdmEubGFuZy5OdW1iZXKGrJUdC5TgiwIAAHhwAAAAAXNyAA5qYXZhLmxhbmcuTG9uZzuL5JDMjyPfAgABSgAFdmFsdWV4cQB+AB0AAAAAAAAPfXNyABlqYXZheC54bWwubmFtZXNwYWNlLlFOYW1lgW2oLfw73WwCAANMAAlsb2NhbFBhcnRxAH4ABEwADG5hbWVzcGFjZVVSSXEAfgAETAAGcHJlZml4cQB+AAR4cHQAGk9TQ2FjaGVOb3RpZmljYXRpb25TZXJ2aWNldAAAcQB+ACR0B3RyTzBBQlhOeUFESnZjbWN1YTNWaGJHa3VjbWxqWlM1cmMySXViV1Z6YzJGbmFXNW5Ma3BoZG1GVFpYSjJhV05sUkdWbWFXNXBkR2x2YnJibkRWOUJOR3p4QWdBQlRBQVJjMlZ5ZG1salpVbHVkR1Z5Wm1GalpYTjBBQkJNYW1GMllTOTFkR2xzTDB4cGMzUTdlSElBTG05eVp5NXJkV0ZzYVM1eWFXTmxMbXR6WWk1dFpYTnpZV2RwYm1jdVUyVnlkbWxqWlVSbFptbHVhWFJwYjI0QW13SlBXTjBwZmdJQURVd0FDMkoxYzFObFkzVnlhWFI1ZEFBVFRHcGhkbUV2YkdGdVp5OUNiMjlzWldGdU8wd0FEMk55WldSbGJuUnBZV3h6Vkhsd1pYUUFURXh2Y21jdmEzVmhiR2t2Y21salpTOWpiM0psTDNObFkzVnlhWFI1TDJOeVpXUmxiblJwWVd4ekwwTnlaV1JsYm5ScFlXeHpVMjkxY21ObEpFTnlaV1JsYm5ScFlXeHpWSGx3WlR0TUFCQnNiMk5oYkZObGNuWnBZMlZPWVcxbGRBQVNUR3BoZG1FdmJHRnVaeTlUZEhKcGJtYzdUQUFYYldWemMyRm5aVVY0WTJWd2RHbHZia2hoYm1Sc1pYSnhBSDRBQlV3QURHMXBiR3hwYzFSdlRHbDJaWFFBRUV4cVlYWmhMMnhoYm1jdlRHOXVaenRNQUFod2NtbHZjbWwwZVhRQUUweHFZWFpoTDJ4aGJtY3ZTVzUwWldkbGNqdE1BQVZ4ZFdWMVpYRUFmZ0FEVEFBTmNtVjBjbmxCZEhSbGJYQjBjM0VBZmdBSFRBQUhjMlZ5ZG1salpYUUFFa3hxWVhaaEwyeGhibWN2VDJKcVpXTjBPMHdBRDNObGNuWnBZMlZGYm1SUWIybHVkSFFBRGt4cVlYWmhMMjVsZEM5VlVrdzdUQUFUYzJWeWRtbGpaVTVoYldWVGNHRmpaVlZTU1hFQWZnQUZUQUFRYzJWeWRtbGpaVTVoYldWemNHRmpaWEVBZmdBRlRBQUxjMlZ5ZG1salpWQmhkR2h4QUg0QUJYaHdjM0lBRVdwaGRtRXViR0Z1Wnk1Q2IyOXNaV0Z1elNCeWdOV2MrdTRDQUFGYUFBVjJZV3gxWlhod0FYQjBBQnBQVTBOaFkyaGxUbTkwYVdacFkyRjBhVzl1VTJWeWRtbGpaWFFBVFc5eVp5NXJkV0ZzYVM1eWFXTmxMbXR6WWk1dFpYTnpZV2RwYm1jdVpYaGpaWEIwYVc5dWFHRnVaR3hwYm1jdVJHVm1ZWFZzZEUxbGMzTmhaMlZGZUdObGNIUnBiMjVJWVc1a2JHVnljM0lBRG1waGRtRXViR0Z1Wnk1TWIyNW5PNHZra015UEk5OENBQUZLQUFWMllXeDFaWGh5QUJCcVlYWmhMbXhoYm1jdVRuVnRZbVZ5aHF5VkhRdVU0SXNDQUFCNGNQLy8vLy8vLy8vL2MzSUFFV3BoZG1FdWJHRnVaeTVKYm5SbFoyVnlFdUtncFBlQmh6Z0NBQUZKQUFWMllXeDFaWGh4QUg0QUVBQUFBQU56Y1FCK0FBc0FjUUIrQUJOd2MzSUFER3BoZG1FdWJtVjBMbFZTVEpZbE56WWEvT1J5QXdBSFNRQUlhR0Z6YUVOdlpHVkpBQVJ3YjNKMFRBQUpZWFYwYUc5eWFYUjVjUUIrQUFWTUFBUm1hV3hsY1FCK0FBVk1BQVJvYjNOMGNRQitBQVZNQUFod2NtOTBiMk52YkhFQWZnQUZUQUFEY21WbWNRQitBQVY0Y1AvLy8vOEFBQitRZEFBT2JHOWpZV3hvYjNOME9qZ3dPREIwQUNzdmEzSXRaR1YyTDNKbGJXOTBhVzVuTDA5VFEyRmphR1ZPYjNScFptbGpZWFJwYjI1VFpYSjJhV05sZEFBSmJHOWpZV3hvYjNOMGRBQUVhSFIwY0hCNGRBQUFkQUFHVkZKQlZrVk1kQUFCTDNOeUFCTnFZWFpoTG5WMGFXd3VRWEp5WVhsTWFYTjBlSUhTSFpuSFlaMERBQUZKQUFSemFYcGxlSEFBQUFBRmR3UUFBQUFLZEFBemIzSm5MbXQxWVd4cExuSnBZMlV1';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
DECLARE    data CLOB; buffer VARCHAR2(32000);
BEGIN
    SELECT MSG_PYLD INTO data FROM KRSB_MSG_PYLD_T 
    WHERE 
 MSG_QUE_ID = 462    FOR UPDATE;        
    buffer := 'YTNOaUxtMWxjM05oWjJsdVp5NXpaWEoyYVdObExrdFRRa3BoZG1GVFpYSjJhV05sZEFBOFkyOXRMbTl3Wlc1emVXMXdhRzl1ZVM1dmMyTmhZMmhsTG1KaGMyVXVaWFpsYm5SekxrTmhZMmhsUlc1MGNubEZkbVZ1ZEV4cGMzUmxibVZ5ZEFBM1kyOXRMbTl3Wlc1emVXMXdhRzl1ZVM1dmMyTmhZMmhsTG1KaGMyVXVaWFpsYm5SekxrTmhZMmhsUlhabGJuUk1hWE4wWlc1bGNuUUFGMnBoZG1FdWRYUnBiQzVGZG1WdWRFeHBjM1JsYm1WeWRBQXNZMjl0TG05d1pXNXplVzF3YUc5dWVTNXZjMk5oWTJobExtSmhjMlV1VEdsbVpXTjVZMnhsUVhkaGNtVjR0AA0xMjkuMTg2Ljc5LjQ1c3IAMm9yZy5rdWFsaS5yaWNlLmtzYi5tZXNzYWdpbmcuSmF2YVNlcnZpY2VEZWZpbml0aW9utucNX0E0bPECAAFMABFzZXJ2aWNlSW50ZXJmYWNlc3QAEExqYXZhL3V0aWwvTGlzdDt4cgAub3JnLmt1YWxpLnJpY2Uua3NiLm1lc3NhZ2luZy5TZXJ2aWNlRGVmaW5pdGlvbgCbAk9Y3Sl+AgANTAALYnVzU2VjdXJpdHlxAH4AE0wAD2NyZWRlbnRpYWxzVHlwZXQATExvcmcva3VhbGkvcmljZS9jb3JlL3NlY3VyaXR5L2NyZWRlbnRpYWxzL0NyZWRlbnRpYWxzU291cmNlJENyZWRlbnRpYWxzVHlwZTtMABBsb2NhbFNlcnZpY2VOYW1lcQB+AARMABdtZXNzYWdlRXhjZXB0aW9uSGFuZGxlcnEAfgAETAAMbWlsbGlzVG9MaXZlcQB+ABVMAAhwcmlvcml0eXEAfgAUTAAFcXVldWVxAH4AE0wADXJldHJ5QXR0ZW1wdHNxAH4AFEwAB3NlcnZpY2V0ABJMamF2YS9sYW5nL09iamVjdDtMAA9zZXJ2aWNlRW5kUG9pbnR0AA5MamF2YS9uZXQvVVJMO0wAE3NlcnZpY2VOYW1lU3BhY2VVUklxAH4ABEwAEHNlcnZpY2VOYW1lc3BhY2VxAH4ABEwAC3NlcnZpY2VQYXRocQB+AAR4cHNxAH4AGQFwdAAaT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2V0AE1vcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLmV4Y2VwdGlvbmhhbmRsaW5nLkRlZmF1bHRNZXNzYWdlRXhjZXB0aW9uSGFuZGxlcnNxAH4AH///////////c3EAfgAcAAAAA3NxAH4AGQBxAH4AMnBzcgAMamF2YS5uZXQuVVJMliU3Nhr85HIDAAdJAAhoYXNoQ29kZUkABHBvcnRMAAlhdXRob3JpdHlxAH4ABEwABGZpbGVxAH4ABEwABGhvc3RxAH4ABEwACHByb3RvY29scQB+AARMAANyZWZxAH4ABHhwaRuioAAAH5B0AA5sb2NhbGhvc3Q6ODA4MHQAKy9rci1kZXYvcmVtb3RpbmcvT1NDYWNoZU5vdGlmaWNhdGlvblNlcnZpY2V0AAlsb2NhbGhvc3R0AARodHRwcHh0AAB0AAZUUkFWRUx0AAEvc3IAE2phdmEudXRpbC5BcnJheUxpc3R4gdIdmcdhnQMAAUkABHNpemV4cAAAAAV3BAAAAAp0ADNvcmcua3VhbGkucmljZS5rc2IubWVzc2FnaW5nLnNlcnZpY2UuS1NCSmF2YVNlcnZpY2V0ADxjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5ldmVudHMuQ2FjaGVFbnRyeUV2ZW50TGlzdGVuZXJ0ADdjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5ldmVudHMuQ2FjaGVFdmVudExpc3RlbmVydAAXamF2YS51dGlsLkV2ZW50TGlzdGVuZXJ0ACxjb20ub3BlbnN5bXBob255Lm9zY2FjaGUuYmFzZS5MaWZlY3ljbGVBd2FyZXhxAH4AI3QABlRSQVZFTA==';
    DBMS_LOB.writeappend(data,LENGTH(buffer),buffer);
END;
/
--
-- KRSB_MSG_QUE_T
--


INSERT INTO KRSB_MSG_QUE_T (DT,IP_NBR,MSG_QUE_ID,PRIO,RTRY_CNT,STAT_CD,SVC_MTHD_NM,SVC_NM,SVC_NMSPC,VER_NBR)
  VALUES (TO_DATE( '20090915140215', 'YYYYMMDDHH24MISS' ),'129.186.79.45',361,3,0,'R','invoke','OSCacheNotificationService','TRAVEL',1)
/
INSERT INTO KRSB_MSG_QUE_T (DT,IP_NBR,MSG_QUE_ID,PRIO,RTRY_CNT,STAT_CD,SVC_MTHD_NM,SVC_NM,SVC_NMSPC,VER_NBR)
  VALUES (TO_DATE( '20090915140215', 'YYYYMMDDHH24MISS' ),'129.186.79.45',362,3,0,'R','invoke','OSCacheNotificationService','TRAVEL',1)
/
INSERT INTO KRSB_MSG_QUE_T (DT,IP_NBR,MSG_QUE_ID,PRIO,RTRY_CNT,STAT_CD,SVC_MTHD_NM,SVC_NM,SVC_NMSPC,VER_NBR)
  VALUES (TO_DATE( '20090915140215', 'YYYYMMDDHH24MISS' ),'129.186.79.45',363,3,0,'R','invoke','OSCacheNotificationService','TRAVEL',1)
/
INSERT INTO KRSB_MSG_QUE_T (DT,IP_NBR,MSG_QUE_ID,PRIO,RTRY_CNT,STAT_CD,SVC_MTHD_NM,SVC_NM,SVC_NMSPC,VER_NBR)
  VALUES (TO_DATE( '20090915140215', 'YYYYMMDDHH24MISS' ),'129.186.79.45',364,3,0,'R','invoke','OSCacheNotificationService','TRAVEL',1)
/
INSERT INTO KRSB_MSG_QUE_T (DT,IP_NBR,MSG_QUE_ID,PRIO,RTRY_CNT,STAT_CD,SVC_MTHD_NM,SVC_NM,SVC_NMSPC,VER_NBR)
  VALUES (TO_DATE( '20090915140216', 'YYYYMMDDHH24MISS' ),'129.186.79.45',365,3,0,'R','invoke','OSCacheNotificationService','TRAVEL',1)
/
INSERT INTO KRSB_MSG_QUE_T (DT,IP_NBR,MSG_QUE_ID,PRIO,RTRY_CNT,STAT_CD,SVC_MTHD_NM,SVC_NM,SVC_NMSPC,VER_NBR)
  VALUES (TO_DATE( '20090915140216', 'YYYYMMDDHH24MISS' ),'129.186.79.45',366,3,0,'R','invoke','OSCacheNotificationService','TRAVEL',1)
/
INSERT INTO KRSB_MSG_QUE_T (DT,IP_NBR,MSG_QUE_ID,PRIO,RTRY_CNT,STAT_CD,SVC_MTHD_NM,SVC_NM,SVC_NMSPC,VER_NBR)
  VALUES (TO_DATE( '20090915140216', 'YYYYMMDDHH24MISS' ),'129.186.79.45',367,3,0,'R','invoke','OSCacheNotificationService','TRAVEL',1)
/
INSERT INTO KRSB_MSG_QUE_T (DT,IP_NBR,MSG_QUE_ID,PRIO,RTRY_CNT,STAT_CD,SVC_MTHD_NM,SVC_NM,SVC_NMSPC,VER_NBR)
  VALUES (TO_DATE( '20090915140216', 'YYYYMMDDHH24MISS' ),'129.186.79.45',368,3,0,'R','invoke','OSCacheNotificationService','TRAVEL',1)
/
INSERT INTO KRSB_MSG_QUE_T (DT,IP_NBR,MSG_QUE_ID,PRIO,RTRY_CNT,STAT_CD,SVC_MTHD_NM,SVC_NM,SVC_NMSPC,VER_NBR)
  VALUES (TO_DATE( '20090915140216', 'YYYYMMDDHH24MISS' ),'129.186.79.45',369,3,0,'R','invoke','OSCacheNotificationService','TRAVEL',1)
/
INSERT INTO KRSB_MSG_QUE_T (DT,IP_NBR,MSG_QUE_ID,PRIO,RTRY_CNT,STAT_CD,SVC_MTHD_NM,SVC_NM,SVC_NMSPC,VER_NBR)
  VALUES (TO_DATE( '20090915140216', 'YYYYMMDDHH24MISS' ),'129.186.79.45',370,3,0,'R','invoke','OSCacheNotificationService','TRAVEL',1)
/
INSERT INTO KRSB_MSG_QUE_T (DT,IP_NBR,MSG_QUE_ID,PRIO,RTRY_CNT,STAT_CD,SVC_MTHD_NM,SVC_NM,SVC_NMSPC,VER_NBR)
  VALUES (TO_DATE( '20090915140218', 'YYYYMMDDHH24MISS' ),'129.186.79.45',371,3,0,'R','invoke','OSCacheNotificationService','TRAVEL',1)
/
INSERT INTO KRSB_MSG_QUE_T (DT,IP_NBR,MSG_QUE_ID,PRIO,RTRY_CNT,STAT_CD,SVC_MTHD_NM,SVC_NM,SVC_NMSPC,VER_NBR)
  VALUES (TO_DATE( '20090915140218', 'YYYYMMDDHH24MISS' ),'129.186.79.45',372,3,0,'R','invoke','OSCacheNotificationService','TRAVEL',1)
/
INSERT INTO KRSB_MSG_QUE_T (DT,IP_NBR,MSG_QUE_ID,PRIO,RTRY_CNT,STAT_CD,SVC_MTHD_NM,SVC_NM,SVC_NMSPC,VER_NBR)
  VALUES (TO_DATE( '20090915140218', 'YYYYMMDDHH24MISS' ),'129.186.79.45',373,3,0,'R','invoke','OSCacheNotificationService','TRAVEL',1)
/
INSERT INTO KRSB_MSG_QUE_T (DT,IP_NBR,MSG_QUE_ID,PRIO,RTRY_CNT,STAT_CD,SVC_MTHD_NM,SVC_NM,SVC_NMSPC,VER_NBR)
  VALUES (TO_DATE( '20090915140218', 'YYYYMMDDHH24MISS' ),'129.186.79.45',374,3,0,'R','invoke','OSCacheNotificationService','TRAVEL',1)
/
INSERT INTO KRSB_MSG_QUE_T (DT,IP_NBR,MSG_QUE_ID,PRIO,RTRY_CNT,STAT_CD,SVC_MTHD_NM,SVC_NM,SVC_NMSPC,VER_NBR)
  VALUES (TO_DATE( '20090915140218', 'YYYYMMDDHH24MISS' ),'129.186.79.45',375,3,0,'R','invoke','OSCacheNotificationService','TRAVEL',1)
/
INSERT INTO KRSB_MSG_QUE_T (DT,IP_NBR,MSG_QUE_ID,PRIO,RTRY_CNT,STAT_CD,SVC_MTHD_NM,SVC_NM,SVC_NMSPC,VER_NBR)
  VALUES (TO_DATE( '20090915140219', 'YYYYMMDDHH24MISS' ),'129.186.79.45',376,3,0,'R','invoke','OSCacheNotificationService','TRAVEL',1)
/
INSERT INTO KRSB_MSG_QUE_T (DT,IP_NBR,MSG_QUE_ID,PRIO,RTRY_CNT,STAT_CD,SVC_MTHD_NM,SVC_NM,SVC_NMSPC,VER_NBR)
  VALUES (TO_DATE( '20090915140219', 'YYYYMMDDHH24MISS' ),'129.186.79.45',377,3,0,'R','invoke','OSCacheNotificationService','TRAVEL',1)
/
INSERT INTO KRSB_MSG_QUE_T (DT,IP_NBR,MSG_QUE_ID,PRIO,RTRY_CNT,STAT_CD,SVC_MTHD_NM,SVC_NM,SVC_NMSPC,VER_NBR)
  VALUES (TO_DATE( '20090915140219', 'YYYYMMDDHH24MISS' ),'129.186.79.45',378,3,0,'R','invoke','OSCacheNotificationService','TRAVEL',1)
/
INSERT INTO KRSB_MSG_QUE_T (DT,IP_NBR,MSG_QUE_ID,PRIO,RTRY_CNT,STAT_CD,SVC_MTHD_NM,SVC_NM,SVC_NMSPC,VER_NBR)
  VALUES (TO_DATE( '20090915140219', 'YYYYMMDDHH24MISS' ),'129.186.79.45',379,3,0,'R','invoke','OSCacheNotificationService','TRAVEL',1)
/
INSERT INTO KRSB_MSG_QUE_T (DT,IP_NBR,MSG_QUE_ID,PRIO,RTRY_CNT,STAT_CD,SVC_MTHD_NM,SVC_NM,SVC_NMSPC,VER_NBR)
  VALUES (TO_DATE( '20090915140219', 'YYYYMMDDHH24MISS' ),'129.186.79.45',380,3,0,'R','invoke','OSCacheNotificationService','TRAVEL',1)
/
INSERT INTO KRSB_MSG_QUE_T (DT,IP_NBR,MSG_QUE_ID,PRIO,RTRY_CNT,STAT_CD,SVC_MTHD_NM,SVC_NM,SVC_NMSPC,VER_NBR)
  VALUES (TO_DATE( '20090915140220', 'YYYYMMDDHH24MISS' ),'129.186.79.45',381,3,0,'R','invoke','OSCacheNotificationService','TRAVEL',1)
/
INSERT INTO KRSB_MSG_QUE_T (DT,IP_NBR,MSG_QUE_ID,PRIO,RTRY_CNT,STAT_CD,SVC_MTHD_NM,SVC_NM,SVC_NMSPC,VER_NBR)
  VALUES (TO_DATE( '20090915140220', 'YYYYMMDDHH24MISS' ),'129.186.79.45',382,3,0,'R','invoke','OSCacheNotificationService','TRAVEL',1)
/
INSERT INTO KRSB_MSG_QUE_T (DT,IP_NBR,MSG_QUE_ID,PRIO,RTRY_CNT,STAT_CD,SVC_MTHD_NM,SVC_NM,SVC_NMSPC,VER_NBR)
  VALUES (TO_DATE( '20090915140220', 'YYYYMMDDHH24MISS' ),'129.186.79.45',383,3,0,'R','invoke','OSCacheNotificationService','TRAVEL',1)
/
INSERT INTO KRSB_MSG_QUE_T (DT,IP_NBR,MSG_QUE_ID,PRIO,RTRY_CNT,STAT_CD,SVC_MTHD_NM,SVC_NM,SVC_NMSPC,VER_NBR)
  VALUES (TO_DATE( '20090915140220', 'YYYYMMDDHH24MISS' ),'129.186.79.45',384,3,0,'R','invoke','OSCacheNotificationService','TRAVEL',1)
/
INSERT INTO KRSB_MSG_QUE_T (DT,IP_NBR,MSG_QUE_ID,PRIO,RTRY_CNT,STAT_CD,SVC_MTHD_NM,SVC_NM,SVC_NMSPC,VER_NBR)
  VALUES (TO_DATE( '20090915140220', 'YYYYMMDDHH24MISS' ),'129.186.79.45',385,3,0,'R','invoke','OSCacheNotificationService','TRAVEL',1)
/
INSERT INTO KRSB_MSG_QUE_T (DT,IP_NBR,MSG_QUE_ID,PRIO,RTRY_CNT,STAT_CD,SVC_MTHD_NM,SVC_NM,SVC_NMSPC,VER_NBR)
  VALUES (TO_DATE( '20090915140220', 'YYYYMMDDHH24MISS' ),'129.186.79.45',386,3,0,'R','invoke','OSCacheNotificationService','TRAVEL',1)
/
INSERT INTO KRSB_MSG_QUE_T (DT,IP_NBR,MSG_QUE_ID,PRIO,RTRY_CNT,STAT_CD,SVC_MTHD_NM,SVC_NM,SVC_NMSPC,VER_NBR)
  VALUES (TO_DATE( '20090915140221', 'YYYYMMDDHH24MISS' ),'129.186.79.45',387,3,0,'R','invoke','OSCacheNotificationService','TRAVEL',1)
/
INSERT INTO KRSB_MSG_QUE_T (DT,IP_NBR,MSG_QUE_ID,PRIO,RTRY_CNT,STAT_CD,SVC_MTHD_NM,SVC_NM,SVC_NMSPC,VER_NBR)
  VALUES (TO_DATE( '20090915140221', 'YYYYMMDDHH24MISS' ),'129.186.79.45',388,3,0,'R','invoke','OSCacheNotificationService','TRAVEL',1)
/
INSERT INTO KRSB_MSG_QUE_T (DT,IP_NBR,MSG_QUE_ID,PRIO,RTRY_CNT,STAT_CD,SVC_MTHD_NM,SVC_NM,SVC_NMSPC,VER_NBR)
  VALUES (TO_DATE( '20090915140221', 'YYYYMMDDHH24MISS' ),'129.186.79.45',389,3,0,'R','invoke','OSCacheNotificationService','TRAVEL',1)
/
INSERT INTO KRSB_MSG_QUE_T (DT,IP_NBR,MSG_QUE_ID,PRIO,RTRY_CNT,STAT_CD,SVC_MTHD_NM,SVC_NM,SVC_NMSPC,VER_NBR)
  VALUES (TO_DATE( '20090915140221', 'YYYYMMDDHH24MISS' ),'129.186.79.45',390,3,0,'R','invoke','OSCacheNotificationService','TRAVEL',1)
/
INSERT INTO KRSB_MSG_QUE_T (DT,IP_NBR,MSG_QUE_ID,PRIO,RTRY_CNT,STAT_CD,SVC_MTHD_NM,SVC_NM,SVC_NMSPC,VER_NBR)
  VALUES (TO_DATE( '20090915140221', 'YYYYMMDDHH24MISS' ),'129.186.79.45',391,3,0,'R','invoke','OSCacheNotificationService','TRAVEL',1)
/
INSERT INTO KRSB_MSG_QUE_T (DT,IP_NBR,MSG_QUE_ID,PRIO,RTRY_CNT,STAT_CD,SVC_MTHD_NM,SVC_NM,SVC_NMSPC,VER_NBR)
  VALUES (TO_DATE( '20090915140221', 'YYYYMMDDHH24MISS' ),'129.186.79.45',392,3,0,'R','invoke','OSCacheNotificationService','TRAVEL',1)
/
INSERT INTO KRSB_MSG_QUE_T (DT,IP_NBR,MSG_QUE_ID,PRIO,RTRY_CNT,STAT_CD,SVC_MTHD_NM,SVC_NM,SVC_NMSPC,VER_NBR)
  VALUES (TO_DATE( '20090915140222', 'YYYYMMDDHH24MISS' ),'129.186.79.45',393,3,0,'R','invoke','OSCacheNotificationService','TRAVEL',1)
/
INSERT INTO KRSB_MSG_QUE_T (DT,IP_NBR,MSG_QUE_ID,PRIO,RTRY_CNT,STAT_CD,SVC_MTHD_NM,SVC_NM,SVC_NMSPC,VER_NBR)
  VALUES (TO_DATE( '20090915140222', 'YYYYMMDDHH24MISS' ),'129.186.79.45',394,3,0,'R','invoke','OSCacheNotificationService','TRAVEL',1)
/
INSERT INTO KRSB_MSG_QUE_T (DT,IP_NBR,MSG_QUE_ID,PRIO,RTRY_CNT,STAT_CD,SVC_MTHD_NM,SVC_NM,SVC_NMSPC,VER_NBR)
  VALUES (TO_DATE( '20090915140222', 'YYYYMMDDHH24MISS' ),'129.186.79.45',395,3,0,'R','invoke','OSCacheNotificationService','TRAVEL',1)
/
INSERT INTO KRSB_MSG_QUE_T (DT,IP_NBR,MSG_QUE_ID,PRIO,RTRY_CNT,STAT_CD,SVC_MTHD_NM,SVC_NM,SVC_NMSPC,VER_NBR)
  VALUES (TO_DATE( '20090915140222', 'YYYYMMDDHH24MISS' ),'129.186.79.45',396,3,0,'R','invoke','OSCacheNotificationService','TRAVEL',1)
/
INSERT INTO KRSB_MSG_QUE_T (DT,IP_NBR,MSG_QUE_ID,PRIO,RTRY_CNT,STAT_CD,SVC_MTHD_NM,SVC_NM,SVC_NMSPC,VER_NBR)
  VALUES (TO_DATE( '20090915140222', 'YYYYMMDDHH24MISS' ),'129.186.79.45',397,3,0,'R','invoke','OSCacheNotificationService','TRAVEL',1)
/
INSERT INTO KRSB_MSG_QUE_T (DT,IP_NBR,MSG_QUE_ID,PRIO,RTRY_CNT,STAT_CD,SVC_MTHD_NM,SVC_NM,SVC_NMSPC,VER_NBR)
  VALUES (TO_DATE( '20090915140222', 'YYYYMMDDHH24MISS' ),'129.186.79.45',398,3,0,'R','invoke','OSCacheNotificationService','TRAVEL',1)
/
INSERT INTO KRSB_MSG_QUE_T (DT,IP_NBR,MSG_QUE_ID,PRIO,RTRY_CNT,STAT_CD,SVC_MTHD_NM,SVC_NM,SVC_NMSPC,VER_NBR)
  VALUES (TO_DATE( '20090915140223', 'YYYYMMDDHH24MISS' ),'129.186.79.45',399,3,0,'R','invoke','OSCacheNotificationService','TRAVEL',1)
/
INSERT INTO KRSB_MSG_QUE_T (DT,IP_NBR,MSG_QUE_ID,PRIO,RTRY_CNT,STAT_CD,SVC_MTHD_NM,SVC_NM,SVC_NMSPC,VER_NBR)
  VALUES (TO_DATE( '20090915140223', 'YYYYMMDDHH24MISS' ),'129.186.79.45',400,3,0,'R','invoke','OSCacheNotificationService','TRAVEL',1)
/
INSERT INTO KRSB_MSG_QUE_T (DT,IP_NBR,MSG_QUE_ID,PRIO,RTRY_CNT,STAT_CD,SVC_MTHD_NM,SVC_NM,SVC_NMSPC,VER_NBR)
  VALUES (TO_DATE( '20090915140224', 'YYYYMMDDHH24MISS' ),'129.186.79.45',401,3,0,'R','invoke','OSCacheNotificationService','TRAVEL',1)
/
INSERT INTO KRSB_MSG_QUE_T (DT,IP_NBR,MSG_QUE_ID,PRIO,RTRY_CNT,STAT_CD,SVC_MTHD_NM,SVC_NM,SVC_NMSPC,VER_NBR)
  VALUES (TO_DATE( '20090915140224', 'YYYYMMDDHH24MISS' ),'129.186.79.45',402,3,0,'R','invoke','OSCacheNotificationService','TRAVEL',1)
/
INSERT INTO KRSB_MSG_QUE_T (DT,IP_NBR,MSG_QUE_ID,PRIO,RTRY_CNT,STAT_CD,SVC_MTHD_NM,SVC_NM,SVC_NMSPC,VER_NBR)
  VALUES (TO_DATE( '20090915140224', 'YYYYMMDDHH24MISS' ),'129.186.79.45',403,3,0,'R','invoke','OSCacheNotificationService','TRAVEL',1)
/
INSERT INTO KRSB_MSG_QUE_T (DT,IP_NBR,MSG_QUE_ID,PRIO,RTRY_CNT,STAT_CD,SVC_MTHD_NM,SVC_NM,SVC_NMSPC,VER_NBR)
  VALUES (TO_DATE( '20090915140224', 'YYYYMMDDHH24MISS' ),'129.186.79.45',404,3,0,'R','invoke','OSCacheNotificationService','TRAVEL',1)
/
INSERT INTO KRSB_MSG_QUE_T (DT,IP_NBR,MSG_QUE_ID,PRIO,RTRY_CNT,STAT_CD,SVC_MTHD_NM,SVC_NM,SVC_NMSPC,VER_NBR)
  VALUES (TO_DATE( '20090915140224', 'YYYYMMDDHH24MISS' ),'129.186.79.45',405,3,0,'R','invoke','OSCacheNotificationService','TRAVEL',1)
/
INSERT INTO KRSB_MSG_QUE_T (DT,IP_NBR,MSG_QUE_ID,PRIO,RTRY_CNT,STAT_CD,SVC_MTHD_NM,SVC_NM,SVC_NMSPC,VER_NBR)
  VALUES (TO_DATE( '20090915140225', 'YYYYMMDDHH24MISS' ),'129.186.79.45',406,3,0,'R','invoke','OSCacheNotificationService','TRAVEL',1)
/
INSERT INTO KRSB_MSG_QUE_T (DT,IP_NBR,MSG_QUE_ID,PRIO,RTRY_CNT,STAT_CD,SVC_MTHD_NM,SVC_NM,SVC_NMSPC,VER_NBR)
  VALUES (TO_DATE( '20090915140225', 'YYYYMMDDHH24MISS' ),'129.186.79.45',407,3,0,'R','invoke','OSCacheNotificationService','TRAVEL',1)
/
INSERT INTO KRSB_MSG_QUE_T (DT,IP_NBR,MSG_QUE_ID,PRIO,RTRY_CNT,STAT_CD,SVC_MTHD_NM,SVC_NM,SVC_NMSPC,VER_NBR)
  VALUES (TO_DATE( '20090915140225', 'YYYYMMDDHH24MISS' ),'129.186.79.45',408,3,0,'R','invoke','OSCacheNotificationService','TRAVEL',1)
/
INSERT INTO KRSB_MSG_QUE_T (DT,IP_NBR,MSG_QUE_ID,PRIO,RTRY_CNT,STAT_CD,SVC_MTHD_NM,SVC_NM,SVC_NMSPC,VER_NBR)
  VALUES (TO_DATE( '20090915140225', 'YYYYMMDDHH24MISS' ),'129.186.79.45',409,3,0,'R','invoke','OSCacheNotificationService','TRAVEL',1)
/
INSERT INTO KRSB_MSG_QUE_T (DT,IP_NBR,MSG_QUE_ID,PRIO,RTRY_CNT,STAT_CD,SVC_MTHD_NM,SVC_NM,SVC_NMSPC,VER_NBR)
  VALUES (TO_DATE( '20090915140225', 'YYYYMMDDHH24MISS' ),'129.186.79.45',410,3,0,'R','invoke','OSCacheNotificationService','TRAVEL',1)
/
INSERT INTO KRSB_MSG_QUE_T (DT,IP_NBR,MSG_QUE_ID,PRIO,RTRY_CNT,STAT_CD,SVC_MTHD_NM,SVC_NM,SVC_NMSPC,VER_NBR)
  VALUES (TO_DATE( '20090915140225', 'YYYYMMDDHH24MISS' ),'129.186.79.45',411,3,0,'R','invoke','OSCacheNotificationService','TRAVEL',1)
/
INSERT INTO KRSB_MSG_QUE_T (DT,IP_NBR,MSG_QUE_ID,PRIO,RTRY_CNT,STAT_CD,SVC_MTHD_NM,SVC_NM,SVC_NMSPC,VER_NBR)
  VALUES (TO_DATE( '20090915140226', 'YYYYMMDDHH24MISS' ),'129.186.79.45',412,3,0,'R','invoke','OSCacheNotificationService','TRAVEL',1)
/
INSERT INTO KRSB_MSG_QUE_T (DT,IP_NBR,MSG_QUE_ID,PRIO,RTRY_CNT,STAT_CD,SVC_MTHD_NM,SVC_NM,SVC_NMSPC,VER_NBR)
  VALUES (TO_DATE( '20090915140226', 'YYYYMMDDHH24MISS' ),'129.186.79.45',413,3,0,'R','invoke','OSCacheNotificationService','TRAVEL',1)
/
INSERT INTO KRSB_MSG_QUE_T (DT,IP_NBR,MSG_QUE_ID,PRIO,RTRY_CNT,STAT_CD,SVC_MTHD_NM,SVC_NM,SVC_NMSPC,VER_NBR)
  VALUES (TO_DATE( '20090915140226', 'YYYYMMDDHH24MISS' ),'129.186.79.45',414,3,0,'R','invoke','OSCacheNotificationService','TRAVEL',1)
/
INSERT INTO KRSB_MSG_QUE_T (DT,IP_NBR,MSG_QUE_ID,PRIO,RTRY_CNT,STAT_CD,SVC_MTHD_NM,SVC_NM,SVC_NMSPC,VER_NBR)
  VALUES (TO_DATE( '20090915140226', 'YYYYMMDDHH24MISS' ),'129.186.79.45',415,3,0,'R','invoke','OSCacheNotificationService','TRAVEL',1)
/
INSERT INTO KRSB_MSG_QUE_T (DT,IP_NBR,MSG_QUE_ID,PRIO,RTRY_CNT,STAT_CD,SVC_MTHD_NM,SVC_NM,SVC_NMSPC,VER_NBR)
  VALUES (TO_DATE( '20090915140226', 'YYYYMMDDHH24MISS' ),'129.186.79.45',416,3,0,'R','invoke','OSCacheNotificationService','TRAVEL',1)
/
INSERT INTO KRSB_MSG_QUE_T (DT,IP_NBR,MSG_QUE_ID,PRIO,RTRY_CNT,STAT_CD,SVC_MTHD_NM,SVC_NM,SVC_NMSPC,VER_NBR)
  VALUES (TO_DATE( '20090915140226', 'YYYYMMDDHH24MISS' ),'129.186.79.45',417,3,0,'R','invoke','OSCacheNotificationService','TRAVEL',1)
/
INSERT INTO KRSB_MSG_QUE_T (DT,IP_NBR,MSG_QUE_ID,PRIO,RTRY_CNT,STAT_CD,SVC_MTHD_NM,SVC_NM,SVC_NMSPC,VER_NBR)
  VALUES (TO_DATE( '20090915140227', 'YYYYMMDDHH24MISS' ),'129.186.79.45',418,3,0,'R','invoke','OSCacheNotificationService','TRAVEL',1)
/
INSERT INTO KRSB_MSG_QUE_T (DT,IP_NBR,MSG_QUE_ID,PRIO,RTRY_CNT,STAT_CD,SVC_MTHD_NM,SVC_NM,SVC_NMSPC,VER_NBR)
  VALUES (TO_DATE( '20090915140227', 'YYYYMMDDHH24MISS' ),'129.186.79.45',419,3,0,'R','invoke','OSCacheNotificationService','TRAVEL',1)
/
INSERT INTO KRSB_MSG_QUE_T (DT,IP_NBR,MSG_QUE_ID,PRIO,RTRY_CNT,STAT_CD,SVC_MTHD_NM,SVC_NM,SVC_NMSPC,VER_NBR)
  VALUES (TO_DATE( '20090915140227', 'YYYYMMDDHH24MISS' ),'129.186.79.45',420,3,0,'R','invoke','OSCacheNotificationService','TRAVEL',1)
/
INSERT INTO KRSB_MSG_QUE_T (DT,IP_NBR,MSG_QUE_ID,PRIO,RTRY_CNT,STAT_CD,SVC_MTHD_NM,SVC_NM,SVC_NMSPC,VER_NBR)
  VALUES (TO_DATE( '20090915140227', 'YYYYMMDDHH24MISS' ),'129.186.79.45',421,3,0,'R','invoke','OSCacheNotificationService','TRAVEL',1)
/
INSERT INTO KRSB_MSG_QUE_T (DT,IP_NBR,MSG_QUE_ID,PRIO,RTRY_CNT,STAT_CD,SVC_MTHD_NM,SVC_NM,SVC_NMSPC,VER_NBR)
  VALUES (TO_DATE( '20090915140227', 'YYYYMMDDHH24MISS' ),'129.186.79.45',422,3,0,'R','invoke','OSCacheNotificationService','TRAVEL',1)
/
INSERT INTO KRSB_MSG_QUE_T (DT,IP_NBR,MSG_QUE_ID,PRIO,RTRY_CNT,STAT_CD,SVC_MTHD_NM,SVC_NM,SVC_NMSPC,VER_NBR)
  VALUES (TO_DATE( '20090915140228', 'YYYYMMDDHH24MISS' ),'129.186.79.45',423,3,0,'R','invoke','OSCacheNotificationService','TRAVEL',1)
/
INSERT INTO KRSB_MSG_QUE_T (DT,IP_NBR,MSG_QUE_ID,PRIO,RTRY_CNT,STAT_CD,SVC_MTHD_NM,SVC_NM,SVC_NMSPC,VER_NBR)
  VALUES (TO_DATE( '20090915140228', 'YYYYMMDDHH24MISS' ),'129.186.79.45',424,3,0,'R','invoke','OSCacheNotificationService','TRAVEL',1)
/
INSERT INTO KRSB_MSG_QUE_T (DT,IP_NBR,MSG_QUE_ID,PRIO,RTRY_CNT,STAT_CD,SVC_MTHD_NM,SVC_NM,SVC_NMSPC,VER_NBR)
  VALUES (TO_DATE( '20090915140228', 'YYYYMMDDHH24MISS' ),'129.186.79.45',425,3,0,'R','invoke','OSCacheNotificationService','TRAVEL',1)
/
INSERT INTO KRSB_MSG_QUE_T (DT,IP_NBR,MSG_QUE_ID,PRIO,RTRY_CNT,STAT_CD,SVC_MTHD_NM,SVC_NM,SVC_NMSPC,VER_NBR)
  VALUES (TO_DATE( '20090915140228', 'YYYYMMDDHH24MISS' ),'129.186.79.45',426,3,0,'R','invoke','OSCacheNotificationService','TRAVEL',1)
/
INSERT INTO KRSB_MSG_QUE_T (DT,IP_NBR,MSG_QUE_ID,PRIO,RTRY_CNT,STAT_CD,SVC_MTHD_NM,SVC_NM,SVC_NMSPC,VER_NBR)
  VALUES (TO_DATE( '20090915140228', 'YYYYMMDDHH24MISS' ),'129.186.79.45',427,3,0,'R','invoke','OSCacheNotificationService','TRAVEL',1)
/
INSERT INTO KRSB_MSG_QUE_T (DT,IP_NBR,MSG_QUE_ID,PRIO,RTRY_CNT,STAT_CD,SVC_MTHD_NM,SVC_NM,SVC_NMSPC,VER_NBR)
  VALUES (TO_DATE( '20090915140228', 'YYYYMMDDHH24MISS' ),'129.186.79.45',428,3,0,'R','invoke','OSCacheNotificationService','TRAVEL',1)
/
INSERT INTO KRSB_MSG_QUE_T (DT,IP_NBR,MSG_QUE_ID,PRIO,RTRY_CNT,STAT_CD,SVC_MTHD_NM,SVC_NM,SVC_NMSPC,VER_NBR)
  VALUES (TO_DATE( '20090915140229', 'YYYYMMDDHH24MISS' ),'129.186.79.45',429,3,0,'R','invoke','OSCacheNotificationService','TRAVEL',1)
/
INSERT INTO KRSB_MSG_QUE_T (DT,IP_NBR,MSG_QUE_ID,PRIO,RTRY_CNT,STAT_CD,SVC_MTHD_NM,SVC_NM,SVC_NMSPC,VER_NBR)
  VALUES (TO_DATE( '20090915140229', 'YYYYMMDDHH24MISS' ),'129.186.79.45',430,3,0,'R','invoke','OSCacheNotificationService','TRAVEL',1)
/
INSERT INTO KRSB_MSG_QUE_T (DT,IP_NBR,MSG_QUE_ID,PRIO,RTRY_CNT,STAT_CD,SVC_MTHD_NM,SVC_NM,SVC_NMSPC,VER_NBR)
  VALUES (TO_DATE( '20090915140229', 'YYYYMMDDHH24MISS' ),'129.186.79.45',431,3,0,'R','invoke','OSCacheNotificationService','TRAVEL',1)
/
INSERT INTO KRSB_MSG_QUE_T (DT,IP_NBR,MSG_QUE_ID,PRIO,RTRY_CNT,STAT_CD,SVC_MTHD_NM,SVC_NM,SVC_NMSPC,VER_NBR)
  VALUES (TO_DATE( '20090915140230', 'YYYYMMDDHH24MISS' ),'129.186.79.45',432,3,0,'R','invoke','OSCacheNotificationService','TRAVEL',1)
/
INSERT INTO KRSB_MSG_QUE_T (DT,IP_NBR,MSG_QUE_ID,PRIO,RTRY_CNT,STAT_CD,SVC_MTHD_NM,SVC_NM,SVC_NMSPC,VER_NBR)
  VALUES (TO_DATE( '20090915140230', 'YYYYMMDDHH24MISS' ),'129.186.79.45',433,3,0,'R','invoke','OSCacheNotificationService','TRAVEL',1)
/
INSERT INTO KRSB_MSG_QUE_T (DT,IP_NBR,MSG_QUE_ID,PRIO,RTRY_CNT,STAT_CD,SVC_MTHD_NM,SVC_NM,SVC_NMSPC,VER_NBR)
  VALUES (TO_DATE( '20090915140230', 'YYYYMMDDHH24MISS' ),'129.186.79.45',434,3,0,'R','invoke','OSCacheNotificationService','TRAVEL',1)
/
INSERT INTO KRSB_MSG_QUE_T (DT,IP_NBR,MSG_QUE_ID,PRIO,RTRY_CNT,STAT_CD,SVC_MTHD_NM,SVC_NM,SVC_NMSPC,VER_NBR)
  VALUES (TO_DATE( '20090915140230', 'YYYYMMDDHH24MISS' ),'129.186.79.45',435,3,0,'R','invoke','OSCacheNotificationService','TRAVEL',1)
/
INSERT INTO KRSB_MSG_QUE_T (DT,IP_NBR,MSG_QUE_ID,PRIO,RTRY_CNT,STAT_CD,SVC_MTHD_NM,SVC_NM,SVC_NMSPC,VER_NBR)
  VALUES (TO_DATE( '20090915140230', 'YYYYMMDDHH24MISS' ),'129.186.79.45',436,3,0,'R','invoke','OSCacheNotificationService','TRAVEL',1)
/
INSERT INTO KRSB_MSG_QUE_T (DT,IP_NBR,MSG_QUE_ID,PRIO,RTRY_CNT,STAT_CD,SVC_MTHD_NM,SVC_NM,SVC_NMSPC,VER_NBR)
  VALUES (TO_DATE( '20090915140231', 'YYYYMMDDHH24MISS' ),'129.186.79.45',437,3,0,'R','invoke','OSCacheNotificationService','TRAVEL',1)
/
INSERT INTO KRSB_MSG_QUE_T (DT,IP_NBR,MSG_QUE_ID,PRIO,RTRY_CNT,STAT_CD,SVC_MTHD_NM,SVC_NM,SVC_NMSPC,VER_NBR)
  VALUES (TO_DATE( '20090915140231', 'YYYYMMDDHH24MISS' ),'129.186.79.45',438,3,0,'R','invoke','OSCacheNotificationService','TRAVEL',1)
/
INSERT INTO KRSB_MSG_QUE_T (DT,IP_NBR,MSG_QUE_ID,PRIO,RTRY_CNT,STAT_CD,SVC_MTHD_NM,SVC_NM,SVC_NMSPC,VER_NBR)
  VALUES (TO_DATE( '20090915140231', 'YYYYMMDDHH24MISS' ),'129.186.79.45',439,3,0,'R','invoke','OSCacheNotificationService','TRAVEL',1)
/
INSERT INTO KRSB_MSG_QUE_T (DT,IP_NBR,MSG_QUE_ID,PRIO,RTRY_CNT,STAT_CD,SVC_MTHD_NM,SVC_NM,SVC_NMSPC,VER_NBR)
  VALUES (TO_DATE( '20090915140231', 'YYYYMMDDHH24MISS' ),'129.186.79.45',440,3,0,'R','invoke','OSCacheNotificationService','TRAVEL',1)
/
INSERT INTO KRSB_MSG_QUE_T (DT,IP_NBR,MSG_QUE_ID,PRIO,RTRY_CNT,STAT_CD,SVC_MTHD_NM,SVC_NM,SVC_NMSPC,VER_NBR)
  VALUES (TO_DATE( '20090915140231', 'YYYYMMDDHH24MISS' ),'129.186.79.45',441,3,0,'R','invoke','OSCacheNotificationService','TRAVEL',1)
/
INSERT INTO KRSB_MSG_QUE_T (DT,IP_NBR,MSG_QUE_ID,PRIO,RTRY_CNT,STAT_CD,SVC_MTHD_NM,SVC_NM,SVC_NMSPC,VER_NBR)
  VALUES (TO_DATE( '20090915140231', 'YYYYMMDDHH24MISS' ),'129.186.79.45',442,3,0,'R','invoke','OSCacheNotificationService','TRAVEL',1)
/
INSERT INTO KRSB_MSG_QUE_T (DT,IP_NBR,MSG_QUE_ID,PRIO,RTRY_CNT,STAT_CD,SVC_MTHD_NM,SVC_NM,SVC_NMSPC,VER_NBR)
  VALUES (TO_DATE( '20090915140232', 'YYYYMMDDHH24MISS' ),'129.186.79.45',443,3,0,'R','invoke','OSCacheNotificationService','TRAVEL',1)
/
INSERT INTO KRSB_MSG_QUE_T (DT,IP_NBR,MSG_QUE_ID,PRIO,RTRY_CNT,STAT_CD,SVC_MTHD_NM,SVC_NM,SVC_NMSPC,VER_NBR)
  VALUES (TO_DATE( '20090915140232', 'YYYYMMDDHH24MISS' ),'129.186.79.45',444,3,0,'R','invoke','OSCacheNotificationService','TRAVEL',1)
/
INSERT INTO KRSB_MSG_QUE_T (DT,IP_NBR,MSG_QUE_ID,PRIO,RTRY_CNT,STAT_CD,SVC_MTHD_NM,SVC_NM,SVC_NMSPC,VER_NBR)
  VALUES (TO_DATE( '20090915140232', 'YYYYMMDDHH24MISS' ),'129.186.79.45',445,3,0,'R','invoke','OSCacheNotificationService','TRAVEL',1)
/
INSERT INTO KRSB_MSG_QUE_T (DT,IP_NBR,MSG_QUE_ID,PRIO,RTRY_CNT,STAT_CD,SVC_MTHD_NM,SVC_NM,SVC_NMSPC,VER_NBR)
  VALUES (TO_DATE( '20090915140232', 'YYYYMMDDHH24MISS' ),'129.186.79.45',446,3,0,'R','invoke','OSCacheNotificationService','TRAVEL',1)
/
INSERT INTO KRSB_MSG_QUE_T (DT,IP_NBR,MSG_QUE_ID,PRIO,RTRY_CNT,STAT_CD,SVC_MTHD_NM,SVC_NM,SVC_NMSPC,VER_NBR)
  VALUES (TO_DATE( '20090915140232', 'YYYYMMDDHH24MISS' ),'129.186.79.45',447,3,0,'R','invoke','OSCacheNotificationService','TRAVEL',1)
/
INSERT INTO KRSB_MSG_QUE_T (DT,IP_NBR,MSG_QUE_ID,PRIO,RTRY_CNT,STAT_CD,SVC_MTHD_NM,SVC_NM,SVC_NMSPC,VER_NBR)
  VALUES (TO_DATE( '20090915140233', 'YYYYMMDDHH24MISS' ),'129.186.79.45',448,3,0,'R','invoke','OSCacheNotificationService','TRAVEL',1)
/
INSERT INTO KRSB_MSG_QUE_T (DT,IP_NBR,MSG_QUE_ID,PRIO,RTRY_CNT,STAT_CD,SVC_MTHD_NM,SVC_NM,SVC_NMSPC,VER_NBR)
  VALUES (TO_DATE( '20090915140233', 'YYYYMMDDHH24MISS' ),'129.186.79.45',449,3,0,'R','invoke','OSCacheNotificationService','TRAVEL',1)
/
INSERT INTO KRSB_MSG_QUE_T (DT,IP_NBR,MSG_QUE_ID,PRIO,RTRY_CNT,STAT_CD,SVC_MTHD_NM,SVC_NM,SVC_NMSPC,VER_NBR)
  VALUES (TO_DATE( '20090915140233', 'YYYYMMDDHH24MISS' ),'129.186.79.45',450,3,0,'R','invoke','OSCacheNotificationService','TRAVEL',1)
/
INSERT INTO KRSB_MSG_QUE_T (DT,IP_NBR,MSG_QUE_ID,PRIO,RTRY_CNT,STAT_CD,SVC_MTHD_NM,SVC_NM,SVC_NMSPC,VER_NBR)
  VALUES (TO_DATE( '20090915140233', 'YYYYMMDDHH24MISS' ),'129.186.79.45',451,3,0,'R','invoke','OSCacheNotificationService','TRAVEL',1)
/
INSERT INTO KRSB_MSG_QUE_T (DT,IP_NBR,MSG_QUE_ID,PRIO,RTRY_CNT,STAT_CD,SVC_MTHD_NM,SVC_NM,SVC_NMSPC,VER_NBR)
  VALUES (TO_DATE( '20090915140233', 'YYYYMMDDHH24MISS' ),'129.186.79.45',452,3,0,'R','invoke','OSCacheNotificationService','TRAVEL',1)
/
INSERT INTO KRSB_MSG_QUE_T (DT,IP_NBR,MSG_QUE_ID,PRIO,RTRY_CNT,STAT_CD,SVC_MTHD_NM,SVC_NM,SVC_NMSPC,VER_NBR)
  VALUES (TO_DATE( '20090915140233', 'YYYYMMDDHH24MISS' ),'129.186.79.45',453,3,0,'R','invoke','OSCacheNotificationService','TRAVEL',1)
/
INSERT INTO KRSB_MSG_QUE_T (DT,IP_NBR,MSG_QUE_ID,PRIO,RTRY_CNT,STAT_CD,SVC_MTHD_NM,SVC_NM,SVC_NMSPC,VER_NBR)
  VALUES (TO_DATE( '20090915140233', 'YYYYMMDDHH24MISS' ),'129.186.79.45',454,3,0,'R','invoke','OSCacheNotificationService','TRAVEL',1)
/
INSERT INTO KRSB_MSG_QUE_T (DT,IP_NBR,MSG_QUE_ID,PRIO,RTRY_CNT,STAT_CD,SVC_MTHD_NM,SVC_NM,SVC_NMSPC,VER_NBR)
  VALUES (TO_DATE( '20090915140234', 'YYYYMMDDHH24MISS' ),'129.186.79.45',455,3,0,'R','invoke','OSCacheNotificationService','TRAVEL',1)
/
INSERT INTO KRSB_MSG_QUE_T (DT,IP_NBR,MSG_QUE_ID,PRIO,RTRY_CNT,STAT_CD,SVC_MTHD_NM,SVC_NM,SVC_NMSPC,VER_NBR)
  VALUES (TO_DATE( '20090915140234', 'YYYYMMDDHH24MISS' ),'129.186.79.45',456,3,0,'R','invoke','OSCacheNotificationService','TRAVEL',1)
/
INSERT INTO KRSB_MSG_QUE_T (DT,IP_NBR,MSG_QUE_ID,PRIO,RTRY_CNT,STAT_CD,SVC_MTHD_NM,SVC_NM,SVC_NMSPC,VER_NBR)
  VALUES (TO_DATE( '20090915140234', 'YYYYMMDDHH24MISS' ),'129.186.79.45',457,3,0,'R','invoke','OSCacheNotificationService','TRAVEL',1)
/
INSERT INTO KRSB_MSG_QUE_T (DT,IP_NBR,MSG_QUE_ID,PRIO,RTRY_CNT,STAT_CD,SVC_MTHD_NM,SVC_NM,SVC_NMSPC,VER_NBR)
  VALUES (TO_DATE( '20090915140234', 'YYYYMMDDHH24MISS' ),'129.186.79.45',458,3,0,'R','invoke','OSCacheNotificationService','TRAVEL',1)
/
INSERT INTO KRSB_MSG_QUE_T (DT,IP_NBR,MSG_QUE_ID,PRIO,RTRY_CNT,STAT_CD,SVC_MTHD_NM,SVC_NM,SVC_NMSPC,VER_NBR)
  VALUES (TO_DATE( '20090915140234', 'YYYYMMDDHH24MISS' ),'129.186.79.45',459,3,0,'R','invoke','OSCacheNotificationService','TRAVEL',1)
/
INSERT INTO KRSB_MSG_QUE_T (DT,IP_NBR,MSG_QUE_ID,PRIO,RTRY_CNT,STAT_CD,SVC_MTHD_NM,SVC_NM,SVC_NMSPC,VER_NBR)
  VALUES (TO_DATE( '20090915140234', 'YYYYMMDDHH24MISS' ),'129.186.79.45',460,3,0,'R','invoke','OSCacheNotificationService','TRAVEL',1)
/
INSERT INTO KRSB_MSG_QUE_T (DT,IP_NBR,MSG_QUE_ID,PRIO,RTRY_CNT,STAT_CD,SVC_MTHD_NM,SVC_NM,SVC_NMSPC,VER_NBR)
  VALUES (TO_DATE( '20090915140235', 'YYYYMMDDHH24MISS' ),'129.186.79.45',461,3,0,'R','invoke','OSCacheNotificationService','TRAVEL',1)
/
INSERT INTO KRSB_MSG_QUE_T (DT,IP_NBR,MSG_QUE_ID,PRIO,RTRY_CNT,STAT_CD,SVC_MTHD_NM,SVC_NM,SVC_NMSPC,VER_NBR)
  VALUES (TO_DATE( '20090915140235', 'YYYYMMDDHH24MISS' ),'129.186.79.45',462,3,0,'R','invoke','OSCacheNotificationService','TRAVEL',1)
/
--
-- KRSB_QRTZ_LOCKS
--


INSERT INTO KRSB_QRTZ_LOCKS (LOCK_NAME)
  VALUES ('CALENDAR_ACCESS')
/
INSERT INTO KRSB_QRTZ_LOCKS (LOCK_NAME)
  VALUES ('JOB_ACCESS')
/
INSERT INTO KRSB_QRTZ_LOCKS (LOCK_NAME)
  VALUES ('MISFIRE_ACCESS')
/
INSERT INTO KRSB_QRTZ_LOCKS (LOCK_NAME)
  VALUES ('STATE_ACCESS')
/
INSERT INTO KRSB_QRTZ_LOCKS (LOCK_NAME)
  VALUES ('TRIGGER_ACCESS')
/
--
-- KR_COUNTRY_T
--


INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','AND','56908AA37904661BE0404F8189D81F46','AD','Andorra','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','ARE','56908AA379DE661BE0404F8189D81F46','AE','United Arab Emirates','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','AFG','56908AA378FF661BE0404F8189D81F46','AF','Afghanistan','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','ATG','56908AA378FE661BE0404F8189D81F46','AG','Antigua and Barbuda','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','AIA','56908AA3790B661BE0404F8189D81F46','AI','Anguilla','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','ALB','56908AA37902661BE0404F8189D81F46','AL','Albania','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','ARM','56908AA37903661BE0404F8189D81F46','AM','Armenia','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','ANT','56908AA379A7661BE0404F8189D81F46','AN','Netherlands Antilles','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','AGO','56908AA37905661BE0404F8189D81F46','AO','Angola','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','ATA','56908AA3790C661BE0404F8189D81F46','AQ','Antarctica','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','ARG','56908AA37907661BE0404F8189D81F46','AR','Argentina','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','ASM','56908AA37906661BE0404F8189D81F46','AS','American Samoa','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','AUT','56908AA3790A661BE0404F8189D81F46','AT','Austria','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','AUS','56908AA37908661BE0404F8189D81F46','AU','Australia','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','ABW','56908AA378FD661BE0404F8189D81F46','AW','Aruba','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','ALA','56908AA3794E661BE0404F8189D81F46','AX','ÿ¿land Islands','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','AZE','56908AA37901661BE0404F8189D81F46','AZ','Azerbaijan','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','BIH','56908AA37915661BE0404F8189D81F46','BA','Bosnia and Herzegovina','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','BRB','56908AA3790E661BE0404F8189D81F46','BB','Barbados','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','BGD','56908AA37913661BE0404F8189D81F46','BD','Bangladesh','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','BEL','56908AA37911661BE0404F8189D81F46','BE','Belgium','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','BFA','56908AA37909661BE0404F8189D81F46','BF','Burkina Faso','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','BGR','56908AA3791F661BE0404F8189D81F46','BG','Bulgaria','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','BHR','56908AA3790D661BE0404F8189D81F46','BH','Bahrain','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','BDI','56908AA37922661BE0404F8189D81F46','BI','Burundi','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','BEN','56908AA37918661BE0404F8189D81F46','BJ','Benin','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','BLM','56908AA3791B661BE0404F8189D81F46','BL','Saint Barthÿ©lemy','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','BMU','56908AA37910661BE0404F8189D81F46','BM','Bermuda','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','BRN','56908AA37921661BE0404F8189D81F46','BN','Brunei Darussalam','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','BOL','56908AA37916661BE0404F8189D81F46','BO','Bolivia, Plurinational State Of','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','BRA','56908AA3791C661BE0404F8189D81F46','BR','Brazil','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','BHS','56908AA37912661BE0404F8189D81F46','BS','Bahamas','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','BTN','56908AA3791E661BE0404F8189D81F46','BT','Bhutan','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','BVT','56908AA37920661BE0404F8189D81F46','BV','Bouvet Island','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','BWA','56908AA3790F661BE0404F8189D81F46','BW','Botswana','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','BLR','56908AA37919661BE0404F8189D81F46','BY','Belarus','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','BLZ','56908AA37914661BE0404F8189D81F46','BZ','Belize','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','CAN','56908AA37923661BE0404F8189D81F46','CA','Canada','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','CCK','56908AA3792C661BE0404F8189D81F46','CC','Cocos (Keeling) Islands','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','COD','56908AA37928661BE0404F8189D81F46','CD','Congo, The Democratic Republic Of The','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','CAF','56908AA37933661BE0404F8189D81F46','CF','Central African Republic','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','COG','56908AA37927661BE0404F8189D81F46','CG','Congo','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','CHE','56908AA379DD661BE0404F8189D81F46','CH','Switzerland','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','CIV','56908AA37973661BE0404F8189D81F46','CI','Cÿ´te D''Ivoire','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','COK','56908AA37936661BE0404F8189D81F46','CK','Cook Islands','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','CHL','56908AA3792A661BE0404F8189D81F46','CL','Chile','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','CMR','56908AA3792D661BE0404F8189D81F46','CM','Cameroon','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','CHN','56908AA37929661BE0404F8189D81F46','CN','China','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','COL','56908AA3792F661BE0404F8189D81F46','CO','Colombia','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','CRI','56908AA37932661BE0404F8189D81F46','CR','Costa Rica','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','CUB','56908AA37934661BE0404F8189D81F46','CU','Cuba','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','CPV','56908AA37935661BE0404F8189D81F46','CV','Cape Verde','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','CXR','56908AA37983661BE0404F8189D81F46','CX','Christmas Island','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','CYP','56908AA37937661BE0404F8189D81F46','CY','Cyprus','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','CZE','56908AA37946661BE0404F8189D81F46','CZ','Czech Republic','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','DEU','56908AA3795A661BE0404F8189D81F46','DE','Germany','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','DJI','56908AA3793A661BE0404F8189D81F46','DJ','Djibouti','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','DNK','56908AA37939661BE0404F8189D81F46','DK','Denmark','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','DMA','56908AA3793B661BE0404F8189D81F46','DM','Dominica','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','DOM','56908AA3793C661BE0404F8189D81F46','DO','Dominican Republic','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','DZA','56908AA37900661BE0404F8189D81F46','DZ','Algeria','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','ECU','56908AA3793D661BE0404F8189D81F46','EC','Ecuador','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','EST','56908AA37941661BE0404F8189D81F46','EE','Estonia','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','EGY','56908AA3793E661BE0404F8189D81F46','EG','Egypt','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','ESH','56908AA379FE661BE0404F8189D81F46','EH','Western Sahara','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','ERI','56908AA37942661BE0404F8189D81F46','ER','Eritrea','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','ESP','56908AA379D6661BE0404F8189D81F46','ES','Spain','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','ETH','56908AA37944661BE0404F8189D81F46','ET','Ethiopia','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','FIN','56908AA37949661BE0404F8189D81F46','FI','Finland','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','FJI','56908AA3794A661BE0404F8189D81F46','FJ','Fiji','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','FLK','56908AA37947661BE0404F8189D81F46','FK','Falkland Islands (Malvinas)','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','FSM','56908AA3794B661BE0404F8189D81F46','FM','Micronesia, Federated States Of','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','FRO','56908AA3794C661BE0404F8189D81F46','FO','Faroe Islands','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','FRA','56908AA3794F661BE0404F8189D81F46','FR','France','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','GAB','56908AA37952661BE0404F8189D81F46','GA','Gabon','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','GBR','56908AA379EF661BE0404F8189D81F46','GB','United Kingdom','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','GRD','56908AA37957661BE0404F8189D81F46','GD','Grenada','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','GEO','56908AA37954661BE0404F8189D81F46','GE','Georgia','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','GUF','56908AA37948661BE0404F8189D81F46','GF','French Guiana','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','GGY','56908AA37958661BE0404F8189D81F46','GG','Guernsey','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','GHA','56908AA37955661BE0404F8189D81F46','GH','Ghana','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','GIB','56908AA37956661BE0404F8189D81F46','GI','Gibraltar','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','GRL','56908AA37959661BE0404F8189D81F46','GL','Greenland','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','GMB','56908AA37951661BE0404F8189D81F46','GM','Gambia','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','GIN','56908AA37960661BE0404F8189D81F46','GN','Guinea','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','GLP','56908AA3795C661BE0404F8189D81F46','GP','Guadeloupe','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','GNQ','56908AA37940661BE0404F8189D81F46','GQ','Equatorial Guinea','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','GRC','56908AA3795E661BE0404F8189D81F46','GR','Greece','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','SGS','56908AA37931661BE0404F8189D81F46','GS','South Georgia and South Sandwich Islands','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','GTM','56908AA3795F661BE0404F8189D81F46','GT','Guatemala','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','GUM','56908AA3795D661BE0404F8189D81F46','GU','Guam','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','GNB','56908AA379C1661BE0404F8189D81F46','GW','Guinea-Bissau','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','GUY','56908AA37961661BE0404F8189D81F46','GY','Guyana','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','HKG','56908AA37964661BE0404F8189D81F46','HK','Hong Kong','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','HMD','56908AA37965661BE0404F8189D81F46','HM','Heard and McDonald Islands','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','HND','56908AA37966661BE0404F8189D81F46','HN','Honduras','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','HRV','56908AA37968661BE0404F8189D81F46','HR','Croatia','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','HTI','56908AA37963661BE0404F8189D81F46','HT','Haiti','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','HUN','56908AA37969661BE0404F8189D81F46','HU','Hungary','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','IDN','56908AA3796B661BE0404F8189D81F46','ID','Indonesia','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','IRL','56908AA3793F661BE0404F8189D81F46','IE','Ireland','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','ISR','56908AA37971661BE0404F8189D81F46','IL','Israel','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','IMN','56908AA3796C661BE0404F8189D81F46','IM','Isle of Man','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','IND','56908AA3796D661BE0404F8189D81F46','IN','India','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','IOT','56908AA3796E661BE0404F8189D81F46','IO','British Indian Ocean Territory','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','IRQ','56908AA37975661BE0404F8189D81F46','IQ','Iraq','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','IRN','56908AA37970661BE0404F8189D81F46','IR','Iran, Islamic Republic Of','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','ISL','56908AA3796A661BE0404F8189D81F46','IS','Iceland','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','ITA','56908AA37972661BE0404F8189D81F46','IT','Italy','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','JEY','56908AA37977661BE0404F8189D81F46','JE','Jersey','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','JAM','56908AA37978661BE0404F8189D81F46','JM','Jamaica','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','JOR','56908AA3797A661BE0404F8189D81F46','JO','Jordan','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','JPN','56908AA37976661BE0404F8189D81F46','JP','Japan','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','KEN','56908AA3797D661BE0404F8189D81F46','KE','Kenya','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','KGZ','56908AA3797E661BE0404F8189D81F46','KG','Kyrgyzstan','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','KHM','56908AA37924661BE0404F8189D81F46','KH','Cambodia','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','KIR','56908AA37981661BE0404F8189D81F46','KI','Kiribati','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','COM','56908AA3792E661BE0404F8189D81F46','KM','Comoros','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','KNA','56908AA379CC661BE0404F8189D81F46','KN','Saint Kitts And Nevis','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','PRK','56908AA3797F661BE0404F8189D81F46','KP','Korea, Democratic People''s Republic Of','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','KOR','56908AA37982661BE0404F8189D81F46','KR','Korea, Republic of','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','KWT','56908AA37984661BE0404F8189D81F46','KW','Kuwait','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','CYM','56908AA3792B661BE0404F8189D81F46','KY','Cayman Islands','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','KAZ','56908AA37985661BE0404F8189D81F46','KZ','Kazakhstan','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','LAO','56908AA37986661BE0404F8189D81F46','LA','Lao People''s Democratic Republic','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','LBN','56908AA37987661BE0404F8189D81F46','LB','Lebanon','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','LCA','56908AA379D8661BE0404F8189D81F46','LC','Saint Lucia','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','LIE','56908AA3798D661BE0404F8189D81F46','LI','Liechtenstein','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','LKA','56908AA37926661BE0404F8189D81F46','LK','Sri Lanka','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','LBR','56908AA3798A661BE0404F8189D81F46','LR','Liberia','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','LSO','56908AA3798E661BE0404F8189D81F46','LS','Lesotho','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','LTU','56908AA37989661BE0404F8189D81F46','LT','Lithuania','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','LUX','56908AA3798F661BE0404F8189D81F46','LU','Luxembourg','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','LVA','56908AA37988661BE0404F8189D81F46','LV','Latvia','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','LBY','56908AA37990661BE0404F8189D81F46','LY','Libyan Arab Jamahiriya','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','MAR','56908AA3799C661BE0404F8189D81F46','MA','Morocco','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','MCO','56908AA3799B661BE0404F8189D81F46','MC','Monaco','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','MDA','56908AA37994661BE0404F8189D81F46','MD','Moldova, Republic of','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','MNE','56908AA379A3661BE0404F8189D81F46','ME','Montenegro','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','MAF','56908AA3791D661BE0404F8189D81F46','MF','Saint Martin','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','MDG','56908AA37991661BE0404F8189D81F46','MG','Madagascar','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','MHL','56908AA379C4661BE0404F8189D81F46','MH','Marshall Islands','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','MKD','56908AA37999661BE0404F8189D81F46','MK','Macedonia, the Fmr. Yugoslav Republic Of','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','MLI','56908AA3799A661BE0404F8189D81F46','ML','Mali','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','MMR','56908AA37917661BE0404F8189D81F46','MM','Myanmar','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','MNG','56908AA37996661BE0404F8189D81F46','MN','Mongolia','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','MAC','56908AA37993661BE0404F8189D81F46','MO','Macao','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','MNP','56908AA37930661BE0404F8189D81F46','MP','Northern Mariana Islands','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','MTQ','56908AA37992661BE0404F8189D81F46','MQ','Martinique','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','MRT','56908AA3799F661BE0404F8189D81F46','MR','Mauritania','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','MSR','56908AA37997661BE0404F8189D81F46','MS','Montserrat','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','MLT','56908AA379A0661BE0404F8189D81F46','MT','Malta','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','MUS','56908AA3799D661BE0404F8189D81F46','MU','Mauritius','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','MDV','56908AA379A2661BE0404F8189D81F46','MV','Maldives','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','MWI','56908AA37998661BE0404F8189D81F46','MW','Malawi','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','MEX','56908AA379A4661BE0404F8189D81F46','MX','Mexico','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','MYS','56908AA379A5661BE0404F8189D81F46','MY','Malaysia','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','MOZ','56908AA379A6661BE0404F8189D81F46','MZ','Mozambique','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','NAM','56908AA379FB661BE0404F8189D81F46','NA','Namibia','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','NCL','56908AA379A8661BE0404F8189D81F46','NC','New Caledonia','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','NER','56908AA379AB661BE0404F8189D81F46','NE','Niger','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','NFK','56908AA379AA661BE0404F8189D81F46','NF','Norfolk Island','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','NGA','56908AA379AD661BE0404F8189D81F46','NG','Nigeria','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','NIC','56908AA379B3661BE0404F8189D81F46','NI','Nicaragua','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','NLD','56908AA379AE661BE0404F8189D81F46','NL','Netherlands','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','NOR','56908AA379AF661BE0404F8189D81F46','NO','Norway','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','NPL','56908AA379B0661BE0404F8189D81F46','NP','Nepal','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','NRU','56908AA379B1661BE0404F8189D81F46','NR','Nauru','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','NIU','56908AA379A9661BE0404F8189D81F46','NU','Niue','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','NZL','56908AA379B4661BE0404F8189D81F46','NZ','New Zealand','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','OMN','56908AA379A1661BE0404F8189D81F46','OM','Oman','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','PAN','56908AA379BD661BE0404F8189D81F46','PA','Panama','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','PER','56908AA379B8661BE0404F8189D81F46','PE','Peru','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','PYF','56908AA3794D661BE0404F8189D81F46','PF','French Polynesia','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','PNG','56908AA379BF661BE0404F8189D81F46','PG','Papua New Guinea','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','PHL','56908AA379C6661BE0404F8189D81F46','PH','Philippines','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','PAK','56908AA379BB661BE0404F8189D81F46','PK','Pakistan','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','POL','56908AA379BC661BE0404F8189D81F46','PL','Poland','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','SPM','56908AA379CB661BE0404F8189D81F46','PM','Saint Pierre And Miquelon','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','PCN','56908AA379B7661BE0404F8189D81F46','PN','Pitcairn','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','PRI','56908AA379C7661BE0404F8189D81F46','PR','Puerto Rico','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','PSE','56908AA37962661BE0404F8189D81F46','PS','Palestinian Territory, Occupied','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','PRT','56908AA379BE661BE0404F8189D81F46','PT','Portugal','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','PLW','56908AA379C0661BE0404F8189D81F46','PW','Palau','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','PRY','56908AA379B6661BE0404F8189D81F46','PY','Paraguay','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','QAT','56908AA379C2661BE0404F8189D81F46','QA','Qatar','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','REU','56908AA379C3661BE0404F8189D81F46','RE','Rÿ©union','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','ROU','56908AA379C5661BE0404F8189D81F46','RO','Romania','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','SRB','56908AA379D7661BE0404F8189D81F46','RS','Serbia','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','RUS','56908AA379C8661BE0404F8189D81F46','RU','Russian Federation','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','RWA','56908AA379C9661BE0404F8189D81F46','RW','Rwanda','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','SAU','56908AA379CA661BE0404F8189D81F46','SA','Saudi Arabia','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','SLB','56908AA3791A661BE0404F8189D81F46','SB','Solomon Islands','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','SYC','56908AA379CD661BE0404F8189D81F46','SC','Seychelles','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','SDN','56908AA379D9661BE0404F8189D81F46','SD','Sudan','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','SWE','56908AA379DB661BE0404F8189D81F46','SE','Sweden','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','SGP','56908AA379D4661BE0404F8189D81F46','SG','Singapore','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','SHN','56908AA379D0661BE0404F8189D81F46','SH','St. Helena, Ascension, Tristan Da Cunha','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','SVN','56908AA379D1661BE0404F8189D81F46','SI','Slovenia','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','SJM','56908AA379DA661BE0404F8189D81F46','SJ','Svalbard And Jan Mayen','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','SVK','56908AA3798B661BE0404F8189D81F46','SK','Slovakia','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','SLE','56908AA379D2661BE0404F8189D81F46','SL','Sierra Leone','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','SMR','56908AA379D3661BE0404F8189D81F46','SM','San Marino','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','SEN','56908AA379CF661BE0404F8189D81F46','SN','Senegal','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','SOM','56908AA379D5661BE0404F8189D81F46','SO','Somalia','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','SUR','56908AA379B2661BE0404F8189D81F46','SR','Suriname','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','STP','56908AA379E7661BE0404F8189D81F46','ST','Sao Tome and Principe','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','SLV','56908AA37943661BE0404F8189D81F46','SV','El Salvador','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','SYR','56908AA379DC661BE0404F8189D81F46','SY','Syrian Arab Republic','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','SWZ','56908AA37A01661BE0404F8189D81F46','SZ','Swaziland','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','TCA','56908AA379E3661BE0404F8189D81F46','TC','Turks and Caicos Islands','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','TCD','56908AA37925661BE0404F8189D81F46','TD','Chad','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','ATF','56908AA37950661BE0404F8189D81F46','TF','French Southern Territories','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','TGO','56908AA379E6661BE0404F8189D81F46','TG','Togo','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','THA','56908AA379E1661BE0404F8189D81F46','TH','Thailand','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','TJK','56908AA379E2661BE0404F8189D81F46','TJ','Tajikistan','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','TKL','56908AA379E4661BE0404F8189D81F46','TK','Tokelau','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','TLS','56908AA37945661BE0404F8189D81F46','TL','Timor-Leste','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','TKM','56908AA379EC661BE0404F8189D81F46','TM','Turkmenistan','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','TUN','56908AA379E8661BE0404F8189D81F46','TN','Tunisia','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','TON','56908AA379E5661BE0404F8189D81F46','TO','Tonga','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','TUR','56908AA379E9661BE0404F8189D81F46','TR','Turkey','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','TTO','56908AA379DF661BE0404F8189D81F46','TT','Trinidad and Tobago','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','TUV','56908AA379EA661BE0404F8189D81F46','TV','Tuvalu','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','TWN','56908AA379EB661BE0404F8189D81F46','TW','Taiwan, Province Of China','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','TZA','56908AA379ED661BE0404F8189D81F46','TZ','Tanzania, United Republic of','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','UKR','56908AA379F0661BE0404F8189D81F46','UA','Ukraine','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','UGA','56908AA379EE661BE0404F8189D81F46','UG','Uganda','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','UMI','56908AA3799E661BE0404F8189D81F46','UM','United States Minor Outlying Islands','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','USA','56908AA379F2661BE0404F8189D81F46','US','United States','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','URY','56908AA379F3661BE0404F8189D81F46','UY','Uruguay','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','UZB','56908AA379F4661BE0404F8189D81F46','UZ','Uzbekistan','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','VAT','56908AA379FA661BE0404F8189D81F46','VA','Holy See (Vatican City State)','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','VCT','56908AA379F5661BE0404F8189D81F46','VC','Saint Vincent And The Grenedines','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','VEN','56908AA379F6661BE0404F8189D81F46','VE','Venezuela, Bolivarian Republic of','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','VGB','56908AA379F7661BE0404F8189D81F46','VG','Virgin Islands, British','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','VIR','56908AA379F9661BE0404F8189D81F46','VI','Virgin Islands, U.S.','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','VNM','56908AA379F8661BE0404F8189D81F46','VN','Viet Nam','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','VUT','56908AA379AC661BE0404F8189D81F46','VU','Vanuatu','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','WLF','56908AA379FD661BE0404F8189D81F46','WF','Wallis and Futuna','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','WSM','56908AA37A00661BE0404F8189D81F46','WS','Samoa','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','YEM','56908AA37A02661BE0404F8189D81F46','YE','Yemen','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','MYT','56908AA37995661BE0404F8189D81F46','YT','Mayotte','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','ZAF','56908AA379CE661BE0404F8189D81F46','ZA','South Africa','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','ZMB','56908AA37A04661BE0404F8189D81F46','ZM','Zambia','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','ZWE','56908AA37A05661BE0404F8189D81F46','ZW','Zimbabwe','N',1)
/
INSERT INTO KR_COUNTRY_T (ACTV_IND,ALT_POSTAL_CNTRY_CD,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_CNTRY_NM,PSTL_CNTRY_RSTRC_IND,VER_NBR)
  VALUES ('Y','ZZZ','56908AA379B5661BE0404F8189D81F46','ZZ','Other Countries','N',1)
/
--
-- KR_STATE_T
--


INSERT INTO KR_STATE_T (ACTV_IND,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_STATE_CD,POSTAL_STATE_NM,VER_NBR)
  VALUES ('Y','56908AA37A06661BE0404F8189D81F46','US','--','OUT OF COUNTRY',1)
/
INSERT INTO KR_STATE_T (ACTV_IND,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_STATE_CD,POSTAL_STATE_NM,VER_NBR)
  VALUES ('Y','56908AA37A3B661BE0404F8189D81F46','US','AA','ARMED FORCES AMERICAS (EXCEPT CANADA)',1)
/
INSERT INTO KR_STATE_T (ACTV_IND,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_STATE_CD,POSTAL_STATE_NM,VER_NBR)
  VALUES ('Y','56908AA37A3C661BE0404F8189D81F46','US','AE','ARMED FORCES EURO/CANADA/AFRICA/MID EAST',1)
/
INSERT INTO KR_STATE_T (ACTV_IND,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_STATE_CD,POSTAL_STATE_NM,VER_NBR)
  VALUES ('Y','56908AA37A07661BE0404F8189D81F46','US','AK','ALASKA',1)
/
INSERT INTO KR_STATE_T (ACTV_IND,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_STATE_CD,POSTAL_STATE_NM,VER_NBR)
  VALUES ('Y','56908AA37A08661BE0404F8189D81F46','US','AL','ALABAMA',1)
/
INSERT INTO KR_STATE_T (ACTV_IND,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_STATE_CD,POSTAL_STATE_NM,VER_NBR)
  VALUES ('Y','56908AA37A3D661BE0404F8189D81F46','US','AP','ARMED FORCES PACIFIC',1)
/
INSERT INTO KR_STATE_T (ACTV_IND,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_STATE_CD,POSTAL_STATE_NM,VER_NBR)
  VALUES ('Y','56908AA37A09661BE0404F8189D81F46','US','AR','ARKANSAS',1)
/
INSERT INTO KR_STATE_T (ACTV_IND,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_STATE_CD,POSTAL_STATE_NM,VER_NBR)
  VALUES ('Y','56908AA37A3E661BE0404F8189D81F46','US','AS','AMERICAN SAMOA',1)
/
INSERT INTO KR_STATE_T (ACTV_IND,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_STATE_CD,POSTAL_STATE_NM,VER_NBR)
  VALUES ('Y','56908AA37A0A661BE0404F8189D81F46','US','AZ','ARIZONA',1)
/
INSERT INTO KR_STATE_T (ACTV_IND,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_STATE_CD,POSTAL_STATE_NM,VER_NBR)
  VALUES ('Y','56908AA37A0B661BE0404F8189D81F46','US','CA','CALIFORNIA',1)
/
INSERT INTO KR_STATE_T (ACTV_IND,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_STATE_CD,POSTAL_STATE_NM,VER_NBR)
  VALUES ('Y','56908AA37A0C661BE0404F8189D81F46','US','CO','COLORADO',1)
/
INSERT INTO KR_STATE_T (ACTV_IND,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_STATE_CD,POSTAL_STATE_NM,VER_NBR)
  VALUES ('Y','56908AA37A0D661BE0404F8189D81F46','US','CT','CONNECTICUT',1)
/
INSERT INTO KR_STATE_T (ACTV_IND,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_STATE_CD,POSTAL_STATE_NM,VER_NBR)
  VALUES ('Y','56908AA37A0E661BE0404F8189D81F46','US','DC','DISTRICT OF COLUMBIA',1)
/
INSERT INTO KR_STATE_T (ACTV_IND,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_STATE_CD,POSTAL_STATE_NM,VER_NBR)
  VALUES ('Y','56908AA37A0F661BE0404F8189D81F46','US','DE','DELAWARE',1)
/
INSERT INTO KR_STATE_T (ACTV_IND,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_STATE_CD,POSTAL_STATE_NM,VER_NBR)
  VALUES ('Y','56908AA37A10661BE0404F8189D81F46','US','FL','FLORIDA',1)
/
INSERT INTO KR_STATE_T (ACTV_IND,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_STATE_CD,POSTAL_STATE_NM,VER_NBR)
  VALUES ('Y','56908AA37A3F661BE0404F8189D81F46','US','FM','FEDERATED STATES OF MICRONESIA',1)
/
INSERT INTO KR_STATE_T (ACTV_IND,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_STATE_CD,POSTAL_STATE_NM,VER_NBR)
  VALUES ('Y','56908AA37A11661BE0404F8189D81F46','US','GA','GEORGIA',1)
/
INSERT INTO KR_STATE_T (ACTV_IND,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_STATE_CD,POSTAL_STATE_NM,VER_NBR)
  VALUES ('Y','56908AA37A40661BE0404F8189D81F46','US','GU','GUAM',1)
/
INSERT INTO KR_STATE_T (ACTV_IND,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_STATE_CD,POSTAL_STATE_NM,VER_NBR)
  VALUES ('Y','56908AA37A12661BE0404F8189D81F46','US','HI','HAWAII',1)
/
INSERT INTO KR_STATE_T (ACTV_IND,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_STATE_CD,POSTAL_STATE_NM,VER_NBR)
  VALUES ('Y','56908AA37A13661BE0404F8189D81F46','US','IA','IOWA',1)
/
INSERT INTO KR_STATE_T (ACTV_IND,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_STATE_CD,POSTAL_STATE_NM,VER_NBR)
  VALUES ('Y','56908AA37A14661BE0404F8189D81F46','US','ID','IDAHO',1)
/
INSERT INTO KR_STATE_T (ACTV_IND,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_STATE_CD,POSTAL_STATE_NM,VER_NBR)
  VALUES ('Y','56908AA37A15661BE0404F8189D81F46','US','IL','ILLINOIS',1)
/
INSERT INTO KR_STATE_T (ACTV_IND,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_STATE_CD,POSTAL_STATE_NM,VER_NBR)
  VALUES ('Y','56908AA37A16661BE0404F8189D81F46','US','IN','INDIANA',1)
/
INSERT INTO KR_STATE_T (ACTV_IND,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_STATE_CD,POSTAL_STATE_NM,VER_NBR)
  VALUES ('Y','56908AA37A17661BE0404F8189D81F46','US','KS','KANSAS',1)
/
INSERT INTO KR_STATE_T (ACTV_IND,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_STATE_CD,POSTAL_STATE_NM,VER_NBR)
  VALUES ('Y','56908AA37A18661BE0404F8189D81F46','US','KY','KENTUCKY',1)
/
INSERT INTO KR_STATE_T (ACTV_IND,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_STATE_CD,POSTAL_STATE_NM,VER_NBR)
  VALUES ('Y','56908AA37A19661BE0404F8189D81F46','US','LA','LOUISIANA',1)
/
INSERT INTO KR_STATE_T (ACTV_IND,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_STATE_CD,POSTAL_STATE_NM,VER_NBR)
  VALUES ('Y','56908AA37A1A661BE0404F8189D81F46','US','MA','MASSACHUSETTS',1)
/
INSERT INTO KR_STATE_T (ACTV_IND,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_STATE_CD,POSTAL_STATE_NM,VER_NBR)
  VALUES ('Y','56908AA37A1B661BE0404F8189D81F46','US','MD','MARYLAND',1)
/
INSERT INTO KR_STATE_T (ACTV_IND,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_STATE_CD,POSTAL_STATE_NM,VER_NBR)
  VALUES ('Y','56908AA37A1C661BE0404F8189D81F46','US','ME','MAINE',1)
/
INSERT INTO KR_STATE_T (ACTV_IND,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_STATE_CD,POSTAL_STATE_NM,VER_NBR)
  VALUES ('Y','56908AA37A41661BE0404F8189D81F46','US','MH','MARSHALL ISLANDS',1)
/
INSERT INTO KR_STATE_T (ACTV_IND,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_STATE_CD,POSTAL_STATE_NM,VER_NBR)
  VALUES ('Y','56908AA37A1D661BE0404F8189D81F46','US','MI','MICHIGAN',1)
/
INSERT INTO KR_STATE_T (ACTV_IND,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_STATE_CD,POSTAL_STATE_NM,VER_NBR)
  VALUES ('Y','56908AA37A1E661BE0404F8189D81F46','US','MN','MINNESOTA',1)
/
INSERT INTO KR_STATE_T (ACTV_IND,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_STATE_CD,POSTAL_STATE_NM,VER_NBR)
  VALUES ('Y','56908AA37A1F661BE0404F8189D81F46','US','MO','MISSOURI',1)
/
INSERT INTO KR_STATE_T (ACTV_IND,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_STATE_CD,POSTAL_STATE_NM,VER_NBR)
  VALUES ('Y','56908AA37A42661BE0404F8189D81F46','US','MP','NORTHERN MARIANA ISLANDS',1)
/
INSERT INTO KR_STATE_T (ACTV_IND,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_STATE_CD,POSTAL_STATE_NM,VER_NBR)
  VALUES ('Y','56908AA37A20661BE0404F8189D81F46','US','MS','MISSISSIPPI',1)
/
INSERT INTO KR_STATE_T (ACTV_IND,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_STATE_CD,POSTAL_STATE_NM,VER_NBR)
  VALUES ('Y','56908AA37A21661BE0404F8189D81F46','US','MT','MONTANA',1)
/
INSERT INTO KR_STATE_T (ACTV_IND,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_STATE_CD,POSTAL_STATE_NM,VER_NBR)
  VALUES ('Y','56908AA37A22661BE0404F8189D81F46','US','NC','NORTH CAROLINA',1)
/
INSERT INTO KR_STATE_T (ACTV_IND,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_STATE_CD,POSTAL_STATE_NM,VER_NBR)
  VALUES ('Y','56908AA37A23661BE0404F8189D81F46','US','ND','NORTH DAKOTA',1)
/
INSERT INTO KR_STATE_T (ACTV_IND,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_STATE_CD,POSTAL_STATE_NM,VER_NBR)
  VALUES ('Y','56908AA37A24661BE0404F8189D81F46','US','NE','NEBRASKA',1)
/
INSERT INTO KR_STATE_T (ACTV_IND,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_STATE_CD,POSTAL_STATE_NM,VER_NBR)
  VALUES ('Y','56908AA37A25661BE0404F8189D81F46','US','NH','NEW HAMPSHIRE',1)
/
INSERT INTO KR_STATE_T (ACTV_IND,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_STATE_CD,POSTAL_STATE_NM,VER_NBR)
  VALUES ('Y','56908AA37A26661BE0404F8189D81F46','US','NJ','NEW JERSEY',1)
/
INSERT INTO KR_STATE_T (ACTV_IND,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_STATE_CD,POSTAL_STATE_NM,VER_NBR)
  VALUES ('Y','56908AA37A27661BE0404F8189D81F46','US','NM','NEW MEXICO',1)
/
INSERT INTO KR_STATE_T (ACTV_IND,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_STATE_CD,POSTAL_STATE_NM,VER_NBR)
  VALUES ('Y','56908AA37A28661BE0404F8189D81F46','US','NV','NEVADA',1)
/
INSERT INTO KR_STATE_T (ACTV_IND,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_STATE_CD,POSTAL_STATE_NM,VER_NBR)
  VALUES ('Y','56908AA37A29661BE0404F8189D81F46','US','NY','NEW YORK',1)
/
INSERT INTO KR_STATE_T (ACTV_IND,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_STATE_CD,POSTAL_STATE_NM,VER_NBR)
  VALUES ('Y','56908AA37A2A661BE0404F8189D81F46','US','OH','OHIO',1)
/
INSERT INTO KR_STATE_T (ACTV_IND,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_STATE_CD,POSTAL_STATE_NM,VER_NBR)
  VALUES ('Y','56908AA37A2B661BE0404F8189D81F46','US','OK','OKLAHOMA',1)
/
INSERT INTO KR_STATE_T (ACTV_IND,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_STATE_CD,POSTAL_STATE_NM,VER_NBR)
  VALUES ('Y','56908AA37A2C661BE0404F8189D81F46','US','OR','OREGON',1)
/
INSERT INTO KR_STATE_T (ACTV_IND,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_STATE_CD,POSTAL_STATE_NM,VER_NBR)
  VALUES ('Y','56908AA37A2D661BE0404F8189D81F46','US','PA','PENNSYLVANIA',1)
/
INSERT INTO KR_STATE_T (ACTV_IND,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_STATE_CD,POSTAL_STATE_NM,VER_NBR)
  VALUES ('Y','56908AA37A2E661BE0404F8189D81F46','US','PR','PUERTO RICO',1)
/
INSERT INTO KR_STATE_T (ACTV_IND,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_STATE_CD,POSTAL_STATE_NM,VER_NBR)
  VALUES ('Y','56908AA37A43661BE0404F8189D81F46','US','PW','PALAU',1)
/
INSERT INTO KR_STATE_T (ACTV_IND,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_STATE_CD,POSTAL_STATE_NM,VER_NBR)
  VALUES ('Y','56908AA37A2F661BE0404F8189D81F46','US','RI','RHODE ISLAND',1)
/
INSERT INTO KR_STATE_T (ACTV_IND,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_STATE_CD,POSTAL_STATE_NM,VER_NBR)
  VALUES ('Y','56908AA37A30661BE0404F8189D81F46','US','SC','SOUTH CAROLINA',1)
/
INSERT INTO KR_STATE_T (ACTV_IND,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_STATE_CD,POSTAL_STATE_NM,VER_NBR)
  VALUES ('Y','56908AA37A31661BE0404F8189D81F46','US','SD','SOUTH DAKOTA',1)
/
INSERT INTO KR_STATE_T (ACTV_IND,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_STATE_CD,POSTAL_STATE_NM,VER_NBR)
  VALUES ('Y','56908AA37A32661BE0404F8189D81F46','US','TN','TENNESSEE',1)
/
INSERT INTO KR_STATE_T (ACTV_IND,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_STATE_CD,POSTAL_STATE_NM,VER_NBR)
  VALUES ('Y','56908AA37A33661BE0404F8189D81F46','US','TX','TEXAS',1)
/
INSERT INTO KR_STATE_T (ACTV_IND,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_STATE_CD,POSTAL_STATE_NM,VER_NBR)
  VALUES ('Y','56908AA37A34661BE0404F8189D81F46','US','UT','UTAH',1)
/
INSERT INTO KR_STATE_T (ACTV_IND,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_STATE_CD,POSTAL_STATE_NM,VER_NBR)
  VALUES ('Y','56908AA37A35661BE0404F8189D81F46','US','VA','VIRGINIA',1)
/
INSERT INTO KR_STATE_T (ACTV_IND,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_STATE_CD,POSTAL_STATE_NM,VER_NBR)
  VALUES ('Y','56908AA37A44661BE0404F8189D81F46','US','VI','VIRGIN ISLANDS',1)
/
INSERT INTO KR_STATE_T (ACTV_IND,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_STATE_CD,POSTAL_STATE_NM,VER_NBR)
  VALUES ('Y','56908AA37A36661BE0404F8189D81F46','US','VT','VERMONT',1)
/
INSERT INTO KR_STATE_T (ACTV_IND,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_STATE_CD,POSTAL_STATE_NM,VER_NBR)
  VALUES ('Y','56908AA37A37661BE0404F8189D81F46','US','WA','WASHINGTON',1)
/
INSERT INTO KR_STATE_T (ACTV_IND,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_STATE_CD,POSTAL_STATE_NM,VER_NBR)
  VALUES ('Y','56908AA37A38661BE0404F8189D81F46','US','WI','WISCONSIN',1)
/
INSERT INTO KR_STATE_T (ACTV_IND,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_STATE_CD,POSTAL_STATE_NM,VER_NBR)
  VALUES ('Y','56908AA37A39661BE0404F8189D81F46','US','WV','WEST VIRGINIA',1)
/
INSERT INTO KR_STATE_T (ACTV_IND,OBJ_ID,POSTAL_CNTRY_CD,POSTAL_STATE_CD,POSTAL_STATE_NM,VER_NBR)
  VALUES ('Y','56908AA37A3A661BE0404F8189D81F46','US','WY','WYOMING',1)
/
