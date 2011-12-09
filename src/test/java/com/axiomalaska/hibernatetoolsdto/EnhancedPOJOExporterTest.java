package com.axiomalaska.hibernatetoolsdto;

import java.io.File;

import junit.framework.TestCase;

import org.hibernate.cfg.Configuration;

import com.axiomalaska.hibernatetoolsdto.EnhancedPOJOExporter;

public class EnhancedPOJOExporterTest extends TestCase {
	private Configuration cfg;
	private EnhancedPOJOExporter epe;

	protected void setUp(){
		cfg = new Configuration();
		cfg.addFile( new File("src/test/resources/LineItem.hbm.xml") );
		cfg.addFile( new File("src/test/resources/Order.hbm.xml") );
		cfg.addFile( new File("src/test/resources/Customer.hbm.xml") );
		cfg.addFile( new File("src/test/resources/Product.hbm.xml") );
        cfg.buildMappings();
		epe = new EnhancedPOJOExporter( cfg, new File("target/hibernate3/test") );
	}

    public void testPojoWithDto(){
        epe.setTemplateName("pojoWithDTO/Pojo.ftl");
        epe.setTemplatePath( new String[]{"src/main/resources"} );
        epe.getProperties().setProperty("dtoPackageName", "com.fake.dto.packagename");
        epe.getProperties().setProperty("suffixDTOParentIds", "true");
        epe.start();
    }

    public void testDto(){
        epe.setTemplateName("DTO/DTO.ftl");
        epe.setFilePattern("{package-name}/{class-name}DTO.java");
        epe.getProperties().setProperty("suffixDTOParentIds", "false");
        epe.start();
    }

    public void testActionscriptDto(){
        epe.setTemplateName("actionscriptDTO/actionscriptDTO.ftl");
        epe.setFilePattern("{package-name}/{class-name}.as");
        epe.start();
    }
}