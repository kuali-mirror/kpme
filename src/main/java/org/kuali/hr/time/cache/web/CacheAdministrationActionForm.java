package org.kuali.hr.time.cache.web;

import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;
import org.kuali.rice.kns.web.struts.form.KualiForm;

public class CacheAdministrationActionForm extends KualiForm {
	
    private static final long serialVersionUID = -4886811168526878141L;

	private Map<String, Set<String>> groups;
	private String itemKey;
	private String groupKey;
	
	public Map<String, Set<String>> getGroups() {
    	return groups;
    }

	public void setGroups(Map<String, Set<String>> groups) {
		this.groups = groups;
    }

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		setItemKey(null);
		setGroupKey(null);
    }

	public String getItemKey() {
    	return itemKey;
    }

	public void setItemKey(String itemKey) {
    	this.itemKey = itemKey;
    }

	public String getGroupKey() {
    	return groupKey;
    }

	public void setGroupKey(String groupKey) {
    	this.groupKey = groupKey;
    }
	
}
