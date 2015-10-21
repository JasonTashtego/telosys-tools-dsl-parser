/**
 *  Copyright (C) 2008-2015  Telosys project org. ( http://www.telosys.org/ )
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

import org.slf4j.Logger;
import org.telosys.tools.dsl.EntityParserException;

/**
 * Generic ancestor class for parser classes
 * 
 * @author Laurent Guerin
 *
 */
public abstract class AbstractParser {

    private final Logger logger;
    

	public AbstractParser(Logger logger) {
		super();
		this.logger = logger;
	}

	protected void logInfo(String message) {
		logger.info(message);
	}
	
	protected void throwParsingError(String entityName, String message) {
        String errorMessage = entityName + " : " + message ;
        this.logger.error(errorMessage);
        throw new EntityParserException(errorMessage);
	}
}
