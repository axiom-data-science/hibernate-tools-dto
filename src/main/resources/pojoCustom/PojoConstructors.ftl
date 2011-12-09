
    public ${pojo.getDeclarationName()}() {
    }
<#if pojo.needsMinimalConstructor()>

    public ${pojo.getDeclarationName()}(${c2j.asParameterList(pojo.getPropertyClosureForMinimalConstructor(), jdk5, pojo)}) {
        super(${c2j.asArgumentList(pojo.getPropertyClosureForMinimalConstructor())});        
    }
</#if>
<#if pojo.needsFullConstructor()>

    public ${pojo.getDeclarationName()}(${c2j.asParameterList(pojo.getPropertyClosureForFullConstructor(), jdk5, pojo)}) {
        super(${c2j.asArgumentList(pojo.getPropertyClosureForFullConstructor())});        
    }
</#if>    
