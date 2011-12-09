package com.axiomalaska.hibernatetoolsdto.translator;

import org.hibernate.tool.hbm2x.Cfg2JavaTool;
import org.hibernate.tool.hbm2x.pojo.POJOClass;

public class AbstractTranslator {

	protected POJOClass pojo;
    protected Cfg2JavaTool c2j;

    public AbstractTranslator( POJOClass pojo, Cfg2JavaTool c2j ) {
        super();
        this.pojo = pojo;
        this.c2j = c2j;
    } 
}