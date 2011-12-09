package com.axiomalaska.hibernatetoolsdto.translator;

public class FlexType {
    private String flexTypeName;
    private boolean requiresImport;

    public FlexType(String flexTypeName ) {
        super();
        this.flexTypeName = flexTypeName;
        this.requiresImport = false;
    }

    public FlexType(String flexTypeName, boolean requiresImport) {
        super();
        this.flexTypeName = flexTypeName;
        this.requiresImport = requiresImport;
    }

    public String getFlexTypeName() {
        return flexTypeName;
    }
    public void setFlexTypeName(String flexTypeName) {
        this.flexTypeName = flexTypeName;
    }
    public boolean requiresImport() {
        return requiresImport;
    }
    public void setRequiresImport(boolean requiresImport) {
        this.requiresImport = requiresImport;
    }
}
