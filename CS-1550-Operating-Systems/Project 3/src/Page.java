class Page {
    private int referenceBit;
    private String pageAddress;
    private boolean dirtyBit;

     protected Page() {
        this.referenceBit = 0;
        this.pageAddress = "";
        this.dirtyBit = false;
    }

    protected int getReferenceBit() {
        return referenceBit;
    }

    protected boolean getDirtyBit() { return dirtyBit; }

    protected String getPageAddress() {
        return pageAddress;
    }

    protected void setPageAddress(String pageAddress) {
        this.pageAddress = pageAddress;
    }

    protected void setReferenceBit(int referenceBit) {
        this.referenceBit = referenceBit;
    }

    protected void setDirtyBit(boolean dirtyBit) { this.dirtyBit = dirtyBit; }
}
