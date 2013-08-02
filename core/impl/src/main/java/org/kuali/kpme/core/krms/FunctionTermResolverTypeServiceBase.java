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
