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
package org.kuali.kpme.core.krms;


import org.kuali.kpme.core.krms.function.FunctionTermResolver;
import org.kuali.kpme.core.krms.function.JavaFunctionResolver;
import org.kuali.rice.krms.api.repository.function.FunctionDefinition;

import java.util.List;
import java.util.Set;

public class JavaFunctionTermResolverService extends FunctionTermResolverTypeServiceBase {
    @Override
    public FunctionTermResolver createFunctionResolver(List<String> functionParams, Set<String> termResolverParams, String output, FunctionDefinition functionTerm) {
        return new JavaFunctionResolver(functionParams,termResolverParams,output,functionTerm);
    }
}
