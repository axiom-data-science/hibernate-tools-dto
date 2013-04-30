	/**
	 * Creates a new ${pojo.getDeclarationName()} DTO and sets its values. This is useful for sending
	 * data objects to the client tier (i.e. avoiding the Hibernate lazy loading problem in the client tier).
	 * Parent/child relationships in the POJO are represented in the DTO with foreign keys.
	 * Example: POJO has getParent() that returns a Parent, DTO has getParentID() returning a key.  
	 * 
	 * @return ${pojo.getDeclarationName()} DTO with values set
	 */

    public ${pojo.importType( dtoPackageName + "." + pojo.getDeclarationName() + "DTO" )} createDTO(){
        return createDTO( new ${pojo.importType( dtoPackageName + "." + pojo.getDeclarationName() + "DTO" )}() );
    }

	@Override
    protected ${pojo.importType( dtoPackageName + "." + pojo.getDeclarationName() + "DTO" )} createDTO( ${pojo.importType( dtoPackageName + ".AbstractDTO" )} abstractDto ){
	    super.createDTO( abstractDto );
	    ${pojo.importType( dtoPackageName + "." + pojo.getDeclarationName() + "DTO" )} dto = (${pojo.importType( dtoPackageName + "." + pojo.getDeclarationName() + "DTO" )}) abstractDto;
	<#foreach pojoProperty in pojo.getAllPropertiesIterator()>
	<#assign useProperty = false />
	<#if pojoProperty.getType().isAssociationType()>
		<#if pojoProperty.getType().isEntityType()>
			<#assign targetPojo = referencedPojos.get( pojoProperty.getValue().getReferencedEntityName() ) />
			<#assign targetProperty = targetPojo.getIdentifierProperty() />
		if( ${pojo.getGetterSignature(pojoProperty)}() != null ){
			dto.set${targetPojo.getPropertyName(targetProperty)}<#if suffixDTOParentIdsBool>${targetPojo.getDeclarationName()}</#if>( ${pojo.getGetterSignature(pojoProperty)}().${dtoTranslator.getPojoToDto( targetProperty, jdk5 )} );
		}
		</#if>
	<#else>
		<#if dtoTranslator.excludeFromDto( pojoProperty )>
		// excluded ${pojoProperty.name}
		<#else>
		dto.set${pojo.getPropertyName(pojoProperty)}( ${dtoTranslator.getPojoToDto( pojoProperty, jdk5 )} );
		</#if>
	</#if>
	</#foreach>
		return dto;		
	}
	
	/**
	 * Ingests a DTO (sets POJO values accoring to DTO values). DTO properties containing foreign keys are 
	 * translated to POJO parent/child relationships. 
	 *
	 * @param session The hibernate session
	 * @param dto The ${pojo.getDeclarationName()}DTO to be ingested 
	 */
	@Override
	public void ingestDTO( ${pojo.importType("org.hibernate.Session")} session, AbstractDTO abstractDto ){
		super.ingestDTO( session, abstractDto );
		${ pojo.importType( dtoPackageName + "." + pojo.getDeclarationName() + "DTO" )} dto = (${ pojo.importType( dtoPackageName + "." + pojo.getDeclarationName() + "DTO" )}) abstractDto;		
	<#foreach pojoProperty in pojo.getAllPropertiesIterator()>
	<#assign useProperty = false />
	<#if pojoProperty.getType().isAssociationType()>
		<#if pojoProperty.getType().isEntityType()>
			<#assign targetPojo = referencedPojos.get( pojoProperty.getValue().getReferencedEntityName() ) />
			<#assign targetProperty = targetPojo.getIdentifierProperty() />
			<#if targetPojo.getJavaTypeName( targetProperty, jdk5).equals("int") || targetPojo.getJavaTypeName( targetProperty, jdk5).equals("long")>
				<#assign nullKey = "0" />
			<#else>
				<#assign nullKey = "null" />
			</#if>
			
		if( dto.${targetPojo.getGetterSignature(targetProperty)}<#if suffixDTOParentIdsBool>${targetPojo.getDeclarationName()}</#if>() != ${nullKey} ){ 
			set${pojo.getPropertyName(pojoProperty)}(
				(${pojo.getPropertyName(pojoProperty)}) session
				.createCriteria(${pojo.getPropertyName(pojoProperty)}.class)
				.add( ${pojo.importType("org.hibernate.criterion.Restrictions")}.idEq( <#if suffixDTOParentIdsBool>${dtoTranslator.getDtoToPojo( "dto.", targetPojo.getDeclarationName(), targetProperty, jdk5 )}<#else>${dtoTranslator.getDtoToPojo( "dto.", pojoProperty, jdk5 )}</#if> ) )
				.uniqueResult()			
			);
		} else {
			set${pojo.getPropertyName(pojoProperty)}( null );		
		}
		</#if>
	<#else>
		<#if dtoTranslator.excludeFromDto( pojoProperty )>
		// excluded ${pojoProperty.name}
		<#else>
		set${pojo.getPropertyName(pojoProperty)}( ${dtoTranslator.getDtoToPojo( "dto.", pojoProperty, jdk5 )} );
		</#if>
	</#if>
	</#foreach>
	}	