package org.kuali.hr.time.util;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

public class TagSupport {

	private static final Logger LOG = Logger.getLogger(TagSupport.class);
	private Map<Long,List<String>> assignments;
	private Map<Long,List<String>> earnCodes;

	public Integer getAssignmentSize() {

		Integer i = 0;

		for(Map.Entry<Long, List<String>> assignmentMap : assignments.entrySet()) {
			for(String assignment : assignmentMap.getValue()) {
				i++;
			}
		}

		return i;
	}

	public Integer getEarnCodeSize() {

		Integer i = 0;

		for(Map.Entry<Long, List<String>> earnCodeMap : earnCodes.entrySet()) {
			for(String earnCode : earnCodeMap.getValue()) {
				i++;
			}
		}

		return i;
	}

	public Map<Long, List<String>> getAssignments() {
		return assignments;
	}

	public void setAssignments(Map<Long, List<String>> assignments) {
		this.assignments = assignments;
	}

	public Map<Long, List<String>> getEarnCodes() {
		return earnCodes;
	}

	public void setEarnCodes(Map<Long, List<String>> earnCodes) {
		this.earnCodes = earnCodes;
	}
}
