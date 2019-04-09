public class Valute {

    private CharSequence charCode;
    private int nominal;
    private String name;
    private Float value;
    private String numCode;

    public Valute (String numCode, CharSequence charCode, int nominal, String name, Float value) {
        this.numCode = numCode;
        this.charCode = charCode;
        this.nominal = nominal;
        this.name = name;
        this.value = value;
    }

    String getNumCode() {
        return numCode;
    }

    int getNominal() {
        return nominal;
    }

    String getName() {
        return name;
    }

    Float getValue() {
        return value;
    }

    //Считаем стоимость за единицу
    float costPerOne () {
        return getValue()/getNominal();
    }
}
