#include <stdio.h>
#include <stdlib.h>

typedef struct lista {
    long long int valor;
    int freq;
    struct lista *prox;
} lista;

int main(void) {

    int N, i, j;
    long long int C;
    scanf(" %d %lld", &N, &C);

    lista *dummy = (lista *) malloc(sizeof(lista));
    dummy->valor = 0;
    dummy->prox = NULL; 
    
    for(i = 0; i < N; i++) {
        int stop = 0; 
        scanf(" %lld", &C);
        lista *aux = dummy; 
        lista *aux_2 = dummy;
        while(aux != NULL) {
            /* Encontramos na lista */
            if(aux->valor == C) {
                aux->freq++; 
                stop = 1;
                break;
            } else {
                aux_2 = aux;
                aux = aux->prox;
            }
        } 

        /* Se nao tava na lista */
        if(stop == 0) {
            aux_2->prox = (lista *) malloc(sizeof(lista)); 
            aux_2->prox->valor = C;
            aux_2->prox->freq = 1;
        }
    }
  
    for(i = 0; i < N; i++) {
        lista *best = NULL;
        lista *aux_3 = dummy->prox; 
        long long int max = -1;
        while(aux_3 != NULL) {
            if(aux_3->freq > max) {
                max = aux_3->freq;
                best = aux_3; 
            } 
            aux_3 = aux_3->prox;
        }
        for(j = 0; j < best->freq; j++) {
            //if(i != N-1) {
                printf("%lld ", best->valor);
            //}
            /*else if(j == best->freq -1) { 
                printf("%lld", best->valor);
            }*/
        }
        best->freq = 0;
        best->valor = 666;
    }  
    return 0;
}
