#include <stdio.h>
#include <stdlib.h>

int pos = -1;

void inserir(int *vetor, int **heap)
{

    int k, c, aux, guarda, guarda2;

    scanf(" %d %d\n", &k, &c);

    if (vetor[k] == -1) {
	vetor[k] = ++pos;
	heap[pos][0] = c;
	heap[pos][1] = k;
	aux = pos;
	while (aux > 0 && heap[(aux - 1) / 2][0] > heap[aux][0]) {
	    guarda = heap[(aux - 1) / 2][0];
	    heap[(aux - 1) / 2][0] = heap[aux][0];
	    heap[aux][0] = guarda;
	    guarda = heap[(aux - 1) / 2][1];
	    heap[(aux - 1) / 2][1] = heap[aux][1];
	    heap[aux][1] = guarda;

	    guarda2 = vetor[guarda];
	    vetor[guarda] = vetor[heap[(aux - 1) / 2][1]];
	    vetor[heap[(aux - 1) / 2][1]] = guarda2;

	    aux = (aux - 1) / 2;
	}
    }
}

void remover(int *vetor, int **heap, int N)
{

    int aux = 0, guarda, guarda2;
    if (pos < 0) {
	printf("vazio\n");
	return;
    } else {
	guarda = heap[0][1];
	vetor[guarda] = -1;
	guarda = heap[pos][1];
	vetor[guarda] = 0;
	printf("minimo {%d,%d}\n", heap[0][1], heap[0][0]);
	heap[0][0] = heap[pos][0];
	heap[0][1] = heap[pos][1];
	heap[pos][0] = 0;
	heap[pos][1] = 0;
	pos--;
	/*printf("Pos: %d e pos/2: %d\n", pos, pos/2);
	   for(i=0; i<N; i++) {
	   printf("i: %d -> %d %d", i, heap[i][0], heap[i][1]);
	   printf("      <- %d\n", vetor[heap[i][1]]);
	   } */
	while (2 * aux < pos) {
	    if (heap[aux][0] > heap[2 * aux + 1][0]
		&& heap[2 * aux + 1][0] < heap[2 * aux + 2][0]) {
	      OTHER:guarda = heap[2 * aux + 1][0];
		heap[2 * aux + 1][0] = heap[aux][0];
		heap[aux][0] = guarda;
		guarda = heap[2 * aux + 1][1];
		heap[2 * aux + 1][1] = heap[aux][1];
		heap[aux][1] = guarda;

		guarda2 = vetor[guarda];
		vetor[guarda] = vetor[heap[2 * aux + 1][1]];
		vetor[heap[2 * aux + 1][1]] = guarda2;

		aux = (2 * aux) + 1;
	    } else if (heap[aux][0] > heap[2 * aux + 1][0]
		       && (2 * aux + 2) > pos) {
		goto OTHER;
	    } else if (heap[aux][0] > heap[2 * aux + 2][0]
		       && (2 * aux + 2) <= pos) {
		guarda = heap[2 * aux + 2][0];
		heap[2 * aux + 2][0] = heap[aux][0];
		heap[aux][0] = guarda;
		guarda = heap[2 * aux + 2][1];
		heap[2 * aux + 2][1] = heap[aux][1];
		heap[aux][1] = guarda;

		guarda2 = vetor[guarda];
		vetor[guarda] = vetor[heap[2 * aux + 2][1]];
		vetor[heap[2 * aux + 2][1]] = guarda2;

		aux = (2 * aux) + 1;
	    } else
		break;
	}
	/*printf("\n");
	   for(i=0; i<N; i++) {
	   printf("i: %d -> %d %d", i, heap[i][0], heap[i][1]);
	   printf("      <- %d\n", vetor[heap[i][1]]);
	   }
	   printf("\n\n"); */
    }
}

void diminuir(int *vetor, int **heap)
{

    int k, c, aux, guarda, guarda2;
    scanf(" %d %d\n", &k, &c);

    aux = vetor[k];
    heap[aux][0] = c;

    while (aux > 0 && heap[(aux - 1) / 2][0] > heap[aux][0]) {
	guarda = heap[(aux - 1) / 2][0];
	heap[(aux - 1) / 2][0] = heap[aux][0];
	heap[aux][0] = guarda;
	guarda = heap[(aux - 1) / 2][1];
	heap[(aux - 1) / 2][1] = heap[aux][1];
	heap[aux][1] = guarda;

	guarda2 = vetor[guarda];
	vetor[guarda] = vetor[heap[(aux - 1) / 2][1]];
	vetor[heap[(aux - 1) / 2][1]] = guarda2;

	aux = (aux - 1) / 2;
    }
}

void menu()
{

    int N, i;
    char c = 0;
    scanf("%d\n", &N);
    int *vetor = (int *) malloc(sizeof(int) * N);
    int **heap = (int **) malloc(sizeof(int *) * N);

    for (i = 0; i < N; i++) {
	heap[i] = (int *) malloc(sizeof(int) * 2);
	vetor[i] = -1;
    }
    while (c != 't') {
	scanf("%c", &c);
	switch (c) {
	case 'i':
	    inserir(vetor, heap);
	    break;
	case 'm':
	    remover(vetor, heap, N);
	    break;
	case 'd':
	    diminuir(vetor, heap);
	    break;
	}
    }
    for (i = 0; i < N; i++)
	free(heap[i]);

    free(heap);
}

int main(void)
{

    menu();

    return 0;
}
