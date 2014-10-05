package org.kuali.kpme.edo.base.web;

import javax.servlet.http.HttpServletRequest;

import org.kuali.rice.krad.web.controller.UifControllerBase;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping(value = "/edoHome")
public class EdoHomeController extends UifControllerBase {

	@Override
	protected UifFormBase createInitialForm(HttpServletRequest request) {
		return new EdoUifForm();
	}

}
