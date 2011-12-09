${packageDeclaration}

/**
 * ${pojo.getDeclarationName()} custom POJO class (extends generated class to hold custom code) 
 */
<#assign classbody>
${pojo.getClassModifiers()} ${pojo.getDeclarationType()} ${pojo.getDeclarationName()} extends ${pojo.getDeclarationName()}<#if generatedClassSuffix??>${generatedClassSuffix}<#else>Generated</#if> {
<#if !pojo.isInterface()>
<#-- Constructors make inheritance messy -->
<#-- <#include "PojoConstructors.ftl"/> --> 
</#if>
}
</#assign>

${pojo.generateImports()}
${classbody}