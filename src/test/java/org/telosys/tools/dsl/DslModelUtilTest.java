package org.telosys.tools.dsl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.io.File;
import java.util.List;

import org.junit.Test;
import org.telosys.tools.commons.env.TelosysToolsEnv;
import org.telosys.tools.junit.utils.TestFileProvider;

public class DslModelUtilTest {
	
    @Test
    public void testGetModelShortFileName() {
    	assertEquals("foo.model", DslModelUtil.getModelShortFileName("foo") );
    	assertEquals("foo.model", DslModelUtil.getModelShortFileName(" foo ") );
    }
    
    @Test
    public void testGetModelName() {
    	String modelName = DslModelUtil.getModelName(new File("C:/foo/bar/toto.model"));
    	assertEquals("toto", modelName);
    }
    
    @Test(expected=RuntimeException.class)
    public void testGetModelNameInvalid() {
    	DslModelUtil.getModelName(new File("C:/foo/bar/toto.foo"));
    }
    
    @Test
    public void testGetEntityName() {
    	String modelName = DslModelUtil.getEntityName(new File("C:/foo/bar/Toto.entity"));
    	assertEquals("Toto", modelName);
    }
    
    @Test(expected=RuntimeException.class)
    public void testGetEntityNameInvalid() {
    	DslModelUtil.getEntityName(new File("C:/foo/bar/Toto.txt"));
    }
    
    @Test
    public void testGetEntitiesAbsoluteFileNames() {
    	File modelFile = new File("src/test/resources/model_test/valid/FourEntities.model");
    	List<String> list = DslModelUtil.getEntitiesAbsoluteFileNames(modelFile);
    	for ( String s : list ) {
    		System.out.println(" . " + s );
    	}
    	assertEquals(4, list.size());
    }
    
    @Test
    public void testRenameEntity() {
    	String newName = "Country2" ;
    	File entityFile = TestFileProvider.copyAndGetTargetTmpFile("model_test/valid/TwoEntities_model/Country.entity");
    	assertTrue(entityFile.exists()) ;
    	assertTrue(entityFile.isFile()) ;

    	TestFileProvider.removeTargetTmpFileIfExists("model_test/valid/TwoEntities_model/Country2.entity");
    	File newFile = TestFileProvider.getTargetTmpFile("model_test/valid/TwoEntities_model/Country2.entity");
    	
    	System.out.println("Rename " + entityFile.getAbsolutePath() + " to " + newName );
    	DslModelUtil.renameEntity(entityFile, newName);
    	
    	assertTrue( newFile.exists() );
    }
    
    private File getFile(String fileFullPath) {
    	File file = new File(fileFullPath) ;
    	System.out.println("File getAbsolutePath() : " + file.getAbsolutePath() );
    	System.out.println("File getName() : " + file.getName() );
    	System.out.println("File getParent() : " + file.getParent() );
    	System.out.println("File getParent().getName() : " + file.getParentFile().getName() );
    	return file ;
    }
    @Test
    public void testIsValidModelFile() {
		TelosysToolsEnv telosysToolsEnv = TelosysToolsEnv.getInstance() ;
		System.out.println("TelosysToolsEnv.getModelsFolder() : " +  telosysToolsEnv.getModelsFolder() ) ;
		
		// check only file path (without folder existence)
    	assertTrue(DslModelUtil.isValidModelFile( getFile("/foo/bar/TelosysTools/aaaa.model"), false )) ;
    	assertFalse(DslModelUtil.isValidModelFile( getFile("/foo/bar/aaaa.model"), false )) ;
    	assertFalse(DslModelUtil.isValidModelFile( getFile("/foo/bar/TelosysTools/aaaa.txt"), false )) ;

		// check folder existence
    	assertFalse(DslModelUtil.isValidModelFile( getFile("/foo/bar/TelosysTools/aaaa.model"), true )) ; // No parent folder
    }
}
