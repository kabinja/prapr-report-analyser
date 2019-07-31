package lu.uni.prapr.report;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class Mutation {
    public enum Status{
        KILLED, SURVIVED, GENUINE, UNKNOWN
    }

    @JacksonXmlProperty(localName = "detected", isAttribute = true)
    private boolean detected;

    @JacksonXmlProperty(localName = "status", isAttribute = true)
    private Status status;

    @JacksonXmlProperty(localName = "numberOfTestsRun", isAttribute = true)
    private int numberOfTestsRun;

    @JacksonXmlProperty(localName = "sourceFile")
    private String sourceFile;

    @JacksonXmlProperty(localName = "mutatedClass")
    private String mutatedClass;

    @JacksonXmlProperty(localName = "mutatedMethod")
    private String mutatedMethod;

    @JacksonXmlProperty(localName = "methodDescription")
    private String methodDescription;

    @JacksonXmlProperty(localName = "lineNumber")
    private int lineNumber;

    @JacksonXmlProperty(localName = "mutator")
    private String mutator;

    @JacksonXmlProperty(localName = "index")
    private int index;

    @JacksonXmlProperty(localName = "block")
    private int block;

    @JacksonXmlProperty(localName = "coveringTests")
    private String coveringTests;

    @JacksonXmlProperty(localName = "killingTests")
    private String killingTests;

    @JacksonXmlProperty(localName = "suspValue")
    private float suspValue;

    @JacksonXmlProperty(localName = "description")
    private String description;

    public boolean isDetected() {
        return detected;
    }

    public void setDetected(boolean detected) {
        this.detected = detected;
    }

    public String getStatus() {
        return status.name();
    }

    public void setStatus(String status) {
        if(status.equalsIgnoreCase("KILLED")){
            this.status = Status.KILLED;
        }
        else if(status.equalsIgnoreCase("SURVIVED")){
            this.status = Status.SURVIVED;
        }
        else{
            this.status = Status.UNKNOWN;
        }

    }

    public int getNumberOfTestsRun() {
        return numberOfTestsRun;
    }

    public void setNumberOfTestsRun(int numberOfTestsRun) {
        this.numberOfTestsRun = numberOfTestsRun;
    }

    public String getSourceFile() {
        return sourceFile;
    }

    public void setSourceFile(String sourceFile) {
        this.sourceFile = sourceFile;
    }

    public String getMutatedClass() {
        return mutatedClass;
    }

    public void setMutatedClass(String mutatedClass) {
        this.mutatedClass = mutatedClass;
    }

    public String getMutatedMethod() {
        return mutatedMethod;
    }

    public void setMutatedMethod(String mutatedMethod) {
        this.mutatedMethod = mutatedMethod;
    }

    public String getMethodDescription() {
        return methodDescription;
    }

    public void setMethodDescription(String methodDescription) {
        this.methodDescription = methodDescription;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public String getMutator() {
        return mutator;
    }

    public void setMutator(String mutator) {
        this.mutator = mutator;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getBlock() {
        return block;
    }

    public void setBlock(int block) {
        this.block = block;
    }

    public String[] getCoveringTestsAsArray(){
        if(coveringTests == null){
            return new String[0];
        }

        return coveringTests.split(",");
    }

    public String getCoveringTests() {
        return coveringTests;
    }

    public void setCoveringTests(String coveringTests) {
        this.coveringTests = coveringTests;
    }

    public int getNumberCoveringTest() {
        return getCoveringTestsAsArray().length;
    }

    public String[] getKillingTestsAsArray(){
        if(killingTests == null){
            return new String[0];
        }

        return killingTests.split(",");
    }

    public String getKillingTests() {
        return killingTests;
    }

    public void setKillingTests(String killingTests) {
        this.killingTests = killingTests;
    }

    public int getNumberKillingTests() {
        return getKillingTestsAsArray().length;
    }

    public float getSuspValue() {
        return suspValue;
    }

    public void setSuspValue(float suspValue) {
        this.suspValue = suspValue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isStatus(Status mutationStatus) {
        return this.status == mutationStatus
                || (this.status == Status.GENUINE && mutationStatus == Status.SURVIVED);
    }
}
