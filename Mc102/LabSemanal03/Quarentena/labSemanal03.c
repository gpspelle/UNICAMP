#include <stdio.h>
#include <stdlib.h>

int L, A;
int F[600][600], V[600][600];

int leCompac() {
	
	//Declarando os vetores auxiliares na leitura da matriz compactada		
	int B=0, t=0, s=0, j;
	int C, D;
		
	//Descompactação da matriz fornecida
	while(B<A*L) {	
		scanf("%d %d", &D, &C);
		B+=D;	
		for(j=0; j<D; j++, s++) {
			if(s%L==0 && j!=0) {
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
	int M[12][12];
	int D, X;
	int aux=0;
	int t, s, q, p;
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
	
	
	//Leitura da matriz comprimida RGB, com a função leCompac
	for(i=0; i<A; i++) {
		for(j=0; j<L; j++) { 
			V[i][j] = 0; 
		}
	}
	leCompac();
	leCompac();
	leCompac();

	for(t=0; t<A; t++) { 
		for(s=0; s<L; s++) { 
			F[t][s]= V[t][s]/3;
		}
	} 
	//Aplicando o filtro à matriz em escala cinza(R2)
	if(X==1) { 
		for(i=0; i<A; i++) { 
			for(j=0; j<L; j++) { 
				F[i][j] = (V[i][j]*M[0][0])/D; 
			}
		}
	}
	else if(X==3) {
		for(i=1; i<A-1; i++) {
			for(j=1; j<L-1; j++) {	
				for(t=0, p=-1; t<X; t++, p++) { 
					for(s=0, q=-1; s<X; s++, q++) { 
						aux+=V[i+p][j+q]*M[t][s]; 
					}	
				}
				F[i][j] = aux/D;
				aux=0;
				if(F[i][j]>255) { 
					F[i][j] = 255; 
				}
				else if(F[i][j]<0) { 
					F[i][j] = 0;
				} 	
			}
		}
	}
	else if(X==5) { 
		for(i=2; i<A-2; i++) {
			for(j=2; j<L-2; j++) { 
				for(t=0, p=-2; t<X; t++, p++) { 
					for(s=0, q=-2; s<X; s++, q++) { 
						aux+=V[i+p][j+q]*M[t][s]; 
					}	
				}
				F[i][j] = aux/D;
				aux=0;
				if(F[i][j]>255) { 
					F[i][j] = 255; 
				}
				else if(F[i][j]<0) { 
					F[i][j] = 0;
				}	
			}
		}
	}
	else if(X==7) { 	
		for(i=3; i<A-3; i++) {
			for(j=3; j<L-3; j++) { 
				for(t=0, p=-3; t<X; t++, p++) { 
					for(s=0, q=-3; s<X; s++, q++) { 
						aux+=V[i+p][j+q]*M[t][s]; 
					}	
				}
				F[i][j] = aux/D;
				aux=0;
				if(F[i][j]>255) { 
					F[i][j] = 255; 
				}
				else if(F[i][j]<0) { 
					F[i][j] = 0;
				}		
			}
		}
	}
	else if(X==9) {
		for(i=4; i<A-4; i++) {
			for(j=4; j<L-4; j++) { 
				for(t=0, p=-4; t<X; t++, p++) { 
					for(s=0, q=-4; s<X; s++, q++) { 
						aux+=V[i+p][j+q]*M[t][s]; 
					}	
				}
				F[i][j] = aux/D;
				aux=0;
				if(F[i][j]>255) { 
					F[i][j] = 255; 
				}
				else if(F[i][j]<0) { 
					F[i][j] = 0;
				}		
			}
		}
	}	
	puts("P2");
	printf("%d %d\n", L, A);
	puts("255");
	for(i=0; i<A; i++) { 
		for(j=0; j<L; j++) { 
			printf("%d ", F[i][j]);
		}
		printf("\n");
	}					
	return 0;
}	

