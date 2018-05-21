/**
 * Created by gabriel on 13/05/17.
 */

import java.util.Scanner;
public class Main {

    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);
        /*n corresponde ao numero de pessoas que jogarao*/
        int n = scan.nextInt();
        while(n < 2 || n > 6) {
            System.out.println("Numero invalido! Insira um numero de 0 a 6");
            n = scan.nextInt();
        }
        Tabuleiro tabuleiro = new Tabuleiro(n);

    }
}
