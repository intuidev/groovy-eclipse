/*******************************************************************************
 * Copyright (c) 2011 Codehaus.org, SpringSource, and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *      Andrew Eisenberg - Initial implemenation
 *******************************************************************************/
package org.codehaus.groovy.eclipse.dsl.pointcuts.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.codehaus.groovy.ast.expr.MethodCallExpression;
import org.codehaus.groovy.eclipse.dsl.pointcuts.AbstractPointcut;
import org.codehaus.groovy.eclipse.dsl.pointcuts.GroovyDSLDContext;
import org.codehaus.groovy.eclipse.dsl.pointcuts.IPointcut;
import org.codehaus.groovy.eclipse.dsl.pointcuts.PointcutVerificationException;
import org.eclipse.core.resources.IStorage;
import org.eclipse.jdt.groovy.search.VariableScope.CallAndType;

/**
 * Tests that the enclosing call matches certain charactereistics, such as names 
 * arguments, and values
 * @author andrew
 * @created Feb 10, 2011
 */
public class EnclosingCallPointcut extends AbstractPointcut {

    public EnclosingCallPointcut(IStorage containerIdentifier, String pointcutName) {
        super(containerIdentifier, pointcutName);
    }

    @Override
    public Collection<?> matches(GroovyDSLDContext pattern, Object toMatch) {
        List<CallAndType> enclosing = pattern.getCurrentScope().getAllEnclosingMethodCallExpressions();
        if (enclosing == null || enclosing.isEmpty()) {
            return null;
        }

        Object firstArgument = getFirstArgument();
        if (firstArgument instanceof String) {
            return matchesInCalls(enclosing, (String) firstArgument, pattern);
        } else if (firstArgument == null) {
            return asCallList(enclosing);
        } else {
            return matchOnPointcutArgument((IPointcut) firstArgument, pattern, asCallList(enclosing));
        }
    }
    
    private List<MethodCallExpression> asCallList(List<CallAndType> enclosing) {
        List<MethodCallExpression> types = new ArrayList<MethodCallExpression>(enclosing.size());
        for (CallAndType callAndType : enclosing) {
            types.add(callAndType.call);
        }
        return types;
    }

    private List<MethodCallExpression> matchesInCalls(List<CallAndType> enclosing,
            String callName, GroovyDSLDContext pattern) {
        List<MethodCallExpression> calls = null;
        for (CallAndType callAndType : enclosing) {
            if (callName == null || callName.equals(callAndType.call.getMethodAsString())) {
                if (calls == null) {
                    calls = new ArrayList<MethodCallExpression>(1);
                }
                calls.add(callAndType.call);
            }
        }
        return calls;
    }


    /**
     * expecting one arg that is either a string or a pointcut
     */
    @Override
    public void verify() throws PointcutVerificationException {
        String hasOneOrNoArgs = hasOneOrNoArgs();
        if (hasOneOrNoArgs != null) {
            throw new PointcutVerificationException(hasOneOrNoArgs, this);
        }
        super.verify();
    }
}