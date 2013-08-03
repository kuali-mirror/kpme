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

import org.kuali.kpme.core.krms.function.FunctionParamComparator;
import org.kuali.kpme.core.krms.function.FunctionTermResolver;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.rice.krms.api.engine.TermResolver;
import org.kuali.rice.krms.api.repository.function.FunctionDefinition;
import org.kuali.rice.krms.api.repository.function.FunctionParameterDefinition;
import org.kuali.rice.krms.api.repository.function.FunctionRepositoryService;
import org.kuali.rice.krms.api.repository.term.TermResolverDefinition;
import org.kuali.rice.krms.framework.type.TermResolverTypeService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;


public abstract class FunctionTermResolverTypeServiceBase implements TermResolverTypeService {

    public abstract FunctionTermResolver createFunctionResolver(List<String> orderedInputParams, Set<String> parameterNames,
                                                                String output, FunctionDefinition functionTerm);

    @Override
    public TermResolver<?> loadTermResolver(TermResolverDefinition termResolverDefinition) {
        String functionId = termResolverDefinition.getOutput().getName();
        FunctionRepositoryService functionRepositoryService = HrServiceLocator.getService("functionRepositoryService");
        FunctionDefinition functionTerm = functionRepositoryService.getFunction(functionId);
        if(functionTerm==null){
            throw new RuntimeException("Not able to find Term for function : "+functionId);
        }
        List<String> params = getFunctionParameters(functionTerm);
        Set<String> paramNames = termResolverDefinition.getParameterNames();
        return createFunctionResolver(params,paramNames,functionId,functionTerm);
    }

    private List<String> getFunctionParameters(FunctionDefinition functionTerm) {
        List<FunctionParameterDefinition> functionParams = functionTerm.getParameters();
        List<FunctionParameterDefinition> modifiableParams = new ArrayList<FunctionParameterDefinition>(functionParams);
        Collections.sort(modifiableParams, new FunctionParamComparator());
        List<String> params = new ArrayList<String>();
        for (FunctionParameterDefinition termResolverFunctionParamDefinition : modifiableParams) {
            params.add(termResolverFunctionParamDefinition.getName());
        }
        return params;
    }

}
