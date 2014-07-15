package org.kuali.kpme.edo.candidate.guest.web;

import javax.servlet.http.HttpServletRequest;

import org.kuali.kpme.edo.base.web.EdoUifForm;
import org.kuali.rice.krad.web.controller.UifControllerBase;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping(value = "/edoGuests")
public class EdoGuestsController extends UifControllerBase {

	@Override
	protected UifFormBase createInitialForm(HttpServletRequest request) {
		return new EdoGuestsForm();
	}

}