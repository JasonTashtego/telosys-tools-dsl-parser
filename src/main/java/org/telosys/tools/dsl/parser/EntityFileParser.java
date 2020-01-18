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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.telosys.tools.dsl.DslModelUtil;
import org.telosys.tools.dsl.DslParserException;
import org.telosys.tools.dsl.parser.model.DomainEntity;

/**
 * Entry point for Telosys DSL entity parser
 *
 * @author Laurent Guerin
 * 
 */
public class EntityFileParser { // extends AbstractParser {

	private static final char SPACE = 32;

	// private final String fileName ;
	private final File entityFile;
	private final String entityNameFromFileName;
	// private final DomainModel model ;

	private final List<Field> fieldsParsed = new LinkedList<>();
	private int   position ;
	private Field currentField = null ;
	
	private void log(String message) {
		System.out.println("LOG:" + message);
	}

	private void throwParsingException(String message) {
		String errorMessage = entityNameFromFileName + " : " + message;
		throw new DslParserException(errorMessage);
	}
	// private void throwParsingException(String message, Exception cause) {
	// String errorMessage = entityName + " : " + message ;
	// throw new DslParserException(errorMessage, cause);
	// }

	public EntityFileParser(String fileName) {

		// this.fileName = fileName ;
		// this.model = model ;
		this.entityFile = new File(fileName);
		this.entityNameFromFileName = DslModelUtil.getEntityName(entityFile);
	}

	/**
	 * Parse entity from the given file
	 * 
	 * @param file
	 * @return
	 */
	protected DomainEntity parse() {

//		if (!entityFile.exists()) {
//			throwParsingException("File not found");
//		}
//		log("parse() : File : " + entityFile.getAbsolutePath());
		DomainEntity domainEntity = new DomainEntity(entityNameFromFileName);

//		position = Const.IN_ENTITY;
//		int lineNumber = 0;
//		try (BufferedReader br = new BufferedReader(new FileReader(entityFile))) {
//			String line;
//			while ((line = br.readLine()) != null) {
//				lineNumber++;
//				processLine(line, lineNumber);
//				// read next line
//			}
//		} catch (IOException e) {
//			throwParsingException("IOException");
//		}
		parseFields() ;
		return domainEntity;
	}
	
	protected List<Field> parseFields() {
		if (!entityFile.exists()) {
			throwParsingException("File not found");
		}
		log("parse() : File : " + entityFile.getAbsolutePath());
		position = Const.IN_ENTITY;
		int lineNumber = 0;
		try (BufferedReader br = new BufferedReader(new FileReader(entityFile))) {
			String line;
			while ((line = br.readLine()) != null) {
				lineNumber++;
				processLine(line, lineNumber);
				// read next line
			}
		} catch (IOException e) {
			throwParsingException("IOException");
		}
		return this.fieldsParsed ;
	}
	
	protected void processLine(String line, int lineNumber) {
//		Field currentField = null ; // no current field at the beginning
		System.out.println("\n-------------------------------------------");
		System.out.println("#" + lineNumber + "("+position+") : " + line);
//		System.out.println("position = " + position);
		switch ( position ) {
		case Const.IN_ENTITY:
			String s = processLineEntityLevel(line, lineNumber);
			if ( s != null ) {
				//String entityNameInText = s ;
				System.out.println("\n=== ENTITY : " + s);
			}
			break;
		case Const.IN_FIELDS:
		case Const.IN_ANNOTATIONS:
//			currentField = processLineFieldLevel(line, lineNumber);
			processLineFieldLevel(line, lineNumber);
			System.out.println("processLineFieldLevel return : Field = " + currentField );
			break;
		default:
			break;
		}
	}
	/**
	 * Process the given line for a current level = ENTITY LEVEL
	 * @param line
	 * @param lineNumber
	 * @return the entity name if found or null if none
	 */
	protected String processLineEntityLevel(String line, int lineNumber) {
		StringBuilder sb = new StringBuilder();
		char previous = 0;
		for (char c : line.toCharArray()) {
			System.out.print( "[" + c+ "]");
			if (c > SPACE) {
				switch (c) {
				case '{':
					position++;
					break;
				case '}':
					position--;
					break;
				case '/':
					if (previous == '/') {
						// comment 
						return currentValue(sb);
					}
					break;
				default:
					//System.out.print("append("+c+")");
					sb.append(c);
				}
				previous = c;
			}
			if ( position != Const.IN_ENTITY ) {
				break;
			}
		}
		return currentValue(sb);
	}
	
//	private Field getCurrentField(Field currentField, Field originalField, int lineNumber) {
//		if (currentField != null) {
//			return currentField;
//		}
//		else if ( originalField != null ) {
//			return originalField;
//		}
//		else {
//			return new Field(lineNumber);
//		}
//	}
	private Field getCurrentField(Field originalField, int lineNumber) {
		if ( originalField != null ) {
			return originalField;
		}
		else {
			return new Field(lineNumber);
		}
	}
	
	private void resetCurrentField(int lineNumber) {
		if ( currentField == null ) {
			currentField = new Field(lineNumber);
		}
		else {
			if ( currentField.isVoid() ) {
				currentField = new Field(lineNumber);
			}
		}
	}
	private void endOfCurrentField() {
		currentField.finished();
		System.out.println("\n\n=== FINISHED : " + currentField);
		currentField = null ; // no current field
		position = Const.IN_FIELDS;
	}

	/**
	 * Process the given line for a current level = FIELD LEVEL
	 * @param line
	 * @param lineNumber
	 * @return
	 */
//	protected Field processLineFieldLevel(String line, int lineNumber) {
	protected void processLineFieldLevel(String line, int lineNumber) {
		
		char previous = 0;
//		Field currentField = null ;
//		Field currentField = getCurrentField(originalField, lineNumber);
		resetCurrentField(lineNumber);
		System.out.println("processLineFieldLevel : #" + lineNumber + " : '" + line + "'");
		System.out.println("processLineFieldLevel : #" + lineNumber + " : Field : " + currentField );

		
		// parse all chararcters in the given line
		for (char c : line.toCharArray()) {
			System.out.print( "[" + c+ "]");
			if (c > SPACE) { // if not a void char
				
				switch (c) {
				case ';':   // end of field 
					currentField.finished();
					System.out.println("\n\n=== FINISHED : " + currentField);
					fieldsParsed.add(currentField);
					currentField = null ; // no current field
					position = Const.IN_FIELDS;
					break;
				case '{':
					position++;
					currentField.setPosition(position);
//					// end of field definition part 
//					field.setFieldPart(fieldPart.toString());
					break;
				case '}':
					position--;
					currentField.setPosition(position);
					break;
				case '/':
					if (previous == '/') {
						// comment => end of current line
						return ;
					}
					break;
				default:
//					System.out.print("append("+c+")");
					//fieldPart.append(c);
					System.out.print( "+" );
					currentField.append(c);
				}
				previous = c;
			}
//			if ( position != IN_ENTITY ) {
//				break;
//			}
		}
//		return currentField;
	}

	private String currentValue(StringBuilder sb) {
		String s = sb.toString();
		return ( s.length() > 0 ? s : null );
	}
}
