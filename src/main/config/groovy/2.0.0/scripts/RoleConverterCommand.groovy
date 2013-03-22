/**
 * Copyright 2005-2013 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License")
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import java.sql.Connection

class RoleConverterCommand extends RecordSelectPerform {

    def dbTypes = ['oracle', 'mysql']
    def systemDates = ['oracle':'SYSDATE', 'mysql':'NOW()']
    def uniqueIds = ['oracle':'SYS_GUID()', 'mysql':'UUID()']
    
    String dbType
	
	def RoleConverterCommand() {
		super("HR_ROLES_T")
	}
	
	def perform(Connection c, List<String> args) {
	   dbType = args[0]
	   
	   if (dbTypes.contains(dbType)) {
    	   super.perform(c, args)
        } else {
            println "ERROR: Cannot generate database statements for database type ${dbType}"
        }
	}
	
	def String generateSelectSql() {
		"SELECT * FROM ${this.table}"
	}
	
	def handleRow(row) {
        def actions = []

        switch (row['ROLE_NAME']) {
            case 'TK_SYS_ADMIN':
                if (row['PRINCIPAL_ID'] != 'admin') {
                    actions << generateInsertKimGroupMember("KPME-HR", "System Administrator", quote(row['PRINCIPAL_ID']), quote(row['EFFDT']), quote(row['EXPDT']), quote(row['TIMESTAMP']), row['ACTIVE'])
                }
                break
            case 'TK_GLOBAL_VO':
                actions << generateInsertKimGroupMember("KPME-HR", "System View Only Group", quote(row['PRINCIPAL_ID']), quote(row['EFFDT']), quote(row['EXPDT']), quote(row['TIMESTAMP']), row['ACTIVE'])
                break
            case 'TK_ORG_ADMIN':
                actions << generateInsertKimLocationRoleMember("KPME-TK", "Time Location Administrator", quote(row['PRINCIPAL_ID']), quote(row['EFFDT']), quote(row['EXPDT']), quote(row['TIMESTAMP']), row['ACTIVE'], quote(row['CHART']))
                break
            case 'TK_LOCATION_VO':
                actions << generateInsertKimLocationRoleMember("KPME-TK", "Time Location View Only", quote(row['PRINCIPAL_ID']), quote(row['EFFDT']), quote(row['EXPDT']), quote(row['TIMESTAMP']), row['ACTIVE'], quote(row['CHART']))
                break
            case 'TK_DEPT_ADMIN':
                actions << generateInsertKimDepartmentRoleMember("KPME-TK", "Time Department Administrator", quote(row['PRINCIPAL_ID']), quote(row['EFFDT']), quote(row['EXPDT']), quote(row['TIMESTAMP']), row['ACTIVE'], quote(row['DEPT']))
                break
            case 'TK_DEPT_VO':
                actions << generateInsertKimDepartmentRoleMember("KPME-TK", "Time Department View Only", quote(row['PRINCIPAL_ID']), quote(row['EFFDT']), quote(row['EXPDT']), quote(row['TIMESTAMP']), row['ACTIVE'], quote(row['DEPT']))
                break
            case 'LV_DEPT_ADMIN':
                actions << generateInsertKimDepartmentRoleMember("KPME-LM", "Leave Department Administrator", quote(row['PRINCIPAL_ID']), quote(row['EFFDT']), quote(row['EXPDT']), quote(row['TIMESTAMP']), row['ACTIVE'], quote(row['DEPT']))
                break
            case 'LV_DEPT_VO':
                actions << generateInsertKimDepartmentRoleMember("KPME-LM", "Leave Department View Only", quote(row['PRINCIPAL_ID']), quote(row['EFFDT']), quote(row['EXPDT']), quote(row['TIMESTAMP']), row['ACTIVE'], quote(row['DEPT']))
                break
            case 'TK_APPROVER':
                actions << generateInsertKimWorkAreaRoleMember("KPME-HR", "Approver", quote(row['PRINCIPAL_ID']), quote(row['POSITION_NBR']), quote(row['EFFDT']), quote(row['EXPDT']), quote(row['TIMESTAMP']), row['ACTIVE'], quote(row['WORK_AREA']))
                break
            case 'TK_APPROVER_DELEGATE':
                actions << generateInsertKimWorkAreaRoleMember("KPME-HR", "Approver Delegate", quote(row['PRINCIPAL_ID']), quote(row['POSITION_NBR']), quote(row['EFFDT']), quote(row['EXPDT']), quote(row['TIMESTAMP']), row['ACTIVE'], quote(row['WORK_AREA']))
                break
            case 'TK_REVIEWER':
                actions << generateInsertKimWorkAreaRoleMember("KPME-HR", "Reviewer", quote(row['PRINCIPAL_ID']), quote(row['POSITION_NBR']), quote(row['EFFDT']), quote(row['EXPDT']), quote(row['TIMESTAMP']), row['ACTIVE'], quote(row['WORK_AREA']))
                break
            case 'TK_EMPLOYEE':
                actions << generateInsertKimDefaultRoleMember("KPME-HR", "Employee", quote(row['PRINCIPAL_ID']), quote(row['EFFDT']), quote(row['EXPDT']), quote(row['TIMESTAMP']), row['ACTIVE'])
    		    break
        }

		actions
	}
	
	def Object quote(Object object) {
	   if (object != null) {
	       return "'${object}'";
	   } else {
	       return object;
	   }
	}
    
    def String generateInsertKimGroupMember(String namespaceCode, String groupName, String principalId, String effectiveDate, String expirationDate, String timestamp, String active) {
        String insertStatement = "";
        
        if (active != 'Y') {
            expirationDate = timestamp;
        }
        
        if (dbType == 'mysql') {
            insertStatement += "INSERT INTO KRIM_GRP_MBR_ID_S VALUES (NULL);"
            insertStatement += System.getProperty("line.separator")
        }
        insertStatement += "INSERT INTO KRIM_GRP_MBR_T (GRP_MBR_ID, GRP_ID, MBR_ID, MBR_TYP_CD, ACTV_FRM_DT, ACTV_TO_DT, LAST_UPDT_DT, OBJ_ID, VER_NBR) VALUES (${generateNextInsert('KRIM_GRP_MBR_ID_S')}, (SELECT GRP_ID FROM KRIM_GRP_T WHERE NMSPC_CD = '${namespaceCode}' AND GRP_NM = '${groupName}'), ${principalId}, 'P', ${effectiveDate}, ${expirationDate}, ${timestamp}, ${uniqueIds[dbType]}, '1');"
    
        return insertStatement;
    }
    
    def String generateInsertKimLocationRoleMember(String namespaceCode, String roleName, String principalId, String effectiveDate, String expirationDate, String timestamp, String active, String location) {
        String insertStatement = "";
        
        if (active != 'Y') {
            expirationDate = timestamp;
        }
        
        if (dbType == 'mysql') {
            insertStatement += "INSERT INTO KRIM_ROLE_MBR_ID_S VALUES (NULL);"
            insertStatement += System.getProperty("line.separator")
        }
        insertStatement += "INSERT INTO KRIM_ROLE_MBR_T (ROLE_MBR_ID, ROLE_ID, MBR_ID, MBR_TYP_CD, ACTV_FRM_DT, ACTV_TO_DT, LAST_UPDT_DT, OBJ_ID, VER_NBR) VALUES (${generateNextInsert('KRIM_ROLE_MBR_ID_S')}, (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = '${namespaceCode}' AND ROLE_NM = '${roleName}'), ${principalId}, 'P', ${effectiveDate}, ${expirationDate}, ${timestamp}, ${uniqueIds[dbType]}, '1');"
        insertStatement += System.getProperty("line.separator")
        if (dbType == 'mysql') {
            insertStatement += "INSERT INTO KRIM_ATTR_DATA_ID_S VALUES (NULL);"
            insertStatement += System.getProperty("line.separator")
        }
        insertStatement += "INSERT INTO KRIM_ROLE_MBR_ATTR_DATA_T (ATTR_DATA_ID, KIM_ATTR_DEFN_ID, KIM_TYP_ID, ROLE_MBR_ID, ATTR_VAL, OBJ_ID, VER_NBR) VALUES (${generateNextInsert('KRIM_ATTR_DATA_ID_S')}, (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'location'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Location'), ${generateCurrentInsert('KRIM_ROLE_MBR_ID_S')}, ${location}, ${uniqueIds[dbType]}, '1');"
        
        return insertStatement;
    }
    
    def String generateInsertKimDepartmentRoleMember(String namespaceCode, String roleName, String principalId, String effectiveDate, String expirationDate, String timestamp, String active, String department) {
        String insertStatement = "";
        
        if (active != 'Y') {
            expirationDate = timestamp;
        }
        
        if (dbType == 'mysql') {
            insertStatement += "INSERT INTO KRIM_ROLE_MBR_ID_S VALUES (NULL);"
            insertStatement += System.getProperty("line.separator")
        }
        insertStatement += "INSERT INTO KRIM_ROLE_MBR_T (ROLE_MBR_ID, ROLE_ID, MBR_ID, MBR_TYP_CD, ACTV_FRM_DT, ACTV_TO_DT, LAST_UPDT_DT, OBJ_ID, VER_NBR) VALUES (${generateNextInsert('KRIM_ROLE_MBR_ID_S')}, (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = '${namespaceCode}' AND ROLE_NM = '${roleName}'), ${principalId}, 'P', ${effectiveDate}, ${expirationDate}, ${timestamp}, " + uniqueIds[dbType] + ", '1');"
        insertStatement += System.getProperty("line.separator")
        if (dbType == 'mysql') {
            insertStatement += "INSERT INTO KRIM_ATTR_DATA_ID_S VALUES (NULL);"
            insertStatement += System.getProperty("line.separator")
        }
        insertStatement += "INSERT INTO KRIM_ROLE_MBR_ATTR_DATA_T (ATTR_DATA_ID, KIM_ATTR_DEFN_ID, KIM_TYP_ID, ROLE_MBR_ID, ATTR_VAL, OBJ_ID, VER_NBR) VALUES (${generateNextInsert('KRIM_ATTR_DATA_ID_S')}, (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'department'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Department'), ${generateCurrentInsert('KRIM_ROLE_MBR_ID_S')}, ${department}, ${uniqueIds[dbType]}, '1');"
    
        return insertStatement;
    }
    
    def String generateInsertKimWorkAreaRoleMember(String namespaceCode, String roleName, String principalId, String positionNumber, String effectiveDate, String expirationDate, String timestamp, String active, String workArea) {
        String insertStatement = "";
        
        if (active != 'Y') {
            expirationDate = timestamp;
        }
        
        if (principalId != null) {
            if (dbType == 'mysql') {
                insertStatement += "INSERT INTO KRIM_ROLE_MBR_ID_S VALUES (NULL);"
                insertStatement += System.getProperty("line.separator")
            }
            insertStatement += "INSERT INTO KRIM_ROLE_MBR_T (ROLE_MBR_ID, ROLE_ID, MBR_ID, MBR_TYP_CD, ACTV_FRM_DT, ACTV_TO_DT, LAST_UPDT_DT, OBJ_ID, VER_NBR) VALUES (${generateNextInsert('KRIM_ROLE_MBR_ID_S')}, (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = '${namespaceCode}' AND ROLE_NM = '${roleName}'), ${principalId}, 'P', ${effectiveDate}, ${expirationDate}, ${timestamp}, " + uniqueIds[dbType] + ", '1');"
            insertStatement += System.getProperty("line.separator")
            if (dbType == 'mysql') {
                insertStatement += "INSERT INTO KRIM_ATTR_DATA_ID_S VALUES (NULL);"
                insertStatement += System.getProperty("line.separator")
            }
            insertStatement += "INSERT INTO KRIM_ROLE_MBR_ATTR_DATA_T (ATTR_DATA_ID, KIM_ATTR_DEFN_ID, KIM_TYP_ID, ROLE_MBR_ID, ATTR_VAL, OBJ_ID, VER_NBR) VALUES (${generateNextInsert('KRIM_ATTR_DATA_ID_S')}, (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'workArea'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Work Area'), ${generateCurrentInsert('KRIM_ROLE_MBR_ID_S')}, ${workArea}, ${uniqueIds[dbType]}, '1');"
        } else if (positionNumber != null) {
            if (dbType == 'mysql') {
                insertStatement += "INSERT INTO KRIM_ROLE_MBR_ID_S VALUES (NULL);"
                insertStatement += System.getProperty("line.separator")
            }
            insertStatement += "INSERT INTO KRIM_ROLE_MBR_T (ROLE_MBR_ID, ROLE_ID, MBR_ID, MBR_TYP_CD, ACTV_FRM_DT, ACTV_TO_DT, LAST_UPDT_DT, OBJ_ID, VER_NBR) VALUES (${generateNextInsert('KRIM_ROLE_MBR_ID_S')}, (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = '${namespaceCode}' AND ROLE_NM = '${roleName}'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'Derived Role : Position'), 'R', ${effectiveDate}, ${expirationDate}, ${timestamp}, ${uniqueIds[dbType]}, '1');"
            insertStatement += System.getProperty("line.separator")
            if (dbType == 'mysql') {
                insertStatement += "INSERT INTO KRIM_ATTR_DATA_ID_S VALUES (NULL);"
                insertStatement += System.getProperty("line.separator")
            }
            insertStatement += "INSERT INTO KRIM_ROLE_MBR_ATTR_DATA_T (ATTR_DATA_ID, KIM_ATTR_DEFN_ID, KIM_TYP_ID, ROLE_MBR_ID, ATTR_VAL, OBJ_ID, VER_NBR) VALUES (${generateNextInsert('KRIM_ATTR_DATA_ID_S')}, (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'workArea'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Work Area'), ${generateCurrentInsert('KRIM_ROLE_MBR_ID_S')}, ${workArea}, ${uniqueIds[dbType]}, '1');"
            insertStatement += System.getProperty("line.separator")
            if (dbType == 'mysql') {
                insertStatement += "INSERT INTO KRIM_ATTR_DATA_ID_S VALUES (NULL);"
                insertStatement += System.getProperty("line.separator")
            }
            insertStatement += "INSERT INTO KRIM_ROLE_MBR_ATTR_DATA_T (ATTR_DATA_ID, KIM_ATTR_DEFN_ID, KIM_TYP_ID, ROLE_MBR_ID, ATTR_VAL, OBJ_ID, VER_NBR) VALUES (${generateNextInsert('KRIM_ATTR_DATA_ID_S')}, (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'position'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Position'), ${generateCurrentInsert('KRIM_ROLE_MBR_ID_S')}, ${positionNumber}, ${uniqueIds[dbType]}, '1');"
        } else {
            insertStatement += 'ERROR: Role member found without principalId and positionNumber'
        }
        
        return insertStatement;
    }
    
    def String generateInsertKimDefaultRoleMember(String namespaceCode, String roleName, String principalId, String effectiveDate, String expirationDate, String timestamp, String active) {
        String insertStatement = "";
        
        if (active != 'Y') {
            expirationDate = timestamp;
        }
        
        if (dbType == 'mysql') {
            insertStatement += "INSERT INTO KRIM_ROLE_MBR_ID_S VALUES (NULL);"
            insertStatement += System.getProperty("line.separator")
        }
        insertStatement += "INSERT INTO KRIM_ROLE_MBR_T (ROLE_MBR_ID, ROLE_ID, MBR_ID, MBR_TYP_CD, ACTV_FRM_DT, ACTV_TO_DT, LAST_UPDT_DT, OBJ_ID, VER_NBR) VALUES (${generateNextInsert('KRIM_ROLE_MBR_ID_S')}, (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = '${namespaceCode}' AND ROLE_NM = '${roleName}'), ${principalId}, 'P', ${effectiveDate}, ${expirationDate}, ${timestamp}, ${uniqueIds[dbType]}, '1');"
    
        return insertStatement;
    }
    
    def String generateNextInsert(String sequenceName) {
        String nextInsert = "";
        
        if (dbType == 'oracle') {
            nextInsert += "${sequenceName}.NEXTVAL"
        } else if (dbType == 'mysql') {
            nextInsert += "(SELECT MAX(ID) FROM ${sequenceName})"
        }
    }
    
    def String generateCurrentInsert(String sequenceName) {
        String nextInsert = "";
        
        if (dbType == 'oracle') {
            nextInsert += "${sequenceName}.CURRVAL"
        } else if (dbType == 'mysql') {
            nextInsert += "(SELECT MAX(ID) FROM ${sequenceName})"
        }
    }
    
	def help() {
        return "Converts ${this.table} to KIM records."
    }

}