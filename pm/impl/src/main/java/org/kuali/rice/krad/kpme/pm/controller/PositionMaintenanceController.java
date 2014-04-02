/**
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
package org.kuali.rice.krad.kpme.pm.controller;

import org.apache.commons.lang.StringUtils;
import org.kuali.kpme.pm.position.PositionBo;
import org.kuali.rice.krad.kpme.controller.EffectiveDateMaintenanceController;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krad.uif.UifParameters;
import org.kuali.rice.krad.uif.view.DialogManager;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.web.form.DocumentFormBase;
import org.kuali.rice.krad.web.form.MaintenanceDocumentForm;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Properties;
import java.util.concurrent.Callable;


@Controller
@RequestMapping(value = "/kpme/positionMaintenance")
public class PositionMaintenanceController extends EffectiveDateMaintenanceController {

    private static final String KPME_PROCESS_CHANGE_WARNING_DIALOG = "ProcessChangeWarning-Dialog";

    @Override
    @RequestMapping(params = "methodToCall=returnFromLightbox")
    public ModelAndView returnFromLightbox(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                                           HttpServletRequest request, HttpServletResponse response) {
        String newMethodToCall = "";

        // Save user responses from dialog
        DialogManager dm = form.getDialogManager();
        String dialogId = dm.getCurrentDialogId();
        if (dialogId == null) {
            // may have been invoked by client.
            // TODO:  handle this case (scheduled for 2.2-m3)
            // for now, log WARNING and default to start, can we add a growl?
            newMethodToCall = "start";
        } else {
            dm.setDialogAnswer(dialogId, form.getDialogResponse());
            dm.setDialogExplanation(dialogId, form.getDialogExplanation());
            newMethodToCall = dm.getDialogReturnMethod(dialogId);
            dm.setCurrentDialogId(null);
        }


        PositionBo aPositionBo = (PositionBo) ((MaintenanceDocumentForm) form).getDocument().getNewMaintainableObject().getDataObject();
        // call intended controller method
        Properties props = new Properties();
        props.put(UifParameters.METHOD_TO_CALL, newMethodToCall);
        props.put(UifParameters.VIEW_ID, form.getViewId());
        props.put(UifParameters.FORM_KEY, form.getFormKey());
        props.put(UifParameters.AJAX_REQUEST, "false");
        props.put("hrPositionId",aPositionBo.getHrPositionId());
        props.put("oldProcess",request.getParameter("oldProcess"));
        props.put("newProcess",request.getParameter("newProcess"));
        return performRedirect(form, form.getFormPostUrl(), props);
    }

    @RequestMapping(params = "methodToCall=changeProcess")
    public ModelAndView changeProcess(@ModelAttribute("KualiForm") final MaintenanceDocumentForm form, final BindingResult result,
                                        final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        return showProcessChangeWarningIfNeeded(form, result, request, response, new Callable<ModelAndView>() {
            @Override
            public ModelAndView call() throws Exception {
                // register the superclass method as the handler
                setupMaintenance(form, request, KRADConstants.MAINTENANCE_EDIT_ACTION);
                String newProcess = request.getParameter("newProcess");

                MaintenanceDocument maintenanceDocument = (MaintenanceDocument) form.getDocument();
                ((PositionBo)maintenanceDocument.getNewMaintainableObject().getDataObject()).setProcess(newProcess);
                return getUIFModelAndView(form);
            }
        });
    }




    // This method contains the common pre-handler logic to show the process change warning dialog for any particular handler action that we wish to 'intercept'.
    // The callable parameter encapsulates the actual super class handler method to be invoked in case of either an absence of warning or user's confirmation on the warning.
    protected ModelAndView showProcessChangeWarningIfNeeded(@ModelAttribute("KualiForm") DocumentFormBase form,
                                                            BindingResult result,
                                                            HttpServletRequest request,
                                                            HttpServletResponse response,
                                                            Callable<ModelAndView> callable) throws Exception {

        ModelAndView retVal = null;
        String oldProcess = request.getParameter("oldProcess");

            if (!hasDialogBeenAnswered(KPME_PROCESS_CHANGE_WARNING_DIALOG, form)) {
                if (StringUtils.isEmpty(oldProcess)) {
                    retVal = getUIFModelAndView(form);
                } else {
                    // redirect back to client to display lightbox
                    retVal = showDialog(KPME_PROCESS_CHANGE_WARNING_DIALOG, form, request, response);
                }
            } else {
                 boolean areYouSure = getBooleanDialogResponse(KPME_PROCESS_CHANGE_WARNING_DIALOG, form, request, response);
                // clear all dialogs in order to display warning again
                form.getDialogManager().removeAllDialogs();
                if (areYouSure) {
                    // call the registered handler method; user has confirmed
                    retVal = callable.call();
                }
                else {
                    // revert to old process

                    MaintenanceDocument maintenanceDocument = (MaintenanceDocument) form.getDocument();
                    ((PositionBo)maintenanceDocument.getNewMaintainableObject().getDataObject()).setProcess(oldProcess);
                    retVal = getUIFModelAndView(form);
                }
            }
        return retVal;
    }

}