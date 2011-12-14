package com.axiomalaska.hibernatetoolsdto.translator;

import java.math.BigDecimal;
import java.sql.Clob;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.hibernate.mapping.Property;
import org.hibernate.tool.hbm2x.Cfg2JavaTool;
import org.hibernate.tool.hbm2x.pojo.POJOClass;


public final class ActionscriptTranslator extends AbstractTranslator {
    public ActionscriptTranslator(POJOClass pojo, Cfg2JavaTool c2j ) {
        super( pojo, c2j );
    }

    private static final Map<String, FlexType> FLEX_TYPES = new HashMap<String, FlexType>();

    static {
        // String
        FLEX_TYPES.put( java.lang.String.class.getName(), new FlexType( "String" ) );
        FLEX_TYPES.put( java.lang.String.class.getSimpleName(), new FlexType( "String" ) );

        // double
        FLEX_TYPES.put( double.class.getName(), new FlexType( "Number" ) );
        FLEX_TYPES.put( double.class.getSimpleName(), new FlexType( "Number" ) );

        // Double
        FLEX_TYPES.put( Double.class.getName(), new FlexType( "Number" ) );
        FLEX_TYPES.put( Double.class.getSimpleName(), new FlexType( "Number" ) );

        // boolean
        FLEX_TYPES.put( boolean.class.getName(), new FlexType( "Boolean" ) );
        FLEX_TYPES.put( boolean.class.getSimpleName(), new FlexType( "Boolean" ) );

        // Boolean
        FLEX_TYPES.put( Boolean.class.getName(), new FlexType( "Boolean" ) );
        FLEX_TYPES.put( Boolean.class.getSimpleName(), new FlexType( "Boolean" ) );

        // int
        FLEX_TYPES.put( int.class.getName(), new FlexType( "int" ) );
        FLEX_TYPES.put( int.class.getSimpleName(), new FlexType( "int" ) );

        // Integer
        FLEX_TYPES.put( Integer.class.getName(), new FlexType( "int" ) );
        FLEX_TYPES.put( Integer.class.getSimpleName(), new FlexType( "int" ) );

        // long
        FLEX_TYPES.put( long.class.getName(), new FlexType( "Number" ) );
        FLEX_TYPES.put( long.class.getSimpleName(), new FlexType( "Number" ) );

        // byte[]
        FLEX_TYPES.put( byte[].class.getName(), new FlexType( "flash.utils.ByteArray", true ) );
        FLEX_TYPES.put( byte[].class.getSimpleName(), new FlexType( "flash.utils.ByteArray", true ) );

        // Date
        FLEX_TYPES.put( Date.class.getName(), new FlexType( "Date" ) );
        FLEX_TYPES.put( Date.class.getSimpleName(), new FlexType( "Date" ) );

        // BigDecimal
        FLEX_TYPES.put( BigDecimal.class.getName(), new FlexType( "Number" ) );
        FLEX_TYPES.put( BigDecimal.class.getSimpleName(), new FlexType( "Number" ) );
        FLEX_TYPES.put( BigDecimal.class.getCanonicalName(), new FlexType( "Number" ) );

        // char
        FLEX_TYPES.put( char.class.getName(), new FlexType( "String" ) );
        FLEX_TYPES.put( char.class.getSimpleName(), new FlexType( "String" ) );
        FLEX_TYPES.put( char.class.getCanonicalName(), new FlexType( "String" ) );

        // Character
        FLEX_TYPES.put( Character.class.getName(), new FlexType( "String" ) );
        FLEX_TYPES.put( Character.class.getSimpleName(), new FlexType( "String" ) );
        FLEX_TYPES.put( Character.class.getCanonicalName(), new FlexType( "String" ) );
        
        //Byte
        FLEX_TYPES.put( Byte.class.getName(), new FlexType( "int" ) );
        FLEX_TYPES.put( Byte.class.getSimpleName(), new FlexType( "int" ) );
        FLEX_TYPES.put( Byte.class.getCanonicalName(), new FlexType( "int" ) );        

        //Clob
        FLEX_TYPES.put( Clob.class.getName(), new FlexType( "String" ) );
        FLEX_TYPES.put( Clob.class.getSimpleName(), new FlexType( "String" ) );
        FLEX_TYPES.put( Clob.class.getCanonicalName(), new FlexType( "String" ) );                

        //UUID
        FLEX_TYPES.put( UUID.class.getName(), new FlexType( "String" ) );
        FLEX_TYPES.put( UUID.class.getSimpleName(), new FlexType( "String" ) );
        FLEX_TYPES.put( UUID.class.getCanonicalName(), new FlexType( "String" ) );                    
    }

    public String getActionscriptTypeName( Property p ){
        String javaTypeName = c2j.getJavaTypeName( p, false );
        FlexType flexType = FLEX_TYPES.get( javaTypeName );
        if( flexType != null ){
            return pojo.importType( flexType.getFlexTypeName() );
        } else {
            return pojo.importType( javaTypeName );
        }
    }
}
