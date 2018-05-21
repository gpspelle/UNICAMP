public class Circulo extends Forma { 

    private float raio;

    public Circulo(float raio) {
        super();
        this.raio = raio;
    }
    public float calculaArea() {
       return (float)3.1415*raio*raio; 
    }
    public float calcularPerimetro() { 
       return (float)3.1415*2*raio; 
    }
}
