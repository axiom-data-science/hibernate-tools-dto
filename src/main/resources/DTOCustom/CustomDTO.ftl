${packageDeclaration}

/**
 * ${pojo.getDeclarationName()} custom DTO class (extends generated class to hold custom code) 
 */
${pojo.getClassModifiers()} ${pojo.getDeclarationType()} ${pojo.getDeclarationName()}DTO extends ${pojo.getDeclarationName()}DTO<#if generatedClassSuffix??>${generatedClassSuffix}<#else>Generated</#if> {
  
}