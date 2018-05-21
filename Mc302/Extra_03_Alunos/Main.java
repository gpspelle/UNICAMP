public class Main { 

    public static void main(String[] args) { 
    
    Aluno a1 = new Aluno("Luma");
    Aluno a2 = new Aluno("Gabriel");
    
    a1.setMatricula(1);
    a2.setMatricula(0);
    a1.setIdade(18);
    a2.setIdade(19);
    a1.setAltura(1.70);
    a2.setAltura(1.90); 
    
    System.out.println(a1.toString());
    System.out.println(a2.toString());
    }
}
