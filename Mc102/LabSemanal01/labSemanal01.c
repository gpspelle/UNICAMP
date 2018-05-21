/* Nome: Gabriel Pellegrino da Silva   RA: 172358
 *
 *  O programa a seguir tem a intenção de, a partir de uma data conhecida, a quantidade de dias em um mês (N),
 *  a quantidade de meses em um ano (M) e quantos dias ainda restam para o evento ocorrer, calcular a data da
 *  ocorrência do evento determinado. Ademais, houve de se fazer a conversão do número de dias restantes para 
 *  o evento da base 6 para a base 10. 
 */

#include <time.h>
#include <stdio.h>
#include <stdlib.h>
#include <math.h>
   	
int main(int argc, char* argv[]) { 
  long int cpu_inic, cpu_fim;
  double tiq_sec;
  tiq_sec = (double)CLOCKS_PER_SEC;
  cpu_inic=clock();
  /* Declaração das variáveis que serão utilizadas */
  int dia_entr, mes_entr, ano_entr, N, M, base6;        
  int A, B, C, D, E, F, G, H;
  int base10; 
  int dias_adic, meses_adic, anos_adic;  
  int dia_sai, mes_sai, ano_sai; 
  /* Leitura da entrada */
  scanf("%d/%d/%d %d %d %d", &dia_entr, &mes_entr, &ano_entr, &N, &M, &base6);
  /* Conversão da base 6 para a base 10 dos dias restantes para o evento */
  A = base6/10000000;         
  B = base6/1000000;          B = B%10; 
  C = base6/100000;           C = C%10; 
  D = base6/10000;            D = D%10;
  E = base6/1000;             E = E%10;
  F = base6/100;              F = F%10;
  G = base6/10;               G = G%10;
  H = base6%10; 
  base10 = (A*pow(6,7))+(B*pow(6,6))+(C*pow(6,5))+(D*pow(6,4))+(E*pow(6,3))+(F*6*6)+(G*6)+H;        
  /* Procedimento para calcular o dia, o mês e o ano a serem imprimidos */
  dia_entr = dia_entr*0.9999;
  mes_entr = mes_entr*0.9999;
  dias_adic = base10%N; 
  dia_entr = dias_adic+dia_entr;
  dia_sai = dia_entr%N+1;
  meses_adic = (dia_entr/N)+(base10/N);
  mes_entr = meses_adic+mes_entr;
  mes_sai = mes_entr%M+1;
  anos_adic = mes_entr/M; 
  ano_sai = ano_entr+anos_adic;
  /* Impressão da saída */
  printf("%d/%d/%d", dia_sai, mes_sai, ano_sai);
  cpu_fim=clock();
  printf("Tempo: %f\n", (cpu_fim-cpu_inic)/tiq_sec);
  /* system ("Pause") */
  return 0;
}
