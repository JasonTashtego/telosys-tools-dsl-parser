/**
 *  Copyright (C) 2008-2017  Telosys project org. ( http://www.telosys.org/ )
 *
 *  Licensed under the GNU LESSER GENERAL PUBLIC LICENSE, Version 3.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *          http://www.gnu.org/licenses/lgpl.html
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.telosys.tools.dsl.parser;

import org.telosys.tools.dsl.parser.annotation.AnnotationDefinition;
import org.telosys.tools.dsl.parser.annotation.Annotations;
import org.telosys.tools.dsl.parser.exceptions.ParsingError;
import org.telosys.tools.dsl.parser.model.DomainAnnotation;

/**
 * Annotation parsing 
 * 
 * @author Laurent GUERIN
 */
public class AnnotationParser extends AnnotationAndTagParser {

	/**
	 * Constructor for parsing at FIELD level
	 * 
	 * @param entityName
	 * @param fieldName
	 */
	public AnnotationParser(String entityName, String fieldName) {
		super(entityName, fieldName);
	}
	
	/**
	 * Constructor for parsing at ENTITY level
	 * 
	 * @param entityName
	 */
	public AnnotationParser(String entityName) {
		super(entityName);
	}
	
	/**
	 * Parse the given annotation string ( a string starting by '@' ) 
	 * @param annotation the full annotation string eg "@Id", "@Min(12)"
	 * @return
	 * @throws ParsingError
	 */
	public DomainAnnotation parseAnnotation(String annotation) throws ParsingError {
		
		// get the name 
		String annotationName = getName(annotation);

		// get the raw parameter value if any
		String annotationParameter = getParameterValue(annotation);

		// use annotation definition to build a new annotation instance
		AnnotationDefinition ad = Annotations.get(annotationName);
		if ( ad != null ) {
			return ad.buildAnnotation(getEntityName(), getFieldName(), annotationParameter);
		}
		else {
			throw newError("'" + annotation + "' : unknown annotation");
		}
	}
}