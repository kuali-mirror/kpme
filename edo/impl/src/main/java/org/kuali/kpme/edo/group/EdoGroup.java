package org.kuali.kpme.edo.group;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.log4j.Logger;
import org.kuali.kpme.edo.util.EdoConstants;
import org.kuali.rice.kim.api.group.Group;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.kim.api.type.KimType;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * $HeadURL$
 * $Revision$
 * Created with IntelliJ IDEA.
 * User: bradleyt
 * Date: 12/11/13
 * Time: 2:43 PM
 */
public class EdoGroup {
    private static final Logger LOG = Logger.getLogger(EdoGroup.class);

    private String groupName;
    private String kimTypeName;
    private String kimGroupId;
    private String kimRoleName;
    private Map<String, String> grpAttributes;

    public EdoGroup() {
        grpAttributes = new HashMap<String, String>();
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getKimTypeName() {
        return kimTypeName;
    }

    public void setKimTypeName(String kimTypeName) {
        this.kimTypeName = kimTypeName;
    }

    public String getKimGroupId() {
        return kimGroupId;
    }

    public void setKimGroupId(String kimGroupId) {
        this.kimGroupId = kimGroupId;
    }

    public String getKimRoleName() {
        return kimRoleName;
    }

    public void setKimRoleName(String kimRoleName) {
        this.kimRoleName = kimRoleName;
    }

    public Map<String, String> getGrpAttributes() {
        return grpAttributes;
    }

    public void setGrpAttributes(Map<String, String> grpAttributes) {
        this.grpAttributes = grpAttributes;
    }

    public boolean isEmpty() {
        boolean flag = false;
        if (this.groupName == null) {
            flag = true;
        }
        return flag;
    }

    public Group addGroup() {

        KimType kimType = KimApiServiceLocator.getKimTypeInfoService().findKimTypeByNameAndNamespace(EdoConstants.EDO_NAME_SPACE, this.kimTypeName);
        Group.Builder groupInfo = Group.Builder.create(EdoConstants.EDO_NAME_SPACE, this.groupName, kimType.getId());
        groupInfo.setActive(true);
        groupInfo.setAttributes(this.grpAttributes);

        Group grp = KimApiServiceLocator.getGroupService().createGroup(groupInfo.build());
        this.setKimGroupId(grp.getId());

        return grp;
    }

    public Group disableGroup() {

        //Group grp = KimApiServiceLocator.getGroupService().getGroupByNamespaceCodeAndName(EdoConstants.EDO_NAME_SPACE, this.groupName);
        KimType kimType = KimApiServiceLocator.getKimTypeInfoService().findKimTypeByNameAndNamespace(EdoConstants.EDO_NAME_SPACE, this.kimTypeName);
        Group.Builder groupInfo = Group.Builder.create(EdoConstants.EDO_NAME_SPACE, this.groupName, kimType.getId());
        groupInfo.setActive(false);

        Group grp = KimApiServiceLocator.getGroupService().updateGroup(groupInfo.build());

        return grp;
    }

    public void addGroupAttribute(String grpAttributeName, String grpAttributeValue) {

        this.grpAttributes.put(grpAttributeName, grpAttributeValue);

        return;
    }

    public String getEdoGroupJSONString() {
        ArrayList<String> tmp = new ArrayList<String>();
        Type tmpType = new TypeToken<List<String>>() {}.getType();
        Gson gson = new Gson();

        tmp.add(this.getGroupName());
        tmp.add(this.getKimRoleName());
        tmp.add(this.getKimGroupId());
        tmp.add(this.getKimTypeName());

        return gson.toJson(tmp, tmpType).toString();
    }

}
