#include <stdio.h>
#include <stdlib.h>

int main(void) {

    int J, R, i, j;
    scanf(" %d %d", &J, &R);  

    int *vetor = malloc(sizeof(int)*J+1); 
    int aux;
    for(i = 0; i < R; i++) {
        for(j = 1; j <= J; j++) {
            scanf(" %d", &aux); 
            vetor[j] += aux;
        }
    }
   
    int big = -1, winner;
    for(i = 1; i <= J; i++) {
        if(vetor[i] >= big) {
            big = vetor[i];
            winner = i;
        }
    }
    
    printf("%d\n", winner);
    return 0;
}
