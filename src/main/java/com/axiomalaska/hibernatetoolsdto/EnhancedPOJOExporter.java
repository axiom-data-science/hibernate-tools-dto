package com.axiomalaska.hibernatetoolsdto;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.cfg.Configuration;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.mapping.Property;
import org.hibernate.tool.hbm2x.GenericExporter;
import org.hibernate.tool.hbm2x.MetaAttributeConstants;
import org.hibernate.tool.hbm2x.TemplateProducer;
import org.hibernate.tool.hbm2x.pojo.EntityPOJOClass;
import org.hibernate.tool.hbm2x.pojo.POJOClass;
import org.hibernate.type.ManyToOneType;
import org.hibernate.type.Type;

import com.axiomalaska.hibernatetoolsdto.translator.ActionscriptTranslator;
import com.axiomalaska.hibernatetoolsdto.translator.DTOTranslator;

/**
 * @author shane@axiomalaska.com
 */
public class EnhancedPOJOExporter extends GenericExporter {
	private static final String ENHANCEDPOJO_JAVACLASS_FTL = "pojoWithDTO/Pojo.ftl";
	
	@Override
    public String getName() {
		return "hbm2javaenhanced";
	}

    public EnhancedPOJOExporter(Configuration cfg, File outputdir) {
    	super(cfg, outputdir);
    	init();
    }

	protected void init() {
		setTemplateName(ENHANCEDPOJO_JAVACLASS_FTL);
    	setFilePattern("{package-name}/{class-name}.java");
	}

	public EnhancedPOJOExporter() {
		init();
	}

    @Override
    protected String getPackageNameForFile(POJOClass element) {
	    String specifiedPackage = (String) getProperties().get("packagename");
	    if( specifiedPackage != null ){
	        return specifiedPackage;
	    } else {
	        return element.getPackageName();
	    }
	}

    protected String getRemoteClassPackage(POJOClass element) {
        String remoteClassPackage = (String) getProperties().get("remoteClassPackage");
        if( remoteClassPackage != null ){
            return remoteClassPackage;
        } else {
            return getPackageNameForFile( element );
        }
    }

    protected List<String> getExcludedTypesList(){
    	String excludedTypesListStr = (String) getProperties().get("excludeTypes");
    	if( excludedTypesListStr != null ){
    		return Arrays.asList( excludedTypesListStr.split(",") );
    	} else {
    		return new ArrayList<String>();
    	}
    }

    protected boolean suffixDTOParentIds(){
        String suffixDTOParentIds = (String) getProperties().get("suffixDTOParentIds");
        if( suffixDTOParentIds != null && suffixDTOParentIds.equals("true") ){
            return true;
        } else {
            return false;
        }
    }

    /**
     * Reimplements getPackageDeclaration from BasicPOJOClass, since its method is protected
     * @param pkgName
     * @return
     */
    public String getPackageDeclaration( String pkgName ) {
        if (pkgName!=null && pkgName.trim().length()!=0 ) {
            return "package " + pkgName + ";";
        }
        else {
            return "// default package";
        }
    }

    public String getExtends( POJOClass element ){
        String extendz = "";
        PersistentClass clazz = (PersistentClass) element.getDecoratedObject();
        String classSuffix = (String) getProperties().get("classSuffix");

        if( element instanceof EntityPOJOClass ){
            EntityPOJOClass epc = (EntityPOJOClass) element;
            if ( epc.isInterface() ) {
                if ( clazz.getSuperclass() != null ) {
                    extendz = extendz.replaceAll( epc.getPackageName(), getPackageNameForFile( epc ) );
                    if( classSuffix != null ){
                        extendz += classSuffix;
                    }
                }
                if ( clazz.getMetaAttribute( MetaAttributeConstants.EXTENDS ) != null ) {
                    if ( !"".equals( extendz ) ) {
                        extendz += ",";
                    }
                    extendz += epc.getMetaAsString( MetaAttributeConstants.EXTENDS, "," );
                }
            }
            else if ( clazz.getSuperclass() != null ) {
                if ( getCfg2JavaTool().getPOJOClass(clazz.getSuperclass()).isInterface() ) {
                    // class cannot extend it's superclass because the superclass is marked as an interface
                }
                else {
                    extendz = clazz.getSuperclass().getClassName();
                    extendz = extendz.replaceAll( epc.getPackageName(), getPackageNameForFile( epc ) );
                    if( classSuffix != null ){
                        extendz += classSuffix;
                    }
                }
            }
            else if ( clazz.getMetaAttribute( MetaAttributeConstants.EXTENDS ) != null ) {
                extendz = epc.getMetaAsString( MetaAttributeConstants.EXTENDS, "," );
            }
        }

        if( extendz == null || extendz.trim().length() == 0  ){
            String universalAncestor = (String) getProperties().get("universalAncestor");
            if( universalAncestor != null ){
                extendz = universalAncestor;
            }
        }

        return "".equals( extendz ) ? null : extendz;
    }

	@Override
    protected void setupContext() {
		//TODO: this safe guard should be in the root templates instead for each variable they depend on.
		if(!getProperties().containsKey("ejb3")) {
			getProperties().put("ejb3", "false");
		}
		if(!getProperties().containsKey("jdk5")) {
			getProperties().put("jdk5", "false");
		}
		super.setupContext();
	}

	@Override
    protected void exportPOJO(Map additionalContext, POJOClass element) {
		TemplateProducer producer = new TemplateProducer(getTemplateHelper(),getArtifactCollector());
		Configuration cfg = this.getConfiguration();

		@SuppressWarnings("unchecked")
        Iterator<Property> it = element.getAllPropertiesIterator();

		Map<String, POJOClass> referencedPojos = new HashMap<String, POJOClass>();
		while(it.hasNext()) {
			Property prop = it.next();
			log.info(prop.getName());
			if( prop.getType() instanceof ManyToOneType ){
				ManyToOneType manyToOne = (ManyToOneType) prop.getType(); 
				PersistentClass referencedEntity = cfg.getClassMapping( manyToOne.getAssociatedEntityName() );
				POJOClass referencedPojo = this.getCfg2JavaTool().getPOJOClass( referencedEntity );
				referencedPojos.put( referencedEntity.getEntityName(), referencedPojo );
			}
		}

		//augment context
		additionalContext.put("pojo", element);
		additionalContext.put("clazz", element.getDecoratedObject());
		additionalContext.put("referencedPojos", referencedPojos);
		additionalContext.put("excludedTypesList", getExcludedTypesList() );
        additionalContext.put("internalPackageName", getPackageNameForFile( element ) );
		additionalContext.put("packageDeclaration", getPackageDeclaration( getPackageNameForFile( element ) ) );
		additionalContext.put("remoteClassPackageName", getRemoteClassPackage( element ) );
		additionalContext.put("suffixDTOParentIdsBool", suffixDTOParentIds() );
		String extendz = getExtends( element );
		if( extendz != null ){
		    additionalContext.put("extendz", getExtends( element ) );
		}
		additionalContext.put("asTranslator", new ActionscriptTranslator( element, this.getCfg2JavaTool() ) );
		additionalContext.put("dtoTranslator", new DTOTranslator( element, this.getCfg2JavaTool() ) );
		
		String filename = resolveFilename( element );
		producer.produce(additionalContext, getTemplateName(), new File(getOutputDirectory(),filename), getTemplateName(), element.toString());
	}

	public void execDoStart(){
	    doStart();
	}
}