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
package org.telosys.tools.dsl.parser.annotations;

import java.util.LinkedList;
import java.util.List;

import org.telosys.tools.dsl.model.DslModel;
import org.telosys.tools.dsl.model.DslModelEntity;
import org.telosys.tools.dsl.model.DslModelLink;
import org.telosys.tools.dsl.parser.annotation.AnnotationDefinition;
import org.telosys.tools.dsl.parser.annotation.AnnotationName;
import org.telosys.tools.dsl.parser.annotation.AnnotationParamType;
import org.telosys.tools.dsl.parser.annotation.AnnotationScope;
import org.telosys.tools.dsl.parser.commons.ParamError;
import org.telosys.tools.generic.model.Entity;
import org.telosys.tools.generic.model.ForeignKey;

public class LinkByJoinEntityAnnotation extends AnnotationDefinition { // extends LinkByAnnotation {

	public LinkByJoinEntityAnnotation() {
		super(AnnotationName.LINK_BY_JOIN_ENTITY, AnnotationParamType.STRING, AnnotationScope.LINK);
	}

	@Override
	public void apply(DslModel model, DslModelEntity entity, DslModelLink link, Object paramValue) throws ParamError {
		checkParamValue(entity, link, paramValue);
		String joinEntityName = (String) paramValue ;
		
		// Try to get the join table
//		JoinTable joinTable = getJoinTable(model, entity, link, joinEntityName);
		
		// Apply join table to link 
//		link.setJoinTable(joinTable);
		link.setJoinEntityName(joinEntityName); // v 3.4.0
		link.setBasedOnJoinEntity(true); // v 3.4.0
		
// Moved in  link converter (finalize)
//		// has Join Table => owning side
//		link.setOwningSide(true);
//		link.setInverseSide(false);
	}

/***********  
	protected JoinTable getJoinTable(DslModel model, DslModelEntity entity, DslModelLink link, String joinEntityName) throws ParamError {
		try {
			DslModelEntity joinEntity = getJoinEntity( model, joinEntityName);
//			List<ForeignKey> foreignKeys = joinEntity.getDatabaseForeignKeys();
			List<ForeignKey> foreignKeys = joinEntity.getForeignKeys();
			checkForeignKeys(foreignKeys, joinEntityName);
			return buildJoinTable(joinEntity, link, foreignKeys);
		} catch (Exception e) {
			throw newParamError(entity, link, e.getMessage());
		}
	}
	
	private DslModelEntity getJoinEntity(DslModel model, String joinEntityName) throws Exception {
		DslModelEntity joinEntity = (DslModelEntity) model.getEntityByClassName(joinEntityName);
		if ( joinEntity == null ) {
			throw new Exception("unknown join entity '"+ joinEntityName + "'");
		}
		return joinEntity;
	}	
	
	private void checkForeignKeys(List<ForeignKey> foreignKeys, String joinEntityName) throws Exception {
		if ( foreignKeys == null ) {
			throw new Exception("no foreign key in entity '"+ joinEntityName + "'");
		}
		if ( foreignKeys.size() != 2 ) {
			throw new Exception("2 foreign keys expected in join entity '"+ joinEntityName + "' (" + foreignKeys.size() + " found)");
		}
	}
	
	private JoinTable buildJoinTable(Entity joinEntity, DslModelLink link, List<ForeignKey> foreignKeys) throws Exception {
		ForeignKey fk1 = foreignKeys.get(0);
		ForeignKey fk2 = foreignKeys.get(1);
		ForeignKey joinColumnsFK = getFKReferencingTable(link.getSourceTableName(), fk1, fk2 );
		ForeignKey inverseJoinColumnsFK = getFKReferencingTable(link.getTargetTableName(), fk1, fk2 );
		if ( joinColumnsFK.getName().equals(inverseJoinColumnsFK.getName()) ) {
			throw new Exception("the 2 foreign keys are identical");
		}
//		JoinColumnsBuilder jcb = new JoinColumnsBuilder("@"+ AnnotationName.LINK_BY_JOIN_ENTITY ) ;

	// TODO : keep JOIN COLUMNS or not ????
//		JoinColumnsBuilder jcb = getJoinColumnsBuilder();
//
//		List<JoinColumn> joinColumns = jcb.buildJoinColumnsFromForeignKey(joinColumnsFK);
//
//		List<JoinColumn> inverseJoinColumns = jcb.buildJoinColumnsFromForeignKey(inverseJoinColumnsFK);
//		
//		return new DslModelJoinTable(joinEntity, joinColumns, inverseJoinColumns);
		return new DslModelJoinTable(joinEntity, new LinkedList<JoinColumn>(),  new LinkedList<JoinColumn>() );
	}
	
	private ForeignKey getFKReferencingTable(String tableName, ForeignKey fk1, ForeignKey fk2 ) throws Exception {
		if ( tableName.equals(fk1.getReferencedTableName()) ) {
			return fk1 ;
		}
		else if ( tableName.equals(fk2.getReferencedTableName()) ) {
			return fk2 ;
		}
		else {
			throw new Exception("join entity does not have FK referencing table '" + tableName + "'");
		}
	}
*******************/
}
