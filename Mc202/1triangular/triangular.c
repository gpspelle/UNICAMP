/* Gabriel Pellegrino da Silva Ra - 172358 

	O programa a seguir armazena dinamicamente os valores de uma 
matriz triangular inferior. Calcula a sua normalizacao e em seguida 
imprime o resultado, bem como o valor da media e o desvio padrao. 
Sendo a[ij] a matriz lida, e b[ij] a matriz impressa. 

(2) b[ij]= (a[ij]- media)/desvio; 

(1) desvio padrao = ((somatorio(x[i]-media)^2)/(n*n+n)/2)^(1/2);
								   */

#include <stdio.h>
#include <stdlib.h>
#include <math.h>

int main(void) { 
	int n, i, j; // n eh o numero de linhas da matriz, i e j sao auxiliares para percorrer essa matriz
	double **vetor, media=0, desvio=0; 
	scanf("%d", &n);
	
	vetor = malloc(sizeof(double*)*n); // alocando dinamicamente apontadores de apontadores para double

	for(i=0, j=1; j<=n; i++, j++) 
		*(vetor+i) = malloc(sizeof(double)*j); // alocando os apontadores para double

	for(i=0; i<n; i++) 
		for(j=0; j<(i+1); j++) { 
			scanf("%lf", &vetor[i][j]); // preenchendo as regioes dos apontadores com doubles
			media+=vetor[i][j]; // somando os valores
			}

	media/=(n*n+n)/2; // ...dividir e calcular a media

	for(i=0; i<n; i++) 
		for(j=0; j<(i+1); j++)
			desvio+=pow((vetor[i][j]-media),2); // utilizando a formula do cabecalho para desvio padrao(1)
	
	desvio/=(n*n+n)/2;
	desvio=sqrt(desvio);
	
	for(i=0; i<n; i++) {
		for(j=0; j<(i+1); j++)
			printf("%.12lf ",(vetor[i][j]-media)/desvio); // usando (2) para normalizar os valores a serem impressos
		free(vetor[i]);	// liberando cada apontar de double   
		printf("\n");
	}
	free(vetor); // liberando o apontador de apontadores de double
	printf("\n%.12lf %.12lf \n", media, desvio);

	return 0;
} 	
