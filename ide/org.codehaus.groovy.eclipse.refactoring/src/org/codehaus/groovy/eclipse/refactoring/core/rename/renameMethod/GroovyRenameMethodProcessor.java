/*
 * Copyright 2003-2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.codehaus.groovy.eclipse.refactoring.core.rename.renameMethod;

import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.internal.corext.refactoring.rename.RenameMethodProcessor;

/**
 * @author Ajay Singh Rathore
 * @created Dec 15, 2009
 *
 */

public class GroovyRenameMethodProcessor extends RenameMethodProcessor{

	protected GroovyRenameMethodProcessor(IMethod method) {
		super(method);
	}

	public String getDelegateUpdatingTitle(boolean plural) {
		return null;
	}

}