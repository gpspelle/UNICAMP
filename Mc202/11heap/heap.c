#include <stdio.h>
#include <stdlib.h>
#define max 99999
int pos = 0;
void inserir(int *vetor, int *heap) {
	
	int k, c, aux, guarda;
	scanf(" %d %d\n", &k, &c);
	
	if(heap[k]==max) {
		vetor[++pos] = k;
		heap[k] = c;
		aux = pos;
		while(aux>1 && heap[vetor[aux/2]]>heap[vetor[aux]]) { 
				
			guarda = vetor[aux/2];
			vetor[aux/2] = vetor[aux];
			vetor[aux] = guarda;
			
			aux = aux/2;
		}
		while(aux <=pos) {
			if(heap[vetor[aux]]>heap[vetor[aux+1]]) {
				guarda = vetor[aux+1];
				vetor[aux+1] = vetor[aux];
				vetor[aux] = guarda;
			}
			aux+=1;
		}
		
	}
}
void remover(int *vetor, int *heap, int N) { 

	int aux=1, guarda;
	if(pos<=0)
		printf("vazio\n");
	else {
		printf("minimo {%d,%d}\n", vetor[1], heap[vetor[1]]);
		vetor[1] = vetor[pos];
		pos--;
		while(aux < pos/2) {
			
			aux = 2*aux;
		}
		pos--; 
	}	
}
void diminuir(int *vetor, int *heap, int N) { 

	int k, c, guarda, i=1;
	scanf(" %d %d\n", &k, &c);
		
	heap[k] = c;
	while(vetor[i]!=k)
		i++;
		
	while(i>1 && heap[vetor[i/2]]>heap[vetor[i]]) { 
				
		guarda = vetor[i/2];
		vetor[i/2] = vetor[i];
		vetor[i] = guarda; 
						
		i = i/2;
	} 		
}
void menu() {

	int N, i, guarda;
	char c=0;
	scanf("%d\n", &N);
	int *vetor = (int *)malloc(sizeof(int)*N+1);
	int *heap = (int *)malloc(sizeof(int)*N+1);
	
	for(i=0; i<=N; i++)
		heap[i] = max;
		
	while(c!='t') {
		scanf("%c", &c);
		switch(c) { 
			case 'i': inserir(vetor, heap); break;
			case 'm': remover(vetor, heap, N); break;
			case 'd': diminuir(vetor, heap, N); break;
		}
	}
	printf("\n");
	for(i=1; i<=N; i++) {
		printf("Posicao [%d] -> Chave %d", i, vetor[i]);
		printf("      <- %d\n", heap[vetor[i]]);
	}
	printf("\n\n"); 
	
	//free(vetor);	
	//free(heap);
}
int main(void) { 
	
	menu();
	
	return 0;
}
