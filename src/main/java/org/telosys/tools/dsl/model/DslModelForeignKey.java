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
package org.telosys.tools.dsl.model;

import java.util.LinkedList;
import java.util.List;

import org.telosys.tools.commons.StrUtil;
import org.telosys.tools.generic.model.ForeignKey;
import org.telosys.tools.generic.model.ForeignKeyAttribute;
import org.telosys.tools.generic.model.LinkAttribute;

public class DslModelForeignKey implements ForeignKey {
	
	private static final String CONTRUCTOR_ERROR = "Foreign Key constructor error : ";
    private final String fkName; 
    private final String originEntityName; // entity holding this FK
    private final String referencedEntityName; // entity referenced by this FK
    
    private final List<ForeignKeyAttribute> attributes;
    
    public DslModelForeignKey(String fkName, String originEntityName, String referencedEntityName) {
		super();
		
		if ( StrUtil.nullOrVoid(fkName)) {
			throw new IllegalArgumentException(CONTRUCTOR_ERROR + "'name' is null or void");
		}
        this.fkName = fkName;
        
		if ( StrUtil.nullOrVoid(originEntityName)) {
			throw new IllegalArgumentException(CONTRUCTOR_ERROR + "'originEntityName' is null or void");
		}
        this.originEntityName = originEntityName;
        
		if ( StrUtil.nullOrVoid(referencedEntityName)) {
			throw new IllegalArgumentException(CONTRUCTOR_ERROR + "'referencedEntityName' is null or void");
		}
        this.referencedEntityName = referencedEntityName;
        
        this.attributes = new LinkedList<>();
	}

	@Override
    public String getName() {
        return fkName;
    }
	
	@Override
    public String getOriginEntityName() {
        return originEntityName;
    }
	
	@Override
    public String getReferencedEntityName() {
        return referencedEntityName;
    }

//    //@Override
//    public String getTableName() {
//        return entityName;
//    }
//
//    //@Override
//    public String getReferencedTableName() {
//        return referencedEntityName;
//    }

    @Override
    public List<ForeignKeyAttribute> getAttributes() {
        return attributes;
    }
	
    @Override
    public boolean isComposite() {
		return attributes.size() > 1 ;
	}
	
    public void addAttribute(DslModelForeignKeyAttribute fkAttribute) {
    	// fkAttribute has always valid attributes (not null & not void)
        this.attributes.add(fkAttribute);
    }

    /**
     * Returns all the join attributes defined in this Foreign Key
     * @return
     * @since  3.4.0
     */
//	public List<JoinAttribute> getJoinAttributes() {
	public List<LinkAttribute> getLinkAttributes() {
//		List<JoinAttribute> joinAttributes = new LinkedList<>();
		List<LinkAttribute> joinAttributes = new LinkedList<>();
		for (ForeignKeyAttribute fka : this.attributes ) {
	    	// each fk attribute has always valid attributes (not null & not void)
			DslModelLinkAttribute linkAttribute = new DslModelLinkAttribute(
					fka.getOriginAttributeName(),
					fka.getReferencedAttributeName());
			joinAttributes.add(linkAttribute);
		}
		return joinAttributes;
	}
}
