package org.telosys.tools.dsl.parser.annotations;

import org.junit.Test;
import org.telosys.tools.dsl.parser.annotation.AnnotationDefinition;
import org.telosys.tools.dsl.parser.annotation.AnnotationParamType;
import org.telosys.tools.dsl.parser.exceptions.AnnotationParsingError;
import org.telosys.tools.dsl.parser.exceptions.ParsingError;
import org.telosys.tools.dsl.parser.model.DomainAnnotation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class OneToOneAnnotationTest {

	private static final String ANNOTATION_NAME = "OneToOne";
	
	private AnnotationDefinition getAnnotationDefinition() {
		return new OneToOneAnnotation() ;
	}
	
	@Test
	public void test1() {
		AnnotationDefinition a = getAnnotationDefinition();
		assertEquals( ANNOTATION_NAME, a.getName() );
		assertEquals( AnnotationParamType.NONE, a.getParamType() );
		// 2 scopes : attribute + link 
		assertFalse( a.hasAttributeScope() );
		assertTrue( a.hasLinkScope() );
		assertFalse( a.hasEntityScope() );
	}

	@Test 
	public void test2() throws ParsingError {
		AnnotationDefinition a = getAnnotationDefinition();
		DomainAnnotation da = a.buildAnnotation("Student", "computer", null);
		assertEquals( ANNOTATION_NAME, da.getName() );
		assertNull(da.getParameter());
	}

	@Test (expected=AnnotationParsingError.class)
	public void test3() throws ParsingError {
		AnnotationDefinition a = getAnnotationDefinition();
		a.buildAnnotation("Student", "computer", "paramValue");
		// Error : unexpected parameter
	}
}
