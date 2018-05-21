public class EnumTeste {

    Dia day;
    public EnumTeste(Dia day) {
        this.day = day;
    }
    
    public void tellDay() {
        switch(day) {
            case SEGUNDA: System.out.println("Sou segunda"); break;
        }
    }
    public static void main(String[] args) {

        EnumTeste teste = new EnumTeste(Dia.SEGUNDA);
        teste.tellDay();
    }
}
