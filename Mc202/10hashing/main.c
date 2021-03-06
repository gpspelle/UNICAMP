#include <stdio.h> 

typedef struct {

	long int chave;
	int valor;

}Tinfo;

void inicializa(Tinfo *vetor) { 

	int i;
	for(i=0; i<62500; i++)
		vetor[i].chave = -1;
}
void inserir(Tinfo *vetor) {

	int valor, l=0;
	long int chave, posicao;
	scanf("%ld %d", &chave, &valor);
	do {
		posicao = (chave%62501 + l*(1 + (chave%62499)))%62501;
		l++;
		if(chave == vetor[posicao].chave)
			break;
	}while(vetor[posicao].chave != -1);
	
	vetor[posicao].valor = valor;
	vetor[posicao].chave = chave;
}
void buscar(Tinfo *vetor) { 

	int l=0, para=0;
	long int chave, posicao;
	scanf(" %ld", &chave);
	
	do {
		posicao = (chave%62501 + l*(1 + (chave%62499)))%62501;
		l++;
		if(posicao<62500) {
			if(vetor[posicao].chave == chave) {
				printf("valor para %ld: %d\n", chave, vetor[posicao].valor);
				para = 1;
				break;
			}
		}
	}while(l<50000);
	if(!para)
		printf("%ld nao existe\n", chave);
}
void remover (Tinfo *vetor) { 

	int l=0;
	long int chave, posicao;
	scanf("%ld", &chave);
	do {
		posicao = (chave%62501 + l*(1 + (chave%62499)))%62501;
		l++;
		
	}while(vetor[posicao].chave == -1);

	vetor[posicao].chave = -1;
}
void menu() {
	
	char c=0;
	Tinfo vetor[62500];
	inicializa(vetor);
	while(c!='f') { 
		scanf("%c", &c);
		switch(c) { 
			case 'i': inserir(vetor); break;
			case 'b': buscar(vetor); break;
			case 'r': remover(vetor); break;
			case 'f': break;
		}
	}
}
int main(void) {
	
	menu();
	
	return 0;
}
