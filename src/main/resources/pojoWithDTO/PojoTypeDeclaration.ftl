/**
${pojo.getClassJavaDoc(pojo.getDeclarationName() + " with DTO methods (generated)", 0)}
 */
<#include "Ejb3TypeDeclaration.ftl"/>
${pojo.getClassModifiers()} ${pojo.getDeclarationType()} ${pojo.getDeclarationName()}Generated<#if extendz??> extends ${pojo.importType(extendz)}</#if> ${pojo.getImplementsDeclaration()}