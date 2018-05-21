//Matheus Rotta Alves (184403)
#include <limits.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#define bit_set(A,i) ((A)[bit_slot(i)] |= bit_mask(i)) 
#define bit_clear(A,i) ((A)[bit_slot(i)] &= ~bit_mask(i)) 
#define bit_test(A,i) ((A)[bit_slot(i)] & bit_mask(i)) 
#define bit_slot(i) ((i) / CHAR_BIT) 
#define bit_nslots(n) ((n) / CHAR_BIT + 1) 
#define bit_mask(i) (1 << ((i) % CHAR_BIT)) 

int size = 0;

unsigned char * create (void) {
	int n;
	scanf("%d", &n); //o vetor vai de 1 a n
	unsigned char *B = calloc(bit_nslots(n), sizeof(unsigned char));
	size = n;
	return B;
}

int main(void) {
	char comando[20];
	unsigned char *vet;
	int count = 0;
	int i;
	int p = 0;
	int j, k;
	while(1) {
		scanf("%s", comando);
		if(strcmp(comando, "create") == 0) {
			if (count > 0) {
				free(vet);
			}
			vet = create();
			count++;
		}
		else if(strcmp(comando, "add") == 0) {
			scanf("%d", &i);
			if ( i >= 0 && i <= size) {
				bit_set(vet, i);
			}
		}
		else if(strcmp(comando, "remove") == 0) {
			scanf("%d", &i);
			if ( i >= 0 && i <= size) {
				bit_clear(vet, i);
			}
		}
		else if(strcmp(comando, "in") == 0) {
			scanf("%d", &i);
			if ( i >= 0 && i <= size) {
				if(bit_test(vet, i)) {
					printf("belongs(%d) = true\n", i);
				}
				else {
					printf("belongs(%d) = false\n", i);
				}
			}
		}
		else if(strcmp(comando, "rank") == 0) {
			scanf("%d", &i);
			for (j = 1; j <= i; j++) {
				p = p + (bit_test(vet, j) != 0);
			}
			printf("rank(%d) = %d\n", i, p);
			p = 0; //reseta p para uso futuro
		}
		else if(strcmp(comando, "select") == 0) {
			scanf("%d", &i);
			for (j = 1; j <= size; j++) {
				p = p + (bit_test(vet, j) != 0);
				if (p == i) {
					printf("select(%d) = %d\n", i, j);
					break;
				}
			}
			if (p == 0) { //essa condicao existe apenas porque quem criou o lab foi desatento
				printf("select(%d) = 0\n", i);
			}
			p = 0; //reseta p para uso futuro
		}
		else if(strcmp(comando, "rangecount") == 0) {
			scanf("%d %d", &j, &k);
			for(i = j; i <= k; i++) {
				p = p + (bit_test(vet, i) != 0);
			}
			printf("rangecount(%d,%d) = %d\n", j, k, p);
			p = 0;
		}
		else if(strcmp(comando, "exit") == 0) {
			if (count > 0) free(vet);
			return 0;
		}
		else if(strcmp(comando, "print") == 0) {
			printf("S = {");
			for (j = 1; j <= size; j++) {
				if (bit_test(vet, j)) p++;
			}
			for (j = 1, i = 0; j <= size; j++) {
				if (bit_test(vet, j)) {
					if (i == p - 1) { //se vc estiver procurando o ultimo elemento, imprima sem a virgula
						printf("%d", j);
					}
					else {
						printf("%d,", j);
					}
					i++;	
				} 
			}
			printf("}\n");
			p = 0;
		}
	}
}



