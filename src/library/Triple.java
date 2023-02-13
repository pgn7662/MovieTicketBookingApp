package library;

public class Triple <A,B,C>{
    private  A firstElement;
    private  B secondElement;
    private  C thirdElement;

    public Triple(A firstElement, B secondElement, C thirdElement) {
        this.firstElement = firstElement;
        this.secondElement = secondElement;
        this.thirdElement = thirdElement;
    }

    public A getFirstElement() {
        return firstElement;
    }

    public B getSecondElement() {
        return secondElement;
    }

    public C getThirdElement() {
        return thirdElement;
    }

    public void setFirstElement(A firstElement) {
        this.firstElement = firstElement;
    }

    public void setSecondElement(B secondElement) {
        this.secondElement = secondElement;
    }

    public void setThirdElement(C thirdElement) {
        this.thirdElement = thirdElement;
    }
}
