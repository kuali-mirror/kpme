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
