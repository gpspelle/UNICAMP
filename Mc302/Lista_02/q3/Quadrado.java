public class Quadrado extends Forma { 

    private float lado;

    public Quadrado(int lado) {
        super();
        this.lado = lado;
    }
    public float calculaArea() {
       return lado*lado; 
    }
    public float calcularPerimetro() { 
       return 4*lado; 
    }
}
