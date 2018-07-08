#include <stdio.h>

int main(void) {

    int N, i, j, aux;
    int vetor[10000] = {0};
    scanf(" %d", &N);
    for(i = 0; i < N; i++) { 
        scanf(" %d", &aux);
        vetor[aux]+=1;
    }

    int coins = 0;
    for(i = 1; i <= N; i++) {
        for(j = i + 1 ; vetor[i] > 1; j++) {
            if(vetor[j] == 0) {
                vetor[i] -= 1;
                vetor[j] = 1;
                coins += (j - i);
            }
        }
    }

    /*for(i = 1; i <= N; i++)
        printf("%d ", vetor[i]);
    printf("\n");*/
    
    printf("%d\n", coins);
    return 0;
}
