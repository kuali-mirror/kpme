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
