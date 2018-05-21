#include <stdio.h>
#include <stdlib.h>

void sort(long int *valor, long int *linha, long int *coluna, long int N) {

	int minimo = 999999999, auxiliar, posicao = -1, i, j;
	for(j=0; j<N; j++, minimo=999999999, posicao=-1) { 
		for(i=j; i<N; i++) { 
			if(linha[i]<minimo) {
				minimo = linha[i];
				posicao = i;
			}
		}
		if(posicao) { 
			auxiliar = valor[posicao];
			valor[posicao] = valor[j];
			valor[j] = auxiliar;
			auxiliar = coluna[posicao];
			coluna[posicao] = coluna[j];
			coluna[j] = auxiliar;
			auxiliar = linha[posicao];
			linha[posicao] = linha[j];
			linha[j] = auxiliar;
		}
	}
}
void sort_pedaco(long int *valor, long int *coluna, long int inic, long int fim) {

	int minimo = 999999999, auxiliar, posicao = -1, j, i;
	for(j=inic; j<=fim; j++, minimo=9999999, posicao=-1) { 
		for(i=j; i<=fim; i++) { 
			if(coluna[i]<minimo) {
				minimo = coluna[i];
				posicao = i;
			}
		}
		if(posicao) { 
			auxiliar = valor[posicao];
			valor[posicao] = valor[j];
			valor[j] = auxiliar;
			auxiliar = coluna[posicao];
			coluna[posicao] = coluna[j];
			coluna[j] = auxiliar;
		}
	}
}
int main(void) { 

	long int k, i, j, count=-1, pos=0, guarda;
	scanf("%ld\n", &k);
	long int *valor = malloc(sizeof(long int)*k);
	long int *coluna = malloc(sizeof(long int)*k);
	long int *linha = malloc(sizeof(long int)*k);
	for(i=0; i<k; i++) 
		scanf(" %ld %ld %ld\n", &linha[i], &coluna[i], &valor[i]);
	
	sort(valor, linha, coluna, k);
		
	pos = linha[k-1];
	long int *linhaT = malloc(sizeof(long int)*pos+1);
	
	i=0;
	while(count+1<linha[i])
		linhaT[++count] = -1;
	for(i=0; i<k;) {
		j=i;
		guarda = linha[i];
		if(j>k)
			break;
		linhaT[++count] = j;	
		while(linha[j]==linha[j+1] && j+1<k)
			j++;	
		sort_pedaco(valor, coluna, i, j);
		i=j+1;
		if(i>=k)
			break;
		while(guarda+1<linha[i]) {
			guarda++;
			linhaT[++count] = -1;
		}
	}
	while(1) {
		guarda = 0; 
		scanf("%ld %ld\n", &i, &j);
		if(i<0)
			break;
		else if(i>pos)
			printf("(%ld,%ld) = 0\n", i, j);
		else if(linhaT[i]==-1)
			printf("(%ld,%ld) = 0\n", i, j);
		else {
			guarda = linhaT[i]; 
			while(guarda < k && guarda != linhaT[i+1] && coluna[guarda]!=j)
				guarda++;
			if(coluna[guarda]==j)
				printf("(%ld,%ld) = %ld\n", i, j, valor[guarda]);
			else
				printf("(%ld,%ld) = 0\n", i, j);
		} 			
	}
	free(linhaT);	
	free(valor);
	free(coluna);
	free(linha);

	return 0;
}
