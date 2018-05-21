/* Nome: Gabriel Pellegrino da Silva  RA: 172358

	O programa a seguir visa ler as  matrizes das cores elementares, azul, verde e vermelho; somar os valores ponto a ponto dessas matrizes e obter uma média, essa média corresponde à matriz em escala cinza correspondente aos dados fornecidos. A partir dessa matriz cinza, aplicaremos um filtro na imagem dada, pelo sistema de convolucao. 

 */

#include <stdio.h>
#include <stdlib.h>

//Declarando as variaveis globais que acompanharão a execucao de todo o programa
int L, A;
int F[600][600], V[600][600];

int leCompac() {
	
	//Declarando as variáveis auxiliares na leitura da matriz compactada		
	int B=0, C, t=0, s=0, j, D;
		
	//Descompactação da matriz fornecida, lendo o valor do numero de vezes que o bloco sera repetido (D),
	//e tambem qual valor(C) sera colocado na matriz V, D vezes. 
	while(B<A*L) {	
		scanf("%d %d", &D, &C);
		B+=D;	
		for(j=0; j<D; j++, s++) {
			if(s==L) {
				t++;
				s=0;
			}
			V[t][s]+=C;
		}	
	}
	return 0;
}

int main(int argc, char* argv[]) {

	/* Declarando as variáveis da função main */
	int M[10][10];
	int D, X, aux=0, t, s, q, p;
	int i=0, j=0;

	//Leitura do inteiro D
	scanf("%d", &D);

	//Leitura do inteiro ímpar X
	scanf("%d", &X);
	
	//leitura da matriz M, quadrada de ordem X 
	for(i=0; i<X; i++) {
		for(j=0; j<X; j++) {
			scanf("%d", &M[i][j]);
		}
	}
	//Leitura das dimensões da imagem, largura e altura
	scanf("%d %d", &L, &A);
	
	
	//Inicilizando a matriz V com 0 em todos as suas posicoes
	for(i=0; i<A; i++) {
		for(j=0; j<L; j++) { 
			V[i][j] = 0; 
		}
	}
	leCompac(); 
	leCompac();  //Chamando a funcao leCompac 3 vezes para somar na matriz V, as matrizes de cores azul, verde e vermelha
	leCompac();

	//Apos a leitura das cores, transformando a matriz de cores na matriz de escala cinza (V/=3)
	for(t=0; t<A; t++) { 
		for(s=0; s<L; s++) { 
			V[t][s]= (V[t][s])/3;
		}
	} 
	//Transferindo os valores de V, na escala cinza, para a matriz final F
	for(t=0; t<A; t++) { 
		for(s=0; s<L; s++) { 
			F[t][s] = V[t][s];
		}
	} 
	
	//Aplicando o filtro à matriz em escala cinza(V), transformando-a na matriz final F
	for(i=X/2; i<A-(X/2); i++) { //i percorre as linhas de V
		for(j=X/2; j<L-(X/2); j++) {	//j percorre as colunas de V
			for(t=0, p=-(X/2); t<X; t++, p++) { //t percorre as linhas de M 
				for(s=0, q=-(X/2); s<X; s++, q++) { //s percorre as colunas de M
					aux+=V[i+p][j+q]*M[t][s]; //guardando o valor do ponto convolucionado
				}	
			}
			F[i][j] = aux/D; //passando o valor da aux, dividida por D, para a matriz final
			aux=0; //preparando aux para o próximo ponto
			if(F[i][j]>255) { //trucando para 255, se necessario
				F[i][j] = 255; 
			}
			else if(F[i][j]<0) { //trucando para 0, se necessario
				F[i][j] = 0;
			} 	
		}
	}
	puts("P2");
	printf("%d %d\n", L, A); //imprimindo as saidas pedidas
	puts("255");
	for(i=0; i<A; i++) { 
		for(j=0; j<L; j++) { 
			if(j==L-1)
				printf("%d", F[i][j]);
			else					//e tambem a matriz F
				printf("%d ", F[i][j]);
		}
		printf("\n");
	}					
	return 0;
}	
