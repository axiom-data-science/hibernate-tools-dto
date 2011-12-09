${packageDeclaration}
// Generated ${date} by Hibernate Tools ${version}

<#assign classbody>
/**
${pojo.getClassJavaDoc(pojo.getDeclarationName() + "DTO (generated)", 0)}
 * Simple DTO class for data transactions with the client tier
 */

${pojo.getClassModifiers()} ${pojo.getDeclarationType()} ${pojo.getDeclarationName()}DTO<#if generatedClassSuffix??>${generatedClassSuffix}<#else>Generated</#if><#if extendz??> extends ${pojo.importType(extendz)}</#if> ${pojo.getImplementsDeclaration()} {

<#-- // Fields -->
<#foreach pojoProperty in pojo.getAllPropertiesIterator()>
<#assign useProperty = false />
<#if pojoProperty.getType().isAssociationType()>
	<#if pojoProperty.getType().isEntityType()>
		<#assign targetPojo = referencedPojos.get( pojoProperty.getValue().getReferencedEntityName() ) />
		<#assign targetProperty = targetPojo.getIdentifierProperty() />
		<#if clazz.getSuperclass()?? && clazz.getSuperclass() == targetPojo.getDecoratedObject()>
			<#assign useProperty = false />
		<#else>
			<#assign useProperty = true />
		</#if>
		<#assign isEntityAssociation = true />
	<#elseif pojoProperty.getType().isCollectionType()>
		<#assign targetPojo = pojo />
		<#assign targetProperty = pojoProperty />
		<#assign useProperty = true />
		<#assign isEntityAssociation = false />
	</#if>
<#else>
	<#assign targetPojo = pojo />
	<#assign targetProperty = pojoProperty />
	<#assign useProperty = true />
	<#assign isEntityAssociation = false />
</#if>
<#if useProperty>
<#if dtoTranslator.excludeFromDto( pojoProperty )>
	// excluded ${targetProperty.name}
<#else>
<#if targetPojo.getMetaAttribAsBool(targetProperty, "gen-property", true)> <#if targetPojo.hasMetaAttribute(targetProperty, "field-description")>    /**
     ${targetPojo.getFieldJavaDoc(targetProperty, 0)}
     */
</#if>   ${targetPojo.getFieldModifiers(targetProperty)} ${dtoTranslator.getJavaTypeName( targetProperty, jdk5, "DTO")} ${targetProperty.name}<#if isEntityAssociation && suffixDTOParentIdsBool>${targetPojo.getDeclarationName()}</#if><#if targetPojo.hasFieldInitializor(targetProperty, jdk5)> = ${dtoTranslator.getFieldInitialization( targetProperty, jdk5, "DTO")}</#if>;
</#if>
</#if>
</#if>
</#foreach>

<#-- // Property accessors -->
<#foreach pojoProperty in pojo.getAllPropertiesIterator()>
<#assign useProperty = false />
<#if pojoProperty.getType().isAssociationType()>
	<#if pojoProperty.getType().isEntityType()>	
		<#assign targetPojo = referencedPojos.get( pojoProperty.getValue().getReferencedEntityName() ) />
		<#assign targetProperty = targetPojo.getIdentifierProperty() />
		<#if clazz.getSuperclass()?? && clazz.getSuperclass() == targetPojo.getDecoratedObject()>
			<#assign useProperty = false />
		<#else>
			<#assign useProperty = true />
		</#if>
		<#assign isEntityAssociation = true />
	<#elseif pojoProperty.getType().isCollectionType()>
		<#assign targetPojo = pojo />
		<#assign targetProperty = pojoProperty />
		<#assign useProperty = true />
		<#assign isEntityAssociation = false />
	</#if>
<#else>
	<#assign targetPojo = pojo />
	<#assign targetProperty = pojoProperty />
	<#if !dtoTranslator.excludeFromDto( pojoProperty )>
		<#assign useProperty = true />
	</#if>
	<#assign isEntityAssociation = false />
</#if>

<#if useProperty>
<#if targetPojo.getMetaAttribAsBool(targetProperty, "gen-property", true)>
 <#if targetPojo.hasFieldJavaDoc(targetProperty)>    
    /**       
     * ${targetPojo.getFieldJavaDoc(targetProperty, 4)}
     */
</#if>
    ${targetPojo.getPropertyGetModifiers(targetProperty)} ${dtoTranslator.getJavaTypeName( targetProperty, jdk5, "DTO")} ${targetPojo.getGetterSignature(targetProperty)}<#if isEntityAssociation && suffixDTOParentIdsBool>${targetPojo.getDeclarationName()}</#if>() {
        return this.${targetProperty.name}<#if isEntityAssociation && suffixDTOParentIdsBool>${targetPojo.getDeclarationName()}</#if>;
    }
    
    ${targetPojo.getPropertySetModifiers(targetProperty)} void set${targetPojo.getPropertyName(targetProperty)}<#if isEntityAssociation && suffixDTOParentIdsBool>${targetPojo.getDeclarationName()}</#if>(${dtoTranslator.getJavaTypeName( targetProperty, jdk5, "DTO")} ${targetProperty.name}<#if isEntityAssociation && suffixDTOParentIdsBool>${targetPojo.getDeclarationName()}</#if>) {
        this.${targetProperty.name}<#if isEntityAssociation && suffixDTOParentIdsBool>${targetPojo.getDeclarationName()}</#if> = ${targetProperty.name}<#if isEntityAssociation && suffixDTOParentIdsBool>${targetPojo.getDeclarationName()}</#if>;
    }
</#if>
</#if>
</#foreach>
}
</#assign>

${pojo.generateImports()}
${classbody}