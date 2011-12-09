package ${internalPackageName} {
// Generated ${date}
<#assign classbody>
	[Bindable]
	public class ${pojo.getDeclarationName()}Generated<#if extendz??> extends ${pojo.importType(extendz)}</#if> {
<#-- // Fields -->
<#foreach pojoProperty in pojo.getAllPropertiesIterator()>
<#assign useProperty = false />
<#if pojoProperty.getType().isAssociationType()>
	<#assign isAssociation = true />
	<#if c2h.isManyToOne( pojoProperty )>
		<#assign isManyToOne = true /> 
	<#else>
		<#assign isManyToOne = false />		
	</#if> 		
	<#if c2h.isOneToMany( pojoProperty )>
		<#assign isOneToMany = true /> 
	<#else>
		<#assign isOneToMany = false />		
	</#if> 				
	<#if pojoProperty.getType().isEntityType()>
		<#assign targetPojo = referencedPojos.get( pojoProperty.getValue().getReferencedEntityName() ) />
		<#assign targetProperty = targetPojo.getIdentifierProperty() />
	</#if>
<#else>
	<#assign isOneToMany = false />	
	<#assign isManyToOne = false />		
	<#assign isAssociation = false />
	<#assign targetPojo = pojo />
	<#assign targetProperty = pojoProperty />
</#if><#if !dtoTranslator.excludeFromDto( pojoProperty ) && !excludedTypesList.contains( targetProperty.getType().getName() )><#if !isOneToMany>
		public var ${targetProperty.name}<#if isAssociation && suffixDTOParentIdsBool>${targetPojo.getDeclarationName()}</#if>:${asTranslator.getActionscriptTypeName( targetProperty )}<#if targetPojo.hasFieldInitializor(targetProperty, jdk5)> = ${targetPojo.getFieldInitialization(targetProperty, jdk5)}</#if>;
</#if><#if isAssociation><#if isManyToOne>
		public var ${pojoProperty.name}:${asTranslator.getActionscriptTypeName( pojoProperty )}<#if pojo.hasFieldInitializor( pojoProperty, jdk5)> = ${pojo.getFieldInitialization( pojoProperty, jdk5)}</#if>;
</#if><#if isOneToMany>
		public var ${pojoProperty.name}:${pojo.importType("mx.collections.ArrayCollection")} = new ${pojo.importType("mx.collections.ArrayCollection")}();
</#if></#if></#if>
</#foreach>
	}
}
</#assign>
${pojo.generateImports()}
${classbody}