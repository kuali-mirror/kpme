/*
 * Copyright 2004-2014 The Kuali Foundation
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
/**
 * This Javascript file added to resolve issue (KPME-3291) with Position Classification Maint doc and Inquiry due
 * to Navigation link : When one of the option like Funding, Qualification, Flags etc are disabled then
 * render property generates blank <li> element which displays extra lines in Navigation Group.
 * 
 *  This will be resolved with Rice 2.5, After updated to the Rice version 2.5, This file might not be 
 *  requried.
 */
jQuery(document).ready(function () {
	jQuery("#positionMenuNavigation li,#positionInquiryMenuNavigation li").each(function () {
		if(!jQuery.trim(jQuery(this).html())){
			jQuery(this).hide();
		}
	});
});
