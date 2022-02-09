package kea.sem3.jwtdemo.entity;

public enum CarBrand{

    VOLVO("Volvo"),
    TOYOTA("Toyata"),
    WW("WW"),
    FORD("Ford"),
    SUZUKI("Suzuki"),
    BMW("BMW");

    public final String printName;

    private CarBrand(String printName){
        this.printName = printName;
    }

}