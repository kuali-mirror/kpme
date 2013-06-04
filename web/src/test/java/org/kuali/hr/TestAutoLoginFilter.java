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
package org.kuali.hr;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.krad.web.filter.AutoLoginFilter;

/**
 * Automatically logs in with the user specified via filter init parameter {@link AutoLoginFilter#USER_PARAM_NAME}.
 * <p>
 * There are no guarantees made that the user specified is a valid user in the system.
 * </p>
 * <p>
 * In rice this Filter can be used via config like that following assuming the bootstrap filter is used: <br />
 * {@code <param name="filter.login.class">org.kuali.kra.test.infrastructure.AutoLoginFilter</param>} <br />
 * {@code <param name="filtermapping.login.1">/*</param>} <br />
 * {@code <param name="filter.login.autouser">admin</param>} <br />
 * </p>
 */
public class TestAutoLoginFilter implements Filter {
    public static final String USER_PARAM_NAME = "autouser";
    public static String OVERRIDE_ID = "";

    private FilterConfig filterConfig;

    /** {@inheritDoc} */
    public void init(FilterConfig config) throws ServletException {
        this.filterConfig = config;
    }

    /** {@inheritDoc} */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String username = StringUtils.isBlank(OVERRIDE_ID) ? filterConfig.getInitParameter(USER_PARAM_NAME) : OVERRIDE_ID;
        if (username == null) {
            throw new IllegalStateException("the " + USER_PARAM_NAME + " param is not set");
        }

        chain.doFilter(new HttpServletRequestWrapper((HttpServletRequest) request) {
            @Override
            public String getRemoteUser() {
                String username = TestAutoLoginFilter.OVERRIDE_ID;
                if (StringUtils.isBlank(username)) {
                    username = TestAutoLoginFilter.this.filterConfig.getInitParameter(USER_PARAM_NAME);
                }
                return username;
            }
        }, response);
    }

    /** {@inheritDoc} */
    public void destroy() {
        this.filterConfig = null;
    }
}
