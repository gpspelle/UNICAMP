#include <stdlib.h>
#include <stdio.h>
#include <time.h>

void insertion(int *vetor, int tamanho) {

	int i, j, auxiliar;
	for(i=1; i<tamanho; i++) {
		j=i;
		while(j>0) {
			if(vetor[j]<vetor[j-1]) {
				auxiliar = vetor[j];
				vetor[j] = vetor[j-1];
				vetor[j-1] = auxiliar;
			}
			j--;
		}
	}
}
void selection(int *vetor, int tamanho) {

	int i, j, auxiliar, min;
	for(i=0; i<tamanho; i++) {
		min = i;
		for(j=i+1; j<tamanho; j++)
			if(vetor[j]<vetor[min])
				min = j;
		
		if(i!=min) {
			auxiliar = vetor[i];
			vetor[i] = vetor[min];
			vetor[min] = auxiliar;
		}	
	}
}
void bubble(int *vetor, int tamanho) { 
	
	int j, i=0, auxiliar;
	while(i<tamanho-1) {
		i=0;
		for(j=0; j<tamanho-1; j++) {
			if(vetor[j]>vetor[j+1]) {
				auxiliar = vetor[j];
				vetor[j] = vetor[j+1];
				vetor[j+1] = auxiliar;
			}
			else {
				i++;
			}
		}
	}
}
void mergesort(int* vetor, int inicio, int fim) { 

	int i, j, k, metade, *auxiliar;
	
	if(inicio==fim)
		return;

	metade = (inicio+fim)/2;
	mergesort(vetor, inicio, metade);
	mergesort(vetor, metade+1, fim);
	
	i = inicio;
	j = metade+1;
	k=0;
	
	auxiliar = malloc(sizeof(int)*(fim-inicio+1));
	
	while(i<metade+1 || j<fim+1) { 
		if(i==metade+1) {
			auxiliar[k] = vetor[j];
			j++;
			k++;
		}
		else {
			if(j==fim+1) { 
				auxiliar[k] = vetor[i];
				i++;
				k++;
			}
			else {
				if(vetor[i] < vetor[j]) { 
					auxiliar[k] = vetor[i];
					i++;
					k++;
				}
				else {
					auxiliar[k] = vetor[j];
					j++;
					k++;
				}
			}
		}
	}
	for(i=inicio; i<=fim; i++) 
		vetor[i] = auxiliar[i-inicio];
	
	free(auxiliar);
} 
int particao(int v[],int esq,int dir) {
	
	int i, fim;
	void troca(int v[],int i,int j);
	
	troca(v,esq,(esq+dir)/2);
	
	fim=esq;
	
	for(i=esq+1;i<=dir;i++)
		if(v[i]<v[esq]) troca(v,++fim,i);
			troca(v,esq,fim);
	
	return fim;
}
void quicksort(int v[], int esq, int dir) {
  
	int i;
	if(esq>=dir) 
		return;
  
	i=particao(v,esq,dir);
	quicksort(v,esq,i-1);
	quicksort(v,i+1,dir);

}
void troca(int v[], int i,int j) {  
	
	int temp;
	
	temp=v[i];
	v[i]=v[j];
	v[j]=temp;
}
int main(void) { 

	FILE *fp;
	fp = fopen("quick7.txt", "a"); // 100000, 200000, 300000, 400000, 500000, 600000, 70000
	clock_t inic, fim;
	int i, tamanho = 700000;
	srand(8000);
	int vetor[tamanho];
	for(i=0; i<tamanho; i++) 
		vetor[i] = rand() % 1000000000;
		
	inic = clock();	
	quicksort(vetor, 0, tamanho-1);
	fim = clock();
	
	fprintf(fp, "%lf\n", (double)1000*(fim-inic)/CLOCKS_PER_SEC);
	
	fclose(fp);
	
	return 0;
}
