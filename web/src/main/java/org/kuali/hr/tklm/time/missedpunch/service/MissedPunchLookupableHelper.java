package org.kuali.hr.tklm.time.missedpunch.service;
/**
 * Copyright 2004-2013 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
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
//package org.kuali.hr.tklm.time.missedpunch.service;
//
//import java.util.List;
//
//import org.kuali.hr.tklm.time.missedpunch.MissedPunch;
//import org.kuali.hr.tklm.time.util.TKContext;
//import org.kuali.rice.kns.bo.BusinessObject;
//import org.kuali.rice.kns.lookup.HtmlData;
//import org.kuali.rice.kns.lookup.KualiLookupableHelperServiceImpl;
//
//public class MissedPunchLookupableHelper extends
//		KualiLookupableHelperServiceImpl {
//	/**
//	 * 
//	 */
//	private static final long serialVersionUID = 1L;
//
//	@Override
//	public List<HtmlData> getCustomActionUrls(BusinessObject businessObject,
//			List pkNames) {
//		List<HtmlData> customActionUrls = super.getCustomActionUrls(
//				businessObject, pkNames);
//		if (TKUser.getCurrentRoles().isSystemAdmin()) {
//			MissedPunch missedPunch = (MissedPunch) businessObject;
//			final String className = this.getBusinessObjectClass().getName();
//			final Long tkMissedPunchId = missedPunch.getTkMissedPunchId();
//			HtmlData htmlData = new HtmlData() {
//
//				@Override
//				public String constructCompleteHtmlTag() {
//					return "<a target=\"_blank\" href=\"inquiry.do?businessObjectClassName="
//							+ className
//							+ "&methodToCall=start&tkMissedPunchId="
//							+ tkMissedPunchId + "\">view</a>";
//				}
//			};
//			customActionUrls.add(htmlData);
//		} else if (customActionUrls.size() != 0) {
//			customActionUrls.remove(0);
//		}
//		return customActionUrls;
//	}
//}
