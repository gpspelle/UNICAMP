#include "din.h"

int main(void) {
	int i;
	char **vetor = (char **)malloc(sizeof(char*)*4);
	Tinfo *info = (Tinfo *)malloc(sizeof(Tinfo));
	info->tam=4, info->ocup=0, info->f = 0; info->l = 1;
	
	opcao(info, &vetor);
	for(i=0; i<info->tam; i++)
		free(vetor[i]);
	free(vetor);
	return 0;
}
