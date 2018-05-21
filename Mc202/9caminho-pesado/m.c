#include <stdio.h>
typedef struct no{

	struct no *left;
	struct no *right;
	int data;
	double peso;
}Tno;

Tno* leitura(int quantidade, int i, char* cadeia) { 

	char c;
	Tno *raiz = (Tno *)malloc(sizeof(Tno));
	do {
		if(!i%100)
			cadeia = (char *)realloc(cadeia, 2*i);
		
		c=getchar();
		cadeia[i++]=c;
	
		if(c=='(') {
			quantidade++;
			raiz = leitura(quantidade, i, cadeia);
		}
		else if(c==')') {
			if(cadeia[i-2]=='(') 
				 
			quantidade--;
		}
		
	}while(quantidade);
	
	return raiz;
}
int main(void) { 

	Tno *raiz;
	int quantidade=0, i;
	char cadeia = (char *)malloc(sizeof(char)*101);
	raiz = leitura(quantidade, i, cadeia);
	
	return 0;
}
